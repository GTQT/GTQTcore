package keqing.gtqtcore.api.unification.matreials;
import gregtech.api.GTValues;
import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.BlastProperty;
import gregtech.api.unification.material.properties.ToolProperty;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static gregtech.api.util.GTUtility.gregtechId;
import static gregtechfoodoption.utils.GTFOUtils.averageRGB;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

import keqing.gtqtcore.api.unification.GTQTMaterials;

public class HigherDegreeMaterials {

    private static int startId = 20600;
    private static final int END_ID = startId + 300;

    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static void register() {
        GTQTMaterials.Nitinol = new Material.Builder(getMaterialsId(), gregtechId("nitinol"))
                .ingot(3)
                .fluid()
                .color(0x4876FF).iconSet(METALLIC)
                .flags(EXT2_METAL, GENERATE_SMALL_GEAR, GENERATE_RING, GENERATE_FRAME, GENERATE_ROTOR, GENERATE_ROUND, GENERATE_FOIL, GENERATE_GEAR)
                .components(Nickel, 2, Titanium, 3)
                .rotorStats(15.0f, 7.0f, 3000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 5120, 3)
                        .attackSpeed(0.1F).enchantability(21).build())
                .blast(1300, BlastProperty.GasTier.LOW)
                .build();

        //贫硫化金矿石
        GTQTMaterials.LeanGoldSulphide= new Material.Builder(getMaterialsId(), gregtechId("lean_gold_sulphide"))
                .ore(1,1)
                .dust()
                .color(0x8B8B00)
                .flags(DISABLE_DECOMPOSITION)
                .components(Gold, 1, Copper, 1,Sulfur,2,Oxygen,3)
                .build();

        //含金多金属矿石
        GTQTMaterials.RichGoldSulphide= new Material.Builder(getMaterialsId(), gregtechId("rich_gold_sulphide"))
                .ore(1,1)
                .dust()
                .color(0xCDCD00)
                .flags(DISABLE_DECOMPOSITION)
                .components(Gold, 1, Copper, 1,Iron,1,Sulfur,4,Oxygen,1)
                .build();

        // 褐煤
        GTQTMaterials.Lignite = new Material.Builder(getMaterialsId(), gregtechId("lignite"))
                .gem(1, 1600).ore(2, 1) // default coal burn time in vanilla
                .color(0x8B5A00).iconSet(LIGNITE)
                .flags(FLAMMABLE, NO_SMELTING, NO_SMASHING, MORTAR_GRINDABLE, EXCLUDE_BLOCK_CRAFTING_BY_HAND_RECIPES,
                        DISABLE_DECOMPOSITION)
                .components(Carbon, 1)
                .build();

        // 深红银矿
        GTQTMaterials.Pyrargyrite= new Material.Builder(getMaterialsId(), gregtechId("pyrargyrite"))
                .ore(true)
                .dust().fluid()
                .color(0x8B4726)
                .components(Silver, 3,Antimony,1,Sulfur,3)
                .build();
        // 锌锑方辉银矿
        GTQTMaterials.Zincantimonygalvanite= new Material.Builder(getMaterialsId(), gregtechId("zincantimonygalvanite"))
                .ore(true)
                .dust().fluid()
                .color(0x8B2252)
                .components(Silver, 2,Zinc,1,Sulfur,2,Oxygen,1)
                .build();
        //铬铅矿
        GTQTMaterials.Crocoite= new Material.Builder(getMaterialsId(), gregtechId("crocoite"))
                .ore(true)
                .dust().fluid()
                .color(0x321452)
                .components(Lead,1,Chromite,1,Oxygen,4)
                .build();
        // 冰晶石
        GTQTMaterials.Cryolite= new Material.Builder(getMaterialsId(), gregtechId("cryolite"))
                .ore(true).dust()
                .color(0x98F5FF)
                .components(Sodium,3,Aluminium,1,Fluorine,6)
                .build();

        // 神秘六要素

        // 磷铝锂石
        GTQTMaterials.Amblygonite= new Material.Builder(getMaterialsId(), gregtechId("amblygonite"))
                .ore()
                .dust().fluid()
                .color(0xA4D3EE)
                .components(Lithium,1,Sodium,1,Aluminium,1,Phosphate,1,Hydrogen,1)
                .build();

        // 琥珀
        GTQTMaterials.Amber= new Material.Builder(getMaterialsId(), gregtechId("amber"))
                .ore(true)
                .dust().fluid()
                .color(0x8B4C39)
                .components(Carbon,10,Hydrogen,16,Oxygen,1)
                .build();

