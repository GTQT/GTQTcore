package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys;


import gregtech.api.GTValues;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.metatileentity.multiblock.ui.KeyManager;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIBuilder;
import gregtech.api.metatileentity.multiblock.ui.UISyncer;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.logic.OCParams;
import gregtech.api.recipes.logic.OCResult;
import gregtech.api.recipes.logic.OverclockingLogic;
import gregtech.api.recipes.properties.RecipePropertyStorage;
import gregtech.api.recipes.properties.impl.TemperatureProperty;
import gregtech.api.util.GTUtility;
import gregtech.api.util.KeyUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.*;
import keqing.gtqtcore.api.capability.IPressureContainer;
import keqing.gtqtcore.api.capability.IPressureMachine;
import keqing.gtqtcore.api.capability.impl.AtmosphericPressureContainer;
import keqing.gtqtcore.api.capability.impl.PressureMultiblockRecipeLogic;
import keqing.gtqtcore.api.metatileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.recipes.logic.OverclockingLogic.heatingCoilOC;

public class MetaTileEntityCZPuller extends RecipeMapMultiblockController implements IHeatingCoil, IPressureMachine {
    private int czpullerTemperarure;
    private IPressureContainer container;

    public MetaTileEntityCZPuller(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.CZPULLER_RECIPES);
        this.recipeMapWorkable = new CZPullerRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCZPuller(metaTileEntityId);
    }

    @Override
    public IPressureContainer getPressureContainer() {
        return this.container;
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
            var heatString = KeyUtil.number(TextFormatting.RED,
                    syncer.syncInt(getCurrentTemperature()), "K");

            keyManager.add(KeyUtil.lang(TextFormatting.GRAY,
                    "gregtech.multiblock.blast_furnace.max_temperature", heatString));
        }
    }

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
        if (this.czpullerTemperarure >= recipe.getProperty(TemperatureProperty.getInstance(), 0))
            return super.checkRecipe(recipe, consumeIfSuccess);
        return false;
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" YYYYY ", "   Y   ", "   X   ", "   G   ", "   G   ", "   G   ", "   G   ", "   G   ", "   X   ", "       ")
                .aisle("YYYYYYY", "  YCY  ", "  XCX  ", "  XCX  ", "  XCX  ", "  XCX  ", "  XCX  ", "  XCX  ", "  XXX  ", "   X   ")
                .aisle("YYYYYYY", " XN NY ", " XN NX ", " XN NX ", " XN NX ", " XN NX ", " XNNNX ", " XN NX ", " XRRRX ", " XXXXX ")
                .aisle("YYYYYYY", "YC   CY", "XC   CX", "GC   CG", "GC   CG", "GC   CG", "GC   CG", "GCN NCG", "XXR RXX", " XXMXX ")
                .aisle("YYYYYYY", " YN NY ", " XN NX ", " XN NX ", " XN NX ", " XN NX ", " XNNNX ", " XN NX ", " XRRRX ", " XXXXX ")
                .aisle("YYYYYYY", "  YCY  ", "  XCX  ", "  XCX  ", "  XCX  ", "  XCX  ", "  XCX  ", "  XCX  ", "  XXX  ", "   X   ")
                .aisle(" YYYYY ", "   S   ", "   X   ", "   G   ", "   G   ", "   G   ", "   G   ", "   G   ", "   X   ", "       ")
                .where('S', selfPredicate())
                .where('X', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID)))
                .where('Y', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID))
                        .or(autoAbilities(true, true, true, true, true, false, false))
                        .or(abilities(GTQTMultiblockAbility.PRESSURE_CONTAINER).setExactLimit(1))
                )
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
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

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("硅宝宝之家", new Object[0]));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.3"));
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.POWER_SUBSTATION_OVERLAY;
    }

    public static class CZPullerRecipeLogic extends PressureMultiblockRecipeLogic {

        public CZPullerRecipeLogic(RecipeMapMultiblockController tileEntity) {
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
    }
}
