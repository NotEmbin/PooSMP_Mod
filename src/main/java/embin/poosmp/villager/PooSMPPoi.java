package embin.poosmp.villager;

import embin.poosmp.PooSMPMod;
import embin.poosmp.block.PooSMPBlocks;
import embin.poosmp.util.ConvertNamespace;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.poi.PointOfInterestType;

public class PooSMPPoi {
    public static final RegistryKey<PointOfInterestType> BANKER_KEY = poi_key("banker");
    public static final PointOfInterestType BANKER = register("banker", PooSMPBlocks.BANKERS_TABLE);


    private static RegistryKey<PointOfInterestType> poi_key(String name) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, ConvertNamespace.convert(name));
    }

    private static PointOfInterestType register(String name, Block block) {
        return PointOfInterestHelper.register(ConvertNamespace.convert(name), 1, 1, block);
    }

    public static void init() {
        PooSMPMod.LOGGER.info("Registering PooSMP POIs");
    }
}
