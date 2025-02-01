package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.RecipeMaps;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import static supercritical.api.unification.material.SCMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.utils.GTQTUniversUtil.SECOND;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class NaquadahFuelChain {
    public static void init() {
        NaquadahFuel();
        //FuelRefineChain();
        HyperReactorFuel();
        CommonFuel();
        ThoriumBasedLiquidFuelChain();
        UraniumBasedLiquidFuelChain();
        PlutoniumBasedLiquidFuelChain();
        FuelRefineChainOT();
        FuelRefineChainNQ();
    }
private static void ThoriumBasedLiquidFuelChain() {

        //  12Th + U-235 + 3C -> Th12UC3
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Thorium, 12)
                .input(dust, Uranium235)
                .input(dust, Carbon, 3)
                .circuitMeta(4)
                .output(dust, UraniumThoriumCarbides, 16)
                .EUt(VA[HV])
                .duration(10 * SECOND)
                .buildAndRegister();

        //  High Density Thorium
        IMPLOSION_RECIPES.recipeBuilder()
                .input(dust, UraniumThoriumCarbides, 64)
                .input(foil, TungstenSteel, 16)
                .output(HIGH_DENSITY_THORIUM)
                .EUt(VA[IV])
                .duration(5 * SECOND)
                .explosivesAmount(32)
                .buildAndRegister();

        //  High Density Thorium + 4Li + 2Ba + Hg -> Th64Li4Ba2Hg
        MIXER_RECIPES.recipeBuilder()
                .input(HIGH_DENSITY_THORIUM)
                .input(dust, Lithium, 4)
                .input(dust, Barium, 2)
                .circuitMeta(4)
                .fluidInputs(Mercury.getFluid(1000))
                .fluidOutputs(ThoriumBasedLiquidFuel.getFluid(1000))
                .EUt(VA[IV])
                .duration(20 * SECOND)
                .buildAndRegister();

        //  Th64Li4Ba2Hg Combustion Generator Recipe
        COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(ThoriumBasedLiquidFuel.getFluid(10))
                .EUt(VA[IV])
                .duration((int) (2.5 * SECOND))
                .buildAndRegister();

        //  Th64Li4Ba2Hg -> *Th64Li4Ba2Hg*
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(ThoriumBasedLiquidFuel.getFluid(1000))
                .fluidInputs(Helium.getPlasma(250))
                .fluidOutputs(ThoriumFluidFuelExcited.getFluid(L))
                .EUt(VA[IV])
                .duration(SECOND)
                .EUToStart(120000000L) // MK1
                .buildAndRegister();

        //  *Th64Li4Ba2Hg* Naquadah Reactor Recipe
        //  back to Taranium Processing class.
    }

    private static void UraniumBasedLiquidFuelChain() {

        //  3C + U-238 -> C3U
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Graphite, 3)
                .input(dust, Uranium238)
                .circuitMeta(2)
                .output(dust, GraphiteUraniumMixture, 4)
                .EUt(VA[MV])
                .duration(5 * SECOND)
                .buildAndRegister();

        //  High Density Uranium
        IMPLOSION_RECIPES.recipeBuilder()
                .input(dust, GraphiteUraniumMixture, 36)
                .input(foil, TungstenCarbide, 16)
                .output(HIGH_DENSITY_URANIUM)
                .EUt(VA[LuV])
                .duration(10 * SECOND)
                .explosivesAmount(32)
                .buildAndRegister();

        //  High Density Uranium + 8K + 4Nq + Rn -> U36K8Nq4Rn
        MIXER_RECIPES.recipeBuilder()
                .input(HIGH_DENSITY_URANIUM)
                .input(dust, Potassium, 8)
                .input(dust, Naquadah, 4)
                .circuitMeta(4)
                .fluidInputs(Radon.getFluid(1000))
                .fluidOutputs(UraniumBasedLiquidFuel.getFluid(1000))
                .EUt(VA[LuV])
                .duration(10 * SECOND)
                .buildAndRegister();

        //  U36K8Nq4Rn Combustion Generator Recipe
        COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(UraniumBasedLiquidFuel.getFluid(10))
                .EUt(VA[IV])
                .duration(5 * SECOND)
                .buildAndRegister();

        //  U36K8Nq4Rn -> *U36K8Nq4Rn*
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(UraniumBasedLiquidFuel.getFluid(1000))
                .fluidInputs(Argon.getPlasma(250))
                .fluidOutputs(UraniumFluidFuelExcited.getFluid(L))
                .EUt(VA[LuV])
                .duration(SECOND)
                .EUToStart(240000000L) // MK2
                .buildAndRegister();

        //  *U36K8Nq4Rn* Naquadah Reactor recipe
        //  back to Taranium Processing class.
    }

    private static void PlutoniumBasedLiquidFuelChain() {

        //  10Pu-239 + 2U-238 + 8C + 12O -> Pu10O12U2C8
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Plutonium239, 10)
                .input(dust, Uranium238, 2)
                .input(dust, Carbon, 8)
                .circuitMeta(4)
                .fluidInputs(Oxygen.getFluid(12000))
                .output(dust, PlutoniumUraniumOxides, 32)
                .EUt(VA[HV])
                .duration((int) (2.5 * SECOND))
                .buildAndRegister();

        //  High Density Plutonium
        IMPLOSION_RECIPES.recipeBuilder()
                .input(dust, PlutoniumUraniumOxides, 64)
                .input(foil, HSSS, 16)
                .output(HIGH_DENSITY_PLUTONIUM)
                .EUt(VA[ZPM])
                .duration(10 * SECOND)
                .explosivesAmount(32)
                .buildAndRegister();

        //  High Density Plutonium + 8Rb + 16Cs + 2Nq+ -> Pu64Rb8Cs16Nq+2
        MIXER_RECIPES.recipeBuilder()
                .input(HIGH_DENSITY_PLUTONIUM)
                .input(dust, Rubidium, 8)
                .input(dust, Caesium, 16)
                .input(dust, NaquadahEnriched, 2)
                .circuitMeta(4)
                .fluidOutputs(PlutoniumBasedLiquidFuel.getFluid(1000))
                .EUt(VA[ZPM])
                .duration(10 * SECOND)
                .buildAndRegister();

        //  Pu64Rb8Cs16Nq+2 Combustion Generator Recipe
        COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(PlutoniumBasedLiquidFuel.getFluid(10))
                .EUt(VA[IV])
                .duration((int) (7.5 * SECOND))
                .buildAndRegister();

        //  Pu64Rb8Cs16Nq+2 -> *Pu64Rb8Cs16Nq+2*
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(PlutoniumBasedLiquidFuel.getFluid(1000))
                .fluidInputs(Xenon.getPlasma(250))
                .fluidOutputs(PlutoniumFluidFuelExcited.getFluid(L))
                .EUt(VA[ZPM])
                .duration(SECOND)
                .EUToStart(360000000L) // MK3
                .buildAndRegister();

        //  *Pu64Rb8Cs16Nq+2* Naquadah Reactor recipe
        //  back to Taranium Processing class

    }
    private static void FuelRefineChainOT() {
        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(ThoriumFluidFuelExcited.getFluid(1))
                .fluidOutputs(GTQTMaterials.ThoriumFluidFuelDepleted.getFluid(1))
                .EUt(131072)
                .duration(8)
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.ThoriumFluidFuelDepleted.getFluid(500))
                .output(dust, Uranium233, 16)
                .chancedOutput(dust, Uranium236, 5000, 200)
                .output(dust, Praseodymium, 2)
                .chancedOutput(dust, Praseodymium, 2000, 200)
                .output(dust, Indium, 2)
                .fluidOutputs(Radon.getFluid(20))
                .EUt(480)
                .duration(600)
                .buildAndRegister();

        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(UraniumFluidFuelExcited.getFluid(1))
                .fluidOutputs(GTQTMaterials.UraniumFluidFuelDepleted.getFluid(1))
                .EUt(131072)
                .duration(10)
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.UraniumFluidFuelDepleted.getFluid(500))
                .output(dust, Europium, 4)
                .chancedOutput(dust, Europium, 1500, 200)
                .output(dust, Bismuth, 2)
                .chancedOutput(dust, Bismuth, 2000, 200)
                .output(dust, RhodiumOxide, 1)
                .fluidOutputs(Xenon.getFluid(20))
                .EUt(480)
                .duration(600)
                .buildAndRegister();

        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(PlutoniumFluidFuelExcited.getFluid(1))
                .fluidOutputs(GTQTMaterials.PlutoniumFluidFuelDepleted.getFluid(1))
                .EUt(131072)
                .duration(36)
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.PlutoniumFluidFuelDepleted.getFluid(500))
                .output(dust, Tritanium, 4)
                .chancedOutput(dust, Tritanium, 1500, 200)
                .output(dust, CeriumOxide, 2)
                .chancedOutput(dust, CeriumOxide, 2000, 200)
                .output(dust, Samarium, 1)
                .fluidOutputs(Xenon.getFluid(20))
                .EUt(480)
                .duration(600)
                .buildAndRegister();
    }

    private static void FuelRefineChainNQ() {
        //机器	电路板	功率(EU/t)	特殊要求	时间	固体输入	流体输入	固体输出1	固体输出2	流体输出1	流体输出2	流体输出3	流体输出4	流体输出5
        //工业高炉	编程 16	1,920	炉温3,400K	180s	富集硅岩粉 16	氢氟酸 3,000L	放射性污泥粉 3		酸性硅岩乳液 2,000L
        //工业高炉	编程 16	4,080	炉温3,400K	180s	超能硅岩粉 32	氟锑酸 4,000L	极不稳定硅岩粉 17		酸性硅岩乳液 8,000L
        //大型化学反应釜	编程 3	30		12s	生石灰粉 8	酸性硅岩乳液 1,000L	小堆三氧化二锑粉 1	氟石粉 4	硅岩乳液 1,000L
        //离心机	编程 1	120		40		硅岩乳液 1,000L	放射性污泥粉 5.46[1]		硅岩溶液 500L
        //蒸馏塔		1,920		20		硅岩溶液 20L			硅岩沥青 2L	重质硅岩燃料 5L	轻质硅岩燃料 10L	水 10L	硅岩气 60L

        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, NaquadahEnriched, 16)
                .fluidInputs(HydrofluoricAcid.getFluid(3000))
                .output(dust, RadioactiveSludge, 3)
                .fluidOutputs(AcidicNaquadahEmulsion.getFluid(2000))
                .blastFurnaceTemp(3400)
                .circuitMeta(16)
                .EUt(1920)
                .duration(180)
                .buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, Naquadria, 32)
                .fluidInputs(FluoroantimonicAcid.getFluid(4000))
                .output(dust, UnstableNaquadahPowder, 17)
                .fluidOutputs(AcidicNaquadahEmulsion.getFluid(8000))
                .blastFurnaceTemp(3400)
                .circuitMeta(16)
                .EUt(4080)
                .duration(180)
                .buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Quicklime, 8)
                .fluidInputs(AcidicNaquadahEmulsion.getFluid(1000))
                .output(dust, AntimonyTrioxide, 1)
                .output(dust, Fluorite, 4)
                .fluidOutputs(NaquadahEmulsion.getFluid(1000))
                .EUt(30)
                .duration(12)
                .circuitMeta(3)
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(NaquadahEmulsion.getFluid(1000))
                .output(dust, RadioactiveSludge, 5)
                .fluidOutputs(CrudeNaquadahFuel.getFluid(500))
                .EUt(120)
                .duration(40)
                .circuitMeta(1)
                .buildAndRegister();

        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(CrudeNaquadahFuel.getFluid(2000))
                .fluidOutputs(HeavyNaquadahFuel.getFluid(500))
                .fluidOutputs(MediumNaquadahFuel.getFluid(1000))
                .fluidOutputs(LightNaquadahFuel.getFluid(1500))
                .fluidOutputs(NaquadahAsphalt.getFluid(200))
                .fluidOutputs(NaquadahGas.getFluid(6000))
                .fluidOutputs(Water.getFluid(1000))
                .EUt(7680)
                .duration(2000)
                .buildAndRegister();
        /////////////////////////////////////后处理////////////////////////////////////
        // 轻度裂化硅岩沥青
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(NaquadahAsphalt.getFluid(1000)) // 输入10L硅岩沥青
                .fluidOutputs(HeavyNaquadahFuel.getFluid(280)) // 输出5L重质硅岩燃料
                .fluidOutputs(Thulium.getFluid(144)) // 输出2L熔融铥
                .fluidOutputs(Uranium235.getFluid(576))
                .fluidOutputs(Plutonium238.getFluid(576))
                .fluidOutputs(Thorium.getFluid(288))
                .EUt(1920) // 功率1920 EU/t
                .duration(20 * SECOND) // 时间20秒
                .buildAndRegister();

        // 轻度裂化硅岩气
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(NaquadahGas.getFluid(100)) // 输入60L硅岩气
                .fluidOutputs(Radon.getFluid(9800)) // 输出672L氡 (11.2倍)
                .fluidOutputs(Xenon.getFluid(120))
                .fluidOutputs(Argon.getFluid(120))
                .fluidOutputs(Helium3.getFluid(36))
                .fluidOutputs(Krypton.getFluid(72))
                .EUt(1920) // 功率1920 EU/t
                .duration(20 * SECOND) // 时间20秒
                .buildAndRegister();

        // 离心机处理放射性污泥粉
        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust, RadioactiveSludge, 5) // 输入5个单位的放射性污泥粉
                .output(dust, Uranium238, 1) // 输出1个单位的铀-238
                .output(dust, Plutonium239, 1) // 输出1个单位的钚-239
                .chancedOutput(dust, Naquadah, 5000, 0)
                .fluidOutputs(Radon.getFluid(10)) // 输出10L氡
                .EUt(120) // 功率120 EU/t
                .duration(60 * SECOND) // 时间60秒
                .buildAndRegister();

        ///////////////////////////////////// /////////////////////////////////////
        //一级燃料
        //核聚变反应堆		26,000	启动：320mEU(MK2)	25s					轻质硅岩燃料 780L	重质硅岩燃料 360L	硅岩基流体燃料-MKI 100L
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(LightNaquadahFuel.getFluid(780))
                .fluidInputs(MediumNaquadahFuel.getFluid(540))
                .fluidOutputs(NaquadahFuelMKI.getFluid(100))
                .EUt(26000)
                .duration(25 * SECOND)
                .EUToStart(320000000L) // MK2
                .buildAndRegister();

        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(MediumNaquadahFuel.getFluid(540))
                .fluidInputs(HeavyNaquadahFuel.getFluid(360))
                .fluidOutputs(NaquadahFuelMKI.getFluid(100))
                .EUt(26000)
                .duration(25 * SECOND)
                .EUToStart(320000000L) // MK2
                .buildAndRegister();

        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(LightNaquadahFuel.getFluid(780))
                .fluidInputs(HeavyNaquadahFuel.getFluid(360))
                .fluidOutputs(NaquadahFuelMKI.getFluid(100))
                .EUt(26000)
                .duration(25 * SECOND)
                .EUToStart(320000000L) // MK2
                .buildAndRegister();

        NAQUADAH_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, NetherStar, 8)
                .input(dust, Phosphorus, 4)
                .input(HIGH_DENSITY_THORIUM, 2)
                .fluidInputs(Helium.getPlasma(1440))
                .fluidInputs(NaquadahFuelMKI.getFluid(2000))
                .fluidInputs(NaquadahGas.getFluid(1500))
                .fluidOutputs(NaquadahFuelMKII.getFluid(500))
                .EUt(525000)
                .duration(25 * SECOND)
                .blastFurnaceTemp(8000)
                .buildAndRegister();

        NAQUADAH_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Adamantium, 8)
                .input(dust, UnstableNaquadahPowder, 4)
                .input(HIGH_DENSITY_URANIUM, 2)
                .fluidInputs(Nitrogen.getPlasma(1440))
                .fluidInputs(NaquadahFuelMKII.getFluid(2000))
                .fluidInputs(NaquadahGas.getFluid(1500))
                .fluidOutputs(NaquadahFuelMKIII.getFluid(500))
                .EUt(1100000)
                .duration(5 * SECOND)
                .blastFurnaceTemp(8000)
                .buildAndRegister();

        NAQUADAH_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Adamantium, 16)
                .input(dust, UnstableNaquadahPowder, 8)
                .input(HIGH_DENSITY_PLUTONIUM, 4)
                .fluidInputs(HeavyNaquadahFuel.getFluid(300))
                .fluidInputs(MediumNaquadahFuel.getFluid(400))
                .fluidInputs(LightNaquadahFuel.getFluid(500))
                .fluidOutputs(NaquadahFuelMKIII.getFluid(100))
                .EUt(1100000)
                .duration(5 * SECOND)
                .blastFurnaceTemp(8000)
                .buildAndRegister();

        NAQUADAH_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, UnstableNaquadahPowder, 16)
                .input(dust, Bedrock, 8)
                .input(dust, AwakenedDraconium, 8)
                .fluidInputs(Xenon.getPlasma(5760))
                .fluidInputs(Praseodymium.getFluid(1440))
                .fluidInputs(NaquadahFuelMKIII.getFluid(2000))
                .fluidOutputs(NaquadahFuelMKIV.getFluid(500))
                .EUt(46000000)
                .duration(8 * SECOND)
                .blastFurnaceTemp(9200)
                .buildAndRegister();

        /////////////////////////////////////偷鸡简化/////////////////////////////////////
        //普通硅岩燃料产线：
        //  Light Naquadah Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Naquadah)
                .fluidInputs(Uranium235.getFluid(500))
                .fluidInputs(Nitrogen.getFluid(500))
                .circuitMeta(1)
                .fluidOutputs(LightNaquadahFuel.getFluid(500))
                .duration(300)
                .EUt(VA[ZPM])
                .buildAndRegister();

        NAQUADAH_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, GalliumSulfide)
                .fluidInputs(EnergeticNaquadria.getFluid(1000))
                .fluidInputs(Nitrogen.getPlasma(1000))
                .circuitMeta(11)
                .fluidOutputs(LightNaquadahFuel.getFluid(2000))
                .duration(300)
                .blastFurnaceTemp(6000)
                .EUt(VA[UV])
                .buildAndRegister();

        //  Medium Naquadah Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, NaquadahEnriched)
                .fluidInputs(Uranium235.getFluid(500))
                .fluidInputs(Plutonium241.getFluid(500))
                .circuitMeta(2)
                .output(dust, Plutonium239)
                .fluidOutputs(MediumNaquadahFuel.getFluid(500))
                .duration(300)
                .EUt(VA[ZPM])
                .buildAndRegister();

        NAQUADAH_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, IndiumPhosphide)
                .fluidInputs(EnergeticNaquadria.getFluid(1000))
                .fluidInputs(Nitrogen.getPlasma(1000))
                .circuitMeta(12)
                .fluidOutputs(MediumNaquadahFuel.getFluid(2000))
                .duration(300)
                .blastFurnaceTemp(6000)
                .EUt(VA[UV])
                .buildAndRegister();

        //  Heavy Naquadah Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Naquadria)
                .input(dust, Plutonium239)
                .fluidInputs(Nitrogen.getPlasma(500))
                .circuitMeta(3)
                .output(dust, Naquadah)
                .fluidOutputs(HeavyNaquadahFuel.getFluid(500))
                .duration(300)
                .EUt(VA[ZPM])
                .buildAndRegister();

        NAQUADAH_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Trinium)
                .fluidInputs(EnergeticNaquadria.getFluid(1000))
                .fluidInputs(Nitrogen.getPlasma(1000))
                .circuitMeta(13)
                .fluidOutputs(HeavyNaquadahFuel.getFluid(2000))
                .duration(300)
                .blastFurnaceTemp(6000)
                .EUt(VA[UV])
                .buildAndRegister();
    }

    private static void CommonFuel() {
        //燃料精炼
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .fluidInputs(LightFuel.getFluid(1000))
                .fluidInputs(HeavyFuel.getFluid(1000))
                .fluidOutputs(Diesel.getFluid(6000))
                .EUt(VA[EV])
                .duration(20)
                .buildAndRegister();

        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .fluidInputs(Diesel.getFluid(1000))
                .fluidInputs(Nitrogen.getFluid(1000))
                .fluidOutputs(CetaneBoostedDiesel.getFluid(6000))
                .EUt(VA[EV])
                .duration(20)
                .buildAndRegister();

        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .fluidInputs(BioDiesel.getFluid(1000))
                .fluidInputs(Nitrogen.getFluid(1000))
                .fluidOutputs(CetaneBoostedDiesel.getFluid(6000))
                .EUt(VA[EV])
                .duration(20)
                .buildAndRegister();

        //  High Octane Gasoline
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .fluidInputs(Gasoline.getFluid(1000))
                .fluidInputs(Octane.getFluid(1000))
                .fluidOutputs(HighOctaneGasoline.getFluid(6000))
                .EUt(VA[IV])
                .duration(100)
                .buildAndRegister();
    }

    private static void HyperReactorFuel() {
        //  Hyper Reactor Mk I
        HYPER_REACTOR_MK1_RECIPES.recipeBuilder()
                .fluidInputs(LightHyperFuel.getFluid(1))
                .duration(200)
                .EUt((int) V[LuV])
                .buildAndRegister();

        HYPER_REACTOR_MK1_RECIPES.recipeBuilder()
                .fluidInputs(MediumHyperFuel.getFluid(1))
                .duration(400)
                .EUt((int) V[LuV])
                .buildAndRegister();

        HYPER_REACTOR_MK1_RECIPES.recipeBuilder()
                .fluidInputs(HeavyHyperFuel.getFluid(1))
                .duration(600)
                .EUt((int) V[LuV])
                .buildAndRegister();

        //  Hyper Reactor Mk II
        HYPER_REACTOR_MK2_RECIPES.recipeBuilder()
                .fluidInputs(LightHyperFuel.getFluid(1))
                .duration(200)
                .EUt((int) V[ZPM])
                .buildAndRegister();

        HYPER_REACTOR_MK2_RECIPES.recipeBuilder()
                .fluidInputs(MediumHyperFuel.getFluid(1))
                .duration(400)
                .EUt((int) V[ZPM])
                .buildAndRegister();

        HYPER_REACTOR_MK2_RECIPES.recipeBuilder()
                .fluidInputs(HeavyHyperFuel.getFluid(1))
                .duration(600)
                .EUt((int) V[ZPM])
                .buildAndRegister();

        //  Hyper Reactor Mk III
        HYPER_REACTOR_MK3_RECIPES.recipeBuilder()
                .fluidInputs(LightHyperFuel.getFluid(1))
                .duration(200)
                .EUt((int) V[UV])
                .buildAndRegister();

        HYPER_REACTOR_MK3_RECIPES.recipeBuilder()
                .fluidInputs(MediumHyperFuel.getFluid(1))
                .duration(400)
                .EUt((int) V[UV])
                .buildAndRegister();

        HYPER_REACTOR_MK3_RECIPES.recipeBuilder()
                .fluidInputs(HeavyHyperFuel.getFluid(1))
                .duration(600)
                .EUt((int) V[UV])
                .buildAndRegister();
    }

    private static void FuelRefineChain() {
        //  Light Taranium Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Taranium)
                .input(dust, Gallium)
                .fluidInputs(DragonBlood.getFluid(1000))
                .fluidInputs(LightNaquadahFuel.getFluid(12000))
                .fluidInputs(Krypton.getFluid(6000))
                .circuitMeta(4)
                .fluidOutputs(LightTaraniumFuel.getFluid(12000))
                .duration(300)
                .EUt(VA[UV])
                .buildAndRegister();

        //  Medium Taranium Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Taranium)
                .input(dust, Duranium)
                .fluidInputs(DragonBlood.getFluid(2000))
                .fluidInputs(MediumNaquadahFuel.getFluid(12000))
                .fluidInputs(Xenon.getFluid(6000))
                .circuitMeta(5)
                .fluidOutputs(MediumTaraniumFuel.getFluid(12000))
                .duration(300)
                .EUt(VA[UV])
                .buildAndRegister();

        //  Heavy Taranium Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Taranium)
                .input(dust, Tritanium)
                .fluidInputs(DragonBlood.getFluid(4000))
                .fluidInputs(HeavyNaquadahFuel.getFluid(12000))
                .fluidInputs(Radon.getFluid(6000))
                .circuitMeta(6)
                .fluidOutputs(HeavyTaraniumFuel.getFluid(12000))
                .duration(300)
                .EUt(VA[UV])
                .buildAndRegister();

        //  Light Enriched Taranium Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Americium)
                .fluidInputs(DragonBlood.getFluid(1000))
                .fluidInputs(LightTaraniumFuel.getFluid(6000))
                .fluidInputs(EnergeticNaquadria.getFluid(1000))
                .circuitMeta(14)
                .fluidOutputs(LightEnrichedTaraniumFuel.getFluid(6000))
                .duration(300)
                .EUt(VA[UHV])
                .buildAndRegister();

        //  Medium Enriched Taranium Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Dubnium)
                .fluidInputs(DragonBlood.getFluid(2000))
                .fluidInputs(MediumTaraniumFuel.getFluid(6000))
                .fluidInputs(EnergeticNaquadria.getFluid(1000))
                .circuitMeta(15)
                .fluidOutputs(MediumEnrichedTaraniumFuel.getFluid(6000))
                .duration(300)
                .EUt(VA[UHV])
                .buildAndRegister();

        //  Heavy Enriched Taranium Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Livermorium)
                .fluidInputs(DragonBlood.getFluid(4000))
                .fluidInputs(HeavyTaraniumFuel.getFluid(6000))
                .fluidInputs(EnergeticNaquadria.getFluid(1000))
                .circuitMeta(16)
                .fluidOutputs(HeavyEnrichedTaraniumFuel.getFluid(6000))
                .duration(300)
                .EUt(VA[UHV])
                .buildAndRegister();

        //  Energetic Naquadria
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Naquadria)
                .fluidInputs(NitrogenDioxide.getFluid(500))
                .fluidInputs(SulfuricAcid.getFluid(500))
                .circuitMeta(0)
                .output(dust, Lutetium)
                .output(dust, Uranium238)
                .output(dust, Plutonium241)
                .output(dust, NaquadahEnriched)
                .fluidOutputs(EnergeticNaquadria.getFluid(1000))
                .duration(300)
                .EUt(65536)
                .buildAndRegister();

        //  Light Hyper Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Europium)
                .fluidInputs(LightTaraniumFuel.getFluid(500))
                .fluidInputs(LightEnrichedTaraniumFuel.getFluid(300))
                .fluidInputs(EnergeticNaquadria.getFluid(200))
                .fluidInputs(Uranium238.getFluid(L))
                .circuitMeta(7)
                .output(dust, Naquadah)
                .fluidOutputs(LightHyperFuel.getFluid(2000))
                .duration(460)
                .EUt(VA[UHV])
                .buildAndRegister();

        //  Medium Hyper Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Americium)
                .fluidInputs(MediumTaraniumFuel.getFluid(400))
                .fluidInputs(MediumEnrichedTaraniumFuel.getFluid(350))
                .fluidInputs(EnergeticNaquadria.getFluid(250))
                .fluidInputs(Uranium235.getFluid(L))
                .circuitMeta(8)
                .output(dust, NaquadahEnriched)
                .fluidOutputs(MediumHyperFuel.getFluid(2000))
                .duration(520)
                .EUt(VA[UHV])
                .buildAndRegister();

        //  Heavy Hyper Fuel
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Orichalcum)
                .fluidInputs(HeavyTaraniumFuel.getFluid(300))
                .fluidInputs(HeavyEnrichedTaraniumFuel.getFluid(400))
                .fluidInputs(EnergeticNaquadria.getFluid(300))
                .fluidInputs(Plutonium239.getFluid(L))
                .circuitMeta(9)
                .output(dust, NaquadahEnriched)
                .fluidOutputs(HeavyHyperFuel.getFluid(2000))
                .duration(580)
                .EUt(VA[UHV])
                .buildAndRegister();

        //  Adamantium + Bedrock Gas + Sulfuric Acid -> Adamantium Enriched + Deep Iron + Naquadah + Osmium + Diluted Sulfuric Acid
        FUEL_REFINE_FACTORY_RECIPES.recipeBuilder()
                .input(dust, Adamantium, 10)
                .fluidInputs(BedrockGas.getFluid(100))
                .fluidInputs(SulfuricAcid.getFluid(100))
                .circuitMeta(10)
                .output(dust, AdamantiumEnriched)
                .output(dust, DeepIron, 5)
                .output(dust, Naquadah, 2)
                .output(dust, Osmium, 2)
                .fluidOutputs(DilutedSulfuricAcid.getFluid(900))
                .duration(200)
                .EUt(VA[UHV])
                .buildAndRegister();

    }

    private static void NaquadahFuel() {
        //小硅岩
        NAQUADAH_REACTOR.recipeBuilder()
                .input(screw, NaquadahEnriched)
                .output(screw, Lead)
                .EUt(8192)
                .duration(2000)
                .buildAndRegister();

        NAQUADAH_REACTOR.recipeBuilder()
                .input(screw, Naquadria)
                .output(screw, Lead)
                .EUt(8192)
                .duration(4000)
                .buildAndRegister();

        NAQUADAH_REACTOR.recipeBuilder()
                .input(stick, NaquadahEnriched)
                .output(stick, Lead)
                .EUt(8192)
                .duration(4000)
                .buildAndRegister();

        NAQUADAH_REACTOR.recipeBuilder()
                .input(stick, Naquadria)
                .output(stick, Lead)
                .EUt(8192)
                .duration(8000)
                .buildAndRegister();

        NAQUADAH_REACTOR.recipeBuilder()
                .input(stickLong, NaquadahEnriched)
                .output(stickLong, Lead)
                .EUt(8192)
                .duration(8000)
                .buildAndRegister();

        NAQUADAH_REACTOR.recipeBuilder()
                .input(stickLong, Naquadria)
                .output(stickLong, Lead)
                .EUt(8192)
                .duration(16000)
                .buildAndRegister();
        //大硅岩
        //  Light Naquadah Fuel
        COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(LightNaquadahFuel.getFluid(1))
                .EUt(8192)
                .duration(15)
                .buildAndRegister();

        //  Medium Naquadah Fuel
        COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(MediumNaquadahFuel.getFluid(1))
                .EUt(8192)
                .duration(30)
                .buildAndRegister();

        //  Heavy Naquadah Fuel
        COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(HeavyNaquadahFuel.getFluid(1))
                .EUt(8192)
                .duration(45)
                .buildAndRegister();

        //硅岩基
        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(NaquadahFuelMKI.getFluid(1))
                .fluidOutputs(GTQTMaterials.NaquadahResidueMKI.getFluid(1))
                .EUt(131072)
                .duration(400)
                .buildAndRegister();

        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(NaquadahFuelMKII.getFluid(1))
                .fluidOutputs(GTQTMaterials.NaquadahResidueMKII.getFluid(1))
                .EUt(131072)
                .duration(2000)
                .buildAndRegister();

        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(NaquadahFuelMKIII.getFluid(1))
                .fluidOutputs(GTQTMaterials.NaquadahResidueMKIII.getFluid(1))
                .EUt(131072)
                .duration(3600)
                .buildAndRegister();

        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(NaquadahFuelMKIV.getFluid(1))
                .fluidOutputs(GTQTMaterials.NaquadahResidueMKIV.getFluid(1))
                .EUt(131072)
                .duration(5200)
                .buildAndRegister();

        // 回收配方
        // 回收配方
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.NaquadahResidueMKI.getFluid(1000))
                .fluidOutputs(NaquadahAsphalt.getFluid(20))
                .chancedOutput(dust, Uranium238, 100, 100)
                .output(dust, Uranium235)
                .output(dust, Plutonium239)
                .EUt(3072)
                .duration(400)
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.NaquadahResidueMKII.getFluid(1000))
                .fluidOutputs(NaquadahAsphalt.getFluid(20))
                .chancedOutput(dust, Plutonium239, 100, 100)
                .chancedOutput(dust, Uranium235, 100, 100)
                .chancedOutput(dust, Uranium238, 100, 100)
                .EUt(3072)
                .duration(400)
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.NaquadahResidueMKIII.getFluid(1000))
                .fluidOutputs(NaquadahAsphalt.getFluid(20))
                .chancedOutput(dust, Uranium235, 100, 100)
                .chancedOutput(dust, GalliumSulfide, 100, 100)
                .chancedOutput(dust, Plutonium239, 100, 100)
                .EUt(3072)
                .duration(400)
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(GTQTMaterials.NaquadahResidueMKIV.getFluid(1000))
                .fluidOutputs(NaquadahAsphalt.getFluid(20))
                .chancedOutput(dust, Plutonium241, 100, 100)
                .chancedOutput(dust, Adamantium, 100, 100)
                .chancedOutput(dust, NetherStar, 100, 100)
                .EUt(3072)
                .duration(400)
                .buildAndRegister();
        /*
        //  Heavy Taranium Fuel
        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(HeavyTaraniumFuel.getFluid(1))
                .EUt(8192)
                .duration(80)
                .buildAndRegister();


        //  Medium Taranium Fuel
        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(MediumTaraniumFuel.getFluid(1))
                .EUt(8192)
                .duration(60)
                .buildAndRegister();

        //  Light Taranium Fuel
        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(LightTaraniumFuel.getFluid(1))
                .EUt(8192)
                .duration(30)
                .buildAndRegister();

        //  Heavy Enriched Taranium Fuel
        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(HeavyEnrichedTaraniumFuel.getFluid(1))
                .EUt(8192)
                .duration(720)
                .buildAndRegister();

        //  Medium Enriched Taranium Fuel
        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(MediumEnrichedTaraniumFuel.getFluid(1))
                .EUt(8192)
                .duration(480)
                .buildAndRegister();

        //  Light Enriched Taranium Fuel
        NAQUADAH_REACTOR_RECIPES.recipeBuilder()
                .fluidInputs(LightEnrichedTaraniumFuel.getFluid(1))
                .EUt(8192)
                .duration(240)
                .buildAndRegister();
         */

    }
}
