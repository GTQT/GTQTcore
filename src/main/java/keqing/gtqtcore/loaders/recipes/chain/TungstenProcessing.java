package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;

import static gregtech.api.recipes.RecipeMaps.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.DRYER_RECIPES;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.SFM;
import static gregtech.api.GTValues.EV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.ingotHot;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

/**
 * The Tungsten Process
 *
 * <p>
 * Adds extra steps for making Tungsten
 * </p>
 *
 * <p>Main Products: Tungsten</p>
 * <p>Side Products: None</p>
 *
 * <p>Does not modify the original CEu product yields</p>
 */
public class TungstenProcessing {

    public static void init() {
        tungstenChain();
        removeGTCERecipes();
    }

    private static void removeGTCERecipes() {
        // Remove Tungstic Acid -> Tungsten
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.ELECTROLYZER_RECIPES, OreDictUnifier.get(dust, TungsticAcid, 7));
    }

    private static void tungstenChain() {
        //1倍
        // 化反H2O(WO3) +氨气+水=钨酸铵
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, TungsticAcid, 7)
                .fluidInputs(Ammonia.getFluid(6000))
                .fluidInputs(Water.getFluid(6000))
                .fluidOutputs(AmmoniumTungstate.getFluid(7000))
                .duration(200).EUt(480).buildAndRegister();
        // 结晶 钨酸铵 = 钨酸铵晶体
        AUTOCLAVE_RECIPES.recipeBuilder()
                .fluidInputs(AmmoniumTungstate.getFluid(7000))
                .output(dust,CammoniumTungstate,7)
                .duration(400).EUt(480).buildAndRegister();
        // 钨酸铵-> WO3 + H2O
        BLAST_RECIPES.recipeBuilder()
                .input(dust, CammoniumTungstate, 7)
                .output(dust, TungstenTrioxide, 4)
                .fluidOutputs(Water.getFluid(6000))
                .fluidInputs(Ammonia.getFluid(6000))
                .blastFurnaceTemp(3600)
                .duration(400).EUt(480).buildAndRegister();


        //二倍
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Scheelite, 1)
                .input(dust,SodaAsh,2)
                .fluidInputs(Water.getFluid(4000))
                .output(dust, SodiumTungstateDihydrate, 1)
                .duration(400).EUt(480).buildAndRegister();

        SFM.recipeBuilder()
                .fluidInputs(HydrochloricAcid.getFluid(10000))
                .input(dust,SodiumTungstateDihydrate,10)
                .chancedOutput(dust, Powellite,10, 5000, 0)
                .fluidOutputs(ItungsticAcid.getFluid(800))
                .fluidOutputs(ItungsticAcid.getFluid(800))
                .fluidOutputs(ItungsticAcid.getFluid(800))
                .fluidOutputs(ItungsticAcid.getFluid(400))
                .fluidOutputs(ItungsticAcid.getFluid(400))
                .fluidOutputs(ItungsticAcid.getFluid(400))
                .fluidOutputs(ItungsticAcid.getFluid(200))
                .fluidOutputs(ItungsticAcid.getFluid(200))
                .fluidOutputs(ItungsticAcid.getFluid(200))
                .fluidOutputs(ItungsticAcid.getFluid(100))
                .fluidOutputs(ItungsticAcid.getFluid(100))
                .fluidOutputs(ItungsticAcid.getFluid(100))
                .duration(2000).EUt(480).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(ItungsticAcid.getFluid(1000))
                .fluidInputs(Ammonia.getFluid(6000))
                .fluidInputs(Water.getFluid(6000))
                .fluidOutputs(AmmoniumTungstate.getFluid(7000))
                .duration(400).EUt(480).buildAndRegister();

        DRYER_RECIPES.recipeBuilder()
                .fluidInputs(ItungsticAcid.getFluid(1000))
                .output(dust, TungstenTrioxide, 1)
                .duration(400).EUt(480).buildAndRegister();

        //下边不要动喵

        // 2WO3 + 3C -> 2W + 3CO2
        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, TungstenTrioxide, 8)
                .input(dust, Carbon, 3)
                .output(ingotHot, Tungsten, 2)
                .fluidOutputs(CarbonDioxide.getFluid(3000))
                .blastFurnaceTemp(Tungsten.getBlastTemperature())
                .duration(2400).EUt(VA[EV]).buildAndRegister();

        // WO3 + 6H -> W + 3H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, TungstenTrioxide, 4)
                .fluidInputs(Hydrogen.getFluid(6000))
                .output(dust, Tungsten)
                .fluidOutputs(Water.getFluid(3000))
                .duration(210).EUt(960).buildAndRegister();
    }
}
