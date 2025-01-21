package embin.poosmp.networking.packet;

import embin.poosmp.util.IEntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

@Deprecated
public class MoneyDataSyncS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        ((IEntityDataSaver) client.player).getPersistentData().putInt("money", buf.readInt());
    }
}
