package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.attribute.FluidAttributes;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.material.properties.BlastProperty;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import net.minecraft.util.text.TextFormatting;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static gregtech.api.util.GTUtility.gregtechId;
import static gregtechfoodoption.utils.GTFOUtils.averageRGB;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;


public class SecondDegreeMaterials {
    private static int startId = 21000;
    private static final int END_ID = startId + 1000;

    public SecondDegreeMaterials() {
    }

    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static void register() {

        GTQTMaterials.PineOil = new Material.Builder(getMaterialsId(), gregtechId("pine_oil"))
                .fluid()
                .color(0xd6ac37)
                .build();

        GTQTMaterials.Periodicium = new Material.Builder(getMaterialsId(), gregtechId("periodicium"))
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

        //注册泡沫
        GTQTMaterials.AlmandineFront = new Material.Builder(getMaterialsId(), gregtechId("almandine_front")).fluid().color(0xD70505).build();
        GTQTMaterials.PentlanditeFront = new Material.Builder(getMaterialsId(), gregtechId("pentlandite_front")).fluid().color(0x8c800a).build();
        GTQTMaterials.ChalcopyriteFront = new Material.Builder(getMaterialsId(), gregtechId("chalcopyrite_front")).fluid().color(0x896826).build();
        GTQTMaterials.GrossularFront = new Material.Builder(getMaterialsId(), gregtechId("grossular_front")).fluid().color(0xab5860).build();
        GTQTMaterials.MonaziteFront = new Material.Builder(getMaterialsId(), gregtechId("monazite_front")).fluid().color(0x2e3f2e).build();
        GTQTMaterials.NickelFront = new Material.Builder(getMaterialsId(), gregtechId("nickel_front")).fluid().color(0xABABD5).build();
        GTQTMaterials.PlatinumFront = new Material.Builder(getMaterialsId(), gregtechId("platinum_front")).fluid().color(0xe2e2b2).build();
        GTQTMaterials.PyropeFront = new Material.Builder(getMaterialsId(), gregtechId("pyrope_front")).fluid().color(0x682E57).build();
        GTQTMaterials.RedstoneFront = new Material.Builder(getMaterialsId(), gregtechId("redstone_front")).fluid().color(0xAC0505).build();
        GTQTMaterials.SpessartineFront = new Material.Builder(getMaterialsId(), gregtechId("spessartine_front")).fluid().color(0XDF5A5A).build();
        GTQTMaterials.SphaleriteFront = new Material.Builder(getMaterialsId(), gregtechId("sphalerite_front")).fluid().color(0xD9D9D9).build();

        GTQTMaterials.MetallicHydrogen = new Material.Builder(getMaterialsId(), gregtechId("metallic_hydrogen"))
                .ingot().fluid()
                .iconSet(MaterialIconSet.SHINY)
                .flags(GENERATE_PLATE, GENERATE_RING, GENERATE_ROUND, GENERATE_ROTOR, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_LONG_ROD, GENERATE_FRAME)
                .color(0x4682B4)
                .fluidPipeProperties(10240, 32000, true, true, true, true)
                .components(Hydrogen, 1)
                .build();

        GTQTMaterials.Ethylenimine = new Material.Builder(getMaterialsId(), gregtechId("ethylenimine"))
                .fluid()
                .color(0x483D8B)
                .components(Carbon, 2, Hydrogen, 5, Nitrogen, 1)
                .build();

        GTQTMaterials.Polyethyleneimine = new Material.Builder(getMaterialsId(), gregtechId("polyethylenimine"))
                .fluid()
                .color(0x483DB4)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 2, Hydrogen, 5, Nitrogen, 1)
                .build();

        GTQTMaterials.RedMud = new Material.Builder(getMaterialsId(), gregtechId("red_mud"))
                .fluid()
                .color(0x483DB4)
                .flags(DISABLE_DECOMPOSITION)
                .components(Rutile, 1, Gallium, 1, HydrochloricAcid, 2)
                .build();

        GTQTMaterials.CarbenDisulfide = new Material.Builder(getMaterialsId(), gregtechId("carbon_disulfide"))
                .fluid()
                .colorAverage()
                .components(Carbon, 1, Sulfur, 2)
                .build();

        GTQTMaterials.UreaMix = new Material.Builder(getMaterialsId(), gregtechId("urea_mix"))
                .fluid()
                .color(0x443610)
                .build();

        GTQTMaterials.FermentationBase = new Material.Builder(getMaterialsId(), gregtechId("fermentation_base"))
                .fluid()
                .color(0x5E5839)
                .build();

        GTQTMaterials.Resin = new Material.Builder(getMaterialsId(), gregtechId("resin"))
                .fluid()
                .color(0x353533)
                .build();

        GTQTMaterials.CalciumCarbonate = new Material.Builder(getMaterialsId(), gregtechId("calcium_carbonate"))
                .dust()
                .components(Materials.Calcium, 1, Materials.Carbon, 1, Materials.Oxygen, 3)
                .color(0xE8E8CB)
                .build();

        GTQTMaterials.PropionicAcid = new Material.Builder(getMaterialsId(), gregtechId("propionic_acid"))
                .fluid()
                .color(0xB3BC88)
                .build();

        GTQTMaterials.SodiumAluminate = new Material.Builder(getMaterialsId(), gregtechId("sodium_aluminate"))
                .dust()
                .colorAverage()
                .components(Sodium, 1, Aluminium, 1, Oxygen, 2)
                .build();

        GTQTMaterials.CarbenDisulfide = new Material.Builder(getMaterialsId(), gregtechId("carbon_disulfide"))
                .fluid()
                .colorAverage()
                .components(Carbon, 1, Sulfur, 2)
                .build();

        GTQTMaterials.CarbenDisulfide = new Material.Builder(getMaterialsId(), gregtechId("carbon_disulfide"))
                .fluid()
                .colorAverage()
                .components(Carbon, 1, Sulfur, 2)
                .build();

        //硫酸锌粉
        GTQTMaterials.ZincSulfate = new Material.Builder(getMaterialsId(), gregtechId("zinc_sulfate"))
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
        GTQTMaterials.LeanGoldBs = new Material.Builder(getMaterialsId(), gregtechId("len_gold_bs"))
                .dust()
                .color(0xFFFF00)
                .build();

        //氰化钠浸出
        GTQTMaterials.LeanGoldJc = new Material.Builder(getMaterialsId(), gregtechId("len_gold_js"))
                .fluid()
                .color(0xFFFF00)
                .build();

        GTQTMaterials.LeanCopperJc = new Material.Builder(getMaterialsId(), gregtechId("len_copper_js"))
                .dust()
                .color(0xFFFF00)
                .build();

        GTQTMaterials.LeanGoldCl = new Material.Builder(getMaterialsId(), gregtechId("len_gold_Cl"))
                .fluid()
                .color(0xFFFF00)
                .build();

        GTQTMaterials.LeanGoldDr = new Material.Builder(getMaterialsId(), gregtechId("len_gold_dr"))
                .dust()
                .color(0xFFFF00)
                .build();

        GTQTMaterials.RichGoldBs = new Material.Builder(getMaterialsId(), gregtechId("rich_gold_bs"))
                .dust()
                .color(0xFFFF00)
                .build();

        GTQTMaterials.RichCopperJc = new Material.Builder(getMaterialsId(), gregtechId("rich_copper_js"))
                .dust()
                .color(0xFFFF00)
                .build();

