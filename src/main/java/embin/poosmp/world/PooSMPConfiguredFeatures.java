package embin.poosmp.world;

import embin.poosmp.block.PooSMPBlocks;
import embin.poosmp.util.Id;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import java.util.OptionalInt;

public class PooSMPConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_OAK_KEY = registerKey("pale_oak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_MOSS_VEGETATION = registerKey("pale_moss_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_MOSS_PATCH_BONEMEAL = registerKey("pale_moss_patch_bonemeal");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<ConfiguredFeature<?, ?>> registryEntryLookup = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, PALE_OAK_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(PooSMPBlocks.PALE_OAK_LOG),
                new DarkOakTrunkPlacer(6, 2, 1),
                BlockStateProvider.simple(PooSMPBlocks.PALE_OAK_LEAVES),
                new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
                new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())
        ).ignoreVines().build());
        register(context, PALE_MOSS_VEGETATION, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                new WeightedStateProvider(DataPool.<BlockState>builder()
                        .add(PooSMPBlocks.PALE_MOSS_CARPET.defaultBlockState(), 25)
                        .add(Blocks.SHORT_GRASS.defaultBlockState(), 25)
                        .add(Blocks.TALL_GRASS.defaultBlockState(), 10))
        ));
        register(context, PALE_MOSS_PATCH_BONEMEAL, Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(BlockTags.MOSS_REPLACEABLE,
                        BlockStateProvider.simple(PooSMPBlocks.PALE_MOSS_BLOCK),
                        PlacementUtils.inlinePlaced(registryEntryLookup.getOrThrow(PALE_MOSS_VEGETATION),
                                new PlacementModifier[0]), CaveSurface.FLOOR,
                        ConstantInt.of(1), 0.0F, 5, 0.6F,
                        UniformInt.of(1, 2), 0.75F)
        );
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Id.of(name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
        context.register(key, new ConfiguredFeature<>(feature, config));
    }
}
