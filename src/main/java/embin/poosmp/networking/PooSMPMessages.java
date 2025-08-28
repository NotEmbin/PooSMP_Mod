package embin.poosmp.networking;

import embin.poosmp.networking.payload.MoneySyncPayload;
import embin.poosmp.networking.payload.UpgradeSyncPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class PooSMPMessages {

    public static void register() {
        //PayloadTypeRegistry.playS2C().register(MoneySyncPayload.ID, MoneySyncPayload.CODEC);
        //PayloadTypeRegistry.playS2C().register(UpgradeSyncPayload.ID, UpgradeSyncPayload.CODEC);
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
