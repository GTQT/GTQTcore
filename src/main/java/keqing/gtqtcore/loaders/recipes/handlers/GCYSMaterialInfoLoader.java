package keqing.gtqtcore.loaders.recipes.handlers;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockTransparentCasing;


import static gregtech.api.GTValues.M;
import static gregtech.api.unification.material.Materials.Europium;
import static gregtech.api.unification.material.Materials.Rubber;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Orichalcum;
import static keqing.gtqtcore.api.unification.GTQTMaterials.PMMA;

public class GCYSMaterialInfoLoader {

    public static void init() {
        OreDictUnifier.registerOre(GTQTMetaBlocks.blockTransparentCasing.getItemVariant(BlockTransparentCasing.CasingType.PMMA), new ItemMaterialInfo(new MaterialStack(PMMA, M * 4))); // single plate

        // fix UHV hull unification
        OreDictUnifier.registerOre(MetaTileEntities.HULL[9].getStackForm(), new ItemMaterialInfo(
                new MaterialStack(Orichalcum, M * 8), // plate
                new MaterialStack(Europium, M), // single cable
                new MaterialStack(Rubber, M * 2))); // plate
    }
}
