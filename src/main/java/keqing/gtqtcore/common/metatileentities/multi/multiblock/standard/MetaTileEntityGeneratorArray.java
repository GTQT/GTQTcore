package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import com.cleanroommc.modularui.api.drawable.IKey;
import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.MultiblockFuelRecipeLogic;
import gregtech.api.metatileentity.*;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIBuilder;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.api.util.KeyUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing1;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.GTValues.V;

public class MetaTileEntityGeneratorArray extends FuelMultiblockController implements IMachineHatchMultiblock {
    private final int tier;
    private boolean machineChanged;
    public MetaTileEntityGeneratorArray(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, null, tier);
        this.tier = tier;
        this.recipeMapWorkable = new ProcessingArrayWorkable(this);
    }

    @Override
    public boolean usesMui2() {
        return false;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityGeneratorArray(metaTileEntityId, tier);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        ((ProcessingArrayWorkable) this.recipeMapWorkable).findMachineStack();
    }

    @Override
    public int getMachineLimit() {
        return (int) Math.pow(2, tier + 1);
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        List<IEnergyContainer> energyContainer = new ArrayList<>(this.getAbilities(MultiblockAbility.OUTPUT_ENERGY));
        energyContainer.addAll(this.getAbilities(MultiblockAbility.OUTPUT_LASER));
        this.energyContainer = new EnergyContainerList(energyContainer);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "X#X", "XXX")
                .aisle("XXX", "XSX", "XXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState())
                        .setMinGlobalLimited(11)
                        .or(autoAbilities(false, true, true, true, true, true, true))
                        .or(abilities(MultiblockAbility.OUTPUT_ENERGY).setMaxGlobalLimited(4))
                        .or(abilities(MultiblockAbility.OUTPUT_LASER).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.MACHINE_HATCH).setExactLimit(1)))
                .where('#', air())
                .build();
    }

    public IBlockState getCasingState() {
        switch (this.tier) {
            case (2) -> {
                return GTQTMetaBlocks.blockMultiblockCasing1.getState(BlockMultiblockCasing1.CasingType.Talonite);
            }
            case (3) -> {
                return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TITANIUM_STABLE);
            }
            case (4) -> {
                return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST);
            }
            case (5) -> {
                return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.HSSE_STURDY);
            }
            default -> {
                return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        switch (this.tier) {
            case (2) -> {
                return GTQTTextures.TALONITE;
            }
            case (3) -> {
                return Textures.STABLE_TITANIUM_CASING;
            }
            case (4) -> {
                return Textures.ROBUST_TUNGSTENSTEEL_CASING;
            }
            case (5) -> {
                return Textures.STURDY_HSSE_CASING;
            }
            default -> {
                return Textures.HEAT_PROOF_CASING;
            }
        }
    }

    @Override
    protected void configureDisplayText(MultiblockUIBuilder builder) {
        ProcessingArrayWorkable logic = (ProcessingArrayWorkable) recipeMapWorkable;

        builder.setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addEnergyUsageLine(this.getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                .addCustom((manager, syncer) -> {
                    if (!isStructureFormed()) return;

                    // Machine mode text
                    // Shared text components for both states
                    IKey maxMachinesText = KeyUtil.number(TextFormatting.DARK_PURPLE,
                            syncer.syncInt(getMachineLimit()));
                    maxMachinesText = KeyUtil.lang(
                            "gregtech.machine.machine_hatch.machines_max", maxMachinesText);

                    if (syncer.syncBoolean(logic.activeRecipeMap == null)) {
                        // No machines in hatch
                        IKey noneText = KeyUtil.lang(TextFormatting.YELLOW,
                                "gregtech.machine.machine_hatch.machines_none");
                        IKey bodyText = KeyUtil.lang(
                                "gregtech.machine.machine_hatch.machines", noneText);
                        IKey hoverText1 = KeyUtil.lang(
                                "gregtech.machine.machine_hatch.machines_none_hover");
                        manager.add(KeyUtil.setHover(bodyText, hoverText1, maxMachinesText));
                    } else {
                        // Some amount of machines in hatch
                        String key = syncer.syncString(logic.getMachineStack().getTranslationKey());
                        IKey mapText = KeyUtil.lang(TextFormatting.DARK_PURPLE,
                                key + ".name");
                        mapText = KeyUtil.string(
                                TextFormatting.DARK_PURPLE,
                                "%sx %s",
                                syncer.syncInt(logic.getParallelLimit()), mapText);
                        IKey bodyText = KeyUtil.lang(
                                "gregtech.machine.machine_hatch.machines", mapText);
                        int tier = syncer.syncInt(logic.machineTier);
                        IKey voltageName = KeyUtil.string(GTValues.VNF[tier]);
                        int amps = syncer.syncInt(logic.getMachineStack().getCount());
                        String energyFormatted = TextFormattingUtil
                                .formatNumbers(GTValues.V[tier] * amps);
                        IKey hoverText = KeyUtil.lang(
                                TextFormatting.GRAY,
                                "gregtech.machine.machine_hatch.machines_max_eut",
                                energyFormatted, amps, voltageName);
                        manager.add(KeyUtil.setHover(bodyText, hoverText, maxMachinesText));
                    }

                    // Hatch locked status
                    if (syncer.syncBoolean(isActive())) {
                        manager.add(KeyUtil.lang(TextFormatting.DARK_RED,
                                "gregtech.machine.machine_hatch.locked"));
                    }
                })
                .addParallelsLine(recipeMapWorkable.getParallelLimit())
                .addWorkingStatusLine()
                .addProgressLine(recipeMapWorkable.getProgress(), recipeMapWorkable.getMaxProgress())
                .addRecipeOutputLine(recipeMapWorkable);
    }

    @SideOnly(Side.CLIENT)

    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @Override
    public void notifyMachineChanged() {
        machineChanged = true;
    }

    @Override
    public String[] getBlacklist() {
        return ConfigHolder.machines.processingArrayBlacklist;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_MECHANICAL;
    }

    @Override
    public SoundEvent getSound() {
        return GTSoundEvents.ARC;
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn,
                                               boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut,
                                               boolean checkMuffler) {
        TraceabilityPredicate predicate = super.autoAbilities(checkMaintenance, checkMuffler);

        predicate = predicate.or(checkEnergyIn ? abilities(MultiblockAbility.OUTPUT_ENERGY).setMaxGlobalLimited(4).setPreviewCount(1) : new TraceabilityPredicate());

        predicate = predicate.or(checkEnergyIn ? abilities(MultiblockAbility.OUTPUT_LASER).setMaxGlobalLimited(1) : new TraceabilityPredicate());

        predicate = predicate.or(abilities(MultiblockAbility.IMPORT_ITEMS).setPreviewCount(1));

        predicate = predicate.or(abilities(MultiblockAbility.EXPORT_ITEMS).setPreviewCount(1));

        predicate = predicate.or(abilities(MultiblockAbility.IMPORT_FLUIDS).setPreviewCount(1));

        predicate = predicate.or(abilities(MultiblockAbility.EXPORT_FLUIDS).setPreviewCount(1));

        predicate = predicate.or(abilities(MultiblockAbility.DUAL_IMPORT).setPreviewCount(1));

        predicate = predicate.or(abilities(MultiblockAbility.DUAL_EXPORT).setPreviewCount(1));

        return predicate;
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("你的多A动力仓在哪！", new Object[0]));
        tooltip.add(I18n.format("gregtech.universal.tooltip.parallel", getMachineLimit()));
        tooltip.add(I18n.format("整合机器内的单方块发电机输出能量！"));
        tooltip.add(I18n.format("动力仓增加的能量为小机器等级对应的电压*小机器的数量"));
        tooltip.add(I18n.format("需要注意的是实际输出的能量是按照动力仓等级计算的"));
        tooltip.add(I18n.format("支持使用激光源仓输出能量"));
    }

    @Override
    public int getItemOutputLimit() {
        ItemStack machineStack = ((ProcessingArrayWorkable) this.recipeMapWorkable).getMachineStack();
        MetaTileEntity mte = GTUtility.getMetaTileEntity(machineStack);
        return mte == null ? 0 : mte.getItemOutputLimit();
    }


    @SuppressWarnings("InnerClassMayBeStatic")
    protected class ProcessingArrayWorkable extends MultiblockFuelRecipeLogic {

        private static final ICleanroomProvider DUMMY_CLEANROOM = DummyCleanroom.createForAllTypes();

        ItemStack currentMachineStack = ItemStack.EMPTY;
        MetaTileEntity mte = null;
        // The Voltage Tier of the machines the PA is operating upon, from GTValues.V
        private int machineTier;
        // The maximum Voltage of the machines the PA is operating upon
        private long machineVoltage;
        // The Recipe Map of the machines the PA is operating upon
        private RecipeMap<?> activeRecipeMap;

        public ProcessingArrayWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        public long getMaximumOverclockVoltage() {
            return machineVoltage * getParallelLimit();
        }

        @Override
        public void invalidate() {
            super.invalidate();

            // invalidate mte's cleanroom reference
            if (mte != null && mte instanceof ICleanroomReceiver) {
                ((ICleanroomReceiver) mte).setCleanroom(null);
            }

            // Reset locally cached variables upon invalidation
            currentMachineStack = ItemStack.EMPTY;
            mte = null;
            machineChanged = true;
            machineTier = 0;
            machineVoltage = 0L;
            activeRecipeMap = null;
        }

        @Override
        protected boolean shouldSearchForRecipes() {
            return canWorkWithMachines() && super.shouldSearchForRecipes();
        }

        @Override
        public boolean isAllowOverclocking() {
            return true;
        }

        public boolean canWorkWithMachines() {
            if (machineChanged) {
                findMachineStack();
                machineChanged = false;
                previousRecipe = null;
                if (isDistinct()) {
                    invalidatedInputList.clear();
                } else {
                    invalidInputsForRecipes = false;
                }
            }
            return (!currentMachineStack.isEmpty() && this.activeRecipeMap != null);
        }

        @Override
        public int getParallelLimit() {
            return (currentMachineStack == null || currentMachineStack.isEmpty()) ? getMachineLimit() :
                    Math.min(currentMachineStack.getCount(), getMachineLimit());
        }

        protected long getMaxParallelVoltage() {
            return machineVoltage * getParallelLimit();
        }

        @Override
        public RecipeMap<?> getRecipeMap() {
            return activeRecipeMap;
        }

        @Override
        protected Recipe findRecipe(long maxVoltage, IItemHandlerModifiable inputs, IMultipleTankHandler fluidInputs) {
            return super.findRecipe(Math.min(super.getMaxVoltage(), this.machineVoltage), inputs, fluidInputs);
        }

        public void findMachineStack() {
            RecipeMapMultiblockController controller = (RecipeMapMultiblockController) this.metaTileEntity;

            // The Processing Array is limited to 1 Machine Interface per multiblock, and only has 1 slot
            ItemStack machine = controller.getAbilities(MultiblockAbility.MACHINE_HATCH).get(0).getStackInSlot(0);
            mte = GTUtility.getMetaTileEntity(machine);

            if (mte != null && mte instanceof SimpleGeneratorMetaTileEntity) {
                this.activeRecipeMap = mte.getRecipeMap();
                // Set the world for MTEs, as some need it for checking their recipes
                MetaTileEntityHolder holder = new MetaTileEntityHolder();
                mte = holder.setMetaTileEntity(mte);
                holder.setWorld(this.metaTileEntity.getWorld());

                // Set the cleanroom of the MTEs to the PA's cleanroom reference
                if (mte instanceof ICleanroomReceiver receiver) {
                    if (ConfigHolder.machines.cleanMultiblocks) {
                        receiver.setCleanroom(DUMMY_CLEANROOM);
                    } else {
                        ICleanroomProvider provider = controller.getCleanroom();
                        if (provider != null) receiver.setCleanroom(provider);
                    }
                }

                // Find the voltage tier of the machine.
                this.machineTier = mte instanceof ITieredMetaTileEntity ? ((ITieredMetaTileEntity) mte).getTier() : 0;

                this.machineVoltage = V[this.machineTier];

                this.currentMachineStack = machine;
            }
        }

        @Override
        public boolean checkRecipe(Recipe recipe) {
            if (mte == null) return false;
            return super.checkRecipe(recipe);
        }

        private ItemStack getMachineStack() {
            return currentMachineStack;
        }

    }
}