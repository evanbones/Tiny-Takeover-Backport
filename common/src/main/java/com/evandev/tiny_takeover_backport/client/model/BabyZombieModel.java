package com.evandev.tiny_takeover_backport.client.model;

import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.monster.Zombie;
import org.jetbrains.annotations.NotNull;

public class BabyZombieModel<T extends Zombie> extends ZombieModel<T> {

    public BabyZombieModel(ModelPart root) {
        super(root);
    }

    @Override
    public void copyPropertiesTo(net.minecraft.client.model.HumanoidModel<T> model) {
        super.copyPropertiesTo(model);
        model.body.y += 0.5F;
        model.head.y -= 0.25F;
        model.hat.y -= 0.25F;
        model.rightArm.x -= 0.5F;
        model.leftArm.x += 0.5F;
        model.rightLeg.x -= 0.5F;
        model.rightLeg.z += 0.5F;
        model.leftLeg.x += 0.5F;
        model.leftLeg.z += 0.5F;
    }

    public static LayerDefinition createArmorLayer(CubeDeformation g) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild(
                "body", CubeListBuilder.create()
                        .texOffs(0, 17)
                        .addBox(-3.0F, -3.0F, -1.5F, 6.0F, 5.0F, 3.0F, g)
                        .texOffs(0, 36)
                        .addBox(-3.0F, -1.2F, -1.49F, 5.9F, 2.0F, 2.9F, g.extend(-0.1F)),
                PartPose.offset(0.0F, 18.0F, 0.0F)
        );
        root.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.5F, -7.0F, -4.5F, 9.0F, 8.0F, 8.0F, g),
                PartPose.offset(0.0F, 15.0F, 0.0F)
        );
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild(
                "right_arm", CubeListBuilder.create().texOffs(30, 25).addBox(-1.0F, 0.0F, -1.53F, 2.0F, 5.0F, 3.0F, g), PartPose.offset(-3.5F, 15.5F, 0.0F)
        );
        root.addOrReplaceChild(
                "left_arm", CubeListBuilder.create().texOffs(30, 17).mirror().addBox(-1.0F, 0.0F, -1.53F, 2.0F, 5.0F, 3.0F, g), PartPose.offset(3.5F, 15.5F, 0.0F)
        );
        root.addOrReplaceChild(
                "right_leg", CubeListBuilder.create()
                        .texOffs(18, 17)
                        .addBox(-1.0F, -0.2F, -2.0F, 3.0F, 4.0F, 3.0F, g.extend(-0.1F))
                        .texOffs(0, 25)
                        .addBox(-1.0F, 2.9F, -2.0F, 3.0F, 1.0F, 3.0F, g),
                PartPose.offset(-1.5F, 20.0F, 0.5F)
        );
        root.addOrReplaceChild(
                "left_leg", CubeListBuilder.create()
                        .texOffs(18, 24)
                        .mirror()
                        .addBox(-2.0F, -0.2F, -2.0F, 3.0F, 4.0F, 3.0F, g.extend(-0.1F))
                        .texOffs(0, 29)
                        .addBox(-2.0F, 2.9F, -2.0F, 3.0F, 1.0F, 3.0F, g),
                PartPose.offset(1.5F, 20.0F, 0.5F)
        );
        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition createBodyLayer() {
        return createBodyLayer(CubeDeformation.NONE);
    }

    public static LayerDefinition createBodyLayer(CubeDeformation g) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild(
                "body", CubeListBuilder.create().texOffs(16, 16).addBox(-2.0F, -2.5F, -1.0F, 4.0F, 5.0F, 2.0F, g), PartPose.offset(0.0F, 17.5F, 0.0F)
        );
        PartDefinition head = root.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(3, 3)
                        .addBox(-3.0F, -6.25F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(35, 3)
                        .addBox(-3.0F, -6.15F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 15.25F, 0.0F)
        );

        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

        root.addOrReplaceChild(
                "right_arm", CubeListBuilder.create().texOffs(36, 16).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F, g), PartPose.offset(-3.0F, 15.5F, 0.0F)
        );
        root.addOrReplaceChild(
                "left_arm", CubeListBuilder.create().texOffs(28, 16).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F, g), PartPose.offset(3.0F, 15.5F, 0.0F)
        );
        root.addOrReplaceChild(
                "right_leg", CubeListBuilder.create().texOffs(8, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, g), PartPose.offset(-1.0F, 20.0F, 0.0F)
        );
        root.addOrReplaceChild(
                "left_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, g), PartPose.offset(1.0F, 20.0F, 0.0F)
        );
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.head.setPos(0.0F, 15.25F, 0.0F);
        this.hat.setPos(0.0F, 15.25F, 0.0F);
        this.body.setPos(0.0F, 17.5F, 0.0F);
        this.rightArm.setPos(-3.0F, 15.5F, 0.0F);
        this.leftArm.setPos(3.0F, 15.5F, 0.0F);
        this.rightLeg.setPos(-1.0F, 20.0F, 0.0F);
        this.leftLeg.setPos(1.0F, 20.0F, 0.0F);
    }
}