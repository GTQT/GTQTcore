package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.CubeRendererState;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.cclop.ColourOperation;
import gregtech.client.renderer.cclop.LightMapOperation;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.BloomEffectUtil;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockTransparentCasing;
import keqing.gtqtcore.common.block.blocks.GTQTIsaCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;

public class MetaTileEntityFlotationFactory extends RecipeMapMultiblockController {

    public MetaTileEntityFlotationFactory(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.FLOTATION_FACTORY_RECIPES);
        this.recipeMapWorkable = new MetaTileEntityFlotationFactoryrWorkable(this);
    }
    @Override
    public boolean canBeDistinct() {return true;}
    protected static class MetaTileEntityFlotationFactoryrWorkable extends MultiblockRecipeLogic {

        public MetaTileEntityFlotationFactoryrWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }
        @Override
        public int getParallelLimit() {
            return 32;
        }
        @Override
        public void setMaxProgress(int maxProgress)
        {
            this.maxProgressTime = maxProgress/32;
        }
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityFlotationFactory(metaTileEntityId);
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("  CCC  ", "  IBI  ", "  GGG  ", "  GGG  ", "  GGG  ", "  IBI  ", "  CCC  ")
                .aisle(" CCCCC ", " PAAAP ", " PAAAP ", " PAAAP ", " PAAAP ", " PAAAP ", " CCCCC ")
                .aisle("CCCCCCC", "IAAAAAI", "GAAAAAG", "GAAAAAG", "GAAAAAG", "IAAAAAI", "CCCCCCC")
                .aisle("CCCCCCC", "BAAPAAB", "GAAPAAG", "GAAPAAG", "GAAPAAG", "BAAPAAB", "CCCCCCC")
                .aisle("CCCCCCC", "IAAAAAI", "GAAAAAG", "GAAAAAG", "GAAAAAG", "IAAAAAI", "CCCCCCC")
                .aisle(" CCCCC ", " PAAAP ", " PAAAP ", " PAAAP ", " PAAAP ", " PAAAP ", " CCCCC ")
                .aisle("  CCC  ", "  ISI  ", "  GGG  ", "  GGG  ", "  GGG  ", "  IBI  ", "  CCC  ")
                .where('S', selfPredicate())
                .where('I', states(getIntakeState()))
                .where('B', states(getGearBoxState()))
                .where('G', states(getGlassState()))
                .where('C', states(getCasingState()).setMinGlobalLimited(57).or(autoAbilities()))
                .where('P', states(getPipeState()))
                .where(' ', any())
                .where('A', air())
                .build();
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.ISA_CASING.getState(GTQTIsaCasing.CasingType.FLOTATION_CASING);
    }
    private static IBlockState getGlassState() {
        return GTQTMetaBlocks.TRANSPARENT_CASING.getState(BlockTransparentCasing.CasingType.PMMA);
    }
    private static IBlockState getPipeState() {
        return GTQTMetaBlocks.ISA_CASING.getState(GTQTIsaCasing.CasingType.FLOTATION_CASING_PIPE);
    }
    private static IBlockState getIntakeState() {
        return GTQTMetaBlocks.ISA_CASING.getState(GTQTIsaCasing.CasingType.FLOTATION_INTAKE_CASING);
    }
    private static IBlockState getGearBoxState() {
        return GTQTMetaBlocks.ISA_CASING.getState(GTQTIsaCasing.CasingType.FLOTATION_CASING_GEARBOX);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(),
                recipeMapWorkable.isActive(), recipeMapWorkable.isWorkingEnabled());
        if (recipeMapWorkable.isActive() && isStructureFormed()) {
            EnumFacing back = getFrontFacing().getOpposite();
            for(float i=-2;i<=2;i++) {
                for (float j = -2; j <=2; j++) {
                    Matrix4 offset = translation.copy().translate(back.getXOffset() * 3+i, -0.3, back.getZOffset() * 3+j);
                    CubeRendererState op = Textures.RENDER_STATE.get();
                    Textures.RENDER_STATE.set(new CubeRendererState(op.layer, CubeRendererState.PASS_MASK, op.world));
                    Textures.renderFace(renderState, offset,
                            ArrayUtils.addAll(pipeline, new LightMapOperation(240, 240), new ColourOperation(0xFFFFFFFF)),
                            EnumFacing.UP, Cuboid6.full, TextureUtils.getBlockTexture("lava_still"),
                            BloomEffectUtil.getEffectiveBloomLayer());
                    Textures.RENDER_STATE.set(op);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.FLOTATION_CASING;
    }
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }

}