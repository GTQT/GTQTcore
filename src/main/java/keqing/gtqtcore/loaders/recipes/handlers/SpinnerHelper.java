package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Polybenzimidazole;
import static gregtech.common.items.MetaItems.PLANT_BALL;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.SPINNER_RECIPES;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.Polyetheretherketone;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.wrap;
import static keqing.gtqtcore.api.utils.GTQTUniversUtil.SECOND;
import static keqing.gtqtcore.api.utils.GTQTUniversUtil.TICK;

public class SpinnerHelper {
    public static void init() {
        SPINNER_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .input(Items.STRING)
                .output(wrap, Wool)
                .duration(200)
                .EUt(VA[ULV])
                .buildAndRegister();

        SPINNER_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .input(OrePrefix.dust,Asbestos,4)
                .output(wrap, Asbestos)
                .duration(200)
                .EUt(VA[LV])
                .buildAndRegister();

        registerWrap(Polycaprolactam,3);
        registerWrap(CarbonNanotube,6);

        registerWrap(Polyethylene,1);
        registerWrap(PolyvinylChloride,2);
        registerWrap(Epoxy,3);
        registerWrap(ReinforcedEpoxyResin,4);
        registerWrap(Polytetrafluoroethylene,5);
        registerWrap(Zylon,6);
        registerWrap(Polybenzimidazole,7);
        registerWrap(Polyetheretherketone,8);
        //registerWrap(Kevlar,9);
        registerWrap(KaptonK,10);
        registerWrap(KaptonE,11);
        registerWrap(FullerenePolymerMatrix,12);



        SPINNER_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .input(Items.STRING, 4)
                .output(Blocks.WOOL)
                .EUt(VA[LV])
                .duration(2 * SECOND)
                .buildAndRegister();

        // 8x string -> 3x carpet
        SPINNER_RECIPES.recipeBuilder()
                .circuitMeta(8)
                .input(Items.STRING, 8)
                .output(Blocks.CARPET, 3)
                .EUt(VA[LV])
                .duration(2 * SECOND)
                .buildAndRegister();

        // Leather armors.
        SPINNER_RECIPES.recipeBuilder()
                .circuitMeta(5)
                .input(Items.LEATHER, 5)
                .output(Items.LEATHER_HELMET)
                .EUt(VA[ULV])
                .duration(2 * SECOND + 10 * TICK)
                .buildAndRegister();

        SPINNER_RECIPES.recipeBuilder()
                .circuitMeta(8)
                .input(Items.LEATHER, 8)
                .output(Items.LEATHER_CHESTPLATE)
                .EUt(VA[ULV])
                .duration(2 * SECOND + 10 * TICK)
                .buildAndRegister();

        SPINNER_RECIPES.recipeBuilder()
                .circuitMeta(7)
                .input(Items.LEATHER, 7)
                .output(Items.LEATHER_LEGGINGS)
                .EUt(VA[ULV])
                .duration(2 * SECOND + 10 * TICK)
                .buildAndRegister();

        SPINNER_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .input(Items.LEATHER, 4)
                .output(Items.LEATHER_BOOTS)
                .EUt(VA[ULV])
                .duration(2 * SECOND + 10 * TICK)
                .buildAndRegister();

        // 1x plant ball -> 2x grass
        SPINNER_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .input(PLANT_BALL, 1)
                .outputs(new ItemStack(Blocks.TALLGRASS, 1, 1))
                .EUt(VA[ULV])
                .duration(2 * SECOND)
                .buildAndRegister();

        // 1x plant ball -> 2x tall grass
        SPINNER_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .input(PLANT_BALL)
                .outputs(new ItemStack(Blocks.TALLGRASS, 1, 2))
                .EUt(VA[ULV])
                .duration(2 * SECOND)
                .buildAndRegister();

        // 2x plant ball -> 1x vine
        SPINNER_RECIPES.recipeBuilder()
                .circuitMeta(3)
                .input(PLANT_BALL, 2)
                .output(Blocks.VINE)
                .EUt(VA[ULV])
                .duration(2 * SECOND)
                .buildAndRegister();

        // 4x plant ball -> 1x waterlily
        SPINNER_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .input(PLANT_BALL, 4)
                .output(Blocks.WATERLILY)
                .EUt(VA[ULV])
                .duration(2 * SECOND)
                .buildAndRegister();
    }
    public static void registerWrap(Material material,int euTier) {
        SPINNER_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .fluidInputs(material.getFluid(L * 4))
                .output(wrap, material)
                .duration(200)
                .EUt(VA[euTier])
                .buildAndRegister();
    }
}
