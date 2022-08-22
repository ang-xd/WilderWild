package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkShriekerBlock.class)
public class SculkShriekerBlockMixin extends BaseEntityBlock {

    public SculkShriekerBlockMixin(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.or(Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(1.0D, 8.0D, 1.0D, 15.0D, 15.0D, 15.0D));
    }

    @Inject(at = @At("TAIL"), method = "createBlockStateDefinition")
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
        builder.add(RegisterProperties.SOULS_TAKEN);
    }

    @Inject(at = @At("HEAD"), method = "stepOn", cancellable = true)
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity, CallbackInfo info) {
        if (state.getValue(RegisterProperties.SOULS_TAKEN) == 2) {
            info.cancel();
        }
    }

    @Shadow
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SculkShriekerBlockEntity(pos, state);
    }
}
