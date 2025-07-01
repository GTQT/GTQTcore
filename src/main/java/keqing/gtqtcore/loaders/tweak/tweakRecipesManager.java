package keqing.gtqtcore.loaders.tweak;

import keqing.gtqtcore.loaders.tweak.ae2.*;
import keqing.gtqtcore.loaders.tweak.oc.OCGTRecipes;
import keqing.gtqtcore.loaders.tweak.oc.OCMiscRecipes;
import keqing.gtqtcore.loaders.tweak.oc.OCTileRecipes;

public class tweakRecipesManager {
    public static void init() {
        ae2Recipes();
        ocRecipes();
    }
    public static void ae2Recipes() {
        AE2TileRecipes.init();
        AE2MiscRecipes.init();
        AE2CellRecipes.init();
    }
    public static void ocRecipes() {
        OCMiscRecipes.init();
        OCTileRecipes.init();
        OCGTRecipes.init();
    }

}
