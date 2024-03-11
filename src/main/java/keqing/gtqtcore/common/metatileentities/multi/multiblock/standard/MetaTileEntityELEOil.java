package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.capability.impl.NoEnergyMultiblockRecipeLogic;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import gregtech.common.blocks.BlockBoilerCasing.BoilerCasingType;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;

import javax.annotation.Nonnull;

public class MetaTileEntityELEOil extends RecipeMapMultiblockController {

    public MetaTileEntityELEOil(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.ELEOIL);
        this.recipeMapWorkable = new MultiblockRecipeLogic(this, true);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityELEOil(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("BBB", "BCB", "ACA")
                .aisle("CCC", "CDC", "ACA")
                .aisle("CCC", "CDC", "ACA")
                .aisle("CCC", "CDC", "ACA")
                .aisle("CCC", "CDC", "ACA")
                .aisle("CCC", "CDC", "ACA")
                .aisle("CCC", "CDC", "ACA")
                .aisle("CCC", "CDC", "ACA")
                .aisle("BBB", "BSB", "ACA")
                .where('S', selfPredicate())
                .where('A', states(MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel)))
                .where('B', states(MetaBlocks.METAL_CASING.getState(MetalCasingType.STEEL_SOLID))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1)))
                .where('C', states(MetaBlocks.METAL_CASING.getState(MetalCasingType.STEEL_SOLID))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.MUFFLER_HATCH).setExactLimit(1)))
                .where('D', states(MetaBlocks.BOILER_CASING.getState(BoilerCasingType.STEEL_PIPE)))
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }
}