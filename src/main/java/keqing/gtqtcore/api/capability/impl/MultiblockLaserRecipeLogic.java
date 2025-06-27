package keqing.gtqtcore.api.capability.impl;

import gregtech.api.capability.*;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.logic.OCParams;
import gregtech.api.recipes.logic.OCResult;
import gregtech.api.recipes.properties.RecipePropertyStorage;
import gregtech.common.ConfigHolder;
import keqing.gtqtcore.api.metatileentity.multiblock.RecipeMapLaserMultiblockController;
import net.minecraft.util.Tuple;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static gregtech.api.recipes.logic.OverclockingLogic.subTickParallelOC;

public class MultiblockLaserRecipeLogic extends AbstractRecipeLogic {

    protected final Set<IItemHandlerModifiable> invalidatedInputList = new HashSet<>();
    // Used for distinct mode
    protected int lastRecipeIndex = 0;
    protected IItemHandlerModifiable currentDistinctInputBus;
    RecipeMapLaserMultiblockController tileEntity;
    private boolean hasDualInputCache;

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
        super.setMaxProgress((int) Math.floor(maxProgress * (1 - this.tileEntity.getTier() * 0.05)));
    }

    protected boolean drawEnergy(long recipeEUt) {
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
    protected boolean drawEnergy(long recipeEUt, boolean simulate) {
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

    @Override
    public void update() {
    }

    public void updateWorkable() {
        super.update();
    }

    @Override
    protected boolean canProgressRecipe() {
        return super.canProgressRecipe() && !((IMultiblockController) metaTileEntity).isStructureObstructed();
    }

    /**
     * Used to reset cached values in the Recipe Logic on structure deform
     */
    @Override
    public void invalidate() {
        super.invalidate();
        lastRecipeIndex = 0;
        invalidatedInputList.clear();
    }

    public void onDistinctChanged() {
        this.lastRecipeIndex = 0;
    }

    @Override
    protected IItemHandlerModifiable getInputInventory() {
        RecipeMapLaserMultiblockController controller = (RecipeMapLaserMultiblockController) metaTileEntity;
        return controller.getInputInventory();
    }

    // Used for distinct bus recipe checking
    protected List<IItemHandlerModifiable> getInputBuses() {
        RecipeMapLaserMultiblockController controller = (RecipeMapLaserMultiblockController) metaTileEntity;
        List<IItemHandlerModifiable> inputItems = new ArrayList<>(
                controller.getAbilities(MultiblockAbility.IMPORT_ITEMS));
        inputItems.addAll(controller.getAbilities(MultiblockAbility.DUAL_IMPORT));
        return inputItems;
    }

    @Override
    protected IItemHandlerModifiable getOutputInventory() {
        RecipeMapLaserMultiblockController controller = (RecipeMapLaserMultiblockController) metaTileEntity;
        return controller.getOutputInventory();
    }

    @Override
    protected IMultipleTankHandler getInputTank() {
        RecipeMapLaserMultiblockController controller = (RecipeMapLaserMultiblockController) metaTileEntity;
        return controller.getInputFluidInventory();
    }

    /**
     * Overload of {@link #getInputTank()} to gather extra fluid tanks that could exist in a distinct item handler (such
     * as a {@link DualHandler})
     *
     * @param items Handler to gather fluid tanks from
     * @return a new FluidTankList with extra fluid tanks on top of the existing fluid tanks
     */
    protected IMultipleTankHandler getInputTank(IItemHandler items) {
        var tanks = new ArrayList<>(getInputTank().getFluidTanks());
        if (items instanceof IMultipleTankHandler tankHandler) {
            tanks.addAll(tankHandler.getFluidTanks());
        }
        return new FluidTankList(getInputTank().allowSameFluidFill(), tanks);
    }

    protected IMultipleTankHandler getDistinctInputTank(IItemHandler items) {
        var tanks = new ArrayList<>(getInputTank().getFluidTanks());
        tanks.clear();
        if (items instanceof IMultipleTankHandler tankHandler) {
            tanks.addAll(tankHandler.getFluidTanks());
        }
        return new FluidTankList(getInputTank().allowSameFluidFill(), tanks);
    }

    @Override
    protected IMultipleTankHandler getOutputTank() {
        RecipeMapLaserMultiblockController controller = (RecipeMapLaserMultiblockController) metaTileEntity;
        return controller.getOutputFluidInventory();
    }

    @Override
    protected boolean canWorkWithInputs() {
        MultiblockWithDisplayBase controller = (MultiblockWithDisplayBase) metaTileEntity;
        if (!(controller instanceof RecipeMapLaserMultiblockController distinctController) ||
                !distinctController.canBeDistinct() ||
                !distinctController.isDistinct() ||
                getInputInventory().getSlots() == 0) {
            return super.canWorkWithInputs();
        }

        // 当无效列表为空时直接通过检查
        if (invalidatedInputList.isEmpty()) {
            return true;
        }

        boolean canWork;

        // 处理流体输入通知
        if (!metaTileEntity.getNotifiedFluidInputList().isEmpty()) {
            canWork = true;
            clearNotificationLists();
        } else {
            // 处理物品输入通知
            canWork = processItemNotifications();
        }

        // 扁平化输入总线并清理无效列表中的DualHandler
        List<IItemHandler> flattenedHandlers = flattenInputBuses();
        removeDualHandlersFromInvalidated();

        // 检查无效列表是否包含所有必要处理器
        if (!new HashSet<>(invalidatedInputList).containsAll(flattenedHandlers)) {
            canWork = true;
        }

        return canWork;
    }

    // 提取方法：清空通知列表
    private void clearNotificationLists() {
        invalidatedInputList.clear();
        metaTileEntity.getNotifiedFluidInputList().clear();
        metaTileEntity.getNotifiedItemInputList().clear();
    }

    // 提取方法：处理物品输入通知
    private boolean processItemNotifications() {
        boolean updated = false;
        Iterator<IItemHandlerModifiable> notifiedIter = metaTileEntity.getNotifiedItemInputList().iterator();

        while (notifiedIter.hasNext()) {
            IItemHandlerModifiable bus = notifiedIter.next();
            Iterator<IItemHandlerModifiable> invalidatedIter = invalidatedInputList.iterator();

            while (invalidatedIter.hasNext()) {
                IItemHandler handler = invalidatedIter.next();
                if (isHandlerMatch(handler, bus)) {
                    invalidatedIter.remove();
                    updated = true;
                }
            }
            notifiedIter.remove();
        }
        return updated;
    }

    // 辅助方法：处理器匹配检查
    private boolean isHandlerMatch(IItemHandler handler, IItemHandlerModifiable bus) {
        if (handler instanceof ItemHandlerList) {
            return ((ItemHandlerList) handler).getBackingHandlers().contains(bus);
        }
        return handler == bus;
    }

    // 提取方法：扁平化输入总线
    private List<IItemHandler> flattenInputBuses() {
        return getInputBuses().stream()
                .flatMap(ih -> {
                    if (ih instanceof ItemHandlerList) {
                        return ((ItemHandlerList) ih).getBackingHandlers().stream();
                    }
                    return Stream.of(ih);
                })
                .collect(Collectors.toList());
    }

    // 提取方法：清理DualHandler
    private void removeDualHandlersFromInvalidated() {
        invalidatedInputList.removeIf(handler -> handler instanceof DualHandler);
    }

    @Override
    protected void trySearchNewRecipe() {
        // do not run recipes when there are more than 5 maintenance problems
        // Maintenance can apply to all multiblocks, so cast to a base multiblock class
        MultiblockWithDisplayBase controller = (MultiblockWithDisplayBase) metaTileEntity;
        if (ConfigHolder.machines.enableMaintenance && controller.hasMaintenanceMechanics() &&
                controller.getNumMaintenanceProblems() > 5) {
            return;
        }

        // Distinct buses only apply to some multiblocks, so check the controller against a lower class
        if (controller instanceof RecipeMapLaserMultiblockController distinctController) {

            if (distinctController.canBeDistinct() && distinctController.isDistinct() &&
                    getInputInventory().getSlots() > 0) {
                trySearchNewRecipeDistinct();
                return;
            }
        }

        trySearchNewRecipeCombined();
    }

    protected void trySearchNewRecipeCombined() {
        super.trySearchNewRecipe();
    }

    protected void trySearchNewRecipeDistinct() {
        long maxVoltage = getMaxVoltage();
        List<IItemHandlerModifiable> importInventory = getInputBuses();

        // 优先尝试缓存命中
        if (attemptCacheHit(importInventory)) {
            return;
        }

        // 更新双输入缓存状态
        updateHasDualInputCache();

        // 遍历总线寻找配方，优先检查上次成功总线
        findRecipeInBuses(importInventory, maxVoltage);
    }

    // 其他方法保持不变，以下为修改后的方法

    private void updateHasDualInputCache() {
        MultiblockWithDisplayBase controller = (MultiblockWithDisplayBase) metaTileEntity;
        hasDualInputCache = !controller.getAbilities(MultiblockAbility.DUAL_IMPORT).isEmpty();
    }

    private boolean hasDualInput() {
        return hasDualInputCache;
    }

    private void findRecipeInBuses(List<IItemHandlerModifiable> importInventory, long maxVoltage) {
        // 优先检查上次成功的总线
        if (lastRecipeIndex >= 0 && lastRecipeIndex < importInventory.size()) {
            IItemHandlerModifiable bus = importInventory.get(lastRecipeIndex);
            if (!isBusInvalid(bus)) {
                Recipe recipe = findRecipeForBus(bus, maxVoltage);
                if (handleFoundRecipe(recipe, bus, lastRecipeIndex)) {
                    return;
                }
            }
        }

        // 遍历剩余总线
        for (int i = 0; i < importInventory.size(); i++) {
            if (i == lastRecipeIndex) continue; // 跳过已检查的总线

            IItemHandlerModifiable bus = importInventory.get(i);
            if (isBusInvalid(bus)) continue;

            Recipe recipe = findRecipeForBus(bus, maxVoltage);
            if (handleFoundRecipe(recipe, bus, i)) return;
        }
    }

    private boolean checkPreviousRecipeDistinct(IItemHandlerModifiable previousBus) {
        boolean dualInput = hasDualInput();
        IMultipleTankHandler tank = dualInput ? getDistinctInputTank(previousBus) : getInputTank(previousBus);
        return previousRecipe != null && previousRecipe.matches(false, previousBus, tank);
    }

    protected boolean checkLatestRecipeDistinct(IItemHandlerModifiable previousBus) {
        boolean dualInput = hasDualInput();
        IMultipleTankHandler tank = dualInput ? getDistinctInputTank(previousBus) : getInputTank(previousBus);

        // 逆序遍历最新配方（跳过最后一个）
        for (int i = latestRecipes.size() - 2; i >= 0; i--) { // 从倒数第二个开始
            Recipe recipe = latestRecipes.get(i);
            if (recipe != null && recipe.matches(false, previousBus, tank)) {
                return checkRecipe(recipe) && prepareRecipeDistinct(recipe);
            }
        }
        return false;
    }

    @Override
    public boolean prepareRecipe(Recipe recipe) {
        ((RecipeMapLaserMultiblockController) metaTileEntity).refreshAllBeforeConsumption();
        return super.prepareRecipe(recipe);
    }

    protected boolean prepareRecipeDistinct(Recipe recipe) {
        ((RecipeMapLaserMultiblockController) metaTileEntity).refreshAllBeforeConsumption();
        recipe = Recipe.trimRecipeOutputs(recipe, getRecipeMap(), metaTileEntity.getItemOutputLimit(),
                metaTileEntity.getFluidOutputLimit());
        boolean dualInput = hasDualInput();
        IMultipleTankHandler inputTank =
                dualInput ? getDistinctInputTank(currentDistinctInputBus) : getInputTank(currentDistinctInputBus);

        recipe = findParallelRecipe(
                recipe,
                currentDistinctInputBus,
                inputTank,
                getOutputInventory(),
                getOutputTank(),
                getMaxParallelVoltage(),
                getParallelLimit());

        if (recipe != null) {
            recipe = setupAndConsumeRecipeInputs(recipe, currentDistinctInputBus, inputTank);
            if (recipe != null) {
                setupRecipe(recipe);
                return true;
            }
        }
        return false;
    }

    // 提取方法：尝试缓存命中
    private boolean attemptCacheHit(List<IItemHandlerModifiable> importInventory) {
        //原先的方法
        if (canUseCachedRecipe(importInventory) && prepareRecipeDistinct(previousRecipe))
            return true;

        //临近搜索系统
        return canUseLatestRecipe(importInventory);
    }

    // 提取方法：检查缓存有效性
    private boolean canUseCachedRecipe(List<IItemHandlerModifiable> importInventory) {
        return previousRecipe != null
                && lastRecipeIndex < importInventory.size()
                && checkPreviousRecipeDistinct(importInventory.get(lastRecipeIndex))
                && checkRecipe(previousRecipe);
    }

    private boolean canUseLatestRecipe(List<IItemHandlerModifiable> importInventory) {
        return previousRecipe != null
                && lastRecipeIndex < importInventory.size()
                && checkLatestRecipeDistinct(importInventory.get(lastRecipeIndex));
    }

    // 提取方法：检查总线有效性
    private boolean isBusInvalid(IItemHandlerModifiable bus) {
        return invalidatedInputList.contains(bus);
    }

    // 提取方法：获取对应总线配方
    private Recipe findRecipeForBus(IItemHandlerModifiable bus, long maxVoltage) {
        return hasDualInput()
                ? findRecipe(maxVoltage, bus, getDistinctInputTank(bus))
                : findRecipe(maxVoltage, bus, getInputTank(bus));
    }

    // 提取方法：处理找到的配方
    private boolean handleFoundRecipe(Recipe recipe, IItemHandlerModifiable bus, int index) {
        if (recipe != null && checkRecipe(recipe)) {
            updateRecipeCache(recipe, bus, index);
            return prepareRecipeDistinct(recipe);
        } else {
            markBusAsInvalid(bus);
            return false;
        }
    }

    // 提取方法：更新配方缓存
    private void updateRecipeCache(Recipe recipe, IItemHandlerModifiable bus, int index) {
        previousRecipe = recipe;
        addToPreviousRecipes(recipe);
        currentDistinctInputBus = bus;
        lastRecipeIndex = index;
    }

    // 提取方法：标记无效总线
    private void markBusAsInvalid(IItemHandlerModifiable bus) {
        invalidatedInputList.add(bus);
    }

    @Override
    public void invalidateInputs() {
        MultiblockWithDisplayBase controller = (MultiblockWithDisplayBase) metaTileEntity;
        RecipeMapLaserMultiblockController distinctController = (RecipeMapLaserMultiblockController) controller;
        if (distinctController.canBeDistinct() && distinctController.isDistinct() &&
                !(getInputInventory() instanceof DualHandler) &&
                getInputInventory().getSlots() > 0) {
            invalidatedInputList.add(currentDistinctInputBus);
        } else {
            super.invalidateInputs();
        }
    }

    @Override
    protected void modifyOverclockPre(OCParams ocParams, RecipePropertyStorage storage) {
        super.modifyOverclockPre(ocParams, storage);

        // apply maintenance bonuses
        Tuple<Integer, Double> maintenanceValues = getMaintenanceValues();

        // duration bonus
        if (maintenanceValues.getSecond() != 1.0) {
            ocParams.setDuration((int) Math.round(ocParams.duration() * maintenanceValues.getSecond()));
        }
    }

    @Override
    protected void runOverclockingLogic(OCParams ocParams, OCResult ocResult,
                                        RecipePropertyStorage propertyStorage, long maxVoltage) {
        subTickParallelOC(ocParams, ocResult, maxVoltage, getOverclockingDurationFactor(),
                getOverclockingVoltageFactor());
    }

    @Override
    protected void modifyOverclockPost(OCResult ocResult, RecipePropertyStorage storage) {
        super.modifyOverclockPost(ocResult, storage);

        // apply maintenance penalties
        Tuple<Integer, Double> maintenanceValues = getMaintenanceValues();

        // duration penalty
        if (maintenanceValues.getFirst() > 0) {
            ocResult.setDuration((int) (ocResult.duration() * (1 + 0.1 * maintenanceValues.getFirst())));
        }
    }


    protected Tuple<Integer, Double> getMaintenanceValues() {
        MultiblockWithDisplayBase displayBase = this.metaTileEntity instanceof MultiblockWithDisplayBase ?
                (MultiblockWithDisplayBase) metaTileEntity : null;
        int numMaintenanceProblems = displayBase == null || !displayBase.hasMaintenanceMechanics() ||
                !ConfigHolder.machines.enableMaintenance ? 0 : displayBase.getNumMaintenanceProblems();
        double durationMultiplier = 1.0D;
        if (displayBase != null && displayBase.hasMaintenanceMechanics() && ConfigHolder.machines.enableMaintenance) {
            durationMultiplier = displayBase.getMaintenanceDurationMultiplier();
        }
        return new Tuple<>(numMaintenanceProblems, durationMultiplier);
    }

    @Override
    public boolean checkRecipe(Recipe recipe) {
        RecipeMapLaserMultiblockController controller = (RecipeMapLaserMultiblockController) metaTileEntity;
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


    @Override
    public RecipeMap<?> getRecipeMap() {
        // if the multiblock has more than one RecipeMap, return the currently selected one
        if (metaTileEntity instanceof IMultipleRecipeMaps)
            return ((IMultipleRecipeMaps) metaTileEntity).getCurrentRecipeMap();
        return super.getRecipeMap();
    }
}


