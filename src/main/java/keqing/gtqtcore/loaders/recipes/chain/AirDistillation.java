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
        vaccum(LiquidCarbonDioxide,CarbonDioxide,1);
        vaccum(LiquidNitrogen,Nitrogen,2);
        vaccum(LiquidHydrogen,Hydrogen,2);
        vaccum(LiquidArgon,Argon,3);
        vaccum(LiquidRadon,Radon,3);
        vaccum(LiquidHelium,Helium,4);

        ///
        VACUUM_RECIPES.recipeBuilder()
                .fluidInputs(MarsAir.getFluid(4000))
                .fluidOutputs(LiquidMarsAir.getFluid(4000))
                .duration(80).EUt(VA[MV]).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(LiquidMarsAir.getFluid(10000))
                .fluidOutputs(LiquidCarbonDioxide.getFluid(7200))
                .fluidOutputs(LiquidArgon.getFluid(1000))
                .fluidOutputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 750))
                .fluidOutputs(Helium.getFluid(FluidStorageKeys.LIQUID, 750))
                .fluidOutputs(LiquidRadon.getFluid(250))
                .fluidOutputs(LiquidHydrogen.getFluid(50))
                .fluidOutputs(LiquidNitrogen.getFluid(50))
                .fluidOutputs(MagicGas.getFluid(50))
                .duration(2000).EUt(VA[MV]).buildAndRegister();

        VACUUM_RECIPES.recipeBuilder()
                .fluidInputs(BetAir.getFluid(4000))
                .fluidOutputs(LiquidBetAir.getFluid(4000))
                .duration(80).EUt(VA[MV]).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(LiquidBetAir.getFluid(10000))
                .fluidOutputs(Methane.getFluid(7200))
                .fluidOutputs(CoalGas.getFluid(1000))
                .fluidOutputs(HydrogenSulfide.getFluid(750))
                .fluidOutputs(SulfurDioxide.getFluid(750))
                .fluidOutputs(Helium3.getFluid(250))
                .fluidOutputs(Neon.getFluid(50))
                .fluidOutputs(Radon.getFluid(50))
                .duration(2000).EUt(VA[MV]).buildAndRegister();

        ///
        VACUUM_RECIPES.recipeBuilder()
                .fluidInputs(BeneathAir.getFluid(4000))
                .fluidOutputs(LiquidBeneathAir.getFluid(4000))
                .duration(80).EUt(VA[MV]).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(LiquidBeneathAir.getFluid(10000))
                .fluidOutputs(Oxygen.getFluid(7200))
                .fluidOutputs(CoalGas.getFluid(1000))
                .fluidOutputs(HydrogenSulfide.getFluid(750))
                .fluidOutputs(SulfurDioxide.getFluid(750))
                .fluidOutputs(Helium3.getFluid(250))
                .fluidOutputs(Neon.getFluid(50))
                .fluidOutputs(Radon.getFluid(50))
                .duration(2000).EUt(VA[MV]).buildAndRegister();
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
