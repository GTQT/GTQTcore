package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.*;

public class StrontiumRadiumChain {
    public static void init() {

        //  Pyrochlore + Sulfuric Acid -> Acidic Pyrochlore + Thorium-Uranium Solution
        BLAST_RECIPES.recipeBuilder()
                .input(dust, Pyrochlore, 6)
                .fluidInputs(SulfuricAcid.getFluid(2000))
                .output(dust, AcidicPyrochlore, 6)
                .fluidOutputs(ThoriumUraniumSolution.getFluid(1000))
                .EUt(VA[HV])
                .duration(380)
                .blastFurnaceTemp(2700)
                .buildAndRegister();

        //  Acidic Pyrochlore + Sulfuric Acid + Hydrogen Peroxide -> Leaching Pyrochlore + Hydrofluoric Acid + Ba-Sr-Ra Solution + Oxygen
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, AcidicPyrochlore, 3)
                .fluidInputs(SulfuricAcid.getFluid(12000))
                .fluidInputs(HydrogenPeroxide.getFluid(3000))
                .output(dust, LeachingPyrochlore, 3)
                .fluidOutputs(HydrofluoricAcid.getFluid(13000))
                .fluidOutputs(BariumStrontiumRadiumSolution.getFluid(8000))
                .fluidOutputs(Oxygen.getFluid(3000))
                .EUt(VA[IV])
                .duration(240)
                .buildAndRegister();

        //  Ba-Sr-Ra Solution Centrifuge
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(BariumStrontiumRadiumSolution.getFluid(4000))
                .output(dust, Barite, 3)
                .output(dust, Gypsum, 8)
                .output(dust, Celestite, 6)
                .output(dustSmall, Radium, 2)
                .fluidOutputs(Water.getFluid(4000))
                .EUt(VA[EV])
                .duration(250)
                .buildAndRegister();

