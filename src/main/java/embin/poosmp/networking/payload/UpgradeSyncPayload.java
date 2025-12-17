package embin.poosmp.networking.payload;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record UpgradeSyncPayload(CompoundTag nbt) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, UpgradeSyncPayload> CODEC = CustomPacketPayload.codec(UpgradeSyncPayload::write, UpgradeSyncPayload::new);
    public static final Type<UpgradeSyncPayload> ID = new Type<>(embin.poosmp.util.Id.of("poosmp:upgrade_sync"));

    public UpgradeSyncPayload(FriendlyByteBuf buf) {
        this(buf.readNbt());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeNbt(nbt);
    }
}
