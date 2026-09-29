package com.evandev.tiny_takeover_backport.client;

//? if forgelike {
import com.evandev.tiny_takeover_backport.client.particle.SimpleVerticalParticle;
import com.evandev.tiny_takeover_backport.registry.ModRegistry;
//? if neoforge {
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
//?} else {
/*import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
*///?}

public class ForgeClientEvents {

    public static void init(IEventBus modEventBus) {
        modEventBus.addListener(ForgeClientEvents::onRegisterLayerDefinitions);
        modEventBus.addListener(ForgeClientEvents::onRegisterParticleProviders);
    }

    private static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        ModClientRegistry.init();
        ModClientRegistry.LAYER_DEFINITIONS.forEach(event::registerLayerDefinition);
    }

    private static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModRegistry.PAUSE_MOB_GROWTH, SimpleVerticalParticle.PauseMobGrowthProvider::new);
        event.registerSpriteSet(ModRegistry.RESET_MOB_GROWTH, SimpleVerticalParticle.ResetMobGrowthProvider::new);
    }
}
//?}
