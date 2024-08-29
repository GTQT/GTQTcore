package keqing.gtqtcore.loaders.recipes.handlers;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.OpV;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.ore.OrePrefix.pipeNonupleFluid;
import static gregtech.api.unification.ore.OrePrefix.pipeQuadrupleFluid;
import static keqing.gtqtcore.api.unification.GCYSMaterials.Kevlar;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.Polyetheretherketone;
import static keqing.gtqtcore.api.utils.GTRecipeHelper.createIOPartConv;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.*;

public class MultiFluidHatches {
    static int SECOND=20;
    public static void init() {
        //  UEV 4x Fluid Import Hatch
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(IMPORT_FLUID_HATCH[0])
                .input(pipeQuadrupleFluid, Lafium)
                .circuitMeta(4)
                .fluidInputs(Polyetheretherketone.getFluid(L * 4))
                .output(QUADRUPLE_IMPORT_FLUID_HATCH[0]) // UEV
                .EUt(VA[UEV])
                .duration(15 * SECOND)
                .buildAndRegister();

        //  UEV 4x Fluid Export Hatch
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(EXPORT_FLUID_HATCH[0])
                .input(pipeQuadrupleFluid, Lafium)
                .circuitMeta(4)
                .fluidInputs(Polyetheretherketone.getFluid(L * 4))
                .output(QUADRUPLE_EXPORT_FLUID_HATCH[0])
                .EUt(VA[UEV])
                .duration(15 * SECOND)
                .buildAndRegister();

        //  UEV 9x Fluid Import Hatch
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(IMPORT_FLUID_HATCH[0])
                .input(pipeNonupleFluid, Lafium)
                .circuitMeta(9)
                .fluidInputs(Polyetheretherketone.getFluid(L * 9))
                .output(NONUPLE_IMPORT_FLUID_HATCH[0])
                .EUt(VA[UEV])
                .duration(30 * SECOND)
                .buildAndRegister();

        //  UEV 9x Fluid Export Hatch
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(EXPORT_FLUID_HATCH[0])
                .input(pipeNonupleFluid, Lafium)
                .circuitMeta(9)
                .fluidInputs(Polyetheretherketone.getFluid(L * 9))
                .output(NONUPLE_EXPORT_FLUID_HATCH[0])
                .EUt(VA[UEV])
                .duration(30 * SECOND)
                .buildAndRegister();

        //  UIV 4x Fluid Import Hatch
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(IMPORT_FLUID_HATCH[1])
                .input(pipeQuadrupleFluid, CrystalMatrix)
                .circuitMeta(4)
                .fluidInputs(Kevlar.getFluid(L * 4))
                .output(QUADRUPLE_IMPORT_FLUID_HATCH[1])
                .EUt(VA[UIV])
                .duration(15 * SECOND)
                .buildAndRegister();

        //  UIV 4x Fluid Export Hatch
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(EXPORT_FLUID_HATCH[1])
                .input(pipeQuadrupleFluid, CrystalMatrix)
                .circuitMeta(4)
                .fluidInputs(Kevlar.getFluid(L * 4))
                .output(QUADRUPLE_EXPORT_FLUID_HATCH[1])
                .EUt(VA[UIV])
                .duration(15 * SECOND)
                .buildAndRegister();

        //  UIV 9x Fluid Import Hatch
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(IMPORT_FLUID_HATCH[1])
                .input(pipeNonupleFluid, CrystalMatrix)
                .circuitMeta(9)
                .fluidInputs(Kevlar.getFluid(L * 9))
                .output(NONUPLE_IMPORT_FLUID_HATCH[1])
                .EUt(VA[UIV])
                .duration(30 * SECOND)
                .buildAndRegister();

        //  UIV 9x Fluid Export Hatch
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(EXPORT_FLUID_HATCH[1])
                .input(pipeNonupleFluid, CrystalMatrix)
                .circuitMeta(9)
                .fluidInputs(Kevlar.getFluid(L * 9))
                .output(NONUPLE_EXPORT_FLUID_HATCH[1])
                .EUt(VA[UEV])
                .duration(600)
                .buildAndRegister();
/*
        //  UXV 4x Fluid Import Hatch
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(IMPORT_FLUID_HATCH[2])
                .input(pipeQuadrupleFluid, QuantumchromodynamicallyConfinedMatter)
                .circuitMeta(4)
                .fluidInputs(Kevlar.getFluid(L * 4))
                .output(QUADRUPLE_IMPORT_FLUID_HATCH[2])
                .EUt(VA[UXV])
                .duration(15 * SECOND)
                .buildAndRegister();

        //  UXV 4x Fluid Export Hatch
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(EXPORT_FLUID_HATCH[2])
                .input(pipeQuadrupleFluid, QuantumchromodynamicallyConfinedMatter)
                .circuitMeta(4)
                .fluidInputs(Kevlar.getFluid(L * 4))
                .output(QUADRUPLE_EXPORT_FLUID_HATCH[2])
                .EUt(VA[UXV])
                .duration(15 * SECOND)
                .buildAndRegister();

        //  UXV 9x Fluid Import Hatch
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(IMPORT_FLUID_HATCH[2])
                .input(pipeNonupleFluid, QuantumchromodynamicallyConfinedMatter)
                .circuitMeta(9)
                .fluidInputs(Kevlar.getFluid(L * 9))
                .output(NONUPLE_IMPORT_FLUID_HATCH[2])
                .EUt(VA[UXV])
                .duration(30 * SECOND)
                .buildAndRegister();

        //  UXV 9x Fluid Export Hatch
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(EXPORT_FLUID_HATCH[2])
                .input(pipeNonupleFluid, QuantumchromodynamicallyConfinedMatter)
                .circuitMeta(9)
                .fluidInputs(Kevlar.getFluid(L * 9))
                .output(NONUPLE_EXPORT_FLUID_HATCH[2])
                .EUt(VA[UXV])
                .duration(30 * SECOND)
                .buildAndRegister();

        //  OpV 4x Fluid Import Hatch
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(IMPORT_FLUID_HATCH[3])
                .input(pipeQuadrupleFluid, Fatalium)
                .circuitMeta(4)
                .fluidInputs(CosmicFabric.getFluid(L * 4))
                .output(QUADRUPLE_IMPORT_FLUID_HATCH[3])
                .EUt(VA[OpV])
                .duration(15 * SECOND)
                .buildAndRegister();

        //  OpV 4x Fluid Export Hatch
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(EXPORT_FLUID_HATCH[3])
                .input(pipeQuadrupleFluid, Fatalium)
                .circuitMeta(4)
                .fluidInputs(CosmicFabric.getFluid(L * 4))
                .output(QUADRUPLE_EXPORT_FLUID_HATCH[3])
                .EUt(VA[OpV])
                .duration(15 * SECOND)
                .buildAndRegister();

        //  OpV 9x Fluid Import Hatch
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(IMPORT_FLUID_HATCH[3])
                .input(pipeNonupleFluid, Fatalium)
                .circuitMeta(9)
                .fluidInputs(CosmicFabric.getFluid(L * 9))
                .output(NONUPLE_IMPORT_FLUID_HATCH[3])
                .EUt(VA[OpV])
                .duration(30 * SECOND)
                .buildAndRegister();

        //  OpV 9x Fluid Export Hatch
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(EXPORT_FLUID_HATCH[3])
                .input(pipeNonupleFluid, Fatalium)
                .circuitMeta(9)
                .fluidInputs(CosmicFabric.getFluid(L * 9))
                .output(NONUPLE_EXPORT_FLUID_HATCH[3])
                .EUt(VA[OpV])
                .duration(30 * SECOND)
                .buildAndRegister();

 */

        //  Add I to O and O to I convension of UEV-OpV Fluid I/O Hatches.
        createIOPartConv(UEV, 4, QUADRUPLE_IMPORT_FLUID_HATCH[0], QUADRUPLE_EXPORT_FLUID_HATCH[0], true);
        createIOPartConv(UIV, 4, QUADRUPLE_IMPORT_FLUID_HATCH[1], QUADRUPLE_EXPORT_FLUID_HATCH[1], true);
        createIOPartConv(UXV, 4, QUADRUPLE_IMPORT_FLUID_HATCH[2], QUADRUPLE_EXPORT_FLUID_HATCH[2], true);
        createIOPartConv(OpV, 4, QUADRUPLE_IMPORT_FLUID_HATCH[3], QUADRUPLE_EXPORT_FLUID_HATCH[3], true);
        createIOPartConv(UEV, 9, NONUPLE_IMPORT_FLUID_HATCH[0],   NONUPLE_EXPORT_FLUID_HATCH[0],   true);
        createIOPartConv(UIV, 9, NONUPLE_IMPORT_FLUID_HATCH[1],   NONUPLE_EXPORT_FLUID_HATCH[0],   true);
        createIOPartConv(UXV, 9, NONUPLE_IMPORT_FLUID_HATCH[2],   NONUPLE_EXPORT_FLUID_HATCH[2],   true);
        createIOPartConv(OpV, 9, NONUPLE_IMPORT_FLUID_HATCH[3],   NONUPLE_EXPORT_FLUID_HATCH[3],   true);

    }
}
