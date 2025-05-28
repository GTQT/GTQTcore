package keqing.gtqtcore.api.unification.ore;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtcore.api.unification.material.info.GTQTMaterialFlags;
import keqing.gtqtcore.api.unification.material.info.GTQTMaterialIconType;
import net.minecraft.client.resources.I18n;

import java.util.Collections;

import static gregtech.api.GTValues.M;
import static gregtech.api.unification.ore.OrePrefix.Conditions.hasGemProperty;
import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;
import static keqing.gtqtcore.api.unification.material.info.GTQTMaterialFlags.*;

public class GTQTOrePrefix {
    public static final OrePrefix gemSolitary = new OrePrefix("gemSolitary", M * 8, null, GTQTMaterialIconType.gemSolitary, OrePrefix.Flags.ENABLE_UNIFICATION, OrePrefix.Conditions.hasGemProperty);

    public static final OrePrefix seedCrystal = new OrePrefix("seedCrystal", M / 9, null, GTQTMaterialIconType.seedCrystal, ENABLE_UNIFICATION, hasGemProperty.and(mat -> mat.hasFlag(GENERATE_BOULE) || (mat.hasFlag(MaterialFlags.CRYSTALLIZABLE) && !mat.hasFlag(GTQTMaterialFlags.DISABLE_CRYSTALLIZATION))));
    public static final OrePrefix boule = new OrePrefix("boule", M * 4, null, GTQTMaterialIconType.boule, ENABLE_UNIFICATION, hasGemProperty.and(mat -> mat.hasFlag(GENERATE_BOULE) || (mat.hasFlag(MaterialFlags.CRYSTALLIZABLE) && !mat.hasFlag(GTQTMaterialFlags.DISABLE_CRYSTALLIZATION))));
    public static final OrePrefix milled = new OrePrefix("milled", -1, null, GTQTMaterialIconType.milled, ENABLE_UNIFICATION, OrePrefix.Conditions.hasOreProperty, mat -> Collections.singletonList(I18n.format("metaitem.milled.tooltip.flotation")));

    public static final OrePrefix wrap = new OrePrefix("wrap", M, null, GTQTMaterialIconType.wrap, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_WRAP));
    public static final OrePrefix swarm = new OrePrefix("swarm", M, null, GTQTMaterialIconType.swarm, ENABLE_UNIFICATION, (mat) -> (!mat.getMaterialComponents().isEmpty() || mat.isElement()) && (mat.hasProperty(PropertyKey.INGOT) || (mat.hasProperty(PropertyKey.FLUID))), mat -> Collections.singletonList(I18n.format("metaitem.swarm.tooltip")));
    public static final OrePrefix singularity = new OrePrefix("singularity", M, null, GTQTMaterialIconType.singularity, ENABLE_UNIFICATION, Material::isElement, mat -> Collections.singletonList(I18n.format("metaitem.singularity.tooltip")));
    public static final OrePrefix soldering_iron_head = new OrePrefix("soldering_iron_head", M, null, GTQTMaterialIconType.soldering_iron_head, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_CURVED_PLATE));
    //这里注册新的加工部件（大焊接板，弯曲板，圆盖，传动轴，气缸，阀门，外壳）
    public static final OrePrefix plate_curved = new OrePrefix("plate_curved", M, null, GTQTMaterialIconType.plate_curved, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_CURVED_PLATE));
    public static final OrePrefix plate_big = new OrePrefix("plate_big", M, null, GTQTMaterialIconType.plate_big, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_CURVED_PLATE));
    public static final OrePrefix round_cover = new OrePrefix("round_cover", M, null, GTQTMaterialIconType.round_cover, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_CURVED_PLATE));
    public static final OrePrefix motor_stick = new OrePrefix("motor_stick", M, null, GTQTMaterialIconType.motor_stick, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_CURVED_PLATE));
    public static final OrePrefix cylinder = new OrePrefix("cylinder", M, null, GTQTMaterialIconType.cylinder, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_CURVED_PLATE));
    public static final OrePrefix valve = new OrePrefix("valve", M, null, GTQTMaterialIconType.valve, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_CURVED_PLATE));
    public static final OrePrefix shell = new OrePrefix("shell", M, null, GTQTMaterialIconType.shell, ENABLE_UNIFICATION, mat -> mat.hasFlag(GENERATE_CURVED_PLATE));

    public static OrePrefix oreGabbro = new OrePrefix("oreGabbro", -1L, null, MaterialIconType.ore, 1L, OrePrefix.Conditions.hasOreProperty);
    public static OrePrefix oreGneiss = new OrePrefix("oreGneiss", -1L, null, MaterialIconType.ore, 1L, OrePrefix.Conditions.hasOreProperty);
    public static OrePrefix oreLimestone = new OrePrefix("oreLimestone", -1L, null, MaterialIconType.ore, 1L, OrePrefix.Conditions.hasOreProperty);
    public static OrePrefix orePhyllite = new OrePrefix("orePhyllite", -1L, null, MaterialIconType.ore, 1L, OrePrefix.Conditions.hasOreProperty);
    public static OrePrefix oreQuartzite = new OrePrefix("oreQuartzite", -1L, null, MaterialIconType.ore, 1L, OrePrefix.Conditions.hasOreProperty);
    public static OrePrefix oreShale = new OrePrefix("oreShale", -1L, null, MaterialIconType.ore, 1L, OrePrefix.Conditions.hasOreProperty);
    public static OrePrefix oreSlate = new OrePrefix("oreSlate", -1L, null, MaterialIconType.ore, 1L, OrePrefix.Conditions.hasOreProperty);
    public static OrePrefix oreSoapstone = new OrePrefix("oreSoapstone", -1L, null, MaterialIconType.ore, 1L, OrePrefix.Conditions.hasOreProperty);
    public static OrePrefix oreKimberlite = new OrePrefix("oreKimberlite", -1L, null, MaterialIconType.ore, 1L, OrePrefix.Conditions.hasOreProperty);
}
