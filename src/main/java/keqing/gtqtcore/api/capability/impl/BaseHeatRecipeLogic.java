package keqing.gtqtcore.api.capability.impl;

import gregtech.api.capability.IMultiblockController;
import gregtech.api.capability.IMultipleRecipeMaps;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.common.ConfigHolder;
import keqing.gtqtcore.api.metaileentity.multiblock.RecipeMapHeatMultiblockController;
import keqing.gtqtcore.api.recipes.properties.HeatProperty;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static gregtech.api.GTValues.LV;
import static gregtech.api.GTValues.V;
import static gregtech.api.recipes.logic.OverclockingLogic.standardOC;

public class BaseHeatRecipeLogic extends AbstractRecipeLogic {

    // Used for distinct mode
    protected int lastRecipeIndex = 0;
    protected IItemHandlerModifiable currentDistinctInputBus;
    protected List<IItemHandlerModifiable> invalidatedInputList = new ArrayList<>();
    RecipeMapHeatMultiblockController tileEntity;

    public BaseHeatRecipeLogic(RecipeMapHeatMultiblockController tileEntity, RecipeMap<?> recipeMap) {
        super(tileEntity, recipeMap);
    }

    @Override
    protected long getEnergyInputPerSecond() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected long getEnergyStored() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected long getEnergyCapacity() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected boolean drawEnergy(long recipeEUt, boolean simulate) {
        return true; // spoof energy being drawn
    }

    @Override
    public long getMaxVoltage() {
        return V[LV];
    }

    @Override
    public void setMaxProgress(int maxProgress) {
        double speedBonus = Math.min(previousRecipe.getProperty(HeatProperty.getInstance(), 0)/tileEntity.getHeat(),1);
        super.setMaxProgress((int) (maxProgress*speedBonus));
    }

    @Override
    public long getMaximumOverclockVoltage() {
        return V[LV];
    }

    /**
     * Used to reset cached values in the Recipe Logic on structure deform
     */
    public void invalidate() {
        previousRecipe = null;
        progressTime = 0;
        maxProgressTime = 0;
        fluidOutputs = null;
        itemOutputs = null;
        lastRecipeIndex = 0;
        parallelRecipesPerformed = 0;
        isOutputsFull = false;
        invalidInputsForRecipes = false;
        invalidatedInputList.clear();
        setActive(false); // this marks dirty for us
    }

    @Override
    public void update() {
    }

    protected void updateRecipeProgress() {
        if (this.canRecipeProgress) {
            if (++this.progressTime > this.maxProgressTime) {
                this.completeRecipe();
            }
        }
    }

    public void updateWorkable() {
        World world = this.getMetaTileEntity().getWorld();
        if (world != null && !world.isRemote) {
            if (this.workingEnabled) {
                if (this.getMetaTileEntity().getOffsetTimer() % 20L == 0L) {
                    this.canRecipeProgress = this.canProgressRecipe();
                }

                if (this.progressTime > 0) {
                    this.updateRecipeProgress();
                }

                if (this.progressTime == 0 && this.shouldSearchForRecipes()) {
                    this.trySearchNewRecipe();
                }
            }

            if (this.wasActiveAndNeedsUpdate) {
                this.wasActiveAndNeedsUpdate = false;
                this.setActive(false);
            }
        }
    }

    @Override
    protected boolean canProgressRecipe() {
        return super.canProgressRecipe() && !((IMultiblockController) metaTileEntity).isStructureObstructed();
    }

    public void onDistinctChanged() {
        this.lastRecipeIndex = 0;
    }

    @Override
    protected IItemHandlerModifiable getInputInventory() {
        RecipeMapHeatMultiblockController controller = (RecipeMapHeatMultiblockController) metaTileEntity;
        return controller.getInputInventory();
    }

    // Used for distinct bus recipe checking
    protected List<IItemHandlerModifiable> getInputBuses() {
        RecipeMapHeatMultiblockController controller = (RecipeMapHeatMultiblockController) metaTileEntity;
        return controller.getAbilities(MultiblockAbility.IMPORT_ITEMS);
    }

    @Override
    protected IItemHandlerModifiable getOutputInventory() {
        RecipeMapHeatMultiblockController controller = (RecipeMapHeatMultiblockController) metaTileEntity;
        return controller.getOutputInventory();
    }

    @Override
    protected IMultipleTankHandler getInputTank() {
        RecipeMapHeatMultiblockController controller = (RecipeMapHeatMultiblockController) metaTileEntity;
        return controller.getInputFluidInventory();
    }

    @Override
    protected IMultipleTankHandler getOutputTank() {
        RecipeMapHeatMultiblockController controller = (RecipeMapHeatMultiblockController) metaTileEntity;
        return controller.getOutputFluidInventory();
    }

