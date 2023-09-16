package keqing.gtqtcore.loaders.recipes;



import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;

public class FuelRecipes {

        public static void init(){
        GTQTcoreRecipeMaps.ROCKET.recipeBuilder()
                .fluidInputs(Water.getFluid(1))
                .fluidOutputs(Water.getFluid(1))
                .duration(10)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Naphtha.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(20))
                .duration(10)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(SulfuricLightFuel.getFluid(4))
                .fluidOutputs(HighPressureSteam.getFluid(20))
                .duration(5)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Methanol.getFluid(4))
                .fluidOutputs(HighPressureSteam.getFluid(20))
                .duration(8)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Ethanol.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(20))
                .duration(6)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Octane.getFluid(2))
                .fluidOutputs(HighPressureSteam.getFluid(20))
                .duration(5)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(BioDiesel.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(20))
                .duration(8)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(LightFuel.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(20))
                .duration(10)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Diesel.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(20))
                .duration(15)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(CetaneBoostedDiesel.getFluid(2))
                .fluidOutputs(HighPressureSteam.getFluid(20))
                .duration(45)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(RocketFuel.getFluid(16))
                .fluidOutputs(HighPressureSteam.getFluid(20))
                .duration(125)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Gasoline.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(20))
                .duration(50)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(HighOctaneGasoline.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(20))
                .duration(100)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(Toluene.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(20))
                .duration(10)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(OilLight.getFluid(32))
                .fluidOutputs(HighPressureSteam.getFluid(20))
                .duration(5)
                .EUt((int) V[LV])
                .buildAndRegister();

        GTQTcoreRecipeMaps.TURBINE_COMBUSTION_CHAMBER.recipeBuilder()
                .fluidInputs(RawOil.getFluid(64))
                .fluidOutputs(HighPressureSteam.getFluid(20))
                .duration(15)
                .EUt((int) V[LV])
                .buildAndRegister();




    }
}
