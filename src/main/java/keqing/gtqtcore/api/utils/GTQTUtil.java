package keqing.gtqtcore.api.utils;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.utils.Color;
import com.cleanroommc.modularui.utils.MouseData;
import gregtech.api.GTValues;
import gregtech.api.capability.INotifiableHandler;
import gregtech.api.capability.impl.GhostCircuitItemStackHandler;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.unification.material.Material;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.SmallDigits;
import gregtech.api.util.TextFormattingUtil;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.GCYSValues;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static gregtech.api.GTValues.*;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtsteam.common.item.GTSMetaitems.*;
import static net.minecraft.util.EnumFacing.*;

public class GTQTUtil {

    public static final Function<Integer, Integer> genericGeneratorTankSizeFunctionPlus = (tier) -> Math.min(16000 * (1 << tier - 1), 128000);

    public static final int[] VZ = new int[]{1, 30, 100, 500, 2000, 8000, 30000, 100000, 500000, 2000000, 8000000, 30000000, 100000000, 500000000, 2000000000};
    public static final Function<Integer, Integer> collectorTankSizeFunction = tier -> {
        if (tier <= GTValues.LV)
            return 16000;
        if (tier == GTValues.MV)
            return 24000;
        if (tier == GTValues.HV)
            return 32000;
        // EV+
        return 64000;
    };
    public static Integer baseTime = 100;

    public static double getAccelerateByCWU(int requestCWUt) {
        if (requestCWUt >= 2048) return 0.6;
        else if (requestCWUt >= 1972) return 0.65;
        else if (requestCWUt >= 1536) return 0.7;
        else if (requestCWUt >= 1280) return 0.78;
        else if (requestCWUt >= 1024) return 0.8;
        else if (requestCWUt >= 768) return 0.85;
        else if (requestCWUt >= 512) return 0.9;
        else if (requestCWUt >= 256) return 0.95;
        return 1.0;
    }

    @Nonnull
    public static ResourceLocation gtqtId(@Nonnull String path) {
        return new ResourceLocation("gtqtcore", path);
    }

    public static String getName(MetaItem.MetaValueItem is) {
        return is.getStackForm().getDisplayName();
    }

    public static String getName(Material mater) {
        return mater.getLocalizedName();
    }

    public static int intValueOfBitSet(BitSet set) {
        int result = 0;
        for (int i = 0; i < set.length(); i++) {
            result = result | (set.get(i) ? 1 : 0) << i;
        }
        return result;
    }

    public static BitSet forIntToBitSet(int i, int length) {
        return forIntToBitSet(i, length, new BitSet(length));
    }

    public static BitSet forIntToBitSet(int i, int length, BitSet result) {
        for (int j = 0; j < length; j++) {
            if (((i & (0b1 << j)) / (0b1 << j)) == 1) {
                result.set(j);
            } else {
                result.clear(j);
            }
        }
        return result;
    }

    public static <T> T getOrDefault(BooleanSupplier canGet, Supplier<T> getter, T defaultValue) {
        return canGet.getAsBoolean() ? getter.get() : defaultValue;
    }

    public static EnumFacing getFacingFromNeighbor(BlockPos pos, BlockPos neighbor) {
        BlockPos rel = neighbor.subtract(pos);
        if (rel.getX() == 1) {
            return EAST;
        }
        if (rel.getX() == -1) {
            return WEST;
        }
        if (rel.getY() == 1) {
            return UP;
        }
        if (rel.getY() == -1) {
            return DOWN;
        }
        if (rel.getZ() == 1) {
            return SOUTH;
        }
        return NORTH;
    }

    public static int getOrDefault(NBTTagCompound tag, String key, int defaultValue) {
        if (tag.hasKey(key)) {
            return tag.getInteger(key);
        }
        return defaultValue;
    }

