package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.MarkerMaterials;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.metatileentities.MetaTileEntities.ELECTROLYZER;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class Electrolyzer {
    public static void init() {
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
                .EUt(30).duration(200)
                .input(dust, Potassium, 1)
                .fluidInputs(Chlorine.getFluid(1000))
                .fluidOutputs(PotassiumChloride.getFluid(1000))
                .buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
                .EUt(30).duration(800)
                .fluidInputs(Water.getFluid(3000))
                .fluidInputs(PotassiumChloride.getFluid(500))
                .fluidInputs(SodiumHydroxideSolution.getFluid(500))
                .input(dust, SodiumBisulfate, 1)
                .fluidOutputs(EleAcid.getFluid(4000))
                .buildAndRegister();


        //石墨电极线
        //石墨+沥青=浸渍石墨
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(300)
                .EUt(30)
                .input(stick, Graphite, 8)
                .fluidInputs(HighlyPurifiedCoalTar.getFluid(2000))
                .output(IMPREGNATED_GRAPHITE_RODS)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(300)
                .EUt(30)
                .input(IMPREGNATED_GRAPHITE_RODS)
                .input(dust, Carbon, 8)
                .fluidInputs(Asphalt.getFluid(1440))
                .output(IMPREGNATED_GRAPHITE_RODSA)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .duration(1000)
                .EUt(120)
                .blastFurnaceTemp(1800)
                .input(IMPREGNATED_GRAPHITE_RODSA, 32)
                .input(dust, Diamond, 16)
                .output(ELECTRODE_GRAPHITE)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .EUt(7680).duration(800)
                .input(plate, Platinum, 8)
                .input(stick, Platinum, 4)
                .input(dust, Graphite, 16)
                .output(ELECTRODE_PLATINUM)
                .circuitMeta(22)
                .fluidInputs(Polyethylene.getFluid(1440))
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .EUt(120).duration(800)
                .input(plate, Silver, 8)
                .input(stick, Silver, 4)
                .input(dust, Graphite, 16)
                .output(ELECTRODE_SILVER)
                .circuitMeta(22)
                .fluidInputs(Polyethylene.getFluid(1440))
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .EUt(1960).duration(800)
                .input(plate, Gold, 8)
                .input(stick, Gold, 4)
                .input(dust, Graphite, 16)
                .output(ELECTRODE_GOLD)
                .circuitMeta(22)
                .fluidInputs(Polyethylene.getFluid(1440))
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .EUt(480).duration(800)
                .input(plate, Molybdenum, 8)
                .input(stick, Molybdenum, 4)
                .input(dust, Graphite, 16)
                .output(ELECTRODE_MOLOYBDENUM, 1)
                .circuitMeta(22)
                .fluidInputs(Polyethylene.getFluid(1440))
                .buildAndRegister();

        //主方块
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(ELECTROLYZER[1], 8)
                .input(circuit, MarkerMaterials.Tier.LV, 16)
                .input(wireFine, Copper, 16)
                .input(stick, Steel, 32)
                .input(rotor, Invar, 8)
                .input(plateDense, Steel, 4)
                .fluidInputs(Tin.getFluid(L * 8))
                .outputs(GTQTMetaTileEntities.ELECTROBATH.getStackForm())
                .duration(200).EUt(30).buildAndRegister();
    }
}
