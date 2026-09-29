package com.evandev.tiny_takeover_backport;

//? if fabric {
/*//? if >=1.21 {
import com.evandev.tiny_takeover_backport.config.NameTagRecipeEnabledCondition;
//?} else
//import com.evandev.tiny_takeover_backport.config.ModConfig;
import com.evandev.tiny_takeover_backport.registry.ModRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;

public class TinyTakeoverBackportFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonClass.init();

        ModRegistry.BLOCKS.forEach((id, block) -> Registry.register(BuiltInRegistries.BLOCK, id, block));
        ModRegistry.ITEMS.forEach((id, item) -> Registry.register(BuiltInRegistries.ITEM, id, item));
        ModRegistry.SOUND_EVENTS.forEach((id, sound) -> Registry.register(BuiltInRegistries.SOUND_EVENT, id, sound));
        ModRegistry.PARTICLES.forEach((id, particle) -> Registry.register(BuiltInRegistries.PARTICLE_TYPE, id, particle));
        //? if >=1.21 {
        ResourceConditions.register(NameTagRecipeEnabledCondition.TYPE);
        //?} else
        //ResourceConditions.register(Constants.location(Constants.MOD_ID, "nametag_recipe_enabled"), jsonObject -> ModConfig.get().enableNameTagRecipe);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(content -> {
            content.accept(ModRegistry.GOLDEN_DANDELION_ITEM);
        });
    }
}
*///?}
