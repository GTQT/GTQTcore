package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.GTValues;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.*;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockCoolingCoil;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing1;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;
import java.util.function.Supplier;

import static keqing.gtqtcore.api.unification.GTQTMaterials.GelidCryotheum;

//冰箱
public class MetaTileEntityCryogenicFreezer extends RecipeMapMultiblockController {
    private static final Supplier<TraceabilityPredicate> COOLING_COILS = () -> new TraceabilityPredicate(blockWorldState -> {
        IBlockState state = blockWorldState.getBlockState();
        if (state.getBlock() instanceof BlockCoolingCoil) {
            BlockCoolingCoil.CoolingCoilType type = GTQTMetaBlocks.COOLING_COIL.getState(state);
            Object currentCoil = blockWorldState.getMatchContext().getOrPut("CoolingCoilType", type);
            if (!currentCoil.equals(type)) {
                blockWorldState.setError(new PatternStringError("gregtech.multiblock.pattern.error.coils"));
                return false;
            }
            blockWorldState.getMatchContext().getOrPut("VABlock", new LinkedList<>()).add(blockWorldState.getPos());
            return true;
        }
        return false;
    }, () -> Arrays.stream(BlockCoolingCoil.CoolingCoilType.values())
            .map(type -> new BlockInfo(GTQTMetaBlocks.COOLING_COIL.getState(type)))
            .toArray(BlockInfo[]::new)
    );
    private final FluidStack GELID_STACK = GelidCryotheum.getFluid(2);
    private int temperature;

