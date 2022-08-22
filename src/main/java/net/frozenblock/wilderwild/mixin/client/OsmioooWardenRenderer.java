package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.render.OsmioooWardenFeatureRenderer;
import net.frozenblock.wilderwild.entity.render.WilderWardenModel;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.WardenRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.warden.Warden;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(WardenRenderer.class)
public abstract class OsmioooWardenRenderer extends MobRenderer<Warden, WardenModel<Warden>> {

    private static final ResourceLocation OSMIOOO_TEXTURE = WilderWild.id("textures/entity/warden/osmiooo_warden.png");
    private static final ResourceLocation OSMIOOO_BIOLUMINESCENT_LAYER_TEXTURE = WilderWild.id("textures/entity/warden/osmiooo_warden_bioluminescent_overlay.png");
    private static final ResourceLocation OSMIOOO_HEART_TEXTURE = WilderWild.id("textures/entity/warden/osmiooo_warden_heart.png");
    private static final ResourceLocation OSMIOOO_TENDRILS_TEXTURE = WilderWild.id("textures/entity/warden/osmiooo_warden_tendrils.png");
    private static final ResourceLocation OSMIOOO_PULSATING_SPOTS_1_TEXTURE = WilderWild.id("textures/entity/warden/osmiooo_warden_pulsating_spots_1.png");
    private static final ResourceLocation OSMIOOO_PULSATING_SPOTS_2_TEXTURE = WilderWild.id("textures/entity/warden/osmiooo_warden_pulsating_spots_2.png");

    public OsmioooWardenRenderer(EntityRendererProvider.Context context, WardenModel<Warden> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void WardenEntity(EntityRendererProvider.Context context, CallbackInfo ci) {
        this.addLayer(
                new OsmioooWardenFeatureRenderer<>(this, OSMIOOO_BIOLUMINESCENT_LAYER_TEXTURE, (warden, tickDelta, animationProgress) -> 1.0F, WardenModel::getBioluminescentLayerModelParts)
        );
        this.addLayer(
                new OsmioooWardenFeatureRenderer<>(
                        this,
                        OSMIOOO_PULSATING_SPOTS_1_TEXTURE,
                        (warden, tickDelta, animationProgress) -> Math.max(0.0F, Mth.cos(animationProgress * 0.045F) * 0.25F),
                        WardenModel::getPulsatingSpotsLayerModelParts
                )
        );
        this.addLayer(
                new OsmioooWardenFeatureRenderer<>(
                        this,
                        OSMIOOO_PULSATING_SPOTS_2_TEXTURE,
                        (warden, tickDelta, animationProgress) -> Math.max(0.0F, Mth.cos(animationProgress * 0.045F + (float) Math.PI) * 0.25F),
                        WardenModel::getPulsatingSpotsLayerModelParts
                )
        );
        this.addLayer(
                new OsmioooWardenFeatureRenderer<>(
                        this, OSMIOOO_TENDRILS_TEXTURE, (warden, tickDelta, animationProgress) -> warden.getTendrilAnimation(tickDelta), model -> ((WilderWardenModel) model).getHeadAndTendrils()
                )
        );
        this.addLayer(
                new OsmioooWardenFeatureRenderer<>(
                        this, OSMIOOO_HEART_TEXTURE, (warden, tickDelta, animationProgress) -> warden.getHeartAnimation(tickDelta), WardenModel::getHeartLayerModelParts
                )
        );
    }

    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/monster/warden/Warden;)Lnet/minecraft/resources/ResourceLocation;", at = @At("HEAD"), cancellable = true)
    public void getTextureLocation(Warden wardenEntity, CallbackInfoReturnable<ResourceLocation> cir) {
        if (((WilderWarden) wardenEntity).isOsmiooo()) {
            cir.setReturnValue(OSMIOOO_TEXTURE);
        }
    }
}
