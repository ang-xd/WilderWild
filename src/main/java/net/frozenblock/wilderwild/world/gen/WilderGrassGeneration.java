package net.frozenblock.wilderwild.world.gen;

import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.minecraft.tag.BiomeTags;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;

public final class WilderGrassGeneration {
    public static void generateGrassForest() {
        BiomeModifications.addFeature(BiomeSelectors.isIn(BiomeTags.IS_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_GRASS_PLACED.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.isIn(BiomeTags.IS_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TALL_GRASS.getKey().orElseThrow());

    }

    public static void generateGrassTaiga() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_GRASS_PLACED.getKey().orElseThrow());
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TALL_GRASS.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_HILLS),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_RARE_GRASS_PLACED.getKey().orElseThrow());
    }
}