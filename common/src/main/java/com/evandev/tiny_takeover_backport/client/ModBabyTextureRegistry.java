package com.evandev.tiny_takeover_backport.client;

import com.evandev.tiny_takeover_backport.config.ModConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class ModBabyTextureRegistry {
    public static ResourceLocation getBabyTexture(LivingEntity entity, ResourceLocation original) {
        if (!entity.isBaby()) {
            return original;
        }

        String entityName = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getPath();
        if (!ModConfig.get().isModelEnabled(entityName)) {
            return original;
        }

        String namespace = original.getNamespace();
        String path = original.getPath();

        if (!namespace.equals("minecraft")) {
            return original;
        }

        switch (path) {
            case "textures/entity/chicken.png" -> {
                return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/chicken/chicken_temperate_baby.png");
            }
            case "textures/entity/cow/cow.png" -> {
                return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/cow/cow_temperate_baby.png");
            }
            case "textures/entity/cow/brown_mooshroom.png" -> {
                return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/cow/mooshroom_brown_baby.png");
            }
            case "textures/entity/cow/red_mooshroom.png" -> {
                return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/cow/mooshroom_red_baby.png");
            }
            case "textures/entity/pig/pig.png" -> {
                return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/pig/pig_temperate_baby.png");
            }
            case "textures/entity/turtle/big_sea_turtle.png" -> {
                return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/turtle/turtle_baby.png");
            }
            case "textures/entity/bear/polarbear.png" -> {
                return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/bear/polarbear_baby.png");
            }
            case "textures/entity/dolphin.png" -> {
                return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/dolphin/dolphin_baby.png");
            }
            case "textures/entity/sniffer/sniffer.png" -> {
                return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/sniffer/snifflet.png");
            }
        }

        // textures/entity/rabbit/brown.png -> textures/entity/rabbit/rabbit_brown_baby.png
        if (path.startsWith("textures/entity/rabbit/")) {
            String rabbitName = path.substring("textures/entity/rabbit/".length(), path.length() - 4);
            return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/rabbit/rabbit_" + rabbitName + "_baby.png");
        }

        // textures/entity/villager/villager.png -> textures/entity/villager/villager_baby.png
        // textures/entity/villager/type/desert.png -> textures/entity/villager/baby/desert.png
        if (path.equals("textures/entity/villager/villager.png")) {
            return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/villager/villager_baby.png");
        }
        if (path.startsWith("textures/entity/villager/type/")) {
            String typeName = path.substring("textures/entity/villager/type/".length());
            return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/villager/baby/" + typeName);
        }

        // textures/entity/zombie_villager/zombie_villager.png -> textures/entity/zombie_villager/zombie_villager_baby.png
        // textures/entity/zombie_villager/type/desert.png -> textures/entity/zombie_villager/baby/desert.png
        if (path.equals("textures/entity/zombie_villager/zombie_villager.png")) {
            return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/zombie_villager/zombie_villager_baby.png");
        }
        if (path.startsWith("textures/entity/zombie_villager/type/")) {
            String typeName = path.substring("textures/entity/zombie_villager/type/".length());
            return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/zombie_villager/baby/" + typeName);
        }

        if (path.endsWith(".png")) {
            String newPath = path.substring(0, path.length() - 4) + "_baby.png";
            return ResourceLocation.fromNamespaceAndPath("minecraft", newPath);
        }

        return original;
    }
}
