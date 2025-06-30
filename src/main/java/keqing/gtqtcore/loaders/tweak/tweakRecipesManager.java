package keqing.gtqtcore.loaders.tweak;

import keqing.gtqtcore.loaders.tweak.ae2.*;

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
    }

}
