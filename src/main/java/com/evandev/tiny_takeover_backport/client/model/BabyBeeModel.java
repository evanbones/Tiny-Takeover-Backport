package com.evandev.tiny_takeover_backport.client.model;

import net.minecraft.client.model.BeeModel;
import net.minecraft.client.model.ModelUtils;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Bee;
import org.jetbrains.annotations.NotNull;

public class BabyBeeModel extends BeeModel {
    private final ModelPart bone;
    private final ModelPart stinger;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart frontLeg;
    private final ModelPart midLeg;
    private final ModelPart backLeg;
    private float rollAmount;

    public BabyBeeModel(ModelPart root) {
        super(root);
        this.bone = root.getChild("bone");
        this.stinger = this.bone.getChild("body").getChild("stinger");
        this.rightWing = this.bone.getChild("right_wing");
        this.leftWing = this.bone.getChild("left_wing");
        this.frontLeg = this.bone.getChild("front_legs");
        this.midLeg = this.bone.getChild("middle_legs");
        this.backLeg = this.bone.getChild("back_legs");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition bone = root.addOrReplaceChild(
                "bone",
                CubeListBuilder.create()
                        .texOffs(6, 12)
                        .addBox(1.0F, -1.6667F, -2.1633F, 1.0F, 2.0F, 2.0F)
                        .texOffs(0, 12)
                        .addBox(-2.0F, -1.6667F, -2.1933F, 1.0F, 2.0F, 2.0F),
                PartPose.offset(0.0F, 19.6667F, -1.8567F)
        );
        PartDefinition body = bone.addOrReplaceChild(
                "body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 4.0F, 5.0F), PartPose.offset(0.0F, 1.3333F, 2.3567F)
        );
        body.addOrReplaceChild(
                "stinger", CubeListBuilder.create().texOffs(13, 2).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 1.0F), PartPose.offset(0.0F, 0.5F, 2.5F)
        );

        body.addOrReplaceChild("left_antenna", CubeListBuilder.create(), PartPose.ZERO);
        body.addOrReplaceChild("right_antenna", CubeListBuilder.create(), PartPose.ZERO);

        bone.addOrReplaceChild(
                "right_wing",
                CubeListBuilder.create().texOffs(3, 9).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 0.0F, 3.0F),
                PartPose.offsetAndRotation(-1.0F, -0.6667F, 0.8567F, 0.2182F, 0.3491F, 0.0F)
        );
        bone.addOrReplaceChild(
                "left_wing",
                CubeListBuilder.create().texOffs(-3, 9).mirror().addBox(0.0F, 0.0F, 0.0F, 3.0F, 0.0F, 3.0F).mirror(false),
                PartPose.offsetAndRotation(1.0F, -0.6667F, 0.8567F, 0.2182F, -0.3491F, 0.0F)
        );
        bone.addOrReplaceChild(
                "front_legs", CubeListBuilder.create().texOffs(13, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 1.0F, 0.0F), PartPose.offset(0.0F, 3.3333F, 1.8567F)
        );
        bone.addOrReplaceChild(
                "middle_legs", CubeListBuilder.create().texOffs(13, 1).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 1.0F, 0.0F), PartPose.offset(0.0F, 3.3333F, 2.8567F)
        );
        bone.addOrReplaceChild(
                "back_legs", CubeListBuilder.create().texOffs(13, 2).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 1.0F, 0.0F), PartPose.offset(0.0F, 3.3333F, 3.8567F)
        );
        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void prepareMobModel(@NotNull Bee entity, float limbSwing, float limbSwingAmount, float partialTick) {
        this.rollAmount = entity.getRollAmount(partialTick);
    }

    @Override
    public void setupAnim(@NotNull Bee entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.bone.resetPose();
        this.rightWing.resetPose();
        this.leftWing.resetPose();
        this.frontLeg.resetPose();
        this.midLeg.resetPose();
        this.backLeg.resetPose();

        this.stinger.visible = !entity.hasStung();
        boolean onGround = entity.onGround() && entity.getDeltaMovement().lengthSqr() < 1.0E-7;
        if (!onGround) {
            float speed = ageInTicks * 120.32113F * Mth.DEG_TO_RAD;
            this.rightWing.yRot = 0.0F;
            this.rightWing.zRot = Mth.cos(speed) * (float) Math.PI * 0.15F;
            this.leftWing.xRot = this.rightWing.xRot;
            this.leftWing.yRot = this.rightWing.yRot;
            this.leftWing.zRot = -this.rightWing.zRot;
            this.frontLeg.xRot = (float) (Math.PI / 4);
            this.midLeg.xRot = (float) (Math.PI / 4);
            this.backLeg.xRot = (float) (Math.PI / 4);
        }

        if (!entity.isAngry() && !onGround) {
            float speed = Mth.cos(ageInTicks * 0.18F);
            this.bone.xRot = 0.1F + speed * (float) Math.PI * 0.025F;
            this.bone.y = this.bone.y - Mth.cos(ageInTicks * 0.18F) * 0.9F;
            this.frontLeg.xRot = -speed * (float) Math.PI * 0.1F + (float) (Math.PI / 8);
            this.backLeg.xRot = -speed * (float) Math.PI * 0.05F + (float) (Math.PI / 4);
        }

        if (this.rollAmount > 0.0F) {
            this.bone.xRot = ModelUtils.rotlerpRad(this.bone.xRot, 3.0915928F, this.rollAmount);
        }
    }
}
