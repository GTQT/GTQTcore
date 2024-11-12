package keqing.gtqtcore.loaders.recipes.chain;


import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.UEV;
import static gregtech.api.recipes.RecipeMaps.FUSION_RECIPES;
import static gregtech.api.recipes.RecipeMaps.PLASMA_GENERATOR_FUELS;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Titanium;
import static gregtechfoodoption.GTFOMaterialHandler.Blood;

public class FusionLoader {

    public static void init() {

        int SECOND=20;
        /* -------------------------------- MK1 -------------------------------- */

        //  Magnesium + Oxygen -> Calcium (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Magnesium.getFluid(128))
                .fluidInputs(Oxygen.getFluid(128))
                .fluidOutputs(Calcium.getPlasma(16))
                .EUt(VA[IV])
                .duration((int) (6.4 * SECOND))
                .EUToStart(120000000L) // MK1
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Calcium.getPlasma(1))
                .fluidOutputs(Calcium.getFluid(1))
                .EUt((int) V[EV])
                .duration((int) (3.8 * SECOND))
                .buildAndRegister();

        //  Helium (plasma) + Lithium -> Boron (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Helium.getPlasma(L))
                .fluidInputs(Lithium.getFluid(L))
                .fluidOutputs(Boron.getPlasma(L))
                .EUt(VA[LuV] / 3)
                .duration(12 * SECOND)
                .EUToStart(50000000L)
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Boron.getPlasma(1))
                .fluidOutputs(Boron.getFluid(1))
                .EUt((int) V[EV])
                .duration(4 * SECOND)
                .buildAndRegister();

        //  Boron (plasma) + Calcium (plasma) -> Neon (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Boron.getPlasma(L))
                .fluidInputs(Calcium.getPlasma(16))
                .fluidOutputs(Neon.getPlasma(1000))
                .EUt(VA[LuV] / 3)
                .duration((int) (3.2 * SECOND))
                .EUToStart(100000000L) // MK1
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Neon.getPlasma(1))
                .fluidOutputs(Neon.getFluid(1))
                .EUt((int) V[EV])
                .duration((int) (4.5 * SECOND))
                .buildAndRegister();

        //  Potassium + Helium (plasma) -> Sodium (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Potassium.getFluid(L))
                .fluidInputs(Helium.getPlasma(1000))
                .fluidOutputs(Sodium.getPlasma(1000))
                .EUt(VA[IV] / 3)
                .duration((int) (0.4 * SECOND))
                .EUToStart(100000000L) // MK1
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Sodium.getPlasma(1))
                .fluidOutputs(Sodium.getFluid(1))
                .EUt((int) V[EV])
                .duration((int) (2.5 * SECOND))
                .buildAndRegister();

        /* -------------------------------- MK2 -------------------------------- */

        //  Aluminium + Lithium -> Sulfur (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Aluminium.getFluid(16))
                .fluidInputs(Lithium.getFluid(16))
                .fluidOutputs(Sulfur.getPlasma(L))
                .EUt(VA[LuV] / 3)
                .duration((int) (1.6 * SECOND))
                .EUToStart(240000000L) // MK2
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Sulfur.getPlasma(1))
                .fluidOutputs(Sulfur.getFluid(1))
                .EUt((int) V[EV])
                .duration((int) (5.6 * SECOND))
                .buildAndRegister();

        //  Copper + Tritium -> Zinc (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Copper.getFluid(L / 2))
                .fluidInputs(Tritium.getFluid(250))
                .fluidOutputs(Zinc.getPlasma(L / 2))
                .EUt(49152) // ZPM
                .duration((int) (0.8 * SECOND))
                .EUToStart(180000000L) // MK2
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Zinc.getPlasma(1))
                .fluidOutputs(Zinc.getFluid(1))
                .EUt((int) V[EV])
                .duration((int) (4.9 * SECOND))
                .buildAndRegister();

        //  Cobalt + Silicon -> Niobium (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Cobalt.getFluid(L))
                .fluidInputs(Silicon.getFluid(L))
                .fluidOutputs(Niobium.getPlasma(L))
                .EUt(49152) // ZPM
                .duration((int) (0.8 * SECOND))
                .EUToStart(200000000L) // MK2
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Niobium.getPlasma(1))
                .fluidOutputs(Niobium.getFluid(1))
                .EUt((int) V[EV])
                .duration((int) (5.2 * SECOND))
                .buildAndRegister();

        //  Aluminium + Fluorine -> Titanium (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Aluminium.getFluid(L))
                .fluidInputs(Fluorine.getFluid(L))
                .fluidOutputs(Titanium.getPlasma(L))
                .EUt(49152) // ZPM
                .duration(8 * SECOND)
                .EUToStart(300000000L) // MK2
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Titanium.getPlasma(1))
                .fluidOutputs(Titanium.getFluid(1))
                .EUt((int) V[EV])
                .duration((int) (7.6 * SECOND))
                .buildAndRegister();

        //  Niobium (plasma) + Zinc (plasma) -> Krypton (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Niobium.getPlasma(L))
                .fluidInputs(Zinc.getPlasma(L))
                .fluidOutputs(Krypton.getPlasma(L))
                .EUt((int) V[ZPM])
                .duration((int) (1.6 * SECOND))
                .EUToStart(300000000L) // MK2
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Krypton.getPlasma(1))
                .fluidOutputs(Krypton.getFluid(1))
                .EUt((int) V[EV])
                .duration((int) (7.2 * SECOND))
                .buildAndRegister();

        //  Calcium (plasma) + Iron (plasma) -> Rhenium (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Calcium.getPlasma(500))
                .fluidInputs(Iron.getPlasma(500))
                .fluidOutputs(Rhenium.getPlasma(500))
                .EUt(VA[ZPM] / 3)
                .duration((int) (2.4 * SECOND))
                .EUToStart(300000000L) // MK2
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Rhenium.getPlasma(1))
                .fluidOutputs(Rhenium.getFluid(1))
                .EUt((int) V[EV])
                .duration((int) (10.4 * SECOND))
                .buildAndRegister();

        //  Plutonium-241 + Neon -> Rutherfordium
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Plutonium241.getFluid(16))
                .fluidInputs(Neon.getFluid(16))
                .fluidOutputs(Rutherfordium.getFluid(16))
                .EUt(VA[LuV])
                .duration(6 * SECOND)
                .EUToStart(250000000L) // MK2
                .buildAndRegister();

        //  Americium + Neon -> Dubnium
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Americium.getFluid(16))
                .fluidInputs(Neon.getFluid(125))
                .fluidOutputs(Dubnium.getFluid(125))
                .EUt(VA[ZPM])
                .duration(8 * SECOND)
                .EUToStart(300000000L) // MK2
                .buildAndRegister();
        /* -------------------------------- MK3 -------------------------------- */

        //  Gold + Arsenic -> Silver (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Gold.getFluid(L))
                .fluidInputs(Arsenic.getFluid(L))
                .fluidOutputs(Silver.getPlasma(L))
                .EUt(49152) // ZPM
                .duration((int) (0.8 * SECOND))
                .EUToStart(350000000L) // MK3
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Silver.getPlasma(1))
                .fluidOutputs(Silver.getFluid(1))
                .EUt((int) V[EV])
                .duration((int) (8.4 * SECOND))
                .buildAndRegister();

        //  Tantalum + Zinc (plasma) -> Bismuth (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Tantalum.getFluid(L))
                .fluidInputs(Zinc.getPlasma(L / 2))
                .fluidOutputs(Bismuth.getPlasma(L))
                .EUt(98304) // ZPM
                .duration((int) (0.8 * SECOND))
                .EUToStart(350000000L) // MK3
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Bismuth.getPlasma(1))
                .fluidOutputs(Bismuth.getFluid(1))
                .EUt((int) V[EV])
                .duration((int) (9.2 * SECOND))
                .buildAndRegister();

        //  Curium + Americium (plasma) -> Xenon (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Curium.getFluid(L))
                .fluidInputs(Americium.getPlasma(L))
                .fluidOutputs(Xenon.getPlasma(L))
                .EUt(VA[UV])
                .duration((int) (0.8 * SECOND))
                .EUToStart(500000000L) // MK3
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Xenon.getPlasma(1))
                .fluidOutputs(Xenon.getFluid(1))
                .EUt((int) V[EV])
                .duration((int) (17.4 * SECOND))
                .buildAndRegister();

        //  Iridium + Fluorine -> Radon (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Iridium.getFluid(L))
                .fluidInputs(Fluorine.getFluid(500))
                .fluidOutputs(Radon.getPlasma(L))
                .EUt(98304) // ZPM
                .duration((int) (1.6 * SECOND))
                .EUToStart(450000000L) // MK3
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Radon.getPlasma(1))
                .fluidOutputs(Radon.getFluid(1))
                .EUt((int) V[EV])
                .duration((int) (18.9 * SECOND))
                .buildAndRegister();

        //  Niobium (plasma) + Helium (plasma) -> Chrome (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Niobium.getPlasma(L))
                .fluidInputs(Helium.getPlasma(1000))
                .fluidOutputs(Chrome.getPlasma(500))
                .EUt(VA[UV] / 3)
                .duration((int) (2.5 * SECOND))
                .EUToStart(450000000L) // MK3
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Chrome.getPlasma(1))
                .fluidOutputs(Chrome.getFluid(1))
                .EUt((int) V[EV])
                .duration((int) (14.8 * SECOND))
                .buildAndRegister();

        /*
        //  Krypton (plasma) + Tiberium -> Quantium (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Krypton.getPlasma(1000))
                .fluidInputs(Tiberium.getFluid(1000))
                .fluidOutputs(Quantium.getPlasma(2000))
                .EUt(VA[ZPM])
                .duration(10 * SECOND)
                .EUToStart(380000000L) // MK3
                .buildAndRegister();
*/

        //  Nickel + Polonium -> Copernicium
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Nickel.getPlasma(L * 4))
                .fluidInputs(Polonium.getFluid(L * 4))
                .fluidOutputs(Copernicium.getFluid(L * 4))
                .EUt(VA[UV])
                .duration(5 * SECOND)
                .EUToStart(480000000L) // MK3
                .buildAndRegister();

        //  Nickel + Astatine -> Nihonium
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Nickel.getPlasma(L * 2))
                .fluidInputs(Astatine.getFluid(L * 2))
                .fluidOutputs(Nihonium.getFluid(L * 4))
                .EUt(VA[UV] / 2)
                .duration(9 * SECOND)
                .EUToStart(410000000L) // MK3
                .buildAndRegister();

        //  Neptunium + Titanium -> Moscovium
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Neptunium.getFluid(L * 2))
                .fluidInputs(Titanium.getFluid(L * 2))
                .fluidOutputs(Moscovium.getFluid(L * 4))
                .EUt(VH[UV])
                .duration(11 * SECOND)
                .EUToStart(380000000L) // MK3
                .buildAndRegister();

        //  Americium + Titanium -> Tennessine
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Americium.getFluid(L * 2))
                .fluidInputs(Titanium.getFluid(L * 2))
                .fluidOutputs(Tennessine.getFluid(L * 4))
                .EUt(VHA[UV])
                .duration(15 * SECOND)
                .EUToStart(420000000L) // MK3
                .buildAndRegister();

        //  Curium + Sodium -> Bohrium
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Curium.getFluid(L * 2))
                .fluidInputs(Sodium.getFluid(L * 2))
                .fluidOutputs(Bohrium.getFluid(L * 4))
                .EUt(VA[UV])
                .duration(7 * SECOND)
                .EUToStart(420000000L) // MK3
                .buildAndRegister();

        //  Bohrium + Naquadria -> Neutronium
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Bohrium.getFluid(128))
                .fluidInputs(Naquadria.getFluid(128))
                .fluidOutputs(Neutronium.getFluid(32))
                .EUt(VA[UV])
                .duration(10 * SECOND)
                .EUToStart(420000000L) // MK3
                .buildAndRegister();
