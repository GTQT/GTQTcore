package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.GTValues;
import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.store.FluidStorageKey;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.integration.crafttweaker.material.MaterialPropertyExpansion.addFluid;
import static gregtech.integration.groovy.MaterialPropertyExpansion.addLiquid;
import static gregtech.integration.groovy.MaterialPropertyExpansion.addPlasma;
import static keqing.gtqtcore.api.unification.GCYSMaterials.ChloroaceticAcid;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class GTQTMaterialPropertyAddition {
    public static void init() {
        //  Setter
        RarestMetalMixture.setFormula("Ir2O2(SiO2)2Au3?");
        IridiumMetalResidue.setFormula("Ir2O4(SiO2)2Au3");
        AcidicOsmiumSolution.setFormula("OsO4(H2O)(HCl)");
        PalladiumRaw.setFormula("PdCl2?");
        NetherStar.setFormula("PtDc?KQ", true);
        //  Elements
        Sulfur.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Zircon.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Erbium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Ytterbium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Hafnium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Terbium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Calcium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Dubnium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Dubnium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Rutherfordium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Rutherfordium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Polonium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Polonium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Bromine.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Iodine.setProperty(PropertyKey.DUST, new DustProperty());
        Iodine.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Livermorium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Livermorium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Seaborgium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Seaborgium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Seaborgium.setProperty(PropertyKey.WIRE, new WireProperties(VA[UEV], 32, 32, false));
        Actinium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Actinium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Caesium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Astatine.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Californium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Californium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Curium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Curium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Radium.setProperty(PropertyKey.DUST, new DustProperty());
        Radium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Neptunium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Neptunium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Bohrium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Bohrium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Sodium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Meitnerium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Meitnerium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Roentgenium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Roentgenium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Copernicium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Copernicium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Nihonium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Nihonium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Moscovium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Moscovium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Tennessine.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Francium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Francium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Technetium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Technetium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Protactinium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Protactinium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Berkelium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Berkelium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Einsteinium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Einsteinium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Fermium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Fermium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Mendelevium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Mendelevium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Nobelium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Nobelium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Lawrencium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Lawrencium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Boron.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        Graphene.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        //  Isa Mill Ore Process
        Strontium.setProperty(PropertyKey.DUST, new DustProperty());
        Strontium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Strontium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
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

        Americium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Chrome.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Iridium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Lutetium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Manganese.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Niobium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Osmium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Titanium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Tungsten.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Uranium235.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        NaquadahEnriched.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Naquadria.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Neutronium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Trinium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Electrum.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Rutile.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        NetherStar.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Gallium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        // Ingots
        Germanium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Rhenium.setProperty(PropertyKey.INGOT, new IngotProperty());

        // Blast
        Germanium.setProperty(PropertyKey.BLAST, new BlastProperty(1211));

        // Fluids
        SodiumHydroxide.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        SodiumBisulfate.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));

        Germanium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
        AmmoniumChloride.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,new FluidBuilder()));
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
        TitanSteel.setProperty(PropertyKey.WIRE, new WireProperties((int) GTValues.V[GTValues.EV], 4,2));

    }
}