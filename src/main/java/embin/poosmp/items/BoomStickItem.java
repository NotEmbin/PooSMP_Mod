package embin.poosmp.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BoomStickItem extends Item {
    public BoomStickItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        double player_x = user.getPos().getX();
        double player_z = user.getPos().getZ();
        double player_y;
        BlockPos pos = BlockPos.ofFloored(player_x, user.getPos().getY() - 1, player_z);
        if (world.getBlockState(pos).isSolidBlock(world, pos)) {
            player_y = user.getPos().getY();
        } else {
            player_y = user.getPos().getY() - 1;
        }
        user.setInvulnerable(true);
        world.createExplosion(null, player_x, player_y, player_z, 7.0F, World.ExplosionSourceType.NONE);
        user.setInvulnerable(false);
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
