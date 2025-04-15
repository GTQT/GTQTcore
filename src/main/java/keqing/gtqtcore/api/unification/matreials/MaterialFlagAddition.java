package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.*;

import static gregicality.multiblocks.api.unification.GCYMMaterials.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.material.info.GTQTMaterialFlags.*;

public class MaterialFlagAddition {

    public static void init() {
        Material[] material = {
                Polyethylene, Carbon, Graphite, Invar, Osmium, Naquadah, Dubnium, Tritium, Rutherfordium, Rhodium, Rubidium,
                Lanthanum, Praseodymium, Neodymium, Cerium, Scandium, Europium, Gadolinium, Yttrium, Terbium, Dysprosium, Holmium, Erbium, Thulium, Ytterbium, Lutetium, Niobium, Palladium, Ruthenium, Naquadria, Samarium,
                Tritanium, Duranium, Technetium, Meitnerium, Roentgenium, Nobelium, Lawrencium, Moscovium, Lutetium, NaquadahEnriched, Trinium,
                Stellite100, WatertightSteel, MaragingSteel300, HastelloyC276, HastelloyX, Trinaquadalloy, Zeron100, TitaniumCarbide,
                Americium, TantalumCarbide, MolybdenumDisilicide, HSLASteel, TitaniumTungstenCarbide, IncoloyMA956
        };
        for (Material materials : material) {
            if (!materials.hasProperty(PropertyKey.INGOT))
                materials.setProperty(PropertyKey.INGOT, new IngotProperty());
            if (!materials.hasProperty(PropertyKey.ORE)) materials.setProperty(PropertyKey.ORE, new OreProperty());
            materials.addFlags(GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_FOIL, GENERATE_DENSE, GENERATE_FINE_WIRE, GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROTOR);
        }

        OreProperty oreProp = Molybdenite.getProperty(PropertyKey.ORE);
        oreProp.setDirectSmeltResult(null);

        oreProp = LeanGoldSulphide.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Copper, Iron, Sulfur);
        oreProp.setWashedIn(Mercury);
        oreProp.setDirectSmeltResult(Gold);

        oreProp = Thorium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Rutile, Zirconium, Uraninite);
        oreProp.setWashedIn(HydrofluoricAcid);
        oreProp.setDirectSmeltResult(Thorium);

        oreProp = RichGoldSulphide.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Copper, Iron, Sulfur);
        oreProp.setWashedIn(Mercury);
        oreProp.setDirectSmeltResult(Gold);

        oreProp = Pyrargyrite.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Copper, Iron, Antimony);
        oreProp.setWashedIn(SodiumPersulfate);
        oreProp.setDirectSmeltResult(Silver);

        oreProp = Zincantimonygalvanite.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Zinc, Sulfur, Tin);
        oreProp.setWashedIn(SodiumPersulfate);
        oreProp.setDirectSmeltResult(Silver);

        oreProp = Crocoite.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Lead, Chromite, Silver);
        oreProp.setWashedIn(SodiumPersulfate);
        oreProp.setDirectSmeltResult(Lead);

        oreProp = Amblygonite.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Alumina, Phosphate, Sulfur);
        oreProp.setWashedIn(SodiumPersulfate);
        oreProp.setDirectSmeltResult(Lithium);

        oreProp = Ulexite.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Borax, Calcium, Calcium);
        oreProp.setWashedIn(SodiumPersulfate);
        oreProp.setDirectSmeltResult(Sodium);

        oreProp = PreciousMetal.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(LeanGoldSulphide, RichGoldSulphide);
        oreProp.setWashedIn(SodiumPersulfate);
        oreProp.setDirectSmeltResult(PreciousMetal);


        oreProp = Americium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Lead, Uraninite, Plutonium239);
        oreProp.setWashedIn(HydrofluoricAcid);
        oreProp.setDirectSmeltResult(Americium);

        oreProp = Chrome.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Iron, Nickel, Sulfur);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Chrome);

        oreProp = Iridium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Platinum, Osmium, Sulfur);
        oreProp.setWashedIn(HydrofluoricAcid);
        oreProp.setDirectSmeltResult(Iridium);

        oreProp = Lutetium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Yttrium, Erbium, Sulfur);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Lutetium);
