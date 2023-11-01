package keqing.gtqtcore.loaders.recipes;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.ToolProperty;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;
import keqing.gtqtcore.common.items.metaitems.GTQTMetaToolItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import static gregtech.api.unification.ore.OrePrefix.plate;
import static gregtech.api.unification.ore.OrePrefix.stickLong;
import static gregtech.common.items.MetaItems.POWER_UNIT_HV;
import static gregtech.loaders.recipe.handlers.ToolRecipeHandler.addToolRecipe;
import static keqing.gtqtcore.common.items.metaitems.GTQTMetaToolItems.Choocher_HV;
import static keqing.gtqtcore.common.items.metaitems.GTQTMetaToolItems.Jinitaimei_HV;

public class GTQTRecipes {
    public static void register(){
    }

    public static void registerTool(){
        plate.addProcessingHandler(PropertyKey.TOOL, GTQTRecipes::gcmTool);
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
