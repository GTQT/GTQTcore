package keqing.gtqtcore.loaders.recipes.chain;

import keqing.gtqtcore.api.unification.GTQTMaterials;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;

public class PhosphorusChain {

    public static void init() {
        phosphorus();
        phosphorene();
        phosphorylChloride();
        phosphine();
    }

    private static void phosphorus() {
        // 2Ca3(PO4)2 + 6SiO2 + 5C -> 6CaSiO3 + 5CO2 + P4
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, TricalciumPhosphate, 10)
                .input(dust, SiliconDioxide, 18)
                .input(dust, Carbon, 5)
                .output(dust, GTQTMaterials.Wollastonite, 30)
                .output(gem, GTQTMaterials.WhitePhosphorus)
                .fluidOutputs(CarbonDioxide.getFluid(5000))
                .blastFurnaceTemp(1800)
                .duration(200).EUt(VA[MV]).buildAndRegister();

        //TODO find better recipe
        COMPRESSOR_RECIPES.recipeBuilder()
                .input(dust, Phosphorus, 4)
                .output(gem, GTQTMaterials.WhitePhosphorus)
                .duration(800).EUt(VA[HV]).buildAndRegister();

        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.WhitePhosphorus)
                .fluidInputs(Argon.getFluid(50))
                .output(gem, GTQTMaterials.RedPhosphorus)
                .blastFurnaceTemp(2700)
                .duration(200).EUt(VA[MV]).buildAndRegister();

        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.WhitePhosphorus)
                .fluidInputs(Lead.getFluid(L * 2))
                .output(gem, GTQTMaterials.VioletPhosphorus)
                .duration(400).EUt(VA[HV]).buildAndRegister();




    }

    private static void phosphorene() {
        MIXER_RECIPES.recipeBuilder()
                .input(dust, SodiumHydroxide, 6)
                .input(gem, GTQTMaterials.BlackPhosphorus, 8)
                .fluidInputs(DistilledWater.getFluid(200)) //TODO ultrapure
                .fluidInputs(GTQTMaterials.NMethylPyrrolidone.getFluid(800))
                .fluidOutputs(GTQTMaterials.PhosphoreneSolution.getFluid(1000))
                .duration(600).EUt(VA[IV]).buildAndRegister();

        SONICATION_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.PhosphoreneSolution.getFluid(125))
                .fluidInputs(Argon.getFluid(100))
                .output(foil, GTQTMaterials.Phosphorene, 4)
                .fluidOutputs(GTQTMaterials.NMethylPyrrolidone.getFluid(100))
                .duration(100).EUt(VA[LuV]).buildAndRegister();
    }

    private static void phosphorylChloride() {
        // P4 + 12Cl -> 4PCl3
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.WhitePhosphorus)
                .fluidInputs(Chlorine.getFluid(12000))
                .fluidOutputs(GTQTMaterials.PhosphorusTrichloride.getFluid(4000))
                .duration(120).EUt(VA[MV]).buildAndRegister();

        // PCl3 + O -> POCl3
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.PhosphorusTrichloride.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(GTQTMaterials.PhosphorylChloride.getFluid(1000))
                .duration(120).EUt(VA[HV]).buildAndRegister();
    }

    private static void phosphine() {
        // P4 + 6H2O -> 4PH3 + 6O (lost)
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.WhitePhosphorus)
                .fluidInputs(Water.getFluid(6000))
                .fluidOutputs(GTQTMaterials.Phosphine.getFluid(4000))
                .duration(200).EUt(16).buildAndRegister();

    }
}