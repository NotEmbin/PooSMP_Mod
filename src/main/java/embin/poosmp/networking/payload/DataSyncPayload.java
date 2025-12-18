package embin.poosmp.networking.payload;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

@SuppressWarnings("NullableProblems")
public record DataSyncPayload(CompoundTag nbt) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, DataSyncPayload> CODEC = CustomPacketPayload.codec(DataSyncPayload::write, DataSyncPayload::new);
    public static final Type<DataSyncPayload> ID = new Type<>(embin.poosmp.util.Id.of("poosmp:upgrade_sync"));

    public DataSyncPayload(FriendlyByteBuf buf) {
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
