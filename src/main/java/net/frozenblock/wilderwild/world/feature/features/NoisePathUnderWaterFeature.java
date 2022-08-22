package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import net.frozenblock.api.mathematics.EasyNoiseSampler;
import net.frozenblock.wilderwild.world.feature.features.config.PathFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import java.util.Iterator;

public class NoisePathUnderWaterFeature extends Feature<PathFeatureConfig> {
    public NoisePathUnderWaterFeature(Codec<PathFeatureConfig> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<PathFeatureConfig> context) {
        boolean generated = false;
        PathFeatureConfig config = context.config();
        BlockPos blockPos = context.origin();
        WorldGenLevel world = context.level();
        EasyNoiseSampler.setSeed(world.getSeed());
        ImprovedNoise sampler = config.noise == 1 ? EasyNoiseSampler.perlinSimple : config.noise == 2 ? EasyNoiseSampler.perlinAtomic : config.noise == 3 ? EasyNoiseSampler.perlinBlocking : EasyNoiseSampler.perlinXoro;
        BlockPos.MutableBlockPos mutable = blockPos.mutable();
        int bx = mutable.getX();
        int bz = mutable.getZ();
        int by = mutable.getY();
        int radiusSquared = config.radius * config.radius;
        RandomSource random = world.getRandom();

        for (int x = bx - config.radius; x <= bx + config.radius; x++) {
            for (int z = bz - config.radius; z <= bz + config.radius; z++) {
                for (int y = by - config.radius; y <= by + config.radius; y++) {
                    double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y)));
                    if (distance < radiusSquared) {
                        mutable.set(x, y, z);
                        double sample1 = EasyNoiseSampler.sample(sampler, mutable, config.multiplier, config.multiplyY, config.useY);
                        //double sample2 = EasyNoiseSampler.samplePerlinSimplePositive(mutable, config.multiplier, config.multiplyY, config.useY);
                        if (sample1 > config.minThresh && sample1 < config.maxThresh && world.getBlockState(mutable).is(config.replaceable) && isWaterNearby(world, mutable, 2)) {
                            generated = true;
                            world.setBlock(mutable, config.pathBlock.getState(random, mutable), 3);
                        }
                    }
                }
            }
        }
        return generated;
    }

    public static boolean isWaterNearby(WorldGenLevel world, BlockPos blockPos, int x) {
        Iterator<BlockPos> var2 = BlockPos.betweenClosed(blockPos.offset(-x, -x, -x), blockPos.offset(x, x, x)).iterator();
        BlockPos blockPos2;
        do {
            if (!var2.hasNext()) {
                return false;
            }
            blockPos2 = var2.next();
        } while (!world.getBlockState(blockPos2).is(Blocks.WATER));
        return true;
    }

}
