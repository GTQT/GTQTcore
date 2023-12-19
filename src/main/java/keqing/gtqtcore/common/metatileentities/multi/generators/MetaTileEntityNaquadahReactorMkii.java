package keqing.gtqtcore.common.metatileentities.multi.generators;

import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;
import gregtech.api.GTValues;
import gregtech.api.capability.impl.MultiblockFuelRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.FuelMultiblockController;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static gregtech.api.GTValues.UV;

public class MetaTileEntityNaquadahReactorMkii extends FuelMultiblockController {


    public MetaTileEntityNaquadahReactorMkii(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.NAQUADAH_REACTOR, UV);
        this.recipeMapWorkable = new TurbineCombustionEngineWorkableHandler(this);
        this.recipeMapWorkable.setMaximumOverclockVoltage(GTValues.V[UV]);
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityNaquadahReactorMkii(metaTileEntityId);
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("IIOII","ABBBA","AAAAA","F   F","F   F","AAAAA")
                .aisle("AAAAA","BCCCB","ACCCA"," EEE "," EEE ","AAAAA")
                .aisle("AAAAA","BCDCB","ACDCA"," EDE "," EDE ","AAAAA")
                .aisle("AAAAA","BCCCB","ACCCA"," EEE "," EEE ","AAAAA")
                .aisle("AAXAA","ABBBA","AAAAA","F   F","F   F","AAAAA")
                .where('X', selfPredicate())
                .where('A', states(getCasingState()).setMinGlobalLimited(60)
                        .or(autoAbilities(false, true, true, true, true, true, false)))
                .where('B', states(MetaBlocks.FRAMES.get(Materials.NaquadahAlloy).getBlock(Materials.NaquadahAlloy)))
                .where('D', states(getCasingState1()))
                .where('E', states(getCasingState2()))
                .where('C', heatingCoils())
                .where('F', states(MetaBlocks.FRAMES.get(Materials.NaquadahAlloy).getBlock(Materials.NaquadahAlloy)))
                .where('O', abilities(MultiblockAbility.OUTPUT_ENERGY))
                .where('I', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where(' ', any())
                .build();
    }

    private static IBlockState getCasingState() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.ATOMIC_CASING);
    }

    private static IBlockState getCasingState1() {
        return GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.HEAT_VENT);
    }

    private static IBlockState getCasingState2() {
        return GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.ELECTROLYTIC_CELL);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GCYMTextures.ATOMIC_CASING;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.VACUUM_DRYING_FURNACE_OVERLAY;
    }


    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    protected boolean shouldShowVoidingModeButton() {
        return false;
    }

    private static class TurbineCombustionEngineWorkableHandler extends MultiblockFuelRecipeLogic {


        public TurbineCombustionEngineWorkableHandler(FuelMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public long getMaxVoltage() {
            return GTValues.V[UV];
        }
    }
}