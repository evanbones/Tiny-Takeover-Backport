package com.evandev.tiny_takeover_backport.client.model;

import net.minecraft.client.model.ModelUtils;
import net.minecraft.client.model.PandaModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Panda;
import org.jetbrains.annotations.NotNull;

public class BabyPandaModel extends PandaModel {
    private float sitAmount;
    private float lieOnBackAmount;

    public BabyPandaModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild(
                "body", CubeListBuilder.create().texOffs(0, 11).addBox(-4.5F, -3.5F, -5.5F, 9.0F, 7.0F, 11.0F), PartPose.offset(0.0F, 18.5F, 2.5F)
        );
        root.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-3.5F, -3.0F, -5.0F, 7.0F, 6.0F, 5.0F)
                        .texOffs(24, 6)
                        .addBox(-2.0F, 1.0F, -6.0F, 4.0F, 2.0F, 1.0F)
                        .texOffs(24, 0)
                        .addBox(-4.5F, -4.0F, -3.5F, 3.0F, 3.0F, 1.0F)
                        .texOffs(33, 0)
                        .addBox(1.5F, -4.0F, -3.5F, 3.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 19.0F, -3.0F)
        );
        root.addOrReplaceChild(
                "right_hind_leg", CubeListBuilder.create().texOffs(0, 34).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 2.0F, 3.0F), PartPose.offset(-3.0F, 22.0F, 6.5F)
        );
        root.addOrReplaceChild(
                "left_hind_leg", CubeListBuilder.create().texOffs(12, 34).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 2.0F, 3.0F), PartPose.offset(3.0F, 22.0F, 6.5F)
        );
        root.addOrReplaceChild(
                "right_front_leg", CubeListBuilder.create().texOffs(0, 29).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 2.0F, 3.0F), PartPose.offset(-3.0F, 22.0F, -1.5F)
        );
        root.addOrReplaceChild(
                "left_front_leg", CubeListBuilder.create().texOffs(12, 29).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 2.0F, 3.0F), PartPose.offset(3.0F, 22.0F, -1.5F)
        );
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void prepareMobModel(@NotNull Panda entity, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
        this.sitAmount = entity.getSitAmount(partialTick);
        this.lieOnBackAmount = entity.getLieOnBackAmount(partialTick);
    }

    @Override
    public void setupAnim(@NotNull Panda entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.resetPose();
        this.body.resetPose();
        this.rightHindLeg.resetPose();
        this.leftHindLeg.resetPose();
        this.rightFrontLeg.resetPose();
        this.leftFrontLeg.resetPose();

        this.head.xRot = headPitch * Mth.DEG_TO_RAD;
        this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        if (entity.getUnhappyCounter() > 0) {
            this.head.yRot = 0.35F * Mth.sin(0.6F * ageInTicks);
            this.head.zRot = 0.35F * Mth.sin(0.6F * ageInTicks);
            this.rightFrontLeg.xRot = -0.75F * Mth.sin(0.3F * ageInTicks);
            this.leftFrontLeg.xRot = 0.75F * Mth.sin(0.3F * ageInTicks);
        }

        if (entity.isSneezing()) {
            int sneezeTime = entity.getSneezeCounter();
            if (sneezeTime < 15) {
                this.head.xRot = (float) (-Math.PI / 4) * sneezeTime / 14.0F;
            } else if (sneezeTime < 20) {
                float internalSneezePos = (float) ((sneezeTime - 15) / 5);
                this.head.xRot = (float) (-Math.PI / 4) + (float) (Math.PI / 4) * internalSneezePos;
            }
        }

        if (this.sitAmount > 0.0F) {
            this.body.xRot = ModelUtils.rotlerpRad(this.body.xRot, (float) (Math.PI / 18), this.sitAmount);
            this.body.z = Mth.lerp(this.sitAmount, this.body.z, -1.5F);
            this.head.z = Mth.lerp(this.sitAmount, this.head.z, -11.5F);
            this.head.y = Mth.lerp(this.sitAmount, this.head.y, 17.5F);
            this.rightFrontLeg.z = Mth.lerp(this.sitAmount, this.rightFrontLeg.z, -5.0F);
            this.leftFrontLeg.z = Mth.lerp(this.sitAmount, this.leftFrontLeg.z, -5.0F);
            this.rightHindLeg.z = Mth.lerp(this.sitAmount, this.rightHindLeg.z, 3.0F);
            this.leftHindLeg.z = Mth.lerp(this.sitAmount, this.leftHindLeg.z, 3.0F);
            this.rightFrontLeg.zRot = -0.27079642F;
            this.leftFrontLeg.zRot = 0.27079642F;
            this.rightHindLeg.zRot = 0.5707964F;
            this.leftHindLeg.zRot = -0.5707964F;

            if (entity.isEating()) {
                this.head.xRot = (float) (Math.PI / 2) + 0.2F * Mth.sin(ageInTicks * 0.6F);
                this.rightFrontLeg.xRot = -0.4F - 0.2F * Mth.sin(ageInTicks * 0.6F);
                this.leftFrontLeg.xRot = -0.4F - 0.2F * Mth.sin(ageInTicks * 0.6F);
            }

            if (entity.isScared()) {
                this.head.xRot = 2.1707964F;
                this.rightFrontLeg.xRot = -0.9F;
                this.leftFrontLeg.xRot = -0.9F;
            }
        }

        if (this.lieOnBackAmount > 0.0F) {
            this.rightHindLeg.xRot = -0.6F * Mth.sin(ageInTicks * 0.15F);
            this.leftHindLeg.xRot = 0.6F * Mth.sin(ageInTicks * 0.15F);
            this.rightFrontLeg.xRot = 0.3F * Mth.sin(ageInTicks * 0.25F);
            this.leftFrontLeg.xRot = -0.3F * Mth.sin(ageInTicks * 0.25F);
            this.head.xRot = ModelUtils.rotlerpRad(this.head.xRot, (float) (Math.PI / 2), this.lieOnBackAmount);
        }
    }
}
