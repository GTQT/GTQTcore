package keqing.gtqtcore.loaders.recipes;

import keqing.gtqtcore.loaders.recipes.chain.*;
import keqing.gtqtcore.loaders.recipes.handlers.DecompositionRecipeHandler;
import keqing.gtqtcore.loaders.recipes.handlers.ELE;
import keqing.gtqtcore.loaders.recipes.handlers.ISA;

public class GTQTRecipesManager {
    private GTQTRecipesManager() {

    }
    public static void load() {
    }
    public static void init() {
        BiochemChains.init();
        BiologyRecipe.init();
        PhotoresistChains.init();
        PetrochemRecipes.init();
        MaterialOreChains.init();
        LubricantChains.init();
        PEEKChain.init();
        ELE.init();
        DecompositionRecipeHandler.runRecipeGeneration();
        CircuitryMaterialChains.init();
        MetaTileEntityLoader.init();
        MetaTileEntityMachine.init();
    }
}
