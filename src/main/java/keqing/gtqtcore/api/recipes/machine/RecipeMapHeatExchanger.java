package keqing.gtqtcore.api.recipes.machine;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.RecipeProgressWidget;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import net.minecraftforge.items.IItemHandlerModifiable;

/**
 * Heat Exchanger Recipe Map
 *
 * @author Glodblock (original author), Magic_Sweepy
 *
 */
public class RecipeMapHeatExchanger<R extends RecipeBuilder<R>> extends RecipeMap<R> {

    public RecipeMapHeatExchanger( String unlocalizedName,
                                  int maxInputs,
                                  int maxOutputs,
                                  int maxFluidInputs,
                                  int maxFluidOutputs,
                                   R defaultRecipeBuilder,
                                  boolean isHidden) {
        super(unlocalizedName, maxInputs, maxOutputs, maxFluidInputs, maxFluidOutputs, defaultRecipeBuilder, isHidden);
    }

    @Override
    public ModularUI.Builder createJeiUITemplate(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems, FluidTankList importFluids, FluidTankList exportFluids, int yOffset) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 176)
                .widget(new RecipeProgressWidget(200, 30, 23 + yOffset - 12, 100, 80, progressBarTexture, moveType, this));
        this.addInventorySlotGroup(builder, importItems, importFluids, false, yOffset);
        this.addInventorySlotGroup(builder, exportItems, exportFluids, true, yOffset);
        return builder;
    }

    @Override
    protected void addInventorySlotGroup(ModularUI.Builder builder, IItemHandlerModifiable itemHandler, FluidTankList fluidHandler, boolean isOutputs, int yOffset) {
        int startInputsX = 78 - 4 * 18 + 12 - 40 - 3;
        int startInputsY = 37 - 2 * 18 + yOffset;

        if (!isOutputs) {
            addSlot(builder, startInputsX + 18 + 15, startInputsY + 13, 0, itemHandler, fluidHandler, true, false);
            addSlot(builder, startInputsX + 18 + 15, startInputsY + 44, 1, itemHandler, fluidHandler, true, false);
        } else {
            addSlot(builder, startInputsX + 18 + 140, startInputsY + 27, 0, itemHandler, fluidHandler, true, true);
            addSlot(builder, startInputsX + 18 + 158, startInputsY + 27, 1, itemHandler, fluidHandler, true, true);
            addSlot(builder, startInputsX + 18 + 127, startInputsY + 67, 2, itemHandler, fluidHandler, true, true);
        }
    }
}
