package net.frozenblock.wilderwild.world.gen.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class StraightTrunkWithLogs extends TrunkPlacer {
    public static final Codec<StraightTrunkWithLogs> CODEC = RecordCodecBuilder.create((instance) -> fillTrunkPlacerFields(instance).and(instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("place_branch_chance").forGetter((trunkPlacer) -> trunkPlacer.logChance), IntProvider.NON_NEGATIVE_CODEC.fieldOf("max_logs").forGetter((trunkPlacer) -> trunkPlacer.maxLogs), IntProvider.NON_NEGATIVE_CODEC.fieldOf("log_height_from_top").forGetter((trunkPlacer) -> trunkPlacer.logHeightFromTop), IntProvider.NON_NEGATIVE_CODEC.fieldOf("extra_branch_length").forGetter((trunkPlacer) -> trunkPlacer.extraBranchLength))).apply(instance, StraightTrunkWithLogs::new));

    private final IntProvider extraBranchLength;
    private final float logChance;
    private final IntProvider maxLogs;
    private final IntProvider logHeightFromTop;

    public StraightTrunkWithLogs(int baseHeight, int firstRandomHeight, int secondRandomHeight, float logChance, IntProvider maxLogs, IntProvider logHeightFromTop, IntProvider extraBranchLength) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
        this.logChance = logChance;
        this.maxLogs = maxLogs;
        this.logHeightFromTop = logHeightFromTop;
        this.extraBranchLength = extraBranchLength;
    }

    protected TrunkPlacerType<?> getType() {
        return WilderWild.STRAIGHT_TRUNK_WITH_LOGS_PLACER_TYPE;
    }

    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        setToDirt(world, replacer, random, startPos.down(), config);
        List<FoliagePlacer.TreeNode> list = Lists.newArrayList();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int maxLogs = this.maxLogs.get(random);
        int logHeightFromTop = this.logHeightFromTop.get(random);
        int placedLogs = 0;
        for (int i = 0; i < height; ++i) {
            int j = startPos.getY() + i;
            if (this.getAndSetState(world, replacer, random, mutable.set(startPos.getX(), j, startPos.getZ()), config)
                    && i < height - 1 && random.nextFloat() < this.logChance && placedLogs < maxLogs && (height-4)-i<=logHeightFromTop) {
                Direction direction = Direction.Type.HORIZONTAL.random(random);
                this.generateExtraBranch(world, replacer, random, config, mutable, j, direction, this.extraBranchLength.get(random));
                ++placedLogs;
            }
            if (i == height - 1) {
                list.add(new FoliagePlacer.TreeNode(mutable.set(startPos.getX(), j + 1, startPos.getZ()), 0, false));
            }
        }

        return list;
    }

    private void generateExtraBranch(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, BlockPos.Mutable pos, int yOffset, Direction direction, int length) {
        int j = pos.getX();
        int k = pos.getZ();
        for (int l = 0; l < length; ++l) {
            j += direction.getOffsetX();
            k += direction.getOffsetZ();
            if (TreeFeature.canReplace(world, pos.set(j, yOffset, k))) {
                if (config.trunkProvider.getBlockState(random, pos.set(j, yOffset, k)).contains(Properties.AXIS)) {
                    Direction.Axis axis = direction.getOffsetX()!=0 ? Direction.Axis.X : Direction.Axis.Z;
                    replacer.accept(pos.set(j, yOffset, k), config.trunkProvider.getBlockState(random, pos.set(j, yOffset, k)).with(Properties.AXIS, axis));
                } else {
                    this.getAndSetState(world, replacer, random, pos.set(j, yOffset, k), config);
                }
            }
        }
    }

}