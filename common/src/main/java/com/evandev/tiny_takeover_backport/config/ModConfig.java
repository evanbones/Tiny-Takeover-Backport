package com.evandev.tiny_takeover_backport.config;

import com.evandev.tiny_takeover_backport.Constants;
import com.evandev.tiny_takeover_backport.platform.Services;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = Services.PLATFORM.getConfigDirectory().resolve("tiny_takeover_backport.json").toFile();
    private static ModConfig INSTANCE;

    public boolean enableNameTagRecipe = true;
    public boolean spawnBabyDolphin = true;
    public boolean spawnBabySquid = true;

    public boolean enableArmadillo = true;
    public boolean enableAxolotl = true;
    public boolean enableBee = true;
    public boolean enableCamel = true;
    public boolean enableCat = true;
    public boolean enableChicken = true;
    public boolean enableCow = true;
    public boolean enableDolphin = true;
    public boolean enableDonkey = true;
    public boolean enableDrowned = true;
    public boolean enableFox = true;
    public boolean enableGoat = true;
    public boolean enableHoglin = true;
    public boolean enableHorse = true;
    public boolean enableHusk = true;
    public boolean enableLlama = true;
    public boolean enableMule = true;
    public boolean enableOcelot = true;
    public boolean enablePanda = true;
    public boolean enablePig = true;
    public boolean enablePiglin = true;
    public boolean enablePolarBear = true;
    public boolean enableRabbit = true;
    public boolean enableSheep = true;
    public boolean enableSniffer = true;
    public boolean enableSquid = true;
    public boolean enableStrider = true;
    public boolean enableTurtle = true;
    public boolean enableVillager = true;
    public boolean enableWolf = true;
    public boolean enableZombie = true;
    public boolean enableZombieVillager = true;
    public boolean enableZombifiedPiglin = true;

    public boolean replaceAdultRabbit = true;
    public boolean rabbitBoundingBox = true;

    public static ModConfig get() {
        if (INSTANCE == null) {
            load();
        }
        return INSTANCE;
    }

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                INSTANCE = GSON.fromJson(reader, ModConfig.class);
            } catch (Exception e) {
                Constants.LOG.error("Failed to load tiny_takeover_backport.json", e);
                INSTANCE = new ModConfig();
                save();
            }
        } else {
            INSTANCE = new ModConfig();
            save();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(INSTANCE, writer);
        } catch (IOException e) {
            Constants.LOG.error("Failed to save tiny_takeover_backport.json", e);
        }
    }

    public static Screen createScreen(Screen parent) {
        YetAnotherConfigLib.Builder builder = YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.tiny_takeover_backport.title"))
                .save(ModConfig::save);

        ConfigCategory.Builder general = ConfigCategory.createBuilder()
                .name(Component.translatable("config.tiny_takeover_backport.category.general"))
                .option(createBoolOption("enable_name_tag_recipe", true, () -> get().enableNameTagRecipe, val -> get().enableNameTagRecipe = val))
                .option(createBoolOption("spawn_baby_dolphin", true, () -> get().spawnBabyDolphin, val -> get().spawnBabyDolphin = val))
                .option(createBoolOption("spawn_baby_squid", true, () -> get().spawnBabySquid, val -> get().spawnBabySquid = val));

        ConfigCategory.Builder models = ConfigCategory.createBuilder()
                .name(Component.translatable("config.tiny_takeover_backport.category.models"))
                .option(createBoolOption("replace_adult_rabbit", true, () -> get().replaceAdultRabbit, val -> get().replaceAdultRabbit = val))
                .option(createBoolOption("rabbit_bounding_box", true, () -> get().rabbitBoundingBox, val -> get().rabbitBoundingBox = val))
                .option(createBoolOption("enable_armadillo", true, () -> get().enableArmadillo, val -> get().enableArmadillo = val))
                .option(createBoolOption("enable_axolotl", true, () -> get().enableAxolotl, val -> get().enableAxolotl = val))
                .option(createBoolOption("enable_bee", true, () -> get().enableBee, val -> get().enableBee = val))
                .option(createBoolOption("enable_camel", true, () -> get().enableCamel, val -> get().enableCamel = val))
                .option(createBoolOption("enable_cat", true, () -> get().enableCat, val -> get().enableCat = val))
                .option(createBoolOption("enable_chicken", true, () -> get().enableChicken, val -> get().enableChicken = val))
                .option(createBoolOption("enable_cow", true, () -> get().enableCow, val -> get().enableCow = val))
                .option(createBoolOption("enable_dolphin", true, () -> get().enableDolphin, val -> get().enableDolphin = val))
                .option(createBoolOption("enable_donkey", true, () -> get().enableDonkey, val -> get().enableDonkey = val))
                .option(createBoolOption("enable_drowned", true, () -> get().enableDrowned, val -> get().enableDrowned = val))
                .option(createBoolOption("enable_fox", true, () -> get().enableFox, val -> get().enableFox = val))
                .option(createBoolOption("enable_goat", true, () -> get().enableGoat, val -> get().enableGoat = val))
                .option(createBoolOption("enable_hoglin", true, () -> get().enableHoglin, val -> get().enableHoglin = val))
                .option(createBoolOption("enable_horse", true, () -> get().enableHorse, val -> get().enableHorse = val))
                .option(createBoolOption("enable_husk", true, () -> get().enableHusk, val -> get().enableHusk = val))
                .option(createBoolOption("enable_llama", true, () -> get().enableLlama, val -> get().enableLlama = val))
                .option(createBoolOption("enable_mule", true, () -> get().enableMule, val -> get().enableMule = val))
                .option(createBoolOption("enable_ocelot", true, () -> get().enableOcelot, val -> get().enableOcelot = val))
                .option(createBoolOption("enable_panda", true, () -> get().enablePanda, val -> get().enablePanda = val))
                .option(createBoolOption("enable_pig", true, () -> get().enablePig, val -> get().enablePig = val))
                .option(createBoolOption("enable_piglin", true, () -> get().enablePiglin, val -> get().enablePiglin = val))
                .option(createBoolOption("enable_polar_bear", true, () -> get().enablePolarBear, val -> get().enablePolarBear = val))
                .option(createBoolOption("enable_rabbit", true, () -> get().enableRabbit, val -> get().enableRabbit = val))
                .option(createBoolOption("enable_sheep", true, () -> get().enableSheep, val -> get().enableSheep = val))
                .option(createBoolOption("enable_sniffer", true, () -> get().enableSniffer, val -> get().enableSniffer = val))
                .option(createBoolOption("enable_squid", true, () -> get().enableSquid, val -> get().enableSquid = val))
                .option(createBoolOption("enable_strider", true, () -> get().enableStrider, val -> get().enableStrider = val))
                .option(createBoolOption("enable_turtle", true, () -> get().enableTurtle, val -> get().enableTurtle = val))
                .option(createBoolOption("enable_villager", true, () -> get().enableVillager, val -> get().enableVillager = val))
                .option(createBoolOption("enable_wolf", true, () -> get().enableWolf, val -> get().enableWolf = val))
                .option(createBoolOption("enable_zombie", true, () -> get().enableZombie, val -> get().enableZombie = val))
                .option(createBoolOption("enable_zombie_villager", true, () -> get().enableZombieVillager, val -> get().enableZombieVillager = val))
                .option(createBoolOption("enable_zombified_piglin", true, () -> get().enableZombifiedPiglin, val -> get().enableZombifiedPiglin = val));

        return builder.category(general.build()).category(models.build()).build().generateScreen(parent);
    }

    private static Option<Boolean> createBoolOption(String name, boolean defaultValue, Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return Option.<Boolean>createBuilder()
                .name(Component.translatable("config.tiny_takeover_backport.option." + name))
                .binding(defaultValue, getter, setter)
                .controller(TickBoxControllerBuilder::create)
                .build();
    }

    public boolean isModelEnabled(String name) {
        return switch (name) {
            case "armadillo" -> enableArmadillo;
            case "axolotl" -> enableAxolotl;
            case "bee" -> enableBee;
            case "camel" -> enableCamel;
            case "cat" -> enableCat;
            case "chicken" -> enableChicken;
            case "cow", "mooshroom" -> enableCow;
            case "dolphin" -> enableDolphin;
            case "donkey" -> enableDonkey;
            case "drowned" -> enableDrowned;
            case "fox" -> enableFox;
            case "goat" -> enableGoat;
            case "hoglin" -> enableHoglin;
            case "horse" -> enableHorse;
            case "husk" -> enableHusk;
            case "llama", "trader_llama" -> enableLlama;
            case "mule" -> enableMule;
            case "ocelot" -> enableOcelot;
            case "panda" -> enablePanda;
            case "pig" -> enablePig;
            case "piglin" -> enablePiglin;
            case "polar_bear" -> enablePolarBear;
            case "rabbit" -> enableRabbit;
            case "sheep" -> enableSheep;
            case "sniffer" -> enableSniffer;
            case "squid", "glow_squid" -> enableSquid;
            case "strider" -> enableStrider;
            case "turtle" -> enableTurtle;
            case "villager" -> enableVillager;
            case "wolf" -> enableWolf;
            case "zombie" -> enableZombie;
            case "zombie_villager" -> enableZombieVillager;
            case "zombified_piglin" -> enableZombifiedPiglin;
            default -> true;
        };
    }
}