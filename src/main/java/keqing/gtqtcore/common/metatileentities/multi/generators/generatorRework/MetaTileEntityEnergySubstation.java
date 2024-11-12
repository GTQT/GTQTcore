package keqing.gtqtcore.common.metatileentities.multi.generators.generatorRework;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import com.google.common.annotations.VisibleForTesting;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.*;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.GTQTAPI;
import keqing.gtqtcore.api.metaileentity.multiblock.ICellData;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTNuclearFusion;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.math.BigInteger;
import java.time.Duration;
import java.util.*;
import java.util.function.Supplier;

import static gregtech.api.util.RelativeDirection.FRONT;
import static gregtech.api.util.RelativeDirection.RIGHT;
import static gregtech.api.util.RelativeDirection.UP;
import static keqing.gtqtcore.api.utils.BigMath.summarizedValue;
import static keqing.gtqtcore.api.utils.GTQTUniverUtil.HOUR;

//  TODO Wireless Mode (allowed this machine IO Wireless Energy Network if this mode is enabled).
public class MetaTileEntityEnergySubstation extends MultiblockWithDisplayBase implements IControllable, IProgressBarMultiblock {

    //  Cell layer range: 3 < x < 18 (seems same like Power Substation).
    public static final int MAX_CELL_LAYERS = 18;

    //  Passive drain (1% capacity per 24 hours)
    public static final long PASSIVE_DRAIN_DIVISOR = HOUR * 24 * 100;
    public static final long PASSIVE_DRAIN_MAX_PER_STORAGE = 100000L;

    private static final String NBT_ENERGY_BANK = "EnergyBank";
    private static final String PMC_CELL_HEADER = "Cell_";

    private EnergyBank energyBank;
    private EnergyContainerList inputHatches;
    private EnergyContainerList outputHatches;
    private long passiveDrain;
    private boolean isActive, isWorkingEnabled = true;

    private long netInLastSec;
    private long averageInLastSec;
    private long netOutLastSec;
    private long averageOutLastSec;

    public MetaTileEntityEnergySubstation(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityEnergySubstation(metaTileEntityId);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        List<IEnergyContainer> inputs = new ArrayList<>();
        inputs.addAll(getAbilities(MultiblockAbility.INPUT_ENERGY));
        inputs.addAll(getAbilities(MultiblockAbility.SUBSTATION_INPUT_ENERGY));
        inputs.addAll(getAbilities(MultiblockAbility.INPUT_LASER));
        this.inputHatches = new EnergyContainerList(inputs);

        List<IEnergyContainer> outputs = new ArrayList<>();
        outputs.addAll(getAbilities(MultiblockAbility.OUTPUT_ENERGY));
        outputs.addAll(getAbilities(MultiblockAbility.SUBSTATION_OUTPUT_ENERGY));
        outputs.addAll(getAbilities(MultiblockAbility.OUTPUT_LASER));
        this.outputHatches = new EnergyContainerList(outputs);

        List<ICellData> parts = new ArrayList<>();
        for (Map.Entry<String, Object> cell : context.entrySet()) {
            if (cell.getKey().startsWith(PMC_CELL_HEADER) &&
                    cell.getValue() instanceof CellMatchWrapper wrapper) {
                for (int i = 0; i < wrapper.amount; i++) {
                    parts.add(wrapper.cellTier);
                }
            }
        }

        if (parts.isEmpty()) {
            // only empty cells found in the structure
            invalidateStructure();
            return;
        }

        if (this.energyBank == null) {
            this.energyBank = new EnergyBank(parts);
        } else {
            this.energyBank = energyBank.rebuild(parts);
        }

        this.passiveDrain = this.energyBank.getPassiveDrainPerTick();
    }

    @Override
    public void invalidateStructure() {
        // don't null out energyBank since it holds the stored energy, which
        // we need to hold on to across rebuilds to not void all energy if a
        // multiblock part or block other than the controller is broken.
        inputHatches = null;
        outputHatches = null;
        passiveDrain = 0;
        netInLastSec = 0;
        averageInLastSec = 0;
        netOutLastSec = 0;
        averageOutLastSec = 0;
        super.invalidateStructure();
    }