    /**
     * Get {@code ResourceLocation} of other mod.
     *
     * @param modid Mod ID.
     * @param path  Name in {@code modid} namespace.
     * @return Namespace of {@code modid}.
     */
    @Nonnull
    public static ResourceLocation getId(String modid, @Nonnull String path) {
        return new ResourceLocation(modid, path);
    }

    /**
     * Get item stack by {@code modid} and {@code name} of item stack.
     *
     * @param modid Mod ID.
     * @param name  Item name.
     * @return Item stack in {@code modid} which named by {@code name}.
     */
    @Nonnull
    public static ItemStack getItemById(String modid, String name) {
        return GameRegistry.makeItemStack(modid + ":" + name, 0, 1, null);
    }

    /**
     * Get item stack by {@code modid} and {@code name} of item stack.
     *
     * @param modid  Mod ID.
     * @param name   Item name.
     * @param amount Amount of Item.
     * @return Allocate amount Item stack in {@code modid} which named by {@code name}.
     */
    @Nonnull
    public static ItemStack getItemById(String modid, String name, int amount) {
        return GameRegistry.makeItemStack(modid + ":" + name, 0, amount, null);
    }

    /**
     * Get item stack with NBT data by {@code modid} and {@code name} of item stack.
     *
     * @param modid Mod ID.
     * @param name  Item Name.
     * @param nbt   NBT data of Item.
     * @return Item stack with NBT data in {@code modid} which named by {@code name}.
     */
    @Nonnull
    public static ItemStack getItemById(String modid, String name, NBTTagCompound nbt) {
        return GameRegistry.makeItemStack(modid + ":" + name, 0, 1, nbt != null ? nbt.toString() : null);
    }

    /**
     * Get item stack with nbt data by {@code modid} and {@code name} of item stack.
     *
     * @param modid  Mod ID.
     * @param name   Item Name.
     * @param amount Amount of Item.
     * @param nbt    NBT data of Item.
     * @return Allocate amount Item stack with NBT data in {@code modid} which named by {@code name}.
     */
    @Nonnull
    public static ItemStack getItemById(String modid, String name, int amount, NBTTagCompound nbt) {
        return GameRegistry.makeItemStack(modid + ":" + name, 0, amount, nbt != null ? nbt.toString() : null);
    }

    /**
     * Get item stack by {@code modid} and {@code name} of item stack which has metadata.
     *
     * @param modid Mod ID.
     * @param name  Item name.
     * @param meta  Metadata of {@code name} item.
     * @return Item stack in {@code modid} which named by {@code name} and has metadata {@code meta}.
     */
    @Nonnull
    public static ItemStack getMetaItemById(String modid, String name, int meta) {
        return GameRegistry.makeItemStack(modid + ":" + name, meta, 1, null);
    }

    /**
     * Get item stack by {@code modid} and {@code name} of item stack which has metadata.
     *
     * @param modid  Mod ID.
     * @param name   Item name.
     * @param meta   Metadata of {@code name} item.
     * @param amount Amount of {@code name} item.
     * @return Allocate amount Item stack in {@code modid} which named by {@code name} and has metadata {@code meta}.
     */
    @Nonnull
    public static ItemStack getMetaItemById(String modid, String name, int meta, int amount) {
        return GameRegistry.makeItemStack(modid + ":" + name, meta, amount, null);
    }

    /**
     * Get item stack with NBT data by {@code modid} and {@code name} of item stack which has metadata.
     *
     * @param modid Mod ID.
     * @param name  Item name.
     * @param meta  Metadata of {@code name} item.
     * @param nbt   NBT data of item.
     * @return Item stack with NBT data in {@code modid} which named by {@code name} and has metadata {@code meta}.
     */
    @Nonnull
    public static ItemStack getMetaItemById(String modid, String name, int meta, NBTTagCompound nbt) {
        return GameRegistry.makeItemStack(modid + ":" + name, meta, 1, nbt != null ? nbt.toString() : null);
    }

