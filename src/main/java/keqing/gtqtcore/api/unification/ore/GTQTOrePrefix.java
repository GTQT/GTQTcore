package keqing.gtqtcore.api.unification.ore;

import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtcore.api.unification.material.info.EPMaterialFlags;
import keqing.gtqtcore.api.unification.material.info.GTQTMaterialIconType;
import net.minecraft.client.resources.I18n;

import java.util.Collections;

import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;
import static keqing.gtqtcore.api.unification.material.info.EPMaterialFlags.GENERATE_ELECTRODE;
import static keqing.gtqtcore.api.unification.material.info.EPMaterialFlags.*;
import static gregtech.api.GTValues.M;
public class GTQTOrePrefix {
    public static final OrePrefix milled = new OrePrefix("milled", -1, null, GTQTMaterialIconType.milled, ENABLE_UNIFICATION,
            OrePrefix.Conditions.hasOreProperty, mat -> Collections.singletonList(I18n.format("metaitem.milled.tooltip.flotation")));

    public static final OrePrefix electrode = new OrePrefix("electrode", -1, null, GTQTMaterialIconType.electrode, ENABLE_UNIFICATION,
            mat -> mat.hasFlag(GENERATE_ELECTRODE));

    public static final OrePrefix swarm = new OrePrefix("swarm", M, null, GTQTMaterialIconType.swarm, ENABLE_UNIFICATION,
            OrePrefix.Conditions.hasIngotProperty,  mat ->Collections.singletonList(I18n.format("metaitem.swarm.tooltip")));



    public static final OrePrefix singularity = new OrePrefix("singularity", M, null, GTQTMaterialIconType.singularity, ENABLE_UNIFICATION,
            OrePrefix.Conditions.hasIngotProperty, mat -> Collections.singletonList(I18n.format("metaitem.singularity.tooltip")));
    public static final OrePrefix leaf = new OrePrefix("leaf", M, null, GTQTMaterialIconType.leaf, ENABLE_UNIFICATION,
            OrePrefix.Conditions.hasIngotProperty, mat -> Collections.singletonList(I18n.format("metaitem.leaf.tooltip")));

    public static final OrePrefix fcrop = new OrePrefix("fcrop", M, null, GTQTMaterialIconType.fcrop, ENABLE_UNIFICATION,
            OrePrefix.Conditions.hasIngotProperty, mat -> Collections.singletonList(I18n.format("metaitem.fcrop.tooltip")));
    public static final OrePrefix nanotube = new OrePrefix("nanotube", M, null, GTQTMaterialIconType.nanotube, ENABLE_UNIFICATION,
            mat -> mat.hasFlag(GENERATE_NANOTUBE));

    public static final OrePrefix nanosensor = new OrePrefix("nanosensor", M, null, GTQTMaterialIconType.nanosensor, ENABLE_UNIFICATION,
            mat -> mat.hasFlag(GENERATE_NANOSENSOR));

}
