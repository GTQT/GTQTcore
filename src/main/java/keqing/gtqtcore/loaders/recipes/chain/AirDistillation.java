package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.GTValues;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collection;
import java.util.List;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.CENTRIFUGE_RECIPES;
import static gregtech.api.recipes.RecipeMaps.VACUUM_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.ingot;
import static gregtech.api.unification.ore.OrePrefix.ingotHot;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class AirDistillation {
    public static void init() {
        bath(Silicon,1);
        vaccum(LiquidCarbonDioxide,CarbonDioxide,1);
        vaccum(LiquidNitrogen,Nitrogen,2);
        vaccum(LiquidHydrogen,Hydrogen,2);
        vaccum(LiquidArgon,Argon,3);
        vaccum(LiquidRadon,Radon,4);

        Collection<Recipe> gasRecipes = RecipeMaps.DISTILLATION_RECIPES.getRecipeList();
        for (Recipe recipe : gasRecipes) {
            List<GTRecipeInput> fluidInputs = recipe.getFluidInputs();
            List<FluidStack> fluidOutputs = recipe.getFluidOutputs();
            Collection<GTRecipeInput> Inputs = recipe.getInputs();
            Collection<ItemStack> Outputs = recipe.getOutputs();
            GTQTcoreRecipeMaps.LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                    .fluidInputs(fluidInputs)
                    .fluidOutputs(fluidOutputs)
                    .inputIngredients(Inputs)
                    .outputs(Outputs)
                    .duration(recipe.getDuration()/4)
                    .EUt(recipe.getEUt()*2)
                    .buildAndRegister();

            GTQTcoreRecipeMaps.HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                    .fluidInputs(fluidInputs)
                    .fluidOutputs(fluidOutputs)
                    .inputIngredients(Inputs)
                    .outputs(Outputs)
                    .duration(recipe.getDuration()/8)
                    .EUt(recipe.getEUt()*2)
                    .buildAndRegister();

        }

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidAir.getFluid(50000))
                .fluidOutputs(LiquidNitrogen.getFluid(35000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 11000))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 1000))
                .fluidOutputs(LiquidArgon.getFluid(500))
                .fluidOutputs(LiquidRadon.getFluid(100))
                .circuitMeta(1)
                .duration(1000).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidAir.getFluid(50000))
                .fluidOutputs(LiquidNitrogen.getFluid(35000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 11000))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 1000))
                .fluidOutputs(LiquidArgon.getFluid(500))
                .circuitMeta(2)
                .duration(800).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidAir.getFluid(50000))
                .fluidOutputs(LiquidNitrogen.getFluid(35000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 11000))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 1000))
                .circuitMeta(2)
                .duration(600).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidAir.getFluid(50000))
                .fluidOutputs(LiquidNitrogen.getFluid(35000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 11000))
                .circuitMeta(2)
                .duration(400).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidAir.getFluid(50000))
                .fluidOutputs(LiquidNitrogen.getFluid(35000))
                .circuitMeta(2)
                .duration(200).EUt(VA[MV]).buildAndRegister();
        ////
        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidAir.getFluid(50000))
                .fluidOutputs(LiquidNitrogen.getFluid(35000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 11000))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 1000))
                .fluidOutputs(LiquidArgon.getFluid(500))
                .fluidOutputs(LiquidRadon.getFluid(100))
                .circuitMeta(1)
                .duration(100).EUt(VA[EV]).buildAndRegister();

        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidAir.getFluid(50000))
                .fluidOutputs(LiquidNitrogen.getFluid(35000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 11000))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 1000))
                .fluidOutputs(LiquidArgon.getFluid(500))
                .circuitMeta(2)
                .duration(80).EUt(VA[EV]).buildAndRegister();

        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidAir.getFluid(50000))
                .fluidOutputs(LiquidNitrogen.getFluid(35000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 11000))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 1000))
                .circuitMeta(2)
                .duration(60).EUt(VA[EV]).buildAndRegister();

        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidAir.getFluid(50000))
                .fluidOutputs(LiquidNitrogen.getFluid(35000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 11000))
                .circuitMeta(2)
                .duration(40).EUt(VA[EV]).buildAndRegister();

        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidAir.getFluid(50000))
                .fluidOutputs(LiquidNitrogen.getFluid(35000))
                .circuitMeta(2)
                .duration(20).EUt(VA[EV]).buildAndRegister();

        ///
        VACUUM_RECIPES.recipeBuilder()
                .fluidInputs(MarsAir.getFluid(4000))
                .fluidOutputs(LiquidMarsAir.getFluid(4000))
                .duration(8).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(100000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(72000))
                .fluidOutputs(LiquidArgon.getFluid(10000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 7500))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 7500))
                .fluidOutputs(LiquidRadon.getFluid(2500))
                .fluidOutputs(LiquidHydrogen.getFluid(500))
                .fluidOutputs(LiquidNitrogen.getFluid(500))
                .fluidOutputs(MagicGas.getFluid(500))
                .circuitMeta(1)
                .duration(2000).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(100000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(72000))
                .fluidOutputs(LiquidArgon.getFluid(10000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 7500))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 7500))
                .fluidOutputs(LiquidRadon.getFluid(2500))
                .fluidOutputs(LiquidHydrogen.getFluid(500))
                .fluidOutputs(MagicGas.getFluid(500))
                .circuitMeta(2)
                .duration(1800).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(100000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(72000))
                .fluidOutputs(LiquidArgon.getFluid(10000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 7500))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 7500))
                .fluidOutputs(LiquidRadon.getFluid(2500))
                .fluidOutputs(MagicGas.getFluid(500))
                .circuitMeta(3)
                .duration(1600).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(100000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(72000))
                .fluidOutputs(LiquidArgon.getFluid(10000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 7500))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 7500))
                .fluidOutputs(MagicGas.getFluid(500))
                .circuitMeta(4)
                .duration(1400).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(100000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(72000))
                .fluidOutputs(LiquidArgon.getFluid(10000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 7500))
                .fluidOutputs(MagicGas.getFluid(500))
                .circuitMeta(5)
                .duration(1200).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(100000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(72000))
                .fluidOutputs(LiquidArgon.getFluid(10000))
                .fluidOutputs(MagicGas.getFluid(500))
                .circuitMeta(6)
                .duration(1000).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(100000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(72000))
                .fluidOutputs(MagicGas.getFluid(500))
                .circuitMeta(7)
                .duration(800).EUt(VA[MV]).buildAndRegister();


        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(100000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(72000))
                .fluidOutputs(LiquidArgon.getFluid(10000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 7500))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 7500))
                .fluidOutputs(LiquidRadon.getFluid(2500))
                .fluidOutputs(LiquidHydrogen.getFluid(500))
                .fluidOutputs(LiquidNitrogen.getFluid(500))
                .fluidOutputs(MagicGas.getFluid(500))
                .circuitMeta(1)
                .duration(200).EUt(VA[EV]).buildAndRegister();

        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(100000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(72000))
                .fluidOutputs(LiquidArgon.getFluid(10000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 7500))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 7500))
                .fluidOutputs(LiquidRadon.getFluid(2500))
                .fluidOutputs(LiquidHydrogen.getFluid(500))
                .fluidOutputs(MagicGas.getFluid(500))
                .circuitMeta(2)
                .duration(180).EUt(VA[EV]).buildAndRegister();

        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(100000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(72000))
                .fluidOutputs(LiquidArgon.getFluid(10000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 7500))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 7500))
                .fluidOutputs(LiquidRadon.getFluid(2500))
                .fluidOutputs(MagicGas.getFluid(500))
                .circuitMeta(3)
                .duration(160).EUt(VA[EV]).buildAndRegister();

        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(100000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(72000))
                .fluidOutputs(LiquidArgon.getFluid(10000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 7500))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 7500))
                .fluidOutputs(MagicGas.getFluid(500))
                .circuitMeta(4)
                .duration(140).EUt(VA[EV]).buildAndRegister();

        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(100000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(72000))
                .fluidOutputs(LiquidArgon.getFluid(10000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 7500))
                .fluidOutputs(MagicGas.getFluid(500))
                .circuitMeta(5)
                .duration(120).EUt(VA[EV]).buildAndRegister();

        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(100000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(72000))
                .fluidOutputs(LiquidArgon.getFluid(10000))
                .fluidOutputs(MagicGas.getFluid(500))
                .circuitMeta(6)
                .duration(100).EUt(VA[EV]).buildAndRegister();

        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(100000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(72000))
                .fluidOutputs(MagicGas.getFluid(500))
                .circuitMeta(7)
                .duration(80).EUt(VA[EV]).buildAndRegister();


        ///
        VACUUM_RECIPES.recipeBuilder()
                .fluidInputs(BetAir.getFluid(4000))
                .fluidOutputs(LiquidBetAir.getFluid(4000))
                .duration(8).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidBetAir.getFluid(100000))
                .fluidOutputs(Methane.getFluid(72000))
                .fluidOutputs(CoalGas.getFluid(10000))
                .fluidOutputs(HydrogenSulfide.getFluid(7500))
                .fluidOutputs(SulfurDioxide.getFluid(7500))
                .fluidOutputs(Helium3.getFluid(2500))
                .fluidOutputs(Neon.getFluid(500))
                .fluidOutputs(Radon.getFluid(500))
                .duration(2000).EUt(VA[MV]).buildAndRegister();

        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidBetAir.getFluid(100000))
                .fluidOutputs(Methane.getFluid(72000))
                .fluidOutputs(CoalGas.getFluid(10000))
                .fluidOutputs(HydrogenSulfide.getFluid(7500))
                .fluidOutputs(SulfurDioxide.getFluid(7500))
                .fluidOutputs(Helium3.getFluid(2500))
                .fluidOutputs(Neon.getFluid(500))
                .fluidOutputs(Radon.getFluid(500))
                .duration(200).EUt(VA[EV]).buildAndRegister();
    }

    private static void bath(Material material, int i) {
        BATH_CONDENSER.recipeBuilder()
                .output(ingotHot,material)
                .output(ingot,material)
                .duration(20+20*i)
                .EUt(GTValues.VA[1+i])
                .buildAndRegister();
    }

    private static void vaccum(Material material1, Material material2, int i) {
        VACUUM_RECIPES.recipeBuilder()
                .fluidInputs(material1.getFluid(1000))
                .fluidOutputs(material2.getFluid(1000))
                .duration(200+200*i).EUt(VA[2+i]).buildAndRegister();

        FLUID_EXTRACTOR_RECIPES.recipeBuilder()
                .fluidOutputs(material1.getFluid(1000))
                .fluidInputs(material2.getFluid(1000))
                .duration(80+20*i).EUt(VA[1+i]).buildAndRegister();
    }
}
