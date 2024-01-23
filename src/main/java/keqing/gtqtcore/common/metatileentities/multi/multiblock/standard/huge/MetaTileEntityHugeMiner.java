package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.huge;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import com.google.common.collect.Lists;
import gregicality.science.api.unification.materials.GCYSMaterials;
import gregtech.api.GTValues;
import gregtech.api.capability.*;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.capability.impl.miner.MultiblockMinerLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ImageCycleButtonWidget;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.api.util.RelativeDirection;
import gregtech.api.util.interpolate.Eases;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.IRenderSetup;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.shader.postprocessing.BloomEffect;
import gregtech.client.shader.postprocessing.BloomType;
import gregtech.client.utils.*;
import gregtech.common.ConfigHolder;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing1;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static gregtech.api.unification.material.Materials.DrillingFluid;

public class MetaTileEntityHugeMiner extends MultiblockWithDisplayBase implements IMiner, IControllable, IDataInfoProvider, IFastRenderMetaTileEntity, IBloomEffect {

    private static final int CHUNK_LENGTH = 16;

    private final int tier;


    private IEnergyContainer energyContainer;
    protected IMultipleTankHandler inputFluidInventory;
    protected IItemHandlerModifiable outputInventory;

    private boolean silkTouch = false;
    private boolean chunkMode = false;

    private boolean isInventoryFull = false;

    private final int drillingFluidConsumePerTick;

    private final MultiblockMinerLogic minerLogic;

    public MetaTileEntityHugeMiner(ResourceLocation metaTileEntityId, int tier, int speed, int maximumChunkDiameter, int fortune, int drillingFluidConsumePerTick) {
        super(metaTileEntityId);
        this.tier = tier;
        this.drillingFluidConsumePerTick = drillingFluidConsumePerTick;
        this.minerLogic = new MultiblockMinerLogic(this, fortune, speed, maximumChunkDiameter * CHUNK_LENGTH / 2, RecipeMaps.MACERATOR_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityHugeMiner(metaTileEntityId, this.tier, this.minerLogic.getSpeed(), this.minerLogic.getMaximumRadius() * 2 / CHUNK_LENGTH, this.minerLogic.getFortune(),  getDrillingFluidConsumePerTick());
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.setFusionRingColor(NO_COLOR);
        resetTileAbilities();
        if (this.minerLogic.isActive())
            this.minerLogic.setActive(false);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();

    }

    private void initializeAbilities() {
        this.inputFluidInventory = new FluidTankList(false, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.outputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.INPUT_ENERGY));
        this.minerLogic.setVoltageTier(GTUtility.getTierByVoltage(this.energyContainer.getInputVoltage()));
        this.minerLogic.setOverclockAmount(Math.max(1, GTUtility.getTierByVoltage(this.energyContainer.getInputVoltage()) - this.tier));
        this.minerLogic.initPos(getPos(), this.minerLogic.getCurrentRadius());
    }

    private void resetTileAbilities() {
        this.inputFluidInventory = new FluidTankList(true);
        this.outputInventory = new ItemStackHandler(0);
        this.energyContainer = new EnergyContainerList(Lists.newArrayList());
    }

    public int getEnergyTier() {
        if (energyContainer == null) return this.tier;
        return Math.min(this.tier + 1, Math.max(this.tier, GTUtility.getFloorTierByVoltage(energyContainer.getInputVoltage())));
    }

    @Override
    public boolean drainEnergy(boolean simulate) {
        long energyToDrain = GTValues.VA[getEnergyTier()];
        long resultEnergy = energyContainer.getEnergyStored() - energyToDrain;
        if (resultEnergy >= 0L && resultEnergy <= energyContainer.getEnergyCapacity()) {
            if (!simulate)
                energyContainer.changeEnergy(-energyToDrain);
            return true;
        }
        return false;
    }

    @Override
    public boolean drainFluid(boolean simulate) {
        FluidStack drillingFluid = DrillingFluid.getFluid(this.drillingFluidConsumePerTick * this.minerLogic.getOverclockAmount());
        FluidStack fluidStack = inputFluidInventory.getTankAt(0).getFluid();
        if (fluidStack != null && fluidStack.isFluidEqual(DrillingFluid.getFluid(1)) && fluidStack.amount >= drillingFluid.amount) {
            if (!simulate)
                inputFluidInventory.drain(drillingFluid, true);
            return true;
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.minerLogic.isWorking(), this.isWorkingEnabled());
        minerLogic.renderPipe(renderState, translation, pipeline);
    }

