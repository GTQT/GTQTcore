package keqing.gtqtcore.loaders.recipes.handlers;

import static gregtech.api.unification.material.Materials.DistilledWater;
import static gregtech.api.unification.material.Materials.Steam;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.HEAT_EXCHANGE_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class HeatExchangeRecipes {
    public static void init() {

        //  Exhaust gas heat exchange
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(HighPressureSteam.getFluid(1))
                .fluidOutputs(Steam.getFluid(160 * 5))
                .fluidOutputs(SuperheatedSteam.getFluid(80 * 5))
                .fluidOutputs(SteamExhaustGas.getFluid(1))
                .maxRate(1600)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();
    }
}
