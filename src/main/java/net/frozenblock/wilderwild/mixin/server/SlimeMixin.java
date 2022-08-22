package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@Mixin(Slime.class)
public abstract class SlimeMixin extends Mob {

    protected SlimeMixin(EntityType<? extends Mob> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "checkSlimeSpawnRules", at = @At("HEAD"), cancellable = true)
    private static void checkSlimeSpawnRules(EntityType<Slime> type, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random, CallbackInfoReturnable<Boolean> info) {
        if (world.getDifficulty() != Difficulty.PEACEFUL) {
            //if (world.getBiome(pos).isIn(WilderBiomeTags.SLIMES_SPAWN_ON_FLOATING_MOSS)) {
            if (world.getBrightness(LightLayer.BLOCK, pos) < random.nextInt(8)) {
                boolean test = spawnReason == MobSpawnType.SPAWNER || random.nextInt(5) == 0;
                if (test && isAlgaeNearby(world, pos, 1)) {
                    info.setReturnValue(true);
                    info.cancel();
                }
            }
        }
    }

    private static boolean isAlgaeNearby(LevelAccessor world, BlockPos blockPos, int x) {
        Iterator<BlockPos> iterator = BlockPos.betweenClosed(blockPos.offset(-x, -x, -x), blockPos.offset(x, x, x)).iterator();
        int count = 0;
        BlockPos pos;
        do {
            if (!iterator.hasNext()) {
                return false;
            }
            pos = iterator.next();
            if (world.getBlockState(pos).is(RegisterBlocks.ALGAE)) {
                count = count + 1;
            }
        } while (count < 3);
        return true;
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader world) {
        return (!world.containsAnyLiquid(this.getBoundingBox()) || isAlgaeNearby(this.getLevel(), this.blockPosition(), 1)) && world.isUnobstructed(this);
    }

}
