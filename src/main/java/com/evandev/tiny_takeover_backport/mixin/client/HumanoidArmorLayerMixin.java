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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? if >=1.21 {
import com.evandev.tiny_takeover_backport.client.model.ModBabyArmorModel;
//?} else {
/*import net.minecraft.world.item.ArmorItem;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
*///?}
//? if neoforge
import net.minecraft.client.model.Model;
//? if forge {
/*import com.evandev.tiny_takeover_backport.client.model.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
*///?}

@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {

    @ModifyVariable(
            //? if neoforge {
            method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;FFFFFF)V",
            //?} else
            //method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;)V",
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
        //? if fabric && <1.21 {
        /*A babyModel = ModRenderHelper.getBabyArmorModel(model, parentModel, slot);
        if (babyModel != model) {
            ModRenderHelper.IS_RENDERING_BABY_ARMOR.set(true);
        }
        return babyModel;
        *///?} else
        return ModRenderHelper.getBabyArmorModel(model, parentModel, slot);
    }

    @Inject(
            method = "setPartVisibility(Lnet/minecraft/client/model/HumanoidModel;Lnet/minecraft/world/entity/EquipmentSlot;)V",
            at = @At("TAIL")
    )
    private void adjustBabyArmorPartVisibility(A model, EquipmentSlot slot, CallbackInfo ci) {
        ModRenderHelper.adjustBabyArmorVisibility(model, slot);
    }

    //? if >=1.21 {
    @ModifyVariable(
            //? if neoforge {
            method = "renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/model/Model;ILnet/minecraft/resources/ResourceLocation;)V",
            //?} else
            //method = "renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/model/HumanoidModel;ILnet/minecraft/resources/ResourceLocation;)V",
            at = @At("HEAD"),
            argsOnly = true
    )
    private ResourceLocation redirectBabyArmorTexture(
            ResourceLocation textureLocation,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            //? if neoforge {
            Model model,
            //?} else
            //HumanoidModel<?> model,
            int color
    ) {
        if (model instanceof ModBabyArmorModel) {
            String path = textureLocation.getPath();
            if (path.startsWith("textures/models/armor/")) {
                String materialAndSuffix = path.substring("textures/models/armor/".length());
                String newPath = "textures/models/armor/baby/" + materialAndSuffix.replace("_layer_1", "").replace("_layer_2", "");
                return ResourceLocation.fromNamespaceAndPath(textureLocation.getNamespace(), newPath);
            }
        }
        return textureLocation;
    }
    //?} else if fabric {
    /*@Inject(
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
            cir.setReturnValue(babyArmorLocation(armorItem, suffix));
        }
    }
    *///?} else {
    /*@Inject(
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
                    cir.setReturnValue(babyArmorLocation(armorItem, type));
                }
            }
        }
    }
    *///?}
    //? if <1.21 {

    /*private static ResourceLocation babyArmorLocation(ArmorItem armorItem, String suffix) {
        String texture = armorItem.getMaterial().getName();
        String domain = "minecraft";
        int idx = texture.indexOf(':');
        if (idx != -1) {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }
        String typeSuffix = suffix == null ? "" : "_" + suffix;
        return new ResourceLocation(domain, "textures/models/armor/baby/" + texture + typeSuffix + ".png");
    }
    *///?}
}
