package embin.poosmp.items;

import embin.poosmp.PooSMPItemComponents;
import embin.poosmp.PooSMPMod;
import embin.poosmp.util.ConvertNamespace;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;
import net.minecraft.util.Unit;

public class PooSMPItems {
    private static final ConvertNamespace cn = new ConvertNamespace();
    public static <T extends Item> T register(String path, T item) {
        return Registry.register(Registries.ITEM, cn.convert(path), item);
    }

    public static final Item POOP_STICK = register("poop_stick", new PoopStickItem(new Item.Settings().rarity(Rarity.EPIC).fireproof().maxCount(1)));
    public static final Item SERVER_SAYS_WHAT_STICK = register("server_says_what_stick", new ServerSaysWhatItem(new Item.Settings().rarity(Rarity.EPIC).maxCount(1).fireproof()));
    public static final Item BIOME_STICK = register("biome_stick", new BiomeStickItem(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON).component(PooSMPItemComponents.SELECTED_BIOME, "minecraft:plains")));
    public static final Item BOOM_STICK = register("boom_stick", new BoomStickItem(new Item.Settings().maxCount(1).rarity(Rarity.EPIC).fireproof()));
    public static final Item ZOMBIE_STICK = register("zombie_stick", new ZombieStickItem(new Item.Settings().maxCount(1).rarity(Rarity.EPIC)));
    public static final Item DIAMOND_SHARD = register("diamond_shard", new Item(new Item.Settings()));
    public static final Item WEDDING_RING = register("wedding_ring", new WeddingRingItem(new Item.Settings().rarity(Rarity.RARE).maxCount(1).fireproof()));
    public static final Item RED_NETHER_BRICK = register("red_nether_brick", new Item(new Item.Settings()));
    public static final Item POOP_BRICK = register("poop_brick", new Item(new Item.Settings()));
    public static final Item POOPLET = register("pooplet", new Item(new Item.Settings()));
    public static final Item RING = register("ring", new Item(new Item.Settings()));
    public static final Item TOTEM_OF_HEALTH = register("totem_of_health", new CreativeSnitchItem(new Item.Settings().attributeModifiers(healthTotemAttributes()).maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item WARP_STICK = register("warp_stick", new WarpStick(new Item.Settings().maxCount(1).rarity(Rarity.EPIC)));
    public static final Item FILL_ARMOR_TRIM_TEMPLATE = register("fill_armor_trim_smithing_template", SmithingTemplateItem.of(cn.convert("poosmp:fill")));

    public static ItemStack getBiomeStickStack(String biome) {
        ItemStack stack = new ItemStack(BIOME_STICK);
        stack.set(PooSMPItemComponents.SELECTED_BIOME, biome);
        return stack;
    }

    public static AttributeModifiersComponent healthTotemAttributes() {
        return AttributeModifiersComponent.builder().add(
            EntityAttributes.GENERIC_MAX_HEALTH,
            new EntityAttributeModifier(
                cn.convert("poosmp:health_totem_buff"), 4, EntityAttributeModifier.Operation.ADD_VALUE
            ),
            AttributeModifierSlot.HAND
        ).build();
    }

    public static void init() {
        PooSMPMod.LOGGER.info("Making PooSMP items!!!");
    }
}
