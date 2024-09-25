package keqing.gtqtcore.loaders.recipes.chain;


import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.PACKER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.CarbonNanotube;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class LargeCircuitAssemblyLine {

    public static void init() {
        Circuits();
        WrapComponents();
    }

    private static void Circuits() {

        //  In recipes, please put not consumable item on the last slot in item input slots.
        //  Because Large Circuit Assembly Line use special ui, you should make it on special slot on left hand.

        //  You do not need to use Data stick or other items to storage circuit's info.
        //  You just needs to put circuit in bus, it is not consumable.

        PrimitiveCircuits();   // LV-MV
        IntegratedCircuits();  // LV-HV
        ProcessorCircuits();   // ULV-IV
        NanoCircuits();        // HV-LuV
        QuantumCircuits();     // EV-ZPM
        CrystalCircuits();     // IV-UV
        WetwareCircuits();     // LuV-UHV
        GoowareCircuits();     // ZPM-UEV
        OpticalCircuits();     // UV-UIV
        SpintronicCircuits();  // UHV-UXV
        CosmicCircuits();      // UEV-OpV
        SupracausalCircuits(); // UIV-MAX

        //  Duration List
        //  ULV: 10s, LV-MV: 20s, HV-EV: 40s, IV-LuV: 60s, ZPM-UV: 80s,
        //  UHV-UEV: 100s, UIV-UXV: 120s, OpV: 140s, MAX: 150s
    }

    private static void WrapComponents() {
        WrapCircuitBoard();
        WrapSMD();
    }

    private static void PrimitiveCircuits() {

        //  This recipe is not filled with enough slots, but we can separate some item to meet the visual effect.
        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(72 * 64),
                Tin.getFluid(144 * 64)
        }) {
            //  LV Electronic Circuit
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(ELECTRONIC_CIRCUIT_LV)
                    .input(WRAP_BASIC_CIRCUIT_BOARD, 2) // (4 * 16) / 2
                    .input(WRAP_SMD_RESISTOR, 2) // (8 * 16 = 2 * 64) / 2
                    .input(WRAP_SMD_RESISTOR, 2)
                    .input(wireGtHex, RedAlloy, 4) // (2 * 64 = 8 * 16) / 2
                    .input(WRAP_CIRCUIT_ULV, 2) // (8 * 16 = 2 * 64) / 2
                    .input(WRAP_CIRCUIT_ULV, 2)
                    .fluidInputs(new FluidStack[]{stack})
                    .output(ELECTRONIC_CIRCUIT_LV, 64)
                    .EUt(VA[LV]).CWUt(CWT[LV])
                    .duration(400)
                    .buildAndRegister();

            //  MV Electronic Circuit
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(ELECTRONIC_CIRCUIT_MV)
                    .input(WRAP_GOOD_CIRCUIT_BOARD, 4) // (4 * 16) / 2
                    .input(WRAP_CIRCUIT_LV, 4) // (8 * 16 = 2 * 64) / 2
                    .input(WRAP_CIRCUIT_LV, 4)
                    .input(WRAP_SMD_DIODE, 4) // (8 * 16 = 2 * 64) / 2
                    .input(WRAP_SMD_DIODE, 4)
                    .input(wireGtHex, Copper, 8) // (2 * 64 = 8 * 16) / 2
                    .fluidInputs(new FluidStack[]{stack})
                    .output(ELECTRONIC_CIRCUIT_MV, 64)
                    .EUt(VA[HV]).CWUt(CWT[HV])
                    .duration(400)
                    .buildAndRegister();
        }
    }

    private static void IntegratedCircuits() {
        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(72 * 64),
                Tin.getFluid(144 * 64)
        }) {
            //  LV Integrated Circuit
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(INTEGRATED_CIRCUIT_LV)
                    .input(WRAP_BASIC_CIRCUIT_BOARD, 2) // (4 * 16) / 2
                    .input(INTEGRATED_LOGIC_CIRCUIT_WAFER, 4) // 1 wafer = 8 chip = 16 circuit
                    .input(WRAP_SMD_RESISTOR, 4) // (8 * 16 = 2 * 64) / 2
                    .input(WRAP_SMD_DIODE, 4) // (8 * 16 = 2 * 64) / 2
                    .input(wireGtHex, Copper, 2) // [4 * 16 = 64 (wire) = 2 * 64 (fine wire)] / 2
                    .input(stickLong, Tin, 8) // [16 long stick = 32 stick = 2 * 64 bolt] / 2
                    .fluidInputs(new FluidStack[]{stack})
                    .output(INTEGRATED_CIRCUIT_LV, 64)
                    .EUt(VA[LV]).CWUt(CWT[LV])
                    .duration(400)
                    .buildAndRegister();

            //  MV Integrated Circuit
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(INTEGRATED_CIRCUIT_MV)
                    .input(WRAP_GOOD_CIRCUIT_BOARD, 2) // (4 * 16) / 2
                    .input(WRAP_CIRCUIT_LV, 4) // (8 * 16 = 2 * 64) / 2
                    .input(WRAP_SMD_RESISTOR, 4) // (8 * 16 = 2 * 64) / 2
                    .input(WRAP_SMD_DIODE, 4) // (8 * 16 = 2 * 64) / 2
                    .input(wireGtHex, Gold, 4) // [8 * 16 = 128 (wire) = 4 * 64 (fine wire)] / 2
                    .input(stickLong, Silver, 16) // [32 long stick = 64 stick = 4 * 64 bolt] / 2
                    .fluidInputs(new FluidStack[]{stack})
                    .output(INTEGRATED_CIRCUIT_MV, 64)
                    .EUt(VA[HV]).CWUt(CWT[HV])
                    .duration(400)
                    .buildAndRegister();

            //  HV Integrated Circuit
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(INTEGRATED_CIRCUIT_HV)
                    .input(WRAP_CIRCUIT_MV, 8) // (8 * 16 = 2 * 64)
                    .input(INTEGRATED_LOGIC_CIRCUIT_WAFER, 8) // 1 wafer = 8 chip = 4 circuit
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 4) // 1 wafer = 32 chip = 16 circuit
                    .input(WRAP_SMD_TRANSISTOR, 16) // (16 * 16 = 4 * 64)
                    .input(wireGtHex, Electrum, 16) // [16 * 16 = 4 * 64 (wire) =  8 * 64 (fine wire)]
                    .input(stickLong, AnnealedCopper, 64) // [64 long stick = 2 * 64 stick = 8 * 64 bolt]
                    .fluidInputs(new FluidStack[]{stack})
                    .output(INTEGRATED_CIRCUIT_HV, 64)
                    .EUt(VA[HV]).CWUt(CWT[HV])
                    .duration(800)
                    .buildAndRegister();
        }
    }

    private static void ProcessorCircuits() {
        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(72 * 64),
                Tin.getFluid(144 * 64)
        }) {
            //  ULV NAND Chip (todo)

            //  ULV NAND Chip SoC recipe (todo)

            //  LV Microprocessor (todo)

            //  LV Microprocessor SoC recipe (todo)

            //  MV Processor
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(PROCESSOR_MV)
                    .input(WRAP_PLASTIC_CIRCUIT_BOARD, 2) // (4 * 16) / 2
                    .input(CENTRAL_PROCESSING_UNIT_WAFER, 4) // 1 wafer = 8 chip = 16 circuit
                    .input(WRAP_SMD_RESISTOR, 8) // (16 * 16 = 4 * 64) / 2
                    .input(WRAP_SMD_CAPACITOR, 8) // (16 * 16 = 4 * 64) / 2
                    .input(WRAP_SMD_TRANSISTOR, 8) // (16 * 16 = 4 * 64) / 2
                    .input(wireGtHex, RedAlloy, 4) // [8 * 16 = 128 (wire) = 4 * 64 (fine wire)] / 2
                    .fluidInputs(new FluidStack[]{stack})
                    .output(PROCESSOR_MV, 64)
                    .EUt(VA[HV]).CWUt(CWT[HV])
                    .duration(400)
                    .buildAndRegister();

            //  MV Processor SoC recipe (todo)

        }

        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(144 * 64),
                Tin.getFluid(288 * 64)
        }) {
            //  HV Processor Assembly
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(PROCESSOR_ASSEMBLY_HV)
                    .input(WRAP_PLASTIC_CIRCUIT_BOARD, 2) // (4 * 16) / 2
                    .input(WRAP_CIRCUIT_MV, 4) // (8 * 16 = 2 * 64) / 2
                    .input(WRAP_SMD_INDUCTOR, 8) // (16 * 16 = 4 * 64) / 2
                    .input(WRAP_SMD_CAPACITOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 4) // 1 wafer = 32 chip = 16 circuit
                    .input(wireGtHex, RedAlloy, 8) // [16 * 16 = 256 (wire) = 8 * 64 (fine wire)] / 2
                    .fluidInputs(new FluidStack[]{stack})
                    .output(PROCESSOR_ASSEMBLY_HV, 64)
                    .EUt(VA[HV]).CWUt(CWT[HV])
                    .duration(800)
                    .buildAndRegister();

            //  EV Workstation
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(WORKSTATION_EV)
                    .input(WRAP_PLASTIC_CIRCUIT_BOARD, 4) // 4 * 16
                    .input(WRAP_CIRCUIT_HV, 8) // 8 * 16 = 2 * 64
                    .input(WRAP_SMD_DIODE, 16) // 16 * 16 = 4 * 64
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 8) // 1 wafer = 32 chip = 8 circuit
                    .input(wireGtHex, Electrum, 16) // 64 * 16 fine wire = 64 * 8 wire = 32 * 16
                    .input(stickLong, BlueAlloy, 128) // 1 circuit = 16 bolt = 4 stick = 2 long stick
                    .fluidInputs(new FluidStack[]{stack})
                    .output(WORKSTATION_EV, 64)
                    .EUt(VA[EV]).CWUt(CWT[EV])
                    .duration(800)
                    .buildAndRegister();
        }

        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(288 * 64),
                Tin.getFluid(576 * 64)
        }) {
            //  IV Computer Mainframe
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(MAINFRAME_IV)
                    .input(frameGt, Aluminium, 128) // 2 * 64
                    .input(WRAP_CIRCUIT_EV, 8) // 8 * 16 = 2 * 64
                    .input(WRAP_SMD_INDUCTOR, 32) // 32 * 16 = 8 * 64
                    .input(WRAP_SMD_CAPACITOR, 64) // 64 * 16
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 32) // 1 wafer = 32 chip = 2 circuit
                    .input(wireGtHex, AnnealedCopper, 64) // 1 circuit = 16 wire
                    .fluidInputs(new FluidStack[]{stack})
                    .output(MAINFRAME_IV, 64)
                    .EUt(VA[IV]).CWUt(CWT[IV])
                    .duration(1200)
                    .buildAndRegister();

            //  IV Computer Mainframe Advanced SMD recipe
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(MAINFRAME_IV)
                    .input(frameGt, Aluminium, 128) // 2 * 64
                    .input(WRAP_CIRCUIT_EV, 8) // 8 * 16 = 2 * 64
                    .input(WRAP_ADVANCED_SMD_INDUCTOR, 8) // (32 * 16 = 8 * 64) / 4
                    .input(WRAP_ADVANCED_SMD_CAPACITOR, 16) // (64 * 16) / 4
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 32) // 1 wafer = 32 chip = 2 circuit
                    .input(wireGtHex, AnnealedCopper, 64) // 1 circuit = 16 wire
                    .fluidInputs(new FluidStack[]{stack})
                    .output(MAINFRAME_IV, 64)
                    .EUt(VA[IV]).CWUt(CWT[IV])
                    .duration(1200)
                    .buildAndRegister();
        }
    }

    private static void NanoCircuits() {
        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(72 * 64),
                Tin.getFluid(144 * 64)
        }) {
            //  HV Nano Processor
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(NANO_PROCESSOR_HV)
                    .input(WRAP_ADVANCED_CIRCUIT_BOARD, 2) // (4 * 16) / 2
                    .input(NANO_CENTRAL_PROCESSING_UNIT_WAFER, 4) // 1 wafer = 8 chip = 16 circuit
                    .input(WRAP_SMD_RESISTOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(WRAP_SMD_CAPACITOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(WRAP_SMD_TRANSISTOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(wireGtHex, Electrum, 8) // (16 * 16 = 4 * 64 wire = 8 * 64 fine wire) / 2
                    .fluidInputs(new FluidStack[]{stack})
                    .output(NANO_PROCESSOR_HV, 64)
                    .EUt(VA[HV]).CWUt(CWT[HV])
                    .duration(800)
                    .buildAndRegister();

            //  HV Nano Processor Advanced SMD recipe
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(NANO_PROCESSOR_HV)
                    .input(WRAP_ADVANCED_CIRCUIT_BOARD, 2) // (4 * 16) / 2
                    .input(NANO_CENTRAL_PROCESSING_UNIT_WAFER, 4) // 1 wafer = 8 chip = 16 circuit
                    .input(WRAP_SMD_RESISTOR, 4) // (32 * 16 = 8 * 64) / 2 / 4
                    .input(WRAP_SMD_CAPACITOR, 4) // (32 * 16 = 8 * 64) / 2 / 4
                    .input(WRAP_SMD_TRANSISTOR, 4) // (32 * 16 = 8 * 64) / 2 / 4
                    .input(wireGtHex, Electrum, 8) // (16 * 16 = 4 * 64 wire = 8 * 64 fine wire) / 2
                    .fluidInputs(new FluidStack[]{stack})
                    .output(NANO_PROCESSOR_HV, 64)
                    .EUt(VA[HV]).CWUt(CWT[HV])
                    .duration(800)
                    .buildAndRegister();

            //  HV Nano Processor SoC recipe (todo)
        }

        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(144 * 64),
                Tin.getFluid(288 * 64)
        }) {
            //  EV Nano Assembly
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(NANO_PROCESSOR_ASSEMBLY_EV)
                    .input(WRAP_ADVANCED_CIRCUIT_BOARD, 2) // (4 * 16) / 2
                    .input(WRAP_CIRCUIT_HV, 4) // (8 * 16 = 2 * 64) / 2
                    .input(WRAP_SMD_INDUCTOR, 8) // (16 * 16 = 4 * 64) / 2
                    .input(WRAP_SMD_CAPACITOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 8) // 1 wafer = 32 chip = 8 circuit
                    .input(wireGtHex, Electrum, 16) // (32 * 16 = 8 * 64 wire = 16 * 64 fine wire) / 2
                    .fluidInputs(new FluidStack[]{stack})
                    .output(NANO_PROCESSOR_ASSEMBLY_EV, 64)
                    .EUt(VA[EV]).CWUt(CWT[EV])
                    .duration(800)
                    .buildAndRegister();

            //  EV Nano Assembly Advanced SMD recipe
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(NANO_PROCESSOR_ASSEMBLY_EV)
                    .input(WRAP_ADVANCED_CIRCUIT_BOARD, 2) // (4 * 16) / 2
                    .input(WRAP_CIRCUIT_HV, 4) // (8 * 16 = 2 * 64) / 2
                    .input(WRAP_ADVANCED_SMD_INDUCTOR, 2) // (16 * 16 = 4 * 64) / 2 / 4
                    .input(WRAP_ADVANCED_SMD_CAPACITOR, 4) // (32 * 16 = 8 * 64) / 2 / 4
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 8) // 1 wafer = 32 chip = 8 circuit
                    .input(wireGtHex, Electrum, 16) // (32 * 16 = 8 * 64 wire = 16 * 64 fine wire) / 2
                    .fluidInputs(new FluidStack[]{stack})
                    .output(NANO_PROCESSOR_ASSEMBLY_EV, 64)
                    .EUt(VA[EV]).CWUt(CWT[EV])
                    .duration(800)
                    .buildAndRegister();

            //  IV Nano Supercomputer
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(NANO_COMPUTER_IV)
                    .input(WRAP_ADVANCED_CIRCUIT_BOARD, 4) // 4 * 16
                    .input(WRAP_CIRCUIT_EV, 8) // 8 * 16 = 2 * 64
                    .input(WRAP_SMD_DIODE, 32) // 32 * 16 = 8 * 64
                    .input(NOR_MEMORY_CHIP_WAFER, 16) // 1 wafer = 16 chip = 4 circuit
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 32) // 1 wafer = 32 chip = 2 circuit
                    .input(wireGtHex, Electrum, 32) // 32 * 16 = 8 * 64 wire = 16 * 64 fine wire
                    .fluidInputs(new FluidStack[]{stack})
                    .output(NANO_COMPUTER_IV, 64)
                    .EUt(VA[IV]).CWUt(CWT[IV])
                    .duration(1200)
                    .buildAndRegister();

            //  IV Nano Supercomputer Advanced SMD recipe
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(NANO_COMPUTER_IV)
                    .input(WRAP_ADVANCED_CIRCUIT_BOARD, 4) // 4 * 16
                    .input(WRAP_CIRCUIT_EV, 8) // 8 * 16 = 2 * 64
                    .input(WRAP_ADVANCED_SMD_DIODE, 8) // (32 * 16 = 8 * 64) / 4
                    .input(NOR_MEMORY_CHIP_WAFER, 16) // 1 wafer = 16 chip = 4 circuit
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 32) // 1 wafer = 32 chip = 2 circuit
                    .input(wireGtHex, Electrum, 32) // 32 * 16 = 8 * 64 wire = 16 * 64 fine wire
                    .fluidInputs(new FluidStack[]{stack})
                    .output(NANO_COMPUTER_IV, 64)
                    .EUt(VA[IV]).CWUt(CWT[IV])
                    .duration(1200)
                    .buildAndRegister();
        }

        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(288 * 64),
                Tin.getFluid(576 * 64)
        }) {
            //  LuV Nano Mainframe
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(NANO_MAINFRAME_LUV)
                    .input(frameGt, Aluminium, 128)
                    .input(WRAP_CIRCUIT_IV, 8) // 8 * 16 = 2 * 64
                    .input(WRAP_SMD_INDUCTOR, 64) // 16 * 64
                    .input(WRAP_SMD_CAPACITOR, 128) // 128 * 16 = 64 * 32
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 32) // 1 wafer = 32 chip = 2 circuit
                    .input(wireGtHex, AnnealedCopper, 128) // 1 circuit = 2 hex wire
                    .fluidInputs(new FluidStack[]{stack})
                    .output(NANO_MAINFRAME_LUV, 64)
                    .EUt(VA[LuV]).CWUt(CWT[LuV])
                    .duration(1200)
                    .buildAndRegister();

            //  LuV Nano Mainframe Advanced SMD recipe
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(NANO_MAINFRAME_LUV)
                    .input(frameGt, Aluminium, 128)
                    .input(WRAP_CIRCUIT_IV, 8) // 8 * 16 = 2 * 64
                    .input(WRAP_ADVANCED_SMD_INDUCTOR, 16) // (16 * 64) / 4
                    .input(WRAP_ADVANCED_SMD_CAPACITOR, 32) // (128 * 16 = 64 * 32) / 4
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 32) // 1 wafer = 32 chip = 2 circuit
                    .input(wireGtHex, AnnealedCopper, 128) // 1 circuit = 2 hex wire
                    .fluidInputs(new FluidStack[]{stack})
                    .output(NANO_MAINFRAME_LUV, 64)
                    .EUt(VA[LuV]).CWUt(CWT[LuV])
                    .duration(1200)
                    .buildAndRegister();
        }
    }

    private static void QuantumCircuits() {
        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(72 * 64),
                Tin.getFluid(144 * 64)
        }) {
            //  EV Quantum Processor
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(QUANTUM_PROCESSOR_EV)
                    .input(WRAP_EXTREME_CIRCUIT_BOARD, 2) // (4 * 16) / 2
                    .input(QUBIT_CENTRAL_PROCESSING_UNIT_WAFER, 8) // 1 wafer = 4 chip = 8 circuit
                    .input(NANO_CENTRAL_PROCESSING_UNIT_WAFER, 4) // 1 wafer = 8 chip = 16 circuit
                    .input(WRAP_SMD_CAPACITOR, 24) // (48 * 16 = 12 * 64) / 2
                    .input(WRAP_SMD_TRANSISTOR, 24) // (48 * 16 = 12 * 64) / 2
                    .input(wireGtHex, Platinum, 12) // (24 * 16 wire = 48 * 16 fine wire) / 2
                    .fluidInputs(new FluidStack[]{stack})
                    .output(QUANTUM_PROCESSOR_EV, 64)
                    .EUt(VA[EV]).CWUt(CWT[EV])
                    .duration(800)
                    .buildAndRegister();

            //  EV Quantum Processor Advanced SMD recipe
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(QUANTUM_PROCESSOR_EV)
                    .input(WRAP_EXTREME_CIRCUIT_BOARD, 2) // (4 * 16) / 2
                    .input(QUBIT_CENTRAL_PROCESSING_UNIT_WAFER, 8) // 1 wafer = 4 chip = 8 circuit
                    .input(NANO_CENTRAL_PROCESSING_UNIT_WAFER, 4) // 1 wafer = 8 chip = 16 circuit
                    .input(WRAP_ADVANCED_SMD_CAPACITOR, 6) // (48 * 16 = 12 * 64) / 2 / 4
                    .input(WRAP_ADVANCED_SMD_TRANSISTOR, 6) // (48 * 16 = 12 * 64) / 2 / 4
                    .input(wireGtHex, Platinum, 12) // (24 * 16 wire = 48 * 16 fine wire) / 2
                    .fluidInputs(new FluidStack[]{stack})
                    .output(QUANTUM_PROCESSOR_EV, 64)
                    .EUt(VA[EV]).CWUt(CWT[EV])
                    .duration(800)
                    .buildAndRegister();

            //  EV Quantum Processor Soc recipe (todo)
        }

        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(144 * 64),
                Tin.getFluid(288 * 64)
        }) {
            //  IV Quantum Assembly
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(QUANTUM_ASSEMBLY_IV)
                    .input(WRAP_EXTREME_CIRCUIT_BOARD, 2) // (4 * 16) / 2
                    .input(WRAP_CIRCUIT_EV, 4) // (8 * 16) / 2
                    .input(WRAP_SMD_INDUCTOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(WRAP_SMD_CAPACITOR, 32) // (16 * 64) / 2
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 16) // 1 wafer = 32 chip = 4 circuit
                    .input(wireGtHex, Platinum, 16) // (32 * 16 = 8 * 64 wire = 16 * 64 fine wire = 2 * 64 circuit) / 2
                    .fluidInputs(new FluidStack[]{stack})
                    .output(QUANTUM_ASSEMBLY_IV, 64)
                    .EUt(VA[IV]).CWUt(CWT[IV])
                    .duration(1200)
                    .buildAndRegister();

            //  IV Quantum Assembly Advanced SMD recipe
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(QUANTUM_ASSEMBLY_IV)
                    .input(WRAP_EXTREME_CIRCUIT_BOARD, 2) // (4 * 16) / 2
                    .input(WRAP_CIRCUIT_EV, 4) // (8 * 16) / 2
                    .input(WRAP_ADVANCED_SMD_INDUCTOR, 4) // (32 * 16 = 8 * 64) / 2 / 4
                    .input(WRAP_ADVANCED_SMD_CAPACITOR, 8) // (16 * 64) / 2 / 4
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 16) // 1 wafer = 32 chip = 4 circuit
                    .input(wireGtHex, Platinum, 16) // (32 * 16 = 8 * 64 wire = 16 * 64 fine wire = 2 * 64 circuit) / 2
                    .fluidInputs(new FluidStack[]{stack})
                    .output(QUANTUM_ASSEMBLY_IV, 64)
                    .EUt(VA[IV]).CWUt(CWT[IV])
                    .duration(1200)
                    .buildAndRegister();

            //  LuV Quantum Supercomputer
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(QUANTUM_COMPUTER_LUV)
                    .input(WRAP_EXTREME_CIRCUIT_BOARD, 4) // 4 * 16
                    .input(WRAP_CIRCUIT_IV, 8) // 8 * 16 = 2 * 64
                    .input(WRAP_SMD_DIODE, 32) // 32 * 16 = 8 * 64
                    .input(NOR_MEMORY_CHIP_WAFER, 16) // 1 wafer = 16 chip = 4 circuit
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 32) // 1 wafer = 32 chip = 2 circuit
                    .input(wireGtHex, Platinum, 64) // 16 * 64 wire = 32 * 64 fine wire
                    .fluidInputs(new FluidStack[]{stack})
                    .output(QUANTUM_COMPUTER_LUV, 64)
                    .EUt(VA[LuV]).CWUt(CWT[LuV])
                    .duration(1200)
                    .buildAndRegister();

            //  LuV Quantum Supercomputer Advanced SMD recipe
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(QUANTUM_COMPUTER_LUV)
                    .input(WRAP_EXTREME_CIRCUIT_BOARD, 4) // 4 * 16
                    .input(WRAP_CIRCUIT_IV, 8) // 8 * 16 = 2 * 64
                    .input(WRAP_ADVANCED_SMD_DIODE, 8) // 32 * 16 = 8 * 64 / 4
                    .input(NOR_MEMORY_CHIP_WAFER, 16) // 1 wafer = 16 chip = 4 circuit
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 32) // 1 wafer = 32 chip = 2 circuit
                    .input(wireGtHex, Platinum, 64) // 16 * 64 wire = 32 * 64 fine wire
                    .fluidInputs(new FluidStack[]{stack})
                    .output(QUANTUM_COMPUTER_LUV, 64)
                    .EUt(VA[LuV]).CWUt(CWT[LuV])
                    .duration(1200)
                    .buildAndRegister();
        }

        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(288 * 64),
                Tin.getFluid(576 * 64)
        }) {
            //  ZPM Quantum Mainframe
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(QUANTUM_MAINFRAME_ZPM)
                    .input(frameGt, HSSG, 128)
                    .input(WRAP_CIRCUIT_LuV, 8) // 8 * 16 = 2 * 64
                    .input(WRAP_SMD_INDUCTOR, 96) // 96 * 16 = 24 * 64
                    .input(WRAP_SMD_CAPACITOR, 192) // 192 * 16 = 48 * 64
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 48) // 64 * 24 chip = 48 wafer
                    .input(wireGtSingle, AnnealedCopper, 192) // 48 * 64 wire
                    .fluidInputs(new FluidStack[]{stack})
                    .output(QUANTUM_MAINFRAME_ZPM, 64)
                    .EUt(VA[ZPM]).CWUt(CWT[ZPM])
                    .duration(1600)
                    .buildAndRegister();

            //  ZPM Quantum Mainframe Advanced SMD recipe
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(QUANTUM_MAINFRAME_ZPM)
                    .input(frameGt, HSSG, 128)
                    .input(WRAP_CIRCUIT_LuV, 8) // 8 * 16 = 2 * 64
                    .input(WRAP_ADVANCED_SMD_INDUCTOR, 24) // 96 * 16 = 24 * 64 / 4
                    .input(WRAP_ADVANCED_SMD_CAPACITOR, 48) // 192 * 16 = 48 * 64 / 4
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 48) // 64 * 24 chip = 48 wafer
                    .input(wireGtSingle, AnnealedCopper, 192) // 48 * 64 wire
                    .fluidInputs(new FluidStack[]{stack})
                    .output(QUANTUM_MAINFRAME_ZPM, 64)
                    .EUt(VA[ZPM]).CWUt(CWT[ZPM])
                    .duration(1600)
                    .buildAndRegister();
        }
    }

    private static void CrystalCircuits() {
        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(72 * 64),
                Tin.getFluid(144 * 64)
        }) {
            //  IV Crystal Processor
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(CRYSTAL_PROCESSOR_IV)
                    .input(WRAP_ELITE_CIRCUIT_BOARD, 2) // (4 * 16) / 2
                    .input(CRYSTAL_CENTRAL_PROCESSING_UNIT, 32)
                    .input(NANO_CENTRAL_PROCESSING_UNIT_WAFER, 8) // 1 wafer = 8 chip = 8 circuit
                    .input(WRAP_ADVANCED_SMD_CAPACITOR, 12) // (24 * 16 = 6 * 64) / 2
                    .input(WRAP_ADVANCED_SMD_TRANSISTOR, 12) // (24 * 16 = 6 * 64) / 2
                    .input(wireGtHex, NiobiumTitanium, 8) // 1 * 64 circuit = 2 * 64 wire = 4 * 64 fine wire = 8 * 16
                    .fluidInputs(new FluidStack[]{stack})
                    .output(CRYSTAL_PROCESSOR_IV, 64)
                    .EUt(VA[IV]).CWUt(CWT[IV])
                    .duration(1200)
                    .buildAndRegister();

            //  IV Crystal Processor SoC recipe (todo)
        }

        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(144 * 64),
                Tin.getFluid(288 * 64)
        }) {
            //  LuV Crystal Assembly
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(CRYSTAL_ASSEMBLY_LUV)
                    .input(WRAP_ELITE_CIRCUIT_BOARD, 2) // (4 * 16) / 2
                    .input(WRAP_CIRCUIT_IV, 4) // (8 * 16 = 2 * 64) / 2
                    .input(WRAP_ADVANCED_SMD_INDUCTOR, 8) // (16 * 16 = 4 * 64) / 2
                    .input(WRAP_ADVANCED_SMD_CAPACITOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 24) // 24 wafer = 24 * 32 chip = 64 circuit
                    .input(wireGtHex, NiobiumTitanium, 16) // 64 circuit = 4 * 64 wire = 8 * 64 fine wire = 16 * 16
                    .fluidInputs(new FluidStack[]{stack})
                    .output(CRYSTAL_ASSEMBLY_LUV, 64)
                    .EUt(VA[LuV]).CWUt(CWT[LuV])
                    .duration(1200)
                    .buildAndRegister();

            //  ZPM Crystal Supercomputer
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(CRYSTAL_COMPUTER_ZPM)
                    .input(WRAP_ELITE_CIRCUIT_BOARD, 4) // 4 * 16
                    .input(WRAP_CIRCUIT_LuV, 8) // 8 * 16 = 2 * 64
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 8) // 1 wafer = 32 chip = 8 circuit
                    .input(NOR_MEMORY_CHIP_WAFER, 128) // 1 Wafer = 16 chip = 0.5 circuit
                    .input(NAND_MEMORY_CHIP_WAFER, 128) // 1 Wafer = 32 chip = 0.5 circuit
                    .input(wireGtHex, NiobiumTitanium, 16) // 1 circuit = 16 wire = 32 fine wire
                    .fluidInputs(new FluidStack[]{stack})
                    .output(CRYSTAL_COMPUTER_ZPM, 64)
                    .EUt(VA[ZPM]).CWUt(CWT[ZPM])
                    .duration(1600)
                    .buildAndRegister();
        }

        //  UV Crystal Mainframe
        //  back to Assembly Line recipes
    }

    private static void WetwareCircuits() {
        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(72 * 64),
                Tin.getFluid(144 * 64)
        }) {
            //  LuV Wetware Processor
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(WETWARE_PROCESSOR_LUV)
                    .input(NEURO_PROCESSOR, 32)
                    .input(CRYSTAL_CENTRAL_PROCESSING_UNIT, 32)
                    .input(NANO_CENTRAL_PROCESSING_UNIT_WAFER, 4) // 1 wafer = 8 chip = 16 circuit
                    .input(WRAP_ADVANCED_SMD_CAPACITOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(WRAP_ADVANCED_SMD_TRANSISTOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(wireGtHex, YttriumBariumCuprate, 8) // 2 * 64 wire = 4 * 64 fine wire = 8 * 16
                    .fluidInputs(new FluidStack[]{stack})
                    .output(WETWARE_PROCESSOR_LUV, 64)
                    .EUt(VA[LuV]).CWUt(CWT[LuV])
                    .duration(1200)
                    .buildAndRegister();

            //  LuV Wetware Processor SoC recipe (todo)
        }

        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(144 * 64),
                Tin.getFluid(288 * 64)
        }) {
            //  ZPM Wetware Assembly
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(WETWARE_PROCESSOR_ASSEMBLY_ZPM)
                    .input(WRAP_WETWARE_CIRCUIT_BOARD, 2) // (4 * 16) / 2
                    .input(WRAP_CIRCUIT_LuV, 4) // (8 * 16 = 2 * 64) / 2
                    .input(WRAP_ADVANCED_SMD_INDUCTOR, 12) // (24 * 16 = 6 * 64) / 2
                    .input(WRAP_ADVANCED_SMD_CAPACITOR, 24) // (48 * 16 = 12 * 64) / 2
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 24) // 1 circuit = 12 chip
                    .input(wireGtHex, YttriumBariumCuprate, 16)
                    .fluidInputs(new FluidStack[]{stack})
                    .output(WETWARE_PROCESSOR_ASSEMBLY_ZPM, 64)
                    .EUt(VA[ZPM]).CWUt(CWT[ZPM])
                    .duration(1600)
                    .buildAndRegister();
        }

        //  UV Wetware Supercomputer and UHV Wetware Mainframe
        //  back to Assembly Line recipes
    }

    private static void GoowareCircuits() {
        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(72 * 64),
                Tin.getFluid(144 * 64)
        }) {
            //  ZPM Gooware Processor
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(GOOWARE_PROCESSOR)
                    .input(WRAP_GOOWARE_CIRCUIT_BOARD, 2) // (4 * 16) / 2
                    .input(NONLINEAR_CHEMICAL_OSCILLATOR, 32)
                    .input(CRYSTAL_CENTRAL_PROCESSING_UNIT, 32)
                    .input(WRAP_ADVANCED_SMD_CAPACITOR, 64) // 16 * 64
                    .input(WRAP_ADVANCED_SMD_TRANSISTOR, 64) // 16 * 64
                    .input(wireGtHex, Europium, 8) // 8 * 16 = 2 * 64 wire = 4 * 64 fine wire
                    .fluidInputs(new FluidStack[]{stack})
                    .output(GOOWARE_PROCESSOR, 64)
                    .EUt(VA[ZPM]).CWUt(CWT[ZPM])
                    .duration(1600)
                    .buildAndRegister();

            //  ZPM Gooware Processor SoC recipe (todo)
        }

        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(144 * 64),
                Tin.getFluid(288 * 64)
        }) {
            //  UV Gooware Assembly
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(GOOWARE_ASSEMBLY)
                    .input(WRAP_GOOWARE_CIRCUIT_BOARD, 2) // (4 * 16) / 2
                    .input(WRAP_CIRCUIT_ZPM, 4) // (8 * 16 = 2 * 64) / 2
                    .input(WRAP_ADVANCED_SMD_INDUCTOR, 64) // 16 * 64
                    .input(WRAP_ADVANCED_SMD_CAPACITOR, 128) // 32 * 64
                    .input(RANDOM_ACCESS_MEMORY_WAFER, 40)
                    .input(wireGtHex, Europium, 16)
                    .fluidInputs(new FluidStack[]{stack})
                    .output(GOOWARE_ASSEMBLY, 64)
                    .EUt(VA[UV]).CWUt(CWT[UV])
                    .duration(1600)
                    .buildAndRegister();
        }

        //  UHV Gooware Supercomputer and UEV Gooware Mainframe
        //  back to Assembly Line recipes
    }

    private static void OpticalCircuits() {
        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(72 * 64),
                Tin.getFluid(144 * 64)
        }) {
            //  UV Optical Processor
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(OPTICAL_PROCESSOR)
                    .input(OPTICAL_LASER_CONTROL_UNIT, 32)
                    .input(CRYSTAL_CENTRAL_PROCESSING_UNIT, 32)
                    .input(WRAP_OPTICAL_SMD_RESISTOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(WRAP_OPTICAL_SMD_CAPACITOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(WRAP_OPTICAL_SMD_TRANSISTOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(OPTICAL_FIBER, 256)
                    .fluidInputs(new FluidStack[]{stack})
                    .output(OPTICAL_PROCESSOR, 64)
                    .EUt(VA[UV]).CWUt(CWT[UV])
                    .duration(1600)
                    .buildAndRegister();

            //  UV Optical Processor SoC recipe (todo)
        }

        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(144 * 64),
                Tin.getFluid(288 * 64)
        }) {
            //  UHV Optical Assembly
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(OPTICAL_ASSEMBLY)
                    .input(WRAP_OPTICAL_CIRCUIT_BOARD, 2) // (4 * 16) / 2
                    .input(WRAP_CIRCUIT_UV, 4) // (8 * 16 = 2 * 64) / 2
                    .input(WRAP_OPTICAL_SMD_INDUCTOR, 12) // (24 * 16 = 6 * 64) / 2
                    .input(WRAP_OPTICAL_SMD_CAPACITOR, 24) // (48 * 16  = 12 * 64) / 2
                    .input(PHASE_CHANGE_MEMORY, 768)
                    .input(OPTICAL_FIBER, 1024)
                    .fluidInputs(new FluidStack[]{stack})
                    .output(OPTICAL_ASSEMBLY, 64)
                    .EUt(VA[UHV]).CWUt(CWT[UHV])
                    .duration(2000)
                    .buildAndRegister();
        }

        //  UEV Optical Supercomputer and UIV Optical Mainframe
        //  back to Assembly Line recipes
    }

    private static void SpintronicCircuits() {
        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(72 * 64),
                Tin.getFluid(144 * 64)
        }) {
            //  UHV Spintronic Processor
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(SPINTRONIC_PROCESSOR)
                    .input(ESR_COMPUTATION_UNIT, 32)
                    .input(CRYSTAL_SYSTEM_ON_CHIP, 32)
                    .input(WRAP_SPINTRONIC_SMD_RESISTOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(WRAP_SPINTRONIC_SMD_CAPACITOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(WRAP_SPINTRONIC_SMD_TRANSISTOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(wireGtHex, CarbonNanotube, 8)
                    .fluidInputs(new FluidStack[]{stack})
                    .output(SPINTRONIC_PROCESSOR, 64)
                    .EUt(VA[UHV]).CWUt(CWT[UHV])
                    .duration(2000)
                    .buildAndRegister();
        }

        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(144 * 64),
                Tin.getFluid(288 * 64)
        }) {
            //  UEV Spintronic Assembly
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(SPINTRONIC_ASSEMBLY)
                    .input(WRAP_SPINTRONIC_CIRCUIT_BOARD, 2) // (4 * 16) / 2
                    .input(WRAP_CIRCUIT_UHV, 4) // (8 * 16) / 2
                    .input(WRAP_SPINTRONIC_SMD_INDUCTOR, 12) // (24 * 16 = 6 * 64) / 2
                    .input(WRAP_SPINTRONIC_SMD_CAPACITOR, 24) // (48 * 16  = 12 * 64) / 2
                    .input(SPIN_TRANSFER_TORQUE_MEMORY, 768)
                    .input(wireGtHex, CarbonNanotube, 16)
                    .fluidInputs(new FluidStack[]{stack})
                    .output(SPINTRONIC_ASSEMBLY, 64)
                    .EUt(VA[UEV]).CWUt(CWT[UEV])
                    .duration(2000)
                    .buildAndRegister();
        }

        //  UIV Spintronic Supercomputer and UXV Spintronic Mainframe
        //  back to Assembly Line recipes
    }


    private static void CosmicCircuits() {
        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(72 * 64),
                Tin.getFluid(144 * 64)
        }) {
            //  UEV Cosmic Processor
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(COSMIC_PROCESSOR)
                    .input(HOLOGRAPHIC_INFORMATION_IMC, 32)
                    .input(INTRAVITAL_SOC, 32)
                    .input(WRAP_COSMIC_SMD_RESISTOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(WRAP_COSMIC_SMD_CAPACITOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(WRAP_COSMIC_SMD_TRANSISTOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(wireFine, Infinity, 256)
                    .fluidInputs(new FluidStack[]{stack})
                    .output(COSMIC_PROCESSOR, 64)
                    .EUt(VA[UEV]).CWUt(CWT[UEV])
                    .duration(2000)
                    .buildAndRegister();

            //  UEV Cosmic Processor SoC recipe (todo)
        }

        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(144 * 64),
                Tin.getFluid(288 * 64)
        }) {
            //  UIV Cosmic Assembly
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(COSMIC_ASSEMBLY)
                    .input(COSMIC_INFORMATION_MODULE, 32)
                    .input(WRAP_CIRCUIT_UEV, 4) // (8 * 16 = 2 * 64) / 2
                    .input(WRAP_COSMIC_SMD_INDUCTOR, 12) // (24 * 16 = 6 * 64) / 2
                    .input(WRAP_COSMIC_SMD_CAPACITOR, 24) // (48 * 16  = 12 * 64) / 2
                    .input(COSMIC_MEMORY_CHIP, 768)
                    .input(wireFine, Infinity, 512)
                    .fluidInputs(new FluidStack[]{stack})
                    .output(COSMIC_ASSEMBLY, 64)
                    .EUt(VA[UIV]).CWUt(CWT[UIV])
                    .duration(2400)
                    .buildAndRegister();
        }

        //  UXV Cosmic Supercomputer and OpV Cosmic Mainframe
        //  back to Assembly Line recipes
    }

    private static void SupracausalCircuits() {
        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(72 * 64),
                Tin.getFluid(144 * 64)
        }) {
            //  UIV Supracausal Processor
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(SUPRACAUSAL_PROCESSOR)
                    .input(LIGHT_CONE_MODULE, 32)
                    .input(INTRAVITAL_SOC, 32)
                    .input(WRAP_SUPRACAUSAL_SMD_RESISTOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(WRAP_SUPRACAUSAL_SMD_CAPACITOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(WRAP_SUPRACAUSAL_SMD_TRANSISTOR, 16) // (32 * 16 = 8 * 64) / 2
                    .input(wireGtHex, Hypogen, 8) // 8 * 16 = 2 * 64 wire = 4 * 64 fine wire
                    .fluidInputs(new FluidStack[]{stack})
                    .output(SUPRACAUSAL_PROCESSOR, 64)
                    .EUt(VA[UIV]).CWUt(CWT[UIV])
                    .duration(2400)
                    .buildAndRegister();

            //  UIV Supracausal Processor SoC recipe (todo)
        }

        for (FluidStack stack : new FluidStack[] {
                SolderingAlloy.getFluid(144 * 64),
                Tin.getFluid(288 * 64)
        }) {
            //  UXV Supracausal Assembly
            LARGE_CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .notConsumable(SUPRACAUSAL_ASSEMBLY)
                    .input(SPACETIME_CONDENSER, 32)
                    .input(WRAP_CIRCUIT_UIV, 4) // (8 * 16 = 2 * 64) / 2
                    .input(WRAP_SUPRACAUSAL_SMD_INDUCTOR, 12) // (24 * 16 = 6 * 64) / 2
                    .input(WRAP_SUPRACAUSAL_SMD_CAPACITOR, 24) // (48 * 16  = 12 * 64) / 2
                    .input(SUPRACAUSAL_MEMORY_CHIP, 768)
                    .input(wireGtHex, Hypogen, 16)
                    .fluidInputs(new FluidStack[]{stack})
                    .output(SUPRACAUSAL_ASSEMBLY, 64)
                    .EUt(VA[UXV]).CWUt(CWT[UXV])
                    .duration(2400)
                    .buildAndRegister();
        }

        //  OpV Supracausal Supercomputer and MAX Supracausal Mainframe
        //  back to Assembly Line recipes
    }


    private static void WrapCircuitBoard() {

        //  Basic Circuit Board
        PACKER_RECIPES.recipeBuilder()
                .input(BASIC_CIRCUIT_BOARD, 16)
                .circuitMeta(16)
                .output(WRAP_BASIC_CIRCUIT_BOARD)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Good Circuit Board
        PACKER_RECIPES.recipeBuilder()
                .input(GOOD_CIRCUIT_BOARD, 16)
                .circuitMeta(16)
                .output(WRAP_GOOD_CIRCUIT_BOARD)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Plastic Circuit Board
        PACKER_RECIPES.recipeBuilder()
                .input(PLASTIC_CIRCUIT_BOARD, 16)
                .circuitMeta(16)
                .output(WRAP_PLASTIC_CIRCUIT_BOARD)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Advanced Circuit Board
        PACKER_RECIPES.recipeBuilder()
                .input(ADVANCED_CIRCUIT_BOARD, 16)
                .circuitMeta(16)
                .output(WRAP_ADVANCED_CIRCUIT_BOARD)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Extreme Circuit Board
        PACKER_RECIPES.recipeBuilder()
                .input(EXTREME_CIRCUIT_BOARD, 16)
                .circuitMeta(16)
                .output(WRAP_EXTREME_CIRCUIT_BOARD)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Elite Circuit Board
        PACKER_RECIPES.recipeBuilder()
                .input(ELITE_CIRCUIT_BOARD, 16)
                .circuitMeta(16)
                .output(WRAP_ELITE_CIRCUIT_BOARD)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Wetware Circuit Board
        PACKER_RECIPES.recipeBuilder()
                .input(WETWARE_CIRCUIT_BOARD, 16)
                .circuitMeta(16)
                .output(WRAP_WETWARE_CIRCUIT_BOARD)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Gooware Circuit Board
        PACKER_RECIPES.recipeBuilder()
                .input(GOOWARE_CIRCUIT_BOARD, 16)
                .circuitMeta(16)
                .output(WRAP_GOOWARE_CIRCUIT_BOARD)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Optical Circuit Board
        PACKER_RECIPES.recipeBuilder()
                .input(OPTICAL_CIRCUIT_BOARD, 16)
                .circuitMeta(16)
                .output(WRAP_OPTICAL_CIRCUIT_BOARD)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Spintronic Circuit Board
        PACKER_RECIPES.recipeBuilder()
                .input(SPINTRONIC_CIRCUIT_BOARD, 16)
                .circuitMeta(16)
                .output(WRAP_SPINTRONIC_CIRCUIT_BOARD)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();
    }

    private static void WrapSMD() {

        //  SMD Transistor
        PACKER_RECIPES.recipeBuilder()
                .input(SMD_TRANSISTOR, 16)
                .circuitMeta(16)
                .output(WRAP_SMD_TRANSISTOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Advanced SMD Transistor
        PACKER_RECIPES.recipeBuilder()
                .input(ADVANCED_SMD_TRANSISTOR, 16)
                .circuitMeta(16)
                .output(WRAP_ADVANCED_SMD_TRANSISTOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Optical Transistor
        PACKER_RECIPES.recipeBuilder()
                .input(OPTICAL_TRANSISTOR, 16)
                .circuitMeta(16)
                .output(WRAP_OPTICAL_SMD_TRANSISTOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Spintronic Transistor
        PACKER_RECIPES.recipeBuilder()
                .input(SPINTRONIC_TRANSISTOR, 16)
                .circuitMeta(16)
                .output(WRAP_SPINTRONIC_SMD_TRANSISTOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Cosmic Transistor
        PACKER_RECIPES.recipeBuilder()
                .input(COSMIC_TRANSISTOR, 16)
                .circuitMeta(16)
                .output(WRAP_COSMIC_SMD_TRANSISTOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Supracausal Transistor
        PACKER_RECIPES.recipeBuilder()
                .input(SUPRACAUSAL_TRANSISTOR, 16)
                .circuitMeta(16)
                .output(WRAP_SUPRACAUSAL_SMD_TRANSISTOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  SMD Resistor
        PACKER_RECIPES.recipeBuilder()
                .input(SMD_RESISTOR, 16)
                .circuitMeta(16)
                .output(WRAP_SMD_RESISTOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Advanced SMD Resistor
        PACKER_RECIPES.recipeBuilder()
                .input(ADVANCED_SMD_RESISTOR, 16)
                .circuitMeta(16)
                .output(WRAP_ADVANCED_SMD_RESISTOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Optical Resistor
        PACKER_RECIPES.recipeBuilder()
                .input(OPTICAL_RESISTOR, 16)
                .circuitMeta(16)
                .output(WRAP_OPTICAL_SMD_RESISTOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Spintronic Resistor
        PACKER_RECIPES.recipeBuilder()
                .input(SPINTRONIC_RESISTOR, 16)
                .circuitMeta(16)
                .output(WRAP_SPINTRONIC_SMD_RESISTOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Cosmic Resistor
        PACKER_RECIPES.recipeBuilder()
                .input(COSMIC_RESISTOR, 16)
                .circuitMeta(16)
                .output(WRAP_COSMIC_SMD_RESISTOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Supracausal Resistor
        PACKER_RECIPES.recipeBuilder()
                .input(SUPRACAUSAL_RESISTOR, 16)
                .circuitMeta(16)
                .output(WRAP_SUPRACAUSAL_SMD_RESISTOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  SMD Capacitor
        PACKER_RECIPES.recipeBuilder()
                .input(SMD_CAPACITOR, 16)
                .circuitMeta(16)
                .output(WRAP_SMD_CAPACITOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Advanced SMD Capacitor
        PACKER_RECIPES.recipeBuilder()
                .input(ADVANCED_SMD_CAPACITOR, 16)
                .circuitMeta(16)
                .output(WRAP_ADVANCED_SMD_CAPACITOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Optical Capacitor
        PACKER_RECIPES.recipeBuilder()
                .input(OPTICAL_CAPACITOR, 16)
                .circuitMeta(16)
                .output(WRAP_OPTICAL_SMD_CAPACITOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Spintronic Capacitor
        PACKER_RECIPES.recipeBuilder()
                .input(SPINTRONIC_CAPACITOR, 16)
                .circuitMeta(16)
                .output(WRAP_SPINTRONIC_SMD_CAPACITOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Cosmic Capacitor
        PACKER_RECIPES.recipeBuilder()
                .input(COSMIC_CAPACITOR, 16)
                .circuitMeta(16)
                .output(WRAP_COSMIC_SMD_CAPACITOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Supracausal Capacitor
        PACKER_RECIPES.recipeBuilder()
                .input(SUPRACAUSAL_CAPACITOR, 16)
                .circuitMeta(16)
                .output(WRAP_SUPRACAUSAL_SMD_CAPACITOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  SMD Diode
        PACKER_RECIPES.recipeBuilder()
                .input(SMD_DIODE, 16)
                .circuitMeta(16)
                .output(WRAP_SMD_DIODE)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Advanced SMD Diode
        PACKER_RECIPES.recipeBuilder()
                .input(ADVANCED_SMD_DIODE, 16)
                .circuitMeta(16)
                .output(WRAP_ADVANCED_SMD_DIODE)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Optical Diode
        PACKER_RECIPES.recipeBuilder()
                .input(OPTICAL_DIODE, 16)
                .circuitMeta(16)
                .output(WRAP_OPTICAL_SMD_DIODE)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Spintronic Diode
        PACKER_RECIPES.recipeBuilder()
                .input(SPINTRONIC_DIODE, 16)
                .circuitMeta(16)
                .output(WRAP_SPINTRONIC_SMD_DIODE)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Cosmic Diode
        PACKER_RECIPES.recipeBuilder()
                .input(COSMIC_DIODE, 16)
                .circuitMeta(16)
                .output(WRAP_COSMIC_SMD_DIODE)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Supracausal Diode
        PACKER_RECIPES.recipeBuilder()
                .input(SUPRACAUSAL_DIODE, 16)
                .circuitMeta(16)
                .output(WRAP_SUPRACAUSAL_SMD_DIODE)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  SMD Inductor
        PACKER_RECIPES.recipeBuilder()
                .input(SMD_INDUCTOR, 16)
                .circuitMeta(16)
                .output(WRAP_SMD_INDUCTOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Advanced SMD Inductor
        PACKER_RECIPES.recipeBuilder()
                .input(ADVANCED_SMD_INDUCTOR, 16)
                .circuitMeta(16)
                .output(WRAP_ADVANCED_SMD_INDUCTOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Optical Inductor
        PACKER_RECIPES.recipeBuilder()
                .input(OPTICAL_INDUCTOR, 16)
                .circuitMeta(16)
                .output(WRAP_OPTICAL_SMD_INDUCTOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Spintronic Inductor
        PACKER_RECIPES.recipeBuilder()
                .input(SPINTRONIC_INDUCTOR, 16)
                .circuitMeta(16)
                .output(WRAP_SPINTRONIC_SMD_INDUCTOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Cosmic Inductor
        PACKER_RECIPES.recipeBuilder()
                .input(COSMIC_INDUCTOR, 16)
                .circuitMeta(16)
                .output(WRAP_COSMIC_SMD_INDUCTOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();

        //  Supracausal Inductor
        PACKER_RECIPES.recipeBuilder()
                .input(SUPRACAUSAL_INDUCTOR, 16)
                .circuitMeta(16)
                .output(WRAP_SUPRACAUSAL_SMD_INDUCTOR)
                .EUt(VA[ULV])
                .duration(100)
                .buildAndRegister();
    }
}
