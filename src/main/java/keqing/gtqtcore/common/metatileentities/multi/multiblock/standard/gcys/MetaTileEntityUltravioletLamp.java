package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys;

import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3;
import keqing.gtqtcore.common.block.blocks.BlockPCBFactoryCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import supercritical.api.recipes.SCRecipeMaps;

import javax.annotation.Nonnull;

public class MetaTileEntityUltravioletLamp extends GCYMRecipeMapMultiblockController {

    public MetaTileEntityUltravioletLamp(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GTQTcoreRecipeMaps.ULTRAVIOLET_LAMP_CHAMBER_RECIPES,
                SCRecipeMaps.DECAY_CHAMBER_RECIPES});
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityUltravioletLamp(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCQCC", "CCQCC", "CCQCC", "CC CC")
                .aisle("CCQCC", "CCQCC", "CCQCC", "C   C")
                .aisle("CCCCC", "CCSCC", "CCQCC", "CC CC")
                .where('S', this.selfPredicate())
                .where('C', states(this.getCasingState())
                        .setMinGlobalLimited(8)
                        .or(this.autoAbilities()))
                .where('Q', states(this.getPipeCasingState()))
                .where(' ', any())
                .build();
    }
    private IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing3.getState(BlockMultiblockCasing3.CasingType.red_steel);
    }

    private IBlockState getPipeCasingState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE);
    }

    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return GTQTTextures.LOW_TEMP_ACTIVATOR_OVERLAY;
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.red_steel;
    }
}
