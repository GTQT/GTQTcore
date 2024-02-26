package keqing.gtqtcore.loaders.recipes.chain;

import keqing.gtqtcore.api.unification.GTQTMaterials;

import static gregtech.api.recipes.RecipeMaps.DISTILLATION_RECIPES;
import static gregtech.api.unification.material.Materials.Salt;
import static gregtech.api.unification.material.Materials.SaltWater;
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
                .fluidOutputs(MagicFas.getFluid(300))
                .fluidOutputs(MagicFas.getFluid(200))
                .fluidOutputs(MagicFas.getFluid(100))
                .fluidOutputs(MagicFas.getFluid(300))
                .fluidOutputs(MagicFas.getFluid(200))
                .fluidOutputs(MagicFas.getFluid(100))
                .fluidOutputs(MagicDas.getFluid(300))
                .fluidOutputs(MagicDas.getFluid(200))
                .fluidOutputs(MagicDas.getFluid(100))
                .fluidOutputs(LiquidMarsAir.getFluid(300))
                .fluidOutputs(LiquidMarsAir.getFluid(200))
                .fluidOutputs(LiquidMarsAir.getFluid(100))
                .duration(20).EUt(120).buildAndRegister();
        //离散态纯净魔力-》元素充能-》凝聚态素魔力

        //凝聚态素魔力-》魔力萃取-》魔力
    }
}
