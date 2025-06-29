package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.GTValues;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.metatileentity.multiblock.ui.KeyManager;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIBuilder;
import gregtech.api.metatileentity.multiblock.ui.UISyncer;
import gregtech.api.pattern.*;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.GTUtility;
import gregtech.api.util.KeyUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.metatileentity.GTQTNoTierMultiblockController;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockCoolingCoil;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing5;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

import static keqing.gtqtcore.api.unification.GTQTMaterials.GelidCryotheum;

//冰箱
public class MetaTileEntityCryogenicFreezer extends GTQTNoTierMultiblockController {
    private static final Supplier<TraceabilityPredicate> COOLING_COILS = () -> new TraceabilityPredicate(blockWorldState -> {
        IBlockState state = blockWorldState.getBlockState();
        if (state.getBlock() instanceof BlockCoolingCoil) {
            BlockCoolingCoil.CoolingCoilType type = GTQTMetaBlocks.blockCoolingCoil.getState(state);
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
            .map(type -> new BlockInfo(GTQTMetaBlocks.blockCoolingCoil.getState(type)))
            .toArray(BlockInfo[]::new)
    );
    private final FluidStack GelidStack = GelidCryotheum.getFluid(1);
    private int temperature;

    public MetaTileEntityCryogenicFreezer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.VACUUM_RECIPES,
                GTQTcoreRecipeMaps.LOW_TEMP_ACTIVATOR_RECIPES
        });

        this.recipeMapWorkable = new CryogenicFreezerRecipeLogic(this);

        //setMaxParallel(auto);
        setMaxParallelFlag(true);
        //setTimeReduce(auto);
        setTimeReduceFlag(true);
        setOverclocking(3);
    }

    public static TraceabilityPredicate coolingCoils() {
        return COOLING_COILS.get();
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.ADVANCED_COLD_CASING);
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
                .where('X', GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.ADVANCED_COLD_CASING))
                .where('S', GTQTMetaTileEntities.CRYOGENIC_FREEZER, EnumFacing.SOUTH)
                .where('#', Blocks.AIR.getDefaultState())
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.MV], EnumFacing.SOUTH)
                .where('I', MetaTileEntities.ITEM_IMPORT_BUS[GTValues.LV], EnumFacing.SOUTH)
                .where('O', MetaTileEntities.ITEM_EXPORT_BUS[GTValues.LV], EnumFacing.SOUTH)
                .where('D', MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.LV], EnumFacing.EAST)
                .where('M', () -> ConfigHolder.machines.enableMaintenance ? MetaTileEntities.MAINTENANCE_HATCH : MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF), EnumFacing.NORTH);
        Arrays.stream(BlockCoolingCoil.CoolingCoilType.values())
                .sorted(Comparator.comparingInt(entry -> -entry.coilTemperature))
                .forEach(entry -> shapeInfo.add(builder.where('C', GTQTMetaBlocks.blockCoolingCoil.getState(entry)).build()));
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
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("大厂冻，小厂烧", new Object[0]));
        super.addInformation(stack, player, tooltip, advanced);
    }


    @Override
    protected void configureDisplayText(MultiblockUIBuilder builder) {
        builder.setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addEnergyUsageLine(this.getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                .addCustom(this::addCustomData)
                .addParallelsLine(recipeMapWorkable.getParallelLimit())
                .addWorkingStatusLine()
                .addProgressLine(recipeMapWorkable.getProgress(), recipeMapWorkable.getMaxProgress())
                .addRecipeOutputLine(recipeMapWorkable);
    }

    @Override
    public void addCustomData(KeyManager keyManager, UISyncer syncer) {
        super.addCustomData(keyManager, syncer);
        if (getInputFluidInventory() != null) {
            FluidStack fluidStack = getInputFluidInventory().drain(GelidCryotheum.getFluid(Integer.MAX_VALUE), false);
            int liquidOxygenAmount = fluidStack == null ? 0 : fluidStack.amount;
            keyManager.add(KeyUtil.lang(TextFormatting.GRAY, "gtqtcore.multiblock.vc.amount", syncer.syncString(TextFormattingUtil.formatNumbers((liquidOxygenAmount)))));
            if (isActive()) {
                keyManager.add(KeyUtil.lang(TextFormatting.GREEN, "gtqtcore.machine.cryogenic_freezer.subcooled"));
            }
        }
        var heatString = KeyUtil.number(TextFormatting.RED, syncer.syncInt(temperature), "K");
        keyManager.add(KeyUtil.lang(TextFormatting.GRAY, "gregtech.multiblock.blast_furnace.max_temperature", heatString));
    }

    @Override
    protected void configureWarningText(MultiblockUIBuilder builder) {
        super.configureWarningText(builder);
        builder.addCustom((manager, syncer) -> {
            if (isStructureFormed() && getInputFluidInventory() != null) {
                FluidStack fluidStack = getInputFluidInventory().drain(GelidCryotheum.getFluid(Integer.MAX_VALUE), false);
                int liquidOxygenAmount = syncer.syncInt(fluidStack == null ? 0 : fluidStack.amount);
                if (syncer.syncInt(liquidOxygenAmount) == 0) {
                    manager.add(KeyUtil.lang(TextFormatting.RED, "gtqtcore.machine.cryogenic_freezer.warning"));
                }
            }
        });
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
        setMaxParallel((int) Math.pow(4, getCoilTier()));
        setTimeReduce((100 - getCoilTier() * 10.0) / 100);
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

    public boolean drainColdStack(boolean sim) {
        IMultipleTankHandler inputTank = getInputFluidInventory();
        if (!sim && !isStructureFormed()) return false;
        if (inputTank != null) {
            if (GelidStack.isFluidStackIdentical(inputTank.drain(GelidStack, false))) {
                inputTank.drain(GelidStack, sim);
                return true;
            }
        }
        return false;
    }

    protected class CryogenicFreezerRecipeLogic extends GTQTMultiblockLogic {

        private final MetaTileEntityCryogenicFreezer freezer;

        public CryogenicFreezerRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
            this.freezer = (MetaTileEntityCryogenicFreezer) tileEntity;
        }

        @Override
        protected void updateRecipeProgress() {
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true)) {
                if (drainColdStack(false)) {
                    drainColdStack(true);
                    if (++this.progressTime > this.maxProgressTime) {
                        this.completeRecipe();
                    }
                } else {
                    decreaseProgress();
                }
                this.drawEnergy(this.recipeEUt, false);
            }
        }
    }
}
