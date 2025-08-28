package embin.poosmp.networking.payload;

import net.minecraft.network.packet.CustomPayload;

@Deprecated
public record MoneySyncPayload() implements CustomPayload {
    public static final CustomPayload.Id<MoneySyncPayload> ID = new CustomPayload.Id<>(embin.poosmp.util.Id.of("poosmp:money_sync"));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
