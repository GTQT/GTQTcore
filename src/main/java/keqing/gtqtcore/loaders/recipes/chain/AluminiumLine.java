package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.RecipeMaps;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.ELECTROBATH;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.REACTION_FURNACE_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.electrode;

public class AluminiumLine {
    public static void init() {
        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, Ealuminium, 1)
                .input(dust, Carbon, 2)
                .output(ingot, Aluminium, 1)
                .fluidOutputs(CarbonDioxide.getFluid(288))
                .circuitMeta(1)
                .blastFurnaceTemp(1700)
                .duration(1000).EUt(VA[MV]).buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, Ealuminium, 1)
                .input(dust, Carbon, 2)
                .fluidInputs(Nitrogen.getFluid(2000))
                .output(ingot, Aluminium, 1)
                .fluidOutputs(CarbonDioxide.getFluid(288))
                .circuitMeta(2)
                .blastFurnaceTemp(1700)
                .duration(800).EUt(VA[MV]).buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, Aluminium, 1)
                .input(dust, Cryolite, 1)
                .output(ingot, Aluminium, 2)
                .fluidInputs(Nitrogen.getFluid(1000))
                .circuitMeta(2)
                .blastFurnaceTemp(2600)
                .duration(400).EUt(VA[HV]).buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, Aluminium, 1)
                .output(ingot, Aluminium, 1)
                .fluidInputs(Helium.getFluid(1000))
                .circuitMeta(2)
                .blastFurnaceTemp(3500)
                .duration(400).EUt(VA[EV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Graphite,1)
                .input(dust,Aluminium,10)
                .fluidInputs(Cryolite.getFluid(1440))
                .output(dust,Ealuminium,10)
                .fluidOutputs(Cryolite.getFluid(288))
                .EUt(120)
                .tier(1)
                .duration(400)
                .buildAndRegister();




    }
}
