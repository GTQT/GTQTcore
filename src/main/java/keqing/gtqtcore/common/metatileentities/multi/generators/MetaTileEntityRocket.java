package keqing.gtqtcore.common.metatileentities.multi.generators;

import java.util.Collections;
import java.util.List;

import gregicality.multiblocks.api.render.GCYMTextures;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockFuelRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.common.blocks.*;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.api.utils.GTQTUtil;
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
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public class MetaTileEntityRocket extends FuelMultiblockController  {

    protected static int heatingCoilLevel;

    private final int tier;
    private final boolean isExtreme;
    private boolean boostAllowed;

    public MetaTileEntityRocket(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId,GTQTcoreRecipeMaps.ROCKET, tier);
        this.recipeMapWorkable = new TurbineCombustionEngineWorkableHandler(this, false);
        this.recipeMapWorkable.setMaximumOverclockVoltage(GTValues.V[tier]);
        this.tier = tier;
        this.isExtreme = false;
    }


    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (isStructureFormed()) {
            if (getInputFluidInventory() != null) {
                FluidStack WaterStack = getInputFluidInventory().drain(Materials.Lubricant.getFluid(Integer.MAX_VALUE), false);
                FluidStack LubricantStack = getInputFluidInventory().drain(Materials.Water.getFluid(Integer.MAX_VALUE), false);
                FluidStack liquidOxygenStack = getInputFluidInventory().drain(Materials.Oxygen.getFluid(Integer.MAX_VALUE), false);
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
        tooltip.add(I18n.format("gregtech.universal.tooltip.base_production_eut", GTValues.V[tier]));
        tooltip.add(I18n.format("gregtech.universal.tooltip.uses_per_hour_lubricant", 1000));
        if (isExtreme) {
            tooltip.add(I18n.format("gregtech.machine.large_combustion_engine.tooltip.boost_extreme", GTValues.V[tier] * 4));
        } else {
            tooltip.add(I18n.format("gregtech.machine.large_combustion_engine.tooltip.boost_regular", GTValues.V[tier] * 3));
        }
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.boostAllowed = energyContainer != null && energyContainer.getOutputVoltage() >= GTValues.V[this.tier + 1];
        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
        } else {
            heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
        }
    }


    @Nonnull
    @Override
    protected  BlockPattern createStructurePattern() {
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
                .where('N',  heatingCoils())
                .where('A', states(getCasingState2()))
                .where('B', states(getCasingState3()))
                .where('O', abilities(MultiblockAbility.EXPORT_FLUIDS))
                .where('E', abilities(MultiblockAbility.OUTPUT_ENERGY))
                .where('I', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('#', any())
                .build();
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

    private class TurbineCombustionEngineWorkableHandler extends MultiblockFuelRecipeLogic {

        private boolean isOxygenBoosted = false;

        private final MetaTileEntityRocket combustionEngine;
        private boolean isExtreme;
        private Fluid outFluid;
        public TurbineCombustionEngineWorkableHandler(RecipeMapMultiblockController tileEntity, boolean isExtreme) {
            super(tileEntity);
            this.combustionEngine = (MetaTileEntityRocket) tileEntity;
            this.isExtreme = isExtreme;
        }
        private final FluidStack WATER_STACK = Materials.Water.getFluid(1000*getmax(heatingCoilLevel));
        private final FluidStack HOT_STACK = GTQTMaterials.HighPressureSteam.getFluid(60*getmax(heatingCoilLevel));
        private final FluidStack LUBRICANT_STACK = Materials.Lubricant.getFluid(100*getmax(heatingCoilLevel));
        private final FluidStack OXYGEN_STACK = Materials.Oxygen.getFluid(400*getmax(heatingCoilLevel));
        public int getmax(int heatingCoilLevel)
        {
            if(heatingCoilLevel <= 5)return heatingCoilLevel;
            if(heatingCoilLevel<10)return 6;
            if(heatingCoilLevel<16)return 7;
            if(heatingCoilLevel<25)return 8;
            return 1;
        }

        public boolean fillTanks(FluidStack stack, boolean simulate) {
            return GTTransferUtils.addFluidsToFluidHandler(outputFluidInventory, simulate, Collections.singletonList(stack));
        }

        @Override
        public long getMaxVoltage() {
            //this multiplies consumption through parallel
            if (isOxygenBoosted)
                return GTValues.V[getmax(heatingCoilLevel)+1] ;
            else
                return GTValues.V[getmax(heatingCoilLevel)];
        }
        @Override
        protected void updateRecipeProgress() {
            if (canRecipeProgress && drawEnergy(recipeEUt, true)) {

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
                }

                drawEnergy(recipeEUt, false);
                if (++progressTime > maxProgressTime) {
                    fillTanks(HOT_STACK,false);
                    completeRecipe();
                }
            }
        }
        @Override
        protected long boostProduction(long production) {
            if (isOxygenBoosted)
                return production * 3/2;
            return production;
        }
        @Override
        public void invalidate() {
            isOxygenBoosted = false;
            super.invalidate();
        }
    }

}