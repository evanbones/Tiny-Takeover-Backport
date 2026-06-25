package com.evandev.tiny_takeover_backport.config;

import com.evandev.tiny_takeover_backport.Constants;
import com.evandev.tiny_takeover_backport.platform.Services;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
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

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = Services.PLATFORM.getConfigDirectory().resolve("tiny_takeover_backport.json").toFile();
    private static ModConfig INSTANCE;

    @SerializedName("enabled")
    public boolean enabled = true;

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
        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.tiny_takeover_backport.title"))
                .save(ModConfig::save)
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.tiny_takeover_backport.category.general"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.tiny_takeover_backport.option.enabled"))
                                .binding(
                                        true,
                                        () -> get().enabled,
                                        newValue -> get().enabled = newValue
                                )
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .build())
                .build()
                .generateScreen(parent);
    }
}