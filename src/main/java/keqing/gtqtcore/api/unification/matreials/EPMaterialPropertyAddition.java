package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.GTValues;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.material.properties.*;


import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;

public class EPMaterialPropertyAddition {
    public static void init() {
        //  Setter
        RarestMetalMixture.setFormula("Ir2O2(SiO2)2Au3?");
        IridiumMetalResidue.setFormula("Ir2O4(SiO2)2Au3");
        AcidicOsmiumSolution.setFormula("OsO4(H2O)(HCl)");
        PalladiumRaw.setFormula("PdCl2?");

        // Dusts
        Iodine.setProperty(PropertyKey.DUST, new DustProperty());
        Praseodymium.setProperty(PropertyKey.DUST, new DustProperty());
        Scandium.setProperty(PropertyKey.DUST, new DustProperty());
        Gadolinium.setProperty(PropertyKey.DUST, new DustProperty());
        Terbium.setProperty(PropertyKey.DUST, new DustProperty());
        Dysprosium.setProperty(PropertyKey.DUST, new DustProperty());
        Holmium.setProperty(PropertyKey.DUST, new DustProperty());
        Erbium.setProperty(PropertyKey.DUST, new DustProperty());
        Thulium.setProperty(PropertyKey.DUST, new DustProperty());
        Ytterbium.setProperty(PropertyKey.DUST, new DustProperty());
        Zirconium.setProperty(PropertyKey.DUST, new DustProperty());
        Tellurium.setProperty(PropertyKey.DUST, new DustProperty());
        Selenium.setProperty(PropertyKey.DUST, new DustProperty());
        Rubidium.setProperty(PropertyKey.DUST, new DustProperty());
        Thallium.setProperty(PropertyKey.DUST, new DustProperty());

        // Ingots
        Germanium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Rhenium.setProperty(PropertyKey.INGOT, new IngotProperty());

        // Blast
        Germanium.setProperty(PropertyKey.BLAST, new BlastProperty(1211));

        // Fluids
        SodiumBisulfate.setProperty(PropertyKey.FLUID, new FluidProperty());
        Bromine.setProperty(PropertyKey.FLUID, new FluidProperty());
        Bromine.setMaterialIconSet(MaterialIconSet.FLUID);
        Germanium.setProperty(PropertyKey.FLUID, new FluidProperty());


        // Ore Byproducts
        //TODO Fix ore byproduct changes
        Pollucite.getProperty(PropertyKey.ORE).setOreByProducts(Aluminium, Potassium, Caesium, Pollucite);

        // Cable Properties
        WireProperties wireProp = RutheniumTriniumAmericiumNeutronate.getProperty(PropertyKey.WIRE);
        wireProp.setSuperconductor(false);
        wireProp.setLossPerBlock(32);
        wireProp.setVoltage((int) GTValues.V[GTValues.UIV]);

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