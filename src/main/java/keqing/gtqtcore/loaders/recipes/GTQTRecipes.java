package keqing.gtqtcore.loaders.recipes;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.properties.*;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import gregtechfoodoption.recipe.GTFORecipeMaps;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.unification.ore.GTQTOrePrefix;
import keqing.gtqtcore.common.items.metaitems.GTQTMetaToolItems;
import keqing.gtqtcore.loaders.recipes.handlers.ISA;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.RawGrowthMedium;
import static gregtech.api.unification.ore.OrePrefix.plate;
import static gregtech.api.unification.ore.OrePrefix.stickLong;
import static gregtech.common.items.MetaItems.POWER_UNIT_HV;
import static gregtech.loaders.recipe.handlers.ToolRecipeHandler.addToolRecipe;
import static keqing.gtqtcore.api.unification.ore.GTQTOrePrefix.*;
import static keqing.gtqtcore.common.items.metaitems.GTQTMetaToolItems.Choocher_HV;
import static keqing.gtqtcore.common.items.metaitems.GTQTMetaToolItems.Jinitaimei_HV;

public class GTQTRecipes {

    public static void register(){
    }

    public static void registerTool(){
        plate.addProcessingHandler(PropertyKey.TOOL, GTQTRecipes::gcmTool);
        milled.addProcessingHandler(PropertyKey.ORE,GTQTRecipes::processMilled);
        fcrop.addProcessingHandler(PropertyKey.INGOT,GTQTRecipes::processCrops);
        leaf.addProcessingHandler(PropertyKey.INGOT,GTQTRecipes::processLeaf);

        OrePrefix.gear.addProcessingHandler(PropertyKey.DUST, GTQTRecipes::processGear);
        OrePrefix.gearSmall.addProcessingHandler(PropertyKey.DUST, GTQTRecipes::processGear);
        OrePrefix.plate.addProcessingHandler(PropertyKey.DUST, GTQTRecipes::processPlate);
        OrePrefix.rotor.addProcessingHandler(PropertyKey.INGOT, GTQTRecipes::processRotor);

        OrePrefix.block.addProcessingHandler(PropertyKey.DUST, GTQTRecipes::processBlock);
        OrePrefix.ingot.addProcessingHandler(PropertyKey.INGOT, GTQTRecipes::processIngot);
        OrePrefix.nugget.addProcessingHandler(PropertyKey.DUST, GTQTRecipes::processNugget);
    }

