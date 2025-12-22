package embin.poosmp.datagen;

import embin.poosmp.block.PooSMPBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import java.util.concurrent.CompletableFuture;

public class PooSMPLootTableProvider extends FabricBlockLootTableProvider {
    public PooSMPLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        dropSelf(PooSMPBlocks.POOP_BRICK_STAIRS);
        dropSelf(PooSMPBlocks.POOP_BRICK_WALL);
        add(PooSMPBlocks.POOP_BRICK_SLAB, createSlabItemTable(PooSMPBlocks.POOP_BRICK_SLAB));
        dropSelf(PooSMPBlocks.RED_NETHER_BRICK_FENCE);
        dropSelf(PooSMPBlocks.FAKE_GRASS_BLOCK);
        dropSelf(PooSMPBlocks.FAKE_DIRT);
        dropSelf(PooSMPBlocks.FAKE_STONE);
        dropSelf(PooSMPBlocks.RIGGED_STONE);
    }
}
