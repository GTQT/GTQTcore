package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.unification.material.Material;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTParticleAccelerator;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.DECAY_CHAMBER_RECIPES;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.RTG_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.Cobalt60;
import static keqing.gtqtcore.api.unification.material.info.EPMaterialFlags.GENERATE_PELLETS;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.pellets;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.upellets;
import static keqing.gtqtcore.common.block.blocks.GTQTPowerSupply.SupplyType.POWER_SUPPLY_BASIC;

public class NuclearLine {
    public static void init() {
        GRANULAR_SOURCE();
        DECAY_CHAMBER_RECIPES(Thorium,Lead,3);
        DECAY_CHAMBER_RECIPES(Radium,Lead,3);
        DECAY_CHAMBER_RECIPES(Polonium,Lead,4);
        DECAY_CHAMBER_RECIPES(Strontium,Zirconium,4);
        DECAY_CHAMBER_RECIPES(Promethium,Neodymium,4);
        DECAY_CHAMBER_RECIPES(Uranium233,Bismuth,4);
        DECAY_CHAMBER_RECIPES(Uranium234,Radium,4);
        DECAY_CHAMBER_RECIPES(Uranium235,Lead,4);
        DECAY_CHAMBER_RECIPES(Uranium238,Radium,4);
        DECAY_CHAMBER_RECIPES(Plutonium238,Uranium234,4);
        DECAY_CHAMBER_RECIPES(Plutonium239,Uranium235,4);
        DECAY_CHAMBER_RECIPES(Plutonium242,Uranium238,4);
        DECAY_CHAMBER_RECIPES(Cobalt60,Nickel,4);
        DECAY_CHAMBER_RECIPES(Iridium,Platinum,4);
        DECAY_CHAMBER_RECIPES(Neptunium236,Thorium,4);
        DECAY_CHAMBER_RECIPES(Neptunium,Uranium233,4);
        DECAY_CHAMBER_RECIPES(Americium241,Neptunium,4);
        DECAY_CHAMBER_RECIPES(Americium243,Plutonium239,4);
        DECAY_CHAMBER_RECIPES(Curium243,Plutonium239,4);
        DECAY_CHAMBER_RECIPES(Curium245,Plutonium241,4);
        DECAY_CHAMBER_RECIPES(Curium246,Plutonium242,4);
        DECAY_CHAMBER_RECIPES(Curium247,Americium243,4);

        RTG(Uranium233,1);
        RTG(Uranium234,1);
        RTG(Uranium235,1);
        RTG(Uranium236,2);
        RTG(Uranium238,2);
        RTG(Plutonium238,3);
        RTG(Plutonium239,3);
        RTG(Plutonium241,4);
        RTG(Plutonium242,4);
        RTG(Plutonium244,4);
        RTG(Neptunium236,4);
        RTG(Neptunium,4);
        RTG(Americium,5);
        RTG(Americium241,5);
        RTG(Americium242,5);
        RTG(Americium243,5);
        RTG(Curium243,5);
        RTG(Curium245,5);
        RTG(Curium246,5);
        RTG(Curium247,5);
        RTG(Curium,5);
        RTG(Thorium,3);
    }

    private static void DECAY_CHAMBER_RECIPES(Material material1,Material material2,int tier) {
        DECAY_CHAMBER_RECIPES.recipeBuilder()
                .input(dust,material1)
                .output(dust,material2)
                .duration(400+tier*200)
                .EUt(VA[tier])
                .buildAndRegister();
    }

    private static void RTG(Material material,int fuel) {
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .output(pellets,material)
                .input(dustSmall,material,1)
                .input(dustSmall,material,1)
                .input(dustSmall,material,1)
                .input(dustSmall,material,1)
                .input(dustSmall,material,1)
                .input(dust,material,1)
                .input(dust,material,1)
                .input(dustSmall,material,1)
                .input(dustSmall,material,1)
                .input(dust,material,1)
                .input(dust,material,1)
                .input(dustSmall,material,1)
                .input(dustSmall,material,1)
                .input(dustSmall,material,1)
                .input(dustSmall,material,1)
                .input(dustSmall,material,1)
                .fluidInputs(Nitrogen.getFluid(16000))
                .fluidInputs(Argon.getFluid(16000))
                .fluidInputs(Helium.getFluid(16000))
                .fluidInputs(Oxygen.getFluid(16000))
                .duration(400+fuel*20)
                .EUt(VA[fuel])
                .buildAndRegister();

        RTG_RECIPES.recipeBuilder()
                .input(pellets,material)
                .output(upellets,material)
                .fluidInputs(Argon.getFluid(1000))
                .duration(1200+fuel*160)
                .EUt(2048)
                .buildAndRegister();

        RTG_RECIPES.recipeBuilder()
                .input(pellets,material)
                .output(upellets,material)
                .fluidInputs(Helium.getFluid(1000))
                .duration(800+fuel*120)
                .EUt(2048)
                .buildAndRegister();

        RTG_RECIPES.recipeBuilder()
                .input(pellets,material)
                .output(upellets,material)
                .fluidInputs(Nitrogen.getFluid(1000))
                .duration(600+fuel*80)
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
