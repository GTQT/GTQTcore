package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys;

import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.capability.IPressureContainer;
import keqing.gtqtcore.api.capability.IPressureMachine;
import keqing.gtqtcore.api.capability.impl.AtmosphericPressureContainer;
import keqing.gtqtcore.api.capability.impl.PressureMultiblockRecipeLogic;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockPCBFactoryCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;

public class MetaTileEntityCVDUnit extends GCYMRecipeMapMultiblockController implements IPressureMachine {

    private IPressureContainer container;

    public MetaTileEntityCVDUnit(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.CVD_RECIPES);
        this.recipeMapWorkable = new PressureMultiblockRecipeLogic(this);
    }

    private static IBlockState getCasingState() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.NONCONDUCTING_CASING);
    }

    private static IBlockState getSubstrateState() {
        return GTQTMetaBlocks.blockPCBFactoryCasing.getState(BlockPCBFactoryCasing.PCBFactoryCasingType.SUBSTRATE_CASING);
    }

    private static IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        List<IPressureContainer> list = getAbilities(GTQTMultiblockAbility.PRESSURE_CONTAINER);
        if (list.isEmpty()) {
            this.container = new AtmosphericPressureContainer(this, 1.0);
        } else {
            this.container = list.get(0);
        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityCVDUnit(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXXX", "XGGGX", "XGGGX")
                .aisle("XXXXX", "XCCCX", "XGGGX").setRepeatable(3)
                .aisle("XXXXX", "SGGGX", "XGGGX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(35)
                        .or(autoAbilities())
                        .or(abilities(GTQTMultiblockAbility.PRESSURE_CONTAINER).setExactLimit(1))
                )
                .where('G', states(getGlassState()))
                .where('C', states(getSubstrateState()))
                .build();
    }

    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return GTQTTextures.CVD_UNIT_OVERLAY;
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GCYMTextures.NONCONDUCTING_CASING;
    }

    @Override
    public IPressureContainer getPressureContainer() {
        return this.container;
    }
}
