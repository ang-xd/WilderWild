package net.frozenblock.wilderwild.world.gen;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;

public final class WilderSpawns {

    public static void addFireflies() {
        BiomeModifications.addSpawn(BiomeSelectors.isIn(WilderBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY),
                WilderWild.FIREFLIES, RegisterEntities.FIREFLY, 12, 2, 4);

        BiomeModifications.addSpawn(BiomeSelectors.isIn(WilderBiomeTags.FIREFLY_SPAWNABLE_CAVE),
                WilderWild.FIREFLIES, RegisterEntities.FIREFLY, 5, 2, 4);

        BiomeModifications.addSpawn(BiomeSelectors.isIn(WilderBiomeTags.FIREFLY_SPAWNABLE),
                WilderWild.FIREFLIES, RegisterEntities.FIREFLY, 5, 1, 2);
    }

}