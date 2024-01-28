package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.kqcc;

import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.capability.impl.ComputationRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.LASERNetProperty;
import keqing.gtqtcore.api.recipes.properties.StarProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class MetaTileEntityCosmicRayDetector extends RecipeMapMultiblockController  implements IOpticalComputationReceiver {
    int tier=1;
    private IOpticalComputationProvider computationProvider;
    public MetaTileEntityCosmicRayDetector(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.STAR_SURVEY);
        this.recipeMapWorkable = new CosmicRayDetectorLogic(this);
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        List<IOpticalComputationHatch> providers = this.getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION);
        if (providers != null && providers.size() >= 1) {
            this.computationProvider = (IOpticalComputationProvider)providers.get(0);
        }
    }
    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        if(recipe.getProperty(StarProperty.getInstance(), 0)<=tier)
        {
            return super.checkRecipe(recipe, consumeIfSuccess);
        }
        return false;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCosmicRayDetector(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "      XXX      ",
                        "               ")
                .aisle(
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "      XXX      ",
                        "    XX   XX    ",
                        "               ")
                .aisle(
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "       X       ",
                        "    XXX XXX    ",
                        "   X       X   ",
                        "               ")
                .aisle(
                        "      CCC      ",
                        "      CCC      ",
                        "      CCC      ",
                        "               ",
                        "               ",
                        "       C       ",
                        "     XXXXX     ",
                        "   XX     XX   ",
                        "  X         X  ",
                        "               ")
                .aisle(
                        "     CCCCC     ",
                        "     C   C     ",
                        "     C   C     ",
                        "      CCC      ",
                        "      CCC      ",
                        "     CCCCC     ",
                        "    XXXXXXX    ",
                        "  XX       XX  ",
                        " X           X ",
                        "               ")
                .aisle(
                        "    CCCCCCC    ",
                        "    C     C    ",
                        "    C     C    ",
                        "     C   C     ",
                        "     C   C     ",
                        "    CCXXXCC    ",
                        "   XXX   XXX   ",
                        "  X         X  ",
                        " X           X ",
                        "               ")
                .aisle(
                        "   CCCCCCCCC   ",
                        "   C   E   C   ",
                        "   C       C   ",
                        "    C     C    ",
                        "    C  F  C    ",
                        "    CXXXXXC    ",
                        "   XX     XX   ",
                        " XX         XX ",
                        "X             X",
                        "               ")
                .aisle(
                        "   CCCCCCCCC   ",
                        "   C  ELE  C   ",
                        "   C   L   C   ",
                        "    C  L  C    ",
                        "    C FLF C    ",
                        "   CCXXEXXCC   ",
                        "  XXX  T  XXX  ",
                        " X     T     X ",
                        "X      T      X",
                        "       T       ")
                .aisle(
                        "   CCCCCCCCC   ",
                        "   C   E   C   ",
                        "   C       C   ",
                        "    C     C    ",
                        "    C  F  C    ",
                        "    CXXXXXC    ",
                        "   XX     XX   ",
                        " XX         XX ",
                        "X             X",
                        "               ")
                .aisle(
                        "    CCCCCCC    ",
                        "    C     C    ",
                        "    C     C    ",
                        "     C   C     ",
                        "     C   C     ",
                        "    CCXXXCC    ",
                        "   XXX   XXX   ",
                        "  X         X  ",
                        " X           X ",
                        "               ")
                .aisle(
                        "     CCCCC     ",
                        "     C   C     ",
                        "     C   C     ",
                        "      CCC      ",
                        "      CCC      ",
                        "     CCCCC     ",
                        "    XXXXXXX    ",
                        "  XX       XX  ",
                        " X           X ",
                        "               ")
                .aisle(
                        "      CPC      ",
                        "      CSC      ",
                        "      CCC      ",
                        "               ",
                        "               ",
                        "       C       ",
                        "     XXXXX     ",
                        "   XX     XX   ",
                        "  X         X  ",
                        "               ")
                .aisle(
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "       X       ",
                        "    XXX XXX    ",
                        "   X       X   ",
                        "               ")
                .aisle(
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "      XXX      ",
                        "    XX   XX    ",
                        "               ")
                .aisle(
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "               ",
                        "      XXX      ",
                        "               ")
                .where('S', selfPredicate())
                .where('C', states(getCasingAState()).or(autoAbilities()))
                .where('P', abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION))
                .where('X', states(getFrameState()))
                .where('E', states(getFrameState()))
                .where('F', states(getFrameState()))
                .where('L', states(getFrameState()))
                .where('T', states(getFrameState()))
                .where(' ', any())
                .build();
    }


    private static IBlockState getCasingAState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
    }

    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }

    public IOpticalComputationProvider getComputationProvider() {
        return this.computationProvider;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }
    @SideOnly(Side.CLIENT)
    @Override
    public SoundEvent getSound() {
        return GTSoundEvents.ELECTROLYZER;
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    protected  class CosmicRayDetectorLogic extends ComputationRecipeLogic {
        public CosmicRayDetectorLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity,ComputationType.SPORADIC);
        }



    }
}