package net.frozenblock.wilderwild.block;

import net.frozenblock.lib.mathematics.EasyNoiseSampler;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class OsseousSculkBlock extends RotatedPillarBlock implements SculkBehaviour {

    public OsseousSculkBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(HEIGHT_LEFT, 0).setValue(AXIS, Direction.Axis.Y).setValue(UPSIDEDOWN, false).setValue(TOTAL_HEIGHT, 0));
    }

    public void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
        if (dropExperience) {
            this.tryDropExperience(level, pos, stack, ConstantInt.of(3));
        }
    }

    public static Direction getDir(Direction.Axis axis, boolean UpsideDown) {
        if (axis != null) {
            if (axis.isHorizontal()) {
                if (axis == Direction.Axis.X) {
                    return Math.random() > 0.5 ? Direction.EAST : Direction.WEST;
                }
                if (axis == Direction.Axis.Z) {
                    return Math.random() > 0.5 ? Direction.NORTH : Direction.SOUTH;
                }
            }
        }
        return UpsideDown ? Direction.DOWN : Direction.UP;
    }

    public static Direction.Axis getAxis(BlockPos pos) {
        return EasyNoiseSampler.sample(EasyNoiseSampler.perlinSimple, pos, 0.7, false, false) > 0 ? Direction.Axis.X : Direction.Axis.Z;
    }

    public static void convertToSculk(LevelAccessor level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.is(RegisterBlocks.OSSEOUS_SCULK)) {
            Direction.Axis axis = state.getValue(AXIS);
            Direction dir = getDir(axis, state.getValue(UPSIDEDOWN));
            if (level.getBlockState(pos.relative(dir)).is(RegisterBlocks.OSSEOUS_SCULK)) {
                BlockPos newPos = pos.relative(dir);
                for (Direction direction : UPDATE_SHAPE_ORDER) {
                    BlockState stateReplace = level.getBlockState(newPos.relative(direction));
                    BlockState stateSetTo = null;
                    if (stateReplace.is(Blocks.SCULK_VEIN)) {
                        stateSetTo = stateReplace.setValue(MultifaceBlock.getFaceProperty(direction.getOpposite()), true);
                    }
                    if (stateReplace.isAir()) {
                        stateSetTo = Blocks.SCULK_VEIN.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(direction.getOpposite()), true);
                    }
                    if (stateReplace == Blocks.WATER.defaultBlockState()) {
                        stateSetTo = Blocks.SCULK_VEIN.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(direction.getOpposite()), true).setValue(BlockStateProperties.WATERLOGGED, true);
                    }
                    if (stateSetTo != null) {
                        level.setBlock(newPos.relative(direction), stateSetTo, 3);
                    }
                }
                level.setBlock(pos, Blocks.SCULK.defaultBlockState(), 3);
            }
        }
    }

    public static final IntegerProperty HEIGHT_LEFT = RegisterProperties.PILLAR_HEIGHT_LEFT;
    public static final BooleanProperty UPSIDEDOWN = RegisterProperties.UPSIDE_DOWN;
    public static final IntegerProperty TOTAL_HEIGHT = RegisterProperties.TOTAL_HEIGHT;

    @Override
    public int attemptUseCharge(SculkSpreader.ChargeCursor cursor, LevelAccessor level, BlockPos catalystPos, RandomSource random, SculkSpreader spreadManager, boolean shouldConvertToBlock) {
        if (spreadManager.isWorldGeneration()) {
            worldGenSpread(cursor.getPos(), level, random);
            return cursor.getCharge();
        }
        int i = cursor.getCharge();
        int j = 1;
        if (i != 0 && random.nextInt(2) == 0) {
            BlockPos blockPos = cursor.getPos();
            boolean bl = blockPos.closerThan(catalystPos, spreadManager.noGrowthRadius());
            if (!bl) {
                int pillarHeight = level.getBlockState(blockPos).getValue(OsseousSculkBlock.HEIGHT_LEFT);
                BlockPos topPos = getTop(level, blockPos, pillarHeight);
                if (topPos != null) {
                    BlockState state = level.getBlockState(topPos);
                    pillarHeight = state.getValue(OsseousSculkBlock.HEIGHT_LEFT);
                    Direction direction = getDir(state.getValue(AXIS), state.getValue(UPSIDEDOWN));
                    if (level.getBlockState(topPos.relative(direction)).isAir() || level.getBlockState(topPos.relative(direction)).getBlock() == Blocks.SCULK_VEIN) {
                        BlockState blockState = RegisterBlocks.OSSEOUS_SCULK.defaultBlockState().setValue(HEIGHT_LEFT, Math.max(0, pillarHeight - 1));

                        if (pillarHeight == 1 && !state.getValue(UPSIDEDOWN) && state.getValue(TOTAL_HEIGHT) > 0) {
                            if (EasyNoiseSampler.localRandom.nextInt(Math.max(1, state.getValue(TOTAL_HEIGHT) / 2)) <= 1) {
                                if (random.nextInt(11) == 0) {
                                    blockState = Blocks.SCULK_CATALYST.defaultBlockState();
                                }
                            }
                        }
                        if (pillarHeight == 1 && state.getValue(UPSIDEDOWN) && state.getValue(TOTAL_HEIGHT) > 0) {
                            if (EasyNoiseSampler.localRandom.nextInt(3) <= 1) {

                            }
                        }
                        if (blockState.getBlock() == RegisterBlocks.OSSEOUS_SCULK) {
                            blockState = blockState.setValue(TOTAL_HEIGHT, state.getValue(TOTAL_HEIGHT));
                            if (state.getValue(UPSIDEDOWN)) {
                                blockState = blockState.setValue(UPSIDEDOWN, true);
                                if (direction == Direction.DOWN && Math.random() > 0.8) {
                                    Direction ribCage = getDir(getAxis(topPos), false);
                                    if (ISITSAFE(level.getBlockState(topPos.relative(ribCage)))) {
                                        level.setBlock(topPos.relative(ribCage), RegisterBlocks.OSSEOUS_SCULK.defaultBlockState().setValue(AXIS, getAxis(topPos)).setValue(TOTAL_HEIGHT, state.getValue(TOTAL_HEIGHT)).setValue(HEIGHT_LEFT, 0), 3);
                                        if (ISITSAFE(level.getBlockState(topPos.relative(ribCage).below()))) {
                                            if (Math.random() > 0.7) {
                                                level.setBlock(topPos.relative(ribCage).below(), RegisterBlocks.HANGING_TENDRIL.defaultBlockState(), 3);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        level.setBlock(topPos.relative(direction), blockState, 3);
                        level.playSound(null, blockPos, blockState.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
                        if (spreadManager.isWorldGeneration() && Math.random() > 0.2) {
                            j = 0;
                        }
                        BlockPos bottom = getBottom(level, topPos.relative(direction), state.getValue(TOTAL_HEIGHT));
                        if (bottom != null) {
                            BlockState bottomState = level.getBlockState(bottom);
                            if (bottomState.is(RegisterBlocks.OSSEOUS_SCULK)) {
                                int piece = bottomState.getValue(HEIGHT_LEFT);
                                int total = bottomState.getValue(TOTAL_HEIGHT);
                                if ((total) - piece <= total / 3) {
                                    convertToSculk(level, bottom);
                                }
                            }
                        }
                        return Math.max(0, i - j);
                    }
                }
            }
        }
        return i;
    }

    public static boolean ISITSAFE(BlockState state) {
        return state.is(Blocks.SCULK_VEIN) || state.isAir() || state.is(Blocks.WATER);
    }

    public static void worldGenSpread(BlockPos blockPos, LevelAccessor level, RandomSource random) {
        if (level.getBlockState(blockPos).is(RegisterBlocks.OSSEOUS_SCULK)) {
            int pillarHeight = level.getBlockState(blockPos).getValue(HEIGHT_LEFT);
            BlockPos topPos = getTop(level, blockPos, pillarHeight);
            if (topPos != null) {
                BlockState state = level.getBlockState(topPos);
                pillarHeight = state.getValue(HEIGHT_LEFT);
                Direction direction = getDir(state.getValue(AXIS), state.getValue(UPSIDEDOWN));
                if (level.getBlockState(topPos.relative(direction)).isAir() || level.getBlockState(topPos.relative(direction)).getBlock() == Blocks.SCULK_VEIN) {
                    BlockState blockState = RegisterBlocks.OSSEOUS_SCULK.defaultBlockState().setValue(HEIGHT_LEFT, Math.max(0, pillarHeight - 1));

                    if (pillarHeight == 1 && !state.getValue(UPSIDEDOWN) && state.getValue(TOTAL_HEIGHT) > 0) {
                        if (EasyNoiseSampler.localRandom.nextInt(Math.max(1, state.getValue(TOTAL_HEIGHT) / 2)) <= 1) {

                            if (random.nextInt(11) == 0) {
                                blockState = Blocks.SCULK_CATALYST.defaultBlockState();
                            }
                        }
                    }
                    if (pillarHeight == 1 && state.getValue(UPSIDEDOWN) && state.getValue(TOTAL_HEIGHT) > 0) {
                        if (EasyNoiseSampler.localRandom.nextInt(3) <= 1) {

                        }
                    }
                    if (blockState.getBlock() == RegisterBlocks.OSSEOUS_SCULK) {
                        blockState = blockState.setValue(TOTAL_HEIGHT, state.getValue(TOTAL_HEIGHT));
                        if (state.getValue(UPSIDEDOWN)) {
                            blockState = blockState.setValue(UPSIDEDOWN, true);
                            if (direction == Direction.DOWN && Math.random() > 0.9) {
                                Direction ribCage = getDir(getAxis(topPos), false);
                                if (ISITSAFE(level.getBlockState(topPos.relative(ribCage)))) {
                                    level.setBlock(topPos.relative(ribCage), RegisterBlocks.OSSEOUS_SCULK.defaultBlockState().setValue(AXIS, getAxis(topPos)).setValue(TOTAL_HEIGHT, state.getValue(TOTAL_HEIGHT)).setValue(HEIGHT_LEFT, 0), 3);
                                    if (ISITSAFE(level.getBlockState(topPos.relative(ribCage).below()))) {
                                        if (Math.random() > 0.66) {
                                            level.setBlock(topPos.relative(ribCage).below(), RegisterBlocks.HANGING_TENDRIL.defaultBlockState(), 3);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    level.setBlock(topPos.relative(direction), blockState, 3);
                    level.playSound(null, blockPos, blockState.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);

                    if (blockState.getBlock() == Blocks.SCULK_CATALYST || (blockState.getBlock() == RegisterBlocks.OSSEOUS_SCULK && blockState.getValue(HEIGHT_LEFT) == 0)) {
                        for (int i = 0; i < 4; i++) {
                            BlockPos bottom = getBottom(level, topPos, state.getValue(TOTAL_HEIGHT));
                            if (bottom != null) {
                                BlockState bottomState = level.getBlockState(bottom);
                                if (bottomState.is(RegisterBlocks.OSSEOUS_SCULK)) {
                                    int piece = bottomState.getValue(HEIGHT_LEFT);
                                    int total = bottomState.getValue(TOTAL_HEIGHT);
                                    if ((total) - piece <= total / 3) {
                                        convertToSculk(level, bottom);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static BlockPos getTop(LevelAccessor level, BlockPos pos, int max) {
        for (int i = 0; i < max; i++) {
            Block block = level.getBlockState(pos).getBlock();
            if (block != RegisterBlocks.OSSEOUS_SCULK) {
                return null;
            }
            Direction direction = getDir(level.getBlockState(pos).getValue(AXIS), level.getBlockState(pos).getValue(UPSIDEDOWN));
            if (level.getBlockState(pos.relative(direction)).isAir() || level.getBlockState(pos.relative(direction)).getBlock() == Blocks.SCULK_VEIN) {
                return pos;
            }
            pos = pos.relative(direction);
        }
        return null;
    }

    public static BlockPos getBottom(LevelAccessor level, BlockPos pos, int max) {
        for (int i = 0; i < max; i++) {
            Block block = level.getBlockState(pos).getBlock();
            if (block != RegisterBlocks.OSSEOUS_SCULK) {
                return null;
            }
            Direction direction = getDir(null, level.getBlockState(pos).getValue(UPSIDEDOWN)).getOpposite();
            if (level.getBlockState(pos.relative(direction)).is(Blocks.SCULK)) {
                return pos;
            }
            pos = pos.relative(direction);
        }
        return null;
    }

    public int updateDecayDelay(int oldDecay) {
        return 1;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HEIGHT_LEFT).add(BlockStateProperties.AXIS).add(UPSIDEDOWN).add(TOTAL_HEIGHT);
    }

}
