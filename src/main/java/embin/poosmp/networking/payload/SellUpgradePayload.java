package embin.poosmp.networking.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SellUpgradePayload(int upgrade) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, SellUpgradePayload> CODEC = CustomPacketPayload.codec(SellUpgradePayload::write, SellUpgradePayload::new);
    public static final Type<SellUpgradePayload> ID = new Type<>(embin.poosmp.util.Id.of("poosmp:sell_upgrade"));

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
