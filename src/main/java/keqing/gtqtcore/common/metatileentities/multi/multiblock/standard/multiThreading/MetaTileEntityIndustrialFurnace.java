package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.multiThreading;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.client.renderer.ICubeRenderer;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockCore;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing1;
import keqing.gtqtcore.common.block.blocks.BlockIsaCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static gregtech.api.GTValues.IV;
import static gregtech.api.GTValues.VA;

public class MetaTileEntityIndustrialFurnace extends GTQTMultiblockCore implements IHeatingCoil {

    public MetaTileEntityIndustrialFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.FURNACE_RECIPES,
                GTQTcoreRecipeMaps.REACTION_FURNACE_RECIPES,
                GTQTcoreRecipeMaps.BURNER_REACTOR_RECIPES,
                GTQTcoreRecipeMaps.DRYER_RECIPES,
                RecipeMaps.ALLOY_SMELTER_RECIPES
        });
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing1.getState(BlockMultiblockCasing1.CasingType.Hdcs);
    }

    private static IBlockState getSecondCasingState() {
        return GTQTMetaBlocks.blockIsaCasing.getState(BlockIsaCasing.CasingType.IRIDIUM_TURBINE);
    }

    @Override
    public boolean checkRecipe(Recipe recipe, boolean consumeIfSuccess) {
        return 6000 >= recipe.getProperty(TemperatureProperty.getInstance(), 0);
    }

    @Override
    public int getMinVa() {
        return VA[IV];
    }

    @Override
    public int getCoreNum() {
        return 64;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityIndustrialFurnace(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCC", "CCC", "CCC")
                .aisle("CCC", "CGC", "CCC")
                .aisle("PPP", "PSP", "PPP")
                .where('P', states(getCasingState()))
                .where('S', this.selfPredicate())
                .where('C', states(getCasingState())
                        .setMinGlobalLimited(10)
                        .or(autoAbilities()))
                .where('G', states(getSecondCasingState()))
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.Hdcs;
    }

    @Override
    public void update() {
        super.update();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.recipeMapWorkable.isWorkingEnabled(),
                isActive());
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.BURNER_REACTOR_OVERLAY;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public int getCurrentTemperature() {
        return 6000;
    }
}

