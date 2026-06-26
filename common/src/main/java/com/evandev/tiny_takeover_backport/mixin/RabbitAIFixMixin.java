package com.evandev.tiny_takeover_backport.mixin;

import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Rabbit.class)
public abstract class RabbitAIFixMixin {

    @Unique
    private int tiny_takeover_backport$ticksSinceLastJump = 0;
    @Unique
    private int tiny_takeover_backport$ticksSinceLastLand = 0;
    @Unique
    private int tiny_takeover_backport$ticksStuckInJumpLoop = 0;

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void tiny_takeover_backport$onAiStep(CallbackInfo ci) {
        Rabbit rabbit = (Rabbit) (Object) this;

        if (rabbit.level().isClientSide()) {
            return;
        }

        if (!rabbit.onGround()) {
            this.tiny_takeover_backport$ticksSinceLastJump++;
        } else if (this.tiny_takeover_backport$ticksSinceLastJump > 0) {
            this.tiny_takeover_backport$ticksSinceLastJump = 0;
            this.tiny_takeover_backport$ticksSinceLastLand = 0;
        } else {
            this.tiny_takeover_backport$ticksSinceLastLand++;
        }

        if (this.tiny_takeover_backport$ticksSinceLastLand < 2) {
            this.tiny_takeover_backport$ticksStuckInJumpLoop++;
        } else {
            this.tiny_takeover_backport$ticksStuckInJumpLoop = 0;
        }

        float rabbitYaw = rabbit.yBodyRot;
        float radians = (float) Math.toRadians(rabbitYaw);
        Vec3 direction = new Vec3(-Math.sin(radians), 0, Math.cos(radians));

        // Stuck check
        if (this.tiny_takeover_backport$ticksStuckInJumpLoop > 5) {
            this.tiny_takeover_backport$ticksStuckInJumpLoop = -25;
            tiny_takeover_backport$forceJump(rabbit, direction);
        }
    }

    @Unique
    private void tiny_takeover_backport$forceJump(Rabbit rabbit, Vec3 direction) {
        rabbit.setJumping(false);

        Vec3 rabbitPos = rabbit.position();
        rabbit.setPos(rabbitPos.x + direction.x * 0.05D, rabbitPos.y + 0.5D, rabbitPos.z + direction.z * 0.05D);

        Vec3 velocity = rabbit.getDeltaMovement();
        rabbit.setDeltaMovement(velocity.x + direction.x * 0.15D, 0.22D, velocity.z + direction.z * 0.15D);
    }
}