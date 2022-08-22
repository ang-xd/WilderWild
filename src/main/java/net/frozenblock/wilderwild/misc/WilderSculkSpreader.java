package net.frozenblock.wilderwild.misc;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class WilderSculkSpreader implements SculkBehaviour {
    @Override
    public int attemptUseCharge(SculkSpreader.ChargeCursor cursor, LevelAccessor world, BlockPos catalystPos, RandomSource random, SculkSpreader spreadManager, boolean shouldConvertToBlock) {
        BlockState placementState = null;
        BlockPos cursorPos = cursor.getPos();
        BlockState currentState = world.getBlockState(cursorPos);
        if (currentState.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN)) {
            placementState = RegisterBlocks.SCULK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, currentState.getValue(StairBlock.FACING)).setValue(StairBlock.HALF, currentState.getValue(StairBlock.HALF)).setValue(StairBlock.SHAPE, currentState.getValue(StairBlock.SHAPE)).setValue(StairBlock.WATERLOGGED, currentState.getValue(StairBlock.WATERLOGGED));
        } else if (currentState.is(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN)) {
            placementState = RegisterBlocks.SCULK_WALL.defaultBlockState().setValue(WallBlock.UP, currentState.getValue(WallBlock.UP)).setValue(WallBlock.NORTH_WALL, currentState.getValue(WallBlock.NORTH_WALL))
                    .setValue(WallBlock.EAST_WALL, currentState.getValue(WallBlock.EAST_WALL)).setValue(WallBlock.WEST_WALL, currentState.getValue(WallBlock.WEST_WALL))
                    .setValue(WallBlock.SOUTH_WALL, currentState.getValue(WallBlock.SOUTH_WALL)).setValue(WallBlock.WATERLOGGED, currentState.getValue(WallBlock.WATERLOGGED));
        } else if (currentState.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)) {
            placementState = RegisterBlocks.SCULK_SLAB.defaultBlockState().setValue(SlabBlock.WATERLOGGED, currentState.getValue(SlabBlock.WATERLOGGED)).setValue(SlabBlock.TYPE, currentState.getValue(SlabBlock.TYPE));
        }

        if (placementState != null) {
            world.setBlock(cursorPos, placementState, 3);
            return cursor.getCharge() - 1;
        }
        return random.nextInt(spreadManager.chargeDecayRate()) == 0 ? Mth.floor((float) cursor.getCharge() * 0.5F) : cursor.getCharge();
    }

    @Override
    public boolean attemptSpreadVein(LevelAccessor world, BlockPos pos, BlockState state, @Nullable Collection<Direction> directions, boolean markForPostProcessing) {
        BlockState placementState = null;
        BlockState currentState = world.getBlockState(pos);
        if (currentState.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN)) {
            placementState = RegisterBlocks.SCULK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, currentState.getValue(StairBlock.FACING)).setValue(StairBlock.HALF, currentState.getValue(StairBlock.HALF)).setValue(StairBlock.SHAPE, currentState.getValue(StairBlock.SHAPE)).setValue(StairBlock.WATERLOGGED, currentState.getValue(StairBlock.WATERLOGGED));
        } else if (currentState.is(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN)) {
            placementState = RegisterBlocks.SCULK_WALL.defaultBlockState().setValue(WallBlock.UP, currentState.getValue(WallBlock.UP)).setValue(WallBlock.NORTH_WALL, currentState.getValue(WallBlock.NORTH_WALL))
                    .setValue(WallBlock.EAST_WALL, currentState.getValue(WallBlock.EAST_WALL)).setValue(WallBlock.WEST_WALL, currentState.getValue(WallBlock.WEST_WALL))
                    .setValue(WallBlock.SOUTH_WALL, currentState.getValue(WallBlock.SOUTH_WALL)).setValue(WallBlock.WATERLOGGED, currentState.getValue(WallBlock.WATERLOGGED));
        } else if (currentState.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)) {
            placementState = RegisterBlocks.SCULK_SLAB.defaultBlockState().setValue(SlabBlock.WATERLOGGED, currentState.getValue(SlabBlock.WATERLOGGED)).setValue(SlabBlock.TYPE, currentState.getValue(SlabBlock.TYPE));
        }

        if (placementState != null) {
            world.setBlock(pos, placementState, 3);
            return true;
        }
        return false;
    }
}
