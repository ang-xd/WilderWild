package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.StairShape;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.SwampHutGenerator;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(SwampHutGenerator.class)
public class SwampHutMixin {
    @Shadow
    private boolean hasWitch;
    private boolean hasCat;

    private final SwampHutGenerator swampHut = SwampHutGenerator.class.cast(this);

    @Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pivot, CallbackInfo info) {
        info.cancel();
        if (swampHut.adjustToAverageHeight(world, chunkBox, 0)) {
            swampHut.fillWithOutline(world, chunkBox, 1, 1, 1, 5, 1, 7, RegisterBlocks.CYPRESS_PLANKS.getDefaultState(), RegisterBlocks.CYPRESS_PLANKS.getDefaultState(), false);
            swampHut.fillWithOutline(world, chunkBox, 1, 4, 2, 5, 4, 7, RegisterBlocks.CYPRESS_PLANKS.getDefaultState(), RegisterBlocks.CYPRESS_PLANKS.getDefaultState(), false);
            swampHut.fillWithOutline(world, chunkBox, 2, 1, 0, 4, 1, 0, RegisterBlocks.CYPRESS_PLANKS.getDefaultState(), RegisterBlocks.CYPRESS_PLANKS.getDefaultState(), false);
            swampHut.fillWithOutline(world, chunkBox, 2, 2, 2, 3, 3, 2, RegisterBlocks.CYPRESS_PLANKS.getDefaultState(), RegisterBlocks.CYPRESS_PLANKS.getDefaultState(), false);
            swampHut.fillWithOutline(world, chunkBox, 1, 2, 3, 1, 3, 6, RegisterBlocks.CYPRESS_PLANKS.getDefaultState(), RegisterBlocks.CYPRESS_PLANKS.getDefaultState(), false);
            swampHut.fillWithOutline(world, chunkBox, 5, 2, 3, 5, 3, 6, RegisterBlocks.CYPRESS_PLANKS.getDefaultState(), RegisterBlocks.CYPRESS_PLANKS.getDefaultState(), false);
            swampHut.fillWithOutline(world, chunkBox, 2, 2, 7, 4, 3, 7, RegisterBlocks.CYPRESS_PLANKS.getDefaultState(), RegisterBlocks.CYPRESS_PLANKS.getDefaultState(), false);
            swampHut.fillWithOutline(world, chunkBox, 1, 0, 2, 1, 3, 2, Blocks.MANGROVE_LOG.getDefaultState(), Blocks.MANGROVE_LOG.getDefaultState(), false);
            swampHut.fillWithOutline(world, chunkBox, 5, 0, 2, 5, 3, 2, Blocks.MANGROVE_LOG.getDefaultState(), Blocks.MANGROVE_LOG.getDefaultState(), false);
            swampHut.fillWithOutline(world, chunkBox, 1, 0, 7, 1, 3, 7, Blocks.MANGROVE_LOG.getDefaultState(), Blocks.MANGROVE_LOG.getDefaultState(), false);
            swampHut.fillWithOutline(world, chunkBox, 5, 0, 7, 5, 3, 7, Blocks.MANGROVE_LOG.getDefaultState(), Blocks.MANGROVE_LOG.getDefaultState(), false);
            swampHut.addBlock(world, Blocks.MANGROVE_FENCE.getDefaultState().with(Properties.WEST, true).with(Properties.EAST, true), 2, 3, 2, chunkBox);
            swampHut.addBlock(world, Blocks.MANGROVE_FENCE.getDefaultState().with(Properties.WEST, true).with(Properties.EAST, true), 3, 3, 7, chunkBox);
            swampHut.addBlock(world, Blocks.AIR.getDefaultState(), 1, 3, 4, chunkBox);
            swampHut.addBlock(world, Blocks.AIR.getDefaultState(), 5, 3, 4, chunkBox);
            swampHut.addBlock(world, Blocks.AIR.getDefaultState(), 5, 3, 5, chunkBox);
            swampHut.addBlock(world, Blocks.POTTED_RED_MUSHROOM.getDefaultState(), 1, 3, 5, chunkBox);
            swampHut.addBlock(world, Blocks.CRAFTING_TABLE.getDefaultState(), 3, 2, 6, chunkBox);
            swampHut.addBlock(world, Blocks.CAULDRON.getDefaultState(), 4, 2, 6, chunkBox);
            swampHut.addBlock(world, Blocks.MANGROVE_FENCE.getDefaultState().with(Properties.NORTH, true), 1, 2, 1, chunkBox);
            swampHut.addBlock(world, Blocks.MANGROVE_FENCE.getDefaultState().with(Properties.NORTH, true), 5, 2, 1, chunkBox);
            BlockState blockState = RegisterBlocks.CYPRESS_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH);
            BlockState blockState2 = RegisterBlocks.CYPRESS_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.EAST);
            BlockState blockState3 = RegisterBlocks.CYPRESS_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.WEST);
            BlockState blockState4 = RegisterBlocks.CYPRESS_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH);
            swampHut.fillWithOutline(world, chunkBox, 0, 4, 1, 6, 4, 1, blockState, blockState, false);
            swampHut.fillWithOutline(world, chunkBox, 0, 4, 2, 0, 4, 7, blockState2, blockState2, false);
            swampHut.fillWithOutline(world, chunkBox, 6, 4, 2, 6, 4, 7, blockState3, blockState3, false);
            swampHut.fillWithOutline(world, chunkBox, 0, 4, 8, 6, 4, 8, blockState4, blockState4, false);
            swampHut.addBlock(world, blockState.with(StairsBlock.SHAPE, StairShape.OUTER_RIGHT), 0, 4, 1, chunkBox);
            swampHut.addBlock(world, blockState.with(StairsBlock.SHAPE, StairShape.OUTER_LEFT), 6, 4, 1, chunkBox);
            swampHut.addBlock(world, blockState4.with(StairsBlock.SHAPE, StairShape.OUTER_LEFT), 0, 4, 8, chunkBox);
            swampHut.addBlock(world, blockState4.with(StairsBlock.SHAPE, StairShape.OUTER_RIGHT), 6, 4, 8, chunkBox);

            for (int i = 2; i <= 7; i += 5) {
                for (int j = 1; j <= 5; j += 4) {
                    swampHut.fillDownwards(world, Blocks.MANGROVE_LOG.getDefaultState(), j, -1, i, chunkBox);
                }
            }

            if (!this.hasWitch) {
                BlockPos blockPos = swampHut.offsetPos(2, 2, 5);
                if (chunkBox.contains(blockPos)) {
                    this.hasWitch = true;
                    WitchEntity witchEntity = EntityType.WITCH.create(world.toServerWorld());
                    assert witchEntity != null;
                    witchEntity.setPersistent();
                    witchEntity.refreshPositionAndAngles((double) blockPos.getX() + 0.5, blockPos.getY(), (double) blockPos.getZ() + 0.5, 0.0F, 0.0F);
                    witchEntity.initialize(world, world.getLocalDifficulty(blockPos), SpawnReason.STRUCTURE, null, null);
                    world.spawnEntityAndPassengers(witchEntity);
                }
            }

            this.spawnCat(world, chunkBox);
        }
    }

    @Shadow
    private void spawnCat(ServerWorldAccess world, BlockBox box) {
        if (!this.hasCat) {
            BlockPos blockPos = swampHut.offsetPos(2, 2, 5);
            if (box.contains(blockPos)) {
                this.hasCat = true;
                CatEntity catEntity = EntityType.CAT.create(world.toServerWorld());
                assert catEntity != null;
                catEntity.setPersistent();
                catEntity.refreshPositionAndAngles((double) blockPos.getX() + 0.5, blockPos.getY(), (double) blockPos.getZ() + 0.5, 0.0F, 0.0F);
                catEntity.initialize(world, world.getLocalDifficulty(blockPos), SpawnReason.STRUCTURE, null, null);
                world.spawnEntityAndPassengers(catEntity);
            }
        }

    }

}