        //方钍石
        GTQTMaterials.Thorianite= new Material.Builder(getMaterialsId(), gregtechId("thorianite"))
                .ore(true).dust()
                .color(0x8FBC8F)
                .flags(DISABLE_DECOMPOSITION)
                .components(Thorium,1,Oxygen,2)
                .build();

        //氧化硅岩
        GTQTMaterials.NaquadahOxide = new Material.Builder(getMaterialsId(), gregtechId("naquadah_oxide"))
                .ore(true).dust()
                .flags(DISABLE_DECOMPOSITION)
                .color(0x636363)
                .components(Naquadah,1,Oxygen,2)
                .build();

        //钠硼解石
        GTQTMaterials.Ulexite= new Material.Builder(getMaterialsId(), gregtechId("ulexite"))
                .ore().dust().fluid()
                .color(0xFFFAFA)
                .components(Sodium,1,Calcium,1,Borax,1, Oxygen, 2, Hydrogen, 2,Water,6)
                .build();

        //bittern 盐卤
        GTQTMaterials.Bittern= new Material.Builder(getMaterialsId(), gregtechId("bittern"))
                .fluid()
                .color(0xFFFAFA)
                .build();

        //氯化物型卤水
        GTQTMaterials.Bitterncl= new Material.Builder(getMaterialsId(), gregtechId("bitterncl"))
                .fluid()
                .color(0xEE5C42)
                .build();
        //硫酸盐型卤水
        GTQTMaterials.Bitternso= new Material.Builder(getMaterialsId(), gregtechId("bitternso"))
                .fluid()
                .color(0xCD69C9)
                .build();
        //碳酸盐型卤水
        GTQTMaterials.Bitternco= new Material.Builder(getMaterialsId(), gregtechId("bitternco"))
                .fluid()
                .color(0xB3EE3A)
                .build();

        //硫酸酸化卤水
        GTQTMaterials.Bitterns= new Material.Builder(getMaterialsId(), gregtechId("bitterns"))
                .fluid()
                .color(0xEE3A8C)
                .build();

        //氧化卤水
        GTQTMaterials.Bitterno= new Material.Builder(getMaterialsId(), gregtechId("bitterno"))
                .fluid()
                .color(0xDEB887)
                .build();

        //离散态氧化溴
        GTQTMaterials.Bitternbr= new Material.Builder(getMaterialsId(), gregtechId("bitternbr"))
                .fluid()
                .color(0xD02090)
                .build();

        //氧化富集溴
        GTQTMaterials.Bitternobr= new Material.Builder(getMaterialsId(), gregtechId("bitternobr"))
                .fluid()
                .color(0xCD1076)
                .build();

        // 可燃冰
        GTQTMaterials.Gashydrate = new Material.Builder(getMaterialsId(), gregtechId("gashydrate"))
                .gem(1, 128000).ore(2, 1) // default coal burn time in vanilla
                .color(0xE0FFFF).iconSet(LIGNITE)
                .flags(FLAMMABLE, NO_SMELTING, NO_SMASHING, MORTAR_GRINDABLE, EXCLUDE_BLOCK_CRAFTING_BY_HAND_RECIPES,
                        DISABLE_DECOMPOSITION)
                .components(Methane,576,Water,11520)
                .build();
        
        //超导
        LVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("lv_superconductor"))
                .ingot().liquid()
                .color(0xf8f8ff)
                .iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION)
                .components(SolderingAlloy,10,Gallium,2)
                .cableProperties(GTValues.V[GTValues.LV],8,0,true)
                .build();

        MVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("mv_superconductor"))
                .ingot().liquid()
                .color(MagnesiumDiboride .getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.MV], 16,0,true)
                .components(MagnesiumDiboride ,1)
                .build();

        HVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("hv_superconductor"))
                .ingot().liquid()
                .color(MercuryBariumCalciumCuprate .getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.HV], 16,0,true)
                .components(MercuryBariumCalciumCuprate ,1)
                .build();

        EVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("ev_superconductor"))
                .ingot().liquid()
                .color(UraniumTriplatinum .getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.EV], 24,0,true)
                .components(UraniumTriplatinum ,1)
                .build();

        IVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("iv_superconductor"))
                .ingot().liquid()
                .color(SamariumIronArsenicOxide .getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.IV], 24,0,true)
                .components(SamariumIronArsenicOxide ,1)
                .build();

        LuVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("luv_superconductor"))
                .ingot().liquid()
                .color(IndiumTinBariumTitaniumCuprate .getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(IndiumTinBariumTitaniumCuprate ,1)
                .cableProperties(GTValues.V[GTValues.LuV], 32,0,true)
                .build();

        ZPMSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("zpm_superconductor"))
                .ingot().liquid()
                .color(UraniumRhodiumDinaquadide .getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.ZPM], 32,0,true)
                .components(UraniumRhodiumDinaquadide ,1)
                .build();

        UVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("uv_superconductor"))
                .ingot().liquid()
                .color(EnrichedNaquadahTriniumEuropiumDuranide .getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[UV], 64,0,true)
                .components(EnrichedNaquadahTriniumEuropiumDuranide ,1)
                .build();

        UHVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("uhv_superconductor"))
                .ingot().liquid()
                .color(RutheniumTriniumAmericiumNeutronate .getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.UHV], 96,0,true)
                .components(RutheniumTriniumAmericiumNeutronate ,1)
                .build();
