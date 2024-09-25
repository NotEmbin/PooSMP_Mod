package embin.poosmp.items;

import embin.poosmp.util.ConvertNamespace;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public interface PooSMPJukeboxSongs {
    private static RegistryKey<JukeboxSong> of (String namespace) {
        return RegistryKey.of(RegistryKeys.JUKEBOX_SONG, ConvertNamespace.cn.convert(namespace));
    }

    RegistryKey<JukeboxSong> TRIFECTA_CAP = of("trifecta_cap");
    RegistryKey<JukeboxSong> BUTTERFLIES_AND_HURRICANES_INSTRUMENTAL = of("butterflies_and_hurricanes_instrumental");
    RegistryKey<JukeboxSong> BUDDY_HOLLY = of("buddy_holly");
    RegistryKey<JukeboxSong> STEREO_MADNESS = of("stereo_madness");
    RegistryKey<JukeboxSong> NOT_LIKE_US = of("not_like_us");
    RegistryKey<JukeboxSong> RESISTANCE_INSTRUMENTAL = of("resistance_instrumental");
}
