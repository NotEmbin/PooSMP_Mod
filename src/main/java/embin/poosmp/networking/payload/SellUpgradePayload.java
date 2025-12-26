package embin.poosmp.networking.payload;

import embin.poosmp.networking.PooSMPMessages;
import embin.poosmp.util.Id;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SellUpgradePayload(int upgrade) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, SellUpgradePayload> CODEC = CustomPacketPayload.codec(SellUpgradePayload::write, SellUpgradePayload::new);
    public static final Type<SellUpgradePayload> ID = new Type<>(PooSMPMessages.SELL_UPGRADE);

    public SellUpgradePayload(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(upgrade);
    }
}
