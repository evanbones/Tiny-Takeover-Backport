package com.evandev.tiny_takeover_backport.compat;

import com.blackgear.vanillabackport.client.level.entities.model.ArmadilloModel;
import com.evandev.tiny_takeover_backport.client.ModClientRegistry;
import com.evandev.tiny_takeover_backport.client.ModModelLayers;
import com.evandev.tiny_takeover_backport.client.model.BabyArmadilloModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.LivingEntity;

public class VanillaBackportClientCompat {

    @SuppressWarnings("unchecked")
    public static <T extends LivingEntity, M extends EntityModel<T>> EntityModel<T> createBabyArmadilloModel(
            EntityRendererProvider.Context context, M adultModel) {
        if (adultModel instanceof ArmadilloModel) {
            return (EntityModel<T>) new BabyArmadilloModel(context.getModelSet().bakeLayer(ModModelLayers.ARMADILLO_BABY));
        }
        return null;
    }

    public static void registerArmadilloLayer() {
        ModClientRegistry.register(ModModelLayers.ARMADILLO_BABY, BabyArmadilloModel::createBodyLayer);
    }
}
