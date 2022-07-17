package net.frozenblock.wilderwild.world.gen;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.minecraft.world.biome.BiomeKeys;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;

public class WilderSpawns {

    public static void addFirefliesSwamps() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP),
                WilderWild.FIREFLIES, RegisterEntities.FIREFLY, 12, 2, 4);
    }

    public static void addFirefliesOthers() {
        //TODO: Decide which biomes fireflies should spawn in, if any at all (probably not honestly)
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE),
                WilderWild.FIREFLIES, RegisterEntities.FIREFLY, 5, 1, 2);
    }

}