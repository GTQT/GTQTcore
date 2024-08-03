package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.metatileentity.multiblock.CleanroomType;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.ingot;
import static gregtech.common.items.MetaItems.BLACKLIGHT;
import static gregtechfoodoption.GTFOMaterialHandler.AceticAnhydride;
import static gregtechfoodoption.GTFOMaterialHandler.Glyoxal;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.NitrousAcid;
import static keqing.gtqtcore.api.unification.TJMaterials.SodiumAcetate;

public class HexanitrohexaaxaisowurtzitaneChain {
    static int SECOND=20;
    public static void init() {
        Hexabenzylhexaazaisowurtzitane();
        Dibenzyltetraacetylhexaazaisowurtzitane();
        Tetraacetyldinitrosohexaazaisowurtzitane();
        CrudeHexanitrohexaaxaisowurtzitane();
        Hexanitrohexaaxaisowurtzitane();
    }

    private static void Hexabenzylhexaazaisowurtzitane() {


        //  CaCO3 + (NH2)4SO4 -> (NH4)2CO3
        ROASTER_RECIPES.recipeBuilder()
                .input(dust, Calcite, 5)
                .fluidInputs(AmmoniumSulfate.getFluid(1000))
                .output(dust, Gypsum, 6)
                .output(dust, AmmoniumCarbonate, 14) // Another (NH4)2CO3 recipe from Lu/Tm:YVO, but it is byproduct in chemical dryer recipes
                .EUt(VA[MV])
                .duration(13 * SECOND + 10)
                .temperature(598)
                .buildAndRegister();

        //  (NH4)2CO3 + 2C2H4O2 -> 2NH4CH3CO2 + CO2 + H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, AmmoniumCarbonate, 14)
                .fluidInputs(AceticAcid.getFluid(2000))
                .output(dust, AmmoniumAcetate, 24)
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VA[EV])
                .duration(10 * SECOND)
                .buildAndRegister();

        //  NH4CH3CO2 -> CH3CONH2
        DRYER_RECIPES.recipeBuilder()
                .input(dust, AmmoniumAcetate, 12)
                .output(dust, Acetamide, 9)
                .EUt(VA[MV])
                .duration(SECOND*10)
                .buildAndRegister();

        //  CH3CONH2 -> CH3CN
        DRYER_RECIPES.recipeBuilder()
                .input(dust, Acetamide, 9)
                .output(dust, Acetonitrile, 6)
                .EUt(VA[HV])
                 .duration(SECOND*10)
                .buildAndRegister();

        //  4NH3 + 6CH2O -> (CH2)6N4 + 6H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Ammonia.getFluid(4000))
                .fluidInputs(Formaldehyde.getFluid(6000))
                .output(dust, Hexamethylenetetramine, 22)
                .fluidOutputs(Water.getFluid(6000))
                .EUt(VA[HV])
                .duration(30 * SECOND)
                .buildAndRegister();

        //  C7H8 + 2Cl -> C7H7Cl + HCl
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(BLACKLIGHT)
                .fluidInputs(Toluene.getFluid(1000))
                .fluidInputs(Chlorine.getFluid(2000))
                .fluidOutputs(BenzylChloride.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(1000))
                .EUt(VA[HV])
                .duration(40 * SECOND)
                .buildAndRegister();

