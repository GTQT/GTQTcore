package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys;

import gregtech.api.capability.IHeatingCoil;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.*;
import gregtech.api.recipes.Recipe;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.*;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.pattern.GTQTTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.NoCoilTemperatureProperty;
import keqing.gtqtcore.client.textures.GTQTTextures;
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
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static gregtech.api.GTValues.EV;
import static gregtech.api.GTValues.LV;

public class MetaTileEntityIndustrialRoaster extends RecipeMapMultiblockController implements IHeatingCoil {


    private int temperature;

    public MetaTileEntityIndustrialRoaster(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.ROASTER_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityIndustrialRoaster(metaTileEntityId);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CasingType");
        if (type instanceof BlockFireboxCasing.FireboxCasingType)
            this.temperature = ((BlockFireboxCasing.FireboxCasingType) type).ordinal() * 1000 + 1000;
        else this.temperature = 0;
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.temperature = 0;
        replaceFireboxAsActive(false);
    }

    @Override
    public void onRemoval() {
        super.onRemoval();
        if (!getWorld().isRemote && isStructureFormed()) {
            replaceFireboxAsActive(false);
        }
    }

    public void replaceFireboxAsActive(boolean isActive) {
        BlockPos centerPos = getPos().offset(getFrontFacing().getOpposite()).down();
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos blockPos = centerPos.add(x, 0, z);
                IBlockState blockState = getWorld().getBlockState(blockPos);
                if (blockState.getBlock() instanceof BlockFireboxCasing) {
                    blockState = ((IExtendedBlockState) blockState).withProperty(BlockFireboxCasing.ACTIVE, isActive);
                    getWorld().setBlockState(blockPos, blockState);
                }
            }
        }
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
                .where('B', GTQTTraceabilityPredicate.FIRE_BOX.get())
                .where('#', air())
                .where(' ', any())
                .build();
    }

    private static IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TITANIUM_STABLE);
    }

    private static IBlockState getBoilerCasingState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TITANIUM_PIPE);
    }


    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.StainlessSteel).getBlock(Materials.StainlessSteel);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.STABLE_TITANIUM_CASING;
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
    public void addInformation(ItemStack stack, @Nullable World world,List<String> tooltip,
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
        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
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
        return Arrays.stream(BlockFireboxCasing.FireboxCasingType.values())
                .map(casingType -> builder.where('B', MetaBlocks.BOILER_FIREBOX_CASING.getState(casingType)).build())
                .collect(Collectors.toList());
    }
}