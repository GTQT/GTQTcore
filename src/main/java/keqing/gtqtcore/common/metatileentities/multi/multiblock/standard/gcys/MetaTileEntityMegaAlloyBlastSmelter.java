package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys;

import gregicality.multiblocks.api.capability.impl.GCYMHeatCoilRecipeLogic;
import gregicality.multiblocks.api.metatileentity.GCYMAdvanceRecipeMapMultiblockController;
import gregicality.multiblocks.api.metatileentity.GCYMMultiblockAbility;
import gregicality.multiblocks.api.recipes.GCYMRecipeMaps;
import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.GCYMConfigHolder;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.metatileentity.ITieredMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.properties.impl.TemperatureProperty;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static gregtech.api.metatileentity.multiblock.MultiblockAbility.THREAD_HATCH;
import static gtqt.common.metatileentities.GTQTMetaTileEntities.TREAD_HATCH;
import static keqing.gtqtcore.GTQTCoreConfig.MachineSwitch;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.MEGA_ALLOY_BLAST_FURANCE;

public class MetaTileEntityMegaAlloyBlastSmelter extends GCYMAdvanceRecipeMapMultiblockController implements IHeatingCoil {

    private int blastFurnaceTemperature;

    public MetaTileEntityMegaAlloyBlastSmelter(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GCYMRecipeMaps.ALLOY_BLAST_RECIPES
        });
        recipeMapWorkable = new ArrayList<>();
        this.recipeMapWorkable.add(new GCYMHeatCoilRecipeLogic(this));
    }

    private static IBlockState getCasingState() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.HIGH_TEMPERATURE_CASING);
    }

    private static IBlockState getBoilerCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.NQ_MACHINE_CASING);
    }

    private static IBlockState getUniqueCasingState() {
        return GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.HEAT_VENT);
    }

    private static IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS);
    }

    @Override
    public void checkStructurePattern() {
        if (MachineSwitch.DelayStructureCheckSwitch) {
            if (this.getOffsetTimer() % 100 == 0 || this.isFirstTick()) {
                super.checkStructurePattern();
            }
        } else super.checkStructurePattern();
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
        return new MetaTileEntityMegaAlloyBlastSmelter(this.metaTileEntityId);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(checkWorkingEnable(), checkActive())
                .addEnergyUsageLine(getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.get(0).getMaxVoltage()))
                .addCustom(tl -> {
                    // Tiered Hatch Line
                    if (isStructureFormed()) {
                        List<ITieredMetaTileEntity> list = getAbilities(GCYMMultiblockAbility.TIERED_HATCH);
                        if (GCYMConfigHolder.globalMultiblocks.enableTieredCasings && !list.isEmpty()) {
                            long maxVoltage = Math.min(GTValues.V[list.get(0).getTier()],
                                    Math.max(energyContainer.getInputVoltage(), energyContainer.getOutputVoltage()));
                            String voltageName = GTValues.VNF[list.get(0).getTier()];
                            tl.add(new TextComponentTranslation("gcym.multiblock.tiered_hatch.tooltip", maxVoltage,
                                    voltageName));
                        }
                    }
                })
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
                .addWorkingStatusLine();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("最强合金王", new Object[0]));
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.multiblock.kq.laser.tooltip"));
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CoilType");
        if (type instanceof IHeatingCoilBlockStats stats) {
            this.blastFurnaceTemperature = stats.getCoilTemperature();
        } else {
            this.blastFurnaceTemperature = BlockWireCoil.CoilType.CUPRONICKEL.getCoilTemperature();
        }

        this.blastFurnaceTemperature += 100 *
                Math.max(0, GTUtility.getTierByVoltage(getEnergyContainer().getInputVoltage()) - GTValues.MV);

        this.thread = this.getAbilities(THREAD_HATCH).isEmpty() ? 1 : this.getAbilities(THREAD_HATCH).get(0).getCurrentThread();
        this.recipeMapWorkable = new ArrayList<>();

        for (int i = 0; i < this.thread; ++i) {
            this.recipeMapWorkable.add(new GCYMHeatCoilRecipeLogic(this));
        }
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.blastFurnaceTemperature = 0;
    }

    public boolean checkRecipe(Recipe recipe, boolean consumeIfSuccess) {
        return this.blastFurnaceTemperature >= recipe.getProperty(TemperatureProperty.getInstance(), 0);
    }

    public int getCurrentTemperature() {
        return this.blastFurnaceTemperature;
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("   BBBBB   ", "   CCCCC   ", "   CCCCC   ", "   CCCCC   ", "   BBBBB   ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ")
                .aisle("  BDDDDDB  ", "  G     G  ", "  G     G  ", "  G     G  ", "  BDDDDDB  ", "   DDDDD   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   DDDDD   ", "   DDDDD   ", "           ")
                .aisle(" BDDHHHDDB ", " G       G ", " G       G ", " G       G ", " BDDHHHDDB ", "  DVVVVVD  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  DDDDDDD  ", "  DDDDDDD  ", "   DDDDD   ")
                .aisle("BDDDDDDDDDB", "C  VWWWV  C", "C  VBBBV  C", "C  VBBBV  C", "BDDDDDDDDDB", " DVVVVVVVD ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " DDDDDDDDD ", " DDDDDDDDD ", "  DDDDDDD  ")
                .aisle("BDHDDDDDHDB", "C  W   W  C", "C  B   B  C", "C  B   B  C", "BDHDDDDDHDB", " DVVVVVVVD ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " DDDDDDDDD ", " DDDDDDDDD ", "  DDDDDDD  ")
                .aisle("BDHDDDDDHDB", "C  W V W  C", "C  B V B  C", "C  B V B  C", "BDHDDVDDHDB", " DVVVVVVVD ", " GW  V  WG ", " GW  V  WG ", " GW  V  WG ", " GW  V  WG ", " GW  V  WG ", " GW  V  WG ", " GW  V  WG ", " GW  V  WG ", " GW  V  WG ", " GW  V  WG ", " GW  V  WG ", " DDDDDDDDD ", " DDDDDDDDD ", "  DDDMDDD  ")
                .aisle("BDHDDDDDHDB", "C  W   W  C", "C  B   B  C", "C  B   B  C", "BDHDDDDDHDB", " DVVVVVVVD ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " DDDDDDDDD ", " DDDDDDDDD ", "  DDDDDDD  ")
                .aisle("BDDDDDDDDDB", "C  VWWWV  C", "C  VBBBV  C", "C  VBBBV  C", "BDDDDDDDDDB", " DVVVVVVVD ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " DDDDDDDDD ", " DDDDDDDDD ", "  DDDDDDD  ")
                .aisle(" BDDHHHDDB ", " G       G ", " G       G ", " G       G ", " BDDHHHDDB ", "  DVVVVVD  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  DDDDDDD  ", "  DDDDDDD  ", "   DDDDD   ")
                .aisle("  BDDDDDB  ", "  G     G  ", "  G     G  ", "  G     G  ", "  BDDDDDB  ", "   DDDDD   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   DDDDD   ", "   DDDDD   ", "           ")
                .aisle("   BBBBB   ", "   CCCCC   ", "   CCSCC   ", "   CCCCC   ", "   BBBBB   ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ")
                .where('S', this.selfPredicate())
                .where('B', states(getUniqueCasingState()))
                .where('D', states(getCasingState()))
                .where('G', states(getGlassState()))
                .where('H', states(getUniqueCasingState()))
                .where('V', states(getBoilerCasingState()))
                .where('W', heatingCoils())
                .where('C', states(getCasingState())
                        .setMinGlobalLimited(15)
                        .or(abilities(MultiblockAbility.INPUT_ENERGY)
                                .setMaxGlobalLimited(3))
                        .or(abilities(MultiblockAbility.INPUT_LASER)
                                .setMaxGlobalLimited(1))
                        .or(autoAbilities(false, true, true, true, true, true, false))
                )
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where(' ', any())
                .build();
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle("   BBBBB   ", "   CCENC   ", "   CCCCC   ", "   CCCCC   ", "   BBBBB   ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ")
                .aisle("  BDDDDDB  ", "  G     G  ", "  G     G  ", "  G     G  ", "  BDDDDDB  ", "   DDDDD   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   DDDDD   ", "   DDDDD   ", "           ")
                .aisle(" BDDHHHDDB ", " G       G ", " G       G ", " G       G ", " BDDHHHDDB ", "  DVVVVVD  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  DDDDDDD  ", "  DDDDDDD  ", "   DDDDD   ")
                .aisle("BDDDDDDDDDB", "C  VWWWV  C", "C  VBBBV  C", "C  VBBBV  C", "BDDDDDDDDDB", " DVVVVVVVD ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " DDDDDDDDD ", " DDDDDDDDD ", "  DDDDDDD  ")
                .aisle("BDHDDDDDHDB", "C  W   W  C", "C  B   B  C", "C  B   B  C", "BDHDDDDDHDB", " DVVVVVVVD ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " DDDDDDDDD ", " DDDDDDDDD ", "  DDDDDDD  ")
                .aisle("BDHDDDDDHDB", "C  W V W  C", "C  B V B  C", "C  B V B  C", "BDHDDVDDHDB", " DVVVVVVVD ", " GW  V  WG ", " GW  V  WG ", " GW  V  WG ", " GW  V  WG ", " GW  V  WG ", " GW  V  WG ", " GW  V  WG ", " GW  V  WG ", " GW  V  WG ", " GW  V  WG ", " GW  V  WG ", " DDDDDDDDD ", " DDDDDDDDD ", "  DDDMDDD  ")
                .aisle("BDHDDDDDHDB", "C  W   W  C", "C  B   B  C", "C  B   B  C", "BDHDDDDDHDB", " DVVVVVVVD ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " GW     WG ", " DDDDDDDDD ", " DDDDDDDDD ", "  DDDDDDD  ")
                .aisle("BDDDDDDDDDB", "C  VWWWV  C", "C  VBBBV  C", "C  VBBBV  C", "BDDDDDDDDDB", " DVVVVVVVD ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " GWW   WWG ", " DDDDDDDDD ", " DDDDDDDDD ", "  DDDDDDD  ")
                .aisle(" BDDHHHDDB ", " G       G ", " G       G ", " G       G ", " BDDHHHDDB ", "  DVVVVVD  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  GWWWWWG  ", "  DDDDDDD  ", "  DDDDDDD  ", "   DDDDD   ")
                .aisle("  BDDDDDB  ", "  G     G  ", "  G     G  ", "  G     G  ", "  BDDDDDB  ", "   DDDDD   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   DDDDD   ", "   DDDDD   ", "           ")
                .aisle("   BBBBB   ", "   IUXTJ   ", "   CCSCC   ", "   CCCCC   ", "   BBBBB   ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ")
                .where('S', MEGA_ALLOY_BLAST_FURANCE, EnumFacing.SOUTH)
                .where('B', getUniqueCasingState())
                .where('D', getCasingState())
                .where('H', getUniqueCasingState())
                .where('V', getBoilerCasingState())
                .where('C', getCasingState())
                .where('G', MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS))
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[5], EnumFacing.NORTH)
                .where('N', () -> ConfigHolder.machines.enableMaintenance ? MetaTileEntities.MAINTENANCE_HATCH : getCasingState(), EnumFacing.NORTH)
                .where('I', MetaTileEntities.FLUID_IMPORT_HATCH[4], EnumFacing.SOUTH)
                .where('T', TREAD_HATCH[0], EnumFacing.SOUTH)
                .where('X', MetaTileEntities.ITEM_IMPORT_BUS[4], EnumFacing.SOUTH)
                .where('U', MetaTileEntities.LASER_INPUT_HATCH_256[0], EnumFacing.SOUTH)
                .where('J', MetaTileEntities.FLUID_EXPORT_HATCH[4], EnumFacing.SOUTH)
                .where('M', MetaTileEntities.MUFFLER_HATCH[1], EnumFacing.UP)
                .where(' ', Blocks.AIR.getDefaultState());
        GregTechAPI.HEATING_COILS.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().getTier()))
                .forEach(entry -> shapeInfo.add(builder.where('W', entry.getKey()).build()));
        return shapeInfo;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GCYMTextures.BLAST_CASING;
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
}