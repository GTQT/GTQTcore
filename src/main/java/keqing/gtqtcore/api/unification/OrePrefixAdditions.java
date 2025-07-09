package keqing.gtqtcore.api.unification;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.common.ConfigHolder;
import gregtech.common.items.MetaItems;
import keqing.gtqtcore.api.unification.ore.GTQTOrePrefix;

public class OrePrefixAdditions {
    public static void init() {
        MetaItems.addOrePrefix(GTQTOrePrefix.seedCrystal);
        MetaItems.addOrePrefix(GTQTOrePrefix.boule);
        MetaItems.addOrePrefix(GTQTOrePrefix.milled);
        MetaItems.addOrePrefix(GTQTOrePrefix.soldering_iron_head);
        MetaItems.addOrePrefix(GTQTOrePrefix.plate_curved);

        MetaItems.addOrePrefix(GTQTOrePrefix.swarm);
        MetaItems.addOrePrefix(GTQTOrePrefix.singularity);
        MetaItems.addOrePrefix(GTQTOrePrefix.wrap);

        MetaItems.addOrePrefix(GTQTOrePrefix.plate_big);
        MetaItems.addOrePrefix(GTQTOrePrefix.round_cover);
        MetaItems.addOrePrefix(GTQTOrePrefix.motor_stick);
        MetaItems.addOrePrefix(GTQTOrePrefix.cylinder);
        MetaItems.addOrePrefix(GTQTOrePrefix.valve);
        MetaItems.addOrePrefix(GTQTOrePrefix.shell);

        if (ConfigHolder.worldgen.allUniqueStoneTypes) {
            GTQTOrePrefix.oreGabbro.addSecondaryMaterial(new MaterialStack(GTQTMaterials.Gabbro, 144));
            GTQTOrePrefix.oreGneiss.addSecondaryMaterial(new MaterialStack(GTQTMaterials.Gneiss, 144));
            GTQTOrePrefix.oreLimestone.addSecondaryMaterial(new MaterialStack(GTQTMaterials.Limestone, 144));
            GTQTOrePrefix.orePhyllite.addSecondaryMaterial(new MaterialStack(GTQTMaterials.Phyllite, 144));
            GTQTOrePrefix.oreQuartzite.addSecondaryMaterial(new MaterialStack(Materials.Quartzite, 144));
            GTQTOrePrefix.oreShale.addSecondaryMaterial(new MaterialStack(GTQTMaterials.Shale, 144));
            GTQTOrePrefix.oreSlate.addSecondaryMaterial(new MaterialStack(GTQTMaterials.Slate, 144));
            GTQTOrePrefix.oreSoapstone.addSecondaryMaterial(new MaterialStack(Materials.Soapstone, 144));
            GTQTOrePrefix.oreKimberlite.addSecondaryMaterial(new MaterialStack(GTQTMaterials.Kimberlite, 144));
        }
    }
}