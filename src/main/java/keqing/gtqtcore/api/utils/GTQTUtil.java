package keqing.gtqtcore.api.utils;
import java.net.URI;
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
import gregtech.api.unification.material.Materials;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.TextFormattingUtil;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.GCYSValues;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Supplier;

import static gregtech.api.GregTechAPI.materialManager;
import static net.minecraft.util.EnumFacing.*;
import static net.minecraftforge.common.DimensionManager.getWorlds;

public class GTQTUtil {

    public static final Function<Integer, Integer> genericGeneratorTankSizeFunctionPlus = (tier) -> Math.min(16000 * (1 << tier - 1), 128000);
    public static final int[] CWT = new int[]{0, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072};
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
    public static double getAccelerateByCWU(int requestCWUt)
    {
        if(requestCWUt>=2048) return 0.6;
        else if(requestCWUt>=1972) return 0.65;
        else if(requestCWUt>=1536) return 0.7;
        else if(requestCWUt>=1280) return 0.78;
        else if(requestCWUt>=1024) return 0.8;
        else if(requestCWUt>=768) return 0.85;
        else if(requestCWUt>=512) return 0.9;
        else if(requestCWUt>=256) return 0.95;
        return 1.0;
    }
    @Nonnull
    public static ResourceLocation gtqtId(@Nonnull String path) {
        return new ResourceLocation("gtqtcore", path);
    }
    public static  Integer baseTime = 100;

    public static String getName(MetaItem.MetaValueItem is) {
        return is.getStackForm().getDisplayName();
    }
    public static String getName(Material mater)
    {
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

}