package embin.poosmp.items;

import com.google.common.collect.Maps;
import embin.poosmp.util.Id;
import embin.poosmp.util.PooSMPTags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

@SuppressWarnings({"SameParameterValue", "NullableProblems"})
public class PooSMPMaterials {
    public static final ToolMaterial RED_POO = new ToolMaterial(
            PooSMPTags.Blocks.INCORRECT_FOR_RED_POO_TOOLS,
            4096, // durability
            10.0F, // speed (yarn: miningSpeedMultiplier)
            5.0f, // attack damage
            25, // enchantability
            PooSMPTags.Items.RED_POO_REPAIR_ITEMS
    );

    public static final ResourceKey<EquipmentAsset> RED_POO_ASSET = ResourceKey.create(EquipmentAssets.ROOT_ID, Id.of("red_poo"));
    public static final ArmorMaterial A_RED_POO = new ArmorMaterial(
            40, makeDefense(4, 7, 8, 4, 16),
            25, SoundEvents.ARMOR_EQUIP_DIAMOND, 4.5f, 0.1f,
            PooSMPTags.Items.RED_POO_REPAIR_ITEMS, RED_POO_ASSET
    );

    private static Map<ArmorType, Integer> makeDefense(int boots, int leggings, int chestplate, int helmet, int body) {
        return Maps.newEnumMap(Map.of(
                ArmorType.BOOTS, boots,
                ArmorType.LEGGINGS, leggings,
                ArmorType.CHESTPLATE, chestplate,
                ArmorType.HELMET, helmet,
                ArmorType.BODY, body
        ));
    }
}
