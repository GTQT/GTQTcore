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
import static gregtech.api.recipes.RecipeMaps.*;
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
        vaccum(LiquidRadon,Radon,3);
        vaccum(LiquidHelium,Helium,4);

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
                .fluidInputs(LiquidAir.getFluid(5000))
                .fluidOutputs(LiquidNitrogen.getFluid(3500))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 1100))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 100))
                .fluidOutputs(LiquidArgon.getFluid(50))
                .fluidOutputs(LiquidRadon.getFluid(10))
                .circuitMeta(1)
                .duration(1000).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidAir.getFluid(5000))
                .fluidOutputs(LiquidNitrogen.getFluid(3500))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 1100))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 100))
                .fluidOutputs(LiquidArgon.getFluid(50))
                .circuitMeta(2)
                .duration(800).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidAir.getFluid(5000))
                .fluidOutputs(LiquidNitrogen.getFluid(3500))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 1000))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 100))
                .circuitMeta(2)
                .duration(600).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidAir.getFluid(5000))
                .fluidOutputs(LiquidNitrogen.getFluid(3500))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 1100))
                .circuitMeta(2)
                .duration(400).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidAir.getFluid(5000))
                .fluidOutputs(LiquidNitrogen.getFluid(3500))
                .circuitMeta(2)
                .duration(200).EUt(VA[MV]).buildAndRegister();
        ////
        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidAir.getFluid(5000))
                .fluidOutputs(LiquidNitrogen.getFluid(3500))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 1100))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 100))
                .fluidOutputs(LiquidArgon.getFluid(50))
                .fluidOutputs(LiquidRadon.getFluid(10))
                .circuitMeta(1)
                .duration(100).EUt(VA[EV]).buildAndRegister();

        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidAir.getFluid(5000))
                .fluidOutputs(LiquidNitrogen.getFluid(3500))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 1100))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 100))
                .fluidOutputs(LiquidArgon.getFluid(50))
                .circuitMeta(2)
                .duration(80).EUt(VA[EV]).buildAndRegister();

        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidAir.getFluid(5000))
                .fluidOutputs(LiquidNitrogen.getFluid(3500))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 1100))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 100))
                .circuitMeta(2)
                .duration(60).EUt(VA[EV]).buildAndRegister();

        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidAir.getFluid(5000))
                .fluidOutputs(LiquidNitrogen.getFluid(3500))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 1100))
                .circuitMeta(2)
                .duration(40).EUt(VA[EV]).buildAndRegister();

        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidAir.getFluid(5000))
                .fluidOutputs(LiquidNitrogen.getFluid(3500))
                .circuitMeta(2)
                .duration(20).EUt(VA[EV]).buildAndRegister();

        ///
        VACUUM_RECIPES.recipeBuilder()
                .fluidInputs(MarsAir.getFluid(4000))
                .fluidOutputs(LiquidMarsAir.getFluid(4000))
                .duration(80).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(10000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(7200))
                .fluidOutputs(LiquidArgon.getFluid(1000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 750))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 750))
                .fluidOutputs(LiquidRadon.getFluid(250))
                .fluidOutputs(LiquidHydrogen.getFluid(50))
                .fluidOutputs(LiquidNitrogen.getFluid(50))
                .fluidOutputs(MagicGas.getFluid(50))
                .circuitMeta(1)
                .duration(2000).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(10000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(7200))
                .fluidOutputs(LiquidArgon.getFluid(1000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 750))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 750))
                .fluidOutputs(LiquidRadon.getFluid(250))
                .fluidOutputs(LiquidHydrogen.getFluid(50))
                .fluidOutputs(MagicGas.getFluid(50))
                .circuitMeta(2)
                .duration(1800).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(10000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(7200))
                .fluidOutputs(LiquidArgon.getFluid(1000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 750))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 750))
                .fluidOutputs(LiquidRadon.getFluid(250))
                .fluidOutputs(MagicGas.getFluid(50))
                .circuitMeta(3)
                .duration(1600).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(10000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(7200))
                .fluidOutputs(LiquidArgon.getFluid(1000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 750))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 750))
                .fluidOutputs(MagicGas.getFluid(50))
                .circuitMeta(4)
                .duration(1400).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(10000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(7200))
                .fluidOutputs(LiquidArgon.getFluid(1000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 750))
                .fluidOutputs(MagicGas.getFluid(50))
                .circuitMeta(5)
                .duration(1200).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(10000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(7200))
                .fluidOutputs(LiquidArgon.getFluid(1000))
                .fluidOutputs(MagicGas.getFluid(50))
                .circuitMeta(6)
                .duration(1000).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(10000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(7200))
                .fluidOutputs(MagicGas.getFluid(50))
                .circuitMeta(7)
                .duration(800).EUt(VA[MV]).buildAndRegister();


        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(10000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(7200))
                .fluidOutputs(LiquidArgon.getFluid(1000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 750))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 750))
                .fluidOutputs(LiquidRadon.getFluid(250))
                .fluidOutputs(LiquidHydrogen.getFluid(50))
                .fluidOutputs(LiquidNitrogen.getFluid(50))
                .fluidOutputs(MagicGas.getFluid(50))
                .circuitMeta(1)
                .duration(200).EUt(VA[EV]).buildAndRegister();

        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(10000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(7200))
                .fluidOutputs(LiquidArgon.getFluid(1000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 750))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 750))
                .fluidOutputs(LiquidRadon.getFluid(250))
                .fluidOutputs(LiquidHydrogen.getFluid(50))
                .fluidOutputs(MagicGas.getFluid(50))
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
                .fluidInputs(LiquidMarsAir.getFluid(10000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(7200))
                .fluidOutputs(LiquidArgon.getFluid(1000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 750))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 750))
                .fluidOutputs(MagicGas.getFluid(50))
                .circuitMeta(4)
                .duration(140).EUt(VA[EV]).buildAndRegister();

        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(10000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(7200))
                .fluidOutputs(LiquidArgon.getFluid(1000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 7500))
                .fluidOutputs(MagicGas.getFluid(50))
                .circuitMeta(5)
                .duration(120).EUt(VA[EV]).buildAndRegister();

        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(10000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(7200))
                .fluidOutputs(LiquidArgon.getFluid(1000))
                .fluidOutputs(MagicGas.getFluid(50))
                .circuitMeta(6)
                .duration(100).EUt(VA[EV]).buildAndRegister();

        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(10000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(7200))
                .fluidOutputs(MagicGas.getFluid(50))
                .circuitMeta(7)
                .duration(800).EUt(VA[EV]).buildAndRegister();


        ///
        VACUUM_RECIPES.recipeBuilder()
                .fluidInputs(BetAir.getFluid(4000))
                .fluidOutputs(LiquidBetAir.getFluid(4000))
                .duration(80).EUt(VA[MV]).buildAndRegister();

        LOW_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidBetAir.getFluid(10000))
                .fluidOutputs(Methane.getFluid(7200))
                .fluidOutputs(CoalGas.getFluid(1000))
                .fluidOutputs(HydrogenSulfide.getFluid(750))
                .fluidOutputs(SulfurDioxide.getFluid(750))
                .fluidOutputs(Helium3.getFluid(250))
                .fluidOutputs(Neon.getFluid(50))
                .fluidOutputs(Radon.getFluid(50))
                .duration(200).EUt(VA[MV]).buildAndRegister();

        HIGH_PRESSURE_CRYOGENIC_DISTILLATION.recipeBuilder()
                .fluidInputs(LiquidBetAir.getFluid(10000))
                .fluidOutputs(Methane.getFluid(7200))
                .fluidOutputs(CoalGas.getFluid(1000))
                .fluidOutputs(HydrogenSulfide.getFluid(750))
                .fluidOutputs(SulfurDioxide.getFluid(750))
                .fluidOutputs(Helium3.getFluid(250))
                .fluidOutputs(Neon.getFluid(50))
                .fluidOutputs(Radon.getFluid(50))
                .duration(20).EUt(VA[EV]).buildAndRegister();
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
                .fluidInputs(material2.getFluid(1000))
                .fluidOutputs(material1.getFluid(1000))
                .duration((int) (200*Math.pow(2,i+1))).EUt(VA[3+i]).buildAndRegister();

        FLUID_HEATER_RECIPES.recipeBuilder()
                .fluidInputs(material1.getFluid(1000))
                .fluidOutputs(material2.getFluid(1000))
                .duration((int) (20*Math.pow(2,i))).EUt(VA[i]).buildAndRegister();
    }
}
