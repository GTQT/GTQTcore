package keqing.gtqtcore.api.utils;

import gregtech.api.GTValues;
import gregtech.api.util.TextFormattingUtil;
import keqing.gtqtcore.GTQTCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Supplier;

import static keqing.gtqtcore.api.utils.GTQTUniverUtil.*;
import static net.minecraft.util.EnumFacing.*;

public class GTQTUtil {

    @Nonnull
    public static ResourceLocation gtqtId(@Nonnull String path) {
        return new ResourceLocation("gtqtcore", path);
    }

    public static int intValueOfBitSet(BitSet set){
        int result = 0;
        for(int i = 0; i<set.length();i++){
            result = result | (set.get(i)?1:0) << i;
        }
        return result;
    }
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
    public static BitSet forIntToBitSet(int i,int length){
        return forIntToBitSet(i,length,new BitSet(length));
    }

    public static BitSet forIntToBitSet(int i,int length,BitSet result){
        for(int j = 0;j<length;j++){
            if(((i & ( 0b1 << j)) / ( 0b1 << j)) == 1){
                result.set(j);
            }
            else {
                result.clear(j);
            }
        }
        return result;
    }

    public static <T> T getOrDefault(BooleanSupplier canGet, Supplier<T> getter, T defaultValue){
        return canGet.getAsBoolean() ? getter.get() : defaultValue;
    }


    public static EnumFacing getFacingFromNeighbor(BlockPos pos,BlockPos neighbor){
        BlockPos rel = neighbor.subtract(pos);
        if(rel.getX() == 1){
            return EAST;
        }
        if(rel.getX() == -1){
            return WEST;
        }
        if(rel.getY() == 1){
            return UP;
        }
        if(rel.getY() == -1){
            return DOWN;
        }
        if(rel.getZ() == 1){
            return SOUTH;
        }
        return NORTH;
    }



    public static int getOrDefault(NBTTagCompound tag, String key, int defaultValue){
        if(tag.hasKey(key)){
            return tag.getInteger(key);
        }
        return defaultValue;
    }
    /**
     * Get {@code ResourceLocation} of other mod.
     *
     * @param modid  Mod ID.
     * @param path   Name in {@code modid} namespace.
     * @return       Namespace of {@code modid}.
     */
    @Nonnull
    public static ResourceLocation getId(String modid, @Nonnull String path) {
        return new ResourceLocation(modid, path);
    }

    /**
     * Get item stack by {@code modid} and {@code name} of item stack.
     *
     * @param modid  Mod ID.
     * @param name   Item name.
     * @return       Item stack in {@code modid} which named by {@code name}.
     */
    @Nonnull
    public static ItemStack getItemById(String modid, String name) {
        return GameRegistry.makeItemStack(modid + ":" + name, 0, 1, null);
    }

    /**
     * Get item stack by {@code modid} and {@code name} of item stack.
     *
     * @param modid   Mod ID.
     * @param name    Item name.
     * @param amount  Amount of Item.
     * @return        Allocate amount Item stack in {@code modid} which named by {@code name}.
     */
    @Nonnull
    public static ItemStack getItemById(String modid, String name, int amount) {
        return GameRegistry.makeItemStack(modid + ":" + name, 0, amount, null);
    }

    /**
     * Get item stack with NBT data by {@code modid} and {@code name} of item stack.
     *
     * @param modid  Mod ID.
     * @param name   Item Name.
     * @param nbt    NBT data of Item.
     * @return       Item stack with NBT data in {@code modid} which named by {@code name}.
     */
    @Nonnull
    public static ItemStack getItemById(String modid, String name, NBTTagCompound nbt) {
        return GameRegistry.makeItemStack(modid + ":" + name, 0, 1, nbt != null ? nbt.toString() : null);
    }

