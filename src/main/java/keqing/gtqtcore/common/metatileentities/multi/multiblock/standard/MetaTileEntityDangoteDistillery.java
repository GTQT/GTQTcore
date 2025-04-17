package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.IDistillationTower;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.DistillationTowerLogicHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.api.util.RelativeDirection;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityFluidHatch;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4;
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

import java.util.List;
import java.util.function.Function;

import static gregtech.api.GTValues.*;
import static gregtech.api.util.RelativeDirection.*;

public class MetaTileEntityDangoteDistillery extends MultiMapMultiblockController implements IDistillationTower {
    protected DistillationTowerLogicHandler handler;

    public MetaTileEntityDangoteDistillery(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.DISTILLERY_RECIPES,
                RecipeMaps.DISTILLATION_RECIPES,
                GTQTcoreRecipeMaps.MOLECULAR_DISTILLATION_RECIPES
        });

        this.recipeMapWorkable = new DangoteDistilleryRecipeLogic(this);
        this.handler = new DistillationTowerLogicHandler(this);

    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityDangoteDistillery(metaTileEntityId);
    }

    @Override
    public boolean allowSameFluidFillForOutputs() {
        return false;
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        if (this.handler != null) handler.invalidate();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);

        if (this.handler == null || this.structurePattern == null) return;
        handler.determineLayerCount(this.structurePattern);
        handler.determineOrderedFluidOutputs();
    }


    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    protected Function<BlockPos, Integer> multiblockPartSorter() {
        return RelativeDirection.UP.getSorter(getFrontFacing(), getUpwardsFacing(), isFlipped());
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle(" CSC  ", "CCCCCC", "CCCCCC", "CCCCCC", " CCC  ")
                .aisle(" CGC  ", "C#F#CC", "IFFF#P", "C#F#CC", " CCC  ")
                .aisle(" CGC  ", "C#F#CC", "CFFFCC", "C#F#CC", " CCC  ")
                .aisle(" XGX  ", "X#F#D ", "XFFFD ", "X#F#D ", " XXX  ").setRepeatable(1, 12)
                .aisle(" DDD  ", "DDDDD ", "DDDDD ", "DDDDD ", " DDD  ")
                .where('S', this.selfPredicate())
                .where('G', states(this.getGlassState()))
                .where('P', states(this.getPipeCasingState()))
                .where('F', states(this.getFrameState()))
                .where('C', states(this.getCasingState())
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(1)))
                .where('I', abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(1))
                .where('D', states(this.getCasingState()))
                .where('X', states(getCasingState())
                        .or(metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbility.EXPORT_FLUIDS).stream()
                                .filter(mte -> (mte instanceof MetaTileEntityFluidHatch))
                                .toArray(MetaTileEntity[]::new))
                                .setMinLayerLimited(1).setMaxLayerLimited(1))
                        .or(autoAbilities(true, false)))
                .where('#', air())
                .build();
    }


    protected IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.Naquadah).getBlock(Materials.Naquadah);
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }

    protected IBlockState getPipeCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.NQ_MACHINE_CASING);
    }

    private IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing3.getState(BlockMultiblockCasing3.CasingType.HC_ALLOY_CASING);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            FluidStack stackInTank = importFluids.drain(Integer.MAX_VALUE, false);
            if (stackInTank != null && stackInTank.amount > 0) {
                ITextComponent fluidName = TextComponentUtil.setColor(GTUtility.getFluidTranslation(stackInTank),
                        TextFormatting.AQUA);
                textList.add(TextComponentUtil.translationWithColor(
                        TextFormatting.GRAY,
                        "gregtech.multiblock.distillation_tower.distilling_fluid",
                        fluidName));
            }
        }
        super.addDisplayText(textList);
    }


    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GTQTTextures.HC_ALLOY_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.DISTILLATION_TOWER_OVERLAY;
    }

    @Override
    public void addInformation(ItemStack stack,
                               World player,
                               List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.machine.dangote_distillery.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.dangote_distillery.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.dangote_distillery.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.machine.dangote_distillery.tooltip.4"));
        tooltip.add(I18n.format("gtqtcore.machine.dangote_distillery.tooltip.5"));
        tooltip.add(I18n.format("gtqtcore.machine.dangote_distillery.tooltip.6"));
        tooltip.add(I18n.format("gtqtcore.machine.dangote_distillery.tooltip.7"));
    }

    @Override
    public int getFluidOutputLimit() {
        if (this.handler != null) return this.handler.getLayerCount();
        else return super.getFluidOutputLimit();
    }

    protected class DangoteDistilleryRecipeLogic extends MultiblockRecipeLogic {

        public DangoteDistilleryRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        private boolean isDistilleryMode() {
            return this.getRecipeMap() == RecipeMaps.DISTILLERY_RECIPES;
        }

        private boolean isDistillationMode() {
            return this.getRecipeMap() == RecipeMaps.DISTILLATION_RECIPES;
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            if (isDistilleryMode()) {
                this.maxProgressTime = maxProgress / 4;
            } else if (isDistillationMode()) {
                this.maxProgressTime = maxProgress / 2;
            } else {
                this.maxProgressTime = maxProgress;
            }
        }

        @Override
        public int getParallelLimit() {
            if (this.getMaxVoltage() > V[MAX]) { //  For MAX+, get (12 * 15 * 4) = 720.
                return 720;
            }
            int tier = GTUtility.getTierByVoltage(getMaxVoltage());
            if (tier == 0) {
                return 1;
            }
            if (tier <= UV) {
                return 4 * (tier * 4);
            } else {
                return 12 * (tier * 4);
            }
        }

        protected IMultipleTankHandler getOutputTank() {
            return handler.getFluidTanks();
        }

    }
}