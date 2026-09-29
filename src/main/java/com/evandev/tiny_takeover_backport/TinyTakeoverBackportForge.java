package com.evandev.tiny_takeover_backport;

//? if forgelike {
import com.evandev.tiny_takeover_backport.client.ClientConfigSetup;
import com.evandev.tiny_takeover_backport.client.ForgeClientEvents;
import com.evandev.tiny_takeover_backport.config.NameTagRecipeEnabledCondition;
import com.evandev.tiny_takeover_backport.registry.ModRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
//? if neoforge {
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;
//?} else {
/*import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegisterEvent;
*///?}

@Mod(Constants.MOD_ID)
public class TinyTakeoverBackportForge {

    //? if neoforge {
    public TinyTakeoverBackportForge(IEventBus modEventBus, ModContainer modContainer) {
    //?} else {
    /*public TinyTakeoverBackportForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModContainer modContainer = ModLoadingContext.get().getActiveContainer();
    *///?}
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onRegister);
        modEventBus.addListener(this::addCreative);

        if (FMLEnvironment.dist.isClient()) {
            ClientConfigSetup.register(modContainer);
            ForgeClientEvents.init(modEventBus);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        //? if forge
        //CraftingHelper.register(NameTagRecipeEnabledCondition.SERIALIZER);
        CommonClass.init();
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(
                    Constants.vanillaLocation("golden_dandelion"),
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
        //? if neoforge {
        else if (event.getRegistryKey().equals(NeoForgeRegistries.Keys.CONDITION_CODECS)) {
            event.register(NeoForgeRegistries.Keys.CONDITION_CODECS, Constants.location(Constants.MOD_ID, "nametag_recipe_enabled"), () -> NameTagRecipeEnabledCondition.CODEC);
        }
        //?}
    }
}
//?}
