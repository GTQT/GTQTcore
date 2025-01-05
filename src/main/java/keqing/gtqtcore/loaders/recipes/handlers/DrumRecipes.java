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

public class DrumRecipes {
    public static void init() {
        // Rhodium Plated Palladium Drum
        ModHandler.addShapelessNBTClearingRecipe("drum_nbt_rhodium_plated_palladium",
                GTQTMetaTileEntities.RHODIUM_PLATED_PALLADIUM_DRUM.getStackForm(),
                GTQTMetaTileEntities.RHODIUM_PLATED_PALLADIUM_DRUM.getStackForm());

        ModHandler.addShapedRecipe(true, "rhodium_plated_palladium_drum", GTQTMetaTileEntities.RHODIUM_PLATED_PALLADIUM_DRUM.getStackForm(),
                " h ", "PSP", "PSP",
                'P', new UnificationEntry(plate, RhodiumPlatedPalladium),
                'S', new UnificationEntry(stickLong, RhodiumPlatedPalladium));

        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .input(stickLong, RhodiumPlatedPalladium, 2)
                .input(plate, RhodiumPlatedPalladium, 4)
                .output(GTQTMetaTileEntities.RHODIUM_PLATED_PALLADIUM_DRUM)
                .EUt(VA[LV])
                .duration(10 * SECOND)
                .buildAndRegister();

        // Naquadah Alloy Drum
        ModHandler.addShapelessNBTClearingRecipe("drum_nbt_naquadah_alloy",
                GTQTMetaTileEntities.NAQUADAH_ALLOY_DRUM.getStackForm(),
                GTQTMetaTileEntities.NAQUADAH_ALLOY_DRUM.getStackForm());

        ModHandler.addShapedRecipe(true, "naquadah_alloy_drum", GTQTMetaTileEntities.NAQUADAH_ALLOY_DRUM.getStackForm(),
                " h ", "PSP", "PSP",
                'P', new UnificationEntry(plate, NaquadahAlloy),
                'S', new UnificationEntry(stickLong, NaquadahAlloy));

        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .input(stickLong, NaquadahAlloy, 2)
                .input(plate, NaquadahAlloy, 4)
                .output(GTQTMetaTileEntities.NAQUADAH_ALLOY_DRUM)
                .EUt(VA[LV])
                .duration(10 * SECOND)
                .buildAndRegister();

        // Darmstadtium Drum
        ModHandler.addShapelessNBTClearingRecipe("drum_nbt_darmstadtium",
                GTQTMetaTileEntities.DARMSTADTIUM_DRUM.getStackForm(),
                GTQTMetaTileEntities.DARMSTADTIUM_DRUM.getStackForm());

        ModHandler.addShapedRecipe(true, "darmstadtium_drum", GTQTMetaTileEntities.DARMSTADTIUM_DRUM.getStackForm(),
                " h ", "PSP", "PSP",
                'P', new UnificationEntry(plate, Darmstadtium),
                'S', new UnificationEntry(stickLong, Darmstadtium));

        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .input(stickLong, Darmstadtium, 2)
                .input(plate, Darmstadtium, 4)
                .output(GTQTMetaTileEntities.DARMSTADTIUM_DRUM)
                .EUt(VA[LV])
                .duration(10 * SECOND)
                .buildAndRegister();

        // Neutronium Drum
        ModHandler.addShapelessNBTClearingRecipe("drum_nbt_neutronium",
                GTQTMetaTileEntities.NEUTRONIUM_DRUM.getStackForm(),
                GTQTMetaTileEntities.NEUTRONIUM_DRUM.getStackForm());

        ModHandler.addShapedRecipe(true, "neutronium_drum", GTQTMetaTileEntities.NEUTRONIUM_DRUM.getStackForm(),
                " h ", "PSP", "PSP",
                'P', new UnificationEntry(plate, Neutronium),
                'S', new UnificationEntry(stickLong, Neutronium));

        ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .input(stickLong, Neutronium, 2)
                .input(plate, Neutronium, 4)
                .output(GTQTMetaTileEntities.NEUTRONIUM_DRUM)
                .EUt(VA[LV])
                .duration(10 * SECOND)
                .buildAndRegister();

    }
}
