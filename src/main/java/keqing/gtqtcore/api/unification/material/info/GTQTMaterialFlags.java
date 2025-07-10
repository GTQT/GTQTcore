package keqing.gtqtcore.api.unification.material.info;

import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.properties.PropertyKey;

public class GTQTMaterialFlags {
    public static final MaterialFlag GENERATE_CURVED_PLATE = new MaterialFlag.Builder("generate_curved_plate")
            .requireFlags(MaterialFlags.GENERATE_PLATE)
            .build();

    public static final MaterialFlag DISABLE_CRYSTALLIZATION = new MaterialFlag.Builder("no_crystallization")
            .requireFlags(MaterialFlags.CRYSTALLIZABLE)
            .requireProps(PropertyKey.GEM)
            .build();

    public static final MaterialFlag GENERATE_BOULE = new MaterialFlag.Builder("generate_boule")
            .requireProps(PropertyKey.GEM)
            .build();

    public static final MaterialFlag GENERATE_WRAP = new MaterialFlag.Builder("generate_wrap")
            .build();

}
