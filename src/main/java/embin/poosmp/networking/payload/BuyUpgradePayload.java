package embin.poosmp.networking.payload;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record BuyUpgradePayload(Identifier upgrade) implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, BuyUpgradePayload> CODEC = CustomPayload.codecOf(BuyUpgradePayload::write, BuyUpgradePayload::new);
    public static final Id<BuyUpgradePayload> ID = new Id<>(embin.poosmp.util.Id.of("poosmp:buy_upgrade"));

    public BuyUpgradePayload(PacketByteBuf buf) {
        this(buf.readIdentifier());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    private void write(PacketByteBuf buf) {
        buf.writeIdentifier(upgrade);
    }
}
