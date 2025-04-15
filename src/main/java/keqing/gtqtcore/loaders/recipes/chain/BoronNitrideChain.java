package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.common.items.MetaItems;
import keqing.gtqtcore.api.unification.GTQTMaterials;

import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.utils.GTQTUniversUtil.SECOND;
import static keqing.gtqtcore.api.utils.GTQTUniversUtil.TICK;
import static supercritical.api.unification.material.SCMaterials.BoronTrioxide;

public class BoronNitrideChain {

    public static void register() {
        BoronTrioxideProcess();
        BorazineProcess();
        BoronNitrideProcess();
    }

    private static void BoronTrioxideProcess() {
        // Na2B4O7·10H2O + 2HCl -> 4H3BO3 + 2NaCl + 5H2O
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, Borax, 23)
                .fluidInputs(HydrochloricAcid.getFluid(2000))
                .output(dust, GTQTMaterials.BoricAcid, 16)
                .output(dust, Salt, 4)
                .fluidOutputs(Water.getFluid(5000))
                .EUt(VA[MV])
                .duration(15 * SECOND)
                .buildAndRegister();

        // 2H3BO3 -> B2O3 + 3H2O
        DRYER_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.BoricAcid, 8)
                .output(dust, BoronTrioxide, 5)
                .fluidOutputs(Water.getFluid(3000))
                .EUt(VA[MV])
                .duration(5 * SECOND)
                .buildAndRegister();
    }

    private static void BorazineProcess() {
        // B2O3 + 6HF -> 2BF3 + 3H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.BoricAcid, 5)
                .fluidInputs(HydrofluoricAcid.getFluid(6000))
                .fluidOutputs(GTQTMaterials.BoronTrifluoride.getFluid(2000))
                .fluidOutputs(Water.getFluid(3000))
                .EUt(VA[HV])
                .duration(8 * SECOND)
                .buildAndRegister();

        // Li + H -> LiH (873K)
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, Lithium, 1)
                .fluidInputs(Hydrogen.getFluid(1000))
                .output(ingot, GTQTMaterials.LithiumHydride, 1)
                .EUt(VA[HV])
                .duration(15 * SECOND)
                .blastFurnaceTemp(2700)
                .buildAndRegister();

        // 8BF3 + 6LiH -> B2H6 + 6LiBF4
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.LithiumHydride, 12)
                .fluidInputs(GTQTMaterials.BoronTrifluoride.getFluid(8000))
                .output(dust, GTQTMaterials.LithiumTetrafluoroborate, 36)
                .fluidOutputs(GTQTMaterials.Diborane.getFluid(1000))
                .EUt(VA[HV])
                .duration(32 * SECOND)
                .buildAndRegister();

        // LiBF4 -> BF3 + LiH (cycle)
        DRYER_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.LithiumTetrafluoroborate, 6)
                .output(ingot, GTQTMaterials.LithiumHydride, 2)
                .fluidOutputs(GTQTMaterials.BoronTrifluoride.getFluid(1000))
                .EUt(VA[LV])
                .duration(4 * SECOND)
                .buildAndRegister();

        // 3B2H6 + 6NH3 -> 2B3H6N3 + 24H
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.Diborane.getFluid(3000))
                .fluidInputs(Ammonia.getFluid(6000))
                .fluidOutputs(GTQTMaterials.Borazine.getFluid(2000))
                .fluidOutputs(Hydrogen.getFluid(24000))
                .EUt(VA[LuV])
                .duration(20 * SECOND)
                .buildAndRegister();

        // B2O3 + 3C + 6Cl -> 2BCl3 + 3CO (774K)
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, BoronTrioxide, 5)
                .input(dust, Carbon, 3)
                .fluidInputs(Chlorine.getFluid(6000))
                .fluidOutputs(GTQTMaterials.BoronTrichloride.getFluid(2000))
                .fluidOutputs(CarbonMonoxide.getFluid(3000))
                .EUt(VA[HV])
                .blastFurnaceTemp(2700)
                .duration(4 * SECOND + 10 * TICK)
                .buildAndRegister();

        // 3BCl3 + 3NH4Cl -> B3Cl3H3N3 + 9HCl
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, AmmoniumChloride, 6)
                .fluidInputs(GTQTMaterials.BoronTrichloride.getFluid(3000))
                .fluidOutputs(GTQTMaterials.Trichloroborazine.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(9000))
                .EUt(VA[IV])
                .duration(20 * SECOND)
                .buildAndRegister();
    }

    private static void BoronNitrideProcess() {
        // B3H6N3 + 3O -> 3h-BN + 3H2O (1300K)
        CVD_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.Borazine.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(3000))
                .output(gem, GTQTMaterials.HexagonalBoronNitride, 6)
                .fluidOutputs(Water.getFluid(3000))
                .EUt(VA[UV])
                .duration(20 * SECOND)
                .buildAndRegister();

        // Advanced recipe of h-BN.
        // B + N -> h-BN
        MOLECULAR_BEAM_RECIPES.recipeBuilder()
                .input(foil, Nickel, 8)
                .input(dust, Boron)
                .fluidInputs(Nitrogen.getFluid(1000))
                .output(gem, GTQTMaterials.HexagonalBoronNitride, 2)
                .EUt(VA[UEV])
                .duration(4 * SECOND)
                .temperature(2900)
                .buildAndRegister();

        // h-BN -> c-BN (3501K)
        CVD_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.HexagonalBoronNitride, 1)
                .output(gem, GTQTMaterials.CubicBoronNitride, 1)
                .EUt(VA[UV])
                .duration(5 * SECOND)
                .buildAndRegister();

        // B3Cl3H3N3 -> 3 a-BN + 3HCl
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Caesium) // as Catalyst
                .fluidInputs(GTQTMaterials.Trichloroborazine.getFluid(1000))
                .output(dust, GTQTMaterials.AmorphousBoronNitride, 6)
                .fluidOutputs(HydrochloricAcid.getFluid(3000))
                .EUt(VA[ZPM])
                .duration(10 * SECOND)
                .buildAndRegister();

        // c-BN + C -> BCN
        IMPLOSION_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.CubicBoronNitride)
                .input(dust, Carbon)
                .output(gem, GTQTMaterials.Heterodiamond, 2)
                .EUt(VA[LV])
                .duration(SECOND)
                .explosivesAmount(32)
                .buildAndRegister();

        IMPLOSION_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.CubicBoronNitride)
                .input(dust, Carbon)
                .output(gem, GTQTMaterials.Heterodiamond, 2)
                .explosivesType(MetaItems.DYNAMITE.getStackForm(16))
                .EUt(VA[LV])
                .duration(SECOND)
                .buildAndRegister();

        // BCN + C -> c-BC2N (2200K)
        CVD_RECIPES.recipeBuilder()
                .input(dust, GTQTMaterials.Heterodiamond)
                .input(dust, Carbon)
                .output(gem, GTQTMaterials.CubicHeterodiamond)
                .EUt(VA[UHV])
                .duration(20 * SECOND)
                .buildAndRegister();

        // Fix recipe of c-BN Sawblade because c-BN is gem.
        LATHE_RECIPES.recipeBuilder()
                .input(gemExquisite, GTQTMaterials.CubicBoronNitride)
                .output(toolHeadBuzzSaw, GTQTMaterials.CubicBoronNitride)
                .EUt(VHA[HV])
                .duration((int) (GTQTMaterials.CubicBoronNitride.getMass() * 4))
                .buildAndRegister();
    }
}
