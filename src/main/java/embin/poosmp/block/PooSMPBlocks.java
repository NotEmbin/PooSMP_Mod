package embin.poosmp.block;

import embin.poosmp.PooSMPMod;
import embin.poosmp.block.annoyance.Annoyances;
import embin.poosmp.util.Id;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class PooSMPBlocks {
    public static final Block POOP_BLOCK = register("poop_block", new Block(copyBlock(Blocks.MUD).mapColor(DyeColor.BROWN)));
    public static final Block MISSINGNO_BLOCK = register("missingno", new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().mapColor(DyeColor.MAGENTA).strength(1.5F)), new Item.Properties().rarity(Rarity.EPIC));
    public static final Block POOP_BRICKS = register("poop_bricks", new Block(copyBlock(Blocks.MUD_BRICKS).mapColor(MapColor.COLOR_ORANGE)));
    public static final Block POOP_BRICK_STAIRS = register("poop_brick_stairs", stairBlock(POOP_BRICKS));
    public static final Block POOP_BRICK_SLAB = register("poop_brick_slab", slabBlock(POOP_BRICKS));
    public static final Block POOP_BRICK_WALL = register("poop_brick_wall", wallBlock(POOP_BRICKS));
    public static final Block RED_NETHER_BRICK_FENCE = register("red_nether_brick_fence", new FenceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NETHER).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F, 6.0F).sound(SoundType.NETHER_BRICKS)));
    public static final Block SUS = register("im_gonna_kill_myself", new AnnoyanceBlock(Annoyances.SUS, BlockBehaviour.Properties.of().mapColor(DyeColor.RED).strength(1.0F)));
    public static final Block DDEDEDODEDIAMANTE_BLOCK = register("ddededodediamante_block", new ddededodediamanteBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.MAGENTA).strength(1.0F)));
    public static final Block PENIS_BLOCK = register("minecraft:penis", new GrassBlock(copyBlock(Blocks.GRASS_BLOCK)));
    public static final Block BANKERS_TABLE = register("bankers_table", new Block(copyBlock(Blocks.FLETCHING_TABLE).mapColor(DyeColor.BROWN)));

    // public static final Block ITEM_SHOP = register("item_shop", new ItemShopBlock(copyBlock(Blocks.IRON_BLOCK)));
    // i can't bother with this right now
    public static final Block RED_POO_BLOCK = register("red_poo_block", new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().mapColor(DyeColor.RED).strength(2.5F).sound(SoundType.BONE_BLOCK)), new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final Block DRAGON_ANNOYANCE = register("ear_destroyer_9000", new AnnoyanceBlock(Annoyances.DRAGON, BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE).strength(1.0F)));
    public static final Block FAKE_DIRT = register("fake_dirt", fakeBlock(Blocks.DIRT));
    public static final Block FAKE_GRASS_BLOCK = register("fake_grass_block", fakeBlock(Blocks.GRASS_BLOCK));
    public static final Block FAKE_STONE = register("fake_stone", fakeBlock(Blocks.STONE));
    public static final Block RIGGED_STONE = register("rigged_stone", new RiggedBlock(copyBlock(Blocks.STONE)));


    public static Block register(Block block, String name, Item.Properties settings, boolean should_register_item) {
        Identifier id = Id.of(name);
        if (should_register_item) {
            BlockItem blockItem = new BlockItem(block, settings);
            Registry.register(BuiltInRegistries.ITEM, id, blockItem);
        }
        return Registry.register(BuiltInRegistries.BLOCK, id, block);
    }

    public static Block register(String name, Block block, Item.Properties settings) {
        return register(block, name, settings, true);
    }

    public static Block register(String name, Block block, boolean should_register_item) {
        return register(block, name, new Item.Properties(), should_register_item);
    }

    public static Block register(String name, Block block) {
        return register(block, name, new Item.Properties(), true);
    }

    public static BlockBehaviour.Properties copyBlock(Block block) {
        return BlockBehaviour.Properties.ofFullCopy(block);
    }

    public static Block stairBlock(Block block) {
        return new StairBlock(block.defaultBlockState(), copyBlock(block));
    }

    public static Block slabBlock(Block block) {
        return new SlabBlock(copyBlock(block));
    }

    public static Block wallBlock(Block block) {
        return new WallBlock(copyBlock(block).forceSolidOn());
    }

    public static Block fakeBlock(Block block) {
        return new FakeBlock(block, copyBlock(block).instabreak());
    }

    public static void init() {
        PooSMPMod.LOGGER.info("Making PooSMP blocks!");
    }
}
