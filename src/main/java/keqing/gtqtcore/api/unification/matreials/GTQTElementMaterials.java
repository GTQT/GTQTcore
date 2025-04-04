package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.Elements;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.BlastProperty;
import gregtech.api.unification.material.properties.ToolProperty;
import keqing.gtqtcore.api.unification.GCYSMaterials;
import keqing.gtqtcore.api.unification.GTQTElements;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import net.minecraft.init.Enchantments;
import net.minecraft.util.text.TextFormatting;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static gregtech.api.util.GTUtility.gregtechId;
import static keqing.gtqtcore.api.unification.GTQTElements.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.material.info.GTQTMaterialIconSet.*;

public class GTQTElementMaterials {

    private static int startId = 28000;
    private static final int END_ID = startId + 100;


    public static void register() {
        //  26001 Draconium
        GTQTMaterials.Draconium = new Material.Builder(getMaterialsId(), gregtechId("draconium"))
                .ingot()
                .fluid()
                .color(0xbe49ed)
                .iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROTOR, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_FRAME, GENERATE_DENSE)
                .element(GTQTElements.Draconium)
                .toolStats(ToolProperty.Builder.of(7.0F, 25.0F, 1700000, 8)
                        .magnetic()
                        .enchantment(Enchantments.EFFICIENCY, 5)
                        .enchantment(Enchantments.FORTUNE, 5)
                        .build())
                .blast(10800, BlastProperty.GasTier.HIGHER)
                .build();
        //  26002 Awakened Draconium
        GTQTMaterials.AwakenedDraconium = new Material.Builder(getMaterialsId(), gregtechId("awakened_draconium"))
                .ingot()
                .fluid()
                .color(0xf58742)
                .iconSet(BRIGHT)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 10.0f, 20000)
                .toolStats(ToolProperty.Builder.of(10.0F, 30.0F, 2800000, 9)
                        .attackSpeed(0.1F).enchantability(21)
                        .unbreakable()
                        .enchantability(33)
                        .magnetic()
                        .enchantment(Enchantments.EFFICIENCY, 5)
                        .enchantment(Enchantments.FORTUNE, 5)
                        .build())

                .element(GTQTElements.AwakenedDraconium)
                .blast(10800, BlastProperty.GasTier.HIGHER)
                .cableProperties(V[UHV], 16, 4)
                .build();
        //  26003 Chaotic Draconium
        GTQTMaterials.ChaoticDraconium = new Material.Builder(getMaterialsId(), gregtechId("chaotic_draconium"))
                .ingot()
                .fluid()
                .color(0x2C195A)
                .iconSet(SHINY)
                .flags(GENERATE_FOIL,GENERATE_DENSE,GENERATE_FINE_WIRE,GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR,GENERATE_ROTOR)
                .element(GTQTElements.ChaoticDraconium)
                .cableProperties(V[UEV], 32, 16)
                .build();

        //  26009 Metastable Oganesson
        MetastableOganesson = new Material.Builder(getMaterialsId(), gregtechId("metastable_oganesson"))
                .ingot()
                .gas()
                .plasma()
                .color(0xE61C24)
                .iconSet(SHINY)
                .element(Elements.Og)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR,GENERATE_ROTOR)
                .blast(10300, BlastProperty.GasTier.LOW)
                .build();
        //  TODO Radium-Radon Mixture + Scandium-Titanium-50 Mixture -> Metastable Hassium
        //  26010 Metastable Hassium
        MetastableHassium = new Material.Builder(getMaterialsId(), gregtechId("metastable_hassium"))
                .ingot()
                .fluid()
                .color(0x2D3A9D)
                .iconSet(BRIGHT)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR,GENERATE_ROTOR)
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
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR,GENERATE_ROTOR)
                .element(Elements.Fl)
                .flags(GENERATE_ROD)
                .build();
        //  26012 Cosmic Neutronium
        GTQTMaterials.CosmicNeutronium = new Material.Builder(getMaterialsId(), gregtechId("cosmic_neutronium"))
                .ingot()
                .liquid(new FluidBuilder().temperature(2000000000))
                .color(0x323232)
                .iconSet(BRIGHT)
                .flags(NO_SMELTING, NO_SMASHING,GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR,GENERATE_ROTOR)
                .toolStats(ToolProperty.Builder.of(40.0F, 20.0F, 2560000, 7)
                        .attackSpeed(0.1F).enchantability(21)
                        .unbreakable()
                        .enchantability(33)
                        .magnetic()
                        .enchantment(Enchantments.EFFICIENCY, 3)
                        .enchantment(Enchantments.FORTUNE, 2)
                        .enchantment(Enchantments.SHARPNESS, 2).build())
                .element(GTQTElements.CosmicNeutronium)
                .cableProperties(V[UIV], 256, 128, false)
                .build();
        //  26013 Degenerate Rhenium
        DegenerateRhenium = new Material.Builder(getMaterialsId(), gregtechId("degenerate_rhenium"))
                .dust()
                .liquid()
                .plasma(new FluidBuilder().temperature((int) V[UV]))
                .color(0x6666FF)
                .iconSet(CUSTOM_DEGENERATE_RHENIUM)
                .element(Elements.Rh)
                .flags(GENERATE_PLATE)
                .build()
                .setFormula("§cR§de", false);

        //  26014 Infinity
        GTQTMaterials.Infinity = new Material.Builder(getMaterialsId(), gregtechId("infinity"))
                .ingot()
                .liquid(new FluidBuilder().temperature((int) V[UIV]))
                .iconSet(CUSTOM_INFINITY)
                .element(GTQTElements.Infinity)
                .blast(12600, BlastProperty.GasTier.HIGHER)
                .rotorStats(18.0f, 10.0f, 128000)
                .toolStats(ToolProperty.Builder.of(40.0F, 20.0F, 51200000, 8)
                        .attackSpeed(0.1F).enchantability(21)
                        .unbreakable()
                        .enchantability(33)
                        .magnetic()
                        .enchantment(Enchantments.EFFICIENCY, 5)
                        .enchantment(Enchantments.FORTUNE, 4)
                        .enchantment(Enchantments.SHARPNESS, 3).build())
                .flags(GENERATE_FOIL,GENERATE_DENSE,GENERATE_FINE_WIRE,GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR,GENERATE_ROTOR)
                .build();
        //  26015 Rhugnor
        GTQTMaterials.Rhugnor = new Material.Builder(getMaterialsId(), gregtechId("rhugnor"))
                .ingot().dust()
                .liquid(new FluidBuilder().temperature((int) V[UIV]))
                .color(0xBE00FF)
                .iconSet(SHINY)
                .element(GTQTElements.Rhugnor)
                .blast(12800, BlastProperty.GasTier.HIGHER)
                .flags(GENERATE_PLATE)
                .rotorStats(18.0f, 10.0f, 64000)
                .toolStats(ToolProperty.Builder.of(40.0F, 20.0F, 2560000, 8)
                        .attackSpeed(0.1F).enchantability(21).build())
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR,GENERATE_ROTOR)
                .build();
        //  26016 Hypogen
        GTQTMaterials.Hypogen = new Material.Builder(getMaterialsId(), gregtechId("hypogen"))
                .ingot()
                .liquid(new FluidBuilder().temperature((int) (V[UXV] - V[LuV])))
                .element(GTQTElements.Hypogen)
                .color(0xDC784B)
                .iconSet(CUSTOM_HYPOGEN)
                .cableProperties(V[UXV], 32, 16, false)
                .toolStats(ToolProperty.Builder.of(20.0F, 200.0F, 200000000, 200)
                        .unbreakable()
                        .enchantability(33)
                        .magnetic()
                        .enchantment(Enchantments.EFFICIENCY, 8)
                        .enchantment(Enchantments.FORTUNE, 5)
                        .enchantment(Enchantments.SHARPNESS, 10)
                        .enchantment(Enchantments.LOOTING, 5)
                        .enchantment(Enchantments.SWEEPING, 3).build())
                .flags(GENERATE_FRAME,GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD)
                .build();
        //  26017 Californium-252
        GTQTMaterials.Californium252 = new Material.Builder(getMaterialsId(), gregtechId("californium_252"))
                .ingot()
                .fluid()
                .iconSet(SHINY)
                .element(GTQTElements.Californium252)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR,GENERATE_ROTOR)
                .color(Californium.getMaterialRGB())
                .build();
        //  26018 Astral Titanium
        GTQTMaterials.AstralTitanium = new Material.Builder(getMaterialsId(), gregtechId("astral_titanium"))
                .ingot()
                .fluid()
                .color(0xDCA0F0)
                .iconSet(BRIGHT)
                //  TODO may be re-balance
                .blast(12800, BlastProperty.GasTier.HIGHER)
                .element(GTQTElements.AstralTitanium)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR,GENERATE_ROTOR)
                .build();
        //  26019 Celestial Tungsten
        GTQTMaterials.CelestialTungsten = new Material.Builder(getMaterialsId(), gregtechId("celestial_tungsten"))
                .ingot()
                .fluid()
                .color(0x323232)
                .iconSet(BRIGHT)
                //  TODO may be re-balance
                .blast(12800, BlastProperty.GasTier.HIGHER)
                .element(GTQTElements.CelestialTungsten)
                .rotorStats(18.0f, 10.0f, 64000)
                .toolStats(ToolProperty.Builder.of(40.0F, 20.0F, 1280000, 8)
                        .attackSpeed(0.1F).enchantability(21).build())
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR,GENERATE_ROTOR)

                .build();
        //  26020 Ytterbium-178
        GTQTMaterials.Ytterbium178 = new Material.Builder(getMaterialsId(), gregtechId("ytterbium_178"))
                .dust()
                .fluid()
                .color(Ytterbium.getMaterialRGB())
                .iconSet(SHINY)
                .element(GTQTElements.Ytterbium178)
                .rotorStats(18.0f, 10.0f, 64000)
                .toolStats(ToolProperty.Builder.of(40.0F, 20.0F, 1280000, 8)
                        .attackSpeed(0.1F).enchantability(21).build())
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR,GENERATE_ROTOR)

                .build();
        //  26021 Ichorium
        GTQTMaterials.Ichorium = new Material.Builder(getMaterialsId(), gregtechId("ichorium"))
                .ingot()
                .fluid()
                .color(0xE5A559)
                .iconSet(BRIGHT)
                .blast(12800, BlastProperty.GasTier.HIGHER)
                .element(GTQTElements.Ichorium)
                .rotorStats(18.0f, 10.0f, 64000)
                .toolStats(ToolProperty.Builder.of(40.0F, 20.0F, 1280000, 8)
                        .attackSpeed(0.1F).enchantability(21).build())
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR,GENERATE_ROTOR)

                .build();
        //  26022 Ichor Liquid
        GTQTMaterials.IchorLiquid = new Material.Builder(getMaterialsId(), gregtechId("ichor_liquid"))
                .liquid(new FluidBuilder().temperature(214748))
                .color(0xE5A559)
                .element(GTQTElements.IchorLiquid)
                .build();
        //  26023 Crystal Matrix
        GTQTMaterials.CrystalMatrix = new Material.Builder(getMaterialsId(), gregtechId("crystal_matrix"))
                .ingot()
                .liquid(new FluidBuilder().temperature(660450))
                .color(0x70ecff)
                .iconSet(BRIGHT)
                .element(GTQTElements.CrystalMatrix)
                .build();
        //  26024 Void Metal
        GTQTMaterials.VoidMetal = new Material.Builder(getMaterialsId(), gregtechId("void_metal"))
                .ingot()
                .fluid()
                .color(0x20142C)
                .iconSet(DULL)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .element(GTQTElements.VoidMetal)
                .build();
        //  26025 Mithril
        GTQTMaterials.Mithril = new Material.Builder(getMaterialsId(), gregtechId("mithril"))
                .ingot().plasma()
                .liquid(new FluidBuilder().temperature(4450))
                .color(0x428fdb)
                .iconSet(DULL)
                .blast(10800, BlastProperty.GasTier.HIGHER)
                .element(GTQTElements.Mithril)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .build();

        //  26026 Bismuth-209
        GTQTMaterials.Bismuth209 = new Material.Builder(getMaterialsId(), gregtechId("bismuth_209"))
                .fluid()
                .color(Bismuth.getMaterialRGB())
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .element(GTQTElements.Bismuth209)
                .build();
        //  26027 Lead-209
        GTQTMaterials.Lead209 = new Material.Builder(getMaterialsId(), gregtechId("lead_209"))
                .fluid()
                .color(Lead.getMaterialRGB())
                .element(GTQTElements.Lead209)
                .build();

        GTQTMaterials.Solarium = new Material.Builder(getMaterialsId(), gregtechId("solarium"))
                .ingot()
                .liquid()
                .color(0xFFFF33)
                .iconSet(BRIGHT)
                .element(So)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_FOIL, GENERATE_FINE_WIRE, GENERATE_DOUBLE_PLATE)
                .blast(b -> b
                        .temp(14800, BlastProperty.GasTier.HIGHEST)
                        .blastStats(VA[UEV], 800)
                        .vacuumStats(VA[UEV], 400))
                .cableProperties(V[UIV], 16, 8, false)
                .build();

        GTQTMaterials.Eternity = new Material.Builder(getMaterialsId(), gregtechId("eternity"))
                .ingot()
                .liquid()
                .iconSet(CUSTOM_ETERNITY)
                .element(En)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_LENS, GENERATE_ROTOR)
                .build();

        //  10009 Spacetime
        GTQTMaterials.Spacetime = new Material.Builder(getMaterialsId(), gregtechId("spacetime"))
                .ingot()
                .liquid(new FluidBuilder().customStill())
                .iconSet(CUSTOM_SPACETIME)
                .blast(b -> b
                        .temp(16000, BlastProperty.GasTier.HIGHEST)
                        .blastStats(VA[OpV], 100)
                        .vacuumStats(VA[OpV], 100))
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_DENSE, GENERATE_ROD, GENERATE_RING, GENERATE_ROTOR, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_LENS, GENERATE_LONG_ROD, GENERATE_FOIL)
                .element(St)
                .rotorStats(360.0F, 1.0F, 524288000)
                .build()
                .setFormula(addObfuscatedFormula(), false);

        //质子洪流
        GTQTMaterials.ProtonFlux = new Material.Builder(getMaterialsId(), gregtechId("proton_flux"))
                .fluid()
                .color(0xFF3030)
                .element(p)
                .build();

        //中子洪流
        GTQTMaterials.NeutronFlux = new Material.Builder(getMaterialsId(), gregtechId("neutron_flux"))
                .fluid()
                .color(0x303030)
                .element(n)
                .build();
    }
    public static String addObfuscatedFormula() {
        return TextFormatting.OBFUSCATED + "aaaaaa";
    }
    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }
}
