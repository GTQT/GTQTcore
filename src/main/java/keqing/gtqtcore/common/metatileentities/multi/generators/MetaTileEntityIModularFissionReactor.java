package keqing.gtqtcore.common.metatileentities.multi.generators;

import gregicality.multiblocks.api.render.GCYMTextures;
import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockFuelRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.FuelMultiblockController;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.List;

public class MetaTileEntityIModularFissionReactor extends FuelMultiblockController {

    private final int tier;
    private final boolean isExtreme;
    private boolean boostAllowed;

    public MetaTileEntityIModularFissionReactor(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId,GTQTcoreRecipeMaps.I_MODULAR_FISSION_REACTOR, tier);
        this.recipeMapWorkable = new TurbineCombustionEngineWorkableHandler(this, tier > GTValues.EV);
        this.recipeMapWorkable.setMaximumOverclockVoltage(GTValues.V[tier]);
        this.tier = tier;
        this.isExtreme = tier > GTValues.EV;
    }


    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (isStructureFormed()) {
            if (getInputFluidInventory() != null) {
                FluidStack lubricantStack = getInputFluidInventory().drain(Materials.Lubricant.getFluid(Integer.MAX_VALUE), false);
                FluidStack oxygenStack = getInputFluidInventory().drain(Materials.Oxygen.getFluid(Integer.MAX_VALUE), false);
                FluidStack liquidOxygenStack = getInputFluidInventory().drain(Materials.LiquidOxygen.getFluid(Integer.MAX_VALUE), false);
                int lubricantAmount = lubricantStack == null ? 0 : lubricantStack.amount;
                textList.add(new TextComponentTranslation("gregtech.multiblock.large_combustion_engine.lubricant_amount", TextFormattingUtil.formatNumbers(lubricantAmount)));
                if (boostAllowed) {
                    if (!isExtreme) {
                        if (((TurbineCombustionEngineWorkableHandler) recipeMapWorkable).isOxygenBoosted) {
                            int oxygenAmount = oxygenStack == null ? 0 : oxygenStack.amount;
                            textList.add(new TextComponentTranslation("gregtech.multiblock.large_combustion_engine.oxygen_amount", TextFormattingUtil.formatNumbers(oxygenAmount)));
                            textList.add(new TextComponentTranslation("gregtech.multiblock.large_combustion_engine.oxygen_boosted"));
                        } else {
                            textList.add(new TextComponentTranslation("gregtech.multiblock.large_combustion_engine.supply_oxygen_to_boost"));
                        }
                    }
                    else {
                        if (((TurbineCombustionEngineWorkableHandler) recipeMapWorkable).isOxygenBoosted) {
                            int liquidOxygenAmount = liquidOxygenStack == null ? 0 : liquidOxygenStack.amount;
                            textList.add(new TextComponentTranslation("gregtech.multiblock.large_combustion_engine.liquid_oxygen_amount", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));
                            textList.add(new TextComponentTranslation("gregtech.multiblock.large_combustion_engine.liquid_oxygen_boosted"));
                        } else {
                            textList.add(new TextComponentTranslation("gregtech.multiblock.large_combustion_engine.supply_liquid_oxygen_to_boost"));
                        }
                    }
                } else {
                    textList.add(new TextComponentTranslation("gregtech.multiblock.large_combustion_engine.boost_disallowed"));
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
        return new MetaTileEntityIModularFissionReactor(metaTileEntityId, tier);
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

    @Nonnull
    @Override
    protected  BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("MMMMMMM","MMMMMMM","MMMMMMM","MMMMMMM","MMMMMMM","MMMMMMM","MMMMMMM")
                .aisle("MMMMMMM","MC   CM","MC   CM","MC   CM","MC   CM","MC   CM","MMMMMMM")
                .aisle("MMMMMMM","M     M","M ABA M","M ABA M","M ABA M","M     M","MMMMMMM")
                .aisle("MMMMMMM","M     M","M BAB M","M BAB M","M BAB M","M     M","MMMMMMM")
                .aisle("MMMMMMM","M     M","M ABA M","M ABA M","M ABA M","M     M","MMMMMMM")
                .aisle("MMMMMMM","MC   CM","MC   CM","MC   CM","MC   CM","MC   CM","MMMMMMM")
                .aisle("MMEFIMM","MMMMMMM","MMMMMMM","MMMMMMM","MMMMMMM","MMMMMMM","MMMMMMM")
                .where('F', selfPredicate())
                .where('M', states(getCasingState()).setMinGlobalLimited(35)
                        .or(autoAbilities(false, true, true, true, true, true, false)))
                .where('E', abilities(MultiblockAbility.OUTPUT_ENERGY))
                .where('I', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where(' ', any())
                .where('C', states(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TITANIUM_PIPE)))
                .where('B', states(MetaBlocks.FRAMES.get(Materials.TungstenSteel).getBlock(Materials.TungstenSteel)))
                .where('A', states(MetaBlocks.FRAMES.get(Materials.HSSE).getBlock(Materials.HSSE)))
                .build();
    }
    private static IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST);
    }
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.ROBUST_TUNGSTENSTEEL_CASING;
    }
    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return GCYMTextures.ALLOY_BLAST_SMELTER_OVERLAY;
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

        private final MetaTileEntityIModularFissionReactor combustionEngine;
        private final boolean isExtreme;
        private final int tier;
        private static final FluidStack OXYGEN_STACK = Materials.Oxygen.getFluid(20);
        private static final FluidStack LIQUID_OXYGEN_STACK = Materials.LiquidOxygen.getFluid(80);
        private static final FluidStack LUBRICANT_STACK = Materials.Lubricant.getFluid(1);

        public TurbineCombustionEngineWorkableHandler(RecipeMapMultiblockController tileEntity, boolean isExtreme) {
            super(tileEntity);
            this.combustionEngine = (MetaTileEntityIModularFissionReactor) tileEntity;
            this.isExtreme = isExtreme;
            this.tier = isExtreme ? GTValues.IV : GTValues.EV;
        }


        @Override
        protected void updateRecipeProgress() {
            if (canRecipeProgress && drawEnergy(recipeEUt, true)) {

                //drain lubricant and invalidate if it fails
                if (totalContinuousRunningTime == 1 || totalContinuousRunningTime % 72 == 0) {
                    IMultipleTankHandler inputTank = combustionEngine.getInputFluidInventory();
                    if (LUBRICANT_STACK.isFluidStackIdentical(inputTank.drain(LUBRICANT_STACK, false))) {
                        inputTank.drain(LUBRICANT_STACK, true);
                    } else {
                        invalidate();
                        return;
                    }
                }

                //drain oxygen if present to boost production, and if the dynamo hatch supports it
                if (combustionEngine.isBoostAllowed() && (totalContinuousRunningTime == 1 || totalContinuousRunningTime % 20 == 0)) {
                    IMultipleTankHandler inputTank = combustionEngine.getInputFluidInventory();
                    FluidStack boosterStack = isExtreme ? LIQUID_OXYGEN_STACK : OXYGEN_STACK;
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
        protected long getMaxVoltage() {
            //this multiplies consumption through parallel
            if (isOxygenBoosted)
                return GTValues.V[tier] * 2;
            else
                return GTValues.V[tier];
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