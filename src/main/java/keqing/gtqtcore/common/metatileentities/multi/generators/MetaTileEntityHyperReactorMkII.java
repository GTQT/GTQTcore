package keqing.gtqtcore.common.metatileentities.multi.generators;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.MultiblockFuelRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityLaserHatch;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.GTValues.*;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4.TurbineCasingType.HYPER_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4.TurbineCasingType.HYPER_CIRE_MK2;

public class MetaTileEntityHyperReactorMkII extends FuelMultiblockController implements IProgressBarMultiblock {

    private boolean boostAllowed;

    public MetaTileEntityHyperReactorMkII(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.HYPER_REACTOR_MK2_RECIPES, UIV);
        this.recipeMapWorkable = new HyperReactorMark2WorkableHandler(this);
        this.recipeMapWorkable.setMaximumOverclockVoltage(V[UIV]);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityHyperReactorMkII(metaTileEntityId);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        HyperReactorMark2WorkableHandler recipeLogic = (HyperReactorMark2WorkableHandler) this.recipeMapWorkable;
        MultiblockDisplayText.builder(textList, this.isStructureFormed())
                .setWorkingStatus(recipeLogic.isWorkingEnabled(), recipeLogic.isActive())
                .addEnergyProductionLine(V[UXV], recipeLogic.getRecipeEUt())
                .addFuelNeededLine(recipeLogic.getRecipeFluidInputInfo(), recipeLogic.getPreviousRecipeDuration())
                .addCustom((tl) -> {
                    if (this.isStructureFormed() && recipeLogic.isBoosted) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.AQUA, "gtqtcore.machine.large_naquadah_reactor.plasma_oxygen_boosted"));
                    }
                })
                .addWorkingStatusLine();
    }

    @Override
    public void addInformation(ItemStack stack,
                                World player,
                                List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.universal.tooltip.base_production_eut", GTValues.V[UIV]));
        tooltip.add(I18n.format("gtqtcore.machine.hyper_reactor_mk2.tooltip.boost", GTValues.V[UIV] * 4L));
        tooltip.add(I18n.format("gtqtcore.universal.tooltip.laser_output"));
    }


    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("       C       ", "     CCCCC     ", "       C       ")
                .aisle("    CCCCCCC    ", "   CC#####CC   ", "    CCCCCCC    ")
                .aisle("   CCCCCCCCC   ", "  C##CCCCC##C  ", "   CCCCCCCCC   ")
                .aisle("  CCC#####CCC  ", " C##C#####C##C ", "  CCC#####CCC  ")
                .aisle(" CCC#######CCC ", " C#C#######C#C ", " CCC#######CCC ")
                .aisle(" CC#########CC ", "C#C#########C#C", " CC#########CC ")
                .aisle(" CC####F####CC ", "C#C####H####C#C", " CC#########CC ")
                .aisle("CCC###FHF###CCC", "C#C###HHH###C#C", "CCC####H####CCC")
                .aisle(" CC####F####CC ", "C#C####H####C#C", " CC#########CC ")
                .aisle(" CC#########CC ", "C#C#########C#C", " CC#########CC ")
                .aisle(" CCC#######CCC ", " C#C#######C#C ", " CCC#######CCC ")
                .aisle("  CCC#####CCC  ", " C##C#####C##C ", "  CCC#####CCC  ")
                .aisle("   CCCCCCCCC   ", "  C##CCCCC##C  ", "   CCCCCCCCC   ")
                .aisle("    CCCCCCC    ", "   CC#####CC   ", "    CCCCCCC    ")
                .aisle("       C       ", "     CCSCC     ", "       C       ")
                .where('S', this.selfPredicate())
                .where('C', states(getCasingState())
                        .setMinGlobalLimited(220)
                        .or(autoAbilities(false, true, false, false, true, false, false))
                        .or(metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbility.OUTPUT_ENERGY).stream()
                                .filter(mte -> {
                                    IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, null);
                                    return container != null && container.getOutputVoltage() == GTValues.V[UIV];})
                                .toArray(MetaTileEntity[]::new))
                                .setMaxGlobalLimited(1)
                                .setPreviewCount(1))
                        .or(abilities(MultiblockAbility.OUTPUT_LASER)
                                .setMaxGlobalLimited(1)
                                .setPreviewCount(1))
                )
                .where('F', states(getFrameState()))
                .where('H', states(getUniqueCasingState()))
                .where('#', air())
                .where(' ', any())
                .build();
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(HYPER_CASING);
    }

    private static IBlockState getUniqueCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(HYPER_CIRE_MK2);
    }

    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.Naquadria).getBlock(Materials.Naquadria);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.HYPER_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.POWER_SUBSTATION_OVERLAY;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        IEnergyContainer energyContainer = this.getEnergyContainer();
        this.boostAllowed = energyContainer != null && energyContainer.getOutputVoltage() >= GTValues.V[UXV];
    }

    @Override
    protected boolean shouldShowVoidingModeButton() {
        return false;
    }

    public boolean isBoostAllowed() {
        return this.boostAllowed;
    }

    @Override
    public int getNumProgressBars() {
        return 2;
    }

    @Override
    public double getFillPercentage(int index) {
        int[] plasmaAmount;
        if (index == 0) {
            plasmaAmount = new int[2];
            if (this.getInputFluidInventory() != null) {
                MultiblockFuelRecipeLogic recipeLogic = (MultiblockFuelRecipeLogic) this.recipeMapWorkable;
                if (recipeLogic.getInputFluidStack() != null) {
                    FluidStack testStack = recipeLogic.getInputFluidStack().copy();
                    testStack.amount = Integer.MAX_VALUE;
                    plasmaAmount = this.getTotalFluidAmount(testStack, this.getInputFluidInventory());
                }
            }

            return plasmaAmount[1] != 0 ? 1.0 * plasmaAmount[0] / (double) plasmaAmount[1] : 0.0;
        } else {
            plasmaAmount = new int[2];
            if (this.getInputFluidInventory() != null && this.isBoostAllowed()) {
                FluidStack plasmaStack = Materials.Americium.getPlasma(Integer.MAX_VALUE);
                plasmaAmount = this.getTotalFluidAmount(plasmaStack, this.getInputFluidInventory());
            }

            return plasmaAmount[1] != 0 ? 1.0 * plasmaAmount[0] / (double) plasmaAmount[1] : 0.0;
        }
    }

    @Override
    public TextureArea getProgressBarTexture(int index) {
        if (index == 0) {
            return GuiTextures.PROGRESS_BAR_LCE_FUEL;
        } else {
            return GuiTextures.PROGRESS_BAR_HPCA_COMPUTATION;
        }
    }

    @Override
    public void addBarHoverText(List<ITextComponent> hoverList, int index) {
        if (index == 0) {
            this.addFuelText(hoverList);
        } else {
            int plasmaStored;
            int plasmaCapacity;
            TextComponentString plasmaInfo;

            if (this.isBoostAllowed()) {
                plasmaStored = 0;
                plasmaCapacity = 0;
                if (this.isStructureFormed() && this.getInputFluidInventory() != null) {
                    FluidStack plasmaStack = Materials.Americium.getPlasma(Integer.MAX_VALUE);
                    int[] plasmaAmount = this.getTotalFluidAmount(plasmaStack, this.getInputFluidInventory());
                    plasmaStored = plasmaAmount[0];
                    plasmaCapacity = plasmaAmount[1];
                }

                plasmaInfo = TextComponentUtil.stringWithColor(TextFormatting.AQUA, TextFormattingUtil.formatNumbers(plasmaStored) + " / " + TextFormattingUtil.formatNumbers(plasmaCapacity) + " L");
                hoverList.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "gtqtcore.machine.large_naquadah_reactor.plasma_amount", plasmaInfo));
            } else {
                hoverList.add(TextComponentUtil.translationWithColor(TextFormatting.YELLOW, "gtqtcore.machine.large_naquadah_reactor.boost_disallowed"));
            }
        }
    }

    private static class HyperReactorMark2WorkableHandler extends MultiblockFuelRecipeLogic {

        private boolean isBoosted = false;
        private final MetaTileEntityHyperReactorMkII hyperReactor;
        private static final FluidStack PLASMA_AMERICIUM_STACK = Materials.Americium.getPlasma(80);

        public HyperReactorMark2WorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
            this.hyperReactor = (MetaTileEntityHyperReactorMkII) tileEntity;
        }

        @Override
        protected void updateRecipeProgress() {
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true)) {
                this.drainPlasma();
                this.drawEnergy(this.recipeEUt, false);
                if (++this.progressTime > this.maxProgressTime) {
                    this.completeRecipe();
                }
            }
        }

        protected void checkPlasma() {
            if (this.hyperReactor.isBoostAllowed()) {
                IMultipleTankHandler inputTank = this.hyperReactor.getInputFluidInventory();
                FluidStack boosterStack = PLASMA_AMERICIUM_STACK;
                this.isBoosted = boosterStack.isFluidStackIdentical(inputTank.drain(boosterStack, false));
            }
        }

        protected void drainPlasma() {
            if (this.isBoosted && this.totalContinuousRunningTime % 20L == 0L) {
                this.hyperReactor.getInputFluidInventory().drain(PLASMA_AMERICIUM_STACK, true);
            }
        }

        @Override
        protected boolean shouldSearchForRecipes() {
            this.checkPlasma();
            return super.shouldSearchForRecipes();
        }

        @Override
        protected boolean canProgressRecipe() {
            return super.canProgressRecipe();
        }

        @Override
        public long getMaxVoltage() {
            return this.isBoosted ? V[UIV] * 2L : V[UIV];
        }

        @Override
        protected long boostProduction(long production) {
            if (this.isBoosted) {
                return production * 2L;
            } else {
                return production;
            }
        }

        @Override
        public void invalidate() {
            this.isBoosted = false;
            super.invalidate();
        }
    }

}
