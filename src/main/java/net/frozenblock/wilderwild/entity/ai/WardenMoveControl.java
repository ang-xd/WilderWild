package net.frozenblock.wilderwild.entity.ai;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.warden.Warden;

public class WardenMoveControl extends MoveControl {

    private final Warden entity;
    private final float pitchChange;
    private final float yawChange;
    private final float speedInWater;
    private final float speedInAir;
    private final boolean buoyant;

    public WardenMoveControl(@NotNull Warden warden, float pitchChange, float yawChange, float speedInWater, float speedInAir, boolean buoyant) {
        super(warden);
        this.entity = warden;
        this.pitchChange = pitchChange;
        this.yawChange = yawChange;
        this.speedInWater = speedInWater;
        this.speedInAir = speedInAir;
        this.buoyant = buoyant;
    }

    @Override
    public void tick() {
        if (this.isEntityTouchingWaterOrLava(this.entity)) {
            if (this.buoyant) {
                if (this.entity.getBrain().hasMemoryValue(MemoryModuleType.ROAR_TARGET) || this.entity.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
                    Optional<LivingEntity> ROAR_TARGET = this.entity.getBrain().getMemory(MemoryModuleType.ROAR_TARGET);
                    Optional<LivingEntity> ATTACK_TARGET = this.entity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
                    if (ROAR_TARGET.isPresent()) {
                        LivingEntity target = ROAR_TARGET.get();
                        if ((!this.isEntityTouchingWaterOrLava(target) || !this.isEntitySubmergedInWaterOrLava(this.entity)) && target.getY() > this.entity.getY()) {
                            this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0.0D, 0.01D, 0.0D));
                        } else {
                            if (target.getY() > this.entity.getY()) {
                                this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0.0D, 0.003D, 0.0D));
                            } else {
                                this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0.0D, -0.003D, 0.0D));
                            }
                        }
                    } else if (ATTACK_TARGET.isPresent()) {
                        LivingEntity target = ATTACK_TARGET.get();
                        if ((!this.isEntityTouchingWaterOrLava(target) || !this.isEntitySubmergedInWaterOrLava(this.entity)) && target.getY() > this.entity.getY()) {
                            this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0.0D, 0.01D, 0.0D));
                        } else {
                            if (target.getY() > this.entity.getY()) {
                                this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0.0D, 0.003D, 0.0D));
                            } else {
                                this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0.0D, -0.003D, 0.0D));
                            }
                        }
                    }
                } else {
                    if (!this.isEntitySubmergedInWaterOrLava(this.entity)) {
                        this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0.0D, 0.01D, 0.0D));
                    } else {
                        this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0.0D, 0.006D, 0.0D));
                    }
                }
            }

            if (this.operation == MoveControl.Operation.MOVE_TO && !this.entity.getNavigation().isDone()) {
                double d = this.wantedX - this.entity.getX();
                double e = this.wantedY - this.entity.getY();
                double f = this.wantedZ - this.entity.getZ();
                double g = d * d + e * e + f * f;
                if (g < 2.5000003E-7F) {
                    this.entity.setZza(0.0F);
                } else {
                    float h = (float) (Mth.atan2(f, d) * 180.0F / (float) Math.PI) - 90.0F;
                    this.entity.setYRot(this.rotlerp(this.entity.getYRot(), h, this.yawChange));
                    this.entity.yBodyRot = this.entity.getYRot();
                    this.entity.yHeadRot = this.entity.getYRot();
                    float i = (float) (this.speedModifier * this.entity.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (this.isEntityTouchingWaterOrLava(entity)) {
                        this.entity.setSpeed(i * this.speedInWater);
                        double j = Math.sqrt(d * d + f * f);
                        if (Math.abs(e) > 1.0E-5F || Math.abs(j) > 1.0E-5F) {
                            float k = -((float) (Mth.atan2(e, j) * 180.0F / (float) Math.PI));
                            k = Mth.clamp(Mth.wrapDegrees(k), -this.pitchChange, this.pitchChange);
                            this.entity.setXRot(this.rotlerp(this.entity.getXRot(), k, 5.0F));
                        }

                        float k = Mth.cos(this.entity.getXRot() * (float) (Math.PI / 180.0));
                        float l = Mth.sin(this.entity.getXRot() * (float) (Math.PI / 180.0));
                        this.entity.zza = k * i;
                        this.entity.yya = -l * i;
                    } else {
                        this.entity.setSpeed(i * this.speedInAir);
                    }

                }
            } else {
                this.entity.setSpeed(0.0F);
                this.entity.setXxa(0.0F);
                this.entity.setYya(0.0F);
                this.entity.setZza(0.0F);
            }
        } else {
            super.tick();
        }
    }

    private boolean isEntityTouchingWaterOrLava(Entity entity) {
        return entity.isInWaterOrBubble() || entity.isInLava();
    }

    private boolean isEntitySubmergedInWaterOrLava(Entity entity) {
        return entity.isEyeInFluid(FluidTags.WATER) || entity.isEyeInFluid(FluidTags.LAVA);
    }
}
