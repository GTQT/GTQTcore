package keqing.gtqtcore.loaders;

import gregtech.api.unification.OreDictUnifier;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.StoneVariantBlock;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTStoneVariantBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryLoader {
    public static void init(){
        loadStoneOredict();
    }

    public static void loadStoneOredict(){

        for (GTQTStoneVariantBlock.StoneType type : GTQTStoneVariantBlock.StoneType.values()) {
            ItemStack smooth = GTQTMetaBlocks.GTQT_STONE_BLOCKS.get(GTQTStoneVariantBlock.StoneVariant.SMOOTH).getItemVariant(type);
            ItemStack cobble = GTQTMetaBlocks.GTQT_STONE_BLOCKS.get(GTQTStoneVariantBlock.StoneVariant.COBBLE).getItemVariant(type);
            OreDictUnifier.registerOre(smooth, type.getOrePrefix(), type.getMaterial());
            OreDictionary.registerOre("stone", smooth);
            OreDictionary.registerOre("cobblestone", cobble);
            OreDictionary.registerOre("stoneCobble", cobble);
        }

        for (StoneVariantBlock.StoneType type : StoneVariantBlock.StoneType.values()) {
            ItemStack smooth = MetaBlocks.STONE_BLOCKS.get(StoneVariantBlock.StoneVariant.SMOOTH).getItemVariant(type);
            ItemStack cobble = MetaBlocks.STONE_BLOCKS.get(StoneVariantBlock.StoneVariant.COBBLE).getItemVariant(type);
            OreDictionary.registerOre("stone", smooth);
            OreDictionary.registerOre("cobblestone", cobble);
        }
    }
}
