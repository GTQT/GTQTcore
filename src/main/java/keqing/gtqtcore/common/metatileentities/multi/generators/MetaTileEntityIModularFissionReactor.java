package keqing.gtqtcore.common.metatileentities.multi.generators;

import gregicality.multiblocks.api.render.GCYMTextures;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.FuelMultiblockController;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;

public class MetaTileEntityIModularFissionReactor extends FuelMultiblockController {


    public MetaTileEntityIModularFissionReactor(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId,GTQTcoreRecipeMaps.I_MODULAR_FISSION_REACTOR, 5);

    }
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityIModularFissionReactor(metaTileEntityId, 5);
    }


    @Nonnull
    @Override
    protected  BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("MMMMMMM","MMMMMMM","MMMMMMM","MMMMMMM","MMMMMMM","MMMMMMM","MMMMMMM")
                .aisle("MMMMMMM","MC   CM","MC   CM","MC   CM","MC   CM","MC   CM","MMMMMMM")
                .aisle("MMMMMMM","M     M","M ABA M","M ABA M","M ABA M","M     M","MMMMMMM")
                .aisle("MMMMMMM","M     M","M BAB M","M BAB M","M BAB M","M     M","MMMMMMM")
                .aisle("MMMMMMM","M     M","M ABA M","M ABA M","M ABA M","M     M","MMMMMMM")
                .aisle("MMMMMMM","MC   CM","MC   CM","MC   CM","MC   CM","MC   CM","MMMMMMM")
                .aisle("MMEFIMM","MMMMMMM","MMMMMMM","MMMMMMM","MMMMMMM","MMMMMMM","MMMMMMM")
                .where('F', selfPredicate())
                .where('M', states(getCasingState()).setMinGlobalLimited(200)
                        .or(autoAbilities(false, true, true, true, true, true, false)))
                .where('E', abilities(MultiblockAbility.OUTPUT_ENERGY))
                .where('I', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where(' ', any())
                .where('C', states(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TITANIUM_PIPE)))
                .where('B', states(MetaBlocks.FRAMES.get(Materials.TungstenSteel).getBlock(Materials.TungstenSteel)))
                .where('A', states(MetaBlocks.FRAMES.get(Materials.HSSE).getBlock(Materials.HSSE)))
                .build();
    }
    private static IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST);
    }
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.ROBUST_TUNGSTENSTEEL_CASING;
    }
    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return GCYMTextures.ALLOY_BLAST_SMELTER_OVERLAY;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    protected boolean shouldShowVoidingModeButton() {
        return false;
    }


}