/*
        UEVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("uev_superconductor"))
                .ingot().liquid()
                .color(UEVSuperconductorBase.getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.UEV], 8,0,true)
                .components(UEVSuperconductorBase,1)
                .build();

        UIVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("uiv_superconductor"))
                .ingot().liquid()
                .color(UIVSuperconductorBase.getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.UIV], 16,0,true)
                .components(UIVSuperconductorBase,1)
                .build();

        UXVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("uxv_superconductor"))
                .ingot().liquid()
                .color(UXVSuperconductorBase.getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.UXV], 16,0,true)
                .components(UXVSuperconductorBase,1)
                .build();

        OpVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("opv_superconductor"))
                .ingot().liquid()
                .color(OpVSuperconductorBase.getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[OpV], 16,0,true)
                .components(OpVSuperconductorBase,1)
                .build();

        MAXSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("max_superconductor"))
                .ingot().liquid()
                .color(0XFFFFFF)
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.MAX], 32,0,true)
                .components(OpVSuperconductorBase,1)
                .build();
*/

        getMaterialsId();
        getMaterialsId();
        getMaterialsId();
        getMaterialsId();
        getMaterialsId();
        //高级矿物
        Prismarine = new Material.Builder(getMaterialsId(), gregtechId("prismarine"))
                .gem(1)
                .color(0x73B5AA)
                .iconSet(FINE)
                .flags(CRYSTALLIZABLE, EXCLUDE_BLOCK_CRAFTING_BY_HAND_RECIPES,
                        DISABLE_DECOMPOSITION, NO_WORKING)
                .components(Pyrolusite, 1, BrownLimonite, 1, Water, 1)
                .build()
                .setFormula("(Fe,Mn)HO2(H2O)2", true);

        Purpur = new Material.Builder(getMaterialsId(), gregtechId("purpur"))
                .dust(1)
                .color(0xCC94CC)
                .iconSet(FINE)
                .build();

        //  24093 Lanthanum Extracting Nano Resin
        LanthanumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("lanthanum_extracting_nano_resin"))
                .liquid()
                .color(LanthanumOxide.getMaterialRGB())
                .iconSet(DULL)
                .build();

        //  24094 Praseodymium Extracting Nano Resin
        PraseodymiumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("praseodymium_extracting_nano_resin"))
                .liquid()
                .color(PraseodymiumOxide.getMaterialRGB())
                .iconSet(DULL)
                .build();

        //  24095 Neodymium Extracting Nano Resin
        NeodymiumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("neodymium_extracting_nano_resin"))
                .liquid()
                .color(NeodymiumOxide.getMaterialRGB())
                .iconSet(DULL)
                .build();

        //  24096 Cerium Extracting Nano Resin
        CeriumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("cerium_extracting_nano_resin"))
                .liquid()
                .color(CeriumOxide.getMaterialRGB())
                .iconSet(DULL)
                .build();

        //  24097 Scandium Extracting Nano Resin
        ScandiumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("scandium_extracting_nano_resin"))
                .liquid()
                .color(ScandiumOxide.getMaterialRGB())
                .iconSet(DULL)
                .build();

        //  24098 Europium Extracting Nano Resin
        EuropiumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("europium_extracting_nano_resin"))
                .liquid()
                .color(EuropiumOxide.getMaterialRGB())
                .iconSet(DULL)
                .build();

        //  24099 Gadolinium Extracting Nano Resin
        GadoliniumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("gadolinium_extracting_nano_resin"))
                .liquid()
                .color(GadoliniumOxide.getMaterialRGB())
                .iconSet(DULL)
                .build();

        //  24100 Samarium Extracting Nano Resin
        SamariumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("samarium_extracting_nano_resin"))
                .liquid()
                .color(SamariumOxide.getMaterialRGB())
                .iconSet(DULL)
                .build();

        //  24101 Yttrium Extracting Nano Resin
        YttriumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("yttrium_extracting_nano_resin"))
                .liquid()
                .color(YttriumOxide.getMaterialRGB())
                .iconSet(DULL)
                .build();

        //  24102 Terbium Extracting Nano Resin
        TerbiumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("terbium_extracting_nano_resin"))
                .liquid()
                .color(TerbiumOxide.getMaterialRGB())
                .iconSet(DULL)
                .build();

        //  24103 Dysprosium Extracting Nano Resin
        DysprosiumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("dysprosium_extracting_nano_resin"))
                .liquid()
                .color(DysprosiumOxide.getMaterialRGB())
                .iconSet(DULL)
                .build();

        //  24104 Holmium Extracting Nano Resin
        HolmiumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("holmium_extracting_nano_resin"))
                .liquid()
                .color(HolmiumOxide.getMaterialRGB())
                .iconSet(DULL)
                .build();

        //  24105 Erbium Extracting Nano Resin
        ErbiumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("erbium_extracting_nano_resin"))
                .liquid()
                .color(ErbiumOxide.getMaterialRGB())
                .iconSet(DULL)
                .build();

        //  24106 Thulium Extracting Nano Resin
        ThuliumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("thulium_extracting_nano_resin"))
                .liquid()
                .color(ThuliumOxide.getMaterialRGB())
                .iconSet(DULL)
                .build();

        //  24107 Ytterbium Extracting Nano Resin
        YtterbiumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("ytterbium_extracting_nano_resin"))
                .liquid()
                .color(YtterbiumOxide.getMaterialRGB())
                .iconSet(DULL)
                .build();

        //  24108 Lutetium Extracting Nano Resin
        LutetiumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("lutetium_extracting_nano_resin"))
                .liquid()
                .color(LutetiumOxide.getMaterialRGB())
                .iconSet(DULL)
                .build();

        //  24109 Filled Lanthanum Extracting Nano Resin
        FilledLanthanumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("filled_lanthanum_extracting_nano_resin"))
                .liquid()
                .color(LanthanumOxide.getMaterialRGB())
                .build();

        //  24110 Filled Praseodymium Extracting Nano Resin
        FilledPraseodymiumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("filled_praseodymium_extracting_nano_resin"))
                .liquid()
                .color(PraseodymiumOxide.getMaterialRGB())
                .build();

        //  24111 Filled Neodymium Extracting Nano Resin
        FilledNeodymiumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("filled_neodymium_extracting_nano_resin"))
                .liquid()
                .color(NeodymiumOxide.getMaterialRGB())
                .build();

        //  24112 Filled Cerium Extracting Nano Resin
        FilledCeriumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("filled_cerium_extracting_nano_resin"))
                .liquid()
                .color(CeriumOxide.getMaterialRGB())
                .build();

        //  24113 Filled Scandium Extracting Nano Resin
        FilledScandiumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("filled_scandium_extracting_nano_resin"))
                .liquid()
                .color(ScandiumOxide.getMaterialRGB())
                .build();

        //  24114 Filled Europium Extracting Nano Resin
        FilledEuropiumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("filled_europium_extracting_nano_resin"))
                .liquid()
                .color(EuropiumOxide.getMaterialRGB())
                .build();

        //  24115 Filled Gadolinium Extracting Nano Resin
        FilledGadoliniumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("filled_gadolinium_extracting_nano_resin"))
                .liquid()
                .color(GadoliniumOxide.getMaterialRGB())
                .build();

        //  24116 Filled Samarium Extracting Nano Resin
        FilledSamariumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("filled_samarium_extracting_nano_resin"))
                .liquid()
                .color(SamariumOxide.getMaterialRGB())
                .build();

        //  24117 Filled Yttrium Extracting Nano Resin
        FilledYttriumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("filled_yttrium_extracting_nano_resin"))
                .liquid()
                .color(YttriumOxide.getMaterialRGB())
                .build();

        //  24118 Filled Terbium Extracting Nano Resin
        FilledTerbiumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("filled_terbium_extracting_nano_resin"))
                .liquid()
                .color(TerbiumOxide.getMaterialRGB())
                .build();

        //  24119 Filled Dysprosium Extracting Nano Resin
        FilledDysprosiumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("filled_dysprosium_extracting_nano_resin"))
                .liquid()
                .color(DysprosiumOxide.getMaterialRGB())
                .build();

        //  24120 Filled Holmium Extracting Nano Resin
        FilledHolmiumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("filled_holmium_extracting_nano_resin"))
                .liquid()
                .color(HolmiumOxide.getMaterialRGB())
                .build();

        //  24121 Filled Erbium Extracting Nano Resin
        FilledErbiumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("filled_erbium_extracting_nano_resin"))
                .liquid()
                .color(ErbiumOxide.getMaterialRGB())
                .build();

        //  24122 Filled Thulium Extracting Nano Resin
        FilledThuliumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("filled_thulium_extracting_nano_resin"))
                .liquid()
                .color(ThuliumOxide.getMaterialRGB())
                .build();

        //  24123 Filled Ytterbium Extracting Nano Resin
        FilledYtterbiumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("filled_ytterbium_extracting_nano_resin"))
                .liquid()
                .color(YtterbiumOxide.getMaterialRGB())
                .build();

        //  24124 Filled Lutetium Extracting Nano Resin
        FilledLutetiumExtractingNanoResin = new Material.Builder(getMaterialsId(), gregtechId("filled_lutetium_extracting_nano_resin"))
                .liquid()
                .color(LutetiumOxide.getMaterialRGB())
                .build();

        //  24163 Rare Earth Chlorides Concentrate
        RareEarthChloridesConcentrate = new Material.Builder(getMaterialsId(), gregtechId("rare_earth_chlorides_concentrate"))
                .liquid()
                .color(RareEarthChloridesSolution.getMaterialRGB() - 10)
                .iconSet(DULL)
                .build();

        //  24164 Rare Earth Chlorides Enriched Solution
        RareEarthChloridesEnrichedSolution = new Material.Builder(getMaterialsId(), gregtechId("rare_earth_chlorides_enriched_solution"))
                .liquid()
                .color(RareEarthChloridesSolution.getMaterialRGB() - 20)
                .iconSet(DULL)
                .build();

        //  24165 Rare Earth Chlorides Diluted Solution
        RareEarthChloridesDilutedSolution = new Material.Builder(getMaterialsId(), gregtechId("rare_earth_chlorides_diluted_solution"))
                .liquid()
                .color(RareEarthChloridesSolution.getMaterialRGB() - 40)
                .iconSet(DULL)
                .build();

        //  24166 Chlorinated Rare Earth Waste Fluid
        ChlorinatedRareEarthWasteFluid = new Material.Builder(getMaterialsId(), gregtechId("chlorinated_rare_earth_waste_fluid"))
                .liquid()
                .color(RareEarthChloridesSolution.getMaterialRGB() - 80)
                .iconSet(DULL)
                .build();

        MysteriousCrystal = new Material.Builder(getMaterialsId(), gregtechId("mysterious_crystal"))
                .gem()
                .ore(2, 2)
                .color(0x16856C)
                .iconSet(DIAMOND)
                .flags(GENERATE_PLATE, GENERATE_LENS)
                .build()
                .setFormula("My", false);

        //超临界尾气
        SuperCriticalGas = new Material.Builder(getMaterialsId(), gregtechId("supercritical_gas"))
                .gas(new FluidBuilder().temperature(6000).color(0x8A8A8A))
                .color(0x8A8A8A)
                .iconSet(DULL)
                .build();

        //过热尾气
        OverheatedGas = new Material.Builder(getMaterialsId(), gregtechId("overheated_gas"))
                .gas(new FluidBuilder().temperature(1800).color(0x8A8A8A))
                .color(0x8A8A8A)
                .iconSet(DULL)
                .build();

        //高温尾气
        HighTemperatureGas = new Material.Builder(getMaterialsId(), gregtechId("hightemperature_gas"))
                .gas(new FluidBuilder().temperature(900).color(0x8A8A8A))
                .color(0x8A8A8A)
                .iconSet(DULL)
                .build();

        //常温尾气
        NormalGas = new Material.Builder(getMaterialsId(), gregtechId("normal_gas"))
                .gas(new FluidBuilder().temperature(300).color(0x8A8A8A))
                .color(0x8A8A8A)
                .iconSet(DULL)
                .build();
    }
}
