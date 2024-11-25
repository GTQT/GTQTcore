package keqing.gtqtcore.common.metatileentities.multi.generators;

import gregtech.api.GTValues;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockFuelRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.metatileentity.ITieredMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMultiblockCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTMultiblockCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

import static gregtech.api.unification.material.Materials.Oxygen;

public class MetaTileEntityRocket extends FuelMultiblockController implements ITieredMetaTileEntity, IProgressBarMultiblock {

    protected static int heatingCoilLevel;

    private final int tier;
    private final boolean isExtreme;

    int speed;
    int Durability = 100;
    int add;

    public MetaTileEntityRocket(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.ROCKET, tier);
        this.recipeMapWorkable = new TurbineCombustionEngineWorkableHandler(this);
        this.tier = tier;
        this.isExtreme = false;
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.MULTI_CASING.getState(GTQTMultiblockCasing.CasingType.NITINOL_MACHINE_CASING);
    }

    private static IBlockState getCasingState2() {
        return MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.EXTREME_ENGINE_INTAKE_CASING);
    }

    private static IBlockState getCasingState3() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE);
    }

    public int getTier() {
        return tier;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (isStructureFormed()) {
            if (getInputFluidInventory() != null) {
                FluidStack WaterStack = getInputFluidInventory().drain(Materials.Lubricant.getFluid(Integer.MAX_VALUE), false);
                FluidStack LubricantStack = getInputFluidInventory().drain(Materials.Water.getFluid(Integer.MAX_VALUE), false);
                FluidStack liquidOxygenStack = getInputFluidInventory().drain(Oxygen.getFluid(Integer.MAX_VALUE), false);
                int lubricantAmount = WaterStack == null ? 0 : WaterStack.amount;
                textList.add(new TextComponentTranslation("gtqtcore.multiblock.large_combustion_engine.water_amount", TextFormattingUtil.formatNumbers(lubricantAmount)));

                if (!isExtreme) {
                    if (((TurbineCombustionEngineWorkableHandler) recipeMapWorkable).isOxygenBoosted) {
                        int oxygenAmount = LubricantStack == null ? 0 : LubricantStack.amount;
                        textList.add(new TextComponentTranslation("gtqtcore.multiblock.large_combustion_engine.lubricant_amount", TextFormattingUtil.formatNumbers(oxygenAmount)));
                        textList.add(new TextComponentTranslation("gtqtcore.multiblock.large_combustion_engine.lubricant_boosted"));
                    } else {
                        textList.add(new TextComponentTranslation("gtqtcore.multiblock.large_combustion_engine.supply_lubricant_to_boost"));
                    }
                } else {
                    if (((TurbineCombustionEngineWorkableHandler) recipeMapWorkable).isOxygenBoosted) {
                        int liquidOxygenAmount = liquidOxygenStack == null ? 0 : liquidOxygenStack.amount;
                        textList.add(new TextComponentTranslation("gtqtcore.multiblock.large_combustion_engine.liquid_lubricant_amount", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));
                        textList.add(new TextComponentTranslation("gtqtcore.multiblock.large_combustion_engine.liquid_lubricant_boosted"));
                    } else {
                        textList.add(new TextComponentTranslation("gtqtcore.multiblock.large_combustion_engine.supply_liquid_lubricant_to_boost"));
                    }
                }

            }
        }
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (isStructureFormed()) {
            FluidStack lubricantStack = getInputFluidInventory().drain(Materials.Lubricant.getFluid(Integer.MAX_VALUE), false);
            if (lubricantStack == null || lubricantStack.amount == 0) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.large_combustion_engine.no_lubricant"));
            }
        }
    }

    @Override
    protected void addErrorText(List<ITextComponent> textList) {
        super.addErrorText(textList);
        if (isStructureFormed() && checkIntakesObstructed()) {
            textList.add(new TextComponentTranslation("gregtech.multiblock.large_combustion_engine.obstructed"));
        }
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityRocket(metaTileEntityId, tier);
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gtqtcore.rocket.tooltip.1", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.rocket.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.rocket.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.rocket.tooltip.4"));
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);

        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
        } else {
            heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
        }
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("MMM", "MEM", "MMM")
                .aisle("MMM", "MBM", "MIM")
                .aisle("MMM", "MBM", "MIM")
                .aisle("MMM", "MBM", "MIM")
                .aisle("MMM", "MBM", "MIM")
                .aisle("MMM", "MBO", "MIM")
                .aisle("NNN", "NBN", "NNN")
                .aisle("NNN", "NBN", "NNN")
                .aisle("AAA", "ACA", "AAA")
                .where('C', selfPredicate())
                .where('M', states(getCasingState()).setMinGlobalLimited(35)
                        .or(autoAbilities(false, true, true, true, true, true, false)))
                .where('N', heatingCoils())
                .where('A', states(getCasingState2()))
                .where('B', states(getCasingState3()))
                .where('O', abilities(MultiblockAbility.EXPORT_FLUIDS))
                .where('E', abilities(MultiblockAbility.OUTPUT_ENERGY))
                .where('I', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('#', any())
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.NITINOL_CASING;
    }

    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.POWER_SUBSTATION_OVERLAY;
    }


    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    protected boolean shouldShowVoidingModeButton() {
        return false;
    }


    private boolean checkIntakesObstructed() {
        EnumFacing facing = this.getFrontFacing();
        boolean permuteXZ = facing.getAxis() == EnumFacing.Axis.Z;
        BlockPos centerPos = this.getPos().offset(facing);
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                //Skip the controller block itself
                if (x == 0 && y == 0)
                    continue;
                BlockPos blockPos = centerPos.add(permuteXZ ? x : 0, y, permuteXZ ? 0 : x);
                IBlockState blockState = this.getWorld().getBlockState(blockPos);
                if (!blockState.getBlock().isAir(blockState, this.getWorld(), blockPos))
                    return true;
            }
        }
        return false;
    }

    public int getNumProgressBars() {
        return 3;
    }

    public double getFillPercentage(int index) {
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
            return 1.0 * getRotorSpeed() / getMaxRotorHolderSpeed();
        } else {
            return 1.0 * Durability / 100;
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
        if (index == 0) {
            // Fuel
            addFuelText(hoverList);
        } else if (index == 1) {
            // Rotor speed
            {
                int rotorSpeed = getRotorSpeed();
                int rotorMaxSpeed = getMaxRotorHolderSpeed();
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

    private TextFormatting getRotorDurabilityColor(int durability) {
        if (durability > 40) {
            return TextFormatting.GREEN;
        } else if (durability > 10) {
            return TextFormatting.YELLOW;
        } else {
            return TextFormatting.RED;
        }
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

    public int getMax(int heatingCoilLevel) {
        if (heatingCoilLevel == 1) return 3 + add;
        if (heatingCoilLevel == 2) return 4 + add;
        if (heatingCoilLevel == 4) return 5 + add;
        if (heatingCoilLevel == 8) return 6 + add;
        return 1;
    }

    int getRotorSpeed() {
        return speed;
    }

    int getRotorDurabilityPercent() {
        return Durability;
    }

    int getMaxRotorHolderSpeed() {
        return 3600 * getMax(heatingCoilLevel);
    }

    private class TurbineCombustionEngineWorkableHandler extends MultiblockFuelRecipeLogic {

        private final MetaTileEntityRocket combustionEngine;
        private final FluidStack WATER_STACK = Materials.Water.getFluid(512 * getMax(heatingCoilLevel));
        private final FluidStack LUBRICANT_STACK = Materials.Lubricant.getFluid(10 * getMax(heatingCoilLevel));
        private final FluidStack OXYGEN_STACK = Oxygen.getFluid(40 * getMax(heatingCoilLevel));
        private final FluidStack HOT_STACK = GTQTMaterials.SupercriticalSteam.getFluid(640 * 4 * getMax(heatingCoilLevel));
        private boolean isOxygenBoosted = false;
        public TurbineCombustionEngineWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
            this.combustionEngine = (MetaTileEntityRocket) tileEntity;
        }

        public void update() {
            super.update();
            if (speed > 1) speed--;
            if (speed >= 5000) speed--;
            if (speed >= 10000) speed--;
            if (speed >= 15000) speed--;

        }

        public void fillTanks(FluidStack stack, boolean simulate) {
            GTTransferUtils.addFluidsToFluidHandler(outputFluidInventory, simulate, Collections.singletonList(stack));
        }

        @Override
        public long getMaxVoltage() {
            if (isOxygenBoosted)
                return GTValues.V[getMax(heatingCoilLevel)] * 15 / 8;
            else
                return GTValues.V[getMax(heatingCoilLevel)] * 3 / 2;
        }

        @Override
        protected void updateRecipeProgress() {
            if (canRecipeProgress) {

                //这里是蒸燃联合需要的水
                IMultipleTankHandler inputTank = combustionEngine.getInputFluidInventory();
                if (WATER_STACK.isFluidStackIdentical(inputTank.drain(WATER_STACK, false))) {
                    inputTank.drain(WATER_STACK, true);
                } else {
                    return;
                }

                //这里是增产的润滑油
                if (LUBRICANT_STACK.isFluidStackIdentical(inputTank.drain(LUBRICANT_STACK, false))) {
                    isOxygenBoosted = true;
                    inputTank.drain(LUBRICANT_STACK, true);
                } else {
                    isOxygenBoosted = false;
                }

                //这里是增产的氧气
                if (OXYGEN_STACK.isFluidStackIdentical(inputTank.drain(OXYGEN_STACK, false))) {
                    inputTank.drain(OXYGEN_STACK, true);
                    add = 1;
                } else add = 0;

                drawEnergy(recipeEUt, false);
                fillTanks(HOT_STACK, false);
                if (speed < getMaxRotorHolderSpeed()) speed = speed + 10;
                if (++progressTime > maxProgressTime) {
                    completeRecipe();
                }
            }
        }

        @Override
        protected long boostProduction(long production) {
            if (isOxygenBoosted)
                return production * 3 / 2 * speed / getMaxRotorHolderSpeed();
            return production * speed / getMaxRotorHolderSpeed();
        }

        @Override
        public void invalidate() {
            isOxygenBoosted = false;
            super.invalidate();
        }
    }

}