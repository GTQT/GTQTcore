package keqing.gtqtcore.loaders.recipes;

import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.common.ConfigHolder;
import gregtech.loaders.recipe.BatteryRecipes;
import gregtech.loaders.recipe.handlers.OreRecipeHandler;
import keqing.gtqtcore.api.unification.ore.GTQTOrePrefix;

public class GTQTOreRecipeHandler {

    public static void init(){
        if (ConfigHolder.worldgen.allUniqueStoneTypes) {
            GTQTOrePrefix.oreGabbro.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
            GTQTOrePrefix.oreGneiss.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
            GTQTOrePrefix.oreLimestone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
            GTQTOrePrefix.orePhyllite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
            GTQTOrePrefix.oreQuartzite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
            GTQTOrePrefix.oreShale.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
            GTQTOrePrefix.oreSlate.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
            GTQTOrePrefix.oreSoapstone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
            GTQTOrePrefix.oreKimberlite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        }
    }
}