package net.frozenblock.wilderwild.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class LargeSaplingBlock extends SaplingBlock {

    private final SaplingGenerator generator;

    public LargeSaplingBlock(SaplingGenerator generator, Settings settings) {
        super(generator, settings);
        this.generator = generator;
    }

    @Override
    public void generate(ServerWorld world, BlockPos pos, BlockState state, Random random) {
        if (state.get(STAGE) == 0) {
            world.setBlockState(pos, state.cycle(STAGE), 4);
        } else {
            this.generator.generate(world, world.getChunkManager().getChunkGenerator(), pos, state, random);
            world.getChunkManager().markForUpdate(pos);
        }

    }
}
