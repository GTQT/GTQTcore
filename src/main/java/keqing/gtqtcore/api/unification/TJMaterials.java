package keqing.gtqtcore.api.unification;

import gregtech.api.unification.Element;
import gregtech.api.unification.Elements;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlag;
import keqing.gtqtcore.api.unification.matreials.GTQTTJFirstDegreeMaterials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static gregtech.api.unification.material.info.MaterialFlags.*;


public class TJMaterials {

    //Isotopes
    public static final Element Co60 = Elements.add(27L, 33L, -1L, null, "Cobalt-60", "Co-60", true);

    public static List<MaterialFlag> STANDARDPLATE = new ArrayList<>(Arrays.asList(GENERATE_PLATE, GENERATE_DENSE, EXCLUDE_BLOCK_CRAFTING_BY_HAND_RECIPES));
    public static List<MaterialFlag> STANDARDWIREFINE = new ArrayList<>(Collections.singletonList(GENERATE_FINE_WIRE));
    public static List<MaterialFlag> STANDARDFOIL = new ArrayList<>(Collections.singletonList(GENERATE_FOIL));
    public static List<MaterialFlag> STANDARDROD = new ArrayList<>(Arrays.asList(GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_BOLT_SCREW));
    public static List<MaterialFlag> STANDARDROTOR = new ArrayList<>(Arrays.asList(GENERATE_ROTOR, GENERATE_RING));
    public static List<MaterialFlag> STANDARDGEAR = new ArrayList<>(Arrays.asList(GENERATE_GEAR, GENERATE_SMALL_GEAR));
    public static List<MaterialFlag> STANDARDSPRING = new ArrayList<>(Arrays.asList(GENERATE_SPRING, GENERATE_SPRING_SMALL));
    public static List<MaterialFlag> STANDARDROUND = new ArrayList<>(Collections.singletonList(GENERATE_ROUND));

    public static List<MaterialFlag> STANDARDCASING = new ArrayList<>(Collections.singletonList(GENERATE_FRAME));


    public static Material Cobalt60;
    public static Material SuperfluidHelium3;

    //Alloys
    public static Material BT6;
    public static Material Birmabright;
    public static Material NickelPlatedTin;
    public static Material DegenerateRhenium;
    public static Material NihoniumTriiodide;
    public static Material SuperheavyH;
    public static Material SuperheavyL;
    public static Material EnrichedNaqAlloy;
    public static Material TriniumSteel;
    public static Material TerfenolD_H;
    public static Material TerfenolD_L;
    public static Material LutetiumTantalate;
    public static Material Iridrhodruthenium;
    public static Material HEA_1;
    public static Material HEA_2;
    public static Material HEA_3;
    public static Material HDCS_1;
    public static Material HDCS_2;
    public static Material HDCS_3;
    public static Material Pikyonium;
    //public static Material TantalumCarbide;
    public static Material HafniumCarbide;
    public static Material SeaborgiumCarbide;
    public static Material TantalumHafniumSeaborgiumCarbide;
    public static Material TantalumHafniumSeaborgiumCarboNitride;
    public static Material OganessonTetraTennesside;


    //Chemicals
    public static Material Methyltrichlorosilane;
    public static Material Methyltrimethoxysilane;
    public static Material Polymethylsilesquioxane;
    public static Material Silane;
    public static Material TriphenylPhosphine;
    public static Material PhenylmagnesiumBromide;
    public static Material Bromobenzene;
    public static Material HydrogenSilsesquioxane;
    public static Material SU8_Photoresist;
    public static Material MolybdenumSulfide;
    public static Material Difluoroethane; //C2H4F2
    public static Material PalladiumChloride; // PdCl2
    public static Material AllylAcetate;
    public static Material SilverLeadOxide;
    public static Material Difluorobenzophenone;
    public static Material Fluorobenzene;
    public static Material Hydroquinone;
    public static Material ZnFeAlClCatalyst;
    public static Material PolyPhosphonitrileFluoroRubber;
    public static Material NitrileButadieneRubber;
    public static Material Resorcinol;
    public static Material Fluorotoluene;
    public static Material LuminescentSiliconNanocrystals;
    public static Material SeleniumMonobromide;
    public static Material HydraziniumChloride;
    public static Material DibromoisophthalicAcid;
    public static Material Dibromoterephthaloyldichloride;
    public static Material HafniumSilicate;
    public static Material GraphenePQD;
    public static Material BismuthIridiumOxide;
    public static Material IndiumFluoride;
    public static Material XenonDioxide;
    public static Material XenonTetraFluoride;
    public static Material XenonOxyTetraFluoride;
    public static Material XenonHexaFluoride;
    public static Material NeonFluoride;
    public static Material ExcitedNeonFluoride;
    public static Material ArgonFluorine;
    public static Material SilverGalliumSelenide;
    public static Material IridiumOnCubicZirconia;
    public static Material OnePropanol;
    public static Material ZSM_5_ZEOLITE;
    public static Material SodiumHydroxideSilica;
    public static Material SodiumAluminate;
    public static Material SodiumAluminumSilicaSolution;
    public static Material AluminoSilicateGlass;
    public static Material TetramethylammoniumBromide;
    public static Material CobaltChloride;
    public static Material CobaltIodide;
    public static Material Cobalt60Iodide;
    public static Material Cobalt59Iodide;
    public static Material Cobalt59;
    public static Material HydroiodicAcid;
    public static Material ImpureHydroiodicAcid;
    public static Material Butynediol;
    public static Material KAOil;
    public static Material AdipicAcid;
    public static Material NitrousAcid;
    public static Material BariumOxide;
    public static Material BariumHydroxide;
    public static Material Cyclopentanone;
    public static Material BenzenesulfonicAcid;
    public static Material TriphenylsulfoniumHexafluoroantimonate;
    public static Material HypofluorousAcid;
    public static Material HexafluoroantimonateSalt1;
    public static Material HexafluoroantimonateSalt2;
    public static Material MixedHexafluoroantimonateSalts;
    public static Material DiluteFluoroantimonicAcid;

