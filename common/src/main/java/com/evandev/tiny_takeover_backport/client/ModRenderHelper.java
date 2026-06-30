package com.evandev.tiny_takeover_backport.client;

import net.minecraft.client.model.EntityModel;

public class ModRenderHelper {
    public static final ThreadLocal<Boolean> SUPPRESS_AGE_SCALE = ThreadLocal.withInitial(() -> false);
    public static EntityModel<?> babyWoolModel;
}
