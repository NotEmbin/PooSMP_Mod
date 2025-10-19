package embin.poosmp.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class RiggedBlock extends Block {
    public RiggedBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
        if (player.isSneaking()) return super.onBreak(world, pos, state, player);

        this.spawnBreakParticles(world, player, pos, state);
        Vec3d vec3d = pos.toCenterPos();
        world.createExplosion(null, world.getDamageSources().badRespawnPoint(vec3d), null, vec3d, 4.0F, true, World.ExplosionSourceType.BLOCK);
        return state;
    }

    @Override
    protected void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        world.breakBlock(hit.getBlockPos(), false, projectile.getOwner());
        Vec3d vec3d = hit.getBlockPos().toCenterPos();
        world.createExplosion(projectile, world.getDamageSources().badRespawnPoint(vec3d), null, vec3d, 4.0F, true, World.ExplosionSourceType.BLOCK);
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        return List.of(this.asItem().getDefaultStack());
    }
}
