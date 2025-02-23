package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class OceanChain {
    public static void init() {
        I();          //碘
        plastic();    //阻燃剂
    }

    private static void plastic() {
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(200)
                .EUt(120)
                .notConsumable(dust,AluminiumTrichloride,16)
                .fluidInputs(Naphtha.getFluid(4000))
                .fluidOutputs(Toluene.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(2)
                .duration(200)
                .EUt(120)
                .notConsumable(dust,Gold,16)
                .fluidInputs(Toluene.getFluid(4000))
                .fluidOutputs(ParaXylene.getFluid(1000))
                .fluidOutputs(Dimethylbenzene.getFluid(1000))
                .buildAndRegister();

        //在AlCl3催化下，对二甲苯与Br2反应，得到2，3，5，6-四溴对二甲苯。
        CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(2)
                .duration(200)
                .EUt(120)
                .notConsumable(dust,AluminiumTrichloride,16)
                .fluidInputs(ParaXylene.getFluid(1000))
                .fluidInputs(Bromine.getFluid(4000))
                .fluidOutputs(Tetrabromo.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(4000))
                .buildAndRegister();

        //将80kg四氯化碳加入反应釜中，在搅拌下加入上述制备的四溴对二甲苯42kg
        CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(2)
                .duration(400)
                .EUt(120)
                .fluidInputs(Tetrabromo.getFluid(1000))
                .notConsumable(CarbonTetrachloride.getFluid(4000))
                .fluidInputs(Bromine.getFluid(2000))
                .fluidOutputs(Tetrabromobenzene.getFluid(3000))
                .fluidOutputs(Hydrogen.getFluid(2000))
                .buildAndRegister();

        //溴化聚苯乙烯
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(400)
                .EUt(120)
                .fluidInputs(Tetrabromobenzene.getFluid(4000))
                .fluidInputs(Polystyrene.getFluid(1000))
                .fluidOutputs(Bps.getFluid(5000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(400)
                .EUt(120)
                .fluidInputs(Tetrabromobenzene.getFluid(4000))
                .fluidInputs(Epoxy.getFluid(1000))
                .fluidOutputs(Brominatedepoxyresins.getFluid(5000))
                .buildAndRegister();
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

        
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(COMMON_ALGAE)
                .output(dust, KelpAsh,1)
                .duration(100)
                .EUt(30)
                .blastFurnaceTemp(1000)
                .buildAndRegister();

        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(GREEN_ALGAE)
                .output(dust, KelpAsh,1)
                .duration(100)
                .EUt(30)
                .blastFurnaceTemp(1000)
                .buildAndRegister();

        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(RED_ALGAE)
                .output(dust, KelpAsh,1)
                .duration(100)
                .EUt(30)
                .blastFurnaceTemp(1000)
                .buildAndRegister();

        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(BROWN_ALGAE)
                .output(dust, KelpAsh,1)
                .duration(100)
                .EUt(30)
                .blastFurnaceTemp(1000)
                .buildAndRegister();

        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(GOLD_ALGAE)
                .output(dust, KelpAsh,1)
                .duration(100)
                .EUt(30)
                .blastFurnaceTemp(1000)
                .buildAndRegister();
                
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .input(dust, KelpAsh)
                .fluidInputs(CarbonTetrachloride.getFluid(1000))
                .fluidOutputs(ExtractKelpAsh.getFluid(2000))
                .buildAndRegister();

        //
        DISTILLATION_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .fluidInputs(ExtractKelpAsh.getFluid(2000))
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
                .input(dust,SodaAsh,10)
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
