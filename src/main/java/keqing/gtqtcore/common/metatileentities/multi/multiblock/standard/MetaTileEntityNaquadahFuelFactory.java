package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.ui.KeyManager;
import gregtech.api.metatileentity.multiblock.ui.UISyncer;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.KeyUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metatileentity.GTQTNoTierMultiblockController;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.ForceFieldCoilTierProperty;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static keqing.gtqtcore.api.GTQTAPI.MAP_FORCE_FIELD_COIL;

public class MetaTileEntityNaquadahFuelFactory extends GTQTNoTierMultiblockController {

    protected int CoilLevel;

    public MetaTileEntityNaquadahFuelFactory(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GTQTcoreRecipeMaps.NAQUADAH_REFINE_FACTORY_RECIPES
        });

        //setMaxParallel(auto);
        setMaxParallelFlag(true);

        //setTimeReduce(glassTire);
        setTimeReduceFlag(true);

        setOverclocking(3);
    }

    @Override
    public void addCustomData(KeyManager keyManager, UISyncer syncer) {
        super.addCustomData(keyManager, syncer);
        if (isStructureFormed())
            keyManager.add(KeyUtil.lang(TextFormatting.GRAY, "线圈等级 %s", syncer.syncInt(CoilLevel)));
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

    @Override
    protected void formStructure(PatternMatchContext context) {
        Object CoilLevel = context.get("ForceFieldCoilTieredStats");
        this.CoilLevel = GTQTUtil.getOrDefault(() -> CoilLevel instanceof WrappedIntTired,
                () -> ((WrappedIntTired) CoilLevel).getIntTier(),
                0);

        setMaxParallel((int) Math.pow(2, Math.min(10, this.CoilLevel)));
        setTimeReduce((double) (100 - (Math.min(10, this.CoilLevel)) * 5) / 100);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.CoilLevel = 0;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("硅岩快乐之家", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.naquadah_fuel_refine_factory.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.naquadah_fuel_refine_factory.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.naquadah_fuel_refine_factory.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.machine.naquadah_fuel_refine_factory.tooltip.4"));
        tooltip.add(I18n.format("gtqtcore.machine.naquadah_fuel_refine_factory.tooltip.5"));
        tooltip.add(I18n.format("gtqtcore.machine.naquadah_fuel_refine_factory.tooltip.6"));
        tooltip.add(I18n.format("gtqtcore.multiblock.kq.acc.tooltip"));
        tooltip.add(I18n.format("gtqtcore.multiblock.kq.laser.tooltip"));
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("               ", "      CGC      ", "    CC   CC    ", "   C       C   ", "  C         C  ", "  C         C  ", " C           C ", " G           G ", " C           C ", "  C         C  ", "  C         C  ", "   C       C   ", "    CC   CC    ", "      CGC      ", "               ")
                .aisle("      CCC      ", "    CCLLLCC    ", "   CLLCSCLLC   ", "  CLCC   CCLC  ", " CLC       CLC ", " CLC       CLC ", "CLC         CLC", "CLC         CLC", "CLC         CLC", " CLC       CLC ", " CLC       CLC ", "  CLCC   CCLC  ", "   CLLCCCLLC   ", "    CCLLLCC    ", "      CCC      ")
                .aisle("               ", "      CGC      ", "    CC   CC    ", "   C       C   ", "  C         C  ", "  C         C  ", " C           C ", " G           G ", " C           C ", "  C         C  ", "  C         C  ", "   C       C   ", "    CC   CC    ", "      CGC      ", "               ")
                .where('S', this.selfPredicate())
                .where('C', states(this.getCasingState1())
                        .setMinGlobalLimited(100)
                        .or(abilities(MultiblockAbility.INPUT_LASER)
                                .setMaxGlobalLimited(1))
                        .or(autoAbilities())
                )
                .where('G', states(this.getCasingState2()))
                .where('L', TiredTraceabilityPredicate.FORCE_FIELD_COIL.get())
                .where(' ', any())
                .build();
    }

    private IBlockState getCasingState1() {
        return GTQTMetaBlocks.blockMultiblockCasing3.getState(BlockMultiblockCasing3.CasingType.SFTC);
    }

    private IBlockState getCasingState2() {
        return GTQTMetaBlocks.blockMultiblockCasing3.getState(BlockMultiblockCasing3.CasingType.SFTS);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.SFTC;
    }

    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityNaquadahFuelFactory(metaTileEntityId);
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder;
        builder = MultiblockShapeInfo.builder()
                .aisle("               ", "      CGC      ", "    CC   CC    ", "   C       C   ", "  C         C  ", "  C         C  ", " C           C ", " G           G ", " C           C ", "  C         C  ", "  C         C  ", "   C       C   ", "    CC   CC    ", "      CGC      ", "               ")
                .aisle("      CEC      ", "    CCLLLCC    ", "   CLLISJLLC   ", "  CLCK   MCLC  ", " CLC       CLC ", " CLC       CLC ", "CLC         CLC", "CLC         CLC", "CLC         CLC", " CLC       CLC ", " CLC       CLC ", "  CLCC   CCLC  ", "   CLLCCCLLC   ", "    CCLLLCC    ", "      CCC      ")
                .aisle("               ", "      CGC      ", "    CC   CC    ", "   C       C   ", "  C         C  ", "  C         C  ", " C           C ", " G           G ", " C           C ", "  C         C  ", "  C         C  ", "   C       C   ", "    CC   CC    ", "      CGC      ", "               ")
                .where('S', GTQTMetaTileEntities.NAQUADAH_FUEL_FACTORY, EnumFacing.SOUTH)
                .where('C', this.getCasingState1())
                .where('G', this.getCasingState2())
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[0], EnumFacing.NORTH)
                .where('I', MetaTileEntities.ITEM_IMPORT_BUS[0], EnumFacing.SOUTH)
                .where('J', MetaTileEntities.ITEM_EXPORT_BUS[0], EnumFacing.SOUTH)
                .where('K', MetaTileEntities.FLUID_IMPORT_HATCH[0], EnumFacing.SOUTH)
                .where('M', MetaTileEntities.FLUID_EXPORT_HATCH[0], EnumFacing.SOUTH);
        MultiblockShapeInfo.Builder finalBuilder = builder;
        MAP_FORCE_FIELD_COIL.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> ((WrappedIntTired) entry.getValue()).getIntTier()))
                .forEach(entry -> shapeInfo.add(finalBuilder.where('L', entry.getKey()).build()));
        return shapeInfo;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    public boolean checkRecipe(Recipe recipe, boolean consumeIfSuccess) {
        return super.checkRecipe(recipe, consumeIfSuccess)
                && this.CoilLevel >= recipe.getProperty(ForceFieldCoilTierProperty.getInstance(), 0);
    }
}
