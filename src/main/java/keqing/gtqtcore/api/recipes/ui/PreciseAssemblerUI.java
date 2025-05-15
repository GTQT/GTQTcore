package keqing.gtqtcore.api.recipes.ui;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ui.RecipeMapUI;
import net.minecraftforge.items.IItemHandlerModifiable;

import static gregtech.api.gui.widgets.ProgressWidget.MoveType.HORIZONTAL;

public class PreciseAssemblerUI<R extends RecipeMap<?>> extends RecipeMapUI<R> {
    public PreciseAssemblerUI(R recipeMap) {
        super(recipeMap, false, false, false, false, false);
    }

    @Override
    public ModularUI.Builder createJeiUITemplate(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems, FluidTankList importFluids, FluidTankList exportFluids, int yOffset) {
        int newYOffset = yOffset + 18;
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 176)
                .widget(new ProgressWidget(200, 90 + 7, 23 + newYOffset - 6, 22, 22, GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, HORIZONTAL));
        this.addInventorySlotGroup(builder, importItems, importFluids, false, newYOffset);
        this.addInventorySlotGroup(builder, exportItems, exportFluids, true, newYOffset);
        return builder;
    }

    @Override
    protected void addInventorySlotGroup(ModularUI.Builder builder, IItemHandlerModifiable itemHandler, FluidTankList fluidHandler, boolean isOutputs, int yOffset) {
        int startInputsX = 78 - 4 * 18 + 12;
        int startInputsY = 37 - 2 * 18 + yOffset;
        if (!isOutputs) {
            for (int i = 0; i < 4; i++) {
                addSlot(builder, startInputsX + 18 * i, startInputsY + 6, i, itemHandler, fluidHandler, false, false);
                addSlot(builder, startInputsX + 18 * i, startInputsY + 18 * 2 - 6, i, itemHandler, fluidHandler, true, false);
            }
        } else {
            addSlot(builder, 78 + 40 + 7, startInputsY + 18, 0, itemHandler, fluidHandler, false, true);
        }
    }
}
