package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtechfoodoption.GTFOMaterialHandler.AceticAnhydride;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.SodiumAcetate;

public class PPBChain {
    //乙酸铋ok、十六烷基三甲基溴化铵ok、坏血酸酸ok，得到混合溶液
    //加入乙酰丙酮铂和乙酰丙酮钯
    public static void init() {
        //混合溶液
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(BismuthAcetate.getFluid(1000))
                .fluidInputs(CetaneTrimethylAmmoniumBromide.getFluid(1000))
                .fluidInputs(AscorbicAcid.getFluid(1000))
                .fluidOutputs(PPBFront.getFluid(3000))
                .fluidOutputs(AceticAnhydride.getFluid(1000))
                .fluidOutputs(AceticAcid.getFluid(1000))
                .duration(200).EUt(VA[IV]).buildAndRegister();

        //乙酸丙酯
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(SodiumAcetate.getFluid(1000))
                .notConsumable(NitricAcid.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .fluidOutputs(PropylAcetate.getFluid(1000))
                .circuitMeta(5)
                .duration(200).EUt(VA[IV]).buildAndRegister();

        //乙酰丙酮
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(PropylAcetate.getFluid(1000))
                .fluidInputs(AceticAnhydride.getFluid(1000))
                .fluidOutputs(Acetylacetone.getFluid(2000))
                .duration(200).EUt(VA[IV]).buildAndRegister();

        //乙酰丙酮铂=乙酰丙酮+氯化
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Acetylacetone.getFluid(1000))
                .input(dust,Platinum)
                .fluidInputs(Oxygen.getFluid(2000))
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .fluidOutputs(PlatinumBis.getFluid(3000))
                .duration(200).EUt(VA[IV]).buildAndRegister();
        //乙酰丙酮钯=乙酰丙酮+氯化
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Acetylacetone.getFluid(1000))
                .input(dust,Palladium)
                .fluidInputs(Oxygen.getFluid(2000))
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .fluidOutputs(PalladiumBis.getFluid(3000))
                .duration(200).EUt(VA[IV]).buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(PPBFront.getFluid(3000))
                .fluidInputs(PalladiumBis.getFluid(1000))
                .fluidInputs(PlatinumBis.getFluid(1000))
                .output(dust,PPB,20)
                .fluidOutputs(AceticAnhydride.getFluid(1000))
                .fluidOutputs(AceticAcid.getFluid(1000))
                .duration(400).EUt(VA[IV]).buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(BismuthAcetate.getFluid(1000))
                .fluidInputs(CetaneTrimethylAmmoniumBromide.getFluid(1000))
                .fluidInputs(AscorbicAcid.getFluid(1000))
                .fluidOutputs(BismuthAcetate.getFluid(3000))
                .duration(200).EUt(VA[IV]).buildAndRegister();

        //乙酸铋=氧化铋+乙酸+乙酸酐
        BLAST_RECIPES.recipeBuilder()
                .input(dust, Bismuth,2)
                .fluidInputs(Oxygen.getFluid(3000))
                .output(dust,BismuthOxygen,5)
                .duration(200).EUt(VA[IV]).buildAndRegister();

        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, BismuthOxygen,5)
                .fluidInputs(AceticAcid.getFluid(1000))
                .fluidInputs(AceticAnhydride.getFluid(1000))
                .fluidOutputs(BismuthAcetate.getFluid(5000))
                .duration(200).EUt(VA[IV]).buildAndRegister();
    }

}
