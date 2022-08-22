package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.world.feature.WilderTreeConfigured;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OakTreeGrower.class)
public class OakTreeGrowerMixin {

    @Inject(method = "getConfiguredFeature", at = @At("HEAD"), cancellable = true)
    public void getConfiguredFeature(RandomSource random, boolean bees, CallbackInfoReturnable<Holder<? extends ConfiguredFeature<?, ?>>> cir) {
        if (random.nextInt(10) == 0) {
            cir.setReturnValue(bees ? WilderTreeConfigured.NEW_FANCY_OAK_BEES_0004 : WilderTreeConfigured.NEW_FANCY_OAK);
            cir.cancel();
        } else {
            cir.setReturnValue(bees ? WilderTreeConfigured.NEW_OAK_BEES_0004 : WilderTreeConfigured.NEW_OAK);
            cir.cancel();
        }
    }
}