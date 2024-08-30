package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.common.items.MetaItems;

import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

public class BoronNitrideChain {

    static int SECOND=20;
    public static void init() {
        //氮化硼
        BoronTrioxideChain();
        BorazineChain();
        BoronNitrideProcess();
    }
    private static void BoronTrioxideChain() {

        //  Na2B4O7·10H2O + 2HCl -> 4H3BO3 + 2NaCl + 5H2O
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, Borax, 23)
                .fluidInputs(HydrochloricAcid.getFluid(2000))
                .output(dust, BoricAcid, 16)
                .output(dust, Salt, 4)
                .fluidOutputs(Water.getFluid(5000))
                .EUt(VA[IV])
                .duration(15 * SECOND)
                .buildAndRegister();

        //  2H3BO3 -> B2O3 + 3H2O
        DRYER_RECIPES.recipeBuilder()
                .input(dust, BoricAcid, 8)
                .output(dust, BoronTrioxide, 5)
                .fluidOutputs(Water.getFluid(3000))
                .EUt(VA[IV])
                .duration(5 * SECOND)
                .buildAndRegister();
    }

    private static void BorazineChain() {

        //  B2O3 + 6HF -> 2BF3 + 3H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, BoricAcid, 5)
                .fluidInputs(HydrofluoricAcid.getFluid(6000))
                .fluidOutputs(BoronTrifluoride.getFluid(2000))
                .fluidOutputs(Water.getFluid(3000))
                .EUt(VA[LuV])
                .duration(8 * SECOND)
                .buildAndRegister();

        //  Li + H -> LiH
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, Lithium)
                .fluidInputs(Hydrogen.getFluid(1000))
                .output(ingot, LithiumHydride)
                .EUt(VA[LuV])
                .duration(300)
                .blastFurnaceTemp(873)
                .buildAndRegister();

        //  8BF3 + 6LiH -> B2H6 + 6LiBF4
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, LithiumHydride, 12)
                .fluidInputs(BoronTrifluoride.getFluid(8000))
                .output(dust, LithiumTetrafluoroborate, 36)
                .fluidOutputs(Diborane.getFluid(1000))
                .EUt(VA[LuV])
                .duration(32 * SECOND)
                .buildAndRegister();

        //  LiBF4 -> LiH + BF3
        DRYER_RECIPES.recipeBuilder()
                .input(dust, LithiumTetrafluoroborate, 6)
                .output(ingot, LithiumHydride, 2)
                .fluidOutputs(BoronTrifluoride.getFluid(1000))
                .EUt(VA[EV])
                .duration(4 * SECOND)
                .buildAndRegister();

        //  3B2H6 + 6NH3 -> 2B3H6N3 + 24H
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Diborane.getFluid(3000))
                .fluidInputs(Ammonia.getFluid(6000))
                .fluidOutputs(Borazine.getFluid(2000))
                .fluidOutputs(Hydrogen.getFluid(24000))
                .EUt(VA[LuV])
                .duration(20 * SECOND)
                .buildAndRegister();

        //  B2O3 + 3C + 6Cl -> 2BCl3 + 3CO
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, BoronTrioxide, 5)
                .input(dust, Carbon, 3)
                .fluidInputs(Chlorine.getFluid(6000))
                .fluidOutputs(BoronTrichloride.getFluid(2000))
                .fluidOutputs(CarbonMonoxide.getFluid(3000))
                .EUt(VA[LuV])
                .duration(4 * SECOND + 10)
                .blastFurnaceTemp(774)
                .buildAndRegister();

        //  3BCl3 + 3NH4Cl -> B3Cl3H3N3 + 9HCl
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, AmmoniumChloride, 6)
                .fluidInputs(BoronTrichloride.getFluid(3000))
                .fluidOutputs(Trichloroborazine.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(9000))
                .EUt(VA[IV])
                .duration(20 * SECOND)
                .buildAndRegister();
    }

    private static void BoronNitrideProcess() {

        //  B3H6N3 + 3O -> 3BN + 3H2O
        CRYSTALLIZER_RECIPES.recipeBuilder()
                .fluidInputs(Borazine.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(3000))
                .output(gem, HexagonalBoronNitride, 6)
                .fluidOutputs(Water.getFluid(3000))
                .EUt(VA[LuV])
                .duration(20 * SECOND)
                .blastFurnaceTemp(1300)
                .buildAndRegister();

        //  B + N -> BN
        MOLECULAR_BEAM_RECIPES.recipeBuilder()
                .input(foil, Nickel, 8)
                .input(dust, Boron)
                .fluidInputs(Nitrogen.getFluid(1000))
                .output(gem, HexagonalBoronNitride, 2)
                .EUt(VA[LuV])
                .duration(4 * SECOND)
                .temperature(2900)
                .buildAndRegister();

        //  h-BN -> c-BN
        CRYSTALLIZER_RECIPES.recipeBuilder()
                .input(dust, HexagonalBoronNitride)
                .output(gem, CubicBoronNitride)
                .EUt(VA[LuV])
                .duration(5 * SECOND)
                .blastFurnaceTemp(3501)
                .buildAndRegister();

        //  B3Cl3H3N3 -> 3 a-BN + 3HCl
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Caesium)
                .fluidInputs(Trichloroborazine.getFluid(1000))
                .output(dust, AmorphousBoronNitride, 6)
                .fluidOutputs(HydrochloricAcid.getFluid(3000))
                .EUt(VA[LuV])
                .duration(10 * SECOND)
                .buildAndRegister();

        //  c-BN + C -> BCN
        IMPLOSION_RECIPES.recipeBuilder()
                .input(dust, CubicBoronNitride)
                .input(dust, Carbon)
                .output(gem, Heterodiamond, 2)
                .EUt(VA[EV])
                .duration(SECOND)
                .explosivesAmount(32)
                .buildAndRegister();

        AUTOCLAVE_RECIPES.recipeBuilder()
                .input(dust, CubicBoronNitride)
                .input(dust, Carbon)
                .output(gem, Heterodiamond, 2)
                .EUt(VA[EV])
                .duration(SECOND)
                .buildAndRegister();

        //  c-BN + C -> BCN
        IMPLOSION_RECIPES.recipeBuilder()
                .input(dust, CubicBoronNitride)
                .input(dust, Carbon)
                .output(gem, Heterodiamond, 2)
                .explosivesType(MetaItems.DYNAMITE.getStackForm(16))
                .EUt(VA[EV])
                .duration(SECOND)
                .buildAndRegister();

        AUTOCLAVE_RECIPES.recipeBuilder()
                .input(dust, CubicBoronNitride)
                .input(dust, Carbon)
                .output(gem, Heterodiamond, 2)
                .EUt(VA[EV])
                .duration(SECOND)
                .buildAndRegister();

        //  BCN + C -> c-BC2N
        CRYSTALLIZER_RECIPES.recipeBuilder()
                .input(dust, Heterodiamond)
                .input(dust, Carbon)
                .output(gem, CubicHeterodiamond)
                .EUt(VA[LuV])
                .duration(20 * SECOND)
                .blastFurnaceTemp(500)
                .buildAndRegister();
    }
}
