package com.evandev.tiny_takeover_backport.mixin;

import com.evandev.tiny_takeover_backport.config.ModConfig;
import com.evandev.tiny_takeover_backport.entity.AgeLockable;
import com.evandev.tiny_takeover_backport.entity.ModEntityData;
import com.evandev.tiny_takeover_backport.entity.ModifiableBaby;
import com.evandev.tiny_takeover_backport.registry.ModRegistry;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin {

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void tiny_takeover_backport$defineBabyData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        if ((Object) this instanceof Squid) {
            builder.define(ModEntityData.SQUID_BABY_ID, false);
        } else if ((Object) this instanceof Dolphin) {
            builder.define(ModEntityData.DOLPHIN_BABY_ID, false);
        }
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void tiny_takeover_backport$handleInteractions(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        Mob mob = (Mob) (Object) this;
        ItemStack itemStack = player.getItemInHand(hand);

        if (mob instanceof AgeableMob ageable && ageable instanceof AgeLockable lockable) {
            if (mob instanceof Villager) return;

            if (itemStack.is(ModRegistry.GOLDEN_DANDELION_ITEM) && ageable.isBaby() && lockable.tiny_takeover_backport$getAgeLockParticleTimer() == 0) {
                lockable.tiny_takeover_backport$setAgeLocked(!lockable.tiny_takeover_backport$isAgeLocked());
                if (lockable.tiny_takeover_backport$isAgeLocked()) {
                    ageable.setPersistenceRequired();
                }
                itemStack.consume(1, player);
                lockable.tiny_takeover_backport$setAgeLockParticleTimer(40);

                ageable.level().playSound(
                        null,
                        ageable.blockPosition(),
                        lockable.tiny_takeover_backport$isAgeLocked() ? ModRegistry.GOLDEN_DANDELION_USE : ModRegistry.GOLDEN_DANDELION_UNUSE,
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                );

                cir.setReturnValue(InteractionResult.sidedSuccess(ageable.level().isClientSide()));
            }
        }
    }

    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    private void tiny_takeover_backport$onFinalizeSpawn(CallbackInfoReturnable<?> cir) {
        Mob mob = (Mob) (Object) this;
        if (mob instanceof Dolphin) {
            if (ModConfig.get().spawnBabyDolphin && mob.getRandom().nextFloat() < 0.10F) {
                ((ModifiableBaby) mob).tiny_takeover_backport$setBaby(true);
            }
        } else if (mob instanceof Squid) {
            if (ModConfig.get().spawnBabySquid && mob.getRandom().nextFloat() < 0.05F) {
                ((ModifiableBaby) mob).tiny_takeover_backport$setBaby(true);
            }
        }
    }
}