package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.unification.ore.OrePrefix;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.recipes.RecipeMaps.GAS_TURBINE_FUELS;
import static gregtech.api.unification.material.MarkerMaterials.Color.Lime;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtechfoodoption.GTFOMaterialHandler.FertilizerSolution;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Tiberium;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.loaders.recipes.chain.OilChains.lightlyCrack;
import static keqing.gtqtcore.loaders.recipes.chain.OilChains.severelyCrack;

public class AlgaeChain {
    public static void init() {
        AlgaeGroth(COMMON_ALGAE, 40);
        AlgaeGroth(GREEN_ALGAE, 60);
        AlgaeGroth(RED_ALGAE, 60);
        AlgaeGroth(BROWN_ALGAE, 80);
        AlgaeGroth(GOLD_ALGAE, 80);

        ExoticGasProcessing();

        // 初级处理 - 生物油初步分离
        SFM.recipeBuilder()
                .duration(120)
                .EUt(96)
                .fluidInputs(BioOil.getFluid(6000))
                .input(OrePrefix.dust, SodaAsh, 10)
                .fluidOutputs(CrudeBioOil.getFluid(6000)) // 初级原油
                .fluidOutputs(Naphtha.getFluid(800)) // 石脑油
                .fluidOutputs(BioDiesel.getFluid(500)) // 生物柴油
                .fluidOutputs(OrganicAcids.getFluid(200)) // 有机酸
                .fluidOutputs(AqueousPhase.getFluid(200)) // 水相
                .output(dust, BioChar, 2) // 生物炭
                .buildAndRegister();

        // 中级处理 - 原油精炼
        DISTILLATION_RECIPES.recipeBuilder()
                .duration(180)
                .EUt(192)
                .fluidInputs(CrudeBioOil.getFluid(6000))
                .fluidOutputs(BioDiesel.getFluid(1000)) // 精炼油
                .fluidOutputs(LightOrganicFraction.getFluid(2500)) // 轻质有机馏分
                .fluidOutputs(HeavyOrganicFraction.getFluid(2500)) // 重质有机馏分
                .buildAndRegister();

        lightlyCrack(HeavyOrganicFraction, LightlyHydroCrackedHeavyFuel, LightlySteamCrackedHeavyFuel);
        severelyCrack(HeavyOrganicFraction, SeverelyHydroCrackedHeavyFuel, SeverelySteamCrackedHeavyFuel);
        lightlyCrack(LightOrganicFraction, LightlyHydroCrackedLightFuel, LightlySteamCrackedLightFuel);
        severelyCrack(LightOrganicFraction, SeverelyHydroCrackedLightFuel, SeverelySteamCrackedLightFuel);

        // 水相处理 - 资源回收
        DISTILLATION_RECIPES.recipeBuilder()
                .duration(90)
                .EUt(128)
                .fluidInputs(AqueousPhase.getFluid(1000)) // 5批初级处理产物
                .input(OrePrefix.dust, Lime, 3) // 添加石灰
                .fluidOutputs(FertilizerSolution.getFluid(800)) // 肥料溶液
                .output(OrePrefix.dust, RockSalt, 2) // 钾盐
                .output(OrePrefix.dust, Phosphorus, 1) // 磷
                .buildAndRegister();

        // 精密分馏分离有机酸
        DISTILLATION_RECIPES.recipeBuilder()
                .duration(160)
                .EUt(512)
                .fluidInputs(OrganicAcids.getFluid(1000))
                .fluidOutputs(AceticAcid.getFluid(600))   // 乙酸
                .fluidOutputs(PropionicAcid.getFluid(300)) // 丙酸
                .fluidOutputs(ButyricAcid.getFluid(100))   // 丁酸
                .buildAndRegister();

        // 乙酸制醋酸乙烯酯
        DISTILLATION_RECIPES.recipeBuilder()
                .duration(180)
                .EUt(480)
                .fluidInputs(AceticAcid.getFluid(1000))
                .fluidInputs(Ethylene.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(1000))
                .input(OrePrefix.dust, Palladium, 1)    // 钯催化剂
                .fluidOutputs(VinylAcetate.getFluid(1600)) // 醋酸乙烯酯
                .fluidOutputs(Water.getFluid(400))
                .buildAndRegister();

        // 酯化反应生产食品添加剂
        DISTILLATION_RECIPES.recipeBuilder()
                .duration(150)
                .EUt(320)
                .fluidInputs(PropionicAcid.getFluid(500))
                .fluidInputs(ButyricAcid.getFluid(500))
                .fluidInputs(Ethanol.getFluid(1000))
                .input(OrePrefix.dust, SulfuricAcid, 1) // 酸催化剂
                .fluidOutputs(PropionateEster.getFluid(800)) // 丙酸乙酯(水果香精)
                .fluidOutputs(ButyrateEster.getFluid(800))   // 丁酸乙酯(菠萝香精)
                .fluidOutputs(Water.getFluid(400))
                .buildAndRegister();

    }

