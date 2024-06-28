package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.GTValues;
import gregtech.api.unification.material.properties.*;


import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.integration.crafttweaker.material.MaterialPropertyExpansion.addFluid;
import static keqing.gtqtcore.api.unification.GTQTMaterials.TitanSteel;

public class GTQTMaterialPropertyAddition {
    public static void init() {
        //  Setter
        RarestMetalMixture.setFormula("Ir2O2(SiO2)2Au3?");
        IridiumMetalResidue.setFormula("Ir2O4(SiO2)2Au3");
        AcidicOsmiumSolution.setFormula("OsO4(H2O)(HCl)");
        PalladiumRaw.setFormula("PdCl2?");
        NetherStar.setFormula("PtDc?KQ", true);
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

        //Cable
        TitanSteel.setProperty(PropertyKey.WIRE, new WireProperties((int) GTValues.V[GTValues.EV], 4,2));
        //PLASMAS
        /*
        List<Material> pmats = new ArrayList<>();
        Collections.addAll(pmats, Carbon, Hydrogen, Helium3, Radon, Krypton, Neon, Magnesium, Silicon, Sulfur, Argon,Calcium, Titanium, Potassium);
        for (Material mat : pmats) {
            addFluid(mat, "plasma", false);
        }

         */
    }
}