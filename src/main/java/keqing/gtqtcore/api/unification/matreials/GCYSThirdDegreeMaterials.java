package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;

import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.util.GTUtility.gregtechId;
import static gregtech.api.unification.material.Materials.Zinc;
import static gregtech.api.unification.material.info.MaterialFlags.DISABLE_DECOMPOSITION;
import static keqing.gtqtcore.api.unification.GCYSMaterials.RoastedSphalerite;

public class GCYSThirdDegreeMaterials {

    public static void init() {
        ZincRichSphalerite = new Material.Builder(9001, gregtechId("zinc_rich_sphalerite"))
                .dust()
                .color(0xC3AC8F)
                .iconSet(MaterialIconSet.METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .components(Zinc, 2, RoastedSphalerite, 3)
                .build()
                .setFormula("Zn2(GaGeO2)", true);
    }
}
