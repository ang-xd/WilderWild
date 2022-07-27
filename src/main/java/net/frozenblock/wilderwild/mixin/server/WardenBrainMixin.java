package net.frozenblock.wilderwild.mixin.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.mob.WardenBrain;
import net.minecraft.entity.mob.WardenEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WardenBrain.class)
public class WardenBrainMixin {

    @Inject(method = "addIdleActivities", at = @At("HEAD"), cancellable = true)
    private static void addIdleActivities(Brain<WardenEntity> brain, CallbackInfo info) {
        info.cancel();
        brain.setTaskList(
                Activity.IDLE,
                10,
                ImmutableList.of(
                        new FindRoarTargetTask<>(WardenEntity::getPrimeSuspect),
                        new StartSniffingTask(),
                        new RandomTask<>(
                                ImmutableMap.of(MemoryModuleType.IS_SNIFFING, MemoryModuleState.VALUE_ABSENT),
                                ImmutableList.of(Pair.of(new AquaticStrollTask(0.5F), 2), Pair.of(new StrollTask(0.5F), 2), Pair.of(new WaitTask(30, 60), 1))
                        )
                )
        );
    }

}
