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
        GABBRO = new StoneType(12, "gabbro", SoundType.STONE, GTQTOrePrefix.oreGabbro, GTQTMaterials.Gabbro,
                () -> gtStoneState(GTQTStoneVariantBlock.StoneType.GABBRO),
                state -> gtStonePredicate(state, GTQTStoneVariantBlock.StoneType.GABBRO), false);
        GNEISS = new StoneType(13, "gneiss", SoundType.STONE, GTQTOrePrefix.oreGneiss, GTQTMaterials.Gneiss,
                () -> gtStoneState(GTQTStoneVariantBlock.StoneType.GNEISS),
                state -> gtStonePredicate(state, GTQTStoneVariantBlock.StoneType.GNEISS), false);
        LIMESTONE = new StoneType(14, "limestone", SoundType.STONE, GTQTOrePrefix.oreLimestone, GTQTMaterials.Limestone,
                () -> gtStoneState(GTQTStoneVariantBlock.StoneType.LIMESTONE),
                state -> gtStonePredicate(state, GTQTStoneVariantBlock.StoneType.LIMESTONE), false);
        PHYLLITE = new StoneType(15, "phyllite", SoundType.STONE, GTQTOrePrefix.orePhyllite, GTQTMaterials.Phyllite,
                () -> gtStoneState(GTQTStoneVariantBlock.StoneType.PHYLLITE),
                state -> gtStonePredicate(state, GTQTStoneVariantBlock.StoneType.PHYLLITE), false);
        QUARTZITE = new StoneType(16, "quartzite", SoundType.STONE, GTQTOrePrefix.oreQuartzite, Materials.Quartzite,
                () -> gtStoneState(GTQTStoneVariantBlock.StoneType.QUARTZITE),
                state -> gtStonePredicate(state, GTQTStoneVariantBlock.StoneType.QUARTZITE), false);
        SHALE = new StoneType(17, "shale", SoundType.STONE, GTQTOrePrefix.oreShale, GTQTMaterials.Shale,
                () -> gtStoneState(GTQTStoneVariantBlock.StoneType.SHALE),
                state -> gtStonePredicate(state, GTQTStoneVariantBlock.StoneType.SHALE), false);
        SLATE = new StoneType(18, "slate", SoundType.STONE, GTQTOrePrefix.oreSlate, GTQTMaterials.Slate,
                () -> gtStoneState(GTQTStoneVariantBlock.StoneType.SLATE),
                state -> gtStonePredicate(state, GTQTStoneVariantBlock.StoneType.SLATE), false);
        SOAPSTONE = new StoneType(19, "soapstone", SoundType.STONE, GTQTOrePrefix.oreSoapstone, Materials.Soapstone,
                () -> gtStoneState(GTQTStoneVariantBlock.StoneType.SOAPSTONE),
                state -> gtStonePredicate(state, GTQTStoneVariantBlock.StoneType.SOAPSTONE), false);
        KIMBERLITE = new StoneType(20, "kimberlite", SoundType.STONE, GTQTOrePrefix.oreKimberlite, GTQTMaterials.Kimberlite,
                () -> gtStoneState(GTQTStoneVariantBlock.StoneType.KIMBERLITE),
                state -> gtStonePredicate(state, GTQTStoneVariantBlock.StoneType.KIMBERLITE), false);
    }
    private static IBlockState gtStoneState(GTQTStoneVariantBlock.StoneType stoneType) {
        return GTQTMetaBlocks.GTQT_STONE_BLOCKS.get(GTQTStoneVariantBlock.StoneVariant.SMOOTH).getState(stoneType);
    }

    private static boolean gtStonePredicate(IBlockState state, GTQTStoneVariantBlock.StoneType stoneType) {
        GTQTStoneVariantBlock block = GTQTMetaBlocks.GTQT_STONE_BLOCKS.get(GTQTStoneVariantBlock.StoneVariant.SMOOTH);
        return state.getBlock() == block && block.getState(state) == stoneType;
    }
}