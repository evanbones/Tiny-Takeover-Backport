package com.evandev.tiny_takeover_backport.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

final class BabyFelineAnimation {
    private static final float AGE_SCALE = 0.5F;

    private BabyFelineAnimation() {
    }

    static void setupAnim(
            ModelPart head, ModelPart body, ModelPart tail1, ModelPart tail2,
            ModelPart leftFrontLeg, ModelPart rightFrontLeg, ModelPart leftHindLeg, ModelPart rightHindLeg,
            boolean isCrouching, boolean isSprinting, boolean isSitting,
            float lieDownAmount, float lieDownAmountTail, float relaxStateOneAmount,
            float walkAnimationPos, float walkAnimationSpeed, float yRot, float xRot
    ) {
        for (ModelPart part : new ModelPart[]{head, body, tail1, tail2, leftFrontLeg, rightFrontLeg, leftHindLeg, rightHindLeg}) {
            part.resetPose();
        }

        if (isCrouching) {
            body.y += 1.0F * AGE_SCALE;
            head.y += 2.0F * AGE_SCALE;
            tail1.y += 1.0F * AGE_SCALE;
            tail2.y += -4.0F * AGE_SCALE;
            tail2.z += 2.0F * AGE_SCALE;
            tail1.xRot = (float) (Math.PI / 2);
            tail2.xRot = (float) (Math.PI / 2);
        } else if (isSprinting) {
            tail2.y = tail1.y;
            tail2.z += 2.0F * AGE_SCALE;
            tail1.xRot = (float) (Math.PI / 2);
            tail2.xRot = (float) (Math.PI / 2);
        }

        head.xRot = xRot * Mth.DEG_TO_RAD;
        head.yRot = yRot * Mth.DEG_TO_RAD;
        if (!isSitting) {
            if (isSprinting) {
                leftHindLeg.xRot = Mth.cos(walkAnimationPos * 0.6662F) * walkAnimationSpeed;
                rightHindLeg.xRot = Mth.cos(walkAnimationPos * 0.6662F + 0.3F) * walkAnimationSpeed;
                leftFrontLeg.xRot = Mth.cos(walkAnimationPos * 0.6662F + (float) Math.PI + 0.3F) * walkAnimationSpeed;
                rightFrontLeg.xRot = Mth.cos(walkAnimationPos * 0.6662F + (float) Math.PI) * walkAnimationSpeed;
                tail2.xRot = 1.7278761F + (float) (Math.PI / 10) * Mth.cos(walkAnimationPos) * walkAnimationSpeed;
            } else {
                leftHindLeg.xRot = Mth.cos(walkAnimationPos * 0.6662F) * walkAnimationSpeed;
                rightHindLeg.xRot = Mth.cos(walkAnimationPos * 0.6662F + (float) Math.PI) * walkAnimationSpeed;
                leftFrontLeg.xRot = Mth.cos(walkAnimationPos * 0.6662F + (float) Math.PI) * walkAnimationSpeed;
                rightFrontLeg.xRot = Mth.cos(walkAnimationPos * 0.6662F) * walkAnimationSpeed;
                if (!isCrouching) {
                    tail2.xRot = 1.7278761F + (float) (Math.PI / 4) * Mth.cos(walkAnimationPos) * walkAnimationSpeed;
                } else {
                    tail2.xRot = 1.7278761F + 0.47123894F * Mth.cos(walkAnimationPos) * walkAnimationSpeed;
                }
            }
        } else {
            body.xRot += -0.43633232F;
            body.y++;
            head.z += 0.75F;
            tail1.xRot += 0.5454154F;
            tail1.y += 4.0F;
            tail1.z -= 0.9F;
            leftHindLeg.z -= 0.9F;
            rightHindLeg.z -= 0.9F;
        }

        if (lieDownAmount > 0.0F) {
            body.x++;
            head.xRot = Mth.rotLerp(lieDownAmount, head.xRot, (float) (Math.PI / 18));
            head.zRot = Mth.rotLerp(lieDownAmount, head.zRot, (float) (-Math.PI * 5.0 / 12.0));
            head.x++;
            head.y += 0.75F;
            head.z -= 0.5F;
            rightFrontLeg.xRot = (float) (-Math.PI / 4);
            rightFrontLeg.x += 3.5F;
            rightFrontLeg.y -= 0.5F;
            leftFrontLeg.xRot = (float) (-Math.PI / 2);
            leftFrontLeg.x++;
            leftFrontLeg.y--;
            leftFrontLeg.z -= 2.0F;
            rightHindLeg.xRot = (float) (Math.PI * 2.0 / 9.0);
            rightHindLeg.yRot = (float) (Math.PI / 9);
            rightHindLeg.zRot = (float) (-Math.PI / 9);
            rightHindLeg.x += 2.5F;
            rightHindLeg.y -= 0.25F;
            rightHindLeg.z += 0.5F;
            leftHindLeg.x++;
            leftHindLeg.z--;
            tail1.xRot = tail1.xRot + Mth.rotLerp(lieDownAmountTail, tail1.xRot, (float) (-Math.PI / 6));
            tail1.yRot = tail1.yRot + Mth.rotLerp(lieDownAmountTail, tail1.yRot, 0.0F);
            tail1.zRot = tail1.zRot + Mth.rotLerp(lieDownAmountTail, tail1.zRot, (float) (-Math.PI / 18));
            tail1.x++;
            tail1.y += 0.5F;
            tail1.z -= 0.25F;
        }

        if (relaxStateOneAmount > 0.0F) {
            head.xRot = Mth.rotLerp(relaxStateOneAmount, head.xRot, -0.58177644F);
        }
    }
}
