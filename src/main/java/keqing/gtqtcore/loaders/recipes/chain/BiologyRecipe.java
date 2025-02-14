package keqing.gtqtcore.loaders.recipes.chain;


import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;
import gregtechfoodoption.recipe.GTFORecipeMaps;
import keqing.gtqtcore.api.items.IntCircuitIngredientBiology;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.ZPM;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.CarbonDioxide;
import static gregtech.api.unification.material.Materials.Water;
import static gregtech.api.unification.ore.OrePrefix.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.CalciumCarbonate;

public class BiologyRecipe {

    public static ItemStack getBiologyCircuitData(int configuration) {
        ItemStack stack = GTQTMetaItems.BIOLOGY_INTEGRATED_CIRCUIT.getStackForm();
        IntCircuitIngredientBiology.setCircuitConfiguration(stack, configuration);
        return stack;
    }

    public static void init() {
        ModHandler.addShapelessRecipe("integrated_circuit_biology", IntCircuitIngredientBiology.getIntegratedCircuit(0), new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.ULV));

        GTFORecipeMaps.GREENHOUSE_RECIPES.recipeBuilder()
                .EUt(GTValues.VA[ZPM])
                .duration(2000)
                .input(GTQTMetaBlocks.BLOCK_PINE_SAPLING,1)
                .fluidInputs(Water.getFluid(10000))
                .output(GTQTMetaBlocks.BLOCK_PINE_LOG,6)
                .output(GTQTMetaBlocks.BLOCK_PINE_SAPLING)
                .output(GTQTMetaItems.PINE_CONE)
                .circuitMeta(1)
                .buildAndRegister();

        GTFORecipeMaps.GREENHOUSE_RECIPES.recipeBuilder()
                .EUt(GTValues.VA[ZPM])
                .duration(2000)
                .input(GTQTMetaBlocks.BLOCK_PINE_SAPLING,1)
                .fluidInputs(Water.getFluid(10000))
                .output(GTQTMetaBlocks.BLOCK_PINE_SAPLING,1)
                .output(GTQTMetaBlocks.BLOCK_PINE_LOG,5)
                .output(GTQTMetaBlocks.BLOCK_PINE_LEAVES,20)
                .circuitMeta(2)
                .buildAndRegister();

        GTFORecipeMaps.GREENHOUSE_RECIPES.recipeBuilder()
                .EUt(GTValues.VA[ZPM])
                .duration(2000)
                .input(GTQTMetaBlocks.BLOCK_PINE_SAPLING,1)
                .fluidInputs(Water.getFluid(15000))
                .output(GTQTMetaBlocks.BLOCK_PINE_SAPLING,1)
                .output(GTQTMetaBlocks.BLOCK_PINE_LOG,5)
                .output(GTQTMetaItems.PINE_CONE,5)
                .circuitMeta(3)
                .buildAndRegister();

        GTFORecipeMaps.GREENHOUSE_RECIPES.recipeBuilder()
                .EUt(1920)
                .duration(600)
                .input(MetaItems.FERTILIZER,2)
                .input(GTQTMetaBlocks.BLOCK_PINE_SAPLING,1)
                .fluidInputs(Water.getFluid(20000))
                .output(GTQTMetaBlocks.BLOCK_PINE_LOG,10)
                .output(GTQTMetaBlocks.BLOCK_PINE_SAPLING,2)
                .output(GTQTMetaItems.PINE_CONE,4)
                .circuitMeta(4)
                .buildAndRegister();

