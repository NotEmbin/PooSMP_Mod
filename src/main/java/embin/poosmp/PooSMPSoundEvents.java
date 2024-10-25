package embin.poosmp;

import embin.poosmp.util.ConvertNamespace;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class PooSMPSoundEvents {
    private PooSMPSoundEvents() {}

    public static final SoundEvent MUSIC_DISC_TRIFECTA_CAP = registerSound("music_disc.trifecta_cap");
    public static final SoundEvent MUSIC_DISC_BUTTERFLIES_AND_HURRICANES_INSTRUMENTAL = registerSound("music_disc.butterflies_and_hurricanes_instrumental");
    public static final SoundEvent MUSIC_DISC_BUDDY_HOLLY = registerSound("music_disc.buddy_holly");
    public static final SoundEvent MUSIC_DISC_STEREO_MADNESS = registerSound("music_disc.stereo_madness");
    public static final SoundEvent MUSIC_DISC_NOT_LIKE_US = registerSound("music_disc.not_like_us");
    public static final SoundEvent MUSIC_DISC_RESISTANCE_INSTRUMENTAL = registerSound("music_disc.resistance_instrumental");
    public static final SoundEvent MUSIC_DISC_BLISS_INSTRUMENTAL = registerSound("music_disc.bliss_instrumental");
    public static final SoundEvent MUSIC_DISC_ENDLESSLY_INSTRUMENTAL = registerSound("music_disc.endlessly_instrumental");
    public static final SoundEvent MUSIC_DISC_ENDLESSLY = registerSound("music_disc.endlessly");
    public static final SoundEvent MUSIC_DISC_ENDLESSLY_STEREO = registerSound("music_disc.endlessly.stereo");
    public static final SoundEvent SUS = registerSound("sus");

    private static SoundEvent registerSound(String namespace) {
        Identifier id = ConvertNamespace.convert(namespace);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void init() {
        PooSMPMod.LOGGER.info("Registering PooSMP Mod sounds! Help me.");
    }
}
