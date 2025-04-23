package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.attribute.FluidAttributes;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.BlastProperty;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import net.minecraft.util.text.TextFormatting;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static keqing.gtqtcore.api.GTQTValue.gtqtcoreId;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.material.info.GTQTMaterialIconSet.CUSTOM_MHCSM;

public class UnknownCompositionMaterials {

    //无组分材料
    //无任何前置的材料可以写在这里

    private static int startId = 8000;
    private static final int END_ID = startId + 2000;

    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static void init() {
        //8000-8100 100位艾萨
        //铁系泡沫
        GTQTMaterials.IronFront = new Material.Builder(getMaterialsId(), gtqtcoreId("iron_front")).fluid().color(0xD6D6D6).build().setFormula("Front");
        GTQTMaterials.BandedIronFront = new Material.Builder(getMaterialsId(), gtqtcoreId("banded_iron_front")).fluid().color(0xDAA520).build().setFormula("Front");
        GTQTMaterials.BrownLimoniteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("brown_limonite_front")).fluid().color(0xD2691E).build().setFormula("Front");
        GTQTMaterials.YellowLimoniteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("yellow_limonite_front")).fluid().color(0xCDCD00).build().setFormula("Front");
        GTQTMaterials.ChromiteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("chromite_front")).fluid().color(0x8E8E38).build().setFormula("Front");
        GTQTMaterials.IlmeniteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("ilmenite_front")).fluid().color(0x8B814C).build().setFormula("Front");
        GTQTMaterials.MagnetiteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("magnetite_front")).fluid().color(0x8B7500).build().setFormula("Front");
        GTQTMaterials.PyriteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("pyrite_front")).fluid().color(0x8B5A2B).build().setFormula("Front");
        GTQTMaterials.TantaliteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("tantalite_front")).fluid().color(0x8DEEEE).build().setFormula("Front");
        GTQTMaterials.CopperFront = new Material.Builder(getMaterialsId(), gtqtcoreId("copper_front")).fluid().color(0xD2691E).build().setFormula("Front");
        GTQTMaterials.TetrahedriteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("tetrahedrite_front")).fluid().color(0x8B2323).build().setFormula("Front");
        GTQTMaterials.ChalcociteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("chalocite_front")).fluid().color(0x595959).build().setFormula("Front");
        GTQTMaterials.AluminiumFront = new Material.Builder(getMaterialsId(), gtqtcoreId("aluminium_front")).fluid().color(0x1E90FF).build().setFormula("Front");
        GTQTMaterials.BauxiteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("bauxite_front")).fluid().color(0x8B4726).build().setFormula("Front");
        GTQTMaterials.AlmandineFront = new Material.Builder(getMaterialsId(), gtqtcoreId("almandine_front")).fluid().color(0xD70505).build().setFormula("Front");
        GTQTMaterials.PentlanditeFront = new Material.Builder(getMaterialsId(), gtqtcoreId("pentlandite_front")).fluid().color(0x8c800a).build().setFormula("Front");
        GTQTMaterials.ChalcopyriteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("chalcopyrite_front")).fluid().color(0x896826).build().setFormula("Front");
        GTQTMaterials.GrossularFront = new Material.Builder(getMaterialsId(), gtqtcoreId("grossular_front")).fluid().color(0xab5860).build().setFormula("Front");
        GTQTMaterials.MonaziteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("monazite_front")).fluid().color(0x2e3f2e).build().setFormula("Front");
        GTQTMaterials.NickelFront = new Material.Builder(getMaterialsId(), gtqtcoreId("nickel_front")).fluid().color(0xABABD5).build().setFormula("Front");
        GTQTMaterials.PlatinumFront = new Material.Builder(getMaterialsId(), gtqtcoreId("platinum_front")).fluid().color(0xe2e2b2).build().setFormula("Front");
        GTQTMaterials.PyropeFront = new Material.Builder(getMaterialsId(), gtqtcoreId("pyrope_front")).fluid().color(0x682E57).build().setFormula("Front");
        GTQTMaterials.RedstoneFront = new Material.Builder(getMaterialsId(), gtqtcoreId("redstone_front")).fluid().color(0xAC0505).build().setFormula("Front");
        GTQTMaterials.SpessartineFront = new Material.Builder(getMaterialsId(), gtqtcoreId("spessartine_front")).fluid().color(0XDF5A5A).build().setFormula("Front");
        GTQTMaterials.SphaleriteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("sphalerite_front")).fluid().color(0xD9D9D9).build().setFormula("Front");
        GTQTMaterials.TinFront = new Material.Builder(getMaterialsId(), gtqtcoreId("tin_front")).fluid().color(0xFFFFFF).build().setFormula("Front");
        GTQTMaterials.CassiteriteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("cassiterite_front")).fluid().color(0xFFFFFF).build().setFormula("Front");
        GTQTMaterials.GoldFront = new Material.Builder(getMaterialsId(), gtqtcoreId("gold_front")).fluid().color(0xEEEE00).build().setFormula("Front");
        GTQTMaterials.PreciousFront = new Material.Builder(getMaterialsId(), gtqtcoreId("precious_front")).fluid().color(0xCDAD00).build().setFormula("Front");
        GTQTMaterials.LeanGoldFront = new Material.Builder(getMaterialsId(), gtqtcoreId("lean_gold_front")).fluid().color(0xDAA520).build().setFormula("Front");
        GTQTMaterials.RichGoldFront = new Material.Builder(getMaterialsId(), gtqtcoreId("rich_gold_front")).fluid().color(0xD2691E).build().setFormula("Front");
        GTQTMaterials.LeadFront = new Material.Builder(getMaterialsId(), gtqtcoreId("lead_front")).fluid().color(0x9A32CD).build().setFormula("Front");
        GTQTMaterials.GalenaFront = new Material.Builder(getMaterialsId(), gtqtcoreId("galena_front")).fluid().color(0x9B30FF).build().setFormula("Front");
        GTQTMaterials.WulfeniteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("wulfenite_front")).fluid().color(0x8B8B00).build().setFormula("Front");
        GTQTMaterials.CrocoiteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("crocoite_front")).fluid().color(0x8B8B7A).build().setFormula("Front");
        GTQTMaterials.StibniteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("stibnite_front")).fluid().color(0x303030).build().setFormula("Front");
        GTQTMaterials.ScheeliteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("scheelite_front")).fluid().color(0xB8860B).build().setFormula("Front");
        GTQTMaterials.TungstateFront = new Material.Builder(getMaterialsId(), gtqtcoreId("tungstate_front")).fluid().color(0x303030).build().setFormula("Front");
        GTQTMaterials.UraniniteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("uraninite_front")).fluid().color(0x303030).build().setFormula("Front");
        GTQTMaterials.PitchblendeFront = new Material.Builder(getMaterialsId(), gtqtcoreId("pitchblende_front")).fluid().color(0xBCEE68).build().setFormula("Front");
        GTQTMaterials.NaquadahOxideFront = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadahoxide_front")).fluid().color(0xCD853F).build().setFormula("Front");
        GTQTMaterials.BorniteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("bornite_front")).fluid().color(0xCD853F).build().setFormula("Front");
        GTQTMaterials.BastnasiteFront = new Material.Builder(getMaterialsId(), gtqtcoreId("bastnasite_front")).fluid().color(0x8B4726).build().setFormula("Front");

        //
        startId = 8100;
        //
        //快乐细菌
        //原始杂质
        GTQTMaterials.Enzymesaz = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymesaz"))
                .fluid()
                .color(0xCDAA7D)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.Enzymesbz = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymesbz"))
                .fluid()
                .color(0xCD8162)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.Enzymescz = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymescz"))
                .fluid()
                .color(0xD2691E)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.Enzymesdz = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymesdz"))
                .fluid()
                .color(0x68228B)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.Enzymesez = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymesez"))
                .fluid()
                .color(0x0000EE)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //这里注册线性方程组的几个基础解系
        GTQTMaterials.Enzymesa = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymesa"))
                .fluid()
                .color(0xEE00EE)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：1 0 0 0 0", true);

        GTQTMaterials.Enzymesb = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymesb"))
                .fluid()
                .color(0xCD3333)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：0 1 0 0 0", true);

        GTQTMaterials.Enzymesc = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymesc"))
                .fluid()
                .color(0xB03060)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：0 0 1 0 0", true);

        GTQTMaterials.Enzymesd = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymesd"))
                .fluid()
                .color(0xB4EEB4)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：0 0 0 1 0", true);

        GTQTMaterials.Enzymese = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymese"))
                .fluid()
                .color(0xBDB76B)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：0 0 0 0 1", true);

        //富集生物培养基
        GTQTMaterials.Rnzymes = new Material.Builder(getMaterialsId(), gtqtcoreId("rnzymes"))
                .fluid()
                .color(0xFA8072)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //普适矿处菌种 101
        GTQTMaterials.Enzymesaa = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymese_101"))
                .fluid()
                .color(0x00E5EE)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：1 0 1 0 0", true);
        //定向铂系菌种 102
        GTQTMaterials.Enzymesab = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymese_102"))
                .fluid()
                .color(0x54FF9F)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：1 0 1 1 0", true);
        //普适魔性菌种 103
        GTQTMaterials.Enzymesac = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymese_103"))
                .fluid()
                .color(0x6B8E23)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：1 1 0 1 0", true);
        //普适副产菌种 104
        GTQTMaterials.Enzymesad = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymese_104"))
                .fluid()
                .color(0x76EE00)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：1 1 0 1 1", true);
        //
        //工业合成菌种I 201
        GTQTMaterials.Enzymesba = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymese_201"))
                .fluid()
                .color(0xA0522D)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：2 1 1 3 1", true);
        //工业还原菌种 202
        GTQTMaterials.Enzymesbb = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymese_202"))
                .fluid()
                .color(0x9932CC)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：1 2 3 1 1", true);
        //工业氧化菌种 203
        GTQTMaterials.Enzymesbc = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymese_203"))
                .fluid()
                .color(0x8FBC8F)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：1 3 2 1 1", true);
        //工业催化菌种 204
        GTQTMaterials.Enzymesbd = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymese_204"))
                .fluid()
                .color(0x8B7D7B)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：2 1 1 3 1", true);
        //
        //定向脂肪酶 301
        GTQTMaterials.Enzymesca = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymese_301"))
                .fluid()
                .color(0x838B8B)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：4 1 1 3 2", true);
        //普适发酵酶 302
        GTQTMaterials.Enzymescb = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymese_302"))
                .fluid()
                .color(0x8B008B)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：2 4 2 3 1", true);
        //定向发酵酶 303
        GTQTMaterials.Enzymescc = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymese_303"))
                .fluid()
                .color(0x8A2BE2)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：2 3 2 4 1", true);
        //
        //活性诱变酶 401
        GTQTMaterials.Enzymesda = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymese_401"))
                .fluid()
                .color(0x8B0000)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：2 5 2 4 3", true);

        //定向镧系菌种
        GTQTMaterials.Enzymesea = new Material.Builder(getMaterialsId(), gtqtcoreId("enzymese_501"))
                .fluid()
                .color(0x54FF9F)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("性状：* * * * *", true);

        //
        startId = 8200;
        //

        //破乳剂
        GTQTMaterials.Demulsifier = new Material.Builder(getMaterialsId(), gtqtcoreId("demulsifier"))
                .fluid()
                .color(0xC6E2FF)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.Pyrotheum = new Material.Builder(getMaterialsId(), gtqtcoreId("pyrotheum"))
                .fluid()
                .color(14601000)
                .build()
                .setFormula("Pyrotheum");


        //盐水
        GTQTMaterials.SeaWater = new Material.Builder(getMaterialsId(), gtqtcoreId("sea_water"))
                .fluid()
                .color(0xB2DFEE)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //含杂预处理原油
        GTQTMaterials.PreTreatedCrudeOilContainingImpurities = new Material.Builder(getMaterialsId(), gtqtcoreId("pre_treated_crude_oil_containing_impurities"))
                .fluid()
                .color(0x262626)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //预处理原油
        GTQTMaterials.PreTreatedCrudeOil = new Material.Builder(getMaterialsId(), gtqtcoreId("pre_treated_crude_oil"))
                .fluid()
                .color(0x2F4F4F)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //环氧丙烷
        GTQTMaterials.PropyleneOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("propylene_oxide"))
                .fluid()
                .color(0xBDB76B)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //沥青
        GTQTMaterials.Asphalt = new Material.Builder(getMaterialsId(), gtqtcoreId("asphalt"))
                .fluid()
                .color(0x282828)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //蜡油
        GTQTMaterials.WaxOil = new Material.Builder(getMaterialsId(), gtqtcoreId("wax_oil"))
                .fluid()
                .color(0x8B8B00)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //水煤气
        GTQTMaterials.WaterGas = new Material.Builder(getMaterialsId(), gtqtcoreId("water_gas"))
                .fluid()
                .color(0xA9A9A9)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //常压渣油
        GTQTMaterials.AtmosphericResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("atmospheric_residue"))
                .fluid()
                .color(0x8B1A1A)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //减压渣油
        GTQTMaterials.VacuumResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("vacuum_residue"))
                .fluid()
                .color(0xCD4F39)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //防爆剂
        GTQTMaterials.MTBE = new Material.Builder(getMaterialsId(), gtqtcoreId("mtbe"))
                .fluid()
                .color(0xC0FF3E)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //轻柴油
        GTQTMaterials.DieselLight = new Material.Builder(getMaterialsId(), gtqtcoreId("diesel_light"))
                .fluid()
                .color(0x8B5A00)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //重柴油
        GTQTMaterials.DieselHeavy = new Material.Builder(getMaterialsId(), gtqtcoreId("diesel_heavy"))
                .fluid()
                .color(0x8B5742)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //含硫轻柴油
        GTQTMaterials.SDieselLight = new Material.Builder(getMaterialsId(), gtqtcoreId("sdiesel_light"))
                .fluid()
                .color(0x8B4C39)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //含硫重柴油
        GTQTMaterials.SDieselHeavy = new Material.Builder(getMaterialsId(), gtqtcoreId("sdiesel_heavy"))
                .fluid()
                .color(0x8B4513)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //粗柴油
        GTQTMaterials.GasOil = new Material.Builder(getMaterialsId(), gtqtcoreId("gas_oil"))
                .fluid()
                .color(0x8B7355)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //直馏汽油
        GTQTMaterials.Distilledgasoline = new Material.Builder(getMaterialsId(), gtqtcoreId("distilledgasoline"))
                .fluid()
                .color(0x8B7355)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //油浆
        GTQTMaterials.OilSlurry = new Material.Builder(getMaterialsId(), gtqtcoreId("oil_slurry"))
                .fluid()
                .color(0x8B7355)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //油气
        GTQTMaterials.OilGas = new Material.Builder(getMaterialsId(), gtqtcoreId("oil_gas"))
                .fluid()
                .color(0x8B7355)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //轻度加氢裂解轻柴油
        GTQTMaterials.LightlyHydroCrackedDieselLight = new Material.Builder(getMaterialsId(), gtqtcoreId("LightlyHydroCrackedDieselLight"))
                .fluid()
                .color(0xFFC125)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //轻度蒸汽裂解轻柴油
        GTQTMaterials.LightlySteamCrackedDieselLight = new Material.Builder(getMaterialsId(), gtqtcoreId("LightlySteamCrackedDieselLight"))
                .fluid()
                .color(0xFFB90F)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //中度加氢裂解轻柴油
        GTQTMaterials.SeverelyHydroCrackedDieselLight = new Material.Builder(getMaterialsId(), gtqtcoreId("SeverelyHydroCrackedDieselLight"))
                .fluid()
                .color(0xFFA54F)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //中度蒸汽裂解轻柴油
        GTQTMaterials.SeverelySteamCrackedDieselLight = new Material.Builder(getMaterialsId(), gtqtcoreId("SeverelySteamCrackedDieselLight"))
                .fluid()
                .color(0xFF8C00)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //轻度加氢裂解重柴油
        GTQTMaterials.LightlyHydroCrackedDieselHeavy = new Material.Builder(getMaterialsId(), gtqtcoreId("LightlyHydroCrackedDieselHeavy"))
                .fluid()
                .color(0xFF7F50)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //轻度蒸汽裂解重柴油
        GTQTMaterials.LightlySteamCrackedDieselHeavy = new Material.Builder(getMaterialsId(), gtqtcoreId("LightlySteamCrackedDieselHeavy"))
                .fluid()
                .color(0xFF7F24)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //中度加氢裂解重柴油
        GTQTMaterials.SeverelyHydroCrackedDieselHeavy = new Material.Builder(getMaterialsId(), gtqtcoreId("SeverelyHydroCrackedDieselHeavy"))
                .fluid()
                .color(0xFF7F00)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //中度蒸汽裂解重柴油
        GTQTMaterials.SeverelySteamCrackedDieselHeavy = new Material.Builder(getMaterialsId(), gtqtcoreId("SeverelySteamCrackedDieselHeavy"))
                .fluid()
                .color(0xFF7256)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //轻度加氢裂解煤油
        GTQTMaterials.LightlyHydroCrackedCoalOil = new Material.Builder(getMaterialsId(), gtqtcoreId("LightlyHydroCrackedCoalOil"))
                .fluid()
                .color(0xFF7F50)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //轻度蒸汽裂解煤油
        GTQTMaterials.LightlySteamCrackedCoalOil = new Material.Builder(getMaterialsId(), gtqtcoreId("LightlySteamCrackedCoalOil"))
                .fluid()
                .color(0xFF7F24)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //中度加氢裂解煤油
        GTQTMaterials.SeverelyHydroCrackedCoalOil = new Material.Builder(getMaterialsId(), gtqtcoreId("SeverelyHydroCrackedCoalOil"))
                .fluid()
                .color(0xFF7F00)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //中度蒸汽裂解煤油
        GTQTMaterials.SeverelySteamCrackedCoalOil = new Material.Builder(getMaterialsId(), gtqtcoreId("SeverelySteamCrackedCoalOil"))
                .fluid()
                .color(0xFF7256)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //轻度加氢裂解渣油
        GTQTMaterials.LightlyHydroCrackedOil = new Material.Builder(getMaterialsId(), gtqtcoreId("LightlyHydroCrackedOil"))
                .fluid()
                .color(0xFF7F50)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //轻度蒸汽裂解渣油
        GTQTMaterials.LightlySteamCrackedOil = new Material.Builder(getMaterialsId(), gtqtcoreId("LightlySteamCrackedOil"))
                .fluid()
                .color(0xFF7F24)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //重度加氢裂解渣油
        GTQTMaterials.SeverelyHydroCrackedOil = new Material.Builder(getMaterialsId(), gtqtcoreId("SeverelyHydroCrackedOil"))
                .fluid()
                .color(0xFF7F00)
                .flags(DISABLE_DECOMPOSITION)
                .build();
        //重度蒸汽裂解渣油
        GTQTMaterials.SeverelySteamCrackedOil = new Material.Builder(getMaterialsId(), gtqtcoreId("SeverelySteamCrackedOil"))
                .fluid()
                .color(0xFF7256)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //酚醛
        GTQTMaterials.Phenolic = new Material.Builder(getMaterialsId(), gtqtcoreId("phenolic"))
                .fluid()
                .color(0xFF7256)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(C6H6O·CH2O)x");

        //乳胶
        GTQTMaterials.Latex = new Material.Builder(getMaterialsId(), gtqtcoreId("latex"))
                .fluid()
                .color(0xFFFADA)
                .build();


        //独居石稀土浊溶液
        GTQTMaterials.MonaziteRareEarthTurbid = new Material.Builder(getMaterialsId(), gtqtcoreId("monazite_rare_earth_turbid"))
                .fluid()
                .color(0xd89045).iconSet(SHINY)
                .build();

        //稀释独居石稀土泥浆
        GTQTMaterials.DiluteMonaziteRareEarthMud = new Material.Builder(getMaterialsId(), gtqtcoreId("dilute_monazite_rare_earth_mud"))
                .fluid()
                .color(0xFFB6C1).iconSet(SHINY)
                .build();

        //硫酸独居石
        GTQTMaterials.SulfuricAcidMonazite = new Material.Builder(getMaterialsId(), gtqtcoreId("sulfuric_acid_monazite"))
                .fluid()
                .color(0xFF82AB).iconSet(SHINY)
                .build();

        //独居石稀土滤渣粉
        GTQTMaterials.MonaziteRareEarthFilterResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("monazite_rare_earth_filter_residue"))
                .dust()
                .color(0xFF8247).iconSet(SHINY)
                .build();

        //磷酸钍滤饼
        GTQTMaterials.ThoriumPhosphateFilterCake = new Material.Builder(getMaterialsId(), gtqtcoreId("thorium_phosphate_filter_cake"))
                .dust()
                .color(0xFF6EB4).iconSet(SHINY)
                .build();

        //磷酸钍精粉
        GTQTMaterials.ThoriumPhosphateConcentrate = new Material.Builder(getMaterialsId(), gtqtcoreId("thorium_phosphate_concentrate"))
                .dust()
                .color(0xFF6A6A).iconSet(SHINY)
                .build();

        //中和独居石稀土滤渣粉
        GTQTMaterials.NeutralizationMonaziteRareEarthFilterResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("neutralization_monazite_rare_earth_filter_residue"))
                .dust()
                .color(0xFF69B4).iconSet(SHINY)
                .build();

        //浓缩独居石稀土氢氧化物粉
        GTQTMaterials.ConcentratedMonaziteRareEarthHydroxide = new Material.Builder(getMaterialsId(), gtqtcoreId("concentrated_monazite_rare_earth_hydroxide"))
                .dust()
                .color(0xFF6347).iconSet(SHINY)
                .build();

        //铀滤渣粉
        GTQTMaterials.UraniumFilterResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("uranium_filter_residue"))
                .dust()
                .color(0xFF3030).iconSet(SHINY)
                .build();

        //干燥浓缩硝酸独居石稀土
        GTQTMaterials.DryConcentratedNitrateMonaziteRareEarth = new Material.Builder(getMaterialsId(), gtqtcoreId("dry_concentrated_nitrate_monazite_rare_earth"))
                .dust()
                .color(0xFF1493).iconSet(SHINY)
                .build();

        //独居石罕土沉淀粉
        GTQTMaterials.SolitaryStoneRareSoilSedimentation = new Material.Builder(getMaterialsId(), gtqtcoreId("solitary_stone_rare_soil_sedimentation"))
                .dust()
                .color(0xFF00FF).iconSet(SHINY)
                .build();

        //异质卤化独居石稀土混合物
        GTQTMaterials.HeterogeneousHalogenatedMonaziteRareEarthMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("heterogeneous_halogenated_monazite_rare_earth_mixture"))
                .dust()
                .color(0xFF0000).iconSet(SHINY)
                .build();

        //饱和独居石稀土
        GTQTMaterials.SaturatedMonaziteDopedWithRareEarth = new Material.Builder(getMaterialsId(), gtqtcoreId("saturated_monazite_doped_with_rare_earth"))
                .dust()
                .color(0xd89045).iconSet(SHINY)
                .build();

        //钐精
        GTQTMaterials.SamariumEssence = new Material.Builder(getMaterialsId(), gtqtcoreId("samarium_essence"))
                .dust()
                .color(0xF5DEB3).iconSet(SHINY)
                .build();


        //氟碳镧铈稀土浊
        GTQTMaterials.FluorocarbonLanthanumCeriumRareEarthTurbidity = new Material.Builder(getMaterialsId(), gtqtcoreId("fluorocarbon_lanthanum_cerium_rare_earth_turbidity"))
                .fluid()
                .color(0x8B7500).iconSet(SHINY)
                .build();

        //蒸汽裂化氟碳镧铈泥浆
        GTQTMaterials.SteamCrackingFluorocarbonLanthanumCeriumSlurry = new Material.Builder(getMaterialsId(), gtqtcoreId("steam_cracking_fluorocarbon_lanthanum_cerium_slurry"))
                .fluid()
                .color(0x8B7355).iconSet(SHINY)
                .build();

        //调制氟碳镧铈泥浆
        GTQTMaterials.ModulationFluorocarbonLanthanumCeriumSlurry = new Material.Builder(getMaterialsId(), gtqtcoreId("modulation_fluorocarbon_lanthanum_cerium_slurry"))
                .fluid()
                .color(0x8B6969).iconSet(SHINY)
                .build();

        //过滤氟碳镧铈泥浆
        GTQTMaterials.FilterFluorocarbonLanthanumCeriumSlurry = new Material.Builder(getMaterialsId(), gtqtcoreId("filter_fluorocarbon_lanthanum_cerium_slurry"))
                .dust()
                .color(0x8B6914).iconSet(SHINY)
                .build();

        //氟碳镧铈稀土氧化物粉
        GTQTMaterials.FluorocarbonLanthanumCeriumRareEarthOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("fluorocarbon_lanthanum_cerium_rare_earth_oxide"))
                .dust()
                .color(0x8B668B).iconSet(SHINY)
                .build();

        //酸浸氟碳镧铈稀土氧化物粉
        GTQTMaterials.AcidFluorocarbonLanthanumCeriumRareEarthOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("acid_fluorocarbon_lanthanum_cerium_rare_earth_oxide"))
                .fluid()
                .color(0x8B6508).iconSet(SHINY)
                .build();

        //焙烧稀土氧化物粉
        GTQTMaterials.RoastedRareEarthOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("roasted_rare_earth_oxide"))
                .dust()
                .color(0x8B636C).iconSet(SHINY)
                .build();

        //氧化铈稀土氧化物粉
        GTQTMaterials.CeriumOxideRareEarthOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("cerium_oxide_rare_earth_oxide"))
                .fluid()
                .color(0x8B5F65).iconSet(SHINY)
                .build();

        //氟碳镧铈罕土氧化物粉
        GTQTMaterials.FluorocarbonLanthanumCeriumRareSoilOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("fluorocarbon_lanthanum_cerium_rare_soil_oxide"))
                .dust()
                .color(0x8B5A2B).iconSet(SHINY)
                .build();

        //氟碳镧铈罕土氧化物悬浊液
        GTQTMaterials.FluorocarbonLanthanumCeriumRareSoilOxideSuspension = new Material.Builder(getMaterialsId(), gtqtcoreId("fluorocarbon_lanthanum_cerium_rare_soil_oxide_suspension"))
                .fluid()
                .color(0x8B5A00).iconSet(SHINY)
                .build();

        //钐稀土精粉
        GTQTMaterials.SamariumRareEarth = new Material.Builder(getMaterialsId(), gtqtcoreId("samarium_rare_earth"))
                .dust()
                .color(0x8B5742).iconSet(SHINY)
                .build();

        //氟化钐稀土精粉
        GTQTMaterials.SamariumFluorideRareEarthRefined = new Material.Builder(getMaterialsId(), gtqtcoreId("samarium_fluoride_rare_earth_refined"))
                .fluid()
                .color(0x8B4C39).iconSet(SHINY)
                .build();

        //钐-铽混合物粉
        GTQTMaterials.SamariumTerbiumMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("samarium_terbium_mixture"))
                .fluid()
                .color(0x8B4789).iconSet(SHINY)
                .build();

        GTQTMaterials.CrudeRareEarthTurbidSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("crude_rare_earth_turbid_solution"))
                .fluid()
                .color(0x9C5C6B)
                .iconSet(DULL)
                .build();

        GTQTMaterials.NitratedRareEarthTurbidSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("nitrated_rare_earth_turbid_solution"))
                .fluid()
                .color(0x754550)
                .iconSet(DULL)
                .build();
        //  25254 Rare Earth Hydroxides Solution
        GTQTMaterials.RareEarthHydroxidesSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("rare_earth_hydroxides_solution"))
                .fluid()
                .color(0x434327)
                .flags(DISABLE_DECOMPOSITION)
                .components(RareEarth, 1, Oxygen, 1, Hydrogen, 1, Water, 1)
                .build();
        //  25255 Rare Earth Chlorides Slurry
        GTQTMaterials.RareEarthChloridesSlurry = new Material.Builder(getMaterialsId(), gtqtcoreId("rare_earth_chlorides_slurry"))
                .dust()
                .color(0x838367)
                .iconSet(ROUGH)
                .flags(DISABLE_DECOMPOSITION)
                .components(RareEarth, 1, Chlorine, 1, Water, 1)
                .build();
        //  25256 Low-purity Rare Earth Chlorides Solution
        GTQTMaterials.LowPurityRareEarthChloridesSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("low_purity_rare_earth_chlorides_solution"))
                .fluid()
                .color(0x838333)
                .iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION)
                .components(RareEarth, 1, Chlorine, 1, Water, 2)
                .build();
        //  25257 Roughly Purified Rare Earth Chlorides Solution
        GTQTMaterials.RoughlyPurifiedRareEarthChloridesSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("roughly_purified_rare_earth_chlorides_solution"))
                .fluid()
                .color(0xA2A27F)
                .iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION)
                .components(LowPurityRareEarthChloridesSolution, 4, AquaRegia, 2)
                .build();
        //  25258 High Purity Rare Earth Chlorides Slurry
        GTQTMaterials.HighPurityRareEarthChloridesSlurry = new Material.Builder(getMaterialsId(), gtqtcoreId("high_purity_rare_earth_chlorides_slurry"))
                .dust()
                .color(0x838367)
                .iconSet(ROUGH)
                .flags(DISABLE_DECOMPOSITION)
                .components(RareEarth, 1, Chlorine, 1, Water, 1)
                .build();
        //  25259 High Purity Rare Earth Chlorides Solution
        GTQTMaterials.HighPurityRareEarthChloridesSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("high_purity_rare_earth_chlorides_solution"))
                .fluid()
                .color(0x838367)
                .flags(DISABLE_DECOMPOSITION)
                .components(RareEarth, 1, Chlorine, 1, Water, 1)
                .build();
        //  25260 Low Purity Rare Earth Chlorides Slag
        GTQTMaterials.LowPurityRareEarthChloridesSlag = new Material.Builder(getMaterialsId(), gtqtcoreId("low_purity_rare_earth_chlorides_slag"))
                .dust()
                .color(0x62624D)
                .iconSet(DULL)
                .build();

        GTQTMaterials.RareEarthChloridesSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("rare_earth_chlorides_solution"))
                .fluid()
                .color(0x838367)
                .flags(DISABLE_DECOMPOSITION)
                .components(RareEarth, 1, Chlorine, 1, Water, 1)
                .build();

        //  24164 Rare Earth Chlorides Enriched Solution
        RareEarthChloridesEnrichedSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("rare_earth_chlorides_enriched_solution"))
                .liquid()
                .color(RareEarthChloridesSolution.getMaterialRGB() - 20)
                .iconSet(DULL)
                .build();

        //  24165 Rare Earth Chlorides Diluted Solution
        RareEarthChloridesDilutedSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("rare_earth_chlorides_diluted_solution"))
                .liquid()
                .color(RareEarthChloridesSolution.getMaterialRGB() - 40)
                .iconSet(DULL)
                .build();

        //  24163 Rare Earth Chlorides Concentrate
        RareEarthChloridesConcentrate = new Material.Builder(getMaterialsId(), gtqtcoreId("rare_earth_chlorides_concentrate"))
                .liquid()
                .color(RareEarthChloridesSolution.getMaterialRGB() - 10)
                .iconSet(DULL)
                .build();

        //  24166 Chlorinated Rare Earth Waste Fluid
        ChlorinatedRareEarthWasteFluid = new Material.Builder(getMaterialsId(), gtqtcoreId("chlorinated_rare_earth_waste_fluid"))
                .liquid()
                .color(RareEarthChloridesSolution.getMaterialRGB() - 80)
                .iconSet(DULL)
                .build();

        GTQTMaterials.LeachedTurpentine = new Material.Builder(getMaterialsId(), gtqtcoreId("leached_turpentine"))
                .fluid()
                .color(0x330D16)
                .flags(DISABLE_DECOMPOSITION)
                .components(GTQTMaterials.Turpentine, 1, RareEarth, 1)
                .build();

        GTQTMaterials.SteamCrackedTurpentine = new Material.Builder(getMaterialsId(), gtqtcoreId("steamcracked_turpentine")).fluid().color(0x634D56).build();

        GTQTMaterials.BZMedium = new Material.Builder(getMaterialsId(), gtqtcoreId("bz_medium")).fluid().color(0xA2FD35).build(); //TODO "The Belousov-Zhabotinsky Reaction" tooltip

        GTQTMaterials.MutatedLivingSolder = new Material.Builder(getMaterialsId(), gtqtcoreId("mutated_living_solder"))
                .fluid()
                .color(0x936D9B)
                .iconSet(DULL)
                .build()
                .setFormula("?Sn?Bi?", true);

        GTQTMaterials.RichNitrogenMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("rich_nitrogen_mixture")).gas().color(0x6891D8).build();

        GTQTMaterials.RichAmmoniaMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("rich_ammonia_mixture")).fluid().color(0x708ACD).build();

        GTQTMaterials.Brine = new Material.Builder(getMaterialsId(), gtqtcoreId("brine")).fluid().color(0xFCFC8A).build();

        GTQTMaterials.ChlorinatedBrine = new Material.Builder(getMaterialsId(), gtqtcoreId("chlorinated_brine")).fluid().color(0xFAFC8A).build();

        GTQTMaterials.ChalcogenAnodeMud = new Material.Builder(getMaterialsId(), gtqtcoreId("chalcogen_anode_mud")).dust().color(0x8A3324).iconSet(FINE).build();

        GTQTMaterials.MethylamineMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("methylamine_mixture")).fluid().color(0xAA4400).build();

        GTQTMaterials.EDP = new Material.Builder(getMaterialsId(), gtqtcoreId("edp")).fluid().color(0xFBFF17).build();

        GTQTMaterials.PhosphoreneSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("phosphorene_solution")).fluid().color(0x465966).build();

        GTQTMaterials.SodioIndene = new Material.Builder(getMaterialsId(), gtqtcoreId("sodio_indene")).fluid().color(0x1D1C24).build();

        GTQTMaterials.SteamCrackedSodioIndene = new Material.Builder(getMaterialsId(), gtqtcoreId("steam_cracked_sodio_indene"))
                .fluid()
                .color(0x1C1A29).build();

        GTQTMaterials.MolybdenumFlue = new Material.Builder(getMaterialsId(), gtqtcoreId("molybdenum_flue")).gas().color(0x39194A).build();

        GTQTMaterials.TraceRheniumFlue = new Material.Builder(getMaterialsId(), gtqtcoreId("trace_rhenium_flue")).gas().color(0x96D6D5).build();

        GTQTMaterials.FracturingFluid = new Material.Builder(getMaterialsId(), gtqtcoreId("fracturing_fluid")).fluid().color(0x96D6D5).build();

        GTQTMaterials.BedrockSmoke = new Material.Builder(getMaterialsId(), gtqtcoreId("bedrock_smoke")).gas().color(0x525252).build();

        GTQTMaterials.Bedrock = new Material.Builder(getMaterialsId(), gtqtcoreId("bedrock"))
                .dust()
                .liquid()
                .color(0x404040)
                .iconSet(ROUGH)
                .flags(GENERATE_PLATE)
                .build();

        GTQTMaterials.BedrockSootSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("bedrock_soot_solution")).fluid().color(0x1E2430).build();

        GTQTMaterials.CleanBedrockSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("clean_bedrock_solution")).fluid().color(0xA89F9E).build();

        GTQTMaterials.HeavyBedrockSmoke = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_bedrock_smoke")).gas().color(0x242222).build();

        GTQTMaterials.MediumBedrockSmoke = new Material.Builder(getMaterialsId(), gtqtcoreId("medium_bedrock_smoke")).gas().color(0x2E2C2C).build();

        GTQTMaterials.LightBedrockSmoke = new Material.Builder(getMaterialsId(), gtqtcoreId("light_bedrock_smoke")).gas().color(0x363333).build();

        GTQTMaterials.UltralightBedrockSmoke = new Material.Builder(getMaterialsId(), gtqtcoreId("ultralight_bedrock_smoke")).gas().color(0x403D3D).build();

        GTQTMaterials.HeavyTaraniumGas = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_taranium_gas")).gas().color(0x262626).build();

        GTQTMaterials.MediumTaraniumGas = new Material.Builder(getMaterialsId(), gtqtcoreId("medium_taranium_gas")).gas().color(0x313131).build();

        GTQTMaterials.LightTaraniumGas = new Material.Builder(getMaterialsId(), gtqtcoreId("light_taranium_gas")).gas().color(0x404040).build();

        GTQTMaterials.BedrockGas = new Material.Builder(getMaterialsId(), gtqtcoreId("bedrock_gas")).gas().color(0x575757).build();

        GTQTMaterials.CrackedHeavyTaranium = new Material.Builder(getMaterialsId(), gtqtcoreId("cracked_heavy_taranium")).fluid().color(0x1F2B2E).build();

        GTQTMaterials.CrackedMediumTaranium = new Material.Builder(getMaterialsId(), gtqtcoreId("cracked_medium_taranium")).fluid().color(0x29393D).build();

        GTQTMaterials.CrackedLightTaranium = new Material.Builder(getMaterialsId(), gtqtcoreId("cracked_light_taranium")).fluid().color(0x374C52).build();

        GTQTMaterials.EnrichedBedrockSootSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("enriched_bedrock_soot_solution")).fluid().color(0x280C26).build();

        GTQTMaterials.CleanEnrichedBedrockSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("clean_enriched_bedrock_solution")).fluid().color(0x828C8C).build();

        GTQTMaterials.HeavyEnrichedBedrockSmoke = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_enriched_bedrock_smoke")).gas().color(0x1A2222).build();

        GTQTMaterials.MediumEnrichedBedrockSmoke = new Material.Builder(getMaterialsId(), gtqtcoreId("medium_enriched_bedrock_smoke")).gas().color(0x1E2C2C).build();

        GTQTMaterials.LightEnrichedBedrockSmoke = new Material.Builder(getMaterialsId(), gtqtcoreId("light_enriched_bedrock_smoke")).gas().color(0x163333).build();

        GTQTMaterials.HeavyEnrichedTaraniumGas = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_enriched_taranium_gas")).gas().color(0x1F2626).build();

        GTQTMaterials.MediumEnrichedTaraniumGas = new Material.Builder(getMaterialsId(), gtqtcoreId("medium_enriched_taranium_gas")).gas().color(0x1F3131).build();

        GTQTMaterials.LightEnrichedTaraniumGas = new Material.Builder(getMaterialsId(), gtqtcoreId("light_enriched_taranium_gas")).gas().color(0x1F4040).build();

        GTQTMaterials.CrackedHeavyEnrichedTaranium = new Material.Builder(getMaterialsId(), gtqtcoreId("cracked_heavy_enriched_taranium")).fluid().color(0x2E1F2E).build();

        GTQTMaterials.CrackedMediumEnrichedTaranium = new Material.Builder(getMaterialsId(), gtqtcoreId("cracked_medium_enriched_taranium")).fluid().color(0x29393D).build();

        GTQTMaterials.CrackedLightEnrichedTaranium = new Material.Builder(getMaterialsId(), gtqtcoreId("cracked_light_enriched_taranium")).fluid().color(0x374C52).build();

        GTQTMaterials.EnergeticNaquadria = new Material.Builder(getMaterialsId(), gtqtcoreId("energetic_naquadria")).fluid().color(0x202020).build();

        GTQTMaterials.LightHyperFuel = new Material.Builder(getMaterialsId(), gtqtcoreId("light_hyper_fuel")).fluid().color(0x8C148C).build();

        GTQTMaterials.MediumHyperFuel = new Material.Builder(getMaterialsId(), gtqtcoreId("medium_hyper_fuel")).fluid().color(0xDC0A0A).build();

        GTQTMaterials.HeavyHyperFuel = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_hyper_fuel")).fluid().color(0x1E5064).build();

        GTQTMaterials.StellarMaterialResidueA = (new Material.Builder(getMaterialsId(), gtqtcoreId("stellar_material_residue_a"))).fluid().plasma().color(14601000).build().setFormula("ST-α", true);
        GTQTMaterials.StellarMaterialResidueB = (new Material.Builder(getMaterialsId(), gtqtcoreId("stellar_material_residue_b"))).fluid().plasma().color(14600000).build().setFormula("ST-β", true);
        GTQTMaterials.StellarMaterialResidueC = (new Material.Builder(getMaterialsId(), gtqtcoreId("stellar_material_residue_c"))).fluid().plasma().color(14639000).build().setFormula("ST-γ", true);
        GTQTMaterials.StellarMaterial = (new Material.Builder(getMaterialsId(), gtqtcoreId("stellar_material"))).fluid().plasma().color(14638000).build().setFormula("ST-ST", true);

        GTQTMaterials.LightNaquadahFuel = (new Material.Builder(getMaterialsId(), gtqtcoreId("light_naquadah_fuel"))).fluid().color(14638000).build().setFormula("NQ", true);
        GTQTMaterials.MediumNaquadahFuel = (new Material.Builder(getMaterialsId(), gtqtcoreId("medium_naquadah_fuel"))).fluid().color(14638000).build().setFormula("-NQ-", true);
        GTQTMaterials.HeavyNaquadahFuel = (new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_naquadah_fuel"))).fluid().color(14638000).build().setFormula("+NQ+", true);

        GTQTMaterials.MagnetoHydrodynamicallyConstrainedStarMatter = new Material.Builder(getMaterialsId(), gtqtcoreId("magneto_hydrodynamically_constrained_star_matter"))
                .ingot()
                .liquid(new FluidBuilder().temperature(6000000))
                .iconSet(CUSTOM_MHCSM)
                .flags(NO_SMELTING, GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR)
                .build();


        GTQTMaterials.DragonBreath = new Material.Builder(getMaterialsId(), gtqtcoreId("dragon_breath"))
                .liquid(new FluidBuilder().attributes(FluidAttributes.ACID))
                .color(0x9400D3)
                .build()
                .setFormula("Dc?", false);

        GTQTMaterials.ConcentrateDragonBreath = new Material.Builder(getMaterialsId(), gtqtcoreId("concentrate_dragon_breath"))
                .liquid(new FluidBuilder().attributes(FluidAttributes.ACID))
                .color(0x9400D3)
                .build()
                .setFormula("Dc2?", true);


        GTQTMaterials.DragonBlood = new Material.Builder(getMaterialsId(), gtqtcoreId("dragon_blood"))
                .liquid(new FluidBuilder().attributes(FluidAttributes.ACID))
                .color(0xDC2814)
                .iconSet(DULL)
                .build()
                .setFormula("*Dc*Rn?", true);

        //
        GTQTMaterials.SuperGlue = new Material.Builder(getMaterialsId(), gtqtcoreId("super_glue"))
                .fluid()
                .color(0xFAFAD2)
                .build();

        GTQTMaterials.UltraGlue = new Material.Builder(getMaterialsId(), gtqtcoreId("ultra_glue"))
                .fluid()
                .color(0xB2DFEE)
                .build();

        GTQTMaterials.BacterialGrowthMedium = new Material.Builder(getMaterialsId(), gtqtcoreId("bacterial_growth_medium"))
                .liquid()
                .color(0x0b2e12)
                .build()
                .setFormula("For Bacteria", true);

        GTQTMaterials.DepletedGrowthMedium = new Material.Builder(getMaterialsId(), gtqtcoreId("depleted_growth_medium"))
                .liquid()
                .color(0x071209)
                .build()
                .setFormula("Depleted", true);

        GTQTMaterials.Shewanella = new Material.Builder(getMaterialsId(), gtqtcoreId("shewanella"))
                .dust()
                .color(0x8752ab)
                .iconSet(METALLIC)
                .build()
                .setFormula("Bacteria", true);

        GTQTMaterials.BrevibacteriumFlavium = new Material.Builder(getMaterialsId(), gtqtcoreId("brevibacterium_flavium"))
                .dust()
                .color(0x2c4d24)
                .iconSet(ROUGH)
                .build()
                .setFormula("Bacteria", true);

        GTQTMaterials.BifidobacteriumBreve = new Material.Builder(getMaterialsId(), gtqtcoreId("bifidobacterium_breve"))
                .dust()
                .color(0x377528)
                .iconSet(ROUGH)
                .build()
                .setFormula("Bacteria", true);

        GTQTMaterials.EschericiaColi = new Material.Builder(getMaterialsId(), gtqtcoreId("eschericia_coli"))
                .dust()
                .color(0x2d4228)
                .iconSet(ROUGH)
                .build()
                .setFormula("Bacteria", true);

        GTQTMaterials.CupriavidusNecator = new Material.Builder(getMaterialsId(), gtqtcoreId("cupriavidus_necator"))
                .dust()
                .color(0x22704f)
                .iconSet(ROUGH)
                .build()
                .setFormula("Bacteria", true);

        GTQTMaterials.SelectivelyMutatedCupriavidiusNecator = new Material.Builder(getMaterialsId(), gtqtcoreId("selectively_mutated_cupriavidius_necator"))
                .dust()
                .iconSet(SHINY)
                .color(0xe04800)
                .build()
                .setFormula("Bacteria", true);


        GTQTMaterials.Gluons = new Material.Builder(getMaterialsId(), gtqtcoreId("gluons"))
                .fluid()
                .color(0xffffff)
                .build();

        GTQTMaterials.LightQuarks = new Material.Builder(getMaterialsId(), gtqtcoreId("light_quarks"))
                .fluid()
                .color(0x59ff7d)
                .build();

        GTQTMaterials.HeavyQuarks = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_quarks"))
                .fluid()
                .color(0x4a080b)
                .build();

        //  15012 Heavy Quark Degenerate Matter
        GTQTMaterials.HeavyQuarkDegenerateMatter = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_quark_degenerate_matter"))
                .ingot()
                .liquid(new FluidBuilder().temperature((int) (V[UV] + V[HV] * V[HV])))
                .plasma(new FluidBuilder().temperature((int) (V[UV] * V[HV])))
                .color(0x5DBD3A)
                .iconSet(BRIGHT)
                .blast(b -> b
                        .temp(14960, BlastProperty.GasTier.HIGHEST)
                        .blastStats(VA[UIV]))
                .flags(DISABLE_DECOMPOSITION, GENERATE_PLATE, GENERATE_FOIL, GENERATE_FINE_WIRE, GENERATE_ROD, GENERATE_FRAME, GENERATE_DOUBLE_PLATE, GENERATE_BOLT_SCREW)
                .cableProperties(V[UXV], 576, 1024, false)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "aaaaaa", false);
    }
}
