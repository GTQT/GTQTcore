package keqing.gtqtcore.loaders.recipes.handlers;

import static gregtech.api.unification.ore.OrePrefix.dustTiny;
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
                .output(dust,LeanGoldBs,4)
                .fluidOutputs(SulfurDioxide.getFluid(1000))
                .blastFurnaceTemp(1600)
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        COKE_OVEN_RECIPES.recipeBuilder()
                .input(dust,LeanGoldSulphide)
                .output(dust,LeanGoldBs)
                .fluidOutputs(SulfurDioxide.getFluid(1000))
                .duration(400)
                .buildAndRegister();

        ARC_FURNACE_RECIPES.recipeBuilder()
                .input(dust,LeanGoldBs)
                .output(dust,Gold,1)
                .fluidInputs(Oxygen.getFluid(1000))
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        CLARIFIER.recipeBuilder()
                .input(dust,LeanGoldBs,24)
                .fluidOutputs(LeanGoldJc.getFluid(16000))
                .output(dust,LeanCopperJc,8)
                .duration(1200)
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust,LeanGoldBs,12)
                .fluidOutputs(LeanGoldJc.getFluid(6000))
                .output(dust,LeanCopperJc,6)
                .duration(100)
                .EUt(120)
                .buildAndRegister();

        //置换
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Zinc)
                .fluidInputs(LeanGoldJc.getFluid(4000))
                .output(dust,ZincSulfate)
                .fluidOutputs(LeanGoldCl.getFluid(4000))
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        ARC_FURNACE_RECIPES.recipeBuilder()
                .fluidInputs(LeanGoldJc.getFluid(1000))
                .output(dust,Gold)
                .fluidOutputs(SulfuricAcid.getFluid(1000))
                .fluidOutputs()
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .fluidInputs(LeanGoldCl.getFluid(1000))
                .output(dust,LeanGoldDr,4)
                .fluidOutputs(SulfuricNickelSolution.getFluid(1000))
                .fluidOutputs()
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        ARC_FURNACE_RECIPES.recipeBuilder()
                .input(dust,LeanGoldDr)
                .output(dust,Gold,2)
                .fluidInputs(Oxygen.getFluid(1000))
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
                .fluidOutputs(SulfurDioxide.getFluid(4000))
                .blastFurnaceTemp(1700)
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        COKE_OVEN_RECIPES.recipeBuilder()
                .input(dust,RichGoldSulphide)
                .output(dust,RichGoldBs,4)
                .fluidOutputs(SulfurDioxide.getFluid(1000))
                .duration(400)
                .buildAndRegister();

        ARC_FURNACE_RECIPES.recipeBuilder()
                .input(dust,RichGoldBs)
                .output(dust,Gold,2)
                .fluidInputs(Oxygen.getFluid(1000))
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        CLARIFIER.recipeBuilder()
                .input(dust,RichGoldBs,24)
                .fluidOutputs(LeanGoldJc.getFluid(16000))
                .output(dust,RichCopperJc,8)
                .duration(1200)
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust,RichGoldBs,12)
                .fluidOutputs(LeanGoldJc.getFluid(6000))
                .output(dust,RichCopperJc,6)
                .duration(100)
                .EUt(120)
                .buildAndRegister();

        ARC_FURNACE_RECIPES.recipeBuilder()
                .input(dust,RichCopperJc)
                .output(dust,Gold,4)
                .fluidInputs(Oxygen.getFluid(1000))
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
