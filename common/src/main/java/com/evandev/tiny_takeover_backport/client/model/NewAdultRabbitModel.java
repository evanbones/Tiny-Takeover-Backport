package com.evandev.tiny_takeover_backport.client.model;

import com.evandev.tiny_takeover_backport.config.ModConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Rabbit;
import org.jetbrains.annotations.NotNull;

public class NewAdultRabbitModel<T extends Rabbit> extends RabbitModel<T> {

    private final ModelPart root;
    private final ModelPart realHead;
    private final ModelPart realLeftHaunch;
    private final ModelPart realRightHaunch;
    private final ModelPart realLeftFrontLeg;
    private final ModelPart realRightFrontLeg;
    private float jumpRotation;

    public NewAdultRabbitModel(ModelPart root) {
        super(root);
        this.root = root;
        ModelPart body = root.getChild("body");
        this.realHead = body.getChild("head");

        ModelPart frontlegs = body.getChild("frontlegs");
        this.realLeftFrontLeg = frontlegs.getChild("left_front_leg");
        this.realRightFrontLeg = frontlegs.getChild("right_front_leg");

        ModelPart backlegs = root.getChild("backlegs");
        this.realLeftHaunch = backlegs.getChild("left_hind_leg").getChild("left_haunch");
        this.realRightHaunch = backlegs.getChild("right_hind_leg").getChild("right_haunch");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("left_hind_foot", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("right_hind_foot", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("left_haunch", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("right_haunch", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("nose", CubeListBuilder.create(), PartPose.ZERO);

        PartDefinition body = root.addOrReplaceChild(
                "body",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -6.0F, -9.0F, 8.0F, 6.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 23.0F, 4.0F, -0.3927F, 0.0F, 0.0F)
        );
        body.addOrReplaceChild(
                "tail", CubeListBuilder.create().texOffs(20, 16).addBox(-2.0F, -3.0084F, -1.0125F, 4.0F, 4.0F, 4.0F), PartPose.offset(0.0F, -4.9916F, 0.0125F)
        );
        PartDefinition head = body.addOrReplaceChild(
                "head",
                CubeListBuilder.create().texOffs(0, 16).addBox(-2.5F, -3.0F, -4.0F, 5.0F, 5.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -5.2929F, -8.1213F, 0.3927F, 0.0F, 0.0F)
        );
        head.addOrReplaceChild(
                "left_ear", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -4.2929F, -0.1213F, 2.0F, 5.0F, 1.0F), PartPose.offset(1.5F, -3.7071F, -0.8787F)
        );
        head.addOrReplaceChild(
                "right_ear",
                CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, -4.2929F, -0.1213F, 2.0F, 5.0F, 1.0F),
                PartPose.offset(-1.5F, -3.7071F, -0.8787F)
        );
        PartDefinition frontLegs = body.addOrReplaceChild("frontlegs", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5349F, -6.3108F));
        frontLegs.addOrReplaceChild(
                "right_front_leg",
                CubeListBuilder.create().texOffs(36, 18).addBox(-0.9F, -1.0F, -0.9F, 2.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(-2.0F, 1.9239F, 0.3827F, 0.3927F, 0.0F, 0.0F)
        );
        frontLegs.addOrReplaceChild(
                "left_front_leg",
                CubeListBuilder.create().texOffs(44, 18).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(2.0F, 1.9239F, 0.4827F, 0.3927F, 0.0F, 0.0F)
        );
        PartDefinition backLegs = root.addOrReplaceChild("backlegs", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, 4.0F));
        PartDefinition rightBackLeg = backLegs.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.5F, 0.0F));
        rightBackLeg.addOrReplaceChild(
                "right_haunch",
                CubeListBuilder.create().texOffs(20, 24).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 1.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, 0.3927F, 0.0F)
        );
        PartDefinition leftBackLeg = backLegs.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(3.0F, 0.5F, 0.0F));
        leftBackLeg.addOrReplaceChild(
                "left_haunch",
                CubeListBuilder.create().texOffs(36, 24).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 1.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, -0.3927F, 0.0F)
        );
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void prepareMobModel(@NotNull T entity, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
        this.jumpRotation = Mth.sin(entity.getJumpCompletion(partialTick) * (float) Math.PI);
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.realHead.xRot = headPitch * ((float) Math.PI / 180F);
        this.realHead.yRot = netHeadYaw * ((float) Math.PI / 180F);

        this.realLeftHaunch.xRot = (this.jumpRotation * 50.0F - 21.0F) * ((float) Math.PI / 180F);
        this.realRightHaunch.xRot = (this.jumpRotation * 50.0F - 21.0F) * ((float) Math.PI / 180F);

        this.realLeftFrontLeg.xRot = (this.jumpRotation * -40.0F - 11.0F) * ((float) Math.PI / 180F);
        this.realRightFrontLeg.xRot = (this.jumpRotation * -40.0F - 11.0F) * ((float) Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        if (ModConfig.get().rabbitBoundingBox) {
            this.root.render(poseStack, buffer, packedLight, packedOverlay, color);
        } else {
            poseStack.pushPose();
            poseStack.scale(0.6F, 0.6F, 0.6F);
            poseStack.translate(0.0F, 1.0F, 0.0F);
            this.root.render(poseStack, buffer, packedLight, packedOverlay, color);
            poseStack.popPose();
        }
    }
}