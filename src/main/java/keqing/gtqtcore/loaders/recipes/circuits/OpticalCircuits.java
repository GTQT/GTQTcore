package keqing.gtqtcore.loaders.recipes.circuits;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import net.minecraftforge.fluids.FluidStack;

import static gregicality.multiblocks.api.recipes.GCYMRecipeMaps.ALLOY_BLAST_RECIPES;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.items.MetaItems.SHAPE_EXTRUDER_WIRE;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.ElectrolyteReflectorMixture;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Polyetheretherketone;
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
public class OpticalCircuits {
    public static void init() {
        board();
        smd();
        SoC();
        circuits();
        CircuitComponent();
    }
    private static void CircuitComponent() {

        //TODO Low Gravity
        ALLOY_BLAST_RECIPES.recipeBuilder()
                .input(dust, Zirconium, 5)
                .input(dust, Barium, 2)
                .input(dust, Lanthanum)
                .input(dust, Aluminium)
                .input(dust, Sodium, 2)
                .fluidInputs(Fluorine.getFluid(6200))
                .notConsumable(new IntCircuitIngredient(5))
                .fluidOutputs(ZBLANGlass.getFluid(L * 11))
                .blastFurnaceTemp(1073)
                .duration(1800).EUt(VA[HV]).buildAndRegister();

        ALLOY_BLAST_RECIPES.recipeBuilder()
                .input(dust, Zirconium, 5)
                .input(dust, Barium, 2)
                .input(dust, Lanthanum)
                .input(dust, Aluminium)
                .input(dust, Sodium, 2)
                .fluidInputs(Fluorine.getFluid(6200))
                .circuitMeta(5)
                .fluidOutputs(ZBLANGlass.getFluid(L * 11))
                .EUt(VA[HV])
                .duration(MINUTE + MINUTE / 2)
                .blastFurnaceTemp(1073)
                .buildAndRegister();

        //  GST Glass
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Germanium, 2)
                .input(dust, Antimony, 2)
                .input(dust, Tellurium, 5)
                .circuitMeta(3)
                .output(dust, GSTGlass, 9)
                .EUt(VA[HV])
                .duration(12 * SECOND + 10)
                .buildAndRegister();

        ALLOY_BLAST_RECIPES.recipeBuilder()
                .input(dust, Germanium, 2)
                .input(dust, Antimony, 2)
                .input(dust, Tellurium, 5)
                .circuitMeta(3)
                .fluidOutputs(GSTGlass.getFluid(L * 9))
                .EUt(VA[HV])
                .duration(MINUTE + 20 * SECOND)
                .blastFurnaceTemp(873)
                .buildAndRegister();

        //  Er-doped ZBLAN Glass
        ALLOY_SMELTER_RECIPES.recipeBuilder()
                .input(ingot, ZBLANGlass)
                .input(dust, Erbium)
                .output(ingot, ErbiumDopedZBLANGlass, 2)
                .EUt(VA[HV])
                .duration(10 * SECOND)
                .buildAndRegister();

        //  Pr-doped ZBLAN Glass
        ALLOY_SMELTER_RECIPES.recipeBuilder()
                .input(ingot, ZBLANGlass)
                .input(dust, Praseodymium)
                .output(ingot, PraseodymiumDopedZBLANGlass, 2)
                .EUt(VA[HV])
                .duration(10 * SECOND)
                .buildAndRegister();

