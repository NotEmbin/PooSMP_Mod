package embin.poosmp.block;

import com.mojang.serialization.MapCodec;
import embin.poosmp.PooSMPSoundEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class SussyBlock extends Block {
    public static final MapCodec<SussyBlock> CODEC = createCodec(SussyBlock::new);

    @Override
    public MapCodec<? extends SussyBlock> getCodec() {
        return CODEC;
    }

    public SussyBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    protected void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient) {
            BlockPos blockPos = hit.getBlockPos();
            world.playSound(null, blockPos, PooSMPSoundEvents.SUS, SoundCategory.BLOCKS, 1.2F, 1.2F);
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.playSound(null, pos, PooSMPSoundEvents.SUS, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }
}
