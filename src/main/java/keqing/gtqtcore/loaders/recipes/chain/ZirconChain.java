package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.ore.OrePrefix;

import static gregtech.api.GTValues.EV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_BATH_RECIPES;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;

import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.HafniumTetrachloride;
import static keqing.gtqtcore.api.unification.TJMaterials.ZirconiumTetrachloride;

public class ZirconChain {
    public static void init() {
        //锆石-》烧结  熟制精炼锆石粉
        REACTION_FURNACE_RECIPES.recipeBuilder()
                .input(dust, Zircon, 16)
                .input(dust, Carbon, 4)
                .output(dust, Zirconf, 16)
                .fluidOutputs(CarbonDioxide.getFluid(4000))
                .duration(400).EUt(VA[EV]).buildAndRegister();
        //-》酸浸     酸性锆石溶液
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, Zirconf, 4)
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .fluidOutputs(Zircons.getFluid(400))
                .duration(400).EUt(VA[EV]).buildAndRegister();
        //-》闪蒸     硫酸锆 铪 +
        SFM.recipeBuilder()
                .duration(100)
                .EUt(120)
                .fluidInputs(Zircons.getFluid(6000))
                .input(OrePrefix.dust,Sodium,10)
                .fluidOutputs(Zrso.getFluid(200))
                .fluidOutputs(Hfso.getFluid(200))
                .fluidOutputs(Zrso.getFluid(200))
                .fluidOutputs(Hfso.getFluid(200))
                .fluidOutputs(Zrso.getFluid(200))
                .fluidOutputs(Hfso.getFluid(200))
                .fluidOutputs(Zrso.getFluid(200))
                .fluidOutputs(Hfso.getFluid(200))
                .fluidOutputs(SulfuricNickelSolution.getFluid(200))
                .fluidOutputs(SulfuricCopperSolution.getFluid(200))
                .fluidOutputs(SulfuricNickelSolution.getFluid(200))
                .fluidOutputs(SulfuricCopperSolution.getFluid(200))
                .buildAndRegister();
        //化反+盐酸   四氯化锆 铪
        CHEMICAL_RECIPES.recipeBuilder()
                .output(dust,SodiumSulfide,7)
                .fluidInputs(HydrochloricAcid.getFluid(4000))
                .fluidInputs(Zrso.getFluid(1000))
                .fluidOutputs(ZirconiumTetrachloride.getFluid(1000))
                .duration(400).EUt(VA[EV]).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .output(dust,SodiumSulfide,7)
                .fluidInputs(HydrochloricAcid.getFluid(4000))
                .fluidInputs(Hfso.getFluid(1000))
                .fluidOutputs(HafniumTetrachloride.getFluid(1000))
                .duration(400).EUt(VA[EV]).buildAndRegister();
    }
}
