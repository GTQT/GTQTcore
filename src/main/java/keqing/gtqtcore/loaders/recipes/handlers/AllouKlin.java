package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import keqing.gtqtcore.api.unification.ore.GTQTOrePrefix;

import static gregtech.api.unification.material.Materials.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.ALLOY_kILN;

public class AllouKlin {
    private static final MaterialStack[][] alloySmelterList = {
            {new MaterialStack(Materials.Copper, 3L), new MaterialStack(Materials.Tin, 1),
                    new MaterialStack(Materials.Bronze, 4L)},
            {new MaterialStack(Materials.Copper, 3L), new MaterialStack(Materials.Zinc, 1),
                    new MaterialStack(Materials.Brass, 4L)},
            {new MaterialStack(Materials.Copper, 1), new MaterialStack(Materials.Nickel, 1),
                    new MaterialStack(Materials.Cupronickel, 2L)},
            {new MaterialStack(Materials.Copper, 1), new MaterialStack(Materials.Redstone, 4L),
                    new MaterialStack(Materials.RedAlloy, 1)},
            {new MaterialStack(Materials.AnnealedCopper, 3L), new MaterialStack(Materials.Tin, 1),
                    new MaterialStack(Materials.Bronze, 4L)},
            {new MaterialStack(Materials.AnnealedCopper, 3L), new MaterialStack(Materials.Zinc, 1),
                    new MaterialStack(Materials.Brass, 4L)},
            {new MaterialStack(Materials.AnnealedCopper, 1), new MaterialStack(Materials.Nickel, 1),
                    new MaterialStack(Materials.Cupronickel, 2L)},
            {new MaterialStack(Materials.AnnealedCopper, 1), new MaterialStack(Materials.Redstone, 4L),
                    new MaterialStack(Materials.RedAlloy, 1)},
            {new MaterialStack(Materials.Iron, 1), new MaterialStack(Materials.Tin, 1),
                    new MaterialStack(Materials.TinAlloy, 2L)},
            {new MaterialStack(Materials.WroughtIron, 1), new MaterialStack(Materials.Tin, 1),
                    new MaterialStack(Materials.TinAlloy, 2L)},
            {new MaterialStack(Materials.Iron, 2L), new MaterialStack(Materials.Nickel, 1),
                    new MaterialStack(Materials.Invar, 3L)},
            {new MaterialStack(Materials.WroughtIron, 2L), new MaterialStack(Materials.Nickel, 1),
                    new MaterialStack(Materials.Invar, 3L)},
            {new MaterialStack(Materials.Lead, 4L), new MaterialStack(Materials.Antimony, 1),
                    new MaterialStack(Materials.BatteryAlloy, 5L)},
            {new MaterialStack(Materials.Gold, 1), new MaterialStack(Materials.Silver, 1),
                    new MaterialStack(Materials.Electrum, 2L)},
            {new MaterialStack(Materials.Magnesium, 1), new MaterialStack(Materials.Aluminium, 2L),
                    new MaterialStack(Materials.Magnalium, 3L)},
            {new MaterialStack(Materials.Silver, 1), new MaterialStack(Materials.Electrotine, 4),
                    new MaterialStack(Materials.BlueAlloy, 1)}};

    public static void init(){
        ALLOY_kILN.recipeBuilder()
                .duration(20)
                .input(OrePrefix.dust, RawRubber,3)
                .input(OrePrefix.dust, Sulfur,1)
                .output(OrePrefix.ingot,Rubber)
                .buildAndRegister();

        for (MaterialStack[] stack : alloySmelterList) {
            if (stack[0].material.hasProperty(PropertyKey.INGOT)) {
                ALLOY_kILN.recipeBuilder()
                        .duration((int) stack[2].amount * 50)
                        .input(OrePrefix.ingot, stack[0].material, (int) stack[0].amount)
                        .input(OrePrefix.dust, stack[1].material, (int) stack[1].amount)
                        .outputs(OreDictUnifier.get(OrePrefix.ingot, stack[2].material, (int) stack[2].amount))
                        .buildAndRegister();
            }
            if (stack[1].material.hasProperty(PropertyKey.INGOT)) {
                ALLOY_kILN.recipeBuilder()
                        .duration((int) stack[2].amount * 50)
                        .input(OrePrefix.dust, stack[0].material, (int) stack[0].amount)
                        .input(OrePrefix.ingot, stack[1].material, (int) stack[1].amount)
                        .outputs(OreDictUnifier.get(OrePrefix.ingot, stack[2].material, (int) stack[2].amount))
                        .buildAndRegister();
            }
            if (stack[0].material.hasProperty(PropertyKey.INGOT) && stack[1].material.hasProperty(PropertyKey.INGOT)) {
                ALLOY_kILN.recipeBuilder()
                        .duration((int) stack[2].amount * 50)
                        .input(OrePrefix.ingot, stack[0].material, (int) stack[0].amount)
                        .input(OrePrefix.ingot, stack[1].material, (int) stack[1].amount)
                        .outputs(OreDictUnifier.get(OrePrefix.ingot, stack[2].material, (int) stack[2].amount))
                        .buildAndRegister();
            }
            ALLOY_kILN.recipeBuilder()
                    .duration((int) stack[2].amount * 50)
                    .input(OrePrefix.dust, stack[0].material, (int) stack[0].amount)
                    .input(OrePrefix.dust, stack[1].material, (int) stack[1].amount)
                    .outputs(OreDictUnifier.get(OrePrefix.ingot, stack[2].material, (int) stack[2].amount))
                    .buildAndRegister();
        }
        /*
        for (MaterialStack[] stack : alloySmelterList) {
            ALLOY_kILN.recipeBuilder()
                    .duration((int) stack[2].amount * 50)
                    .input(GTQTOrePrefix.swarm, stack[0].material, (int) stack[0].amount)
                    .input(GTQTOrePrefix.swarm, stack[1].material, (int) stack[1].amount)
                    .outputs(OreDictUnifier.get(GTQTOrePrefix.swarm, stack[2].material, (int) stack[2].amount))
                    .buildAndRegister();

            ALLOY_kILN.recipeBuilder()
                    .duration((int) stack[2].amount * 50)
                    .input(GTQTOrePrefix.fcrop, stack[0].material, (int) stack[0].amount)
                    .input(GTQTOrePrefix.fcrop, stack[1].material, (int) stack[1].amount)
                    .outputs(OreDictUnifier.get(GTQTOrePrefix.fcrop, stack[2].material, (int) stack[2].amount))
                    .buildAndRegister();
        }

         */
    }

}