package com.evandev.tiny_takeover_backport.client.model;

import com.evandev.tiny_takeover_backport.client.animation.FoxBabyAnimation;
import com.evandev.tiny_takeover_backport.client.animation.PartAnimator;
import net.minecraft.client.model.FoxModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Fox;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BabyFoxModel<T extends Fox> extends FoxModel<T> {

    private final ModelPart body;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart tail;
    private final Map<String, ModelPart> animatedParts;
    private float partialTick;
    private float legMotionPos;

    public BabyFoxModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.tail = this.body.getChild("tail");
        this.animatedParts = Map.of(
                "head", this.head, "body", this.body, "tail", this.tail,
                "right_hind_leg", this.rightHindLeg, "left_hind_leg", this.leftHindLeg,
                "right_front_leg", this.rightFrontLeg, "left_front_leg", this.leftFrontLeg
        );
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();
        root.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-3.0F, -2.125F, -5.125F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(18, 20)
                        .addBox(-1.0F, 0.875F, -7.125F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(22, 8)
                        .addBox(-3.0F, -4.125F, -4.125F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(22, 11)
                        .addBox(1.0F, -4.125F, -4.125F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 18.125F, 0.125F)
        );
        root.addOrReplaceChild(
                "right_hind_leg",
                CubeListBuilder.create().texOffs(22, 4).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.5F, 22.0F, 4.0F)
        );
        root.addOrReplaceChild(
                "left_hind_leg",
                CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.5F, 22.0F, 4.0F)
        );
        root.addOrReplaceChild(
                "right_front_leg",
                CubeListBuilder.create().texOffs(22, 4).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.5F, 22.0F, 0.0F)
        );
        root.addOrReplaceChild(
                "left_front_leg",
                CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.5F, 22.0F, 0.0F)
        );
        PartDefinition body = root.addOrReplaceChild(
                "body",
                CubeListBuilder.create().texOffs(0, 10).addBox(-2.5F, -2.0F, -3.0F, 5.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 20.0F, 2.0F)
        );
        body.addOrReplaceChild(
                "tail",
                CubeListBuilder.create().texOffs(0, 20).addBox(-1.5F, -1.48F, -1.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -0.5F, 3.0F)
        );
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void prepareMobModel(@NotNull T entity, float limbSwing, float limbSwingAmount, float partialTick) {
        this.partialTick = partialTick;
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        for (ModelPart part : this.animatedParts.values()) {
            part.resetPose();
        }

        float ageScale = 0.5F;
        boolean isCrouching = entity.isCrouching();
        boolean isSleeping = entity.isSleeping();
        boolean isFaceplanted = entity.isFaceplanted();

        this.head.zRot = entity.getHeadRollAngle(this.partialTick);
        this.rightHindLeg.visible = true;
        this.leftHindLeg.visible = true;
        this.rightFrontLeg.visible = true;
        this.leftFrontLeg.visible = true;
        PartAnimator.applyWalk(this.animatedParts, FoxBabyAnimation.FOX_BABY_WALK, limbSwing, limbSwingAmount, 1.0F, 2.5F);

        if (isCrouching) {
            float crouchAmount = entity.getCrouchAmount(this.partialTick);
            this.body.xRot += 0.10471976F;
            this.head.y += crouchAmount * ageScale;
            float wiggleAmount = Mth.cos(ageInTicks) * 0.05F;
            this.body.yRot = wiggleAmount;
            this.rightHindLeg.zRot = wiggleAmount;
            this.leftHindLeg.zRot = wiggleAmount;
            this.rightFrontLeg.zRot = wiggleAmount / 2.0F;
            this.leftFrontLeg.zRot = wiggleAmount / 2.0F;
            this.body.y += crouchAmount / 6.0F;
        } else if (isSleeping) {
            this.rightHindLeg.visible = false;
            this.leftHindLeg.visible = false;
            this.rightFrontLeg.visible = false;
            this.leftFrontLeg.visible = false;
            this.body.zRot = (float) (-Math.PI / 2);
            this.body.xRot = (float) (-Math.PI / 18);
            this.body.y++;
            this.body.z--;
            this.body.x--;
            this.tail.xRot = -2.1816616F;
            this.tail.x -= 0.7F;
            this.tail.z += 0.6F;
            this.tail.y += 0.9F;
            this.head.x -= 2.0F;
            this.head.y += 2.8F;
            this.head.z -= 4.0F;
            this.head.yRot = (float) (-Math.PI * 2.0 / 3.0);
            this.head.zRot = 0.0F;
        } else if (entity.isSitting()) {
            this.head.xRot = 0.0F;
            this.head.yRot = 0.0F;
            this.body.xRot = -0.959931F;
            this.body.z -= 4.5F * ageScale;
            this.body.y += 3.0F * ageScale;
            this.tail.y -= 0.6F;
            this.tail.z -= 2.0F * ageScale;
            this.tail.xRot = 0.95993114F;
            this.head.y -= 0.75F;
            this.rightFrontLeg.xRot = (float) (-Math.PI / 12);
            this.leftFrontLeg.xRot = (float) (-Math.PI / 12);
            this.rightFrontLeg.z--;
            this.leftFrontLeg.z--;
            this.rightFrontLeg.x += 0.01F;
            this.leftFrontLeg.x -= 0.01F;
            this.rightHindLeg.z -= 3.75F;
            this.leftHindLeg.z -= 3.75F;
            this.rightHindLeg.x += 0.01F;
            this.leftHindLeg.x -= 0.01F;
        }

        if (!isSleeping && !isFaceplanted && !isCrouching) {
            this.head.xRot = headPitch * Mth.DEG_TO_RAD;
            this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        }

        if (isSleeping) {
            this.head.xRot = 0.0F;
            this.head.yRot = (float) (-Math.PI * 2.0 / 3.0);
            this.head.zRot = Mth.cos(ageInTicks * 0.027F) / 22.0F;
        }

        if (isFaceplanted) {
            this.legMotionPos += 0.67F;
            this.rightHindLeg.xRot = Mth.cos(this.legMotionPos * 0.4662F) * 0.1F;
            this.leftHindLeg.xRot = Mth.cos(this.legMotionPos * 0.4662F + (float) Math.PI) * 0.1F;
            this.rightFrontLeg.xRot = Mth.cos(this.legMotionPos * 0.4662F + (float) Math.PI) * 0.1F;
            this.leftFrontLeg.xRot = Mth.cos(this.legMotionPos * 0.4662F) * 0.1F;
        }
    }
}
