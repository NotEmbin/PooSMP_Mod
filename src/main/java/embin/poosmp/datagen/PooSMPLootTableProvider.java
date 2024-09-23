package embin.poosmp.datagen;

import embin.poosmp.block.PooSMPBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class PooSMPLootTableProvider extends FabricBlockLootTableProvider {
    public PooSMPLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(PooSMPBlocks.POOP_BRICK_STAIRS);
        addDrop(PooSMPBlocks.POOP_BRICK_WALL);
        addDrop(PooSMPBlocks.POOP_BRICK_SLAB, slabDrops(PooSMPBlocks.POOP_BRICK_SLAB));
        addDrop(PooSMPBlocks.RED_NETHER_BRICK_FENCE);
    }
}
