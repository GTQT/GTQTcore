package keqing.gtqtcore.api.metaileentity.multiblock;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import com.google.common.collect.Lists;
import gregtech.api.GTValues;
import gregtech.api.capability.IDistinctBusController;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.common.ConfigHolder;
import keqing.gtqtcore.api.capability.ILaser;
import keqing.gtqtcore.api.capability.impl.MultiblockLaserRecipeLogic;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.GTValues.V;
import static gregtech.api.GTValues.VN;
import static keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility.LASER_INPUT;

public abstract class RecipeMapLaserMultiblockController extends MultiblockWithDisplayBase implements IDistinctBusController {
    public final RecipeMap<?> recipeMap;
    protected MultiblockLaserRecipeLogic recipeMapWorkable;
    protected IItemHandlerModifiable inputInventory;
    protected IItemHandlerModifiable outputInventory;
    protected IMultipleTankHandler inputFluidInventory;
    protected IMultipleTankHandler outputFluidInventory;
    protected int inputNum;
    private boolean isDistinct = false;
    private ICleanroomProvider cleanroom;

    public RecipeMapLaserMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId);
        this.recipeMap = recipeMap;
        this.recipeMapWorkable = new MultiblockLaserRecipeLogic(this);
        this.resetTileAbilities();
    }

    public IItemHandlerModifiable getInputInventory() {
        return this.inputInventory;
    }

    public IItemHandlerModifiable getOutputInventory() {
        return this.outputInventory;
    }

    public IMultipleTankHandler getInputFluidInventory() {
        return this.inputFluidInventory;
    }

    public IMultipleTankHandler getOutputFluidInventory() {
        return this.outputFluidInventory;
    }

    public MultiblockLaserRecipeLogic getRecipeMapWorkable() {
        return this.recipeMapWorkable;
    }

    public boolean checkRecipe(Recipe recipe, boolean consumeIfSuccess) {
        return true;
    }

    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.initializeAbilities();
        List<ILaser> i = getAbilities(LASER_INPUT);
        this.inputNum = i.size();
    }

    public void invalidateStructure() {
        super.invalidateStructure();
        this.resetTileAbilities();
        this.recipeMapWorkable.invalidate();
    }

    protected void updateFormedValid() {
        if (!this.hasMufflerMechanics() || this.isMufflerFaceFree()) {
            this.recipeMapWorkable.updateWorkable();
        }

    }

    public boolean isActive() {
        return this.isStructureFormed() && this.recipeMapWorkable.isActive() && this.recipeMapWorkable.isWorkingEnabled();
    }

    protected void initializeAbilities() {
        this.inputInventory = new ItemHandlerList(this.getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.inputFluidInventory = new FluidTankList(this.allowSameFluidFillForOutputs(), this.getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.outputInventory = new ItemHandlerList(this.getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.outputFluidInventory = new FluidTankList(this.allowSameFluidFillForOutputs(), this.getAbilities(MultiblockAbility.EXPORT_FLUIDS));
    }

    private void resetTileAbilities() {
        this.inputInventory = new GTItemStackHandler(this, 0);
        this.inputFluidInventory = new FluidTankList(true);
        this.outputInventory = new GTItemStackHandler(this, 0);
        this.outputFluidInventory = new FluidTankList(true);
    }

    protected boolean allowSameFluidFillForOutputs() {
        return true;
    }

    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, this.isStructureFormed())
                .setWorkingStatus(this.recipeMapWorkable.isWorkingEnabled(), this.recipeMapWorkable.isActive())
                .addCustom(tl -> tl.add(new TextComponentTranslation("激光最大转换EU：%s %s",V[getMaxLaser()],VN[getMaxLaser()])))
                .addCustom(tl -> tl.add(new TextComponentTranslation("实际激光转换EU：%s %s",LaserToEu(),VN[GTUtility.getTierByVoltage(LaserToEu())])))
                .addCustom(tl -> tl.add(new TextComponentTranslation("激光器最大等级：%s",getTier())))
                .addCustom(tl -> tl.add(new TextComponentTranslation("激光最大换热温度温度：%s",getTemp())))
                .addEnergyTierLine(GTUtility.getTierByVoltage(this.recipeMapWorkable.getMaxVoltage()))
                .addParallelsLine(this.recipeMapWorkable.getParallelLimit()).addWorkingStatusLine()
                .addProgressLine(this.recipeMapWorkable.getProgressPercent());
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("使用高能无线激光靶仓代替能源仓，可以使用双仓升压.注意，请手动打开你的激光源仓！"));
        tooltip.add(I18n.format("最大并行：Math.pow(2, 激光靶仓等级)"));
        tooltip.add(I18n.format("耗时减免：1-激光靶仓等级*0.05"));
    }

    protected void addWarningText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, this.isStructureFormed(), false).addLowPowerLine(this.recipeMapWorkable.isHasNotEnoughEnergy()).addMaintenanceProblemLines(this.getMaintenanceProblems());
    }

    public TraceabilityPredicate autoAbilities() {
        return this.autoAbilities(true, true, true, true, true, true, true);
    }

    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn, boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut, boolean checkMuffler) {
        TraceabilityPredicate predicate = super.autoAbilities(checkMaintenance, checkMuffler);
        if (checkEnergyIn) {
            predicate = predicate.or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2).setPreviewCount(1));
        }

        if (checkItemIn && this.recipeMap.getMaxInputs() > 0) {
            predicate = predicate.or(abilities(MultiblockAbility.IMPORT_ITEMS).setPreviewCount(1));
        }

        if (checkItemOut && this.recipeMap.getMaxOutputs() > 0) {
            predicate = predicate.or(abilities(MultiblockAbility.EXPORT_ITEMS).setPreviewCount(1));
        }

        if (checkFluidIn && this.recipeMap.getMaxFluidInputs() > 0) {
            predicate = predicate.or(abilities(MultiblockAbility.IMPORT_FLUIDS).setPreviewCount(1));
        }

        if (checkFluidOut && this.recipeMap.getMaxFluidOutputs() > 0) {
            predicate = predicate.or(abilities(MultiblockAbility.EXPORT_FLUIDS).setPreviewCount(1));
        }

        return predicate;
    }

    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, this.getFrontFacing(), this.recipeMapWorkable.isActive(), this.recipeMapWorkable.isWorkingEnabled());
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setBoolean("isDistinct", this.isDistinct);
        return data;
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.isDistinct = data.getBoolean("isDistinct");
    }

    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(this.isDistinct);
    }

    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.isDistinct = buf.readBoolean();
    }

    public boolean canBeDistinct() {
        return false;
    }

    public boolean isDistinct() {
        return this.isDistinct;
    }

    public void setDistinct(boolean isDistinct) {
        this.isDistinct = isDistinct;
        this.recipeMapWorkable.onDistinctChanged();
        this.getMultiblockParts().forEach((part) -> {
            part.onDistinctChange(isDistinct);
        });
        if (this.isDistinct) {
            this.notifiedItemInputList.addAll(this.getAbilities(MultiblockAbility.IMPORT_ITEMS));
        } else {
            this.notifiedItemInputList.add(this.inputInventory);
        }

    }

    public SoundEvent getSound() {
        return this.recipeMap.getSound();
    }

    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ArrayList();
        if (this.recipeMapWorkable.getMaxProgress() > 0) {
            list.add(new TextComponentTranslation("behavior.tricorder.workable_progress", (new TextComponentTranslation(TextFormattingUtil.formatNumbers(this.recipeMapWorkable.getProgress() / 20))).setStyle((new Style()).setColor(TextFormatting.GREEN)), (new TextComponentTranslation(TextFormattingUtil.formatNumbers(this.recipeMapWorkable.getMaxProgress() / 20))).setStyle((new Style()).setColor(TextFormatting.YELLOW))));
        }

        if (this.recipeMapWorkable.getRecipeEUt() > 0) {
            list.add(new TextComponentTranslation("behavior.tricorder.workable_consumption", (new TextComponentTranslation(TextFormattingUtil.formatNumbers(this.recipeMapWorkable.getRecipeEUt()))).setStyle((new Style()).setColor(TextFormatting.RED)), (new TextComponentTranslation(TextFormattingUtil.formatNumbers(this.recipeMapWorkable.getRecipeEUt() == 0 ? 0L : 1L))).setStyle((new Style()).setColor(TextFormatting.RED))));
        }

        if (ConfigHolder.machines.enableMaintenance && this.hasMaintenanceMechanics()) {
            list.add(new TextComponentTranslation("behavior.tricorder.multiblock_maintenance", (new TextComponentTranslation(TextFormattingUtil.formatNumbers(this.getNumMaintenanceProblems()))).setStyle((new Style()).setColor(TextFormatting.RED))));
        }

        if (this.recipeMapWorkable.getParallelLimit() > 1) {
            list.add(new TextComponentTranslation("behavior.tricorder.multiblock_parallel", (new TextComponentTranslation(TextFormattingUtil.formatNumbers(this.recipeMapWorkable.getParallelLimit()))).setStyle((new Style()).setColor(TextFormatting.GREEN))));
        }

        return list;
    }

    public ICleanroomProvider getCleanroom() {
        return this.cleanroom;
    }

    public void setCleanroom(ICleanroomProvider provider) {
        this.cleanroom = provider;
    }

    public int getTier() {
        if (!isStructureFormed()) return 0;
        if (this.getAbilities(LASER_INPUT) == null || inputNum == 0) return 0;

        int maxTier = 0;
        for (ILaser laser : this.getAbilities(LASER_INPUT)) {
            int tier = GTUtility.getTierByVoltage(laser.Laser());
            if (tier > maxTier) {
                maxTier = tier;
            }
        }
        return maxTier;
    }

    public int getMaxLaser() {
        if (!isStructureFormed()) return 0;
        if (this.getAbilities(LASER_INPUT) == null || inputNum == 0) return 0;

        int maxLaserTier = 0;
        for (ILaser laser : this.getAbilities(LASER_INPUT)) {
            int tier = GTUtility.getTierByVoltage(laser.MaxLaser());
            if (tier > maxLaserTier) {
                maxLaserTier = tier;
            }
        }
        return maxLaserTier;
    }

    public int getTemp() {
        return getTier() * 900 + 900;
    }

    public long LaserToEu() {
        if (!isStructureFormed()) return 0;
        if (this.getAbilities(LASER_INPUT) == null || inputNum == 0) return 0;

        long totalEu = 0;
        for (ILaser laser : this.getAbilities(LASER_INPUT)) {
            totalEu += laser.Laser();
        }
        return totalEu;
    }

    public int getParallelLimit() {
        return (int) Math.pow(2, getTier());
    }


}
