package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;


import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityFluidHatch;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.GTValues.V;
import static gregtech.api.GTValues.VA;
import static gregtech.api.util.RelativeDirection.*;

public class MetaTileEntityVacuumDistillationTower extends RecipeMapMultiblockController {
    public MetaTileEntityVacuumDistillationTower(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.DISTILLATION_RECIPES);
        this.recipeMapWorkable = new MFSWorkableHandler(this);
    }
    private class MFSWorkableHandler extends MultiblockRecipeLogic {
        private boolean isDistilleryMode() {
            return this.getRecipeMap() == GTQTcoreRecipeMaps.DISTILLATION_KETTLE;
        }
        public MFSWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }
        @Override
        public int getParallelLimit() {
            return 32;
        }

        public long getMaxVoltage() {
            return V[6];
        }

        @Override
        public void setMaxProgress(int maxProgress)
        {
            this.maxProgressTime = (int) (maxProgress*0.8);
        }
    }
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityVacuumDistillationTower(this.metaTileEntityId);
    }

    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle(" CSC  ", "CCCCCC", "CCCCCC", "CCCCCC", " CCC  ")
                .aisle(" CGC  ", "C#F#CC", "IFFF#P", "C#F#CC", " CCC  ")
                .aisle(" CGC  ", "C#F#CC", "CFFFCC", "C#F#CC", " CCC  ")
                .aisle(" XGX  ", "X#F#D ", "XFFFD ", "X#F#D ", " XXX  ").setRepeatable(1,12)
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
                                .filter(mte->(mte instanceof MetaTileEntityFluidHatch))
                                .toArray(MetaTileEntity[]::new))
                                .setMinLayerLimited(1).setMaxLayerLimited(1))
                        .or(autoAbilities(true, false)))
                .where('#', air())
                .build();
    }
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GTQTTextures.IRIDIUM_CASING;
    }
    protected IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.Naquadah).getBlock(Materials.Naquadah);
    }
    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }
    protected IBlockState getCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.IRIDIUM_CASING);
    }
    protected IBlockState getPipeCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.NQ_MACHINE_CASING);
    }

    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gregtech.machine.perfect_oc", new Object[0]));
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.DRYER_OVERLAY;
    }

    @Override
    public boolean allowsExtendedFacing() {
        return false;
    }
}