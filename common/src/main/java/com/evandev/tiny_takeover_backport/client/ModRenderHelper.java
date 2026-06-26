package com.evandev.tiny_takeover_backport.client;

public class ModRenderHelper {
    public static final ThreadLocal<Boolean> SUPPRESS_AGE_SCALE = ThreadLocal.withInitial(() -> false);
}
