package com.evandev.tiny_takeover_backport.config;

//? if fabric && >=1.21 {
/*import com.evandev.tiny_takeover_backport.Constants;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.core.HolderLookup;
import org.jetbrains.annotations.Nullable;

public class NameTagRecipeEnabledCondition implements ResourceCondition {
    public static final MapCodec<NameTagRecipeEnabledCondition> CODEC = MapCodec.unit(NameTagRecipeEnabledCondition::new);
    public static final ResourceConditionType<NameTagRecipeEnabledCondition> TYPE = ResourceConditionType.create(
            Constants.location(Constants.MOD_ID, "nametag_recipe_enabled"),
            CODEC
    );

    @Override
    public ResourceConditionType<?> getType() {
        return TYPE;
    }

    @Override
    public boolean test(@Nullable HolderLookup.Provider provider) {
        return ModConfig.get().enableNameTagRecipe;
    }
}
*///?} else if neoforge {
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

public class NameTagRecipeEnabledCondition implements ICondition {
    public static final NameTagRecipeEnabledCondition INSTANCE = new NameTagRecipeEnabledCondition();
    public static final MapCodec<NameTagRecipeEnabledCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public boolean test(@NotNull IContext context) {
        return ModConfig.get().enableNameTagRecipe;
    }

    @Override
    public @NotNull MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
//?} else if forge {
/*import com.evandev.tiny_takeover_backport.Constants;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import org.jetbrains.annotations.NotNull;

public class NameTagRecipeEnabledCondition implements ICondition {
    public static final ResourceLocation ID = Constants.location(Constants.MOD_ID, "nametag_recipe_enabled");
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
*///?}
