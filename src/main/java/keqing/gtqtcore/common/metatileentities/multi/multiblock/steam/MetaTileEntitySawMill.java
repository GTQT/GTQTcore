package keqing.gtqtcore.common.metatileentities.multi.multiblock.steam;

import gregtech.api.capability.impl.SteamMultiWorkable;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapSteamMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockSteamCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.wood.BlockGregPlanks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class MetaTileEntitySawMill extends RecipeMapSteamMultiblockController {

    public MetaTileEntitySawMill(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.SAW_MILL,CONVERSION_RATE);
        this.recipeMapWorkable = new SteamMultiWorkable(this, CONVERSION_RATE);
        this.recipeMapWorkable.setParallelLimit(8);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntitySawMill(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("PPPPP", "    F", "    F")
                .aisle("PXXXP", "XX XF", "FFFFF")
                .aisle("PXXXP", "XX XF", " F  F")
                .aisle("PXXXP", "XX XF", "FFFFF")
                .aisle("PSPPP", "    F", "    F")
                .where('S', selfPredicate())
                .where('P', states(MetaBlocks.STEAM_CASING.getState(BlockSteamCasing.SteamCasingType.WOOD_WALL)))
                .where('F', states(MetaBlocks.FRAMES.get(Materials.TreatedWood).getBlock(Materials.TreatedWood)))
                .where('X', states(MetaBlocks.PLANKS.getState(BlockGregPlanks.BlockType.TREATED_PLANK))
                        .or(autoAbilities(true, false, true, true, false)))
                .where(' ', any())
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.WOOD_WALL;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.BLOWER_OVERLAY;
    }

    public boolean hasMaintenanceMechanics() {
        return false;
    }

    public boolean hasMufflerMechanics() {
        return false;
    }
}