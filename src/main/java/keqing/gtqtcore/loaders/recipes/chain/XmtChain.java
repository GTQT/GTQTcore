package keqing.gtqtcore.loaders.recipes.chain;

import keqing.gtqtcore.api.unification.GTQTMaterials;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.CHEMICAL_PLANT;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class XmtChain {
    public static void init()
    {
        // 合成路径部分
        // ========== XMT前驱体合成 ==========
        // 阶段1 - 硅烷化反应
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.EpoxyCrosslinker.getFluid(1000))
                .fluidInputs(Silane.getFluid(2000))
                .fluidOutputs(GTQTMaterials.SiloxanePrepolymer.getFluid(2000))
                .duration(400)
                .EUt(VA[IV])
                .buildAndRegister();

        // 阶段2 - 氟化改性
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.SiloxanePrepolymer.getFluid(2000))
                .fluidInputs(Fluorine.getFluid(4000))
                .fluidOutputs(GTQTMaterials.FluorinatedSiloxane.getFluid(1500))
                .duration(400)
                .EUt(VA[IV])
                .buildAndRegister();

        // 阶段3 - 光敏接枝
        BLAST_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.FluorinatedSiloxane.getFluid(1000))
                .input(dust, GTQTMaterials.PhotoAcidGenerator, 2)
                .input(foil, Polybenzimidazole, 4)
                .fluidInputs(Nitrogen.getFluid(4000))
                .fluidOutputs(GTQTMaterials.XmtPrecursor.getFluid(2000))
                .blastFurnaceTemp(4500)
                .duration(900)
                .EUt(VA[IV])
                .buildAndRegister();

        // ========== xMT光刻胶合成 ==========
        // xMT光刻胶
        CHEMICAL_PLANT.recipeBuilder()
                .fluidInputs(GTQTMaterials.XmtPrecursor.getFluid(2000))
                .fluidInputs(GTQTMaterials.NucleophilicQuencher.getFluid(2000))
                .fluidInputs(Chlorobenzene.getFluid(2000))    // 新增溶剂
                .input(dust,GTQTMaterials.BaseQuencher,12)
                .fluidOutputs(GTQTMaterials.Xmt.getFluid(6000))
                .recipeLevel(7)
                .duration(1200)
                .EUt(VA[LuV])
                .buildAndRegister();

        // ========== 其他 ==========
        // 环氧交联剂合成
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Epichlorohydrin.getFluid(1000))
                .fluidInputs(BisphenolA.getFluid(1000))
                .circuitMeta(2)
                .fluidOutputs(GTQTMaterials.EpoxyCrosslinker.getFluid(2000))
                .duration(200)
                .EUt(VA[EV])
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(GrignardReagent.getFluid(3000)) // 格氏试剂
                .fluidInputs(Formaldehyde.getFluid(1000))   // 甲醛
                .fluidOutputs(GTQTMaterials.TriphenylMethanol.getFluid(4000))
                .duration(600)
                .EUt(VA[IV])
                .buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(TriphenylMethanol.getFluid(1000))
                .fluidInputs(ThionylChloride.getFluid(1000))
                .fluidOutputs(TritylChloride.getFluid(1000))
                .fluidOutputs(SulfurDioxide.getFluid(500))
                .fluidOutputs(HydrochloricAcid.getFluid(500))
                .duration(280)
                .EUt(VA[IV])
                .buildAndRegister();

        // 光致酸产生剂合成
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Antimony, 1)
                .fluidInputs(TritylChloride.getFluid(1000))
                .fluidInputs(HydrofluoricAcid.getFluid(2000))
                .output(dust,GTQTMaterials.PhotoAcidGenerator,4)
                .duration(800)
                .EUt(VA[IV])
                .buildAndRegister();

        // 亲核猝灭剂合成扩展
        // 阶段一：制备季铵化中间体
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Ethylenediamine.getFluid(1000))
                .fluidInputs(Chloromethane.getFluid(2000)) // 引入卤代烃
                .input(dust, SodiumHydroxide)
                .fluidOutputs(QuaternaryAmmoniumIntermediate.getFluid(2000))
                .fluidOutputs(SaltWater.getFluid(1000)) // 副产物盐水
                .duration(200)
                .EUt(VA[EV])
                .buildAndRegister();

        // 阶段二：酯化预处理
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(AceticAcid.getFluid(1000))
                .fluidInputs(Ethanol.getFluid(1000)) // 添加醇类促进酯化
                .notConsumable(SulfuricAcid.getFluid(100)) // 酸催化剂
                .fluidOutputs(AcetylEsterMix.getFluid(2000))
                .duration(200)
                .EUt(VA[EV])
                .buildAndRegister();

        // 阶段三：高压缩合反应
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(QuaternaryAmmoniumIntermediate.getFluid(2000))
                .fluidInputs(AcetylEsterMix.getFluid(2000))
                .notConsumable(foil, Platinum, 1) // 贵金属催化剂
                .fluidOutputs(NucleophilicQuencherCrude.getFluid(3000))
                .duration(200)
                .EUt(VA[EV])
                .buildAndRegister();

        // 阶段四：蒸馏纯化
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(NucleophilicQuencherCrude.getFluid(3000))
                .fluidOutputs(GTQTMaterials.NucleophilicQuencher.getFluid(2000))
                .fluidOutputs(HeavyOrganicWaste.getFluid(800))
                .fluidOutputs(Acetone.getFluid(200)) // 回收副产物
                .duration(200)
                .EUt(VA[EV])
                .buildAndRegister();

        // 有机废液离心处理
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(HeavyOrganicWaste.getFluid(1000))
                .fluidOutputs(AceticAcid.getFluid(400))
                .fluidOutputs(Ethanol.getFluid(300))
                .fluidOutputs(SaltWater.getFluid(200))
                .duration(200)
                .EUt(VA[HV])
                .buildAndRegister();

        // 碱猝灭剂合成
        // 阶段一：对甲基苯甲酸氯化
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(ParaToluicAcid.getFluid(1000))
                .fluidInputs(ThionylChloride.getFluid(2000))
                .notConsumable(foil, Samarium, 1)
                .fluidOutputs(AcidChlorideMix.getFluid(2000)) // 酰氯中间体
                .fluidOutputs(HydrochloricAcid.getFluid(1000))
                .duration(200)
                .EUt(VA[IV])
                .buildAndRegister();

        // 阶段二：酸碱中和结晶
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SodaAsh, 3)
                .fluidInputs(AcidChlorideMix.getFluid(2000))
                .fluidInputs(Ammonia.getFluid(1000))
                .output(dust, QuencherCrystals, 6)
                .fluidOutputs(OrganicSolventWaste.getFluid(2000))
                .duration(200)
                .EUt(VA[IV])
                .buildAndRegister();

        // 阶段三：流化床活化
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, QuencherCrystals, 6)
                .fluidInputs(Silane.getFluid(500))
                .output(dust, GTQTMaterials.BaseQuencher,6)
                .fluidOutputs(SiliconDioxideSlurry.getFluid(1000))
                .duration(200)
                .EUt(VA[IV])
                .buildAndRegister();

        // 硅烷再生
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(SiliconDioxideSlurry.getFluid(4000))
                .fluidInputs(Methane.getFluid(2000))
                .fluidOutputs(Silane.getFluid(1000))
                .fluidOutputs(CarbonDioxide.getFluid(2000))
                .duration(200)
                .EUt(VA[EV])
                .buildAndRegister();

        // 氯化剂回收
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(OrganicSolventWaste.getFluid(4000))
                .fluidOutputs(ThionylChloride.getFluid(1800))
                .output(dust,AmmoniumChloride)
                .duration(200)
                .EUt(VA[EV])
                .buildAndRegister();
    }
}
