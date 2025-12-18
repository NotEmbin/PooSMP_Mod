package embin.poosmp.datagen;

import embin.poosmp.PooSMPMod;
import embin.poosmp.items.PooSMPItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;

public class PooSMPModelProvider extends FabricModelProvider {
    public PooSMPModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        BuiltInRegistries.ITEM.stream().filter(item -> item.getCreatorNamespace(item.getDefaultInstance()).equals(PooSMPMod.MOD_ID)).forEach(item -> {
            Identifier itemId = BuiltInRegistries.ITEM.getKey(item);
            Identifier modelId = item instanceof BlockItem ? itemId.withPrefix("block/") : itemId.withPrefix("item/");
            blockStateModelGenerator.registerSimpleItemModel(item, modelId);
        });
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
    }
}
