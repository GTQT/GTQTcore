package keqing.gtqtcore.loaders.recipes.circuits;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import net.minecraftforge.fluids.FluidStack;

import static gregtechfoodoption.GTFOMaterialHandler.Blood;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.BetaPinene;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Polyetheretherketone;
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;

public class GoowareCircuits {
    public static void init() {
        CircuitBoard();
        CircuitComponent();
        SoC();
        Circuits();
    }

    private static void CircuitBoard() {

        //  Gooware Board
        CVD_RECIPES.recipeBuilder()
                .input(plate, KaptonE)
                .input(foil, Europium, 4)
                .fluidInputs(FluorinatedEthylenePropylene.getFluid(L))
                .output(GOOWARE_BOARD)
                .EUt(VA[UV])
                .duration(2 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Gooware Circuit Board
        for (FluidStack stack : new FluidStack[]{
                TetramethylammoniumHydroxide.getFluid(2000),
                EDP.getFluid(500)}) {
            CHEMICAL_RECIPES.recipeBuilder()
                    .input(GOOWARE_BOARD)
                    .input(foil, YttriumBariumCuprate, 48)
                    .fluidInputs(new FluidStack[]{stack})
                    .output(GOOWARE_CIRCUIT_BOARD)
                    .duration(10 * SECOND + 10)
                    .EUt(GTValues.VA[EV])
                    .cleanroom(CleanroomType.CLEANROOM)
                    .buildAndRegister();
        }
    }

    private static void CircuitComponent() {

        //  BZ Reactor Chamber
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(FLUID_CELL_LARGE_STAINLESS_STEEL.getStackForm())
                .input(plate, Naquadah, 4)
                .input(plate, Ruridit, 2)
                .input(bolt, Trinium, 12)
                .input(stick, SamariumMagnetic)
                .input(rotor, Iridium)
                .input(ELECTRIC_MOTOR_LuV)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .outputs(BZ_REACTION_CHAMBER.getStackForm())
                .EUt(VA[UV])
                .duration(MINUTE / 2)
                .buildAndRegister();

        //  Non-linear Chemical Oscillator
        CANNER_RECIPES.recipeBuilder()
                .inputs(BZ_REACTION_CHAMBER.getStackForm())
                .fluidInputs(BZMedium.getFluid(500))
                .outputs(NONLINEAR_CHEMICAL_OSCILLATOR.getStackForm())
                .EUt(VA[IV])
                .duration(3 * SECOND)
                .buildAndRegister();
    }

    private static void SoC() {

        //  Some idea from Stem cell processing in Gregicality and Bartworks.

        //  Pb + Ti + c-ZrO2 + O -> PbZrTiO3
        MIXER_RECIPES.recipeBuilder()
                .input(dust, Lead)
                .input(dust, Titanium)
                .input(dust, CubicZirconia)
                .circuitMeta(4)
                .fluidInputs(Oxygen.getFluid(1000))
                .output(dust, LeadZirconateTitanate, 4)
                .EUt(VA[LuV])
                .duration(14 * SECOND)
                .buildAndRegister();

        //  Piezoelectric Crystal
        AUTOCLAVE_RECIPES.recipeBuilder()
                .input(gemExquisite, LeadZirconateTitanate)
                .input(wireFine, Gold, 4)
                .fluidInputs(TinAlloy.getFluid(L))
                .output(PIEZOELECTRIC_CRYSTAL)
                .EUt(VA[HV])
                .duration(10 * SECOND + 15)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Ultrasonic Homogenizer
        VACUUM_CHAMBER_RECIPES.recipeBuilder()
                .input(stickLong, RhodiumPlatedPalladium)
                .input(plate, Polybenzimidazole, 2)
                .input(PIEZOELECTRIC_CRYSTAL)
                .input(VACUUM_TUBE)
                .fluidInputs(SolderingAlloy.getFluid(L * 2))
                .output(ULTRASONIC_HOMOGENIZER)
                .EUt(VA[LuV])
                .duration(21 * SECOND + 10)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Blood -> Blood Cells + Blood Plasma
        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .notConsumable(ULTRASONIC_HOMOGENIZER)
                .fluidInputs(Blood.getFluid(1000))
                .fluidOutputs(BloodCells.getFluid(500))
                .fluidOutputs(BloodPlasma.getFluid(500))
                .EUt(VA[HV])
                .duration(40 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Another Blood Sonication recipe
        SONICATION_RECIPES.recipeBuilder()
                .fluidInputs(Blood.getFluid(16000))
                .fluidOutputs(BloodCells.getFluid(8000))
                .fluidOutputs(BloodPlasma.getFluid(8000))
                .EUt(VA[IV])
                .duration(10 * SECOND)
                .buildAndRegister();

        //  Blood Plasma -> bFGF, EGF, CAT
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(BloodPlasma.getFluid(1000))
                .fluidOutputs(BFGF.getFluid(200))
                .fluidOutputs(EGF.getFluid(200))
                .fluidOutputs(CAT.getFluid(200))
                .EUt(VA[HV])
                .duration(2 * SECOND + 10)
                .buildAndRegister();

        //  Biotin
        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .notConsumable(dustTiny, Copper)
                .input(dust, Ash)
                .input(dust, Sugar)
                .fluidInputs(Hydrogen.getFluid(1000))
                .fluidInputs(Nitrogen.getFluid(1000))
                .fluidOutputs(Biotin.getFluid(2000))
                .EUt(VH[IV])
                .duration(2 * SECOND)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .notConsumable(dustTiny, Copper)
                .input(dust, DarkAsh)
                .input(dust, Sugar)
                .fluidInputs(Hydrogen.getFluid(1000))
                .fluidInputs(Nitrogen.getFluid(1000))
                .fluidOutputs(Biotin.getFluid(2000))
                .EUt(VH[IV])
                .duration(2 * SECOND)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .notConsumable(dustTiny, Copper)
                .input(dust, Potash)
                .input(dust, Sugar)
                .fluidInputs(Hydrogen.getFluid(1000))
                .fluidInputs(Nitrogen.getFluid(1000))
                .fluidOutputs(Biotin.getFluid(2000))
                .EUt(VH[IV])
                .duration(2 * SECOND)
                .buildAndRegister();

        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .notConsumable(dustTiny, Copper)
                .input(dust, SodaAsh)
                .input(dust, Sugar)
                .fluidInputs(Hydrogen.getFluid(1000))
                .fluidInputs(Nitrogen.getFluid(1000))
                .fluidOutputs(Biotin.getFluid(2000))
                .EUt(VH[IV])
                .duration(2 * SECOND)
                .buildAndRegister();

        //  Linoleic Acid
        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .notConsumable(ULTRASONIC_HOMOGENIZER)
                .fluidInputs(FermentedBiomass.getFluid(2000))
                .fluidOutputs(LinoleicAcid.getFluid(1000))
                .fluidOutputs(Biomass.getFluid(1000))
                .EUt(VA[MV])
                .duration(2 * MINUTE + 20 * SECOND)
                .buildAndRegister();

        SONICATION_RECIPES.recipeBuilder()
                .fluidInputs(FermentedBiomass.getFluid(32000))
                .fluidOutputs(LinoleicAcid.getFluid(16000))
                .fluidOutputs(Biomass.getFluid(16000))
                .EUt(VA[HV])
                .duration(20 * SECOND)
                .buildAndRegister();

        //  Turpentine -> β-Pinene
        MIXER_RECIPES.recipeBuilder()
                .fluidInputs(Turpentine.getFluid(1000))
                .fluidInputs(SulfuricAcid.getFluid(1000))
                .output(dust, BetaPinene, 26)
                .fluidOutputs(SulfuricAcid.getFluid(1000))
                .EUt(VA[LV])
                .duration(5 * SECOND + 10)
                .buildAndRegister();

        //  C10H16 + 2H2O + 2C5H6 -> 2C10H16O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, BetaPinene, 26)
                .fluidInputs(Cyclopentadiene.getFluid(2000))
                .fluidInputs(Water.getFluid(2000))
                .fluidOutputs(Citral.getFluid(2000))
                .EUt(VA[EV])
                .duration(25 * SECOND)
                .buildAndRegister();

        //  C10H16O + C3H6O -> C13H20O + H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Citral.getFluid(1000))
                .fluidInputs(Acetone.getFluid(1000))
                .fluidOutputs(BetaIonone.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VA[MV])
                .duration(12 * SECOND + 10)
                .buildAndRegister();

        //  CH2O + C2H2 -> CHCCH2OH
        MIXER_RECIPES.recipeBuilder()
                .notConsumable(dust, Copper)
                .fluidInputs(Formaldehyde.getFluid(1000))
                .fluidInputs(Acetylene.getFluid(1000))
                .fluidOutputs(PropargylAlcohol.getFluid(1000))
                .EUt(VA[HV])
                .duration(14 * SECOND)
                .buildAndRegister();

        //  CHCCH2OH + HCl -> yuHC2CH2Cl + H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(PropargylAlcohol.getFluid(1000))
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .fluidOutputs(PropargylChloride.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .EUt(VA[MV])
                .duration(5 * SECOND)
                .buildAndRegister();

        //  25C13H20O + 5HC2CH2Cl -> 17C20H30O + 8O + 5HCl
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(BetaIonone.getFluid(25000))
                .fluidInputs(PropargylChloride.getFluid(5000))
                .fluidOutputs(VitaminA.getFluid(17000))
                .fluidOutputs(Oxygen.getFluid(8000))
                .fluidOutputs(HydrochloricAcid.getFluid(5000))
                .EUt(VA[IV])
                .duration(7 * SECOND)
                .buildAndRegister();

        //  C2H4O + NH3 -> HOCH2CH2NH2
        CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(EthyleneOxide.getFluid(1000))
                .fluidInputs(Ammonia.getFluid(1000))
                .fluidOutputs(Ethanolamine.getFluid(1000))
                .EUt(VH[EV])
                .duration(3 * SECOND)
                .buildAndRegister();

        //  B27
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Biotin.getFluid(1000))
                .fluidInputs(LinoleicAcid.getFluid(1000))
                .fluidInputs(CAT.getFluid(1000))
                .fluidInputs(VitaminA.getFluid(1000))
                .fluidInputs(Ethanolamine.getFluid(1000))
                .fluidOutputs(B27.getFluid(5000))
                .EUt(VA[ZPM])
                .duration(7 * SECOND + 10)
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .buildAndRegister();

        //  Bio Dish
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(PETRI_DISH)
                .fluidInputs(B27.getFluid(100))
                .output(BIO_DISH)
                .EUt(VA[ZPM])
                .duration(6 * SECOND)
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .buildAndRegister();

        //  Exotic Mutagen
        BIOLOGICAL_REACTION_RECIPES.recipeBuilder()
                .input(BIO_DISH)
                .fluidInputs(BFGF.getFluid(1000))
                .fluidInputs(EGF.getFluid(1000))
                .fluidInputs(BloodCells.getFluid(1000))
                .output(PETRI_DISH)
                .fluidOutputs(ExoticMutagen.getFluid(4000))
                .EUt(VA[UV])
                .duration(4 * SECOND)
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .buildAndRegister();

        //  Bio Cell
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, NaquadahEnriched)
                .fluidInputs(SterileGrowthMedium.getFluid(1000))
                .fluidInputs(Bacteria.getFluid(1000))
                .fluidInputs(ExoticMutagen.getFluid(500))
                .output(BIO_CELL, 64)
                .EUt(VA[ZPM])
                .duration(MINUTE / 2)
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .buildAndRegister();

        //  Intravital SoC
        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(CRYSTAL_SYSTEM_ON_CHIP)
                .inputs(HIGHLY_ADVANCED_SOC.getStackForm(2))
                .input(BIO_CELL, 2)
                .input(wireFine, Vibranium, 4)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .fluidInputs(AdvancedLubricant.getFluid(2000))
                .outputs(INTRAVITAL_SOC.getStackForm(2))
                .EUt(VA[UHV])
                .CWUt(CWT[UHV])
                .duration(24 * SECOND)
                .Tier(4) // UHV
                .buildAndRegister();
    }

    private static void Circuits() {

        //  Processor
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(GOOWARE_CIRCUIT_BOARD)
                .input(NONLINEAR_CHEMICAL_OSCILLATOR)
                .input(CRYSTAL_CENTRAL_PROCESSING_UNIT)
                .input(ADVANCED_SMD_CAPACITOR, 16)
                .input(ADVANCED_SMD_TRANSISTOR, 16)
                .input(wireFine, Europium, 8)
                .solderMultiplier(1)
                .output(GOOWARE_PROCESSOR, 2)
                .EUt(VA[UV])
                .duration(10 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(GOOWARE_CIRCUIT_BOARD)
                .input(INTRAVITAL_SOC)
                .input(wireFine, Europium, 8)
                .input(bolt, Orichalcum, 8)
                .solderMultiplier(1)
                .output(GOOWARE_PROCESSOR, 4)
                .EUt(VA[UHV])
                .duration(5 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Assembly
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(GOOWARE_CIRCUIT_BOARD)
                .input(GOOWARE_PROCESSOR, 2)
                .input(ADVANCED_SMD_INDUCTOR, 16)
                .input(ADVANCED_SMD_CAPACITOR, 32)
                .input(RANDOM_ACCESS_MEMORY, 40)
                .input(wireFine, Europium, 16)
                .output(GOOWARE_ASSEMBLY, 2)
                .solderMultiplier(2)
                .EUt(VA[UV])
                .duration(20 * SECOND)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Computer
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(GOOWARE_CIRCUIT_BOARD)
                .input(GOOWARE_ASSEMBLY, 2)
                .input(ADVANCED_SMD_DIODE, 10)
                .input(NOR_MEMORY_CHIP, 16)
                .input(RANDOM_ACCESS_MEMORY, 32)
                .input(wireFine, Europium, 24)
                .input(foil, KaptonK, 32)
                .input(plate, Americium, 4)
                .fluidInputs(SolderingAlloy.getFluid(2304))
                .output(GOOWARE_COMPUTER)
                .EUt(VA[UV])
                .duration(20 * SECOND)
                .stationResearch(b -> b
                        .researchStack(GOOWARE_ASSEMBLY.getStackForm())
                        .CWUt(32)
                        .EUt(VA[UV]))
                .buildAndRegister();

        //  Mainframe
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, Darmstadtium, 2)
                .input(GOOWARE_COMPUTER, 2)
                .input(ADVANCED_SMD_DIODE, 64)
                .input(ADVANCED_SMD_CAPACITOR, 64)
                .input(ADVANCED_SMD_TRANSISTOR, 64)
                .input(ADVANCED_SMD_RESISTOR, 64)
                .input(ADVANCED_SMD_INDUCTOR, 64)
                .input(foil, KaptonK, 64)
                .input(RANDOM_ACCESS_MEMORY, 32)
                .input(wireGtDouble, PedotPSS, 16)
                .input(plate, Americium, 8)
                .fluidInputs(SolderingAlloy.getFluid(4320))
                .fluidInputs(KaptonE.getFluid(2304))
                .fluidInputs(Polyetheretherketone.getFluid(1152))
                .output(GOOWARE_MAINFRAME)
                .EUt(VA[UHV])
                .duration(40 * SECOND)
                .stationResearch(b -> b
                        .researchStack(GOOWARE_COMPUTER.getStackForm())
                        .CWUt(192)
                        .EUt(VA[UHV]))
                .buildAndRegister();
    }
}
