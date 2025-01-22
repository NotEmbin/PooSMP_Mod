package embin.poosmp.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class NewMossBlock extends Block implements Fertilizable {
    public static final MapCodec<NewMossBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(RegistryKey.createCodec(RegistryKeys.CONFIGURED_FEATURE).fieldOf("feature").forGetter((block) -> block.feature), createSettingsCodec()).apply(instance, NewMossBlock::new));
    private final RegistryKey<ConfiguredFeature<?, ?>> feature;

    public NewMossBlock(RegistryKey<ConfiguredFeature<?, ?>> feature, Settings settings) {
        super(settings);
        this.feature = feature;
    }

    public MapCodec<NewMossBlock> getCodec() {
        return CODEC;
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return world.getBlockState(pos.up()).isAir();
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.getRegistryManager().getOptional(RegistryKeys.CONFIGURED_FEATURE).flatMap((registry) -> registry.getEntry(this.feature)).ifPresent((entry) -> (entry.value()).generate(world, world.getChunkManager().getChunkGenerator(), random, pos.up()));
    }

    @Override
    public FertilizableType getFertilizableType() {
        return FertilizableType.NEIGHBOR_SPREADER;
    }
}
