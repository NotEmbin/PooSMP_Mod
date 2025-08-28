package embin.poosmp.networking.payload;

import net.minecraft.network.packet.CustomPayload;

public record UpgradeSyncPayload() implements CustomPayload {
    public static final Id<UpgradeSyncPayload> ID = new Id<>(embin.poosmp.util.Id.of("poosmp:upgrade_sync"));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
