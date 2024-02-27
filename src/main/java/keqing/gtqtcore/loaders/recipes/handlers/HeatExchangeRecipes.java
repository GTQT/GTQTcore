package keqing.gtqtcore.loaders.recipes.handlers;

import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Oxygen;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class HeatExchangeRecipes {
    public static void init() {
        HEAT_EXCHANGE.recipeBuilder()
                .fluidInputs(HighPressureSteam.getFluid(1))
                .fluidOutputs(SuperheatedSteam.getFluid(40))
                .duration(2)
                .buildAndRegister();

        HEAT_EXCHANGE.recipeBuilder()
                .fluidInputs(SuperheatedSteam.getFluid(1))
                .fluidOutputs(SteamExhaustGas.getFluid(40))
                .duration(1)
                .buildAndRegister();

        HEAT_EXCHANGE.recipeBuilder()
                .fluidInputs(SteamExhaustGas.getFluid(600))
                .fluidOutputs(Water.getFluid(600))
                .duration(1)
                .buildAndRegister();

        HEAT_EXCHANGE.recipeBuilder()
                .fluidInputs(Oxygen.getPlasma(1))
                .fluidOutputs(Oxygen.getFluid(1))
                .duration(24)
                .buildAndRegister();

        HEAT_EXCHANGE.recipeBuilder()
                .fluidInputs(Nitrogen.getPlasma(1))
                .fluidOutputs(Nitrogen.getFluid(1))
                .duration(32)
                .buildAndRegister();

        HEAT_EXCHANGE.recipeBuilder()
                .fluidInputs(Argon.getPlasma(1))
                .fluidOutputs(Argon.getFluid(1))
                .duration(48)
                .buildAndRegister();

        HEAT_EXCHANGE.recipeBuilder()
                .fluidInputs(Iron.getPlasma(1))
                .fluidOutputs(Iron.getFluid(1))
                .duration(56)
                .buildAndRegister();

        HEAT_EXCHANGE.recipeBuilder()
                .fluidInputs(Tin.getPlasma(1))
                .fluidOutputs(Tin.getFluid(1))
                .duration(64)
                .buildAndRegister();

        HEAT_EXCHANGE.recipeBuilder()
                .fluidInputs(Nickel.getPlasma(1))
                .fluidOutputs(Nickel.getFluid(1))
                .duration(96)
                .buildAndRegister();

        HEAT_EXCHANGE.recipeBuilder()
                .fluidInputs(Americium.getPlasma(1))
                .fluidOutputs(Americium.getFluid(1))
                .duration(160)
                .buildAndRegister();
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

        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(10))
                .fluidInputs(SuperheatedSteam.getFluid(20))
                .fluidOutputs(Steam.getFluid(160 * 10))
                .fluidOutputs(Steam.getFluid(80 * 10))
                .fluidOutputs(SteamExhaustGas.getFluid(20))
                .maxRate(3200)
                .flowRate(1000)
                .duration(1)
                .buildAndRegister();

        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(10))
                .fluidInputs(SteamExhaustGas.getFluid(200))
                .fluidOutputs(Steam.getFluid(160 * 10))
                .fluidOutputs(Steam.getFluid(80 * 10))
                .fluidOutputs(Steam.getFluid(20))
                .maxRate(3200)
                .flowRate(1000)
                .duration(1)
                .buildAndRegister();

    }
}
