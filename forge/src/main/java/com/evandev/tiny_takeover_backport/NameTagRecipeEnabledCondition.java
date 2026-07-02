package com.evandev.tiny_takeover_backport;

import com.evandev.tiny_takeover_backport.config.ModConfig;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import org.jetbrains.annotations.NotNull;

public class NameTagRecipeEnabledCondition implements ICondition {
    public static final ResourceLocation ID = new ResourceLocation(Constants.MOD_ID, "nametag_recipe_enabled");
    public static final NameTagRecipeEnabledCondition INSTANCE = new NameTagRecipeEnabledCondition();
    public static final IConditionSerializer<NameTagRecipeEnabledCondition> SERIALIZER = new IConditionSerializer<>() {
        @Override
        public void write(JsonObject json, NameTagRecipeEnabledCondition value) {
        }

        @Override
        public NameTagRecipeEnabledCondition read(JsonObject json) {
            return INSTANCE;
        }

        @Override
        public ResourceLocation getID() {
            return ID;
        }
    };

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public boolean test(@NotNull IContext context) {
        return ModConfig.get().enableNameTagRecipe;
    }
}
