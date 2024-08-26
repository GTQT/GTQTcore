package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.recipes.RecipeMaps.PYROLYSE_RECIPES;
import static gregtech.api.unification.material.Materials.Steel;
import static gregtech.api.unification.material.Materials.Tetrafluoroethylene;
import static gregtech.api.unification.ore.OrePrefix.stick;
import static keqing.gtqtcore.api.unification.GTQTMaterials.FluorinatedEthylenePropylene;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Hexafluoropropylene;

public class FEPChain {

    public static void init() {

        //  3C2F4 -> 2C3F6
        PYROLYSE_RECIPES.recipeBuilder()
                .notConsumable(stick, Steel)
                .fluidInputs(Tetrafluoroethylene.getFluid(3000))
                .fluidOutputs(Hexafluoropropylene.getFluid(2000))
                .EUt(VA[HV])
                .duration(20 * 20)
                .buildAndRegister();

        //  C2F4 + C3F6 -> C5F10
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Tetrafluoroethylene.getFluid(1000))
                .fluidInputs(Hexafluoropropylene.getFluid(1000))
                .fluidOutputs(FluorinatedEthylenePropylene.getFluid(1000))
                .EUt(VA[EV])
                .duration(5 * 20)
                .buildAndRegister();
    }
}