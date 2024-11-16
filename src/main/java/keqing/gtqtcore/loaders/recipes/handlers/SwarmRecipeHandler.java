package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.CarbonNanotube;
import static keqing.gtqtcore.api.unification.GCYSMaterials.NdYAG;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.nanosensor;

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
    public static void runRecipeBreeding() {
        for (Material material : GregTechAPI.materialManager.getRegisteredMaterials()) {
            processBreeding(material);
        }
    }
    public static Material []lenListCommon={
            Diamond,Glass,Sapphire,Ruby,Emerald
    };
    public static Material []lenListAdvance={
            NetherStar,Prasiolite,LeadZirconateTitanate
    };
    public static Material []lenListElite={
            LuTmYVO,Celestite,PrHoYLF,CeLAG,NdYAG
    };
    static int eliteIndex = 0;
    static int advanceIndex = 0;
    static int commonIndex = 0;
    private static void processBreeding(Material material) {
        if (!material.isElement())
            return;

        if(material.hasProperty(PropertyKey.INGOT))
        {
            if (material.getBlastTemperature() >= 6000) {
                NEUTRAL_NETWORK_NEXUS_BREEDING_MODE.recipeBuilder()
                        .notConsumable(lens, lenListElite[eliteIndex % lenListElite.length])
                        .input(block, material, 64)
                        .input(HIGHLY_ADVANCED_SOC, 8)
                        .input(dust, CarbonNanotube, 32)
                        .fluidInputs(UUMatter.getFluid(16000))
                        .output(swarm, material)
                        .EUt(VA[UV])
                        .duration(material.getBlastTemperature()/2)
                        .tier(3)
                        .buildAndRegister();
                eliteIndex++;
            } else if (material.getBlastTemperature() >= 3000) {
                NEUTRAL_NETWORK_NEXUS_BREEDING_MODE.recipeBuilder()
                        .notConsumable(lens, lenListAdvance[advanceIndex % lenListAdvance.length])
                        .input(block, material, 64)
                        .input(ADVANCED_SYSTEM_ON_CHIP, 8)
                        .input(stickLong, CarbonNanotube, 16)
                        .fluidInputs(UUMatter.getFluid(8000))
                        .output(swarm, material)
                        .EUt(VA[ZPM])
                        .duration((int) (material.getBlastTemperature()/1.5))
                        .tier(2)
                        .buildAndRegister();
                advanceIndex++;
            } else {
                NEUTRAL_NETWORK_NEXUS_BREEDING_MODE.recipeBuilder()
                        .notConsumable(lens, lenListCommon[commonIndex % lenListCommon.length])
                        .input(block, material, 64)
                        .input(SYSTEM_ON_CHIP, 8)
                        .input(stickLong, CarbonNanotube, 4)
                        .fluidInputs(UUMatter.getFluid(4000))
                        .output(swarm, material)
                        .EUt(VA[LuV])
                        .duration(1000)
                        .tier(1)
                        .buildAndRegister();
                commonIndex++;
            }
        }
        else if(material.hasProperty(PropertyKey.FLUID))
        {
            if (material.getBlastTemperature() >= 6000) {
                NEUTRAL_NETWORK_NEXUS_BREEDING_MODE.recipeBuilder()
                        .notConsumable(lens, lenListElite[eliteIndex % lenListElite.length])
                        .input(HIGHLY_ADVANCED_SOC, 8)
                        .input(stickLong, CarbonNanotube, 32)
                        .fluidInputs(material.getFluid(64*9*144))
                        .fluidInputs(UUMatter.getFluid(16000))
                        .output(swarm, material)
                        .EUt(VA[UV])
                        .duration(material.getBlastTemperature()/2)
                        .tier(3)
                        .buildAndRegister();
                eliteIndex++;
            } else if (material.getBlastTemperature() >= 3000) {
                NEUTRAL_NETWORK_NEXUS_BREEDING_MODE.recipeBuilder()
                        .notConsumable(lens, lenListAdvance[advanceIndex % lenListAdvance.length])
                        .input(ADVANCED_SYSTEM_ON_CHIP, 8)
                        .input(stickLong, CarbonNanotube, 16)
                        .fluidInputs(material.getFluid(64*9*144))
                        .fluidInputs(UUMatter.getFluid(8000))
                        .output(swarm, material)
                        .EUt(VA[ZPM])
                        .duration((int) (material.getBlastTemperature()/1.5))
                        .tier(2)
                        .buildAndRegister();
                advanceIndex++;
            } else {
                NEUTRAL_NETWORK_NEXUS_BREEDING_MODE.recipeBuilder()
                        .notConsumable(lens, lenListCommon[commonIndex % lenListCommon.length])
                        .input(SYSTEM_ON_CHIP, 8)
                        .input(stickLong, CarbonNanotube, 4)
                        .fluidInputs(material.getFluid(64*9*144))
                        .fluidInputs(UUMatter.getFluid(4000))
                        .output(swarm, material)
                        .EUt(VA[LuV])
                        .duration(1000)
                        .tier(1)
                        .buildAndRegister();
                commonIndex++;
            }
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