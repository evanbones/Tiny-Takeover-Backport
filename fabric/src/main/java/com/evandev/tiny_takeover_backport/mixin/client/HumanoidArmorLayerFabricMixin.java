package com.evandev.tiny_takeover_backport.mixin.client;

import com.evandev.tiny_takeover_backport.client.ModRenderHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerFabricMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {

    @ModifyVariable(
            method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;)V",
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
        A babyModel = ModRenderHelper.getBabyArmorModel(model, parentModel, slot);
        if (babyModel != model) {
            ModRenderHelper.IS_RENDERING_BABY_ARMOR.set(true);
        }
        return babyModel;
    }

    @Inject(
            method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;)V",
            at = @At("TAIL")
    )
    private void clearRenderingBabyArmor(
            PoseStack poseStack,
            MultiBufferSource buffer,
            T livingEntity,
            EquipmentSlot slot,
            int packedLight,
            A model,
            CallbackInfo ci
    ) {
        ModRenderHelper.IS_RENDERING_BABY_ARMOR.set(false);
    }

    @Inject(
            method = "getArmorLocation",
            at = @At("RETURN"),
            cancellable = true
    )
    private void redirectBabyArmorTexture(
            ArmorItem armorItem,
            boolean layer2,
            String suffix,
            CallbackInfoReturnable<ResourceLocation> cir
    ) {
        if (ModRenderHelper.IS_RENDERING_BABY_ARMOR.get()) {
            String texture = armorItem.getMaterial().getName();
            String domain = "minecraft";
            int idx = texture.indexOf(':');
            if (idx != -1) {
                domain = texture.substring(0, idx);
                texture = texture.substring(idx + 1);
            }
            String typeSuffix = suffix == null ? "" : "_" + suffix;
            cir.setReturnValue(new ResourceLocation(domain, "textures/models/armor/baby/" + texture + typeSuffix + ".png"));
        }
    }
}
