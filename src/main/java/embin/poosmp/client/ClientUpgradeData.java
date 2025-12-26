package embin.poosmp.client;

import embin.poosmp.upgrade.Upgrade;
import embin.poosmp.util.Id;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
@Deprecated
public class ClientUpgradeData {
    public static final ClientUpgradeData INSTANCE = new ClientUpgradeData();
    public Map<Identifier, Integer> purchases;
    public double balance;

    private ClientUpgradeData() {
        this.purchases = HashMap.newHashMap(32);
        this.balance = 0;
    }

    public int getPurchasedAmount(Upgrade upgrade, Registry<Upgrade> upgradeRegistry) {
        Identifier id = upgrade.getId(upgradeRegistry);
        return this.purchases.getOrDefault(id, 0);
    }

    public void setPurchasedAmount(Upgrade upgrade, int amount, Registry<Upgrade> upgradeRegistry) {
        Identifier id = upgrade.getId(upgradeRegistry);
        this.purchases.put(id, amount);
    }

    public void setPurchasedAmount(Identifier upgrade, int amount) {
        this.purchases.put(upgrade, amount);
    }

    public void setBalance(double newAmount) {
        this.balance = newAmount;
    }

    public double getBalance() {
        return this.balance;
    }

    public void sync(FriendlyByteBuf buf) {
        CompoundTag data = buf.readNbt();
        if (data != null) {
            for (String key : data.keySet()) {
                this.purchases.put(Id.of(key), data.getIntOr(key, 0));
            }
        }
    }
}
