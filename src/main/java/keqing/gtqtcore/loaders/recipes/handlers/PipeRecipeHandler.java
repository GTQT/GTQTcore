package keqing.gtqtcore.loaders.recipes.handlers;


import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.IMaterialProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.EXTRUDER_RECIPES;
import static gregtech.api.unification.material.info.MaterialFlags.NO_SMASHING;

public class PipeRecipeHandler {

    public static void register() {
        OrePrefix.pipeTinyFluid.addProcessingHandler(PropertyKey.FLUID_PIPE, PipeRecipeHandler::processPipeTiny);
        OrePrefix.pipeSmallFluid.addProcessingHandler(PropertyKey.FLUID_PIPE, PipeRecipeHandler::processPipeSmall);
        OrePrefix.pipeNormalFluid.addProcessingHandler(PropertyKey.FLUID_PIPE, PipeRecipeHandler::processPipeNormal);
        OrePrefix.pipeLargeFluid.addProcessingHandler(PropertyKey.FLUID_PIPE, PipeRecipeHandler::processPipeLarge);
        OrePrefix.pipeHugeFluid.addProcessingHandler(PropertyKey.FLUID_PIPE, PipeRecipeHandler::processPipeHuge);

        //TODO add item pipe recipes
    }

    /**
     * Processing tiny pipe by Exotic tiny pipe extruder.
     *
     * <p>
     *     <ul>
     *         <li>Energy Consumed: {@code getVoltageMultiplier(material) * 6 -> getVoltageMultiplier(material) * 3}.</li>
     *         <li>Duration: {@code material.getMass()} -> {@code 10 tick}.</li>
     *     </ul>
     * </p>
     */
    private static void processPipeTiny(OrePrefix pipePrefix, Material material, IMaterialProperty property) {
        ItemStack pipeStack = OreDictUnifier.get(pipePrefix, material);
        EXTRUDER_RECIPES.recipeBuilder()
                .input(OrePrefix.ingot, material, 1)
                .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_PIPE_TINY)
                .outputs(GTUtility.copy(2, pipeStack))
                .EUt(getVoltageMultiplier(material) * 3)
                .duration(10)
                .buildAndRegister();

        if (material.hasFlag(NO_SMASHING)) {
            EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.dust, material, 1)
                    .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_PIPE_TINY)
                    .outputs(GTUtility.copy(2, pipeStack))
                    .EUt(getVoltageMultiplier(material) * 3)
                    .duration(10)
                    .buildAndRegister();
        }
    }

    /**
     * Processing small pipe by Exotic small pipe extruder.
     *
     * <p>
     *     <ul>
     *         <li>Energy Consumed: {@code getVoltageMultiplier(material) * 6} -> {@code getVoltageMultiplier(material) * 3}</li>
     *         <li>Duration: {@code material.getMass()} -> {@code 10 tick}.</li>
     *     </ul>
     * </p>
     */
    private static void processPipeSmall(OrePrefix pipePrefix, Material material, IMaterialProperty property) {
        ItemStack pipeStack = OreDictUnifier.get(pipePrefix, material);
        EXTRUDER_RECIPES.recipeBuilder()
                .input(OrePrefix.ingot, material, 1)
                .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_PIPE_SMALL)
                .outputs(pipeStack)
                .EUt(getVoltageMultiplier(material) * 3)
                .duration(10)
                .buildAndRegister();

        if (material.hasFlag(NO_SMASHING)) {
            EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.dust, material, 1)
                    .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_PIPE_SMALL)
                    .outputs(pipeStack)
                    .EUt(getVoltageMultiplier(material) * 3)
                    .duration(10)
                    .buildAndRegister();
        }
    }

    /**
     * Processing normal pipe by Exotic normal pipe extruder.
     *
     * <p>
     *     <ul>
     *         <li>Energy Consumed: {@code getVoltageMultiplier(material) * 6} -> {@code getVoltageMultiplier(material) * 3}.</li>
     *         <li>Duration: {@code material.getMass() * 3} -> {@code 20 tick}.</li>
     *     </ul>
     * </p>
     */
    private static void processPipeNormal(OrePrefix pipePrefix, Material material, IMaterialProperty property) {
        ItemStack pipeStack = OreDictUnifier.get(pipePrefix, material);
        EXTRUDER_RECIPES.recipeBuilder()
                .input(OrePrefix.ingot, material, 3)
                .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_PIPE_NORMAL)
                .outputs(pipeStack)
                .EUt(getVoltageMultiplier(material) * 3)
                .duration(20)
                .buildAndRegister();

        if (material.hasFlag(NO_SMASHING)) {
            EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.dust, material, 3)
                    .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_PIPE_NORMAL)
                    .outputs(pipeStack)
                    .EUt(getVoltageMultiplier(material) * 3)
                    .duration(20)
                    .buildAndRegister();
        }
    }

    /**
     * Processing large pipe by Exotic large pipe extruder.
     *
     * <p>
     *     <ul>
     *         <li>Energy Consumed: {@code getVoltageMultiplier(material) * 6} -> {@code getVoltageMultiplier(material) * 3}.</li>
     *         <li>Duration: {@code material.getMass() * 6} -> {@code 30 tick}.</li>
     *     </ul>
     * </p>
     */
    private static void processPipeLarge(OrePrefix pipePrefix, Material material, IMaterialProperty property) {
        ItemStack pipeStack = OreDictUnifier.get(pipePrefix, material);
        EXTRUDER_RECIPES.recipeBuilder()
                .input(OrePrefix.ingot, material, 6)
                .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_PIPE_LARGE)
                .outputs(pipeStack)
                .EUt(getVoltageMultiplier(material) * 3)
                .duration(30)
                .buildAndRegister();

        if (material.hasFlag(NO_SMASHING)) {
            EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.dust, material, 6)
                    .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_PIPE_LARGE)
                    .outputs(pipeStack)
                    .EUt(getVoltageMultiplier(material) * 3)
                    .duration(30)
                    .buildAndRegister();
        }
    }

    /**
     * Processing huge pipe by Exotic huge pipe extruder.
     *
     * <p>
     *     <ul>
     *         <li>Energy Consumed: {@code getVoltageMultiplier(material) * 6} -> {@code getVoltageMultiplier(material) * 3}.</li>
     *         <li>Duration: {@code material.getMass() * 24} -> {@code 40 tick}.</li>
     *     </ul>
     * </p>
     */
    private static void processPipeHuge(OrePrefix pipePrefix, Material material, IMaterialProperty property) {
        ItemStack pipeStack = OreDictUnifier.get(pipePrefix, material);
        EXTRUDER_RECIPES.recipeBuilder()
                .input(OrePrefix.ingot, material, 12)
                .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_PIPE_HUGE)
                .outputs(pipeStack)
                .EUt(getVoltageMultiplier(material) * 3)
                .duration(40)
                .buildAndRegister();

        if (material.hasFlag(NO_SMASHING)) {
            EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.dust, material, 12)
                    .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_PIPE_HUGE)
                    .outputs(pipeStack)
                    .EUt(getVoltageMultiplier(material) * 3)
                    .duration(40)
                    .buildAndRegister();
        }
    }
    public static long getVoltageMultiplier(Material material) {
        return material.getBlastTemperature() > 2800 ? VA[LV] : VA[ULV];
    }
}
