package net.frozenblock.wilderwild.entity;

import io.netty.buffer.Unpooled;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.misc.WilderProjectileDamageSource;
import net.frozenblock.wilderwild.misc.mod_compat.simple_copper_pipes.InteractionHandler;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.*;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.warden.AngerLevel;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

import static net.frozenblock.wilderwild.item.AncientHorn.*;

//TODO: Fix rendering (Renders too bright or too dark depending on direction; renders under other translucents like water, doesn't render further than 8 block away)

public class AncientHornProjectile extends AbstractArrow {
    private final TagKey<Block> NON_COLLIDE = WilderBlockTags.ANCIENT_HORN_NON_COLLIDE;
    private boolean shot;
    private boolean leftOwner;
    public int aliveTicks;
    public double vecX;
    public double vecY;
    public double vecZ;
    public boolean shotByPlayer;
    public int bubbles;
    private net.minecraft.world.level.block.state.BlockState inBlockState;

    public AncientHornProjectile(@NotNull EntityType<? extends AbstractArrow> entityType, Level world) {
        super(entityType, world);
        this.setSoundEvent(RegisterSounds.ANCIENT_HORN_PROJECTILE_DISSIPATE);
    }

    public AncientHornProjectile(Level world, double x, double y, double z) {
        super(RegisterEntities.ANCIENT_HORN_PROJECTILE_ENTITY, x, y, z, world);
        this.setSoundEvent(RegisterSounds.ANCIENT_HORN_PROJECTILE_DISSIPATE);
    }

