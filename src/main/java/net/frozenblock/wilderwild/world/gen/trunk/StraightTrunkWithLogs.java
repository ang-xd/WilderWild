package net.frozenblock.wilderwild.world.gen.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import java.util.List;
import java.util.function.BiConsumer;

public class StraightTrunkWithLogs extends TrunkPlacer {
    public static final Codec<StraightTrunkWithLogs> CODEC = RecordCodecBuilder.create((instance) -> trunkPlacerParts(instance).and(instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("place_branch_chance").forGetter((trunkPlacer) -> trunkPlacer.logChance), IntProvider.NON_NEGATIVE_CODEC.fieldOf("max_logs").forGetter((trunkPlacer) -> trunkPlacer.maxLogs), IntProvider.NON_NEGATIVE_CODEC.fieldOf("log_height_from_top").forGetter((trunkPlacer) -> trunkPlacer.logHeightFromTop), IntProvider.NON_NEGATIVE_CODEC.fieldOf("extra_branch_length").forGetter((trunkPlacer) -> trunkPlacer.extraBranchLength))).apply(instance, StraightTrunkWithLogs::new));

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

    protected TrunkPlacerType<?> type() {
        return WilderWild.STRAIGHT_TRUNK_WITH_LOGS_PLACER_TYPE;
    }

    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, int height, BlockPos startPos, TreeConfiguration config) {
        setDirtAt(world, replacer, random, startPos.below(), config);
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        int maxLogs = this.maxLogs.sample(random);
        int logHeightFromTop = this.logHeightFromTop.sample(random);
        int placedLogs = 0;
        for (int i = 0; i < height; ++i) {
            int j = startPos.getY() + i;
            if (this.placeLog(world, replacer, random, mutable.set(startPos.getX(), j, startPos.getZ()), config)
                    && i < height - 1 && random.nextFloat() < this.logChance && placedLogs < maxLogs && (height - 4) - i <= logHeightFromTop) {
                Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                this.generateExtraBranch(world, replacer, random, config, mutable, j, direction, this.extraBranchLength.sample(random));
                ++placedLogs;
            }
            if (i == height - 1) {
                list.add(new FoliagePlacer.FoliageAttachment(mutable.set(startPos.getX(), j + 1, startPos.getZ()), 0, false));
            }
        }

        return list;
    }

    private void generateExtraBranch(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, TreeConfiguration config, BlockPos.MutableBlockPos pos, int yOffset, Direction direction, int length) {
        int j = pos.getX();
        int k = pos.getZ();
        for (int l = 0; l < length; ++l) {
            j += direction.getStepX();
            k += direction.getStepZ();
            if (TreeFeature.validTreePos(world, pos.set(j, yOffset, k))) {
                if (config.trunkProvider.getState(random, pos.set(j, yOffset, k)).hasProperty(BlockStateProperties.AXIS)) {
                    Direction.Axis axis = direction.getStepX() != 0 ? Direction.Axis.X : Direction.Axis.Z;
                    replacer.accept(pos.set(j, yOffset, k), config.trunkProvider.getState(random, pos.set(j, yOffset, k)).setValue(BlockStateProperties.AXIS, axis));
                } else {
                    this.placeLog(world, replacer, random, pos.set(j, yOffset, k), config);
                }
            }
        }
    }

}