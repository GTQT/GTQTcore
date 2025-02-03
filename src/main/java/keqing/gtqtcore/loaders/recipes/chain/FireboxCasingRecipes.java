package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.ConfigHolder;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Neutronium;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.common.block.blocks.BlockFireboxCasing.FireboxCasingType.*;

public class FireboxCasingRecipes {

    public static void register() {
        int outputAmount = ConfigHolder.recipes.casingsPerCraft;

        // Stainless Steel Firebox Casing
        ModHandler.addShapedRecipe(true, "casing_stainless_steel_firebox", GTQTMetaBlocks.FIREBOX_CASING.getItemVariant(STAINLESS_STEEL_FIREBOX, outputAmount),
                "PSP", "SFS", "PSP",
                'P', new UnificationEntry(plate, StainlessSteel),
                'S', new UnificationEntry(stick, StainlessSteel),
                'F', new UnificationEntry(frameGt, StainlessSteel));

        // Rhodium Plated Palladium Firebox Casing
        ModHandler.addShapedRecipe(true, "casing_rhodium_plated_palladium_firebox", GTQTMetaBlocks.FIREBOX_CASING.getItemVariant(RHODIUM_PLATED_PALLADIUM_FIREBOX, outputAmount),
                "PSP", "SFS", "PSP",
                'P', new UnificationEntry(plate, RhodiumPlatedPalladium),
                'S', new UnificationEntry(stick, RhodiumPlatedPalladium),
                'F', new UnificationEntry(frameGt, RhodiumPlatedPalladium));

        // Naquadah Alloy Firebox Casing
        ModHandler.addShapedRecipe(true, "casing_naquadah_alloy_firebox", GTQTMetaBlocks.FIREBOX_CASING.getItemVariant(NAQUADAH_ALLOY_FIREBOX, outputAmount),
                "PSP", "SFS", "PSP",
                'P', new UnificationEntry(plate, NaquadahAlloy),
                'S', new UnificationEntry(stick, NaquadahAlloy),
                'F', new UnificationEntry(frameGt, NaquadahAlloy));

        // Darmstadtium Firebox Casing
        ModHandler.addShapedRecipe(true, "casing_darmstadtium_firebox", GTQTMetaBlocks.FIREBOX_CASING.getItemVariant(DARMSTADTIUM_FIREBOX, outputAmount),
                "PSP", "SFS", "PSP",
                'P', new UnificationEntry(plate, Darmstadtium),
                'S', new UnificationEntry(stick, Darmstadtium),
                'F', new UnificationEntry(frameGt, Darmstadtium));

        // Neutronium Firebox Casing
        ModHandler.addShapedRecipe(true, "casing_neutronium_firebox", GTQTMetaBlocks.FIREBOX_CASING.getItemVariant(NEUTRONIUM_FIREBOX, outputAmount),
                "PSP", "SFS", "PSP",
                'P', new UnificationEntry(plate, Neutronium),
                'S', new UnificationEntry(stick, Neutronium),
                'F', new UnificationEntry(frameGt, Neutronium));

    }

}
