package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;

import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.util.GTUtility.gregtechId;

public class GCYSOrganicChemistryMaterials {

    /**
     * IDs: 15000-17999
     */
    public static void init() {

        Tetrahydrofuran = new Material.Builder(15000, gregtechId("tetrahydrofuran")) //TODO "THF" tooltip
                .fluid()
                .color(0x3234A8)
                .components(Carbon, 4, Hydrogen, 8, Oxygen, 1)
                .build();

        Ethylhexanol = new Material.Builder(15001, gregtechId("ethylhexanol"))
                .fluid()
                .color(0xFEEA9A)
                .components(Carbon, 8, Hydrogen, 10, Oxygen, 1)
                .build();

        DiethylhexylPhosphoricAcid = new Material.Builder(15002, gregtechId("diethylhexyl_phosphoric_acid"))
                .fluid()
                .color(0xFFFF99)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 16, Hydrogen, 35, Oxygen, 4, Phosphorus, 1)
                .build()
                .setFormula("(C8H7O)2PO2H", true);

        Butanol = new Material.Builder(15003, gregtechId("butanol"))
                .fluid()
                .color(0xC7AF2E)
                .components(Carbon, 4, Hydrogen, 10, Oxygen, 1)
                .build()
                .setFormula("C4H9OH", true);

        MethylFormate = new Material.Builder(15004, gregtechId("methyl_formate"))
                .fluid()
                .color(0xFFAAAA)
                .flags(DISABLE_DECOMPOSITION)
                .components(Hydrogen, 4, Carbon, 2, Oxygen, 2)
                .build()
                .setFormula("HCO2CH3", true);

        FormicAcid = new Material.Builder(15005, gregtechId("formic_acid"))
                .color(0xFFAA77).fluid()
                .flags(DISABLE_DECOMPOSITION)
                .components(Hydrogen, 2, Carbon, 1, Oxygen, 2)
                .build()
                .setFormula("HCOOH", true);

