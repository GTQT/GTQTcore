package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;

import static gregicality.science.api.unification.materials.GCYSMaterials.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtechfoodoption.GTFOMaterialHandler.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.electrode;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class CPULine {
    public static void init() {
        Pre();          //基板
    }

    private static void Pre()
    {
        //酚醛基板
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .circuitMeta(1)
                .fluidInputs(Phenol.getFluid(1000))
                .fluidInputs(Formaldehyde.getFluid(1000))
                .fluidOutputs(Phenolic.getFluid(2000))
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .circuitMeta(1)
                .notConsumable(dust,Silver,16)
                .fluidInputs(Methanol.getFluid(1000))
                .fluidOutputs(Formaldehyde.getFluid(100))
                .fluidOutputs(Hydrogen.getFluid(200))
                .buildAndRegister();

        //酚醛基板简化版
        //防腐木板+胶水（浸渍基板）+酚醛
        CUTTER_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .input(plank, TreatedWood)
                .output(plate,TreatedWood)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .input(plate,TreatedWood,4)
                .input(plate,Copper,2)
                .fluidInputs(RedAlloy.getFluid(576))
                .fluidInputs(Glue.getFluid(400))
                .output(IMPREGNATED_SUBSTRATE,4)
                .buildAndRegister();

        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .input(IMPREGNATED_SUBSTRATE)
                .fluidInputs(Phenolic.getFluid(100))
                .output(MetaItems.PHENOLIC_BOARD)
                .buildAndRegister();

        //石墨电极线
        //石墨+沥青=浸渍石墨
        FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                .duration(2000)
                .EUt(8)
                .input(stick,Graphite,16)
                .fluidInputs(HighlyPurifiedCoalTar.getFluid(100))
                .output(IMPREGNATED_GRAPHITE_RODS)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(100)
                .EUt(30)
                .input(IMPREGNATED_GRAPHITE_RODS)
                .input(dust,Graphite,16)
                .fluidInputs(Asphalt.getFluid(2000))
                .output(IMPREGNATED_GRAPHITE_RODSA)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .duration(2000)
                .EUt(120)
                .blastFurnaceTemp(1800)
                .input(IMPREGNATED_GRAPHITE_RODSA)
                .input(dust,Diamond)
                .fluidInputs(Nitrogen.getFluid(1000))
                .output(electrode,Graphite)
                .buildAndRegister();
    }
}
