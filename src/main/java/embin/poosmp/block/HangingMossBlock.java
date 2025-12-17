package embin.poosmp.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MultifaceSpreadeableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HangingMossBlock extends Block implements BonemealableBlock {
    public static final MapCodec<HangingMossBlock> CODEC = simpleCodec(HangingMossBlock::new);
    public static final BooleanProperty TIP = BooleanProperty.create("tip");
    private static final VoxelShape TIP_SHAPE = Block.box((double)1.0F, (double)2.0F, (double)1.0F, (double)15.0F, (double)16.0F, (double)15.0F);
    private static final VoxelShape SHAPE = Block.box((double)1.0F, (double)0.0F, (double)1.0F, (double)15.0F, (double)16.0F, (double)15.0F);

    public HangingMossBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(TIP, true));
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return this.canGrowInto(world.getBlockState(this.getTipPos(world, pos).below()));
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos blockPos = this.getTipPos(world, pos).below();
        if (this.canGrowInto(world.getBlockState(blockPos))) {
            world.setBlockAndUpdate(blockPos, (BlockState) state.setValue(TIP, true));
        }
    }

    public BlockPos getTipPos(BlockGetter world, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = pos.mutable();
        BlockState blockState;
        do {
            mutable.move(Direction.DOWN);
            blockState = world.getBlockState(mutable);
        } while (blockState.is(this));
        return mutable.relative(Direction.UP).immutable();
    }

    private boolean canGrowInto(BlockState state) {
        return state.isAir();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIP);
    }

    @Override
    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!this.canPlaceAt(world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    private boolean canPlaceAt(BlockGetter world, BlockPos pos) {
        BlockPos blockPos = pos.relative(Direction.UP);
        BlockState blockState = world.getBlockState(blockPos);
        return MultifaceSpreadeableBlock.canAttachTo(world, Direction.UP, blockPos, blockState) || blockState.is(PooSMPBlocks.PALE_HANGING_MOSS);
    }


    @Override
    protected boolean isTransparent(BlockState state, BlockGetter world, BlockPos pos) {
        return true;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return state.getValue(TIP) ? TIP_SHAPE : SHAPE;
    }

    public MapCodec<HangingMossBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return this.canPlaceAt(world, pos);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (!this.canPlaceAt(world, pos)) {
            world.scheduleTick(pos, this, 1);
        }
        return state.setValue(TIP, !world.getBlockState(pos.below()).is(this));
    }
}