    public static void processIngot(OrePrefix ingotPrefix, Material material, IngotProperty property) {
        if (material.hasFluid()) {
            RecipeMaps.VACUUM_RECIPES.recipeBuilder()
                    .notConsumable(MetaItems.SHAPE_MOLD_INGOT)
                    .fluidInputs(material.getFluid(L))
                    .outputs(OreDictUnifier.get(ingotPrefix, material))
                    .duration(20).EUt(VA[MV]*4)
                    .buildAndRegister();
        }
    }
    public static void processNugget(OrePrefix orePrefix, Material material, DustProperty property) {
        if (material.hasFluid()) {
            RecipeMaps.VACUUM_RECIPES.recipeBuilder()
                    .notConsumable(MetaItems.SHAPE_MOLD_NUGGET)
                    .fluidInputs(material.getFluid(L))
                    .outputs(OreDictUnifier.get(orePrefix, material, 9))
                    .duration((int) material.getMass()*4)
                    .EUt(VA[MV])
                    .buildAndRegister();
        }

    }
    public static void processBlock(OrePrefix blockPrefix, Material material, DustProperty property) {
        ItemStack blockStack = OreDictUnifier.get(blockPrefix, material);
        long materialAmount = blockPrefix.getMaterialAmount(material);
        if (material.hasFluid()) {
            RecipeMaps.VACUUM_RECIPES.recipeBuilder()
                    .notConsumable(MetaItems.SHAPE_MOLD_BLOCK)
                    .fluidInputs(material.getFluid((int) (materialAmount * L / M)))
                    .outputs(blockStack)
                    .duration((int) material.getMass()*4).EUt(VA[MV])
                    .buildAndRegister();
        }

    }
        public static void processGear(OrePrefix gearPrefix, Material material, DustProperty property) {
        ItemStack stack = OreDictUnifier.get(gearPrefix, material);
        if (material.hasFluid()) {
            boolean isSmall = gearPrefix == OrePrefix.gearSmall;
            RecipeMaps.VACUUM_RECIPES.recipeBuilder()
                    .notConsumable(isSmall ? MetaItems.SHAPE_MOLD_GEAR_SMALL : MetaItems.SHAPE_MOLD_GEAR)
                    .fluidInputs(material.getFluid(L * (isSmall ? 1 : 4)))
                    .outputs(stack)
                    .duration(isSmall ? 80 : 400)
                    .EUt(GTValues.VA[HV])
                    .buildAndRegister();
        }

    }
    public static void processPlate(OrePrefix platePrefix, Material material, DustProperty property) {
        if (material.hasFluid()) {
            RecipeMaps.VACUUM_RECIPES.recipeBuilder()
                    .notConsumable(MetaItems.SHAPE_MOLD_PLATE)
                    .fluidInputs(material.getFluid(L))
                    .outputs(OreDictUnifier.get(platePrefix, material))
                    .duration(160)
                    .EUt(GTValues.VA[HV])
                    .buildAndRegister();
        }
    }
    public static void processRotor(OrePrefix rotorPrefix, Material material, IngotProperty property) {
        ItemStack stack = OreDictUnifier.get(rotorPrefix, material);
        if (material.hasFluid()) {
            RecipeMaps.VACUUM_RECIPES.recipeBuilder()
                    .notConsumable(MetaItems.SHAPE_MOLD_ROTOR)
                    .fluidInputs(material.getFluid(L * 4))
                    .outputs(GTUtility.copy(stack))
                    .duration(480)
                    .EUt(GTValues.VA[HV])
                    .buildAndRegister();
        }

    }
    public static void processCrops(OrePrefix fcropPrefix, Material material, IngotProperty property)
    {
        GTFORecipeMaps.GREENHOUSE_RECIPES.recipeBuilder()
                .EUt(GTValues.VA[ZPM])
                .duration(4500)
                .input(MetaItems.FERTILIZER,12)
                .fluidInputs(RawGrowthMedium.getFluid(12000))
                .notConsumable(fcropPrefix, material, 1)
                .chancedOutput(fcropPrefix, material, 100,100)
                .circuitMeta(1)
                .buildAndRegister();

        GTFORecipeMaps.GREENHOUSE_RECIPES.recipeBuilder()
                .EUt(GTValues.VA[ZPM])
                .duration(1500)
                .fluidInputs(RawGrowthMedium.getFluid(4000))
                .input(MetaItems.FERTILIZER,4)
                .notConsumable(fcropPrefix, material, 1)
                .chancedOutput(GTQTOrePrefix.leaf, material, 2000,1000)
                .chancedOutput(GTQTOrePrefix.leaf, material, 2000,1000)
                .chancedOutput(GTQTOrePrefix.leaf, material, 2000,1000)
                .chancedOutput(GTQTOrePrefix.leaf, material, 2000,1000)
                .circuitMeta(2)
                .buildAndRegister();

    }
    public static void processLeaf(OrePrefix leafPrefix, Material material, IngotProperty property)
    {
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
                .EUt(GTValues.VA[ZPM])
                .duration(1500)
                .input(leafPrefix, material, 1)
                .chancedOutput(OrePrefix.dust, material, 500,125)
                .chancedOutput(OrePrefix.dust, material, 500,125)
                .chancedOutput(OrePrefix.dust, material, 500,125)
                .chancedOutput(OrePrefix.dust, material, 500,125)
                .buildAndRegister();
    }
    public static void processMilled(OrePrefix milledPrefix, Material material, OreProperty property) {
        GTQTcoreRecipeMaps.ISA_MILL_GRINDER.recipeBuilder()
                .EUt(GTValues.VA[ZPM])
                .duration(1500)
                .input(OrePrefix.crushed, material, 16)
                .output(milledPrefix, material, 16)
                .circuitMeta(11)
                .grindBallTier(1)
                .buildAndRegister();

        GTQTcoreRecipeMaps.ISA_MILL_GRINDER.recipeBuilder()
                .EUt(GTValues.VA[ZPM])
                .duration(1200)
                .input(OrePrefix.crushed, material, 16)
                .output(milledPrefix, material, 32)
                .circuitMeta(10)
                .grindBallTier(2)
                .buildAndRegister();
    }

