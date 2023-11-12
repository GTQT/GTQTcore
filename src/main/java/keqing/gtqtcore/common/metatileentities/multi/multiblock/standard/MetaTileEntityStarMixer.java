package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregicality.science.common.block.GCYSMetaBlocks;
import gregicality.science.common.block.blocks.BlockGCYSMultiblockCasing;
import gregicality.science.common.block.blocks.BlockTransparentCasing;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.ParallelLogicType;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTQuantumCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.unification.material.Materials.Lubricant;

public class MetaTileEntityStarMixer extends RecipeMapMultiblockController {

    protected int heatingCoilLevel;
    protected int heatingCoilDiscount;
    public MetaTileEntityStarMixer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.STAR_MIXER);
        this.recipeMapWorkable = new MetaTileEntityStarMixerWorkable(this);
    }
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityStarMixer(this.metaTileEntityId);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            if (getInputFluidInventory() != null) {
                FluidStack LubricantStack = getInputFluidInventory().drain(Lubricant.getFluid(Integer.MAX_VALUE), false);
                int liquidOxygenAmount = LubricantStack == null ? 0 : LubricantStack.amount;
                textList.add(new TextComponentTranslation("gtqtcore.multiblock.ma.amount", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));
                textList.add(new TextComponentTranslation("gtqtcore.multiblock.ma.heat"));
            }
        }
        if (isStructureFormed()) {
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.md.level", heatingCoilLevel));
        }
        super.addDisplayText(textList);
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (isStructureFormed()) {
            FluidStack lubricantStack = getInputFluidInventory().drain(Lubricant.getFluid(Integer.MAX_VALUE), false);
            if (lubricantStack == null || lubricantStack.amount == 0) {
                textList.add(new TextComponentTranslation("gtqtcore.multiblock.ma.no"));
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.2", 16));
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.1"));
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("把宇宙搅拌在一起", new Object[0]));
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
            this.heatingCoilDiscount = ((IHeatingCoilBlockStats) coilType).getEnergyDiscount();
        } else {
            this.heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
            this.heatingCoilDiscount = BlockWireCoil.CoilType.CUPRONICKEL.getEnergyDiscount();
        }
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("#XXX#", "#CCC#", "#GGG#", "#CCC#", "#XXX#")
                .aisle("XXXXX", "CABAC", "GAAAG", "CABAC", "XXXXX")
                .aisle("XXXXX", "CBBBC", "GABAG", "CBBBC", "XXMXX")
                .aisle("XXXXX", "CABAC", "GAAAG", "CABAC", "XXXXX")
                .aisle("#XSX#", "#CCC#", "#GGG#", "#CCC#", "#XXX#")
                .where('S', selfPredicate())
                .where('X',
                        states(getCasingState()).setMinGlobalLimited(30)
                                .or(autoAbilities(true, true, true, true, true, true, false)))
                .where('C', heatingCoils())
                .where('G', states(getCasingState1()))
                .where('B', states(getCasingState2()))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('A', air())
                .where('#', any())
                .build();
    }
    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.QUANTUM_CASING.getState(GTQTQuantumCasing.CasingType.QUANTUM_CASING);
    }

    private static IBlockState getCasingState1() {
        return GTQTMetaBlocks.QUANTUM_CASING.getState(GTQTQuantumCasing.CasingType.DIMENSIONAL_CASING);
    }
    private static IBlockState getCasingState2() {
        return GTQTMetaBlocks.QUANTUM_CASING.getState(GTQTQuantumCasing.CasingType.FIELD_GENERATOR_CASING);
    }

    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.QUANTUM_CASING;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    /**
     * @param heatingCoilLevel the level to get the parallel for
     * @return the max parallel for the heating coil level
     */
    public static int getMaxParallel(int heatingCoilLevel) {
        return  heatingCoilLevel;
    }
    private final FluidStack LUBRICANT_STACK = Lubricant.getFluid(1);

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        heatingCoilLevel = 0;
        heatingCoilDiscount = 0;
    }
    protected class MetaTileEntityStarMixerWorkable extends MultiblockRecipeLogic {

        private final MetaTileEntityStarMixer combustionEngine;
        public MetaTileEntityStarMixerWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
            this.combustionEngine = (MetaTileEntityStarMixer) tileEntity;
        }
        @Nonnull
        @Override
        public ParallelLogicType getParallelLogicType() {
            return ParallelLogicType.APPEND_ITEMS;
        }
        @Override
        public int getParallelLimit() {
            return getMaxParallel(heatingCoilLevel);
        }
        protected void updateRecipeProgress() {
            if (canRecipeProgress && drawEnergy(recipeEUt, true)) {
                IMultipleTankHandler inputTank = combustionEngine.getInputFluidInventory();
                if (LUBRICANT_STACK.isFluidStackIdentical(inputTank.drain(LUBRICANT_STACK, false))) {
                    inputTank.drain(LUBRICANT_STACK, true);
                    if (++progressTime > maxProgressTime) {
                        completeRecipe();
                    }
                }
                else return;
                drawEnergy(recipeEUt, false);

            }
        }
    }
}