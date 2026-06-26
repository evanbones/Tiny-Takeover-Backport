package com.evandev.tiny_takeover_backport.mixin.client;

import com.evandev.tiny_takeover_backport.client.ModBabyModelRegistry;
import com.evandev.tiny_takeover_backport.client.ModBabyTextureRegistry;
import com.evandev.tiny_takeover_backport.client.ModRenderHelper;
import com.evandev.tiny_takeover_backport.config.ModConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {

    @Shadow
    protected M model;

    @Unique
    private EntityModel<T> tiny_takeover_backport$babyModel;

    @Unique
    private EntityModel<T> tiny_takeover_backport$newAdultModel;

    @Unique
    private M tiny_takeover_backport$originalModel;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(EntityRendererProvider.Context context, M model, float shadowRadius, CallbackInfo ci) {
        this.tiny_takeover_backport$babyModel = ModBabyModelRegistry.createBabyModel((LivingEntityRenderer<T, M>) (Object) this, context, model);
        this.tiny_takeover_backport$newAdultModel = ModBabyModelRegistry.createAdultModel((LivingEntityRenderer<T, M>) (Object) this, context, model);
    }

    @SuppressWarnings("unchecked")
    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"))
    private void swapModel(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        String entityName = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getPath();

        if (entity.isBaby() && this.tiny_takeover_backport$babyModel != null) {
            if (ModConfig.get().isModelEnabled(entityName)) {
                this.tiny_takeover_backport$originalModel = this.model;
                this.model = (M) this.tiny_takeover_backport$babyModel;
            }
        } else if (!entity.isBaby() && this.tiny_takeover_backport$newAdultModel != null) {
            if (entityName.equals("rabbit") && ModConfig.get().replaceAdultRabbit) {
                this.tiny_takeover_backport$originalModel = this.model;
                this.model = (M) this.tiny_takeover_backport$newAdultModel;
            }
        }
    }

    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("RETURN"))
    private void restoreModel(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        if (this.tiny_takeover_backport$originalModel != null) {
            this.model = this.tiny_takeover_backport$originalModel;
            this.tiny_takeover_backport$originalModel = null;
        }
    }

    @WrapOperation(method = "getRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getTextureLocation(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/resources/ResourceLocation;"))
    private ResourceLocation wrapTextureCall(LivingEntityRenderer<T, M> instance, Entity entity, Operation<ResourceLocation> original) {
        ResourceLocation originalTex = original.call(instance, entity);
        return ModBabyTextureRegistry.getBabyTexture((LivingEntity) entity, originalTex);
    }

    @WrapOperation(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/model/EntityModel;young:Z", opcode = 181))
    private void wrapSetYoung(EntityModel<?> model, boolean value, Operation<Void> original, T entity) {
        String entityName = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getPath();
        if (entity.isBaby() && this.tiny_takeover_backport$babyModel != null && ModConfig.get().isModelEnabled(entityName)) {
            original.call(model, false);
            return;
        }
        original.call(model, value);
    }

    @WrapOperation(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;scale(Lnet/minecraft/world/entity/LivingEntity;Lcom/mojang/blaze3d/vertex/PoseStack;F)V"))
    private void wrapScaleCall(LivingEntityRenderer<T, M> renderer, T entity, PoseStack poseStack, float partialTick, Operation<Void> original) {
        String entityName = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getPath();
        if (entity.isBaby() && this.tiny_takeover_backport$babyModel != null && ModConfig.get().isModelEnabled(entityName)) {
            ModRenderHelper.SUPPRESS_AGE_SCALE.set(true);
            try {
                original.call(renderer, entity, poseStack, partialTick);
            } finally {
                ModRenderHelper.SUPPRESS_AGE_SCALE.set(false);
            }
        } else {
            original.call(renderer, entity, poseStack, partialTick);
        }
    }
}