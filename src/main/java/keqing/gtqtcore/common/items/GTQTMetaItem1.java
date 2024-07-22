package keqing.gtqtcore.common.items;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.ElectricStats;
import gregtech.api.items.metaitem.StandardMetaItem;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.common.items.behaviors.MultiblockBuilderBehavior;
import gregtech.common.items.behaviors.ProspectorScannerBehavior;
import gregtech.common.items.behaviors.TooltipBehavior;
import keqing.gtqtcore.api.unification.GCYSMaterials;
import keqing.gtqtcore.common.CommonProxy;
import keqing.gtqtcore.common.block.blocks.GTQTCrops;
import keqing.gtqtcore.common.items.behaviors.*;
import net.minecraft.client.resources.I18n;


import static gregtech.api.GTValues.M;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.BET4;


public class GTQTMetaItem1 extends StandardMetaItem {

    public GTQTMetaItem1() {
        this.setRegistryName("gtqt_meta_item_1");
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
    }



    public void registerSubItems() {



        //基础材料
        IMPREGNATED_GRAPHITE_RODSA = this.addItem(1,"item.impregnated_graphite_rodsa").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_CH);
        IMPREGNATED_GRAPHITE_RODS = this.addItem(2,"item.impregnated_graphite_rods").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_CH);
        IMPREGNATED_SUBSTRATE = this.addItem(3,"item.impregnated_substrate").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_CH);
        IMPREGNATED_PLASTIC_SUBSTRATE = this.addItem(4,"item.impregnated_plastic_substrate").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_CH);
        IMPREGNATED_EPOXY = this.addItem(5,"item.impregnated_epoxy").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_CH);
        IMPREGNATED_FIBER = this.addItem(6,"item.impregnated_fiber").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_CH);
        IMPREGNATED_MULTILAYER_FIBER = this.addItem(7,"item.impregnated_multilayer_fiber").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_CH);
        INSULATINGMICA = this.addItem(15,"item.insulatingmica").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_CH);
        LAMINATION_GE = this.addItem(16,"item.lamination_ge").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_CH);
        LAMINATION_IR = this.addItem(17,"item.insulating_ir").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_CH);

        //其他燃料
        JIAO_XIAN_REN_ZHANG = this.addItem(20,"fuel.jiaoxianrenzhang").setBurnValue(9600).setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        NONG_SUO_XIAN_REN_ZHANG = this.addItem(21,"fuel.nongsuoxianrenzhang").setBurnValue(16000).setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        JIAO_TANG_JIAO = this.addItem(22,"fuel.tangjiao").setBurnValue(9600).setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        NONG_SUO_TANG_JIAO = this.addItem(23,"fuel.tangtan").setBurnValue(16000).setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        ZHU_TAN = this.addItem(24,"fuel.zhutan").setBurnValue(12800).setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);

        //快乐海藻
        COMMON_ALGAE = this.addItem(30,"algae.common").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        GREEN_ALGAE = this.addItem(31,"algae.green").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        RED_ALGAE = this.addItem(32,"algae.red").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        BROWN_ALGAE = this.addItem(33,"algae.brown").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        GOLD_ALGAE = this.addItem(34,"algae.gold").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        T_ALGAE = this.addItem(35,"algae.t").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        BIOLOGY_INTEGRATED_CIRCUIT = this.addItem(40, "item.biology_integrated_circuit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO).addComponents(new IntBcircuitBehavior());
        ROUGH_BIOLOGY_RESIN=this.addItem(41, "item.biology_resin").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        PURIFIED_ALUMINIUM_MIXTURE=this.addItem(42, "item.aluminium_mixture").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        CELLULOSE_PULP=this.addItem(43, "item.cellulose_pulp").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        CELLULOSE_FIBER=this.addItem(44, "item.cellulose_fiber_green").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        CELLULOSE_FIBER_RED=this.addItem(45, "item.cellulose_fiber_red").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        CELLULOSE_FIBER_YELLOW=this.addItem(46, "item.cellulose_fiber_yellow").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        PINE_CONE=this.addItem(47, "item.pine_cone").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        PINE_FRAGMENT=this.addItem(48, "item.pine_fragment").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        COMPOST=this.addItem(49, "item.compost").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        ALGAE_ACID=this.addItem(50, "item.algae_acid").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        WOOD_PELLETS=this.addItem(51, "item.wood_pellets").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        PELLETS_MOULD=this.addItem(52, "item.pellets_mould").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        ALUMINIUM_PELLETS=this.addItem(53, "item.aluminium_pellets").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        POTASSIUM_ETHYLATE=this.addItem(54, "item.potassium.ethylate").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        POTASSIUM_ETHYLXANTHATE=this.addItem(55, "item.potassium.ethylxanthate").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        SODIUM_ETHYLXANTHATE=this.addItem(56, "item.sodium.ethylxanthate").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        SODIUM_ETHYLATE=this.addItem(57, "item.sodium.ethylate").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_BIO);

        RETICLE_ADVANCED_SYSTEM_ON_CHIP=this.addItem(58, "reticle.advanced_system_on_chip").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_GD);
        RETICLE_CENTRAL_PROCESSING_UNIT=this.addItem(59, "reticle.central_processing_unit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_GD);
        RETICLE_HIGH_POWER_INTEGRATED_CIRCUIT=this.addItem(60, "reticle.high_power_integrated_circuit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_GD);
        RETICLE_HIGHLY_ADVANCED_SYSTEM_ON_CHIP=this.addItem(61, "reticle.highly_advanced_system_on_chip").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_GD);
        RETICLE_INTEGRATED_LOGIC_CIRCUIT=this.addItem(62, "reticle.integrated_logic_circuit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_GD);
        RETICLE_LOW_POWER_INTEGRATED_CIRCUIT=this.addItem(63, "reticle.low_power_integrated_circuit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_GD);
        RETICLE_NAND_MEMORY_CHIP=this.addItem(64, "reticle.nand_memory_chip").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_GD);
        RETICLE_NANO_CENTRAL_PROCESSING_UNIT=this.addItem(65, "reticle.nano_central_processing_unit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_GD);
        RETICLE_NOR_MEMORY_CHIP=this.addItem(66, "reticle.nor_memory_chip").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_GD);
        RETICLE_POWER_INTEGRATED_CIRCUIT=this.addItem(67, "reticle.power_integrated_circuit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_GD);
        RETICLE_QBIT_CENTRAL_PROCESSING_UNIT=this.addItem(68, "reticle.qbit_central_processing_unit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_GD);
        RETICLE_RANDOM_ACCESS_MEMORY=this.addItem(69, "reticle.random_access_memory").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_GD);
        RETICLE_SIMPLE_SYSTEM_ON_CHIP=this.addItem(70, "reticle.simple_system_on_chip").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_GD);
        RETICLE_SYSTEM_ON_CHIP=this.addItem(71, "reticle.system_on_chip").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_GD);
        RETICLE_ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT=this.addItem(72, "reticle.ultra_high_power_integrated_circuit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_GD);
        RETICLE_ULTRA_LOW_POWER_INTEGRATED_CIRCUIT=this.addItem(73, "reticle.ultra_low_power_integrated_circuit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_GD);
        RETICLE_SILICON=this.addItem(74, "reticle.silicon").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_GD);

        //AE
        //神秘硅束
        AE_SILICON = this.addItem(75, "ae.silicon").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_NE);
        //神秘硅晶
        AE_WAFER=this.addItem(76, "ae.wafer").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_NE);
        //光掩模*3
        AE_RETICLEA=this.addItem(77, "ae.reticlea").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_NE);
        AE_RETICLEB=this.addItem(78, "ae.reticleb").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_NE);
        AE_RETICLEC=this.addItem(79, "ae.reticlec").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_NE);
        //掺杂晶圆*3
        AE_WAFERA=this.addItem(80, "ae.wafera").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_NE);
        AE_WAFERB=this.addItem(81, "ae.waferb").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_NE);
        AE_WAFERC=this.addItem(82, "ae.waferc").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_NE);
        //蚀刻后*3
        AE_A=this.addItem(83, "ae.a").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_NE);
        AE_B=this.addItem(84, "ae.b").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_NE);
        AE_C=this.addItem(85, "ae.c").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_NE);
        //fluix束能器
        AE_FLUIX_FIRM=this.addItem(86, "ae.fluix_firm").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_NE);
        //光伏板
        SOLAR_PLATE_MKI=this.addItem(90, "solar_plate_mki").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SOLAR_PLATE_MKII=this.addItem(91, "solar_plate_mkii").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SOLAR_PLATE_MKIII=this.addItem(92, "solar_plate_mkiii").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);

        TIME_BOTTLE = this.addItem(97, "time_bottle").setMaxStackSize(1).addComponents(new TimeBottleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        WATER_CLEAR = this.addItem(96, "water_clear").setMaxStackSize(1).addComponents(new WaterClearBehavior()).setCreativeTabs(CommonProxy.GTQTCore_TAB);

         //电池
        BATTERY_NIMH_EMPTY = addItem(100, "nickel.metal.hydride.battery.empty").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        BATTERY_SMALL_LITHIUM_ION_EMPTY = addItem(102, "small.lithium.ion.battery.empty").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        BATTERY_MEDIUM_LITHIUM_ION_EMPTY = addItem(104, "medium.lithium.ion.battery.empty").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        BATTERY_LARGE_LITHIUM_ION_EMPTY = addItem(106, "large.lithium.ion.battery.empty").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        BATTERY_SMALL_LIS_EMPTY = addItem(108, "small.lithium.sulfide.battery.empty").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        BATTERY_MEDIUM_LIS_EMPTY = addItem(110, "medium.lithium.sulfide.battery.empty").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        BATTERY_LARGE_LIS_EMPTY = addItem(112, "large.lithium.sulfide.battery.empty").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        BATTERY_SMALL_FLUORIDE_EMPTY = addItem(114, "small.fluoride.battery.empty").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        BATTERY_MEDIUM_FLUORIDE_EMPTY = addItem(116,"medium.fluoride.battery.empty").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        BATTERY_LARGE_FLUORIDE_EMPTY = addItem(118,"large.fluoride.battery.empty").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);

        HEAVY_METAL_ABSORBING_YARN = addItem(150, "heavy_metal_absorbing_yarn").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        URANIUM_SATURATED_YARN = addItem(151, "uranium_saturated_yarn").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        BORON_RETAINING_YARN = addItem(152, "boron_retaining_yarn").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        BORON_SATURATED_YARN = addItem(153, "boron_saturated_yarn").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        LITHIUM_SIEVE = addItem(154, "lithium_sieve").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        LITHIUM_SATURATED_LITHIUM_SIEVE = addItem(155, "lithium_saturated_lithium_sieve").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        ACRYLIC_YARN = addItem(156, "acrylic_yarn").setMaxStackSize(1).setCreativeTabs(CommonProxy.GTQTCore_TAB);

        //ROCK
        POS_ORE_CARD = this.addItem(180, "pos_ore_card").setMaxStackSize(1).addComponents(new OreCheckerBehavior()).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        BLANK = addItem(181, "rock.blank").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        HYDROTHERMAL = addItem(182, "rock.hydrothermal").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        MAGMATIC_HYDROTHERMAL = addItem(183, "rock.magmatic_hydrothermal").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        ALLUVIAL = addItem(184, "rock.alluvial").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        DIAPHRAGMATIC = addItem(185, "rock.diaphragmatic").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        ORTHOSTATIC = addItem(186, "rock.orthostatic").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        METAMORPHIC = addItem(187, "rock.metamorphic").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        END1 = addItem(188, "rock.end1").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        END2 = addItem(189, "rock.end2").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        END3 = addItem(190, "rock.end3").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        END4 = addItem(191, "rock.end4").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        BET1 = addItem(192, "rock.bet1").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        BET2 = addItem(193, "rock.bet2").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        BET3 = addItem(194, "rock.bet3").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        BET4 = addItem(195, "rock.bet4").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);

        NANOSILICON_CATHODE = addItem(199,"nanosilicon.cathode").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        //神秘农业
        COPPER_CROP = addItem(200, "copper_crop");
        COPPER_CROP.addComponents(new GTQTCropSeedBehaviour(GTQTCrops.COPPER_CROP, COPPER_CROP.getStackForm(), COPPER_CROP.getStackForm())).setCreativeTabs(CommonProxy.GTQTCore_BIO);

        IRON_CROP = addItem(201, "iron_crop");
        IRON_CROP.addComponents(new GTQTCropSeedBehaviour(GTQTCrops.IRON_CROP, IRON_CROP.getStackForm(), IRON_CROP.getStackForm())).setCreativeTabs(CommonProxy.GTQTCore_BIO);

        TIN_CROP = addItem(202, "tin_crop");
        TIN_CROP.addComponents(new GTQTCropSeedBehaviour(GTQTCrops.TIN_CROP, TIN_CROP.getStackForm(), TIN_CROP.getStackForm())).setCreativeTabs(CommonProxy.GTQTCore_BIO);

        BRONZE_CROP = addItem(203, "bronze_crop");
        BRONZE_CROP.addComponents(new GTQTCropSeedBehaviour(GTQTCrops.BRONZE_CROP, BRONZE_CROP.getStackForm(), BRONZE_CROP.getStackForm())).setCreativeTabs(CommonProxy.GTQTCore_BIO);

        CARBON_CROP = addItem(204, "carbon_crop");
        CARBON_CROP.addComponents(new GTQTCropSeedBehaviour(GTQTCrops.CARBON_CROP, CARBON_CROP.getStackForm(), CARBON_CROP.getStackForm())).setCreativeTabs(CommonProxy.GTQTCore_BIO);

        GOLD_CROP = addItem(205, "gold_crop");
        GOLD_CROP.addComponents(new GTQTCropSeedBehaviour(GTQTCrops.GOLD_CROP, GOLD_CROP.getStackForm(), GOLD_CROP.getStackForm())).setCreativeTabs(CommonProxy.GTQTCore_BIO);

        //催化剂框架 6种绰绰有余
        CATALYST_FRAMEWORK_I = this.addItem(250,"catalyst_framework_i").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_CH);
        CATALYST_FRAMEWORK_II = this.addItem(251,"catalyst_framework_ii").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_CH);
        CATALYST_FRAMEWORK_III = this.addItem(252,"catalyst_framework_iii").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_CH);
        CATALYST_FRAMEWORK_IV = this.addItem(253,"catalyst_framework_iv").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_CH);
        CATALYST_FRAMEWORK_V = this.addItem(254,"catalyst_framework_v").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_CH);
        CATALYST_FRAMEWORK_VI = this.addItem(255,"catalyst_framework_vi").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_CH);


        //
        CLEAN_CULTURE = addItem(276, "clean.culture");
        STERILIZED_PETRI_DISH = addItem(277, "sterilized.petri.dish");
        CONTAMINATED_PETRI_DISH = addItem(278, "contaminated.petri.dish");
        ELECTRICALLY_WIRED_PETRI_DISH = addItem(279, "electrically.wired.petri.dish");

        CUPRIVADUS_CULTURE = addItem(280, "cupriavidus.culture");
        SHEWANELLA_CULTURE = addItem(281, "shewanella.culture").setCreativeTabs(CommonProxy.GTQTCore_BIO);
        STREPTOCOCCUS_CULTURE = addItem(282, "streptococcus.culture").setCreativeTabs(CommonProxy.GTQTCore_BIO);
        ESCHERICHIA_CULTURE = addItem(283, "eschericia.culture").setCreativeTabs(CommonProxy.GTQTCore_BIO);
        BIFIDOBACTERIUM_CULTURE = addItem(284, "bifidobacterium.culture").setCreativeTabs(CommonProxy.GTQTCore_BIO);
        BREVIBACTERIUM_CULTURE = addItem(285, "brevibacterium.culture").setCreativeTabs(CommonProxy.GTQTCore_BIO);

        //快乐模具
        MOLD_GAS=this.addItem(300, "mold.gas").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        MOLD_MOTOR=this.addItem(301, "mold.motor").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);

        //阳光化合物们
        SUN_MATTER=this.addItem(320, "sun_matter").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_HP);
        SUNLINSE=this.addItem(321, "sunlinse").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_HP);

        SUNNARIUMPART=this.addItem(322, "sunnariumpart").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_HP);
        SUNNARIUM=this.addItem(323, "sunnarium").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_HP);
        SUNNARIUM_PLATE=this.addItem(324, "sunnarium_plate").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_HP);

        SUNNARIUM_ENRICHED=this.addItem(325, "sunnarium_enriched").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_HP);
        SUNNARIUM_ENRICHED_PLATE=this.addItem(326, "sunnarium_enriched_plate").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_HP);

        //升级
        ADV_ENERGY_STORAGE=this.addItem(350, "adv_energy_storage").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        ADV_CAPACITOR =this.addItem(351, "adv_capacitor").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        ADV_COMPONENT_VENT=this.addItem(352, "adv_component_vent").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        ADV_HEAT_EXCHANGE=this.addItem(353, "adv_heat_exchange").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        //磨球
        GRINDBALL_SOAPSTONE = this.addItem(391, "mill.grindball_soapstone").setMaxStackSize(1).addComponents(new MillBallBehavior()).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GRINDBALL_ALUMINIUM = this.addItem(392, "mill.grindball_aluminium").setMaxStackSize(1).addComponents(new MillBallBehavior()).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        // Simple Machine Crafting Components: ID 480-499
        COMPONENT_GRINDER_BORON_NITRIDE = this.addItem(393, "component.grinder.boron_nitride").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(GCYSMaterials.CubicBoronNitride, M * 4), new MaterialStack(GCYSMaterials.Vibranium, M * 8), new MaterialStack(GCYSMaterials.CubicHeterodiamond, M))).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        // Voltage Coils: ID 350-355
        VOLTAGE_COIL_UHV = this.addItem(394, "voltage_coil.uhv").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(GCYSMaterials.MercuryCadmiumTelluride, M * 2), new MaterialStack(GCYSMaterials.ChromiumGermaniumTellurideMagnetic, M / 2))).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        VOLTAGE_COIL_UEV = this.addItem(395, "voltage_coil.uev").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(GCYSMaterials.MercuryCadmiumTelluride, M * 2), new MaterialStack(GCYSMaterials.ChromiumGermaniumTellurideMagnetic, M / 2))).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        VOLTAGE_COIL_UIV = this.addItem(396, "voltage_coil.uiv").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(GCYSMaterials.MercuryCadmiumTelluride, M * 2), new MaterialStack(GCYSMaterials.ChromiumGermaniumTellurideMagnetic, M / 2))).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        VOLTAGE_COIL_UXV = this.addItem(397, "voltage_coil.uxv").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(GCYSMaterials.MercuryCadmiumTelluride, M * 2), new MaterialStack(GCYSMaterials.ChromiumGermaniumTellurideMagnetic, M / 2))).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        VOLTAGE_COIL_OpV = this.addItem(398, "voltage_coil.opv").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(GCYSMaterials.MercuryCadmiumTelluride, M * 2), new MaterialStack(GCYSMaterials.ChromiumGermaniumTellurideMagnetic, M / 2))).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        VOLTAGE_COIL_MAX = this.addItem(399, "voltage_coil.max").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(GCYSMaterials.MercuryCadmiumTelluride, M * 2), new MaterialStack(GCYSMaterials.ChromiumGermaniumTellurideMagnetic, M / 2))).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        //封装电路板
        WRAP_CIRCUIT_ULV = this.addItem(400, "wrap.circuit.ulv").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        WRAP_CIRCUIT_LV = this.addItem(401, "wrap.circuit.lv").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        WRAP_CIRCUIT_MV = this.addItem(402, "wrap.circuit.mv").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        WRAP_CIRCUIT_HV = this.addItem(403, "wrap.circuit.hv").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        WRAP_CIRCUIT_EV = this.addItem(404, "wrap.circuit.ev").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        WRAP_CIRCUIT_IV = this.addItem(405, "wrap.circuit.iv").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        WRAP_CIRCUIT_LuV = this.addItem(406, "wrap.circuit.luv").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        WRAP_CIRCUIT_ZPM = this.addItem(407, "wrap.circuit.zpm").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        WRAP_CIRCUIT_UV = this.addItem(408, "wrap.circuit.uv").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        WRAP_CIRCUIT_UHV = this.addItem(409, "wrap.circuit.uhv").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        WRAP_CIRCUIT_UEV = this.addItem(410, "wrap.circuit.uev").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        WRAP_CIRCUIT_UIV = this.addItem(411, "wrap.circuit.uiv").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        WRAP_CIRCUIT_UXV = this.addItem(412, "wrap.circuit.uxv").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        WRAP_CIRCUIT_OpV = this.addItem(413, "wrap.circuit.opv").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        WRAP_CIRCUIT_MAX = this.addItem(414, "wrap.circuit.max").setCreativeTabs(CommonProxy.GTQTCore_TAB);

        PROSPECTOR_UV = addItem(415, "prospector.uv")
                .addComponents(ElectricStats.createElectricItem(16_000_000_000L, GTValues.UV),
                        new ProspectorScannerBehavior(7, GTValues.UV))
                .setMaxStackSize(1)
                .setCreativeTabs(CommonProxy.GTQTCore_TO);

        PROSPECTOR_UV = addItem(416, "prospector.uiv")
                .addComponents(ElectricStats.createElectricItem(256_000_000_000L, GTValues.UIV),
                        new ProspectorScannerBehavior(9, GTValues.UIV))
                .setMaxStackSize(1)
                .setCreativeTabs(CommonProxy.GTQTCore_TO);

        POS_BINDING_CARD = this.addItem(417, "pos_binding_card").setMaxStackSize(1).addComponents(new PosBindingCardBehaviors()).setCreativeTabs(CommonProxy.GTQTCore_TAB);

        DEBUG_STRUCTURE_WRITER = this.addItem(418, "debug.structure_writer").addComponents(StructureWriteBehavior.INSTANCE).setCreativeTabs(CommonProxy.GTQTCore_TO);
        DEBUG_STRUCTURE_BUILDER = this.addItem(419, "debug.structure_builder").addComponents(new MultiblockBuilderBehavior()).setCreativeTabs(CommonProxy.GTQTCore_TO);

        CARBON_ALLOTROPE_MIXTURE = this.addItem(420, "mixture.carbon_allotrope").setCreativeTabs(CommonProxy.GTQTCore_CH);
        GRAPHENE_ALIGNED_CNT = this.addItem(421, "cnt.graphene_aligned").setCreativeTabs(CommonProxy.GTQTCore_CH);

        BORON_NITRIDE_GRINDER = this.addItem(422, "grinder.boron_nitride").setCreativeTabs(CommonProxy.GTQTCore_HP);
        VACUUM_TUBE_COMPONENT = this.addItem(423, "component.vacuum_tube").setCreativeTabs(CommonProxy.GTQTCore_HP);
        SEPARATION_ELECTROMAGNET = this.addItem(424, "separation_electromagnet").setCreativeTabs(CommonProxy.GTQTCore_HP);
        PROTONATED_FULLERENE_SIEVING_MATRIX = this.addItem(425, "protonated_fullerene_sieving_matrix").setCreativeTabs(CommonProxy.GTQTCore_CH);
        SATURATED_FULLERENE_SIEVING_MATRIX = this.addItem( 426, "saturated_fullerene_sieving_matrix").setCreativeTabs(CommonProxy.GTQTCore_CH);
        METASTABLE_SELF_HEALING_ADHESIVE = this.addItem(427, "metastable_self_healing_adhesive").setCreativeTabs(CommonProxy.GTQTCore_HP);
        HYPERDIMENSIONAL_TACHYON_CONDENSED_MATTER = this.addItem(428, "hyperdimensional_tachyon_condensed_matter").setCreativeTabs(CommonProxy.GTQTCore_HP);
        UNSTABLE_STAR = this.addItem(429, "unstable_star").setCreativeTabs(CommonProxy.GTQTCore_HP);
        CLADDED_OPTICAL_FIBER_CORE = this.addItem(430, "component.cosmic.cladded_optical_fiber_core").setCreativeTabs(CommonProxy.GTQTCore_HP);
        CLOSED_TIMELIKE_CURVE_COMPUTATIONAL_UNIT = addItem(431, "component.cosmic.closed_timelike_curve_computational_unit").setCreativeTabs(CommonProxy.GTQTCore_HP);
        CLOSED_TIMELIKE_CURVE_GUIDANCE_UNIT = addItem(432, "component.cosmic.closed_timelike_curve_guidance_unit").setCreativeTabs(CommonProxy.GTQTCore_HP);
        NUCLEAR_CLOCK = addItem(433, "component.cosmic.nuclear_clock").setCreativeTabs(CommonProxy.GTQTCore_HP);
        MANIFOLD_OSCILLATORY_POWER_CELL = addItem(434, "component.cosmic.manifold_oscillatory_power_cell").setCreativeTabs(CommonProxy.GTQTCore_HP);
        SCINTILLATOR = addItem(435, "scintillator").setCreativeTabs(CommonProxy.GTQTCore_GD);
        SCINTILLATOR_CRYSTAL = addItem(436, "scintillator_crystal").setCreativeTabs(CommonProxy.GTQTCore_GD);

        //440
        CIRCUIT_GOOD_I = addItem(440, "circuit.good_i").setCreativeTabs(CommonProxy.GTQTCore_HP);
        CIRCUIT_GOOD_II = addItem(441, "circuit.good_ii").setCreativeTabs(CommonProxy.GTQTCore_HP);
        CIRCUIT_GOOD_III = addItem(442, "circuit.good_iii").setCreativeTabs(CommonProxy.GTQTCore_HP);
        CIRCUIT_GOOD_IV = addItem(443, "circuit.good_iv").setCreativeTabs(CommonProxy.GTQTCore_HP);
        CIRCUIT_GOOD_V = addItem(444, "circuit.good_v").setCreativeTabs(CommonProxy.GTQTCore_HP);
        // Circuits: ID 0-49
        GOOWARE_PROCESSOR = this.addItem(500, "circuit.gooware_processor").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.ZPM).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GOOWARE_ASSEMBLY = this.addItem(501, "circuit.gooware_assembly").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GOOWARE_COMPUTER = this.addItem(502, "circuit.gooware_computer").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UHV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GOOWARE_MAINFRAME = this.addItem(503, "circuit.gooware_mainframe").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UEV).setCreativeTabs(CommonProxy.GTQTCore_TAB);

        OPTICAL_PROCESSOR = this.addItem(504,"circuit.optical_processor").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        OPTICAL_ASSEMBLY = this.addItem(505,"circuit.optical_assembly").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UHV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        OPTICAL_COMPUTER = this.addItem(506,"circuit.optical_computer").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UEV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        OPTICAL_MAINFRAME = this.addItem(507,"circuit.optical_mainframe").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UIV).setCreativeTabs(CommonProxy.GTQTCore_TAB);

        SPINTRONIC_PROCESSOR = this.addItem(508, "circuit.spintronic_processor").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UHV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SPINTRONIC_ASSEMBLY = this.addItem(509, "circuit.spintronic_assembly").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UEV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SPINTRONIC_COMPUTER = this.addItem(510, "circuit.spintronic_computer").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UIV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SPINTRONIC_MAINFRAME = this.addItem(511, "circuit.spintronic_mainframe").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UXV).setCreativeTabs(CommonProxy.GTQTCore_TAB);

        COSMIC_PROCESSOR = this.addItem(512, "circuit.cosmic_processor").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UEV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        COSMIC_ASSEMBLY = this.addItem(513, "circuit.cosmic_assembly").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UIV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        COSMIC_COMPUTER = this.addItem(514, "circuit.cosmic_computer").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UXV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        COSMIC_MAINFRAME = this.addItem(515, "circuit.cosmic_mainframe").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.OpV).setCreativeTabs(CommonProxy.GTQTCore_TAB);

        SUPRACAUSAL_PROCESSOR = this.addItem(516, "circuit.supracausal_processor").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UIV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SUPRACAUSAL_ASSEMBLY = this.addItem(517, "circuit.supracausal_assembly").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UXV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SUPRACAUSAL_COMPUTER = this.addItem(518, "circuit.supracausal_computer").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.OpV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SUPRACAUSAL_MAINFRAME = this.addItem(519, "circuit.supracausal_mainframe").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.MAX).setCreativeTabs(CommonProxy.GTQTCore_TAB);

        SUPRACHRONAL_ULV = this.addItem(520, "circuit.suprachronal.ulv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.ULV).setCreativeTabs(CommonProxy.GTQTCore_HP);
        SUPRACHRONAL_LV = this.addItem(521, "circuit.suprachronal.lv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.LV).setCreativeTabs(CommonProxy.GTQTCore_HP);
        SUPRACHRONAL_MV = this.addItem(522, "circuit.suprachronal.mv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.MV).setCreativeTabs(CommonProxy.GTQTCore_HP);
        SUPRACHRONAL_HV = this.addItem(523, "circuit.suprachronal.hv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.HV).setCreativeTabs(CommonProxy.GTQTCore_HP);
        SUPRACHRONAL_EV = this.addItem(524, "circuit.suprachronal.ev").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.EV).setCreativeTabs(CommonProxy.GTQTCore_HP);
        SUPRACHRONAL_IV = this.addItem(525, "circuit.suprachronal.iv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.IV).setCreativeTabs(CommonProxy.GTQTCore_HP);
        SUPRACHRONAL_LuV = this.addItem(526, "circuit.suprachronal.luv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.LuV).setCreativeTabs(CommonProxy.GTQTCore_HP);
        SUPRACHRONAL_ZPM = this.addItem(527, "circuit.suprachronal.zpm").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.ZPM).setCreativeTabs(CommonProxy.GTQTCore_HP);
        SUPRACHRONAL_UV = this.addItem(528, "circuit.suprachronal.uv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UV).setCreativeTabs(CommonProxy.GTQTCore_HP);
        SUPRACHRONAL_UHV = this.addItem(529, "circuit.suprachronal.uhv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UHV).setCreativeTabs(CommonProxy.GTQTCore_HP);
        SUPRACHRONAL_UEV = this.addItem(530, "circuit.suprachronal.uev").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UEV).setCreativeTabs(CommonProxy.GTQTCore_HP);
        SUPRACHRONAL_UIV = this.addItem(531, "circuit.suprachronal.uiv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UIV).setCreativeTabs(CommonProxy.GTQTCore_HP);
        SUPRACHRONAL_UXV = this.addItem(532, "circuit.suprachronal.uxv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UXV).setCreativeTabs(CommonProxy.GTQTCore_HP);
        SUPRACHRONAL_OpV = this.addItem(533, "circuit.suprachronal.opv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.OpV).setCreativeTabs(CommonProxy.GTQTCore_HP);
        SUPRACHRONAL_MAX = this.addItem(534, "circuit.suprachronal.max").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.MAX).setCreativeTabs(CommonProxy.GTQTCore_HP);

        MAGNETO_RESONATIC_CIRCUIT_ULV = this.addItem(535, "circuit.magneto_resonatic.ulv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.ULV).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        MAGNETO_RESONATIC_CIRCUIT_LV = this.addItem(536, "circuit.magneto_resonatic.lv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.LV).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        MAGNETO_RESONATIC_CIRCUIT_MV = this.addItem(537, "circuit.magneto_resonatic.mv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.MV).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        MAGNETO_RESONATIC_CIRCUIT_HV = this.addItem(538, "circuit.magneto_resonatic.hv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.HV).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        MAGNETO_RESONATIC_CIRCUIT_EV = this.addItem(539, "circuit.magneto_resonatic.ev").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.EV).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        MAGNETO_RESONATIC_CIRCUIT_IV = this.addItem(540, "circuit.magneto_resonatic.iv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.IV).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        MAGNETO_RESONATIC_CIRCUIT_LuV = this.addItem(541, "circuit.magneto_resonatic.luv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.LuV).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        MAGNETO_RESONATIC_CIRCUIT_ZPM = this.addItem(542, "circuit.magneto_resonatic.zpm").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.ZPM).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        MAGNETO_RESONATIC_CIRCUIT_UV = this.addItem(543, "circuit.magneto_resonatic.uv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UV).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        MAGNETO_RESONATIC_CIRCUIT_UHV = this.addItem(544, "circuit.magneto_resonatic.uhv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UHV).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        MAGNETO_RESONATIC_CIRCUIT_UEV = this.addItem(545, "circuit.magneto_resonatic.uev").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UEV).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        MAGNETO_RESONATIC_CIRCUIT_UIV = this.addItem(546, "circuit.magneto_resonatic.uiv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UIV).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        MAGNETO_RESONATIC_CIRCUIT_UXV = this.addItem(547, "circuit.magneto_resonatic.uxv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UXV).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        MAGNETO_RESONATIC_CIRCUIT_OpV = this.addItem(548, "circuit.magneto_resonatic.opv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.OpV).setCreativeTabs(CommonProxy.GTQTCore_BIO);
        MAGNETO_RESONATIC_CIRCUIT_MAX = this.addItem(549, "circuit.magneto_resonatic.max").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.MAX).setCreativeTabs(CommonProxy.GTQTCore_BIO);

        ASUPRACHRONAL_ULV = this.addItem(550, "circuit_suprachronal_ulv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.ULV).setCreativeTabs(CommonProxy.GTQTCore_GD);
        ASUPRACHRONAL_LV = this.addItem(551, "circuit_suprachronal_lv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.LV).setCreativeTabs(CommonProxy.GTQTCore_GD);
        ASUPRACHRONAL_MV = this.addItem(552, "circuit_suprachronal_mv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.MV).setCreativeTabs(CommonProxy.GTQTCore_GD);
        ASUPRACHRONAL_HV = this.addItem(553, "circuit_suprachronal_hv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.HV).setCreativeTabs(CommonProxy.GTQTCore_GD);
        ASUPRACHRONAL_EV = this.addItem(554, "circuit_suprachronal_ev").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.EV).setCreativeTabs(CommonProxy.GTQTCore_GD);
        ASUPRACHRONAL_IV = this.addItem(555, "circuit_suprachronal_iv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.IV).setCreativeTabs(CommonProxy.GTQTCore_GD);
        ASUPRACHRONAL_LuV = this.addItem(556, "circuit_suprachronal_luv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.LuV).setCreativeTabs(CommonProxy.GTQTCore_GD);
        ASUPRACHRONAL_ZPM = this.addItem(557, "circuit_suprachronal_zpm").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.ZPM).setCreativeTabs(CommonProxy.GTQTCore_GD);
        ASUPRACHRONAL_UV = this.addItem(558, "circuit_suprachronal_uv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UV).setCreativeTabs(CommonProxy.GTQTCore_GD);
        ASUPRACHRONAL_UHV = this.addItem(559, "circuit_suprachronal_uhv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UHV).setCreativeTabs(CommonProxy.GTQTCore_GD);
        ASUPRACHRONAL_UEV = this.addItem(560, "circuit_suprachronal_uev").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UEV).setCreativeTabs(CommonProxy.GTQTCore_GD);
        ASUPRACHRONAL_UIV = this.addItem(561, "circuit_suprachronal_uiv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UIV).setCreativeTabs(CommonProxy.GTQTCore_GD);
        ASUPRACHRONAL_UXV = this.addItem(562, "circuit_suprachronal_uxv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UXV).setCreativeTabs(CommonProxy.GTQTCore_GD);
        ASUPRACHRONAL_OpV = this.addItem(563, "circuit_suprachronal_opv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.OpV).setCreativeTabs(CommonProxy.GTQTCore_GD);
        ASUPRACHRONAL_MAX = this.addItem(564, "circuit_suprachronal_max").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.MAX).setCreativeTabs(CommonProxy.GTQTCore_GD);

        //  General Circuits
        GENERAL_CIRCUIT_ULV = this.addItem(565, "general_circuit.ulv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.ULV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GENERAL_CIRCUIT_LV = this.addItem(566, "general_circuit.lv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.LV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GENERAL_CIRCUIT_MV = this.addItem(567, "general_circuit.mv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.MV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GENERAL_CIRCUIT_HV = this.addItem(568, "general_circuit.hv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.HV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GENERAL_CIRCUIT_EV = this.addItem(569, "general_circuit.ev").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.EV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GENERAL_CIRCUIT_IV = this.addItem(570, "general_circuit.iv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.IV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GENERAL_CIRCUIT_LuV = this.addItem(571, "general_circuit.luv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.LuV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GENERAL_CIRCUIT_ZPM = this.addItem(572, "general_circuit.zpm").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.ZPM).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GENERAL_CIRCUIT_UV = this.addItem(573, "general_circuit.uv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GENERAL_CIRCUIT_UHV = this.addItem(574, "general_circuit.uhv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UHV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GENERAL_CIRCUIT_UEV = this.addItem(575, "general_circuit.uev").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UEV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GENERAL_CIRCUIT_UIV = this.addItem(576, "general_circuit.uiv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UIV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GENERAL_CIRCUIT_UXV = this.addItem(577, "general_circuit.uxv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.UXV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GENERAL_CIRCUIT_OpV = this.addItem(578, "general_circuit.opv").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.OpV).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GENERAL_CIRCUIT_MAX = this.addItem(579, "general_circuit.max").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.MAX).setCreativeTabs(CommonProxy.GTQTCore_TAB);

        // Primitive Circuit Components: ID 50-59
        VACUUM_TUBE_COMPONENTS = this.addItem(600, "vacuum_tube.components").setCreativeTabs(CommonProxy.GTQTCore_TAB);

        // Electronic Circuit Components: ID 60-69


        // Micro Circuit Components: ID 70-89


        // Nano Circuit Components: ID 90-109


        // Quantum Circuit Components: ID 110-129


        // Crystal Circuit Components: ID 130-159
        STRONTIUM_CARBONATE_OPTICAL_WAFER = this.addItem(625, "component.optical.strontium_carbonate_wafer").setCreativeTabs(CommonProxy.GTQTCore_GD);
        STRONTIUM_CARBONATE_BOHRIUM_BOULE = this.addItem(626, "boule.strontium_carbonate.bohrium").setCreativeTabs(CommonProxy.GTQTCore_GD);
        STRONTIUM_CARBONATE_BOHRIUM_WAFER = this.addItem(627, "wafer.strontium_carbonate.bohrium").setCreativeTabs(CommonProxy.GTQTCore_GD);
        DUBNIUM_BOULE = this.addItem(628, "boule.dubnium").setCreativeTabs(CommonProxy.GTQTCore_GD);
        EU_DOPED_CUBIC_ZIRCONIA_BOULE = this.addItem(630, "boule.cubic_zirconia.europium").setCreativeTabs(CommonProxy.GTQTCore_GD);
        EU_DOPED_CUBIC_ZIRCONIA_WAFER = this.addItem(631, "wafer.cubic_zirconia.europium").setCreativeTabs(CommonProxy.GTQTCore_GD);
        CRYSTAL_INTERFACE_WAFER = this.addItem(632, "wafer.crystal.interface").setCreativeTabs(CommonProxy.GTQTCore_GD);
        CRYSTAL_INTERFACE_CHIP = this.addItem(633, "plate.crystal.interface").setCreativeTabs(CommonProxy.GTQTCore_GD);
        ENGRAVED_RUBY_CRYSTAL_CHIP = this.addItem(634, "engraved.crystal_chip.ruby").setCreativeTabs(CommonProxy.GTQTCore_GD);
        ENGRAVED_SAPPHIRE_CRYSTAL_CHIP = this.addItem(635, "engraved.crystal_chip.sapphire").setCreativeTabs(CommonProxy.GTQTCore_GD);
        ENGRAVED_DIAMOND_CRYSTAL_CHIP = this.addItem(636, "engraved.crystal_chip.diamond").setCreativeTabs(CommonProxy.GTQTCore_GD);
        CRYSTAL_MODULATOR_RUBY = this.addItem(637, "crystal.modulator.ruby").setCreativeTabs(CommonProxy.GTQTCore_GD);
        CRYSTAL_MODULATOR_SAPPHIRE = this.addItem(638, "crystal.modulator.sapphire").setCreativeTabs(CommonProxy.GTQTCore_GD);
        CRYSTAL_MODULATOR_DIAMOND = this.addItem(639, "crystal.modulator.diamond").setCreativeTabs(CommonProxy.GTQTCore_GD);
        CRYSTAL_SYSTEM_ON_CHIP_SOCKET = this.addItem(640, "crystal.system_on_chip.socket").setCreativeTabs(CommonProxy.GTQTCore_GD);
        INTRAVITAL_SOC = this.addItem(641, "component.gooware.intravital_soc").setCreativeTabs(CommonProxy.GTQTCore_BIO);
        OPTICAL_IMC_BOARD = this.addItem(642, "component.optical.optical_imc_board").setCreativeTabs(CommonProxy.GTQTCore_GD);
        PHOTOELECTRON_SOC = this.addItem(643, "component.optical.photoelectron_soc").setCreativeTabs(CommonProxy.GTQTCore_GD);

        // Wetware Circuit Components: ID 160-179


        // Gooware Circuit Components: ID 180-199
        BZ_REACTION_CHAMBER = this.addItem(680, "reaction_chamber.bz").setCreativeTabs(CommonProxy.GTQTCore_CH);
        NONLINEAR_CHEMICAL_OSCILLATOR = this.addItem(681, "nonlinear_chemical_oscillator").setCreativeTabs(CommonProxy.GTQTCore_CH);

        //  Cosmic Components
        COSMIC_INFORMATION_MODULE = this.addItem(682, "component.cosmic.information_module").setCreativeTabs(CommonProxy.GTQTCore_HP);
        HOLOGRAPHIC_INFORMATION_IMC = this.addItem(683, "component.cosmic.holographic_imc").setCreativeTabs(CommonProxy.GTQTCore_HP);

        //  Supracausal Components
        SPACETIME_CONDENSER = this.addItem(684, "component.supracausal.spacetime_condenser").setCreativeTabs(CommonProxy.GTQTCore_HP);
        LIGHT_CONE_MODULE = this.addItem(685, "component.supracausal.light_cone_module").setCreativeTabs(CommonProxy.GTQTCore_HP);

        // Optical Circuit Components: ID 200-219
        PHASE_CHANGE_MEMORY = this.addItem(700, "plate.phase_change_memory").setCreativeTabs(CommonProxy.GTQTCore_GD);
        OPTICAL_FIBER = this.addItem(701, "optical_fiber").setCreativeTabs(CommonProxy.GTQTCore_GD);
        DIELECTRIC_MIRROR = this.addItem(702, "dielectric_mirror").setCreativeTabs(CommonProxy.GTQTCore_GD);
        EMPTY_LASER_ASSEMBLY = this.addItem(703, "laser.assembly.empty").setCreativeTabs(CommonProxy.GTQTCore_GD);
        HELIUM_LASER = this.addItem(704, "laser.emitter.helium").setCreativeTabs(CommonProxy.GTQTCore_GD);
        NEON_LASER = this.addItem(705, "laser.emitter.neon").setCreativeTabs(CommonProxy.GTQTCore_GD);
        ARGON_LASER = this.addItem(706, "laser.emitter.argon").setCreativeTabs(CommonProxy.GTQTCore_GD);
        KRYPTON_LASER = this.addItem(707, "laser.emitter.krypton").setCreativeTabs(CommonProxy.GTQTCore_GD);
        XENON_LASER = this.addItem(708, "laser.emitter.xenon").setCreativeTabs(CommonProxy.GTQTCore_GD);
        HELIUM_NEON_LASER = this.addItem(709, "laser.helium_neon").setCreativeTabs(CommonProxy.GTQTCore_GD);
        ND_YAG_LASER = this.addItem(710, "laser.nd_yag").setCreativeTabs(CommonProxy.GTQTCore_GD);
        OPTICAL_LASER_CONTROL_UNIT = this.addItem(711, "optical_laser_control_unit").setCreativeTabs(CommonProxy.GTQTCore_GD);



        // Spintronic Circuit Components: ID 220-239
        SPIN_TRANSFER_TORQUE_MEMORY = this.addItem(720, "plate.spin_transfer_torque_memory").setCreativeTabs(CommonProxy.GTQTCore_HP);
        TOPOLOGICAL_INSULATOR_TUBE = this.addItem(721, "tube.topological_insulator").setCreativeTabs(CommonProxy.GTQTCore_HP);
        BOSE_EINSTEIN_CONDENSATE_CONTAINMENT_UNIT = this.addItem(722, "containment_unit.bose_einstein_condensate").setCreativeTabs(CommonProxy.GTQTCore_HP);
        BOSE_EINSTEIN_CONDENSATE = this.addItem(723, "bose_einstein_condensate").setCreativeTabs(CommonProxy.GTQTCore_HP);
        ESR_COMPUTATION_UNIT = this.addItem(724, "esr_computation_unit").setCreativeTabs(CommonProxy.GTQTCore_HP);

        // Cosmic Circuit Components: ID 240-259


        // Supra-Causal Circuit Components: ID 260-299


        // Supra-Chronal Circuit Components: ID 300-349

        //核物理 800
        NAQUADAH_ROD = this.addItem(800, "naquadah_rod").setCreativeTabs(CommonProxy.GTQTCore_NC);
        NAQUADAH_ROD_DUAL = this.addItem(801, "naquadah_rod_dual").setCreativeTabs(CommonProxy.GTQTCore_NC);
        NAQUADAH_ROD_QUAD = this.addItem(802, "naquadah_rod_quad").setCreativeTabs(CommonProxy.GTQTCore_NC);

        POOR_NAQUADAH_ROD = this.addItem(803, "poor_naquadah_rod").setCreativeTabs(CommonProxy.GTQTCore_NC);
        POOR_NAQUADAH_ROD_DUAL = this.addItem(804, "poor_naquadah_rod_dual").setCreativeTabs(CommonProxy.GTQTCore_NC);
        POOR_NAQUADAH_ROD_QUAD = this.addItem(805, "poor_naquadah_rod_quad").setCreativeTabs(CommonProxy.GTQTCore_NC);

        THORIUM_ROD = this.addItem(806, "thorium_rod").setCreativeTabs(CommonProxy.GTQTCore_NC);
        THORIUM_ROD_DUAL = this.addItem(807, "thorium_rod_dual").setCreativeTabs(CommonProxy.GTQTCore_NC);
        THORIUM_ROD_QUAD = this.addItem(808, "thorium_rod_quad").setCreativeTabs(CommonProxy.GTQTCore_NC);

        POOR_THORIUM_ROD = this.addItem(809, "poor_thorium_rod").setCreativeTabs(CommonProxy.GTQTCore_NC);
        POOR_THORIUM_ROD_DUAL = this.addItem(810, "poor_thorium_rod_dual").setCreativeTabs(CommonProxy.GTQTCore_NC);
        POOR_THORIUM_ROD_QUAD = this.addItem(811, "poor_thorium_rod_quad").setCreativeTabs(CommonProxy.GTQTCore_NC);

        // Power Components: ID 356-379
        NANO_POWER_IC_WAFER = this.addItem(856, "wafer.nano_power_integrated_circuit").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        PICO_POWER_IC_WAFER = this.addItem(857, "wafer.pico_power_integrated_circuit").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        FEMTO_POWER_IC_WAFER = this.addItem(858, "wafer.femto_power_integrated_circuit").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        ATTO_PIC_WAFER = this.addItem(859, "wafer.atto_pic").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        ZEPTO_PIC_WAFER = this.addItem(860, "wafer.zepto_pic").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        UHASOC_WAFER = this.addItem(861, "wafer.uhasoc").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        OPTICAL_NOR_MEMORY_CHIP = this.addItem(862, "wafer.chip.optical_nor_memory_chip").setCreativeTabs(CommonProxy.GTQTCore_TAB);

        NANO_POWER_IC = this.addItem(865, "plate.nano_power_integrated_circuit").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        PICO_POWER_IC = this.addItem(866, "plate.pico_power_integrated_circuit").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        FEMTO_POWER_IC = this.addItem(867, "plate.femto_power_integrated_circuit").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        ATTO_PIC_CHIP = this.addItem(868, "wafer.chip.atto_pic").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        ZEPTO_PIC_CHIP = this.addItem(869, "wafer.chip.zepto_pic").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        UHASOC_CHIP = this.addItem(870, "wafer.chip.uhasoc").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SPINTRONIC_NAND_MEMORY_CHIP = this.addItem(871, "wafer.chip.spintronic_nand_memory_chip").setCreativeTabs(CommonProxy.GTQTCore_TAB);


        // Circuit Boards: ID 380-419
        GOOWARE_BOARD = this.addItem(894, "board.gooware").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        OPTICAL_BOARD = this.addItem(895, "board.optical").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SPINTRONIC_BOARD = this.addItem(896, "board.spintronic").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GOOWARE_CIRCUIT_BOARD = this.addItem(897, "circuit_board.gooware").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        OPTICAL_CIRCUIT_BOARD = this.addItem(898, "circuit_board.optical").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SPINTRONIC_CIRCUIT_BOARD = this.addItem(899, "circuit_board.spintronic").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        // SMDs: ID 420-479
        OPTICAL_CAPACITOR = this.addItem(900, "component.optical_smd.capacitor").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        OPTICAL_DIODE = this.addItem(901, "component.optical_smd.diode").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        OPTICAL_RESISTOR = this.addItem(902, "component.optical_smd.resistor").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        OPTICAL_TRANSISTOR = this.addItem(903, "component.optical_smd.transistor").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        OPTICAL_INDUCTOR = this.addItem(904, "component.optical_smd.inductor").setCreativeTabs(CommonProxy.GTQTCore_TAB);

        SPINTRONIC_CAPACITOR = this.addItem(905, "component.spintronic_smd.capacitor").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SPINTRONIC_DIODE = this.addItem(906, "component.spintronic_smd.diode").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SPINTRONIC_RESISTOR = this.addItem(907, "component.spintronic_smd.resistor").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SPINTRONIC_TRANSISTOR = this.addItem(908, "component.spintronic_smd.transistor").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SPINTRONIC_INDUCTOR = this.addItem(909, "component.spintronic_smd.inductor").setCreativeTabs(CommonProxy.GTQTCore_TAB);

        COSMIC_CAPACITOR = this.addItem(910, "component.cosmic_smd.capacitor").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        COSMIC_DIODE = this.addItem(911, "component.cosmic_smd.diode").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        COSMIC_RESISTOR = this.addItem(912, "component.cosmic_smd.resistor").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        COSMIC_TRANSISTOR = this.addItem(913, "component.cosmic_smd.transistor").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        COSMIC_INDUCTOR = this.addItem(914, "component.cosmic_smd.inductor").setCreativeTabs(CommonProxy.GTQTCore_TAB);

        SUPRACAUSAL_CAPACITOR = this.addItem(915, "component.supracausal_smd.capacitor").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SUPRACAUSAL_DIODE = this.addItem(916, "component.supracausal_smd.diode").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SUPRACAUSAL_RESISTOR = this.addItem(917, "component.supracausal_smd.resistor").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SUPRACAUSAL_TRANSISTOR = this.addItem(918, "component.supracausal_smd.transistor").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SUPRACAUSAL_INDUCTOR = this.addItem(919, "component.supracausal_smd.inductor").setCreativeTabs(CommonProxy.GTQTCore_TAB);

        //  Covers
        ELECTRIC_MOTOR_ULV = this.addItem(934, "cover.electric_motor.ulv").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        ELECTRIC_PISTON_ULV = this.addItem(935, "cover.electric_piston.ulv").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        ELECTRIC_PUMP_ULV = this.addItem(936, "cover.electric_pump.ulv").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        CONVEYOR_MODULE_ULV = this.addItem(937, "cover.conveyor_module.ulv").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        ROBOT_ARM_ULV = this.addItem(938, "cover.robot_arm.ulv").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        EMITTER_ULV = this.addItem(939, "cover.emitter.ulv").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SENSOR_ULV = this.addItem(940, "cover.sensor.ulv").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        FIELD_GENERATOR_ULV = this.addItem(941, "cover.field_generator.ulv").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        ELECTRIC_MOTOR_MAX = this.addItem(942, "cover.electric_motor.max").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        ELECTRIC_PISTON_MAX = this.addItem(943, "cover.electric_piston.max").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        ELECTRIC_PUMP_MAX = this.addItem(944, "cover.electric_pump.max").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        CONVEYOR_MODULE_MAX = this.addItem(945, "cover.conveyor_module.max").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        ROBOT_ARM_MAX = this.addItem(946, "cover.robot_arm.max").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        EMITTER_MAX = this.addItem(947, "cover.emitter.max").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SENSOR_MAX = this.addItem(948, "cover.sensor.max").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        FIELD_GENERATOR_MAX = this.addItem(949, "cover.field_generator.max").setCreativeTabs(CommonProxy.GTQTCore_TAB);
        COVER_SOLAR_PANEL_MAX = this.addItem(950, "cover.solar_panel.max").addComponents(new TooltipBehavior((lines) -> {
            lines.add(I18n.format("metaitem.cover.solar.panel.tooltip.1"));
            lines.add(I18n.format("metaitem.cover.solar.panel.tooltip.2"));
            lines.add(I18n.format("gregtech.universal.tooltip.voltage_out", GTValues.V[GTValues.MAX], GTValues.VNF[GTValues.MAX]));
        }));
        // Process-Specific Components: ID 500-999
        MAGNETRON = this.addItem(951, "magnetron").setCreativeTabs(CommonProxy.GTQTCore_TAB);

        //  High Energy Physics items
        PLASMA_CONTAINMENT_CELL = this.addItem(1000, "plasma_containment_cell").setCreativeTabs(CommonProxy.GTQTCore_HP);
        RHENIUM_PLASMA_CONTAINMENT_CELL = this.addItem(1001, "rhenium_plasma_containment_cell").setCreativeTabs(CommonProxy.GTQTCore_HP);
        NEUTRON_PLASMA_CONTAINMENT_CELL = this.addItem(1002, "neutron_plasma_containment_cell").setCreativeTabs(CommonProxy.GTQTCore_HP);
        HYPOGEN_PLASMA_CONTAINMENT_CELL = this.addItem(1003, "hypogen_plasma_containment_cell").setCreativeTabs(CommonProxy.GTQTCore_HP);
        ACTINIUM_SUPERHYDRIDE_PLASMA_CONTAINMENT_CELL = this.addItem(1004, "actinium_superhydride_plasma_containment_cell").setCreativeTabs(CommonProxy.GTQTCore_HP);
        QUANTUM_ANOMALY = this.addItem(1005, "quantum_anomaly").setCreativeTabs(CommonProxy.GTQTCore_HP);

        //  Biological Components
        ELECTROCHEMICAL_GRADIENT_RECORDER = this.addItem(1006, "biological.components.electrochemical_gradient_recorder").setCreativeTabs(CommonProxy.GTQTCore_BIO);
        ULTRA_MICRO_PHASE_SEPARATOR = this.addItem(1007, "biological.components.ultra_micro_phase_separator").setCreativeTabs(CommonProxy.GTQTCore_BIO);
        QUANTUM_TUNNELING_MICROTUBULE = this.addItem(1008, "biological.components.quantum_tunneling_microtubule").setCreativeTabs(CommonProxy.GTQTCore_BIO);
        HYPERRIBOSOME = this.addItem(1009, "biological.components.hyperribosome").setCreativeTabs(CommonProxy.GTQTCore_BIO);
        NEUTRON_ABSORBING_PROTEIN = this.addItem(1010, "biological.components.neutron_absorbing_protein").setCreativeTabs(CommonProxy.GTQTCore_BIO);
        SUPEREXCITED_CONDUCTIVE_POLYMER = this.addItem(1011, "biological.components.superexcited_conductive_polymer").setCreativeTabs(CommonProxy.GTQTCore_BIO);
        DNA_ENCODER = this.addItem(1012, "biological.components.dna_encoder").setCreativeTabs(CommonProxy.GTQTCore_BIO);
        DNA_DECODER = this.addItem(1013, "biological.components.dna_decoder").setCreativeTabs(CommonProxy.GTQTCore_BIO);
        DNA_DECODE_ENCODER = this.addItem(1014, "biological.components.dna_decode_encoder").setCreativeTabs(CommonProxy.GTQTCore_BIO);
        DRAGON_CELL = this.addItem(1015, "dragon_cell").setCreativeTabs(CommonProxy.GTQTCore_BIO);
        PRE_DRAGON_CELL = this.addItem(1016, "pre_dragon_cell").setCreativeTabs(CommonProxy.GTQTCore_BIO);

        //蓝图
        DISK_0 = this.addItem(2000, "disk.0").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        DISK_1 = this.addItem(2001, "disk.1").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        DISK_2 = this.addItem(2002, "disk.2").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        DISK_3 = this.addItem(2003, "disk.3").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        DISK_4 = this.addItem(2004, "disk.4").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        DISK_5 = this.addItem(2005, "disk.5").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        DISK_6 = this.addItem(2006, "disk.6").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        DISK_7 = this.addItem(2007, "disk.7").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        DISK_8 = this.addItem(2008, "disk.8").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        DISK_9 = this.addItem(2009, "disk.9").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        DISK_10 = this.addItem(2010, "disk.10").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        DISK_11 = this.addItem(2011, "disk.11").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        DISK_12 = this.addItem(2012, "disk.12").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        DISK_13 = this.addItem(2013, "disk.13").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        DISK_14 = this.addItem(2014, "disk.14").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        DISK_15 = this.addItem(2015, "disk.15").setCreativeTabs(CommonProxy.GTQTCore_DISK);


        ALPHA=this.addItem(2500, "alpha").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ANTIALPHA=this.addItem(2501, "antialpha").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ANTIBOTTOM_QUARK=this.addItem(2502, "antibottom_quark").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ANTICHARM_QUARK=this.addItem(2503, "anticharm_quark").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ANTIDELTA_MINUS=this.addItem(2504, "antidelta_minus").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ANTIDELTA_PLUS_PLUS=this.addItem(2505, "antidelta_plus_plus").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ANTIDEUTERON=this.addItem(2506, "antideuteron").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ANTIDOWN_QUARK=this.addItem(2507, "antidown_quark").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ANTIHELION=this.addItem(2508, "antihelion").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ANTIKAON_NAUGHT=this.addItem(2509, "antikaon_naught").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ANTIMUON=this.addItem(2510, "antimuon").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ANTINEUTRON=this.addItem(2511, "antineutron").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ANTIPROTON=this.addItem(2512, "antiproton").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ANTISIGMA_MINUS=this.addItem(2513, "antisigma_minus").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ANTISIGMA_NAUGHT=this.addItem(2514, "antisigma_naught").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ANTISIGMA_PLUS=this.addItem(2515, "antisigma_plus").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ANTISTRANGE_QUARK=this.addItem(2516, "antistrange_quark").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ANTITAU=this.addItem(2517, "antitau").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ANTITOP_QUARK=this.addItem(2518, "antitop_quark").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ANTITRITON=this.addItem(2519, "antitriton").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ANTIUP_QUARK=this.addItem(2520, "antiup_quark").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        BORON_ION=this.addItem(2521, "boron_ion").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        BOTTOM_ETA=this.addItem(2522, "bottom_eta").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        BOTTOM_QUARK=this.addItem(2523, "bottom_quark").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        CALCIUM_48_ION=this.addItem(2524, "calcium_48_ion").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        CHARM_QUARK=this.addItem(2525, "charm_quark").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        CHARMED_ETA=this.addItem(2526, "charmed_eta").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        DELTA_MINUS=this.addItem(2527, "delta_minus").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        DELTA_PLUS_PLUS=this.addItem(2528, "delta_plus_plus").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        DEUTERON=this.addItem(2529, "deuteron").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        DOWN_QUARK=this.addItem(2530, "down_quark").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ELECTRON=this.addItem(2531, "electron").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ELECTRON_ANTINEUTRINO=this.addItem(2532, "electron_antineutrino").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ELECTRON_NEUTRINO=this.addItem(2533, "electron_neutrino").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ETA=this.addItem(2534, "eta").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        ETA_PRIME=this.addItem(2535, "eta_prime").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        GLUEBALL=this.addItem(2536, "glueball").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        GLUON=this.addItem(2537, "gluon").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        HELION=this.addItem(2538, "helion").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        HIGGS_BOSON=this.addItem(2539, "higgs_boson").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        KAON_MINUS=this.addItem(2540, "kaon_minus").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        KAON_NAUGHT=this.addItem(2541, "kaon_naught").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        KAON_PLUS=this.addItem(2542, "kaon_plus").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        MUON=this.addItem(2543, "muon").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        MUON_ANTINEUTRINO=this.addItem(2544, "muon_antineutrino").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        MUON_NEUTRINO=this.addItem(2545, "muon_neutrino").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        NEUTRON=this.addItem(2546, "neutron").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        PHOTON=this.addItem(2547, "photon").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        PION_MINUS=this.addItem(2548, "pion_minus").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        PION_NAUGHT=this.addItem(2549, "pion_naught").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        PION_PLUS=this.addItem(2550, "pion_plus").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        POSITRON=this.addItem(2551, "positron").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        PROTON=this.addItem(2552, "proton").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        SIGMA_MINUS=this.addItem(2553, "sigma_minus").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        SIGMA_NAUGHT=this.addItem(2554, "sigma_naught").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        SIGMA_PLUS=this.addItem(2555, "sigma_plus").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        STRANGE_QUARK=this.addItem(2556, "strange_quark").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        TAU=this.addItem(2557, "tau").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        TAU_ANTINEUTRINO=this.addItem(2558, "tau_antineutrino").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        TAU_NEUTRINO=this.addItem(2559, "tau_neutrino").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        TOP_QUARK=this.addItem(2560, "top_quark").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        TRITON=this.addItem(2561, "triton").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        UP_QUARK=this.addItem(2562, "up_quark").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        W_MINUS_BOSON=this.addItem(2563, "w_minus_boson").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        W_PLUS_BOSON=this.addItem(2564, "w_plus_boson").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);
        Z_BOSON=this.addItem(2565, "z_boson").addComponents(new ParticleBehavior()).setCreativeTabs(CommonProxy.GTQTCore_PA);

        //科研
        ARROW_UP_MKI = this.addItem(3000, "arrow_up_mki").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        ARROW_DOWN_MKI = this.addItem(3001, "arrow_down_mki").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        ARROW_LEFT_MKI = this.addItem(3002, "arrow_left_mki").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        ARROW_RIGHT_MKI = this.addItem(3003, "arrow_right_mki").setCreativeTabs(CommonProxy.GTQTCore_DISK);

        ARROW_UP_MKII = this.addItem(3004, "arrow_up_mkii").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        ARROW_DOWN_MKII = this.addItem(3005, "arrow_down_mkii").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        ARROW_LEFT_MKII = this.addItem(3006, "arrow_left_mkii").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        ARROW_RIGHT_MKII = this.addItem(3007, "arrow_right_mkii").setCreativeTabs(CommonProxy.GTQTCore_DISK);

        ARROW_UP_MKIII = this.addItem(3008, "arrow_up_mkiii").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        ARROW_DOWN_MKIII = this.addItem(3009, "arrow_down_mkiii").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        ARROW_LEFT_MKIII = this.addItem(3010, "arrow_left_mkiii").setCreativeTabs(CommonProxy.GTQTCore_DISK);
        ARROW_RIGHT_MKIII = this.addItem(3011, "arrow_right_mkiii").setCreativeTabs(CommonProxy.GTQTCore_DISK);
    }

}
