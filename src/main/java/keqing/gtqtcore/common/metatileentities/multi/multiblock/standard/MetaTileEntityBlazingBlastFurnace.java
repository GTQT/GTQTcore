package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregicality.multiblocks.api.metatileentity.GCYMMultiblockAbility;
import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class MetaTileEntityBlazingBlastFurnace extends GCYMRecipeMapMultiblockController implements IHeatingCoil {

    private int blastFurnaceTemperature;
    public MetaTileEntityBlazingBlastFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[] {
                GTQTcoreRecipeMaps.BLAZING_BLAST_FURNACE,
                RecipeMaps.BLAST_RECIPES
        });
    }


    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityBlazingBlastFurnace(this.metaTileEntityId);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            textList.add(new TextComponentTranslation("gregtech.multiblock.blast_furnace.max_temperature", blastFurnaceTemperature)
                    .setStyle(new Style().setColor(TextFormatting.RED)));
        }
        super.addDisplayText(textList);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CoilType");
        if (type instanceof IHeatingCoilBlockStats) {
            this.blastFurnaceTemperature = ((IHeatingCoilBlockStats) type).getCoilTemperature();
        } else {
            this.blastFurnaceTemperature = BlockWireCoil.CoilType.CUPRONICKEL.getCoilTemperature();
        }

        this.blastFurnaceTemperature += 100 * Math.max(0, GTUtility.getTierByVoltage(getEnergyContainer().getInputVoltage()) - GTValues.MV);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.blastFurnaceTemperature = 0;
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return this.blastFurnaceTemperature >= recipe.getProperty(TemperatureProperty.getInstance(), 0);
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "CCC", "CCC", "XXX")
                .aisle("XXX", "C#C", "C#C", "XMX")
                .aisle("XSX", "CCC", "CCC", "XXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState())
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                        .or(abilities(GCYMMultiblockAbility.PARALLEL_HATCH).setMinGlobalLimited(1).setMaxGlobalLimited(1))
                )
                .where('C', heatingCoils())
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('#', air())
                .build();
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = null;
        if (Blocks.AIR != null) {
            builder = MultiblockShapeInfo.builder()
                    .aisle("MIF", "CCC", "CCC", "XXX")
                    .aisle("OXX", "C#C", "C#C", "XMX")
                    .aisle("ESH", "CCC", "CCC", "XXX")
                    .where('S', GTQTMetaTileEntities.BLAZING_BLAST_FURNACE, EnumFacing.SOUTH)
                    .where('X', getCasingState())
                    .where('M', MetaTileEntities.MUFFLER_HATCH[GTValues.HV], EnumFacing.UP)
                    .where('I', MetaTileEntities.ITEM_IMPORT_BUS[GTValues.HV], EnumFacing.SOUTH)
                    .where('F', MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.HV], EnumFacing.SOUTH)
                    .where('O', MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.HV], EnumFacing.SOUTH)
                    .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.EV], EnumFacing.NORTH)
                    .where('H', () -> ConfigHolder.machines.enableMaintenance ? MetaTileEntities.MAINTENANCE_HATCH : getCasingState(), EnumFacing.SOUTH)
                    .where('#', Blocks.AIR.getDefaultState());
        }

        MultiblockShapeInfo.Builder finalBuilder = builder;
        GregTechAPI.HEATING_COILS.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().getTier()))
                .forEach(entry -> {
                    if (finalBuilder != null) {
                        shapeInfo.add(finalBuilder.where('C', entry.getKey()).build());
                    }
                });
        return shapeInfo;
    }

    private static IBlockState getCasingState() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.HIGH_TEMPERATURE_CASING);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.3"));
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GCYMTextures.BLAST_CASING;
    }

    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return GCYMTextures.ALLOY_BLAST_SMELTER_OVERLAY;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public int getCurrentTemperature() {
        return this.blastFurnaceTemperature;
    }
}

