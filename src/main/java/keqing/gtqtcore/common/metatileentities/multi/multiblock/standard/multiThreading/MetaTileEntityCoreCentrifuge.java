package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.multiThreading;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockCore;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing1;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static gregtech.api.GTValues.VA;
import static keqing.gtqtcore.api.GCYSValues.HV;

public class MetaTileEntityCoreCentrifuge extends GTQTMultiblockCore {

    public MetaTileEntityCoreCentrifuge(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.CENTRIFUGE_RECIPES,
                RecipeMaps.EXTRACTOR_RECIPES,
                RecipeMaps.THERMAL_CENTRIFUGE_RECIPES
        });
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing1.getState(BlockMultiblockCasing1.CasingType.Stellite);
    }

    private static IBlockState getSecondCasingState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.POLYTETRAFLUOROETHYLENE_PIPE);
    }

    @Override
    public int getMinVa() {
        return VA[HV];
    }

    @Override
    public int getCoreNum() {
        return 8;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCoreCentrifuge(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCC", "CCC", "CCC")
                .aisle("CCC", "CGC", "CCC")
                .aisle("CCC", "CGC", "CCC")
                .aisle("PPP", "PSP", "PPP")
                .where('P', states(getCasingState()))
                .where('S', this.selfPredicate())
                .where('C', states(getCasingState())
                        .setMinGlobalLimited(16)
                        .or(autoAbilities()))
                .where('G', states(getSecondCasingState()))
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.Stellite;
    }


    @Override
    public void update() {
        super.update();
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState,
                                     Matrix4 translation,
                                     IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        GTQTTextures.ISA_MILL.renderSided(renderState, translation, pipeline, getFrontFacing(), this.recipeMapWorkable.isWorkingEnabled(), isActive());
    }

    @SideOnly(Side.CLIENT)

    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.INDUSTRIAL_CENTRIFUGE_OVERLAY;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }
}
