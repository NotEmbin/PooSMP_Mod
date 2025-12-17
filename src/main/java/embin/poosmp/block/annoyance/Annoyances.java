package embin.poosmp.block.annoyance;

import embin.poosmp.PooSMPRegistries;
import embin.poosmp.PooSMPSoundEvents;
import embin.poosmp.util.Id;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class Annoyances {
    public static final Annoyance SUS = register("sus", PooSMPSoundEvents.SUS, 1, 1);
    public static final Annoyance DRAGON = register("dragon", SoundEvents.ENDER_DRAGON_DEATH, 1.25F, 1, 35);

    public static Annoyance register(String id, SoundEvent soundEvent, float volume, float pitch, int chance) {
        return Registry.register(PooSMPRegistries.ANNOYANCE, Id.of(id), new Annoyance(soundEvent, volume, pitch, chance));
    }

    public static Annoyance register(String id, SoundEvent soundEvent, float volume, float pitch) {
        return register(id, soundEvent, volume, pitch, 100);
    }

    public static Annoyance register(String id, SoundEvent soundEvent) {
        return register(id, soundEvent, 1.0F, 1.0F);
    }
}
