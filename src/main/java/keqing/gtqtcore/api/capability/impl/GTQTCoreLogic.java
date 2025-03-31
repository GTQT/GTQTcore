package keqing.gtqtcore.api.capability.impl;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.Recipe;

public class GTQTCoreLogic extends MultiblockRecipeLogic {

    public GTQTCoreLogic(RecipeMapMultiblockController tileEntity) {
        super(tileEntity);
    }
    @Override
    public  RecipeMapMultiblockController getMetaTileEntity() {
        return (RecipeMapMultiblockController)super.getMetaTileEntity();
    }
    @Override
    public void update() {

    }
    @Override
    public void updateWorkable() {

    }

    @Override
    public boolean checkRecipe( Recipe recipe) {
        return false;
    }
    @Override
    public void setActive(boolean active) {
        super.setActive(active);
    }
}
