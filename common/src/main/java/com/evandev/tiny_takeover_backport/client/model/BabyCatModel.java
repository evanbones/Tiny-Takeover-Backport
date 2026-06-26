package com.evandev.tiny_takeover_backport.client.model;

import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class BabyCatModel extends CatModel {
    public BabyCatModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        return createBodyLayer(CubeDeformation.NONE);
    }

    public static LayerDefinition createCollarLayer() {
        return createBodyLayer(new CubeDeformation(0.01F));
    }

    public static LayerDefinition createBodyLayer(CubeDeformation g) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-2.5F, -3.0F, -2.875F, 5.0F, 4.0F, 4.0F, g)
                        .texOffs(18, 0)
                        .addBox(-2.0F, -4.0F, -0.875F, 1.0F, 1.0F, 2.0F, g)
                        .texOffs(24, 0)
                        .addBox(1.0F, -4.0F, -0.875F, 1.0F, 1.0F, 2.0F, g)
                        .texOffs(18, 3)
                        .addBox(-1.5F, -1.0F, -3.875F, 3.0F, 2.0F, 1.0F, g),
                PartPose.offset(0.0F, 20.0F, -3.125F)
        );
        partdefinition.addOrReplaceChild(
                "left_front_leg", CubeListBuilder.create().texOffs(18, 18).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, g), PartPose.offset(1.0F, 22.0F, -1.5F)
        );
        partdefinition.addOrReplaceChild(
                "right_front_leg", CubeListBuilder.create().texOffs(12, 18).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, g), PartPose.offset(-1.0F, 22.0F, -1.5F)
        );
        partdefinition.addOrReplaceChild(
                "left_hind_leg", CubeListBuilder.create().texOffs(18, 22).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, g), PartPose.offset(1.0F, 22.0F, 2.5F)
        );
        partdefinition.addOrReplaceChild(
                "body", CubeListBuilder.create().texOffs(0, 8).addBox(-2.0F, -1.5F, -3.5F, 4.0F, 3.0F, 7.0F, g), PartPose.offset(0.0F, 20.5F, 0.5F)
        );
        partdefinition.addOrReplaceChild(
                "right_hind_leg", CubeListBuilder.create().texOffs(12, 22).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, g), PartPose.offset(-1.0F, 22.0F, 2.5F)
        );
        partdefinition.addOrReplaceChild(
                "tail1",
                CubeListBuilder.create().texOffs(0, 18).addBox(-0.5F, -0.107F, 0.0849F, 1.0F, 1.0F, 5.0F, g),
                PartPose.offsetAndRotation(0.0F, 19.107F, 3.9151F, -0.567232F, 0.0F, 0.0F)
        );
        partdefinition.addOrReplaceChild("tail2", CubeListBuilder.create(), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 32, 32);
    }
}
