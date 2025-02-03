package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.CENTRIFUGE_RECIPES;
import static gregtech.api.recipes.RecipeMaps.MIXER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Stone;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.CHEMICAL_PLANT;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.LARGE_MIXER_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.utils.GTQTUniversUtil.SECOND;
import static keqing.gtqtcore.api.utils.GTQTUniversUtil.TICK;

public class DrillingMudChain {

    public static void register() {

        // CaCO3 + BaSO4 + 2H2O -> (CaCO3)(BaSO4)(H2O)2
        MIXER_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .input(dust, Calcite, 5)
                .input(dust, Barite, 6)
                .fluidInputs(Water.getFluid(2000))
                .fluidOutputs(CalciteBariteSlurry.getFluid(4000))
                .EUt(VA[MV])
                .duration(5 * SECOND)
                .buildAndRegister();

        // Calcite-Barite Slurry -> Drilling Mud
        MIXER_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .input(dust, Bentonite, 1)
                .input(dust, Clay, 1)
                .fluidInputs(CalciteBariteSlurry.getFluid(1000))
                .fluidInputs(Lubricant.getFluid(1000))
                .fluidOutputs(DrillingMud.getFluid(4000))
                .EUt(VA[EV])
                .duration(2 * SECOND + 10 * TICK)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .input(dust, Bentonite, 1)
                .input(dust, Clay, 1)
                .fluidInputs(CalciteBariteSlurry.getFluid(1000))
                .fluidInputs(AdvancedLubricant.getFluid(1000))
                .fluidOutputs(DrillingMud.getFluid(16000))
                .EUt(VA[EV])
                .duration(2 * SECOND + 10 * TICK)
                .buildAndRegister();

        LARGE_MIXER_RECIPES.recipeBuilder()
                .circuitMeta(6)
                .input(dust, Calcite, 5)
                .input(dust, Barite, 6)
                .input(dust, Bentonite, 4)
                .input(dust, Clay, 4)
                .fluidInputs(Water.getFluid(2000))
                .fluidInputs(Lubricant.getFluid(4000))
                .fluidOutputs(DrillingMud.getFluid(16000))
                .EUt(VA[IV])
                .duration(5 * TICK)
                .buildAndRegister();

        LARGE_MIXER_RECIPES.recipeBuilder()
                .circuitMeta(6)
                .input(dust, Calcite, 5)
                .input(dust, Barite, 6)
                .input(dust, Bentonite, 4)
                .input(dust, Clay, 4)
                .fluidInputs(Water.getFluid(2000))
                .fluidInputs(AdvancedLubricant.getFluid(4000))
                .fluidOutputs(DrillingMud.getFluid(64000))
                .EUt(VA[IV])
                .duration(5 * TICK)
                .buildAndRegister();

        // Used Drilling Mud -> Drilling Mud
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(UsedDrillingMud.getFluid(1000))
                .output(dust, Stone, 1)
                .fluidOutputs(DrillingMud.getFluid(856))
                .EUt(VA[LV])
                .duration(2 * SECOND + 10 * TICK)
                .buildAndRegister();

    }

}
