package embin.poosmp.world;

import embin.poosmp.block.PooSMPBlocks;
import embin.poosmp.util.Id;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.ThreeLayersFeatureSize;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;

import java.util.OptionalInt;

public class PooSMPConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> PALE_OAK_KEY = registerKey("pale_oak");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PALE_MOSS_VEGETATION = registerKey("pale_moss_vegetation");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PALE_MOSS_PATCH_BONEMEAL = registerKey("pale_moss_patch_bonemeal");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        register(context, PALE_OAK_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(PooSMPBlocks.PALE_OAK_LOG),
                new DarkOakTrunkPlacer(6, 2, 1),
                BlockStateProvider.of(PooSMPBlocks.PALE_OAK_LEAVES),
                new DarkOakFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0)),
                new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())
        ).ignoreVines().build());
        register(context, PALE_MOSS_VEGETATION, Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                        .add(PooSMPBlocks.PALE_MOSS_CARPET.getDefaultState(), 25)
                        .add(Blocks.SHORT_GRASS.getDefaultState(), 25)
                        .add(Blocks.TALL_GRASS.getDefaultState(), 10))
        ));
        register(context, PALE_MOSS_PATCH_BONEMEAL, Feature.VEGETATION_PATCH,
                new VegetationPatchFeatureConfig(BlockTags.MOSS_REPLACEABLE,
                        BlockStateProvider.of(PooSMPBlocks.PALE_MOSS_BLOCK),
                        PlacedFeatures.createEntry(registryEntryLookup.getOrThrow(PALE_MOSS_VEGETATION),
                                new PlacementModifier[0]), VerticalSurfaceType.FLOOR,
                        ConstantIntProvider.create(1), 0.0F, 5, 0.6F,
                        UniformIntProvider.create(1, 2), 0.75F)
        );
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Id.of(name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
        context.register(key, new ConfiguredFeature<>(feature, config));
    }
}
