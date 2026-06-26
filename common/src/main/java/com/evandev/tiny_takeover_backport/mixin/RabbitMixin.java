package com.evandev.tiny_takeover_backport.mixin;

import com.evandev.tiny_takeover_backport.config.ModConfig;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.Rabbit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class RabbitMixin {

    @Unique
    private static final EntityDimensions ADULT_261_DIMENSIONS = EntityDimensions.scalable(0.49F, 0.6F).withEyeHeight(0.59F);
    @Unique
    private static final EntityDimensions BABY_261_DIMENSIONS = EntityDimensions.scalable(0.24F, 0.4F).withEyeHeight(0.39F);

    @Inject(method = "getDefaultDimensions", at = @At("HEAD"), cancellable = true)
    private void injectRabbit261Dimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        if ((Object) this instanceof Rabbit rabbit && ModConfig.get().rabbitBoundingBox) {
            cir.setReturnValue(rabbit.isBaby() ? BABY_261_DIMENSIONS : ADULT_261_DIMENSIONS);
        }
    }
}