//
        oreProp = Manganese.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Iron, Silicon, Sulfur);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Manganese);

        oreProp = Niobium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Tantalum, Iron, Sulfur);
        oreProp.setWashedIn(HydrofluoricAcid);
        oreProp.setDirectSmeltResult(Niobium);

        oreProp = Osmium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Iridium, Platinum, Sulfur);
        oreProp.setWashedIn(HydrofluoricAcid);
        oreProp.setDirectSmeltResult(Osmium);

        oreProp = Titanium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Iron, Aluminium, Sulfur);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Titanium);

        oreProp = Tungsten.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Molybdenum, Iron, Sulfur);
        oreProp.setWashedIn(HydrofluoricAcid);
        oreProp.setDirectSmeltResult(Tungsten);
//
        oreProp = Uranium235.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Lead, Thorium, Sulfur);
        oreProp.setWashedIn(HydrofluoricAcid);
        oreProp.setDirectSmeltResult(Uranium235);

        oreProp = Neutronium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Neutronium, Trinium, Sulfur);
        oreProp.setWashedIn(HydrofluoricAcid);
        oreProp.setDirectSmeltResult(Neutronium);

        oreProp = Trinium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Naquadah, Neutronium, Sulfur);
        oreProp.setWashedIn(HydrofluoricAcid);
        oreProp.setDirectSmeltResult(Trinium);

        oreProp = Electrum.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Gold, Silver, Sulfur);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Electrum);

        oreProp = Gallium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Aluminium, Zinc, Sulfur);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Gallium);

        oreProp = Bismuth.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Lead, Antimony, Sulfur);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Bismuth);

        oreProp = Rhodium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Platinum, Palladium, Sulfur);
        oreProp.setWashedIn(HydrofluoricAcid);
        oreProp.setDirectSmeltResult(Rhodium);

        oreProp = Ruthenium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Platinum, Osmium, Sulfur);
        oreProp.setWashedIn(HydrofluoricAcid);
        oreProp.setDirectSmeltResult(Ruthenium);
