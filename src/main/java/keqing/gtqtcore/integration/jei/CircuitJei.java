package keqing.gtqtcore.integration.jei;

import gregtech.api.items.metaitem.MetaItem;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;
import java.util.stream.Collectors;


public class CircuitJei implements IRecipeWrapper {

    private final List<List<ItemStack>> groupedInputsAsItemStacks = new ArrayList<>();
    private final List<List<ItemStack>> groupedOutputsAsItemStacks = new ArrayList<>();

    public CircuitJei(MetaItem.MetaValueItem[] tierCircuit)
    {
        for(MetaItem.MetaValueItem inputList:tierCircuit) {
            this.groupedInputsAsItemStacks.add(Collections.singletonList(inputList.getStackForm()));

            this.groupedOutputsAsItemStacks.add(getItemStacksFromOreNames(Arrays.stream(OreDictionary.getOreIDs(inputList.getStackForm())).mapToObj(OreDictionary::getOreName).collect(Collectors.toList())));
        }

    }
    public static List<ItemStack> getItemStacksFromOreNames(List<String> oreNames) {
        List<ItemStack> itemStacks = new ArrayList<>();

        for (String oreName : oreNames) {
            Collection<ItemStack> ores = OreDictionary.getOres(oreName);
            if (ores != null) {
                itemStacks.addAll(ores);
            }
        }

        return itemStacks;
    }
    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, groupedInputsAsItemStacks);
        ingredients.setOutputLists(VanillaTypes.ITEM, groupedOutputsAsItemStacks);
    }
    public int getOutputCount() {
        return groupedOutputsAsItemStacks.size();
    }
    public int getInputCount() {
        return groupedInputsAsItemStacks.size();
    }
}
