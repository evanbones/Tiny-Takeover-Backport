package com.evandev.tiny_takeover_backport.compat;

import com.evandev.tiny_takeover_backport.Platform;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.lang.reflect.Method;

// this really shouldn't use reflection but I don't trust ML to not break my mod sooo
public class MixedLitterCompat {
    private static boolean initialized;
    private static Method resolveTexture;
    private static Method remodelActive;
    private static Class<?> remodelMarker;

    private static void init() {
        if (initialized) return;
        initialized = true;
        if (!Platform.isModLoaded("mixed_litter")) return;
        try {
            resolveTexture = Class.forName("dev.tazer.mixed_litter.VariantUtil")
                    .getMethod("resolveTexture", Entity.class, ResourceLocation.class, boolean.class);
            remodelActive = Class.forName("dev.tazer.mixed_litter.RemodelRegistry")
                    .getMethod("remodelActive", EntityType.class);
            remodelMarker = Class.forName("dev.tazer.mixed_litter.client.renderers.RemodelMarker");
        } catch (Throwable t) {
            resolveTexture = null;
            remodelActive = null;
            remodelMarker = null;
        }
    }

    public static ResourceLocation resolveTexture(Entity entity, ResourceLocation texture) {
        init();
        if (resolveTexture == null) return texture;
        try {
            boolean remodel = (boolean) remodelActive.invoke(null, entity.getType());
            ResourceLocation resolved = (ResourceLocation) resolveTexture.invoke(null, entity, texture, remodel);
            return resolved != null ? resolved : texture;
        } catch (Throwable t) {
            return texture;
        }
    }

    public static boolean isRemodelRenderer(LivingEntityRenderer<?, ?> renderer) {
        init();
        return remodelMarker != null && remodelMarker.isInstance(renderer);
    }
}
