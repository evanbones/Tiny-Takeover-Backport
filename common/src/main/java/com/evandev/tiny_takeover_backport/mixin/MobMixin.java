package com.evandev.tiny_takeover_backport.mixin;

import com.evandev.tiny_takeover_backport.config.ModConfig;
import com.evandev.tiny_takeover_backport.entity.AgeLockable;
import com.evandev.tiny_takeover_backport.entity.ModEntityData;
import com.evandev.tiny_takeover_backport.entity.ModifiableBaby;
import com.evandev.tiny_takeover_backport.registry.ModRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin extends Entity implements AgeLockable {

    @Unique
    private static final EntityDataAccessor<Boolean> tiny_takeover_backport$DATA_AGE_LOCKED =
            SynchedEntityData.defineId(Mob.class, EntityDataSerializers.BOOLEAN);
    @Unique
    private int tiny_takeover_backport$ageLockParticleTimer = 0;
    @Unique
    private int tiny_takeover_backport$age = 0;

    public MobMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean tiny_takeover_backport$isAgeLocked() {
        return this.entityData.get(tiny_takeover_backport$DATA_AGE_LOCKED);
    }

    @Override
    public void tiny_takeover_backport$setAgeLocked(boolean ageLocked) {
        this.entityData.set(tiny_takeover_backport$DATA_AGE_LOCKED, ageLocked);
    }

    @Override
    public int tiny_takeover_backport$getAgeLockParticleTimer() {
        return this.tiny_takeover_backport$ageLockParticleTimer;
    }

    @Override
    public void tiny_takeover_backport$setAgeLockParticleTimer(int timer) {
        this.tiny_takeover_backport$ageLockParticleTimer = timer;
    }

    @Override
    public int tiny_takeover_backport$getCustomAge() {
        return this.tiny_takeover_backport$age;
    }

    @Override
    public void tiny_takeover_backport$setCustomAge(int age) {
        this.tiny_takeover_backport$age = age;
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void tiny_takeover_backport$defineBabyData(CallbackInfo ci) {
        this.entityData.define(tiny_takeover_backport$DATA_AGE_LOCKED, false);
        if ((Object) this instanceof Squid) {
            this.entityData.define(ModEntityData.SQUID_BABY_ID, false);
        } else if ((Object) this instanceof Dolphin) {
            this.entityData.define(ModEntityData.DOLPHIN_BABY_ID, false);
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void writeAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        tag.putBoolean("AgeLocked", this.tiny_takeover_backport$isAgeLocked());
        tag.putInt("Age", this.tiny_takeover_backport$age);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        this.tiny_takeover_backport$setAgeLocked(tag.getBoolean("AgeLocked"));
        if (tag.contains("Age")) {
            this.tiny_takeover_backport$age = tag.getInt("Age");
        }
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void onAiStep(CallbackInfo ci) {
        Mob mob = (Mob) (Object) this;
        if (this.tiny_takeover_backport$ageLockParticleTimer > 0) {
            if (mob.level().isClientSide() && this.tiny_takeover_backport$ageLockParticleTimer % 2 == 0) {
                boolean locked = this.tiny_takeover_backport$isAgeLocked();
                float yOffset = locked ? 0.2F : 0.0F;
                double x = mob.getRandomX(1.0);
                double z = mob.getRandomZ(1.0);
                mob.level().addParticle(
                        locked ? ModRegistry.PAUSE_MOB_GROWTH : ModRegistry.RESET_MOB_GROWTH,
                        x, mob.getY() + mob.getBbHeight() + yOffset, z,
                        0.0, 0.0, 0.0
                );
            }
            this.tiny_takeover_backport$ageLockParticleTimer--;
        }

        if (!mob.level().isClientSide() && mob.isBaby() && (mob instanceof Dolphin || mob instanceof Squid)) {
            if (!this.tiny_takeover_backport$isAgeLocked()) {
                this.tiny_takeover_backport$age++;
                if (this.tiny_takeover_backport$age >= 0) {
                    ((ModifiableBaby) mob).tiny_takeover_backport$setBaby(false);
                }
            }
        }
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void tiny_takeover_backport$handleInteractions(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        Mob mob = (Mob) (Object) this;
        ItemStack itemStack = player.getItemInHand(hand);

        if ((mob instanceof AgeableMob || mob instanceof Dolphin || mob instanceof Squid) && mob instanceof AgeLockable lockable) {
            if (mob instanceof Villager) return;

            if (itemStack.is(ModRegistry.GOLDEN_DANDELION_ITEM) && mob.isBaby() && lockable.tiny_takeover_backport$getAgeLockParticleTimer() == 0) {
                lockable.tiny_takeover_backport$setAgeLocked(!lockable.tiny_takeover_backport$isAgeLocked());
                if (lockable.tiny_takeover_backport$isAgeLocked()) {
                    mob.setPersistenceRequired();
                }
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
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