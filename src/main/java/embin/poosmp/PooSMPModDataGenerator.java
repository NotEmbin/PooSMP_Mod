package embin.poosmp;

import embin.poosmp.datagen.PooSMPLootTableProvider;
import embin.poosmp.datagen.PooSMPModelProvider;
import embin.poosmp.datagen.PooSMPRecipeProvider;
import embin.poosmp.datagen.PooSMPRegistryDataGenerator;
import embin.poosmp.world.PooSMPConfiguredFeatures;
import embin.poosmp.world.PooSMPPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class PooSMPModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(PooSMPModelProvider::new);
		pack.addProvider(PooSMPLootTableProvider::new);
		pack.addProvider(PooSMPRecipeProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.CONFIGURED_FEATURE, PooSMPConfiguredFeatures::bootstrap);
		registryBuilder.add(Registries.PLACED_FEATURE, PooSMPPlacedFeatures::bootstrap);
	}
}
