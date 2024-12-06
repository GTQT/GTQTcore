package keqing.gtqtcore.common.metatileentities.multi.multiblock.steam;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMachineCasing.MachineCasingType;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class MetaTileEntitySteamFermentationVat extends RecipeMapMultiblockController {

    public MetaTileEntitySteamFermentationVat(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.FERMENTING_RECIPES);
        this.recipeMapWorkable = new SteamFermentationVatWorkableHandler(this);
    }
    protected class SteamFermentationVatWorkableHandler extends MultiblockRecipeLogic {

        public boolean checkRecipe( Recipe recipe) {
            return true;
        }

        public SteamFermentationVatWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }
        public long getMaxVoltage() {
            return 30;
        }
        @Override
        public int getParallelLimit() {
            return 8;
        }
        @Override
        public void setMaxProgress(int maxProgress)
        {
            this.maxProgressTime = maxProgress*4;
        }
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntitySteamFermentationVat(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("     ", "     ", " XXX ", " XXX ", " XXX ", "     ")
                .aisle(" F F ", " XXX ", "X###X", "X###X", "X###X", " XXX ")
                .aisle("     ", " XXX ", "X###X", "X###X", "X###X", " XMX ")
                .aisle(" F F ", " XXX ", "X###X", "X###X", "X###X", " XXX ")
                .aisle("     ", "     ", " XXX ", " XSX ", " XXX ", "     ")
                .where('S', selfPredicate())
                .where('X', states(MetaBlocks.MACHINE_CASING.getState(MachineCasingType.ULV))
                        .setMinGlobalLimited(40)
                        .or(autoAbilities(true, false, true, true, true, true, false)))
                .where('F', states(MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel)))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where(' ', any())
                .where('#', air())
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.VOLTAGE_CASINGS[0];
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.PYROLYSE_OVEN_OVERLAY;
    }

    public boolean hasMaintenanceMechanics() {
        return false;
    }

    public boolean hasMufflerMechanics() {
        return true;
    }
}