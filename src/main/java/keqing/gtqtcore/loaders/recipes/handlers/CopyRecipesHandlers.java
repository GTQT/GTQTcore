package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.GTValues;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.unification.material.Materials;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collection;
import java.util.List;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.DistilledWater;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.CW_LASER_ENGRAVER_RECIPES;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.PLASMA_CONDENSER_RECIPES;

public class CopyRecipesHandlers {
    public static void init() {
        //等离子冷凝
        Collection<Recipe> plasmaRecipes = RecipeMaps.PLASMA_GENERATOR_FUELS.getRecipeList();
        for (Recipe recipe : plasmaRecipes) {
            List<GTRecipeInput> fluidInputs = recipe.getFluidInputs();
            List<FluidStack> fluidOutputs = recipe.getFluidOutputs();

            int baseDuration = recipe.getDuration();

            PLASMA_CONDENSER_RECIPES.recipeBuilder()
                    .fluidInputs(fluidInputs.get(0).copyWithAmount(1000))
                    .fluidOutputs(new FluidStack(fluidOutputs.get(0).getFluid(),1000))
                    .duration(baseDuration)
                    .EUt(VA[ZPM])
                    .buildAndRegister();
        }
        //
        Collection<Recipe> cutterRecipes = RecipeMaps.CUTTER_RECIPES.getRecipeList();
        for (Recipe recipe : cutterRecipes) {
            List<GTRecipeInput> itemInputs = recipe.getInputs();
            List<ItemStack> itemOutputs = recipe.getOutputs();
            int EUt = recipe.getEUt() * 4;
            int baseDuration;

            if (EUt <= V[IV]) baseDuration = recipe.getDuration() * 4;
            else baseDuration = recipe.getDuration();

            CW_LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .fluidInputs(DistilledWater.getFluid(100))
                    .inputIngredients(itemInputs)
                    .outputs(itemOutputs)
                    .duration(baseDuration)
                    .EUt(EUt)
                    .circuitMeta(6)
                    .buildAndRegister();
        }
        //燃料电池
        Collection<Recipe> oilsRecipes = RecipeMaps.COMBUSTION_GENERATOR_FUELS.getRecipeList();
        for (Recipe recipe : oilsRecipes) {

            if(recipe.getEUt()>V[EV])continue;

            List<GTRecipeInput> fluidInputs = recipe.getFluidInputs();
            int EUt = (int) GTValues.V[GTValues.LV];
            int baseDuration = 4 * recipe.getDuration() * recipe.getEUt() / EUt;

            if(baseDuration>4000)continue;

            GTQTcoreRecipeMaps.FUEL_CELL.recipeBuilder()
                    .fluidInputs(fluidInputs)
                    .fluidInputs(Materials.Air.getFluid(baseDuration * 4))
                    .duration(baseDuration)
                    .EUt(EUt)
                    .buildAndRegister();

            GTQTcoreRecipeMaps.FUEL_CELL.recipeBuilder()
                    .fluidInputs(fluidInputs)
                    .fluidInputs(Materials.Oxygen.getFluid(FluidStorageKeys.GAS, baseDuration * 2))
                    .duration(baseDuration * 2)
                    .EUt(EUt)
                    .buildAndRegister();

            GTQTcoreRecipeMaps.FUEL_CELL.recipeBuilder()
                    .fluidInputs(fluidInputs)
                    .fluidInputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, baseDuration))
                    .duration(baseDuration * 4)
                    .EUt(EUt)
                    .buildAndRegister();

        }
        Collection<Recipe> gasRecipes = RecipeMaps.GAS_TURBINE_FUELS.getRecipeList();
        for (Recipe recipe : gasRecipes) {

            if(recipe.getEUt()>V[EV])continue;

            List<GTRecipeInput> fluidInputs = recipe.getFluidInputs();
            int EUt = (int) GTValues.V[GTValues.LV];
            int baseDuration = 4 * recipe.getDuration() * recipe.getEUt() / EUt;

            if(baseDuration>4000)continue;

            GTQTcoreRecipeMaps.FUEL_CELL.recipeBuilder()
                    .fluidInputs(fluidInputs)
                    .fluidInputs(Materials.Air.getFluid(baseDuration))
                    .duration(baseDuration)
                    .EUt(EUt)
                    .buildAndRegister();
            GTQTcoreRecipeMaps.FUEL_CELL.recipeBuilder()
                    .fluidInputs(fluidInputs)
                    .fluidInputs(Materials.Oxygen.getFluid(FluidStorageKeys.GAS, (int) Math.ceil(baseDuration * 1.5)))
                    .duration((int) Math.floor(baseDuration * 1.5))
                    .EUt(EUt)
                    .buildAndRegister();
            GTQTcoreRecipeMaps.FUEL_CELL.recipeBuilder()
                    .fluidInputs(fluidInputs)
                    .fluidInputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, baseDuration * 8))
                    .duration(baseDuration * 2)
                    .EUt(EUt)
                    .buildAndRegister();

        }
    }
}
