package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.RecipeMaps;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.ELECTROBATH;
import static keqing.gtqtcore.api.unification.GCYSMaterials.Alumina;
import static keqing.gtqtcore.api.unification.GCYSMaterials.AluminiumHydroxide;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class AluminiumLine {
    public static void init() {

        ELECTROBATH.recipeBuilder()
                .input(dust, Alumina, 2)
                .fluidOutputs(Aluminium.getFluid(576))
                .fluidOutputs(Oxygen.getFluid(6000))
                .tier(1)
                .circuitMeta(4)
                .duration(1600).EUt(VA[MV]).buildAndRegister();

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
                .input(dust,AluminiumHydroxide,10)
                .input(dust,Cryolite,10)
                .output(dust,Sodium,3)
                .output(dust,Aluminium,1)
                .fluidOutputs(Fluorine.getFluid(6000))
                .output(dust,Alumina,5)
                .fluidOutputs(Water.getFluid(3000))
                .circuitMeta(4)
                .EUt(120)
                .tier(1)
                .duration(200)
                .buildAndRegister();

    }
}
