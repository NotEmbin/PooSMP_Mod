package embin.poosmp.items;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ZapStick extends CreativeSnitchItem {
    public static int ZAP_STICK_COOLDOWN = 10;

    public ZapStick(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        if (!world.isClientSide()) {
            Commands commandManager = world.getServer().getCommands();
            CommandSourceStack commandSource = world.getServer().createCommandSourceStack().withSuppressedOutput();
            String player_uuid = user.getStringUUID();
            commandManager.performPrefixedCommand(commandSource, "execute at " + player_uuid + " run summon minecraft:lightning_bolt");
        }

        user.awardStat(Stats.ITEM_USED.get(this));
        user.getCooldowns().addCooldown(user.getItemInHand(hand), ZAP_STICK_COOLDOWN);
        return InteractionResult.SUCCESS;
    }
}
