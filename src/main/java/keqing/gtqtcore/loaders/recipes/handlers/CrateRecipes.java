package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.stack.UnificationEntry;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;

import static gregtech.api.GTValues.LV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Neutronium;
import static gregtech.api.unification.ore.OrePrefix.plate;
import static gregtech.api.unification.ore.OrePrefix.stickLong;
import static keqing.gtqtcore.api.utils.GTQTUniversUtil.SECOND;

public class CrateRecipes {
    public static void init() {
        // Rhodium Plated Palladium Crate
        ModHandler.addShapedRecipe(true, "rhodium_plated_palladium_crate", GTQTMetaTileEntities.RHODIUM_PLATED_PALLADIUM_CRATE.getStackForm(),
                "SPS", "PhP", "SPS",
                'P', new UnificationEntry(plate, RhodiumPlatedPalladium),
                'S', new UnificationEntry(stickLong, RhodiumPlatedPalladium));

        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .input(stickLong, RhodiumPlatedPalladium, 4)
                .input(plate, RhodiumPlatedPalladium, 4)
                .output(GTQTMetaTileEntities.RHODIUM_PLATED_PALLADIUM_CRATE)
                .EUt(VA[LV])
                .duration(10 * SECOND)
                .buildAndRegister();

        // Naquadah Alloy Crate
        ModHandler.addShapedRecipe(true, "naquadah_alloy_crate", GTQTMetaTileEntities.NAQUADAH_ALLOY_CRATE.getStackForm(),
                "SPS", "PhP", "SPS",
                'P', new UnificationEntry(plate, NaquadahAlloy),
                'S', new UnificationEntry(stickLong, NaquadahAlloy));

        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .input(stickLong, NaquadahAlloy, 4)
                .input(plate, NaquadahAlloy, 4)
                .output(GTQTMetaTileEntities.NAQUADAH_ALLOY_CRATE)
                .EUt(VA[LV])
                .duration(10 * SECOND)
                .buildAndRegister();

        // Darmstadtium Crate
        ModHandler.addShapedRecipe(true, "darmstadtium_crate", GTQTMetaTileEntities.DARMSTADTIUM_CRATE.getStackForm(),
                "SPS", "PhP", "SPS",
                'P', new UnificationEntry(plate, Darmstadtium),
                'S', new UnificationEntry(stickLong, Darmstadtium));

        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .input(stickLong, Darmstadtium, 4)
                .input(plate, Darmstadtium, 4)
                .output(GTQTMetaTileEntities.DARMSTADTIUM_CRATE)
                .EUt(VA[LV])
                .duration(10 * SECOND)
                .buildAndRegister();

        // Neutronium Crate
        ModHandler.addShapedRecipe(true, "neutronium_crate", GTQTMetaTileEntities.NEUTRONIUM_CRATE.getStackForm(),
                "SPS", "PhP", "SPS",
                'P', new UnificationEntry(plate, Neutronium),
                'S', new UnificationEntry(stickLong, Neutronium));

        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .input(stickLong, Neutronium, 4)
                .input(plate, Neutronium, 4)
                .output(GTQTMetaTileEntities.NEUTRONIUM_CRATE)
                .EUt(VA[LV])
                .duration(10 * SECOND)
                .buildAndRegister();

    }

}
