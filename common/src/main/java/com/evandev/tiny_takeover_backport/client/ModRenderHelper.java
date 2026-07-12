package com.evandev.tiny_takeover_backport.client;

import com.evandev.tiny_takeover_backport.client.model.*;
import com.evandev.tiny_takeover_backport.config.ModConfig;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;

public class ModRenderHelper {
    public static final ThreadLocal<Boolean> SUPPRESS_AGE_SCALE = ThreadLocal.withInitial(() -> false);
    public static EntityModel<?> babyWoolModel;

    public static <T extends LivingEntity, A extends HumanoidModel<T>> void adjustBabyArmorScale(A model, EntityModel<?> parentModel) {
        if (parentModel instanceof BabyDrownedModel) {
            if (ModConfig.get().enableDrowned) {
                model.head.xScale = 0.75F;
                model.head.yScale = 0.75F;
                model.head.zScale = 0.75F;
                model.hat.xScale = 0.75F;
                model.hat.yScale = 0.75F;
                model.hat.zScale = 0.75F;

                model.body.xScale = 0.5F;
                model.body.yScale = 0.5F;
                model.body.zScale = 0.5F;
                model.rightArm.xScale = 0.5F;
                model.rightArm.yScale = 0.5F;
                model.rightArm.zScale = 0.5F;
                model.leftArm.xScale = 0.5F;
                model.leftArm.yScale = 0.5F;
                model.leftArm.zScale = 0.5F;
                model.rightLeg.xScale = 0.5F;
                model.rightLeg.yScale = 0.5F;
                model.rightLeg.zScale = 0.5F;
                model.leftLeg.xScale = 0.5F;
                model.leftLeg.yScale = 0.5F;
                model.leftLeg.zScale = 0.5F;

                model.body.y -= 2.5F;
                model.rightArm.y += 0.5F;
                model.leftArm.y += 0.5F;
            }
        } else if (parentModel instanceof BabyZombieModel) {
            if (ModConfig.get().enableZombie) {
                model.head.xScale = 0.75F;
                model.head.yScale = 0.75F;
                model.head.zScale = 0.75F;
                model.hat.xScale = 0.75F;
                model.hat.yScale = 0.75F;
                model.hat.zScale = 0.75F;

                model.body.xScale = 0.5F;
                model.body.yScale = 0.5F;
                model.body.zScale = 0.5F;
                model.rightArm.xScale = 0.5F;
                model.rightArm.yScale = 0.5F;
                model.rightArm.zScale = 0.5F;
                model.leftArm.xScale = 0.5F;
                model.leftArm.yScale = 0.5F;
                model.leftArm.zScale = 0.5F;
                model.rightLeg.xScale = 0.5F;
                model.rightLeg.yScale = 0.5F;
                model.rightLeg.zScale = 0.5F;
                model.leftLeg.xScale = 0.5F;
                model.leftLeg.yScale = 0.5F;
                model.leftLeg.zScale = 0.5F;

                model.body.y -= 2.5F;
                model.rightArm.y += 0.5F;
                model.leftArm.y += 0.5F;
            }
        } else if (parentModel instanceof BabyZombieVillagerModel) {
            if (ModConfig.get().enableZombieVillager) {
                model.head.xScale = 1.0F;
                model.head.yScale = 0.9F;
                model.head.zScale = 1.0F;
                model.hat.xScale = 1.0F;
                model.hat.yScale = 0.9F;
                model.hat.zScale = 1.0F;

                model.head.z -= 0.5F;
                model.hat.z -= 0.5F;

                model.body.xScale = 0.55F;
                model.body.yScale = 0.5F;
                model.body.zScale = 0.8F;

                model.rightArm.xScale = 0.5F;
                model.rightArm.yScale = 0.5F;
                model.rightArm.zScale = 0.5F;
                model.leftArm.xScale = 0.5F;
                model.leftArm.yScale = 0.5F;
                model.leftArm.zScale = 0.5F;

                model.rightLeg.xScale = 0.5F;
                model.rightLeg.yScale = 0.5F;
                model.rightLeg.zScale = 0.5F;
                model.leftLeg.xScale = 0.5F;
                model.leftLeg.yScale = 0.5F;
                model.leftLeg.zScale = 0.5F;

                model.body.y -= 2.75F;
                model.rightArm.y += 0.5F;
                model.leftArm.y += 0.5F;
                model.rightLeg.y -= 0.5F;
                model.leftLeg.y -= 0.5F;
            }
        } else if (parentModel instanceof BabyZombifiedPiglinModel) {
            if (ModConfig.get().enableZombifiedPiglin) {
                model.head.xScale = 1.0F;
                model.head.yScale = 0.8F;
                model.head.zScale = 0.9F;
                model.hat.xScale = 1.0F;
                model.hat.yScale = 0.8F;
                model.hat.zScale = 0.9F;

                model.body.xScale = 0.8F;
                model.body.yScale = 0.5F;
                model.body.zScale = 0.8F;

                model.rightArm.xScale = 0.5F;
                model.rightArm.yScale = 0.5F;
                model.rightArm.zScale = 0.8F;
                model.rightArm.x -= 0.5F;

                model.leftArm.xScale = 0.5F;
                model.leftArm.yScale = 0.5F;
                model.leftArm.zScale = 0.8F;
                model.leftArm.x += 0.5F;

                model.rightLeg.xScale = 0.8F;
                model.rightLeg.yScale = 0.5F;
                model.rightLeg.zScale = 0.8F;
                model.rightLeg.x -= 0.25F;

                model.leftLeg.xScale = 0.8F;
                model.leftLeg.yScale = 0.5F;
                model.leftLeg.zScale = 0.8F;
                model.leftLeg.x += 0.25F;

                model.body.y -= 3.0F;
                model.rightArm.y += 1.0F;
                model.leftArm.y += 1.0F;
            }
        } else if (parentModel instanceof BabyPiglinModel) {
            if (ModConfig.get().enablePiglin) {
                model.head.xScale = 1.0F;
                model.head.yScale = 0.8F;
                model.head.zScale = 0.9F;
                model.hat.xScale = 1.0F;
                model.hat.yScale = 0.8F;
                model.hat.zScale = 0.9F;

                model.body.xScale = 0.8F;
                model.body.yScale = 0.5F;
                model.body.zScale = 0.8F;

                model.rightArm.xScale = 0.5F;
                model.rightArm.yScale = 0.5F;
                model.rightArm.zScale = 0.8F;
                model.rightArm.x -= 0.5F;

                model.leftArm.xScale = 0.5F;
                model.leftArm.yScale = 0.5F;
                model.leftArm.zScale = 0.8F;
                model.leftArm.x += 0.5F;

                model.rightLeg.xScale = 0.8F;
                model.rightLeg.yScale = 0.5F;
                model.rightLeg.zScale = 0.8F;
                model.rightLeg.x -= 0.25F;

                model.leftLeg.xScale = 0.8F;
                model.leftLeg.yScale = 0.5F;
                model.leftLeg.zScale = 0.8F;
                model.leftLeg.x += 0.25F;

                model.body.y -= 3.0F;
                model.rightArm.y += 1.0F;
                model.leftArm.y += 1.0F;
            }
        }
    }
}
