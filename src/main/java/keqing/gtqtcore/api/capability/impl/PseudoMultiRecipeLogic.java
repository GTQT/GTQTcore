package keqing.gtqtcore.api.capability.impl;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.RecipeLogicEnergy;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import keqing.gtqtcore.api.metaileentity.PseudoMultiMachineMetaTileEntity;
import keqing.gtqtcore.api.recipes.properties.PseudoMultiProperty;


import java.util.function.Supplier;

public class PseudoMultiRecipeLogic extends RecipeLogicEnergy {

    private final PseudoMultiMachineMetaTileEntity pmMTE;

    public PseudoMultiRecipeLogic(PseudoMultiMachineMetaTileEntity tileEntity, RecipeMap recipeMap, Supplier<IEnergyContainer> energyContainer) {
        super(tileEntity, recipeMap, energyContainer);
        pmMTE = tileEntity;
    }

    @Override
    public boolean checkRecipe( Recipe recipe) {
        if (this.pmMTE.getTargetBlockState() == null ) return false; //if world was remote or null
        return !recipe.hasProperty(PseudoMultiProperty.getInstance()) || recipe.getProperty(PseudoMultiProperty.getInstance(), null)
                .getValidBlockStates().contains(this.pmMTE.getTargetBlockState()) && super.checkRecipe(recipe);
    }

    @Override
    public boolean canProgressRecipe() {
        return this.previousRecipe == null || !previousRecipe.hasProperty(PseudoMultiProperty.getInstance()) ||
                this.previousRecipe.getProperty(PseudoMultiProperty.getInstance(), null).getValidBlockStates()
                        .contains(this.pmMTE.getTargetBlockState()) && super.canProgressRecipe();
    }
}