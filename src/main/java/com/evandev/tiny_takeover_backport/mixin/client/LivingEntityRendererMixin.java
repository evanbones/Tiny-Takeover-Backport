package com.evandev.tiny_takeover_backport.mixin.client;

import com.evandev.tiny_takeover_backport.client.ModBabyModelRegistry;
import com.evandev.tiny_takeover_backport.client.ModBabyTextureRegistry;
import com.evandev.tiny_takeover_backport.client.ModRenderHelper;
import com.evandev.tiny_takeover_backport.client.model.BabyTurtleModel;
import com.evandev.tiny_takeover_backport.config.ModConfig;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if <1.21 {
/*import net.minecraft.client.renderer.entity.StriderRenderer;
*///?}

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {

    @Shadow
    protected M model;

    @Unique
    private EntityModel<T> tiny_takeover_backport$babyModel;

    @Unique
    private EntityModel<T> tiny_takeover_backport$newAdultModel;

    @Unique
    private static float tiny_takeover_backport$getAdultOnlyScale(LivingEntityRenderer<?, ?> renderer, LivingEntity entity) {
        if (renderer instanceof PolarBearRenderer) return 1.2F;
        if (renderer instanceof CatRenderer) return 0.8F;
        if (renderer instanceof HuskRenderer) return 1.0625F;
        if (renderer instanceof VillagerRenderer) return 0.9375F;
        if (renderer instanceof HorseRenderer) return 1.1F;
        if (renderer instanceof ChestedHorseRenderer<?>) {
            if (entity.getType() == EntityType.DONKEY) return 0.87F;
            if (entity.getType() == EntityType.MULE) return 0.92F;
        }
        return 1.0F;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(EntityRendererProvider.Context context, M model, float shadowRadius, CallbackInfo ci) {
        this.tiny_takeover_backport$babyModel = ModBabyModelRegistry.createBabyModel((LivingEntityRenderer<T, M>) (Object) this, context, model);
        this.tiny_takeover_backport$newAdultModel = ModBabyModelRegistry.createAdultModel((LivingEntityRenderer<T, M>) (Object) this, context, model);
    }

    @SuppressWarnings("unchecked")
    @WrapMethod(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    private void wrapRenderModelSwap(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, Operation<Void> original) {
        M swappedModel = null;
        if (entity.isBaby() && this.tiny_takeover_backport$babyModel != null && ModConfig.get().isModelEnabled(entity)) {
            swappedModel = (M) this.tiny_takeover_backport$babyModel;
        } else if (!entity.isBaby() && this.tiny_takeover_backport$newAdultModel != null
                && ModConfig.get().isAdultRabbitReplaced(entity)) {
            swappedModel = (M) this.tiny_takeover_backport$newAdultModel;
        }

        if (swappedModel == null) {
            original.call(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
            return;
        }

        M originalModel = this.model;
        this.model = swappedModel;
        try {
            original.call(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        } finally {
            this.model = originalModel;
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
        if (entity.isBaby() && this.tiny_takeover_backport$babyModel != null && ModConfig.get().isModelEnabled(entity)) {
            original.call(model, false);
            return;
        }
        original.call(model, value);
    }

    @WrapOperation(method = "getRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;renderType(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"))
    private RenderType wrapModelRenderType(EntityModel<?> model, ResourceLocation texture, Operation<RenderType> original) {
        if (model instanceof BabyTurtleModel) {
            return RenderType.entityCutout(texture);
        }
        return original.call(model, texture);
    }

    @ModifyConstant(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            constant = @Constant(floatValue = 3.0F))
    private float removeBabyWalkSpeedup(float multiplier, T entity) {
        if (entity.isBaby() && this.tiny_takeover_backport$babyModel != null && ModConfig.get().isModelEnabled(entity)) {
            return 1.0F;
        }
        return multiplier;
    }

    @WrapOperation(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;scale(Lnet/minecraft/world/entity/LivingEntity;Lcom/mojang/blaze3d/vertex/PoseStack;F)V"))
    private void wrapScaleCall(LivingEntityRenderer<T, M> renderer, T entity, PoseStack poseStack, float partialTick, Operation<Void> original) {
        if (entity.isBaby() && this.tiny_takeover_backport$babyModel != null && ModConfig.get().isModelEnabled(entity)) {
            ModRenderHelper.SUPPRESS_AGE_SCALE.set(true);
            try {
                original.call(renderer, entity, poseStack, partialTick);
                //? if <1.21 {
                /*if (renderer instanceof VillagerRenderer || renderer instanceof StriderRenderer) {
                    poseStack.scale(2.0F, 2.0F, 2.0F);
                }
                *///?}
                float adultScale = tiny_takeover_backport$getAdultOnlyScale(renderer, entity);
                if (adultScale != 1.0F) {
                    poseStack.scale(1.0F / adultScale, 1.0F / adultScale, 1.0F / adultScale);
                }
            } finally {
                ModRenderHelper.SUPPRESS_AGE_SCALE.set(false);
            }
        } else {
            original.call(renderer, entity, poseStack, partialTick);
        }
    }
}