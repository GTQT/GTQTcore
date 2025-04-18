package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.GTValues;
import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.attribute.FluidAttributes;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.material.properties.BlastProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.MaterialToolProperty;
import keqing.gtqtcore.api.unification.GTQTMaterials;

import static gregicality.multiblocks.api.unification.GCYMMaterials.IncoloyMA956;
import static gregtech.api.GTValues.*;
import static gregtech.api.fluids.attribute.FluidAttributes.ACID;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static gregtech.api.unification.material.properties.BlastProperty.GasTier.*;
import static gregtechfoodoption.GTFOMaterialHandler.SodiumSulfate;
import static keqing.gtqtcore.api.GTQTValue.gtqtcoreId;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.material.info.GTQTMaterialFlags.DISABLE_CRYSTALLIZATION;

public class FirstDegreeMaterials {

    //第一类材料
    //通常为基本元素组成的化合物，其组分为已注册的单质

    private static int startId = 200;
    private static final int END_ID = startId + 1800;

    public FirstDegreeMaterials() {
    }

    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static void register() {
        SupercriticalSteam = new Material.Builder(getMaterialsId(), gtqtcoreId("supercritical_steam"))
                .gas(new FluidBuilder().temperature(873).customStill())
                .color(0xC4C4C4)
                .components(Water, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24074 Superheated Steam
        SuperheatedSteam = new Material.Builder(getMaterialsId(), gtqtcoreId("superheated_steam"))
                .gas(new FluidBuilder().temperature(573).customStill())
                .color(0xC4C4C)
                .components(Water, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.DenseHydrazineMixtureFuel = new Material.Builder(getMaterialsId(), gtqtcoreId("dense_hydrazine_mixture_fuel"))
                .fluid()
                .color(0x912565)
                .components(Dimethylhydrazine, 1, Methanol, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24324 Highly Purified Coal Tar
        HighlyPurifiedCoalTar = new Material.Builder(getMaterialsId(), gtqtcoreId("highly_purified_coal_tar"))
                .fluid()
                .color(0x7F811D)
                .components(CoalTar, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24325 RP-1 Rocket Fuel
        GTQTMaterials.RP1RocketFuel = new Material.Builder(getMaterialsId(), gtqtcoreId("rp_1_rocket_fuel"))
                .fluid()
                .color(0xFB2A08)
                .components(HighlyPurifiedCoalTar, 1, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24326 Methylhydrazine
        Methylhydrazine = new Material.Builder(getMaterialsId(), gtqtcoreId("methylhydrazine"))
                .fluid()
                .color(0x321452)
                .components(Carbon, 1, Hydrogen, 6, Nitrogen, 2)
                .build();

        //  24327 Methylhydrazine Nitrate Rocket Fuel
        GTQTMaterials.MethylhydrazineNitrateRocketFuel = new Material.Builder(getMaterialsId(), gtqtcoreId("methylhydrazine_nitrate_rocket_fuel"))
                .fluid()
                .color(0x607186)
                .components(Methylhydrazine, 1, Tetranitromethane, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.Alubrass = new Material.Builder(getMaterialsId(), gtqtcoreId("alubrass"))
                .fluid()
                .ingot()
                .color(0x321452)
                .flags(DISABLE_DECOMPOSITION)
                .components(Aluminium, 3, Copper, 1)
                .build();

        //氯化锌
        GTQTMaterials.ZincCl = new Material.Builder(getMaterialsId(), gtqtcoreId("zinc_cl"))
                .dust()
                .color(0xB2DFEE)
                .components(Zinc, 1, Chlorine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //氯乙醇
        GTQTMaterials.EthyleneChlorohydrin = new Material.Builder(getMaterialsId(), gtqtcoreId("ethylene_chlorohydrin"))
                .fluid()
                .color(0xB2DFEE)
                .components(Carbon, 2, Hydrogen, 6, Oxygen, 1, Chlorine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //硫酸铵
        GTQTMaterials.AmmoniumSulfate = new Material.Builder(getMaterialsId(), gtqtcoreId("ammonium_sulfate"))
                .fluid()
                .color(0xB2DFEE)
                .components(Nitrogen, 2, Hydrogen, 8, Sulfur, 1, Oxygen, 4)
                .build();

        GTQTMaterials.Polysilicon = new Material.Builder(getMaterialsId(), gtqtcoreId("polysilicon"))
                .ingot()
                .color(0x3C3C50).iconSet(METALLIC)
                .flags(GENERATE_FOIL)
                .element(gregtech.api.unification.Elements.Si)
                .blast(1800, LOW) // no gas tier for silicon
                .build();

        //氯化铜
        GTQTMaterials.CopperChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("copper_chloride"))
                .ingot()
                .color(0x8B4C39)
                .iconSet(METALLIC)
                .flags(GENERATE_FOIL)
                .components(Copper, 1, Chlorine, 2)
                .blast(2273)
                .build();

        //聚乙烯醇肉桂酸酯
        GTQTMaterials.Vinylcinnamate = new Material.Builder(getMaterialsId(), gtqtcoreId("vinylcinnamate"))
                .fluid()
                .color(0xFF8C00)
                .iconSet(SHINY)
                .build()
                .setFormula("C11H12O3");

        //感光树脂-聚乙烯醇肉桂酸酯

        //增感剂
        //光刻胶溶剂

        //xMT分子光刻
        GTQTMaterials.Xmt = new Material.Builder(getMaterialsId(), gtqtcoreId("xmt"))
                .fluid()
                .color(0x3C3C50)
                .iconSet(SHINY)
                .build();

        //xMT
        //环氧交联剂
        //光致酸产生剂(PAG)
        //亲核猝灭剂
        //碱猝灭剂


        //氧化锆杂化物-（2,4-双（三氯甲基）-6-（4-甲氧基苯乙烯基）-1,3,5-三嗪）（ZrO2-BTMST）
        GTQTMaterials.Zrbtmst = new Material.Builder(getMaterialsId(), gtqtcoreId("zrbtmst"))
                .fluid().dust()
                .color(0xB2DFEE).iconSet(METALLIC)
                .flags(GENERATE_FOIL)
                .build()
                .setFormula("ZrO2-BTMST");

        //氧化锆杂化物
        //（2,4-双（三氯甲基）-6-（4-甲氧基苯乙烯基）-1,3,5-三嗪）
        //（ZrO2-BTMST）

        //纳米钛酸钡
        GTQTMaterials.NanometerBariumTitanate = new Material.Builder(getMaterialsId(), gtqtcoreId("nanometer_barium_titanate"))
                .ingot()
                .color(0x8B6914).iconSet(METALLIC)
                .components(Barium, 1, Titanium, 1, Oxygen, 3)
                .flags(DISABLE_DECOMPOSITION)
                .blast(4500, MID)
                .flags(GENERATE_PLATE, GENERATE_DENSE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_FOIL)
                .build();

        //海绵钛
        GTQTMaterials.Htitanate = new Material.Builder(getMaterialsId(), gtqtcoreId("htitanate"))
                .ingot()
                .color(0x8B6914).iconSet(METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .components(Titanium, 1)
                .build();

        GTQTMaterials.Fluix = new Material.Builder(getMaterialsId(), gtqtcoreId("fluix"))
                .gem(1).ore()
                .color(0x7D26CD).iconSet(SHINY)
                .flags(GENERATE_PLATE, CRYSTALLIZABLE)
                .components(Silicon, 1, Oxygen, 2)
                .build();

        //eio
        GTQTMaterials.RedstoneAlloy = new Material.Builder(getMaterialsId(), gtqtcoreId("redstone_alloy"))
                .ingot().fluid()
                .color(0x943423).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(Redstone, 1, Hydrogen, 1, Oxygen, 1)
                .blast(2700, LOW)
                .fluidPipeProperties(500, 400, true, true, true, false)
                .cableProperties(32, 1, 2)
                .build();

        GTQTMaterials.PulsatingIron = new Material.Builder(getMaterialsId(), gtqtcoreId("pulsating_iron"))
                .ingot().fluid()
                .color(0x4b915b).iconSet(SHINY)
                .fluidPipeProperties(500, 400, true, true, true, false)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(EnderPearl, 1, Iron, 1, RedstoneAlloy, 1)
                .blast(2700, LOW)
                .build();

        GTQTMaterials.ConductiveIron = new Material.Builder(getMaterialsId(), gtqtcoreId("conductive_iron"))
                .ingot().fluid()
                .color(0xb89791).iconSet(SHINY)
                .fluidPipeProperties(500, 600, true, true, true, false)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(Silver, 1, Iron, 1, RedstoneAlloy, 1)
                .blast(2700, LOW)
                .build();

        GTQTMaterials.EnergeticAlloy = new Material.Builder(getMaterialsId(), gtqtcoreId("energetic_alloy"))
                .ingot().fluid()
                .color(0xd89045).iconSet(SHINY)
                .fluidPipeProperties(500, 800, true, true, true, false)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(BlackSteel, 1, Gold, 1, ConductiveIron, 1)
                .blast(2700, LOW)
                .cableProperties(512, 2, 4)
                .build();

        GTQTMaterials.VibrantAlloy = new Material.Builder(getMaterialsId(), gtqtcoreId("vibrant_alloy"))
                .ingot().fluid()
                .color(0x859f2d).iconSet(SHINY)
                .fluidPipeProperties(500, 1200, true, true, true, false)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(EnderEye, 1, EnergeticAlloy, 1, Chromite, 1)
                .blast(2700, LOW)
                .cableProperties(2048, 4, 4)
                .build();

        GTQTMaterials.Soularium = new Material.Builder(getMaterialsId(), gtqtcoreId("soularium"))
                .ingot().fluid()
                .color(0x372719).iconSet(SHINY)
                .fluidPipeProperties(500, 1200, true, true, true, false)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(Gold, 1, Carbon, 1)
                .blast(2700, LOW)
                .build();

        GTQTMaterials.ElectricalSteel = new Material.Builder(getMaterialsId(), gtqtcoreId("electrical_steel"))
                .ingot().fluid()
                .color(0x9d9d9d).iconSet(SHINY)
                .fluidPipeProperties(500, 900, true, true, true, false)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(Steel, 1, Coal, 1, Silicon, 1)
                .blast(2700, LOW)
                .rotorStats(9.0f, 5.0f, 3000)
                .toolStats(MaterialToolProperty.Builder.of(7.0F, 6.0F, 28000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .cableProperties(128, 2, 2)
                .build();

        GTQTMaterials.DarkSteel = new Material.Builder(getMaterialsId(), gtqtcoreId("dark_steel"))
                .ingot().fluid()
                .color(0x2f292f).iconSet(SHINY)
                .fluidPipeProperties(500, 600, true, true, true, false)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(ElectricalSteel, 1, Coal, 1, Obsidian, 1)
                .rotorStats(12.0f, 6.0f, 4500)
                .toolStats(MaterialToolProperty.Builder.of(7.0F, 6.0F, 32000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .blast(3600, MID)
                .build();

        GTQTMaterials.EndSteel = new Material.Builder(getMaterialsId(), gtqtcoreId("end_steel"))
                .ingot().fluid()
                .color(0xbdb88c).iconSet(SHINY)
                .fluidPipeProperties(500, 2400, true, true, true, false)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(DarkSteel, 1, Endstone, 1, TungstenSteel, 1)
                .rotorStats(15.0f, 7.0f, 6000)
                .toolStats(MaterialToolProperty.Builder.of(7.0F, 6.0F, 36000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .blast(4500, MID)
                .build();

        GTQTMaterials.CrystallineAlloy = new Material.Builder(getMaterialsId(), gtqtcoreId("crystalline_alloy"))
                .ingot().fluid()
                .color(0x8FE3F7).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(PulsatingIron, 1, Diamond, 1, Emerald, 1, Gold, 1)
                .blast(4500, MID)
                .build();

        GTQTMaterials.MelodicAlloy = new Material.Builder(getMaterialsId(), gtqtcoreId("melodic_alloy"))
                .ingot().fluid()
                .color(0xA877A8).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(EndSteel, 1, EnderEye, 1, Bismuth, 1)
                .fluidPipeProperties(500, 3600, true, true, true, false)
                .blast(5400, MID)
                .build();

        GTQTMaterials.StellarAlloy = new Material.Builder(getMaterialsId(), gtqtcoreId("stellar_alloy"))
                .ingot().fluid()
                .color(0xCCCCCC).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(MelodicAlloy, 1, NetherStar, 1, Naquadah, 1)
                .fluidPipeProperties(500, 4500, true, true, true, false)
                .blast(7200, MID)
                .rotorStats(15.0f, 7.0f, 6000)
                .toolStats(MaterialToolProperty.Builder.of(7.0F, 6.0F, 32000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .cableProperties(32768, 6, 8)
                .build();

        GTQTMaterials.CrystallinePinkSlime = new Material.Builder(getMaterialsId(), gtqtcoreId("crystalline_pink_slime"))
                .ingot().fluid()
                .color(0xE79EDB).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(CrystallineAlloy, 1, Diamond, 1, RawRubber, 1)
                .blast(5000, MID)
                .fluidPipeProperties(500, 6000, true, true, true, false)
                .rotorStats(15.0f, 7.0f, 4000)
                .toolStats(MaterialToolProperty.Builder.of(7.0F, 6.0F, 6000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .cableProperties(2048, 6, 2)
                .build();

        GTQTMaterials.EnergeticSilver = new Material.Builder(getMaterialsId(), gtqtcoreId("energetic_silver"))
                .ingot().fluid()
                .color(0x598DB3).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(Silver, 1, ConductiveIron, 1, BlackSteel, 1)
                .blast(2700, MID)
                .rotorStats(15.0f, 7.0f, 2000)
                .toolStats(MaterialToolProperty.Builder.of(7.0F, 6.0F, 8000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .cableProperties(512, 4, 2)
                .build();

        GTQTMaterials.VividAlloy = new Material.Builder(getMaterialsId(), gtqtcoreId("vivid_alloy"))
                .ingot().fluid()
                .color(0x469BB1).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(Chromite, 1, EnergeticSilver, 1, EnderEye, 1)
                .blast(3000, MID)
                .rotorStats(15.0f, 7.0f, 4000)
                .toolStats(MaterialToolProperty.Builder.of(7.0F, 6.0F, 12000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .cableProperties(2048, 4, 2)
                .build();

        GTQTMaterials.CrudeSteel = new Material.Builder(getMaterialsId(), gtqtcoreId("crude_steel"))
                .ingot().fluid()
                .color(0x807C74).iconSet(ROUGH)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .flags(DISABLE_DECOMPOSITION)
                .build();


        GTQTMaterials.Ealuminium = new Material.Builder(getMaterialsId(), gtqtcoreId("ealuminium"))
                .fluid().dust()
                .color(0xd89045).iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Aluminium, 1, Nitrogen, 3, Oxygen, 9)
                .build();

        //海带灰
        GTQTMaterials.KelpAsh = new Material.Builder(getMaterialsId(), gtqtcoreId("kelp_ash"))
                .dust()
                .color(0xd89045).iconSet(SHINY)
                .build();

        //Extract kelp ash（四氯化碳处理后
        GTQTMaterials.ExtractKelpAsh = new Material.Builder(getMaterialsId(), gtqtcoreId("extract_kelp_ash"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .build();

        //碘化钾
        GTQTMaterials.PotassiumIodide = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_iodide"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .components(Potassium, 1, Iodine, 1)
                .build();
        //溴化钾
        GTQTMaterials.PotassiumBromide = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_bromide"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .components(Potassium, 1, Bromine, 1)
                .build();

        //2-丁烯醛
        GTQTMaterials.Crotonaldehyde = new Material.Builder(getMaterialsId(), gtqtcoreId("crotonaldehyde"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .components(Carbon, 4, Hydrogen, 8, Oxygen, 1)
                .build();

        //2-乙基（-2-）己烯醛
        GTQTMaterials.Ethylhexenal = new Material.Builder(getMaterialsId(), gtqtcoreId("ethylhexenal"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .components(Carbon, 8, Hydrogen, 14, Oxygen, 1)
                .build();
        //草酸
        GTQTMaterials.EthanedioicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("ethanedioic_acid"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .components(Hydrogen, 2, Carbon, 2, Oxygen, 4)
                .build();

        //五氧化二钒
        GTQTMaterials.VanadiumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("vanadium_oxide"))
                .dust()
                .color(0xd89045).iconSet(SHINY)
                .components(Vanadium, 2, Oxygen, 5)
                .build();

        //SodiumSilicofluoride 氟硅酸钠
        GTQTMaterials.SodiumSilicofluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_silicofluoride"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .components(Sodium, 2, Silicon, 1, Fluorine, 6)
                .build();

        //Hexafluorosilicic acid 氟硅酸
        GTQTMaterials.HexafluorosilicicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("hexafluorosilicic_acid"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .components(Hydrogen, 2, Silicon, 1, Fluorine, 6)
                .build();

        //氯化铈
        GTQTMaterials.CeriumChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("cerium_chloride"))
                .dust()
                .color(0xF4F4F4).iconSet(SHINY)
                .components(Cerium, 1, Chlorine, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //草酸铈
        GTQTMaterials.CeriumOxalate = new Material.Builder(getMaterialsId(), gtqtcoreId("cerium_oxalate"))
                .dust()
                .color(0xF4A460).iconSet(SHINY)
                .components(Cerium, 1, Hydrogen, 2, Carbon, 2, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //Cerium
        GTQTMaterials.CeriumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("cerium_oxide"))
                .dust()
                .color(0xF2F2F2).iconSet(SHINY)
                .components(Cerium, 2, Oxygen, 3)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24147 Barium Carbonate
        GTQTMaterials.BariumCarbonate = new Material.Builder(getMaterialsId(), gtqtcoreId("barium_carbonate"))
                .dust()
                .color(0x425A73)
                .iconSet(ROUGH)
                .components(Barium, 1, Carbon, 1, Oxygen, 3)
                .build();

        //  24050 Palladium on Carbon
        GTQTMaterials.PalladiumOnCarbon = new Material.Builder(getMaterialsId(), gtqtcoreId("palladium_on_carbon"))
                .dust()
                .color(0x480104)
                .iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION)
                .components(Palladium, 1, Carbon, 1)
                .build();

        //  24204 Au-Pd-C Catalyst
        GTQTMaterials.AuPdCCatalyst = new Material.Builder(getMaterialsId(), gtqtcoreId("au_pd_c_catalyst"))
                .dust()
                .color(0xB7B305)
                .iconSet(SHINY)
                .components(Gold, 1, PalladiumOnCarbon, 1)
                .build();

        //  25106 Dibromomethylbenzene
        GTQTMaterials.Dibromomethylbenzene = new Material.Builder(getMaterialsId(), gtqtcoreId("dibromomethylbenzene"))
                .fluid()
                .color(0x9F4839)
                .components(Carbon, 7, Hydrogen, 6, Bromine, 2)
                .build();

        //  25105 Terephthalaldehyde
        GTQTMaterials.Terephthalaldehyde = new Material.Builder(getMaterialsId(), gtqtcoreId("terephthalaldehyde"))
                .dust()
                .color(0x567C2D)
                .iconSet(ROUGH)
                .components(Carbon, 8, Hydrogen, 6, Oxygen, 2)
                .build();

        //  24205 Sodium Oxide
        GTQTMaterials.SodiumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_oxide"))
                .dust()
                .color(0x2C96FC)
                .iconSet(BRIGHT)
                .components(Sodium, 2, Oxygen, 1)
                .build();

        //  25108 Dinitrodipropanyloxybenzene
        GTQTMaterials.Dinitrodipropanyloxybenzene = new Material.Builder(getMaterialsId(), gtqtcoreId("dinitrodipropanyloxybenzene"))
                .fluid()
                .color(0x9FAD1D)
                .components(Carbon, 12, Hydrogen, 16, Oxygen, 6, Nitrogen, 2)
                .build()
                .setFormula("C12H16O2(NO2)2", true);

        //  25104 Pre Zylon
        GTQTMaterials.PreZylon = new Material.Builder(getMaterialsId(), gtqtcoreId("pre_zylon"))
                .dust()
                .color(0x623250)
                .iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION, NO_SMASHING, NO_SMELTING)
                .components(Carbon, 20, Hydrogen, 22, Nitrogen, 2, Oxygen, 2)
                .build();

        //  25103 Zylon
        GTQTMaterials.Zylon = new Material.Builder(getMaterialsId(), gtqtcoreId("zylon"))
                .polymer().fluid()
                .fluidPipeProperties(800, 200, true, true, false, false)
                .color(0xFFE000)
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION, NO_SMASHING, NO_SMELTING, GENERATE_PLATE, GENERATE_FOIL)
                .components(Carbon, 14, Hydrogen, 6, Nitrogen, 2, Oxygen, 2)
                .build();

        //  24092 Hexafluoride Enriched Naquadah Solution
        GTQTMaterials.HexafluorideEnrichedNaquadahSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("hexafluoride_enriched_naquadah_solution"))
                .fluid()
                .color(0x868D7F)
                .components(NaquadahEnriched, 1, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24093 Xenon Hexafluoro Enriched Naquadate
        GTQTMaterials.XenonHexafluoroEnrichedNaquadate = new Material.Builder(getMaterialsId(), gtqtcoreId("xenon_hexafluoro_enriched_naquadate"))
                .fluid()
                .color(0x255A55)
                .components(Xenon, 1, NaquadahEnriched, 1, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24094 Enriched Naquadah Residue Solution
        GTQTMaterials.EnrichedNaquadahResidueSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("enriched_naquadah_residue_solution"))
                .fluid()
                .color(0x868D7F)
                .iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("XeAuSbKeF6S2?");

        //  24095 Xenoauric Fluoroantimonic Acid
        GTQTMaterials.XenoauricFluoroantimonicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("xenoauric_fluoroantimonic_acid"))
                .color(0xE0BD74)
                .fluid()
                .components(Xenon, 1, Gold, 1, Antimony, 1, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24096 Gold Chloride
        GTQTMaterials.GoldChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("gold_chloride"))
                .fluid()
                .color(0xCCCC66)
                .components(Gold, 2, Chlorine, 6)
                .build();

        //  24097 Bromine Trifluoride
        GTQTMaterials.BromineTrifluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("bromine_trifluoride"))
                .fluid()
                .color(0xA88E57)
                .components(Bromine, 1, Fluorine, 3)
                .build();

        //  24098 Gold Trifluoride
        GTQTMaterials.GoldTrifluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("gold_trifluoride"))
                .dust()
                .color(0xE8C478)
                .iconSet(BRIGHT)
                .components(Gold, 1, Fluorine, 3)
                .build();

        //  24099 Naquadria Caesiumfluoride
        GTQTMaterials.NaquadriaCaesiumfluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadria_caesiumfluoride"))
                .fluid()
                .color(0xAAEB69)
                .components(Naquadria, 1, Fluorine, 3, Caesium, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("*Nq*F2CsF", true);

        //  24100 Acidic Naquadria Caesiumfluoride
        GTQTMaterials.AcidicNaquadriaCaesiumfluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("acidic_naquadria_caesiumfluoride"))
                .fluid()
                .color(0x75EB00)
                .components(Naquadria, 1, Fluorine, 3, Caesium, 1, Sulfur, 2, Oxygen, 8)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("*Nq*F2CsF(SO4)2", true);

        //  24101 Nitrosonium Octafluoroxenate
        GTQTMaterials.NitrosoniumOctafluoroxenate = new Material.Builder(getMaterialsId(), gtqtcoreId("nitrosonium_octafluoroxenate"))
                .fluid()
                .color(0x953D9F)
                .components(NitrogenDioxide, 2, Xenon, 1, Fluorine, 8)
                .build()
                .setFormula("(NO2)2XeF8", true);

        //  24102 Naquadria Caesium Xenonnonfluoride
        GTQTMaterials.NaquadriaCaesiumXenonnonfluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadria_caesium_xenonnonfluoride"))
                .fluid()
                .color(0x54C248)
                .components(Naquadria, 1, Caesium, 1, Xenon, 1, Fluorine, 9)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24103 Radon Naquadria Octafluoride
        GTQTMaterials.RadonNaquadriaOctafluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("radon_naquadria_octafluoride"))
                .fluid()
                .color(0x85F378)
                .components(Radon, 1, Naquadria, 1, Fluorine, 8)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24104 Caesium Xenontrioxide Fluoride
        GTQTMaterials.CaesiumXenontrioxideFluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("caesium_xenontrioxide_fluoride"))
                .fluid()
                .color(0x5067D7)
                .flags(DISABLE_DECOMPOSITION)
                .components(Caesium, 1, Xenon, 1, Oxygen, 3, Fluorine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24105 Radon Trioxide
        GTQTMaterials.RadonTrioxide = new Material.Builder(getMaterialsId(), gtqtcoreId("radon_trioxide"))
                .fluid()
                .color(0x9A6DD7)
                .components(Radon, 1, Oxygen, 3)
                .build();

        //  24106 Cesium Fluoride
        GTQTMaterials.CaesiumFluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("caesium_fluoride"))
                .fluid()
                .color(0xFF7A5F)
                .components(Caesium, 1, Fluorine, 1)
                .build();

        //  24107 Xenon Trioxide
        GTQTMaterials.XenonTrioxide = new Material.Builder(getMaterialsId(), gtqtcoreId("xenon_trioxide"))
                .fluid()
                .color(0x359FC3)
                .components(Xenon, 1, Oxygen, 3)
                .build();

        //  24108 Hexafluoride Naquadria Solution
        GTQTMaterials.HexafluorideNaquadriaSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("hexafluoride_naquadria_solution"))
                .fluid()
                .color(0x25C213)
                .components(Naquadria, 1, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24109 Naquadria Residue Solution
        GTQTMaterials.NaquadriaResidueSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadria_residue_solution"))
                .fluid()
                .color(0x25C213)
                .iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("InPS6?", true);

        //  24110 Radon Difluoride
        GTQTMaterials.RadonDifluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("radon_difluoride"))
                .fluid()
                .color(0x8B7EFF)
                .components(Radon, 1, Fluorine, 2)
                .build();

        //  25079 Nitryl Fluoride
        GTQTMaterials.NitrylFluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("nitryl_fluoride"))
                .fluid()
                .color(0x8B7EFF)
                .components(Nitrogen, 1, Oxygen, 2, Fluorine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();


        //  24240 Ammonium Carbonate
        GTQTMaterials.AmmoniumCarbonate = new Material.Builder(getMaterialsId(), gtqtcoreId("ammonium_carbonate"))
                .dust()
                .color(0x7C89D9)
                .components(Carbon, 1, Hydrogen, 8, Oxygen, 3, Nitrogen, 2)
                .build()
                .setFormula("(NH4)2CO3", true);

        //  24239 Ammonium Acetate
        GTQTMaterials.AmmoniumAcetate = new Material.Builder(getMaterialsId(), gtqtcoreId("ammonium_acetate"))
                .dust()
                .color(0x646882)
                .components(Carbon, 2, Hydrogen, 7, Oxygen, 2, Nitrogen, 1)
                .build()
                .setFormula("NH4CH3CO2", true);

        //  25119 Acetamide
        GTQTMaterials.Acetamide = new Material.Builder(getMaterialsId(), gtqtcoreId("acetamide"))
                .dust()
                .color(0x7D82A3)
                .iconSet(DULL)
                .components(Carbon, 2, Hydrogen, 5, Nitrogen, 1, Oxygen, 1)
                .build()
                .setFormula("CH3CONH2", true);

        //  25118 Acetonitrile
        GTQTMaterials.Acetonitrile = new Material.Builder(getMaterialsId(), gtqtcoreId("acetonitrile"))
                .dust()
                .color(0x7D82A3)
                .iconSet(ROUGH)
                .components(Carbon, 2, Hydrogen, 3, Nitrogen, 1)
                .build()
                .setFormula("CH3CN");

        //  25109 Hexanitrohexaaxaisowurtzitane
        GTQTMaterials.Hexanitrohexaaxaisowurtzitane = new Material.Builder(getMaterialsId(), gtqtcoreId("hexanitrohexaaxaisowurtzitane"))
                .dust()
                .color(0x0B7222)
                .iconSet(BRIGHT)
                .components(Carbon, 6, Hydrogen, 6, Nitrogen, 12, Oxygen, 12)
                .build();
        //  25110 Crude Hexanitrohexaaxaisowurtzitane
        GTQTMaterials.CrudeHexanitrohexaaxaisowurtzitane = new Material.Builder(getMaterialsId(), gtqtcoreId("crude_hexanitrohexaaxaisowurtzitane"))
                .dust()
                .color(0x5799EC)
                .iconSet(DULL)
                .components(Carbon, 6, Hydrogen, 6, Nitrogen, 12, Oxygen, 12)
                .build();

        //  25111 Tetraacetyldinitrosohexaazaisowurtzitane
        GTQTMaterials.Tetraacetyldinitrosohexaazaisowurtzitane = new Material.Builder(getMaterialsId(), gtqtcoreId("tetraacetyldinitrosohexaazaisowurtzitane"))
                .dust()
                .color(0xEA7584)
                .iconSet(ROUGH)
                .components(Carbon, 14, Hydrogen, 18, Nitrogen, 8, Oxygen, 6)
                .build();

        //  25112 Dibenzyltetraacetylhexaazaisowurtzitane
        GTQTMaterials.Dibenzyltetraacetylhexaazaisowurtzitane = new Material.Builder(getMaterialsId(), gtqtcoreId("dibenzyltetraacetylhexaazaisowurtzitane"))
                .dust()
                .color(0xB7E8EE)
                .iconSet(DULL)
                .components(Carbon, 28, Hydrogen, 32, Nitrogen, 6, Oxygen, 4)
                .build();

        //  25113 Succinimidyl Acetate
        GTQTMaterials.SuccinimidylAcetate = new Material.Builder(getMaterialsId(), gtqtcoreId("succinimidyl_acetate"))
                .dust()
                .color(0x1D3605)
                .iconSet(ROUGH)
                .components(Carbon, 6, Hydrogen, 7, Nitrogen, 1, Oxygen, 4)
                .build();

        //  25114 N-Hydroxysuccinimide
        GTQTMaterials.NHydroxysuccinimide = new Material.Builder(getMaterialsId(), gtqtcoreId("n_hydroxysuccinimide"))
                .dust()
                .color(0x33BAFB)
                .iconSet(DULL)
                .components(Carbon, 4, Hydrogen, 5, Nitrogen, 1, Oxygen, 3)
                .build()
                .setFormula("(CH2CO)2NOH", true);

        //  25116 Succinic Anhydride
        GTQTMaterials.SuccinicAnhydride = new Material.Builder(getMaterialsId(), gtqtcoreId("succinic_anhydride"))
                .dust()
                .color(0xA2569D)
                .components(Carbon, 4, Hydrogen, 4, Oxygen, 3)
                .build()
                .setFormula("(CH2CO)2O");

        //  25117 Hexabenzylhexaazaisowurtzitane
        GTQTMaterials.Hexabenzylhexaazaisowurtzitane = new Material.Builder(getMaterialsId(), gtqtcoreId("hexabenzylhexaazaisowurtzitane"))
                .dust()
                .color(0x48561E)
                .iconSet(DULL)
                .components(Carbon, 48, Hydrogen, 48, Nitrogen, 6)
                .build();

        //  25120 Benzylamine
        GTQTMaterials.Benzylamine = new Material.Builder(getMaterialsId(), gtqtcoreId("benzylamine"))
                .fluid()
                .color(0x527A92)
                .components(Carbon, 7, Hydrogen, 9, Nitrogen, 1)
                .build();

        //  25121 Benzyl Chloride
        GTQTMaterials.BenzylChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("benzyl_chloride"))
                .fluid()
                .color(0x6699CC)
                .components(Carbon, 7, Hydrogen, 7, Chlorine, 1)
                .build();

        //  25122 Hexamethylenetetramine
        GTQTMaterials.Hexamethylenetetramine = new Material.Builder(getMaterialsId(), gtqtcoreId("hexamethylenetetramine"))
                .dust()
                .color(0x53576D)
                .iconSet(DULL)
                .components(Carbon, 6, Hydrogen, 12, Nitrogen, 4)
                .build()
                .setFormula("(CH2)6N4", true);

        //  25064 Succinic Acid
        GTQTMaterials.SuccinicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("succinic_acid"))
                .dust()
                .color(0x0C3A5B)
                .iconSet(DULL)
                .components(Carbon, 4, Hydrogen, 6, Oxygen, 4)
                .build();

        //  24233 Potassium Carbonate
        GTQTMaterials.PotassiumCarbonate = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_carbonate"))
                .dust()
                .color(0x7C89D9)
                .iconSet(ROUGH)
                .components(Potassium, 2, Carbon, 1, Oxygen, 3)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24232 Potassium Bisulfite
        GTQTMaterials.PotassiumBisulfite = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_bisulfite"))
                .dust()
                .color(344314)
                .iconSet(DULL)
                .components(Potassium, 1, Hydrogen, 1, Sulfur, 1, Oxygen, 3)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24235 Potassium Nitrite
        GTQTMaterials.PotassiumNitrite = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_nitrite"))
                .dust()
                .color(0xB9B9B9)
                .components(Potassium, 1, Nitrogen, 1, Oxygen, 2)
                .build();

        //  24230 Hydroxylammonium Sulfate
        GTQTMaterials.HydroxylammoniumSulfate = new Material.Builder(getMaterialsId(), gtqtcoreId("hydroxylammonium_sulfate"))
                .dust()
                .color(0x999933)
                .iconSet(DULL)
                .components(Nitrogen, 2, Hydrogen, 8, Oxygen, 6, Sulfur, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(NH3OH)2SO4", true);

        //  24231 Potassium Hydroxylaminedisulfonate
        GTQTMaterials.PotassiumHydroxylaminedisulfonate = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_hydroxylaminedisulfonate"))
                .dust()
                .color(0x627D25)
                .iconSet(ROUGH)
                .components(Potassium, 2, Nitrogen, 1, Hydrogen, 1, Sulfur, 2, Oxygen, 7)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24236 Barium Dichloride
        GTQTMaterials.BariumDichloride = new Material.Builder(getMaterialsId(), gtqtcoreId("barium_dichloride"))
                .dust()
                .color(0xBF6700)
                .iconSet(BRIGHT)
                .components(Barium, 1, Chlorine, 3)
                .build();

        //  24238 Barium Sulfate Suspension
        GTQTMaterials.BariumSulfateSuspension = new Material.Builder(getMaterialsId(), gtqtcoreId("barium_sulfate_suspension"))
                .fluid()
                .color(0x71560B)
                .components(Barium, 1, Sulfur, 1, Oxygen, 4, Water, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24194 Potassium Sulfate
        GTQTMaterials.PotassiumSulfate = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_sulfate"))
                .dust()
                .color(0xF4FBB0)
                .iconSet(DULL)
                .components(Potassium, 2, Sulfur, 1, Oxygen, 4)
                .build();

        //  25063 Succinimide
        GTQTMaterials.Succinimide = new Material.Builder(getMaterialsId(), gtqtcoreId("succinimide"))
                .dust()
                .color(0x1774B6)
                .iconSet(ROUGH)
                .components(Carbon, 4, Hydrogen, 5, Nitrogen, 1, Oxygen, 2)
                .build();

        //  24229 Nitrogen Monoxide
        GTQTMaterials.NitrogenMonoxide = new Material.Builder(getMaterialsId(), gtqtcoreId("nitrogen_monoxide"))
                .fluid()
                .color(0x98BCDA)
                .components(Nitrogen, 1, Oxygen, 1)
                .build();

        //  24227 Nitrosonium Tetrafluoroborate
        GTQTMaterials.NitrosoniumTetrafluoroborate = new Material.Builder(getMaterialsId(), gtqtcoreId("nitrosonium_tetrafluoroborate"))
                .dust()
                .color(0xA32A8C)
                .iconSet(ROUGH)
                .components(Sodium, 1, Oxygen, 1, Boron, 1, Fluorine, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24228 Tetrafluoroboric Acid
        GTQTMaterials.TetrafluoroboricAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("tetrafluoroboric_acid"))
                .fluid()
                .color(0x83A731)
                .components(Hydrogen, 1, Boron, 1, Fluorine, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  25033 Benzaldehyde
        GTQTMaterials.Benzaldehyde = new Material.Builder(getMaterialsId(), gtqtcoreId("benzaldehyde"))
                .fluid()
                .color(0x957D53)
                .components(Carbon, 7, Hydrogen, 6, Oxygen, 1)
                .build();

        //  24226 Nitronium Tetrafluoroborate
        GTQTMaterials.NitroniumTetrafluoroborate = new Material.Builder(getMaterialsId(), gtqtcoreId("nitronium_tetrafluoroborate"))
                .dust()
                .color(0x787449)
                .iconSet(DULL)
                .components(Sodium, 1, Oxygen, 2, Boron, 1, Fluorine, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24225 Silica Gel Base
        GTQTMaterials.SilicaGelBase = new Material.Builder(getMaterialsId(), gtqtcoreId("silica_gel_base"))
                .fluid()
                .color(0x9695FD)
                .iconSet(ROUGH)
                .components(SiliconDioxide, 1, HydrochloricAcid, 1, SodiumHydroxide, 1, Water, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24224 Silica Gel
        GTQTMaterials.SilicaGel = new Material.Builder(getMaterialsId(), gtqtcoreId("silica_gel"))
                .dust()
                .color(0x9695FD)
                .iconSet(SHINY)
                .components(Silicon, 1, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  25213 Precious Metal
        GTQTMaterials.PreciousMetal = new Material.Builder(getMaterialsId(), gtqtcoreId("precious_metal"))
                .ore(1, 1, false)
                .ingot()
                .color(0xDAA520)
                .iconSet(SHINY)
                .build()
                .setFormula("Au?", false);

        //  24266 Gold Copper Mixture
        GTQTMaterials.GoldCopperMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("gold_copper_mixture"))
                .dust()
                .color(0xD2D242)
                .iconSet(SHINY)
                .components(Copper, 3, Gold, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("Cu3Au?", true);

        //  24267 Leaching Gold
        GTQTMaterials.LeachingGold = new Material.Builder(getMaterialsId(), gtqtcoreId("leaching_gold"))
                .dust()
                .color(0xA7650F)
                .iconSet(ROUGH)
                .components(Copper, 3, Gold, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("Cu3Au?", true);

        //  24268 Chloroauric Acid
        GTQTMaterials.ChloroauricAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("chloroauric_acid"))
                .fluid()
                .color(LeachingGold.getMaterialRGB() + HydrochloricAcid.getMaterialRGB())
                .components(Hydrogen, 1, Gold, 1, Chlorine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("HAuCl?", false);

        //  24269 Leaching Copper
        GTQTMaterials.LeachingCopper = new Material.Builder(getMaterialsId(), gtqtcoreId("leaching_copper"))
                .dust()
                .color(Copper.getMaterialRGB() + LeachingGold.getMaterialRGB())
                .iconSet(SHINY)
                .components(Copper, 3)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("Cu3?", true);

        //  24286 Gold Nickel Mixture
        GTQTMaterials.GoldNickelMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("gold_nickel_mixture"))
                .dust()
                .color(GoldCopperMixture.getMaterialRGB() + Nickel.getMaterialRGB())
                .iconSet(SAND)
                .components(Nickel, 3, Gold, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("Ni3Au?", true);

        //  24285 Leaching Nickel
        GTQTMaterials.LeachingNickel = new Material.Builder(getMaterialsId(), gtqtcoreId("leaching_nickel"))
                .dust()
                .color(LeachingCopper.getMaterialRGB() + Nickel.getMaterialRGB())
                .iconSet(BRIGHT)
                .components(Nickel, 3)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("Ni3?", true);

        //  24270 Potassium Metabisulfite
        GTQTMaterials.PotassiumMetabisulfite = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_metabisulfite"))
                .dust()
                .color(Potassium.getMaterialRGB() + Sulfur.getMaterialRGB())
                .iconSet(SAND)
                .components(Potassium, 2, Sulfur, 2, Oxygen, 5)
                .build();

        //低纯度硅岩乳液
        GTQTMaterials.LowPurityNaquadahLotion = new Material.Builder(getMaterialsId(), gtqtcoreId("low_purity_naquadah_lotion"))
                .fluid()
                .flags(DISABLE_DECOMPOSITION)
                .color(0x9AFF9A)
                .components(Naquadah, 1, Oxygen, 2)
                .build();

        //低纯度硅岩溶液
        GTQTMaterials.LowPurityNaquadahSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("low_purity_naquadah_solution"))
                .fluid()
                .flags(DISABLE_DECOMPOSITION)
                .color(0xB4EEB4)
                .components(Naquadah, 1, Oxygen, 2)
                .build();

        //硅岩-精金混合
        GTQTMaterials.Nqad = new Material.Builder(getMaterialsId(), gtqtcoreId("nqad"))
                .fluid()
                .color(0xE9967A)
                .build();

        //氢氧化镓 material('gallium')
        GTQTMaterials.GalliumHydroxide = new Material.Builder(getMaterialsId(), gtqtcoreId("gallium_hydroxide"))
                .dust()
                .color(0xDBDBDB)
                .components(Gallium, 1, Oxygen, 1, Hydrogen, 1)
                .build();

        //废氟
        GTQTMaterials.WasteFluorine = new Material.Builder(getMaterialsId(), gtqtcoreId("waste_fluorine"))
                .fluid()
                .color(0x556B2F)
                .flags(DISABLE_DECOMPOSITION)
                .components(Fluorine, 1)
                .build();

        //废液
        GTQTMaterials.WasteLiquid = new Material.Builder(getMaterialsId(), gtqtcoreId("waste_liquid"))
                .fluid()
                .color(0x548B54)
                .build();

        //浓缩富集硅岩矿泥粉
        GTQTMaterials.ConcentrateEnrichNaquadahClay = new Material.Builder(getMaterialsId(), gtqtcoreId("concentrate_enrich_naquadah_clay"))
                .dust()
                .color(0x48D1CC)
                .build();
        //氧化硅岩混合物粉
        GTQTMaterials.NaquadahOxideRockMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadah_oxide_rock_mixture"))
                .dust()
                .color(0x458B00)
                .build();
        //高纯硅岩溶液
        GTQTMaterials.HighPurityNaquadahSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("high_purity_naquadah_solution"))
                .fluid()
                .color(0x3CB371)
                .build();

        //低纯硫酸超能硅岩粉
        GTQTMaterials.LowPuritySulfuricAcidSuperEnergyNaquadah = new Material.Builder(getMaterialsId(), gtqtcoreId("low_purity_sulfuric_acid_super_energy_naquadah"))
                .dust()
                .color(0x32CD32)
                .build();

        //低纯硫酸超能硅岩溶液
        GTQTMaterials.LowPuritySulfuricAcidSuperEnergyNaquadahSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("low_purity_sulfuric_acid_super_energy_naquadah_solution"))
                .fluid()
                .color(0x2E8B57)
                .build();

        //高纯超能硅岩溶液
        GTQTMaterials.HighPuritySulfuricAcidSuperEnergyNaquadah = new Material.Builder(getMaterialsId(), gtqtcoreId("high_purity_sulfuric_acid_super_energy_naquadah_solution"))
                .fluid()
                .color(0x228B22)
                .build();

        OganessonBreedingBase = new Material.Builder(getMaterialsId(), gtqtcoreId("oganesson_breeding_base"))
                .liquid()
                .color(0xA65A7F)
                .components(Oganesson, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //四溴对二甲苯
        GTQTMaterials.Tetrabromo = new Material.Builder(getMaterialsId(), gtqtcoreId("tetrabromo"))
                .fluid()
                .color(0x8B8B00)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 8, Hydrogen, 6, Bromine, 4)
                .build();

        //六溴对二甲苯
        GTQTMaterials.Tetrabromobenzene = new Material.Builder(getMaterialsId(), gtqtcoreId("tetrabromobenzene"))
                .fluid()
                .color(0x8E8E38)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 8, Hydrogen, 4, Bromine, 6)
                .build();

        //BPS
        GTQTMaterials.Bps = new Material.Builder(getMaterialsId(), gtqtcoreId("bps"))
                .fluid()
                .color(0xB452CD)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(C8H8)n-C8H4Br6");

        //溴化环氧树脂
        GTQTMaterials.Brominatedepoxyresins = new Material.Builder(getMaterialsId(), gtqtcoreId("brominatedepoxyresins"))
                .fluid()
                .color(0xCD3278)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(C11H12O3)n-C8H4Br6");

        GTQTMaterials.Anorthite = new Material.Builder(getMaterialsId(), gtqtcoreId("anorthite"))
                .dust()
                .gem()
                .color(0x595853).iconSet(CERTUS)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Calcium, 1, Aluminium, 2, Silicon, 2, Oxygen, 8)
                .build()
                .setFormula("Ca(Al2Si2O8)", true);

        GTQTMaterials.Albite = new Material.Builder(getMaterialsId(), gtqtcoreId("albite"))
                .dust()
                .gem()
                .color(0xc4a997).iconSet(CERTUS)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Sodium, 1, Aluminium, 1, Silicon, 3, Oxygen, 8)
                .build()
                .setFormula("Na(AlSi3O8)", true);

        GTQTMaterials.Oligoclase = new Material.Builder(getMaterialsId(), gtqtcoreId("oligoclase"))
                .dust()
                .gem()
                .color(0xd5c4b8).iconSet(CERTUS)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Albite, 4, Anorthite, 1)
                .build()
                .setFormula("(Na,Ca)(Si,Al)4O8", true);

        GTQTMaterials.Andesine = new Material.Builder(getMaterialsId(), gtqtcoreId("andesine"))
                .dust()
                .gem()
                .color(0xe18e6f).iconSet(EMERALD)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Albite, 3, Anorthite, 2)
                .build()
                .setFormula("(Na,Ca)(Si,Al)4O8", true);

        GTQTMaterials.Labradorite = new Material.Builder(getMaterialsId(), gtqtcoreId("labradorite"))
                .dust()
                .gem()
                .color(0x5c7181).iconSet(RUBY)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Albite, 2, Anorthite, 3)
                .build()
                .setFormula("(Na,Ca)(Si,Al)4O8", true);

        GTQTMaterials.Bytownite = new Material.Builder(getMaterialsId(), gtqtcoreId("bytownite"))
                .dust()
                .gem()
                .color(0xc99c67).iconSet(LAPIS)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Albite, 1, Anorthite, 4)
                .build()
                .setFormula("(Na,Ca)(Si,Al)4O8", true);

        GTQTMaterials.Clinochlore = new Material.Builder(getMaterialsId(), gtqtcoreId("chlinochlore"))
                .dust()
                .gem()
                .color(0x303e38).iconSet(EMERALD)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Magnesium, 5, Aluminium, 2, Silicon, 3, Oxygen, 18, Hydrogen, 8)
                .build()
                .setFormula("(Mg5Al)(AlSi3)O10(OH)8", true);

        GTQTMaterials.Augite = new Material.Builder(getMaterialsId(), gtqtcoreId("augite"))
                .dust()
                .color(0x1b1717).iconSet(ROUGH)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Calcium, 2, Magnesium, 3, Iron, 3, Silicon, 8, Oxygen, 24)
                .build()
                .setFormula("(Ca2MgFe)(MgFe)2(Si2O6)4", true);


        GTQTMaterials.Dolomite = new Material.Builder(getMaterialsId(), gtqtcoreId("dolomite"))
                .dust()
                .color(0xbbb8b2)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Calcium, 1, Magnesium, 1, Carbon, 2, Oxygen, 6)
                .build()
                .setFormula("CaMg(CO3)2", true);

        GTQTMaterials.Muscovite = new Material.Builder(getMaterialsId(), gtqtcoreId("muscovite"))
                .dust()
                .color(0x8b876a)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Potassium, 1, Aluminium, 3, Silicon, 3, Oxygen, 12, Hydrogen, 10)
                .build()
                .setFormula("KAl2(AlSi3O10)(OH)2)", true);

        GTQTMaterials.Fluorite = new Material.Builder(getMaterialsId(), gtqtcoreId("fluorite"))
                .dust()
                .gem()
                .ore()
                .color(0x276a4c).iconSet(CERTUS)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Calcium, 1, Fluorine, 2)
                .build();

        GTQTMaterials.Forsterite = new Material.Builder(getMaterialsId(), gtqtcoreId("forsterite"))
                .dust()
                .gem()
                .color(0x1d640f).iconSet(LAPIS)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Magnesium, 2, Sulfur, 1, Oxygen, 4)
                .build()
                .setFormula("Mg2(SiO4)", true);

        GTQTMaterials.Lizardite = new Material.Builder(getMaterialsId(), gtqtcoreId("lizardite"))
                .dust()
                .color(0xa79e42)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Magnesium, 3, Silicon, 2, Oxygen, 9, Hydrogen, 4)
                .build()
                .setFormula("Mg3Si2O5(OH)4", true);

        //单推三肼燃料
        GTQTMaterials.TrihydraziniumGel = new Material.Builder(getMaterialsId(), gtqtcoreId("trihydrazinium_gel"))
                .fluid()
                .color(0x0000EE)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //风趣幽默的生物工程
        //CTAB
        GTQTMaterials.CTAB = new Material.Builder(getMaterialsId(), gtqtcoreId("cbat"))
                .fluid()
                .color(0x9ACD32)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C19H42BrN", true);

        //溴代十六烷
        GTQTMaterials.BromoHexadecane = new Material.Builder(getMaterialsId(), gtqtcoreId("bromo_hexadecane"))
                .fluid()
                .color(0xAB82FF)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C16H33Br", true);

        //十六醇
        GTQTMaterials.Hexadecanol = new Material.Builder(getMaterialsId(), gtqtcoreId("hexadecanol"))
                .fluid()
                .color(0xBDB76B)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C16H34O", true);

        //β-巯基乙醇
        GTQTMaterials.Hydroxyethanethiol = new Material.Builder(getMaterialsId(), gtqtcoreId("hydroxyethanethiol"))
                .fluid()
                .color(0xCD3333)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C2H6OS", true);

        //二硫代二甘醇
        GTQTMaterials.Erliudaierganchun = new Material.Builder(getMaterialsId(), gtqtcoreId("erliudaierganchun"))
                .fluid()
                .color(0xCD8C95)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C4H6O4S2", true);

        //三羟甲基氨基甲烷
        GTQTMaterials.TRIS = new Material.Builder(getMaterialsId(), gtqtcoreId("tris"))
                .fluid()
                .color(0xCDB7B5)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(HOCH2)3CNH2", true);

        //三羟甲基氨基甲烷
        GTQTMaterials.TRISP = new Material.Builder(getMaterialsId(), gtqtcoreId("trisp"))
                .fluid()
                .color(0xDEB887)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(HOCH2)3CNH2*", true);

        //三羟甲基硝基甲烷
        GTQTMaterials.TRISN = new Material.Builder(getMaterialsId(), gtqtcoreId("trisn"))
                .fluid()
                .color(0xEEAEEE)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("CH2（NO3）3", true);

        //异戊醇
        GTQTMaterials.Isoamylalcohol = new Material.Builder(getMaterialsId(), gtqtcoreId("isoamylalcohol"))
                .fluid()
                .color(0xEE00EE)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C5H12O", true);

        //异戊醛
        GTQTMaterials.Isovaleraldehyde = new Material.Builder(getMaterialsId(), gtqtcoreId("isovaleraldehyde"))
                .fluid()
                .color(0xEE8262)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C5H10O", true);

        //dna
        GTQTMaterials.DNAorigin = new Material.Builder(getMaterialsId(), gtqtcoreId("dnaorigin"))
                .fluid()
                .color(0xB3EE3A)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.DNAdeal = new Material.Builder(getMaterialsId(), gtqtcoreId("dnadeal"))
                .fluid()
                .color(0xEE8262)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.DNAh = new Material.Builder(getMaterialsId(), gtqtcoreId("dnah"))
                .fluid()
                .color(0x737373)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.DNAl = new Material.Builder(getMaterialsId(), gtqtcoreId("dnal"))
                .fluid()
                .color(0xCD9B1D)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.DNAp = new Material.Builder(getMaterialsId(), gtqtcoreId("dnap"))
                .fluid()
                .color(0x388E8E)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.DNAw = new Material.Builder(getMaterialsId(), gtqtcoreId("dnaw"))
                .fluid()
                .color(0xFFFAFA)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.DNAn = new Material.Builder(getMaterialsId(), gtqtcoreId("dnan"))
                .fluid()
                .color(0xFFFFFF)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.Wool = new Material.Builder(getMaterialsId(), gtqtcoreId("wool"))
                .fluid().dust()
                .color(0xFFFFFF)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //氧化钛
        GTQTMaterials.TitaniumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("titanium_oxide"))
                .ingot().dust()
                .color(0xFF6EB4)
                .components(Titanium, 1, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //酸性钛
        GTQTMaterials.AcidicTitanium = new Material.Builder(getMaterialsId(), gtqtcoreId("acidic_titanium"))
                .fluid()
                .color(0xFF6A6A)
                .components(Titanium, 1, Sulfur, 1, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //含杂酰化钛
        GTQTMaterials.ImpureTitaniumAcylate = new Material.Builder(getMaterialsId(), gtqtcoreId("impure_titanium_acylate"))
                .fluid()
                .color(0xFF3E96)
                .components(Titanium, 1, Carbon, 1, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("[Ti(O2)(OH)(H2O)4]*", true);

        //酰化钛沉淀
        GTQTMaterials.TitaniumAcylatePrecipitation = new Material.Builder(getMaterialsId(), gtqtcoreId("titanium_acylate_precipitation"))
                .dust()
                .color(0xFF7F00)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("[Ti(O2)(OH)(H2O)4]*", true);

        //粗制酰化钛
        GTQTMaterials.CrudeTitaniumAcylate = new Material.Builder(getMaterialsId(), gtqtcoreId("crude_titanium_acylate"))
                .fluid()
                .color(0xFF8C69)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("[Ti(O2)(OH)(H2O)4]*", true);

        //酰化钛
        GTQTMaterials.TitaniumAcylate = new Material.Builder(getMaterialsId(), gtqtcoreId("titanium_acylate"))
                .fluid()
                .color(0xFF00FF)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("[Ti(O2)(OH)(H2O)4]＋", true);
        //乙酸钡
        GTQTMaterials.BariumAcetate = new Material.Builder(getMaterialsId(), gtqtcoreId("barium_acetate"))
                .fluid()
                .color(0xFA8072)
                .components(Titanium, 1, Carbon, 1, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(CH₃COO)₂Ba", true);

        //钛合金TC4
        GTQTMaterials.TitaniumAlloyTCF = new Material.Builder(getMaterialsId(), gtqtcoreId("titanium_alloy_tcf"))
                .fluid()
                .color(0xFF7F00)
                .blast(3600, MID)
                .components(Titanium, 6, Aluminium, 4, Vanadium, 1)
                .flags(DISABLE_DECOMPOSITION, GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .build();

        //5-羟甲基糠醛
        GTQTMaterials.Hydroxymethylfurfural = new Material.Builder(getMaterialsId(), gtqtcoreId("hydroxymethylfurfural"))
                .fluid()
                .color(0x7A378B)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C6H8O3", true);

        //2,5-二甲基呋喃
        GTQTMaterials.Methylfuran = new Material.Builder(getMaterialsId(), gtqtcoreId("methylfuran"))
                .fluid()
                .color(0xA0522D)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C5H6O", true);

        //2-环己胺基乙磺酸
        GTQTMaterials.CHES = new Material.Builder(getMaterialsId(), gtqtcoreId("ches"))
                .fluid()
                .color(0xCD3278)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C8H17NO3S", true);

        //环己胺基乙磺酸钠
        GTQTMaterials.Ethanesulphonate = new Material.Builder(getMaterialsId(), gtqtcoreId("ethanesulphonate"))
                .fluid()
                .color(0xCDAA7D)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C8H16NNaO3S", true);

        //冷却液
        GTQTMaterials.GelidCryotheum = new Material.Builder(getMaterialsId(), gtqtcoreId("gelid_cryotheum"))
                .liquid(new FluidBuilder().temperature(8).customStill().customFlow())
                .color(0x40B8FB)
                .components(Ice, 2, Electrotine, 1, Water, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.SodiumPhosphomolybdate = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_phosphomolybdate"))
                .dust()
                .color(0xF3E0A8)
                .iconSet(BRIGHT)
                .components(Oxygen, 40, Molybdenum, 12, Sodium, 3, Phosphorus, 1)
                .flags(DECOMPOSITION_BY_ELECTROLYZING)
                .build()
                .setFormula("(MoO3)12Na3PO4", true);

        GTQTMaterials.FerricCatalyst = new Material.Builder(getMaterialsId(), gtqtcoreId("ferric_catalyst"))
                .dust()
                .color(0xFFD700)
                .build()
                .setFormula("Fe2(C4H4O6)3", true);

        GTQTMaterials.PhosphonitrilicChlorideTrimer = new Material.Builder(getMaterialsId(), gtqtcoreId("phosphonitrilic_chloride_trimer"))
                .fluid()
                .color(0x082C38)
                .components(Chlorine, 6, Nitrogen, 3, Phosphorus, 3)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.SodiumTrifluoroethanolate = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_trifluoroethanolate"))
                .dust()
                .color(0x50083E)
                .iconSet(ROUGH)
                .components(Carbon, 2, Hydrogen, 4, Fluorine, 3, Sodium, 1, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.OctafluoroPentanol = new Material.Builder(getMaterialsId(), gtqtcoreId("octafluoro_pentanol"))
                .fluid()
                .color(0xE5EBDE)
                .components(Carbon, 5, Hydrogen, 4, Fluorine, 8, Oxygen, 1)
                .build();

        GTQTMaterials.FluoroboricAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("fluoroboric_acid"))
                .liquid(new FluidBuilder().attributes(ACID))
                .color(0xD5811B)
                .components(Hydrogen, 1, Boron, 1, Fluorine, 4)
                .build();

        GTQTMaterials.SodiumNitrite = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_nitrite"))
                .dust()
                .color(0x205CA4)
                .iconSet(DULL)
                .components(Sodium, 1, Nitrogen, 1, Oxygen, 2)
                .build();

        GTQTMaterials.BenzenediazoniumTetrafluoroborate = new Material.Builder(getMaterialsId(), gtqtcoreId("benzenediazonium_tetrafluoroborate"))
                .fluid()
                .color(0xD5C5B2)
                .components(Carbon, 6, Hydrogen, 5, Boron, 1, Fluorine, 4, Nitrogen, 2)
                .build();

        GTQTMaterials.CoACABCatalyst = new Material.Builder(getMaterialsId(), gtqtcoreId("co_ac_ab_catalyst"))
                .dust()
                .color(0x6B4312)
                .iconSet(METALLIC)
                .build();

        //  25098 Sodium Formate
        GTQTMaterials.SodiumFormate = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_formate"))
                .fluid()
                .color(0x416CC0)
                .iconSet(ROUGH)
                .components(Carbon, 1, Hydrogen, 1, Oxygen, 2, Sodium, 1)
                .build();

        //钨酸铵
        GTQTMaterials.AmmoniumTungstate = new Material.Builder(getMaterialsId(), gtqtcoreId("ammonium_tungstate"))
                .fluid()
                .color(0x008B00)
                .iconSet(ROUGH)
                .build()
                .setFormula("（NH4)6W7024•6H20", true);
        //晶体钨酸铵
        GTQTMaterials.CammoniumTungstate = new Material.Builder(getMaterialsId(), gtqtcoreId("cammonium_tungstate"))
                .dust()
                .color(0x458B00)
                .iconSet(SHINY)
                .build()
                .setFormula("（NH4)6W7024", true);

        //Na2WO4
        GTQTMaterials.SodiumTungstateDihydrate = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_tungstate_dihydrate"))
                .dust()
                .color(0x473C8B)
                .iconSet(SHINY)
                .build()
                .setFormula("Na2WO4", true);

        //工业钨酸
        GTQTMaterials.ItungsticAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("itungstic_acid"))
                .fluid()
                .color(0x68228B)
                .iconSet(SHINY)
                .build()
                .setFormula("H2WO4*", true);

        //AmmoniaBisulfate
        GTQTMaterials.AmmoniaBisulfate = new Material.Builder(getMaterialsId(), gtqtcoreId("ammonia_bisulfate"))
                .fluid()
                .color(0x6B4312)
                .build()
                .setFormula("NH4HSO4", true);

        //酵母提取物
        GTQTMaterials.YeastExtract = new Material.Builder(getMaterialsId(), gtqtcoreId("yeast_extract"))
                .fluid()
                .color(0x5D478B)
                .build();

        //酵母
        GTQTMaterials.Yeast = new Material.Builder(getMaterialsId(), gtqtcoreId("yeast"))
                .fluid()
                .color(0xD8BFD8)
                .build();

        //葡萄糖发酵溶液
        GTQTMaterials.GlucoseFermentationSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("glucose_fermentation_solution"))
                .fluid()
                .color(0x8B3A62)
                .build();

        //葡萄糖发酵残留
        GTQTMaterials.GlucoseFermentationResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("glucose_fermentation_residue"))
                .fluid()
                .color(0x8B3E2F)
                .build();

        //钽铌铁粉
        GTQTMaterials.NiobiumTantalumFe = new Material.Builder(getMaterialsId(), gtqtcoreId("niobium_tantalum_fe"))
                .dust()
                .color(0x8B3E2F)
                .iconSet(SHINY)
                .build();

        GTQTMaterials.NiobiumTantalumFec = new Material.Builder(getMaterialsId(), gtqtcoreId("niobium_tantalum_fec"))
                .fluid()
                .color(0x8B3A62)
                .iconSet(SHINY)
                .build();

        //氯化铌
        GTQTMaterials.NiobiumChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("niobium_chloride"))
                .fluid()
                .color(0x6B8E23)
                .iconSet(SHINY)
                .build()
                .setFormula("NbCl₄", true);
        //氯化钽
        GTQTMaterials.TantalumChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("tantalum_chloride"))
                .fluid()
                .color(0x698B22)
                .iconSet(SHINY)
                .build()
                .setFormula("TaCl₄", true);

        //氟化铌
        GTQTMaterials.NiobiumFluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("niobium_fluoride"))
                .fluid()
                .color(0x5D478B)
                .iconSet(SHINY)
                .build()
                .setFormula("NbCl₄", true);
        //氟化钽
        GTQTMaterials.TantalumFluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("tantalum_fluoride"))
                .fluid()
                .color(0x473C8B)
                .iconSet(SHINY)
                .build()
                .setFormula("TaCl₄", true);

        //七氟铌酸钾
        GTQTMaterials.PotassiumHeptafluorooniobate = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_heptafluorooniobate"))
                .fluid()
                .color(0x218868)
                .iconSet(SHINY)
                .build()
                .setFormula("F7NbCl4", true);

        //七氟钽酸钾
        GTQTMaterials.PotassiumHeptafluorotanate = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_heptafluorotanate"))
                .fluid()
                .color(0x2E8B57)
                .iconSet(SHINY)
                .build()
                .setFormula("F7TaCl4", true);

        GTQTMaterials.ElectrolyteReflectorMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("electrolyte_reflector_mixture"))
                .liquid(new FluidBuilder().temperature(209))
                .color(0xE62A35)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("TaCl₄", true);

        GTQTMaterials.FullerenePolymerMatrix = new Material.Builder(getMaterialsId(), gtqtcoreId("fullerene_polymer_matrix"))
                .polymer()
                .liquid(new FluidBuilder().temperature(500))
                .color(0x2F0B01)
                .iconSet(SHINY)
                .components(Lead, 1, Iron, 1, Carbon, 153, Hydrogen, 36, Nitrogen, 1, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION, NO_SMASHING, NO_SMELTING, GENERATE_PLATE, GENERATE_FOIL, GENERATE_ROD, GENERATE_FINE_WIRE)
                .build();

        GTQTMaterials.IsobutyricAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("isobutyric_acid"))
                .fluid()
                .color(0x9400D3)
                .components(Carbon, 4, Hydrogen, 8, Oxygen, 2)
                .build();

        GTQTMaterials.IsobutyricAnhydride = new Material.Builder(getMaterialsId(), gtqtcoreId("isobutyric_anhydride"))
                .fluid()
                .color(0x473C8B)
                .components(Carbon, 8, Hydrogen, 14, Oxygen, 3)
                .build();

        GTQTMaterials.Dimethylketene = new Material.Builder(getMaterialsId(), gtqtcoreId("dimethylketene"))
                .fluid()
                .color(0x0925BE)
                .components(Carbon, 4, Hydrogen, 6, Oxygen, 2)
                .build();

        GTQTMaterials.Tetramethylcyclobutanediol = new Material.Builder(getMaterialsId(), gtqtcoreId("tetramethylcyclobutanediol"))
                .fluid()
                .color(Dimethylketene.getMaterialRGB() + Hydrogen.getMaterialRGB())
                .components(Carbon, 8, Hydrogen, 16, Oxygen, 2)
                .build();

        GTQTMaterials.CBDOPolycarbonate = new Material.Builder(getMaterialsId(), gtqtcoreId("cbdo_polycarbonate"))
                .ingot()
                .fluid()
                .color(0xDFDFDF)
                .flags(DISABLE_DECOMPOSITION, NO_SMASHING, NO_SMELTING, GENERATE_PLATE)
                .components(Carbon, 9, Hydrogen, 14, Oxygen, 3)
                .build();

        GTQTMaterials.DiphenylCarbonate = new Material.Builder(getMaterialsId(), gtqtcoreId("diphenyl_carbonate"))
                .fluid()
                .color(0xEE3A8C)
                .components(Carbon, 13, Hydrogen, 10, Oxygen, 3)
                .build();

        GTQTMaterials.BPAPolycarbonate = new Material.Builder(getMaterialsId(), gtqtcoreId("bpa_polycarbonate"))
                .ingot()
                .fluid()
                .color(0xE3EBDA)
                .flags(DISABLE_DECOMPOSITION, NO_SMASHING, NO_SMELTING, GENERATE_PLATE)
                .components(Carbon, 16, Hydrogen, 14, Oxygen, 3)
                .build();

        //  24296 Californium Nitrite
        GTQTMaterials.CaliforniumNitrite = new Material.Builder(getMaterialsId(), gtqtcoreId("californium_nitrite"))
                .dust()
                .color(0x914626)
                .iconSet(ROUGH)
                .components(Californium, 1, Nitrogen, 3, Oxygen, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("Cf(NO2)3", true);

        //  24297 Californium Dioxide
        GTQTMaterials.CaliforniumDioxide = new Material.Builder(getMaterialsId(), gtqtcoreId("californium_dioxide"))
                .dust()
                .color(0x912D01)
                .iconSet(DULL)
                .components(Californium, 1, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24298 Californium Hexachloride
        GTQTMaterials.CaliforniumHexachloride = new Material.Builder(getMaterialsId(), gtqtcoreId("californium_hexachloride"))
                .fluid()
                .color(Californium.getMaterialRGB() + Chlorine.getMaterialRGB())
                .components(Californium, 2, Chlorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24299 Californium Hexafluoride
        GTQTMaterials.CaliforniumHexafluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("californium_hexafluoride"))
                .gas()
                .color(0xBBFFFF)
                .components(Californium, 2, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24300 Californium-252 Hexafluoride
        GTQTMaterials.Californium252Hexafluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("californium_252_hexafluoride"))
                .gas()
                .color(0xA4D3EE)
                .components(Californium252, 2, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24301 Steam Cracked Californium-252 Hexafluoride
        GTQTMaterials.SteamCrackedCalifornium252Hexafluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("steam_cracked_californium_252_hexafluoride"))
                .gas()
                .color(0x9F79EE)
                .components(Californium252, 2, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.Californium252Dioxide = new Material.Builder(getMaterialsId(), gtqtcoreId("californium_252_dioxide"))
                .dust()
                .color(0x912D01)
                .iconSet(ROUGH)
                .components(Californium252, 1, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();


        GTQTMaterials.Celestite = new Material.Builder(getMaterialsId(), gtqtcoreId("celestite"))
                .gem()
                .color(0x4AE3E6)
                .iconSet(OPAL)
                .components(Strontium, 1, Sulfur, 1, Oxygen, 4)
                .flags(CRYSTALLIZABLE, DISABLE_DECOMPOSITION, GENERATE_LENS)
                .build();

        GTQTMaterials.StrontiumCarbonate = new Material.Builder(getMaterialsId(), gtqtcoreId("strontium_carbonate"))
                .dust()
                .color(0x1DAFD3)
                .iconSet(SAND)
                .components(Strontium, 1, Carbon, 1, Oxygen, 3)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.StrontiumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("stronium_oxide"))
                .dust()
                .color(0x16839E)
                .iconSet(BRIGHT)
                .components(Strontium, 1, Oxygen, 1)
                .build();

        GTQTMaterials.AcidicPyrochlore = new Material.Builder(getMaterialsId(), gtqtcoreId("acidic_pyrochlore"))
                .dust()
                .color(Pyrochlore.getMaterialRGB() + SulfuricAcid.getMaterialRGB())
                .iconSet(SHINY)
                .components(Pyrochlore, 1, SulfuricAcid, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.ThoriumUraniumSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("thorium_uranium_solution"))
                .fluid()
                .color(Thorium.getMaterialRGB() + Uranium235.getMaterialRGB())
                .iconSet(DULL)
                .build()
                .setFormula("?SO4", true);

        GTQTMaterials.LeachingPyrochlore = new Material.Builder(getMaterialsId(), gtqtcoreId("leaching_pyrochlore"))
                .dust()
                .color(0xE2502C)
                .iconSet(BRIGHT)
                .build()
                .setFormula("(Nb2O5)9Ta2O5?", true);

        GTQTMaterials.BariumStrontiumRadiumSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("barium_strontium_radium_solution"))
                .fluid()
                .color(Barite.getMaterialRGB())
                .components(Barite, 1, Gypsum, 1, Celestite, 1, Radium, 1, Water, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.FluoroniobicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("fluoroniobic_acid"))
                .liquid(new FluidBuilder().attributes(ACID))
                .color(Niobium.getMaterialRGB() + HydrofluoricAcid.getMaterialRGB())
                .components(Niobium, 1, Hydrogen, 1, Fluorine, 7)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.Oxypentafluoroniobate = new Material.Builder(getMaterialsId(), gtqtcoreId("oxypentafluoroniobate"))
                .fluid()
                .color(0x17F742)
                .components(Hydrogen, 2, Niobium, 1, Oxygen, 1, Fluorine, 5)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.Heptafluorotantalate = new Material.Builder(getMaterialsId(), gtqtcoreId("heptafluorotantalate"))
                .fluid()
                .color(0x16EB3F)
                .components(Hydrogen, 2, Tantalum, 1, Fluorine, 7)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.PotassiumFluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_fluoride"))
                .dust()
                .color(Potassium.getMaterialRGB() + Fluorine.getMaterialRGB())
                .iconSet(ROUGH)
                .components(Potassium, 1, Fluorine, 1)
                .build();

        GTQTMaterials.PotassiumFluoniobate = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_fluoniobate"))
                .dust()
                .color(PotassiumFluoride.getMaterialRGB() + FluoroniobicAcid.getMaterialRGB())
                .iconSet(BRIGHT)
                .components(Potassium, 2, Niobium, 1, Fluorine, 7)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.PotassiumFluotantalate = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_fluotantalate"))
                .dust()
                .color(Tantalum.getMaterialRGB() + PotassiumFluoniobate.getMaterialRGB())
                .iconSet(BRIGHT)
                .components(Potassium, 2, Tantalum, 1, Fluorine, 7)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.UraniumThoriumNitrate = new Material.Builder(getMaterialsId(), gtqtcoreId("uranium_thorium_nitrate"))
                .dust()
                .color(Uranium238.getMaterialRGB() + Thorium.getMaterialRGB() + Nitrogen.getMaterialRGB())
                .iconSet(SHINY)
                .build()
                .setFormula("UO2(NO3)2Th(NO3)4", true);

        GTQTMaterials.UraniumOxideThoriumNitrate = new Material.Builder(getMaterialsId(), gtqtcoreId("uranium_oxide_thorium_nitrate"))
                .dust()
                .color(Uranium238.getMaterialRGB() + Oxygen.getMaterialRGB())
                .iconSet(SHINY)
                .build()
                .setFormula("UO2Th(NO3)4", true);

        GTQTMaterials.ThoriumNitrateSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("thorium_nitrate_solution"))
                .fluid()
                .color(Thorium.getMaterialRGB())
                .build()
                .setFormula("Th(NO3)4(H2O)", true);

        GTQTMaterials.ThoriumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("thorium_oxide"))
                .dust()
                .color(Thorium.getMaterialRGB() + Oxygen.getMaterialRGB())
                .components(Thorium, 1, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.TributylPhosphate = new Material.Builder(getMaterialsId(), gtqtcoreId("tributyl_phosphate"))
                .fluid()
                .color(0xBED323)
                .components(Carbon, 12, Hydrogen, 27, Phosphorus, 1, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(C4H9)3PO4", true);

        GTQTMaterials.MesitylOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("mesityl_oxide"))
                .fluid()
                .color(0x783E50)
                .components(Carbon, 6, Hydrogen, 10, Oxygen, 1)
                .build();

        GTQTMaterials.MethylIsobutylKetone = new Material.Builder(getMaterialsId(), gtqtcoreId("methyl_isobutyl_ketone"))
                .fluid()
                .color(0x2F5687)
                .components(Carbon, 6, Hydrogen, 12, Oxygen, 1)
                .build();

        GTQTMaterials.TBPMIBKSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("tbp_mibk_solutione"))
                .fluid()
                .color(0xBED323)
                .components(TributylPhosphate, 1, MethylIsobutylKetone, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.DimethylCarbonate = new Material.Builder(getMaterialsId(), gtqtcoreId("dimethyl_carbonate"))
                .fluid()
                .color(0xC5EB9E)
                .components(Carbon, 3, Hydrogen, 6, Oxygen, 3)
                .build()
                .setFormula("(CH3O)2CO", true);

        GTQTMaterials.DimethylamineHydrochloride = new Material.Builder(getMaterialsId(), gtqtcoreId("dimethylamine_hydrochloride"))
                .fluid()
                .color(0xE3EBDC)
                .components(Dimethylamine, 1, HydrochloricAcid, 1)
                .build()
                .setFormula("C2H8NCl", true);

        GTQTMaterials.PotassiumFormate = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_formate"))
                .dust()
                .color(0x74B5A9)
                .components(Carbon, 1, Hydrogen, 3, Oxygen, 1, Potassium, 1)
                .build();

        //不稳定的氟化肼
        GTQTMaterials.UnstableHydrazineFluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("unstable_hydrazine_fluoride"))
                .fluid()
                .color(0x33A1C9) // 浅蓝色
                .components(Methylhydrazine, 1, HydrofluoricAcid, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //氟化肼燃料
        GTQTMaterials.HydrazineFluorideFuel = new Material.Builder(getMaterialsId(), gtqtcoreId("hydrazine_fluoride_fuel"))
                .fluid()
                .color(0x66B2FF) // 浅天蓝色
                .components(Methylhydrazine, 1, HydrofluoricAcid, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //肼氯（Hydrazinium Chloride）
        GTQTMaterials.HydraziniumChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("hydrazinium_chloride"))
                .fluid()
                .color(0x99C6E3) // 浅青色
                .components(Methylhydrazine, 1, HydrochloricAcid, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.SodiumNitrate = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_nitrate"))
                .dust().fluid()
                .color(0x846684)
                .iconSet(MaterialIconSet.ROUGH)
                .components(Sodium, 1, Nitrogen, 1, Oxygen, 3)
                .build();

        //发烟硝酸
        GTQTMaterials.SodiumNitrateSulfuricAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_nitrate_sulfuric_acid"))
                .fluid()
                .color(0xFF6347) // 橙红色
                .components(SodiumNitrate, 1, SulfuricAcid, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //DinitrogenPentoxide 五氧化二氮
        GTQTMaterials.DinitrogenPentoxide = new Material.Builder(getMaterialsId(), gtqtcoreId("dinitrogen_pentoxide"))
                .fluid()
                .color(0xFFA07A) // 浅珊瑚色
                .components(Nitrogen, 5, Oxygen, 10)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //HydraziniumDinitramide 肼二硝酰胺
        GTQTMaterials.HydraziniumDinitramide = new Material.Builder(getMaterialsId(), gtqtcoreId("hydrazinium_dinitramide"))
                .dust()
                .color(0xFF4500) // 橙红色
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("N₂H₅N(NO₂)₂", true);


        GTQTMaterials.SodiumTungstate = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_tungstate"))
                .fluid()
                .color(0x595E54)
                .components(Sodium, 1, Tungsten, 1, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.SodiumPhosphotungstate = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_phosphotungstate"))
                .dust()
                .color(0x4D3635)
                .components(Oxygen, 40, Tungsten, 12, Sodium, 3, Phosphorus, 1)
                .flags(DECOMPOSITION_BY_ELECTROLYZING)
                .build()
                .setFormula("(WO3)12Na3PO4", true);

        GTQTMaterials.SodiumMolybdate = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_molybdate"))
                .dust()
                .color(0xCCCC99)
                .iconSet(ROUGH)
                .components(Sodium, 2, Molybdenum, 1, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.FluxedElectrum = new Material.Builder(getMaterialsId(), gtqtcoreId("fluxed_electrum"))
                .ingot()
                .fluid()
                .color(0xFFE049)
                .iconSet(BRIGHT)
                .components(Electrum, 8, RoseGold, 4, SterlingSilver, 4, NaquadahEnriched, 2)
                .blast(b -> b
                        .temp(8400, BlastProperty.GasTier.HIGHER)
                        .blastStats(VA[ZPM], 877)
                        .vacuumStats(VA[IV], 405))
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_FRAME, GENERATE_RING)
                .build();

        GTQTMaterials.BismuthVanadate = new Material.Builder(getMaterialsId(), gtqtcoreId("bismuth_vanadate"))
                .dust()
                .color(0xFFAF33)
                .iconSet(SHINY)
                .components(Bismuth, 1, Vanadium, 1, Oxygen, 4)
                .build();

        GTQTMaterials.MaleicAnhydride = new Material.Builder(getMaterialsId(), gtqtcoreId("maleic_anhydride"))
                .liquid()
                .color(0x610C2F)
                .components(Carbon, 4, Hydrogen, 2, Oxygen, 3)
                .build();

        GTQTMaterials.NickelOxideHydroxide = new Material.Builder(getMaterialsId(), gtqtcoreId("nickel_oxide_hydroxide"))
                .dust()
                .color(0xa2f2a2)
                .iconSet(METALLIC)
                .build()
                .setFormula("NiO(OH)", true);

        GTQTMaterials.LithiumCarbonateSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("lithium_carbonate_solution"))
                .liquid()
                .color((Lithium.getMaterialRGB() + Carbon.getMaterialRGB() + Oxygen.getMaterialRGB()) / 3)
                .iconSet(FLUID)
                .build()
                .setFormula("Li2CO3(H2O)", true);

        GTQTMaterials.LithiumCobaltOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("lithium_cobalt_oxide"))
                .dust()
                .color(0xd2a4f3)
                .iconSet(SHINY)
                .build()
                .setFormula("LiCoO", true);

        GTQTMaterials.BariumTriflate = new Material.Builder(getMaterialsId(), gtqtcoreId("barium_triflate"))
                .dust()
                .color((Barium.getMaterialRGB() + Fluorine.getMaterialRGB()) / 2)
                .iconSet(SHINY)
                .build()
                .setFormula("Ba(OSO2CF3)2", true);

        GTQTMaterials.LithiumTriflate = new Material.Builder(getMaterialsId(), gtqtcoreId("lithium_triflate"))
                .dust()
                .color(0xe2dae3)
                .iconSet(SHINY)
                .build()
                .setFormula("LiCSO3F3", true);

        GTQTMaterials.Xylose = new Material.Builder(getMaterialsId(), gtqtcoreId("xylose"))
                .dust()
                .color(0xd2a4f3)
                .iconSet(ROUGH)
                .build()
                .setFormula("C5H10O5", true);

        GTQTMaterials.SodiumAlginateSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_alginate_solution"))
                .liquid()
                .color(0xca8642)
                .iconSet(FLUID)
                .build()
                .setFormula("NaC6H7O6", true);

        GTQTMaterials.CalciumAlginate = new Material.Builder(getMaterialsId(), gtqtcoreId("calcium_alginate"))
                .dust()
                .color(0x654321)
                .iconSet(ROUGH)
                .build()
                .setFormula("CaC12H14O12", true);

        GTQTMaterials.Trimethylsilane = new Material.Builder(getMaterialsId(), gtqtcoreId("trimethylsilane"))
                .liquid()
                .color(0xd2a4f3)
                .iconSet(FLUID)
                .build()
                .setFormula("C3H10Si", true);

        GTQTMaterials.CetaneTrimethylAmmoniumBromide = new Material.Builder(getMaterialsId(), gtqtcoreId("cetane_trimethyl_ammonium_bromide"))
                .liquid()
                .color(0xb9c1c9)
                .iconSet(FLUID)
                .build()
                .setFormula("C19H42BrN", true);

        GTQTMaterials.SiliconNanoparticles = new Material.Builder(getMaterialsId(), gtqtcoreId("silicon_nanoparticles"))
                .dust()
                .color(Silicon.getMaterialRGB())
                .iconSet(SHINY)
                .build()
                .setFormula("Si?", true);

        GTQTMaterials.Glucose = new Material.Builder(getMaterialsId(), gtqtcoreId("glucose"))
                .dust()
                .color((Sugar.getMaterialRGB() + 5))
                .iconSet(ROUGH)
                .build()
                .setFormula("C6H12O6", true);

        GTQTMaterials.StreptococcusPyogenes = new Material.Builder(getMaterialsId(), gtqtcoreId("streptococcus_pyogenes"))
                .dust()
                .color(0x1c3b15)
                .iconSet(ROUGH)
                .build()
                .setFormula("Bacteria", true);

        GTQTMaterials.Sorbose = new Material.Builder(getMaterialsId(), gtqtcoreId("sorbose"))
                .dust()
                .color(Glucose.getMaterialRGB())
                .iconSet(DULL)
                .build()
                .setFormula("C6H12O6", true);

        GTQTMaterials.AscorbicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("ascorbic_acid"))
                .liquid(new FluidBuilder().attribute(ACID))
                .color(0xe6cd00)
                .iconSet(FLUID)
                .build()
                .setFormula("C6H8O6", true);

        GTQTMaterials.DehydroascorbicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("dehydroascorbic_acid"))
                .liquid(new FluidBuilder().attribute(ACID))
                .color(0xe6cd00)
                .iconSet(FLUID)
                .build()
                .setFormula("C6H6O6", true);

        GTQTMaterials.GalliumChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("gallium_chloride"))
                .dust()
                .color(0x92867a)
                .iconSet(ROUGH)
                .build()
                .setFormula("GaCl3", true);

        GTQTMaterials.Halloysite = new Material.Builder(getMaterialsId(), gtqtcoreId("halloysite"))
                .dust()
                .color(0x23423a)
                .iconSet(ROUGH)
                .build()
                .setFormula("Al9Si10O50Ga", true);

        GTQTMaterials.SulfurCoatedHalloysite = new Material.Builder(getMaterialsId(), gtqtcoreId("sulfur_coated_halloysite"))
                .dust()
                .color(0x23973a)
                .iconSet(ROUGH)
                .build()
                .setFormula("S2C2(Al9Si10O50Ga)", true);

        GTQTMaterials.FluorideBatteryElectrolyte = new Material.Builder(getMaterialsId(), gtqtcoreId("fluoride_battery_electrolyte"))
                .dust()
                .color(0x9a628a)
                .iconSet(SHINY)
                .build()
                .setFormula("La9BaF29(C8H7F)", true);

        GTQTMaterials.LanthanumNickelOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("lanthanum_nickel_oxide"))
                .dust()
                .color(0x23973a)
                .iconSet(SHINY)
                .build()
                .setFormula("La2NiO4", true);


        GTQTMaterials.AbyssalAlloy = new Material.Builder(getMaterialsId(), gtqtcoreId("abyssal_alloy"))
                .ingot(6).liquid()
                .color(0x9E706A)
                .iconSet(METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.UHV], 2, 8)
                .components(StainlessSteel, 5, TungstenCarbide, 5, Nichrome, 5, Bronze, 5, IncoloyMA956, 5, Iodine, 1, Germanium, 1, Radon, 1)
                .blast(9625)
                .build();

        GTQTMaterials.NaquadriaticTaranium = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadriatic_taranium"))
                .ingot()
                .color((Naquadria.getMaterialRGB() + Taranium.getMaterialRGB()) / 2)
                .iconSet(SHINY)
                .flags(STD_METAL, GENERATE_LONG_ROD)
                .components(Naquadria, 1, Taranium, 1)
                .cableProperties(GTValues.V[GTValues.UXV], 2, 32)
                .blast(11200)
                .build();

        GTQTMaterials.CarbonSulfide = new Material.Builder(getMaterialsId(), gtqtcoreId("carbon_sulfide"))
                .liquid()
                .color(0x40ffbf)
                .iconSet(FLUID)
                .build()
                .setFormula("CS2", true);

        GTQTMaterials.Biperfluoromethanedisulfide = new Material.Builder(getMaterialsId(), gtqtcoreId("biperfluoromethanedisulfide"))
                .liquid()
                .color(0x3ada40)
                .iconSet(FLUID)
                .build()
                .setFormula("C2F6S2", true);

        GTQTMaterials.BariumTriflateSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("barium_triflate_solution"))
                .liquid()
                .color((Barium.getMaterialRGB() + Fluorine.getMaterialRGB()) / 2)
                .iconSet(FLUID)
                .build()
                .setFormula("(H2O)3(Hg)C2BaF6O6S2", true);

