package com.evandev.tiny_takeover_backport;

import com.evandev.tiny_takeover_backport.config.ModConfig;

public class CommonClass {

    public static void init() {
        ModConfig.load();
    }
}