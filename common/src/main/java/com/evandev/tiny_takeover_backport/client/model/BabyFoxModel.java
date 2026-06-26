package com.evandev.tiny_takeover_backport.client.model;

import net.minecraft.client.model.FoxModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.animal.Fox;
import org.jetbrains.annotations.NotNull;

public class BabyFoxModel extends FoxModel {

    private final ModelPart body;
    private final ModelPart tail;

    public BabyFoxModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.tail = this.body.getChild("tail");
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

    @SuppressWarnings("unchecked")
    @Override
    public void prepareMobModel(@NotNull Fox entity, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
        this.body.xRot = 0.0F;
        this.tail.xRot = -0.05235988F;
    }
}