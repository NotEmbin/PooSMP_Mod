package embin.poosmp.client;

import embin.poosmp.PooSMPRegistries;
import embin.poosmp.upgrade.Upgrade;
import embin.poosmp.util.Id;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class ClientUpgradeData {
    public static final ClientUpgradeData INSTANCE = new ClientUpgradeData();
    public Map<Identifier, Integer> purchases;

    private ClientUpgradeData() {
        this.purchases = HashMap.newHashMap(32);
    }

    public int getPurchasedAmount(Upgrade upgrade) {
        Identifier id = PooSMPRegistries.UPGRADE.getId(upgrade);
        return this.purchases.getOrDefault(id, 0);
    }

    public void setPurchasedAmount(Upgrade upgrade, int amount) {
        Identifier id = PooSMPRegistries.UPGRADE.getId(upgrade);
        this.purchases.put(id, amount);
    }

    public void sync(PacketByteBuf buf) {
        NbtCompound data = buf.readNbt();
        if (data != null) {
            for (String key : data.getKeys()) {
                this.purchases.put(Id.of(key), data.getInt(key));
            }
        }
    }
}
