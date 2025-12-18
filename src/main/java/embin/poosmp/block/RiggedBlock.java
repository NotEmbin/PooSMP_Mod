package embin.poosmp.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import java.util.List;

public class RiggedBlock extends Block {
    public RiggedBlock(Properties settings) {
        super(settings);
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        if (player.isShiftKeyDown()) return super.playerWillDestroy(world, pos, state, player);

        this.spawnDestroyParticles(world, player, pos, state);
        Vec3 vec3d = pos.getCenter();
        world.explode(null, world.damageSources().badRespawnPointExplosion(vec3d), null, vec3d, 4.0F, true, Level.ExplosionInteraction.BLOCK);
        return state;
    }

    @Override
    protected void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile) {
        world.destroyBlock(hit.getBlockPos(), false, projectile.getOwner());
        Vec3 vec3d = hit.getBlockPos().getCenter();
        world.explode(projectile, world.damageSources().badRespawnPointExplosion(vec3d), null, vec3d, 4.0F, true, Level.ExplosionInteraction.BLOCK);
    }
}
