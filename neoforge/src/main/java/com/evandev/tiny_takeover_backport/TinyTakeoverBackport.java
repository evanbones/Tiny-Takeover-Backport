package com.evandev.tiny_takeover_backport;

import com.evandev.tiny_takeover_backport.client.ClientConfigSetup;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(Constants.MOD_ID)
public class TinyTakeoverBackport {
    public TinyTakeoverBackport(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        if (FMLEnvironment.dist.isClient()) {
            ClientConfigSetup.register(modContainer);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        CommonClass.init();
    }
}