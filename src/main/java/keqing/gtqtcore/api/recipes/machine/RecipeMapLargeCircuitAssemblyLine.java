package keqing.gtqtcore.api.recipes.machine;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.RecipeProgressWidget;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import net.minecraftforge.items.IItemHandlerModifiable;
/**
 * Large Circuit Assembly Line Recipe Map
 *
 * @author Magic_Sweepy
 */
public class RecipeMapLargeCircuitAssemblyLine<R extends RecipeBuilder<R>> extends RecipeMap<R> {

    public RecipeMapLargeCircuitAssemblyLine( String unlocalizedName,
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
        int newYOffset = yOffset + 18;
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 166)
                .widget(new RecipeProgressWidget(200, 90 + 3 + 16 , 23 + newYOffset - 14, 20, 20, progressBarTexture, moveType, this));
        this.addInventorySlotGroup(builder, importItems, importFluids, false, newYOffset);
        this.addInventorySlotGroup(builder, exportItems, exportFluids, true, newYOffset);
        return builder;
    }

    //  TODO Maybe we can rewrite this more clean...
    @Override
    protected void addInventorySlotGroup(ModularUI.Builder builder,
                                          IItemHandlerModifiable itemHandler,
                                          FluidTankList fluidHandler,
                                         boolean isOutputs,
                                         int yOffset) {
        //int startInputsX = 70 - 3 * 18;
        int startInputsX = 70 - 2 * 18;
        int startInputsY = 37 - 2 * 18;

        if (!isOutputs) {
            // item input slots
            addSlot(builder, startInputsX - 9, yOffset + 10, 6, itemHandler, fluidHandler, false, false);

            addSlot(builder, startInputsX + 18, startInputsY + 18, 0, itemHandler, fluidHandler, false, false);
            addSlot(builder, startInputsX + 18 * 2, startInputsY + 18, 1, itemHandler, fluidHandler, false, false);
            addSlot(builder, startInputsX + 18 * 3, startInputsY + 18, 2, itemHandler, fluidHandler, false, false);
            addSlot(builder, startInputsX + 18, startInputsY + 18 * 2, 3, itemHandler, fluidHandler, false, false);
            addSlot(builder, startInputsX + 18 * 2, startInputsY + 18 * 2, 4, itemHandler, fluidHandler, false, false);
            addSlot(builder, startInputsX + 18 * 3, startInputsY + 18 * 2, 5, itemHandler, fluidHandler, false, false);

            //  fluid input slots
            addSlot(builder, startInputsX + 18 * 3, startInputsY + 18 * 3, 0, itemHandler, fluidHandler, true, false);
        } else {
            // output slot
            // addSlot(builder, 90 + 30, yOffset + 10, 0, itemHandler, fluidHandler, false, true);
            addSlot(builder, 90 + 30 + 14, yOffset + 10, 0, itemHandler, fluidHandler, false, true);
        }
    }
}
