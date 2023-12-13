package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.Elements;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.BlastProperty;
import gregtech.api.unification.material.properties.ToolProperty;
import keqing.gtqtcore.api.unification.GTQTElements;
import net.minecraft.init.Enchantments;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static gregtech.api.util.GTUtility.gregtechId;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.material.info.GTQTMaterialIconSet.*;

public class GTQTElementMaterials {

    private static int startId = 28000;
    private static final int END_ID = startId + 100;


    public static void register() {

        //  26001 Draconium
        Draconium = new Material.Builder(getMaterialsId(), gregtechId("draconium"))
                .ingot()
                .fluid()
                .color(0xbe49ed)
                .iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROTOR, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_FRAME, GENERATE_DENSE)
                .element(GTQTElements.Draconium)
                .toolStats(ToolProperty.Builder.of(7.0F, 25.0F, 17000, 22)
                        .magnetic()
                        .enchantment(Enchantments.EFFICIENCY, 5)
                        .enchantment(Enchantments.FORTUNE, 5)
                        .build())
                .blast(10800, BlastProperty.GasTier.HIGHER)
                .build();
        //  26002 Awakened Draconium
        AwakenedDraconium = new Material.Builder(getMaterialsId(), gregtechId("awakened_draconium"))
                .ingot()
                .fluid()
                .color(0xf58742)
                .iconSet(BRIGHT)
                .flags(GENERATE_PLATE, GENERATE_FOIL, GENERATE_FINE_WIRE)
                .element(GTQTElements.AwakenedDraconium)
                .blast(10800, BlastProperty.GasTier.HIGHER)
                .cableProperties(V[UHV], 16, 4)
                .build();
        //  26003 Chaotic Draconium
        ChaoticDraconium = new Material.Builder(getMaterialsId(), gregtechId("chaotic_draconium"))
                .ingot()
                .fluid()
                .color(0x2C195A)
                .iconSet(SHINY)
                .flags(NO_SMELTING, GENERATE_PLATE, GENERATE_DOUBLE_PLATE)
                .element(GTQTElements.ChaoticDraconium)
                .cableProperties(V[UEV], 32, 16)
                .build();

        //  26008 Plutonium-244
        Plutonium244 = new Material.Builder(getMaterialsId(), gregtechId("plutonium_244"))
                .ingot()
                .fluid()
                .color(0xF03232)
                .iconSet(SHINY)
                .element(GTQTElements.Plutonium244)
                .build();
        //  26009 Metastable Oganesson
        MetastableOganesson = new Material.Builder(getMaterialsId(), gregtechId("metastable_oganesson"))
                .ingot()
                .gas()
                .color(0xE61C24)
                .iconSet(SHINY)
                .element(Elements.Og)
                .flags(GENERATE_PLATE)
                .blast(10300, BlastProperty.GasTier.LOW)
                .build();
        //  TODO Radium-Radon Mixture + Scandium-Titanium-50 Mixture -> Metastable Hassium
        //  26010 Metastable Hassium
        MetastableHassium = new Material.Builder(getMaterialsId(), gregtechId("metastable_hassium"))
                .ingot()
                .fluid()
                .color(0x2D3A9D)
                .iconSet(BRIGHT)
                .element(Elements.Hs)
                .flags(GENERATE_ROD)
                .build();
        //  TODO Uranium-238 -> Quasi-fissioning Plasma -> Flerovium-Ytterbium Plasma -> Metastable Flerovium + Ytterbium-178
        //  26011 Metastable Flerovium
        MetastableFlerovium = new Material.Builder(getMaterialsId(), gregtechId("metastable_flerovium"))
                .ingot()
                .fluid()
                .color(0x521973)
                .iconSet(SHINY)
                .element(Elements.Fl)
                .flags(GENERATE_ROD)
                .build();
        //  26012 Cosmic Neutronium
        CosmicNeutronium = new Material.Builder(getMaterialsId(), gregtechId("cosmic_neutronium"))
                .ingot()
                .liquid(new FluidBuilder().temperature(2000000000))
                .color(0x323232)
                .iconSet(BRIGHT)
                .flags(NO_SMELTING, NO_SMASHING)
                .element(GTQTElements.CosmicNeutronium)
                .cableProperties(V[UIV], 256, 128, false)
                .build();
        //  26013 Degenerate Rhenium
        DegenerateRhenium = new Material.Builder(getMaterialsId(), gregtechId("degenerate_rhenium"))
                .dust()
                .color(0x6666FF)
                .iconSet(CUSTOM_DEGENERATE_RHENIUM)
                .element(Elements.Rh)
                .flags(GENERATE_PLATE)
                .build()
                .setFormula("§cR§de", false);

