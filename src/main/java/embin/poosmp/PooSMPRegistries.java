package embin.poosmp;

import embin.poosmp.block.annoyance.Annoyance;
import embin.poosmp.economy.shop.ShopCategory;
import embin.poosmp.upgrade.Upgrade;
import embin.poosmp.util.Id;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class PooSMPRegistries {
    //public static final Registry<Upgrade> UPGRADE = FabricRegistryBuilder.createSimple(Keys.UPGRADE).buildAndRegister();
    public static final Registry<Annoyance> ANNOYANCE = FabricRegistryBuilder.createSimple(Keys.ANNOYANCE).buildAndRegister();
    public static final Registry<ShopCategory> SHOP_CATEGORY = FabricRegistryBuilder.createSimple(Keys.SHOP_CATEGORY).buildAndRegister();

    public static class Keys {
        public static final RegistryKey<Registry<Upgrade>> UPGRADE = RegistryKey.ofRegistry(Id.of("upgrade"));
        public static final RegistryKey<Registry<Annoyance>> ANNOYANCE = RegistryKey.ofRegistry(Id.of("annoyance"));
        public static final RegistryKey<Registry<ShopCategory>> SHOP_CATEGORY = RegistryKey.ofRegistry(Id.of("shop_category"));
    }

    public static void acknowledge() {
        PooSMPMod.LOGGER.info("Creating PooSMP registries");
    }
}
