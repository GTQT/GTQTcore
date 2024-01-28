package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;

import static gregtech.api.GTValues.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.EFUSION_RECIPES;

public class EFusionLoader {

    public static void init() {
        /*EFUSION_RECIPES.recipeBuilder()
                .fluidInputs(Materials.Deuterium.getFluid(125))
                .fluidInputs(Materials.Tritium.getFluid(125))
                .fluidOutputs(Materials.Helium.getPlasma(125))
                .duration(16)
                .EUt(4096)
                .EUToStart(40_000_000)
                .buildAndRegister();

        EFUSION_RECIPES.recipeBuilder()
                .fluidInputs(Materials.Carbon.getFluid(16))
                .fluidInputs(Materials.Helium3.getFluid(125))
                .fluidOutputs(Materials.Oxygen.getPlasma(125))
                .duration(32)
                .EUt(4096)
                .EUToStart(180_000_000)
                .buildAndRegister();

        EFUSION_RECIPES.recipeBuilder()
                .fluidInputs(Materials.Beryllium.getFluid(16))
                .fluidInputs(Materials.Deuterium.getFluid(375))
                .fluidOutputs(Materials.Nitrogen.getPlasma(125))
                .duration(16)
                .EUt(16384)
                .EUToStart(280_000_000)
                .buildAndRegister();

        EFUSION_RECIPES.recipeBuilder()
                .fluidInputs(Materials.Silicon.getFluid(16))
                .fluidInputs(Materials.Magnesium.getFluid(16))
                .fluidOutputs(Materials.Iron.getPlasma(144))
                .duration(32)
                .EUt(VA[IV])
                .EUToStart(300_000_000)
                .buildAndRegister();

        EFUSION_RECIPES.recipeBuilder()
                .fluidInputs(Materials.Potassium.getFluid(16))
                .fluidInputs(Materials.Fluorine.getFluid(125))
                .fluidOutputs(Materials.Nickel.getPlasma(144))
                .duration(16)
                .EUt(VA[LuV])
                .EUToStart(480_000_000)
                .buildAndRegister();

        EFUSION_RECIPES.recipeBuilder()
                .fluidInputs(Materials.Carbon.getFluid(16))
                .fluidInputs(Materials.Magnesium.getFluid(16))
                .fluidOutputs(Materials.Argon.getPlasma(125))
                .duration(32)
                .EUt(24576)
                .EUToStart(180_000_000)
                .buildAndRegister();

        EFUSION_RECIPES.recipeBuilder()
                .fluidInputs(Materials.Plutonium241.getFluid(144))
                .fluidInputs(Materials.Hydrogen.getFluid(2000))
                .fluidOutputs(Materials.Americium.getPlasma(144))
                .duration(64)
                .EUt(98304)
                .EUToStart(500_000_000)
                .buildAndRegister();

        EFUSION_RECIPES.recipeBuilder()
                .fluidInputs(Materials.Silver.getFluid(144))
                .fluidInputs(Materials.Helium3.getFluid(375))
                .fluidOutputs(Materials.Tin.getPlasma(144))
                .duration(16)
                .EUt(49152)
                .EUToStart(280_000_000)
                .buildAndRegister();

         */

    }
}