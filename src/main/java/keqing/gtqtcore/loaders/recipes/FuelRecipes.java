package keqing.gtqtcore.loaders.recipes;

import static gregicality.science.api.unification.materials.GCYSMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.loaders.OreDictionaryLoader.OREDICT_BLOCK_FUEL_COKE;
import static gregtech.loaders.OreDictionaryLoader.OREDICT_FUEL_COKE;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;

public class FuelRecipes {

        public static void init(){


                GTQTcoreRecipeMaps.STAR_MIXER.recipeBuilder()
                        .fluidInputs(Oxygen.getPlasma(1000))
                        .fluidInputs(Nitrogen.getPlasma(1000))
                        .fluidInputs(Helium.getPlasma(1000))
                        .fluidOutputs(StellarMaterialResidueA.getFluid(1000))
                        .duration(18000)
                        .EUt(7864320)
                        .circuitMeta(1)
                        .buildAndRegister();

                GTQTcoreRecipeMaps.STAR_MIXER.recipeBuilder()
                        .fluidInputs(Oxygen.getPlasma(1000))
                        .fluidInputs(Nitrogen.getPlasma(1000))
                        .fluidInputs(Helium.getPlasma(1000))
                        .fluidInputs(Iron.getPlasma(1000))
                        .fluidInputs(Nickel.getPlasma(1000))
                        .fluidInputs(Argon.getPlasma(1000))
                        .fluidOutputs(StellarMaterialResidueB.getFluid(1000))
                        .duration(36000)
                        .EUt(7864320)
                        .circuitMeta(2)
                        .buildAndRegister();

                GTQTcoreRecipeMaps.STAR_MIXER.recipeBuilder()
                        .fluidInputs(Oxygen.getPlasma(1000))
                        .fluidInputs(Nitrogen.getPlasma(1000))
                        .fluidInputs(Helium.getPlasma(1000))
                        .fluidInputs(Iron.getPlasma(1000))
                        .fluidInputs(Nickel.getPlasma(1000))
                        .fluidInputs(Argon.getPlasma(1000))
                        .fluidInputs(Adamantium.getPlasma(1000))
                        .fluidInputs(Vibranium.getPlasma(1000))
                        .fluidInputs(StellarMaterial.getPlasma(1000))
                        .fluidOutputs(StellarMaterialResidueC.getFluid(1000))
                        .duration(72000)
                        .EUt(7864320)
                        .circuitMeta(3)
                        .buildAndRegister();

        GTQTcoreRecipeMaps.QFT.recipeBuilder()
                .fluidInputs(Hydrogen.getFluid(26000))
                .fluidInputs(Oxygen.getFluid(2000))
                .fluidInputs(Fluorine.getFluid(4000))
                .fluidInputs(Nitrogen.getFluid(6000))
                .fluidInputs(Carbon.getFluid(38000))
                .fluidOutputs(Polyethylene.getFluid(1000))
                .fluidOutputs(Polytetrafluoroethylene.getFluid(1000))
                .fluidOutputs(Polybenzimidazole.getFluid(1000))
                .fluidOutputs(Kevlar.getFluid(1000))
                .duration(360)
                .EUt(122880)
                .buildAndRegister();

        GTQTcoreRecipeMaps.STEAM_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(ingot, WroughtIron)
                .output(ingot, Steel)
                .duration(3600)
                .EUt(24)
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Naphtha.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(200))
                .duration(10)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(SulfuricLightFuel.getFluid(4))
                .fluidOutputs(HighPressureSteam.getFluid(100))
                .duration(5)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Methanol.getFluid(4))
                .fluidOutputs(HighPressureSteam.getFluid(160))
                .duration(8)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Ethanol.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(120))
                .duration(6)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Octane.getFluid(2))
                .fluidOutputs(HighPressureSteam.getFluid(100))
                .duration(5)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(BioDiesel.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(160))
                .duration(8)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(LightFuel.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(200))
                .duration(10)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Diesel.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(300))
                .duration(15)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(CetaneBoostedDiesel.getFluid(2))
                .fluidOutputs(HighPressureSteam.getFluid(900))
                .duration(45)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(RocketFuel.getFluid(16))
                .fluidOutputs(HighPressureSteam.getFluid(2500))
                .duration(125)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Gasoline.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(1000))
                .duration(50)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(HighOctaneGasoline.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(2000))
                .duration(100)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Toluene.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(2000))
                .duration(10)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(OilLight.getFluid(32))
                .fluidOutputs(HighPressureSteam.getFluid(100))
                .duration(5)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(RawOil.getFluid(64))
                .fluidOutputs(HighPressureSteam.getFluid(300))
                .duration(15)
                .EUt((int) V[LV])
                .buildAndRegister();




    }
}
