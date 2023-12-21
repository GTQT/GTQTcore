package keqing.gtqtcore.api.unification.material.info;

import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.properties.PropertyKey;

import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_ROD;

public class EPMaterialFlags {

    public static final MaterialFlag GENERATE_COIL = new MaterialFlag.Builder("generate_coil")
            .requireFlags(MaterialFlags.GENERATE_FINE_WIRE)
            .build();
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

    public static final MaterialFlag GENERATE_ELECTRODE = new MaterialFlag.Builder("electrode")
            .requireFlags(GENERATE_ROD)
            .requireProps(PropertyKey.INGOT)
            .build();

    public static final MaterialFlag GENERATE_NANOTUBE = new MaterialFlag.Builder("generate_nanotube")
            .build();
    public static final MaterialFlag GENERATE_NANOSENSOR = new MaterialFlag.Builder("generate_nanosensor")
            .build();

}
