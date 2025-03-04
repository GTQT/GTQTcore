package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.GregTechAPI;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.BlastProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.stack.MaterialStack;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.CarbonNanotube;
import static keqing.gtqtcore.api.unification.GCYSMaterials.NdYAG;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.power;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.swarm;

public class SwarmRecipeHandler {

    public static Material[] lenListCommon = {
            Diamond, Glass, Sapphire, Ruby, Emerald
    };
    public static Material[] lenListAdvance = {
            NetherStar, Prasiolite, LeadZirconateTitanate
    };
    public static Material[] lenListElite = {
            LuTmYVO, Celestite, PrHoYLF, CeLAG, NdYAG
    };
    static int eliteIndex = 0;
    static int advanceIndex = 0;
    static int commonIndex = 0;

    public static void runRecipeGeneration() {

        for (Material material : GregTechAPI.materialManager.getRegisteredMaterials()) {


            if (material.hasProperty(PropertyKey.INGOT) || material.hasProperty(PropertyKey.FLUID)) {
                processDecomposition(material);
            }
        }
    }

    public static void runRecipeBreeding() {
        for (Material material : GregTechAPI.materialManager.getRegisteredMaterials()) {
            processBreeding(material);
        }
    }

    private static void processBreeding(Material material) {
        if (!material.isElement())
            return;

        if (material.hasProperty(PropertyKey.INGOT)) {
            if (material.getBlastTemperature() >= 6000) {
                NEUTRAL_NETWORK_NEXUS_BREEDING_MODE.recipeBuilder()
                        .notConsumable(lens, lenListElite[eliteIndex % lenListElite.length])
                        .input(block, material, 64)
                        .input(HIGHLY_ADVANCED_SOC, 8)
                        .input(dust, CarbonNanotube, 32)
                        .fluidInputs(UUMatter.getFluid(16000))
                        .output(swarm, material)
                        .EUt(VA[UV])
                        .duration(material.getBlastTemperature() / 2)
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
                        .duration((int) (material.getBlastTemperature() / 1.5))
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
        } else if (material.hasProperty(PropertyKey.FLUID)) {
            if (material.getBlastTemperature() >= 6000) {
                NEUTRAL_NETWORK_NEXUS_BREEDING_MODE.recipeBuilder()
                        .notConsumable(lens, lenListElite[eliteIndex % lenListElite.length])
                        .input(HIGHLY_ADVANCED_SOC, 8)
                        .input(stickLong, CarbonNanotube, 32)
                        .fluidInputs(material.getFluid(64 * 9 * 1000))
                        .fluidInputs(UUMatter.getFluid(16000))
                        .output(swarm, material)
                        .EUt(VA[UV])
                        .duration(material.getBlastTemperature() / 2)
                        .tier(3)
                        .buildAndRegister();
                eliteIndex++;
            } else if (material.getBlastTemperature() >= 3000) {
                NEUTRAL_NETWORK_NEXUS_BREEDING_MODE.recipeBuilder()
                        .notConsumable(lens, lenListAdvance[advanceIndex % lenListAdvance.length])
                        .input(ADVANCED_SYSTEM_ON_CHIP, 8)
                        .input(stickLong, CarbonNanotube, 16)
                        .fluidInputs(material.getFluid(64 * 9 * 1000))
                        .fluidInputs(UUMatter.getFluid(8000))
                        .output(swarm, material)
                        .EUt(VA[ZPM])
                        .duration((int) (material.getBlastTemperature() / 1.5))
                        .tier(2)
                        .buildAndRegister();
                advanceIndex++;
            } else {
                NEUTRAL_NETWORK_NEXUS_BREEDING_MODE.recipeBuilder()
                        .notConsumable(lens, lenListCommon[commonIndex % lenListCommon.length])
                        .input(SYSTEM_ON_CHIP, 8)
                        .input(stickLong, CarbonNanotube, 4)
                        .fluidInputs(material.getFluid(64 * 9 * 1000))
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

    private static void processDecomposition(Material material) {
        if (material.getMaterialComponents().isEmpty() || material.getMaterialComponents().size() > 15)
            return;


        int totalInputAmount = 0;

        // compute outputs
        for (MaterialStack component : material.getMaterialComponents()) {
            totalInputAmount += (int) component.amount;
        }

        // generate builder
        RecipeBuilder<?> builder;

        ItemStack stackForm = GTQTMetaItems.CD_ROM.getStackForm();
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("Name",  GTQTUtil.getName(material));
        stackForm.setTagCompound(compound);

        builder = SWARM_GROWTH.recipeBuilder()
                .duration((int) Math.ceil(material.getMass() * totalInputAmount * 20))
                .notConsumable(stackForm)
                .tier(getTIerByAmount(totalInputAmount))
                .EUt(VA[LuV + getTIerByAmount(totalInputAmount)]);

        for (MaterialStack component : material.getMaterialComponents()) {
            builder.input(swarm, component.material, (int) component.amount);
        }

        // finish builder
        builder.output(swarm, material, totalInputAmount);

        // register recipe
        builder.buildAndRegister();

        /////////////////////////////////////////////////////////////////////////////
        // generate builder
        RecipeBuilder<?> builder2;

        builder2 = SWARM_ASSEMBLER.recipeBuilder()
                .duration((int) Math.ceil(material.getMass() * totalInputAmount * 40))
                .input(swarm, material)
                .tier(getTIerByAmount(totalInputAmount))
                .EUt(VA[LuV + getTIerByAmount(totalInputAmount)]);

        for (MaterialStack component : material.getMaterialComponents()) {
            if (component.material.hasProperty(PropertyKey.DUST)) {

                if (component.material.hasProperty(PropertyKey.FLUID)) builder2.fluidInputs(component.material.getFluid((int) (144 * component.amount)));

                else if (component.material.hasProperty(PropertyKey.INGOT))builder2.input(ingot, component.material, (int) component.amount);

                else builder2.input(dust, component.material, (int) component.amount);

            } else if (component.material.hasProperty(PropertyKey.FLUID)) {
                Fluid fluid = component.material.getFluid();
                if (fluid == null) {
                    return; // 或者抛出异常，根据需求决定如何处理
                }
                builder2.fluidInputs(component.material.getFluid((int) (1000 * component.amount)));
            }
        }
        // finish builder
        if (material.hasProperty(PropertyKey.FLUID)&&!material.hasProperty(PropertyKey.DUST))
            builder2.fluidOutputs(material.getFluid(totalInputAmount * 1000));
        else if (material.hasProperty(PropertyKey.FLUID)&&material.hasProperty(PropertyKey.DUST))
            builder2.fluidOutputs(material.getFluid(totalInputAmount * 144));
        else if (material.hasProperty(PropertyKey.INGOT))
            builder2.output(ingot, material, totalInputAmount);
        else builder2.output(dust, material, totalInputAmount);
        // register recipe
        builder2.buildAndRegister();
        /////////////////////////////////////////////////////////////////////////////
        if(material.getBlastTemperature()<1000)return;
        if(!material.hasFluid())return;
        if(!material.hasProperty(PropertyKey.INGOT))return;

        RecipeBuilder<?> builder3;

        int duration;
        int gasTier;
        int EUt;


        gasTier=material.getBlastTemperature()/2000;
        duration=material.getMaterialComponents().size()*400;
        EUt=VA[material.getBlastTemperature()/1500];

        builder3 = CW_LASER_ALLOY_RECIPES.recipeBuilder()
                .blastFurnaceTemp(material.getBlastTemperature())
                .duration(duration)
                .notConsumable(stackForm)
                .EUt(EUt);

        switch (gasTier)
        {
            case 1 -> builder3.fluidInputs(Materials.Helium.getFluid(1000));
            case 2 -> builder3.fluidInputs(Neon.getFluid(1000));
            case 3 -> builder3.fluidInputs(Argon.getFluid(1000));
            case 4 -> builder3.fluidInputs(Krypton.getFluid(1000));
            default -> builder3.fluidInputs(Materials.Nitrogen.getFluid(1000));
        }

        int amount=0;

        for (MaterialStack component : material.getMaterialComponents()) {
            if(component.material.hasProperty(PropertyKey.DUST))builder3.input(power, component.material, (int) component.amount);
            else builder3.fluidInputs(component.material.getFluid((int)component.amount*1000));

            amount+=(int)component.amount;
        }

        builder3 .fluidOutputs(material.getFluid(144*amount))
                .buildAndRegister();
    }

    private static int getTIerByAmount(int totalInputAmount) {
        if (totalInputAmount <= 10) return 1;
        if (totalInputAmount <= 20) return 2;
        return 3;
    }

}