        //Common Algae Usage
        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .input(GTQTMetaItems.COMMON_ALGAE, 10)
                .fluidInputs(Materials.DistilledWater.getFluid(500))
                .notConsumable(getBiologyCircuitData(12))
                .EUt(64)
                .duration(100)
                .fluidOutputs(Materials.Methane.getFluid(500))
                .buildAndRegister();
        //Green Algae Usage
        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .input(GTQTMetaItems.GREEN_ALGAE, 5)
                .inputs(new ItemStack(Blocks.DIRT))
                .notConsumable(getBiologyCircuitData(3))
                .EUt(30)
                .duration(20)
                .output(GTQTMetaItems.ROUGH_BIOLOGY_RESIN)
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .input(GTQTMetaItems.GREEN_ALGAE, 16)
                .input(GTQTMetaItems.COMPOST, 8)
                .fluidInputs(GTQTMaterials.UreaMix.getFluid(200))
                .notConsumable(getBiologyCircuitData(12))
                .EUt(60)
                .duration(600)
                .output(MetaItems.FERTILIZER, 32)
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .input(GTQTMetaItems.GREEN_ALGAE, 10)
                .input(GTQTMetaItems.BROWN_ALGAE, 6)
                .fluidInputs(Materials.DistilledWater.getFluid(5000))
                .notConsumable(getBiologyCircuitData(7))
                .EUt(60)
                .duration(1000)
                .fluidOutputs(Materials.SulfuricAcid.getFluid(5000))
                .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
                .input(GTQTMetaItems.GREEN_ALGAE, 4)
                .output(GTQTMetaItems.COMPOST)
                .EUt(2).duration(400)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .notConsumable(getBiologyCircuitData(4))
                .input(GTQTMetaItems.GREEN_ALGAE, 2)
                .output(GTQTMetaItems.CELLULOSE_FIBER)
                .EUt(16).duration(30)
                .buildAndRegister();

        //Brown Algae Usage
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(getBiologyCircuitData(8))
                .input(GTQTMetaItems.BROWN_ALGAE, 40)
                .fluidInputs(Materials.DistilledWater.getFluid(2000))
                .output(dust, Materials.SodaAsh, 20)
                .EUt(30).duration(600)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .notConsumable(getBiologyCircuitData(8))
                .input(GTQTMetaItems.BROWN_ALGAE, 2)
                .fluidInputs(Materials.DistilledWater.getFluid(100))
                .output(dust, Materials.SodaAsh, 1)
                .EUt(30).duration(30)
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(3)
                .input(GTQTMetaItems.CELLULOSE_FIBER_YELLOW, 2)
                .input(GTQTMetaItems.BROWN_ALGAE, 10)
                .fluidInputs(Materials.DistilledWater.getFluid(5000))
                .notConsumable(getBiologyCircuitData(7))
                .EUt(180)
                .duration(120)
                .fluidOutputs(Materials.SulfuricAcid.getFluid(5000))
                .buildAndRegister();

        EXTRACTOR_RECIPES.recipeBuilder()
                .input(GTQTMetaItems.BROWN_ALGAE, 10)
                .output(GTQTMetaItems.ALGAE_ACID, 2)
                .EUt(30).duration(45)
                .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
                .input(GTQTMetaItems.BROWN_ALGAE, 2)
                .output(GTQTMetaItems.COMPOST)
                .EUt(2).duration(400)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .notConsumable(getBiologyCircuitData(8))
                .input(GTQTMetaItems.BROWN_ALGAE, 4)
                .output(dust, Materials.LithiumChloride, 1)
                .blastFurnaceTemp(1200)
                .EUt(120).duration(24)
                .buildAndRegister();

        //Gold Algae

        MACERATOR_RECIPES.recipeBuilder()
                .input(GTQTMetaItems.GOLD_ALGAE)
                .output(GTQTMetaItems.COMPOST)
                .EUt(2).duration(400)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .notConsumable(getBiologyCircuitData(12))
                .input(GTQTMetaItems.GOLD_ALGAE, 2)
                .output(GTQTMetaItems.CELLULOSE_FIBER_YELLOW)
                .EUt(120).duration(30)
                .buildAndRegister();

        //Red Algae

        MACERATOR_RECIPES.recipeBuilder()
                .input(GTQTMetaItems.RED_ALGAE)
                .output(GTQTMetaItems.COMPOST, 2)
                .EUt(2).duration(400)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .notConsumable(getBiologyCircuitData(16))
                .input(GTQTMetaItems.RED_ALGAE, 2)
                .output(GTQTMetaItems.CELLULOSE_FIBER_RED)
                .EUt(240).duration(30)
                .buildAndRegister();

