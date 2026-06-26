package com.evandev.tiny_takeover_backport.client.model;

import net.minecraft.client.model.AxolotlModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class BabyAxolotlModel extends AxolotlModel {

    public BabyAxolotlModel(ModelPart root) {
        super(root);
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
                CubeListBuilder.create().texOffs(20, 16).addBox(-3.0F, 0.0F, -0.5F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-2.0F, 0.25F, -1.25F)
        );
        PartDefinition right_leg = body.addOrReplaceChild(
                "right_hind_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.0F, 0.25F, 1.75F, 0.0F, 1.5708F, 1.5708F)
        );
        right_leg.addOrReplaceChild(
                "right_leg_r1",
                CubeListBuilder.create().texOffs(20, 14).addBox(0.0F, 0.0F, -0.5F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 1.5708F)
        );
        body.addOrReplaceChild(
                "left_front_leg",
                CubeListBuilder.create().texOffs(20, 13).addBox(0.0F, 0.0F, -0.5F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(2.0F, 0.25F, -1.25F)
        );
        body.addOrReplaceChild(
                "left_hind_leg",
                CubeListBuilder.create().texOffs(20, 14).addBox(0.0F, 0.0F, -0.5F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
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
}