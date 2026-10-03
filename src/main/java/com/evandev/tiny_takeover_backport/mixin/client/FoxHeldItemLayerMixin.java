package com.evandev.tiny_takeover_backport.mixin.client;

import com.evandev.tiny_takeover_backport.config.ModConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.FoxHeldItemLayer;
import net.minecraft.world.entity.animal.Fox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FoxHeldItemLayer.class)
public abstract class FoxHeldItemLayerMixin {

    @Unique
    private static boolean tiny_takeover_backport$usesBabyModel(Fox fox) {
        return fox.isBaby() && ModConfig.get().isModelEnabled(fox);
    }

    @WrapOperation(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/Fox;FFFFFF)V",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", ordinal = 0))
    private void skipBabyScale(PoseStack poseStack, float x, float y, float z, Operation<Void> original, PoseStack methodPoseStack, MultiBufferSource buffer, int packedLight, Fox fox) {
        if (!tiny_takeover_backport$usesBabyModel(fox)) {
            original.call(poseStack, x, y, z);
        }
    }

    @WrapOperation(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/Fox;FFFFFF)V",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", ordinal = 0))
    private void skipBabyOffset(PoseStack poseStack, float x, float y, float z, Operation<Void> original, PoseStack methodPoseStack, MultiBufferSource buffer, int packedLight, Fox fox) {
        if (!tiny_takeover_backport$usesBabyModel(fox)) {
            original.call(poseStack, x, y, z);
        }
    }

    @WrapOperation(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/Fox;FFFFFF)V",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", ordinal = 1))
    private void scaleAfterHeadOffset(PoseStack poseStack, float x, float y, float z, Operation<Void> original, PoseStack methodPoseStack, MultiBufferSource buffer, int packedLight, Fox fox) {
        original.call(poseStack, x, y, z);
        if (tiny_takeover_backport$usesBabyModel(fox)) {
            poseStack.scale(0.75F, 0.75F, 0.75F);
        }
    }
}
