package keqing.gtqtcore.loaders.recipes.chain;

import keqing.gtqtcore.api.unification.GTQTMaterials;


import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.ingot;
import static gregtechfoodoption.GTFOMaterialHandler.BlueVitriol;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.BURNER_REACTOR_RECIPES;

public class SeleniumTelluriumChain {
    public static void init() {

        //  CuSO4 + H2O -> H2SO4 + Cu + O
        CHEMICAL_RECIPES.recipeBuilder()
                .circuitMeta(3)
                .fluidInputs(BlueVitriol.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .output(dust, Copper)
                .chancedOutput(dust, GTQTMaterials.ChalcogenAnodeMud, 500, 0)
                .fluidOutputs(SulfuricAcid.getFluid(1000))
                .fluidOutputs(Oxygen.getFluid(1000))
                .EUt(VA[MV])
                .duration((int) (3.6 * SECOND))
                .buildAndRegister();

        //  Optional recovery of metals to provide some nice bonus
        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.ChalcogenAnodeMud)
                .output(dust, Silver)
                .chancedOutput(dust, Copper, 1000, 1000)
                .chancedOutput(dust, Gold, 750, 750)
                .EUt(VA[MV])
                .duration(15 * SECOND)
                .buildAndRegister();

        TelluriumProcess();
        SeleniumProcess();
    }

    private static void TelluriumProcess() {

        //  Ag2TeSe + 4O + Na2CO3 -> Na2TeO3 + SeO2 + 2Ag + CO2
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.ChalcogenAnodeMud)
                .input(dust, SodaAsh, 6)
                .fluidInputs(Oxygen.getFluid(4000))
                .output(dust, GTQTMaterials.SodiumTellurite, 6)
                .output(dust, GTQTMaterials.SeleniumDioxide, 3)
                .output(ingot, Silver, 2)
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .EUt(VA[HV])
                .duration(MINUTE / 2)
                .blastFurnaceTemp(1900)
                .buildAndRegister();

        //  Na2TeO3 + H2O -> TeO2 + 2NaOH
        ELECTROLYZER_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.SodiumTellurite, 6)
                .circuitMeta(2)
                .fluidInputs(Water.getFluid(1000))
                .output(dust, GTQTMaterials.TelluriumDioxide, 3)
                .output(dust, SodiumHydroxide, 6)
                .EUt(VA[MV])
                .duration(20 * SECOND)
                .buildAndRegister();

        //  TeO2 + 2SO2 + H2O -> Te + H2SO4 + SO3
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.TelluriumDioxide, 3)
                .fluidInputs(SulfurDioxide.getFluid(2000))
                .fluidInputs(Water.getFluid(1000))
                .output(dust, Tellurium)
                .fluidOutputs(SulfuricAcid.getFluid(1000))
                .fluidOutputs(SulfurTrioxide.getFluid(1000))
                .EUt(VA[EV])
                .duration(15 * SECOND)
                .buildAndRegister();
    }

    private static void SeleniumProcess() {

        //  SeO2 + H2O -> H2SeO3
        MIXER_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.SeleniumDioxide, 3)
                .fluidInputs(Water.getFluid(1000))
                .output(dust, GTQTMaterials.SelenousAcid, 6)
                .EUt(VA[MV])
                .duration(20 * SECOND)
                .buildAndRegister();

        //  H2SeO3 + 2SO2 -> Se + H2SO4 + SO3
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.SelenousAcid, 6)
                .fluidInputs(SulfurDioxide.getFluid(2000))
                .output(dust, Selenium)
                .fluidOutputs(SulfuricAcid.getFluid(1000))
                .fluidOutputs(SulfurTrioxide.getFluid(1000))
                .EUt(VA[EV])
                .duration(15 * SECOND)
                .buildAndRegister();
    }
}