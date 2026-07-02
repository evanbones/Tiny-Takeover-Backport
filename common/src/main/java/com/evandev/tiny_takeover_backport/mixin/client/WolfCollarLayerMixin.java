package com.evandev.tiny_takeover_backport.mixin.client;

import com.evandev.tiny_takeover_backport.client.ModModelLayers;
import com.evandev.tiny_takeover_backport.client.model.BabyWolfModel;
import com.evandev.tiny_takeover_backport.config.ModConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.WolfCollarLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WolfCollarLayer.class)
public abstract class WolfCollarLayerMixin extends RenderLayer<Wolf, WolfModel<Wolf>> {

    @Unique
    private WolfModel<Wolf> tiny_takeover_backport$babyModel;

    public WolfCollarLayerMixin(RenderLayerParent<Wolf, WolfModel<Wolf>> renderer) {
        super(renderer);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(RenderLayerParent<Wolf, WolfModel<Wolf>> renderer, CallbackInfo ci) {
        this.tiny_takeover_backport$babyModel = new BabyWolfModel(
                Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.WOLF_BABY)
        );
    }

    @WrapOperation(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/Wolf;FFFFFF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/WolfCollarLayer;renderColoredCutoutModel(Lnet/minecraft/client/model/EntityModel;Lnet/minecraft/resources/ResourceLocation;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFF)V")
    )
    private void wrapRenderCall(EntityModel<?> model, ResourceLocation textureLocation, PoseStack poseStack, MultiBufferSource buffer, int packedLight, LivingEntity entity, float red, float green, float blue, Operation<Void> original) {
        if (entity.isBaby() && ModConfig.get().enableWolf) {
            original.call(this.tiny_takeover_backport$babyModel, textureLocation, poseStack, buffer, packedLight, entity, red, green, blue);
        } else {
            original.call(model, textureLocation, poseStack, buffer, packedLight, entity, red, green, blue);
        }
    }
}