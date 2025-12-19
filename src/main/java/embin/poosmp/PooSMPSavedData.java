package embin.poosmp;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embin.poosmp.networking.payload.DataSyncPayload;
import embin.poosmp.upgrade.Upgrade;
import embin.poosmp.util.PooUtil;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Util;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;

import java.util.*;

@SuppressWarnings({"NullableProblems", "DataFlowIssue"})
public class PooSMPSavedData extends SavedData {
    public static final String NBT_KEY = "poosmp_data";
    public static final Codec<PooSMPSavedData> CODEC = RecordCodecBuilder.create(u -> u.group(
        uuidMapCodec(Codec.DOUBLE).fieldOf("player_balance").forGetter(z -> z.balance),
        uuidMapIdCodec(Codec.INT).fieldOf("purchased_upgrades").forGetter(z -> z.purchasedUpgrades)
    ).apply(u, PooSMPSavedData::new));
    public static final SavedDataType<PooSMPSavedData> TYPE = new SavedDataType<>("poosmp", PooSMPSavedData::new, PooSMPSavedData.CODEC, null);
    public Map<UUID, Map<Identifier, Integer>> purchasedUpgrades;
    public Map<UUID, Double> balance;
    private final Logger logger = LogUtils.getLogger();

    public PooSMPSavedData(
            Map<UUID, Double> balance,
            Map<UUID, Map<Identifier, Integer>> purchases
    ) {
        this.balance = new HashMap<>(balance);
        this.purchasedUpgrades = fixMaps(purchases);
    }

    public PooSMPSavedData() {
        this.balance = HashMap.newHashMap(16);
        this.purchasedUpgrades = HashMap.newHashMap(16);
    }

    public Map<Identifier, Integer> getPurchasedUpgradesIdMap(Player player) {
        UUID playerUuid = player.getUUID();
        if (!this.purchasedUpgrades.containsKey(playerUuid)) {
            this.purchasedUpgrades.put(playerUuid, HashMap.newHashMap(16));
            this.setDirty();
        }
        return this.purchasedUpgrades.get(playerUuid);
    }

    public List<Upgrade> upgradesPlayerHas(Player player) {
        return this.getPurchasedUpgradesIdMap(player).keySet().stream()
                .map(getUpgradeRegistry(player)::getOptional)
                .filter(Optional::isPresent)
                .map(Optional::orElseThrow)
                .toList();
    }

    public boolean hasUpgrade(Player player, Upgrade upgrade) {
        return this.upgradesPlayerHas(player).contains(upgrade);
    }

    public void buyUpgrade(Player player, Upgrade upgrade) {
        Identifier upgradeId = upgrade.getId(getUpgradeRegistry(player));
        final int amountPurchased = this.getPurchasedUpgradesIdMap(player).getOrDefault(upgradeId, 0);
        upgrade.onPurchase(player);
        this.getPurchasedUpgradesIdMap(player).put(upgradeId, amountPurchased + 1);
        this.setDirty();
    }

    public void sellUpgrade(Player player, Upgrade upgrade) {
        Identifier upgradeId = upgrade.getId(getUpgradeRegistry(player));
        final int amountPurchased = this.getPurchasedUpgradesIdMap(player).getOrDefault(upgradeId, 0);
        if (amountPurchased > 0) {
            upgrade.onSell(player);
            this.getPurchasedUpgradesIdMap(player).put(upgradeId, amountPurchased - 1);
            this.setDirty();
        } else {
            this.logger.warn("{} attempted to sell {} whilst not having it!", player.getPlainTextName(), upgradeId);
        }
    }

    public double getBalance(Player player) {
        UUID uuid = player.getUUID();
        if (!this.balance.containsKey(uuid)) {
            this.balance.put(uuid, 0D);
            this.setDirty();
        }
        return this.balance.get(uuid);
    }

    public double getTrimmedBalance(Player player) {
        return PooSMPSavedData.trimBalance(this.getBalance(player));
    }

