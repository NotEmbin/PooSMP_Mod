package embin.poosmp.datagen;

import embin.poosmp.PooSMPMod;
import embin.poosmp.block.PooSMPBlocks;
import embin.poosmp.items.PooSMPItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;

public class PooSMPModelProvider extends FabricModelProvider {
    private final List<Item> ignoredItems;
    public PooSMPModelProvider(FabricDataOutput output) {
        super(output);
        this.ignoredItems = List.of(
                PooSMPItems.NULL_STICK,
                PooSMPBlocks.SUS.asItem(),
                PooSMPBlocks.POOP_BRICK_WALL.asItem(),
                PooSMPBlocks.RED_NETHER_BRICK_FENCE.asItem(),
                PooSMPItems.POOP_STICK
        );
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        // why do i HAVE to put item model generation in the BLOCK STATE MODEL GEN FUNCTION
        BuiltInRegistries.ITEM.stream().filter(item -> item.getCreatorNamespace(item.getDefaultInstance()).equals(PooSMPMod.MOD_ID)).forEach(item -> {
            if (!this.ignoredItems.contains(item)) {
                Identifier itemId = BuiltInRegistries.ITEM.getKey(item);
                Identifier modelId = item instanceof BlockItem ? itemId.withPrefix("block/") : itemId.withPrefix("item/");
                blockStateModelGenerator.registerSimpleItemModel(item, modelId);
            }
        });
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateTrimmableItem(Items.WOODEN_PICKAXE);
    }
}