        GTQTMaterials.Caliche = new Material.Builder(getMaterialsId(), gtqtcoreId("caliche"))
                .dust(3)
                .color(0xeb9e3f)
                .ore()
                .iconSet(DULL)
                .components(SodiumNitrate, 1, Potassium, 1, RockSalt, 1, Sodium, 1, Iodine, 1)
                .build();

        GTQTMaterials.CalicheIodateBrine = new Material.Builder(getMaterialsId(), gtqtcoreId("caliche_iodate_brine"))
                .liquid()
                .color(0xffe6660)
                .iconSet(FLUID)
                .build()
                .setFormula("(H2O)NaNO3KNO3KClNaIO3", true);

        GTQTMaterials.IodideSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("iodide_solution"))
                .liquid()
                .color(0x08081c)
                .iconSet(FLUID)
                .build()
                .setFormula("(H2O)NaNO3KNO3KClNaI", true);

        GTQTMaterials.CalicheIodineBrine = new Material.Builder(getMaterialsId(), gtqtcoreId("caliche_iodine_brine"))
                .liquid()
                .color(0xffe6660)
                .iconSet(FLUID)
                .build()
                .setFormula("(H2O)NaNO3KNO3KClNaOHI", true);

        GTQTMaterials.CalicheNitrateSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("caliche_nitrate_solution"))
                .liquid()
                .color(0xffe6660)
                .iconSet(FLUID)
                .build()
                .setFormula("(H2O)NaNO3KNO3KClNaOH", true);

        GTQTMaterials.KeroseneIodineSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("kerosene_iodine_solution"))
                .liquid()
                .color(0x08081c)
                .iconSet(FLUID)
                .build()
                .setFormula("C12H26I", true);

        GTQTMaterials.IodizedOil = new Material.Builder(getMaterialsId(), gtqtcoreId("iodized_oil"))
                .liquid()
                .color(0x666666)
                .iconSet(FLUID)
                .build();

        GTQTMaterials.IodizedBrine = new Material.Builder(getMaterialsId(), gtqtcoreId("iodized_brine"))
                .liquid()
                .color(0x525242)
                .iconSet(FLUID)
                .build()
                .setFormula("I?", true);

        GTQTMaterials.IodineBrineMix = new Material.Builder(getMaterialsId(), gtqtcoreId("iodine_brine_mix"))
                .liquid()
                .color(0x525242)
                .iconSet(FLUID)
                .build()
                .setFormula("I??", true);

        GTQTMaterials.IodineSlurry = new Material.Builder(getMaterialsId(), gtqtcoreId("iodine_slurry"))
                .liquid()
                .color(0x08081c)
                .iconSet(FLUID)
                .build()
                .setFormula("I?", true);

        Cellulose = new Material.Builder(getMaterialsId(), gtqtcoreId("cellulose"))
                .dust()
                .color(0xfefefc)
                .iconSet(DULL)
                .build()
                .setFormula("C6H10O5", true);

        TriniumTrioxide = new Material.Builder(getMaterialsId(), gtqtcoreId("trinium_trioxide"))
                .dust()
                .color(0xC037C5)
                .iconSet(METALLIC)
                .components(Trinium, 2, Oxygen, 3)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.SodiumThiocyanate = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_thiocyanate"))
                .liquid()
                .color((Sodium.getMaterialRGB() + Sulfur.getMaterialRGB()) / 2)
                .iconSet(FLUID)
                .build()
                .setFormula("NaSCN", true);

        PolyacrylonitrileSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("polyacrylonitrile_solution"))
                .liquid()
                .color(0x9999ff)
                .iconSet(FLUID)
                .build()
                .setFormula("(C3H3N)n(NaSCN)", true);

        AcrylicFibers = new Material.Builder(getMaterialsId(), gtqtcoreId("acrylic_fibers"))
                .dust()
                .color(0xfdfdfb)
                .iconSet(FINE)
                .build()
                .setFormula("(C5O2H8)n", true);

        WetFormamide = new Material.Builder(getMaterialsId(), gtqtcoreId("wet_formamide"))
                .liquid()
                .color(0x33CCFF)
                .iconSet(FLUID)
                .build()
                .setFormula("(H2O)CH3NO", true);

        Formamide = new Material.Builder(getMaterialsId(), gtqtcoreId("formamide"))
                .liquid()
                .color(0x33CCFF)
                .iconSet(FLUID)
                .build()
                .setFormula("CH3NO", true);

        HydroxylamineDisulfate = new Material.Builder(getMaterialsId(), gtqtcoreId("hydroxylamine_disulfate"))
                .liquid()
                .color(0x99add6)
                .iconSet(FLUID)
                .build()
                .setFormula("(NH3OH)2(NH4)2(SO4)2", true);

        Amidoxime = new Material.Builder(getMaterialsId(), gtqtcoreId("amidoxime"))
                .liquid()
                .color(0x66ff33)
                .iconSet(FLUID)
                .build()
                .setFormula("H3N2O(CH)", true);

        PureUranylNitrateSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("pure_uranyl_nitrate"))
                .liquid()
                .color(0x33bd45)
                .iconSet(FLUID)
                .build()
                .setFormula("(H2O)UO2(NO3)2", true);

        UranylNitrate = new Material.Builder(getMaterialsId(), gtqtcoreId("uranyl_nitrate"))
                .dust()
                .color(0x33bd45)
                .iconSet(SHINY)
                .build()
                .setFormula("UO2(NO3)2", true);

        DiluteNitricAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("dilute_nitric_acid"))
                .liquid()
                .color((NitricAcid.getMaterialRGB() + Water.getMaterialRGB()) / 2)
                .iconSet(FLUID)
                .build()
                .setFormula("(H2O)HNO3", true);

        ConcentratedBrine = new Material.Builder(getMaterialsId(), gtqtcoreId("concentrated_brine"))
                .liquid()
                .color(0xfcfc95)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        BrominatedBrine = new Material.Builder(getMaterialsId(), gtqtcoreId("brominated_brine"))
                .liquid()
                .color(0xfdd48d)
                .iconSet(FLUID)
                .build()
                .setFormula("Br?", true);

        AcidicBrominatedBrine = new Material.Builder(getMaterialsId(), gtqtcoreId("acidic_brominated_brine"))
                .liquid(new FluidBuilder().attribute(ACID))
                .color(0xfdd48d)
                .iconSet(FLUID)
                .build()
                .setFormula("(H2SO4)Cl?", true);

        CalciumFreeBrine = new Material.Builder(getMaterialsId(), gtqtcoreId("calcium_free_brine"))
                .liquid()
                .color(0xfcfca6)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        CalciumSalts = new Material.Builder(getMaterialsId(), gtqtcoreId("calcium_salts"))
                .dust()
                .color(Calcium.getMaterialRGB() - 10)
                .iconSet(ROUGH)
                .components(Calcite, 1, Gypsum, 1)
                .build();

        SodiumFreeBrine = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_free_brine"))
                .liquid()
                .color(0xfcfcb1)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        SodiumSalts = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_salts"))
                .dust()
                .color(Sodium.getMaterialRGB() - 5)
                .iconSet(ROUGH)
                .build()
                .setFormula("NaCl?", true);

        PotassiumFreeBrine = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_free_brine"))
                .liquid()
                .color(0xfcfcbc)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        PotassiumMagnesiumSalts = new Material.Builder(getMaterialsId(), gtqtcoreId("kmg_salts"))
                .dust()
                .color(0xcacac8)
                .iconSet(ROUGH)
                .build()
                .setFormula("KClMg(SO4)K2(SO4)KF", true);

        BoronFreeSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("boron_free_solution"))
                .liquid()
                .color(0xfcfccd)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        CalciumMagnesiumSalts = new Material.Builder(getMaterialsId(), gtqtcoreId("camg_salts"))
                .dust()
                .color(0xcacac8)
                .iconSet(ROUGH)
                .build()
                .setFormula("Ca(CO3)(Sr(CO3))(CO2)(MgO)", true);

        SodiumLithiumSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_lithium_solution"))
                .liquid()
                .color(0xfcfccd)
                .iconSet(FLUID)
                .build()
                .setFormula("NaLi?", true);

        SodiumAluminiumHydride = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_aluminium_hydride"))
                .dust()
                .color(0x98cafc)
                .iconSet(ROUGH)
                .build()
                .setFormula("NaAlH4", true);

        Fructose = new Material.Builder(getMaterialsId(), gtqtcoreId("fructose"))
                .dust()
                .color(0x98cafc)
                .iconSet(ROUGH)
                .build()
                .setFormula("C6H12O6", true);

        SodiumAzide = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_azide"))
                .dust()
                .color((Sodium.getMaterialRGB() + Nitrogen.getMaterialRGB()) / 2)
                .iconSet(FINE)
                .build()
                .setFormula("NaN3", true);

        LithiumHydroxideSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("lithium_hydroxide_solution"))
                .liquid()
                .color((Lithium.getMaterialRGB() + Oxygen.getMaterialRGB() + Hydrogen.getMaterialRGB()) / 3)
                .iconSet(FLUID)
                .build()
                .setFormula("H2O)LiOH", true);

        Glucosamine = new Material.Builder(getMaterialsId(), gtqtcoreId("glucosamine"))
                .dust()
                .color(0x98cafc)
                .iconSet(FINE)
                .build()
                .setFormula("C6H13NO5", true);

        Chitosan = new Material.Builder(getMaterialsId(), gtqtcoreId("chitosan"))
                .liquid()
                .color(0xb1bd42)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        SodiumSulfateSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_sulfate_solution"))
                .liquid()
                .color((SodiumSulfate.getMaterialRGB() + 30))
                .iconSet(FLUID)
                .build()
                .setFormula("Na2SO4(H2O)", true);

        BoronOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("boron_oxide"))
                .dust()
                .color((Boron.getMaterialRGB() + Oxygen.getMaterialRGB()) / 2)
                .iconSet(ROUGH)
                .build()
                .setFormula("B2O3", true);

        LithiumAluminiumFluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("lithium_aluminium_fluoride"))
                .dust()
                .color((Lithium.getMaterialRGB() + Aluminium.getMaterialRGB() + Fluorine.getMaterialRGB()) / 3)
                .iconSet(ROUGH)
                .build()
                .setFormula("AlF4Li", true);

        //cellulose_fermentation_residue
        CelluloseFermentationResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("cellulose_fermentation_residue"))
                .fluid()
                .color(0xfffcfc)
                .build();

        DimethylthiocarbamoilChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("dimethylthiocarbamoil_chloride"))
                .liquid()
                .color(0xd9ff26)
                .iconSet(FLUID)
                .build()
                .setFormula("(CH3)2NC(S)Cl", true);

        Mercaptophenol = new Material.Builder(getMaterialsId(), gtqtcoreId("mercaptophenol"))
                .liquid()
                .color(0xbaaf18)
                .iconSet(FLUID)
                .build()
                .setFormula("C6H6OS", true);

        AmineMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("amine_mixture"))
                .liquid()
                .color((Methanol.getMaterialRGB() - 20 + Ammonia.getMaterialRGB() - 10) / 2)
                .iconSet(FLUID)
                .build()
                .setFormula("(NH3)CH4", true);

        //Cellulose exposure solution
        CelluloseExposureSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("cellulose_exposure_solution"))
                .liquid()
                .color(0x4A4A4A)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build();

        SodiumTungstate = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_tungstate"))
                .liquid()
                .color(0x7a7777)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .components(Sodium, 2, Tungsten, 1, Oxygen, 4)
                .build();

        IridiumCyclooctadienylChlorideDimer = new Material.Builder(getMaterialsId(), gtqtcoreId("iridium_cyclooctadienyl_chloride_dimer"))
                .dust()
                .color(0x888079)
                .iconSet(SHINY)
                .build()
                .setFormula("Ir2(C8H12)2Cl2", true);

        ChlorodiisopropylPhosphine = new Material.Builder(getMaterialsId(), gtqtcoreId("chlorodiisopropyl_phosphine"))
                .liquid()
                .color(0xa2c122)
                .iconSet(FLUID)
                .build();

        AmmoniumPersulfate = new Material.Builder(getMaterialsId(), gtqtcoreId("ammonium_persulfate"))
                .liquid()
                .color(0x6464f5)
                .iconSet(FLUID)
                .build()
                .setFormula("(NH4)2S2O8", true);

        PolystyreneNanoParticles = new Material.Builder(getMaterialsId(), gtqtcoreId("polystryrene_nanoparticles"))
                .dust()
                .color(0x888079)
                .iconSet(FINE)
                .build()
                .setFormula("(C8H8)n", true);

        Celestine = new Material.Builder(getMaterialsId(), gtqtcoreId("celestine"))
                .dust()
                .color(0x9db1b8)
                .iconSet(SHINY)
                .components(Strontium, 1, Sulfur, 1, Oxygen, 4)
                .build();

        MagnesiumSulfate = new Material.Builder(getMaterialsId(), gtqtcoreId("magnesium_sulfate"))
                .dust()
                .color(0xcacac8)
                .iconSet(DULL)
                .build()
                .setFormula("MgSO4", true);

        StrontiumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("strontium_oxide"))
                .dust()
                .color(0xcacac8)
                .iconSet(SHINY)
                .build()
                .setFormula("SrO", true);

        ChilledBrine = new Material.Builder(getMaterialsId(), gtqtcoreId("chilled_brine"))
                .liquid()
                .color(0xfcfc95)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        MagnesiumContainingBrine = new Material.Builder(getMaterialsId(), gtqtcoreId("magnesium_containing_brine"))
                .liquid()
                .color(0xfcfcbc)
                .iconSet(FLUID)
                .build()
                .setFormula("Mg?", true);

        GTQTMaterials.DebrominatedWater = new Material.Builder(getMaterialsId(), gtqtcoreId("debrominated_brine"))
                .liquid()
                .color(0x0000ff)
                .iconSet(FLUID)
                .build()
                .setFormula("H2O", true);

        GTQTMaterials.SulfuricBromineSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("sulfuric_bromine_solution"))
                .liquid()
                .color(0xff5100)
                .iconSet(FLUID)
                .build()
                .setFormula("H2SO4Br(H2O)Cl2", true);

        GTQTMaterials.LithiumChlorideSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("lithium_chloride_solution"))
                .liquid()
                .color((Lithium.getMaterialRGB() + Chlorine.getMaterialRGB()))
                .iconSet(FLUID)
                .build()
                .setFormula("LiCl(H2O)", true);

        GTQTMaterials.AluminiumHydride = new Material.Builder(getMaterialsId(), gtqtcoreId("aluminium_hydride"))
                .dust()
                .color(0x0b585c)
                .iconSet(ROUGH)
                .build()
                .setFormula("AlH3", true);

        GTQTMaterials.SodiumHydride = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_hydride"))
                .dust()
                .color(0xcacac8)
                .iconSet(ROUGH)
                .build()
                .setFormula("NaH", true);

        GTQTMaterials.LithiumAluminiumHydride = new Material.Builder(getMaterialsId(), gtqtcoreId("lithium_aluminium_hydride"))
                .dust()
                .color(0xc0defc)
                .iconSet(ROUGH)
                .build()
                .setFormula("LiAlH4", true);

        GTQTMaterials.SodiumAzanide = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_azanide"))
                .dust()
                .color((Sodium.getMaterialRGB() + Hydrogen.getMaterialRGB() + Nitrogen.getMaterialRGB()) / 3)
                .iconSet(FINE)
                .build()
                .setFormula("NaNH2", true);

        GTQTMaterials.SodiumHydroxideSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_hydroxide_solution"))
                .liquid()
                .color(SodiumHydroxide.getMaterialRGB() + 50)
                .iconSet(FLUID)
                .build()
                .setFormula("(H2O)NaOH", true);

        GTQTMaterials.BoronFluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("boron_fluoride"))
                .liquid()
                .color(0xD5D2D7)
                .iconSet(FLUID)
                .build()
                .setFormula("BF3", true);

        GTQTMaterials.IsopropylAlcohol = new Material.Builder(getMaterialsId(), gtqtcoreId("isopropyl_alcohol"))
                .liquid()
                .color((Water.getMaterialRGB() + Propene.getMaterialRGB()) / 2)
                .iconSet(FLUID)
                .build()
                .setFormula("C3H8O", true);

        GTQTMaterials.BerylliumFluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("beryllium_fluoride"))
                .ingot(2)
                .color(0x757575)
                .iconSet(SHINY)
                .components(Beryllium, 1, Fluorine, 2)
                .build();

        GTQTMaterials.Oct1ene = new Material.Builder(getMaterialsId(), gtqtcoreId("1_octene"))
                .liquid()
                .color(0x7e8778)
                .iconSet(FLUID)
                .build()
                .setFormula("C8H16", true);

        GTQTMaterials.Ozone = new Material.Builder(getMaterialsId(), gtqtcoreId("ozone"))
                .gas()
                .liquid((new FluidBuilder())
                        .temperature(90)
                        .color(6719709)
                        .name("liquid_ozone")
                        .translation("gregtech.fluid.liquid_generic"))
                .color(0x9370DB)
                .iconSet(FLUID)
                .build()
                .setFormula("O3", true);
        Ozone.getProperty(PropertyKey.FLUID).setPrimaryKey(FluidStorageKeys.GAS);

        GTQTMaterials.NitrogenPentoxide = new Material.Builder(getMaterialsId(), gtqtcoreId("nitrogen_pentoxide"))
                .liquid()
                .color(0x0033C0)
                .iconSet(FLUID)
                .build()
                .setFormula("N2O5", true);

        GTQTMaterials.TitaniumNitrate = new Material.Builder(getMaterialsId(), gtqtcoreId("titanium_nitrate"))
                .dust()
                .color(0xFF0066)
                .iconSet(FINE)
                .build()
                .setFormula("TiNO3", true);

        GTQTMaterials.LithiumTitanate = new Material.Builder(getMaterialsId(), gtqtcoreId("lithium_titanate"))
                .ingot(5)
                .color(0xfe71a9)
                .iconSet(SHINY)
                .flags(GENERATE_PLATE)
                .components(Lithium, 2, Titanium, 1, Oxygen, 3)
                .build();

        GTQTMaterials.AcidicSaltWater = new Material.Builder(getMaterialsId(), gtqtcoreId("acidic_salt_water"))
                .liquid(new FluidBuilder().attribute(ACID))
                .color(0x006960)
                .iconSet(FLUID)
                .build()
                .setFormula("H2SO4(NaCl)3(H2O)3Cl2", true);

        GTQTMaterials.HotVapourMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("hot_vapour_mixture"))
                .gas()
                .color(0xff5100)
                .iconSet(FLUID)
                .build()
                .setFormula("H2SO4Br(H2O)2Cl2", true);

        GTQTMaterials.DampBromine = new Material.Builder(getMaterialsId(), gtqtcoreId("damp_bromine"))
                .liquid()
                .color(0xe17594)
                .iconSet(FLUID)
                .build()
                .setFormula("Br(H2O)", true);

        GTQTMaterials.EleAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("ele_acid"))
                .fluid()
                .color(0xe04800)
                .build();

        GTQTMaterials.PotassiumChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_chloride"))
                .fluid()
                .components(Potassium, 1, Chlorine, 1)
                .color(0xe04800)
                .build();

        GTQTMaterials.ManganeseIronArsenicPhosphide = new Material.Builder(getMaterialsId(), gtqtcoreId("manganese_iron_arsenic_phosphide"))
                .ingot()
                .color(0x03FCF0).iconSet(MaterialIconSet.METALLIC)
                .cableProperties(GTValues.V[4], 2, 4)
                .components(Manganese, 2, Iron, 2, Arsenic, 1, Phosphorus, 1)
                .blast(2100, BlastProperty.GasTier.LOW)
                .build();

        GTQTMaterials.PraseodymiumNickel = new Material.Builder(getMaterialsId(), gtqtcoreId("praseodymium_nickel"))
                .ingot()
                .color(0x03BAFC).iconSet(MaterialIconSet.METALLIC)
                .cableProperties(GTValues.V[4], 2, 4)
                .components(Praseodymium, 5, Nickel, 1)
                .blast(2100, BlastProperty.GasTier.MID)
                .build();

        GTQTMaterials.GadoliniumSiliconGermanium = new Material.Builder(getMaterialsId(), gtqtcoreId("gadolinium_silicon_germanium"))
                .ingot()
                .color(0x0388FC).iconSet(MaterialIconSet.SHINY)
                .cableProperties(GTValues.V[4], 2, 4)
                .components(Gadolinium, 5, Silicon, 2, Germanium, 2)
                .blast(2100, BlastProperty.GasTier.HIGH)
                .build();

        //氰乙酸
        GTQTMaterials.CyanoaceticAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("cyanoacetic_acid"))
                .dust()
                .color(0xCD00CD)
                .components(Carbon, 3, Hydrogen, 3, Nitrogen, 1, Oxygen, 2)
                .build();

        //氰乙酸乙酯
        GTQTMaterials.Ethylcyanoacetate = new Material.Builder(getMaterialsId(), gtqtcoreId("ethylcyanoacetate"))
                .fluid()
                .color(0xCDCD00)
                .components(Carbon, 5, Hydrogen, 7, Nitrogen, 1, Oxygen, 2)
                .build();

        //氰基丙烯酸酯聚合物
        GTQTMaterials.Cyanoacrylate = new Material.Builder(getMaterialsId(), gtqtcoreId("cyanoacrylate"))
                .fluid()
                .color(0xB4CDCD)
                .components(Carbon, 5, Hydrogen, 7, Nitrogen, 1, Oxygen, 2)
                .build();

        //ppb
        GTQTMaterials.PPB = new Material.Builder(getMaterialsId(), gtqtcoreId("ppb"))
                .ingot()
                .color(0xFF6347)
                .flags(GENERATE_FOIL, DISABLE_DECOMPOSITION, GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROUND, GENERATE_SMALL_GEAR
                        , GENERATE_SPRING, GENERATE_SPRING_SMALL)
                .components(Palladium, 1, Platinum, 1, Bismuth, 1)
                .blast(4500, MID)
                .build();

        GTQTMaterials.PPBFront = new Material.Builder(getMaterialsId(), gtqtcoreId("ppbfront"))
                .fluid()
                .color(0xFFC125)
                .flags(DISABLE_DECOMPOSITION)
                .components(Palladium, 1, Platinum, 1, Bismuth, 1)
                .build();

        //氧化铋
        GTQTMaterials.BismuthOxygen = new Material.Builder(getMaterialsId(), gtqtcoreId("bismuth_oxygen"))
                .dust()
                .color(0xC0FF3E)
                .flags(DISABLE_DECOMPOSITION)
                .components(Bismuth, 2, Oxygen, 3)
                .build();

        //乙酸铋
        GTQTMaterials.BismuthAcetate = new Material.Builder(getMaterialsId(), gtqtcoreId("bismuth_acetate"))
                .fluid()
                .color(0x9AFF9A)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 6, Hydrogen, 9, Bismuth, 1, Oxygen, 2)
                .build();

        //乙酸丙酯
        GTQTMaterials.PropylAcetate = new Material.Builder(getMaterialsId(), gtqtcoreId("propyl_acetate"))
                .fluid()
                .color(0x8B7355)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 5, Hydrogen, 10, Oxygen, 2)
                .build();

        //乙酰丙酮
        GTQTMaterials.Acetylacetone = new Material.Builder(getMaterialsId(), gtqtcoreId("acetylacetone"))
                .fluid()
                .color(0x8DB6CD)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 5, Hydrogen, 8, Oxygen, 2)
                .build();

        //乙酰丙酮铂
        GTQTMaterials.PlatinumBis = new Material.Builder(getMaterialsId(), gtqtcoreId("platinum_bis"))
                .fluid()
                .color(0x7AC5CD)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 10, Hydrogen, 14, Oxygen, 4, Platinum, 1)
                .build();

        //乙酰丙酮钯
        GTQTMaterials.PalladiumBis = new Material.Builder(getMaterialsId(), gtqtcoreId("palladium_bis"))
                .fluid()
                .color(0x76EE00)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 10, Hydrogen, 14, Oxygen, 4, Palladium, 1)
                .build();

        //玻璃釉
        GTQTMaterials.GlassGlaze = new Material.Builder(getMaterialsId(), gtqtcoreId("glass_glaze"))
                .fluid()
                .color(0x76EE00)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //肉桂酸
        GTQTMaterials.CinnamicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("cinnamic_acid"))
                .fluid()
                .color(0x8E8E38)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C6H5CH=CHCOOH", true);

        //肉桂酸乙酯
        GTQTMaterials.EthylCinnamate = new Material.Builder(getMaterialsId(), gtqtcoreId("ethyl_cinnamate"))
                .fluid()
                .color(0xA52A2A)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C6H5CH=CHCOOCH3", true);

        //肉桂酸钠
        GTQTMaterials.SodiumCinnamate = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_cinnamate"))
                .fluid()
                .color(0xA0522D)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C6H5CH=CHCOONa", true);

        //乙二酰氯
        GTQTMaterials.OxalylChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("oxalyl_chloride"))
                .fluid()
                .color(0xBCEE68)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 1, Oxygen, 1, Chlorine, 2)
                .build()
                .setFormula("COCl2", true);

        //五氯化磷
        GTQTMaterials.PhosphorusPentachloride = new Material.Builder(getMaterialsId(), gtqtcoreId("phosphorus_pentachloride"))
                .dust()
                .color(0xAB82FF)
                .flags(DISABLE_DECOMPOSITION)
                .components(Phosphorus, 1, Chlorine, 5)
                .build();

        //三氯氧磷
        GTQTMaterials.PhosphorusOxychloride = new Material.Builder(getMaterialsId(), gtqtcoreId("phosphorus_oxychloride"))
                .fluid()
                .color(0xBF3EFF)
                .flags(DISABLE_DECOMPOSITION)
                .components(Phosphorus, 1, Oxygen, 1, Chlorine, 3)
                .build();

        //肉桂酰氯
        GTQTMaterials.TransPhenylacryloylChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("trans_phenylacryloyl_chloride"))
                .fluid()
                .color(0xC1FFC1)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 9, Hydrogen, 7, Chlorine, 1, Oxygen, 1)
                .build();

        //聚乙烯醇
        GTQTMaterials.PolyvinylAlcoholVinylalcoholPolymer = new Material.Builder(getMaterialsId(), gtqtcoreId("polyvinyl_alcohol_vinylalcohol_polymer"))
                .fluid().dust()
                .color(0xEE9A00)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("[C2H4O]n", true);

        //吡啶
        GTQTMaterials.Pyridine = new Material.Builder(getMaterialsId(), gtqtcoreId("pyridine"))
                .fluid()
                .color(0xE0FFFF)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 5, Hydrogen, 5, Nitrogen, 1)
                .build();

        //超能硅岩化镓
        GTQTMaterials.NaquadriaGalliumIndium = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadria_gallium_indium"))
                .dust()
                .color(0x71C671)
                .iconSet(SHINY)
                .components(Naquadria, 1, Gallium, 1, Indium, 1)
                .build();

        //  11157 Lithium Niobate
        GTQTMaterials.LithiumNiobate = new Material.Builder(getMaterialsId(), gtqtcoreId("lithium_niobate"))
                .ingot()
                .color(0xD27700)
                .iconSet(SHINY)
                .components(Lithium, 1, Niobium, 1, Oxygen, 4)
                .blast(6700)
                .flags(DISABLE_DECOMPOSITION, GENERATE_LENS, CRYSTALLIZABLE, GENERATE_PLATE)
                .build();

        //  11156 Niobium Pentachloride
        GTQTMaterials.NiobiumPentachloride = new Material.Builder(getMaterialsId(), gtqtcoreId("niobium_pentachloride"))
                .dust()
                .color(Niobium.getMaterialRGB() + Chlorine.getMaterialRGB())
                .iconSet(SHINY)
                .components(Niobium, 1, Chlorine, 5)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11164 Lu-Tm-Y Chlorides Solution
        GTQTMaterials.LuTmYChloridesSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("lu_tm_y_chlorides_solution"))
                .liquid()
                .color(Lutetium.getMaterialRGB() + Thulium.getMaterialRGB() + Yttrium.getMaterialRGB())
                .components(Lutetium, 2, Thulium, 2, Yttrium, 6, Chlorine, 30, Hydrogen, 30, Oxygen, 15)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(LuCl3)2(TmCl3)2(YCl3)6(H2O)15", true);

        //  11159 Sodium Vanadate
        GTQTMaterials.SodiumVanadate = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_vanadate"))
                .dust()
                .color(0xCC9933)
                .iconSet(BRIGHT)
                .flags(DISABLE_DECOMPOSITION)
                .components(Sodium, 3, Vanadium, 1, Oxygen, 4)
                .build();

        //  13098 Carbamide
        GTQTMaterials.Carbamide = new Material.Builder(getMaterialsId(), gtqtcoreId("carbamide"))
                .dust()
                .color(0x16EF57)
                .iconSet(ROUGH)
                .components(Carbon, 1, Hydrogen, 4, Nitrogen, 2, Oxygen, 1)
                .build();

        //  11165 Lu-Tm-doped Yttrium Vanadate Deposition
        GTQTMaterials.LuTmDopedYttriumVanadateDeposition = new Material.Builder(getMaterialsId(), gtqtcoreId("lu_tm_doped_yttrium_vanadate_deposition"))
                .dust()
                .color(Yttrium.getMaterialRGB() + Vanadium.getMaterialRGB() + Lutetium.getMaterialRGB())
                .iconSet(FINE)
                .build()
                .setFormula("Lu/Tm:YVO?", false);

        //  11167 Lu/Tm:YVO
        GTQTMaterials.LuTmYVO = new Material.Builder(getMaterialsId(), gtqtcoreId("lu_tm_yvo"))
                .gem()
                .color(0x8C1B23)
                .iconSet(GEM_HORIZONTAL)
                .flags(DISABLE_DECOMPOSITION, GENERATE_LENS, CRYSTALLIZABLE)
                .components(Yttrium, 1, Vanadium, 1, Oxygen, 1, Lutetium, 1, Thulium, 1)
                .build()
                .setFormula("Lu/Tm:YVO", false);

        //  11171 Ammonium Fluoride
        GTQTMaterials.AmmoniumFluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("ammonium_fluoride"))
                .liquid()
                .color(Ammonia.getMaterialRGB() + Fluorine.getMaterialRGB())
                .components(Nitrogen, 1, Hydrogen, 4, Fluorine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11172 Ammonium Difluoride
        GTQTMaterials.AmmoniumDifluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("ammonium_difluoride"))
                .dust()
                .color(AmmoniumFluoride.getMaterialRGB())
                .iconSet(FINE)
                .components(Nitrogen, 1, Hydrogen, 5, Fluorine, 2)
                .build()
                .setFormula("NH4HF2", true);

        //  11173 Beryllium Difluoride
        GTQTMaterials.BerylliumDifluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("beryllium_difluoride"))
                .dust()
                .color(Beryllium.getMaterialRGB() + Fluorine.getMaterialRGB())
                .iconSet(SHINY)
                .components(Beryllium, 1, Fluorine, 2)
                .build();


        //  11168 Pr-Ho-Y Nitrates Solution
        GTQTMaterials.PrHoYNitratesSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("pr_ho_y_nitrates_solution"))
                .liquid(new FluidBuilder().attributes(FluidAttributes.ACID))
                .color(Praseodymium.getMaterialRGB() + Holmium.getMaterialRGB() + Yttrium.getMaterialRGB())
                .components(Praseodymium, 2, Holmium, 2, Yttrium, 6, Nitrogen, 30, Oxygen, 105, Hydrogen, 30)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(Pr(NO3)3)2(Ho(NO3)3)2(Y(NO3)3)6(H2O)15", true);

        //  11174 Pr:Ho/YLF
        GTQTMaterials.PrHoYLF = new Material.Builder(getMaterialsId(), gtqtcoreId("pr_ho_ylf"))
                .gem()
                .color(Praseodymium.getMaterialRGB() + Holmium.getMaterialRGB() + Yttrium.getMaterialRGB() + Lithium.getMaterialRGB())
                .iconSet(RUBY)
                .components(Praseodymium, 1, Holmium, 1, Lithium, 1, Yttrium, 1, Fluorine, 4)
                .flags(DISABLE_DECOMPOSITION, GENERATE_LENS, CRYSTALLIZABLE)
                .build()
                .setFormula("Pr/Ho:YLF", false);

        //  11148 Potassium Manganate
        GTQTMaterials.PotassiumManganate = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_manganate"))
                .dust()
                .color(0x873883)
                .iconSet(METALLIC)
                .components(Potassium, 2, Manganese, 1, Oxygen, 4)
                .build();

        //  11149 Potassium Manganate
        GTQTMaterials.PotassiumPermanganate = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_permanganate"))
                .dust()
                .color(0x871D82)
                .iconSet(DULL)
                .components(Potassium, 1, Manganese, 1, Oxygen, 4)
                .build();

        //  11150 Manganese Sulfate
        GTQTMaterials.ManganeseSulfate = new Material.Builder(getMaterialsId(), gtqtcoreId("manganese_sulfate"))
                .dust()
                .color(0xF0F895)
                .iconSet(ROUGH)
                .components(Manganese, 1, Sulfur, 1, Oxygen, 4)
                .build();

        //  11152 Neodymium-Doped Yttrium Oxide
        GTQTMaterials.NeodymiumDopedYttriumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("neodymium_doped_yttrium_oxide"))
                .dust()
                .color(0x5AD55F)
                .iconSet(DULL)
                .build()
                .setFormula("Nd:Y?", false);

        //  11153 Aluminium Nitrate
        GTQTMaterials.AluminiumNitrate = new Material.Builder(getMaterialsId(), gtqtcoreId("aluminium_nitrate"))
                .dust()
                .color(0x3AB3AA)
                .iconSet(SHINY)
                .components(Aluminium, 1, Nitrogen, 3, Oxygen, 9)
                .build()
                .setFormula("Al(NO3)3", true);

        //  11155 Unprocessed Nd:YAG Solution
        GTQTMaterials.UnprocessedNdYAGSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("unprocessed_nd_yag_solution"))
                .liquid()
                .color(0x6f20af)
                .iconSet(DULL)
                .build()
                .setFormula("Nd:YAG?", false);

        //  13097 Ammonium Cyanate
        GTQTMaterials.AmmoniumCyanate = new Material.Builder(getMaterialsId(), gtqtcoreId("ammonium_cyanate"))
                .liquid()
                .color(0x3a5dcf)
                .components(Hydrogen, 4, Nitrogen, 2, Carbon, 1, Oxygen, 1)
                .build()
                .setFormula("NH4CNO", true);

        //  13099 Tributylamine
        GTQTMaterials.Tributylamine = new Material.Builder(getMaterialsId(), gtqtcoreId("tributylamine"))
                .liquid()
                .color(0x801a80)
                .components(Carbon, 12, Hydrogen, 27, Nitrogen, 1)
                .build()
                .setFormula("(C4H9)3N", true);

        //  13100 Dichloromethane
        GTQTMaterials.Dichloromethane = new Material.Builder(getMaterialsId(), gtqtcoreId("dichloromethane"))
                .liquid()
                .color(0xB87FC7)
                .components(Carbon, 1, Hydrogen, 2, Chlorine, 2)
                .build();

        GTQTMaterials.Birmabright = new Material.Builder(getMaterialsId(), gtqtcoreId("birmabright"))
                .ingot()
                .fluid()
                .blast(1100)
                .color(1755371).iconSet(MaterialIconSet.DULL)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .components(Aluminium, 5, Magnesium, 1, Manganese, 1)
                .build();

        GTQTMaterials.BT6 = new Material.Builder(getMaterialsId(), gtqtcoreId("bt_6"))
                .ingot()
                .fluid()
                .colorAverage().iconSet(MaterialIconSet.SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .components(Iron, 3, Carbon, 1, Vanadium, 5, Titanium, 40, Aluminium, 6)
                .blast(3400)
                .build();

        GTQTMaterials.TriphenylPhosphine = new Material.Builder(getMaterialsId(), gtqtcoreId("triphenylphosphine"))
                .dust()
                .fluid()
                .colorAverage()
                .components(Phosphorus, 1, Carbon, 18, Hydrogen, 15)
                .build()
                .setFormula("P(C6H5)3", true);

        GTQTMaterials.Difluorobenzophenone = new Material.Builder(getMaterialsId(), gtqtcoreId("difluorobenzophenone"))
                .dust()
                .color(0xC44DA5)
                .iconSet(SHINY)
                .components(Carbon, 13, Hydrogen, 8, Oxygen, 1, Fluorine, 2)
                .build()
                .setFormula("(FC6H4)2CO", true);

        GTQTMaterials.PhenylmagnesiumBromide = new Material.Builder(getMaterialsId(), gtqtcoreId("phenylmagnesiumbromide"))
                .fluid()
                .colorAverage()
                .components(Carbon, 6, Hydrogen, 5, Magnesium, 1, Bromine, 1)
                .build()
                .setFormula("C6H5MgBr", true);

        GTQTMaterials.Bromobenzene = new Material.Builder(getMaterialsId(), gtqtcoreId("bromobenzene"))
                .fluid()
                .colorAverage()
                .components(Carbon, 6, Hydrogen, 4, Bromine, 1)
                .build()
                .setFormula("C6H5Br", true);

        GTQTMaterials.Draconium = new Material.Builder(getMaterialsId(), gtqtcoreId("draconium"))
                .ingot().fluid()
                .color(0x573d85).iconSet(MaterialIconSet.DULL)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .build();

        GTQTMaterials.SilicaCeramic = new Material.Builder(getMaterialsId(), gtqtcoreId("silicaceramic"))
                .ingot()
                .blast(1000)
                .color(0x8c7a50).iconSet(MaterialIconSet.SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .build();

        GTQTMaterials.Ladder_Poly_P_Phenylene = new Material.Builder(getMaterialsId(), gtqtcoreId("polypphenylene"))
                .fluid()
                .ingot()
                .color(0xbfb393)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .build()
                .setFormula("(C₆H₄)_n");

        GTQTMaterials.HydrogenSilsesquioxane = new Material.Builder(getMaterialsId(), gtqtcoreId("hydrogensilsesquioxane"))
                .fluid()
                .color(0x471525)
                .build()
                .setFormula("[HSiO3/2]", true);

        GTQTMaterials.SU8_Photoresist = new Material.Builder(getMaterialsId(), gtqtcoreId("su_photoresist"))
                .fluid()
                .color(0x0e242b)
                .build()
                .setFormula("(C18H19O3)n");

        GTQTMaterials.Fiberglass = new Material.Builder(getMaterialsId(), gtqtcoreId("fiberglass"))
                .ingot().fluid()
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .color(0x99c0cf)
                .build();

        GTQTMaterials.LuminescentSiliconNanocrystals = new Material.Builder(getMaterialsId(), gtqtcoreId("luminescentsiliconnanocrystals"))
                .dust()
                .iconSet(MaterialIconSet.SHINY)
                .color(0x363636)
                .build();

        GTQTMaterials.SeleniumMonobromide = new Material.Builder(getMaterialsId(), gtqtcoreId("seleniummonobromide"))
                .fluid()
                .color(0x472a1a)
                .build();

        //  25038 Hydroquinone
        GTQTMaterials.Hydroquinone = new Material.Builder(getMaterialsId(), gtqtcoreId("hydroquinone"))
                .fluid()
                .color(0x83251A)
                .components(Carbon, 6, Hydrogen, 6, Oxygen, 2)
                .build()
                .setFormula("C6H4(OH)2", true);

        GTQTMaterials.Fluorobenzene = new Material.Builder(getMaterialsId(), gtqtcoreId("fluorobenzene"))
                .fluid()
                .color(0x7CCA88)
                .components(Carbon, 6, Hydrogen, 5, Fluorine, 1)
                .build();

        GTQTMaterials.Starlight = new Material.Builder(getMaterialsId(), gtqtcoreId("starlight"))
                .fluid()
                .color(0xebfafc)
                .iconSet(MaterialIconSet.SHINY)
                .build();

        GTQTMaterials.TetrakisPDCatalyst = new Material.Builder(getMaterialsId(), gtqtcoreId("tretrakispdcatalyst"))
                .dust()
                .color(0x9bd1e8)
                .iconSet(MaterialIconSet.SHINY)
                .build();

        GTQTMaterials.HydraziniumChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("hydraziniumchloride"))
                .fluid()
                .colorAverage()
                .components(Nitrogen, 2, Hydrogen, 5, Chlorine, 1)
                .build()
                .setFormula("N2H4HCl", true);

        GTQTMaterials.DibromoisophthalicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("dibromoisophthalicacid"))
                .fluid()
                .colorAverage()
                .components(Carbon, 8, Hydrogen, 5, Bromine, 1, Oxygen, 4)
                .build()
                .setFormula("C8H5BrO4", true);

        GTQTMaterials.Dibromoterephthaloyldichloride = new Material.Builder(getMaterialsId(), gtqtcoreId("dibromoterephthaloyldichloride"))
                .fluid()
                .colorAverage()
                .components(Carbon, 8, Hydrogen, 2, Bromine, 2, Chlorine, 2, Oxygen, 2)
                .build()
                .setFormula("C8H2Br2Cl2O2", true);

        GTQTMaterials.P1Solution = new Material.Builder(getMaterialsId(), gtqtcoreId("p_one_solution"))
                .fluid()
                .color(0x6b0c05)
                .build();

        GTQTMaterials.HafniumSilicate = new Material.Builder(getMaterialsId(), gtqtcoreId("hafnium_silicate"))
                .dust()
                .colorAverage()
                .components(Hafnium, 1, Oxygen, 4, Silicon, 1)
                .build()
                .setFormula("HfO4Si", true);

        GTQTMaterials.Cobalt60 = new Material.Builder(getMaterialsId(), gtqtcoreId("cobalt_sixty"))
                .dust()
                .iconSet(MaterialIconSet.SHINY)
                .color(Cobalt.getMaterialRGB())
                .element(GTQTMaterials.Co60)
                .build()
                .setFormula("Co-60", false);

        GTQTMaterials.Fluorotoluene = new Material.Builder(getMaterialsId(), gtqtcoreId("fluorotoluene"))
                .fluid()
                .color(0x6EC5B8)
                .components(Carbon, 7, Hydrogen, 7, Fluorine, 1)
                .build();

        GTQTMaterials.Resorcinol = new Material.Builder(getMaterialsId(), gtqtcoreId("resorcinol"))
                .fluid()
                .color(0x9DA38D)
                .components(Carbon, 6, Hydrogen, 6, Oxygen, 2)
                .build();

        GTQTMaterials.ZnFeAlClCatalyst = new Material.Builder(getMaterialsId(), gtqtcoreId("zn_fe_al_cl_catalyst"))
                .dust()
                .color(0xC522A9)
                .iconSet(DULL)
                .components(Zinc, 1, Iron, 1, Aluminium, 1, Chlorine, 1)
                .build();

        GTQTMaterials.NitrileButadieneRubber = new Material.Builder(getMaterialsId(), gtqtcoreId("nitrile_butadiene_rubber"))
                .fluid()
                .color(0x211A18)
                .flags(DISABLE_DECOMPOSITION, GENERATE_PLATE, GENERATE_RING)
                .components(Carbon, 7, Hydrogen, 9, Nitrogen, 1)
                .build();

        GTQTMaterials.PolyPhosphonitrileFluoroRubber = new Material.Builder(getMaterialsId(), gtqtcoreId("poly_phosphonitrile_fluoro_rubber"))
                .fluid()
                .color(0x372B28)
                .flags(DISABLE_DECOMPOSITION, GENERATE_PLATE, GENERATE_RING)
                .components(Carbon, 24, Hydrogen, 16, Oxygen, 8, Nitrogen, 4, Phosphorus, 4, Fluorine, 40)
                .build();

        GTQTMaterials.DichIorosilane = new Material.Builder(getMaterialsId(), gtqtcoreId("dich_iorosilane"))
                .fluid()
                .colorAverage()
                .flags(DISABLE_DECOMPOSITION)
                .components(Silicon, 1, Hydrogen, 2, Chlorine, 2)
                .build()
                .setFormula("SiH2Cl2", true);

        GTQTMaterials.Silane = new Material.Builder(getMaterialsId(), gtqtcoreId("silane"))
                .fluid()
                .colorAverage()
                .flags(DISABLE_DECOMPOSITION)
                .components(Silicon, 1, Hydrogen, 4)
                .build()
                .setFormula("SiH4", true);

        GTQTMaterials.ArgonSilane = new Material.Builder(getMaterialsId(), gtqtcoreId("argon_silane"))
                .gas().plasma()
                .components(Argon, 1, GTQTMaterials.Silane, 1)
                .color(0x24BB18)
                .build();

        GTQTMaterials.CarbonNanotubePolymer = new Material.Builder(getMaterialsId(), gtqtcoreId("carbon_nanotube_polymer"))
                .dust()
                .ingot()
                .color(0x0d0d0d)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .iconSet(MaterialIconSet.SHINY)
                .build();


        GTQTMaterials.NihoniumTriiodide = new Material.Builder(getMaterialsId(), gtqtcoreId("nihonium_triiodide"))
                .dust()
                .ingot()
                .color(0x5986a8)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .iconSet(MaterialIconSet.SHINY)
                .build()
                .setFormula("Nh-I3", false);

        GTQTMaterials.BismuthTelluride = new Material.Builder(getMaterialsId(), gtqtcoreId("bismuth_telluride"))
                .ingot()
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .colorAverage()
                .components(Bismuth, 2, Tellurium, 3)
                .build();

        GTQTMaterials.SynthDiamond = new Material.Builder(getMaterialsId(), gtqtcoreId("synthetic_diamond"))
                .dust()
                .gem()
                .color(0x8fbaff)
                .components(Carbon, 8)
                .build();

        GTQTMaterials.GraphenePQD = new Material.Builder(getMaterialsId(), gtqtcoreId("photoluminescent_graphene_quantum_dots"))
                .dust()
                .color(0x616161)
                .iconSet(MaterialIconSet.SHINY)
                .components(Carbon, 6)
                .build();

        GTQTMaterials.BismuthIridiumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("bismuth_iridium_oxide"))
                .ingot()
                .colorAverage()
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .components(Bismuth, 2, Iridium, 2, Oxygen, 7)
                .iconSet(MaterialIconSet.DULL)
                .build();

        GTQTMaterials.IndiumFluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("indium_fluoride"))
                .fluid()
                .color(0x2d5c53)
                .components(Indium, 1, Fluorine, 3)
                .build();

        GTQTMaterials.EnrichedNaqAlloy = new Material.Builder(getMaterialsId(), gtqtcoreId("enriched_naquadah_alloy"))
                .ingot()
                .fluid()
                .colorAverage()
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .components(NaquadahEnriched, 16, Einsteinium, 4, Rhodium, 4, Technetium, 4, Astatine, 2, Erbium, 2)
                .blast(9700)
                .iconSet(MaterialIconSet.SHINY)
                .build();

        GTQTMaterials.SodiumPotassiumNiobate = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_potassium_niobate"))
                .ingot()
                .colorAverage()
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .components(Sodium, 1, Potassium, 1, Niobium, 2, Oxygen, 6)
                .blast(3600)
                .build();

        GTQTMaterials.TriniumSteel = new Material.Builder(getMaterialsId(), gtqtcoreId("trinium_steel"))
                .ingot()
                .iconSet(MaterialIconSet.SHINY)
                .blast(10200)
                .colorAverage()
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .components(Trinium, 18, Tungsten, 6, Vanadium, 4, Chrome, 2, Tantalum, 1, Cobalt, 1)
                .build();

        GTQTMaterials.TerfenolD_H = new Material.Builder(getMaterialsId(), gtqtcoreId("terfenol_d_heavy"))
                .ingot()
                .blast(10200)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .components(Osmium, 12, Iron, 6, Dysprosium, 2, Terbium, 1)
                .color(0x4d4d4d)
                .build();

        GTQTMaterials.TerfenolD_L = new Material.Builder(getMaterialsId(), gtqtcoreId("terfenol_d_light"))
                .ingot()
                .blast(10200)
                .color(0x9c9c9c)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .build();

        GTQTMaterials.SuspendedPGQD = new Material.Builder(getMaterialsId(), gtqtcoreId("suspended_pgqd"))
                .fluid()
                .color(0x65ad95)
                .components(Krypton, 1, GTQTMaterials.GraphenePQD, 1)
                .build();

        GTQTMaterials.Leptons = new Material.Builder(getMaterialsId(), gtqtcoreId("leptons"))
                .fluid()
                .color(0x5500ff)
                .build();

        GTQTMaterials.NeonFluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("neon_fluoride"))
                .fluid()
                .colorAverage()
                .components(Neon, 1, Fluorine, 1)
                .build();

        GTQTMaterials.ExcitedNeonFluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("excited_neon_fluoride"))
                .fluid()
                .colorAverage()
                .color(GTQTMaterials.NeonFluoride.getMaterialRGB())
                .components(GTQTMaterials.NeonFluoride, 1)
                .build();

        GTQTMaterials.ArgonFluorine = new Material.Builder(getMaterialsId(), gtqtcoreId("argon_fluorine"))
                .fluid()
                .color(0x00ff88)
                .components(Argon, 1, Fluorine, 1)
                .build();

        GTQTMaterials.SilverGalliumSelenide = new Material.Builder(getMaterialsId(), gtqtcoreId("silver_gallium_selenide"))
                .dust()
                .colorAverage()
                .components(Silver, 1, Gallium, 1, Selenium, 2)
                .build();

        GTQTMaterials.BismuthPhosphomolybdate = new Material.Builder(getMaterialsId(), gtqtcoreId("bismuth_phosphomolybdate"))
                .dust()
                .colorAverage()
                .components(Bismuth, 9, Phosphorus, 1, Molybdenum, 12, Oxygen, 52)
                .build();

        GTQTMaterials.Acrylonitrile = new Material.Builder(getMaterialsId(), gtqtcoreId("acrylonitrile"))
                .fluid()
                .color(0x565734)
                .components(Carbon, 3, Hydrogen, 3, Nitrogen, 1)
                .build()
                .setFormula("CH2CHCN", true);

        GTQTMaterials.Polyacrylonitrile = new Material.Builder(getMaterialsId(), gtqtcoreId("polyacrylonitrile"))
                .dust()
                .color(0x854218)
                .build();

        GTQTMaterials.CFCoatingSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("cf_coating_solution"))
                .fluid()
                .colorAverage()
                .components(PolyvinylChloride, 1, Polyethylene, 1)
                .build();

        GTQTMaterials.Polyetheretherketone = new Material.Builder(getMaterialsId(), gtqtcoreId("peek"))
                .ingot().fluid()
                .iconSet(MaterialIconSet.DULL)
                .color(0x2b2b2b)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .build()
                .setFormula("C20H12O3", true);

        GTQTMaterials.ProgrammableMatter = new Material.Builder(getMaterialsId(), gtqtcoreId("programmable_matter"))
                .ingot()
                .fluid()
                .color(0x8196a3)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .iconSet(MaterialIconSet.SHINY)
                .build()
                .setFormula("robots!", false);

        GTQTMaterials.Methyltrichlorosilane = new Material.Builder(getMaterialsId(), gtqtcoreId("methyltrichlorosilane"))
                .fluid()
                .colorAverage()
                .components(Carbon, 1, Hydrogen, 3, Chlorine, 3, Silicon, 1)
                .build()
                .setFormula("CH3Cl3Si", true);

        GTQTMaterials.Methyltrimethoxysilane = new Material.Builder(getMaterialsId(), gtqtcoreId("methyltrimethyoxysilane"))
                .fluid()
                .color(0x42163c)
                .components(Silicon, 1, Oxygen, 3, Carbon, 4, Hydrogen, 12)
                .build()
                .setFormula("CH3Si(OCH3)3", true);

        GTQTMaterials.Polymethylsilesquioxane = new Material.Builder(getMaterialsId(), gtqtcoreId("polymethylsilesquioxane"))
                .fluid()
                .color(0xff7ab4)
                .components(Silicon, 1, Oxygen, 3, Carbon, 4, Hydrogen, 12)
                .build()
                .setFormula("[CH3Si(OCH3)3]N", true);

        GTQTMaterials.IridiumOnCubicZirconia = new Material.Builder(getMaterialsId(), gtqtcoreId("iridiumoncubiczirconia"))
                .dust()
                .colorAverage()
                .build();

        GTQTMaterials.OnePropanol = new Material.Builder(getMaterialsId(), gtqtcoreId("onepropanol"))
                .fluid()
                .color(0xbad17b)
                .components(Carbon, 3, Hydrogen, 8, Oxygen, 1)
                .build()
                .setFormula("CH3CH2CH2OH", true);

        GTQTMaterials.ZSM_5_ZEOLITE = new Material.Builder(getMaterialsId(), gtqtcoreId("zsm_five_zeolite"))
                .dust()
                .color(0xb8ffe1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(Na13Al13Si83O192)16H2O", true);

        GTQTMaterials.SodiumHydroxideSilica = new Material.Builder(getMaterialsId(), gtqtcoreId("sodiumhydroxidesilica"))
                .fluid()
                .color(0x213996)
                .iconSet(MaterialIconSet.FLUID)
                .components(SodiumHydroxide, 1, SiliconDioxide, 1, Water, 1)
                .build();

        GTQTMaterials.SodiumAluminate = new Material.Builder(getMaterialsId(), gtqtcoreId("sodiumaluminate"))
                .dust()
                .colorAverage()
                .components(Sodium, 1, Aluminium, 1, Oxygen, 2)
                .build();

        GTQTMaterials.SodiumAluminumSilicaSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("aluminumsilicasolution"))
                .fluid()
                .color(0xb0c1ff)
                .components(Sodium, 1, Aluminium, 1, Silicon, 1, Oxygen, 2, Water, 1)
                .build();

        GTQTMaterials.AluminoSilicateGlass = new Material.Builder(getMaterialsId(), gtqtcoreId("aluminosilicateglass"))
                .ingot()
                .flags(GENERATE_PLATE)
                .color(0x9a9fb3)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .iconSet(MaterialIconSet.SHINY)
                .components(Aluminium, 1, Silicon, 1, Oxygen, 4)
                .build();

        GTQTMaterials.TetramethylammoniumBromide = new Material.Builder(getMaterialsId(), gtqtcoreId("tetramethylammoniumbromide"))
                .dust()
                .colorAverage()
                .components(Carbon, 4, Hydrogen, 12, Nitrogen, 1, Bromine, 1)
                .build()
                .setFormula("(CH3)4NBr", true);

        GTQTMaterials.DiamondSonicationSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("diamondsonicationsolution"))
                .fluid()
                .colorAverage()
                .flags(DISABLE_DECOMPOSITION)
                .components(Graphite, 1, Phenol, 1)
                .build();

        GTQTMaterials.SuperfluidHelium3 = new Material.Builder(getMaterialsId(), gtqtcoreId("superfluidhelium"))
                .fluid()
                .colorAverage()
                .flags(DISABLE_DECOMPOSITION)
                .components(Helium3, 1)
                .build();

        GTQTMaterials.CobaltChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("cobaltchloride"))
                .fluid()
                .color(0x48559F)
                .flags(DISABLE_DECOMPOSITION)
                .components(Cobalt, 1, Chlorine, 2)
                .build();

        GTQTMaterials.CobaltIodide = new Material.Builder(getMaterialsId(), gtqtcoreId("cobaltiodide"))
                .fluid()
                .colorAverage()
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.Cobalt59 = new Material.Builder(getMaterialsId(), gtqtcoreId("cobalt_59"))
                .dust()
                .color(0x3D3DFA)
                .build()
                .setFormula("Co-59", false);

        GTQTMaterials.Cobalt59Iodide = new Material.Builder(getMaterialsId(), gtqtcoreId("cobalt_59_iodide"))
                .fluid()
                .color(0x0B0058)
                .build();

        GTQTMaterials.Cobalt60Iodide = new Material.Builder(getMaterialsId(), gtqtcoreId("cobalt_60_iodide"))
                .fluid()
                .colorAverage()
                .build();

        GTQTMaterials.HydroiodicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("hydroiodicacid"))
                .fluid()
                .colorAverage()
                .build();

        GTQTMaterials.ImpureHydroiodicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("impurehydroiodicacid"))
                .fluid()
                .colorAverage()
                .flags(DISABLE_DECOMPOSITION)
                .components(GTQTMaterials.HydroiodicAcid, 1, Water, 1)
                .build()
                .setFormula("N2(HI)2H2O", true);

        GTQTMaterials.Butynediol = new Material.Builder(getMaterialsId(), gtqtcoreId("butynediol"))
                .fluid()
                .colorAverage()
                .components(Carbon, 4, Hydrogen, 6, Oxygen, 2)
                .build();

        GTQTMaterials.KAOil = new Material.Builder(getMaterialsId(), gtqtcoreId("ka_oil"))
                .fluid()
                .color(0xFA7B53)
                .components(Carbon, 12, Hydrogen, 22, Oxygen, 2)
                .build()
                .setFormula("2(C6H11OH)2((CH2)5CO)", true);

        GTQTMaterials.AdipicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("adipic_acid"))
                .dust()
                .colorAverage()
                .components(Carbon, 6, Hydrogen, 10, Oxygen, 4)
                .build()
                .setFormula("C6H8O2(OH)2", true);

        GTQTMaterials.NitrousAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("nitrous_acid"))
                .fluid()
                .colorAverage()
                .components(Hydrogen, 1, Nitrogen, 1, Oxygen, 2)
                .build();

        GTQTMaterials.BariumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("barium_oxide"))
                .dust()
                .colorAverage()
                .components(Barium, 1, Oxygen, 1)
                .build();

        GTQTMaterials.BariumHydroxide = new Material.Builder(getMaterialsId(), gtqtcoreId("barium_hydroxide"))
                .dust()
                .colorAverage()
                .components(Barium, 1, Hydrogen, 1, Oxygen, 1)
                .build();

        GTQTMaterials.Cyclopentanone = new Material.Builder(getMaterialsId(), gtqtcoreId("cyclopentanone"))
                .fluid()
                .colorAverage()
                .components(Carbon, 5, Hydrogen, 8, Oxygen, 1)
                .build()
                .setFormula("(CH2)4CO", true);

        GTQTMaterials.BenzenesulfonicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("benzenesulfonic_acid"))
                .dust()
                .colorAverage()
                .components(Carbon, 6, Hydrogen, 6, Oxygen, 3, Sulfur, 1)
                .build();

        GTQTMaterials.TriphenylsulfoniumHexafluoroantimonate = new Material.Builder(getMaterialsId(), gtqtcoreId("triphenylsulfonium_hexafluoroantimonate"))
                .fluid()
                .color(0x8968FA)
                .components(Carbon, 18, Hydrogen, 15, Sulfur, 1, Antimony, 1, Fluorine, 6)
                .build();

        GTQTMaterials.HypofluorousAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("hypofluorous_acid"))
                .fluid()
                .colorAverage()
                .components(Hydrogen, 1, Fluorine, 1, Oxygen, 1)
                .build();

        GTQTMaterials.HexafluoroantimonateSalt1 = new Material.Builder(getMaterialsId(), gtqtcoreId("hexafluoroantimonate_salt_1"))
                .fluid()
                .color(0x26095F)
                .components(Carbon, 36, Hydrogen, 28, Sulfur, 3, Antimony, 2, Fluorine, 12)
                .build()
                .setFormula("C36H28S3(SbF6)2", true);

        GTQTMaterials.HexafluoroantimonateSalt2 = new Material.Builder(getMaterialsId(), gtqtcoreId("hexafluoroantimonate_salt_2"))
                .fluid()
                .color(0x5A2F9F)
                .components(Carbon, 24, Hydrogen, 19, Sulfur, 2, Antimony, 1, Fluorine, 6)
                .build();

        GTQTMaterials.MixedHexafluoroantimonateSalts = new Material.Builder(getMaterialsId(), gtqtcoreId("mixed_hexafluoroantimonate_salts"))
                .fluid()
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .colorAverage()
                .components(GTQTMaterials.HexafluoroantimonateSalt1, 1, GTQTMaterials.HexafluoroantimonateSalt2, 1)
                .build();

        GTQTMaterials.DiluteFluoroantimonicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("dilute_fluoroantimonic_acid"))
                .fluid()
                .flags(DISABLE_DECOMPOSITION)
                .colorAverage()
                .components(Hydrogen, 1, Antimony, 1, Fluorine, 6)
                .build();

        GTQTMaterials.IndiumSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("indium_solution"))
                .fluid()
                .color(0x58474C)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(H2SO4)?", true);

        GTQTMaterials.IndiumSulfide = new Material.Builder(getMaterialsId(), gtqtcoreId("indium_sulfide"))
                .dust()
                .iconSet(MaterialIconSet.SHINY)
                .colorAverage()
                .components(Indium, 2, Sulfur, 3)
                .build();

        GTQTMaterials.IndiumResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("indium_refining_residue"))
                .fluid()
                .iconSet(MaterialIconSet.LIGNITE)
                .color(0x060921)
                .build();

        GTQTMaterials.ZirconiumTetrafluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("zirconium_fluoride"))
                .dust()
                .colorAverage()
                .components(Zirconium, 1, Fluorine, 4)
                .build();

        GTQTMaterials.BariumDifluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("barium_fluoride"))
                .dust()
                .colorAverage()
                .components(Barium, 1, Fluorine, 2)
                .build();

        GTQTMaterials.LanthanumTrifluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("lanthanum_fluoride"))
                .dust()
                .colorAverage()
                .components(Lanthanum, 1, Fluorine, 3)
                .build();

        GTQTMaterials.AluminiumTrifluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("aluminium_fluoride"))
                .dust()
                .colorAverage()
                .components(Aluminium, 1, Fluorine, 3)
                .build();

        GTQTMaterials.SodiumFluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_fluoride"))
                .dust()
                .colorAverage()
                .components(Sodium, 1, Fluorine, 1)
                .build();

        GTQTMaterials.IlmeniteSlurry = new Material.Builder(getMaterialsId(), gtqtcoreId("ilmenite_slurry"))
                .fluid()
                .color(0x0A0212)
                .build();

        GTQTMaterials.HeavyRedMudResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("red_mud_residue"))
                .fluid()
                .color(0x091012)
                .build();

        GTQTMaterials.RefractoryMetalResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("refractory_metal_residue"))
                .fluid()
                .color(0x164347)
                .build();

        GTQTMaterials.PotassiumHexafluorohafnate = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_hexafluorohafnate"))
                .gem()
                .colorAverage()
                .components(Potassium, 2, Hafnium, 1, Fluorine, 6)
                .build();

        GTQTMaterials.PotassiumHexafluorozirconate = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_hexafluorozironate"))
                .gem()
                .colorAverage()
                .components(Potassium, 2, Zirconium, 1, Fluorine, 6)
                .build();

        GTQTMaterials.PotassiumFluorideRefractoryMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_fluoride_refractory_mixture"))
                .fluid()
                .color(0x667E71)
                .build();

        GTQTMaterials.Trichlorosilane = new Material.Builder(getMaterialsId(), gtqtcoreId("trichlorosilane"))
                .fluid()
                .colorAverage()
                .components(Hydrogen, 1, Silicon, 1, Chlorine, 3)
                .build();

        GTQTMaterials.PotassiumHydride = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_hydride"))
                .dust()
                .components(Potassium, 1, Hydrogen, 1)
                .colorAverage()
                .build();

        GTQTMaterials.Aminopropionitrile = new Material.Builder(getMaterialsId(), gtqtcoreId("aminopropionitrile"))
                .fluid()
                .components(Carbon, 3, Hydrogen, 6, Nitrogen, 2)
                .colorAverage()
                .build();

        GTQTMaterials.Aminopropylamine = new Material.Builder(getMaterialsId(), gtqtcoreId("aminopropylamine"))
                .fluid()
                .components(Carbon, 3, Hydrogen, 10, Nitrogen, 2)
                .colorAverage()
                .build();

        GTQTMaterials.KAPA = new Material.Builder(getMaterialsId(), gtqtcoreId("kapa"))
                .fluid()
                .components(Potassium, 2, Carbon, 3, Hydrogen, 10, Nitrogen, 2)
                .colorAverage()
                .build();

        GTQTMaterials.BetaPinene = new Material.Builder(getMaterialsId(), gtqtcoreId("betapinene"))
                .dust()
                .components(Carbon, 10, Hydrogen, 16)
                .colorAverage()
                .build();

        GTQTMaterials.AlphaPinene = new Material.Builder(getMaterialsId(), gtqtcoreId("alphapinene"))
                .dust()
                .components(Carbon, 10, Hydrogen, 16)
                .colorAverage()
                .build();

        GTQTMaterials.Camphene = new Material.Builder(getMaterialsId(), gtqtcoreId("camphene"))
                .dust()
                .components(Carbon, 10, Hydrogen, 16)
                .colorAverage()
                .build();

        GTQTMaterials.IsobornylAcetate = new Material.Builder(getMaterialsId(), gtqtcoreId("isobornyl_acetate"))
                .dust()
                .components(Carbon, 12, Hydrogen, 20, Oxygen, 2)
                .colorAverage()
                .build();

        GTQTMaterials.Isoborneol = new Material.Builder(getMaterialsId(), gtqtcoreId("isoborneol"))
                .dust()
                .components(Carbon, 10, Hydrogen, 18, Oxygen, 1)
                .colorAverage()
                .build();

        GTQTMaterials.SodiumAcetate = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_acetate"))
                .fluid()
                .components(Carbon, 2, Hydrogen, 3, Sodium, 1, Oxygen, 2)
                .colorAverage()
                .build()
                .setFormula("CH3COONa", true);

        GTQTMaterials.DehydrogenationCatalyst = new Material.Builder(getMaterialsId(), gtqtcoreId("dehydrogenation_catalyst"))
                .dust()
                .components()
                .color(0x2C1711)
                .build();

        GTQTMaterials.SodiumPerchlorate = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_perchlorate"))
                .dust()
                .iconSet(MaterialIconSet.BRIGHT)
                .components(Sodium, 1, Chlorine, 1, Oxygen, 4)
                .colorAverage()
                .build();

        GTQTMaterials.Phenylhydrazine = new Material.Builder(getMaterialsId(), gtqtcoreId("phenylhydrazine"))
                .fluid()
                .components(Carbon, 6, Hydrogen, 8, Nitrogen, 2)
                .colorAverage()
                .build();

        GTQTMaterials.BenzoylChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("benzoyl_chloride"))
                .fluid()
                .components(Carbon, 7, Hydrogen, 5, Chlorine, 1, Oxygen, 1)
                .colorAverage()
                .build();

        GTQTMaterials.TriphenylMethoxytriazoylPerchlorate = new Material.Builder(getMaterialsId(), gtqtcoreId("triphenyl_methoxytriazoyl_perchlorate"))
                .dust()
                .components(Carbon, 20, Hydrogen, 20, Oxygen, 6, Chlorine, 1, Nitrogen, 3)
                .flags()
                .colorAverage()
                .build();

        GTQTMaterials.SodiumMethoxide = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_methoxide"))
                .dust()
                .components(Carbon, 1, Hydrogen, 3, Sodium, 1, Oxygen, 1)
                .colorAverage()
                .build();

        GTQTMaterials.TriphenylMethoxytriazole = new Material.Builder(getMaterialsId(), gtqtcoreId("triphenyl_methoxytriazole"))
                .dust()
                .iconSet(MaterialIconSet.SHINY)
                .components(Carbon, 21, Hydrogen, 23, Oxygen, 3, Nitrogen, 3)
                .colorAverage()
                .build();

        GTQTMaterials.MetaNitrochlorobenzine = new Material.Builder(getMaterialsId(), gtqtcoreId("meta_nitrochlorobenzine"))
                .fluid()
                .components(Carbon, 6, Hydrogen, 4, Chlorine, 1, Nitrogen, 1, Oxygen, 2)
                .colorAverage()
                .build();

        GTQTMaterials.Nitroanisole = new Material.Builder(getMaterialsId(), gtqtcoreId("nitroanisole"))
                .fluid()
                .components(Carbon, 7, Hydrogen, 7, Nitrogen, 1, Oxygen, 3)
                .colorAverage()
                .build();

        GTQTMaterials.MethylBromoacetate = new Material.Builder(getMaterialsId(), gtqtcoreId("methyl_bromoacetate"))
                .dust()
                .colorAverage()
                .components(Carbon, 3, Hydrogen, 5, Bromine, 1, Oxygen, 2)
                .build();

        GTQTMaterials.CarbethoxymethyleneTriphenylphosphorane = new Material.Builder(getMaterialsId(), gtqtcoreId("carbomethoxy_methylenetriphenylphosphorane"))
                .dust()
                .colorAverage()
                .components(Carbon, 22, Hydrogen, 21, Oxygen, 2, Phosphorus, 1)
                .build();

        GTQTMaterials.PropargylAlcohol = new Material.Builder(getMaterialsId(), gtqtcoreId("propargyl_alcohol"))
                .fluid()
                .colorAverage()
                .components(Carbon, 3, Hydrogen, 4, Oxygen, 1)
                .build();

        GTQTMaterials.PropargylBromide = new Material.Builder(getMaterialsId(), gtqtcoreId("propargyl_bromide"))
                .fluid()
                .colorAverage()
                .components(Carbon, 3, Hydrogen, 3, Bromine, 1)
                .build();

        GTQTMaterials.MethylmagnesiumIodide = new Material.Builder(getMaterialsId(), gtqtcoreId("methylmagnesium_iodide"))
                .dust()
                .colorAverage()
                .components(Carbon, 1, Hydrogen, 3, Magnesium, 1)
                .build()
                .setFormula("CH3COCl", true);

        GTQTMaterials.AcetylChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("acetyl_chloride"))
                .fluid()
                .colorAverage()
                .components(Carbon, 2, Hydrogen, 3, Oxygen, 1, Chlorine, 1)
                .build();

        GTQTMaterials.Acetophenone = new Material.Builder(getMaterialsId(), gtqtcoreId("acetophenone"))
                .fluid()
                .colorAverage()
                .components(Carbon, 8, Hydrogen, 8, Oxygen, 1)
                .build()
                .setFormula("C6H5COCH3", true);

        GTQTMaterials.Phenylethylamine = new Material.Builder(getMaterialsId(), gtqtcoreId("phenylethylamine"))
                .fluid()
                .colorAverage()
                .components(Carbon, 8, Hydrogen, 11, Nitrogen, 1)
                .build();

        GTQTMaterials.PhenylethylIsocyanate = new Material.Builder(getMaterialsId(), gtqtcoreId("phenylethyl_isocyanate"))
                .fluid()
                .colorAverage()
                .components(Carbon, 9, Hydrogen, 9, Nitrogen, 1, Oxygen, 1)
                .build();

        GTQTMaterials.TriethyloxoniumTetrafluoroborate = new Material.Builder(getMaterialsId(), gtqtcoreId("triethyloxonium_tetrafluoroborate"))
                .dust()
                .colorAverage()
                .components(Carbon, 6, Hydrogen, 15, Oxygen, 1, Boron, 1, Fluorine, 4)
                .build()
                .setFormula("(C2H5)3O(BF4)", true);

        GTQTMaterials.TrischloroethoxypopanylBorate = new Material.Builder(getMaterialsId(), gtqtcoreId("trischloroethoxypopanyl_borate"))
                .dust()
                .colorAverage()
                .components()
                .build();

        GTQTMaterials.SulfuricFlueGas = new Material.Builder(getMaterialsId(), gtqtcoreId("sulfuric_flue_gas"))
                .fluid()
                .color(0x6B6623)
                .build();

        GTQTMaterials.SulfuricIronSlag = new Material.Builder(getMaterialsId(), gtqtcoreId("sulfuric_iron_slag"))
                .ingot()
                .iconSet(MaterialIconSet.ROUGH)
                .color(0x616B33)
                .build();

        GTQTMaterials.SulfuricCopperSlag = new Material.Builder(getMaterialsId(), gtqtcoreId("sulfuric_copper_slag"))
                .ingot()
                .iconSet(MaterialIconSet.ROUGH)
                .color(0x975128)
                .build();

        GTQTMaterials.SulfuricNickelSlag = new Material.Builder(getMaterialsId(), gtqtcoreId("sulfuric_nickel_slag"))
                .ingot()
                .iconSet(MaterialIconSet.ROUGH)
                .color(0x769197)
                .build();

        GTQTMaterials.SulfuricZincSlag = new Material.Builder(getMaterialsId(), gtqtcoreId("sulfuric_zinc_slag"))
                .ingot()
                .iconSet(MaterialIconSet.ROUGH)
                .color(0xB5AECA)
                .build();

        GTQTMaterials.LanthanumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("lanthanum_oxide"))
                .dust()
                .color(0x5F7777)
                .iconSet(MaterialIconSet.SHINY)
                .components(Lanthanum, 2, Oxygen, 3)
                .build();

        GTQTMaterials.PraseodymiumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("praseodymium_oxide"))
                .dust()
                .color(0xD0D0D0)
                .iconSet(MaterialIconSet.METALLIC)
                .components(Praseodymium, 2, Oxygen, 3)
                .build();

        GTQTMaterials.NeodymiumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("neodymium_oxide"))
                .dust()
                .color(0x868686)
                .components(Neodymium, 2, Oxygen, 3)
                .build();

        GTQTMaterials.ScandiumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("scandium_oxide"))
                .dust()
                .color(0x43964F)
                .iconSet(MaterialIconSet.METALLIC)
                .components(Scandium, 2, Oxygen, 3)
                .build();

        GTQTMaterials.EuropiumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("europium_oxide"))
                .dust()
                .color(0x20AAAA)
                .iconSet(MaterialIconSet.SHINY)
                .components(Europium, 2, Oxygen, 3)
                .build();

        GTQTMaterials.GadoliniumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("gadolinium_oxide"))
                .dust()
                .color(0xEEEEFF)
                .iconSet(MaterialIconSet.METALLIC)
                .components(Gadolinium, 2, Oxygen, 3)
                .build();

        GTQTMaterials.SamariumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("samarium_oxide"))
                .dust()
                .color(0xFFFFDD)
                .components(Samarium, 2, Oxygen, 3)
                .build();

        GTQTMaterials.YttriumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("yttrium_oxide"))
                .dust()
                .color(0x78544E)
                .iconSet(MaterialIconSet.SHINY)
                .components(Yttrium, 2, Oxygen, 3)
                .build();

        GTQTMaterials.TerbiumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("terbium_oxide"))
                .dust()
                .color(0xA264A2)
                .iconSet(MaterialIconSet.METALLIC)
                .components(Terbium, 2, Oxygen, 3)
                .build();

        GTQTMaterials.DysprosiumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("dysprosium_oxide"))
                .dust()
                .color(0xD273D2)
                .iconSet(MaterialIconSet.METALLIC)
                .components(Dysprosium, 2, Oxygen, 3)
                .build();

        GTQTMaterials.HolmiumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("holmium_oxide"))
                .dust()
                .color(0xAF7F2A)
                .iconSet(MaterialIconSet.SHINY)
                .components(Holmium, 2, Oxygen, 3)
                .build();

        GTQTMaterials.ErbiumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("erbium_oxide"))
                .dust()
                .color(0xE07A32)
                .iconSet(MaterialIconSet.METALLIC)
                .components(Erbium, 2, Oxygen, 3)
                .build();

        GTQTMaterials.ThuliumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("thulium_oxide"))
                .dust()
                .color(0x3B9E8B)
                .components(Thulium, 2, Oxygen, 3)
                .build();

        GTQTMaterials.YtterbiumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("ytterbium_oxide"))
                .dust()
                .color(0xA9A9A9)
                .components(Ytterbium, 2, Oxygen, 3)
                .build();

        GTQTMaterials.LutetiumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("lutetium_oxide"))
                .dust()
                .color(0x11BBFF)
                .iconSet(MaterialIconSet.METALLIC)
                .components(Lutetium, 2, Oxygen, 3)
                .build();

        GTQTMaterials.PurifiedPlatinumGroupConcentrate = new Material.Builder(getMaterialsId(), gtqtcoreId("purified_platinum_group_concentrate"))
                .fluid()
                .color(0xFFFFC8)
                .flags(DISABLE_DECOMPOSITION)
                .components(Hydrogen, 2, Platinum, 1, Palladium, 1, Chlorine, 6)
                .build();

        GTQTMaterials.AmmoniumHexachloroplatinate = new Material.Builder(getMaterialsId(), gtqtcoreId("ammonium_hexachloroplatinate"))
                .fluid()
                .color(0xFEF0C2)
                .flags(DISABLE_DECOMPOSITION)
                .components(Nitrogen, 2, Hydrogen, 8, Platinum, 1, Chlorine, 6)
                .build()
                .setFormula("(NH4)2PtCl6", true);

        GTQTMaterials.AmmoniumHexachloropalladate = new Material.Builder(getMaterialsId(), gtqtcoreId("ammonium_hexachloropalladate"))
                .fluid()
                .color(0x808080)
                .flags(DISABLE_DECOMPOSITION)
                .components(Nitrogen, 2, Hydrogen, 8, Palladium, 1, Chlorine, 6)
                .build()
                .setFormula("(NH4)2PdCl6", true);

        GTQTMaterials.PotassiumHydroxide = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_hydroxide"))
                .dust()
                .liquid(new FluidBuilder().temperature(633))
                .color(0xFA9849)
                .flags(DISABLE_DECOMPOSITION)
                .components(Potassium, 1, Oxygen, 1, Hydrogen, 1)
                .build();

        GTQTMaterials.CarbonTetrachloride = new Material.Builder(getMaterialsId(), gtqtcoreId("carbon_tetrachloride"))
                .fluid()
                .color(0x75201A)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 1, Chlorine, 4)
                .build();

        GTQTMaterials.RutheniumChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("ruthenium_chloride"))
                .dust()
                .color(0x605C6C)
                .iconSet(MaterialIconSet.METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .components(Ruthenium, 1, Chlorine, 3)
                .build();

        GTQTMaterials.SodiumPeroxide = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_peroxide"))
                .dust()
                .color(0xECFF80)
                .iconSet(MaterialIconSet.ROUGH)
                .components(Sodium, 2, Oxygen, 2)
                .build();

        GTQTMaterials.RhodiumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("rhodium_oxide"))
                .dust()
                .color(0xD93D16)
                .iconSet(MaterialIconSet.METALLIC)
                .components(Rhodium, 2, Oxygen, 3)
                .build();

        GTQTMaterials.SodiumChlorate = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_chlorate"))
                .dust().fluid()
                .color(0xAB8D85)
                .iconSet(MaterialIconSet.METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .components(Sodium, 1, Chlorine, 1, Oxygen, 3)
                .build();

        GTQTMaterials.SulfurDichloride = new Material.Builder(getMaterialsId(), gtqtcoreId("sulfur_dichloride"))
                .fluid()
                .color(0x761410)
                .components(Sulfur, 1, Chlorine, 2)
                .build();

        GTQTMaterials.ThionylChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("thionyl_chloride"))
                .fluid()
                .color(0xEBE863)
                .flags(DISABLE_DECOMPOSITION)
                .components(Sulfur, 1, Oxygen, 1, Chlorine, 2)
                .build();

        GTQTMaterials.OsmiumTetrachloride = new Material.Builder(getMaterialsId(), gtqtcoreId("osmium_tetrachloride"))
                .dust()
                .color(0x29080A)
                .iconSet(MaterialIconSet.METALLIC)
                .components(Osmium, 1, Chlorine, 4)
                .build();

        GTQTMaterials.PotassiumBromate = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_bromate"))
                .dust()
                .color(0x782828)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(MaterialIconSet.ROUGH)
                .components(Potassium, 1, Bromine, 1, Oxygen, 3)
                .build();

        GTQTMaterials.TungstenTrioxide = new Material.Builder(getMaterialsId(), gtqtcoreId("tungsten_trioxide"))
                .dust()
                .color(0xC7D300)
                .iconSet(MaterialIconSet.DULL)
                .flags(DISABLE_DECOMPOSITION)
                .components(Tungsten, 1, Oxygen, 3)
                .build();

        GTQTMaterials.HydrogenPeroxide = new Material.Builder(getMaterialsId(), gtqtcoreId("hydrogen_peroxide"))
                .fluid()
                .color(0xD2FFFF)
                .flags(DISABLE_DECOMPOSITION)
                .components(Hydrogen, 2, Oxygen, 2)
                .build();

        GTQTMaterials.Hydrazine = new Material.Builder(getMaterialsId(), gtqtcoreId("hydrazine"))
                .fluid()
                .color(0xB50707)
                .flags(DISABLE_DECOMPOSITION)
                .components(Nitrogen, 2, Hydrogen, 4)
                .build();

        GTQTMaterials.BerylliumOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("beryllium_oxide"))
                .ingot()
                .color(0x54C757)
                .flags(GENERATE_ROD, GENERATE_RING)
                .components(Beryllium, 1, Oxygen, 1)
                .build();

        GTQTMaterials.TantalumPentoxide = new Material.Builder(getMaterialsId(), gtqtcoreId("tantalum_pentoxide"))
                .dust().ingot()
                .color(0x72728A)
                .iconSet(MaterialIconSet.ROUGH)
                .components(Tantalum, 2, Oxygen, 5)
                .build();

        GTQTMaterials.NiobiumPentoxide = new Material.Builder(getMaterialsId(), gtqtcoreId("niobium_pentoxide"))
                .dust().fluid()
                .color(0xBAB0C3)
                .iconSet(MaterialIconSet.ROUGH)
                .components(Niobium, 2, Oxygen, 5)
                .build();

        GTQTMaterials.CalciumDifluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("calcium_difluoride"))
                .dust().fluid()
                .color(0xFFFC9E)
                .iconSet(MaterialIconSet.ROUGH)
                .components(Calcium, 1, Fluorine, 2)
                .build();

        GTQTMaterials.ManganeseDifluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("manganese_difluoride"))
                .dust().fluid()
                .color(0xEF4B3D)
                .iconSet(MaterialIconSet.ROUGH)
                .components(Manganese, 1, Fluorine, 2)
                .build();

        GTQTMaterials.CalciumCarbide = new Material.Builder(getMaterialsId(), gtqtcoreId("calcium_carbide"))
                .dust().fluid()
                .color(0x807B70)
                .iconSet(MaterialIconSet.DULL)
                .components(Calcium, 1, Carbon, 2)
                .build();

        GTQTMaterials.CalciumHydroxide = new Material.Builder(getMaterialsId(), gtqtcoreId("calcium_hydroxide"))
                .dust().fluid()
                .color(0x5F8764)
                .iconSet(MaterialIconSet.ROUGH)
                .flags(DISABLE_DECOMPOSITION)
                .components(Calcium, 1, Hydrogen, 2, Oxygen, 2)
                .build()
                .setFormula("Ca(OH)2", true);

        GTQTMaterials.SodiumCyanide = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_cyanide"))
                .dust().fluid()
                .color(0x5F7C8C)
                .iconSet(MaterialIconSet.METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .components(Sodium, 1, Carbon, 1, Nitrogen, 1)
                .build();

        GTQTMaterials.ChlorosulfuricAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("chlorosulfuric_acid"))
                .fluid()
                .color(0x916C1D)
                .components(Hydrogen, 1, Sulfur, 1, Oxygen, 3, Chlorine, 1)
                .build();

        GTQTMaterials.CubicZirconia = new Material.Builder(getMaterialsId(), gtqtcoreId("cubic_zirconia"))
                .gem()
                .color(0xFFDFE2)
                .iconSet(MaterialIconSet.DIAMOND)
                .flags(CRYSTALLIZABLE, DISABLE_DECOMPOSITION)
                .components(Zirconium, 1, Oxygen, 2)
                .build();

        GTQTMaterials.MolybdenumTrioxide = new Material.Builder(getMaterialsId(), gtqtcoreId("molybdenum_trioxide"))
                .dust()
                .color(0xCBCFDA)
                .iconSet(MaterialIconSet.ROUGH)
                .flags(DISABLE_DECOMPOSITION)
                .components(Molybdenum, 1, Oxygen, 3)
                .build();

        GTQTMaterials.LeadChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("lead_chloride"))
                .dust()
                .color(0xF3F3F3)
                .iconSet(MaterialIconSet.ROUGH)
                .components(Lead, 1, Chlorine, 2)
                .build();

        GTQTMaterials.SodiumTellurite = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_tellurite"))
                .dust()
                .color(0xC6C9BE)
                .iconSet(MaterialIconSet.ROUGH)
                .flags(DISABLE_DECOMPOSITION)
                .components(Sodium, 2, Tellurium, 1, Oxygen, 3)
                .build();

        GTQTMaterials.TelluriumDioxide = new Material.Builder(getMaterialsId(), gtqtcoreId("tellurium_dioxide"))
                .dust()
                .color(0xE3DDB8)
                .iconSet(MaterialIconSet.METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .components(Tellurium, 1, Oxygen, 2)
                .build();

        GTQTMaterials.SeleniumDioxide = new Material.Builder(getMaterialsId(), gtqtcoreId("selenium_dioxide"))
                .dust()
                .color(0xE0DDD8)
                .iconSet(MaterialIconSet.METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .components(Selenium, 1, Oxygen, 2)
                .build();

        GTQTMaterials.SelenousAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("selenous_acid"))
                .dust()
                .color(0xE0E083)
                .iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Hydrogen, 2, Selenium, 1, Oxygen, 3)
                .build();

        GTQTMaterials.BoricAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("boric_acid"))
                .dust().fluid()
                .color(0xFAFAFA)
                .iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Hydrogen, 3, Boron, 1, Oxygen, 3)
                .build();

        GTQTMaterials.BoronTrifluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("boron_trifluoride"))
                .gas()
                .color(0xFAF191)
                .components(Boron, 1, Fluorine, 3)
                .build();

        GTQTMaterials.LithiumTetrafluoroborate = new Material.Builder(getMaterialsId(), gtqtcoreId("lithium_tetrafluoroborate"))
                .dust()
                .color(0x90FAF6)
                .iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Lithium, 1, Boron, 1, Fluorine, 4)
                .build();

        GTQTMaterials.Diborane = new Material.Builder(getMaterialsId(), gtqtcoreId("diborane"))
                .fluid()
                .color(0x3F3131)
                .flags(DISABLE_DECOMPOSITION)
                .components(Boron, 2, Hydrogen, 6)
                .build();

        GTQTMaterials.Borazine = new Material.Builder(getMaterialsId(), gtqtcoreId("borazine"))
                .fluid()
                .color(0x542828)
                .flags(DISABLE_DECOMPOSITION)
                .components(Boron, 3, Hydrogen, 6, Nitrogen, 3)
                .build();

        GTQTMaterials.BoronTrichloride = new Material.Builder(getMaterialsId(), gtqtcoreId("boron_trichloride"))
                .gas()
                .color(0x033F1B)
                .components(Boron, 1, Chlorine, 3)
                .build();

        GTQTMaterials.Trichloroborazine = new Material.Builder(getMaterialsId(), gtqtcoreId("trichloroborazine"))
                .fluid()
                .color(0xD62929)
                .flags(DISABLE_DECOMPOSITION)
                .components(Boron, 3, Chlorine, 3, Hydrogen, 3, Nitrogen, 3)
                .build();

        GTQTMaterials.HexagonalBoronNitride = new Material.Builder(getMaterialsId(), gtqtcoreId("hexagonal_boron_nitride"))
                .gem().fluid()
                .color(0x6A6A72)
                .iconSet(MaterialIconSet.GEM_VERTICAL)
                .flags(GENERATE_PLATE, DISABLE_DECOMPOSITION)
                .components(Boron, 1, Nitrogen, 1)
                .build()
                .setFormula("h-BN", true);

        GTQTMaterials.CubicBoronNitride = new Material.Builder(getMaterialsId(), gtqtcoreId("cubic_boron_nitride"))
                .gem().fluid()
                .color(0x545572)
                .iconSet(MaterialIconSet.DIAMOND)
                .flags(GENERATE_PLATE, DISABLE_DECOMPOSITION, CRYSTALLIZABLE, FLAMMABLE, EXPLOSIVE, DISABLE_CRYSTALLIZATION) // to disable implosion recipes
                .components(Boron, 1, Nitrogen, 1)
                .toolStats(MaterialToolProperty.Builder.of(14.0F, 9.0F, 12400, 15).build())
                .build()
                .setFormula("c-BN", true);

        GTQTMaterials.AmorphousBoronNitride = new Material.Builder(getMaterialsId(), gtqtcoreId("amorphous_boron_nitride"))
                .ingot().fluid()
                .color(0x9193C5)
                .iconSet(MaterialIconSet.SHINY)
                .flags(GENERATE_PLATE, DISABLE_DECOMPOSITION, NO_SMASHING, NO_SMELTING)
                .components(Boron, 1, Nitrogen, 1)
                .build()
                .setFormula("a-BN", true);

        GTQTMaterials.Heterodiamond = new Material.Builder(getMaterialsId(), gtqtcoreId("heterodiamond"))
                .gem()
                .color(0x512A72)
                .iconSet(MaterialIconSet.GEM_HORIZONTAL)
                .flags(GENERATE_PLATE, DISABLE_DECOMPOSITION)
                .components(Boron, 1, Carbon, 1, Nitrogen, 1)
                .build();

        GTQTMaterials.CubicHeterodiamond = new Material.Builder(getMaterialsId(), gtqtcoreId("cubic_heterodiamond"))
                .gem()
                .color(0x753DA6)
                .iconSet(MaterialIconSet.DIAMOND)
                .flags(GENERATE_PLATE, DISABLE_DECOMPOSITION)
                .components(Boron, 1, Carbon, 2, Nitrogen, 1)
                .build()
                .setFormula("c-BC2N", true);

        GTQTMaterials.LithiumHydride = new Material.Builder(getMaterialsId(), gtqtcoreId("lithium_hydride"))
                .ingot()
                .color(0x9BAFDB)
                .iconSet(MaterialIconSet.METALLIC)
                .components(Lithium, 1, Hydrogen, 1)
                .build();

        GTQTMaterials.HydrobromicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("hydrobromic_acid"))
                .fluid()
                .color(0x8D1212)
                .components(Hydrogen, 1, Bromine, 1)
                .build();

        GTQTMaterials.WhitePhosphorus = new Material.Builder(getMaterialsId(), gtqtcoreId("white_phosphorus"))
                .gem()
                .color(0xECEADD)
                .iconSet(MaterialIconSet.FLINT)
                .flags(DISABLE_DECOMPOSITION)
                .components(Phosphorus, 4)
                .build();

        GTQTMaterials.RedPhosphorus = new Material.Builder(getMaterialsId(), gtqtcoreId("red_phosphorus"))
                .gem()
                .color(0x77040E)
                .iconSet(MaterialIconSet.FLINT)
                .flags(DISABLE_DECOMPOSITION)
                .components(Phosphorus, 4)
                .build();

        GTQTMaterials.VioletPhosphorus = new Material.Builder(getMaterialsId(), gtqtcoreId("violet_phosphorus"))
                .gem()
                .color(0x8000FF)
                .iconSet(MaterialIconSet.FLINT)
                .flags(DISABLE_DECOMPOSITION)
                .components(Phosphorus, 4)
                .build();

        GTQTMaterials.BlackPhosphorus = new Material.Builder(getMaterialsId(), gtqtcoreId("black_phosphorus"))
                .dust()
                .color(0x36454F)
                .iconSet(MaterialIconSet.FLINT)
                .flags(DISABLE_DECOMPOSITION)
                .components(Phosphorus, 4)
                .build();

        GTQTMaterials.BluePhosphorus = new Material.Builder(getMaterialsId(), gtqtcoreId("blue_phosphorus"))
                .gem()
                .color(0x9BE3E4)
                .iconSet(MaterialIconSet.FLINT)
                .flags(DISABLE_DECOMPOSITION)
                .components(Phosphorus, 4)
                .build();

        GTQTMaterials.Phosphorene = new Material.Builder(getMaterialsId(), gtqtcoreId("phosphorene"))
                .ingot()
                .color(0x273239)
                .iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION, GENERATE_FOIL)
                .components(Phosphorus, 4)
                .build();

        GTQTMaterials.PhosphorusTrichloride = new Material.Builder(getMaterialsId(), gtqtcoreId("phosphorus_trichloride"))
                .fluid()
                .color(0xE8C474)
                .components(Phosphorus, 1, Chlorine, 3)
                .build();

        GTQTMaterials.PhosphorylChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("phosphoryl_chloride"))
                .fluid()
                .color(0xE8BB5B)
                .components(Phosphorus, 1, Oxygen, 1, Chlorine, 3)
                .build();

        GTQTMaterials.ZincOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("zinc_oxide"))
                .dust()
                .color(0xB85C34)
                .flags(DECOMPOSITION_BY_ELECTROLYZING)
                .components(Zinc, 1, Oxygen, 1)
                .build();

        GTQTMaterials.GermaniumTetrachloride = new Material.Builder(getMaterialsId(), gtqtcoreId("germanium_tetrachloride"))
                .fluid()
                .color(0x787878)
                .flags(DISABLE_DECOMPOSITION)
                .components(Germanium, 1, Chlorine, 4)
                .build();

        GTQTMaterials.GermaniumDioxide = new Material.Builder(getMaterialsId(), gtqtcoreId("germanium_dioxide"))
                .dust()
                .color(0x666666)
                .flags(DISABLE_DECOMPOSITION)
                .components(Germanium, 1, Oxygen, 2)
                .build();

        GTQTMaterials.SiliconTetrachloride = new Material.Builder(getMaterialsId(), gtqtcoreId("silicon_tetrachloride"))
                .fluid()
                .color(0x5B5B7A)
                .flags(DISABLE_DECOMPOSITION)
                .components(Silicon, 1, Chlorine, 4)
                .build();

        GTQTMaterials.GSTGlass = new Material.Builder(getMaterialsId(), gtqtcoreId("gst_glass"))
                .ingot().fluid()
                .color(0xCFFFFF)
                .iconSet(MaterialIconSet.SHINY)
                .flags(GENERATE_PLATE, NO_SMASHING, NO_WORKING, DECOMPOSITION_BY_CENTRIFUGING)
                .components(Germanium, 2, Antimony, 2, Tellurium, 5)
                .blast(873, MID)
                .build();

        GTQTMaterials.ZBLANGlass = new Material.Builder(getMaterialsId(), gtqtcoreId("zblan_glass"))
                .ingot().fluid()
                .color(0xACB4BC)
                .iconSet(MaterialIconSet.SHINY)
                .flags(NO_SMASHING, NO_WORKING, DISABLE_DECOMPOSITION)
                .components(Zirconium, 5, Barium, 2, Lanthanum, 1, Aluminium, 1, Sodium, 2, Fluorine, 6)
                .build()
                .setFormula("(ZrF4)5(BaF2)2(LaF3)(AlF3)(NaF)2", true);

        GTQTMaterials.HeliumNeon = new Material.Builder(getMaterialsId(), gtqtcoreId("helium_neon"))
                .gas()
                .color(0xFF0080)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .components(Helium, 9, Neon, 1)
                .build();

        GTQTMaterials.AluminiumHydroxide = new Material.Builder(getMaterialsId(), gtqtcoreId("aluminium_hydroxide"))
                .dust()
                .color(0xBEBEC8)
                .flags(DISABLE_DECOMPOSITION)
                .components(Aluminium, 1, Oxygen, 3, Hydrogen, 3)
                .build()
                .setFormula("Al(OH)3", true);

        GTQTMaterials.AluminiumTrichloride = new Material.Builder(getMaterialsId(), gtqtcoreId("aluminium_trichloride"))
                .dust()
                .color(0x78C3EB)
                .iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Aluminium, 1, Chlorine, 3)
                .build();

        GTQTMaterials.GalliumTrichloride = new Material.Builder(getMaterialsId(), gtqtcoreId("gallium_trichloride"))
                .dust()
                .color(0x6EB4FF)
                .iconSet(MaterialIconSet.ROUGH)
                .components(Gallium, 1, Chlorine, 3)
                .build();

        GTQTMaterials.GalliumTrioxide = new Material.Builder(getMaterialsId(), gtqtcoreId("gallium_trioxide"))
                .dust().fluid()
                .color(0xE4CDFF)
                .iconSet(MaterialIconSet.METALLIC)
                .components(Gallium, 1, Oxygen, 3)
                .build();

        GTQTMaterials.GalliumNitride = new Material.Builder(getMaterialsId(), gtqtcoreId("gallium_nitride"))
                .ingot()
                .color(0xFFF458)
                .iconSet(MaterialIconSet.SHINY)
                .flags(GENERATE_PLATE)
                .components(Gallium, 1, Nitrogen, 1)
                .build();

        GTQTMaterials.CadmiumBromide = new Material.Builder(getMaterialsId(), gtqtcoreId("cadmium_bromide"))
                .dust()
                .color(0xFF1774)
                .iconSet(MaterialIconSet.SHINY)
                .components(Cadmium, 1, Bromine, 2)
                .build();

        GTQTMaterials.MagnesiumBromide = new Material.Builder(getMaterialsId(), gtqtcoreId("magnesium_bromide"))
                .dust()
                .color(0x5F4C32)
                .iconSet(MaterialIconSet.METALLIC)
                .components(Magnesium, 1, Bromine, 2)
                .build();

        GTQTMaterials.CadmiumSulfide = new Material.Builder(getMaterialsId(), gtqtcoreId("cadmium_sulfide"))
                .dust().ingot()
                .color(0xC8C43C)
                .flags(DECOMPOSITION_BY_ELECTROLYZING, GENERATE_PLATE)
                .iconSet(MaterialIconSet.METALLIC)
                .components(Cadmium, 1, Sulfur, 1)
                .build();

        GTQTMaterials.CadmiumSelenide = new Material.Builder(getMaterialsId(), gtqtcoreId("cadmium_selenide")) //TODO "Quantum Dots" tooltip
                .dust()
                .color(0x983034)
                .flags(DECOMPOSITION_BY_ELECTROLYZING)
                .iconSet(MaterialIconSet.METALLIC)
                .components(Cadmium, 1, Selenium, 1)
                .build();

        GTQTMaterials.Phosphine = new Material.Builder(getMaterialsId(), gtqtcoreId("phosphine"))
                .gas()
                .color(0xACB330)
                .flags(DECOMPOSITION_BY_ELECTROLYZING, FLAMMABLE)
                .components(Phosphorus, 1, Hydrogen, 3)
                .build();

        GTQTMaterials.PlutoniumTrihydride = new Material.Builder(getMaterialsId(), gtqtcoreId("plutonium_trihydride"))
                .dust()
                .color(0x140002)
                .iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Plutonium239, 1, Hydrogen, 3)
                .build()
                .setFormula("PuH3", true);

        GTQTMaterials.PlutoniumPhosphide = new Material.Builder(getMaterialsId(), gtqtcoreId("plutonium_phosphide"))
                .ingot()
                .color(0x1F0104)
                .iconSet(MaterialIconSet.MAGNETIC)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD)
                .components(Plutonium239, 1, Phosphorus, 1)
                .build()
                .setFormula("PuP", true);

        GTQTMaterials.LithiumHydroxide = new Material.Builder(getMaterialsId(), gtqtcoreId("lithium_hydroxide"))
                .dust()
                .color(0xDECAFA)
                .iconSet(MaterialIconSet.FINE)
                .components(Lithium, 1, Oxygen, 1, Hydrogen, 1)
                .build();

        GTQTMaterials.LithiumAmalgam = new Material.Builder(getMaterialsId(), gtqtcoreId("lithium_amalgam"))
                .fluid()
                .color(0xAEA7D4).iconSet(MaterialIconSet.FINE)
                .flags(DISABLE_DECOMPOSITION)
                .components(Mercury, 1, Lithium, 1)
                .build();

        GTQTMaterials.Lithium7Hydroxide = new Material.Builder(getMaterialsId(), gtqtcoreId("lithium_7_hydroxide"))
                .dust()
                .fluid()
                .color(0xAEAACA).iconSet(MaterialIconSet.FINE)
                .flags(DECOMPOSITION_BY_ELECTROLYZING)
                .components(GTQTMaterials.Lithium7, 1, Oxygen, 1, Hydrogen, 1)
                .build()
                .setFormula("LiOH", true);

        GTQTMaterials.NeptuniumAluminide = new Material.Builder(getMaterialsId(), gtqtcoreId("neptunium_aluminide"))
                .ingot().fluid()
                .color(0x5E228F)
                .iconSet(MaterialIconSet.MAGNETIC)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD)
                .components(Neptunium, 1, Aluminium, 3)
                .blast(1568, HIGHER)
                .build()
                .setFormula("NpAl3", true);

        GTQTMaterials.BismuthTrioxide = new Material.Builder(getMaterialsId(), gtqtcoreId("bismuth_trioxide"))
                .dust()
                .color(0xF5EF42).iconSet(MaterialIconSet.FINE)
                .components(Bismuth, 2, Oxygen, 3)
                .build();

        GTQTMaterials.FerricOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("ferric_oxide"))
                .dust()
                .color(0x915A5A).iconSet(MaterialIconSet.ROUGH)
                .components(Iron, 2, Oxygen, 3)
                .build();

        GTQTMaterials.BismuthChalcogenide = new Material.Builder(getMaterialsId(), gtqtcoreId("bismuth_chalcogenide")) //TODO "3D Topological Isolator" tooltip
                .ingot()
                .color(0x91994D).iconSet(MaterialIconSet.SHINY)
                .flags(GENERATE_PLATE, GENERATE_FOIL, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Bismuth, 1, Antimony, 1, Tellurium, 2, Sulfur, 1)
                .build();

        GTQTMaterials.MercuryCadmiumTelluride = new Material.Builder(getMaterialsId(), gtqtcoreId("mercury_cadmium_telluride"))
                .ingot().fluid()
                .color(0x823C80).iconSet(MaterialIconSet.BRIGHT)
                .flags(GENERATE_FINE_WIRE)
                .components(Mercury, 2, Cadmium, 1, Tellurium, 2)
                .cableProperties(GTValues.V[GTValues.UHV], 6, 8)
                .blast(2170, HIGHER)
                .build();

        GTQTMaterials.AluminiumSelenide = new Material.Builder(getMaterialsId(), gtqtcoreId("aluminium_selenide"))
                .dust()
                .color(0x969651)
                .components(Aluminium, 2, Selenium, 3)
                .build();

        GTQTMaterials.HydrogenSelenide = new Material.Builder(getMaterialsId(), gtqtcoreId("hydrogen_selenide"))
                .gas()
                .color(0x42f554)
                .components(Hydrogen, 2, Selenium, 1)
                .build();

        GTQTMaterials.PalladiumNitrate = new Material.Builder(getMaterialsId(), gtqtcoreId("palladium_nitrate"))
                .dust()
                .color(0x82312A).iconSet(MaterialIconSet.METALLIC)
                .components(Palladium, 1, Nitrogen, 2, Oxygen, 6)
                .build()
                .setFormula("Pd(NO3)2", true);

        GTQTMaterials.Fullerene = new Material.Builder(getMaterialsId(), gtqtcoreId("fullerene"))
                .ingot()
                .color(0x72556A)
                .iconSet(MaterialIconSet.BRIGHT)
                .flags(DISABLE_DECOMPOSITION, NO_SMELTING, GENERATE_PLATE, GENERATE_FOIL, GENERATE_ROD, GENERATE_RING,
                        GENERATE_FRAME)
                .components(Carbon, 60)
                .build();

        GTQTMaterials.ThalliumCopperChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("thallium_copper_chloride")) //TODO "Antiferromagnetic" Tooltip
                .ingot().fluid()
                .color(0x3C5CB5)
                .iconSet(MaterialIconSet.MAGNETIC)
                .flags(GENERATE_FINE_WIRE)
                .components(Thallium, 1, Copper, 1, Chlorine, 3)
                .build();

        GTQTMaterials.PerrhenicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("perrhenic_acid"))
                .dust()
                .color(0xE6DC70)
                .iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Hydrogen, 1, Rhenium, 1, Oxygen, 4)
                .build();

        GTQTMaterials.AmmoniumPerrhenate = new Material.Builder(getMaterialsId(), gtqtcoreId("ammonium_perrhenate"))
                .dust()
                .color(0xA69970)
                .iconSet(MaterialIconSet.METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .components(Nitrogen, 1, Hydrogen, 4, Rhenium, 1, Oxygen, 4)
                .build();

        GTQTMaterials.ThalliumSulfate = new Material.Builder(getMaterialsId(), gtqtcoreId("thallium_sulfate"))
                .dust()
                .color(0x9C222C)
                .iconSet(MaterialIconSet.METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .components(Thallium, 2, Sulfur, 1, Oxygen, 4)
                .build();

        GTQTMaterials.HeavyTaraniumFuel = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_taranium_fuel"))
                .fluid()
                .color(0x141414)
                .flags(DISABLE_DECOMPOSITION)
                .components(GTQTMaterials.Taranium, 1)
                .build();

        GTQTMaterials.MediumTaraniumFuel = new Material.Builder(getMaterialsId(), gtqtcoreId("medium_taranium_fuel"))
                .fluid()
                .color(0x181818)
                .flags(DISABLE_DECOMPOSITION)
                .components(GTQTMaterials.Taranium, 1)
                .build();

        GTQTMaterials.LightTaraniumFuel = new Material.Builder(getMaterialsId(), gtqtcoreId("light_taranium_fuel"))
                .fluid()
                .color(0x1C1C1C)
                .flags(DISABLE_DECOMPOSITION)
                .components(GTQTMaterials.Taranium, 1)
                .build();

        GTQTMaterials.HeavyEnrichedTaraniumFuel = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_enriched_taranium_fuel"))
                .fluid()
                .color(0x0F1414)
                .flags(DISABLE_DECOMPOSITION)
                .components(GTQTMaterials.Taranium, 1)
                .build();

        GTQTMaterials.MediumEnrichedTaraniumFuel = new Material.Builder(getMaterialsId(), gtqtcoreId("medium_enriched_taranium_fuel"))
                .fluid()
                .color(0x0F1818)
                .flags(DISABLE_DECOMPOSITION)
                .components(GTQTMaterials.Taranium, 1)
                .build();

        GTQTMaterials.LightEnrichedTaraniumFuel = new Material.Builder(getMaterialsId(), gtqtcoreId("light_enriched_taranium_fuel"))
                .fluid()
                .color(0x0F1C1C)
                .flags(DISABLE_DECOMPOSITION)
                .components(GTQTMaterials.Taranium, 1)
                .build();

        GTQTMaterials.Adamantite = new Material.Builder(getMaterialsId(), gtqtcoreId("adamantite"))
                .dust()
                .fluidPipeProperties(18000, 36000, true)
                .color(0xC83C3C)
                .iconSet(MaterialIconSet.ROUGH)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_FOIL, GENERATE_FRAME, GENERATE_GEAR, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_FRAME)
                .components(GTQTMaterials.Adamantium, 3, Oxygen, 4)
                .build();

        GTQTMaterials.AdamantiumUnstable = new Material.Builder(getMaterialsId(), gtqtcoreId("adamantium_unstable"))
                .fluid()
                .color(0xFF763C)
                .flags(DISABLE_DECOMPOSITION)
                .components(GTQTMaterials.Adamantium, 1)
                .build();

        GTQTMaterials.OrichalcumEnergized = new Material.Builder(getMaterialsId(), gtqtcoreId("orichalcum_energized"))
                .dust()
                .color(0xF4FC0C)
                .iconSet(MaterialIconSet.BRIGHT)
                .flags(DISABLE_DECOMPOSITION)
                .components(GTQTMaterials.Orichalcum, 1)
                .build();

        GTQTMaterials.AdamantiumEnriched = new Material.Builder(getMaterialsId(), gtqtcoreId("adamantium_enriched"))
                .dust()
                .color(0x64B4FF)
                .iconSet(MaterialIconSet.ROUGH)
                .flags(DISABLE_DECOMPOSITION)
                .components(GTQTMaterials.Vibranium, 1, RareEarth, 1)
                .build();

        GTQTMaterials.DeepIron = new Material.Builder(getMaterialsId(), gtqtcoreId("deep_iron"))
                .dust()
                .color(0x968C8C)
                .iconSet(MaterialIconSet.METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .components(Iron, 2, Trinium, 1, Indium, 1)
                .build();

        GTQTMaterials.VibraniumUnstable = new Material.Builder(getMaterialsId(), gtqtcoreId("vibranium_unstable"))
                .fluid()
                .color(0xFF7832)
                .flags(DISABLE_DECOMPOSITION)
                .components(GTQTMaterials.Vibranium, 1)
                .build();

        GTQTMaterials.SiliconCarbide = new Material.Builder(getMaterialsId(), gtqtcoreId("silicon_carbide")) //TODO Carborundum tooltip
                .dust()
                .fluid()
                .color(0x4D4D4D)
                .iconSet(METALLIC)
                .flags(GENERATE_FINE_WIRE)
                .components(Silicon, 1, Carbon, 1)
                .blast(b -> b
                        .temp(2500, BlastProperty.GasTier.HIGH)
                        .blastStats(VA[UV])
                        .vacuumStats(VA[EV], 280))
                .cableProperties(V[UHV], 6, 8, false)
                .flags(GENERATE_PLATE, GENERATE_FOIL, GENERATE_ROD, GENERATE_GEAR, GENERATE_FRAME, GENERATE_DOUBLE_PLATE)
                .build();

        GTQTMaterials.ChromiumGermaniumTelluride = new Material.Builder(getMaterialsId(), gtqtcoreId("cgt"))
                .ingot().fluid()
                .color(0x8F103E)
                .iconSet(MaterialIconSet.METALLIC)
                .flags(GENERATE_ROD, GENERATE_LONG_ROD)
                .components(Chrome, 1, Germanium, 1, Tellurium, 3)
                .blast(2900, HIGHER)
                .build();

        GTQTMaterials.Kovar = new Material.Builder(getMaterialsId(), gtqtcoreId("kovar"))
                .ingot()
                .color(0xCBC0A6)
                .flags(GENERATE_ROD, GENERATE_RING, DECOMPOSITION_BY_CENTRIFUGING)
                .components(Iron, 4, Nickel, 2, Cobalt, 1)
                .build();

        GTQTMaterials.StannicChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("stannic_chloride"))
                .fluid()
                .color(0x33BBF5)
                .components(Tin, 1, Chlorine, 4)
                .build();

        GTQTMaterials.RubidiumChlorostannate = new Material.Builder(getMaterialsId(), gtqtcoreId("rubidium_chlorostannate"))
                .dust()
                .color(0xBD888A)
                .iconSet(MaterialIconSet.METALLIC)
                .components(Rubidium, 2, Tin, 1, Chlorine, 6)
                .build();

        GTQTMaterials.CaesiumChlorostannate = new Material.Builder(getMaterialsId(), gtqtcoreId("caesium_chlorostannate"))
                .dust()
                .color(0xBDAD88)
                .iconSet(MaterialIconSet.SHINY)
                .components(Caesium, 2, Tin, 1, Chlorine, 6)
                .build();

        GTQTMaterials.HRAMagnesium = new Material.Builder(getMaterialsId(), gtqtcoreId("hra_magnesium")) //TODO "Reike Metal" tooltip
                .dust()
                .color(Magnesium.getMaterialRGB())
                .iconSet(MaterialIconSet.SHINY)
                .components(Magnesium, 1)
                .build();

        GTQTMaterials.LithiumFluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("lithium_fluoride"))
                .dust()
                .color(0x9AE7AD)
                .components(Lithium, 1, Fluorine, 1)
                .build();

        GTQTMaterials.Alumina = new Material.Builder(getMaterialsId(), gtqtcoreId("alumina"))
                .dust()
                .color(0x0b5394).iconSet(MaterialIconSet.SHINY)
                .components(Aluminium, 2, Oxygen, 3)
                .build();

        //  11154 Alumina Solution
        GTQTMaterials.AluminaSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("alumina_solution"))
                .liquid()
                .color(0x6C4DC1)
                .components(Alumina, 1, Carbon, 25, Hydrogen, 56, Chlorine, 2, Nitrogen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(Al2O3)(CH2Cl2)(C12H27N)2", true);

        GTQTMaterials.Hematite = new Material.Builder(getMaterialsId(), gtqtcoreId("hematite"))
                .dust().ore()
                .color(0xa52222)
                .components(Iron, 2, Oxygen, 3)
                .build();

        GTQTMaterials.Tetrahydrofuran = new Material.Builder(getMaterialsId(), gtqtcoreId("tetrahydrofuran")) //TODO "THF" tooltip
                .fluid()
                .color(0x3234A8)
                .components(Carbon, 4, Hydrogen, 8, Oxygen, 1)
                .build();

        GTQTMaterials.Ethylhexanol = new Material.Builder(getMaterialsId(), gtqtcoreId("ethylhexanol"))
                .fluid()
                .color(0xFEEA9A)
                .components(Carbon, 8, Hydrogen, 10, Oxygen, 1)
                .build();

        GTQTMaterials.DiethylhexylPhosphoricAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("diethylhexyl_phosphoric_acid"))
                .fluid()
                .color(0xFFFF99)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 16, Hydrogen, 35, Oxygen, 4, Phosphorus, 1)
                .build()
                .setFormula("(C8H7O)2PO2H", true);

        GTQTMaterials.Butanol = new Material.Builder(getMaterialsId(), gtqtcoreId("butanol"))
                .fluid()
                .color(0xC7AF2E)
                .components(Carbon, 4, Hydrogen, 10, Oxygen, 1)
                .build()
                .setFormula("C4H9OH", true);

        GTQTMaterials.MethylFormate = new Material.Builder(getMaterialsId(), gtqtcoreId("methyl_formate"))
                .fluid()
                .color(0xFFAAAA)
                .flags(DISABLE_DECOMPOSITION)
                .components(Hydrogen, 4, Carbon, 2, Oxygen, 2)
                .build()
                .setFormula("HCO2CH3", true);

        GTQTMaterials.FormicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("formic_acid"))
                .color(0xFFAA77).fluid()
                .flags(DISABLE_DECOMPOSITION)
                .components(Hydrogen, 2, Carbon, 1, Oxygen, 2)
                .build()
                .setFormula("HCOOH", true);

        GTQTMaterials.PhthalicAnhydride = new Material.Builder(getMaterialsId(), gtqtcoreId("phthalic_anhydride"))
                .dust()
                .color(0xEEAAEE)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 8, Hydrogen, 4, Oxygen, 3)
                .build()
                .setFormula("C6H4(CO)2O", true);

        GTQTMaterials.Ethylanthraquinone = new Material.Builder(getMaterialsId(), gtqtcoreId("ethylanthraquinone"))
                .fluid()
                .color(0xCC865A)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 16, Hydrogen, 12, Oxygen, 2)
                .build()
                .setFormula("C6H4(CO)2C6H3Et", true);

        GTQTMaterials.Ethylanthrahydroquinone = new Material.Builder(getMaterialsId(), gtqtcoreId("ethylanthrahydroquinone"))
                .fluid()
                .color(0xAD531A)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 16, Hydrogen, 18, Oxygen, 2)
                .build()
                .setFormula("C6H4(CH2OH)2C6H3Et", true);

        GTQTMaterials.Formaldehyde = new Material.Builder(getMaterialsId(), gtqtcoreId("formaldehyde"))
                .fluid()
                .color(0x95A13A)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 1, Hydrogen, 2, Oxygen, 1)
                .build();

        GTQTMaterials.Acetylene = new Material.Builder(getMaterialsId(), gtqtcoreId("acetylene"))
                .fluid()
                .color(0x959C60)
                .components(Carbon, 2, Hydrogen, 2)
                .build();

        GTQTMaterials.Turpentine = new Material.Builder(getMaterialsId(), gtqtcoreId("turpentine"))
                .fluid()
                .color(0x93BD46)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 10, Hydrogen, 16)
                .build();

        GTQTMaterials.Dichloroethane = new Material.Builder(getMaterialsId(), gtqtcoreId("dichloroethane"))
                .fluid()
                .color(0xDAAED3)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 2, Hydrogen, 4, Chlorine, 2)
                .build();

        GTQTMaterials.Trichloroethylene = new Material.Builder(getMaterialsId(), gtqtcoreId("trichloroethylene"))
                .fluid()
                .color(0xB685B1)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 2, Hydrogen, 1, Chlorine, 3)
                .build();

        GTQTMaterials.ChloroaceticAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("chloroacetic_acid"))
                .fluid()
                .color(0x38541A)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(MaterialIconSet.SHINY)
                .components(Carbon, 2, Hydrogen, 3, Chlorine, 1, Oxygen, 2)
                .build();

        GTQTMaterials.MalonicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("malonic_acid"))
                .dust()
                .color(0x61932E)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(MaterialIconSet.SHINY)
                .components(Carbon, 3, Hydrogen, 4, Oxygen, 4)
                .build();

        GTQTMaterials.TetramethylammoniumChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("tetramethylammonium_chloride"))
                .dust().fluid()
                .color(0x27FF81)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(MaterialIconSet.METALLIC)
                .components(Carbon, 4, Hydrogen, 12, Nitrogen, 1, Chlorine, 1)
                .build()
                .setFormula("N(CH3)4Cl", true);

        GTQTMaterials.Ethylenediamine = new Material.Builder(getMaterialsId(), gtqtcoreId("ethylenediamine"))
                .fluid()
                .color(0xD00ED0)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 2, Hydrogen, 8, Nitrogen, 2)
                .build()
                .setFormula("C2H4(NH2)2", true);

        GTQTMaterials.HydrogenCyanide = new Material.Builder(getMaterialsId(), gtqtcoreId("hydrogen_cyanide"))
                .fluid()
                .color(0xB6D1AE)
                .flags(DISABLE_DECOMPOSITION)
                .components(Hydrogen, 1, Carbon, 1, Nitrogen, 1)
                .build();

        GTQTMaterials.TetrasodiumEDTA = new Material.Builder(getMaterialsId(), gtqtcoreId("tetrasodium_edta"))
                .dust()
                .color(0x8890E0)
                .iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 10, Hydrogen, 12, Nitrogen, 2, Oxygen, 8, Sodium, 4)
                .build();

        GTQTMaterials.EthylenediaminetetraaceticAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("ethylenediaminetetraacetic_acid")) //TODO EDTA Tooltip
                .fluid()
                .color(0x87E6D9)
                .iconSet(MaterialIconSet.ROUGH)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 10, Hydrogen, 16, Nitrogen, 2, Oxygen, 8)
                .build();

        GTQTMaterials.Aniline = new Material.Builder(getMaterialsId(), gtqtcoreId("aniline"))
                .fluid()
                .color(0x4C911D)
                .components(Carbon, 6, Hydrogen, 7, Nitrogen, 1)
                .build()
                .setFormula("C6H5NH2", true);

        GTQTMaterials.ParaXylene = new Material.Builder(getMaterialsId(), gtqtcoreId("para_xylene"))
                .fluid()
                .color(0x666040)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 8, Hydrogen, 10)
                .build()
                .setFormula("C6H4(CH3)2", true);

        GTQTMaterials.Durene = new Material.Builder(getMaterialsId(), gtqtcoreId("durene"))
                .dust()
                .color(0x336040)
                .iconSet(MaterialIconSet.FINE)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 10, Hydrogen, 14)
                .build()
                .setFormula("C6H2(CH3)4", true);

        GTQTMaterials.PyromelliticDianhydride = new Material.Builder(getMaterialsId(), gtqtcoreId("pyromellitic_dianhydride")) //TODO PDMA Tooltip
                .dust()
                .color(0xF0EAD6)
                .iconSet(MaterialIconSet.ROUGH)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 10, Hydrogen, 2, Oxygen, 6)
                .build()
                .setFormula("C6H2(C2O3)2", true);

        GTQTMaterials.Aminophenol = new Material.Builder(getMaterialsId(), gtqtcoreId("aminophenol"))
                .fluid()
                .color(0xFF7F50)
                .components(Carbon, 6, Hydrogen, 7, Nitrogen, 1, Oxygen, 1)
                .build()
                .setFormula("HOC6H4NH2", true);

        GTQTMaterials.Dimethylformamide = new Material.Builder(getMaterialsId(), gtqtcoreId("dimethylformamide"))
                .fluid()
                .color(0x42BDFF)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 3, Hydrogen, 7, Nitrogen, 1, Oxygen, 1)
                .build()
                .setFormula("(CH3)2NC(O)H", true);

        GTQTMaterials.Oxydianiline = new Material.Builder(getMaterialsId(), gtqtcoreId("oxydianiline"))
                .dust()
                .color(0xF0E130)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 12, Hydrogen, 12, Nitrogen, 2, Oxygen, 1)
                .build()
                .setFormula("O(C6H4NH2)2", true);

        GTQTMaterials.KaptonK = new Material.Builder(getMaterialsId(), gtqtcoreId("kapton_k"))
                .ingot().fluid()
                .color(0xFFCE52)
                .flags(DISABLE_DECOMPOSITION, NO_SMASHING, NO_SMELTING, GENERATE_PLATE, GENERATE_FOIL)
                .components(Carbon, 12, Hydrogen, 12, Nitrogen, 2, Oxygen, 1)
                .build()
                .setFormula("(C7H2N2O4)(O(C6H4)2)", true);

        GTQTMaterials.BiphenylTetracarboxylicAcidDianhydride = new Material.Builder(getMaterialsId(), gtqtcoreId("biphenyl_tetracarboxylic_acid_dianhydride")) //TODO BPDA Tooltip
                .dust()
                .color(0xFF7F50)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 16, Hydrogen, 6, Oxygen, 6)
                .build()
                .setFormula("(C8H3O3)2", true);

        GTQTMaterials.Nitroaniline = new Material.Builder(getMaterialsId(), gtqtcoreId("nitroaniline"))
                .fluid()
                .color(0x2A6E68)
                .components(Carbon, 6, Hydrogen, 6, Nitrogen, 2, Oxygen, 2)
                .build()
                .setFormula("H2NC6H4NO2", true);

        GTQTMaterials.ParaPhenylenediamine = new Material.Builder(getMaterialsId(), gtqtcoreId("para_phenylenediamine"))
                .dust()
                .color(0x4A8E7B)
                .iconSet(MaterialIconSet.ROUGH)
                .components(Carbon, 6, Hydrogen, 8, Nitrogen, 2)
                .build()
                .setFormula("H2NC6H4NH2", true);

        GTQTMaterials.KaptonE = new Material.Builder(getMaterialsId(), gtqtcoreId("kapton_e"))
                .ingot().fluid()
                .color(0xFFDF8C)
                .flags(DISABLE_DECOMPOSITION, GENERATE_PLATE, NO_SMASHING, NO_SMELTING, GENERATE_FOIL)
                .components(Carbon, 12, Hydrogen, 12, Nitrogen, 2, Oxygen, 1)
                .build()
                .setFormula("O(C6H4NH2)2", true);

        GTQTMaterials.Methylamine = new Material.Builder(getMaterialsId(), gtqtcoreId("methylamine"))
                .gas()
                .color(0xAA6600)
                .components(Carbon, 1, Hydrogen, 5, Nitrogen, 1)
                .build()
                .setFormula("CH3NH2", true);

        GTQTMaterials.Trimethylamine = new Material.Builder(getMaterialsId(), gtqtcoreId("trimethylamine"))
                .gas()
                .color(0xBB7700)
                .components(Carbon, 3, Hydrogen, 9, Nitrogen, 1)
                .build()
                .setFormula("(CH3)3N", true);

        GTQTMaterials.Bistrichloromethylbenzene = new Material.Builder(getMaterialsId(), gtqtcoreId("bistrichloromethylbenzene"))
                .fluid()
                .color(0xCF8498)
                .components(Carbon, 8, Hydrogen, 4, Chlorine, 6)
                .build()
                .setFormula("C6H4(CCl3)2", true);

        GTQTMaterials.Tetrabromoethane = new Material.Builder(getMaterialsId(), gtqtcoreId("tetrabromoethane"))
                .fluid()
                .color(0x5AAADA)
                .components(Carbon, 2, Hydrogen, 2, Bromine, 4)
                .build();

        GTQTMaterials.TerephthalicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("terephthalic_acid")) //TODO "PTA" Tooltip
                .dust()
                .color(0x5ACCDA)
                .iconSet(MaterialIconSet.ROUGH)
                .components(Carbon, 8, Hydrogen, 6, Oxygen, 4)
                .build()
                .setFormula("C6H4(CO2H)2", true);

        GTQTMaterials.TerephthaloylChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("terephthaloyl_chloride"))
                .dust()
                .color(0xFAC4DA)
                .iconSet(MaterialIconSet.SHINY)
                .components(Carbon, 8, Hydrogen, 4, Oxygen, 2, Chlorine, 2)
                .build()
                .setFormula("C6H4(COCl)2", true);

        GTQTMaterials.Butanediol = new Material.Builder(getMaterialsId(), gtqtcoreId("butanediol"))
                .fluid()
                .color(0xAAC4DA)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 4, Hydrogen, 10, Oxygen, 2)
                .build()
                .setFormula("C4H8(OH)2", true);

        GTQTMaterials.GammaButyrolactone = new Material.Builder(getMaterialsId(), gtqtcoreId("gamma_butyrolactone"))
                .fluid()
                .color(0xAF04D6)
                .components(Carbon, 4, Hydrogen, 6, Oxygen, 2)
                .build();

        GTQTMaterials.NMethylPyrrolidone = new Material.Builder(getMaterialsId(), gtqtcoreId("n_methyl_pyrrolidone"))
                .fluid()
                .color(0xA504D6)
                .components(Carbon, 5, Hydrogen, 9, Nitrogen, 1, Oxygen, 1)
                .build();

        GTQTMaterials.Kevlar = new Material.Builder(getMaterialsId(), gtqtcoreId("kevlar"))
                .polymer()
                .liquid().ingot()
                .color(0xF0F078)
                .flags(DISABLE_DECOMPOSITION, NO_SMASHING, NO_SMELTING, GENERATE_PLATE,
                        GENERATE_FINE_WIRE, GENERATE_RING)
                .components(Carbon, 14, Hydrogen, 10, Nitrogen, 2, Oxygen, 2)
                .fluidPipeProperties(2000, 800, true)
                .build()
                .setFormula("(C6H4)2(CO)2(NH)2", true);

        GTQTMaterials.TetramethylammoniumHydroxide = new Material.Builder(getMaterialsId(), gtqtcoreId("tetramethylammonium_hydroxide")) //TODO "TMAH" tooltip
                .fluid() //this should be a solid, however it will be liquid for circuit etching purposes
                .color(0x40FFD6)
                .flags(DISABLE_DECOMPOSITION)
                .components(Nitrogen, 1, Carbon, 4, Hydrogen, 12, Oxygen, 1, Hydrogen, 1)
                .build()
                .setFormula("N(CH3)4OH", true);

        GTQTMaterials.Pyrocatechol = new Material.Builder(getMaterialsId(), gtqtcoreId("pyrocatechol"))
                .dust()
                .color(0x784421)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(MaterialIconSet.DULL)
                .components(Carbon, 6, Hydrogen, 6, Oxygen, 2)
                .build();

        GTQTMaterials.EthyleneOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("ethylene_oxide"))
                .gas()
                .color(0xDCBFE1)
                .components(Carbon, 2, Hydrogen, 4, Oxygen, 1)
                .build();

        GTQTMaterials.EthyleneGlycol = new Material.Builder(getMaterialsId(), gtqtcoreId("ethylene_glycol"))
                .fluid()
                .color(0x286632)
                .components(Carbon, 2, Hydrogen, 6, Oxygen, 2)
                .build()
                .setFormula("C2H4(OH)2", true);

        GTQTMaterials.Diacetyl = new Material.Builder(getMaterialsId(), gtqtcoreId("diacetyl"))
                .fluid()
                .color(0xF7FF65)
                .components(Carbon, 4, Hydrogen, 6, Oxygen, 2)
                .build();

        GTQTMaterials.AcetoneCyanohydrin = new Material.Builder(getMaterialsId(), gtqtcoreId("acetone_cyanohydrin"))
                .fluid()
                .color(0xA1FFD0)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 4, Hydrogen, 7, Nitrogen, 1, Oxygen, 1)
                .build();

        GTQTMaterials.PMMA = new Material.Builder(getMaterialsId(), gtqtcoreId("polymethylmethacrylate"))
                .ingot().fluid()
                .color(0x91CAE1)
                .flags(DISABLE_DECOMPOSITION, NO_SMASHING, NO_SMELTING, GENERATE_PLATE)
                .components(Carbon, 5, Hydrogen, 8, Oxygen, 2)
                .build();

        GTQTMaterials.Trimethylaluminium = new Material.Builder(getMaterialsId(), gtqtcoreId("trimethylaluminium"))
                .fluid()
                .color(0x6ECCFF)
                .flags(DISABLE_DECOMPOSITION)
                .components(Aluminium, 2, Carbon, 6, Hydrogen, 18)
                .build()
                .setFormula("Al2(CH3)6", true);

        GTQTMaterials.Trimethylgallium = new Material.Builder(getMaterialsId(), gtqtcoreId("trimethylgallium"))
                .fluid()
                .color(0x4F92FF)
                .flags(DISABLE_DECOMPOSITION)
                .components(Gallium, 1, Carbon, 3, Hydrogen, 9)
                .build()
                .setFormula("Ga(CH3)3", true);

        GTQTMaterials.EthyleneDibromide = new Material.Builder(getMaterialsId(), gtqtcoreId("ethylene_dibromide"))
                .fluid()
                .color(0x4F1743)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 2, Hydrogen, 4, Bromine, 2)
                .build();

        GTQTMaterials.GrignardReagent = new Material.Builder(getMaterialsId(), gtqtcoreId("grignard_reagent"))
                .fluid()
                .color(0xA12AA1)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 1, Hydrogen, 3, Magnesium, 1, Bromine, 1)
                .build();

        GTQTMaterials.Dimethylcadmium = new Material.Builder(getMaterialsId(), gtqtcoreId("dimethylcadmium"))
                .fluid()
                .color(0x5C037F)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 2, Hydrogen, 6, Cadmium, 1)
                .build()
                .setFormula("(CH3)2Cd", true);

        GTQTMaterials.DiethylSuflide = new Material.Builder(getMaterialsId(), gtqtcoreId("diethyl_sulfide"))
                .fluid()
                .color(0xFF7E4B)
                .flags(DISABLE_DECOMPOSITION)
                .components(Ethylene, 2, Sulfur, 1)
                .build()
                .setFormula("(C2H5)2S", true);

        GTQTMaterials.Cycloparaphenylene = new Material.Builder(getMaterialsId(), gtqtcoreId("cycloparaphenylene")) //TODO "CPP" tooltip
                .fluid()
                .color(0x60545A)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 6, Hydrogen, 4)
                .build();

        GTQTMaterials.Indene = new Material.Builder(getMaterialsId(), gtqtcoreId("indene"))
                .fluid()
                .color(0x171429)
                .components(Carbon, 9, Hydrogen, 8)
                .build();

        GTQTMaterials.Indanone = new Material.Builder(getMaterialsId(), gtqtcoreId("indanone"))
                .dust()
                .color(0x2E1616).iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 9, Hydrogen, 8, Oxygen, 1)
                .build();

        GTQTMaterials.Truxene = new Material.Builder(getMaterialsId(), gtqtcoreId("truxene"))
                .fluid()
                .color(0x1A3336)
                .components(Carbon, 27, Hydrogen, 18)
                .build();

        GTQTMaterials.Bromomethane = new Material.Builder(getMaterialsId(), gtqtcoreId("bromomethane"))
                .gas()
                .color(0xC82C31)
                .components(Carbon, 1, Hydrogen, 3, Bromine, 1)
                .build();

        GTQTMaterials.BromoBromomethylNaphthalene = new Material.Builder(getMaterialsId(), gtqtcoreId("bromo_bromomethyl_naphthalene"))
                .fluid()
                .color(0x52122E)
                .components(Carbon, 11, Hydrogen, 8, Bromine, 2)
                .build();

        GTQTMaterials.Bromobutane = new Material.Builder(getMaterialsId(), gtqtcoreId("bromobutane"))
                .gas()
                .color(0xE6E8A2)
                .components(Butene, 1, GTQTMaterials.HydrobromicAcid, 1)
                .build()
                .setFormula("C4H9Br", true);

        GTQTMaterials.Butyllithium = new Material.Builder(getMaterialsId(), gtqtcoreId("butyllithium"))
                .fluid()
                .color(0xE683B6B)
                .components(Butene, 1, Hydrogen, 1, Lithium, 1)
                .build()
                .setFormula("C4H9Li", true);

        GTQTMaterials.PalladiumAcetate = new Material.Builder(getMaterialsId(), gtqtcoreId("palladium_acetate"))
                .dust()
                .color(0x693C2D).iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Palladium, 1, AceticAcid, 2)
                .build()
                .setFormula("Pd(CH3COOH)2", true);

        GTQTMaterials.GeodesicPolyarene = new Material.Builder(getMaterialsId(), gtqtcoreId("geodesic_polyarene"))
                .dust()
                .color(0x9E81A8).iconSet(MaterialIconSet.METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 60, Hydrogen, 30)
                .build();

        GTQTMaterials.Edot = new Material.Builder(getMaterialsId(), gtqtcoreId("edot"))
                .fluid()
                .color(0xB1FFD7)
                .components(Carbon, 6, Hydrogen, 6, Oxygen, 2, Sulfur, 1)
                .build();

        GTQTMaterials.Polystyrene = new Material.Builder(getMaterialsId(), gtqtcoreId("polystyrene"))
                .fluid()
                .color(0xE1C2C2)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 8, Hydrogen, 8)
                .build();

        GTQTMaterials.PolystyreneSulfonate = new Material.Builder(getMaterialsId(), gtqtcoreId("polystyrene_sulfonate"))
                .ingot().fluid()
                .color(0xE17C72)
                .flags(DISABLE_DECOMPOSITION, GENERATE_PLATE)
                .components(Carbon, 8, Hydrogen, 8, Sulfur, 1, Oxygen, 3)
                .build();

    }
}
