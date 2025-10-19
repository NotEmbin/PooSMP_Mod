package embin.poosmp.upgrade;

import embin.poosmp.PooSMPRegistries;
import embin.poosmp.util.Id;
import net.minecraft.registry.RegistryKey;

public final class UpgradeKeys {
    public static final RegistryKey<Upgrade> HEALTH_INCREASE = key("health_increase");

    public static RegistryKey<Upgrade> key(String id) {
        return RegistryKey.of(PooSMPRegistries.Keys.UPGRADE, Id.of(id));
    }
}