    @Override
    protected void updateFormedValid() {
        if (!getWorld().isRemote) {
            if (getOffsetTimer() % 20 == 0) {
                // active here is just used for rendering
                setActive(energyBank.hasEnergy());
                averageInLastSec = netInLastSec / 20;
                averageOutLastSec = netOutLastSec / 20;
                netInLastSec = 0;
                netOutLastSec = 0;
            }

            if (isWorkingEnabled()) {
                // Bank from Energy Input Hatches
                long energyBanked = energyBank.fill(inputHatches.getEnergyStored());
                inputHatches.changeEnergy(-energyBanked);
                netInLastSec += energyBanked;

                // Passive drain
                long energyPassiveDrained = energyBank.drain(getPassiveDrain());
                netOutLastSec += energyPassiveDrained;

                // Debank to Dynamo Hatches
                long energyDebanked = energyBank
                        .drain(outputHatches.getEnergyCapacity() - outputHatches.getEnergyStored());
                outputHatches.changeEnergy(energyDebanked);
                netOutLastSec += energyDebanked;
            }
        }
    }

    public long getPassiveDrain() {
        if (ConfigHolder.machines.enableMaintenance) {
            int multiplier = 1 + getNumMaintenanceProblems();
            double modifier = getMaintenanceDurationMultiplier();
            return (long) (passiveDrain * multiplier * modifier);
        }
        return passiveDrain;
    }

    @Override
    public boolean isActive() {
        return super.isActive() && this.isActive;
    }

    public void setActive(boolean active) {
        if (this.isActive != active) {
            this.isActive = active;
            markDirty();
            World world = getWorld();
            if (world != null && !world.isRemote) {
                writeCustomData(GregtechDataCodes.WORKABLE_ACTIVE, buf -> buf.writeBoolean(active));
            }
        }
    }

    @Override
    public boolean isWorkingEnabled() {
        return this.isWorkingEnabled;
    }

    @Override
    public void setWorkingEnabled(boolean isWorkingAllowed) {
        this.isWorkingEnabled = isWorkingAllowed;
        markDirty();
        World world = getWorld();
        if (world != null && !world.isRemote) {
            writeCustomData(GregtechDataCodes.WORKING_ENABLED, buf -> buf.writeBoolean(isWorkingEnabled));
        }
    }

