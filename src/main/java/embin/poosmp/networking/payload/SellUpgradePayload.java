package embin.poosmp.networking.payload;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SellUpgradePayload(Identifier upgrade) implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, SellUpgradePayload> CODEC = CustomPayload.codecOf(SellUpgradePayload::write, SellUpgradePayload::new);
    public static final Id<SellUpgradePayload> ID = new Id<>(embin.poosmp.util.Id.of("poosmp:sell_upgrade"));

    public SellUpgradePayload(PacketByteBuf buf) {
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
