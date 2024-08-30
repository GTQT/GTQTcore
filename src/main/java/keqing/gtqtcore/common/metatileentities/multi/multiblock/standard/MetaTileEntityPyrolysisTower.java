package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

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
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MetaTileEntityPyrolysisTower extends RecipeMapMultiblockController {

    public MetaTileEntityPyrolysisTower(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.PYROLYSIS_TOWER);
    }


    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPyrolysisTower(metaTileEntityId);
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
                        .or(autoAbilities(true, true, true, true, true, false, false)))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('C', states(getCasingState1()))
                .where('T', states(getCasingState2()))
                .where('F', states(getFrameState()))
                .where('O', states(getCasingState()).or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(8)))
                .where('#', any())
                .build();
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

    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
                return Textures.SOLID_STEEL_CASING;
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