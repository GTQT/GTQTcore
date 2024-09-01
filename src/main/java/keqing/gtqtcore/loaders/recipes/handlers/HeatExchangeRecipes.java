package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.unification.material.Material;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Oxygen;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class HeatExchangeRecipes {
    static int SECOND=20;
    public static void heatExchange(Material material, int time)
    {
        HEAT_EXCHANGE.recipeBuilder()
                .fluidInputs(material.getPlasma(1))
                .fluidOutputs(material.getFluid(1))
                .duration(time)
                .buildAndRegister();
    }
    public static void init() {
        HEAT_EXCHANGE.recipeBuilder()
                .fluidInputs(HighPressureSteam.getFluid(1))
                .fluidOutputs(SuperheatedSteam.getFluid(40))
                .duration(2)
                .buildAndRegister();

        HEAT_EXCHANGE.recipeBuilder()
                .fluidInputs(SuperheatedSteam.getFluid(1))
                .fluidOutputs(SteamExhaustGas.getFluid(40))
                .duration(1)
                .buildAndRegister();

        HEAT_EXCHANGE.recipeBuilder()
                .fluidInputs(SteamExhaustGas.getFluid(600))
                .fluidOutputs(Water.getFluid(600))
                .duration(1)
                .buildAndRegister();

        heatExchange(Oxygen,30);

        heatExchange(Nitrogen,36);
        heatExchange(Argon,48);

        heatExchange(Iron,56);
        heatExchange(Tin,64);
        heatExchange(Nickel,72);
        heatExchange(Americium,160);
        heatExchange(Calcium,4 * SECOND);
        heatExchange(Boron, (int) (3.2 * SECOND));
        heatExchange(Neon, (int) (4.5 * SECOND));
        heatExchange(Sodium, (int) (2.5 * SECOND));
        heatExchange(Sulfur, (int) (5.6 * SECOND));
        heatExchange(Zinc, (int) (4.9 * SECOND));
        heatExchange(Niobium, (int) (5.2 * SECOND));
        heatExchange(Titanium, (int) (7.6 * SECOND));
        heatExchange(Krypton, (int) (7.2 * SECOND));
        heatExchange(Rhenium, (int) (10.4 * SECOND));
        heatExchange(Silver, (int) (8.4 * SECOND));
        heatExchange(Bismuth, (int) (9.2 * SECOND));
        heatExchange(Xenon, (int) (17.4 * SECOND));
        heatExchange(Radon, (int) (18.9 * SECOND));
        heatExchange(Chrome, (int) (14.8 * SECOND));
        heatExchange(Neptunium, (int) (34.5 * SECOND));
        heatExchange(Fermium, (int) (38.7 * SECOND));
        heatExchange(Gadolinium, (int) (44.3 * SECOND));
        heatExchange(Plutonium241, (int) (47.8 * SECOND));
        heatExchange(Lead, (int) (46.4 * SECOND));
        heatExchange(Thorium, (int) (52.2 * SECOND));
        heatExchange(Germanium, (int) (61.2 * SECOND));

        //  Exhaust gas heat exchange
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(10))
                .fluidInputs(HighPressureSteam.getFluid(2))
                .fluidOutputs(Steam.getFluid(160 * 10))
                .fluidOutputs(SuperheatedSteam.getFluid(80 * 10))
                .fluidOutputs(SteamExhaustGas.getFluid(2))
                .maxRate(3200)
                .flowRate(1000)
                .duration(1)
                .buildAndRegister();

        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(10))
                .fluidInputs(SuperheatedSteam.getFluid(20))
                .fluidOutputs(Steam.getFluid(160 * 10))
                .fluidOutputs(Steam.getFluid(80 * 10))
                .fluidOutputs(SteamExhaustGas.getFluid(20))
                .maxRate(3200)
                .flowRate(1000)
                .duration(1)
                .buildAndRegister();

        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(10))
                .fluidInputs(SteamExhaustGas.getFluid(200))
                .fluidOutputs(Steam.getFluid(160 * 10))
                .fluidOutputs(Steam.getFluid(80 * 10))
                .fluidOutputs(Steam.getFluid(20))
                .maxRate(3200)
                .flowRate(1000)
                .duration(1)
                .buildAndRegister();

    }
}
