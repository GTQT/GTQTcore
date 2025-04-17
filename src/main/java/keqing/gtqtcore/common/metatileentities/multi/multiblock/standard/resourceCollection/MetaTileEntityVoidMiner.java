package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.resourceCollection;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import com.google.common.collect.Lists;
import gregtech.api.GTValues;
import gregtech.api.capability.*;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.capability.IVoidMinerProvider;
import keqing.gtqtcore.api.capability.impl.VoidMinerLogic;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

import static gregtech.api.util.TextFormattingUtil.formatNumbers;

import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing6.CasingType.*;

public class MetaTileEntityVoidMiner extends MultiblockWithDisplayBase implements IWorkable, IVoidMinerProvider {

    private final VoidMinerLogic logic;

    private final int maxTemperature;
    private final int tier;
    private final long energyConsumed;
    protected IMultipleTankHandler importFluidHandler;
    protected IMultipleTankHandler exportFluidHandler;
    protected IItemHandlerModifiable outputInventory;
    private IEnergyContainer energyContainer;

    /* ------------------------------- MetaTileEntity constructors ------------------------------- */
    public MetaTileEntityVoidMiner(ResourceLocation metaTileEntityId, int tier, int maxTemperature) {
        super(metaTileEntityId);
        this.tier = tier;
        this.energyConsumed = GTValues.V[tier];
        this.maxTemperature = maxTemperature;
        this.logic = new VoidMinerLogic(this, tier, maxTemperature);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityVoidMiner(metaTileEntityId, this.tier, this.maxTemperature);
    }

