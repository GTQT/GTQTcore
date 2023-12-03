package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.material.properties.*;


import static gregicality.multiblocks.api.unification.GCYMMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.ore.OrePrefix.stickLong;
import static keqing.gtqtcore.api.unification.material.info.EPMaterialFlags.GENERATE_CURVED_PLATE;

public class EPMaterialPropertyAddition {
    public static void init() {
        //  Setter
        RarestMetalMixture.setFormula("Ir2O2(SiO2)2Au3?");
        IridiumMetalResidue.setFormula("Ir2O4(SiO2)2Au3");
        AcidicOsmiumSolution.setFormula("OsO4(H2O)(HCl)");
        PalladiumRaw.setFormula("PdCl2?");

        WroughtIron.addFlags(GENERATE_ROTOR, GENERATE_SMALL_GEAR);
        Rhenium.addFlags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE);
        Nickel.addFlags(GENERATE_FOIL);
        Titanium.addFlags(GENERATE_FOIL);
        Germanium.addFlags(GENERATE_FOIL);
        Tungsten.addFlags(GENERATE_FINE_WIRE);
        RhodiumPlatedPalladium.addFlags(GENERATE_FRAME, GENERATE_GEAR);
        Darmstadtium.addFlags(GENERATE_FRAME, GENERATE_GEAR);
        Naquadria.addFlags(GENERATE_FRAME);
        Neutronium.addFlags(GENERATE_FRAME);
        Neutronium.addFlags(GENERATE_ROTOR, GENERATE_SMALL_GEAR);
        HSSE.addFlags(GENERATE_DOUBLE_PLATE);
        HSSS.addFlags(GENERATE_DOUBLE_PLATE);
        Dubnium.addFlags(GENERATE_ROD, GENERATE_BOLT_SCREW);
        Rutherfordium.addFlags(GENERATE_ROD, GENERATE_BOLT_SCREW);
        Livermorium.addFlags(GENERATE_ROD, GENERATE_BOLT_SCREW);
        Tritanium.addFlags(GENERATE_DOUBLE_PLATE);
        Osmiridium.addFlags(GENERATE_DOUBLE_PLATE);
        Ruridit.addFlags(GENERATE_DOUBLE_PLATE);
        IronMagnetic.addFlags(GENERATE_LONG_ROD);
        SteelMagnetic.addFlags(GENERATE_LONG_ROD);
        NeodymiumMagnetic.addFlags(GENERATE_LONG_ROD);
    }
}