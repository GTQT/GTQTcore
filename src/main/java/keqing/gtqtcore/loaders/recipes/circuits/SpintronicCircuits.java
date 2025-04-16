package keqing.gtqtcore.loaders.recipes.circuits;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import net.minecraftforge.fluids.FluidStack;

import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Polyetheretherketone;
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;

public class SpintronicCircuits {
    public static void init() {
        CircuitBoard();
        CircuitComponent();
        SMDs();
        SoC();
        Circuits();
    }

    private static void CircuitBoard() {

        //  Spintronic Board
        CVD_RECIPES.recipeBuilder()
                .input(plate, CarbonNanotube)
                .input(foil, Phosphorene, 4)
                .fluidInputs(PolyethyleneTerephthalate.getFluid(L * 4))
                .output(SPINTRONIC_BOARD)
                .EUt(VA[UEV])
                .duration(2 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Spintronic Circuit Board
        for (FluidStack stack : new FluidStack[]{TetramethylammoniumHydroxide.getFluid(4000), EDP.getFluid(1000)}) {
            CHEMICAL_RECIPES.recipeBuilder()
                    .input(SPINTRONIC_BOARD)
                    .input(foil, Fullerene, 16)
                    .fluidInputs(stack)
                    .output(SPINTRONIC_CIRCUIT_BOARD)
                    .EUt(VA[LuV])
                    .duration(10 * SECOND + 10)
                    .cleanroom(CleanroomType.CLEANROOM)
                    .buildAndRegister();
        }
    }

    private static void CircuitComponent() {

        //  STTRAM
        FORMING_PRESS_RECIPES.recipeBuilder()
                .input(RANDOM_ACCESS_MEMORY)
                .input(plate, ErbiumDopedZBLANGlass, 2)
                .input(plate, PraseodymiumDopedZBLANGlass, 2)
                .input(foil, Vibranium, 8)
                .input(wireFine, PedotPSS, 16)
                .output(SPIN_TRANSFER_TORQUE_MEMORY, 4)
                .EUt(VA[UEV])
                .duration(10 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  MDNAND
        FORMING_PRESS_RECIPES.recipeBuilder()
                .input(NAND_MEMORY_CHIP)
                .input(dust, PedotTMA, 2)
                .input(foil, Seaborgium, 8)
                .input(wireFine, CarbonNanotube, 16)
                .output(SPINTRONIC_NAND_MEMORY_CHIP, 4)
                .EUt(VA[UEV])
                .duration(10 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  ESR Computation Unit
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(SPINTRONIC_CIRCUIT_BOARD)
                .input(plate, PlutoniumPhosphide, 2)
                .input(plate, BismuthFerrite)
                .input(foil, BismuthChalcogenide, 2)
                .input(TOPOLOGICAL_INSULATOR_TUBE)
                .input(BOSE_EINSTEIN_CONDENSATE)
                .input(wireFine, ThalliumCopperChloride, 24)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .output(ESR_COMPUTATION_UNIT)
                .EUt(VA[UEV])
                .duration(MINUTE / 2)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Topological Insulator Tube process

        //  Bi + Sb + 2Te + S -> Bismuth Chalcogenide
        MOLECULAR_BEAM_RECIPES.recipeBuilder()
                .input(dust, Bismuth)
                .input(dust, Antimony)
                .input(dust, Tellurium, 2)
                .input(dust, Sulfur)
                .notConsumable(plate, CadmiumSulfide)
                .output(dust, BismuthChalcogenide, 5)
                .EUt(VA[UV])
                .duration(4 * SECOND)
                .temperature(4876)
                .buildAndRegister();

        //  Cd + 2Te + 2Hg -> Hg2CdTe2
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Cadmium)
                .input(dust, Tellurium, 2)
                .fluidInputs(Mercury.getFluid(2000))
                .circuitMeta(5)
                .output(dust, MercuryCadmiumTelluride, 5)
                .EUt(VA[UHV])
                .duration(20 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Topological Insulator Tube
        CANNER_RECIPES.recipeBuilder()
                .input(wireFine, MercuryCadmiumTelluride, 16)
                .input(spring, CarbonNanotube)
                .output(TOPOLOGICAL_INSULATOR_TUBE)
                .EUt(VA[HV])
                .duration(SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Bose-Einstein Condensate process

        //  Bose-Einstein Condensate Containment Unit
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(FIELD_GENERATOR_IV)
                .input(HELIUM_NEON_LASER)
                .input(plate, Trinium, 2)
                .input(cableGtSingle, Europium, 2)
                .inputs(MetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockGlassCasing.CasingType.LAMINATED_GLASS, 2))
                .output(BOSE_EINSTEIN_CONDENSATE_CONTAINMENT_UNIT)
                .EUt(VA[UV])
                .duration(4 * SECOND)
                .buildAndRegister();

        //  Bose-Einstein Condensate
        CANNER_RECIPES.recipeBuilder()
                .input(BOSE_EINSTEIN_CONDENSATE_CONTAINMENT_UNIT)
                .input(dust, Rubidium, 8)
                .output(BOSE_EINSTEIN_CONDENSATE)
                .EUt(VA[IV])
                .duration(SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
    }

    private static void SMDs() {

        CVD_RECIPES.recipeBuilder()
                .input(wireFine, MercuryCadmiumTelluride, 4)
                .input(gem, HexagonalBoronNitride)
                .output(SPINTRONIC_RESISTOR, 16)
                .fluidInputs(Kevlar.getFluid(L * 2))
                .EUt(VA[UHV])
                .duration(8 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(wireFine, CarbonNanotube, 8)
                .input(plate, AmorphousBoronNitride)
                .fluidInputs(Kevlar.getFluid(L))
                .output(SPINTRONIC_TRANSISTOR, 16)
                .EUt(VA[UHV])
                .duration(8 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        PLASMA_CVD_RECIPES.recipeBuilder()
                .input(wireGtSingle, CarbonNanotube, 2)
                .input(plate, CubicBoronNitride)
                .fluidInputs(Kevlar.getFluid(L / 4))
                .output(SPINTRONIC_CAPACITOR, 16)
                .EUt(VA[UHV])
                .duration(8 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(dust, CadmiumSelenide)
                .input(wireFine, CarbonNanotube, 4)
                .fluidInputs(Kevlar.getFluid(L / 2))
                .output(SPINTRONIC_DIODE, 16)
                .EUt(VA[UHV])
                .duration(8 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        ION_IMPLANTATOR_RECIPES.recipeBuilder()
                .input(ring, Fullerene)
                .input(wireFine, ThalliumCopperChloride, 4)
                .fluidInputs(Kevlar.getFluid(L))
                .output(SPINTRONIC_INDUCTOR, 16)
                .EUt(VA[UHV])
                .duration(8 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
    }

    private static void SoC() {

        //  C4H6 + C -> C5H6
        PYROLYSE_RECIPES.recipeBuilder()
                .fluidInputs(Butadiene.getFluid(1000))
                .input(dust, Carbon)
                .notConsumable(foil, Nickel)
                .fluidOutputs(Cyclopentadiene.getFluid(1000))
                .EUt(VA[EV])
                .duration(20 * SECOND)
                .buildAndRegister();

        //  C4H9Li + C5H6-> C5H5Li + C4H10
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Butyllithium.getFluid(1000))
                .fluidInputs(Cyclopentadiene.getFluid(1000))
                .fluidOutputs(LithiumCyclopentadienide.getFluid(1000))
                .fluidOutputs(Butane.getFluid(1000))
                .EUt(VA[EV])
                .duration(6 * SECOND)
                .buildAndRegister();

        //  Cf + 3Cl -> CfCl3
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Californium)
                .circuitMeta(3)
                .fluidInputs(Chlorine.getFluid(3000))
                .output(dust, CaliforniumTrichloride, 4)
                .EUt(VA[LV])
                .duration(2 * SECOND)
                .buildAndRegister();

        //  3C5H5Li + CfCl3 -> C15H15Cf + 3LiCl
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, CaliforniumTrichloride, 4)
                .fluidInputs(LithiumCyclopentadienide.getFluid(3000))
                .output(dust, LithiumChloride, 6)
                .fluidOutputs(CaliforniumCyclopentadienide.getFluid(1000))
                .EUt(VA[ZPM])
                .duration(7 * SECOND)
                .buildAndRegister();

        //  X-Ray Waveguide
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(CLADDED_OPTICAL_FIBER_CORE)
                .fluidInputs(FullerenePolymerMatrix.getFluid(16))
                .output(X_RAY_WAVEGUIDE)
                .EUt(VA[ZPM])
                .duration(10 * SECOND)
                .buildAndRegister();

        //  Microfocus X-Ray Tube
        VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .input(stick, Californium)
                .input(plate, PolyvinylChloride)
                .input(wireFine, Europium, 4)
                .fluidInputs(SolderingAlloy.getFluid(L / 2))
                .output(MICROFOCUS_X_RAY_TUBE)
                .EUt(VA[ZPM])
                .duration(4 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  X-Ray Mirror
        VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .notConsumable(ring, Iridium)
                .input(plate, Graphene)
                .fluidInputs(ElectrolyteReflectorMixture.getFluid(L / 4))
                .fluidInputs(Chlorine.getFluid(1000))
                .output(X_RAY_MIRROR)
                .EUt(VA[ZPM])
                .duration(5 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  X-Ray Laser
        VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .input(X_RAY_WAVEGUIDE)
                .input(MICROFOCUS_X_RAY_TUBE)
                .input(X_RAY_MIRROR)
                .fluidInputs(CaliforniumCyclopentadienide.getFluid(100))
                .output(X_RAY_LASER)
                .EUt(VA[UHV])
                .duration(8 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Cryogenic Interface
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Graphene, 2)
                .input(plate, Trinium, 2)
                .input(CRYSTAL_INTERFACE_CHIP)
                .input(FULLERENE_FIBER)
                .fluidInputs(Zylon.getFluid(L / 2))
                .output(CRYOGENIC_INTERFACE)
                .EUt(VA[UHV])
                .duration(8 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Exitation Maintainer
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(PLASMA_CONTAINMENT_CELL)
                .input(plate, MetastableOganesson, 2)
                .input(ND_YAG_LASER)
                .input(wireFine, SiliconCarbide, 4)
                .fluidInputs(FreeElectronGas.getFluid(L))
                .output(EXCITATION_MAINTAINER)
                .EUt(VA[UEV])
                .duration(SECOND / 2)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Electron Source
        VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .input(plate, Polyetheretherketone, 2)
                .input(plate, Technetium, 2)
                .input(EMITTER_EV)
                .input(dustSmall, Radium)
                .fluidInputs(TinAlloy.getFluid(L))
                .fluidInputs(Krypton.getFluid(L / 2))
                .output(ELECTRON_SOURCE)
                .EUt(VA[UV])
                .duration(SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Rydberg Spinorial Assembly
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, MetastableHassium)
                .input(CRYOGENIC_INTERFACE)
                .input(EXCITATION_MAINTAINER)
                .input(X_RAY_WAVEGUIDE)
                .input(ELECTRON_SOURCE)
                .input(dust, CadmiumSelenide)
                .fluidInputs(Xenon.getFluid(L))
                .output(RYDBERG_SPINORIAL_ASSEMBLY)
                .EUt(VA[UIV])
                .duration(8 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Exotic SoC
        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, DegenerateRhenium)
                .input(RYDBERG_SPINORIAL_ASSEMBLY)
                .input(X_RAY_LASER)
                .input(wireFine, HeavyQuarkDegenerateMatter, 4)
                .fluidInputs(MetastableFlerovium.getFluid(L))
                .fluidInputs(QuantumAlloy.getFluid(L / 2))
                .output(EXOTIC_SOC, 4)
                .EUt(VA[UIV])
                .CWUt(CWT[UIV])
                .duration(10 * SECOND)
                .Tier(6) // UIV
                .buildAndRegister();
    }

    private static void Circuits() {

        //  Spintronic Processor
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(ESR_COMPUTATION_UNIT)
                .input(CRYSTAL_SYSTEM_ON_CHIP)
                .input(SPINTRONIC_RESISTOR, 8)
                .input(SPINTRONIC_CAPACITOR, 8)
                .input(SPINTRONIC_TRANSISTOR, 8)
                .input(wireFine, CarbonNanotube, 8)
                .output(SPINTRONIC_PROCESSOR, 2)
                .duration(10 * SECOND)
                .EUt(VA[UEV])
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(SPINTRONIC_CIRCUIT_BOARD)
                .input(EXOTIC_SOC)
                .input(wireFine, PedotTMA, 8)
                .input(bolt, Infinity, 8)
                .output(SPINTRONIC_PROCESSOR, 4)
                .duration(5 * SECOND)
                .EUt(VA[UIV])
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Spintronic Assembly
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(SPINTRONIC_CIRCUIT_BOARD)
                .input(SPINTRONIC_PROCESSOR, 2)
                .input(SPINTRONIC_INDUCTOR, 6)
                .input(SPINTRONIC_CAPACITOR, 12)
                .input(SPIN_TRANSFER_TORQUE_MEMORY, 24)
                .input(wireFine, CarbonNanotube, 16)
                .output(SPINTRONIC_ASSEMBLY, 2)
                .solderMultiplier(2)
                .duration(20 * SECOND)
                .EUt(VA[UEV])
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Spintronic Computer
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(SPINTRONIC_CIRCUIT_BOARD)
                .input(SPINTRONIC_ASSEMBLY, 2)
                .input(SPINTRONIC_DIODE, 8)
                .input(SPINTRONIC_NAND_MEMORY_CHIP, 16)
                .input(SPIN_TRANSFER_TORQUE_MEMORY, 32)
                .input(wireFine, CarbonNanotube, 24)
                .input(foil, Fullerene, 32)
                .input(plate, PlutoniumPhosphide, 4)
                .fluidInputs(SolderingAlloy.getFluid(8640))
                .fluidInputs(Polyetheretherketone.getFluid(4608))
                .fluidInputs(Kevlar.getFluid(2304))
                .fluidInputs(Adamantium.getFluid(1152))
                .output(SPINTRONIC_COMPUTER)
                .duration(20 * SECOND)
                .EUt(VA[UEV])
                .stationResearch(b -> b
                        .researchStack(SPINTRONIC_ASSEMBLY.getStackForm())
                        .CWUt(128)
                        .EUt(VA[UEV]))
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, Fullerene, 2)
                .input(SPINTRONIC_COMPUTER, 2)
                .input(SPINTRONIC_DIODE, 16)
                .input(SPINTRONIC_CAPACITOR, 16)
                .input(SPINTRONIC_TRANSISTOR, 16)
                .input(SPINTRONIC_RESISTOR, 16)
                .input(SPINTRONIC_INDUCTOR, 16)
                .input(foil, CarbonNanotube, 16)
                .input(SPIN_TRANSFER_TORQUE_MEMORY, 32)
                .input(wireGtDouble, FullereneSuperconductor, 16)
                .input(plate, NeptuniumAluminide, 8)
                .fluidInputs(SolderingAlloy.getFluid(10944))
                .fluidInputs(Kevlar.getFluid(8640))
                .fluidInputs(Zylon.getFluid(4608))
                .fluidInputs(Adamantium.getFluid(2304))
                .output(SPINTRONIC_MAINFRAME)
                .duration(MINUTE + 20 * SECOND)
                .EUt(VA[UIV])
                .stationResearch(b -> b
                        .researchStack(SPINTRONIC_COMPUTER.getStackForm())
                        .CWUt(768)
                        .EUt(VA[UIV]))
                .buildAndRegister();
    }
}
