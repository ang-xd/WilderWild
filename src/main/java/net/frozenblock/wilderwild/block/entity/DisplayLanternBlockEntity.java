package net.frozenblock.wilderwild.block.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.ai.FireflyAi;
import net.frozenblock.wilderwild.item.FireflyBottle;
import net.frozenblock.wilderwild.misc.FireflyColor;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DisplayLanternBlockEntity extends BlockEntity {
    public NonNullList<ItemStack> inventory;
    ArrayList<FireflyInLantern> fireflies = new ArrayList<>();

    public int age;
    public boolean hasUpdated = false;

    public DisplayLanternBlockEntity(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntities.DISPLAY_LANTERN, pos, blockState);
        this.inventory = NonNullList.withSize(1, ItemStack.EMPTY);
    }

    public void serverTick(Level level, BlockPos pos) {
        if (!this.fireflies.isEmpty()) {
            for (FireflyInLantern firefly : this.fireflies) {
                firefly.tick(level, pos);
            }
        }
    }

    public void clientTick(Level level, BlockPos pos) {
        this.age += 1;
        if (!this.fireflies.isEmpty()) {
            for (FireflyInLantern firefly : this.fireflies) {
                firefly.tick(level, pos);
            }
        }
    }

    public void updateSync() {
        for (ServerPlayer player : PlayerLookup.tracking(this)) {
            player.connection.send(Objects.requireNonNull(this.getUpdatePacket()));
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    public boolean invEmpty() {
        Optional<ItemStack> stack = this.inventory.stream().findFirst();
        return stack.map(itemStack -> itemStack == ItemStack.EMPTY).orElse(true);
    }

    public Optional<ItemStack> getItem() {
        return this.inventory.stream().findFirst();
    }

    public boolean noFireflies() {
        return this.getFireflies().isEmpty();
    }

    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Fireflies", 9)) {
            this.fireflies.clear();
            DataResult<List<FireflyInLantern>> var10000 = FireflyInLantern.CODEC.listOf().parse(new Dynamic<>(NbtOps.INSTANCE, tag.getList("Fireflies", 10)));
            Logger var10001 = WilderWild.LOGGER;
            Objects.requireNonNull(var10001);
            Optional<List<FireflyInLantern>> list = var10000.resultOrPartial(var10001::error);
            if (list.isPresent()) {
                List<FireflyInLantern> fireflyList = list.get();
                this.fireflies.addAll(fireflyList);
            }
        }
        this.inventory = NonNullList.withSize(1, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.inventory);
        this.age = tag.getInt("age");
    }

    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        DataResult<Tag> var10000 = FireflyInLantern.CODEC.listOf().encodeStart(NbtOps.INSTANCE, this.fireflies);
        Logger var10001 = WilderWild.LOGGER;
        Objects.requireNonNull(var10001);
        var10000.resultOrPartial(var10001::error).ifPresent((cursorsNbt) -> {
            tag.put("Fireflies", cursorsNbt);
        });
        ContainerHelper.saveAllItems(tag, this.inventory);
        tag.putInt("age", this.age);
    }

    public ArrayList<FireflyInLantern> getFireflies() {
        return this.fireflies;
    }

    public void addFirefly(FireflyBottle bottle, String name) {
        Vec3 newVec = new Vec3(0.5 + (0.15 - Math.random() * 0.3), 0, 0.5 + (0.15 - Math.random() * 0.3));
        this.fireflies.add(new FireflyInLantern(newVec, bottle.color, name, Math.random() > 0.7, (int) (Math.random() * 20), 0));
    }

    public void removeFirefly(FireflyInLantern firefly) {
        this.fireflies.remove(firefly);
    }

    public void spawnFireflies() {
        if (this.level != null) {
            if (!this.level.isClientSide) {
                double extraHeight = this.getBlockState().getValue(BlockStateProperties.HANGING) ? 0.155 : 0;
                for (DisplayLanternBlockEntity.FireflyInLantern firefly : this.getFireflies()) {
                    Firefly entity = RegisterEntities.FIREFLY.create(level);
                    if (entity != null) {
                        entity.moveTo(worldPosition.getX() + firefly.pos.x, worldPosition.getY() + firefly.y + extraHeight + 0.07, worldPosition.getZ() + firefly.pos.z, 0, 0);
                        entity.setFromBottle(true);
                        boolean spawned = level.addFreshEntity(entity);
                        if (spawned) {
                            entity.hasHome = true;
                            FireflyAi.rememberHome(entity, entity.blockPosition());
                            entity.setColor(firefly.color);
                            entity.setScale(1.0F);
                            if (!Objects.equals(firefly.customName, "")) {
                                entity.setCustomName(Component.nullToEmpty(firefly.customName));
                            }
                        } else {
                            WilderWild.log("Couldn't spawn Firefly from lantern @ " + worldPosition, WilderWild.UNSTABLE_LOGGING);
                        }
                    }
                }
            }
        }
    }

    public void spawnFireflies(Level level) {
        double extraHeight = this.getBlockState().getValue(BlockStateProperties.HANGING) ? 0.155 : 0;
        for (DisplayLanternBlockEntity.FireflyInLantern firefly : this.getFireflies()) {
            Firefly entity = RegisterEntities.FIREFLY.create(level);
            if (entity != null) {
                entity.moveTo(worldPosition.getX() + firefly.pos.x, worldPosition.getY() + firefly.y + extraHeight + 0.07, worldPosition.getZ() + firefly.pos.z, 0, 0);
                entity.setFromBottle(true);
                boolean spawned = level.addFreshEntity(entity);
                if (spawned) {
                    entity.hasHome = true;
                    FireflyAi.rememberHome(entity, entity.blockPosition());
                    entity.setColor(firefly.color);
                    entity.setScale(1.0F);
                    if (!Objects.equals(firefly.customName, "")) {
                        entity.setCustomName(Component.nullToEmpty(firefly.customName));
                    }
                } else {
                    WilderWild.log("Couldn't spawn Firefly from lantern @ " + worldPosition, WilderWild.UNSTABLE_LOGGING);
                }
            }
        }
    }

    public static class FireflyInLantern {
        public Vec3 pos;
        public FireflyColor color;
        public String customName;
        public boolean flickers;
        public int age;
        public double y;
        public boolean wasNamedNectar;

        public static final Codec<FireflyInLantern> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                Vec3.CODEC.fieldOf("pos").forGetter(FireflyInLantern::getPos),
                FireflyColor.CODEC.fieldOf("color").forGetter(FireflyInLantern::getColor),
                Codec.STRING.fieldOf("customName").orElse("").forGetter(FireflyInLantern::getCustomName),
                Codec.BOOL.fieldOf("flickers").orElse(false).forGetter(FireflyInLantern::getFlickers),
                Codec.INT.fieldOf("age").forGetter(FireflyInLantern::getAge),
                Codec.DOUBLE.fieldOf("y").forGetter(FireflyInLantern::getY)
        ).apply(instance, FireflyInLantern::new));

        public FireflyInLantern(Vec3 pos, FireflyColor color, String customName, boolean flickers, int age, double y) {
            this.pos = pos;
            this.color = color;
            this.customName = customName;
            this.flickers = flickers;
            this.age = age;
            this.y = y;
        }

        private boolean isNectar = false;

        public void tick(Level level, BlockPos pos) {
            this.age += 1;
            this.y = Math.sin(this.age * 0.03) * 0.15;
            this.isNectar = this.getCustomName().toLowerCase().contains("nectar");

            if (isNectar != wasNamedNectar) {
                if (isNectar) {
                    if (level.getGameTime() % 70L == 0L) {
                        level.playSound(null, pos, RegisterSounds.BLOCK_DISPLAY_LANTERN_NECTAR_LOOP, SoundSource.AMBIENT, 0.5F, 1.0F);
                    }
                    this.wasNamedNectar = true;
                } else {
                    this.wasNamedNectar = false;
                }
            } else {
                this.wasNamedNectar = false;
            }
        }

        public Vec3 getPos() {
            return this.pos;
        }

        public FireflyColor getColor() {
            return this.color;
        }

        public String getCustomName() {
            return this.customName;
        }

        public boolean getFlickers() {
            return this.flickers;
        }

        public int getAge() {
            return this.age;
        }

        public double getY() {
            return this.y;
        }

    }

}