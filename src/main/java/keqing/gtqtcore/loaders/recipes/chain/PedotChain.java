package keqing.gtqtcore.loaders.recipes.chain;


import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import keqing.gtqtcore.api.unification.GTQTMaterials;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.POLYMERIZATION_RECIPES;


public class PedotChain {

    public static void init() {
        edot();
        pss();
        tma();
    }

    private static void edot() {
        //C4H10O2 -> C4H6O2 + 4H
        ELECTROLYZER_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.Butanediol.getFluid(1000))
                .fluidOutputs(GTQTMaterials.Diacetyl.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(4000))
                .duration(80).EUt(VA[MV]).buildAndRegister();

        // C4H6O2 + C2H6O2 + SCl2 -> C6H6O2S + 2HCl + 2H2O (lost)
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.Diacetyl.getFluid(1000))
                .fluidInputs(GTQTMaterials.EthyleneGlycol.getFluid(1000))
                .fluidInputs(GTQTMaterials.SulfurDichloride.getFluid(1000))
                .fluidOutputs(GTQTMaterials.Edot.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .duration(100).EUt(VA[HV]).buildAndRegister();
    }


    private static void pss() {
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(1))
                .fluidInputs(Styrene.getFluid(L))
                .fluidInputs(Air.getFluid(1000))
                .fluidOutputs(GTQTMaterials.Polystyrene.getFluid(L))
                .duration(160).EUt(VA[LV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(1))
                .fluidInputs(Styrene.getFluid(L))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(GTQTMaterials.Polystyrene.getFluid(216))
                .duration(160).EUt(VA[LV]).buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(2))
                .fluidInputs(Styrene.getFluid(2160))
                .fluidInputs(Air.getFluid(7500))
                .fluidOutputs(GTQTMaterials.Polystyrene.getFluid(3240))
                .duration(800).EUt(VA[LV]).buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(2))
                .fluidInputs(Styrene.getFluid(2160))
                .fluidInputs(Oxygen.getFluid(7500))
                .fluidOutputs(GTQTMaterials.Polystyrene.getFluid(4320))
                .duration(800).EUt(VA[LV]).buildAndRegister();

        // C8H8 + SO3 -> C8H8SO3
        POLYMERIZATION_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.Polystyrene.getFluid(L))
                .fluidInputs(SulfurTrioxide.getFluid(1000))
                .fluidOutputs(GTQTMaterials.PolystyreneSulfonate.getFluid(L))
                .EUt(VA[HV])
                .duration(8 * SECOND)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.Edot.getFluid(1000))
                .fluidInputs(GTQTMaterials.PolystyreneSulfonate.getFluid(L))
                .fluidOutputs(GTQTMaterials.PedotPSS.getFluid(L * 9))
                .duration(400).EUt(VA[LuV]).buildAndRegister();
    }

    private static void tma() {
        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.Edot.getFluid(1000))
                .fluidInputs(GTQTMaterials.PMMA.getFluid(L))
                .fluidOutputs(GTQTMaterials.PedotTMA.getFluid(L * 9))
                .duration(400).EUt(VA[ZPM]).buildAndRegister();
    }
}