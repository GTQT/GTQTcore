package keqing.gtqtcore.api.unification.materials1;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.material.properties.*;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.api.unification.TJMaterials;


import static gregicality.multiblocks.api.unification.GCYMMaterials.TantalumCarbide;
import static gregicality.science.api.unification.materials.GCYSMaterials.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.DULL;
import static gregtech.api.unification.material.info.MaterialIconSet.SHINY;
import static gregtech.api.util.GTUtility.gregtechId;
import static keqing.gtqtcore.api.unification.TJMaterials.*;


public class TJFirstDegreeMaterials {

    // static material arrays to be used in commonProxy



    public static void registerMaterials(){
        Birmabright = new Material.Builder(25000, gregtechId("birmabright"))
                .ingot()
                .fluid()
                .blast(1100)
                .color(1755371).iconSet(MaterialIconSet.DULL)
                .flags(setMaterialFlags(STANDARDPLATE, STANDARDROD, STANDARDROTOR, STANDARDGEAR, STANDARDSPRING, STANDARDCASING))
                .components(Aluminium, 5, Magnesium, 1, Manganese, 1)
                .build();

        BT6 = new Material.Builder(25001, gregtechId("bt_6"))
                .ingot()
                .fluid()
                .colorAverage().iconSet(MaterialIconSet.SHINY)
                .flags(setMaterialFlags(STANDARDPLATE, STANDARDROD, STANDARDROTOR, STANDARDGEAR, STANDARDSPRING))
                .components(Iron, 3, Carbon, 1, Vanadium, 5, Titanium, 40, Aluminium, 6)
                .blast(3400)
                .build();

        TriphenylPhosphine  = new Material.Builder(25002, gregtechId("triphenylphosphine"))
                .dust()
                .fluid()
                .colorAverage()
                .components(Phosphorus, 1, Carbon, 18, Hydrogen, 15)
                .build()
                .setFormula("P(C6H5)3", true);

        Difluorobenzophenone = new Material.Builder(25003, gregtechId("difluorobenzophenone"))
                .dust()
                .color(0xC44DA5)
                .iconSet(SHINY)
                .components(Carbon, 13, Hydrogen, 8, Oxygen, 1, Fluorine, 2)
                .build()
                .setFormula("(FC6H4)2CO", true);

        MolybdenumSulfide = new Material.Builder(25004, gregtechId("molybdenumsulfide"))
                .fluid()
                .colorAverage()
                .components(Molybdenum, 1, Sulfur, 2)
                .build()
                .setFormula("MoS2", true);

        PhenylmagnesiumBromide = new Material.Builder(25005, gregtechId("phenylmagnesiumbromide"))
                .fluid()
                .colorAverage()
                .components(Carbon, 6, Hydrogen, 5, Magnesium, 1, Bromine, 1)
                .build()
                .setFormula("C6H5MgBr", true);

        Bromobenzene = new Material.Builder(25006, gregtechId("bromobenzene"))
                .fluid()
                .colorAverage()
                .components(Carbon, 6, Hydrogen, 4, Bromine, 1)
                .build()
                .setFormula("C6H5Br",true);

        Draconium = new Material.Builder(25007, gregtechId("draconium"))
                .ingot().fluid()
                .color(0x573d85).iconSet(MaterialIconSet.DULL)
                .flags(setMaterialFlags(STANDARDPLATE, STANDARDCASING))
                .build();

        SilicaCeramic = new Material.Builder(25008, gregtechId("silicaceramic"))
                .ingot()
                .blast(1000)
                .color(0x8c7a50).iconSet(MaterialIconSet.SHINY)
                .flags(setMaterialFlags(STANDARDPLATE,STANDARDROD,STANDARDFOIL,STANDARDROUND))
                .build();

        NickelPlatedTin = new Material.Builder(25009, gregtechId("nickelplatedtin"))
                .ingot()
                .color(0x8fb7c4).iconSet(MaterialIconSet.SHINY)
                .build();

        //TODO: CARBON make the formula for ladder poly-p-phenylene
        Ladder_Poly_P_Phenylene = new Material.Builder(25011, gregtechId("polypphenylene"))
                .fluid()
                .ingot()
                .color(0xbfb393)
                .flags(setMaterialFlags(STANDARDWIREFINE,STANDARDFOIL))
                .build();

        HydrogenSilsesquioxane = new Material.Builder(25012, gregtechId("hydrogensilsesquioxane"))
                .fluid()
                .color(0x471525)
                .build()
                .setFormula("[HSiO3/2]", true);

        //TODO: CARBON make the formula for SU-8 Photoresist
        SU8_Photoresist = new Material.Builder(25013, gregtechId("su_photoresist"))
                .fluid()
                .color(0x0e242b)
                .build();
        Fiberglass = new Material.Builder(25014, gregtechId("fiberglass"))
                .ingot()
                .color(0x99c0cf)
                .build();

        LuminescentSiliconNanocrystals = new Material.Builder(25015, gregtechId("luminescentsiliconnanocrystals"))
                .dust()
                .iconSet(MaterialIconSet.SHINY)
                .color(0x363636)
                .build();

        SeleniumMonobromide = new Material.Builder(25016, gregtechId("seleniummonobromide"))
                .fluid()
                .color(0x472a1a)
                .build();

        //  25038 Hydroquinone
        Hydroquinone = new Material.Builder(25017, gregtechId("hydroquinone"))
                .fluid()
                .color(0x83251A)
                .components(Carbon, 6, Hydrogen, 6, Oxygen, 2)
                .build()
                .setFormula("C6H4(OH)2", true);

        Fluorobenzene = new Material.Builder(25018, gregtechId("fluorobenzene"))
                .fluid()
                .color(0x7CCA88)
                .components(Carbon, 6, Hydrogen, 5, Fluorine, 1)
                .build();

        Starlight = new Material.Builder(25019, gregtechId("starlight"))
                .fluid()
                .color(0xebfafc)
                .iconSet(MaterialIconSet.SHINY)
                .build();

        SilverLeadOxide = new Material.Builder(25020, gregtechId("silverleadoxide"))
                .dust()
                .colorAverage()
                .components(Silver, 1, Lead, 1, Oxygen, 1)
                .iconSet(MaterialIconSet.SHINY)
                .build();

        PalladiumChloride = new Material.Builder(25021, gregtechId("palladiumchloride"))
                .dust()
                .colorAverage()
                .components(Palladium, 1, Chlorine, 2)
                .build()
                .setFormula("PdCl2",true);

        TetrakisPDCatalyst = new Material.Builder(25022, gregtechId("tretrakispdcatalyst"))
                .dust()
                .color(0x9bd1e8)
                .iconSet(MaterialIconSet.SHINY)
                .build();

        HydraziniumChloride = new Material.Builder(25023, gregtechId("hydraziniumchloride"))
                .fluid()
                .colorAverage()
                .components(Nitrogen, 2, Hydrogen, 5, Chlorine, 1)
                .build()
                .setFormula("N2H4HCl", true);

        DibromoisophthalicAcid = new Material.Builder(25024, gregtechId("dibromoisophthalicacid"))
                .fluid()
                .colorAverage()
                .components(Carbon, 8, Hydrogen, 5, Bromine, 1, Oxygen, 4)
                .build()
                .setFormula("C8H5BrO4",true);

        Dibromoterephthaloyldichloride = new Material.Builder(25025, gregtechId("dibromoterephthaloyldichloride"))
                .fluid()
                .colorAverage()
                .components(Carbon, 8, Hydrogen, 2, Bromine, 2, Chlorine, 2, Oxygen, 2)
                .build()
                .setFormula("C8H2Br2Cl2O2",true);

        P1Solution = new Material.Builder(25026, gregtechId("p_one_solution"))
                .fluid()
                .color(0x6b0c05)
                .build();

        HafniumSilicate = new Material.Builder(25027, gregtechId("hafnium_silicate"))
                .dust()
                .colorAverage()
                .components(Hafnium, 1, Oxygen, 4, Silicon, 1)
                .build()
                .setFormula("HfO4Si", true);

        Cobalt60 = new Material.Builder(25028, gregtechId("cobalt_sixty"))
                .dust()
                .iconSet(MaterialIconSet.SHINY)
                .color(Cobalt.getMaterialRGB())
                .element(Co60)
                .build()
                .setFormula("Co-60", false);

        Fluorotoluene = new Material.Builder(25029, gregtechId("fluorotoluene"))
                .fluid()
                .color(0x6EC5B8)
                .components(Carbon, 7, Hydrogen, 7, Fluorine, 1)
                .build();

        Resorcinol = new Material.Builder(25030, gregtechId("resorcinol"))
                .fluid()
                .color(0x9DA38D)
                .components(Carbon, 6, Hydrogen, 6, Oxygen, 2)
                .build();

        ZnFeAlClCatalyst = new Material.Builder(25031, gregtechId("zn_fe_al_cl_catalyst"))
                .dust()
                .color(0xC522A9)
                .iconSet(DULL)
                .components(Zinc, 1, Iron, 1, Aluminium, 1, Chlorine, 1)
                .build();

        NitrileButadieneRubber = new Material.Builder(25032, gregtechId("nitrile_butadiene_rubber"))
                .fluid()
                .color(0x211A18)
                .flags(DISABLE_DECOMPOSITION, GENERATE_PLATE, GENERATE_RING)
                .components(Carbon, 7, Hydrogen, 9, Nitrogen, 1)
                .build();

        PolyPhosphonitrileFluoroRubber = new Material.Builder(25033, gregtechId("poly_phosphonitrile_fluoro_rubber"))
                .fluid()
                .color(0x372B28)
                .flags(DISABLE_DECOMPOSITION, GENERATE_PLATE, GENERATE_RING)
                .components(Carbon, 24, Hydrogen, 16, Oxygen, 8, Nitrogen, 4, Phosphorus, 4, Fluorine, 40)
                .build();

        Silane = new Material.Builder(25045, gregtechId("silane"))
                .fluid()
                .colorAverage()
                .components(Silicon, 1, Hydrogen, 4)
                .build()
                .setFormula("SiH4", true);

        ArgonSilane = new Material.Builder(25044, gregtechId("argon_silane"))
                .gas().plasma()
                .components(Argon, 1, Silane, 1)
                .color(0x24BB18)
                .build();


        CarbonNanotubePolymer = new Material.Builder(25046, gregtechId("carbon_nanotube_polymer"))
                .dust()
                .ingot()
                .color(0x0d0d0d)
                .iconSet(MaterialIconSet.SHINY)
                .build();


        NihoniumTriiodide = new Material.Builder(25048, gregtechId("nihonium_triiodide"))
                .dust()
                .ingot()
                .color(0x5986a8)
                .iconSet(MaterialIconSet.SHINY)
                .build()
                .setFormula("Nh-I3", false);


        Gluons = new Material.Builder(25051, gregtechId("gluons"))
                .fluid()
                .color(0xffffff)
                .build();

        LightQuarks = new Material.Builder(25052, gregtechId("light_quarks"))
                .fluid()
                .color(0x59ff7d)
                .build();

        HeavyQuarks = new Material.Builder(25053, gregtechId("heavy_quarks"))
                .fluid()
                .color(0x4a080b)
                .build();

        BismuthTelluride = new Material.Builder(25054, gregtechId("bismuth_telluride"))
                .ingot()
                .colorAverage()
                .components(Bismuth, 2, Tellurium, 3)
                .build();

        SynthDiamond = new Material.Builder(25055, gregtechId("synthetic_diamond"))
                .dust()
                .gem()
                .color(0x8fbaff)
                .components(Carbon, 8)
                .build();

        GraphenePQD = new Material.Builder(25056, gregtechId("photoluminescent_graphene_quantum_dots"))
                .dust()
                .color(0x616161)
                .iconSet(MaterialIconSet.SHINY)
                .components(Carbon, 6)
                .build();

        HeavyQuarkDegenerate = new Material.Builder(25057, gregtechId("heavy_quark_degenerate_matter"))
                .fluid()
                .ingot()
                .color(0x171717)
                .components(Gluons, 1, HeavyQuarks, 4, LightQuarks, 1)
                .build();

        BismuthIridiumOxide = new Material.Builder(25058, gregtechId("bismuth_iridium_oxide"))
                .ingot()
                .colorAverage()
                .components(Bismuth, 2, Iridium, 2, Oxygen, 7)
                .iconSet(MaterialIconSet.DULL)
                .build();

        IndiumFluoride = new Material.Builder(25059, gregtechId("indium_fluoride"))
                .fluid()
                .color(0x2d5c53)
                .components(Indium, 1, Fluorine, 3)
                .build();

        EnrichedNaqAlloy = new Material.Builder(25060, gregtechId("enriched_naquadah_alloy"))
                .ingot()
                .fluid()
                .colorAverage()
                .components(NaquadahEnriched, 16, Einsteinium, 4, Rhodium, 4, Technetium, 4, Astatine, 2, Erbium, 2)
                .blast(9700)
                .iconSet(MaterialIconSet.SHINY)
                .build();

        SodiumPotassiumNiobate = new Material.Builder(25061, gregtechId("sodium_potassium_niobate"))
                .ingot()
                .colorAverage()
                .components(Sodium, 1, Potassium, 1, Niobium, 2, Oxygen, 6)
                .blast(3600)
                .build();

        TriniumSteel = new Material.Builder(25062, gregtechId("trinium_steel"))
                .ingot()
                .iconSet(MaterialIconSet.SHINY)
                .blast(10200)
                .colorAverage()
                .components(Trinium, 18, Tungsten, 6, Vanadium, 4, Chrome, 2, Tantalum, 1, Cobalt, 1)
                .build();

        LeadZirconateTitanate = new Material.Builder(25063, gregtechId("lead_zirconate_titanate"))
                .ingot()
                .iconSet(MaterialIconSet.DULL)
                .blast(6100)
                .color(0x355232)
                .components(Lead, 12, Titanium, 1, Zirconium, 1, Oxygen, 16)
                .build();

        XenonFluorideSupercondiveMix = new Material.Builder(25064, gregtechId("xenon_fluoride_mix"))
                .fluid()
                .color(0x251a33)
                .build();


        XenonOxyTetraFluoride = new Material.Builder(25065, gregtechId("xenon_oxytetrafluoride"))
                .fluid()
                .colorAverage()
                .components(Xenon, 1, Oxygen, 1, Fluorine, 4)
                .build();

        XenonDioxide = new Material.Builder(25066, gregtechId("xenon_dioxide"))
                .fluid()
                .colorAverage()
                .components(Xenon, 1, Oxygen, 2)
                .build();

        XenonTetraFluoride = new Material.Builder(25067, gregtechId("xenon_tetrafluoride"))
                .fluid()
                .color(0x3d1e42)
                .components(Xenon, 1, Fluorine, 4)
                .build();
        XenonHexaFluoride = new Material.Builder(25068, gregtechId("xenon_hexafluoride"))
                .fluid()
                .color(0x8d5cff)
                .components(Xenon, 1, Fluorine, 6)
                .build();

        TerfenolD_H = new Material.Builder(25069, gregtechId("terfenol_d_heavy"))
                .ingot()
                .blast(10200)
                .components(Osmium, 12, Iron, 6, Dysprosium, 2, Terbium, 1)
                .color(0x4d4d4d)
                .build();

        TerfenolD_L = new Material.Builder(25070, gregtechId("terfenol_d_light"))
                .ingot()
                .blast(10200)
                .color(0x9c9c9c)
                .build();

        SuspendedPGQD = new Material.Builder(25071, gregtechId("suspended_pgqd"))
                .fluid()
                .color(0x65ad95)
                .components(Krypton, 1, GraphenePQD, 1)
                .build();

        Leptons = new Material.Builder(25072, gregtechId("leptons"))
                .fluid()
                .color(0x5500ff)
                .build();

        NeonFluoride = new Material.Builder(25073, gregtechId("neon_fluoride"))
                .fluid()
                .colorAverage()
                .components(Neon, 1, Fluorine, 1)
                .build();

        ExcitedNeonFluoride = new Material.Builder(25074, gregtechId("excited_neon_fluoride"))
                .fluid()
                .colorAverage()
                .color(NeonFluoride.getMaterialRGB())
                .components(NeonFluoride, 1)
                .build();

        ArgonFluorine = new Material.Builder(25075, gregtechId("argon_fluorine"))
                .fluid()
                .color(0x00ff88)
                .components(Argon, 1, Fluorine, 1)
                .build();

        SilverGalliumSelenide = new Material.Builder(25076, gregtechId("silver_gallium_selenide"))
                .dust()
                .colorAverage()
                .components(Silver, 1, Gallium, 1, Selenium, 2)
                .build();

        BismuthPhosphomolybdate = new Material.Builder(25077, gregtechId("bismuth_phosphomolybdate"))
                .dust()
                .colorAverage()
                .components(Bismuth, 9, Phosphorus, 1, Molybdenum, 12, Oxygen, 52)
                .build();

        Acrylonitrile = new Material.Builder(25078, gregtechId("acrylonitrile"))
                .fluid()
                .color(0x565734)
                .components(Carbon, 3, Hydrogen, 3, Nitrogen, 1)
                .build()
                .setFormula("CH2CHCN", true);

        SodiumThiocyanate = new Material.Builder(25079, gregtechId("sodium_thiocyanate"))
                .dust()
                .colorAverage()
                .build();

        SodiumThiocyanatePolymerizationSolution = new Material.Builder(25080, gregtechId("sodiumthiocyanatepolymerizationsolution"))
                .fluid()
                .colorAverage()
                .components(Water, 1, SodiumThiocyanate, 1)
                .build();

        Polyacrylonitrile = new Material.Builder(25081, gregtechId("polyacrylonitrile"))
                .dust()
                .color(0x854218)
                .build();

        CFCoatingSolution = new Material.Builder(25082, gregtechId("cf_coating_solution"))
                .fluid()
                .colorAverage()
                .components(PolyvinylChloride, 1, Polyethylene, 1)
                .build();

        GalvanizedSteel = new Material.Builder(25083, gregtechId("galvanized_steel"))
                .ingot()
                .color(0xb5b5b5)
                .components(Iron, 9, Zinc, 1)
                .iconSet(MaterialIconSet.SHINY)
                .arcSmeltInto(Steel)
                .flags(DISABLE_DECOMPOSITION, NO_WORKING, NO_SMASHING, NO_SMELTING)
                .build();

        Polyetheretherketone = new Material.Builder(25086, gregtechId("peek"))
                .ingot()
                .fluid()
                .iconSet(MaterialIconSet.DULL)
                .color(0x2b2b2b)
                .build()
                .setFormula("C20H12O3", true);

        ProgrammableMatter = new Material.Builder(25087, gregtechId("programmable_matter"))
                .ingot()
                .fluid()
                .color(0x8196a3)
                .iconSet(MaterialIconSet.SHINY)
                .build()
                .setFormula("robots!", false);

        LutetiumTantalate = new Material.Builder(25088, gregtechId("lutetium_tantalite"))
                .ingot()
                .iconSet(MaterialIconSet.SHINY)
                .components(Lutetium, 1, Tantalum, 1, Oxygen, 4)
                .color(0xaccde6)
                .build();

        Iridrhodruthenium = new Material.Builder(25089, gregtechId("iridrhodruthenium"))
                .ingot()
                .iconSet(MaterialIconSet.SHINY)
                .components(Ruthenium, 8, Rhodium, 1, Iridium, 1)
                .colorAverage()
                .build();

        HEA_1 = new Material.Builder(25090, gregtechId("high_entropy_alloy_1"))
                .ingot()
                .iconSet(MaterialIconSet.METALLIC)
                .components(Chrome, 5, Niobium, 8, Silicon, 7, Titanium, 3, Zirconium, 5)
                .colorAverage()
                .build();

        HEA_2 = new Material.Builder(25091, gregtechId("high_entropy_alloy_2"))
                .ingot()
                .fluid()
                .iconSet(MaterialIconSet.METALLIC)
                .components(Zirconium, 6, Tungsten, 4, Vanadium, 5, Cobalt, 3, Manganese, 4)
                .colorAverage()
                .build();

        HEA_3 = new Material.Builder(25092, gregtechId("high_entropy_alloy_3"))
                .ingot()
                .fluid()
                .iconSet(MaterialIconSet.SHINY)
                .components(Aluminium, 5, Chrome, 5, Molybdenum, 7, Tantalum, 9, Titanium, 6, Zirconium, 4, Nitrogen, 21)
                .colorAverage()
                .build();

        HDCS_1 = new Material.Builder(25093, gregtechId("high_durability_compound_steel_1"))
                .ingot()
                .iconSet(MaterialIconSet.SHINY)
                .color(0x2e216e)
                .build();

        HDCS_2 = new Material.Builder(25094, gregtechId("high_durability_compound_steel_2"))
                .ingot()
                .iconSet(MaterialIconSet.SHINY)
                .color(0x3d0b0e)
                .build();

        HDCS_3 = new Material.Builder(25095, gregtechId("high_durability_compound_steel_3"))
                .ingot()
                .iconSet(MaterialIconSet.SHINY)
                .color(0x1f0126)
                .build();

        Pikyonium = new Material.Builder(25096, gregtechId("pikyonium"))
                .ingot()
                .iconSet(MaterialIconSet.SHINY)
                .color(0x3770bf)
                .components(HSSE, 12, Seaborgium, 7, Lead, 5, Molybdenum, 5, Beryllium, 3, Gallium, 3, Mercury, 2)
                .build();

        HafniumCarbide = new Material.Builder(25097, gregtechId("hafnium_carbide"))
                .dust()
                .iconSet(MaterialIconSet.METALLIC)
                .colorAverage()
                .components(Hafnium, 1, Carbon, 1)
                .build();

        SeaborgiumCarbide = new Material.Builder(25099, gregtechId("seaborgium_carbide"))
                .dust()
                .iconSet(MaterialIconSet.METALLIC)
                .colorAverage()
                .components(Seaborgium, 1, Carbon, 1)
                .build();

        TantalumHafniumSeaborgiumCarbide = new Material.Builder(25100, gregtechId("tantalum_hafnium_seaborgium_carbide"))
                .ingot()
                .iconSet(MaterialIconSet.SHINY)
                .colorAverage()
                .build();

        TantalumHafniumSeaborgiumCarboNitride = new Material.Builder(25101, gregtechId("tantalum_hafnium_seaborgium_carbonitide"))
                .ingot()
                .iconSet(MaterialIconSet.SHINY)
                .color(0x1c1c1c)
                .components(TantalumHafniumSeaborgiumCarbide, 1, Nitrogen, 1)
                .build()
                .setFormula("4TaHf3Sg10C7N");


        Methyltrichlorosilane = new Material.Builder(25103, gregtechId("methyltrichlorosilane"))
                .fluid()
                .colorAverage()
                .components(Carbon, 1, Hydrogen, 3, Chlorine, 3, Silicon, 1)
                .build()
                .setFormula("CH3Cl3Si", true);

        Methyltrimethoxysilane = new Material.Builder(25104, gregtechId("methyltrimethyoxysilane"))
                .fluid()
                .color(0x42163c)
                .components(Silicon, 1, Oxygen, 3, Carbon, 4, Hydrogen, 12)
                .build()
                .setFormula("CH3Si(OCH3)3", true);

        Polymethylsilesquioxane = new Material.Builder(25105, gregtechId("polymethylsilesquioxane"))
                .fluid()
                .color(0xff7ab4)
                .components(Silicon, 1, Oxygen, 3, Carbon, 4, Hydrogen, 12)
                .build()
                .setFormula("[CH3Si(OCH3)3]N", true);

        Cellulose = new Material.Builder(25106, gregtechId("cellulose"))
                .dust()
                .color(0xd9d9d9)
                .components(Carbon, 6, Hydrogen, 10, Oxygen, 5)
                .build();

        IridiumOnCubicZirconia = new Material.Builder(25107, gregtechId("iridiumoncubiczirconia"))
                .dust()
                .colorAverage()
                .build();

        OnePropanol = new Material.Builder(25108, gregtechId("onepropanol"))
                .fluid()
                .color(0xbad17b)
                .components(Carbon, 3, Hydrogen, 8, Oxygen, 1)
                .build()
                .setFormula("CH3CH2CH2OH", true);

        ZSM_5_ZEOLITE = new Material.Builder(25109, gregtechId("zsm_five_zeolite"))
                .dust()
                .color(0xb8ffe1)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(Na13Al13Si83O192)16H2O", true);

        SodiumHydroxideSilica = new Material.Builder(25110, gregtechId("sodiumhydroxidesilica"))
                .fluid()
                .color(0x213996)
                .iconSet(MaterialIconSet.FLUID)
                .components(SodiumHydroxide, 1, SiliconDioxide, 1, Water, 1)
                .build();

        SodiumAluminate = new Material.Builder(25111, gregtechId("sodiumaluminate"))
                .dust()
                .colorAverage()
                .components(Sodium, 1, Aluminium, 1, Oxygen, 2)
                .build();

        SodiumAluminumSilicaSolution = new Material.Builder(25112, gregtechId("aluminumsilicasolution"))
                .fluid()
                .color(0xb0c1ff)
                .components(Sodium, 1, Aluminium, 1, Silicon, 1, Oxygen, 2, Water, 1)
                .build();

        AluminoSilicateGlass = new Material.Builder(25113, gregtechId("aluminosilicateglass"))
                .ingot()
                .flags(GENERATE_PLATE)
                .color(0x9a9fb3)
                .iconSet(MaterialIconSet.SHINY)
                .components(Aluminium, 1, Silicon, 1, Oxygen, 4)
                .build();

        DimethylCarbonate = new Material.Builder(25115, gregtechId("dimethylcarbonate"))
                .fluid()
                .colorAverage()
                .components(Carbon, 3, Hydrogen, 6, Oxygen, 3)
                .build()
                .setFormula("(CH3O)2CO",true);

        TetramethylammoniumBromide = new Material.Builder(25116, gregtechId("tetramethylammoniumbromide"))
                .dust()
                .colorAverage()
                .components(Carbon, 4, Hydrogen, 12, Nitrogen, 1, Bromine, 1)
                .build()
                .setFormula("(CH3)4NBr",true);

        DiamondSonicationSolution = new Material.Builder(25117, gregtechId("diamondsonicationsolution"))
                .fluid()
                .colorAverage()
                .flags(DISABLE_DECOMPOSITION)
                .components(Graphite, 1, Phenol, 1)
                .build();

        SuperfluidHelium3 = new Material.Builder(25118, gregtechId("superfluidhelium"))
                .fluid()
                .colorAverage()
                .flags(DISABLE_DECOMPOSITION)
                .components(Helium3, 1)
                .build();

        CobaltChloride = new Material.Builder(25119, gregtechId("cobaltchloride"))
                .fluid()
                .color(0x48559F)
                .flags(DISABLE_DECOMPOSITION)
                .components(Cobalt, 1, Chlorine, 2)
                .build();

        CobaltIodide = new Material.Builder(25120, gregtechId("cobaltiodide"))
                .fluid()
                .colorAverage()
                .flags(DISABLE_DECOMPOSITION)
                .build();

        Cobalt59 = new Material.Builder(25121, gregtechId("cobalt_59"))
                .dust()
                .color(0x3D3DFA)
                .build()
                .setFormula("Co-59", false);

        Cobalt59Iodide = new Material.Builder(25122, gregtechId("cobalt_59_iodide"))
                .fluid()
                .color(0x0B0058)
                .build();

        Cobalt60Iodide = new Material.Builder(25123, gregtechId("cobalt_60_iodide"))
                .fluid()
                .colorAverage()
                .build();

        HydroiodicAcid = new Material.Builder(25124, gregtechId("hydroiodicacid"))
                .fluid()
                .colorAverage()
                .build();

        ImpureHydroiodicAcid = new Material.Builder(25125, gregtechId("impurehydroiodicacid"))
                .fluid()
                .colorAverage()
                .flags(DISABLE_DECOMPOSITION)
                .components(HydroiodicAcid, 1, Water, 1)
                .build()
                .setFormula("N2(HI)2H2O",true);

        Butynediol = new Material.Builder(25126, gregtechId("butynediol"))
                .fluid()
                .colorAverage()
                .components(Carbon, 4, Hydrogen, 6, Oxygen, 2)
                .build();

        KAOil = new Material.Builder(25127, gregtechId("ka_oil"))
                .fluid()
                .color(0xFA7B53)
                .components(Carbon, 12, Hydrogen, 22, Oxygen, 2)
                .build()
                .setFormula("2(C6H11OH)2((CH2)5CO)", true);

        AdipicAcid = new Material.Builder(25128, gregtechId("adipic_acid"))
                .dust()
                .colorAverage()
                .components(Carbon, 6, Hydrogen, 10, Oxygen, 4)
                .build()
                .setFormula("C6H8O2(OH)2", true);

        NitrousAcid = new Material.Builder(25138, gregtechId("nitrous_acid"))
                .fluid()
                .colorAverage()
                .components(Hydrogen, 1, Nitrogen, 1, Oxygen, 2)
                .build();

        BariumOxide = new Material.Builder(25129, gregtechId("barium_oxide"))
                .dust()
                .colorAverage()
                .components(Barium, 1, Oxygen, 1)
                .build();

        BariumHydroxide = new Material.Builder(25130, gregtechId("barium_hydroxide"))
                .dust()
                .colorAverage()
                .components(Barium, 1, Hydrogen, 1, Oxygen, 1)
                .build();

        Cyclopentanone = new Material.Builder(25131, gregtechId("cyclopentanone"))
                .fluid()
                .colorAverage()
                .components(Carbon, 5, Hydrogen, 8, Oxygen, 1)
                .build()
                .setFormula("(CH2)4CO",true);

        BenzenesulfonicAcid = new Material.Builder(25132, gregtechId("benzenesulfonic_acid"))
                .dust()
                .colorAverage()
                .components(Carbon, 6, Hydrogen, 6, Oxygen, 3, Sulfur, 1)
                .build();

        TriphenylsulfoniumHexafluoroantimonate = new Material.Builder(25133, gregtechId("triphenylsulfonium_hexafluoroantimonate"))
                .fluid()
                .color(0x8968FA)
                .components(Carbon, 18, Hydrogen, 15, Sulfur, 1, Antimony, 1, Fluorine, 6)
                .build();

        HypofluorousAcid = new Material.Builder(25134, gregtechId("hypofluorous_acid"))
                .fluid()
                .colorAverage()
                .components(Hydrogen, 1, Fluorine, 1, Oxygen, 1)
                .build();

        HexafluoroantimonateSalt1 = new Material.Builder(25135, gregtechId("hexafluoroantimonate_salt_1"))
                .fluid()
                .color(0x26095F)
                .components(Carbon, 36, Hydrogen, 28, Sulfur, 3, Antimony, 2, Fluorine, 12)
                .build()
                .setFormula("C36H28S3(SbF6)2", true);

        HexafluoroantimonateSalt2 = new Material.Builder(25136, gregtechId("hexafluoroantimonate_salt_2"))
                .fluid()
                .color(0x5A2F9F)
                .components(Carbon, 24, Hydrogen, 19, Sulfur, 2, Antimony, 1, Fluorine, 6)
                .build();

        MixedHexafluoroantimonateSalts = new Material.Builder(25137, gregtechId("mixed_hexafluoroantimonate_salts"))
                .fluid()
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .colorAverage()
                .components(HexafluoroantimonateSalt1, 1, HexafluoroantimonateSalt2, 1)
                .build();

        DiluteFluoroantimonicAcid = new Material.Builder(25139, gregtechId("dilute_fluoroantimonic_acid"))
                .fluid()
                .flags(DISABLE_DECOMPOSITION)
                .colorAverage()
                .components(Hydrogen, 1, Antimony, 1, Fluorine, 6)
                .build();

        IndiumSolution = new Material.Builder(25140, gregtechId("indium_solution"))
                .fluid()
                .color(0x58474C)
                .flags(DISABLE_DECOMPOSITION)
                .build()
                .setFormula("(H2SO4)?", true);

        IndiumSulfide = new Material.Builder(25141, gregtechId("indium_sulfide"))
                .dust()
                .iconSet(MaterialIconSet.SHINY)
                .colorAverage()
                .components(Indium, 2, Sulfur, 3)
                .build();

        IndiumResidue = new Material.Builder(25142, gregtechId("indium_refining_residue"))
                .fluid()
                .iconSet(MaterialIconSet.LIGNITE)
                .color(0x060921)
                .build();

        ZirconiumTetrafluoride = new Material.Builder(25143, gregtechId("zirconium_fluoride"))
                .dust()
                .colorAverage()
                .components(Zirconium, 1, Fluorine, 4)
                .build();

        BariumDifluoride = new Material.Builder(25144, gregtechId("barium_fluoride"))
                .dust()
                .colorAverage()
                .components(Barium, 1, Fluorine, 2)
                .build();

        LanthanumTrifluoride = new Material.Builder(25145, gregtechId("lanthanum_fluoride"))
                .dust()
                .colorAverage()
                .components(Lanthanum, 1, Fluorine, 3)
                .build();

        AluminiumTrifluoride = new Material.Builder(25146, gregtechId("aluminium_fluoride"))
                .dust()
                .colorAverage()
                .components(Aluminium, 1, Fluorine, 3)
                .build();

        SodiumFluoride = new Material.Builder(25147, gregtechId("sodium_fluoride"))
                .dust()
                .colorAverage()
                .components(Sodium, 1, Fluorine, 1)
                .build();

        BauxiteSlurry = new Material.Builder(25148, gregtechId("bauxite_slurry"))
                .fluid()
                .color(0x51040A)
                .build();

        IlmeniteSlurry = new Material.Builder(25149, gregtechId("ilmenite_slurry"))
                .fluid()
                .color(0x0A0212)
                .build();

        RedMud = new Material.Builder(25150, gregtechId("red_mud"))
                .fluid()
                .colorAverage()
                .flags(DISABLE_DECOMPOSITION)
                .components(Rutile, 1, HydrochloricAcid, 2)
                .build();

        HeavyRedMudResidue = new Material.Builder(25151, gregtechId("red_mud_residue"))
                .fluid()
                .color(0x091012)
                .build();

        RefractoryMetalResidue = new Material.Builder(25152, gregtechId("refractory_metal_residue"))
                .fluid()
                .color(0x164347)
                .build();

        PotassiumHexafluorohafnate = new Material.Builder(25153, gregtechId("potassium_hexafluorohafnate"))
                .gem()
                .colorAverage()
                .components(Potassium, 2, Hafnium, 1, Fluorine, 6)
                .build();

        PotassiumHexafluorozirconate = new Material.Builder(25154, gregtechId("potassium_hexafluorozironate"))
                .gem()
                .colorAverage()
                .components(Potassium, 2, Zirconium, 1, Fluorine, 6)
                .build();

        HafniumTetrachloride = new Material.Builder(25155, gregtechId("hafnium_tetrachloride"))
                .fluid()
                .colorAverage()
                .components(Hafnium, 1, Chlorine, 4)
                .build();

        ZirconiumTetrachloride = new Material.Builder(25156, gregtechId("zirconium_tetrachloride"))
                .fluid()
                .colorAverage()
                .components(Zirconium, 1, Chlorine, 4)
                .build();

        PotassiumFluorideRefractoryMixture = new Material.Builder(25157, gregtechId("potassium_fluoride_refractory_mixture"))
                .fluid()
                .color(0x667E71)
                .build();

        Trichlorosilane = new Material.Builder(25158, gregtechId("trichlorosilane"))
                .fluid()
                .colorAverage()
                .components(Hydrogen, 1, Silicon, 1, Chlorine, 3)
                .build();

        PotassiumHydride = new Material.Builder(25181, gregtechId("potassium_hydride"))
                .dust()
                .components(Potassium, 1, Hydrogen, 1)
                .colorAverage()
                .build();

        Aminopropionitrile = new Material.Builder(25159, gregtechId("aminopropionitrile"))
                .fluid()
                .components(Carbon, 3, Hydrogen, 6, Nitrogen, 2)
                .colorAverage()
                .build();

        Aminopropylamine = new Material.Builder(25160, gregtechId("aminopropylamine"))
                .fluid()
                .components(Carbon, 3, Hydrogen, 10, Nitrogen, 2)
                .colorAverage()
                .build();

        KAPA = new Material.Builder(25161, gregtechId("kapa"))
                .fluid()
                .components(Potassium, 2, Carbon, 3, Hydrogen, 10, Nitrogen, 2)
                .colorAverage()
                .build();

        BetaPinene  = new Material.Builder(25162, gregtechId("betapinene"))
                .dust()
                .components(Carbon, 10, Hydrogen, 16)
                .colorAverage()
                .build();

        AlphaPinene = new Material.Builder(25163, gregtechId("alphapinene"))
                .dust()
                .components(Carbon, 10, Hydrogen, 16)
                .colorAverage()
                .build();

        Camphene = new Material.Builder(25164, gregtechId("camphene"))
                .dust()
                .components(Carbon, 10, Hydrogen, 16)
                .colorAverage()
                .build();

        IsobornylAcetate = new Material.Builder(25165, gregtechId("isobornyl_acetate"))
                .dust()
                .components(Carbon, 12, Hydrogen, 20, Oxygen, 2)
                .colorAverage()
                .build();

        Isoborneol = new Material.Builder(25166, gregtechId("isoborneol"))
                .dust()
                .components(Carbon, 10, Hydrogen, 18, Oxygen, 1)
                .colorAverage()
                .build();

        SodiumAcetate = new Material.Builder(25167, gregtechId("sodium_acetate"))
                .fluid()
                .components(Carbon, 2, Hydrogen, 3, Sodium, 1, Oxygen, 2)
                .colorAverage()
                .build()
                .setFormula("CH3COONa", true);

        DehydrogenationCatalyst = new Material.Builder(25168, gregtechId("dehydrogenation_catalyst"))
                .dust()
                .components()
                .color(0x2C1711)
                .build();

        SodiumPerchlorate = new Material.Builder(25169, gregtechId("sodium_perchlorate"))
                .dust()
                .iconSet(MaterialIconSet.BRIGHT)
                .components(Sodium, 1, Chlorine, 1, Oxygen, 4)
                .colorAverage()
                .build();

        PerchloricAcid = new Material.Builder(25170, gregtechId("perchloric_acid"))
                .fluid()
                .components(Hydrogen, 1, Chlorine, 1, Oxygen, 4)
                .colorAverage()
                .build();

        Phenylhydrazine = new Material.Builder(25171, gregtechId("phenylhydrazine"))
                .fluid()
                .components(Carbon, 6, Hydrogen, 8, Nitrogen, 2)
                .colorAverage()
                .build();

        BenzoylChloride = new Material.Builder(25172, gregtechId("benzoyl_chloride"))
                .fluid()
                .components(Carbon, 7, Hydrogen, 5, Chlorine, 1, Oxygen, 1)
                .colorAverage()
                .build();

        TriphenylMethoxytriazoylPerchlorate = new Material.Builder(25173, gregtechId("triphenyl_methoxytriazoyl_perchlorate"))
                .dust()
                .components(Carbon, 20, Hydrogen, 20, Oxygen, 6, Chlorine, 1, Nitrogen, 3)
                .flags()
                .colorAverage()
                .build();

        SodiumMethoxide = new Material.Builder(25174, gregtechId("sodium_methoxide"))
                .dust()
                .components(Carbon, 1, Hydrogen, 3, Sodium, 1, Oxygen, 1)
                .colorAverage()
                .build();

        TriphenylMethoxytriazole = new Material.Builder(25175, gregtechId("triphenyl_methoxytriazole"))
                .dust()
                .iconSet(MaterialIconSet.SHINY)
                .components(Carbon, 21, Hydrogen, 23, Oxygen, 3, Nitrogen, 3)
                .colorAverage()
                .build();

        Acetaldehyde = new Material.Builder(25176, gregtechId("acetaldehyde"))
                .fluid()
                .components(Carbon, 2, Hydrogen, 4, Oxygen, 1)
                .colorAverage()
                .build();

        Acetoin  = new Material.Builder(25178, gregtechId("acetoin"))
                .fluid()
                .components(Carbon, 4, Hydrogen, 8, Oxygen, 2)
                .colorAverage()
                .build();

        MetaNitrochlorobenzine = new Material.Builder(25179, gregtechId("meta_nitrochlorobenzine"))
                .fluid()
                .components(Carbon, 6, Hydrogen, 4, Chlorine, 1, Nitrogen, 1, Oxygen, 2)
                .colorAverage()
                .build();

        Nitroanisole = new Material.Builder(25180, gregtechId("nitroanisole"))
                .fluid()
                .components(Carbon, 7, Hydrogen, 7, Nitrogen, 1, Oxygen, 3)
                .colorAverage()
                .build();

        Anisidine = new Material.Builder(25182, gregtechId("anisidine"))
                .fluid()
                .components(Carbon, 7, Hydrogen, 9, Nitrogen, 1, Oxygen, 1)
                .colorAverage()
                .build();

        MethylBromoacetate = new Material.Builder(25183, gregtechId("methyl_bromoacetate"))
                .dust()
                .colorAverage()
                .components(Carbon, 3, Hydrogen, 5, Bromine, 1, Oxygen, 2)
                .build();

        CarbethoxymethyleneTriphenylphosphorane = new Material.Builder(25184, gregtechId("carbomethoxy_methylenetriphenylphosphorane"))
                .dust()
                .colorAverage()
                .components(Carbon, 22, Hydrogen, 21, Oxygen, 2, Phosphorus, 1)
                .build();

        PropargylAlcohol = new Material.Builder(25185, gregtechId("propargyl_alcohol"))
                .fluid()
                .colorAverage()
                .components(Carbon, 3, Hydrogen, 4, Oxygen, 1)
                .build();

        PropargylBromide = new Material.Builder(25186, gregtechId("propargyl_bromide"))
                .fluid()
                .colorAverage()
                .components(Carbon, 3, Hydrogen, 3, Bromine, 1)
                .build();

        MethylmagnesiumIodide = new Material.Builder(25187, gregtechId("methylmagnesium_iodide"))
                .dust()
                .colorAverage()
                .components(Carbon, 1, Hydrogen, 3, Magnesium, 1)
                .build()
                .setFormula("CH3COCl", true);

        AcetylChloride = new Material.Builder(25188, gregtechId("acetyl_chloride"))
                .fluid()
                .colorAverage()
                .components(Carbon, 2, Hydrogen, 3, Oxygen, 1, Chlorine, 1)
                .build();

        Acetophenone = new Material.Builder(25189, gregtechId("acetophenone"))
                .fluid()
                .colorAverage()
                .components(Carbon, 8, Hydrogen, 8, Oxygen, 1)
                .build()
                .setFormula("C6H5COCH3", true);

        Phenylethylamine = new Material.Builder(25190, gregtechId("phenylethylamine"))
                .fluid()
                .colorAverage()
                .components(Carbon, 8, Hydrogen, 11, Nitrogen, 1)
                .build();

        PhenylethylIsocyanate = new Material.Builder(25191, gregtechId("phenylethyl_isocyanate"))
                .fluid()
                .colorAverage()
                .components(Carbon, 9, Hydrogen, 9, Nitrogen, 1, Oxygen, 1)
                .build();

        TriethyloxoniumTetrafluoroborate = new Material.Builder(25192, gregtechId("triethyloxonium_tetrafluoroborate"))
                .dust()
                .colorAverage()
                .components(Carbon, 6, Hydrogen, 15, Oxygen, 1, Boron, 1, Fluorine, 4)
                .build()
                .setFormula("(C2H5)3O(BF4)", true);

        TrischloroethoxypopanylBorate = new Material.Builder(25193, gregtechId("trischloroethoxypopanyl_borate"))
                .dust()
                .colorAverage()
                .components()
                .build();

        SulfuricFlueGas = new Material.Builder(25194, gregtechId("sulfuric_flue_gas"))
                .fluid()
                .color(0x6B6623)
                .build();

        SulfuricIronSlag = new Material.Builder(25195, gregtechId("sulfuric_iron_slag"))
                .dust()
                .iconSet(MaterialIconSet.ROUGH)
                .color(0x616B33)
                .build();

        SulfuricCopperSlag = new Material.Builder(25196, gregtechId("sulfuric_copper_slag"))
                .dust()
                .iconSet(MaterialIconSet.ROUGH)
                .color(0x975128)
                .build();

        SulfuricNickelSlag = new Material.Builder(25197, gregtechId("sulfuric_nickel_slag"))
                .dust()
                .iconSet(MaterialIconSet.ROUGH)
                .color(0x769197)
                .build();

        SulfuricZincSlag = new Material.Builder(25198, gregtechId("sulfuric_zinc_slag"))
                .dust()
                .iconSet(MaterialIconSet.ROUGH)
                .color(0xB5AECA)
                .build();


    }
}
