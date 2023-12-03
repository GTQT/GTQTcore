package keqing.gtqtcore.api.recipes.machine;


import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import keqing.gtqtcore.api.gui.GTQTGuiTextures;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

public class RecipeMapComponentAssemblyLine<R extends RecipeBuilder<R>> extends RecipeMap<R> {
    public RecipeMapComponentAssemblyLine(@Nonnull String unlocalizedName, int maxInputs, int maxOutputs, int maxFluidInputs, int maxFluidOutputs, @Nonnull R defaultRecipeBuilder, boolean isHidden) {
        super(unlocalizedName, maxInputs, maxOutputs, maxFluidInputs, maxFluidOutputs, defaultRecipeBuilder, isHidden);
    }

    @Override
    public ModularUI.Builder createJeiUITemplate(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems, FluidTankList importFluids, FluidTankList exportFluids, int yOffset) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 176)
                .widget(new ProgressWidget(200, 70, 12, 72, 40, GTQTGuiTextures.PROGRESS_BAR_COMPONENT_ASSEMBLY_LINE_1, ProgressWidget.MoveType.HORIZONTAL))
                .widget(new ProgressWidget(200, 131, 15, 3, 12, GTQTGuiTextures.PROGRESS_BAR_COMPONENT_ASSEMBLY_LINE_2, ProgressWidget.MoveType.VERTICAL));
        this.addInventorySlotGroup(builder, importItems, importFluids, false, yOffset);
        this.addInventorySlotGroup(builder, exportItems, exportFluids, true, yOffset);
        return builder;
    }

    @Override
    protected void addInventorySlotGroup(ModularUI.Builder builder, IItemHandlerModifiable itemHandler, FluidTankList fluidHandler, boolean isOutputs, int yOffset) {
        int startInputsX = 70 - 3 * 18;
        int startInputsY = 45 - 2 * 18;

        if (!isOutputs) {
            // item input slots
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    int slotIndex = i * 3 + j;
                    addSlot(builder, startInputsX + 18 * j, startInputsY + 18 * i, slotIndex, itemHandler, fluidHandler, false, false);
                }
            }

            // fluid slots
            int startFluidX = startInputsX + 18 * 4;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    int slotIndex = i * 4 + j;
                    addSlot(builder, startFluidX + 18 * j, startInputsY + 18 + 18 * i, slotIndex, itemHandler, fluidHandler, true, false);
                }
            }
        } else {
            // output slot
            addSlot(builder, startInputsX + 18 * 7, 9, 0, itemHandler, fluidHandler, false, true);
        }
    }
}