    private static void gcmTool(OrePrefix prefix, Material material, ToolProperty property) {
        UnificationEntry plate = new UnificationEntry(OrePrefix.plate, material);
        UnificationEntry ingot = new UnificationEntry(material.hasProperty(PropertyKey.GEM) ? OrePrefix.gem : OrePrefix.ingot, material);
        //tools
        if (material.hasFlag(MaterialFlags.GENERATE_LONG_ROD)) {
            UnificationEntry rod = new UnificationEntry(stickLong, material);
            if (material.hasFlag(MaterialFlags.GENERATE_PLATE)) {
                if (material.hasFlag(MaterialFlags.GENERATE_BOLT_SCREW))
                {
                    addToolRecipe(material, GTQTMetaToolItems.Choocher, false, "IdP", "IPP", "ST ", 'I', ingot, 'P', plate, 'T', new UnificationEntry(OrePrefix.screw, material), 'S', rod);
                    addToolRecipe(material, GTQTMetaToolItems.Jinitaimei, false, "SdS", "IPI", " T ", 'I', ingot, 'P', plate, 'T', new UnificationEntry(OrePrefix.screw, material), 'S', rod);
                }
            }
        }
        if (property.getToolDurability() > 0) {
            ItemStack[] powerUnits = {POWER_UNIT_HV.getMaxChargeOverrideStack(1800000L), POWER_UNIT_HV.getMaxChargeOverrideStack(1600000L), POWER_UNIT_HV.getMaxChargeOverrideStack(1200000L), POWER_UNIT_HV.getMaxChargeOverrideStack(6400000L)};
            for (int i = 0; i < powerUnits.length; i++) {
                IElectricItem powerUnit = powerUnits[i].getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
                ItemStack toolItem = Choocher_HV.get(material, 0, powerUnit.getMaxCharge());
                ModHandler.addShapedEnergyTransferRecipe(String.format("%s_%s_%s", "choocher_hv", material, i),
                        toolItem,
                        Ingredient.fromStacks(powerUnits[i]), true, true,
                        "IdP", "IPP", "ST ",
                        'I', ingot,
                        'P', plate,
                        'T', MetaItems.ELECTRIC_PISTON_HV,
                        'S', powerUnits[i]);
            }
        }
        if (property.getToolDurability() > 0) {
            ItemStack[] powerUnits = {POWER_UNIT_HV.getMaxChargeOverrideStack(1800000L), POWER_UNIT_HV.getMaxChargeOverrideStack(1600000L), POWER_UNIT_HV.getMaxChargeOverrideStack(1200000L), POWER_UNIT_HV.getMaxChargeOverrideStack(6400000L)};
            for (int i = 0; i < powerUnits.length; i++) {
                IElectricItem powerUnit = powerUnits[i].getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
                ItemStack toolItem = Jinitaimei_HV.get(material, 0, powerUnit.getMaxCharge());
                ModHandler.addShapedEnergyTransferRecipe(String.format("%s_%s_%s", "jinitaimei_hv", material, i),
                        toolItem,
                        Ingredient.fromStacks(powerUnits[i]), true, true,
                        "PdP", "ITP", " S ",
                        'I', ingot,
                        'P', plate,
                        'T', MetaItems.ELECTRIC_MOTOR_HV,
                        'S', powerUnits[i]);
            }
        }
    }



}
