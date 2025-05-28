package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.properties.impl.TemperatureProperty;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.*;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.GCYSValues;
import keqing.gtqtcore.api.capability.IPressureContainer;
import keqing.gtqtcore.api.capability.IPressureMachine;
import keqing.gtqtcore.api.capability.impl.AtmosphericPressureContainer;
import keqing.gtqtcore.api.capability.impl.PressureMultiblockRecipeLogic;
import keqing.gtqtcore.api.metaileentity.GTQTNoTierMultiblockController;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static keqing.gtqtcore.api.unification.GTQTMaterials.Pyrotheum;

public class MetaTileEntityBlazingCZPuller extends GTQTNoTierMultiblockController implements IHeatingCoil, IPressureMachine {
    protected static int heatingCoilLevel;
    private int blastFurnaceTemperature;
    private FluidStack pyrotheumFluid = Pyrotheum.getFluid(heatingCoilLevel);
    private IPressureContainer container;

    public MetaTileEntityBlazingCZPuller(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GTQTcoreRecipeMaps.CZPULLER_RECIPES
        });
        this.recipeMapWorkable = new BlazingBlastFurnaceWorkable(this);

        //setMaxParallel(auto);
        setMaxParallelFlag(true);
        //setTimeReduce(auto);
        setTimeReduceFlag(true);
        setOverclocking(3.0);
    }

    private static IBlockState getCasingState() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.HIGH_TEMPERATURE_CASING);
    }

    private static IBlockState getCasingState2() {
        return GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.HEAT_VENT);
    }

    @Override
    public IPressureContainer getPressureContainer() {
        return this.container;
    }

    protected void initializeAbilities() {
        super.initializeAbilities();
        List<IPressureContainer> list = getAbilities(GTQTMultiblockAbility.PRESSURE_CONTAINER);
        if (list.isEmpty()) {
            this.container = new AtmosphericPressureContainer(this, 1.0);
        } else {
            this.container = list.get(0);
        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityBlazingCZPuller(this.metaTileEntityId);
    }


    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CoilType");
        Object coilType = context.get("CoilType");
        if (type instanceof IHeatingCoilBlockStats) {
            this.blastFurnaceTemperature = ((IHeatingCoilBlockStats) type).getCoilTemperature();
            heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
        } else {
            this.blastFurnaceTemperature = BlockWireCoil.CoilType.CUPRONICKEL.getCoilTemperature();
            heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
        }
        this.blastFurnaceTemperature += 100 * Math.max(0, GTUtility.getTierByVoltage(getEnergyContainer().getInputVoltage()) - GTValues.MV);
        setMaxParallel(Math.min((int) Math.pow(2, heatingCoilLevel), 16));
        setTimeReduce((100 - (Math.min(heatingCoilLevel, 15) * 5.0)) / 100);
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return this.blastFurnaceTemperature >= recipe.getProperty(TemperatureProperty.getInstance(), 0);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" YYYYY ", "   Y   ", "   X   ", "   G   ", "   G   ", "   G   ", "   G   ", "   G   ", "   X   ", "       ")
                .aisle("YYYYYYY", "  YCY  ", "  XCX  ", "  XCX  ", "  XCX  ", "  XCX  ", "  XCX  ", "  XCX  ", "  XXX  ", "   X   ")
                .aisle("YYYYYYY", " XN NY ", " XN NX ", " XN NX ", " XN NX ", " XN NX ", " XNNNX ", " XN NX ", " XRRRX ", " XXXXX ")
                .aisle("YYYYYYY", "YC   CY", "XC   CX", "GC   CG", "GC   CG", "GC   CG", "GC   CG", "GCN NCG", "XXR RXX", " XXMXX ")
                .aisle("YYYYYYY", " YN NY ", " XN NX ", " XN NX ", " XN NX ", " XN NX ", " XNNNX ", " XN NX ", " XRRRX ", " XXXXX ")
                .aisle("YYYYYYY", "  YCY  ", "  XCX  ", "  XCX  ", "  XCX  ", "  XCX  ", "  XCX  ", "  XCX  ", "  XXX  ", "   X   ")
                .aisle(" YYYYY ", "   S   ", "   X   ", "   G   ", "   G   ", "   G   ", "   G   ", "   G   ", "   X   ", "       ")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()))
                .where('Y', states(getCasingState())
                        .or(autoAbilities(true,false))
                        .or(abilities(GTQTMultiblockAbility.PRESSURE_CONTAINER).setExactLimit(1))
                )
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('#', any())
                .where('G', states(MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.LAMINATED_GLASS)))
                .where('N', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST)))
                .where('R', states(getCasingState2()))
                .where('C', heatingCoils())
                .build();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("风韵犹存的硅", new Object[0]));
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            super.addDisplayText(textList);
            if (getInputFluidInventory() != null) {
                FluidStack fluidStack = getInputFluidInventory().drain(Pyrotheum.getFluid(Integer.MAX_VALUE), false);
                int liquidOxygenAmount = fluidStack == null ? 0 : fluidStack.amount;
                textList.add(new TextComponentTranslation("gtqtcore.multiblock.vc.amount", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));
            }
            textList.add(new TextComponentTranslation("Temperature : %s", blastFurnaceTemperature));
        }
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (isStructureFormed()) {
            FluidStack lubricantStack = getInputFluidInventory().drain(Pyrotheum.getFluid(Integer.MAX_VALUE), false);
            if (lubricantStack == null || lubricantStack.amount == 0) {
                textList.add(new TextComponentTranslation("gtqtcore.multiblock.vc.no"));
            }
        }
    }

    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GCYMTextures.BLAST_CASING;
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
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public int getCurrentTemperature() {
        return this.blastFurnaceTemperature;
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        blastFurnaceTemperature = 0;
        heatingCoilLevel = 0;
        pyrotheumFluid = null;
    }
    public boolean drainPyrotheum(boolean sim)
    {
        if (pyrotheumFluid.isFluidStackIdentical(getInputFluidInventory().drain(pyrotheumFluid, false))) {
            getInputFluidInventory().drain(pyrotheumFluid, sim);
            return true;
        }
        return false;
    }
    protected class BlazingBlastFurnaceWorkable extends PressureMultiblockRecipeLogic {

        private final MetaTileEntityBlazingCZPuller combustionEngine;

        public BlazingBlastFurnaceWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
            this.combustionEngine = (MetaTileEntityBlazingCZPuller) tileEntity;
        }

        @Override
        protected void updateRecipeProgress() {

            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true) && isPressureSuit() && drainPyrotheum(false)) {
                drawEnergy(recipeEUt, false);
                drainPyrotheum(true);
                if (++this.progressTime > this.maxProgressTime) {
                    if (drawPressure(this.recipePressure, true)) {
                        drawPressure(this.recipePressure, false);
                        this.completeRecipe();
                    }
                }

                if (this.hasNotEnoughEnergy) {
                    if (this.getEnergyInputPerSecond() > 19L * this.recipeEUt) {
                        this.hasNotEnoughEnergy = false;
                    }
                }
            } else if (this.recipeEUt > 0 || this.recipePressure != GCYSValues.EARTH_PRESSURE) {
                this.hasNotEnoughEnergy = true;
                if (this.progressTime >= 2) {
                    if (ConfigHolder.machines.recipeProgressLowEnergy) {
                        this.progressTime = 1;
                    } else {
                        this.progressTime = Math.max(1, this.progressTime - 2);
                    }
                }
            }
        }

        @Override
        protected double getOverclockingDurationFactor() {
            return OCFirst ? 1/Overclocking : 0.5;
        }

        @Override
        protected double getOverclockingVoltageFactor() {
            return OCFirst ? Overclocking : 2.0;
        }

        @Override
        public void update() {
            super.update();
            if (autoParallelModel) {
                autoParallel = (int) ((this.getEnergyStored() + energyContainer.getInputPerSec() / 19L) / (getMinVoltage() == 0 ? 1 : getMinVoltage()));
                autoParallel = Math.min(autoParallel, limitAutoParallel);
                autoParallel = Math.min(autoParallel, getMaxParallel());
            }
        }

        public int getMinVoltage() {
            if ((Math.min(this.getEnergyCapacity() / (energyHatchMaxWork == 0 ? 1 : energyHatchMaxWork), this.getMaxVoltage())) == 0)
                return 1;
            return (int) (Math.min(this.getEnergyCapacity() / (energyHatchMaxWork == 0 ? 1 : energyHatchMaxWork), this.getMaxVoltage()));
        }

        @Override
        public int getParallelLimit() {
            return autoParallelModel ? autoParallel : customParallel;
        }

        @Override
        public long getMaxParallelVoltage() {
            if (OCFirst) return super.getMaxParallelVoltage();
            return super.getMaxVoltage() * getParallelLimit();
        }

        @Override
        public long getMaximumOverclockVoltage() {
            if (OCFirst) return energyContainer.getInputVoltage();
            return super.getMaximumOverclockVoltage();
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            super.setMaxProgress((int) (maxProgress * timeReduce));
        }

    }
}