/*
        //  Orichalcum + Zirconium -> Mithril
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Orichalcum.getFluid(16))
                .fluidInputs(Zirconium.getFluid(L * 4))
                .fluidOutputs(Mithril.getPlasma(L * 4))
                .EUt(VA[UV])
                .duration(3 * SECOND)
                .EUToStart(450000000L) // MK3
                .buildAndRegister();

        //  Neon + Bedrock -> Taranium (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Neon.getFluid(L))
                .fluidInputs(Bedrock.getFluid(L))
                .fluidOutputs(Taranium.getPlasma(L * 2))
                .EUt(VA[UV])
                .duration((int) (3.2 * SECOND))
                .EUToStart(360000000L) // MK3
                .buildAndRegister();
*/
        /* -------------------------------- MK4 -------------------------------- */

        //  Radon (plasma) + Nitrogen (plasma) -> Neptunium (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Radon.getPlasma(100))
                .fluidInputs(Nitrogen.getPlasma(100))
                .fluidOutputs(Neptunium.getPlasma(100))
                .EUt(VA[UHV])
                .duration((int) (12.8 * SECOND))
                .EUToStart(940000000L) // MK4
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Neptunium.getPlasma(1))
                .fluidOutputs(Neptunium.getFluid(1))
                .EUt((int) V[EV])
                .duration((int) (34.5 * SECOND))
                .buildAndRegister();

        //  Americium (plasma) + Boron (plasma) -> Fermium (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Americium.getPlasma(100))
                .fluidInputs(Boron.getPlasma(100))
                .fluidOutputs(Fermium.getPlasma(100))
                .EUt(VA[UHV])
                .duration((int) (13.2 * SECOND))
                .EUToStart(960000000L) // MK4
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Fermium.getPlasma(1))
                .fluidOutputs(Fermium.getFluid(1))
                .EUt((int) V[EV])
                .duration((int) (38.7 * SECOND))
                .buildAndRegister();

        //  Rhenium (plasma) + Sodium (plasma) -> Gadolinium (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Rhenium.getPlasma(1000))
                .fluidInputs(Sodium.getPlasma(1000))
                .fluidOutputs(Gadolinium.getPlasma(1000))
                .EUt(VA[UHV] / 2)
                .duration((int) (7.6 * SECOND))
                .EUToStart(960000000L) // MK4
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Gadolinium.getPlasma(1))
                .fluidOutputs(Gadolinium.getFluid(1))
                .EUt(VA[EV])
                .duration((int) (44.3 * SECOND))
                .buildAndRegister();

        //  Plutonium-241 + Titanium -> Livermorium
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Plutonium241.getFluid(32))
                .fluidInputs(Titanium.getFluid(32))
                .fluidOutputs(Livermorium.getFluid(64))
                .EUt(VA[UHV])
                .duration(10 * SECOND)
                .EUToStart(850000000L) // MK4
                .buildAndRegister();

        //  Plutonium-241 + Calcium -> Seaborgium
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Plutonium241.getFluid(16))
                .fluidInputs(Calcium.getFluid(32))
                .fluidOutputs(Seaborgium.getFluid(48))
                .EUt(VA[UV])
                .duration(11 * SECOND)
                .EUToStart(650000000L) // MK4
                .buildAndRegister();
