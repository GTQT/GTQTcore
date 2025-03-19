package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.giantEquipment;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
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
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.logic.OverclockingLogic;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metaileentity.GTQTNoTierMultiblockController;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4;
import keqing.gtqtcore.common.block.blocks.BlockPCBFactoryCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
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
import java.util.Comparator;
import java.util.List;

import static keqing.gtqtcore.api.utils.GTQTUtil.getAccelerateByCWU;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.HUGE_CHEMICAL_REACTOR;

public class MetaTileEntityHugeChemicalReactor extends GTQTNoTierMultiblockController implements IHeatingCoil, IOpticalComputationReceiver {

    protected int heatingCoilLevel;
    protected int coilTier;
    protected int glassTire;
    int requestCWUt;
    private int blastFurnaceTemperature;
    private IOpticalComputationProvider computationProvider;

    public MetaTileEntityHugeChemicalReactor(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.CHEMICAL_RECIPES,
                RecipeMaps.LARGE_CHEMICAL_RECIPES,
                GTQTcoreRecipeMaps.POLYMERIZATION_RECIPES,
                GTQTcoreRecipeMaps.DESULPHURIZATION_RECIPES,
                GTQTcoreRecipeMaps.FLUIDIZED_BED,
                GTQTcoreRecipeMaps.CATALYTIC_REFORMER_RECIPES
        });
        this.recipeMapWorkable = new HugeChemicalReactorWorkable(this);

        setMaxParallel(256);
        setMaxParallelFlag(true);

        //setTimeReduce(glassTire);
        setTimeReduceFlag(true);

        setOverclocking(4.0);
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
        return new MetaTileEntityHugeChemicalReactor(this.metaTileEntityId);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("超级化学反应", new Object[0]));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.3"));
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.multiblock.kq.acc.tooltip"));
        tooltip.add(I18n.format("gtqtcore.multiblock.kq.acc.tooltip"));
    }

    @Override
    public IOpticalComputationProvider getComputationProvider() {
        return this.computationProvider;
    }


    @Override
    public void updateFormedValid() {
        super.updateFormedValid();
        if (isStructureFormed() && isActive()) {
            requestCWUt = computationProvider.requestCWUt(2048, false);
        }
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        ITextComponent heatString = TextComponentUtil.stringWithColor(TextFormatting.RED, TextFormattingUtil.formatNumbers(this.blastFurnaceTemperature) + "K");
        textList.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "gregtech.multiblock.blast_furnace.max_temperature", heatString));
        textList.add(new TextComponentTranslation("gtqtcore.kqcc_accelerate", requestCWUt, getAccelerateByCWU(requestCWUt)));
        textList.add(new TextComponentTranslation("玻璃等级：%s 线圈等级:%s", glassTire, coilTier));
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

    public int getCurrentTemperature() {
        return this.blastFurnaceTemperature;
    }


    @Nonnull
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCCCC", "CGGGC", "CGMGC", "CGGGC", "CCCCC")
                .aisle("CBCBC", "BGGGB", "CGAGC", "BGGGB", "CBCBC")
                .aisle("CBCBC", "BXXXB", "CXAXC", "BXXXB", "CBCBC")
                .aisle("CBCBC", "BGGGB", "CGAGC", "BGGGB", "CBCBC")
                .aisle("CBCBC", "BGGGB", "CGAGC", "BGGGB", "CBCBC")
                .aisle("CBCBC", "BXXXB", "CXAXC", "BXXXB", "CBCBC")
                .aisle("CBCBC", "BGGGB", "CGAGC", "BGGGB", "CBCBC")
                .aisle("CCCCC", "CGGGC", "CGOGC", "CGGGC", "CCCCC")
                .where('O', this.selfPredicate())
                .where('C', states(this.getCasingState())
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY)
                                .setMaxGlobalLimited(3))
                        .or(abilities(MultiblockAbility.INPUT_LASER)
                                .setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION).setExactLimit(1))
                )
                .where('X', heatingCoils())
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('A', states(this.getSecondCasingState()))
                .where('B', states(this.getPipeCasingState()))
                .where('G', TiredTraceabilityPredicate.CP_GLASS.get())
                .build();
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle("CCCCC", "CGGGC", "CGMGC", "CGGGC", "CCCCC")
                .aisle("CBCBC", "BGGGB", "CGAGC", "BGGGB", "CBCBC")
                .aisle("CBCBC", "BXXXB", "CXAXC", "BXXXB", "CBCBC")
                .aisle("CBCBC", "BGGGB", "CGAGC", "BGGGB", "CBCBC")
                .aisle("CBCBC", "BGGGB", "CGAGC", "BGGGB", "CBCBC")
                .aisle("CBCBC", "BXXXB", "CXAXC", "BXXXB", "CBCBC")
                .aisle("CBCBC", "BGGGB", "CGAGC", "BGGGB", "CBCBC")
                .aisle("ENILJ", "CGGGC", "CGOGC", "CGGGC", "CCKCC")
                .where('O', HUGE_CHEMICAL_REACTOR, EnumFacing.SOUTH)
                .where('G', MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS))
                .where('C', getCasingState())
                .where('A', this.getSecondCasingState())
                .where('B', this.getPipeCasingState())
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[5], EnumFacing.SOUTH)
                .where('N', MetaTileEntities.MAINTENANCE_HATCH, EnumFacing.SOUTH)
                .where('I', MetaTileEntities.FLUID_IMPORT_HATCH[4], EnumFacing.SOUTH)
                .where('L', MetaTileEntities.ITEM_IMPORT_BUS[4], EnumFacing.SOUTH)
                .where('K', MetaTileEntities.COMPUTATION_HATCH_RECEIVER, EnumFacing.SOUTH)
                .where('J', MetaTileEntities.FLUID_EXPORT_HATCH[4], EnumFacing.SOUTH)
                .where('M', MetaTileEntities.MUFFLER_HATCH[1], EnumFacing.NORTH);
        GregTechAPI.HEATING_COILS.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().getTier()))
                .forEach(entry -> shapeInfo.add(builder.where('X', entry.getKey()).build()));
        return shapeInfo;
    }

    private IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.NQ_TURBINE_CASING);
    }

    private IBlockState getSecondCasingState() {
        return GTQTMetaBlocks.blockPCBFactoryCasing.getState(BlockPCBFactoryCasing.PCBFactoryCasingType.ADVANCED_SUBSTRATE_CASING);
    }

    private IBlockState getPipeCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.NQ_MACHINE_CASING);
    }

    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.NQ_CASING;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    protected class HugeChemicalReactorWorkable extends GTQTMultiblockLogic {

        public HugeChemicalReactorWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public void setMaxProgress(int maxProgress) {
            super.setMaxProgress((int) (maxProgress * getAccelerateByCWU(requestCWUt)));
        }

        protected void modifyOverclockPre(int[] values, IRecipePropertyStorage storage) {
            super.modifyOverclockPre(values, storage);
            values[0] = OverclockingLogic.applyCoilEUtDiscount(values[0], ((IHeatingCoil) this.metaTileEntity).getCurrentTemperature(), storage.getRecipePropertyValue(TemperatureProperty.getInstance(), 0));
        }

        protected int[] runOverclockingLogic(IRecipePropertyStorage propertyStorage, int recipeEUt, long maxVoltage, int duration, int amountOC) {
            return OverclockingLogic.heatingCoilOverclockingLogic(Math.abs(recipeEUt), maxVoltage, duration, amountOC, ((IHeatingCoil) this.metaTileEntity).getCurrentTemperature(), propertyStorage.getRecipePropertyValue(TemperatureProperty.getInstance(), 0));
        }
    }
}