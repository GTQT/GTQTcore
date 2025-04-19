package keqing.gtqtcore.loaders;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.StoneVariantBlock;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTStoneVariantBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.common.items.MetaItems.SHAPE_EXTRUDER_BLOCK;
import static gregtech.common.items.MetaItems.SHAPE_EXTRUDER_INGOT;

public class OreDictionaryLoader {
    public static void init(){
        loadStoneOredict();
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
