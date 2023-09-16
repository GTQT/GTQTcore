package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.recipes.ingredients.GTRecipeOreInput;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtcore.api.event.SearchMaterialsClassEvent;


import java.lang.reflect.Modifier;
import gregtech.api.recipes.RecipeMaps;

import net.minecraftforge.common.MinecraftForge;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;



public class IntegratedMiningDivision {
    public static void init() {


        for(var field : allMaterials()){
            if(Modifier.isStatic(field.getModifiers())){
                try {
                    var obj = field.get(null);
                    if(obj instanceof Material material
                    ){

                        var ingot = GTRecipeOreInput.getOrCreate(OrePrefix.ingot,material).getInputStacks();
                        var plate = GTRecipeOreInput.getOrCreate(OrePrefix.plate,material).getInputStacks();
                        var foil = GTRecipeOreInput.getOrCreate(OrePrefix.foil,material).getInputStacks();

                        if(ingot.length>0 && plate.length>0 && foil.length>0){
                            RecipeMaps.BENDER_RECIPES.recipeBuilder()
                                    .EUt(24)
                                    .input(OrePrefix.ingot,material)
                                    .output(OrePrefix.foil,material,4)
                                    .duration(Math.max((int)(material.getMass()*2.1),1))
                                    .circuitMeta(8)
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

}
