package embin.poosmp;

import embin.poosmp.datagen.PooSMPLootTableProvider;
import embin.poosmp.datagen.PooSMPModelProvider;
import embin.poosmp.datagen.PooSMPRecipeProvider;
import embin.poosmp.datagen.PooSMPRegistryDataGenerator;
import embin.poosmp.world.PooSMPConfiguredFeatures;
import embin.poosmp.world.PooSMPPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class PooSMPModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(PooSMPModelProvider::new);
		pack.addProvider(PooSMPLootTableProvider::new);
		pack.addProvider(PooSMPRecipeProvider::new);
		pack.addProvider(PooSMPRegistryDataGenerator::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, PooSMPConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, PooSMPPlacedFeatures::bootstrap);
	}
}
