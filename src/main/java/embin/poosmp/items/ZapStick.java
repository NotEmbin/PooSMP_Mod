package embin.poosmp.items;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.stats.Stats;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ZapStick extends CreativeSnitchItem {
    public static int zap_stick_cooldown = 10;

    public ZapStick(Properties settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if (!world.isClientSide) {
            Commands commandManager = world.getServer().getCommands();
            CommandSourceStack commandSource = world.getServer().createCommandSourceStack().withSuppressedOutput();
            String player_uuid = user.getStringUUID();
            commandManager.executeWithPrefix(commandSource, "execute at " + player_uuid + " run summon minecraft:lightning_bolt");
        }

        user.awardStat(Stats.ITEM_USED.get(this));
        user.getCooldowns().addCooldown(this, zap_stick_cooldown);
        return TypedActionResult.success(user.getItemInHand(hand));
    }
}
