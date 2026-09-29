package com.evandev.tiny_takeover_backport.client;

//? if forgelike {
import com.evandev.tiny_takeover_backport.config.ModConfig;
//? if neoforge {
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
//?} else {
/*import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModContainer;
*///?}

public class ClientConfigSetup {
    public static void register(ModContainer container) {
        //? if neoforge {
        container.registerExtensionPoint(IConfigScreenFactory.class, (c, parent) -> ModConfig.createScreen(parent));
        //?} else {
        /*container.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((mc, parent) -> ModConfig.createScreen(parent)));
        *///?}
    }
}
//?}
