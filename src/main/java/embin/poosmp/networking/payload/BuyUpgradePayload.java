package embin.poosmp.networking.payload;

import embin.poosmp.networking.PooSMPMessages;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record BuyUpgradePayload(int upgrade) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, BuyUpgradePayload> CODEC = CustomPacketPayload.codec(BuyUpgradePayload::write, BuyUpgradePayload::new);
    public static final Type<BuyUpgradePayload> ID = new Type<>(PooSMPMessages.BUY_UPGRADE);

    public BuyUpgradePayload(FriendlyByteBuf buf) {
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
