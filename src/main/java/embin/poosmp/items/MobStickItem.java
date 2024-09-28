package embin.poosmp.items;

import embin.poosmp.PooSMPItemComponents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
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

public class MobStickItem extends Item {
    public static int zombie_stick_cooldown = 20;
    public EntityType<MobEntity> mob;
    public String[] names;

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
        try {
            if (user.getStackInHand(user.getActiveHand()).contains(PooSMPItemComponents.MOB_OVERRIDE)) {
                selected_mob = (EntityType<MobEntity>) user.getStackInHand(user.getActiveHand()).get(PooSMPItemComponents.MOB_OVERRIDE);
            }
        } catch (Exception exception) {
            if (world.isClient) {
                user.sendMessage(Text.literal("Mob override failed!").formatted(Formatting.RED));
                user.sendMessage(Text.literal(exception.toString()).formatted(Formatting.RED));
            }
        }
        try {
            if (user.getStackInHand(user.getActiveHand()).contains(PooSMPItemComponents.MOB_NAMES)) {
                name_list = user.getStackInHand(user.getActiveHand()).get(PooSMPItemComponents.MOB_NAMES);
            }
        } catch (Exception exception) {
            if (world.isClient) {
                user.sendMessage(Text.literal("Mob name list override failed!").formatted(Formatting.RED));
                user.sendMessage(Text.literal(exception.toString()).formatted(Formatting.RED));
            }
        }

        if (!world.isClient) {
            MobEntity mob = selected_mob.spawn(world.getServer().getWorld(world.getRegistryKey()), pos, SpawnReason.COMMAND);
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
            mob.setStackInHand(Hand.OFF_HAND, new ItemStack(Items.TOTEM_OF_UNDYING));
            CommandManager commandManager = world.getServer().getCommandManager();
            ServerCommandSource commandSource = world.getServer().getCommandSource().withSilent();
            commandManager.executeWithPrefix(commandSource, "team add " + player_uuid + " \"" + user.getName().getString() + "\"");
            commandManager.executeWithPrefix(commandSource, "team join " + player_uuid + " " + player_uuid);
            commandManager.executeWithPrefix(commandSource, "team join " + player_uuid + " " + zombie_uuid);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        user.getItemCooldownManager().set(this, zombie_stick_cooldown);
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
            tooltip.add(Text.literal(" ").append(mob_id).formatted(Formatting.GRAY));
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
}
