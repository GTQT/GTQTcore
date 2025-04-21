package keqing.gtqtcore.common.metatileentities.multi.generators;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.MultiblockFuelRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.ImageCycleButtonWidget;
import gregtech.api.metatileentity.ITieredMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.FuelMultiblockController;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.IProgressBarMultiblock;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.api.util.LocalizationUtils;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.items.behaviors.TurbineRotorBehavior;
import keqing.gtqtcore.api.capability.GTQTDataCode;
import keqing.gtqtcore.api.capability.IReinforcedRotorHolder;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.metaileentity.multiblock.ITurbineMode;
import keqing.gtqtcore.api.pattern.GTQTTraceabilityPredicate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static gregtech.api.util.TextFormattingUtil.formatNumbers;

public class MetaTileEntityMegaTurbine extends FuelMultiblockController implements ITieredMetaTileEntity, ITurbineMode, IProgressBarMultiblock {

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
        List<IReinforcedRotorHolder> abilities = getAbilities(GTQTMultiblockAbility.REINFORCED_ROTOR_HOLDER_ABILITY);
        return abilities;
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

    @Nonnull
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
                .where('R', GTQTTraceabilityPredicate.ROTOR_HOLDER.get())
                .where('M', states(getCasingState())
                        .or(abilities(MultiblockAbility.MUFFLER_HATCH))
                        .setPreviewCount(8))
                .where('A', states(getCasingState())
                        .or(abilities(MultiblockAbility.OUTPUT_ENERGY)
                                .setPreviewCount(1)
                                .setMaxGlobalLimited(4))
                        .or(abilities(MultiblockAbility.OUTPUT_LASER)
                                .setPreviewCount(1)
                                .setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH)
                                .setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS)
                                .setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS)
                                .setMinGlobalLimited(1)
                                .setMaxGlobalLimited(4))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS)
                                .setMinGlobalLimited(1)
                                .setMaxGlobalLimited(4)))
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
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (isStructureFormed()) {
            IReinforcedRotorHolder rotorHolder = getRotorHolders().get(0);

            if (checkRotors() && checkRotorMaterial() && rotorHolder.getRotorEfficiency() > 0) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.turbine.rotor_speed", formatNumbers(rotorHolder.getRotorSpeed()), formatNumbers(rotorHolder.getMaxRotorHolderSpeed())));
                textList.add(new TextComponentTranslation("gregtech.multiblock.turbine.efficiency", rotorHolder.getTotalEfficiency()));

                long maxProduction = recipeMapWorkable.getMaxVoltage();
                long currentProduction = isActive() ? ((MegaTurbineWorkableHandler) recipeMapWorkable).boostProduction((int) maxProduction) : 0;
                String voltageName = GTValues.VNF[GTUtility.getTierByVoltage(currentProduction)];

                if (isActive()) {
                    textList.add(3, new TextComponentTranslation("gregtech.multiblock.turbine.energy_per_tick", formatNumbers(currentProduction), voltageName));
                }

                int rotorDurability = rotorHolder.getRotorDurabilityPercent();
                if (rotorDurability > MIN_DURABILITY_TO_WARN) {
                    textList.add(new TextComponentTranslation("gregtech.multiblock.turbine.rotor_durability", rotorDurability));
                } else {
                    textList.add(new TextComponentTranslation("gregtech.multiblock.turbine.rotor_durability", rotorDurability).setStyle(new Style().setColor(TextFormatting.RED)));
                }
            }
        }
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (isStructureFormed()) {
            if (!checkRotors()) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.turbine.no_rotor"));
            }
        }
    }

    @Override
    protected void addErrorText(List<ITextComponent> textList) {
        super.addErrorText(textList);
        if (isStructureFormed() && !isRotorFaceFree()) {
            textList.add(new TextComponentTranslation("gregtech.multiblock.turbine.obstructed").setStyle(new Style().setColor(TextFormatting.RED)));
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.universal.tooltip.base_production_eut", GTValues.V[tier] * 2 * 16));
        tooltip.add(I18n.format("gregtech.multiblock.turbine.efficiency_tooltip", GTValues.VNF[tier]));
    }

    @Override
    public String[] getDescription() {
        return new String[]{I18n.format("gregtech.machine.mega_turbine.description")};
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
    public int getNumProgressBars() {
        return 3;
    }

    @Override
    public double getFillPercentage(int index) {
        IReinforcedRotorHolder rotorHolder = getRotorHolders().get(0);

        long maxProduction = recipeMapWorkable.getMaxVoltage();
        long currentProduction = isActive() ? ((MegaTurbineWorkableHandler) recipeMapWorkable).boostProduction((int) maxProduction) : 0;
        if (index == 0) {
            int[] fuelAmount = new int[2];
            if (getInputFluidInventory() != null) {
                MultiblockFuelRecipeLogic recipeLogic = (MultiblockFuelRecipeLogic) recipeMapWorkable;
                if (recipeLogic.getInputFluidStack() != null) {
                    FluidStack testStack = recipeLogic.getInputFluidStack().copy();
                    testStack.amount = Integer.MAX_VALUE;
                    fuelAmount = getTotalFluidAmount(testStack, getInputFluidInventory());
                }
            }
            return fuelAmount[1] != 0 ? 1.0 * fuelAmount[0] / fuelAmount[1] : 0;
        } else if (index == 1) {
            return 1.0 * rotorHolder.getRotorSpeed() / rotorHolder.getMaxRotorHolderSpeed();
        } else {
            return 1.0 * getRotorDurabilityPercent() / 100;
        }
    }

    public TextureArea getProgressBarTexture(int index) {
        if (index == 0) {
            return GuiTextures.PROGRESS_BAR_LCE_FUEL;
        } else if (index == 1) {
            return GuiTextures.PROGRESS_BAR_TURBINE_ROTOR_SPEED;
        } else {
            return GuiTextures.PROGRESS_BAR_TURBINE_ROTOR_DURABILITY;
        }
    }

    public void addBarHoverText(List<ITextComponent> hoverList, int index) {
        IReinforcedRotorHolder rotorHolder = getRotorHolders().get(0);
        if (index == 0) {
            // Fuel
            addFuelText(hoverList);
        } else if (index == 1) {
            // Rotor speed
            {
                int rotorSpeed = rotorHolder.getRotorSpeed();
                int rotorMaxSpeed = rotorHolder.getMaxRotorHolderSpeed();
                ITextComponent rpmTranslated = TextComponentUtil.translationWithColor(
                        getRotorSpeedColor(rotorSpeed, rotorMaxSpeed),
                        "gregtech.multiblock.turbine.rotor_rpm_unit_name");
                ITextComponent rotorInfo = TextComponentUtil.translationWithColor(
                        getRotorSpeedColor(rotorSpeed, rotorMaxSpeed),
                        "%s / %s %s",
                        TextFormattingUtil.formatNumbers(rotorSpeed),
                        TextFormattingUtil.formatNumbers(rotorMaxSpeed),
                        rpmTranslated);
                hoverList.add(TextComponentUtil.translationWithColor(
                        TextFormatting.GRAY,
                        "gregtech.multiblock.turbine.rotor_speed",
                        rotorInfo));
            }
        } else {
            // Rotor durability
            {
                int rotorDurability = getRotorDurabilityPercent();
                ITextComponent rotorInfo = TextComponentUtil.stringWithColor(
                        getRotorDurabilityColor(rotorDurability),
                        rotorDurability + "%");
                hoverList.add(TextComponentUtil.translationWithColor(
                        TextFormatting.GRAY,
                        "gregtech.multiblock.turbine.rotor_durability",
                        rotorInfo));
            }
        }
    }

    int getRotorDurabilityPercent() {
        IReinforcedRotorHolder rotorHolder = getRotorHolders().get(0);
        return rotorHolder.getRotorDurabilityPercent();
    }

    private TextFormatting getRotorSpeedColor(int rotorSpeed, int maxRotorSpeed) {
        double speedRatio = 1.0 * rotorSpeed / maxRotorSpeed;
        if (speedRatio < 0.4) {
            return TextFormatting.RED;
        } else if (speedRatio < 0.8) {
            return TextFormatting.YELLOW;
        } else {
            return TextFormatting.GREEN;
        }
    }

    private TextFormatting getRotorDurabilityColor(int durability) {
        if (durability > 40) {
            return TextFormatting.GREEN;
        } else if (durability > 10) {
            return TextFormatting.YELLOW;
        } else {
            return TextFormatting.RED;
        }
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
    protected boolean shouldShowVoidingModeButton() {
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
