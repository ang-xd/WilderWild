package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.world.feature.WilderTreeConfigured;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.BirchTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BirchTreeGrower.class)
public class BirchTreeGrowerMixin {

    @Inject(method = "getConfiguredFeature", at = @At("HEAD"), cancellable = true)
    public void getConfiguredFeature(RandomSource random, boolean bees, CallbackInfoReturnable<Holder<? extends ConfiguredFeature<?, ?>>> cir) {
        cir.setReturnValue(WilderTreeConfigured.NEW_BIRCH_BEES_0004);
        cir.cancel();
    }
}