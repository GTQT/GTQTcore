package keqing.gtqtcore.loaders.recipes.chain;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.RecyclingData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.CENTRIFUGE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Prismarine;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Purpur;


public class VanillaRecipes {
    public static void register() {


        CENTRIFUGE_RECIPES.recipeBuilder()
                 .inputs(new ItemStack(Blocks.MAGMA))
                 .chancedOutput(dust, SiliconDioxide, 9500, 150)
                 .chancedOutput(dustSmall, Obsidian, 6000, 150)
                 .chancedOutput(dustSmall, GarnetYellow, 5000, 130)
                 .chancedOutput(dustSmall, Pyrochlore, 500, 130)
                 .chancedOutput(dustTiny, Barite, 750, 50)
                 .chancedOutput(nugget, Gold, 250, 80)
                 .fluidOutputs(Lava.getFluid(250))
                 .EUt(80)
                 .duration(100)
                 .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust, Prismarine, 1)
                .chancedOutput(dustSmall, Pyrolusite, 7500, 500)
                .chancedOutput(dustSmall, BrownLimonite, 7500, 500)
                .chancedOutput(dustSmall, CobaltOxide, 500, 100)
                .chancedOutput(dustSmall, Garnierite, 500, 100)
                .fluidOutputs(Water.getFluid(250))
                .EUt(VA[MV])
                .duration(5 * SECOND)
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust, Purpur, 1)
                .chancedOutput(dustSmall, Asbestos, 9500, 150)
                .chancedOutput(dustSmall, Endstone, 3000, 125)
                .chancedOutput(dustSmall, EnderPearl, 2000, 110)
                .chancedOutput(dustTiny, Vanadium, 2000, 110)
                .EUt(VA[HV])
                .duration(3 * SECOND)
                .buildAndRegister();


        GregTechAPI.RECYCLING_MANAGER.registerRecyclingData(new ItemStack(Items.CHORUS_FRUIT_POPPED, 1),
                new RecyclingData(new MaterialStack(Purpur, M)));
        GregTechAPI.RECYCLING_MANAGER.registerRecyclingData(new ItemStack(Blocks.PURPUR_BLOCK, 1),
                new RecyclingData(new MaterialStack(Purpur, M)));
        GregTechAPI.RECYCLING_MANAGER.registerRecyclingData(new ItemStack(Blocks.PURPUR_PILLAR, 1),
                new RecyclingData(new MaterialStack(Purpur, M)));
        GregTechAPI.RECYCLING_MANAGER.registerRecyclingData(new ItemStack(Blocks.END_ROD, 1),
                new RecyclingData(new MaterialStack(Purpur, M / 4), new MaterialStack(Blaze, M)));
        GregTechAPI.RECYCLING_MANAGER.registerRecyclingData(new ItemStack(Blocks.PRISMARINE, 1, 1),
                new RecyclingData(new MaterialStack(Prismarine, M * 9)));
        GregTechAPI.RECYCLING_MANAGER.registerRecyclingData(new ItemStack(Blocks.PRISMARINE, 1, 2),
                new RecyclingData(new MaterialStack(Prismarine, M * 8)));
        GregTechAPI.RECYCLING_MANAGER.registerRecyclingData(new ItemStack(Blocks.SEA_LANTERN, 1),
                new RecyclingData(new MaterialStack(Prismarine, M * 9)));



        OrePrefix.dust.setIgnored(Prismarine);
        OrePrefix.gem.setIgnored(Prismarine);
        OrePrefix.block.setIgnored(Prismarine);
    }
}
