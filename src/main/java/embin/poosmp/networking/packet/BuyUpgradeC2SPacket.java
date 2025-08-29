package embin.poosmp.networking.packet;

import embin.poosmp.PooSMPRegistries;
import embin.poosmp.upgrade.Upgrade;
import embin.poosmp.util.IEntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class BuyUpgradeC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender packetSender) {
        ServerWorld world = player.getServerWorld();
        Upgrade upgrade = server.getRegistryManager().get(PooSMPRegistries.Keys.UPGRADE).get(Identifier.of(buf.readString()));
        if (upgrade != null) {
            //upgrade.buyUpgrade((IEntityDataSaver) player);
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS);
        }
    }
}
