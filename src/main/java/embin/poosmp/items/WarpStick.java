package embin.poosmp.items;

import embin.poosmp.util.Id;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.stats.Stats;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WarpStick extends CreativeSnitchItem {
    public static int warp_stick_cooldown = 200;

    public WarpStick(Properties settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(Level world, Player user, InteractionHand hand) {
        double player_x = user.getPos().getX();
        double player_z = user.getPos().getZ();

        String pos = player_x + " 170 " + player_z;

        if (!world.isClientSide) {
            Commands commandManager = world.getServer().getCommands();
            CommandSourceStack commandSource = world.getServer().createCommandSourceStack().withSuppressedOutput();
            String player_uuid = user.getStringUUID();
            if (user.level().dimensionTypeRegistration().is(Id.of("poosmp:hyrule"))) {
                commandManager.executeWithPrefix(commandSource, "execute in minecraft:overworld run tp " + player_uuid + " " + pos);
            } else {
                commandManager.executeWithPrefix(commandSource, "execute in poosmp:hyrule run tp " + player_uuid + " " + pos);
            }
            commandManager.executeWithPrefix(commandSource, "effect give " + player_uuid + " minecraft:resistance 11 5 true");
        }

        user.awardStat(Stats.ITEM_USED.get(this));
        user.getCooldowns().addCooldown(this, warp_stick_cooldown);
        return TypedActionResult.success(user.getItemInHand(hand));
    }
}
