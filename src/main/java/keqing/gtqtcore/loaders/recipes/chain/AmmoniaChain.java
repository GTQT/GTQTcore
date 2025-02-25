package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;


import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.DRYER_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;

public class AmmoniaChain {

    public static void init() {
        // Minimized Haber-Bosch Process
        DRYER_RECIPES.recipeBuilder()
                .input(dust,SodiumBicarbonate,12)
                .circuitMeta(1)
                .output(dust,SodaAsh,6)
                .fluidOutputs(Water.getFluid(3000))
                .fluidOutputs(CarbonDioxide.getFluid(3000))
                .duration(20).EUt(VA[LV]).buildAndRegister();
        // CH4 + N -> CH4N
        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(Methane.getFluid(1000))
                .fluidInputs(Air.getFluid(1500))
                .fluidOutputs(RichNitrogenMixture.getFluid(2500))
                .duration(80).EUt(VA[MV]).buildAndRegister();

        // CH4N + 2H2O -> NH4 + CH4 + O2 (lossy)
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(dust, Ruthenium)
                .fluidInputs(RichNitrogenMixture.getFluid(2500))
                .fluidInputs(Water.getFluid(2000))
                .fluidOutputs(RichAmmoniaMixture.getFluid(3000))
                .fluidOutputs(Methane.getFluid(1000))
                .duration(80).EUt(VA[MV]).buildAndRegister();

        // NH4 -> NH3 + H (lossy)
        BREWING_RECIPES.recipeBuilder()
                .notConsumable(dust, Magnetite)
                .fluidInputs(RichAmmoniaMixture.getFluid(1000))
                .fluidOutputs(Ammonia.getFluid(1000))
                .duration(160).EUt(VA[LV]).buildAndRegister();

    }
}
