package embin.poosmp.items;

import embin.poosmp.PooSMPItemComponents;
import embin.poosmp.PooSMPMod;
import embin.poosmp.util.ConvertNamespace;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Rarity;

public class PooSMPItems {
    private static final ConvertNamespace cn = new ConvertNamespace();
    public static <T extends Item> T register(String path, T item) {
        return Registry.register(Registries.ITEM, cn.convert(path), item);
    }

    public static final Item POOP_STICK = register("poop_stick", new PoopStickItem(new Item.Settings().rarity(Rarity.UNCOMMON).fireproof().maxCount(1)));
    public static final Item SERVER_SAYS_WHAT_STICK = register("server_says_what_stick", new ServerSaysWhatItem(new Item.Settings().rarity(Rarity.RARE).maxCount(1).fireproof()));
    public static final Item BIOME_STICK = register("biome_stick", new BiomeStickItem(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON).component(PooSMPItemComponents.SELECTED_BIOME, "minecraft:plains")));
    public static final Item BOOM_STICK = register("boom_stick", new BoomStickItem(new Item.Settings().maxCount(1).rarity(Rarity.RARE).fireproof()));
    public static final Item ZOMBIE_STICK = register("zombie_stick", new MobStickItem(
        new Item.Settings().maxCount(1).rarity(Rarity.RARE).fireproof(),
        EntityType.ZOMBIE,
        MobStickItem.BuiltInNames.zombie_names, false
    ));
    public static final Item DIAMOND_SHARD = register("diamond_shard", new Item(new Item.Settings()));
    public static final Item WEDDING_RING = register("wedding_ring", new WeddingRingItem(new Item.Settings().rarity(Rarity.RARE).maxCount(1).fireproof()));
    public static final Item RED_NETHER_BRICK = register("red_nether_brick", new Item(new Item.Settings()));
    public static final Item POOP_BRICK = register("poop_brick", new Item(new Item.Settings()));
    public static final Item POOPLET = register("pooplet", new Item(new Item.Settings()));
    public static final Item RING = register("ring", new Item(new Item.Settings()));
    public static final Item TOTEM_OF_HEALTH = totem("health", healthTotemAttributes(4, ""), false);
    public static final Item WARP_STICK = register("warp_stick", new WarpStick(new Item.Settings().maxCount(1).rarity(Rarity.EPIC)));
    public static final Item FILL_ARMOR_TRIM_TEMPLATE = register("fill_armor_trim_smithing_template", SmithingTemplateItem.of(cn.convert("poosmp:fill")));
    public static final Item DISC_TRIFECTA_CAP = musicDisc("trifecta_cap", PooSMPJukeboxSongs.TRIFECTA_CAP, "Embin");
    public static final Item DISC_BUTTERFLIES_AND_HURRICANES_INSTRUMENTAL = musicDisc("butterflies_and_hurricanes_instrumental", PooSMPJukeboxSongs.BUTTERFLIES_AND_HURRICANES_INSTRUMENTAL, "Embin");
    public static final Item DISC_BUDDY_HOLLY = musicDisc("buddy_holly", PooSMPJukeboxSongs.BUDDY_HOLLY, "ianyourgod");
    public static final Item DISC_STEREO_MADNESS = musicDisc("stereo_madness", PooSMPJukeboxSongs.STEREO_MADNESS, "a_pc");
    public static final Item DISC_NOT_LIKE_US = musicDisc("not_like_us", PooSMPJukeboxSongs.NOT_LIKE_US, "a_pc");
    public static final Item DISC_RESISTANCE_INSTRUMENTAL = musicDisc("resistance_instrumental", PooSMPJukeboxSongs.RESISTANCE_INSTRUMENTAL, "Embin");
    public static final Item TOTEM_OF_REACH = totem("reach", reachTotemAttributes(1.0F, ""), false);
    public static final Item BLANK_MUSIC_DISC = register("blank_music_disc", new Item(new Item.Settings().rarity(Rarity.UNCOMMON)));
    public static final Item ENCHANTED_TOTEM_OF_REACH = totem("reach", reachTotemAttributes(2.0F, "_enchanted"), true);
    public static final Item ENCHANTED_TOTEM_OF_HEALTH = totem("health", healthTotemAttributes(6, "_enchanted"), true);
    public static final Item DISC_BLISS_INSTRUMENTAL = musicDisc("bliss_instrumental", PooSMPJukeboxSongs.BLISS_INSTRUMENTAL, "Embin");
    public static final Item DISC_ENDLESSLY_INSTRUMENTAL = musicDisc("endlessly_instrumental", PooSMPJukeboxSongs.ENDLESSLY_INSTRUMENTAL, "Embin");
    public static final Item DISC_ENDLESSLY = musicDisc("endlessly", PooSMPJukeboxSongs.ENDLESSLY, "Embin");
    public static final Item DISC_ENDLESSLY_STEREO = musicDisc("endlessly_stereo", PooSMPJukeboxSongs.ENDLESSLY_STEREO, "Embin");
    public static final Item ZAP_STICK = register("lightning_stick", new ZapStick(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item VILLAGER_STICK = register("villager_stick", new MobStickItem(new Item.Settings().maxCount(1).rarity(Rarity.EPIC), EntityType.VILLAGER, MobStickItem.BuiltInNames.villager_names));
    public static final Item CUBE_POTTERY_SHERD = register("cube_pottery_sherd", new Item(new Item.Settings()));
    public static final Item POO_POTTERY_SHERD = register("poo_pottery_sherd", new Item(new Item.Settings()));

    public static ItemStack getBiomeStickStack(String biome) {
        ItemStack stack = new ItemStack(BIOME_STICK);
        stack.set(PooSMPItemComponents.SELECTED_BIOME, biome);
        return stack;
    }

    private static Item musicDisc(String name, RegistryKey<JukeboxSong> song, String requester) {
        return register("music_disc/" + name, new RequestedDiscItem(requester, new Item.Settings().maxCount(1).rarity(Rarity.RARE).jukeboxPlayable(song)));
    }

    private static Item totem(String name, AttributeModifiersComponent attributes, boolean enchanted) {
        if (enchanted) {
            return register("enchanted_totem_of_" + name, new CreativeSnitchItem(new Item.Settings().attributeModifiers(attributes).rarity(Rarity.RARE).maxCount(1).component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)));
        } else {
            return register("totem_of_" + name, new CreativeSnitchItem(new Item.Settings().attributeModifiers(attributes).rarity(Rarity.UNCOMMON).maxCount(1)));
        }
    }

    public static AttributeModifiersComponent healthTotemAttributes(int hp, String id_suffix) {
        return AttributeModifiersComponent.builder().add(
            EntityAttributes.GENERIC_MAX_HEALTH,
            new EntityAttributeModifier(
                cn.convert("poosmp:health_totem_buff" + id_suffix), hp, EntityAttributeModifier.Operation.ADD_VALUE
            ),
            AttributeModifierSlot.HAND
        ).build();
    }

    public static AttributeModifiersComponent reachTotemAttributes(float amount, String id_suffix) {
        return AttributeModifiersComponent.builder().add(
            EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE,
            new EntityAttributeModifier(
                cn.convert("poosmp:reach_totem_blocks" + id_suffix), amount, EntityAttributeModifier.Operation.ADD_VALUE
            ),
            AttributeModifierSlot.HAND
        ).add(
            EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
            new EntityAttributeModifier(
                cn.convert("poosmp:reach_totem_entities" + id_suffix), amount, EntityAttributeModifier.Operation.ADD_VALUE
            ),
            AttributeModifierSlot.HAND
        ).build();
    }

    public static void init() {
        PooSMPMod.LOGGER.info("Making PooSMP items!!!");
    }
}
