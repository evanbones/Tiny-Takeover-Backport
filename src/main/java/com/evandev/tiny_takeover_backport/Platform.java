package com.evandev.tiny_takeover_backport;

//? if fabric {
//import net.fabricmc.loader.api.FabricLoader;
//?} else if neoforge {
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
//?} else {
/*import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;
*///?}

import java.nio.file.Path;

public class Platform {
    public static boolean isModLoaded(String modId) {
        //? if fabric {
        //return FabricLoader.getInstance().isModLoaded(modId);
        //?} else
        return ModList.get().isLoaded(modId);
    }

    public static Path configDir() {
        //? if fabric {
        //return FabricLoader.getInstance().getConfigDir();
        //?} else
        return FMLPaths.CONFIGDIR.get();
    }
}
