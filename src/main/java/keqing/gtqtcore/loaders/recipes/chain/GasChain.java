package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.BetAir;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class GasChain {
    public static void init() {
        AirProgress();
        CollectAir();
    }

    private static void CollectAir() {
        GAS_COLLECTOR_RECIPES.recipeBuilder()
                .dimension(50)
                .circuitMeta(30)
                .fluidOutputs(Air.getFluid(10000))
                .duration(200).EUt(16).buildAndRegister();

        //交错
        GAS_COLLECTOR_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .fluidOutputs(Air.getFluid(10000))
                .dimension(20)
                .duration(200).EUt(16).buildAndRegister();

        //交错
        GAS_COLLECTOR_RECIPES.recipeBuilder()
                .circuitMeta(5)
                .fluidOutputs(BetAir.getFluid(10000))
                .dimension(41)
                .duration(200).EUt(16).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(BetAir.getFluid(100000))
                .fluidOutputs(Methane.getFluid(72000))
                .fluidOutputs(CoalGas.getFluid(10000))
                .fluidOutputs(HydrogenSulfide.getFluid(7500))
                .fluidOutputs(SulfurDioxide.getFluid(7500))
                .fluidOutputs(Helium3.getFluid(2500))
                .fluidOutputs(Neon.getFluid(500))
                .fluidOutputs(Radon.getFluid(500))
                .duration(20000).EUt(120).buildAndRegister();

        //火星
        GAS_COLLECTOR_RECIPES.recipeBuilder()
                .circuitMeta(6)
                .fluidOutputs(MarsAir.getFluid(10000))
                .dimension(52)
                .duration(200).EUt(16).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(MarsAir.getFluid(100000))
                .fluidOutputs(CarbonDioxide.getFluid(72000))
                .fluidOutputs(Argon.getFluid(10000))
                .fluidOutputs(Oxygen.getFluid(7500))
                .fluidOutputs(Helium3.getFluid(7500))
                .fluidOutputs(Radon.getFluid(2500))
                .fluidOutputs(Hydrogen.getFluid(500))
                .fluidOutputs(Nitrogen.getFluid(500))
                .fluidOutputs(MagicGas.getFluid(500))
                .duration(2000).EUt(120).buildAndRegister();

        //火星
        GAS_COLLECTOR_RECIPES.recipeBuilder()
                .circuitMeta(7)
                .fluidOutputs(BeneathAir.getFluid(10000))
                .dimension(10)
                .duration(200).EUt(16).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(BeneathAir.getFluid(100000))
                .fluidOutputs(Oxygen.getFluid(72000))
                .fluidOutputs(Carbon.getFluid(10000))
                .fluidOutputs(Argon.getFluid(7500))
                .fluidOutputs(Helium3.getFluid(7500))
                .fluidOutputs(Radon.getFluid(2500))
                .fluidOutputs(Hydrogen.getFluid(500))
                .fluidOutputs(Nitrogen.getFluid(500))
                .fluidOutputs(MagicGas.getFluid(500))
                .duration(2000).EUt(120).buildAndRegister();
    }

    private static void AirProgress() {
    }
}
