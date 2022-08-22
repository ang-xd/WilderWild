package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Environment(EnvType.CLIENT)
@Mixin(SlimeRenderer.class)
public class MerpSlimeRenderer {

    private static final ResourceLocation MERP_SLIME = WilderWild.id("textures/entity/slime/merp_slime.png");

    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/monster/Slime;)Lnet/minecraft/resources/ResourceLocation;", at = @At("HEAD"), cancellable = true)
    public void getTextureLocation(Slime slimeEntity, CallbackInfoReturnable<ResourceLocation> cir) {
        String string = ChatFormatting.stripFormatting(slimeEntity.getName().getString());
        if (Objects.equals(string, "Merp")) {
            cir.setReturnValue(MERP_SLIME);
        }
    }
}
