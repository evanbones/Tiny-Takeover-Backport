package com.evandev.tiny_takeover_backport.mixin;

import net.minecraft.world.entity.animal.goat.Goat;
import org.spongepowered.asm.mixin.Mixin;
//? if <1.21 {
/*import com.evandev.tiny_takeover_backport.config.ModConfig;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
*///?}

@Mixin(Goat.class)
public abstract class GoatMixin {
    //? if <1.21 {
    /*@Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    private void tiny_takeover_backport$modifyLongJumpingDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        Goat goat = (Goat) (Object) this;
        if (pose == Pose.LONG_JUMPING && goat.isBaby() && ModConfig.get().isModelEnabled(goat)) {
            cir.setReturnValue(cir.getReturnValue().scale(0.55F / 0.5F));
        }
    }
    *///?}
}
