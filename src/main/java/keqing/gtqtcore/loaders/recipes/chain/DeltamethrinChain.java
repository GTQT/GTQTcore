package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.unification.ore.OrePrefix;

import static gregtech.api.GTValues.IV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.Resorcinol;

public class DeltamethrinChain {
    public static void init() {
        //PR
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Bromine.getFluid(2000))
                .fluidInputs(Phenol.getFluid(1000))
                .fluidOutputs(Dfeltamethrina.getFluid(3000))
                .duration(400).EUt(VA[IV]).buildAndRegister();
        //1. 首先，以3,4-二溴水苯为原料，在碱性条件下进行溴取反应，将其与氢氧化钠反应生成2,3-二溴苯酚。
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Dfeltamethrina.getFluid(2000))
                .input(OrePrefix.dust,SodiumOxide)
                .fluidOutputs(Dfeltamethrinb.getFluid(3000))
                .duration(400).EUt(VA[IV]).buildAndRegister();
        //2. 接下来，将2,3-二溴苯酚与氯丙酮进行缩合反应，生成2,3-二溴-2-丙酮酚。
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Dfeltamethrinb.getFluid(1000))
                .fluidInputs(Acetone.getFluid(1000))
                .fluidInputs(Chlorine.getFluid(1000))
                .fluidOutputs(Dfeltamethrinc.getFluid(3000))
                .duration(400).EUt(VA[IV]).buildAndRegister();
        //3. 然后，将2,3-二溴-2-丙酮酚与盐酸进行酸化反应，生成2,3-二溴-2-丙酮酚盐酸盐。
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Dfeltamethrinc.getFluid(1000))
                .fluidInputs(HydrochloricAcid.getFluid(2000))
                .fluidOutputs(Dfeltamethrind.getFluid(3000))
                .duration(400).EUt(VA[IV]).buildAndRegister();
        //4. 最后，将2,3-二溴-2-丙酮酚盐酸盐与碳酸银反应，生成1R,3R-二溴菊酸。
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Dfeltamethrind.getFluid(1000))
                .input(OrePrefix.dust,SodaAsh,8)
                .notConsumable(OrePrefix.dust,Silver)
                .fluidOutputs(Dfeltamethrine.getFluid(3000))
                .duration(400).EUt(VA[IV]).buildAndRegister();
        //(R,顺)-2,2-二甲基-3-(2,2-二溴乙烯基环丙烷羧酸)，即二溴菊酸，用亚硫酰氯氯化，生成二溴菊酰氯。
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Dfeltamethrine.getFluid(2000))
                .fluidInputs(ThionylChloride.getFluid(1000))
                .fluidOutputs(Dfeltamethrinf.getFluid(3000))
                .duration(400).EUt(VA[IV]).buildAndRegister();
        //间苯氧基苯甲醛与氢氰酸加成得α-氰基间苯氧基苯甲醇。
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Dfeltamethring.getFluid(1000))
                .input(OrePrefix.dust,SodiumCyanide,4)
                .fluidOutputs(Dfeltamethrinh.getFluid(2000))
                .duration(400).EUt(VA[IV]).buildAndRegister();

        //间苯氧基苯甲醛
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Resorcinol.getFluid(1000))
                .fluidInputs(Formaldehyde.getFluid(1000))
                .fluidOutputs(Dfeltamethring.getFluid(2000))
                .duration(400).EUt(VA[IV]).buildAndRegister();


        //在吡啶存在下，二溴菊酰氯与(+)α-氰基间苯氧基苯甲醇作用得溴氰菊酯：
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Dfeltamethrinf.getFluid(1000))
                .fluidInputs(Dfeltamethrinh.getFluid(1000))
                .output(OrePrefix.dust,Deltamethrin,20)
                .duration(400).EUt(VA[IV]).buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(Water.getFluid(2000))
                .fluidOutputs(Dfeltamethrin.getFluid(2000))
                .input(OrePrefix.dust,Deltamethrin,20)
                .duration(400).EUt(VA[IV]).buildAndRegister();
    }


}
