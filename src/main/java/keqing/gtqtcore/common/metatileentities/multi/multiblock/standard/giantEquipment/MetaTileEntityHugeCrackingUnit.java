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
import gregtech.api.recipes.logic.OCParams;
import gregtech.api.recipes.logic.OCResult;
import gregtech.api.recipes.logic.OverclockingLogic;
import gregtech.api.recipes.properties.RecipePropertyStorage;
import gregtech.api.recipes.properties.impl.TemperatureProperty;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metaileentity.GTQTNoTierMultiblockController;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.utils.GTQTUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static gregtech.api.recipes.logic.OverclockingLogic.heatingCoilOC;
import static keqing.gtqtcore.api.utils.GTQTUtil.getAccelerateByCWU;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.HUGE_CRACKING_UNIT;

public class MetaTileEntityHugeCrackingUnit extends GTQTNoTierMultiblockController implements IHeatingCoil, IOpticalComputationReceiver {

    protected int heatingCoilLevel;
    protected int coilTier;
    protected int glassTire;
    int requestCWUt;
    private int blastFurnaceTemperature;
    private IOpticalComputationProvider computationProvider;

    public MetaTileEntityHugeCrackingUnit(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.CRACKING_RECIPES
        });
        this.recipeMapWorkable = new MegaOilCrackingUnitWorkableHandler(this);

        setMaxParallel(256);
        setMaxParallelFlag(true);

        //setTimeReduce(glassTire);
        setTimeReduceFlag(true);

        setOverclocking(4);
    }

    private static IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN);
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

    public int getCurrentTemperature() {
        return this.blastFurnaceTemperature;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityHugeCrackingUnit(metaTileEntityId);
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

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCCCCCCCCCCCC", " C         C ", " C         C ", " C         C ", " C         C ", " C         C ", " C         C ")
                .aisle("CCCCCCCCCCCCC", "CCGGGGGGGGGCC", "CCGGGGGGGGGCC", "CCGGGGGGGGGCC", "CCGGGGGGGGGCC", "CCGGGGGGGGGCC", "CCGGGGGGGGGCC")
                .aisle("CCCCCCCCCCCCC", " GALALALALAG ", " GALALALALAG ", " GALALALALAG ", " GALALALALAG ", " GALALALALAG ", " CGGGGGGGGGC ")
                .aisle("CCCCCCCCCCCCC", " GALALALALAG ", " EAAAAAAAAAD ", " EALALALALAD ", " EAAAAAAAAAD ", " GALALALALAG ", " CGGGEEEGGGC ")
                .aisle("CCCCCCCCCCCCC", " GALALALALAG ", " EALALALALAD ", " EALALALALAD ", " EALALALALAD ", " GALALALALAG ", " CGGGEEEGGGC ")
                .aisle("CCCCCCCCCCCCC", " GALALALALAG ", " EAAAAAAAAAD ", " EALALALALAD ", " EAAAAAAAAAD ", " GALALALALAG ", " CGGGEEEGGGC ")
                .aisle("CCCCCCCCCCCCC", " GALALALALAG ", " GALALALALAG ", " GALALALALAG ", " GALALALALAG ", " GALALALALAG ", " CGGGGGGGGGC ")
                .aisle("CCCCCCCCCCCCC", "CCGGGGGGGGGCC", "CCGGGGGGGGGCC", "CCGGGGGGGGGCC", "CCGGGGGGGGGCC", "CCGGGGGGGGGCC", "CCGGGGGGGGGCC")
                .aisle("CCCCCCSCCCCCC", " C         C ", " C         C ", " C         C ", " C         C ", " C         C ", " C         C ")
                .where('S', this.selfPredicate())
                .where('C', states(getCasingState())
                        .setMinGlobalLimited(190)
                        .or(abilities(MultiblockAbility.INPUT_ENERGY)
                                .setMaxGlobalLimited(3))
                        .or(abilities(MultiblockAbility.INPUT_LASER)
                                .setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH))
                        .or(abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION).setExactLimit(1))
                )
                .where('G', TiredTraceabilityPredicate.CP_GLASS.get())
                .where('L', heatingCoils())
                .where('D', states(getCasingState())
                        .or(autoAbilities(false,true,false,true,false,true,false))
                )
                .where('E', states(getCasingState())
                        .or(autoAbilities(false,true,true,false,true,false,false))
                )
                .where(' ', any())
                .where('A', air())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.CLEAN_STAINLESS_STEEL_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.CRACKING_UNIT_OVERLAY;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
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
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle("CCCCCCJHCCCCC", " CAAAAAAAAAC ", " CAAAAAAAAAC ", " CAAAAAAAAAC ", " CAAAAAAAAAC ", " CAAAAAAAAAC ", " CAAAAAAAAAC ")
                .aisle("CCCCCCCCCCCCC", "CCGGGGGGGGGCC", "CCGGGGGGGGGCC", "CCGGGGGGGGGCC", "CCGGGGGGGGGCC", "CCGGGGGGGGGCC", "CCGGGGGGGGGCC")
                .aisle("CCCCCCCCCCCCC", " GALALALALAG ", " GALALALALAG ", " GALALALALAG ", " GALALALALAG ", " GALALALALAG ", " CGGGGGGGGGC ")
                .aisle("CCCCCCCCCCCCC", " GALALALALAG ", " CAAAAAAAAAC ", " CALALALALAC ", " CAAAAAAAAAC ", " GALALALALAG ", " CGGGCCCGGGC ")
                .aisle("CCCCCCCCCCCCC", " GALALALALAG ", " CALALALALAC ", " DALALALALAN ", " CALALALALAC ", " GALALALALAG ", " CGGGCVCGGGC ")
                .aisle("CCCCCCCCCCCCC", " GALALALALAG ", " CAAAAAAAAAC ", " CALALALALAC ", " CAAAAAAAAAC ", " GALALALALAG ", " CGGGCCCGGGC ")
                .aisle("CCCCCCCCCCCCC", " GALALALALAG ", " GALALALALAG ", " GALALALALAG ", " GALALALALAG ", " GALALALALAG ", " CGGGGGGGGGC ")
                .aisle("CCCCCCCCCCCCC", "CCGGGGGGGGGCC", "CCGGGGGGGGGCC", "CCGGGGGGGGGCC", "CCGGGGGGGGGCC", "CCGGGGGGGGGCC", "CCGGGGGGGGGCC")
                .aisle("CCCCCISKCCCCC", " CAAAAAAAAAC ", " CAAAAAAAAAC ", " CAAAAAAAAAC ", " CAAAAAAAAAC ", " CAAAAAAAAAC ", " CAAAAAAAAAC ")
                .where('S', HUGE_CRACKING_UNIT, EnumFacing.SOUTH)
                .where('C', getCasingState())
                .where('V', MetaTileEntities.FLUID_IMPORT_HATCH[4], EnumFacing.UP)
                .where('D', MetaTileEntities.FLUID_IMPORT_HATCH[4], EnumFacing.WEST)
                .where('N', MetaTileEntities.FLUID_EXPORT_HATCH[4], EnumFacing.EAST)
                .where('K', MetaTileEntities.ITEM_IMPORT_BUS[4], EnumFacing.SOUTH)
                .where('H', MetaTileEntities.ENERGY_INPUT_HATCH[5], EnumFacing.NORTH)
                .where('I', MetaTileEntities.COMPUTATION_HATCH_RECEIVER[5], EnumFacing.SOUTH)
                .where('G', MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS))
                .where('A', Blocks.AIR.getDefaultState())
                .where('J', MetaTileEntities.MAINTENANCE_HATCH, EnumFacing.NORTH);
        GregTechAPI.HEATING_COILS.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().getTier()))
                .forEach(entry -> shapeInfo.add(builder.where('L', entry.getKey()).build()));
        return shapeInfo;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    private class MegaOilCrackingUnitWorkableHandler extends GTQTMultiblockLogic {

        public MegaOilCrackingUnitWorkableHandler(RecipeMapMultiblockController tileEntity) {
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