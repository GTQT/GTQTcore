package keqing.gtqtcore.loaders.recipes.handlers;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.Titanium;
import static gregtech.api.unification.ore.OrePrefix.pipeNonupleFluid;
import static gregtech.api.unification.ore.OrePrefix.pipeQuadrupleFluid;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static gtqt.api.util.MaterialHelper.Pipe;
import static gtqt.api.util.MaterialHelper.Plastic;
import static keqing.gtqtcore.api.utils.GTRecipeHelper.createIOPartConv;
import static keqing.gtqtcore.api.utils.GTRecipeHelper.createIOPartRecipe;

public class HatchHandler {
    public static void init() {
        createIOPartRecipe(UEV, ITEM_IMPORT_BUS[UEV], ITEM_EXPORT_BUS[UEV], QUANTUM_CHEST[1].getStackForm());
        createIOPartRecipe(UIV, ITEM_IMPORT_BUS[UIV], ITEM_EXPORT_BUS[UIV], QUANTUM_CHEST[2].getStackForm());

        createIOPartRecipe(UEV, FLUID_IMPORT_HATCH[UEV], FLUID_EXPORT_HATCH[UEV], QUANTUM_TANK[1].getStackForm());
        createIOPartRecipe(UIV, FLUID_IMPORT_HATCH[UIV], FLUID_EXPORT_HATCH[UIV], QUANTUM_TANK[2].getStackForm());

        //  Add I to O and O to I convension of UEV-OpV Fluid I/O Hatches.
        createIOPartConv(UEV, 4, QUADRUPLE_IMPORT_HATCH[10], QUADRUPLE_EXPORT_HATCH[10], true);
        createIOPartConv(UIV, 4, QUADRUPLE_IMPORT_HATCH[11], QUADRUPLE_EXPORT_HATCH[11], true);
        createIOPartConv(UXV, 4, QUADRUPLE_IMPORT_HATCH[12], QUADRUPLE_EXPORT_HATCH[12], true);
        createIOPartConv(OpV, 4, QUADRUPLE_IMPORT_HATCH[13], QUADRUPLE_EXPORT_HATCH[13], true);
        createIOPartConv(UEV, 9, NONUPLE_IMPORT_HATCH[10],   NONUPLE_EXPORT_HATCH[10],   true);
        createIOPartConv(UIV, 9, NONUPLE_IMPORT_HATCH[11],   NONUPLE_EXPORT_HATCH[10],   true);
        createIOPartConv(UXV, 9, NONUPLE_IMPORT_HATCH[12],   NONUPLE_EXPORT_HATCH[12],   true);
        createIOPartConv(OpV, 9, NONUPLE_IMPORT_HATCH[13],   NONUPLE_EXPORT_HATCH[13],   true);
        createIOPartConv(UEV, 16, SIXTEEN_IMPORT_HATCH[10],   SIXTEEN_EXPORT_HATCH[10],   true);
        createIOPartConv(UIV, 16, SIXTEEN_IMPORT_HATCH[11],   SIXTEEN_EXPORT_HATCH[10],   true);
        createIOPartConv(UXV, 16, SIXTEEN_IMPORT_HATCH[12],   SIXTEEN_EXPORT_HATCH[12],   true);
        createIOPartConv(OpV, 16, SIXTEEN_IMPORT_HATCH[13],   SIXTEEN_EXPORT_HATCH[13],   true);

        for (int i = 10; i < UIV; i++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(FLUID_IMPORT_HATCH[i])
                    .input(pipeQuadrupleFluid, Pipe.get(i))
                    .fluidInputs(Plastic.get(i).getFluid(L * 4))
                    .circuitMeta(4)
                    .output(QUADRUPLE_IMPORT_HATCH[i])
                    .duration(300).EUt(VA[i]).buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(FLUID_IMPORT_HATCH[i])
                    .input(pipeQuadrupleFluid, Pipe.get(i))
                    .fluidInputs(Plastic.get(i).getFluid(L * 9))
                    .circuitMeta(9)
                    .output(NONUPLE_IMPORT_HATCH[i])
                    .duration(600).EUt(VA[i]).buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(FLUID_IMPORT_HATCH[i])
                    .input(pipeQuadrupleFluid, Pipe.get(i))
                    .fluidInputs(Plastic.get(i).getFluid(L * 16))
                    .circuitMeta(16)
                    .output(SIXTEEN_IMPORT_HATCH[i])
                    .duration(1200).EUt(VA[i]).buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(FLUID_EXPORT_HATCH[i])
                    .input(pipeQuadrupleFluid, Pipe.get(i))
                    .fluidInputs(Plastic.get(i).getFluid(L * 4))
                    .circuitMeta(4)
                    .output(QUADRUPLE_EXPORT_HATCH[i])
                    .duration(300).EUt(VA[i]).buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(FLUID_EXPORT_HATCH[i])
                    .input(pipeNonupleFluid, Titanium)
                    .fluidInputs(Plastic.get(i).getFluid(L * 9))
                    .circuitMeta(9)
                    .output(NONUPLE_EXPORT_HATCH[i])
                    .duration(600).EUt(VA[i]).buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(FLUID_EXPORT_HATCH[i])
                    .input(pipeQuadrupleFluid, Pipe.get(i))
                    .fluidInputs(Plastic.get(i).getFluid(L * 16))
                    .circuitMeta(16)
                    .output(SIXTEEN_EXPORT_HATCH[i])
                    .duration(1200).EUt(VA[i]).buildAndRegister();
        }
    }
}
