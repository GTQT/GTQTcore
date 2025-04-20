package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.endGame;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockComputerCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.GTQTAPI;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static gregtech.api.GTValues.UHV;
import static keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate.CP_DM_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockQuantumCasing.CasingType.DIMENSIONAL_BRIDGE_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockQuantumCasing.CasingType.ULTIMATE_HIGH_ENERGY_CASING;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.DIMENSIONAL_MIXER;

public class MetaTileEntityDimensionalMixer extends MultiMapMultiblockController {

    public int casingTier;

    public MetaTileEntityDimensionalMixer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{RecipeMaps.FURNACE_RECIPES});
        this.recipeMapWorkable = new DimensionalMixerRecipeLogic(this);
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockQuantumCasing.getState(ULTIMATE_HIGH_ENERGY_CASING);
    }

    private static IBlockState getSecondCasingState() {
        return MetaBlocks.COMPUTER_CASING.getState(BlockComputerCasing.CasingType.HIGH_POWER_CASING);
    }

    private static IBlockState getThirdCasingState() {
        return GTQTMetaBlocks.blockQuantumCasing.getState(DIMENSIONAL_BRIDGE_CASING);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityDimensionalMixer(metaTileEntityId);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object casingTier = context.get("FieldCasingTieredStats");
        this.casingTier = GTQTUtil.getOrDefault(() -> casingTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) casingTier).getIntTier(),
                0);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.casingTier = 0;
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
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(" CAC ", " ABA ", " ABA ", " ABA ", " ABA ", " ABA ", " CAC ")
                .aisle("CBBBC", "A###A", "A###A", "A###A", "A###A", "A###A", "CBBBC")
                .aisle("ABBBA", "B#F#B", "B#F#B", "B#F#B", "B#F#B", "B#F#B", "ABBBA")
                .aisle("CBBBC", "A###A", "A###A", "A###A", "A###A", "A###A", "CBBBC")
                .aisle(" CAC ", " ABA ", " ABA ", " ASA ", " ABA ", " ABA ", " CAC ")
                .where('S', this.selfPredicate())
                .where('A', states(getCasingState()))
                .where('B', states(getSecondCasingState())
                        .or(autoAbilities()))
                .where('C', states(getThirdCasingState()))
                .where('F', CP_DM_CASING.get())
                .where('#', air())
                .where(' ', any())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return iMultiblockPart == null ? GTQTTextures.ULTIMATE_HIGH_ENERGY_CASING : Textures.HIGH_POWER_CASING;
    }

    @SideOnly(Side.CLIENT)

    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.DIMENSIONALLY_TRANSCENDENT_PLASMA_FORGE_OVERLAY;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    protected boolean shouldShowVoidingModeButton() {
        return true;
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle(" CAC ", " AMA ", " AXA ", " ABA ", " ABA ", " ABA ", " CAC ")
                .aisle("CBBBC", "A###A", "A###A", "A###A", "A###A", "A###A", "CBBBC")
                .aisle("ABBBA", "B#F#B", "B#F#B", "B#F#B", "B#F#B", "B#F#B", "ABBBA")
                .aisle("CBBBC", "A###A", "A###A", "A###A", "A###A", "A###A", "CBBBC")
                .aisle(" CAC ", " ALA ", " AKA ", " ASA ", " AJA ", " AIA ", " CAC ")
                .where('S', DIMENSIONAL_MIXER, EnumFacing.SOUTH)
                .where('A', getCasingState())
                .where('B', getSecondCasingState())
                .where('I', MetaTileEntities.ITEM_IMPORT_BUS[UHV], EnumFacing.SOUTH)
                .where('J', MetaTileEntities.ITEM_EXPORT_BUS[UHV], EnumFacing.SOUTH)
                .where('K', MetaTileEntities.FLUID_IMPORT_HATCH[UHV], EnumFacing.SOUTH)
                .where('L', MetaTileEntities.FLUID_EXPORT_HATCH[UHV], EnumFacing.SOUTH)
                .where('M', MetaTileEntities.AUTO_MAINTENANCE_HATCH, EnumFacing.NORTH)
                .where('X', MetaTileEntities.LASER_INPUT_HATCH_256[0], EnumFacing.NORTH)
                .where('C', getThirdCasingState())
                .where('#', Blocks.AIR.getDefaultState());
        MultiblockShapeInfo.Builder finalBuilder = builder;
        GTQTAPI.MAP_DC_CASING.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> ((WrappedIntTired) entry.getValue()).getIntTier()))
                .forEach(entry -> shapeInfo.add(finalBuilder.where('F', entry.getKey()).build()));
        return shapeInfo;
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.BLINKING_RED + I18n.format("我是搅拌机"));
    }

    public class DimensionalMixerRecipeLogic extends MultiblockRecipeLogic {

        public DimensionalMixerRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

    }
}
