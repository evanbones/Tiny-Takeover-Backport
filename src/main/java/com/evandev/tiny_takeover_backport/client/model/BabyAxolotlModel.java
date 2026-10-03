package com.evandev.tiny_takeover_backport.client.model;

import com.evandev.tiny_takeover_backport.client.animation.BabyAxolotlAnimation;
import com.evandev.tiny_takeover_backport.client.animation.PartAnimator;
import com.evandev.tiny_takeover_backport.entity.AxolotlAnimationStates;
import net.minecraft.client.model.AxolotlModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BabyAxolotlModel extends AxolotlModel {
    private final Map<String, ModelPart> animatedParts;

    public BabyAxolotlModel(ModelPart root) {
        super(root);
        ModelPart body = root.getChild("body");
        ModelPart head = body.getChild("head");
        this.animatedParts = Map.of(
                "body", body,
                "head", head,
                "tail", body.getChild("tail"),
                "left_gills", head.getChild("left_gills"),
                "right_gills", head.getChild("right_gills"),
                "top_gills", head.getChild("top_gills"),
                "left_front_leg", body.getChild("left_front_leg"),
                "right_front_leg", body.getChild("right_front_leg"),
                "left_hind_leg", body.getChild("left_hind_leg"),
                "right_hind_leg", body.getChild("right_hind_leg")
        );
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
        PartDefinition rightHindLeg = body.addOrReplaceChild(
                "right_hind_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.0F, 0.25F, 1.75F, 0.0F, 1.5708F, 1.5708F)
        );
        rightHindLeg.addOrReplaceChild(
                "right_leg_r1",
                CubeListBuilder.create().texOffs(20, 14).addBox(0.0F, 0.0F, -0.5F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 1.5708F)
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
        for (ModelPart part : this.animatedParts.values()) {
            part.resetPose();
        }

        AxolotlAnimationStates states = (AxolotlAnimationStates) entity;
        if (states.tiny_takeover_backport$getWalkAnimationState().isStarted()) {
            PartAnimator.applyWalk(this.animatedParts, BabyAxolotlAnimation.AXOLOTL_WALK_FLOOR, limbSwing, limbSwingAmount, 15.0F, 30.0F);
        }
        PartAnimator.animate(this.animatedParts, states.tiny_takeover_backport$getSwimAnimationState(), BabyAxolotlAnimation.BABY_AXOLOTL_SWIM, ageInTicks);
        PartAnimator.animate(this.animatedParts, states.tiny_takeover_backport$getWalkAnimationState(), BabyAxolotlAnimation.WALK_FLOOR_UNDERWATER, ageInTicks);
        PartAnimator.animate(this.animatedParts, states.tiny_takeover_backport$getIdleOnGroundAnimationState(), BabyAxolotlAnimation.BABY_AXOLOTL_IDLE_FLOOR, ageInTicks);
        PartAnimator.animate(this.animatedParts, states.tiny_takeover_backport$getIdleUnderWaterAnimationState(), BabyAxolotlAnimation.IDLE_UNDERWATER, ageInTicks);
        PartAnimator.animate(this.animatedParts, states.tiny_takeover_backport$getIdleUnderWaterOnGroundAnimationState(), BabyAxolotlAnimation.IDLE_FLOOR_UNDERWATER, ageInTicks);
        PartAnimator.animate(this.animatedParts, states.tiny_takeover_backport$getPlayDeadAnimationState(), BabyAxolotlAnimation.BABY_AXOLOTL_PLAY_DEAD, ageInTicks);
    }
}
