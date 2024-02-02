package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.Elements;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.BlastProperty;
import gregtech.api.unification.material.properties.ToolProperty;
import gregtech.api.util.GTUtility;
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
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.material.info.GTQTMaterialIconSet.CUSTOM_MHCSM;

public class FirstDegreeMaterials {
    public FirstDegreeMaterials() {
    }
    private static int startId = 20000;
    private static final int END_ID = startId + 100;
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
                .flags(DISABLE_DECOMPOSITION)
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
                .dust().fluid()
                .color(0xFFFADA)
                .build();

        GTQTMaterials.CSilicon = new Material.Builder(getMaterialsId(), gregtechId("csilicon"))
                .ingot().fluid()
                .color(0x3C3C50).iconSet(METALLIC)
                .flags(GENERATE_FOIL)
                .element(Elements.Si)
                .blast(2273) // no gas tier for silicon
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
        //陨铁
        GTQTMaterials.MeteoricIron = new Material.Builder(getMaterialsId(), gregtechId("meteoric_iron"))
                .ingot().dust().ore()
                .color(0x8B6914).iconSet(METALLIC)
                .flags(GENERATE_PLATE, GENERATE_DENSE,GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR)
                .build();

        //陨铁
        GTQTMaterials.Desh = new Material.Builder(getMaterialsId(), gregtechId("desh"))
                .ingot().dust().ore()
                .color(0xFF7256).iconSet(METALLIC)
                .flags(GENERATE_PLATE,GENERATE_DENSE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR)
                .build();

        GTQTMaterials.Fluix = new Material.Builder(getMaterialsId(), gregtechId("fluix"))
                .gem(1).dust()
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
                .components(MelodicAlloy ,1,NetherStar,1,Naquadah,1)
                .cableProperties(32768,6,8)
                .build();



    }
}
