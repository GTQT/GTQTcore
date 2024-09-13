package keqing.gtqtcore.loaders.recipes.chain;

import static gregtech.api.recipes.RecipeMaps.MACERATOR_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.dustTiny;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class ArgChain {
    public static void init() {
        MACERATOR_RECIPES.recipeBuilder().duration(200).EUt(8)
                .input(COPPER_CROP)
                .output(dustTiny,Copper)
                .chancedOutput(dustTiny,Copper,5000,0)
                .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder().duration(200).EUt(8)
                .input(IRON_CROP)
                .output(dustTiny,Iron)
                .chancedOutput(dustTiny,Iron,5000,0)
                .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder().duration(200).EUt(8)
                .input(TIN_CROP)
                .output(dustTiny,Tin)
                .chancedOutput(dustTiny,Tin,5000,0)
                .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder().duration(200).EUt(8)
                .input(BRONZE_CROP)
                .output(dustTiny,Bronze)
                .chancedOutput(dustTiny,Bronze,5000,0)
                .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder().duration(200).EUt(8)
                .input(CARBON_CROP)
                .output(dustTiny,Carbon)
                .chancedOutput(dustTiny,Carbon,5000,0)
                .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder().duration(200).EUt(8)
                .input(GOLD_CROP)
                .output(dustTiny,Gold)
                .chancedOutput(dustTiny,Gold,5000,0)
                .buildAndRegister();
    }
}
