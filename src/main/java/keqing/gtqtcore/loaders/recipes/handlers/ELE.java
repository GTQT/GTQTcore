package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtcore.api.unification.ore.GTQTOrePrefix;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTElectrobath;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.ore.OrePrefix.plate;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.ELECTROLYZER;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.ELECTROBATH;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.HEAT_EXCHANGE_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.electrode;
import static keqing.gtqtcore.common.block.blocks.GTQTStepper.CasingType.*;

public class ELE {
    public static void init() {
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .EUt(120).duration(800)
                .input(plate, Platinum, 8)
                .input(stick, Platinum, 4)
                .input(dust,Graphite,16)
                .output(electrode,Platinum, 1)
                .circuitMeta(21)
                .fluidInputs(Asphalt.getFluid(2000))
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .EUt(120).duration(800)
                .input(plate, Palladium, 8)
                .input(stick, Palladium, 4)
                .input(dust,Graphite,16)
                .output(electrode,Palladium, 1)
                .circuitMeta(21)
                .fluidInputs(Asphalt.getFluid(2000))
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .EUt(120).duration(800)
                .input(plate, Silver, 8)
                .input(stick, Silver, 4)
                .input(dust,Graphite,16)
                .output(electrode,Silver, 1)
                .circuitMeta(21)
                .fluidInputs(Asphalt.getFluid(2000))
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .EUt(120).duration(800)
                .input(plate, Copper, 8)
                .input(stick, Copper, 4)
                .input(dust,Graphite,16)
                .output(electrode,Copper, 1)
                .circuitMeta(21)
                .fluidInputs(Asphalt.getFluid(2000))
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .EUt(120).duration(800)
                .input(plate, Aluminium, 8)
                .input(stick, Aluminium, 4)
                .input(dust,Graphite,16)
                .output(electrode,Aluminium, 1)
                .circuitMeta(21)
                .fluidInputs(Asphalt.getFluid(2000))
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .EUt(120).duration(800)
                .input(plate, Zinc, 8)
                .input(stick, Zinc, 4)
                .input(dust,Graphite,16)
                .output(electrode,Zinc, 1)
                .circuitMeta(21)
                .fluidInputs(Asphalt.getFluid(2000))
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .EUt(120).duration(800)
                .input(plate, Steel, 8)
                .input(stick, Steel, 4)
                .input(dust,Graphite,16)
                .output(electrode,Steel, 1)
                .circuitMeta(21)
                .fluidInputs(Asphalt.getFluid(2000))
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .EUt(120).duration(800)
                .input(plate, Brass, 8)
                .input(stick, Brass, 4)
                .input(dust,Graphite,16)
                .output(electrode,Brass, 1)
                .circuitMeta(21)
                .fluidInputs(Asphalt.getFluid(2000))
                .buildAndRegister();

        //钻头
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[1].getStackForm())
                .input(ELECTRIC_MOTOR_LV, 4)
                .input(plate,Steel, 8)
                .input(frameGt,Steel, 8)
                .fluidInputs(Polyethylene.getFluid(L * 4))
                .circuitMeta(5)
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.DRILL_HEAD_LV))
                .duration(2000).EUt(30).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[2].getStackForm())
                .input(ELECTRIC_MOTOR_MV, 4)
                .input(plate,Aluminium, 8)
                .input(frameGt,Aluminium, 8)
                .fluidInputs(PolyvinylChloride.getFluid(L * 4))
                .circuitMeta(5)
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.DRILL_HEAD_MV))
                .duration(200).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[3].getStackForm())
                .input(ELECTRIC_MOTOR_HV, 4)
                .input(plate,StainlessSteel, 8)
                .input(frameGt,StainlessSteel, 8)
                .fluidInputs(Epoxy.getFluid(L * 4))
                .circuitMeta(5)
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.DRILL_HEAD_HV))
                .duration(200).EUt(480).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[4].getStackForm())
                .input(ELECTRIC_MOTOR_EV, 4)
                .input(plate,Titanium, 8)
                .input(frameGt,Titanium, 8)
                .fluidInputs(ReinforcedEpoxyResin.getFluid(L * 4))
                .circuitMeta(5)
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.DRILL_HEAD_EV))
                .duration(200).EUt(1960).buildAndRegister();


        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[1].getStackForm())
                .input(ELECTRIC_MOTOR_LV, 8)
                .input(circuit, MarkerMaterials.Tier.LV,1)
                .input(electrode,Graphite, 8)
                .fluidInputs(Polyethylene.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.I_ELECTROBATH))
                .duration(2000).EUt(30).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[2].getStackForm())
                .input(ELECTRIC_MOTOR_MV, 8)
                .input(circuit, MarkerMaterials.Tier.MV,1)
                .input(electrode, Silver, 8)
                .fluidInputs(PolyvinylChloride.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.II_ELECTROBATH))
                .duration(200).EUt(120).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[3].getStackForm())
                .input(ELECTRIC_MOTOR_HV, 8)
                .input(circuit, MarkerMaterials.Tier.HV,1)
                .input(electrode, Aluminium, 8)
                .fluidInputs(Epoxy.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.III_ELECTROBATH))
                .duration(200).EUt(480).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[4].getStackForm())
                .input(ELECTRIC_MOTOR_EV, 8)
                .input(circuit, MarkerMaterials.Tier.EV,1)
                .input(electrode, Platinum, 8)
                .fluidInputs(ReinforcedEpoxyResin.getFluid(L * 4))
                .outputs(GTQTMetaBlocks.ELECTROBATH.getItemVariant(GTQTElectrobath.CasingType.IV_ELECTROBATH))
                .duration(200).EUt(1960).buildAndRegister();
        //主方块
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(HULL[1].getStackForm(4))
                .input(electrode, Graphite, 16)
                .input(circuit, MarkerMaterials.Tier.LV,16)
                .input(wireFine,Copper,16)
                .input(ELECTROLYZER[1],4)
                .fluidInputs(Tin.getFluid(L * 4))
                .outputs(GTQTMetaTileEntities.ELECTROBATH.getStackForm())
                .duration(200).EUt(30).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Steel,1)
                .fluidInputs(DistilledWater.getFluid(4000))
                .fluidOutputs(Hydrogen.getFluid(8000))
                .fluidOutputs(Oxygen.getFluid(4000))
                .EUt(180)
                .tier(1)
                .duration(800)
                .buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Steel,1)
                .fluidInputs(Water.getFluid(4000))
                .fluidOutputs(Hydrogen.getFluid(8000))
                .fluidOutputs(Oxygen.getFluid(4000))
                .EUt(240)
                .tier(2)
                .duration(800)
                .buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Silver,1)
                .fluidInputs(SaltWater.getFluid(4000))
                .output(dust,SodiumHydroxide,12)
                .fluidOutputs(Chlorine.getFluid(4000))
                .fluidOutputs(Hydrogen.getFluid(4000))
                .EUt(240)
                .tier(2)
                .duration(200)
                .buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Steel,1)
                .tier(3)
                .input(dust, SodiumHydroxide, 3)
                .output(dust, Sodium)
                .fluidOutputs(Oxygen.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(1000))
                .duration(150).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Graphite,1)
                .tier(3)
                .input(dust, Sugar, 10)
                .output(dust, Carbon,6)
                .fluidOutputs(Oxygen.getFluid(6000))
                .fluidOutputs(Hydrogen.getFluid(12000))
                .duration(64).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Graphite,1)
                .tier(3)
                .input(dust, Apatite, 9)
                .output(dust, Calcium, 5)
                .output(dust, Phosphorus, 3)
                .fluidOutputs(Chlorine.getFluid(1000))
                .duration(288).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Graphite,1)
                .tier(3)
                .fluidInputs(Propane.getFluid(1000))
                .output(dust, Carbon, 3)
                .fluidOutputs(Hydrogen.getFluid(8000))
                .duration(176).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Graphite,1)
                .tier(3)
                .fluidInputs(Butene.getFluid(1000))
                .output(dust, Carbon, 4)
                .fluidOutputs(Hydrogen.getFluid(8000))
                .duration(192).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Graphite,1)
                .tier(3)
                .fluidInputs(Butane.getFluid(1000))
                .output(dust, Carbon, 4)
                .fluidOutputs(Hydrogen.getFluid(10000))
                .duration(224).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Aluminium,1)
                .tier(3)
                .fluidInputs(Styrene.getFluid(1000))
                .output(dust, Carbon, 8)
                .fluidOutputs(Hydrogen.getFluid(8000))
                .duration(384).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Aluminium,1)
                .tier(3)
                .fluidInputs(Butadiene.getFluid(1000))
                .output(dust, Carbon, 4)
                .fluidOutputs(Hydrogen.getFluid(6000))
                .duration(240).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Aluminium,1)
                .tier(3)
                .fluidInputs(Phenol.getFluid(1000))
                .output(dust, Carbon, 6)
                .fluidOutputs(Hydrogen.getFluid(6000))
                .fluidOutputs(Oxygen.getFluid(1000))
                .duration(312).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Aluminium,1)
                .tier(3)
                .fluidInputs(Ethylene.getFluid(1000))
                .output(dust, Carbon, 2)
                .fluidOutputs(Hydrogen.getFluid(4000))
                .duration(96).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Zinc,1)
                .tier(3)
                .fluidInputs(Benzene.getFluid(1000))
                .output(dust, Carbon, 6)
                .fluidOutputs(Hydrogen.getFluid(6000))
                .duration(288).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Zinc,1)
                .tier(3)
                .fluidInputs(Ethanol.getFluid(1000))
                .output(dust, Carbon, 2)
                .fluidOutputs(Hydrogen.getFluid(6000))
                .fluidOutputs(Oxygen.getFluid(1000))
                .duration(144).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Zinc,1)
                .tier(3)
                .fluidInputs(Toluene.getFluid(1000))
                .output(dust, Carbon, 7)
                .fluidOutputs(Hydrogen.getFluid(8000))
                .duration(360).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Zinc,1)
                .tier(3)
                .fluidInputs(Dimethylbenzene.getFluid(1000))
                .output(dust, Carbon, 8)
                .fluidOutputs(Hydrogen.getFluid(10000))
                .duration(432).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Brass,1)
                .tier(3)
                .fluidInputs(Octane.getFluid(1000))
                .output(dust, Carbon, 8)
                .fluidOutputs(Hydrogen.getFluid(18000))
                .duration(624).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Brass,1)
                .tier(3)
                .fluidInputs(Propene.getFluid(1000))
                .output(dust, Carbon, 3)
                .fluidOutputs(Hydrogen.getFluid(6000))
                .duration(144).EUt(VA[HV]).buildAndRegister();

        ELECTROBATH.recipeBuilder()
                .notConsumable(electrode,Brass,1)
                .tier(3)
                .fluidInputs(Ethane.getFluid(1000))
                .output(dust, Carbon, 2)
                .fluidOutputs(Hydrogen.getFluid(6000))
                .duration(128).EUt(VA[HV]).buildAndRegister();


        ELECTROBATH.recipeBuilder().duration(200).EUt(VA[HV])
                .notConsumable(electrode,Palladium,1)
                .tier(4)
                .input(dust, Uraninite, 3)
                .fluidInputs(HydrofluoricAcid.getFluid(4000))
                .fluidInputs(Fluorine.getFluid(2000))
                .fluidOutputs(UraniumHexafluoride.getFluid(1000))
                .fluidOutputs(Water.getFluid(2000))
                .buildAndRegister();

        ELECTROBATH.recipeBuilder().duration(160).EUt(VA[EV])
                .notConsumable(electrode,Palladium,1)
                .tier(4)
                .fluidInputs(UraniumHexafluoride.getFluid(1000))
                .fluidOutputs(EnrichedUraniumHexafluoride.getFluid(100))
                .fluidOutputs(DepletedUraniumHexafluoride.getFluid(900))
                .buildAndRegister();

        ELECTROBATH.recipeBuilder().duration(160).EUt(VA[EV])
                .notConsumable(electrode,Palladium,1)
                .tier(4)
                .fluidInputs(EnrichedUraniumHexafluoride.getFluid(1000))
                .output(dust, Uranium235)
                .fluidOutputs(Fluorine.getFluid(6000))
                .buildAndRegister();

        ELECTROBATH.recipeBuilder().duration(160).EUt(VA[EV])
                .notConsumable(electrode,Palladium,1)
                .tier(4)
                .fluidInputs(DepletedUraniumHexafluoride.getFluid(1000))
                .output(dust, Uranium238)
                .fluidOutputs(Fluorine.getFluid(6000))
                .buildAndRegister();
    }
}
