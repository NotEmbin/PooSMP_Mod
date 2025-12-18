package embin.poosmp.util;

import embin.poosmp.PooSMPMod;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;

public final class PooSMPPredicates {
    private PooSMPPredicates() {}

    public static boolean biomeFromPooSMP(BiomeSelectionContext biomeSelectionContext) {
        return biomeSelectionContext.getBiomeKey().identifier().getNamespace().equals(PooSMPMod.MOD_ID);
    }

    public static boolean biomeNotFromPooSMP(BiomeSelectionContext biomeSelectionContext) {
        return !biomeFromPooSMP(biomeSelectionContext);
    }
}