    /**
     * Get item stack with NBT data by {@code modid} and {@code name} of item stack which has metadata.
     *
     * @param modid  Mod ID.
     * @param name   Item name.
     * @param meta   Metadata of {@code name} item.
     * @param amount Amount of {@code name} item.
     * @param nbt    NBT data of item.
     * @return Allocate amount Item stack with NBT data in {@code modid} which named by {@code name} and has metadata {@code meta}.
     */
    @Nonnull
    public static ItemStack getMetaItemById(String modid, String name, int meta, int amount, NBTTagCompound nbt) {
        return GameRegistry.makeItemStack(modid + ":" + name, meta, amount, nbt != null ? nbt.toString() : null);
    }

    /**
     * Get fluid stack by {@code name}.
     *
     * @param name Fluid name.
     * @return Fluid stack named by {@code name}.
     */
    @Nonnull
    public static FluidStack getFluidById(String name) {
        return Objects.requireNonNull(FluidRegistry.getFluidStack(name, 1000));
    }

    /**
     * Get fluid stack by {@code name}.
     *
     * @param name   Fluid name.
     * @param amount Amount of {@code name} fluid.
     * @return Allocate amount fluid stack named by {@code name}.
     */
    @Nonnull
    public static FluidStack getFluidById(String name, int amount) {
        return Objects.requireNonNull(FluidRegistry.getFluidStack(name, amount));
    }

    public static MetaTileEntityHolder getTileEntity(MetaTileEntity tile) {
        MetaTileEntityHolder holder = new MetaTileEntityHolder();
        holder.setMetaTileEntity(tile);
        holder.getMetaTileEntity().onPlacement();
        holder.getMetaTileEntity().setFrontFacing(EnumFacing.SOUTH);
        return holder;
    }

    /**
     * @param allowedStates Allowed Block States.
     * @return Used to build upgrade multiblock.
     */
    public static Supplier<BlockInfo[]> getCandidates(IBlockState... allowedStates) {
        return () -> Arrays.stream(allowedStates)
                .map(state -> new BlockInfo(state, null))
                .toArray(BlockInfo[]::new);
    }

    /**
     * @param metaTileEntities Allowed Meta Tile Entities.
     * @return Used to build upgrade multiblock.
     */
    public static Supplier<BlockInfo[]> getCandidates(MetaTileEntity... metaTileEntities) {
        return () -> Arrays.stream(metaTileEntities)
                .filter(Objects::nonNull)
                .map(tile -> new BlockInfo(MetaBlocks.MACHINE.getDefaultState(), getTileEntity(tile)))
                .toArray(BlockInfo[]::new);
    }

    /**
     * @param number Long number.
     * @return Just a rewrite of formatNumbers(),
     * please see {@link TextFormattingUtil#formatNumbers(long)}.
     */
    public static String formatNumbers(long number) {
        return TextFormattingUtil.formatNumbers(number);
    }

    /**
     * @param number Double number.
     * @return Just a rewrite of formatNumbers(),
     * please see {@link TextFormattingUtil#formatNumbers(double)}.
     */
    public static String formatNumbers(double number) {
        return TextFormattingUtil.formatNumbers(number);
    }

    public static void getCircuitSlotTooltip(SlotWidget widget,
                                             GhostCircuitItemStackHandler circuitItemStackHandler) {
        String configString;
        if (circuitItemStackHandler.getCircuitValue() == GhostCircuitItemStackHandler.NO_CONFIG) {
            configString = new TextComponentTranslation("gregtech.gui.configurator_slot.no_value").getFormattedText();
        } else {
            configString = String.valueOf(circuitItemStackHandler.getCircuitValue());
        }

        widget.setTooltipText("gregtech.gui.configurator_slot.tooltip", configString);
    }

    public static boolean isInventoryEmpty(IItemHandler inventory) {
        for (int slot = 0; slot < inventory.getSlots(); slot++) {
            if (!inventory.getStackInSlot(slot).isEmpty()) return false;
        }

        return true;
    }

