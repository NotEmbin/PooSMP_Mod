package embin.poosmp;

import com.mojang.serialization.Codec;
import embin.poosmp.util.ConvertNamespace;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Unit;

import java.util.List;

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

    public static final ComponentType<Unit> FORCE_MARRIED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convert("force_married"),
        ComponentType.<Unit>builder().codec(Unit.CODEC).build()
    );

    public static final ComponentType<EntityType<?>> MOB_OVERRIDE = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convert("mob_override"),
        ComponentType.<EntityType<?>>builder().codec(Registries.ENTITY_TYPE.getCodec()).build()
    );

    public static final ComponentType<List<String>> MOB_NAMES = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        cn.convert("mob_stick_names_override"),
        ComponentType.<List<String>>builder().codec(Codec.STRING.listOf()).build()
    );
}
