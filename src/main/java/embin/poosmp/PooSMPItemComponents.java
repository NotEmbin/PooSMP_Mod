package embin.poosmp;

import com.mojang.serialization.Codec;
import embin.poosmp.util.ConvertNamespace;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Unit;

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

    public static final ComponentType<Boolean> MARRIED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convert("married"),
        ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static final ComponentType<Boolean> FROM_CREATIVE = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convert("from_creative"),
        ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );
}
