package keqing.gtqtcore.api.recipes;

import gregtech.api.gui.GuiTextures;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.BlastRecipeBuilder;
import gregtech.api.recipes.builders.FuelRecipeBuilder;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import keqing.gtqtcore.api.capability.chemical_plant.ChemicalPlantBuilder;


//怎么写请看
//https://github.com/Darknight123MC/Gregica-Sharp/blob/master/src/main/java/me/oganesson/gregicas/api/recipe/GSRecipeMaps.java
public class GTQTcoreRecipeMaps {

    public static final RecipeMap<FuelRecipeBuilder> TURBINE_COMBUSTION_CHAMBER;

    public static final RecipeMap<FuelRecipeBuilder> ROCKET;

    public static final RecipeMap<ChemicalPlantBuilder> CHEMICAL_PLANT;

    public static final RecipeMap<SimpleRecipeBuilder> INTEGRATED_MINING_DIVISION;

    public static final RecipeMap<BlastRecipeBuilder> BLAZING_BLAST_FURNACE = new RecipeMap<>("blazing_blast_furnace", 9, 0, 3, 1, new BlastRecipeBuilder(), false)
            .setSlotOverlay(false, false, false, GuiTextures.FURNACE_OVERLAY_1)
            .setSlotOverlay(false, false, true, GuiTextures.FURNACE_OVERLAY_1)
            .setSlotOverlay(false, true, false, GuiTextures.FURNACE_OVERLAY_2)
            .setSlotOverlay(false, true, true, GuiTextures.FURNACE_OVERLAY_2)
            .setSlotOverlay(true, true, false, GuiTextures.FURNACE_OVERLAY_2)
            .setSlotOverlay(true, true, true, GuiTextures.FURNACE_OVERLAY_2);
    private GTQTcoreRecipeMaps() {}
    static {
        TURBINE_COMBUSTION_CHAMBER = new RecipeMap<>("turbine_combustion_chamber",
                0, 0, 1, 1, new FuelRecipeBuilder(), false);

        ROCKET = new RecipeMap<>("rocket",
                0, 0, 1, 1, new FuelRecipeBuilder(), false);

        CHEMICAL_PLANT = new RecipeMap<>("chemical_plant",
                0, 0, 1, 1, new ChemicalPlantBuilder(), false);

        INTEGRATED_MINING_DIVISION = new RecipeMap<>("integrated_mining_division",
                3, 10, 3, 3, new SimpleRecipeBuilder(), false);
    }

}