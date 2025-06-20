package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.*;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockComputerCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.GTQTCoreConfig;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.ArrayList;
import java.util.List;

import static keqing.gtqtcore.common.block.blocks.BlockQuantumCasing.CasingType.HIGH_ENERGY_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockQuantumCasing.CasingType.MOLECULAR_COIL;


public class MetaTileEntityEnergyInfuser extends MultiblockWithDisplayBase implements IControllable {
    @Override
    public boolean usesMui2() {
        return false;
    }
    protected IItemHandlerModifiable inputInventory;
    protected IItemHandlerModifiable outputInventory;
    protected IMultipleTankHandler inputFluidInventory;
    protected IEnergyContainer energyContainer;
    private boolean isWorkingEnabled = true;

    private boolean isChargingItem = false;

    public MetaTileEntityEnergyInfuser(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityEnergyInfuser(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {
        World world = getWorld();
        if (!isWorkingEnabled() || world == null || world.isRemote) return;

        boolean itemProcessed = false;
        for (int index = 0; index < inputInventory.getSlots(); index++) {
            long availableEU = energyContainer.getEnergyStored();
            if (availableEU < 1) break;

            ItemStack stackInSlot = inputInventory.getStackInSlot(index);
            if (stackInSlot.isEmpty()) continue;

            boolean isCharged = isItemFullyCharged(stackInSlot);
            boolean isRepaired = isItemFullyRepaired(stackInSlot);

            if (isCharged && isRepaired) {
                stackInSlot = inputInventory.extractItem(index, 1, true);
                // Check if there's actually space to put the item in the output bus
                if (outputInventory.getSlots() > 0 &&
                        GTTransferUtils.insertItem(outputInventory, stackInSlot, true).isEmpty()) {
                    stackInSlot = inputInventory.extractItem(index, 1, false);
                    GTTransferUtils.insertItem(outputInventory, stackInSlot, false);
                } else if (getVoidingMode() == 1 || getVoidingMode() == 3) {
                    // If the voiding mode voids items, extract the item but don't do anything with it.
                    inputInventory.extractItem(index, 1, false);
                }
            }

            if (!isCharged) {
                long usedEU = chargeItem(stackInSlot, availableEU);
                if (!itemProcessed) itemProcessed = usedEU > 0;
            }

            if (!isRepaired && inputFluidInventory.getTanks() > 0) {
                availableEU = energyContainer.getEnergyStored();
                int toRepair = Math.min(stackInSlot.getItemDamage(),
                        GTQTCoreConfig.MachineSwitch.energyInfuserSettings.maxRepairedDamagePerOperation);
                long powerCost = (long) toRepair *
                        GTQTCoreConfig.MachineSwitch.energyInfuserSettings.usedEUPerDurability;
                FluidStack toDrain = new FluidStack(Materials.UUMatter.getFluid(),
                        toRepair * GTQTCoreConfig.MachineSwitch.energyInfuserSettings.usedUUMatterPerDurability);
                if (availableEU > powerCost && inputFluidInventory.drain(toDrain, false) != null) {
                    stackInSlot.setItemDamage(Math.max(stackInSlot.getItemDamage() - toRepair, 0));
                    inputFluidInventory.drain(toDrain, true);
                    energyContainer.removeEnergy(powerCost);
                    itemProcessed = true;
                }
            }
        }

        if (itemProcessed != isActive()) {
            setActive(itemProcessed);
        }
    }

    private boolean isItemFullyCharged( ItemStack itemStack) {
        if (itemStack.hasCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null)) {
            IElectricItem electricItem = itemStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
            if (electricItem == null || !electricItem.chargeable()) return true;
            return electricItem.getCharge() >= electricItem.getMaxCharge();
        } else if (itemStack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage energyStorage = itemStack.getCapability(CapabilityEnergy.ENERGY, null);
            if (energyStorage == null || !energyStorage.canReceive()) return true;
            return energyStorage.getEnergyStored() >= energyStorage.getMaxEnergyStored();
        }

        return true;
    }

    private long chargeItem( ItemStack itemStack, long toCharge) {
        long availableEU = Math.min(toCharge, energyContainer.getInputVoltage() * energyContainer.getInputAmperage());

        if (itemStack.hasCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null)) {
            IElectricItem electricItem = itemStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
            if (electricItem == null) return 0;
            return electricItem.charge(availableEU, GTUtility.getFloorTierByVoltage(energyContainer.getInputVoltage()),
                    true, false);
        } else if (itemStack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage energyStorage = itemStack.getCapability(CapabilityEnergy.ENERGY, null);
            if (energyStorage == null) return 0;
            return FeCompat.insertEu(energyStorage, availableEU);
        }

        return 0;
    }

