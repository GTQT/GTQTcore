package keqing.gtqtcore.api.capability.impl;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.Recipe;

public class GTQTCoreLogic extends MultiblockRecipeLogic {

    public GTQTCoreLogic(RecipeMapMultiblockController tileEntity) {
        super(tileEntity);
    }
    public  RecipeMapMultiblockController getMetaTileEntity() {
        return (RecipeMapMultiblockController)super.getMetaTileEntity();
    }
    public void update() {

    }
    public boolean checkRecipe( Recipe recipe) {
        return false;
    }

}
