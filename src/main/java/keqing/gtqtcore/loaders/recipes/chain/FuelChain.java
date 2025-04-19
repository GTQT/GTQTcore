package keqing.gtqtcore.loaders.recipes.chain;

import net.minecraft.init.Blocks;

import static gregtech.api.recipes.RecipeMaps.COKE_OVEN_RECIPES;
import static gregtech.api.unification.material.Materials.Ash;
import static gregtech.api.unification.material.Materials.Creosote;
import static gregtech.loaders.recipe.MachineRecipeLoader.PrimitiveBlastFurnaceBuilder;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class FuelChain {
    public static void init() {
        //仙人掌
        COKE_OVEN_RECIPES.recipeBuilder()
                .input(Blocks.CACTUS)
                .output(JIAO_XIAN_REN_ZHANG)
                .fluidOutputs(Creosote.getFluid(100))
                .duration(450)
                .buildAndRegister();

        COKE_OVEN_RECIPES.recipeBuilder()
                .input(JIAO_XIAN_REN_ZHANG)
                .output(NONG_SUO_XIAN_REN_ZHANG)
                .fluidOutputs(Creosote.getFluid(150))
                .duration(600)
                .buildAndRegister();

        PrimitiveBlastFurnaceBuilder(JIAO_XIAN_REN_ZHANG.getStackForm(), Ash, 1.25);
        PrimitiveBlastFurnaceBuilder(NONG_SUO_XIAN_REN_ZHANG.getStackForm(), Ash, 0.75);
        //甘蔗
        COKE_OVEN_RECIPES.recipeBuilder()
                .input(Blocks.REEDS)
                .output(JIAO_TANG_JIAO)
                .fluidOutputs(Creosote.getFluid(100))
                .duration(450)
                .buildAndRegister();

        COKE_OVEN_RECIPES.recipeBuilder()
                .input(JIAO_TANG_JIAO)
                .output(NONG_SUO_TANG_JIAO)
                .fluidOutputs(Creosote.getFluid(150))
                .duration(600)
                .buildAndRegister();

        PrimitiveBlastFurnaceBuilder(JIAO_TANG_JIAO.getStackForm(), Ash, 1.25);
        PrimitiveBlastFurnaceBuilder(NONG_SUO_TANG_JIAO.getStackForm(), Ash, 0.75);

        PrimitiveBlastFurnaceBuilder(ZHU_TAN.getStackForm(), Ash, 1);
    }
}
