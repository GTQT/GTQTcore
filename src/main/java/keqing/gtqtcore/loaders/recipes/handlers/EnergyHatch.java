package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.stack.UnificationEntry;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.ELECTRIC_PUMP_IV;
import static gregtech.common.items.MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static gtqt.api.util.MaterialHelper.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class EnergyHatch {
    public static void init() {
        EnergyHatches1A();
        EnergyHatches4A();
        EnergyHatches16A();
        EnergyHatches64A();
        Transformers();
        PowerTransformers();
        HiAmpTransformers();
    }

    private static void HiAmpTransformers() {

        //  UHV
        ModHandler.addShapedRecipe(true, "machine.transformer.hi_amp.uhv", HI_AMP_TRANSFORMER[UHV].getStackForm(),
                "VQQ", "PH ", "VQQ",
                'V', VOLTAGE_COIL_UHV,
                'H', HULL[UHV].getStackForm(),
                'P', new UnificationEntry(cableGtQuadruple, PedotTMA),
                'Q', new UnificationEntry(cableGtQuadruple, Europium));


        //  UEV
        ModHandler.addShapedRecipe(true, "machine.transformer.hi_amp.uev", HI_AMP_TRANSFORMER[UEV].getStackForm(),
                "VQQ", "PH ", "VQQ",
                'V', VOLTAGE_COIL_UEV,
                'H', HULL[UEV].getStackForm(),
                'P', new UnificationEntry(cableGtQuadruple, Solarium),
                'Q', new UnificationEntry(cableGtQuadruple, PedotTMA));

        //  UIV
        ModHandler.addShapedRecipe(true, "machine.transformer.hi_amp.uiv", HI_AMP_TRANSFORMER[UIV].getStackForm(),
                "VQQ", "PH ", "VQQ",
                'V', VOLTAGE_COIL_UIV,
                'H', HULL[UIV].getStackForm(),
                'P', new UnificationEntry(cableGtQuadruple, Hypogen),
                'Q', new UnificationEntry(cableGtQuadruple, Solarium));
    }

    private static void PowerTransformers() {
        //  UHV
        for (int i = 10; i < UIV; i++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(HI_AMP_TRANSFORMER[UHV])
                    .input(ELECTRIC_PUMP_IV)
                    .input(cableGtOctal, Cable.get(i))
                    .input(cableGtHex, Plate.get(i), 2)
                    .input(springSmall, Plate.get(i))
                    .input(spring, Pipe.get(i))
                    .fluidInputs(Lubricant.getFluid(2000))
                    .output(POWER_TRANSFORMER[i])
                    .EUt(VA[UHV])
                    .duration(200)
                    .buildAndRegister();
        }
    }

    private static void Transformers() {
        //  UHV
        ModHandler.addShapedRecipe(true, "machine.transformer.uhv", TRANSFORMER[UHV].getStackForm(),
                "PWW", "WH ", "PWW",
                'W', new UnificationEntry(cableGtSingle, Europium),
                'P', NANO_POWER_IC,
                'H', HULL[UHV].getStackForm());

        //  UEV
        ModHandler.addShapedRecipe(true, "machine.transformer.uev", TRANSFORMER[UEV].getStackForm(),
                "PWW", "WH ", "PWW",
                'W', new UnificationEntry(cableGtSingle, PedotTMA),
                'P', NANO_POWER_IC,
                'H', HULL[UEV].getStackForm());
    }

    private static void EnergyHatches64A() {
        //UEV+
        // 64A Substation Energy Hatches
        for (int i = 10; i < UIV; i++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(POWER_TRANSFORMER[i])
                    .input(ENERGY_INPUT_HATCH_16A[i])
                    .input(wireGtHex, Cable.get(i), 2)
                    .input(plate, Plate.get(i), 6)
                    .output(SUBSTATION_ENERGY_INPUT_HATCH[i])
                    .duration(400).EUt(VA[ULV + i]).buildAndRegister();
        }

        // 64A Substation Dynamo Hatches
        for (int i = 10; i < UIV; i++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(POWER_TRANSFORMER[i])
                    .input(ENERGY_OUTPUT_HATCH_16A[i])
                    .input(wireGtHex, Cable.get(i), 2)
                    .input(plate, Plate.get(i), 6)
                    .output(SUBSTATION_ENERGY_OUTPUT_HATCH[i])
                    .duration(400).EUt(VA[ULV + i]).buildAndRegister();
        }
    }

    private static void EnergyHatches16A() {
        //UEV+
        // 16A Energy Hatches
        for (int i = 10; i < UIV; i++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(TRANSFORMER[i])
                    .input(ENERGY_INPUT_HATCH_4A[i])
                    .input(wireGtOctal, Cable.get(i), 2)
                    .input(plate, Plate.get(i), 4)
                    .output(ENERGY_INPUT_HATCH_16A[i])
                    .duration(200).EUt(VA[ULV + i]).buildAndRegister();
        }

        // 16A Dynamo Hatches
        for (int i = 10; i < UIV; i++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(TRANSFORMER[i])
                    .input(ENERGY_OUTPUT_HATCH_4A[i])
                    .input(wireGtOctal, Cable.get(i), 2)
                    .input(plate, Plate.get(i), 4)
                    .output(ENERGY_OUTPUT_HATCH_16A[i])
                    .duration(200).EUt(VA[ULV + i]).buildAndRegister();
        }
    }

    private static void EnergyHatches4A() {
        //UEV+
        // 4A Energy Hatches
        for (int i = 10; i < UIV; i++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(ENERGY_INPUT_HATCH[i])
                    .input(wireGtQuadruple, Cable.get(i), 2)
                    .input(plate, Plate.get(i), 2)
                    .output(ENERGY_INPUT_HATCH_4A[i])
                    .duration(100).EUt(VA[ULV + i]).buildAndRegister();
        }

        // 4A Dynamo Hatches
        for (int i = 10; i < UIV; i++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(ENERGY_OUTPUT_HATCH[i])
                    .input(wireGtQuadruple, Cable.get(i), 2)
                    .input(plate, Plate.get(i), 2)
                    .output(ENERGY_OUTPUT_HATCH_4A[i])
                    .duration(100).EUt(VA[ULV + i]).buildAndRegister();
        }
    }

    private static void EnergyHatches1A() {
        //UHV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[UHV])
                .input(cableGtSingle, Europium, 4)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 2)
                .input(circuit, MarkerMaterials.Tier.UHV)
                .input(VOLTAGE_COIL_UHV, 2)
                .fluidInputs(SodiumPotassium.getFluid(12000))
                .fluidInputs(SolderingAlloy.getFluid(5760))
                .output(ENERGY_INPUT_HATCH[UHV])
                .stationResearch(b -> b
                        .researchStack(ENERGY_INPUT_HATCH[UV].getStackForm())
                        .CWUt(CWT[UV])
                        .EUt(VA[UV]))
                .duration(1000).EUt(VA[UHV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[UHV])
                .input(spring, Europium, 4)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 2)
                .input(circuit, MarkerMaterials.Tier.UHV)
                .input(VOLTAGE_COIL_UHV, 2)
                .fluidInputs(SodiumPotassium.getFluid(12000))
                .fluidInputs(SolderingAlloy.getFluid(5760))
                .output(ENERGY_OUTPUT_HATCH[UHV])
                .stationResearch(b -> b
                        .researchStack(ENERGY_OUTPUT_HATCH[UV].getStackForm())
                        .CWUt(CWT[UV])
                        .EUt(VA[UV]))
                .duration(1000).EUt(VA[UHV]).buildAndRegister();

        //  UEV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[UEV])
                .input(cableGtSingle, PedotTMA, 4)
                .input(NANO_POWER_IC, 2)
                .input(circuit, MarkerMaterials.Tier.UEV)
                .input(VOLTAGE_COIL_UEV, 2)
                .fluidInputs(SodiumPotassium.getFluid(14000))
                .fluidInputs(SolderingAlloy.getFluid(11520))
                .output(ENERGY_INPUT_HATCH[UEV])
                .EUt(VA[UEV])
                .duration(1200)
                .stationResearch(b -> b
                        .researchStack(ENERGY_INPUT_HATCH[UHV].getStackForm())
                        .CWUt(CWT[UHV])
                        .EUt(VA[UHV]))
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[UEV])
                .input(spring, PedotTMA, 4)
                .input(NANO_POWER_IC, 2)
                .input(circuit, MarkerMaterials.Tier.UEV)
                .input(VOLTAGE_COIL_UEV, 2)
                .fluidInputs(SodiumPotassium.getFluid(14000))
                .fluidInputs(SolderingAlloy.getFluid(11520))
                .output(ENERGY_OUTPUT_HATCH[UEV])
                .EUt(VA[UEV])
                .duration(1200)
                .stationResearch(b -> b
                        .researchStack(ENERGY_OUTPUT_HATCH[UHV].getStackForm())
                        .CWUt(CWT[UHV])
                        .EUt(VA[UHV]))
                .buildAndRegister();

        //  UIV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[UIV])
                .input(cableGtSingle, Solarium, 4)
                .input(PICO_POWER_IC, 2)
                .input(circuit, MarkerMaterials.Tier.UIV)
                .input(VOLTAGE_COIL_UIV, 2)
                .fluidInputs(SodiumPotassium.getFluid(16000))
                .fluidInputs(SolderingAlloy.getFluid(23040))
                .output(ENERGY_INPUT_HATCH[UIV])
                .EUt(VA[UIV])
                .duration(1400)
                .stationResearch(b -> b
                        .researchStack(ENERGY_INPUT_HATCH[UEV].getStackForm())
                        .CWUt(CWT[UEV])
                        .EUt(VA[UEV]))
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[UIV])
                .input(spring, Solarium, 4)
                .input(PICO_POWER_IC, 2)
                .input(circuit, MarkerMaterials.Tier.UIV)
                .input(VOLTAGE_COIL_UIV, 2)
                .fluidInputs(SodiumPotassium.getFluid(16000))
                .fluidInputs(SolderingAlloy.getFluid(23040))
                .output(ENERGY_OUTPUT_HATCH[UIV])
                .EUt(VA[UIV])
                .duration(1400)
                .stationResearch(b -> b
                        .researchStack(ENERGY_OUTPUT_HATCH[UEV].getStackForm())
                        .CWUt(CWT[UEV])
                        .EUt(VA[UEV]))
                .buildAndRegister();
    }

}