    public boolean addBalance(Player player, double amount) {
        double currentBalance = this.getTrimmedBalance(player);
        final double trimmedAmount = PooSMPSavedData.trimBalance(amount);
        final double newAmount = Math.clamp(currentBalance + trimmedAmount, 0.0D, Double.MAX_VALUE);
        this.balance.put(player.getUUID(), newAmount);
        this.setDirty();
        return newAmount == (currentBalance + trimmedAmount);
    }

    public int upgradePurchaseAmount(Player player, Upgrade upgrade) {
        return this.getPurchasedUpgradesIdMap(player).getOrDefault(upgrade.getId(getUpgradeRegistry(player)), 0);
    }

    public Tag asNbt() {
        return PooSMPSavedData.CODEC.encodeStart(NbtOps.INSTANCE, this).getOrThrow();
    }

    public static PooSMPSavedData get(MinecraftServer server) {
        ServerLevel serverLevel = server.getLevel(ServerLevel.OVERWORLD);
        if (serverLevel == null) return new PooSMPSavedData();
        return serverLevel.getDataStorage().computeIfAbsent(PooSMPSavedData.TYPE);
    }

    public static PooSMPSavedData get(Player player) {
        MinecraftServer server = player.level().getServer();
        if (server != null) return PooSMPSavedData.get(server);
        return new PooSMPSavedData(); // i don't think returning null makes sense
    }

    public static void syncToClient(ServerPlayer player) {
        CompoundTag data = new CompoundTag();
        PooSMPSavedData savedData = PooSMPSavedData.get(player.level().getServer());
        PooSMPSavedData sendData = Util.make(new PooSMPSavedData(), poodata -> {
            poodata.balance = Util.make(HashMap.newHashMap(1), map -> map.put(player.getUUID(), savedData.getBalance(player)));
            poodata.purchasedUpgrades = Util.make(HashMap.newHashMap(1), map -> {
                Map<Identifier, Integer> mapSync = savedData.getPurchasedUpgradesIdMap(player);
                map.put(player.getUUID(), mapSync);
            });
        });
        data.put(PooSMPSavedData.NBT_KEY, sendData.asNbt());
        ServerPlayNetworking.send(player, new DataSyncPayload(data));
    }

    private static <T> Codec<Map<UUID, T>> uuidMapCodec(Codec<T> valueCodec) {
        return Codec.unboundedMap(UUIDUtil.STRING_CODEC, valueCodec);
    }

    private static <T> Codec<Map<UUID, Map<Identifier, T>>> uuidMapIdCodec(Codec<T> valueCodec) {
        return uuidMapCodec(Codec.unboundedMap(Identifier.CODEC, valueCodec));
    }

    private static Registry<Upgrade> getUpgradeRegistry(Player player) {
        return player.registryAccess().lookupOrThrow(PooSMPRegistries.Keys.UPGRADE);
    }

    private static double trimBalance(final double balance) {
        return PooUtil.roundTwo(balance);
    }

    private static <T, K, V> Map<T, Map<K, V>> fixMaps(Map<T, Map<K, V>> map) {
        if (!map.isEmpty()) {
            Map<T, Map<K, V>> fixedMap = HashMap.newHashMap(map.size());
            for (T t : map.keySet()) {
                fixedMap.put(t, new HashMap<>(map.get(t)));
            }
            return fixedMap;
        } else return HashMap.newHashMap(16);
    }

    private boolean syncFrom(Tag nbtTag) {
        DataResult<PooSMPSavedData> decoded = PooSMPSavedData.CODEC.parse(NbtOps.INSTANCE, nbtTag);
        decoded.ifSuccess(savedData -> {
            this.balance = new HashMap<>(savedData.balance);
            this.purchasedUpgrades = fixMaps(savedData.purchasedUpgrades);
        });
        return decoded.isSuccess();
    }

    public static final class Client {
        public static final PooSMPSavedData INSTANCE = new PooSMPSavedData();

        public static boolean sync(Tag nbtTag) {
            return Client.INSTANCE.syncFrom(nbtTag);
        }
    }
}
