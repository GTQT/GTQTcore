package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys;

import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockTurbineCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3.CasingType.blue_steel;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3.CasingType.eglin_steel;

public class MetaTileEntityCatalyticReformer extends GCYMRecipeMapMultiblockController {

    public MetaTileEntityCatalyticReformer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.CATALYTIC_REFORMER_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityCatalyticReformer(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("F   F", "XXXXX", "XXXXX", "XXXXX")
                .aisle("     ", "XXXXX", "XPPPM", "XXXXX")
                .aisle("F   F", "XXXXX", "XSXPX", "XXXXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState1())
                        .setMinGlobalLimited(24)
                        .or(autoAbilities(true, true, true, true, true, true, false)))
                .where('P', states(getCasingState2()))
                .where('F', states(MetaBlocks.FRAMES.get(Materials.BlueSteel).getBlock(Materials.BlueSteel)))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where(' ', any())
                .where('#', air())
                .build();
    }
    protected IBlockState getCasingState1() {
        return GTQTMetaBlocks.blockMultiblockCasing3.getState(blue_steel);
    }

    protected IBlockState getCasingState2() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.blue_steel;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.CATALYTIC_REFORMER_OVERLAY;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }
}
