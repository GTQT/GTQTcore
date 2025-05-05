package keqing.gtqtcore.loaders.recipes.chain;

import keqing.gtqtcore.api.unification.GTQTMaterials;

import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static gregtech.api.GTValues.HV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.ELECTROLYZER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

/**
 * The Thallium Process
 *
 * <p>
 * Produces Thallium from Pyrite or Galena
 * </p>
 *
 * <p>Main Products: Thallium</p>
 * <p>Side Products: None</p>
 *
 * <p>3 Pyrite -> 2 Thallium</p>
 * <p>4 Galena -> 2 Thallium</p>
 */
public class ThalliumProcessing {

    public static void init() {
        // FeS2 + 6O -> Fe + Tl2SO4 + SO2
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, Pyrite, 3)
                .fluidInputs(Oxygen.getFluid(6000))
                .output(ingot, Iron)
                .output(dust, GTQTMaterials.ThalliumSulfate, 7)
                .fluidOutputs(SulfurDioxide.getFluid(1000))
                .blastFurnaceTemp(2700)
                .duration(100).EUt(VA[HV]).buildAndRegister();

        // 2PbS + 6O -> 2Pb + Tl2SO4 + SO2
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, Galena, 4)
                .fluidInputs(Oxygen.getFluid(6000))
                .output(ingot, Lead, 2)
                .output(dust, GTQTMaterials.ThalliumSulfate, 7)
                .fluidOutputs(SulfurDioxide.getFluid(1000))
                .blastFurnaceTemp(2700)
                .duration(100).EUt(VA[HV]).buildAndRegister();

        // Tl2SO4 + H2O -> 2Th + H2SO4 + O
        ELECTROLYZER_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.ThalliumSulfate, 7)
                .fluidInputs(Water.getFluid(1000))
                .circuitMeta(2)
                .output(dust, Thallium, 2)
                .fluidOutputs(SulfuricAcid.getFluid(1000))
                .fluidOutputs(Oxygen.getFluid(1000))
                .duration(200).EUt(60).buildAndRegister();
    }
}
