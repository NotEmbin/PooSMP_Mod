package embin.poosmp.upgrade;

import embin.poosmp.PooSMPRegistries;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

//@Environment(EnvType.SERVER)
public class ServerUpgradeData {
    public static final ServerUpgradeData INSTANCE = new ServerUpgradeData();
    public Map<Identifier, Integer> purchases;
    public Map<Identifier, StatusEffectInstance> activeEffects;
    public int balance;

    private ServerUpgradeData() {
        this.purchases = HashMap.newHashMap(32);
        this.activeEffects = HashMap.newHashMap(32);
        this.balance = 0;
    }

    public Set<Identifier> savedUpgrades() {
        return this.purchases.keySet();
    }

    public int getPurchasedAmount(Upgrade upgrade) {
        Identifier id = PooSMPRegistries.UPGRADE.getId(upgrade);
        return this.purchases.getOrDefault(id, 0);
    }

    public int getPurchasedAmount(Identifier upgrade) {
        return this.purchases.getOrDefault(upgrade, 0);
    }

    public void setPurchasedAmount(Upgrade upgrade, int amount) {
        Identifier id = PooSMPRegistries.UPGRADE.getId(upgrade);
        this.purchases.put(id, amount);
    }

    public void setPurchasedAmount(Identifier upgrade, int amount) {
        this.purchases.put(upgrade, amount);
    }

    public void addStatusEffect(Identifier upgrade, StatusEffectInstance instance) {
        this.activeEffects.put(upgrade, instance);
    }

    @Nullable
    public StatusEffectInstance getStatusEffect(Identifier upgrade) {
        if (this.activeEffects.containsKey(upgrade)) {
            return this.activeEffects.get(upgrade);
        }
        return null;
    }

    public void setBalance(int newAmount) {
        this.balance = newAmount;
    }

    public int getBalance() {
        return this.balance;
    }
}
