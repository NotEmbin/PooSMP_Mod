package embin.poosmp.items;

import embin.poosmp.items.component.PooSMPItemComponents;
import embin.poosmp.util.Id;
import net.minecraft.ChatFormatting;
import net.minecraft.IdentifierException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import java.util.function.Consumer;

public class BiomeStickItem extends Item {
    public BiomeStickItem(Properties settings) {
        super(settings);
    }
    public static final int default_radius = 8;
    public final DataComponentType<String> selected_biome_component = PooSMPItemComponents.SELECTED_BIOME;
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
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        try {
            if (stack.has(selected_biome_component) || stack.has(DataComponents.CUSTOM_NAME)) {
                int radius = stack.getOrDefault(PooSMPItemComponents.BIOME_STICK_RADIUS_OVERRIDE, default_radius);
                RegistryAccess registryManager = world.registryAccess();
                String biome_id = Id.ofVanilla(Id.removeInvalidCharactersFromString(stack.getOrDefault(selected_biome_component, default_biome))).toString();
                if (stack.has(DataComponents.CUSTOM_NAME)) {
                    biome_id = Id.ofVanilla(Id.removeInvalidCharactersFromString(stack.getOrDefault(DataComponents.CUSTOM_NAME, Component.literal(default_biome)).getString())).toString();
                }
                if (registryManager.lookupOrThrow(Registries.BIOME).containsKey(Id.ofVanilla(biome_id))) {
                    if (!world.isClientSide()) {
                        MinecraftServer server = user.level().getServer();
                        Commands commandManager = server.getCommands();
                        CommandSourceStack commandSource = server.createCommandSourceStack().withSuppressedOutput();
                        String name = user.getName().getString();
                        int build_limit = world.getHeight();
                        int build_limit_bottom = world.getMinY();
                        int player_y = (int) Math.clamp(user.getY(), build_limit_bottom + radius, build_limit - radius);
                        String negative_relative = "~-" + radius + " " + (player_y - radius) + " ~-" + radius;
                        String positive_relative = "~" + radius + " " + (player_y + radius) + " ~" + radius;
                        commandManager.performPrefixedCommand(commandSource,
                            "execute at " + name + " run fillbiome " + negative_relative + " " + positive_relative + " " + biome_id
                        );
                        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS);
                    }
                } else {
                    world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS);
                    if (world.isClientSide()) {
                        user.displayClientMessage(Component.literal("Biome \"" + biome_id + "\" is invalid!").withStyle(ChatFormatting.RED, ChatFormatting.ITALIC), true);
                    }
                }
            }
        } catch (IdentifierException e) {
            world.playSound(null, user.getX(), user.getY() + 3, user.getZ(), SoundEvents.HORSE_DEATH, SoundSource.PLAYERS);
            if (world.isClientSide()) {
                user.displayClientMessage(Component.literal(String.valueOf(e)).withStyle(ChatFormatting.RED), true);
            }
        }
        user.awardStat(Stats.ITEM_USED.get(this));
        user.getCooldowns().addCooldown(stack, stack.getOrDefault(PooSMPItemComponents.STICK_COOLDOWN_OVERRIDE, 5));
        return InteractionResult.SUCCESS;
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltipDisplay, consumer, type);
        try {
            if (stack.has(selected_biome_component) || stack.has(DataComponents.CUSTOM_NAME)) {
                String biome = stack.getOrDefault(selected_biome_component, default_biome);
                if (stack.has(DataComponents.CUSTOM_NAME)) {
                    biome = Id.removeInvalidCharactersFromString(stack.getOrDefault(DataComponents.CUSTOM_NAME, Component.literal(default_biome)).getString());
                }
                String biome_name = Id.ofVanilla(biome).toLanguageKey("biome");
                Identifier biome_id = Id.ofVanilla(biome);
                consumer.accept(Component.translatable("tooltip.poosmp.selected_biome").withStyle(ChatFormatting.GREEN).append(":"));
                if (Language.getInstance().has(biome_name)) {
                    consumer.accept(Component.literal(" ").withStyle(ChatFormatting.GRAY).append(Component.translatable(biome_name)));
                    if (type.isAdvanced()) {
                        consumer.accept(Component.literal(" ").withStyle(ChatFormatting.DARK_GRAY).append(biome_id.toString()));
                    }
                } else {
                    consumer.accept(Component.literal(" ").withStyle(ChatFormatting.GRAY).append(biome_id.toString()));
                }
                boolean exists = context.registries().lookupOrThrow(Registries.BIOME).get(ResourceKey.create(Registries.BIOME, biome_id)).isPresent();
                if (!exists) {
                    consumer.accept(Component.translatable("tooltip.poosmp.selected_biome.not_valid").withStyle(ChatFormatting.RED, ChatFormatting.ITALIC));
                }
            } else {
                consumer.accept(Component.translatable("tooltip.poosmp.selected_biome.none_selected").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC));
            }
        } catch (IdentifierException e) {
            consumer.accept(Component.translatable("tooltip.poosmp.selected_biome.invalid").withStyle(ChatFormatting.RED));
            consumer.accept(Component.literal("").withStyle(ChatFormatting.RED));
            consumer.accept(Component.literal("InvalidIdentifierException:").withStyle(ChatFormatting.RED));
            consumer.accept(Component.literal(e.getMessage()).withStyle(ChatFormatting.RED, ChatFormatting.ITALIC));
        } catch (NullPointerException e) {
            consumer.accept(Component.literal("NullPointerException").withStyle(ChatFormatting.RED, ChatFormatting.ITALIC));
        }
    }
}
