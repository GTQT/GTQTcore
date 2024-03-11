package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;

import static gregtech.api.GTValues.MV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class GermaniumChain {
    public static void init() {
        Che();
        Beishao();
        Shifa();
        Zhenliu();
    }

    private static void Che() {
        //氢氧化铁
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Iron,1)
                .input(dust,SodiumHydroxide,3)
                .fluidInputs(NitricAcid.getFluid(3000))
                .output(dust,SodiumNitrate,3)
                .output(dust,Qingyanghuatie,1)
                .duration(20)
                .EUt(120)
                .buildAndRegister();
        //鞣酸
        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .input(log, Wood, 16)
                .fluidInputs(HydrobromicAcid.getFluid(100))
                .fluidOutputs(Rzousuan.getFluid(400))
                .EUt(VA[MV])
                .duration(120)
                .rate(60)
                .buildAndRegister();

        SFM.recipeBuilder()
                .duration(100)
                .EUt(120)
                .fluidInputs(Rzousuan.getFluid(2000))
                .input(OrePrefix.dust,Carbon,80)
                .fluidOutputs(HydrobromicAcid.getFluid(5))
                .fluidOutputs(HydrobromicAcid.getFluid(5))
                .fluidOutputs(HydrobromicAcid.getFluid(5))
                .fluidOutputs(HydrobromicAcid.getFluid(5))
                .fluidOutputs(Rousuan.getFluid(10))
                .fluidOutputs(Rousuan.getFluid(10))
                .fluidOutputs(Rousuan.getFluid(10))
                .fluidOutputs(Rousuan.getFluid(10))
                .fluidOutputs(Rousuan.getFluid(10))
                .fluidOutputs(Rousuan.getFluid(10))
                .fluidOutputs(Rousuan.getFluid(10))
                .fluidOutputs(Rousuan.getFluid(10))
                .buildAndRegister();
    }

    private static void Zhenliu() {
        //四氯化锗
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Fujishanxinbeishaocanliu,1)
                .fluidInputs(HydrochloricAcid.getFluid(4000))
                .fluidOutputs(GermaniumTetrachloride.getFluid(1000))
                .duration(20)
                .EUt(120)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Fujizhechendian,1)
                .fluidInputs(HydrochloricAcid.getFluid(4000))
                .fluidOutputs(GermaniumTetrachloride.getFluid(1000))
                .duration(20)
                .EUt(120)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(GermaniumTetrachloride.getFluid(1000))
                .fluidInputs(HydrochloricAcid.getFluid(500))
                .fluidOutputs(Silvhuazhe.getFluid(1000))
                .duration(20)
                .EUt(120)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Silvhuazhe.getFluid(1000))
                .fluidInputs(HydrochloricAcid.getFluid(500))
                .fluidOutputs(Ssilvhuazhe.getFluid(2000))
                .duration(20)
                .EUt(120)
                .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(Ssilvhuazhe.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(1000))
                .fluidOutputs(Iron3Chloride.getFluid(1000))
                .fluidOutputs(Sgilvhuazhe.getFluid(1000))
                .duration(20)
                .EUt(120)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Sgilvhuazhe.getFluid(1000))
                .fluidInputs(Water.getFluid(2000))
                .fluidOutputs(HydrochloricAcid.getFluid(4000))
                .output(dust,Egryanghuazhe)
                .duration(20)
                .EUt(120)
                .buildAndRegister();

        REACTION_FURNACE_RECIPES.recipeBuilder()
                .input(dust,Egryanghuazhe,4)
                .output(dust,Germanium,4)
                .chancedOutput(dust,Hungezhecanzha,1,1000,500)
                .chancedOutput(dust,Huntiezhecanzha,1,1000,500)
                .fluidOutputs(Oxygen.getFluid(8000))
                .duration(1000)
                .EUt(480)
                .buildAndRegister();
    }

    private static void Beishao() {
        //闪锌矿+氧气 回转炉=氧化闪锌混合物
        REACTION_FURNACE_RECIPES.recipeBuilder()
                .input(crushed,Sphalerite,16)
                .fluidInputs(Oxygen.getFluid(16000))
                .output(dust,Yanghuashanxinhunhewu,8)
                .duration(1000)
                .EUt(480)
                .buildAndRegister();

        REACTION_FURNACE_RECIPES.recipeBuilder()
                .input(dust,Sphalerite,32)
                .fluidInputs(Oxygen.getFluid(16000))
                .output(dust,Yanghuashanxinhunhewu,8)
                .duration(1000)
                .EUt(480)
                .buildAndRegister();

        //氧化闪锌混合物+过氧化氢=富集闪锌混合物
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Yanghuashanxinhunhewu,1)
                .fluidInputs(HydrogenPeroxide.getFluid(1000))
                .output(dust,Fujishanxinhunhewu,1)
                .duration(20)
                .EUt(120)
                .buildAndRegister();

        //富集闪锌混合物+盐 焙烧=富集闪锌焙烧残留
        REACTION_FURNACE_RECIPES.recipeBuilder()
                .input(dust,Fujishanxinhunhewu,16)
                .input(dust,Salt,16)
                .output(dust,Fujishanxinbeishaocanliu,16)
                .output(dust,Sodium,8)
                .duration(1000)
                .EUt(480)
                .buildAndRegister();
    }

    private static void Shifa() {
        //闪锌矿+硫酸=含闪锌溶液
        CHEMICAL_RECIPES.recipeBuilder()
                .input(crushed,Sphalerite,16)
                .fluidInputs(SulfuricAcid.getFluid(8000))
                .fluidOutputs(Hanxinrongye.getFluid(8000))
                .duration(20)
                .EUt(120)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Sphalerite,32)
                .fluidInputs(SulfuricAcid.getFluid(8000))
                .fluidOutputs(Hanxinrongye.getFluid(8000))
                .duration(20)
                .EUt(120)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Qingyanghuatie,1)
                .fluidInputs(Hanxinrongye.getFluid(8000))
                .fluidOutputs(Canzharongye.getFluid(8000))
                .output(dust,Huntiezhecanzha,8)
                .duration(20)
                .EUt(120)
                .buildAndRegister();

        //锌置换
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Zinc,1)
                .fluidInputs(Canzharongye.getFluid(1000))
                .output(dust,Hungezhecanzha,2)
                .duration(20)
                .EUt(120)
                .buildAndRegister();

        //渣处理
        REACTION_FURNACE_RECIPES.recipeBuilder()
                .input(dust,Huntiezhecanzha,16)
                .fluidOutputs(Yiyanghauzhe.getFluid(16000))
                .output(dust,Zhezha,16)
                .duration(1000)
                .EUt(480)
                .buildAndRegister();

        REACTION_FURNACE_RECIPES.recipeBuilder()
                .input(dust,Hungezhecanzha,16)
                .fluidOutputs(Yiyanghauzhe.getFluid(16000))
                .output(dust,Zhezha,16)
                .duration(1000)
                .EUt(480)
                .buildAndRegister();

        //锗渣处理
        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust,Zhezha,8)
                .output(dust,Iron,2)
                .output(dust,Zinc,2)
                .output(dust,Lead,2)
                .output(dust,Cadmium,2)
                .duration(1000)
                .EUt(480)
                .buildAndRegister();

        //烟气处理
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Rousuan.getFluid(1000))
                .fluidInputs(Yiyanghauzhe.getFluid(8000))
                .output(dust,Fujizhechendian,8)
                .duration(20)
                .EUt(120)
                .buildAndRegister();
    }
}
