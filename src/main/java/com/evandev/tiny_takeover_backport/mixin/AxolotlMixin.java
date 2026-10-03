package com.evandev.tiny_takeover_backport.mixin;

import com.evandev.tiny_takeover_backport.entity.AxolotlAnimationStates;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Axolotl.class)
public abstract class AxolotlMixin extends Animal implements AxolotlAnimationStates {
    @Unique
    private final AnimationState tiny_takeover_backport$swimAnimationState = new AnimationState();
    @Unique
    private final AnimationState tiny_takeover_backport$walkAnimationState = new AnimationState();
    @Unique
    private final AnimationState tiny_takeover_backport$walkUnderWaterAnimationState = new AnimationState();
    @Unique
    private final AnimationState tiny_takeover_backport$idleUnderWaterAnimationState = new AnimationState();
    @Unique
    private final AnimationState tiny_takeover_backport$idleUnderWaterOnGroundAnimationState = new AnimationState();
    @Unique
    private final AnimationState tiny_takeover_backport$idleOnGroundAnimationState = new AnimationState();
    @Unique
    private final AnimationState tiny_takeover_backport$playDeadAnimationState = new AnimationState();

    protected AxolotlMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract boolean isPlayingDead();

    @Inject(method = "baseTick", at = @At("TAIL"))
    private void tiny_takeover_backport$tickBabyAnimations(CallbackInfo ci) {
        if (!this.level().isClientSide() || !this.isBaby()) return;
        boolean isInWater = this.isInWater();
        boolean onGround = this.onGround();
        boolean isMoving = this.walkAnimation.isMoving() || this.getXRot() != this.xRotO || this.getYRot() != this.yRotO;
        if (this.isPlayingDead()) {
            this.tiny_takeover_backport$soloAnimation(this.tiny_takeover_backport$playDeadAnimationState);
        } else if (isMoving) {
            if (isInWater && !onGround) {
                this.tiny_takeover_backport$soloAnimation(this.tiny_takeover_backport$swimAnimationState);
            } else if (!isInWater && onGround) {
                this.tiny_takeover_backport$soloAnimation(this.tiny_takeover_backport$walkAnimationState);
            } else {
                this.tiny_takeover_backport$soloAnimation(this.tiny_takeover_backport$walkUnderWaterAnimationState);
            }
        } else if (isInWater && !onGround) {
            this.tiny_takeover_backport$soloAnimation(this.tiny_takeover_backport$idleUnderWaterAnimationState);
        } else if (isInWater) {
            this.tiny_takeover_backport$soloAnimation(this.tiny_takeover_backport$idleUnderWaterOnGroundAnimationState);
        } else {
            this.tiny_takeover_backport$soloAnimation(this.tiny_takeover_backport$idleOnGroundAnimationState);
        }
    }

    @Unique
    private void tiny_takeover_backport$soloAnimation(AnimationState toStart) {
        for (AnimationState animation : new AnimationState[]{
                this.tiny_takeover_backport$swimAnimationState,
                this.tiny_takeover_backport$walkAnimationState,
                this.tiny_takeover_backport$walkUnderWaterAnimationState,
                this.tiny_takeover_backport$idleUnderWaterAnimationState,
                this.tiny_takeover_backport$idleUnderWaterOnGroundAnimationState,
                this.tiny_takeover_backport$idleOnGroundAnimationState,
                this.tiny_takeover_backport$playDeadAnimationState
        }) {
            if (animation == toStart) {
                animation.startIfStopped(this.tickCount);
            } else {
                animation.stop();
            }
        }
    }

    @Override
    public AnimationState tiny_takeover_backport$getSwimAnimationState() {
        return this.tiny_takeover_backport$swimAnimationState;
    }

    @Override
    public AnimationState tiny_takeover_backport$getWalkAnimationState() {
        return this.tiny_takeover_backport$walkAnimationState;
    }

    @Override
    public AnimationState tiny_takeover_backport$getWalkUnderWaterAnimationState() {
        return this.tiny_takeover_backport$walkUnderWaterAnimationState;
    }

    @Override
    public AnimationState tiny_takeover_backport$getIdleUnderWaterAnimationState() {
        return this.tiny_takeover_backport$idleUnderWaterAnimationState;
    }

    @Override
    public AnimationState tiny_takeover_backport$getIdleUnderWaterOnGroundAnimationState() {
        return this.tiny_takeover_backport$idleUnderWaterOnGroundAnimationState;
    }

    @Override
    public AnimationState tiny_takeover_backport$getIdleOnGroundAnimationState() {
        return this.tiny_takeover_backport$idleOnGroundAnimationState;
    }

    @Override
    public AnimationState tiny_takeover_backport$getPlayDeadAnimationState() {
        return this.tiny_takeover_backport$playDeadAnimationState;
    }
}
