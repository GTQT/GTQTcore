package keqing.gtqtcore.api.capability.impl;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.IRotorHolder;
import gregtech.api.capability.impl.PrimitiveRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.common.metatileentities.multi.electric.generator.MetaTileEntityLargeTurbine;
import keqing.gtqtcore.api.capability.IHeatExchanger;
import keqing.gtqtcore.api.metaileentity.multiblock.NoEnergyMultiblockController;
import keqing.gtqtcore.api.recipes.properties.FlowRateProperty;
import keqing.gtqtcore.api.utils.GTQTMathUtil;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeatExchangerLogic  extends NoEnergyMultiblockRecipeLogic {
    public HeatExchangerLogic(NoEnergyMultiblockController tileEntity) {
        super(tileEntity, tileEntity.recipeMap);
    }

    @Override
    protected boolean checkPreviousRecipe() {
        if (this.previousRecipe == null) {
            return false;
        } else {
            List<FluidStack> fluidStackList = new ArrayList<>(GTUtility.fluidHandlerToList(getInputTank()));
            fluidStackList.add(Materials.DistilledWater.getFluid(Integer.MAX_VALUE));
            return (long) this.previousRecipe.getEUt() <= this.getMaxVoltage() && this.previousRecipe.matches(false, Collections.emptyList(), fluidStackList);
        }
    }

    @Override
    protected void trySearchNewRecipeCombined() {
        long maxVoltage = getMaxVoltage();
        List<FluidStack> fluidStackList = new ArrayList<>(GTUtility.fluidHandlerToList(getInputTank()));
        fluidStackList.add(Materials.DistilledWater.getFluid(Integer.MAX_VALUE));
        Recipe currentRecipe;

        // see if the last recipe we used still works
        if (checkPreviousRecipe()) {
            currentRecipe = this.previousRecipe;
            // If there is no active recipe, then we need to find one.
        } else {
            currentRecipe = getRecipeMap().findRecipe(Integer.MAX_VALUE, Collections.emptyList(), fluidStackList);
        }
        // If a recipe was found, then inputs were valid. Cache found recipe.
        if (currentRecipe != null) {
            this.previousRecipe = currentRecipe;
        }
        this.invalidInputsForRecipes = (currentRecipe == null);

        // proceed if we have a usable recipe.
        if (currentRecipe != null && checkRecipe(currentRecipe)) {
            prepareRecipe(currentRecipe);
        }
    }

    @Override
    protected boolean prepareRecipe(Recipe recipe) {
        FluidStack recipeFluidStack = recipe.getFluidInputs().get(1).getInputFluidStack();
        FluidStack inputFluidStack = getInputFluidStack(recipeFluidStack);
        int maxInletFlow = (int) (Math.ceil(0.4 + 0.006 * ((IHeatExchanger)metaTileEntity).getThrottle() * ((IHeatExchanger)metaTileEntity).getParallel()));
        int threshold = (int) Math.ceil((double) (recipe.getProperty(FlowRateProperty.getInstance(), 0) * ((IHeatExchanger) metaTileEntity).getThrottle()) / 100);

        if (inputFluidStack != null) {
            int amount = GTQTMathUtil.clamp(inputFluidStack.amount, 0, maxInletFlow);
            if (amount >= threshold) {
                return setRecipe(recipe, amount, 1);
            } else {
                return setRecipe(recipe, amount, 0);
            }
        }
        return false;
    }

    private boolean setRecipe(Recipe recipe, int amount, int i) {
        Recipe tRecipe =  getRecipeMap().recipeBuilder().append(recipe, 1, false)
                .clearFluidOutputs()
                .fluidOutputs(recipe.getFluidOutputs().get(i))
                .fluidOutputs(recipe.getFluidOutputs().get(2))
                .build().getResult();
        RecipeBuilder<?> recipeBuilder = getRecipeMap().recipeBuilder();
        recipeBuilder.append(tRecipe, amount, false);
        applyParallelBonus(recipeBuilder);
        recipe = recipeBuilder.build().getResult();
        if (recipe != null && setupAndConsumeRecipeInputs(recipe, getInputInventory())) {
            setupRecipe(recipe);
            return true;
        } else {
            metaTileEntity.doExplosion(6);
            return false;
        }
    }

    public FluidStack getInputFluidStack(FluidStack fluidStack) {
        return getInputTank().drain(new FluidStack(fluidStack.getFluid(), Integer.MAX_VALUE), false);
    }
}
