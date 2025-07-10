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
        vaccum(Ozone, 3);
        vaccum(MarsAir, 5);
        vaccum(BeneathAir, 5);
        vaccum(BetAir, 5);


        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(MarsAir.getFluid(FluidStorageKeys.LIQUID, 10000))
                .fluidOutputs(CarbonDioxide.getFluid(FluidStorageKeys.LIQUID, 2500))
                .fluidOutputs(Argon.getFluid(FluidStorageKeys.LIQUID, 1000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 750))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 750))
                .fluidOutputs(Radon.getFluid(FluidStorageKeys.LIQUID, 250))
                .fluidOutputs(Hydrogen.getFluid(FluidStorageKeys.LIQUID, 50))
                .fluidOutputs(Nitrogen.getFluid(FluidStorageKeys.LIQUID, 50))
                .fluidOutputs(MagicGas.getFluid(50))
                .duration(2000).EUt(VA[MV]).buildAndRegister();


        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(BetAir.getFluid(FluidStorageKeys.LIQUID, 10000))
                .fluidOutputs(Methane.getFluid(7200))
                .fluidOutputs(CoalGas.getFluid(1000))
                .fluidOutputs(HydrogenSulfide.getFluid(750))
                .fluidOutputs(SulfurDioxide.getFluid(750))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 250))
                .fluidOutputs(Neon.getFluid(50))
                .fluidOutputs(Radon.getFluid(FluidStorageKeys.LIQUID, 50))
                .duration(2000).EUt(VA[MV]).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(BeneathAir.getFluid(FluidStorageKeys.LIQUID, 10000))
                .fluidOutputs(Oxygen.getFluid(FluidStorageKeys.LIQUID, 7200))
                .fluidOutputs(CoalGas.getFluid(1000))
                .fluidOutputs(HydrogenSulfide.getFluid(750))
                .fluidOutputs(SulfurDioxide.getFluid(750))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 250))
                .fluidOutputs(Neon.getFluid(50))
                .fluidOutputs(Radon.getFluid(FluidStorageKeys.LIQUID, 50))
                .duration(2000).EUt(VA[MV]).buildAndRegister();
    }

    private static void vaccum(Material material, int i) {
        VACUUM_RECIPES.recipeBuilder()
                .fluidInputs(material.getFluid(FluidStorageKeys.GAS, 1000))
                .fluidOutputs(material.getFluid(FluidStorageKeys.LIQUID, 1000))
                .duration((int) (100*Math.pow(2,i+1))).EUt(VA[i]).buildAndRegister();

        FLUID_HEATER_RECIPES.recipeBuilder()
                .fluidInputs(material.getFluid(FluidStorageKeys.LIQUID, 4000))
                .fluidOutputs(material.getFluid(FluidStorageKeys.GAS, 4000))
                .duration((int) (20*Math.pow(2,i))).EUt(VA[i]).buildAndRegister();
    }
}