        //  (CH2)6N4 + C7H7Cl + 2HCl + 6H2O -> C7H9N + 3NH4Cl + 6CH2O
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Hexamethylenetetramine, 22)
                .fluidInputs(BenzylChloride.getFluid(1000))
                .fluidInputs(HydrochloricAcid.getFluid(2000))
                .fluidInputs(Water.getFluid(6000))
                .fluidOutputs(Benzylamine.getFluid(1000))
                .fluidOutputs(AmmoniumChloride.getFluid(3000))
                .fluidOutputs(Formaldehyde.getFluid(6000))
                .EUt(VA[IV])
                .duration(20 * SECOND)
                .buildAndRegister();

        //  CH3CN + 3C2H2O2 + 6C7H9N -> C48H48N6
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Acetonitrile, 6)
                .fluidInputs(Glyoxal.getFluid(3000))
                .fluidInputs(Benzylamine.getFluid(6000))
                .notConsumable(Hydrogen.getFluid(1))
                .output(dust, Hexabenzylhexaazaisowurtzitane, 102)
                .EUt(VA[IV])
                .duration(10 * SECOND)
                .buildAndRegister();
    }

    private static void Dibenzyltetraacetylhexaazaisowurtzitane() {
        SuccinicAnhydrideChain();
        SuccinimidylAcetateChain();

        //  C48H48N6 + 4C6H7NO4 + 8H -> C28H32N6O4 + 4C4H5NO2 + 4C7H8 + 4O
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Hexabenzylhexaazaisowurtzitane, 102)
                .input(dust, SuccinimidylAcetate, 72)
                .fluidInputs(Hydrogen.getFluid(8000))
                .notConsumable(Ethylbenzene.getFluid(1))
                .notConsumable(Dimethylformamide.getFluid(1))
                .notConsumable(HydrobromicAcid.getFluid(1))
                .output(dust, Dibenzyltetraacetylhexaazaisowurtzitane, 70)
                .output(dust, Succinimide, 48)
                .fluidOutputs(Toluene.getFluid(4000))
                .fluidOutputs(Oxygen.getFluid(4000))
                .EUt(VA[LuV])
                .duration(30 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
    }

    private static void SuccinicAnhydrideChain() {

        //  C4H10 + 7O -> C4H2O3 + 4H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(dust, BismuthVanadate)
                .fluidInputs(Butane.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(7000))
                .fluidOutputs(MaleicAnhydride.getFluid(1000))
                .fluidOutputs(Water.getFluid(4000))
                .EUt(VA[HV])
                .duration(20 * SECOND)
                .buildAndRegister();

        //  2C4H2O3 + H + H2O -> C4H6O4
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(dust, Rhodium)
                .fluidInputs(MaleicAnhydride.getFluid(2000))
                .fluidInputs(Hydrogen.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .output(dust, SuccinicAcid, 14)
                .EUt(VA[EV])
                .duration(20 * SECOND)
                .buildAndRegister();

        //  C4H6O4 + C4H6O3 -> (CH2CO)2O + 2C2H4O2
        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(dust, CalciumChloride)
                .input(dust, SuccinicAcid, 14)
                .fluidInputs(AceticAnhydride.getFluid(1000))
                .output(dust, SuccinicAnhydride, 11)
                .fluidOutputs(AceticAcid.getFluid(2000))
                .EUt(VA[IV])
                 .duration(SECOND*10)
                .buildAndRegister();
    }

    private static void SuccinimidylAcetateChain() {

        //  2KOH + CO2 -> K2CO3 + H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(PotassiumHydroxide.getFluid(2000))
                .fluidInputs(CarbonDioxide.getFluid(1000))
                .output(dust, PotassiumCarbonate, 6)
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VA[LV])
                .duration(SECOND * 10)
                .buildAndRegister();

        //  K2CO3 + 2SO2 + H2O -> 2KHSO3 + CO2 (cycle)
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, PotassiumCarbonate, 6)
                .fluidInputs(SulfurDioxide.getFluid(2000))
                .fluidInputs(Water.getFluid(1000))
                .output(dust, PotassiumBisulfite, 12)
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .EUt(VA[HV])
                .duration(10 * SECOND)
                .buildAndRegister();

        //  KNO3 + PbS -> KNO2 + Pd + S (lost)
        ROASTER_RECIPES.recipeBuilder()
                .input(dust, Saltpeter, 5)
                .input(dust, Galena, 2)
                .output(dust, PotassiumNitrite, 4)
                .output(ingot, Lead)
                .EUt(VA[HV])
                .duration(24 * SECOND)
                .temperature(1300)
                .buildAndRegister();

        //  KNO2 + NaCl + C2H4O2 -> KCl + HNO2 + C2H3NaO2
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .input(dust, PotassiumNitrite, 4)
                .input(dust, Salt, 2)
                .fluidInputs(AceticAcid.getFluid(1000))
                .output(dust, RockSalt, 2)
                .fluidOutputs(NitrousAcid.getFluid(1000))
                .fluidOutputs(SodiumAcetate.getFluid(1000))
                .EUt(VA[HV])
                .duration(10 * SECOND)
                .blastFurnaceTemp(570)
                .buildAndRegister();

        //  2KHSO3 + HNO2 -> K2NHS2O7 + H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, PotassiumBisulfite, 12)
                .fluidInputs(NitrousAcid.getFluid(1000))
                .output(dust, PotassiumHydroxylaminedisulfonate, 13)
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VA[IV])
                .duration(12 * SECOND)
                .buildAndRegister();

        //  2K2NHS2O7 + 4H2O -> (NH3OH)2SO4 + 2K2SO4 + H2SO4
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, PotassiumHydroxylaminedisulfonate, 26)
                .fluidInputs(Water.getFluid(4000))
                .output(dust, HydroxylammoniumSulfate, 17)
                .output(dust, PotassiumSulfate, 14)
                .fluidOutputs(SulfuricAcid.getFluid(1000))
                .EUt(VA[EV])
                .duration(20 * SECOND)
                .buildAndRegister();

        //  (NH3OH)2SO4 + CuCl2 -> 2HONH2HCl + SO3 + CuO
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, HydroxylammoniumSulfate, 17)
                .input(dust, CopperCl, 3)
                .output(dust, CupricOxide, 2)
                .fluidOutputs(HydroxylamineHydrochloride.getFluid(2000))
                .fluidOutputs(SulfurTrioxide.getFluid(1000))
                .EUt(VA[HV])
                .duration(20 * SECOND)
                .buildAndRegister();

        //  6(CH2CO)2O + 6Na + 6HONH2HCl + 6C7H8 + 40CH4O -> (CH2CO)2NOH + 6NaCl + 6H + 6H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SuccinicAnhydride, 66)
                .input(dust, Sodium, 6)
                .fluidInputs(HydroxylamineHydrochloride.getFluid(6000))
                .fluidInputs(Toluene.getFluid(6000))
                .fluidInputs(Methanol.getFluid(40000))
                .output(dust, NHydroxysuccinimide, 13)
                .output(dust, Salt, 12)
                .fluidOutputs(Hydrogen.getFluid(6000))
                .fluidOutputs(Water.getFluid(6000))
                .EUt(VA[EV])
                .duration(20 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  (CH2CO)2NOH + C4H6O3 -> C6H7NO4 + C2H4O2
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, NHydroxysuccinimide, 13)
                .notConsumable(Trimethylamine.getFluid(1))
                .notConsumable(Tetrahydrofuran.getFluid(1))
                .fluidInputs(AceticAnhydride.getFluid(1000))
                .output(dust, SuccinimidylAcetate, 18)
                .fluidOutputs(AceticAcid.getFluid(1000))
                .EUt(VA[IV])
                .duration(10 * SECOND)
                .buildAndRegister();
    }

    private static void Tetraacetyldinitrosohexaazaisowurtzitane() {

        //  C28H32N6O4 + 6NaOBF4 + 2H2O -> C14H18N8O6 + 4NO + 2C7H6O
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Dibenzyltetraacetylhexaazaisowurtzitane, 70)
                .input(dust, NitrosoniumTetrafluoroborate, 42)
                .fluidInputs(Water.getFluid(2000))
                .output(dust, Tetraacetyldinitrosohexaazaisowurtzitane, 46)
                .fluidOutputs(TetrafluoroboricAcid.getFluid(6000))
                .fluidOutputs(NitricOxide.getFluid(4000))
                .fluidOutputs(Benzaldehyde.getFluid(2000))
                .EUt(VA[IV])
                .duration(20 * SECOND)
                .buildAndRegister();

        //  TODO Benzaldehyde cycle
    }

    private static void CrudeHexanitrohexaaxaisowurtzitane() {

        //  BF3 + HF + HNO3 -> NaO2BF4 + H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(BoronTrifluoride.getFluid(1000))
                .fluidInputs(HydrofluoricAcid.getFluid(1000))
                .fluidInputs(NitricAcid.getFluid(1000))
                .circuitMeta(1)
                .output(dust, NitroniumTetrafluoroborate, 8)
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VA[EV])
                .duration(5 * SECOND)
                .buildAndRegister();

        //  2BF3 + 2HF + 2N2O4 -> NaOBF4 + HNO3
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(BoronTrifluoride.getFluid(2000))
                .fluidInputs(HydrofluoricAcid.getFluid(2000))
                .fluidInputs(DinitrogenTetroxide.getFluid(2000))
                .circuitMeta(2)
                .output(dust, NitrosoniumTetrafluoroborate, 7)
                .fluidOutputs(NitricAcid.getFluid(1000))
                .EUt(VA[EV])
                .duration(5 * SECOND)
                .buildAndRegister();

        //  C14H18N8O6 + 6NaO2BF4 + 4H2O -> C6H6N12O12 + 2NaOBF4 + 4HBF4 + 4C2H4O2
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Tetraacetyldinitrosohexaazaisowurtzitane, 46)
                .input(dust, NitroniumTetrafluoroborate, 48)
                .fluidInputs(Water.getFluid(4000))
                .output(dust, CrudeHexanitrohexaaxaisowurtzitane, 36)
                .output(dust, NitrosoniumTetrafluoroborate, 14)
                .fluidOutputs(TetrafluoroboricAcid.getFluid(4000))
                .fluidOutputs(AceticAcid.getFluid(4000))
                .EUt(VA[IV])
                .duration(200 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  HBF4 + 3H2O -> 4HF + H3BO3 (cycle)
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(TetrafluoroboricAcid.getFluid(1000))
                .fluidInputs(Water.getFluid(3000))
                .fluidOutputs(HydrofluoricAcid.getFluid(4000))
                .fluidOutputs(BoricAcid.getFluid(1000))
                .EUt(VA[MV])
                .duration(10 * SECOND)
                .buildAndRegister();
    }

    private static void Hexanitrohexaaxaisowurtzitane() {

        //  SiO2 + NaOH + HCl + H2O -> (SiO2)(HCl)(NaOH)(H2O)
        MIXER_RECIPES.recipeBuilder()
                .input(dust, SiliconDioxide, 3)
                .input(dust, SodiumHydroxide, 3)
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .fluidInputs(Steam.getFluid(1000))
                .fluidOutputs(SilicaGelBase.getFluid(1000))
                .EUt(VA[MV])
                .duration(20 * SECOND)
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(SilicaGelBase.getFluid(1000))
                .output(dust, SiliconDioxide, 3)
                .output(dust, SodiumHydroxide, 3)
                .fluidOutputs(HydrochloricAcid.getFluid(1000))
                .fluidOutputs(Steam.getFluid(1000))
                .EUt(VA[LV])
                .duration(20 * SECOND)
                .buildAndRegister();

        //  (SiO2)(HCl)(NaOH)(H2O) -> SiO2 + NaCl
        DRYER_RECIPES.recipeBuilder()
                .fluidInputs(SilicaGelBase.getFluid(1000))
                .output(dust, SilicaGel, 3)
                .output(dust, Salt, 2)
                .EUt(VA[HV])
                .duration(20 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  C6H6N12O12 + C2H4(NH2)2 + SiO2 -> C6H6N12O12
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, CrudeHexanitrohexaaxaisowurtzitane, 36)
                .input(dust, SilicaGel, 3)
                .fluidInputs(Ethylenediamine.getFluid(1000))
                .output(dust, Hexanitrohexaaxaisowurtzitane, 36)
                .EUt(VA[IV])
                .duration(SECOND*10)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
    }
}
