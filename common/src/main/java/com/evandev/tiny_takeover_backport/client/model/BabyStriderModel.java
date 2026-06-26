package com.evandev.tiny_takeover_backport.client.model;

import net.minecraft.client.model.StriderModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class BabyStriderModel extends StriderModel {

    public BabyStriderModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition body = root.addOrReplaceChild(
                "body",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.75F, -4.0F, 7.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 16.75F, 0.0F)
        );
        root.addOrReplaceChild(
                "right_leg",
                CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.5F, 20.0F, 0.0F)
        );
        root.addOrReplaceChild(
                "left_leg",
                CubeListBuilder.create().texOffs(8, 24).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.5F, 20.0F, 0.0F)
        );
        body.addOrReplaceChild(
                "bristle0",
                CubeListBuilder.create().texOffs(0, 21).addBox(-3.5F, -2.5F, 0.0F, 7.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -4.25F, 2.0F)
        );
        body.addOrReplaceChild(
                "bristle1",
                CubeListBuilder.create().texOffs(0, 18).addBox(-3.5F, -2.5F, 0.0F, 7.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -4.25F, 0.0F)
        );
        body.addOrReplaceChild(
                "bristle2",
                CubeListBuilder.create().texOffs(0, 15).addBox(-3.5F, -2.5F, 0.0F, 7.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -4.25F, -2.0F)
        );

        body.addOrReplaceChild("right_bottom_bristle", CubeListBuilder.create(), PartPose.ZERO);
        body.addOrReplaceChild("right_middle_bristle", CubeListBuilder.create(), PartPose.ZERO);
        body.addOrReplaceChild("right_top_bristle", CubeListBuilder.create(), PartPose.ZERO);
        body.addOrReplaceChild("left_top_bristle", CubeListBuilder.create(), PartPose.ZERO);
        body.addOrReplaceChild("left_middle_bristle", CubeListBuilder.create(), PartPose.ZERO);
        body.addOrReplaceChild("left_bottom_bristle", CubeListBuilder.create(), PartPose.ZERO);

        return LayerDefinition.create(mesh, 32, 32);
    }
}