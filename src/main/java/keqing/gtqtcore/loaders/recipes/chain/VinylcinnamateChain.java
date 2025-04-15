package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.unification.ore.OrePrefix;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtechfoodoption.GTFOMaterialHandler.Acetaldehyde;
import static gregtechfoodoption.GTFOMaterialHandler.AceticAnhydride;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.CHEMICAL_PLANT;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class VinylcinnamateChain {
    public static void init() {
        //二级光胶

        //Vinylcinnamate=吡啶+聚乙烯醇+肉桂酰氯
        CHEMICAL_PLANT.recipeBuilder()
                .fluidInputs(Pyridine.getFluid(1000))
                .fluidInputs(PolyvinylAlcoholVinylalcoholPolymer.getFluid(576))
                .fluidInputs(TransPhenylacryloylChloride.getFluid(1000))
                .fluidOutputs(Vinylcinnamate.getFluid(5000))
                .recipeLevel(5)
                .duration(2000).EUt(VA[EV]).buildAndRegister();

        //吡啶+6水+4二氧化碳=5乙醛+5甲醛+4氨
        CHEMICAL_PLANT.recipeBuilder()
                .fluidInputs(Acetaldehyde.getFluid(5000))
                .fluidInputs(Formaldehyde.getFluid(5000))
                .fluidInputs(Ammonia.getFluid(4000))
                .fluidOutputs(Water.getFluid(6000))
                .fluidOutputs(CarbonDioxide.getFluid(4000))
                .fluidOutputs(Pyridine.getFluid(1000))
                .recipeLevel(3)
                .duration(2000).EUt(VA[EV]).buildAndRegister();

        //聚乙烯醇
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(PolyvinylAcetate.getFluid(144))
                .notConsumable(SodiumHydroxide.getFluid(1000))
                .notConsumable(Methanol.getFluid(1000))
                .fluidOutputs(PolyvinylAlcoholVinylalcoholPolymer.getFluid(144))
                .duration(80).EUt(VA[EV]).buildAndRegister();

        //肉桂酰氯+盐+二氧化碳=肉桂酸钠+乙二酰氯
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(PhosphorusOxychloride.getFluid(1000))
                .fluidInputs(SodiumCinnamate.getFluid(1000))
                .output(OrePrefix.dust,Salt,1)
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .fluidOutputs(TransPhenylacryloylChloride.getFluid(1000))
                .duration(200).EUt(VA[EV]).buildAndRegister();

        //乙二酰氯  草酸+2五氯化磷=乙二酰氯+2氯化氢+2三氯氧磷
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(EthanedioicAcid.getFluid(1000))
                .input(OrePrefix.dust,PhosphorusPentachloride,2)
                .fluidOutputs(OxalylChloride.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .fluidOutputs(PhosphorusOxychloride.getFluid(2000))
                .duration(200).EUt(VA[HV]).buildAndRegister();
        //五氯化磷
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(PhosphorusTrichloride.getFluid(1000))
                .fluidInputs(Chlorine.getFluid(2000))
                .output(OrePrefix.dust,PhosphorusPentachloride,1)
                .duration(200).EUt(VA[HV]).buildAndRegister();
        //吡啶法  。先将聚乙烯醇精制处理，，再将精制的聚乙烯醇加入95～100℃的吡啶溶剂中，在50℃时滴加肉桂酰氯与其反应，用丙酮稀释，在水中沉淀、洗净、干燥后得到成品。
        //由肉桂酸钠与乙二酰氯反应而得。将乙二酰氯和苯配成溶液，在不时搅拌下将干燥过的肉桂酸钠分多次加入，每次加入待反应放出二氧化碳后再加下次。
        // 。稍冷后，将产生的氯化钠和微量未反应的肉桂酸钠滤除。滤液先在常压下蒸除苯，再减压蒸馏收集131℃（1.47kPa）馏分，即得产品，
        // 产率75-90%。需提纯时，可用四氯化碳重结晶。

        //肉桂酸钠=肉桂酸与氢氧化钠

        //苯乙醛+乙酸酐=肉桂酸乙酯+乙酸
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Benzaldehyde.getFluid(1000))
                .fluidInputs(AceticAnhydride.getFluid(1000))
                .fluidOutputs(AceticAcid.getFluid(1000))
                .fluidOutputs(EthylCinnamate.getFluid(1000))
                .duration(400).EUt(VA[EV]).buildAndRegister();
        //肉桂酸乙酯+水=肉桂酸+乙醇
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(EthylCinnamate.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(Ethanol.getFluid(1000))
                .fluidOutputs(CinnamicAcid.getFluid(1000))
                .duration(400).EUt(VA[EV]).buildAndRegister();
        //肉桂酸+氢氧化钠=肉桂酸钠
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(CinnamicAcid.getFluid(1000))
                .fluidInputs(SodiumHydroxide.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .fluidOutputs(SodiumCinnamate.getFluid(1000))
                .duration(400).EUt(VA[EV]).buildAndRegister();


    }
}
