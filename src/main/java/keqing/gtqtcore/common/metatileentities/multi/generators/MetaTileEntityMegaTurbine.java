package keqing.gtqtcore.common.metatileentities.multi.generators;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.api.widget.Interactable;
import com.cleanroommc.modularui.value.sync.IntSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.value.sync.StringSyncValue;
import com.cleanroommc.modularui.widgets.CycleButtonWidget;
import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IRotorHolder;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.capability.impl.MultiblockFuelRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ImageCycleButtonWidget;
import gregtech.api.metatileentity.ITieredMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.FuelMultiblockController;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.ProgressBarMultiblock;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIBuilder;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIFactory;
import gregtech.api.metatileentity.multiblock.ui.TemplateBarBuilder;
import gregtech.api.mui.GTGuiTextures;
import gregtech.api.mui.sync.FixedIntArraySyncValue;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.KeyUtil;
import gregtech.api.util.LocalizationUtils;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.items.behaviors.TurbineRotorBehavior;
import keqing.gtqtcore.api.capability.GTQTDataCode;
import keqing.gtqtcore.api.capability.IReinforcedRotorHolder;
import keqing.gtqtcore.api.metatileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.metatileentity.multiblock.ITurbineMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static keqing.gtqtcore.api.pattern.GTQTTraceabilityPredicate.rotorHolders;

public class MetaTileEntityMegaTurbine extends FuelMultiblockController implements ITieredMetaTileEntity, ITurbineMode, ProgressBarMultiblock {

    private static final int MIN_DURABILITY_TO_WARN = 10;
    public final int tier;
    public final IBlockState casingState;
    public final IBlockState gearboxState;
    public final ICubeRenderer casingRenderer;
    public final boolean hasMufflerHatch;
    public final ICubeRenderer frontOverlay;
    public IFluidHandler exportFluidHandler;
    private int mode = 0;

    public MetaTileEntityMegaTurbine(ResourceLocation metaTileEntityId,
                                     RecipeMap<?> recipeMap,
                                     int tier,
                                     IBlockState casingState,
                                     IBlockState gearboxState,
                                     ICubeRenderer casingRenderer,
                                     boolean hasMufflerHatch,
                                     ICubeRenderer frontOverlay) {
        super(metaTileEntityId, recipeMap, tier);
        this.casingState = casingState;
        this.gearboxState = gearboxState;
        this.casingRenderer = casingRenderer;
        this.hasMufflerHatch = hasMufflerHatch;
        this.frontOverlay = frontOverlay;
        this.tier = tier;
        this.recipeMapWorkable = new MegaTurbineWorkableHandler(this, tier);
        this.recipeMapWorkable.setMaximumOverclockVoltage(GTValues.V[tier]);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMegaTurbine(metaTileEntityId, recipeMap, tier, casingState, gearboxState, casingRenderer, hasMufflerHatch, frontOverlay);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.exportFluidHandler = new FluidTankList(true, getAbilities(MultiblockAbility.EXPORT_FLUIDS));
        ((MegaTurbineWorkableHandler) this.recipeMapWorkable).updateTanks();
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.exportFluidHandler = null;
    }

