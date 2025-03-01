package net.frozenblock.wilderwild.world.feature;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.misc.FlowerColor;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.world.feature.features.config.ColumnWithDiskFeatureConfig;
import net.frozenblock.wilderwild.world.feature.features.config.NematocystFeatureConfig;
import net.frozenblock.wilderwild.world.feature.features.config.ShelfFungusFeatureConfig;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public final class WilderConfiguredFeatures {
    //FALLEN TREES
    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_TREES_MIXED =
            register("fallen_trees_mixed", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of((new WeightedPlacedFeature(WilderTreePlaced.FALLEN_SPRUCE_CHECKED, 0.4F)),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_FALLEN_BIRCH_CHECKED, 0.3F)), WilderTreePlaced.NEW_FALLEN_OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_BIRCH =
            register("fallen_birch", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_FALLEN_BIRCH_CHECKED, 1.0F)), WilderTreePlaced.NEW_FALLEN_BIRCH_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_SPRUCE =
            register("fallen_spruce", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_SPRUCE_CHECKED, 1.0F)), WilderTreePlaced.FALLEN_SPRUCE_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_SPRUCE_AND_OAK =
            register("fallen_spruce_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_SPRUCE_CHECKED, 0.55F)), WilderTreePlaced.NEW_FALLEN_OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_FALLEN_BIRCH_AND_OAK =
            register("fallen_birch_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_FALLEN_BIRCH_CHECKED, 0.35F)), WilderTreePlaced.NEW_FALLEN_OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_FALLEN_CYPRESS_AND_OAK =
            register("fallen_cypress_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_FALLEN_OAK_CHECKED, 0.35F)), WilderTreePlaced.NEW_FALLEN_CYPRESS_CHECKED));

    //TREES
    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_TREES_PLAINS =
            register("trees_plains", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.NEW_FANCY_OAK_BEES_0004), 0.33333334F),
                            new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.FANCY_DYING_OAK_BEES_0004), 0.035F),
                            new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.SHORT_OAK), 0.169F)),
                            PlacementUtils.inlinePlaced(WilderTreeConfigured.NEW_OAK_BEES_0004)));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_TREES_BIRCH_AND_OAK =
            register("trees_birch_and_oak", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_SHORT_BIRCH_BEES_0004, 0.2F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.04F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_FANCY_OAK_BEES_0004, 0.26F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004, 0.055F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.04F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED, 0.155F)), WilderTreePlaced.NEW_OAK_BEES_0004));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_TREES_BIRCH =
            register("trees_birch", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_SHORT_BIRCH_BEES_0004, 0.065F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.012F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.035F)), WilderTreePlaced.NEW_BIRCH_BEES_0004));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_TREES_BIRCH_TALL =
            register("trees_birch_tall", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_SHORT_BIRCH_BEES_0004, 0.002F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.0001F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SUPER_BIRCH, 0.032F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_BIRCH_BEES_0004, 0.02F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH, 0.017F)), WilderTreePlaced.NEW_SUPER_BIRCH_BEES_0004));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_TREES_FLOWER_FOREST =
            register("trees_flower_forest", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_SHORT_BIRCH_BEES_0004, 0.2F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.035F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.05F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004, 0.063F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_FANCY_OAK_BEES_0004, 0.205F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED, 0.095F)), WilderTreePlaced.NEW_OAK_BEES_0004));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> MIXED_TREES =
            register("mixed_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_SPRUCE_CHECKED, 0.39F),
                            new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.086F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED, 0.02F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_SPRUCE_SHORT_CHECKED, 0.13F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_FANCY_OAK_BEES_0004, 0.37F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004, 0.025F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.01F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.01F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED, 0.13F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH, 0.325F)), WilderTreePlaced.NEW_OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_DARK_FOREST_VEGETATION =
            register("dark_forest_vegetation", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(TreeFeatures.HUGE_BROWN_MUSHROOM), 0.025F),
                            new WeightedPlacedFeature(PlacementUtils.inlinePlaced(TreeFeatures.HUGE_RED_MUSHROOM), 0.05F),
                            new WeightedPlacedFeature(TreePlacements.DARK_OAK_CHECKED, 0.55F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_DARK_OAK_CHECKED, 0.075F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH, 0.2F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH, 0.015F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_TALL_DARK_OAK_CHECKED, 0.35F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_TALL_DARK_OAK_CHECKED, 0.048F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED, 0.02F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.012F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_FANCY_OAK_CHECKED, 0.185F)), WilderTreePlaced.NEW_OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_TREES_TAIGA =
            register("trees_taiga", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.33333334F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED, 0.075F)), WilderTreePlaced.NEW_SPRUCE_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_SHORT_TREES_TAIGA =
            register("short_trees_taiga", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_SPRUCE_SHORT_CHECKED, 0.33333334F)), WilderTreePlaced.NEW_SPRUCE_SHORT_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_TREES_OLD_GROWTH_PINE_TAIGA =
            register("trees_old_growth_pine_taiga", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED, 0.025641026F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_MEGA_FUNGUS_PINE_CHECKED, 0.028F),
                            new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_PINE_CHECKED, 0.30769232F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED, 0.045F),
                            new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.33333334F)), WilderTreePlaced.NEW_SPRUCE_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_TREES_OLD_GROWTH_SPRUCE_TAIGA =
            register("trees_old_growth_spruce_taiga", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED, 0.33333334F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED, 0.075F),
                            new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED, 0.33333334F)), WilderTreePlaced.NEW_SPRUCE_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_TREES_GROVE =
            register("trees_grove", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_ON_SNOW, 0.33333334F)), WilderTreePlaced.NEW_SPRUCE_ON_SNOW));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_TREES_WINDSWEPT_HILLS =
            register("trees_windswept_hills", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_SPRUCE_CHECKED, 0.666F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED, 0.01F),
                            new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED, 0.02F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_FANCY_OAK_CHECKED, 0.1F)), WilderTreePlaced.NEW_OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> NEW_MEADOW_TREES =
            register("meadow_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.NEW_FANCY_OAK_BEES, 0.5F)), WilderTreePlaced.NEW_SUPER_BIRCH_BEES));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> SAVANNA_TREES =
            register("savanna_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(TreePlacements.ACACIA_CHECKED, 0.8F),
                            new WeightedPlacedFeature(WilderTreePlaced.BAOBAB, 0.062F),
                            new WeightedPlacedFeature(WilderTreePlaced.BAOBAB_TALL, 0.035F)), WilderTreePlaced.NEW_OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> WINDSWEPT_SAVANNA_TREES =
            register("windswept_savanna_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(TreePlacements.ACACIA_CHECKED, 0.8F)), WilderTreePlaced.NEW_OAK_CHECKED));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> CYPRESS_WETLANDS_TREES =
            register("cypress_wetlands_trees", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.CYPRESS, 0.37F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_CYPRESS, 0.25F),
                            new WeightedPlacedFeature(WilderTreePlaced.SWAMP_CYPRESS, 0.81F),
                            new WeightedPlacedFeature(WilderTreePlaced.NEW_OAK_CHECKED, 0.1F)), WilderTreePlaced.FUNGUS_CYPRESS));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> CYPRESS_WETLANDS_TREES_SAPLING =
            register("cypress_wetlands_trees_sapling", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.CYPRESS, 0.4F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_CYPRESS, 0.15F),
                            new WeightedPlacedFeature(WilderTreePlaced.SWAMP_CYPRESS, 0.81F)),
                            WilderTreePlaced.FUNGUS_CYPRESS));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> CYPRESS_WETLANDS_TREES_WATER =
            register("cypress_wetlands_trees_water", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.CYPRESS, 0.2F),
                            new WeightedPlacedFeature(WilderTreePlaced.SHORT_CYPRESS, 0.1F),
                            new WeightedPlacedFeature(WilderTreePlaced.SWAMP_CYPRESS, 0.85F)), WilderTreePlaced.FUNGUS_CYPRESS));

    //FLOWERS
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> SEEDING_DANDELION =
            register("seeding_dandelion", Feature.FLOWER,
                    FeatureUtils.simpleRandomPatchConfiguration(48, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.SEEDING_DANDELION)))));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> CARNATION =
            register("carnation", Feature.FLOWER,
                    FeatureUtils.simpleRandomPatchConfiguration(48, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.CARNATION)))));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> DATURA =
            register("datura", Feature.FLOWER,
                    FeatureUtils.simpleRandomPatchConfiguration(64, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.DATURA)))));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> NEW_FLOWER_PLAIN =
            register("flower_plain", Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(new NoiseThresholdProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0), 0.005F, -0.8F, 0.33333334F, Blocks.DANDELION.defaultBlockState(), List.of(Blocks.ORANGE_TULIP.defaultBlockState(), Blocks.RED_TULIP.defaultBlockState(), Blocks.PINK_TULIP.defaultBlockState(), Blocks.WHITE_TULIP.defaultBlockState()), List.of(RegisterBlocks.SEEDING_DANDELION.defaultBlockState(), Blocks.POPPY.defaultBlockState(), Blocks.AZURE_BLUET.defaultBlockState(), Blocks.OXEYE_DAISY.defaultBlockState(), Blocks.CORNFLOWER.defaultBlockState()))))));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> MILKWEED =
            register("milkweed", Feature.FLOWER,
                    FeatureUtils.simpleRandomPatchConfiguration(32, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.MILKWEED)))));

    public static final SimpleWeightedRandomList<BlockState> GLORY_OF_THE_SNOW_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.BLUE), 3).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.PURPLE), 3).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.PINK), 2).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.WHITE), 1).build();
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> GLORY_OF_THE_SNOW =
            register("glory_of_the_snow", Feature.FLOWER,
                    FeatureUtils.simpleRandomPatchConfiguration(64, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(new WeightedStateProvider(GLORY_OF_THE_SNOW_POOL)))));

    //VEGETATION
    public static final SimpleWeightedRandomList<BlockState> LARGE_FERN_AND_GRASS_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.TALL_GRASS.defaultBlockState(), 3).add(Blocks.LARGE_FERN.defaultBlockState(), 3).build();
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> LARGE_FERN_AND_GRASS =
            register("large_fern_and_grass", Feature.RANDOM_PATCH,
                    FeatureUtils.simpleRandomPatchConfiguration(36, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(new WeightedStateProvider(LARGE_FERN_AND_GRASS_POOL)))));

    public static final Holder<ConfiguredFeature<MultifaceGrowthConfiguration, ?>> POLLEN_CONFIGURED =
            register("pollen", Feature.MULTIFACE_GROWTH, new MultifaceGrowthConfiguration((MultifaceBlock) RegisterBlocks.POLLEN_BLOCK, 20, true, true, true, 0.5F, HolderSet.direct(Block::builtInRegistryHolder, Blocks.GRASS_BLOCK, Blocks.BIRCH_LEAVES, Blocks.OAK_LEAVES, Blocks.OAK_LOG)));

    public static final Holder<ConfiguredFeature<ShelfFungusFeatureConfig, ?>> BROWN_SHELF_FUNGUS_CONFIGURED =
            register("brown_shelf_fungus", WilderWild.SHELF_FUNGUS_FEATURE, new ShelfFungusFeatureConfig((ShelfFungusBlock) RegisterBlocks.BROWN_SHELF_FUNGUS, 20, true, true, true, HolderSet.direct(Block::builtInRegistryHolder, Blocks.MANGROVE_LOG, Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG, RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.MYCELIUM, Blocks.MUSHROOM_STEM, RegisterBlocks.HOLLOWED_SPRUCE_LOG)));

    public static final Holder<ConfiguredFeature<ShelfFungusFeatureConfig, ?>> RED_SHELF_FUNGUS_CONFIGURED =
            register("red_shelf_fungus", WilderWild.SHELF_FUNGUS_FEATURE, new ShelfFungusFeatureConfig((ShelfFungusBlock) RegisterBlocks.RED_SHELF_FUNGUS, 20, true, true, true, HolderSet.direct(Block::builtInRegistryHolder, Blocks.MANGROVE_LOG, Blocks.DARK_OAK_LOG, RegisterBlocks.HOLLOWED_BIRCH_LOG, RegisterBlocks.HOLLOWED_OAK_LOG, Blocks.MYCELIUM, Blocks.MUSHROOM_STEM)));

    public static final Holder<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> CATTAIL =
            register("cattail", WilderWild.CATTAIL_FEATURE, new ProbabilityFeatureConfiguration(0.8F));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_FLOWERED_WATERLILY =
            register("patch_flowered_waterlily", Feature.RANDOM_PATCH,
                    new RandomPatchConfiguration(10, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.FLOWERING_LILY_PAD)))));

    public static final Holder<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> PATCH_ALGAE =
            register("patch_algae", WilderWild.ALGAE_FEATURE, new ProbabilityFeatureConfiguration(0.8F));

    public static final Holder<ConfiguredFeature<ColumnWithDiskFeatureConfig, ?>> TERMITE_CONFIGURED =
            register("termite_mound_baobab", WilderWild.COLUMN_WITH_DISK_FEATURE, new ColumnWithDiskFeatureConfig(RegisterBlocks.TERMITE_MOUND.defaultBlockState().setValue(RegisterProperties.NATURAL, true), UniformInt.of(4, 9), UniformInt.of(3, 7), UniformInt.of(1, 3), HolderSet.direct(Block::builtInRegistryHolder, Blocks.GRASS_BLOCK, Blocks.STONE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.GRANITE), HolderSet.direct(Block::builtInRegistryHolder, Blocks.COARSE_DIRT, Blocks.SAND, Blocks.PACKED_MUD)));

    public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> BLUE_MESOGLEA = register(
            "mesoglea",
            Feature.VEGETATION_PATCH,
            new VegetationPatchConfiguration(
                    BlockTags.LUSH_GROUND_REPLACEABLE,
                    BlockStateProvider.simple(RegisterBlocks.MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
                    PlacementUtils.inlinePlaced(CaveFeatures.DRIPLEAF),
                    CaveSurface.FLOOR,
                    ConstantInt.of(3),
                    0.8F,
                    2,
                    0.04F,
                    UniformInt.of(4, 14),
                    0.7F
            )
    );
    public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> BLUE_MESOGLEA_POOL = register(
            "mesoglea_pool",
            Feature.WATERLOGGED_VEGETATION_PATCH,
            new VegetationPatchConfiguration(
                    BlockTags.LUSH_GROUND_REPLACEABLE,
                    BlockStateProvider.simple(RegisterBlocks.MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
                    PlacementUtils.inlinePlaced(CaveFeatures.DRIPLEAF),
                    CaveSurface.FLOOR,
                    ConstantInt.of(3),
                    0.8F,
                    5,
                    0.04F,
                    UniformInt.of(4, 14),
                    0.7F
            )
    );
    public static final Holder<ConfiguredFeature<RandomBooleanFeatureConfiguration, ?>> JELLYFISH_CAVES_BLUE_MESOGLEA = register(
            "jellyfish_caves_blue_mesoglea",
            Feature.RANDOM_BOOLEAN_SELECTOR,
            new RandomBooleanFeatureConfiguration(
                    PlacementUtils.inlinePlaced(BLUE_MESOGLEA),
                    PlacementUtils.inlinePlaced(BLUE_MESOGLEA_POOL)
            )
    );
    public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> UPSIDE_DOWN_BLUE_MESOGLEA = register(
            "upside_down_blue_mesoglea",
            Feature.VEGETATION_PATCH,
            new VegetationPatchConfiguration(
                    BlockTags.LUSH_GROUND_REPLACEABLE,
                    BlockStateProvider.simple(RegisterBlocks.MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
                    PlacementUtils.inlinePlaced(WilderMiscConfigured.DOWNWARDS_MESOGLEA_PILLAR),
                    CaveSurface.CEILING,
                    ConstantInt.of(3),
                    0.8F,
                    2,
                    0.08F,
                    UniformInt.of(4, 14),
                    0.7F
            )
    );
    public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> PURPLE_MESOGLEA = register(
            "mesoglea_with_dripleaves",
            Feature.VEGETATION_PATCH,
            new VegetationPatchConfiguration(
                    BlockTags.LUSH_GROUND_REPLACEABLE,
                    BlockStateProvider.simple(RegisterBlocks.PURPLE_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
                    PlacementUtils.inlinePlaced(CaveFeatures.DRIPLEAF),
                    CaveSurface.FLOOR,
                    ConstantInt.of(3),
                    0.8F,
                    2,
                    0.04F,
                    UniformInt.of(4, 14),
                    0.7F
            )
    );
    public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> PURPLE_MESOGLEA_POOL = register(
            "purple_mesoglea_pool",
            Feature.WATERLOGGED_VEGETATION_PATCH,
            new VegetationPatchConfiguration(
                    BlockTags.LUSH_GROUND_REPLACEABLE,
                    BlockStateProvider.simple(RegisterBlocks.PURPLE_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
                    PlacementUtils.inlinePlaced(CaveFeatures.DRIPLEAF),
                    CaveSurface.FLOOR,
                    ConstantInt.of(3),
                    0.8F,
                    5,
                    0.04F,
                    UniformInt.of(4, 14),
                    0.7F
            )
    );
    public static final Holder<ConfiguredFeature<RandomBooleanFeatureConfiguration, ?>> JELLYFISH_CAVES_PURPLE_MESOGLEA = register(
            "jellyfish_caves_purple_mesoglea",
            Feature.RANDOM_BOOLEAN_SELECTOR,
            new RandomBooleanFeatureConfiguration(
                    PlacementUtils.inlinePlaced(PURPLE_MESOGLEA),
                    PlacementUtils.inlinePlaced(PURPLE_MESOGLEA_POOL)
            )
    );
    public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> UPSIDE_DOWN_PURPLE_MESOGLEA = register(
            "upside_down_purple_mesoglea",
            Feature.VEGETATION_PATCH,
            new VegetationPatchConfiguration(
                    BlockTags.LUSH_GROUND_REPLACEABLE,
                    BlockStateProvider.simple(RegisterBlocks.PURPLE_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
                    PlacementUtils.inlinePlaced(WilderMiscConfigured.DOWNWARDS_PURPLE_MESOGLEA_PILLAR),
                    CaveSurface.CEILING,
                    ConstantInt.of(3),
                    0.8F,
                    2,
                    0.08F,
                    UniformInt.of(4, 14),
                    0.7F
            )
    );

    public static final Holder<ConfiguredFeature<NematocystFeatureConfig, ?>> PATCH_NEMATOCYST_UP = register("patch_nematocyst_up",
            WilderWild.NEMATOCYST_FEATURE,
            new NematocystFeatureConfig(new NoiseProvider(
                    10L,
                    new NormalNoise.NoiseParameters(0, 1.0),
                    0.3F,
                    List.of(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.UP),
                            RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.UP))),
                    128, 16, 6
            )
    );
    public static final Holder<ConfiguredFeature<NematocystFeatureConfig, ?>> PATCH_NEMATOCYST_DOWN = register("patch_nematocyst_down",
            WilderWild.NEMATOCYST_FEATURE,
            new NematocystFeatureConfig(new NoiseProvider(
                    10L,
                    new NormalNoise.NoiseParameters(0, 1.0),
                    0.3F,
                    List.of(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN),
                            RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN))),
                    64, 16, 6
            )
    );
    public static final Holder<ConfiguredFeature<NematocystFeatureConfig, ?>> PATCH_NEMATOCYST_NORTH = register("patch_nematocyst_north",
            WilderWild.NEMATOCYST_FEATURE,
            new NematocystFeatureConfig(new NoiseProvider(
                    10L,
                    new NormalNoise.NoiseParameters(0, 1.0),
                    0.3F,
                    List.of(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH),
                            RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH))),
                    32, 8, 8
            )
    );
    public static final Holder<ConfiguredFeature<NematocystFeatureConfig, ?>> PATCH_NEMATOCYST_SOUTH = register("patch_nematocyst_south",
            WilderWild.NEMATOCYST_FEATURE,
            new NematocystFeatureConfig(new NoiseProvider(
                    10L,
                    new NormalNoise.NoiseParameters(0, 1.0),
                    0.3F,
                    List.of(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.SOUTH),
                            RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.SOUTH))),
                    32, 8, 8
            )
    );
    public static final Holder<ConfiguredFeature<NematocystFeatureConfig, ?>> PATCH_NEMATOCYST_EAST = register("patch_nematocyst_east",
            WilderWild.NEMATOCYST_FEATURE,
            new NematocystFeatureConfig(new NoiseProvider(
                    10L,
                    new NormalNoise.NoiseParameters(0, 1.0),
                    0.3F,
                    List.of(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.EAST),
                            RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.EAST))),
                    32, 8, 8
            )
    );
    public static final Holder<ConfiguredFeature<NematocystFeatureConfig, ?>> PATCH_NEMATOCYST_WEST = register("patch_nematocyst_west",
            WilderWild.NEMATOCYST_FEATURE,
            new NematocystFeatureConfig(new NoiseProvider(
                    10L,
                    new NormalNoise.NoiseParameters(0, 1.0),
                    0.3F,
                    List.of(RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.WEST),
                            RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.WEST))),
                    32, 8, 8
            )
    );

    public static void registerConfiguredFeatures() {
        WilderWild.logWild("Registering WilderConfiguredFeatures for", true);
    }

    private static RandomPatchConfiguration createRandomPatchFeatureConfig(BlockStateProvider block, int tries) {
        return FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(block)));
    }

    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> register(String id, Feature<NoneFeatureConfiguration> feature) {
        return register(id, feature, FeatureConfiguration.NONE);
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(@NotNull String id, F feature, @NotNull FC config) {
        return registerExact(BuiltinRegistries.CONFIGURED_FEATURE, id, new ConfiguredFeature<>(feature, config));
    }

    public static <V extends T, T> Holder<V> registerExact(Registry<T> registry, String id, V value) {
        return (Holder<V>) BuiltinRegistries.register(registry, WilderWild.id(id), value);
    }
}
