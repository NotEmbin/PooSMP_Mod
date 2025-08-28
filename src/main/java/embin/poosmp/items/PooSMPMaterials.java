package embin.poosmp.items;

import embin.poosmp.util.Id;
import embin.poosmp.util.PooSMPTags;
import net.minecraft.block.Block;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class PooSMPMaterials {
    public static final ToolMaterial RED_POO = new ToolMaterial() {
        @Override
        public int getDurability() {
            return 4096;
        }

        @Override
        public float getMiningSpeedMultiplier() {
            return 10.0F;
        }

        @Override
        public float getAttackDamage() {
            return 5.0F;
        }

        @Override
        public TagKey<Block> getInverseTag() {
            return PooSMPTags.Blocks.INCORRECT_FOR_RED_POO_TOOLS;
        }

        @Override
        public int getEnchantability() {
            return 25;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(PooSMPItems.RED_POO_INGOT);
        }
    };

    public static RegistryEntry<ArmorMaterial> register(String name, Supplier<ArmorMaterial> material) {
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Id.of(name), material.get());
    }

    public static final RegistryEntry<ArmorMaterial> A_RED_POO = register("red_poo",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 4);
                map.put(ArmorItem.Type.LEGGINGS, 7);
                map.put(ArmorItem.Type.CHESTPLATE, 9);
                map.put(ArmorItem.Type.HELMET, 4);
                map.put(ArmorItem.Type.BODY, 16);
            }), 25, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, () -> Ingredient.ofItems(PooSMPItems.RED_POO_INGOT),
                List.of(new ArmorMaterial.Layer(Id.of("red_poo"))), 4.5f, 0.1f
            )
    );
}
