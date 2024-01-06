package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.GregTechAPI;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import keqing.gtqtcore.api.unification.ore.GTQTOrePrefix;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.Palladium;
import static gregtech.api.unification.material.Materials.Platinum;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.ELECTROBATH;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.NANOHYBRID;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.electrode;

public class TESEDecompositionRecipeHandler {

    public static void runRecipeGeneration() {
        for (Material material : GregTechAPI.materialManager.getRegisteredMaterials()) {
            OrePrefix prefix = material.hasProperty(PropertyKey.DUST) ? GTQTOrePrefix.swarm : null;
            processDecomposition(prefix, material);
        }
    }

    private static void processDecomposition(OrePrefix decomposePrefix, Material material) {
        if (material.getMaterialComponents().isEmpty() ||
                (!material.hasFlag(DECOMPOSITION_BY_ELECTROLYZING) &&
                        !material.hasFlag(DECOMPOSITION_BY_CENTRIFUGING)) ||
                // disable decomposition if explicitly disabled for this material or for one of it's components
                material.hasFlag(DISABLE_DECOMPOSITION) ||
                material.getMaterialComponents().size() > 6)
            return;

        List<ItemStack> inputs = new ArrayList<>();
        int totalInputAmount = 0;

        // compute outputs
        for (MaterialStack component : material.getMaterialComponents()) {
            totalInputAmount += component.amount;
            if (component.material.hasProperty(PropertyKey.DUST)) {
                inputs.add(OreDictUnifier.get(GTQTOrePrefix.swarm, component.material, (int) component.amount));
            }
        }

        // only reduce items
        if (decomposePrefix != null) {
            // calculate lowest common denominator
            List<Integer> materialAmounts = new ArrayList<>();
            materialAmounts.add(totalInputAmount);
            inputs.forEach(itemStack -> materialAmounts.add(itemStack.getCount()));

            int highestDivisor = 1;

            int smallestMaterialAmount = getSmallestMaterialAmount(materialAmounts);
            for (int i = 2; i <= smallestMaterialAmount; i++) {
                if (isEveryMaterialReducible(i, materialAmounts))
                    highestDivisor = i;
            }

            // divide components
            if (highestDivisor != 1) {
                List<ItemStack> reducedOutputs = new ArrayList<>();

                for (ItemStack itemStack : inputs) {
                    ItemStack reducedStack = itemStack.copy();
                    reducedStack.setCount(reducedStack.getCount() / highestDivisor);
                    reducedOutputs.add(reducedStack);
                }
                inputs = reducedOutputs;
                totalInputAmount /= highestDivisor;
            }
        }

        // generate builder
        RecipeBuilder<?> builder;
        if (material.hasFlag(DECOMPOSITION_BY_ELECTROLYZING)) {
            builder =  NANOHYBRID.recipeBuilder()
                    .duration(((int) material.getProtons() * totalInputAmount * 2))
                    .EUt(material.getMaterialComponents().size() <= 2 ? VA[UHV] : 2 * VA[UHV]);
        } else {
            builder = NANOHYBRID.recipeBuilder()
                    .duration((int) Math.ceil(material.getMass() * totalInputAmount * 1.5))
                    .EUt(VA[UHV]);
        }
        builder.inputStacks(inputs);

        // finish builder
        if (decomposePrefix != null) {
            builder.chancedOutput(decomposePrefix, material,1,1);
        }

        // register recipe
        builder.buildAndRegister();
    }

    private static boolean isEveryMaterialReducible(int divisor, List<Integer> materialAmounts) {
        for (int amount : materialAmounts) {
            if (amount % divisor != 0)
                return false;
        }
        return true;
    }

    private static int getSmallestMaterialAmount(List<Integer> materialAmounts) {
        return materialAmounts.stream().min(Integer::compare).orElse(0);
    }
}