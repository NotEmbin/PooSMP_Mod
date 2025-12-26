package embin.poosmp.networking.payload;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

@Deprecated
public record MoneySyncPayload() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MoneySyncPayload> ID = new CustomPacketPayload.Type<>(embin.poosmp.util.Id.of("poosmp:money_sync"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
