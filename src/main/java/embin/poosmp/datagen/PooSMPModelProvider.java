package embin.poosmp.datagen;

import embin.poosmp.block.PooSMPBlocks;
import embin.poosmp.items.PooSMPItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class PooSMPModelProvider extends FabricModelProvider {
    public PooSMPModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        BlockStateModelGenerator.BlockTexturePool poop_bricks = blockStateModelGenerator.registerCubeAllModelTexturePool(PooSMPBlocks.POOP_BRICKS);
        BlockStateModelGenerator.BlockTexturePool red_nether_bricks = blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.RED_NETHER_BRICKS);
        poop_bricks.stairs(PooSMPBlocks.POOP_BRICK_STAIRS);
        poop_bricks.slab(PooSMPBlocks.POOP_BRICK_SLAB);
        poop_bricks.wall(PooSMPBlocks.POOP_BRICK_WALL);
        red_nether_bricks.fence(PooSMPBlocks.RED_NETHER_BRICK_FENCE);
        blockStateModelGenerator.registerSimpleCubeAll(PooSMPBlocks.PENIS_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(PooSMPItems.ONE_DOLLAR_BILL, Models.GENERATED);
        itemModelGenerator.register(PooSMPItems.TWO_DOLLAR_BILL, Models.GENERATED);
        itemModelGenerator.register(PooSMPItems.FIVE_DOLLAR_BILL, Models.GENERATED);
        itemModelGenerator.register(PooSMPItems.TEN_DOLLAR_BILL, Models.GENERATED);
        itemModelGenerator.register(PooSMPItems.TWENTY_FIVE_DOLLAR_BILL, Models.GENERATED);
        itemModelGenerator.register(PooSMPItems.FIFTY_DOLLAR_BILL, Models.GENERATED);
        itemModelGenerator.register(PooSMPItems.HUNDRED_DOLLAR_BILL, Models.GENERATED);
        itemModelGenerator.register(PooSMPItems.ONE_CENT_COIN, Models.GENERATED);
        itemModelGenerator.register(PooSMPItems.FIVE_CENT_COIN, Models.GENERATED);
        itemModelGenerator.register(PooSMPItems.TEN_CENT_COIN, Models.GENERATED);
        itemModelGenerator.register(PooSMPItems.TWENTY_FIVE_CENT_COIN, Models.GENERATED);
        itemModelGenerator.register(PooSMPItems.ONE_DOLLAR_COIN, Models.GENERATED);
        itemModelGenerator.register(PooSMPItems.BACON_BUCKET, Models.GENERATED);
    }
}
