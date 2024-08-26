package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.ZPM;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.recipes.RecipeMaps.LARGE_CHEMICAL_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.gem;
import static gregtechfoodoption.GTFOMaterialHandler.HydrogenCyanide;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class NdYAGChain {
    public static void init() {
        CarbamideChain();
        NdYOChain();
        NdYAGProcess();
    }
    static int SECOND=20;
    static int MINUTE=1200;
    private static void CarbamideChain() {

        //  MnO2 + 2KOH + O -> K2MnO4 + H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Pyrolusite, 3)
                .fluidInputs(PotassiumHydroxide.getFluid(2000))
                .fluidInputs(Oxygen.getFluid(1000))
                .output(dust, PotassiumManganate, 7)
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VHA[MV] / 2)
                .duration(8 * SECOND + 10)
                .buildAndRegister();

        //  3K2MnO4 + 2H2O -> 2KMnO4 + MnO2 (cycle) + 4KOH (cycle)
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, PotassiumManganate, 21)
                .fluidInputs(Water.getFluid(2000))
                .output(dust, PotassiumPermanganate, 12)
                .output(dust, Pyrolusite, 3)
                .fluidOutputs(PotassiumHydroxide.getFluid(4000))
                .EUt(VA[MV])
                .duration(12 * SECOND + 10)
                .blastFurnaceTemp(7200)
                .buildAndRegister();

        //  2KMnO4 + 5NH3 + 5HCN + 3H2SO4 -> 2MnSO4 + K2SO4 + 5NH4CNO + 3H2O
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, PotassiumPermanganate, 12)
                .fluidInputs(Ammonia.getFluid(5000))
                .fluidInputs(HydrogenCyanide.getFluid(5000))
                .fluidInputs(SulfuricAcid.getFluid(3000))
                .output(dust, ManganeseSulfate, 12)
                .output(dust, PotassiumSulfate, 7)
                .fluidOutputs(AmmoniumCyanate.getFluid(5000))
                .fluidOutputs(Water.getFluid(3000))
                .EUt(VA[EV])
                .duration(40 * SECOND)
                .buildAndRegister();

        //  NH4CNO -> CH4N2O
        DRYER_RECIPES.recipeBuilder()
                .fluidInputs(AmmoniumCyanate.getFluid(1000))
                .output(dust, Carbamide, 8)
                .EUt(VA[HV])
                .duration(16 * SECOND)
                .buildAndRegister();
    }

    private static void NdYOChain() {

        //  YO + NdO -> Nd:YO?
        ROASTER_RECIPES.recipeBuilder()
                .input(dust, YttriumOxide, 45)
                .input(dust, NeodymiumOxide, 5)
                .output(dust, NeodymiumDopedYttriumOxide, 5)
                .EUt(VA[IV])
                .duration(11 * SECOND)
                .temperature(1880)
                .buildAndRegister();
    }

    private static void NdYAGProcess() {

        //  3C4H10O + NH3 -> (C4H9)3N + 3H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(dust, Zeolite)
                .fluidInputs(Butanol.getFluid(3000))
                .fluidInputs(Ammonia.getFluid(1000))
                .fluidOutputs(Tributylamine.getFluid(1000))
                .fluidOutputs(Water.getFluid(3000))
                .EUt(VA[HV])
                .duration(7 * SECOND)
                .buildAndRegister();

        //  Al2O3 + HNO3 -> 2Al(NO3)3 + 3H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Alumina, 5)
                .fluidInputs(NitricAcid.getFluid(6000))
                .output(dust, AluminiumNitrate, 26)
                .fluidOutputs(Water.getFluid(3000))
                .EUt(VA[LV])
                .duration(9 * SECOND + 10)
                .buildAndRegister();

        //  2Al(NO3)3 + CH2Cl2 + 2(C4H9)3N -> (Al2O3)(CH2Cl2)(C12H27N)2 + 2HNO3 (cycle) + NO2
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, AluminiumNitrate, 26)
                .fluidInputs(Dichloromethane.getFluid(1000))
                .fluidInputs(Tributylamine.getFluid(2000))
                .fluidInputs(HydrogenPeroxide.getFluid(1000))
                .fluidOutputs(AluminaSolution.getFluid(1000))
                .fluidOutputs(NitricAcid.getFluid(2000))
                .fluidOutputs(NitrogenDioxide.getFluid(1000))
                .EUt(VA[HV])
                .duration(14 * SECOND)
                .buildAndRegister();

        //  CH4N2O + Nd:YO? + (Al2O3)(CH2Cl2)(C12H27N)2 -> 2Nd:YAG? + 2(C4H9)3N (cycle)
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Carbamide, 8)
                .input(dust, NeodymiumDopedYttriumOxide)
                .fluidInputs(AluminaSolution.getFluid(1000))
                .fluidOutputs(UnprocessedNdYAGSolution.getFluid(2000))
                .fluidOutputs(Tributylamine.getFluid(2000))
                .EUt(VA[IV])
                .duration(16 * SECOND)
                .buildAndRegister();

        //  Nd:YAG? -> Nd:YAG + CH2Cl2 (cycle)
        CVD_RECIPES.recipeBuilder()
                .fluidInputs(UnprocessedNdYAGSolution.getFluid(1000))
                .output(gem, NdYAG)
                .fluidOutputs(Dichloromethane.getFluid(1000))
                .EUt(VA[ZPM])
                .duration(27 * SECOND + 10)
                .buildAndRegister();
    }
}
