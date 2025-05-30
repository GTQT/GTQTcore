package keqing.gtqtcore.loaders.recipes.component;

import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockTransparentCasing;
import keqing.gtqtcore.common.items.GTQTMetaItems;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.loaders.recipe.CraftingComponent.*;

public class CraftingComponent {

    public static void init() {
        WIRE_ELECTRIC.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.wireGtSingle, Trinium)},
//                {10, new UnificationEntry(OrePrefix.wireGtSingle, Trinium)},
//                {11, new UnificationEntry(OrePrefix.wireGtSingle, Trinium)},
//                {12, new UnificationEntry(OrePrefix.wireGtSingle, Trinium)},
//                {13, new UnificationEntry(OrePrefix.wireGtSingle, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        WIRE_QUAD.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.wireGtQuadruple, Europium)},
                {10, new UnificationEntry(OrePrefix.wireGtQuadruple, GTQTMaterials.PedotTMA)},
//                {11, new UnificationEntry(OrePrefix.wireGtQuadruple, Trinium)},
//                {12, new UnificationEntry(OrePrefix.wireGtQuadruple, Trinium)},
//                {13, new UnificationEntry(OrePrefix.wireGtQuadruple, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        WIRE_OCT.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.wireGtOctal, Europium)},
                {10, new UnificationEntry(OrePrefix.wireGtOctal, GTQTMaterials.PedotTMA)},
//                {11, new UnificationEntry(OrePrefix.wireGtOctal, Trinium)},
//                {12, new UnificationEntry(OrePrefix.wireGtOctal, Trinium)},
//                {13, new UnificationEntry(OrePrefix.wireGtOctal, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        WIRE_HEX.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.wireGtHex, Europium)},
                {10, new UnificationEntry(OrePrefix.wireGtHex, GTQTMaterials.PedotTMA)},
//                {11, new UnificationEntry(OrePrefix.wireGtHex, Trinium)},
//                {12, new UnificationEntry(OrePrefix.wireGtHex, Trinium)},
//                {13, new UnificationEntry(OrePrefix.wireGtHex, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE.appendIngredients(Stream.of(new Object[][]{
                {10, new UnificationEntry(OrePrefix.cableGtSingle, GTQTMaterials.PedotTMA)},
//                {11, new UnificationEntry(OrePrefix.cableGtSingle, Trinium)},
//                {12, new UnificationEntry(OrePrefix.cableGtSingle, Trinium)},
//                {13, new UnificationEntry(OrePrefix.cableGtSingle, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_QUAD.appendIngredients(Stream.of(new Object[][]{
                {10, new UnificationEntry(OrePrefix.cableGtQuadruple, GTQTMaterials.PedotTMA)},
//                {11, new UnificationEntry(OrePrefix.cableGtQuadruple, Trinium)},
//                {12, new UnificationEntry(OrePrefix.cableGtQuadruple, Trinium)},
//                {13, new UnificationEntry(OrePrefix.cableGtQuadruple, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_OCT.appendIngredients(Stream.of(new Object[][]{
                {10, new UnificationEntry(OrePrefix.cableGtOctal, GTQTMaterials.PedotTMA)},
//                {11, new UnificationEntry(OrePrefix.cableGtOctal, Trinium)},
//                {12, new UnificationEntry(OrePrefix.cableGtOctal, Trinium)},
//                {13, new UnificationEntry(OrePrefix.cableGtOctal, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_HEX.appendIngredients(Stream.of(new Object[][]{
                {10, new UnificationEntry(OrePrefix.cableGtHex, GTQTMaterials.PedotTMA)},
//                {11, new UnificationEntry(OrePrefix.cableGtHex, Trinium)},
//                {12, new UnificationEntry(OrePrefix.cableGtHex, Trinium)},
//                {13, new UnificationEntry(OrePrefix.cableGtHex, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_TIER_UP.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.cableGtSingle, GTQTMaterials.PedotTMA)},
//                {10, new UnificationEntry(OrePrefix.cableGtSingle, Trinium)},
//                {11, new UnificationEntry(OrePrefix.cableGtSingle, Trinium)},
//                {12, new UnificationEntry(OrePrefix.cableGtSingle, Trinium)},
//                {13, new UnificationEntry(OrePrefix.cableGtSingle, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        PIPE_NORMAL.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.pipeNormalFluid, Duranium)},
//                {10, new UnificationEntry(OrePrefix.pipeNormalFluid, Trinium)},
//                {11, new UnificationEntry(OrePrefix.pipeNormalFluid, Trinium)},
//                {12, new UnificationEntry(OrePrefix.pipeNormalFluid, Trinium)},
//                {13, new UnificationEntry(OrePrefix.pipeNormalFluid, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        PIPE_LARGE.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.pipeLargeFluid, Duranium)},
//                {10, new UnificationEntry(OrePrefix.pipeNormalFluid, Trinium)},
//                {11, new UnificationEntry(OrePrefix.pipeNormalFluid, Trinium)},
//                {12, new UnificationEntry(OrePrefix.pipeNormalFluid, Trinium)},
//                {13, new UnificationEntry(OrePrefix.pipeNormalFluid, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        GLASS.appendIngredients(Stream.of(new Object[][]{
                {9, GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.PMMA)},
                {10, GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.PMMA)},
