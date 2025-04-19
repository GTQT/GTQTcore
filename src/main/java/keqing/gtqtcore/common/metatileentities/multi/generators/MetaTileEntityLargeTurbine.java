package keqing.gtqtcore.common.metatileentities.multi.generators;

import gregtech.api.GTValues;
import gregtech.api.capability.IRotorHolder;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.MultiblockFuelRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.metatileentity.ITieredMetaTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import keqing.gtqtcore.api.capability.IReinforcedRotorHolder;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static keqing.gtqtcore.api.utils.GTQTUtil.formatNumbers;


public abstract class MetaTileEntityLargeTurbine extends FuelMultiblockController implements ITieredMetaTileEntity, IProgressBarMultiblock {

    public final int tier;
    public IFluidHandler exportFluidHandler;

    public MetaTileEntityLargeTurbine(ResourceLocation metaTileEntityId,
                                      RecipeMap<?> recipeMap,
                                      int tier) {
        super(metaTileEntityId, recipeMap, tier);
        this.tier = tier;
        this.recipeMapWorkable = new LargeTurbineWorkableHandler(this, tier);
        this.recipeMapWorkable.setMaximumOverclockVoltage(GTValues.V[tier]);
    }

    public IReinforcedRotorHolder getRotorHolder() {
        List<IReinforcedRotorHolder> abilities = getAbilities(GTQTMultiblockAbility.REINFORCED_ROTOR_HOLDER_ABILITY);
        return abilities.isEmpty() ? null : abilities.get(0);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.exportFluidHandler = null;
    }

