package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.GregTechAPI;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.api.recipes.ingredients.GTRecipeOreInput;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.registry.MaterialRegistry;
import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.ore;


public class IntegratedMiningDivision {
    public static void init() {
        addStaticRecipes();
    }

    private static void addStaticRecipes() {
        for (MaterialRegistry materialRegistry : GregTechAPI.materialManager.getRegistries())
        {
            for (Material material : materialRegistry) {
                if (material.hasProperty(PropertyKey.ORE)) {
                    addIntegratedMiningRecipe1(material, 3)
                            .EUt(7680).duration(20)
                            .buildAndRegister();
                    addIntegratedMiningRecipe9(material, 3)
                            .EUt(7680).duration(20)
                            .buildAndRegister();
                    addIntegratedMiningRecipe2(material, 3)
                            .EUt(7680).duration(20)
                            .buildAndRegister();
                    addIntegratedMiningRecipe3(material, 1)
                            .EUt(7680).duration(20)
                            .buildAndRegister();
                    addIntegratedMiningRecipe4(material, 1)
                            .EUt(30720).duration(20)
                            .buildAndRegister();
                    addIntegratedMiningRecipe5(material, 1)
                            .EUt(30720).duration(20)
                            .buildAndRegister();
                    addIntegratedMiningRecipe6(material, 1)
                            .EUt(30720).duration(20)
                            .buildAndRegister();
                    addIntegratedMiningRecipe7(material, 2)
                            .EUt(30720).duration(20)
                            .buildAndRegister();
                    addIntegratedMiningRecipe8(material, 2)
                            .EUt(30720).duration(20)
                            .buildAndRegister();
                }
            }
        }
    }

    private static SimpleRecipeBuilder addIntegratedMiningRecipe1(Material material, int output) {
        return GTQTcoreRecipeMaps.INTEGRATED_MINING_DIVISION.recipeBuilder()
                .input(OrePrefix.ore, material)
                .fluidInputs(Water.getFluid(1000))
                .circuitMeta(1)
                .output(OrePrefix.dust, material, output);
    }
    private static SimpleRecipeBuilder addIntegratedMiningRecipe2(Material material, int output) {
        return GTQTcoreRecipeMaps.INTEGRATED_MINING_DIVISION.recipeBuilder()
                .input(OrePrefix.oreNetherrack, material)
                .fluidInputs(Water.getFluid(1000))
                .circuitMeta(1)
                .output(OrePrefix.dust, material, output);
    }

    private static SimpleRecipeBuilder addIntegratedMiningRecipe3(Material material, int output) {
        return GTQTcoreRecipeMaps.INTEGRATED_MINING_DIVISION.recipeBuilder()
                .input(OrePrefix.crushed, material)
                .fluidInputs(Water.getFluid(500))
                .circuitMeta(1)
                .output(OrePrefix.dustImpure, material, output);
    }
    private static SimpleRecipeBuilder addIntegratedMiningRecipe4(Material material, int output) {
        return GTQTcoreRecipeMaps.INTEGRATED_MINING_DIVISION.recipeBuilder()
                .input(OrePrefix.crushed, material)
                .circuitMeta(3)
                .output(OrePrefix.crushedCentrifuged, material, output);
    }
    private static SimpleRecipeBuilder addIntegratedMiningRecipe5(Material material, int output) {
        return GTQTcoreRecipeMaps.INTEGRATED_MINING_DIVISION.recipeBuilder()
                .input(OrePrefix.dustImpure, material)
                .circuitMeta(1)
                .output(OrePrefix.dust, material, output);
    }
    private static SimpleRecipeBuilder addIntegratedMiningRecipe6(Material material, int output) {
        return GTQTcoreRecipeMaps.INTEGRATED_MINING_DIVISION.recipeBuilder()
                .input(OrePrefix.crushedCentrifuged, material)
                .circuitMeta(1)
                .output(OrePrefix.dust, material, output);
    }
    private static SimpleRecipeBuilder addIntegratedMiningRecipe7(Material material, int output) {
        return GTQTcoreRecipeMaps.INTEGRATED_MINING_DIVISION.recipeBuilder()
                .input(ore, material)
                .fluidInputs(Water.getFluid(2000))
                .circuitMeta(2)
                .output(OrePrefix.crushedPurified, material, output);

    }
    private static SimpleRecipeBuilder addIntegratedMiningRecipe8(Material material, int output) {
        return GTQTcoreRecipeMaps.INTEGRATED_MINING_DIVISION.recipeBuilder()
                .input(OrePrefix.crushedPurified, material)
                .circuitMeta(1)
                .output(OrePrefix.crushedCentrifuged, material, output);
    }
    private static SimpleRecipeBuilder addIntegratedMiningRecipe9(Material material, int output) {
        return GTQTcoreRecipeMaps.INTEGRATED_MINING_DIVISION.recipeBuilder()
                .input(OrePrefix.oreEndstone, material)
                .fluidInputs(Water.getFluid(1000))
                .circuitMeta(1)
                .output(OrePrefix.dust, material, output);
    }
}