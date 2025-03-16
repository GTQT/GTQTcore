package keqing.gtqtcore.loaders.recipes.handlers;

import com.google.common.collect.ImmutableMap;
import gregtech.api.GTValues;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.WireProperties;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.SiliconeRubber;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.LAMINATOR_RECIPES;
import static keqing.gtqtcore.api.utils.GTQTUniversUtil.SECOND;

public class WireRecipeHandler {

    private static final ImmutableMap<OrePrefix, Integer> INSULATION_AMOUNT = ImmutableMap.of(
            OrePrefix.cableGtSingle, 1,
            OrePrefix.cableGtDouble, 1,
            OrePrefix.cableGtQuadruple, 2,
            OrePrefix.cableGtOctal, 3,
            OrePrefix.cableGtHex, 5);

    public static void init() {
        OrePrefix.wireGtSingle.addProcessingHandler(PropertyKey.WIRE, WireRecipeHandler::generateCableCovering);
        OrePrefix.wireGtDouble.addProcessingHandler(PropertyKey.WIRE, WireRecipeHandler::generateCableCovering);
        OrePrefix.wireGtQuadruple.addProcessingHandler(PropertyKey.WIRE, WireRecipeHandler::generateCableCovering);
        OrePrefix.wireGtOctal.addProcessingHandler(PropertyKey.WIRE, WireRecipeHandler::generateCableCovering);
        OrePrefix.wireGtHex.addProcessingHandler(PropertyKey.WIRE, WireRecipeHandler::generateCableCovering);
    }

    private static void generateCableCovering(OrePrefix wirePrefix, Material material, WireProperties properties) {
        try {
            // Reflection access to private method
            Method targetMethod = gregtech.loaders.recipe.handlers.WireRecipeHandler.class
                    .getDeclaredMethod("generateManualRecipe", OrePrefix.class, Material.class, OrePrefix.class, Integer.TYPE);
            targetMethod.setAccessible(true);

            if (properties.isSuperconductor()) return;

            int cableAmount = (int) (wirePrefix.getMaterialAmount(material) * 2 / GTValues.M);
            OrePrefix cablePrefix = OrePrefix.getPrefix("cable" + wirePrefix.name().substring(4));
            int voltageTier = GTUtility.getTierByVoltage(properties.getVoltage());
            int insulationAmount = INSULATION_AMOUNT.get(cablePrefix);

            // Generate manual recipes for low tier cables
            if (voltageTier <= GTValues.LV) {
                targetMethod.invoke(null, wirePrefix, material, cablePrefix, cableAmount);
            }

            // Rubber recipes (ULV-EV)
            if (voltageTier < GTValues.EV) {
                LAMINATOR_RECIPES.recipeBuilder()
                        .input(wirePrefix, material)
                        .fluidInputs(Materials.Rubber.getFluid(L * insulationAmount))
                        .output(cablePrefix, material)
                        .EUt(VA[ULV])
                        .duration(5 * SECOND)
                        .buildAndRegister();
            }

            if (voltageTier == GTValues.EV) {
                LAMINATOR_RECIPES.recipeBuilder()
                        .input(wirePrefix, material)
                        .fluidInputs(Materials.Rubber.getFluid(L * insulationAmount))
                        .input(OrePrefix.foil, Materials.PolyvinylChloride, insulationAmount)
                        .output(cablePrefix, material)
                        .EUt(VA[ULV])
                        .duration(5 * SECOND)
                        .buildAndRegister();
            }

            // Synthetic Rubber recipe (for EV-UV cables).
            if (voltageTier <= GTValues.UV)
            {
                // Silicone Rubber recipes.
                var builder = LAMINATOR_RECIPES.recipeBuilder()
                        .input(wirePrefix, material)
                        .output(cablePrefix, material)
                        .EUt(VA[ULV])
                        .duration(5 * SECOND);
                // Apply PVC foil for EV or above cables.
                if (voltageTier >= GTValues.EV)
                    builder.input(OrePrefix.foil, Materials.PolyvinylChloride, insulationAmount);
                // Apply PPS foil for LuV or above cables.
                if (voltageTier >= GTValues.LuV)
                    builder.input(OrePrefix.foil, Materials.PolyphenyleneSulfide, insulationAmount);
                builder.fluidInputs(SiliconeRubber.getFluid(L * insulationAmount / 2))
                        .buildAndRegister();

                // Styrene Butadiene Rubber recipes.
                builder = LAMINATOR_RECIPES.recipeBuilder()
                        .input(wirePrefix, material)
                        .output(cablePrefix, material)
                        .EUt(VA[ULV])
                        .duration(5 * SECOND);
                // Apply PVC foil for EV or above cables.
                if (voltageTier >= EV)
                    builder.input(OrePrefix.foil, Materials.PolyvinylChloride, insulationAmount);
                // Apply PPS foil for LuV or above cables.
                if (voltageTier >= LuV)
                    builder.input(OrePrefix.foil, Materials.PolyphenyleneSulfide, insulationAmount);
                builder.fluidInputs(Materials.StyreneButadieneRubber.getFluid(L * insulationAmount / 4))
                        .buildAndRegister();
            }

            // Advanced materials (UV-OpV)
            if (voltageTier <= GTValues.OpV) {
                // TODO: Add advanced material recipes
            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
        }
    }
}