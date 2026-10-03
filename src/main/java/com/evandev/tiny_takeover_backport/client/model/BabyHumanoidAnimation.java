package com.evandev.tiny_takeover_backport.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;

final class BabyHumanoidAnimation {
    private static final float AGE_SCALE = 0.5F;

    private BabyHumanoidAnimation() {
    }

    static void applyAttackArmOffsets(HumanoidModel<?> model) {
        if (model.attackTime > 0.0F) {
            model.rightArm.z = Mth.sin(model.body.yRot) * 5.0F * AGE_SCALE;
            model.rightArm.x = -Mth.cos(model.body.yRot) * 5.0F * AGE_SCALE;
            model.leftArm.z = -Mth.sin(model.body.yRot) * 5.0F * AGE_SCALE;
            model.leftArm.x = Mth.cos(model.body.yRot) * 5.0F * AGE_SCALE;
        }
    }

    static void animateBabyPiglinEars(HumanoidModel<?> model, float limbSwing, float limbSwingAmount, float ageInTicks) {
        float defaultAngle = 5.0F * Mth.DEG_TO_RAD;
        float frequency = ageInTicks * 0.1F + limbSwing * 0.5F;
        float amplitude = 0.08F + limbSwingAmount * 0.4F;
        model.head.getChild("left_ear").zRot = -defaultAngle - Mth.cos(frequency * 1.2F) * amplitude;
        model.head.getChild("right_ear").zRot = defaultAngle + Mth.cos(frequency) * amplitude;
    }
}
