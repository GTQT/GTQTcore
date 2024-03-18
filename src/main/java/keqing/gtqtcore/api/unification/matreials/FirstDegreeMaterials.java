package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.Elements;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.BlastProperty;
import gregtech.api.unification.material.properties.ToolProperty;
import gregtech.api.util.GTUtility;
import keqing.gtqtcore.api.unification.GTQTElements;
import keqing.gtqtcore.api.unification.GTQTMaterials;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.UHV;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Tetranitromethane;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_SMALL_GEAR;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static gregtech.api.unification.material.properties.BlastProperty.GasTier.LOW;
import static gregtech.api.unification.material.properties.BlastProperty.GasTier.MID;
import static gregtech.api.util.GTUtility.gregtechId;
import static gregtechfoodoption.GTFOMaterialHandler.AceticAnhydride;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.DimethylCarbonate;
import static keqing.gtqtcore.api.unification.TJMaterials.*;
import static keqing.gtqtcore.api.unification.material.info.GTQTMaterialIconSet.CUSTOM_MHCSM;

public class FirstDegreeMaterials {
    public FirstDegreeMaterials() {
    }
    private static int startId = 20000;
    private static final int END_ID = startId + 300;
    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static void register() {
        GTQTMaterials.HighPressureSteam = (new Material.Builder(getMaterialsId(), GTUtility.gregtechId("high_pressure_steam"))).fluid().color(14601607).build();
        GTQTMaterials.SteamExhaustGas = (new Material.Builder(getMaterialsId(), GTUtility.gregtechId("steam_exhaust_gas"))).fluid().color(14601607).build();
        GTQTMaterials.SuperheatedSteam = (new Material.Builder(getMaterialsId(), GTUtility.gregtechId("super_heated_steam"))).fluid().color(14601607).build();
        GTQTMaterials.Pyrotheum = (new Material.Builder(getMaterialsId(), GTUtility.gregtechId("pyrotheum"))).fluid().color(14601000).build();

        GTQTMaterials.StellarMaterialResidueA = (new Material.Builder(getMaterialsId(), GTUtility.gregtechId("stellar_material_residue_a")))  .fluid().plasma().color(14601000).build().setFormula("ST-α", true);
        GTQTMaterials.StellarMaterialResidueB = (new Material.Builder(getMaterialsId(), GTUtility.gregtechId("stellar_material_residue_b")))  .fluid().plasma().color(14600000).build().setFormula("ST-β", true);
        GTQTMaterials.StellarMaterialResidueC = (new Material.Builder(getMaterialsId(), GTUtility.gregtechId("stellar_material_residue_c")))  .fluid().plasma().color(14639000).build().setFormula("ST-γ", true);
        GTQTMaterials.StellarMaterial = (new Material.Builder(getMaterialsId(), GTUtility.gregtechId("stellar_material")))  .fluid().plasma().color(14638000).build().setFormula("ST-ST", true);

        GTQTMaterials.LightNaquadahFuel = (new Material.Builder(getMaterialsId(), GTUtility.gregtechId("light_naquadah_fuel"))) .fluid().color(14638000).build().setFormula("NQ", true);
        GTQTMaterials.MediumNaquadahFuel = (new Material.Builder(getMaterialsId(), GTUtility.gregtechId("medium_naquadah_fuel"))) .fluid().color(14638000).build().setFormula("-NQ-", true);
        GTQTMaterials.HeavyNaquadahFuel = (new Material.Builder(getMaterialsId(), GTUtility.gregtechId("heavy_naquadah_fuel"))) .fluid().color(14638000).build().setFormula("+NQ+", true);

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
        GTQTMaterials.Alubrassa = new Material.Builder(getMaterialsId(), gregtechId("alubrassa"))
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
                .dust().fluid()
                .color(0xB2DFEE)
                .components(Carbon, 2, Hydrogen, 6, Oxygen, 1,Chlorine,1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //硫酸铵
        GTQTMaterials.AmmoniumSulfate = new Material.Builder(getMaterialsId(), gregtechId("ammonium_sulfate"))
                .dust().fluid()
                .color(0xB2DFEE)
                .components(Nitrogen, 2, Hydrogen, 8, Sulfur, 1,Oxygen,4)
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

        GTQTMaterials.CSilicon = new Material.Builder(getMaterialsId(), gregtechId("csilicon"))
                .ingot()
                .color(0x3C3C50).iconSet(METALLIC)
                .flags(GENERATE_FOIL)
                .element(Elements.Si)
                .blast(1800) // no gas tier for silicon
                .build();

        GTQTMaterials.CopperCl = new Material.Builder(getMaterialsId(), gregtechId("copper_cl"))
                .ingot()
                .color(0x8B4C39).iconSet(METALLIC)
                .flags(GENERATE_FOIL)
                .components(Copper,1,Chlorine,2)
                .blast(2273) // no gas tier for silicon
                .build();

        //聚乙烯醇肉桂酸酯
        GTQTMaterials.Vinylcinnamate = new Material.Builder(getMaterialsId(), gregtechId("vinylcinnamate"))
                .fluid().dust()
                .color(0xFF8C00).iconSet(METALLIC)
                .flags(GENERATE_FOIL)
                .build();
        //感光树脂-聚乙烯醇肉桂酸酯

        //增感剂
        //光刻胶溶剂

        //xMT分子光刻
        GTQTMaterials.Xmt = new Material.Builder(getMaterialsId(), gregtechId("xmt"))
                .fluid().dust()
                .color(0x3C3C50).iconSet(METALLIC)
                .flags(GENERATE_FOIL)
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
                .ingot().dust().ore()
                .color(0x8B6914).iconSet(METALLIC)
                .components(Barium,1,Titanium,1,Oxygen,3)
                .flags(DISABLE_DECOMPOSITION)
                .flags(GENERATE_PLATE, GENERATE_DENSE,GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR)
                .build();

        //海绵钛
        GTQTMaterials.Htitanate = new Material.Builder(getMaterialsId(), gregtechId("htitanate"))
                .ingot()
                .color(0x8B6914).iconSet(METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .components(Titanium,1)
                .build();

        GTQTMaterials.Fluix = new Material.Builder(getMaterialsId(), gregtechId("fluix"))
                .gem(1).ore()
                .color(0x7D26CD).iconSet(CERTUS)
                .flags(GENERATE_PLATE, CRYSTALLIZABLE)
                .components(Silicon, 1, Oxygen, 2)
                .build();

        //eio
        GTQTMaterials.RedstoneAlloy = new Material.Builder(getMaterialsId(), gregtechId("redstone_alloy"))
                .ingot().fluid()
                .color(0x943423).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROD,GENERATE_FRAME,GENERATE_BOLT_SCREW,GENERATE_FOIL,GENERATE_GEAR,GENERATE_SMALL_GEAR,GENERATE_ROUND,GENERATE_SPRING)
                .components(Redstone ,1,Hydrogen, 1,Oxygen ,1)
                .blast(2700,LOW)
                .cableProperties(32,1,2)
                .build();

        GTQTMaterials.PulsatingIron = new Material.Builder(getMaterialsId(), gregtechId("pulsating_iron"))
                .ingot().fluid()
                .color(0x4b915b).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROD,GENERATE_FRAME,GENERATE_BOLT_SCREW,GENERATE_FOIL,GENERATE_GEAR,GENERATE_SMALL_GEAR,GENERATE_ROUND,GENERATE_SPRING)
                .components(EnderPearl ,1,Iron, 1,RedstoneAlloy ,1)
                .build();

        GTQTMaterials.ConductiveIron = new Material.Builder(getMaterialsId(), gregtechId("conductive_iron"))
                .ingot().fluid()
                .color(0xb89791).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROD,GENERATE_FRAME,GENERATE_BOLT_SCREW,GENERATE_FOIL,GENERATE_GEAR,GENERATE_SMALL_GEAR,GENERATE_ROUND,GENERATE_SPRING)
                .components(Silver ,1,Iron, 1,RedstoneAlloy ,1)
                .build();

