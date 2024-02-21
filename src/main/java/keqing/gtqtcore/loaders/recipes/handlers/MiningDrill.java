package keqing.gtqtcore.loaders.recipes.handlers;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Coal;
import static gregtech.api.unification.ore.OrePrefix.ore;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.MINING_DRILL_RECIPES;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Cryolite;
import static net.minecraft.init.Blocks.SAND;

public class MiningDrill {
    public static void init() {
        MiningDrillR();

    }

    private static void MiningDrillR() {

        MINING_DRILL_RECIPES.recipeBuilder()
                .chancedOutput(new ItemStack(Blocks.SAND),5000,500)
                .chancedOutput(new ItemStack(Blocks.GRAVEL),5000,500)
                .chancedOutput(new ItemStack(Blocks.CLAY),5000,500)
                .chancedOutput(new ItemStack(Blocks.DIRT),5000,500)
                .chancedOutput(new ItemStack(Blocks.STONE),5000,500)
                .tier(1)
                .EUt(30)
                .duration(40)
                .circuitMeta(1)
                .buildAndRegister();

        MINING_DRILL_RECIPES.recipeBuilder()
                .chancedOutput(ore,Copper,5000,500)
                .chancedOutput(ore,Iron,5000,500)
                .chancedOutput(ore,Tin,5000,500)
                .chancedOutput(ore,Coal,5000,500)
                .tier(1)
                .EUt(120)
                .duration(40)
                .circuitMeta(2)
                .buildAndRegister();

        MINING_DRILL_RECIPES.recipeBuilder()
                .chancedOutput(ore,Lead,2000,200)
                .chancedOutput(ore,Nickel,2000,200)
                .chancedOutput(ore,Sulfur,2000,200)
                .chancedOutput(ore,Redstone,2000,200)
                .chancedOutput(ore,Salt,2000,200)
                .chancedOutput(ore,Cassiterite,2000,200)
                .chancedOutput(ore,Chalcocite,2000,200)
                .chancedOutput(ore,Cryolite,2000,200)
                .tier(1)
                .EUt(120)
                .duration(40)
                .circuitMeta(3)
                .buildAndRegister();

        MINING_DRILL_RECIPES.recipeBuilder()
                .chancedOutput(ore,BrownLimonite,2000,200)
                .chancedOutput(ore,RockSalt,2000,200)
                .chancedOutput(ore,Pyrope,2000,200)
                .chancedOutput(ore,Ruby,2000,200)
                .chancedOutput(ore,Sphalerite,2000,200)
                .chancedOutput(ore,YellowLimonite,2000,200)
                .chancedOutput(ore,Stibnite,2000,200)
                .tier(1)
                .EUt(120)
                .duration(40)
                .circuitMeta(4)
                .buildAndRegister();

        MINING_DRILL_RECIPES.recipeBuilder()
                .chancedOutput(ore,Pentlandite,2000,200)
                .chancedOutput(ore,NetherQuartz,2000,200)
                .chancedOutput(ore,Graphite,2000,200)
                .chancedOutput(ore,Chalcopyrite,2000,200)
                .chancedOutput(ore,Mica,2000,200)
                .chancedOutput(ore,Olivine,2000,200)
                .chancedOutput(ore,Amethyst,2000,200)
                .tier(2)
                .EUt(480)
                .duration(40)
                .circuitMeta(5)
                .buildAndRegister();

        MINING_DRILL_RECIPES.recipeBuilder()
                .chancedOutput(ore,Apatite,2000,200)
                .chancedOutput(ore,GarnetRed,2000,200)
                .chancedOutput(ore,VanadiumMagnetite,2000,200)
                .chancedOutput(ore,Trona,2000,200)
                .chancedOutput(ore,BandedIron,2000,200)
                .chancedOutput(ore,Electrotine,2000,200)
                .chancedOutput(ore,Zeolite,2000,200)
                .tier(2)
                .EUt(480)
                .duration(40)
                .circuitMeta(6)
                .buildAndRegister();

    }
}
