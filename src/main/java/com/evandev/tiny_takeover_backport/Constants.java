package com.evandev.tiny_takeover_backport;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
    public static final String MOD_ID = "tiny_takeover_backport";
    public static final String MOD_NAME = "Tiny Takeover Backport";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static ResourceLocation location(String namespace, String path) {
        //? if >=1.21 {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
        //?} else
        //return new ResourceLocation(namespace, path);
    }

    public static ResourceLocation vanillaLocation(String path) {
        return location("minecraft", path);
    }
}
