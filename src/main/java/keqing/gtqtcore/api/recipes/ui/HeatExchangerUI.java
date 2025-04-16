package keqing.gtqtcore.api.recipes.ui;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.gui.widgets.RecipeProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ui.RecipeMapUI;
import keqing.gtqtcore.api.gui.GTQTGuiTextures;
import net.minecraftforge.items.IItemHandlerModifiable;

import static gregtech.api.gui.widgets.ProgressWidget.MoveType.HORIZONTAL;

public class HeatExchangerUI<R extends RecipeMap<?>> extends RecipeMapUI<R> {
    public HeatExchangerUI(R recipeMap) {
        super(recipeMap, false, false, false, false, false);
    }
    @Override
    public ModularUI.Builder createJeiUITemplate(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems, FluidTankList importFluids, FluidTankList exportFluids, int yOffset) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 176)
                .widget(new ProgressWidget(200, 30, 23 + yOffset - 12, 100, 80, GTQTGuiTextures.PROGRESS_BAR_HEAT_EXCHANGE, HORIZONTAL));
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
