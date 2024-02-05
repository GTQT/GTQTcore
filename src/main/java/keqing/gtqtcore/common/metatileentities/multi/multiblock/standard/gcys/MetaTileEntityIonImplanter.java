package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys;

import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;

import gregicality.science.common.block.GCYSMetaBlocks;
import gregicality.science.common.block.blocks.BlockGCYSMultiblockCasing;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class MetaTileEntityIonImplanter extends RecipeMapMultiblockController {

    public MetaTileEntityIonImplanter(ResourceLocation metaTileEntityId){
        super(metaTileEntityId, GTQTcoreRecipeMaps.ION_IMPLANTATOR_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityIonImplanter(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.BACK, RelativeDirection.UP, RelativeDirection.RIGHT)
                .aisle("AAAAAAAA ", "AAAAAAAA ", "AAAAAAAA ","AAAAAAAA ", "AAAAAAAA ", "         " )
                .aisle("AAAAAAAAA", "A BBB AA ", "A B B AA ", "A BBB AA ", "A     A  ", "AAACAA   ")
                .aisle("AAAAAAAAA", "A BBB   A", "A B B   A", "A BBB   A", "A     AA ", "AAACAA   ")
                .aisle("AAAAAAAAA", "A BBB   C", "A B B   C", "A BBB   C", "A     AAA", "AAACAA   ")
                .aisle("AAAAAAAAA", "A BBB   C", "A B B   C", "A BBB   C", "A     AAA", "AAACAA   ")
                .aisle("AAAAAAAAA", "A BBB  AA", "A B B  AA", "A BBB  AA", "A     AA ", "AAACAA   ")
                .aisle("AAAAAAAAA", "A BBB AA ", "A B B AA ", "A BBB AA ", "A     A  ", "AAACAA   ")
                .aisle("AAAAAAAAA", "ADDDDDAA ", "ADDDDDA~ ", "ADDDDDAA ", "ADDDDDA  ", "AAACAA   ")
                .aisle("AAAAAAAAA", "A BBB AA ", "A B B AA ", "A BBB AA ", "A     A  ", "AAACAA   ")
                .aisle("AAAAAAAAA", "A BBB  AA", "A B B  AA", "A BBB  AA", "A     AA ", "AAACAA   ")
                .aisle("AAAAAAAAA", "A BBB   C", "A B B   C", "A BBB   C", "A     AAA", "AAACAA   ")
                .aisle("AAAAAAAAA", "A BBB   C", "A B B   C", "A BBB   C", "A     AAA", "AAACAA   ")
                .aisle("AAAAAAAAA", "A BBB  AA", "A B B  AA", "A BBB  AA", "A     AA ", "AAACAA   ")
                .aisle("AAAAAAAAA", "A BBB AA ", "A B B AA ", "A BBB AA ", "A     A  ", "AAACAA   ")
                .aisle("AAAAAAAA ", "AAAAAAAA ", "AAAAAAAA ","AAAAAAAA ", "AAAAAAAA ", "         " )
                .where('~', selfPredicate())
                .where('A', states(getCasingState()) .or(autoAbilities(true, true, true, true, true, true, false)))
                .where('B', states(getElectrolicState()))
                .where('D', states(getSuperHeavyCasingState()))
                .where('C', states(MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS)))
                .build();
    }

    private IBlockState getCasingState() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.ENGRAVER_CASING);
    }

    private IBlockState getElectrolicState() {
        return GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.ELECTROLYTIC_CELL);
    }

    private IBlockState getSuperHeavyCasingState() {
       return GCYSMetaBlocks.MULTIBLOCK_CASING.getState(BlockGCYSMultiblockCasing.CasingType.SUPERHEAVY_QUANTUM_CASING);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GCYMTextures.ENGRAVER_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay(){
        return Textures.FUSION_REACTOR_OVERLAY;
    }
}
