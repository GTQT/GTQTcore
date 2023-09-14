package keqing.gtqtcore.loaders.recipes;

import gregtech.api.event.MaterialInfoEvent;
import gregtech.loaders.recipe.CraftingRecipeLoader;
import gregtech.loaders.recipe.MachineRecipeLoader;
import gregtech.loaders.recipe.MetaTileEntityMachineRecipeLoader;
import gregtech.loaders.recipe.RecyclingRecipes;
import gregtech.loaders.recipe.handlers.DecompositionRecipeHandler;
import gregtech.loaders.recipe.handlers.RecipeHandlerList;
import gregtech.loaders.recipe.handlers.ToolRecipeHandler;
import net.minecraftforge.common.MinecraftForge;

public class GTQTRecipesManager {
    private void GTQTRecipeManager() {/**/}

    public static void preLoad() {
        ToolRecipeHandler.initializeMetaItems();
    }

    public static void load() {
        MachineRecipeLoader.init();
        CraftingRecipeLoader.init();
       // MetaTileEntityLoader.init();
        MetaTileEntityMachineRecipeLoader.init();
        RecipeHandlerList.register();
    }

    public static void loadLatest() {
        MinecraftForge.EVENT_BUS.post(new MaterialInfoEvent());
        DecompositionRecipeHandler.runRecipeGeneration();
        RecyclingRecipes.init();
    }
}
