package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.unification.material.Material;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTParticleAccelerator;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.metatileentities.MetaTileEntities.CENTRIFUGE;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.Cobalt60;
import static keqing.gtqtcore.api.unification.material.info.EPMaterialFlags.GENERATE_PELLETS;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.*;
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

        //回收配方 钍-》铀233 238 镎236 237 锶 铯
        //回收配方 铀233-》铀238 钚241 242 镅243 锶 铯
        //回收配方 铀235-》铀238 钚239 242 镅243 钼 铯
        //回收配方 镎236-》铀238 镎237 钚241 242 钼 铯
        //回收配方 钚239-》钚242 镅242 243 锔246 锶 钷
        //回收配方 钚241-》镅241 242 243 锔246 锶 钷
        //回收配方 镅242-》镅243 锔245 246 248 锶 钷
        RTG(Thorium,1,Uranium233,Uranium238,Plutonium238,Neptunium,Strontium,Caesium);
        RTG(Uranium233,2,Uranium234,Plutonium238,Plutonium239,Americium241,Strontium,Caesium);
        RTG(Uranium234,2,Uranium235,Plutonium239,Plutonium241,Americium241,Strontium,Caesium);
        RTG(Uranium235,2,Uranium236,Plutonium241,Plutonium242,Americium241,Strontium,Caesium);
        RTG(Uranium236,3,Uranium238,Plutonium242,Plutonium244,Americium242,Strontium,Caesium);
        RTG(Uranium238,3,Plutonium238,Plutonium244,Neptunium236,Americium242,Strontium,Caesium);
        RTG(Plutonium238,3,Plutonium239,Neptunium236,Neptunium,Americium242,Molybdenum,Caesium);
        RTG(Plutonium239,3,Plutonium241,Neptunium,Americium,Americium243,Molybdenum,Caesium);
        RTG(Plutonium241,4,Plutonium242,Americium,Americium241,Americium243,Molybdenum,Caesium);
        RTG(Plutonium242,4,Plutonium244,Americium241,Americium242,Curium243,Molybdenum,Caesium);
        RTG(Plutonium244,4,Neptunium236,Americium242,Americium243,Curium243,Molybdenum,Caesium);
        RTG(Neptunium236,5,Neptunium,Americium243,Curium243,Curium245,Molybdenum,Caesium);
        RTG(Neptunium,5,Americium,Curium243,Curium245,Curium245,Strontium,Promethium);
        RTG(Americium,5,Americium241,Curium245,Curium246,Curium246,Strontium,Promethium);
        RTG(Americium241,5,Americium242,Curium246,Curium247,Curium246,Strontium,Promethium);
        RTG(Americium242,5,Americium243,Curium247,Curium,Curium247,Strontium,Promethium);
        RTG(Americium243,6,Curium243,Curium,Neptunium236,Curium247,Strontium,Promethium);
        RTG(Curium243,6);
        RTG(Curium245,6);
        RTG(Curium246,7);
        RTG(Curium247,7);
        RTG(Curium,7);

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
    private static void RTG(Material material,int fuel,Material material1,Material material2,Material material3,Material material4,Material material5,Material material6) {
        RTG(material,fuel);

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(upellets,material,9)
                .output(dust,material1)
                .output(dust,material2,5)
                .output(dust,material3)
                .output(dust,material4)
                .chancedOutput(dust,material5,1,2500,0)
                .chancedOutput(dust,material6,1,2500,0)
                .duration(400+fuel*20)
                .EUt(19)
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(kujieranliaowan,material,9)
                .output(dust,material1,4)
                .output(dust,material2,20)
                .output(dust,material3,4)
                .output(dust,material4,4)
                .chancedOutput(dust,material5,4,5000,0)
                .chancedOutput(dust,material6,4,5000,0)
                .fluidOutputs(Graphite.getFluid(1296*4*9))
                .fluidOutputs(Lead.getFluid(1296*2*9))
                .duration(800+fuel*40)
                .EUt(1920)
                .buildAndRegister();

        NUCLEAR_RECIPES.recipeBuilder()
                .input(ranliaowan,material)
                .output(kujieranliaowan,material)
                .duration(600+fuel*80)
                .EUt(2048)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .output(ranliaowan,material)
                .input(block,Graphite,1)
                .input(block,Graphite,1)
                .input(block,Graphite,1)
                .input(block,Graphite,1)
                .input(plateDense,Lead,1)
                .input(upellets,material,1)
                .input(upellets,material,1)
                .input(plateDense,Lead,1)
                .input(plateDense,Lead,1)
                .input(upellets,material,1)
                .input(upellets,material,1)
                .input(plateDense,Lead,1)
                .input(block,Graphite,1)
                .input(block,Graphite,1)
                .input(block,Graphite,1)
                .input(block,Graphite,1)
                .fluidInputs(Nitrogen.getFluid(16000))
                .fluidInputs(Argon.getFluid(16000))
                .fluidInputs(Helium.getFluid(16000))
                .fluidInputs(Oxygen.getFluid(16000))
                .duration(400+fuel*20)
                .EUt(VA[fuel])
                .buildAndRegister();
    }

    private static void DECAY_CHAMBER_RECIPES(Material material1,Material material2,int tier) {
        DECAY_CHAMBER_RECIPES.recipeBuilder()
                .input(dust,material1)
                .output(dust,material2)
                .duration(400+tier*200)
                .EUt(VA[tier])
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
