package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.recipes.RecipeMaps.DISTILLATION_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtechfoodoption.GTFOMaterialHandler.Stearin;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class OceanChain {
    public static void init() {
        I();          //碘
    }

    private static void I()
    {
        //2kI+Br2==2kBr+I2
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .fluidInputs(Bromine.getFluid(1000))
                .fluidInputs(PotassiumIodide.getFluid(1000))
                .fluidOutputs(PotassiumBromide.getFluid(1000))
                .fluidOutputs(Iodine.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .fluidInputs(Haidaihui.getFluid(1000))
                .fluidInputs(CarbonTetrachloride.getFluid(1000))
                .fluidOutputs(Cuiquhaidaihui.getFluid(2000))
                .buildAndRegister();

        //
        DISTILLATION_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .fluidInputs(Cuiquhaidaihui.getFluid(2000))
                .fluidOutputs(CarbonTetrachloride.getFluid(1000))
                .fluidOutputs(PotassiumIodide.getFluid(1000))
                .buildAndRegister();
    }
}
