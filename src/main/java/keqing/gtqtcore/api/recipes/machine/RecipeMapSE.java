package keqing.gtqtcore.api.recipes.machine;


import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.gui.widgets.RecipeProgressWidget;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import keqing.gtqtcore.api.gui.GTQTGuiTextures;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

public class RecipeMapSE<R extends RecipeBuilder<R>> extends RecipeMap<R> {
    public RecipeMapSE(@Nonnull String unlocalizedName, int maxInputs, int maxOutputs, int maxFluidInputs, int maxFluidOutputs, @Nonnull R defaultRecipeBuilder, boolean isHidden) {
        super(unlocalizedName, maxInputs, maxOutputs, maxFluidInputs, maxFluidOutputs, defaultRecipeBuilder, isHidden);
    }

    @Override
    public ModularUI.Builder createJeiUITemplate(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems, FluidTankList importFluids, FluidTankList exportFluids, int yOffset) {
        int newYOffset = yOffset + 18;
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 176)
                .widget(new RecipeProgressWidget(200, 60 , 23 + newYOffset - 6, 20, 20, progressBarTexture, moveType, this));
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
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    int slotIndex = i * 2 + j;
                    addSlot(builder, startInputsX + 18 * j, startInputsY + 18 * i, slotIndex, itemHandler, fluidHandler, false, false);
                }
            }


                for (int j = 0; j < 2; j++) {
                    int slotIndex = j;
                    addSlot(builder, startInputsX + 18 * j, startInputsY + 18 * 2+9, slotIndex, itemHandler, fluidHandler, true, false);
                }


        } else {
            // output slot
            int startFluidX = startInputsX + 18 * 4;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    int slotIndex = i * 4 + j;
                    addSlot(builder, startFluidX + 18 * j, startInputsY  + 18 * i, slotIndex, itemHandler, fluidHandler, false, true);
                }
            }
        }
    }
}
