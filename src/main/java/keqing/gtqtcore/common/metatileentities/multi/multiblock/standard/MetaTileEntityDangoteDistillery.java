package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityFluidHatch;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTMultiblockCasing;
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

import static gregtech.api.GTValues.*;
import static gregtech.api.util.RelativeDirection.*;

public class MetaTileEntityDangoteDistillery extends MultiMapMultiblockController {

    public MetaTileEntityDangoteDistillery(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.DISTILLATION_RECIPES,
                GTQTcoreRecipeMaps.MOLECULAR_DISTILLATION_RECIPES
        });
        this.recipeMapWorkable = new DangoteDistilleryRecipeLogic(this);
    }
    @Override
    public boolean canBeDistinct() {return true;}

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityDangoteDistillery(this.metaTileEntityId);
    }

    protected void addDisplayText(List<ITextComponent> textList) {
        if (this.isStructureFormed()) {
            FluidStack stackInTank = this.importFluids.drain(Integer.MAX_VALUE, false);
            if (stackInTank != null && stackInTank.amount > 0) {
                TextComponentTranslation fluidName = new TextComponentTranslation(stackInTank.getFluid().getUnlocalizedName(stackInTank), new Object[0]);
                textList.add(new TextComponentTranslation("gregtech.multiblock.distillation_tower.distilling_fluid", new Object[]{fluidName}));
            }
        }

        super.addDisplayText(textList);
    }


    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("YSY", "YYY", "YYY")
                .aisle("XXX", "XFX", "XXX").setRepeatable(11)
                .aisle("XXX", "XXX", "XXX")
                .where('S', selfPredicate())
                .where('Y', states(this.getCasingState())
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setExactLimit(1)))
                .where('X', states(this.getCasingState())
                        .or(metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbility.EXPORT_FLUIDS).stream()
                                .filter(mte->(mte instanceof MetaTileEntityFluidHatch))
                                .toArray(MetaTileEntity[]::new))
                                .setMinLayerLimited(1).setMaxLayerLimited(1))
                        .or(autoAbilities(true, false)))
                .where('F', states(this.getCasingState()))
                .build();
    }
    public boolean hasMufflerMechanics() {
        return false;
    }
    private IBlockState getCasingState() {
        return GTQTMetaBlocks.MULTI_CASING.getState(GTQTMultiblockCasing.CasingType.HC_ALLOY_CASING);
    }

    protected boolean allowSameFluidFillForOutputs() {
        return false;
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
        return GTQTTextures.DRYER_OVERLAY;
    }


    public int getFluidOutputLimit() {
        return this.getOutputFluidInventory().getTanks();
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

//            int tier = (int) Math.round(((Math.log(this.getMaxVoltage()) / Math.log(2)) - 3) / 2);

        }
    }
}