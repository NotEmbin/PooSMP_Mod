package embin.poosmp.items;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class ZombieStickItem extends Item {
    public static int zombie_stick_cooldown = 20;

    private final String[] henchmen_names = {
        "Goon",
        "Henchman"
    };

    public ZombieStickItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        double player_x = user.getPos().getX();
        double player_z = user.getPos().getZ();
        double player_y = user.getPos().getY();
        BlockPos pos = BlockPos.ofFloored(player_x, player_y, player_z);

        if (!world.isClient) {
            ZombieEntity mob = EntityType.ZOMBIE.spawn(world.getServer().getWorld(world.getRegistryKey()), pos, SpawnReason.COMMAND);
            String zombie_uuid = mob.getUuidAsString();
            String player_uuid = user.getUuidAsString();
            Random random = new Random();
            mob.setCustomName(Text.literal(user.getName().getString()).append("'s ").append(henchmen_names[random.nextInt(0, henchmen_names.length)]));
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
}
