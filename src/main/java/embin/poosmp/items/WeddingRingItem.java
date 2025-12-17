package embin.poosmp.items;

import embin.poosmp.items.component.PooSMPItemComponents;
import embin.poosmp.util.Id;
import java.util.Arrays;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

public class WeddingRingItem extends CreativeSnitchItem {
    public static String[] married_users = {"Embin", "ddededodediamant", "_thecubic_", "schleop"};
    public WeddingRingItem(Properties settings) {
        super(settings);
    }

    public static ItemAttributeModifiers weddingRingAttributes() {
        return ItemAttributeModifiers.builder().add(
            EntityAttributes.GENERIC_MAX_HEALTH,
            new AttributeModifier(
                Id.of("poosmp:wedding_ring_hp_buff"), 10, AttributeModifier.Operation.ADD_VALUE
            ),
            EquipmentSlotGroup.OFFHAND
        ).add(
            EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
            new AttributeModifier(
                Id.of("poosmp:wedding_ring_entity_reach_buff"), 1.0F, AttributeModifier.Operation.ADD_VALUE
            ),
            EquipmentSlotGroup.OFFHAND
        ).add(
            EntityAttributes.GENERIC_ATTACK_DAMAGE,
            new AttributeModifier(
                Id.of("poosmp:wedding_ring_attack_damage"), 8, AttributeModifier.Operation.ADD_VALUE
            ),
            EquipmentSlotGroup.HAND
        ).add(
            EntityAttributes.GENERIC_ARMOR_TOUGHNESS,
            new AttributeModifier(
                Id.of("poosmp:wedding_ring_armor_toughness"), 6, AttributeModifier.Operation.ADD_VALUE
            ),
            EquipmentSlotGroup.OFFHAND
        ).build();
    }

    public static ItemAttributeModifiers weddingRingPublicAttributes() {
        return ItemAttributeModifiers.builder().add(
            EntityAttributes.GENERIC_ATTACK_DAMAGE,
            new AttributeModifier(
                Id.of("poosmp:wedding_ring_attack_damage"), 2, AttributeModifier.Operation.ADD_VALUE
            ),
            EquipmentSlotGroup.HAND
        ).build();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!world.isClientSide) {
            if (entity.isAlwaysTicking()) {
                if (Arrays.asList(married_users).contains(entity.getName().getString())
                    || entity.getName().getString().startsWith("Player")
                    || stack.has(PooSMPItemComponents.FORCE_MARRIED)
                ) {
                    stack.set(DataComponents.ATTRIBUTE_MODIFIERS, weddingRingAttributes());
                    stack.set(PooSMPItemComponents.MARRIED, true);
                } else {
                    stack.set(DataComponents.ATTRIBUTE_MODIFIERS, weddingRingPublicAttributes());
                    stack.set(PooSMPItemComponents.MARRIED, false);
                }
            } else {
                stack.set(DataComponents.ATTRIBUTE_MODIFIERS, weddingRingAttributes());
                stack.set(PooSMPItemComponents.MARRIED, true);
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        if (stack.has(PooSMPItemComponents.MARRIED)) {
            if (stack.getOrDefault(PooSMPItemComponents.MARRIED, false)) {
                tooltip.add(Component.translatable("tooltip.poosmp.married.true").withStyle(ChatFormatting.GOLD));
            } else {
                tooltip.add(Component.translatable("tooltip.poosmp.married.false").withStyle(ChatFormatting.RED));
                tooltip.add(Component.translatable("tooltip.poosmp.married.false.line_2").withStyle(ChatFormatting.RED));
            }
        }
    }
}
