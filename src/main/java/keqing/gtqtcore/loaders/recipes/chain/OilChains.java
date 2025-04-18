package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.material.Material;
import gregtech.common.blocks.BlockAsphalt;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtechfoodoption.GTFOMaterialHandler.SodiumSulfate;
import static gregtechfoodoption.GTFOMaterialHandler.Stearin;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class OilChains {
    public static void init() {
        Chemical();     //化工线路
        NewOil();       //新油电
        Pre();          //预处理
        Kettle();       //原始蒸馏
        atmosphericAndVacuumPressure();  //atmosphericAndVacuumPressure
        exquisiteRecycling();  //exquisiteRecycling
        catalyticCracking(); //catalyticCracking
        Desulphurization();
    }

    private static void Desulphurization() {
        DESULPHURIZATION_RECIPES.recipeBuilder()
                .fluidInputs(SulfuricHeavyFuel.getFluid(800))
                .fluidInputs(Hydrogen.getFluid(200))
                .fluidOutputs(HydrogenSulfide.getFluid(100))
                .fluidOutputs(HeavyFuel.getFluid(800))
                .duration(20).EUt(VA[LV]).buildAndRegister();

        DESULPHURIZATION_RECIPES.recipeBuilder()
                .fluidInputs(SulfuricLightFuel.getFluid(1200))
                .fluidInputs(Hydrogen.getFluid(200))
                .fluidOutputs(HydrogenSulfide.getFluid(100))
                .fluidOutputs(LightFuel.getFluid(1200))
                .duration(20).EUt(VA[LV]).buildAndRegister();

        DESULPHURIZATION_RECIPES.recipeBuilder()
                .fluidInputs(SulfuricNaphtha.getFluid(1200))
                .fluidInputs(Hydrogen.getFluid(200))
                .fluidOutputs(HydrogenSulfide.getFluid(100))
                .fluidOutputs(Naphtha.getFluid(1200))
                .duration(20).EUt(VA[LV]).buildAndRegister();

        DESULPHURIZATION_RECIPES.recipeBuilder()
                .fluidInputs(SulfuricGas.getFluid(1600))
                .fluidInputs(Hydrogen.getFluid(200))
                .fluidOutputs(HydrogenSulfide.getFluid(100))
                .fluidOutputs(RefineryGas.getFluid(1600))
                .duration(20).EUt(VA[LV]).buildAndRegister();

        DESULPHURIZATION_RECIPES.recipeBuilder()
                .fluidInputs(NaturalGas.getFluid(1600))
                .fluidInputs(Hydrogen.getFluid(200))
                .fluidOutputs(HydrogenSulfide.getFluid(100))
                .fluidOutputs(RefineryGas.getFluid(1600))
                .duration(20).EUt(VA[LV]).buildAndRegister();

        DESULPHURIZATION_RECIPES.recipeBuilder()
                .fluidInputs(SDieselLight.getFluid(1600))
                .fluidInputs(Hydrogen.getFluid(200))
                .fluidOutputs(HydrogenSulfide.getFluid(100))
                .fluidOutputs(DieselLight.getFluid(1600))
                .duration(20).EUt(VA[LV]).buildAndRegister();

        DESULPHURIZATION_RECIPES.recipeBuilder()
                .fluidInputs(SDieselHeavy.getFluid(1600))
                .fluidInputs(Hydrogen.getFluid(200))
                .fluidOutputs(HydrogenSulfide.getFluid(100))
                .fluidOutputs(DieselHeavy.getFluid(1600))
                .duration(20).EUt(VA[LV]).buildAndRegister();
    }

    private static void Chemical() {
        //一氧化碳和氢氧化钠溶液在160-200 ℃和2 MPa压力下反应生成甲酸钠，然后经硫酸酸解、蒸馏即得成品。
        //CO NaOH HCOONa
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(CarbonMonoxide.getFluid(1000))
                .fluidInputs(SodiumHydroxideSolution.getFluid(1000))
                .fluidOutputs(SodiumFormate.getFluid(1000))
                .circuitMeta(1)
                .duration(120).EUt(30).buildAndRegister();
        //2HCOONa    H2SO4     CH₂O₂  Na2SO4
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .fluidInputs(SodiumFormate.getFluid(2000))
                .fluidInputs(Oxygen.getFluid(2000))
                .fluidOutputs(FormicAcid.getFluid(2000))
                .output(dust, SodiumSulfate, 7)
                .circuitMeta(1)
                .duration(120).EUt(30).buildAndRegister();

    }

    private static void exquisiteRecycling() {
        //常压渣油 减压渣油产线
        /*
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(200)
                .EUt(30)
                .circuitMeta(1)
                .fluidInputs(AtmosphericResidue.getFluid(2000))
                .fluidInputs(WaxOil.getFluid(1000))
                .fluidOutputs(OilGas.getFluid(3000))
                .buildAndRegister();
        */
        //VacuumResidue 减压渣油

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(600)
                .EUt(480)
                .fluidInputs(AtmosphericResidue.getFluid(1000))
                .fluidInputs(WaxOil.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(7000))
                .fluidOutputs(OilGas.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(600)
                .EUt(480)
                .fluidInputs(VacuumResidue.getFluid(1000))
                .fluidInputs(WaxOil.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(7000))
                .fluidOutputs(OilGas.getFluid(3000))
                .buildAndRegister();
        //用减压渣油和未转化催化裂化蜡油(FGO)的混合原料，在液时体积空速0.20 h-1、反应器入口氢分压16.5 MPa、氢油比700的工艺条件下，
        // 开展了固定床渣油加氢试验。加氢常压渣油主要性质满足缓和催化裂化原料要求，利用加氢常压渣油开展了缓和催化裂化试验。
        // 通过渣油加氢与缓和催化裂化工艺组合，使固定床渣油加氢可以加工100%减压渣油。以减压渣油进料计，多产FGO、兼顾FGO和汽油、多产汽油3种方案
        // 汽油+柴油质量收率分别为67.77%，66.38%，61.99%，高附加值的液化石油气质量收率分别为15.69%，16.76%，19.22%，

        CRACKING_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .fluidInputs(OilGas.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidOutputs(LightlyHydroCrackedDieselLight.getFluid(500))
                .fluidOutputs(LightlyHydroCrackedDieselHeavy.getFluid(500))
                .duration(300).EUt(120).buildAndRegister();

        CRACKING_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .fluidInputs(OilGas.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(6000))
                .fluidOutputs(SeverelyHydroCrackedDieselLight.getFluid(500))
                .fluidOutputs(SeverelyHydroCrackedDieselHeavy.getFluid(500))
                .duration(600).EUt(240).buildAndRegister();

        SFM.recipeBuilder()
                .fluidInputs(OilGas.getFluid(2000))
                .circuitMeta(1)
                .chancedOutput(dust, Carbon, 1111, 0)
                .fluidOutputs(DieselLight.getFluid(240))
                .fluidOutputs(Naphtha.getFluid(50))
                .fluidOutputs(Toluene.getFluid(25))
                .fluidOutputs(Benzene.getFluid(125))
                .fluidOutputs(Butene.getFluid(25))
                .fluidOutputs(Butadiene.getFluid(15))
                .fluidOutputs(Propane.getFluid(3))
                .fluidOutputs(Propene.getFluid(30))
                .fluidOutputs(Ethane.getFluid(5))
                .fluidOutputs(Ethylene.getFluid(50))
                .fluidOutputs(Acetylene.getFluid(50))
                .fluidOutputs(Dimethylbenzene.getFluid(50))
                .duration(120).EUt(120).buildAndRegister();

        SFM.recipeBuilder()
                .fluidInputs(OilGas.getFluid(2000))
                .circuitMeta(2)
                .chancedOutput(dust, Carbon, 1111, 0)
                .fluidOutputs(DieselHeavy.getFluid(240))
                .fluidOutputs(Naphtha.getFluid(50))
                .fluidOutputs(Toluene.getFluid(25))
                .fluidOutputs(Benzene.getFluid(125))
                .fluidOutputs(Butene.getFluid(25))
                .fluidOutputs(Butadiene.getFluid(15))
                .fluidOutputs(Propane.getFluid(3))
                .fluidOutputs(Propene.getFluid(30))
                .fluidOutputs(Ethane.getFluid(5))
                .fluidOutputs(Ethylene.getFluid(50))
                .fluidOutputs(Acetylene.getFluid(50))
                .fluidOutputs(Dimethylbenzene.getFluid(50))
                .duration(120).EUt(120).buildAndRegister();
    }

    private static void NewOil() {

        GTRecipeHandler.removeRecipesByInputs(MIXER_RECIPES, Tetranitromethane.getFluid(20), Diesel.getFluid(1000));
        GTRecipeHandler.removeRecipesByInputs(MIXER_RECIPES, Tetranitromethane.getFluid(40), BioDiesel.getFluid(1000));

        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{IntCircuitIngredient.getIntegratedCircuit(24)}, new FluidStack[]{Tetranitromethane.getFluid(200), Diesel.getFluid(10000)});
        GTRecipeHandler.removeRecipesByInputs(LARGE_CHEMICAL_RECIPES, new ItemStack[]{IntCircuitIngredient.getIntegratedCircuit(24)}, new FluidStack[]{Tetranitromethane.getFluid(400), BioDiesel.getFluid(10000)});

        CHEMICAL_PLANT.recipeBuilder()
                .fluidInputs(Diesel.getFluid(7500))
                .fluidInputs(Tetranitromethane.getFluid(300))
                .fluidInputs(Tetrabromobenzene.getFluid(200))
                .fluidOutputs(CetaneBoostedDiesel.getFluid(8000))
                .recipeLevel(2)
                .duration(200)
                .EUt(480)
                .buildAndRegister();

        CHEMICAL_PLANT.recipeBuilder()
                .fluidInputs(BioDiesel.getFluid(15000))
                .fluidInputs(Tetranitromethane.getFluid(500))
                .fluidInputs(Tetrabromobenzene.getFluid(300))
                .fluidInputs(MTBE.getFluid(200))
                .fluidOutputs(CetaneBoostedDiesel.getFluid(8000))
                .recipeLevel(2)
                .duration(200)
                .EUt(480)
                .buildAndRegister();
        //柴油燃烧
        COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(DieselLight.getFluid(1))
                .duration(27)
                .fluidOutputs(HighTemperatureGas.getFluid(1350))
                .EUt(32)
                .buildAndRegister();

        COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(DieselHeavy.getFluid(1))
                .duration(27)
                .fluidOutputs(HighTemperatureGas.getFluid(1350))
                .EUt(32)
                .buildAndRegister();

        GTRecipeHandler.removeRecipesByInputs(COMBUSTION_GENERATOR_FUELS, Naphtha.getFluid(1));
        RecipeMaps.COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Naphtha.getFluid(1))
                .duration(45)
                .fluidOutputs(HighTemperatureGas.getFluid(2250))
                .EUt(32)
                .buildAndRegister();

        GTRecipeHandler.removeRecipesByInputs(COMBUSTION_GENERATOR_FUELS, SulfuricLightFuel.getFluid(4));
        RecipeMaps.COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(SulfuricLightFuel.getFluid(4))
                .duration(15)
                .fluidOutputs(HighTemperatureGas.getFluid(750))
                .EUt(32)
                .buildAndRegister();

        GTRecipeHandler.removeRecipesByInputs(COMBUSTION_GENERATOR_FUELS, Methanol.getFluid(4));
        RecipeMaps.COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Methanol.getFluid(4))
                .duration(36)
                .fluidOutputs(HighTemperatureGas.getFluid(1800))
                .EUt(32)
                .buildAndRegister();

        GTRecipeHandler.removeRecipesByInputs(COMBUSTION_GENERATOR_FUELS, Ethanol.getFluid(1));
        RecipeMaps.COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Ethanol.getFluid(1))
                .duration(18)
                .fluidOutputs(HighTemperatureGas.getFluid(1900))
                .EUt(32)
                .buildAndRegister();

        GTRecipeHandler.removeRecipesByInputs(COMBUSTION_GENERATOR_FUELS, Octane.getFluid(2));
        RecipeMaps.COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Octane.getFluid(2))
                .duration(15)
                .fluidOutputs(HighTemperatureGas.getFluid(750))
                .EUt(32)
                .buildAndRegister();

        GTRecipeHandler.removeRecipesByInputs(COMBUSTION_GENERATOR_FUELS, BioDiesel.getFluid(1));
        RecipeMaps.COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(BioDiesel.getFluid(1))
                .duration(18)
                .fluidOutputs(HighTemperatureGas.getFluid(900))
                .EUt(32)
                .buildAndRegister();

        GTRecipeHandler.removeRecipesByInputs(COMBUSTION_GENERATOR_FUELS, LightFuel.getFluid(1));
        RecipeMaps.COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(LightFuel.getFluid(1))
                .duration(30)
                .fluidOutputs(HighTemperatureGas.getFluid(1500))
                .EUt(32)
                .buildAndRegister();

        GTRecipeHandler.removeRecipesByInputs(COMBUSTION_GENERATOR_FUELS, Diesel.getFluid(1));
        RecipeMaps.COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Diesel.getFluid(1))
                .duration(45)
                .fluidOutputs(HighTemperatureGas.getFluid(2250))
                .EUt(32)
                .buildAndRegister();

        GTRecipeHandler.removeRecipesByInputs(COMBUSTION_GENERATOR_FUELS, CetaneBoostedDiesel.getFluid(2));
        RecipeMaps.COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(CetaneBoostedDiesel.getFluid(2))
                .duration(135)
                .fluidOutputs(HighTemperatureGas.getFluid(6750))
                .EUt(32)
                .buildAndRegister();

        GTRecipeHandler.removeRecipesByInputs(COMBUSTION_GENERATOR_FUELS, Gasoline.getFluid(1));
        RecipeMaps.COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Gasoline.getFluid(1))
                .duration(150)
                .fluidOutputs(HighTemperatureGas.getFluid(7500))
                .EUt(32)
                .buildAndRegister();

        GTRecipeHandler.removeRecipesByInputs(COMBUSTION_GENERATOR_FUELS, HighOctaneGasoline.getFluid(1));
        RecipeMaps.COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(HighOctaneGasoline.getFluid(1))
                .duration(300)
                .fluidOutputs(HighTemperatureGas.getFluid(15000))
                .EUt(32)
                .buildAndRegister();

        GTRecipeHandler.removeRecipesByInputs(COMBUSTION_GENERATOR_FUELS, Toluene.getFluid(1));
        RecipeMaps.COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Toluene.getFluid(1))
                .duration(90)
                .fluidOutputs(HighTemperatureGas.getFluid(4500))
                .EUt(32)
                .buildAndRegister();

        GTRecipeHandler.removeRecipesByInputs(COMBUSTION_GENERATOR_FUELS, OilLight.getFluid(32));
        RecipeMaps.COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(OilLight.getFluid(32))
                .duration(15)
                .fluidOutputs(HighTemperatureGas.getFluid(750))
                .EUt(32)
                .buildAndRegister();

        GTRecipeHandler.removeRecipesByInputs(COMBUSTION_GENERATOR_FUELS, RawOil.getFluid(64));
        RecipeMaps.COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(RawOil.getFluid(64))
                .duration(45)
                .fluidOutputs(HighTemperatureGas.getFluid(2250))
                .EUt(32)
                .buildAndRegister();

    }

    private static void Pre() {
        //破乳剂
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .circuitMeta(1)
                .input(dust, Sodium, 10)
                .fluidInputs(Stearin.getFluid(1000))
                .fluidInputs(EthyleneOxide.getFluid(1000))
                .fluidInputs(PropyleneOxide.getFluid(1000))
                .fluidOutputs(Demulsifier.getFluid(8000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(120)
                .circuitMeta(2)
                .input(dust, Potassium, 10)
                .fluidInputs(Stearin.getFluid(1000))
                .fluidInputs(EthyleneOxide.getFluid(1000))
                .fluidInputs(PropyleneOxide.getFluid(1000))
                .fluidOutputs(Demulsifier.getFluid(12000))
                .buildAndRegister();
        //氯乙醇
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .circuitMeta(1)
                .fluidInputs(Ethanol.getFluid(1000))
                .fluidInputs(HypochlorousAcid.getFluid(1000))
                .fluidInputs(Water.getFluid(1000))
                .fluidOutputs(EthyleneChlorohydrin.getFluid(1000))
                .buildAndRegister();
        //环氧乙烷
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .circuitMeta(1)
                .input(dust, Quicklime, 2)
                .fluidInputs(EthyleneChlorohydrin.getFluid(1000))
                .fluidOutputs(EthyleneOxide.getFluid(1000))
                .fluidOutputs(DilutedHydrochloricAcid.getFluid(1000))
                .output(dust, Calcium)
                .buildAndRegister();
        //环氧丙烷
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .circuitMeta(1)
                .input(dust, Quicklime)
                .fluidInputs(Propene.getFluid(1000))
                .fluidInputs(HydrogenPeroxide.getFluid(1000))
                .fluidOutputs(PropyleneOxide.getFluid(1000))
                .output(dust, Calcium)
                .buildAndRegister();
        //硫酸氨
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .circuitMeta(1)
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .fluidInputs(Ammonia.getFluid(1000))
                .fluidOutputs(AmmoniumSulfate.getFluid(1000))
                .buildAndRegister();

        //过氧化氢
        ELECTROBATH.recipeBuilder()
                .duration(100)
                .EUt(60)
                .tier(1)
                .circuitMeta(1)
                .fluidInputs(AmmoniumSulfate.getFluid(2000))
                .fluidInputs(Water.getFluid(2000))
                .fluidOutputs(Hydrogen.getFluid(1000))
                .fluidOutputs(HydrogenPeroxide.getFluid(1000))
                .fluidOutputs(AmmoniaBisulfate.getFluid(2000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(60)
                .circuitMeta(1)
                .fluidInputs(AmmoniaBisulfate.getFluid(1000))
                .fluidInputs(Ammonia.getFluid(1000))
                .fluidOutputs(AmmoniumSulfate.getFluid(1000))
                .buildAndRegister();


        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .circuitMeta(1)
                .fluidInputs(Ethanol.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .fluidOutputs(HydrogenPeroxide.getFluid(1000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .notConsumable(plate, Steel, 1)
                .circuitMeta(1)
                .fluidInputs(Ethanol.getFluid(1000))
                .fluidInputs(Air.getFluid(1000))
                .output(dust, Ash, 2)
                .fluidOutputs(Hydrogen.getFluid(4000))
                .fluidOutputs(HydrogenPeroxide.getFluid(500))
                .buildAndRegister();

        //含杂预处理
        OIL_POOL.recipeBuilder()
                .fluidInputs(RawOil.getFluid(1000))
                .fluidInputs(Demulsifier.getFluid(20))
                .fluidOutputs(PreTreatedCrudeOilContainingImpurities.getFluid(1000))
                .fluidOutputs(SaltWater.getFluid(200))
                .duration(100)
                .buildAndRegister();

        OIL_POOL.recipeBuilder()
                .fluidInputs(OilHeavy.getFluid(1000))
                .fluidInputs(Demulsifier.getFluid(20))
                .fluidOutputs(PreTreatedCrudeOilContainingImpurities.getFluid(1000))
                .fluidOutputs(SaltWater.getFluid(200))
                .duration(100)
                .buildAndRegister();

        OIL_POOL.recipeBuilder()
                .fluidInputs(OilLight.getFluid(1000))
                .fluidInputs(Demulsifier.getFluid(20))
                .fluidOutputs(PreTreatedCrudeOilContainingImpurities.getFluid(1000))
                .fluidOutputs(SaltWater.getFluid(200))
                .duration(100)
                .buildAndRegister();

        OIL_POOL.recipeBuilder()
                .fluidInputs(Oil.getFluid(1000))
                .fluidInputs(Demulsifier.getFluid(20))
                .fluidOutputs(PreTreatedCrudeOilContainingImpurities.getFluid(1000))
                .fluidOutputs(SaltWater.getFluid(200))
                .duration(100)
                .buildAndRegister();


        //预处理
        ELEOIL.recipeBuilder()
                .fluidInputs(RawOil.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(1500))
                .fluidOutputs(SaltWater.getFluid(200))
                .duration(40)
                .EUt(120)
                .buildAndRegister();

        ELEOIL.recipeBuilder()
                .fluidInputs(OilHeavy.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(1500))
                .fluidOutputs(SaltWater.getFluid(200))
                .duration(40)
                .EUt(120)
                .buildAndRegister();

        ELEOIL.recipeBuilder()
                .fluidInputs(OilLight.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(1500))
                .fluidOutputs(SaltWater.getFluid(200))
                .duration(40)
                .EUt(120)
                .buildAndRegister();

        ELEOIL.recipeBuilder()
                .fluidInputs(Oil.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(1500))
                .fluidOutputs(SaltWater.getFluid(200))
                .duration(40)
                .EUt(120)
                .buildAndRegister();

        CLARIFIER.recipeBuilder()
                .fluidInputs(RawOil.getFluid(16000))
                .fluidInputs(Demulsifier.getFluid(400))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(16000))
                .fluidOutputs(SaltWater.getFluid(3200))
                .circuitMeta(1)
                .duration(1600)
                .buildAndRegister();

        CLARIFIER.recipeBuilder()
                .fluidInputs(OilHeavy.getFluid(16000))
                .fluidInputs(Demulsifier.getFluid(400))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(16000))
                .fluidOutputs(SaltWater.getFluid(3200))
                .circuitMeta(1)
                .duration(1600)
                .buildAndRegister();

        CLARIFIER.recipeBuilder()
                .fluidInputs(OilLight.getFluid(16000))
                .fluidInputs(Demulsifier.getFluid(400))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(16000))
                .fluidOutputs(SaltWater.getFluid(3200))
                .circuitMeta(1)
                .duration(1600)
                .buildAndRegister();

        CLARIFIER.recipeBuilder()
                .fluidInputs(Oil.getFluid(16000))
                .fluidInputs(Demulsifier.getFluid(400))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(16000))
                .fluidOutputs(SaltWater.getFluid(3200))
                .circuitMeta(1)
                .duration(1600)
                .buildAndRegister();

        CLARIFIER.recipeBuilder()
                .fluidInputs(RawOil.getFluid(16000))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(16000))
                .fluidOutputs(SaltWater.getFluid(3200))
                .circuitMeta(2)
                .duration(3200)
                .buildAndRegister();

        CLARIFIER.recipeBuilder()
                .fluidInputs(OilHeavy.getFluid(16000))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(16000))
                .fluidOutputs(SaltWater.getFluid(3200))
                .circuitMeta(2)
                .duration(3200)
                .buildAndRegister();

        CLARIFIER.recipeBuilder()
                .fluidInputs(OilLight.getFluid(16000))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(16000))
                .fluidOutputs(SaltWater.getFluid(3200))
                .circuitMeta(2)
                .duration(3200)
                .buildAndRegister();

        CLARIFIER.recipeBuilder()
                .fluidInputs(Oil.getFluid(16000))
                .fluidOutputs(PreTreatedCrudeOil.getFluid(16000))
                .fluidOutputs(SaltWater.getFluid(3200))
                .circuitMeta(2)
                .duration(3200)
                .buildAndRegister();
    }

    private static void Kettle() {
        //煤焦线
        PYROLYSIS_TOWER.recipeBuilder()
                .input(log, Wood, 64)
                .fluidInputs(Steam.getFluid(6000))
                .output(Items.COAL, 64, 1)
                .fluidOutputs(WoodTar.getFluid(1000))
                .fluidOutputs(WoodGas.getFluid(1000))
                .fluidOutputs(WoodVinegar.getFluid(2000))
                .fluidOutputs(Creosote.getFluid(3000))
                .duration(600).Heat(425)
                .buildAndRegister();

        PYROLYSIS_TOWER.recipeBuilder()
                .input(log, Wood, 64)
                .fluidInputs(Nitrogen.getFluid(6000))
                .output(gem, Coke, 64)
                .fluidOutputs(CharcoalByproducts.getFluid(3000))
                .fluidOutputs(WoodTar.getFluid(1000))
                .fluidOutputs(WoodGas.getFluid(1000))
                .fluidOutputs(WoodVinegar.getFluid(2000))
                .fluidOutputs(Creosote.getFluid(3000))
                .duration(600).Heat(500)
                .buildAndRegister();


        PYROLYSIS_TOWER.recipeBuilder()
                .input(gem, Coal, 10)
                .fluidInputs(Steam.getFluid(10000))
                .output(gem, Coke, 10)
                .fluidOutputs(Ethylene.getFluid(1000))
                .fluidOutputs(Methanol.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(1000))
                .fluidOutputs(Nitrogen.getFluid(1000))
                .fluidOutputs(CarbonDioxide.getFluid(2000))
                .duration(400).Heat(425)
                .buildAndRegister();

        PYROLYSIS_TOWER.recipeBuilder()
                .input(gem, Coal, 10)
                .fluidInputs(Nitrogen.getFluid(12000))
                .output(gem, Coke, 10)
                .fluidOutputs(CharcoalByproducts.getFluid(3000))
                .fluidOutputs(Ethylene.getFluid(1000))
                .fluidOutputs(Methanol.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(1000))
                .fluidOutputs(Nitrogen.getFluid(1000))
                .fluidOutputs(CarbonDioxide.getFluid(2000))
                .duration(400).Heat(500)
                .buildAndRegister();

        //原始处理
        DISTILLATION_KETTLE.recipeBuilder()
                .fluidInputs(Oil.getFluid(1000))
                .fluidOutputs(Distilledgasoline.getFluid(500))
                .fluidOutputs(GasOil.getFluid(300))
                .fluidOutputs(OilHeavy.getFluid(200))
                .duration(200).Heat(425)
                .buildAndRegister();

        DISTILLATION_KETTLE.recipeBuilder()
                .fluidInputs(OilLight.getFluid(1000))
                .fluidOutputs(Distilledgasoline.getFluid(500))
                .fluidOutputs(GasOil.getFluid(300))
                .fluidOutputs(OilHeavy.getFluid(200))
                .duration(200).Heat(425)
                .buildAndRegister();

        DISTILLATION_KETTLE.recipeBuilder()
                .fluidInputs(OilHeavy.getFluid(1000))
                .fluidOutputs(Distilledgasoline.getFluid(500))
                .fluidOutputs(GasOil.getFluid(300))
                .fluidOutputs(OilHeavy.getFluid(200))
                .duration(200).Heat(425)
                .buildAndRegister();

        DISTILLATION_KETTLE.recipeBuilder()
                .fluidInputs(RawOil.getFluid(1000))
                .fluidOutputs(Distilledgasoline.getFluid(500))
                .fluidOutputs(GasOil.getFluid(300))
                .fluidOutputs(OilHeavy.getFluid(200))
                .duration(200).Heat(425)
                .buildAndRegister();

        //粗柴油
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(GasOil.getFluid(2000))
                .fluidOutputs(SDieselLight.getFluid(800))
                .fluidOutputs(SDieselHeavy.getFluid(800))
                .fluidOutputs(SulfuricGas.getFluid(400))
                .fluidOutputs(Butane.getFluid(200))
                .fluidOutputs(Propene.getFluid(200))
                .fluidOutputs(Butene.getFluid(200))
                .duration(400).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(Distilledgasoline.getFluid(3000))
                .fluidOutputs(Methane.getFluid(500))
                .fluidOutputs(Ethanol.getFluid(500))
                .fluidOutputs(Propane.getFluid(500))
                .fluidOutputs(Butane.getFluid(500))
                .fluidOutputs(Propene.getFluid(500))
                .fluidOutputs(Butene.getFluid(500))
                .duration(300).EUt(120).buildAndRegister();

        //煤油
        DISTILLATION_KETTLE.recipeBuilder()
                .fluidInputs(CoalTar.getFluid(2000))
                .fluidOutputs(Naphthalene.getFluid(200))
                .fluidOutputs(HydrogenSulfide.getFluid(200))
                .fluidOutputs(HighlyPurifiedCoalTar.getFluid(100))
                .fluidOutputs(Creosote.getFluid(100))
                .fluidOutputs(Phenol.getFluid(100))
                .duration(400).Heat(425)
                .buildAndRegister();

        //煤油
        SFM.recipeBuilder()
                .fluidInputs(CoalTar.getFluid(2000))
                .fluidOutputs(Naphthalene.getFluid(600))
                .fluidOutputs(HydrogenSulfide.getFluid(500))
                .fluidOutputs(HighlyPurifiedCoalTar.getFluid(400))
                .fluidOutputs(Creosote.getFluid(300))
                .fluidOutputs(Phenol.getFluid(200))
                .duration(80).EUt(120)
                .buildAndRegister();

    }

    private static void atmosphericAndVacuumPressure() {
        //含杂预处理
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(PreTreatedCrudeOilContainingImpurities.getFluid(4000))
                .chancedOutput(dust, Ash, 2500, 0)
                .fluidOutputs(Distilledgasoline.getFluid(600))
                .fluidOutputs(HighlyPurifiedCoalTar.getFluid(500))
                .fluidOutputs(SulfuricLightFuel.getFluid(300))
                .fluidOutputs(SulfuricHeavyFuel.getFluid(300))
                .fluidOutputs(AtmosphericResidue.getFluid(300))
                .duration(80).EUt(120)
                .buildAndRegister();

        //预处理
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(PreTreatedCrudeOil.getFluid(3000))
                .chancedOutput(dust, Ash, 2500, 0)
                .fluidOutputs(Distilledgasoline.getFluid(1200))
                .fluidOutputs(HighlyPurifiedCoalTar.getFluid(1000))
                .fluidOutputs(SDieselLight.getFluid(600))
                .fluidOutputs(SDieselHeavy.getFluid(600))
                .fluidOutputs(SulfuricLightFuel.getFluid(500))
                .fluidOutputs(SulfuricHeavyFuel.getFluid(500))
                .fluidOutputs(AtmosphericResidue.getFluid(400))
                .duration(80).EUt(120)
                .buildAndRegister();


        //减压部分
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(AtmosphericResidue.getFluid(5000))
                .chancedOutput(dust, Ash, 5000, 0)
                .fluidOutputs(SulfuricNaphtha.getFluid(600))
                .fluidOutputs(SulfuricLightFuel.getFluid(800))
                .fluidOutputs(SulfuricHeavyFuel.getFluid(800))
                .fluidOutputs(Asphalt.getFluid(800))
                .fluidOutputs(GasOil.getFluid(600))
                .fluidOutputs(WaxOil.getFluid(600))
                .fluidOutputs(VacuumResidue.getFluid(600))
                .fluidOutputs(Lubricant.getFluid(400))
                .duration(60).EUt(120)
                .buildAndRegister();

        //
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(SDieselHeavy.getFluid(8000))
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidOutputs(HydrogenSulfide.getFluid(1000))
                .fluidOutputs(DieselHeavy.getFluid(8000))
                .duration(160).EUt(30).buildAndRegister();
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(SDieselLight.getFluid(8000))
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidOutputs(HydrogenSulfide.getFluid(1000))
                .fluidOutputs(DieselLight.getFluid(8000))
                .duration(160).EUt(30).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(DieselLight.getFluid(4000))
                .fluidInputs(DieselHeavy.getFluid(4000))
                .fluidInputs(MTBE.getFluid(500))
                .fluidOutputs(Diesel.getFluid(8000))
                .duration(160).EUt(30).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Butene.getFluid(1000))
                .fluidInputs(Methanol.getFluid(1000))
                .fluidOutputs(MTBE.getFluid(2000))
                .duration(160).EUt(30).buildAndRegister();


        //燃油燃烧
        COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(LightFuel.getFluid(1))
                .fluidOutputs(HighTemperatureGas.getFluid(400))
                .duration(8)
                .EUt(32)
                .buildAndRegister();

        COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(HeavyFuel.getFluid(1))
                .fluidOutputs(HighTemperatureGas.getFluid(400))
                .duration(8)
                .EUt(32)
                .buildAndRegister();

        //沥青
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Coal)
                .fluidInputs(Concrete.getFluid(144))
                .fluidInputs(Asphalt.getFluid(144))
                .outputs(MetaBlocks.ASPHALT.getItemVariant(BlockAsphalt.BlockType.ASPHALT))
                .EUt(30)
                .duration(200)
                .buildAndRegister();

        //油气处理
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(OilGas.getFluid(1000))
                .fluidOutputs(RefineryGas.getFluid(4000))
                .EUt(30)
                .duration(200)
                .buildAndRegister();
    }

    private static void catalyticCracking() {
        lightlyCrack(DieselLight, LightlyHydroCrackedDieselLight, LightlySteamCrackedDieselLight);
        severelyCrack(DieselLight, SeverelyHydroCrackedDieselLight, SeverelySteamCrackedDieselLight);

        lightlyCrack(DieselHeavy, LightlyHydroCrackedDieselHeavy, LightlySteamCrackedDieselHeavy);
        severelyCrack(DieselHeavy, SeverelyHydroCrackedDieselHeavy, SeverelySteamCrackedDieselHeavy);

        //蒸汽
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(LightlySteamCrackedDieselLight.getFluid(800))
                .chancedOutput(dust, Carbon, 1111, 0)
                .fluidOutputs(DieselLight.getFluid(240))
                .fluidOutputs(Naphtha.getFluid(50))
                .fluidOutputs(Toluene.getFluid(25))
                .fluidOutputs(Benzene.getFluid(125))
                .fluidOutputs(Butene.getFluid(25))
                .fluidOutputs(Butadiene.getFluid(15))
                .fluidOutputs(Propane.getFluid(3))
                .fluidOutputs(Propene.getFluid(30))
                .fluidOutputs(Ethane.getFluid(5))
                .fluidOutputs(Ethylene.getFluid(50))
                .fluidOutputs(Acetylene.getFluid(50))
                .fluidOutputs(Dimethylbenzene.getFluid(50))
                .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(SeverelySteamCrackedDieselLight.getFluid(800))
                .chancedOutput(dust, Carbon, 3333, 0)
                .fluidOutputs(DieselLight.getFluid(80))
                .fluidOutputs(Naphtha.getFluid(125))
                .fluidOutputs(Toluene.getFluid(80))
                .fluidOutputs(Benzene.getFluid(400))
                .fluidOutputs(Butene.getFluid(80))
                .fluidOutputs(Butadiene.getFluid(50))
                .fluidOutputs(Propane.getFluid(10))
                .fluidOutputs(Propene.getFluid(100))
                .fluidOutputs(Ethane.getFluid(15))
                .fluidOutputs(Ethylene.getFluid(150))
                .fluidOutputs(Acetylene.getFluid(150))
                .fluidOutputs(Dimethylbenzene.getFluid(150))
                .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(LightlySteamCrackedDieselHeavy.getFluid(800))
                .chancedOutput(dust, Carbon, 1111, 0)
                .fluidOutputs(DieselHeavy.getFluid(240))
                .fluidOutputs(Naphtha.getFluid(50))
                .fluidOutputs(Toluene.getFluid(25))
                .fluidOutputs(Benzene.getFluid(125))
                .fluidOutputs(Butene.getFluid(25))
                .fluidOutputs(Butadiene.getFluid(15))
                .fluidOutputs(Propane.getFluid(3))
                .fluidOutputs(Propene.getFluid(30))
                .fluidOutputs(Ethane.getFluid(5))
                .fluidOutputs(Ethylene.getFluid(50))
                .fluidOutputs(Acetylene.getFluid(50))
                .fluidOutputs(Dimethylbenzene.getFluid(50))
                .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(SeverelySteamCrackedDieselHeavy.getFluid(800))
                .chancedOutput(dust, Carbon, 3333, 0)
                .fluidOutputs(DieselHeavy.getFluid(80))
                .fluidOutputs(Naphtha.getFluid(125))
                .fluidOutputs(Toluene.getFluid(80))
                .fluidOutputs(Benzene.getFluid(400))
                .fluidOutputs(Butene.getFluid(80))
                .fluidOutputs(Butadiene.getFluid(50))
                .fluidOutputs(Propane.getFluid(10))
                .fluidOutputs(Propene.getFluid(100))
                .fluidOutputs(Ethane.getFluid(15))
                .fluidOutputs(Ethylene.getFluid(150))
                .fluidOutputs(Acetylene.getFluid(150))
                .fluidOutputs(Dimethylbenzene.getFluid(150))
                .duration(120).EUt(120).buildAndRegister();


        //氢气
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(LightlyHydroCrackedDieselLight.getFluid(800))
                .fluidOutputs(DieselLight.getFluid(480))
                .fluidOutputs(Naphtha.getFluid(100))
                .fluidOutputs(Butane.getFluid(100))
                .fluidOutputs(Propane.getFluid(100))
                .fluidOutputs(Ethane.getFluid(75))
                .fluidOutputs(Methane.getFluid(75))
                .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(SeverelyHydroCrackedDieselLight.getFluid(800))
                .fluidOutputs(DieselLight.getFluid(160))
                .fluidOutputs(Naphtha.getFluid(250))
                .fluidOutputs(Butane.getFluid(300))
                .fluidOutputs(Propane.getFluid(300))
                .fluidOutputs(Ethane.getFluid(175))
                .fluidOutputs(Methane.getFluid(175))
                .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(LightlyHydroCrackedDieselHeavy.getFluid(800))
                .fluidOutputs(DieselHeavy.getFluid(480))
                .fluidOutputs(Naphtha.getFluid(100))
                .fluidOutputs(Butane.getFluid(100))
                .fluidOutputs(Propane.getFluid(100))
                .fluidOutputs(Ethane.getFluid(75))
                .fluidOutputs(Methane.getFluid(75))
                .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(SeverelyHydroCrackedDieselHeavy.getFluid(800))
                .fluidOutputs(DieselHeavy.getFluid(160))
                .fluidOutputs(Naphtha.getFluid(250))
                .fluidOutputs(Butane.getFluid(300))
                .fluidOutputs(Propane.getFluid(300))
                .fluidOutputs(Ethane.getFluid(175))
                .fluidOutputs(Methane.getFluid(175))
                .duration(120).EUt(120).buildAndRegister();

        //燃油裂化补充配方
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(LightFuel.getFluid(1000))
                .fluidInputs(Steam.getFluid(4000))
                .fluidOutputs(LightlySteamCrackedLightFuel.getFluid(500))
                .circuitMeta(1)
                .duration(120).EUt(30).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(HeavyFuel.getFluid(1000))
                .fluidInputs(Steam.getFluid(4000))
                .fluidOutputs(LightlySteamCrackedHeavyFuel.getFluid(500))
                .circuitMeta(1)
                .duration(120).EUt(30).buildAndRegister();
    }

    private static void lightlyCrack(Material raw, Material hydroCracked, Material steamCracked) {
        CRACKING_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .fluidInputs(raw.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidOutputs(hydroCracked.getFluid(1000))
                .duration(80).EUt(120).buildAndRegister();


        CRACKING_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .fluidInputs(raw.getFluid(1000))
                .fluidInputs(Steam.getFluid(1000))
                .fluidOutputs(steamCracked.getFluid(1000))
                .duration(80).EUt(240).buildAndRegister();

    }

    private static void severelyCrack(Material raw, Material hydroCracked, Material steamCracked) {
        CRACKING_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .fluidInputs(raw.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(6000))
                .fluidOutputs(hydroCracked.getFluid(1000))
                .duration(160).EUt(240).buildAndRegister();


        CRACKING_RECIPES.recipeBuilder()
                .circuitMeta(3)
                .fluidInputs(raw.getFluid(1000))
                .fluidInputs(Steam.getFluid(1000))
                .fluidOutputs(steamCracked.getFluid(1000))
                .duration(160).EUt(480).buildAndRegister();

    }
}
