package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.ingredients.IntCircuitIngredient;


import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.ingot;
import static gregtechfoodoption.GTFOMaterialHandler.BlueVitriol;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.ROASTER_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;

public class SeleniumTelluriumChain {
    static int SECOND=20;
    static int MINUTE=1200;
    public static void init() {

        //  Fix GTFO Blue Vitriol original Electrolyzer recipe
        ELECTROLYZER_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .fluidInputs(BlueVitriol.getFluid(1000))
                .output(dust, Copper)
                .output(dust, Sulfur)
                .fluidOutputs(Oxygen.getFluid(4000))
                .EUt(VA[MV])
                .duration((int) (7.2 * SECOND))
                .buildAndRegister();

        //  CuSO4 + H2O -> H2SO4 + Cu + O
        ELECTROLYZER_RECIPES.recipeBuilder()
                .circuitMeta(3)
                .fluidInputs(BlueVitriol.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .output(dust, Copper)
                .chancedOutput(dust, ChalcogenAnodeMud, 500, 0)
                .fluidOutputs(SulfuricAcid.getFluid(1000))
                .fluidOutputs(Oxygen.getFluid(1000))
                .EUt(VA[MV])
                .duration((int) (3.6 * SECOND))
                .buildAndRegister();

        //  Optional recovery of metals to provide some nice bonus
        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust, ChalcogenAnodeMud)
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
        ROASTER_RECIPES.recipeBuilder()
                .input(dust, ChalcogenAnodeMud)
                .input(dust, SodaAsh, 6)
                .fluidInputs(Oxygen.getFluid(4000))
                .output(dust, SodiumTellurite, 6)
                .output(dust, SeleniumDioxide, 3)
                .output(ingot, Silver, 2)
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .EUt(VA[HV])
                .duration(MINUTE / 2)
                .temperature(1900)
                .buildAndRegister();

        //  Na2TeO3 + H2O -> TeO2 + 2NaOH
        ELECTROLYZER_RECIPES.recipeBuilder()
                .input(dust, SodiumTellurite, 6)
                .fluidInputs(Water.getFluid(1000))
                .output(dust, TelluriumDioxide, 3)
                .output(dust, SodiumHydroxide, 6)
                .EUt(VA[MV])
                .duration(20 * SECOND)
                .buildAndRegister();

        //  TeO2 + 2SO2 + H2O -> Te + H2SO4 + SO3
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, TelluriumDioxide, 3)
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
                .input(dust, SeleniumDioxide, 3)
                .fluidInputs(Water.getFluid(1000))
                .output(dust, SelenousAcid, 6)
                .EUt(VA[MV])
                .duration(20 * SECOND)
                .buildAndRegister();

        //  H2SeO3 + 2SO2 -> Se + H2SO4 + SO3
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SelenousAcid, 6)
                .fluidInputs(SulfurDioxide.getFluid(2000))
                .output(dust, Selenium)
                .fluidOutputs(SulfuricAcid.getFluid(1000))
                .fluidOutputs(SulfurTrioxide.getFluid(1000))
                .EUt(VA[EV])
                .duration(15 * SECOND)
                .buildAndRegister();
    }
}