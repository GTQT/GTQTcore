package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtechfoodoption.GTFOMaterialHandler.LithiumCarbonate;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.MSF;

public class Bittern {
    public static void init() {
        Common();
        MSF();
    }

    private static void MSF() {
        // 海水多级闪蒸处理
        // 海水多级闪蒸处理（包含初级浓缩）
        SFM.recipeBuilder()
                .fluidInputs(SeaWater.getFluid(24000)) // 增加输入量以实现连续处理
                .output(dust, Salt, 27)               // 总盐量 = 18 + (15×0.375)
                .fluidOutputs(Bitterncl.getFluid(900))  // 氯化物相 (3000×0.3)
                .fluidOutputs(Bitternso.getFluid(675))  // 硫酸盐相 (3000×0.225)
                .fluidOutputs(Bitternco.getFluid(450))  // 碳酸盐相 (3000×0.15)
                .fluidOutputs(Bitternbr.getFluid(225))  // 溴相 (3000×0.075)
                .fluidOutputs(DistilledWater.getFluid(18000)) // 纯水总量
                .duration(450)                         // 总处理时间
                .EUt(1440)                            // 平均能耗
                .buildAndRegister();

        // 卤水多级闪蒸处理（深度处理）
        SFM.recipeBuilder()
                .fluidInputs(Bittern.getFluid(8000))
                .output(dust, Salt, 21)               // 总盐量 = 15 + 6
                .fluidOutputs(Bitterncl.getFluid(800))  // 浓缩氯化物相
                .fluidOutputs(Bitternso.getFluid(600))  // 浓缩硫酸盐相
                .fluidOutputs(Bitternco.getFluid(400))  // 浓缩碳酸盐相
                .fluidOutputs(Bitternbr.getFluid(200))  // 浓缩溴相
                .fluidOutputs(DistilledWater.getFluid(5000)) // 副产纯水
                .duration(420)                         // 240+180
                .EUt(1920)                            // 取较高能耗值
                .buildAndRegister();
    }

    private static void Common() {
        // 阶段1: 卤水预处理与分馏
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(Bittern.getFluid(8000))
                .output(dust, Salt, 12) // 食盐结晶
                .fluidOutputs(Bitterncl.getFluid(2000)) // 氯化物富集相
                .fluidOutputs(Bitternso.getFluid(1500)) // 硫酸盐富集相
                .fluidOutputs(Bitternco.getFluid(1000)) // 碳酸盐富集相
                .fluidOutputs(Bitternbr.getFluid(500))  // 溴富集相
                .duration(240).EUt(480).buildAndRegister();

        // 阶段2: 溴元素专项提取
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Bitternbr.getFluid(1000))
                .fluidInputs(Chlorine.getFluid(200)) // 氯气氧化
                .output(dust, Bromine, 2) // 溴单质
                .fluidOutputs(HydrochloricAcid.getFluid(300))
                .duration(120).EUt(240).buildAndRegister();

        // 阶段3: 氯化物相处理（锂/镁/钙提取）
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(Bitterncl.getFluid(3000))
                .input(dust, SodaAsh, 6) // Na₂CO₃ 沉淀剂
                .output(dust, LithiumCarbonate, 2) // 碳酸锂
                .output(dust, MagnesiumHydroxide, 3) // 氢氧化镁
                .output(dust, CalciumCarbonate, 3) // 碳酸钙
                .fluidOutputs(SaltWater.getFluid(1000))
                .duration(300).EUt(1920).buildAndRegister();

        // 阶段4: 硫酸盐相处理（钾/镁提取）
        ELECTROBATH.recipeBuilder()
                .fluidInputs(Bitternso.getFluid(2000))
                .output(dust, PotassiumHydroxide, 6) // 氢氧化钾
                .output(dust, MagnesiumHydroxide, 2)
                .fluidOutputs(Oxygen.getFluid(1000))
                .fluidOutputs(SulfurDioxide.getFluid(500))
                .tier(1)
                .duration(400).EUt(3072).buildAndRegister();

        // 阶段5: 碳酸盐相处理（钠/钡提取）
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Bitternco.getFluid(1500))
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .output(dust, Salt, 4) // 氯化钠
                .output(dust, BariumChloride, 3) // 氯化钡
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .duration(180).EUt(960).buildAndRegister();

        // 阶段6: 海水直接处理优化
        VACUUM_RECIPES.recipeBuilder()
                .fluidInputs(SeaWater.getFluid(16000)) // 增大处理量
                .output(dust, Salt, 24)
                .fluidOutputs(Bittern.getFluid(3000)) // 浓缩卤水
                .fluidOutputs(DistilledWater.getFluid(12000))
                .duration(360).EUt(1920).buildAndRegister();

        // 阶段7: 副产物深度加工
        BLAST_RECIPES.recipeBuilder()
                .input(dust, MagnesiumHydroxide, 5)
                .output(ingot, Magnesium, 2) // 金属镁锭
                .fluidOutputs(Steam.getFluid(1000))
                .blastFurnaceTemp(1200)
                .duration(600).EUt(3840).buildAndRegister();

    }
}
