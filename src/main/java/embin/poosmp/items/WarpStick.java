package embin.poosmp.items;

import embin.poosmp.util.Id;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class WarpStick extends CreativeSnitchItem {
    public static int WARP_STICK_COOLDOWN = 200;

    public WarpStick(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        double player_x = user.getX();
        double player_z = user.getZ();

        String pos = player_x + " 170 " + player_z;

        if (!world.isClientSide()) {
            Commands commandManager = world.getServer().getCommands();
            CommandSourceStack commandSource = world.getServer().createCommandSourceStack().withSuppressedOutput();
            String player_uuid = user.getStringUUID();
            if (user.level().dimensionTypeRegistration().is(Id.of("poosmp:hyrule"))) {
                commandManager.performPrefixedCommand(commandSource, "execute in minecraft:overworld run tp " + player_uuid + " " + pos);
            } else {
                commandManager.performPrefixedCommand(commandSource, "execute in poosmp:hyrule run tp " + player_uuid + " " + pos);
            }
            commandManager.performPrefixedCommand(commandSource, "effect give " + player_uuid + " minecraft:resistance 11 5 true");
        }

        user.awardStat(Stats.ITEM_USED.get(this));
        user.getCooldowns().addCooldown(user.getItemInHand(hand), WARP_STICK_COOLDOWN);
        return InteractionResult.SUCCESS;
    }
}
