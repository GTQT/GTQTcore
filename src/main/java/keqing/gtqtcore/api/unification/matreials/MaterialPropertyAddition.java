package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.GTValues;
import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.unification.material.properties.*;

import static gregtech.api.GTValues.UEV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.TitanSteel;

public class MaterialPropertyAddition {
    public static void init() {
        //  Setter
        RarestMetalMixture.setFormula("Ir2O2(SiO2)2Au3?");
        IridiumMetalResidue.setFormula("Ir2O4(SiO2)2Au3");
        AcidicOsmiumSolution.setFormula("OsO4(H2O)(HCl)");
        PalladiumRaw.setFormula("PdCl2?");
        NetherStar.setFormula("PtDc?KQ", true);
        //  Elements
        Nitrogen.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.LIQUID,
                new FluidBuilder()
                        .temperature(77)
                        .color(0x008D8F)
                        .name("liquid_nitrogen")
                        .translation("gregtech.fluid.liquid_generic"));

        Hydrogen.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.LIQUID,
                new FluidBuilder()
                        .temperature(20) // 氢气液化温度约为20K
                        .color(0xE0FFFF) // 氢气液态颜色
                        .name("liquid_hydrogen")
                        .translation("gregtech.fluid.liquid_generic"));

        Argon.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.LIQUID,
                new FluidBuilder()
                        .temperature(87) // 氪气液化温度约为87K
                        .color(0x8080FF) // 氪气液态颜色
                        .name("liquid_argon")
                        .translation("gregtech.fluid.liquid_generic"));

        Radon.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.LIQUID,
                new FluidBuilder()
                        .temperature(211) // 氡气液化温度约为211K
                        .color(0x9400D3) // 氡气液态颜色
                        .name("liquid_radon")
                        .translation("gregtech.fluid.liquid_generic"));

        Neon.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.LIQUID,
                new FluidBuilder()
                        .temperature(27) // 氖气液化温度约为27K
                        .color(0xFF4500) // 氖气液态颜色
                        .name("liquid_neon")
                        .translation("gregtech.fluid.liquid_generic"));

        Krypton.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.LIQUID,
                new FluidBuilder()
                        .temperature(119) // 氪气液化温度约为119K
                        .color(0x8080FF) // 氪气液态颜色
                        .name("liquid_krypton")
                        .translation("gregtech.fluid.liquid_generic"));

        Xenon.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.LIQUID,
                new FluidBuilder()
                        .temperature(165) // 氙气液化温度约为165K
                        .color(0x00FFFF) // 氙气液态颜色
                        .name("liquid_xenon")
                        .translation("gregtech.fluid.liquid_generic"));

        CarbonDioxide.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.LIQUID,
                new FluidBuilder()
                        .temperature(195) // 二氧化碳液化温度约为195K
                        .color(0x9B9B9B) // 二氧化碳液态颜色
                        .name("liquid_carbon_dioxide")
                        .translation("gregtech.fluid.liquid_generic"));

        Zirconium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Thulium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Tellurium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Erbium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Ytterbium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Hafnium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Terbium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Polonium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Bromine.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Iodine.setProperty(PropertyKey.DUST, new DustProperty());
        Iodine.setProperty(PropertyKey.INGOT, new IngotProperty());
        Iodine.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Caesium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Curium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Curium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Radium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Tennessine.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Protactinium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Fermium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Uranium238.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Graphene.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        SiliconDioxide.setProperty(PropertyKey.INGOT, new IngotProperty());
        SiliconDioxide.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
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
        Tellurium.setProperty(PropertyKey.DUST, new DustProperty());
        Selenium.setProperty(PropertyKey.DUST, new DustProperty());
        Thallium.setProperty(PropertyKey.DUST, new DustProperty());
        Promethium.setProperty(PropertyKey.DUST, new DustProperty());

        Rhodium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Ruthenium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Praseodymium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Scandium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Gadolinium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Terbium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Dysprosium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Holmium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Erbium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Thulium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Ytterbium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Tellurium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Selenium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Rubidium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Thallium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Californium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        Promethium.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
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
        Bismuth.setProperty(PropertyKey.ORE, new OreProperty(1, 1, false));
        // Ingots
        Germanium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Rhenium.setProperty(PropertyKey.INGOT, new IngotProperty());

        // Blast
        Germanium.setProperty(PropertyKey.BLAST, new BlastProperty(1211));

        StainlessSteel.getProperty(PropertyKey.BLAST).setBlastTemperature(2700);
        // Fluids
        SodiumHydroxide.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        SodiumBisulfate.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Scandium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Dysprosium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Holmium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        AmmoniumChloride.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Promethium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
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
        TitanSteel.setProperty(PropertyKey.WIRE, new WireProperties((int) GTValues.V[GTValues.EV], 4, 2));

        /* -------------------------------- Element Materials with Plasma -------------------------------- */
        var fpPropertyBoron = new FluidProperty();
        fpPropertyBoron.enqueueRegistration(FluidStorageKeys.LIQUID, new FluidBuilder());
        fpPropertyBoron.enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());
        Boron.setProperty(PropertyKey.FLUID, fpPropertyBoron);

        var fpPropertyCalcium = new FluidProperty();
        fpPropertyCalcium.enqueueRegistration(FluidStorageKeys.LIQUID, new FluidBuilder());
        fpPropertyCalcium.enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());
        Calcium.setProperty(PropertyKey.FLUID, fpPropertyCalcium);

        Neon.getProperty(PropertyKey.FLUID)
                .enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());

        var fpPropertySulfur = new FluidProperty();
        fpPropertySulfur.enqueueRegistration(FluidStorageKeys.LIQUID, new FluidBuilder());
        fpPropertySulfur.enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());
        Sulfur.setProperty(PropertyKey.FLUID, fpPropertySulfur);

        Zinc.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());

        Niobium.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());

        Titanium.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());

        Krypton.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());

        Silver.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());

        Bismuth.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());

        Xenon.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());

        Radon.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());

        var fpPropertyFermium = new FluidProperty();
        fpPropertyFermium.enqueueRegistration(FluidStorageKeys.LIQUID, new FluidBuilder());
        fpPropertyFermium.enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());
        Fermium.setProperty(PropertyKey.FLUID, fpPropertyFermium);

        Plutonium241.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());

        Lead.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());

        Thorium.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());

        Hydrogen.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());

        var fpPropertyGermanium = new FluidProperty();
        fpPropertyGermanium.enqueueRegistration(FluidStorageKeys.LIQUID, new FluidBuilder());
        fpPropertyGermanium.enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());
        Germanium.setProperty(PropertyKey.FLUID, fpPropertyGermanium);

        Chrome.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());

        var fpPropertyGadolinium = new FluidProperty();
        fpPropertyGadolinium.enqueueRegistration(FluidStorageKeys.LIQUID, new FluidBuilder());
        fpPropertyGadolinium.enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());
        Gadolinium.setProperty(PropertyKey.FLUID, fpPropertyGadolinium);

        var fpPropertySodium = new FluidProperty();
        fpPropertySodium.enqueueRegistration(FluidStorageKeys.LIQUID, new FluidBuilder());
        fpPropertySodium.enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());
        Sodium.setProperty(PropertyKey.FLUID, fpPropertySodium);

        var fpPropertyRhenium = new FluidProperty();
        fpPropertyRhenium.enqueueRegistration(FluidStorageKeys.LIQUID, new FluidBuilder());
        fpPropertyRhenium.enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());
        Rhenium.setProperty(PropertyKey.FLUID, fpPropertyRhenium);

        var fpPropertyPraseodymium = new FluidProperty();
        fpPropertyPraseodymium.enqueueRegistration(FluidStorageKeys.LIQUID, new FluidBuilder());
        fpPropertyPraseodymium.enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());
        Praseodymium.setProperty(PropertyKey.FLUID, fpPropertyPraseodymium);

        var fpPropertyPolonium = new FluidProperty();
        fpPropertyPolonium.enqueueRegistration(FluidStorageKeys.LIQUID, new FluidBuilder());
        fpPropertyPolonium.enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());
        Polonium.setProperty(PropertyKey.FLUID, fpPropertyPolonium);

        var fpPropertyAstatine = new FluidProperty();
        fpPropertyAstatine.enqueueRegistration(FluidStorageKeys.LIQUID, new FluidBuilder());
        fpPropertyAstatine.enqueueRegistration(FluidStorageKeys.PLASMA, new FluidBuilder());
        Astatine.setProperty(PropertyKey.FLUID, fpPropertyAstatine);

        var fpNetherStar = new FluidProperty();
        fpNetherStar.enqueueRegistration(FluidStorageKeys.LIQUID, new FluidBuilder());
        NetherStar.setProperty(PropertyKey.FLUID, fpNetherStar);
    }
}