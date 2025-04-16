package keqing.gtqtcore.api.utils;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.recipes.ingredients.GTRecipeItemInput;
import gregtech.api.recipes.ingredients.GTRecipeOreInput;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Polybenzimidazole;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Kevlar;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Infinity;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Polyetheretherketone;

public class GTRecipeHelper {
    private static GTRecipeInput getGtRecipeInput(Object extraInput) {
        GTRecipeInput extra; //  Used to check {@code extraInput} type.
        if (extraInput instanceof ItemStack stack) { //  If {@code extraInput} is Item Stack, then create a {@code GTRecipeItemInput}.
            extra = new GTRecipeItemInput(stack);
        } else if (extraInput instanceof String oreDict) { //  If {@code extraInput} is Ore Dictionary Name, then create a {@code GTRecipeOreInput}.
            extra = new GTRecipeOreInput(oreDict);
        } else {
            throw new IllegalArgumentException("extraInput must be ItemStack or GTRecipeInput.");
        }
        return extra;
    }
    private static int getFluidAmount(int offsetTier) {
        return switch (offsetTier) {
            case ULV -> 4;
            case LV  -> L / 16; // 9
            case MV  -> L / 8;  // 18
            case HV  -> L / 4;  // 36
            case EV  -> L / 2;  // 72
            case IV  -> L;      // 144
            case LuV -> L * 2;  // 288
            case ZPM -> L * 3;  // 432
            case UV  -> L * 4;  // 576
            case UHV -> L * 5;  // 720
            case UEV -> L * 6;  // 864
            case UIV -> L * 7;  // 1008
            case UXV -> L * 8;  // 1152
            case OpV -> L * 9;  // 1296
            default -> 1;
        };
    }
    public static void createIOPartRecipe(int tier, MetaTileEntity input, MetaTileEntity output, Object extraInput) {
        GTRecipeInput extra = getGtRecipeInput(extraInput);
        //  Glue recipe for ULV and LV.
        if (tier <= LV) {
            int fluidAmount = tier == ULV ? 250 : 500;
            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .input(HULL[tier])
                    .inputs(extra)
                    .fluidInputs(Glue.getFluid(fluidAmount))
                    .output(input)
                    .EUt(VA[tier])
                    .duration(15 * SECOND)
                    .buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(2)
                    .input(HULL[tier])
                    .inputs(extra)
                    .fluidInputs(Glue.getFluid(fluidAmount))
                    .output(output)
                    .EUt(VA[tier])
                    .duration(15 * SECOND)
                    .buildAndRegister();
            return;
        }

        //  PE recipe for HV and below.
        if (tier <= HV) {
            int peAmount = getFluidAmount(tier + 4);
            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .input(HULL[tier])
                    .inputs(extra)
                    .fluidInputs(Polyethylene.getFluid(peAmount))
                    .output(input)
                    .EUt(VA[tier])
                    .duration(15 * SECOND)
                    .buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(2)
                    .input(HULL[tier])
                    .inputs(extra)
                    .fluidInputs(Polyethylene.getFluid(peAmount))
                    .output(output)
                    .EUt(VA[tier])
                    .duration(15 * SECOND)
                    .buildAndRegister();
            return;
        }

        //  PTFE recipe for LuV and below.
        if (tier <= LuV) {
            int ptfeAmount = getFluidAmount(tier + 3);
            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .input(HULL[tier])
                    .inputs(extra)
                    .fluidInputs(Polytetrafluoroethylene.getFluid(ptfeAmount))
                    .output(input)
                    .EUt(VA[tier])
                    .duration(15 * SECOND)
                    .buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(2)
                    .input(HULL[tier])
                    .inputs(extra)
                    .fluidInputs(Polytetrafluoroethylene.getFluid(ptfeAmount))
                    .output(output)
                    .EUt(VA[tier])
                    .duration(15 * SECOND)
                    .buildAndRegister();
            return;
        }

        //  PBI recipe for UV and below.
        if (tier <= UV) {
            int pbiAmount = getFluidAmount(tier);
            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .input(HULL[tier])
                    .inputs(extra)
                    .fluidInputs(Polybenzimidazole.getFluid(pbiAmount))
                    .output(input)
                    .EUt(VA[tier])
                    .duration(15 * SECOND)
                    // .withRecycling()
                    .buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(2)
                    .input(HULL[tier])
                    .inputs(extra)
                    .fluidInputs(Polybenzimidazole.getFluid(pbiAmount))
                    .output(output)
                    .EUt(VA[tier])
                    .duration(15 * SECOND)
                    // .withRecycling()
                    .buildAndRegister();
            return;
        }

        //  PEEK recipe for UEV and below.
        if (tier <= UEV) {
            int peekAmount = getFluidAmount(tier - 1);
            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .input(HULL[tier])
                    .inputs(extra)
                    .fluidInputs(Polyetheretherketone.getFluid(peekAmount))
                    .output(input)
                    .EUt(VA[tier])
                    .duration(15 * SECOND)
                    // .withRecycling()
                    .buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(2)
                    .input(HULL[tier])
                    .inputs(extra)
                    .fluidInputs(Polyetheretherketone.getFluid(peekAmount))
                    .output(output)
                    .EUt(VA[tier])
                    .duration(15 * SECOND)
                    // .withRecycling()
                    .buildAndRegister();
            return;
        }

        //  Kevlar recipe for UXV and below
        if (tier <= UXV) {
            int kevlarAmount = getFluidAmount(tier - 2);
            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .input(HULL[tier])
                    .inputs(extra)
                    .fluidInputs(Kevlar.getFluid(kevlarAmount))
                    .output(input)
                    .EUt(VA[tier])
                    .duration(15 * SECOND)
                    // .withRecycling()
                    .buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(2)
                    .input(HULL[tier])
                    .inputs(extra)
                    .fluidInputs(Kevlar.getFluid(kevlarAmount))
                    .output(output)
                    .EUt(VA[tier])
                    .duration(15 * SECOND)
                    // .withRecycling()
                    .buildAndRegister();
            return;

        }

        //  Cosmic Fabric recipe for MAX and below
        if (tier <= MAX) {
            int cosmicFabricAmount = getFluidAmount(tier - 4);
            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .input(HULL[tier])
                    .inputs(extra)
                    .fluidInputs(Infinity.getFluid(cosmicFabricAmount))
                    .output(input)
                    .EUt(VA[tier])
                    .duration(15 * SECOND)
                    // .withRecycling()
                    .buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(2)
                    .input(HULL[tier])
                    .inputs(extra)
                    .fluidInputs(Infinity.getFluid(cosmicFabricAmount))
                    .output(output)
                    .EUt(VA[tier])
                    .duration(15 * SECOND)
                    // .withRecycling()
                    .buildAndRegister();

        }

    }

