package com.evandev.tiny_takeover_backport.client.model;

import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.jetbrains.annotations.NotNull;

public class BabyHorseModel<T extends AbstractHorse> extends HorseModel<T> {
    private final ModelPart tail;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private float partialTick;

    public BabyHorseModel(ModelPart root) {
        super(root);
        this.tail = this.body.getChild("tail");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftFrontLeg = root.getChild("left_front_leg");
    }

    public static LayerDefinition createBodyLayer() {
        return LayerDefinition.create(createBabyMesh(CubeDeformation.NONE), 64, 64);
    }

    public static MeshDefinition createBabyMesh(CubeDeformation g) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("right_hind_baby_leg", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("left_hind_baby_leg", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("right_front_baby_leg", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("left_front_baby_leg", CubeListBuilder.create(), PartPose.ZERO);

        PartDefinition Body = root.addOrReplaceChild(
                "body", CubeListBuilder.create().texOffs(0, 13).addBox(-4.0F, -3.5F, -7.0F, 8.0F, 7.0F, 14.0F, g), PartPose.offset(0.0F, 12.5F, 0.0F)
        );
        Body.addOrReplaceChild(
                "tail",
                CubeListBuilder.create().texOffs(24, 34).addBox(-1.5F, -1.5F, -1.0F, 3.0F, 3.0F, 8.0F, g),
                PartPose.offsetAndRotation(0.0F, -1.0F, 7.0F, -0.7418F, 0.0F, 0.0F)
        );

        Body.addOrReplaceChild("saddle", CubeListBuilder.create(), PartPose.ZERO);
        Body.addOrReplaceChild("left_chest", CubeListBuilder.create(), PartPose.ZERO);
        Body.addOrReplaceChild("right_chest", CubeListBuilder.create(), PartPose.ZERO);

        root.addOrReplaceChild(
                "left_hind_leg", CubeListBuilder.create().texOffs(12, 46).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 9.0F, 3.0F, g), PartPose.offset(2.4F, 16.0F, 5.4F)
        );
        root.addOrReplaceChild(
                "right_hind_leg", CubeListBuilder.create().texOffs(0, 46).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 9.0F, 3.0F, g), PartPose.offset(-2.4F, 16.0F, 5.4F)
        );
        root.addOrReplaceChild(
                "left_front_leg", CubeListBuilder.create().texOffs(12, 34).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 9.0F, 3.0F, g), PartPose.offset(2.4F, 16.0F, -5.4F)
        );
        root.addOrReplaceChild(
                "right_front_leg", CubeListBuilder.create().texOffs(0, 34).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 9.0F, 3.0F, g), PartPose.offset(-2.4F, 16.0F, -5.4F)
        );

        PartDefinition neck = root.addOrReplaceChild(
                "head_parts",
                CubeListBuilder.create().texOffs(30, 0).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 8.0F, 4.0F, g),
                PartPose.offsetAndRotation(0.0F, 10.0F, -6.0F, 0.6109F, 0.0F, 0.0F)
        );

        neck.addOrReplaceChild("head_saddle", CubeListBuilder.create(), PartPose.ZERO);
        neck.addOrReplaceChild("left_saddle_mouth", CubeListBuilder.create(), PartPose.ZERO);
        neck.addOrReplaceChild("right_saddle_mouth", CubeListBuilder.create(), PartPose.ZERO);
        neck.addOrReplaceChild("left_saddle_line", CubeListBuilder.create(), PartPose.ZERO);
        neck.addOrReplaceChild("right_saddle_line", CubeListBuilder.create(), PartPose.ZERO);
        neck.addOrReplaceChild("mouth_saddle_wrap", CubeListBuilder.create(), PartPose.ZERO);

        PartDefinition head = neck.addOrReplaceChild(
                "head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.9484F, -6.705F, 6.0F, 4.0F, 9.0F, g), PartPose.offset(0.0F, -6.0516F, -0.2951F)
        );
        head.addOrReplaceChild(
                "left_ear",
                CubeListBuilder.create().texOffs(0, 4).addBox(-1.0F, -2.5F, -0.8F, 2.0F, 3.0F, 1.0F, g),
                PartPose.offsetAndRotation(2.0F, -4.2484F, 1.9451F, 0.0F, 0.0F, 0.2618F)
        );
        head.addOrReplaceChild(
                "right_ear",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.5F, -0.5F, 2.0F, 3.0F, 1.0F, g),
                PartPose.offsetAndRotation(-2.0F, -4.2484F, 1.645F, 0.0F, 0.0F, -0.2618F)
        );

        return mesh;
    }

    @Override
    public void prepareMobModel(@NotNull T entity, float limbSwing, float limbSwingAmount, float partialTick) {
        this.partialTick = partialTick;
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.headParts.resetPose();
        this.body.resetPose();
        this.tail.resetPose();
        this.rightHindLeg.resetPose();
        this.leftHindLeg.resetPose();
        this.rightFrontLeg.resetPose();
        this.leftFrontLeg.resetPose();

        float clampedYRot = Mth.clamp(netHeadYaw, -20.0F, 20.0F);
        float headRotXRad = headPitch * Mth.DEG_TO_RAD;
        if (limbSwingAmount > 0.2F) {
            headRotXRad += Mth.cos(limbSwing * 0.8F) * 0.15F * limbSwingAmount;
        }

        float eating = entity.getEatAnim(this.partialTick);
        float standing = entity.getStandAnim(this.partialTick);
        float iStanding = 1.0F - standing;
        float feedingAnim = entity.getMouthAnim(this.partialTick);
        this.headParts.xRot = (float) (Math.PI / 6) + headRotXRad;
        this.headParts.yRot = clampedYRot * Mth.DEG_TO_RAD;
        float waterMultiplier = entity.isInWater() ? 0.2F : 1.0F;
        float legAnim1 = Mth.cos(waterMultiplier * limbSwing * 0.6662F + (float) Math.PI);
        float legXRotAnim = legAnim1 * 0.8F * limbSwingAmount;
        float baseHeadAngle = (1.0F - Math.max(standing, eating)) * ((float) (Math.PI / 6) + headRotXRad + feedingAnim * Mth.sin(ageInTicks) * 0.05F);
        this.headParts.xRot = standing * ((float) (Math.PI / 12) + headRotXRad) + eating * (2.1816616F + Mth.sin(ageInTicks) * 0.05F) + baseHeadAngle;
        this.headParts.yRot = standing * clampedYRot * Mth.DEG_TO_RAD + (1.0F - Math.max(standing, eating)) * this.headParts.yRot;
        this.headParts.y = this.headParts.y + Mth.lerp(eating, Mth.lerp(standing, 0.0F, -2.0F), 2.0F);
        this.headParts.z = Mth.lerp(standing, this.headParts.z, -4.0F);
        this.body.xRot = standing * (float) (-Math.PI / 4) + iStanding * this.body.xRot;
        this.leftFrontLeg.y = this.leftFrontLeg.y - 4.0F * standing;
        this.rightFrontLeg.y = this.leftFrontLeg.y;
        this.rightFrontLeg.z = this.leftFrontLeg.z;
        float standAngle = (float) (Math.PI / 12) * standing;
        float bobValue = Mth.cos(ageInTicks * 0.6F + (float) Math.PI);
        float legStandingXRotOffset = (float) (-Math.PI / 3);
        float rlegRot = (legStandingXRotOffset + bobValue) * standing + legXRotAnim * iStanding;
        float llegRot = (legStandingXRotOffset - bobValue) * standing - legXRotAnim * iStanding;
        this.leftHindLeg.xRot = standAngle - legAnim1 * 0.5F * limbSwingAmount * iStanding;
        this.rightHindLeg.xRot = standAngle + legAnim1 * 0.5F * limbSwingAmount * iStanding;
        this.leftFrontLeg.xRot = rlegRot;
        this.rightFrontLeg.xRot = llegRot;
        float ageScale = 0.5F;
        this.tail.xRot = (float) (-Math.PI / 2) + (float) (Math.PI / 6) + limbSwingAmount * 0.75F;
        this.tail.y += limbSwingAmount * ageScale;
        this.tail.z += limbSwingAmount * 2.0F * ageScale;
        this.tail.yRot = entity.tailCounter > 0 ? Mth.cos(ageInTicks * 0.7F) : 0.0F;
    }
}
