package keqing.gtqtcore.common.metatileentities.multi.generators;

import gregtech.api.GTValues;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.IRotorHolder;
import gregtech.api.capability.impl.MultiblockFuelRecipeLogic;
import gregtech.api.metatileentity.multiblock.FuelMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import keqing.gtqtcore.api.capability.IReinforcedRotorHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LargeTurbineWorkableHandler extends MultiblockFuelRecipeLogic {

    private final int BASE_EU_OUTPUT;
    private int excessVoltage;

    public LargeTurbineWorkableHandler(RecipeMapMultiblockController metaTileEntity, int tier) {
        super(metaTileEntity);
        this.BASE_EU_OUTPUT = (int) GTValues.V[tier] * 2;
    }

    @Override
    protected void updateRecipeProgress() {
        if (canRecipeProgress) {
            // turbines can void energy
            drawEnergy(recipeEUt, false);
            //as recipe starts with progress on 1 this has to be > only not => to compensate for it
            if (++progressTime > maxProgressTime) {
                completeRecipe();
            }
        }
    }

    public FluidStack getInputFluidStack() {
        // Previous Recipe is always null on first world load, so try to acquire a new recipe
        if (this.previousRecipe == null) {
            Recipe recipe = super.findRecipe(Integer.MAX_VALUE, this.getInputInventory(), this.getInputTank());
            return recipe == null ? null : getInputTank().drain(new FluidStack(recipe.getFluidInputs().get(0).getInputFluidStack().getFluid(), Integer.MAX_VALUE), false);
        } else {
            FluidStack fuelStack = this.previousRecipe.getFluidInputs().get(0).getInputFluidStack();
            return this.getInputTank().drain(new FluidStack(fuelStack.getFluid(), Integer.MAX_VALUE), false);
        }
    }

    @Override
    public long getMaxVoltage() {
        IReinforcedRotorHolder rotorHolder = ((MetaTileEntityLargeTurbine) this.metaTileEntity).getRotorHolder();
        return rotorHolder != null && rotorHolder.hasRotor() ? (long) this.BASE_EU_OUTPUT * (long) rotorHolder.getTotalPower() / 100L : 0L;
    }

    @Override
    protected long boostProduction(long production) {
        IReinforcedRotorHolder rotorHolder = ((MetaTileEntityLargeTurbine) metaTileEntity).getRotorHolder();
        if (rotorHolder != null && rotorHolder.hasRotor()) {
            int maxSpeed = rotorHolder.getMaxRotorHolderSpeed();
            int currentSpeed = rotorHolder.getRotorSpeed();
            return currentSpeed >= maxSpeed ? production : (long) ((double) production * Math.pow(1.0 * currentSpeed / (double) maxSpeed, 2.0));
        } else {
            return 0L;
        }
    }

    private int getParallel(Recipe recipe, double totalHolderEfficiencyCoefficient, long turbineMaxVoltage) {
        return MathHelper.ceil((turbineMaxVoltage - this.excessVoltage) /
                (recipe.getEUt() * totalHolderEfficiencyCoefficient));
    }

    private boolean canDoRecipeWithParallel(Recipe recipe) {
        IReinforcedRotorHolder rotorHolder = ((MetaTileEntityLargeTurbine) this.metaTileEntity).getRotorHolder();
        if (rotorHolder != null && rotorHolder.hasRotor()) {
            double totalHolderEfficiencyCoefficient = (double) rotorHolder.getTotalEfficiency() / 100.0;
            int turbineMaxVoltage = (int) this.getMaxVoltage();
            int parallel = this.getParallel(recipe, totalHolderEfficiencyCoefficient, turbineMaxVoltage);
            FluidStack recipeFluidStack = recipe.getFluidInputs().get(0).getInputFluidStack();
            FluidStack inputFluid = this.getInputTank().drain(new FluidStack(recipeFluidStack.getFluid(), Integer.MAX_VALUE), false);
            return inputFluid != null && inputFluid.amount >= recipeFluidStack.amount * parallel;
        } else {
            return false;
        }
    }

    @Override
    protected boolean checkPreviousRecipe() {
        return super.checkPreviousRecipe() && this.canDoRecipeWithParallel(this.previousRecipe);
    }

    @Override
    protected Recipe findRecipe(long maxVoltage,
                                IItemHandlerModifiable inputs, IMultipleTankHandler fluidInputs) {
        RecipeMap<?> map = this.getRecipeMap();
        if (map != null && this.isRecipeMapValid(map)) {
            List<ItemStack> items = GTUtility.itemHandlerToList(inputs).stream()
                    .filter((s) -> !s.isEmpty())
                    .collect(Collectors.toList());
            List<FluidStack> fluids = GTUtility.fluidHandlerToList(fluidInputs).stream()
                    .filter((f) -> f != null && f.amount != 0)
                    .collect(Collectors.toList());
            return map.find(items, fluids, (recipe) -> {
                if ((long) recipe.getEUt() > maxVoltage) {
                    return false;
                } else {
                    return recipe.matches(false, inputs, fluidInputs) && this.canDoRecipeWithParallel(recipe);
                }
            });
        } else {
            return null;
        }
    }

    @Override
    public boolean prepareRecipe(Recipe recipe) {
        IRotorHolder rotorHolder = ((gregtech.common.metatileentities.multi.electric.generator.MetaTileEntityLargeTurbine) metaTileEntity).getRotorHolder();
        if (rotorHolder == null || !rotorHolder.hasRotor())
            return false;

        long turbineMaxVoltage = getMaxVoltage();
        FluidStack recipeFluidStack = recipe.getFluidInputs().get(0).getInputFluidStack();
        int parallel = 0;

        if (this.excessVoltage >= turbineMaxVoltage) {
            this.excessVoltage -= turbineMaxVoltage;
        } else {
            double holderEfficiency = rotorHolder.getTotalEfficiency() / 100.0;
            // get the amount of parallel required to match the desired output voltage
            parallel = getParallel(recipe, holderEfficiency, turbineMaxVoltage);

            // Null check fluid here, since it can return null on first join into world or first form
            FluidStack inputFluid = getInputFluidStack();
            if (inputFluid == null || getInputFluidStack().amount < recipeFluidStack.amount * parallel) {
                return false;
            }

            // this is necessary to prevent over-consumption of fuel
            this.excessVoltage += (long) (parallel * recipe.getEUt() * holderEfficiency - turbineMaxVoltage);
        }

        // rebuild the recipe and adjust voltage to match the turbine
        RecipeBuilder<?> recipeBuilder = getRecipeMap().recipeBuilder();
        recipeBuilder.append(recipe, parallel, false)
                .EUt(turbineMaxVoltage);
        applyParallelBonus(recipeBuilder);
        recipe = recipeBuilder.build().getResult();

        if (recipe != null) {
            recipe = setupAndConsumeRecipeInputs(recipe, getInputInventory());
            if (recipe != null) {
                setupRecipe(recipe);
                return true;
            }
        }
        return false;
    }
    @Override
    public void invalidate() {
        excessVoltage = 0;
        super.invalidate();
    }

    public void updateTanks() {
        FuelMultiblockController controller = (FuelMultiblockController) this.metaTileEntity;
        List<IFluidHandler> tanks = controller.getNotifiedFluidInputList();
        for (IFluidTank tank : controller.getAbilities(MultiblockAbility.IMPORT_FLUIDS)) {
            tanks.add((IFluidHandler) tank);
        }
    }
}
