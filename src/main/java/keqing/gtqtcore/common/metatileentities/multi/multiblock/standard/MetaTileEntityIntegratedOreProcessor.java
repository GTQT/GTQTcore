package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import com.google.common.collect.Lists;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMaintenanceHatch;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.*;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityEnergyHatch;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityItemBus;
import gregtech.common.metatileentities.multi.multiblockpart.appeng.MetaTileEntityMEInputBus;
import keqing.gtqtcore.api.capability.impl.OreProcessorRecipeLogic;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MetaTileEntityIntegratedOreProcessor extends MultiblockWithDisplayBase implements IDataInfoProvider {

    protected OreProcessorRecipeLogic logic;
    protected IEnergyContainer energyContainer;
    protected IItemHandlerModifiable inputItemInventory;
    protected IItemHandlerModifiable outputItemInventory;
    protected IMultipleTankHandler inputFluidInventory;

    public MetaTileEntityIntegratedOreProcessor(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.logic = new OreProcessorRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityIntegratedOreProcessor(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {
        this.logic.update();
    }

    public IEnergyContainer getEnergyContainer() {
        return energyContainer;
    }

    public IItemHandlerModifiable getInputInventory() {
        return inputItemInventory;
    }

    public IItemHandlerModifiable getOutputInventory() {
        return outputItemInventory;
    }

    public IMultipleTankHandler getInputFluidInventory() {
        return inputFluidInventory;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
    }

    protected void initializeAbilities() {
        this.inputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.inputItemInventory = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.outputItemInventory = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.INPUT_ENERGY));
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        resetTileAbilities();
        this.logic.invalidate();
    }

    private void resetTileAbilities() {
        this.inputFluidInventory = new FluidTankList(true);
        this.inputItemInventory = new ItemHandlerList(Collections.emptyList());
        this.outputItemInventory = new ItemHandlerList(Collections.emptyList());
        this.energyContainer = new EnergyContainerList(Lists.newArrayList());
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("EEEEEE     ", "IGGGGI     ", "IGGGGI     ", "IGGGGI     ", "IGGGGI     ", "IIIIII     ", "           ", "           ", "           ", "           ", "           ", "           ")
                .aisle("EEEEEEEEEEE", "GT  T ICCCI", "GT  T ICCCI", "GT  T ICCCI", "GT  T ICCCI", "IOOOOIICCCI", "       CCC ", "       CCC ", "       CCC ", "       CCC ", "       CCC ", "           ")
                .aisle("EEEEEEEEEEE", "G XX  C   D", "G XX  CPPPD", "G XX  C   C", "G XX  CPPPC", "IOOOOIC   C", "      CPPPC", "      C   C", "      CPPPC", "      C   C", "      CPPPC", "       WWW ")
                .aisle("EEEEEEEEEEE", "G XX  C   D", "G XX  CPPPD", "G XX  C   C", "G XX  CPPPC", "IOOOOIC   C", "      CPPPC", "      C   C", "      CPPPC", "      C   C", "      CPPPC", "       WWW ")
                .aisle("EEEEEEEEEEE", "GT  T ICCCI", "GT  T ICSCI", "GT  T ICCCI", "GT  T ICCCI", "IOOOOIICCCI", "       CCC ", "       CCC ", "       CCC ", "       CCC ", "       CCC ", "           ")
                .aisle("EEEEEE     ", "IGGGGI     ", "IGGGGI     ", "IGGGGI     ", "IGGGGI     ", "IIIIII     ", "           ", "           ", "           ", "           ", "           ", "           ")
                .where('S', this.selfPredicate())
                .where('I', states(getCasingState()))
                .where('C', states(getSecondCasingState()))
                .where('P', states(getBoilerCasingState()))
                .where('G', states(getGlassState()))
                .where('T', states(getFrameState()))
                .where('X', states(getThirdCasingState()))
                .where('E', states(getCasingState())
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH)
                                .setExactLimit(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY)
                                .setMinGlobalLimited(1)
                                .setMaxGlobalLimited(3)))
                .where('O', states(getCasingState())
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS)
                                .setPreviewCount(1)))
                .where('W', states(getSecondCasingState())
                        .or(abilities(MultiblockAbility.MUFFLER_HATCH)
                                .setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS)
                                .setPreviewCount(1)))
                .where('D', states(getSecondCasingState())
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS)
                                .setPreviewCount(1)))
                .where(' ', any())
                .build();
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.IRIDIUM_CASING);
    }
    private static IBlockState getSecondCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN);
    }
    private static IBlockState getBoilerCasingState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE);
    }
    private static IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.LAMINATED_GLASS);
    }
    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.TungstenSteel).getBlock(Materials.TungstenSteel);
    }
    private static IBlockState getThirdCasingState() {
        return MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STEEL_GEARBOX);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        if ((iMultiblockPart instanceof MetaTileEntityItemBus && ((MetaTileEntityItemBus) iMultiblockPart).getExportItems().getSlots() == 0)
                || iMultiblockPart instanceof MetaTileEntityMEInputBus
                || iMultiblockPart instanceof MetaTileEntityEnergyHatch
                || iMultiblockPart instanceof IMaintenanceHatch) {
            return GTQTTextures.IRIDIUM_CASING;
        } else {
            return Textures.CLEAN_STAINLESS_STEEL_CASING;
        }
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.HYDRAULIC_FRACKER_OVERLAY;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState,
                                     Matrix4 translation,
                                     IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.logic.isActive(), this.logic.isWorkingEnabled());
    }


    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (isStructureFormed()) {
            if (this.energyContainer != null && energyContainer.getEnergyCapacity() > 0) {
                long maxVoltage = Math.max(energyContainer.getInputVoltage(), energyContainer.getOutputVoltage());
                String voltageName = GTValues.VNF[GTUtility.getFloorTierByVoltage(maxVoltage)];
                textList.add(new TextComponentTranslation("gregtech.multiblock.max_energy_per_tick", TextFormattingUtil.formatNumbers(maxVoltage), voltageName));
            }
            if (!logic.isWorkingEnabled()) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.work_paused"));
            } else if (logic.isActive()) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.running"));
                int currentProgress = (int) (logic.getProgressPercent() * 100);
                if (logic.getParallelLimit() != 1) {
                    textList.add(new TextComponentTranslation("gregtech.multiblock.parallel", logic.getParallelLimit()));
                }
                textList.add(new TextComponentTranslation("gregtech.multiblock.progress", currentProgress));
            } else {
                textList.add(new TextComponentTranslation("gregtech.multiblock.idling"));
            }
            switch (logic.getMode()) {
                case 0 -> textList.add(new TextComponentTranslation("gtqtcore.machine.integrated_ore_processor.mode.0"));
                case 1 -> textList.add(new TextComponentTranslation("gtqtcore.machine.integrated_ore_processor.mode.1"));
                case 2 -> textList.add(new TextComponentTranslation("gtqtcore.machine.integrated_ore_processor.mode.2"));
                case 3 -> textList.add(new TextComponentTranslation("gtqtcore.machine.integrated_ore_processor.mode.3"));
                case 4 -> textList.add(new TextComponentTranslation("gtqtcore.machine.integrated_ore_processor.mode.4"));
                default -> textList.add(new TextComponentTranslation("gtqtcore.machine.integrated_ore_processor.mode.error"));
            }
            if (!drainEnergy(true)) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.not_enough_energy").setStyle(new Style().setColor(TextFormatting.RED)));
            }
            textList.add(new TextComponentTranslation("gregtech.integrated_ore_processor.statue",logic.furnace,logic.isVoidStone));
        }
    }

    @Override
    public void addInformation(ItemStack stack,
                               @Nullable World world,
                               @Nonnull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("§aIt is OP. I mean Ore Processor."));
        tooltip.add(I18n.format("gtqtcore.machine.integrated_ore_processor.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.integrated_ore_processor.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.integrated_ore_processor.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.machine.integrated_ore_processor.tooltip.4"));
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            tooltip.add(I18n.format("gtqtcore.machine.integrated_ore_processor.tooltip_shift.1"));
            tooltip.add(I18n.format("gtqtcore.machine.integrated_ore_processor.tooltip_shift.2"));
        } else {
            tooltip.add(I18n.format("gregtech.tooltip.hold_shift"));
        }
    }
    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 18, 9, "", this::incrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("gtqtcore.multiblock.iop.threshold_increment"));

        group.addWidget(new ClickButtonWidget(0, 9, 9, 9, "", this::furnace)
                .setButtonTexture(GuiTextures.BUTTON_FILTER_DAMAGE)
                .setTooltipText("gtqtcore.multiblock.iop.furnace"));
        group.addWidget(new ClickButtonWidget(9, 9, 9, 9, "", this::stone)
                .setButtonTexture(GuiTextures.BUTTON_ALLOW_IMPORT_EXPORT)
                .setTooltipText("gtqtcore.multiblock.iop.stone"));
        return group;
    }

    private void incrementThreshold(Widget.ClickData clickData) {
        logic.setMode((logic.getMode() + 1) % 5);
    }
    private void furnace(Widget.ClickData clickData) {
        logic.setFurnace(!logic.furnace);
    }
    private void stone(Widget.ClickData clickData) {
        logic.setVoidStone(!logic.isVoidStone);
    }
    @Override
    public final boolean onScrewdriverClick(EntityPlayer playerIn,
                                            EnumHand hand,
                                            EnumFacing facing,
                                            CuboidRayTraceResult hitResult) {
        if (playerIn.isSneaking()) {
            logic.setVoidStone(!logic.isVoidStone());
            if (playerIn.getEntityWorld().isRemote) {
                playerIn.sendMessage( new TextComponentTranslation("gtqtcore.machine.integrated_ore_processor.void." + logic.isVoidStone()));
            }
        } else {
            logic.setMode((logic.getMode() + 1) % 5);
        }
        return true;
    }

    public int getEnergyTier() {
        if (energyContainer == null) return GTValues.IV;
        return Math.max(GTValues.IV, GTUtility.getFloorTierByVoltage(energyContainer.getInputVoltage()));
    }

    @Override
    public boolean isActive() {
        return (isStructureFormed() && this.logic.isActive() && this.logic.isWorkingEnabled());
    }


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
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound data) {
        super.writeToNBT(data);
        return this.logic.serializeNBT(data);
    }

   
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.logic.deserializeNBT(data);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        this.logic.writeInitialData(buf);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.logic.receiveInitialData(buf);
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        this.logic.receiveCustomData(dataId, buf);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_WORKABLE)
            return GregtechTileCapabilities.CAPABILITY_WORKABLE.cast(this.logic);
        if (capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE)
            return GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this.logic);
        return super.getCapability(capability, side);
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ArrayList<>();
        if (logic.getMaxProgress() > 0) {
            list.add(new TextComponentTranslation("behavior.tricorder.workable_progress",
                    new TextComponentTranslation(TextFormattingUtil.formatNumbers(logic.getProgress() / 20)).setStyle(new Style().setColor(TextFormatting.GREEN)),
                    new TextComponentTranslation(TextFormattingUtil.formatNumbers(logic.getMaxProgress() / 20)).setStyle(new Style().setColor(TextFormatting.YELLOW))
            ));
        }

        list.add(new TextComponentTranslation("behavior.tricorder.energy_container_storage",
                new TextComponentTranslation(TextFormattingUtil.formatNumbers(energyContainer.getEnergyStored())).setStyle(new Style().setColor(TextFormatting.GREEN)),
                new TextComponentTranslation(TextFormattingUtil.formatNumbers(energyContainer.getEnergyCapacity())).setStyle(new Style().setColor(TextFormatting.YELLOW))
        ));

        if (logic.getRecipeEUt() > 0) {
            list.add(new TextComponentTranslation("behavior.tricorder.workable_consumption",
                    new TextComponentTranslation(TextFormattingUtil.formatNumbers(logic.getRecipeEUt())).setStyle(new Style().setColor(TextFormatting.RED)),
                    new TextComponentTranslation(TextFormattingUtil.formatNumbers(logic.getRecipeEUt() == 0 ? 0 : 1)).setStyle(new Style().setColor(TextFormatting.RED))
            ));
        }

        list.add(new TextComponentTranslation("behavior.tricorder.multiblock_energy_input",
                new TextComponentTranslation(TextFormattingUtil.formatNumbers(energyContainer.getInputVoltage())).setStyle(new Style().setColor(TextFormatting.YELLOW)),
                new TextComponentTranslation(GTValues.VN[GTUtility.getTierByVoltage(energyContainer.getInputVoltage())]).setStyle(new Style().setColor(TextFormatting.YELLOW))
        ));

        if (ConfigHolder.machines.enableMaintenance && hasMaintenanceMechanics()) {
            list.add(new TextComponentTranslation("behavior.tricorder.multiblock_maintenance",
                    new TextComponentTranslation(TextFormattingUtil.formatNumbers(getNumMaintenanceProblems())).setStyle(new Style().setColor(TextFormatting.RED))
            ));
        }

        if (logic.getParallelLimit() > 1) {
            list.add(new TextComponentTranslation("behavior.tricorder.multiblock_parallel",
                    new TextComponentTranslation(TextFormattingUtil.formatNumbers(logic.getParallelLimit())).setStyle(new Style().setColor(TextFormatting.GREEN))
            ));
        }
        if (logic.getMode() < 5 && logic.getMode() >= 0) {
            list.add(new TextComponentTranslation("gtqtcore.machine.integrated_ore_processor.mode." + logic.getMode()));
        }
        return list;
    }

}

