package embin.poosmp.upgrade;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embin.poosmp.PooSMPRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import org.slf4j.Logger;

import java.util.*;

@SuppressWarnings({"NullableProblems", "DataFlowIssue"})
public class PooSMPSavedData extends SavedData {
    public static final Codec<PooSMPSavedData> CODEC = RecordCodecBuilder.create(u -> u.group(
        uuidMapCodec(Codec.DOUBLE).fieldOf("player_balance").forGetter(z -> z.balance),
        uuidMapIdCodec(Codec.INT).fieldOf("purchased_upgrades").forGetter(z -> z.purchasedUpgrades),
        uuidMapIdCodec(MobEffectInstance.CODEC).fieldOf("active_effects").forGetter(z -> z.activeEffects)
    ).apply(u, PooSMPSavedData::new));
    public static final SavedDataType<PooSMPSavedData> TYPE = new SavedDataType<>("poosmp", PooSMPSavedData::new, PooSMPSavedData.CODEC, null);
    private final Map<UUID, Map<Identifier, Integer>> purchasedUpgrades;
    private final Map<UUID, Map<Identifier, MobEffectInstance>> activeEffects;
    private final Map<UUID, Double> balance;
    private final Logger logger = LogUtils.getLogger();

    public PooSMPSavedData(
            Map<UUID, Double> balance,
            Map<UUID, Map<Identifier, Integer>> purchases,
            Map<UUID, Map<Identifier, MobEffectInstance>> activeEffects
    ) {
        this.balance = balance;
        this.purchasedUpgrades = purchases;
        this.activeEffects = activeEffects;
    }

    public PooSMPSavedData() {
        this.balance = HashMap.newHashMap(16);
        this.purchasedUpgrades = HashMap.newHashMap(16);
        this.activeEffects = HashMap.newHashMap(16);
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
        this.getPurchasedUpgradesIdMap(player).put(upgradeId, amountPurchased + 1);
        this.setDirty();
    }

    public void sellUpgrade(Player player, Upgrade upgrade) {
        Identifier upgradeId = upgrade.getId(getUpgradeRegistry(player));
        final int amountPurchased = this.getPurchasedUpgradesIdMap(player).getOrDefault(upgradeId, 0);
        if (amountPurchased > 0) {
            this.getPurchasedUpgradesIdMap(player).put(upgradeId, amountPurchased + 1);
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

    public boolean addBalance(Player player, double amount) {
        double currentBalance = this.getBalance(player);
        final double newAmount = Math.clamp(currentBalance + amount, 0.0D, Double.MAX_VALUE);
        this.balance.put(player.getUUID(), newAmount);
        this.setDirty();
        return newAmount == (currentBalance + amount);
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
}
