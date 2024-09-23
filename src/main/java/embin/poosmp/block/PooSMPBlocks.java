package embin.poosmp.block;

import embin.poosmp.PooSMPMod;
import embin.poosmp.util.ConvertNamespace;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.Objects;

public class PooSMPBlocks {
    private static final ConvertNamespace cn = new ConvertNamespace();

    public static final Block POOP_BLOCK = register("poop_block", new Block(copyBlock(Blocks.MUD).mapColor(DyeColor.BROWN)));
    public static final Block MISSINGNO_BLOCK = register("missingno", new Block(AbstractBlock.Settings.create().requiresTool().mapColor(DyeColor.MAGENTA).strength(1.5F)), new Item.Settings().rarity(Rarity.EPIC));
    public static final Block POOP_BRICKS = register("poop_bricks", new Block(copyBlock(Blocks.MUD_BRICKS).mapColor(MapColor.ORANGE)));
    public static final Block POOP_BRICK_STAIRS = register("poop_brick_stairs", stairBlock(POOP_BRICKS));
    public static final Block POOP_BRICK_SLAB = register("poop_brick_slab", slabBlock(POOP_BRICKS));
    public static final Block POOP_BRICK_WALL = register("poop_brick_wall", wallBlock(POOP_BRICKS));
    public static final Block RED_NETHER_BRICK_FENCE = register("red_nether_brick_fence", new FenceBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_RED).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F).sounds(BlockSoundGroup.NETHER_BRICKS)));


    public static Block register(Block block, String name, Item.Settings settings, boolean should_register_item) {
        Identifier id = cn.convert(name);
        if (should_register_item) {
            BlockItem blockItem = new BlockItem(block, settings);
            Registry.register(Registries.ITEM, id, blockItem);
        }
        return Registry.register(Registries.BLOCK, id, block);
    }

    public static Block register(String name, Block block, Item.Settings settings) {
        return register(block, name, settings, true);
    }

    public static Block register(String name, Block block, boolean should_register_item) {
        return register(block, name, new Item.Settings(), should_register_item);
    }

    public static Block register(String name, Block block) {
        return register(block, name, new Item.Settings(), true);
    }

    public static AbstractBlock.Settings copyBlock(Block block) {
        return AbstractBlock.Settings.copy(block);
    }

    public static Block stairBlock(Block block) {
        return new StairsBlock(block.getDefaultState(), copyBlock(block));
    }

    public static Block slabBlock(Block block) {
        return new SlabBlock(copyBlock(block));
    }

    public static Block wallBlock(Block block) {
        return new WallBlock(copyBlock(block).solid());
    }

    public static void init() {
        PooSMPMod.LOGGER.info("Making PooSMP blocks!");
    }
}
