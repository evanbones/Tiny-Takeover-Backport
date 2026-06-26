package com.evandev.tiny_takeover_backport.mixin.client;

import com.evandev.tiny_takeover_backport.client.ModModelLayers;
import com.evandev.tiny_takeover_backport.client.model.BabyLlamaModel;
import com.evandev.tiny_takeover_backport.config.ModConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.LlamaDecorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.animal.horse.Llama;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LlamaDecorLayer.class)
public abstract class LlamaDecorLayerMixin extends RenderLayer<Llama, LlamaModel<Llama>> {

    @Unique
    private LlamaModel<Llama> tiny_takeover_backport$babyModel;

    public LlamaDecorLayerMixin(RenderLayerParent<Llama, LlamaModel<Llama>> renderer) {
        super(renderer);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(RenderLayerParent<Llama, LlamaModel<Llama>> renderer, EntityModelSet modelSet, CallbackInfo ci) {
        this.tiny_takeover_backport$babyModel = new BabyLlamaModel(modelSet.bakeLayer(ModModelLayers.LLAMA_BABY_DECOR));
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/horse/Llama;FFFFFF)V", at = @At("HEAD"))
    private void preRenderSetup(PoseStack poseStack, net.minecraft.client.renderer.MultiBufferSource buffer, int packedLight, Llama entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (entity.isBaby() && ModConfig.get().enableLlama) {
            this.tiny_takeover_backport$babyModel.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
            this.tiny_takeover_backport$babyModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }
    }

    @WrapOperation(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/horse/Llama;FFFFFF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/LlamaModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V"))
    private void wrapRenderCall(LlamaModel<Llama> instance, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int overlay, Operation<Void> original, PoseStack methodPoseStack, net.minecraft.client.renderer.MultiBufferSource buffer, int methodPackedLight, Llama entity) {
        if (entity.isBaby() && ModConfig.get().enableLlama) {
            instance = this.tiny_takeover_backport$babyModel;
        }
        original.call(instance, poseStack, vertexConsumer, packedLight, overlay);
    }
}