//                {11, GCYSMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.)},
//                {12, GCYSMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.)},
//                {13, GCYSMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        PLATE.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.plate, GTQTMaterials.Orichalcum)},
                {10, new UnificationEntry(OrePrefix.plate, GTQTMaterials.Adamantium)},
//                {11, new UnificationEntry(OrePrefix.plate, Trinium)},
//                {12, new UnificationEntry(OrePrefix.plate, Trinium)},
//                {13, new UnificationEntry(OrePrefix.plate, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        HULL_PLATE.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.plate, Polybenzimidazole)},
                {10, new UnificationEntry(OrePrefix.plate, GTQTMaterials.Kevlar)},
//                {11, new UnificationEntry(OrePrefix.plate, Trinium)},
//                {12, new UnificationEntry(OrePrefix.plate, Trinium)},
//                {13, new UnificationEntry(OrePrefix.plate, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        ROTOR.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.rotor, GTQTMaterials.Orichalcum)},
                {10, new UnificationEntry(OrePrefix.rotor, GTQTMaterials.Adamantium)},
//                {11, new UnificationEntry(OrePrefix.rotor, Trinium)},
//                {12, new UnificationEntry(OrePrefix.rotor, Trinium)},
//                {13, new UnificationEntry(OrePrefix.rotor, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        GRINDER.appendIngredients(Stream.of(new Object[][]{
                {6, MetaItems.COMPONENT_GRINDER_TUNGSTEN.getStackForm()},
                {7, MetaItems.COMPONENT_GRINDER_TUNGSTEN.getStackForm()},
                {8, MetaItems.COMPONENT_GRINDER_TUNGSTEN.getStackForm()},
                {9, GTQTMetaItems.COMPONENT_GRINDER_BORON_NITRIDE.getStackForm()},
                {10, GTQTMetaItems.COMPONENT_GRINDER_BORON_NITRIDE.getStackForm()},
                {11, GTQTMetaItems.COMPONENT_GRINDER_BORON_NITRIDE.getStackForm()},
//                {12, GCMetaItems.},
//                {13, GCMetaItems.},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        SAWBLADE.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.toolHeadBuzzSaw, GTQTMaterials.CubicBoronNitride)},
//                {10, new UnificationEntry(OrePrefix.toolHeadBuzzSaw, Trinium)},
//                {11, new UnificationEntry(OrePrefix.toolHeadBuzzSaw, Trinium)},
//                {12, new UnificationEntry(OrePrefix.toolHeadBuzzSaw, Trinium)},
//                {13, new UnificationEntry(OrePrefix.toolHeadBuzzSaw, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        COIL_HEATING.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.wireGtDouble, Trinium)},
                {10, new UnificationEntry(OrePrefix.wireGtDouble, Tritanium)},
//                {11, new UnificationEntry(OrePrefix.wireGtDouble, Trinium)},
//                {12, new UnificationEntry(OrePrefix.wireGtDouble, Trinium)},
//                {13, new UnificationEntry(OrePrefix.wireGtDouble, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        COIL_HEATING_DOUBLE.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.wireGtQuadruple, Trinium)},
                {11, new UnificationEntry(OrePrefix.wireGtQuadruple, Tritanium)},
