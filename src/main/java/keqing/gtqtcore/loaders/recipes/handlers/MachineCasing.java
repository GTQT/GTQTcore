package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.recipes.RecipeMaps;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTADVBlock;

import static gregicality.multiblocks.api.recipes.GCYMRecipeMaps.ALLOY_BLAST_RECIPES;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.recipes.RecipeMaps.MIXER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.BLAST_ARC_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.ChloroplatinicAcid;
import static keqing.gtqtcore.api.unification.GTQTMaterials.GalvanizedSteel;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Talonite;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing1.TurbineCasingType.GALVANIZE_STEEL_CASING;

public class MachineCasing {
    public static void init() {
        DustMixer();
        CasingAssembler();
        Arc();

    }

    private static void Arc() {
        BLAST_ARC_RECIPES.recipeBuilder()
                .input(ingot,Steel,16)
                .output(dust, Ash, 16)
                .duration(2000)
                .EUt(VA[HV])
                .buildAndRegister();

        ALLOY_BLAST_RECIPES.recipeBuilder().duration(600).EUt(VA[EV])
                .input(dust, Iron, 4)
                .input(dust, Invar, 3)
                .input(dust, Manganese)
                .input(dust, Chrome)
                .circuitMeta(1)
                .fluidOutputs(StainlessSteel.getFluid(9*144))
                .buildAndRegister();

        ALLOY_BLAST_RECIPES.recipeBuilder().duration(600).EUt(VA[EV])
                .input(dust, Iron, 6)
                .input(dust, Nickel)
                .input(dust, Manganese)
                .input(dust, Chrome)
                .circuitMeta(1)
                .fluidOutputs(StainlessSteel.getFluid(9*144))
                .buildAndRegister();

    }

    private static void DustMixer() {
        MIXER_RECIPES.recipeBuilder()
                .input(dust,Cobalt,4)
                .input(dust,Chrome,3)
                .input(dust,Phosphorus,2)
                .input(dust,Molybdenum,1)
                .output(dust, Talonite, 4)
                .duration(120)
                .EUt(VA[MV])
                .buildAndRegister();


    }

    private static void CasingAssembler() {
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder().duration(200).EUt(120)
                .input(plate,Talonite, 6)
                .input(frameGt,Talonite, 1)
                .circuitMeta(6)
                .outputs(GTQTMetaBlocks.ADV_BLOCK.getItemVariant(GTQTADVBlock.CasingType.Talonite))
                .buildAndRegister();
    }
}