    @Override
    protected boolean canWorkWithInputs() {
        MultiblockWithDisplayBase controller = (MultiblockWithDisplayBase) metaTileEntity;
        if (controller instanceof RecipeMapHeatMultiblockController distinctController) {

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

    @Override
    protected void trySearchNewRecipe() {
        // do not run recipes when there are more than 5 maintenance problems
        // Maintenance can apply to all multiblocks, so cast to a base multiblock class
        MultiblockWithDisplayBase controller = (MultiblockWithDisplayBase) metaTileEntity;
        if (ConfigHolder.machines.enableMaintenance && controller.hasMaintenanceMechanics() && controller.getNumMaintenanceProblems() > 5) {
            return;
        }

        // Distinct buses only apply to some multiblocks, so check the controller against a lower class
        if (controller instanceof RecipeMapHeatMultiblockController distinctController) {

            if (distinctController.canBeDistinct() && distinctController.isDistinct() && getInputInventory().getSlots() > 0) {
                trySearchNewRecipeDistinct();
                return;
            }
        }

        trySearchNewRecipeCombined();
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

    protected void trySearchNewRecipeDistinct() {
        long maxVoltage = getMaxVoltage();
        Recipe currentRecipe;
        List<IItemHandlerModifiable> importInventory = getInputBuses();
        IMultipleTankHandler importFluids = getInputTank();

        // Our caching implementation
        // This guarantees that if we get a recipe cache hit, our efficiency is no different from other machines
        if (checkPreviousRecipeDistinct(importInventory.get(lastRecipeIndex)) && checkRecipe(previousRecipe)) {
            currentRecipe = previousRecipe;
            currentDistinctInputBus = importInventory.get(lastRecipeIndex);
            if (prepareRecipeDistinct(currentRecipe)) {
                // No need to cache the previous recipe here, as it is not null and matched by the current recipe,
                // so it will always be the same
                return;
            }
        }

        // On a cache miss, our efficiency is much worse, as it will check
        // each bus individually instead of the combined inventory all at once.
        for (int i = 0; i < importInventory.size(); i++) {
            IItemHandlerModifiable bus = importInventory.get(i);
            // Skip this bus if no recipe was found last time
            if (invalidatedInputList.contains(bus)) {
                continue;
            }
            // Look for a new recipe after a cache miss
            currentRecipe = findRecipe(maxVoltage, bus, importFluids);
            // Cache the current recipe, if one is found
            if (currentRecipe != null && checkRecipe(currentRecipe)) {
                this.previousRecipe = currentRecipe;
                currentDistinctInputBus = bus;
                if (prepareRecipeDistinct(currentRecipe)) {
                    lastRecipeIndex = i;
                    return;
                }
            }
            if (currentRecipe == null) {
                //no valid recipe found, invalidate this bus
                invalidatedInputList.add(bus);
            }
        }
    }

    @Override
    public void invalidateInputs() {
        MultiblockWithDisplayBase controller = (MultiblockWithDisplayBase) metaTileEntity;
        RecipeMapHeatMultiblockController distinctController = (RecipeMapHeatMultiblockController) controller;
        if (distinctController.canBeDistinct() && distinctController.isDistinct() && getInputInventory().getSlots() > 0) {
            invalidatedInputList.add(currentDistinctInputBus);
        } else {
            super.invalidateInputs();
        }
    }

    protected boolean checkPreviousRecipeDistinct(IItemHandlerModifiable previousBus) {
        return previousRecipe != null && previousRecipe.matches(false, previousBus, getInputTank());
    }

    protected boolean prepareRecipeDistinct(Recipe recipe) {

        recipe = Recipe.trimRecipeOutputs(recipe, getRecipeMap(), metaTileEntity.getItemOutputLimit(), metaTileEntity.getFluidOutputLimit());

        recipe = findParallelRecipe(
                recipe,
                currentDistinctInputBus,
                getInputTank(),
                getOutputInventory(),
                getOutputTank(),
                getMaxParallelVoltage(),
                getParallelLimit()

        );


        if (recipe != null) {
            recipe = setupAndConsumeRecipeInputs(recipe, getInputInventory());
            if (recipe != null) {
                setupRecipe(recipe);
                return true;
            }
        }

        return false;
    }


    @Nonnull
    protected Tuple<Integer, Double> getMaintenanceValues() {
        MultiblockWithDisplayBase displayBase = this.metaTileEntity instanceof MultiblockWithDisplayBase ? (MultiblockWithDisplayBase) metaTileEntity : null;
        int numMaintenanceProblems = displayBase == null || !displayBase.hasMaintenanceMechanics() || !ConfigHolder.machines.enableMaintenance ? 0 : displayBase.getNumMaintenanceProblems();
        double durationMultiplier = 1.0D;
        if (displayBase != null && displayBase.hasMaintenanceMechanics() && ConfigHolder.machines.enableMaintenance) {
            durationMultiplier = displayBase.getMaintenanceDurationMultiplier();
        }
        return new Tuple<>(numMaintenanceProblems, durationMultiplier);
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe) {
        RecipeMapHeatMultiblockController controller = (RecipeMapHeatMultiblockController) metaTileEntity;
        if (controller.checkRecipe(recipe, false)) {
            controller.checkRecipe(recipe, true);
            return super.checkRecipe(recipe);
        }
        return false;
    }

    @Override
    protected void completeRecipe() {
        performMufflerOperations();
        super.completeRecipe();
    }

    protected void performMufflerOperations() {
        if (metaTileEntity instanceof MultiblockWithDisplayBase controller) {
            // output muffler items
            if (controller.hasMufflerMechanics()) {
                if (parallelRecipesPerformed > 1) {
                    controller.outputRecoveryItems(parallelRecipesPerformed);
                } else {
                    controller.outputRecoveryItems();
                }
            }
        }
    }

    @Nullable
    @Override
    public RecipeMap<?> getRecipeMap() {
        // if the multiblock has more than one RecipeMap, return the currently selected one
        if (metaTileEntity instanceof IMultipleRecipeMaps)
            return ((IMultipleRecipeMaps) metaTileEntity).getCurrentRecipeMap();
        return super.getRecipeMap();
    }

    @Override
    public long getInfoProviderEUt() {
        return 0;
    }
}