//                {11, new UnificationEntry(OrePrefix.wireGtDouble, Trinium)},
//                {12, new UnificationEntry(OrePrefix.wireGtDouble, Trinium)},
//                {13, new UnificationEntry(OrePrefix.wireGtDouble, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        COIL_ELECTRIC.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.wireGtOctal, GTQTMaterials.MercuryCadmiumTelluride)},
//                {10, new UnificationEntry(OrePrefix.wireGtOctal, Trinium)},
//                {11, new UnificationEntry(OrePrefix.wireGtOctal, Trinium)},
//                {12, new UnificationEntry(OrePrefix.wireGtOctal, Trinium)},
//                {13, new UnificationEntry(OrePrefix.wireGtOctal, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        STICK_MAGNETIC.appendIngredients(Stream.of(new Object[][]{
//                {9, new UnificationEntry(OrePrefix.stickLong, Trinium)},
//                {10, new UnificationEntry(OrePrefix.stickLong, Trinium)},
//                {11, new UnificationEntry(OrePrefix.stickLong, Trinium)},
//                {12, new UnificationEntry(OrePrefix.stickLong, Trinium)},
//                {13, new UnificationEntry(OrePrefix.stickLong, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        STICK_DISTILLATION.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.spring, Trinium)},
                {10, new UnificationEntry(OrePrefix.spring, Tritanium)},
//                {11, new UnificationEntry(OrePrefix.spring, Trinium)},
//                {12, new UnificationEntry(OrePrefix.spring, Trinium)},
//                {13, new UnificationEntry(OrePrefix.spring, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        STICK_ELECTROMAGNETIC.appendIngredients(Stream.of(new Object[][]{
                {5, new UnificationEntry(OrePrefix.stick, VanadiumGallium)},
                {6, new UnificationEntry(OrePrefix.stick, VanadiumGallium)},
                {7, new UnificationEntry(OrePrefix.stick, VanadiumGallium)},
                {8, new UnificationEntry(OrePrefix.stick, VanadiumGallium)},
                {9, new UnificationEntry(OrePrefix.stickLong, VanadiumGallium)},
                {10, new UnificationEntry(OrePrefix.stick, GTQTMaterials.CarbonNanotube)},
                {11, new UnificationEntry(OrePrefix.stick, GTQTMaterials.CarbonNanotube)},
                {12, new UnificationEntry(OrePrefix.stickLong, GTQTMaterials.CarbonNanotube)},
                {13, new UnificationEntry(OrePrefix.stick, GTQTMaterials.CarbonNanotube)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        STICK_RADIOACTIVE.appendIngredients(Stream.of(new Object[][]{
//                {9, new UnificationEntry(OrePrefix.stick, Trinium)},
//                {9, new UnificationEntry(OrePrefix.stick, Trinium)},
//                {10, new UnificationEntry(OrePrefix.stick, Trinium)},
//                {11, new UnificationEntry(OrePrefix.stick, Trinium)},
//                {12, new UnificationEntry(OrePrefix.stick, Trinium)},
//                {13, new UnificationEntry(OrePrefix.stick, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        PIPE_REACTOR.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.pipeNormalFluid, Polybenzimidazole)},
                {10, new UnificationEntry(OrePrefix.pipeLargeFluid, Polybenzimidazole)},
                {11, new UnificationEntry(OrePrefix.pipeHugeFluid, Polybenzimidazole)},
//                {12, new UnificationEntry(OrePrefix.pipeLargeFluid, Trinium)},
//                {13, new UnificationEntry(OrePrefix.pipeHugeFluid, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        POWER_COMPONENT.appendIngredients(Stream.of(new Object[][]{
                {9, GTQTMetaItems.NANO_POWER_IC},
                {10, GTQTMetaItems.NANO_POWER_IC},
                {11, GTQTMetaItems.PICO_POWER_IC},
                {12, GTQTMetaItems.PICO_POWER_IC},
                {13, GTQTMetaItems.FEMTO_POWER_IC},
                {14, GTQTMetaItems.FEMTO_POWER_IC},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        VOLTAGE_COIL.appendIngredients(Stream.of(new Object[][]{
                {9, GTQTMetaItems.VOLTAGE_COIL_UHV},
                {10, GTQTMetaItems.VOLTAGE_COIL_UEV},
                {11, GTQTMetaItems.VOLTAGE_COIL_UIV},
                {12, GTQTMetaItems.VOLTAGE_COIL_UXV},
                {13, GTQTMetaItems.VOLTAGE_COIL_OpV},
                {14, GTQTMetaItems.VOLTAGE_COIL_MAX},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        SPRING.appendIngredients(Stream.of(new Object[][]{
//                {10, new UnificationEntry(OrePrefix.spring, Trinium)},
//                {11, new UnificationEntry(OrePrefix.spring, Trinium)},
//                {12, new UnificationEntry(OrePrefix.spring, Trinium)},
//                {13, new UnificationEntry(OrePrefix.spring, Trinium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));
    }
}