        //Algae Acid
        LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .input(GTQTMetaItems.ALGAE_ACID, 2)
                .input(GTQTMetaItems.CELLULOSE_FIBER, 8)
                .output(GTQTMetaItems.CELLULOSE_PULP, 10)
                .EUt(16).duration(200)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(GTQTMetaItems.ALGAE_ACID, 1)
                .input(GTQTMetaItems.CELLULOSE_FIBER, 4)
                .output(GTQTMetaItems.CELLULOSE_PULP, 5)
                .EUt(16).duration(100)
                .buildAndRegister();

        //Cellulose Fiber
        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .input(GTQTMetaItems.CELLULOSE_FIBER, 8)
                .input(GTQTMetaItems.CELLULOSE_FIBER_YELLOW, 6)
                .input(GTQTMetaItems.CELLULOSE_FIBER_RED, 4)
                .fluidInputs(Materials.Methane.getFluid(2000))
                .notConsumable(getBiologyCircuitData(13))
                .EUt(60)
                .duration(200)
                .fluidOutputs(Materials.Ethylene.getFluid(2000))
                .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
                .input(GTQTMetaItems.CELLULOSE_FIBER, 3)
                .output(GTQTMetaItems.COMPOST)
                .EUt(2).duration(400)
                .buildAndRegister();

        EXTRACTOR_RECIPES.recipeBuilder()
                .input(GTQTMetaItems.CELLULOSE_FIBER, 3)
                .fluidOutputs(Materials.Methanol.getFluid(250))
                .EUt(30).duration(37)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .notConsumable(getBiologyCircuitData(2))
                .input(GTQTMetaItems.CELLULOSE_FIBER, 4)
                .output(GTQTMetaItems.WOOD_PELLETS, 8)
                .EUt(7).duration(16)
                .buildAndRegister();

        EXTRUDER_RECIPES.recipeBuilder()
                .notConsumable(GTQTMetaItems.PELLETS_MOULD)
                .input(GTQTMetaItems.CELLULOSE_FIBER, 4)
                .output(GTQTMetaItems.WOOD_PELLETS)
                .EUt(16).duration(66)
                .buildAndRegister();

        //Cellulose Fiber yellow
        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .input(GTQTMetaItems.CELLULOSE_FIBER_YELLOW, 6)
                .input(GTQTMetaItems.CELLULOSE_FIBER_RED, 16)
                .fluidInputs(GTQTMaterials.FermentationBase.getFluid(48000))
                .notConsumable(getBiologyCircuitData(5))
                .EUt(32)
                .duration(2000)
                .fluidOutputs(Butanol.getFluid(18000))
                .fluidOutputs(Materials.Acetone.getFluid(9000))
                .fluidOutputs(Materials.Ethanol.getFluid(3000))
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .input(GTQTMetaItems.CELLULOSE_FIBER, 8)
                .input(GTQTMetaItems.CELLULOSE_FIBER_YELLOW, 6)
                .input(GTQTMetaItems.CELLULOSE_FIBER_RED, 4)
                .fluidInputs(Materials.Methane.getFluid(2000))
                .notConsumable(getBiologyCircuitData(13))
                .EUt(60)
                .duration(200)
                .fluidOutputs(Materials.Ethylene.getFluid(3000))
                .buildAndRegister();

        EXTRACTOR_RECIPES.recipeBuilder()
                .input(GTQTMetaItems.CELLULOSE_FIBER_YELLOW)
                .fluidOutputs(Materials.Ammonia.getFluid(500))
                .EUt(120).duration(300)
                .buildAndRegister();

