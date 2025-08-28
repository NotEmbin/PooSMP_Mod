package embin.poosmp;

import embin.poosmp.upgrade.Upgrade;
import embin.poosmp.util.Id;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class PooSMPRegistries {
    public static final Registry<Upgrade> UPGRADE = FabricRegistryBuilder.createSimple(Keys.UPGRADE).buildAndRegister();

    public static class Keys {
        public static final RegistryKey<Registry<Upgrade>> UPGRADE = RegistryKey.ofRegistry(Id.of("upgrade"));
    }

    public static void acknowledge() {
        PooSMPMod.LOGGER.info("Creating \"poosmp:upgrade\" registry");
    }
}
