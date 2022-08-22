package net.frozenblock.wilderwild.misc.mod_compat.simple_copper_pipes;

import net.frozenblock.wilderwild.WilderWild;
import net.lunade.copper.block_entity.CopperPipeEntity;
import net.lunade.copper.pipe_nbt.MoveablePipeDataHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;

public class InteractionHandler {
    public static final ResourceLocation horn = new ResourceLocation(WilderWild.MOD_ID, "ancient_horn");

    public static boolean addHornNbtToBlock(ServerLevel world, BlockPos pos, Entity owner) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity != null) {
            if (entity instanceof CopperPipeEntity pipe) {
                world.playSound(null, pos, Registry.SOUND_EVENT.get(new ResourceLocation("lunade", "block.copper_pipe.item_in")), SoundSource.BLOCKS, 0.2F, (world.random.nextFloat() * 0.25F) + 0.8F);
                pipe.moveablePipeDataHandler.addSaveableMoveablePipeNbt(new MoveablePipeDataHandler.SaveableMovablePipeNbt().withVec3d(owner.position()).withVec3d2(owner.position()).withString(owner.getStringUUID()).withOnlyThroughOnePipe(true).withOnlyUseableOnce(true).withNBTID(horn));
                return true;
            }
        }
        return false;
    }

}
