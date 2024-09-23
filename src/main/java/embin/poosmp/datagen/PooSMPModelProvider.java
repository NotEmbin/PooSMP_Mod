package embin.poosmp.datagen;

import embin.poosmp.block.PooSMPBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

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
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}
