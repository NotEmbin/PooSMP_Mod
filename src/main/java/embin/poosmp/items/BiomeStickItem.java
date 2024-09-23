package embin.poosmp.items;

import embin.poosmp.PooSMPItemComponents;
import embin.poosmp.util.ConvertNamespace;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.DynamicRegistryManager;
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

import java.util.Arrays;
import java.util.List;

public class BiomeStickItem extends Item {
    public BiomeStickItem(Settings settings) {
        super(settings);
    }
    private static final ConvertNamespace cn = new ConvertNamespace();
    public static final int biome_stick_cooldown = 5;
    public final int radius = 8;
    public final ComponentType selected_biome_component = PooSMPItemComponents.SELECTED_BIOME;
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
        "minecraft:snowy_slops",
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
        "poosmp:outer_hyrule"
    };

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        try {
            if (stack.contains(selected_biome_component) || stack.contains(DataComponentTypes.CUSTOM_NAME)) {
                DynamicRegistryManager registryManager = world.getRegistryManager();
                String biome_id = cn.convertVanilla(cn.removeInvalidCharactersFromString(stack.getOrDefault(selected_biome_component, default_biome))).toString();
                if (stack.contains(DataComponentTypes.CUSTOM_NAME)) {
                    biome_id = cn.convertVanilla(cn.removeInvalidCharactersFromString(stack.getOrDefault(DataComponentTypes.CUSTOM_NAME, Text.literal(default_biome)).getString())).toString();
                }
                if (registryManager.get(RegistryKeys.BIOME).containsId(cn.convertVanilla(biome_id))) {
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
        user.getItemCooldownManager().set(this, biome_stick_cooldown);
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        try {
            if (stack.contains(selected_biome_component) || stack.contains(DataComponentTypes.CUSTOM_NAME)) {
                String biome = stack.getOrDefault(selected_biome_component, default_biome);
                if (stack.contains(DataComponentTypes.CUSTOM_NAME)) {
                    biome = cn.removeInvalidCharactersFromString(stack.getOrDefault(DataComponentTypes.CUSTOM_NAME, Text.literal(default_biome)).getString());
                }
                String biome_name = cn.convertVanilla(biome).toTranslationKey("biome");
                String biome_id = cn.convertVanilla(biome).toString();
                tooltip.add(Text.translatable("tooltip.poosmp.selected_biome").formatted(Formatting.GREEN).append(":"));
                if (Language.getInstance().hasTranslation(biome_name)) {
                    tooltip.add(Text.literal(" ").formatted(Formatting.GRAY).append(Text.translatable(biome_name)));
                }
                tooltip.add(Text.literal(" ").formatted(Formatting.GRAY).append(biome_id));
                if (Arrays.stream(vanilla_biomes).noneMatch(biome_id::equals)) {
                    tooltip.add(Text.translatable("tooltip.poosmp.selected_biome.not_vanilla").formatted(Formatting.RED, Formatting.ITALIC));
                }
            } else {
                tooltip.add(Text.translatable("tooltip.poosmp.selected_biome.none_selected").formatted(Formatting.LIGHT_PURPLE, Formatting.ITALIC));
            }
        } catch (InvalidIdentifierException e) {
            tooltip.add(Text.translatable("tooltip.poosmp.selected_biome.invalid").formatted(Formatting.RED));
            tooltip.add(Text.literal("").formatted(Formatting.RED));
            tooltip.add(Text.literal("InvalidIdentifierException:").formatted(Formatting.RED));
            tooltip.add(Text.literal(e.getMessage()).formatted(Formatting.RED, Formatting.ITALIC));
        }
    }
}
