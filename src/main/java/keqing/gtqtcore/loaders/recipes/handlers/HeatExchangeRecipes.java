package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;

import static gregtech.api.GTValues.EV;
import static gregtech.api.GTValues.V;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Oxygen;
import static keqing.gtqtcore.api.GCYSValues.MV;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

public class HeatExchangeRecipes {

    public static void init() {
        BasicHeatExchange();
        Fuels();
    }

    private static void BasicHeatExchange() {
        RecipeMaps.SEMI_FLUID_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(Lava.getFluid(100))
                .EUt(32)
                .duration(20)
                .buildAndRegister();

        //  Lava
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Lava.getFluid(1))
                .fluidOutputs(Steam.getFluid(160 * 5))
                .fluidOutputs(SuperheatedSteam.getFluid(80 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(1))
                .maxRate(1600)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        Mark1Fusion();
        Mark2Fusion();
        Mark3Fusion();
        Mark4Fusion();
        Mark5Fusion();
    }

    private static void Mark1Fusion() {
        //  Helium Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Helium.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(160 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(80 * 5))
                .fluidOutputs(Helium.getFluid(1))
                .maxRate(1600)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        //  Calcium Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Calcium.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(160 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(80 * 5))
                .fluidOutputs(Calcium.getFluid(1))
                .maxRate(1600)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        //  Boron Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Boron.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(160 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(80 * 5))
                .fluidOutputs(Boron.getFluid(1))
                .maxRate(1600)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        //  Noen Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Neon.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(160 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(80 * 5))
                .fluidOutputs(Neon.getFluid(1))
                .maxRate(1600)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();
    }

    private static void Mark2Fusion() {
        //  Nitrogen Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Nitrogen.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(320 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(160 * 5))
                .fluidOutputs(Nitrogen.getFluid(1))
                .maxRate(3200)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        //  Oxygen Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Oxygen.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(320 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(160 * 5))
                .fluidOutputs(Oxygen.getFluid(1))
                .maxRate(3200)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        //  Argon Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Argon.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(320 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(160 * 5))
                .fluidOutputs(Argon.getFluid(1))
                .maxRate(3200)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        //  Iron Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Iron.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(320 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(160 * 5))
                .fluidOutputs(Iron.getFluid(1))
                .maxRate(3200)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        //  Tin Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Tin.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(320 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(160 * 5))
                .fluidOutputs(Tin.getFluid(1))
                .maxRate(3200)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        //  Sulfur Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Sulfur.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(320 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(160 * 5))
                .fluidOutputs(Sulfur.getFluid(1))
                .maxRate(3200)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        //  Zinc Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Zinc.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(320 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(160 * 5))
                .fluidOutputs(Zinc.getFluid(1))
                .maxRate(3200)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        //  Niobium Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Niobium.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(320 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(160 * 5))
                .fluidOutputs(Niobium.getFluid(1))
                .maxRate(3200)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        //  Titanium Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Titanium.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(320 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(160 * 5))
                .fluidOutputs(Titanium.getFluid(1))
                .maxRate(3200)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        //  Krypton Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Krypton.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(320 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(160 * 5))
                .fluidOutputs(Krypton.getFluid(1))
                .maxRate(3200)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();
    }

    private static void Mark3Fusion() {
        //  Nickel Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Nickel.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(640 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(320 * 5))
                .fluidOutputs(Nickel.getFluid(1))
                .maxRate(6400)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        //  Americium Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Americium.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(640 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(320 * 5))
                .fluidOutputs(Americium.getFluid(1))
                .maxRate(6400)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        //  Silver Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Silver.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(640 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(320 * 5))
                .fluidOutputs(Silver.getFluid(1))
                .maxRate(6400)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        //  Bismuth Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Bismuth.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(640 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(320 * 5))
                .fluidOutputs(Bismuth.getFluid(1))
                .maxRate(6400)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        //  Xenon Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Xenon.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(640 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(320 * 5))
                .fluidOutputs(Xenon.getFluid(1))
                .maxRate(6400)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        //  Radon Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Radon.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(640 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(320 * 5))
                .fluidOutputs(Radon.getFluid(1))
                .maxRate(6400)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();
    }

    private static void Mark4Fusion() {
        //  Neptunium Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Neptunium.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(1280 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(640 * 5))
                .fluidOutputs(Neptunium.getFluid(1))
                .maxRate(12800)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        //  Fermium Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Fermium.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(1280 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(640 * 5))
                .fluidOutputs(Fermium.getFluid(1))
                .maxRate(12800)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

    }

    private static void Mark5Fusion() {

        //  Plutonium-241 Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Plutonium241.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(2560 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(1280 * 5))
                .fluidOutputs(Plutonium241.getFluid(1))
                .maxRate(25600)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        //  Lead Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Lead.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(2560 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(1280 * 5))
                .fluidOutputs(Lead.getFluid(1))
                .maxRate(25600)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();

        //  Thorium Plasma
        HEAT_EXCHANGE_RECIPES.recipeBuilder()
                .fluidInputs(DistilledWater.getFluid(5))
                .fluidInputs(Thorium.getPlasma(1))
                .fluidOutputs(SuperheatedSteam.getFluid(2560 * 5))
                .fluidOutputs(SupercriticalSteam.getFluid(1280 * 5))
                .fluidOutputs(Thorium.getFluid(1))
                .maxRate(25600)
                .flowRate(500)
                .duration(20)
                .buildAndRegister();
    }

    private static void Fuels() {
        //  Superheated Steam
        HIGH_PRESSURE_STEAM_TURBINE_RECIPES.recipeBuilder()
                .fluidInputs(SuperheatedSteam.getFluid(320))
                .fluidOutputs(DistilledWater.getFluid(64))
                .EUt((int) V[MV])
                .duration(10)
                .buildAndRegister();

        //  Supercritical Steam
        SUPERCRITICAL_STEAM_TURBINE_RECIPES.recipeBuilder()
                .fluidInputs(SupercriticalSteam.getFluid(640))
                .fluidOutputs(DistilledWater.getFluid(128))
                .EUt((int) V[EV])
                .duration(10)
                .buildAndRegister();
    }
}