/*
        //  Oganesson Breeding Base + Curium -> Hot Oganesson
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(OganessonBreedingBase.getFluid(L))
                .fluidInputs(Curium.getFluid(36))
                .fluidOutputs(MetastableOganesson.getFluid(L))
                .EUt(VA[UHV])
                .duration(5 * SECOND)
                .EUToStart(700000000L) // MK4
                .buildAndRegister();

        //  Uranium-238 + Uranium-238 -> Quasi-fissioning Plasma
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Uranium238.getFluid(125))
                .fluidInputs(Uranium238.getFluid(125))
                .fluidOutputs(QuasifissioningPlasma.getPlasma(125))
                .EUt(600000)
                .duration(5 * SECOND)
                .EUToStart(250000000L) // MK4
                .buildAndRegister();
*/

        //  Iron + Bismuth -> Meitnerium
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Iron.getPlasma(L))
                .fluidInputs(Bismuth.getFluid(L))
                .fluidOutputs(Meitnerium.getFluid(L * 2))
                .EUt(VA[UHV])
                .duration(6 * SECOND)
                .EUToStart(400000000L) // MK4
                .buildAndRegister();

        //  Nickel + Bismuth -> Roentgenium
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Nickel.getPlasma(L))
                .fluidInputs(Bismuth.getFluid(L))
                .fluidOutputs(Roentgenium.getFluid(L * 2))
                .EUt(VA[UHV])
                .duration(9 * SECOND)
                .EUToStart(440000000L) // MK4
                .buildAndRegister();

