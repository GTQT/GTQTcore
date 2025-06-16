package keqing.gtqtcore.api.recipes.ui;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ui.RecipeMapUI;
import net.minecraftforge.items.IItemHandlerModifiable;

public class EOHUI<R extends RecipeMap<?>> extends RecipeMapUI<R> {

    public EOHUI(R recipeMap) {
        super(recipeMap, false, false, false, false, false);
    }

    @Override
    public ModularUI.Builder createJeiUITemplate(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems, FluidTankList importFluids, FluidTankList exportFluids, int yOffset) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 196 * 2 + 10)
                .widget(new ProgressWidget(200, 88 - 9, 18, 18, 15, GuiTextures.PROGRESS_BAR_HAMMER, ProgressWidget.MoveType.VERTICAL_DOWNWARDS));
        this.addInventorySlotGroup(builder, importItems, importFluids, false, yOffset);
        this.addInventorySlotGroup(builder, exportItems, exportFluids, true, yOffset);
        return builder;
    }
    @Override
    protected void addInventorySlotGroup(ModularUI.Builder builder, IItemHandlerModifiable itemHandler, FluidTankList fluidHandler, boolean isOutputs, int yOffset) {

        int startOutputsX = 7;
        int startOutputsY = 18 + 15;

        if (isOutputs) {
            //  item output slots
            for (int i = 0; i < 9; i++) { // height
                for (int j = 0; j < 9; j++) { // width
                    int slotIndex = i * 9 + j;
                    addSlot(builder, startOutputsX + 18 * j, startOutputsY + 18 * i, slotIndex, itemHandler, fluidHandler, false, true);
                }
            }
            //  TODO clean it!
            //  fluid output slots
            int StartOutputsY = 18 + 15 + 18 * 8;
            addSlot(builder, startOutputsX, StartOutputsY + 18, 0, itemHandler, fluidHandler, true, false);
            addSlot(builder, startOutputsX + 18, StartOutputsY + 18, 1, itemHandler, fluidHandler, true, false);
            addSlot(builder, startOutputsX + 18 * 2, StartOutputsY + 18, 2, itemHandler, fluidHandler, true, false);
            addSlot(builder, startOutputsX + 18 * 3, StartOutputsY + 18, 3, itemHandler, fluidHandler, true, false);
            addSlot(builder, startOutputsX + 18 * 4, StartOutputsY + 18, 4, itemHandler, fluidHandler, true, false);
            addSlot(builder, startOutputsX + 18 * 5, StartOutputsY + 18, 5, itemHandler, fluidHandler, true, false);
            addSlot(builder, startOutputsX + 18 * 6, StartOutputsY + 18, 6, itemHandler, fluidHandler, true, false);
            addSlot(builder, startOutputsX + 18 * 7, StartOutputsY + 18, 7, itemHandler, fluidHandler, true, false);
            addSlot(builder, startOutputsX + 18 * 8, StartOutputsY + 18, 8, itemHandler, fluidHandler, true, false);
            addSlot(builder, startOutputsX, StartOutputsY + 18 * 2, 9, itemHandler, fluidHandler, true, false);
            addSlot(builder, startOutputsX + 18, StartOutputsY + 18 * 2, 10, itemHandler, fluidHandler, true, false);
            addSlot(builder, startOutputsX + 18 * 2, StartOutputsY + 18 * 2, 11, itemHandler, fluidHandler, true, false);
            addSlot(builder, startOutputsX + 18 * 3, StartOutputsY + 18 * 2, 12, itemHandler, fluidHandler, true, false);
            addSlot(builder, startOutputsX + 18 * 4, StartOutputsY + 18 * 2, 13, itemHandler, fluidHandler, true, false);
            addSlot(builder, startOutputsX + 18 * 5, StartOutputsY + 18 * 2, 14, itemHandler, fluidHandler, true, false);
            addSlot(builder, startOutputsX + 18 * 6, StartOutputsY + 18 * 2, 15, itemHandler, fluidHandler, true, false);
            addSlot(builder, startOutputsX + 18 * 7, StartOutputsY + 18 * 2, 16, itemHandler, fluidHandler, true, false);
            addSlot(builder, startOutputsX + 18 * 8, StartOutputsY + 18 * 2, 17, itemHandler, fluidHandler, true, false);
        } else {
            // 修改点：在输入物品槽位两侧添加流体槽
            int centerX = 88 - 10; // 原物品槽的X坐标
            int yPos = 0;          // Y坐标保持不变

            // 左侧流体槽 (索引0)
            addSlot(builder, centerX - 18, yPos, 0, itemHandler, fluidHandler, true, false);

            // 中间物品槽 (保持原索引0)
            addSlot(builder, centerX, yPos, 0, itemHandler, fluidHandler, false, false);

            // 右侧流体槽 (索引1)
            addSlot(builder, centerX + 18, yPos, 1, itemHandler, fluidHandler, true, false);
        }
    }
}