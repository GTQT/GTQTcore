package keqing.gtqtcore.api.unification.ore;

import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtcore.api.unification.material.info.GTQTMaterialIconType;
import net.minecraft.client.resources.I18n;

import java.util.Collections;

import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;

public class GTQTOrePrefix {
    public static final OrePrefix milled = new OrePrefix("milled", -1, null, GTQTMaterialIconType.milled, ENABLE_UNIFICATION,
            OrePrefix.Conditions.hasOreProperty, mat -> Collections.singletonList(I18n.format("metaitem.milled.tooltip.flotation")));
}
