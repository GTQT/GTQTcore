package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys;

import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregicality.multiblocks.api.unification.GCYMMaterials;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing1;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing1.CasingType.Hdcs;

public class MetaTileEntityChemicalReactor extends GCYMRecipeMapMultiblockController {

    public MetaTileEntityChemicalReactor(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.CHEMICAL_RECIPES,
                RecipeMaps.LARGE_CHEMICAL_RECIPES,
                GTQTcoreRecipeMaps.POLYMERIZATION_RECIPES});
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityChemicalReactor(metaTileEntityId);
    }


    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("   FF", "   UU", "   UU", "   DD", "   DD")
                .aisle("F  FF", "CMCDU", "CCCCU", "CCCDD", "   DD")
                .aisle("     ", "CCCC ", "CQQC ", "CCCC ", "     ")
                .aisle("F  F ", "CCCC ", "CSCC ", "CCCC ", "     ")
                .where('S', this.selfPredicate())
                .where('C', states(this.getCasingState1())
                        .setMinGlobalLimited(6)
                        .or(this.autoAbilities(true, true, true, true, true, true, false)))
                .where('D', states(this.getCasingState2()))
                .where('U', states(this.getUniqueCasingState()))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('Q', states(this.getPipeCasingState()))
                .where('F', states(MetaBlocks.FRAMES.get(GCYMMaterials.HSLASteel).getBlock(GCYMMaterials.HSLASteel)))
                .where(' ', any())
                .build();
    }

    protected IBlockState getCasingState1() {
        return GTQTMetaBlocks.blockMultiblockCasing1.getState(Hdcs);
    }

    protected IBlockState getCasingState2() {
        return GTQTMetaBlocks.blockMultiblockCasing1.getState(BlockMultiblockCasing1.CasingType.HastelloyN);
    }

    private IBlockState getUniqueCasingState() {
        return GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.HEAT_VENT);
    }

    private IBlockState getPipeCasingState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.Hdcs;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.CRYOGENIC_REACTOR_OVERLAY;
    }
}
