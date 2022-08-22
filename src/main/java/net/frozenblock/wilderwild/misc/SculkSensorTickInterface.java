package net.frozenblock.wilderwild.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface SculkSensorTickInterface {

    void tickServer(ServerLevel world, BlockPos pos, BlockState state);

    void tickClient(Level world, BlockPos pos, BlockState state);

    int getAge();

    int getAnimTicks();

    int getPrevAnimTicks();

    boolean isActive();

    void setActive(boolean active);

    void setAnimTicks(int i);

}