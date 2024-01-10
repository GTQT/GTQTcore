package keqing.gtqtcore.loaders.recipes;

import keqing.gtqtcore.loaders.recipes.chain.*;
import keqing.gtqtcore.loaders.recipes.handlers.*;

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
        CPULine.init();
        AllouKlin.init();
        DecompositionRecipeHandler.runRecipeGeneration();
        TESEDecompositionRecipeHandler.runRecipeGeneration();
        CircuitryMaterialChains.init();
        MetaTileEntityLoader.init();
        MetaTileEntityMachine.init();
        OilChains.init();
        EFusionLoader.init();
        PCBline.init();
    }
}
