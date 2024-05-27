package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.GTValues;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.material.properties.*;


import static gregtech.api.GTValues.UEV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Thorianite;
import static keqing.gtqtcore.api.unification.material.info.EPMaterialFlags.GENERATE_PELLETS;

public class EPMaterialPropertyAddition {
    public static void init() {
        //  Setter
        RarestMetalMixture.setFormula("Ir2O2(SiO2)2Au3?");
        IridiumMetalResidue.setFormula("Ir2O4(SiO2)2Au3");
        AcidicOsmiumSolution.setFormula("OsO4(H2O)(HCl)");
        PalladiumRaw.setFormula("PdCl2?");

        //  Elements
        Calcium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Dubnium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Dubnium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Rutherfordium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Rutherfordium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Polonium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Polonium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Bromine.setProperty(PropertyKey.FLUID, new FluidProperty());
        Iodine.setProperty(PropertyKey.DUST, new DustProperty());
        Iodine.setProperty(PropertyKey.FLUID, new FluidProperty());
        Livermorium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Livermorium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Seaborgium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Seaborgium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Seaborgium.setProperty(PropertyKey.WIRE, new WireProperties(VA[UEV], 32, 32, false));
        Actinium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Actinium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Caesium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Astatine.setProperty(PropertyKey.FLUID, new FluidProperty());
        Californium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Californium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Curium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Curium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Radium.setProperty(PropertyKey.DUST, new DustProperty());
        Radium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Neptunium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Neptunium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Bohrium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Bohrium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Sodium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Meitnerium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Meitnerium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Roentgenium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Roentgenium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Copernicium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Copernicium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Nihonium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Nihonium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Moscovium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Moscovium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Tennessine.setProperty(PropertyKey.FLUID, new FluidProperty());
        Francium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Francium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Technetium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Technetium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Protactinium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Protactinium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Berkelium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Berkelium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Einsteinium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Einsteinium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Fermium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Fermium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Mendelevium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Mendelevium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Nobelium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Nobelium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Lawrencium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Lawrencium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Boron.setProperty(PropertyKey.FLUID, new FluidProperty());
        Graphene.setProperty(PropertyKey.FLUID, new FluidProperty());
        //  Isa Mill Ore Process
        Strontium.setProperty(PropertyKey.DUST, new DustProperty());
        Strontium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Strontium.setProperty(PropertyKey.FLUID, new FluidProperty());
        Promethium.setProperty(PropertyKey.INGOT, new IngotProperty());

        // Dusts
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

        Praseodymium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Scandium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Gadolinium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Terbium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Dysprosium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Holmium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Erbium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Thulium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Ytterbium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Zirconium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Tellurium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Selenium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Rubidium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Thallium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Californium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        // Ingots
        Germanium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Rhenium.setProperty(PropertyKey.INGOT, new IngotProperty());

        // Blast
        Germanium.setProperty(PropertyKey.BLAST, new BlastProperty(1211));

        // Fluids
        SodiumHydroxide.setProperty(PropertyKey.FLUID, new FluidProperty());
        SodiumBisulfate.setProperty(PropertyKey.FLUID, new FluidProperty());

        Germanium.setProperty(PropertyKey.FLUID, new FluidProperty());
        AmmoniumChloride.setProperty(PropertyKey.FLUID, new FluidProperty());
        Hafnium.setProperty(PropertyKey.DUST, new DustProperty());
        Hafnium.setProperty(PropertyKey.INGOT, new IngotProperty());
        // Ore Byproducts
        //TODO Fix ore byproduct changes
        Pollucite.getProperty(PropertyKey.ORE).setOreByProducts(Aluminium, Potassium, Caesium, Pollucite);

        // Cable Properties
        WireProperties wireProp = RutheniumTriniumAmericiumNeutronate.getProperty(PropertyKey.WIRE);
        wireProp.setSuperconductor(false);
        wireProp.setLossPerBlock(32);
        wireProp.setVoltage((int) GTValues.V[GTValues.UIV]);

        Copper.addFlags(GENERATE_BOLT_SCREW);
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
        NiobiumTitanium.addFlags(GENERATE_FRAME);
        Hafnium.addFlags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE);

        //pellets
        Uranium235.addFlags(GENERATE_PELLETS);
        Uranium238.addFlags(GENERATE_PELLETS);
        Plutonium239.addFlags(GENERATE_PELLETS);
        Plutonium241.addFlags(GENERATE_PELLETS);
        Plutonium244.addFlags(GENERATE_PELLETS);
        Thorium.addFlags(GENERATE_PELLETS);
        Curium.addFlags(GENERATE_PELLETS);
        Americium.addFlags(GENERATE_PELLETS);
        Neptunium.addFlags(GENERATE_PELLETS);
    }
}