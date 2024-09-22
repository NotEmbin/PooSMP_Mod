package embin.poosmp;

import com.mojang.serialization.Codec;
import embin.poosmp.util.ConvertNamespace;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class PooSMPItemComponents {
    private static final ConvertNamespace cn = new ConvertNamespace();
    public static void init() {
        PooSMPMod.LOGGER.info("Making PooSMP item components!!!");
    }

    public static final ComponentType<String> SELECTED_BIOME = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convert("selected_biome"),
        ComponentType.<String>builder().codec(Codec.STRING).build()
    );
}
