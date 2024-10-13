package keqing.gtqtcore.loaders.recipes.handlers;


import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.DustProperty;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.EXTRUDER_RECIPES;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_PLATE;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_ROD;
import static gregtech.api.unification.material.info.MaterialFlags.NO_SMASHING;

public class PartRecipeHandler {
    public static void register() {
        OrePrefix.bolt.addProcessingHandler(PropertyKey.DUST, PartRecipeHandler::processBolt);
        OrePrefix.foil.addProcessingHandler(PropertyKey.INGOT, PartRecipeHandler::processFoil);
        OrePrefix.gear.addProcessingHandler(PropertyKey.DUST, PartRecipeHandler::processGear);
        OrePrefix.gearSmall.addProcessingHandler(PropertyKey.DUST, PartRecipeHandler::processGear);
        OrePrefix.ring.addProcessingHandler(PropertyKey.INGOT, PartRecipeHandler::processRing);
        OrePrefix.rotor.addProcessingHandler(PropertyKey.INGOT, PartRecipeHandler::processRotor);
        OrePrefix.stickLong.addProcessingHandler(PropertyKey.DUST, PartRecipeHandler::processLongStick);
    }

    /**
     * Processing bolt by Exotic Bolt extruder.
     *
     * <p>
     *     <ul>
     *         <li>Energy Consumed: {@code VA[MV]} -> {@code VA[ULV]}.</li>
     *         <li>Duration: {@code 15 tick} -> {@code 1 tick}</li>
     *     </ul>
     * </p>
     */
    public static void processBolt(OrePrefix boltPrefix, Material material, DustProperty property) {
        ItemStack boltStack = OreDictUnifier.get(boltPrefix, material);
        ItemStack ingotStack = OreDictUnifier.get(OrePrefix.ingot, material);

        if (!boltStack.isEmpty() && !ingotStack.isEmpty()) {
            EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.ingot, material)
                    .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_BOLT)
                    .outputs(GTUtility.copy(8, boltStack))
                    .EUt(VA[ULV])
                    .duration(1)
                    .buildAndRegister();

