package keqing.gtqtcore.api.recipes;

import gregtech.api.unification.stack.UnificationEntry;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ModHandler {
    public static void addShapedRecipe(@Nonnull String regName, @Nonnull ItemStack result, @Nonnull Object... recipe) {
        addShapedRecipe(false, regName, result, false, false, recipe);
    }

    /**
     * Adds a shaped recipe with a single fluid container, which gets consumed as input and is output with different contents
     *
     * @see ModHandler#addShapedRecipe(String, ItemStack, Object...)
     */
    public static void addFluidReplaceRecipe(String regName, ItemStack result, Object... recipe) {
        addFluidReplaceRecipe(regName, result, false, recipe);
    }

    /**
     * Adds a shaped recipe which clears the nbt of the outputs
     *
     * @see ModHandler#addShapedRecipe(String, ItemStack, Object...)
     */
    public static void addShapedNBTClearingRecipe(String regName, ItemStack result, Object... recipe) {
        addShapedRecipe(false, regName, result, true, false, recipe);
    }

    /**
     * Adds a shaped recipe which sets the {@link UnificationEntry} for the output according to the crafting inputs
     *
     * @param withUnificationData whether to use unification data
     * @see ModHandler#addShapedRecipe(String, ItemStack, Object...)
     */
    public static void addShapedRecipe(boolean withUnificationData, String regName, ItemStack result, Object... recipe) {
        addShapedRecipe(withUnificationData, regName, result, false, false, recipe);
    }

    /**
     * Adds a mirrored shaped recipe
     *
     * @see ModHandler#addShapedRecipe(String, ItemStack, Object...)
     */
    public static void addMirroredShapedRecipe(String regName, ItemStack result, Object... recipe) {
        addShapedRecipe(false, regName, result, false, true, recipe);
    }

    /**
     * @param withUnificationData whether to use unification data
     * @param isNBTClearing       whether to clear output nbt
     * @param isMirrored          whether the recipe should be mirrored
     * @see ModHandler#addShapedRecipe(String, ItemStack, Object...)
     */

}
