package keqing.gtqtcore.loaders.recipes.handlers;

import gregicality.multiblocks.api.recipes.GCYMRecipeMaps;
import gregtech.api.GTValues;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.recipes.ingredients.GTRecipeOreInput;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.utils.GTQTUtil.*;

public class CopyRecipesHandlers {
    public static void init() {
        //合金
        /*
        Collection<Recipe> alloyRecipes = GCYMRecipeMaps.ALLOY_BLAST_RECIPES.getRecipeList();
        for (Recipe recipe : alloyRecipes) {
            List<GTRecipeInput> fluidInputs = recipe.getFluidInputs();
            List<FluidStack> fluidOutputs = recipe.getFluidOutputs();
            List<GTRecipeInput> itemInputs = recipe.getFluidInputs();
            int EUt = (int) (recipe.getEUt()*1.5);
            int baseDuration = recipe.getDuration()/10;
            int temp=recipe.getProperty(TemperatureProperty.getInstance(), 0);

            // generate builder
            RecipeBuilder<?> builder;

            builder = CW_LASER_ALLOY_RECIPES.recipeBuilder()
                    .blastFurnaceTemp(temp)
                    .duration(baseDuration)
                    .EUt(EUt)
                    .fluidOutputs(fluidOutputs)
                    .fluidInputs(fluidInputs);

            for (GTRecipeInput itemInput : itemInputs) {
                ItemStack[] inputStacks = itemInput.getInputStacks();
                if (inputStacks == null || inputStacks.length == 0) {
                    continue; // 跳过无效的输入
                }

                String oreName = getOreNameByStack(Arrays.asList(inputStacks).get(0));
                if (oreName.contains("null")) {
                    continue;
                }

                if (oreName.contains("dust")) {
                    ItemStack production = getItemStacksFromOreNames(oreName.replace("dust", "power"));
                    builder.inputs(setStack(production, itemInput.getAmount()));
                }
            }

            builder.buildAndRegister();
        }

         */
        //搅拌
        Collection<Recipe> mixerRecipes = RecipeMaps.MIXER_RECIPES.getRecipeList();
        for (Recipe recipe : mixerRecipes) {
            List<GTRecipeInput> fluidInputs = recipe.getFluidInputs();
            List<FluidStack> fluidOutputs = recipe.getFluidOutputs();
            List<GTRecipeInput> itemInputs = recipe.getInputs();
            List<ItemStack> itemOutputs = recipe.getOutputs();
            int EUt = (int) (recipe.getEUt()*1.5);
            int baseDuration = recipe.getDuration()/2;

            LARGE_MIXER_RECIPES.recipeBuilder()
                    .duration(baseDuration)
                    .EUt(EUt)
                    .fluidInputs(fluidInputs)
                    .inputIngredients(itemInputs)
                    .outputs(itemOutputs)
                    .fluidOutputs(fluidOutputs)
                    .buildAndRegister();
        }
        //固化
        Collection<Recipe> solidificationRecipes = RecipeMaps.FLUID_SOLIDFICATION_RECIPES.getRecipeList();
        for (Recipe recipe : solidificationRecipes) {
            List<GTRecipeInput> fluidInputs = recipe.getFluidInputs();
            List<GTRecipeInput> itemInputs = recipe.getInputs();
            List<ItemStack> itemOutputs = recipe.getOutputs();
            int EUt = recipe.getEUt();
            int baseDuration= (int) (recipe.getDuration()*0.8);

            TD_PRINT_RECIPES.recipeBuilder()
                    .duration(baseDuration)
                    .EUt(EUt<=V[LV]?EUt:EUt/4)
                    .fluidInputs(fluidInputs)
                    .inputIngredients(itemInputs)
                    .outputs(itemOutputs)
                    .CWUt(CWT[(GTUtility.getTierByVoltage(recipe.getEUt()))])
                    .buildAndRegister();
        }

        //蒸馏塔
        Collection<Recipe> DistillationRecipes = RecipeMaps.DISTILLATION_RECIPES.getRecipeList();
        for (Recipe recipe : DistillationRecipes) {
            int EUt = recipe.getEUt();
            if(EUt>=512)continue;

            List<GTRecipeInput> fluidInputs = recipe.getFluidInputs();
            List<FluidStack> fluidOutputs = recipe.getFluidOutputs();

            int baseDuration= recipe.getDuration()*4;

            // generate builder
            RecipeBuilder<?> builder;

            builder = DISTILLATION_KETTLE.recipeBuilder()
                    .duration(baseDuration)
                    .Heat(300+EUt);

            if(fluidInputs!=null)builder.fluidInputs(fluidInputs);
            if(fluidOutputs!=null) {
                if (fluidOutputs.size() > 9) continue;
                builder.fluidOutputs(fluidOutputs);
            }

            builder.buildAndRegister();
        }
        //电解槽
        Collection<Recipe> electrolyzerRecipes = RecipeMaps.ELECTROLYZER_RECIPES.getRecipeList();
        for (Recipe recipe : electrolyzerRecipes) {
            List<GTRecipeInput> fluidInputs = recipe.getFluidInputs();
            List<FluidStack> fluidOutputs = recipe.getFluidOutputs();
            List<GTRecipeInput> itemInputs = recipe.getInputs();
            List<ItemStack> itemOutputs = recipe.getOutputs();
            int EUt = recipe.getEUt() * 4;
            int baseDuration= recipe.getDuration()/2;
            int tier=Math.min(5, GTUtility.getTierByVoltage(recipe.getEUt())+1);
            // generate builder
            RecipeBuilder<?> builder;

            builder = ELECTROBATH.recipeBuilder()
                    .circuitMeta(2)
                    .duration(baseDuration)
                    .tier(tier)
                    .EUt(EUt);

            if(fluidInputs!=null)builder.fluidInputs(fluidInputs);
            if(fluidOutputs!=null)builder.fluidOutputs(fluidOutputs);
            if(itemInputs!=null)builder.inputIngredients(itemInputs);
            if(itemOutputs!=null)builder.outputs(itemOutputs);


            builder.buildAndRegister();
        }


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

            List<GTRecipeInput> fluidInputs = recipe.getFluidInputs();
            if(fluidInputs.get(0).getInputFluidStack().getFluid()!=Lubricant.getFluid()) continue;

            List<GTRecipeInput> itemInputs = recipe.getInputs();
            List<ItemStack> itemOutputs = recipe.getOutputs();

            int EUt = recipe.getEUt() * 4;
            int baseDuration;

            if (EUt <= V[IV]) baseDuration = recipe.getDuration() * 4;
            else baseDuration = recipe.getDuration();

            CW_LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .fluidInputs(fluidInputs)
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
                    .fluidInputs(Materials.Air.getFluid(baseDuration))
                    .duration(baseDuration)
                    .EUt(EUt)
                    .buildAndRegister();

            GTQTcoreRecipeMaps.FUEL_CELL.recipeBuilder()
                    .fluidInputs(fluidInputs)
                    .fluidInputs(Materials.Oxygen.getFluid(FluidStorageKeys.GAS, baseDuration))
                    .duration((int) Math.floor(baseDuration * 1.5))
                    .EUt(EUt)
                    .buildAndRegister();

            GTQTcoreRecipeMaps.FUEL_CELL.recipeBuilder()
                    .fluidInputs(fluidInputs)
                    .fluidInputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, baseDuration))
                    .duration(baseDuration * 2)
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
                    .fluidInputs(Materials.Oxygen.getFluid(FluidStorageKeys.GAS,baseDuration))
                    .duration((int) Math.floor(baseDuration * 1.5))
                    .EUt(EUt)
                    .buildAndRegister();

            GTQTcoreRecipeMaps.FUEL_CELL.recipeBuilder()
                    .fluidInputs(fluidInputs)
                    .fluidInputs(Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, baseDuration))
                    .duration(baseDuration * 2)
                    .EUt(EUt)
                    .buildAndRegister();

        }
    }
}
