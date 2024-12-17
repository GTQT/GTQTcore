package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.capability.IHeat;
import keqing.gtqtcore.api.capability.impl.HeatRecipeLogic;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.metaileentity.multiblock.RecipeMapHeatMultiblockController;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.HeatProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class MetaTileEntityPyrolysisTower extends RecipeMapHeatMultiblockController {

    public MetaTileEntityPyrolysisTower(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.PYROLYSIS_TOWER);
        this.recipeMapWorkable = new HeatRecipeLogic(this, GTQTcoreRecipeMaps.PYROLYSIS_TOWER);
    }

    private static IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
    }

    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel);
    }

    private static IBlockState getCasingState1() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PRIMITIVE_BRICKS);
    }

    private static IBlockState getCasingState2() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPyrolysisTower(metaTileEntityId);
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        setTier(getHeatHatch().getTier());
    }

    @Override
    public void updateFormedValid() {
        super.updateFormedValid();
        setHeat((int) getHeatHatch().getHeat());
    }

    public IHeat getHeatHatch() {
        List<IHeat> abilities = getAbilities(GTQTMultiblockAbility.HEAT_MULTIBLOCK_ABILITY);
        if (abilities.isEmpty())
            return null;
        return abilities.get(0);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("FCMCF", "FCCCF", "FCOCF", "FCOCF", "FCOCF", "FXOXF", "FXOXF", "FXOXF", "FXOXF", "FXOXF", "FFFFF", "#####", "#####", "#####", "#####", "#####", "#####", "#####", "#####")
                .aisle("CCCCC", "C###C", "CT#TC", "C###C", "C###C", "C###C", "CCCCC", "X###X", "X#T#X", "XXXXX", "F###F", "#####", "#####", "#####", "#####", "#####", "#####", "#####", "#####")
                .aisle("CCCCC", "C#T#C", "CTTTC", "C#T#C", "C#T#C", "C#T#C", "CCTCC", "XTTTX", "X#T#X", "XXXXX", "F###F", "#####", "#####", "#####", "#####", "#####", "#####", "#####", "#####")
                .aisle("CCCCC", "C###C", "CT#TC", "C###C", "C###C", "C###C", "CCCCC", "X###X", "X#T#X", "XXXXX", "FFFFF", "F#F#F", "F#F#F", "F#F#F", "FFFFF", "F#F#F", "F#F#F", "F#F#F", "FFFFF")
                .aisle("FCCCF", "FCCCF", "FTCTF", "FTCTF", "FTCTF", "FC#CF", "FCCCF", "FCCCF", "FCCCF", "FCCCF", "FCCCF", "#CCC#", "#CCC#", "#CCC#", "FCCCF", "#CCC#", "#CCC#", "#CCC#", "FCCCF")
                .aisle("XXXXX", "XXXXX", "XXSXX", "XXXXX", "XXXXX", "#C#C#", "#C#C#", "#C#C#", "#C#C#", "#C#C#", "FC#CF", "#C#C#", "#C#C#", "#C#C#", "FC#CF", "#C#C#", "#C#C#", "#C#C#", "FC#CF")
                .aisle("XXXXX", "#####", "#####", "#####", "XXXXX", "#CCC#", "#CCC#", "#CCC#", "#CCC#", "#CCC#", "FCCCF", "#CCC#", "#CCC#", "#CCC#", "FCCCF", "#CCC#", "#CCC#", "#CCC#", "FCCCF")
                .aisle("FXXXF", "F###F", "F###F", "F###F", "FFFFF", "F#F#F", "F#F#F", "F#F#F", "F#F#F", "F#F#F", "FFFFF", "F#F#F", "F#F#F", "F#F#F", "FFFFF", "F#F#F", "F#F#F", "F#F#F", "FFFFF")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(50)
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setExactLimit(1))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(GTQTMultiblockAbility.HEAT_MULTIBLOCK_ABILITY).setExactLimit(1))
                )
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('C', states(getCasingState1()))
                .where('T', states(getCasingState2()))
                .where('F', states(getFrameState()))
                .where('O', states(getCasingState()).or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(8)))
                .where('#', any())
                .build();
    }

    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }
}