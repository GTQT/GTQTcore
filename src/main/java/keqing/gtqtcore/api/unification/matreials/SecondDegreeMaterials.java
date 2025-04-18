package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.attribute.FluidAttributes;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.material.properties.BlastProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import keqing.gtqtcore.api.unification.Elements;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import net.minecraft.util.text.TextFormatting;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static gregtechfoodoption.utils.GTFOUtils.averageRGB;
import static keqing.gtqtcore.api.GTQTValue.gtqtcoreId;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;


public class SecondDegreeMaterials {

    //第二类材料
    //通常为混合物品或者由不同化合物组成的符合化合物

    private static int startId = 2000;
    private static final int END_ID = startId + 2000;

    public SecondDegreeMaterials() {
    }

    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static void register() {
        //离散态素魔力
        GTQTMaterials.MagicGas = new Material.Builder(getMaterialsId(), gtqtcoreId("magic_gas"))
                .gas()
                .color(0x00FFFF)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //交错次元空气
        GTQTMaterials.BetAir = new Material.Builder(getMaterialsId(), gtqtcoreId("bet_air"))
                .gas()
                .liquid(new FluidBuilder().temperature(58))
                .color(0x2E8B57)
                .flags(DISABLE_DECOMPOSITION)
                .components(Methane, 78, HydrogenSulfide, 21, Neon, 7, Radon, 2)
                .build();

        //交错次元空气
        GTQTMaterials.BeneathAir = new Material.Builder(getMaterialsId(), gtqtcoreId("beneath_air"))
                .gas()
                .liquid(new FluidBuilder().temperature(58))
                .color(0x4A4A4A)
                .flags(DISABLE_DECOMPOSITION)
                .components(Oxygen, 80, CarbonDioxide, 20, Argon, 10, Radon, 10, Hydrogen, 10, Nitrogen, 10, MagicGas, 10)
                .build();

        //交错次元空气
        GTQTMaterials.MarsAir = new Material.Builder(getMaterialsId(), gtqtcoreId("mars_air"))
                .liquid(new FluidBuilder().temperature(58))
                .gas()
                .color(0x8B3E2F)
                .flags(DISABLE_DECOMPOSITION)
                .components(CarbonDioxide, 80, Argon, 20, Oxygen, 10, Radon, 10, Hydrogen, 10, Nitrogen, 10, MagicGas, 10)
                .build();

        //离散态微薄魔力
        GTQTMaterials.MagicGas = new Material.Builder(getMaterialsId(), gtqtcoreId("magic_gas"))
                .gas()
                .color(0xB2DFEE)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("-KQ-");

        //离散态素魔力
        GTQTMaterials.MagicFas = new Material.Builder(getMaterialsId(), gtqtcoreId("magic_fas"))
                .gas()
                .color(0xAEEEEE)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("-KQ-");

        //离散态纯净魔力
        GTQTMaterials.MagicDas = new Material.Builder(getMaterialsId(), gtqtcoreId("magic_das"))
                .gas()
                .color(0x98F5FF)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("-KQ-");

        //凝聚态素魔力
        GTQTMaterials.MagicAas = new Material.Builder(getMaterialsId(), gtqtcoreId("magic_aas"))
                .gas()
                .color(0x87CEEB)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("-KQ-");

        //魔力废液
        GTQTMaterials.MagicRub = new Material.Builder(getMaterialsId(), gtqtcoreId("magic_rub"))
                .gas()
                .color(0x87CEFF)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("-KQ-");

        //魔力
        GTQTMaterials.Magic = new Material.Builder(getMaterialsId(), gtqtcoreId("magic"))
                .fluid()
                .color(0x7B68EE)
                .iconSet(DULL)
                .element(Elements.Magic)
                .build();

        //富集魔力
        GTQTMaterials.Richmagic = new Material.Builder(getMaterialsId(), gtqtcoreId("richmagic"))
                .ingot().fluid()
                .color(0x7D26CD)
                .iconSet(DULL)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD)
                .build()
                .setFormula("*KQ*");

        //生物原油
        GTQTMaterials.BioOil = new Material.Builder(getMaterialsId(), gtqtcoreId("biooil"))
                .fluid()
                .color(0xFA8072)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        GTQTMaterials.PineOil = new Material.Builder(getMaterialsId(), gtqtcoreId("pine_oil"))
                .fluid()
                .color(0xd6ac37)
                .build();

        GTQTMaterials.Periodicium = new Material.Builder(getMaterialsId(), gtqtcoreId("periodicium"))
                .color(0x3D4BF6)
                .ingot(6)
                .iconSet(MaterialIconSet.SHINY)
                .blast(13500)
                .components(Hydrogen, 1, Helium, 1, Lithium, 1, Beryllium, 1,
                        Boron, 1, Carbon, 1, Nitrogen, 1, Oxygen, 1,
                        Fluorine, 1, Neon, 1, Sodium, 1, Magnesium, 1,
                        Aluminium, 1, Silicon, 1, Phosphorus, 1, Sulfur, 1,
                        Chlorine, 1, Argon, 1, Potassium, 1, Calcium, 1,
                        Scandium, 1, Titanium, 1, Vanadium, 1, Chrome, 1,
                        Manganese, 1, Iron, 1, Cobalt, 1, Nickel, 1,
                        Copper, 1, Zinc, 1, Gallium, 1, Germanium, 1,
                        Arsenic, 1, Selenium, 1, Bromine, 1, Krypton, 1,
                        Rubidium, 1, Strontium, 1, Yttrium, 1, Zirconium, 1,
                        Niobium, 1, Molybdenum, 1, Technetium, 1, Ruthenium, 1,
                        Rhodium, 1, Palladium, 1, Silver, 1, Cadmium, 1,
                        Indium, 1, Tin, 1, Antimony, 1, Tellurium, 1,
                        Iodine, 1, Xenon, 1, Caesium, 1, Barium, 1,
                        Lanthanum, 1, Cerium, 1, Praseodymium, 1, Neodymium, 1,
                        Promethium, 1, Samarium, 1, Europium, 1, Gadolinium, 1,
                        Terbium, 1, Dysprosium, 1, Holmium, 1, Erbium, 1,
                        Thulium, 1, Ytterbium, 1, Lutetium, 1, Hafnium, 1,
                        Tantalum, 1, Tungsten, 1, Rhenium, 1, Osmium, 1,
                        Iridium, 1, Platinum, 1, Gold, 1, Mercury, 1,
                        Thallium, 1, Lead, 1, Bismuth, 1, Polonium, 1,
                        Astatine, 1, Radon, 1, Francium, 1, Radium, 1,
                        Actinium, 1, Thorium, 1, Protactinium, 1, Uranium235, 1,
                        Neptunium, 1, Plutonium239, 1, Americium, 1, Curium, 1,
                        Berkelium, 1, Californium, 1, Einsteinium, 1, Fermium, 1,
                        Mendelevium, 1, Nobelium, 1, Lawrencium, 1, Rutherfordium, 1,
                        Dubnium, 1, Seaborgium, 1, Bohrium, 1, Hassium, 1,
                        Meitnerium, 1, Darmstadtium, 1, Roentgenium, 1, Copernicium, 1,
                        Nihonium, 1, Flerovium, 1, Moscovium, 1, Livermorium, 1, Tennessine, 1,
                        Oganesson, 1)
                .build();

        GTQTMaterials.MetallicHydrogen = new Material.Builder(getMaterialsId(), gtqtcoreId("metallic_hydrogen"))
                .ingot().fluid()
                .iconSet(MaterialIconSet.SHINY)
                .flags(GENERATE_PLATE, GENERATE_RING, GENERATE_ROUND, GENERATE_ROTOR, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_LONG_ROD, GENERATE_FRAME)
                .color(0x4682B4)
                .fluidPipeProperties(10240, 32000, true, true, true, true)
                .components(Hydrogen, 1)
                .build();

        GTQTMaterials.Ethylenimine = new Material.Builder(getMaterialsId(), gtqtcoreId("ethylenimine"))
                .fluid()
                .color(0x483D8B)
                .components(Carbon, 2, Hydrogen, 5, Nitrogen, 1)
                .build();

