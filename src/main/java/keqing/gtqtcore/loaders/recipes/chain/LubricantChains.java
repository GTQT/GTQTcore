package keqing.gtqtcore.loaders.recipes.chain;

import keqing.gtqtcore.api.unification.GTQTMaterials;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

public class LubricantChains {

    public static void init() {
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Chlorine.getFluid(3000))
                .input(dust, Phosphorus)
                .fluidOutputs(GTQTMaterials.PhosphorusTrichloride.getFluid(1000))
                .EUt(120)
                .duration(600)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.PhosphorusTrichloride.getFluid(1000), Chlorobenzene.getFluid(3000))
                .input(dust, Sodium, 6)
                .output(dust, Salt, 6)
                .fluidOutputs(GTQTMaterials.TriphenylPhosphine.getFluid(1000))
                .EUt(120)
                .duration(600)
                .buildAndRegister();


    }
}
