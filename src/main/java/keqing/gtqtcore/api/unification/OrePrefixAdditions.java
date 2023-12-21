package keqing.gtqtcore.api.unification;

import gregtech.common.items.MetaItems;
import keqing.gtqtcore.api.unification.ore.GTQTOrePrefix;

public class OrePrefixAdditions {
    public static void init() {

        MetaItems.addOrePrefix(GTQTOrePrefix.milled);
        MetaItems.addOrePrefix(GTQTOrePrefix.electrode);
        MetaItems.addOrePrefix(GTQTOrePrefix.nanotube);
        MetaItems.addOrePrefix(GTQTOrePrefix.nanosensor);
        MetaItems.addOrePrefix(GTQTOrePrefix.swarm);
        MetaItems.addOrePrefix(GTQTOrePrefix.singularity);
        MetaItems.addOrePrefix(GTQTOrePrefix.leaf);
        MetaItems.addOrePrefix(GTQTOrePrefix.fcrop);
    }
}