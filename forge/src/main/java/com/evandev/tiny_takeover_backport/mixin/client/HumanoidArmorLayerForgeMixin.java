package com.evandev.tiny_takeover_backport.mixin.client;

import com.evandev.tiny_takeover_backport.client.ModRenderHelper;
import com.evandev.tiny_takeover_backport.client.model.*;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerForgeMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {

    @ModifyVariable(
            method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;)V",
            at = @At("HEAD"),
            argsOnly = true
    )
    private A swapBabyArmorModel(
            A model,
            PoseStack poseStack,
            MultiBufferSource buffer,
            T livingEntity,
            EquipmentSlot slot,
            int packedLight
    ) {
        EntityModel<?> parentModel = ((HumanoidArmorLayer<T, M, A>) (Object) this).getParentModel();
        return ModRenderHelper.getBabyArmorModel(model, parentModel, slot);
    }

    @Inject(
            method = "getArmorResource",
            at = @At("RETURN"),
            cancellable = true,
            remap = false
    )
    private void redirectBabyArmorTexture(
            Entity entity,
            ItemStack stack,
            EquipmentSlot slot,
            String type,
            CallbackInfoReturnable<ResourceLocation> cir
    ) {
        if (entity instanceof LivingEntity && ((LivingEntity) entity).isBaby()) {
            EntityModel<?> parentModel = ((HumanoidArmorLayer<T, M, A>) (Object) this).getParentModel();
            if (parentModel instanceof BabyZombieModel ||
                    parentModel instanceof BabyPiglinModel ||
                    parentModel instanceof BabyZombieVillagerModel ||
                    parentModel instanceof BabyZombifiedPiglinModel ||
                    parentModel instanceof BabyDrownedModel) {
                if (stack.getItem() instanceof ArmorItem armorItem) {
                    String texture = armorItem.getMaterial().getName();
                    String domain = "minecraft";
                    int idx = texture.indexOf(':');
                    if (idx != -1) {
                        domain = texture.substring(0, idx);
                        texture = texture.substring(idx + 1);
                    }
                    String suffix = type == null ? "" : "_" + type;
                    cir.setReturnValue(new ResourceLocation(domain, "textures/models/armor/baby/" + texture + suffix + ".png"));
                }
            }
        }
    }

    @Inject(
            method = "setPartVisibility(Lnet/minecraft/client/model/HumanoidModel;Lnet/minecraft/world/entity/EquipmentSlot;)V",
            at = @At("TAIL")
    )
    private void adjustBabyArmorPartVisibility(A model, EquipmentSlot slot, CallbackInfo ci) {
        ModRenderHelper.adjustBabyArmorVisibility(model, slot);
    }
}
