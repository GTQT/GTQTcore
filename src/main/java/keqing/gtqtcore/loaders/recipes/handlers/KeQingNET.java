package keqing.gtqtcore.loaders.recipes.handlers;

import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;

import static gregtech.api.unification.material.Materials.Copper;
import static gregtech.api.unification.material.Materials.Iron;
import static gregtech.api.unification.ore.OrePrefix.stick;
import static gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_COIL;
import static gregtech.common.blocks.BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL;
import static gregtech.common.blocks.MetaBlocks.FUSION_CASING;
import static gregtech.common.metatileentities.MetaTileEntities.FUSION_REACTOR;

public class KeQingNET {
    public static void init() {

        GTQTcoreRecipeMaps.KEQING_NET_RECIES.recipeBuilder()
                .inputs(FUSION_CASING.getItemVariant(SUPERCONDUCTOR_COIL))
                .outputs(FUSION_REACTOR[0].getStackForm())
                .EUt(7680)
                .CWUt(2)
                .totalCWU(1000)
                .buildAndRegister();
    }
}
