package com.evandev.tiny_takeover_backport.client.model;

import net.minecraft.client.model.TurtleModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Turtle;
import org.jetbrains.annotations.NotNull;

public class BabyTurtleModel extends TurtleModel {

    public BabyTurtleModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 2.0F, 4.0F), PartPose.offset(0.0F, 22.9F, 1.0F));
        root.addOrReplaceChild(
                "head", CubeListBuilder.create().texOffs(0, 6).addBox(-1.5F, -2.0F, -3.0F, 3.0F, 3.0F, 3.0F), PartPose.offset(0.0F, 22.9F, -1.0F)
        );
        root.addOrReplaceChild(
                "right_hind_leg", CubeListBuilder.create().texOffs(-1, 0).addBox(-2.0F, 0.0F, -0.5F, 2.0F, 0.01F, 1.0F), PartPose.offset(-2.0F, 23.9F, 2.5F)
        );
        root.addOrReplaceChild(
                "left_hind_leg", CubeListBuilder.create().texOffs(-1, 1).addBox(0.0F, 0.0F, -0.5F, 2.0F, 0.01F, 1.0F), PartPose.offset(2.0F, 23.9F, 2.5F)
        );
        root.addOrReplaceChild(
                "right_front_leg", CubeListBuilder.create().texOffs(8, 6).addBox(-2.0F, 0.0F, -0.5F, 2.0F, 0.01F, 1.0F), PartPose.offset(-2.0F, 23.9F, -0.5F)
        );
        root.addOrReplaceChild(
                "left_front_leg", CubeListBuilder.create().texOffs(8, 7).addBox(0.0F, 0.0F, -0.5F, 2.0F, 0.01F, 1.0F), PartPose.offset(2.0F, 23.9F, -0.5F)
        );

        root.addOrReplaceChild("egg_belly", CubeListBuilder.create(), PartPose.ZERO);

        return LayerDefinition.create(mesh, 16, 16);
    }

    @Override
    public void setupAnim(@NotNull Turtle entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * Mth.DEG_TO_RAD;
        this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.rightHindLeg.yRot = 0.0F;
        this.leftHindLeg.yRot = 0.0F;
        this.rightFrontLeg.yRot = 0.0F;
        this.leftFrontLeg.yRot = 0.0F;
        this.rightFrontLeg.zRot = 0.0F;
        this.leftFrontLeg.zRot = 0.0F;

        if (!entity.isInWater() && entity.onGround()) {
            float layEgg = entity.isLayingEgg() ? 4.0F : 1.0F;
            float layEggAmplitude = entity.isLayingEgg() ? 2.0F : 1.0F;
            float swingPos = limbSwing * 5.0F;
            float frontSwing = Mth.cos(layEgg * swingPos);
            float hindSwing = Mth.cos(swingPos);
            this.rightFrontLeg.yRot = -frontSwing * 8.0F * limbSwingAmount * layEggAmplitude;
            this.leftFrontLeg.yRot = frontSwing * 8.0F * limbSwingAmount * layEggAmplitude;
            this.rightHindLeg.yRot = -hindSwing * 3.0F * limbSwingAmount;
            this.leftHindLeg.yRot = hindSwing * 3.0F * limbSwingAmount;
        } else {
            float swing = Mth.cos(limbSwing * 0.6662F * 0.6F) * 0.5F * limbSwingAmount;
            this.rightHindLeg.xRot = swing;
            this.leftHindLeg.xRot = -swing;
            this.rightFrontLeg.zRot = -swing;
            this.leftFrontLeg.zRot = swing;
        }
    }
}