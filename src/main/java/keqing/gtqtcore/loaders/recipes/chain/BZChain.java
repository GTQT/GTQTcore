package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import keqing.gtqtcore.api.unification.GTQTMaterials;

import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;

/**
 * The Belousov-Zhabotinsky Reaction
 */
public class BZChain {

    public static void init() {
        potassiumBromate();
        malonicAcid();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.PotassiumBromate, 4)
                .input(dust, GTQTMaterials.MalonicAcid, 3)
                .input(dust, Cerium)
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidOutputs(GTQTMaterials.BZMedium.getFluid(1000))
                .duration(100).EUt(VA[ZPM]).buildAndRegister();
    }

    private static void potassiumBromate() {
        // 2Br + SO2 + 2H2O -> H2SO4 + 2HBr
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Bromine.getFluid(2000))
                .fluidInputs(SulfurDioxide.getFluid(1000))
                .fluidInputs(Water.getFluid(2000))
                .fluidOutputs(SulfuricAcid.getFluid(1000))
                .fluidOutputs(GTQTMaterials.HydrobromicAcid.getFluid(2000))
                .duration(400).EUt(VA[HV]).buildAndRegister();

        // KCl + H2O -> KOH + Cl + H
        ELECTROLYZER_RECIPES.recipeBuilder()
                .input(dust, RockSalt, 2)
                .notConsumable(new IntCircuitIngredient(2))
                .fluidInputs(Water.getFluid(1000))
                .output(dust, GTQTMaterials.PotassiumHydroxide, 3)
                .fluidOutputs(Chlorine.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(1000))
                .duration(720).EUt(VA[LV]).buildAndRegister();

        // 3HBr + 3KOH -> KBrO3 + 3H2O
        LOW_TEMP_ACTIVATOR_RECIPES.recipeBuilder()
                .fluidInputs(Bromine.getFluid(3000))
                .input(dust, GTQTMaterials.PotassiumHydroxide, 1)
                .output(dust, GTQTMaterials.PotassiumBromate, 5)
                .fluidOutputs(Ice.getFluid(3000))
                .duration(200).EUt(VA[HV]).buildAndRegister();
    }

    private static void malonicAcid() {
        // C2H4Cl2 + Cl -> C2HCl3 + 3H
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.Dichloroethane.getFluid(1000))
                .fluidInputs(Chlorine.getFluid(1000))
                .fluidOutputs(GTQTMaterials.Trichloroethylene.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(3000))
                .blastFurnaceTemp(4500)
                .duration(100).EUt(VA[EV]).buildAndRegister();

        // C2HCl3 + 2H2O -> C2H3ClO2 + 2HCl
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.Trichloroethylene.getFluid(1000))
                .fluidInputs(Water.getFluid(2000))
                .notConsumable(SulfuricAcid.getFluid(250))
                .fluidOutputs(GTQTMaterials.ChloroaceticAcid.getFluid(8000))
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .duration(100).EUt(VA[EV]).buildAndRegister();

        // C2H3ClO2 + Na2CO3 + 2H2O -> C3H4O4 + 2NaOH + HClO
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SodaAsh, 6)
                .fluidInputs(GTQTMaterials.ChloroaceticAcid.getFluid(8000))
                .fluidInputs(Water.getFluid(2000))
                .output(dust, SodiumHydroxide, 6)
                .output(dust, GTQTMaterials.MalonicAcid, 11)
                .fluidOutputs(HypochlorousAcid.getFluid(1000))
                .duration(400).EUt(VA[IV]).buildAndRegister();
    }
}
