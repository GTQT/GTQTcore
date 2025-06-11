package keqing.gtqtcore.loaders.recipes.handlers;

import gregicality.multiblocks.api.unification.GCYMMaterials;
import gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.common.blocks.BlockFusionCasing;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing7;
import keqing.gtqtcore.common.block.blocks.BlockTransparentCasing;

import static gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities.MEGA_BLAST_FURNACE;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.EXTRADIMENSIONAL_MIXING_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.swarm;
import static keqing.gtqtcore.common.block.GTQTMetaBlocks.blockWireCoil;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockGlass.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.BlockQuantumCasing.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.BlockQuantumForceTransformerCasing.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.BlockWireCoil.CoilType.NEUTRONIUM;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.*;

public class QuantumMachine {
    public static void init() {
        DTPFRecipes();
        QFTRecipes();
        CasingRecipes();
        //能量注入器
        EnergyInfuser();
    }

    private static void EnergyInfuser() {
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(MetaTileEntities.ACTIVE_TRANSFORMER)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 16)
                .input(VOLTAGE_COIL_UV, 4)
                .input(CIRCUIT_GOOD_II, 4)
                .input(circuit, MarkerMaterials.Tier.UV, 8)
                .input(screw,Neutronium,8)
                .fluidInputs(VanadiumGallium.getFluid(L * 16))
                .fluidInputs(Kevlar.getFluid(L * 16))
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Polyetheretherketone.getFluid(L * 16))
                .output(ENERGY_INFUSER)
                .stationResearch(b -> b
                        .researchStack(MetaTileEntities.ACTIVE_TRANSFORMER.getStackForm())
                        .EUt(VA[UV])
                        .CWUt(CWT[ZPM]))
                .EUt(VA[UV])
                .duration(1200)
                .buildAndRegister();
    }

    public static void DTPFRecipes() {
        //DTPF控制器
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockQuantumCasing.getItemVariant(DIMENSIONAL_BRIDGE_CASING, 4))
                .input(MEGA_BLAST_FURNACE, 64)
                .input(GCYMMetaTileEntities.MEGA_ALLOY_BLAST_SMELTER, 64)
                .input(MetaTileEntities.ENERGY_INPUT_HATCH[UEV], 32)
                .input(FIELD_GENERATOR_UEV, 16)
                .input(VOLTAGE_COIL_UEV, 16)
                .input(QUANTUM_ANOMALY)
                .input(ULTIMATE_BATTERY)
                .input(CIRCUIT_GOOD_IV, 16)
                .input(circuit, MarkerMaterials.Tier.UIV, 16)
                .input(circuit, MarkerMaterials.Tier.UEV, 32)
                .input(circuit, MarkerMaterials.Tier.UHV, 64)
                .input(wireGtSingle, UEVSuperconductor, 64)
                .input(wireGtSingle, UEVSuperconductor, 64)
                .fluidInputs(MetastableOganesson.getFluid(L * 256))
                .fluidInputs(MutantActiveSolder.getFluid(L * 128))
                .fluidInputs(Octahedrite.getFluid(L * 64))
                .fluidInputs(Naquadria.getFluid(L * 64))
                .output(DIMENSIONALLY_TRANSCENDENT_PLASMA_FORGE)
                .stationResearch(b -> b
                        .researchStack(DISK_35.getStackForm())
                        .EUt(VA[UEV])
                        .CWUt(CWT[UHV]))
                .EUt(VA[UEV])
                .duration(1200)
                .buildAndRegister();

        //超维度搅拌机
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockQuantumCasing.getItemVariant(DIMENSIONAL_BRIDGE_CASING, 4))
                .input(ELECTRIC_PUMP_UEV, 16)
                .input(CONVEYOR_MODULE_UEV, 16)
                .input(QUANTUM_ANOMALY)
                .input(plate, Adamantium, 64)
                .input(gearSmall, Adamantium, 64)
                .input(stick, Adamantium, 64)
                .input(screw, Adamantium, 64)
                .input(circuit, MarkerMaterials.Tier.UEV, 32)
                .input(circuit, MarkerMaterials.Tier.UHV, 64)
                .fluidInputs(MetastableOganesson.getFluid(L * 32))
                .fluidInputs(MutantActiveSolder.getFluid(L * 32))
                .fluidInputs(Octahedrite.getFluid(L * 16))
                .fluidInputs(Naquadria.getFluid(L * 16))
                .output(DIMENSIONAL_MIXER)
                .stationResearch(b -> b
                        .researchStack(DISK_35.getStackForm())
                        .EUt(VA[UEV])
                        .CWUt(CWT[UHV]))
                .EUt(VA[UEV])
                .duration(1200)
                .buildAndRegister();

        //搅拌超维度催化剂
        EXTRADIMENSIONAL_MIXING_RECIPES.recipeBuilder()
                .fluidInputs(Helium.getPlasma(1000))
                .fluidInputs(Iron.getPlasma(1000))
                .fluidInputs(Calcium.getPlasma(1000))
                .fluidInputs(Niobium.getPlasma(1000))
                .circuitMeta(1)
                .fluidOutputs(SuperDimensionalCatalystMKI.getPlasma(1000))
                .EUt(VA[UIV])
                .duration(1200)
                .buildAndRegister();

        EXTRADIMENSIONAL_MIXING_RECIPES.recipeBuilder()
                .fluidInputs(Helium.getPlasma(1000))
                .fluidInputs(Iron.getPlasma(1000))
                .fluidInputs(Calcium.getPlasma(1000))
                .fluidInputs(Niobium.getPlasma(1000))
                .fluidInputs(Radon.getPlasma(1000))
                .fluidInputs(Nickel.getPlasma(1000))
                .fluidInputs(Boron.getPlasma(1000))
                .fluidInputs(Sulfur.getPlasma(1000))
                .circuitMeta(2)
                .fluidOutputs(SuperDimensionalCatalystMKII.getPlasma(1000))
                .EUt(VA[UXV])
                .duration(1200)
                .buildAndRegister();

        EXTRADIMENSIONAL_MIXING_RECIPES.recipeBuilder()
                .fluidInputs(Helium.getPlasma(1000))
                .fluidInputs(Iron.getPlasma(1000))
                .fluidInputs(Calcium.getPlasma(1000))
                .fluidInputs(Niobium.getPlasma(1000))
                .fluidInputs(Radon.getPlasma(1000))
                .fluidInputs(Nickel.getPlasma(1000))
                .fluidInputs(Boron.getPlasma(1000))
                .fluidInputs(Sulfur.getPlasma(1000))
                .fluidInputs(Nitrogen.getPlasma(1000))
                .fluidInputs(Zinc.getPlasma(1000))
                .fluidInputs(Silver.getPlasma(1000))
                .fluidInputs(Titanium.getPlasma(1000))
                .circuitMeta(3)
                .fluidOutputs(SuperDimensionalCatalystMKII.getPlasma(1000))
                .EUt(VA[OpV])
                .duration(1200)
                .buildAndRegister();
    }

    public static void QFTRecipes() {
        //控制器
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getItemVariant(QUANTUM_CONSTRAINT_CASING, 4))
                .input(CHEMICAL_PLANT, 64)
                .input(GCYMMetaTileEntities.MEGA_CHEMICAL_REACTOR, 64)
                .input(MetaTileEntities.ENERGY_INPUT_HATCH[UEV], 32)
                .input(ELECTRIC_PUMP_UEV, 16)
                .input(VOLTAGE_COIL_UEV, 16)
                .input(QUANTUM_ANOMALY)
                .input(ULTIMATE_BATTERY)
                .input(CIRCUIT_GOOD_IV, 16)
                .input(circuit, MarkerMaterials.Tier.UIV, 16)
                .input(circuit, MarkerMaterials.Tier.UEV, 32)
                .input(circuit, MarkerMaterials.Tier.UHV, 64)
                .input(wireGtSingle, UEVSuperconductor, 64)
                .input(wireGtSingle, UEVSuperconductor, 64)
                .fluidInputs(MetastableOganesson.getFluid(L * 256))
                .fluidInputs(MutantActiveSolder.getFluid(L * 128))
                .fluidInputs(Octahedrite.getFluid(L * 64))
                .fluidInputs(Naquadria.getFluid(L * 64))
                .output(QUANTUM_FORCE_TRANSFORMER)
                .stationResearch(b -> b
                        .researchStack(DISK_35.getStackForm())
                        .EUt(VA[UEV])
                        .CWUt(CWT[UHV]))
                .EUt(VA[UEV])
                .duration(1200)
                .buildAndRegister();
        //外壳
        //量子约束外壳
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockMultiblockCasing7.getItemVariant(BlockMultiblockCasing7.CasingType.MAGNETIC_FIELD_CASING))
                .input(frameGt, Neutronium)
                .input(circuit, MarkerMaterials.Tier.UHV)
                .input(screw, Inconel625, 32)
                .input(bolt, GCYMMaterials.Zeron100, 12)
                .input(plate, Osmiridium, 8)
                .fluidInputs(PbB.getFluid(L * 4))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getItemVariant(QUANTUM_CONSTRAINT_CASING))
                .EUt(VA[UEV])
                .duration(400)
                .buildAndRegister();

        //中空管道
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockQuantumCasing.getItemVariant(HIGH_ENERGY_CASING))
                .input(plate, Europium, 64)
                .input(screw, Plutonium241, 16)
                .input(pipeHugeFluid, Neutronium, 64)
                .input(rotor, Adamantium, 4)
                .fluidInputs(Argon.getFluid(1000))
                .fluidInputs(Osmiridium.getFluid(1296))
                .fluidInputs(PCBCoolant.getFluid(2000))
                .fluidInputs(Trinium.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockQuantumCasing.getItemVariant(HOLLOW_CASING, 2))
                .EUt(VA[UV])
                .duration(800)
                .buildAndRegister();

        //分子线圈
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockQuantumCasing.getItemVariant(HOLLOW_CASING))
                .inputs(MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.FUSION_COIL, 2))
                .inputs(MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.NAQUADAH, 2))
                .input(wireFine, Europium, 64)
                .input(foil, Europium, 64)
                .fluidInputs(PbB.getFluid(L * 4))
                .fluidInputs(SiliconeRubber.getFluid(1296))
                .fluidInputs(PCBCoolant.getFluid(2000))
                .fluidInputs(NaquadahAlloy.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockQuantumCasing.getItemVariant(MOLECULAR_COIL, 4))
                .EUt(VA[UV])
                .duration(800)
                .buildAndRegister();

        //维度转换线圈
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockQuantumCasing.getItemVariant(MOLECULAR_COIL))
                .inputs(GTQTMetaBlocks.blockWireCoil.getItemVariant(NEUTRONIUM))
                .input(plate, Adamantium, 16)
                .input(foil, Europium, 16)
                .input(wireGtSingle, UHVSuperconductor, 8)
                .fluidInputs(PCBCoolant.getFluid(2000))
                .fluidInputs(QuantumAlloy.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockQuantumCasing.getItemVariant(MOLECULAR_COIL))
                .EUt(VA[UV])
                .duration(800)
                .buildAndRegister();

        //量操线圈
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(blockWireCoil.getItemVariant(NEUTRONIUM))
                .inputs(GTQTMetaBlocks.blockQuantumCasing.getItemVariant(MOLECULAR_COIL))
                .input(screw, Neutronium,4)
                .input(plate, Osmiridium, 8)
                .fluidInputs(MutantActiveSolder.getFluid(L * 4))
                .fluidInputs(QuantumAlloy.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getItemVariant(QUANTUM_FORCE_TRANSFORMER_COIL))
                .EUt(VA[UEV])
                .duration(400)
                .buildAndRegister();

        //量子玻璃
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockQuantumCasing.getItemVariant(HIGH_ENERGY_CASING))
                .inputs(MetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockGlassCasing.CasingType.FUSION_GLASS))
                .input(screw, Neutronium, 4)
                .input(stick, NaquadahAlloy, 8)
                .input(bolt, Adamantium, 8)
                .fluidInputs(Argon.getFluid(1000))
                .fluidInputs(Osmiridium.getFluid(1296))
                .fluidInputs(PCBCoolant.getFluid(2000))
                .fluidInputs(Trinium.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.QUANTUM_GLASS))
                .EUt(VA[UV])
                .duration(800)
                .buildAndRegister();

        //智能玻璃
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.QUANTUM_GLASS))
                .input(FIELD_GENERATOR_ZPM)
                .input(stickLong, CelestialTungsten, 6)
                .input(screw, Neutronium, 8)
                .input(plate, Adamantium, 8)
                .fluidInputs(MutantActiveSolder.getFluid(2000))
                .fluidInputs(QuantumAlloy.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockMultiblockGlass.getItemVariant(ADV_MACHINE_GLASS))
                .EUt(VA[UEV])
                .duration(800)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.QUANTUM_GLASS))
                .input(FIELD_GENERATOR_UV)
                .input(stickLong, CelestialTungsten, 6)
                .input(screw, Neutronium, 8)
                .input(plate, Adamantium, 8)
                .fluidInputs(MutantActiveSolder.getFluid(2000))
                .fluidInputs(QuantumAlloy.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockMultiblockGlass.getItemVariant(ADV_MACHINE_GLASS_B))
                .EUt(VA[UEV])
                .duration(800)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.QUANTUM_GLASS))
                .input(FIELD_GENERATOR_UHV)
                .input(stickLong, CelestialTungsten, 6)
                .input(screw, Neutronium, 8)
                .input(plate, Adamantium, 8)
                .fluidInputs(MutantActiveSolder.getFluid(2000))
                .fluidInputs(QuantumAlloy.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockMultiblockGlass.getItemVariant(ADV_MACHINE_GLASS_G))
                .EUt(VA[UEV])
                .duration(800)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.QUANTUM_GLASS))
                .input(FIELD_GENERATOR_UEV)
                .input(stickLong, CelestialTungsten, 6)
                .input(screw, Neutronium, 8)
                .input(plate, Adamantium, 8)
                .fluidInputs(MutantActiveSolder.getFluid(2000))
                .fluidInputs(QuantumAlloy.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockMultiblockGlass.getItemVariant(ADV_MACHINE_GLASS_O))
                .EUt(VA[UEV])
                .duration(800)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.QUANTUM_GLASS))
                .input(FIELD_GENERATOR_UIV)
                .input(stickLong, CelestialTungsten, 6)
                .input(screw, Neutronium, 8)
                .input(plate, Adamantium, 8)
                .fluidInputs(MutantActiveSolder.getFluid(2000))
                .fluidInputs(QuantumAlloy.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockMultiblockGlass.getItemVariant(ADV_MACHINE_GLASS_P))
                .EUt(VA[UEV])
                .duration(800)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.QUANTUM_GLASS))
                .input(FIELD_GENERATOR_UXV)
                .input(stickLong, CelestialTungsten, 6)
                .input(screw, Neutronium, 8)
                .input(plate, Adamantium, 8)
                .fluidInputs(MutantActiveSolder.getFluid(2000))
                .fluidInputs(QuantumAlloy.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockMultiblockGlass.getItemVariant(ADV_MACHINE_GLASS_R))
                .EUt(VA[UEV])
                .duration(800)
                .buildAndRegister();

        //屏蔽核心
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.QUANTUM_GLASS))
                .input(swarm, Darmstadtium, 4)
                .input(EMITTER_UV, 4)
                .input(HIGH_POWER_INTEGRATED_CIRCUIT, 16)
                .input(wireGtHex, UHVSuperconductor, 8)
                .input(QUANTUM_ANOMALY)
                .fluidInputs(MutantActiveSolder.getFluid(2000))
                .fluidInputs(QuantumAlloy.getFluid(L * 4))
                .fluidInputs(Neptunium.getPlasma(500))
                .fluidInputs(Fermium.getPlasma(500))
                .outputs(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getItemVariant(NEUTRON_PULSE_MANIPULATOR_CASING))
                .EUt(VA[UEV])
                .duration(800)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.QUANTUM_GLASS))
                .input(swarm, Neutronium, 4)
                .input(EMITTER_UHV, 4)
                .input(NANO_POWER_IC, 16)
                .input(wireGtHex, UEVSuperconductor, 8)
                .input(QUANTUM_ANOMALY)
                .fluidInputs(MutantActiveSolder.getFluid(2000))
                .fluidInputs(QuantumAlloy.getFluid(L * 4))
                .fluidInputs(Neptunium.getPlasma(1000))
                .fluidInputs(Fermium.getPlasma(1000))
                .outputs(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getItemVariant(COSMIC_FABRIC_MANIPULATOR_CASING))
                .EUt(VA[UIV])
                .duration(800)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.QUANTUM_GLASS))
                .input(swarm, Adamantium, 4)
                .input(EMITTER_UEV, 4)
                .input(PICO_POWER_IC, 16)
                .input(wireGtHex, UIVSuperconductor, 8)
                .input(QUANTUM_ANOMALY)
                .fluidInputs(MutantActiveSolder.getFluid(2000))
                .fluidInputs(QuantumAlloy.getFluid(L * 4))
                .fluidInputs(Neptunium.getPlasma(2000))
                .fluidInputs(Fermium.getPlasma(2000))
                .outputs(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getItemVariant(INFINITY_INFUSED_MANIPULATOR_CASING))
                .EUt(VA[UXV])
                .duration(800)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.QUANTUM_GLASS))
                .input(swarm, Infinity, 4)
                .input(EMITTER_UIV, 4)
                .input(FEMTO_POWER_IC, 16)
                .input(wireGtHex, UXVSuperconductor, 8)
                .input(QUANTUM_ANOMALY)
                .fluidInputs(MutantActiveSolder.getFluid(2000))
                .fluidInputs(QuantumAlloy.getFluid(L * 4))
                .fluidInputs(Neptunium.getPlasma(4000))
                .fluidInputs(Fermium.getPlasma(4000))
                .outputs(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getItemVariant(SUPRACAUSAL_CONTINUUM_MANIPULATOR_CASING))
                .EUt(VA[UXV])
                .duration(800)
                .buildAndRegister();

        //脉冲方块
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getItemVariant(QUANTUM_CONSTRAINT_CASING))
                .input(plateDense, Darmstadtium, 4)
                .input(FIELD_GENERATOR_UV, 4)
                .input(frameGt, YttriumBariumCuprate, 8)
                .input(QUANTUM_ANOMALY)
                .fluidInputs(MutantActiveSolder.getFluid(2000))
                .fluidInputs(QuantumAlloy.getFluid(L * 4))
                .fluidInputs(Neptunium.getPlasma(500))
                .fluidInputs(Fermium.getPlasma(500))
                .outputs(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getItemVariant(NEUTRON_SHIELDING_CORE_CASING))
                .EUt(VA[UEV])
                .duration(800)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getItemVariant(QUANTUM_CONSTRAINT_CASING))
                .input(plateDense, Neutronium, 4)
                .input(FIELD_GENERATOR_UHV, 4)
                .input(frameGt, Europium, 8)
                .input(QUANTUM_ANOMALY)
                .fluidInputs(MutantActiveSolder.getFluid(2000))
                .fluidInputs(QuantumAlloy.getFluid(L * 4))
                .fluidInputs(Neptunium.getPlasma(1000))
                .fluidInputs(Fermium.getPlasma(1000))
                .outputs(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getItemVariant(COSMIC_FABRIC_SHIELDING_CORE_CASING))
                .EUt(VA[UIV])
                .duration(800)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getItemVariant(QUANTUM_CONSTRAINT_CASING))
                .input(plateDense, Adamantium, 4)
                .input(FIELD_GENERATOR_UEV, 4)
                .input(frameGt, PedotTMA, 8)
                .input(QUANTUM_ANOMALY)
                .fluidInputs(MutantActiveSolder.getFluid(2000))
                .fluidInputs(QuantumAlloy.getFluid(L * 4))
                .fluidInputs(Neptunium.getPlasma(2000))
                .fluidInputs(Fermium.getPlasma(2000))
                .outputs(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getItemVariant(INFINITY_INFUSED_SHIELDING_CORE_CASING))
                .EUt(VA[UXV])
                .duration(800)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getItemVariant(QUANTUM_CONSTRAINT_CASING))
                .input(plateDense, Infinity, 4)
                .input(FIELD_GENERATOR_UIV, 4)
                .input(frameGt, Solarium, 8)
                .input(QUANTUM_ANOMALY)
                .fluidInputs(MutantActiveSolder.getFluid(2000))
                .fluidInputs(QuantumAlloy.getFluid(L * 4))
                .fluidInputs(Neptunium.getPlasma(4000))
                .fluidInputs(Fermium.getPlasma(4000))
                .outputs(GTQTMetaBlocks.blockQuantumForceTransformerCasing.getItemVariant(SUPRACAUSAL_CONTINUUM_SHIELDING_CORE_CASING))
                .EUt(VA[UXV])
                .duration(800)
                .buildAndRegister();
    }

    public static void CasingRecipes() {
        //超维度机械方块
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(frameGt, Neutronium)
                .input(plate, Osmiridium, 4)
                .input(screw, Laurenium, 12)
                .input(wireGtSingle, UHVSuperconductor, 1)
                .fluidInputs(MetastableOganesson.getFluid(L * 4))
                .fluidInputs(MutantActiveSolder.getFluid(L * 2))
                .fluidInputs(Naquadria.getFluid(L))
                .outputs(GTQTMetaBlocks.blockQuantumCasing.getItemVariant(HIGH_ENERGY_CASING))
                .EUt(VA[UEV])
                .duration(400)
                .buildAndRegister();

        //维度注入机械方块
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockQuantumCasing.getItemVariant(HIGH_ENERGY_CASING))
                .input(circuit, MarkerMaterials.Tier.UHV)
                .input(MetaTileEntities.QUANTUM_CHEST[5], 1)
                .input(MetaTileEntities.QUANTUM_TANK[5], 1)
                .input(NANO_POWER_IC, 1)
                .fluidInputs(MetastableOganesson.getFluid(L * 4))
                .fluidInputs(MutantActiveSolder.getFluid(L * 2))
                .fluidInputs(Naquadria.getFluid(L))
                .outputs(GTQTMetaBlocks.blockQuantumCasing.getItemVariant(DIMENSIONAL_INJECTION_CASING))
                .EUt(VA[UEV])
                .duration(800)
                .buildAndRegister();

        //维度桥接机械方块
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockQuantumCasing.getItemVariant(HIGH_ENERGY_CASING))
                .input(circuit, MarkerMaterials.Tier.UHV, 6)
                .input(FIELD_GENERATOR_UHV, 4)
                .input(EMITTER_UHV, 4)
                .input(swarm, Iron)
                .input(NANO_POWER_IC, 2)
                .fluidInputs(MetastableOganesson.getFluid(L * 16))
                .fluidInputs(MutantActiveSolder.getFluid(L * 8))
                .fluidInputs(Naquadria.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.blockQuantumCasing.getItemVariant(DIMENSIONAL_BRIDGE_CASING))
                .EUt(VA[UEV])
                .duration(1200)
                .buildAndRegister();
    }
}
