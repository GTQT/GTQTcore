package keqing.gtqtcore.api.capability.impl;


import gregtech.api.GTValues;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.IWorkable;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.chance.output.impl.ChancedItemOutput;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.common.ConfigHolder;
import keqing.gtqtcore.api.utils.GTQTUniverUtil;
import keqing.gtqtcore.api.utils.GTQTUniverUtil;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityIntegratedOreProcessor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Recipe Logic for Integrated Ore Processor
 *
 * @author Gate Guardian
 *
 * <p>
 *     Based on my friend Gate Guardian's work.
 *     Some code reference <a href="https://github.com/GTNewHorizons/GT5-Unofficial/">GT5u</a>.
 * </p>
 */
public class OreProcessorRecipeLogic implements IWorkable {

    private static final int MAX_PARALLEL = 1024; // max parallel per one run.

    //  Basic Hash set of all ore types
    private static final HashSet<Integer> isOre = new HashSet<>();
    private static final HashSet<Integer> isPureOre = new HashSet<>();
    private static final HashSet<Integer> isImpureOre = new HashSet<>();
    private static final HashSet<Integer> isCrushedOre = new HashSet<>();
    private static final HashSet<Integer> isCrushedPureOre = new HashSet<>();
    private static final HashSet<Integer> isThermalOre = new HashSet<>();

    private ItemStack[] midProduct;

    protected int[] overclockResults;

    private int progressTime = 0;
    protected int maxProgressTime;
    private boolean isActive;
    protected boolean canRecipeProgress = true;
    protected int recipeEUt;
    protected NonNullList<ItemStack> itemOutputs;
    protected boolean workingEnabled = true;
    protected boolean hasNotEnoughEnergy;
    protected boolean wasActiveAndNeedsUpdate;
    protected boolean isOutputsFull;

    public int mode = 0;
    public boolean furnace= false;
    private static final boolean init = false;
    public boolean isVoidStone = false;
    private final MetaTileEntityIntegratedOreProcessor metaTileEntity;

    public OreProcessorRecipeLogic(MetaTileEntityIntegratedOreProcessor tileEntity) {
        this.metaTileEntity = tileEntity;
        if (!init)
            initHash(); //  init Hash set of ore types.
    }

    //  get initialized abilities of metatileentity.
    public IEnergyContainer getEnergyContainer() {
        return metaTileEntity.getEnergyContainer();
    }

    protected IItemHandlerModifiable getInputInventory() {
        return metaTileEntity.getInputInventory();
    }

    protected IItemHandlerModifiable getOutputInventory() {
        return metaTileEntity.getOutputInventory();
    }

    protected IMultipleTankHandler getInputTank() {
        return metaTileEntity.getInputFluidInventory();
    }

    protected long getEnergyCapacity() {
        return getEnergyContainer().getEnergyCapacity();
    }