    @Override
    protected boolean shouldShowVoidingModeButton() {
        return false;
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("XXSXX", "XXXXX", "XXXXX", "XXXXX", "XXXXX")
                .aisle("XXXXX", "XBBBX", "XBBBX", "XBBBX", "XXXXX")
                .aisle("GGGGG", "GBBBG", "GBBBG", "GBBBG", "GGGGG").setRepeatable(3, MAX_CELL_LAYERS-1)
                .aisle("XXXXX", "XXXXX", "XXXXX", "XXXXX", "XXXXX")
                .where('S', this.selfPredicate())
                .where('X', states(getCasingState())
                        .setMinGlobalLimited(20)
                        .or(maintenancePredicate())
                        .or(abilities(MultiblockAbility.INPUT_ENERGY,
                                MultiblockAbility.SUBSTATION_INPUT_ENERGY,
                                MultiblockAbility.INPUT_LASER)
                                .setMinGlobalLimited(1))
                        .or(abilities(MultiblockAbility.OUTPUT_ENERGY,
                                MultiblockAbility.SUBSTATION_OUTPUT_ENERGY,
                                MultiblockAbility.OUTPUT_LASER)
                                .setMinGlobalLimited(1)))
                .where('G', states(getGlassState()))
                .where('B', CELL_PREDICATE.get())
                .build();
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        List<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle("CCMCC", "CCCCC", "GGGGG", "GGGGG", "GGGGG", "CCCCC")
                .aisle("CCCCC", "CBBBC", "GBBBG", "GBBBG", "GBBBG", "CCCCC")
                .aisle("CCCCC", "CBBBC", "GBBBG", "GBBBG", "GBBBG", "CCCCC")
                .aisle("CCCCC", "CBBBC", "GBBBG", "GBBBG", "GBBBG", "CCCCC")
                .aisle("INSTO", "CCCCC", "GGGGG", "GGGGG", "GGGGG", "CCCCC")
                .where('S', GTQTMetaTileEntities.ENERGY_SUBSTATION, EnumFacing.SOUTH)
                .where('C', getCasingState())
                .where('G', getGlassState())
                .where('I', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.HV], EnumFacing.SOUTH)
                .where('N', MetaTileEntities.SUBSTATION_ENERGY_INPUT_HATCH[0], EnumFacing.SOUTH)
                .where('O', MetaTileEntities.ENERGY_OUTPUT_HATCH[GTValues.HV], EnumFacing.SOUTH)
                .where('T', MetaTileEntities.SUBSTATION_ENERGY_OUTPUT_HATCH[0], EnumFacing.SOUTH)
                .where('M', () -> ConfigHolder.machines.enableMaintenance ? MetaTileEntities.MAINTENANCE_HATCH : getCasingState(), EnumFacing.NORTH);
        GTQTAPI.MAP_ES_CELLS.entrySet().stream()
                .filter(entry -> entry.getValue().getCapacity() > 0)
                .sorted(Comparator.comparingInt(entry -> entry.getValue().getTier()))
                .forEach(entry -> shapeInfo.add(builder.where('B', entry.getKey()).build()));
        return shapeInfo;
    }

    protected IBlockState getCasingState() {
        return GTQTMetaBlocks.NUCLEAR_FUSION.getState(GTQTNuclearFusion.CasingType.ENERGY_CELL);
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }

    protected static final Supplier<TraceabilityPredicate> CELL_PREDICATE = () -> new TraceabilityPredicate(
            blockWorldState -> {
                IBlockState state = blockWorldState.getBlockState();
                if (GTQTAPI.MAP_ES_CELLS.containsKey(state)) {
                    ICellData cell = GTQTAPI.MAP_ES_CELLS.get(state);
                    // Allow unfilled batteries in the structure, but do not add them to match context.
                    // This lets you use empty batteries as "filler slots" for convenience if desired.
                    if (cell.getTier() != -1 && cell.getCapacity() > 0) {
                        String key = PMC_CELL_HEADER + cell.getCellName();
                        CellMatchWrapper wrapper = blockWorldState.getMatchContext().get(key);
                        if (wrapper == null) wrapper = new CellMatchWrapper(cell);
                        blockWorldState.getMatchContext().set(key, wrapper.increment());
                    }
                    return true;
                }
                return false;
            }, () -> GTQTAPI.MAP_ES_CELLS.entrySet().stream()
            .sorted(Comparator.comparingInt(entry -> entry.getValue().getTier()))
            .map(entry -> new BlockInfo(entry.getKey(), null))
            .toArray(BlockInfo[]::new));

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GTQTTextures.ENERGY_CELL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.POWER_SUBSTATION_OVERLAY;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState,
                                     Matrix4 translation,
                                     IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.isActive(), this.isWorkingEnabled());
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, this.isStructureFormed())
                .setWorkingStatus(true, this.isActive() && this.isWorkingEnabled()) // transform into two-state system for display
                .setWorkingStatusKeys(
                        "gtqtcore.machine.energy_substation.idling",
                        "gtqtcore.machine.energy_substation.paused",
                        "gtqtcore.machine.energy_substation.running")
                .addCustom(tl -> {
                    if (this.isStructureFormed() && energyBank != null) {
                        BigInteger energyStored = energyBank.getStored();
                        BigInteger energyCapacity = energyBank.getCapacity();

                        // Stored EU line
                        ITextComponent storedFormatted = TextComponentUtil.stringWithColor(
                                TextFormatting.GOLD,
                                TextFormattingUtil.formatNumbers(energyStored) + " EU");
                        tl.add(TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "gtqtcore.machine.energy_substation.stored",
                                storedFormatted));

                        // EU Capacity line
                        ITextComponent capacityFormatted = TextComponentUtil.stringWithColor(
                                TextFormatting.GOLD,
                                TextFormattingUtil.formatNumbers(energyCapacity) + " EU");
                        tl.add(TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "gtqtcore.machine.energy_substation.capacity",
                                capacityFormatted));

                        // Passive Drain line
                        ITextComponent passiveDrain = TextComponentUtil.stringWithColor(
                                TextFormatting.DARK_RED,
                                TextFormattingUtil.formatNumbers(getPassiveDrain()) + " EU/t");
                        tl.add(TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "gregtech.multiblock.power_substation.passive_drain",
                                passiveDrain));

                        // Average EU IN line
                        ITextComponent avgValue = TextComponentUtil.stringWithColor(
                                TextFormatting.GREEN,
                                TextFormattingUtil.formatNumbers(averageInLastSec) + " EU/t");
                        ITextComponent base = TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "gregtech.multiblock.power_substation.average_in",
                                avgValue);
                        ITextComponent hover = TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "gtqtcore.machine.energy_substation.average_in_hover");
                        tl.add(TextComponentUtil.setHover(base, hover));

                        // Average EU OUT line
                        avgValue = TextComponentUtil.stringWithColor(
                                TextFormatting.RED,
                                TextFormattingUtil.formatNumbers(averageOutLastSec) + " EU/t");
                        base = TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "gregtech.multiblock.power_substation.average_out",
                                avgValue);
                        hover = TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "gtqtcore.machine.energy_substation.average_out_hover");
                        tl.add(TextComponentUtil.setHover(base, hover));

                        // Time to fill/drain line
                        if (averageInLastSec > averageOutLastSec) {
                            ITextComponent timeToFill = getTimeToFillDrainText(energyCapacity.subtract(energyStored)
                                    .divide(BigInteger.valueOf((averageInLastSec - averageOutLastSec) * 20)));
                            TextComponentUtil.setColor(timeToFill, TextFormatting.GREEN);
                            tl.add(TextComponentUtil.translationWithColor(
                                    TextFormatting.GRAY,
                                    "gregtech.multiblock.power_substation.time_to_fill",
                                    timeToFill));
                        } else if (averageInLastSec < averageOutLastSec) {
                            ITextComponent timeToDrain = getTimeToFillDrainText(
                                    energyStored.divide(BigInteger.valueOf(
                                            (averageOutLastSec - averageInLastSec) * 20)));
                            TextComponentUtil.setColor(timeToDrain, TextFormatting.RED);
                            tl.add(TextComponentUtil.translationWithColor(
                                    TextFormatting.GRAY,
                                    "gregtech.multiblock.power_substation.time_to_drain",
                                    timeToDrain));
                        }
                    }
                })
                .addWorkingStatusLine();
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (isStructureFormed()) {
            if (averageInLastSec < averageOutLastSec) { // decreasing
                BigInteger timeToDrainSeconds = energyBank.getStored()
                        .divide(BigInteger.valueOf((averageOutLastSec - averageInLastSec) * 20));
                if (timeToDrainSeconds.compareTo(BigInteger.valueOf(60 * 60)) < 0) { // less than 1 hour left
                    textList.add(TextComponentUtil.translationWithColor(
                            TextFormatting.YELLOW,
                            "gregtech.multiblock.power_substation.under_one_hour_left"));
                }
            }
        }
    }

    private static ITextComponent getTimeToFillDrainText(BigInteger timeToFillSeconds) {
        if (timeToFillSeconds.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
            // too large to represent in a java Duration
            timeToFillSeconds = BigInteger.valueOf(Long.MAX_VALUE);
        }

        Duration duration = Duration.ofSeconds(timeToFillSeconds.longValue());
        String key;
        long fillTime;
        if (duration.getSeconds() <= 180) {
            fillTime = duration.getSeconds();
            key = "gregtech.multiblock.power_substation.time_seconds";
        } else if (duration.toMinutes() <= 180) {
            fillTime = duration.toMinutes();
            key = "gregtech.multiblock.power_substation.time_minutes";
        } else if (duration.toHours() <= 72) {
            fillTime = duration.toHours();
            key = "gregtech.multiblock.power_substation.time_hours";
        } else if (duration.toDays() <= 730) { // 2 years
            fillTime = duration.toDays();
            key = "gregtech.multiblock.power_substation.time_days";
        } else if (duration.toDays() / 365 < 1_000_000) {
            fillTime = duration.toDays() / 365;
            key = "gregtech.multiblock.power_substation.time_years";
        } else {
            return new TextComponentTranslation("gregtech.multiblock.power_substation.time_forever");
        }

        return new TextComponentTranslation(key, TextFormattingUtil.formatNumbers(fillTime));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setBoolean("isActive", isActive);
        data.setBoolean("isWorkingEnabled", isWorkingEnabled);
        if (energyBank != null) {
            data.setTag(NBT_ENERGY_BANK, energyBank.writeToNBT(new NBTTagCompound()));
        }
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        isActive = data.getBoolean("isActive");
        isWorkingEnabled = data.getBoolean("isWorkingEnabled");
        if (data.hasKey(NBT_ENERGY_BANK)) {
            energyBank = new EnergyBank(data.getCompoundTag(NBT_ENERGY_BANK));
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(isActive);
        buf.writeBoolean(isWorkingEnabled);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        isActive = buf.readBoolean();
        isWorkingEnabled = buf.readBoolean();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GregtechDataCodes.WORKABLE_ACTIVE) {
            isActive = buf.readBoolean();
            scheduleRenderUpdate();
        } else if (dataId == GregtechDataCodes.WORKING_ENABLED) {
            isWorkingEnabled = buf.readBoolean();
            scheduleRenderUpdate();
        }
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE) {
            return GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this);
        }
        return super.getCapability(capability, side);
    }

    @Override
    public void addInformation(ItemStack stack,
                               World world,
                               List<String> tooltip,
                               boolean advanced) {
        tooltip.add(I18n.format("gtqtcore.machine.energy_substation.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.energy_substation.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.energy_substation.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.machine.energy_substation.tooltip.4", MAX_CELL_LAYERS));
        tooltip.add(I18n.format("gtqtcore.machine.energy_substation.tooltip.5"));
        tooltip.add(I18n.format("gtqtcore.machine.energy_substation.tooltip.6", PASSIVE_DRAIN_MAX_PER_STORAGE));
        tooltip.add(I18n.format("gtqtcore.machine.energy_substation.tooltip.7"));
        tooltip.add(I18n.format("gtqtcore.machine.energy_substation.tooltip.8"));
    }

    public String getStored() {
        if (energyBank == null) {
            return "0";
        }
        return TextFormattingUtil.formatNumbers(energyBank.getStored());
    }

    public long getStoredLong() {
        if (energyBank == null) {
            return 0;
        }
        return energyBank.getStored().longValue() & ~(1L << 63);
    }

    public long getCapacityLong() {
        if (energyBank == null) {
            return 0;
        }
        return energyBank.getCapacity().longValue() & ~(1L << 63);
    }

    public String getCapacity() {
        if (energyBank == null) {
            return "0";
        }
        return TextFormattingUtil.formatNumbers(energyBank.getCapacity());
    }

    public long getAverageInLastSec() {
        return averageInLastSec;
    }

    public long getAverageOutLastSec() {
        return averageOutLastSec;
    }

    @Override
    public double getFillPercentage(int index) {
        if (energyBank == null) return 0;
        return energyBank.getStored().doubleValue() / energyBank.getCapacity().doubleValue();
    }

    @Override
    public void addBarHoverText(List<ITextComponent> hoverList, int index) {
        String stored = energyBank != null ? TextFormattingUtil.formatNumbers(energyBank.getStored()) : "0";
        String capacity = energyBank != null ? TextFormattingUtil.formatNumbers(energyBank.getCapacity()) : "0";

        ITextComponent energyInfo = TextComponentUtil.stringWithColor(
                TextFormatting.YELLOW,
                stored + " / " + capacity + " EU");
        hoverList.add(TextComponentUtil.translationWithColor(
                TextFormatting.GRAY,
                "gregtech.multiblock.energy_stored",
                energyInfo));
    }

    public static class EnergyBank {

        private static final String NBT_SIZE = "Size";
        private static final String NBT_STORED = "Stored";
        private static final String NBT_MAX = "Max";

        private final long[] storage;
        private final long[] maximums;
        private final BigInteger capacity;
        private int index;

        public EnergyBank(List<ICellData> cells) {
            storage = new long[cells.size()];
            maximums = new long[cells.size()];
            for (int i = 0; i < cells.size(); i++) {
                maximums[i] = cells.get(i).getCapacity();
            }
            capacity = summarizedValue(maximums);
        }

        public EnergyBank(NBTTagCompound storageTag) {
            int size = storageTag.getInteger(NBT_SIZE);
            storage = new long[size];
            maximums = new long[size];
            for (int i = 0; i < size; i++) {
                NBTTagCompound subtag = storageTag.getCompoundTag(String.valueOf(i));
                if (subtag.hasKey(NBT_STORED)) {
                    storage[i] = subtag.getLong(NBT_STORED);
                }
                maximums[i] = subtag.getLong(NBT_MAX);
            }
            capacity = summarizedValue(maximums);
        }

        private NBTTagCompound writeToNBT(NBTTagCompound compound) {
            compound.setInteger(NBT_SIZE, storage.length);
            for (int i = 0; i < storage.length; i++) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                if (storage[i] > 0) {
                    tagCompound.setLong(NBT_STORED, storage[i]);
                }
                tagCompound.setLong(NBT_MAX, maximums[i]);
                compound.setTag(String.valueOf(i), tagCompound);
            }
            return compound;
        }

        /** Rebuild energy storage with a new list of cells. **/
        public EnergyBank rebuild(List<ICellData> cells) {
            if (cells.isEmpty()) {
                throw new IllegalArgumentException("Cannot rebuild Energy Substation power bank with no cells!");
            }
            EnergyBank newPowerStorage = new EnergyBank(cells);
            for (long stored : storage) {
                newPowerStorage.fill(stored);
            }
            return newPowerStorage;
        }

        /** @return  amount filled into storage. **/
        public long fill(long amount) {
            if (amount < 0) {
                throw new IllegalArgumentException("Amount cannot be negative!");
            }

            // ensure index
            if (index != storage.length - 1 && storage[index] == maximums[index]) {
                index++;
            }

            long maxFill = Math.min(maximums[index] - storage[index], amount);

            // storage is completely full
            if (maxFill == 0 && index == storage.length - 1) {
                return 0;
            }

            // fill this "cells" as much as possible
            storage[index] += maxFill;
            amount -= maxFill;

            // try to fill other "cells" if necessary
            if (amount > 0 && index != storage.length - 1) {
                return maxFill + fill(amount);
            }

            // other fill not necessary, either because the storage is now completely full,
            // or we were able to consume all the energy in this "cell"
            return maxFill;
        }

        /** @return  amount drained from storage */
        public long drain(long amount) {
            if (amount < 0)
                throw new IllegalArgumentException("Amount cannot be negative!");

            // ensure index
            if (index != 0 && storage[index] == 0) {
                index--;
            }

            long maxDrain = Math.min(storage[index], amount);

            // storage is completely empty
            if (maxDrain == 0 && index == 0) {
                return 0;
            }

            // drain this "cell" as much as possible
            storage[index] -= maxDrain;
            amount -= maxDrain;

            // try to drain other "cells" if necessary
            if (amount > 0 && index != 0) {
                index--;
                return maxDrain + drain(amount);
            }

            // other drain not necessary, either because the storage is now completely empty,
            // or we were able to drain all the energy from this "cell"
            return maxDrain;
        }

        public BigInteger getCapacity() {
            return capacity;
        }

        public BigInteger getStored() {
            return summarizedValue(storage);
        }

        public boolean hasEnergy() {
            for (long l : storage) {
                if (l > 0) return true;
            }
            return false;
        }

        @VisibleForTesting
        public long getPassiveDrainPerTick() {
            long[] maximumsExcl = new long[maximums.length];
            int index = 0;
            int numExcl = 0;
            for (long maximum : maximums) {
                if (maximum / PASSIVE_DRAIN_DIVISOR >= PASSIVE_DRAIN_MAX_PER_STORAGE) {
                    numExcl++;
                } else {
                    maximumsExcl[index++] = maximum;
                }
            }
            maximumsExcl = Arrays.copyOf(maximumsExcl, index);
            BigInteger capacityExcl = summarizedValue(maximumsExcl);

            return capacityExcl.divide(BigInteger.valueOf(PASSIVE_DRAIN_DIVISOR))
                    .add(BigInteger.valueOf(PASSIVE_DRAIN_MAX_PER_STORAGE * numExcl))
                    .longValue();
        }
    }

    private static class CellMatchWrapper {

        private final ICellData cellTier;
        private int amount;

        public CellMatchWrapper(ICellData cellTier) {
            this.cellTier = cellTier;
        }

        public CellMatchWrapper increment() {
            amount++;
            return this;
        }
    }
}