        //  13078 Dichlorocyclooctadieneplatinium
        GTQTMaterials.Dichlorocyclooctadieneplatinium = new Material.Builder(getMaterialsId(), gregtechId("dichlorocyclooctadieneplatinium"))
                .dust()
                .color(0xD4E982)
                .iconSet(BRIGHT)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 8, Hydrogen, 12, Chlorine, 2, Platinum, 1)
                .build();

        //  13079 Diiodobiphenyl
        GTQTMaterials.Diiodobiphenyl = new Material.Builder(getMaterialsId(), gregtechId("diiodobiphenyl"))
                .dust()
                .color(0x000C52)
                .iconSet(METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 12, Hydrogen, 8, Iodine, 2)
                .build();

        //  11086 Silver Tetrafluoroborate
        GTQTMaterials.SilverTetrafluoroborate = new Material.Builder(getMaterialsId(), gregtechId("silver_tetrafluoroborate"))
                .liquid()
                .color(0x818024)
                .flags(DISABLE_DECOMPOSITION)
                .components(Silver, 1, Boron, 1, Fluorine, 4)
                .build()
                .setFormula("AgBF4", true);


        //  11088 Trimethyltin Chloride
        GTQTMaterials.TrimethyltinChloride = new Material.Builder(getMaterialsId(), gregtechId("trimethyltin_chloride"))
                .liquid()
                .color(0x7F776F)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 3, Hydrogen, 6, Tin, 1, Chlorine, 1)
                .build()
                .setFormula("(CH3)3SnCl", true);

        //  11084 Silver Chloride
        GTQTMaterials.SilverChloride = new Material.Builder(getMaterialsId(), gregtechId("silver_chloride"))
                .dust()
                .color(0x8D8D8D)
                .iconSet(METALLIC)
                .components(Silver, 1, Chlorine, 1)
                .build();

        //  13082 Octene
        GTQTMaterials.Octene = new Material.Builder(getMaterialsId(), gregtechId("octene"))
                .liquid()
                .color(0x818022)
                .components(Carbon, 8, Hydrogen, 16)
                .build();

        //  13077 Cyclooctadiene
        GTQTMaterials.Cyclooctadiene = new Material.Builder(getMaterialsId(), gregtechId("cyclooctadiene"))
                .liquid()
                .color(0x40AC40)
                .components(Carbon, 8, Hydrogen, 12)
                .build();

        //  13084 Hexafluoropropylene
        GTQTMaterials.Hexafluoropropylene = new Material.Builder(getMaterialsId(), gregtechId("hexafluoropropylene"))
                .liquid()
                .color(0x141D6F)
                .components(Carbon, 3, Fluorine, 6)
                .build();

        //  11007 Hexachloroplatinic Acid
        GTQTMaterials.HexachloroplatinicAcid = new Material.Builder(getMaterialsId(), gregtechId("hexachloroplatinic_acid"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(0xFEF4D1)
                .components(Hydrogen, 2, Platinum, 1, Chlorine, 6)
                .build();

        //  11089 Potassium Tetrachloroplatinate
        GTQTMaterials.PotassiumTetrachloroplatinate = new Material.Builder(getMaterialsId(), gregtechId("potassium_tetrachloroplatinate"))
                .dust()
                .color(0xF1B04F)
                .iconSet(SHINY)
                .components(Potassium, 2, Platinum, 1, Chlorine, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("K2PtCl4", true);

        //  13080 Hydroxylamine Disulfate
        GTQTMaterials.HydroxylamineDisulfate = new Material.Builder(getMaterialsId(), gregtechId("hydroxylamine_disulfate"))
                .liquid()
                .color(0x91A6D2)
                .components(Nitrogen, 4, Hydrogen, 16, Oxygen, 10, Sulfur, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(NH3OH)2(NH4)2(SO4)2", true);

        //  13120 Hydroxylamine Hydrochloride
        GTQTMaterials.HydroxylamineHydrochloride = new Material.Builder(getMaterialsId(), gregtechId("hydroxylamine_hydrochloride"))
                .liquid()
                .color(0x893E28)
                .components(Hydrogen, 4, Oxygen, 1, Nitrogen, 1, Chlorine, 1)
                .build()
                .setFormula("HONH2HCl", true);

        //  13081 Hydroxylamine
        GTQTMaterials.Hydroxylamine = new Material.Builder(getMaterialsId(), gregtechId("hydroxylamine"))
                .liquid()
                .color(0x91C791)
                .components(Hydrogen, 3, Nitrogen, 1, Oxygen, 1)
                .build()
                .setFormula("H3NO", true);

        //  11091 Ammonium Persulfate
        GTQTMaterials.AmmoniumPersulfate = new Material.Builder(getMaterialsId(), gregtechId("ammonium_persulfate"))
                .liquid()
                .color(0x4242B7)
                .components(Nitrogen, 2, Hydrogen, 8, Sulfur, 2, Oxygen, 8)
                .build()
                .setFormula("(NH4)2S2O8", true);

        //  11187 Lanthanum Fullerene Mixture
        GTQTMaterials.LanthanumFullereneMixture = new Material.Builder(getMaterialsId(), gregtechId("lanthanum_fullerene_mixture"))
                .dust()
                .color(0xD26D8E)
                .iconSet(BRIGHT)
                .build()
                .setFormula("(C60H30)La2", true);

        //  11085 Silver Oxide
        GTQTMaterials.SilverOxide = new Material.Builder(getMaterialsId(), gregtechId("silver_oxide"))
                .dust()
                .color(0xA4A4A4)
                .components(Silver, 2, Oxygen, 1)
                .build();

        //  11087 Tin Chloride
        GTQTMaterials.TinChloride = new Material.Builder(getMaterialsId(), gregtechId("tin_chloride"))
                .dust()
                .liquid()
                .color(0xDBDBDB)
                .iconSet(METALLIC)
                .components(Tin, 1, Chlorine, 2)
                .build();

        //  12025 Magneto Resonatic
        GTQTMaterials.MagnetoResonatic = new Material.Builder(getMaterialsId(), gregtechId("magneto_resonatic"))
                .gem()
                .color(0xFF97FF)
                .iconSet(MAGNETIC)
                .flags(NO_SMELTING, GENERATE_LENS)
                .build();
        MagnetoResonatic.setFormula("(Si₅O₁₀Fe)₃(Bi₂Te₃)₆(ZrO₂)Fe");

        //  12023 Prasiolite
        Prasiolite = new Material.Builder(getMaterialsId(), gregtechId("prasiolite"))
                .gem()
                .color(0x9EB749)
                .iconSet(QUARTZ)
                .flags(CRYSTALLIZABLE, GENERATE_LENS)
                .components(SiliconDioxide, 5, Iron, 1)
                .build();

        //  12024 Bismuth Tellurite
        GTQTMaterials.BismuthTellurite = new Material.Builder(getMaterialsId(), gregtechId("bismuth_tellurite"))
                .dust()
                .color(0x0E8933)
                .iconSet(DULL)
                .components(Bismuth, 2, Tellurium, 3)
                .build();


        //  11188 Lanthanum Embedded Fullerene
        GTQTMaterials.LanthanumEmbeddedFullerene = new Material.Builder(getMaterialsId(), gregtechId("lanthanum_embedded_fullerene"))
                .dust()
                .color(0x84FFAC)
                .iconSet(BRIGHT)
                .build()
                .setFormula("(C60H30)La2", true);

        //  12035 Lanthanum Fullerene Nanotube
        GTQTMaterials.LanthanumFullereneNanotube = new Material.Builder(getMaterialsId(), gregtechId("lanthanum_fullerene_nanotube"))
                .ingot()
                .color(0xD24473)
                .iconSet(BRIGHT)
                .flags(DISABLE_DECOMPOSITION, GENERATE_PLATE, GENERATE_RING, GENERATE_ROUND, GENERATE_ROTOR, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_LONG_ROD, GENERATE_FRAME)
                .build()
                .setFormula("(C60H30)C48La2", true);

        //  11219 Neutronium Nanotube
        GTQTMaterials.NeutroniumNanotube = new Material.Builder(getMaterialsId(), gregtechId("neutronium_nanotube"))
                .ingot()
                .color(0xDDBDCC)
                .iconSet(BRIGHT)
                .flags(DISABLE_DECOMPOSITION, GENERATE_PLATE)
                .components(Carbon, 48, Neutronium, 1)
                .build()
                .setFormula("Nt:CNT");

        GTQTMaterials.Gabbro = new Material.Builder(getMaterialsId(), gregtechId("gabbro"))
                .dust()
                .color(0x5C5C5C).iconSet(ROUGH)
                .components(Labradorite, 5, Bytownite, 3, Olivine, 2, Augite, 1, Biotite, 1)
                .build();

        GTQTMaterials.Gneiss = new Material.Builder(getMaterialsId(), gregtechId("gneiss"))
                .dust()
                .color(0x643631).iconSet(ROUGH)
                .components(Albite, 4, SiliconDioxide, 3, Biotite, 1, Muscovite, 1)
                .build();

        GTQTMaterials.Limestone = new Material.Builder(getMaterialsId(), gregtechId("limestone"))
                .dust()
                .color(0xa9a9a9).iconSet(ROUGH)
                .components(Calcite, 4, Dolomite, 1)
                .build();

        GTQTMaterials.Phyllite = new Material.Builder(getMaterialsId(), gregtechId("phyllite"))
                .dust()
                .color(0x716f71).iconSet(ROUGH)
                .components(Albite, 3, SiliconDioxide, 3, Muscovite, 4)
                .build();

        GTQTMaterials.Shale = new Material.Builder(getMaterialsId(), gregtechId("shale"))
                .dust()
                .color(0x3f2e2f).iconSet(ROUGH)
                .components(Calcite, 6, Clay, 2, SiliconDioxide, 1, Fluorite, 1)
                .build();

        GTQTMaterials.Slate = new Material.Builder(getMaterialsId(), gregtechId("slate"))
                .dust()
                .color(0x756869).iconSet(ROUGH)
                .components(SiliconDioxide, 5, Muscovite, 2, Clinochlore, 2, Albite, 1)
                .build();

        GTQTMaterials.Kimberlite = new Material.Builder(getMaterialsId(), gregtechId("kimberlite"))
                .dust()
                .color(0x201313).iconSet(ROUGH)
                .components(Forsterite, 3, Augite, 3, Andradite, 2, Lizardite, 1)
                .build();

        //铁系泡沫
        GTQTMaterials.IronFront = new Material.Builder(getMaterialsId(), gregtechId("iron_front")).fluid().color(0xD6D6D6).build();
        GTQTMaterials.BandedIronFront = new Material.Builder(getMaterialsId(), gregtechId("banded_iron_front")).fluid().color(0xDAA520).build();
        GTQTMaterials.BrownLimoniteFront = new Material.Builder(getMaterialsId(), gregtechId("brown_limonite_front")).fluid().color(0xD2691E).build();
        GTQTMaterials.YellowLimoniteFront = new Material.Builder(getMaterialsId(), gregtechId("yellow_limonite_front")).fluid().color(0xCDCD00).build();
        GTQTMaterials.ChromiteFront = new Material.Builder(getMaterialsId(), gregtechId("chromite_front")).fluid().color(0x8E8E38).build();
        GTQTMaterials.IlmeniteFront = new Material.Builder(getMaterialsId(), gregtechId("ilmenite_front")).fluid().color(0x8B814C).build();
        GTQTMaterials.MagnetiteFront = new Material.Builder(getMaterialsId(), gregtechId("magnetite_front")).fluid().color(0x8B7500).build();
        GTQTMaterials.PyriteFront = new Material.Builder(getMaterialsId(), gregtechId("pyrite_front")).fluid().color(0x8B5A2B).build();
        GTQTMaterials.TantaliteFront = new Material.Builder(getMaterialsId(), gregtechId("tantalite_front")).fluid().color(0x8DEEEE).build();
        //铜系泡沫
        GTQTMaterials.CopperFront = new Material.Builder(getMaterialsId(), gregtechId("copper_front")).fluid().color(0xD2691E).build();
        GTQTMaterials.TetrahedriteFront = new Material.Builder(getMaterialsId(), gregtechId("tetrahedrite_front")).fluid().color(0x8B2323).build();
        GTQTMaterials.ChalcociteFront = new Material.Builder(getMaterialsId(), gregtechId("chalocite_front")).fluid().color(0x595959).build();
        //铝系
        GTQTMaterials.AluminiumFront = new Material.Builder(getMaterialsId(), gregtechId("aluminium_front")).fluid().color(0x1E90FF).build();
        GTQTMaterials.BauxiteFront = new Material.Builder(getMaterialsId(), gregtechId("bauxite_front")).fluid().color(0x8B4726).build();

        //  13085 Fluorinated Ethylene Propylene
        GTQTMaterials.FluorinatedEthylenePropylene = new Material.Builder(getMaterialsId(), gregtechId("fluorinated_ethylene_propylene"))
                .liquid() // TODO polymer?
                .color(0xC8C8C8)
                .iconSet(DULL)
                .components(Carbon, 5, Fluorine, 10)
                .build();

        //  11248 Lead Zirconate Titanate
        GTQTMaterials.LeadZirconateTitanate = new Material.Builder(getMaterialsId(), gregtechId("lead_zirconate_titanate"))
                .gem(3)
                .color(0x359ADE)
                .iconSet(OPAL)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND)
                .components(Lead, 12, Titanium, 1, Zirconium, 1, Oxygen, 16)
                .build()
                .setFormula("PbZrTiO3", true);

        //  24086 Blood Cells
        GTQTMaterials.BloodCells = new Material.Builder(getMaterialsId(), gregtechId("blood_cells"))
                .liquid()
                .color(0xC43A31)
                .iconSet(DULL)
                .build();

        //  24087 Blood Plasma
        GTQTMaterials.BloodPlasma = new Material.Builder(getMaterialsId(), gregtechId("blood_plasma"))
                .liquid()
                .color(0x882822)
                .build();

        //  24088 bFGF
        GTQTMaterials.BFGF = new Material.Builder(getMaterialsId(), gregtechId("bfgf"))
                .liquid()
                .color(0xA15C72)
                .build()
                .setFormula("bFGF", false);

        //  24089 EGF
        GTQTMaterials.EGF = new Material.Builder(getMaterialsId(), gregtechId("egf"))
                .liquid()
                .color(0x993300)
                .build()
                .setFormula("EGF", false);

        //  24090 CAT
        GTQTMaterials.CAT = new Material.Builder(getMaterialsId(), gregtechId("cat"))
                .liquid()
                .color(0x72B5EA)
                .build()
                .setFormula("CAT", false);

        //  13169 Biotin
        GTQTMaterials.Biotin = new Material.Builder(getMaterialsId(), gregtechId("biotin"))
                .liquid()
                .color(0x08C74A)
                .components(Carbon, 10, Hydrogen, 16, Nitrogen, 2, Oxygen, 3, Sulfur, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13170 Linoleic Acid
        GTQTMaterials.LinoleicAcid = new Material.Builder(getMaterialsId(), gregtechId("linoleic_acid"))
                .liquid()
                .color(0x919C2B)
                .components(Carbon, 18, Hydrogen, 32, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13161 Cyclopentadiene
        GTQTMaterials.Cyclopentadiene = new Material.Builder(getMaterialsId(), gregtechId("cyclopentadiene"))
                .liquid()
                .color(0x8BEB2A)
                .components(Carbon, 5, Hydrogen, 6)
                .build();

        //  13171 Vitamin A
        GTQTMaterials.VitaminA = new Material.Builder(getMaterialsId(), gregtechId("vitamin_a"))
                .liquid()
                .color(0xAB5EC3)
                .components(Carbon, 20, Hydrogen, 30, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13172 β-Ionone
        GTQTMaterials.BetaIonone = new Material.Builder(getMaterialsId(), gregtechId("beta_ionone"))
                .liquid()
                .color(0xC3A0B2)
                .components(Carbon, 13, Hydrogen, 20, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13173 Citral
        GTQTMaterials.Citral = new Material.Builder(getMaterialsId(), gregtechId("citral"))
                .liquid()
                .color(0xE4E77E)
                .components(Carbon, 10, Hydrogen, 16, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13175 Propargyl Chloride
        GTQTMaterials.PropargylChloride = new Material.Builder(getMaterialsId(), gregtechId("propargyl_chloride"))
                .liquid()
                .color(0x156101)
                .components(Carbon, 3, Hydrogen, 3, Chlorine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("HC2CH2Cl", true);

        //  13177 Ethanolamine
        GTQTMaterials.Ethanolamine = new Material.Builder(getMaterialsId(), gregtechId("ethanolamine"))
                .liquid()
                .color(0xD3DEA2)
                .components(Carbon, 2, Hydrogen, 7, Nitrogen, 1, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("HOCH2CH2NH2", true);

        //  13176 Propargyl Alcohol
        GTQTMaterials.PropargylAlcohol = new Material.Builder(getMaterialsId(), gregtechId("propargyl_alcohol"))
                .liquid()
                .color(0xB7AB44)
                .components(Carbon, 3, Hydrogen, 4, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("CHCCH2OH", true);

        //  13168 B27
        GTQTMaterials.B27 = new Material.Builder(getMaterialsId(), gregtechId("b_27"))
                .liquid()
                .color(0xC2B7E3)
                .components(Carbon, 142, Hydrogen, 230, Nitrogen, 36, Oxygen, 44, Sulfur, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11247 Ce:LAG
        GTQTMaterials.CeLAG = new Material.Builder(getMaterialsId(), gregtechId("ce_lag"))
                .gem()
                .color(0x00A816)
                .iconSet(GEM_VERTICAL)
                .flags(CRYSTALLIZABLE, DECOMPOSITION_BY_CENTRIFUGING, GENERATE_LENS)
                .components(CeriumOxide, 1, LutetiumOxide, 1, Alumina, 5)
                .build()
                .setFormula("Ce:LAG", true);

        //  13162 Lithium Cyclopentadienide
        GTQTMaterials.LithiumCyclopentadienide = new Material.Builder(getMaterialsId(), gregtechId("lithium_cyclopentadienide"))
                .liquid()
                .color(0x963D5F)
                .components(Carbon, 5, Hydrogen, 5, Lithium, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13163 Californium Cyclopentadienide
        GTQTMaterials.CaliforniumCyclopentadienide = new Material.Builder(getMaterialsId(), gregtechId("californium_cyclopentadienide"))
                .liquid()
                .color(0x821554)
                .components(Carbon, 15, Hydrogen, 15, Californium, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11236 Californium Trichloride
        GTQTMaterials.CaliforniumTrichloride = new Material.Builder(getMaterialsId(), gregtechId("californium_trichloride"))
                .dust()
                .color(0x8B67D1)
                .iconSet(METALLIC)
                .components(Californium, 1, Chlorine, 3)
                .build();

        //  24064 Free Electron Gas
        GTQTMaterials.FreeElectronGas = new Material.Builder(getMaterialsId(), gregtechId("free_electron_gas"))
                .gas(new FluidBuilder()
                        .translation("gregtech.fluid.generic"))
                .color(0x507BB3)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "a" + TextFormatting.RESET + "§ee" + TextFormatting.OBFUSCATED + "a", false);


        //  15012 Heavy Quark Degenerate Matter
        GTQTMaterials.HeavyQuarkDegenerateMatter = new Material.Builder(getMaterialsId(), gregtechId("heavy_quark_degenerate_matter"))
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

        //  13200 Polyethylene Terephthalate (PET)
        GTQTMaterials.PolyethyleneTerephthalate = new Material.Builder(getMaterialsId(), gregtechId("polyethylene_terephthalate"))
                .polymer()
                .liquid()
                .color(0x1E5C58)
                .components(Carbon, 10, Hydrogen, 6, Oxygen, 4)
                .flags(GENERATE_PLATE, GENERATE_FOIL)
                .build();

        //  13197 Para Toluic Acid
        GTQTMaterials.ParaToluicAcid = new Material.Builder(getMaterialsId(), gregtechId("para_toluic_acid"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(0x4FA597)
                .components(Carbon, 8, Hydrogen, 8, Oxygen, 2)
                .build();

        //  13198 Methylparatoluate
        GTQTMaterials.Methylparatoluate = new Material.Builder(getMaterialsId(), gregtechId("methylparatoluate"))
                .liquid()
                .color(0x76BCB0)
                .components(Carbon, 9, Hydrogen, 10, Oxygen, 2)
                .build();

        //  13199 Dimethyl Terephthalate
        GTQTMaterials.DimethylTerephthalate = new Material.Builder(getMaterialsId(), gregtechId("dimethyl_terephthalate"))
                .liquid()
                .color(0x05D8AF)
                .components(Carbon, 10, Hydrogen, 10, Oxygen, 4)
                .build();

        //  24075 Exotic Mutagen
        ExoticMutagen = new Material.Builder(getMaterialsId(), gregtechId("exotic_mutagen"))
                .liquid(new FluidBuilder().temperature(18406).attributes(FluidAttributes.ACID))
                .color(0x9C31F9)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "aaa", false);

        //  24076 Crude Exotic Gas
        CrudeExoticGas = new Material.Builder(getMaterialsId(), gregtechId("crude_exotic_gas"))
                .gas(new FluidBuilder().temperature(8090))
                .color(0xBEF32C)
                .iconSet(DULL)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "aaa", false);

        //  24077 Cracked Crude Exotic Gas
        CrackedCrudeExoticGas = new Material.Builder(getMaterialsId(), gregtechId("cracked_crude_exotic_gas"))
                .gas(new FluidBuilder().temperature(12390))
                .color(0xEA1798)
                .iconSet(DULL)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "aaa", false);

        //  24078 Naquadic Exotic Gas
        NaquadicExoticGas = new Material.Builder(getMaterialsId(), gregtechId("naquadic_exotic_gas"))
                .gas(new FluidBuilder().temperature(40223))
                .color(0xB01172)
                .iconSet(DULL)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "aaa", false);

        //  24079 Superheavy Exotic Gas
        SuperheavyExoticGas = new Material.Builder(getMaterialsId(), gregtechId("superheavy_exotic_gas"))
                .gas()
                .color(0x33FF99)
                .iconSet(DULL)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "aaa", false);

        //  24080 Heavy Exotic Gas
        HeavyExoticGas = new Material.Builder(getMaterialsId(), gregtechId("heavy_exotic_gas"))
                .gas()
                .color(0x57FFBC)
                .iconSet(DULL)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "aaa", false);

        //  24081 Medium Exotic Gas
        MediumExoticGas = new Material.Builder(getMaterialsId(), gregtechId("medium_exotic_gas"))
                .gas()
                .color(0x1FFFA6)
                .iconSet(DULL)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "aaa", false);

        //  24082 Light Exotic Gas
        LightExoticGas = new Material.Builder(getMaterialsId(), gregtechId("light_exotic_gas"))
                .gas()
                .color(0x62FFC1)
                .iconSet(DULL)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "aaa", false);

        //补 硅岩燃料线
        // 放射性污泥
        GTQTMaterials.RadioactiveSludge = new Material.Builder(getMaterialsId(), gregtechId("radioactive_sludge"))
                .dust()
                .color(0x006400) // 深绿色
                .build();

        // 酸性硅岩乳液
        GTQTMaterials.AcidicNaquadahEmulsion = new Material.Builder(getMaterialsId(), gregtechId("acidic_naquadah_emulsion"))
                .fluid()
                .color(0x90EE90) // 浅绿色
                .build();

        // 硅岩乳液
        GTQTMaterials.NaquadahEmulsion = new Material.Builder(getMaterialsId(), gregtechId("naquadah_emulsion"))
                .fluid()
                .color(0x808080) // 灰色
                .build();

        // 粗硅岩燃料溶液
        GTQTMaterials.CrudeNaquadahFuel = new Material.Builder(getMaterialsId(), gregtechId("crude_naquadah_fuel"))
                .fluid()
                .color(0xA52A2A) // 棕色
                .build();

        // 硅岩沥青
        GTQTMaterials.NaquadahAsphalt = new Material.Builder(getMaterialsId(), gregtechId("naquadah_asphalt"))
                .fluid()
                .color(0x000000) // 黑色
                .build();

        // 硅岩气
        GTQTMaterials.NaquadahGas = new Material.Builder(getMaterialsId(), gregtechId("naquadah_gas"))
                .fluid()
                .color(0x0000FF) // 蓝色
                .build();

        // 极不稳定硅岩粉
        GTQTMaterials.UnstableNaquadahPowder = new Material.Builder(getMaterialsId(), gregtechId("unstable_naquadah_powder"))
                .dust()
                .color(0xFFA500) // 橙色
                .build();

        // 硅岩基流体燃料-MKI
        GTQTMaterials.NaquadahFuelMKI = new Material.Builder(getMaterialsId(), gregtechId("naquadah_fuel_mki"))
                .fluid()
                .color(0xFF0000) // 红色
                .build();

        // 硅岩基流体燃料-MKII
        GTQTMaterials.NaquadahFuelMKII = new Material.Builder(getMaterialsId(), gregtechId("naquadah_fuel_mkii"))
                .fluid()
                .color(0xFF4500) // 橙红色
                .build();

        // 硅岩基流体燃料-MKIII
        GTQTMaterials.NaquadahFuelMKIII = new Material.Builder(getMaterialsId(), gregtechId("naquadah_fuel_mkiii"))
                .fluid()
                .color(0xFFFF00) // 黄色
                .build();

        // 硅岩基流体燃料-MKIV
        GTQTMaterials.NaquadahFuelMKIV = new Material.Builder(getMaterialsId(), gregtechId("naquadah_fuel_mkiv"))
                .fluid()
                .color(0xFFFFE0) // 亮黄色
                .build();

        // 硅岩基流体燃料-MKV
        GTQTMaterials.NaquadahFuelMKV = new Material.Builder(getMaterialsId(), gregtechId("naquadah_fuel_mkv"))
                .fluid()
                .color(0xFFFFFF) // 白色
                .build();

        // 副产物流体定义
        NaquadahResidueMKI = new Material.Builder(getMaterialsId(), gregtechId("naquadah_residue_mki"))
                .fluid()
                .color(0xFFA07A) // 淡棕色
                .build();

        NaquadahResidueMKII = new Material.Builder(getMaterialsId(), gregtechId("naquadah_residue_mkii"))
                .fluid()
                .color(0xFFD700) // 金色
                .build();

        NaquadahResidueMKIII = new Material.Builder(getMaterialsId(), gregtechId("naquadah_residue_mkiii"))
                .fluid()
                .color(0x8B0000) // 暗红色
                .build();

        NaquadahResidueMKIV = new Material.Builder(getMaterialsId(), gregtechId("naquadah_residue_mkiv"))
                .fluid()
                .color(0xFF4500) // 橙红色
                .build();

        //  11260 Uranium-Thorium Carbides
        UraniumThoriumCarbides = new Material.Builder(getMaterialsId(), gregtechId("uranium_thorium_carbides"))
                .dust()
                .color(0x163207)
                .iconSet(DULL)
                .components(Thorium, 12, Uranium235, 1, Carbon, 3)
                .build()
                .setFormula("Th12UC3", true);

        //  11261 Thorium Based Liquid Fuel
        ThoriumBasedLiquidFuel = new Material.Builder(getMaterialsId(), gregtechId("thorium_based_liquid_fuel"))
                .liquid()
                .color(0x503266)
                .iconSet(DULL)
                .components(Thorium, 64, Lithium, 4, Barium, 2, Mercury, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("Th64Li4Ba2Hg", true);

        //  11262 Graphite-Uranium Mixture
        GraphiteUraniumMixture = new Material.Builder(getMaterialsId(), gregtechId("graphite_uranium_mixture"))
                .dust()
                .color(0x3A773D)
                .iconSet(DULL)
                .components(Graphite, 3, Uranium238, 1)
                .build()
                .setFormula("C3U", true);

        //  11263 Uranium Based Liquid Fuel
        UraniumBasedLiquidFuel = new Material.Builder(getMaterialsId(), gregtechId("uranium_based_liquid_fuel"))
                .liquid()
                .color(0x00FF00)
                .iconSet(DULL)
                .components(Uranium238, 36, Potassium, 8, Naquadah, 4, Radon, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("U36K8Nq4Rn", true);

        //  11264 Plutonium Uranium Oxides
        PlutoniumUraniumOxides = new Material.Builder(getMaterialsId(), gregtechId("plutonium_uranium_oxides"))
                .dust()
                .color(0xD11F4A)
                .iconSet(SHINY)
                .components(Plutonium239, 10, Uranium238, 2, Carbon, 8, Oxygen, 12)
                .build()
                .setFormula("Pu10O12U2C8", true);

        //  11265 Plutonium Based Liquid Fuel
        PlutoniumBasedLiquidFuel = new Material.Builder(getMaterialsId(), gregtechId("plutonium_based_liquid_fuel"))
                .liquid()
                .color(0xEF1515)
                .iconSet(DULL)
                .components()
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("Pu64Rb8Cs16Nq+2", true);

        // 注册钍基流体燃料（激发）
        ThoriumFluidFuelExcited = new Material.Builder(getMaterialsId(), gregtechId("thorium_fluid_fuel_excited"))
                .fluid()
                .color(0xFFFF00) // 黄色
                .build();

        // 注册钍基流体燃料（枯竭）
        ThoriumFluidFuelDepleted = new Material.Builder(getMaterialsId(), gregtechId("thorium_fluid_fuel_depleted"))
                .fluid()
                .color(0x8B4513) // 棕色
                .build();

        // 注册铀基流体燃料（激发）
        UraniumFluidFuelExcited = new Material.Builder(getMaterialsId(), gregtechId("uranium_fluid_fuel_excited"))
                .fluid()
                .color(0x00FF00) // 绿色
                .build();

        // 注册铀基流体燃料（枯竭）
        UraniumFluidFuelDepleted = new Material.Builder(getMaterialsId(), gregtechId("uranium_fluid_fuel_depleted"))
                .fluid()
                .color(0x808080) // 灰色
                .build();

        // 注册钚基流体燃料（激发）
        PlutoniumFluidFuelExcited = new Material.Builder(getMaterialsId(), gregtechId("plutonium_fluid_fuel_excited"))
                .fluid()
                .color(0x00FFFF) // 青色
                .build();

        // 注册钚基流体燃料（枯竭）
        PlutoniumFluidFuelDepleted = new Material.Builder(getMaterialsId(), gregtechId("plutonium_fluid_fuel_depleted"))
                .fluid()
                .color(0x8B0000) // 深红色
                .build();

        AcetylsulfanilylChloride = new Material.Builder(getMaterialsId(), gregtechId("acetylsulfanilyl_chloride"))
                .liquid()
                .color(0x00FFFF)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("C8H8ClNO3S", true);

        Sulfanilamide = new Material.Builder(getMaterialsId(), gregtechId("sulfanilamide"))
                .liquid()
                .color(0x523b0a)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("C6H8N2O2S", true);

        AnimalCells = new Material.Builder(getMaterialsId(), gregtechId("animal_cells"))
                .liquid()
                .color(0xc94996)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("???", true);

        GeneTherapyFluid = new Material.Builder(getMaterialsId(), gregtechId("pluripotency_induction_gene_therapy_fluid"))
                .liquid()
                .color(0x6b2f66)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        GenePlasmids = new Material.Builder(getMaterialsId(), gregtechId("pluripotency_induction_gene_plasmids"))
                .liquid()
                .color(0xabe053)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        RapidlyReplicatingAnimalCells = new Material.Builder(getMaterialsId(), gregtechId("rapidly_replicating_animal_cells"))
                .liquid()
                .color(0x7a335e)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula(TextFormatting.OBFUSCATED + "????", true);

        Oct4Gene = new Material.Builder(getMaterialsId(), gregtechId("oct_4_gene"))
                .liquid()
                .color(0x374f0d)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        SOX2Gene = new Material.Builder(getMaterialsId(), gregtechId("sox_2_gene"))
                .liquid()
                .color(0x5d8714)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        KFL4Gene = new Material.Builder(getMaterialsId(), gregtechId("kfl_4_gene"))
                .liquid()
                .color(0x759143)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        Cas9 = new Material.Builder(getMaterialsId(), gregtechId("cas_9"))
                .liquid()
                .color(0x5f6e46)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        MycGene = new Material.Builder(getMaterialsId(), gregtechId("myc_gene"))
                .liquid()
                .color(0x445724)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        Chitin = new Material.Builder(getMaterialsId(), gregtechId("chitin"))
                .liquid()
                .color(0xcbd479)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        Catalase = new Material.Builder(getMaterialsId(), gregtechId("catalase"))
                .liquid()
                .color(0xdb6596)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("?", true);

        B27Supplement = new Material.Builder(getMaterialsId(), gregtechId("b_27_supplement"))
                .liquid()
                .color(0x386939)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("C142H230N36O44S", true);

        CleanAmmoniaSolution = new Material.Builder(getMaterialsId(), gregtechId("clear_ammonia_solution"))
                .liquid()
                .color(0x53c9a0)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(FLUID)
                .build()
                .setFormula("NH3(H2O)", true);

        Glutamine = new Material.Builder(getMaterialsId(), gregtechId("glutamine"))
                .dust()
                .color(0xede9b4)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(DULL)
                .build()
                .setFormula("C5H10N2O3", true);

        GTQTMaterials.TinFront = new Material.Builder(getMaterialsId(), gregtechId("tin_front")).fluid().color(0xFFFFFF).build();
        GTQTMaterials.CassiteriteFront = new Material.Builder(getMaterialsId(), gregtechId("cassiterite_front")).fluid().color(0xFFFFFF).build();
        GTQTMaterials.GoldFront = new Material.Builder(getMaterialsId(), gregtechId("gold_front")).fluid().color(0xEEEE00).build();
        GTQTMaterials.PreciousFront = new Material.Builder(getMaterialsId(), gregtechId("precious_front")).fluid().color(0xCDAD00).build();
        GTQTMaterials.LeanGoldFront = new Material.Builder(getMaterialsId(), gregtechId("lean_gold_front")).fluid().color(0xDAA520).build();
        GTQTMaterials.RichGoldFront = new Material.Builder(getMaterialsId(), gregtechId("rich_gold_front")).fluid().color(0xD2691E).build();
        GTQTMaterials.LeadFront = new Material.Builder(getMaterialsId(), gregtechId("lead_front")).fluid().color(0x9A32CD).build();
        GTQTMaterials.GalenaFront = new Material.Builder(getMaterialsId(), gregtechId("galena_front")).fluid().color(0x9B30FF).build();
        GTQTMaterials.WulfeniteFront = new Material.Builder(getMaterialsId(), gregtechId("wulfenite_front")).fluid().color(0x8B8B00).build();
        GTQTMaterials.CrocoiteFront = new Material.Builder(getMaterialsId(), gregtechId("crocoite_front")).fluid().color(0x8B8B7A).build();
        GTQTMaterials.StibniteFront = new Material.Builder(getMaterialsId(), gregtechId("stibnite_front")).fluid().color(0x303030).build();
        GTQTMaterials.ScheeliteFront = new Material.Builder(getMaterialsId(), gregtechId("scheelite_front")).fluid().color(0xB8860B).build();
        GTQTMaterials.TungstateFront = new Material.Builder(getMaterialsId(), gregtechId("tungstate_front")).fluid().color(0x303030).build();
        GTQTMaterials.UraniniteFront = new Material.Builder(getMaterialsId(), gregtechId("uraninite_front")).fluid().color(0x303030).build();
        GTQTMaterials.PitchblendeFront = new Material.Builder(getMaterialsId(), gregtechId("pitchblende_front")).fluid().color(0xBCEE68).build();

        Diaminotoluene = new Material.Builder(getMaterialsId(), gregtechId("diaminotoluene"))
                .liquid()
                .color(0xEA8204)
                .components(Carbon, 7, Hydrogen, 7, Nitrogen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("C6H3(NH2)2CH3", true);

        Dinitrotoluene = new Material.Builder(getMaterialsId(), gregtechId("dinitrotoluene"))
                .liquid()
                .color(0xEAEFC9)
                .components(Carbon, 7, Hydrogen, 6, Nitrogen, 2, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        Phosgene = new Material.Builder(getMaterialsId(), gregtechId("phosgene"))
                .gas()
                .color(0x48927C)
                .components(Carbon, 1, Oxygen, 1, Chlorine, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        TolueneDiisocyanate = new Material.Builder(getMaterialsId(), gregtechId("toluene_diisocyanate"))
                .liquid()
                .color(0xCCCC66)
                .components(Carbon, 9, Hydrogen, 8, Nitrogen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("CH3C6H3(NCO)2", true);

        Polytetrahydrofuran = new Material.Builder(getMaterialsId(), gregtechId("polytetrahydrofuran"))
                .liquid()
                .color(0x089B3E)
                .components(Carbon, 4, Hydrogen, 10, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(C4H8O)OH2", true);

        TolueneTetramethylDiisocyanate = new Material.Builder(getMaterialsId(), gregtechId("toluene_tetramethyl_diisocyanate"))
                .liquid()
                .color(0xBFBFBF)
                .components(Carbon, 19, Hydrogen, 12, Oxygen, 3, Nitrogen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(CONH)2(C6H4)2CH2(C4O)", true);

        PolytetramethyleneGlycolRubber = new Material.Builder(getMaterialsId(), gregtechId("polytetramethylene_glycol_rubber"))
                .polymer()
                .liquid()
                .color(0xFFFFFF)
                .flags(DISABLE_DECOMPOSITION, GENERATE_PLATE, GENERATE_RING, GENERATE_FOIL)
                .components(Carbon, 23, Hydrogen, 23, Oxygen, 5, Nitrogen, 2)
                .build()
                .setFormula("(CONH)2(C6H4)2CH2(C4O)HO(CH2)4OH", true);

        // 25073 Drilling Mud
        DrillingMud = new Material.Builder(getMaterialsId(), gregtechId("drilling_mud"))
                .liquid()
                .color(0x996600)
                .iconSet(DULL)
                .build();

        // 25074 Used Drilling Mud
        UsedDrillingMud = new Material.Builder(getMaterialsId(), gregtechId("used_drilling_mud"))
                .liquid()
                .color(0x998833)
                .iconSet(DULL)
                .build();

        // 5171 Calcite-Barite Slurry
        CalciteBariteSlurry = new Material.Builder(getMaterialsId(), gregtechId("calcite_barite_slurry"))
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
        AdvancedLubricant = new Material.Builder(getMaterialsId(), gregtechId("advanced_lubricant"))
                .liquid()
                .color(0xAD968F)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 20045 Methoxycresol
        Methoxycreosol = new Material.Builder(getMaterialsId(), gregtechId("methoxycreosol"))
                .liquid()
                .color(0xAF4617)
                .components(Carbon, 8, Hydrogen, 10, Oxygen, 2)
                .build();

        // 20046 Creosol Mixture
        CreosolMixture = new Material.Builder(getMaterialsId(), gregtechId("creosol_mixture"))
                .liquid()
                .color(0x71191C)
                .components(Carbon, 21, Hydrogen, 24, Oxygen, 3)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(C8H10O)(C7H8O)(C6H6O)", true);

        // 20047 Xylenol
        Xylenol = new Material.Builder(getMaterialsId(), gregtechId("xylenol"))
                .liquid()
                .color(0xCF7D10)
                .components(Carbon, 8, Hydrogen, 10, Oxygen, 1)
                .build();

        // 20048 Creosol
        Creosol = new Material.Builder(getMaterialsId(), gregtechId("creosol"))
                .liquid()
                .color(0x704E46)
                .components(Carbon, 7, Hydrogen, 8, Oxygen, 1)
                .build();


        // 20050 Tricresyl Phosphate
        TricresylPhosphate = new Material.Builder(getMaterialsId(), gregtechId("tricresyl_phosphate"))
                .liquid()
                .color(0x704E46)
                .components(Carbon, 21, Hydrogen, 21, Oxygen, 4, Phosphorus, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 20043 Polyethylene Glycol
        PolyethyleneGlycol = new Material.Builder(getMaterialsId(), gregtechId("polyethylene_glycol"))
                .liquid()
                .color(0x5CD813)
                .components(Carbon, 2, Hydrogen, 4, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 5047 Chromium Doped Molybdenite
        ChromiumDopedMolybdenite = new Material.Builder(getMaterialsId(), gregtechId("chromium_doped_molybdenite"))
                .dust()
                .color(0x9C5fB5)
                .iconSet(ROUGH)
                .flags(DISABLE_DECOMPOSITION)
                .components(Chrome, 1, Molybdenite, 1)
                .build()
                .setFormula("Cr:MoS2", true);

        // 20018 Polyurethane Resin
        PolyurethaneResin = new Material.Builder(getMaterialsId(), gregtechId("polyurethane_resin"))
                .liquid()
                .color(0xE6E678)
                .iconSet(DULL)
                .components(Carbon, 17, Hydrogen, 16, Nitrogen, 2, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 20026 Polyurethane Catalyst A
        PolyurethaneCatalystA = new Material.Builder(getMaterialsId(), gregtechId("polyurethane_catalyst_a"))
                .dust()
                .color(0x323232)
                .iconSet(DULL)
                .components(Butanol, 2, PropionicAcid, 1, Tin, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 20027 Nickel Tetracarbonyl
        NickelTetracarbonyl = new Material.Builder(getMaterialsId(), gregtechId("nickel_tetracarbonyl"))
                .liquid()
                .color(0x6158BB)
                .components(Carbon, 4, Nickel, 1, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 20030 Diaminodiphenylmethane Mixture
        DiaminodiphenylmethaneMixture = new Material.Builder(getMaterialsId(), gregtechId("diaminodiphenylmethane_mixture"))
                .liquid()
                .color(0xFFF37A)
                .iconSet(DULL)
                .components(Carbon, 13, Hydrogen, 14, Nitrogen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 20028 Diphenylmethane Diisocyanate Mixture
        DiphenylmethaneDiisocyanateMixture = new Material.Builder(getMaterialsId(), gregtechId("diphenylmethane_diisocyanate_mixture"))
                .liquid()
                .color(0xFFE632)
                .iconSet(DULL)
                .components(Carbon, 15, Hydrogen, 10, Nitrogen, 2, Oxygen, 2, RareEarth, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 20021 Silicon Oil
        SiliconOil = new Material.Builder(getMaterialsId(), gregtechId("silicon_oil"))
                .liquid()
                .color(0x9EA744)
                .iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION)
                .components(EthyleneOxide, 1, Dimethyldichlorosilane, 4, Water, 5)
                .build();

        // 20022 Diphenylmethane Diisocyanate
        DiphenylmethaneDiisocyanate = new Material.Builder(getMaterialsId(), gregtechId("diphenylmethane_diisocyanate"))
                .dust()
                .color(0xFFE632)
                .iconSet(DULL)
                .components(Carbon, 15, Hydrogen, 10, Nitrogen, 2, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 5184 Mercury Dibromide
        MercuryDibromide = new Material.Builder(getMaterialsId(), gregtechId("mercury_dibromide"))
                .dust()
                .color(0xD6E6F4)
                .components(Mercury, 1, Bromine, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 20023 Pentaerythritol
        Pentaerythritol = new Material.Builder(getMaterialsId(), gregtechId("pentaerythritol"))
                .dust()
                .color(0x1A6FAF)
                .iconSet(ROUGH)
                .components(Carbon, 5, Hydrogen, 12, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        // 20016 Crystal Kevlar
        CrystalKevlar = new Material.Builder(getMaterialsId(), gregtechId("crystal_kevlar"))
                .liquid()
                .color(0xF0F078)
                .components(Carbon, 14, Hydrogen, 10, Nitrogen, 2, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("[-CO-C6H4-CO-NH-C6H4-NH-]n", true);

        GTQTMaterials.NaquadahOxideFront = new Material.Builder(getMaterialsId(), gregtechId("naquadahoxide_front")).fluid().color(0xCD853F).build();
        GTQTMaterials.BorniteFront = new Material.Builder(getMaterialsId(), gregtechId("bornite_front")).fluid().color(0xCD853F).build();

        //  11266 Dirty Hexafluorosilicic Acid
        DirtyHexafluorosilicicAcid = new Material.Builder(getMaterialsId(), gregtechId("dirty_hexafluorosilicic_acid"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(Stone.getMaterialRGB() + HydrofluoricAcid.getMaterialRGB())
                .iconSet(DULL)
                .components(Hydrogen, 2, Silicon, 1, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("H2SiF6?", true);

        //  11267 Diluted Hexafluorosilicic Acid
        DilutedHexafluorosilicicAcid = new Material.Builder(getMaterialsId(), gregtechId("diluted_hexafluorosilicic_acid"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(Stone.getMaterialRGB() + HydrofluoricAcid.getMaterialRGB())
                .components(Hydrogen, 6, Oxygen, 2, Silicon, 1, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(H2SiF6)(H2O)2", true);

        //  11268 Dioxygen Difluoride
        DioxygenDifluoride = new Material.Builder(getMaterialsId(), gregtechId("dioxygen_difluoride"))
                .gas()
                .color(Oxygen.getMaterialRGB() + Fluorine.getMaterialRGB())
                .components(Fluorine, 2, Oxygen, 2)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .build();

        //  11269 Diluted Hydrofluoric Acid
        DilutedHydrofluoricAcid = new Material.Builder(getMaterialsId(), gregtechId("diluted_hydrofluoric_acid"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color((HydrofluoricAcid.getMaterialRGB() + Water.getMaterialRGB()) / 3)
                .components(HydrofluoricAcid, 1, Water, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11270 Tritium Hydride
        TritiumHydride = new Material.Builder(getMaterialsId(), gregtechId("tritium_hydride"))
                .gas()
                .color(Tritium.getMaterialRGB() + Hydrogen.getMaterialRGB())
                .components(Tritium, 1, Hydrogen, 1)
                .build();

        //  11271 Helium-3 Hydride
        Helium3Hydride = new Material.Builder(getMaterialsId(), gregtechId("helium_3_hydride"))
                .gas()
                .color(Helium3.getMaterialRGB() + Hydrogen.getMaterialRGB())
                .components(Helium3, 1, Hydrogen, 1)
                .build();

        //  11272 Xenic Acid
        XenicAcid = new Material.Builder(getMaterialsId(), gregtechId("xenic_acid"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(Xenon.getMaterialRGB() + Hydrogen.getMaterialRGB() + Oxygen.getMaterialRGB())
                .components(Hydrogen, 2, Xenon, 1, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  24133 Stone Residue
        StoneResidue = new Material.Builder(getMaterialsId(), gregtechId("stone_residue"))
                .dust()
                .color(Stone.getMaterialRGB() + RedMud.getMaterialRGB())
                .iconSet(ROUGH)
                .build();

        //  24134 Selected Stone Residue
        SelectedStoneResidue = new Material.Builder(getMaterialsId(), gregtechId("selected_stone_residue"))
                .dust()
                .color(Stone.getMaterialRGB() + Naquadah.getMaterialRGB())
                .iconSet(FINE)
                .build();

        //  24135 Partially Oxidized Stone Residue
        PartiallyOxidizedStoneResidue = new Material.Builder(getMaterialsId(), gregtechId("partially_oxidized_stone_residue"))
                .dust()
                .color(Stone.getMaterialRGB() + Oxygen.getMaterialRGB())
                .iconSet(DULL)
                .build();

        //  24136 Oxidized Residual Stone Slurry
        OxidizedResidualStoneSlurry = new Material.Builder(getMaterialsId(), gregtechId("oxidized_residual_stone_slurry"))
                .liquid()
                .color(0x23ad7f)
                .iconSet(DULL)
                .build();

        //  24137 Inert Stone Residue
        InertStoneResidue = new Material.Builder(getMaterialsId(), gregtechId("inert_stone_residue"))
                .dust()
                .iconSet(ROUGH)
                .build();

        //  24138 Oxidized Stone Residue
        OxidizedStoneResidue = new Material.Builder(getMaterialsId(), gregtechId("oxidized_stone_residue"))
                .dust()
                .color(0x9F0000)
                .iconSet(DULL)
                .build();

        //  24139 Heavy Oxidized Stone Residue
        HeavyOxidizedStoneResidue = new Material.Builder(getMaterialsId(), gregtechId("heavy_oxidized_stone_residue"))
                .dust()
                .color(0x770000)
                .iconSet(DULL)
                .build();

        //  24140 Metallic Stone Residue
        MetallicStoneResidue = new Material.Builder(getMaterialsId(), gregtechId("metallic_stone_residue"))
                .dust()
                .color(0x904A59)
                .iconSet(SHINY)
                .build();

        //  24141 Heavy Metallic Stone Residue
        HeavyMetallicStoneResidue = new Material.Builder(getMaterialsId(), gregtechId("heavy_metallic_stone_residue"))
                .dust()
                .color(0x6C2635)
                .iconSet(METALLIC)
                .build();

        //  24142 Diamagnetic Residue
        DiamagneticResidue = new Material.Builder(getMaterialsId(), gregtechId("diamagnetic_residue"))
                .dust()
                .color((Calcium.getMaterialRGB() + Zinc.getMaterialRGB() + Copper.getMaterialRGB() + Gallium.getMaterialRGB() + Beryllium.getMaterialRGB() + Tin.getMaterialRGB()) / 15)
                .iconSet(DULL)
                .build()
                .setFormula("CaZnCuGaBeSn?", true);

        //  24143 Paramagnetic Residue
        ParamagneticResidue = new Material.Builder(getMaterialsId(), gregtechId("paramagnetic_residue"))
                .dust()
                .color((Sodium.getMaterialRGB() + Potassium.getMaterialRGB() + Magnesium.getMaterialRGB() + Titanium.getMaterialRGB() + Vanadium.getMaterialRGB() + Manganese.getMaterialRGB()) / 15)
                .iconSet(DULL)
                .build()
                .setFormula("NaKMgTiVMn?", true);

        //  24144 Ferromagnetic Residue
        FerromagneticResidue = new Material.Builder(getMaterialsId(), gregtechId("ferromagnetic_residue"))
                .dust()
                .color(Iron.getMaterialRGB() + Nickel.getMaterialRGB() + Cobalt.getMaterialRGB())
                .iconSet(DULL)
                .build()
                .setFormula("FeNiCo?", true);

        //  24145 Heavy Diamagnetic Residue
        HeavyDiamagneticResidue = new Material.Builder(getMaterialsId(), gregtechId("heavy_diamagnetic_residue"))
                .dust()
                .color((Lead.getMaterialRGB() + Mercury.getMaterialRGB() + Cadmium.getMaterialRGB() + Indium.getMaterialRGB() + Gold.getMaterialRGB() + Bismuth.getMaterialRGB()) / 15)
                .iconSet(DULL)
                .build()
                .setFormula("PbCdInAuBi?", true);

        //  24146 Heavy Paramagnetic Residue
        HeavyParamagneticResidue = new Material.Builder(getMaterialsId(), gregtechId("heavy_paramagnetic_residue"))
                .dust()
                .color((Thorium.getMaterialRGB() + Thallium.getMaterialRGB() + Uranium235.getMaterialRGB() + Tungsten.getMaterialRGB() + Hafnium.getMaterialRGB() + Tantalum.getMaterialRGB()) / 15)
                .iconSet(DULL)
                .build()
                .setFormula("ThUWHfTaTl", true);

        //  24147 Heavy Ferromagnetic Residue
        HeavyFerromagneticResidue = new Material.Builder(getMaterialsId(), gregtechId("heavy_ferromagnetic_residue"))
                .dust()
                .color(DysprosiumOxide.getMaterialRGB() * 3 / 11)
                .iconSet(DULL)
                .build()
                .setFormula("Dy2O3?", true);

        //  24148 Superheavy Stone Residue
        SuperheavyStoneResidue = new Material.Builder(getMaterialsId(), gregtechId("superheavy_stone_residue"))
                .dust()
                .color(0x59FF7E)
                .iconSet(SHINY)
                .build();

        //  24149 Clean Inert Stone Residue
        CleanInertStoneResidue = new Material.Builder(getMaterialsId(), gregtechId("clean_inert_stone_residue"))
                .dust()
                .color(InertStoneResidue.getMaterialRGB() + FluoroantimonicAcid.getMaterialRGB())
                .iconSet(SHINY)
                .build();

        //  24150 Ultra-acidic Stone Residue Solution
        UltraacidicStoneResidueSolution = new Material.Builder(getMaterialsId(), gregtechId("ultraacidic_stone_residue_solution"))
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(FluoroantimonicAcid.getMaterialRGB() + Helium3Hydride.getMaterialRGB())
                .iconSet(DULL)
                .build();

        //  24151 Dusty Helium-3
        DustyHelium3 = new Material.Builder(getMaterialsId(), gregtechId("dusty_helium_3"))
                .liquid()
                .color(Helium3.getMaterialRGB() + Stone.getMaterialRGB())
                .iconSet(DULL)
                .build()
                .setFormula("He-3?", true);

        //  24152 Taranium-enriched Helium-3
        TaraniumEnrichedHelium3 = new Material.Builder(getMaterialsId(), gregtechId("taranium_enriched_helium_3"))
                .liquid()
                .color(Taranium.getMaterialRGB() + Helium3.getMaterialRGB())
                .iconSet(DULL)
                .build()
                .setFormula("TnHe-3?", true);

        //  24153 Taranium-semidepleted Helium-3
        TaraniumSemidepletedHelium3 = new Material.Builder(getMaterialsId(), gregtechId("taranium_semidepleted_helium_3"))
                .liquid()
                .color(Taranium.getMaterialRGB() + Helium3.getMaterialRGB() - 10)
                .iconSet(DULL)
                .build()
                .setFormula("TnHe-3?", true);

        //  24154 Taranium-depleted Helium-3
        TaraniumDepletedHelium3 = new Material.Builder(getMaterialsId(), gregtechId("taranium_depleted_helium_3"))
                .liquid()
                .plasma()
                .color(Taranium.getMaterialRGB() + Helium3.getMaterialRGB() - 50)
                .iconSet(DULL)
                .build()
                .setFormula("TnHe-3?", true);

        //  24155 Taranium Rich Dusty Helium-3
        TaraniumRichDustyHelium3 = new Material.Builder(getMaterialsId(), gregtechId("taranium_rich_dusty_helium_3"))
                .plasma()
                .color(Taranium.getMaterialRGB() + Helium3.getMaterialRGB())
                .build()
                .setFormula("TnHe-3?", true);

        //  24156 Taranium Rich Helium4
        TaraniumRichHelium4 = new Material.Builder(getMaterialsId(), gregtechId("taranium_rich_helium_4"))
                .liquid()
                .plasma()
                .color(Taranium.getMaterialRGB() + Krypton.getMaterialRGB())
                .build()
                .setFormula("TnHe-4?", true);

        //  24157 Taranium Poor Helium
        TaraniumPoorHelium = new Material.Builder(getMaterialsId(), gregtechId("taranium_poor_helium"))
                .liquid()
                .color(Taranium.getMaterialRGB() + Helium.getMaterialRGB() - 80)
                .build()
                .setFormula("He?", true);

        //  24158 Taranium Poor Helium Mixture
        TaraniumPoorHeliumMixture = new Material.Builder(getMaterialsId(), gregtechId("taranium_poor_helium_mixture"))
                .liquid()
                .color(Taranium.getMaterialRGB() + Helium3.getMaterialRGB() + Helium.getMaterialRGB())
                .build();

        //  24159 Heavy Fluorinated Trinium Solution
        HeavyFluorinatedTriniumSolution = new Material.Builder(getMaterialsId(), gregtechId("heavy_fluorinated_trinium_solution"))
                .liquid()
                .color(0x8C117D)
                .build();

        //  11284 Fluorocarborane
        Fluorocarborane = new Material.Builder(getMaterialsId(), gregtechId("fluorocarborane"))
                .dust()
                .color(0x59B35C)
                .iconSet(BRIGHT)
                .components(Carbon, 1, Hydrogen, 2, Boron, 11, Fluorine, 11)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("HCHB11F11", true);

        //  13201 Perfluorobenzene
        Perfluorobenzene = new Material.Builder(getMaterialsId(), gregtechId("perfluorobenzene"))
                .liquid()
                .color(0x39733B)
                .components(Carbon, 6, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11273 Krypton Difluoride
        KryptonDifluoride = new Material.Builder(getMaterialsId(), gregtechId("krypton_difluoride"))
                .gas()
                .color(Krypton.getMaterialRGB() + Fluorine.getMaterialRGB())
                .components(Krypton, 1, Fluorine, 2)
                .build();

        //  11274 Trinium Tetrafluoride
        TriniumTetrafluoride = new Material.Builder(getMaterialsId(), gregtechId("trinium_tetrafluoride"))
                .dust()
                .color(0xBA16A6)
                .iconSet(DULL)
                .components(Trinium, 1, Fluorine, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11275 Potassium Fluoride
        PotassiumFluoride = new Material.Builder(getMaterialsId(), gregtechId("potassium_fluoride"))
                .dust()
                .color(Potassium.getMaterialRGB() + Fluorine.getMaterialRGB())
                .iconSet(ROUGH)
                .components(Potassium, 1, Fluorine, 1)
                .build();

        //  11276 Caesium Carborane
        CaesiumCarborane = new Material.Builder(getMaterialsId(), gregtechId("caesium_carborane"))
                .dust()
                .color(Caesium.getMaterialRGB() + Carbon.getMaterialRGB())
                .iconSet(DULL)
                .components(Caesium, 1, Carbon, 1, Boron, 11, Hydrogen, 12)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11277 Silver Nitrate
        SilverNitrate = new Material.Builder(getMaterialsId(), gregtechId("silver_nitrate"))
                .dust()
                .color(Silver.getMaterialRGB() + NitricAcid.getMaterialRGB())
                .iconSet(SHINY)
                .components(Silver, 1, Nitrogen, 1, Oxygen, 3)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11278 Caesium Nitrate
        CaesiumNitrate = new Material.Builder(getMaterialsId(), gregtechId("caesium_nitrate"))
                .dust()
                .color(Caesium.getMaterialRGB() + NitricAcid.getMaterialRGB())
                .iconSet(ROUGH)
                .components(Caesium, 1, Nitrogen, 1, Oxygen, 3)
                .flags(DECOMPOSITION_BY_ELECTROLYZING)
                .build();

        //  11279 Silver Iodide
        SilverIodide = new Material.Builder(getMaterialsId(), gregtechId("silver_iodide"))
                .dust()
                .iconSet(SHINY)
                .color(Silver.getMaterialRGB() + Iodine.getMaterialRGB())
                .components(Silver, 1, Iodine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11280 Caesium Carborane Precursor
        CaesiumCarboranePrecursor = new Material.Builder(getMaterialsId(), gregtechId("caesium_carborane_precursor"))
                .dust()
                .color(CaesiumCarborane.getMaterialRGB())
                .iconSet(SAND)
                .components(Caesium, 1, Boron, 10, Hydrogen, 21, Carbon, 4, Nitrogen, 1, Chlorine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("CsB10H12CN(CH3)3Cl", true);

        //  11281 Caesium Hydroxide
        CaesiumHydroxide = new Material.Builder(getMaterialsId(), gregtechId("caesium_hydroxide"))
                .dust()
                .color(Caesium.getMaterialRGB() + Hydrogen.getMaterialRGB() + Oxygen.getMaterialRGB())
                .components(Caesium, 1, Oxygen, 1, Hydrogen, 1)
                .flags(DECOMPOSITION_BY_ELECTROLYZING)
                .build();

        //  11282 Sodium Borohydride
        SodiumBorohydride = new Material.Builder(getMaterialsId(), gregtechId("sodium_borohydride"))
                .dust()
                .color(Sodium.getMaterialRGB() + Boron.getMaterialRGB())
                .iconSet(ROUGH)
                .components(Sodium, 1, Boron, 1, Hydrogen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  11283 Sodium Tetrafluoroborate
        SodiumTetrafluoroborate = new Material.Builder(getMaterialsId(), gregtechId("sodium_tetrafluoroborate"))
                .dust()
                .color(Sodium.getMaterialRGB() + BoronTrifluoride.getMaterialRGB())
                .iconSet(SAND)
                .components(Sodium, 1, Boron, 1, Fluorine, 4)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13182 Trimethylchlorosilane
        Trimethylchlorosilane = new Material.Builder(getMaterialsId(), gregtechId("trimethylchlorosilane"))
                .liquid()
                .color(0x702169)
                .components(Carbon, 3, Hydrogen, 9, Silicon, 1, Chlorine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(CH3)3SiCl", true);

        //  13202 Borane Dimethylsulfide
        BoraneDimethylsulfide = new Material.Builder(getMaterialsId(), gregtechId("borane_dimethylsulfide"))
                .liquid()
                .color(Lead.getMaterialRGB() + Boron.getMaterialRGB())
                .components(Carbon, 2, Hydrogen, 9, Boron, 1, Sulfur, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13203 Decaborane
        Decaborane = new Material.Builder(getMaterialsId(), gregtechId("decaborane"))
                .dust()
                .color(0x4C994F)
                .iconSet(ROUGH)
                .components(Boron, 10, Hydrogen, 14)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13204 Boron Trifluoride Etherate
        BoronTrifluorideEtherate = new Material.Builder(getMaterialsId(), gregtechId("boron_trifluoride_etherate"))
                .liquid()
                .color(0xBF6E6E)
                .components(Boron, 1, Fluorine, 3, Carbon, 4, Hydrogen, 7, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(BF3)(C2H5)2O", true);

        //  13205 Diethyl Ether
        DiethylEther = new Material.Builder(getMaterialsId(), gregtechId("diethyl_ether"))
                .liquid()
                .color(0xFFA4A3)
                .components(Carbon, 4, Hydrogen, 10, Oxygen, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(C2H5)2O", true);

        //  13145 Dimethyl Sulfide
        DimethylSulfide = new Material.Builder(getMaterialsId(), gregtechId("dimethyl_sulfide"))
                .liquid()
                .color(0xFFC66B)
                .components(Carbon, 2, Hydrogen, 6, Sulfur, 1)
                .build()
                .setFormula("(CH3)2S", true);

        //  11218 Sodium Ethoxide
        SodiumEthoxide = new Material.Builder(getMaterialsId(), gregtechId("sodium_ethoxide"))
                .dust()
                .color(Sodium.getMaterialRGB() + Ethanol.getMaterialRGB())
                .iconSet(DULL)
                .components(Carbon, 2, Hydrogen, 5, Oxygen, 1, Sodium, 1)
                .build();

        //  13142 Palladium-Fullerene Matrix
        PalladiumFullereneMatrix = new Material.Builder(getMaterialsId(), gregtechId("palladium_fullerene_matrix"))
                .dust()
                .color(0x40AEE0)
                .iconSet(SHINY)
                .components(Palladium, 1, Carbon, 73, Hydrogen, 15, Nitrogen, 1, Iron, 1)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13143 PCBS
        PCBS = new Material.Builder(getMaterialsId(), gregtechId("pcbs"))
                .liquid()
                .color(0x11B557)
                .components(Carbon, 80, Hydrogen, 21, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        //  13144 Phenylpentanoic Acid
        PhenylpentanoicAcid = new Material.Builder(getMaterialsId(), gregtechId("phenylpentanoic_acid"))
                .liquid()
                .color(0x6F4303)
                .components(Carbon, 11, Hydrogen, 14, Oxygen, 2)
                .build();

        //  13146 Ferrocenylfulleropyrddolidine
        Ferrocenylfulleropyrddolidine = new Material.Builder(getMaterialsId(), gregtechId("ferrocenylfulleropyrddolidine"))
                .liquid()
                .color(0x67AE4C)
                .components(Carbon, 74, Hydrogen, 19, Iron, 1, Nitrogen, 1)
                .build();

        //  13147 Sarcosine
        Sarcosine = new Material.Builder(getMaterialsId(), gregtechId("sarcosine"))
                .dust()
                .color(0x328534)
                .iconSet(SHINY)
                .components(Carbon, 3, Hydrogen, 7, Nitrogen, 1, Oxygen, 2)
                .build();

        //  13148 Glycine
        Glycine = new Material.Builder(getMaterialsId(), gregtechId("glycine"))
                .liquid()
                .color(0x95BA83)
                .components(Carbon, 2, Hydrogen, 5, Nitrogen, 1, Oxygen, 2)
                .build()
                .setFormula("NH2CH2COOH", true);

        //  13149 Ferrocene
        Ferrocene = new Material.Builder(getMaterialsId(), gregtechId("ferrocene"))
                .liquid()
                .color(0x4D3B61)
                .components(Carbon, 10, Hydrogen, 10, Iron, 1)
                .build();

        //  11217 Lithium Iodide
        LithiumIodide = new Material.Builder(getMaterialsId(), gregtechId("lithium_iodide"))
                .dust()
                .color(Lithium.getMaterialRGB() + Iodine.getMaterialRGB())
                .iconSet(DULL)
                .components(Lithium, 1, Iodine, 1)
                .build();

        //氟碳镧铈
        GTQTMaterials.BastnasiteFront = new Material.Builder(getMaterialsId(), gregtechId("bastnasite_front")).fluid().color(0x8B4726).build();


    }
}
