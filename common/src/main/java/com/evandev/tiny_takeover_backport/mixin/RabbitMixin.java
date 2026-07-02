package com.evandev.tiny_takeover_backport.mixin;

import com.evandev.tiny_takeover_backport.config.ModConfig;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Rabbit.class)
public abstract class RabbitMixin extends Animal {

    @Unique
    private static final EntityDimensions ADULT_261_DIMENSIONS = EntityDimensions.scalable(0.49F, 0.6F);
    @Unique
    private static final EntityDimensions BABY_261_DIMENSIONS = EntityDimensions.scalable(0.24F, 0.4F);

    protected RabbitMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        if (ModConfig.get().rabbitBoundingBox) {
            return this.isBaby() ? BABY_261_DIMENSIONS : ADULT_261_DIMENSIONS;
        }
        return super.getDimensions(pose);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, @NotNull EntityDimensions dimensions) {
        if (ModConfig.get().rabbitBoundingBox) {
            return this.isBaby() ? 0.39F : 0.59F;
        }
        return super.getStandingEyeHeight(pose, dimensions);
    }
}