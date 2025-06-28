package keqing.gtqtcore.api.metatileentity.multiblock;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IControllable;
import gregtech.api.capability.IDistinctBusController;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.interfaces.IRefreshBeforeConsumption;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIBuilder;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.api.util.KeyUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.common.ConfigHolder;
import gtqt.api.util.GTQTUtility;
import keqing.gtqtcore.api.capability.impl.BaseHeatRecipeLogic;
import keqing.gtqtcore.api.recipes.properties.HeatProperty;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static gregtech.api.GTValues.V;

public abstract class RecipeMapHeatMultiblockController extends MultiblockWithDisplayBase implements IDataInfoProvider, IDistinctBusController, IControllable {

    public final RecipeMap<?> recipeMap;
    public int recipeHeat;
    protected HeatRecipeLogic recipeMapWorkable;
    protected IItemHandlerModifiable inputInventory;
    protected IItemHandlerModifiable outputInventory;
    protected IMultipleTankHandler inputFluidInventory;
    protected IMultipleTankHandler outputFluidInventory;
    protected List<IRefreshBeforeConsumption> refreshBeforeConsumptions;
    int heat;
    int tier;
    private boolean isDistinct = false;

    public RecipeMapHeatMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId);
        this.recipeMap = recipeMap;
        this.recipeMapWorkable = new HeatRecipeLogic(this, recipeMap);
        this.refreshBeforeConsumptions = new ArrayList<>();
        resetTileAbilities();
    }

    public void refreshAllBeforeConsumption() {
        for (IRefreshBeforeConsumption refresh : refreshBeforeConsumptions) {
            refresh.refreshBeforeConsumption();
        }
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int level) {
        tier = level;
    }

    public int getHeat() {
        return heat;
    }

    public void setHeat(int temp) {
        heat = temp;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("heat", heat);
        data.setInteger("tier", tier);
        data.setInteger("recipeHeat", recipeHeat);
        data.setBoolean("isDistinct", isDistinct);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        heat = data.getInteger("heat");
        tier = data.getInteger("tier");
        recipeHeat = data.getInteger("recipeHeat");
        isDistinct = data.getBoolean("isDistinct");
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        resetTileAbilities();
        this.recipeMapWorkable.invalidate();
    }

    public IItemHandlerModifiable getInputInventory() {
        return inputInventory;
    }

    public IItemHandlerModifiable getOutputInventory() {
        return outputInventory;
    }

    public IMultipleTankHandler getInputFluidInventory() {
        return inputFluidInventory;
    }

    public IMultipleTankHandler getOutputFluidInventory() {
        return outputFluidInventory;
    }

    public HeatRecipeLogic getRecipeMapWorkable() {
        return recipeMapWorkable;
    }

    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        if (heat < 300) return false;
        recipeHeat = recipe.getProperty(HeatProperty.getInstance(), 0);
        return heat >= recipeHeat;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
    }

    @Override
    protected void updateFormedValid() {
        if (isMufflerFaceFree()) {
            this.recipeMapWorkable.updateWorkable();
        }
    }

    @Override
    public boolean isActive() {
        return isStructureFormed() && recipeMapWorkable.isActive() && recipeMapWorkable.isWorkingEnabled();
    }

    protected void initializeAbilities() {
        List<IItemHandler> inputItems = new ArrayList<>(this.getAbilities(MultiblockAbility.IMPORT_ITEMS));
        inputItems.addAll(getAbilities(MultiblockAbility.DUAL_IMPORT));
        this.inputInventory = new ItemHandlerList(inputItems);

        List<IMultipleTankHandler> inputFluids = new ArrayList<>(getAbilities(MultiblockAbility.DUAL_IMPORT));
        inputFluids.add(new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS)));
        this.inputFluidInventory = GTQTUtility.mergeTankHandlers(inputFluids, true);

        List<IItemHandler> outputItems = new ArrayList<>(this.getAbilities(MultiblockAbility.EXPORT_ITEMS));
        outputItems.addAll(getAbilities(MultiblockAbility.DUAL_EXPORT));
        this.outputInventory = new ItemHandlerList(outputItems);
        List<IMultipleTankHandler> outputFluids = new ArrayList<>(getAbilities(MultiblockAbility.DUAL_EXPORT));
        outputFluids.add(new FluidTankList(false, getAbilities(MultiblockAbility.EXPORT_FLUIDS)));
        this.outputFluidInventory = GTQTUtility.mergeTankHandlers(outputFluids, false);


        for (IMultiblockPart part : getMultiblockParts()) {
            if (part instanceof IRefreshBeforeConsumption refresh) {
                refreshBeforeConsumptions.add(refresh);
            }
        }
    }

    private void resetTileAbilities() {
        this.inputInventory = new GTItemStackHandler(this, 0);
        this.inputFluidInventory = new FluidTankList(true);
        this.outputInventory = new GTItemStackHandler(this, 0);
        this.outputFluidInventory = new FluidTankList(true);
        this.refreshBeforeConsumptions.clear();
    }


    protected boolean allowSameFluidFillForOutputs() {
        return true;
    }

    public SoundEvent getSound() {
        return this.recipeMapWorkable.getRecipeMap().getSound();
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), recipeMapWorkable.isActive(), recipeMapWorkable.isWorkingEnabled());
    }

    @Override
    protected void configureDisplayText(MultiblockUIBuilder builder) {
        builder.setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                .addCustom((textList, syncer) -> {
                    textList.add(KeyUtil.lang(TextFormatting.GRAY, "热源温度:%s", syncer.syncInt(heat)));
                })
                .addParallelsLine(recipeMapWorkable.getParallelLimit())
                .addWorkingStatusLine()
                .addProgressLine(recipeMapWorkable.getProgress(), recipeMapWorkable.getMaxProgress())
                .addRecipeOutputLine(recipeMapWorkable);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(isDistinct);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        isDistinct = buf.readBoolean();
    }

    @Override
    public boolean canBeDistinct() {
        return false;
    }

    @Override
    public boolean isDistinct() {
        return isDistinct;
    }

    @Override
    public void setDistinct(boolean isDistinct) {
        this.isDistinct = isDistinct;
        recipeMapWorkable.onDistinctChanged();
        //mark buses as changed on distinct toggle
        if (this.isDistinct) {
            this.notifiedItemInputList.addAll(this.getAbilities(MultiblockAbility.IMPORT_ITEMS));
        } else {
            this.notifiedItemInputList.add(this.inputInventory);
        }
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ArrayList<>();
        if (recipeMapWorkable.getMaxProgress() > 0) {
            list.add(new TextComponentTranslation("behavior.tricorder.workable_progress",
                    new TextComponentTranslation(TextFormattingUtil.formatNumbers(recipeMapWorkable.getProgress() / 20)).setStyle(new Style().setColor(TextFormatting.GREEN)),
                    new TextComponentTranslation(TextFormattingUtil.formatNumbers(recipeMapWorkable.getMaxProgress() / 20)).setStyle(new Style().setColor(TextFormatting.YELLOW))
            ));
        }

        if (ConfigHolder.machines.enableMaintenance && hasMaintenanceMechanics()) {
            list.add(new TextComponentTranslation("behavior.tricorder.multiblock_maintenance",
                    new TextComponentTranslation(TextFormattingUtil.formatNumbers(getNumMaintenanceProblems())).setStyle(new Style().setColor(TextFormatting.RED))
            ));
        }

        if (recipeMapWorkable.getParallelLimit() > 1) {
            list.add(new TextComponentTranslation("behavior.tricorder.multiblock_parallel",
                    new TextComponentTranslation(TextFormattingUtil.formatNumbers(recipeMapWorkable.getParallelLimit())).setStyle(new Style().setColor(TextFormatting.GREEN))
            ));
        }

        return list;
    }

    @Override
    public boolean isWorkingEnabled() {
        return recipeMapWorkable.isWorkingEnabled();
    }

    @Override
    public void setWorkingEnabled(boolean isWorkingAllowed) {
        recipeMapWorkable.setWorkingEnabled(isWorkingAllowed);
    }

    protected class HeatRecipeLogic extends BaseHeatRecipeLogic {

        public HeatRecipeLogic(RecipeMapHeatMultiblockController tileEntity, RecipeMap<?> recipeMap) {
            super(tileEntity, recipeMap);
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            if (recipeHeat == 0) {
                super.setMaxProgress(maxProgress);
                return;
            }
            double speedBonus = Math.min(heat / recipeHeat, 1);
            super.setMaxProgress((int) (maxProgress * speedBonus));
        }

        @Override
        public long getMaxVoltage() {
            return V[tier];
        }

        @Override
        public long getMaximumOverclockVoltage() {
            return heat;
        }
    }
}