        //  Leaching Pyrochlore + Hydrofluoric Acid -> Fluoroniobic acid + Water
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, LeachingPyrochlore)
                .fluidInputs(HydrofluoricAcid.getFluid(10400))
                .fluidOutputs(FluoroniobicAcid.getFluid(2000))
                .fluidOutputs(Water.getFluid(3200))
                .EUt(VA[MV])
                .duration(180)
                .buildAndRegister();

        //  Tributyl Phosphate
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Butanol.getFluid(3000))
                .fluidInputs(PhosphorylChloride.getFluid(1000))
                .fluidOutputs(TributylPhosphate.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(3000))
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        //  Mesityl Oxide
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(dust, CalciumHydroxide)
                .fluidInputs(Acetone.getFluid(2000))
                .fluidOutputs(MesitylOxide.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VA[MV])
                .duration(120)
                .buildAndRegister();

        //  Methyl Isobutyl Ketone
        CHEMICAL_PLANT.recipeBuilder()
                .notConsumable(dust, PalladiumOnCarbon)
                .input(dust, Carbon)
                .fluidInputs(MesitylOxide.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(MethylIsobutylKetone.getFluid(1000))
                .fluidOutputs(CarbonMonoxide.getFluid(1000))
                .EUt(VA[HV])
                .duration(140)
                .recipeLevel(3)
                .buildAndRegister();

        //  Tributyl Phosphate + Methyl Isobutyl Ketone -> TBP:MIBK Solution
        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(TributylPhosphate.getFluid(1000))
                .fluidInputs(MethylIsobutylKetone.getFluid(1000))
                .fluidOutputs(TBPMIBKSolution.getFluid(2000))
                .EUt(VA[LV])
                .duration(220)
                .buildAndRegister();

        //  Fluoroniobic acid + TBP:MIBK Solution -> Oxypentafluoroniobate + Heptafluorotantalate
        CHEMICAL_PLANT.recipeBuilder()
                .fluidInputs(FluoroniobicAcid.getFluid(10000))
                .fluidInputs(TBPMIBKSolution.getFluid(1000))
                .fluidOutputs(Oxypentafluoroniobate.getFluid(9000))
                .fluidOutputs(Heptafluorotantalate.getFluid(1000))
                .EUt(VA[IV])
                .duration(130)
                .recipeLevel(5)
                .buildAndRegister();

        //  Potassium Fluoride
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Potassium)
                .fluidInputs(Fluorine.getFluid(1000))
                .circuitMeta(1)
                .output(dust, PotassiumFluoride)
                .EUt(VA[LV])
                .duration(60)
                .buildAndRegister();

        //  Oxypentafluoroniobate -> Postassium Fluoniobate
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, PotassiumFluoride, 4)
                .fluidInputs(Oxypentafluoroniobate.getFluid(1000))
                .output(dust, PotassiumFluoniobate, 10)
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VA[MV])
                .duration(170)
                .buildAndRegister();

        //  Potassium Fluoniobate Electrolyzer (Potassium Fluoride cycle)
        ELECTROLYZER_RECIPES.recipeBuilder()
                .input(dust, PotassiumFluoniobate, 10)
                .fluidInputs(Sodium.getFluid(720))
                .output(dust, SodiumFluoride, 10)
                .output(dust, PotassiumFluoride, 4)
                .output(dust, Niobium)
                .EUt(VA[HV])
                .duration(120)
                .buildAndRegister();

        //  Heptafluorotantalate -> Potassium Fluotantalate
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Heptafluorotantalate.getFluid(1000))
                .input(dust, PotassiumHydroxide, 2)
                .output(dust, PotassiumFluotantalate, 10)
                .fluidOutputs(Water.getFluid(2000))
                .EUt(VA[MV])
                .duration(170)
                .buildAndRegister();

        //  Potassium Fluotantalate Electrolyzer
        ELECTROLYZER_RECIPES.recipeBuilder()
                .input(dust, PotassiumFluotantalate, 10)
                .fluidInputs(Sodium.getFluid(720))
                .output(dust, SodiumFluoride, 10)
                .output(dust, PotassiumFluoride, 4)
                .output(dust, Tantalum)
                .EUt(VA[MV])
                .duration(170)
                .buildAndRegister();

        //  Tantalum Oxide cycle
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, PotassiumFluotantalate, 20)
                .fluidInputs(Water.getFluid(9000))
                .output(dust, TantalumPentoxide, 7)
                .fluidOutputs(HydrofluoricAcid.getFluid(14000))
                .output(dust, PotassiumHydroxide, 4)
                .EUt(VA[LV])
                .duration(20)
                .buildAndRegister();

        //  Thorium-Uranium Chain Step 1
        DISSOLUTION_TANK_RECIPES.recipeBuilder()
                .fluidInputs(ThoriumUraniumSolution.getFluid(2000))
                .fluidInputs(NitricAcid.getFluid(7000))
                .output(dust, UraniumThoriumNitrate, 26)
                .fluidOutputs(DilutedSulfuricAcid.getFluid(4000))
                .EUt(VA[HV])
                .duration(340)
                .buildAndRegister();

        //  Step 2
        BLAST_RECIPES.recipeBuilder()
                .input(dust, UraniumThoriumNitrate, 26)
                .fluidInputs(Hydrogen.getFluid(2000))
                .output(dust, UraniumOxideThoriumNitrate, 18)
                .fluidOutputs(NitricAcid.getFluid(2000))
                .EUt(VA[MV])
                .duration(200)
                .blastFurnaceTemp(480)
                .buildAndRegister();

        //  Step3
        MIXER_RECIPES.recipeBuilder()
                .input(dust, UraniumOxideThoriumNitrate, 18)
                .fluidInputs(DistilledWater.getFluid(1000))
                .output(dust, Uraninite, 3)
                .fluidOutputs(ThoriumNitrateSolution.getFluid(1000))
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

        //  Step4
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, Sodium)
                .fluidInputs(ThoriumNitrateSolution.getFluid(1000))
                .output(dust, ThoriumOxide, 2)
                .output(dust, SodiumNitrate, 10)
                .fluidOutputs(NitricAcid.getFluid(2000))
                .EUt(VA[EV])
                .duration(120)
                .buildAndRegister();

        //  Step5
        ROASTER_RECIPES.recipeBuilder()
                .input(dust, ThoriumOxide, 2)
                .input(dust, CalciumChloride, 3)
                .output(dust, Thorium)
                .output(dust, Quicklime, 2)
                .fluidOutputs(Chlorine.getFluid(2000))
                .EUt(VA[HV])
                .temperature(2700)
                .duration(150)
                .temperature(1000)
                .buildAndRegister();

        //  Celestite + Soda Ash + Carbon -> Strontium Carbonate + Sodium Sulfide + Carbon Dioxide
        ROASTER_RECIPES.recipeBuilder()
                .input(dust, Celestite, 6)
                .input(dust, SodaAsh, 6)
                .input(dust, Carbon, 2)
                .output(dust, StrontiumCarbonate, 5)
                .output(dust, SodiumSulfide, 3)
                .fluidOutputs(CarbonDioxide.getFluid(2000))
                .temperature(2700)
                .EUt(VA[HV])
                .duration(360)
                .buildAndRegister();

        //  Strontium Carbonate -> Strontium Oxide + Carbon Dioxide
        DIGESTER_RECIPES.recipeBuilder()
                .input(dust, StrontiumCarbonate, 5)
                .output(dust, StrontiumOxide, 2)
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .EUt(VA[EV])
                .duration(200)
                .buildAndRegister();
    }
}