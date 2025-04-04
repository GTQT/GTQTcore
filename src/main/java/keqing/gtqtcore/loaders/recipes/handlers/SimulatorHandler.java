package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.unification.material.MarkerMaterials;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.IV;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.SIMULATOR_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Zylon;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class SimulatorHandler {
    public static void init() {
        MemoryCards();
        Simulation();
        BiowareSimulation();
    }
    private static void MemoryCards() {

        //  Base
        CIRCUIT_ASSEMBLER_RECIPES.recipeBuilder()
                .input(frameGt,Iridium)
                .input(plate, RhodiumPlatedPalladium)
                .input(circuit, MarkerMaterials.Tier.IV,2)
                .input(wireFine, Samarium, 4)
                .output(MEMORY_CARD_BASE)
                .EUt(VA[IV])
                .duration(200)
                .buildAndRegister();

        //  Zombie
        SCANNER_RECIPES.recipeBuilder()
                .input(MEMORY_CARD_BASE)
                .input(Items.ROTTEN_FLESH)
                .output(MEMORY_CARD_ZOMBIE)
                .EUt(VA[IV])
                .duration(2400)
                .buildAndRegister();

        //  Skeleton
        SCANNER_RECIPES.recipeBuilder()
                .input(MEMORY_CARD_BASE)
                .input(Items.BONE)
                .output(MEMORY_CARD_SKELETON)
                .EUt(VA[IV])
                .duration(2400)
                .buildAndRegister();

        //  Creeper
        SCANNER_RECIPES.recipeBuilder()
                .input(MEMORY_CARD_BASE)
                .input(Items.GUNPOWDER)
                .output(MEMORY_CARD_CREEPER)
                .EUt(VA[IV])
                .duration(2400)
                .buildAndRegister();

        //  Slime
        SCANNER_RECIPES.recipeBuilder()
                .input(MEMORY_CARD_BASE)
                .input(Items.SLIME_BALL)
                .output(MEMORY_CARD_SLIME)
                .EUt(VA[IV])
                .duration(2400)
                .buildAndRegister();

        //  Spider
        SCANNER_RECIPES.recipeBuilder()
                .input(MEMORY_CARD_BASE)
                .input(Items.SPIDER_EYE)
                .output(MEMORY_CARD_SPIDER)
                .EUt(VA[IV])
                .duration(2400)
                .buildAndRegister();

        //  Blaze
        SCANNER_RECIPES.recipeBuilder()
                .input(MEMORY_CARD_BASE)
                .input(Items.BLAZE_POWDER)
                .output(MEMORY_CARD_BLAZE)
                .EUt(VA[IV])
                .duration(2400)
                .buildAndRegister();

        //  Ghast
        SCANNER_RECIPES.recipeBuilder()
                .input(MEMORY_CARD_BASE)
                .input(Items.GHAST_TEAR)
                .output(MEMORY_CARD_GHAST)
                .EUt(VA[IV])
                .duration(2400)
                .buildAndRegister();

        //  Guardian
        SCANNER_RECIPES.recipeBuilder()
                .input(MEMORY_CARD_BASE)
                .input(Items.PRISMARINE_SHARD)
                .output(MEMORY_CARD_GUARDIAN)
                .EUt(VA[IV])
                .duration(2400)
                .buildAndRegister();

        //  Wither Skeleton
        SCANNER_RECIPES.recipeBuilder()
                .input(MEMORY_CARD_BASE)
                .input(Items.SKULL, 1, 1)
                .output(MEMORY_CARD_WITHER_SKELETON)
                .EUt(VA[IV])
                .duration(2400)
                .buildAndRegister();

        //  Witch
        SCANNER_RECIPES.recipeBuilder()
                .input(MEMORY_CARD_BASE)
                .input(Items.GLASS_BOTTLE)
                .output(MEMORY_CARD_WITCH)
                .EUt(VA[IV])
                .duration(2400)
                .buildAndRegister();

        //  Enderman
        SCANNER_RECIPES.recipeBuilder()
                .input(MEMORY_CARD_BASE)
                .input(Items.ENDER_EYE)
                .output(MEMORY_CARD_ENDERMAN)
                .EUt(VA[IV])
                .duration(2400)
                .buildAndRegister();

        //  Shulker
        SCANNER_RECIPES.recipeBuilder()
                .input(MEMORY_CARD_BASE)
                .input(Items.SHULKER_SHELL)
                .output(MEMORY_CARD_SHULKER)
                .EUt(VA[IV])
                .duration(2400)
                .buildAndRegister();

        //  Wither
        SCANNER_RECIPES.recipeBuilder()
                .input(MEMORY_CARD_BASE)
                .input(Items.NETHER_STAR)
                .output(MEMORY_CARD_WITHER)
                .EUt(VA[IV])
                .duration(2400)
                .buildAndRegister();

        //  Ender Dragon
        SCANNER_RECIPES.recipeBuilder()
                .input(MEMORY_CARD_BASE)
                .inputs(new ItemStack(Blocks.DRAGON_EGG))
                .output(MEMORY_CARD_ENDER_DRAGON)
                .EUt(VA[IV])
                .duration(2400)
                .buildAndRegister();
    }

    private static void Simulation() {

        int outputChance = 1000;
        int outputChanceBoost = 100;
        int simulateDuration = 1200;

        //  LV: Zombie, Skeleton, Creeper, Slime, Spider
        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_ZOMBIE)
                .circuitMeta(1)
                .chancedOutput(new ItemStack(Items.ROTTEN_FLESH, 64), outputChance, outputChanceBoost)
                .EUt(VA[LV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_ZOMBIE)
                .circuitMeta(2)
                .chancedOutput(ingot, Iron, 16, outputChance, outputChanceBoost)
                .EUt(VA[LV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_ZOMBIE)
                .circuitMeta(3)
                .chancedOutput(new ItemStack(Items.CARROT, 32), outputChance, outputChanceBoost)
                .EUt(VA[LV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_ZOMBIE)
                .circuitMeta(4)
                .chancedOutput(new ItemStack(Items.POTATO, 32), outputChance, outputChanceBoost)
                .EUt(VA[LV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_ZOMBIE)
                .circuitMeta(5)
                .chancedOutput(new ItemStack(Items.SKULL, 16, 2), outputChance, outputChanceBoost)
                .EUt(VA[LV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_SKELETON)
                .circuitMeta(1)
                .chancedOutput(new ItemStack(Items.BONE, 32), outputChance, outputChanceBoost)
                .EUt(VA[LV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_SKELETON)
                .circuitMeta(2)
                .chancedOutput(new ItemStack(Items.ARROW, 64), outputChance, outputChanceBoost)
                .EUt(VA[LV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_SKELETON)
                .circuitMeta(3)
                .chancedOutput(new ItemStack(Items.SKULL, 16, 0), outputChance, outputChanceBoost)
                .EUt(VA[LV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_SKELETON)
                .circuitMeta(4)
                .chancedOutput(ingot, Tin, 16, outputChance, outputChanceBoost)
                .EUt(VA[LV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_CREEPER)
                .circuitMeta(1)
                .chancedOutput(new ItemStack(Items.GUNPOWDER, 32), outputChance, outputChanceBoost)
                .EUt(VA[LV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_CREEPER)
                .circuitMeta(2)
                .chancedOutput(new ItemStack(Items.COAL, 32), outputChance, outputChanceBoost)
                .EUt(VA[LV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_CREEPER)
                .circuitMeta(3)
                .chancedOutput(new ItemStack(Items.SKULL, 16, 4), outputChance, outputChanceBoost)
                .EUt(VA[LV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_SLIME)
                .circuitMeta(1)
                .chancedOutput(new ItemStack(Items.SLIME_BALL, 32), outputChance, outputChanceBoost)
                .EUt(VA[LV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_SLIME)
                .circuitMeta(2)
                .chancedOutput(ingot, Nickel, 16, outputChance, outputChanceBoost)
                .EUt(VA[LV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_SPIDER)
                .circuitMeta(1)
                .chancedOutput(new ItemStack(Items.SPIDER_EYE, 64), outputChance, outputChanceBoost)
                .EUt(VA[LV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_SPIDER)
                .circuitMeta(2)
                .chancedOutput(new ItemStack(Items.STRING, 32), outputChance, outputChanceBoost)
                .EUt(VA[LV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_SPIDER)
                .circuitMeta(3)
                .chancedOutput(ingot, Copper, 16, outputChance, outputChanceBoost)
                .EUt(VA[LV])
                .duration(simulateDuration)
                .buildAndRegister();

        //  MV: Blaze, Ghast, Guardian, Wither Skeleton, Witch
        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_BLAZE)
                .circuitMeta(1)
                .chancedOutput(new ItemStack(Items.BLAZE_ROD, 16), outputChance, outputChanceBoost)
                .EUt(VA[MV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_BLAZE)
                .circuitMeta(2)
                .chancedOutput(dust, Sulfur, 32, outputChance, outputChanceBoost)
                .EUt(VA[MV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_BLAZE)
                .circuitMeta(3)
                .chancedOutput(new ItemStack(Items.MAGMA_CREAM, 64), outputChance, outputChanceBoost)
                .EUt(VA[MV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_GHAST)
                .circuitMeta(1)
                .chancedOutput(new ItemStack(Items.GHAST_TEAR, 32), outputChance, outputChanceBoost)
                .EUt(VA[MV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_GHAST)
                .circuitMeta(2)
                .chancedOutput(ingot, Silver, 16, outputChance, outputChanceBoost)
                .EUt(VA[MV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_GUARDIAN)
                .circuitMeta(1)
                .chancedOutput(new ItemStack(Items.PRISMARINE_SHARD, 64), outputChance, outputChanceBoost)
                .EUt(VA[MV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_GUARDIAN)
                .circuitMeta(2)
                .chancedOutput(new ItemStack(Items.PRISMARINE_CRYSTALS, 64), outputChance, outputChanceBoost)
                .EUt(VA[MV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_GUARDIAN)
                .circuitMeta(3)
                .chancedOutput(new ItemStack(Items.FISH, 64), outputChance, outputChanceBoost)
                .EUt(VA[MV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_GUARDIAN)
                .circuitMeta(4)
                .chancedOutput(ingot, Gold, 16, outputChance, outputChanceBoost)
                .EUt(VA[MV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_GUARDIAN)
                .circuitMeta(5)
                .chancedOutput(dust, Aluminium, 16, outputChance, outputChanceBoost)
                .EUt(VA[MV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_WITHER_SKELETON)
                .circuitMeta(1)
                .chancedOutput(new ItemStack(Items.SKULL, 16, 1), outputChance, outputChanceBoost)
                .EUt(VA[MV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_WITHER_SKELETON)
                .circuitMeta(2)
                .chancedOutput(ingot, Lead, 16, outputChance, outputChanceBoost)
                .EUt(VA[MV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_WITHER_SKELETON)
                .circuitMeta(3)
                .chancedOutput(new ItemStack(Blocks.SOUL_SAND, 64), outputChance, outputChanceBoost)
                .EUt(VA[MV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_WITCH)
                .circuitMeta(1)
                .chancedOutput(dust, Redstone, 32, outputChance, outputChanceBoost)
                .EUt(VA[MV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_WITCH)
                .circuitMeta(2)
                .chancedOutput(dust, Glowstone, 32, outputChance, outputChanceBoost)
                .EUt(VA[MV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_WITCH)
                .circuitMeta(3)
                .chancedOutput(new ItemStack(Items.SUGAR, 64), outputChance, outputChanceBoost)
                .EUt(VA[MV])
                .duration(simulateDuration)
                .buildAndRegister();

        //  HV: Enderman, Shulker
        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_ENDERMAN)
                .circuitMeta(1)
                .chancedOutput(new ItemStack(Items.ENDER_PEARL, 16), outputChance, outputChanceBoost)
                .EUt(VA[HV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_ENDERMAN)
                .circuitMeta(2)
                .chancedOutput(new ItemStack(Items.EMERALD, 16), outputChance, outputChanceBoost)
                .EUt(VA[HV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_ENDERMAN)
                .circuitMeta(3)
                .chancedOutput(new ItemStack(Blocks.END_STONE, 32), outputChance, outputChanceBoost)
                .EUt(VA[HV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_SHULKER)
                .circuitMeta(1)
                .chancedOutput(new ItemStack(Items.SHULKER_SHELL, 16), outputChance, outputChanceBoost)
                .EUt(VA[HV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_SHULKER)
                .circuitMeta(2)
                .chancedOutput(new ItemStack(Items.DIAMOND, 16), outputChance, outputChanceBoost)
                .EUt(VA[HV])
                .duration(simulateDuration)
                .buildAndRegister();

        //  EV: Wither
        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_WITHER)
                .circuitMeta(1)
                .chancedOutput(new ItemStack(Items.NETHER_STAR), outputChance, outputChanceBoost)
                .EUt(VA[EV])
                .duration(1200)
                .buildAndRegister();

        //  IV: Ender Dragon
        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_ENDER_DRAGON)
                .circuitMeta(1)
                .chancedOutput(new ItemStack(Items.DRAGON_BREATH, 32), outputChance, outputChanceBoost)
                .EUt(VA[IV])
                .duration(simulateDuration)
                .buildAndRegister();

        SIMULATOR_RECIPES.recipeBuilder()
                .notConsumable(MEMORY_CARD_ENDER_DRAGON)
                .circuitMeta(2)
                .chancedOutput(new ItemStack(Blocks.DRAGON_EGG), outputChance, outputChanceBoost)
                .EUt(VA[IV])
                .duration(simulateDuration)
                .buildAndRegister();
    }

    private static void BiowareSimulation() {

    }
}
