package embin.poosmp.upgrade;

import embin.poosmp.PooSMPMod;
import embin.poosmp.PooSMPRegistries;
import embin.poosmp.util.Id;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

import java.util.Optional;

public class Upgrades {
    public static final Upgrade HEALTH_INCREASE = register("health_increase", Items.ENCHANTED_GOLDEN_APPLE, 15, 10);
    public static final Upgrade BLOCK_REACH_DISTANCE = register("block_reach_distance", Items.STONE, PriceObject.of(40, 25), 15);
    public static final Upgrade ENTITY_REACH_DISTANCE = register("entity_reach_distance", Items.ZOMBIE_HEAD, PriceObject.of(50, 40), 15);
    public static final Upgrade FIRE_RESISTANCE = register("fire_resistance", Items.MAGMA_BLOCK, PriceObject.of(50, 40), 15);

    public static Upgrade register(String id, Item icon, int base_price, int price_increase_base) {
        return Registry.register(PooSMPRegistries.UPGRADE, Id.of(id), new Upgrade(
                Registries.ITEM.getEntry(icon),
                PriceObject.of(base_price, price_increase_base),
                true,
                Optional.empty()
        ));
    }

    public static Upgrade register(String id, Item icon, PriceObject price, int max_purchases) {
        return Registry.register(PooSMPRegistries.UPGRADE, Id.of(id), new Upgrade(
                Registries.ITEM.getEntry(icon),
                price,
                true,
                Optional.of(max_purchases)
        ));
    }

    public static void init() {
        PooSMPMod.LOGGER.info("Upgrades maybe????");
    }
}
