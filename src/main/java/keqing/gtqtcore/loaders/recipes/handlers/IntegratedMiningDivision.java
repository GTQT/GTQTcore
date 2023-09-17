package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.api.recipes.ingredients.GTRecipeOreInput;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
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

        GTQTcoreRecipeMaps.INTEGRATED_MINING_DIVISION.recipeBuilder()
                .fluidInputs(Naphtha.getFluid(1))
                .fluidOutputs(HighPressureSteam.getFluid(20))
                .duration(10)
                .EUt((int) V[LV])
                .buildAndRegister();


    }

    private static void addStaticRecipes() {
        for(var field : allMaterials()){
            if(Modifier.isStatic(field.getModifiers())){
                try {
                    var obj = field.get(null);
                    if(obj instanceof Material material
                    ){
                        if (material.hasProperty(PropertyKey.ORE)) {
                            addIntegratedMiningRecipe(material, 64)
                                    .buildAndRegister();
                        }
                    }
                }   catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static Collection<Field> allMaterials(){
        var classList = new ArrayList<Class<?>>();
        var event = new SearchMaterialsClassEvent(classList);
        if(!MinecraftForge.EVENT_BUS.post(event)){
            return event.getClassList().stream().flatMap(
                    (clazz) -> Arrays.stream(clazz.getFields())).collect(Collectors.toList());
        }
        return Collections.emptySet();
    }

    private static SimpleRecipeBuilder addIntegratedMiningRecipe(Material material, int output) {
        return GTQTcoreRecipeMaps.INTEGRATED_MINING_DIVISION.recipeBuilder()
                .input(OrePrefix.ore, material)
                .output(OrePrefix.ingot, material, output);
    }

}