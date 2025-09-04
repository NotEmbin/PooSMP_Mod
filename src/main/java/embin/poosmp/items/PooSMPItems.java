package embin.poosmp.items;

import embin.poosmp.PooSMPItemComponents;
import embin.poosmp.PooSMPMod;
import embin.poosmp.util.Id;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Rarity;

public class PooSMPItems {
    public static <T extends Item> T register(String path, T item) {
        return Registry.register(Registries.ITEM, Id.of(path), item);
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
    public static final Item DIAMOND_SHARD = register("diamond_shard");
    public static final Item WEDDING_RING = register("wedding_ring", new WeddingRingItem(new Item.Settings().rarity(Rarity.RARE).maxCount(1).fireproof()));
    public static final Item RED_NETHER_BRICK = register("red_nether_brick");
    public static final Item POOP_BRICK = register("poop_brick");
    public static final Item POOPLET = register("pooplet", new Item(new Item.Settings().food(PooSMPFoods.POOPLET)));
    public static final Item RING = register("ring");
    public static final Item TOTEM_OF_HEALTH = totem("health", healthTotemAttributes(4, ""), false);
    public static final Item WARP_STICK = register("warp_stick", new WarpStick(new Item.Settings().maxCount(1).rarity(Rarity.EPIC)));
    public static final Item FILL_ARMOR_TRIM_TEMPLATE = register("fill_armor_trim_smithing_template", SmithingTemplateItem.of(Id.of("poosmp:fill")));
    public static final Item DISC_TRIFECTA_CAP = musicDisc("trifecta_cap", PooSMPJukeboxSongs.TRIFECTA_CAP, "Embin");
    public static final Item DISC_BUTTERFLIES_AND_HURRICANES_INSTRUMENTAL = musicDisc("butterflies_and_hurricanes_instrumental", PooSMPJukeboxSongs.BUTTERFLIES_AND_HURRICANES_INSTRUMENTAL, "Embin");
    public static final Item DISC_BUDDY_HOLLY = musicDisc("buddy_holly", PooSMPJukeboxSongs.BUDDY_HOLLY, "ianyourgod");
    public static final Item DISC_STEREO_MADNESS = musicDisc("stereo_madness", PooSMPJukeboxSongs.STEREO_MADNESS, "a_pc");
    public static final Item DISC_NOT_LIKE_US = musicDisc("not_like_us", PooSMPJukeboxSongs.NOT_LIKE_US, "a_pc");
    public static final Item DISC_RESISTANCE_INSTRUMENTAL = musicDisc("resistance_instrumental", PooSMPJukeboxSongs.RESISTANCE_INSTRUMENTAL, "Embin");
    public static final Item TOTEM_OF_REACH = totem("reach", reachTotemAttributes(1.0F, ""), false);
    public static final Item BLANK_MUSIC_DISC = register("blank_music_disc", Rarity.UNCOMMON);
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
    public static final Item ONE_DOLLAR_BILL = moneyItem("one_dollar_bill", 1);
    public static final Item BACON_BUCKET = register(
        "bacon_bucket",
        new EntityBucketItem(
            EntityType.PIG,
            Fluids.LAVA,
            SoundEvents.ITEM_BUCKET_EMPTY_FISH,
            new Item.Settings().maxCount(1).component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)
        )
    );
    public static final Item TWO_DOLLAR_BILL = moneyItem("two_dollar_bill", 2);
    public static final Item FIVE_DOLLAR_BILL = moneyItem("five_dollar_bill", 5);
    public static final Item TEN_DOLLAR_BILL = moneyItem("ten_dollar_bill", 10);
    public static final Item TWENTY_FIVE_DOLLAR_BILL = moneyItem("twenty_five_dollar_bill", 25);
    public static final Item FIFTY_DOLLAR_BILL = moneyItem("fifty_dollar_bill", 50);
    public static final Item HUNDRED_DOLLAR_BILL = moneyItem("hundred_dollar_bill", 100);
    //public static final Item ONE_DOLLAR_COIN = coinItem("one_dollar_coin", 100);
    //public static final Item ONE_CENT_COIN = coinItem("one_cent_coin", 1);
    //public static final Item FIVE_CENT_COIN = coinItem("five_cent_coin", 5);
    //public static final Item TEN_CENT_COIN = coinItem("ten_cent_coin", 10);
    //public static final Item TWENTY_FIVE_CENT_COIN = coinItem("twenty_five_cent_coin", 25);
    public static final Item COW_STICK = register("cow_stick", new MobStickItem(
        new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON).fireproof(),
        EntityType.COW,
        MobStickItem.BuiltInNames.cow_names, false
    ));
    public static final Item RESIN_BRICK = register("minecraft:resin_brick");
    public static final Item STRANGE_DIAMOND_PICKAXE = register("strange_diamond_pickaxe", new StrangePickaxeItem(ToolMaterials.DIAMOND, copyAttributes(Items.DIAMOND_PICKAXE).component(PooSMPItemComponents.BLOCKS_MINED, 0).rarity(Rarity.EPIC)));
    public static final Item STRANGE_NETHERITE_PICKAXE = register("strange_netherite_pickaxe", new StrangePickaxeItem(ToolMaterials.NETHERITE, copyAttributes(Items.NETHERITE_PICKAXE).fireproof().component(PooSMPItemComponents.BLOCKS_MINED, 0).rarity(Rarity.EPIC)));
    public static final Item STRANGE_UPGRADE_SMITHING_TEMPLATE = register("strange_upgrade_smithing_template");
    public static final Item DISC_STORY_OF_UNDERTALE = musicDisc("story_of_undertale", PooSMPJukeboxSongs.SOU, "Cubey");
    public static final Item RAW_RED_POO = register("raw_red_poo", Rarity.UNCOMMON);
    public static final Item RED_POO_INGOT = register("red_poo_ingot", Rarity.UNCOMMON);
    public static final Item RED_POO_UPGRADE_SMITHING_TEMPLATE = register("red_poo_upgrade_smithing_template", Rarity.RARE);
    public static final Item BANANA = register("banana", new Item(new Item.Settings().food(PooSMPFoods.BANANA)));
    public static final Item RED_POO_SWORD = register("red_poo_sword", new SwordItem(PooSMPMaterials.RED_POO, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(PooSMPMaterials.RED_POO, 3, -2.4F))));
    public static final Item RED_POO_SHOVEL = register("red_poo_shovel", new ShovelItem(PooSMPMaterials.RED_POO, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(PooSMPMaterials.RED_POO, 1.5F, -3.0F))));
    public static final Item RED_POO_PICKAXE = register("red_poo_pickaxe", new PickaxeItem(PooSMPMaterials.RED_POO, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(PooSMPMaterials.RED_POO, 1.0F, -2.8F))));
    public static final Item RED_POO_AXE = register("red_poo_axe", new AxeItem(PooSMPMaterials.RED_POO, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(PooSMPMaterials.RED_POO, 5.0F, -3.0F))));
    public static final Item RED_POO_HOE = register("red_poo_hoe", new HoeItem(PooSMPMaterials.RED_POO, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(PooSMPMaterials.RED_POO, -3.0F, 0.0F))));
    public static final Item RED_POO_HELMET = register("red_poo_helmet", new ArmorItem(PooSMPMaterials.A_RED_POO, ArmorItem.Type.HELMET, (new Item.Settings()).maxDamage(ArmorItem.Type.HELMET.getMaxDamage(44))));
    public static final Item RED_POO_CHESTPLATE = register("red_poo_chestplate", new ArmorItem(PooSMPMaterials.A_RED_POO, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(44))));
    public static final Item RED_POO_LEGGINGS = register("red_poo_leggings", new ArmorItem(PooSMPMaterials.A_RED_POO, ArmorItem.Type.LEGGINGS, (new Item.Settings()).maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(44))));
    public static final Item RED_POO_BOOTS = register("red_poo_boots", new ArmorItem(PooSMPMaterials.A_RED_POO, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(44))));
    public static final Item GEAR = register("gear");
    public static final Item SCREW = register("screw");
    public static final Item GLASS_SHARD = register("glass_shard");
    public static final Item MAGIC_DEVICE = register("magic_device", new MagicDeviceItem(new Item.Settings().maxDamage(1024).attributeModifiers(magicDeviceAttributes(17.0F)).component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)));
    public static final Item NULL_SHARD = register("null_shard", Rarity.EPIC);

    public static ItemStack getBiomeStickStack(String biome) {
        ItemStack stack = new ItemStack(BIOME_STICK);
        stack.set(PooSMPItemComponents.SELECTED_BIOME, biome);
        return stack;
    }

    private static Item moneyItem(String name, int value) {
        return register(name, new MoneyItem(new Item.Settings().component(PooSMPItemComponents.MONEY, (value * 100)).maxCount(99)));
    }

    private static Item coinItem(String name, int value) {
        return register(name, new MoneyItem(new Item.Settings().component(PooSMPItemComponents.MONEY, value).maxCount(99)));
    }

    private static Item musicDisc(String name, RegistryKey<JukeboxSong> song, String requester) {
        return register("music_disc/" + name, new RequestedDiscItem(requester, new Item.Settings().maxCount(1).rarity(Rarity.RARE).jukeboxPlayable(song)));
    }

    private static Item register(String name) {
        return register(name, new Item(new Item.Settings()));
    }

    private static Item register(String name, int max_count) {
        return register(name, new Item(new Item.Settings().maxCount(max_count)));
    }

    private static Item register(String name, Rarity rarity) {
        return register(name, new Item(new Item.Settings().rarity(rarity)));
    }

    private static Item food(String name, FoodComponent food) {
        return register(name, new Item(new Item.Settings().food(food)));
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
                Id.of("poosmp:health_totem_buff" + id_suffix), hp, EntityAttributeModifier.Operation.ADD_VALUE
            ),
            AttributeModifierSlot.HAND
        ).build();
    }

    public static AttributeModifiersComponent reachTotemAttributes(float amount, String id_suffix) {
        return AttributeModifiersComponent.builder().add(
            EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE,
            new EntityAttributeModifier(
                Id.of("poosmp:reach_totem_blocks" + id_suffix), amount, EntityAttributeModifier.Operation.ADD_VALUE
            ),
            AttributeModifierSlot.HAND
        ).add(
            EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
            new EntityAttributeModifier(
                Id.of("poosmp:reach_totem_entities" + id_suffix), amount, EntityAttributeModifier.Operation.ADD_VALUE
            ),
            AttributeModifierSlot.HAND
        ).build();
    }

    public static void init() {
        PooSMPMod.LOGGER.info("Making PooSMP items!!!");
    }

    public static Item.Settings copyAttributes(ItemConvertible item) {
        AttributeModifiersComponent attributes = item.asItem().getComponents().getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);
        return new Item.Settings().attributeModifiers(attributes);
    }

    public static AttributeModifiersComponent magicDeviceAttributes(float amount) {
        return AttributeModifiersComponent.builder().add(
                EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                new EntityAttributeModifier(
                        Id.of("poosmp:magic_device"), amount, EntityAttributeModifier.Operation.ADD_VALUE
                ),
                AttributeModifierSlot.HAND
        ).build();
    }
}
