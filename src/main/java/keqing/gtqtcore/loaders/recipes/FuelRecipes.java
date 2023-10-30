package keqing.gtqtcore.loaders.recipes;



import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.loaders.OreDictionaryLoader.OREDICT_BLOCK_FUEL_COKE;
import static gregtech.loaders.OreDictionaryLoader.OREDICT_FUEL_COKE;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;

public class FuelRecipes {

        public static void init(){

        GTQTcoreRecipeMaps.QFT.recipeBuilder()
                .input(ingot, WroughtIron)
                .output(ingot, Steel)
                .duration(3600)
                .EUt(24)
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
