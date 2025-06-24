package keqing.gtqtcore.api.capability.impl;

import gregtech.api.capability.impl.RecipeLogicSteam;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import keqing.gtqtcore.api.metatileentity.PseudoMultiSteamMachineMetaTileEntity;
import keqing.gtqtcore.api.recipes.properties.PseudoMultiProperty;
import net.minecraftforge.fluids.IFluidTank;


public class PseudoMultiSteamRecipeLogic extends RecipeLogicSteam {
    private final PseudoMultiSteamMachineMetaTileEntity pmsMTE;

    public PseudoMultiSteamRecipeLogic(PseudoMultiSteamMachineMetaTileEntity tileEntity, RecipeMap recipeMap, boolean isHighPressure, IFluidTank steamFluidTank, double conversionRate) {
        super(tileEntity, recipeMap, isHighPressure, steamFluidTank, conversionRate);
        pmsMTE = tileEntity;
    }

    @Override
    public boolean checkRecipe( Recipe recipe) {
        if (this.pmsMTE.getTargetBlockState() == null) return false; //if world was remote or null
        //if no property was given don't check if state matches
        return !recipe.hasProperty(PseudoMultiProperty.getInstance()) || recipe.getProperty(PseudoMultiProperty.getInstance(), null)
                .getValidBlockStates().contains(this.pmsMTE.getTargetBlockState()) && super.checkRecipe(recipe);
    }

    @Override
    public boolean canProgressRecipe() {
        //recipe stalled due to valid block removal will complete on world reload
        return this.previousRecipe == null || !this.previousRecipe.hasProperty(PseudoMultiProperty.getInstance()) ||
                this.previousRecipe.getProperty(PseudoMultiProperty.getInstance(), null).getValidBlockStates()
                        .contains(this.pmsMTE.getTargetBlockState()) && super.canProgressRecipe();
    }
}
