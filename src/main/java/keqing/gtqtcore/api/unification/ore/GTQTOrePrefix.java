package keqing.gtqtcore.api.unification.ore;

import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtcore.api.unification.material.info.EPMaterialFlags;
import keqing.gtqtcore.api.unification.material.info.GTQTMaterialIconType;
import net.minecraft.client.resources.I18n;

import java.util.Collections;

import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_PLATE;
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
    public static final OrePrefix soldering_iron_head = new OrePrefix("soldering_iron_head", M, null, GTQTMaterialIconType.soldering_iron_head, ENABLE_UNIFICATION,
            OrePrefix.Conditions.hasIngotProperty, mat -> Collections.singletonList(I18n.format("metaitem.soldering_iron_head.tooltip")));

    //这里注册新的加工部件（大焊接板，弯曲板，圆盖，传动轴，气缸，阀门，外壳）
    public static final OrePrefix plate_curved = new OrePrefix("plate_curved", M, null, GTQTMaterialIconType.plate_curved, ENABLE_UNIFICATION,
            mat -> mat.hasFlag(GENERATE_CURVED_PLATE));
    public static final OrePrefix plate_big = new OrePrefix("plate_big", M, null, GTQTMaterialIconType.plate_big, ENABLE_UNIFICATION,
            mat -> mat.hasFlag(GENERATE_CURVED_PLATE));
    public static final OrePrefix round_cover = new OrePrefix("round_cover", M, null, GTQTMaterialIconType.round_cover, ENABLE_UNIFICATION,
            mat -> mat.hasFlag(GENERATE_CURVED_PLATE));
    public static final OrePrefix motor_stick = new OrePrefix("motor_stick", M, null, GTQTMaterialIconType.motor_stick, ENABLE_UNIFICATION,
            mat -> mat.hasFlag(GENERATE_CURVED_PLATE));
    public static final OrePrefix cylinder = new OrePrefix("cylinder", M, null, GTQTMaterialIconType.cylinder, ENABLE_UNIFICATION,
            mat -> mat.hasFlag(GENERATE_CURVED_PLATE));
    public static final OrePrefix valve = new OrePrefix("valve", M, null, GTQTMaterialIconType.valve, ENABLE_UNIFICATION,
            mat -> mat.hasFlag(GENERATE_CURVED_PLATE));
    public static final OrePrefix shell = new OrePrefix("shell", M, null, GTQTMaterialIconType.shell, ENABLE_UNIFICATION,
            mat -> mat.hasFlag(GENERATE_CURVED_PLATE));
}
