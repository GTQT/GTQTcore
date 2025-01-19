package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.*;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.*;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.pattern.GTQTTraceabilityPredicate;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static keqing.gtqtcore.api.utils.GTQTUniversUtil.consistentList;
import static keqing.gtqtcore.api.utils.GTQTUniversUtil.maxLength;

public class MetaTileEntityCokingTower extends RecipeMapMultiblockController {
    private static final TraceabilityPredicate IS_SNOW_LAYER = new TraceabilityPredicate(bws -> GTUtility.isBlockSnow(bws.getBlockState()));
    private static boolean init = false;
    protected int heatingCoilLevel;
    protected int coilTier;
    private byte auxiliaryBlastFurnaceNumber = 0;

    public MetaTileEntityCokingTower(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.PYROLYSE_RECIPES);
        this.recipeMapWorkable = new CokingTowerRecipeLogic(this);
        initMap();
    }

    private static IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST);
    }

    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.BlueSteel).getBlock(Materials.BlueSteel);
    }

    private static IBlockState getBoilerState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE);
    }

    private static IBlockState getFireBoxState() {
        return MetaBlocks.BOILER_FIREBOX_CASING.getState(BlockFireboxCasing.FireboxCasingType.TUNGSTENSTEEL_FIREBOX);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCokingTower(metaTileEntityId);
    }

    private void initMap() {
        if (init) return;

        List<IBlockState> listCoil = GregTechAPI.HEATING_COILS.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().getTier()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        int maxLeng = maxLength(new ArrayList<List<IBlockState>>() {{
            add(listCoil);
        }});
        consistentList(listCoil, maxLeng);

        init = true;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
            this.coilTier = ((IHeatingCoilBlockStats) coilType).getTier();
        } else {
            this.heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
            this.coilTier = BlockWireCoil.CoilType.CUPRONICKEL.getTier();
        }
        if (context.get("AuxiliaryBlastFurnace1") != null) {
            auxiliaryBlastFurnaceNumber += 1;
        }
        if (context.get("AuxiliaryBlastFurnace2") != null) {
            auxiliaryBlastFurnaceNumber += 1;
        }
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        heatingCoilLevel = 0;
        auxiliaryBlastFurnaceNumber = 0;
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("     DDD     ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ")
                .aisle("    CDDDC    ", "    CDDDC    ", "    CDDDC    ", "     DDD     ", "             ", "             ", "             ", "             ", "             ")
                .aisle("AAAGDDDDDJFFF", "GGG DhhhD JJJ", " G  D###D  J ", " G  DhhhD  J ", " G   DDD   J ", " G    D    J ", "      D      ", "      D      ", "      D      ")
                .aisle("AAAGDDDDDJFFF", "G@GHDhhhDIJ$J", "G G D###D J J", "G*G Dh#hD J!J", "G*G D###D J!J", "G*G  D#D  J!J", "     D#D     ", "     D#D     ", "     DRD     ")
                .aisle("AAAGDDDDDJFFF", "GGG DhhhD JJJ", " G  D###D  J ", " G  DhhhD  J ", " G   DDD   J ", " G    D    J ", "      D      ", "      D      ", "      D      ")
                .aisle("    CDDDC    ", "    CDSDC    ", "    CDDDC    ", "     DDD     ", "             ", "             ", "             ", "             ", "             ")
                .aisle("     DDD     ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ")
                .where('S', selfPredicate())
                .where('A', GTQTTraceabilityPredicate.optionalStates("AuxiliaryBlastFurnace1", getFireBoxState()))
                .where('C', states(getFrameState()))
                .where('h', heatingCoils())
                .where('D', states(getCasingState())
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMinGlobalLimited(1).setMaxGlobalLimited(2)))
                .where('R', abilities(MultiblockAbility.MUFFLER_HATCH).setExactLimit(1))
                .where('F', GTQTTraceabilityPredicate.optionalStates("AuxiliaryBlastFurnace2", getFireBoxState()))
                .where('G', GTQTTraceabilityPredicate.optionalStates("AuxiliaryBlastFurnace1", getCasingState()))
                .where('H', GTQTTraceabilityPredicate.optionalStates("AuxiliaryBlastFurnace1", getBoilerState()))
                .where('I', GTQTTraceabilityPredicate.optionalStates("AuxiliaryBlastFurnace2", getBoilerState()))
                .where('J', GTQTTraceabilityPredicate.optionalStates("AuxiliaryBlastFurnace2", getCasingState()))
                .where('#', air())
                .where('@', /*EPTraceabilityPredicate.optionalStates("AuxiliaryBlastFurnace1", )*/any())
                .where('*', /*EPTraceabilityPredicate.optionalStates("AuxiliaryBlastFurnace1", )*/any())
                .where('$', /*EPTraceabilityPredicate.optionalStates("AuxiliaryBlastFurnace2", )*/any())
                .where('!', /*EPTraceabilityPredicate.optionalStates("AuxiliaryBlastFurnace2", )*/any())
                .where(' ', any())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.ROBUST_TUNGSTENSTEEL_CASING;
    }

    @SideOnly(Side.CLIENT)

    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }

    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.machine.coking_tower.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.modify_overclock","Coil Tier"));
        tooltip.add(I18n.format("gtqtcore.machine.parallel.pow.machineTier",2,32));
    }

    protected int getCoilTier() {
        return this.coilTier;
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = null;
        if (Blocks.AIR != null) {
            builder = MultiblockShapeInfo.builder()
                    .aisle("     DDD     ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ")
                    .aisle("    CDDDC    ", "    CDbDC    ", "    CDDDC    ", "     DDD     ", "             ", "             ", "             ", "             ", "             ")
                    .aisle("AAAGDDDDDJFFF", "GGG DhhhD JJJ", " G  D   D  J ", " G  DhhhD  J ", " G   DDD   J ", " G    D    J ", "      D      ", "      D      ", "      D      ")
                    .aisle("AAAGDDDDDJFFF", "G GHDhhhDIJ J", "G G D   D J J", "G*G Dh hD J!J", "G G D   D J J", "G G  D D  J J", "     D D     ", "     D D     ", "     DRD     ")
                    .aisle("AAAGDDDDDJFFF", "GGG DhhhD JJJ", " G  D   D  J ", " G  DhhhD  J ", " G   DDD   J ", " G    D    J ", "      D      ", "      D      ", "      D      ")
                    .aisle("    CDDDC    ", "    CXSYC    ", "    CxyaC    ", "     DDD     ", "             ", "             ", "             ", "             ", "             ")
                    .aisle("     DDD     ", "             ", "             ", "             ", "             ", "             ", "             ", "             ", "             ")
                    .where('S', GTQTMetaTileEntities.COKING_TOWER, EnumFacing.SOUTH)
                    .where('C', getFrameState())
                    .where('D', getCasingState())

                    .where('X', MetaTileEntities.ITEM_IMPORT_BUS[GTValues.EV], EnumFacing.SOUTH)
                    .where('Y', MetaTileEntities.ITEM_EXPORT_BUS[GTValues.EV], EnumFacing.SOUTH)

                    .where('x', MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.EV], EnumFacing.SOUTH)
                    .where('y', MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.EV], EnumFacing.SOUTH)

                    .where('a', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.EV], EnumFacing.SOUTH)

                    .where('b', MetaTileEntities.MAINTENANCE_HATCH, EnumFacing.NORTH)

                    .where('R', MetaTileEntities.MUFFLER_HATCH[GTValues.EV], EnumFacing.UP)
                    .where('h', MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.CUPRONICKEL))
                    .where(' ', Blocks.AIR.getDefaultState());
            shapeInfo.add(builder.build());
            shapeInfo.add(builder
                    .where('A', getFireBoxState())
                    .where('G', getCasingState())
                    .where('H', getBoilerState())
                    .build());
            shapeInfo.add(builder
                    .where('F', getFireBoxState())
                    .where('I', getBoilerState())
                    .where('J', getCasingState())
                    .build());

        }
        return shapeInfo;
    }

    protected class CokingTowerRecipeLogic extends MultiblockRecipeLogic {

        public CokingTowerRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public int getParallelLimit() {
            return Math.min((int)Math.pow(2, coilTier),128);
        }

        @Override
        protected void modifyOverclockPost(int[] resultOverclock, IRecipePropertyStorage storage) {
            super.modifyOverclockPost(resultOverclock, storage);

            int coilTier = ((MetaTileEntityCokingTower) metaTileEntity).getCoilTier();
            if (coilTier == -1)
                return;

            if (coilTier == 0) {
                // 75% speed with cupronickel (coilTier = 0)
                resultOverclock[1] = 4 * resultOverclock[1] / 3;
            } else {
                // each coil above kanthal (coilTier = 1) is 50% faster
                resultOverclock[1] = resultOverclock[1] * 2 / (coilTier + auxiliaryBlastFurnaceNumber);
            }

            resultOverclock[1] = Math.max(1, resultOverclock[1]);
        }
    }

}

