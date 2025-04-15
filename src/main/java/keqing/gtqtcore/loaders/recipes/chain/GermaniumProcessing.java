package keqing.gtqtcore.loaders.recipes.chain;

import keqing.gtqtcore.api.unification.GTQTMaterials;

import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;

/**
 * The Waelz Process
 *
 * <p>Produces GermaniumChain from Sphalerite</p>
 *
 * <p>Main Products: GermaniumChain</p>
 * <p>Side Products: Zinc, Gallium, Manganese</p>
 *
 * <p>2 Sphalerite -> 1 GermaniumChain</p>
 *
 */
public class GermaniumProcessing {

    public static void init() {
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .duration(140)
                .EUt(VA[HV])
                .input(dust, Sphalerite, 2)
                .fluidInputs(Oxygen.getFluid(5000))
                .output(dust, GTQTMaterials.RoastedSphalerite, 3)
                .output(dust, GTQTMaterials.ZincOxide, 2)
                .fluidOutputs(SulfurDioxide.getFluid(1000))
                .blastFurnaceTemp(3600)
                .buildAndRegister();

        // GeO2 + 2Zn -> Zn2(GeO2)
        MIXER_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.RoastedSphalerite, 3)
                .input(dust, Zinc, 2)
                .output(dust, GTQTMaterials.ZincRichSphalerite, 5)
                .duration(320).EUt(VA[LV]).buildAndRegister();

        // Zn2(GeO2) + H2SO4 -> ZnGeO2 + ZnSO4 + 2H (lost)
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.ZincRichSphalerite, 5)
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .output(dust, GTQTMaterials.WaelzOxide)
                .output(dust, GTQTMaterials.WaelzSlag, 5)
                .duration(400).EUt(VA[LV]).buildAndRegister();

        // ZnSO4 + H2O -> ZnO + H2SO4
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.WaelzSlag, 5)
                .fluidInputs(Water.getFluid(1000))
                .output(dust, GTQTMaterials.ZincOxide, 2)
                .chancedOutput(dust, Gallium, 2000, 1000)
                .fluidOutputs(SulfuricAcid.getFluid(1000))
                .duration(160).EUt(VA[HV]).buildAndRegister();

        // ZnGeO2 + H2SO4 -> GeO2 + ZnSO4 + 2H (lost)
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.WaelzOxide)
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .output(dust, GTQTMaterials.ImpureGermaniumDioxide, 3)
                .output(dust, GTQTMaterials.WaelzSlag, 5)
                .chancedOutput(dust, Manganese, 1000, 1000)
                .duration(200).EUt(VA[HV]).buildAndRegister();

        // GeO2 + 4HCl -> GeCl4 + 2H2O
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.ImpureGermaniumDioxide, 3)
                .fluidInputs(HydrochloricAcid.getFluid(4000))
                .chancedOutput(dust, Cadmium, 500, 1000)
                .fluidOutputs(GTQTMaterials.GermaniumTetrachloride.getFluid(1000))
                //.fluidOutputs(Water.getFluid(2000))
                .duration(300).EUt(VA[HV]).buildAndRegister();

        // GeCl4 + 2H2O -> GeO2 + 4HCl
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.GermaniumTetrachloride.getFluid(1000))
                .fluidInputs(Water.getFluid(2000))
                .output(dust, GTQTMaterials.GermaniumDioxide, 3)
                .fluidOutputs(HydrochloricAcid.getFluid(4000))
                .duration(100).EUt(VA[LV]).buildAndRegister();

        // GeO2 + 4HCl -> GeCl4 + 2H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.GermaniumDioxide, 3)
                .fluidInputs(HydrochloricAcid.getFluid(4000))
                .fluidOutputs(GTQTMaterials.GermaniumTetrachloride.getFluid(1000))
                .fluidOutputs(Water.getFluid(2000))
                .duration(100).EUt(VA[LV]).buildAndRegister();

        // GeO2 + 4H -> Ge + 2H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.GermaniumDioxide, 3)
                .fluidInputs(Hydrogen.getFluid(4000))
                .output(dust, Germanium)
                .fluidOutputs(Water.getFluid(2000))
                .duration(240).EUt(VA[EV]).buildAndRegister();
    }
}
