package com.evandev.tiny_takeover_backport.client.model;

import net.minecraft.client.model.AxolotlModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import org.jetbrains.annotations.NotNull;

public class BabyAxolotlModel extends AxolotlModel {
    private final ModelPart babyBody;
    private final ModelPart babyLeftHindLeg;
    private final ModelPart babyRightHindLeg;
    private final ModelPart babyLeftFrontLeg;
    private final ModelPart babyRightFrontLeg;

    public BabyAxolotlModel(ModelPart root) {
        super(root);
        this.babyBody = root.getChild("body");
        this.babyLeftHindLeg = this.babyBody.getChild("left_hind_leg");
        this.babyRightHindLeg = this.babyBody.getChild("right_hind_leg");
        this.babyLeftFrontLeg = this.babyBody.getChild("left_front_leg");
        this.babyRightFrontLeg = this.babyBody.getChild("right_front_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-2.0F, -0.75F, -2.75F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 12)
                        .addBox(0.0F, -1.75F, -2.75F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 22.75F, 1.75F)
        );

        body.addOrReplaceChild(
                "right_front_leg",
                CubeListBuilder.create().texOffs(20, 16).addBox(-3.0F, 0.0F, -0.5F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)),
                PartPose.offset(-2.0F, 0.25F, -1.25F)
        );
        body.addOrReplaceChild(
                "right_hind_leg",
                CubeListBuilder.create().texOffs(20, 14).addBox(-3.0F, 0.0F, -0.5F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)),
                PartPose.offset(-2.0F, 0.25F, 1.75F)
        );
        body.addOrReplaceChild(
                "left_front_leg",
                CubeListBuilder.create().texOffs(20, 13).addBox(0.0F, 0.0F, -0.5F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)),
                PartPose.offset(2.0F, 0.25F, -1.25F)
        );
        body.addOrReplaceChild(
                "left_hind_leg",
                CubeListBuilder.create().texOffs(20, 14).addBox(0.0F, 0.0F, -0.5F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)),
                PartPose.offset(2.0F, 0.25F, 1.75F)
        );
        body.addOrReplaceChild(
                "tail",
                CubeListBuilder.create().texOffs(10, 9).addBox(0.0F, -1.5F, -1.0F, 0.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -0.25F, 3.25F)
        );
        PartDefinition head = body.addOrReplaceChild(
                "head",
                CubeListBuilder.create().texOffs(0, 8).addBox(-3.0F, -2.0F, -4.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.25F, -2.75F)
        );
        head.addOrReplaceChild(
                "left_gills",
                CubeListBuilder.create().texOffs(20, 8).addBox(0.0F, -3.5F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(3.0F, -0.5F, -2.0F)
        );
        head.addOrReplaceChild(
                "right_gills",
                CubeListBuilder.create().texOffs(20, 3).addBox(-3.0F, -3.5F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-3.0F, -0.5F, -2.0F)
        );
        head.addOrReplaceChild(
                "top_gills",
                CubeListBuilder.create().texOffs(20, 0).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -2.0F, -2.01F)
        );
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(@NotNull Axolotl entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        this.babyLeftFrontLeg.xRot = 0.0F;
        this.babyLeftFrontLeg.yRot = 0.0F;
        this.babyLeftFrontLeg.zRot = 0.0F;
        this.babyRightFrontLeg.xRot = 0.0F;
        this.babyRightFrontLeg.yRot = 0.0F;
        this.babyRightFrontLeg.zRot = 0.0F;
        this.babyLeftHindLeg.xRot = 0.0F;
        this.babyLeftHindLeg.yRot = 0.0F;
        this.babyLeftHindLeg.zRot = 0.0F;
        this.babyRightHindLeg.xRot = 0.0F;
        this.babyRightHindLeg.yRot = 0.0F;
        this.babyRightHindLeg.zRot = 0.0F;

        if (entity.isPlayingDead()) {
            this.babyLeftFrontLeg.yRot = 0.3F;
            this.babyLeftFrontLeg.zRot = 0.5F;
            this.babyRightFrontLeg.yRot = -0.3F;
            this.babyRightFrontLeg.zRot = -0.5F;
            this.babyLeftHindLeg.yRot = -0.3F;
            this.babyLeftHindLeg.zRot = 0.5F;
            this.babyRightHindLeg.yRot = 0.3F;
            this.babyRightHindLeg.zRot = -0.5F;
        } else if (entity.isInWaterOrBubble()) {
            boolean isMoving = limbSwingAmount > 1.0E-5F;
            if (isMoving) {
                float wave = Mth.cos(ageInTicks * 0.33F) * 0.45F;
                this.babyLeftFrontLeg.yRot = -0.2F + wave;
                this.babyRightFrontLeg.yRot = 0.2F + wave;
                this.babyLeftHindLeg.yRot = -0.2F - wave;
                this.babyRightHindLeg.yRot = 0.2F - wave;
            } else {
                float wave = Mth.cos(ageInTicks * 0.075F) * 0.1F;
                this.babyLeftFrontLeg.yRot = wave;
                this.babyRightFrontLeg.yRot = -wave;
                this.babyLeftHindLeg.yRot = -wave;
                this.babyRightHindLeg.yRot = wave;
            }
        } else if (entity.onGround()) {
            this.babyLeftFrontLeg.zRot = 0.25F;
            this.babyRightFrontLeg.zRot = -0.25F;
            this.babyLeftHindLeg.zRot = 0.25F;
            this.babyRightHindLeg.zRot = -0.25F;

            boolean isMoving = limbSwingAmount > 1.0E-5F;
            if (isMoving) {
                float wave = Mth.cos(ageInTicks * 0.22F) * 0.5F;
                this.babyLeftFrontLeg.yRot = wave;
                this.babyRightFrontLeg.yRot = wave;
                this.babyLeftHindLeg.yRot = -wave;
                this.babyRightHindLeg.yRot = -wave;
            }
        } else {
            this.babyLeftFrontLeg.zRot = 0.8F;
            this.babyRightFrontLeg.zRot = -0.8F;
            this.babyLeftHindLeg.zRot = 0.8F;
            this.babyRightHindLeg.zRot = -0.8F;
        }

        this.babyBody.y += 2.75F;
    }
}