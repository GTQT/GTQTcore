package keqing.gtqtcore.common.metatileentities.multi.generators;

import java.util.List;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockFuelRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.*;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.FuelMultiblockController;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class MetaTileEntityTurbineCombustionChamber extends FuelMultiblockController {

    private final int tier;
    private final boolean isExtreme;
    private boolean boostAllowed;

    public MetaTileEntityTurbineCombustionChamber(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId,GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER, tier);
        this.recipeMapWorkable = new TurbineCombustionEngineWorkableHandler(this, tier > GTValues.EV);
        this.recipeMapWorkable.setMaximumOverclockVoltage(GTValues.V[tier]);
        this.tier = tier;
        this.isExtreme = tier > GTValues.EV;
    }


    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (isStructureFormed()) {
            if (getInputFluidInventory() != null) {
                FluidStack WaterStack = getInputFluidInventory().drain(Materials.Lubricant.getFluid(Integer.MAX_VALUE), false);
                FluidStack LubricantStack = getInputFluidInventory().drain(Materials.Water.getFluid(Integer.MAX_VALUE), false);
                FluidStack liquidOxygenStack = getInputFluidInventory().drain(Materials.LiquidOxygen.getFluid(Integer.MAX_VALUE), false);
                int lubricantAmount = WaterStack == null ? 0 : WaterStack.amount;
                textList.add(new TextComponentTranslation("gtqtcore.multiblock.large_combustion_engine.water_amount", TextFormattingUtil.formatNumbers(lubricantAmount)));
                if (boostAllowed) {
                    if (!isExtreme) {
                        if (((TurbineCombustionEngineWorkableHandler) recipeMapWorkable).isOxygenBoosted) {
                            int oxygenAmount = LubricantStack == null ? 0 : LubricantStack.amount;
                            textList.add(new TextComponentTranslation("gtqtcore.multiblock.large_combustion_engine.lubricant_amount", TextFormattingUtil.formatNumbers(oxygenAmount)));
                            textList.add(new TextComponentTranslation("gtqtcore.multiblock.large_combustion_engine.lubricant_boosted"));
                        } else {
                            textList.add(new TextComponentTranslation("gtqtcore.multiblock.large_combustion_engine.supply_lubricant_to_boost"));
                        }
                    }
                    else {
                        if (((TurbineCombustionEngineWorkableHandler) recipeMapWorkable).isOxygenBoosted) {
                            int liquidOxygenAmount = liquidOxygenStack == null ? 0 : liquidOxygenStack.amount;
                            textList.add(new TextComponentTranslation("gtqtcore.multiblock.large_combustion_engine.liquid_lubricant_amount", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));
                            textList.add(new TextComponentTranslation("gtqtcore.multiblock.large_combustion_engine.liquid_lubricant_boosted"));
                        } else {
                            textList.add(new TextComponentTranslation("gtqtcore.multiblock.large_combustion_engine.supply_liquid_lubricant_to_boost"));
                        }
                    }
                } else {
                    textList.add(new TextComponentTranslation("gtqtcore.multiblock.large_combustion_engine.boost_disallowed"));
                }
            }
        }
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (isStructureFormed()) {
            FluidStack lubricantStack = getInputFluidInventory().drain(Materials.Water.getFluid(Integer.MAX_VALUE), false);
            if (lubricantStack == null || lubricantStack.amount == 0) {
                textList.add(new TextComponentTranslation("gtqtcore.multiblock.large_combustion_engine.no_water"));
            }
        }
    }

    @Override
    protected void addErrorText(List<ITextComponent> textList) {
        super.addErrorText(textList);
        if (isStructureFormed() && checkIntakesObstructed()) {
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.large_combustion_engine.obstructed"));
        }
    }

    @Override
    public void addInformation(ItemStack stack, World player,  List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.universal.tooltip.base_production_eut", GTValues.V[tier]));
        tooltip.add(I18n.format("gregtech.universal.tooltip.uses_per_hour_lubricant", 1000));
        if (isExtreme) {
            tooltip.add(I18n.format("gregtech.machine.large_combustion_engine.tooltip.boost_extreme", GTValues.V[tier] * 4));
        } else {
            tooltip.add(I18n.format("gregtech.machine.large_combustion_engine.tooltip.boost_regular", GTValues.V[tier] * 3));
        }
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityTurbineCombustionChamber(metaTileEntityId, tier);
    }
    @Nonnull
    @Override
    protected  BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("MNNMMMNNM", "MNNMEINNM", "MNNMMMNNM")
                .aisle("MNNMMMNNM", "ABBBBBBBA", "MNNMMMNNM")
                .aisle("MNNAAANNM", "MNNACANNM", "MNNAAANNM")
                .where('C', selfPredicate())
                .where('M', states(getCasingState()).setMinGlobalLimited(15)
                        .or(autoAbilities(false, true, true, true, true, true, false)))
                .where('N', states(getCasingState1()))
                .where('A', states(getCasingState2()))
                .where('B', states(getCasingState3()))
                .where('E', abilities(MultiblockAbility.OUTPUT_ENERGY))
                .where('I', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('#', any())
                .build();
    }

    private static IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST);
    }

    private static IBlockState getCasingState1() {
        return MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.TUNGSTENSTEEL);
    }

    private static IBlockState getCasingState2() {
        return MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.EXTREME_ENGINE_INTAKE_CASING);
    }

    private static IBlockState getCasingState3() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE);
    }


    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return   Textures.ROBUST_TUNGSTENSTEEL_CASING ;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return  Textures.EXTREME_COMBUSTION_ENGINE_OVERLAY ;
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

    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        IEnergyContainer energyContainer = getEnergyContainer();
        this.boostAllowed = energyContainer != null && energyContainer.getOutputVoltage() >= GTValues.V[this.tier + 1];
    }
    private static class TurbineCombustionEngineWorkableHandler extends MultiblockFuelRecipeLogic {

        private boolean isOxygenBoosted = false;

        private final MetaTileEntityTurbineCombustionChamber combustionEngine;
        private final boolean isExtreme;
        private static final FluidStack OXYGEN_STACK = Materials.Lubricant.getFluid(20);
        private static final FluidStack LUBRICANT_STACK = Materials.Water.getFluid(4000);

        public TurbineCombustionEngineWorkableHandler(RecipeMapMultiblockController tileEntity, boolean isExtreme) {
            super(tileEntity);
            this.combustionEngine = (MetaTileEntityTurbineCombustionChamber) tileEntity;
            this.isExtreme = isExtreme;
        }


        @Override
        protected void updateRecipeProgress() {
            if (canRecipeProgress && drawEnergy(recipeEUt, true)) {

                //这里是蒸燃联合需要的水
                if (totalContinuousRunningTime == 1 || totalContinuousRunningTime % 72 == 0) {
                    IMultipleTankHandler inputTank = combustionEngine.getInputFluidInventory();
                    if (LUBRICANT_STACK.isFluidStackIdentical(inputTank.drain(LUBRICANT_STACK, false))) {
                        inputTank.drain(LUBRICANT_STACK, true);
                    } else {
                        invalidate();
                        return;
                    }
                }

                //这里是增产的润滑油
                if (combustionEngine.isBoostAllowed() && (totalContinuousRunningTime == 1 || totalContinuousRunningTime % 20 == 0)) {
                    IMultipleTankHandler inputTank = combustionEngine.getInputFluidInventory();
                    FluidStack boosterStack = OXYGEN_STACK;
                    if (boosterStack.isFluidStackIdentical(inputTank.drain(boosterStack, false))) {
                        isOxygenBoosted = true;
                        inputTank.drain(boosterStack, true);
                    } else {
                        isOxygenBoosted = false;
                    }
                }

                drawEnergy(recipeEUt, false);

                //as recipe starts with progress on 1 this has to be > only not => to compensate for it
                if (++progressTime > maxProgressTime) {
                    completeRecipe();
                }
            }
        }

        @Override
        protected boolean shouldSearchForRecipes() {
            return super.shouldSearchForRecipes() && LUBRICANT_STACK.isFluidStackIdentical(((RecipeMapMultiblockController) metaTileEntity).getInputFluidInventory().drain(LUBRICANT_STACK, false));
        }


        @Override
        protected long boostProduction(long production) {
            //this multiplies production without increasing consumption
            if (isOxygenBoosted)
                if (!isExtreme)
                    //recipe gives 2A EV and we want 3A EV, for 150% efficiency
                    return production * 3 / 2;
                else
                    //recipe gives 2A IV and we want 4A IV, for 200% efficiency
                    return production * 2;
            return production;
        }

        @Override
        public void invalidate() {
            isOxygenBoosted = false;
            super.invalidate();
        }
    }

    boolean isBoostAllowed() {
        return boostAllowed;
    }
}