package net.frozenblock.wilderwild.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

@Environment(EnvType.CLIENT)
public class AncientHornProjectileModel extends Model {
    private final net.minecraft.client.model.geom.ModelPart bone;
    private final net.minecraft.client.model.geom.ModelPart front;
    private final net.minecraft.client.model.geom.ModelPart middle;
    private final net.minecraft.client.model.geom.ModelPart back;

    public AncientHornProjectileModel(net.minecraft.client.model.geom.ModelPart root) {
        super(RenderType::entityTranslucentEmissive);
        this.bone = root.getChild("bone");
        this.front = bone.getChild("front");
        this.middle = bone.getChild("middle");
        this.back = bone.getChild("back");
    }

    private static final float pi = (float) Math.PI;
    private static final float bonePitchYaw = 1.57079632f;
    private static final float pulse2Extra = 8.0F / 1.5F;
    private static final float pulse3Extra = 8.0F / 3.0F;

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition bone = modelPartData.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, 0.0F, 0.0F, bonePitchYaw, bonePitchYaw, 0));
        PartDefinition front = bone.addOrReplaceChild("front", CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));
        PartDefinition middle = bone.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(0, 16).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition back = bone.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    public void render(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha, float tickDelta, AncientHornProjectile entity) {
        matrices.scale(1.0F, 1.0F, 1.0F);
        float aliveDelta = entity.aliveTicks + tickDelta;

        float pulse = (((float) Math.sin((aliveDelta * pi) * 0.2f) * 0.16666667f) + 1);
        float pulse2 = (((float) Math.sin(((aliveDelta + pulse2Extra) * pi) * 0.2f) * 0.16666667f) + 1);
        float pulse3 = (((float) Math.sin(((aliveDelta + pulse3Extra) * pi) * 0.2f) * 0.16666667f) + 1);

        this.front.xScale = pulse;
        this.front.yScale = pulse;
        this.front.z = pulse3 * 2.0F - 6.0F;

        this.middle.xScale = pulse2;
        this.middle.yScale = pulse2;
        this.middle.z = pulse * 2.0F - 2.0F;

        this.back.xScale = pulse3;
        this.back.yScale = pulse3;
        this.back.z = pulse2 * 2.0F + 2.0F;

        this.bone.render(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {

    }
}
