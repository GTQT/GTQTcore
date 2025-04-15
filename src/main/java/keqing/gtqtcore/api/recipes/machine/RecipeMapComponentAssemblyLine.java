package keqing.gtqtcore.api.recipes.machine;


import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ui.RecipeMapUIFunction;
import keqing.gtqtcore.api.gui.GTQTGuiTextures;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

public class RecipeMapComponentAssemblyLine<R extends RecipeBuilder<R>> extends RecipeMap<R> {
    public RecipeMapComponentAssemblyLine(@Nonnull String unlocalizedName, int maxInputs, int maxOutputs, int maxFluidInputs, int maxFluidOutputs, @Nonnull R defaultRecipeBuilder,
                                          RecipeMapUIFunction recipeMapUI) {
        super(unlocalizedName, defaultRecipeBuilder, recipeMapUI, maxInputs, maxOutputs, maxFluidInputs, maxFluidOutputs);
    }

}
