package embin.poosmp.items;

import embin.poosmp.items.component.PooSMPItemComponents;
import embin.poosmp.util.Id;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class BiomeStickItem extends Item {
    public BiomeStickItem(Settings settings) {
        super(settings);
    }
    public static final int default_radius = 8;
    public final ComponentType<String> selected_biome_component = PooSMPItemComponents.SELECTED_BIOME;
    public final String default_biome = "minecraft:plains";
    public static final String[] vanilla_biomes = {
        // used purely for the tooltip and item group
        // actual biome stick logic does not use this list
        "minecraft:plains",
        "minecraft:forest",
        "minecraft:badlands",
        "minecraft:bamboo_jungle",
        "minecraft:basalt_deltas",
        "minecraft:beach",
        "minecraft:birch_forest",
        "minecraft:cherry_grove",
        "minecraft:cold_ocean",
        "minecraft:crimson_forest",
        "minecraft:dark_forest",
        "minecraft:deep_cold_ocean",
        "minecraft:deep_dark",
        "minecraft:deep_frozen_ocean",
        "minecraft:deep_lukewarm_ocean",
        "minecraft:deep_ocean",
        "minecraft:desert",
        "minecraft:dripstone_caves",
        "minecraft:end_barrens",
        "minecraft:end_highlands",
        "minecraft:end_midlands",
        "minecraft:eroded_badlands",
        "minecraft:flower_forest",
        "minecraft:frozen_ocean",
        "minecraft:frozen_peaks",
        "minecraft:frozen_river",
        "minecraft:grove",
        "minecraft:ice_spikes",
        "minecraft:jagged_peaks",
        "minecraft:jungle",
        "minecraft:lukewarm_ocean",
        "minecraft:lush_caves",
        "minecraft:mangrove_swamp",
        "minecraft:meadow",
        "minecraft:mushroom_fields",
        "minecraft:nether_wastes",
        "minecraft:ocean",
        "minecraft:old_growth_birch_forest",
        "minecraft:old_growth_pine_taiga",
        "minecraft:old_growth_spruce_taiga",
        "minecraft:river",
        "minecraft:savanna",
        "minecraft:savanna_plateau",
        "minecraft:small_end_islands",
        "minecraft:snowy_beach",
        "minecraft:snowy_plains",
        "minecraft:snowy_slopes",
        "minecraft:snowy_taiga",
        "minecraft:soul_sand_valley",
        "minecraft:sparse_jungle",
        "minecraft:stony_peaks",
        "minecraft:stony_shore",
        "minecraft:sunflower_plains",
        "minecraft:swamp",
        "minecraft:taiga",
        "minecraft:the_end",
        "minecraft:the_void",
        "minecraft:warm_ocean",
        "minecraft:warped_forest",
        "minecraft:windswept_forest",
        "minecraft:windswept_gravelly_hills",
        "minecraft:windswept_hills",
        "minecraft:windswept_savanna",
        "minecraft:wooded_badlands",
        "poosmp:ddededodediamante_plains",
        "poosmp:sky_islands",
        "poosmp:outer_hyrule",
        "poosmp:grasslands",
        "poosmp:hyrule_castle",
        "poosmp:hyrule",
        "poosmp:missingno"
    };

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        try {
            if (stack.contains(selected_biome_component) || stack.contains(DataComponentTypes.CUSTOM_NAME)) {
                int radius = stack.getOrDefault(PooSMPItemComponents.BIOME_STICK_RADIUS_OVERRIDE, default_radius);
                DynamicRegistryManager registryManager = world.getRegistryManager();
                String biome_id = Id.ofVanilla(Id.removeInvalidCharactersFromString(stack.getOrDefault(selected_biome_component, default_biome))).toString();
                if (stack.contains(DataComponentTypes.CUSTOM_NAME)) {
                    biome_id = Id.ofVanilla(Id.removeInvalidCharactersFromString(stack.getOrDefault(DataComponentTypes.CUSTOM_NAME, Text.literal(default_biome)).getString())).toString();
                }
                if (registryManager.get(RegistryKeys.BIOME).containsId(Id.ofVanilla(biome_id))) {
                    if (!world.isClient) {
                        MinecraftServer server = user.getServer();
                        CommandManager commandManager = server.getCommandManager();
                        ServerCommandSource commandSource = server.getCommandSource().withSilent();
                        String name = user.getName().getString();
                        int build_limit = world.getTopY();
                        int build_limit_bottom = world.getBottomY();
                        int player_y = (int) Math.clamp(user.getY(), build_limit_bottom + radius, build_limit - radius);
                        String negative_relative = "~-" + radius + " " + (player_y - radius) + " ~-" + radius;
                        String positive_relative = "~" + radius + " " + (player_y + radius) + " ~" + radius;
                        commandManager.executeWithPrefix(commandSource,
                            "execute at " + name + " run fillbiome " + negative_relative + " " + positive_relative + " " + biome_id
                        );
                        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS);
                    }
                } else {
                    world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS);
                    if (world.isClient) {
                        user.sendMessage(Text.literal("Biome \"" + biome_id + "\" is invalid!").formatted(Formatting.RED, Formatting.ITALIC));
                    }
                }
            }
        } catch (InvalidIdentifierException e) {
            world.playSound(null, user.getX(), user.getY() + 3, user.getZ(), SoundEvents.ENTITY_HORSE_DEATH, SoundCategory.PLAYERS);
            if (world.isClient) {
                user.sendMessage(Text.literal(String.valueOf(e)).formatted(Formatting.RED));
            }
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        user.getItemCooldownManager().set(this, stack.getOrDefault(PooSMPItemComponents.STICK_COOLDOWN_OVERRIDE, 5));
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        try {
            if (stack.contains(selected_biome_component) || stack.contains(DataComponentTypes.CUSTOM_NAME)) {
                String biome = stack.getOrDefault(selected_biome_component, default_biome);
                if (stack.contains(DataComponentTypes.CUSTOM_NAME)) {
                    biome = Id.removeInvalidCharactersFromString(stack.getOrDefault(DataComponentTypes.CUSTOM_NAME, Text.literal(default_biome)).getString());
                }
                String biome_name = Id.ofVanilla(biome).toTranslationKey("biome");
                Identifier biome_id = Id.ofVanilla(biome);
                tooltip.add(Text.translatable("tooltip.poosmp.selected_biome").formatted(Formatting.GREEN).append(":"));
                if (Language.getInstance().hasTranslation(biome_name)) {
                    tooltip.add(Text.literal(" ").formatted(Formatting.GRAY).append(Text.translatable(biome_name)));
                    if (type.isAdvanced()) {
                        tooltip.add(Text.literal(" ").formatted(Formatting.DARK_GRAY).append(biome_id.toString()));
                    }
                } else {
                    tooltip.add(Text.literal(" ").formatted(Formatting.GRAY).append(biome_id.toString()));
                }
                boolean exists = context.getRegistryLookup().createRegistryLookup().getOrThrow(RegistryKeys.BIOME).getOptional(RegistryKey.of(RegistryKeys.BIOME, biome_id)).isPresent();
                if (!exists) {
                    tooltip.add(Text.translatable("tooltip.poosmp.selected_biome.not_valid").formatted(Formatting.RED, Formatting.ITALIC));
                }
            } else {
                tooltip.add(Text.translatable("tooltip.poosmp.selected_biome.none_selected").formatted(Formatting.LIGHT_PURPLE, Formatting.ITALIC));
            }
        } catch (InvalidIdentifierException e) {
            tooltip.add(Text.translatable("tooltip.poosmp.selected_biome.invalid").formatted(Formatting.RED));
            tooltip.add(Text.literal("").formatted(Formatting.RED));
            tooltip.add(Text.literal("InvalidIdentifierException:").formatted(Formatting.RED));
            tooltip.add(Text.literal(e.getMessage()).formatted(Formatting.RED, Formatting.ITALIC));
        } catch (NullPointerException e) {
            tooltip.add(Text.literal("NullPointerException").formatted(Formatting.RED, Formatting.ITALIC));
        }
    }
}
