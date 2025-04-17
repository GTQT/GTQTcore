package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys;

import gregicality.multiblocks.api.capability.impl.GCYMMultiblockRecipeLogic;
import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;
import gregtech.api.GTValues;
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
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil.CoilType;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static gregtech.api.unification.material.Materials.TungstenSteel;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3.CasingType.grisium;

public class MetaTileEntityCrystallizationCrucible extends GCYMRecipeMapMultiblockController implements IHeatingCoil {

    private int temperature;

    public MetaTileEntityCrystallizationCrucible(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.CRYSTALLIZER_RECIPES);
        this.recipeMapWorkable = new CrystallizationCrucibleRecipeLogic(this);
    }


    @Nonnull
    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(TungstenSteel).getBlock(TungstenSteel);
    }

    @Nonnull
    private static IBlockState getVentState() {
        return GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.HEAT_VENT);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity holder) {
        return new MetaTileEntityCrystallizationCrucible(metaTileEntityId);
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
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CoilType");
        if (type instanceof CoilType)
            this.temperature = ((CoilType) type).getCoilTemperature();
        else
            this.temperature = CoilType.CUPRONICKEL.getCoilTemperature();

        this.temperature += 100 * Math.max(0, GTUtility.getTierByVoltage(getEnergyContainer().getInputVoltage()) - GTValues.MV);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.temperature = 0;
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return this.temperature >= recipe.getProperty(TemperatureProperty.getInstance(), 0);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXXX", "G###G", "G###G", "XXXXX")
                .aisle("XXXXX", "#VCV#", "#VCV#", "XXXXX")
                .aisle("XXXXX", "#CAC#", "#CAC#", "XXMXX")
                .aisle("XXXXX", "#VCV#", "#VCV#", "XXXXX")
                .aisle("XXSXX", "G###G", "G###G", "XXXXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState1()).setMinGlobalLimited(32)
                        .or(autoAbilities(true, true, true, true, true, false, false)))
                .where('C', heatingCoils())
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('G', states(getFrameState()))
                .where('V', states(getVentState()))
                .where('A', air())
                .where('#', any())
                .build();
    }

    protected IBlockState getCasingState1() {
        return GTQTMetaBlocks.blockMultiblockCasing3.getState(grisium);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.3"));
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.grisium;
    }

    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return GTQTTextures.CRYSTALLIZATION_CRUCIBLE_OVERLAY;
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
        return this.temperature;
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle("XXEXX", "G###G", "G###G", "XXXXX")
                .aisle("XXXXX", "#VCV#", "#VCV#", "XXXXX")
                .aisle("XXXXX", "#C#C#", "#C#C#", "XXHXX")
                .aisle("XXXXX", "#VCV#", "#VCV#", "XXXXX")
                .aisle("IOSMF", "G###G", "G###G", "XXXXX")
                .where('S', GTQTMetaTileEntities.LARGE_CRYSTALLIZATION_CRUCIBLE, EnumFacing.SOUTH)
                .where('X', getCasingState1())
                .where('G', MetaBlocks.FRAMES.get(TungstenSteel).getBlock(TungstenSteel))
                .where('V', GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.HEAT_VENT))
                .where('#', Blocks.AIR.getDefaultState())
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.HV], EnumFacing.NORTH)
                .where('I', MetaTileEntities.ITEM_IMPORT_BUS[GTValues.LV], EnumFacing.SOUTH)
                .where('O', MetaTileEntities.ITEM_EXPORT_BUS[GTValues.LV], EnumFacing.SOUTH)
                .where('F', MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.LV], EnumFacing.SOUTH)
                .where('H', MetaTileEntities.MUFFLER_HATCH[GTValues.LV], EnumFacing.UP)
                .where('M', () -> ConfigHolder.machines.enableMaintenance ? MetaTileEntities.MAINTENANCE_HATCH : MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TITANIUM_STABLE), EnumFacing.SOUTH);
        Arrays.stream(CoilType.values())
                .sorted(Comparator.comparingInt(CoilType::getLevel))
                .forEach(coilType -> shapeInfo.add(builder.where('C', MetaBlocks.WIRE_COIL.getState(coilType)).build()));
        return shapeInfo;
    }

    public static class CrystallizationCrucibleRecipeLogic extends GCYMMultiblockRecipeLogic {

        public CrystallizationCrucibleRecipeLogic(RecipeMapMultiblockController tileEntity) {
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
}
