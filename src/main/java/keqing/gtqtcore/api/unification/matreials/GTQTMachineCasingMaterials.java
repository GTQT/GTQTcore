package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Material;



import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.properties.BlastProperty;
import gregtech.api.unification.material.properties.BlastProperty.GasTier;
import gregtech.api.unification.material.properties.ToolProperty;

import static gregicality.multiblocks.api.unification.GCYMMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static gregtech.api.util.GTUtility.gregtechId;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class GTQTMachineCasingMaterials {
    //  Range 24501-25000
    private static int startId = 24500;
    private static final int END_ID = startId + 500;

    public static void register() {
        //  25086 BETS Perrhenate
        BETSPerrhenate = new Material.Builder(getMaterialsId(), gregtechId("bets_perrhenate"))
                .dust()
                .color(0x98E993)
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(SHINY)
                .components(Rhenium, 1, Carbon, 10, Hydrogen, 8, Sulfur, 4, Selenium, 4, Oxygen, 4)
                .build();
        //  24501 Inconel-625
        Inconel625 = new Material.Builder(getMaterialsId(), gregtechId("inconel_625"))
                .ingot().fluid()
                .fluidPipeProperties(4500, 340, true, true, true, false)
                .color(0x3fcc60)
                .iconSet(METALLIC)
                .blast(4850, GasTier.HIGHEST)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(15.0f, 7.0f, 6000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 36000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .components(Nickel, 8, Chrome, 6, Molybdenum, 4, Niobium, 4, Titanium, 3, Iron, 2, Aluminium, 2)
                .build();
        //  24502 Hastelloy-N
        HastelloyN = new Material.Builder(getMaterialsId(), gregtechId("hastelloy_n"))
                .ingot()
                .liquid(new FluidBuilder().temperature(3980))
                .fluidPipeProperties(4380, 300, true, true, true,false)
                .color(0x939554)
                .iconSet(DULL)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(15.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 48000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .blast(b -> b
                        .temp(4550, BlastProperty.GasTier.HIGHER)
                        .blastStats(VA[EV], 800)
                        .vacuumStats(VA[HV], 180))
                .components(Nickel, 15, Molybdenum, 4, Chrome, 2, Titanium, 2, Yttrium, 2)
                .build();
        //  24503 Stellite
        Stellite = new Material.Builder(getMaterialsId(), gregtechId("stellite"))
                .ingot()
                .liquid(new FluidBuilder().temperature(4100))
                .color(0x9991A5)
                .iconSet(METALLIC)
                .blast(4310, GasTier.HIGHER)
                .components(Chrome, 9, Cobalt, 9, Manganese, 5, Titanium, 2)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(15.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();

        //  24506 HDCS (High Durability Compound Steel)
        Hdcs = new Material.Builder(getMaterialsId(), gregtechId("hdcs"))
                .ingot()
                .fluid()
                .color(0x334433)
                .iconSet(SHINY)
                .toolStats(new ToolProperty(20.0F, 10.0F, 180000, 4))
                .blast(11900, GasTier.HIGHER)
                .components(TungstenSteel, 12, HSSS, 9, HSSG, 6, Ruridit, 3, Naphtha, 2, Plutonium241, 1)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL,GENERATE_FOIL)
                .rotorStats(18.0f, 7.0f, 8000)
                .build();

        //  24508 Lafium
        Lafium = new Material.Builder(getMaterialsId(), gregtechId("lafium"))
                .ingot()
                .liquid(new FluidBuilder().temperature(23000))
                .color(0x0D0D60)
                .iconSet(SHINY)
                .blast(9865, GasTier.HIGHER)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .components(HastelloyN, 8, Naquadria, 4, Samarium, 2, Tungsten, 4, Aluminium, 6, Nickel, 8, Titanium, 4, Carbon, 2, Argon, 2)
                .build();
        //  24509 Black Titanium
        BlackTitanium = new Material.Builder(getMaterialsId(), gregtechId("black_titanium"))
                .ingot()
                .fluid()
                .color(0x6C003B)
                .iconSet(SHINY)
                .cableProperties(GTValues.V[GTValues.UIV], 2, 32)
                .blast(11500, GasTier.HIGHER)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 10000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 80000, 6)
                        .attackSpeed(0.1F).enchantability(21).build())
                .components(Titanium, 26, Lanthanum, 6, TungstenSteel, 4, Cobalt, 3, Manganese, 2, Phosphorus, 2, Palladium, 2, Niobium, 1, Argon, 5)
                .build();
        //  24510 Talonite
        Talonite = new Material.Builder(getMaterialsId(), gregtechId("talonite"))
                .ingot()
                .fluid()
                .color(0x9991A5)
                .iconSet(SHINY)
                .blast(2700, GasTier.LOW)
                .flags(GENERATE_PLATE,GENERATE_ROD,GENERATE_GEAR,GENERATE_SMALL_GEAR,GENERATE_LONG_ROD,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_FRAME)
                .components(Cobalt, 4, Chrome, 3, Phosphorus, 2, Molybdenum, 1)
                .build();
        //  24511 Black Plutonium
        BlackPlutonium = new Material.Builder(getMaterialsId(), gregtechId("black_plutonium"))
                .ingot()
                .fluid()
                .color(0x060606)
                .iconSet(BRIGHT)
                .blast(12960, GasTier.HIGHER)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 6)
                        .attackSpeed(0.1F).enchantability(21).build())
                .components(Plutonium244, 18, Cerium, 9, Gadolinium, 3, Dysprosium, 3, Thulium, 2, TungstenCarbide, 6, RedSteel, 6, Duranium, 2, Radon, 2)
                .build();
        //  24512 Maraging Steel-250
        MaragingSteel250 = new Material.Builder(getMaterialsId(), gregtechId("maraging_steel_250"))
                .ingot()
                .fluid()
                .color(0xA5ADB2)
                .iconSet(SHINY)
                .blast(2413, GasTier.HIGHER)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(14.0f, 10.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .components(Steel, 16, Molybdenum, 1, Titanium, 1, Nickel, 4, Cobalt, 2)
                .build();
        //  24513 Staballoy
        Staballoy = new Material.Builder(getMaterialsId(), gregtechId("staballoy"))
                .ingot()
                .fluid()
                .color(0x444B42)
                .iconSet(SHINY)
                .blast(3450, GasTier.HIGHER)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 6000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 40000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .components(Uranium238, 9, Titanium, 1)
                .build();
        //  24514 Babbitt Alloy
        BabbittAlloy = new Material.Builder(getMaterialsId(), gregtechId("babbitt_alloy"))
                .ingot()
                .fluid()
                .color(0xA19CA4)
                .iconSet(SHINY)
                .components(Tin, 5, Lead, 36, Antimony, 8, Astatine, 1)
                .blast(737, GasTier.LOW)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 40000, 7)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();
        //  24515 Zirconium Carbide
        ZirconiumCarbide = new Material.Builder(getMaterialsId(), gregtechId("zirconium_carbide"))
                .ingot()
                .fluid()
                .color(0xFFDACD)
                .iconSet(SHINY)
                .components(Zirconium, 1, Carbon, 1)
                .blast(1200, GasTier.LOW)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 6000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 45000, 6)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();
        //  24516 Inconel-792
        Inconel792 = new Material.Builder(getMaterialsId(), gregtechId("inconel_792"))
                .ingot()
                .liquid(new FluidBuilder().temperature(4900))
                .color(0x6CF076)
                .iconSet(SHINY)
                .components(Nickel, 2, Niobium, 1, Aluminium, 2, Nichrome, 1)
                .blast(6200, GasTier.HIGHER)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 6)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();
        //  24517 Incoloy-MA813
        IncoloyMA813 = new Material.Builder(getMaterialsId(), gregtechId("incoloy_ma_813"))
                .ingot()
                .fluid()
                .color(0x6CF076)
                .iconSet(SHINY)
                .components(VanadiumSteel, 4, Osmiridium, 2, HSSS, 3, Germanium, 4, Duranium, 5, Dubnium, 1)
                .blast(9900, GasTier.HIGHER)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(15.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 75000, 4)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();
        //  24518 Hastelloy-X78
        HastelloyX78 = new Material.Builder(getMaterialsId(), gregtechId("hastelloy_x_78"))
                .ingot()
                .fluid()
                .color(0x6BA3E3)
                .iconSet(SHINY)
                .blast(12280, GasTier.HIGHER)
                .components(NaquadahAlloy, 10, Rhenium, 5, Naquadria, 4, Gadolinium, 3, Strontium, 2, Polonium, 3, Rutherfordium, 2, Fermium, 1)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 6)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();
        //  24519 Hastelloy-K243
        HastelloyK243 = new Material.Builder(getMaterialsId(), gregtechId("hastelloy_k_243"))
                .ingot()
                .fluid()
                .color(0xa4ff70)
                .iconSet(BRIGHT)
                .blast(12400, GasTier.HIGHER)
                .components(HastelloyX78, 5, NiobiumNitride, 2, Tritanium, 4, TungstenCarbide, 4, Promethium, 4, Mendelevium, 1)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 6)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();
        //  24520 Mar-M200 Steel
        MARM200Steel = new Material.Builder(getMaterialsId(), gregtechId("mar_m_200_steel"))
                .ingot()
                .fluid()
                .color(0x515151)
                .iconSet(SHINY)
                .blast(5000, GasTier.HIGHER)
                .components(Niobium, 2, Chrome, 9, Aluminium, 5, Titanium, 2, Cobalt, 10, Tungsten, 13, Nickel, 18)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 6)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();
        //  24521 Mar-M200-Ce Steel
        MARM200CeSteel = new Material.Builder(getMaterialsId(), gregtechId("mar_m_200_ce_steel"))
                .ingot()
                .fluid()
                .color(0x383030)
                .iconSet(BRIGHT)
                .blast(7500, GasTier.HIGHER)
                .components(MARM200Steel, 18, Cerium, 1)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 7)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();
        //  24522 Tanmolyium Beta-C
        TanmolyiumBetaC = new Material.Builder(getMaterialsId(), gregtechId("tanmolyium_beta_c"))
                .ingot()
                .fluid()
                .color(0xc72fcc)
                .iconSet(METALLIC)
                .blast(4400, GasTier.HIGHER)
                .components(Titanium, 5, Molybdenum, 5, Vanadium, 2, Chrome, 3, Aluminium, 1)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 7)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();
        //  24523 Hastelloy-C59
        HastelloyC59 = new Material.Builder(getMaterialsId(), gregtechId("hastelloy_c_59"))
                .ingot()
                .fluid()
                .color(0xD6D0F0)
                .iconSet(DULL)
                .blast(7600, GasTier.HIGHER)
                .components(Nickel, 18, Chrome, 16, TinAlloy, 8, Cobalt, 6, Niobium, 4, Aluminium, 4, Silicon, 2, Phosphorus, 2)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 7)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();
        //  24524 HMS-1J79 Alloy
        HMS1J79Alloy = new Material.Builder(getMaterialsId(), gregtechId("hms_1_j_79_alloy"))
                .ingot()
                .fluid()
                .color(0xD1CB0B)
                .iconSet(SHINY)
                .blast(8100, GasTier.HIGHER)
                .components(Nickel, 14, Iron, 12, Molybdenum, 11, CobaltBrass, 8, Chrome, 6, Silicon, 4)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 6)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();
        //  24525 High Strength Structural Steel-HY130-1
        HY1301 = new Material.Builder(getMaterialsId(), gregtechId("hy_1301"))
                .ingot()
                .fluid()
                .color(0x6F3E57)
                .iconSet(SHINY)
                .blast(7820, GasTier.HIGHER)
                .components(Nickel, 9, NickelZincFerrite, 6, Chrome, 4, Nichrome, 4, Iron, 4, Molybdenum, 4, Rhenium, 2, Silicon, 1)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 6)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();
        //  24526 Super Austenitic Stainless Steel-904L
        AusteniticStainlessSteel904L = new Material.Builder(getMaterialsId(), gregtechId("super_austenitic_stainless_steel_904_l"))
                .ingot()
                .fluid()
                .color(0x881357)
                .iconSet(METALLIC)
                .blast(4400, GasTier.HIGHER)
                .components(StainlessSteel, 8, NickelZincFerrite, 4, Kanthal, 4, Molybdenum, 4)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 7)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();
        //  24527 Eglin Steel Base
        EglinSteelBase = new Material.Builder(getMaterialsId(), gregtechId("eglin_steel_base"))
                .dust()
                .color(0x8B4513)
                .iconSet(SAND)
                .components(Iron, 4, Kanthal, 1, Invar, 5)
                .build();
        //  24528 Eglin Steel
        EglinSteel = new Material.Builder(getMaterialsId(), gregtechId("eglin_steel"))
                .ingot()
                .fluid()
                .color(0x8B4513)
                .iconSet(METALLIC)
                .components(EglinSteelBase, 10, Sulfur, 1, Silicon, 1, Carbon, 1)
                .blast(1080, GasTier.HIGHER)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 7)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();
        //  24529 Pikyonium-64B
        Pikyonium64B = new Material.Builder(getMaterialsId(), gregtechId("pikyonium_64_b"))
                .ingot()
                .fluid()
                .color(0x3467BA)
                .iconSet(SHINY)
                .blast(10400, GasTier.HIGHER)
                .components(Inconel792, 8, EglinSteel, 5, NaquadahAlloy, 4, TungstenSteel, 4, Cerium, 3, Antimony, 2, Platinum, 2, Ytterbium, 1)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 6)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();

        //  24532 Incoloy-DS
        IncoloyDS = new Material.Builder(getMaterialsId(), gregtechId("incoloy_ds"))
                .ingot()
                .fluid()
                .color(0x6746B7)
                .iconSet(BRIGHT)
                .blast(5680, GasTier.HIGHER)
                .components(Iron, 23, Cobalt, 9, Chrome, 9, Nickel, 9)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 6)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();
        //  24533 Inconel-690
        Inconel690 = new Material.Builder(getMaterialsId(), gregtechId("inconel_690"))
                .ingot()
                .fluid()
                .color(0x4FC050)
                .iconSet(SHINY)
                .components(Chrome, 1, Niobium, 2, Molybdenum, 2, Nichrome, 3)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 6)
                        .attackSpeed(0.1F).enchantability(21).build())
                .blast(6200, GasTier.HIGHER)
                .build();
        //  24534 Tantalloy61
        Tantalloy61 = new Material.Builder(getMaterialsId(), gregtechId("tantalloy_61"))
                .ingot()
                .fluid()
                .color(0x717171)
                .iconSet(METALLIC)
                .components(Tantalum, 13, Tungsten, 12, Titanium, 6, Yttrium, 4)
                .blast(6200, GasTier.HIGHER)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 7)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();
        //  24535 Inconel-020
        Incoloy020 = new Material.Builder(getMaterialsId(), gregtechId("incoloy_020"))
                .ingot()
                .fluid()
                .color(0xF8BFFC)
                .iconSet(METALLIC)
                .components(Iron, 10, Nickel, 9, Chrome, 5, Copper, 1)
                .blast(3400, GasTier.HIGHER)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 6)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();
        //  24536 HG-1223
        HG1223 = new Material.Builder(getMaterialsId(), gregtechId("hg_1223"))
                .ingot()
                .fluid()
                .color(0x235497)
                .iconSet(SHINY)
                .components(Mercury, 1, Barium, 2, Calcium, 2, Copper, 3, Oxygen, 8)
                .blast(5253, GasTier.HIGHER)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();
        //  24537 HMS-1J22 Alloy
        HMS1J22Alloy = new Material.Builder(getMaterialsId(), gregtechId("hms_1_j_22_alloy"))
                .ingot()
                .fluid()
                .color(0x9E927D)
                .iconSet(DULL)
                .components(Nickel, 12, TinAlloy, 8, Chrome, 4, Phosphorus, 2, Silicon, 2)
                .blast(4300, GasTier.HIGHER)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 8000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 60000, 5)
                        .attackSpeed(0.1F).enchantability(21).build())
                .build();

        //  24540 Superheavy-H Alloy
        SuperheavyHAlloy = new Material.Builder(getMaterialsId(), gregtechId("superheavy_h_alloy"))
                .ingot()
                .fluid()
                .color(0xE84B36)
                .iconSet(SHINY)
                .components(Copernicium, 1, Nihonium, 1, MetastableFlerovium, 1, Moscovium, 1, Livermorium, 1, Tennessine, 1, MetastableOganesson, 1)
                //  TODO UEV stage coil?
                .blast(12960, GasTier.HIGHER)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 10.0f, 16000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 100000, 6)
                        .attackSpeed(0.1F).enchantability(21).build())
                .cableProperties(V[UIV], 256, 64, false)
                .build();
        //  24541 Superheavy-L Alloy
        SuperheavyLAlloy = new Material.Builder(getMaterialsId(), gregtechId("superheavy_l_alloy"))
                .ingot()
                .fluid()
                .color(0x4D8BE9)
                .iconSet(SHINY)
                .components(Rutherfordium, 1, Dubnium, 1, Seaborgium, 1, Bohrium, 1, MetastableHassium, 1, Meitnerium, 1, Darmstadtium, 1, Roentgenium, 1)
                .blast(10000, GasTier.HIGHER)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_ROTOR,
                        GENERATE_FRAME,GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROUND,GENERATE_SMALL_GEAR
                        ,GENERATE_SPRING,GENERATE_SPRING_SMALL)
                .rotorStats(18.0f, 7.0f, 18000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 160000, 6)
                        .attackSpeed(0.1F).enchantability(21).build())
                .flags(GENERATE_ROD, GENERATE_BOLT_SCREW)
                .build();
        //  24542 Platinum-group Alloy
        PlatinumGroupAlloy = new Material.Builder(getMaterialsId(), gregtechId("platinum_group_alloy"))
                .ingot()
                .fluid()
                .color(Gold.getMaterialRGB() + Silver.getMaterialRGB() + Platinum.getMaterialRGB() + Palladium.getMaterialRGB() + Ruthenium.getMaterialRGB() + Rhodium.getMaterialRGB() + Iridium.getMaterialRGB() + Osmium.getMaterialRGB())
                .iconSet(BRIGHT)
                .components(Gold, 1, Silver, 1, Platinum, 1, Palladium, 1, Ruthenium, 1, Rhodium, 1, Iridium, 1, Osmium, 1)
                .blast(10000, GasTier.HIGHER)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE)
                .build();

        GalvanizedSteel = new Material.Builder(getMaterialsId(), gregtechId("galvanized_steel"))
                .ingot()
                .color(0xb5b5b5)
                .components(Iron, 9, Zinc, 1)
                .iconSet(SHINY)
                .arcSmeltInto(Steel)
                .flags(DISABLE_DECOMPOSITION, NO_WORKING, NO_SMASHING, NO_SMELTING,GENERATE_ROUND, GENERATE_FRAME, GENERATE_ROTOR, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW, GENERATE_PLATE, GENERATE_SPRING_SMALL, GENERATE_SPRING, GENERATE_RING)
                .build();

        Cinobite = new Material.Builder(getMaterialsId(), gregtechId("cinobite"))
                .ingot()
                .fluid()
                .color(0x010101)
                .iconSet(SHINY)
                .blast(b -> b
                        .temp(10460, BlastProperty.GasTier.HIGHER)
                        .blastStats(VA[UV], 800)
                        .vacuumStats(VA[LuV], 140))
                .components(Zeron100, 8, Stellite100, 6, Titanium, 6, Naquadria, 4, Osmiridium, 3, Aluminium, 2, Tin, 1, Mercury, 1)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROTOR)
                .build()
                .setFormula("(Cr₁₃Ni₃Mo₂Cu₁₀W₂Fe₂₀)₈Nq₄Gd₃Al₂HgSnTi₆(Ir₃Os)");

        //  12021 Titan Steel
        TitanSteel = new Material.Builder(getMaterialsId(), gregtechId("titan_steel"))
                .ingot()
                .fluid()
                .color(0xAA0D0D)
                .iconSet(SHINY)
                .blast(b -> b
                        .temp(10600, BlastProperty.GasTier.HIGHEST)
                        .blastStats(VA[UHV], 1200)
                        .vacuumStats(VA[ZPM], 180))
                .components(TitaniumTungstenCarbide, 6, AusteniticStainlessSteel904L, 3, Ruby, 3)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW, GENERATE_ROTOR)
                .build()
                .setFormula("((WC)₇Ti₃)₃?₃");
    }

    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }
}
