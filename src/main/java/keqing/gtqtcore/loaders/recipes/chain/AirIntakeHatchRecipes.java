package keqing.gtqtcore.loaders.recipes.chain;


import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.BlockMultiblockCasing;
import gregtech.common.blocks.MetaBlocks;

import static gregtech.api.GTValues.IV;
import static gregtech.api.GTValues.ZPM;
import static gregtech.api.unification.material.Materials.RedAlloy;
import static gregtech.api.unification.material.Materials.Redstone;
import static gregtech.api.unification.ore.OrePrefix.circuit;
import static gregtech.api.unification.ore.OrePrefix.plate;
import static gregtech.common.items.MetaItems.FLUID_REGULATOR_IV;
import static gregtech.common.items.MetaItems.FLUID_REGULATOR_ZPM;
import static gregtech.common.metatileentities.MetaTileEntities.FLUID_IMPORT_HATCH;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.AIR_INTAKE_HATCH;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.EXTREME_AIR_INTAKE_HATCH;

public class AirIntakeHatchRecipes {

    public static void register() {
        // Air Intake Hatch
        ModHandler.addShapedRecipe(true, "air_intake_hatch", AIR_INTAKE_HATCH.getStackForm(),
                "AGA", "APA", "XHX",
                'A', new UnificationEntry(plate, Redstone),
                'G', MetaBlocks.MULTIBLOCK_CASING.getItemVariant(BlockMultiblockCasing.MultiblockCasingType.GRATE_CASING),
                'P', FLUID_REGULATOR_IV,
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.IV),
                'H', FLUID_IMPORT_HATCH[IV].getStackForm());

        // Extreme Air Intake Hatch
        ModHandler.addShapedRecipe(true, "advanced_air_intake_hatch", EXTREME_AIR_INTAKE_HATCH.getStackForm(),
                "AGA", "APA", "XHX",
                'A', new UnificationEntry(plate, RedAlloy),
                'G', MetaBlocks.MULTIBLOCK_CASING.getItemVariant(BlockMultiblockCasing.MultiblockCasingType.GRATE_CASING),
                'P', FLUID_REGULATOR_ZPM,
                'X', new UnificationEntry(circuit, MarkerMaterials.Tier.ZPM),
                'H', FLUID_IMPORT_HATCH[ZPM].getStackForm());

        // Ultimate Air Intake Hatch
        // TODO ElectrumFluxed plate, Fluid Regulator UHV, UHV Fluid Import Hatch.
    }

}