    /* ----------------------------- Create MetaTileEntity structure ----------------------------- */

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.initializeAbilities();
        this.logic.setMaxProgress(10 * SECOND);
    }

    private void initializeAbilities() {
        this.importFluidHandler = new FluidTankList(true, this.getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.exportFluidHandler = new FluidTankList(true, this.getAbilities(MultiblockAbility.EXPORT_FLUIDS));
        this.outputInventory = new ItemHandlerList(this.getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.energyContainer = new EnergyContainerList(this.getAbilities(MultiblockAbility.INPUT_ENERGY));
    }

    @Override
    public void checkStructurePattern() {
        if (!this.isStructureFormed()) {
            this.reinitializeStructurePattern();
        }
        super.checkStructurePattern();
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.resetTileAbilities();
        this.logic.invalidate();
    }

    private void resetTileAbilities() {
        this.importFluidHandler = new FluidTankList(true);
        this.exportFluidHandler = new FluidTankList(true);
        this.outputInventory = new ItemStackHandler(0);
        this.energyContainer = new EnergyContainerList(Lists.newArrayList());
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("F   F", "F   F", "CCCCC", "     ", "     ", "     ", "     ", "     ", "     ", "     ")
                .aisle("     ", "     ", "CCCCC", " CCC ", "  F  ", "  F  ", "  F  ", "     ", "     ", "     ")
                .aisle("     ", "     ", "CCCCC", " CCC ", " FCF ", " FCF ", " FCF ", "  F  ", "  F  ", "  F  ")
                .aisle("     ", "     ", "CCCCC", " CSC ", "  F  ", "  F  ", "  F  ", "     ", "     ", "     ")
                .aisle("F   F", "F   F", "CCCCC", "     ", "     ", "     ", "     ", "     ", "     ", "     ")
                .where('S', this.selfPredicate())
                .where('C', states(this.getCasingState())
                        .setMinGlobalLimited(24)
                        .or(abilities(MultiblockAbility.INPUT_ENERGY)
                                .setMinGlobalLimited(1)
                                .setMaxGlobalLimited(8)
                                .setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS)
                                .setMinGlobalLimited(1)
                                .setMaxGlobalLimited(3)
                                .setPreviewCount(3))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS)
                                .setMinGlobalLimited(1)
                                .setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS)
                                .setMinGlobalLimited(1)
                                .setPreviewCount(1)))
                .where('F', this.tier == GTValues.LuV ? frames(Materials.RhodiumPlatedPalladium)
                        : (this.tier == GTValues.ZPM ? frames(Materials.Osmiridium) : frames(Materials.Neutronium)))
                .where(' ', any())
                .build();
    }

    private IBlockState getCasingState() {
        if (this.tier == GTValues.LuV) {
            return GTQTMetaBlocks.blockMultiblockCasing6.getState(RHODIUM);
        } else if (this.tier == GTValues.ZPM) {
            return GTQTMetaBlocks.blockMultiblockCasing6.getState(OSMIRIDIUM);
        } else if (this.tier == GTValues.UV) {
            return GTQTMetaBlocks.blockMultiblockCasing6.getState(NEUTRONIUM);
        } else { // rick-roll
            return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart texture) {
        if (this.tier == GTValues.LuV) {
            return GTQTTextures.RHODIUM_PLATED_PALLADIUM_CASING;
        } else if (this.tier == GTValues.ZPM) {
            return GTQTTextures.OSMIRIDIUM_CASING;
        } else if (this.tier == GTValues.UV) {
            return GTQTTextures.NEUTRONIUM_CASING;
        } else {
            return Textures.ROBUST_TUNGSTENSTEEL_CASING;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.LARGE_MINER_OVERLAY_ADVANCED_2;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState,
                                     Matrix4 translation,
                                     IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation,
                pipeline, getFrontFacing(), this.logic.isActive(),
                this.logic.isWorkingEnabled());
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, this.isStructureFormed())
                .setWorkingStatus(this.logic.isWorkingEnabled(), this.logic.isActive())
                .addCustom(tl -> {
                    if (this.isStructureFormed()) {
                        ITextComponent energyUsedInfo = TextComponentUtil.stringWithColor(
                                TextFormatting.YELLOW,
                                formatNumbers(this.energyConsumed)
                        );
                        tl.add(TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "gtqtcore.machine.void_miner.max_energy_per_tick",
                                energyUsedInfo,
                                GTValues.VNF[GTUtility.getFloorTierByVoltage(this.energyConsumed)]
                        ));
                        ITextComponent temperatureInfo = TextComponentUtil.stringWithColor(
                                TextFormatting.RED,
                                formatNumbers(this.logic.getTemperature())
                        );
                        ITextComponent maxTemperatureInfo = TextComponentUtil.stringWithColor(
                                TextFormatting.DARK_RED,
                                formatNumbers(this.maxTemperature)
                        );
                        tl.add(TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "gtqtcore.machine.void_miner.temperature",
                                temperatureInfo,
                                maxTemperatureInfo
                        ));
                        ITextComponent drillingMudInfo = TextComponentUtil.stringWithColor(
                                TextFormatting.AQUA,
                                formatNumbers(this.logic.getCurrentDrillingFluid())
                        );
                        tl.add(TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "gtqtcore.machine.void_miner.drilling_mud_amount",
                                drillingMudInfo
                        ));
                    }
                })
                .addWorkingStatusLine()
                .addProgressLine(this.getProgressPercent() / 100.0);
    }

    public int getProgressPercent() {
        return this.logic.getProgressPercent();
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, this.isStructureFormed(), false)
                .addLowPowerLine(!this.drainEnergy(true))
                .addCustom(tl -> {
                    if (this.isStructureFormed()) {
                        if (this.logic.isFluidOutputInventoryFull()) {
                            tl.add(TextComponentUtil.translationWithColor(
                                    TextFormatting.YELLOW,
                                    "gtqtcore.machine.void_miner.fluid_output_full"
                            ));
                        }
                        if (this.logic.isOverheatStatus()) {
                            tl.add(TextComponentUtil.translationWithColor(
                                    TextFormatting.RED,
                                    "gtqtcore.machine.void_miner.overheat_status"
                            ));
                        }
                    }
                })
                .addMaintenanceProblemLines(this.getMaintenanceProblems());
    }

    @Override
    public boolean drainEnergy(boolean simulate) {
        long energyToDrain = GTValues.VA[this.tier];
        long resultEnergy = this.energyContainer.getEnergyStored() - energyToDrain;
        if (resultEnergy >= 0L && resultEnergy <= this.energyContainer.getEnergyCapacity()) {
            if (!simulate)
                this.energyContainer.changeEnergy(-energyToDrain);
            return true;
        }
        return false;
    }

    @Override
    public IMultipleTankHandler getImportFluidHandler() {
        return this.importFluidHandler;
    }

    @Override
    public IMultipleTankHandler getExportFluidHandler() {
        return this.exportFluidHandler;
    }

    @Override
    public IItemHandlerModifiable getOutputInventory() {
        return this.outputInventory;
    }

    @Override
    public void addInformation(ItemStack stack,
                               World world,
                               List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        // FIXME its error now.
        // int d = (int) (this.logic.getConsumeMultiplier());
        // int s = (int) (d - Math.floor(this.logic.getConsumeMultiplier())) * 100;
        // int f = (int) (100 * (1.0 - 1.0 / d));
        tooltip.add(I18n.format("gtqtcore.machine.void_miner.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.void_miner.tooltip.2", GTValues.V[this.tier], GTValues.VNF[this.tier]));
        tooltip.add(I18n.format("gtqtcore.machine.void_miner.tooltip.3", this.maxTemperature));
        tooltip.add(I18n.format("gtqtcore.machine.void_miner.tooltip.4"));
        tooltip.add(I18n.format("gtqtcore.machine.void_miner.tooltip.5"));
        // tooltip.add(I18n.format("gtqtcore.machine.void_miner.tooltip.6", s, d, f));
        tooltip.add(I18n.format("gtqtcore.machine.void_miner.tooltip.6"));
        tooltip.add(I18n.format("gtqtcore.machine.void_miner.tooltip.7"));
        tooltip.add(I18n.format("gtqtcore.machine.void_miner.tooltip.8"));
        tooltip.add(I18n.format("gtqtcore.machine.void_miner.tooltip.9"));
        tooltip.add(I18n.format("gtqtcore.machine.void_miner.tooltip.10"));
        tooltip.add(I18n.format("gtqtcore.machine.void_miner.tooltip.11"));
    }

    @Override
    public String[] getDescription() {
        return super.getDescription(); // Todo
    }

    /* -------------------------------- Sync MetaTileEntity datas -------------------------------- */
    @Override
    protected void updateFormedValid() {
        if (!this.getWorld().isRemote) {
            this.logic.update();
            if (this.logic.wasActiveAndNeedsUpdate()) {
                this.logic.setWasActiveAndNeedsUpdate(false);
                this.logic.setActive(false);
            }
        }
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GregtechDataCodes.WORKABLE_ACTIVE) {
            this.logic.setActive(buf.readBoolean());
            this.scheduleRenderUpdate();
        } else if (dataId == GregtechDataCodes.WORKING_ENABLED) {
            this.logic.setWorkingEnabled(buf.readBoolean());
            this.scheduleRenderUpdate();
        }

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        return this.logic.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.reinitializeStructurePattern();
        this.logic.readFromNBT(data);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        this.logic.writeInitialSyncData(buf);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.logic.receiveInitialSyncData(buf);
    }

    /* ---------------------------------- MetaTileEntity Logics ---------------------------------- */

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    protected boolean shouldShowVoidingModeButton() {
        return false;
    }

    @Override
    public SoundEvent getSound() {
        return GTSoundEvents.MINER;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_WORKABLE)
            return GregtechTileCapabilities.CAPABILITY_WORKABLE.cast(this);
        if (capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE)
            return GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this);
        return super.getCapability(capability, side);
    }

    @Override
    public boolean isWorkingEnabled() {
        return this.logic.isWorkingEnabled();
    }

    @Override
    public void setWorkingEnabled(boolean isWorkingAllowed) {
        this.logic.setWorkingEnabled(isWorkingAllowed);
    }

    @Override
    public int getProgress() {
        return (int) logic.getProgressTime();
    }

    @Override
    public int getMaxProgress() {
        return (int) logic.getMaxProgress();
    }

    @Override
    public boolean isActive() {
        return super.isActive() && this.logic.isActive();
    }

}