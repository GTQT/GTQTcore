package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.stack.UnificationEntry;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing2;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasingActive;

import static gregicality.multiblocks.api.unification.GCYMMaterials.HSLASteel;
import static gregtech.api.GTValues.UV;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;

public class GCYSBlockRecipeLoader {

    public static void init() {
        // Drill Head
        ModHandler.addShapedRecipe(true, "industrial_drill_head", GTQTMetaBlocks.blockMultiblockCasing2.getItemVariant(BlockMultiblockCasing2.CasingType.DRILL_HEAD),
                "PGP", "MHM", "SSS",
                'P', ELECTRIC_PISTON_UV.getStackForm(),
                'G', new UnificationEntry(gear, GTQTMaterials.Orichalcum),
                'M', ELECTRIC_MOTOR_UV.getStackForm(),
                'H', HULL[UV].getStackForm(),
                'S', COMPONENT_GRINDER_TUNGSTEN.getStackForm()
        );

        // Airfoil Chambers
        ModHandler.addShapedRecipe(true, "airfoil_chamber", GTQTMetaBlocks.blockMultiblockCasingActive.getItemVariant(BlockMultiblockCasingActive.CasingType.AIRFOIL),
                " IR", "SOS", "RI ",
                'I', new UnificationEntry(ring, StyreneButadieneRubber),
                'R', new UnificationEntry(rotor, TungstenSteel),
                'S', new UnificationEntry(screw, Ultimet),
                'O', new UnificationEntry(stick, HSLASteel)
        );

        ModHandler.addShapedRecipe(true, "advanced_airfoil_chamber", GTQTMetaBlocks.blockMultiblockCasingActive.getItemVariant(BlockMultiblockCasingActive.CasingType.ADVANCED_AIRFOIL),
                " IR", "SOS", "RI ",
                'I', new UnificationEntry(ring, SiliconeRubber),
                'R', new UnificationEntry(rotor, RhodiumPlatedPalladium),
                'S', new UnificationEntry(screw, Osmiridium),
                'O', new UnificationEntry(stickLong, HSSG)
        );
    }
}
