package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys;


import gregicality.science.api.recipes.recipeproperties.NoCoilTemperatureProperty;
import gregicality.science.client.render.GCYSTextures;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.*;
import gregtech.api.recipes.Recipe;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockFireboxCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MetaTileEntityRoaster extends RecipeMapMultiblockController implements IHeatingCoil {

    private int temperature;

    public MetaTileEntityRoaster(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.ROASTER_RECIPES);
    };

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityRoaster(metaTileEntityId);
    };

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.temperature = 0;
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return this.temperature >= recipe.getProperty(NoCoilTemperatureProperty.getInstance(), 0);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("#######", "#######", "#######", "#######", "#P###P#", "#P###P#", "#P###P#", "#P###P#")
                .aisle("#R###R#", "#R###R#", "#CCCCC#", "#CCCCC#", "#PCFCP#", "#CCCCC#", "#######", "#P###P#")
                .aisle( "#######", "#######", "#CCCCC#", "CGGGGGC", "CGG#GGC", "CGGGGGC", "#CCCCC#", "#P###P#")
                .aisle( "#######", "#######", "#CCECC#", "CGGGGGO", "I#####O", "CGGGGGO", "#PCMCP#", "#######")
                .aisle("#######", "#######", "#CCCCC#", "CGGGGGC", "CGGGGGC", "CGGGGGC", "#CCCCC#", "#######")
                .aisle("#R###R#", "#R###R#", "#CCCCC#", "#CCHCC#", "#CC~CC#", "#CCCCC#", "#######", "#######")
                .where('~', selfPredicate())
                .where('#', any())
                .where('P', states(getPipeStates()))
                .where('C', states(getCasingState()))
                .where('G', heatingCoils())
                .where('I', abilities(MultiblockAbility.IMPORT_ITEMS))
                .where('O', states(getCasingState())
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS)).setPreviewCount(1)
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS)).setPreviewCount(1))
                .where('F', abilities(MultiblockAbility.IMPORT_FLUIDS).setPreviewCount(1))
                .where('R', getFramePredicate())
                .where('E', abilities(MultiblockAbility.INPUT_ENERGY))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH).setPreviewCount(1))
                .where('H', abilities(MultiblockAbility.MAINTENANCE_HATCH))
                .build();
    }

    private IBlockState getPipeStates() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE);
    }

    private IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF);
    }

    @Nonnull
    private TraceabilityPredicate getFramePredicate() {
        return frames(Materials.Invar);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()){
            textList.add(new TextComponentTranslation("gregtech.multiblock.blast_furnace.max_temperature",
                    TextFormatting.RED + TextFormattingUtil.formatNumbers(temperature) + "K"));
        }
        super.addDisplayText(textList);
    }

    @Override
    public int getCurrentTemperature() {
        return this.temperature;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GCYSTextures.ROASTER_OVERLAY;
    }

    @Override
    public boolean hasMufflerMechanics(){
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart){
        return Textures.HEAT_PROOF_CASING;
    }
}
