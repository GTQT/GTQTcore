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

        oreProp = RheniumMolybdenite.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Molybdenum, Rhenium, Sulfur);
        oreProp.setWashedIn(Water);
        oreProp.setDirectSmeltResult(RheniumMolybdenite);

        oreProp = Xenotime.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Yttrium, Phosphate, RareEarth);
        oreProp.setWashedIn(Mercury);
        oreProp.setDirectSmeltResult(Xenotime);

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

       
        Aluminium.addFlags(GENERATE_CURVED_PLATE);
        Bronze.addFlags(GENERATE_CURVED_PLATE);
        Chrome.addFlags(GENERATE_CURVED_PLATE);
        Copper.addFlags(GENERATE_CURVED_PLATE);
        Duranium.addFlags(GENERATE_CURVED_PLATE);
        Europium.addFlags(GENERATE_CURVED_PLATE);
        Gold.addFlags(GENERATE_CURVED_PLATE);
        Iridium.addFlags(GENERATE_CURVED_PLATE);
        Lead.addFlags(GENERATE_CURVED_PLATE);
        Naquadah.addFlags(GENERATE_CURVED_PLATE);
        Neutronium.addFlags(GENERATE_CURVED_PLATE);
        NiobiumTitanium.addFlags(GENERATE_CURVED_PLATE);
        Potin.addFlags(GENERATE_CURVED_PLATE);
        StainlessSteel.addFlags(GENERATE_CURVED_PLATE);
        Steel.addFlags(GENERATE_CURVED_PLATE);
        TinAlloy.addFlags(GENERATE_CURVED_PLATE);
        Titanium.addFlags(GENERATE_CURVED_PLATE);
        Tungsten.addFlags(GENERATE_CURVED_PLATE);
        TungstenCarbide.addFlags(GENERATE_CURVED_PLATE);
        TungstenSteel.addFlags(GENERATE_CURVED_PLATE);
        VanadiumSteel.addFlags(GENERATE_CURVED_PLATE);
        Americium.addFlags(GENERATE_CURVED_PLATE);
        Magnalium.addFlags(GENERATE_CURVED_PLATE);
        SterlingSilver.addFlags(GENERATE_CURVED_PLATE);
        Tin.addFlags(GENERATE_CURVED_PLATE);
        Cupronickel.addFlags(GENERATE_CURVED_PLATE);
        BlackBronze.addFlags(GENERATE_CURVED_PLATE);
        CobaltBrass.addFlags(GENERATE_CURVED_PLATE);
        Electrum.addFlags(GENERATE_CURVED_PLATE);
        Platinum.addFlags(GENERATE_CURVED_PLATE);
        Brass.addFlags(GENERATE_CURVED_PLATE);
        Osmium.addFlags(GENERATE_CURVED_PLATE);
        Ultimet.addFlags(GENERATE_CURVED_PLATE);
        Osmiridium.addFlags(GENERATE_CURVED_PLATE);
        Nickel.addFlags(GENERATE_CURVED_PLATE);
        Cobalt.addFlags(GENERATE_CURVED_PLATE);
        RoseGold.addFlags(GENERATE_CURVED_PLATE);

        
        //塑料
        CarbonNanotube.addFlags(GENERATE_WRAP);
        Asbestos.addFlags(GENERATE_WRAP);
        Wool.addFlags(GENERATE_WRAP);
        Polyethylene.addFlags(GENERATE_WRAP);
        PolyvinylChloride.addFlags(GENERATE_WRAP);
        Epoxy.addFlags(GENERATE_WRAP);
        ReinforcedEpoxyResin.addFlags(GENERATE_WRAP);
        Polytetrafluoroethylene.addFlags(GENERATE_WRAP);
        Zylon.addFlags(GENERATE_WRAP);
        Polybenzimidazole.addFlags(GENERATE_WRAP);
        Polyetheretherketone.addFlags(GENERATE_WRAP);
        Kevlar.addFlags(GENERATE_WRAP);
        KaptonK.addFlags(GENERATE_WRAP);
        KaptonE.addFlags(GENERATE_WRAP);
        FullerenePolymerMatrix.addFlags(GENERATE_WRAP);

        //  Disable Crystallization
        Monazite.addFlags(DISABLE_CRYSTALLIZATION);


        // Crystallizable
        Sapphire.addFlags(CRYSTALLIZABLE);
        Ruby.addFlags(CRYSTALLIZABLE);
        Emerald.addFlags(CRYSTALLIZABLE);
        Olivine.addFlags(CRYSTALLIZABLE);
        Amethyst.addFlags(CRYSTALLIZABLE);
        Opal.addFlags(CRYSTALLIZABLE);
        Sapphire.addFlags(CRYSTALLIZABLE);
        Ruby.addFlags(CRYSTALLIZABLE);
        Emerald.addFlags(CRYSTALLIZABLE);
        Olivine.addFlags(CRYSTALLIZABLE);
        Amethyst.addFlags(CRYSTALLIZABLE);
        Opal.addFlags(CRYSTALLIZABLE);
        NetherStar.addFlags(CRYSTALLIZABLE);

        // Disable Decomposition
        Pyrochlore.addFlags(DISABLE_DECOMPOSITION);
        Tantalite.addFlags(DISABLE_DECOMPOSITION);
        Molybdenite.addFlags(DISABLE_DECOMPOSITION);
        Powellite.addFlags(DISABLE_DECOMPOSITION);
        Wulfenite.addFlags(DISABLE_DECOMPOSITION);
        RockSalt.addFlags(DISABLE_DECOMPOSITION); // Conflict between Potassium Hydroxide and Rock Salt Electrolysis
        Salt.addFlags(DISABLE_DECOMPOSITION); // Conflict between Sodium Chlorate and Salt Electrolysis
        Pollucite.addFlags(DISABLE_DECOMPOSITION); // for rubidium chain
    }
}
