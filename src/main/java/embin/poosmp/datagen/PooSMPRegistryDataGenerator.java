package embin.poosmp.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import java.util.concurrent.CompletableFuture;

public class PooSMPRegistryDataGenerator extends FabricDynamicRegistryProvider {
    public PooSMPRegistryDataGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider wrapperLookup, Entries entries) {
        entries.addAll(wrapperLookup.getWrapperOrThrow(Registries.CONFIGURED_FEATURE));
        entries.addAll(wrapperLookup.getWrapperOrThrow(Registries.PLACED_FEATURE));
    }

    @Override
    public String getName() {
        return "";
    }
}
