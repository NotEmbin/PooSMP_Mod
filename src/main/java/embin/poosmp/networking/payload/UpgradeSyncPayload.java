package embin.poosmp.networking.payload;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record UpgradeSyncPayload(NbtCompound nbt) implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, UpgradeSyncPayload> CODEC = CustomPayload.codecOf(UpgradeSyncPayload::write, UpgradeSyncPayload::new);
    public static final Id<UpgradeSyncPayload> ID = new Id<>(embin.poosmp.util.Id.of("poosmp:upgrade_sync"));

    public UpgradeSyncPayload(PacketByteBuf buf) {
        this(buf.readNbt());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    private void write(PacketByteBuf buf) {
        buf.writeNbt(nbt);
    }
}
