package com.evandev.tiny_takeover_backport;

import net.fabricmc.api.ModInitializer;

public class TinyTakeoverBackport implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonClass.init();
    }

}