package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys;


import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.capability.impl.HeatingCoilRecipeLogic;
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
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.*;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.List;

public class MetaTileEntityCZPuller extends RecipeMapMultiblockController implements IHeatingCoil {
    private int czpullerTemperarure;

    public MetaTileEntityCZPuller(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.CZPULLER_RECIPES);
        this.recipeMapWorkable = new HeatingCoilRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCZPuller(metaTileEntityId);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            textList.add(new TextComponentTranslation("gregtech.multiblock.blast_furnace.max_temperature",
                    new TextComponentTranslation(TextFormattingUtil.formatNumbers(czpullerTemperarure) + "K").setStyle(new Style().setColor(TextFormatting.DARK_PURPLE))));
        }
        super.addDisplayText(textList);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CoilType");
        if (type instanceof IHeatingCoilBlockStats) {
            this.czpullerTemperarure = ((IHeatingCoilBlockStats) type).getCoilTemperature();
        } else {
            this.czpullerTemperarure = BlockWireCoil.CoilType.CUPRONICKEL.getCoilTemperature();
        }
            this.czpullerTemperarure += 100 * Math.max(0, GTUtility.getFloorTierByVoltage(getEnergyContainer().getInputVoltage()) - GTValues.MV);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.czpullerTemperarure = 0;
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return this.czpullerTemperarure >= recipe.getProperty(TemperatureProperty.getInstance(), 0);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("#XXXXX#", "###I###", "###X###", "###G###", "###G###", "###G###", "###G###", "###G###", "###X###", "#######")
                .aisle("XXXXXXX", "##XCX##", "##XCX##", "##XCX##", "##XCX##", "##XCX##", "##XCX##", "##XCX##", "##XXX##", "###X###")
                .aisle("XXXXXXX", "#XN#NX#", "#XN#NX#", "#XN#NX#", "#XN#NX#", "#XN#NX#", "#XNNNX#", "#XN#NX#", "#XRRRX#", "#XXXXX#")
                .aisle("XXXEXXX", "FC###CO", "XC###CX", "GC###CG", "GC###CG", "GC###CG", "GC###CG", "GCN#NCG", "XXR#RXX", "#XXMXX#")
                .aisle("XXXXXXX", "#XN#NX#", "#XN#NX#", "#XN#NX#", "#XN#NX#", "#XN#NX#", "#XNNNX#", "#XN#NX#", "#XRRRX#", "#XXXXX#")
                .aisle("XXXXXXX", "##XCX##", "##XCX##", "##XCX##", "##XCX##", "##XCX##", "##XCX##", "##XCX##", "##XXX##", "###X###")
                .aisle("#XXKXX#", "###~###", "###X###", "###G###", "###G###", "###G###", "###G###", "###G###", "###X###", "#######")
                .where('~', selfPredicate())
                .where('X', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID)))
                .where('I', abilities(MultiblockAbility.IMPORT_ITEMS))
                .where('F', abilities(MultiblockAbility.IMPORT_FLUIDS))
                .where('O', abilities(MultiblockAbility.EXPORT_ITEMS))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('K', abilities(MultiblockAbility.MAINTENANCE_HATCH))
                .where('E', abilities(MultiblockAbility.INPUT_ENERGY))
                .where('#', any())
                .where('G', states(MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS)))
                .where('N', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF)))
                .where('R', states(MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STEEL_GEARBOX)))
                .where('C', heatingCoils())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @Override
    public int getCurrentTemperature() {
        return this.czpullerTemperarure;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay(){
        return Textures.POWER_SUBSTATION_OVERLAY;
    }
}
