package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import keqing.gtqtcore.GTQTCoreConfig;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gregtech.api.unification.ore.OrePrefix.ore;

public class OreRecipeHandler {
    // Void Miner ore lists, can tweak via config settings.
    public static List<ItemStack> oreBasic = new ArrayList<>();
    public static List<ItemStack> oreAdvanced = new ArrayList<>();
    public static List<ItemStack> oreUltimate = new ArrayList<>();

    public static void register() {
        ore.addProcessingHandler(OreRecipeHandler::generateVMRecipes);
    }


    public static void generateVMRecipes(OrePrefix prefix, Material material) {
        if (GTQTCoreConfig.MachineSwitch.enableVoidMiner) {
            List<String> blacklistUniversal = Arrays.asList(GTQTCoreConfig.MachineSwitch.oreBlacklistVM);
            List<String> blacklistBasic = Arrays.asList(GTQTCoreConfig.MachineSwitch.oreBlacklistVM1);
            List<String> blacklistAdvanced = Arrays.asList(GTQTCoreConfig.MachineSwitch.oreBlacklistVM2);
            List<String> blacklistUltimate = Arrays.asList(GTQTCoreConfig.MachineSwitch.oreBlacklistVM3);
            if (GTQTCoreConfig.MachineSwitch.hasOreVariantsVM) {
                if (!blacklistUniversal.contains(material.getName())) {
                    if (!blacklistBasic.contains(material.getName())) {
                        oreBasic.addAll(OreDictUnifier.getAll(new UnificationEntry(OrePrefix.ore, material)));
                    }
                    if (!blacklistAdvanced.contains(material.getName())) {
                        oreAdvanced.addAll(OreDictUnifier.getAll(new UnificationEntry(OrePrefix.ore, material)));
                    }
                    if (!blacklistUltimate.contains(material.getName())) {
                        oreUltimate.addAll(OreDictUnifier.getAll(new UnificationEntry(OrePrefix.ore, material)));
                    }
                }
            } else {
                if (!blacklistUniversal.contains(material.getName())) {
                    if (!blacklistBasic.contains(material.getName())) {
                        oreBasic.add(OreDictUnifier.get(new UnificationEntry(OrePrefix.ore, material)));
                    }
                    if (!blacklistAdvanced.contains(material.getName())) {
                        oreAdvanced.add(OreDictUnifier.get(new UnificationEntry(OrePrefix.ore, material)));
                    }
                    if (!blacklistUltimate.contains(material.getName())) {
                        oreUltimate.add(OreDictUnifier.get(new UnificationEntry(OrePrefix.ore, material)));
                    }
                }
            }
        }
    }
}
