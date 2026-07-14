package com.evandev.tiny_takeover_backport.client;

import com.evandev.tiny_takeover_backport.client.model.*;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class ModRenderHelper {
    public static final ThreadLocal<Boolean> SUPPRESS_AGE_SCALE = ThreadLocal.withInitial(() -> false);
    public static EntityModel<?> babyWoolModel;

    private static HumanoidModel<?> babyZombieInnerModel;
    private static HumanoidModel<?> babyZombieOuterModel;
    private static HumanoidModel<?> babyPiglinInnerModel;
    private static HumanoidModel<?> babyPiglinOuterModel;

    @SuppressWarnings("unchecked")
    public static <T extends LivingEntity, A extends HumanoidModel<T>> A getBabyArmorModel(A originalModel, EntityModel<?> parentModel, EquipmentSlot slot) {
        if (parentModel instanceof BabyZombieModel || parentModel instanceof BabyDrownedModel || parentModel instanceof BabyZombieVillagerModel) {
            boolean isInner = (slot == EquipmentSlot.LEGS);
            if (babyZombieInnerModel == null) {
                net.minecraft.client.model.geom.EntityModelSet modelSet = net.minecraft.client.Minecraft.getInstance().getEntityModels();
                babyZombieInnerModel = new BabyZombieModel<>(modelSet.bakeLayer(ModModelLayers.ZOMBIE_BABY_INNER_ARMOR));
                babyZombieOuterModel = new BabyZombieModel<>(modelSet.bakeLayer(ModModelLayers.ZOMBIE_BABY_OUTER_ARMOR));
            }
            return (A) (isInner ? babyZombieInnerModel : babyZombieOuterModel);
        } else if (parentModel instanceof BabyPiglinModel || parentModel instanceof BabyZombifiedPiglinModel) {
            boolean isInner = (slot == EquipmentSlot.LEGS);
            if (babyPiglinInnerModel == null) {
                net.minecraft.client.model.geom.EntityModelSet modelSet = net.minecraft.client.Minecraft.getInstance().getEntityModels();
                babyPiglinInnerModel = new BabyPiglinModel(modelSet.bakeLayer(ModModelLayers.PIGLIN_BABY_INNER_ARMOR));
                babyPiglinOuterModel = new BabyPiglinModel(modelSet.bakeLayer(ModModelLayers.PIGLIN_BABY_OUTER_ARMOR));
            }
            return (A) (isInner ? babyPiglinInnerModel : babyPiglinOuterModel);
        }
        return originalModel;
    }
}