        GTQTMaterials.EnergeticAlloy = new Material.Builder(getMaterialsId(), gregtechId("energetic_alloy"))
                .ingot().fluid()
                .color(0xd89045).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROD,GENERATE_FRAME,GENERATE_BOLT_SCREW,GENERATE_FOIL,GENERATE_GEAR,GENERATE_SMALL_GEAR,GENERATE_ROUND,GENERATE_SPRING)
                .components(BlackSteel ,1,Gold, 1,ConductiveIron ,1)
                .blast(2700,LOW)
                .cableProperties(512,2,4)
                .build();

        GTQTMaterials.VibrantAlloy = new Material.Builder(getMaterialsId(), gregtechId("vibrant_alloy"))
                .ingot().fluid()
                .color(0x859f2d).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROD,GENERATE_FRAME,GENERATE_BOLT_SCREW,GENERATE_FOIL,GENERATE_GEAR,GENERATE_SMALL_GEAR,GENERATE_ROUND,GENERATE_SPRING)
                .components(EnderEye ,1,EnergeticAlloy, 1,Chromite ,1)
                .blast(2700,LOW)
                .cableProperties(2048,4,4)
                .build();

        GTQTMaterials.Soularium = new Material.Builder(getMaterialsId(), gregtechId("soularium"))
                .ingot().fluid()
                .color(0x372719).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROD,GENERATE_FRAME,GENERATE_BOLT_SCREW,GENERATE_FOIL,GENERATE_GEAR,GENERATE_SMALL_GEAR,GENERATE_ROUND,GENERATE_SPRING)
                .components(Gold ,1,Ash,1)
                .build();

        GTQTMaterials.ElectricalSteel = new Material.Builder(getMaterialsId(), gregtechId("electrical_steel"))
                .ingot().fluid()
                .color(0x9d9d9d).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROD,GENERATE_FRAME,GENERATE_BOLT_SCREW,GENERATE_FOIL,GENERATE_GEAR,GENERATE_SMALL_GEAR,GENERATE_ROUND,GENERATE_SPRING)
                .components(Steel ,1,Coal, 1,Silicon ,1)
                .blast(2700,LOW)
                .rotorStats(9.0f, 5.0f, 3000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 28000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .cableProperties(128,2,2)
                .build();

        GTQTMaterials.DarkSteel = new Material.Builder(getMaterialsId(), gregtechId("dark_steel"))
                .ingot().fluid()
                .color(0x2f292f).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROD,GENERATE_FRAME,GENERATE_BOLT_SCREW,GENERATE_FOIL,GENERATE_GEAR,GENERATE_SMALL_GEAR,GENERATE_ROUND,GENERATE_SPRING)
                .components(ElectricalSteel ,1,Coal, 1,Obsidian ,1)
                .rotorStats(12.0f, 6.0f, 4500)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 32000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .blast(3600,MID)
                .build();

        GTQTMaterials.EndSteel = new Material.Builder(getMaterialsId(), gregtechId("end_steel"))
                .ingot().fluid()
                .color(0xbdb88c).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROD,GENERATE_FRAME,GENERATE_BOLT_SCREW,GENERATE_FOIL,GENERATE_GEAR,GENERATE_SMALL_GEAR,GENERATE_ROUND,GENERATE_SPRING)
                .components(DarkSteel ,1,Endstone, 1,TungstenSteel ,1)
                .rotorStats(15.0f, 7.0f, 6000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 36000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .blast(4500,MID)
                .build();

        GTQTMaterials.CrystallineAlloy = new Material.Builder(getMaterialsId(), gregtechId("crystalline_alloy"))
                .ingot().fluid()
                .color(0x8FE3F7).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROD,GENERATE_FRAME,GENERATE_BOLT_SCREW,GENERATE_FOIL,GENERATE_GEAR,GENERATE_SMALL_GEAR,GENERATE_ROUND,GENERATE_SPRING)
                .components(PulsatingIron ,1,Diamond,1,Emerald,1,Gold,1)
                .blast(4500,MID)
                .build();

        GTQTMaterials.MelodicAlloy = new Material.Builder(getMaterialsId(), gregtechId("melodic_alloy"))
                .ingot().fluid()
                .color(0xA877A8).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROD,GENERATE_FRAME,GENERATE_BOLT_SCREW,GENERATE_FOIL,GENERATE_GEAR,GENERATE_SMALL_GEAR,GENERATE_ROUND,GENERATE_SPRING)
                .components(EndSteel ,1,EnderEye,1,Bismuth,1)
                .blast(5400,MID)
                .build();

        GTQTMaterials.StellarAlloy = new Material.Builder(getMaterialsId(), gregtechId("stellar_alloy"))
                .ingot().fluid()
                .color(0xCCCCCC).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROD,GENERATE_FRAME,GENERATE_BOLT_SCREW,GENERATE_FOIL,GENERATE_GEAR,GENERATE_SMALL_GEAR,GENERATE_ROUND,GENERATE_SPRING)
                .components(MelodicAlloy ,1,NetherStar,1,Naquadah,1)
                .blast(7200,MID)
                .rotorStats(15.0f, 7.0f, 6000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 32000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .cableProperties(32768,6,8)
                .build();

        GTQTMaterials.CrystallinePinkSlime = new Material.Builder(getMaterialsId(), gregtechId("crystalline_pink_slime"))
                .ingot().fluid()
                .color(0xE79EDB).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROD,GENERATE_FRAME,GENERATE_BOLT_SCREW,GENERATE_FOIL,GENERATE_GEAR,GENERATE_SMALL_GEAR,GENERATE_ROUND,GENERATE_SPRING)
                .components(CrystallineAlloy ,1,Diamond,1,RawRubber,1)
                .blast(5000,MID)
                .rotorStats(15.0f, 7.0f, 4000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 6000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .cableProperties(2048,6,2)
                .build();

        GTQTMaterials.EnergeticSilver = new Material.Builder(getMaterialsId(), gregtechId("energetic_silver"))
                .ingot().fluid()
                .color(0x598DB3).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROD,GENERATE_FRAME,GENERATE_BOLT_SCREW,GENERATE_FOIL,GENERATE_GEAR,GENERATE_SMALL_GEAR,GENERATE_ROUND,GENERATE_SPRING)
                .components(Silver ,1,ConductiveIron,1,BlackSteel,1)
                .blast(2700,MID)
                .rotorStats(15.0f, 7.0f, 2000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 8000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .cableProperties(512,4,2)
                .build();

        GTQTMaterials.VividAlloy = new Material.Builder(getMaterialsId(), gregtechId("vivid_alloy"))
                .ingot().fluid()
                .color(0x469BB1).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROD,GENERATE_FRAME,GENERATE_BOLT_SCREW,GENERATE_FOIL,GENERATE_GEAR,GENERATE_SMALL_GEAR,GENERATE_ROUND,GENERATE_SPRING)
                .components(Chromite ,1,EnergeticSilver,1,EnderEye,1)
                .blast(3000,MID)
                .rotorStats(15.0f, 7.0f, 4000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 12000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .cableProperties(2048,4,2)
                .build();

        GTQTMaterials.CrudeSteel = new Material.Builder(getMaterialsId(), gregtechId("crude_steel"))
                .ingot().fluid()
                .color(0x807C74).iconSet(ROUGH)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROD,GENERATE_FRAME,GENERATE_BOLT_SCREW,GENERATE_FOIL,GENERATE_GEAR,GENERATE_SMALL_GEAR,GENERATE_ROUND,GENERATE_SPRING)
                .flags(DISABLE_DECOMPOSITION)
                .build();


        GTQTMaterials.Ealuminium = new Material.Builder(getMaterialsId(), gregtechId("ealuminium"))
                .fluid().dust()
                .color(0xd89045).iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Aluminium ,1,Nitrogen,3,Oxygen,9)
                .build();

        //海带灰溶液
        GTQTMaterials.Haidaihui = new Material.Builder(getMaterialsId(), gregtechId("haidaihui"))
                .fluid()
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
                .components(Potassium ,1,Iodine,1)
                .build();
        //溴化钾
        GTQTMaterials.PotassiumBromide = new Material.Builder(getMaterialsId(), gregtechId("potassium_bromide"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .components(Potassium ,1,Bromine,1)
                .build();
        //2-丁烯醛
        GTQTMaterials.Crotonaldehyde = new Material.Builder(getMaterialsId(), gregtechId("crotonaldehyde"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .components(Carbon ,4,Hydrogen,8,Oxygen,1)
                .build();

        //2-乙基（-2-）己烯醛
        GTQTMaterials.Ethylhexenal = new Material.Builder(getMaterialsId(), gregtechId("ethylhexenal"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .components(Carbon,8,Hydrogen,14,Oxygen,1)
                .build();
        //草酸
        GTQTMaterials.EthanedioicAcid = new Material.Builder(getMaterialsId(), gregtechId("ethanedioic_acid"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .components(Hydrogen,2,Carbon,2,Oxygen,4)
                .build();

        //五氧化二钒
        GTQTMaterials.VanadiumOxide = new Material.Builder(getMaterialsId(), gregtechId("vanadium_oxide"))
                .dust()
                .color(0xd89045).iconSet(SHINY)
                .components(Vanadium,2,Oxygen,5)
                .build();
        //SodiumSilicofluoride 氟硅酸钠
        GTQTMaterials.SodiumSilicofluoride = new Material.Builder(getMaterialsId(), gregtechId("sodium_silicofluoride"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .components(Sodium,2,Silicon,1,Fluorine,6)
                .build();

        //Hexafluorosilicic acid 氟硅酸
        GTQTMaterials.HexafluorosilicicAcid = new Material.Builder(getMaterialsId(), gregtechId("hexafluorosilicic_acid"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .components(Hydrogen,2,Silicon,1,Fluorine,6)
                .build();

        //独居石稀土浊溶液
        GTQTMaterials.Dujushixitu = new Material.Builder(getMaterialsId(), gregtechId("dujushixitu"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .build();

        //氧化铪-氧化锆混合物
        GTQTMaterials.Yanghuahayanghuagao = new Material.Builder(getMaterialsId(), gregtechId("yanghuahayanghuagao"))
                .dust()
                .color(0xFFB90F).iconSet(SHINY)
                .components(Hafnium,1,Zirconium,1,Oxygen,4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //稀释独居石稀土泥浆
        GTQTMaterials.Xishidujushixitu = new Material.Builder(getMaterialsId(), gregtechId("xishidujushixitu"))
                .fluid()
                .color(0xFFB6C1).iconSet(SHINY)
                .build();

        //氧化铪
        GTQTMaterials.Yanghuaha = new Material.Builder(getMaterialsId(), gregtechId("yanghuaha"))
                .dust()
                .color(0xd89045).iconSet(SHINY)
                .components(Hafnium,1,Oxygen,2)
                .build();

        //四氯化铪_锆
        GTQTMaterials.Silvhuaha = new Material.Builder(getMaterialsId(), gregtechId("silvhuaha"))
                .fluid()
                .color(0xFFAEB9).iconSet(SHINY)
                .components(Hafnium,1,Zirconium,1,Chlorine,8)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //低纯
        GTQTMaterials.Dilvhuaha = new Material.Builder(getMaterialsId(), gregtechId("dilvhuaha"))
                .dust()
                .color(0xFF8C00).iconSet(SHINY)
                .components(Hafnium,1,Zirconium,1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //低纯碘化
        GTQTMaterials.Iilvhuaha = new Material.Builder(getMaterialsId(), gregtechId("iilvhuaha"))
                .fluid()
                .color(0xFF83FA).iconSet(SHINY)
                .components(Hafnium,1,Zirconium,1,Iodine,8)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //硫酸独居石
        GTQTMaterials.Liusuandujushi = new Material.Builder(getMaterialsId(), gregtechId("liusuandujushi"))
                .fluid()
                .color(0xFF82AB).iconSet(SHINY)
                .build();

        //独居石稀土滤渣粉
        GTQTMaterials.Dujushixitulvzha = new Material.Builder(getMaterialsId(), gregtechId("dujushixitulvzha"))
                .dust()
                .color(0xFF8247).iconSet(SHINY)
                .build();

        //磷酸钍滤饼
        GTQTMaterials.Dujushixitulvb = new Material.Builder(getMaterialsId(), gregtechId("dujushixitulvb"))
                .dust()
                .color(0xFF6EB4).iconSet(SHINY)
                .build();

        //磷酸钍精粉
        GTQTMaterials.Dujushixitulvbf = new Material.Builder(getMaterialsId(), gregtechId("dujushixitulvbf"))
                .dust()
                .color(0xFF6A6A).iconSet(SHINY)
                .build();

        //中和独居石稀土滤渣粉
        GTQTMaterials.Zhonghedujushixitulvzha = new Material.Builder(getMaterialsId(), gregtechId("zhonghedujushixitulvzha"))
                .dust()
                .color(0xFF69B4).iconSet(SHINY)
                .build();

        //浓缩独居石稀土氢氧化物粉
        GTQTMaterials.Nongsuodujushiqingyanghuawu = new Material.Builder(getMaterialsId(), gregtechId("nongsuodujushiqingyanghuawu"))
                .dust()
                .color(0xFF6347).iconSet(SHINY)
                .build();

        //铀滤渣粉
        GTQTMaterials.Youlvzhafen = new Material.Builder(getMaterialsId(), gregtechId("youlvzhafen"))
                .dust()
                .color(0xFF3030).iconSet(SHINY)
                .build();

        //干燥浓缩硝酸独居石稀土
        GTQTMaterials.Ganzaonongsuoxiaosuandujushixitu = new Material.Builder(getMaterialsId(), gregtechId("ganzaonongsuoxiaosuandujushixitu"))
                .dust()
                .color(0xFF1493).iconSet(SHINY)
                .build();

        //独居石罕土沉淀粉
        GTQTMaterials.Dujushihantuchendian = new Material.Builder(getMaterialsId(), gregtechId("dujushihantuchendian"))
                .dust()
                .color(0xFF00FF).iconSet(SHINY)
                .build();

        //异质卤化独居石稀土混合物
        GTQTMaterials.Yizhiluhuadujushi = new Material.Builder(getMaterialsId(), gregtechId("yizhiluhuadujushi"))
                .dust()
                .color(0xFF0000).iconSet(SHINY)
                .build();

        //饱和独居石稀土
        GTQTMaterials.Baohedujushixituhunhe = new Material.Builder(getMaterialsId(), gregtechId("baohedujushixituhunhe"))
                .dust()
                .color(0xd89045).iconSet(SHINY)
                .build();

        //钐精
        GTQTMaterials.Shanjing = new Material.Builder(getMaterialsId(), gregtechId("shanjing"))
                .dust()
                .color(0xF5DEB3).iconSet(SHINY)
                .build();

        //氯化铈
        GTQTMaterials.Lvhuashi = new Material.Builder(getMaterialsId(), gregtechId("lvhuashi"))
                .dust()
                .color(0xF4F4F4).iconSet(SHINY)
                .components(Cerium,1,Chlorine,2)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //草酸铈
        GTQTMaterials.Cvhuashi = new Material.Builder(getMaterialsId(), gregtechId("cvhuashi"))
                .dust()
                .color(0xF4A460).iconSet(SHINY)
                .components(Cerium,1,Hydrogen,2,Carbon,2,Oxygen,4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //Cerium
        GTQTMaterials.Tvhuashi = new Material.Builder(getMaterialsId(), gregtechId("tvhuashi"))
                .dust()
                .color(0xF2F2F2).iconSet(SHINY)
                .components(Cerium,2,Oxygen,3)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //氟碳镧铈稀土浊
        GTQTMaterials.Futanlanshixituzhuo = new Material.Builder(getMaterialsId(), gregtechId("futanlanshixituzhuo"))
                .fluid()
                .color(0x8B7500).iconSet(SHINY)
                .build();

        //蒸汽裂化氟碳镧铈泥浆
        GTQTMaterials.Zhengqiliehuafutanlanshinijiang = new Material.Builder(getMaterialsId(), gregtechId("zhengqiliehuafutanlanshinijiang"))
                .fluid()
                .color(0x8B7355).iconSet(SHINY)
                .build();

        //调制氟碳镧铈泥浆
        GTQTMaterials.Tiaozhifutanlanshinijiang = new Material.Builder(getMaterialsId(), gregtechId("tiaozhifutanlanshinijiang"))
                .fluid()
                .color(0x8B6969).iconSet(SHINY)
                .build();

        //过滤氟碳镧铈泥浆
        GTQTMaterials.Guolvfutanlanshinijiang = new Material.Builder(getMaterialsId(), gregtechId("guolvfutanlanshinijiang"))
                .dust()
                .color(0x8B6914).iconSet(SHINY)
                .build();

        //氟碳镧铈稀土氧化物粉
        GTQTMaterials.Futanlanshixituyanghuawu = new Material.Builder(getMaterialsId(), gregtechId("futanlanshixituyanghuawu"))
                .dust()
                .color(0x8B668B).iconSet(SHINY)
                .build();

        //酸浸氟碳镧铈稀土氧化物粉
        GTQTMaterials.Suanjinfutanlanshixituyanghuawu = new Material.Builder(getMaterialsId(), gregtechId("suanjinfutanlanshixituyanghuawu"))
                .fluid()
                .color(0x8B6508).iconSet(SHINY)
                .build();

        //焙烧稀土氧化物粉
        GTQTMaterials.Bsxituyanghuawu = new Material.Builder(getMaterialsId(), gregtechId("bsxituyanghuawu"))
                .dust()
                .color(0x8B636C).iconSet(SHINY)
                .build();

        //氧化铈稀土氧化物粉
        GTQTMaterials.Yanghuaxituyanghauwu = new Material.Builder(getMaterialsId(), gregtechId("yanghuaxituyanghauwu"))
                .fluid()
                .color(0x8B5F65).iconSet(SHINY)
                .build();

        //氟碳镧铈罕土氧化物粉
        GTQTMaterials.Futanlanshihantuyanghuawu = new Material.Builder(getMaterialsId(), gregtechId("futanlanshihantuyanghuawu"))
                .dust()
                .color(0x8B5A2B).iconSet(SHINY)
                .build();

        //氟碳镧铈罕土氧化物悬浊液
        GTQTMaterials.Futanlanshihantuyanghuawuxuanzhuo = new Material.Builder(getMaterialsId(), gregtechId("futanlanshihantuyanghuawuxuanzhuo"))
                .fluid()
                .color(0x8B5A00).iconSet(SHINY)
                .build();

        //钐稀土精粉
        GTQTMaterials.Shanxitujingfen = new Material.Builder(getMaterialsId(), gregtechId("shanxitujingfen"))
                .dust()
                .color(0x8B5742).iconSet(SHINY)
                .build();

        //氟化钐稀土精粉
        GTQTMaterials.Fhanxitujingfen = new Material.Builder(getMaterialsId(), gregtechId("fhanxitujingfen"))
                .fluid()
                .color(0x8B4C39).iconSet(SHINY)
                .build();

        //钐-铽混合物粉
        GTQTMaterials.Shantui = new Material.Builder(getMaterialsId(), gregtechId("shantui"))
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
        GTQTMaterials.Isochloropropane = new Material.Builder(getMaterialsId(), gregtechId("isochloropropane"))
                .fluid()
                .color(0xC3AC65)
                .components(Carbon, 3, Hydrogen, 7, Chlorine, 1)
                .build()
                .setFormula("CH3CHClCH3", true);

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
                .polymer()
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
                .components(Carbon, 48, Hydrogen, 48 ,Nitrogen, 6)
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
                .addOreByproducts(Iron)
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
        GTQTMaterials.Dichunduguiyanruye= new Material.Builder(getMaterialsId(), gregtechId("dichunduguiyanruye"))
                .fluid()
                .flags(DISABLE_DECOMPOSITION)
                .color(0x9AFF9A)
                .components(Naquadah,1,Oxygen,2)
                .build();

        //低纯度硅岩溶液
        GTQTMaterials.Dichunduguiyanrongye= new Material.Builder(getMaterialsId(), gregtechId("dichunduguiyanrongye"))
                .fluid()
                .flags(DISABLE_DECOMPOSITION)
                .color(0xB4EEB4)
                .components(Naquadah,1,Oxygen,2)
                .build();

        //硅岩-精金混合
        GTQTMaterials.Nqad= new Material.Builder(getMaterialsId(), gregtechId("nqad"))
                .fluid()
                .color(0xE9967A)
                .build();

        //氢氧化镓 material('gallium')
        GTQTMaterials.Galliumoh= new Material.Builder(getMaterialsId(), gregtechId("galliumoh"))
                .dust()
                .color(0xDBDBDB)
                .components(Gallium,1,Oxygen,1,Hydrogen,1)
                .build();

        //废氟
        GTQTMaterials.Feifu= new Material.Builder(getMaterialsId(), gregtechId("feifu"))
                .fluid()
                .color(0x556B2F)
                .flags(DISABLE_DECOMPOSITION)
                .components(Fluorine,1)
                .build();

        //废液
        GTQTMaterials.Feiye= new Material.Builder(getMaterialsId(), gregtechId("feiye"))
                .fluid()
                .color(0x548B54)
                .build();

        //浓缩富集硅岩矿泥粉
        GTQTMaterials.Nongsuofujiguiyankuangni= new Material.Builder(getMaterialsId(), gregtechId("nongsuofujiguiyankuangni"))
                .dust()
                .color(0x48D1CC)
                .build();
        //氧化硅岩混合物粉
        GTQTMaterials.Yanghuaguiyanhunhe= new Material.Builder(getMaterialsId(), gregtechId("yanghuaguiyanhunhe"))
                .dust()
                .color(0x458B00)
                .build();
        //高纯硅岩溶液
        GTQTMaterials.Gaochunguiyanrongye= new Material.Builder(getMaterialsId(), gregtechId("gaochunguiyanrongye"))
                .fluid()
                .color(0x3CB371)
                .build();

        //低纯硫酸超能硅岩粉
        GTQTMaterials.Dichunliusuanchaonengguiyan= new Material.Builder(getMaterialsId(), gregtechId("dichunliusuanchaonengguiyan"))
                .dust()
                .color(0x32CD32)
                .build();

        //低纯硫酸超能硅岩溶液
        GTQTMaterials.Dichunliusuanchaonengguiyanr= new Material.Builder(getMaterialsId(), gregtechId("dichunliusuanchaonengguiyanr"))
                .fluid()
                .color(0x2E8B57)
                .build();

        //高纯超能硅岩溶液
        GTQTMaterials.Gaochunchaonengguiyanrongye= new Material.Builder(getMaterialsId(), gregtechId("gaochunchaonengguiyanrongye"))
                .fluid()
                .color(0x228B22)
                .build();

        //交错次元空气
        GTQTMaterials.BetAir = new Material.Builder(getMaterialsId(), gregtechId("bet_air"))
                .gas()
                .color(0x2E8B57)
                .flags(DISABLE_DECOMPOSITION)
                .components(Methane, 78, HydrogenSulfide, 21, Neon, 7,Radon,2)
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
                .components(CarbonDioxide, 80, Argon, 20, Oxygen, 10,Radon,10,Hydrogen,10,Nitrogen,10,MagicGas,10)
                .build();

        GTQTMaterials.LiquidMarsAir = new Material.Builder(getMaterialsId(), gregtechId("liquid_mars_air"))
                .liquid(new FluidBuilder().temperature(58))
                .color(0x8B4500)
                .flags(DISABLE_DECOMPOSITION)
                .components(CarbonDioxide, 160, Argon, 15, Oxygen, 15, Helium3, 10, Radon,
                        10, Hydrogen, 10,Nitrogen,10,MagicGas,5)
                .build();

        //离散态微薄魔力
        GTQTMaterials.MagicGas = new Material.Builder(getMaterialsId(), gregtechId("magic_gas"))
                .gas()
                .color(0x00FFFF)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //离散态素魔力
        GTQTMaterials.MagicFas = new Material.Builder(getMaterialsId(), gregtechId("magic_fas"))
                .gas()
                .color(0x00FFFF)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //离散态纯净魔力
        GTQTMaterials.MagicDas = new Material.Builder(getMaterialsId(), gregtechId("magic_das"))
                .gas()
                .color(0x00FFFF)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //凝聚态素魔力
        GTQTMaterials.MagicAas = new Material.Builder(getMaterialsId(), gregtechId("magic_aas"))
                .gas()
                .color(0x00FFFF)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //魔力废液
        GTQTMaterials.MagicRub = new Material.Builder(getMaterialsId(), gregtechId("magic_rub"))
                .gas()
                .color(0x00FFFF)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //魔力
        GTQTMaterials.Magic = new Material.Builder(getMaterialsId(), gregtechId("magic"))
                .fluid()
                .color(0x428fdb)
                .iconSet(DULL)
                .element(GTQTElements.Magic)
                .build();

        //富集魔力
        GTQTMaterials.Richmagic = new Material.Builder(getMaterialsId(), gregtechId("richmagic"))
                .ingot()
                .color(0x428fdb)
                .iconSet(DULL)
                .flags(GENERATE_PLATE,GENERATE_DOUBLE_PLATE,GENERATE_ROD,GENERATE_LONG_ROD)
                .build();
                Richmagic.setFormula("*KQ*");

        //四溴对二甲苯
        GTQTMaterials.Tetrabromo = new Material.Builder(getMaterialsId(), gregtechId("tetrabromo"))
                .fluid()
                .color(0x8B8B00)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 8, Hydrogen, 6, Bromine,4)
                .build();

        //六溴对二甲苯
        GTQTMaterials.Tetrabromobenzene = new Material.Builder(getMaterialsId(), gregtechId("tetrabromobenzene"))
                .fluid()
                .color(0x8E8E38)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 8, Hydrogen, 4, Bromine,6)
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
                .setFormula("Mg2(SiO4)",true);

        GTQTMaterials.Lizardite = new Material.Builder(getMaterialsId(), gregtechId("lizardite"))
                .dust()
                .color(0xa79e42)
                .flags(NO_SMASHING, DECOMPOSITION_BY_ELECTROLYZING)
                .components(Magnesium, 3, Silicon, 2, Oxygen, 9, Hydrogen, 4)
                .build()
                .setFormula("Mg3Si2O5(OH)4",true);

        //锗线
        //氧化闪锌混合物
        GTQTMaterials.Yanghuashanxinhunhewu = new Material.Builder(getMaterialsId(), gregtechId("yanghuashanxinhunhewu"))
                .dust()
                .color(0xAEEEEE)
                .build()
                .setFormula("Ge?O?",true);

        //富集闪锌混合物
        GTQTMaterials.Fujishanxinhunhewu = new Material.Builder(getMaterialsId(), gregtechId("fujishanxinhunhewu"))
                .dust()
                .color(0xB0C4DE)
                .build()
                .setFormula("Ge?O?",true);
        //富集闪锌焙烧残留
        GTQTMaterials.Fujishanxinbeishaocanliu = new Material.Builder(getMaterialsId(), gregtechId("fujishanxinbeishaocanliu"))
                .dust()
                .color(0xB0E0E6)
                .build()
                .setFormula("Ge?O?",true);

        //含闪锌溶液
        GTQTMaterials.Hanxinrongye = new Material.Builder(getMaterialsId(), gregtechId("hanxinrongye"))
                .fluid()
                .color(0xa79e42)
                .build()
                .setFormula("Ge?SO4?",true);
        //氢氧化铁
        GTQTMaterials.Qingyanghuatie = new Material.Builder(getMaterialsId(), gregtechId("qingyanghuatie"))
                .dust()
                .color(0xCD3700)
                .flags(DISABLE_DECOMPOSITION)
                .components(Iron, 1,  Oxygen, 3, Hydrogen, 3)
                .build();

        //混锗铁残渣
        GTQTMaterials.Huntiezhecanzha = new Material.Builder(getMaterialsId(), gregtechId("huntiezhecanzha"))
                .dust()
                .color(0xB3EE3A)
                .build()
                .setFormula("Ge?Fe?Zn?",true);

        //混锗镉残渣
        GTQTMaterials.Hungezhecanzha = new Material.Builder(getMaterialsId(), gregtechId("hungezhecanzha"))
                .dust()
                .color(0xC67171)
                .build()
                .setFormula("Ge?Cd?Zn?",true);

        //残渣溶液
        GTQTMaterials.Canzharongye = new Material.Builder(getMaterialsId(), gregtechId("canzharongye"))
                .fluid()
                .color(0xCD950C)
                .build()
                .setFormula("Ge?Cd?*",true);

        //一氧化锗
        GTQTMaterials.Yiyanghauzhe = new Material.Builder(getMaterialsId(), gregtechId("yiyanghauzhe"))
                .fluid()
                .color(0x97FFFF)
                .build()
                .setFormula("GeO",true);
        //炉渣
        GTQTMaterials.Zhezha = new Material.Builder(getMaterialsId(), gregtechId("zhezha"))
                .dust()
                .color(0xA0522D)
                .build()
                .setFormula("Fe?Zn?Pd?Cd?",true);

        //鞣酸
        GTQTMaterials.Rousuan = new Material.Builder(getMaterialsId(), gregtechId("rousuan"))
                .fluid()
                .color(0x8B6914)
                .build()
                .setFormula("C76H52O46",true);

        GTQTMaterials.Rzousuan = new Material.Builder(getMaterialsId(), gregtechId("rzousuan"))
                .fluid()
                .color(0x8B7E66)
                .build()
                .setFormula("C76H52O46*",true);


        //富集锗沉淀
        GTQTMaterials.Fujizhechendian = new Material.Builder(getMaterialsId(), gregtechId("fujizhechendian"))
                .dust()
                .color(0x7EC0EE)
                .build()
                .setFormula("Ge?",true);


        //四氯化锗
        GTQTMaterials.Silvhuazhe = new Material.Builder(getMaterialsId(), gregtechId("silvhuazhe"))
                .fluid()
                .color(0x8A2BE2)
                .build()
                .setFormula("GeCl4*",true);

        //四氯化锗
        GTQTMaterials.Ssilvhuazhe = new Material.Builder(getMaterialsId(), gregtechId("ssilvhuazhe"))
                .fluid()
                .color(0xa79e42)
                .build()
                .setFormula("GeCl4*HCl",true);

        //高淳四氯化锗
        GTQTMaterials.Sgilvhuazhe = new Material.Builder(getMaterialsId(), gregtechId("sgilvhuazhe"))
                .fluid()
                .color(0x7171C6)
                .build()
                .setFormula("GeCl4",true);

        //高纯二氧化锗
        GTQTMaterials.Egryanghuazhe = new Material.Builder(getMaterialsId(), gregtechId("egryanghuazhe"))
                .dust()
                .color(0x68228B)
                .build()
                .setFormula("Ge02",true);

        //杀虫剂
        GTQTMaterials.Dfeltamethrin = new Material.Builder(getMaterialsId(), gregtechId("dfeltamethrin"))
                .fluid()
                .color(0x0000EE)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C22H19Br2NO3",true);

        //溴氰菊酯
        GTQTMaterials.Deltamethrin = new Material.Builder(getMaterialsId(), gregtechId("deltamethrin"))
                .dust()
                .components(Carbon, 22, Hydrogen, 19, Bromine, 2, Nitrogen, 1,Oxygen,3)
                .color(0x0000FF)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C22H19Br2NO3",true);

        //二溴水苯
        GTQTMaterials.Dfeltamethrina = new Material.Builder(getMaterialsId(), gregtechId("dfeltamethrina"))
                .fluid()
                .color(0x006400)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 22, Hydrogen, 19, Bromine, 2, Nitrogen, 1,Oxygen,3)
                .build();

        //二溴苯酚
        GTQTMaterials.Dfeltamethrinb = new Material.Builder(getMaterialsId(), gregtechId("dfeltamethrinb"))
                .fluid()
                .color(0x00688B)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C6H4Br2O",true);
        //二溴-2-丙酮酚
        GTQTMaterials.Dfeltamethrinc = new Material.Builder(getMaterialsId(), gregtechId("dfeltamethrinc"))
                .fluid()
                .color(0x00868B)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C6H4Br2O",true);
        //二溴-2-丙酮酚盐酸盐
        GTQTMaterials.Dfeltamethrind = new Material.Builder(getMaterialsId(), gregtechId("dfeltamethrind"))
                .fluid()
                .color(0x008B00)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C6H4Br2O*HCl",true);
        //二溴菊酸
        GTQTMaterials.Dfeltamethrine = new Material.Builder(getMaterialsId(), gregtechId("dfeltamethrine"))
                .fluid()
                .color(0x008B45)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C8H10Br2O2",true);
        //二溴菊酰氯
        GTQTMaterials.Dfeltamethrinf = new Material.Builder(getMaterialsId(), gregtechId("dfeltamethrinf"))
                .fluid()
                .color(0x008B8B)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C8H10Br2O2*CN",true);
        //间苯氧基苯甲醛
        GTQTMaterials.Dfeltamethring = new Material.Builder(getMaterialsId(), gregtechId("dfeltamethring"))
                .fluid()
                .color(0x009ACD)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C13H10O2",true);
        //α-氰基间苯氧基苯甲醇
        GTQTMaterials.Dfeltamethrinh = new Material.Builder(getMaterialsId(), gregtechId("dfeltamethrinh"))
                .fluid()
                .color(0x00B2EE)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C22H19Br2NO3",true);
    }
}
