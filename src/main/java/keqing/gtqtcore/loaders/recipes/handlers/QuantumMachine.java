package keqing.gtqtcore.loaders.recipes.handlers;

import gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;

import static gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities.MEGA_BLAST_FURNACE;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.swarm;
import static keqing.gtqtcore.common.block.blocks.BlockQuantumCasing.CasingType.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.DIMENSIONALLY_TRANSCENDENT_PLASMA_FORGE;

public class QuantumMachine {
    public static void init() {
        DTPFRecipes();
        QFTRecipes();
        CasingRecipes();
    }

    public static void DTPFRecipes() {
        //控制器
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTMetaBlocks.blockQuantumCasing.getItemVariant(DIMENSIONAL_BRIDGE_CASING, 4))
                .input(MEGA_BLAST_FURNACE, 64)
                .input(GCYMMetaTileEntities.MEGA_ALLOY_BLAST_SMELTER, 64)
                .input(MetaTileEntities.ENERGY_INPUT_HATCH[UEV], 32)
                .input(FIELD_GENERATOR_UEV, 16)
                .input(ELECTRIC_PUMP_UEV, 16)
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
                        .researchStack(MEGA_BLAST_FURNACE.getStackForm())
                        .EUt(VA[UEV])
                        .CWUt(CWT[UHV]))
                .EUt(VA[UEV])
                .duration(1200)
                .buildAndRegister();
    }

    public static void QFTRecipes() {

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
