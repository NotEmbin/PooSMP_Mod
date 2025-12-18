package embin.poosmp.datagen;

import embin.poosmp.items.PooSMPItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

public class PooSMPModelProvider extends FabricModelProvider {
    public PooSMPModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.createFlatItemModel(PooSMPItems.ONE_DOLLAR_BILL, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.createFlatItemModel(PooSMPItems.ONE_DOLLAR_BILL, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.createFlatItemModel(PooSMPItems.TWO_DOLLAR_BILL, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.createFlatItemModel(PooSMPItems.FIVE_DOLLAR_BILL, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.createFlatItemModel(PooSMPItems.TEN_DOLLAR_BILL, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.createFlatItemModel(PooSMPItems.TWENTY_FIVE_DOLLAR_BILL, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.createFlatItemModel(PooSMPItems.FIFTY_DOLLAR_BILL, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.createFlatItemModel(PooSMPItems.HUNDRED_DOLLAR_BILL, ModelTemplates.FLAT_ITEM);
        //itemModelGenerator.register(PooSMPItems.ONE_CENT_COIN, Models.GENERATED);
        //itemModelGenerator.register(PooSMPItems.FIVE_CENT_COIN, Models.GENERATED);
        //itemModelGenerator.register(PooSMPItems.TEN_CENT_COIN, Models.GENERATED);
        //itemModelGenerator.register(PooSMPItems.TWENTY_FIVE_CENT_COIN, Models.GENERATED);
        //itemModelGenerator.register(PooSMPItems.ONE_DOLLAR_COIN, Models.GENERATED);
        itemModelGenerator.createFlatItemModel(PooSMPItems.BACON_BUCKET, ModelTemplates.FLAT_ITEM);

    }
}
