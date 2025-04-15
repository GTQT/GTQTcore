package keqing.gtqtcore.loaders.recipes.chain;

import keqing.gtqtcore.api.unification.GTQTMaterials;

import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.CVD_RECIPES;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.DRYER_RECIPES;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_BATH_RECIPES;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.plate;

public class GalliumNitrideChain {

    public static void init() {
        // Al + 3Na + 3CH3Cl -> 0.5 Al2(CH3)6 + 3NaCl
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Aluminium)
                .input(dust, Sodium, 3)
                .fluidInputs(Chloromethane.getFluid(3000))
                .fluidOutputs(GTQTMaterials.Trimethylaluminium.getFluid(500))
                .output(dust, Salt, 6)
                .duration(150).EUt(VA[MV]).buildAndRegister();

        // Ga + 3Cl -> GaCl3
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Gallium)
                .fluidInputs(Chlorine.getFluid(3000))
                .output(dust, GTQTMaterials.GalliumTrichloride, 4)
                .duration(100).EUt(VA[LV]).buildAndRegister();

        // GaCl3 + 0.5 Al2(CH3)6 -> Ga(CH3)3 + AlCl3
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.GalliumTrichloride, 4)
                .fluidInputs(GTQTMaterials.Trimethylaluminium.getFluid(500))
                .output(dust, GTQTMaterials.AluminiumTrichloride, 4)
                .fluidOutputs(GTQTMaterials.Trimethylgallium.getFluid(1000))
                .duration(300).EUt(VA[MV]).buildAndRegister();

        // AlCl3 + 3H2O -> Al(OH)3 + 3HCl
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.AluminiumTrichloride, 4)
                .fluidInputs(Water.getFluid(3000))
                .output(dust, GTQTMaterials.AluminiumHydroxide, 7)
                .fluidOutputs(HydrochloricAcid.getFluid(3000))
                .duration(60).EUt(VA[LV]).buildAndRegister();

        // 2Al(OH)3 -> Al2O3 + 3H2O
        DRYER_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.AluminiumHydroxide, 2)
                .output(dust, GTQTMaterials.Alumina, 1)
                .fluidOutputs(Water.getFluid(3000))
                .duration(40).EUt(480).buildAndRegister();

        // 2Ga(CH3)3 + 3H2O -> Ga2O3 + 3CH4 + 3H (H lost)
        CVD_RECIPES.recipeBuilder()
                .input(plate, Sapphire)
                .fluidInputs(GTQTMaterials.Trimethylgallium.getFluid(2000))
                .fluidInputs(Water.getFluid(3000))
                .output(dust, GTQTMaterials.GalliumTrioxide, 5)
                .fluidOutputs(Methane.getFluid(3000))
                .duration(160).EUt(VA[HV]).buildAndRegister();

        // Ga2O3 + 2NH3 -> 2GaN + 3H2O
        CVD_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.GalliumTrioxide.getFluid(L * 5))
                .fluidInputs(Ammonia.getFluid(2000))
                .output(plate, GTQTMaterials.GalliumNitride, 4)
                .fluidOutputs(Steam.getFluid(3000))
                .duration(250).EUt(VA[LuV]).buildAndRegister();
    }
}
