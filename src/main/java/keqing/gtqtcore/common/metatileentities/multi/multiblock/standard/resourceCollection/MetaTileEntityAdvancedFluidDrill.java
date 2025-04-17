package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.resourceCollection;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import com.google.common.collect.Lists;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.IWorkable;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.metatileentity.ITieredMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.api.worldgen.bedrockFluids.BedrockFluidVeinHandler;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;

import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing1.CasingType.*;

public class MetaTileEntityAdvancedFluidDrill extends MultiblockWithDisplayBase implements ITieredMetaTileEntity, IWorkable, IProgressBarMultiblock {

    private final AdvancedFluidDrillLogic minerLogic = new AdvancedFluidDrillLogic(this);
    private final int tier;
    protected IMultipleTankHandler inputFluidInventory;
    protected IMultipleTankHandler outputFluidInventory;
    protected IEnergyContainer energyContainer;

    public MetaTileEntityAdvancedFluidDrill(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId);
        this.tier = tier;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAdvancedFluidDrill(metaTileEntityId, tier);
    }

    protected void initializeAbilities() {
        this.inputFluidInventory = new FluidTankList(true, this.getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.outputFluidInventory = new FluidTankList(true, this.getAbilities(MultiblockAbility.EXPORT_FLUIDS));
        this.energyContainer = new EnergyContainerList(this.getAbilities(MultiblockAbility.INPUT_ENERGY));
    }

    private void resetTileAbilities() {
        this.inputFluidInventory = new FluidTankList(true);
        this.outputFluidInventory = new FluidTankList(true);
        this.energyContainer = new EnergyContainerList(Lists.newArrayList());
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.initializeAbilities();
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.resetTileAbilities();
    }

    @Override
    protected void updateFormedValid() {
        this.minerLogic.performDrilling();
        if (!this.getWorld().isRemote && this.minerLogic.needUpdate) {
            this.minerLogic.setNeedUpdate(false);
            this.minerLogic.setActive(false);
        }

    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "#F#", "#F#", "#F#", "###", "###", "###")
                .aisle("XXX", "FCF", "FCF", "FCF", "#F#", "#F#", "#F#")
                .aisle("XSX", "#F#", "#F#", "#F#", "###", "###", "###")
                .where('S', this.selfPredicate())
                .where('X', states(getCasingState())
                        .setMinGlobalLimited(3)
                        .or(abilities(MultiblockAbility.INPUT_ENERGY)
                                .setMinGlobalLimited(1)
                                .setMaxGlobalLimited(3))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS)
                                .setMaxGlobalLimited(1)))
                .where('C', states(getCasingState()))
                .where('F', getFrameState())
                .where('#', any()).build();
    }

    private IBlockState getCasingState() {
        if (this.tier == 5) {
            return GTQTMetaBlocks.blockMultiblockCasing1.getState(MaragingSteel250);
        } else if (this.tier == 6) {
            return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.HSSE_STURDY);
        } else {
            return GTQTMetaBlocks.blockMultiblockCasing1.getState(this.tier == 7 ? IncoloyMA813 : HastelloyX78);
        }
    }

    private TraceabilityPredicate getFrameState() {
        if (this.tier == 5) {
            return frames(GTQTMaterials.MaragingSteel250);
        } else if (this.tier == 6) {
            return frames(Materials.HSSE);
        } else {
            return this.tier == 7 ? frames(GTQTMaterials.IncoloyMA813) : frames(GTQTMaterials.HastelloyX78);
        }
    }

    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        if (this.tier == 5) {
            return GTQTTextures.MaragingSteel250;
        } else if (this.tier == 6) {
            return Textures.STURDY_HSSE_CASING;
        } else {
            return this.tier == 7 ? GTQTTextures.IncoloyMA813 : GTQTTextures.HastelloyX78;
        }
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, this.isStructureFormed())
                .setWorkingStatus(this.minerLogic.isWorkingEnabled, this.minerLogic.isActive)
                .setWorkingStatusKeys("gregtech.multiblock.idling", "gregtech.multiblock.work_paused", "gregtech.multiblock.miner.drilling")
                .addEnergyUsageLine(this.energyContainer).addCustom((tl) -> {
                    if (this.isStructureFormed()) {
                        if (this.minerLogic.getDrilledFluid() != null) {
                            Fluid drilledFluid = this.minerLogic.getDrilledFluid();
                            ITextComponent fluidInfo = TextComponentUtil.setColor(GTUtility.getFluidTranslation(drilledFluid), TextFormatting.GREEN);
                            tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "gregtech.multiblock.fluid_rig.drilled_fluid", fluidInfo));
                            ITextComponent amountInfo = TextComponentUtil.stringWithColor(TextFormatting.BLUE, TextFormattingUtil.formatNumbers((long) this.minerLogic.getFluidToProduce() * 20L / 20L) + " L/t");
                            tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "gregtech.multiblock.fluid_rig.fluid_amount", amountInfo));
                        } else {
                            ITextComponent noFluid = TextComponentUtil.translationWithColor(TextFormatting.RED, "gregtech.multiblock.fluid_rig.no_fluid_in_area");
                            tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "gregtech.multiblock.fluid_rig.drilled_fluid", noFluid));
                        }
                    }
                })
                .addWorkingStatusLine()
                .addProgressLine(this.minerLogic.getProgressPercent());
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, this.isStructureFormed(), false)
                .addLowPowerLine(this.isStructureFormed() && !this.drainEnergy(true))
                .addCustom((tl) -> {
                    if (this.isStructureFormed() && this.minerLogic.isInventoryFull) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.YELLOW, "gregtech.machine.miner.invfull"));
                    }
                });
    }

    @Override
    public void addInformation(ItemStack stack,
                               World world,
                               List<String> tooltip,
                               boolean advanced) {
        tooltip.add(I18n.format("gregtech.machine.fluid_drilling_rig.description"));
        tooltip.add(I18n.format("gregtech.machine.fluid_drilling_rig.depletion", TextFormattingUtil.formatNumbers(100.0 / (double) this.getDepletionChance())));
        tooltip.add(I18n.format("gregtech.universal.tooltip.energy_tier_range", GTValues.VNF[this.tier], GTValues.VNF[this.tier + 1]));
        tooltip.add(I18n.format("gregtech.machine.fluid_drilling_rig.production", this.getRigMultiplier(), TextFormattingUtil.formatNumbers((double) this.getRigMultiplier() * 1.5)));
        if (this.tier > 2) {
            tooltip.add(I18n.format("gregtech.machine.fluid_drilling_rig.shows_depletion"));
        }

    }

    @Override
    public void addToolUsages(ItemStack stack,
                              World world,
                              List<String> tooltip,
                              boolean advanced) {
        tooltip.add(I18n.format("gregtech.tool_action.screwdriver.access_covers"));
        tooltip.add(I18n.format("gregtech.tool_action.wrench.set_facing"));
        super.addToolUsages(stack, world, tooltip, advanced);
    }

    public int getTier() {
        return this.tier;
    }

    public int getRigMultiplier() {
        if (this.tier == 5) {
            return 128;
        } else if (this.tier == 6) {
            return 256;
        } else {
            return this.tier == 7 ? 1024 : 4096;
        }
    }

    public int getDepletionChance() {
        if (this.tier == 5) {
            return 16;
        } else if (this.tier == 6) {
            return 32;
        } else {
            return this.tier == 7 ? 64 : 128;
        }
    }

    @SideOnly(Side.CLIENT)
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FLUID_RIG_OVERLAY;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState,
                                     Matrix4 translation,
                                     IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, this.getFrontFacing(), this.minerLogic.isActive, this.minerLogic.isWorkingEnabled);
    }

    @Override
    public boolean isWorkingEnabled() {
        return this.minerLogic.isWorkingEnabled;
    }

    @Override
    public void setWorkingEnabled(boolean isActivationAllowed) {
        this.minerLogic.setWorkingEnabled(isActivationAllowed);
    }

    public boolean fillTanks(FluidStack stack, boolean simulate) {
        return GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, simulate, Collections.singletonList(stack));
    }

    public int getEnergyTier() {
        return this.energyContainer == null ? this.tier : Math.min(this.tier + 1, Math.max(this.tier, GTUtility.getFloorTierByVoltage(this.energyContainer.getInputVoltage())));
    }

    public long getEnergyInputPerSecond() {
        return this.energyContainer.getInputPerSec();
    }

    public boolean drainEnergy(boolean simulate) {
        long energyToDrain = GTValues.VA[this.getEnergyTier()];
        long resultEnergy = this.energyContainer.getEnergyStored() - energyToDrain;
        if (resultEnergy >= 0L && resultEnergy <= this.energyContainer.getEnergyCapacity()) {
            if (!simulate) {
                this.energyContainer.changeEnergy(-energyToDrain);
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        return this.minerLogic.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.minerLogic.readFromNBT(data);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        this.minerLogic.writeInitialSyncData(buf);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.minerLogic.receiveInitialSyncData(buf);
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        this.minerLogic.receiveCustomData(dataId, buf);
    }

    @Override
    public int getProgress() {
        return this.minerLogic.progressTime;
    }

    @Override
    public int getMaxProgress() {
        return 20;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_WORKABLE) {
            return GregtechTileCapabilities.CAPABILITY_WORKABLE.cast(this);
        } else {
            return capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE ? GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this) : super.getCapability(capability, side);
        }
    }

    @Override
    protected boolean shouldShowVoidingModeButton() {
        return false;
    }

    @Override
    public boolean showProgressBar() {
        return this.tier > 2;
    }

    @Override
    public double getFillPercentage(int index) {
        int numOperationsLeft = BedrockFluidVeinHandler.getOperationsRemaining(this.getWorld(), this.minerLogic.getChunkX(), this.minerLogic.getChunkZ());
        int maxOperations = 100000;
        return (double) numOperationsLeft / (double) maxOperations;
    }

    @Override
    public TextureArea getProgressBarTexture(int index) {
        return GuiTextures.PROGRESS_BAR_FLUID_RIG_DEPLETION;
    }

    @Override
    public void addBarHoverText(List<ITextComponent> hoverList, int index) {
        int numOperationsLeft = BedrockFluidVeinHandler.getOperationsRemaining(this.getWorld(), this.minerLogic.getChunkX(), this.minerLogic.getChunkZ());
        int maxOperations = 100000;
        int percentage = (int) Math.round((double) numOperationsLeft / (double) maxOperations * 100.0);
        TextFormatting color = percentage > 40 ? TextFormatting.GREEN : (percentage > 10 ? TextFormatting.YELLOW : TextFormatting.RED);
        if (numOperationsLeft == 0) {
            hoverList.add(TextComponentUtil.translationWithColor(TextFormatting.RED, "gregtech.multiblock.fluid_rig.vein_depleted"));
        } else {
            ITextComponent veinInfo = TextComponentUtil.stringWithColor(color, percentage + "%");
            hoverList.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "gregtech.multiblock.fluid_rig.vein_depletion", veinInfo));
        }

    }

    @Override
    public boolean allowsExtendedFacing() {
        return false;
    }
}