            if (material.hasFlag(NO_SMASHING)) {
                EXTRUDER_RECIPES.recipeBuilder()
                        .input(OrePrefix.dust, material)
                        .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_BOLT)
                        .outputs(GTUtility.copy(8, boltStack))
                        .EUt(VA[ULV])
                        .duration(1)
                        .buildAndRegister();
            }
        }
    }

    /**
     * Processing foil by Exotic Foil extruder.
     *
     * <p>
     *     <ul>
     *         <li>Energy Consumed: {@code 24} -> {@code VA[ULV]}.</li>
     *         <li>Duration: {@code material.getMass()} -> {@code 1}</li>
     *     </ul>
     * </p>
     */
    public static void processFoil(OrePrefix foilPrefix, Material material, IngotProperty property) {
        if (material.hasFlag(NO_SMASHING)) {
            EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.ingot, material)
                    .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_FOIL)
                    .output(foilPrefix, material, 4)
                    .EUt(VA[ULV])
                    .duration(1)
                    .buildAndRegister();

            EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.dust, material)
                    .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_FOIL)
                    .output(foilPrefix, material, 4)
                    .EUt(VA[ULV])
                    .duration(1)
                    .buildAndRegister();
        }
    }

    /**
     * Processing gear by Exotic gear extruder.
     *
     * <p>
     *     <ul>
     *         <li>For Gear:
     *             <ul>
     *                 <li>Energy Consumed: {@code 8 * voltageMultiplier} -> {@code 4 * voltageMultiplier}.</li>
     *                 <li>Duration: {@code material.getMass() * 8} or {@code * 5} -> {@code 20 tick}.</li>
     *             </ul>
     *         </li>
     *         <li>For Small Gear:
     *             <ul>
     *                 <li>Energy Consumed: {@code 64/256} -> {@code VA[ULV]}.</li>
     *                 <li>Duration: {@code material.getMass()} -> {@code 1 tick}.</li>
     *             </ul>
     *         </li>
     *     </ul>
     *
     * </p>
     */
    public static void processGear(OrePrefix gearPrefix, Material material, DustProperty property) {
        ItemStack stack = OreDictUnifier.get(gearPrefix, material);
        if (gearPrefix == OrePrefix.gear && material.hasProperty(PropertyKey.INGOT)) {
            int voltageMultiplier = getVoltageMultiplier(material);
            EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.ingot, material, 4)
                    .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_GEAR)
                    .outputs(OreDictUnifier.get(gearPrefix, material))
                    .EUt(4 * voltageMultiplier)
                    .duration(20)
                    .buildAndRegister();

            if (material.hasFlag(NO_SMASHING)) {
                EXTRUDER_RECIPES.recipeBuilder()
                        .input(OrePrefix.dust, material, 4)
                        .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_GEAR)
                        .outputs(OreDictUnifier.get(gearPrefix, material))
                        .EUt(4 * voltageMultiplier)
                        .duration(20)
                        .buildAndRegister();
            }
        }

        if (material.hasFlag(GENERATE_PLATE) && material.hasFlag(GENERATE_ROD)) {
            if (gearPrefix == OrePrefix.gearSmall) {
                EXTRUDER_RECIPES.recipeBuilder()
                        .input(OrePrefix.ingot, material)
                        .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_GEAR_SMALL)
                        .outputs(stack)
                        .EUt(VA[ULV])
                        .duration(1)
                        .buildAndRegister();

                if (material.hasFlag(NO_SMASHING)) {
                    EXTRUDER_RECIPES.recipeBuilder()
                            .input(OrePrefix.dust, material)
                            .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_GEAR_SMALL)
                            .outputs(stack)
                            .EUt(VA[ULV])
                            .duration(1)
                            .buildAndRegister();
                }
            }
        }
    }

    /**
     * Processing ring by Exotic ring extruder.
     *
     * <p>
     *     <ul>
     *         <li>Energy Consumed: {@code getVoltageMultiplier(material) * 6} -> {@code getVoltageMultiplier(material) * 3}.</li>
     *         <li>Duration: {@code material.getMass() * 2} -> {@code 10 tick}.</li>
     *     </ul>
     * </p>
     */
    public static void processRing(OrePrefix ringPrefix, Material material, IngotProperty property) {
        EXTRUDER_RECIPES.recipeBuilder()
                .input(OrePrefix.ingot, material)
                .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_RING)
                .outputs(OreDictUnifier.get(ringPrefix, material, 4))
                .EUt(getVoltageMultiplier(material) * 3)
                .duration(10)
                .buildAndRegister();

        if (material.hasFlag(NO_SMASHING)) {
            EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.dust, material)
                    .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_RING)
                    .outputs(OreDictUnifier.get(ringPrefix, material, 4))
                    .EUt(getVoltageMultiplier(material) * 3)
                    .duration(10)
                    .buildAndRegister();
        }
    }

    /**
     * Processing rotor by Exotic rotor extruder.
     *
     * <p>
     *     <ul>
     *         <li>Energy Consumed: {@code 64/256} -> {@code VA[ULV]}.</li>
     *         <li>Duration: {@code material.getMass() * 4} -> {@code 20 tick}.</li>
     *     </ul>
     * </p>
     */
    public static void processRotor(OrePrefix rotorPrefix, Material material, IngotProperty property) {
        ItemStack stack = OreDictUnifier.get(rotorPrefix, material);
        EXTRUDER_RECIPES.recipeBuilder()
                .input(OrePrefix.ingot, material, 4)
                .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_ROTOR)
                .outputs(GTUtility.copy(stack))
                .EUt(VA[ULV])
                .duration(20)
                .buildAndRegister();

        if (material.hasFlag(NO_SMASHING)) {
            EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.dust, material, 4)
                    .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_ROTOR)
                    .outputs(GTUtility.copy(stack))
                    .EUt(VA[ULV])
                    .duration(20)
                    .buildAndRegister();
        }
    }

    /**
     * Processing long stick by Exotic long rod extruder.
     *
     * <p>
     *     <ul>
     *         <li>Energy Consumed: {@code 64} -> {@code VA[ULV]}.</li>
     *         <li>Duration: {@code max(material.getMass(), 1L)} -> {@code 1}.</li>
     *     </ul>
     * </p>
     */
    public static void processLongStick(OrePrefix longStickPrefix, Material material, DustProperty property) {
        ItemStack stack = OreDictUnifier.get(longStickPrefix, material);
        if (material.hasProperty(PropertyKey.INGOT)) {
            EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.ingot, material)
                    .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_ROD_LONG)
                    .outputs(stack)
                    .EUt(VA[ULV])
                    .duration(1)
                    .buildAndRegister();
            if (material.hasFlag(NO_SMASHING)) {
                EXTRUDER_RECIPES.recipeBuilder()
                        .input(OrePrefix.dust, material)
                        .notConsumable(GTQTMetaItems.EXOTIC_SHAPE_EXTRUDER_ROD_LONG)
                        .outputs(stack)
                        .EUt(VA[ULV])
                        .duration(1)
                        .buildAndRegister();
            }
        }
    }
    public static int getVoltageMultiplier(Material material) {
        return material.getBlastTemperature() > 2800 ? VA[LV] : VA[ULV];
    }
}
