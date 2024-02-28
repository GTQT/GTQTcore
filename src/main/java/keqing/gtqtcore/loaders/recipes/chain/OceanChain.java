package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.unification.ore.OrePrefix;

import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.recipes.RecipeMaps.DISTILLATION_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtechfoodoption.GTFOMaterialHandler.Stearin;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.SFM;
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

        //溴线
        //三种卤素硫酸酸化+氯气=硫酸酸化卤水
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .fluidInputs(Bitterncl.getFluid(1000))
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .fluidOutputs(Bitterns.getFluid(2000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .fluidInputs(Bitternso.getFluid(1000))
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .fluidOutputs(Bitterns.getFluid(2000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .fluidInputs(Bitternco.getFluid(1000))
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .fluidOutputs(Bitterns.getFluid(2000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .fluidInputs(Bittern.getFluid(3000))
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .fluidOutputs(Bitterns.getFluid(2000))
                .buildAndRegister();
        //硫酸酸化卤水+氧气=氧化卤水
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .fluidInputs(Bitterns.getFluid(2000))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(Bitterno.getFluid(2000))
                .buildAndRegister();
        //蒸汽置换=离散态氧化溴
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .fluidInputs(Bitterno.getFluid(2000))
                .fluidInputs(Steam.getFluid(9600))
                .fluidOutputs(Water.getFluid(60))
                .fluidOutputs(Bitternbr.getFluid(2000))
                .buildAndRegister();
        //纯碱吸收=氧化富集溴
        SFM.recipeBuilder()
                .duration(100)
                .EUt(120)
                .fluidInputs(Bitternbr.getFluid(6000))
                .input(OrePrefix.dust,SodaAsh,10)
                .fluidOutputs(Bitternobr.getFluid(200))
                .fluidOutputs(Bitternobr.getFluid(200))
                .fluidOutputs(Bitternobr.getFluid(200))
                .fluidOutputs(Bitternco.getFluid(200))
                .fluidOutputs(Bitternco.getFluid(200))
                .fluidOutputs(Bitternco.getFluid(200))
                .fluidOutputs(Bitternso.getFluid(200))
                .fluidOutputs(Bitternso.getFluid(200))
                .fluidOutputs(Bitternso.getFluid(200))
                .fluidOutputs(Bitterncl.getFluid(200))
                .fluidOutputs(Bitterncl.getFluid(200))
                .fluidOutputs(Bitterncl.getFluid(200))
                .buildAndRegister();
        //二氧化硫还原=溴+硫酸
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .fluidInputs(Bitternobr.getFluid(200))
                .fluidInputs(SulfurDioxide.getFluid(6000))
                .fluidOutputs(Bromine.getFluid(200))
                .fluidOutputs(SulfuricAcid.getFluid(1600))
                .buildAndRegister();
    }
}
