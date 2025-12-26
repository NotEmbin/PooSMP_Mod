package embin.poosmp.villager;

import embin.poosmp.PooSMPMod;
import embin.poosmp.block.PooSMPBlocks;
import embin.poosmp.util.Id;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;

public class PooSMPPoi {
    public static final ResourceKey<PoiType> BANKER_KEY = poi_key("banker");
    public static final PoiType BANKER = register("banker", PooSMPBlocks.BANKERS_TABLE);


    private static ResourceKey<PoiType> poi_key(String name) {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, Id.of(name));
    }

    private static PoiType register(String name, Block block) {
        return PointOfInterestHelper.register(Id.of(name), 1, 1, block);
    }

    public static void init() {
        PooSMPMod.LOGGER.info("Registering PooSMP POIs");
    }
}
