package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.BlastProperty;
import gregtech.api.unification.material.properties.ToolProperty;
import keqing.gtqtcore.api.unification.GTQTElements;

import static gregtech.api.unification.material.Materials.NaquadahEnriched;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.util.GTUtility.gregtechId;

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
                .ingot().fluid()
                .color(0xE6E1FF)
                .flags(GENERATE_PLATE, GENERATE_FOIL)
                .element(GTQTElements.Li6)
                .build();

        Lithium7 = new Material.Builder(getMaterialsId(), gregtechId("lithium_7"))
                .ingot().fluid()
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

        Orichalcum = new Material.Builder(getMaterialsId(), gregtechId("orichalcum"))
                .ingot().fluid()
                .fluidPipeProperties(16000,32000,true)
                .color(0x72A0C1).iconSet(METALLIC)
                .flags(GENERATE_FOIL,GENERATE_DENSE,GENERATE_FINE_WIRE,GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR,GENERATE_ROTOR)
                .element(GTQTElements.Or)
                .toolStats(ToolProperty.Builder.of(7.0F, 25.0F, 17000, 22).build())
                .blast(9000, BlastProperty.GasTier.HIGH)
                .build();

        Vibranium = new Material.Builder(getMaterialsId(), gregtechId("vibranium"))
                .ingot().fluid().plasma()
                .color(0xC880FF).iconSet(SHINY)
                .flags(GENERATE_FOIL,GENERATE_DENSE,GENERATE_FINE_WIRE,GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR,GENERATE_ROTOR)
                .element(GTQTElements.Vb)
                .blast(4852, BlastProperty.GasTier.HIGH)
                .build();

        Adamantium = new Material.Builder(getMaterialsId(), gregtechId("adamantium"))
                .ingot().fluid().plasma()
                .color(0xFF0040).iconSet(METALLIC)
                .flags(GENERATE_FOIL,GENERATE_DENSE,GENERATE_FINE_WIRE,GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR,GENERATE_ROTOR)
                .element(GTQTElements.Ad)
                .blast(5225, BlastProperty.GasTier.HIGH)
                .build();

        Taranium = new Material.Builder(getMaterialsId(), gregtechId("taranium"))
                .dust()
                .color(0x4F404F).iconSet(METALLIC)
                .flags(GENERATE_FOIL,GENERATE_DENSE,GENERATE_FINE_WIRE,GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR,GENERATE_ROTOR)
                .element(GTQTElements.Tn)
                .build();

        //  10025 Tiberium
        Tiberium = new Material.Builder(getMaterialsId(), gregtechId("tiberium"))
                .ore(2, 2)
                .gem()
                .liquid()
                .addOreByproducts(NaquadahEnriched)
                .color(0x79B349)
                .iconSet(RUBY)
                .flags(GENERATE_FOIL,GENERATE_DENSE,GENERATE_FINE_WIRE,GENERATE_PLATE, GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR,GENERATE_ROTOR)
                .element(GTQTElements.Tu)
                .build();

    }
}
