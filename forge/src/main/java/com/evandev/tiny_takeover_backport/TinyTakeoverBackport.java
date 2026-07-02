package com.evandev.tiny_takeover_backport;

import com.evandev.tiny_takeover_backport.client.ClientConfigSetup;
import com.evandev.tiny_takeover_backport.client.ForgeClientEvents;
import com.evandev.tiny_takeover_backport.registry.ModRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
public class TinyTakeoverBackport {

    public TinyTakeoverBackport() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModContainer modContainer = ModLoadingContext.get().getActiveContainer();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onRegister);
        modEventBus.addListener(this::addCreative);

        if (FMLEnvironment.dist.isClient()) {
            ClientConfigSetup.register(modContainer);
            ForgeClientEvents.init(modEventBus);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        CraftingHelper.register(NameTagRecipeEnabledCondition.SERIALIZER);
        CommonClass.init();
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(
                    new ResourceLocation("minecraft", "golden_dandelion"),
                    () -> ModRegistry.POTTED_GOLDEN_DANDELION
            );
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModRegistry.GOLDEN_DANDELION_ITEM);
        }
    }

    private void onRegister(RegisterEvent event) {
        if (event.getRegistryKey().equals(Registries.BLOCK)) {
            ModRegistry.BLOCKS.forEach((id, block) -> event.register(Registries.BLOCK, id, () -> block));
        } else if (event.getRegistryKey().equals(Registries.ITEM)) {
            ModRegistry.ITEMS.forEach((id, item) -> event.register(Registries.ITEM, id, () -> item));
        } else if (event.getRegistryKey().equals(Registries.SOUND_EVENT)) {
            ModRegistry.SOUND_EVENTS.forEach((id, sound) -> event.register(Registries.SOUND_EVENT, id, () -> sound));
        } else if (event.getRegistryKey().equals(Registries.PARTICLE_TYPE)) {
            ModRegistry.PARTICLES.forEach((id, particle) -> event.register(Registries.PARTICLE_TYPE, id, () -> particle));
        }
    }
}