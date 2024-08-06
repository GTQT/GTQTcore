package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.metatileentity.multiblock.CleanroomType;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.MAX;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.recipes.RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.SolderingAlloy;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.ore.OrePrefix.gemExquisite;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.items.MetaItems.ADVANCED_SMD_DIODE;
import static keqing.gtqtcore.api.unification.GTQTMaterials.BismuthTellurite;
import static keqing.gtqtcore.api.unification.GTQTMaterials.MagnetoResonatic;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class MagnetoResonaticCircuits {
    static int MINUTE=20;
    public static void init() {
        CircuitBoard();
        Circuits();
    }

    private static void CircuitBoard() {
        //  Magneto Resonatic Board
        FORMING_PRESS_RECIPES.recipeBuilder()
                .input(dust, IndiumGalliumPhosphide)
                .input(dust, BismuthTellurite, 2)
                .input(dust, Boron, 2)
                .input(dust, MagnetoResonatic)
                .output(MAGNETO_RESONATIC_BOARD)
                .EUt(VA[HV])
                .duration(MINUTE / 4)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  Magneto Resonatic Circuit Board
        AUTOCLAVE_RECIPES.recipeBuilder()
                .input(MAGNETO_RESONATIC_BOARD)
                .fluidInputs(SolderingAlloy.getFluid(L * 2))
                .output(MAGNETO_RESONATIC_CIRCUIT_BOARD)
                .EUt(VA[EV])
                .duration(MINUTE / 2)
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();
    }

    private static void Circuits() {

        int baseDuration = 75;

        //  ULV
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MAGNETO_RESONATIC_CIRCUIT_BOARD)
                .input(gem, MagnetoResonatic)
                .input(CAPACITOR, 4)
                .input(TRANSISTOR, 4)
                .input(DIODE, 4)
                .input(VACUUM_TUBE, 1)
                .fluidInputs(SolderingAlloy.getFluid(36))
                .output(MAGNETO_RESONATIC_CIRCUIT_ULV, 4)
                .EUt(VA[LV])
                .duration(baseDuration)
                .buildAndRegister();

        //  LV
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MAGNETO_RESONATIC_CIRCUIT_BOARD)
                .input(gem, MagnetoResonatic)
                .input(MAGNETO_RESONATIC_CIRCUIT_ULV)
                .input(CAPACITOR, 4)
                .input(TRANSISTOR, 4)
                .input(DIODE, 4)
                .fluidInputs(SolderingAlloy.getFluid(72))
                .output(MAGNETO_RESONATIC_CIRCUIT_LV, 4)
                .EUt(VA[MV])
                .duration(baseDuration * 2)
                .buildAndRegister();

        //  MV
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MAGNETO_RESONATIC_CIRCUIT_BOARD)
                .input(gem, MagnetoResonatic)
                .input(MAGNETO_RESONATIC_CIRCUIT_LV)
                .input(CAPACITOR, 8)
                .input(TRANSISTOR, 8)
                .input(DIODE, 8)
                .fluidInputs(SolderingAlloy.getFluid(108))
                .output(MAGNETO_RESONATIC_CIRCUIT_MV, 4)
                .EUt(VA[HV])
                .duration(baseDuration * 3)
                .buildAndRegister();

        //  HV
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MAGNETO_RESONATIC_CIRCUIT_BOARD)
                .input(gem, MagnetoResonatic)
                .input(MAGNETO_RESONATIC_CIRCUIT_MV)
                .input(CAPACITOR, 8)
                .input(TRANSISTOR, 8)
                .input(DIODE, 8)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .output(MAGNETO_RESONATIC_CIRCUIT_HV, 4)
                .EUt(VA[EV])
                .duration(baseDuration * 4)
                .buildAndRegister();

        //  EV
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MAGNETO_RESONATIC_CIRCUIT_BOARD)
                .input(gem, MagnetoResonatic)
                .input(MAGNETO_RESONATIC_CIRCUIT_HV)
                .input(SMD_CAPACITOR, 16)
                .input(SMD_TRANSISTOR, 16)
                .input(SMD_DIODE, 16)
                .fluidInputs(SolderingAlloy.getFluid(180))
                .output(MAGNETO_RESONATIC_CIRCUIT_EV, 4)
                .EUt(VA[IV])
                .duration(baseDuration * 5)
                .buildAndRegister();

        //  IV
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MAGNETO_RESONATIC_CIRCUIT_BOARD, 6)
                .input(gem, MagnetoResonatic, 6)
                .input(MAGNETO_RESONATIC_CIRCUIT_EV)
                .input(SMD_CAPACITOR, 16)
                .input(SMD_TRANSISTOR, 16)
                .input(SMD_DIODE, 16)
                .fluidInputs(SolderingAlloy.getFluid(864))
                .output(MAGNETO_RESONATIC_CIRCUIT_IV, 4)
                .EUt(VA[LuV])
                .duration(baseDuration * 6)
                .buildAndRegister();

        //  LuV
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MAGNETO_RESONATIC_CIRCUIT_BOARD, 6)
                .input(gem, MagnetoResonatic, 6)
                .input(MAGNETO_RESONATIC_CIRCUIT_IV)
                .input(ADVANCED_SMD_CAPACITOR, 24)
                .input(ADVANCED_SMD_TRANSISTOR, 24)
                .input(ADVANCED_SMD_DIODE, 24)
                .fluidInputs(SolderingAlloy.getFluid(1008))
                .output(MAGNETO_RESONATIC_CIRCUIT_LuV, 4)
                .EUt(VA[ZPM])
                .duration(baseDuration * 7)
                .buildAndRegister();

        //  ZPM
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MAGNETO_RESONATIC_CIRCUIT_BOARD, 6)
                .input(gemExquisite, MagnetoResonatic)
                .input(MAGNETO_RESONATIC_CIRCUIT_LuV)
                .input(ADVANCED_SMD_CAPACITOR, 24)
                .input(ADVANCED_SMD_TRANSISTOR, 24)
                .input(ADVANCED_SMD_DIODE, 24)
                .fluidInputs(SolderingAlloy.getFluid(4608))
                .output(MAGNETO_RESONATIC_CIRCUIT_ZPM, 4)
                .EUt(VA[UV])
                .duration(baseDuration * 8)
                .buildAndRegister();

        //  UV
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MAGNETO_RESONATIC_CIRCUIT_BOARD, 6)
                .input(gemExquisite, MagnetoResonatic, 6)
                .input(MAGNETO_RESONATIC_CIRCUIT_ZPM)
                .input(ADVANCED_SMD_CAPACITOR, 32)
                .input(ADVANCED_SMD_TRANSISTOR, 32)
                .input(ADVANCED_SMD_DIODE, 32)
                .fluidInputs(SolderingAlloy.getFluid(5184))
                .output(MAGNETO_RESONATIC_CIRCUIT_UV, 4)
                .EUt(VA[UHV])
                .duration(baseDuration * 9)
                .buildAndRegister();

        //  UHV
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MAGNETO_RESONATIC_CIRCUIT_BOARD, 12)
                .input(gemExquisite, MagnetoResonatic, 12)
                .input(MAGNETO_RESONATIC_CIRCUIT_UV)
                .input(OPTICAL_CAPACITOR, 32)
                .input(OPTICAL_TRANSISTOR, 32)
                .input(OPTICAL_DIODE, 32)
                .fluidInputs(SolderingAlloy.getFluid(5760))
                .output(MAGNETO_RESONATIC_CIRCUIT_UHV, 4)
                .EUt(VA[UEV])
                .duration(baseDuration * 10)
                .buildAndRegister();

        //  UEV
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MAGNETO_RESONATIC_CIRCUIT_BOARD, 18)
                .input(gemExquisite, MagnetoResonatic, 18)
                .input(MAGNETO_RESONATIC_CIRCUIT_UHV, 2)
                .input(SPINTRONIC_CAPACITOR, 32)
                .input(SPINTRONIC_TRANSISTOR, 32)
                .input(SPINTRONIC_DIODE, 32)
                .fluidInputs(SolderingAlloy.getFluid(6336))
                .output(MAGNETO_RESONATIC_CIRCUIT_UEV, 4)
                .EUt(VA[UIV])
                .duration(baseDuration * 11)
                .buildAndRegister();

        //  UIV
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MAGNETO_RESONATIC_CIRCUIT_BOARD, 24)
                .input(gemExquisite, MagnetoResonatic, 24)
                .input(MAGNETO_RESONATIC_CIRCUIT_UEV, 4)
                .input(COSMIC_CAPACITOR, 32)
                .input(COSMIC_TRANSISTOR, 32)
                .input(COSMIC_DIODE, 32)
                .fluidInputs(SolderingAlloy.getFluid(6912))
                .output(MAGNETO_RESONATIC_CIRCUIT_UIV, 2)
                .EUt(VA[UXV])
                .duration(baseDuration * 12)
                .buildAndRegister();

        //  UXV
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MAGNETO_RESONATIC_CIRCUIT_BOARD, 30)
                .input(gemExquisite, MagnetoResonatic, 30)
                .input(MAGNETO_RESONATIC_CIRCUIT_UIV, 8)
                .input(SUPRACAUSAL_CAPACITOR, 32)
                .input(SUPRACAUSAL_TRANSISTOR, 32)
                .input(SUPRACAUSAL_DIODE, 32)
                .fluidInputs(SolderingAlloy.getFluid(7488))
                .output(MAGNETO_RESONATIC_CIRCUIT_UXV, 2)
                .EUt(VA[OpV])
                .duration(baseDuration * 13)
                .buildAndRegister();

        //  OpV
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MAGNETO_RESONATIC_CIRCUIT_BOARD, 36)
                .input(gemExquisite, MagnetoResonatic, 36)
                .input(MAGNETO_RESONATIC_CIRCUIT_UXV, 16)
                .input(SUPRACAUSAL_CAPACITOR, 48)
                .input(SUPRACAUSAL_TRANSISTOR, 48)
                .input(SUPRACAUSAL_DIODE, 48)
                .fluidInputs(SolderingAlloy.getFluid(8064))
                .output(MAGNETO_RESONATIC_CIRCUIT_OpV)
                .EUt(VA[MAX])
                .duration(baseDuration * 14)
                .buildAndRegister();

        //  MAX
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MAGNETO_RESONATIC_CIRCUIT_BOARD, 42)
                .input(gemExquisite, MagnetoResonatic, 42)
                .input(MAGNETO_RESONATIC_CIRCUIT_OpV, 32)
                .input(SUPRACAUSAL_CAPACITOR, 64)
                .input(SUPRACAUSAL_TRANSISTOR, 64)
                .input(SUPRACAUSAL_DIODE, 64)
                .fluidInputs(SolderingAlloy.getFluid(8640))
                .output(MAGNETO_RESONATIC_CIRCUIT_MAX)
                .EUt((int) V[MAX])
                .duration(baseDuration * 15)
                .buildAndRegister();
    }
}
