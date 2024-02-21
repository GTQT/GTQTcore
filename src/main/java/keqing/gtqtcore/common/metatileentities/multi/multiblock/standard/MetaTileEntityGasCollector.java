package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregicality.multiblocks.api.capability.IParallelMultiblock;
import gregtech.api.GTValues;
import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.GasCollectorDimensionProperty;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockMultiblockCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import it.unimi.dsi.fastutil.ints.IntLists;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.overwrite.MetaTileEntityPyrolyseOven;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static gregtech.api.GTValues.VA;

public class MetaTileEntityGasCollector extends RecipeMapMultiblockController {

    public MetaTileEntityGasCollector(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.GAS_COLLECTOR_RECIPES);
        this.recipeMapWorkable = new GasCollectorWorkableHandler(this);
    }
    private class GasCollectorWorkableHandler extends MultiblockRecipeLogic {

        public GasCollectorWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public int getParallelLimit() {
            return clean_tier;
        }

        @Override
        public void setMaxProgress(int maxProgress)
        {
            this.maxProgressTime = maxProgress/clean_tier;
        }
    }
    private int clean_tier;
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityGasCollector(metaTileEntityId);
    }

    protected boolean checkRecipe( Recipe recipe) {
        for (int dimension : recipe.getProperty(GasCollectorDimensionProperty.getInstance(), IntLists.EMPTY_LIST)) {
            if (dimension == 0) {
                return true;
            }
            if (dimension == this.getWorld().provider.getDimension()) {
                return true;
            }
        }
        return false;
    }


    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX","XXX","XXX","XXX","XXX", "AAA", "AAA")
                .aisle("XXX", "XJX","XJX","XJX","XJX","XJX", "AJA", "AAA")
                .aisle("XXX", "XSX","XXX","XXX","XXX","XXX", "AAA", "AAA")
                .where('S', selfPredicate())
                .where('A', states(getIntakeState()))
                .where('X', states(getCasingAState()).setMinGlobalLimited(15)
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.MUFFLER_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setExactLimit(1)))
                .where('J', TiredTraceabilityPredicate.CP_ZJ_CASING)
                .where(' ', any())
                .build();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object clean_tier = context.get("ZJTiredStats");
        this.clean_tier = GTQTUtil.getOrDefault(() -> clean_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)clean_tier).getIntTier(),
                0);
    }

    public IBlockState getIntakeState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE);
    }
    private IBlockState getCasingAState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
    }

    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
                return Textures.SOLID_STEEL_CASING;
    }


    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.POWER_SUBSTATION_OVERLAY;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

}