package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.GregTechAPI;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.GTValues.LV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.Copper;
import static gregtech.api.unification.material.Materials.Zinc;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.ELECTROBATH;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.SWARM_GROWTH;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.electrode;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.swarm;

public class SwarmRecipeHandler {

    public static void runRecipeGeneration() {
        int i=0;
        for (Material material : GregTechAPI.materialManager.getRegisteredMaterials()) {
            i++;
            if(i>30)i=1;
            OrePrefix prefix = material.hasProperty(PropertyKey.INGOT)||material.hasProperty(PropertyKey.FLUID) ? swarm : null;
            processDecomposition(prefix, material,i);
        }
    }

    private static void processDecomposition(OrePrefix decomposePrefix, Material material,int u) {
        if (material.getMaterialComponents().isEmpty() || material.getMaterialComponents().size() > 15)
            return;

        List<ItemStack> inputs = new ArrayList<>();
        int totalInputAmount = 0;

        // compute outputs
        for (MaterialStack component : material.getMaterialComponents()) {
            totalInputAmount += (int) component.amount;
            inputs.add(OreDictUnifier.get(swarm, component.material, (int) component.amount));
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
                List<ItemStack> reducedInputs = new ArrayList<>();

                for (ItemStack itemStack : inputs) {
                    ItemStack reducedStack = itemStack.copy();
                    reducedStack.setCount(reducedStack.getCount() / highestDivisor);
                    reducedInputs.add(reducedStack);
                }

                inputs = reducedInputs;

                totalInputAmount /= highestDivisor;
            }
        }

        // generate builder
        RecipeBuilder<?> builder;

        builder = SWARM_GROWTH.recipeBuilder()
                .duration((int) Math.ceil(material.getMass() * totalInputAmount * 1.5))
                .circuitMeta(u)
                .EUt(VA[LV]);

        builder.inputStacks(inputs);


        // finish builder
        if (decomposePrefix != null) {
            builder.output(swarm, material, totalInputAmount);
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