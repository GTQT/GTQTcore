package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.metatileentity.multiblock.ui.KeyManager;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIBuilder;
import gregtech.api.metatileentity.multiblock.ui.UISyncer;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.KeyUtil;
import gregtech.api.util.LocalizationUtils;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.capability.IPHValue;
import keqing.gtqtcore.api.metatileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.PHChangeProperty;
import keqing.gtqtcore.api.recipes.properties.PHErrorRangeProperty;
import keqing.gtqtcore.api.recipes.properties.PHProperty;
import keqing.gtqtcore.api.utils.GTQTMathUtil;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static gregicality.multiblocks.api.unification.GCYMMaterials.WatertightSteel;
import static keqing.gtqtcore.common.block.blocks.BlockTransparentCasing.CasingType.PMMA_GLASS;

public class MetaTileEntityFermentationTank extends RecipeMapMultiblockController implements IPHValue {
    private double pH = 7;

    public MetaTileEntityFermentationTank(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.FERMENTATION_TANK_RECIPES);
        this.recipeMapWorkable = new PHRecipeLogic(this);
    }

    private static IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN);
    }

    private static IBlockState getBoilerState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.POLYTETRAFLUOROETHYLENE_PIPE);
    }

    @Nonnull
    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(WatertightSteel).getBlock(WatertightSteel);
    }

    private static IBlockState getGlassState() {
        return GTQTMetaBlocks.blockTransparentCasing.getState(PMMA_GLASS);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityFermentationTank(metaTileEntityId);
    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        double min = recipe.getProperty(PHProperty.getInstance(), 7D) - recipe.getProperty(PHErrorRangeProperty.getInstance(), 0D);
        double max = recipe.getProperty(PHProperty.getInstance(), 7D) + recipe.getProperty(PHErrorRangeProperty.getInstance(), 0D);
        if (min < 0) {
            min = 0D;
        }
        if (max > 14) {
            max = 14D;
        }
        return (this.pH >= min && this.pH <= max) && super.checkRecipe(recipe, consumeIfSuccess);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.pH = 7D;
        this.markDirty();
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("FAAAF", "FXXXF", "FXGXF", "FXGXF", "FXGXF", "FXXXF", "AAAAA")
                .aisle("AXXXA", "XEEEX", "XEEEX", "XEEEX", "XEEEX", "XEEEX", "AXXXA")
                .aisle("AXXXA", "XEPEX", "GEPEG", "GEPEG", "GEPEG", "XEPEX", "AXXXA")
                .aisle("AXXXA", "XEEEX", "XEEEX", "XEEEX", "XEEEX", "XEEEX", "AXXXA")
                .aisle("FAAAF", "FXSXF", "FXGXF", "FXGXF", "FXGXF", "FXXXF", "AAAAA")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(42).or(autoAbilities()).or(abilities(GTQTMultiblockAbility.BUFFER_MULTIBLOCK_ABILITY).setExactLimit(1)))
                .where('G', states(getGlassState()))
                .where('F', states(getFrameState()))
                .where('P', states(getBoilerState()))
                .where('A', any())
                .where('E', air())
                .build();
    }
    @Override
    protected void configureDisplayText(MultiblockUIBuilder builder) {
        builder.setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addEnergyUsageLine(this.getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                .addCustom(this::addHeatCapacity)
                .addParallelsLine(recipeMapWorkable.getParallelLimit())
                .addWorkingStatusLine()
                .addProgressLine(recipeMapWorkable.getProgress(), recipeMapWorkable.getMaxProgress())
                .addRecipeOutputLine(recipeMapWorkable);
    }

    private void addHeatCapacity(KeyManager keyManager, UISyncer syncer) {
        if (isStructureFormed()) {
            keyManager.add(KeyUtil.lang(TextFormatting.GRAY,
                    "gtqtcore.machine.fermentation_tank.ph", syncer.syncString(String.format("%, .2f", this.pH))));
        }
    }


    @Override
    public String[] getDescription() {
        List<String> list = new ArrayList<>();
        list.add(I18n.format("gtqtcore.machine.fermentation_tank.description"));
        Collections.addAll(list, LocalizationUtils.formatLines("gtqtcore.machine.fermentation_tank.extra1"));
        Collections.addAll(list, LocalizationUtils.formatLines("gtqtcore.machine.fermentation_tank.extra2"));
        return list.toArray(new String[0]);
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.POWER_SUBSTATION_OVERLAY;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.CLEAN_STAINLESS_STEEL_CASING;
    }

    @Override
    public double getCurrentPHValue() {
        return this.pH;
    }

    @Override
    public void changeCurrentPHValue(double ph_change) {
        this.pH = GTQTMathUtil.clamp(pH + ph_change, 0, 14);
        this.markDirty();
    }

    @Override
    public void changeCurrentPHValue(double ph_change, double ph_change_limit) {
        if (ph_change > 0) {
            double ph = this.pH + ph_change;
            this.pH = Math.min(ph, ph_change_limit);
        } else {
            double ph = this.pH + ph_change;
            this.pH = Math.max(ph, ph_change_limit);
        }
        this.markDirty();
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setDouble("ph", this.pH);
        return super.writeToNBT(data);
    }


    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.pH = data.getDouble("ph");
    }

    @Override
    public void addInformation(ItemStack stack,
                               World player,
                               List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("工业化生产沼气", new Object[0]));
    }

    protected class PHRecipeLogic extends MultiblockRecipeLogic {
        private double current_ph_change;

        public PHRecipeLogic(RecipeMapMultiblockController metaTileEntity) {
            super(metaTileEntity);
            if (!(metaTileEntity instanceof IPHValue)) {
                throw new IllegalArgumentException("MetaTileEntity must be instanceof IPHValue");
            }
        }

        @Override
        protected void setupRecipe(Recipe recipe) {
            super.setupRecipe(recipe);
            this.current_ph_change = recipe.getProperty(PHChangeProperty.getInstance(), 0D);
        }

        @Nonnull
        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound tag = super.serializeNBT();
            tag.setDouble("current_ph_change", this.current_ph_change);
            return tag;
        }

        @Override
        public void deserializeNBT(@Nonnull NBTTagCompound compound) {
            super.deserializeNBT(compound);
            this.current_ph_change = compound.getDouble("current_ph_change");
        }

        @Override
        protected void completeRecipe() {
            super.completeRecipe();
            ((IPHValue) this.metaTileEntity).changeCurrentPHValue(this.current_ph_change);
        }
    }
}