        GTQTMaterials.Polyethyleneimine = new Material.Builder(getMaterialsId(), gtqtcoreId("polyethylenimine"))
                .fluid()
                .color(0x483DB4)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 2, Hydrogen, 5, Nitrogen, 1)
                .build();

        GTQTMaterials.RedMud = new Material.Builder(getMaterialsId(), gtqtcoreId("red_mud"))
                .fluid()
                .color(0x483DB4)
                .flags(DISABLE_DECOMPOSITION)
                .components(Rutile, 1, Gallium, 1, HydrochloricAcid, 2)
                .build();

        GTQTMaterials.CarbenDisulfide = new Material.Builder(getMaterialsId(), gtqtcoreId("carbon_disulfide"))
                .fluid()
                .colorAverage()
                .components(Carbon, 1, Sulfur, 2)
                .build();

        GTQTMaterials.UreaMix = new Material.Builder(getMaterialsId(), gtqtcoreId("urea_mix"))
                .fluid()
                .color(0x443610)
                .build();

        GTQTMaterials.FermentationBase = new Material.Builder(getMaterialsId(), gtqtcoreId("fermentation_base"))
                .fluid()
                .color(0x5E5839)
                .build();

        GTQTMaterials.Resin = new Material.Builder(getMaterialsId(), gtqtcoreId("resin"))
                .fluid()
                .color(0x353533)
                .build();

        GTQTMaterials.CalciumCarbonate = new Material.Builder(getMaterialsId(), gtqtcoreId("calcium_carbonate"))
                .dust()
                .components(Materials.Calcium, 1, Materials.Carbon, 1, Materials.Oxygen, 3)
                .color(0xE8E8CB)
                .build();

        GTQTMaterials.PropionicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("propionic_acid"))
                .fluid()
                .color(0xB3BC88)
                .build();

        GTQTMaterials.SodiumAluminate = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_aluminate"))
                .dust()
                .colorAverage()
                .components(Sodium, 1, Aluminium, 1, Oxygen, 2)
                .build();

        GTQTMaterials.CarbenDisulfide = new Material.Builder(getMaterialsId(), gtqtcoreId("carbon_disulfide"))
                .fluid()
                .colorAverage()
                .components(Carbon, 1, Sulfur, 2)
                .build();

        GTQTMaterials.CarbenDisulfide = new Material.Builder(getMaterialsId(), gtqtcoreId("carbon_disulfide"))
                .fluid()
                .colorAverage()
                .components(Carbon, 1, Sulfur, 2)
                .build();

        //硫酸锌粉
        GTQTMaterials.ZincSulfate = new Material.Builder(getMaterialsId(), gtqtcoreId("zinc_sulfate"))
                .dust()
                .components(Zinc, 1, Sulfur, 1, Oxygen, 4)
                .color(0x4682B4)
                .build();

        getMaterialsId();
        getMaterialsId();
        getMaterialsId();
        getMaterialsId();
        getMaterialsId();
        getMaterialsId();
        getMaterialsId();
        getMaterialsId();

        //硫化金处理
        //焙烧
        GTQTMaterials.LeanGoldBs = new Material.Builder(getMaterialsId(), gtqtcoreId("len_gold_bs"))
                .dust()
                .color(0xFFFF00)
                .build();

        //氰化钠浸出
        GTQTMaterials.LeanGoldJc = new Material.Builder(getMaterialsId(), gtqtcoreId("len_gold_js"))
                .fluid()
                .color(0xFFFF00)
                .build();

        GTQTMaterials.LeanCopperJc = new Material.Builder(getMaterialsId(), gtqtcoreId("len_copper_js"))
                .dust()
                .color(0xFFFF00)
                .build();

        GTQTMaterials.LeanGoldCl = new Material.Builder(getMaterialsId(), gtqtcoreId("len_gold_Cl"))
                .fluid()
                .color(0xFFFF00)
                .build();

        GTQTMaterials.LeanGoldDr = new Material.Builder(getMaterialsId(), gtqtcoreId("len_gold_dr"))
                .dust()
                .color(0xFFFF00)
                .build();

        GTQTMaterials.RichGoldBs = new Material.Builder(getMaterialsId(), gtqtcoreId("rich_gold_bs"))
                .dust()
                .color(0xFFFF00)
                .build();

        GTQTMaterials.RichCopperJc = new Material.Builder(getMaterialsId(), gtqtcoreId("rich_copper_js"))
                .dust()
                .color(0xFFFF00)
                .build();

        //  13078 Dichlorocyclooctadieneplatinium
        GTQTMaterials.Dichlorocyclooctadieneplatinium = new Material.Builder(getMaterialsId(), gtqtcoreId("dichlorocyclooctadieneplatinium"))
                .dust()
                .color(0xD4E982)
                .iconSet(BRIGHT)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 8, Hydrogen, 12, Chlorine, 2, Platinum, 1)
                .build();

        //  13079 Diiodobiphenyl
        GTQTMaterials.Diiodobiphenyl = new Material.Builder(getMaterialsId(), gtqtcoreId("diiodobiphenyl"))
                .dust()
                .color(0x000C52)
                .iconSet(METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 12, Hydrogen, 8, Iodine, 2)
                .build();

        //  11086 Silver Tetrafluoroborate
        GTQTMaterials.SilverTetrafluoroborate = new Material.Builder(getMaterialsId(), gtqtcoreId("silver_tetrafluoroborate"))
                .liquid()
                .color(0x818024)
                .flags(DISABLE_DECOMPOSITION)
                .components(Silver, 1, Boron, 1, Fluorine, 4)
                .build()
                .setFormula("AgBF4", true);


        //  11088 Trimethyltin Chloride
        GTQTMaterials.TrimethyltinChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("trimethyltin_chloride"))
                .liquid()
                .color(0x7F776F)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 3, Hydrogen, 6, Tin, 1, Chlorine, 1)
                .build()
                .setFormula("(CH3)3SnCl", true);

        //  11084 Silver Chloride
        GTQTMaterials.SilverChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("silver_chloride"))
                .dust()
                .color(0x8D8D8D)
                .iconSet(METALLIC)
                .components(Silver, 1, Chlorine, 1)
                .build();

        //  13082 Octene
        GTQTMaterials.Octene = new Material.Builder(getMaterialsId(), gtqtcoreId("octene"))
                .liquid()
                .color(0x818022)
                .components(Carbon, 8, Hydrogen, 16)
                .build();

        //  13077 Cyclooctadiene
        GTQTMaterials.Cyclooctadiene = new Material.Builder(getMaterialsId(), gtqtcoreId("cyclooctadiene"))
                .liquid()
                .color(0x40AC40)
                .components(Carbon, 8, Hydrogen, 12)
                .build();

        //  13084 Hexafluoropropylene
        GTQTMaterials.Hexafluoropropylene = new Material.Builder(getMaterialsId(), gtqtcoreId("hexafluoropropylene"))
                .liquid()
                .color(0x141D6F)
                .components(Carbon, 3, Fluorine, 6)
                .build();

        //  11007 Hexachloroplatinic Acid
        GTQTMaterials.HexachloroplatinicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("hexachloroplatinic_acid"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(0xFEF4D1)
                .components(Hydrogen, 2, Platinum, 1, Chlorine, 6)
                .build();

        //  11089 Potassium Tetrachloroplatinate
        GTQTMaterials.PotassiumTetrachloroplatinate = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_tetrachloroplatinate"))
                .dust()
                .color(0xF1B04F)
                .iconSet(SHINY)
                .components(Potassium, 2, Platinum, 1, Chlorine, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("K2PtCl4", true);

        //  13080 Hydroxylamine Disulfate
        GTQTMaterials.HydroxylamineDisulfate = new Material.Builder(getMaterialsId(), gtqtcoreId("hydroxylamine_disulfate"))
                .liquid()
                .color(0x91A6D2)
                .components(Nitrogen, 4, Hydrogen, 16, Oxygen, 10, Sulfur, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(NH3OH)2(NH4)2(SO4)2", true);

        //  13120 Hydroxylamine Hydrochloride
        GTQTMaterials.HydroxylamineHydrochloride = new Material.Builder(getMaterialsId(), gtqtcoreId("hydroxylamine_hydrochloride"))
                .liquid()
                .color(0x893E28)
                .components(Hydrogen, 4, Oxygen, 1, Nitrogen, 1, Chlorine, 1)
                .build()
                .setFormula("HONH2HCl", true);

        //  13081 Hydroxylamine
        GTQTMaterials.Hydroxylamine = new Material.Builder(getMaterialsId(), gtqtcoreId("hydroxylamine"))
                .liquid()
                .color(0x91C791)
                .components(Hydrogen, 3, Nitrogen, 1, Oxygen, 1)
                .build()
                .setFormula("H3NO", true);

        //  11091 Ammonium Persulfate
        GTQTMaterials.AmmoniumPersulfate = new Material.Builder(getMaterialsId(), gtqtcoreId("ammonium_persulfate"))
                .liquid()
                .color(0x4242B7)
                .components(Nitrogen, 2, Hydrogen, 8, Sulfur, 2, Oxygen, 8)
                .build()
                .setFormula("(NH4)2S2O8", true);

        //  11187 Lanthanum Fullerene Mixture
        GTQTMaterials.LanthanumFullereneMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("lanthanum_fullerene_mixture"))
                .dust()
                .color(0xD26D8E)
                .iconSet(BRIGHT)
                .build()
                .setFormula("(C60H30)La2", true);

        //  11085 Silver Oxide
        GTQTMaterials.SilverOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("silver_oxide"))
                .dust()
                .color(0xA4A4A4)
                .components(Silver, 2, Oxygen, 1)
                .build();

        //  11087 Tin Chloride
        GTQTMaterials.TinChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("tin_chloride"))
                .dust()
                .liquid()
                .color(0xDBDBDB)
                .iconSet(METALLIC)
                .components(Tin, 1, Chlorine, 2)
                .build();

        //  12025 Magneto Resonatic
        GTQTMaterials.MagnetoResonatic = new Material.Builder(getMaterialsId(), gtqtcoreId("magneto_resonatic"))
                .gem()
                .color(0xFF97FF)
                .iconSet(MAGNETIC)
                .flags(NO_SMELTING, GENERATE_LENS)
                .build();
        MagnetoResonatic.setFormula("(Si₅O₁₀Fe)₃(Bi₂Te₃)₆(ZrO₂)Fe");

        //  12023 Prasiolite
        Prasiolite = new Material.Builder(getMaterialsId(), gtqtcoreId("prasiolite"))
                .gem()
                .color(0x9EB749)
                .iconSet(QUARTZ)
                .flags(CRYSTALLIZABLE, GENERATE_LENS)
                .components(SiliconDioxide, 5, Iron, 1)
                .build();

        //  12024 Bismuth Tellurite
        GTQTMaterials.BismuthTellurite = new Material.Builder(getMaterialsId(), gtqtcoreId("bismuth_tellurite"))
                .dust()
                .color(0x0E8933)
                .iconSet(DULL)
                .components(Bismuth, 2, Tellurium, 3)
                .build();


        //  11188 Lanthanum Embedded Fullerene
        GTQTMaterials.LanthanumEmbeddedFullerene = new Material.Builder(getMaterialsId(), gtqtcoreId("lanthanum_embedded_fullerene"))
                .dust()
                .color(0x84FFAC)
                .iconSet(BRIGHT)
                .build()
                .setFormula("(C60H30)La2", true);

        //  12035 Lanthanum Fullerene Nanotube
        GTQTMaterials.LanthanumFullereneNanotube = new Material.Builder(getMaterialsId(), gtqtcoreId("lanthanum_fullerene_nanotube"))
                .ingot()
                .color(0xD24473)
                .iconSet(BRIGHT)
                .flags(DISABLE_DECOMPOSITION, GENERATE_PLATE, GENERATE_RING, GENERATE_ROUND, GENERATE_ROTOR, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_LONG_ROD, GENERATE_FRAME)
                .build()
                .setFormula("(C60H30)C48La2", true);

        //  11219 Neutronium Nanotube
        GTQTMaterials.NeutroniumNanotube = new Material.Builder(getMaterialsId(), gtqtcoreId("neutronium_nanotube"))
                .ingot()
                .color(0xDDBDCC)
                .iconSet(BRIGHT)
                .flags(DISABLE_DECOMPOSITION, GENERATE_PLATE)
                .components(Carbon, 48, Neutronium, 1)
                .build()
                .setFormula("Nt:CNT");

        GTQTMaterials.Gabbro = new Material.Builder(getMaterialsId(), gtqtcoreId("gabbro"))
                .dust()
                .color(0x5C5C5C).iconSet(ROUGH)
                .components(Labradorite, 5, Bytownite, 3, Olivine, 2, Augite, 1, Biotite, 1)
                .build();

        GTQTMaterials.Gneiss = new Material.Builder(getMaterialsId(), gtqtcoreId("gneiss"))
                .dust()
                .color(0x643631).iconSet(ROUGH)
                .components(Albite, 4, SiliconDioxide, 3, Biotite, 1, Muscovite, 1)
                .build();

        GTQTMaterials.Limestone = new Material.Builder(getMaterialsId(), gtqtcoreId("limestone"))
                .dust()
                .color(0xa9a9a9).iconSet(ROUGH)
                .components(Calcite, 4, Dolomite, 1)
                .build();

        GTQTMaterials.Phyllite = new Material.Builder(getMaterialsId(), gtqtcoreId("phyllite"))
                .dust()
                .color(0x716f71).iconSet(ROUGH)
                .components(Albite, 3, SiliconDioxide, 3, Muscovite, 4)
                .build();

        GTQTMaterials.Shale = new Material.Builder(getMaterialsId(), gtqtcoreId("shale"))
                .dust()
                .color(0x3f2e2f).iconSet(ROUGH)
                .components(Calcite, 6, Clay, 2, SiliconDioxide, 1, Fluorite, 1)
                .build();

        GTQTMaterials.Slate = new Material.Builder(getMaterialsId(), gtqtcoreId("slate"))
                .dust()
                .color(0x756869).iconSet(ROUGH)
                .components(SiliconDioxide, 5, Muscovite, 2, Clinochlore, 2, Albite, 1)
                .build();

        GTQTMaterials.Kimberlite = new Material.Builder(getMaterialsId(), gtqtcoreId("kimberlite"))
                .dust()
                .color(0x201313).iconSet(ROUGH)
                .components(Forsterite, 3, Augite, 3, Andradite, 2, Lizardite, 1)
                .build();


        //  13085 Fluorinated Ethylene Propylene
        GTQTMaterials.FluorinatedEthylenePropylene = new Material.Builder(getMaterialsId(), gtqtcoreId("fluorinated_ethylene_propylene"))
                .liquid() // TODO polymer?
                .color(0xC8C8C8)
                .iconSet(DULL)
                .components(Carbon, 5, Fluorine, 10)
                .build();

        //  11248 Lead Zirconate Titanate
        GTQTMaterials.LeadZirconateTitanate = new Material.Builder(getMaterialsId(), gtqtcoreId("lead_zirconate_titanate"))
                .gem(3)
                .color(0x359ADE)
                .iconSet(OPAL)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .components(Lead, 12, Titanium, 1, Zirconium, 1, Oxygen, 16)
                .build()
                .setFormula("PbZrTiO3", true);

        //  24086 Blood Cells
        GTQTMaterials.BloodCells = new Material.Builder(getMaterialsId(), gtqtcoreId("blood_cells"))
                .liquid()
                .color(0xC43A31)
                .iconSet(DULL)
                .build();

        //  24087 Blood Plasma
        GTQTMaterials.BloodPlasma = new Material.Builder(getMaterialsId(), gtqtcoreId("blood_plasma"))
                .liquid()
                .color(0x882822)
                .build();

        //  24088 bFGF
        GTQTMaterials.BFGF = new Material.Builder(getMaterialsId(), gtqtcoreId("bfgf"))
                .liquid()
                .color(0xA15C72)
                .build()
                .setFormula("bFGF", false);

        //  24089 EGF
        GTQTMaterials.EGF = new Material.Builder(getMaterialsId(), gtqtcoreId("egf"))
                .liquid()
                .color(0x993300)
                .build()
                .setFormula("EGF", false);

        //  24090 CAT
        GTQTMaterials.CAT = new Material.Builder(getMaterialsId(), gtqtcoreId("cat"))
                .liquid()
                .color(0x72B5EA)
                .build()
                .setFormula("CAT", false);

        //  13169 Biotin
        GTQTMaterials.Biotin = new Material.Builder(getMaterialsId(), gtqtcoreId("biotin"))
                .liquid()
                .color(0x08C74A)
                .components(Carbon, 10, Hydrogen, 16, Nitrogen, 2, Oxygen, 3, Sulfur, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13170 Linoleic Acid
        GTQTMaterials.LinoleicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("linoleic_acid"))
                .liquid()
                .color(0x919C2B)
                .components(Carbon, 18, Hydrogen, 32, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13161 Cyclopentadiene
        GTQTMaterials.Cyclopentadiene = new Material.Builder(getMaterialsId(), gtqtcoreId("cyclopentadiene"))
                .liquid()
                .color(0x8BEB2A)
                .components(Carbon, 5, Hydrogen, 6)
                .build();

        //  13171 Vitamin A
        GTQTMaterials.VitaminA = new Material.Builder(getMaterialsId(), gtqtcoreId("vitamin_a"))
                .liquid()
                .color(0xAB5EC3)
                .components(Carbon, 20, Hydrogen, 30, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13172 β-Ionone
        GTQTMaterials.BetaIonone = new Material.Builder(getMaterialsId(), gtqtcoreId("beta_ionone"))
                .liquid()
                .color(0xC3A0B2)
                .components(Carbon, 13, Hydrogen, 20, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13173 Citral
        GTQTMaterials.Citral = new Material.Builder(getMaterialsId(), gtqtcoreId("citral"))
                .liquid()
                .color(0xE4E77E)
                .components(Carbon, 10, Hydrogen, 16, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13175 Propargyl Chloride
        GTQTMaterials.PropargylChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("propargyl_chloride"))
                .liquid()
                .color(0x156101)
                .components(Carbon, 3, Hydrogen, 3, Chlorine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("HC2CH2Cl", true);

        //  13177 Ethanolamine
        GTQTMaterials.Ethanolamine = new Material.Builder(getMaterialsId(), gtqtcoreId("ethanolamine"))
                .liquid()
                .color(0xD3DEA2)
                .components(Carbon, 2, Hydrogen, 7, Nitrogen, 1, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("HOCH2CH2NH2", true);

        //  13176 Propargyl Alcohol
        GTQTMaterials.PropargylAlcohol = new Material.Builder(getMaterialsId(), gtqtcoreId("propargyl_alcohol"))
                .liquid()
                .color(0xB7AB44)
                .components(Carbon, 3, Hydrogen, 4, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("CHCCH2OH", true);

        //  13168 B27
        GTQTMaterials.B27 = new Material.Builder(getMaterialsId(), gtqtcoreId("b_27"))
                .liquid()
                .color(0xC2B7E3)
                .components(Carbon, 142, Hydrogen, 230, Nitrogen, 36, Oxygen, 44, Sulfur, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11247 Ce:LAG
        GTQTMaterials.CeLAG = new Material.Builder(getMaterialsId(), gtqtcoreId("ce_lag"))
                .gem()
                .color(0x00A816)
                .iconSet(GEM_VERTICAL)
                .flags(CRYSTALLIZABLE, DECOMPOSITION_BY_CENTRIFUGING, GENERATE_LENS)
                .components(CeriumOxide, 1, LutetiumOxide, 1, Alumina, 5)
                .build()
                .setFormula("Ce:LAG", true);

        //  13162 Lithium Cyclopentadienide
        GTQTMaterials.LithiumCyclopentadienide = new Material.Builder(getMaterialsId(), gtqtcoreId("lithium_cyclopentadienide"))
                .liquid()
                .color(0x963D5F)
                .components(Carbon, 5, Hydrogen, 5, Lithium, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13163 Californium Cyclopentadienide
        GTQTMaterials.CaliforniumCyclopentadienide = new Material.Builder(getMaterialsId(), gtqtcoreId("californium_cyclopentadienide"))
                .liquid()
                .color(0x821554)
                .components(Carbon, 15, Hydrogen, 15, Californium, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11236 Californium Trichloride
        GTQTMaterials.CaliforniumTrichloride = new Material.Builder(getMaterialsId(), gtqtcoreId("californium_trichloride"))
                .dust()
                .color(0x8B67D1)
                .iconSet(METALLIC)
                .components(Californium, 1, Chlorine, 3)
                .build();

        //  24064 Free Electron Gas
        GTQTMaterials.FreeElectronGas = new Material.Builder(getMaterialsId(), gtqtcoreId("free_electron_gas"))
                .gas(new FluidBuilder()
                        .translation("gregtech.fluid.generic"))
                .color(0x507BB3)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "a" + TextFormatting.RESET + "§ee" + TextFormatting.OBFUSCATED + "a", false);

        //  13200 Polyethylene Terephthalate (PET)
        GTQTMaterials.PolyethyleneTerephthalate = new Material.Builder(getMaterialsId(), gtqtcoreId("polyethylene_terephthalate"))
                .polymer()
                .liquid()
                .color(0x1E5C58)
                .components(Carbon, 10, Hydrogen, 6, Oxygen, 4)
                .flags(GENERATE_PLATE, GENERATE_FOIL)
                .build();

        //  13197 Para Toluic Acid
        GTQTMaterials.ParaToluicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("para_toluic_acid"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(0x4FA597)
                .components(Carbon, 8, Hydrogen, 8, Oxygen, 2)
                .build();

        //  13198 Methylparatoluate
        GTQTMaterials.Methylparatoluate = new Material.Builder(getMaterialsId(), gtqtcoreId("methylparatoluate"))
                .liquid()
                .color(0x76BCB0)
                .components(Carbon, 9, Hydrogen, 10, Oxygen, 2)
                .build();

        //  13199 Dimethyl Terephthalate
        GTQTMaterials.DimethylTerephthalate = new Material.Builder(getMaterialsId(), gtqtcoreId("dimethyl_terephthalate"))
                .liquid()
                .color(0x05D8AF)
                .components(Carbon, 10, Hydrogen, 10, Oxygen, 4)
                .build();

        //  24075 Exotic Mutagen
        ExoticMutagen = new Material.Builder(getMaterialsId(), gtqtcoreId("exotic_mutagen"))
                .liquid(new FluidBuilder().temperature(18406).attributes(FluidAttributes.ACID))
                .color(0x9C31F9)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "aaa", false);

        //  24076 Crude Exotic Gas
        CrudeExoticGas = new Material.Builder(getMaterialsId(), gtqtcoreId("crude_exotic_gas"))
                .gas(new FluidBuilder().temperature(8090))
                .color(0xBEF32C)
                .iconSet(DULL)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "aaa", false);

        //  24077 Cracked Crude Exotic Gas
        CrackedCrudeExoticGas = new Material.Builder(getMaterialsId(), gtqtcoreId("cracked_crude_exotic_gas"))
                .gas(new FluidBuilder().temperature(12390))
                .color(0xEA1798)
                .iconSet(DULL)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "aaa", false);

        //  24078 Naquadic Exotic Gas
        NaquadicExoticGas = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadic_exotic_gas"))
                .gas(new FluidBuilder().temperature(40223))
                .color(0xB01172)
                .iconSet(DULL)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "aaa", false);

        //  24079 Superheavy Exotic Gas
        SuperheavyExoticGas = new Material.Builder(getMaterialsId(), gtqtcoreId("superheavy_exotic_gas"))
                .gas()
                .color(0x33FF99)
                .iconSet(DULL)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "aaa", false);

        //  24080 Heavy Exotic Gas
        HeavyExoticGas = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_exotic_gas"))
                .gas()
                .color(0x57FFBC)
                .iconSet(DULL)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "aaa", false);

        //  24081 Medium Exotic Gas
        MediumExoticGas = new Material.Builder(getMaterialsId(), gtqtcoreId("medium_exotic_gas"))
                .gas()
                .color(0x1FFFA6)
                .iconSet(DULL)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "aaa", false);

        //  24082 Light Exotic Gas
        LightExoticGas = new Material.Builder(getMaterialsId(), gtqtcoreId("light_exotic_gas"))
                .gas()
                .color(0x62FFC1)
                .iconSet(DULL)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "aaa", false);

        //补 硅岩燃料线
        // 放射性污泥
        GTQTMaterials.RadioactiveSludge = new Material.Builder(getMaterialsId(), gtqtcoreId("radioactive_sludge"))
                .dust()
                .color(0x006400) // 深绿色
                .build();

        // 酸性硅岩乳液
        GTQTMaterials.AcidicNaquadahEmulsion = new Material.Builder(getMaterialsId(), gtqtcoreId("acidic_naquadah_emulsion"))
                .fluid()
                .color(0x90EE90) // 浅绿色
                .build();

        // 硅岩乳液
        GTQTMaterials.NaquadahEmulsion = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadah_emulsion"))
                .fluid()
                .color(0x808080) // 灰色
                .build();

        // 粗硅岩燃料溶液
        GTQTMaterials.CrudeNaquadahFuel = new Material.Builder(getMaterialsId(), gtqtcoreId("crude_naquadah_fuel"))
                .fluid()
                .color(0xA52A2A) // 棕色
                .build();

        // 硅岩沥青
        GTQTMaterials.NaquadahAsphalt = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadah_asphalt"))
                .fluid()
                .color(0x000000) // 黑色
                .build();

        // 硅岩气
        GTQTMaterials.NaquadahGas = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadah_gas"))
                .fluid()
                .color(0x0000FF) // 蓝色
                .build();

        // 极不稳定硅岩粉
        GTQTMaterials.UnstableNaquadahPowder = new Material.Builder(getMaterialsId(), gtqtcoreId("unstable_naquadah_powder"))
                .dust()
                .color(0xFFA500) // 橙色
                .build();

        // 硅岩基流体燃料-MKI
        GTQTMaterials.NaquadahFuelMKI = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadah_fuel_mki"))
                .fluid()
                .color(0xFF0000) // 红色
                .build();

        // 硅岩基流体燃料-MKII
        GTQTMaterials.NaquadahFuelMKII = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadah_fuel_mkii"))
                .fluid()
                .color(0xFF4500) // 橙红色
                .build();

        // 硅岩基流体燃料-MKIII
        GTQTMaterials.NaquadahFuelMKIII = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadah_fuel_mkiii"))
                .fluid()
                .color(0xFFFF00) // 黄色
                .build();

        // 硅岩基流体燃料-MKIV
        GTQTMaterials.NaquadahFuelMKIV = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadah_fuel_mkiv"))
                .fluid()
                .color(0xFFFFE0) // 亮黄色
                .build();

        // 硅岩基流体燃料-MKV
        GTQTMaterials.NaquadahFuelMKV = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadah_fuel_mkv"))
                .fluid()
                .color(0xFFFFFF) // 白色
                .build();

        // 副产物流体定义
        NaquadahResidueMKI = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadah_residue_mki"))
                .fluid()
                .color(0xFFA07A) // 淡棕色
                .build();

        NaquadahResidueMKII = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadah_residue_mkii"))
                .fluid()
                .color(0xFFD700) // 金色
                .build();

        NaquadahResidueMKIII = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadah_residue_mkiii"))
                .fluid()
                .color(0x8B0000) // 暗红色
                .build();

        NaquadahResidueMKIV = new Material.Builder(getMaterialsId(), gtqtcoreId("naquadah_residue_mkiv"))
                .fluid()
                .color(0xFF4500) // 橙红色
                .build();

        //  11260 Uranium-Thorium Carbides
        UraniumThoriumCarbides = new Material.Builder(getMaterialsId(), gtqtcoreId("uranium_thorium_carbides"))
                .dust()
                .color(0x163207)
                .iconSet(DULL)
                .components(Thorium, 12, Uranium235, 1, Carbon, 3)
                .build()
                .setFormula("Th12UC3", true);

        //  11261 Thorium Based Liquid Fuel
        ThoriumBasedLiquidFuel = new Material.Builder(getMaterialsId(), gtqtcoreId("thorium_based_liquid_fuel"))
                .liquid()
                .color(0x503266)
                .iconSet(DULL)
                .components(Thorium, 64, Lithium, 4, Barium, 2, Mercury, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("Th64Li4Ba2Hg", true);

        //  11262 Graphite-Uranium Mixture
        GraphiteUraniumMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("graphite_uranium_mixture"))
                .dust()
                .color(0x3A773D)
                .iconSet(DULL)
                .components(Graphite, 3, Uranium238, 1)
                .build()
                .setFormula("C3U", true);

        //  11263 Uranium Based Liquid Fuel
        UraniumBasedLiquidFuel = new Material.Builder(getMaterialsId(), gtqtcoreId("uranium_based_liquid_fuel"))
                .liquid()
                .color(0x00FF00)
                .iconSet(DULL)
                .components(Uranium238, 36, Potassium, 8, Naquadah, 4, Radon, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("U36K8Nq4Rn", true);

        //  11264 Plutonium Uranium Oxides
        PlutoniumUraniumOxides = new Material.Builder(getMaterialsId(), gtqtcoreId("plutonium_uranium_oxides"))
                .dust()
                .color(0xD11F4A)
                .iconSet(SHINY)
                .components(Plutonium239, 10, Uranium238, 2, Carbon, 8, Oxygen, 12)
                .build()
                .setFormula("Pu10O12U2C8", true);

        //  11265 Plutonium Based Liquid Fuel
        PlutoniumBasedLiquidFuel = new Material.Builder(getMaterialsId(), gtqtcoreId("plutonium_based_liquid_fuel"))
                .liquid()
                .color(0xEF1515)
                .iconSet(DULL)
                .components()
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("Pu64Rb8Cs16Nq+2", true);

        // 注册钍基流体燃料（激发）
        ThoriumFluidFuelExcited = new Material.Builder(getMaterialsId(), gtqtcoreId("thorium_fluid_fuel_excited"))
                .fluid()
                .color(0xFFFF00) // 黄色
                .build();

        // 注册钍基流体燃料（枯竭）
        ThoriumFluidFuelDepleted = new Material.Builder(getMaterialsId(), gtqtcoreId("thorium_fluid_fuel_depleted"))
                .fluid()
                .color(0x8B4513) // 棕色
                .build();

        // 注册铀基流体燃料（激发）
        UraniumFluidFuelExcited = new Material.Builder(getMaterialsId(), gtqtcoreId("uranium_fluid_fuel_excited"))
                .fluid()
                .color(0x00FF00) // 绿色
                .build();

        // 注册铀基流体燃料（枯竭）
        UraniumFluidFuelDepleted = new Material.Builder(getMaterialsId(), gtqtcoreId("uranium_fluid_fuel_depleted"))
                .fluid()
                .color(0x808080) // 灰色
                .build();

        // 注册钚基流体燃料（激发）
        PlutoniumFluidFuelExcited = new Material.Builder(getMaterialsId(), gtqtcoreId("plutonium_fluid_fuel_excited"))
                .fluid()
                .color(0x00FFFF) // 青色
                .build();

        // 注册钚基流体燃料（枯竭）
        PlutoniumFluidFuelDepleted = new Material.Builder(getMaterialsId(), gtqtcoreId("plutonium_fluid_fuel_depleted"))
                .fluid()
                .color(0x8B0000) // 深红色
                .build();

        AcetylsulfanilylChloride = new Material.Builder(getMaterialsId(), gtqtcoreId("acetylsulfanilyl_chloride"))
                .liquid()
                .color(0x00FFFF)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("C8H8ClNO3S", true);

        Sulfanilamide = new Material.Builder(getMaterialsId(), gtqtcoreId("sulfanilamide"))
                .liquid()
                .color(0x523b0a)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("C6H8N2O2S", true);

        AnimalCells = new Material.Builder(getMaterialsId(), gtqtcoreId("animal_cells"))
                .liquid()
                .color(0xc94996)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("???", true);

        GeneTherapyFluid = new Material.Builder(getMaterialsId(), gtqtcoreId("pluripotency_induction_gene_therapy_fluid"))
                .liquid()
                .color(0x6b2f66)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        GenePlasmids = new Material.Builder(getMaterialsId(), gtqtcoreId("pluripotency_induction_gene_plasmids"))
                .liquid()
                .color(0xabe053)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        RapidlyReplicatingAnimalCells = new Material.Builder(getMaterialsId(), gtqtcoreId("rapidly_replicating_animal_cells"))
                .liquid()
                .color(0x7a335e)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "????", true);

        Oct4Gene = new Material.Builder(getMaterialsId(), gtqtcoreId("oct_4_gene"))
                .liquid()
                .color(0x374f0d)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        SOX2Gene = new Material.Builder(getMaterialsId(), gtqtcoreId("sox_2_gene"))
                .liquid()
                .color(0x5d8714)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        KFL4Gene = new Material.Builder(getMaterialsId(), gtqtcoreId("kfl_4_gene"))
                .liquid()
                .color(0x759143)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        Cas9 = new Material.Builder(getMaterialsId(), gtqtcoreId("cas_9"))
                .liquid()
                .color(0x5f6e46)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        MycGene = new Material.Builder(getMaterialsId(), gtqtcoreId("myc_gene"))
                .liquid()
                .color(0x445724)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        Chitin = new Material.Builder(getMaterialsId(), gtqtcoreId("chitin"))
                .liquid()
                .color(0xcbd479)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        Catalase = new Material.Builder(getMaterialsId(), gtqtcoreId("catalase"))
                .liquid()
                .color(0xdb6596)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        B27Supplement = new Material.Builder(getMaterialsId(), gtqtcoreId("b_27_supplement"))
                .liquid()
                .color(0x386939)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("C142H230N36O44S", true);

        CleanAmmoniaSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("clear_ammonia_solution"))
                .liquid()
                .color(0x53c9a0)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("NH3(H2O)", true);

        Glutamine = new Material.Builder(getMaterialsId(), gtqtcoreId("glutamine"))
                .dust()
                .color(0xede9b4)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(DULL)
                .build()
                .setFormula("C5H10N2O3", true);

        Diaminotoluene = new Material.Builder(getMaterialsId(), gtqtcoreId("diaminotoluene"))
                .liquid()
                .color(0xEA8204)
                .components(Carbon, 7, Hydrogen, 7, Nitrogen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C6H3(NH2)2CH3", true);

        Dinitrotoluene = new Material.Builder(getMaterialsId(), gtqtcoreId("dinitrotoluene"))
                .liquid()
                .color(0xEAEFC9)
                .components(Carbon, 7, Hydrogen, 6, Nitrogen, 2, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        Phosgene = new Material.Builder(getMaterialsId(), gtqtcoreId("phosgene"))
                .gas()
                .color(0x48927C)
                .components(Carbon, 1, Oxygen, 1, Chlorine, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        TolueneDiisocyanate = new Material.Builder(getMaterialsId(), gtqtcoreId("toluene_diisocyanate"))
                .liquid()
                .color(0xCCCC66)
                .components(Carbon, 9, Hydrogen, 8, Nitrogen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("CH3C6H3(NCO)2", true);

        Polytetrahydrofuran = new Material.Builder(getMaterialsId(), gtqtcoreId("polytetrahydrofuran"))
                .liquid()
                .color(0x089B3E)
                .components(Carbon, 4, Hydrogen, 10, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(C4H8O)OH2", true);

        TolueneTetramethylDiisocyanate = new Material.Builder(getMaterialsId(), gtqtcoreId("toluene_tetramethyl_diisocyanate"))
                .liquid()
                .color(0xBFBFBF)
                .components(Carbon, 19, Hydrogen, 12, Oxygen, 3, Nitrogen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(CONH)2(C6H4)2CH2(C4O)", true);

        PolytetramethyleneGlycolRubber = new Material.Builder(getMaterialsId(), gtqtcoreId("polytetramethylene_glycol_rubber"))
                .polymer()
                .liquid()
                .color(0xFFFFFF)
                .flags(DISABLE_DECOMPOSITION, GENERATE_PLATE, GENERATE_RING, GENERATE_FOIL)
                .components(Carbon, 23, Hydrogen, 23, Oxygen, 5, Nitrogen, 2)
                .build()
                .setFormula("(CONH)2(C6H4)2CH2(C4O)HO(CH2)4OH", true);

        // 25073 Drilling Mud
        DrillingMud = new Material.Builder(getMaterialsId(), gtqtcoreId("drilling_mud"))
                .liquid()
                .color(0x996600)
                .iconSet(DULL)
                .build();

        // 25074 Used Drilling Mud
        UsedDrillingMud = new Material.Builder(getMaterialsId(), gtqtcoreId("used_drilling_mud"))
                .liquid()
                .color(0x998833)
                .iconSet(DULL)
                .build();

        // 5171 Calcite-Barite Slurry
        CalciteBariteSlurry = new Material.Builder(getMaterialsId(), gtqtcoreId("calcite_barite_slurry"))
                .liquid()
                .color(averageRGB(2,
                        Calcite.getMaterialRGB(),
                        Barite.getMaterialRGB()))
                .iconSet(DULL)
                .components(Calcite, 1, Barite, 1, Water, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(CaCO3)(BaSO4)(H2O)2", true);

        // 25022 Advanced Lubricant
        AdvancedLubricant = new Material.Builder(getMaterialsId(), gtqtcoreId("advanced_lubricant"))
                .liquid()
                .color(0xAD968F)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 20045 Methoxycresol
        Methoxycreosol = new Material.Builder(getMaterialsId(), gtqtcoreId("methoxycreosol"))
                .liquid()
                .color(0xAF4617)
                .components(Carbon, 8, Hydrogen, 10, Oxygen, 2)
                .build();

        // 20046 Creosol Mixture
        CreosolMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("creosol_mixture"))
                .liquid()
                .color(0x71191C)
                .components(Carbon, 21, Hydrogen, 24, Oxygen, 3)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(C8H10O)(C7H8O)(C6H6O)", true);

        // 20047 Xylenol
        Xylenol = new Material.Builder(getMaterialsId(), gtqtcoreId("xylenol"))
                .liquid()
                .color(0xCF7D10)
                .components(Carbon, 8, Hydrogen, 10, Oxygen, 1)
                .build();

        // 20048 Creosol
        Creosol = new Material.Builder(getMaterialsId(), gtqtcoreId("creosol"))
                .liquid()
                .color(0x704E46)
                .components(Carbon, 7, Hydrogen, 8, Oxygen, 1)
                .build();


        // 20050 Tricresyl Phosphate
        TricresylPhosphate = new Material.Builder(getMaterialsId(), gtqtcoreId("tricresyl_phosphate"))
                .liquid()
                .color(0x704E46)
                .components(Carbon, 21, Hydrogen, 21, Oxygen, 4, Phosphorus, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 20043 Polyethylene Glycol
        PolyethyleneGlycol = new Material.Builder(getMaterialsId(), gtqtcoreId("polyethylene_glycol"))
                .liquid()
                .color(0x5CD813)
                .components(Carbon, 2, Hydrogen, 4, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 5047 Chromium Doped Molybdenite
        ChromiumDopedMolybdenite = new Material.Builder(getMaterialsId(), gtqtcoreId("chromium_doped_molybdenite"))
                .dust()
                .color(0x9C5fB5)
                .iconSet(ROUGH)
                .flags(DISABLE_DECOMPOSITION)
                .components(Chrome, 1, Molybdenite, 1)
                .build()
                .setFormula("Cr:MoS2", true);

        // 20018 Polyurethane Resin
        PolyurethaneResin = new Material.Builder(getMaterialsId(), gtqtcoreId("polyurethane_resin"))
                .liquid()
                .color(0xE6E678)
                .iconSet(DULL)
                .components(Carbon, 17, Hydrogen, 16, Nitrogen, 2, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 20026 Polyurethane Catalyst A
        PolyurethaneCatalystA = new Material.Builder(getMaterialsId(), gtqtcoreId("polyurethane_catalyst_a"))
                .dust()
                .color(0x323232)
                .iconSet(DULL)
                .components(Butanol, 2, PropionicAcid, 1, Tin, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 20027 Nickel Tetracarbonyl
        NickelTetracarbonyl = new Material.Builder(getMaterialsId(), gtqtcoreId("nickel_tetracarbonyl"))
                .liquid()
                .color(0x6158BB)
                .components(Carbon, 4, Nickel, 1, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 20030 Diaminodiphenylmethane Mixture
        DiaminodiphenylmethaneMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("diaminodiphenylmethane_mixture"))
                .liquid()
                .color(0xFFF37A)
                .iconSet(DULL)
                .components(Carbon, 13, Hydrogen, 14, Nitrogen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 20028 Diphenylmethane Diisocyanate Mixture
        DiphenylmethaneDiisocyanateMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("diphenylmethane_diisocyanate_mixture"))
                .liquid()
                .color(0xFFE632)
                .iconSet(DULL)
                .components(Carbon, 15, Hydrogen, 10, Nitrogen, 2, Oxygen, 2, RareEarth, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 20021 Silicon Oil
        SiliconOil = new Material.Builder(getMaterialsId(), gtqtcoreId("silicon_oil"))
                .liquid()
                .color(0x9EA744)
                .iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION)
                .components(EthyleneOxide, 1, Dimethyldichlorosilane, 4, Water, 5)
                .build();

        // 20022 Diphenylmethane Diisocyanate
        DiphenylmethaneDiisocyanate = new Material.Builder(getMaterialsId(), gtqtcoreId("diphenylmethane_diisocyanate"))
                .dust()
                .color(0xFFE632)
                .iconSet(DULL)
                .components(Carbon, 15, Hydrogen, 10, Nitrogen, 2, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 5184 Mercury Dibromide
        MercuryDibromide = new Material.Builder(getMaterialsId(), gtqtcoreId("mercury_dibromide"))
                .dust()
                .color(0xD6E6F4)
                .components(Mercury, 1, Bromine, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 20023 Pentaerythritol
        Pentaerythritol = new Material.Builder(getMaterialsId(), gtqtcoreId("pentaerythritol"))
                .dust()
                .color(0x1A6FAF)
                .iconSet(ROUGH)
                .components(Carbon, 5, Hydrogen, 12, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 20016 Crystal Kevlar
        CrystalKevlar = new Material.Builder(getMaterialsId(), gtqtcoreId("crystal_kevlar"))
                .liquid()
                .color(0xF0F078)
                .components(Carbon, 14, Hydrogen, 10, Nitrogen, 2, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("[-CO-C6H4-CO-NH-C6H4-NH-]n", true);

        //  11266 Dirty Hexafluorosilicic Acid
        DirtyHexafluorosilicicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("dirty_hexafluorosilicic_acid"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(Stone.getMaterialRGB() + HydrofluoricAcid.getMaterialRGB())
                .iconSet(DULL)
                .components(Hydrogen, 2, Silicon, 1, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("H2SiF6?", true);

        //  11267 Diluted Hexafluorosilicic Acid
        DilutedHexafluorosilicicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("diluted_hexafluorosilicic_acid"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(Stone.getMaterialRGB() + HydrofluoricAcid.getMaterialRGB())
                .components(Hydrogen, 6, Oxygen, 2, Silicon, 1, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(H2SiF6)(H2O)2", true);

        //  11268 Dioxygen Difluoride
        DioxygenDifluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("dioxygen_difluoride"))
                .gas()
                .color(Oxygen.getMaterialRGB() + Fluorine.getMaterialRGB())
                .components(Fluorine, 2, Oxygen, 2)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .build();

        //  11269 Diluted Hydrofluoric Acid
        DilutedHydrofluoricAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("diluted_hydrofluoric_acid"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color((HydrofluoricAcid.getMaterialRGB() + Water.getMaterialRGB()) / 3)
                .components(HydrofluoricAcid, 1, Water, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11270 Tritium Hydride
        TritiumHydride = new Material.Builder(getMaterialsId(), gtqtcoreId("tritium_hydride"))
                .gas()
                .color(Tritium.getMaterialRGB() + Hydrogen.getMaterialRGB())
                .components(Tritium, 1, Hydrogen, 1)
                .build();

        //  11271 Helium-3 Hydride
        Helium3Hydride = new Material.Builder(getMaterialsId(), gtqtcoreId("helium_3_hydride"))
                .gas()
                .color(Helium3.getMaterialRGB() + Hydrogen.getMaterialRGB())
                .components(Helium3, 1, Hydrogen, 1)
                .build();

        //  11272 Xenic Acid
        XenicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("xenic_acid"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(Xenon.getMaterialRGB() + Hydrogen.getMaterialRGB() + Oxygen.getMaterialRGB())
                .components(Hydrogen, 2, Xenon, 1, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24133 Stone Residue
        StoneResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("stone_residue"))
                .dust()
                .color(Stone.getMaterialRGB() + RedMud.getMaterialRGB())
                .iconSet(ROUGH)
                .build();

        //  24134 Selected Stone Residue
        SelectedStoneResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("selected_stone_residue"))
                .dust()
                .color(Stone.getMaterialRGB() + Naquadah.getMaterialRGB())
                .iconSet(FINE)
                .build();

        //  24135 Partially Oxidized Stone Residue
        PartiallyOxidizedStoneResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("partially_oxidized_stone_residue"))
                .dust()
                .color(Stone.getMaterialRGB() + Oxygen.getMaterialRGB())
                .iconSet(DULL)
                .build();

        //  24136 Oxidized Residual Stone Slurry
        OxidizedResidualStoneSlurry = new Material.Builder(getMaterialsId(), gtqtcoreId("oxidized_residual_stone_slurry"))
                .liquid()
                .color(0x23ad7f)
                .iconSet(DULL)
                .build();

        //  24137 Inert Stone Residue
        InertStoneResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("inert_stone_residue"))
                .dust()
                .iconSet(ROUGH)
                .build();

        //  24138 Oxidized Stone Residue
        OxidizedStoneResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("oxidized_stone_residue"))
                .dust()
                .color(0x9F0000)
                .iconSet(DULL)
                .build();

        //  24139 Heavy Oxidized Stone Residue
        HeavyOxidizedStoneResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_oxidized_stone_residue"))
                .dust()
                .color(0x770000)
                .iconSet(DULL)
                .build();

        //  24140 Metallic Stone Residue
        MetallicStoneResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("metallic_stone_residue"))
                .dust()
                .color(0x904A59)
                .iconSet(SHINY)
                .build();

        //  24141 Heavy Metallic Stone Residue
        HeavyMetallicStoneResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_metallic_stone_residue"))
                .dust()
                .color(0x6C2635)
                .iconSet(METALLIC)
                .build();

        //  24142 Diamagnetic Residue
        DiamagneticResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("diamagnetic_residue"))
                .dust()
                .color((Calcium.getMaterialRGB() + Zinc.getMaterialRGB() + Copper.getMaterialRGB() + Gallium.getMaterialRGB() + Beryllium.getMaterialRGB() + Tin.getMaterialRGB()) / 15)
                .iconSet(DULL)
                .build()
                .setFormula("CaZnCuGaBeSn?", true);

        //  24143 Paramagnetic Residue
        ParamagneticResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("paramagnetic_residue"))
                .dust()
                .color((Sodium.getMaterialRGB() + Potassium.getMaterialRGB() + Magnesium.getMaterialRGB() + Titanium.getMaterialRGB() + Vanadium.getMaterialRGB() + Manganese.getMaterialRGB()) / 15)
                .iconSet(DULL)
                .build()
                .setFormula("NaKMgTiVMn?", true);

        //  24144 Ferromagnetic Residue
        FerromagneticResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("ferromagnetic_residue"))
                .dust()
                .color(Iron.getMaterialRGB() + Nickel.getMaterialRGB() + Cobalt.getMaterialRGB())
                .iconSet(DULL)
                .build()
                .setFormula("FeNiCo?", true);

        //  24145 Heavy Diamagnetic Residue
        HeavyDiamagneticResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_diamagnetic_residue"))
                .dust()
                .color((Lead.getMaterialRGB() + Mercury.getMaterialRGB() + Cadmium.getMaterialRGB() + Indium.getMaterialRGB() + Gold.getMaterialRGB() + Bismuth.getMaterialRGB()) / 15)
                .iconSet(DULL)
                .build()
                .setFormula("PbCdInAuBi?", true);

        //  24146 Heavy Paramagnetic Residue
        HeavyParamagneticResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_paramagnetic_residue"))
                .dust()
                .color((Thorium.getMaterialRGB() + Thallium.getMaterialRGB() + Uranium235.getMaterialRGB() + Tungsten.getMaterialRGB() + Hafnium.getMaterialRGB() + Tantalum.getMaterialRGB()) / 15)
                .iconSet(DULL)
                .build()
                .setFormula("ThUWHfTaTl", true);

        //  24147 Heavy Ferromagnetic Residue
        HeavyFerromagneticResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_ferromagnetic_residue"))
                .dust()
                .color(DysprosiumOxide.getMaterialRGB() * 3 / 11)
                .iconSet(DULL)
                .build()
                .setFormula("Dy2O3?", true);

        //  24148 Superheavy Stone Residue
        SuperheavyStoneResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("superheavy_stone_residue"))
                .dust()
                .color(0x59FF7E)
                .iconSet(SHINY)
                .build();

        //  24149 Clean Inert Stone Residue
        CleanInertStoneResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("clean_inert_stone_residue"))
                .dust()
                .color(InertStoneResidue.getMaterialRGB() + FluoroantimonicAcid.getMaterialRGB())
                .iconSet(SHINY)
                .build();

        //  24150 Ultra-acidic Stone Residue Solution
        UltraacidicStoneResidueSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("ultraacidic_stone_residue_solution"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(FluoroantimonicAcid.getMaterialRGB() + Helium3Hydride.getMaterialRGB())
                .iconSet(DULL)
                .build();

        //  24151 Dusty Helium-3
        DustyHelium3 = new Material.Builder(getMaterialsId(), gtqtcoreId("dusty_helium_3"))
                .liquid()
                .color(Helium3.getMaterialRGB() + Stone.getMaterialRGB())
                .iconSet(DULL)
                .build()
                .setFormula("He-3?", true);

        //  24152 Taranium-enriched Helium-3
        TaraniumEnrichedHelium3 = new Material.Builder(getMaterialsId(), gtqtcoreId("taranium_enriched_helium_3"))
                .liquid()
                .color(Taranium.getMaterialRGB() + Helium3.getMaterialRGB())
                .iconSet(DULL)
                .build()
                .setFormula("TnHe-3?", true);

        //  24153 Taranium-semidepleted Helium-3
        TaraniumSemidepletedHelium3 = new Material.Builder(getMaterialsId(), gtqtcoreId("taranium_semidepleted_helium_3"))
                .liquid()
                .color(Taranium.getMaterialRGB() + Helium3.getMaterialRGB() - 10)
                .iconSet(DULL)
                .build()
                .setFormula("TnHe-3?", true);

        //  24154 Taranium-depleted Helium-3
        TaraniumDepletedHelium3 = new Material.Builder(getMaterialsId(), gtqtcoreId("taranium_depleted_helium_3"))
                .liquid()
                .plasma()
                .color(Taranium.getMaterialRGB() + Helium3.getMaterialRGB() - 50)
                .iconSet(DULL)
                .build()
                .setFormula("TnHe-3?", true);

        //  24155 Taranium Rich Dusty Helium-3
        TaraniumRichDustyHelium3 = new Material.Builder(getMaterialsId(), gtqtcoreId("taranium_rich_dusty_helium_3"))
                .plasma()
                .color(Taranium.getMaterialRGB() + Helium3.getMaterialRGB())
                .build()
                .setFormula("TnHe-3?", true);

        //  24156 Taranium Rich Helium4
        TaraniumRichHelium4 = new Material.Builder(getMaterialsId(), gtqtcoreId("taranium_rich_helium_4"))
                .liquid()
                .plasma()
                .color(Taranium.getMaterialRGB() + Krypton.getMaterialRGB())
                .build()
                .setFormula("TnHe-4?", true);

        //  24157 Taranium Poor Helium
        TaraniumPoorHelium = new Material.Builder(getMaterialsId(), gtqtcoreId("taranium_poor_helium"))
                .liquid()
                .color(Taranium.getMaterialRGB() + Helium.getMaterialRGB() - 80)
                .build()
                .setFormula("He?", true);

        //  24158 Taranium Poor Helium Mixture
        TaraniumPoorHeliumMixture = new Material.Builder(getMaterialsId(), gtqtcoreId("taranium_poor_helium_mixture"))
                .liquid()
                .color(Taranium.getMaterialRGB() + Helium3.getMaterialRGB() + Helium.getMaterialRGB())
                .build();

        //  24159 Heavy Fluorinated Trinium Solution
        HeavyFluorinatedTriniumSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_fluorinated_trinium_solution"))
                .liquid()
                .color(0x8C117D)
                .build();

        //  11284 Fluorocarborane
        Fluorocarborane = new Material.Builder(getMaterialsId(), gtqtcoreId("fluorocarborane"))
                .dust()
                .color(0x59B35C)
                .iconSet(BRIGHT)
                .components(Carbon, 1, Hydrogen, 2, Boron, 11, Fluorine, 11)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("HCHB11F11", true);

        //  13201 Perfluorobenzene
        Perfluorobenzene = new Material.Builder(getMaterialsId(), gtqtcoreId("perfluorobenzene"))
                .liquid()
                .color(0x39733B)
                .components(Carbon, 6, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11273 Krypton Difluoride
        KryptonDifluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("krypton_difluoride"))
                .gas()
                .color(Krypton.getMaterialRGB() + Fluorine.getMaterialRGB())
                .components(Krypton, 1, Fluorine, 2)
                .build();

        //  11274 Trinium Tetrafluoride
        TriniumTetrafluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("trinium_tetrafluoride"))
                .dust()
                .color(0xBA16A6)
                .iconSet(DULL)
                .components(Trinium, 1, Fluorine, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11275 Potassium Fluoride
        PotassiumFluoride = new Material.Builder(getMaterialsId(), gtqtcoreId("potassium_fluoride"))
                .dust()
                .color(Potassium.getMaterialRGB() + Fluorine.getMaterialRGB())
                .iconSet(ROUGH)
                .components(Potassium, 1, Fluorine, 1)
                .build();

        //  11276 Caesium Carborane
        CaesiumCarborane = new Material.Builder(getMaterialsId(), gtqtcoreId("caesium_carborane"))
                .dust()
                .color(Caesium.getMaterialRGB() + Carbon.getMaterialRGB())
                .iconSet(DULL)
                .components(Caesium, 1, Carbon, 1, Boron, 11, Hydrogen, 12)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11277 Silver Nitrate
        SilverNitrate = new Material.Builder(getMaterialsId(), gtqtcoreId("silver_nitrate"))
                .dust()
                .color(Silver.getMaterialRGB() + NitricAcid.getMaterialRGB())
                .iconSet(SHINY)
                .components(Silver, 1, Nitrogen, 1, Oxygen, 3)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11278 Caesium Nitrate
        CaesiumNitrate = new Material.Builder(getMaterialsId(), gtqtcoreId("caesium_nitrate"))
                .dust()
                .color(Caesium.getMaterialRGB() + NitricAcid.getMaterialRGB())
                .iconSet(ROUGH)
                .components(Caesium, 1, Nitrogen, 1, Oxygen, 3)
                .flags(DECOMPOSITION_BY_ELECTROLYZING)
                .build();

        //  11279 Silver Iodide
        SilverIodide = new Material.Builder(getMaterialsId(), gtqtcoreId("silver_iodide"))
                .dust()
                .iconSet(SHINY)
                .color(Silver.getMaterialRGB() + Iodine.getMaterialRGB())
                .components(Silver, 1, Iodine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11280 Caesium Carborane Precursor
        CaesiumCarboranePrecursor = new Material.Builder(getMaterialsId(), gtqtcoreId("caesium_carborane_precursor"))
                .dust()
                .color(CaesiumCarborane.getMaterialRGB())
                .iconSet(SAND)
                .components(Caesium, 1, Boron, 10, Hydrogen, 21, Carbon, 4, Nitrogen, 1, Chlorine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("CsB10H12CN(CH3)3Cl", true);

        //  11281 Caesium Hydroxide
        CaesiumHydroxide = new Material.Builder(getMaterialsId(), gtqtcoreId("caesium_hydroxide"))
                .dust()
                .color(Caesium.getMaterialRGB() + Hydrogen.getMaterialRGB() + Oxygen.getMaterialRGB())
                .components(Caesium, 1, Oxygen, 1, Hydrogen, 1)
                .flags(DECOMPOSITION_BY_ELECTROLYZING)
                .build();

        //  11282 Sodium Borohydride
        SodiumBorohydride = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_borohydride"))
                .dust()
                .color(Sodium.getMaterialRGB() + Boron.getMaterialRGB())
                .iconSet(ROUGH)
                .components(Sodium, 1, Boron, 1, Hydrogen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11283 Sodium Tetrafluoroborate
        SodiumTetrafluoroborate = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_tetrafluoroborate"))
                .dust()
                .color(Sodium.getMaterialRGB() + BoronTrifluoride.getMaterialRGB())
                .iconSet(SAND)
                .components(Sodium, 1, Boron, 1, Fluorine, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13182 Trimethylchlorosilane
        Trimethylchlorosilane = new Material.Builder(getMaterialsId(), gtqtcoreId("trimethylchlorosilane"))
                .liquid()
                .color(0x702169)
                .components(Carbon, 3, Hydrogen, 9, Silicon, 1, Chlorine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(CH3)3SiCl", true);

        //  13202 Borane Dimethylsulfide
        BoraneDimethylsulfide = new Material.Builder(getMaterialsId(), gtqtcoreId("borane_dimethylsulfide"))
                .liquid()
                .color(Lead.getMaterialRGB() + Boron.getMaterialRGB())
                .components(Carbon, 2, Hydrogen, 9, Boron, 1, Sulfur, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13203 Decaborane
        Decaborane = new Material.Builder(getMaterialsId(), gtqtcoreId("decaborane"))
                .dust()
                .color(0x4C994F)
                .iconSet(ROUGH)
                .components(Boron, 10, Hydrogen, 14)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13204 Boron Trifluoride Etherate
        BoronTrifluorideEtherate = new Material.Builder(getMaterialsId(), gtqtcoreId("boron_trifluoride_etherate"))
                .liquid()
                .color(0xBF6E6E)
                .components(Boron, 1, Fluorine, 3, Carbon, 4, Hydrogen, 7, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(BF3)(C2H5)2O", true);

        //  13205 Diethyl Ether
        DiethylEther = new Material.Builder(getMaterialsId(), gtqtcoreId("diethyl_ether"))
                .liquid()
                .color(0xFFA4A3)
                .components(Carbon, 4, Hydrogen, 10, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(C2H5)2O", true);

        //  13145 Dimethyl Sulfide
        DimethylSulfide = new Material.Builder(getMaterialsId(), gtqtcoreId("dimethyl_sulfide"))
                .liquid()
                .color(0xFFC66B)
                .components(Carbon, 2, Hydrogen, 6, Sulfur, 1)
                .build()
                .setFormula("(CH3)2S", true);

        //  11218 Sodium Ethoxide
        SodiumEthoxide = new Material.Builder(getMaterialsId(), gtqtcoreId("sodium_ethoxide"))
                .dust()
                .color(Sodium.getMaterialRGB() + Ethanol.getMaterialRGB())
                .iconSet(DULL)
                .components(Carbon, 2, Hydrogen, 5, Oxygen, 1, Sodium, 1)
                .build();

        //  13142 Palladium-Fullerene Matrix
        PalladiumFullereneMatrix = new Material.Builder(getMaterialsId(), gtqtcoreId("palladium_fullerene_matrix"))
                .dust()
                .color(0x40AEE0)
                .iconSet(SHINY)
                .components(Palladium, 1, Carbon, 73, Hydrogen, 15, Nitrogen, 1, Iron, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13143 PCBS
        PCBS = new Material.Builder(getMaterialsId(), gtqtcoreId("pcbs"))
                .liquid()
                .color(0x11B557)
                .components(Carbon, 80, Hydrogen, 21, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13144 Phenylpentanoic Acid
        PhenylpentanoicAcid = new Material.Builder(getMaterialsId(), gtqtcoreId("phenylpentanoic_acid"))
                .liquid()
                .color(0x6F4303)
                .components(Carbon, 11, Hydrogen, 14, Oxygen, 2)
                .build();

        //  13146 Ferrocenylfulleropyrddolidine
        Ferrocenylfulleropyrddolidine = new Material.Builder(getMaterialsId(), gtqtcoreId("ferrocenylfulleropyrddolidine"))
                .liquid()
                .color(0x67AE4C)
                .components(Carbon, 74, Hydrogen, 19, Iron, 1, Nitrogen, 1)
                .build();

        //  13147 Sarcosine
        Sarcosine = new Material.Builder(getMaterialsId(), gtqtcoreId("sarcosine"))
                .dust()
                .color(0x328534)
                .iconSet(SHINY)
                .components(Carbon, 3, Hydrogen, 7, Nitrogen, 1, Oxygen, 2)
                .build();

        //  13148 Glycine
        Glycine = new Material.Builder(getMaterialsId(), gtqtcoreId("glycine"))
                .liquid()
                .color(0x95BA83)
                .components(Carbon, 2, Hydrogen, 5, Nitrogen, 1, Oxygen, 2)
                .build()
                .setFormula("NH2CH2COOH", true);

        //  13149 Ferrocene
        Ferrocene = new Material.Builder(getMaterialsId(), gtqtcoreId("ferrocene"))
                .liquid()
                .color(0x4D3B61)
                .components(Carbon, 10, Hydrogen, 10, Iron, 1)
                .build();

        GTQTMaterials.HafniumCarbide = new Material.Builder(getMaterialsId(), gtqtcoreId("hafnium_carbide"))
                .dust()
                .iconSet(MaterialIconSet.METALLIC)
                .colorAverage()
                .components(Hafnium, 1, Carbon, 1)
                .build();

        GTQTMaterials.SeaborgiumCarbide = new Material.Builder(getMaterialsId(), gtqtcoreId("seaborgium_carbide"))
                .dust()
                .iconSet(MaterialIconSet.METALLIC)
                .colorAverage()
                .components(Seaborgium, 1, Carbon, 1)
                .build();

        GTQTMaterials.TantalumHafniumSeaborgiumCarbide = new Material.Builder(getMaterialsId(), gtqtcoreId("tantalum_hafnium_seaborgium_carbide"))
                .ingot()
                .iconSet(MaterialIconSet.SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .colorAverage()
                .build()
                .setFormula("4TaHf3Sg10C7");

        GTQTMaterials.TantalumHafniumSeaborgiumCarboNitride = new Material.Builder(getMaterialsId(), gtqtcoreId("tantalum_hafnium_seaborgium_carbonitide"))
                .ingot()
                .iconSet(MaterialIconSet.SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .color(0x1c1c1c)
                .components(GTQTMaterials.TantalumHafniumSeaborgiumCarbide, 1, Nitrogen, 1)
                .build()
                .setFormula("4TaHf3Sg10C7N");

        LithiumIodide = new Material.Builder(getMaterialsId(), gtqtcoreId("lithium_iodide"))
                .dust()
                .color(Lithium.getMaterialRGB() + Iodine.getMaterialRGB())
                .iconSet(DULL)
                .components(Lithium, 1, Iodine, 1)
                .build();

        GTQTMaterials.LaPrNdCeOxidesSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("la_pr_nd_ce_oxides_solution"))
                .fluid()
                .color(0x9CE3DB)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .components(GTQTMaterials.LanthanumOxide, 1, GTQTMaterials.PraseodymiumOxide, 1, GTQTMaterials.NeodymiumOxide, 1, Cerium, 2, Oxygen, 3)
                .build();

        GTQTMaterials.ScEuGdSmOxidesSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("sc_eu_gd_sm_oxides_solution"))
                .fluid()
                .color(0xFFFF99)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .components(GTQTMaterials.ScandiumOxide, 1, GTQTMaterials.EuropiumOxide, 1, GTQTMaterials.GadoliniumOxide, 1, GTQTMaterials.SamariumOxide, 1)
                .build();

        GTQTMaterials.YTbDyHoOxidesSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("y_tb_dy_ho_oxides_solution"))
                .fluid()
                .color(0x99FF99)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .components(GTQTMaterials.YttriumOxide, 1, GTQTMaterials.TerbiumOxide, 1, GTQTMaterials.DysprosiumOxide, 1, GTQTMaterials.HolmiumOxide, 1)
                .build();

        GTQTMaterials.ErTmYbLuOxidesSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("er_tm_yb_lu_oxides_solution"))
                .fluid()
                .color(0xFFB3FF)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .components(GTQTMaterials.ErbiumOxide, 1, GTQTMaterials.ThuliumOxide, 1, GTQTMaterials.YtterbiumOxide, 1, GTQTMaterials.LutetiumOxide, 1)
                .build();

        GTQTMaterials.PlatinumGroupConcentrate = new Material.Builder(getMaterialsId(), gtqtcoreId("platinum_group_concentrate"))
                .fluid()
                .color(0xFFFFA6)
                .flags(DISABLE_DECOMPOSITION)
                .components(Gold, 1, Platinum, 1, Palladium, 1, HydrochloricAcid, 6)
                .build();

        GTQTMaterials.PlatinumGroupResidue = new Material.Builder(getMaterialsId(), gtqtcoreId("platinum_group_residue"))
                .dust()
                .color(0x64632E)
                .iconSet(MaterialIconSet.ROUGH)
                .flags(DISABLE_DECOMPOSITION)
                .components(Iridium, 1, Osmium, 1, Rhodium, 1, Ruthenium, 1, RareEarth, 1)
                .build();

        GTQTMaterials.GrapheneOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("graphene_oxide"))
                .dust()
                .color(0x777777)
                .iconSet(MaterialIconSet.ROUGH)
                .flags(DISABLE_DECOMPOSITION)
                .components(Graphene, 1, Oxygen, 1)
                .build();

        GTQTMaterials.AmmoniumNitrate = new Material.Builder(getMaterialsId(), gtqtcoreId("ammonium_nitrate"))
                .fluid()
                .color(0xA59ED7)
                .components(Ammonia, 1, NitricAcid, 1)
                .build()
                .setFormula("NH4NO3", true);

        GTQTMaterials.Wollastonite = new Material.Builder(getMaterialsId(), gtqtcoreId("wollastonite"))
                .dust()
                .color(0xF0F0F0)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .components(Quicklime, 2, SiliconDioxide, 3)
                .build()
                .setFormula("CaSiO3", true);

        GTQTMaterials.RoastedSphalerite = new Material.Builder(getMaterialsId(), gtqtcoreId("roasted_sphalerite"))
                .dust()
                .color(0xAC8B5C)
                .iconSet(MaterialIconSet.FINE)
                .flags(DISABLE_DECOMPOSITION)
                .components(Gallium, 1, GTQTMaterials.GermaniumDioxide, 1)
                .build();

        GTQTMaterials.WaelzOxide = new Material.Builder(getMaterialsId(), gtqtcoreId("waelz_oxide"))
                .dust()
                .color(0xB8B8B8)
                .iconSet(MaterialIconSet.FINE)
                .flags(DISABLE_DECOMPOSITION)
                .components(Zinc, 1, GTQTMaterials.GermaniumDioxide, 1)
                .build();

        //TODO move to first degree
        GTQTMaterials.WaelzSlag = new Material.Builder(getMaterialsId(), gtqtcoreId("waelz_slag"))
                .dust()
                .color(0xAC8B5C)
                .iconSet(MaterialIconSet.ROUGH)
                .flags(DISABLE_DECOMPOSITION)
                .components(Gallium, 1, Zinc, 1, Sulfur, 1, Oxygen, 4)
                .build();

        GTQTMaterials.ImpureGermaniumDioxide = new Material.Builder(getMaterialsId(), gtqtcoreId("impure_germanium_dioxide"))
                .dust()
                .color(0x666666)
                .iconSet(MaterialIconSet.ROUGH)
                .flags(DISABLE_DECOMPOSITION)
                .components(GTQTMaterials.GermaniumDioxide, 1)
                .build()
                .setFormula("GeO2?", true);

        GTQTMaterials.ErbiumDopedZBLANGlass = new Material.Builder(getMaterialsId(), gtqtcoreId("erbium_doped_zblan_glass"))
                .ingot()
                .color(0x505444)
                .iconSet(MaterialIconSet.BRIGHT)
                .flags(NO_SMASHING, NO_WORKING, DISABLE_DECOMPOSITION, GENERATE_PLATE)
                .components(GTQTMaterials.ZBLANGlass, 1, Erbium, 1)
                .build()
                .setFormula("(ZrF4)5(BaF2)2(LaF3)(AlF3)(NaF)2Er", true);

        GTQTMaterials.PraseodymiumDopedZBLANGlass = new Material.Builder(getMaterialsId(), gtqtcoreId("praseodymium_doped_zblan_glass"))
                .ingot()
                .color(0xC5C88D)
                .iconSet(MaterialIconSet.BRIGHT)
                .flags(NO_SMASHING, NO_WORKING, DISABLE_DECOMPOSITION, GENERATE_PLATE)
                .components(GTQTMaterials.ZBLANGlass, 1, Praseodymium, 1)
                .build()
                .setFormula("(ZrF4)5(BaF2)2(LaF3)(AlF3)(NaF)2Pr", true);

        GTQTMaterials.NdYAG = new Material.Builder(getMaterialsId(), gtqtcoreId("nd_yag")) //TODO "Yttrium-Aluminium-Garnet" Tooltip
                .gem()
                .color(0xD99DE4)
                .iconSet(MaterialIconSet.GEM_VERTICAL)
                .flags(CRYSTALLIZABLE, DECOMPOSITION_BY_CENTRIFUGING, GENERATE_LENS)
                .components(GTQTMaterials.YttriumOxide, 2, GTQTMaterials.NeodymiumOxide, 1, GTQTMaterials.Alumina, 5)
                .build()
                .setFormula("NdY2Al5O12", true);

        GTQTMaterials.BismuthFerrite = new Material.Builder(getMaterialsId(), gtqtcoreId("bismuth_ferrite")) //TODO "Multiferroic!" tooltip
                .gem()
                .color(0x43634B)
                .iconSet(MaterialIconSet.MAGNETIC)
                .flags(CRYSTALLIZABLE, GENERATE_PLATE)
                .components(GTQTMaterials.BismuthTrioxide, 2, GTQTMaterials.FerricOxide, 2)
                .build()
                .setFormula("BiFeO3", true);

        GTQTMaterials.ChromiumGermaniumTellurideMagnetic = new Material.Builder(getMaterialsId(), gtqtcoreId("cgt_magnetic"))
                .ingot()
                .color(0x8F103E)
                .iconSet(MaterialIconSet.MAGNETIC)
                .flags(GENERATE_ROD, GENERATE_LONG_ROD, IS_MAGNETIC)
                .components(GTQTMaterials.ChromiumGermaniumTelluride, 1)
                .ingotSmeltInto(GTQTMaterials.ChromiumGermaniumTelluride)
                .arcSmeltInto(GTQTMaterials.ChromiumGermaniumTelluride)
                .macerateInto(GTQTMaterials.ChromiumGermaniumTelluride)
                .build();
        GTQTMaterials.ChromiumGermaniumTelluride.getProperty(PropertyKey.INGOT).setMagneticMaterial(GTQTMaterials.ChromiumGermaniumTellurideMagnetic);

        GTQTMaterials.HeavyAlkaliChlorideSolution = new Material.Builder(getMaterialsId(), gtqtcoreId("heavy_alkali_chloride_solution"))
                .fluid()
                .color(0x8F5353)
                .flags(DISABLE_DECOMPOSITION)
                .components(Rubidium, 1, Caesium, 2, Chlorine, 6, Water, 2)
                .build()
                .setFormula("RbCl(CsCl)2Cl3(H2O)2", true);


        GTQTMaterials.LutetiumTantalate = new Material.Builder(getMaterialsId(), gtqtcoreId("lutetium_tantalite"))
                .ingot()
                .iconSet(MaterialIconSet.SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .components(Lutetium, 1, Tantalum, 1, Oxygen, 4)
                .color(0xaccde6)
                .build();

        GTQTMaterials.Iridrhodruthenium = new Material.Builder(getMaterialsId(), gtqtcoreId("iridrhodruthenium"))
                .ingot()
                .iconSet(MaterialIconSet.SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .components(Ruthenium, 8, Rhodium, 1, Iridium, 1)
                .colorAverage()
                .build();

        GTQTMaterials.HEA_1 = new Material.Builder(getMaterialsId(), gtqtcoreId("high_entropy_alloy_1"))
                .ingot()
                .iconSet(MaterialIconSet.METALLIC)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .components(Chrome, 5, Niobium, 8, Silicon, 7, Titanium, 3, Zirconium, 5)
                .colorAverage()
                .build();

        GTQTMaterials.HEA_2 = new Material.Builder(getMaterialsId(), gtqtcoreId("high_entropy_alloy_2"))
                .ingot()
                .fluid()
                .iconSet(MaterialIconSet.METALLIC)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .components(Zirconium, 6, Tungsten, 4, Vanadium, 5, Cobalt, 3, Manganese, 4)
                .colorAverage()
                .build();

        GTQTMaterials.HEA_3 = new Material.Builder(getMaterialsId(), gtqtcoreId("high_entropy_alloy_3"))
                .ingot()
                .fluid()
                .iconSet(MaterialIconSet.SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .components(Aluminium, 5, Chrome, 5, Molybdenum, 7, Tantalum, 9, Titanium, 6, Zirconium, 4, Nitrogen, 21)
                .colorAverage()
                .build();


    }
}
