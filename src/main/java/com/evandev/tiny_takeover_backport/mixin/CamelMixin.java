package com.evandev.tiny_takeover_backport.mixin;

import com.evandev.tiny_takeover_backport.config.ModConfig;
import net.minecraft.world.entity.animal.camel.Camel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Before 1.21 there is no getAgeScale; baby camel sizing lives in LivingEntityMixin#getDimensions instead
@Mixin(Camel.class)
public abstract class CamelMixin {
    //? if >=1.21 {
    @Inject(method = "getAgeScale", at = @At("RETURN"), cancellable = true)
    private void tiny_takeover_backport$modifyCamelAgeScale(CallbackInfoReturnable<Float> cir) {
        Camel camel = (Camel) (Object) this;
        if (ModConfig.get().isModelEnabled("camel") && camel.isBaby()) {
            cir.setReturnValue(0.6F);
        }
    }
    //?}
}
