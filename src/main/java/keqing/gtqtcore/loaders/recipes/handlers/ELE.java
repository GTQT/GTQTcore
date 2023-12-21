package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtcore.api.unification.ore.GTQTOrePrefix;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.cableGtHex;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.ELECTROBATH;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.HEAT_EXCHANGE_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.electrode;

public class ELE {
    public static void init() {

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
