package com.evandev.tiny_takeover_backport.client;

import com.evandev.tiny_takeover_backport.config.ModConfig;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModContainer;

public class ClientConfigSetup {
    public static void register(ModContainer container) {
        container.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((mc, parent) -> ModConfig.createScreen(parent)));
    }
}