package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.unification.material.Material;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockParticleAcceleratorCasing;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.EMITTER_IV;
import static gregtech.common.items.MetaItems.FLUID_CELL_LARGE_TUNGSTEN_STEEL;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.BEAM_COLLECTION;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.PAC_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.Carbon16;
import static keqing.gtqtcore.api.unification.GCYSMaterials.Radium226;
import static keqing.gtqtcore.api.unification.GTQTMaterials.AuPdCCatalyst;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static supercritical.api.unification.material.SCMaterials.*;

public class NuclearLine {
    public static void init() {
        GRANULAR_SOURCE();
        //核素——》衰变后 + 种类 + 等级
        PAc(Hydrogen,PROTON, 1, 1);
        PAc(Deuterium,DEUTERON, 2, 1);
        PAc(Tritium,TRITON, 3, 1);
        PAc(Helium,ALPHA, 4, 1);
        PAc(Sodium,POSITRON, 6, 1);
        PAc(Calcium,CALCIUM_48_ION, 7, 1);
        PAc(Boron,BORON_ION, 8, 1);

        PA(Uranium238,ALPHA, Thorium, 4, 3);
        PA(Plutonium238,ALPHA, Uranium236, 4, 4);
        PA(Americium,ALPHA, Neptunium, 4, 5);
        PA(Curium246,ALPHA, Plutonium244, 4, 6);

        PAC_RECIPES.recipeBuilder()
                .notConsumable(EMITTER_IV)
                .duration(1000)
                .output(ELECTRON)
                .part(5)
                .EUt(VA[EV])
                .buildAndRegister();

        BEAM_COLLECTION.recipeBuilder()
                .notConsumable(EMITTER_IV)
                .duration(200)
                .output(ELECTRON,10)
                .part(5)
                .EUt(VA[EV])
                .buildAndRegister();

        Radiation();
    }

    private static void Radiation() {
        CANNER_RECIPES.recipeBuilder()
                .EUt(7680).duration(800)
                .input(dust, Uranium235, 16)
                .input(FLUID_CELL_LARGE_TUNGSTEN_STEEL)
                .output(RADIATION_U235)
                .buildAndRegister();

        // 铀-238 放射源
        CANNER_RECIPES.recipeBuilder()
                .EUt(7680).duration(800)
                .input(dust, Uranium238, 16)
                .input(FLUID_CELL_LARGE_TUNGSTEN_STEEL)
                .output(RADIATION_U238)
                .buildAndRegister();

        // 钚-241 放射源
        CANNER_RECIPES.recipeBuilder()
                .EUt(7680).duration(800)
                .input(dust, Plutonium241, 16)
                .input(FLUID_CELL_LARGE_TUNGSTEN_STEEL)
                .output(RADIATION_U241)
                .buildAndRegister();

        // 钚-244 放射源
        CANNER_RECIPES.recipeBuilder()
                .EUt(7680).duration(800)
                .input(dust, Plutonium244, 16)
                .input(FLUID_CELL_LARGE_TUNGSTEN_STEEL)
                .output(RADIATION_U244)
                .buildAndRegister();

        // 锆-241 放射源
        CANNER_RECIPES.recipeBuilder()
                .EUt(7680).duration(800)
                .input(dust, Americium241, 16)
                .input(FLUID_CELL_LARGE_TUNGSTEN_STEEL)
                .output(RADIATION_Am241)
                .buildAndRegister();


    }

    private static void PAc(Material material1, MetaItem<?>.MetaValueItem particle, int kind, int tier) {
        PAC_RECIPES.recipeBuilder()
                .fluidInputs(material1.getFluid(1000))
                .duration(1000)
                .output(particle)
                .part(kind)
                .EUt(VA[EV+tier])
                .buildAndRegister();

        BEAM_COLLECTION.recipeBuilder()
                .fluidInputs(material1.getFluid(1000))
                .duration(200)
                .output(particle,10)
                .part(kind)
                .EUt(VA[EV+tier])
                .buildAndRegister();
    }


    private static void PA(Material material1, MetaItem<?>.MetaValueItem particle, Material material2, int kind, int tier) {
        PAC_RECIPES.recipeBuilder()
                .input(dustTiny, material1)
                .output(dustTiny, material2)
                .duration(1000)
                .output(particle)
                .part(kind)
                .EUt(VA[EV+tier])
                .buildAndRegister();

        BEAM_COLLECTION.recipeBuilder()
                .input(dustTiny, material1)
                .output(dustTiny, material2)
                .duration(200)
                .output(particle,10)
                .part(kind)
                .EUt(VA[EV+tier])
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
                .outputs(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(BlockParticleAcceleratorCasing.MachineType.GRANULAR_SOURCE_A))
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
                .outputs(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(BlockParticleAcceleratorCasing.MachineType.GRANULAR_SOURCE_B))
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
                .outputs(GTQTMetaBlocks.blockParticleAcceleratorCasing.getItemVariant(BlockParticleAcceleratorCasing.MachineType.GRANULAR_SOURCE_C))
                .circuitMeta(1)
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(400).EUt(1920).buildAndRegister();

        //钚244的低效率配方
        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust, Plutonium241, 1)
                .chancedOutput(dustSmall, Plutonium244, 1, 1000, 250)
                .chancedOutput(dustSmall, Plutonium239, 2, 2000, 250)
                .cleanroom(CleanroomType.CLEANROOM)
                .duration(1600).EUt(7680).buildAndRegister();
    }
}
