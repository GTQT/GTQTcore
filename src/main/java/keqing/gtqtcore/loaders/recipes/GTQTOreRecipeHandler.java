package keqing.gtqtcore.loaders.recipes;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.common.ConfigHolder;
import gregtech.loaders.recipe.BatteryRecipes;
import gregtech.loaders.recipe.handlers.OreRecipeHandler;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.api.unification.ore.GTQTOrePrefix;

public class GTQTOreRecipeHandler {

    public static void init(){
            GTQTOrePrefix.oreGabbro.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
            GTQTOrePrefix.oreGneiss.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
            GTQTOrePrefix.oreLimestone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
            GTQTOrePrefix.orePhyllite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
            GTQTOrePrefix.oreQuartzite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
            GTQTOrePrefix.oreShale.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
            GTQTOrePrefix.oreSlate.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
            GTQTOrePrefix.oreSoapstone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
            GTQTOrePrefix.oreKimberlite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);

            GTQTOrePrefix.oreGabbro.addSecondaryMaterial(new MaterialStack(GTQTMaterials.Gabbro, 144));
            GTQTOrePrefix.oreGneiss.addSecondaryMaterial(new MaterialStack(GTQTMaterials.Gneiss, 144));
            GTQTOrePrefix.oreLimestone.addSecondaryMaterial(new MaterialStack(GTQTMaterials.Limestone, 144));
            GTQTOrePrefix.orePhyllite.addSecondaryMaterial(new MaterialStack(GTQTMaterials.Phyllite, 144));
            GTQTOrePrefix.oreQuartzite.addSecondaryMaterial(new MaterialStack(Materials.Quartzite, 144));
            GTQTOrePrefix.oreShale.addSecondaryMaterial(new MaterialStack(GTQTMaterials.Shale, 144));
            GTQTOrePrefix.oreSlate.addSecondaryMaterial(new MaterialStack(GTQTMaterials.Slate, 144));
            GTQTOrePrefix.oreSoapstone.addSecondaryMaterial(new MaterialStack(Materials.Soapstone, 144));
            GTQTOrePrefix.oreKimberlite.addSecondaryMaterial(new MaterialStack(GTQTMaterials.Kimberlite, 144));
    }
}