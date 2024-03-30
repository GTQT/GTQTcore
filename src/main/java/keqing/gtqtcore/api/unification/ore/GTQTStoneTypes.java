package keqing.gtqtcore.api.unification.ore;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.StoneType;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTStoneVariantBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;

public class GTQTStoneTypes {
    public static StoneType GABBRO;
    public static StoneType GNEISS;
    public static StoneType GRAPHITE;
    public static StoneType LIMESTONE;
    public static StoneType MICA;
    public static StoneType PHYLLITE;
    public static StoneType QUARTZITE;
    public static StoneType SHALE;
    public static StoneType SLATE;
    public static StoneType SOAPSTONE;
    public static StoneType KIMBERLITE;

    public GTQTStoneTypes(){
    }
    public static void init(){
        GABBRO = new StoneType(12, "gabbro", SoundType.STONE, OrePrefix.ore, GTQTMaterials.Gabbro,
                () -> gtStoneState(GTQTStoneVariantBlock.StoneType.GABBRO),
                state -> gtStonePredicate(state, GTQTStoneVariantBlock.StoneType.GABBRO), false);
        GNEISS = new StoneType(13, "gneiss", SoundType.STONE, OrePrefix.ore, GTQTMaterials.Gneiss,
                () -> gtStoneState(GTQTStoneVariantBlock.StoneType.GNEISS),
                state -> gtStonePredicate(state, GTQTStoneVariantBlock.StoneType.GNEISS), false);
        LIMESTONE = new StoneType(14, "limestone", SoundType.STONE, OrePrefix.ore, GTQTMaterials.Limestone,
                () -> gtStoneState(GTQTStoneVariantBlock.StoneType.LIMESTONE),
                state -> gtStonePredicate(state, GTQTStoneVariantBlock.StoneType.LIMESTONE), false);
        PHYLLITE = new StoneType(15, "phyllite", SoundType.STONE, OrePrefix.ore, GTQTMaterials.Phyllite,
                () -> gtStoneState(GTQTStoneVariantBlock.StoneType.PHYLLITE),
                state -> gtStonePredicate(state, GTQTStoneVariantBlock.StoneType.PHYLLITE), false);
        QUARTZITE = new StoneType(16, "quartzite", SoundType.STONE, OrePrefix.ore, Materials.Quartzite,
                () -> gtStoneState(GTQTStoneVariantBlock.StoneType.QUARTZITE),
                state -> gtStonePredicate(state, GTQTStoneVariantBlock.StoneType.QUARTZITE), false);
        SHALE = new StoneType(17, "shale", SoundType.STONE, OrePrefix.ore, GTQTMaterials.Shale,
                () -> gtStoneState(GTQTStoneVariantBlock.StoneType.SHALE),
                state -> gtStonePredicate(state, GTQTStoneVariantBlock.StoneType.SHALE), false);
        SLATE = new StoneType(18, "slate", SoundType.STONE, OrePrefix.ore, GTQTMaterials.Slate,
                () -> gtStoneState(GTQTStoneVariantBlock.StoneType.SLATE),
                state -> gtStonePredicate(state, GTQTStoneVariantBlock.StoneType.SLATE), false);
        SOAPSTONE = new StoneType(19, "soapstone", SoundType.STONE, OrePrefix.ore, Materials.Soapstone,
                () -> gtStoneState(GTQTStoneVariantBlock.StoneType.SOAPSTONE),
                state -> gtStonePredicate(state, GTQTStoneVariantBlock.StoneType.SOAPSTONE), false);
        KIMBERLITE = new StoneType(20, "kimberlite", SoundType.STONE, OrePrefix.ore, GTQTMaterials.Kimberlite,
                () -> gtStoneState(GTQTStoneVariantBlock.StoneType.KIMBERLITE),
                state -> gtStonePredicate(state, GTQTStoneVariantBlock.StoneType.KIMBERLITE), false);
    }
    private static IBlockState gtStoneState(GTQTStoneVariantBlock.StoneType stoneType) {
        return GTQTMetaBlocks.SUSY_STONE_BLOCKS.get(GTQTStoneVariantBlock.StoneVariant.SMOOTH).getState(stoneType);
    }

    private static boolean gtStonePredicate(IBlockState state, GTQTStoneVariantBlock.StoneType stoneType) {
        GTQTStoneVariantBlock block = GTQTMetaBlocks.SUSY_STONE_BLOCKS.get(GTQTStoneVariantBlock.StoneVariant.SMOOTH);
        return state.getBlock() == block && block.getState(state) == stoneType;
    }
}