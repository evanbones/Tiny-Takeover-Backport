package com.evandev.tiny_takeover_backport.client;

import com.evandev.tiny_takeover_backport.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class ClientConfigSetup {
    public static void register(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, (c, parent) -> ModConfig.createScreen(parent));
    }
}