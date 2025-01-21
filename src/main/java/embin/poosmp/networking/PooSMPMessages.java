package embin.poosmp.networking;

import embin.poosmp.networking.packet.MoneyDataSyncS2CPacket;
import embin.poosmp.networking.payload.MoneySyncPayload;
import embin.poosmp.util.ConvertNamespace;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

@Deprecated
public class PooSMPMessages {

    public static void register() {
        //PayloadTypeRegistry.playS2C().register(MoneySyncPayload.ID, MoneySyncPayload.CODEC);
    }


    public static void registerC2SPackets() {

    }

    public static void registerS2CPackets() {
        /*

        ClientPlayNetworking.registerGlobalReceiver(MoneySyncPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
            });
        });

         */
    }
}
