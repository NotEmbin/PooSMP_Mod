package embin.poosmp.items;

import embin.poosmp.util.Id;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class WarpStick extends CreativeSnitchItem {
    public static int warp_stick_cooldown = 200;

    public WarpStick(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        double player_x = user.getPos().getX();
        double player_z = user.getPos().getZ();

        String pos = player_x + " 170 " + player_z;

        if (!world.isClient) {
            CommandManager commandManager = world.getServer().getCommandManager();
            ServerCommandSource commandSource = world.getServer().getCommandSource().withSilent();
            String player_uuid = user.getUuidAsString();
            if (user.getEntityWorld().getDimensionEntry().matchesId(Id.of("poosmp:hyrule"))) {
                commandManager.executeWithPrefix(commandSource, "execute in minecraft:overworld run tp " + player_uuid + " " + pos);
            } else {
                commandManager.executeWithPrefix(commandSource, "execute in poosmp:hyrule run tp " + player_uuid + " " + pos);
            }
            commandManager.executeWithPrefix(commandSource, "effect give " + player_uuid + " minecraft:resistance 11 5 true");
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        user.getItemCooldownManager().set(this, warp_stick_cooldown);
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
