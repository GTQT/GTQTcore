package keqing.gtqtcore.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.HydrogenSilsesquioxane;
import static keqing.gtqtcore.api.unification.TJMaterials.SU8_Photoresist;

public class PhotolithographyFactoryJei implements IRecipeWrapper {

    private final List<List<ItemStack>> groupedInputsAsItemStacks = new ArrayList<>();
    private final List<List<ItemStack>> groupedOutputsAsItemStacks = new ArrayList<>();
    private final List<List<FluidStack>> fluidList = new ArrayList<>();

    public PhotolithographyFactoryJei(List<ItemStack> input, List<ItemStack> output)
    {
        for(ItemStack inputList:input)
            this.groupedInputsAsItemStacks.add(Collections.singletonList(inputList));

        for(ItemStack outputList:output)
            this.groupedOutputsAsItemStacks.add(Collections.singletonList(outputList));

        this.fluidList.addAll(Arrays.asList(Arrays.asList(
                HydrogenSilsesquioxane.getFluid(1000),
                Vinylcinnamate.getFluid(1000),
                SU8_Photoresist.getFluid(1000),
                Xmt.getFluid(1000),
                Zrbtmst.getFluid(1000))));
    }
    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, groupedInputsAsItemStacks);
        ingredients.setOutputLists(VanillaTypes.ITEM, groupedOutputsAsItemStacks);
        ingredients.setInputLists(VanillaTypes.FLUID, fluidList);
    }
    public int getOutputCount() {
        return groupedOutputsAsItemStacks.size();
    }
    public int getInputCount() {
        return groupedInputsAsItemStacks.size();
    }
}
