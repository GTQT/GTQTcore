package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.metatileentity.multiblock.CleanroomType;

import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.seedCrystal;
import static keqing.gtqtcore.common.items.GTQTMetaItems.CARBON_ALLOTROPE_MIXTURE;
import static keqing.gtqtcore.common.items.GTQTMetaItems.GRAPHENE_ALIGNED_CNT;


public class NanotubesChain {

    public static void init() {
        CarbonNanotubeChain();
        LanthanumFullereneNanotubeChain();
        MagnetoResonaticChain();
    }

    private static void MagnetoResonaticChain() {
        //  2.5 SiO2 + Fe -> (SiO2)5Fe
        AUTOCLAVE_RECIPES.recipeBuilder()
                .input(dust, SiliconDioxide, 5)
                .input(dust, Iron)
                .fluidInputs(DistilledWater.getFluid(L))
                .output(seedCrystal, Prasiolite)
                .duration(400)
                .EUt(VA[MV])
                .buildAndRegister();

        //  3Te + 2Bi -> Be2Te3
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Tellurium, 3)
                .input(dust, Bismuth, 2)
                .notConsumable(SulfuricAcid.getFluid(1))
                .output(dust, BismuthTellurite, 5)
                .EUt(VA[MV])
                .duration(160)
                .buildAndRegister();

