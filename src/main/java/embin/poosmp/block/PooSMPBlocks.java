package embin.poosmp.block;

import embin.poosmp.PooSMPMod;
import embin.poosmp.block.annoyance.Annoyances;
import embin.poosmp.util.Id;
import embin.poosmp.world.PooSMPConfiguredFeatures;
import embin.poosmp.world.tree.PooSMPSaplingGens;
import net.minecraft.block.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

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

    public static final Block PALE_OAK_LOG = register("minecraft:pale_oak_log", new RotatedPillarBlock(copyBlock(Blocks.OAK_LOG)));
    public static final Block PALE_OAK_WOOD = register("minecraft:pale_oak_wood", new RotatedPillarBlock(copyBlock(Blocks.OAK_WOOD)));
    public static final Block STRIPPED_PALE_OAK_LOG = register("minecraft:stripped_pale_oak_log", new RotatedPillarBlock(copyBlock(Blocks.STRIPPED_OAK_LOG)));
    public static final Block STRIPPED_PALE_OAK_WOOD = register("minecraft:stripped_pale_oak_wood", new RotatedPillarBlock(copyBlock(Blocks.STRIPPED_OAK_WOOD)));
    public static final Block PALE_OAK_PLANKS = register("minecraft:pale_oak_planks", new Block(copyBlock(Blocks.OAK_PLANKS).mapColor(MapColor.QUARTZ)));
    public static final Block PALE_OAK_LEAVES = register("minecraft:pale_oak_leaves", new LeavesBlock(copyBlock(Blocks.OAK_LEAVES)));
    public static final Block PALE_OAK_SAPLING = register("minecraft:pale_oak_sapling", new SaplingBlock(PooSMPSaplingGens.PALE_OAK, copyBlock(Blocks.OAK_SAPLING)));
    public static final Block PALE_MOSS_BLOCK = register("minecraft:pale_moss_block", new NewMossBlock(PooSMPConfiguredFeatures.PALE_MOSS_PATCH_BONEMEAL, BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(0.1F).sound(SoundType.MOSS).pushReaction(PushReaction.DESTROY)));
    public static final Block PALE_HANGING_MOSS = register("minecraft:pale_hanging_moss", new HangingMossBlock(BlockBehaviour.Properties.of().ignitedByLava().mapColor(PALE_MOSS_BLOCK.defaultMapColor()).noCollision().sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY)));
    public static final Block PALE_MOSS_CARPET = register("minecraft:pale_moss_carpet", new CarpetBlock(BlockBehaviour.Properties.of().ignitedByLava().mapColor(PALE_MOSS_BLOCK.defaultMapColor()).strength(0.1F).sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY)));
    public static final Block POTTED_PALE_OAK_SAPLING = register("minecraft:potted_pale_oak_sapling", new FlowerPotBlock(PALE_OAK_SAPLING, BlockBehaviour.Properties.of()));
    public static final Block RESIN_BLOCK = register("minecraft:resin_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.PACKED_MUD)));
    public static final Block RESIN_BRICKS = register("minecraft:resin_bricks", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.MUD_BRICKS)));
    public static final Block RESIN_BRICK_STAIRS = register("minecraft:resin_brick_stairs", stairBlock(RESIN_BRICKS));
    public static final Block RESIN_BRICK_SLAB = register("minecraft:resin_brick_slab", slabBlock(RESIN_BRICKS));
    public static final Block RESIN_BRICK_WALL = register("minecraft:resin_brick_wall", wallBlock(RESIN_BRICKS));
    public static final Block CHISELED_RESIN_BRICKS = register("minecraft:chiseled_resin_bricks", new Block(copyBlock(RESIN_BRICKS)));
    public static final Block PALE_OAK_DOOR = register("minecraft:pale_oak_door", new DoorBlock(PooSMPBlockSetTypes.PALE_OAK, BlockBehaviour.Properties.of().mapColor(PALE_OAK_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final Block PALE_OAK_FENCE = register("minecraft:pale_oak_fence", new FenceBlock(BlockBehaviour.Properties.of().mapColor(PALE_OAK_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD)));
    public static final Block PALE_OAK_FENCE_GATE = register("minecraft:pale_oak_fence_gate", new FenceGateBlock(PooSMPWoodTypes.PALE_OAK, BlockBehaviour.Properties.of().mapColor(PALE_OAK_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD)));
    public static final Block PALE_OAK_STAIRS = register("minecraft:pale_oak_stairs", stairBlock(PALE_OAK_PLANKS));
    public static final Block PALE_OAK_SLAB = register("minecraft:pale_oak_slab", slabBlock(PALE_OAK_PLANKS));
    public static final Block PALE_OAK_TRAPDOOR = register("minecraft:pale_oak_trapdoor", new TrapDoorBlock(PooSMPBlockSetTypes.PALE_OAK, BlockBehaviour.Properties.of().mapColor(PALE_OAK_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn(Blocks::never).ignitedByLava()));
    public static final Block PALE_OAK_PRESSURE_PLATE = register("minecraft:pale_oak_pressure_plate", new PressurePlateBlock(PooSMPBlockSetTypes.PALE_OAK, BlockBehaviour.Properties.of().mapColor(PALE_OAK_PLANKS.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final Block PALE_OAK_BUTTON = register("minecraft:pale_oak_button", new ButtonBlock(PooSMPBlockSetTypes.PALE_OAK, 30, BlockBehaviour.Properties.of().noCollision().strength(0.5f).pushReaction(PushReaction.DESTROY)));
    public static final Block RESIN_CLUMP = register("minecraft:resin_clump", new MultifaceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).replaceable().noCollision().sound(SoundType.PACKED_MUD).ignitedByLava().pushReaction(PushReaction.DESTROY)));
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
