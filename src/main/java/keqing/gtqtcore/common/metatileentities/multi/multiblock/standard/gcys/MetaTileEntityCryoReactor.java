package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys;

import gregicality.multiblocks.api.metatileentity.GCYMAdvanceRecipeMapMultiblockController;
import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static gregtech.api.recipes.RecipeMaps.VACUUM_RECIPES;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3.CasingType.black_steel;

public class MetaTileEntityCryoReactor extends GCYMAdvanceRecipeMapMultiblockController {

    public MetaTileEntityCryoReactor(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GTQTcoreRecipeMaps.LOW_TEMP_ACTIVATOR_RECIPES
        });
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityCryoReactor(metaTileEntityId);
    }


    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("    RR", "    TV", "    VV", "    TV", "    TT")
                .aisle("F   RR", "F X TT", "FXPPPV", "F X TT", "F   TT")
                .aisle("  X   ", " XCX  ", "XCKCP ", " XCX  ", "  X   ")
                .aisle(" XXX  ", "XCCCX ", "XC#KP ", "XCCCX ", " XXX  ")
                .aisle("  X   ", " XCX  ", "XCCCX ", " XCX  ", "  X   ")
                .aisle("F   F ", "F X F ", "FXSXF ", "F X F ", "F   F ")
                .where('S', selfPredicate())
                .where('X', states(getCasingState1()).setMinGlobalLimited(20)
                        .or(autoAbilities()))
                .where('F', states(MetaBlocks.FRAMES.get(Materials.BlackSteel).getBlock(Materials.BlackSteel)))
                .where('R', states(MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel)))
                .where('C', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PTFE_INERT_CASING)))
                .where('K', states(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.POLYTETRAFLUOROETHYLENE_PIPE)))
                .where('P', states(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE)))
                .where('V', states(GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.HEAT_VENT)))
                .where('T', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID)))
                .where(' ', any())
                .where('#', air())
                .build();
    }

    protected IBlockState getCasingState1() {
        return GTQTMetaBlocks.blockMultiblockCasing3.getState(black_steel);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.black_steel;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.CRYOGENIC_REACTOR_OVERLAY;
    }
}