        //  26014 Infinity
        Infinity = new Material.Builder(getMaterialsId(), gregtechId("infinity"))
                .ingot()
                .liquid(new FluidBuilder().temperature((int) V[UIV]))
                .iconSet(CUSTOM_INFINITY)
                .element(GTQTElements.Infinity)
                .blast(12600, BlastProperty.GasTier.HIGHER)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR,GENERATE_ROTOR)
                .build();
        //  26015 Rhugnor
        Rhugnor = new Material.Builder(getMaterialsId(), gregtechId("rhugnor"))
                .ingot().dust()
                .liquid(new FluidBuilder().temperature((int) V[UIV]))
                .color(0xBE00FF)
                .iconSet(CUSTOM_INFINITY)
                .element(GTQTElements.Rhugnor)
                .blast(12800, BlastProperty.GasTier.HIGHER)
                .flags(GENERATE_PLATE)
                .build();
        //  26016 Hypogen
        Hypogen = new Material.Builder(getMaterialsId(), gregtechId("hypogen"))
                .ingot()
                .liquid(new FluidBuilder().temperature((int) (V[UXV] - V[LuV])))
                .element(GTQTElements.Hypogen)
                .color(0xDC784B)
                .iconSet(CUSTOM_HYPOGEN)
                .toolStats(ToolProperty.Builder.of(20.0F, 200.0F, 2000000, 200)
                        .unbreakable()
                        .enchantability(33)
                        .magnetic()
                        .enchantment(Enchantments.SHARPNESS, 10)
                        .enchantment(Enchantments.LOOTING, 5)
                        .enchantment(Enchantments.SWEEPING, 3).build())
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE)
                .build();
        //  26017 Californium-252
        Californium252 = new Material.Builder(getMaterialsId(), gregtechId("californium_252"))
                .ingot()
                .fluid()
                .iconSet(SHINY)
                .element(GTQTElements.Californium252)
                .color(Californium.getMaterialRGB())
                .build();
        //  26018 Astral Titanium
        AstralTitanium = new Material.Builder(getMaterialsId(), gregtechId("astral_titanium"))
                .ingot()
                .fluid()
                .color(0xDCA0F0)
                .iconSet(BRIGHT)
                //  TODO may be re-balance
                .blast(12800, BlastProperty.GasTier.HIGHER)
                .element(GTQTElements.AstralTitanium)
                .flags(GENERATE_PLATE, GENERATE_FOIL)
                .build();
        //  26019 Celestial Tungsten
        CelestialTungsten = new Material.Builder(getMaterialsId(), gregtechId("celestial_tungsten"))
                .ingot()
                .fluid()
                .color(0x323232)
                .iconSet(BRIGHT)
                //  TODO may be re-balance
                .blast(12800, BlastProperty.GasTier.HIGHER)
                .element(GTQTElements.CelestialTungsten)
                .flags(GENERATE_PLATE, GENERATE_FOIL, GENERATE_ROD, GENERATE_BOLT_SCREW)
                .build();
        //  26020 Ytterbium-178
        Ytterbium178 = new Material.Builder(getMaterialsId(), gregtechId("ytterbium_178"))
                .dust()
                .fluid()
                .color(Ytterbium.getMaterialRGB())
                .iconSet(SHINY)
                .element(GTQTElements.Ytterbium178)
                .build();
        //  26021 Ichorium
        Ichorium = new Material.Builder(getMaterialsId(), gregtechId("ichorium"))
                .ingot()
                .fluid()
                .color(0xE5A559)
                .iconSet(BRIGHT)
                .blast(12800, BlastProperty.GasTier.HIGHER)
                .element(GTQTElements.Ichorium)
                .flags(GENERATE_PLATE)
                .build();
        //  26022 Ichor Liquid
        IchorLiquid = new Material.Builder(getMaterialsId(), gregtechId("ichor_liquid"))
                .liquid(new FluidBuilder().temperature(214748))
                .color(0xE5A559)
                .element(GTQTElements.IchorLiquid)
                .build();
        //  26023 Crystal Matrix
        CrystalMatrix = new Material.Builder(getMaterialsId(), gregtechId("crystal_matrix"))
                .ingot()
                .liquid(new FluidBuilder().temperature(660450))
                .color(0x70ecff)
                .iconSet(BRIGHT)
                .element(GTQTElements.CrystalMatrix)
                .build();
        //  26024 Void Metal
        VoidMetal = new Material.Builder(getMaterialsId(), gregtechId("void_metal"))
                .ingot()
                .fluid()
                .color(0x20142C)
                .iconSet(DULL)
                .element(GTQTElements.VoidMetal)
                .build();
        //  26025 Mithril
        Mithril = new Material.Builder(getMaterialsId(), gregtechId("mithril"))
                .ingot()
                .liquid(new FluidBuilder().temperature(4450))
                .color(0x428fdb)
                .iconSet(DULL)
                .blast(10800, BlastProperty.GasTier.HIGHER)
                .element(GTQTElements.Mithril)
                .flags(GENERATE_PLATE, GENERATE_FOIL, GENERATE_FINE_WIRE)
                .build();
        //  26026 Bismuth-209
        Bismuth209 = new Material.Builder(getMaterialsId(), gregtechId("bismuth_209"))
                .fluid()
                .color(Bismuth.getMaterialRGB())
                .element(GTQTElements.Bismuth209)
                .build();
        //  26027 Lead-209
        Lead209 = new Material.Builder(getMaterialsId(), gregtechId("lead_209"))
                .fluid()
                .color(Lead.getMaterialRGB())
                .element(GTQTElements.Lead209)
                .build();
    }

    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }
}
