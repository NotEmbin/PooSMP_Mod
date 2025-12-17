package embin.poosmp.villager;

import com.google.common.collect.ImmutableSet;
import embin.poosmp.PooSMPMod;
import embin.poosmp.util.Id;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;

public class PooSMPVillagers {

    public static final VillagerProfession BANKER = register("banker", PooSMPPoi.BANKER_KEY, SoundEvents.VILLAGER_WORK_CARTOGRAPHER);


    private static VillagerProfession register(String name, ResourceKey<PoiType> type, SoundEvent sound_event) {
        return Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, Id.of(name),
                new VillagerProfession(name, entry -> entry.is(type),
                        entry -> entry.is(type), ImmutableSet.of(),
                        ImmutableSet.of(), sound_event));
    }

    public static void init() {
        PooSMPMod.LOGGER.info("Registering PooSMP Villagers");
    }
}
