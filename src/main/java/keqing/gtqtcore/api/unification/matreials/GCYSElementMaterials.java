package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.BlastProperty;
import gregtech.api.unification.material.properties.ToolProperty;
import keqing.gtqtcore.api.unification.GTQTElements;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLRemappingAdapter;

import static gregtech.api.unification.Elements.Hf;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.METALLIC;
import static gregtech.api.unification.material.info.MaterialIconSet.SHINY;
import static gregtech.api.util.GTUtility.gregtechId;
import static keqing.gtqtcore.api.unification.material.info.EPMaterialFlags.GENERATE_PELLETS;

public class GCYSElementMaterials {

    /**
     * 3100-3499
     */
    private static int startId = 3100;
    private static final int END_ID = startId + 400;

    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }
    public static void init() {
        Lithium6 = new Material.Builder(getMaterialsId(), gregtechId("lithium_6"))
                .ingot()
                .color(0xE6E1FF)
                .flags(GENERATE_PLATE, GENERATE_FOIL)
                .element(GTQTElements.Li6)
                .build();

        Lithium7 = new Material.Builder(getMaterialsId(), gregtechId("lithium_7"))
                .ingot()
                .color(0xE1DCFF).iconSet(METALLIC)
                .element(GTQTElements.Li7)
                .build();

        Beryllium7 = new Material.Builder(getMaterialsId(), gregtechId("beryllium_7"))
                .ingot().fluid()
                .color(0x6EBE6E)
                .element(GTQTElements.Be7)
                .build();

        Radium226 = new Material.Builder(getMaterialsId(), gregtechId("radium_226"))
                .ingot().fluid()
                .color(0xF0E68C)
                .element(GTQTElements.Ra226)
                .build();

        Carbon16 = new Material.Builder(getMaterialsId(), gregtechId("carbon_16"))
                .ingot().fluid()
                .color(0x1F1F1F)
                .element(GTQTElements.Carbon16)
                .build();

        Uranium233 = new Material.Builder(getMaterialsId(), gregtechId("uranium_233"))
                .ingot().fluid()
                .color(0x3B3B3B)
                .flags(GENERATE_PELLETS)
                .element(GTQTElements.Uranium233)
                .build();

        Uranium234 = new Material.Builder(getMaterialsId(), gregtechId("uranium_234"))
                .ingot().fluid()
                .color(0x90EE90)
                .flags(GENERATE_PELLETS)
                .element(GTQTElements.Uranium234)
                .build();

        Uranium236 = new Material.Builder(getMaterialsId(), gregtechId("uranium_236"))
                .ingot().fluid()
                .color(0x8FBC8F)
                .flags(GENERATE_PELLETS)
                .element(GTQTElements.Uranium236)
                .build();

        Neptunium236 = new Material.Builder(getMaterialsId(), gregtechId("neptunium_236"))
                .ingot().fluid()
                .color(0x7D26CD)
                .flags(GENERATE_PELLETS)
                .element(GTQTElements.Neptunium236)
                .build();

        Plutonium238 = new Material.Builder(getMaterialsId(), gregtechId("plutonium_238"))
                .ingot().fluid()
                .color(0xB22222)
                .flags(GENERATE_PELLETS)
                .element(GTQTElements.Plutonium238)
                .build();

        Plutonium242 = new Material.Builder(getMaterialsId(), gregtechId("plutonium_242"))
                .ingot().fluid()
                .color(0xCD3333)
                .flags(GENERATE_PELLETS)
                .element(GTQTElements.Plutonium242)
                .build();

        Americium241 = new Material.Builder(getMaterialsId(), gregtechId("americium_241"))
                .ingot().fluid()
                .color(0x00868B)
                .flags(GENERATE_PELLETS)
                .element(GTQTElements.Americium241)
                .build();

        Americium242 = new Material.Builder(getMaterialsId(), gregtechId("americium_242"))
                .ingot().fluid()
                .color(0x008B45)
                .flags(GENERATE_PELLETS)
                .element(GTQTElements.Americium242)
                .build();

        Americium243 = new Material.Builder(getMaterialsId(), gregtechId("americium_243"))
                .ingot().fluid()
                .color(0x006400)
                .flags(GENERATE_PELLETS)
                .element(GTQTElements.Americium243)
                .build();

        Curium243 = new Material.Builder(getMaterialsId(), gregtechId("curium_243"))
                .ingot().fluid()
                .color(0x0000FF)
                .flags(GENERATE_PELLETS)
                .element(GTQTElements.Curium243)
                .build();

        Curium245 = new Material.Builder(getMaterialsId(), gregtechId("curium_245"))
                .ingot().fluid()
                .color(0x0000EE)
                .flags(GENERATE_PELLETS)
                .element(GTQTElements.Curium245)
                .build();

        Curium246 = new Material.Builder(getMaterialsId(), gregtechId("curium_246"))
                .ingot().fluid()
                .color(0x0000CD)
                .flags(GENERATE_PELLETS)
                .element(GTQTElements.Curium246)
                .build();

        Curium247 = new Material.Builder(getMaterialsId(), gregtechId("curium_247"))
                .ingot().fluid()
                .color(0x0000AA)
                .flags(GENERATE_PELLETS)
                .element(GTQTElements.Curium247)
                .build();

        Orichalcum = new Material.Builder(getMaterialsId(), gregtechId("orichalcum"))
                .ingot().fluid()
                .fluidPipeProperties(16000,32000,true)
                .color(0x72A0C1).iconSet(METALLIC)
                .flags(GENERATE_PLATE, GENERATE_ROTOR, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, GENERATE_FRAME)
                .element(GTQTElements.Or)
                .toolStats(ToolProperty.Builder.of(7.0F, 25.0F, 17000, 22).build())
                .blast(9000, BlastProperty.GasTier.HIGH)
                .build();

        Vibranium = new Material.Builder(getMaterialsId(), gregtechId("vibranium"))
                .ingot().fluid().plasma()
                .color(0xC880FF).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_FOIL)
                .element(GTQTElements.Vb)
                .blast(4852, BlastProperty.GasTier.HIGH)
                .build();

        Adamantium = new Material.Builder(getMaterialsId(), gregtechId("adamantium"))
                .ingot().fluid().plasma()
                .color(0xFF0040).iconSet(METALLIC)
                .flags(GENERATE_PLATE, GENERATE_ROTOR, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND)
                .element(GTQTElements.Ad)
                .blast(5225, BlastProperty.GasTier.HIGH)
                .build();

        Taranium = new Material.Builder(getMaterialsId(), gregtechId("taranium"))
                .dust()
                .color(0x4F404F).iconSet(METALLIC)
                .element(GTQTElements.Tn)
                .build();

    }
}
