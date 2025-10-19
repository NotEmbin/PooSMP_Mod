package embin.poosmp.upgrade;

import embin.poosmp.PooSMPRegistries;
import embin.poosmp.util.PooUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.*;

//@Environment(EnvType.SERVER) // it's all built on top a house of cards
public class ServerUpgradeData {
    public static final ServerUpgradeData INSTANCE = new ServerUpgradeData();
    public Map<UUID, Map<Identifier, Integer>> purchases = HashMap.newHashMap(16);
    public Map<UUID, Map<Identifier, StatusEffectInstance>> activeEffects = HashMap.newHashMap(16);
    public Map<UUID, Double> balance = HashMap.newHashMap(16);

    private ServerUpgradeData() {
    }

    public Set<Identifier> savedUpgrades(ServerPlayerEntity player) {
        if (!this.purchases.containsKey(player.getUuid())) return Set.of();
        return this.purchases.get(player.getUuid()).keySet();
    }

    public Map<Identifier, Integer> getPurchasesMap(ServerPlayerEntity player) {
        if (!this.purchases.containsKey(player.getUuid())) this.purchases.put(player.getUuid(),HashMap.newHashMap(32));
        return this.purchases.get(player.getUuid());
    }

    public int getPurchasedAmount(ServerPlayerEntity player, Upgrade upgrade) {
        Identifier id = upgrade.getId(player.getServerWorld().getRegistryManager().get(PooSMPRegistries.Keys.UPGRADE));
        return getPurchasesMap(player).getOrDefault(id, 0);
    }

    public int getPurchasedAmount(ServerPlayerEntity player, Identifier upgrade) {
        return getPurchasesMap(player).getOrDefault(upgrade, 0);
    }

    public void setPurchasedAmount(ServerPlayerEntity player, Upgrade upgrade, int amount) {
        Identifier id = upgrade.getId(player.getServerWorld().getRegistryManager().get(PooSMPRegistries.Keys.UPGRADE));
        Map<Identifier, Integer> purchaseMap = getPurchasesMap(player);
        purchaseMap.put(id, amount);
        this.purchases.put(player.getUuid(), purchaseMap);
    }

    public void setPurchasedAmount(ServerPlayerEntity player, Identifier upgrade, int amount) {
        Map<Identifier, Integer> purchaseMap = getPurchasesMap(player);
        purchaseMap.put(upgrade, amount);
        this.purchases.put(player.getUuid(), purchaseMap);
    }

    public void addStatusEffect(ServerPlayerEntity player, Identifier upgrade, StatusEffectInstance instance) {
        if (!this.activeEffects.containsKey(player.getUuid())) this.activeEffects.put(player.getUuid(), HashMap.newHashMap(32));
        this.activeEffects.get(player.getUuid()).put(upgrade, instance);
    }

    public void setBalance(ServerPlayerEntity player, double newAmount) {
        this.balance.put(player.getUuid(), newAmount);
    }

    public void addBalance(ServerPlayerEntity player, double addAmount) {
        if (!this.balance.containsKey(player.getUuid())) this.balance.put(player.getUuid(), 0.0);
        this.balance.replace(player.getUuid(), PooUtil.roundTwo(this.balance.get(player.getUuid()) + addAmount));
    }

    public double getBalance(ServerPlayerEntity player) {
        if (!this.balance.containsKey(player.getUuid())) this.balance.put(player.getUuid(), 0.0);
        return this.balance.get(player.getUuid());
    }
}
