package embin.poosmp.upgrade;

import embin.poosmp.PooSMPRegistries;
import embin.poosmp.util.Id;
import net.minecraft.resources.ResourceKey;

public final class UpgradeKeys {
    public static final ResourceKey<Upgrade> HEALTH_INCREASE = key("health_increase");

    public static ResourceKey<Upgrade> key(String id) {
        return ResourceKey.create(PooSMPRegistries.Keys.UPGRADE, Id.of(id));
    }
}
