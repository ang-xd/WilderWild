package net.frozenblock.wilderwild.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.Firefly;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class FireflyRenderer extends EntityRenderer<Firefly> {

    //CREDIT TO magistermaks ON GITHUB!!

    public FireflyRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    private static final ResourceLocation TEXTURE = WilderWild.id("textures/entity/firefly/firefly_off.png");
    private static final RenderType LAYER = RenderType.entityCutout(TEXTURE);

    private static final RenderType NECTAR_LAYER = RenderType.entityCutout(WilderWild.id("textures/entity/firefly/nectar.png"));
    private static final RenderType NECTAR_FLAP_LAYER = RenderType.entityCutout(WilderWild.id("textures/entity/firefly/nectar_wings_down.png"));
    private static final RenderType NECTAR_OVERLAY = RenderType.entityTranslucentEmissive(WilderWild.id("textures/entity/firefly/nectar_overlay.png"), true);

    public static Object2ObjectMap<String, RenderType> layers = new Object2ObjectLinkedOpenHashMap<>() {{
        put("on", RenderType.entityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_on.png"), true));
        put("red", RenderType.entityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_red.png"), true));
        put("orange", RenderType.entityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_orange.png"), true));
        put("yellow", RenderType.entityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_yellow.png"), true));
        put("lime", RenderType.entityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_lime.png"), true));
        put("green", RenderType.entityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_green.png"), true));
        put("cyan", RenderType.entityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_cyan.png"), true));
        put("light_blue", RenderType.entityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_light_blue.png"), true));
        put("blue", RenderType.entityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_blue.png"), true));
        put("pink", RenderType.entityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_pink.png"), true));
        put("magenta", RenderType.entityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_magenta.png"), true));
        put("purple", RenderType.entityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_purple.png"), true));
        put("black", RenderType.entityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_black.png"), true));
        put("gray", RenderType.entityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_gray.png"), true));
        put("light_gray", RenderType.entityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_light_gray.png"), true));
        put("white", RenderType.entityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_white.png"), true));
        put("brown", RenderType.entityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_brown.png"), true));
    }};

    private static final double yOffset = 0.155F;
    private static final Quaternion one80Quat = Vector3f.YP.rotationDegrees(180.0F);
    private static final float pi = (float) Math.PI;

    @Override
    public void render(Firefly entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        boolean nectar = false;

        if (entity.hasCustomName()) {
            nectar = entity.getCustomName().getString().toLowerCase().contains("nectar");
        }

        int age = entity.getFlickerAge();
        boolean flickers = entity.flickers();
        float preScale = entity.getScale();
        float scale = preScale == 1.5F ? 1.5F : preScale - (tickDelta * 0.001875F);

        int overlay = getOverlay(entity, 0);

        matrices.pushPose();
        matrices.scale(scale, scale, scale);
        matrices.translate(0, yOffset, 0);
        matrices.mulPose(this.entityRenderDispatcher.cameraOrientation());
        matrices.mulPose(one80Quat);

        PoseStack.Pose entry = matrices.last();
        Matrix4f matrix4f = entry.pose();
        Matrix3f matrix3f = entry.normal();
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(nectar ? age % 2 == 0 ? NECTAR_LAYER : NECTAR_FLAP_LAYER : LAYER);

        vertexConsumer
                .vertex(matrix4f, -0.5F, -0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .uv(0, 1)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
        vertexConsumer
                .vertex(matrix4f, 0.5F, -0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .uv(1, 1)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
        vertexConsumer
                .vertex(matrix4f, 0.5F, 0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .uv(1, 0)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
        vertexConsumer
                .vertex(matrix4f, -0.5F, 0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .uv(0, 0)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();

        if (!nectar) {
            vertexConsumer = vertexConsumers.getBuffer(layers.get(entity.getColor()));
        } else {
            vertexConsumer = vertexConsumers.getBuffer(NECTAR_OVERLAY);
        }

        int color = flickers ? (int) ((255 * (Math.cos(((age + tickDelta) * pi) * 0.025))) + 127.5) : (int) Math.max((255 * (Math.cos(((age + tickDelta) * pi) * 0.05))), 0);

        vertexConsumer
                .vertex(matrix4f, -0.5F, -0.5F, 0.0F)
                .color(color, color, color, color)
                .uv(0, 1)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
        vertexConsumer
                .vertex(matrix4f, 0.5F, -0.5F, 0.0F)
                .color(color, color, color, color)
                .uv(1, 1)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
        vertexConsumer
                .vertex(matrix4f, 0.5F, 0.5F, 0.0F)
                .color(color, color, color, color)
                .uv(1, 0)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
        vertexConsumer
                .vertex(matrix4f, -0.5F, 0.5F, 0.0F)
                .color(color, color, color, color)
                .uv(0, 0)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();

        matrices.popPose();

        if (this.shouldShowName(entity)) {
            this.renderNameTag(entity, entity.getDisplayName(), matrices, vertexConsumers, light);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(Firefly entity) {
        return TEXTURE;
    }

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, int light, float f, int j, int k, int l, int overlay) {
        vertexConsumer
                .vertex(matrix4f, f - 0.5F, j - 0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .uv(k, l)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
    }

    private static void vertexPulsate(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, int light, float f, int j, int k, int l, int colors, int overlay) {
        vertexConsumer
                .vertex(matrix4f, f - 0.5F, j - 0.5F, 0.0F)
                .color(colors, colors, colors, colors)
                .uv(k, l)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
    }

    public static int getOverlay(Firefly entity, float whiteOverlayProgress) {
        return OverlayTexture.pack(OverlayTexture.u(whiteOverlayProgress), OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
    }

}
