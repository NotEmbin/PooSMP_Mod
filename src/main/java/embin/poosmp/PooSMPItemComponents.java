package embin.poosmp;

import com.mojang.serialization.Codec;
import embin.poosmp.util.ConvertNamespace;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Unit;

import java.util.List;

public class PooSMPItemComponents {
    public static void init() {
        PooSMPMod.LOGGER.info("Making PooSMP item components!!!");
    }

    public static final ComponentType<String> SELECTED_BIOME = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        ConvertNamespace.convert("selected_biome"),
        ComponentType.<String>builder().codec(Codec.STRING).build()
    );

    public static final ComponentType<Boolean> MARRIED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        ConvertNamespace.convert("married"),
        ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static final ComponentType<Boolean> FROM_CREATIVE = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        ConvertNamespace.convert("from_creative"),
        ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static final ComponentType<Unit> FORCE_MARRIED = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        ConvertNamespace.convert("force_married"),
        ComponentType.<Unit>builder().codec(Unit.CODEC).build()
    );

    public static final ComponentType<EntityType<?>> MOB_OVERRIDE = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        ConvertNamespace.convert("mob_override"),
        ComponentType.<EntityType<?>>builder().codec(Registries.ENTITY_TYPE.getCodec()).build()
    );

    public static final ComponentType<List<String>> MOB_NAMES = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        ConvertNamespace.convert("mob_stick_names_override"),
        ComponentType.<List<String>>builder().codec(Codec.STRING.listOf()).build()
    );

    public static final ComponentType<ItemStack> MOB_OFFHAND = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        ConvertNamespace.convert("mob_offhand_item_override"),
        ComponentType.<ItemStack>builder().codec(ItemStack.OPTIONAL_CODEC).build()
    );

    public static final ComponentType<Integer> MONEY = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        ConvertNamespace.convert("money"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Integer> STICK_COOLDOWN_OVERRIDE = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        ConvertNamespace.convert("stick_cooldown_override"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Integer> BIOME_STICK_RADIUS_OVERRIDE = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        ConvertNamespace.convert("biome_stick_radius_override"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );
}
