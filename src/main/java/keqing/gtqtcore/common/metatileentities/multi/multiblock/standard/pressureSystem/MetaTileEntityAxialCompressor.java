package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.pressureSystem;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import com.google.common.collect.Lists;
import gregtech.api.GTValues;
import gregtech.api.block.VariantActiveBlock;
import gregtech.api.capability.*;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.TextFieldWidget2;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.*;
import keqing.gtqtcore.api.GCYSValues;
import keqing.gtqtcore.api.capability.IPressureContainer;
import keqing.gtqtcore.api.metatileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.api.utils.NumberFormattingUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static keqing.gtqtcore.common.block.blocks.BlockActiveUniqueCasing1.ActiveCasingType.ADVANCED_AIRFOIL;
import static keqing.gtqtcore.common.block.blocks.BlockActiveUniqueCasing1.ActiveCasingType.AIRFOIL;

public class MetaTileEntityAxialCompressor extends MultiblockWithDisplayBase implements IWorkable {
    @Override
    public boolean usesMui2() {
        return false;
    }
    public static final int MAX_PROGRESS = 20;
    public static final int FLUID_USE_AMOUNT = 100;
    protected static final FluidStack LUBRICANT = Materials.Lubricant.getFluid(1);
    protected final int tier;

    protected final double maxPressure;
    protected final double pressureRate;

    protected double targetPressure;

    protected IMultipleTankHandler inputFluidInventory;
    protected IEnergyContainer energyContainer;
    protected boolean lastActive;
    protected List<BlockPos> variantActiveBlocks;
    private int progressTime = 0;
    private boolean isActive;
    private boolean isWorkingEnabled = true;
    private boolean wasActiveAndNeedsUpdate;
    private boolean hasNotEnoughEnergy;

    public MetaTileEntityAxialCompressor(ResourceLocation metaTileEntityId, int tier, double maxPressure, double pressureRate) {
        super(metaTileEntityId);
        this.tier = tier;
        this.maxPressure = maxPressure;
        this.pressureRate = pressureRate;
    }

    @Nonnull
    public static Function<String, String> getTextFieldValidator() {
        return val -> {
            if (val.isEmpty()) return "" + GCYSValues.EARTH_PRESSURE;
            return val;
        };
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityAxialCompressor(metaTileEntityId, this.tier, this.maxPressure, this.pressureRate);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
        this.variantActiveBlocks = context.getOrDefault("VABlock", new LinkedList<>());
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        resetTileAbilities();
        this.replaceVariantBlocksActive(false);
        this.variantActiveBlocks.clear();
        this.lastActive = false;
    }

    protected void replaceVariantBlocksActive(boolean isActive) {
        if (this.variantActiveBlocks != null) {
            for (BlockPos blockPos : this.variantActiveBlocks) {
                IBlockState blockState = this.getWorld().getBlockState(blockPos);
                if (blockState.getBlock() instanceof VariantActiveBlock<?> variantActiveBlock) {
                    VariantActiveBlock.setBlockActive(this.getWorld().provider.getDimension(), blockPos, isActive); // TODO
                }
            }
        }
    }

    public void onRemoval() {
        super.onRemoval();
        if (!this.getWorld().isRemote && this.isStructureFormed()) {
            this.replaceVariantBlocksActive(false);
            this.variantActiveBlocks.clear();
            this.lastActive = false;
        }
    }

