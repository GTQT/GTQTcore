package keqing.gtqtcore.common.metatileentities.single.electric;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IActiveOutputSide;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.ConfigHolder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import keqing.gtqtcore.api.GCYSValues;
import keqing.gtqtcore.api.capability.GTQTTileCapabilities;
import keqing.gtqtcore.api.capability.IPressureContainer;
import keqing.gtqtcore.api.capability.impl.PressureContainer;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.api.utils.NumberFormattingUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.GTValues.VA;
import static keqing.gtqtcore.api.GCYSValues.decreaseDetailP;
import static keqing.gtqtcore.api.GCYSValues.increaseDetailP;

public class MetaTileEntityPressureBooster extends TieredMetaTileEntity implements IDataInfoProvider, IActiveOutputSide {

    private final PressureContainer pressureContainer;
    protected EnumFacing outputFacing;
    int PRESSURE_DECREASE;
    int tier;
    boolean increasePressure;
    double minPressure;
    double maxPressure;

    public MetaTileEntityPressureBooster(ResourceLocation metaTileEntityId, int tier, boolean increasePressure) {
        super(metaTileEntityId, tier);
        this.tier = tier;
        minPressure = decreaseDetailP[tier];
        maxPressure = increaseDetailP[tier];
        this.increasePressure = increasePressure;
        PRESSURE_DECREASE = GCYSValues.getPressureChange(tier, increasePressure);

        if (increasePressure)
            this.pressureContainer = new PressureContainer(this, GCYSValues.EARTH_PRESSURE, maxPressure, 1.0);
        else this.pressureContainer = new PressureContainer(this, minPressure, GCYSValues.EARTH_PRESSURE, 1.0);
    }

    public PressureContainer getPressureContainer() {
        return pressureContainer;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.AIR_VENT_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
        Textures.PIPE_OUT_OVERLAY.renderSided(getOutputFacing(), renderState, translation, pipeline);
    }

    @Override
    protected ModularUI createUI(@Nonnull EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 166)
                .label(6, 6, getMetaFullName()).shouldColor(false)

                .widget(new ImageWidget(70, 26, 10, 54, GuiTextures.SLOT)
                        .setTooltip(getTooltipEU()))
                .widget(new ProgressWidget(() -> (double) energyContainer.getEnergyStored() / energyContainer.getEnergyCapacity(), 70, 26, 10, 54)
                        .setProgressBar(GuiTextures.PROGRESS_BAR_BOILER_EMPTY.get(true),
                                GuiTextures.PROGRESS_BAR_BOILER_HEAT, ProgressWidget.MoveType.VERTICAL))