    private static void ExoticGasProcessing() {

        //  Algae -> Crude Exotic Gas
        PYROLYSE_RECIPES.recipeBuilder()
                .input(BARNARDA_C_BRYOPSIS_HYPNOIDES, 4)
                .fluidInputs(NaturalGas.getFluid(200))
                .circuitMeta(1)
                .fluidOutputs(CrudeExoticGas.getFluid(200))
                .EUt(VA[IV])
                .duration(10)
                .buildAndRegister();

        PYROLYSE_RECIPES.recipeBuilder()
                .input(BARNARDA_C_CHLORELLA, 4)
                .fluidInputs(NaturalGas.getFluid(200))
                .circuitMeta(1)
                .fluidOutputs(CrudeExoticGas.getFluid(200))
                .EUt(VA[IV])
                .duration(10)
                .buildAndRegister();

        PYROLYSE_RECIPES.recipeBuilder()
                .input(BARNARDA_C_ZOOXANTHELLAE, 4)
                .fluidInputs(NaturalGas.getFluid(200))
                .circuitMeta(1)
                .fluidOutputs(CrudeExoticGas.getFluid(200))
                .EUt(VA[IV])
                .duration(10)
                .buildAndRegister();

        PYROLYSE_RECIPES.recipeBuilder()
                .input(PROXIMA_B_CONCHOSPORE, 4)
                .fluidInputs(NaturalGas.getFluid(200))
                .circuitMeta(1)
                .fluidOutputs(CrudeExoticGas.getFluid(200))
                .EUt(VA[IV])
                .duration(10)
                .buildAndRegister();

        PYROLYSE_RECIPES.recipeBuilder()
                .input(PROXIMA_B_POLYSIPHONIA_SENTICULOSA, 4)
                .fluidInputs(NaturalGas.getFluid(200))
                .circuitMeta(1)
                .fluidOutputs(CrudeExoticGas.getFluid(200))
                .EUt(VA[IV])
                .duration(10)
                .buildAndRegister();

        PYROLYSE_RECIPES.recipeBuilder()
                .input(PROXIMA_B_SPIROGYRA, 4)
                .fluidInputs(NaturalGas.getFluid(200))
                .circuitMeta(1)
                .fluidOutputs(CrudeExoticGas.getFluid(200))
                .EUt(VA[IV])
                .duration(10)
                .buildAndRegister();

        PYROLYSE_RECIPES.recipeBuilder()
                .input(TAU_CETI_F_PHAEOPHYTA, 4)
                .fluidInputs(NaturalGas.getFluid(200))
                .circuitMeta(1)
                .fluidOutputs(CrudeExoticGas.getFluid(200))
                .EUt(VA[IV])
                .duration(10)
                .buildAndRegister();

        PYROLYSE_RECIPES.recipeBuilder()
                .input(TAU_CETI_F_SCENEDESMUS_OBLIQUUS, 4)
                .fluidInputs(NaturalGas.getFluid(200))
                .circuitMeta(1)
                .fluidOutputs(CrudeExoticGas.getFluid(200))
                .EUt(VA[IV])
                .duration(10)
                .buildAndRegister();

        PYROLYSE_RECIPES.recipeBuilder()
                .input(TAU_CETI_F_SPIRULINA, 4)
                .fluidInputs(NaturalGas.getFluid(200))
                .circuitMeta(1)
                .fluidOutputs(CrudeExoticGas.getFluid(200))
                .EUt(VA[IV])
                .duration(10)
                .buildAndRegister();

        //  Crude Exotic Gas -> Cracked Crude Exotic Gas
        CRACKING_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .fluidInputs(CrudeExoticGas.getFluid(1000))
                .fluidInputs(MetastableOganesson.getFluid(1000))
                .fluidOutputs(CrackedCrudeExoticGas.getFluid(2000))
                .EUt(VA[UV])
                .duration(100)
                .buildAndRegister();

        //  Cs + F -> CsF
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Caesium)
                .fluidInputs(Fluorine.getFluid(1000))
                .circuitMeta(1)
                .fluidOutputs(CaesiumFluoride.getFluid(1000))
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        //  Xe + 3O -> XeO3
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Xenon.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(3000))
                .circuitMeta(3)
                .fluidOutputs(XenonTrioxide.getFluid(1000))
                .EUt(VA[MV])
                .duration(100)
                .buildAndRegister();

        //  Rn + 3O -> RnO3
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Radon.getFluid(1000))
                .fluidInputs(Oxygen.getFluid(3000))
                .circuitMeta(3)
                .fluidOutputs(RadonTrioxide.getFluid(1000))
                .EUt(VA[HV])
                .duration(100)
                .buildAndRegister();

        //  RnO3 + 2HF -> RnF2 + H2O + O (lost)
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(RadonTrioxide.getFluid(1000))
                .fluidInputs(HydrofluoricAcid.getFluid(2000))
                .fluidOutputs(RadonDifluoride.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VA[EV])
                .duration(50)
                .buildAndRegister();

        //  *Nq* dust + RnF2 + 6HF -> Rn*Nq*F8 + 6H
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Naquadria)
                .fluidInputs(RadonDifluoride.getFluid(1000))
                .fluidInputs(HydrofluoricAcid.getFluid(6000))
                .fluidOutputs(RadonNaquadriaOctafluoride.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(6000))
                .EUt(VA[UV])
                .duration(20)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  CsF + XeO3 -> CsXeO3F
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(CaesiumFluoride.getFluid(1000))
                .fluidInputs(XenonTrioxide.getFluid(1000))
                .fluidOutputs(CaesiumXenontrioxideFluoride.getFluid(1000))
                .EUt(VA[MV])
                .duration(480)
                .buildAndRegister();

        //  Rn*Nq*F8 + CsXeO3F -> *Nq*CsXeF9 + RnO3
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(RadonNaquadriaOctafluoride.getFluid(1000))
                .fluidInputs(CaesiumXenontrioxideFluoride.getFluid(1000))
                .fluidOutputs(NaquadriaCaesiumXenonnonfluoride.getFluid(1000))
                .fluidOutputs(RadonTrioxide.getFluid(1000))
                .EUt(VA[IV])
                .duration(800)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Cracked Crude Exotic Gas + *Nq*CsXeF9 -> Naquadic Exotic Gas
        BURNER_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(CrackedCrudeExoticGas.getFluid(8000))
                .fluidInputs(NaquadriaCaesiumXenonnonfluoride.getFluid(1000))
                .fluidOutputs(NaquadicExoticGas.getFluid(4000))
                .fluidOutputs(NaquadriaCaesiumfluoride.getFluid(1000))
                .EUt(VA[UHV])
                .blastFurnaceTemp(8000)
                .duration(200)
                .buildAndRegister();

        //  Naquadic Exotic Gas process
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(NaquadicExoticGas.getFluid(4000))
                .output(dust, Tiberium)
                .fluidOutputs(SuperheavyExoticGas.getFluid(200))
                .fluidOutputs(HeavyExoticGas.getFluid(400))
                .fluidOutputs(MediumExoticGas.getFluid(1400))
                .fluidOutputs(LightExoticGas.getFluid(2000))
                .EUt(VA[UHV])
                .duration(200)
                .buildAndRegister();

        //  *Nq*F2CsF -> *Nq* (cycle) + Cs (cycle) + 3F
        DRYER_RECIPES.recipeBuilder()
                .fluidInputs(NaquadriaCaesiumfluoride.getFluid(1000))
                .output(dust, Naquadria)
                .output(dust, Caesium)
                .fluidOutputs(Fluorine.getFluid(3000))
                .EUt(VA[ZPM])
                .duration(40)
                .buildAndRegister();

        //  Exotic Gas
        GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(CrudeExoticGas.getFluid(1))
                //.fluidOutputs(HighTemperatureGas.getFluid(240*50))
                .EUt(V[EV])
                .duration(240)
                .buildAndRegister();

        GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(SuperheavyExoticGas.getFluid(1))
                //.fluidOutputs(HighTemperatureGas.getFluid(480*50))
                .EUt(V[EV])
                .duration(480)
                .buildAndRegister();

        GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(HeavyExoticGas.getFluid(2))
                //.fluidOutputs(HighTemperatureGas.getFluid(360*50))
                .EUt(V[EV])
                .duration(360)
                .buildAndRegister();

        GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(MediumExoticGas.getFluid(4))
                //.fluidOutputs(HighTemperatureGas.getFluid(180*50))
                .EUt(V[EV])
                .duration(180)
                .buildAndRegister();

        GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(LightExoticGas.getFluid(8))
                //.fluidOutputs(HighTemperatureGas.getFluid(90*50))
                .EUt(V[EV])
                .duration(90)
                .buildAndRegister();

        GAS_TURBINE_FUELS.recipeBuilder()
                .fluidInputs(Hydrogen.getFluid(12))
                //.fluidOutputs(HighTemperatureGas.getFluid(50))
                .EUt(V[LV])
                .duration(1)
                .buildAndRegister();
    }

    private static void AlgaeGroth(MetaItem.MetaValueItem items, int rate) {
        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .fluidInputs(SterileGrowthMedium.getFluid(100))
                .notConsumable(items)
                .chancedOutput(items, 4000, 500)
                .duration(400).EUt(VA[HV]).buildAndRegister();

        FERMENTATION_TANK_RECIPES.recipeBuilder()
                .fluidInputs(Biomass.getFluid(1000))
                .input(items, 1)
                .fluidOutputs(BioOil.getFluid(2000))
                .pH(8.6)
                .duration(1600).EUt(480).buildAndRegister();

        FLUIDIZED_BED.recipeBuilder()
                .input(items, 16)
                .fluidInputs(Steam.getFluid(2000))
                .fluidOutputs(BioOil.getFluid(4000))
                .recipeLevel(3)
                .Catalyst(CATALYST_ZR.getStackForm())
                .EUt(180)
                .duration(120)
                .buildAndRegister();
    }
}