    protected void initializeAbilities() {
        this.inputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.INPUT_ENERGY));
    }

    protected void resetTileAbilities() {
        this.inputFluidInventory = new FluidTankList(true);
        this.energyContainer = new EnergyContainerList(Lists.newArrayList());
    }

    @Override
    protected BlockPattern createStructurePattern() {
        if (tier == GTValues.LuV) {
            return FactoryBlockPattern.start()
                    .aisle("  F  ", "  F  ", " XXX ", " XPX ", " XXX ", "  F  ", "  F  ")
                    .aisle("     ", "     ", " XXX ", " XAX ", " XXX ", "     ", "     ").setRepeatable(2)
                    .aisle("     ", "  X  ", " XTX ", "GTATX", " XTX ", "  G  ", "     ")
                    .aisle("     ", "  X  ", "XTATX", "GAAAG", "XTATX", " XGX ", "     ").setRepeatable(3)
                    .aisle("F   F", "FXXXF", "XBTBX", "XTSTX", "XBTBX", "FXXXF", "F   F")
                    .where('S', selfPredicate())
                    .where('F', states(getFrameState()))
                    .where('X', states(getCasingState()).setMinGlobalLimited(50)
                            .or(autoAbilities())
                            .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setExactLimit(1))
                            .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3)))
                    .where('G', states(getGlassState()))
                    .where('B', states(MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.EXTREME_ENGINE_INTAKE_CASING)))
                    .where('T', states(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE)))
                    .where('A', airfoilPredicate())
                    .where('P', abilities(GTQTMultiblockAbility.PRESSURE_CONTAINER))
                    .build();
        }

        return FactoryBlockPattern.start()
                .aisle("FXF", "XPX", "XXX")
                .aisle("XXX", "GAG", "XXX").setRepeatable(4)
                .aisle("FXF", "XSX", "XXX")
                .where('S', selfPredicate())
                .where('F', states(getFrameState()))
                .where('X', states(getCasingState()).setMinGlobalLimited(27)
                        .or(autoAbilities())
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3)))
                .where('G', states(getGlassState()))
                .where('A', airfoilPredicate())
                .where('P', abilities(GTQTMultiblockAbility.PRESSURE_CONTAINER))
                .build();
    }

    @Nonnull
    protected IBlockState getFrameState() {
        if (tier == GTValues.LuV) {
            return MetaBlocks.FRAMES.get(Materials.TungstenSteel).getBlock(Materials.TungstenSteel);
        }

        return MetaBlocks.FRAMES.get(Materials.Titanium).getBlock(Materials.Titanium);
    }

    @Nonnull
    protected IBlockState getCasingState() {
        if (tier == GTValues.LuV) {
            return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST);
        }

        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TITANIUM_STABLE);
    }

    @Nonnull
    protected IBlockState getGlassState() {
        if (tier == GTValues.LuV) {
            return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.LAMINATED_GLASS);
        }

        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }

    @Nonnull
    protected TraceabilityPredicate airfoilPredicate() {
        if (tier == GTValues.LuV)
            return states(GTQTMetaBlocks.blockActiveUniqueCasing1.getState(ADVANCED_AIRFOIL));
        else
            return states(GTQTMetaBlocks.blockActiveUniqueCasing1.getState(AIRFOIL));
    }

    @Override
    protected void updateFormedValid() {
        if (!getWorld().isRemote) {
            updateLogic();
            if (this.wasActiveAndNeedsUpdate) {
                this.wasActiveAndNeedsUpdate = false;
                this.setActive(false);
            }
        }

        boolean state = this.isActive() && this.isWorkingEnabled() && ConfigHolder.client.machinesEmissiveTextures;
        if (this.lastActive != state) {
            this.lastActive = state;
            this.replaceVariantBlocksActive(this.lastActive);
        }
    }

    protected void updateLogic() {
        // do nothing if working is disabled
        if (!this.isWorkingEnabled()) return;

        // check if compressing is possible
        if (!checkCanDrain()) return;

        // actually drain the energy
        drainEnergy(false);

        // since energy is being consumed the compressor is now active
        if (!this.isActive()) {
            setActive(true);
        }

        // increase progress
        progressTime++;
        if (progressTime % MAX_PROGRESS != 0) return;
        progressTime = 0;

        // actually increase pressure
        IPressureContainer container = getAbilities(GTQTMultiblockAbility.PRESSURE_CONTAINER).get(0);
        if (container.getPressure() == targetPressure || targetPressure < GCYSValues.EARTH_PRESSURE)
            return; // do nothing when at max

        double increase = pressureRate * container.getVolume() - (getNumMaintenanceProblems() * 1000);
        double potentialPressure = container.getPressureForParticles(container.getParticles() + increase);
        if (potentialPressure <= targetPressure) {
            if (container.changeParticles(increase, true)) {
                if (drainTanks(true)) {
                    container.changeParticles(increase, false);
                    drainTanks(false);
                }
            }
        } else if (container.getMaxPressure() > GCYSValues.EARTH_PRESSURE) {
            if (drainTanks(true)) {
                container.setParticles(targetPressure * container.getVolume());
                drainTanks(false);
            }
        }
    }

    /**
     * @return true if the machine is able to drain energy, else false
     */
    protected boolean checkCanDrain() {
        if (!drainEnergy(true)) {
            if (progressTime >= 2) {
                if (ConfigHolder.machines.recipeProgressLowEnergy) this.progressTime = 1;
                else this.progressTime = Math.max(1, progressTime - 2);

                hasNotEnoughEnergy = true;
            }
            return false;
        } else if (drainTanks(true)) {
            return true;
        }

        if (this.hasNotEnoughEnergy && getEnergyInputPerSecond() > 19L * GTValues.VA[tier]) {
            this.hasNotEnoughEnergy = false;
        }

        if (isActive()) {
            setActive(false);
            this.wasActiveAndNeedsUpdate = true;
        }
        return false;
    }

    public boolean drainEnergy(boolean simulate) {
        long energyToDrain = GTValues.VA[tier];
        long resultEnergy = energyContainer.getEnergyStored() - energyToDrain;
        if (resultEnergy >= 0L && resultEnergy <= energyContainer.getEnergyCapacity()) {
            if (!simulate) {
                energyContainer.changeEnergy(-energyToDrain);
            }
            return true;
        }
        return false;
    }

    protected boolean drainTanks(boolean simulate) {
        FluidStack stack = this.inputFluidInventory.drain(FLUID_USE_AMOUNT, !simulate);
        return stack != null && stack.isFluidEqual(LUBRICANT) && stack.amount == FLUID_USE_AMOUNT;
    }

    public long getEnergyInputPerSecond() {
        return energyContainer.getInputPerSec();
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = super.createUITemplate(entityPlayer);
        if (this.isStructureFormed()) {
            builder.widget(new ImageWidget(10, 100, 156, 20, GuiTextures.DISPLAY)
                    .setTooltip("gtqtcore.universal.tooltip.pressure.target"));
            builder.widget(new TextFieldWidget2(12, 108, 112, 16, () -> String.valueOf(targetPressure), value -> {
                if (!value.isEmpty())
                    targetPressure = Math.max(GCYSValues.EARTH_PRESSURE, Math.min(Double.parseDouble(value), maxPressure));
            }).setAllowedChars(TextFieldWidget2.DECIMALS).setMaxLength(19).setValidator(getTextFieldValidator()));
        }

        return builder;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (!isStructureFormed()) return;

        if (energyContainer != null && energyContainer.getEnergyCapacity() > 0) {
            long maxVoltage = Math.max(energyContainer.getInputVoltage(), energyContainer.getOutputVoltage());
            String voltageName = GTValues.VNF[GTUtility.getTierByVoltage(maxVoltage)];
            textList.add(new TextComponentTranslation("gregtech.multiblock.max_energy_per_tick", maxVoltage, voltageName));
        }

        if (!this.isWorkingEnabled()) {
            textList.add(new TextComponentTranslation("gregtech.multiblock.work_paused"));

        } else if (this.isActive()) {
            textList.add(new TextComponentTranslation("gregtech.multiblock.running"));
            textList.add(new TextComponentTranslation("gregtech.multiblock.progress", (int) (getProgressPercent() * 100)));
        } else {
            textList.add(new TextComponentTranslation("gregtech.multiblock.idling"));
        }

        if (!drainEnergy(true)) {
            textList.add(new TextComponentTranslation("gregtech.multiblock.not_enough_energy").setStyle(new Style().setColor(TextFormatting.RED)));
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.multiblock.axial_compressor.tooltip.1",
                NumberFormattingUtil.formatDoubleToCompactString(this.pressureRate),
                NumberFormattingUtil.formatDoubleToCompactString(this.maxPressure), I18n.format(GCYSValues.PNF[GTQTUtil.getTierByPressure(this.maxPressure)])));
        tooltip.add(I18n.format("gtqtcore.multiblock.axial_compressor.tooltip.2", FLUID_USE_AMOUNT));
        tooltip.add(I18n.format("gtqtcore.multiblock.axial_compressor.tooltip.3", I18n.format(GTValues.VNF[this.tier])));
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.isActive(), this.isWorkingEnabled());
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        if (tier == GTValues.LuV) {
            return GTQTTextures.SUPERSONIC_AXIAL_COMPRESSOR_OVERLAY;
        }

        return GTQTTextures.SUBSONIC_AXIAL_COMPRESSOR_OVERLAY;
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        if (tier == GTValues.LuV) {
            return Textures.ROBUST_TUNGSTENSTEEL_CASING;
        }

        return Textures.STABLE_TITANIUM_CASING;
    }

    @Override
    public int getProgress() {
        return this.progressTime;
    }

    @Override
    public int getMaxProgress() {
        return MAX_PROGRESS;
    }

    public double getProgressPercent() {
        return getProgress() * 1.0 / MAX_PROGRESS;
    }

    @Override
    public boolean isWorkingEnabled() {
        return this.isWorkingEnabled;
    }

    @Override
    public void setWorkingEnabled(boolean isWorkingEnabled) {
        if (this.isWorkingEnabled != isWorkingEnabled) {
            this.isWorkingEnabled = isWorkingEnabled;
            this.markDirty();
            if (getWorld() != null && !getWorld().isRemote) {
                writeCustomData(GregtechDataCodes.WORKING_ENABLED, buf -> buf.writeBoolean(isWorkingEnabled));
            }
        }
    }

    @Override
    public boolean isActive() {
        return super.isActive() && this.isActive;
    }

    public void setActive(boolean active) {
        if (this.isActive != active) {
            this.isActive = active;
            this.markDirty();
            if (getWorld() != null && !getWorld().isRemote) {
                writeCustomData(GregtechDataCodes.WORKABLE_ACTIVE, buf -> buf.writeBoolean(active));
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound data) {
        super.writeToNBT(data);
        data.setBoolean("isActive", this.isActive);
        data.setBoolean("isWorkingEnabled", this.isWorkingEnabled);
        data.setBoolean("wasActiveAndNeedsUpdate", this.wasActiveAndNeedsUpdate);
        data.setInteger("progressTime", this.progressTime);
        data.setDouble("targetPressure", this.targetPressure);
        return data;
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound data) {
        super.readFromNBT(data);
        this.isActive = data.getBoolean("isActive");
        this.isWorkingEnabled = data.getBoolean("isWorkingEnabled");
        this.wasActiveAndNeedsUpdate = data.getBoolean("wasActiveAndNeedsUpdate");
        this.progressTime = data.getInteger("progressTime");
        this.targetPressure = data.getDouble("targetPressure");
    }

    @Override
    public void writeInitialSyncData(@Nonnull PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(this.isActive);
        buf.writeBoolean(this.isWorkingEnabled);
        buf.writeBoolean(this.wasActiveAndNeedsUpdate);
        buf.writeInt(this.progressTime);
        buf.writeDouble(this.targetPressure);
    }

    @Override
    public void receiveInitialSyncData(@Nonnull PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        setActive(buf.readBoolean());
        setWorkingEnabled(buf.readBoolean());
        this.wasActiveAndNeedsUpdate = buf.readBoolean();
        this.progressTime = buf.readInt();
        this.targetPressure = buf.readDouble();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GregtechDataCodes.WORKABLE_ACTIVE) {
            this.isActive = buf.readBoolean();
            scheduleRenderUpdate();
        } else if (dataId == GregtechDataCodes.WORKING_ENABLED) {
            this.isWorkingEnabled = buf.readBoolean();
            scheduleRenderUpdate();
        }
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
    public boolean shouldShowVoidingModeButton() {
        return false;
    }
}