package keqing.gtqtcore.loaders.recipes.chain;

import keqing.gtqtcore.api.unification.GTQTMaterials;


import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.DRYER_RECIPES;

public class AmmoniaChain {

    public static void init() {
        // Minimized Haber-Bosch Process
        DRYER_RECIPES.recipeBuilder()
                .input(dust,SodiumBicarbonate,12)
                .circuitMeta(1)
                .output(dust,SodaAsh,6)
                .fluidOutputs(Water.getFluid(3000))
                .fluidOutputs(CarbonDioxide.getFluid(3000))
                .duration(20).EUt(VA[MV]).buildAndRegister();
        // CH4 + N -> CH4N
        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(Methane.getFluid(1000))
                .fluidInputs(Air.getFluid(1500))
                .fluidOutputs(GTQTMaterials.RichNitrogenMixture.getFluid(2500))
                .duration(80).EUt(VA[HV]).buildAndRegister();

        // CH4N + 2H2O -> NH4 + CH4 + O2 (lossy)
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(dust, Ruthenium)
                .fluidInputs(GTQTMaterials.RichNitrogenMixture.getFluid(2500))
                .fluidInputs(Water.getFluid(2000))
                .fluidOutputs(GTQTMaterials.RichAmmoniaMixture.getFluid(3000))
                .fluidOutputs(Methane.getFluid(1000))
                .duration(80).EUt(VA[HV]).buildAndRegister();

        // NH4 -> NH3 + H (lossy)
        BREWING_RECIPES.recipeBuilder()
                .notConsumable(dust, Magnetite)
                .fluidInputs(GTQTMaterials.RichAmmoniaMixture.getFluid(1000))
                .fluidOutputs(Ammonia.getFluid(1000))
                .duration(160).EUt(VA[HV]).buildAndRegister();

    }
}
