package embin.poosmp.upgrade;

import embin.poosmp.PooSMPMod;
import embin.poosmp.PooSMPRegistries;
import embin.poosmp.util.Id;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.List;
import java.util.Optional;

public class Upgrades {
    public static final Upgrade HEALTH_INCREASE = register("health_increase", Items.ENCHANTED_GOLDEN_APPLE, 50, 15,
            AttributeModifiersComponent.builder().add(
                    EntityAttributes.GENERIC_MAX_HEALTH,
                    new EntityAttributeModifier(Id.of("upgrade/health_increase"), 2.0D, EntityAttributeModifier.Operation.ADD_VALUE),
                    AttributeModifierSlot.ANY
            ).build().modifiers()
    );
    public static final Upgrade BLOCK_REACH_DISTANCE = register("block_reach_distance", Items.STONE, PriceObject.of(60, 10), 10,
            AttributeModifiersComponent.builder().add(
                    EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE,
                    new EntityAttributeModifier(Id.of("upgrade/block_reach_distance"), 1.0D, EntityAttributeModifier.Operation.ADD_VALUE),
                    AttributeModifierSlot.ANY
            ).build().modifiers()
    );
    public static final Upgrade ENTITY_REACH_DISTANCE = register(
            "entity_reach_distance", Items.ZOMBIE_HEAD, PriceObject.of(60, 20), 10,
            AttributeModifiersComponent.builder().add(
                    EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                    new EntityAttributeModifier(Id.of("upgrade/entity_reach_distance"), 1.0D, EntityAttributeModifier.Operation.ADD_VALUE),
                    AttributeModifierSlot.ANY
            ).build().modifiers()
    );
    public static final Upgrade FIRE_RESISTANCE = register(
            "fire_resistance", Items.MAGMA_BLOCK, PriceObject.of(130),
            new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, -1, 0, true, false, true)
    );
    public static final Upgrade ATTACK_DAMAGE_INCREASE = register("attack_damage_increase", Items.DIAMOND_SWORD, PriceObject.of(50, 25), 15,
            AttributeModifiersComponent.builder().add(
                    EntityAttributes.GENERIC_ATTACK_DAMAGE,
                    new EntityAttributeModifier(Id.of("upgrade/attack_damage_increase"), 1.0D, EntityAttributeModifier.Operation.ADD_VALUE),
                    AttributeModifierSlot.ANY
            ).build().modifiers()
    );
    public static final Upgrade WATER_BREATHING = register(
            "water_breathing", Items.PUFFERFISH_BUCKET, PriceObject.of(120),
            new StatusEffectInstance(StatusEffects.WATER_BREATHING, -1, 0, true, false, true)
    );
    public static final Upgrade TRIAL_OMEN = register(
            "trial_omen", Items.OMINOUS_TRIAL_KEY, PriceObject.of(80),
            new StatusEffectInstance(StatusEffects.TRIAL_OMEN, -1, 0, true, false, true)
    );

    public static Upgrade register(String id, Item icon, int base_price, int price_increase_base) {
        return Registry.register(PooSMPRegistries.UPGRADE, Id.of(id), new Upgrade(
                Registries.ITEM.getEntry(icon),
                PriceObject.of(base_price, price_increase_base),
                true,
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        ));
    }

    public static Upgrade register(String id, Item icon, int base_price, int price_increase_base, List<AttributeModifiersComponent.Entry> attributes) {
        return Registry.register(PooSMPRegistries.UPGRADE, Id.of(id), new Upgrade(
                Registries.ITEM.getEntry(icon),
                PriceObject.of(base_price, price_increase_base),
                true,
                Optional.empty(),
                Optional.of(attributes),
                Optional.empty()
        ));
    }

    public static Upgrade register(String id, Item icon, PriceObject price, int max_purchases) {
        return Registry.register(PooSMPRegistries.UPGRADE, Id.of(id), new Upgrade(
                Registries.ITEM.getEntry(icon),
                price,
                true,
                Optional.of(max_purchases),
                Optional.empty(),
                Optional.empty()
        ));
    }

    public static Upgrade register(String id, Item icon, PriceObject price, int max_purchases, List<AttributeModifiersComponent.Entry> attributes) {
        return Registry.register(PooSMPRegistries.UPGRADE, Id.of(id), new Upgrade(
                Registries.ITEM.getEntry(icon),
                price,
                true,
                Optional.of(max_purchases),
                Optional.of(attributes),
                Optional.empty()
        ));
    }

    public static Upgrade register(String id, Item icon, PriceObject price, StatusEffectInstance statusEffect) {
        return Registry.register(PooSMPRegistries.UPGRADE, Id.of(id), new Upgrade(
                Registries.ITEM.getEntry(icon),
                price,
                true,
                Optional.of(1),
                Optional.empty(),
                Optional.of(statusEffect)
        ));
    }

    public static void init() {
        PooSMPMod.LOGGER.info("Upgrading your MOM!");

        for (int i = 1; i <= 64; i++) {
            Registry.register(PooSMPRegistries.UPGRADE, Id.of("dummy_" + i), new Upgrade(
                    Registries.ITEM.getEntry(Registries.ITEM.get(i)),
                    PriceObject.of(1),
                    true,
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty()
            ));
        }
    }
}
