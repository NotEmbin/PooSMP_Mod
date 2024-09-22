package embin.poosmp.block;

import embin.poosmp.PooSMPMod;
import embin.poosmp.util.ConvertNamespace;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PooSMPBlocks {
    public static Block register(Block block, String name, Item.Settings settings, boolean should_register_item) {
        Identifier id = ConvertNamespace.cn.convert(name);
        if (should_register_item) {
            BlockItem blockItem = new BlockItem(block, settings);
            Registry.register(Registries.ITEM, id, blockItem);
        }
        return Registry.register(Registries.BLOCK, id, block);
    }

    public static Block register(Block block, String name, Item.Settings settings) {
        return register(block, name, settings, true);
    }

    public static Block register(Block block, String name, boolean should_register_item) {
        return register(block, name, new Item.Settings(), should_register_item);
    }

    public static final Block POOP_BLOCK = register(new Block(AbstractBlock.Settings.copy(Blocks.MUD)), "poop_block", true);
    public static final Block MISSINGNO_BLOCK = register(new Block(AbstractBlock.Settings.copy(Blocks.WHITE_CONCRETE)), "missingno", true);

    public static void init() {
        PooSMPMod.LOGGER.info("Making PooSMP blocks!");
    }
}
