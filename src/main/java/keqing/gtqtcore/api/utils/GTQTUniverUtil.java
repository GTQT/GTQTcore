package keqing.gtqtcore.api.utils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.recipes.RecipeMaps.LARGE_CHEMICAL_RECIPES;
import static net.minecraft.util.EnumFacing.*;
import static net.minecraft.util.EnumFacing.SOUTH;

public class GTQTUniverUtil {

    public static final int TICK = 1;

    public static final int SECOND = 20;

    public static final int MINUTE = 60 * SECOND;

    public static final int HOUR = 60 * MINUTE;

    public static final int HALF_HOUR = HOUR / 2;

    public static final int QUAT_HOUR = HOUR / 4;

    public static void removeChemicalRecipe(ItemStack... itemInputs) {
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, itemInputs);
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, itemInputs);
    }
    public static void removeChemicalRecipe(FluidStack... fluidInputs) {
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, fluidInputs);
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, fluidInputs);
    }
    public static void removeChemicalRecipe(ItemStack[] itemInputs,
                                            FluidStack[] fluidInputs) {
        GTRecipeHandler.removeRecipesByInputs(CHEMICAL_RECIPES, itemInputs, fluidInputs);
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, itemInputs, fluidInputs);
    }
    public static int getOrDefault(NBTTagCompound tag, String key, int defaultValue){
        if(tag.hasKey(key)){
            return tag.getInteger(key);
        }
        return defaultValue;
    }

    private static final Table<RecipeMap<?>, Integer, ItemStack> MAP_TIER_TO_MACHINE_STACK = HashBasedTable.create();
    public static ItemStack getMachineStack(RecipeMap<?> map, int tier, int count) {
        if (tier < 0 || tier > GTValues.MAX) return ItemStack.EMPTY;
        ItemStack stack = MAP_TIER_TO_MACHINE_STACK.get(map, tier);
        if (stack == null) return ItemStack.EMPTY;
        stack = stack.copy();
        stack.setCount(count);
        return stack;
    }

    public static ItemStack getMachineStack(RecipeMap<?> map, int tier) {
        return getMachineStack(map, tier, 1);
    }
    public static <T> T getOrDefault(BooleanSupplier canGet, Supplier<T> getter, T defaultValue){
        return canGet.getAsBoolean() ? getter.get() : defaultValue;
    }

    //  List Utils
    public static <T> int maxLength(List<List<T>> lists) {
        return lists.stream().mapToInt(List::size).max().orElse(0);
    }

    public static <T> List<T> consistentList(List<T> list, int length) {
        if (list.size() >= length) {
            return list;
        }
        List<T> finalList = new ArrayList<>(list);
        T last = list.get(list.size() - 1);
        for (int i = 0; i < length - list.size(); i++) {
            finalList.add(last);
        }
        return finalList;
    }

    //  Set Utils
    public static int intValueOfBitSet(BitSet set){
        int result = 0;
        for(int i = 0; i<set.length();i++){
            result = result | (set.get(i)?1:0) << i;
        }
        return result;
    }

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

    //  Face Utils
    public static EnumFacing getFacingFromNeighbor(BlockPos pos, BlockPos neighbor){
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

    public static EnumFacing getCounterClockWise(EnumFacing self) {

        return switch (self) {
            case NORTH -> WEST;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
            case EAST -> NORTH;
            default -> throw new IllegalStateException("Unable to get CCW facing of " + self);
        };
    }

    public static EnumFacing getClockWise(EnumFacing self) {

        return switch (self) {
            case NORTH -> EAST;
            case SOUTH -> WEST;
            case WEST -> NORTH;
            case EAST -> SOUTH;
            default -> throw new IllegalStateException("Unable to get Y-rotated facing of " + self);
        };
    }

    // Item Utils
    public static int stackToInt(ItemStack stack) {
        if (isStackInvalid(stack)) return 0;
        return itemToInt(stack.getItem(), stack.getMetadata());
    }

    public static int itemToInt(Item item, int meta) {
        return Item.getIdFromItem(item) | (meta << 16);
    }

    public static boolean isStackInvalid(Object stack) {
        return !(stack instanceof ItemStack) || ((ItemStack) stack).getCount() < 0;
    }

    public static boolean isStackValid(Object aStack) {
        return (aStack instanceof ItemStack) && ((ItemStack) aStack).getCount() >= 0;
    }

    public static ItemStack intToStack(int aStack) {
        int tID = aStack & (~0 >>> 16), tMeta = aStack >>> 16;
        Item tItem = Item.getItemById(tID);
        if (tItem != null) return new ItemStack(tItem, 1, tMeta);
        return null;
    }

    public static ItemStack copyAmountUnsafe(long aAmount, Object... aStacks) {
        ItemStack rStack = copy(aStacks);
        if (isStackInvalid(rStack)) return null;
        if (aAmount > Integer.MAX_VALUE) aAmount = Integer.MAX_VALUE;
        else if (aAmount < 0) aAmount = 0;
        rStack.setCount((int) aAmount);
        return rStack;
    }

    public static ItemStack copy(Object... aStacks) {
        for (Object tStack : aStacks) if (isStackValid(tStack)) return ((ItemStack) tStack).copy();
        return null;
    }

    //  MetaTileEntity Utils
    public static <T> MetaTileEntity[] getGTTierHatches(MultiblockAbility<T> ability, int tier) {
        return MultiblockAbility.REGISTRY.get(ability).stream()
                .filter(mte -> {
                    if (mte != null && mte instanceof MetaTileEntityMultiblockPart) {
                        return ((MetaTileEntityMultiblockPart) mte).getTier() <= tier;
                    }
                    return false;
                }).toArray(MetaTileEntity[]::new);
    }

    public static MetaTileEntityHolder getTileEntity(MetaTileEntity tile) {
        MetaTileEntityHolder holder = new MetaTileEntityHolder();
        holder.setMetaTileEntity(tile);
        holder.getMetaTileEntity().onPlacement();
        holder.getMetaTileEntity().setFrontFacing(EnumFacing.SOUTH);
        return holder;
    }

    //  Tier Utils
    public static int getOpticalGlassTier(int glassTier) {
        return (int) (Math.floor((double) glassTier / 2) + glassTier % 2 - 2);
    }
}