    public List<Entity> collidingEntities() {
        return level.getEntities(this, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), this::canHitEntity);
    }

    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }

    public void tick() {
        this.baseTick();
        if (this.bubbles > 0 && this.level instanceof ServerLevel server) {
            --this.bubbles;
            EasyPacket.EasyFloatingSculkBubblePacket.createParticle(server, this.position(), Math.random() > 0.7 ? 1 : 0, 20 + WilderWild.random().nextInt(40), 0.05, server.random.nextIntBetweenInclusive(1, 3));
        }
        if (this.aliveTicks > 300) {
            this.remove(RemovalReason.DISCARDED);
        }
        ++this.aliveTicks;
        if (!this.shot) {
            this.shot = true;
        }
        if (!this.leftOwner) {
            this.leftOwner = this.checkLeftOwner();
        }
        boolean bl = this.isNoPhysics();
        Vec3 vec3d = this.getDeltaMovement();
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d = vec3d.horizontalDistance();
            this.setYRot((float) (Mth.atan2(vec3d.x, vec3d.z) * 57.2957763671875D));
            this.setXRot((float) (Mth.atan2(vec3d.y, d) * 57.2957763671875D));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }
        BlockPos blockPos = this.blockPosition();
        net.minecraft.world.level.block.state.BlockState blockState = this.level.getBlockState(blockPos);
        Vec3 vec3d2;

        if (this.shakeTime > 0) {
            --this.shakeTime;
        }

        if (this.isInWater() && level instanceof ServerLevel server) {
            EasyPacket.EasyFloatingSculkBubblePacket.createParticle(server, new Vec3(this.xo, this.yo, this.zo), 0, 60, 0.05, 4);
        }
        if (this.isInWaterOrRain() || blockState.is(Blocks.POWDER_SNOW)) {
            this.clearFire();
        }
        Vec3 vec3d3 = this.position();
        vec3d2 = vec3d3.add(vec3d);
        HitResult hitResult = this.level.clip(new ClipContext(vec3d3, vec3d2, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        while (!this.isRemoved() && canInteract()) {
            List<Entity> entities = this.collidingEntities();
            Entity owner = this.getOwner();
            for (Entity entity : entities) {
                if (!this.isRemoved() && entity != null && entity != owner) {
                    boolean shouldDamage = true;
                    if (entity instanceof Player player) {
                        if (player.isCreative()) {
                            shouldDamage = false;
                        }
                        if (owner instanceof Player playerOwner && !playerOwner.canHarmPlayer(player)) {
                            shouldDamage = false;
                        }
                    }
                    if (entity.isInvulnerable()) {
                        shouldDamage = false;
                    }
                    if (shouldDamage) {
                        this.hitEntity(entity);
                    }
                }
            }
            break;
        }
        if (!this.isRemoved() && hitResult != null && !bl) {
            this.onHit(hitResult);
            if (this.isRemoved()) {
                return;
            }
            this.hasImpulse = true;
        }
        vec3d = this.getDeltaMovement();
        double e = vec3d.x;
        double f = vec3d.y;
        double g = vec3d.z;
        if (this.isCritArrow()) {
            for (int i = 0; i < 4; ++i) {
                this.level.addParticle(ParticleTypes.CRIT, this.getX() + e * (double) i / 4.0D, this.getY() + f * (double) i / 4.0D, this.getZ() + g * (double) i / 4.0D, -e, -f + 0.2D, -g);
            }
        }
        double h = this.getX() + e;
        double j = this.getY() + f;
        double k = this.getZ() + g;
        double l = vec3d.horizontalDistance();
        if (bl) {
            this.setYRot((float) (Mth.atan2(-e, -g) * 57.2957763671875D));
        } else {
            this.setYRot((float) (Mth.atan2(e, g) * 57.2957763671875D));
        }
        this.setXRot((float) (Mth.atan2(f, l) * 57.2957763671875D));
        this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
        this.setYRot(lerpRotation(this.yRotO, this.getYRot()));

        this.setPos(h, j, k);
        this.checkInsideBlocks();
    }

    public void setCooldown(int cooldown) {
        Entity entity = this.getOwner();
        if (entity != null) {
            if (entity instanceof Player user) {
                user.getCooldowns().addCooldown(RegisterItems.ANCIENT_HORN, cooldown);
            }
        }
    }

    public void addCooldown(int i) {
        Entity entity = this.getOwner();
        if (entity != null) {
            if (entity instanceof Player user) {
                if (!user.isCreative()) {
                    ItemCooldowns manager = user.getCooldowns();
                    ItemCooldowns.CooldownInstance entry = manager.cooldowns.get(RegisterItems.ANCIENT_HORN);
                    if (entry != null) {
                        int cooldown = (entry.endTime - entry.startTime) + i;
                        manager.removeCooldown(RegisterItems.ANCIENT_HORN);
                        manager.addCooldown(RegisterItems.ANCIENT_HORN, Math.min(600, cooldown));
                    } else {
                        manager.addCooldown(RegisterItems.ANCIENT_HORN, i);
                    }
                }
            }
        }
    }

    public boolean canHitEntity(Entity entity) {
        if (!entity.isSpectator() && entity.isAlive() && entity.isPickable() && !(entity instanceof Projectile)) {
            Entity entity2 = this.getOwner();
            return entity2 != null && (this.leftOwner || !entity2.isPassengerOfSameVehicle(entity));
        } else {
            return false;
        }
    }

    public void playerTouch(Player player) {
    }

    protected void onHitBlock(BlockHitResult blockHitResult) {
        this.inBlockState = this.level.getBlockState(blockHitResult.getBlockPos());
        net.minecraft.world.level.block.state.BlockState blockState = this.level.getBlockState(blockHitResult.getBlockPos());
        Entity owner = this.getOwner();
        if (WilderWild.isCopperPipe(blockState) && owner != null) {
            if (blockHitResult.getDirection() == blockState.getValue(BlockStateProperties.FACING).getOpposite() && this.level instanceof ServerLevel server) {
                if (InteractionHandler.addHornNbtToBlock(server, blockHitResult.getBlockPos(), owner)) {
                    this.discard();
                }
            }
        }
        blockState.onProjectileHit(this.level, blockState, blockHitResult, this);
        Vec3 vec3d = blockHitResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(vec3d);
        Vec3 vec3d2 = vec3d.normalize().scale(0.05000000074505806D);
        this.setPosRaw(this.getX() - vec3d2.x, this.getY() - vec3d2.y, this.getZ() - vec3d2.z);
        this.playSound(this.getHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.inGround = true;
        this.shakeTime = 7;
        this.setCritArrow(false);
        if (level instanceof ServerLevel server && canInteract()) {
            if (blockState.getBlock() == Blocks.SCULK_SHRIEKER) {
                BlockPos pos = blockHitResult.getBlockPos();
                WilderWild.log(Blocks.SCULK_SHRIEKER, pos, "Horn Projectile Touched", WilderWild.UNSTABLE_LOGGING);
                if (blockState.getValue(RegisterProperties.SOULS_TAKEN) < 2 && !blockState.getValue(SculkShriekerBlock.SHRIEKING)) {
                    if (!blockState.getValue(SculkShriekerBlock.CAN_SUMMON)) {
                        server.setBlockAndUpdate(pos, blockState.setValue(RegisterProperties.SOULS_TAKEN, blockState.getValue(RegisterProperties.SOULS_TAKEN) + 1));
                    } else {
                        server.setBlockAndUpdate(pos, blockState.setValue(SculkShriekerBlock.CAN_SUMMON, false));
                    }
                    server.sendParticles(ParticleTypes.SCULK_SOUL, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.15D, (double) pos.getZ() + 0.5D, 1, 0.2D, 0.0D, 0.2D, 0.0D);
                    trySpawnWarden(server, pos);
                    Warden.applyDarknessAround(server, Vec3.atCenterOf(this.blockPosition()), null, 40);
                    server.levelEvent(LevelEvent.PARTICLES_SCULK_SHRIEK, pos, 0);
                    server.gameEvent(GameEvent.SHRIEK, pos, GameEvent.Context.of(owner));
                    setCooldown(getCooldown(this.getOwner(), SHRIEKER_COOLDOWN));
                    this.setSoundEvent(RegisterSounds.ANCIENT_HORN_PROJECTILE_DISSIPATE);
                    this.setShotFromCrossbow(false);
                    this.remove(RemovalReason.DISCARDED);
                }
            } else if (blockState.getBlock() == Blocks.SCULK_SENSOR) {
                BlockPos pos = blockHitResult.getBlockPos();
                WilderWild.log(Blocks.SCULK_SENSOR, pos, "Horn Projectile Touched", WilderWild.UNSTABLE_LOGGING);
                if (blockState.getValue(RegisterProperties.HICCUPPING)) {
                    server.setBlockAndUpdate(pos, blockState.setValue(RegisterProperties.HICCUPPING, false));
                } else {
                    server.setBlockAndUpdate(pos, blockState.setValue(RegisterProperties.HICCUPPING, true));
                }
                if (SculkSensorBlock.canActivate(blockState)) {
                    SculkSensorBlock.activate(null, level, pos, level.getBlockState(pos), WilderWild.random().nextInt(15));
                    level.gameEvent(null, RegisterGameEvents.SCULK_SENSOR_ACTIVATE, pos);
                    setCooldown(getCooldown(this.getOwner(), SENSOR_COOLDOWN));
                }
            }
        }
        this.setSoundEvent(RegisterSounds.ANCIENT_HORN_PROJECTILE_DISSIPATE);
        this.setShotFromCrossbow(false);
        this.remove(RemovalReason.DISCARDED);
    }

    private static void trySpawnWarden(ServerLevel world, BlockPos pos) {
        if (world.getGameRules().getBoolean(GameRules.RULE_DO_WARDEN_SPAWNING)) {
            Optional<Warden> warden = SpawnUtil.trySpawnMob(EntityType.WARDEN, MobSpawnType.TRIGGERED, world, pos, 20, 5, 6, SpawnUtil.Strategy.ON_TOP_OF_COLLIDER);
            warden.ifPresent(wardenEntity -> wardenEntity.playSound(SoundEvents.WARDEN_AGITATED, 5.0F, 1.0F));
        }
    }

    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return RegisterSounds.ANCIENT_HORN_PROJECTILE_DISSIPATE;
    }

    public boolean isNoPhysics() {
        net.minecraft.world.level.block.state.BlockState insideState = level.getBlockState(this.blockPosition());
        if (insideState.is(RegisterBlocks.HANGING_TENDRIL) && level instanceof ServerLevel server && canInteract()) {
            BlockPos pos = this.blockPosition();
            BlockEntity entity = level.getBlockEntity(pos);
            WilderWild.log(RegisterBlocks.HANGING_TENDRIL, pos, "Horn Projectile Touched", WilderWild.DEV_LOGGING);
            if (entity instanceof HangingTendrilBlockEntity tendril) {
                WilderWild.log("Horn Projectile Found Hanging Tendril Entity", WilderWild.UNSTABLE_LOGGING);
                this.playSound(this.getHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                int XP = tendril.storedXP;
                if (XP > 0) {
                    tendril.storedXP = 0;
                    level.explode(this, this.getX(), this.getY(), this.getZ(), 0, Explosion.BlockInteraction.NONE);
                    level.playLocalSound(this.getX(), this.getY(), this.getZ(), RegisterSounds.ANCIENT_HORN_BLAST, SoundSource.NEUTRAL, 1.0F, 1.0F, true);
                    level.destroyBlock(this.blockPosition(), false);
                    ExperienceOrb.award(server, Vec3.atCenterOf(pos).add(0, 0, 0), XP);
                    setCooldown(getCooldown(this.getOwner(), TENDRIL_COOLDOWN));
                    this.setShotFromCrossbow(false);
                    this.remove(RemovalReason.DISCARDED);
                }
            }
        }
        if (insideState.is(this.NON_COLLIDE)) {
            if (level instanceof ServerLevel server) {
                if (insideState.is(Blocks.BELL)) {
                    ((BellBlock) insideState.getBlock()).onProjectileHit(server, insideState, this.level.clip(new ClipContext(this.position(), new Vec3(this.getBlockX(), this.getBlockY(), this.getBlockZ()), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)), this);
                }
            }
            return true;
        }
        Vec3 vec3d3 = this.position();
        Vec3 vec3d = this.getDeltaMovement();
        Vec3 vec3d2 = vec3d3.add(vec3d.scale(0.08));
        BlockHitResult hitResult = this.level.clip(new ClipContext(vec3d3, vec3d2, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            net.minecraft.world.level.block.state.BlockState state = level.getBlockState(hitResult.getBlockPos());
            return state.is(this.NON_COLLIDE);
        }
        return false;
    }

    private boolean checkLeftOwner() {
        Entity entity = this.getOwner();
        if (entity != null) {
            for (Entity entity2 : this.level.getEntities(this, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), (entityx) -> !entityx.isSpectator() && entityx.isPickable())) {
                if (entity2.getRootVehicle() == entity.getRootVehicle()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return EntitySpawnPacket.create(this, WilderWild.HORN_PROJECTILE_PACKET_ID);
    }

    public boolean canInteract() {
        return this.getOwner() != null;
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    public void addAdditionalSaveData(CompoundTag nbt) {
        if (!this.isRemoved()) {
            if (this.inBlockState != null) {
                nbt.put("inBlockState", NbtUtils.writeBlockState(this.inBlockState));
            }
            nbt.putInt("aliveTicks", this.aliveTicks);
            if (this.leftOwner) {
                nbt.putBoolean("LeftOwner", true);
            }
            nbt.putBoolean("HasBeenShot", this.shot);
            nbt.putDouble("originX", this.vecX);
            nbt.putDouble("originY", this.vecY);
            nbt.putDouble("originZ", this.vecZ);
            nbt.putBoolean("shotByPlayer", this.shotByPlayer);
            nbt.putInt("bubbles", this.bubbles);
        }
    }

    public void readAdditionalSaveData(CompoundTag nbt) {
        if (!this.isRemoved()) {
            if (nbt.contains("inBlockState", 10)) {
                this.inBlockState = NbtUtils.readBlockState(nbt.getCompound("inBlockState"));
            }
            this.aliveTicks = nbt.getInt("aliveTicks");
            this.leftOwner = nbt.getBoolean("LeftOwner");
            this.shot = nbt.getBoolean("HasBeenShot");
            this.vecX = nbt.getDouble("originX");
            this.vecY = nbt.getDouble("originY");
            this.vecZ = nbt.getDouble("originZ");
            this.shotByPlayer = nbt.getBoolean("shotByPlayer");
            this.bubbles = nbt.getInt("bubbles");
        }
    }

    public void shootFromRotation(Entity shooter, float pitch, float yaw, float roll, float speed, float divergence) {
        float f = -Mth.sin(yaw * 0.017453292F) * Mth.cos(pitch * 0.017453292F);
        float g = -Mth.sin((pitch + roll) * 0.017453292F);
        float h = Mth.cos(yaw * 0.017453292F) * Mth.cos(pitch * 0.017453292F);
        this.shoot(f, g, h, speed, divergence);
        this.vecX = shooter.getX();
        this.vecY = shooter.getEyeY();
        this.vecZ = shooter.getZ();
        this.setOwner(shooter);
    }

    protected void onHit(HitResult hitResult) {
        HitResult.Type type = hitResult.getType();
        if (type == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            if (!level.getBlockState(blockHitResult.getBlockPos()).is(this.NON_COLLIDE)) {
                this.onHitBlock(blockHitResult);
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    public double getDamage(@Nullable Entity entity) {
        if (entity != null) {
            if (!(entity instanceof Player)) {
                return 22;
            }
        }
        return 15;
    }

    protected float getWaterInertia() {
        return 1.0F;
    }

    public boolean isNoGravity() {
        return true;
    }

    private void hitEntity(Entity entity) {
        int i = (int) this.getDamage(entity);
        Entity entity2 = this.getOwner();
        if (entity != entity2) {
            DamageSource damageSource;
            if (entity2 == null) {
                damageSource = WilderProjectileDamageSource.ancientHorn(this, this);
            } else {
                damageSource = WilderProjectileDamageSource.ancientHorn(this, entity2);
                if (entity2 instanceof LivingEntity) {
                    ((LivingEntity) entity2).setLastHurtMob(entity);
                }
            }
            int j = entity.getRemainingFireTicks();
            if (this.isOnFire()) {
                entity.setSecondsOnFire(5);
            }
            if (entity instanceof Warden warden && entity2 != null && canInteract()) {
                WilderWild.log(warden, "Horn Projectile Touched", WilderWild.DEV_LOGGING);
                warden.increaseAngerAt(entity2, AngerLevel.ANGRY.getMinimumAnger() + 20, true);
                warden.playSound(SoundEvents.WARDEN_TENDRIL_CLICKS, 5.0F, warden.getVoicePitch());
                this.discard();
            } else if (entity.hurt(damageSource, (float) i)) {
                if (entity instanceof LivingEntity livingEntity) {
                    WilderWild.log(livingEntity, "Horn Projectile Touched", WilderWild.DEV_LOGGING);
                    if (!this.level.isClientSide && entity2 instanceof LivingEntity) {
                        EnchantmentHelper.doPostHurtEffects(livingEntity, entity2);
                        EnchantmentHelper.doPostDamageEffects((LivingEntity) entity2, livingEntity);
                    }
                    this.doPostHurtEffects(livingEntity);
                    if (livingEntity instanceof Player && entity2 instanceof ServerPlayer && !this.isSilent()) {
                        ((ServerPlayer) entity2).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                    }
                    if (livingEntity.isDeadOrDying() && level instanceof ServerLevel server) {
                        server.sendParticles(ParticleTypes.SCULK_SOUL, livingEntity.getX(), livingEntity.getEyeY(), livingEntity.getZ(), 1, 0.2D, 0.0D, 0.2D, 0.0D);
                        if (this.getOwner() != null) {
                            if (this.getOwner() instanceof ServerPlayer serverPlayer) {
                                addCooldown(livingEntity.getExperienceReward() * 10);
                                EasyPacket.EasyCompetitionPacket.sendAncientHornKillInfo(level, serverPlayer, livingEntity);
                            }
                        }
                    }
                }

                this.playSound(RegisterSounds.ANCIENT_HORN_PROJECTILE_DISSIPATE, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            } else {
                entity.setRemainingFireTicks(j);
                if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
                    this.discard();
                }
            }
        }
    }

    public void gameEvent(GameEvent event) {
    }

    public void gameEvent(GameEvent event, @Nullable Entity entity) {
    }

    public static class EntitySpawnPacket { //When the Fabric tutorial WORKS!!!!! BOM BOM BOM BOM BOM BOM BOM, BOBOBOM! DUNDUN!
        public static Packet<?> create(Entity e, ResourceLocation packetID) {
            if (e.level.isClientSide)
                throw new IllegalStateException("SpawnPacketUtil.create called on the logical client!");
            FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
            byteBuf.writeVarInt(Registry.ENTITY_TYPE.getId(e.getType()));
            byteBuf.writeUUID(e.getUUID());
            byteBuf.writeVarInt(e.getId());
            PacketBufUtil.writeVec3d(byteBuf, e.position());
            PacketBufUtil.writeAngle(byteBuf, e.getXRot());
            PacketBufUtil.writeAngle(byteBuf, e.getYRot());
            return ServerPlayNetworking.createS2CPacket(packetID, byteBuf);
        }

        public static final class PacketBufUtil {

            public static byte packAngle(float angle) {
                return (byte) Mth.floor(angle * 256 / 360);
            }

            public static float unpackAngle(byte angleByte) {
                return (angleByte * 360) / 256f;
            }

            public static void writeAngle(FriendlyByteBuf byteBuf, float angle) {
                byteBuf.writeByte(packAngle(angle));
            }

            public static float readAngle(FriendlyByteBuf byteBuf) {
                return unpackAngle(byteBuf.readByte());
            }

            public static void writeVec3d(FriendlyByteBuf byteBuf, Vec3 vec3d) {
                byteBuf.writeDouble(vec3d.x);
                byteBuf.writeDouble(vec3d.y);
                byteBuf.writeDouble(vec3d.z);
            }

            public static Vec3 readVec3d(FriendlyByteBuf byteBuf) {
                double x = byteBuf.readDouble();
                double y = byteBuf.readDouble();
                double z = byteBuf.readDouble();
                return new Vec3(x, y, z);
            }
        }
    }
}