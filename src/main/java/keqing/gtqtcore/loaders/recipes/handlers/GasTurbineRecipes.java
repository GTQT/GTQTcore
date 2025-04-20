package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.recipes.RecipeMaps;

import static gregtech.api.GTValues.LV;
import static gregtech.api.GTValues.V;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Nitrobenzene;
import static keqing.gtqtcore.api.unification.GTQTMaterials.HighTemperatureGas;

public class GasTurbineRecipes {
    public static void init() {
        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(NaturalGas.getFluid(8))
                .fluidOutputs(HighTemperatureGas.getFluid(500))
                .duration(10)
                .EUt(V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(WoodGas.getFluid(8))
                .fluidOutputs(HighTemperatureGas.getFluid(600))
                .duration(12)
                .EUt(V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(SulfuricGas.getFluid(32))
                .fluidOutputs(HighTemperatureGas.getFluid(2500))
                .duration(50)
                .EUt(V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(SulfuricNaphtha.getFluid(4))
                .fluidOutputs(HighTemperatureGas.getFluid(500))
                .duration(10)
                .EUt(V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(CoalGas.getFluid(1))
                .fluidOutputs(HighTemperatureGas.getFluid(300))
                .duration(6)
                .EUt(V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Methane.getFluid(2))
                .fluidOutputs(HighTemperatureGas.getFluid(700))
                .duration(14)
                .EUt(V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Ethylene.getFluid(1))
                .fluidOutputs(HighTemperatureGas.getFluid(400))
                .duration(8)
                .EUt(V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(RefineryGas.getFluid(1))
                .fluidOutputs(HighTemperatureGas.getFluid(500))
                .duration(10)
                .EUt(V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Ethane.getFluid(4))
                .fluidOutputs(HighTemperatureGas.getFluid(2100))
                .duration(42)
                .EUt(V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Propene.getFluid(1))
                .fluidOutputs(HighTemperatureGas.getFluid(600))
                .duration(12)
                .EUt(V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Butadiene.getFluid(16))
                .fluidOutputs(HighTemperatureGas.getFluid(10200))
                .duration(204)
                .EUt(V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Propane.getFluid(4))
                .fluidOutputs(HighTemperatureGas.getFluid(2900))
                .duration(58)
                .EUt(V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Butene.getFluid(1))
                .fluidOutputs(HighTemperatureGas.getFluid(800))
                .duration(16)
                .EUt(V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Phenol.getFluid(1))
                .fluidOutputs(HighTemperatureGas.getFluid(900))
                .duration(18)
                .EUt(V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Benzene.getFluid(1))
                .fluidOutputs(HighTemperatureGas.getFluid(1100))
                .duration(22)
                .EUt(V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Butane.getFluid(4))
                .fluidOutputs(HighTemperatureGas.getFluid(3700))
                .duration(74)
                .EUt(V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(LPG.getFluid(1))
                .fluidOutputs(HighTemperatureGas.getFluid(1000))
                .duration(20)
                .EUt(V[LV])
                .buildAndRegister();

        RecipeMaps.GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Nitrobenzene.getFluid(1))
                .fluidOutputs(HighTemperatureGas.getFluid(4000))
                .duration(80)
                .EUt(V[LV])
                .buildAndRegister();
    }
}
