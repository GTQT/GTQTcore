package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.unification.material.Material;
import gregtech.common.blocks.BlockAsphalt;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregicality.science.api.unification.materials.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtechfoodoption.GTFOMaterialHandler.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.Trichlorosilane;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.electrode;

public class OilChains {
    public static void init() {
        Pre();          //预处理
        Kettle();       //原始蒸馏
        changjianya();  //常减压
        cuihualiehua(); //催化裂化
    }

    private static void Pre()
    {
        //破乳剂
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .circuitMeta(1)
                .fluidInputs(Stearin.getFluid(1000))
                .fluidInputs(EthyleneOxide.getFluid(1000))
                .fluidInputs(PropyleneOxide.getFluid(1000))
                .fluidOutputs(Demulsifier.getFluid(3000))
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
                .input(dust,Quicklime)
                .fluidInputs(EthyleneChlorohydrin.getFluid(1000))
                .fluidOutputs(EthyleneOxide.getFluid(1000))
                .output(dust,Calcium)
                .buildAndRegister();
        //环氧丙烷
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .circuitMeta(1)
                .input(dust,Quicklime)
                .fluidInputs(Propene.getFluid(1000))
                .fluidInputs(HydrogenPeroxide.getFluid(1000))
                .fluidOutputs(PropyleneOxide.getFluid(1000))
                .output(dust,Calcium)
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
                .notConsumable(electrode,Steel,1)
                .circuitMeta(1)
                .fluidInputs(AmmoniumSulfate.getFluid(2000))
                .fluidInputs(Water.getFluid(2000))
                .fluidOutputs(Hydrogen.getFluid(1000))
                .fluidOutputs(HydrogenPeroxide.getFluid(1000))
                .fluidOutputs(AmmoniumSulfate.getFluid(2000))
                .buildAndRegister();

        //含杂预处理
        OIL_POOL.recipeBuilder()
                .fluidInputs(RawOil.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOilContainingImpurities.getFluid(1000))
                .fluidOutputs(SaltWater.getFluid(2000))
                .duration(1000)
                .buildAndRegister();

        OIL_POOL.recipeBuilder()
                .fluidInputs(OilHeavy.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOilContainingImpurities.getFluid(1000))
                .fluidOutputs(SaltWater.getFluid(2000))
                .duration(1000)
                .buildAndRegister();

        OIL_POOL.recipeBuilder()
                .fluidInputs(OilLight.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOilContainingImpurities.getFluid(1000))
                .fluidOutputs(SaltWater.getFluid(2000))
                .duration(1000)
                .buildAndRegister();

        OIL_POOL.recipeBuilder()
                .fluidInputs(Oil.getFluid(1000))
                .fluidOutputs(PreTreatedCrudeOilContainingImpurities.getFluid(1000))
                .fluidOutputs(SaltWater.getFluid(2000))
                .duration(1000)
                .buildAndRegister();

        //预处理
    }
    private static void Kettle()
    {

        //煤焦线
        DISTILLATION_KETTLE.recipeBuilder()
                .input(log, Wood, 64)
                .outputs(new ItemStack(Items.COAL, 48, 1))
                .fluidOutputs(WoodTar.getFluid(1000))
                .fluidOutputs(WoodGas.getFluid(1000))
                .fluidOutputs(WoodVinegar.getFluid(2000))
                .fluidOutputs(Creosote.getFluid(3000))
                .duration(2000).EUt(120)
                .buildAndRegister();

        DISTILLATION_KETTLE.recipeBuilder()
                .input(log, Wood, 80)
                .fluidInputs(Nitrogen.getFluid(6000))
                .output(gem, Coke, 48)
                .fluidOutputs(CharcoalByproducts.getFluid(3000))
                .fluidOutputs(WoodTar.getFluid(1000))
                .fluidOutputs(WoodGas.getFluid(1000))
                .fluidOutputs(WoodVinegar.getFluid(2000))
                .fluidOutputs(Creosote.getFluid(3000))
                .duration(2000).EUt(120)
                .buildAndRegister();


        DISTILLATION_KETTLE.recipeBuilder()
                .fluidInputs(Steam.getFluid(10000))
                .input(gem, Coal,10)
                .fluidOutputs(Water.getFluid(3000))
                .fluidOutputs(Ethylene.getFluid(1000))
                .fluidOutputs(Methanol.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(1000))
                .fluidOutputs(Nitrogen.getFluid(1000))
                .fluidOutputs(CarbonDioxide.getFluid(2000))
                .duration(8000).EUt(60).buildAndRegister();

        DISTILLATION_KETTLE.recipeBuilder()
                .fluidInputs(FermentedBiomass.getFluid(2000))
                .output(MetaItems.FERTILIZER)
                .fluidOutputs(Water.getFluid(300))
                .fluidOutputs(Ethanol.getFluid(100))
                .fluidOutputs(Methanol.getFluid(100))
                .fluidOutputs(Ammonia.getFluid(50))
                .fluidOutputs(CarbonDioxide.getFluid(300))
                .fluidOutputs(Methane.getFluid(300))
                .duration(400).EUt(60).buildAndRegister();

        DISTILLATION_KETTLE.recipeBuilder()
                .fluidInputs(Biomass.getFluid(1000))
                .output(MetaItems.FERTILIZER)
                .fluidOutputs(Water.getFluid(200))
                .fluidOutputs(Ethanol.getFluid(100))
                .duration(200).EUt(60).buildAndRegister();

        DISTILLATION_KETTLE.recipeBuilder()
                .fluidInputs(Oil.getFluid(1000))
                .fluidOutputs(Distilledgasoline.getFluid(500))
                .fluidOutputs(GasOil.getFluid(300))
                .fluidOutputs(OilHeavy.getFluid(200))
                .duration(200).EUt(60).buildAndRegister();

        DISTILLATION_KETTLE.recipeBuilder()
                .fluidInputs(OilLight.getFluid(1000))
                .fluidOutputs(Distilledgasoline.getFluid(500))
                .fluidOutputs(GasOil.getFluid(300))
                .fluidOutputs(OilHeavy.getFluid(200))
                .duration(200).EUt(60).buildAndRegister();

        DISTILLATION_KETTLE.recipeBuilder()
                .fluidInputs(OilHeavy.getFluid(1000))
                .fluidOutputs(Distilledgasoline.getFluid(500))
                .fluidOutputs(GasOil.getFluid(300))
                .fluidOutputs(OilHeavy.getFluid(200))
                .duration(200).EUt(60).buildAndRegister();

        DISTILLATION_KETTLE.recipeBuilder()
                .fluidInputs(RawOil.getFluid(1000))
                .fluidOutputs(Distilledgasoline.getFluid(500))
                .fluidOutputs(GasOil.getFluid(300))
                .fluidOutputs(OilHeavy.getFluid(200))
                .duration(200).EUt(60).buildAndRegister();

        //粗柴油
        DISTILLATION_KETTLE.recipeBuilder()
                .fluidInputs(GasOil.getFluid(2000))
                .fluidOutputs(WaxOil.getFluid(800))
                .fluidOutputs(SDieselLight.getFluid(500))
                .fluidOutputs(SDieselHeavy.getFluid(500))
                .fluidOutputs(SulfuricGas.getFluid(200))
                .duration(200).EUt(60).buildAndRegister();


        DISTILLATION_KETTLE.recipeBuilder()
                .fluidInputs(Distilledgasoline.getFluid(2000))
                .notConsumable(dust,ZincCl)
                .fluidOutputs(Methane.getFluid(200))
                .fluidOutputs(Ethanol.getFluid(200))
                .fluidOutputs(Propane.getFluid(200))
                .fluidOutputs(Butane.getFluid(200))
                .fluidOutputs(Propene.getFluid(200))
                .fluidOutputs(Butene.getFluid(200))
                .duration(400).EUt(60).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(Distilledgasoline.getFluid(3000))
                .notConsumable(dust,ZincCl)
                .fluidOutputs(Methane.getFluid(500))
                .fluidOutputs(Ethanol.getFluid(500))
                .fluidOutputs(Propane.getFluid(500))
                .fluidOutputs(Butane.getFluid(500))
                .fluidOutputs(Propene.getFluid(500))
                .fluidOutputs(Butene.getFluid(500))
                .duration(300).EUt(60).buildAndRegister();
        //煤油
        DISTILLATION_KETTLE.recipeBuilder()
                .fluidInputs(CoalTar.getFluid(2000))
                .fluidOutputs(Naphthalene.getFluid(200))
                .fluidOutputs(HydrogenSulfide.getFluid(200))
                .fluidOutputs(HighlyPurifiedCoalTar.getFluid(100))
                .fluidOutputs(Creosote.getFluid(100))
                .fluidOutputs(Phenol.getFluid(100))
                .duration(400).EUt(60).buildAndRegister();

        //煤油
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(CoalTar.getFluid(2000))
                .chancedOutput(dust, Coke, 2500, 0)
                .fluidOutputs(Naphthalene.getFluid(600))
                .fluidOutputs(HydrogenSulfide.getFluid(500))
                .fluidOutputs(HighlyPurifiedCoalTar.getFluid(400))
                .fluidOutputs(Creosote.getFluid(300))
                .fluidOutputs(Phenol.getFluid(200))
                .duration(80).EUt(VA[MV])
                .buildAndRegister();

        PYROLYSIS_TOWER.recipeBuilder()
                .input(log, Wood, 64)
                .fluidInputs(Steam.getFluid(6000))
                .outputs(new ItemStack(Items.COAL, 64, 1))
                .fluidOutputs(WoodTar.getFluid(1500))
                .fluidOutputs(WoodGas.getFluid(1500))
                .fluidOutputs(WoodVinegar.getFluid(3000))
                .fluidOutputs(Creosote.getFluid(4000))
                .duration(2400).EUt(120)
                .buildAndRegister();

        PYROLYSIS_TOWER.recipeBuilder()
                .input(log, Wood, 80)
                .fluidInputs(Nitrogen.getFluid(6000))
                .output(gem, Coke, 80)
                .fluidOutputs(CharcoalByproducts.getFluid(4000))
                .fluidOutputs(WoodTar.getFluid(1500))
                .fluidOutputs(WoodGas.getFluid(1500))
                .fluidOutputs(WoodVinegar.getFluid(3000))
                .fluidOutputs(Creosote.getFluid(4000))
                .duration(2400).EUt(120)
                .buildAndRegister();
    }
    private static void changjianya()
    {

        //没处理的
        PYROLYSIS_TOWER.recipeBuilder()
                .fluidInputs(RawOil.getFluid(4000))
                .chancedOutput(dust, Ash, 1000, 0)
                .fluidOutputs(Distilledgasoline.getFluid(400))
                .fluidOutputs(HighlyPurifiedCoalTar.getFluid(250))
                .fluidOutputs(SulfuricLightFuel.getFluid(150))
                .fluidOutputs(SulfuricHeavyFuel.getFluid(150))
                .fluidOutputs(AtmosphericResidue.getFluid(50))
                .duration(400).EUt(120)
                .buildAndRegister();

        PYROLYSIS_TOWER.recipeBuilder()
                .fluidInputs(OilHeavy.getFluid(4000))
                .chancedOutput(dust, Ash, 2500, 0)
                .fluidOutputs(Distilledgasoline.getFluid(300))
                .fluidOutputs(HighlyPurifiedCoalTar.getFluid(250))
                .fluidOutputs(SulfuricLightFuel.getFluid(100))
                .fluidOutputs(SulfuricHeavyFuel.getFluid(200))
                .fluidOutputs(AtmosphericResidue.getFluid(150))
                .duration(400).EUt(120)
                .buildAndRegister();

        PYROLYSIS_TOWER.recipeBuilder()
                .fluidInputs(OilLight.getFluid(4000))
                .chancedOutput(dust, Ash, 2500, 0)
                .fluidOutputs(Distilledgasoline.getFluid(300))
                .fluidOutputs(HighlyPurifiedCoalTar.getFluid(250))
                .fluidOutputs(SulfuricLightFuel.getFluid(200))
                .fluidOutputs(SulfuricHeavyFuel.getFluid(100))
                .fluidOutputs(AtmosphericResidue.getFluid(150))
                .duration(400).EUt(120)
                .buildAndRegister();

        PYROLYSIS_TOWER.recipeBuilder()
                .fluidInputs(Oil.getFluid(4000))
                .chancedOutput(dust, Ash, 2500, 0)
                .fluidOutputs(Distilledgasoline.getFluid(250))
                .fluidOutputs(HighlyPurifiedCoalTar.getFluid(300))
                .fluidOutputs(SulfuricLightFuel.getFluid(150))
                .fluidOutputs(SulfuricHeavyFuel.getFluid(150))
                .fluidOutputs(AtmosphericResidue.getFluid(150))
                .duration(400).EUt(120)
                .buildAndRegister();

        //含杂预处理
        PYROLYSIS_TOWER.recipeBuilder()
                .fluidInputs(PreTreatedCrudeOilContainingImpurities.getFluid(4000))
                .chancedOutput(dust, Ash, 2500, 0)
                .fluidOutputs(Distilledgasoline.getFluid(600))
                .fluidOutputs(HighlyPurifiedCoalTar.getFluid(500))
                .fluidOutputs(SulfuricLightFuel.getFluid(300))
                .fluidOutputs(SulfuricHeavyFuel.getFluid(300))
                .fluidOutputs(AtmosphericResidue.getFluid(300))
                .duration(400).EUt(120)
                .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(PreTreatedCrudeOilContainingImpurities.getFluid(4000))
                .chancedOutput(dust, Ash, 2500, 0)
                .fluidOutputs(Distilledgasoline.getFluid(600))
                .fluidOutputs(HighlyPurifiedCoalTar.getFluid(500))
                .fluidOutputs(SulfuricLightFuel.getFluid(300))
                .fluidOutputs(SulfuricHeavyFuel.getFluid(300))
                .fluidOutputs(AtmosphericResidue.getFluid(300))
                .duration(200).EUt(120)
                .buildAndRegister();
        //预处理
        PYROLYSIS_TOWER.recipeBuilder()
                .fluidInputs(PreTreatedCrudeOil.getFluid(2000))
                .chancedOutput(dust, Ash, 2500, 0)
                .fluidOutputs(Distilledgasoline.getFluid(600))
                .fluidOutputs(HighlyPurifiedCoalTar.getFluid(500))
                .fluidOutputs(SDieselLight.getFluid(300))
                .fluidOutputs(SDieselHeavy.getFluid(300))
                .fluidOutputs(AtmosphericResidue.getFluid(300))
                .duration(400).EUt(120)
                .buildAndRegister();

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
                .duration(100).EUt(120)
                .buildAndRegister();


        //减压部分
        PYROLYSIS_TOWER.recipeBuilder()
                .fluidInputs(AtmosphericResidue.getFluid(5000))
                .chancedOutput(dust, Ash, 5000, 0)
                .fluidOutputs(SulfuricNaphtha.getFluid(600))
                .fluidOutputs(SulfuricLightFuel.getFluid(800))
                .fluidOutputs(SulfuricHeavyFuel.getFluid(800))
                .fluidOutputs(GasOil.getFluid(600))
                .fluidOutputs(WaxOil.getFluid(600))
                .fluidOutputs(Asphalt.getFluid(600))
                .fluidOutputs(VacuumResidue.getFluid(600))
                .fluidOutputs(Lubricant.getFluid(400))
                .duration(200).EUt(120)
                .buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(AtmosphericResidue.getFluid(5000))
                .chancedOutput(dust, Ash, 5000, 0)
                .fluidOutputs(SulfuricNaphtha.getFluid(600))
                .fluidOutputs(SulfuricLightFuel.getFluid(800))
                .fluidOutputs(SulfuricHeavyFuel.getFluid(800))
                .fluidOutputs(GasOil.getFluid(600))
                .fluidOutputs(WaxOil.getFluid(600))
                .fluidOutputs(Asphalt.getFluid(600))
                .fluidOutputs(VacuumResidue.getFluid(600))
                .fluidOutputs(Lubricant.getFluid(400))
                .duration(100).EUt(120)
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
                .fluidInputs(DieselLight.getFluid(8000))
                .fluidInputs(DieselHeavy.getFluid(8000))
                .fluidInputs(MTBE.getFluid(1000))
                .fluidOutputs(Diesel.getFluid(160000))
                .duration(160).EUt(30).buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Butene.getFluid(1000))
                .fluidInputs(Methanol.getFluid(1000))
                .fluidOutputs(MTBE.getFluid(2000))
                .duration(160).EUt(30).buildAndRegister();


