package keqing.gtqtcore.api.unification.matreials;

import gregicality.science.api.unification.GCYSElements;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.BlastProperty;
import gregtech.api.unification.material.properties.ToolProperty;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLRemappingAdapter;

import static gregtech.api.unification.Elements.Hf;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.METALLIC;
import static gregtech.api.unification.material.info.MaterialIconSet.SHINY;
import static gregtech.api.util.GTUtility.gregtechId;

public class GCYSElementMaterials {

    /**
     * 3100-3499
     */
    public static void init() {
        Lithium6 = new Material.Builder(3100, gregtechId("lithium_6"))
                .ingot()
                .color(0xE6E1FF)
                .flags(GENERATE_PLATE, GENERATE_FOIL)
                .element(GCYSElements.Li6)
                .build();

        Lithium7 = new Material.Builder(3101, gregtechId("lithium_7"))
                .ingot()
                .color(0xE1DCFF).iconSet(METALLIC)
                .element(GCYSElements.Li7)
                .build();

        Beryllium7 = new Material.Builder(3102, gregtechId("beryllium_7"))
                .ingot().fluid()
                .color(0x6EBE6E)
                .element(GCYSElements.Be7)
                .build();

        Orichalcum = new Material.Builder(3103, gregtechId("orichalcum"))
                .ingot().fluid()
                .fluidPipeProperties(16000,32000,true)
                .color(0x72A0C1).iconSet(METALLIC)
                .flags(GENERATE_PLATE, GENERATE_ROTOR, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_FRAME)
                .element(GCYSElements.Or)
                .toolStats(ToolProperty.Builder.of(7.0F, 25.0F, 17000, 22).build())
                .blast(9000, BlastProperty.GasTier.HIGH)
                .build();

        Vibranium = new Material.Builder(3104, gregtechId("vibranium"))
                .ingot().fluid().plasma()
                .color(0xC880FF).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_FOIL)
                .element(GCYSElements.Vb)
                .blast(4852, BlastProperty.GasTier.HIGH)
                .build();

        Adamantium = new Material.Builder(3105, gregtechId("adamantium"))
                .ingot().fluid().plasma()
                .color(0xFF0040).iconSet(METALLIC)
                .flags(GENERATE_PLATE, GENERATE_ROTOR, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND)
                .element(GCYSElements.Ad)
                .blast(5225, BlastProperty.GasTier.HIGH)
                .build();

        Taranium = new Material.Builder(3106, gregtechId("taranium"))
                .dust()
                .color(0x4F404F).iconSet(METALLIC)
                .element(GCYSElements.Tn)
                .build();

    }
}