        //  Magneto Resonatic
        MIXER_RECIPES.recipeBuilder()
                .input(dust, BismuthTellurite, 4)
                .input(dust, Prasiolite, 3)
                .input(dust, CubicZirconia, 1)
                .input(dust, SteelMagnetic, 1)
                .output(dust, MagnetoResonatic, 9)
                .EUt(VA[MV])
                .duration(80)
                .buildAndRegister();
    }

    private static void CarbonNanotubeChain() {

        CPPChain();

        //  3C2H2 + 7C6H4 + 10N -> C48 + 10NH3
        CVD_RECIPES.recipeBuilder()
                .notConsumable(plate, Rhenium)
                .fluidInputs(Acetylene.getFluid(3000))
                .fluidInputs(Cycloparaphenylene.getFluid(7000))
                .fluidInputs(Nitrogen.getPlasma(10000))
                .output(ingot, CarbonNanotube)
                .fluidOutputs(Ammonia.getFluid(10000))
                .duration(100)
                .EUt(VA[IV])
                .buildAndRegister();

        //  Decomposition
        ELECTROLYZER_RECIPES.recipeBuilder()
                .input(dust, CarbonNanotube)
                .output(dust, Carbon, 48)
                .duration((int) Carbon.getMass() * 48)
                .EUt(64)
                .buildAndRegister();

        EXTRUDER_RECIPES.recipeBuilder()
                .input(dust, Fullerene)
                .notConsumable(SHAPE_EXTRUDER_INGOT)
                .output(ingot, Fullerene)
                .duration(400)
                .EUt(240)
                .buildAndRegister();

        //  CPP cycle

        //  6C8 + C48 -> C96
        FORMING_PRESS_RECIPES.recipeBuilder()
                .input(foil, Graphene, 24)
                .input(dust, CarbonNanotube)
                .output(CARBON_ALLOTROPE_MIXTURE)
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(100).EUt(VA[IV]).buildAndRegister();

        //  C96 -> C48 + (C6H4)7(C12)
        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder()
                .input(CARBON_ALLOTROPE_MIXTURE)
                .output(ingot, CarbonNanotube)
                .output(GRAPHENE_ALIGNED_CNT, 4)
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(200)
                .EUt(VA[UV])
                .buildAndRegister();

        //  (C6H4)7(C12) -> 7C6H4 + 12C
        EXTRACTOR_RECIPES.recipeBuilder()
                .input(GRAPHENE_ALIGNED_CNT)
                .output(dust, Carbon, 3)
                .fluidOutputs(Cycloparaphenylene.getFluid(1750))
                .duration(50)
                .EUt(VA[HV])
                .buildAndRegister();
    }

    private static void CPPChain() {

        //  C8H12Cl2Pt + C12H8I2 + AgBF4 + (CH3)3SnCl -> CPP + BF3 + C8H16 + 4HF + 3PtCl2 + 4AgCl + 4Sn (lost)
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Dichlorocyclooctadieneplatinium, 23)
                .input(dust, Diiodobiphenyl, 4)
                .fluidInputs(SilverTetrafluoroborate.getFluid(40000))
                .fluidInputs(TrimethyltinChloride.getFluid(40000))
                .output(dust, PlatinumRaw, 6)
                .output(dust, Iodine, 8)
                .output(dust, SilverChloride, 8)
                .fluidOutputs(Cycloparaphenylene.getFluid(10000))
                .fluidOutputs(BoronTrifluoride.getFluid(4000))
                .fluidOutputs(Octene.getFluid(3000))
                .fluidOutputs(HydrofluoricAcid.getFluid(4000))
                .EUt(VA[IV])
                .duration(200)
                .buildAndRegister();

        DichlorocyclooctadieneplatiniumChain();
        DiiodobiphenylChain();
        SilverTetrafluoroborateChain();
        TrimethyltinChlorideChain();
    }

    private static void DichlorocyclooctadieneplatiniumChain() {

        //  2C4H6 -> C8H12
        PYROLYSE_RECIPES.recipeBuilder()
                .notConsumable(dust, Nickel)
                .fluidInputs(Butadiene.getFluid(2000))
                .fluidOutputs(Cyclooctadiene.getFluid(1000))
                .EUt(VA[HV])
                .duration(80)
                .buildAndRegister();

        //  K2 + H2PtCl6 -> K2PtCl4 + 2HCl
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Potassium, 2)
                .fluidInputs(HexachloroplatinicAcid.getFluid(1000))
                .output(dust, PotassiumTetrachloroplatinate, 7)
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .EUt(VA[HV])
                .duration(300)
                .buildAndRegister();

        //  K2PtCl4 + C8H12 -> C8H12Cl2Pt + 2KCl
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, PotassiumTetrachloroplatinate, 7)
                .fluidInputs(Cyclooctadiene.getFluid(1000))
                .output(dust, Dichlorocyclooctadieneplatinium, 23)
                .output(dust, RockSalt, 4)
                .EUt(VA[HV])
                .duration(360)
                .buildAndRegister();


    }

    private static void DiiodobiphenylChain() {

        //  NH4NO3 + 2SO2 + 2NH3 + 3H2O -> 2(NH3OH)2(NH4)2(SO4)2
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(AmmoniumNitrate.getFluid(1000))
                .fluidInputs(SulfurDioxide.getFluid(2000))
                .fluidInputs(Ammonia.getFluid(2000))
                .fluidInputs(Water.getFluid(3000))
                .circuitMeta(22)
                .fluidOutputs(HydroxylamineDisulfate.getFluid(2000))
                .EUt(VA[HV])
                .duration(360)
                .buildAndRegister();

        //  2(NH3OH)2(NH4)2(SO4)2 + 2NH3 -> 2H3NO + 2(NH2)4SO4
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(HydroxylamineDisulfate.getFluid(2000))
                .fluidInputs(Ammonia.getFluid(2000))
                .circuitMeta(2)
                .fluidOutputs(Hydroxylamine.getFluid(2000))
                .fluidOutputs(AmmoniumSulfate.getFluid(2000))
                .EUt(VA[MV])
                .duration(180)
                .buildAndRegister();

        //  (NH2)4SO4 + H2SO4 -> (NH4)2S2O8 + 2H
        //  Just another recipe of (NH2)4SO4
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(AmmoniumSulfate.getFluid(1000))
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .circuitMeta(1)
                .fluidOutputs(AmmoniumPersulfate.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(2000))
                .EUt(VA[MV])
                .duration(220)
                .buildAndRegister();

        //  (C6H5)2 + 2I + (NH2)4SO4 + H2SO4 -> C12H8I2 + (NH4)2S2O8 + 4H
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Biphenyl, 22)
                .input(dust, Iodine, 2)
                .fluidInputs(AmmoniumSulfate.getFluid(1000))
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .output(dust, Diiodobiphenyl, 22)
                .fluidOutputs(AmmoniumPersulfate.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(4000))
                .EUt(VA[HV])
                .duration(260)
                .buildAndRegister();
    }

    private static void SilverTetrafluoroborateChain() {

        //  Ag + Cl -> AgCl
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Silver)
                .fluidInputs(Chlorine.getFluid(1000))
                .circuitMeta(1)
                .output(dust, SilverChloride, 2)
                .EUt(VA[MV])
                .duration(60)
                .buildAndRegister();

        //  2AgCl + H2O -> Ag2O + 2HCl
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SilverChloride, 4)
                .notConsumable(dust, SodiumHydroxide)
                .fluidInputs(Water.getFluid(1000))
                .output(dust, SilverOxide, 3)
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .EUt(VA[HV])
                .duration(180)
                .buildAndRegister();

        //  3Ag2O + 2BF3 -> B2O3 + AgBF4
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SilverOxide, 9)
                .fluidInputs(BoronTrifluoride.getFluid(8000))
                .notConsumable(Benzene.getFluid(1))
                .output(dust, BoronTrioxide, 5)
                .fluidOutputs(SilverTetrafluoroborate.getFluid(6000))
                .EUt(7680)
                .duration(650)
                .buildAndRegister();
    }

    private static void TrimethyltinChlorideChain() {

        //  Sn + 2Cl -> SnCl2
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Tin)
                .fluidInputs(Chlorine.getFluid(2000))
                .circuitMeta(2)
                .output(dust, TinChloride, 3)
                .EUt(VA[LV])
                .duration(60)
                .buildAndRegister();

        //  SnCl + CH4 + O -> (CH3)3SnCl + 2HCl
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, TinChloride, 3)
                .notConsumable(dust, Magnesium)
                .notConsumable(dust, Iodine)
                .fluidInputs(Methane.getFluid(3000))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidOutputs(TrimethyltinChloride.getFluid(1000))
                .fluidOutputs(HydrochloricAcid.getFluid(2000))
                .EUt(1920)
                .duration(260)
                .buildAndRegister();
    }

    private static void LanthanumFullereneNanotubeChain() {

        //  Lanthanum-Fullerene Mixture
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Lanthanum, 2)
                .input(dust, GeodesicPolyarene, 2)
                .output(dust, LanthanumFullereneMixture, 4)
                .EUt(VA[LuV])
                .duration(40)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Lanthanum-Fullerene Mixture + Nitrogen -> Lanthanum Embedded Fullerene
        PLASMA_CVD_RECIPES.recipeBuilder()
                .notConsumable(plate, MagnetoResonatic)
                .input(dust, LanthanumFullereneMixture, 4)
                .fluidInputs(Nitrogen.getFluid(20000))
                .output(dust, LanthanumEmbeddedFullerene, 4)
                .fluidOutputs(Ammonia.getFluid(20000))
                .EUt(VA[UHV])
                .duration(320)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Lanthanum Embedded Fullerene -> Lanthanum Fullerene Nanotube
        CVD_RECIPES.recipeBuilder()
                .notConsumable(plate, Rhenium)
                .input(dust, LanthanumEmbeddedFullerene)
                .fluidInputs(Acetylene.getFluid(3000))
                .fluidInputs(Cycloparaphenylene.getFluid(7000))
                .fluidInputs(Nitrogen.getPlasma(10000))
                .output(ingot, LanthanumFullereneNanotube)
                .fluidOutputs(Ammonia.getFluid(10000))
                .duration(1200)
                .EUt(VA[UEV])
                .buildAndRegister();
    }
}