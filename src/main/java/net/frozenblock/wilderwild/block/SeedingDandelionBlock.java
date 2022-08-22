package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SeedingDandelionBlock extends FlowerBlock {
    public SeedingDandelionBlock(MobEffect suspiciousStewEffect, int effectDuration, Properties settings) {
        super(suspiciousStewEffect, effectDuration, settings);
    }

    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (random.nextFloat() > 0.95) {
            world.addParticle(RegisterParticles.DANDELION_SEED, pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
        }
    }

    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (world instanceof ServerLevel server) {
            if (server.random.nextFloat() > 0.95) {
                EasyPacket.EasySeedPacket.createParticle(world, Vec3.atCenterOf(pos).add(0, 0.3, 0), server.random.nextIntBetweenInclusive(1, 3), false, 32);
            }
        }
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(world, pos, state, player);
        if (world instanceof ServerLevel server) {
            EasyPacket.EasySeedPacket.createParticle(world, Vec3.atCenterOf(pos).add(0, 0.3, 0), server.random.nextIntBetweenInclusive(3, 7), false, 32);
        }
    }
}
