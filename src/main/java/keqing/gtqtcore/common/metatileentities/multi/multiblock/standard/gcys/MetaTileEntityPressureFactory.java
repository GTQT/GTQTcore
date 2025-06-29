package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys;

import gregicality.multiblocks.api.metatileentity.GCYMAdvanceRecipeMapMultiblockController;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3.CasingType.black_steel;

public class MetaTileEntityPressureFactory extends GCYMAdvanceRecipeMapMultiblockController {

    public MetaTileEntityPressureFactory(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GTQTcoreRecipeMaps.PRESSURE_LAMINATOR_RECIPES,
                GTQTcoreRecipeMaps.LAMINATOR_RECIPES});
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPressureFactory(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXFXX", "XXXFXX", "XXXFXX")
                .aisle("XXXFXX", "XPPPPM", "XXXFXX")
                .aisle("XXXFXX", "XSXFXX", "XXXFXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState1())
                        .setMinGlobalLimited(24)
                        .or(autoAbilities(true, true, true, true, true, true, false)))
                .where('P', states(getCasingState2()))
                .where('F', states(MetaBlocks.FRAMES.get(Materials.BlackSteel).getBlock(Materials.BlackSteel)))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where(' ', any())
                .where('#', air())
                .build();
    }

    protected IBlockState getCasingState1() {
        return GTQTMetaBlocks.blockMultiblockCasing3.getState(black_steel);
    }

    protected IBlockState getCasingState2() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.black_steel;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.LAMINATOR_OVERLAY;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }
}
