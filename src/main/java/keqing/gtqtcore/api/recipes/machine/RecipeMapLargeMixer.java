package keqing.gtqtcore.api.recipes.machine;


import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.RecipeProgressWidget;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import net.minecraftforge.items.IItemHandlerModifiable;

public class RecipeMapLargeMixer<R extends RecipeBuilder<R>> extends RecipeMap<R> {

    public RecipeMapLargeMixer(String unlocalizedName,
                               int maxInputs, int maxOutputs,
                               int maxFluidInputs, int maxFluidOutputs,
                               R defaultRecipeBuilder,
                               boolean isHidden) {
        super(unlocalizedName, maxInputs, maxOutputs, maxFluidInputs, maxFluidOutputs, defaultRecipeBuilder, isHidden);
    }

    @Override
    public ModularUI.Builder createJeiUITemplate(IItemHandlerModifiable importItems,
                                                 IItemHandlerModifiable exportItems,
                                                 FluidTankList importFluids,
                                                 FluidTankList exportFluids,
                                                 int yOffset) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 203)
                .widget(new RecipeProgressWidget(200, 74, 45, 22, 22,
                        progressBarTexture, moveType, this));
        this.addInventorySlotGroup(builder, importItems, importFluids, false, yOffset);
        this.addInventorySlotGroup(builder, exportItems, exportFluids, true, yOffset);
        return builder;
    }

    @Override
    protected void addInventorySlotGroup(ModularUI.Builder builder,
                                         IItemHandlerModifiable itemHandler, FluidTankList fluidHandler,
                                         boolean isOutputs, int yOffset) {
        int startInputsX1 = 14;
        int startInputsY1 = 9;
        if (!isOutputs) {
            // Item input slots.
            for (int i = 0; i < 3; i++) { // Height.
                for (int j = 0; j < 3; j++) { // Width.
                    int slotIndex = i * 3 + j;
                    this.addSlot(builder, startInputsX1 + 18 * j, startInputsY1 + 18 * i,
                            slotIndex, itemHandler, fluidHandler, false, false);
                }
            }
            // Fluid input slots.
            int startInputsY2 = startInputsY1 + 18 * 3;
            for (int i = 0; i < 2; i++) { // Height.
                for (int j = 0; j < 3; j++) { // Width.
                    int slotIndex = i * 3 + j;
                    this.addSlot(builder, startInputsX1 + 18 * j, startInputsY2 + 18 * i,
                            slotIndex, itemHandler, fluidHandler, true, false);
                }
            }
        } else {
            int startInputsX2 = startInputsX1 + 18 * 3 + 34;
            int startInputsY3 = startInputsY1 + 18 * 2;
            this.addSlot(builder, startInputsX2, startInputsY3, 0,
                    itemHandler, fluidHandler, false, true);
            this.addSlot(builder, startInputsX2 + 18, startInputsY3, 0,
                    itemHandler, fluidHandler, true, true);
        }
    }

}
