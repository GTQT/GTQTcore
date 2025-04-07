package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.unification.OreDictUnifier;

import static gregtech.api.GTValues.LV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.EXTRUDER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.ingot;
import static gregtech.common.items.MetaItems.FLUID_CELL;
import static gregtech.common.items.MetaItems.SHAPE_EXTRUDER_CELL;
import static keqing.gtqtcore.api.unification.GCYSMaterials.Kevlar;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Zylon;
import static keqing.gtqtcore.api.utils.GTQTUniversUtil.SECOND;
import static keqing.gtqtcore.api.utils.GTQTUniversUtil.TICK;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.*;

public class ExtruderRecipes {
    public static void init() {
        // Deleted original cell recipes which used PE, PTFE and PBI.
        GTRecipeHandler.removeRecipesByInputs(EXTRUDER_RECIPES,
                OreDictUnifier.get(ingot, Polytetrafluoroethylene),
                SHAPE_EXTRUDER_CELL.getStackForm());

        GTRecipeHandler.removeRecipesByInputs(EXTRUDER_RECIPES,
                OreDictUnifier.get(ingot, Polybenzimidazole),
                SHAPE_EXTRUDER_CELL.getStackForm());

        // Add new empty cell buff recipes.
        EXTRUDER_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_EXTRUDER_CELL)
                .input(ingot, Aluminium)
                .output(FLUID_CELL, 4)
                .EUt(VA[LV])
                .duration(6 * SECOND + 5 * TICK)
                .buildAndRegister();

        EXTRUDER_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_EXTRUDER_CELL)
                .input(ingot, StainlessSteel)
                .output(FLUID_CELL, 16)
                .EUt(VA[LV])
                .duration(6 * SECOND + 5 * TICK)
                .buildAndRegister();

        EXTRUDER_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_EXTRUDER_CELL)
                .input(ingot, Titanium)
                .output(FLUID_CELL, 32)
                .EUt(VA[LV])
                .duration(6 * SECOND + 5 * TICK)
                .buildAndRegister();

        EXTRUDER_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_EXTRUDER_CELL)
                .input(ingot, TungstenSteel)
                .output(FLUID_CELL, 64)
                .EUt(VA[LV])
                .duration(6 * SECOND + 5 * TICK)
                .buildAndRegister();

        // PE Plastic Can
        EXTRUDER_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_EXTRUDER_CELL)
                .input(ingot, Polyethylene, 6)
                .output(PE_CAN)
                .EUt(VA[LV])
                .duration(10 * SECOND)
                .buildAndRegister();

        // PTFE Plastic Can
        EXTRUDER_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_EXTRUDER_CELL)
                .input(ingot, Polytetrafluoroethylene, 6)
                .output(PTFE_CAN)
                .EUt(VA[LV])
                .duration(10 * SECOND)
                .buildAndRegister();

        // PBI Plastic Can
        EXTRUDER_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_EXTRUDER_CELL)
                .input(ingot, Polybenzimidazole, 6)
                .output(PBI_CAN)
                .EUt(VA[LV])
                .duration(10 * SECOND)
                .buildAndRegister();

        EXTRUDER_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_EXTRUDER_CELL)
                .input(ingot, Zylon, 6)
                .output(ZYLON_CAN)
                .EUt(VA[LV])
                .duration(10 * SECOND)
                .buildAndRegister();

        EXTRUDER_RECIPES.recipeBuilder()
                .notConsumable(SHAPE_EXTRUDER_CELL)
                .input(ingot, Kevlar, 6)
                .output(KEVLAR_CAN)
                .EUt(VA[LV])
                .duration(10 * SECOND)
                .buildAndRegister();
    };
}
