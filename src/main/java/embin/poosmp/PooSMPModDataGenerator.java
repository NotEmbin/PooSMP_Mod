package embin.poosmp;

import embin.poosmp.datagen.PooSMPLootTableProvider;
import embin.poosmp.datagen.PooSMPModelProvider;
import embin.poosmp.datagen.PooSMPRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class PooSMPModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(PooSMPModelProvider::new);
		pack.addProvider(PooSMPLootTableProvider::new);
		pack.addProvider(PooSMPRecipeProvider::new);
	}
}