    @Override
    protected void updateFormedValid() {

        this.minerLogic.performMining();
        if (!getWorld().isRemote && this.minerLogic.wasActiveAndNeedsUpdate()) {
            this.minerLogic.setWasActiveAndNeedsUpdate(false);
            this.minerLogic.setActive(false);
        }
            setFusionRingColor(NO_COLOR);

    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("#######", "###C###", "#######", "#######", "#######", "#######", "#######")
                .aisle("#######", "##CCC##", "#######", "#######", "#######", "#######", "#######")
                .aisle("###C###", "#CCCCC#", "###X###", "###X###", "###C###", "###C###", "#######")
                .aisle("##C#C##", "CCC#CCC", "##X#X##", "##X#X##", "##C#C##", "##C#C##", "###S###")
                .aisle("###C###", "#CCCCC#", "###X###", "###X###", "###C###", "###C###", "#######")
                .aisle("#######", "##CCC##", "#######", "#######", "#######", "#######", "#######")
                .aisle("#######", "###C###", "#######", "#######", "#######", "#######", "#######")
                .where('S', selfPredicate())
                .where('X', states(getCasingState())
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setExactLimit(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3).setPreviewCount(1)))
                .where('C', states(getCasingState()))
                .where('F', getFramePredicate())
                .where('#', any())
                .build();
    }

    @Override
    public String[] getDescription() {
        return new String[]{I18n.format("gregtech.machine.miner.multi.description")};
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        int workingAreaChunks = this.minerLogic.getCurrentRadius() * 2 / CHUNK_LENGTH;
        tooltip.add(I18n.format("gregtech.machine.miner.multi.modes"));
        tooltip.add(I18n.format("gregtech.machine.miner.multi.production"));
        tooltip.add(I18n.format("gregtech.universal.tooltip.working_area_chunks_max", workingAreaChunks, workingAreaChunks));
        tooltip.add(I18n.format("gregtech.universal.tooltip.energy_tier_range", GTValues.VNF[this.tier], GTValues.VNF[this.tier + 1]));
    }

    @Override
    public void addToolUsages(ItemStack stack, @Nullable World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.tool_action.screwdriver.toggle_mode_covers"));
        tooltip.add(I18n.format("gregtech.tool_action.wrench.set_facing"));
        if (getSound() != null) {
            tooltip.add(I18n.format("gregtech.tool_action.hammer"));
        }
        tooltip.add(I18n.format("gregtech.tool_action.crowbar"));
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);

        if (this.isStructureFormed()) {
            if (energyContainer != null && energyContainer.getEnergyCapacity() > 0) {
                int energyContainer = getEnergyTier();
                long maxVoltage = GTValues.V[energyContainer];
                String voltageName = GTValues.VNF[energyContainer];
                textList.add(new TextComponentTranslation("gregtech.multiblock.max_energy_per_tick", maxVoltage, voltageName));
            }

            int workingAreaChunks = this.minerLogic.getCurrentRadius() * 2 / CHUNK_LENGTH;
            int workingArea = getWorkingArea(minerLogic.getCurrentRadius());
            textList.add(new TextComponentTranslation("gregtech.machine.miner.startx", this.minerLogic.getX().get() == Integer.MAX_VALUE ? 0 : this.minerLogic.getX().get()));
            textList.add(new TextComponentTranslation("gregtech.machine.miner.starty", this.minerLogic.getY().get() == Integer.MAX_VALUE ? 0 : this.minerLogic.getY().get()));
            textList.add(new TextComponentTranslation("gregtech.machine.miner.startz", this.minerLogic.getZ().get() == Integer.MAX_VALUE ? 0 : this.minerLogic.getZ().get()));
            if (this.minerLogic.isChunkMode()) {
                textList.add(new TextComponentTranslation("gregtech.machine.miner.working_area_chunks", workingAreaChunks, workingAreaChunks));
            } else {
                textList.add(new TextComponentTranslation("gregtech.machine.miner.working_area", workingArea, workingArea));
            }
            if (this.minerLogic.isDone())
                textList.add(new TextComponentTranslation("gregtech.machine.miner.done").setStyle(new Style().setColor(TextFormatting.GREEN)));
            else if (this.minerLogic.isWorking())
                textList.add(new TextComponentTranslation("gregtech.machine.miner.working").setStyle(new Style().setColor(TextFormatting.GOLD)));
            else if (!this.isWorkingEnabled())
                textList.add(new TextComponentTranslation("gregtech.multiblock.work_paused"));
        }
    }

    private void addDisplayText2(List<ITextComponent> textList) {
        if (this.isStructureFormed()) {
            ITextComponent mCoords = new TextComponentString("    ")
                    .appendSibling(new TextComponentTranslation("gregtech.machine.miner.minex", this.minerLogic.getMineX().get()))
                    .appendText("\n    ")
                    .appendSibling(new TextComponentTranslation("gregtech.machine.miner.miney", this.minerLogic.getMineY().get()))
                    .appendText("\n    ")
                    .appendSibling(new TextComponentTranslation("gregtech.machine.miner.minez", this.minerLogic.getMineZ().get()));
            textList.add(mCoords);
        }
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (isStructureFormed()) {
            if (this.isInventoryFull) {
                textList.add(new TextComponentTranslation("gregtech.machine.miner.invfull").setStyle(new Style().setColor(TextFormatting.RED)));
            }
            if (!drainFluid(true)) {
                textList.add(new TextComponentTranslation("gregtech.machine.miner.multi.needsfluid").setStyle(new Style().setColor(TextFormatting.RED)));
            }
            if (!drainEnergy(true)) {
                textList.add(new TextComponentTranslation("gregtech.machine.miner.needspower").setStyle(new Style().setColor(TextFormatting.RED)));
            }
        }
    }

    public IBlockState getCasingState() {
        if (this.tier==7)
            return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.PD_TURBINE_CASING);
        if (this.tier==8)
            return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.NQ_TURBINE_CASING);
        return GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.ST_TURBINE_CASING);
    }

    @Nonnull
    private TraceabilityPredicate getFramePredicate() {
        if (this.tier==7)
            return frames(Materials.RhodiumPlatedPalladium);
        if (this.tier==8)
            return frames(Materials.NaquadahAlloy);
        return frames(GCYSMaterials.Orichalcum);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        if (this.tier==7)
            return GTQTTextures.PD_CASING;
        if (this.tier==8)
            return GTQTTextures.NQ_CASING;
        return GTQTTextures.ST_CASING;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag("chunkMode", new NBTTagInt(chunkMode ? 1 : 0));
        data.setTag("silkTouch", new NBTTagInt(silkTouch ? 1 : 0));
        return this.minerLogic.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        chunkMode = data.getInteger("chunkMode") != 0;
        silkTouch = data.getInteger("silkTouch") != 0;
        this.minerLogic.readFromNBT(data);
    }


    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        this.minerLogic.writeInitialSyncData(buf);
        buf.writeVarInt(this.fusionRingColor);
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

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        if (this.tier == 5)
            return Textures.LARGE_MINER_OVERLAY_ADVANCED;
        if (this.tier == 6)
            return Textures.LARGE_MINER_OVERLAY_ADVANCED_2;
        return Textures.LARGE_MINER_OVERLAY_BASIC;
    }

    public long getMaxVoltage() {
        return GTValues.V[GTUtility.getTierByVoltage(energyContainer.getInputVoltage())];
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = super.createUITemplate(entityPlayer);
        builder.widget(new AdvancedTextWidget(63, 31, this::addDisplayText2, 0xFFFFFF)
                .setMaxWidthLimit(68).setClickHandler(this::handleDisplayClick));
        return builder;
    }

    // used for UI
    private int getCurrentMode() {
        // 0 -> not chunk mode, not silk touch mode
        if (!minerLogic.isChunkMode() && !minerLogic.isSilkTouchMode()) {
            return 0;
        }
        // 1 -> is chunk mode, not silk touch mode
        if (minerLogic.isChunkMode() && !minerLogic.isSilkTouchMode()) {
            return 1;
        }
        // 2 -> not chunk mode, is silk touch mode
        if (!minerLogic.isChunkMode() && minerLogic.isSilkTouchMode()) {
            return 2;
        }
        // 3 -> is chunk mode, is silk touch mode
        return 3;
    }

    // used for UI
    private void setCurrentMode(int mode) {
        switch (mode) {
            case 0 -> {
                minerLogic.setChunkMode(false);
                minerLogic.setSilkTouchMode(false);
            }
            case 1 -> {
                minerLogic.setChunkMode(true);
                minerLogic.setSilkTouchMode(false);
            }
            case 2 -> {
                minerLogic.setChunkMode(false);
                minerLogic.setSilkTouchMode(true);
            }
            default -> {
                minerLogic.setChunkMode(true);
                minerLogic.setSilkTouchMode(true);
            }
        }
    }

    @Nonnull
    @Override
    protected  Widget getFlexButton(int x, int y, int width, int height) {
        return new ImageCycleButtonWidget(x, y, width, height, GuiTextures.BUTTON_MINER_MODES, 4, this::getCurrentMode, this::setCurrentMode)
                .setTooltipHoverString(mode -> switch (mode) {
                    case 0  -> "gregtech.multiblock.miner.neither_mode";
                    case 1  -> "gregtech.multiblock.miner.chunk_mode";
                    case 2  -> "gregtech.multiblock.miner.silk_touch_mode";
                    default -> "gregtech.multiblock.miner.both_modes";
                });
    }

    @Override
    public boolean onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        if (getWorld().isRemote || !this.isStructureFormed())
            return true;

        if (!this.isActive()) {
            int currentRadius = this.minerLogic.getCurrentRadius();
            if (this.minerLogic.isChunkMode()) {
                if (currentRadius - CHUNK_LENGTH <= 0) {
                    this.minerLogic.setCurrentRadius(this.minerLogic.getMaximumRadius());
                } else {
                    this.minerLogic.setCurrentRadius(currentRadius - CHUNK_LENGTH);
                }
                int workingAreaChunks = this.minerLogic.getCurrentRadius() * 2 / CHUNK_LENGTH;
                playerIn.sendMessage(new TextComponentTranslation("gregtech.machine.miner.working_area_chunks", workingAreaChunks, workingAreaChunks));
            } else {
                if (currentRadius - CHUNK_LENGTH / 2 <= 0) {
                    this.minerLogic.setCurrentRadius(this.minerLogic.getMaximumRadius());
                } else {
                    this.minerLogic.setCurrentRadius(currentRadius - CHUNK_LENGTH / 2);
                }
                int workingArea = getWorkingArea(minerLogic.getCurrentRadius());
                playerIn.sendMessage(new TextComponentTranslation("gregtech.universal.tooltip.working_area", workingArea, workingArea));
            }
            this.minerLogic.resetArea();
        } else {
            playerIn.sendMessage(new TextComponentTranslation("gregtech.machine.miner.errorradius"));
        }
        return true;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    public boolean isInventoryFull() {
        return this.isInventoryFull;
    }

    @Override
    public void setInventoryFull(boolean isFull) {
        this.isInventoryFull = isFull;
    }

    public int getTier() {
        return this.tier;
    }

    public int getDrillingFluidConsumePerTick() {
        return this.drillingFluidConsumePerTick;
    }

    @Override
    public boolean isWorkingEnabled() {
        return this.minerLogic.isWorkingEnabled();
    }

    @Override
    public void setWorkingEnabled(boolean isActivationAllowed) {
        this.minerLogic.setWorkingEnabled(isActivationAllowed);
    }

    public int getMaxChunkRadius() {
        return this.minerLogic.getMaximumRadius() / CHUNK_LENGTH;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE) {
            return GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this);
        }
        return super.getCapability(capability, side);
    }

    @Override
    public IItemHandlerModifiable getExportItems() {
        return this.outputInventory;
    }

    @Override
    public SoundEvent getSound() {
        return GTSoundEvents.MINER;
    }

    @Override
    public boolean isActive() {
        return minerLogic.isActive() && isWorkingEnabled();
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        int workingArea = getWorkingArea(this.minerLogic.getCurrentRadius());
        return Collections.singletonList(new TextComponentTranslation("gregtech.machine.miner.working_area", workingArea, workingArea));
    }




    protected static final int NO_COLOR = 0;
    private int fusionRingColor = NO_COLOR;
    private boolean registeredBloomRenderTicket;
    @Override
    protected boolean shouldShowVoidingModeButton() {
        return false;
    }
    protected int getFusionRingColor() {
        return this.fusionRingColor;
    }
    protected boolean hasFusionRingColor() {
        return true;
    }
    protected void setFusionRingColor(int fusionRingColor) {
        if (this.fusionRingColor != fusionRingColor) {
            this.fusionRingColor = fusionRingColor;
            writeCustomData(GregtechDataCodes.UPDATE_COLOR, buf -> buf.writeVarInt(fusionRingColor));
        }
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(double x, double y, double z, float partialTicks) {
        if (this.hasFusionRingColor() && !this.registeredBloomRenderTicket) {
            this.registeredBloomRenderTicket = true;
            BloomEffectUtil.registerBloomRender(FusionBloomSetup.INSTANCE, getBloomType(), this, this);
        }
    }
    private static BloomType getBloomType() {
        ConfigHolder.FusionBloom fusionBloom = ConfigHolder.client.shader.fusionBloom;
        return BloomType.fromValue(fusionBloom.useShader ? fusionBloom.bloomStyle : -1);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void renderBloomEffect(BufferBuilder buffer, EffectRenderContext context) {
        int color = RenderUtil.interpolateColor(this.getFusionRingColor(), -1, Eases.QUAD_IN.getInterpolation(
                Math.abs((Math.abs(getOffsetTimer() % 50) + context.partialTicks()) - 25) / 25));
        float a = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        EnumFacing relativeBack = RelativeDirection.BACK.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());
        EnumFacing.Axis axis = RelativeDirection.UP.getRelativeFacing(getFrontFacing(), getUpwardsFacing(), isFlipped())
                .getAxis();

        RenderBufferHelper.renderRing(buffer,
                getPos().getX() - context.cameraX() + relativeBack.getXOffset()+0.75,
                getPos().getY() - context.cameraY() + relativeBack.getYOffset() ,
                getPos().getZ() - context.cameraZ() + relativeBack.getZOffset()+0.75,
                10, 0.2, 10, 20,
                r, g, b, a, EnumFacing.Axis.Y);

        RenderBufferHelper.renderRing(buffer,
                getPos().getX() - context.cameraX() + relativeBack.getXOffset()+0.75,
                getPos().getY() - context.cameraY() + relativeBack.getYOffset()-5 ,
                getPos().getZ() - context.cameraZ() + relativeBack.getZOffset()+0.75,
                8, 0.2, 10, 20,
                r, g, b, a, EnumFacing.Axis.Y);

        RenderBufferHelper.renderRing(buffer,
                getPos().getX() - context.cameraX() + relativeBack.getXOffset()+0.75,
                getPos().getY() - context.cameraY() + relativeBack.getYOffset()-10 ,
                getPos().getZ() - context.cameraZ() + relativeBack.getZOffset()+0.75,
                4, 0.2, 10, 20,
                r, g, b, a, EnumFacing.Axis.Y);

        for(int i=0;i<40;i++) {
            RenderBufferHelper.renderRing(buffer,
                    getPos().getX() - context.cameraX() + relativeBack.getXOffset()+0.75,
                    getPos().getY() - context.cameraY() + relativeBack.getYOffset() - 20-i*5,
                    getPos().getZ() - context.cameraZ() + relativeBack.getZOffset()+0.75,
                    2, 0.2, 10, 20,
                    r, g, b, a, EnumFacing.Axis.Y);

        }


    }
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRenderBloomEffect( EffectRenderContext context) {
        return this.hasFusionRingColor();
    }
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        EnumFacing relativeRight = RelativeDirection.RIGHT.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());
        EnumFacing relativeBack = RelativeDirection.BACK.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());

        return new AxisAlignedBB(
                this.getPos().offset(relativeBack).offset(relativeRight, 6),
                this.getPos().offset(relativeBack, 13).offset(relativeRight.getOpposite(), 6));
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 0;
    }

    @Override
    public boolean isGlobalRenderer() {
        return true;
    }
    @SideOnly(Side.CLIENT)
    private static final class FusionBloomSetup implements IRenderSetup {

        private static final FusionBloomSetup INSTANCE = new FusionBloomSetup();

        float lastBrightnessX;
        float lastBrightnessY;

        @Override
        public void preDraw( BufferBuilder buffer) {
            BloomEffect.strength = (float) ConfigHolder.client.shader.fusionBloom.strength;
            BloomEffect.baseBrightness = (float) ConfigHolder.client.shader.fusionBloom.baseBrightness;
            BloomEffect.highBrightnessThreshold = (float) ConfigHolder.client.shader.fusionBloom.highBrightnessThreshold;
            BloomEffect.lowBrightnessThreshold = (float) ConfigHolder.client.shader.fusionBloom.lowBrightnessThreshold;
            BloomEffect.step = 1;

            lastBrightnessX = OpenGlHelper.lastBrightnessX;
            lastBrightnessY = OpenGlHelper.lastBrightnessY;
            GlStateManager.color(1, 1, 1, 1);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GlStateManager.disableTexture2D();

            buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
        }

        @Override
        public void postDraw( BufferBuilder buffer) {
            Tessellator.getInstance().draw();

            GlStateManager.enableTexture2D();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
        }
    }
}