package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import javax.annotation.Nonnull;


import static gregtech.api.util.RelativeDirection.*;
import static keqing.gtqtcore.common.block.blocks.GTQTIsaCasing.CasingType.SEPARATOR_ROTOR;

public class MetaTileEntityGravitySeparator extends RecipeMapMultiblockController {
    public MetaTileEntityGravitySeparator(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.GRAVITY_SEPARATOR_RECIPES);
        this.recipeMapWorkable = new MultiblockRecipeLogic(this);
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityGravitySeparator(this.metaTileEntityId);
    }
    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("C   C", "C   C", "CCCCC", "FCCCF", "FCCCF", "FCCCF", "CCCCC")
                .aisle("F   F", "F   F", "F   F", "CCCCC", "C###C", "CRRRC", "C###C")
                .aisle("F   F", "F   F", "F   F", "CCCCC", "CRRRC", "C###C", "C###C")
                .aisle("C   C", "C   C", "CCCCC", "CRRRC", "C###C", "C###C", "     ")
                .aisle("C   C", "C   C", "CCCCC", "C###C", "CRRRC", "C   C", "     ")
                .aisle("F   F", "FCCCF", "C###C", "RRRRR", "C###C", "C   C", "     ")
                .aisle("C   C", "C   C", "CCCCC", "CCSCC", " CCC ", " CCC ", "     ")
                .where('S', selfPredicate())
                .where('R', states(steelRotorState()))
                .where('C', states(getCasingState()).setMinGlobalLimited(95).or(autoAbilities()))
                .where('F', this.getFramePredicate())
                .where('#', air())
                .where(' ', any())
                .build();
    }

    public  TraceabilityPredicate getFramePredicate() {
        return frames(Materials.Steel);
    }
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.SOLID_STEEL_CASING;
    }

    protected IBlockState steelRotorState() {
        return GTQTMetaBlocks.ISA_CASING.getState(SEPARATOR_ROTOR);
    }

    protected static IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
    }

    @Nonnull
    protected ICubeRenderer getFrontOverlay() {
        return Textures.BLAST_FURNACE_OVERLAY;
    }
    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }
    @Override
    public boolean allowsExtendedFacing() {
        return false;
    }
}