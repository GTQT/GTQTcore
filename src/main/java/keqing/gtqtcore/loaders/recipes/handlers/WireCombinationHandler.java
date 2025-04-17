package keqing.gtqtcore.loaders.recipes.handlers;

import com.google.common.collect.ImmutableMap;
import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.WireProperties;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import org.apache.commons.lang3.ArrayUtils;

import static gregtech.api.GTValues.SECOND;
import static gregtech.api.GTValues.TICK;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.SPINNER_RECIPES;
public class WireCombinationHandler {

    private static final OrePrefix[] WIRE_DOUBLING_ORDER = {
            OrePrefix.wireGtSingle,
            OrePrefix.wireGtDouble,
            OrePrefix.wireGtQuadruple,
            OrePrefix.wireGtOctal,
            OrePrefix.wireGtHex
    };

    private static final ImmutableMap<OrePrefix, OrePrefix> cableToWireMap = ImmutableMap.of(
            OrePrefix.cableGtSingle, OrePrefix.wireGtSingle,
            OrePrefix.cableGtDouble, OrePrefix.wireGtDouble,
            OrePrefix.cableGtQuadruple, OrePrefix.wireGtQuadruple,
            OrePrefix.cableGtOctal, OrePrefix.wireGtOctal,
            OrePrefix.cableGtHex, OrePrefix.wireGtHex
    );

    public static void init() {
        OrePrefix.wireGtSingle.addProcessingHandler(PropertyKey.WIRE, WireCombinationHandler::processWireCompression);
        for (OrePrefix wirePrefix : WIRE_DOUBLING_ORDER) {
            wirePrefix.addProcessingHandler(PropertyKey.WIRE, WireCombinationHandler::generateWireCombiningRecipe);
        }
        for (OrePrefix cablePrefix : cableToWireMap.keySet()) {
            cablePrefix.addProcessingHandler(PropertyKey.WIRE, WireCombinationHandler::processCableStripping);
        }
    }

    private static void processWireCompression(OrePrefix wirePrefix, Material material, WireProperties properties) {
        for (int startTier = 0; startTier < 4; startTier++) {
            for (int i = 1; i < 5 - startTier; i++) {
                SPINNER_RECIPES.recipeBuilder()
                        .circuitMeta((int) Math.pow(2, i))
                        .inputs(OreDictUnifier.get(WIRE_DOUBLING_ORDER[startTier], material, 1 << i))
                        .outputs(OreDictUnifier.get(WIRE_DOUBLING_ORDER[startTier + i], material, 1))
                        .EUt(12) // LV
                        .duration(10 * TICK)
                        .buildAndRegister();
            }
        }
        for (int i = 1; i < 5; i++) {
            SPINNER_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .inputs(OreDictUnifier.get(WIRE_DOUBLING_ORDER[i], material, 1))
                    .outputs(OreDictUnifier.get(WIRE_DOUBLING_ORDER[0], material, (int) Math.pow(2, i)))
                    .EUt(12) // LV
                    .duration(10 * TICK)
                    .buildAndRegister();
        }
    }

    private static void generateWireCombiningRecipe(OrePrefix wirePrefix, Material material, WireProperties property) {
        int wireIndex = ArrayUtils.indexOf(WIRE_DOUBLING_ORDER, wirePrefix);
        if (wireIndex < WIRE_DOUBLING_ORDER.length - 1) {
            ModHandler.addShapelessRecipe(String.format("%s_wire_%s_doubling", material, wirePrefix),
                    OreDictUnifier.get(WIRE_DOUBLING_ORDER[wireIndex + 1], material),
                    new UnificationEntry(wirePrefix, material),
                    new UnificationEntry(wirePrefix, material));
        }
        if (wireIndex > 0) {
            ModHandler.addShapelessRecipe(String.format("%s_wire_%s_splitting", material, wirePrefix),
                    OreDictUnifier.get(WIRE_DOUBLING_ORDER[wireIndex - 1], material, 2),
                    new UnificationEntry(wirePrefix, material));
        }
        if (wireIndex < 3) {
            ModHandler.addShapelessRecipe(String.format("%s_wire_%s_quadrupling", material, wirePrefix),
                    OreDictUnifier.get(WIRE_DOUBLING_ORDER[wireIndex + 2], material),
                    new UnificationEntry(wirePrefix, material),
                    new UnificationEntry(wirePrefix, material),
                    new UnificationEntry(wirePrefix, material),
                    new UnificationEntry(wirePrefix, material));
        }
    }

    private static void processCableStripping(OrePrefix prefix, Material material, WireProperties property) {
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .input(prefix, material)
                .output(cableToWireMap.get(prefix), material)
                .output(OrePrefix.plate, Materials.Rubber, (int) (prefix.secondaryMaterials.get(0).amount / GTValues.M))
                .EUt(GTValues.VA[GTValues.ULV])
                .duration(5 * SECOND)
                .buildAndRegister();
    }
}