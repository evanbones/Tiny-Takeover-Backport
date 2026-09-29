package com.evandev.tiny_takeover_backport.mixin;

import com.evandev.tiny_takeover_backport.entity.AgeLockable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Animal.class)
public abstract class AnimalMixin {

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void blockBreedingFoodForLockedBaby(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        Animal animal = (Animal) (Object) this;
        if (animal.isBaby() && ((AgeLockable) animal).tiny_takeover_backport$isAgeLocked()) {
            ItemStack itemStack = player.getItemInHand(hand);
            if (animal.isFood(itemStack)) {
                cir.setReturnValue(InteractionResult.PASS);
            }
        }
    }
}