    public static void addNotifiableToMTE(ItemHandlerList itemHandlerList, MultiblockControllerBase controllerBase,
                                          MetaTileEntity sourceMTE, boolean isExport) {
        for (IItemHandler handler : itemHandlerList.getBackingHandlers()) {
            if (handler instanceof INotifiableHandler notifiableHandler) {
                notifiableHandler.addNotifiableMetaTileEntity(controllerBase);
                notifiableHandler.addToNotifiedList(sourceMTE, handler, isExport);
            }
        }
    }

    public static void removeNotifiableFromMTE(ItemHandlerList itemHandlerList,
                                               MultiblockControllerBase controllerBase) {
        for (IItemHandler handler : itemHandlerList.getBackingHandlers()) {
            if (handler instanceof INotifiableHandler notifiableHandler) {
                notifiableHandler.removeNotifiableMetaTileEntity(controllerBase);
            }
        }
    }

    public static void addNotifiableToMTE(INotifiableHandler notifiableHandler, MultiblockControllerBase controllerBase,
                                          MetaTileEntity sourceMTE, boolean isExport) {
        notifiableHandler.addNotifiableMetaTileEntity(controllerBase);
        notifiableHandler.addToNotifiedList(sourceMTE, notifiableHandler, isExport);
    }

    public static void removeNotifiableFromMTE(INotifiableHandler notifiableHandler,
                                               MultiblockControllerBase controllerBase) {
        notifiableHandler.removeNotifiableMetaTileEntity(controllerBase);
    }

    public static String getOreNameByStack(ItemStack stackInSlot) {
        if (stackInSlot.isEmpty()) return "null";

        int[] oreIDs = OreDictionary.getOreIDs(stackInSlot);
        if (oreIDs == null || oreIDs.length == 0) return "null";

        int oreID = oreIDs[0];
        String oreName = OreDictionary.getOreName(oreID);
        if (oreName == null) return "null";
        else return oreName;
    }

