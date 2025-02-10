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
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.api.util.RelativeDirection;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityFluidHatch;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

import static gregtech.api.GTValues.*;
import static gregtech.api.util.RelativeDirection.*;

public class MetaTileEntityDangoteDistillery extends MultiMapMultiblockController implements IDistillationTower {
    protected final DistillationTowerLogicHandler handler;

    public MetaTileEntityDangoteDistillery(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.DISTILLATION_RECIPES,
                GTQTcoreRecipeMaps.MOLECULAR_DISTILLATION_RECIPES
        });
        this.recipeMapWorkable = new DangoteDistilleryRecipeLogic(this);
        this.handler = new DistillationTowerLogicHandler(this);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityDangoteDistillery(this.metaTileEntityId);
    }

    @Override
    protected Function<BlockPos, Integer> multiblockPartSorter() {
        return RelativeDirection.UP.getSorter(getFrontFacing(), getUpwardsFacing(), isFlipped());
    }

    @Override
    public boolean allowsExtendedFacing() {
        return false;
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

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        if (!usesAdvHatchLogic() || this.structurePattern == null) return;
        handler.determineLayerCount(this.structurePattern);
        handler.determineOrderedFluidOutputs();
    }

    protected boolean usesAdvHatchLogic() {
        return getCurrentRecipeMap() == RecipeMaps.DISTILLATION_RECIPES;
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        if (usesAdvHatchLogic())
            this.handler.invalidate();
    }

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

    @Override
    public boolean allowSameFluidFillForOutputs() {
        return !usesAdvHatchLogic();
    }

    @Override
    public int getFluidOutputLimit() {
        if (usesAdvHatchLogic()) return this.handler.getLayerCount();
        else return super.getFluidOutputLimit();
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
    public boolean hasMufflerMechanics() {
        return false;
    }

    private IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing3.getState(BlockMultiblockCasing3.CasingType.HC_ALLOY_CASING);
    }

    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GTQTTextures.HC_ALLOY_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return GTQTTextures.CHEMICAL_DRYER_OVERLAY;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("超级蒸馏塔", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.dangote_distillery.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.dangote_distillery.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.dangote_distillery.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.machine.dangote_distillery.tooltip.4"));
        tooltip.add(I18n.format("gtqtcore.machine.dangote_distillery.tooltip.5"));
        tooltip.add(I18n.format("gtqtcore.machine.dangote_distillery.tooltip.6"));
        tooltip.add(I18n.format("gtqtcore.machine.dangote_distillery.tooltip.7"));
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

        public void setMaxProgress(int maxProgress) {
            if (isDistilleryMode()) {
                this.maxProgressTime = maxProgress / 4;
            } else if (isDistillationMode()) {
                this.maxProgressTime = maxProgress / 2;
            } else {
                this.maxProgressTime = maxProgress;
            }
        }

        private int ParallelTier(int tier) {
            return 4 * (tier * 4);
        }

        private int HigherParallelTier(int tier) {
            return 12 * (tier * 4);
        }

        private int getTier(long vol) {
            for (int i = 0; i < V.length; i++) {
                if (V[i] == vol) {
                    return i;
                }
            }
            return 0;
        }

        @Override
        public int getParallelLimit() {
            if (this.getMaxVoltage() > V[MAX]) {    //  For MAX+, get 4 * 15 * 4
                return HigherParallelTier(15);
            }
            int tier = getTier(getMaxVoltage());
            if (tier == 0) {
                return 1;
            }
            if (tier <= UV) {
                return ParallelTier(getTier(getMaxVoltage()));
            } else {
                return HigherParallelTier(getTier(getMaxVoltage()));
            }
        }

        @Override
        protected void outputRecipeOutputs() {
            if (usesAdvHatchLogic()) {
                GTTransferUtils.addItemsToItemHandler(getOutputInventory(), false, itemOutputs);
                handler.applyFluidToOutputs(fluidOutputs, true);
            } else {
                super.outputRecipeOutputs();
            }
        }

        @Override
        protected boolean setupAndConsumeRecipeInputs(Recipe recipe,
                                                      IItemHandlerModifiable importInventory,
                                                      IMultipleTankHandler importFluids) {
            if (!usesAdvHatchLogic()) {
                return super.setupAndConsumeRecipeInputs(recipe, importInventory, importFluids);
            }

            this.overclockResults = calculateOverclock(recipe);

            modifyOverclockPost(overclockResults, recipe.getRecipePropertyStorage());

            if (!hasEnoughPower(overclockResults)) {
                return false;
            }

            IItemHandlerModifiable exportInventory = getOutputInventory();

            // We have already trimmed outputs and chanced outputs at this time
            // Attempt to merge all outputs + chanced outputs into the output bus, to prevent voiding chanced outputs
            if (!metaTileEntity.canVoidRecipeItemOutputs() &&
                    !GTTransferUtils.addItemsToItemHandler(exportInventory, true, recipe.getAllItemOutputs())) {
                this.isOutputsFull = true;
                return false;
            }

            // Perform layerwise fluid checks
            if (!metaTileEntity.canVoidRecipeFluidOutputs() &&
                    !handler.applyFluidToOutputs(recipe.getAllFluidOutputs(), false)) {
                this.isOutputsFull = true;
                return false;
            }

            this.isOutputsFull = false;
            if (recipe.matches(true, importInventory, importFluids)) {
                this.metaTileEntity.addNotifiedInput(importInventory);
                return true;
            }
            return false;
        }

        @Override
        protected IMultipleTankHandler getOutputTank() {
            if (usesAdvHatchLogic())
                return handler.getFluidTanks();
            return super.getOutputTank();
        }
    }
}