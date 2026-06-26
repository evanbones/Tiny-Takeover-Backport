package com.evandev.tiny_takeover_backport.mixin;

import com.evandev.tiny_takeover_backport.entity.AgeLockable;
import com.evandev.tiny_takeover_backport.registry.ModRegistry;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin {

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void handleDandelionInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if ((Object) this instanceof AgeableMob mob && mob instanceof AgeLockable lockable) {
            if (mob instanceof Villager) return;

            ItemStack itemStack = player.getItemInHand(hand);
            if (itemStack.is(ModRegistry.GOLDEN_DANDELION_ITEM) && mob.isBaby() && lockable.tiny_takeover_backport$getAgeLockParticleTimer() == 0) {
                lockable.tiny_takeover_backport$setAgeLocked(!lockable.tiny_takeover_backport$isAgeLocked());
                if (lockable.tiny_takeover_backport$isAgeLocked()) {
                    mob.setPersistenceRequired();
                }
                itemStack.consume(1, player);
                lockable.tiny_takeover_backport$setAgeLockParticleTimer(40);

                mob.level().playSound(
                        null,
                        mob.blockPosition(),
                        lockable.tiny_takeover_backport$isAgeLocked() ? ModRegistry.GOLDEN_DANDELION_USE : ModRegistry.GOLDEN_DANDELION_UNUSE,
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                );

                cir.setReturnValue(InteractionResult.sidedSuccess(mob.level().isClientSide()));
            }
        }
    }
}