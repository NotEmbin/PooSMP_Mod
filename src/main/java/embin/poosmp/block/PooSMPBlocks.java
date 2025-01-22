package embin.poosmp.block;

import embin.poosmp.PooSMPMod;
import embin.poosmp.util.ConvertNamespace;
import embin.poosmp.world.PooSMPConfiguredFeatures;
import embin.poosmp.world.tree.PooSMPSaplingGens;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class PooSMPBlocks {
    public static final Block POOP_BLOCK = register("poop_block", new Block(copyBlock(Blocks.MUD).mapColor(DyeColor.BROWN)));
    public static final Block MISSINGNO_BLOCK = register("missingno", new Block(AbstractBlock.Settings.create().requiresTool().mapColor(DyeColor.MAGENTA).strength(1.5F)), new Item.Settings().rarity(Rarity.EPIC));
    public static final Block POOP_BRICKS = register("poop_bricks", new Block(copyBlock(Blocks.MUD_BRICKS).mapColor(MapColor.ORANGE)));
    public static final Block POOP_BRICK_STAIRS = register("poop_brick_stairs", stairBlock(POOP_BRICKS));
    public static final Block POOP_BRICK_SLAB = register("poop_brick_slab", slabBlock(POOP_BRICKS));
    public static final Block POOP_BRICK_WALL = register("poop_brick_wall", wallBlock(POOP_BRICKS));
    public static final Block RED_NETHER_BRICK_FENCE = register("red_nether_brick_fence", new FenceBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_RED).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F).sounds(BlockSoundGroup.NETHER_BRICKS)));
    public static final Block SUS = register("im_gonna_kill_myself", new SussyBlock(AbstractBlock.Settings.create().mapColor(DyeColor.RED).strength(1.0F)));
    public static final Block DDEDEDODEDIAMANTE_BLOCK = register("ddededodediamante_block", new ddededodediamanteBlock(AbstractBlock.Settings.create().mapColor(DyeColor.MAGENTA).strength(1.0F)));
    public static final Block PENIS_BLOCK = register("minecraft:penis", new GrassBlock(copyBlock(Blocks.GRASS_BLOCK)));
    public static final Block BANKERS_TABLE = register("bankers_table", new Block(copyBlock(Blocks.FLETCHING_TABLE).mapColor(DyeColor.BROWN)));

    public static final Block PALE_OAK_LOG = register("minecraft:pale_oak_log", new PillarBlock(copyBlock(Blocks.OAK_LOG)));
    public static final Block PALE_OAK_WOOD = register("minecraft:pale_oak_wood", new PillarBlock(copyBlock(Blocks.OAK_WOOD)));
    public static final Block STRIPPED_PALE_OAK_LOG = register("minecraft:stripped_pale_oak_log", new PillarBlock(copyBlock(Blocks.STRIPPED_OAK_LOG)));
    public static final Block STRIPPED_PALE_OAK_WOOD = register("minecraft:stripped_pale_oak_wood", new PillarBlock(copyBlock(Blocks.STRIPPED_OAK_WOOD)));
    public static final Block PALE_OAK_PLANKS = register("minecraft:pale_oak_planks", new Block(copyBlock(Blocks.OAK_PLANKS).mapColor(MapColor.OFF_WHITE)));
    public static final Block PALE_OAK_LEAVES = register("minecraft:pale_oak_leaves", new LeavesBlock(copyBlock(Blocks.OAK_LEAVES)));
    public static final Block PALE_OAK_SAPLING = register("minecraft:pale_oak_sapling", new SaplingBlock(PooSMPSaplingGens.PALE_OAK, copyBlock(Blocks.OAK_SAPLING)));
    public static final Block PALE_MOSS_BLOCK = register("minecraft:pale_moss_block", new NewMossBlock(PooSMPConfiguredFeatures.PALE_MOSS_PATCH_BONEMEAL, AbstractBlock.Settings.create().burnable().mapColor(MapColor.LIGHT_GRAY).strength(0.1F).sounds(BlockSoundGroup.MOSS_BLOCK).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block PALE_HANGING_MOSS = register("minecraft:pale_hanging_moss", new HangingMossBlock(AbstractBlock.Settings.create().burnable().mapColor(PALE_MOSS_BLOCK.getDefaultMapColor()).noCollision().sounds(BlockSoundGroup.MOSS_CARPET).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block PALE_MOSS_CARPET = register("minecraft:pale_moss_carpet", new CarpetBlock(AbstractBlock.Settings.create().burnable().mapColor(PALE_MOSS_BLOCK.getDefaultMapColor()).strength(0.1F).sounds(BlockSoundGroup.MOSS_CARPET).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block POTTED_PALE_OAK_SAPLING = register("minecraft:potted_pale_oak_sapling", new FlowerPotBlock(PALE_OAK_SAPLING, AbstractBlock.Settings.create()));
    public static final Block RESIN_BLOCK = register("minecraft:resin_block", new Block(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_ORANGE).instrument(NoteBlockInstrument.BASEDRUM).sounds(BlockSoundGroup.PACKED_MUD)));
    public static final Block RESIN_BRICKS = register("minecraft:resin_bricks", new Block(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_ORANGE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F).sounds(BlockSoundGroup.MUD_BRICKS)));
    public static final Block RESIN_BRICK_STAIRS = register("minecraft:resin_brick_stairs", stairBlock(RESIN_BRICKS));
    public static final Block RESIN_BRICK_SLAB = register("minecraft:resin_brick_slab", slabBlock(RESIN_BRICKS));
    public static final Block RESIN_BRICK_WALL = register("minecraft:resin_brick_wall", wallBlock(RESIN_BRICKS));
    public static final Block CHISELED_RESIN_BRICKS = register("minecraft:chiseled_resin_bricks", new Block(copyBlock(RESIN_BRICKS)));
    public static final Block PALE_OAK_DOOR = register("minecraft:pale_oak_door", new DoorBlock(PooSMPBlockSetTypes.PALE_OAK, AbstractBlock.Settings.create().mapColor(PALE_OAK_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).nonOpaque().burnable().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block PALE_OAK_FENCE = register("minecraft:pale_oak_fence", new FenceBlock(AbstractBlock.Settings.create().mapColor(PALE_OAK_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).burnable().sounds(BlockSoundGroup.WOOD)));
    public static final Block PALE_OAK_FENCE_GATE = register("minecraft:pale_oak_fence_gate", new FenceGateBlock(PooSMPWoodTypes.PALE_OAK, AbstractBlock.Settings.create().mapColor(PALE_OAK_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).burnable().sounds(BlockSoundGroup.WOOD)));
    public static final Block PALE_OAK_STAIRS = register("minecraft:pale_oak_stairs", stairBlock(PALE_OAK_PLANKS));
    public static final Block PALE_OAK_SLAB = register("minecraft:pale_oak_slab", slabBlock(PALE_OAK_PLANKS));
    public static final Block PALE_OAK_TRAPDOOR = register("minecraft:pale_oak_trapdoor", new TrapdoorBlock(PooSMPBlockSetTypes.PALE_OAK, AbstractBlock.Settings.create().mapColor(PALE_OAK_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).nonOpaque().allowsSpawning(Blocks::never).burnable()));
    public static final Block PALE_OAK_PRESSURE_PLATE = register("minecraft:pale_oak_pressure_plate", new PressurePlateBlock(PooSMPBlockSetTypes.PALE_OAK, AbstractBlock.Settings.create().mapColor(PALE_OAK_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(0.5F).burnable().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block PALE_OAK_BUTTON = register("minecraft:pale_oak_button", new ButtonBlock(PooSMPBlockSetTypes.PALE_OAK, 30, AbstractBlock.Settings.create().noCollision().strength(0.5f).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block RESIN_CLUMP = register("minecraft:resin_clump", new MultifaceBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_ORANGE).replaceable().noCollision().sounds(BlockSoundGroup.PACKED_MUD).burnable().pistonBehavior(PistonBehavior.DESTROY)));
    // public static final Block ITEM_SHOP = register("item_shop", new ItemShopBlock(copyBlock(Blocks.IRON_BLOCK)));
    // i can't bother with this right now


    public static Block register(Block block, String name, Item.Settings settings, boolean should_register_item) {
        Identifier id = ConvertNamespace.convert(name);
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