    /**
     * @return true if turbine is formed, and it's face is free and contains
     * only air blocks in front of rotor holder
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isRotorFaceFree() {
        IReinforcedRotorHolder rotorHolder = getRotorHolder();
        if (rotorHolder != null)
            return isStructureFormed() && getRotorHolder().isFrontFaceFree();
        return false;
    }
    @Override
    public void onRemoval() {
        super.onRemoval();
        if(getRotorHolder()!=null)getRotorHolder().setCurrentSpeed(0);
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.exportFluidHandler = new FluidTankList(true, getAbilities(MultiblockAbility.EXPORT_FLUIDS));
        ((LargeTurbineWorkableHandler) this.recipeMapWorkable).updateTanks();
    }

    @Override
    protected long getMaxVoltage() {
        long maxProduction = recipeMapWorkable.getMaxVoltage();
        long currentProduction = ((LargeTurbineWorkableHandler) recipeMapWorkable).boostProduction((int) maxProduction);
        if (isActive() && currentProduction < maxProduction) {
            return recipeMapWorkable.getMaxVoltage();
        } else {
            return 0L;
        }
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockFuelRecipeLogic recipeLogic = (MultiblockFuelRecipeLogic) this.recipeMapWorkable;
        MultiblockDisplayText.builder(textList, this.isStructureFormed())
                .setWorkingStatus(recipeLogic.isWorkingEnabled(), recipeLogic.isActive())
                .addEnergyProductionLine(this.getMaxVoltage(), recipeLogic.getRecipeEUt())
                .addCustom((tl) -> {
                    if (this.isStructureFormed()) {
                        IRotorHolder rotorHolder = this.getRotorHolder();
                        if (rotorHolder.getRotorEfficiency() > 0) {
                            ITextComponent efficiencyInfo = TextComponentUtil.stringWithColor(
                                    TextFormatting.AQUA,
                                    formatNumbers(rotorHolder.getTotalEfficiency()) + "%");
                            tl.add(TextComponentUtil.translationWithColor(
                                    TextFormatting.GRAY,
                                    "gregtech.multiblock.turbine.efficiency",
                                    efficiencyInfo));
                        }
                    }})
                .addFuelNeededLine(recipeLogic.getRecipeFluidInputInfo(), recipeLogic.getPreviousRecipeDuration())
                .addWorkingStatusLine();
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, this.isStructureFormed(), false)
                .addCustom((tl) -> {
                    if (this.isStructureFormed()) {
                        IRotorHolder rotorHolder = this.getRotorHolder();
                        if (rotorHolder.getRotorEfficiency() > 0 && rotorHolder.getRotorDurabilityPercent() <= 10) {
                            tl.add(TextComponentUtil.translationWithColor(
                                    TextFormatting.YELLOW,
                                    "gregtech.multiblock.turbine.rotor_durability_low"));
                        }
                    }})
                .addLowDynamoTierLine(this.isDynamoTierTooLow())
                .addMaintenanceProblemLines(this.getMaintenanceProblems());
    }

    @Override
    protected void addErrorText(List<ITextComponent> textList) {
        super.addErrorText(textList);
        if (this.isStructureFormed()) {
            if (!this.isRotorFaceFree()) {
                textList.add(TextComponentUtil.translationWithColor(
                        TextFormatting.RED,
                        "gregtech.multiblock.turbine.obstructed"));
                textList.add(TextComponentUtil.translationWithColor(
                        TextFormatting.GRAY,
                        "gregtech.multiblock.turbine.obstructed.desc"));
            }

            IRotorHolder rotorHolder = this.getRotorHolder();
            if (rotorHolder.getRotorEfficiency() <= 0) {
                textList.add(TextComponentUtil.translationWithColor(
                        TextFormatting.RED,
                        "gregtech.multiblock.turbine.no_rotor"));
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack,
                               World player,
                               List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.universal.tooltip.base_production_eut", GTValues.V[tier] * 2));
        tooltip.add(I18n.format("gregtech.multiblock.turbine.efficiency_tooltip", GTValues.VNF[tier]));
    }

    @Override
    protected abstract BlockPattern createStructurePattern();

    @Override
    public String[] getDescription() {
        return new String[]{I18n.format("gregtech.multiblock.large_turbine.description")};
    }

    @SideOnly(Side.CLIENT)
    @Override
    public abstract ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart);

    @SideOnly(Side.CLIENT)
    @Override
    protected abstract ICubeRenderer getFrontOverlay();

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public boolean isStructureObstructed() {
        return super.isStructureObstructed() || !isRotorFaceFree();
    }

    @Override
    public int getTier() {
        return tier;
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
    public int getNumProgressBars() {
        return 3;
    }

    @Override
    public double getFillPercentage(int index) {
        if (index == 0) {
            int[] fuelAmount = new int[2];
            if (getInputFluidInventory() != null) {
                MultiblockFuelRecipeLogic recipeLogic = (MultiblockFuelRecipeLogic) this.recipeMapWorkable;
                if (recipeLogic.getInputFluidStack() != null) {
                    FluidStack fluidStack = recipeLogic.getInputFluidStack().copy();
                    fluidStack.amount = Integer.MAX_VALUE;
                    fuelAmount = getTotalFluidAmount(fluidStack, getInputFluidInventory());
                }
            }
            return fuelAmount[1] != 0 ? 1.0 * fuelAmount[0] / (double) fuelAmount[1] : 0.0;
        } else {
            IRotorHolder rotorHolder;
            if (index == 1) {
                rotorHolder = this.getRotorHolder();
                return rotorHolder != null ? 1.0 * rotorHolder.getRotorSpeed() / (double) rotorHolder.getMaxRotorHolderSpeed() : 0.0;
            } else {
                rotorHolder = this.getRotorHolder();
                return rotorHolder != null ? 1.0 * rotorHolder.getRotorDurabilityPercent() / 100.0 : 0.0;
            }
        }
    }

    @Override
    public TextureArea getProgressBarTexture(int index) {
        if (index == 0) {
            return GuiTextures.PROGRESS_BAR_LCE_FUEL;
        } else {
            return index == 1 ? GuiTextures.PROGRESS_BAR_TURBINE_ROTOR_SPEED : GuiTextures.PROGRESS_BAR_TURBINE_ROTOR_DURABILITY;
        }
    }

    @Override
    public void addBarHoverText(List<ITextComponent> hoverList, int index) {
        if (index == 0) {
            this.addFuelText(hoverList);
        } else {
            IRotorHolder rotorHolder;
            int rotorSpeed;
            if (index == 1) {
                rotorHolder = this.getRotorHolder();
                if (rotorHolder != null && rotorHolder.getRotorEfficiency() > 0) {
                    rotorSpeed = rotorHolder.getRotorSpeed();
                    int rotorMaxSpeed = rotorHolder.getMaxRotorHolderSpeed();
                    ITextComponent rpmTranslated = TextComponentUtil.translationWithColor(this.getRotorSpeedColor(rotorSpeed, rotorMaxSpeed), "gregtech.multiblock.turbine.rotor_rpm_unit_name");
                    ITextComponent rotorInfo = TextComponentUtil.translationWithColor(this.getRotorSpeedColor(rotorSpeed, rotorMaxSpeed), "%s / %s %s", new Object[]{TextFormattingUtil.formatNumbers(rotorSpeed), TextFormattingUtil.formatNumbers(rotorMaxSpeed), rpmTranslated});
                    hoverList.add(TextComponentUtil.translationWithColor(
                            TextFormatting.GRAY,
                            "gregtech.multiblock.turbine.rotor_speed",
                            rotorInfo));
                } else {
                    hoverList.add(TextComponentUtil.translationWithColor(
                            TextFormatting.YELLOW,
                            "gregtech.multiblock.turbine.no_rotor"));
                }
            } else {
                rotorHolder = this.getRotorHolder();
                if (rotorHolder != null && rotorHolder.getRotorEfficiency() > 0) {
                    rotorSpeed = rotorHolder.getRotorDurabilityPercent();
                    ITextComponent rotorInfo = TextComponentUtil.stringWithColor(this.getRotorDurabilityColor(rotorSpeed), rotorSpeed + "%");
                    hoverList.add(TextComponentUtil.translationWithColor(
                            TextFormatting.GRAY,
                            "gregtech.multiblock.turbine.rotor_durability",
                            rotorInfo));
                } else {
                    hoverList.add(TextComponentUtil.translationWithColor(
                            TextFormatting.YELLOW,
                            "gregtech.multiblock.turbine.no_rotor"));
                }
            }
        }
    }

    private TextFormatting getRotorDurabilityColor(int durability) {
        if (durability > 40) {
            return TextFormatting.GREEN;
        } else {
            return durability > 10 ? TextFormatting.YELLOW : TextFormatting.RED;
        }
    }

    private TextFormatting getRotorSpeedColor(int rotorSpeed, int maxRotorSpeed) {
        double speedRatio = 1.0 * rotorSpeed / (double)maxRotorSpeed;
        if (speedRatio < 0.4) {
            return TextFormatting.RED;
        } else {
            return speedRatio < 0.8 ? TextFormatting.YELLOW : TextFormatting.GREEN;
        }
    }
}