package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys;

import gregicality.multiblocks.api.capability.IParallelMultiblock;
import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregtech.api.GTValues;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.logic.OCParams;
import gregtech.api.recipes.logic.OCResult;
import gregtech.api.recipes.logic.OverclockingLogic;
import gregtech.api.recipes.properties.RecipePropertyStorage;
import gregtech.api.recipes.properties.impl.TemperatureProperty;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.blocks.BlockTurbineCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import keqing.gtqtcore.api.capability.IPressureContainer;
import keqing.gtqtcore.api.capability.IPressureMachine;
import keqing.gtqtcore.api.capability.impl.AtmosphericPressureContainer;
import keqing.gtqtcore.api.capability.impl.PressureMultiblockRecipeLogic;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.recipes.logic.OverclockingLogic.heatingCoilOC;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3.CasingType.eglin_steel;

public class MetaTileEntityBurnerReactor extends GCYMRecipeMapMultiblockController implements IHeatingCoil, IPressureMachine {

    int blastFurnaceTemperature;
    private IPressureContainer container;

    public MetaTileEntityBurnerReactor(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GTQTcoreRecipeMaps.BURNER_REACTOR_RECIPES,
                GTQTcoreRecipeMaps.ROASTER_RECIPES});
        this.recipeMapWorkable = new BurnerReactorRecipeLogic(this);
    }

    @Override
    public IPressureContainer getPressureContainer() {
        return this.container;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityBurnerReactor(metaTileEntityId);
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        List<IPressureContainer> list = getAbilities(GTQTMultiblockAbility.PRESSURE_CONTAINER);
        if (list.isEmpty()) {
            this.container = new AtmosphericPressureContainer(this, 1.0);
        } else {
            this.container = list.get(0);
        }
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addEnergyUsageLine(getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                .addCustom(tl -> {
                    // Coil heat capacity line
                    if (isStructureFormed()) {
                        ITextComponent heatString = TextComponentUtil.stringWithColor(
                                TextFormatting.RED,
                                TextFormattingUtil.formatNumbers(blastFurnaceTemperature) + "K");

                        tl.add(TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "gregtech.multiblock.blast_furnace.max_temperature",
                                heatString));
                    }
                })
                .addParallelsLine(recipeMapWorkable.getParallelLimit())
                .addWorkingStatusLine()
                .addProgressLine(recipeMapWorkable.getProgressPercent());
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
        // the subtracted tier gives the starting level (exclusive) of the +100K heat bonus
        this.blastFurnaceTemperature += 100 *
                Math.max(0, GTUtility.getFloorTierByVoltage(getEnergyContainer().getInputVoltage()) - GTValues.MV);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.blastFurnaceTemperature = 0;
    }

    @Override
    public boolean checkRecipe(Recipe recipe, boolean consumeIfSuccess) {
        if (this.blastFurnaceTemperature >= recipe.getProperty(TemperatureProperty.getInstance(), 0))
            return super.checkRecipe(recipe, consumeIfSuccess);
        return false;
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("F   F", "F X F", "FXXXF", "F X F", "F   F", "     ")
                .aisle("  X  ", " XCX ", "XCKCX", " XKX ", "  X  ", "  X  ")
                .aisle(" XXX ", "XCKCX", "XK#KX", "XK#KX", " XKX ", " XMX ")
                .aisle("  X  ", " XCX ", "XCKCX", " XKX ", "  X  ", "  X  ")
                .aisle("F   F", "F X F", "FXSXF", "F X F", "F   F", "     ")
                .where('S', selfPredicate())
                .where('X', states(getCasingState1()).setMinGlobalLimited(25)
                        .or(autoAbilities(true, true, true, true, true, true, false))
                        .or(abilities(GTQTMultiblockAbility.PRESSURE_CONTAINER).setExactLimit(1))
                )
                .where('F', states(MetaBlocks.FRAMES.get(Materials.RedSteel).getBlock(Materials.RedSteel)))
                .where('C', states(getCasingState2()))
                .where('K', heatingCoils())
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where(' ', any())
                .where('#', air())
                .build();
    }

    protected IBlockState getCasingState1() {
        return GTQTMetaBlocks.blockMultiblockCasing3.getState(eglin_steel);
    }

    protected IBlockState getCasingState2() {
        return MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STAINLESS_STEEL_GEARBOX);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.eglin_steel;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.LARGE_BURNER_REACTOR_OVERLAY;
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
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = super.getDataInfo();
        list.add(new TextComponentTranslation("gregtech.multiblock.blast_furnace.max_temperature",
                new TextComponentTranslation(TextFormattingUtil.formatNumbers(blastFurnaceTemperature) + "K")
                        .setStyle(new Style().setColor(TextFormatting.RED))));
        return list;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    public int getCurrentTemperature() {
        return this.blastFurnaceTemperature;
    }

    public static class BurnerReactorRecipeLogic extends PressureMultiblockRecipeLogic {

        public BurnerReactorRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        protected void modifyOverclockPre(OCParams ocParams, RecipePropertyStorage storage) {
            super.modifyOverclockPre(ocParams, storage);
            // coil EU/t discount
            ocParams.setEut(OverclockingLogic.applyCoilEUtDiscount(ocParams.eut(),
                    ((IHeatingCoil) metaTileEntity).getCurrentTemperature(),
                    storage.get(TemperatureProperty.getInstance(), 0)));
        }

        @Override
        protected void runOverclockingLogic(OCParams ocParams, OCResult ocResult,
                                            RecipePropertyStorage propertyStorage, long maxVoltage) {
            heatingCoilOC(ocParams, ocResult, maxVoltage, ((IHeatingCoil) metaTileEntity).getCurrentTemperature(),
                    propertyStorage.get(TemperatureProperty.getInstance(), 0));
        }

        public int getParallelLimit() {
            return this.metaTileEntity instanceof IParallelMultiblock && ((IParallelMultiblock) this.metaTileEntity).isParallel() ? ((IParallelMultiblock) this.metaTileEntity).getMaxParallel() : 1;
        }
    }
}
