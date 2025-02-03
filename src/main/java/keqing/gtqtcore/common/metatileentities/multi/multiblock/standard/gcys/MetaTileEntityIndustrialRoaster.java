package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys;

import gregicality.multiblocks.api.capability.impl.GCYMMultiblockRecipeLogic;
import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.logic.OverclockingLogic;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockFireboxCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.pattern.GTQTTraceabilityPredicate;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.NoCoilTemperatureProperty;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static gregtech.api.GTValues.EV;
import static gregtech.api.GTValues.LV;
import static keqing.gtqtcore.api.GTQTAPI.MAP_FIREBOX_CASING;
import static keqing.gtqtcore.api.GTQTAPI.MAP_PA_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing1.CasingType.MaragingSteel250;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3.CasingType.grisium;

public class MetaTileEntityIndustrialRoaster extends GCYMRecipeMapMultiblockController implements IHeatingCoil {

    int FireBoxTier;
    private int temperature;

    public MetaTileEntityIndustrialRoaster(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.ROASTER_RECIPES);
        this.recipeMapWorkable = new IndustrialRoasterRecipeLogic(this);
    }
    public static class IndustrialRoasterRecipeLogic extends GCYMMultiblockRecipeLogic {

        public IndustrialRoasterRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        protected void modifyOverclockPre(@NotNull int[] values, @NotNull IRecipePropertyStorage storage) {
            super.modifyOverclockPre(values, storage);
            values[0] = OverclockingLogic.applyCoilEUtDiscount(values[0], ((IHeatingCoil) this.metaTileEntity).getCurrentTemperature(), storage.getRecipePropertyValue(TemperatureProperty.getInstance(), 0));
        }

        protected @NotNull int[] runOverclockingLogic(@NotNull IRecipePropertyStorage propertyStorage, int recipeEUt, long maxVoltage, int duration, int amountOC) {
            return OverclockingLogic.heatingCoilOverclockingLogic(Math.abs(recipeEUt), maxVoltage, duration, amountOC, ((IHeatingCoil) this.metaTileEntity).getCurrentTemperature(), propertyStorage.getRecipePropertyValue(TemperatureProperty.getInstance(), 0));
        }

    }
    private static IBlockState getBoilerCasingState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE);
    }

    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(GTQTMaterials.Talonite).getBlock(GTQTMaterials.Talonite);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityIndustrialRoaster(metaTileEntityId);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object FireBoxTier = context.get("FireboxCasingTieredStats");
        this.FireBoxTier = GTQTUtil.getOrDefault(() -> FireBoxTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) FireBoxTier).getIntTier(),
                0);

        this.temperature = this.FireBoxTier * 900 + 900;
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe,
                               boolean consumeIfSuccess) {
        return this.temperature >= recipe.getProperty(NoCoilTemperatureProperty.getInstance(), 0);
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("     ", "     ", " P P ", " P P ", " P P ")
                .aisle("F   F", "FBBBF", "XPXPX", "XXXXX", " P P ")
                .aisle("     ", "XBBBX", "XP#PX", "XPMPX", " P P ")
                .aisle("F   F", "FBBBF", "XXSXX", "XXXXX", "     ")
                .where('S', this.selfPredicate())
                .where('X', states(getCasingState())
                        .setMinGlobalLimited(14)
                        .or(autoAbilities(true, true, true, true, true, true, false)))
                .where('P', states(getBoilerCasingState()))
                .where('F', states(getFrameState()))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('B', TiredTraceabilityPredicate.FIREBOX_CASING.get())
                .where('#', air())
                .where(' ', any())
                .build();
    }
    protected IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing1.getState(MaragingSteel250);
    }
    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.MaragingSteel250;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.FRACKER_OVERLAY;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            textList.add(new TextComponentTranslation("gregtech.multiblock.blast_furnace.max_temperature",
                    TextFormatting.RED + TextFormattingUtil.formatNumbers(temperature) + "K"));
        }
        super.addDisplayText(textList);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.3"));
    }


    @Override
    public int getCurrentTemperature() {
        return this.temperature;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = super.getDataInfo();
        list.add(new TextComponentTranslation("gregtech.multiblock.blast_furnace.max_temperature",
                TextFormatting.RED + TextFormattingUtil.formatNumbers(temperature) + "K"));
        return list;
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder;
        builder = MultiblockShapeInfo.builder()
                .aisle("     ", "     ", " P P ", " P P ", " P P ")
                .aisle("F   F", "FBBBF", "XPEPX", "XXXXX", " P P ")
                .aisle("     ", "XBBBX", "XP PX", "XPHPX", " P P ")
                .aisle("F   F", "FBBBF", "XISOX", "XLMDX", "     ")
                .where('S', GTQTMetaTileEntities.LARGE_ROASTER, EnumFacing.SOUTH)
                .where('X', getCasingState())
                .where('P', getBoilerCasingState())
                .where('F', getFrameState())
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[EV], EnumFacing.NORTH)
                .where('I', MetaTileEntities.ITEM_IMPORT_BUS[LV], EnumFacing.SOUTH)
                .where('O', MetaTileEntities.ITEM_EXPORT_BUS[LV], EnumFacing.SOUTH)
                .where('L', MetaTileEntities.FLUID_IMPORT_HATCH[LV], EnumFacing.WEST)
                .where('D', MetaTileEntities.FLUID_EXPORT_HATCH[LV], EnumFacing.EAST)
                .where('H', MetaTileEntities.MUFFLER_HATCH[LV], EnumFacing.UP)
                .where(' ', Blocks.AIR.getDefaultState())
                .where('M', () -> ConfigHolder.machines.enableMaintenance ? MetaTileEntities.MAINTENANCE_HATCH : getCasingState(), EnumFacing.NORTH);
        MultiblockShapeInfo.Builder finalBuilder = builder;
        MAP_FIREBOX_CASING.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> ((WrappedIntTired) entry.getValue()).getIntTier()))
                .forEach(entry -> shapeInfo.add(finalBuilder.where('B', entry.getKey()).build()));
        return shapeInfo;
    }
}