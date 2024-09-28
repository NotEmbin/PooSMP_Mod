package embin.poosmp.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ZapStick extends CreativeSnitchItem {
    public static int zap_stick_cooldown = 10;

    public ZapStick(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            CommandManager commandManager = world.getServer().getCommandManager();
            ServerCommandSource commandSource = world.getServer().getCommandSource().withSilent();
            String player_uuid = user.getUuidAsString();
            commandManager.executeWithPrefix(commandSource, "execute at " + player_uuid + " run summon minecraft:lightning_bolt");
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        user.getItemCooldownManager().set(this, zap_stick_cooldown);
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