        //  PRAM
        FORMING_PRESS_RECIPES.recipeBuilder()
                .input(RANDOM_ACCESS_MEMORY)
                .input(plate, GSTGlass, 2)
                .input(foil, Americium, 8)
                .output(PHASE_CHANGE_MEMORY, 4)
                .EUt(VA[UHV])
                .duration(10 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  ACNOR
        FORMING_PRESS_RECIPES.recipeBuilder()
                .input(NOR_MEMORY_CHIP)
                .input(OPTICAL_FIBER, 2)
                .input(foil, Trinium, 8)
                .output(OPTICAL_NOR_MEMORY_CHIP, 4)
                .EUt(VA[UHV])
                .duration(10 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Another GeCl4 recipe
        //  You can get GeCl4 by Germanium process, but when you make optical circuit,
        //  through Isa mill chain, you can directly get Germanium, so maybe this recipe is useful.
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Germanium)
                .circuitMeta(4)
                .fluidInputs(Chlorine.getFluid(4000))
                .fluidOutputs(GermaniumTetrachloride.getFluid(1000))
                .EUt(VA[MV])
                .duration(17 * SECOND + 10)
                .buildAndRegister();

        //  Optical Fiber
        PLASMA_CVD_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_EXTRUDER_WIRE)
                .fluidInputs(GermaniumTetrachloride.getFluid(250))
                .fluidInputs(PhosphorylChloride.getFluid(250))
                .fluidInputs(SiliconTetrachloride.getFluid(1000))
                .output(OPTICAL_FIBER, 8)
                .EUt(VA[LuV])
                .duration(20 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Dielectric Mirror
        MOLECULAR_BEAM_RECIPES.recipeBuilder()
                .input(foil, Polybenzimidazole)
                .input(dust, ErbiumDopedZBLANGlass, 2)
                .input(dust, PraseodymiumDopedZBLANGlass, 2)
                .input(dust, TantalumPentoxide, 7)
                .output(DIELECTRIC_MIRROR)
                .EUt(VA[LuV])
                .duration(MINUTE / 2)
                .temperature(2820)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Empty Laser
        VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .input(DIELECTRIC_MIRROR)
                .input(plate, SterlingSilver, 2)
                .input(ring, TungstenSteel, 2)
                .input(cableGtSingle, Platinum, 2)
                .fluidInputs(BorosilicateGlass.getFluid(L * 2))
                .output(EMPTY_LASER_ASSEMBLY)
                .EUt(VA[IV])
                .duration(5 * SECOND)
                .buildAndRegister();

        //  Helium-Neon Laser
        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(Helium.getFluid(1000))
                .fluidInputs(Neon.getFluid(1000))
                .fluidOutputs(HeliumNeon.getFluid(1000))
                .EUt(VA[MV])
                .duration(6 * SECOND)
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .input(EMPTY_LASER_ASSEMBLY)
                .fluidInputs(HeliumNeon.getFluid(1000))
                .output(HELIUM_NEON_LASER)
                .EUt(VA[HV])
                .duration(6 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Nd:YAG Laser
        CANNER_RECIPES.recipeBuilder()
                .input(EMPTY_LASER_ASSEMBLY)
                .input(gem, NdYAG)
                .output(ND_YAG_LASER)
                .EUt(VA[HV])
                .duration(6 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Optical Laser Control Unit
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(OPTICAL_CIRCUIT_BOARD)
                .input(HELIUM_NEON_LASER)
                .input(ND_YAG_LASER)
                .input(lens, Diamond, 2)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .output(OPTICAL_LASER_CONTROL_UNIT)
                .duration(MINUTE / 2)
                .EUt(VA[UHV])
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
    }
    private static void SoC() {
        //  MnF2 + ZnS + Ta2O5 + TiO2 + C2H6O -> Electrolyte Reflector Mixture
        MIXER_RECIPES.recipeBuilder()
                .input(dust, ManganeseDifluoride, 3)
                .input(dust, ZincSulfide, 2)
                .input(dust, TantalumPentoxide, 7)
                .input(dust, Rutile, 3)
                .fluidInputs(Ethanol.getFluid(1000))
                .fluidOutputs(ElectrolyteReflectorMixture.getFluid(1000))
                .EUt(VA[UHV])
                .duration(10 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Centrifuge recipe of Electrolyte Reflector Mixture
        //  This recipe should in Cleanroom environment.
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(ElectrolyteReflectorMixture.getFluid(1000))
                .output(dust, ManganeseDifluoride, 3)
                .output(dust, ZincSulfide, 2)
                .output(dust, TantalumPentoxide, 7)
                .output(dust, Rutile, 3)
                .fluidOutputs(Ethanol.getFluid(1000))
                .EUt(VA[LuV])
                .duration(20 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Bh-doped SrSO4 Boule -> Bh-doped SrSO4 Wafer
        CUTTER_RECIPES.recipeBuilder()
                .input(STRONTIUM_CARBONATE_BOHRIUM_BOULE)
                .fluidInputs(Lubricant.getFluid(300))
                .outputs(STRONTIUM_CARBONATE_BOHRIUM_WAFER.getStackForm(6))
                .EUt(VA[EV])
                .duration(10 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Bh-doped SrSO4 Wafer -> Optical Wafer
        //  This is a special wafer for Optical SoC process.
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(STRONTIUM_CARBONATE_BOHRIUM_WAFER)
                .fluidInputs(ElectrolyteReflectorMixture.getFluid(16))
                .output(STRONTIUM_CARBONATE_OPTICAL_WAFER)
                .EUt(VA[UV])
                .duration(6 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Optical Wafer -> Optical Chip
        CUTTER_RECIPES.recipeBuilder()
                .input(STRONTIUM_CARBONATE_OPTICAL_WAFER)
                .fluidInputs(Lubricant.getFluid(200))
                .output(STRONTIUM_CARBONATE_OPTICAL_CHIP, 8)
                .EUt(VA[EV])
                .duration(5 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Optical IMC Board
        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, PedotTMA)
                .input(STRONTIUM_CARBONATE_OPTICAL_CHIP, 2)
                .input(lens, LithiumNiobate)
                .input(dust, ZBLANGlass, 2)
                .fluidInputs(TinAlloy.getFluid(L * 2))
                .output(OPTICAL_IMC_BOARD, 2)
                .EUt(VA[UEV])
                .duration(20 * SECOND)
                .Tier(3) // UV
                .CWUt(CWT[UHV])
                .buildAndRegister();

        //  Optical SoC
        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(OPTICAL_IMC_BOARD)
                .input(UHASOC_CHIP, 2)
                .input(OPTICAL_FIBER, 4)
                .input(wireFine, Solarium, 4)
                .fluidInputs(SiliconCarbide.getFluid(L * 2))
                .fluidInputs(Glowstone.getFluid(4000))
                .output(PHOTOELECTRON_SOC, 4)
                .EUt(VA[UEV])
                .CWUt(CWT[UEV])
                .duration(10 * SECOND)
                .Tier(5)
                .buildAndRegister();
    }

    private static void board() {
        CVD_RECIPES.recipeBuilder()
                .input(plate, GalliumNitride)
                .input(foil, Americium, 4)
                .output(OPTICAL_BOARD)
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(40).EUt(VA[UHV]).buildAndRegister();

        for (FluidStack stack : new FluidStack[]{TetramethylammoniumHydroxide.getFluid(4000), EDP.getFluid(1000)}) {
            CHEMICAL_RECIPES.recipeBuilder()
                    .input(OPTICAL_BOARD)
                    .input(foil, Americium, 64)
                    .fluidInputs(stack)
                    .output(OPTICAL_CIRCUIT_BOARD)
                    .cleanroom(CleanroomType.CLEANROOM)
                    .duration(210).EUt(VA[IV]).buildAndRegister();
        }
    }

    private static void smd() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(wireFine, Naquadah, 4)
                .input(dust, CadmiumSulfide)
                .fluidInputs(KaptonE.getFluid(L * 2))
                .output(OPTICAL_RESISTOR, 16)
                .EUt(VA[UV])
                .duration(8 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(wireFine, Iridium, 8)
                .input(foil, Germanium)
                .fluidInputs(KaptonE.getFluid(L))
                .output(OPTICAL_TRANSISTOR, 16)
                .EUt(VA[UV])
                .duration(8 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        PLASMA_CVD_RECIPES.recipeBuilder()
                .input(OPTICAL_FIBER, 2)
                .input(plate, ErbiumDopedZBLANGlass)
                .fluidInputs(KaptonE.getFluid(L / 4))
                .output(OPTICAL_CAPACITOR, 16)
                .EUt(VA[UV])
                .duration(8 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(dust, Terbium)
                .input(wireFine, BorosilicateGlass, 4)
                .fluidInputs(KaptonE.getFluid(L / 2))
                .output(OPTICAL_DIODE, 16)
                .EUt(VA[UV])
                .duration(8 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        ION_IMPLANTATOR_RECIPES.recipeBuilder()
                .input(dust, Silver, 4)
                .input(plate, PMMA)
                .fluidInputs(KaptonE.getFluid(L))
                .output(OPTICAL_INDUCTOR, 16)
                .EUt(VA[UV])
                .duration(8 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
    }

    private static void circuits() {
        //  Processor
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(OPTICAL_LASER_CONTROL_UNIT)
                .input(CRYSTAL_CENTRAL_PROCESSING_UNIT)
                .input(OPTICAL_RESISTOR, 8)
                .input(OPTICAL_CAPACITOR, 8)
                .input(OPTICAL_TRANSISTOR, 8)
                .input(OPTICAL_FIBER, 8)
                .output(OPTICAL_PROCESSOR, 2)
                .solderMultiplier(1)
                .EUt(VA[UHV])
                .duration(10 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(OPTICAL_CIRCUIT_BOARD)
                .input(PHOTOELECTRON_SOC)
                .input(wireFine, PedotPSS, 8)
                .input(bolt, Adamantium, 8)
                .output(OPTICAL_PROCESSOR, 4)
                .solderMultiplier(1)
                .EUt(VA[UEV])
                .duration(5 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Assembly
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(OPTICAL_CIRCUIT_BOARD)
                .input(OPTICAL_PROCESSOR, 2)
                .input(OPTICAL_INDUCTOR, 6)
                .input(OPTICAL_CAPACITOR, 12)
                .input(PHASE_CHANGE_MEMORY, 24)
                .input(OPTICAL_FIBER, 16)
                .output(OPTICAL_ASSEMBLY, 2)
                .solderMultiplier(2)
                .EUt(VA[UHV])
                .duration(20 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Computer
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(OPTICAL_CIRCUIT_BOARD)
                .input(OPTICAL_ASSEMBLY, 2)
                .input(OPTICAL_DIODE, 8)
                .input(OPTICAL_NOR_MEMORY_CHIP, 16)
                .input(PHASE_CHANGE_MEMORY, 32)
                .input(OPTICAL_FIBER, 24)
                .input(foil, KaptonE, 32)
                .input(plate, Tritanium, 4)
                .fluidInputs(SolderingAlloy.getFluid(4320))
                .fluidInputs(Polyetheretherketone.getFluid(2304))
                .fluidInputs(Vibranium.getFluid(1152))
                .output(OPTICAL_COMPUTER)
                .EUt(VA[UHV])
                .duration(20 * SECOND)
                .stationResearch(b -> b
                        .researchStack(OPTICAL_ASSEMBLY.getStackForm())
                        .CWUt(64)
                        .EUt(VA[UHV]))
                .buildAndRegister();

        //  Mainframe
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, Orichalcum, 2)
                .input(OPTICAL_COMPUTER, 2)
                .input(OPTICAL_DIODE, 16)
                .input(OPTICAL_CAPACITOR, 16)
                .input(OPTICAL_TRANSISTOR, 16)
                .input(OPTICAL_RESISTOR, 16)
                .input(OPTICAL_INDUCTOR, 16)
                .input(foil, KaptonE, 64)
                .input(PHASE_CHANGE_MEMORY, 32)
                .input(wireGtDouble, Tritanium, 16)
                .input(plate, Infinity, 8)
                .fluidInputs(SolderingAlloy.getFluid(5472))
                .fluidInputs(Polyetheretherketone.getFluid(4320))
                .fluidInputs(Vibranium.getFluid(2304))
                .fluidInputs(KaptonK.getFluid(1152))
                .output(OPTICAL_MAINFRAME)
                .EUt(VA[UEV])
                .duration(MINUTE)
                .stationResearch(b -> b
                        .researchStack(OPTICAL_COMPUTER.getStackForm())
                        .CWUt(384)
                        .EUt(VA[UEV]))
                .buildAndRegister();
    }
}
