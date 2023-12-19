package keqing.gtqtcore.api.unification.matreials;
import gregtech.api.GTValues;
import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.BlastProperty;
import gregtech.api.unification.material.properties.BlastProperty.GasTier;
import gregtech.api.unification.material.properties.ToolProperty;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static gregtech.api.util.GTUtility.gregtechId;
import gregtech.api.unification.material.Material;
import keqing.gtqtcore.api.unification.GTQTMaterials;

public class HigherDegreeMaterials {
    public static void register() {
        GTQTMaterials.Nitinol = new Material.Builder(20600, gregtechId("nitinol"))
                .ingot(3)
                .fluid()
                .color(0x8C6464).iconSet(METALLIC)
                .flags(EXT2_METAL, GENERATE_SMALL_GEAR, GENERATE_RING, GENERATE_FRAME, GENERATE_ROTOR, GENERATE_ROUND, GENERATE_FOIL, GENERATE_GEAR)
                .components(Nickel, 2, Titanium, 3)
                .rotorStats(15.0f, 7.0f, 3000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 2560, 3)
                        .attackSpeed(0.1F).enchantability(21).build())
                .blast(1300, BlastProperty.GasTier.LOW)
                .build();



    }
}