/*
        //  Nether Star + Bedrock -> Ichor Liquid (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(NetherStar.getFluid(L))
                .fluidInputs(Bedrock.getFluid(1000))
                .fluidOutputs(IchorLiquid.getPlasma(L * 4))
                .EUt(VA[UHV])
                .duration((int) (2.5 * SECOND))
                .EUToStart(650000000L) // MK4
                .buildAndRegister();

        //  Nether Star + Glowstone -> Solarium
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(NetherStar.getFluid(L))
                .fluidInputs(Glowstone.getFluid(1000))
                .fluidOutputs(Solarium.getFluid(L * 4))
                .EUt(VA[UHV])
                .duration((int) (3.1 * SECOND))
                .EUToStart(650000000L) // MK4
                .buildAndRegister();

        //  Silver + Starlight Liquid -> Luna Silver
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Silver.getFluid(L / 2))
                .fluidInputs(StarlightLiquid.getFluid(L / 2))
                .fluidOutputs(LunaSilver.getFluid(L))
                .EUt(VA[UHV])
                .duration((int) (2.8 * SECOND))
                .EUToStart(650000000L) // MK4
                .buildAndRegister();

        //  Ichor Liquid + Radon -> Ichorium
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(IchorLiquid.getPlasma(L))
                .fluidInputs(Radon.getFluid(1000))
                .fluidOutputs(Ichorium.getFluid(500))
                .EUt(VA[UHV])
                .duration(5 * SECOND)
                .EUToStart(1200000000L) // MK4
                .buildAndRegister();

        //  Vibranium + Duranium -> Crystal Matrix
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Vibranium.getPlasma(16))
                .fluidInputs(Duranium.getFluid(L * 2))
                .fluidOutputs(CrystalMatrix.getFluid(L * 2))
                .EUt(VA[UHV])
                .duration(6 * SECOND)
                .EUToStart(800000000L) // MK4
                .buildAndRegister();

        //  Void Metal + Bedrock -> Rhugnor
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(VoidMetal.getFluid(L * 2))
                .fluidInputs(Bedrock.getFluid(1000))
                .fluidOutputs(Rhugnor.getFluid(L * 4))
                .EUt(VA[UHV])
                .duration(8 * SECOND)
                .EUToStart(900000000L) // MK4
                .buildAndRegister();

        //  Concentrate Dragon Breath + Blood -> Dragon Blood (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(ConcentrateDragonBreath.getFluid(1000))
                .fluidInputs(Blood.getFluid(1000))
                .fluidOutputs(DragonBlood.getPlasma(1000))
                .EUt(VA[UHV])
                .duration(5 * SECOND)
                .EUToStart(900000000L) // MK4
                .buildAndRegister();

        //  Naquadria + Radon (plasma) -> Extremely Unstable Naquadah
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Naquadria.getFluid(32))
                .fluidInputs(Radon.getPlasma(250))
                .fluidOutputs(ExtremelyUnstableNaquadah.getFluid(8))
                .EUt(VA[UHV] / 3)
                .duration((int) (1.6 * SECOND))
                .EUToStart(900000000L) // MK4
                .buildAndRegister();

        /* -------------------------------- MK5 -------------------------------- */

        //  Advanced Neptunium Plasma recipe
        //  Xenon (plasma) + Yttrium -> Neptunium (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Xenon.getPlasma(L * 2))
                .fluidInputs(Yttrium.getFluid(L * 2))
                .fluidOutputs(Neptunium.getPlasma(L * 2))
                .EUt(VA[UEV])
                .duration((int) (1.6 * SECOND))
                .EUToStart(1800000000L) // MK5
                .buildAndRegister();
