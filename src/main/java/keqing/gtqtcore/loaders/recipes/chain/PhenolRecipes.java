package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.recipes.RecipeMaps.BLAST_RECIPES;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.CHEMICAL_PLANT;
import static keqing.gtqtcore.api.unification.GTQTMaterials.BenzenesulfonicAcid;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class PhenolRecipes {
    public static void init()
    {
        //碳酸钠与二氧化硫在水中反应生成亚硫酸钠
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SodaAsh, 1)  // 碳酸钠 Na2CO3
                .fluidInputs(SulfurDioxide.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .output(dust, SodiumSulfite, 3)  // 亚硫酸钠 Na2SO3
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .duration(200).EUt(30).buildAndRegister();

        //(替代路线) 氢氧化钠与二氧化硫直接反应
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SodiumHydroxide, 3)
                .fluidInputs(SulfurDioxide.getFluid(1000))
                .output(dust, SodiumSulfite, 3)  // 2NaOH + SO2 → Na2SO3 + H2O
                .fluidOutputs(Water.getFluid(1000))
                .duration(200).EUt(30).buildAndRegister();

        // 1. 苯被浓硫酸磺化
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Benzene.getFluid(1000))
                .fluidInputs(SulfuricAcid.getFluid(1000)) // 浓硫酸
                .fluidOutputs(BenzenesulfonicAcid.getFluid(1000))
                .duration(200).EUt(30).buildAndRegister();

        // 2. 亚硫酸钠中和
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(BenzenesulfonicAcid.getFluid(1000))
                .input(dust, SodiumSulfite, 3)
                .fluidOutputs(SodiumBenzenesulfonate.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .duration(200).EUt(30).buildAndRegister();

        // 3. 氢氧化钠碱熔
        BLAST_RECIPES.recipeBuilder()
                .fluidInputs(SodiumBenzenesulfonate.getFluid(1000))
                .input(dust, SodiumHydroxide, 3)
                .output(dust, SodiumPhenoxide, 3)
                .blastFurnaceTemp(1350)
                .duration(300).EUt(120).buildAndRegister();

        // 4. 酸化制苯酚
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SodiumPhenoxide, 3)
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .fluidOutputs(Phenol.getFluid(1000))
                .output(dust, Salt, 1) // NaCl
                .fluidOutputs(Water.getFluid(1000))
                .duration(200).EUt(30).buildAndRegister();
    }


}