    private boolean isItemFullyRepaired( ItemStack itemStack) {
        Item item = itemStack.getItem();
        return !item.isRepairable() || item.getDamage(itemStack) <= 0;
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(isWorkingEnabled);
        buf.writeBoolean(isChargingItem);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        isWorkingEnabled = buf.readBoolean();
        isChargingItem = buf.readBoolean();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);

        if (dataId == GregtechDataCodes.WORKABLE_ACTIVE) {
            isChargingItem = buf.readBoolean();
            scheduleRenderUpdate();
        } else if (dataId == GregtechDataCodes.WORKING_ENABLED) {
            isWorkingEnabled = buf.readBoolean();
            scheduleRenderUpdate();
        }
    }

    private void initializeAbilities() {
        this.inputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.inputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.outputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.INPUT_ENERGY));
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("HHH", "CCC", "MMM", "CCC", "HHH")
                .aisle("HHH", "CMC", "MMM", "CMC", "HHH")
                .aisle("HHH", "CCC", "MSM", "CCC", "HHH")
                .where('S', selfPredicate())
                .where('H', states(getCasingState())
                        .setMinGlobalLimited(9)
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS)
                                .setExactLimit(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS)
                                .setMaxGlobalLimited(1)
                                .setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY, MultiblockAbility.SUBSTATION_INPUT_ENERGY,
                                MultiblockAbility.INPUT_LASER)
                                .setMinGlobalLimited(1)))
                .where('C', states(getCoilState()))
                .where('M', states(getMolecularCasingState()))
                .build();
    }

    private IBlockState getCasingState() {
        return MetaBlocks.COMPUTER_CASING.getState(BlockComputerCasing.CasingType.HIGH_POWER_CASING);
    }

    private IBlockState getCoilState() {
        return GTQTMetaBlocks.blockQuantumCasing.getState(MOLECULAR_COIL);
    }

    private IBlockState getMolecularCasingState() {
        return GTQTMetaBlocks.blockQuantumCasing.getState(HIGH_ENERGY_CASING);
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        List<MultiblockShapeInfo> shapeInfoList = new ArrayList<>();

        shapeInfoList.add(MultiblockShapeInfo.builder()
                .aisle("HHH", "CCC", "MMM", "CCC", "HHH")
                .aisle("HHH", "CMC", "MMM", "CMC", "HHH")
                .aisle("IEO", "CCC", "MSM", "CCC", "HHH")
                .where('S', GTQTMetaTileEntities.ENERGY_INFUSER, EnumFacing.SOUTH)
                .where('H', getCasingState())
                .where('C', getCoilState())
                .where('M', getMolecularCasingState())
                .where('I', MetaTileEntities.ITEM_IMPORT_BUS[GTValues.LV], EnumFacing.SOUTH)
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.IV], EnumFacing.SOUTH)
                .where('O', MetaTileEntities.ITEM_EXPORT_BUS[GTValues.LV], EnumFacing.SOUTH)
                .build());

        return shapeInfoList;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), isActive(),
                isWorkingEnabled());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return iMultiblockPart == null ? GTQTTextures.HIGH_ENERGY_CASING : Textures.HIGH_POWER_CASING;
    }

    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.DATA_BANK_OVERLAY;
    }

    @Override
    public boolean isActive() {
        return super.isActive() && isChargingItem;
    }

    private void setActive(boolean active) {
        this.isChargingItem = active;
        markDirty();
        GTQTUtil.writeCustomData(this, getWorld(), GregtechDataCodes.WORKABLE_ACTIVE,
                buf -> buf.writeBoolean(isChargingItem));
    }

    @Override
    public boolean isWorkingEnabled() {
        return this.isWorkingEnabled;
    }

    @Override
    public void setWorkingEnabled(boolean isWorkingAllowed) {
        this.isWorkingEnabled = isWorkingAllowed;
        markDirty();
        GTQTUtil.writeCustomData(this, getWorld(), GregtechDataCodes.WORKING_ENABLED,
                buf -> buf.writeBoolean(isWorkingEnabled));
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE) {
            return GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this);
        }

        return super.getCapability(capability, side);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);

        data.setBoolean("IsWorkingEnabled", isWorkingEnabled);

        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        isWorkingEnabled = data.getBoolean("IsWorkingEnabled");
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip,
                               boolean advanced) {
        tooltip.add(I18n.format("gtqtcore.machine.energy_infuser.tooltip.0"));
        tooltip.add(I18n.format("gtqtcore.machine.energy_infuser.tooltip.1",
                GTQTCoreConfig.MachineSwitch.energyInfuserSettings.usedUUMatterPerDurability));
        tooltip.add(I18n.format("gtqtcore.multiblock.kq.laser.tooltip"));
    }
}
