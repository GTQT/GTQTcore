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

    public static final RecipeMap<FuelRecipeBuilder> NAQUADAH_REACTOR;

    public static final RecipeMap<FuelRecipeBuilder> I_MODULAR_FISSION_REACTOR;

    public static final RecipeMap<ChemicalPlantBuilder> CHEMICAL_PLANT;

    public static final RecipeMap<SimpleRecipeBuilder> INTEGRATED_MINING_DIVISION;
    public static final RecipeMap<FuelRecipeBuilder> STEAM_BLAST_FURNACE_RECIPES;
    public static final RecipeMap<FuelRecipeBuilder> STEAM_ORE_WASHER_RECIPES;

    private GTQTcoreRecipeMaps() {}
    static {
        TURBINE_COMBUSTION_CHAMBER = new RecipeMap<>("turbine_combustion_chamber",
                0, 0, 1, 1, new FuelRecipeBuilder(), false);

        ROCKET = new RecipeMap<>("rocket",
                0, 0, 1, 1, new FuelRecipeBuilder(), false);

        NAQUADAH_REACTOR = new RecipeMap<>("naquadah_reactor",
                0, 0, 1, 1, new FuelRecipeBuilder(), false);

        I_MODULAR_FISSION_REACTOR = new RecipeMap<>("i_modular_fission_reactor",
                1, 1, 1, 1, new FuelRecipeBuilder(), false);

        CHEMICAL_PLANT = new RecipeMap<>("chemical_plant",
                6, 6, 6, 6, new ChemicalPlantBuilder(), false);

        INTEGRATED_MINING_DIVISION = new RecipeMap<>("integrated_mining_division",
                3, 3, 3, 0, new SimpleRecipeBuilder(), false);

        STEAM_BLAST_FURNACE_RECIPES = new RecipeMap<>("steam_blast_furance",
                1, 1, 0, 0, new FuelRecipeBuilder(), false);

        STEAM_ORE_WASHER_RECIPES = new RecipeMap<>("steam_ore_washer",
                1, 1, 0, 0, new FuelRecipeBuilder(), false);
    }

}