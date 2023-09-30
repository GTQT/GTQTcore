package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregicality.multiblocks.api.render.GCYMTextures;
import gregtech.api.GTValues;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.capability.impl.HeatingCoilRecipeLogic;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntityHugeBlastFurnace extends RecipeMapMultiblockController implements IHeatingCoil {

    private int blastFurnaceTemperature;
    protected int heatingCoilLevel;
    protected int heatingCoilDiscount;
    public MetaTileEntityHugeBlastFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.BLAST_RECIPES);
        this.recipeMapWorkable = new MetaTileEntityHugeBlastFurnaceWorkable(this);
        this.recipeMapWorkable = new HeatingCoilRecipeLogic(this);
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityHugeBlastFurnace(this.metaTileEntityId);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            textList.add(new TextComponentTranslation("gregtech.multiblock.blast_furnace.max_temperature",
                    new TextComponentTranslation(TextFormattingUtil.formatNumbers(blastFurnaceTemperature) + "K").setStyle(new Style().setColor(TextFormatting.RED))));
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.md.level", heatingCoilLevel));
        }
        super.addDisplayText(textList);
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.3"));
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("不再需要高炉", new Object[0]));
    }

    @Override
    public int getCurrentTemperature() {
        return this.blastFurnaceTemperature;
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object coilType = context.get("CoilType");
        Object type = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
            this.blastFurnaceTemperature = ((IHeatingCoilBlockStats) type).getCoilTemperature();
            this.heatingCoilDiscount = ((IHeatingCoilBlockStats) coilType).getEnergyDiscount();
        } else {
            this.heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
            this.blastFurnaceTemperature = BlockWireCoil.CoilType.CUPRONICKEL.getCoilTemperature();
            this.heatingCoilDiscount = BlockWireCoil.CoilType.CUPRONICKEL.getEnergyDiscount();
        }
        this.blastFurnaceTemperature += 100 * Math.max(0, GTUtility.getFloorTierByVoltage(getEnergyContainer().getInputVoltage()) - GTValues.MV);
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        return this.blastFurnaceTemperature >= recipe.getProperty(TemperatureProperty.getInstance(), 0);
    }
    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("MMMMMMMSMMMMMMM","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","AB           BA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","ABBBBBBBBBBBBBA","MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMXMMMMMMM","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAA","MMMMMMMMMMMMMMM")
                .where('X', selfPredicate())
                .where('M', states(getCasingState())
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                )
                .where('B', heatingCoils())
                .where('A', states(this.getGlassState()))
                .where('S', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where(' ', air())
                .build();
    }
    private IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS);
    }
    private static IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF);
    }


    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.HEAT_PROOF_CASING;
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

    /**
     * @param heatingCoilLevel the level to get the parallel for
     * @return the max parallel for the heating coil level
     */
    public static int getMaxParallel(int heatingCoilLevel) {
        return 64 * heatingCoilLevel;
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.blastFurnaceTemperature = 0;
        heatingCoilLevel = 0;
        heatingCoilDiscount = 0;
    }
    protected class MetaTileEntityHugeBlastFurnaceWorkable extends MultiblockRecipeLogic {


        public MetaTileEntityHugeBlastFurnaceWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public int getParallelLimit() {
            return getMaxParallel(heatingCoilLevel);
        }
    }
}