package keqing.gtqtcore.api.unification.matreials;
import gregtech.api.GTValues;
import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.BlastProperty;
import gregtech.api.unification.material.properties.ToolProperty;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static gregtech.api.util.GTUtility.gregtechId;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;

import gregtech.api.unification.material.Material;
import keqing.gtqtcore.api.unification.GTQTMaterials;

public class HigherDegreeMaterials {

    private static int startId = 20600;
    private static final int END_ID = startId + 300;

    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static void register() {
        GTQTMaterials.Nitinol = new Material.Builder(getMaterialsId(), gregtechId("nitinol"))
                .ingot(3)
                .fluid()
                .color(0x4876FF).iconSet(METALLIC)
                .flags(EXT2_METAL, GENERATE_SMALL_GEAR, GENERATE_RING, GENERATE_FRAME, GENERATE_ROTOR, GENERATE_ROUND, GENERATE_FOIL, GENERATE_GEAR)
                .components(Nickel, 2, Titanium, 3)
                .rotorStats(15.0f, 7.0f, 3000)
                .toolStats(ToolProperty.Builder.of(7.0F, 6.0F, 5120, 3)
                        .attackSpeed(0.1F).enchantability(21).build())
                .blast(1300, BlastProperty.GasTier.LOW)
                .build();


        //贫硫化金矿石
        GTQTMaterials.LeanGoldSulphide= new Material.Builder(getMaterialsId(), gregtechId("lean_gold_sulphide"))
                .ore(1,1)
                .dust().fluid()
                .color(0x8B8B00)
                .flags(DISABLE_DECOMPOSITION)
                .components(Gold, 1, Copper, 1,Sulfur,2,Oxygen,3)
                .build();
        //含金多金属矿石
        GTQTMaterials.RichGoldSulphide= new Material.Builder(getMaterialsId(), gregtechId("rich_gold_sulphide"))
                .ore()
                .dust().fluid()
                .color(0xCDCD00)
                .flags(DISABLE_DECOMPOSITION)
                .components(Gold, 1, Copper, 1,Iron,1,Sulfur,4,Oxygen,1)
                .build();

        // 褐煤
        GTQTMaterials.Lignite = new Material.Builder(getMaterialsId(), gregtechId("lignite"))
                .gem(1, 1600).ore(2, 1) // default coal burn time in vanilla
                .color(0x8B5A00).iconSet(LIGNITE)
                .flags(FLAMMABLE, NO_SMELTING, NO_SMASHING, MORTAR_GRINDABLE, EXCLUDE_BLOCK_CRAFTING_BY_HAND_RECIPES,
                        DISABLE_DECOMPOSITION)
                .components(Carbon, 1)
                .build();


        // 深红银矿
        GTQTMaterials.Pyrargyrite= new Material.Builder(getMaterialsId(), gregtechId("pyrargyrite"))
                .ore(true)
                .dust().fluid()
                .color(0x8B4726)
                .components(Silver, 3,Antimony,1,Sulfur,3)
                .build();
        // 锌锑方辉银矿
        GTQTMaterials.Zincantimonygalvanite= new Material.Builder(getMaterialsId(), gregtechId("zincantimonygalvanite"))
                .ore(true)
                .dust().fluid()
                .color(0x8B2252)
                .components(Silver, 2,Zinc,1,Sulfur,2,Oxygen,1)
                .build();
        //铬铅矿
        GTQTMaterials.Crocoite= new Material.Builder(getMaterialsId(), gregtechId("crocoite"))
                .ore(true)
                .dust().fluid()
                .color(0x321452)
                .components(Lead,1,Chromite,1,Oxygen,4)
                .build();
        // 冰晶石
        GTQTMaterials.Cryolite= new Material.Builder(getMaterialsId(), gregtechId("cryolite"))
                .ore(true).dust()
                .color(0x98F5FF)
                .components(Sodium,3,Aluminium,1,Fluorine,6)
                .build();

        // 神秘六要素

        // 磷铝锂石
        GTQTMaterials.Amblygonite= new Material.Builder(getMaterialsId(), gregtechId("amblygonite"))
                .ore()
                .dust().fluid()
                .color(0xA4D3EE)
                .components(Lithium,1,Sodium,1,Aluminium,1,Phosphate,1,Hydrogen,1)
                .build();

        // 琥珀
        GTQTMaterials.Amber= new Material.Builder(getMaterialsId(), gregtechId("amber"))
                .ore(true)
                .dust().fluid()
                .color(0x8B4C39)
                .components(Carbon,10,Hydrogen,16,Oxygen,1)
                .build();

        //方钍石
        GTQTMaterials.Thorianite= new Material.Builder(getMaterialsId(), gregtechId("thorianite"))
                .ore(true).dust()
                .color(0x8FBC8F)
                .flags(DISABLE_DECOMPOSITION)
                .components(Thorium,1,Oxygen,2)
                .build();

        //氧化硅岩
        GTQTMaterials.Yanghuaguiyan= new Material.Builder(getMaterialsId(), gregtechId("yanghuaguiyan"))
                .ore(true).dust()
                .flags(DISABLE_DECOMPOSITION)
                .color(0x636363)
                .components(Naquadah,1,Oxygen,2)
                .build();

        //钠硼解石
        GTQTMaterials.Ulexite= new Material.Builder(getMaterialsId(), gregtechId("ulexite"))
                .ore().dust().fluid()
                .color(0xFFFAFA)
                .components(Sodium,1,Calcium,1,Borax,1, Oxygen, 2, Hydrogen, 2,Water,6)
                .build();

        //bittern 盐卤
        GTQTMaterials.Bittern= new Material.Builder(getMaterialsId(), gregtechId("bittern"))
                .fluid()
                .color(0xFFFAFA)
                .build();

        //氯化物型卤水
        GTQTMaterials.Bitterncl= new Material.Builder(getMaterialsId(), gregtechId("bitterncl"))
                .fluid()
                .color(0xEE5C42)
                .build();
        //硫酸盐型卤水
        GTQTMaterials.Bitternso= new Material.Builder(getMaterialsId(), gregtechId("bitternso"))
                .fluid()
                .color(0xCD69C9)
                .build();
        //碳酸盐型卤水
        GTQTMaterials.Bitternco= new Material.Builder(getMaterialsId(), gregtechId("bitternco"))
                .fluid()
                .color(0xB3EE3A)
                .build();

        //硫酸酸化卤水
        GTQTMaterials.Bitterns= new Material.Builder(getMaterialsId(), gregtechId("bitterns"))
                .fluid()
                .color(0xEE3A8C)
                .build();

        //氧化卤水
        GTQTMaterials.Bitterno= new Material.Builder(getMaterialsId(), gregtechId("bitterno"))
                .fluid()
                .color(0xDEB887)
                .build();

        //离散态氧化溴
        GTQTMaterials.Bitternbr= new Material.Builder(getMaterialsId(), gregtechId("bitternbr"))
                .fluid()
                .color(0xD02090)
                .build();

        //氧化富集溴
        GTQTMaterials.Bitternobr= new Material.Builder(getMaterialsId(), gregtechId("bitternobr"))
                .fluid()
                .color(0xCD1076)
                .build();

        // 可燃冰
        GTQTMaterials.Gashydrate = new Material.Builder(getMaterialsId(), gregtechId("gashydrate"))
                .gem(1, 128000).ore(2, 1) // default coal burn time in vanilla
                .color(0xE0FFFF).iconSet(LIGNITE)
                .flags(FLAMMABLE, NO_SMELTING, NO_SMASHING, MORTAR_GRINDABLE, EXCLUDE_BLOCK_CRAFTING_BY_HAND_RECIPES,
                        DISABLE_DECOMPOSITION)
                .components(Methane,576,Water,11520)
                .build();
        
        //超导
        LVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("lv_superconductor"))
                .ingot().liquid()
                .color(0xf8f8ff)
                .iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION)
                .components(SolderingAlloy,10,Gallium,2)
                .cableProperties(GTValues.V[GTValues.LV],8,0,true)
                .build();

        MVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("mv_superconductor"))
                .ingot().liquid()
                .color(MagnesiumDiboride .getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.MV], 16,0,true)
                .components(MagnesiumDiboride ,1)
                .build();

        HVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("hv_superconductor"))
                .ingot().liquid()
                .color(MercuryBariumCalciumCuprate .getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.HV], 16,0,true)
                .components(MercuryBariumCalciumCuprate ,1)
                .build();

        EVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("ev_superconductor"))
                .ingot().liquid()
                .color(UraniumTriplatinum .getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.EV], 24,0,true)
                .components(UraniumTriplatinum ,1)
                .build();

        IVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("iv_superconductor"))
                .ingot().liquid()
                .color(SamariumIronArsenicOxide .getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.IV], 24,0,true)
                .components(SamariumIronArsenicOxide ,1)
                .build();

        LuVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("luv_superconductor"))
                .ingot().liquid()
                .color(IndiumTinBariumTitaniumCuprate .getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(IndiumTinBariumTitaniumCuprate ,1)
                .cableProperties(GTValues.V[GTValues.LuV], 32,0,true)
                .build();

        ZPMSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("zpm_superconductor"))
                .ingot().liquid()
                .color(UraniumRhodiumDinaquadide .getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.ZPM], 32,0,true)
                .components(UraniumRhodiumDinaquadide ,1)
                .build();

        UVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("uv_superconductor"))
                .ingot().liquid()
                .color(EnrichedNaquadahTriniumEuropiumDuranide .getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[UV], 64,0,true)
                .components(EnrichedNaquadahTriniumEuropiumDuranide ,1)
                .build();

        UHVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("uhv_superconductor"))
                .ingot().liquid()
                .color(RutheniumTriniumAmericiumNeutronate .getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.UHV], 96,0,true)
                .components(RutheniumTriniumAmericiumNeutronate ,1)
                .build();