    public static ItemStack getItemStacksFromOreNames(String oreName) {
        Collection<ItemStack> ores = OreDictionary.getOres(oreName);
        Iterator<ItemStack> iterator = ores.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack setStack(ItemStack itemstack, int amount) {
        ItemStack itemstack1;
        itemstack1 = itemstack.copy();
        itemstack1.setCount(amount);
        return itemstack1;
    }

    public static int getTierByPressure(double pressure) {
        if (pressure == GCYSValues.EARTH_PRESSURE) return GCYSValues.EAP;
        for (int i = 0; i < GCYSValues.P.length; i++) {
            double p = GCYSValues.P[i];
            if (pressure <= GCYSValues.EARTH_PRESSURE && p <= GCYSValues.EARTH_PRESSURE) {
                if (p < pressure) continue;
                else return i;
            }
            if (pressure >= GCYSValues.EARTH_PRESSURE && p > GCYSValues.EARTH_PRESSURE) {
                if (p >= pressure) return i;
            }
        }
        return 0;
    }

    public static void writeCustomData(MetaTileEntity mte, World world, int dataID, Consumer<PacketBuffer> bufWriter) {
        if (world != null && !world.isRemote) {
            mte.writeCustomData(dataID, bufWriter);
        }
    }

    public static double logBase(int value, int base) {
        return Math.log(value) / Math.log(base);
    }

    /**
     * Get Electric Motor Meta Item by Voltage Tier.
     *
     * @param tier Voltage Tier.
     * @return Correspondence Electric Motor Meta Item.
     */
    public static MetaItem<?>.MetaValueItem getMotorByTier(int tier) {
        return switch (tier) {
            case LV -> ELECTRIC_MOTOR_LV;
            case MV -> ELECTRIC_MOTOR_MV;
            case HV -> ELECTRIC_MOTOR_HV;
            case EV -> ELECTRIC_MOTOR_EV;
            case IV -> ELECTRIC_MOTOR_IV;
            case LuV -> ELECTRIC_MOTOR_LuV;
            case ZPM -> ELECTRIC_MOTOR_ZPM;
            case UV -> ELECTRIC_MOTOR_UV;
            case UHV -> ELECTRIC_MOTOR_UHV;
            case UEV -> ELECTRIC_MOTOR_UEV;
            case UIV -> ELECTRIC_MOTOR_UIV;
            case UXV -> ELECTRIC_MOTOR_UXV;
            case OpV -> ELECTRIC_MOTOR_OpV;
            case MAX -> ELECTRIC_MOTOR_MAX;
            default -> ELECTRIC_MOTOR_ULV;
        };
    }

    /**
     * Get Electric Piston Meta Item by Voltage Tier.
     *
     * @param tier Voltage Tier.
     * @return Correspondence Electric Piston Meta Item.
     */
    public static MetaItem<?>.MetaValueItem getPistonByTier(int tier) {
        return switch (tier) {
            case LV -> ELECTRIC_PISTON_LV;
            case MV -> ELECTRIC_PISTON_MV;
            case HV -> ELECTRIC_PISTON_HV;
            case EV -> ELECTRIC_PISTON_EV;
            case IV -> ELECTRIC_PISTON_IV;
            case LuV -> ELECTRIC_PISTON_LUV;
            case ZPM -> ELECTRIC_PISTON_ZPM;
            case UV -> ELECTRIC_PISTON_UV;
            case UHV -> ELECTRIC_PISTON_UHV;
            case UEV -> ELECTRIC_PISTON_UEV;
            case UIV -> ELECTRIC_PISTON_UIV;
            case UXV -> ELECTRIC_PISTON_UXV;
            case OpV -> ELECTRIC_PISTON_OpV;
            case MAX -> ELECTRIC_PISTON_MAX;
            default -> ELECTRIC_PISTON_ULV;
        };
    }

    /**
     * Get Robot Arm Meta Item by Voltage Tier.
     *
     * @param tier Voltage Tier.
     * @return Correspondence Robot Arm Meta Item.
     */
    public static MetaItem<?>.MetaValueItem getRobotArmByTier(int tier) {
        return switch (tier) {
            case LV -> ROBOT_ARM_LV;
            case MV -> ROBOT_ARM_MV;
            case HV -> ROBOT_ARM_HV;
            case EV -> ROBOT_ARM_EV;
            case IV -> ROBOT_ARM_IV;
            case LuV -> ROBOT_ARM_LuV;
            case ZPM -> ROBOT_ARM_ZPM;
            case UV -> ROBOT_ARM_UV;
            case UHV -> ROBOT_ARM_UHV;
            case UEV -> ROBOT_ARM_UEV;
            case UIV -> ROBOT_ARM_UIV;
            case UXV -> ROBOT_ARM_UXV;
            case OpV -> ROBOT_ARM_OpV;
            case MAX -> ROBOT_ARM_MAX;
            default -> ROBOT_ARM_ULV;
        };
    }

    /**
     * Get Electric Pump Meta Item by Voltage Tier.
     *
     * @param tier Voltage Tier.
     * @return Correspondence Electric Pump Meta Item.
     */
    public static MetaItem<?>.MetaValueItem getPumpByTier(int tier) {
        return switch (tier) {
            case LV -> ELECTRIC_PUMP_LV;
            case MV -> ELECTRIC_PUMP_MV;
            case HV -> ELECTRIC_PUMP_HV;
            case EV -> ELECTRIC_PUMP_EV;
            case IV -> ELECTRIC_PUMP_IV;
            case LuV -> ELECTRIC_PUMP_LuV;
            case ZPM -> ELECTRIC_PUMP_ZPM;
            case UV -> ELECTRIC_PUMP_UV;
            case UHV -> ELECTRIC_PUMP_UHV;
            case UEV -> ELECTRIC_PUMP_UEV;
            case UIV -> ELECTRIC_PUMP_UIV;
            case UXV -> ELECTRIC_PUMP_UXV;
            case OpV -> ELECTRIC_PUMP_OpV;
            case MAX -> ELECTRIC_PUMP_MAX;
            default -> ELECTRIC_PUMP_ULV;
        };
    }

    /**
     * Get Conveyor Module Meta Item by Voltage Tier.
     *
     * @param tier Voltage Tier.
     * @return Correspondence Conveyor Module Meta Item.
     */
    public static MetaItem<?>.MetaValueItem getConveyorByTier(int tier) {
        return switch (tier) {
            case LV -> CONVEYOR_MODULE_LV;
            case MV -> CONVEYOR_MODULE_MV;
            case HV -> CONVEYOR_MODULE_HV;
            case EV -> CONVEYOR_MODULE_EV;
            case IV -> CONVEYOR_MODULE_IV;
            case LuV -> CONVEYOR_MODULE_LuV;
            case ZPM -> CONVEYOR_MODULE_ZPM;
            case UV -> CONVEYOR_MODULE_UV;
            case UHV -> CONVEYOR_MODULE_UHV;
            case UEV -> CONVEYOR_MODULE_UEV;
            case UIV -> CONVEYOR_MODULE_UIV;
            case UXV -> CONVEYOR_MODULE_UXV;
            case OpV -> CONVEYOR_MODULE_OpV;
            case MAX -> CONVEYOR_MODULE_MAX;
            default -> CONVEYOR_MODULE_ULV;
        };
    }

    /**
     * Get Emitter Meta Item by Voltage Tier.
     *
     * @param tier Voltage Tier.
     * @return Correspondence Emitter Meta Item.
     */
    public static MetaItem<?>.MetaValueItem getEmitterByTier(int tier) {
        return switch (tier) {
            case LV -> EMITTER_LV;
            case MV -> EMITTER_MV;
            case HV -> EMITTER_HV;
            case EV -> EMITTER_EV;
            case IV -> EMITTER_IV;
            case LuV -> EMITTER_LuV;
            case ZPM -> EMITTER_ZPM;
            case UV -> EMITTER_UV;
            case UHV -> EMITTER_UHV;
            case UEV -> EMITTER_UEV;
            case UIV -> EMITTER_UIV;
            case UXV -> EMITTER_UXV;
            case OpV -> EMITTER_OpV;
            case MAX -> EMITTER_MAX;
            default -> EMITTER_ULV;
        };
    }

    /**
     * Get Sensor Meta Item by Voltage Tier.
     *
     * @param tier Voltage Tier.
     * @return Correspondence Sensor Meta Item.
     */
    public static MetaItem<?>.MetaValueItem getSensorByTier(int tier) {
        return switch (tier) {
            case LV -> SENSOR_LV;
            case MV -> SENSOR_MV;
            case HV -> SENSOR_HV;
            case EV -> SENSOR_EV;
            case IV -> SENSOR_IV;
            case LuV -> SENSOR_LuV;
            case ZPM -> SENSOR_ZPM;
            case UV -> SENSOR_UV;
            case UHV -> SENSOR_UHV;
            case UEV -> SENSOR_UEV;
            case UIV -> SENSOR_UIV;
            case UXV -> SENSOR_UXV;
            case OpV -> SENSOR_OpV;
            case MAX -> SENSOR_MAX;
            default -> SENSOR_ULV;
        };
    }

    /**
     * Get Field Generator Meta Item by Voltage Tier.
     *
     * @param tier Voltage Tier.
     * @return Correspondence Field Generator Meta Item.
     */
    public static MetaItem<?>.MetaValueItem getFieldGenByTier(int tier) {
        return switch (tier) {
            case LV -> FIELD_GENERATOR_LV;
            case MV -> FIELD_GENERATOR_MV;
            case HV -> FIELD_GENERATOR_HV;
            case EV -> FIELD_GENERATOR_EV;
            case IV -> FIELD_GENERATOR_IV;
            case LuV -> FIELD_GENERATOR_LuV;
            case ZPM -> FIELD_GENERATOR_ZPM;
            case UV -> FIELD_GENERATOR_UV;
            case UHV -> FIELD_GENERATOR_UHV;
            case UEV -> FIELD_GENERATOR_UEV;
            case UIV -> FIELD_GENERATOR_UIV;
            case UXV -> FIELD_GENERATOR_UXV;
            case OpV -> FIELD_GENERATOR_OpV;
            case MAX -> FIELD_GENERATOR_MAX;
            default -> FIELD_GENERATOR_ULV;
        };
    }

    /**
     * Sub-script style Formula Numbers.
     *
     * @param formula Formula (used string, such as {@code "CO2"}).
     * @return Formula with Sub-script Numbers.
     * @author Bartimaeusnek
     * @see SmallDigits
     */
    public static String subscriptNumbers(String formula) {
        char[] formulas = formula.toCharArray();
        char[] chars = new char[formulas.length];
        for (int i = 0; i < formulas.length; i++) {
            chars[i] = switch (formulas[i]) {
                case '0' -> '₀';
                case '1' -> '₁';
                case '2' -> '₂';
                case '3' -> '₃';
                case '4' -> '₄';
                case '5' -> '₅';
                case '6' -> '₆';
                case '7' -> '₇';
                case '8' -> '₈';
                case '9' -> '₉';
                default -> formulas[i];
            };
        }
        return new String(chars);
    }

    /**
     * Super-script style Formula Numbers.
     *
     * @param formula Formula (used string, such as {@code "239Pu"}).
     * @return Formula with Super-script Numbers.
     * @author Bartimaeusnek
     */
    public static String superscriptNumbers(String formula) {
        char[] formulas = formula.toCharArray();
        char[] chars = new char[formulas.length];
        for (int i = 0; i < formulas.length; i++) {
            chars[i] = switch (formulas[i]) {
                case '0' -> '⁰';
                case '1' -> '¹';
                case '2' -> '²';
                case '3' -> '³';
                case '4' -> '⁴';
                case '5' -> '⁵';
                case '6' -> '⁶';
                case '7' -> '⁷';
                case '8' -> '⁸';
                case '9' -> '⁹';
                default -> formulas[i];
            };
        }
        return new String(chars);
    }
    public static int getIncrementValue(MouseData data) {
        int adjust = 1;
        if (data.shift) adjust *= 4;
        if (data.ctrl) adjust *= 16;
        if (data.alt) adjust *= 64;
        return adjust;
    }

    public static IKey createAdjustOverlay(boolean increment) {
        final StringBuilder builder = new StringBuilder();
        builder.append(increment ? '+' : '-');
        builder.append(getIncrementValue(MouseData.create(-1)));

        float scale = 1f;
        if (builder.length() == 3) {
            scale = 0.9f;
        } else if (builder.length() == 4) {
            scale = 0.8f;
        } else if (builder.length() > 4) {
            scale = 0.7f;
        }
        return IKey.str(builder.toString())
                .color(Color.WHITE.main)
                .scale(scale);
    }

    public static int rangeMetFormMte(BlockPos mte, BlockPos range){
        return (int) Math.sqrt(mte.distanceSq(range));
    }

    public static int[] splitNumberToDigits(int number) {
        number = Math.abs(number);
        int length = number == 0 ? 1 : (int) Math.log10(number) + 1;
        int[] digits = new int[length];

        for (int i = length - 1; i >= 0; i--) {
            digits[i] = number % 10;
            number /= 10;
        }
        return digits;
    }
}