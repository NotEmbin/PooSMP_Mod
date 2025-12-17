package embin.poosmp.items;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BoomStickItem extends Item {
    public static int boom_stick_cooldown = 20;
    public BoomStickItem(Properties settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if (!world.isClientSide) {
            double player_x = user.getPos().getX();
            double player_z = user.getPos().getZ();
            double player_y;
            BlockPos pos = BlockPos.containing(player_x, user.getPos().getY() - 1, player_z);
            if (world.getBlockState(pos).isRedstoneConductor(world, pos)) {
                player_y = user.getPos().getY();
            } else {
                player_y = user.getPos().getY() - 1;
            }
            user.setInvulnerable(true);
            world.explode(null, player_x, player_y, player_z, 3.5F, Level.ExplosionInteraction.NONE);
            user.setInvulnerable(false);
        }
        user.awardStat(Stats.ITEM_USED.get(this));
        user.getCooldowns().addCooldown(this, boom_stick_cooldown);
        return TypedActionResult.success(user.getItemInHand(hand));
    }
}
