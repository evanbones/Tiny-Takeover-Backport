package com.evandev.tiny_takeover_backport.client.model;

import net.minecraft.client.model.ChestedHorseModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import org.jetbrains.annotations.NotNull;

public class BabyDonkeyModel<T extends AbstractChestedHorse> extends ChestedHorseModel<T> {

    public BabyDonkeyModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        return createBabyLayer();
    }

    public static LayerDefinition createBabyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("right_hind_baby_leg", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("left_hind_baby_leg", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_front_baby_leg", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("left_front_baby_leg", CubeListBuilder.create(), PartPose.ZERO);

        PartDefinition body = partdefinition.addOrReplaceChild(
                "body",
                CubeListBuilder.create().texOffs(0, 13).addBox(-5.0F, -3.0F, -7.0F, 8.0F, 6.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.0F, 14.0F, 0.0F)
        );

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, 6.5F));
        tail.addOrReplaceChild(
                "tail_r1",
                CubeListBuilder.create().texOffs(24, 33).addBox(-2.5F, -1.0F, -0.5F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7418F, 0.0F, 0.0F)
        );

        partdefinition.addOrReplaceChild(
                "left_hind_leg",
                CubeListBuilder.create().texOffs(12, 44).addBox(-2.5F, -1.5F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(3.25F, 17.5F, 5.25F)
        );
        partdefinition.addOrReplaceChild(
                "right_hind_leg",
                CubeListBuilder.create().texOffs(0, 44).addBox(-2.5F, -1.5F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.4F, 17.5F, 5.4F)
        );
        partdefinition.addOrReplaceChild(
                "left_front_leg",
                CubeListBuilder.create().texOffs(12, 33).addBox(-2.5F, -1.5F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(3.4F, 17.5F, -5.3F)
        );
        partdefinition.addOrReplaceChild(
                "right_front_leg",
                CubeListBuilder.create().texOffs(0, 33).addBox(-2.5F, -1.5F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.4F, 17.5F, -5.4F)
        );

        body.addOrReplaceChild("saddle", CubeListBuilder.create(), PartPose.ZERO);
        body.addOrReplaceChild("right_chest", CubeListBuilder.create(), PartPose.offset(-1.0F, 10.0F, 0.0F));
        body.addOrReplaceChild("left_chest", CubeListBuilder.create(), PartPose.offset(-1.0F, 10.0F, 0.0F));

        PartDefinition neck = partdefinition.addOrReplaceChild("head_parts", CubeListBuilder.create(), PartPose.offset(1.0F, 11.0F, -5.0F));
        neck.addOrReplaceChild(
                "neck_r1",
                CubeListBuilder.create().texOffs(30, 9).addBox(-3.0F, -6.0F, -3.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F)
        );

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, -3.0F));
        head.addOrReplaceChild(
                "head_r1",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -3.6F, -8.4F, 6.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -1.0F, 1.0F, 0.3927F, 0.0F, 0.0F)
        );
        head.addOrReplaceChild(
                "left_ear",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -6.5F, -0.3F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.0F, -3.5F, -1.0F, 0.48F, 0.0F, 0.48F)
        );
        head.addOrReplaceChild(
                "right_ear",
                CubeListBuilder.create().texOffs(22, 0).mirror().addBox(-2.0F, -6.5F, -0.3F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(-2.0F, -3.5F, -1.0F, 0.48F, 0.0F, -0.48F)
        );

        neck.addOrReplaceChild("head_saddle", CubeListBuilder.create(), PartPose.ZERO);
        neck.addOrReplaceChild("left_saddle_mouth", CubeListBuilder.create(), PartPose.ZERO);
        neck.addOrReplaceChild("right_saddle_mouth", CubeListBuilder.create(), PartPose.ZERO);
        neck.addOrReplaceChild("left_saddle_line", CubeListBuilder.create(), PartPose.ZERO);
        neck.addOrReplaceChild("right_saddle_line", CubeListBuilder.create(), PartPose.ZERO);
        neck.addOrReplaceChild("mouth_saddle_wrap", CubeListBuilder.create(), PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.body.y = 14.0F;
    }

    @Override
    public void prepareMobModel(@NotNull T entity, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
        float eatAnim = entity.getEatAnim(partialTick);
        float standAnim = entity.getStandAnim(partialTick);
        float animationProgress = 1.0F - Math.max(standAnim, eatAnim);

        this.headParts.y += animationProgress * 7.0F;
        this.headParts.z += animationProgress * 7.0F;
        this.body.y = 14.0F;
    }
}