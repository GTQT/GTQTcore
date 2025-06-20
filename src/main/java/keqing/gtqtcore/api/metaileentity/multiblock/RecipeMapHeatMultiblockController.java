package keqing.gtqtcore.api.metaileentity.multiblock;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IDistinctBusController;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.multiblock.ICleanroomProvider;
import gregtech.api.metatileentity.multiblock.ICleanroomReceiver;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.TextFormattingUtil;
import gregtech.common.ConfigHolder;
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
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static gregtech.api.GTValues.V;

public abstract class RecipeMapHeatMultiblockController extends MultiblockWithDisplayBase implements IDataInfoProvider, IDistinctBusController {
    @Override
    public boolean usesMui2() {
        return false;
    }
    public final RecipeMap<?> recipeMap;
    protected HeatRecipeLogic recipeMapWorkable;
    protected IItemHandlerModifiable inputInventory;
    protected IItemHandlerModifiable outputInventory;
    protected IMultipleTankHandler inputFluidInventory;
    protected IMultipleTankHandler outputFluidInventory;
    int heat;
    int tier;
    private boolean isDistinct = false;
    private ICleanroomProvider cleanroom;

    public RecipeMapHeatMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId);
        this.recipeMap = recipeMap;
        this.recipeMapWorkable = new HeatRecipeLogic(this, recipeMap);
        resetTileAbilities();
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
        data.setBoolean("isDistinct", isDistinct);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        heat = data.getInteger("heat");
        tier = data.getInteger("tier");
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
        if(heat<300)return false;
        return heat >= recipe.getProperty(HeatProperty.getInstance(), 0);
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
        this.inputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.inputFluidInventory = new FluidTankList(allowSameFluidFillForOutputs(), getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.outputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.outputFluidInventory = new FluidTankList(allowSameFluidFillForOutputs(), getAbilities(MultiblockAbility.EXPORT_FLUIDS));
    }

    private void resetTileAbilities() {
        this.inputInventory = new ItemStackHandler(0);
        this.inputFluidInventory = new FluidTankList(true);
        this.outputInventory = new ItemStackHandler(0);
        this.outputFluidInventory = new FluidTankList(true);
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

    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (isStructureFormed()) {
            textList.add(new TextComponentTranslation("热源温度:%s", heat));
            if (!recipeMapWorkable.isWorkingEnabled()) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.work_paused"));

            } else if (recipeMapWorkable.isActive()) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.running"));
                int currentProgress = (int) (recipeMapWorkable.getProgressPercent() * 100);
                if (this.recipeMapWorkable.getParallelLimit() != 1) {
                    textList.add(new TextComponentTranslation("gregtech.multiblock.parallel", this.recipeMapWorkable.getParallelLimit()));
                }
                textList.add(new TextComponentTranslation("gregtech.multiblock.progress", currentProgress));
            } else {
                textList.add(new TextComponentTranslation("gregtech.multiblock.idling"));
            }
        }
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

    protected class HeatRecipeLogic extends BaseHeatRecipeLogic {

        public HeatRecipeLogic(RecipeMapHeatMultiblockController tileEntity, RecipeMap<?> recipeMap) {
            super(tileEntity, recipeMap);
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
