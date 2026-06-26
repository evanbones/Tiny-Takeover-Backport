package com.evandev.tiny_takeover_backport;

import com.evandev.tiny_takeover_backport.config.ModConfig;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class NameTagRecipeEnabledCondition implements ResourceCondition {
    public static final MapCodec<NameTagRecipeEnabledCondition> CODEC = MapCodec.unit(NameTagRecipeEnabledCondition::new);
    public static final ResourceConditionType<NameTagRecipeEnabledCondition> TYPE = ResourceConditionType.create(
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "nametag_recipe_enabled"),
            CODEC
    );

    public NameTagRecipeEnabledCondition() {
    }

    @Override
    public ResourceConditionType<?> getType() {
        return TYPE;
    }

    @Override
    public boolean test(@Nullable HolderLookup.Provider provider) {
        return ModConfig.get().enableNameTagRecipe;
    }
}
