package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.resourceCollection;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import com.google.common.collect.Lists;
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
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing5;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.Collections;
import java.util.List;

import static keqing.gtqtcore.api.unification.GTQTMaterials.Adamantium;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Orichalcum;

public class MetaTileEntityAdvancedLargeMiner extends MultiblockWithDisplayBase implements IMiner, IControllable, IDataInfoProvider {

    private static final int CHUNK_LENGTH = 16;
    private final Material material;
    private final int tier;
    private final int drillingFluidConsumePerTick;
    private final MultiblockMinerLogic minerLogic;
    protected IMultipleTankHandler inputFluidInventory;
    protected IItemHandlerModifiable outputInventory;
    private IEnergyContainer energyContainer;
    private boolean silkTouch = false;
    private boolean chunkMode = false;
    private boolean isInventoryFull = false;

    public MetaTileEntityAdvancedLargeMiner(ResourceLocation metaTileEntityId,
                                            int tier,
                                            int speed,
                                            int maximumChunkDiameter,
                                            int fortune,
                                            Material material,
                                            int drillingFluidConsumePerTick) {
        super(metaTileEntityId);
        this.material = material;
        this.tier = tier;
        this.drillingFluidConsumePerTick = drillingFluidConsumePerTick;
        this.minerLogic = new MultiblockMinerLogic(this, fortune, speed, maximumChunkDiameter * 16 / 2, RecipeMaps.MACERATOR_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAdvancedLargeMiner(metaTileEntityId, tier, minerLogic.getSpeed(), minerLogic.getMaximumRadius() * 2 / 16, minerLogic.getFortune(), getMaterial(), getDrillingFluidConsumePerTick());
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.resetTileAbilities();
        if (this.minerLogic.isActive()) {
            this.minerLogic.setActive(false);
        }
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.initializeAbilities();
    }

    private void initializeAbilities() {
        this.inputFluidInventory = new FluidTankList(false, this.getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.outputInventory = new ItemHandlerList(this.getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.energyContainer = new EnergyContainerList(this.getAbilities(MultiblockAbility.INPUT_ENERGY));
        this.minerLogic.setVoltageTier(GTUtility.getTierByVoltage(this.energyContainer.getInputVoltage()));
        this.minerLogic.setOverclockAmount(Math.max(1, GTUtility.getTierByVoltage(this.energyContainer.getInputVoltage()) - this.tier));
        this.minerLogic.initPos(this.getPos(), this.minerLogic.getCurrentRadius());
    }

    private void resetTileAbilities() {
        this.inputFluidInventory = new FluidTankList(true);
        this.outputInventory = new GTItemStackHandler(this, 0);
        this.energyContainer = new EnergyContainerList(Lists.newArrayList());
    }

    public int getEnergyTier() {
        return this.energyContainer == null ? this.tier : Math.min(this.tier + 1, Math.max(this.tier, GTUtility.getFloorTierByVoltage(this.energyContainer.getInputVoltage())));
    }

    @Override
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
    public boolean drainFluid(boolean simulate) {
        FluidStack drillingFluid = Materials.DrillingFluid.getFluid(this.drillingFluidConsumePerTick * this.minerLogic.getOverclockAmount());
        FluidStack fluidStack = this.inputFluidInventory.getTankAt(0).getFluid();
        if (fluidStack != null && fluidStack.isFluidEqual(Materials.DrillingFluid.getFluid(1)) && fluidStack.amount >= drillingFluid.amount) {
            if (!simulate) {
                this.inputFluidInventory.drain(drillingFluid, true);
            }

            return true;
        } else {
            return false;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, this.getFrontFacing(), this.minerLogic.isWorking(), this.isWorkingEnabled());
        this.minerLogic.renderPipe(renderState, translation, pipeline);
    }

    @Override
    protected void updateFormedValid() {
        this.minerLogic.performMining();
        if (!this.getWorld().isRemote && this.minerLogic.wasActiveAndNeedsUpdate()) {
            this.minerLogic.setWasActiveAndNeedsUpdate(false);
            this.minerLogic.setActive(false);
        }
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("##X#X##","##C#C##","#######","#######","#######","#######","#######","#######","#######")
                .aisle("#F#F#F#","#FCCCF#","#F###F#","#######","#######","#######","#######","#######","#######")
                .aisle("X#####X","CCCCCCC","##CCC##","###F###","###F###","###F###","#######","#######","#######")
                .aisle("#F#C#F#","#CCCCC#","##CCC##","##FCF##","##FCF##","##FCF##","###F###","###F###","###F###")
                .aisle("X#####X","CCCCCCC","##CCC##","###F###","###F###","###F###","#######","#######","#######")
                .aisle("#F#F#F#","#FCSCF#","#F###F#","#######","#######","#######","#######","#######","#######")
                .aisle("##X#X##","##C#C##","#######","#######","#######","#######","#######","#######","#######")
                .where('S', this.selfPredicate())
                .where('X', states(getCasingState())
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS)
                                .setMaxGlobalLimited(1)
                                .setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS)
                                .setExactLimit(1)
                                .setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY)
                                .setMinGlobalLimited(1)
                                .setMaxGlobalLimited(3)
                                .setPreviewCount(1)))
                .where('C', states(getCasingState()))
                .where('T', states(getTurbineState()))
                .where('F', getFrameState())
                .where('#', any())
                .build();
    }

    @Override
    public String[] getDescription() {
        return new String[]{I18n.format("gregtech.machine.miner.multi.description")};
    }

    @Override
    public void addInformation(ItemStack stack,
                               World player,
                               List<String> tooltip,
                               boolean advanced) {
        int workingAreaChunks = this.minerLogic.getCurrentRadius() * 2 / 16;
        tooltip.add(I18n.format("gregtech.machine.miner.multi.modes"));
        tooltip.add(I18n.format("gregtech.machine.miner.multi.production"));
        tooltip.add(I18n.format("gregtech.machine.miner.fluid_usage", this.getDrillingFluidConsumePerTick(), Materials.DrillingFluid.getLocalizedName()));
        tooltip.add(I18n.format("gregtech.universal.tooltip.working_area_chunks_max", workingAreaChunks, workingAreaChunks));
        tooltip.add(I18n.format("gregtech.universal.tooltip.energy_tier_range", GTValues.VNF[this.tier], GTValues.VNF[this.tier + 1]));
    }

    @Override
    public void addToolUsages(ItemStack stack,
                              World world,
                              List<String> tooltip,
                              boolean advanced) {
        tooltip.add(I18n.format("gregtech.tool_action.screwdriver.toggle_mode_covers"));
        tooltip.add(I18n.format("gregtech.tool_action.wrench.set_facing"));
        if (this.getSound() != null) {
            tooltip.add(I18n.format("gregtech.tool_action.hammer"));
        }

        tooltip.add(I18n.format("gregtech.tool_action.crowbar"));
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (this.isStructureFormed()) {
            int workingAreaChunks;
            if (this.energyContainer != null && this.energyContainer.getEnergyCapacity() > 0L) {
                workingAreaChunks = this.getEnergyTier();
                long maxVoltage = GTValues.V[workingAreaChunks];
                String voltageName = GTValues.VNF[workingAreaChunks];
                textList.add(new TextComponentTranslation("gregtech.multiblock.max_energy_per_tick", maxVoltage, voltageName));
            }

            workingAreaChunks = this.minerLogic.getCurrentRadius() * 2 / 16;
            int workingArea = this.getWorkingArea(this.minerLogic.getCurrentRadius());
            textList.add(new TextComponentTranslation("gregtech.machine.miner.startx", this.minerLogic.getX().get() == Integer.MAX_VALUE ? 0 : this.minerLogic.getX().get()));
            textList.add(new TextComponentTranslation("gregtech.machine.miner.starty", this.minerLogic.getY().get() == Integer.MAX_VALUE ? 0 : this.minerLogic.getY().get()));
            textList.add(new TextComponentTranslation("gregtech.machine.miner.startz", this.minerLogic.getZ().get() == Integer.MAX_VALUE ? 0 : this.minerLogic.getZ().get()));
            if (this.minerLogic.isChunkMode()) {
                textList.add(new TextComponentTranslation("gregtech.machine.miner.working_area_chunks", workingAreaChunks, workingAreaChunks));
            } else {
                textList.add(new TextComponentTranslation("gregtech.machine.miner.working_area", workingArea, workingArea));
            }

            if (this.minerLogic.isDone()) {
                textList.add((new TextComponentTranslation("gregtech.machine.miner.done")).setStyle((new Style()).setColor(TextFormatting.GREEN)));
            } else if (this.minerLogic.isWorking()) {
                textList.add((new TextComponentTranslation("gregtech.machine.miner.working")).setStyle((new Style()).setColor(TextFormatting.GOLD)));
            } else if (!this.isWorkingEnabled()) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.work_paused"));
            }
        }

    }

    private void addDisplayText2(List<ITextComponent> textList) {
        if (this.isStructureFormed()) {
            ITextComponent mCoords = (new TextComponentString("    "))
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
        MultiblockDisplayText.builder(textList, this.isStructureFormed(), false)
                .addLowPowerLine(this.isStructureFormed() && !this.drainEnergy(true))
                .addCustom((tl) -> {
                    if (this.isStructureFormed() && this.isInventoryFull) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.YELLOW, "gregtech.machine.miner.invfull"));
                    }
                });
    }

    @Override
    protected void addErrorText(List<ITextComponent> textList) {
        super.addErrorText(textList);
        if (this.isStructureFormed() && !this.drainFluid(true)) {
            textList.add(TextComponentUtil.translationWithColor(TextFormatting.RED, "gregtech.machine.miner.multi.needsfluid"));
        }
    }

    public IBlockState getCasingState() {
        if (this.material.equals(Materials.Naquadah)) {
            return GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.NQ_TURBINE_CASING);
        } else {
            return this.material.equals(Orichalcum) ? GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.ST_TURBINE_CASING) :
                    GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.AD_TURBINE_CASING);
        }
    }

    public IBlockState getTurbineState() {
        if (this.material.equals(Materials.Naquadah)) {
            return GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.NQ_MACHINE_CASING);
        } else {
            return this.material.equals(Orichalcum) ? GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.ST_MACHINE_CASING) :
                    GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.AD_MACHINE_CASING);
        }
    }

    private TraceabilityPredicate getFrameState() {
        if (this.material.equals(Materials.Naquadah)) {
            return frames(Materials.Naquadah);
        } else {
            return this.material.equals(Orichalcum) ? frames(Orichalcum) : frames(Adamantium);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        if (this.material.equals(Materials.Naquadah)) {
            return GTQTTextures.NQ_CASING;
        } else {
            return this.material.equals(Orichalcum) ? GTQTTextures.ST_CASING : GTQTTextures.AD_CASING;
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag("chunkMode", new NBTTagInt(this.chunkMode ? 1 : 0));
        data.setTag("silkTouch", new NBTTagInt(this.silkTouch ? 1 : 0));
        return this.minerLogic.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.chunkMode = data.getInteger("chunkMode") != 0;
        this.silkTouch = data.getInteger("silkTouch") != 0;
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

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.LARGE_MINER_OVERLAY_ADVANCED_2;
    }

    public long getMaxVoltage() {
        return GTValues.V[GTUtility.getTierByVoltage(this.energyContainer.getInputVoltage())];
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = super.createUITemplate(entityPlayer);
        builder.widget((new AdvancedTextWidget(63, 31, this::addDisplayText2, 16777215))
                .setMaxWidthLimit(68)
                .setClickHandler((x$0, x$1) -> {
                    this.handleDisplayClick(x$0, x$1);
                }));
        return builder;
    }


    private int getCurrentMode() {
        if (!this.minerLogic.isChunkMode() && !this.minerLogic.isSilkTouchMode()) {
            return 0;
        } else if (this.minerLogic.isChunkMode() && !this.minerLogic.isSilkTouchMode()) {
            return 1;
        } else {
            return !this.minerLogic.isChunkMode() && this.minerLogic.isSilkTouchMode() ? 2 : 3;
        }
    }

    private void setCurrentMode(int mode) {
        switch (mode) {
            case 0:
                this.minerLogic.setChunkMode(false);
                this.minerLogic.setSilkTouchMode(false);
                break;
            case 1:
                this.minerLogic.setChunkMode(true);
                this.minerLogic.setSilkTouchMode(false);
                break;
            case 2:
                this.minerLogic.setChunkMode(false);
                this.minerLogic.setSilkTouchMode(true);
                break;
            default:
                this.minerLogic.setChunkMode(true);
                this.minerLogic.setSilkTouchMode(true);
        }

    }

    protected Widget getFlexButton(int x, int y, int width, int height) {
        return (new ImageCycleButtonWidget(x, y, width, height, GuiTextures.BUTTON_MINER_MODES, 4, this::getCurrentMode, this::setCurrentMode)).setTooltipHoverString((mode) -> {
            String var10000;
            switch (mode) {
                case 0:
                    var10000 = "gregtech.multiblock.miner.neither_mode";
                    break;
                case 1:
                    var10000 = "gregtech.multiblock.miner.chunk_mode";
                    break;
                case 2:
                    var10000 = "gregtech.multiblock.miner.silk_touch_mode";
                    break;
                default:
                    var10000 = "gregtech.multiblock.miner.both_modes";
            }

            return var10000;
        });
    }

    @Override
    public boolean onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        if (!this.getWorld().isRemote && this.isStructureFormed()) {
            if (!this.isActive()) {
                int currentRadius = this.minerLogic.getCurrentRadius();
                int workingAreaChunks;
                if (this.minerLogic.isChunkMode()) {
                    if (currentRadius - 16 <= 0) {
                        this.minerLogic.setCurrentRadius(this.minerLogic.getMaximumRadius());
                    } else {
                        this.minerLogic.setCurrentRadius(currentRadius - 16);
                    }

                    workingAreaChunks = this.minerLogic.getCurrentRadius() * 2 / 16;
                    playerIn.sendMessage(new TextComponentTranslation("gregtech.machine.miner.working_area_chunks", workingAreaChunks, workingAreaChunks));
                } else {
                    if (currentRadius - 8 <= 0) {
                        this.minerLogic.setCurrentRadius(this.minerLogic.getMaximumRadius());
                    } else {
                        this.minerLogic.setCurrentRadius(currentRadius - 8);
                    }

                    workingAreaChunks = this.getWorkingArea(this.minerLogic.getCurrentRadius());
                    playerIn.sendMessage(new TextComponentTranslation("gregtech.universal.tooltip.working_area", workingAreaChunks, workingAreaChunks));
                }

                this.minerLogic.resetArea();
            } else {
                playerIn.sendMessage(new TextComponentTranslation("gregtech.machine.miner.errorradius"));
            }

            return true;
        } else {
            return true;
        }
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

    public Material getMaterial() {
        return this.material;
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
        return this.minerLogic.getMaximumRadius() / 16;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        return capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE ? GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this) : super.getCapability(capability, side);
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
        return this.minerLogic.isActive() && this.isWorkingEnabled();
    }

    @Override
    public List<ITextComponent> getDataInfo() {
        int workingArea = this.getWorkingArea(this.minerLogic.getCurrentRadius());
        return Collections.singletonList(new TextComponentTranslation("gregtech.machine.miner.working_area", workingArea, workingArea));
    }

    protected boolean shouldShowVoidingModeButton() {
        return false;
    }

    public boolean allowsExtendedFacing() {
        return false;
    }
}