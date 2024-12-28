package keqing.gtqtcore.integration.jei;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.common.metatileentities.MetaTileEntities;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlgaeJei implements IRecipeWrapper {

    private final List<List<ItemStack>> groupedInputsAsItemStacks = new ArrayList<>();
    private final List<List<ItemStack>> groupedOutputsAsItemStacks = new ArrayList<>();

    public AlgaeJei(MetaItem.MetaValueItem[] output) {

        for (int i = 0; i < 6; i++)
            this.groupedInputsAsItemStacks.add(Collections.singletonList(MetaTileEntities.HULL[i].getStackForm()));

        for (MetaItem.MetaValueItem outputList : output)
            this.groupedOutputsAsItemStacks.add(Collections.singletonList(outputList.getStackForm()));

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
