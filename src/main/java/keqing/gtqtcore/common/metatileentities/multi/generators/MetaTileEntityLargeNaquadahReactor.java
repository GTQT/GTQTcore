package keqing.gtqtcore.common.metatileentities.multi.generators;

import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;
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
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static gregtech.api.GTValues.*;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing.TurbineCasingType.HYPER_CASING;

public class MetaTileEntityLargeNaquadahReactor extends FuelMultiblockController implements IProgressBarMultiblock {

    private boolean boostAllowed;

    public MetaTileEntityLargeNaquadahReactor(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.NAQUADAH_REACTOR_RECIPES, UV);
        this.recipeMapWorkable = new LargeNaquadahReactorWorkableHandler(this);
        this.recipeMapWorkable.setMaximumOverclockVoltage(V[UV]);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLargeNaquadahReactor(metaTileEntityId);
    }

    @Override
    protected void initializeAbilities() {
        this.inputFluidInventory = new FluidTankList(this.allowSameFluidFillForOutputs(), this.getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        List<IEnergyContainer> energyContainer = new ArrayList<>(this.getAbilities(MultiblockAbility.OUTPUT_LASER));
        energyContainer.addAll(this.getAbilities(MultiblockAbility.OUTPUT_LASER));
        this.energyContainer = new EnergyContainerList(energyContainer);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        LargeNaquadahReactorWorkableHandler recipeLogic = (LargeNaquadahReactorWorkableHandler) this.recipeMapWorkable;
        MultiblockDisplayText.builder(textList, this.isStructureFormed())
                .setWorkingStatus(recipeLogic.isWorkingEnabled(), recipeLogic.isActive())
                .addEnergyProductionLine(GTValues.V[UHV], recipeLogic.getRecipeEUt())
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
                               @Nullable World player,
                               List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.universal.tooltip.base_production_eut", GTValues.V[UV]));
        tooltip.add(I18n.format("gtqtcore.machine.large_naquadah_reactor.tooltip.boost", GTValues.V[UV] * 4L));
        tooltip.add(I18n.format("gtqtcore.universal.tooltip.laser_output"));
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" CCC ", " CUC ", " CCC ", "  C  ", "  C  ", " CCC ", " CUC ", " CCC ")
                .aisle("CCCCC", "CP#PC", "CG#GC", " P#P ", " P#P ", "CG#GC", "CP#PC", " CCC ")
                .aisle("CCCCC", "U#F#U", "C#F#C", "C#F#C", "C#F#C", "C#F#C", "U#F#U", " COC ")
                .aisle("CCCCC", "CP#PC", "CG#GC", " P#P ", " P#P ", "CG#GC", "CP#PC", " CCC ")
                .aisle(" CCC ", " CSC ", " CCC ", "  C  ", "  C  ", " CCC ", " CUC ", " CCC ")
                .where('S', this.selfPredicate())
                .where('C', states(getCasingState())
                        .setMinGlobalLimited(70)
                        .or(autoAbilities(false, true, false, false, true, false, false))
                        .or(metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbility.OUTPUT_ENERGY).stream()
                                .filter(mte -> {
                                    IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, null);
                                    return container != null && container.getOutputVoltage() == GTValues.V[UV];})
                                .toArray(MetaTileEntity[]::new))
                                .setMaxGlobalLimited(1)
                                .setPreviewCount(1))
                        .or(abilities(MultiblockAbility.OUTPUT_LASER)
                                .setMaxGlobalLimited(1)
                                .setPreviewCount(1)))
                .where('U', states(getUniqueCasingState()))
                .where('G', states(getSecondCasingState()))
                .where('F', states(getFrameState()))
                .where('P', states(getBoilerCasingState()))
                .where('O', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where(' ', any())
                .where('#', air())
                .build();
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(HYPER_CASING);
    }


    private static IBlockState getSecondCasingState() {
        return MetaBlocks.MULTIBLOCK_CASING.getState(gregtech.common.blocks.BlockMultiblockCasing.MultiblockCasingType.GRATE_CASING);
    }

    private static IBlockState getUniqueCasingState() {
        return GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.HEAT_VENT);
    }

    private static IBlockState getBoilerCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.POLYBENZIMIDAZOLE_PIPE);
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

    @SideOnly(Side.CLIENT)
    @Override
    public EnumParticleTypes getMufflerParticle() {
        return EnumParticleTypes.SPELL_WITCH;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        IEnergyContainer energyContainer = this.getEnergyContainer();
        this.boostAllowed = energyContainer != null && energyContainer.getOutputVoltage() >= GTValues.V[UHV];
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
                FluidStack plasmaStack = Materials.Oxygen.getPlasma(Integer.MAX_VALUE);
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
                    FluidStack plasmaStack = Materials.Oxygen.getPlasma(Integer.MAX_VALUE);
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

    private static class LargeNaquadahReactorWorkableHandler extends MultiblockFuelRecipeLogic {

        private boolean isBoosted = false;
        private final MetaTileEntityLargeNaquadahReactor naquadahReactor;
        private static final FluidStack PLASMA_OXYGEN_STACK = Materials.Oxygen.getPlasma(50); // This amount from Gregicality (Legacy).

        public LargeNaquadahReactorWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
            this.naquadahReactor = (MetaTileEntityLargeNaquadahReactor) tileEntity;
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
            if (this.naquadahReactor.isBoostAllowed()) {
                IMultipleTankHandler inputTank = this.naquadahReactor.getInputFluidInventory();
                FluidStack boosterStack = PLASMA_OXYGEN_STACK;
                this.isBoosted = boosterStack.isFluidStackIdentical(inputTank.drain(boosterStack, false));
            }
        }

        protected void drainPlasma() {
            if (this.isBoosted && this.totalContinuousRunningTime % 20L == 0L) {
                this.naquadahReactor.getInputFluidInventory().drain(PLASMA_OXYGEN_STACK, true);
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
            return this.isBoosted ? GTValues.V[UV] * 2L : GTValues.V[UV];
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
