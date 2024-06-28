package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

public class BoulesRecipes {

    public static void init() {
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.BLAST_RECIPES, OreDictUnifier.get(OrePrefix.dust, Silicon, 32), OreDictUnifier.get(dustSmall, GalliumArsenide, 4),
                IntCircuitIngredient.getIntegratedCircuit(2));

        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.BLAST_RECIPES,
                new ItemStack[]{OreDictUnifier.get(OrePrefix.dust, Silicon, 64),
                        OreDictUnifier.get(dustSmall, GalliumArsenide, 2),
                        OreDictUnifier.get(dust, Phosphorus, 8), IntCircuitIngredient.getIntegratedCircuit(2)},
                new FluidStack[]{Nitrogen.getFluid(8000)}
                );

        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.BLAST_RECIPES,
                new ItemStack[]{OreDictUnifier.get(block, Silicon, 16),
                        OreDictUnifier.get(dust, GalliumArsenide, 1),
                        OreDictUnifier.get(ingot, Naquadah, 1)},
                new FluidStack[]{Argon.getFluid(8000)}
        );

    }
}
