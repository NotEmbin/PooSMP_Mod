package embin.poosmp.items;

import embin.poosmp.PooSMPItemComponents;
import embin.poosmp.PooSMPMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class MobStickItem extends CreativeSnitchItem {
    public EntityType<MobEntity> mob;
    public String[] names;

    public MobStickItem(Settings settings, EntityType<?> mob, String[] name_list, boolean snitch) {
        super(settings, snitch);
        this.mob = (EntityType<MobEntity>) mob;
        this.names = name_list;
    }

    public MobStickItem(Settings settings, EntityType<?> mob, String[] name_list) {
        super(settings);
        this.mob = (EntityType<MobEntity>) mob;
        this.names = name_list;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        double player_x = user.getPos().getX();
        double player_z = user.getPos().getZ();
        double player_y = user.getPos().getY();
        BlockPos pos = BlockPos.ofFloored(player_x, player_y, player_z);
        EntityType<MobEntity> selected_mob = this.mob;
        List<String> name_list = List.of(this.names);
        ItemStack stack = user.getStackInHand(user.getActiveHand());
        try {
            if (stack.contains(PooSMPItemComponents.MOB_OVERRIDE)) {
                selected_mob = (EntityType<MobEntity>) stack.get(PooSMPItemComponents.MOB_OVERRIDE);
            }
        } catch (Exception exception) {
            if (world.isClient) {
                user.sendMessage(Text.literal("Mob override failed!").formatted(Formatting.RED));
                user.sendMessage(Text.literal(exception.toString()).formatted(Formatting.RED));
            }
        }
        try {
            if (stack.contains(PooSMPItemComponents.MOB_NAMES)) {
                name_list = stack.get(PooSMPItemComponents.MOB_NAMES);
            }
        } catch (Exception exception) {
            if (world.isClient) {
                user.sendMessage(Text.literal("Mob name list override failed!").formatted(Formatting.RED));
                user.sendMessage(Text.literal(exception.toString()).formatted(Formatting.RED));
            }
        }

        if (!world.isClient) {
            EntityType<MobEntity> default_mob = this.mob;
            ItemStack offhand_item = stack.getOrDefault(PooSMPItemComponents.MOB_OFFHAND, new ItemStack(Items.TOTEM_OF_UNDYING));
            if (offhand_item == null) {
                offhand_item = new ItemStack(Items.TOTEM_OF_UNDYING);;
                user.sendMessage(Text.literal("Invalid offhand override item! Using default instead...").formatted(Formatting.RED));
            }
            MobEntity mob = selected_mob.spawn(world.getServer().getWorld(world.getRegistryKey()), pos, SpawnReason.COMMAND);
            if (mob == null) {
                user.sendMessage(Text.literal("Mob override failed because mob is null! Using default instead...").formatted(Formatting.RED));
                mob = default_mob.spawn(world.getServer().getWorld(world.getRegistryKey()), pos, SpawnReason.COMMAND);
            }
            String zombie_uuid = mob.getUuidAsString();
            String player_uuid = user.getUuidAsString();
            Random random = new Random();
            try {
                mob.setCustomName(Text.literal(user.getName().getString()).append("'s ").append(name_list.get(random.nextInt(0, name_list.size()))));
            } catch (IllegalArgumentException exception) {
                user.sendMessage(Text.literal("Mob could not be given a custom name!").formatted(Formatting.RED));
                user.sendMessage(Text.literal(exception.toString()).formatted(Formatting.RED));
                mob.setCustomName(Text.literal(user.getName().getString()).append("'s Mob"));
            }
            mob.setCustomNameVisible(true);
            mob.setStackInHand(Hand.OFF_HAND, offhand_item.copy());
            CommandManager commandManager = world.getServer().getCommandManager();
            ServerCommandSource commandSource = world.getServer().getCommandSource().withSilent();
            commandManager.executeWithPrefix(commandSource, "team add " + player_uuid + " \"" + user.getName().getString() + "\"");
            commandManager.executeWithPrefix(commandSource, "team join " + player_uuid + " " + player_uuid);
            commandManager.executeWithPrefix(commandSource, "team join " + player_uuid + " " + zombie_uuid);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        user.getItemCooldownManager().set(this, stack.getOrDefault(PooSMPItemComponents.STICK_COOLDOWN_OVERRIDE, 20));
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        if (stack.contains(PooSMPItemComponents.MOB_OVERRIDE)) {
            Text mob_name = Text.translatable(stack.get(PooSMPItemComponents.MOB_OVERRIDE).getTranslationKey());
            Text mob_id = Text.literal(stack.get(PooSMPItemComponents.MOB_OVERRIDE).getRegistryEntry().getIdAsString());
            tooltip.add(Text.translatable("tooltip.poosmp.selected_mob").append(":").formatted(Formatting.GREEN));
            tooltip.add(Text.literal(" ").append(mob_name).formatted(Formatting.GRAY));
            if (type.isAdvanced()) {
                tooltip.add(Text.literal(" ").append(mob_id).formatted(Formatting.DARK_GRAY));
            }
        }
        if (stack.contains(PooSMPItemComponents.MOB_OFFHAND)) {
            ItemStack override_stack = stack.getOrDefault(PooSMPItemComponents.MOB_OFFHAND, new ItemStack(Items.TOTEM_OF_UNDYING));
            if (!(override_stack.getItem() == Items.TOTEM_OF_UNDYING) || override_stack.getCount() != 1) {
                tooltip.add(Text.translatable("tooltip.poosmp.selected_mob_offhand_item").append(":").formatted(Formatting.GREEN));
                if (override_stack != null) {
                    Formatting item_rarity = override_stack.getRarity().getFormatting();
                    Text name = Text.empty().append(override_stack.getName()).formatted(item_rarity);
                    if (override_stack.getCount() > 1) {
                        name = Text.empty().append(name).append(" x").append(String.valueOf(override_stack.getCount())).formatted(Formatting.GRAY);
                    }
                    String id = Registries.ITEM.getId(override_stack.getItem()).toString();
                    tooltip.add(Text.literal(" ").append(name));
                    if (type.isAdvanced()) {
                        tooltip.add(Text.literal(" ").append(id).formatted(Formatting.DARK_GRAY));
                        if (!override_stack.isEmpty()) {
                            int component_count = override_stack.getComponents().size();
                            if (PooSMPMod.componentless_installed) {
                                int component_base = override_stack.getItem().getComponents().size();
                                int component_diff = component_count - component_base;
                                String component_display = String.valueOf(component_diff);
                                if (!component_display.startsWith("-")) component_display = "+" + component_diff;
                                if (component_diff != 0) {
                                    tooltip.add(Text.literal(" ").append(Text.translatable("item.components", component_display)).formatted(Formatting.DARK_GRAY));
                                }
                            } else {
                                tooltip.add(Text.literal(" ").append(Text.translatable("item.components", component_count)).formatted(Formatting.DARK_GRAY));
                            }
                        }
                    }
                } else {
                    tooltip.add(Text.literal(" ").append(Text.translatable("tooltip.poosmp.selected_mob_offhand_item.invalid")).formatted(Formatting.RED));
                }
            }
        }
        if (stack.contains(PooSMPItemComponents.MOB_NAMES)) {
            List<String> name_list = stack.get(PooSMPItemComponents.MOB_NAMES);
            tooltip.add(Text.translatable("tooltip.poosmp.selected_mob_names").append(":").formatted(Formatting.GREEN));
            for (String name : name_list) {
                tooltip.add(Text.literal(" ").append(name).formatted(Formatting.GRAY));
            }
            if (name_list.isEmpty()) {
                tooltip.add(Text.literal(" ").append(Text.translatable("tooltip.poosmp.selected_mob_names.none")).formatted(Formatting.RED));
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
    }

    public static final class Stack {
        public static ItemStack getMobStick(EntityType<MobEntity> mob) {
            ItemStack stack = new ItemStack(PooSMPItems.ZOMBIE_STICK);
            stack.set(PooSMPItemComponents.MOB_OVERRIDE, mob);
            return stack;
        }

        public static final EntityType<?>[] mob_list = {
            EntityType.PIG,
            EntityType.COW,
            EntityType.SHEEP,
            EntityType.CHICKEN,
            EntityType.CREEPER,
            EntityType.SKELETON,
            EntityType.SPIDER,
            EntityType.WITCH,
            EntityType.IRON_GOLEM,
            EntityType.CAVE_SPIDER,
            EntityType.BOGGED,
            EntityType.HUSK,
            EntityType.STRAY,
            EntityType.DROWNED,
            EntityType.WITHER_SKELETON,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.ZOMBIFIED_PIGLIN,
            EntityType.PIGLIN,
            EntityType.PIGLIN_BRUTE,
            EntityType.HOGLIN,
            EntityType.ZOGLIN,
            EntityType.BLAZE,
            EntityType.BREEZE,
            EntityType.ALLAY,
            EntityType.ARMADILLO,
            EntityType.AXOLOTL,
            EntityType.BAT,
            EntityType.BEE,
            EntityType.CAMEL,
            EntityType.CAT,
            EntityType.OCELOT,
            EntityType.PANDA,
            EntityType.PARROT,
            EntityType.PILLAGER,
            EntityType.VINDICATOR,
            EntityType.EVOKER,
            EntityType.COD,
            EntityType.SALMON,
            EntityType.TROPICAL_FISH,
            EntityType.SQUID,
            EntityType.GLOW_SQUID,
            EntityType.DOLPHIN,
            EntityType.GUARDIAN,
            EntityType.ELDER_GUARDIAN,
            EntityType.HORSE,
            EntityType.DONKEY,
            EntityType.MULE,
            EntityType.SKELETON_HORSE,
            EntityType.ZOMBIE_HORSE,
            EntityType.GIANT,
            EntityType.ENDERMAN,
            EntityType.ENDERMITE,
            EntityType.SHULKER,
            EntityType.FOX,
            EntityType.FROG,
            EntityType.GHAST,
            EntityType.GOAT,
            EntityType.SNOW_GOLEM,
            EntityType.LLAMA,
            EntityType.TRADER_LLAMA,
            EntityType.PUFFERFISH,
            EntityType.WANDERING_TRADER,
            EntityType.SLIME,
            EntityType.MAGMA_CUBE,
            EntityType.WOLF,
            EntityType.MOOSHROOM,
            EntityType.RABBIT,
            EntityType.RAVAGER,
            EntityType.PHANTOM,
            EntityType.POLAR_BEAR,
            EntityType.SILVERFISH,
            EntityType.TADPOLE,
            EntityType.SNIFFER,
            EntityType.STRIDER,
            EntityType.TURTLE
        };
    }
}
