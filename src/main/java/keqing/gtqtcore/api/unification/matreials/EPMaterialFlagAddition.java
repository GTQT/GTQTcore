package keqing.gtqtcore.api.unification.matreials;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.CRYSTALLIZABLE;
import static keqing.gtqtcore.api.unification.material.info.EPMaterialFlags.*;

public class EPMaterialFlagAddition {

    public static void init() {
        //  Coils

        //  Motor coils
        //  Copper (LV), Cupronickel (MV), Electrum (HV), Kanthal (EV),
        //  Graphene (IV), Ruridit (LuV), Vanadium Gallium (ZPM), Americium (UV)
        //  Europium (UHV), Carbon Nanotube (UEV)

        Copper.addFlags(GENERATE_COIL);
        Cupronickel.addFlags(GENERATE_COIL);
        Electrum.addFlags(GENERATE_COIL);
        Kanthal.addFlags(GENERATE_COIL);
        Graphene.addFlags(GENERATE_COIL);
        Ruridit.addFlags(GENERATE_COIL);
        VanadiumGallium.addFlags(GENERATE_COIL);
        Americium.addFlags(GENERATE_COIL);
        Europium.addFlags(GENERATE_COIL);
        // CarbonNanotube.addFlags(GENERATE_COIL);

        //  Voltage coils
        //  Lead (ULV), Steel (LV), Aluminium (MV), Black Steel (HV),
        //  Tungsten Steel (EV), Iridium (IV), Osmiridium (LuV), Europium (ZPM),
        //  Tritanium (UV), Vibranium (UHV), Seaborgium (UEV)
        Lead.addFlags(GENERATE_COIL);
        Steel.addFlags(GENERATE_COIL);
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
        Iron.addFlags(GENERATE_CURVED_PLATE);
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
        // Polyethylene.addFlags(GENERATE_CURVED_PLATE);
        // Polybenzimidazole.addFlags(GENERATE_CURVED_PLATE);
        // Polytetrafluoroethylene.addFlags(GENERATE_CURVED_PLATE);
        Potin.addFlags(GENERATE_CURVED_PLATE);
        StainlessSteel.addFlags(GENERATE_CURVED_PLATE);
        Steel.addFlags(GENERATE_CURVED_PLATE);
        TinAlloy.addFlags(GENERATE_CURVED_PLATE);
        Titanium.addFlags(GENERATE_CURVED_PLATE);
        Tungsten.addFlags(GENERATE_CURVED_PLATE);
        TungstenCarbide.addFlags(GENERATE_CURVED_PLATE);
        TungstenSteel.addFlags(GENERATE_CURVED_PLATE);
        VanadiumSteel.addFlags(GENERATE_CURVED_PLATE);

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
        Americium.addFlags(GENERATE_CURVED_PLATE);
        Magnalium.addFlags(GENERATE_CURVED_PLATE);
        SterlingSilver.addFlags(GENERATE_CURVED_PLATE);
        Tin.addFlags(GENERATE_CURVED_PLATE);
        Cupronickel.addFlags(GENERATE_CURVED_PLATE);
        BlackBronze.addFlags(GENERATE_CURVED_PLATE);
        CobaltBrass.addFlags(GENERATE_CURVED_PLATE);
        Electrum.addFlags(GENERATE_CURVED_PLATE);
        Cobalt.addFlags(GENERATE_CURVED_PLATE);
        Platinum.addFlags(GENERATE_CURVED_PLATE);
        Brass.addFlags(GENERATE_CURVED_PLATE);
        Osmium.addFlags(GENERATE_CURVED_PLATE);
        Ultimet.addFlags(GENERATE_CURVED_PLATE);
        Osmiridium.addFlags(GENERATE_CURVED_PLATE);
        Nickel.addFlags(GENERATE_CURVED_PLATE);
        // PolyvinylChloride.addFlags(GENERATE_CURVED_PLATE);
        RoseGold.addFlags(GENERATE_CURVED_PLATE);

        //  Disable Crystallization
        Monazite.addFlags(DISABLE_CRYSTALLIZATION);

        //  Crystallizable
        Sapphire.addFlags(CRYSTALLIZABLE);
        Ruby.addFlags(CRYSTALLIZABLE);
        Emerald.addFlags(CRYSTALLIZABLE);
        Olivine.addFlags(CRYSTALLIZABLE);
        Amethyst.addFlags(CRYSTALLIZABLE);
        Opal.addFlags(CRYSTALLIZABLE);
    }
}