        PhthalicAnhydride = new Material.Builder(15006, gregtechId("phthalic_anhydride"))
                .dust()
                .color(0xEEAAEE)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 8, Hydrogen, 4, Oxygen, 3)
                .build()
                .setFormula("C6H4(CO)2O", true);

        Ethylanthraquinone = new Material.Builder(15007, gregtechId("ethylanthraquinone"))
                .fluid()
                .color(0xCC865A)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 16, Hydrogen, 12, Oxygen, 2)
                .build()
                .setFormula("C6H4(CO)2C6H3Et", true);

        Ethylanthrahydroquinone = new Material.Builder(15008, gregtechId("ethylanthrahydroquinone"))
                .fluid()
                .color(0xAD531A)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 16, Hydrogen, 18, Oxygen, 2)
                .build()
                .setFormula("C6H4(CH2OH)2C6H3Et", true);

        Formaldehyde = new Material.Builder(15009, gregtechId("formaldehyde"))
                .fluid()
                .color(0x95A13A)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 1, Hydrogen, 2, Oxygen, 1)
                .build();

        Acetylene = new Material.Builder(15010, gregtechId("acetylene"))
                .fluid()
                .color(0x959C60)
                .components(Carbon, 2, Hydrogen, 2)
                .build();

        Turpentine = new Material.Builder(15013, gregtechId("turpentine"))
                .fluid()
                .color(0x93BD46)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 10, Hydrogen, 16)
                .build();

        Dichloroethane = new Material.Builder(15014, gregtechId("dichloroethane"))
                .fluid()
                .color(0xDAAED3)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 2, Hydrogen, 4, Chlorine, 2)
                .build();

        Trichloroethylene = new Material.Builder(15015, gregtechId("trichloroethylene"))
                .fluid()
                .color(0xB685B1)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 2, Hydrogen, 1, Chlorine, 3)
                .build();

        ChloroaceticAcid = new Material.Builder(15016, gregtechId("chloroacetic_acid"))
                .fluid()
                .color(0x38541A)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(MaterialIconSet.SHINY)
                .components(Carbon, 2, Hydrogen, 3, Chlorine, 1, Oxygen, 2)
                .build();

        MalonicAcid = new Material.Builder(15017, gregtechId("malonic_acid"))
                .dust()
                .color(0x61932E)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(MaterialIconSet.SHINY)
                .components(Carbon, 3, Hydrogen, 4, Oxygen, 4)
                .build();

        TetramethylammoniumChloride = new Material.Builder(15018, gregtechId("tetramethylammonium_chloride"))
                .dust().fluid()
                .color(0x27FF81)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(MaterialIconSet.METALLIC)
                .components(Carbon, 4, Hydrogen, 12, Nitrogen, 1, Chlorine, 1)
                .build()
                .setFormula("N(CH3)4Cl", true);

        Ethylenediamine = new Material.Builder(15019, gregtechId("ethylenediamine"))
                .fluid()
                .color(0xD00ED0)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 2, Hydrogen, 8, Nitrogen, 2)
                .build()
                .setFormula("C2H4(NH2)2", true);

        HydrogenCyanide = new Material.Builder(15020, gregtechId("hydrogen_cyanide"))
                .fluid()
                .color(0xB6D1AE)
                .flags(DISABLE_DECOMPOSITION)
                .components(Hydrogen, 1, Carbon, 1, Nitrogen, 1)
                .build();

        TetrasodiumEDTA = new Material.Builder(15021, gregtechId("tetrasodium_edta"))
                .dust()
                .color(0x8890E0)
                .iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 10, Hydrogen, 12, Nitrogen, 2, Oxygen, 8, Sodium, 4)
                .build();

        EthylenediaminetetraaceticAcid = new Material.Builder(15022, gregtechId("ethylenediaminetetraacetic_acid")) //TODO EDTA Tooltip
                .fluid()
                .color(0x87E6D9)
                .iconSet(MaterialIconSet.ROUGH)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 10, Hydrogen, 16, Nitrogen, 2, Oxygen, 8)
                .build();

        Aniline = new Material.Builder(15026, gregtechId("aniline"))
                .fluid()
                .color(0x4C911D)
                .components(Carbon, 6, Hydrogen, 7, Nitrogen, 1)
                .build()
                .setFormula("C6H5NH2", true);

        ParaXylene = new Material.Builder(15030, gregtechId("para_xylene"))
                .fluid()
                .color(0x666040)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 8, Hydrogen, 10)
                .build()
                .setFormula("C6H4(CH3)2", true);

        Durene = new Material.Builder(15031, gregtechId("durene"))
                .dust()
                .color(0x336040)
                .iconSet(MaterialIconSet.FINE)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 10, Hydrogen, 14)
                .build()
                .setFormula("C6H2(CH3)4", true);

        PyromelliticDianhydride = new Material.Builder(15032, gregtechId("pyromellitic_dianhydride")) //TODO PDMA Tooltip
                .dust()
                .color(0xF0EAD6)
                .iconSet(MaterialIconSet.ROUGH)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 10, Hydrogen, 2, Oxygen, 6)
                .build()
                .setFormula("C6H2(C2O3)2", true);

        Aminophenol = new Material.Builder(15033, gregtechId("aminophenol"))
                .fluid()
                .color(0xFF7F50)
                .components(Carbon, 6, Hydrogen, 7, Nitrogen, 1, Oxygen, 1)
                .build()
                .setFormula("HOC6H4NH2", true);

        Dimethylformamide = new Material.Builder(15034, gregtechId("dimethylformamide")) //TODO DMF Tooltip
                .fluid()
                .color(0x42BDFF)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 3, Hydrogen, 7, Nitrogen, 1, Oxygen, 1)
                .build()
                .setFormula("(CH3)2NC(O)H", true);

        Oxydianiline = new Material.Builder(15035, gregtechId("oxydianiline")) //TODO ODA Tooltip
                .dust()
                .color(0xF0E130)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 12, Hydrogen, 12, Nitrogen, 2, Oxygen, 1)
                .build()
                .setFormula("O(C6H4NH2)2", true);

        KaptonK = new Material.Builder(15036, gregtechId("kapton_k")) //TODO Poly 4,4'-oxydiphenylene-pyromellitimide Tooltip
                .ingot().fluid()
                .color(0xFFCE52)
                .flags(DISABLE_DECOMPOSITION, NO_SMASHING, NO_SMELTING, GENERATE_PLATE, GENERATE_FOIL)
                .components(Carbon, 12, Hydrogen, 12, Nitrogen, 2, Oxygen, 1)
                .build()
                .setFormula("(C7H2N2O4)(O(C6H4)2)", true);

        BiphenylTetracarboxylicAcidDianhydride = new Material.Builder(15037, gregtechId("biphenyl_tetracarboxylic_acid_dianhydride")) //TODO BPDA Tooltip
                .dust()
                .color(0xFF7F50)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 16, Hydrogen, 6, Oxygen, 6)
                .build()
                .setFormula("(C8H3O3)2", true);

        Nitroaniline = new Material.Builder(15038, gregtechId("nitroaniline"))
                .fluid()
                .color(0x2A6E68)
                .components(Carbon, 6, Hydrogen, 6, Nitrogen, 2, Oxygen, 2)
                .build()
                .setFormula("H2NC6H4NO2", true);

        ParaPhenylenediamine = new Material.Builder(15039, gregtechId("para_phenylenediamine"))
                .dust()
                .color(0x4A8E7B)
                .iconSet(MaterialIconSet.ROUGH)
                .components(Carbon, 6, Hydrogen, 8, Nitrogen, 2)
                .build()
                .setFormula("H2NC6H4NH2", true);

        KaptonE = new Material.Builder(15040, gregtechId("kapton_e"))
                .ingot().fluid()
                .color(0xFFDF8C)
                .flags(DISABLE_DECOMPOSITION, GENERATE_PLATE, NO_SMASHING, NO_SMELTING, GENERATE_FOIL)
                .components(Carbon, 12, Hydrogen, 12, Nitrogen, 2, Oxygen, 1)
                .build()
                .setFormula("O(C6H4NH2)2", true);

        Methylamine = new Material.Builder(15041, gregtechId("methylamine"))
                .gas()
                .color(0xAA6600)
                .components(Carbon, 1, Hydrogen, 5, Nitrogen, 1)
                .build()
                .setFormula("CH3NH2", true);

        Trimethylamine = new Material.Builder(15042, gregtechId("trimethylamine"))
                .gas()
                .color(0xBB7700)
                .components(Carbon, 3, Hydrogen, 9, Nitrogen, 1)
                .build()
                .setFormula("(CH3)3N", true);

        Bistrichloromethylbenzene = new Material.Builder(15043, gregtechId("bistrichloromethylbenzene"))
                .fluid()
                .color(0xCF8498)
                .components(Carbon, 8, Hydrogen, 4, Chlorine, 6)
                .build()
                .setFormula("C6H4(CCl3)2", true);

        Tetrabromoethane = new Material.Builder(15044, gregtechId("tetrabromoethane"))
                .fluid()
                .color(0x5AAADA)
                .components(Carbon, 2, Hydrogen, 2, Bromine, 4)
                .build();

        TerephthalicAcid = new Material.Builder(15045, gregtechId("terephthalic_acid")) //TODO "PTA" Tooltip
                .dust()
                .color(0x5ACCDA)
                .iconSet(MaterialIconSet.ROUGH)
                .components(Carbon, 8, Hydrogen, 6, Oxygen, 4)
                .build()
                .setFormula("C6H4(CO2H)2", true);

        TerephthaloylChloride = new Material.Builder(15046, gregtechId("terephthaloyl_chloride"))
                .dust()
                .color(0xFAC4DA)
                .iconSet(MaterialIconSet.SHINY)
                .components(Carbon, 8, Hydrogen, 4, Oxygen, 2, Chlorine, 2)
                .build()
                .setFormula("C6H4(COCl)2", true);

        Butanediol = new Material.Builder(15047, gregtechId("butanediol"))
                .fluid()
                .color(0xAAC4DA)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 4, Hydrogen, 10, Oxygen, 2)
                .build()
                .setFormula("C4H8(OH)2", true);

        GammaButyrolactone = new Material.Builder(15048, gregtechId("gamma_butyrolactone")) //TODO "GBL" tooltip
                .fluid()
                .color(0xAF04D6)
                .components(Carbon, 4, Hydrogen, 6, Oxygen, 2)
                .build();

        NMethylPyrrolidone = new Material.Builder(15049, gregtechId("n_methyl_pyrrolidone"))
                .fluid()
                .color(0xA504D6)
                .components(Carbon, 5, Hydrogen, 9, Nitrogen, 1, Oxygen, 1)
                .build();

        Kevlar = new Material.Builder(15050, gregtechId("kevlar"))
                .polymer()
                .liquid().ingot()
                .color(0xF0F078)
                .flags(DISABLE_DECOMPOSITION, NO_SMASHING, NO_SMELTING, GENERATE_PLATE,
                        GENERATE_FINE_WIRE,GENERATE_RING)
                .components(Carbon, 14, Hydrogen, 10, Nitrogen, 2, Oxygen, 2)
                .fluidPipeProperties(2000, 700, true)
                .build()
                .setFormula("(C6H4)2(CO)2(NH)2", true);

        TetramethylammoniumHydroxide = new Material.Builder(15051, gregtechId("tetramethylammonium_hydroxide")) //TODO "TMAH" tooltip
                .fluid() //this should be a solid, however it will be liquid for circuit etching purposes
                .color(0x40FFD6)
                .flags(DISABLE_DECOMPOSITION)
                .components(Nitrogen, 1, Carbon, 4, Hydrogen, 12, Oxygen, 1, Hydrogen, 1)
                .build()
                .setFormula("N(CH3)4OH", true);

        Pyrocatechol = new Material.Builder(15052, gregtechId("pyrocatechol"))
                .dust()
                .color(0x784421)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(MaterialIconSet.DULL)
                .components(Carbon, 6, Hydrogen, 6, Oxygen, 2)
                .build();

        EthyleneOxide = new Material.Builder(15053, gregtechId("ethylene_oxide"))
                .gas()
                .color(0xDCBFE1)
                .components(Carbon, 2, Hydrogen, 4, Oxygen, 1)
                .build();

        EthyleneGlycol = new Material.Builder(15054, gregtechId("ethylene_glycol"))
                .fluid()
                .color(0x286632)
                .components(Carbon, 2, Hydrogen, 6, Oxygen, 2)
                .build()
                .setFormula("C2H4(OH)2", true);

        Diacetyl = new Material.Builder(15055, gregtechId("diacetyl")) //TODO "Tastes like butter" tooltip
                .fluid()
                .color(0xF7FF65)
                .components(Carbon, 4, Hydrogen, 6, Oxygen, 2)
                .build();

        AcetoneCyanohydrin = new Material.Builder(15056, gregtechId("acetone_cyanohydrin"))
                .fluid()
                .color(0xA1FFD0)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 4, Hydrogen, 7, Nitrogen, 1, Oxygen, 1)
                .build();

        PMMA = new Material.Builder(15057, gregtechId("polymethylmethacrylate")) //TODO PMMA tooltip
                .ingot().fluid()
                .color(0x91CAE1)
                .flags(DISABLE_DECOMPOSITION, NO_SMASHING, NO_SMELTING, GENERATE_PLATE)
                .components(Carbon, 5, Hydrogen, 8, Oxygen, 2)
                .build();

        Trimethylaluminium = new Material.Builder(15058, gregtechId("trimethylaluminium"))
                .fluid()
                .color(0x6ECCFF)
                .flags(DISABLE_DECOMPOSITION)
                .components(Aluminium, 2, Carbon, 6, Hydrogen, 18)
                .build()
                .setFormula("Al2(CH3)6", true);

        Trimethylgallium = new Material.Builder(15059, gregtechId("trimethylgallium"))
                .fluid()
                .color(0x4F92FF)
                .flags(DISABLE_DECOMPOSITION)
                .components(Gallium, 1, Carbon, 3, Hydrogen, 9)
                .build()
                .setFormula("Ga(CH3)3", true);

        EthyleneDibromide = new Material.Builder(15060, gregtechId("ethylene_dibromide")) //TODO "EDB" tooltip
                .fluid()
                .color(0x4F1743)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 2, Hydrogen, 4, Bromine, 2)
                .build();

        GrignardReagent = new Material.Builder(15061, gregtechId("grignard_reagent"))
                .fluid()
                .color(0xA12AA1)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 1, Hydrogen, 3, Magnesium, 1, Bromine, 1)
                .build();

        Dimethylcadmium = new Material.Builder(15062, gregtechId("dimethylcadmium"))
                .fluid()
                .color(0x5C037F)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 2, Hydrogen, 6, Cadmium, 1)
                .build()
                .setFormula("(CH3)2Cd", true);

        DiethylSuflide = new Material.Builder(15063, gregtechId("diethyl_sulfide"))
                .fluid()
                .color(0xFF7E4B)
                .flags(DISABLE_DECOMPOSITION)
                .components(Ethylene, 2, Sulfur, 1)
                .build()
                .setFormula("(C2H5)2S", true);

        Cycloparaphenylene = new Material.Builder(15064, gregtechId("cycloparaphenylene")) //TODO "CPP" tooltip
                .fluid()
                .color(0x60545A)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 6, Hydrogen, 4)
                .build();

        Indene = new Material.Builder(15065, gregtechId("indene"))
                .fluid()
                .color(0x171429)
                .components(Carbon, 9, Hydrogen, 8)
                .build();

        Indanone = new Material.Builder(15066, gregtechId("indanone"))
                .dust()
                .color(0x2E1616).iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 9, Hydrogen, 8, Oxygen, 1)
                .build();

        Truxene = new Material.Builder(15067, gregtechId("truxene"))
                .fluid()
                .color(0x1A3336)
                .components(Carbon, 27, Hydrogen, 18)
                .build();

        Bromomethane = new Material.Builder(15068, gregtechId("bromomethane"))
                .gas()
                .color(0xC82C31)
                .components(Carbon, 1, Hydrogen, 3, Bromine, 1)
                .build();

        BromoBromomethylNaphthalene = new Material.Builder(15069, gregtechId("bromo_bromomethyl_naphthalene"))
                .fluid()
                .color(0x52122E)
                .components(Carbon, 11, Hydrogen, 8, Bromine, 2)
                .build();

        Bromobutane = new Material.Builder(15070, gregtechId("bromobutane"))
                .gas()
                .color(0xE6E8A2)
                .components(Butene, 1, HydrobromicAcid, 1)
                .build()
                .setFormula("C4H9Br", true);

        Butyllithium = new Material.Builder(15071, gregtechId("butyllithium"))
                .fluid()
                .color(0xE683B6B)
                .components(Butene, 1, Hydrogen, 1, Lithium, 1)
                .build()
                .setFormula("C4H9Li", true);

        PalladiumAcetate = new Material.Builder(15072, gregtechId("palladium_acetate"))
                .dust()
                .color(0x693C2D).iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Palladium, 1, AceticAcid, 2)
                .build()
                .setFormula("Pd(CH3COOH)2", true);

        GeodesicPolyarene = new Material.Builder(15073, gregtechId("geodesic_polyarene"))
                .dust()
                .color(0x9E81A8).iconSet(MaterialIconSet.METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 60, Hydrogen, 30)
                .build();

        Edot = new Material.Builder(15074, gregtechId("edot"))
                .fluid()
                .color(0xB1FFD7)
                .components(Carbon, 6, Hydrogen, 6, Oxygen, 2, Sulfur, 1)
                .build();

        Polystyrene = new Material.Builder(15075, gregtechId("polystyrene"))
                .fluid()
                .color(0xE1C2C2)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 8, Hydrogen, 8)
                .build();

        PolystyreneSulfonate = new Material.Builder(15076, gregtechId("polystyrene_sulfonate"))
                .ingot().fluid()
                .color(0xE17C72)
                .flags(DISABLE_DECOMPOSITION, GENERATE_PLATE)
                .components(Carbon, 8, Hydrogen, 8, Sulfur, 1, Oxygen, 3)
                .build();

        PedotPSS = new Material.Builder(15077, gregtechId("pedot_pss"))
                .ingot().fluid()
                .color(0xE165A7)
                .flags(DISABLE_DECOMPOSITION, GENERATE_FINE_WIRE)
                .components(Edot, 1, PolystyreneSulfonate, 1)
                .cableProperties(V[UHV], 24, 0, true)
                .build();

        PedotTMA = new Material.Builder(15078, gregtechId("pedot_tma"))
                .ingot().fluid()
                .color(0x5E9EE1)
                .flags(DISABLE_DECOMPOSITION,GENERATE_ROUND,GENERATE_FINE_WIRE, GENERATE_FRAME, GENERATE_ROTOR, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW, GENERATE_PLATE, GENERATE_SPRING_SMALL, GENERATE_SPRING, GENERATE_RING)
                .components(Edot, 1, PMMA, 2)
                .cableProperties(V[UEV], 8, 6)
                .build();
    }
}
