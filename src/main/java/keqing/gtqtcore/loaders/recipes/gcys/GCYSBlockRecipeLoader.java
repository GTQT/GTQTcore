package keqing.gtqtcore.loaders.recipes.gcys;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.stack.UnificationEntry;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockCrucible;
import keqing.gtqtcore.common.block.blocks.BlockGCYSMultiblockCasing;
import keqing.gtqtcore.common.block.blocks.BlockGCYSMultiblockCasingActive;

import static gregicality.multiblocks.api.unification.GCYMMaterials.HSLASteel;
import static gregtech.api.GTValues.UV;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;

public class GCYSBlockRecipeLoader {

    public static void init() {
        // Crucibles
        // TODO better recipes for crucibles
        ModHandler.addShapedRecipe(true, "quartz_crucible", GTQTMetaBlocks.CRUCIBLE.getItemVariant(BlockCrucible.CrucibleType.QUARTZ_CRUCIBLE),
                "P P", "PhP", "PPP",
                'P', new UnificationEntry(plate, Quartzite)
        );

        ModHandler.addShapedRecipe(true, "tungsten_crucible", GTQTMetaBlocks.CRUCIBLE.getItemVariant(BlockCrucible.CrucibleType.TUNGSTEN_CRUCIBLE),
                "P P", "PhP", "PPP",
                'P', new UnificationEntry(plateDouble, Tungsten)
        );

        ModHandler.addShapedRecipe(true, "graphite_crucible", GTQTMetaBlocks.CRUCIBLE.getItemVariant(BlockCrucible.CrucibleType.GRAPHITE_CRUCIBLE),
                "P P", "PhP", "PPP",
                'P', new UnificationEntry(plate, Graphene)
        );

        ModHandler.addShapedRecipe(true, "hexagonal_boron_nitride_crucible", GTQTMetaBlocks.CRUCIBLE.getItemVariant(BlockCrucible.CrucibleType.BORON_NITRIDE_CRUCIBLE),
                "P P", "PhP", "PPP",
                'P', new UnificationEntry(plate, HexagonalBoronNitride)
        );

        // Substrates
        ModHandler.addShapedRecipe(true, "substrate", GTQTMetaBlocks.MULTIBLOCK_CASING.getItemVariant(BlockGCYSMultiblockCasing.CasingType.SUBSTRATE),
                "PPP", "RFR", "R R",
                'P', new UnificationEntry(plate, Palladium),
                'R', new UnificationEntry(stick, RedSteel),
                'F', new UnificationEntry(frameGt, BlueSteel)
        );

        ModHandler.addShapedRecipe(true, "advanced_substrate", GTQTMetaBlocks.MULTIBLOCK_CASING.getItemVariant(BlockGCYSMultiblockCasing.CasingType.ADVANCED_SUBSTRATE),
                "PPP", "RFR", "R R",
                'P', new UnificationEntry(plate, Ruridit),
                'R', new UnificationEntry(stick, Duranium),
                'F', new UnificationEntry(frameGt, NaquadahAlloy)
        );

        // Drill Head
        ModHandler.addShapedRecipe(true, "industrial_drill_head", GTQTMetaBlocks.MULTIBLOCK_CASING.getItemVariant(BlockGCYSMultiblockCasing.CasingType.DRILL_HEAD),
                "PGP", "MHM", "SSS",
                'P', ELECTRIC_PISTON_UV.getStackForm(),
                'G', new UnificationEntry(gear, Orichalcum),
                'M', ELECTRIC_MOTOR_UV.getStackForm(),
                'H', HULL[UV].getStackForm(),
                'S', COMPONENT_GRINDER_TUNGSTEN.getStackForm()
        );

        // Airfoil Chambers
        ModHandler.addShapedRecipe(true, "airfoil_chamber", GTQTMetaBlocks.MULTIBLOCK_CASING_ACTIVE.getItemVariant(BlockGCYSMultiblockCasingActive.CasingType.AIRFOIL),
                " IR", "SOS", "RI ",
                'I', new UnificationEntry(ring, StyreneButadieneRubber),
                'R', new UnificationEntry(rotor, TungstenSteel),
                'S', new UnificationEntry(screw, Ultimet),
                'O', new UnificationEntry(stick, HSLASteel)
        );

        ModHandler.addShapedRecipe(true, "advanced_airfoil_chamber", GTQTMetaBlocks.MULTIBLOCK_CASING_ACTIVE.getItemVariant(BlockGCYSMultiblockCasingActive.CasingType.ADVANCED_AIRFOIL),
                " IR", "SOS", "RI ",
                'I', new UnificationEntry(ring, SiliconeRubber),
                'R', new UnificationEntry(rotor, RhodiumPlatedPalladium),
                'S', new UnificationEntry(screw, Osmiridium),
                'O', new UnificationEntry(stickLong, HSSG)
        );
    }
}
