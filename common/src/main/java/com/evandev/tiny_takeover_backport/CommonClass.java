package com.evandev.tiny_takeover_backport;

import com.evandev.tiny_takeover_backport.config.ModConfig;
import com.evandev.tiny_takeover_backport.registry.ModRegistry;

public class CommonClass {

    public static void init() {
        ModConfig.load();
        ModRegistry.init();
    }
}