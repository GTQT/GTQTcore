package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.giantEquipment;

import com.cleanroommc.modularui.api.drawable.IKey;
import gregicality.multiblocks.api.recipes.GCYMRecipeMaps;
import gregicality.multiblocks.api.render.GCYMTextures;
import gregtech.api.GTValues;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.*;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
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
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.logic.OCParams;
import gregtech.api.recipes.logic.OCResult;
import gregtech.api.recipes.logic.OverclockingLogic;
import gregtech.api.recipes.properties.RecipePropertyStorage;
import gregtech.api.recipes.properties.impl.TemperatureProperty;
import gregtech.api.util.GTUtility;
import gregtech.api.util.KeyUtil;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metatileentity.GTQTNoTierMultiblockController;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static gregtech.api.recipes.logic.OverclockingLogic.heatingCoilOC;
import static keqing.gtqtcore.GTQTCoreConfig.MachineSwitch;
import static keqing.gtqtcore.api.utils.GTQTUtil.getAccelerateByCWU;

public class MetaTileEntityHugeBlastFurnace extends GTQTNoTierMultiblockController implements IHeatingCoil, IOpticalComputationReceiver {

    protected int heatingCoilLevel;
    protected int coilTier;
    protected int glassTire;
    int requestCWUt;
    private int blastFurnaceTemperature;
    private IOpticalComputationProvider computationProvider;

    public MetaTileEntityHugeBlastFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.FURNACE_RECIPES,
                RecipeMaps.ARC_FURNACE_RECIPES,
                RecipeMaps.BLAST_RECIPES,
                RecipeMaps.ALLOY_SMELTER_RECIPES,
                GTQTcoreRecipeMaps.EXTRADIMENSIONAL_SMELTING_RECIPES,
                GTQTcoreRecipeMaps.BURNER_REACTOR_RECIPES,
                GTQTcoreRecipeMaps.DRYER_RECIPES,
                GTQTcoreRecipeMaps.VACUUM_DRYING_FURNACE_RECIPES,
                GCYMRecipeMaps.ALLOY_BLAST_RECIPES
        });
        this.recipeMapWorkable = new MetaTileEntityHugeBlastFurnacerWorkable(this);

        setMaxParallel(256);
        setMaxParallelFlag(true);

        //setTimeReduce(glassTire);
        setTimeReduceFlag(true);

        setOverclocking(3);
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing3.getState(BlockMultiblockCasing3.CasingType.ALLOY_MELTING);
    }

    @Override
    public boolean shouldDelayCheck() {
        return true;
    }

    @Override
    protected void initializeAbilities() {
        this.inputInventory = new ItemHandlerList(this.getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.inputFluidInventory = new FluidTankList(this.allowSameFluidFillForOutputs(), this.getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.outputInventory = new ItemHandlerList(this.getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.outputFluidInventory = new FluidTankList(this.allowSameFluidFillForOutputs(), this.getAbilities(MultiblockAbility.EXPORT_FLUIDS));
        List<IEnergyContainer> energyContainer = new ArrayList<>(this.getAbilities(MultiblockAbility.INPUT_ENERGY));
        energyContainer.addAll(this.getAbilities(MultiblockAbility.INPUT_LASER));
        this.energyContainer = new EnergyContainerList(energyContainer);
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityHugeBlastFurnace(this.metaTileEntityId);
    }


    @Override
    public void updateFormedValid() {
        super.updateFormedValid();
        if (isStructureFormed() && isActive()) {
            requestCWUt = computationProvider.requestCWUt(2048, false);
        }
    }

    @Override
    protected void configureDisplayText(MultiblockUIBuilder builder) {
        builder.setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addEnergyUsageLine(getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                .addCustom(this::addHeatCapacity)
                .addCustom((textList, syncer) -> {
                    if (!isStructureFormed()) return;
                    textList.add(KeyUtil.lang( "玻璃等级：%s 线圈等级:%s", syncer.syncInt(glassTire), syncer.syncInt(coilTier)));
                    textList.add(KeyUtil.lang( "gtqtcore.kqcc_accelerate", syncer.syncInt(requestCWUt), syncer.syncDouble(getAccelerateByCWU(requestCWUt))));
                })
                .addParallelsLine(recipeMapWorkable.getParallelLimit())
                .addWorkingStatusLine()
                .addProgressLine(recipeMapWorkable.getProgress(), recipeMapWorkable.getMaxProgress())
                .addRecipeOutputLine(recipeMapWorkable);
    }

    private void addHeatCapacity(KeyManager keyManager, UISyncer syncer) {
        if (isStructureFormed()) {
            var heatString = KeyUtil.number(TextFormatting.RED,
                    syncer.syncInt(getCurrentTemperature()), "K");

            keyManager.add(KeyUtil.lang(
                    "gregtech.multiblock.blast_furnace.max_temperature", heatString));
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("最强冶炼王", new Object[0]));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.3"));
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.multiblock.kq.acc.tooltip"));
        tooltip.add(I18n.format("gtqtcore.multiblock.kq.laser.tooltip"));
    }

    @Override
    public IOpticalComputationProvider getComputationProvider() {
        return this.computationProvider;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);

        List<IOpticalComputationHatch> providers = this.getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION);
        if (providers != null && !providers.isEmpty()) {
            this.computationProvider = providers.get(0);
        }

        Object glassTire = context.get("GlassTieredStats");
        this.glassTire = GTQTUtil.getOrDefault(() -> glassTire instanceof WrappedIntTired,
                () -> ((WrappedIntTired) glassTire).getIntTier(),
                0);

        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.blastFurnaceTemperature = ((IHeatingCoilBlockStats) coilType).getCoilTemperature();
            this.heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
            this.coilTier = ((IHeatingCoilBlockStats) coilType).getTier();
        } else {
            this.blastFurnaceTemperature = BlockWireCoil.CoilType.CUPRONICKEL.getCoilTemperature();
            this.heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
            this.coilTier = BlockWireCoil.CoilType.CUPRONICKEL.getTier();
        }

        setTimeReduce((100 - Math.min(this.glassTire, 10) * 5.0) / 100);

        this.blastFurnaceTemperature += 100 * Math.max(0, GTUtility.getTierByVoltage(getEnergyContainer().getInputVoltage()) - GTValues.MV);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        blastFurnaceTemperature = 0;
        heatingCoilLevel = 0;
    }

    public boolean checkRecipe(Recipe recipe, boolean consumeIfSuccess) {
        return this.blastFurnaceTemperature >= recipe.getProperty(TemperatureProperty.getInstance(), 0);
    }

    public int getCurrentTemperature() {
        return this.blastFurnaceTemperature;
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("MMMMMMMMMMMMMMM", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "MMMMMMMSMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "AB           BA", "MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMMMMMMMMM", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "ABBBBBBBBBBBBBA", "MMMMMMMMMMMMMMM")
                .aisle("MMMMMMMXMMMMMMM", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAA", "MMMMMMMMMMMMMMM")
                .where('X', selfPredicate())
                .where('M', states(getCasingState())
                        .or(autoAbilities(false, true, true, true, true, true, false))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY)
                                .setMaxGlobalLimited(3))
                        .or(abilities(MultiblockAbility.INPUT_LASER)
                                .setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION).setExactLimit(1))
                )
                .where('B', heatingCoils())
                .where('A', TiredTraceabilityPredicate.CP_GLASS.get())
                .where('S', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where(' ', air())
                .build();
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.ALLOY_MELTING_CASING;
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

    protected class MetaTileEntityHugeBlastFurnacerWorkable extends GTQTMultiblockLogic {

        public MetaTileEntityHugeBlastFurnacerWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            super.setMaxProgress((int) (maxProgress * getAccelerateByCWU(requestCWUt)));
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