    //  Get Input items
    protected List<ItemStack> getInputItems() {
        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < this.getInputInventory().getSlots(); i++) { //  traverse all index of item input hatch slots.
            final ItemStack currentInputItem = getInputInventory().getStackInSlot(i);
            if (currentInputItem.isEmpty()) //  skip empty slots.
                continue;
            list.add(this.getInputInventory().getStackInSlot(i));
        }
        return list;
    }

    //  fluid depleted from recipes
    private void depleteInput(FluidStack fluid) {
        if (fluid == null)
            return;
        IMultipleTankHandler inputTank = this.getInputTank();
        if (fluid.isFluidStackIdentical(inputTank.drain(fluid, false)))
            inputTank.drain(fluid, true);
    }

    //  initialized hash set
    private static void initHash() {
        for (String name : OreDictionary.getOreNames()) {
            if (name == null || name.isEmpty()) // if item do not have ore dict, then skip it.
                continue;
            if (name.startsWith("crushedPurified")) { // crushed purified ore
                for (ItemStack stack : OreDictionary.getOres(name)) {
                    isCrushedPureOre.add(GTQTUniverUtil.stackToInt(stack));
                }
            } else if (name.startsWith("crushedCentrifuged")) { // crushed centrifuged ore
                for (ItemStack stack : OreDictionary.getOres(name)) {
                    isThermalOre.add(GTQTUniverUtil.stackToInt(stack));
                }
            } else if (name.startsWith("crushed")) { // crushed ore
                for (ItemStack stack : OreDictionary.getOres(name)) {
                    isCrushedOre.add(GTQTUniverUtil.stackToInt(stack));
                }
            } else if (name.startsWith("dustImpure")) { // impure dust
                for (ItemStack stack : OreDictionary.getOres(name)) {
                    isImpureOre.add(GTQTUniverUtil.stackToInt(stack));
                }
            } else if (name.startsWith("dustPure")) { // pure dust
                for (ItemStack stack : OreDictionary.getOres(name)) {
                    isPureOre.add(GTQTUniverUtil.stackToInt(stack));
                }
            } else if (name.startsWith("ore")) { // ore
                for (ItemStack stack : OreDictionary.getOres(name)) {
                    isOre.add(GTQTUniverUtil.stackToInt(stack));
                }
            }
        }
    }

    //  check mid-product, add t_product to outputs (if empty, then add stack to t_product).
    @SafeVarargs
    private void doMacerator(HashSet<Integer>... tables) {
        List<ItemStack> t_product = new ArrayList<>();
        if (midProduct != null) {
            for (ItemStack stack : midProduct) {
                int t_id = GTQTUniverUtil.stackToInt(stack);
                if (checkTypes(t_id, tables)) {
                    Recipe recipe = RecipeMaps.MACERATOR_RECIPES.findRecipe(GTValues.V[GTValues.MAX], Collections.singletonList(stack), Collections.emptyList());
                    if (recipe != null) {
                        t_product.addAll(getOutputStack(recipe, stack.getCount()));
                    } else {
                        t_product.add(stack);
                    }
                } else {
                    t_product.add(stack);
                }
            }
        }
        doCompress(t_product);
    }

    @SafeVarargs
    private void doOreWasher(HashSet<Integer>... tables) {
        List<ItemStack> t_product = new ArrayList<>();
        if (midProduct != null) {
            for (ItemStack stack : midProduct) {
                int t_id = GTQTUniverUtil.stackToInt(stack);
                if (checkTypes(t_id, tables)) {
                    Recipe recipe = RecipeMaps.ORE_WASHER_RECIPES.findRecipe(GTValues.V[GTValues.MAX], Collections.singletonList(stack), Collections.singletonList(Materials.DistilledWater.getFluid(Integer.MAX_VALUE)));
                    if (recipe != null) {
                        t_product.addAll(getOutputStack(recipe, stack.getCount()));
                    } else {
                        t_product.add(stack);
                    }
                } else {
                    t_product.add(stack);
                }
            }
        }
        doCompress(t_product);
    }

    @SafeVarargs
    private void doThermalCentrifuge(HashSet<Integer>... tables) {
        List<ItemStack> t_product = new ArrayList<>();
        if (midProduct != null) {
            for (ItemStack stack : midProduct) {
                int t_id = GTQTUniverUtil.stackToInt(stack);
                if (checkTypes(t_id, tables)) {
                    Recipe recipe = RecipeMaps.THERMAL_CENTRIFUGE_RECIPES.findRecipe(GTValues.V[GTValues.MAX], Collections.singletonList(stack), Collections.emptyList());
                    if (recipe != null) {
                        t_product.addAll(getOutputStack(recipe, stack.getCount()));
                    } else {
                        t_product.add(stack);
                    }
                } else {
                    t_product.add(stack);
                }
            }
        }
        doCompress(t_product);
    }

    @SafeVarargs
    private void doCentrifuge(HashSet<Integer>... tables) {
        List<ItemStack> t_product = new ArrayList<>();
        if (midProduct != null) {
            for (ItemStack stack : midProduct) {
                int t_id = GTQTUniverUtil.stackToInt(stack);
                if (checkTypes(t_id, tables)) {
                    Recipe recipe = RecipeMaps.CENTRIFUGE_RECIPES.findRecipe(GTValues.V[GTValues.MAX], Collections.singletonList(stack), Collections.emptyList());
                    if (recipe != null) {
                        t_product.addAll(getOutputStack(recipe, stack.getCount()));
                    } else {
                        t_product.add(stack);
                    }
                } else {
                    t_product.add(stack);
                }
            }
        }
        doCompress(t_product);
    }

    @SafeVarargs
    private void doSifter(HashSet<Integer>... tables) {
        List<ItemStack> t_product = new ArrayList<>();
        if (midProduct != null) {
            for (ItemStack stack : midProduct) {
                int t_id = GTQTUniverUtil.stackToInt(stack);
                if (checkTypes(t_id, tables)) {
                    Recipe recipe = RecipeMaps.SIFTER_RECIPES.findRecipe(GTValues.V[GTValues.MAX], Collections.singletonList(stack), Collections.emptyList());
                    if (recipe != null) {
                        t_product.addAll(getOutputStack(recipe, stack.getCount()));
                    } else {
                        t_product.add(stack);
                    }
                } else {
                    t_product.add(stack);
                }
            }
        }
        doCompress(t_product);
    }

    @SafeVarargs
    private void doChemicalBath(HashSet<Integer>... tables) {
        List<ItemStack> t_product = new ArrayList<>();
        if (midProduct != null) {
            for (ItemStack stack : midProduct) {
                int t_id = GTQTUniverUtil.stackToInt(stack);
                if (checkTypes(t_id, tables)) {
                    Recipe recipe = RecipeMaps.CHEMICAL_BATH_RECIPES.findRecipe(GTValues.V[GTValues.MAX], Collections.singletonList(stack), GTUtility.fluidHandlerToList(getInputTank()));
                    if (recipe != null && !recipe.getFluidInputs().isEmpty()) {
                        FluidStack t_input_fluid = recipe.getFluidInputs().get(0).getInputFluidStack().copy();
                        int t_stored = getFluidAmount(t_input_fluid);
                        int t_washed = Math.min(t_stored / t_input_fluid.amount, stack.getCount());
                        depleteInput(new FluidStack(t_input_fluid.getFluid(), t_washed * t_input_fluid.amount));
                        t_product.addAll(getOutputStack(recipe, t_washed));
                        if (t_washed < stack.getCount()) {
                            t_product.add(GTQTUniverUtil.copyAmountUnsafe(stack.getCount() - t_washed, stack));
                        }
                    } else {
                        t_product.add(stack);
                    }
                } else {
                    t_product.add(stack);
                }
            }
        }
        doCompress(t_product);
    }

    private int getFluidAmount(FluidStack fluid) {
        int t_amount = 0;
        if (fluid == null)
            return 0;
        for (FluidStack t_fluid : GTUtility.fluidHandlerToList(getInputTank())) {
            if (t_fluid != null && t_fluid.isFluidEqual(fluid)) {
                t_amount += t_fluid.amount;
            }
        }
        return t_amount;
    }


    private void recipeProcessing() {
        int tCharged = MAX_PARALLEL;
        List<ItemStack> tInput = getInputItems();
        List<FluidStack> tInputFluid = GTUtility.fluidHandlerToList(getInputTank());

        //  initialize amounts of lubricant and distilled water.
        int t_lubricant = 0;
        int t_distilled_water = 0;

        //  calculate amounts of lubricant and distilled water.
        for (FluidStack fluid : tInputFluid) {
            if (fluid != null && fluid.isFluidEqual(Materials.DistilledWater.getFluid(1))) {
                t_distilled_water += fluid.amount;
            } else if (fluid != null && fluid.isFluidEqual(Materials.Lubricant.getFluid(1))) {
                t_lubricant += fluid.amount;
            }
        }

        tCharged = Math.min(tCharged, t_lubricant / 2);
        tCharged = Math.min(tCharged, t_distilled_water / 200);
        tCharged = (int) Math.min(tCharged, getMaxVoltage() / 30L);

        List<ItemStack> tOres = new ArrayList<>();
        int tRealUsed = 0;

        for (ItemStack ore : tInput) {
            if (tCharged <= 0)
                break;
            int t_id = GTQTUniverUtil.stackToInt(ore);
            if (t_id == 0)
                continue;
            if (isPureOre.contains(t_id)
                    || isImpureOre.contains(t_id)
                    || isCrushedPureOre.contains(t_id)
                    || isThermalOre.contains(t_id)
                    || isCrushedOre.contains(t_id)
                    || isOre.contains(t_id)) {
                if (ore.getCount() <= tCharged) {
                    tRealUsed += ore.getCount();
                    tOres.add(GTUtility.copy(ore));
                    tCharged -= ore.getCount();
                    ore.setCount(0);
                } else {
                    tRealUsed = tCharged;
                    tOres.add(GTQTUniverUtil.copyAmountUnsafe(tCharged, ore));
                    ore.setCount(ore.getCount() - tCharged);
                    break;
                }
            }
        }

        if (tRealUsed != 0) {
            overclockResults = calculateOverclock(tRealUsed);
            depleteInput(Materials.DistilledWater.getFluid(tRealUsed * 200));
            depleteInput(Materials.Lubricant.getFluid(tRealUsed * 2));

            midProduct = tOres.toArray(new ItemStack[0]);

            switch (mode) {
                case 0 -> {
                    doMacerator(isOre);
                    doOreWasher(isCrushedOre);
                    doThermalCentrifuge(isCrushedPureOre, isCrushedOre);
                    doMacerator(isThermalOre, isOre, isCrushedOre, isCrushedPureOre);
                }
                case 1 -> {
                    doMacerator(isOre);
                    doOreWasher(isCrushedOre);
                    doMacerator(isOre, isCrushedOre, isCrushedPureOre);
                    doCentrifuge(isImpureOre, isPureOre);
                }
                case 2 -> {
                    doMacerator(isOre);
                    doMacerator(isThermalOre, isOre, isCrushedOre, isCrushedPureOre);
                    doCentrifuge(isImpureOre, isPureOre);
                }
                case 3 -> {
                    doMacerator(isOre);
                    doOreWasher(isCrushedOre);
                    doSifter(isCrushedPureOre);
                }
                case 4 -> {
                    doMacerator(isOre);
                    doChemicalBath(isCrushedOre);
                    doMacerator(isCrushedOre, isCrushedPureOre);
                    doCentrifuge(isImpureOre, isPureOre);
                }
                default -> {}
            }

            if (itemOutputs == null) {
                itemOutputs = NonNullList.create();
            }

            progressTime = 1;
            maxProgressTime = overclockResults[0];
            recipeEUt = overclockResults[1];
            if(furnace)
            {
                for(ItemStack stack : midProduct)
                {
                    Recipe recipe = RecipeMaps.FURNACE_RECIPES.findRecipe(GTValues.V[GTValues.MAX], Collections.singletonList(stack), Collections.emptyList());
                    if (recipe != null) {
                        itemOutputs.addAll(getOutputStack(recipe, stack.getCount()));
                    }
                    else itemOutputs.addAll(Arrays.asList(midProduct));
                }
            }
            else itemOutputs.addAll(Arrays.asList(midProduct));
        }
    }

    private int[] calculateOverclock(int parallel) {
        long recipeVoltage = parallel * 30L;
        int overclock = (int) Math.floor(Math.log((double) (getMaxVoltage() / recipeVoltage)) / Math.log(4));
        int[] overclockResult = new int[2];
        overclockResult[0] = (int) (getDuration(mode,furnace) / Math.pow(2, overclock));
        overclockResult[1] = (int) (recipeVoltage * Math.pow(4, overclock));
        return overclockResult;
    }

    private static int getDuration(int mode,boolean furnace) {
        int i;
        if(furnace) i=2;
        else i=1;
        return switch (mode) {
            case 0 -> 30 * 20*i;
            case 1 -> 15 * 20*i;
            case 2 -> 10 * 20*i;
            case 3 -> 20 * 20*i;
            case 4 -> 17 * 20*i;
            default -> 1000000000;
        };
    }

    protected long getEnergyInputPerSecond() {
        return getEnergyContainer().getInputPerSec();
    }

    protected long getEnergyStored() {
        return getEnergyContainer().getEnergyStored();
    }

    protected boolean drawEnergy(int recipeEUt,
                                 boolean simulate) {
        long resultEnergy = getEnergyStored() - recipeEUt;
        if (resultEnergy >= 0L && resultEnergy <= getEnergyCapacity()) {
            if (!simulate)
                getEnergyContainer().changeEnergy(- recipeEUt);
            return true;
        } else
            return false;
    }

    protected long getMaxVoltage() {
        IEnergyContainer energyContainer = getEnergyContainer();
        if (energyContainer instanceof EnergyContainerList) {
            long voltage;
            long amperage;
            if (energyContainer.getInputVoltage() > energyContainer.getOutputVoltage()) {
                voltage = energyContainer.getInputVoltage();
                amperage = energyContainer.getInputAmperage();
            } else {
                voltage = energyContainer.getOutputVoltage();
                amperage = energyContainer.getOutputAmperage();
            }

            if (amperage == 1) { // amperage = 1 when the energy is not exactly on a tier.
                //  the voltage for recipe search is always on tier, so take the closest lower tier.
                return GTValues.V[GTUtility.getFloorTierByVoltage(voltage)];
            } else { // amperage != 1 means the voltage is exactly on a tier
                //  ignore amperage, since only the voltage is relevant for recipe search.
                return voltage;
            } //  amperages are never > 3 in an EnergyContainerList
        }
        return Math.max(energyContainer.getInputVoltage(), energyContainer.getOutputVoltage());
    }

    public void update() {
        World world = getMetaTileEntity().getWorld();
        if (world != null && !world.isRemote) {
            if (workingEnabled) {
                if (progressTime > 0) {
                    setActive(true);
                    updateRecipeProgress();
                }
                if (progressTime == 0 && shouldProcessRecipe()) {
                    recipeProcessing();
                }
            }
            if (wasActiveAndNeedsUpdate) {
                this.wasActiveAndNeedsUpdate = false;
                setActive(false);
            }
        }
    }

    protected void updateRecipeProgress() {
        if (canRecipeProgress && drawEnergy(recipeEUt, true)) {
            drawEnergy(recipeEUt, false);
            if (++progressTime > maxProgressTime) {
                completeRecipe();
            }
            if (this.hasNotEnoughEnergy && getEnergyInputPerSecond() > 19L * recipeEUt) {
                this.hasNotEnoughEnergy = false;
            }
        } else if (recipeEUt > 0) {
            this.hasNotEnoughEnergy = true;
            decreaseProgress();
        }
    }

    protected void decreaseProgress() {
        //  if current progress value is greater than 2, decrement it by 2
        if (progressTime >= 2) {
            if (ConfigHolder.machines.recipeProgressLowEnergy) {
                this.progressTime = 1;
            } else {
                this.progressTime = Math.max(1, progressTime - 2);
            }
        }
    }

    protected void completeRecipe() {
        outputRecipeOutputs();
        this.progressTime = 0;
        setMaxProgress(0);
        this.recipeEUt = 0;
        this.itemOutputs = null;
        this.overclockResults = new int[]{0, 0};
        this.hasNotEnoughEnergy = false;
        this.wasActiveAndNeedsUpdate = true;
    }

    protected void outputRecipeOutputs() {
        isOutputsFull = !GTTransferUtils.addItemsToItemHandler(getOutputInventory(), true, itemOutputs);
        GTTransferUtils.addItemsToItemHandler(getOutputInventory(), false, itemOutputs);
    }

    private boolean shouldProcessRecipe() {
        return true;
    }

    //  check if item has corresponded ore dict.
    @SafeVarargs
    private boolean checkTypes(int id, HashSet<Integer>... tables) {
        for (HashSet<Integer> set : tables) {
            if (set.contains(id)) {
                return true;
            }
        }
        return false;
    }

    //  calculate recipe outputs with chanced output.
    private List<ItemStack> getOutputStack(Recipe recipe, int time) {
        List<ItemStack> outputStacks = new ArrayList<>();

        //  calculate amount
        for (ItemStack item : recipe.getOutputs()) {
            ItemStack output = item.copy();
            output.setCount(output.getCount() * time);
            outputStacks.add(output);
        }

        // acalculate amount with chance
        for (ChancedItemOutput chancedItem : recipe.getChancedOutputs().getChancedEntries()) {
            float chance = chancedItem.getChance(); // original change
            float randomValue = new Random().nextFloat(); // get a random
            if (randomValue < chance) { // compare change and random
                ItemStack chancedOutput = chancedItem.getIngredient().copy();
                chancedOutput.setCount(chancedOutput.getCount() * time);
                outputStacks.add(chancedOutput);
            }
        }

        return outputStacks;
    }



    //  t_product to midProduct
    private void doCompress(List<ItemStack> list) {
        HashMap<Integer, Integer> r_product = new HashMap<>();
        for (ItemStack stack : list) {
            int t_id = GTQTUniverUtil.stackToInt(stack);

            //  if enable stone dust voiding mode, then void the stone dust.
            if (isVoidStone) {
                if (stack.isItemEqual(OreDictUnifier.get(OrePrefix.dust, Materials.Stone, 1)) ||
                        stack.isItemEqual(OreDictUnifier.get(OrePrefix.dust, Materials.Endstone, 1)) ||
                        stack.isItemEqual(OreDictUnifier.get(OrePrefix.dust, Materials.Netherrack, 1))
                ) {

                    continue;
                }
            }
            if (t_id != 0) {
                if (r_product.containsKey(t_id)) {
                    r_product.put(t_id, r_product.get(t_id) + stack.getCount());
                } else {
                    r_product.put(t_id, stack.getCount());
                }
            }
        }
        midProduct = new ItemStack[r_product.size()];
        int cnt = 0;
        for (Integer id : r_product.keySet()) {
            ItemStack stack = GTQTUniverUtil.intToStack(id);
            midProduct[cnt] = GTQTUniverUtil.copyAmountUnsafe(r_product.get(id), stack);
            cnt++;
        }
    }

    public void invalidate() {
        this.progressTime = 0;
        this.maxProgressTime = 0;
        recipeEUt = 0;
        isOutputsFull = false;
        setActive(false);
    }

    public boolean isVoidStone() {
        return isVoidStone;
    }

    public void setVoidStone(boolean voidStone) {
        isVoidStone = voidStone;
        metaTileEntity.markDirty();
    }


    public void setFurnace(boolean f) {
        furnace = f;
        metaTileEntity.markDirty();
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
        metaTileEntity.markDirty();
    }

    @Nonnull
    public MetaTileEntity getMetaTileEntity() {
        return metaTileEntity;
    }

    public boolean isWorkingEnabled() {
        return workingEnabled;
    }

    @Override
    public void setWorkingEnabled(boolean workingEnabled) {
        this.workingEnabled = workingEnabled;
        metaTileEntity.markDirty();
        World world = metaTileEntity.getWorld();
        if (world != null && !world.isRemote) {
            metaTileEntity.writeCustomData(GregtechDataCodes.WORKING_ENABLED, buf -> buf.writeBoolean(workingEnabled));
        }
    }

    public int getProgress() {
        return this.progressTime;
    }

    public int getMaxProgress() {
        return this.maxProgressTime;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgressTime = maxProgress;
        metaTileEntity.markDirty();
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean active) {
        if (this.isActive != active) {
            this.isActive = active;
            if (metaTileEntity.getWorld() != null && !metaTileEntity.getWorld().isRemote) {
                this.metaTileEntity.writeCustomData(GregtechDataCodes.WORKABLE_ACTIVE, buf -> buf.writeBoolean(active));
                this.metaTileEntity.markDirty();
            }
        }
    }

    public double getProgressPercent() {
        return getMaxProgress() == 0 ? 0.0 : getProgress() / (getMaxProgress() * 1.0);
    }

    public int getParallelLimit() {
        return MAX_PARALLEL;
    }

    public int getRecipeEUt() {
        return recipeEUt;
    }

    public void receiveCustomData(int dataId, @Nonnull PacketBuffer buf) {
        if (dataId == GregtechDataCodes.WORKABLE_ACTIVE) {
            this.isActive = buf.readBoolean();
            getMetaTileEntity().scheduleRenderUpdate();
        }
        if (dataId == GregtechDataCodes.WORKING_ENABLED) {
            this.workingEnabled = buf.readBoolean();
            getMetaTileEntity().scheduleRenderUpdate();
        }
    }

    public void writeInitialData(@Nonnull PacketBuffer buf) {
        buf.writeBoolean(isActive);
        buf.writeBoolean(workingEnabled);
        buf.writeBoolean(isVoidStone);
        buf.writeBoolean(furnace);
    }

    public void receiveInitialData(@Nonnull PacketBuffer buf) {
        this.isActive = buf.readBoolean();
        this.workingEnabled = buf.readBoolean();
        this.isVoidStone = buf.readBoolean();
        this.furnace = buf.readBoolean();
    }

    @Nonnull
    public NBTTagCompound serializeNBT(NBTTagCompound compound) {
        compound.setBoolean("WorkEnabled", workingEnabled);
        compound.setBoolean("CanRecipeProgress", canRecipeProgress);
        compound.setBoolean("isVoidStone", isVoidStone);
        compound.setBoolean("furnace", furnace);
        compound.setInteger("mode", mode);
        if (progressTime > 0) {
            compound.setInteger("Progress", progressTime);
            compound.setInteger("MaxProgress", maxProgressTime);
            compound.setInteger("RecipeEUt", this.recipeEUt);
            NBTTagList itemOutputsList = new NBTTagList();
            for (ItemStack itemOutput : itemOutputs) {
                itemOutputsList.appendTag(itemOutput.writeToNBT(new NBTTagCompound()));
            }
            compound.setTag("ItemOutputs", itemOutputsList);
        }
        return compound;
    }

    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
        this.workingEnabled = compound.getBoolean("WorkEnabled");
        this.canRecipeProgress = compound.getBoolean("CanRecipeProgress");
        this.isVoidStone = compound.getBoolean("isVoidStone");
        this.furnace = compound.getBoolean("furnace");
        this.progressTime = compound.getInteger("Progress");
        this.mode = compound.getInteger("mode");
        if (progressTime > 0) {
            this.isActive = true;
            this.maxProgressTime = compound.getInteger("MaxProgress");
            this.recipeEUt = compound.getInteger("RecipeEUt");
            NBTTagList itemOutputsList = compound.getTagList("ItemOutputs", Constants.NBT.TAG_COMPOUND);
            this.itemOutputs = NonNullList.create();
            for (int i = 0; i < itemOutputsList.tagCount(); i++) {
                this.itemOutputs.add(new ItemStack(itemOutputsList.getCompoundTagAt(i)));
            }
        }
    }
}