//
        oreProp = Praseodymium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Neodymium, Lanthanum, Sulfur);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Praseodymium);

        oreProp = Scandium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Yttrium, Lanthanum, Sulfur);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Scandium);

        oreProp = Gadolinium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Yttrium, Terbium, Sulfur);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Gadolinium);

        oreProp = Terbium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Yttrium, Dysprosium, Sulfur);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Terbium);

        oreProp = Dysprosium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Yttrium, Holmium, Sulfur);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Dysprosium);

        oreProp = Holmium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Yttrium, Erbium, Sulfur);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Holmium);

        oreProp = Erbium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Yttrium, Thulium, Sulfur);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Erbium);

        oreProp = Thulium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Yttrium, Ytterbium, Sulfur);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Thulium);

        oreProp = Ytterbium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Yttrium, Lutetium, Sulfur);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Ytterbium);

        oreProp = Tellurium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Selenium, Sulfur, Copper);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Tellurium);

        oreProp = Selenium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Tellurium, Sulfur, Copper);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Selenium);

        oreProp = Rubidium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Potassium, Caesium, Sulfur);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Rubidium);

        oreProp = Thallium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Lead, Bismuth, Sulfur);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Thallium);

        oreProp = Californium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Uraninite, Plutonium239, Sulfur);
        oreProp.setWashedIn(HydrofluoricAcid);
        oreProp.setDirectSmeltResult(Californium);

        oreProp = Promethium.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Neodymium, Lanthanum, Sulfur);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(Promethium);
        /*
         */
        //  Coils
        //  Motor coils
        //  Copper (LV), Cupronickel (MV), Electrum (HV), Kanthal (EV),
        //  Graphene (IV), Ruridit (LuV), Vanadium Gallium (ZPM), Americium (UV)
        //  Europium (UHV), Carbon Nanotube (UEV)
        HSSG.addFlags(GENERATE_RING);
        Osmiridium.addFlags(GENERATE_FRAME);
        BlueAlloy.addFlags(GENERATE_LONG_ROD);
        RhodiumPlatedPalladium.addFlags(GENERATE_SPRING);
        Invar.addFlags(GENERATE_DOUBLE_PLATE);
        Polybenzimidazole.addFlags(GENERATE_FRAME);
        Aluminium.addFlags(GENERATE_ROTOR);
        Copper.addFlags(GENERATE_COIL);
        Cupronickel.addFlags(GENERATE_COIL);
        Electrum.addFlags(GENERATE_COIL);
        Kanthal.addFlags(GENERATE_COIL);
        Graphene.addFlags(GENERATE_COIL);
        Ruridit.addFlags(GENERATE_COIL);
        VanadiumGallium.addFlags(GENERATE_COIL);
        Americium.addFlags(GENERATE_COIL);
        Europium.addFlags(GENERATE_COIL);
        Naquadah.addFlags(GENERATE_FRAME);
        Coal.addFlags(GENERATE_DENSE, GENERATE_PLATE, GENERATE_DOUBLE_PLATE);
        RedSteel.addFlags(GENERATE_BOULE, GENERATE_FRAME);
        Naquadria.addFlags(GENERATE_FRAME);
        NaquadahEnriched.addFlags(GENERATE_FRAME);
        Tritanium.addFlags(GENERATE_FRAME);
        Orichalcum.addFlags(GENERATE_FRAME);
        Vanadium.addFlags(GENERATE_PLATE);
        Bohrium.addFlags(GENERATE_PLATE);
        NaquadahEnriched.addFlags(GENERATE_BOLT_SCREW);
        // CarbonNanotube.addFlags(GENERATE_COIL);

        //  Voltage coils
        //  Lead (ULV), Steel (LV), Aluminium (MV), Black Steel (HV),
        //  Tungsten Steel (EV), Iridium (IV), Osmiridium (LuV), Europium (ZPM),
        //  Tritanium (UV), Vibranium (UHV), Seaborgium (UEV)
        Lead.addFlags(GENERATE_COIL);
        Steel.addFlags(GENERATE_COIL, GENERATE_DENSE);
        Aluminium.addFlags(GENERATE_COIL);
        BlackSteel.addFlags(GENERATE_COIL);
        TungstenSteel.addFlags(GENERATE_COIL);
        Iridium.addFlags(GENERATE_COIL);
        Osmiridium.addFlags(GENERATE_COIL);
        // Europium.addFlags(GENERATE_COIL);
        Tritanium.addFlags(GENERATE_COIL);
        // Vibranium.addFlags(GENERATE_COIL);
        Seaborgium.addFlags(GENERATE_COIL);

        //  Curved plates

        //  Rotors
        Iron.addFlags(GENERATE_CURVED_PLATE, GENERATE_DENSE);
        WroughtIron.addFlags(GENERATE_CURVED_PLATE);
        Darmstadtium.addFlags(GENERATE_CURVED_PLATE);
        RhodiumPlatedPalladium.addFlags(GENERATE_CURVED_PLATE);
        NaquadahAlloy.addFlags(GENERATE_CURVED_PLATE);
        HSSS.addFlags(GENERATE_CURVED_PLATE);
        //  HastelloyN.addFlags(GENERATE_CURVED_PLATE);
        //  Draconium.addFlags(GENERATE_CURVED_PLATE);
        //  Adamantium.addFlags(GENERATE_CURVED_PLATE);
        //  Dawnstone.addFlags(GENERATE_CURVED_PLATE);

        /*
         *  CEu Vanilla Fluid Pipes
         *  Aluminium
         *  Bronze
         *  Chrome
         *  Copper
         *  Duranium
         *  Europium
         *  Gold
         *  Iridium
         *  Lead
         *  Naquadah
         *  Neutronium
         *  NiobiumTitanium
         *  Polyethylene            (x)
         *  Polybenzimidazole       (x)
         *  Polytetrafluoroethylene (x)
         *  Potin
         *  Stainless Steel
         *  Steel
         *  Tin Alloy
         *  Titanium
         *  Tungsten
         *  Tungsten Carbide
         *  TungstenSteel
         *  VanadiumSteel
         */
        Aluminium.addFlags(GENERATE_CURVED_PLATE);
        Bronze.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_DENSE, GENERATE_SMALL_GEAR);
        Chrome.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR);
        Copper.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_DENSE, GENERATE_SMALL_GEAR);
        Duranium.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR);
        Europium.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR);
        Gold.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR);
        Iridium.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR);
        Lead.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_DENSE, GENERATE_SMALL_GEAR);
        Naquadah.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR);
        Neutronium.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR);
        NiobiumTitanium.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR);
        // Polyethylene.addFlags(GENERATE_CURVED_PLATE);
        // Polybenzimidazole.addFlags(GENERATE_CURVED_PLATE);
        // Polytetrafluoroethylene.addFlags(GENERATE_CURVED_PLATE);
        Potin.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL);
        StainlessSteel.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL);
        Steel.addFlags(GENERATE_CURVED_PLATE);
        TinAlloy.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR);
        Titanium.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR);
        Tungsten.addFlags(GENERATE_CURVED_PLATE);
        TungstenCarbide.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR);
        TungstenSteel.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR);
        VanadiumSteel.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR);
        /*
         *  CEu Vanilla Item Pipes
         *  Americium
         *  Magnalium
         *  Sterling Silver
         *  Tin
         *  Cupronickel
         *  Black Bronze
         *  Cobalt Brass
         *  Electrum
         *  Cobalt
         *  Platinum
         *  Brass
         *  Osmium
         *  Ultimet
         *  Osmiridium
         *  Nickel
         *  Polyvinyl Chloride (x)
         *  Rose Gold
         */
        Americium.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_GEAR);
        Magnalium.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_GEAR);
        SterlingSilver.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_GEAR);
        Tin.addFlags(GENERATE_CURVED_PLATE, GENERATE_DENSE, GENERATE_SMALL_GEAR, GENERATE_GEAR);
        Cupronickel.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_GEAR);
        BlackBronze.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_GEAR);
        CobaltBrass.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_GEAR);
        Electrum.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_GEAR);
        Platinum.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_GEAR);
        Brass.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_GEAR);
        Osmium.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_GEAR, GENERATE_FINE_WIRE);
        Ultimet.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_GEAR);
        Osmiridium.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR, GENERATE_GEAR);
        Nickel.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_DENSE, GENERATE_SMALL_GEAR, GENERATE_GEAR);
        Cobalt.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_DENSE, GENERATE_SMALL_GEAR, GENERATE_GEAR);
        // PolyvinylChloride.addFlags(GENERATE_CURVED_PLATE);
        RoseGold.addFlags(GENERATE_CURVED_PLATE, GENERATE_SPRING, GENERATE_SPRING_SMALL);
        Copper.addFlags(GENERATE_RING);
        Cobalt.addFlags(GENERATE_RING);
        Tungsten.addFlags(GENERATE_RING, GENERATE_SMALL_GEAR, GENERATE_GEAR);
        Osmium.addFlags(GENERATE_RING);
        Nickel.addFlags(GENERATE_RING);
        Naquadah.addFlags(GENERATE_RING);
        Brass.addFlags(GENERATE_RING);
        Cupronickel.addFlags(GENERATE_RING);
        Magnalium.addFlags(GENERATE_RING);
        NiobiumTitanium.addFlags(GENERATE_RING);
        SterlingSilver.addFlags(GENERATE_RING);
        BlackBronze.addFlags(GENERATE_RING);
        RoseGold.addFlags(GENERATE_SMALL_GEAR);
        TinAlloy.addFlags(GENERATE_RING);
        Ultimet.addFlags(GENERATE_RING);
        Potin.addFlags(GENERATE_RING, GENERATE_SMALL_GEAR, GENERATE_GEAR, GENERATE_FRAME);
        VanadiumSteel.addFlags(GENERATE_RING);
        CobaltBrass.addFlags(GENERATE_RING);
        TungstenCarbide.addFlags(GENERATE_RING);
        //塑料
        CarbonNanotube.addFlags(GENERATE_WRAP);
        Asbestos.addFlags(GENERATE_WRAP);
        Wool.addFlags(GENERATE_WRAP);
        Polyethylene.addFlags(GENERATE_WRAP, GENERATE_RING);
        PolyvinylChloride.addFlags(GENERATE_WRAP, GENERATE_RING);
        Epoxy.addFlags(GENERATE_WRAP, GENERATE_RING);
        ReinforcedEpoxyResin.addFlags(GENERATE_WRAP, GENERATE_RING);
        Polytetrafluoroethylene.addFlags(GENERATE_WRAP, GENERATE_RING);
        Zylon.addFlags(GENERATE_WRAP, GENERATE_RING);
        Polybenzimidazole.addFlags(GENERATE_WRAP, GENERATE_RING);
        Polyetheretherketone.addFlags(GENERATE_WRAP, GENERATE_RING);
        Kevlar.addFlags(GENERATE_WRAP, GENERATE_RING);
        KaptonK.addFlags(GENERATE_WRAP, GENERATE_RING);
        KaptonE.addFlags(GENERATE_WRAP, GENERATE_RING);
        FullerenePolymerMatrix.addFlags(GENERATE_WRAP, GENERATE_RING);

        //  Disable Crystallization
        Monazite.addFlags(DISABLE_CRYSTALLIZATION);
        LeadZirconateTitanate.addFlags(GENERATE_LENS);
        //  Crystallizable
        Sapphire.addFlags(CRYSTALLIZABLE);
        Ruby.addFlags(CRYSTALLIZABLE);
        Emerald.addFlags(CRYSTALLIZABLE);
        Olivine.addFlags(CRYSTALLIZABLE);
        Amethyst.addFlags(CRYSTALLIZABLE);
        Opal.addFlags(CRYSTALLIZABLE);
        Silicon.addFlags(GENERATE_DENSE);
        RedAlloy.addFlags(GENERATE_RING, GENERATE_GEAR);
        Carbon.addFlags(GENERATE_ROD);
        Graphite.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Magnesium.addFlags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROTOR, GENERATE_ROD, GENERATE_SPRING, GENERATE_GEAR, GENERATE_FRAME);

        NetherStar.addFlags(CRYSTALLIZABLE);
        Copper.addFlags(GENERATE_BOLT_SCREW);
        WroughtIron.addFlags(GENERATE_ROTOR, GENERATE_SMALL_GEAR, GENERATE_GEAR);
        Rhenium.addFlags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE);
        Nickel.addFlags(GENERATE_FOIL);
        Titanium.addFlags(GENERATE_FOIL);
        Germanium.addFlags(GENERATE_FOIL);
        Tungsten.addFlags(GENERATE_FINE_WIRE);
        RhodiumPlatedPalladium.addFlags(GENERATE_FRAME, GENERATE_GEAR, GENERATE_FOIL);
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

        // Formula Changes
        PalladiumRaw.setFormula("PdCl2", true);
        RarestMetalMixture.setFormula("IrOs?", true);
        IridiumMetalResidue.setFormula("Ir2O3", true);

        // Disable Decomposition
        Pyrochlore.addFlags(DISABLE_DECOMPOSITION);
        Tantalite.addFlags(DISABLE_DECOMPOSITION);

        Molybdenite.addFlags(DISABLE_DECOMPOSITION);

        Powellite.addFlags(DISABLE_DECOMPOSITION);
        Wulfenite.addFlags(DISABLE_DECOMPOSITION);

        RockSalt.addFlags(DISABLE_DECOMPOSITION); // Conflict between Potassium Hydroxide and Rock Salt Electrolysis
        Salt.addFlags(DISABLE_DECOMPOSITION); // Conflict between Sodium Chlorate and Salt Electrolysis
        Pollucite.addFlags(DISABLE_DECOMPOSITION); // for rubidium chain

        // Disable Crystallization
        Monazite.addFlags(DISABLE_CRYSTALLIZATION);

        // Crystallizable
        Sapphire.addFlags(CRYSTALLIZABLE);
        Ruby.addFlags(CRYSTALLIZABLE);
        Emerald.addFlags(CRYSTALLIZABLE);
        Olivine.addFlags(CRYSTALLIZABLE);
        Amethyst.addFlags(CRYSTALLIZABLE);
        Opal.addFlags(CRYSTALLIZABLE);

        // Plates
        Germanium.addFlags(GENERATE_PLATE);
        Rhenium.addFlags(GENERATE_PLATE);

        // Rods
        Darmstadtium.addFlags(GENERATE_ROD);

        // Springs
        Trinium.addFlags(GENERATE_SPRING);
        Tritanium.addFlags(GENERATE_SPRING);

        // Small Springs
        Europium.addFlags(GENERATE_SPRING_SMALL);

        // Frames
        Darmstadtium.addFlags(GENERATE_FRAME);

        // Foils
        Nickel.addFlags(GENERATE_FOIL);
        Titanium.addFlags(GENERATE_FOIL);
        Germanium.addFlags(GENERATE_FOIL);

        RhodiumPlatedPalladium.setProperty(PropertyKey.FLUID_PIPE,
                new FluidPipeProperties(6200, 200,
                        true, true, true, false));

        NaquadahAlloy.setProperty(PropertyKey.FLUID_PIPE,
                new FluidPipeProperties(8000, 250,
                        true, true, true, true));

        Darmstadtium.setProperty(PropertyKey.FLUID_PIPE,
                new FluidPipeProperties(9600, 300,
                        true, true, true, true));

    }
}
