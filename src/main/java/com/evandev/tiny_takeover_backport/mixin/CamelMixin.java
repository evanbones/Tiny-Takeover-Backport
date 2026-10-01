package com.evandev.tiny_takeover_backport.mixin;

import com.evandev.tiny_takeover_backport.config.ModConfig;
import net.minecraft.world.entity.animal.camel.Camel;
//? if <1.21 {
/*import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
*///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camel.class)
public abstract class CamelMixin {
    //? if >=1.21 {
    @Inject(method = "getAgeScale", at = @At("RETURN"), cancellable = true)
    private void tiny_takeover_backport$modifyCamelAgeScale(CallbackInfoReturnable<Float> cir) {
        Camel camel = (Camel) (Object) this;
        if (ModConfig.get().isModelEnabled(camel) && camel.isBaby()) {
            cir.setReturnValue(0.6F);
        }
    }
    //?} else {
    /*@Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    private void tiny_takeover_backport$modifySittingDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        Camel camel = (Camel) (Object) this;
        if (pose == Pose.SITTING && camel.isBaby() && ModConfig.get().isModelEnabled(camel)) {
            cir.setReturnValue(cir.getReturnValue().scale(0.6F / 0.5F));
        }
    }
    *///?}
}
