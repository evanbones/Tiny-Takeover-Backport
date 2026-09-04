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
    private void wrapRenderCall(EntityModel<?> model, ResourceLocation textureLocation, PoseStack poseStack, MultiBufferSource buffer, int packedLight, LivingEntity entity, float red, float green, float blue, Operation<Void> original, PoseStack methodPoseStack, net.minecraft.client.renderer.MultiBufferSource methodBuffer, int methodPackedLight, Wolf wolf, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isBaby() && ModConfig.get().enableWolf) {
            WolfModel<Wolf> babyModel = this.tiny_takeover_backport$babyModel;
            ((WolfModel<Wolf>) model).copyPropertiesTo(babyModel);
            babyModel.prepareMobModel(wolf, limbSwing, limbSwingAmount, partialTicks);
            babyModel.setupAnim(wolf, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            model = babyModel;
            textureLocation = new ResourceLocation("textures/entity/wolf/wolf_collar_baby.png");
        }
        original.call(model, textureLocation, poseStack, buffer, packedLight, entity, red, green, blue);
    }
}