package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.SculkSensorTickInterface;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkSensorBlock.class)
public class SculkSensorBlockMixin {

    @Inject(at = @At("TAIL"), method = "createBlockStateDefinition")
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
        builder.add(RegisterProperties.HICCUPPING);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void SculkSensorBlock(BlockBehaviour.Properties settings, int range, CallbackInfo ci) {
        SculkSensorBlock sculkSensor = SculkSensorBlock.class.cast(this);
        sculkSensor.registerDefaultState(sculkSensor.defaultBlockState().setValue(RegisterProperties.HICCUPPING, false));
    }

    @Inject(at = @At("HEAD"), method = "getTicker", cancellable = true)
    public <T extends BlockEntity> void getTicker(Level world, BlockState state, BlockEntityType<T> type, CallbackInfoReturnable<BlockEntityTicker<T>> info) {
        info.cancel();
        if (world.isClientSide) {
            info.setReturnValue(checkType(type, BlockEntityType.SCULK_SENSOR, (worldx, pos, statex, blockEntity) -> {
                ((SculkSensorTickInterface) blockEntity).tickClient(worldx, pos, statex);
            }));
        } else {
            info.setReturnValue(checkType(type, BlockEntityType.SCULK_SENSOR, (worldx, pos, statex, blockEntity) -> {
                ((SculkSensorTickInterface) blockEntity).tickServer((ServerLevel) worldx, pos, statex);
            }));
        }
    }

    @Inject(at = @At("HEAD"), method = "activate")
    private static void activate(@Nullable Entity entity, Level world, BlockPos pos, BlockState state, int power, CallbackInfo info) {
        if (world.getBlockEntity(pos) instanceof SculkSensorBlockEntity blockEntity) {
            ((SculkSensorTickInterface) blockEntity).setActive(true);
            ((SculkSensorTickInterface) blockEntity).setAnimTicks(10);
        }
    }

    @Inject(at = @At("HEAD"), method = "getRenderShape", cancellable = true)
    public void getRenderShape(BlockState state, CallbackInfoReturnable<RenderShape> info) {
        info.setReturnValue(WilderWild.RENDER_TENDRILS ? RenderShape.INVISIBLE : RenderShape.MODEL);
        info.cancel();
    }

    @Nullable
    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }
}