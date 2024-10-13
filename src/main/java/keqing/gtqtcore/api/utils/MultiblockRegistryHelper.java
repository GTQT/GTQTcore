package keqing.gtqtcore.api.utils;

import gregtech.api.recipes.RecipeMap;
import gregtech.client.renderer.ICubeRenderer;
import keqing.gtqtcore.api.metaileentity.SimpleSteamMetaTileEntity;
import keqing.gtqtcore.api.metaileentity.SteamProgressIndicator;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;
import static keqing.gtqtcore.api.GTQTValue.gtqtcoreId;

public class MultiblockRegistryHelper {
    public static void registerSimpleSteamMetaTileEntity(SimpleSteamMetaTileEntity[] machines,
                                                         int startId,
                                                         String name,
                                                         RecipeMap<?> recipeMap,
                                                         SteamProgressIndicator progressIndicator,
                                                         ICubeRenderer texture,
                                                         boolean isBricked) {
        machines[0] = registerMetaTileEntity(startId, new SimpleSteamMetaTileEntity(gtqtcoreId(String.format("%s.bronze", name)), recipeMap, progressIndicator, texture, isBricked, false));
        machines[1] = registerMetaTileEntity(startId + 1, new SimpleSteamMetaTileEntity(gtqtcoreId(String.format("%s.steel", name)), recipeMap, progressIndicator, texture, isBricked, true));
    }
}