    // Cermaics + Glass
    public static Material SodiumPotassiumNiobate;
    public static Material BismuthTelluride;
    public static Material SilicaCeramic;
    public static Material Fiberglass;
    public static Material SynthDiamond;
    public static Material BismuthPhosphomolybdate;
    public static Material Acrylonitrile;
    public static Material ZirconiumTetrafluoride;
    public static Material BariumDifluoride;
    public static Material LanthanumTrifluoride;
    public static Material AluminiumTrifluoride;
    public static Material SodiumFluoride;
    //
    public static Material BauxiteSlurry;
    public static Material IlmeniteSlurry;
    public static Material HeavyRedMudResidue;
    public static Material RefractoryMetalResidue;
    public static Material PotassiumFluorideRefractoryMixture;
    public static Material PotassiumHexafluorozirconate;
    public static Material PotassiumHexafluorohafnate;
    public static Material HafniumTetrachloride;
    public static Material ZirconiumTetrachloride;
    public static Material Trichlorosilane;

    // Polymers
    public static Material Polyacrylonitrile;
    public static Material Polyetheretherketone;
    public static Material CarbonNanotubePolymer;
    public static Material Ladder_Poly_P_Phenylene;

    // Mixtures
    public static Material SuspendedPGQD;
    public static Material ArgonSilane;
    public static Material DiamondSonicationSolution;
    public static Material XenonFluorideSupercondiveMix;
    public static Material P1Solution;
    public static Material TetrakisPDCatalyst;
    public static Material CFCoatingSolution;
    public static Material IndiumSolution;
    public static Material IndiumSulfide;
    public static Material IndiumResidue;
    public static Material SulfuricFlueGas;
    public static Material SulfuricIronSlag;
    public static Material SulfuricZincSlag;
    public static Material SulfuricNickelSlag;
    public static Material SulfuricCopperSlag;

    //BIOCHEM MATERIALS BEWARE

    public static Material PotassiumHydride;
    public static Material Aminopropionitrile;
    public static Material Aminopropylamine;
    public static Material KAPA;
    public static Material BetaPinene;
    public static Material AlphaPinene;
    public static Material Camphene;
    public static Material IsobornylAcetate;
    public static Material Isoborneol;
    public static Material SodiumAcetate;
    public static Material DehydrogenationCatalyst;
    public static Material SodiumPerchlorate;
    public static Material Phenylhydrazine;
    public static Material BenzoylChloride;
    public static Material TriphenylMethoxytriazoylPerchlorate;
    public static Material SodiumMethoxide;
    public static Material TriphenylMethoxytriazole;
    public static Material MetaNitrochlorobenzine;
    public static Material Nitroanisole;
    public static Material MethylBromoacetate;
    public static Material CarbethoxymethyleneTriphenylphosphorane;
    public static Material PropargylAlcohol;
    public static Material PropargylBromide;
    public static Material MethylmagnesiumIodide;
    public static Material AcetylChloride;
    public static Material Acetophenone;
    public static Material Phenylethylamine;
    public static Material PhenylethylIsocyanate;
    public static Material TriethyloxoniumTetrafluoroborate;
    public static Material TrischloroethoxypopanylBorate;



    // Magic materials, unknown composition
    public static Material Draconium;
    public static Material Manasteel;
    public static Material Terrasteel;
    public static Material Thaumium;
    public static Material Void;
    public static Material ColdIron;
    public static Material Starmetal;
    public static Material ManasteelAlloy;
    public static Material Starlight;
    public static Material Salis;

    //Long dist cable materials


    //endgame unknown
    public static Material ProgrammableMatter;
    public static Material Gluons;
    public static Material HeavyQuarks;
    public static Material LightQuarks;
    public static Material Leptons;
    public static Material HeavyQuarkDegenerate;



    @SafeVarargs
    public static List<MaterialFlag> setMaterialFlags(List<MaterialFlag>... materials) {
        List<MaterialFlag> result = new ArrayList<MaterialFlag>();
        for (List<MaterialFlag> x : materials) {
            result.addAll(x);
        }
        return result;
    }

    public static void register() {

        GTQTTJFirstDegreeMaterials.registerMaterials();

    }

}