        //燃油燃烧
        COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(LightFuel.getFluid(1))
                .duration(8)
                .EUt(30)
                .buildAndRegister();

        COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(HeavyFuel.getFluid(1))
                .duration(8)
                .EUt(30)
                .buildAndRegister();

        //柴油燃烧
        COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(DieselLight.getFluid(1))
                .duration(9)
                .EUt(30)
                .buildAndRegister();

        COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(DieselHeavy.getFluid(1))
                .duration(9)
                .EUt(30)
                .buildAndRegister();

        //沥青
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust,Coal)
                .fluidInputs(Concrete.getFluid(144))
                .fluidInputs(Asphalt.getFluid(144))
                .outputs(MetaBlocks.ASPHALT.getItemVariant(BlockAsphalt.BlockType.ASPHALT))
                .EUt(30)
                .duration(200)
                .buildAndRegister();
    }
    private static void cuihualiehua()
    {
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(200)
                .EUt(30)
                .circuitMeta(1)
                .fluidInputs(AtmosphericResidue.getFluid(2000))
                .fluidInputs(WaxOil.getFluid(1000))
                .fluidOutputs(OilGas.getFluid(3000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(200)
                .EUt(30)
                .circuitMeta(1)
                .fluidInputs(VacuumResidue.getFluid(1000))
                .fluidInputs(WaxOil.getFluid(2000))
                .fluidOutputs(OilGas.getFluid(9000))
                .buildAndRegister();

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
                .duration(120).EUt(VA[MV]).buildAndRegister();

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
                .duration(120).EUt(VA[MV]).buildAndRegister();

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
                .duration(120).EUt(VA[MV]).buildAndRegister();

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
                .duration(120).EUt(VA[MV]).buildAndRegister();



        //氢气
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(LightlyHydroCrackedDieselLight.getFluid(800))
                .fluidOutputs(DieselLight.getFluid(480))
                .fluidOutputs(Naphtha.getFluid(100))
                .fluidOutputs(Butane.getFluid(100))
                .fluidOutputs(Propane.getFluid(100))
                .fluidOutputs(Ethane.getFluid(75))
                .fluidOutputs(Methane.getFluid(75))
                .duration(120).EUt(VA[MV]).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(SeverelyHydroCrackedDieselLight.getFluid(800))
                .fluidOutputs(DieselLight.getFluid(160))
                .fluidOutputs(Naphtha.getFluid(250))
                .fluidOutputs(Butane.getFluid(300))
                .fluidOutputs(Propane.getFluid(300))
                .fluidOutputs(Ethane.getFluid(175))
                .fluidOutputs(Methane.getFluid(175))
                .duration(120).EUt(VA[MV]).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(LightlyHydroCrackedDieselHeavy.getFluid(800))
                .fluidOutputs(DieselHeavy.getFluid(480))
                .fluidOutputs(Naphtha.getFluid(100))
                .fluidOutputs(Butane.getFluid(100))
                .fluidOutputs(Propane.getFluid(100))
                .fluidOutputs(Ethane.getFluid(75))
                .fluidOutputs(Methane.getFluid(75))
                .duration(120).EUt(VA[MV]).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(SeverelyHydroCrackedDieselHeavy.getFluid(800))
                .fluidOutputs(DieselHeavy.getFluid(160))
                .fluidOutputs(Naphtha.getFluid(250))
                .fluidOutputs(Butane.getFluid(300))
                .fluidOutputs(Propane.getFluid(300))
                .fluidOutputs(Ethane.getFluid(175))
                .fluidOutputs(Methane.getFluid(175))
                .duration(120).EUt(VA[MV]).buildAndRegister();
    }
    private static void lightlyCrack(Material raw, Material hydroCracked, Material steamCracked) {
        CRACKING_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .fluidInputs(raw.getFluid(1000))
                .fluidInputs(Hydrogen.getFluid(2000))
                .fluidOutputs(hydroCracked.getFluid(1000))
                .duration(80).EUt(VA[MV]).buildAndRegister();


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
                .duration(160).EUt(VA[HV]).buildAndRegister();

    }
}
