package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.GTValues;
import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.attribute.FluidAttributes;
import gregtech.api.unification.Elements;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.material.properties.BlastProperty;
import gregtech.api.unification.material.properties.ToolProperty;
import gregtech.api.util.GTUtility;
import keqing.gtqtcore.api.unification.GTQTElements;
import keqing.gtqtcore.api.unification.GTQTMaterials;

import static gregicality.multiblocks.api.unification.GCYMMaterials.IncoloyMA956;
import static gregtech.api.GTValues.*;
import static gregtech.api.fluids.attribute.FluidAttributes.ACID;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Tetranitromethane;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_SMALL_GEAR;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static gregtech.api.unification.material.properties.BlastProperty.GasTier.LOW;
import static gregtech.api.unification.material.properties.BlastProperty.GasTier.MID;
import static gregtech.api.util.GTUtility.gregtechId;
import static gregtechfoodoption.GTFOMaterialHandler.SodiumSulfate;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.material.info.GTQTMaterialIconSet.CUSTOM_MHCSM;

public class FirstDegreeMaterials {
    public FirstDegreeMaterials() {
    }

    private static int startId = 20000;
    private static final int END_ID = startId + 1000;

    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static void register() {
        SupercriticalSteam = new Material.Builder(getMaterialsId(), gregtechId("supercritical_steam"))
                .gas(new FluidBuilder().temperature(873).customStill())
                .color(0xC4C4C4)
                .components(Water, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();


        //  24074 Superheated Steam
        SuperheatedSteam = new Material.Builder(getMaterialsId(), gregtechId("superheated_steam"))
                .gas(new FluidBuilder().temperature(573).customStill())
                .color(0xC4C4C)
                .components(Water, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //
        getMaterialsId();

        GTQTMaterials.Pyrotheum = (new Material.Builder(getMaterialsId(), GTUtility.gregtechId("pyrotheum"))) .fluid().color(14601000).build();

        GTQTMaterials.StellarMaterialResidueA = (new Material.Builder(getMaterialsId(), GTUtility.gregtechId("stellar_material_residue_a"))).fluid().plasma().color(14601000).build().setFormula("ST-α", true);
        GTQTMaterials.StellarMaterialResidueB = (new Material.Builder(getMaterialsId(), GTUtility.gregtechId("stellar_material_residue_b"))).fluid().plasma().color(14600000).build().setFormula("ST-β", true);
        GTQTMaterials.StellarMaterialResidueC = (new Material.Builder(getMaterialsId(), GTUtility.gregtechId("stellar_material_residue_c"))).fluid().plasma().color(14639000).build().setFormula("ST-γ", true);
        GTQTMaterials.StellarMaterial = (new Material.Builder(getMaterialsId(), GTUtility.gregtechId("stellar_material"))).fluid().plasma().color(14638000).build().setFormula("ST-ST", true);

        GTQTMaterials.LightNaquadahFuel = (new Material.Builder(getMaterialsId(), GTUtility.gregtechId("light_naquadah_fuel"))).fluid().color(14638000).build().setFormula("NQ", true);
        GTQTMaterials.MediumNaquadahFuel = (new Material.Builder(getMaterialsId(), GTUtility.gregtechId("medium_naquadah_fuel"))).fluid().color(14638000).build().setFormula("-NQ-", true);
        GTQTMaterials.HeavyNaquadahFuel = (new Material.Builder(getMaterialsId(), GTUtility.gregtechId("heavy_naquadah_fuel"))).fluid().color(14638000).build().setFormula("+NQ+", true);

        GTQTMaterials.MagnetoHydrodynamicallyConstrainedStarMatter = new Material.Builder(getMaterialsId(), gregtechId("magneto_hydrodynamically_constrained_star_matter"))
                .ingot()
                .liquid(new FluidBuilder().temperature(6000000))
                .iconSet(CUSTOM_MHCSM)
                .flags(NO_SMELTING, GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR)
                .build();

        GTQTMaterials.DenseHydrazineMixtureFuel = new Material.Builder(getMaterialsId(), gregtechId("dense_hydrazine_mixture_fuel"))
                .fluid()
                .color(0x912565)
                .components(Dimethylhydrazine, 1, Methanol, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //  24324 Highly Purified Coal Tar
        HighlyPurifiedCoalTar = new Material.Builder(getMaterialsId(), gregtechId("highly_purified_coal_tar"))
                .fluid()
                .color(0x7F811D)
                .components(CoalTar, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //  24325 RP-1 Rocket Fuel
        GTQTMaterials.RP1RocketFuel = new Material.Builder(getMaterialsId(), gregtechId("rp_1_rocket_fuel"))
                .fluid()
                .color(0xFB2A08)
                .components(HighlyPurifiedCoalTar, 1, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //  24326 Methylhydrazine
        Methylhydrazine = new Material.Builder(getMaterialsId(), gregtechId("methylhydrazine"))
                .fluid()
                .color(0x321452)
                .components(Carbon, 1, Hydrogen, 6, Nitrogen, 2)
                .build();
        //  24327 Methylhydrazine Nitrate Rocket Fuel
        GTQTMaterials.MethylhydrazineNitrateRocketFuel = new Material.Builder(getMaterialsId(), gregtechId("methylhydrazine_nitrate_rocket_fuel"))
                .fluid()
                .color(0x607186)
                .components(Methylhydrazine, 1, Tetranitromethane, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        GTQTMaterials.Alumite = new Material.Builder(getMaterialsId(), gregtechId("alumite"))
                .fluid()
                .ingot()
                .color(0x607186)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        GTQTMaterials.Alubrass = new Material.Builder(getMaterialsId(), gregtechId("alubrass"))
                .fluid()
                .ingot()
                .color(0x321452)
                .flags(DISABLE_DECOMPOSITION)
                .components(Aluminium, 3, Copper, 1)
                .build();
        //破乳剂
        GTQTMaterials.Demulsifier = new Material.Builder(getMaterialsId(), gregtechId("demulsifier"))
                .fluid()
                .color(0xC6E2FF)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //盐水
        GTQTMaterials.SeaWater = new Material.Builder(getMaterialsId(), gregtechId("sea_water"))
                .fluid()
                .color(0xB2DFEE)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //含杂预处理原油
        GTQTMaterials.PreTreatedCrudeOilContainingImpurities = new Material.Builder(getMaterialsId(), gregtechId("pre_treated_crude_oil_containing_impurities"))
                .fluid()
                .color(0x262626)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //预处理原油
        GTQTMaterials.PreTreatedCrudeOil = new Material.Builder(getMaterialsId(), gregtechId("pre_treated_crude_oil"))
                .fluid()
                .color(0x2F4F4F)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //环氧丙烷
        GTQTMaterials.PropyleneOxide = new Material.Builder(getMaterialsId(), gregtechId("propylene_oxide"))
                .fluid()
                .color(0xBDB76B)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //沥青
        GTQTMaterials.Asphalt = new Material.Builder(getMaterialsId(), gregtechId("asphalt"))
                .fluid()
                .color(0x282828)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //蜡油
        GTQTMaterials.WaxOil = new Material.Builder(getMaterialsId(), gregtechId("wax_oil"))
                .fluid()
                .color(0x8B8B00)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //水煤气
        GTQTMaterials.WaterGas = new Material.Builder(getMaterialsId(), gregtechId("water_gas"))
                .fluid()
                .color(0xA9A9A9)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //常压渣油
        GTQTMaterials.AtmosphericResidue = new Material.Builder(getMaterialsId(), gregtechId("atmospheric_residue"))
                .fluid()
                .color(0x8B1A1A)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //减压渣油
        GTQTMaterials.VacuumResidue = new Material.Builder(getMaterialsId(), gregtechId("vacuum_residue"))
                .fluid()
                .color(0xCD4F39)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //防爆剂
        GTQTMaterials.MTBE = new Material.Builder(getMaterialsId(), gregtechId("mtbe"))
                .fluid()
                .color(0xC0FF3E)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //轻柴油
        GTQTMaterials.DieselLight = new Material.Builder(getMaterialsId(), gregtechId("diesel_light"))
                .fluid()
                .color(0x8B5A00)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //重柴油
        GTQTMaterials.DieselHeavy = new Material.Builder(getMaterialsId(), gregtechId("diesel_heavy"))
                .fluid()
                .color(0x8B5742)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //含硫轻柴油
        GTQTMaterials.SDieselLight = new Material.Builder(getMaterialsId(), gregtechId("sdiesel_light"))
                .fluid()
                .color(0x8B4C39)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //含硫重柴油
        GTQTMaterials.SDieselHeavy = new Material.Builder(getMaterialsId(), gregtechId("sdiesel_heavy"))
                .fluid()
                .color(0x8B4513)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //粗柴油
        GTQTMaterials.GasOil = new Material.Builder(getMaterialsId(), gregtechId("gas_oil"))
                .fluid()
                .color(0x8B7355)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //直馏汽油
        GTQTMaterials.Distilledgasoline = new Material.Builder(getMaterialsId(), gregtechId("distilledgasoline"))
                .fluid()
                .color(0x8B7355)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //油浆
        GTQTMaterials.OilSlurry = new Material.Builder(getMaterialsId(), gregtechId("oil_slurry"))
                .fluid()
                .color(0x8B7355)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //油气
        GTQTMaterials.OilGas = new Material.Builder(getMaterialsId(), gregtechId("oil_gas"))
                .fluid()
                .color(0x8B7355)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //氯化锌
        GTQTMaterials.ZincCl = new Material.Builder(getMaterialsId(), gregtechId("zinc_cl"))
                .dust()
                .color(0xB2DFEE)
                .components(Zinc, 1, Chlorine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //氯乙醇
        GTQTMaterials.EthyleneChlorohydrin = new Material.Builder(getMaterialsId(), gregtechId("ethylene_chlorohydrin"))
                .fluid()
                .color(0xB2DFEE)
                .components(Carbon, 2, Hydrogen, 6, Oxygen, 1, Chlorine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //硫酸铵
        GTQTMaterials.AmmoniumSulfate = new Material.Builder(getMaterialsId(), gregtechId("ammonium_sulfate"))
                .fluid()
                .color(0xB2DFEE)
                .components(Nitrogen, 2, Hydrogen, 8, Sulfur, 1, Oxygen, 4)
                .build();

        //轻度加氢裂解轻柴油
        GTQTMaterials.LightlyHydroCrackedDieselLight = new Material.Builder(getMaterialsId(), gregtechId("LightlyHydroCrackedDieselLight"))
                .fluid()
                .color(0xFFC125)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //轻度蒸汽裂解轻柴油
        GTQTMaterials.LightlySteamCrackedDieselLight = new Material.Builder(getMaterialsId(), gregtechId("LightlySteamCrackedDieselLight"))
                .fluid()
                .color(0xFFB90F)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //中度加氢裂解轻柴油
        GTQTMaterials.SeverelyHydroCrackedDieselLight = new Material.Builder(getMaterialsId(), gregtechId("SeverelyHydroCrackedDieselLight"))
                .fluid()
                .color(0xFFA54F)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //中度蒸汽裂解轻柴油
        GTQTMaterials.SeverelySteamCrackedDieselLight = new Material.Builder(getMaterialsId(), gregtechId("SeverelySteamCrackedDieselLight"))
                .fluid()
                .color(0xFF8C00)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //轻度加氢裂解重柴油
        GTQTMaterials.LightlyHydroCrackedDieselHeavy = new Material.Builder(getMaterialsId(), gregtechId("LightlyHydroCrackedDieselHeavy"))
                .fluid()
                .color(0xFF7F50)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //轻度蒸汽裂解重柴油
        GTQTMaterials.LightlySteamCrackedDieselHeavy = new Material.Builder(getMaterialsId(), gregtechId("LightlySteamCrackedDieselHeavy"))
                .fluid()
                .color(0xFF7F24)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //中度加氢裂解重柴油
        GTQTMaterials.SeverelyHydroCrackedDieselHeavy = new Material.Builder(getMaterialsId(), gregtechId("SeverelyHydroCrackedDieselHeavy"))
                .fluid()
                .color(0xFF7F00)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //中度蒸汽裂解重柴油
        GTQTMaterials.SeverelySteamCrackedDieselHeavy = new Material.Builder(getMaterialsId(), gregtechId("SeverelySteamCrackedDieselHeavy"))
                .fluid()
                .color(0xFF7256)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //酚醛
        GTQTMaterials.Phenolic = new Material.Builder(getMaterialsId(), gregtechId("phenolic"))
                .fluid()
                .color(0xFF7256)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.Latex = new Material.Builder(getMaterialsId(), gregtechId("latex"))
                .fluid()
                .color(0xFFFADA)
                .build();

        GTQTMaterials.Polysilicon = new Material.Builder(getMaterialsId(), gregtechId("polysilicon"))
                .ingot()
                .color(0x3C3C50).iconSet(METALLIC)
                .flags(GENERATE_FOIL)
                .element(Elements.Si)
                .blast(1800,LOW) // no gas tier for silicon
                .build();

        GTQTMaterials.CopperCl = new Material.Builder(getMaterialsId(), gregtechId("copper_cl"))
                .ingot()
                .color(0x8B4C39)
                .iconSet(METALLIC)
                .flags(GENERATE_FOIL)
                .components(Copper, 1, Chlorine, 2)
                .blast(2273)
                .build();

        //聚乙烯醇肉桂酸酯
        GTQTMaterials.Vinylcinnamate = new Material.Builder(getMaterialsId(), gregtechId("vinylcinnamate"))
                .fluid()
                .color(0xFF8C00)
                .iconSet(SHINY)
                .build();

        //感光树脂-聚乙烯醇肉桂酸酯

        //增感剂
        //光刻胶溶剂

        //xMT分子光刻
        GTQTMaterials.Xmt = new Material.Builder(getMaterialsId(), gregtechId("xmt"))
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
        GTQTMaterials.Zrbtmst = new Material.Builder(getMaterialsId(), gregtechId("zrbtmst"))
                .fluid().dust()
                .color(0xB2DFEE).iconSet(METALLIC)
                .flags(GENERATE_FOIL)
                .build();
        //氧化锆杂化物
        //（2,4-双（三氯甲基）-6-（4-甲氧基苯乙烯基）-1,3,5-三嗪）
        //（ZrO2-BTMST）

        //纳米钛酸钡
        GTQTMaterials.NanometerBariumTitanate = new Material.Builder(getMaterialsId(), gregtechId("nanometer_barium_titanate"))
                .ingot()
                .color(0x8B6914).iconSet(METALLIC)
                .components(Barium, 1, Titanium, 1, Oxygen, 3)
                .flags(DISABLE_DECOMPOSITION)
                .blast(4500,MID)
                .flags(GENERATE_PLATE, GENERATE_DENSE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR,GENERATE_FOIL)
                .build();

        //海绵钛
        GTQTMaterials.Htitanate = new Material.Builder(getMaterialsId(), gregtechId("htitanate"))
                .ingot()
                .color(0x8B6914).iconSet(METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .components(Titanium, 1)
                .build();

        GTQTMaterials.Fluix = new Material.Builder(getMaterialsId(), gregtechId("fluix"))
                .gem(1).ore()
                .color(0x7D26CD).iconSet(SHINY)
                .flags(GENERATE_PLATE, CRYSTALLIZABLE)
                .components(Silicon, 1, Oxygen, 2)
                .build();

        //eio
        GTQTMaterials.RedstoneAlloy = new Material.Builder(getMaterialsId(), gregtechId("redstone_alloy"))
                .ingot().fluid()
                .color(0x943423).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(Redstone, 1, Hydrogen, 1, Oxygen, 1)
                .blast(2700, LOW)
                .fluidPipeProperties(500, 400, true, true, true, false)
                .cableProperties(32, 1, 2)
                .build();

        GTQTMaterials.PulsatingIron = new Material.Builder(getMaterialsId(), gregtechId("pulsating_iron"))
                .ingot().fluid()
                .color(0x4b915b).iconSet(SHINY)
                .fluidPipeProperties(500, 400, true, true, true, false)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(EnderPearl, 1, Iron, 1, RedstoneAlloy, 1)
                .blast(2700, LOW)
                .build();

        GTQTMaterials.ConductiveIron = new Material.Builder(getMaterialsId(), gregtechId("conductive_iron"))
                .ingot().fluid()
                .color(0xb89791).iconSet(SHINY)
                .fluidPipeProperties(500, 600, true, true, true, false)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(Silver, 1, Iron, 1, RedstoneAlloy, 1)
                .blast(2700, LOW)
                .build();

        GTQTMaterials.EnergeticAlloy = new Material.Builder(getMaterialsId(), gregtechId("energetic_alloy"))
                .ingot().fluid()
                .color(0xd89045).iconSet(SHINY)
                .fluidPipeProperties(500, 800, true, true, true, false)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(BlackSteel, 1, Gold, 1, ConductiveIron, 1)
                .blast(2700, LOW)
                .cableProperties(512, 2, 4)
                .build();

        GTQTMaterials.VibrantAlloy = new Material.Builder(getMaterialsId(), gregtechId("vibrant_alloy"))
                .ingot().fluid()
                .color(0x859f2d).iconSet(SHINY)
                .fluidPipeProperties(500, 1200, true, true, true, false)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(EnderEye, 1, EnergeticAlloy, 1, Chromite, 1)
                .blast(2700, LOW)
                .cableProperties(2048, 4, 4)
                .build();

        GTQTMaterials.Soularium = new Material.Builder(getMaterialsId(), gregtechId("soularium"))
                .ingot().fluid()
                .color(0x372719).iconSet(SHINY)
                .fluidPipeProperties(500, 1200, true, true, true, false)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(Gold, 1, Carbon, 1)
                .blast(2700, LOW)
                .build();

        GTQTMaterials.ElectricalSteel = new Material.Builder(getMaterialsId(), gregtechId("electrical_steel"))
                .ingot().fluid()
                .color(0x9d9d9d).iconSet(SHINY)
                .fluidPipeProperties(500, 900, true, true, true, false)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(Steel, 1, Coal, 1, Silicon, 1)
                .blast(2700, LOW)
                .rotorStats(9.0f, 5.0f, 3000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 28000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .cableProperties(128, 2, 2)
                .build();

        GTQTMaterials.DarkSteel = new Material.Builder(getMaterialsId(), gregtechId("dark_steel"))
                .ingot().fluid()
                .color(0x2f292f).iconSet(SHINY)
                .fluidPipeProperties(500, 600, true, true, true, false)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(ElectricalSteel, 1, Coal, 1, Obsidian, 1)
                .rotorStats(12.0f, 6.0f, 4500)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 32000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .blast(3600, MID)
                .build();

        GTQTMaterials.EndSteel = new Material.Builder(getMaterialsId(), gregtechId("end_steel"))
                .ingot().fluid()
                .color(0xbdb88c).iconSet(SHINY)
                .fluidPipeProperties(500, 2400, true, true, true, false)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(DarkSteel, 1, Endstone, 1, TungstenSteel, 1)
                .rotorStats(15.0f, 7.0f, 6000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 36000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .blast(4500, MID)
                .build();

        GTQTMaterials.CrystallineAlloy = new Material.Builder(getMaterialsId(), gregtechId("crystalline_alloy"))
                .ingot().fluid()
                .color(0x8FE3F7).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(PulsatingIron, 1, Diamond, 1, Emerald, 1, Gold, 1)
                .blast(4500, MID)
                .build();

        GTQTMaterials.MelodicAlloy = new Material.Builder(getMaterialsId(), gregtechId("melodic_alloy"))
                .ingot().fluid()
                .color(0xA877A8).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(EndSteel, 1, EnderEye, 1, Bismuth, 1)
                .fluidPipeProperties(500, 3600, true, true, true, false)
                .blast(5400, MID)
                .build();

        GTQTMaterials.StellarAlloy = new Material.Builder(getMaterialsId(), gregtechId("stellar_alloy"))
                .ingot().fluid()
                .color(0xCCCCCC).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(MelodicAlloy, 1, NetherStar, 1, Naquadah, 1)
                .fluidPipeProperties(500, 4500, true, true, true, false)
                .blast(7200, MID)
                .rotorStats(15.0f, 7.0f, 6000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 32000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .cableProperties(32768, 6, 8)
                .build();

        GTQTMaterials.CrystallinePinkSlime = new Material.Builder(getMaterialsId(), gregtechId("crystalline_pink_slime"))
                .ingot().fluid()
                .color(0xE79EDB).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(CrystallineAlloy, 1, Diamond, 1, RawRubber, 1)
                .blast(5000, MID)
                .fluidPipeProperties(500, 6000, true, true, true, false)
                .rotorStats(15.0f, 7.0f, 4000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 6000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .cableProperties(2048, 6, 2)
                .build();

        GTQTMaterials.EnergeticSilver = new Material.Builder(getMaterialsId(), gregtechId("energetic_silver"))
                .ingot().fluid()
                .color(0x598DB3).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(Silver, 1, ConductiveIron, 1, BlackSteel, 1)
                .blast(2700, MID)
                .rotorStats(15.0f, 7.0f, 2000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 8000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .cableProperties(512, 4, 2)
                .build();

        GTQTMaterials.VividAlloy = new Material.Builder(getMaterialsId(), gregtechId("vivid_alloy"))
                .ingot().fluid()
                .color(0x469BB1).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .components(Chromite, 1, EnergeticSilver, 1, EnderEye, 1)
                .blast(3000, MID)
                .rotorStats(15.0f, 7.0f, 4000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 12000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .cableProperties(2048, 4, 2)
                .build();

        GTQTMaterials.CrudeSteel = new Material.Builder(getMaterialsId(), gregtechId("crude_steel"))
                .ingot().fluid()
                .color(0x807C74).iconSet(ROUGH)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .flags(DISABLE_DECOMPOSITION)
                .build();


        GTQTMaterials.Ealuminium = new Material.Builder(getMaterialsId(), gregtechId("ealuminium"))
                .fluid().dust()
                .color(0xd89045).iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Aluminium, 1, Nitrogen, 3, Oxygen, 9)
                .build();

        //海带灰
        GTQTMaterials.Haidaihui = new Material.Builder(getMaterialsId(), gregtechId("haidaihui"))
                .dust()
                .color(0xd89045).iconSet(SHINY)
                .build();

        //萃取海带灰（四氯化碳处理后
        GTQTMaterials.Cuiquhaidaihui = new Material.Builder(getMaterialsId(), gregtechId("cuiquhaidaihui"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .build();
        //碘化钾
        GTQTMaterials.PotassiumIodide = new Material.Builder(getMaterialsId(), gregtechId("potassium_iodide"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .components(Potassium, 1, Iodine, 1)
                .build();
        //溴化钾
        GTQTMaterials.PotassiumBromide = new Material.Builder(getMaterialsId(), gregtechId("potassium_bromide"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .components(Potassium, 1, Bromine, 1)
                .build();
        //2-丁烯醛
        GTQTMaterials.Crotonaldehyde = new Material.Builder(getMaterialsId(), gregtechId("crotonaldehyde"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .components(Carbon, 4, Hydrogen, 8, Oxygen, 1)
                .build();

        //2-乙基（-2-）己烯醛
        GTQTMaterials.Ethylhexenal = new Material.Builder(getMaterialsId(), gregtechId("ethylhexenal"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .components(Carbon, 8, Hydrogen, 14, Oxygen, 1)
                .build();
        //草酸
        GTQTMaterials.EthanedioicAcid = new Material.Builder(getMaterialsId(), gregtechId("ethanedioic_acid"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .components(Hydrogen, 2, Carbon, 2, Oxygen, 4)
                .build();

        //五氧化二钒
        GTQTMaterials.VanadiumOxide = new Material.Builder(getMaterialsId(), gregtechId("vanadium_oxide"))
                .dust()
                .color(0xd89045).iconSet(SHINY)
                .components(Vanadium, 2, Oxygen, 5)
                .build();
        //SodiumSilicofluoride 氟硅酸钠
        GTQTMaterials.SodiumSilicofluoride = new Material.Builder(getMaterialsId(), gregtechId("sodium_silicofluoride"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .components(Sodium, 2, Silicon, 1, Fluorine, 6)
                .build();

        //Hexafluorosilicic acid 氟硅酸
        GTQTMaterials.HexafluorosilicicAcid = new Material.Builder(getMaterialsId(), gregtechId("hexafluorosilicic_acid"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .components(Hydrogen, 2, Silicon, 1, Fluorine, 6)
                .build();

        //独居石稀土浊溶液
        GTQTMaterials.MonaziteRareEarthTurbid = new Material.Builder(getMaterialsId(), gregtechId("monazite_rare_earth_turbid"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .build();

        //氧化铪-氧化锆混合物
        GTQTMaterials.HafniumOxideZirconiumOxideMixture = new Material.Builder(getMaterialsId(), gregtechId("hafnium_oxide_zirconium_oxid_mixture"))
                .dust()
                .color(0xFFB90F).iconSet(SHINY)
                .components(Hafnium, 1, Zirconium, 1, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //稀释独居石稀土泥浆
        GTQTMaterials.DiluteMonaziteRareEarthMud = new Material.Builder(getMaterialsId(), gregtechId("xishidujushixitu"))
                .fluid()
                .color(0xFFB6C1).iconSet(SHINY)
                .build();

        //氧化铪
        GTQTMaterials.HafniumOxide = new Material.Builder(getMaterialsId(), gregtechId("hafnium_oxide"))
                .dust()
                .color(0xd89045).iconSet(SHINY)
                .components(Hafnium, 1, Oxygen, 2)
                .build();

        //四氯化铪_锆
        GTQTMaterials.HafniumZirconiumTetrachloride = new Material.Builder(getMaterialsId(), gregtechId("hafnium_zirconium_tetrachloride"))
                .fluid()
                .color(0xFFAEB9).iconSet(SHINY)
                .components(Hafnium, 1, Zirconium, 1, Chlorine, 8)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //低纯
        GTQTMaterials.LowPurityHafniumZirconiumResidue = new Material.Builder(getMaterialsId(), gregtechId("low_purity_hafnium_zirconium_residue"))
                .dust()
                .color(0xFF8C00).iconSet(SHINY)
                .components(Hafnium, 1, Zirconium, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //低纯碘化
        GTQTMaterials.LowPurityHafniumZirconiumIodideMixture = new Material.Builder(getMaterialsId(), gregtechId("low_purity_hafnium_zirconium_iodide_mixture"))
                .fluid()
                .color(0xFF83FA).iconSet(SHINY)
                .components(Hafnium, 1, Zirconium, 1, Iodine, 8)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //硫酸独居石
        GTQTMaterials.SulfuricAcidMonazite = new Material.Builder(getMaterialsId(), gregtechId("sulfuric_acid_monazite"))
                .fluid()
                .color(0xFF82AB).iconSet(SHINY)
                .build();

        //独居石稀土滤渣粉
        GTQTMaterials.MonaziteRareEarthFilterResidue = new Material.Builder(getMaterialsId(), gregtechId("monazite_rare_earth_filter_residue"))
                .dust()
                .color(0xFF8247).iconSet(SHINY)
                .build();

        //磷酸钍滤饼
        GTQTMaterials.ThoriumPhosphateFilterCake = new Material.Builder(getMaterialsId(), gregtechId("thorium_phosphate_filter_cake"))
                .dust()
                .color(0xFF6EB4).iconSet(SHINY)
                .build();

        //磷酸钍精粉
        GTQTMaterials.ThoriumPhosphateConcentrate = new Material.Builder(getMaterialsId(), gregtechId("thorium_phosphate_concentrate"))
                .dust()
                .color(0xFF6A6A).iconSet(SHINY)
                .build();

        //中和独居石稀土滤渣粉
        GTQTMaterials.NeutralizationMonaziteRareEarthFilterResidue = new Material.Builder(getMaterialsId(), gregtechId("zhonghedujushixitulvzha"))
                .dust()
                .color(0xFF69B4).iconSet(SHINY)
                .build();

        //浓缩独居石稀土氢氧化物粉
        GTQTMaterials.ConcentratedMonaziteRareEarthHydroxide = new Material.Builder(getMaterialsId(), gregtechId("concentrated_monazite_rare_earth_hydroxide"))
                .dust()
                .color(0xFF6347).iconSet(SHINY)
                .build();

        //铀滤渣粉
        GTQTMaterials.UraniumFilterResidue = new Material.Builder(getMaterialsId(), gregtechId("uranium_filter_residue"))
                .dust()
                .color(0xFF3030).iconSet(SHINY)
                .build();

        //干燥浓缩硝酸独居石稀土
        GTQTMaterials.DryConcentratedNitrateMonaziteRareEarth = new Material.Builder(getMaterialsId(), gregtechId("dry_concentrated_nitrate_monazite_rare_earth"))
                .dust()
                .color(0xFF1493).iconSet(SHINY)
                .build();

        //独居石罕土沉淀粉
        GTQTMaterials.SolitaryStoneRareSoilSedimentation = new Material.Builder(getMaterialsId(), gregtechId("solitary_stone_rare_soil_sedimentation"))
                .dust()
                .color(0xFF00FF).iconSet(SHINY)
                .build();

        //异质卤化独居石稀土混合物
        GTQTMaterials.HeterogeneousHalogenatedMonaziteRareEarthMixture = new Material.Builder(getMaterialsId(), gregtechId("heterogeneous_halogenated_monazite_rare_earth_mixture"))
                .dust()
                .color(0xFF0000).iconSet(SHINY)
                .build();

        //饱和独居石稀土
        GTQTMaterials.SaturatedMonaziteDopedWithRareEarth = new Material.Builder(getMaterialsId(), gregtechId("saturated_monazite_doped_with_rare_earth"))
                .dust()
                .color(0xd89045).iconSet(SHINY)
                .build();

        //钐精
        GTQTMaterials.SamariumEssence = new Material.Builder(getMaterialsId(), gregtechId("samarium_essence"))
                .dust()
                .color(0xF5DEB3).iconSet(SHINY)
                .build();

        //氯化铈
        GTQTMaterials.CeriumChloride = new Material.Builder(getMaterialsId(), gregtechId("cerium_chloride"))
                .dust()
                .color(0xF4F4F4).iconSet(SHINY)
                .components(Cerium, 1, Chlorine, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //草酸铈
        GTQTMaterials.CeriumOxalate = new Material.Builder(getMaterialsId(), gregtechId("cerium_oxalate"))
                .dust()
                .color(0xF4A460).iconSet(SHINY)
                .components(Cerium, 1, Hydrogen, 2, Carbon, 2, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //Cerium
        GTQTMaterials.CeriumOxide = new Material.Builder(getMaterialsId(), gregtechId("cerium_oxide"))
                .dust()
                .color(0xF2F2F2).iconSet(SHINY)
                .components(Cerium, 2, Oxygen, 3)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //氟碳镧铈稀土浊
        GTQTMaterials.FluorocarbonLanthanumCeriumRareEarthTurbidity = new Material.Builder(getMaterialsId(), gregtechId("fluorocarbon_lanthanum_cerium_rare_earth_turbidity"))
                .fluid()
                .color(0x8B7500).iconSet(SHINY)
                .build();

        //蒸汽裂化氟碳镧铈泥浆
        GTQTMaterials.SteamCrackingFluorocarbonLanthanumCeriumSlurry = new Material.Builder(getMaterialsId(), gregtechId("steam_cracking_fluorocarbon_lanthanum_cerium_slurry"))
                .fluid()
                .color(0x8B7355).iconSet(SHINY)
                .build();

        //调制氟碳镧铈泥浆
        GTQTMaterials.ModulationFluorocarbonLanthanumCeriumSlurry = new Material.Builder(getMaterialsId(), gregtechId("modulation_fluorocarbon_lanthanum_cerium_slurry"))
                .fluid()
                .color(0x8B6969).iconSet(SHINY)
                .build();

        //过滤氟碳镧铈泥浆
        GTQTMaterials.FilterFluorocarbonLanthanumCeriumSlurry = new Material.Builder(getMaterialsId(), gregtechId("filter_fluorocarbon_lanthanum_cerium_slurry"))
                .dust()
                .color(0x8B6914).iconSet(SHINY)
                .build();

        //氟碳镧铈稀土氧化物粉
        GTQTMaterials.FluorocarbonLanthanumCeriumRareEarthOxide = new Material.Builder(getMaterialsId(), gregtechId("fluorocarbon_lanthanum_cerium_rare_earth_oxide"))
                .dust()
                .color(0x8B668B).iconSet(SHINY)
                .build();

        //酸浸氟碳镧铈稀土氧化物粉
        GTQTMaterials.AcidFluorocarbonLanthanumCeriumRareEarthOxide = new Material.Builder(getMaterialsId(), gregtechId("acid_fluorocarbon_lanthanum_cerium_rare_earth_oxide"))
                .fluid()
                .color(0x8B6508).iconSet(SHINY)
                .build();

        //焙烧稀土氧化物粉
        GTQTMaterials.RoastedRareEarthOxide = new Material.Builder(getMaterialsId(), gregtechId("roasted_rare_earth_oxide"))
                .dust()
                .color(0x8B636C).iconSet(SHINY)
                .build();

        //氧化铈稀土氧化物粉
        GTQTMaterials.CeriumOxideRareEarthOxide = new Material.Builder(getMaterialsId(), gregtechId("cerium_oxide_rare_earth_oxide"))
                .fluid()
                .color(0x8B5F65).iconSet(SHINY)
                .build();

        //氟碳镧铈罕土氧化物粉
        GTQTMaterials.FluorocarbonLanthanumCeriumRareSoilOxide = new Material.Builder(getMaterialsId(), gregtechId("fluorocarbon_lanthanum_cerium_rare_soil_oxide"))
                .dust()
                .color(0x8B5A2B).iconSet(SHINY)
                .build();

        //氟碳镧铈罕土氧化物悬浊液
        GTQTMaterials.FluorocarbonLanthanumCeriumRareSoilOxideSuspension = new Material.Builder(getMaterialsId(), gregtechId("fluorocarbon_lanthanum_cerium_rare_soil_oxide_suspension"))
                .fluid()
                .color(0x8B5A00).iconSet(SHINY)
                .build();

        //钐稀土精粉
        GTQTMaterials.SamariumRareEarth = new Material.Builder(getMaterialsId(), gregtechId("samarium_rare_earth"))
                .dust()
                .color(0x8B5742).iconSet(SHINY)
                .build();

        //氟化钐稀土精粉
        GTQTMaterials.SamariumFluorideRareEarthRefined = new Material.Builder(getMaterialsId(), gregtechId("samarium_fluoride_rare_earth_refined"))
                .fluid()
                .color(0x8B4C39).iconSet(SHINY)
                .build();

        //钐-铽混合物粉
        GTQTMaterials.SamariumTerbiumMixture = new Material.Builder(getMaterialsId(), gregtechId("shantui"))
                .fluid()
                .color(0x8B4789).iconSet(SHINY)
                .build();

        GTQTMaterials.CrudeRareEarthTurbidSolution = new Material.Builder(getMaterialsId(), gregtechId("crude_rare_earth_turbid_solution"))
                .fluid()
                .color(0x9C5C6B)
                .iconSet(DULL)
                .build();

        GTQTMaterials.NitratedRareEarthTurbidSolution = new Material.Builder(getMaterialsId(), gregtechId("nitrated_rare_earth_turbid_solution"))
                .fluid()
                .color(0x754550)
                .iconSet(DULL)
                .build();
        //  25254 Rare Earth Hydroxides Solution
        GTQTMaterials.RareEarthHydroxidesSolution = new Material.Builder(getMaterialsId(), gregtechId("rare_earth_hydroxides_solution"))
                .fluid()
                .color(0x434327)
                .flags(DISABLE_DECOMPOSITION)
                .components(RareEarth, 1, Oxygen, 1, Hydrogen, 1, Water, 1)
                .build();
        //  25255 Rare Earth Chlorides Slurry
        GTQTMaterials.RareEarthChloridesSlurry = new Material.Builder(getMaterialsId(), gregtechId("rare_earth_chlorides_slurry"))
                .dust()
                .color(0x838367)
                .iconSet(ROUGH)
                .flags(DISABLE_DECOMPOSITION)
                .components(RareEarth, 1, Chlorine, 1, Water, 1)
                .build();
        //  25256 Low-purity Rare Earth Chlorides Solution
        GTQTMaterials.LowPurityRareEarthChloridesSolution = new Material.Builder(getMaterialsId(), gregtechId("low_purity_rare_earth_chlorides_solution"))
                .fluid()
                .color(0x838333)
                .iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION)
                .components(RareEarth, 1, Chlorine, 1, Water, 2)
                .build();
        //  25257 Roughly Purified Rare Earth Chlorides Solution
        GTQTMaterials.RoughlyPurifiedRareEarthChloridesSolution = new Material.Builder(getMaterialsId(), gregtechId("roughly_purified_rare_earth_chlorides_solution"))
                .fluid()
                .color(0xA2A27F)
                .iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION)
                .components(LowPurityRareEarthChloridesSolution, 4, AquaRegia, 2)
                .build();
        //  25258 High Purity Rare Earth Chlorides Slurry
        GTQTMaterials.HighPurityRareEarthChloridesSlurry = new Material.Builder(getMaterialsId(), gregtechId("high_purity_rare_earth_chlorides_slurry"))
                .dust()
                .color(0x838367)
                .iconSet(ROUGH)
                .flags(DISABLE_DECOMPOSITION)
                .components(RareEarth, 1, Chlorine, 1, Water, 1)
                .build();
        //  25259 High Purity Rare Earth Chlorides Solution
        GTQTMaterials.HighPurityRareEarthChloridesSolution = new Material.Builder(getMaterialsId(), gregtechId("high_purity_rare_earth_chlorides_solution"))
                .fluid()
                .color(0x838367)
                .flags(DISABLE_DECOMPOSITION)
                .components(RareEarth, 1, Chlorine, 1, Water, 1)
                .build();
        //  25260 Low Purity Rare Earth Chlorides Slag
        GTQTMaterials.LowPurityRareEarthChloridesSlag = new Material.Builder(getMaterialsId(), gregtechId("low_purity_rare_earth_chlorides_slag"))
                .dust()
                .color(0x62624D)
                .iconSet(DULL)
                .build();

        //  24147 Barium Carbonate
        GTQTMaterials.BariumCarbonate = new Material.Builder(getMaterialsId(), gregtechId("barium_carbonate"))
                .dust()
                .color(0x425A73)
                .iconSet(ROUGH)
                .components(Barium, 1, Carbon, 1, Oxygen, 3)
                .build();

        //  24050 Palladium on Carbon
        GTQTMaterials.PalladiumOnCarbon = new Material.Builder(getMaterialsId(), gregtechId("palladium_on_carbon"))
                .dust()
                .color(0x480104)
                .iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION)
                .components(Palladium, 1, Carbon, 1)
                .build();

        //  24204 Au-Pd-C Catalyst
        GTQTMaterials.AuPdCCatalyst = new Material.Builder(getMaterialsId(), gregtechId("au_pd_c_catalyst"))
                .dust()
                .color(0xB7B305)
                .iconSet(SHINY)
                .components(Gold, 1, PalladiumOnCarbon, 1)
                .build();

        //  25106 Dibromomethylbenzene
        GTQTMaterials.Dibromomethylbenzene = new Material.Builder(getMaterialsId(), gregtechId("dibromomethylbenzene"))
                .fluid()
                .color(0x9F4839)
                .components(Carbon, 7, Hydrogen, 6, Bromine, 2)
                .build();

        //  25105 Terephthalaldehyde
        GTQTMaterials.Terephthalaldehyde = new Material.Builder(getMaterialsId(), gregtechId("terephthalaldehyde"))
                .dust()
                .color(0x567C2D)
                .iconSet(ROUGH)
                .components(Carbon, 8, Hydrogen, 6, Oxygen, 2)
                .build();

        //  25107 Isochloropropane
        getMaterialsId();

        //  24205 Sodium Oxide
        GTQTMaterials.SodiumOxide = new Material.Builder(getMaterialsId(), gregtechId("sodium_oxide"))
                .dust()
                .color(0x2C96FC)
                .iconSet(BRIGHT)
                .components(Sodium, 2, Oxygen, 1)
                .build();

        //  25108 Dinitrodipropanyloxybenzene
        GTQTMaterials.Dinitrodipropanyloxybenzene = new Material.Builder(getMaterialsId(), gregtechId("dinitrodipropanyloxybenzene"))
                .fluid()
                .color(0x9FAD1D)
                .components(Carbon, 12, Hydrogen, 16, Oxygen, 6, Nitrogen, 2)
                .build()
                .setFormula("C12H16O2(NO2)2", true);

        //  25104 Pre Zylon
        GTQTMaterials.PreZylon = new Material.Builder(getMaterialsId(), gregtechId("pre_zylon"))
                .dust()
                .color(0x623250)
                .iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION, NO_SMASHING, NO_SMELTING)
                .components(Carbon, 20, Hydrogen, 22, Nitrogen, 2, Oxygen, 2)
                .build();

        //  25103 Zylon
        GTQTMaterials.Zylon = new Material.Builder(getMaterialsId(), gregtechId("zylon"))
                .polymer().fluid()
                .color(0xFFE000)
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION, NO_SMASHING, NO_SMELTING, GENERATE_PLATE, GENERATE_FOIL)
                .components(Carbon, 14, Hydrogen, 6, Nitrogen, 2, Oxygen, 2)
                .build();


        //  24092 Hexafluoride Enriched Naquadah Solution
        GTQTMaterials.HexafluorideEnrichedNaquadahSolution = new Material.Builder(getMaterialsId(), gregtechId("hexafluoride_enriched_naquadah_solution"))
                .fluid()
                .color(0x868D7F)
                .components(NaquadahEnriched, 1, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //  24093 Xenon Hexafluoro Enriched Naquadate
        GTQTMaterials.XenonHexafluoroEnrichedNaquadate = new Material.Builder(getMaterialsId(), gregtechId("xenon_hexafluoro_enriched_naquadate"))
                .fluid()
                .color(0x255A55)
                .components(Xenon, 1, NaquadahEnriched, 1, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //  24094 Enriched Naquadah Residue Solution
        GTQTMaterials.EnrichedNaquadahResidueSolution = new Material.Builder(getMaterialsId(), gregtechId("enriched_naquadah_residue_solution"))
                .fluid()
                .color(0x868D7F)
                .iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("XeAuSbKeF6S2?");
        //  24095 Xenoauric Fluoroantimonic Acid
        GTQTMaterials.XenoauricFluoroantimonicAcid = new Material.Builder(getMaterialsId(), gregtechId("xenoauric_fluoroantimonic_acid"))
                .color(0xE0BD74)
                .fluid()
                .components(Xenon, 1, Gold, 1, Antimony, 1, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //  24096 Gold Chloride
        GTQTMaterials.GoldChloride = new Material.Builder(getMaterialsId(), gregtechId("gold_chloride"))
                .fluid()
                .color(0xCCCC66)
                .components(Gold, 2, Chlorine, 6)
                .build();
        //  24097 Bromine Trifluoride
        GTQTMaterials.BromineTrifluoride = new Material.Builder(getMaterialsId(), gregtechId("bromine_trifluoride"))
                .fluid()
                .color(0xA88E57)
                .components(Bromine, 1, Fluorine, 3)
                .build();
        //  24098 Gold Trifluoride
        GTQTMaterials.GoldTrifluoride = new Material.Builder(getMaterialsId(), gregtechId("gold_trifluoride"))
                .dust()
                .color(0xE8C478)
                .iconSet(BRIGHT)
                .components(Gold, 1, Fluorine, 3)
                .build();
        //  24099 Naquadria Caesiumfluoride
        GTQTMaterials.NaquadriaCaesiumfluoride = new Material.Builder(getMaterialsId(), gregtechId("naquadria_caesiumfluoride"))
                .fluid()
                .color(0xAAEB69)
                .components(Naquadria, 1, Fluorine, 3, Caesium, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("*Nq*F2CsF", true);
        //  24100 Acidic Naquadria Caesiumfluoride
        GTQTMaterials.AcidicNaquadriaCaesiumfluoride = new Material.Builder(getMaterialsId(), gregtechId("acidic_naquadria_caesiumfluoride"))
                .fluid()
                .color(0x75EB00)
                .components(Naquadria, 1, Fluorine, 3, Caesium, 1, Sulfur, 2, Oxygen, 8)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("*Nq*F2CsF(SO4)2", true);
        //  24101 Nitrosonium Octafluoroxenate
        GTQTMaterials.NitrosoniumOctafluoroxenate = new Material.Builder(getMaterialsId(), gregtechId("nitrosonium_octafluoroxenate"))
                .fluid()
                .color(0x953D9F)
                .components(NitrogenDioxide, 2, Xenon, 1, Fluorine, 8)
                .build()
                .setFormula("(NO2)2XeF8", true);
        //  24102 Naquadria Caesium Xenonnonfluoride
        GTQTMaterials.NaquadriaCaesiumXenonnonfluoride = new Material.Builder(getMaterialsId(), gregtechId("naquadria_caesium_xenonnonfluoride"))
                .fluid()
                .color(0x54C248)
                .components(Naquadria, 1, Caesium, 1, Xenon, 1, Fluorine, 9)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //  24103 Radon Naquadria Octafluoride
        GTQTMaterials.RadonNaquadriaOctafluoride = new Material.Builder(getMaterialsId(), gregtechId("radon_naquadria_octafluoride"))
                .fluid()
                .color(0x85F378)
                .components(Radon, 1, Naquadria, 1, Fluorine, 8)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //  24104 Caesium Xenontrioxide Fluoride
        GTQTMaterials.CaesiumXenontrioxideFluoride = new Material.Builder(getMaterialsId(), gregtechId("caesium_xenontrioxide_fluoride"))
                .fluid()
                .color(0x5067D7)
                .flags(DISABLE_DECOMPOSITION)
                .components(Caesium, 1, Xenon, 1, Oxygen, 3, Fluorine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //  24105 Radon Trioxide
        GTQTMaterials.RadonTrioxide = new Material.Builder(getMaterialsId(), gregtechId("radon_trioxide"))
                .fluid()
                .color(0x9A6DD7)
                .components(Radon, 1, Oxygen, 3)
                .build();
        //  24106 Cesium Fluoride
        GTQTMaterials.CaesiumFluoride = new Material.Builder(getMaterialsId(), gregtechId("caesium_fluoride"))
                .fluid()
                .color(0xFF7A5F)
                .components(Caesium, 1, Fluorine, 1)
                .build();
        //  24107 Xenon Trioxide
        GTQTMaterials.XenonTrioxide = new Material.Builder(getMaterialsId(), gregtechId("xenon_trioxide"))
                .fluid()
                .color(0x359FC3)
                .components(Xenon, 1, Oxygen, 3)
                .build();
        //  24108 Hexafluoride Naquadria Solution
        GTQTMaterials.HexafluorideNaquadriaSolution = new Material.Builder(getMaterialsId(), gregtechId("hexafluoride_naquadria_solution"))
                .fluid()
                .color(0x25C213)
                .components(Naquadria, 1, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //  24109 Naquadria Residue Solution
        GTQTMaterials.NaquadriaResidueSolution = new Material.Builder(getMaterialsId(), gregtechId("naquadria_residue_solution"))
                .fluid()
                .color(0x25C213)
                .iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("InPS6?", true);
        //  24110 Radon Difluoride
        GTQTMaterials.RadonDifluoride = new Material.Builder(getMaterialsId(), gregtechId("radon_difluoride"))
                .fluid()
                .color(0x8B7EFF)
                .components(Radon, 1, Fluorine, 2)
                .build();
        //  25079 Nitryl Fluoride
        GTQTMaterials.NitrylFluoride = new Material.Builder(getMaterialsId(), gregtechId("nitryl_fluoride"))
                .fluid()
                .color(0x8B7EFF)
                .components(Nitrogen, 1, Oxygen, 2, Fluorine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24240 Ammonium Carbonate
        GTQTMaterials.AmmoniumCarbonate = new Material.Builder(getMaterialsId(), gregtechId("ammonium_carbonate"))
                .dust()
                .color(0x7C89D9)
                .components(Carbon, 1, Hydrogen, 8, Oxygen, 3, Nitrogen, 2)
                .build()
                .setFormula("(NH4)2CO3", true);

        //  24239 Ammonium Acetate
        GTQTMaterials.AmmoniumAcetate = new Material.Builder(getMaterialsId(), gregtechId("ammonium_acetate"))
                .dust()
                .color(0x646882)
                .components(Carbon, 2, Hydrogen, 7, Oxygen, 2, Nitrogen, 1)
                .build()
                .setFormula("NH4CH3CO2", true);

        //  25119 Acetamide
        GTQTMaterials.Acetamide = new Material.Builder(getMaterialsId(), gregtechId("acetamide"))
                .dust()
                .color(0x7D82A3)
                .iconSet(DULL)
                .components(Carbon, 2, Hydrogen, 5, Nitrogen, 1, Oxygen, 1)
                .build()
                .setFormula("CH3CONH2", true);

        //  25118 Acetonitrile
        GTQTMaterials.Acetonitrile = new Material.Builder(getMaterialsId(), gregtechId("acetonitrile"))
                .dust()
                .color(0x7D82A3)
                .iconSet(ROUGH)
                .components(Carbon, 2, Hydrogen, 3, Nitrogen, 1)
                .build()
                .setFormula("CH3CN");

        //  25109 Hexanitrohexaaxaisowurtzitane
        GTQTMaterials.Hexanitrohexaaxaisowurtzitane = new Material.Builder(getMaterialsId(), gregtechId("hexanitrohexaaxaisowurtzitane"))
                .dust()
                .color(0x0B7222)
                .iconSet(BRIGHT)
                .components(Carbon, 6, Hydrogen, 6, Nitrogen, 12, Oxygen, 12)
                .build();
        //  25110 Crude Hexanitrohexaaxaisowurtzitane
        GTQTMaterials.CrudeHexanitrohexaaxaisowurtzitane = new Material.Builder(getMaterialsId(), gregtechId("crude_hexanitrohexaaxaisowurtzitane"))
                .dust()
                .color(0x5799EC)
                .iconSet(DULL)
                .components(Carbon, 6, Hydrogen, 6, Nitrogen, 12, Oxygen, 12)
                .build();

        //  25111 Tetraacetyldinitrosohexaazaisowurtzitane
        GTQTMaterials.Tetraacetyldinitrosohexaazaisowurtzitane = new Material.Builder(getMaterialsId(), gregtechId("tetraacetyldinitrosohexaazaisowurtzitane"))
                .dust()
                .color(0xEA7584)
                .iconSet(ROUGH)
                .components(Carbon, 14, Hydrogen, 18, Nitrogen, 8, Oxygen, 6)
                .build();
        //  25112 Dibenzyltetraacetylhexaazaisowurtzitane
        GTQTMaterials.Dibenzyltetraacetylhexaazaisowurtzitane = new Material.Builder(getMaterialsId(), gregtechId("dibenzyltetraacetylhexaazaisowurtzitane"))
                .dust()
                .color(0xB7E8EE)
                .iconSet(DULL)
                .components(Carbon, 28, Hydrogen, 32, Nitrogen, 6, Oxygen, 4)
                .build();
        //  25113 Succinimidyl Acetate
        GTQTMaterials.SuccinimidylAcetate = new Material.Builder(getMaterialsId(), gregtechId("succinimidyl_acetate"))
                .dust()
                .color(0x1D3605)
                .iconSet(ROUGH)
                .components(Carbon, 6, Hydrogen, 7, Nitrogen, 1, Oxygen, 4)
                .build();
        //  25114 N-Hydroxysuccinimide
        GTQTMaterials.NHydroxysuccinimide = new Material.Builder(getMaterialsId(), gregtechId("n_hydroxysuccinimide"))
                .dust()
                .color(0x33BAFB)
                .iconSet(DULL)
                .components(Carbon, 4, Hydrogen, 5, Nitrogen, 1, Oxygen, 3)
                .build()
                .setFormula("(CH2CO)2NOH", true);
        //  25116 Succinic Anhydride
        GTQTMaterials.SuccinicAnhydride = new Material.Builder(getMaterialsId(), gregtechId("succinic_anhydride"))
                .dust()
                .color(0xA2569D)
                .components(Carbon, 4, Hydrogen, 4, Oxygen, 3)
                .build()
                .setFormula("(CH2CO)2O");
        //  25117 Hexabenzylhexaazaisowurtzitane
        GTQTMaterials.Hexabenzylhexaazaisowurtzitane = new Material.Builder(getMaterialsId(), gregtechId("hexabenzylhexaazaisowurtzitane"))
                .dust()
                .color(0x48561E)
                .iconSet(DULL)
                .components(Carbon, 48, Hydrogen, 48, Nitrogen, 6)
                .build();
        //  25120 Benzylamine
        GTQTMaterials.Benzylamine = new Material.Builder(getMaterialsId(), gregtechId("benzylamine"))
                .fluid()
                .color(0x527A92)
                .components(Carbon, 7, Hydrogen, 9, Nitrogen, 1)
                .build();
        //  25121 Benzyl Chloride
        GTQTMaterials.BenzylChloride = new Material.Builder(getMaterialsId(), gregtechId("benzyl_chloride"))
                .fluid()
                .color(0x6699CC)
                .components(Carbon, 7, Hydrogen, 7, Chlorine, 1)
                .build();
        //  25122 Hexamethylenetetramine
        GTQTMaterials.Hexamethylenetetramine = new Material.Builder(getMaterialsId(), gregtechId("hexamethylenetetramine"))
                .dust()
                .color(0x53576D)
                .iconSet(DULL)
                .components(Carbon, 6, Hydrogen, 12, Nitrogen, 4)
                .build()
                .setFormula("(CH2)6N4", true);
        //  25064 Succinic Acid
        GTQTMaterials.SuccinicAcid = new Material.Builder(getMaterialsId(), gregtechId("succinic_acid"))
                .dust()
                .color(0x0C3A5B)
                .iconSet(DULL)
                .components(Carbon, 4, Hydrogen, 6, Oxygen, 4)
                .build();

        //  24233 Potassium Carbonate
        GTQTMaterials.PotassiumCarbonate = new Material.Builder(getMaterialsId(), gregtechId("potassium_carbonate"))
                .dust()
                .color(0x7C89D9)
                .iconSet(ROUGH)
                .components(Potassium, 2, Carbon, 1, Oxygen, 3)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24232 Potassium Bisulfite
        GTQTMaterials.PotassiumBisulfite = new Material.Builder(getMaterialsId(), gregtechId("potassium_bisulfite"))
                .dust()
                .color(344314)
                .iconSet(DULL)
                .components(Potassium, 1, Hydrogen, 1, Sulfur, 1, Oxygen, 3)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24235 Potassium Nitrite
        GTQTMaterials.PotassiumNitrite = new Material.Builder(getMaterialsId(), gregtechId("potassium_nitrite"))
                .dust()
                .color(0xB9B9B9)
                .components(Potassium, 1, Nitrogen, 1, Oxygen, 2)
                .build();

        //  24230 Hydroxylammonium Sulfate
        GTQTMaterials.HydroxylammoniumSulfate = new Material.Builder(getMaterialsId(), gregtechId("hydroxylammonium_sulfate"))
                .dust()
                .color(0x999933)
                .iconSet(DULL)
                .components(Nitrogen, 2, Hydrogen, 8, Oxygen, 6, Sulfur, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(NH3OH)2SO4", true);
        //  24231 Potassium Hydroxylaminedisulfonate
        GTQTMaterials.PotassiumHydroxylaminedisulfonate = new Material.Builder(getMaterialsId(), gregtechId("potassium_hydroxylaminedisulfonate"))
                .dust()
                .color(0x627D25)
                .iconSet(ROUGH)
                .components(Potassium, 2, Nitrogen, 1, Hydrogen, 1, Sulfur, 2, Oxygen, 7)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24236 Barium Dichloride
        GTQTMaterials.BariumDichloride = new Material.Builder(getMaterialsId(), gregtechId("barium_dichloride"))
                .dust()
                .color(0xBF6700)
                .iconSet(BRIGHT)
                .components(Barium, 1, Chlorine, 2)
                .build();

        //  24238 Barium Sulfate Suspension
        GTQTMaterials.BariumSulfateSuspension = new Material.Builder(getMaterialsId(), gregtechId("barium_sulfate_suspension"))
                .fluid()
                .color(0x71560B)
                .components(Barium, 1, Sulfur, 1, Oxygen, 4, Water, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24194 Potassium Sulfate
        GTQTMaterials.PotassiumSulfate = new Material.Builder(getMaterialsId(), gregtechId("potassium_sulfate"))
                .dust()
                .color(0xF4FBB0)
                .iconSet(DULL)
                .components(Potassium, 2, Sulfur, 1, Oxygen, 4)
                .build();

        //  25063 Succinimide
        GTQTMaterials.Succinimide = new Material.Builder(getMaterialsId(), gregtechId("succinimide"))
                .dust()
                .color(0x1774B6)
                .iconSet(ROUGH)
                .components(Carbon, 4, Hydrogen, 5, Nitrogen, 1, Oxygen, 2)
                .build();
        //  24229 Nitrogen Monoxide
        GTQTMaterials.NitrogenMonoxide = new Material.Builder(getMaterialsId(), gregtechId("nitrogen_monoxide"))
                .fluid()
                .color(0x98BCDA)
                .components(Nitrogen, 1, Oxygen, 1)
                .build();
        //  24227 Nitrosonium Tetrafluoroborate
        GTQTMaterials.NitrosoniumTetrafluoroborate = new Material.Builder(getMaterialsId(), gregtechId("nitrosonium_tetrafluoroborate"))
                .dust()
                .color(0xA32A8C)
                .iconSet(ROUGH)
                .components(Sodium, 1, Oxygen, 1, Boron, 1, Fluorine, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //  24228 Tetrafluoroboric Acid
        GTQTMaterials.TetrafluoroboricAcid = new Material.Builder(getMaterialsId(), gregtechId("tetrafluoroboric_acid"))
                .fluid()
                .color(0x83A731)
                .components(Hydrogen, 1, Boron, 1, Fluorine, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  25033 Benzaldehyde
        GTQTMaterials.Benzaldehyde = new Material.Builder(getMaterialsId(), gregtechId("benzaldehyde"))
                .fluid()
                .color(0x957D53)
                .components(Carbon, 7, Hydrogen, 6, Oxygen, 1)
                .build();

        //  24226 Nitronium Tetrafluoroborate
        GTQTMaterials.NitroniumTetrafluoroborate = new Material.Builder(getMaterialsId(), gregtechId("nitronium_tetrafluoroborate"))
                .dust()
                .color(0x787449)
                .iconSet(DULL)
                .components(Sodium, 1, Oxygen, 2, Boron, 1, Fluorine, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //  24225 Silica Gel Base
        GTQTMaterials.SilicaGelBase = new Material.Builder(getMaterialsId(), gregtechId("silica_gel_base"))
                .fluid()
                .color(0x9695FD)
                .iconSet(ROUGH)
                .components(SiliconDioxide, 1, HydrochloricAcid, 1, SodiumHydroxide, 1, Water, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //  24224 Silica Gel
        GTQTMaterials.SilicaGel = new Material.Builder(getMaterialsId(), gregtechId("silica_gel"))
                .dust()
                .color(0x9695FD)
                .iconSet(SHINY)
                .components(Silicon, 1, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  25213 Precious Metal
        GTQTMaterials.PreciousMetal = new Material.Builder(getMaterialsId(), gregtechId("precious_metal"))
                .ore(1, 1, false)
                .ingot()
                .color(0xDAA520)
                .iconSet(SHINY)
                .build()
                .setFormula("Au?", false);

        //  24266 Gold Copper Mixture
        GTQTMaterials.GoldCopperMixture = new Material.Builder(getMaterialsId(), gregtechId("gold_copper_mixture"))
                .dust()
                .color(0xD2D242)
                .iconSet(SHINY)
                .components(Copper, 3, Gold, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("Cu3Au?", true);

        //  24267 Leaching Gold
        GTQTMaterials.LeachingGold = new Material.Builder(getMaterialsId(), gregtechId("leaching_gold"))
                .dust()
                .color(0xA7650F)
                .iconSet(ROUGH)
                .components(Copper, 3, Gold, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("Cu3Au?", true);
        //  24268 Chloroauric Acid
        GTQTMaterials.ChloroauricAcid = new Material.Builder(getMaterialsId(), gregtechId("chloroauric_acid"))
                .fluid()
                .color(LeachingGold.getMaterialRGB() + HydrochloricAcid.getMaterialRGB())
                .components(Hydrogen, 1, Gold, 1, Chlorine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("HAuCl?", false);
        //  24269 Leaching Copper
        GTQTMaterials.LeachingCopper = new Material.Builder(getMaterialsId(), gregtechId("leaching_copper"))
                .dust()
                .color(Copper.getMaterialRGB() + LeachingGold.getMaterialRGB())
                .iconSet(SHINY)
                .components(Copper, 3)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("Cu3?", true);
        //  24286 Gold Nickel Mixture
        GTQTMaterials.GoldNickelMixture = new Material.Builder(getMaterialsId(), gregtechId("gold_nickel_mixture"))
                .dust()
                .color(GoldCopperMixture.getMaterialRGB() + Nickel.getMaterialRGB())
                .iconSet(SAND)
                .components(Nickel, 3, Gold, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("Ni3Au?", true);
        //  24285 Leaching Nickel
        GTQTMaterials.LeachingNickel = new Material.Builder(getMaterialsId(), gregtechId("leaching_nickel"))
                .dust()
                .color(LeachingCopper.getMaterialRGB() + Nickel.getMaterialRGB())
                .iconSet(BRIGHT)
                .components(Nickel, 3)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("Ni3?", true);
        //  24270 Potassium Metabisulfite
        GTQTMaterials.PotassiumMetabisulfite = new Material.Builder(getMaterialsId(), gregtechId("potassium_metabisulfite"))
                .dust()
                .color(Potassium.getMaterialRGB() + Sulfur.getMaterialRGB())
                .iconSet(SAND)
                .components(Potassium, 2, Sulfur, 2, Oxygen, 5)
                .build();

        //低纯度硅岩乳液
        GTQTMaterials.LowPurityNaquadahLotion = new Material.Builder(getMaterialsId(), gregtechId("low_purity_naquadah_lotion"))
                .fluid()
                .flags(DISABLE_DECOMPOSITION)
                .color(0x9AFF9A)
                .components(Naquadah, 1, Oxygen, 2)
                .build();

        //低纯度硅岩溶液
        GTQTMaterials.LowPurityNaquadahSolution = new Material.Builder(getMaterialsId(), gregtechId("low_purity_naquadah_solution"))
                .fluid()
                .flags(DISABLE_DECOMPOSITION)
                .color(0xB4EEB4)
                .components(Naquadah, 1, Oxygen, 2)
                .build();

        //硅岩-精金混合
        GTQTMaterials.Nqad = new Material.Builder(getMaterialsId(), gregtechId("nqad"))
                .fluid()
                .color(0xE9967A)
                .build();

        //氢氧化镓 material('gallium')
        GTQTMaterials.GalliumHydroxide = new Material.Builder(getMaterialsId(), gregtechId("gallium_hydroxide"))
                .dust()
                .color(0xDBDBDB)
                .components(Gallium, 1, Oxygen, 1, Hydrogen, 1)
                .build();

        //废氟
        GTQTMaterials.WasteFluorine = new Material.Builder(getMaterialsId(), gregtechId("waste_fluorine"))
                .fluid()
                .color(0x556B2F)
                .flags(DISABLE_DECOMPOSITION)
                .components(Fluorine, 1)
                .build();

        //废液
        GTQTMaterials.WasteLiquid = new Material.Builder(getMaterialsId(), gregtechId("waste_liquid"))
                .fluid()
                .color(0x548B54)
                .build();

        //浓缩富集硅岩矿泥粉
        GTQTMaterials.ConcentrateEnrichNaquadahClay = new Material.Builder(getMaterialsId(), gregtechId("concentrate_enrich_naquadah_clay"))
                .dust()
                .color(0x48D1CC)
                .build();
        //氧化硅岩混合物粉
        GTQTMaterials.NaquadahOxideRockMixture = new Material.Builder(getMaterialsId(), gregtechId("naquadah_oxide_rock_mixture"))
                .dust()
                .color(0x458B00)
                .build();
        //高纯硅岩溶液
        GTQTMaterials.HighPurityNaquadahSolution = new Material.Builder(getMaterialsId(), gregtechId("high_purity_naquadah_solution"))
                .fluid()
                .color(0x3CB371)
                .build();

        //低纯硫酸超能硅岩粉
        GTQTMaterials.LowPuritySulfuricAcidSuperEnergyNaquadah = new Material.Builder(getMaterialsId(), gregtechId("low_purity_sulfuric_acid_super_energy_naquadah"))
                .dust()
                .color(0x32CD32)
                .build();

        //低纯硫酸超能硅岩溶液
        GTQTMaterials.LowPuritySulfuricAcidSuperEnergyNaquadahSolution = new Material.Builder(getMaterialsId(), gregtechId("low_purity_sulfuric_acid_super_energy_naquadah_solution"))
                .fluid()
                .color(0x2E8B57)
                .build();

        //高纯超能硅岩溶液
        GTQTMaterials.HighPuritySulfuricAcidSuperEnergyNaquadah = new Material.Builder(getMaterialsId(), gregtechId("high_purity_sulfuric_acid_super_energy_naquadah_solution"))
                .fluid()
                .color(0x228B22)
                .build();

        //交错次元空气
        GTQTMaterials.BetAir = new Material.Builder(getMaterialsId(), gregtechId("bet_air"))
                .gas()
                .color(0x2E8B57)
                .flags(DISABLE_DECOMPOSITION)
                .components(Methane, 78, HydrogenSulfide, 21, Neon, 7, Radon, 2)
                .build();

        GTQTMaterials.LiquidBetAir = new Material.Builder(getMaterialsId(), gregtechId("liquid_bet_air"))
                .liquid(new FluidBuilder().temperature(58))
                .color(0x228B22)
                .flags(DISABLE_DECOMPOSITION)
                .components(Methane, 144, CoalGas, 20, HydrogenSulfide, 15, SulfurDioxide, 15, Helium3, 5, Neon,
                        1, Radon, 1)
                .build();

        //离散态素魔力
        GTQTMaterials.MagicGas = new Material.Builder(getMaterialsId(), gregtechId("magic_gas"))
                .gas()
                .color(0x00FFFF)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //交错次元空气
        GTQTMaterials.MarsAir = new Material.Builder(getMaterialsId(), gregtechId("mars_air"))
                .gas()
                .color(0x8B3E2F)
                .flags(DISABLE_DECOMPOSITION)
                .components(CarbonDioxide, 80, Argon, 20, Oxygen, 10, Radon, 10, Hydrogen, 10, Nitrogen, 10, MagicGas, 10)
                .build();

        GTQTMaterials.LiquidMarsAir = new Material.Builder(getMaterialsId(), gregtechId("liquid_mars_air"))
                .liquid(new FluidBuilder().temperature(58))
                .color(0x8B4500)
                .flags(DISABLE_DECOMPOSITION)
                .components(CarbonDioxide, 160, Argon, 15, Oxygen, 15, Helium3, 10, Radon,
                        10, Hydrogen, 10, Nitrogen, 10, MagicGas, 5)
                .build();

        //离散态微薄魔力
        GTQTMaterials.MagicGas = new Material.Builder(getMaterialsId(), gregtechId("magic_gas"))
                .gas()
                .color(0xB2DFEE)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        MagicGas.setFormula("-KQ-");
        //离散态素魔力
        GTQTMaterials.MagicFas = new Material.Builder(getMaterialsId(), gregtechId("magic_fas"))
                .gas()
                .color(0xAEEEEE)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        MagicFas.setFormula("-KQ-");
        //离散态纯净魔力
        GTQTMaterials.MagicDas = new Material.Builder(getMaterialsId(), gregtechId("magic_das"))
                .gas()
                .color(0x98F5FF)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        MagicDas.setFormula("-KQ-");
        //凝聚态素魔力
        GTQTMaterials.MagicAas = new Material.Builder(getMaterialsId(), gregtechId("magic_aas"))
                .gas()
                .color(0x87CEEB)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        MagicAas.setFormula("-KQ-");
        //魔力废液
        GTQTMaterials.MagicRub = new Material.Builder(getMaterialsId(), gregtechId("magic_rub"))
                .gas()
                .color(0x87CEFF)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        MagicRub.setFormula("-KQ-");
        //魔力
        GTQTMaterials.Magic = new Material.Builder(getMaterialsId(), gregtechId("magic"))
                .fluid()
                .color(0x7B68EE)
                .iconSet(DULL)
                .element(GTQTElements.Magic)
                .build();

        //富集魔力
        GTQTMaterials.Richmagic = new Material.Builder(getMaterialsId(), gregtechId("richmagic"))
                .ingot().fluid()
                .color(0x7D26CD)
                .iconSet(DULL)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD)
                .build();
        Richmagic.setFormula("*KQ*");

        //四溴对二甲苯
        GTQTMaterials.Tetrabromo = new Material.Builder(getMaterialsId(), gregtechId("tetrabromo"))
                .fluid()
                .color(0x8B8B00)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 8, Hydrogen, 6, Bromine, 4)
                .build();

        //六溴对二甲苯
        GTQTMaterials.Tetrabromobenzene = new Material.Builder(getMaterialsId(), gregtechId("tetrabromobenzene"))
                .fluid()
                .color(0x8E8E38)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 8, Hydrogen, 4, Bromine, 6)
                .build();

        //BPS
        GTQTMaterials.Bps = new Material.Builder(getMaterialsId(), gregtechId("bps"))
                .fluid()
                .color(0xB452CD)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        Bps.setFormula("(C8H8)n-C8H4Br6");

        //溴化环氧树脂
        GTQTMaterials.Brominatedepoxyresins = new Material.Builder(getMaterialsId(), gregtechId("brominatedepoxyresins"))
                .fluid()
                .color(0xCD3278)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        Brominatedepoxyresins.setFormula("(C11H12O3)n-C8H4Br6");

        GTQTMaterials.Anorthite = new Material.Builder(getMaterialsId(), gregtechId("anorthite"))
                .dust()
                .gem()
                .color(0x595853).iconSet(CERTUS)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Calcium, 1, Aluminium, 2, Silicon, 2, Oxygen, 8)
                .build()
                .setFormula("Ca(Al2Si2O8)", true);


        GTQTMaterials.Albite = new Material.Builder(getMaterialsId(), gregtechId("albite"))
                .dust()
                .gem()
                .color(0xc4a997).iconSet(CERTUS)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Sodium, 1, Aluminium, 1, Silicon, 3, Oxygen, 8)
                .build()
                .setFormula("Na(AlSi3O8)", true);

        GTQTMaterials.Oligoclase = new Material.Builder(getMaterialsId(), gregtechId("oligoclase"))
                .dust()
                .gem()
                .color(0xd5c4b8).iconSet(CERTUS)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Albite, 4, Anorthite, 1)
                .build()
                .setFormula("(Na,Ca)(Si,Al)4O8", true);

        GTQTMaterials.Andesine = new Material.Builder(getMaterialsId(), gregtechId("andesine"))
                .dust()
                .gem()
                .color(0xe18e6f).iconSet(EMERALD)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Albite, 3, Anorthite, 2)
                .build()
                .setFormula("(Na,Ca)(Si,Al)4O8", true);

        GTQTMaterials.Labradorite = new Material.Builder(getMaterialsId(), gregtechId("labradorite"))
                .dust()
                .gem()
                .color(0x5c7181).iconSet(RUBY)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Albite, 2, Anorthite, 3)
                .build()
                .setFormula("(Na,Ca)(Si,Al)4O8", true);

        GTQTMaterials.Bytownite = new Material.Builder(getMaterialsId(), gregtechId("bytownite"))
                .dust()
                .gem()
                .color(0xc99c67).iconSet(LAPIS)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Albite, 1, Anorthite, 4)
                .build()
                .setFormula("(Na,Ca)(Si,Al)4O8", true);

        GTQTMaterials.Clinochlore = new Material.Builder(getMaterialsId(), gregtechId("chlinochlore"))
                .dust()
                .gem()
                .color(0x303e38).iconSet(EMERALD)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Magnesium, 5, Aluminium, 2, Silicon, 3, Oxygen, 18, Hydrogen, 8)
                .build()
                .setFormula("(Mg5Al)(AlSi3)O10(OH)8", true);

        GTQTMaterials.Augite = new Material.Builder(getMaterialsId(), gregtechId("augite"))
                .dust()
                .color(0x1b1717).iconSet(ROUGH)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Calcium, 2, Magnesium, 3, Iron, 3, Silicon, 8, Oxygen, 24)
                .build()
                .setFormula("(Ca2MgFe)(MgFe)2(Si2O6)4", true);


        GTQTMaterials.Dolomite = new Material.Builder(getMaterialsId(), gregtechId("dolomite"))
                .dust()
                .color(0xbbb8b2)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Calcium, 1, Magnesium, 1, Carbon, 2, Oxygen, 6)
                .build()
                .setFormula("CaMg(CO3)2", true);

        GTQTMaterials.Muscovite = new Material.Builder(getMaterialsId(), gregtechId("muscovite"))
                .dust()
                .color(0x8b876a)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Potassium, 1, Aluminium, 3, Silicon, 3, Oxygen, 12, Hydrogen, 10)
                .build()
                .setFormula("KAl2(AlSi3O10)(OH)2)", true);

        GTQTMaterials.Fluorite = new Material.Builder(getMaterialsId(), gregtechId("fluorite"))
                .dust()
                .gem()
                .ore()
                .color(0x276a4c).iconSet(CERTUS)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Calcium, 1, Fluorine, 2)
                .build();

        GTQTMaterials.Forsterite = new Material.Builder(getMaterialsId(), gregtechId("forsterite"))
                .dust()
                .gem()
                .color(0x1d640f).iconSet(LAPIS)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Magnesium, 2, Sulfur, 1, Oxygen, 4)
                .build()
                .setFormula("Mg2(SiO4)", true);

        GTQTMaterials.Lizardite = new Material.Builder(getMaterialsId(), gregtechId("lizardite"))
                .dust()
                .color(0xa79e42)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Magnesium, 3, Silicon, 2, Oxygen, 9, Hydrogen, 4)
                .build()
                .setFormula("Mg3Si2O5(OH)4", true);

        //锗线
        //氧化闪锌混合物
        GTQTMaterials.ZincOxideFlashMixture = new Material.Builder(getMaterialsId(), gregtechId("zinc_oxide_flash_mixture"))
                .dust()
                .color(0xAEEEEE)
                .build()
                .setFormula("Ge?O?", true);

        //富集闪锌混合物
        GTQTMaterials.RichZincFlashMixture = new Material.Builder(getMaterialsId(), gregtechId("rich_zinc_flash_mixture"))
                .dust()
                .color(0xB0C4DE)
                .build()
                .setFormula("Ge?O?", true);

        //富集闪锌焙烧残留
        GTQTMaterials.EnrichmentResidualFlashZincRoasting = new Material.Builder(getMaterialsId(), gregtechId("enrichment_residual_flash_zinc_roasting"))
                .dust()
                .color(0xB0E0E6)
                .build()
                .setFormula("Ge?O?", true);

        //含闪锌溶液
        GTQTMaterials.ZincFlashSolution = new Material.Builder(getMaterialsId(), gregtechId("zinc _flash _solution"))
                .fluid()
                .color(0xa79e42)
                .build()
                .setFormula("Ge?SO4?", true);

        //氢氧化铁
        GTQTMaterials.FerricHydroxide = new Material.Builder(getMaterialsId(), gregtechId("ferric_hydroxide"))
                .dust()
                .color(0xCD3700)
                .flags(DISABLE_DECOMPOSITION)
                .components(Iron, 1, Oxygen, 3, Hydrogen, 3)
                .build();

        //混锗铁残渣
        GTQTMaterials.MixedGermaniumIronResidue = new Material.Builder(getMaterialsId(), gregtechId("mixed_germanium_iron_residue"))
                .dust()
                .color(0xB3EE3A)
                .build()
                .setFormula("Ge?Fe?Zn?", true);

        //混锗镉残渣
        GTQTMaterials.MixedGermaniumCadmiumResidue = new Material.Builder(getMaterialsId(), gregtechId("mixed_germanium_cadmium_residue"))
                .dust()
                .color(0xC67171)
                .build()
                .setFormula("Ge?Cd?Zn?", true);

        //残渣溶液
        GTQTMaterials.ResidueSolution = new Material.Builder(getMaterialsId(), gregtechId("residue_solution"))
                .fluid()
                .color(0xCD950C)
                .build()
                .setFormula("Ge?Cd?*", true);

        //一氧化锗
        GTQTMaterials.GermaniumMonoxide = new Material.Builder(getMaterialsId(), gregtechId("germanium_monoxide"))
                .fluid()
                .color(0x97FFFF)
                .build()
                .setFormula("GeO", true);
        //炉渣
        GTQTMaterials.BurningGermaniumSlag = new Material.Builder(getMaterialsId(), gregtechId("burning_germanium_slag"))
                .dust()
                .color(0xA0522D)
                .build()
                .setFormula("Fe?Zn?Pd?Cd?", true);

        //鞣酸
        GTQTMaterials.TannicAcid = new Material.Builder(getMaterialsId(), gregtechId("tannic_acid"))
                .fluid()
                .color(0x8B6914)
                .build()
                .setFormula("C76H52O46", true);

        GTQTMaterials.ImpureTannicAcid = new Material.Builder(getMaterialsId(), gregtechId("impure_tannic_acid"))
                .fluid()
                .color(0x8B7E66)
                .build()
                .setFormula("C76H52O46*", true);


        //富集锗沉淀
        GTQTMaterials.Fujizhechendian = new Material.Builder(getMaterialsId(), gregtechId("fujizhechendian"))
                .dust()
                .color(0x7EC0EE)
                .build()
                .setFormula("Ge?", true);


        //四氯化锗
        GTQTMaterials.EnrichedGermaniumPrecipitation = new Material.Builder(getMaterialsId(), gregtechId("enriched_germanium_precipitation"))
                .fluid()
                .color(0x8A2BE2)
                .build()
                .setFormula("GeCl4*", true);

        //四氯化锗
        GTQTMaterials.EnrichedGermaniumPrecipitationAcid = new Material.Builder(getMaterialsId(), gregtechId("enriched_germanium_precipitation_acid"))
                .fluid()
                .color(0xa79e42)
                .build()
                .setFormula("GeCl4*HCl", true);

        //高淳四氯化锗
        GTQTMaterials.HighPurityGermaniumTetrachloride = new Material.Builder(getMaterialsId(), gregtechId("high_purity_germanium_tetrachloride"))
                .fluid()
                .color(0x7171C6)
                .build()
                .setFormula("GeCl4", true);

        //高纯二氧化锗
        GTQTMaterials.HighPurityGermaniumDioxide = new Material.Builder(getMaterialsId(), gregtechId("high_purity_germanium_dioxide"))
                .dust()
                .color(0x68228B)
                .build()
                .setFormula("Ge02", true);

        //杀虫剂
        GTQTMaterials.Dfeltamethrin = new Material.Builder(getMaterialsId(), gregtechId("dfeltamethrin"))
                .fluid()
                .color(0x0000EE)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C22H19Br2NO3", true);

        //溴氰菊酯
        GTQTMaterials.Deltamethrin = new Material.Builder(getMaterialsId(), gregtechId("deltamethrin"))
                .dust()
                .components(Carbon, 22, Hydrogen, 19, Bromine, 2, Nitrogen, 1, Oxygen, 3)
                .color(0x0000FF)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C22H19Br2NO3", true);

        //二溴水苯
        GTQTMaterials.Dfeltamethrina = new Material.Builder(getMaterialsId(), gregtechId("dfeltamethrina"))
                .fluid()
                .color(0x006400)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 22, Hydrogen, 19, Bromine, 2, Nitrogen, 1, Oxygen, 3)
                .build();

        //二溴苯酚
        GTQTMaterials.Dfeltamethrinb = new Material.Builder(getMaterialsId(), gregtechId("dfeltamethrinb"))
                .fluid()
                .color(0x00688B)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C6H4Br2O", true);
        //二溴-2-丙酮酚
        GTQTMaterials.Dfeltamethrinc = new Material.Builder(getMaterialsId(), gregtechId("dfeltamethrinc"))
                .fluid()
                .color(0x00868B)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C6H4Br2O", true);
        //二溴-2-丙酮酚盐酸盐
        GTQTMaterials.Dfeltamethrind = new Material.Builder(getMaterialsId(), gregtechId("dfeltamethrind"))
                .fluid()
                .color(0x008B00)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C6H4Br2O*HCl", true);
        //二溴菊酸
        GTQTMaterials.Dfeltamethrine = new Material.Builder(getMaterialsId(), gregtechId("dfeltamethrine"))
                .fluid()
                .color(0x008B45)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C8H10Br2O2", true);

        //二溴菊酰氯
        GTQTMaterials.Dfeltamethrinf = new Material.Builder(getMaterialsId(), gregtechId("dfeltamethrinf"))
                .fluid()
                .color(0x008B8B)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C8H10Br2O2*CN", true);

        //间苯氧基苯甲醛
        GTQTMaterials.Dfeltamethring = new Material.Builder(getMaterialsId(), gregtechId("dfeltamethring"))
                .fluid()
                .color(0x009ACD)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C13H10O2", true);

        //α-氰基间苯氧基苯甲醇
        GTQTMaterials.Dfeltamethrinh = new Material.Builder(getMaterialsId(), gregtechId("dfeltamethrinh"))
                .fluid()
                .color(0x00B2EE)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C22H19Br2NO3", true);

        //风趣幽默的生物工程
        //CTAB
        GTQTMaterials.CTAB = new Material.Builder(getMaterialsId(), gregtechId("cbat"))
                .fluid()
                .color(0x9ACD32)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C19H42BrN", true);
        //溴代十六烷
        GTQTMaterials.BromoHexadecane = new Material.Builder(getMaterialsId(), gregtechId("bromo_hexadecane"))
                .fluid()
                .color(0xAB82FF)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C16H33Br", true);
        //十六醇
        GTQTMaterials.Hexadecanol = new Material.Builder(getMaterialsId(), gregtechId("hexadecanol"))
                .fluid()
                .color(0xBDB76B)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C16H34O", true);

        //β-巯基乙醇
        GTQTMaterials.Hydroxyethanethiol = new Material.Builder(getMaterialsId(), gregtechId("hydroxyethanethiol"))
                .fluid()
                .color(0xCD3333)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C2H6OS", true);
        //二硫代二甘醇
        GTQTMaterials.Erliudaierganchun = new Material.Builder(getMaterialsId(), gregtechId("erliudaierganchun"))
                .fluid()
                .color(0xCD8C95)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C4H6O4S2", true);
        //三羟甲基氨基甲烷
        GTQTMaterials.TRIS = new Material.Builder(getMaterialsId(), gregtechId("tris"))
                .fluid()
                .color(0xCDB7B5)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(HOCH2)3CNH2", true);

        //三羟甲基氨基甲烷
        GTQTMaterials.TRISP = new Material.Builder(getMaterialsId(), gregtechId("trisp"))
                .fluid()
                .color(0xDEB887)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(HOCH2)3CNH2*", true);

        //三羟甲基硝基甲烷
        GTQTMaterials.TRISN = new Material.Builder(getMaterialsId(), gregtechId("trisn"))
                .fluid()
                .color(0xEEAEEE)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("CH2（NO3）3", true);

        //异戊醇
        GTQTMaterials.Isoamylalcohol = new Material.Builder(getMaterialsId(), gregtechId("isoamylalcohol"))
                .fluid()
                .color(0xEE00EE)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C5H12O", true);

        //异戊醛
        GTQTMaterials.Isovaleraldehyde = new Material.Builder(getMaterialsId(), gregtechId("isovaleraldehyde"))
                .fluid()
                .color(0xEE8262)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C5H10O", true);

        //dna
        GTQTMaterials.DNAorigin = new Material.Builder(getMaterialsId(), gregtechId("dnaorigin"))
                .fluid()
                .color(0xB3EE3A)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        getMaterialsId();
        getMaterialsId();
        getMaterialsId();

        GTQTMaterials.DNAdeal = new Material.Builder(getMaterialsId(), gregtechId("dnadeal"))
                .fluid()
                .color(0xEE8262)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.DNAh = new Material.Builder(getMaterialsId(), gregtechId("dnah"))
                .fluid()
                .color(0x737373)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.DNAl = new Material.Builder(getMaterialsId(), gregtechId("dnal"))
                .fluid()
                .color(0xCD9B1D)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.DNAp = new Material.Builder(getMaterialsId(), gregtechId("dnap"))
                .fluid()
                .color(0x388E8E)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.DNAw = new Material.Builder(getMaterialsId(), gregtechId("dnaw"))
                .fluid()
                .color(0xFFFAFA)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.DNAn = new Material.Builder(getMaterialsId(), gregtechId("dnan"))
                .fluid()
                .color(0xFFFFFF)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.Wool = new Material.Builder(getMaterialsId(), gregtechId("wool"))
                .fluid().dust()
                .color(0xFFFFFF)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //氧化钛
        GTQTMaterials.TitaniumOxide = new Material.Builder(getMaterialsId(), gregtechId("titanium_oxide"))
                .ingot().dust()
                .color(0xFF6EB4)
                .components(Titanium,1,Oxygen,2)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //酸性钛
        GTQTMaterials.AcidicTitanium = new Material.Builder(getMaterialsId(), gregtechId("acidic_titanium"))
                .fluid()
                .color(0xFF6A6A)
                .components(Titanium,1,Sulfur,1,Oxygen,4)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //含杂酰化钛
        GTQTMaterials.ImpureTitaniumAcylate = new Material.Builder(getMaterialsId(), gregtechId("impure_titanium_acylate"))
                .fluid()
                .color(0xFF3E96)
                .components(Titanium,1,Carbon,1,Oxygen,1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("[Ti(O2)(OH)(H2O)4]*", true);
        //酰化钛沉淀
        GTQTMaterials.TitaniumAcylatePrecipitation = new Material.Builder(getMaterialsId(), gregtechId("titanium_acylate_precipitation"))
                .dust()
                .color(0xFF7F00)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("[Ti(O2)(OH)(H2O)4]*", true);
        //粗制酰化钛
        GTQTMaterials.CrudeTitaniumAcylate = new Material.Builder(getMaterialsId(), gregtechId("crude_titanium_acylate"))
                .fluid()
                .color(0xFF8C69)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("[Ti(O2)(OH)(H2O)4]*", true);

        //酰化钛
        GTQTMaterials.TitaniumAcylate = new Material.Builder(getMaterialsId(), gregtechId("titanium_acylate"))
                .fluid()
                .color(0xFF00FF)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("[Ti(O2)(OH)(H2O)4]＋", true);
        //乙酸钡
        GTQTMaterials.BariumAcetate = new Material.Builder(getMaterialsId(), gregtechId("barium_acetate"))
                .fluid()
                .color(0xFA8072)
                .components(Titanium,1,Carbon,1,Oxygen,1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(CH₃COO)₂Ba", true);

        //钛合金TC4
        GTQTMaterials.TitaniumAlloyTCF = new Material.Builder(getMaterialsId(), gregtechId("titanium_alloy_tcf"))
                .fluid()
                .color(0xFF7F00)
                .blast(3600,MID)
                .components(Titanium,6,Aluminium,4,Vanadium,1)
                .flags(DISABLE_DECOMPOSITION,GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW, GENERATE_FOIL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_SPRING)
                .build();

        //生物原油
        GTQTMaterials.BioOil = new Material.Builder(getMaterialsId(), gregtechId("biooil"))
                .fluid()
                .color(0xFA8072)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //快乐细菌
        //原始杂质
        GTQTMaterials.Enzymesaz = new Material.Builder(getMaterialsId(), gregtechId("enzymesaz"))
                .fluid()
                .color(0xCDAA7D)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.Enzymesbz = new Material.Builder(getMaterialsId(), gregtechId("enzymesbz"))
                .fluid()
                .color(0xCD8162)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.Enzymescz = new Material.Builder(getMaterialsId(), gregtechId("enzymescz"))
                .fluid()
                .color(0xD2691E)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.Enzymesdz = new Material.Builder(getMaterialsId(), gregtechId("enzymesdz"))
                .fluid()
                .color(0x68228B)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.Enzymesez = new Material.Builder(getMaterialsId(), gregtechId("enzymesez"))
                .fluid()
                .color(0x0000EE)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //这里注册线性方程组的几个基础解系
        GTQTMaterials.Enzymesa = new Material.Builder(getMaterialsId(), gregtechId("enzymesa"))
                .fluid()
                .color(0xEE00EE)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：1 0 0 0 0", true);

        GTQTMaterials.Enzymesb = new Material.Builder(getMaterialsId(), gregtechId("enzymesb"))
                .fluid()
                .color(0xCD3333)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：0 1 0 0 0", true);

        GTQTMaterials.Enzymesc = new Material.Builder(getMaterialsId(), gregtechId("enzymesc"))
                .fluid()
                .color(0xB03060)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：0 0 1 0 0", true);

        GTQTMaterials.Enzymesd = new Material.Builder(getMaterialsId(), gregtechId("enzymesd"))
                .fluid()
                .color(0xB4EEB4)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：0 0 0 1 0", true);

        GTQTMaterials.Enzymese = new Material.Builder(getMaterialsId(), gregtechId("enzymese"))
                .fluid()
                .color(0xBDB76B)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：0 0 0 0 1", true);

        //富集生物培养基
        GTQTMaterials.Rnzymes = new Material.Builder(getMaterialsId(), gregtechId("rnzymes"))
                .fluid()
                .color(0xFA8072)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //普适矿处菌种 101
        GTQTMaterials.Enzymesaa = new Material.Builder(getMaterialsId(), gregtechId("enzymese_101"))
                .fluid()
                .color(0x00E5EE)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：1 0 1 0 0", true);
        //定向铂系菌种 102
        GTQTMaterials.Enzymesab = new Material.Builder(getMaterialsId(), gregtechId("enzymese_102"))
                .fluid()
                .color(0x54FF9F)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：1 0 1 1 0", true);
        //普适魔性菌种 103
        GTQTMaterials.Enzymesac = new Material.Builder(getMaterialsId(), gregtechId("enzymese_103"))
                .fluid()
                .color(0x6B8E23)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：1 1 0 1 0", true);
        //普适副产菌种 104
        GTQTMaterials.Enzymesad = new Material.Builder(getMaterialsId(), gregtechId("enzymese_104"))
                .fluid()
                .color(0x76EE00)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：1 1 0 1 1", true);
        //
        //工业合成菌种I 201
        GTQTMaterials.Enzymesba = new Material.Builder(getMaterialsId(), gregtechId("enzymese_201"))
                .fluid()
                .color(0xA0522D)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：2 1 1 3 1", true);
        //工业还原菌种 202
        GTQTMaterials.Enzymesbb = new Material.Builder(getMaterialsId(), gregtechId("enzymese_202"))
                .fluid()
                .color(0x9932CC)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：1 2 3 1 1", true);
        //工业氧化菌种 203
        GTQTMaterials.Enzymesbc = new Material.Builder(getMaterialsId(), gregtechId("enzymese_203"))
                .fluid()
                .color(0x8FBC8F)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：1 3 2 1 1", true);
        //工业催化菌种 204
        GTQTMaterials.Enzymesbd = new Material.Builder(getMaterialsId(), gregtechId("enzymese_204"))
                .fluid()
                .color(0x8B7D7B)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：2 1 1 3 1", true);
        //
        //定向脂肪酶 301
        GTQTMaterials.Enzymesca = new Material.Builder(getMaterialsId(), gregtechId("enzymese_301"))
                .fluid()
                .color(0x838B8B)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：4 1 1 3 2", true);
        //普适发酵酶 302
        GTQTMaterials.Enzymescb = new Material.Builder(getMaterialsId(), gregtechId("enzymese_302"))
                .fluid()
                .color(0x8B008B)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：2 4 2 3 1", true);
        //定向发酵酶 303
        GTQTMaterials.Enzymescc = new Material.Builder(getMaterialsId(), gregtechId("enzymese_303"))
                .fluid()
                .color(0x8A2BE2)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：2 3 2 4 1", true);
        //
        //活性诱变酶 401
        GTQTMaterials.Enzymesda = new Material.Builder(getMaterialsId(), gregtechId("enzymese_401"))
                .fluid()
                .color(0x8B0000)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：2 5 2 4 3", true);

        //定向镧系菌种
        GTQTMaterials.Enzymesea = new Material.Builder(getMaterialsId(), gregtechId("enzymese_501"))
                .fluid()
                .color(0x54FF9F)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：* * * * *", true);

        //5-羟甲基糠醛
        GTQTMaterials.Hydroxymethylfurfural = new Material.Builder(getMaterialsId(), gregtechId("hydroxymethylfurfural"))
                .fluid()
                .color(0x7A378B)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C6H8O3", true);
        //2,5-二甲基呋喃
        GTQTMaterials.Methylfuran = new Material.Builder(getMaterialsId(), gregtechId("methylfuran"))
                .fluid()
                .color(0xA0522D)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C5H6O", true);
        //2-环己胺基乙磺酸
        GTQTMaterials.CHES = new Material.Builder(getMaterialsId(), gregtechId("ches"))
                .fluid()
                .color(0xCD3278)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C8H17NO3S", true);
        //环己胺基乙磺酸钠
        GTQTMaterials.Ethanesulphonate  = new Material.Builder(getMaterialsId(), gregtechId("ethanesulphonate"))
                .fluid()
                .color(0xCDAA7D)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C8H16NNaO3S", true);
        //冷却液
        GTQTMaterials.GelidCryotheum = new Material.Builder(getMaterialsId(), gregtechId("gelid_cryotheum"))
                .liquid(new FluidBuilder().temperature(8).customStill().customFlow())
                .color(0x40B8FB)
                .components(Ice, 2, Electrotine, 1, Water, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.SodiumPhosphomolybdate = new Material.Builder(getMaterialsId(), gregtechId("sodium_phosphomolybdate"))
                .dust()
                .color(0xF3E0A8)
                .iconSet(BRIGHT)
                .components(Oxygen, 40, Molybdenum, 12, Sodium, 3, Phosphorus, 1)
                .flags(DECOMPOSITION_BY_ELECTROLYZING)
                .build()
                .setFormula("(MoO3)12Na3PO4", true);

        GTQTMaterials.FerricCatalyst = new Material.Builder(getMaterialsId(), gregtechId("ferric_catalyst"))
                .dust()
                .color(0xFFD700)
                .build()
                .setFormula("Fe2(C4H4O6)3", true);

        GTQTMaterials.PhosphonitrilicChlorideTrimer = new Material.Builder(getMaterialsId(), gregtechId("phosphonitrilic_chloride_trimer"))
                .fluid()
                .color(0x082C38)
                .components(Chlorine, 6, Nitrogen, 3, Phosphorus, 3)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.SodiumTrifluoroethanolate = new Material.Builder(getMaterialsId(), gregtechId("sodium_trifluoroethanolate"))
                .dust()
                .color(0x50083E)
                .iconSet(ROUGH)
                .components(Carbon, 2, Hydrogen, 4, Fluorine, 3, Sodium, 1, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.OctafluoroPentanol = new Material.Builder(getMaterialsId(), gregtechId("octafluoro_pentanol"))
                .fluid()
                .color(0xE5EBDE)
                .components(Carbon, 5, Hydrogen, 4, Fluorine, 8, Oxygen, 1)
                .build();

        GTQTMaterials.FluoroboricAcid = new Material.Builder(getMaterialsId(), gregtechId("fluoroboric_acid"))
                .liquid(new FluidBuilder().attributes(ACID))
                .color(0xD5811B)
                .components(Hydrogen, 1, Boron, 1, Fluorine, 4)
                .build();

        GTQTMaterials.SodiumNitrite = new Material.Builder(getMaterialsId(), gregtechId("sodium_nitrite"))
                .dust()
                .color(0x205CA4)
                .iconSet(DULL)
                .components(Sodium, 1, Nitrogen, 1, Oxygen, 2)
                .build();

        GTQTMaterials. BenzenediazoniumTetrafluoroborate = new Material.Builder(getMaterialsId(), gregtechId("benzenediazonium_tetrafluoroborate"))
                .fluid()
                .color(0xD5C5B2)
                .components(Carbon, 6, Hydrogen, 5, Boron, 1, Fluorine, 4, Nitrogen, 2)
                .build();

        GTQTMaterials.CoACABCatalyst = new Material.Builder(getMaterialsId(), gregtechId("co_ac_ab_catalyst"))
                .dust()
                .color(0x6B4312)
                .iconSet(METALLIC)
                .build();

        //  25098 Sodium Formate
        GTQTMaterials.SodiumFormate = new Material.Builder(getMaterialsId(), gregtechId("sodium_formate"))
                .fluid()
                .color(0x416CC0)
                .iconSet(ROUGH)
                .components(Carbon, 1, Hydrogen, 1, Oxygen, 2, Sodium, 1)
                .build();

        //钨酸铵
        GTQTMaterials.AmmoniumTungstate = new Material.Builder(getMaterialsId(), gregtechId("ammonium_tungstate"))
                .fluid()
                .color(0x008B00)
                .iconSet(ROUGH)
                .build()
                .setFormula("（NH4)6W7024•6H20", true);
        //晶体钨酸铵
        GTQTMaterials.CammoniumTungstate = new Material.Builder(getMaterialsId(), gregtechId("cammonium_tungstate"))
                .dust()
                .color(0x458B00)
                .iconSet(SHINY)
                .build()
                .setFormula("（NH4)6W7024", true);

        //Na2WO4
        GTQTMaterials.SodiumTungstateDihydrate = new Material.Builder(getMaterialsId(), gregtechId("sodium_tungstate_dihydrate"))
                .dust()
                .color(0x473C8B)
                .iconSet(SHINY)
                .build()
                .setFormula("Na2WO4", true);

        //工业钨酸
        GTQTMaterials.ItungsticAcid = new Material.Builder(getMaterialsId(), gregtechId("itungstic_acid"))
                .fluid()
                .color(0x68228B)
                .iconSet(SHINY)
                .build()
                .setFormula("H2WO4*", true);

        //锆石
        GTQTMaterials.Zircon = new Material.Builder(getMaterialsId(), gregtechId("zircon"))
                .ore().dust()
                .color(0x8B8B00)
                .iconSet(SHINY)
                .build()
                .setFormula("ZrSiO₄*", true);

        //熟制锆石
        GTQTMaterials.Zirconf = new Material.Builder(getMaterialsId(), gregtechId("zirconf"))
                .dust()
                .color(0xA52A2A)
                .iconSet(SHINY)
                .build();

        //酸性锆石溶液
        GTQTMaterials.Zircons = new Material.Builder(getMaterialsId(), gregtechId("zircons"))
                .fluid()
                .color(0x9AFF9A)
                .iconSet(SHINY)
                .build();

        //硫酸锆
        GTQTMaterials.Zrso = new Material.Builder(getMaterialsId(), gregtechId("zrso"))
                .fluid()
                .color(0xC6E2FF)
                .iconSet(SHINY)
                .build()
                .setFormula("ZrSO₄", true);

        //硫酸铪
        GTQTMaterials.Hfso = new Material.Builder(getMaterialsId(), gregtechId("hfso"))
                .fluid()
                .color(0x9F79EE)
                .iconSet(SHINY)
                .build()
                .setFormula("HfSO₄", true);

        //钽铌铁粉
        GTQTMaterials.NiobiumTantalumFe = new Material.Builder(getMaterialsId(), gregtechId("niobium_tantalum_fe"))
                .dust()
                .color(0x8B3E2F)
                .iconSet(SHINY)
                .build();

        GTQTMaterials.NiobiumTantalumFec = new Material.Builder(getMaterialsId(), gregtechId("niobium_tantalum_fec"))
                .fluid()
                .color(0x8B3A62)
                .iconSet(SHINY)
                .build();

        //氯化铌
        GTQTMaterials.Nbcl = new Material.Builder(getMaterialsId(), gregtechId("nbcl"))
                .fluid()
                .color(0x6B8E23)
                .iconSet(SHINY)
                .build()
                .setFormula("NbCl₄", true);
        //氯化钽
        GTQTMaterials.Tacl = new Material.Builder(getMaterialsId(), gregtechId("tacl"))
                .fluid()
                .color(0x698B22)
                .iconSet(SHINY)
                .build()
                .setFormula("TaCl₄", true);

        //氟化铌
        GTQTMaterials.Nbcla = new Material.Builder(getMaterialsId(), gregtechId("nbcla"))
                .fluid()
                .color(0x5D478B)
                .iconSet(SHINY)
                .build()
                .setFormula("NbCl₄", true);
        //氟化钽
        GTQTMaterials.Tacla = new Material.Builder(getMaterialsId(), gregtechId("tacla"))
                .fluid()
                .color(0x473C8B)
                .iconSet(SHINY)
                .build()
                .setFormula("TaCl₄", true);

        //七氟铌酸钾
        GTQTMaterials.Nbclb = new Material.Builder(getMaterialsId(), gregtechId("nbclb"))
                .fluid()
                .color(0x218868)
                .iconSet(SHINY)
                .build()
                .setFormula("F7NbCl4", true);
        //七氟铌酸钾
        GTQTMaterials.Taclb = new Material.Builder(getMaterialsId(), gregtechId("taclb"))
                .fluid()
                .color(0x2E8B57)
                .iconSet(SHINY)
                .build()
                .setFormula("F7TaCl4", true);

        GTQTMaterials.ElectrolyteReflectorMixture = new Material.Builder(getMaterialsId(), gregtechId("electrolyte_reflector_mixture"))
                .liquid(new FluidBuilder().temperature(209))
                .color(0xE62A35)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("TaCl₄", true);

        GTQTMaterials.FullerenePolymerMatrix = new Material.Builder(getMaterialsId(), gregtechId("fullerene_polymer_matrix"))
                .polymer()
                .liquid(new FluidBuilder().temperature(500))
                .color(0x2F0B01)
                .iconSet(SHINY)
                .components(Lead, 1, Iron, 1, Carbon, 153, Hydrogen, 36, Nitrogen, 1, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION, NO_SMASHING, NO_SMELTING, GENERATE_PLATE, GENERATE_FOIL, GENERATE_ROD, GENERATE_FINE_WIRE)
                .build();


        GTQTMaterials.DragonBreath = new Material.Builder(getMaterialsId(), gregtechId("dragon_breath"))
                .liquid(new FluidBuilder().attributes(FluidAttributes.ACID))
                .color(0x9400D3)
                .build()
                .setFormula("Dc?", false);


        GTQTMaterials.ConcentrateDragonBreath = new Material.Builder(getMaterialsId(), gregtechId("concentrate_dragon_breath"))
                .liquid(new FluidBuilder().attributes(FluidAttributes.ACID))
                .color(0x9400D3)
                .build()
                .setFormula("Dc2?", true);


        GTQTMaterials.DragonBlood = new Material.Builder(getMaterialsId(), gregtechId("dragon_blood"))
                .liquid(new FluidBuilder().attributes(FluidAttributes.ACID))
                .color(0xDC2814)
                .iconSet(DULL)
                .build()
                .setFormula("*Dc*Rn?", true);

        GTQTMaterials.IsobutyricAcid = new Material.Builder(getMaterialsId(), gregtechId("isobutyric_acid"))
                .fluid()
                .color(0x9400D3)
                .components(Carbon, 4, Hydrogen, 8, Oxygen, 2)
                .build();

        GTQTMaterials.IsobutyricAnhydride = new Material.Builder(getMaterialsId(), gregtechId("isobutyric_anhydride"))
                .fluid()
                .color(0x473C8B)
                .components(Carbon, 8, Hydrogen, 14, Oxygen, 3)
                .build();

        GTQTMaterials.Dimethylketene = new Material.Builder(getMaterialsId(), gregtechId("dimethylketene"))
                .fluid()
                .color(0x0925BE)
                .components(Carbon, 4, Hydrogen, 6, Oxygen, 2)
                .build();

        GTQTMaterials.Tetramethylcyclobutanediol = new Material.Builder(getMaterialsId(), gregtechId("tetramethylcyclobutanediol"))
                .fluid()
                .color(Dimethylketene.getMaterialRGB() + Hydrogen.getMaterialRGB())
                .components(Carbon, 8, Hydrogen, 16, Oxygen, 2)
                .build();

        GTQTMaterials.CBDOPolycarbonate = new Material.Builder(getMaterialsId(), gregtechId("cbdo_polycarbonate"))
                .ingot()
                .fluid()
                .color(0xDFDFDF)
                .flags(DISABLE_DECOMPOSITION, NO_SMASHING, NO_SMELTING, GENERATE_PLATE)
                .components(Carbon, 9, Hydrogen, 14, Oxygen, 3)
                .build();

        GTQTMaterials.DiphenylCarbonate = new Material.Builder(getMaterialsId(), gregtechId("diphenyl_carbonate"))
                .fluid()
                .color(0xEE3A8C)
                .components(Carbon, 13, Hydrogen, 10, Oxygen, 3)
                .build();

        GTQTMaterials.BPAPolycarbonate = new Material.Builder(getMaterialsId(), gregtechId("bpa_polycarbonate"))
                .ingot()
                .fluid()
                .color(0xE3EBDA)
                .flags(DISABLE_DECOMPOSITION, NO_SMASHING, NO_SMELTING, GENERATE_PLATE)
                .components(Carbon, 16, Hydrogen, 14, Oxygen, 3)
                .build();

        //  24296 Californium Nitrite
        GTQTMaterials.CaliforniumNitrite = new Material.Builder(getMaterialsId(), gregtechId("californium_nitrite"))
                .dust()
                .color(0x914626)
                .iconSet(ROUGH)
                .components(Californium, 1, Nitrogen, 3, Oxygen, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("Cf(NO2)3", true);
        //  24297 Californium Dioxide
        GTQTMaterials.CaliforniumDioxide = new Material.Builder(getMaterialsId(), gregtechId("californium_dioxide"))
                .dust()
                .color(0x912D01)
                .iconSet(DULL)
                .components(Californium, 1, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //  24298 Californium Hexachloride
        GTQTMaterials.CaliforniumHexachloride = new Material.Builder(getMaterialsId(), gregtechId("californium_hexachloride"))
                .fluid()
                .color(Californium.getMaterialRGB() + Chlorine.getMaterialRGB())
                .components(Californium, 2, Chlorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //  24299 Californium Hexafluoride
        GTQTMaterials.CaliforniumHexafluoride = new Material.Builder(getMaterialsId(), gregtechId("californium_hexafluoride"))
                .gas()
                .color(0xBBFFFF)
                .components(Californium, 2, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //  24300 Californium-252 Hexafluoride
        GTQTMaterials.Californium252Hexafluoride = new Material.Builder(getMaterialsId(), gregtechId("californium_252_hexafluoride"))
                .gas()
                .color(0xA4D3EE)
                .components(Californium252, 2, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //  24301 Steam Cracked Californium-252 Hexafluoride
        GTQTMaterials.SteamCrackedCalifornium252Hexafluoride = new Material.Builder(getMaterialsId(), gregtechId("steam_cracked_californium_252_hexafluoride"))
                .gas()
                .color(0x9F79EE)
                .components(Californium252, 2, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.Californium252Dioxide = new Material.Builder(getMaterialsId(), gregtechId("californium_252_dioxide"))
                .dust()
                .color(0x912D01)
                .iconSet(ROUGH)
                .components(Californium252, 1, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();


        GTQTMaterials.Celestite = new Material.Builder(getMaterialsId(), gregtechId("celestite"))
                .gem()
                .color(0x4AE3E6)
                .iconSet(OPAL)
                .components(Strontium, 1, Sulfur, 1, Oxygen, 4)
                .flags(CRYSTALLIZABLE, DISABLE_DECOMPOSITION, GENERATE_LENS)
                .build();

        GTQTMaterials.StrontiumCarbonate = new Material.Builder(getMaterialsId(), gregtechId("strontium_carbonate"))
                .dust()
                .color(0x1DAFD3)
                .iconSet(SAND)
                .components(Strontium, 1, Carbon, 1, Oxygen, 3)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.StrontiumOxide = new Material.Builder(getMaterialsId(), gregtechId("stronium_oxide"))
                .dust()
                .color(0x16839E)
                .iconSet(BRIGHT)
                .components(Strontium, 1, Oxygen, 1)
                .build();

        GTQTMaterials.AcidicPyrochlore = new Material.Builder(getMaterialsId(), gregtechId("acidic_pyrochlore"))
                .dust()
                .color(Pyrochlore.getMaterialRGB() + SulfuricAcid.getMaterialRGB())
                .iconSet(SHINY)
                .components(Pyrochlore, 1, SulfuricAcid, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.ThoriumUraniumSolution = new Material.Builder(getMaterialsId(), gregtechId("thorium_uranium_solution"))
                .fluid()
                .color(Thorium.getMaterialRGB() + Uranium235.getMaterialRGB())
                .iconSet(DULL)
                .build()
                .setFormula("?SO4", true);

        GTQTMaterials.LeachingPyrochlore = new Material.Builder(getMaterialsId(), gregtechId("leaching_pyrochlore"))
                .dust()
                .color(0xE2502C)
                .iconSet(BRIGHT)
                .build()
                .setFormula("(Nb2O5)9Ta2O5?", true);

        GTQTMaterials.BariumStrontiumRadiumSolution = new Material.Builder(getMaterialsId(), gregtechId("barium_strontium_radium_solution"))
                .fluid()
                .color(Barite.getMaterialRGB())
                .components(Barite, 1, Gypsum, 1, Celestite, 1, Radium, 1, Water, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.FluoroniobicAcid = new Material.Builder(getMaterialsId(), gregtechId("fluoroniobic_acid"))
                .liquid(new FluidBuilder().attributes(ACID))
                .color(Niobium.getMaterialRGB() + HydrofluoricAcid.getMaterialRGB())
                .components(Niobium, 1, Hydrogen, 1, Fluorine, 7)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.Oxypentafluoroniobate = new Material.Builder(getMaterialsId(), gregtechId("oxypentafluoroniobate"))
                .fluid()
                .color(0x17F742)
                .components(Hydrogen, 2, Niobium, 1, Oxygen, 1, Fluorine, 5)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.Heptafluorotantalate = new Material.Builder(getMaterialsId(), gregtechId("heptafluorotantalate"))
                .fluid()
                .color(0x16EB3F)
                .components(Hydrogen, 2, Tantalum, 1, Fluorine, 7)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.PotassiumFluoride = new Material.Builder(getMaterialsId(), gregtechId("potassium_fluoride"))
                .dust()
                .color(Potassium.getMaterialRGB() + Fluorine.getMaterialRGB())
                .iconSet(ROUGH)
                .components(Potassium, 1, Fluorine, 1)
                .build();

        GTQTMaterials.PotassiumFluoniobate = new Material.Builder(getMaterialsId(), gregtechId("potassium_fluoniobate"))
                .dust()
                .color(PotassiumFluoride.getMaterialRGB() + FluoroniobicAcid.getMaterialRGB())
                .iconSet(BRIGHT)
                .components(Potassium, 2, Niobium, 1, Fluorine, 7)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.PotassiumFluotantalate = new Material.Builder(getMaterialsId(), gregtechId("potassium_fluotantalate"))
                .dust()
                .color(Tantalum.getMaterialRGB() + PotassiumFluoniobate.getMaterialRGB())
                .iconSet(BRIGHT)
                .components(Potassium, 2, Tantalum, 1, Fluorine, 7)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.UraniumThoriumNitrate = new Material.Builder(getMaterialsId(), gregtechId("uranium_thorium_nitrate"))
                .dust()
                .color(Uranium238.getMaterialRGB() + Thorium.getMaterialRGB() + Nitrogen.getMaterialRGB())
                .iconSet(SHINY)
                .build()
                .setFormula("UO2(NO3)2Th(NO3)4", true);

        GTQTMaterials.UraniumOxideThoriumNitrate = new Material.Builder(getMaterialsId(), gregtechId("uranium_oxide_thorium_nitrate"))
                .dust()
                .color(Uranium238.getMaterialRGB() + Oxygen.getMaterialRGB())
                .iconSet(SHINY)
                .build()
                .setFormula("UO2Th(NO3)4", true);

        GTQTMaterials.ThoriumNitrateSolution = new Material.Builder(getMaterialsId(), gregtechId("thorium_nitrate_solution"))
                .fluid()
                .color(Thorium.getMaterialRGB())
                .build()
                .setFormula("Th(NO3)4(H2O)", true);

        GTQTMaterials.ThoriumOxide = new Material.Builder(getMaterialsId(), gregtechId("thorium_oxide"))
                .dust()
                .color(Thorium.getMaterialRGB() + Oxygen.getMaterialRGB())
                .components(Thorium, 1, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.TributylPhosphate = new Material.Builder(getMaterialsId(), gregtechId("tributyl_phosphate"))
                .fluid()
                .color(0xBED323)
                .components(Carbon, 12, Hydrogen, 27, Phosphorus, 1, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(C4H9)3PO4", true);

        GTQTMaterials.MesitylOxide = new Material.Builder(getMaterialsId(), gregtechId("mesityl_oxide"))
                .fluid()
                .color(0x783E50)
                .components(Carbon, 6, Hydrogen, 10, Oxygen, 1)
                .build();

        GTQTMaterials.MethylIsobutylKetone = new Material.Builder(getMaterialsId(), gregtechId("methyl_isobutyl_ketone"))
                .fluid()
                .color(0x2F5687)
                .components(Carbon, 6, Hydrogen, 12, Oxygen, 1)
                .build();

        GTQTMaterials.TBPMIBKSolution = new Material.Builder(getMaterialsId(), gregtechId("tbp_mibk_solutione"))
                .fluid()
                .color(0xBED323)
                .components(TributylPhosphate, 1, MethylIsobutylKetone, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.DimethylCarbonate = new Material.Builder(getMaterialsId(), gregtechId("dimethyl_carbonate"))
                .fluid()
                .color(0xC5EB9E)
                .components(Carbon, 3, Hydrogen, 6, Oxygen, 3)
                .build()
                .setFormula("(CH3O)2CO", true);

        GTQTMaterials.DimethylamineHydrochloride = new Material.Builder(getMaterialsId(), gregtechId("dimethylamine_hydrochloride"))
                .fluid()
                .color(0xE3EBDC)
                .components(Dimethylamine, 1, HydrochloricAcid, 1)
                .build()
                .setFormula("C2H8NCl", true);

        GTQTMaterials.PotassiumFormate = new Material.Builder(getMaterialsId(), gregtechId("potassium_formate"))
                .dust()
                .color(0x74B5A9)
                .components(Carbon, 1, Hydrogen, 3, Oxygen, 1, Potassium, 1)
                .build();

        GTQTMaterials.LiquidNitrogen = new Material.Builder(getMaterialsId(), gregtechId("liquid_nitrogen"))
                .liquid(new FluidBuilder().temperature(77))
                .color(0x00BFC1)
                .flags(DISABLE_DECOMPOSITION)
                .components(Nitrogen,2)
                .build();

        GTQTMaterials.LiquidHelium = new Material.Builder(getMaterialsId(), gregtechId("liquid_helium"))
                .liquid(new FluidBuilder().temperature(2))
                .color(0x00BFC1)
                .flags(DISABLE_DECOMPOSITION)
                .components(Helium,1)
                .build();

        GTQTMaterials.LiquidHydrogen = new Material.Builder(getMaterialsId(), gregtechId("liquid_hydrogen"))
                .liquid(new FluidBuilder().temperature(20))
                .color(0x0000B5)
                .flags(DISABLE_DECOMPOSITION)
                .components(Hydrogen,2)
                .build();

        GTQTMaterials.LiquidArgon = new Material.Builder(getMaterialsId(), gregtechId("liquid_argon"))
                .liquid(new FluidBuilder().temperature(84))
                .color(0x00FF00)
                .flags(DISABLE_DECOMPOSITION)
                .components(Argon,1)
                .build();

        GTQTMaterials.LiquidRadon = new Material.Builder(getMaterialsId(), gregtechId("liquid_radon"))
                .liquid(new FluidBuilder().temperature(211))
                .color(0xFF39FF)
                .flags(DISABLE_DECOMPOSITION)
                .components(Radon,1)
                .build();

        GTQTMaterials.LiquidCarbonDioxide = new Material.Builder(getMaterialsId(), gregtechId("liquid_carbon_dioxide"))
                .liquid(new FluidBuilder().temperature(211))
                .color(0xFFA9FF)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon,1,Oxygen,2)
                .build();

        GTQTMaterials.SodiumTungstate = new Material.Builder(getMaterialsId(), gregtechId("sodium_tungstate"))
                .fluid()
                .color(0x595E54)
                .components(Sodium, 1, Tungsten, 1, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.SodiumPhosphotungstate = new Material.Builder(getMaterialsId(), gregtechId("sodium_phosphotungstate"))
                .dust()
                .color(0x4D3635)
                .components(Oxygen, 40, Tungsten, 12, Sodium, 3, Phosphorus, 1)
                .flags(DECOMPOSITION_BY_ELECTROLYZING)
                .build()
                .setFormula("(WO3)12Na3PO4", true);

        GTQTMaterials.SodiumMolybdate = new Material.Builder(getMaterialsId(), gregtechId("sodium_molybdate"))
                .dust()
                .color(0xCCCC99)
                .iconSet(ROUGH)
                .components(Sodium, 2, Molybdenum, 1, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.FluxedElectrum = new Material.Builder(getMaterialsId(), gregtechId("fluxed_electrum"))
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

        //净化水产线
        GTQTMaterials.CleanWater_1 = new Material.Builder(getMaterialsId(), gregtechId("clean_water_1"))
                .fluid()
                .color(0x1874CD)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("H2O", true);

        GTQTMaterials.CleanWater_2 = new Material.Builder(getMaterialsId(), gregtechId("clean_water_2"))
                .fluid()
                .color(0x1874CD)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("H2O", true);

        GTQTMaterials.CleanWater_3 = new Material.Builder(getMaterialsId(), gregtechId("clean_water_3"))
                .fluid()
                .color(0x1874CD)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("H2O", true);

        GTQTMaterials.CleanWater_4 = new Material.Builder(getMaterialsId(), gregtechId("clean_water_4"))
                .fluid()
                .color(0x1874CD)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("H2O", true);

        GTQTMaterials.CleanWater_5 = new Material.Builder(getMaterialsId(), gregtechId("clean_water_5"))
                .fluid()
                .color(0x1874CD)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("H2O", true);

        GTQTMaterials.CleanWater_6 = new Material.Builder(getMaterialsId(), gregtechId("clean_water_6"))
                .fluid()
                .color(0x1874CD)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("H2O", true);

        GTQTMaterials.CleanWater_7 = new Material.Builder(getMaterialsId(), gregtechId("clean_water_7"))
                .fluid()
                .color(0x1874CD)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("H2O", true);

        GTQTMaterials.CleanWater_8 = new Material.Builder(getMaterialsId(), gregtechId("clean_water_8"))
                .fluid()
                .color(0x1874CD)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("H2O", true);

        GTQTMaterials.BismuthVanadate = new Material.Builder(getMaterialsId(), gregtechId("bismuth_vanadate"))
                .dust()
                .color(0xFFAF33)
                .iconSet(SHINY)
                .components(Bismuth, 1, Vanadium, 1, Oxygen, 4)
                .build();

        GTQTMaterials.MaleicAnhydride = new Material.Builder(getMaterialsId(), gregtechId("maleic_anhydride"))
                .liquid()
                .color(0x610C2F)
                .components(Carbon, 4, Hydrogen, 2, Oxygen, 3)
                .build();

        GTQTMaterials.NickelOxideHydroxide = new Material.Builder(getMaterialsId(), gregtechId("nickel_oxide_hydroxide"))
                .dust()
                .color(0xa2f2a2)
                .iconSet(METALLIC)
                .build()
                .setFormula("NiO(OH)",true);

        GTQTMaterials.LithiumCarbonateSolution = new Material.Builder(getMaterialsId(), gregtechId("lithium_carbonate_solution"))
                .liquid()
                .color((Lithium.getMaterialRGB() + Carbon.getMaterialRGB() + Oxygen.getMaterialRGB()) / 3)
                .iconSet(FLUID)
                .build()
                .setFormula("Li2CO3(H2O)", true);

        GTQTMaterials.LithiumCobaltOxide = new Material.Builder(getMaterialsId(), gregtechId("lithium_cobalt_oxide"))
                .dust()
                .color(0xd2a4f3)
                .iconSet(SHINY)
                .build()
                .setFormula("LiCoO",true);

        GTQTMaterials.BariumTriflate = new Material.Builder(getMaterialsId(), gregtechId("barium_triflate"))
                .dust()
                .color((Barium.getMaterialRGB() + Fluorine.getMaterialRGB()) / 2)
                .iconSet(SHINY)
                .build()
                .setFormula("Ba(OSO2CF3)2", true);

        GTQTMaterials.LithiumTriflate = new Material.Builder(getMaterialsId(), gregtechId("lithium_triflate"))
                .dust()
                .color(0xe2dae3)
                .iconSet(SHINY)
                .build()
                .setFormula("LiCSO3F3",true);

        GTQTMaterials.Xylose = new Material.Builder(getMaterialsId(), gregtechId("xylose"))
                .dust()
                .color(0xd2a4f3)
                .iconSet(ROUGH)
                .build()
                .setFormula("C5H10O5",true);

        GTQTMaterials.SodiumAlginateSolution = new Material.Builder(getMaterialsId(), gregtechId("sodium_alginate_solution"))
                .liquid()
                .color(0xca8642)
                .iconSet(FLUID)
                .build()
                .setFormula("NaC6H7O6",true);

        GTQTMaterials.CalciumAlginate = new Material.Builder(getMaterialsId(), gregtechId("calcium_alginate"))
                .dust()
                .color(0x654321)
                .iconSet(ROUGH)
                .build()
                .setFormula("CaC12H14O12",true);

        GTQTMaterials.Trimethylsilane = new Material.Builder(getMaterialsId(), gregtechId("trimethylsilane"))
                .liquid()
                .color(0xd2a4f3)
                .iconSet(FLUID)
                .build()
                .setFormula("C3H10Si", true);

        GTQTMaterials.CetaneTrimethylAmmoniumBromide = new Material.Builder(getMaterialsId(), gregtechId("cetane_trimethyl_ammonium_bromide"))
                .liquid()
                .color(0xb9c1c9)
                .iconSet(FLUID)
                .build()
                .setFormula("C19H42BrN",true);

        GTQTMaterials.SiliconNanoparticles = new Material.Builder(getMaterialsId(), gregtechId("silicon_nanoparticles"))
                .dust()
                .color(Silicon.getMaterialRGB())
                .iconSet(SHINY)
                .build()
                .setFormula("Si?",true);

        GTQTMaterials.Glucose = new Material.Builder(getMaterialsId(), gregtechId("glucose"))
                .dust()
                .color((Sugar.getMaterialRGB() + 5))
                .iconSet(ROUGH)
                .build()
                .setFormula("C6H12O6", true);

        GTQTMaterials.StreptococcusPyogenes = new Material.Builder(getMaterialsId(), gregtechId("streptococcus_pyogenes"))
                .dust()
                .color(0x1c3b15)
                .iconSet(ROUGH)
                .build()
                .setFormula("Bacteria", true);

        GTQTMaterials.Sorbose = new Material.Builder(getMaterialsId(), gregtechId("sorbose"))
                .dust()
                .color(Glucose.getMaterialRGB())
                .iconSet(DULL)
                .build()
                .setFormula("C6H12O6",true);

        GTQTMaterials.AscorbicAcid = new Material.Builder(getMaterialsId(), gregtechId("ascorbic_acid"))
                .liquid(new FluidBuilder().attribute(ACID))
                .color(0xe6cd00)
                .iconSet(FLUID)
                .build()
                .setFormula("C6H8O6",true);

        GTQTMaterials.DehydroascorbicAcid = new Material.Builder(getMaterialsId(), gregtechId("dehydroascorbic_acid"))
                .liquid(new FluidBuilder().attribute(ACID))
                .color(0xe6cd00)
                .iconSet(FLUID)
                .build()
                .setFormula("C6H6O6",true);

        GTQTMaterials.GalliumChloride = new Material.Builder(getMaterialsId(), gregtechId("gallium_chloride"))
                .dust()
                .color(0x92867a)
                .iconSet(ROUGH)
                .build()
                .setFormula("GaCl3",true);

        GTQTMaterials.Halloysite = new Material.Builder(getMaterialsId(), gregtechId("halloysite"))
                .dust()
                .color(0x23423a)
                .iconSet(ROUGH)
                .build()
                .setFormula("Al9Si10O50Ga",true);

        GTQTMaterials.SulfurCoatedHalloysite = new Material.Builder(getMaterialsId(), gregtechId("sulfur_coated_halloysite"))
                .dust()
                .color(0x23973a)
                .iconSet(ROUGH)
                .build()
                .setFormula("S2C2(Al9Si10O50Ga)",true);

        GTQTMaterials.FluorideBatteryElectrolyte = new Material.Builder(getMaterialsId(), gregtechId("fluoride_battery_electrolyte"))
                .dust()
                .color(0x9a628a)
                .iconSet(SHINY)
                .build()
                .setFormula("La9BaF29(C8H7F)",true);

        GTQTMaterials.LanthanumNickelOxide = new Material.Builder(getMaterialsId(), gregtechId("lanthanum_nickel_oxide"))
                .dust()
                .color(0x23973a)
                .iconSet(SHINY)
                .build()
                .setFormula("La2NiO4",true);


        GTQTMaterials.AbyssalAlloy = new Material.Builder(getMaterialsId(), gregtechId("abyssal_alloy"))
                .ingot(6).liquid()
                .color(0x9E706A)
                .iconSet(METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.UHV], 2, 8)
                .components(StainlessSteel, 5, TungstenCarbide, 5, Nichrome, 5, Bronze, 5, IncoloyMA956, 5, Iodine, 1, Germanium, 1, Radon, 1)
                .blast(9625)
                .build();

        GTQTMaterials.NaquadriaticTaranium = new Material.Builder(getMaterialsId(), gregtechId("naquadriatic_taranium"))
                .ingot()
                .color((Naquadria.getMaterialRGB() + Taranium.getMaterialRGB()) / 2)
                .iconSet(SHINY)
                .flags(STD_METAL, GENERATE_LONG_ROD)
                .components(Naquadria, 1, Taranium, 1)
                .cableProperties(GTValues.V[GTValues.UXV], 2, 32)
                .blast(11200)
                .build();

        GTQTMaterials.CarbonSulfide = new Material.Builder(getMaterialsId(), gregtechId("carbon_sulfide"))
                .liquid()
                .color(0x40ffbf)
                .iconSet(FLUID)
                .build()
                .setFormula("CS2", true);

        GTQTMaterials.Biperfluoromethanedisulfide = new Material.Builder(getMaterialsId(), gregtechId("biperfluoromethanedisulfide"))
                .liquid()
                .color(0x3ada40)
                .iconSet(FLUID)
                .build()
                .setFormula("C2F6S2", true);

        GTQTMaterials.BariumTriflateSolution = new Material.Builder(getMaterialsId(), gregtechId("barium_triflate_solution"))
                .liquid()
                .color((Barium.getMaterialRGB() + Fluorine.getMaterialRGB()) / 2)
                .iconSet(FLUID)
                .build()
                .setFormula("(H2O)3(Hg)C2BaF6O6S2", true);

        GTQTMaterials.Caliche = new Material.Builder(getMaterialsId(), gregtechId("caliche"))
                .dust(3)
                .color(0xeb9e3f).ore()
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(DULL)
                .components(SodiumNitrate, 1, Potassium, 1, Nitrogen, 1, Oxygen, 3, RockSalt, 1, Sodium, 1, Iodine, 1, Oxygen, 3)
                .build();

        GTQTMaterials.CalicheIodateBrine = new Material.Builder(getMaterialsId(), gregtechId("caliche_iodate_brine"))
                .liquid()
                .color(0xffe6660)
                .iconSet(FLUID)
                .build()
                .setFormula("(H2O)NaNO3KNO3KClNaIO3", true);

        GTQTMaterials.IodideSolution = new Material.Builder(getMaterialsId(), gregtechId("iodide_solution"))
                .liquid()
                .color(0x08081c)
                .iconSet(FLUID)
                .build()
                .setFormula("(H2O)NaNO3KNO3KClNaI", true);

        GTQTMaterials.CalicheIodineBrine = new Material.Builder(getMaterialsId(), gregtechId("caliche_iodine_brine"))
                .liquid()
                .color(0xffe6660)
                .iconSet(FLUID)
                .build()
                .setFormula("(H2O)NaNO3KNO3KClNaOHI", true);

        GTQTMaterials.CalicheNitrateSolution = new Material.Builder(getMaterialsId(), gregtechId("caliche_nitrate_solution"))
                .liquid()
                .color(0xffe6660)
                .iconSet(FLUID)
                .build()
                .setFormula("(H2O)NaNO3KNO3KClNaOH", true);

        GTQTMaterials.KeroseneIodineSolution = new Material.Builder(getMaterialsId(), gregtechId("kerosene_iodine_solution"))
                .liquid()
                .color(0x08081c)
                .iconSet(FLUID)
                .build()
                .setFormula("C12H26I", true);

        GTQTMaterials.IodizedOil = new Material.Builder(getMaterialsId(), gregtechId("iodized_oil"))
                .liquid()
                .color(0x666666)
                .iconSet(FLUID)
                .build();

        GTQTMaterials.IodizedBrine = new Material.Builder(getMaterialsId(), gregtechId("iodized_brine"))
                .liquid()
                .color(0x525242)
                .iconSet(FLUID)
                .build()
                .setFormula("I?", true);

        GTQTMaterials.IodineBrineMix = new Material.Builder(getMaterialsId(), gregtechId("iodine_brine_mix"))
                .liquid()
                .color(0x525242)
                .iconSet(FLUID)
                .build()
                .setFormula("I??", true);

        GTQTMaterials.IodineSlurry = new Material.Builder(getMaterialsId(), gregtechId("iodine_slurry"))
                .liquid()
                .color(0x08081c)
                .iconSet(FLUID)
                .build()
                .setFormula("I?", true);

        GTQTMaterials.Kerosene = new Material.Builder(getMaterialsId(), gregtechId("kerosene"))
                .liquid()
                .color(0xD570D5)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        GTQTMaterials.Niter = new Material.Builder(getMaterialsId(), gregtechId("niter"))
                .dust(1)
                .color(16763080)
                .flags(NO_SMASHING, NO_SMELTING)
                .iconSet(FLINT)
                .components(Saltpeter, 1)
                .build();

        Cellulose = new Material.Builder(getMaterialsId(), gregtechId("cellulose"))
                .dust()
                .color(0xfefefc)
                .iconSet(DULL)
                .build()
                .setFormula("C6H10O5", true);

        TriniumTrioxide = new Material.Builder(getMaterialsId(), gregtechId("trinium_trioxide"))
                .dust()
                .color(0xC037C5)
                .iconSet(METALLIC)
                .components(Trinium, 2, Oxygen, 3)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.SodiumThiocyanate = new Material.Builder(getMaterialsId(), gregtechId("sodium_thiocyanate"))
                .liquid()
                .color((Sodium.getMaterialRGB() + Sulfur.getMaterialRGB()) / 2)
                .iconSet(FLUID)
                .build()
                .setFormula("NaSCN", true);

        PolyacrylonitrileSolution = new Material.Builder(getMaterialsId(), gregtechId("polyacrylonitrile_solution"))
                .liquid()
                .color(0x9999ff)
                .iconSet(FLUID)
                .build()
                .setFormula("(C3H3N)n(NaSCN)", true);

        AcrylicFibers = new Material.Builder(getMaterialsId(), gregtechId("acrylic_fibers"))
                .dust()
                .color(0xfdfdfb)
                .iconSet(FINE)
                .build()
                .setFormula("(C5O2H8)n", true);

        WetFormamide = new Material.Builder(getMaterialsId(), gregtechId("wet_formamide"))
                .liquid()
                .color(0x33CCFF)
                .iconSet(FLUID)
                .build()
                .setFormula("(H2O)CH3NO", true);

        Formamide = new Material.Builder(getMaterialsId(), gregtechId("formamide"))
                .liquid()
                .color(0x33CCFF)
                .iconSet(FLUID)
                .build()
                .setFormula("CH3NO", true);

        HydroxylamineDisulfate = new Material.Builder(getMaterialsId(), gregtechId("hydroxylamine_disulfate"))
                .liquid()
                .color(0x99add6)
                .iconSet(FLUID)
                .build()
                .setFormula("(NH3OH)2(NH4)2(SO4)2", true);

        Amidoxime = new Material.Builder(getMaterialsId(), gregtechId("amidoxime"))
                .liquid()
                .color(0x66ff33)
                .iconSet(FLUID)
                .build()
                .setFormula("H3N2O(CH)", true);

        PureUranylNitrateSolution = new Material.Builder(getMaterialsId(), gregtechId("pure_uranyl_nitrate"))
                .liquid()
                .color(0x33bd45)
                .iconSet(FLUID)
                .build()
                .setFormula("(H2O)UO2(NO3)2", true);

        UranylNitrate = new Material.Builder(getMaterialsId(), gregtechId("uranyl_nitrate"))
                .dust()
                .color(0x33bd45)
                .iconSet(SHINY)
                .build()
                .setFormula("UO2(NO3)2", true);

        DiluteNitricAcid = new Material.Builder(getMaterialsId(), gregtechId("dilute_nitric_acid"))
                .liquid()
                .color((NitricAcid.getMaterialRGB() + Water.getMaterialRGB()) / 2)
                .iconSet(FLUID)
                .build()
                .setFormula("(H2O)HNO3", true);

        ConcentratedBrine = new Material.Builder(getMaterialsId(), gregtechId("concentrated_brine"))
                .liquid()
                .color(0xfcfc95)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        BrominatedBrine = new Material.Builder(getMaterialsId(), gregtechId("brominated_brine"))
                .liquid()
                .color(0xfdd48d)
                .iconSet(FLUID)
                .build()
                .setFormula("Br?", true);

        AcidicBrominatedBrine = new Material.Builder(getMaterialsId(), gregtechId("acidic_brominated_brine"))
                .liquid(new FluidBuilder().attribute(ACID))
                .color(0xfdd48d)
                .iconSet(FLUID)
                .build()
                .setFormula("(H2SO4)Cl?", true);

        CalciumFreeBrine = new Material.Builder(getMaterialsId(), gregtechId("calcium_free_brine"))
                .liquid()
                .color(0xfcfca6)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        CalciumSalts = new Material.Builder(getMaterialsId(), gregtechId("calcium_salts"))
                .dust()
                .color(Calcium.getMaterialRGB() - 10)
                .iconSet(ROUGH)
                .components(Calcite, 1, Gypsum, 1)
                .build();

        SodiumFreeBrine = new Material.Builder(getMaterialsId(), gregtechId("sodium_free_brine"))
                .liquid()
                .color(0xfcfcb1)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        SodiumSalts = new Material.Builder(getMaterialsId(), gregtechId("sodium_salts"))
                .dust()
                .color(Sodium.getMaterialRGB() - 5)
                .iconSet(ROUGH)
                .build()
                .setFormula("NaCl?", true);

        PotassiumFreeBrine = new Material.Builder(getMaterialsId(), gregtechId("potassium_free_brine"))
                .liquid()
                .color(0xfcfcbc)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        PotassiumMagnesiumSalts = new Material.Builder(getMaterialsId(), gregtechId("kmg_salts"))
                .dust()
                .color(0xcacac8)
                .iconSet(ROUGH)
                .build()
                .setFormula("KClMg(SO4)K2(SO4)KF", true);

        BoronFreeSolution = new Material.Builder(getMaterialsId(), gregtechId("boron_free_solution"))
                .liquid()
                .color(0xfcfccd)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        CalciumMagnesiumSalts = new Material.Builder(getMaterialsId(), gregtechId("camg_salts"))
                .dust()
                .color(0xcacac8)
                .iconSet(ROUGH)
                .build()
                .setFormula("Ca(CO3)(Sr(CO3))(CO2)(MgO)", true);

        SodiumLithiumSolution = new Material.Builder(getMaterialsId(), gregtechId("sodium_lithium_solution"))
                .liquid()
                .color(0xfcfccd)
                .iconSet(FLUID)
                .build()
                .setFormula("NaLi?", true);

        SodiumAluminiumHydride = new Material.Builder(getMaterialsId(), gregtechId("sodium_aluminium_hydride"))
                .dust()
                .color(0x98cafc)
                .iconSet(ROUGH)
                .build()
                .setFormula("NaAlH4", true);

        Fructose = new Material.Builder(getMaterialsId(), gregtechId("fructose"))
                .dust()
                .color(0x98cafc)
                .iconSet(ROUGH)
                .build()
                .setFormula("C6H12O6", true);

        SodiumAzide = new Material.Builder(getMaterialsId(), gregtechId("sodium_azide"))
                .dust()
                .color((Sodium.getMaterialRGB() + Nitrogen.getMaterialRGB()) / 2)
                .iconSet(FINE)
                .build()
                .setFormula("NaN3", true);

        LithiumHydroxideSolution = new Material.Builder(getMaterialsId(), gregtechId("lithium_hydroxide_solution"))
                .liquid()
                .color((Lithium.getMaterialRGB() + Oxygen.getMaterialRGB() + Hydrogen.getMaterialRGB()) / 3)
                .iconSet(FLUID)
                .build()
                .setFormula("H2O)LiOH", true);

        Glucosamine = new Material.Builder(getMaterialsId(), gregtechId("glucosamine"))
                .dust()
                .color(0x98cafc)
                .iconSet(FINE)
                .build()
                .setFormula("C6H13NO5", true);

        Chitosan = new Material.Builder(getMaterialsId(), gregtechId("chitosan"))
                .liquid()
                .color(0xb1bd42)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        SodiumSulfateSolution = new Material.Builder(getMaterialsId(), gregtechId("sodium_sulfate_solution"))
                .liquid()
                .color((SodiumSulfate.getMaterialRGB() + 30))
                .iconSet(FLUID)
                .build()
                .setFormula("Na2SO4(H2O)", true);

        BoronOxide = new Material.Builder(getMaterialsId(), gregtechId("boron_oxide"))
                .dust()
                .color((Boron.getMaterialRGB() + Oxygen.getMaterialRGB()) / 2)
                .iconSet(ROUGH)
                .build()
                .setFormula("B2O3", true);

        LithiumAluminiumFluoride = new Material.Builder(getMaterialsId(), gregtechId("lithium_aluminium_fluoride"))
                .dust()
                .color((Lithium.getMaterialRGB() + Aluminium.getMaterialRGB() + Fluorine.getMaterialRGB()) / 3)
                .iconSet(ROUGH)
                .build()
                .setFormula("AlF4Li", true);

        //TODO
        getMaterialsId();//占位

        DimethylthiocarbamoilChloride = new Material.Builder(getMaterialsId(), gregtechId("dimethylthiocarbamoil_chloride"))
                .liquid()
                .color(0xd9ff26)
                .iconSet(FLUID)
                .build()
                .setFormula("(CH3)2NC(S)Cl", true);

        Mercaptophenol = new Material.Builder(getMaterialsId(), gregtechId("mercaptophenol"))
                .liquid()
                .color(0xbaaf18)
                .iconSet(FLUID)
                .build()
                .setFormula("C6H6OS", true);

        AmineMixture = new Material.Builder(getMaterialsId(), gregtechId("amine_mixture"))
                .liquid()
                .color((Methanol.getMaterialRGB() - 20 + Ammonia.getMaterialRGB() - 10) / 2)
                .iconSet(FLUID)
                .build()
                .setFormula("(NH3)CH4", true);

        SodiumMolybdate = new Material.Builder(getMaterialsId(), gregtechId("sodium_molybdate"))
                .dust()
                .color(0xfcfc00)
                .iconSet(ROUGH)
                .build()
                .setFormula("Na2MoO4", true);
        //TODO
        getMaterialsId();//占位

        SodiumTungstate = new Material.Builder(getMaterialsId(), gregtechId("sodium_tungstate"))
                .liquid()
                .color(0x7a7777)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .components(Sodium, 2, Tungsten, 1, Oxygen, 4)
                .build();

        //TODO
        getMaterialsId();//占位
        
        IridiumCyclooctadienylChlorideDimer = new Material.Builder(getMaterialsId(), gregtechId("iridium_cyclooctadienyl_chloride_dimer"))
                .dust()
                .color(0x888079)
                .iconSet(SHINY)
                .build()
                .setFormula("Ir2(C8H12)2Cl2", true);

        ChlorodiisopropylPhosphine = new Material.Builder(getMaterialsId(), gregtechId("chlorodiisopropyl_phosphine"))
                .liquid()
                .color(0xa2c122)
                .iconSet(FLUID)
                .build();
        
        AmmoniumPersulfate = new Material.Builder(getMaterialsId(), gregtechId("ammonium_persulfate"))
                .liquid()
                .color(0x6464f5)
                .iconSet(FLUID)
                .build()
                .setFormula("(NH4)2S2O8", true);

        PolystyreneNanoParticles = new Material.Builder(getMaterialsId(), gregtechId("polystryrene_nanoparticles"))
                .dust()
                .color(0x888079)
                .iconSet(FINE)
                .build()
                .setFormula("(C8H8)n", true);

        Celestine = new Material.Builder(getMaterialsId(), gregtechId("celestine"))
                .dust()
                .color(0x9db1b8)
                .iconSet(SHINY)
                .components(Strontium, 1, Sulfur, 1, Oxygen, 4)
                .build();

        MagnesiumSulfate = new Material.Builder(getMaterialsId(), gregtechId("magnesium_sulfate"))
                .dust()
                .color(0xcacac8)
                .iconSet(DULL)
                .build()
                .setFormula("MgSO4", true);

        StrontiumOxide = new Material.Builder(getMaterialsId(), gregtechId("strontium_oxide"))
                .dust()
                .color(0xcacac8)
                .iconSet(SHINY)
                .build()
                .setFormula("SrO", true);

        ChilledBrine = new Material.Builder(getMaterialsId(), gregtechId("chilled_brine"))
                .liquid()
                .color(0xfcfc95)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        MagnesiumContainingBrine = new Material.Builder(getMaterialsId(), gregtechId("magnesium_containing_brine"))
                .liquid()
                .color(0xfcfcbc)
                .iconSet(FLUID)
                .build()
                .setFormula("Mg?", true);

        GTQTMaterials.DebrominatedWater = new Material.Builder(getMaterialsId(), gregtechId("debrominated_brine"))
                .liquid()
                .color(0x0000ff)
                .iconSet(FLUID)
                .build()
                .setFormula("H2O", true);

        GTQTMaterials.SulfuricBromineSolution = new Material.Builder(getMaterialsId(), gregtechId("sulfuric_bromine_solution"))
                .liquid()
                .color(0xff5100)
                .iconSet(FLUID)
                .build()
                .setFormula("H2SO4Br(H2O)Cl2", true);

        GTQTMaterials.LithiumChlorideSolution = new Material.Builder(getMaterialsId(), gregtechId("lithium_chloride_solution"))
                .liquid()
                .color((Lithium.getMaterialRGB() + Chlorine.getMaterialRGB()))
                .iconSet(FLUID)
                .build()
                .setFormula("LiCl(H2O)", true);

        GTQTMaterials.AluminiumHydride= new Material.Builder(getMaterialsId(), gregtechId("aluminium_hydride"))
                .dust()
                .color(0x0b585c)
                .iconSet(ROUGH)
                .build()
                .setFormula("AlH3",true);

        GTQTMaterials.SodiumHydride= new Material.Builder(getMaterialsId(), gregtechId("sodium_hydride"))
                .dust()
                .color(0xcacac8)
                .iconSet(ROUGH)
                .build()
                .setFormula("NaH",true);

        GTQTMaterials.LithiumAluminiumHydride= new Material.Builder(getMaterialsId(), gregtechId("lithium_aluminium_hydride"))
                .dust()
                .color(0xc0defc)
                .iconSet(ROUGH)
                .build()
                .setFormula("LiAlH4",true);

        GTQTMaterials.SodiumAzanide = new Material.Builder(getMaterialsId(), gregtechId("sodium_azanide"))
                .dust()
                .color((Sodium.getMaterialRGB() + Hydrogen.getMaterialRGB() + Nitrogen.getMaterialRGB()) / 3)
                .iconSet(FINE)
                .build()
                .setFormula("NaNH2", true);

        GTQTMaterials.SodiumHydroxideSolution = new Material.Builder(getMaterialsId(), gregtechId("sodium_hydroxide_solution"))
                .liquid()
                .color(SodiumHydroxide.getMaterialRGB() + 50)
                .iconSet(FLUID)
                .build()
                .setFormula("(H2O)NaOH", true);

        GTQTMaterials.BoronFluoride = new Material.Builder(getMaterialsId(), gregtechId("boron_fluoride"))
                .liquid()
                .color(0xD5D2D7)
                .iconSet(FLUID)
                .build()
                .setFormula("BF3",true);

        GTQTMaterials.IsopropylAlcohol = new Material.Builder(getMaterialsId(), gregtechId("isopropyl_alcohol"))
                .liquid()
                .color((Water.getMaterialRGB() + Propene.getMaterialRGB()) / 2)
                .iconSet(FLUID)
                .build()
                .setFormula("C3H8O", true);

        GTQTMaterials.BerylliumFluoride= new Material.Builder(getMaterialsId(), gregtechId("beryllium_fluoride"))
                .ingot(2)
                .color(0x757575)
                .iconSet(SHINY)
                .components(Beryllium, 1, Fluorine, 2)
                .build();

        GTQTMaterials.Oct1ene = new Material.Builder(getMaterialsId(), gregtechId("1_octene"))
                .liquid()
                .color(0x7e8778)
                .iconSet(FLUID)
                .build()
                .setFormula("C8H16", true);

        GTQTMaterials.Ozone = new Material.Builder(getMaterialsId(), gregtechId("ozone"))
                .gas()
                .color(0x0099FF)
                .iconSet(FLUID)
                .build()
                .setFormula("O3", true);

        GTQTMaterials.NitrogenPentoxide = new Material.Builder(getMaterialsId(), gregtechId("nitrogen_pentoxide"))
                .liquid()
                .color(0x0033C0)
                .iconSet(FLUID)
                .build()
                .setFormula("N2O5",true);

        GTQTMaterials.TitaniumNitrate = new Material.Builder(getMaterialsId(), gregtechId("titanium_nitrate"))
                .dust()
                .color(0xFF0066)
                .iconSet(FINE)
                .build()
                .setFormula("TiNO3",true);

        GTQTMaterials.LithiumTitanate = new Material.Builder(getMaterialsId(), gregtechId("lithium_titanate"))
                .ingot(5)
                .color(0xfe71a9)
                .iconSet(SHINY)
                .flags(GENERATE_PLATE)
                .components(Lithium, 2, Titanium, 1, Oxygen, 3)
                .build();

        GTQTMaterials.AcidicSaltWater = new Material.Builder(getMaterialsId(), gregtechId("acidic_salt_water"))
                .liquid(new FluidBuilder().attribute(ACID))
                .color(0x006960)
                .iconSet(FLUID)
                .build()
                .setFormula("H2SO4(NaCl)3(H2O)3Cl2", true);

        GTQTMaterials.HotVapourMixture = new Material.Builder(getMaterialsId(), gregtechId("hot_vapour_mixture"))
                .gas()
                .color(0xff5100)
                .iconSet(FLUID)
                .build()
                .setFormula("H2SO4Br(H2O)2Cl2", true);

        GTQTMaterials.DampBromine = new Material.Builder(getMaterialsId(), gregtechId("damp_bromine"))
                .liquid()
                .color(0xe17594)
                .iconSet(FLUID)
                .build()
                .setFormula("Br(H2O)", true);

        GTQTMaterials.EleAcid = new Material.Builder(getMaterialsId(), gregtechId("ele_acid"))
                .fluid()
                .color(0xe04800)
                .build();

        GTQTMaterials.PotassiumChloride = new Material.Builder(getMaterialsId(), gregtechId("potassium_chloride"))
                .fluid()
                .components(Potassium,1,Chlorine,1)
                .color(0xe04800)
                .build();

        GTQTMaterials.ManganeseIronArsenicPhosphide = new Material.Builder(getMaterialsId(), gregtechId("manganese_iron_arsenic_phosphide"))
                .ingot()
                .color(0x03FCF0).iconSet(MaterialIconSet.METALLIC)
                .cableProperties(GTValues.V[4], 2, 4)
                .components(Manganese, 2, Iron, 2, Arsenic, 1, Phosphorus, 1)
                .blast(2100, BlastProperty.GasTier.LOW)
                .build();

        GTQTMaterials.PraseodymiumNickel = new Material.Builder(getMaterialsId(), gregtechId("praseodymium_nickel"))
                .ingot()
                .color(0x03BAFC).iconSet(MaterialIconSet.METALLIC)
                .cableProperties(GTValues.V[4], 2, 4)
                .components(Praseodymium, 5, Nickel, 1)
                .blast(2100, BlastProperty.GasTier.MID)
                .build();

        GTQTMaterials.GadoliniumSiliconGermanium = new Material.Builder(getMaterialsId(), gregtechId("gadolinium_silicon_germanium"))
                .ingot()
                .color(0x0388FC).iconSet(MaterialIconSet.SHINY)
                .cableProperties(GTValues.V[4], 2, 4)
                .components(Gadolinium, 5, Silicon, 2, Germanium, 2)
                .blast(2100, BlastProperty.GasTier.HIGH)
                .build();

        //
        GTQTMaterials.SuperGlue = new Material.Builder(getMaterialsId(), gregtechId("super_glue"))
                .fluid()
                .color(0xFAFAD2)
                .build();

        GTQTMaterials.UltraGlue = new Material.Builder(getMaterialsId(), gregtechId("ultra_glue"))
                .fluid()
                .color(0xB2DFEE)
                .build();

        //氰乙酸
        GTQTMaterials.CyanoaceticAcid = new Material.Builder(getMaterialsId(), gregtechId("cyanoacetic_acid"))
                .dust()
                .color(0xCD00CD)
                .components(Carbon, 3, Hydrogen, 3, Nitrogen, 1,Oxygen,2)
                .build();

        //氰乙酸乙酯
        GTQTMaterials.Ethylcyanoacetate = new Material.Builder(getMaterialsId(), gregtechId("ethylcyanoacetate"))
                .fluid()
                .color(0xCDCD00)
                .components(Carbon, 5, Hydrogen, 7, Nitrogen, 1,Oxygen,2)
                .build();

        //氰基丙烯酸酯聚合物
        GTQTMaterials.Cyanoacrylate = new Material.Builder(getMaterialsId(), gregtechId("cyanoacrylate"))
                .fluid()
                .color(0xB4CDCD)
                .components(Carbon, 5, Hydrogen, 7, Nitrogen, 1,Oxygen,2)
                .build();

        GTQTMaterials.BacterialGrowthMedium = new Material.Builder(getMaterialsId(), gregtechId("bacterial_growth_medium"))
                .liquid()
                .color(0x0b2e12)
                .build()
                .setFormula("For Bacteria", true);

        GTQTMaterials.DepletedGrowthMedium = new Material.Builder(getMaterialsId(), gregtechId("depleted_growth_medium"))
                .liquid()
                .color(0x071209)
                .build()
                .setFormula("Depleted", true);

        GTQTMaterials.Shewanella = new Material.Builder(getMaterialsId(), gregtechId("shewanella"))
                .dust()
                .color(0x8752ab)
                .iconSet(METALLIC)
                .build()
                .setFormula("Bacteria", true);

        GTQTMaterials.BrevibacteriumFlavium = new Material.Builder(getMaterialsId(), gregtechId("brevibacterium_flavium"))
                .dust()
                .color(0x2c4d24)
                .iconSet(ROUGH)
                .build()
                .setFormula("Bacteria", true);

        GTQTMaterials.BifidobacteriumBreve = new Material.Builder(getMaterialsId(), gregtechId("bifidobacterium_breve"))
                .dust()
                .color(0x377528)
                .iconSet(ROUGH)
                .build()
                .setFormula("Bacteria", true);

        GTQTMaterials.EschericiaColi = new Material.Builder(getMaterialsId(), gregtechId("eschericia_coli"))
                .dust()
                .color(0x2d4228)
                .iconSet(ROUGH)
                .build()
                .setFormula("Bacteria", true);

        GTQTMaterials.CupriavidusNecator = new Material.Builder(getMaterialsId(), gregtechId("cupriavidus_necator"))
                .dust()
                .color(0x22704f)
                .iconSet(ROUGH)
                .build()
                .setFormula("Bacteria", true);

        GTQTMaterials.SelectivelyMutatedCupriavidiusNecator = new Material.Builder(getMaterialsId(), gregtechId("selectively_mutated_cupriavidius_necator"))
                .dust()
                .iconSet(SHINY)
                .color(0xe04800)
                .build()
                .setFormula("Bacteria", true);
        //ppb
        GTQTMaterials.PPB = new Material.Builder(getMaterialsId(), gregtechId("ppb"))
                .ingot()
                .color(0xFF6347)
                .flags(GENERATE_FOIL,DISABLE_DECOMPOSITION,GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .components(Palladium,1,Platinum,1,Bismuth,1)
                .blast(4500,MID)
                .build();

        GTQTMaterials.PPBFront = new Material.Builder(getMaterialsId(), gregtechId("ppbfront"))
                .fluid()
                .color(0xFFC125)
                .flags(DISABLE_DECOMPOSITION)
                .components(Palladium,1,Platinum,1,Bismuth,1)
                .build();
        //氧化铋
        GTQTMaterials.BismuthOxygen = new Material.Builder(getMaterialsId(), gregtechId("bismuth_oxygen"))
                .dust()
                .color(0xC0FF3E)
                .flags(DISABLE_DECOMPOSITION)
                .components(Bismuth,2,Oxygen, 3)
                .build();
        //乙酸铋
        GTQTMaterials.BismuthAcetate = new Material.Builder(getMaterialsId(), gregtechId("bismuth_acetate"))
                .fluid()
                .color(0x9AFF9A)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 6, Hydrogen, 9, Bismuth,1,Oxygen, 2)
                .build();

        //乙酸丙酯
        GTQTMaterials.PropylAcetate = new Material.Builder(getMaterialsId(), gregtechId("propyl_acetate"))
                .fluid()
                .color(0x8B7355)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 5, Hydrogen, 10,Oxygen, 2)
                .build();
        //乙酰丙酮
        GTQTMaterials.Acetylacetone = new Material.Builder(getMaterialsId(), gregtechId("acetylacetone"))
                .fluid()
                .color(0x8DB6CD)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 5, Hydrogen, 8,Oxygen, 2)
                .build();
        //乙酰丙酮铂
        GTQTMaterials.PlatinumBis = new Material.Builder(getMaterialsId(), gregtechId("platinum_bis"))
                .fluid()
                .color(0x7AC5CD)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 10, Hydrogen, 14,Oxygen, 4,Platinum,1)
                .build();
        //乙酰丙酮钯
        GTQTMaterials.PalladiumBis = new Material.Builder(getMaterialsId(), gregtechId("palladium_bis"))
                .fluid()
                .color(0x76EE00)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 10, Hydrogen, 14,Oxygen, 4,Palladium,1)
                .build();

        //玻璃釉
        GTQTMaterials.GlassGlaze = new Material.Builder(getMaterialsId(), gregtechId("glass_glaze"))
                .fluid()
                .color(0x76EE00)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //肉桂酸
        GTQTMaterials.CinnamicAcid = new Material.Builder(getMaterialsId(), gregtechId("cinnamic_acid"))
                .fluid()
                .color(0x8E8E38)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C6H5CH=CHCOOH", true);

        //肉桂酸乙酯
        GTQTMaterials.EthylCinnamate = new Material.Builder(getMaterialsId(), gregtechId("ethyl_cinnamate"))
                .fluid()
                .color(0xA52A2A)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C6H5CH=CHCOOCH3", true);

        //肉桂酸钠
        GTQTMaterials.SodiumCinnamate = new Material.Builder(getMaterialsId(), gregtechId("sodium_cinnamate"))
                .fluid()
                .color(0xA0522D)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C6H5CH=CHCOONa", true);

        //乙二酰氯
        GTQTMaterials.OxalylChloride = new Material.Builder(getMaterialsId(), gregtechId("oxalyl_chloride"))
                .fluid()
                .color(0xBCEE68)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 1,Oxygen,1, Chlorine,2)
                .build()
                .setFormula("COCl2", true);

        //五氯化磷
        GTQTMaterials.PhosphorusPentachloride = new Material.Builder(getMaterialsId(), gregtechId("phosphorus_pentachloride"))
                .dust()
                .color(0xAB82FF)
                .flags(DISABLE_DECOMPOSITION)
                .components(Phosphorus, 1, Chlorine,5)
                .build();

        //三氯氧磷
        GTQTMaterials.PhosphorusOxychloride = new Material.Builder(getMaterialsId(), gregtechId("phosphorus_oxychloride"))
                .fluid()
                .color(0xBF3EFF)
                .flags(DISABLE_DECOMPOSITION)
                .components(Phosphorus, 1,Oxygen,1, Chlorine,3)
                .build();

        //肉桂酰氯
        GTQTMaterials.TransPhenylacryloylChloride = new Material.Builder(getMaterialsId(), gregtechId("trans_phenylacryloyl_chloride"))
                .fluid()
                .color(0xC1FFC1)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 9,Hydrogen,7,Chlorine,1, Oxygen,1)
                .build();

        //聚乙烯醇
        GTQTMaterials.PolyvinylAlcoholVinylalcoholPolymer = new Material.Builder(getMaterialsId(), gregtechId("polyvinyl_alcohol_vinylalcohol_polymer"))
                .fluid().dust()
                .color(0xEE9A00)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("[C2H4O]n", true);

        //吡啶
        GTQTMaterials.Pyridine = new Material.Builder(getMaterialsId(), gregtechId("pyridine"))
                .fluid()
                .color(0xE0FFFF)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 5,Hydrogen,5,Nitrogen,1)
                .build();

        //超能硅岩化镓
        GTQTMaterials.NaquadriaGalliumIndium = new Material.Builder(getMaterialsId(), gregtechId("naquadria_gallium_indium"))
                .dust()
                .color(0x71C671)
                .iconSet(SHINY)
                .components(Naquadria,1,Gallium,1,Indium,1)
                .build();

        //  11157 Lithium Niobate
        GTQTMaterials.LithiumNiobate = new Material.Builder(getMaterialsId(), gregtechId("lithium_niobate"))
                .ingot()
                .color(0xD27700)
                .iconSet(SHINY)
                .components(Lithium, 1, Niobium, 1, Oxygen, 4)
                .blast(6700)
                .flags(DISABLE_DECOMPOSITION)
                .flags(GENERATE_PLATE, GENERATE_LENS)
                .build();

        //  11156 Niobium Pentachloride
        GTQTMaterials.NiobiumPentachloride = new Material.Builder(getMaterialsId(), gregtechId("niobium_pentachloride"))
                .dust()
                .color(Niobium.getMaterialRGB() + Chlorine.getMaterialRGB())
                .iconSet(SHINY)
                .components(Niobium, 1, Chlorine, 5)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11164 Lu-Tm-Y Chlorides Solution
        GTQTMaterials.LuTmYChloridesSolution = new Material.Builder(getMaterialsId(), gregtechId("lu_tm_y_chlorides_solution"))
                .liquid()
                .color(Lutetium.getMaterialRGB() + Thulium.getMaterialRGB() + Yttrium.getMaterialRGB())
                .components(Lutetium, 2, Thulium, 2, Yttrium, 6, Chlorine, 30, Hydrogen, 30, Oxygen, 15)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(LuCl3)2(TmCl3)2(YCl3)6(H2O)15", true);

        //  11159 Sodium Vanadate
        GTQTMaterials.SodiumVanadate = new Material.Builder(getMaterialsId(), gregtechId("sodium_vanadate"))
                .dust()
                .color(0xCC9933)
                .iconSet(BRIGHT)
                .flags(DISABLE_DECOMPOSITION)
                .components(Sodium, 3, Vanadium, 1, Oxygen, 4)
                .build();

        //  13098 Carbamide
        GTQTMaterials.Carbamide = new Material.Builder(getMaterialsId(), gregtechId("carbamide"))
                .dust()
                .color(0x16EF57)
                .iconSet(ROUGH)
                .components(Carbon, 1, Hydrogen, 4, Nitrogen, 2, Oxygen, 1)
                .build();

        //  11165 Lu-Tm-doped Yttrium Vanadate Deposition
        GTQTMaterials.LuTmDopedYttriumVanadateDeposition = new Material.Builder(getMaterialsId(), gregtechId("lu_tm_doped_yttrium_vanadate_deposition"))
                .dust()
                .color(Yttrium.getMaterialRGB() + Vanadium.getMaterialRGB() + Lutetium.getMaterialRGB())
                .iconSet(FINE)
                .build()
                .setFormula("Lu/Tm:YVO?", false);

        //  11167 Lu/Tm:YVO
        GTQTMaterials.LuTmYVO = new Material.Builder(getMaterialsId(), gregtechId("lu_tm_yvo"))
                .gem()
                .color(0x8C1B23)
                .iconSet(GEM_HORIZONTAL)
                .flags(DISABLE_DECOMPOSITION, GENERATE_LENS, CRYSTALLIZABLE)
                .components(Yttrium, 1, Vanadium, 1, Oxygen, 1, Lutetium, 1, Thulium, 1)
                .build()
                .setFormula("Lu/Tm:YVO", false);

        //  11171 Ammonium Fluoride
        GTQTMaterials.AmmoniumFluoride = new Material.Builder(getMaterialsId(), gregtechId("ammonium_fluoride"))
                .liquid()
                .color(Ammonia.getMaterialRGB() + Fluorine.getMaterialRGB())
                .components(Nitrogen, 1, Hydrogen, 4, Fluorine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11172 Ammonium Difluoride
        GTQTMaterials.AmmoniumDifluoride = new Material.Builder(getMaterialsId(), gregtechId("ammonium_difluoride"))
                .dust()
                .color(AmmoniumFluoride.getMaterialRGB())
                .iconSet(FINE)
                .components(Nitrogen, 1, Hydrogen, 5, Fluorine, 2)
                .build()
                .setFormula("NH4HF2", true);

        //  11173 Beryllium Difluoride
        GTQTMaterials.BerylliumDifluoride = new Material.Builder(getMaterialsId(), gregtechId("beryllium_difluoride"))
                .dust()
                .color(Beryllium.getMaterialRGB() + Fluorine.getMaterialRGB())
                .iconSet(SHINY)
                .components(Beryllium, 1, Fluorine, 2)
                .build();


        //  11168 Pr-Ho-Y Nitrates Solution
        GTQTMaterials.PrHoYNitratesSolution = new Material.Builder(getMaterialsId(), gregtechId("pr_ho_y_nitrates_solution"))
                .liquid(new FluidBuilder().attributes(FluidAttributes.ACID))
                .color(Praseodymium.getMaterialRGB() + Holmium.getMaterialRGB() + Yttrium.getMaterialRGB())
                .components(Praseodymium, 2, Holmium, 2, Yttrium, 6, Nitrogen, 30, Oxygen, 105, Hydrogen, 30)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(Pr(NO3)3)2(Ho(NO3)3)2(Y(NO3)3)6(H2O)15", true);

        //  11174 Pr:Ho/YLF
        GTQTMaterials.PrHoYLF = new Material.Builder(getMaterialsId(), gregtechId("pr_ho_ylf"))
                .gem()
                .color(Praseodymium.getMaterialRGB() + Holmium.getMaterialRGB() + Yttrium.getMaterialRGB() + Lithium.getMaterialRGB())
                .iconSet(RUBY)
                .components(Praseodymium, 1, Holmium, 1, Lithium, 1, Yttrium, 1, Fluorine, 4)
                .flags(DISABLE_DECOMPOSITION, GENERATE_LENS, CRYSTALLIZABLE)
                .build()
                .setFormula("Pr/Ho:YLF", false);

        //  11148 Potassium Manganate
        GTQTMaterials.PotassiumManganate = new Material.Builder(getMaterialsId(), gregtechId("potassium_manganate"))
                .dust()
                .color(0x873883)
                .iconSet(METALLIC)
                .components(Potassium, 2, Manganese, 1, Oxygen, 4)
                .build();

        //  11149 Potassium Manganate
        GTQTMaterials.PotassiumPermanganate = new Material.Builder(getMaterialsId(), gregtechId("potassium_permanganate"))
                .dust()
                .color(0x871D82)
                .iconSet(DULL)
                .components(Potassium, 1, Manganese, 1, Oxygen, 4)
                .build();

        //  11150 Manganese Sulfate
        GTQTMaterials.ManganeseSulfate = new Material.Builder(getMaterialsId(), gregtechId("manganese_sulfate"))
                .dust()
                .color(0xF0F895)
                .iconSet(ROUGH)
                .components(Manganese, 1, Sulfur, 1, Oxygen, 4)
                .build();

        //  11152 Neodymium-Doped Yttrium Oxide
        GTQTMaterials.NeodymiumDopedYttriumOxide = new Material.Builder(getMaterialsId(), gregtechId("neodymium_doped_yttrium_oxide"))
                .dust()
                .color(0x5AD55F)
                .iconSet(DULL)
                .build()
                .setFormula("Nd:Y?", false);

        //  11153 Aluminium Nitrate
        GTQTMaterials.AluminiumNitrate = new Material.Builder(getMaterialsId(), gregtechId("aluminium_nitrate"))
                .dust()
                .color(0x3AB3AA)
                .iconSet(SHINY)
                .components(Aluminium, 1, Nitrogen, 3, Oxygen, 9)
                .build()
                .setFormula("Al(NO3)3", true);

        //  11154 Alumina Solution
        GTQTMaterials.AluminaSolution = new Material.Builder(getMaterialsId(), gregtechId("alumina_solution"))
                .liquid()
                .color(0x6C4DC1)
                .components(Alumina, 1, Carbon, 25, Hydrogen, 56, Chlorine, 2, Nitrogen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(Al2O3)(CH2Cl2)(C12H27N)2", true);

        //  11155 Unprocessed Nd:YAG Solution
        GTQTMaterials.UnprocessedNdYAGSolution = new Material.Builder(getMaterialsId(), gregtechId("unprocessed_nd_yag_solution"))
                .liquid()
                .color(0x6f20af)
                .iconSet(DULL)
                .build()
                .setFormula("Nd:YAG?", false);

        //  13097 Ammonium Cyanate
        GTQTMaterials.AmmoniumCyanate = new Material.Builder(getMaterialsId(), gregtechId("ammonium_cyanate"))
                .liquid()
                .color(0x3a5dcf)
                .components(Hydrogen, 4, Nitrogen, 2, Carbon, 1, Oxygen, 1)
                .build()
                .setFormula("NH4CNO", true);

        //  13099 Tributylamine
        GTQTMaterials.Tributylamine = new Material.Builder(getMaterialsId(), gregtechId("tributylamine"))
                .liquid()
                .color(0x801a80)
                .components(Carbon, 12, Hydrogen, 27, Nitrogen, 1)
                .build()
                .setFormula("(C4H9)3N", true);

        //  13100 Dichloromethane
        GTQTMaterials.Dichloromethane = new Material.Builder(getMaterialsId(), gregtechId("dichloromethane"))
                .liquid()
                .color(0xB87FC7)
                .components(Carbon, 1, Hydrogen, 2, Chlorine, 2)
                .build();
    }
}
