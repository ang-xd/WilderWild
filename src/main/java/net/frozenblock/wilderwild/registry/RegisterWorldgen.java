package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.world.feature.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.MiscPlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

import static net.minecraft.world.biome.OverworldBiomeCreator.createJungle;
import static net.minecraft.world.biome.OverworldBiomeCreator.createSwamp;

public final class RegisterWorldgen {
    public static final RegistryKey<Biome> MIXED_FOREST = register("mixed_forest");
    public static final RegistryKey<Biome> CYPRESS_WETLANDS = register("cypress_wetlands");

    public static void registerWorldGen() {
        WilderWild.logWild("Registering Biomes for", WilderWild.UNSTABLE_LOGGING);
        BuiltinRegistries.add(BuiltinRegistries.BIOME, MIXED_FOREST, createMixedForest());
        BuiltinRegistries.add(BuiltinRegistries.BIOME, CYPRESS_WETLANDS, createCypressWetlands());
    }

    private static RegistryKey<Biome> register(String name) {
        return RegistryKey.of(Registry.BIOME_KEY, WilderWild.id(name));
    }

    public static Biome createMixedForest() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        DefaultBiomeFeatures.addPlainsMobs(builder);
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 5, 4, 4));
        GenerationSettings.Builder builder2 = new GenerationSettings.Builder();
        addMixedForestFeatures(builder2);
        MusicSound musicSound = MusicType.createIngameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS);
        return new Biome.Builder()
                .precipitation(Biome.Precipitation.RAIN)
                .temperature(0.7F)
                .downfall(0.7F).effects(
                        new BiomeEffects.Builder()
                                .waterColor(4159204)
                                .waterFogColor(329011)
                                .fogColor(12638463)
                                .skyColor(createJungle().getSkyColor())
                                .foliageColor(5879634)
                                .grassColor(8437607)
                                .moodSound(BiomeMoodSound.CAVE)
                                .music(musicSound).build())
                .spawnSettings(builder.build())
                .generationSettings(builder2.build())
                .build();
    }

    public static Biome createCypressWetlands() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        addCypressWetlandsMobs(builder);
        GenerationSettings.Builder builder2 = new GenerationSettings.Builder();
        addCypressWetlandsFeatures(builder2);
        MusicSound musicSound = MusicType.createIngameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS);
        return new Biome.Builder()
                .precipitation(Biome.Precipitation.RAIN)
                .temperature(0.6F)
                .downfall(0.7F)
                .effects(
                        new BiomeEffects.Builder()
                                .waterColor(4552818)
                                .waterFogColor(4552818)
                                .fogColor(12638463)
                                .skyColor(createSwamp().getSkyColor())
                                .foliageColor(5877296)
                                .grassColor(7979098)
                                .moodSound(BiomeMoodSound.CAVE)
                                .music(musicSound).build())
                .spawnSettings(builder.build())
                .generationSettings(builder2.build())
                .build();
    }

    public static void addCypressPaths(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_SAND_PATH);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_GRAVEL_PATH);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_CLAY_PATH);
    }

    public static void addCypressWetlandsFeatures(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.DENSE_FERN_PLACED);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.DENSE_TALL_GRASS_PLACED);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.SEAGRASS_CYPRESS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.SEEDING_DANDELION_CYPRESS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.MILKWEED_CYPRESS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_FOREST_FLOWERS);
        //builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_CYPRESS_ROOTS);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.CYPRESS_WETLANDS_TREES);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.CYPRESS_WETLANDS_TREES_WATER);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_CYPRESS_PLACED);
        addCypressPaths(builder);
        addBasicFeatures(builder, CYPRESS_WETLANDS);
        addCypressVegetation(builder);
    }

    public static void addCypressVegetation(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_SUGAR_CANE_SWAMP);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_PUMPKIN);
    }

    public static void addMixedForestFeatures(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.SEEDING_DANDELION_MIXED);
        builder.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_PATH_5);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.MIXED_MUSHROOMS_PLACED);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.MIXED_TREES);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_TREES_MIXED_PLACED);
        addBasicFeatures(builder, MIXED_FOREST);
        DefaultBiomeFeatures.addForestFlowers(builder);
        DefaultBiomeFeatures.addDefaultOres(builder);
        DefaultBiomeFeatures.addDefaultDisks(builder);
    }

    private static void addBasicFeatures(GenerationSettings.Builder builder, RegistryKey<Biome> biome) {
        DefaultBiomeFeatures.addLandCarvers(builder);
        DefaultBiomeFeatures.addAmethystGeodes(builder);
        DefaultBiomeFeatures.addDungeons(builder);
        DefaultBiomeFeatures.addMineables(builder);
        if (biome == CYPRESS_WETLANDS) {
            builder.feature(GenerationStep.Feature.FLUID_SPRINGS, MiscPlacedFeatures.SPRING_WATER);
        } else {
            DefaultBiomeFeatures.addSprings(builder);
        }
        DefaultBiomeFeatures.addFrozenTopLayer(builder);
    }

    public static void addCypressWetlandsMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.COD, 5, 2, 6));
        //builder.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(EntityType.TADPOLE, 6, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FROG, 12, 4, 5));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PIG, 3, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.CHICKEN, 4, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.COW, 6, 4, 4));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 10, 4, 4));
        builder.spawn(WilderWild.FIREFLIES, new SpawnSettings.SpawnEntry(RegisterEntities.FIREFLY, 1, 2, 6));
    }

}