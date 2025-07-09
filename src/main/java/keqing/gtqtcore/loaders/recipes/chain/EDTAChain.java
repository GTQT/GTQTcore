package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import keqing.gtqtcore.api.unification.GTQTMaterials;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.BURNER_REACTOR_RECIPES;

public class EDTAChain {

    public static void init() {
        // C2H4 + 2Cl -> C2H4Cl2
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Ethylene.getFluid(1000))
                .fluidInputs(Chlorine.getFluid(2000))
                .notConsumable(Iron3Chloride.getFluid(1))
                .fluidOutputs(GTQTMaterials.Dichloroethane.getFluid(1000))
                .duration(80).EUt(VA[LV]).buildAndRegister();

        // C2H4 + 2HCl -> C2H4Cl2 + 2H
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Ethylene.getFluid(1000))
                .fluidInputs(HydrochloricAcid.getFluid(2000))
                .notConsumable(dust, Copper) //TODO CuCl2
                .fluidOutputs(GTQTMaterials.Dichloroethane.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(2000))
                .duration(80).EUt(VA[LV]).buildAndRegister();

        // C2H4Cl2 -> C2H3Cl + HCl
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.Dichloroethane.getFluid(1000))
                .notConsumable(new IntCircuitIngredient(1))
                .fluidOutputs(VinylChloride.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(1000))
                .blastFurnaceTemp(2700)
                .duration(40).EUt(VA[MV]).buildAndRegister();

        // C2H4Cl2 + 2NH3 -> C2H4(NH2)2 + 2HCl
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.Dichloroethane.getFluid(1000))
                .fluidInputs(Ammonia.getFluid(2000))
                .fluidOutputs(GTQTMaterials.Ethylenediamine.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .duration(80).EUt(VA[HV]).buildAndRegister();

        // CH4 + NH3 + 3O -> HCN + 3H2O
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(Methane.getFluid(1000))
                .fluidInputs(Ammonia.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(1000))
                .notConsumable(dust, Platinum)
                .fluidOutputs(GTQTMaterials.HydrogenCyanide.getFluid(1000))
                .fluidOutputs(Steam.getFluid(3000))
                .blastFurnaceTemp(2700)
                .duration(120).EUt(VA[MV]).buildAndRegister();

        // CH₄ + NH₃ + 1.5O₂ → HCN + 3H₂O （铂催化，高温）
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(Methane.getFluid(1000))
                .fluidInputs(Ammonia.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(1500))
                .notConsumable(plate, Platinum)
                .fluidOutputs(GTQTMaterials.HydrogenCyanide.getFluid(1000))
                .fluidOutputs(Steam.getFluid(3000))
                .blastFurnaceTemp(1000)
                .duration(100).EUt(VA[HV]).buildAndRegister();

        // CH₄ + NH₃ → HCN + 3H₂ （无氧，高温催化）
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(Methane.getFluid(1000))
                .fluidInputs(Ammonia.getFluid(1000))
                .notConsumable(dust, Platinum)
                .fluidOutputs(GTQTMaterials.HydrogenCyanide.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(3000))
                .blastFurnaceTemp(4000)
                .duration(120).EUt(VA[EV]).buildAndRegister();

        // NaOH + HCN -> NaCN + H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SodiumHydroxide, 3)
                .fluidInputs(GTQTMaterials.HydrogenCyanide.getFluid(1000))
                .output(dust, GTQTMaterials.SodiumCyanide, 3)
                .fluidOutputs(Water.getFluid(1000))
                .duration(120).EUt(VA[LV]).buildAndRegister();

        // CH3OH -> CH2O + 2H
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dustTiny, Silver)
                .fluidInputs(Methanol.getFluid(1000))
                .fluidOutputs(GTQTMaterials.Formaldehyde.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(2000))
                .blastFurnaceTemp(3600)
                .duration(180).EUt(VA[HV]).buildAndRegister();

        // C2H4(NH2)2 + 4CH2O + 4NaCN + 6H2O -> C10H12Na4N2O8 + 4NH3 + 2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.SodiumCyanide, 12)
                .fluidInputs(GTQTMaterials.Ethylenediamine.getFluid(1000))
                .fluidInputs(GTQTMaterials.Formaldehyde.getFluid(4000))
                .fluidInputs(Water.getFluid(4000))
                .output(dust, GTQTMaterials.TetrasodiumEDTA)
                .fluidOutputs(Ammonia.getFluid(4000))
                .fluidOutputs(Oxygen.getFluid(2000))
                .duration(120).EUt(VA[HV]).buildAndRegister();

        // C10H12Na4N2O8 + 4 HCl -> C10H16N2O8 + 4NaCl
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.TetrasodiumEDTA)
                .fluidInputs(HydrochloricAcid.getFluid(4000))
                .fluidOutputs(GTQTMaterials.EthylenediaminetetraaceticAcid.getFluid(32000))
                .output(dust, Salt, 8)
                .duration(100).EUt(VA[IV]).buildAndRegister();
    }
}
