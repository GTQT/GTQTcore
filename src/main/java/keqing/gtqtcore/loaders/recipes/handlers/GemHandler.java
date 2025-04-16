package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.GemProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtcore.api.unification.ore.GTQTOrePrefix;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.POLISHER_RECIPES;
import static keqing.gtqtcore.api.utils.GTQTUniversUtil.MINUTE;

public class GemHandler {
    public static void init() {
        OrePrefix.lens.addProcessingHandler(PropertyKey.GEM, GemHandler::processGem);
    }
    private static void processGem(OrePrefix gemPrefix, Material material, GemProperty property)
    {
        if (!(OreDictUnifier.get(OrePrefix.plate, material).isEmpty()))
        {
            POLISHER_RECIPES.recipeBuilder()
                    .input(OrePrefix.plate, material)
                    .output(gemPrefix, material)
                    .output(OrePrefix.dustSmall, material)
                    .fluidInputs(Materials.Water.getFluid(1000))
                    .EUt(120)
                    .duration(MINUTE)
                    .buildAndRegister();

            POLISHER_RECIPES.recipeBuilder()
                    .input(OrePrefix.plate, material)
                    .output(gemPrefix, material)
                    .output(OrePrefix.dustSmall, material)
                    .fluidInputs(Materials.DistilledWater.getFluid(200))
                    .EUt(120)
                    .duration(MINUTE)
                    .buildAndRegister();

            POLISHER_RECIPES.recipeBuilder()
                    .input(OrePrefix.plate, material)
                    .output(gemPrefix, material)
                    .output(OrePrefix.dustSmall, material)
                    .fluidInputs(Materials.Lubricant.getFluid(10))
                    .EUt(120)
                    .duration(MINUTE)
                    .buildAndRegister();
        }
        // gemExquisiteX -> craftingLensX.
        if (!(OreDictUnifier.get(OrePrefix.gemExquisite, material).isEmpty()))
        {
            POLISHER_RECIPES.recipeBuilder()
                    .input(OrePrefix.gemExquisite, material)
                    .output(gemPrefix, material)
                    .output(OrePrefix.dust, material, 2)
                    .fluidInputs(Materials.Water.getFluid(1000))
                    .EUt(30)
                    .duration(2 * MINUTE)
                    .buildAndRegister();

            POLISHER_RECIPES.recipeBuilder()
                    .input(OrePrefix.gemExquisite, material)
                    .output(gemPrefix, material)
                    .output(OrePrefix.dust, material, 2)
                    .fluidInputs(Materials.DistilledWater.getFluid(200))
                    .EUt(30)
                    .duration(2 * MINUTE)
                    .buildAndRegister();

            POLISHER_RECIPES.recipeBuilder()
                    .input(OrePrefix.gemExquisite, material)
                    .output(gemPrefix, material)
                    .output(OrePrefix.dust, material, 2)
                    .fluidInputs(Materials.Lubricant.getFluid(10))
                    .EUt(30)
                    .duration(2 * MINUTE)
                    .buildAndRegister();
        }
        // gemSolitaryX -> craftingLensX.
        if (!(OreDictUnifier.get(GTQTOrePrefix.gemSolitary, material).isEmpty()))
        {
            POLISHER_RECIPES.recipeBuilder()
                    .input(GTQTOrePrefix.gemSolitary, material)
                    .output(gemPrefix, material, 2)
                    .output(OrePrefix.dust, material, 4)
                    .fluidInputs(Materials.Water.getFluid(1000))
                    .EUt(120)
                    .duration(MINUTE)
                    .buildAndRegister();

            POLISHER_RECIPES.recipeBuilder()
                    .input(GTQTOrePrefix.gemSolitary, material)
                    .output(gemPrefix, material, 2)
                    .output(OrePrefix.dust, material, 4)
                    .fluidInputs(Materials.DistilledWater.getFluid(200))
                    .EUt(120)
                    .duration(MINUTE)
                    .buildAndRegister();

            POLISHER_RECIPES.recipeBuilder()
                    .input(GTQTOrePrefix.gemSolitary, material)
                    .output(gemPrefix, material, 2)
                    .output(OrePrefix.dust, material, 4)
                    .fluidInputs(Materials.Lubricant.getFluid(10))
                    .EUt(120)
                    .duration(MINUTE)
                    .buildAndRegister();
        }
    }
}
