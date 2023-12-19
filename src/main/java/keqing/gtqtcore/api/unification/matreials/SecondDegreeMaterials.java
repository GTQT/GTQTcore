package keqing.gtqtcore.api.unification.matreials;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;
import keqing.gtqtcore.api.unification.GTQTElements;
import keqing.gtqtcore.api.unification.GTQTMaterials;

import static gregtech.api.unification.material.Materials.*;

import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.util.GTUtility.gregtechId;


public class SecondDegreeMaterials {
    private static int startId = 20300;
    private static final int END_ID = startId + 100;

    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }
    
    public SecondDegreeMaterials() {
    }
    public static void register() {

        GTQTMaterials.PineOil = new Material.Builder(getMaterialsId(), gregtechId( "pine_oil"))
                .fluid()
                .color(0xd6ac37)
                .build();

        GTQTMaterials.Periodicium = new Material.Builder(getMaterialsId(), gregtechId("periodicium"))
                .color(0x3D4BF6)
                .ingot(6)
                .iconSet(MaterialIconSet.SHINY)
                .blast(13500)
                .components(Hydrogen,1, Helium,1, Lithium,1, Beryllium,1,
                        Boron,1, Carbon,1, Nitrogen,1, Oxygen,1,
                        Fluorine,1, Neon,1, Sodium,1, Magnesium,1,
                        Aluminium,1, Silicon,1, Phosphorus,1, Sulfur,1,
                        Chlorine,1, Argon,1, Potassium,1, Calcium,1,
                        Scandium,1, Titanium,1, Vanadium,1, Chrome,1,
                        Manganese,1, Iron,1, Cobalt,1, Nickel,1,
                        Copper,1, Zinc,1, Gallium,1, Germanium,1,
                        Arsenic,1, Selenium,1, Bromine,1, Krypton,1,
                        Rubidium,1, Strontium,1, Yttrium,1, Zirconium,1,
                        Niobium,1, Molybdenum,1, Technetium,1, Ruthenium,1,
                        Rhodium,1, Palladium,1, Silver,1, Cadmium,1,
                        Indium,1, Tin,1, Antimony,1, Tellurium,1,
                        Iodine,1, Xenon,1, Caesium,1, Barium,1,
                        Lanthanum,1, Cerium,1, Praseodymium,1, Neodymium,1,
                        Promethium,1, Samarium,1, Europium,1, Gadolinium,1,
                        Terbium,1, Dysprosium,1, Holmium,1, Erbium,1,
                        Thulium,1, Ytterbium,1, Lutetium,1, Hafnium,1,
                        Tantalum,1, Tungsten,1, Rhenium,1, Osmium,1,
                        Iridium,1, Platinum,1, Gold,1, Mercury,1,
                        Thallium,1, Lead,1, Bismuth,1, Polonium,1,
                        Astatine,1, Radon,1, Francium,1, Radium,1,
                        Actinium,1, Thorium,1, Protactinium,1, Uranium235,1,
                        Neptunium,1, Plutonium239,1, Americium,1, Curium,1,
                        Berkelium,1, Californium,1, Einsteinium,1, Fermium,1,
                        Mendelevium,1, Nobelium,1, Lawrencium,1, Rutherfordium,1,
                        Dubnium,1, Seaborgium,1, Bohrium,1, Hassium,1,
                        Meitnerium,1, Darmstadtium,1, Roentgenium,1, Copernicium,1,
                        Nihonium,1, Flerovium,1, Moscovium,1, Livermorium, 1,Tennessine,1,
                        Oganesson,1)
                .build();

        GTQTMaterials.AlmandineFront  = new Material.Builder(getMaterialsId(), gregtechId("almandine_front")).fluid().color(0xD70505).build();
        GTQTMaterials.PentlanditeFront  = new Material.Builder(getMaterialsId(), gregtechId("pentlandite_front")).fluid().color(0x8c800a).build();
        GTQTMaterials.ChalcopyriteFront  = new Material.Builder(getMaterialsId(), gregtechId( "chalcopyrite_front")).fluid().color(0x896826).build();
        GTQTMaterials.GrossularFront  = new Material.Builder(getMaterialsId(), gregtechId( "grossular_front")).fluid().color(0xab5860).build();
        GTQTMaterials.MonaziteFront  = new Material.Builder(getMaterialsId(), gregtechId("monazite_front")).fluid().color(0x2e3f2e).build();
        GTQTMaterials.NickelFront  = new Material.Builder(getMaterialsId(), gregtechId("nickel_front")).fluid().color(0xABABD5).build();
        GTQTMaterials.PlatinumFront  = new Material.Builder(getMaterialsId(), gregtechId( "platinum_front")).fluid().color(0xe2e2b2).build();
        GTQTMaterials.PyropeFront  = new Material.Builder(getMaterialsId(), gregtechId("pyrope_front")).fluid().color(0x682E57).build();
        GTQTMaterials.RedstoneFront  = new Material.Builder(getMaterialsId(), gregtechId( "redstone_front")).fluid().color(0xAC0505).build();
        GTQTMaterials.SpessartineFront  = new Material.Builder(getMaterialsId(), gregtechId( "spessartine_front")).fluid().color(0XDF5A5A).build();
        GTQTMaterials.SphaleriteFront  = new Material.Builder(getMaterialsId(), gregtechId( "sphalerite_front")).fluid().color(0xD9D9D9).build();


        GTQTMaterials.MetallicHydrogen = new Material.Builder(getMaterialsId(), gregtechId("metallic_hydrogen"))
                .ingot().fluid()
                .iconSet(MaterialIconSet.SHINY)
                .flags(GENERATE_PLATE, GENERATE_RING, GENERATE_ROUND, GENERATE_ROTOR, GENERATE_BOLT_SCREW, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_LONG_ROD, GENERATE_FRAME)
                .color(0x4682B4)
                .fluidPipeProperties(10240, 32000, true, true, true, true)
                .components(Hydrogen, 1)
                .build();

        GTQTMaterials.Ethylenimine = new Material.Builder(getMaterialsId(), gregtechId( "ethylenimine"))
                .fluid()
                .color(0x483D8B)
                .components(Carbon, 2, Hydrogen, 5, Nitrogen, 1)
                .build();

        GTQTMaterials.Polyethyleneimine = new Material.Builder(getMaterialsId(), gregtechId("polyethylenimine"))
                .fluid()
                .color(0x483DB4)
                .flags(DISABLE_DECOMPOSITION)
                .components(Carbon, 2, Hydrogen, 5, Nitrogen, 1)
                .build();

        GTQTMaterials.RedMud = new Material.Builder(getMaterialsId(), gregtechId("red_mud"))
                .fluid()
                .color(0x483DB4)
                .flags(DISABLE_DECOMPOSITION)
                .components(Rutile, 1, HydrochloricAcid, 2)
                .build();

        GTQTMaterials.CarbenDisulfide  = new Material.Builder(getMaterialsId(), gregtechId("carbon_disulfide"))
                .fluid()
                .colorAverage()
                .components(Carbon, 1, Sulfur, 2)
                .build();

    }
}
