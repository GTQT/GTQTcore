package keqing.gtqtcore.api.capability.impl;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.Recipe;

public class GTQTCoreLogic extends MultiblockRecipeLogic {

    public GTQTCoreLogic(RecipeMapMultiblockController tileEntity) {
        super(tileEntity);
    }
    protected boolean isActive=true;

    public boolean isWorkingEnabled() {
        return true;
    }

    public boolean isActive() {
        return true;
    }

    public boolean isWorking() {
        return true;
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
