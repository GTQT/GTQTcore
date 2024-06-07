package keqing.gtqtcore.loaders.recipes.chain;

import keqing.gtqtcore.api.unification.GTQTMaterials;

import static gregtech.api.recipes.RecipeMaps.DISTILLATION_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.SFM;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Bitternco;

public class MagicChain {
    public static void init() {
        MagicGas();
    }

    private static void MagicGas() {
        //离散态素魔力 含有杂质，需要提纯
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.MagicGas.getFluid(2000))
                .fluidOutputs(MagicDas.getFluid(300))
                .fluidOutputs(MagicDas.getFluid(200))
                .fluidOutputs(MagicDas.getFluid(100))
                .fluidOutputs(MagicFas.getFluid(300))
                .fluidOutputs(MagicFas.getFluid(200))
                .fluidOutputs(MagicFas.getFluid(100))
                .fluidOutputs(MagicFas.getFluid(300))
                .fluidOutputs(MagicFas.getFluid(200))
                .fluidOutputs(MagicFas.getFluid(100))
                .fluidOutputs(Water.getFluid(300))
                .fluidOutputs(Water.getFluid(200))
                .fluidOutputs(Water.getFluid(100))
                .duration(20).EUt(120).buildAndRegister();
        //离散态纯净魔力-》元素充能-》凝聚态素魔力
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.MagicFas.getFluid(2000))
                .fluidOutputs(MagicFas.getFluid(300))
                .fluidOutputs(MagicFas.getFluid(200))
                .fluidOutputs(MagicFas.getFluid(100))
                .fluidOutputs(MagicDas.getFluid(300))
                .fluidOutputs(MagicDas.getFluid(200))
                .fluidOutputs(MagicDas.getFluid(100))
                .fluidOutputs(MagicDas.getFluid(300))
                .fluidOutputs(MagicDas.getFluid(200))
                .fluidOutputs(MagicDas.getFluid(100))
                .fluidOutputs(Water.getFluid(300))
                .fluidOutputs(Water.getFluid(200))
                .fluidOutputs(Water.getFluid(100))
                .duration(20).EUt(120).buildAndRegister();
        //凝聚态素魔力-》魔力萃取-》魔力
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.MagicDas.getFluid(2000))
                .fluidOutputs(MagicDas.getFluid(300))
                .fluidOutputs(MagicDas.getFluid(200))
                .fluidOutputs(MagicDas.getFluid(100))
                .fluidOutputs(MagicAas.getFluid(300))
                .fluidOutputs(MagicAas.getFluid(200))
                .fluidOutputs(MagicAas.getFluid(100))
                .fluidOutputs(MagicAas.getFluid(300))
                .fluidOutputs(MagicAas.getFluid(200))
                .fluidOutputs(MagicAas.getFluid(100))
                .fluidOutputs(Water.getFluid(300))
                .fluidOutputs(Water.getFluid(200))
                .fluidOutputs(Water.getFluid(100))
                .duration(20).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.MagicAas.getFluid(2000))
                .fluidOutputs(MagicAas.getFluid(300))
                .fluidOutputs(MagicAas.getFluid(200))
                .fluidOutputs(MagicAas.getFluid(100))
                .fluidOutputs(Magic.getFluid(300))
                .fluidOutputs(Magic.getFluid(200))
                .fluidOutputs(Magic.getFluid(100))
                .fluidOutputs(Magic.getFluid(300))
                .fluidOutputs(Magic.getFluid(200))
                .fluidOutputs(Magic.getFluid(100))
                .fluidOutputs(MagicRub.getFluid(300))
                .fluidOutputs(MagicRub.getFluid(200))
                .fluidOutputs(MagicRub.getFluid(100))
                .duration(20).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.Magic.getFluid(2000))
                .fluidOutputs(Magic.getFluid(300))
                .fluidOutputs(Magic.getFluid(200))
                .fluidOutputs(Magic.getFluid(100))
                .fluidOutputs(Richmagic.getFluid(300))
                .fluidOutputs(Richmagic.getFluid(200))
                .fluidOutputs(Richmagic.getFluid(100))
                .fluidOutputs(Richmagic.getFluid(300))
                .fluidOutputs(Richmagic.getFluid(200))
                .fluidOutputs(Richmagic.getFluid(100))
                .fluidOutputs(MagicRub.getFluid(300))
                .fluidOutputs(MagicRub.getFluid(200))
                .fluidOutputs(MagicRub.getFluid(100))
                .duration(20).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.MagicRub.getFluid(2000))
                .fluidOutputs(MagicRub.getFluid(300))
                .fluidOutputs(MagicRub.getFluid(200))
                .fluidOutputs(MagicRub.getFluid(100))
                .fluidOutputs(MagicDas.getFluid(300))
                .fluidOutputs(MagicDas.getFluid(200))
                .fluidOutputs(MagicDas.getFluid(100))
                .fluidOutputs(MagicDas.getFluid(300))
                .fluidOutputs(MagicDas.getFluid(200))
                .fluidOutputs(MagicDas.getFluid(100))
                .fluidOutputs(MagicFas.getFluid(300))
                .fluidOutputs(MagicFas.getFluid(200))
                .fluidOutputs(MagicFas.getFluid(100))
                .duration(20).EUt(120).buildAndRegister();
    }
}
