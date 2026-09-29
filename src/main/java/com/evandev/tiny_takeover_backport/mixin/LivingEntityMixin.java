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
import org.spongepowered.asm.mixin.Unique;
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
                    custom = EntityDimensions.scalable(0.5F, 0.63F);
                } else if (entity instanceof Dolphin) {
                    custom = EntityType.DOLPHIN.getDimensions().scale(0.65F);
                } else if (entity instanceof Chicken) {
                    custom = EntityDimensions.scalable(0.3F, 0.4F);
                } else if (entity instanceof Fox) {
                    custom = EntityType.FOX.getDimensions().scale(0.6F);
                } else if (entity instanceof ZombieVillager) {
                    custom = EntityDimensions.scalable(0.49F, 0.99F);
                } else if (entity instanceof Husk) {
                    custom = EntityDimensions.scalable(0.49F, 0.99F);
                } else if (entity instanceof Drowned) {
                    custom = EntityDimensions.scalable(0.49F, 0.99F);
                } else if (entity instanceof Zombie) {
                    custom = EntityDimensions.scalable(0.49F, 0.99F);
                } else if (entity instanceof ZombifiedPiglin) {
                    custom = EntityDimensions.scalable(0.49F, 0.99F);
                } else if (entity instanceof Piglin) {
                    custom = EntityDimensions.scalable(0.49F, 0.99F);
                } else if (entity instanceof Villager) {
                    custom = EntityDimensions.scalable(0.49F, 0.99F);
                } else if (entity instanceof Axolotl) {
                    custom = EntityDimensions.scalable(0.5F, 0.25F);
                }
                //? if <1.21 {
                /*else if (entity instanceof Camel) {
                    custom = EntityType.CAMEL.getDimensions().scale(0.6F);
                } else if (entity instanceof Goat) {
                    custom = EntityType.GOAT.getDimensions().scale(0.55F);
                } else if (entity instanceof Horse) {
                    custom = EntityType.HORSE.getDimensions().scale(0.7F);
                } else if (entity instanceof SkeletonHorse) {
                    custom = EntityType.SKELETON_HORSE.getDimensions().scale(0.7F);
                } else if (entity instanceof ZombieHorse) {
                    custom = EntityType.ZOMBIE_HORSE.getDimensions().scale(0.7F);
                }
                *///?}

                if (custom != null) {
                    //? if >=1.21 {
                    Float eyeHeight = tiny_takeover_backport$getBabyEyeHeight(entity);
                    if (eyeHeight != null) {
                        custom = custom.withEyeHeight(eyeHeight);
                    }
                    //?}
                    cir.setReturnValue(custom.scale(entity.getScale()));
                }
            }
        }
    }

    //? if >=1.21 {
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
    //?} else {
    /*@Inject(method = "getStandingEyeHeight", at = @At("RETURN"), cancellable = true)
    private void tiny_takeover_backport$modifyEyeHeight(Pose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.isBaby()) {
            String entityName = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getPath();
            if (ModConfig.get().isModelEnabled(entityName)) {
                Float eyeHeight = tiny_takeover_backport$getBabyEyeHeight(entity);
                if (eyeHeight != null) {
                    cir.setReturnValue(eyeHeight);
                }
            }
        }
    }
    *///?}

    @Unique
    private static Float tiny_takeover_backport$getBabyEyeHeight(LivingEntity entity) {
        if (entity instanceof Squid) return 0.37F;
        if (entity instanceof Chicken) return 0.28F;
        if (entity instanceof Fox) return 0.2975F;
        if (entity instanceof ZombieVillager) return 0.67F;
        if (entity instanceof Husk) return 0.825F;
        if (entity instanceof Drowned) return 0.775F;
        if (entity instanceof Zombie) return 0.775F;
        if (entity instanceof ZombifiedPiglin) return 0.78F;
        if (entity instanceof Piglin) return 0.78F;
        if (entity instanceof Villager) return 0.63F;
        if (entity instanceof Axolotl) return 0.2F;
        return null;
    }
}
