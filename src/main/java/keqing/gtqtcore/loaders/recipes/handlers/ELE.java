package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTElectrobath;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.ELECTROLYZER;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.ELECTROBATH;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class ELE {
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
                .fluidInputs(HighlyPurifiedCoalTar.getFluid(500))
                .output(IMPREGNATED_GRAPHITE_RODS)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .duration(300)
                .EUt(30)
                .input(IMPREGNATED_GRAPHITE_RODS)
                .input(dust, Carbon, 8)
                .fluidInputs(Asphalt.getFluid(72))
                .output(IMPREGNATED_GRAPHITE_RODSA)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .duration(1000)
                .EUt(120)
                .blastFurnaceTemp(1800)
                .input(IMPREGNATED_GRAPHITE_RODSA)
                .input(dust, Diamond)
                .fluidInputs(Nitrogen.getFluid(200))
                .output(ELECTRODE_GRAPHITE)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .EUt(120).duration(800)
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
                .EUt(120).duration(800)
                .input(plate, Gold, 8)
                .input(stick, Gold, 4)
                .input(dust, Graphite, 16)
                .output(ELECTRODE_GOLD)
                .circuitMeta(22)
                .fluidInputs(Polyethylene.getFluid(1440))
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .EUt(120).duration(800)
                .input(plate, Molybdenum, 8)
                .input(stick, Molybdenum, 4)
                .input(dust, Graphite, 16)
                .output(ELECTRODE_MOLOYBDENUM, 1)
                .circuitMeta(22)
                .fluidInputs(Polyethylene.getFluid(1440))
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .EUt(30).duration(800)
                .input(HULL[1])
                .input(ELECTRODE_GRAPHITE)
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.I_ELECTROBATH))
                .fluidInputs(Tin.getFluid(1440))
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .EUt(120).duration(800)
                .input(HULL[2])
                .input(ELECTRODE_SILVER)
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.II_ELECTROBATH))
                .fluidInputs(Tin.getFluid(1440))
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .EUt(480).duration(800)
                .input(HULL[3])
                .input(ELECTRODE_GOLD)
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.III_ELECTROBATH))
                .fluidInputs(Tin.getFluid(1440))
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .EUt(1920).duration(800)
                .input(HULL[4])
                .input(ELECTRODE_MOLOYBDENUM)
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.IV_ELECTROBATH))
                .fluidInputs(Tin.getFluid(1440))
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .EUt(7680).duration(800)
                .input(HULL[5])
                .input(ELECTRODE_PLATINUM)
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.V_ELECTROBATH))
                .fluidInputs(Tin.getFluid(1440))
                .buildAndRegister();


        //主方块
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(ELECTROLYZER[1], 8)
                .input(circuit, MarkerMaterials.Tier.LV, 16)
                .input(wireFine, Copper, 16)
                .input(stick,Steel,32)
                .input(rotor,Invar,8)
                .input(plateDense,Steel,4)
                .fluidInputs(Tin.getFluid(L * 8))
                .outputs(GTQTMetaTileEntities.ELECTROBATH.getStackForm())
                .duration(200).EUt(30).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .fluidInputs(DistilledWater.getFluid(4000))
                .fluidOutputs(Hydrogen.getFluid(8000))
                .fluidOutputs(Oxygen.getFluid(4000))
                .EUt(180)
                .tier(1)
                .duration(800)
                .buildAndRegister();


        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .input(dust, Salt, 2)
                .output(dust, Sodium, 1)
                .fluidOutputs(Chlorine.getFluid(1000))
                .EUt(180)
                .tier(1)
                .duration(800)
                .buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .input(dust, RockSalt, 2)
                .output(dust, Potassium, 1)
                .fluidOutputs(Chlorine.getFluid(1000))
                .EUt(180)
                .tier(1)
                .duration(800)
                .buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .fluidInputs(Water.getFluid(4000))
                .fluidOutputs(Hydrogen.getFluid(8000))
                .fluidOutputs(Oxygen.getFluid(4000))
                .EUt(240)
                .tier(2)
                .duration(800)
                .buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .fluidInputs(SaltWater.getFluid(4000))
                .output(dust, SodiumHydroxide, 12)
                .fluidOutputs(Chlorine.getFluid(4000))
                .fluidOutputs(Hydrogen.getFluid(4000))
                .EUt(240)
                .tier(2)
                .duration(200)
                .buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .tier(3)
                .input(dust, SodiumHydroxide, 3)
                .output(dust, Sodium)
                .fluidOutputs(Oxygen.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(1000))
                .duration(150).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .tier(3)
                .input(dust, Sugar, 10)
                .output(dust, Carbon, 6)
                .fluidOutputs(Oxygen.getFluid(6000))
                .fluidOutputs(Hydrogen.getFluid(12000))
                .duration(64).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .tier(3)
                .input(dust, Apatite, 9)
                .output(dust, Calcium, 5)
                .output(dust, Phosphorus, 3)
                .fluidOutputs(Chlorine.getFluid(1000))
                .duration(288).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .tier(3)
                .fluidInputs(Propane.getFluid(1000))
                .output(dust, Carbon, 3)
                .fluidOutputs(Hydrogen.getFluid(8000))
                .duration(176).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .tier(3)
                .fluidInputs(Butene.getFluid(1000))
                .output(dust, Carbon, 4)
                .fluidOutputs(Hydrogen.getFluid(8000))
                .duration(192).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .tier(3)
                .fluidInputs(Butane.getFluid(1000))
                .output(dust, Carbon, 4)
                .fluidOutputs(Hydrogen.getFluid(10000))
                .duration(224).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .tier(3)
                .fluidInputs(Styrene.getFluid(1000))
                .output(dust, Carbon, 8)
                .fluidOutputs(Hydrogen.getFluid(8000))
                .duration(384).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .tier(3)
                .fluidInputs(Butadiene.getFluid(1000))
                .output(dust, Carbon, 4)
                .fluidOutputs(Hydrogen.getFluid(6000))
                .duration(240).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .tier(3)
                .fluidInputs(Phenol.getFluid(1000))
                .output(dust, Carbon, 6)
                .fluidOutputs(Hydrogen.getFluid(6000))
                .fluidOutputs(Oxygen.getFluid(1000))
                .duration(312).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .tier(3)
                .fluidInputs(Ethylene.getFluid(1000))
                .output(dust, Carbon, 2)
                .fluidOutputs(Hydrogen.getFluid(4000))
                .duration(96).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .tier(3)
                .fluidInputs(Benzene.getFluid(1000))
                .output(dust, Carbon, 6)
                .fluidOutputs(Hydrogen.getFluid(6000))
                .duration(288).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .tier(3)
                .fluidInputs(Ethanol.getFluid(1000))
                .output(dust, Carbon, 2)
                .fluidOutputs(Hydrogen.getFluid(6000))
                .fluidOutputs(Oxygen.getFluid(1000))
                .duration(144).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .tier(3)
                .fluidInputs(Toluene.getFluid(1000))
                .output(dust, Carbon, 7)
                .fluidOutputs(Hydrogen.getFluid(8000))
                .duration(360).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .tier(3)
                .fluidInputs(Dimethylbenzene.getFluid(1000))
                .output(dust, Carbon, 8)
                .fluidOutputs(Hydrogen.getFluid(10000))
                .duration(432).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .tier(3)
                .fluidInputs(Octane.getFluid(1000))
                .output(dust, Carbon, 8)
                .fluidOutputs(Hydrogen.getFluid(18000))
                .duration(624).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .tier(3)
                .fluidInputs(Propene.getFluid(1000))
                .output(dust, Carbon, 3)
                .fluidOutputs(Hydrogen.getFluid(6000))
                .duration(144).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .tier(3)
                .fluidInputs(Ethane.getFluid(1000))
                .output(dust, Carbon, 2)
                .fluidOutputs(Hydrogen.getFluid(6000))
                .duration(128).EUt(VA[HV]).buildAndRegister();


        ELECTROBATH.recipeBuilder()
                .circuitMeta(3).duration(200).EUt(VA[HV])
                .tier(4)
                .input(dust, Uraninite, 3)
                .fluidInputs(HydrofluoricAcid.getFluid(4000))
                .fluidInputs(Fluorine.getFluid(2000))
                .fluidOutputs(UraniumHexafluoride.getFluid(1000))
                .fluidOutputs(Water.getFluid(2000))
                .buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3).duration(160).EUt(VA[EV])
                .tier(4)
                .fluidInputs(UraniumHexafluoride.getFluid(1000))
                .fluidOutputs(EnrichedUraniumHexafluoride.getFluid(100))
                .fluidOutputs(DepletedUraniumHexafluoride.getFluid(900))
                .buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3).duration(160).EUt(VA[EV])
                .tier(4)
                .fluidInputs(EnrichedUraniumHexafluoride.getFluid(1000))
                .output(dust, Uranium235)
                .fluidOutputs(Fluorine.getFluid(6000))
                .buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3).duration(160).EUt(VA[EV])
                .tier(4)
                .fluidInputs(DepletedUraniumHexafluoride.getFluid(1000))
                .output(dust, Uranium238)
                .fluidOutputs(Fluorine.getFluid(6000))
                .buildAndRegister();

        //SeparationRecipes
        // Electrolyzer
        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .input(dust, SodiumBisulfate, 7)
                .fluidOutputs(SodiumPersulfate.getFluid(500))
                .fluidOutputs(Hydrogen.getFluid(1000))
                .duration(150).EUt(VA[LV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .input(dust, Sphalerite, 2)
                .output(dust, Zinc)
                .output(dust, Sulfur)
                .chancedOutput(dust, Gallium, 500, 250)
                .duration(200).EUt(VA[LV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .inputs(new ItemStack(Items.DYE, 3))
                .output(dust, Calcium)
                .duration(96).EUt(26).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .inputs(new ItemStack(Blocks.SAND, 8))
                .output(dust, SiliconDioxide)
                .duration(500).EUt(25).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .input(dust, Diamond)
                .output(dust, Carbon, 64)
                .duration(768).EUt(VA[LV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .input(dust, Trona, 16)
                .output(dust, SodaAsh, 6)
                .output(dust, SodiumBicarbonate, 6)
                .fluidOutputs(Water.getFluid(2000))
                .duration(784).EUt(VA[LV] * 2).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .input(dust, Bauxite, 15)
                .output(dust, Aluminium, 6)
                .output(dust, Rutile)
                .fluidOutputs(Oxygen.getFluid(9000))
                .duration(270).EUt(VA[LV] * 2).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .input(dust, Zeolite, 41)
                .output(dust, Sodium)
                .output(dust, Calcium, 4)
                .output(dust, Silicon, 27)
                .output(dust, Aluminium, 9)
                .duration(656).EUt(VA[MV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .input(dust, Bentonite, 30)
                .output(dust, Sodium)
                .output(dust, Magnesium, 6)
                .output(dust, Silicon, 12)
                .fluidOutputs(Water.getFluid(5000))
                .fluidOutputs(Hydrogen.getFluid(6000))
                .duration(480).EUt(VA[MV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .circuitMeta(3)
                .input(dust, TungsticAcid, 7)
                .output(dust, Tungsten)
                .fluidOutputs(Hydrogen.getFluid(2000))
                .fluidOutputs(Oxygen.getFluid(4000))
                .duration(210).EUt(960).buildAndRegister();
    }
}
