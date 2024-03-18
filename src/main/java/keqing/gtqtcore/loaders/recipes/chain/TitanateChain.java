package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.GTValues.HV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.BLAST_RECIPES;
import static gregtech.api.recipes.RecipeMaps.COMBUSTION_GENERATOR_FUELS;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.Adamantium;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Htitanate;

public class TitanateChain {
    public static void init() {
        GTRecipeHandler.removeRecipesByInputs(BLAST_RECIPES, new ItemStack[]{OreDictUnifier.get(dust, Magnesium,2)}, new FluidStack[]{TitaniumTetrachloride.getFluid(1000)});
        //四氯化钛-》海绵钛
        BLAST_RECIPES.recipeBuilder().duration(200).EUt(VA[HV])
                .input(dust, Magnesium, 2)
                .fluidInputs(TitaniumTetrachloride.getFluid(1000))
                .output(ingot, Htitanate)
                .output(dust, MagnesiumChloride, 6)
                .blastFurnaceTemp(Titanium.getBlastTemperature() + 200)
                .buildAndRegister();

        //海绵钛-》热钛
        BLAST_RECIPES.recipeBuilder().duration(2000).EUt(VA[HV])
                .input(ingot, Htitanate,2)
                .output(ingotHot, Titanium)
                .circuitMeta(1)
                .blastFurnaceTemp(Titanium.getBlastTemperature() + 200)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder().duration(1200).EUt(VA[HV])
                .input(ingot, Htitanate,2)
                .output(ingotHot, Titanium)
                .fluidInputs(Helium.getFluid(1000))
                .circuitMeta(2)
                .blastFurnaceTemp(Titanium.getBlastTemperature() + 200)
                .buildAndRegister();

    }

}
