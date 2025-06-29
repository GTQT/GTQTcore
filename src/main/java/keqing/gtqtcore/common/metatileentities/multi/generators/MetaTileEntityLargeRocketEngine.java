package keqing.gtqtcore.common.metatileentities.multi.generators;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockFuelRecipeLogic;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.RelativeDirection;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

import static keqing.gtqtcore.common.block.blocks.BlockActiveUniqueCasing1.ActiveCasingType.HG1223_INTAKE_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockActiveUniqueCasing1.ActiveCasingType.NITINOL_INTAKE_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3.CasingType.*;

public class MetaTileEntityLargeRocketEngine extends FuelMultiblockController implements IProgressBarMultiblock {

    private final int tier;
    private final boolean isExtreme;
    private boolean boostAllowed;

    public MetaTileEntityLargeRocketEngine(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.ROCKET_RECIPES, tier);
        this.recipeMapWorkable = new LargeRocketEngineWorkableHandler(this, tier > GTValues.LuV);
        this.recipeMapWorkable.setMaximumOverclockVoltage(GTValues.V[tier]);
        this.tier = tier;
        this.isExtreme = tier > GTValues.LuV;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLargeRocketEngine(metaTileEntityId, tier);
    }
    @Override
    public boolean usesMui2() {
        return false;
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        LargeRocketEngineWorkableHandler recipeLogic = ((LargeRocketEngineWorkableHandler) recipeMapWorkable);

        MultiblockDisplayText.Builder builder = MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(recipeLogic.isWorkingEnabled(), recipeLogic.isActive());

        if (isExtreme) {
            builder.addEnergyProductionLine(GTValues.V[tier + 1], recipeLogic.getRecipeEUt());
        } else {
            builder.addEnergyProductionAmpsLine(GTValues.V[tier] * 3, 3);
        }

        builder.addFuelNeededLine(recipeLogic.getRecipeFluidInputInfo(), recipeLogic.getPreviousRecipeDuration())
                .addCustom(tl -> {
                    if (isStructureFormed() && recipeLogic.isOxygenBoosted) {
                        String key = isExtreme ? "gregtech.multiblock.large_combustion_engine.liquid_oxygen_boosted" :
                                "gregtech.multiblock.large_combustion_engine.oxygen_boosted";
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.AQUA, key));
                    }
                })
                .addWorkingStatusLine();
    }

    @Override
    protected void addErrorText(List<ITextComponent> textList) {
        super.addErrorText(textList);
        if (isStructureFormed()) {
            if (checkIntakesObstructed()) {
                textList.add(TextComponentUtil.translationWithColor(TextFormatting.RED,
                        "gregtech.multiblock.large_combustion_engine.obstructed"));
                textList.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY,
                        "gregtech.multiblock.large_combustion_engine.obstructed.desc"));
            }

            FluidStack lubricantStack = getInputFluidInventory().drain(Materials.Lubricant.getFluid(Integer.MAX_VALUE),
                    false);
            if (lubricantStack == null || lubricantStack.amount == 0) {
                textList.add(TextComponentUtil.translationWithColor(TextFormatting.RED,
                        "gregtech.multiblock.large_combustion_engine.no_lubricant"));
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.universal.tooltip.base_production_eut", GTValues.V[tier]));
        tooltip.add(I18n.format("gregtech.universal.tooltip.uses_per_hour_lubricant", 1000));
        if (isExtreme) {
            tooltip.add(I18n.format("gregtech.machine.large_combustion_engine.tooltip.boost_extreme",
                    GTValues.V[tier] * 4));
        } else {
            tooltip.add(I18n.format("gregtech.machine.large_combustion_engine.tooltip.boost_regular",
                    GTValues.V[tier] * 3));
        }
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XDX", "XXX")
                .aisle("XCX", "CGC", "XoX")
                .aisle("XCX", "CGC", "XoX")
                .aisle("XCX", "CGC", "XoX")
                .aisle("XCX", "CGC", "XoX")
                .aisle("XCX", "CGC", "XoX")
                .aisle("XCX", "CGC", "XoX")
                .aisle("XCX", "CGC", "XoX")
                .aisle("XCX", "CGC", "XoX")
                .aisle("AAA", "AYA", "AAA")
                .where('X', states(getCasingState()))
                .where('G', states(getGearboxState()))
                .where('o', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('C',
                        states(getCasingState()).setMinGlobalLimited(18)
                                .or(autoAbilities(false, true, true, true, true, true, false)))
                .where('D', metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbility.OUTPUT_ENERGY).stream()
                        .filter(mte -> {
                            IEnergyContainer container = mte
                                    .getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, null);
                            return container != null &&
                                    container.getOutputVoltage() * container.getOutputAmperage() >= GTValues.V[tier];
                        })
                        .toArray(MetaTileEntity[]::new))
                        .addTooltip("gregtech.multiblock.pattern.error.limited.1", GTValues.VN[tier]))
                .where('A', states(getIntakeState()).addTooltips("gregtech.multiblock.pattern.clear_amount_1"))
                .where('Y', selfPredicate())
                .build();
    }

    public IBlockState getCasingState() {
        return isExtreme ? GTQTMetaBlocks.blockMultiblockCasing3.getState(HC_ALLOY_CASING) :
                GTQTMetaBlocks.blockMultiblockCasing3.getState(NITINOL_MACHINE_CASING);
    }

    public IBlockState getGearboxState() {
        return isExtreme ? GTQTMetaBlocks.blockMultiblockCasing3.getState(HG1223_GEARBOX) :
                GTQTMetaBlocks.blockMultiblockCasing3.getState(NITINOL_GEARBOX);
    }

    public IBlockState getIntakeState() {
        return isExtreme ? GTQTMetaBlocks.blockActiveUniqueCasing1.getState(HG1223_INTAKE_CASING) :
                GTQTMetaBlocks.blockActiveUniqueCasing1.getState(NITINOL_INTAKE_CASING);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return isExtreme ? GTQTTextures.HC_ALLOY_CASING : GTQTTextures.NITINOL_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    public boolean isStructureObstructed() {
        return super.isStructureObstructed() || checkIntakesObstructed();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        IEnergyContainer energyContainer = getEnergyContainer();
        this.boostAllowed = energyContainer != null && energyContainer.getOutputVoltage() >= GTValues.V[this.tier + 1];
    }

    private boolean checkIntakesObstructed() {
        for (int left = -1; left <= 1; left++) {
            for (int up = -1; up <= 1; up++) {
                if (left == 0 && up == 0) {
                    // Skip the controller block itself
                    continue;
                }

                final BlockPos checkPos = RelativeDirection.offsetPos(
                        getPos(), getFrontFacing(), getUpwardsFacing(), isFlipped(), up, left, 1);
                final IBlockState state = getWorld().getBlockState(checkPos);
                if (!state.getBlock().isAir(state, getWorld(), checkPos)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean shouldShowVoidingModeButton() {
        return false;
    }

    public boolean isBoostAllowed() {
        return boostAllowed;
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
                MultiblockFuelRecipeLogic recipeLogic = (MultiblockFuelRecipeLogic) recipeMapWorkable;
                if (recipeLogic.getInputFluidStack() != null) {
                    FluidStack testStack = recipeLogic.getInputFluidStack().copy();
                    testStack.amount = Integer.MAX_VALUE;
                    fuelAmount = getTotalFluidAmount(testStack, getInputFluidInventory());
                }
            }
            return fuelAmount[1] != 0 ? 1.0 * fuelAmount[0] / fuelAmount[1] : 0;
        } else if (index == 1) {
            int[] lubricantAmount = new int[2];
            if (getInputFluidInventory() != null) {
                lubricantAmount = getTotalFluidAmount(Materials.Lubricant.getFluid(Integer.MAX_VALUE),
                        getInputFluidInventory());
            }
            return lubricantAmount[1] != 0 ? 1.0 * lubricantAmount[0] / lubricantAmount[1] : 0;
        } else {
            int[] oxygenAmount = new int[2];
            if (getInputFluidInventory() != null) {
                if (isBoostAllowed()) {
                    FluidStack oxygenStack = isExtreme ?
                            Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, Integer.MAX_VALUE) :
                            Materials.Oxygen.getFluid(Integer.MAX_VALUE);
                    oxygenAmount = getTotalFluidAmount(oxygenStack, getInputFluidInventory());
                }
            }
            return oxygenAmount[1] != 0 ? 1.0 * oxygenAmount[0] / oxygenAmount[1] : 0;
        }
    }

    @Override
    public TextureArea getProgressBarTexture(int index) {
        if (index == 0) {
            return GuiTextures.PROGRESS_BAR_LCE_FUEL;
        } else if (index == 1) {
            return GuiTextures.PROGRESS_BAR_LCE_LUBRICANT;
        } else {
            return GuiTextures.PROGRESS_BAR_LCE_OXYGEN;
        }
    }

    @Override
    public void addBarHoverText(List<ITextComponent> hoverList, int index) {
        if (index == 0) {
            addFuelText(hoverList);
        } else if (index == 1) {
            // Lubricant
            int lubricantStored = 0;
            int lubricantCapacity = 0;
            if (isStructureFormed() && getInputFluidInventory() != null) {
                // Hunt for tanks with lubricant in them
                int[] lubricantAmount = getTotalFluidAmount(Materials.Lubricant.getFluid(Integer.MAX_VALUE),
                        getInputFluidInventory());
                lubricantStored = lubricantAmount[0];
                lubricantCapacity = lubricantAmount[1];
            }

            ITextComponent lubricantInfo = TextComponentUtil.stringWithColor(
                    TextFormatting.GOLD,
                    TextFormattingUtil.formatNumbers(lubricantStored) + " / " +
                            TextFormattingUtil.formatNumbers(lubricantCapacity) + " L");
            hoverList.add(TextComponentUtil.translationWithColor(
                    TextFormatting.GRAY,
                    "gregtech.multiblock.large_combustion_engine.lubricant_amount",
                    lubricantInfo));
        } else {
            // Oxygen/LOX
            if (isBoostAllowed()) {
                int oxygenStored = 0;
                int oxygenCapacity = 0;
                if (isStructureFormed() && getInputFluidInventory() != null) {
                    // Hunt for tanks with Oxygen or LOX (depending on tier) in them
                    FluidStack oxygenStack = isExtreme ?
                            Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, Integer.MAX_VALUE) :
                            Materials.Oxygen.getFluid(Integer.MAX_VALUE);
                    int[] oxygenAmount = getTotalFluidAmount(oxygenStack, getInputFluidInventory());
                    oxygenStored = oxygenAmount[0];
                    oxygenCapacity = oxygenAmount[1];
                }

                ITextComponent oxygenInfo = TextComponentUtil.stringWithColor(
                        TextFormatting.AQUA,
                        TextFormattingUtil.formatNumbers(oxygenStored) + " / " +
                                TextFormattingUtil.formatNumbers(oxygenCapacity) + " L");
                String key = isExtreme ? "gregtech.multiblock.large_combustion_engine.liquid_oxygen_amount" :
                        "gregtech.multiblock.large_combustion_engine.oxygen_amount";
                hoverList.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, key, oxygenInfo));
            } else {
                String key = isExtreme ? "gregtech.multiblock.large_combustion_engine.liquid_oxygen_boost_disallowed" :
                        "gregtech.multiblock.large_combustion_engine.oxygen_boost_disallowed";
                hoverList.add(TextComponentUtil.translationWithColor(TextFormatting.YELLOW, key));
            }
        }
    }

    private static class LargeRocketEngineWorkableHandler extends MultiblockFuelRecipeLogic {

        private static final FluidStack OXYGEN_STACK = Materials.Oxygen.getFluid(20);
        private static final FluidStack LIQUID_OXYGEN_STACK = Materials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 80);
        private static final FluidStack LUBRICANT_STACK = Materials.Lubricant.getFluid(1);
        private final MetaTileEntityLargeRocketEngine RocketEngine;
        private final boolean isExtreme;
        private final int tier;
        private boolean isOxygenBoosted = false;

        public LargeRocketEngineWorkableHandler(RecipeMapMultiblockController tileEntity, boolean isExtreme) {
            super(tileEntity);
            this.RocketEngine = (MetaTileEntityLargeRocketEngine) tileEntity;
            this.isExtreme = isExtreme;
            this.tier = isExtreme ? GTValues.ZPM : GTValues.LuV;
        }

        @Override
        protected void updateRecipeProgress() {
            if (canRecipeProgress && drawEnergy(recipeEUt, true)) {
                drainLubricant();
                drainOxygen();
                drawEnergy(recipeEUt, false);

                // as recipe starts with progress on 1 this has to be > only not => to compensate for it
                if (++progressTime > maxProgressTime) {
                    completeRecipe();
                }
            }
        }

        protected void checkOxygen() {
            // check oxygen if present to boost production, and if the dynamo hatch supports it
            if (RocketEngine.isBoostAllowed()) {
                IMultipleTankHandler inputTank = RocketEngine.getInputFluidInventory();
                FluidStack boosterStack = isExtreme ? LIQUID_OXYGEN_STACK : OXYGEN_STACK;
                isOxygenBoosted = boosterStack.isFluidStackIdentical(inputTank.drain(boosterStack, false));
            }
        }

        protected void drainOxygen() {
            if (isOxygenBoosted && totalContinuousRunningTime % 20 == 0) {
                FluidStack boosterStack = isExtreme ? LIQUID_OXYGEN_STACK : OXYGEN_STACK;
                RocketEngine.getInputFluidInventory().drain(boosterStack, true);
            }
        }

        protected boolean checkLubricant() {
            // check lubricant and invalidate if it fails
            IMultipleTankHandler inputTank = RocketEngine.getInputFluidInventory();
            if (LUBRICANT_STACK.isFluidStackIdentical(inputTank.drain(LUBRICANT_STACK, false))) {
                return true;
            } else {
                invalidate();
                return false;
            }
        }

        protected void drainLubricant() {
            if (totalContinuousRunningTime == 1 || totalContinuousRunningTime % 72 == 0) {
                IMultipleTankHandler inputTank = RocketEngine.getInputFluidInventory();
                inputTank.drain(LUBRICANT_STACK, true);
            }
        }

        @Override
        protected boolean shouldSearchForRecipes() {
            checkOxygen();
            return super.shouldSearchForRecipes() && checkLubricant();
        }

        @Override
        protected boolean canProgressRecipe() {
            return super.canProgressRecipe() && checkLubricant();
        }

        @Override
        public long getMaxVoltage() {
            // this multiplies consumption through parallel
            if (isOxygenBoosted)
                return GTValues.V[tier] * 2;
            else
                return GTValues.V[tier];
        }

        @Override
        protected long boostProduction(long production) {
            // this multiplies production without increasing consumption
            if (isOxygenBoosted)
                if (!isExtreme)
                    // recipe gives 2A LuV and we want 3A LuV, for 150% efficiency
                    return production * 3 / 2;
                else
                    // recipe gives 2A ZPM and we want 4A ZPM, for 200% efficiency
                    return production * 2;
            return production;
        }

        @Override
        public void invalidate() {
            super.invalidate();
            isOxygenBoosted = false;
        }
    }
}