    /**
     * Add Convension Recipes of I/O Buses/Hatches.
     *
     * @param tier     Bus/Hatch tier, used {@code tier} of voltages.
     * @param input    Input Bus/Hatch.
     * @param output   Output Bus/Hatch.
     * @param isFluid  Check if Buses/Hatches is Fluid Buses.
     */
    public static void createIOPartConv(int tier, MetaTileEntity input, MetaTileEntity output, boolean isFluid) {
        if (isFluid) {
            ModHandler.addShapedRecipe(true, "fluid_hatch_input_to_output_" + tier, input.getStackForm(),
                    " d ", " H ", "   ",
                    'H', output.getStackForm());
            ModHandler.addShapedRecipe(true, "fluid_hatch_output_to_input_" + tier, output.getStackForm(),
                    " d ", " H ", "   ",
                    'H', input.getStackForm());
        } else {
            ModHandler.addShapedRecipe(true, "item_hatch_input_to_output_" + tier, input.getStackForm(),
                    " d ", " H ", "   ",
                    'H', output.getStackForm());
            ModHandler.addShapedRecipe(true, "item_hatch_output_to_input_" + tier, output.getStackForm(),
                    " d ", " H ", "   ",
                    'H', input.getStackForm());
        }
    }

    /**
     * Add Convension Recipes of I/O Buses/Hatches.
     *
     * @param tier     Bus/Hatch tier, used {@code tier} of voltages.
     * @param type     Type of Bus/Hatch, means 4x or 9x (only be useful for multi fluid hatches).
     * @param input    Input Bus/Hatch.
     * @param output   Output Bus/Hatch.
     * @param isFluid  Check if Buses/Hatches is Fluid Buses.
     */
    public static void createIOPartConv(int tier, int type, MetaTileEntity input, MetaTileEntity output, boolean isFluid) {
        if (isFluid) {
            ModHandler.addShapedRecipe(true,
                    type == 4 ? "quadruple_fluid_hatch_input_to_output_" + tier
                            : (type == 9 ? "nonuple_fluid_hatch_input_to_output_" + tier : "fluid_hatch_input_to_output_" + tier), input.getStackForm(),
                    " d ", " H ", "   ",
                    'H', output.getStackForm());
            ModHandler.addShapedRecipe(true,
                    type == 4 ? "quadruple_fluid_hatch_input_to_output_" + tier
                            : (type == 9 ? "nonuple_fluid_hatch_input_to_output_" + tier : "fluid_hatch_output_to_input_" + tier), output.getStackForm(),
                    " d ", " H ", "   ",
                    'H', input.getStackForm());
        } else {
            ModHandler.addShapedRecipe(true, "item_hatch_input_to_output_" + tier, input.getStackForm(),
                    " d ", " H ", "   ",
                    'H', output.getStackForm());
            ModHandler.addShapedRecipe(true, "item_hatch_output_to_input_" + tier, output.getStackForm(),
                    " d ", " H ", "   ",
                    'H', input.getStackForm());
        }
    }
}