/*
        //  Advanced Fermium Plasma recipe
        //  Taranium (plasma) + Rubidium -> Fermium (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Taranium.getPlasma(L * 2))
                .fluidInputs(Rubidium.getFluid(L * 2))
                .fluidOutputs(Fermium.getPlasma(L * 2))
                .EUt(VA[UEV])
                .duration((int) (1.6 * SECOND))
                .EUToStart(1800000000L) // MK5
                .buildAndRegister();
*/
        //  Lutetium + Vanadium -> Plutonium-241 (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Lutetium.getFluid(L))
                .fluidInputs(Vanadium.getFluid(L))
                .fluidOutputs(Plutonium241.getPlasma(L))
                .EUt(VA[UEV] / 2)
                .duration(14 * SECOND)
                .EUToStart(1800000000L) // MK5
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Plutonium241.getPlasma(1))
                .fluidOutputs(Plutonium241.getFluid(1))
                .EUt((int) V[EV])
                .duration((int) (47.8 * SECOND))
                .buildAndRegister();

        //  Tellurium + Zinc -> Lead (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Tellurium.getFluid(L))
                .fluidInputs(Zinc.getFluid(L))
                .fluidOutputs(Lead.getPlasma(L))
                .EUt(VA[UEV])
                .duration(12 * SECOND)
                .EUToStart(1800000000L) // MK5
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Lead.getPlasma(1))
                .fluidOutputs(Lead.getFluid(1))
                .EUt((int) V[EV])
                .duration((int) (46.4 * SECOND))
                .buildAndRegister();

        //  Osmium + Silicon -> Thorium (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Osmium.getFluid(L))
                .fluidInputs(Silicon.getFluid(L))
                .fluidOutputs(Thorium.getPlasma(L))
                .EUt(VH[UEV] / 2)
                .duration((int) (13.5 * SECOND))
                .EUToStart(1800000000L) // MK5
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Thorium.getPlasma(1))
                .fluidOutputs(Thorium.getFluid(1))
                .EUt((int) V[EV])
                .duration((int) (52.2 * SECOND))
                .buildAndRegister();

        //  Chrome (plasma) + Oxygen (plasma) -> Germanium (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Chrome.getPlasma(L))
                .fluidInputs(Oxygen.getPlasma(1000))
                .fluidOutputs(Germanium.getPlasma(500))
                .EUt(VA[UEV])
                .duration((int) (0.8 * SECOND))
                .EUToStart(1800000000L) // MK5
                .buildAndRegister();

        PLASMA_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Germanium.getPlasma(1))
                .fluidOutputs(Germanium.getFluid(1))
                .EUt((int) V[EV])
                .duration((int) (61.2 * SECOND))
                .buildAndRegister();
