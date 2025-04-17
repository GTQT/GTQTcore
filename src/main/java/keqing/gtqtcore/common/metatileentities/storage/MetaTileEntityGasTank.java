package keqing.gtqtcore.common.metatileentities.storage;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IActiveOutputSide;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import keqing.gtqtcore.api.GCYSValues;
import keqing.gtqtcore.api.capability.GTQTTileCapabilities;
import keqing.gtqtcore.api.capability.IPressureContainer;
import keqing.gtqtcore.api.capability.impl.PressureContainer;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.api.utils.NumberFormattingUtil;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static keqing.gtqtcore.api.GCYSValues.decreaseDetailP;
import static keqing.gtqtcore.api.GCYSValues.increaseDetailP;

public class MetaTileEntityGasTank extends MetaTileEntity implements IActiveOutputSide {
    private final Material material;
    private final int color;
    private final int tier;
    private final int size;
    private final PressureContainer pressureContainer;
    protected EnumFacing outputFacing;

    public MetaTileEntityGasTank(ResourceLocation metaTileEntityId, Material material, int tier, int size) {
        super(metaTileEntityId);
        this.tier = tier;
        this.size = size;
        this.material = material;
        this.color = material.getMaterialRGB();
        this.pressureContainer = new PressureContainer(this, decreaseDetailP[tier], increaseDetailP[tier], size);
    }

    public PressureContainer getPressureContainer() {
        return pressureContainer;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityGasTank(metaTileEntityId, material, tier, size);
    }

    @SideOnly(Side.CLIENT)
    public Pair<TextureAtlasSprite, Integer> getParticleTexture() {

        int color = GTUtility.convertOpaqueRGBA_CLtoRGB(ColourRGBA.multiply(GTUtility.convertRGBtoOpaqueRGBA_CL(this.color), GTUtility.convertRGBtoOpaqueRGBA_CL(this.getPaintingColorForRendering())));
        return Pair.of(Textures.DRUM.getParticleTexture(), color);

    }

    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        ColourMultiplier multiplier;

        multiplier = new ColourMultiplier(ColourRGBA.multiply(GTUtility.convertRGBtoOpaqueRGBA_CL(this.color), GTUtility.convertRGBtoOpaqueRGBA_CL(this.getPaintingColorForRendering())));
        Textures.DRUM.render(renderState, translation, ArrayUtils.add(pipeline, multiplier), this.getFrontFacing());
        Textures.DRUM_OVERLAY.render(renderState, translation, pipeline);

        Textures.PIPE_OUT_OVERLAY.renderSided(getOutputFacing(), renderState, translation, pipeline);

    }

    @Override
    protected ModularUI createUI(@Nonnull EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 166)
                .label(6, 6, getMetaFullName())

                // TODO add tooltip directly to ProgressWidget in CEu
                .dynamicLabel(8, 12, () -> "耐压储罐 等级：" + tier + " 容积：" + size, 0xFFFFFF)
                .widget(new ImageWidget(70, 26, 10, 54, GuiTextures.SLOT)
                        .setTooltip(NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getPressure()) + "Pa / " +
                                NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMaxPressure()) + "Pa"))
                .widget(new ProgressWidget(() -> pressureContainer.getPressurePercent(false), 70, 26, 10, 54)
                        .setProgressBar(GuiTextures.PROGRESS_BAR_BOILER_EMPTY.get(true),
                                GuiTextures.PROGRESS_BAR_BOILER_HEAT, ProgressWidget.MoveType.VERTICAL))

                .widget(new ImageWidget(96, 26, 10, 54, GuiTextures.SLOT)
                        .setTooltip(NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getPressure()) + "Pa / " +
                                NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMinPressure()) + "Pa"))
                .widget(new ProgressWidget(() -> pressureContainer.getPressurePercent(true), 96, 26, 10, 54)
                        .setProgressBar(GuiTextures.PROGRESS_BAR_BOILER_EMPTY.get(true),
                                GuiTextures.PROGRESS_BAR_BOILER_HEAT, ProgressWidget.MoveType.VERTICAL))


                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0)
                .build(getHolder(), entityPlayer);
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote && getOffsetTimer() % 20 == 0) {
            // vacuum container, needs to increase pressure
            boolean needsPressureIncrease = pressureContainer.getPressure() > pressureContainer.getMinPressure() && this.pressureContainer.getMinPressure() < GCYSValues.EARTH_PRESSURE;
            // pressure container, needs to decrease pressure
            boolean needsPressureDecrease = pressureContainer.getPressure() < pressureContainer.getMaxPressure() && this.pressureContainer.getMaxPressure() > GCYSValues.EARTH_PRESSURE;
            boolean canChangePressure = needsPressureDecrease || needsPressureIncrease;

            if (canChangePressure) {
                TileEntity te = getWorld().getTileEntity(getPos().offset(getFrontFacing()));
                if (te != null) {
                    IPressureContainer container = te.getCapability(GTQTTileCapabilities.CAPABILITY_PRESSURE_CONTAINER, this.getFrontFacing().getOpposite());
                    if (container != null) {
                        IPressureContainer.mergeContainers(false, container, pressureContainer);
                    }
                }
            }

            if (!this.pressureContainer.isPressureSafe()) {
                this.pressureContainer.causePressureExplosion(getWorld(), getPos());
            }
        }
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

    @Override
    public boolean isValidFrontFacing(EnumFacing facing) {
        return super.isValidFrontFacing(facing) && facing != this.outputFacing;
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
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("用于高低压气体缓存"));
        tooltip.add(I18n.format("储罐容积：%s", size));
        tooltip.add(I18n.format("gtqtcore.universal.tooltip.pressure.minimum", NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMinPressure()), GCYSValues.PNF[GTQTUtil.getTierByPressure(pressureContainer.getMinPressure())]));
        tooltip.add(I18n.format("gtqtcore.universal.tooltip.pressure.maximum", NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMaxPressure()), GCYSValues.PNF[GTQTUtil.getTierByPressure(pressureContainer.getMaxPressure())]));
    }
}
