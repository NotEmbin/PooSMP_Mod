package embin.poosmp.items;

import embin.poosmp.util.ConvertNamespace;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public interface PooSMPJukeboxSongs {
    private static RegistryKey<JukeboxSong> of (String namespace) {
        return RegistryKey.of(RegistryKeys.JUKEBOX_SONG, ConvertNamespace.convert(namespace));
    }

    RegistryKey<JukeboxSong> TRIFECTA_CAP = of("trifecta_cap");
    RegistryKey<JukeboxSong> BUTTERFLIES_AND_HURRICANES_INSTRUMENTAL = of("butterflies_and_hurricanes_instrumental");
    RegistryKey<JukeboxSong> BUDDY_HOLLY = of("buddy_holly");
    RegistryKey<JukeboxSong> STEREO_MADNESS = of("stereo_madness");
    RegistryKey<JukeboxSong> NOT_LIKE_US = of("not_like_us");
    RegistryKey<JukeboxSong> RESISTANCE_INSTRUMENTAL = of("resistance_instrumental");
    RegistryKey<JukeboxSong> BLISS_INSTRUMENTAL = of("bliss_instrumental");
    RegistryKey<JukeboxSong> ENDLESSLY_INSTRUMENTAL = of("endlessly_instrumental");
    RegistryKey<JukeboxSong> ENDLESSLY = of("endlessly");
    RegistryKey<JukeboxSong> ENDLESSLY_STEREO = of("endlessly_stereo");
    RegistryKey<JukeboxSong> SOU = of("story_of_undertale");
}
