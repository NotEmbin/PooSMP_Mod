package embin.poosmp.items;

import embin.poosmp.PooSMPItemComponents;
import embin.poosmp.PooSMPMod;
import embin.poosmp.util.ConvertNamespace;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class PooSMPItems {
    private static final ConvertNamespace cn = new ConvertNamespace();
    public static <T extends Item> T register(String path, T item) {
        return Registry.register(Registries.ITEM, cn.convert(path), item);
    }


    public static ItemStack getBiomeStickStack(String biome) {
        ItemStack stack = new ItemStack(BIOME_STICK);
        stack.set(PooSMPItemComponents.SELECTED_BIOME, biome);
        return stack;
    }


    public static final Item POOP_STICK = register("poop_stick", new PoopStickItem(new Item.Settings().rarity(Rarity.EPIC).fireproof()));
    public static final Item SERVER_SAYS_WHAT_STICK = register("server_says_what_stick", new ServerSaysWhatItem(new Item.Settings().rarity(Rarity.EPIC)));
    public static final Item BIOME_STICK = register("biome_stick", new BiomeStickItem(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON).component(PooSMPItemComponents.SELECTED_BIOME, "minecraft:plains").fireproof()));
    public static final Item BOOM_STICK = register("boom_stick", new BoomStickItem(new Item.Settings().maxCount(1).rarity(Rarity.EPIC)));
    public static final Item ZOMBIE_STICK = register("zombie_stick", new ZombieStickItem(new Item.Settings().maxCount(1).rarity(Rarity.EPIC)));
    public static final Item DIAMOND_SHARD = register("diamond_shard", new Item(new Item.Settings()));

    public static void init() {
        PooSMPMod.LOGGER.info("Making PooSMP items!!!");
    }
}
