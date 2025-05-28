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

    public static void init() {
        OrePrefix.wireGtSingle.addProcessingHandler(PropertyKey.WIRE, WireCombinationHandler::processWireCompression);
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
}