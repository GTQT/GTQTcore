package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.kqcc;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
import gregtech.api.capability.IDataAccessHatch;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockCleanroomCasing;
import gregtech.common.blocks.BlockMultiblockCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.BlockComputerCasing.CasingType;
import gregtech.core.sound.GTSoundEvents;
import java.util.ArrayList;
import java.util.List;

import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTPowerSupply;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MetaTileEntityMiniDataBank extends MultiblockWithDisplayBase implements IControllable {
    private static final int EUT_PER_HATCH;
    private static final int EUT_PER_HATCH_CHAINED;
    private IEnergyContainer energyContainer = new EnergyContainerList(new ArrayList());
    private boolean isActive = false;
    private boolean isWorkingEnabled = true;
    protected boolean hasNotEnoughEnergy;
    private int energyUsage = 0;

    public MetaTileEntityMiniDataBank(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMiniDataBank(this.metaTileEntityId);
    }

    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.energyContainer = new EnergyContainerList(this.getAbilities(MultiblockAbility.INPUT_ENERGY));
        this.energyUsage = this.calculateEnergyUsage();
    }

    protected int calculateEnergyUsage() {
        int receivers = this.getAbilities(MultiblockAbility.OPTICAL_DATA_RECEPTION).size();
        int transmitters = this.getAbilities(MultiblockAbility.OPTICAL_DATA_TRANSMISSION).size();
        int regulars = this.getAbilities(MultiblockAbility.DATA_ACCESS_HATCH).size();
        int dataHatches = receivers + transmitters + regulars;
        int eutPerHatch = receivers > 0 ? EUT_PER_HATCH_CHAINED : EUT_PER_HATCH;
        return eutPerHatch * dataHatches;
    }

    public void invalidateStructure() {
        super.invalidateStructure();
        this.energyContainer = new EnergyContainerList(new ArrayList());
        this.energyUsage = 0;
    }

    protected void updateFormedValid() {
        int energyToConsume = this.getEnergyUsage();
        boolean hasMaintenance = ConfigHolder.machines.enableMaintenance && this.hasMaintenanceMechanics();
        if (hasMaintenance) {
            energyToConsume += this.getNumMaintenanceProblems() * energyToConsume / 10;
        }

        if (this.hasNotEnoughEnergy && this.energyContainer.getInputPerSec() > 19L * (long)energyToConsume) {
            this.hasNotEnoughEnergy = false;
        }

        if (this.energyContainer.getEnergyStored() >= (long)energyToConsume) {
            if (!this.hasNotEnoughEnergy) {
                long consumed = this.energyContainer.removeEnergy((long)energyToConsume);
                if (consumed == (long)(-energyToConsume)) {
                    this.setActive(true);
                } else {
                    this.hasNotEnoughEnergy = true;
                    this.setActive(false);
                }
            }
        } else {
            this.hasNotEnoughEnergy = true;
            this.setActive(false);
        }

    }

    protected int getEnergyUsage() {
        return this.energyUsage;
    }

    public boolean isActive() {
        return super.isActive() && this.isActive;
    }

    public void setActive(boolean active) {
        if (this.isActive != active) {
            this.isActive = active;
            this.markDirty();
            World world = this.getWorld();
            if (world != null && !world.isRemote) {
                this.writeCustomData(GregtechDataCodes.WORKABLE_ACTIVE, (buf) -> {
                    buf.writeBoolean(active);
                });
            }
        }

    }

    public boolean isWorkingEnabled() {
        return this.isWorkingEnabled;
    }

    public void setWorkingEnabled(boolean isWorkingAllowed) {
        this.isWorkingEnabled = isWorkingAllowed;
        this.markDirty();
        World world = this.getWorld();
        if (world != null && !world.isRemote) {
            this.writeCustomData(GregtechDataCodes.WORKING_ENABLED, (buf) -> {
                buf.writeBoolean(this.isWorkingEnabled);
            });
        }

    }

    protected  BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XDDDX", "XDDDX", "XDDDX")
                .aisle("XCCCX", "XAAAX", "XCCCX")
                .aisle("XCCCX", "XCSCX", "XCCCX")
                .where('S', this.selfPredicate())
                .where('X', states(getOuterState()))
                .where('D', states(getInnerState()).setMinGlobalLimited(3)
                        .or(abilities(MultiblockAbility.DATA_ACCESS_HATCH).setPreviewCount(3))
                        .or(abilities(MultiblockAbility.OPTICAL_DATA_TRANSMISSION).setMinGlobalLimited(1, 1))
                        .or(abilities(MultiblockAbility.OPTICAL_DATA_RECEPTION).setPreviewCount(1)))
                .where('A', states(getInnerState()))
                .where('C', states(getFrontState()).setMinGlobalLimited(4)
                        .or(this.autoAbilities()).or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2).setPreviewCount(1)))
                .build();
    }

    private static  IBlockState getOuterState() {
        return MetaBlocks.CLEANROOM_CASING.getState(BlockCleanroomCasing.CasingType.FILTER_CASING);
    }

    private static  IBlockState getInnerState() {
        return GTQTMetaBlocks.POWER.getState(GTQTPowerSupply.SupplyType.POWER_SUPPLY_BASIC);
    }

    private static IBlockState getFrontState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.IRIDIUM_CASING);
    }

    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GTQTTextures.IRIDIUM_CASING;
    }

    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, this.getFrontFacing(), this.isActive(), this.isWorkingEnabled());
    }

    @SideOnly(Side.CLIENT)
    protected  ICubeRenderer getFrontOverlay() {
        return Textures.DATA_BANK_OVERLAY;
    }

    protected boolean shouldShowVoidingModeButton() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public SoundEvent getSound() {
        return GTSoundEvents.COMPUTATION;
    }

    public void addInformation(ItemStack stack,  World world,  List<String> tooltip, boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.data_bank.tooltip.1", new Object[0]));
        tooltip.add(I18n.format("gregtech.machine.data_bank.tooltip.2", new Object[0]));
        tooltip.add(I18n.format("gregtech.machine.data_bank.tooltip.3", new Object[0]));
        tooltip.add(I18n.format("gregtech.machine.data_bank.tooltip.4", new Object[]{TextFormattingUtil.formatNumbers((long)EUT_PER_HATCH)}));
        tooltip.add(I18n.format("gregtech.machine.data_bank.tooltip.5", new Object[]{TextFormattingUtil.formatNumbers((long)EUT_PER_HATCH_CHAINED)}));
    }

    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, this.isStructureFormed()).setWorkingStatus(true, this.isActive() && this.isWorkingEnabled()).setWorkingStatusKeys("gregtech.multiblock.idling", "gregtech.multiblock.idling", "gregtech.multiblock.data_bank.providing").addEnergyUsageExactLine((long)this.getEnergyUsage()).addWorkingStatusLine();
    }

    protected void addWarningText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, this.isStructureFormed(), false).addLowPowerLine(this.hasNotEnoughEnergy).addMaintenanceProblemLines(this.getMaintenanceProblems());
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setBoolean("isActive", this.isActive);
        data.setBoolean("isWorkingEnabled", this.isWorkingEnabled);
        return data;
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.isActive = data.getBoolean("isActive");
        this.isWorkingEnabled = data.getBoolean("isWorkingEnabled");
    }

    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(this.isActive);
        buf.writeBoolean(this.isWorkingEnabled);
    }

    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.isActive = buf.readBoolean();
        this.isWorkingEnabled = buf.readBoolean();
    }

    public void receiveCustomData(int dataId,  PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GregtechDataCodes.WORKABLE_ACTIVE) {
            this.isActive = buf.readBoolean();
            this.scheduleRenderUpdate();
        } else if (dataId == GregtechDataCodes.WORKING_ENABLED) {
            this.isWorkingEnabled = buf.readBoolean();
            this.scheduleRenderUpdate();
        }

    }

    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        return capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE ? GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this) : super.getCapability(capability, side);
    }

    static {
        EUT_PER_HATCH = GTValues.VA[2];
        EUT_PER_HATCH_CHAINED = GTValues.VA[4];
    }
}
