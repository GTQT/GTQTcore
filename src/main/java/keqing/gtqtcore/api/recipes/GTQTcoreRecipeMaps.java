package keqing.gtqtcore.api.recipes;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.BlastRecipeBuilder;
import gregtech.api.recipes.builders.FuelRecipeBuilder;
import gregtech.core.sound.GTSoundEvents;


//怎么写请看
//https://github.com/Darknight123MC/Gregica-Sharp/blob/master/src/main/java/me/oganesson/gregicas/api/recipe/GSRecipeMaps.java
public class GTQTcoreRecipeMaps {

    public static final RecipeMap TURBINE_COMBUSTION_CHAMBER;
    public static final RecipeMap<BlastRecipeBuilder> BLAZING_BLAST_FURNACE = new RecipeMap<>("blazing_blast_furnace", 9, 0, 3, 1, new BlastRecipeBuilder(), false)
            .setSlotOverlay(false, false, false, GuiTextures.FURNACE_OVERLAY_1)
            .setSlotOverlay(false, false, true, GuiTextures.FURNACE_OVERLAY_1)
            .setSlotOverlay(false, true, false, GuiTextures.FURNACE_OVERLAY_2)
            .setSlotOverlay(false, true, true, GuiTextures.FURNACE_OVERLAY_2)
            .setSlotOverlay(true, true, false, GuiTextures.FURNACE_OVERLAY_2)
            .setSlotOverlay(true, true, true, GuiTextures.FURNACE_OVERLAY_2);
    private GTQTcoreRecipeMaps() {}

    static {
        TURBINE_COMBUSTION_CHAMBER = (new RecipeMap("plasma_generator", 0, 0, 1, 1, new FuelRecipeBuilder(), false)).setSlotOverlay(false, true, true, GuiTextures.CENTRIFUGE_OVERLAY).setProgressBar(GuiTextures.PROGRESS_BAR_GAS_COLLECTOR, ProgressWidget.MoveType.HORIZONTAL).setSound(GTSoundEvents.TURBINE).allowEmptyOutput();

    }

}