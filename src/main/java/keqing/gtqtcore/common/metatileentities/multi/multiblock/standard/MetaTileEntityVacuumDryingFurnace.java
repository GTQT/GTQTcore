package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.capability.impl.HeatingCoilRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTIsaCasing;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.DRYER_RECIPES;

public class MetaTileEntityVacuumDryingFurnace extends MultiMapMultiblockController implements IHeatingCoil {

    private int temperature;

    public MetaTileEntityVacuumDryingFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                DRYER_RECIPES,
                GTQTcoreRecipeMaps.VACUUM_DRYING_FURNACE_RECIPES});
        this.recipeMapWorkable = new HeatingCoilRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity holder) {
        return new MetaTileEntityVacuumDryingFurnace(metaTileEntityId);
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
        if (type instanceof BlockWireCoil.CoilType)
            this.temperature = ((BlockWireCoil.CoilType) type).getCoilTemperature();
        else
            this.temperature = BlockWireCoil.CoilType.CUPRONICKEL.getCoilTemperature();

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

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "CCC", "CCC", "CCC", "XXX")
                .aisle("XXX", "C#C", "C#C", "C#C", "XMX")
                .aisle("XSX", "CCC", "CCC", "CCC", "XXX")
                .where('S', this.selfPredicate())
                .where('X', states(new IBlockState[]{this.getCasingState()})
                        .setMinGlobalLimited(9)
                        .or(this.autoAbilities(true, true, true, true, true, true, false)))
                .where('M', abilities(new MultiblockAbility[]{MultiblockAbility.MUFFLER_HATCH}))
                .where('C', heatingCoils())
                .where('#', air())
                .build();
    }

    @Nonnull
    private static IBlockState getCasingState() {
       return GTQTMetaBlocks.ISA_CASING.getState(GTQTIsaCasing.CasingType.VACUUM_CASING);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.3"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.VACUUM_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return GTQTTextures.VACUUM_DRYING_FURNACE_OVERLAY;
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
        MultiblockShapeInfo.Builder builder = null;
        if (Blocks.AIR != null) {
            builder = MultiblockShapeInfo.builder()
                    .aisle("EEM", "CCC", "CCC", "CCC", "XXX")
                    .aisle("FXD", "C#C", "C#C", "C#C", "XHX")
                    .aisle("ISO", "CCC", "CCC", "CCC", "XXX")
                    .where('S', GTQTMetaTileEntities.VACUUM_DRYING_FURNACE, EnumFacing.SOUTH)
                    .where('X', getCasingState())
                    .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[4], EnumFacing.NORTH)
                    .where('I', MetaTileEntities.ITEM_IMPORT_BUS[4], EnumFacing.SOUTH)
                    .where('O', MetaTileEntities.ITEM_EXPORT_BUS[4], EnumFacing.SOUTH)
                    .where('F', MetaTileEntities.FLUID_IMPORT_HATCH[4], EnumFacing.WEST)
                    .where('D', MetaTileEntities.FLUID_EXPORT_HATCH[4], EnumFacing.EAST)
                    .where('H', MetaTileEntities.MUFFLER_HATCH[1], EnumFacing.UP)
                    .where('#', Blocks.AIR.getDefaultState())
                    .where('M', () -> {
                        return ConfigHolder.machines.enableMaintenance ? MetaTileEntities.MAINTENANCE_HATCH :  GTQTMetaBlocks.ISA_CASING.getState(GTQTIsaCasing.CasingType.VACUUM_CASING);
                    }, EnumFacing.NORTH);
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
}