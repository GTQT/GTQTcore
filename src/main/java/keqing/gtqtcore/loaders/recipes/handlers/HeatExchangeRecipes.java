package keqing.gtqtcore.loaders.recipes.handlers;

import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Oxygen;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.ELECTROBATH;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.HEAT_EXCHANGE_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class HeatExchangeRecipes {
    public static void init() {
        //  Exhaust gas heat exchange
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(10))
                .fluidInputs(HighPressureSteam.getFluid(2))
                .fluidOutputs(Steam.getFluid(160 * 10))
                .fluidOutputs(SuperheatedSteam.getFluid(80 * 10))
                .fluidOutputs(SteamExhaustGas.getFluid(2))
                .maxRate(3200)
                .flowRate(1000)
                .duration(1)
                .buildAndRegister();
    }
}
