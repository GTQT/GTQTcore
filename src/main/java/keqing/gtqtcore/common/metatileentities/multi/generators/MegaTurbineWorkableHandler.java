package keqing.gtqtcore.common.metatileentities.multi.generators;

import gregtech.api.GTValues;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockFuelRecipeLogic;
import gregtech.api.metatileentity.multiblock.FuelMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import keqing.gtqtcore.api.capability.IReinforcedRotorHolder;
import keqing.gtqtcore.api.metaileentity.multiblock.ITurbineMode;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class MegaTurbineWorkableHandler extends MultiblockFuelRecipeLogic {

    private final int BASE_EU_OUTPUT;
    private int excessVoltage;

    public MegaTurbineWorkableHandler(RecipeMapMultiblockController tileEntity, int tier) {
        super(tileEntity);
        this.BASE_EU_OUTPUT = (int) (GTValues.V[tier] * 2 * 16);
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
        if (previousRecipe == null) {
            Recipe recipe = findRecipe(Integer.MAX_VALUE, getInputInventory(), getInputTank());

            return recipe == null ? null : getInputTank().drain(new FluidStack(recipe.getFluidInputs().get(0).getInputFluidStack().getFluid(), Integer.MAX_VALUE), false);
        }
        FluidStack fuelStack = previousRecipe.getFluidInputs().get(0).getInputFluidStack();
        return getInputTank().drain(new FluidStack(fuelStack.getFluid(), Integer.MAX_VALUE), false);
    }

    @Override
    public long getMaxVoltage() {
        List<IReinforcedRotorHolder> rotorHolders = ((ITurbineMode) metaTileEntity).getRotorHolders();
        if (rotorHolders != null && rotorHolders.get(0).hasRotor()) {
            if (((ITurbineMode) metaTileEntity).getMode() == 0)
                return (long) BASE_EU_OUTPUT * rotorHolders.get(0).getTotalPower() / 100;
            return (BASE_EU_OUTPUT * 3L) * rotorHolders.get(0).getTotalPower() / 100;
        }
        return 0;
    }

    @Override
    protected long boostProduction(long production) {
        List<IReinforcedRotorHolder> rotorHolders = ((ITurbineMode) metaTileEntity).getRotorHolders();
        if (rotorHolders != null) {
            IReinforcedRotorHolder rotorHolder = rotorHolders.get(0);
            if (rotorHolder.hasRotor()) {
                int maxSpeed = rotorHolder.getMaxRotorHolderSpeed();
                int currentSpeed = rotorHolder.getRotorSpeed();
                if (currentSpeed >= maxSpeed)
                    return production;
                return (long) (production * Math.pow(1.0 * currentSpeed / maxSpeed, 2));
            }
        }
        return 0;
    }

    private int getParallel(Recipe recipe,
                            double totalHolderEfficiencyCoefficient,
                            int turbineMaxVoltage) {
        return MathHelper.ceil((double) (turbineMaxVoltage - this.excessVoltage) / ((double) Math.abs(recipe.getEUt()) * totalHolderEfficiencyCoefficient));
    }

    private boolean canDoRecipeWithParallel(Recipe recipe) {
        List<IReinforcedRotorHolder> rotorHolders = ((ITurbineMode) metaTileEntity).getRotorHolders();

        if (rotorHolders != null && rotorHolders.get(0).hasRotor()) {
            double totalHolderEfficiencyCoefficient = (double) rotorHolders.get(0).getTotalEfficiency() / 100.0;
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

    @Nullable
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
        List<IReinforcedRotorHolder> rotorHolders = ((ITurbineMode) metaTileEntity).getRotorHolders();
        if (rotorHolders == null || !rotorHolders.get(0).hasRotor())
            return false;

        int turbineMaxVoltage = (int) getMaxVoltage();
        FluidStack recipeFluidStack = recipe.getFluidInputs().get(0).getInputFluidStack();
        int parallel = 0;

        if (excessVoltage >= turbineMaxVoltage) {
            excessVoltage -= turbineMaxVoltage;
        } else {
            double holderEfficiency = rotorHolders.get(0).getTotalEfficiency() / 100.0;
            //  Get the amount of parallel required to match the desired output voltage
            parallel = MathHelper.ceil((turbineMaxVoltage - excessVoltage) /
                    (Math.abs(recipe.getEUt()) * holderEfficiency));

            // Null check fluid here, since it can return null on first join into world or first form
            FluidStack inputFluid = getInputFluidStack();
            if (inputFluid == null || getInputFluidStack().amount < recipeFluidStack.amount * parallel) {
                return false;
            }

            //  This is necessary to prevent over-consumption of fuel
            excessVoltage += (int) (parallel * Math.abs(recipe.getEUt()) * holderEfficiency - turbineMaxVoltage);
        }

        //  Rebuild the recipe and adjust voltage to match the turbine
        //  Thanks my friend to help me fix a little problem.
        RecipeBuilder<?> recipeBuilder = getRecipeMap().recipeBuilder();
        recipeBuilder.append(recipe, parallel, false)
                .EUt(turbineMaxVoltage);
        applyParallelBonus(recipeBuilder);
        recipe = recipeBuilder.build().getResult();

        if (recipe != null && setupAndConsumeRecipeInputs(recipe, getInputInventory())) {
            setupRecipe(recipe);
            return true;
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

