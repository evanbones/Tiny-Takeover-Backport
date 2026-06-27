package com.evandev.tiny_takeover_backport.mixin;

import com.evandev.tiny_takeover_backport.entity.AgeLockable;
import com.evandev.tiny_takeover_backport.registry.ModRegistry;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AgeableMob.class)
public abstract class AgeableMobMixin extends PathfinderMob implements AgeLockable {

    @Unique
    private static final EntityDataAccessor<Boolean> tiny_takeover_backport$DATA_AGE_LOCKED =
            SynchedEntityData.defineId(AgeableMob.class, EntityDataSerializers.BOOLEAN);
    @Unique
    private int tiny_takeover_backport$ageLockParticleTimer = 0;

    protected AgeableMobMixin(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(tiny_takeover_backport$DATA_AGE_LOCKED, false);
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

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void writeAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        tag.putBoolean("AgeLocked", this.tiny_takeover_backport$isAgeLocked());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        this.tiny_takeover_backport$setAgeLocked(tag.getBoolean("AgeLocked"));
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void onAiStep(CallbackInfo ci) {
        AgeableMob mob = (AgeableMob) (Object) this;
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
    }

    @WrapOperation(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/AgeableMob;setAge(I)V"))
    private void wrapSetAge(AgeableMob instance, int age, Operation<Void> original) {
        if (!this.tiny_takeover_backport$isAgeLocked()) {
            original.call(instance, age);
        }
    }
}