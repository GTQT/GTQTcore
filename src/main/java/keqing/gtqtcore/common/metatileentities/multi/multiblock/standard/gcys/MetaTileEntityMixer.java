package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys;

import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockTurbineCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing1;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MetaTileEntityMixer extends GCYMRecipeMapMultiblockController {

    public MetaTileEntityMixer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.LARGE_MIXER_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityMixer(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" CCC ", " CCC ", " CCC ", " CCC ", " CCC ")
                .aisle("CCCCC", "C#G#C", "C#G#C", "CGGGC", " CCC ")
                .aisle("CCCCC", "CGGGC", "CGGGC", "CGGGC", " CCC ")
                .aisle("CCCCC", "C#G#C", "C#G#C", "CGGGC", " CCC ")
                .aisle(" CCC ", " CSC ", " CCC ", " CCC ", " CCC ")
                .where('S', this.selfPredicate())
                .where('C', states(this.getCasingState())
                        .setMinGlobalLimited(6)
                        .or(this.autoAbilities(true, true, true, true, true, true, false)))
                .where('G', states(this.getGearboxCasingState()))
                .where('#', air())
                .where(' ', any())
                .build();
    }

    private IBlockState getCasingState() {
        return  GTQTMetaBlocks.blockMultiblockCasing1.getState(BlockMultiblockCasing1.CasingType.Staballoy);
    }

    private IBlockState getGearboxCasingState() {
        return MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TITANIUM_GEARBOX);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart texture) {
        return GTQTTextures.Staballoy;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.LARGE_ROCKET_ENGINE_OVERLAY;
    }

}
