package keqing.gtqtcore.loaders.recipes.handlers;

import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class OreDeal {
    public static void init() {
        GoldDeal();

    }

    public static void GoldDeal() {
        //硫化金处理
        //焙烧
        BLAST_RECIPES.recipeBuilder()
                .input(dust,LeanGoldSulphide)
                .output(dust,LeanGoldBs)
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(SulfurDioxide.getFluid(1000))
                .blastFurnaceTemp(1700)
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        CLARIFIER.recipeBuilder()
                .input(dust,LeanGoldBs,10)
                .fluidInputs(SodiumCyanide.getFluid(1000))
                .fluidOutputs(LeanGoldJc.getFluid(10000))
                .output(dust,LeanCopperJc,10)
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Zinc)
                .fluidInputs(LeanGoldJc.getFluid(1000))
                .output(dust,ZincSulfate)
                .fluidOutputs(LeanGoldCl.getFluid(1000))
                .fluidOutputs()
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .fluidInputs(LeanGoldCl.getFluid(1000))
                .output(dust,LeanGoldDr)
                .fluidOutputs(SulfuricNickelSolution.getFluid(1000))
                .fluidOutputs()
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust,LeanGoldDr)
                .output(dust,Gold)
                .fluidInputs(Oxygen.getFluid(1000))
                .blastFurnaceTemp(1700)
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust,LeanCopperJc)
                .chancedOutput(dust,Copper,5000,0)
                .chancedOutput(dust,Nickel,3000,0)
                .chancedOutput(dust,Iron,2000,0)
                .duration(100)
                .EUt(30)
                .buildAndRegister();


        BLAST_RECIPES.recipeBuilder()
                .input(dust,RichGoldSulphide)
                .output(dust,RichGoldBs)
                .fluidInputs(Oxygen.getFluid(7000))
                .fluidOutputs(SulfurDioxide.getFluid(4000))
                .blastFurnaceTemp(1700)
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        CLARIFIER.recipeBuilder()
                .input(dust,RichGoldBs,10)
                .fluidInputs(SodiumCyanide.getFluid(1000))
                .fluidOutputs(LeanGoldJc.getFluid(20000))
                .output(dust,RichCopperJc,20)
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust,RichCopperJc)
                .chancedOutput(dust,Copper,8000,0)
                .chancedOutput(dust,Iron,8000,0)
                .chancedOutput(dust,Nickel,5000,0)
                .chancedOutput(dust,Tin,5000,0)
                .duration(100)
                .EUt(30)
                .buildAndRegister();
    }
}
