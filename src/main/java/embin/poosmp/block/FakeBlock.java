package embin.poosmp.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FakeBlock extends Block implements ImpersonatingBlock {
    private final Block fakeOf;

    public FakeBlock(Block block, Properties settings) {
        super(settings.noCollision());
        this.fakeOf = block;
    }

    @Override
    public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        this.fakeOf.stepOn(level, blockPos, blockState, entity);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        this.fakeOf.animateTick(blockState, level, blockPos, randomSource);
    }

    @Override
    public Block getBlockImpersonatingAs() {
        return this.fakeOf;
    }
}
