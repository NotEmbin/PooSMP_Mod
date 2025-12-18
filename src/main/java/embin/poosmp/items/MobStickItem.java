package embin.poosmp.items;

import embin.poosmp.items.component.PooSMPItemComponents;
import embin.poosmp.PooSMPMod;
import embin.poosmp.util.PooUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class MobStickItem extends CreativeSnitchItem {
    public EntityType<Mob> mob;
    public String[] names;

    public MobStickItem(Properties settings, EntityType<?> mob, String[] name_list, boolean snitch) {
        super(settings, snitch);
        this.mob = (EntityType<Mob>) mob;
        this.names = name_list;
    }

    public MobStickItem(Properties settings, EntityType<?> mob, String[] name_list) {
        super(settings);
        this.mob = (EntityType<Mob>) mob;
        this.names = name_list;
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        double player_x = user.getX();
        double player_z = user.getZ();
        double player_y = user.getY();
        BlockPos pos = BlockPos.containing(player_x, player_y, player_z);
        EntityType<Mob> selected_mob = this.mob;
        List<String> name_list = List.of(this.names);
        ItemStack stack = user.getItemInHand(user.getUsedItemHand());
        try {
            if (stack.has(PooSMPItemComponents.MOB_OVERRIDE)) {
                selected_mob = (EntityType<Mob>) stack.get(PooSMPItemComponents.MOB_OVERRIDE);
            }
        } catch (ClassCastException exception) {
            if (world.isClientSide()) {
                user.displayClientMessage(Component.literal("Mob override failed!").withStyle(ChatFormatting.RED), true);
                user.displayClientMessage(Component.literal(exception.toString()).withStyle(ChatFormatting.RED), true);
            }
        }
        try {
            if (stack.has(PooSMPItemComponents.MOB_NAMES)) {
                name_list = stack.get(PooSMPItemComponents.MOB_NAMES);
            }
        } catch (Exception exception) {
            if (world.isClientSide()) {
                user.displayClientMessage(Component.literal("Mob name list override failed!").withStyle(ChatFormatting.RED), true);
                user.displayClientMessage(Component.literal(exception.toString()).withStyle(ChatFormatting.RED), true);
            }
        }

        if (!world.isClientSide()) {
            EntityType<Mob> default_mob = this.mob;
            ItemStack offhand_item = stack.getOrDefault(PooSMPItemComponents.MOB_OFFHAND, new ItemStack(Items.TOTEM_OF_UNDYING));
            if (offhand_item == null) {
                offhand_item = new ItemStack(Items.TOTEM_OF_UNDYING);;
                user.displayClientMessage(Component.literal("Invalid offhand override item! Using default instead...").withStyle(ChatFormatting.RED), true);
            }
            Mob mob = selected_mob.spawn(world.getServer().getLevel(world.dimension()), pos, EntitySpawnReason.COMMAND);
            if (mob == null) {
                user.displayClientMessage(Component.literal("Mob override failed because mob is null! Using default instead...").withStyle(ChatFormatting.RED), true);
                mob = default_mob.spawn(world.getServer().getLevel(world.dimension()), pos, EntitySpawnReason.COMMAND);
            }
            String zombie_uuid = mob.getStringUUID();
            String player_uuid = user.getStringUUID();
            Random random = new Random();
            if (name_list != null) {
                if (!name_list.isEmpty()) {
                    try {
                        mob.setCustomName(Component.literal(user.getName().getString()).append("'s ").append(name_list.get(random.nextInt(0, name_list.size()))));
                    } catch (IllegalArgumentException exception) {
                        user.displayClientMessage(Component.literal("Mob could not be given a custom name!").withStyle(ChatFormatting.RED), true);
                        user.displayClientMessage(Component.literal(exception.toString()).withStyle(ChatFormatting.RED), true);
                        mob.setCustomName(Component.literal(user.getName().getString()).append("'s Mob"));
                    } catch (NullPointerException exception) {
                        mob.setCustomName(Component.literal(user.getName().getString()).append("'s Mob"));
                    }
                    mob.setCustomNameVisible(true);
                }
            }
            mob.setItemInHand(InteractionHand.OFF_HAND, offhand_item.copy());
            Commands commandManager = world.getServer().getCommands();
            CommandSourceStack commandSource = world.getServer().createCommandSourceStack().withSuppressedOutput();
            commandManager.performPrefixedCommand(commandSource, "team add " + player_uuid + " \"" + user.getName().getString() + "\"");
            commandManager.performPrefixedCommand(commandSource, "team join " + player_uuid + " " + player_uuid);
            commandManager.performPrefixedCommand(commandSource, "team join " + player_uuid + " " + zombie_uuid);
        }

        user.awardStat(Stats.ITEM_USED.get(this));
        user.getCooldowns().addCooldown(stack, stack.getOrDefault(PooSMPItemComponents.STICK_COOLDOWN_OVERRIDE, 20));
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltipDisplay, consumer, type);
        if (stack.has(PooSMPItemComponents.MOB_OVERRIDE)) {
            Component mob_name = Component.translatable(stack.get(PooSMPItemComponents.MOB_OVERRIDE).getDescriptionId());
            Component mob_id = Component.literal(stack.get(PooSMPItemComponents.MOB_OVERRIDE).builtInRegistryHolder().getRegisteredName());
            consumer.accept(Component.translatable("tooltip.poosmp.selected_mob").append(":").withStyle(ChatFormatting.GREEN));
            consumer.accept(Component.literal(" ").append(mob_name).withStyle(ChatFormatting.GRAY));
            if (type.isAdvanced()) {
                consumer.accept(Component.literal(" ").append(mob_id).withStyle(ChatFormatting.DARK_GRAY));
            }
        }
        if (stack.has(PooSMPItemComponents.MOB_OFFHAND)) {
            ItemStack override_stack = stack.getOrDefault(PooSMPItemComponents.MOB_OFFHAND, new ItemStack(Items.TOTEM_OF_UNDYING));
            if (!(override_stack.getItem() == Items.TOTEM_OF_UNDYING) || override_stack.getCount() != 1) {
                consumer.accept(Component.translatable("tooltip.poosmp.selected_mob_offhand_item").append(":").withStyle(ChatFormatting.GREEN));
                if (override_stack != null) {
                    ChatFormatting item_rarity = override_stack.getRarity().color();
                    Component name = Component.empty().append(override_stack.getHoverName()).withStyle(item_rarity);
                    if (override_stack.getCount() > 1) {
                        name = Component.empty().append(name).append(" x").append(String.valueOf(override_stack.getCount())).withStyle(ChatFormatting.GRAY);
                    }
                    String id = BuiltInRegistries.ITEM.getKey(override_stack.getItem()).toString();
                    consumer.accept(Component.literal(" ").append(name));
                    if (type.isAdvanced()) {
                        consumer.accept(Component.literal(" ").append(id).withStyle(ChatFormatting.DARK_GRAY));
                        if (!override_stack.isEmpty()) {
                            int component_count = override_stack.getComponents().size();
                            if (PooSMPMod.componentless_installed) {
                                int component_base = override_stack.getItem().components().size();
                                int component_diff = component_count - component_base;
                                String component_display = String.valueOf(component_diff);
                                if (!component_display.startsWith("-")) component_display = "+" + component_diff;
                                if (component_diff != 0) {
                                    consumer.accept(Component.literal(" ").append(Component.translatable("item.components", component_display)).withStyle(ChatFormatting.DARK_GRAY));
                                }
                            } else {
                                consumer.accept(Component.literal(" ").append(Component.translatable("item.components", component_count)).withStyle(ChatFormatting.DARK_GRAY));
                            }
                        }
                    }
                } else {
                    consumer.accept(Component.literal(" ").append(Component.translatable("tooltip.poosmp.selected_mob_offhand_item.invalid")).withStyle(ChatFormatting.RED));
                }
            }
        }
        if (stack.has(PooSMPItemComponents.MOB_NAMES)) {
            List<String> name_list = stack.get(PooSMPItemComponents.MOB_NAMES);
            consumer.accept(Component.translatable("tooltip.poosmp.selected_mob_names").append(":").withStyle(ChatFormatting.GREEN));
            for (String name : name_list) {
                consumer.accept(Component.literal(" ").append(name).withStyle(ChatFormatting.GRAY));
            }
            if (name_list.isEmpty()) {
                consumer.accept(Component.literal(" ").append(Component.translatable("tooltip.poosmp.selected_mob_names.none")).withStyle(ChatFormatting.RED));
            }
        }
    }

    public static final class BuiltInNames {
        public static final String[] zombie_names = {
            "Goon",
            "Henchman"
        };

        public static final String[] villager_names = {
            "Villager"
        };

        public static final String[] no_names = {};

        public static final String[] cow_names = {
            "Cow",
            "Ol' Betsey"
        };
    }

    public static final class Stack {
        public static ItemStack getMobStick(EntityType<?> mob) {
            ItemStack stack = new ItemStack(PooSMPItems.ZOMBIE_STICK);
            stack.set(PooSMPItemComponents.MOB_OVERRIDE, mob);
            return stack;
        }

        public static void getStacks(HolderLookup.Provider provider, Consumer<ItemStack> consumer) {
            PooUtil.forEachEntry(provider, Registries.ENTITY_TYPE, entityTypeHolder -> {
                if (entityTypeHolder.value().canSummon()) {
                    consumer.accept(Stack.getMobStick(entityTypeHolder.value()));
                }
            });
        }
    }

    public static MobEffectInstance copy(MobEffectInstance instance) {
        return new MobEffectInstance(instance);
    }
}
