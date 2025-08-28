package embin.poosmp.villager;

import com.google.common.collect.ImmutableSet;
import embin.poosmp.PooSMPMod;
import embin.poosmp.util.Id;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class PooSMPVillagers {

    public static final VillagerProfession BANKER = register("banker", PooSMPPoi.BANKER_KEY, SoundEvents.ENTITY_VILLAGER_WORK_CARTOGRAPHER);


    private static VillagerProfession register(String name, RegistryKey<PointOfInterestType> type, SoundEvent sound_event) {
        return Registry.register(Registries.VILLAGER_PROFESSION, Id.of(name),
                new VillagerProfession(name, entry -> entry.matchesKey(type),
                        entry -> entry.matchesKey(type), ImmutableSet.of(),
                        ImmutableSet.of(), sound_event));
    }

    public static void init() {
        PooSMPMod.LOGGER.info("Registering PooSMP Villagers");
    }
}
