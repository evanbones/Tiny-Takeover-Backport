package com.evandev.tiny_takeover_backport.mixin;

import com.evandev.tiny_takeover_backport.config.ModConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.horse.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.piglin.*;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    private void tiny_takeover_backport$modifyDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.isBaby() && pose != Pose.SLEEPING) {
            String entityName = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getPath();
            if (ModConfig.get().isModelEnabled(entityName)) {
                EntityDimensions custom = null;

                if (entity instanceof Squid) {
                    custom = EntityDimensions.scalable(0.5F, 0.63F).withEyeHeight(0.37F);
                } else if (entity instanceof Dolphin) {
                    custom = EntityType.DOLPHIN.getDimensions().scale(0.65F);
                } else if (entity instanceof Chicken) {
                    custom = EntityDimensions.scalable(0.3F, 0.4F).withEyeHeight(0.28F);
                } else if (entity instanceof Fox) {
                    custom = EntityType.FOX.getDimensions().scale(0.6F).withEyeHeight(0.2975F);
                } else if (entity instanceof ZombieVillager) {
                    custom = EntityDimensions.scalable(0.49F, 0.99F).withEyeHeight(0.67F);
                } else if (entity instanceof Husk) {
                    custom = EntityDimensions.scalable(0.49F, 0.99F).withEyeHeight(0.825F);
                } else if (entity instanceof Drowned) {
                    custom = EntityDimensions.scalable(0.49F, 0.99F).withEyeHeight(0.775F);
                } else if (entity instanceof Zombie) {
                    custom = EntityDimensions.scalable(0.49F, 0.99F).withEyeHeight(0.775F);
                } else if (entity instanceof ZombifiedPiglin) {
                    custom = EntityDimensions.scalable(0.49F, 0.99F).withEyeHeight(0.78F);
                } else if (entity instanceof Piglin) {
                    custom = EntityDimensions.scalable(0.49F, 0.99F).withEyeHeight(0.78F);
                } else if (entity instanceof Villager) {
                    custom = EntityDimensions.scalable(0.49F, 0.99F).withEyeHeight(0.63F);
                } else if (entity instanceof Axolotl) {
                    custom = EntityDimensions.scalable(0.5F, 0.25F).withEyeHeight(0.2F);
                }

                if (custom != null) {
                    cir.setReturnValue(custom.scale(entity.getScale()));
                }
            }
        }
    }

    @Inject(method = "getAgeScale", at = @At("RETURN"), cancellable = true)
    private void tiny_takeover_backport$modifyAgeScale(CallbackInfoReturnable<Float> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.isBaby()) {
            String entityName = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getPath();
            if (ModConfig.get().isModelEnabled(entityName)) {
                if (entity instanceof Dolphin) {
                    cir.setReturnValue(0.65F);
                } else if (entity instanceof Goat) {
                    cir.setReturnValue(0.55F);
                } else if (entity instanceof Horse || entity instanceof SkeletonHorse || entity instanceof ZombieHorse) {
                    cir.setReturnValue(0.7F);
                }
            }
        }
    }
}