    @Override
    protected void initializeAbilities() {
        this.inputInventory = new ItemHandlerList(this.getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.inputFluidInventory = new FluidTankList(this.allowSameFluidFillForOutputs(), this.getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.outputInventory = new ItemHandlerList(this.getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.outputFluidInventory = new FluidTankList(this.allowSameFluidFillForOutputs(), this.getAbilities(MultiblockAbility.EXPORT_FLUIDS));
        List<IEnergyContainer> energyContainer = new ArrayList<>(this.getAbilities(MultiblockAbility.OUTPUT_ENERGY));
        energyContainer.addAll(this.getAbilities(MultiblockAbility.OUTPUT_LASER));
        this.energyContainer = new EnergyContainerList(energyContainer);
    }


    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return super.checkRecipe(recipe, consumeIfSuccess) && checkRotors() && checkRotorMaterial();
    }

    @Override
    protected long getMaxVoltage() {
        long maxProduction = recipeMapWorkable.getMaxVoltage();
        long currentProduction = ((MegaTurbineWorkableHandler) recipeMapWorkable).boostProduction((int) maxProduction);
        if (isActive() && currentProduction < maxProduction) {
            return recipeMapWorkable.getMaxVoltage();
        } else {
            return 0L;
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    protected boolean isRotorFaceFree() {
        List<IReinforcedRotorHolder> rotorHolders = getRotorHolders();
        if (!isStructureFormed() || rotorHolders == null) return false;

        for (IReinforcedRotorHolder rotorHolder : rotorHolders) {
            if (!rotorHolder.isFrontFaceFree()) {
                return false;
            }
        }
        return true;
    }


    @Override
    public List<IReinforcedRotorHolder> getRotorHolders() {
        return getAbilities(GTQTMultiblockAbility.REINFORCED_ROTOR_HOLDER_ABILITY);
    }

    @Override
    public void onRemoval() {
        super.onRemoval();
        for (IReinforcedRotorHolder holder : getRotorHolders()) {
            holder.setCurrentSpeed(0);
        }
    }

    protected void setupRotors() {
        if (checkRotors()) return;
        for (int index = 0; index < inputInventory.getSlots(); index++) {
            ItemStack itemStack = inputInventory.getStackInSlot(index);
            if (itemStack.isEmpty()) continue;

            if (TurbineRotorBehavior.getInstanceFor(itemStack) == null) continue;

            for (IReinforcedRotorHolder holder : getRotorHolders()) {
                if (!holder.hasRotor()) {
                    inputInventory.extractItem(index, 1, false);
                    holder.setRotor(itemStack);
                    break;
                }
            }
        }
    }

    protected void setSpeed(int speed) {
        for (IReinforcedRotorHolder holder : getRotorHolders()) {
            if (holder.hasRotor()) {
                holder.setCurrentSpeed(speed);
            }
        }
    }

    protected boolean checkRotors() {
        for (IReinforcedRotorHolder holder : getRotorHolders()) {
            if (!holder.hasRotor()) {
                return false;
            }
        }
        return true;
    }

    protected boolean checkRotorMaterial() {
        return getRotorHolders().stream()
                .map(IReinforcedRotorHolder::getRotorMaterial)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet()).size() == 1;
    }

    @Override
    public void update() {
        super.update();
        if (getOffsetTimer() % 20 == 0) {
            if (!checkRotors()) {
                setSpeed(0);
            }
            if (!getWorld().isRemote && isStructureFormed()) {
                setupRotors();
            }
        }
    }

    @NotNull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCCCCCC", "CRCACRC", "CCCACCC", "CCCACCC", "CRCACRC", "CCCACCC", "CCCACCC", "CRCACRC", "CCCCCCC")
                .aisle("CCCCCCC", "CGCCCGC", "CCCCCCC", "CCCCCCC", "CGCCCGC", "CCCCCCC", "CCCCCCC", "CGCCCGC", "CCCCCCC")
                .aisle("CCCCCCC", "CGCCCGC", "CCCCCCC", "CCCCCCC", "CGCCCGC", "CCCCCCC", "CCCCCCC", "CGCCCGC", "CCMMMCC")
                .aisle("CCCCCCC", "CGCCCGC", "CCCCCCC", "CCCCCCC", "CGCCCGC", "CCCCCCC", "CCCCCCC", "CGCCCGC", "CCMCMCC")
                .aisle("CCCCCCC", "CGCCCGC", "CCCCCCC", "CCCCCCC", "CGCCCGC", "CCCCCCC", "CCCCCCC", "CGCCCGC", "CCMMMCC")
                .aisle("CCCCCCC", "CGCCCGC", "CCCCCCC", "CCCCCCC", "CGCCCGC", "CCCCCCC", "CCCCCCC", "CGCCCGC", "CCCCCCC")
                .aisle("CCCCCCC", "CRCACRC", "CCCACCC", "CCCACCC", "CRCSCRC", "CCCACCC", "CCCACCC", "CRCACRC", "CCCCCCC")
                .where('S', this.selfPredicate())
                .where('C', states(getCasingState()))
                .where('G', states(getGearBoxState()))
                .where('R', rotorHolders())
                .where('M', states(getCasingState())
                        .or(abilities(MultiblockAbility.MUFFLER_HATCH))
                        .setPreviewCount(8))
                .where('A', states(getCasingState())
                        .or(abilities(MultiblockAbility.OUTPUT_ENERGY)
                                .setMaxGlobalLimited(1)
                                .setPreviewCount(1))
                        .or(abilities(MultiblockAbility.OUTPUT_LASER)
                                .setMaxGlobalLimited(1)
                                .setPreviewCount(1))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH)
                                .setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS)
                                .setMaxGlobalLimited(1)
                                .setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS)
                                .setMinGlobalLimited(1)
                                .setMaxGlobalLimited(4)
                                .setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS)
                                .setMinGlobalLimited(1)
                                .setMaxGlobalLimited(4)
                                .setPreviewCount(1)))
                .build();
    }

    public IBlockState getCasingState() {
        return casingState;
    }

    public IBlockState getGearBoxState() {
        return gearboxState;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return casingRenderer;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.isActive(),
                true);
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @Override
    protected MultiblockUIFactory createUIFactory() {
        return super.createUIFactory()
                .createFlexButton((posGuiData, panelSyncManager) -> {
                    IntSyncValue buttonSync = new IntSyncValue(this::getMode, this::setMode);

                    return new CycleButtonWidget() {

                        @Override
                        public @NotNull Result onMousePressed(int mouseButton) {
                            if (recipeMapWorkable.isWorking()) {
                                Interactable.playButtonClickSound();
                                return Result.IGNORE;
                            } else {
                                return super.onMousePressed(mouseButton);
                            }
                        }
                    }
                            .stateCount(2)
                            .value(buttonSync)
                            .addTooltip(0, IKey.lang("低速模式(只能在停机时切换)"))  // 模式0的提示
                            .addTooltip(1, IKey.lang("高速模式(只能在停机时切换)")); // 模式1的提示
                });
    }

    @Override
    protected void configureDisplayText(MultiblockUIBuilder builder) {
        MultiblockFuelRecipeLogic recipeLogic = (MultiblockFuelRecipeLogic) recipeMapWorkable;
        builder.setWorkingStatus(recipeLogic.isWorkingEnabled(), recipeLogic.isActive())
                .addEnergyProductionLine(getMaxVoltage(), recipeLogic.getRecipeEUt())
                .addCustom((keyList, syncer) -> {
                    if (!isStructureFormed()) return;

                    int rotorEfficiency = syncer.syncInt(() -> getRotorHolder().getRotorEfficiency());
                    int totalEfficiency = syncer.syncInt(() -> getRotorHolder().getTotalEfficiency());

                    if (rotorEfficiency > 0) {
                        IKey efficiencyInfo = KeyUtil.number(TextFormatting.AQUA,
                                totalEfficiency, "%");
                        keyList.add(KeyUtil.lang(
                                "gregtech.multiblock.turbine.efficiency",
                                efficiencyInfo));

                    }
                    IKey mod;
                    mod = KeyUtil.lang( "模式：%s", mode == 0 ? "低速模式" : "高速模式");
                    keyList.add(KeyUtil.setHover(mod));
                })
                .addFuelNeededLine(recipeLogic.getRecipeFluidInputInfo(), recipeLogic.getPreviousRecipeDuration())
                .addWorkingStatusLine();
    }

    @Override
    protected void configureWarningText(MultiblockUIBuilder builder) {
        builder.addCustom((keyList, syncer) -> {
            if (!isStructureFormed() || syncer.syncBoolean(() -> getRotorHolder() == null))
                return;

            int rotorEfficiency = syncer.syncInt(() -> getRotorHolder().getRotorEfficiency());
            int rotorDurability = syncer.syncInt(() -> getRotorHolder().getRotorDurabilityPercent());

            if (rotorEfficiency > 0 && rotorDurability <= MIN_DURABILITY_TO_WARN) {
                keyList.add(KeyUtil.lang(TextFormatting.YELLOW,
                        "gregtech.multiblock.turbine.rotor_durability_low"));
            }
        });
        super.configureWarningText(builder);
    }

    @Override
    protected void configureErrorText(MultiblockUIBuilder builder) {
        super.configureErrorText(builder);
        builder.addCustom((keyList, syncer) -> {
            if (!isStructureFormed() || syncer.syncBoolean(() -> getRotorHolder() == null))
                return;

            if (syncer.syncBoolean(!isRotorFaceFree())) {
                keyList.add(KeyUtil.lang(TextFormatting.RED,
                        "gregtech.multiblock.turbine.obstructed"));
                keyList.add(KeyUtil.lang(
                        "gregtech.multiblock.turbine.obstructed.desc"));
            }
            int rotorEfficiency = syncer.syncInt(() -> getRotorHolder().getRotorEfficiency());

            if (rotorEfficiency <= 0) {
                keyList.add(KeyUtil.lang(TextFormatting.RED,
                        "gregtech.multiblock.turbine.no_rotor"));
            }
        });
    }

    private IReinforcedRotorHolder getRotorHolder() {
        List<IReinforcedRotorHolder> holders = getRotorHolders();
        if (holders == null || holders.isEmpty()) {
            return null;
        }
        return holders.get(0);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.universal.tooltip.base_production_eut", GTValues.V[tier] * 2 * 16));
        tooltip.add(I18n.format("gregtech.multiblock.turbine.efficiency_tooltip", GTValues.VNF[tier]));
    }

    @Override
    public String[] getDescription() {
        return new String[]{I18n.format("gtqtcore.machine.mega_turbine.description")};
    }

    @Nonnull
    @Override
    protected Widget getFlexButton(int x, int y, int width, int height) {
        return new ImageCycleButtonWidget(x, y, width, height, GuiTextures.BUTTON_THROTTLE_PLUS,
                2, this::getMode, this::setMode)
                .shouldUseBaseBackground().singleTexture()
                .setTooltipHoverString(i -> LocalizationUtils.format("gregtech.machine.mega_turbine.mode." + getMode()));
    }

    @Override
    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
        if (!getWorld().isRemote) {
            writeCustomData(GTQTDataCode.ChannelMegaTurbine, buf -> buf.writeByte(mode));
            markDirty();
        }
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GTQTDataCode.ChannelMegaTurbine) {
            this.mode = buf.readByte();
            scheduleRenderUpdate();
        }
    }

    @Override
    public int getProgressBarCount() {
        return 3;
    }

    @Override
    public void registerBars(List<UnaryOperator<TemplateBarBuilder>> bars, PanelSyncManager syncManager) {
        FixedIntArraySyncValue fuelValue = new FixedIntArraySyncValue(this::getFuelAmount, null);
        StringSyncValue fuelNameValue = new StringSyncValue(() -> {
            FluidStack stack = ((MultiblockFuelRecipeLogic) recipeMapWorkable).getInputFluidStack();
            if (stack == null) {
                return null;
            }
            Fluid fluid = stack.getFluid();
            if (fluid == null) {
                return null;
            }
            return fluid.getName();
        });
        syncManager.syncValue("fuel_amount", fuelValue);
        syncManager.syncValue("fuel_name", fuelNameValue);

        IntSyncValue rotorSpeedValue = new IntSyncValue(() -> {
            IRotorHolder rotorHolder = getRotorHolder();
            if (rotorHolder == null) {
                return 0;
            }
            return rotorHolder.getRotorSpeed();
        });

        IntSyncValue rotorMaxSpeedValue = new IntSyncValue(() -> {
            IRotorHolder rotorHolder = getRotorHolder();
            if (rotorHolder == null) {
                return 0;
            }
            return rotorHolder.getMaxRotorHolderSpeed();
        });

        syncManager.syncValue("rotor_speed", rotorSpeedValue);
        syncManager.syncValue("rotor_max_speed", rotorMaxSpeedValue);
        IntSyncValue durabilityValue = new IntSyncValue(() -> {
            IRotorHolder rotorHolder = getRotorHolder();
            if (rotorHolder == null) {
                return 0;
            }
            return rotorHolder.getRotorDurabilityPercent();
        });
        IntSyncValue efficiencyValue = new IntSyncValue(() -> {
            IRotorHolder rotorHolder = getRotorHolder();
            if (rotorHolder == null) {
                return 0;
            }
            return rotorHolder.getRotorEfficiency();
        });

        syncManager.syncValue("rotor_durability", durabilityValue);
        syncManager.syncValue("rotor_efficiency", efficiencyValue);

        bars.add(barTest -> barTest
                .progress(() -> fuelValue.getValue(1) == 0 ? 0 :
                        1.0 * fuelValue.getValue(0) / fuelValue.getValue(1))
                .texture(GTGuiTextures.PROGRESS_BAR_LCE_FUEL)
                .tooltipBuilder(t -> createFuelTooltip(t, fuelValue, fuelNameValue)));

        bars.add(barTest -> barTest
                .progress(() -> rotorMaxSpeedValue.getIntValue() == 0 ? 0 :
                        1.0 * rotorSpeedValue.getIntValue() / rotorMaxSpeedValue.getIntValue())
                .texture(GTGuiTextures.PROGRESS_BAR_TURBINE_ROTOR_SPEED)
                .tooltipBuilder(t -> {
                    if (isStructureFormed()) {
                        int speed = rotorSpeedValue.getIntValue();
                        int maxSpeed = rotorMaxSpeedValue.getIntValue();

                        t.addLine(KeyUtil.lang("gregtech.multiblock.turbine.rotor_speed",
                                getSpeedFormat(maxSpeed, speed), speed, maxSpeed));
                    } else {
                        t.addLine(IKey.lang("gregtech.multiblock.invalid_structure"));
                    }
                }));

        bars.add(barTest -> barTest
                .progress(() -> durabilityValue.getIntValue() / 100.0)
                .texture(GTGuiTextures.PROGRESS_BAR_TURBINE_ROTOR_DURABILITY)
                .tooltipBuilder(t -> {
                    if (isStructureFormed()) {
                        if (efficiencyValue.getIntValue() <= 0) {
                            t.addLine(IKey.lang("gregtech.multiblock.turbine.no_rotor"));
                        } else {
                            int durability = durabilityValue.getIntValue();
                            // TODO working dynamic color substitutions into IKey.lang
                            if (durability > 40) {
                                t.addLine(IKey.lang("gregtech.multiblock.turbine.rotor_durability.high",
                                        durability));
                            } else if (durability > MIN_DURABILITY_TO_WARN) {
                                t.addLine(IKey.lang("gregtech.multiblock.turbine.rotor_durability.medium",
                                        durability));
                            } else {
                                t.addLine(IKey.lang("gregtech.multiblock.turbine.rotor_durability.low",
                                        durability));
                            }
                        }
                    } else {
                        t.addLine(IKey.lang("gregtech.multiblock.invalid_structure"));
                    }
                }));
    }

    private @NotNull TextFormatting getSpeedFormat(int maxSpeed, int speed) {
        float percent = maxSpeed == 0 ? 0 : 1.0f * speed / maxSpeed;

        if (percent < 0.4) {
            return TextFormatting.RED;
        } else if (percent < 0.8) {
            return TextFormatting.YELLOW;
        } else {
            return TextFormatting.GREEN;
        }
    }

    /**
     * @return an array of [fuel stored, fuel capacity]
     */
    private int[] getFuelAmount() {
        if (getInputFluidInventory() != null) {
            MultiblockFuelRecipeLogic recipeLogic = (MultiblockFuelRecipeLogic) recipeMapWorkable;
            if (recipeLogic.getInputFluidStack() != null) {
                FluidStack testStack = recipeLogic.getInputFluidStack().copy();
                testStack.amount = Integer.MAX_VALUE;
                return getTotalFluidAmount(testStack, getInputFluidInventory());
            }
        }
        return new int[2];
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public boolean isStructureObstructed() {
        return super.isStructureObstructed() || !isRotorFaceFree();
    }

    @Override
    public boolean hasMufflerMechanics() {
        return hasMufflerHatch;
    }

    @Override
    public boolean canVoidRecipeItemOutputs() {
        return true;
    }

    @Override
    public boolean canVoidRecipeFluidOutputs() {
        return true;
    }

    @Override
    public boolean shouldShowVoidingModeButton() {
        return false;
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeByte(mode);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.mode = buf.readByte();
    }


    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setByte("mode", (byte) mode);
        return super.writeToNBT(data);
    }


    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.mode = data.getByte("mode");
    }
}