    /**
     * Get item stack with nbt data by {@code modid} and {@code name} of item stack.
     *
     * @param modid   Mod ID.
     * @param name    Item Name.
     * @param amount  Amount of Item.
     * @param nbt     NBT data of Item.
     * @return        Allocate amount Item stack with NBT data in {@code modid} which named by {@code name}.
     */
    @Nonnull
    public static ItemStack getItemById(String modid, String name, int amount, NBTTagCompound nbt) {
        return GameRegistry.makeItemStack(modid + ":" + name, 0, amount, nbt != null ? nbt.toString() : null);
    }

    /**
     * Get item stack by {@code modid} and {@code name} of item stack which has metadata.
     *
     * @param modid  Mod ID.
     * @param name   Item name.
     * @param meta   Metadata of {@code name} item.
     * @return       Item stack in {@code modid} which named by {@code name} and has metadata {@code meta}.
     */
    @Nonnull
    public static ItemStack getMetaItemById(String modid, String name, int meta) {
        return GameRegistry.makeItemStack(modid + ":" + name, meta, 1, null);
    }

    /**
     * Get item stack by {@code modid} and {@code name} of item stack which has metadata.
     *
     * @param modid   Mod ID.
     * @param name    Item name.
     * @param meta    Metadata of {@code name} item.
     * @param amount  Amount of {@code name} item.
     * @return        Allocate amount Item stack in {@code modid} which named by {@code name} and has metadata {@code meta}.
     */
    @Nonnull
    public static ItemStack getMetaItemById(String modid, String name, int meta, int amount) {
        return GameRegistry.makeItemStack(modid + ":" + name, meta, amount, null);
    }

    /**
     * Get item stack with NBT data by {@code modid} and {@code name} of item stack which has metadata.
     *
     * @param modid  Mod ID.
     * @param name   Item name.
     * @param meta   Metadata of {@code name} item.
     * @param nbt    NBT data of item.
     * @return       Item stack with NBT data in {@code modid} which named by {@code name} and has metadata {@code meta}.
     */
    @Nonnull
    public static ItemStack getMetaItemById(String modid, String name, int meta, NBTTagCompound nbt) {
        return GameRegistry.makeItemStack(modid + ":" + name, meta, 1, nbt != null ? nbt.toString() : null);
    }

    /**
     * Get item stack with NBT data by {@code modid} and {@code name} of item stack which has metadata.
     *
     * @param modid   Mod ID.
     * @param name    Item name.
     * @param meta    Metadata of {@code name} item.
     * @param amount  Amount of {@code name} item.
     * @param nbt     NBT data of item.
     * @return        Allocate amount Item stack with NBT data in {@code modid} which named by {@code name} and has metadata {@code meta}.
     */
    @Nonnull
    public static ItemStack getMetaItemById(String modid, String name, int meta, int amount, NBTTagCompound nbt) {
        return GameRegistry.makeItemStack(modid + ":" + name, meta, amount, nbt != null ? nbt.toString() : null);
    }

    /**
     * Get fluid stack by {@code name}.
     *
     * @param name  Fluid name.
     * @return      Fluid stack named by {@code name}.
     */
    @Nonnull
    public static FluidStack getFluidById(String name) {
        return Objects.requireNonNull(FluidRegistry.getFluidStack(name, 1000));
    }

    /**
     * Get fluid stack by {@code name}.
     *
     * @param name    Fluid name.
     * @param amount  Amount of {@code name} fluid.
     * @return        Allocate amount fluid stack named by {@code name}.
     */
    @Nonnull
    public static FluidStack getFluidById(String name, int amount) {
        return Objects.requireNonNull(FluidRegistry.getFluidStack(name, amount));
    }

    /**
     * @param number  Long number.
     * @return        Just a rewrite of formatNumbers(),
     *                please see {@link TextFormattingUtil#formatNumbers(long)}.
     */
    public static String formatNumbers(long number) {
        return TextFormattingUtil.formatNumbers(number);
    }

    /**
     * @param number  Double number.
     * @return        Just a rewrite of formatNumbers(),
     *                please see {@link TextFormattingUtil#formatNumbers(double)}.
     */
    public static String formatNumbers(double number) {
        return TextFormattingUtil.formatNumbers(number);
    }


}