package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.RecipeMaps;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.ELECTROBATH;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.REACTION_FURNACE_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.Alumina;
import static keqing.gtqtcore.api.unification.GCYSMaterials.AluminiumHydroxide;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.electrode;

public class AluminiumLine {
    public static void init() {
        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, Alumina, 2)
                .input(dust, Carbon, 3)
                .output(ingot, Aluminium, 4)
                .fluidOutputs(CarbonDioxide.getFluid(3000))
                .circuitMeta(1)
                .blastFurnaceTemp(1700)
                .duration(1600).EUt(VA[MV]).buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, Alumina, 2)
                .input(dust, Carbon, 3)
                .fluidInputs(Nitrogen.getFluid(2000))
                .output(ingot, Aluminium, 4)
                .fluidOutputs(CarbonDioxide.getFluid(3000))
                .circuitMeta(2)
                .blastFurnaceTemp(1700)
                .duration(1000).EUt(VA[MV]).buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, Aluminium, 1)
                .input(dust, Cryolite, 1)
                .output(ingot, Aluminium, 2)
                .fluidInputs(Nitrogen.getFluid(1000))
                .circuitMeta(2)
                .blastFurnaceTemp(3500)
                .duration(400).EUt(VA[EV]).buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, Aluminium, 1)
                .output(ingot, Aluminium, 1)
                .fluidInputs(Helium.getFluid(1000))
                .circuitMeta(2)
                .blastFurnaceTemp(4400)
                .duration(400).EUt(VA[EV]).buildAndRegister();

        //铝土矿-》偏铝酸钠-》氢氧化铝-》氧化铝
        AUTOCLAVE_RECIPES.recipeBuilder()
                .input(crushed,Bauxite,2)
                .fluidInputs(Steam.getFluid(8000))
                .output(dust,SodiumAluminate,4)
                .fluidOutputs(Water.getFluid(2000))
                .EUt(30)
                .duration(400)
                .buildAndRegister();

        AUTOCLAVE_RECIPES.recipeBuilder()
                .input(dust,Bauxite,1)
                .fluidInputs(Steam.getFluid(8000))
                .output(dust,SodiumAluminate,4)
                .fluidOutputs(Water.getFluid(2000))
                .EUt(30)
                .duration(400)
                .buildAndRegister();

        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust,SodiumAluminate,4)
                .fluidInputs(Water.getFluid(6000))
                .output(dust,AluminiumHydroxide,7)
                .EUt(30)
                .duration(400)
                .buildAndRegister();


        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Graphite,1)
                .input(dust,AluminiumHydroxide,10)
                .fluidInputs(Cryolite.getFluid(1440))
                .output(dust,Alumina,5)
                .fluidOutputs(Cryolite.getFluid(288))
                .fluidOutputs(Water.getFluid(3000))
                .EUt(120)
                .tier(1)
                .duration(200)
                .buildAndRegister();




    }
}
