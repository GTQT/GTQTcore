package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.recipes.GTRecipeHandler;

import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class RocketEngineRecipes {
    public static void init() {

        //  Remove rocket fuel Combustion generator recipe
        GTRecipeHandler.removeRecipesByInputs(COMBUSTION_GENERATOR_FUELS, RocketFuel.getFluid(16));
        //  Rocket Fuel
        ROCKET.recipeBuilder()
                .fluidInputs(RocketFuel.getFluid(16))
                .EUt(32)
                .duration(20*64)
                .buildAndRegister();

        //  RP-1 Rocket Fuel
        DISTILLERY_RECIPES.recipeBuilder()
                .fluidInputs(CoalTar.getFluid(50))
                .circuitMeta(0)
                .fluidOutputs(HighlyPurifiedCoalTar.getFluid(25))
                .EUt(128)
                .duration(16)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(HighlyPurifiedCoalTar.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(RP1RocketFuel.getFluid(1000))
                .EUt(VA[HV])
                .duration(16)
                .buildAndRegister();

        ROCKET.recipeBuilder()
                .fluidInputs(RP1RocketFuel.getFluid(12))
                .EUt(32)
                .duration(60*64)
                .buildAndRegister();

        //  Dense Hydrazine Mixture Fuel
        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(Dimethylhydrazine.getFluid(1000))
                .fluidInputs(Methanol.getFluid(1000))
                .fluidOutputs(DenseHydrazineMixtureFuel.getFluid(1000))
                .EUt(240)
                .duration(120)
                .buildAndRegister();

        ROCKET.recipeBuilder()
                .fluidInputs(DenseHydrazineMixtureFuel.getFluid(9))
                .EUt(32)
                .duration(80*64)
                .buildAndRegister();

        SOLAR_PLATE.recipeBuilder()
                .fluidInputs(Water.getFluid(1))
                .EUt(30)
                .duration(200)
                .buildAndRegister();

        //  Methylhydrazine Nitrate Rocket Fuel
        DISSOLUTION_TANK_RECIPES.recipeBuilder()
                .input(dust, Carbon)
                .fluidInputs(Hydrazine.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidOutputs(Methylhydrazine.getFluid(1000))
                .EUt(VA[HV])
                .duration(480)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(Methylhydrazine.getFluid(1000))
                .fluidInputs(Tetranitromethane.getFluid(1000))
                .fluidOutputs(MethylhydrazineNitrateRocketFuel.getFluid(1000))
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

        ROCKET.recipeBuilder()
                .fluidInputs(MethylhydrazineNitrateRocketFuel.getFluid(6))
                .EUt(32)
                .duration(120*64)
                .buildAndRegister();

    }
}