                .widget(new ImageWidget(96, 26, 10, 54, GuiTextures.SLOT)
                        .setTooltip(getTooltips()))
                .widget(new ProgressWidget(() -> pressureContainer.getPressurePercent(true), 96, 26, 10, 54)
                        .setProgressBar(GuiTextures.PROGRESS_BAR_BOILER_EMPTY.get(true),
                                GuiTextures.PROGRESS_BAR_BOILER_HEAT, ProgressWidget.MoveType.VERTICAL))

                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0)
                .build(getHolder(), entityPlayer);
    }

    private String getTooltipEU() {
        return energyContainer.getEnergyStored() + " / " + energyContainer.getEnergyCapacity() + " EU";
    }

    private String getTooltips() {
        return NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getPressure()) + "Pa / " +
                NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMinPressure()) + "Pa";
    }

    @Override
    public boolean onWrenchClick(@Nonnull EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        if (!playerIn.isSneaking()) {
            if (getOutputFacing() == facing || getFrontFacing() == facing) return false;
            this.setOutputFacing(facing);
            return true;
        }
        return super.onWrenchClick(playerIn, hand, facing, hitResult);
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote && getOffsetTimer() % 20 == 0) {
            if (increasePressure) {
                if (pressureContainer.getPressure() < pressureContainer.getMaxPressure()) {
                    if (drainEU(true) && ventSteam(true)) {
                        if (pressureContainer.changeParticles(PRESSURE_DECREASE, true)) {
                            pressureContainer.changeParticles(PRESSURE_DECREASE, false);
                        } else if (pressureContainer.changeParticles(pressureContainer.getParticles() / 2, true)) {
                            // increase pressure by half if the regular increase is too much
                            pressureContainer.changeParticles(pressureContainer.getParticles() / 2, false);
                        } else {
                            // do not allow more than max pressure to prevent explosions
                            pressureContainer.setParticles(pressureContainer.getMaxPressure() * pressureContainer.getVolume());
                        }

                        drainEU(false);
                        ventSteam(false);
                    }
                }
            } else {
                if (pressureContainer.getPressure() > pressureContainer.getMinPressure()) {
                    if (drainEU(true) && ventSteam(true)) {
                        if (pressureContainer.changeParticles(PRESSURE_DECREASE, true)) {
                            pressureContainer.changeParticles(PRESSURE_DECREASE, false);
                        } else if (pressureContainer.changeParticles(-pressureContainer.getParticles() / 2, true)) {
                            // divide pressure by 2 if the regular decrease is too much
                            pressureContainer.changeParticles(-pressureContainer.getParticles() / 2, false);
                        } else {
                            // do not allow less than min pressure to prevent explosions and not require redstone control
                            pressureContainer.setParticles(pressureContainer.getMinPressure() * pressureContainer.getVolume());
                        }

                        drainEU(false);
                        ventSteam(false);
                    }
                }
            }

            TileEntity te = getWorld().getTileEntity(getPos().offset(getOutputFacing()));
            if (te != null) {
                IPressureContainer container = te.getCapability(GTQTTileCapabilities.CAPABILITY_PRESSURE_CONTAINER, this.getOutputFacing().getOpposite());
                if (container != null) {
                    IPressureContainer.mergeContainers(false, container, pressureContainer);
                }
            }

            if (!this.pressureContainer.isPressureSafe()) {
                this.pressureContainer.causePressureExplosion(getWorld(), getPos());
            }
        }
    }

    private boolean ventSteam(boolean simulate) {
        BlockPos pos = getPos().offset(getFrontFacing());
        IBlockState targetBlock = getWorld().getBlockState(pos);
        if (targetBlock.getCollisionBoundingBox(getWorld(), pos) == Block.NULL_AABB) {
            if (!simulate) performVentingAnimation();
            return true;
        } else if (targetBlock.getBlock() == Blocks.SNOW_LAYER && targetBlock.getValue(BlockSnow.LAYERS) == 1) {
            if (!simulate) {
                performVentingAnimation();
                getWorld().destroyBlock(pos, false);
            }
            return true;
        }
        return false;
    }

    private void performVentingAnimation() {
        final BlockPos pos = getPos();
        final EnumFacing facing = getFrontFacing();
        double posX = pos.getX() + 0.5 + facing.getXOffset() * 0.6;
        double posY = pos.getY() + 0.5 + facing.getYOffset() * 0.6;
        double posZ = pos.getZ() + 0.5 + facing.getZOffset() * 0.6;

        ((WorldServer) getWorld()).spawnParticle(EnumParticleTypes.CLOUD, posX, posY, posZ,
                7 + GTValues.RNG.nextInt(3),
                facing.getXOffset() / 2.0,
                facing.getYOffset() / 2.0,
                facing.getZOffset() / 2.0, 0.1);
        if (ConfigHolder.machines.machineSounds && !isMuffled()) {
            getWorld().playSound(null, posX, posY, posZ, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    private boolean drainEU(boolean simulate) {
        if (this.energyContainer.getEnergyStored() > VA[tier]) {
            if (!simulate) energyContainer.changeEnergy(-VA[tier]);
            return true;
        }
        return false;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPressureBooster(metaTileEntityId, tier, increasePressure);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        if (increasePressure) {
            tooltip.add(I18n.format("gtqtcore.steam_ejector.tooltip.5"));
            tooltip.add(I18n.format("gtqtcore.steam_ejector.tooltip.6", NumberFormattingUtil.formatDoubleToCompactString(Math.abs(PRESSURE_DECREASE))));
        } else {
            tooltip.add(I18n.format("gtqtcore.steam_ejector.tooltip.1"));
            tooltip.add(I18n.format("gtqtcore.steam_ejector.tooltip.2", NumberFormattingUtil.formatDoubleToCompactString(Math.abs(PRESSURE_DECREASE))));
        }
        tooltip.add(I18n.format("gtqtcore.steam_ejector.tooltip.4", VA[tier]));
        tooltip.add(I18n.format("gtqtcore.universal.tooltip.pressure.minimum", NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMinPressure()), GCYSValues.PNF[GTQTUtil.getTierByPressure(pressureContainer.getMinPressure())]));
        tooltip.add(I18n.format("gtqtcore.universal.tooltip.pressure.maximum", NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMaxPressure()), GCYSValues.PNF[GTQTUtil.getTierByPressure(pressureContainer.getMaxPressure())]));
    }

    @Override
    public void setFrontFacing(EnumFacing frontFacing) {
        super.setFrontFacing(frontFacing);
        if (getWorld() != null && !getWorld().isRemote && outputFacing == null) {
            setOutputFacing(frontFacing.getOpposite());

        }
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("outputFacing", getOutputFacing().getIndex());
        return data;
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound data) {
        super.readFromNBT(data);
        this.outputFacing = EnumFacing.VALUES[data.getInteger("outputFacing")];
    }

    @Override
    public void writeInitialSyncData(@Nonnull PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(getOutputFacing().getIndex());
    }

    @Override
    public void receiveInitialSyncData(@Nonnull PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.outputFacing = EnumFacing.VALUES[buf.readInt()];
    }

    @Override
    public void receiveCustomData(int dataId, @Nonnull PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GregtechDataCodes.VENTING_SIDE) {
            this.outputFacing = EnumFacing.VALUES[buf.readByte()];
            scheduleRenderUpdate();
        }
    }

    public EnumFacing getOutputFacing() {
        return this.outputFacing == null ? EnumFacing.SOUTH : this.outputFacing;
    }

    public void setOutputFacing(@Nonnull EnumFacing outputFacing) {
        this.outputFacing = outputFacing;
        if (!this.getWorld().isRemote) {
            this.markDirty();
            writeCustomData(GregtechDataCodes.VENTING_SIDE, buf -> buf.writeByte(this.getOutputFacing().getIndex()));
        }
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE) {
            return side == this.getOutputFacing() ? GregtechTileCapabilities.CAPABILITY_ACTIVE_OUTPUT_SIDE.cast(this) : null;
        }
        if (capability.equals(GTQTTileCapabilities.CAPABILITY_PRESSURE_CONTAINER)) {
            return GTQTTileCapabilities.CAPABILITY_PRESSURE_CONTAINER.cast(pressureContainer);
        }
        return super.getCapability(capability, side);
    }

    @Override
    public boolean isValidFrontFacing(EnumFacing facing) {
        return super.isValidFrontFacing(facing) && facing != this.outputFacing;
    }

    @Override
    public boolean isAutoOutputItems() {
        return false;
    }

    @Override
    public boolean isAutoOutputFluids() {
        return false;
    }

    @Override
    public boolean isAllowInputFromOutputSideItems() {
        return false;
    }

    @Override
    public boolean isAllowInputFromOutputSideFluids() {
        return false;
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.current_pressure", new TextComponentString(NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getPressure())).setStyle(new Style().setColor(TextFormatting.AQUA))));
        list.add(new TextComponentTranslation("behavior.tricorder.min_pressure", new TextComponentString(NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMinPressure())).setStyle(new Style().setColor(TextFormatting.GREEN))));
        list.add(new TextComponentTranslation("behavior.tricorder.max_pressure", new TextComponentString(NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMaxPressure())).setStyle(new Style().setColor(TextFormatting.GREEN))));
        return list;
    }

}
