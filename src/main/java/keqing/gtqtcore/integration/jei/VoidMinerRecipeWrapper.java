package keqing.gtqtcore.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class VoidMinerRecipeWrapper implements IRecipeWrapper {

    private final List<List<ItemStack>> groupedInputItemStacks = new ArrayList<>();
    private final List<List<ItemStack>> groupedOutputItemStacks = new ArrayList<>();
    private final int tier;

    public VoidMinerRecipeWrapper(List<ItemStack> ores, int tier) {
        for (var item : ores)
        {
            List<ItemStack> list = new ArrayList<>();
            list.add(item);
            this.groupedInputItemStacks.add(list);
            this.groupedOutputItemStacks.add(list);

        }
        this.tier = tier;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, this.groupedInputItemStacks);
        ingredients.setOutputLists(VanillaTypes.ITEM, this.groupedOutputItemStacks);
    }

    public int getOutputCount() {
        return this.groupedOutputItemStacks.size();
    }

    public int getTier() {
        return this.tier;
    }
}
