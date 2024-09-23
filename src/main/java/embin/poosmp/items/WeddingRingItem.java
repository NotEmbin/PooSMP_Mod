package embin.poosmp.items;

import embin.poosmp.PooSMPItemComponents;
import embin.poosmp.util.ConvertNamespace;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class WeddingRingItem extends CreativeSnitchItem {
    private static final ConvertNamespace cn = new ConvertNamespace();
    public static String[] allowed_users = {"Embin", "lukitagame5"};
    public WeddingRingItem(Settings settings) {
        super(settings);
    }

    public static AttributeModifiersComponent weddingRingAttributes() {
        return AttributeModifiersComponent.builder().add(
            EntityAttributes.GENERIC_MAX_HEALTH,
            new EntityAttributeModifier(
                cn.convert("poosmp:wedding_ring_hp_buff"), 10, EntityAttributeModifier.Operation.ADD_VALUE
            ),
            AttributeModifierSlot.OFFHAND
        ).add(
            EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE,
            new EntityAttributeModifier(
                cn.convert("poosmp:wedding_ring_block_reach_buff"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE
            ),
            AttributeModifierSlot.OFFHAND
        ).add(
            EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
            new EntityAttributeModifier(
                cn.convert("poosmp:wedding_ring_entity_reach_buff"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE
            ),
            AttributeModifierSlot.OFFHAND
        ).add(
            EntityAttributes.GENERIC_ATTACK_DAMAGE,
            new EntityAttributeModifier(
                cn.convert("poosmp:wedding_ring_attack_damage"), 10, EntityAttributeModifier.Operation.ADD_VALUE
            ),
            AttributeModifierSlot.HAND
        ).add(
            EntityAttributes.GENERIC_ARMOR_TOUGHNESS,
            new EntityAttributeModifier(
                cn.convert("poosmp:wedding_ring_armor_toughness"), 4, EntityAttributeModifier.Operation.ADD_VALUE
            ),
            AttributeModifierSlot.OFFHAND
        ).build();
    }

    public static AttributeModifiersComponent weddingRingPublicAttributes() {
        return AttributeModifiersComponent.builder().add(
            EntityAttributes.GENERIC_ATTACK_DAMAGE,
            new EntityAttributeModifier(
                cn.convert("poosmp:wedding_ring_attack_damage"), 2, EntityAttributeModifier.Operation.ADD_VALUE
            ),
            AttributeModifierSlot.HAND
        ).build();
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!world.isClient) {
            if (entity.isPlayer()) {
                if (Arrays.asList(allowed_users).contains(entity.getName().getString()) || entity.getName().getString().startsWith("Player")) {
                    stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, weddingRingAttributes());
                    stack.set(PooSMPItemComponents.MARRIED, true);
                } else {
                    stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, weddingRingPublicAttributes());
                    stack.set(PooSMPItemComponents.MARRIED, false);
                }
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        if (stack.contains(PooSMPItemComponents.MARRIED)) {
            if (stack.getOrDefault(PooSMPItemComponents.MARRIED, false)) {
                tooltip.add(Text.translatable("tooltip.poosmp.married.true").formatted(Formatting.GOLD));
            } else {
                tooltip.add(Text.translatable("tooltip.poosmp.married.false").formatted(Formatting.RED));
                tooltip.add(Text.translatable("tooltip.poosmp.married.false.line_2").formatted(Formatting.RED));
            }
        }
    }
}