/*
        //  Scandium-Titanium Mixture + Radium-Radon Mixture -> Metastable Hassium
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(ScandiumTitaniumMixture.getFluid(L * 2))
                .fluidInputs(RadiumRadonMixture.getFluid(1000))
                .fluidOutputs(MetastableHassium.getFluid(L * 4))
                .EUt(VA[UEV])
                .duration(4 * SECOND)
                .EUToStart(900000000L) // MK5
                .buildAndRegister();

        //  Crystal Matrix + Mithril -> Infinity
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(CrystalMatrix.getFluid(L * 2))
                .fluidInputs(Mithril.getFluid(L * 2))
                .fluidOutputs(Infinity.getFluid(L * 4))
                .EUt(VA[UEV])
                .duration(12 * SECOND)
                .EUToStart(2550000000L) // MK5
                .buildAndRegister();

        //  Dragon Blood + Rhugnor -> Hypogen
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(DragonBlood.getFluid(L * 4))
                .fluidInputs(Rhugnor.getFluid(L * 4))
                .fluidOutputs(Hypogen.getPlasma(L * 8))
                .EUt(VA[UEV])
                .duration(16 * SECOND)
                .EUToStart(2530000000L) // MK5
                .buildAndRegister();

        //  Titanium + Taranium (plasma) -> Astral Titanium (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Titanium.getFluid(L * 2))
                .fluidInputs(Taranium.getPlasma(L * 2))
                .fluidOutputs(AstralTitanium.getPlasma(L * 4))
                .EUt(VA[UEV])
                .duration((int) (1.6 * SECOND))
                .EUToStart(1800000000L) // MK5
                .buildAndRegister();

        //  Tungsten + Taranium plasma -> Celestial Tungsten
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Tungsten.getFluid(L * 2))
                .fluidInputs(Taranium.getPlasma(L * 2))
                .fluidOutputs(CelestialTungsten.getPlasma(L * 4))
                .EUt(VA[UEV])
                .duration((int) (1.6 * SECOND))
                .EUToStart(1800000000L) // MK5
                .buildAndRegister();
        //  Advanced Rhugnor recipe
        //  Dragon Blood (plasma) + Titanium (plasma) -> Rhugnor
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(DragonBlood.getPlasma(500))
                .fluidInputs(Titanium.getPlasma(500))
                .fluidOutputs(Rhugnor.getFluid(1000))
                .EUt(VA[UEV])
                .duration(SECOND)
                .EUToStart(1300000000L) // MK5
                .buildAndRegister();

        //  Hikarium + Tairitsium -> Fatalium (plasma)
        FUSION_RECIPES.recipeBuilder()
                .fluidInputs(Hikarium.getFluid(L * 4))
                .fluidInputs(Tairitsium.getFluid(L * 4))
                .fluidOutputs(Fatalium.getPlasma(L * 2))
                .EUt(VA[UEV])
                .duration(SECOND)
                .EUToStart(2550000000L) // MK5
                .buildAndRegister();

 */
    }
}