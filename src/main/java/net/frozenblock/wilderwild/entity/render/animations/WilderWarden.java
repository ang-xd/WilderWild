package net.frozenblock.wilderwild.entity.render.animations;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.mob.WardenEntity;
import org.quiltmc.qsl.base.api.util.InjectedInterface;

@InjectedInterface(WardenEntity.class)
public interface WilderWarden {

    AnimationState getDyingAnimationState();

    AnimationState getSwimmingDyingAnimationState();

    AnimationState getKirbyDeathAnimationState();

    boolean isOsmiooo();

}
