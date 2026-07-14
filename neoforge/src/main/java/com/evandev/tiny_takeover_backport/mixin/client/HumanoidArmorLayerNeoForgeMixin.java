package com.evandev.tiny_takeover_backport.mixin.client;

import com.evandev.tiny_takeover_backport.client.ModRenderHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerNeoForgeMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {

    @ModifyVariable(
            method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;FFFFFF)V",
            at = @At("HEAD"),
            argsOnly = true
    )
    private A swapBabyArmorModel(
            A model,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            T livingEntity,
            EquipmentSlot slot,
            int packedLight
    ) {
        EntityModel<?> parentModel = ((HumanoidArmorLayer<T, M, A>) (Object) this).getParentModel();
        return ModRenderHelper.getBabyArmorModel(model, parentModel, slot);
    }
}