        //Cellulose Fiber Red
        EXTRACTOR_RECIPES.recipeBuilder()
                .input(GTQTMetaItems.CELLULOSE_FIBER_RED, 3)
                .output(dust, CalciumCarbonate, 5)
                .EUt(240).duration(90)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust,CalciumCarbonate, 5)
                .output(dust,CalciumCarbide, 2)
                .fluidOutputs(CarbonDioxide.getFluid(3000))
                .EUt(240).duration(90)
                .blastFurnaceTemp(1000)
                .circuitMeta(1)
                .buildAndRegister();

        //Wood Pellets
        EXTRACTOR_RECIPES.recipeBuilder()
                .input(GTQTMetaItems.WOOD_PELLETS)
                .fluidOutputs(Materials.CarbonDioxide.getFluid(70))
                .EUt(120).duration(300)
                .buildAndRegister();

        COKE_OVEN_RECIPES.recipeBuilder()
                .input(GTQTMetaItems.WOOD_PELLETS, 2)
                .output(Items.COAL, 3, 1)
                .duration(1200)
                .buildAndRegister();

        //Cellulose Pulp
        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .input(GTQTMetaItems.CELLULOSE_PULP, 8)
                .fluidInputs(GTQTMaterials.Resin.getFluid(144))
                .notConsumable(getBiologyCircuitData(3))
                .EUt(30)
                .duration(1200)
                .output(MetaItems.STICKY_RESIN, 32)
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(2)
                .input(GTQTMetaItems.CELLULOSE_PULP, 4)
                .fluidInputs(Materials.AceticAcid.getFluid(500))
                .fluidInputs(GTQTMaterials.PropionicAcid.getFluid(500))
                .notConsumable(getBiologyCircuitData(16))
                .EUt(240)
                .duration(200)
                .fluidOutputs(Materials.Polyethylene.getFluid(1000))
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .notConsumable(getBiologyCircuitData(2))
                .input(GTQTMetaItems.CELLULOSE_PULP, 2)
                .output(Items.PAPER, 2)
                .EUt(16).duration(20)
                .buildAndRegister();

        //Rough Biology Resin
        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .input(GTQTMetaItems.ROUGH_BIOLOGY_RESIN)
                .fluidInputs(Materials.Ethanol.getFluid(200))
                .notConsumable(getBiologyCircuitData(3))
                .EUt(30)
                .duration(100)
                .fluidOutputs(GTQTMaterials.Resin.getFluid(500))
                .buildAndRegister();

        //Aluminium Pellets
        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .input(gem, Materials.Coke, 1)
                .input(dust, Materials.SodaAsh, 6)
                .input(GTQTMetaItems.ALUMINIUM_PELLETS)
                .output(dust, GTQTMaterials.SodiumAluminate, 8)
                .notConsumable(getBiologyCircuitData(18))
                .EUt(120)
                .duration(2400)
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .input(gem, Materials.Coal, 2)
                .input(dust, Materials.SodaAsh, 6)
                .input(GTQTMetaItems.ALUMINIUM_PELLETS)
                .output(dust, GTQTMaterials.SodiumAluminate, 8)
                .notConsumable(getBiologyCircuitData(18))
                .EUt(120)
                .duration(3600)
                .buildAndRegister();

        //Mould Pellets
        LATHE_RECIPES.recipeBuilder()
                .input(block, Materials.Gold)
                .output(GTQTMetaItems.PELLETS_MOULD)
                .EUt(90).duration(9000)
                .buildAndRegister();

        //Purified Aluminium Mixture

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .input(crushedPurified, Materials.Sapphire, 5)
                .fluidInputs(Materials.Steam.getFluid(10000))
                .notConsumable(getBiologyCircuitData(14))
                .EUt(30)
                .duration(1200)
                .output(GTQTMetaItems.PURIFIED_ALUMINIUM_MIXTURE, 3)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(300))
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .input(crushedPurified, Materials.GreenSapphire, 5)
                .fluidInputs(Materials.Steam.getFluid(10000))
                .notConsumable(getBiologyCircuitData(14))
                .EUt(30)
                .duration(1200)
                .output(GTQTMetaItems.PURIFIED_ALUMINIUM_MIXTURE, 3)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(300))
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .input(crushedPurified, Materials.Ruby, 6)
                .fluidInputs(Materials.Steam.getFluid(12000))
                .notConsumable(getBiologyCircuitData(14))
                .EUt(60)
                .duration(1200)
                .output(GTQTMetaItems.PURIFIED_ALUMINIUM_MIXTURE, 3)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(300))
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .input(crushedPurified, Materials.Pyrope, 20)
                .fluidInputs(Materials.Steam.getFluid(40000))
                .notConsumable(getBiologyCircuitData(14))
                .EUt(90)
                .duration(1200)
                .output(GTQTMetaItems.PURIFIED_ALUMINIUM_MIXTURE, 3)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(300))
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .input(crushedPurified, Materials.Spodumene, 10)
                .fluidInputs(Materials.Steam.getFluid(20000))
                .notConsumable(getBiologyCircuitData(14))
                .EUt(90)
                .duration(1200)
                .output(GTQTMetaItems.PURIFIED_ALUMINIUM_MIXTURE, 2)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(200))
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .input(crushedPurified, Materials.Grossular, 20)
                .fluidInputs(Materials.Steam.getFluid(40000))
                .notConsumable(getBiologyCircuitData(14))
                .EUt(90)
                .duration(1200)
                .output(GTQTMetaItems.PURIFIED_ALUMINIUM_MIXTURE, 3)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(300))
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .input(crushedPurified, Materials.Sodalite, 11)
                .fluidInputs(Materials.Steam.getFluid(22000))
                .notConsumable(getBiologyCircuitData(14))
                .EUt(90)
                .duration(1200)
                .output(GTQTMetaItems.PURIFIED_ALUMINIUM_MIXTURE, 5)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(500))
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .input(crushedPurified, Materials.Bauxite, 39)
                .fluidInputs(Materials.Steam.getFluid(78000))
                .notConsumable(getBiologyCircuitData(14))
                .EUt(90)
                .duration(1200)
                .output(GTQTMetaItems.PURIFIED_ALUMINIUM_MIXTURE, 23)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(2300))
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .input(crushedPurified, Materials.Lazurite, 14)
                .fluidInputs(Materials.Steam.getFluid(28000))
                .notConsumable(getBiologyCircuitData(14))
                .EUt(120)
                .duration(1200)
                .output(GTQTMetaItems.PURIFIED_ALUMINIUM_MIXTURE, 5)
                .fluidOutputs(GTQTMaterials.RedMud.getFluid(500))
                .buildAndRegister();

        EXTRUDER_RECIPES.recipeBuilder()
                .notConsumable(GTQTMetaItems.PELLETS_MOULD)
                .input(GTQTMetaItems.PURIFIED_ALUMINIUM_MIXTURE, 4)
                .output(GTQTMetaItems.ALUMINIUM_PELLETS)
                .EUt(64).duration(600)
                .buildAndRegister();
        //Fish~
        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(2)
                .inputs(new ItemStack(Items.FISH))
                .EUt(300)
                .duration(233)
                .outputs(new ItemStack(Items.COOKED_FISH))
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .fluidInputs(Materials.CarbonDioxide.getFluid(300))
                .fluidInputs(Materials.Ammonia.getFluid(600))
                .notConsumable(getBiologyCircuitData(9))
                .EUt(30)
                .duration(100)
                .fluidOutputs(GTQTMaterials.UreaMix.getFluid(300))
                .fluidOutputs(Materials.DistilledWater.getFluid(300))
                .buildAndRegister();

        GTQTcoreRecipeMaps.CHEMICAL_PLANT.recipeBuilder()
                .recipeLevel(1)
                .fluidInputs(GTQTMaterials.UreaMix.getFluid(200))
                .fluidInputs(Formaldehyde.getFluid(200))
                .notConsumable(getBiologyCircuitData(9))
                .EUt(30)
                .duration(100)
                .fluidOutputs(GTQTMaterials.Resin.getFluid(200))
                .buildAndRegister();


    }
}