    public MetaTileEntityCryogenicFreezer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.VACUUM_RECIPES);
        this.recipeMapWorkable = new CryogenicFreezerRecipeLogic(this);
    }

    public static TraceabilityPredicate coolingCoils() {
        return COOLING_COILS.get();
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.ADVANCED_COLD_CASING);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCryogenicFreezer(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "CCC", "CCC", "XXX")
                .aisle("XXX", "X X", "C C", "C C", "XXX")
                .aisle("XXX", "XSX", "CCC", "CCC", "XXX")
                .where('S', this.selfPredicate())
                .where('C', coolingCoils())
                .where('X', states(getCasingState())
                        .setMinGlobalLimited(14)
                        .or(autoAbilities()))
                .where(' ', air())
                .build();
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle("XXM", "XXX", "CCC", "CCC", "XXX")
                .aisle("XXD", "X#X", "C#C", "C#C", "XXX")
                .aisle("IEO", "XSX", "CCC", "CCC", "XXX")
                .where('X', GTQTMetaBlocks.TURBINE_CASING1.getState(GTQTTurbineCasing1.TurbineCasingType.ADVANCED_COLD_CASING))
                .where('S', GTQTMetaTileEntities.CRYOGENIC_FREEZER, EnumFacing.SOUTH)
                .where('#', Blocks.AIR.getDefaultState())
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.MV], EnumFacing.SOUTH)
                .where('I', MetaTileEntities.ITEM_IMPORT_BUS[GTValues.LV], EnumFacing.SOUTH)
                .where('O', MetaTileEntities.ITEM_EXPORT_BUS[GTValues.LV], EnumFacing.SOUTH)
                .where('D', MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.LV], EnumFacing.EAST)
                .where('M', () -> ConfigHolder.machines.enableMaintenance ? MetaTileEntities.MAINTENANCE_HATCH : MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF), EnumFacing.NORTH);
        Arrays.stream(BlockCoolingCoil.CoolingCoilType.values())
                .sorted(Comparator.comparingInt(entry -> -entry.coilTemperature))
                .forEach(entry -> shapeInfo.add(builder.where('C', GTQTMetaBlocks.COOLING_COIL.getState(entry)).build()));
        return shapeInfo;
    }

    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = super.getDataInfo();
        list.add(new TextComponentTranslation("gregtech.multiblock.magnetic_refrigerator.min_temperature",
                new TextComponentTranslation(TextFormattingUtil.formatNumbers(temperature) + "K")
                        .setStyle(new Style().setColor(TextFormatting.BLUE))));
        return list;
    }

    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.ADV_COLD_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }

    @Override
    public void addInformation(ItemStack stack,
                               World player,
                               List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gtqtcore.machine.cryogenic_freezer.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.cryogenic_freezer.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.cryogenic_freezer.tooltip.3"));
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            MultiblockDisplayText.builder(textList, isStructureFormed())
                    .setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                    .addEnergyUsageLine(getEnergyContainer())
                    .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                    .addCustom(tl -> {
                        // Coil heat capacity line
                        if (isStructureFormed()) {
                            ITextComponent heatString = TextComponentUtil.stringWithColor(
                                    TextFormatting.RED,
                                    TextFormattingUtil.formatNumbers(temperature) + "K");

                            tl.add(TextComponentUtil.translationWithColor(
                                    TextFormatting.GRAY,
                                    "gregtech.multiblock.blast_furnace.max_temperature",
                                    heatString));


                        }
                    })
                    .addParallelsLine(recipeMapWorkable.getParallelLimit())
                    .addWorkingStatusLine()
                    .addProgressLine(recipeMapWorkable.getProgressPercent());
            if (getInputFluidInventory() != null) {
                FluidStack LubricantStack = getInputFluidInventory().drain(GelidCryotheum.getFluid(Integer.MAX_VALUE), false);
                int liquidOxygenAmount = LubricantStack == null ? 0 : LubricantStack.amount;
                textList.add(new TextComponentTranslation("gtqtcore.machine.cryogenic_freezer.amount", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));

                if (isActive()) {
                    textList.add(new TextComponentTranslation("gtqtcore.machine.cryogenic_freezer.subcooled"));
                }
            }
        }
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (isStructureFormed()) {
            FluidStack lubricantStack = getInputFluidInventory().drain(GelidCryotheum.getFluid(Integer.MAX_VALUE), false);
            if (lubricantStack == null || lubricantStack.amount == 0) {
                textList.add(new TextComponentTranslation("gtqtcore.machine.cryogenic_freezer.warning"));
            }
        }
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CoolingCoilType");
        if (type instanceof BlockCoolingCoil.CoolingCoilType) {
            this.temperature = ((BlockCoolingCoil.CoolingCoilType) type).coilTemperature;
        } else {
            this.temperature = BlockCoolingCoil.CoolingCoilType.MANGANESE_IRON_ARSENIC_PHOSPHIDE.coilTemperature;
        }
    }

    public int getCoilTier() {
        if (temperature == 160) return 1;
        if (temperature == 50) return 2;
        if (temperature == 1) return 3;
        return 0;
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.temperature = Integer.MAX_VALUE;
    }

    protected class CryogenicFreezerRecipeLogic extends MultiblockRecipeLogic {

        private final MetaTileEntityCryogenicFreezer freezer;

        public CryogenicFreezerRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
            this.freezer = (MetaTileEntityCryogenicFreezer) tileEntity;
        }

        @Override
        protected void modifyOverclockPost(int[] resultOverclock, IRecipePropertyStorage storage) {
            super.modifyOverclockPost(resultOverclock, storage);

            int coilTier = ((MetaTileEntityCryogenicFreezer) metaTileEntity).getCoilTier();
            if (coilTier == -1)
                return;

            if (coilTier == 0) {
                // 75% speed with cupronickel (coilTier = 0)
                resultOverclock[1] = 4 * resultOverclock[1] / 3;
            } else {
                // each coil above kanthal (coilTier = 1) is 50% faster
                resultOverclock[1] = resultOverclock[1] * 2 / coilTier;
            }

            resultOverclock[1] = Math.max(1, resultOverclock[1]);
        }

        public void setMaxProgress(int maxProgress) {
            this.maxProgressTime = maxProgressTime / 8;
        }

        @Override
        public int getParallelLimit() {
            return 64;
        }

        @Override
        protected void updateRecipeProgress() {
            if (canRecipeProgress && drawEnergy(recipeEUt / 8, true)) {
                drawEnergy(recipeEUt / 8, false);
                IMultipleTankHandler inputTank = freezer.getInputFluidInventory();
                if (GELID_STACK.isFluidStackIdentical(inputTank.drain(GELID_STACK, false))) {
                    inputTank.drain(GELID_STACK, true);
                    if (++progressTime > maxProgressTime) {
                        completeRecipe();
                    }
                } else return;
                drawEnergy(recipeEUt / 8, false);
            }
        }
    }
}
