package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys;


import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.PolymerProperty;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.*;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockGCYSMultiblockCasing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.List;

public class MetaTileEntityEXCVD extends RecipeMapMultiblockController {

    public MetaTileEntityEXCVD(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.PLASMA_CVD_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityEXCVD(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" K        K ", " K        K ", " K        K ", " K        K "," K        K "," K        K "," K        K ","            ")
                .aisle("NK   KK   KN", "NKKKKKKKKKKN", "NNSSSSSSSSNN", "NNSSSSSSSSNN","NNSSSSSSSSNN","NKSSSSSSSSKN","NKSSSSSSSSKN","NG        GN")
                .aisle(" K        K ", " KKKKKKKKKK ", " NSEEEEEESN ", " NS      SN "," NS      SN "," KJJJJJJJJK "," KSSSSSSSSK "," G        G ")
                .aisle(" K        K ", " KKKKKKKKKK ", " NSEEEEEESN ", " NS      SN "," NS      SN "," KS      SK "," KSSSSSSSSK "," G        G ")
                .aisle(" K        K ", " KKKKKKKKKK ", " NSEEEEEESN ", " NS      SN "," NS      SN "," KJJJJJJJJK "," KSSSSSSSSK "," G        G ")
                .aisle("NK   KK   KN", "NKKKKKKKKKKN", "NNSSSSSSSSNN", "NNSSSSSSSSNN","NNSSSSSSSSNN","NKSSSSSSSSKN","NKSSSSSSSSKN","NG        GN")
                .aisle(" K        K ", " K        K ", " K        K ", " C        K "," K        K "," K        K "," K        K ","            ")

                .where('C', selfPredicate())
                .where(' ', any())
                .where('N', getFramePredicate())
                .where('S', states(MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS)))
                .where('G', states(GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.HEAT_VENT)))
                .where('J', states(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.POLYTETRAFLUOROETHYLENE_PIPE)))
                .where('K', states(GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.ENGRAVER_CASING))
                        .or(autoAbilities(true, true, true, true, true, true, false)))
                .where('E', states(GTQTMetaBlocks.MULTIBLOCK_CASING.getState(BlockGCYSMultiblockCasing.CasingType.ADVANCED_SUBSTRATE)))
                .build();
    }

    private TraceabilityPredicate getFramePredicate() {
            return frames(Materials.NaquadahAlloy);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GCYMTextures.ENGRAVER_CASING;
    }


    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay(){
        return GCYMTextures.ALLOY_BLAST_SMELTER_OVERLAY;
    }
}
