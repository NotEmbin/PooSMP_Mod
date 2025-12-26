package embin.poosmp.upgrade;

import embin.poosmp.world.PooSMPRegistries;
import embin.poosmp.util.PooUtil;
import java.util.*;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

//@Environment(EnvType.SERVER) // it's all built on top a house of cards
@Deprecated
public class ServerUpgradeData {
    public static final ServerUpgradeData INSTANCE = new ServerUpgradeData();
    public Map<UUID, Map<Identifier, Integer>> purchases = HashMap.newHashMap(16);
    public Map<UUID, Map<Identifier, MobEffectInstance>> activeEffects = HashMap.newHashMap(16);
    public Map<UUID, Double> balance = HashMap.newHashMap(16);

    public Set<Identifier> savedUpgrades(ServerPlayer player) {
        if (!this.purchases.containsKey(player.getUUID())) return Set.of();
        return this.purchases.get(player.getUUID()).keySet();
    }

    public Map<Identifier, Integer> getPurchasesMap(ServerPlayer player) {
        if (!this.purchases.containsKey(player.getUUID())) this.purchases.put(player.getUUID(),HashMap.newHashMap(32));
        return this.purchases.get(player.getUUID());
    }

    public int getPurchasedAmount(ServerPlayer player, Upgrade upgrade) {
        Identifier id = upgrade.getId(player.registryAccess().lookupOrThrow(PooSMPRegistries.Keys.UPGRADE));
        return getPurchasesMap(player).getOrDefault(id, 0);
    }

    public int getPurchasedAmount(ServerPlayer player, Identifier upgrade) {
        return getPurchasesMap(player).getOrDefault(upgrade, 0);
    }

    public void setPurchasedAmount(ServerPlayer player, Upgrade upgrade, int amount) {
        Identifier id = upgrade.getId(player.registryAccess().lookupOrThrow(PooSMPRegistries.Keys.UPGRADE));
        Map<Identifier, Integer> purchaseMap = getPurchasesMap(player);
        purchaseMap.put(id, amount);
        this.purchases.put(player.getUUID(), purchaseMap);
    }

    public void setPurchasedAmount(ServerPlayer player, Identifier upgrade, int amount) {
        Map<Identifier, Integer> purchaseMap = getPurchasesMap(player);
        purchaseMap.put(upgrade, amount);
        this.purchases.put(player.getUUID(), purchaseMap);
    }

    public void addStatusEffect(ServerPlayer player, Identifier upgrade, MobEffectInstance instance) {
        if (!this.activeEffects.containsKey(player.getUUID())) this.activeEffects.put(player.getUUID(), HashMap.newHashMap(32));
        this.activeEffects.get(player.getUUID()).put(upgrade, instance);
    }

    public void setBalance(ServerPlayer player, double newAmount) {
        this.balance.put(player.getUUID(), newAmount);
    }

    public void addBalance(ServerPlayer player, double addAmount) {
        if (!this.balance.containsKey(player.getUUID())) this.balance.put(player.getUUID(), 0.0);
        this.balance.replace(player.getUUID(), PooUtil.roundTwo(this.balance.get(player.getUUID()) + addAmount));
    }

    public double getBalance(ServerPlayer player) {
        if (!this.balance.containsKey(player.getUUID())) this.balance.put(player.getUUID(), 0.0);
        return this.balance.get(player.getUUID());
    }
}
