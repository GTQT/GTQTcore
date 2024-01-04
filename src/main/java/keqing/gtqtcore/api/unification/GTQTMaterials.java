package keqing.gtqtcore.api.unification;

import gregtech.api.unification.material.Material;
import keqing.gtqtcore.api.unification.matreials.*;

public class GTQTMaterials {
    public static Material GalvanizedSteel;
    public static Material Inconel625;
    public static Material HastelloyN;
    public static Material Stellite;
    public static Material QuantumAlloy;
    public static Material Grisium;
    public static Material Hdcs;
    public static Material Abyssalloy;
    public static Material Lafium;
    public static Material BlackTitanium;
    public static Material Talonite;
    public static Material BlackPlutonium;
    public static Material MaragingSteel250;
    public static Material Staballoy;
    public static Material BabbittAlloy;
    public static Material ZirconiumCarbide;
    public static Material Inconel792;
    public static Material IncoloyMA813;
    public static Material HastelloyX78;
    public static Material HastelloyK243;
    public static Material MARM200Steel;
    public static Material MARM200CeSteel;
    public static Material TanmolyiumBetaC;
    public static Material HastelloyC59;
    public static Material HMS1J79Alloy;
    public static Material HY1301;
    public static Material AusteniticStainlessSteel904L;
    public static Material EglinSteelBase;
    public static Material EglinSteel;
    public static Material Pikyonium64B;
    public static Material Cinobite;
    public static Material TitanSteel;
    public static Material IncoloyDS;
    public static Material BETSPerrhenate;
    public static Material Inconel690;
    public static Material Tantalloy61;
    public static Material Incoloy020;
    public static Material HG1223;
    public static Material HMS1J22Alloy;
    public static Material FullereneSuperconductor;
    public static Material Legendarium;
    public static Material SuperheavyHAlloy;
    public static Material SuperheavyLAlloy;
    public static Material PlatinumGroupAlloy;

    public static Material MagnetoHydrodynamicallyConstrainedStarMatter;
    public static Material Draconium;
    public static Material AwakenedDraconium;
    public static Material ChaoticDraconium;
    public static Material Plutonium244;
    public static Material MetastableOganesson;
    public static Material MetastableHassium;
    public static Material MetastableFlerovium;
    public static Material CosmicNeutronium;
    public static Material DegenerateRhenium;
    public static Material Infinity;
    public static Material Rhugnor;
    public static Material Hypogen;
    public static Material Californium252;
    public static Material AstralTitanium;
    public static Material CelestialTungsten;
    public static Material Ytterbium178;
    public static Material Ichorium;
    public static Material IchorLiquid;
    public static Material CrystalMatrix;
    public static Material VoidMetal;
    public static Material Mithril;
    public static Material Bismuth209;
    public static Material Lead209;
    public static Material HighPressureSteam;
    public static Material SteamExhaustGas;
    public static Material SuperheatedSteam;
    public static Material Nitinol;
    public static Material Pyrotheum;
    public static Material StellarMaterialResidueA;
    public static Material StellarMaterialResidueB;
    public static Material StellarMaterialResidueC;
    public static Material StellarMaterial;
    public static Material LightNaquadahFuel;
    public static Material MediumNaquadahFuel;
    public static Material HeavyNaquadahFuel;

    public static Material DenseHydrazineMixtureFuel;
    public static Material HighlyPurifiedCoalTar;
    public static Material RP1RocketFuel;
    public static Material Methylhydrazine;
    public static Material MethylhydrazineNitrateRocketFuel;
    public static Material Alumite;
    public static Material Alubrassa;
    public static Material Coolant;
    public static Material SuperCoolant;
    public static Material Cryotheum;
    public static Material XPJuice;
    public static Material Spirit;
    public static Material Hollowtears;
    public static Material AtomicSeparationCatalyst;
    public static Material Tiberium;
    public static Material NeutronsFlow;
    public static Material ProtonFlow;
    public static Material BismuthLeadAlloy;
    public static Material UreaMix;
    public static Material FermentationBase;
    public static Material Resin;
    public static Material CalciumCarbonate;
    public static Material PropionicAcid;
    public static Material SodiumAluminate;
    public static Material CarbenDisulfide;
    public static Material PineOil;
    public static Material Periodicium;

    public static Material AlmandineFront;
    public static Material PentlanditeFront;
    public static Material ChalcopyriteFront;
    public static Material GrossularFront;
    public static Material MonaziteFront;
    public static Material NickelFront;
    public static Material PlatinumFront;
    public static Material PyropeFront;
    public static Material RedstoneFront;
    public static Material SpessartineFront;
    public static Material SphaleriteFront;



    public static Material Thaumium;
    public static Material MetallicHydrogen;
    public static Material Ethylenimine;
    public static Material Polyethyleneimine;
    public static Material RedMud;
    public static Material LeanGoldSulphide;
    public static Material RichGoldSulphide;
    public static Material Pyrargyrite;
    public static Material Zincantimonygalvanite;
    public static Material Crocoite;
    public static Material Cryolite;
    public static Material Lignite;
    public static Material infused_air;
    public static Material infused_fire;
    public static Material infused_water;
    public static Material infused_earth;
    public static Material infused_entropy;
    public static Material infused_order;
    public static Material Amblygonite;
    public static Material PreTreatedCrudeOilContainingImpurities;
    public static Material Demulsifier;
    public static Material EthyleneOxide;
    public static Material PropyleneOxide;
    public static Material PreTreatedCrudeOil;
    public static Material Asphalt;
    public static Material WaxOil;
    public static Material WaterGas;
    public static Material VacuumResidue;
    public static Material AtmosphericResidue;
    public static Material GasOil;
    public static Material OilSlurry;

    //https://github.com/Darknight123MC/Gregica-Sharp/blob/master/src/main/java/me/oganesson/gregicas/api/unification/GSMaterials.java

    public GTQTMaterials() {}

    public static void register() {

        GTQTElementMaterials.register();
        FirstDegreeMaterials.register();
        SecondDegreeMaterials.register();
        HigherDegreeMaterials.register();
        EPMachineCasingMaterials.register();
        EPMaterialPropertyAddition.init();
        EPMaterialFlagAddition.init();

    }


}