/*
        UEVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("uev_superconductor"))
                .ingot().liquid()
                .color(UEVSuperconductorBase.getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.UEV], 8,0,true)
                .components(UEVSuperconductorBase,1)
                .build();

        UIVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("uiv_superconductor"))
                .ingot().liquid()
                .color(UIVSuperconductorBase.getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.UIV], 16,0,true)
                .components(UIVSuperconductorBase,1)
                .build();

        UXVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("uxv_superconductor"))
                .ingot().liquid()
                .color(UXVSuperconductorBase.getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.UXV], 16,0,true)
                .components(UXVSuperconductorBase,1)
                .build();

        OpVSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("opv_superconductor"))
                .ingot().liquid()
                .color(OpVSuperconductorBase.getMaterialRGB())
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[OpV], 16,0,true)
                .components(OpVSuperconductorBase,1)
                .build();

        MAXSuperconductor = new Material.Builder(getMaterialsId(), gregtechId("max_superconductor"))
                .ingot().liquid()
                .color(0XFFFFFF)
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .cableProperties(GTValues.V[GTValues.MAX], 32,0,true)
                .components(OpVSuperconductorBase,1)
                .build();
*/

        getMaterialsId();
        getMaterialsId();
        getMaterialsId();
        getMaterialsId();
        getMaterialsId();
        //高级矿物

    }
}
