package keqing.gtqtcore.api.capability.impl;

import gregtech.api.capability.IHeatingCoil;
import gregtech.api.capability.IMultiblockController;
import gregtech.api.capability.IMultipleRecipeMaps;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.logic.OverclockingLogic;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.util.GTTransferUtils;
import gregtech.common.ConfigHolder;
import keqing.gtqtcore.api.capability.chemical_plant.ChemicalPlantProperties;
import keqing.gtqtcore.api.metaileentity.multiblock.RecipeMapLaserMultiblockController;
import net.minecraft.util.Tuple;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MultiblockLaserRecipeLogic extends AbstractRecipeLogic {

    protected int lastRecipeIndex = 0;
    protected IItemHandlerModifiable currentDistinctInputBus;
    protected List<IItemHandlerModifiable> invalidatedInputList = new ArrayList();
    RecipeMapLaserMultiblockController tileEntity;

    public MultiblockLaserRecipeLogic(RecipeMapLaserMultiblockController tileEntity) {
        super(tileEntity, tileEntity.recipeMap);
        this.tileEntity = tileEntity;
    }

    @Override
    public int getParallelLimit() {
        return tileEntity.getParallelLimit();
    }

    @Override
    protected void updateRecipeProgress() {
        if (this.canRecipeProgress) {
            if (drawEnergy(recipeEUt)) {
                if (++this.progressTime > this.maxProgressTime) {
                    this.completeRecipe();
                }
            } else this.decreaseProgress();
        }
    }
    @Override
    public void setMaxProgress(int maxProgress) {
        super.setMaxProgress((int) Math.floor(maxProgress*(1-this.tileEntity.getTier()*0.05)));
    }
    protected boolean drawEnergy(int recipeEUt) {
        return this.tileEntity.LaserToEu() >= recipeEUt;
    }

    @Override
    public long getMaxVoltage() {
        return this.tileEntity.LaserToEu();
    }

    @Override
    protected long getEnergyInputPerSecond() {
        return this.tileEntity.LaserToEu();
    }

    @Override
    protected long getEnergyStored() {
        return this.tileEntity.LaserToEu();
    }

    @Override
    protected long getEnergyCapacity() {
        return this.tileEntity.LaserToEu();
    }

    @Override
    protected boolean drawEnergy(int recipeEUt, boolean simulate) {
        return this.tileEntity.LaserToEu() >= recipeEUt;
    }

    @Override
    public long getMaximumOverclockVoltage() {
        return this.getMaxVoltage();
    }

    @Override
    protected long getMaxParallelVoltage() {
        return this.getMaximumOverclockVoltage();
    }

    public void update() {
    }

    public void updateWorkable() {
        super.update();
    }

    protected boolean canProgressRecipe() {
        return super.canProgressRecipe() && !((IMultiblockController) this.metaTileEntity).isStructureObstructed();
    }

    public void invalidate() {
        this.previousRecipe = null;
        this.progressTime = 0;
        this.maxProgressTime = 0;
        this.recipeEUt = 0;
        this.fluidOutputs = null;
        this.itemOutputs = null;
        this.lastRecipeIndex = 0;
        this.parallelRecipesPerformed = 0;
        this.isOutputsFull = false;
        this.invalidInputsForRecipes = false;
        this.invalidatedInputList.clear();
        this.setActive(false);
    }

    public void onDistinctChanged() {
        this.lastRecipeIndex = 0;
    }

    protected IItemHandlerModifiable getInputInventory() {
        RecipeMapLaserMultiblockController controller = (RecipeMapLaserMultiblockController) this.metaTileEntity;
        return controller.getInputInventory();
    }

    protected List<IItemHandlerModifiable> getInputBuses() {
        RecipeMapLaserMultiblockController controller = (RecipeMapLaserMultiblockController) this.metaTileEntity;
        return controller.getAbilities(MultiblockAbility.IMPORT_ITEMS);
    }

    protected IItemHandlerModifiable getOutputInventory() {
        RecipeMapLaserMultiblockController controller = (RecipeMapLaserMultiblockController) this.metaTileEntity;
        return controller.getOutputInventory();
    }

    protected IMultipleTankHandler getInputTank() {
        RecipeMapLaserMultiblockController controller = (RecipeMapLaserMultiblockController) this.metaTileEntity;
        return controller.getInputFluidInventory();
    }

    protected IMultipleTankHandler getOutputTank() {
        RecipeMapLaserMultiblockController controller = (RecipeMapLaserMultiblockController) this.metaTileEntity;
        return controller.getOutputFluidInventory();
    }

    @Override
    protected boolean canWorkWithInputs() {
        MultiblockWithDisplayBase controller = (MultiblockWithDisplayBase) metaTileEntity;
        if (controller instanceof RecipeMapLaserMultiblockController distinctController) {

            if (distinctController.canBeDistinct() && distinctController.isDistinct() && getInputInventory().getSlots() > 0) {
                boolean canWork = false;
                if (invalidatedInputList.isEmpty()) {
                    return true;
                }
                if (!metaTileEntity.getNotifiedFluidInputList().isEmpty()) {
                    canWork = true;
                    invalidatedInputList.clear();
                    metaTileEntity.getNotifiedFluidInputList().clear();
                    metaTileEntity.getNotifiedItemInputList().clear();
                } else {
                    Iterator<IItemHandlerModifiable> notifiedIter = metaTileEntity.getNotifiedItemInputList().iterator();
                    while (notifiedIter.hasNext()) {
                        IItemHandlerModifiable bus = notifiedIter.next();
                        Iterator<IItemHandlerModifiable> invalidatedIter = invalidatedInputList.iterator();
                        while (invalidatedIter.hasNext()) {
                            IItemHandler invalidatedHandler = invalidatedIter.next();
                            if (invalidatedHandler instanceof ItemHandlerList) {
                                for (IItemHandler ih : ((ItemHandlerList) invalidatedHandler).getBackingHandlers()) {
                                    if (ih == bus) {
                                        canWork = true;
                                        invalidatedIter.remove();
                                        break;
                                    }
                                }
                            } else if (invalidatedHandler == bus) {
                                canWork = true;
                                invalidatedIter.remove();
                            }
                        }
                        notifiedIter.remove();
                    }
                }
                ArrayList<IItemHandler> flattenedHandlers = new ArrayList<>();
                for (IItemHandler ih : getInputBuses()) {
                    if (ih instanceof ItemHandlerList) {
                        flattenedHandlers.addAll(((ItemHandlerList) ih).getBackingHandlers());
                    }
                    flattenedHandlers.add(ih);
                }

                if (!invalidatedInputList.containsAll(flattenedHandlers)) {
                    canWork = true;
                }
                return canWork;
            }
        }
        return super.canWorkWithInputs();
    }

    protected void trySearchNewRecipe() {
        MultiblockWithDisplayBase controller = (MultiblockWithDisplayBase) this.metaTileEntity;
        if (!ConfigHolder.machines.enableMaintenance || !controller.hasMaintenanceMechanics() || controller.getNumMaintenanceProblems() <= 5) {
            if (controller instanceof RecipeMapLaserMultiblockController distinctController) {
                if (distinctController.canBeDistinct() && distinctController.isDistinct() && this.getInputInventory().getSlots() > 0) {
                    this.trySearchNewRecipeDistinct();
                    return;
                }
            }
            this.trySearchNewRecipeCombined();
        }
    }

    protected void trySearchNewRecipeCombined() {
        long maxVoltage = this.getMaxVoltage();
        IItemHandlerModifiable importInventory = this.getInputInventory();
        IMultipleTankHandler importFluids = this.getInputTank();
        Recipe currentRecipe;
        if (this.checkPreviousRecipe()) {
            currentRecipe = this.previousRecipe;
        } else {
            currentRecipe = this.findRecipe(maxVoltage, importInventory, importFluids);
        }

        if (currentRecipe != null) {
            this.previousRecipe = currentRecipe;
        }

        this.invalidInputsForRecipes = currentRecipe == null;
        if (currentRecipe != null && this.checkRecipe(currentRecipe)) {
            this.prepareRecipe(currentRecipe);
        }
    }

    public boolean prepareRecipe(Recipe recipe) {
        return this.prepareRecipe(recipe, this.getInputInventory(), this.getInputTank());
    }

    public boolean prepareRecipe(Recipe recipe, IItemHandlerModifiable inputInventory, IMultipleTankHandler inputFluidInventory) {
        recipe = Recipe.trimRecipeOutputs(recipe, this.getRecipeMap(), this.metaTileEntity.getItemOutputLimit(), this.metaTileEntity.getFluidOutputLimit());

        recipe = this.findParallelRecipe(recipe, inputInventory, inputFluidInventory, this.getOutputInventory(), this.getOutputTank(), this.getMaxParallelVoltage(), this.getParallelLimit());
        if (recipe != null && this.setupAndConsumeRecipeInputs(recipe, inputInventory, inputFluidInventory)) {
            this.setupRecipe(recipe);
            return true;
        } else {
            return false;
        }
    }

    protected boolean setupAndConsumeRecipeInputs(Recipe recipe, IItemHandlerModifiable importInventory, IMultipleTankHandler importFluids) {
        this.overclockResults = this.calculateOverclock(recipe);
        this.modifyOverclockPost(this.overclockResults, recipe.getRecipePropertyStorage());

        IItemHandlerModifiable exportInventory = this.getOutputInventory();
        IMultipleTankHandler exportFluids = this.getOutputTank();
        if (!this.metaTileEntity.canVoidRecipeItemOutputs() && !GTTransferUtils.addItemsToItemHandler(exportInventory, true, recipe.getAllItemOutputs())) {
            this.isOutputsFull = true;
            return false;
        } else if (!this.metaTileEntity.canVoidRecipeFluidOutputs() && !GTTransferUtils.addFluidsToFluidHandler(exportFluids, true, recipe.getAllFluidOutputs())) {
            this.isOutputsFull = true;
            return false;
        } else {
            this.isOutputsFull = false;
            if (recipe.matches(true, importInventory, importFluids)) {
                this.metaTileEntity.addNotifiedInput(importInventory);
                return true;
            } else {
                return false;
            }
        }
    }

    protected boolean hasEnoughPower(int[] resultOverclock) {
        int recipeEUt = resultOverclock[0];
        if (recipeEUt >= 0) {
            return this.getEnergyStored() >= (long) recipeEUt << 3;
        } else {
            return this.getEnergyStored() - (long) recipeEUt <= this.getEnergyCapacity();
        }
    }

    protected void trySearchNewRecipeDistinct() {
        long maxVoltage = this.getMaxVoltage();
        List<IItemHandlerModifiable> importInventory = this.getInputBuses();
        IMultipleTankHandler importFluids = this.getInputTank();
        Recipe currentRecipe;
        if (this.checkPreviousRecipeDistinct(importInventory.get(this.lastRecipeIndex)) && this.checkRecipe(this.previousRecipe)) {
            currentRecipe = this.previousRecipe;
            this.currentDistinctInputBus = importInventory.get(this.lastRecipeIndex);
            if (this.prepareRecipeDistinct(currentRecipe)) {
                return;
            }
        }

        for (int i = 0; i < importInventory.size(); ++i) {
            IItemHandlerModifiable bus = importInventory.get(i);
            if (!this.invalidatedInputList.contains(bus)) {
                currentRecipe = this.findRecipe(maxVoltage, bus, importFluids);
                if (currentRecipe != null && this.checkRecipe(currentRecipe)) {
                    this.previousRecipe = currentRecipe;
                    this.currentDistinctInputBus = bus;
                    if (this.prepareRecipeDistinct(currentRecipe)) {
                        this.lastRecipeIndex = i;
                        return;
                    }
                }

                if (currentRecipe == null) {
                    this.invalidatedInputList.add(bus);
                }
            }
        }

    }

    public void invalidateInputs() {
        MultiblockWithDisplayBase controller = (MultiblockWithDisplayBase) this.metaTileEntity;
        RecipeMapLaserMultiblockController distinctController = (RecipeMapLaserMultiblockController) controller;
        if (distinctController.canBeDistinct() && distinctController.isDistinct() && this.getInputInventory().getSlots() > 0) {
            this.invalidatedInputList.add(this.currentDistinctInputBus);
        } else {
            super.invalidateInputs();
        }

    }

    protected boolean checkPreviousRecipeDistinct(IItemHandlerModifiable previousBus) {
        return this.previousRecipe != null && this.previousRecipe.matches(false, previousBus, this.getInputTank());
    }

    protected boolean prepareRecipeDistinct(Recipe recipe) {
        recipe = Recipe.trimRecipeOutputs(recipe, this.getRecipeMap(), this.metaTileEntity.getItemOutputLimit(), this.metaTileEntity.getFluidOutputLimit());
        recipe = this.findParallelRecipe(recipe, this.currentDistinctInputBus, this.getInputTank(), this.getOutputInventory(), this.getOutputTank(), this.getMaxParallelVoltage(), this.getParallelLimit());
        if (recipe != null && this.setupAndConsumeRecipeInputs(recipe, this.currentDistinctInputBus)) {
            this.setupRecipe(recipe);
            return true;
        } else {
            return false;
        }
    }

    protected void modifyOverclockPre(int[] values, IRecipePropertyStorage storage) {
        super.modifyOverclockPre(values, storage);
        Tuple<Integer, Double> maintenanceValues = this.getMaintenanceValues();
        if (maintenanceValues.getSecond() != 1.0) {
            values[1] = (int) Math.round((double) values[1] * maintenanceValues.getSecond());
        }

    }

    protected void modifyOverclockPost(int[] overclockResults, IRecipePropertyStorage storage) {
        super.modifyOverclockPost(overclockResults, storage);
        Tuple<Integer, Double> maintenanceValues = this.getMaintenanceValues();
        if (maintenanceValues.getFirst() > 0) {
            overclockResults[1] = (int) ((double) overclockResults[1] * (1.0 + 0.1 * (double) maintenanceValues.getFirst()));
        }

    }

    protected Tuple<Integer, Double> getMaintenanceValues() {
        MultiblockWithDisplayBase displayBase = this.metaTileEntity instanceof MultiblockWithDisplayBase ? (MultiblockWithDisplayBase) this.metaTileEntity : null;
        int numMaintenanceProblems = displayBase != null && displayBase.hasMaintenanceMechanics() && ConfigHolder.machines.enableMaintenance ? displayBase.getNumMaintenanceProblems() : 0;
        double durationMultiplier = 1.0;
        if (displayBase != null && displayBase.hasMaintenanceMechanics() && ConfigHolder.machines.enableMaintenance) {
            durationMultiplier = displayBase.getMaintenanceDurationMultiplier();
        }

        return new Tuple(numMaintenanceProblems, durationMultiplier);
    }

    public boolean checkRecipe(Recipe recipe) {
        RecipeMapLaserMultiblockController controller = (RecipeMapLaserMultiblockController) this.metaTileEntity;
        if (controller.checkRecipe(recipe, false)) {
            controller.checkRecipe(recipe, true);
            return super.checkRecipe(recipe);
        } else {
            return false;
        }
    }

    protected void completeRecipe() {
        this.performMufflerOperations();
        super.completeRecipe();
    }

    protected void performMufflerOperations() {
        MetaTileEntity var2 = this.metaTileEntity;
        if (var2 instanceof MultiblockWithDisplayBase controller) {
            if (controller.hasMufflerMechanics()) {
                if (this.parallelRecipesPerformed > 1) {
                    controller.outputRecoveryItems(this.parallelRecipesPerformed);
                } else {
                    controller.outputRecoveryItems();
                }
            }
        }

    }

    public RecipeMap<?> getRecipeMap() {
        return this.metaTileEntity instanceof IMultipleRecipeMaps ? ((IMultipleRecipeMaps) this.metaTileEntity).getCurrentRecipeMap() : super.getRecipeMap();
    }


}


