package keqing.gtqtcore.loaders.recipes.handlers;


import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.properties.DustProperty;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.EXTRUDER_RECIPES;
import static gregtech.api.unification.material.info.MaterialFlags.*;

public class MaterialRecipeHandler {
    public static void register() {
        OrePrefix.dust.addProcessingHandler(PropertyKey.DUST, MaterialRecipeHandler::processDust);
        OrePrefix.ingot.addProcessingHandler(PropertyKey.INGOT, MaterialRecipeHandler::processIngot);
        OrePrefix.block.addProcessingHandler(PropertyKey.DUST, MaterialRecipeHandler::processBlock);
    }

    /**
     * Processing dust to gem by Electric Implosion Compressor.
     */
    public static void processDust(OrePrefix dustPrefix, Material material, DustProperty property) {
        ItemStack dustStack = OreDictUnifier.get(dustPrefix, material);
        if (material.hasProperty(PropertyKey.GEM)) {
            ItemStack gemStack = OreDictUnifier.get(OrePrefix.gem, material);
            if (!material.hasFlag(MaterialFlags.EXPLOSIVE) && !material.hasFlag(MaterialFlags.FLAMMABLE)) {
                RecipeMaps.IMPLOSION_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copy(4, dustStack))
                        .outputs(GTUtility.copy(3, gemStack))
                        .chancedOutput(OrePrefix.dust, Materials.DarkAsh, 2500, 0)
                        .explosivesAmount(1)
                        .EUt(VA[LV])
                        .duration(20)
                        .buildAndRegister();
            }
        }
    }

    public static void processIngot(OrePrefix ingotPrefix, Material material, IngotProperty property) {
        if (material.hasFlag(GENERATE_ROD)) {
            if (!material.hasFlag(NO_WORKING)) {
                EXTRUDER_RECIPES.recipeBuilder()
                        .input(ingotPrefix, material)
                        .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_ROD)
                        .outputs(OreDictUnifier.get(OrePrefix.stick, material, 2))
                        .EUt(getVoltageMultiplier(material) * 3)
                        .duration(10)
                        .buildAndRegister();
            }
        }

        if (material.hasFlag(NO_SMASHING)) {
            EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.dust, material)
                    .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_INGOT)
                    .outputs(OreDictUnifier.get(OrePrefix.ingot, material))
                    .EUt(getVoltageMultiplier(material) * 2)
                    .duration(5)
                    .buildAndRegister();
        }


        if (material.hasFlag(GENERATE_PLATE) && !material.hasFlag(NO_WORKING)) {
            int voltageMultiplier = getVoltageMultiplier(material);
            if (!OreDictUnifier.get(OrePrefix.plate, material).isEmpty()) {
                EXTRUDER_RECIPES.recipeBuilder()
                        .input(ingotPrefix, material)
                        .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_PLATE)
                        .outputs(OreDictUnifier.get(OrePrefix.plate, material))
                        .EUt(4 * voltageMultiplier)
                        .duration(1)
                        .buildAndRegister();

                if (material.hasFlag(NO_SMASHING)) {
                    EXTRUDER_RECIPES.recipeBuilder()
                            .input(OrePrefix.dust, material)
                            .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_PLATE)
                            .outputs(OreDictUnifier.get(OrePrefix.plate, material))
                            .EUt(4 * voltageMultiplier)
                            .duration(1)
                            .buildAndRegister();
                }
            }
        }
    }

    /**
     * Processing block by Exotic block extruder.
     *
     * <p>
     *     <ul>
     *         <li>Energy Consumed: {@code getVoltageMultiplier(material) * 8} -> {@code getVoltageMultiplier(material) * 4}.</li>
     *         <li>Duration: {@code 10} -> {@code 1}.</li>
     *     </ul>
     * </p>
     */
    public static void processBlock(OrePrefix blockPrefix, Material material, DustProperty property) {
        ItemStack blockStack = OreDictUnifier.get(blockPrefix, material);
        long materialAmount = blockPrefix.getMaterialAmount(material);
        if (!material.hasFlag(EXCLUDE_BLOCK_CRAFTING_RECIPES)) {
            if (material.hasProperty(PropertyKey.INGOT)) {
                int voltageMultiplier = getVoltageMultiplier(material);
                EXTRUDER_RECIPES.recipeBuilder()
                        .input(OrePrefix.ingot, material, (int) (materialAmount / M))
                        .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_BLOCK)
                        .outputs(blockStack)
                        .duration(1)
                        .EUt(4 * voltageMultiplier)
                        .buildAndRegister();

            }
        }
    }

    public static int getVoltageMultiplier(Material material) {
        return material.getBlastTemperature() > 2800 ? VA[LV] : VA[ULV];
    }

}
