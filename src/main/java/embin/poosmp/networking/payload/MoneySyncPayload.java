package embin.poosmp.networking.payload;

import com.mojang.serialization.Codec;
import embin.poosmp.util.ConvertNamespace;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

@Deprecated
public record MoneySyncPayload() implements CustomPayload {
    public static final CustomPayload.Id<MoneySyncPayload> ID = new CustomPayload.Id<>(ConvertNamespace.convert("poosmp:money_sync"));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
