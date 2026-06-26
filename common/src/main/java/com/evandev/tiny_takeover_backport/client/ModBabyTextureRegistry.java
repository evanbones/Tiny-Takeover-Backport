package com.evandev.tiny_takeover_backport.client;

import com.evandev.tiny_takeover_backport.config.ModConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class ModBabyTextureRegistry {
    public static ResourceLocation getBabyTexture(LivingEntity entity, ResourceLocation original) {
        if (!entity.isBaby()) {
            String entityName = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getPath();
            if (entityName.equals("rabbit")
                    && ModConfig.get().replaceAdultRabbit
                    && "minecraft".equals(original.getNamespace())
                    && original.getPath().startsWith("textures/entity/rabbit/")) {
                String name = original.getPath().substring("textures/entity/rabbit/".length(), original.getPath().length() - 4);
                return ResourceLocation.withDefaultNamespace("textures/entity/rabbit/rabbit_" + name + ".png");
            }
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
                return ResourceLocation.withDefaultNamespace("textures/entity/chicken/chicken_temperate_baby.png");
            }
            case "textures/entity/cow/cow.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/cow/cow_temperate_baby.png");
            }
            case "textures/entity/cow/brown_mooshroom.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/cow/mooshroom_brown_baby.png");
            }
            case "textures/entity/cow/red_mooshroom.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/cow/mooshroom_red_baby.png");
            }
            case "textures/entity/pig/pig.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/pig/pig_temperate_baby.png");
            }
            case "textures/entity/turtle/big_sea_turtle.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/turtle/turtle_baby.png");
            }
            case "textures/entity/bear/polarbear.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/bear/polarbear_baby.png");
            }
            case "textures/entity/dolphin.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/dolphin/dolphin_baby.png");
            }
            case "textures/entity/sniffer/sniffer.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/sniffer/snifflet.png");
            }
            case "textures/entity/armadillo.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/armadillo/armadillo_baby.png");
            }
            case "textures/entity/cat/tabby.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/cat/cat_tabby_baby.png");
            }
            case "textures/entity/cat/black.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/cat/cat_black_baby.png");
            }
            case "textures/entity/cat/red.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/cat/cat_red_baby.png");
            }
            case "textures/entity/cat/siamese.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/cat/cat_siamese_baby.png");
            }
            case "textures/entity/cat/british_shorthair.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/cat/cat_british_shorthair_baby.png");
            }
            case "textures/entity/cat/calico.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/cat/cat_calico_baby.png");
            }
            case "textures/entity/cat/persian.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/cat/cat_persian_baby.png");
            }
            case "textures/entity/cat/ragdoll.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/cat/cat_ragdoll_baby.png");
            }
            case "textures/entity/cat/white.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/cat/cat_white_baby.png");
            }
            case "textures/entity/cat/jellie.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/cat/cat_jellie_baby.png");
            }
            case "textures/entity/cat/all_black.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/cat/cat_all_black_baby.png");
            }
            case "textures/entity/ocelot.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/cat/ocelot_baby.png");
            }
            case "textures/entity/llama/creamy.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/llama/llama_creamy_baby.png");
            }
            case "textures/entity/llama/white.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/llama/llama_white_baby.png");
            }
            case "textures/entity/llama/brown.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/llama/llama_brown_baby.png");
            }
            case "textures/entity/llama/gray.png" -> {
                return ResourceLocation.withDefaultNamespace("textures/entity/llama/llama_gray_baby.png");
            }
        }

        if (path.startsWith("textures/entity/rabbit/")) {
            String rabbitName = path.substring("textures/entity/rabbit/".length(), path.length() - 4);
            return ResourceLocation.withDefaultNamespace("textures/entity/rabbit/rabbit_" + rabbitName + "_baby.png");
        }

        if (path.equals("textures/entity/villager/villager.png")) {
            return ResourceLocation.withDefaultNamespace("textures/entity/villager/villager_baby.png");
        }
        if (path.startsWith("textures/entity/villager/type/")) {
            String typeName = path.substring("textures/entity/villager/type/".length());
            return ResourceLocation.withDefaultNamespace("textures/entity/villager/baby/" + typeName);
        }

        if (path.equals("textures/entity/zombie_villager/zombie_villager.png")) {
            return ResourceLocation.withDefaultNamespace("textures/entity/zombie_villager/zombie_villager_baby.png");
        }
        if (path.startsWith("textures/entity/zombie_villager/type/")) {
            String typeName = path.substring("textures/entity/zombie_villager/type/".length());
            return ResourceLocation.withDefaultNamespace("textures/entity/zombie_villager/baby/" + typeName);
        }

        if (path.endsWith(".png")) {
            String newPath = path.substring(0, path.length() - 4) + "_baby.png";
            return ResourceLocation.withDefaultNamespace(newPath);
        }

        return original;
    }
}
