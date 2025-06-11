package keqing.gtqtcore.loaders.recipes.component;

import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockTransparentCasing;
import keqing.gtqtcore.common.items.GTQTMetaItems;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.loaders.recipe.CraftingComponent.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class MaterialComponents {
    public static void init() {
        WIRE_ELECTRIC.appendIngredients(Stream.of(new Object[][]{
                {10, new UnificationEntry(OrePrefix.wireGtSingle, Tritanium)},
                {11, new UnificationEntry(OrePrefix.wireGtSingle, Adamantium)},
                {12, new UnificationEntry(OrePrefix.wireGtSingle, Ichorium)},
                //{13, new UnificationEntry(OrePrefix.wireGtSingle, Astralium)},
                //{14, new UnificationEntry(OrePrefix.wireGtSingle, Hikarium)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        WIRE_QUAD.appendIngredients(Stream.of(new Object[][]{
                {10, new UnificationEntry(OrePrefix.wireGtQuadruple, PedotTMA)},
                {11, new UnificationEntry(OrePrefix.wireGtQuadruple, Solarium)},
                {12, new UnificationEntry(OrePrefix.wireGtQuadruple, Hypogen)},
                //{13, new UnificationEntry(OrePrefix.wireGtQuadruple, Galaxium)},
                //{14, new UnificationEntry(OrePrefix.wireGtQuadruple, Universium)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        WIRE_OCT.appendIngredients(Stream.of(new Object[][]{
                {10, new UnificationEntry(OrePrefix.wireGtOctal, PedotTMA)},
                {11, new UnificationEntry(OrePrefix.wireGtOctal, Solarium)},
                {12, new UnificationEntry(OrePrefix.wireGtOctal, Hypogen)},
                //{13, new UnificationEntry(OrePrefix.wireGtOctal, Galaxium)},
                //{14, new UnificationEntry(OrePrefix.wireGtOctal, Universium)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        WIRE_HEX.appendIngredients(Stream.of(new Object[][]{
                {10, new UnificationEntry(OrePrefix.wireGtHex, PedotTMA)},
                {11, new UnificationEntry(OrePrefix.wireGtHex, Solarium)},
                {12, new UnificationEntry(OrePrefix.wireGtHex, Hypogen)},
                //{13, new UnificationEntry(OrePrefix.wireGtHex, Galaxium)},
                //{14, new UnificationEntry(OrePrefix.wireGtHex, Universium)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE.appendIngredients(Stream.of(new Object[][]{
                {10, new UnificationEntry(OrePrefix.cableGtSingle, PedotTMA)},
                {11, new UnificationEntry(OrePrefix.cableGtSingle, Solarium)},
                {12, new UnificationEntry(OrePrefix.cableGtSingle, Hypogen)},
                //{13, new UnificationEntry(OrePrefix.cableGtSingle, Galaxium)},
                //{14, new UnificationEntry(OrePrefix.cableGtSingle, Universium)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_QUAD.appendIngredients(Stream.of(new Object[][]{
                {10, new UnificationEntry(OrePrefix.cableGtQuadruple, PedotTMA)},
                {11, new UnificationEntry(OrePrefix.cableGtQuadruple, Solarium)},
                {12, new UnificationEntry(OrePrefix.cableGtQuadruple, Hypogen)},
                //{13, new UnificationEntry(OrePrefix.cableGtQuadruple, Galaxium)},
                //{14, new UnificationEntry(OrePrefix.cableGtQuadruple, Universium)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_OCT.appendIngredients(Stream.of(new Object[][]{
                {10, new UnificationEntry(OrePrefix.cableGtOctal, PedotTMA)},
                {11, new UnificationEntry(OrePrefix.cableGtOctal, Solarium)},
                {12, new UnificationEntry(OrePrefix.cableGtOctal, Hypogen)},
                //{13, new UnificationEntry(OrePrefix.cableGtOctal, Galaxium)},
                //{14, new UnificationEntry(OrePrefix.cableGtOctal, Universium)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_HEX.appendIngredients(Stream.of(new Object[][]{
                {10, new UnificationEntry(OrePrefix.cableGtHex, PedotTMA)},
                {11, new UnificationEntry(OrePrefix.cableGtHex, Solarium)},
                {12, new UnificationEntry(OrePrefix.cableGtHex, Hypogen)},
                //{13, new UnificationEntry(OrePrefix.cableGtHex, Galaxium)},
                //{14, new UnificationEntry(OrePrefix.cableGtHex, Universium)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_TIER_UP.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.cableGtSingle, PedotTMA)},
                {10, new UnificationEntry(OrePrefix.cableGtSingle, Solarium)},
                {11, new UnificationEntry(OrePrefix.cableGtSingle, Hypogen)},
                //{12, new UnificationEntry(OrePrefix.cableGtSingle, Galaxium)},
                //{13, new UnificationEntry(OrePrefix.cableGtSingle, Universium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        PIPE_NORMAL.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.pipeNormalFluid, Duranium)},
                {10, new UnificationEntry(OrePrefix.pipeNormalFluid, Lafium)},
                {11, new UnificationEntry(OrePrefix.pipeNormalFluid, CrystalMatrix)},
                //{12, new UnificationEntry(OrePrefix.pipeNormalFluid, QuantumchromodynamicallyConfinedMatter)},
                //{13, new UnificationEntry(OrePrefix.pipeNormalFluid, Fatalium)},
                //{14, new UnificationEntry(OrePrefix.pipeNormalFluid, Aetherium)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        PIPE_LARGE.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.pipeLargeFluid, Duranium)},
                {10, new UnificationEntry(OrePrefix.pipeLargeFluid, Lafium)},
                {11, new UnificationEntry(OrePrefix.pipeLargeFluid, CrystalMatrix)},
                //{12, new UnificationEntry(OrePrefix.pipeLargeFluid, QuantumchromodynamicallyConfinedMatter)},
                //{13, new UnificationEntry(OrePrefix.pipeLargeFluid, Fatalium)},
                //{14, new UnificationEntry(OrePrefix.pipeLargeFluid, Aetherium)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        GLASS.appendIngredients(Stream.of(new Object[][]{
                {7, GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.BPA_POLYCARBONATE_GLASS)},
                {8, GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.BPA_POLYCARBONATE_GLASS)},
                {9, GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.PMMA_GLASS)},
                {10, GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.PMMA_GLASS)},
                {11, GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.CBDO_POLYCARBONATE_GLASS)},
                {12, GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.CBDO_POLYCARBONATE_GLASS)},
                {13, GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.INFINITY_GLASS)},
                {14, GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.INFINITY_GLASS)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        PLATE.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.plate, Orichalcum)},  //UHV
                {10, new UnificationEntry(OrePrefix.plate, Adamantium)}, //UEV
                {11, new UnificationEntry(OrePrefix.plate, Infinity)},  //UIV
                {12, new UnificationEntry(OrePrefix.plate, Spacetime)}, //UXV
                {13, new UnificationEntry(OrePrefix.plate, Eternity)}, //OpV
                {14, new UnificationEntry(OrePrefix.plate, Magmatter)} //MAX
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        HULL_PLATE.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.plate, Polyetheretherketone)},
                {10, new UnificationEntry(OrePrefix.plate, Polyetheretherketone)},
                {11, new UnificationEntry(OrePrefix.plate, Kevlar)},
                {12, new UnificationEntry(OrePrefix.plate, Kevlar)},
                //{13, new UnificationEntry(OrePrefix.plate, CosmicFabric)},
                //{14, new UnificationEntry(OrePrefix.plate, CosmicFabric)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        DOUBLE_PLATE.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.plateDouble, Orichalcum)},
                {10, new UnificationEntry(OrePrefix.plateDouble, Adamantium)},
                {11, new UnificationEntry(OrePrefix.plateDouble, Infinity)},
                {12, new UnificationEntry(OrePrefix.plateDouble, CosmicNeutronium)},
                //{13, new UnificationEntry(OrePrefix.plateDouble, Spacetime)},
                //{14, new UnificationEntry(OrePrefix.plateDouble, Eternity)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        ROTOR.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.rotor, Orichalcum)},
                {10, new UnificationEntry(OrePrefix.rotor, Adamantium)},
                {11, new UnificationEntry(OrePrefix.rotor, Infinity)},
                {12, new UnificationEntry(OrePrefix.rotor, CosmicNeutronium)},
                //{13, new UnificationEntry(OrePrefix.rotor, Spacetime)},
                //{14, new UnificationEntry(OrePrefix.rotor, Eternity)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        GRINDER.appendIngredients(Stream.of(new Object[][]{
                {6, MetaItems.COMPONENT_GRINDER_TUNGSTEN.getStackForm()},
                {7, MetaItems.COMPONENT_GRINDER_TUNGSTEN.getStackForm()},
                {8, MetaItems.COMPONENT_GRINDER_TUNGSTEN.getStackForm()},
                {9, GTQTMetaItems.COMPONENT_GRINDER_BORON_NITRIDE.getStackForm()},
                {10, GTQTMetaItems.COMPONENT_GRINDER_BORON_NITRIDE.getStackForm()},
                {11, GTQTMetaItems.COMPONENT_GRINDER_BORON_NITRIDE.getStackForm()},
                //{12, GTQTMetaItems.COMPONENT_GRINDER_BLACK_PLUTONIUM.getStackForm()},
                //{13, GTQTMetaItems.COMPONENT_GRINDER_BLACK_PLUTONIUM.getStackForm()},
                //{14, GTQTMetaItems.COMPONENT_GRINDER_BLACK_PLUTONIUM.getStackForm()}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        SAWBLADE.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.toolHeadBuzzSaw, CubicBoronNitride)},
                {10, new UnificationEntry(OrePrefix.toolHeadBuzzSaw, BlackTitanium)},
                {11, new UnificationEntry(OrePrefix.toolHeadBuzzSaw, BlackPlutonium)},
                //{12, new UnificationEntry(OrePrefix.toolHeadBuzzSaw, Octiron)},
                //{13, new UnificationEntry(OrePrefix.toolHeadBuzzSaw, Edenium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        COIL_HEATING.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.wireGtDouble, Trinium)},
                {10, new UnificationEntry(OrePrefix.wireGtDouble, Tritanium)},
                {11, new UnificationEntry(OrePrefix.wireGtDouble, Adamantium)},
                {12, new UnificationEntry(OrePrefix.wireGtDouble, Ichorium)},
                //{13, new UnificationEntry(OrePrefix.wireGtDouble, Astralium)},
                //{14, new UnificationEntry(OrePrefix.wireGtDouble, Hikarium)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        COIL_HEATING_DOUBLE.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.wireGtQuadruple, Trinium)},
                {10, new UnificationEntry(OrePrefix.wireGtQuadruple, Tritanium)},
                {11, new UnificationEntry(OrePrefix.wireGtDouble, Adamantium)},
                {12, new UnificationEntry(OrePrefix.wireGtDouble, Ichorium)},
                //{13, new UnificationEntry(OrePrefix.wireGtDouble, Astralium)},
                //{14, new UnificationEntry(OrePrefix.wireGtDouble, Hikarium)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        COIL_ELECTRIC.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.wireGtOctal, SiliconCarbide)},
                {10, new UnificationEntry(OrePrefix.wireGtOctal, Seaborgium)},
                {11, new UnificationEntry(OrePrefix.wireGtOctal, Abyssalloy)},
                //{12, new UnificationEntry(OrePrefix.wireGtOctal, BlackDwarfMatter)},
                //{13, new UnificationEntry(OrePrefix.wireGtOctal, Shirabon)},
                //{14, new UnificationEntry(OrePrefix.wireGtOctal, Magmatter)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        STICK_MAGNETIC.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.stickLong, ChromiumGermaniumTellurideMagnetic)},
                {10, new UnificationEntry(OrePrefix.stickLong, ChromiumGermaniumTellurideMagnetic)},
                //{11, new UnificationEntry(OrePrefix.stickLong, PhosphorusDopedEuropiumIronArsenideMagnetic)},
                //{12, new UnificationEntry(OrePrefix.stickLong, PhosphorusDopedEuropiumIronArsenideMagnetic)},
                //{13, new UnificationEntry(OrePrefix.stickLong, BismuthLawrenciumStrontiumCuprateMagnetic)},
                //{14, new UnificationEntry(OrePrefix.stickLong, BismuthLawrenciumStrontiumCuprateMagnetic)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        STICK_DISTILLATION.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.spring, Trinium)},
                {10, new UnificationEntry(OrePrefix.spring, Tritanium)},
                {11, new UnificationEntry(OrePrefix.spring, Adamantium)},
                {12, new UnificationEntry(OrePrefix.spring, Ichorium)},
                //{13, new UnificationEntry(OrePrefix.spring, Astralium)},
                //{14, new UnificationEntry(OrePrefix.spring, Hikarium)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        STICK_ELECTROMAGNETIC.appendIngredients(Stream.of(new Object[][]{
                {5, new UnificationEntry(OrePrefix.stick, VanadiumGallium)},
                {6, new UnificationEntry(OrePrefix.stick, VanadiumGallium)},
                {7, new UnificationEntry(OrePrefix.stick, VanadiumGallium)},
                {8, new UnificationEntry(OrePrefix.stickLong, VanadiumGallium)},
                {9, new UnificationEntry(OrePrefix.stickLong, VanadiumGallium)},
                {10, new UnificationEntry(OrePrefix.stick, CarbonNanotube)},
                {11, new UnificationEntry(OrePrefix.stick, CarbonNanotube)},
                {12, new UnificationEntry(OrePrefix.stickLong, CarbonNanotube)},
                //{13, new UnificationEntry(OrePrefix.stickLong, CarbonNanotube)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        STICK_RADIOACTIVE.appendIngredients(Stream.of(new Object[][]{
                {8, new UnificationEntry(OrePrefix.stick, Dubnium)},
                {9, new UnificationEntry(OrePrefix.stick, Livermorium)},
                {10, new UnificationEntry(OrePrefix.stick, MetastableFlerovium)},
                {11, new UnificationEntry(OrePrefix.stick, MetastableHassium)},
                {12, new UnificationEntry(OrePrefix.stick, SuperheavyLAlloy)},
                //{13, new UnificationEntry(OrePrefix.stick, SuperheavyHAlloy)},
                //{14, new UnificationEntry(OrePrefix.stick, Periodicium)},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        PIPE_REACTOR.appendIngredients(Stream.of(new Object[][]{
                {9, new UnificationEntry(OrePrefix.pipeNormalFluid, Polybenzimidazole)},
                {10, new UnificationEntry(OrePrefix.pipeLargeFluid, Polybenzimidazole)},
                {11, new UnificationEntry(OrePrefix.pipeHugeFluid, Polybenzimidazole)},
                {12, new UnificationEntry(OrePrefix.pipeNormalFluid, Polyetheretherketone)},
                //{13, new UnificationEntry(OrePrefix.pipeLargeFluid, Polyetheretherketone)},
                //{14, new UnificationEntry(OrePrefix.pipeHugeFluid, Polyetheretherketone)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        POWER_COMPONENT.appendIngredients(Stream.of(new Object[][]{
                {9, GTQTMetaItems.NANO_POWER_IC},
                {10, GTQTMetaItems.NANO_POWER_IC},
                {11, GTQTMetaItems.PICO_POWER_IC},
                {12, GTQTMetaItems.PICO_POWER_IC},
                //{13, GTQTMetaItems.FEMTO_POWER_IC},
                //{14, GTQTMetaItems.FEMTO_POWER_IC}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        VOLTAGE_COIL.appendIngredients(Stream.of(new Object[][]{
                {9, GTQTMetaItems.VOLTAGE_COIL_UHV},
                {10, GTQTMetaItems.VOLTAGE_COIL_UEV},
                {11, GTQTMetaItems.VOLTAGE_COIL_UIV},
                {12, GTQTMetaItems.VOLTAGE_COIL_UXV},
                //{13, GTQTMetaItems.VOLTAGE_COIL_OpV},
                //{14, GTQTMetaItems.VOLTAGE_COIL_MAX}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        SPRING.appendIngredients(Stream.of(new Object[][]{
                {10, new UnificationEntry(OrePrefix.spring, CarbonNanotube)},
                {11, new UnificationEntry(OrePrefix.spring, RutheniumTriniumAmericiumNeutronate)},
                //{12, new UnificationEntry(OrePrefix.spring, WhiteDwarfMatter)},
                //{13, new UnificationEntry(OrePrefix.spring, Edenium)},
                //{14, new UnificationEntry(OrePrefix.spring, Aetherium)}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));
    }
}

