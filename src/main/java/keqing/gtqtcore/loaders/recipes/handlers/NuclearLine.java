package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.unification.material.Material;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTParticleAccelerator;

import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.W;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.RTG_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.Carbon16;
import static keqing.gtqtcore.api.unification.GCYSMaterials.Radium226;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.material.info.EPMaterialFlags.GENERATE_PELLETS;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.pellets;
import static keqing.gtqtcore.common.block.blocks.GTQTPowerSupply.SupplyType.POWER_SUPPLY_BASIC;

public class NuclearLine {
    public static void init() {
        GRANULAR_SOURCE();
        RTG(Uranium235,1);
        RTG(Uranium238,2);
        RTG(Plutonium239,3);
        RTG(Plutonium241,4);
        RTG(Plutonium244,5);
        RTG(Thorium,3);
    }

    private static void RTG(Material material,int fuel) {
        RTG_RECIPES.recipeBuilder()
                .input(pellets,material)
                .fluidInputs(Oxygen.getFluid(1000))
                .duration(1200+fuel*120)
                .EUt(2048)
                .buildAndRegister();

        RTG_RECIPES.recipeBuilder()
                .input(pellets,material)
                .fluidInputs(Nitrogen.getFluid(1000))
                .duration(1000+fuel*80)
                .EUt(2048)
                .buildAndRegister();
    }

    private static void GRANULAR_SOURCE() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(dust, Radium226, 16)
                .input(dust, AuPdCCatalyst, 4)
                .input(HULL[4])
                .input(foil, Graphene, 64)
                .input(plateDense, Lead, 64)
                .input(plateDense, Lead, 64)
                .fluidInputs(Argon.getFluid(16000))
                .outputs(GTQTMetaBlocks.PARTICLE_ACCELERATOR.getItemVariant(GTQTParticleAccelerator.MachineType.GRANULAR_SOURCE_A))
                .circuitMeta(1)
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(400).EUt(1920).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(dust, Carbon16, 16)
                .input(dust, AuPdCCatalyst, 4)
                .input(HULL[4])
                .input(foil, Graphene, 64)
                .input(plateDense, Lead, 64)
                .input(plateDense, Lead, 64)
                .fluidInputs(Argon.getFluid(16000))
                .outputs(GTQTMetaBlocks.PARTICLE_ACCELERATOR.getItemVariant(GTQTParticleAccelerator.MachineType.GRANULAR_SOURCE_B))
                .circuitMeta(1)
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(400).EUt(1920).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(dust, Uranium238, 16)
                .input(dust, AuPdCCatalyst, 4)
                .input(HULL[4])
                .input(foil, Graphene, 64)
                .input(plateDense, Lead, 64)
                .input(plateDense, Lead, 64)
                .fluidInputs(Argon.getFluid(16000))
                .outputs(GTQTMetaBlocks.PARTICLE_ACCELERATOR.getItemVariant(GTQTParticleAccelerator.MachineType.GRANULAR_SOURCE_C))
                .circuitMeta(1)
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(400).EUt(1920).buildAndRegister();
    }
}
