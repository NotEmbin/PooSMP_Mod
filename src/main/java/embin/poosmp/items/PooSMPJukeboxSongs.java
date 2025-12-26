package embin.poosmp.items;

import embin.poosmp.util.Id;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;

public interface PooSMPJukeboxSongs {
    private static ResourceKey<JukeboxSong> of (String namespace) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, Id.of(namespace));
    }

    ResourceKey<JukeboxSong> TRIFECTA_CAP = of("trifecta_cap");
    ResourceKey<JukeboxSong> BUTTERFLIES_AND_HURRICANES_INSTRUMENTAL = of("butterflies_and_hurricanes_instrumental");
    ResourceKey<JukeboxSong> BUDDY_HOLLY = of("buddy_holly");
    ResourceKey<JukeboxSong> STEREO_MADNESS = of("stereo_madness");
    ResourceKey<JukeboxSong> NOT_LIKE_US = of("not_like_us");
    ResourceKey<JukeboxSong> RESISTANCE_INSTRUMENTAL = of("resistance_instrumental");
    ResourceKey<JukeboxSong> BLISS_INSTRUMENTAL = of("bliss_instrumental");
    ResourceKey<JukeboxSong> ENDLESSLY_INSTRUMENTAL = of("endlessly_instrumental");
    ResourceKey<JukeboxSong> ENDLESSLY = of("endlessly");
    ResourceKey<JukeboxSong> ENDLESSLY_STEREO = of("endlessly_stereo");
    ResourceKey<JukeboxSong> SOU = of("story_of_undertale");
}
