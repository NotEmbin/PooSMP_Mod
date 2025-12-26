package embin.poosmp.world;

import embin.poosmp.PooSMPMod;
import embin.poosmp.block.annoyance.Annoyance;
import embin.poosmp.economy.shop.ShopCategory;
import embin.poosmp.upgrade.Upgrade;
import embin.poosmp.util.Id;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class PooSMPRegistries {
    //public static final Registry<Upgrade> UPGRADE = FabricRegistryBuilder.createSimple(Keys.UPGRADE).buildAndRegister();
    public static final Registry<Annoyance> ANNOYANCE = FabricRegistryBuilder.createSimple(Keys.ANNOYANCE).buildAndRegister();
    public static final Registry<ShopCategory> SHOP_CATEGORY = FabricRegistryBuilder.createSimple(Keys.SHOP_CATEGORY).buildAndRegister();

    public static class Keys {
        public static final ResourceKey<Registry<Upgrade>> UPGRADE = ResourceKey.createRegistryKey(Id.of("upgrade"));
        public static final ResourceKey<Registry<Annoyance>> ANNOYANCE = ResourceKey.createRegistryKey(Id.of("annoyance"));
        public static final ResourceKey<Registry<ShopCategory>> SHOP_CATEGORY = ResourceKey.createRegistryKey(Id.of("shop_category"));
    }

    public static void acknowledge() {
        PooSMPMod.LOGGER.info("Creating PooSMP registries");
    }
}
