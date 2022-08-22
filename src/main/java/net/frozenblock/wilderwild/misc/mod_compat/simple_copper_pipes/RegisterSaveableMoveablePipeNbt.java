package net.frozenblock.wilderwild.misc.mod_compat.simple_copper_pipes;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.lunade.copper.FittingPipeDispenses;
import net.lunade.copper.RegisterPipeNbtMethods;
import net.lunade.copper.blocks.CopperPipe;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class RegisterSaveableMoveablePipeNbt {
    public static final ResourceLocation horn = new ResourceLocation(WilderWild.MOD_ID, "ancient_horn");

    public static void init() {
        WilderWild.log("Registering A Saveable Moveable Simple Copper Pipe NBT Dispense Method For WilderWild!", true);
        RegisterPipeNbtMethods.register(horn, (nbt, world, pos, blockState, copperPipeEntity) -> {
            if (!nbt.getCanOnlyBeUsedOnce() || nbt.getUseCount() < 1) {
                BlockState state = world.getBlockState(pos);
                if (state.getBlock() instanceof CopperPipe pipe) {
                    Direction direction = state.getValue(BlockStateProperties.FACING);
                    if (nbt.getEntity(world) != null) {
                        nbt.withUseCount(nbt.getUseCount() + 1);
                        AncientHornProjectile projectileEntity = new AncientHornProjectile(world, pos.getX() + pipe.getDripX(direction), pos.getY() + pipe.getDripY(direction), pos.getZ() + pipe.getDripZ(direction));
                        projectileEntity.shoot(direction.getStepX(), direction.getStepY(), direction.getStepZ(), 1.0F, 0.0F);
                        projectileEntity.setOwner(nbt.foundEntity);
                        projectileEntity.shotByPlayer = true;
                        world.addFreshEntity(projectileEntity);
                        EasyPacket.createMovingLoopingSound(world, projectileEntity, RegisterSounds.ANCIENT_HORN_PROJECTILE_LOOP, SoundSource.NEUTRAL, 1.0F, 1.0F, WilderWild.id("default"));
                    }
                }
            }
        }, (nbt, world, pos, blockState, blockEntity) -> {

        }, (nbt, world, pos, blockState, blockEntity) -> {
            if (nbt.foundEntity != null) {
                nbt.vec3d2 = nbt.foundEntity.position();
            }
            if (blockState.getBlock() instanceof CopperPipe pipe) {
                Direction direction = blockState.getValue(BlockStateProperties.FACING);
                for (int i = 0; i < world.getRandom().nextIntBetweenInclusive(10, 20); i++) {
                    world.sendParticles(new DustColorTransitionOptions(DustColorTransitionOptions.SCULK_PARTICLE_COLOR, DustColorTransitionOptions.SCULK_PARTICLE_COLOR, 1.0f), pos.getX() + pipe.getDripX(direction, world.getRandom()), pos.getY() + pipe.getDripY(direction, world.getRandom()), pos.getZ() + pipe.getDripZ(direction, world.getRandom()), 1, 0.0, 0.0, 0.0, 0.7);
                }
            }
        }, (nbt, world, pos, blockState, blockEntity) -> true);

        FittingPipeDispenses.register(RegisterItems.MILKWEED_POD, (world, stack, i, direction, position, state, corroded, pos, pipe) -> {
            double d = position.x();
            double e = position.y();
            double f = position.z();
            RandomSource random = world.random;
            double random1 = random.nextDouble() * 7.0D - 3.5D;
            double random2 = random.nextDouble() * 7.0D - 3.5D;
            Direction.Axis axis = direction.getAxis();
            if (axis == Direction.Axis.Y) {
                e -= 0.125D;
            } else {
                e -= 0.15625D;
            }

            int offX = direction.getStepX();
            int offY = direction.getStepY();
            int offZ = direction.getStepZ();
            double velX = axis == Direction.Axis.X ? (double) (i * offX * 2) : (axis == Direction.Axis.Z ? (corroded ? random2 : random2 * 0.1D) : (corroded ? random1 : random1 * 0.1D));
            double velY = axis == Direction.Axis.Y ? (double) (i * offY * 2) : (corroded ? random1 : random1 * 0.1D);
            double velZ = axis == Direction.Axis.Z ? (double) (i * offZ * 2) : (corroded ? random2 : random2 * 0.1D);
            UniformInt ran1 = UniformInt.of(-3, 3);
            UniformInt ran2 = UniformInt.of(-1, 1);
            UniformInt ran3 = UniformInt.of(-3, 3);
            for (int o = 0; o < random.nextIntBetweenInclusive(10, 30); ++o) {
                EasyPacket.EasySeedPacket.createControlledParticle(world, new Vec3(d + (double) ran1.sample(world.random) * 0.1D, e + (double) ran2.sample(world.random) * 0.1D, f + (double) ran3.sample(world.random) * 0.1D), velX, velY, velZ, 1, true, 64);
            }
        });

        FittingPipeDispenses.register(RegisterBlocks.SEEDING_DANDELION.asItem(), (world, stack, i, direction, position, state, corroded, pos, pipe) -> {
            double d = position.x();
            double e = position.y();
            double f = position.z();
            RandomSource random = world.random;
            double random1 = random.nextDouble() * 7.0D - 3.5D;
            double random2 = random.nextDouble() * 7.0D - 3.5D;
            Direction.Axis axis = direction.getAxis();
            if (axis == Direction.Axis.Y) {
                e -= 0.125D;
            } else {
                e -= 0.15625D;
            }

            int offX = direction.getStepX();
            int offY = direction.getStepY();
            int offZ = direction.getStepZ();
            double velX = axis == Direction.Axis.X ? (double) (i * offX * 2) : (axis == Direction.Axis.Z ? (corroded ? random2 : random2 * 0.1D) : (corroded ? random1 : random1 * 0.1D));
            double velY = axis == Direction.Axis.Y ? (double) (i * offY * 2) : (corroded ? random1 : random1 * 0.1D);
            double velZ = axis == Direction.Axis.Z ? (double) (i * offZ * 2) : (corroded ? random2 : random2 * 0.1D);
            UniformInt ran1 = UniformInt.of(-3, 3);
            UniformInt ran2 = UniformInt.of(-1, 1);
            UniformInt ran3 = UniformInt.of(-3, 3);
            for (int o = 0; o < random.nextIntBetweenInclusive(1, 10); ++o) {
                EasyPacket.EasySeedPacket.createControlledParticle(world, new Vec3(d + (double) ran1.sample(world.random) * 0.1D, e + (double) ran2.sample(world.random) * 0.1D, f + (double) ran3.sample(world.random) * 0.1D), velX, velY, velZ, 1, false, 64);
            }
        });

        FittingPipeDispenses.register(RegisterItems.ANCIENT_HORN, (world, stack, i, direction, position, state, corroded, pos, pipe) -> {
            if (!world.isClientSide) {
                double d = position.x();
                double e = position.y();
                double f = position.z();
                RandomSource random = world.random;
                double random1 = random.nextDouble() * 7.0D - 3.5D;
                Direction.Axis axis = direction.getAxis();
                if (axis == Direction.Axis.Y) {
                    e -= 0.125D;
                } else {
                    e -= 0.15625D;
                }

                int offY = direction.getStepY();
                double velY = axis == Direction.Axis.Y ? (double) (i * offY * 2) : (corroded ? random1 : random1 * 0.1D);
                UniformInt ran1 = UniformInt.of(-3, 3);
                UniformInt ran2 = UniformInt.of(-1, 1);
                UniformInt ran3 = UniformInt.of(-3, 3);
                for (int o = 0; o < random.nextIntBetweenInclusive(1, 4); ++o) {
                    EasyPacket.EasyFloatingSculkBubblePacket.createParticle(world, new Vec3(d + (double) ran1.sample(world.random) * 0.1D, e + (double) ran2.sample(world.random) * 0.1D, f + (double) ran3.sample(world.random) * 0.1D), Math.random() > 0.7 ? 1 : 0, random.nextIntBetweenInclusive(60, 80), velY * 0.05, 1);
                }
            }
        });

    }

}
