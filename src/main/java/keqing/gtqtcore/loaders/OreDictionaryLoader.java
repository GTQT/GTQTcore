package keqing.gtqtcore.loaders;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.StoneVariantBlock;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTStoneVariantBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

import static gregtech.api.recipes.RecipeMaps.EXTRUDER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.FORGE_HAMMER_RECIPES;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.common.items.MetaItems.SHAPE_EXTRUDER_BLOCK;
import static gregtech.common.items.MetaItems.SHAPE_EXTRUDER_INGOT;

public class OreDictionaryLoader {
    public static void init(){
        loadStoneOredict();
        registerStoneBricksRecipes();
    }

    private static void registerStoneBricksRecipes() {
        // normal variant -> cobble variant
        EnumMap<GTQTStoneVariantBlock.StoneVariant, List<ItemStack>> variantListMap = new EnumMap<>(GTQTStoneVariantBlock.StoneVariant.class);
        for (GTQTStoneVariantBlock.StoneVariant shape : GTQTStoneVariantBlock.StoneVariant.values()) {
            GTQTStoneVariantBlock block = GTQTMetaBlocks.GTQT_STONE_BLOCKS.get(shape);
            variantListMap.put(shape,
                    Arrays.stream(GTQTStoneVariantBlock.StoneType.values())
                            .map(block::getItemVariant)
                            .collect(Collectors.toList()));
        }
        List<ItemStack> cobbles = variantListMap.get(GTQTStoneVariantBlock.StoneVariant.COBBLE);
        List<ItemStack> smooths = variantListMap.get(GTQTStoneVariantBlock.StoneVariant.SMOOTH);
        List<ItemStack> bricks = variantListMap.get(GTQTStoneVariantBlock.StoneVariant.BRICKS);

        registerSmoothRecipe(cobbles, smooths);
        registerCobbleRecipe(smooths, cobbles);

        for (int i = 0; i < smooths.size(); i++) {
            EXTRUDER_RECIPES.recipeBuilder()
                    .inputs(smooths.get(i))
                    .notConsumable(SHAPE_EXTRUDER_INGOT.getStackForm())
                    .outputs(bricks.get(i))
                    .duration(24).EUt(8).buildAndRegister();
        }
    }
    private static void registerSmoothRecipe(List<ItemStack> roughStack, List<ItemStack> smoothStack) {
        for (int i = 0; i < roughStack.size(); i++) {
            ModHandler.addSmeltingRecipe(roughStack.get(i), smoothStack.get(i), 0.1f);

            EXTRUDER_RECIPES.recipeBuilder()
                    .inputs(roughStack.get(i))
                    .notConsumable(SHAPE_EXTRUDER_BLOCK.getStackForm())
                    .outputs(smoothStack.get(i))
                    .duration(24).EUt(8).buildAndRegister();
        }
    }

    private static void registerCobbleRecipe(List<ItemStack> smoothStack, List<ItemStack> cobbleStack) {
        for (int i = 0; i < smoothStack.size(); i++) {
            FORGE_HAMMER_RECIPES.recipeBuilder()
                    .inputs(smoothStack.get(i))
                    .outputs(cobbleStack.get(i))
                    .duration(12).EUt(4).buildAndRegister();
        }
    }

    public static void loadStoneOredict(){

        for (GTQTStoneVariantBlock.StoneType type : GTQTStoneVariantBlock.StoneType.values()) {
            ItemStack smooth = GTQTMetaBlocks.GTQT_STONE_BLOCKS.get(GTQTStoneVariantBlock.StoneVariant.SMOOTH).getItemVariant(type);
            ItemStack cobble = GTQTMetaBlocks.GTQT_STONE_BLOCKS.get(GTQTStoneVariantBlock.StoneVariant.COBBLE).getItemVariant(type);
            ItemStack brick = GTQTMetaBlocks.GTQT_STONE_BLOCKS.get(GTQTStoneVariantBlock.StoneVariant.BRICKS).getItemVariant(type);
            OreDictUnifier.registerOre(smooth, type.getOrePrefix(), type.getMaterial());
            OreDictionary.registerOre("stone", smooth);
            OreDictionary.registerOre("cobblestone", cobble);
            OreDictionary.registerOre("stoneCobble", cobble);

            RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
                    .inputs(smooth)
                    .output(dust,type.getMaterial())
                    .duration(150)
                    .EUt(2)
                    .buildAndRegister();

            RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
                    .inputs(cobble)
                    .output(dust,type.getMaterial())
                    .duration(150)
                    .EUt(2)
                    .buildAndRegister();

            RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
                    .inputs(brick)
                    .output(dust,type.getMaterial())
                    .duration(150)
                    .EUt(2)
                    .buildAndRegister();

            RecipeMaps.FORGE_HAMMER_RECIPES.recipeBuilder()
                    .inputs(smooth)
                    .outputs(cobble)
                    .duration(12)
                    .EUt(4)
                    .buildAndRegister();

            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .inputs(smooth)
                    .outputs(brick)
                    .notConsumable(SHAPE_EXTRUDER_INGOT)
                    .duration(12)
                    .EUt(4)
                    .buildAndRegister();

            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .inputs(cobble)
                    .outputs(smooth)
                    .notConsumable(SHAPE_EXTRUDER_BLOCK)
                    .duration(12)
                    .EUt(4)
                    .buildAndRegister();
        }

        for (StoneVariantBlock.StoneType type : StoneVariantBlock.StoneType.values()) {
            ItemStack smooth = MetaBlocks.STONE_BLOCKS.get(StoneVariantBlock.StoneVariant.SMOOTH).getItemVariant(type);
            ItemStack cobble = MetaBlocks.STONE_BLOCKS.get(StoneVariantBlock.StoneVariant.COBBLE).getItemVariant(type);
            OreDictionary.registerOre("stone", smooth);
            OreDictionary.registerOre("cobblestone", cobble);
        }
    }
}
