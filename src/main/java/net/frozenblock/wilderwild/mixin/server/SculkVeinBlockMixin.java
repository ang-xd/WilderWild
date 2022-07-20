package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.fluid.FluidState;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

import static net.minecraft.block.MultifaceGrowthBlock.getProperty;
import static net.minecraft.block.MultifaceGrowthBlock.hasDirection;

@Mixin(SculkVeinBlock.class)
public class SculkVeinBlockMixin {

    @Final
    @Shadow
    private LichenGrower allGrowTypeGrower;

    @Inject(at = @At("HEAD"), method = "convertToBlock", cancellable = true)
    private void convertToBlock(SculkSpreadManager spreadManager, WorldAccess world, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> info) {
        BlockState blockState = world.getBlockState(pos);
        TagKey<Block> tagKey = spreadManager.getReplaceableTag();

        for (Direction direction : Direction.shuffle(random)) {
            if (hasDirection(blockState, direction)) {
                BlockPos blockPos = pos.offset(direction);
                BlockState blockState2 = world.getBlockState(blockPos);
                if (blockState2.isIn(tagKey)) {
                    BlockState blockState3 = Blocks.SCULK.getDefaultState();
                    if (blockState2.isIn(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN)) {
                        blockState3 = RegisterBlocks.SCULK_STAIRS.getDefaultState().with(StairsBlock.FACING, blockState2.get(StairsBlock.FACING)).with(StairsBlock.HALF, blockState2.get(StairsBlock.HALF)).with(StairsBlock.SHAPE, blockState2.get(StairsBlock.SHAPE)).with(StairsBlock.WATERLOGGED, blockState2.get(StairsBlock.WATERLOGGED));
                    } else if (blockState2.isIn(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN)) {
                        blockState3 = RegisterBlocks.SCULK_WALL.getDefaultState().with(WallBlock.UP, blockState2.get(WallBlock.UP)).with(WallBlock.NORTH_SHAPE, blockState2.get(WallBlock.NORTH_SHAPE))
                                .with(WallBlock.EAST_SHAPE, blockState2.get(WallBlock.EAST_SHAPE)).with(WallBlock.WEST_SHAPE, blockState2.get(WallBlock.WEST_SHAPE))
                                .with(WallBlock.SOUTH_SHAPE, blockState2.get(WallBlock.SOUTH_SHAPE)).with(WallBlock.WATERLOGGED, blockState2.get(WallBlock.WATERLOGGED));
                    } else if (blockState2.isIn(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)) {
                        blockState3 = RegisterBlocks.SCULK_SLAB.getDefaultState().with(SlabBlock.WATERLOGGED, blockState2.get(SlabBlock.WATERLOGGED)).with(SlabBlock.TYPE, blockState2.get(SlabBlock.TYPE));
                    }

                    world.setBlockState(blockPos, blockState3, 3);
                    Block.pushEntitiesUpBeforeBlockChange(blockState2, blockState3, world, blockPos);
                    world.playSound(null, blockPos, SoundEvents.BLOCK_SCULK_SPREAD, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    this.allGrowTypeGrower.grow(blockState3, world, blockPos, spreadManager.isWorldGen());
                    Direction direction2 = direction.getOpposite();

                    for (Direction direction3 : Direction.values()) {
                        if (direction3 != direction2) {
                            BlockPos blockPos2 = blockPos.offset(direction3);
                            BlockState blockState4 = world.getBlockState(blockPos2);
                            if (blockState4.isOf(Blocks.SCULK_VEIN)) {
                                this.spreadAtSamePosition(world, blockState4, blockPos2, random);
                            }
                        }
                    }

                    info.setReturnValue(true);
                    info.cancel();
                }
            }
        }

        info.setReturnValue(false);
        info.cancel();
    }

    @Shadow
    public void spreadAtSamePosition(WorldAccess world, BlockState state, BlockPos pos, Random random) {
    }

    @Inject(at = @At("HEAD"), method = "spreadAtSamePosition", cancellable = true)
    public void spreadAtSamePosition(WorldAccess world, BlockState state, BlockPos pos, Random random, CallbackInfo info) {
        if (state.isOf(Blocks.SCULK_VEIN)) {

            for (Direction direction : Direction.values()) {
                BooleanProperty booleanProperty = getProperty(direction);
                if (state.get(booleanProperty) && world.getBlockState(pos.offset(direction)).isIn(WilderBlockTags.SCULK_VEIN_REMOVE)) {
                    state = state.with(booleanProperty, false);
                }
            }

            if (!hasAnyDirection(state)) {
                FluidState fluidState = world.getFluidState(pos);
                state = (fluidState.isEmpty() ? Blocks.AIR : Blocks.WATER).getDefaultState();
            }

            world.setBlockState(pos, state, 3);
        }
    }

    private static boolean hasAnyDirection(BlockState state) {
        return Arrays.stream(Direction.values()).anyMatch((direction) -> {
            return hasDirection(state, direction);
        });
    }

}
