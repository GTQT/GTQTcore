package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.OIL_POOL;
import static keqing.gtqtcore.api.unification.GTQTMaterials.PreTreatedCrudeOilContainingImpurities;

public class OilChains {
    public static void init() {
        Pre();          //预处理
        changjianya();  //常减压
    }

    private static void Pre()
    {
        OIL_POOL.recipeBuilder()
                .fluidInputs(RawOil.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOilContainingImpurities.getFluid(1000))
                .fluidOutputs(SaltWater.getFluid(2000))
                .duration(1000)
                .buildAndRegister();

        OIL_POOL.recipeBuilder()
                .fluidInputs(OilHeavy.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOilContainingImpurities.getFluid(1000))
                .fluidOutputs(SaltWater.getFluid(2000))
                .duration(1000)
                .buildAndRegister();

        OIL_POOL.recipeBuilder()
                .fluidInputs(OilLight.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOilContainingImpurities.getFluid(1000))
                .fluidOutputs(SaltWater.getFluid(2000))
                .duration(1000)
                .buildAndRegister();
    }
    private static void changjianya()
    {

    }
}
