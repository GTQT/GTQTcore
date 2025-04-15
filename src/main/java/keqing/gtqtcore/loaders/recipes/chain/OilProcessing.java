package keqing.gtqtcore.loaders.recipes.chain;


import static gregtech.api.GTValues.MV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.plate;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.CATALYTIC_REFORMER_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.ParaXylene;

public class OilProcessing {

    public static void init() {
        catalyticReforming();
    }

    private static void catalyticReforming() {
        CATALYTIC_REFORMER_RECIPES.recipeBuilder()
                .notConsumable(plate, Platinum) // 铂催化剂
                .fluidInputs(Naphtha.getFluid(1000))
                .fluidOutputs(Toluene.getFluid(60))
                .fluidOutputs(Benzene.getFluid(200))
                .fluidOutputs(ParaXylene.getFluid(350))
                .fluidOutputs(Ethylbenzene.getFluid(200))
                .fluidOutputs(Propene.getFluid(100)) // 新增丙烯
                .fluidOutputs(Ethylene.getFluid(100)) // 新增乙烯
                .duration(120).EUt(VA[MV]).buildAndRegister();

        CATALYTIC_REFORMER_RECIPES.recipeBuilder()
                .notConsumable(plate, Palladium) // 钯催化剂
                .fluidInputs(Naphtha.getFluid(1000))
                .fluidOutputs(Toluene.getFluid(80))
                .fluidOutputs(Benzene.getFluid(250))
                .fluidOutputs(ParaXylene.getFluid(400))
                .fluidOutputs(Ethylbenzene.getFluid(250))
                .fluidOutputs(Propene.getFluid(150)) // 新增丙烯
                .fluidOutputs(Ethylene.getFluid(150)) // 新增乙烯
                .duration(120).EUt(VA[MV]).buildAndRegister();

        CATALYTIC_REFORMER_RECIPES.recipeBuilder()
                .notConsumable(plate, Platinum) // 铂催化剂
                .fluidInputs(Methanol.getFluid(1000)) // 甲醇输入
                .fluidOutputs(Propene.getFluid(500)) // 丙烯
                .fluidOutputs(Ethylene.getFluid(500)) // 乙烯
                .duration(120).EUt(VA[MV]).buildAndRegister();

        CATALYTIC_REFORMER_RECIPES.recipeBuilder()
                .notConsumable(plate, Palladium) // 钯催化剂
                .fluidInputs(Methanol.getFluid(1000)) // 甲醇输入
                .fluidOutputs(Propene.getFluid(600)) // 丙烯
                .fluidOutputs(Ethylene.getFluid(400)) // 乙烯
                .duration(120).EUt(VA[MV]).buildAndRegister();

                // 铂催化剂催化重整反应 - 使用乙烷作为原料
        CATALYTIC_REFORMER_RECIPES.recipeBuilder()
                .notConsumable(plate, Platinum) // 铂催化剂
                .fluidInputs(Ethane.getFluid(1000)) // 乙烷输入
                .fluidOutputs(Ethylene.getFluid(800)) // 乙烯
                .fluidOutputs(Propene.getFluid(200)) // 丙烯
                .duration(120).EUt(VA[MV]).buildAndRegister();

        // 钯催化剂催化重整反应 - 使用乙烷作为原料
        CATALYTIC_REFORMER_RECIPES.recipeBuilder()
                .notConsumable(plate, Palladium) // 钯催化剂
                .fluidInputs(Ethane.getFluid(1000)) // 乙烷输入
                .fluidOutputs(Ethylene.getFluid(900)) // 乙烯
                .fluidOutputs(Propene.getFluid(100)) // 丙烯
                .duration(120).EUt(VA[MV]).buildAndRegister();

        // 铂催化剂催化重整反应 - 使用丙烷作为原料
        CATALYTIC_REFORMER_RECIPES.recipeBuilder()
                .notConsumable(plate, Platinum) // 铂催化剂
                .fluidInputs(Propane.getFluid(1000)) // 丙烷输入
                .fluidOutputs(Propene.getFluid(700)) // 丙烯
                .fluidOutputs(Ethylene.getFluid(300)) // 乙烯
                .duration(120).EUt(VA[MV]).buildAndRegister();

        // 钯催化剂催化重整反应 - 使用丙烷作为原料
        CATALYTIC_REFORMER_RECIPES.recipeBuilder()
                .notConsumable(plate, Palladium) // 钯催化剂
                .fluidInputs(Propane.getFluid(1000)) // 丙烷输入
                .fluidOutputs(Propene.getFluid(800)) // 丙烯
                .fluidOutputs(Ethylene.getFluid(200)) // 乙烯
                .duration(120).EUt(VA[MV]).buildAndRegister();

        // 铂催化剂催化重整反应 - 使用丁烷作为原料
        CATALYTIC_REFORMER_RECIPES.recipeBuilder()
                .notConsumable(plate, Platinum) // 铂催化剂
                .fluidInputs(Butane.getFluid(1000)) // 丁烷输入
                .fluidOutputs(Butene.getFluid(600)) // 丁烯
                .fluidOutputs(Propene.getFluid(300)) // 丙烯
                .fluidOutputs(Ethylene.getFluid(100)) // 乙烯
                .duration(120).EUt(VA[MV]).buildAndRegister();

        // 钯催化剂催化重整反应 - 使用丁烷作为原料
        CATALYTIC_REFORMER_RECIPES.recipeBuilder()
                .notConsumable(plate, Palladium) // 钯催化剂
                .fluidInputs(Butane.getFluid(1000)) // 丁烷输入
                .fluidOutputs(Butene.getFluid(700)) // 丁烯
                .fluidOutputs(Propene.getFluid(200)) // 丙烯
                .fluidOutputs(Ethylene.getFluid(100)) // 乙烯
                .duration(120).EUt(VA[MV]).buildAndRegister();

    }
}