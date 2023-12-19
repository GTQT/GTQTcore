package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregicality.science.common.block.GCYSMetaBlocks;
import gregicality.science.common.block.blocks.BlockTransparentCasing;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTIsaCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class MetaTileEntityFlotationFactory extends RecipeMapMultiblockController {

    public MetaTileEntityFlotationFactory(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.FLOTATION_FACTORY_RECIPES);
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
        return GCYSMetaBlocks.TRANSPARENT_CASING.getState(BlockTransparentCasing.CasingType.PMMA);
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