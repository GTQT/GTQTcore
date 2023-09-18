package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.GregTechAPI;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.api.recipes.ingredients.GTRecipeOreInput;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.registry.MaterialRegistry;
import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtcore.api.event.SearchMaterialsClassEvent;


import java.lang.reflect.Modifier;

import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import net.minecraftforge.common.MinecraftForge;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import static gregtech.api.GTValues.LV;
import static gregtech.api.GTValues.V;
import static gregtech.api.unification.material.Materials.Naphtha;
import static keqing.gtqtcore.api.unification.GTQTMaterials.HighPressureSteam;


public class IntegratedMiningDivision {
    public static void init() {
        addStaticRecipes();

        GTQTcoreRecipeMaps.INTEGRATED_MINING_DIVISION.recipeBuilder()
                .fluidInputs(Naphtha.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(20))
                .duration(10)
                .EUt((int) V[LV])
                .buildAndRegister();
    }

    private static void addStaticRecipes() {
        for (MaterialRegistry materialRegistry : GregTechAPI.materialManager.getRegistries())
        {
            for (Material material : materialRegistry) {
                var ingot = GTRecipeOreInput.getOrCreate(OrePrefix.ingot,material).getInputStacks();
                var plate = GTRecipeOreInput.getOrCreate(OrePrefix.plate,material).getInputStacks();
                var foil = GTRecipeOreInput.getOrCreate(OrePrefix.foil,material).getInputStacks();

                if(ingot.length>0 && plate.length>0 && foil.length>0){
                    GTQTcoreRecipeMaps.INTEGRATED_MINING_DIVISION.recipeBuilder()
                            .EUt(24)
                            .input(OrePrefix.ingot,material)
                            .output(OrePrefix.foil,material,4)
                            .duration(Math.max((int)(material.getMass()*2.1),1))
                            .buildAndRegister();
                }

                if (material.hasProperty(PropertyKey.ORE)) {
                    addIntegratedMiningRecipe(material, 64)
                            .EUt(128).duration(128)
                            .buildAndRegister();
                }
            }
        }
    }

    private static SimpleRecipeBuilder addIntegratedMiningRecipe(Material material, int output) {
        return GTQTcoreRecipeMaps.INTEGRATED_MINING_DIVISION.recipeBuilder()
                .input(OrePrefix.ore, material)
                .output(OrePrefix.dust, material, output);
    }

}
