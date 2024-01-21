package keqing.gtqtcore.common.items;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.ElectricStats;
import gregtech.api.items.metaitem.StandardMetaItem;
import gregtech.common.items.behaviors.MultiblockBuilderBehavior;
import gregtech.common.items.behaviors.ProspectorScannerBehavior;
import keqing.gtqtcore.common.CommonProxy;
import keqing.gtqtcore.common.block.blocks.GTQTCrops;
import keqing.gtqtcore.common.items.behaviors.IntBcircuitBehavior;
import keqing.gtqtcore.common.items.behaviors.MillBallBehavior;
import keqing.gtqtcore.common.items.behaviors.StructureWriteBehavior;

import static keqing.gtqtcore.common.items.GTQTMetaItems.*;


public class GTQTMetaItem1 extends StandardMetaItem {

    public GTQTMetaItem1() {
        this.setRegistryName("gtqt_meta_item_1");
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
    }



    public void registerSubItems() {

        COPPER_CROP = addItem(200, "copper_crop");
        COPPER_CROP.addComponents(new GTQTCropSeedBehaviour(GTQTCrops.COPPER_CROP, COPPER_CROP.getStackForm(), COPPER_CROP.getStackForm()));

        IRON_CROP = addItem(201, "iron_crop");
        IRON_CROP.addComponents(new GTQTCropSeedBehaviour(GTQTCrops.IRON_CROP, IRON_CROP.getStackForm(), IRON_CROP.getStackForm()));

        TIN_CROP = addItem(202, "tin_crop");
        TIN_CROP.addComponents(new GTQTCropSeedBehaviour(GTQTCrops.TIN_CROP, TIN_CROP.getStackForm(), TIN_CROP.getStackForm()));

        BRONZE_CROP = addItem(203, "bronze_crop");
        BRONZE_CROP.addComponents(new GTQTCropSeedBehaviour(GTQTCrops.BRONZE_CROP, BRONZE_CROP.getStackForm(), BRONZE_CROP.getStackForm()));

        CARBON_CROP = addItem(204, "carbon_crop");
        CARBON_CROP.addComponents(new GTQTCropSeedBehaviour(GTQTCrops.CARBON_CROP, CARBON_CROP.getStackForm(), CARBON_CROP.getStackForm()));

        GOLD_CROP = addItem(205, "gold_crop");
        GOLD_CROP.addComponents(new GTQTCropSeedBehaviour(GTQTCrops.GOLD_CROP, GOLD_CROP.getStackForm(), GOLD_CROP.getStackForm()));


        IMPREGNATED_GRAPHITE_RODSA = this.addItem(1,"item.impregnated_graphite_rodsa").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        IMPREGNATED_GRAPHITE_RODS = this.addItem(2,"item.impregnated_graphite_rods").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        IMPREGNATED_SUBSTRATE = this.addItem(3,"item.impregnated_substrate").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        IMPREGNATED_PLASTIC_SUBSTRATE = this.addItem(4,"item.impregnated_plastic_substrate").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);

        COMMON_ALGAE = this.addItem(10,"algae.common").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GREEN_ALGAE = this.addItem(11,"algae.green").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        RED_ALGAE = this.addItem(12,"algae.red").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        BROWN_ALGAE = this.addItem(13,"algae.brown").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        GOLD_ALGAE = this.addItem(14,"algae.gold").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        T_ALGAE = this.addItem(15,"algae.t").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        BIOLOGY_INTEGRATED_CIRCUIT = this.addItem(39, "item.biology_integrated_circuit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB).addComponents(new IntBcircuitBehavior());
        ROUGH_BIOLOGY_RESIN=this.addItem(41, "item.biology_resin").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        PURIFIED_ALUMINIUM_MIXTURE=this.addItem(42, "item.aluminium_mixture").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        CELLULOSE_PULP=this.addItem(43, "item.cellulose_pulp").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        CELLULOSE_FIBER=this.addItem(44, "item.cellulose_fiber_green").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        CELLULOSE_FIBER_RED=this.addItem(45, "item.cellulose_fiber_red").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        CELLULOSE_FIBER_YELLOW=this.addItem(46, "item.cellulose_fiber_yellow").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        PINE_CONE=this.addItem(47, "item.pine_cone").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        PINE_FRAGMENT=this.addItem(48, "item.pine_fragment").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        COMPOST=this.addItem(49, "item.compost").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        ALGAE_ACID=this.addItem(50, "item.algae_acid").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        WOOD_PELLETS=this.addItem(51, "item.wood_pellets").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        PELLETS_MOULD=this.addItem(52, "item.pellets_mould").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        ALUMINIUM_PELLETS=this.addItem(53, "item.aluminium_pellets").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);

        POTASSIUM_ETHYLATE=this.addItem(54, "item.potassium.ethylate").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        POTASSIUM_ETHYLXANTHATE=this.addItem(55, "item.potassium.ethylxanthate").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SODIUM_ETHYLXANTHATE=this.addItem(56, "item.sodium.ethylxanthate").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        SODIUM_ETHYLATE=this.addItem(57, "item.sodium.ethylate").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);


        RETICLE_ADVANCED_SYSTEM_ON_CHIP=this.addItem(58, "reticle.advanced_system_on_chip").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        RETICLE_CENTRAL_PROCESSING_UNIT=this.addItem(59, "reticle.central_processing_unit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        RETICLE_HIGH_POWER_INTEGRATED_CIRCUIT=this.addItem(60, "reticle.high_power_integrated_circuit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        RETICLE_HIGHLY_ADVANCED_SYSTEM_ON_CHIP=this.addItem(61, "reticle.highly_advanced_system_on_chip").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        RETICLE_INTEGRATED_LOGIC_CIRCUIT=this.addItem(62, "reticle.integrated_logic_circuit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        RETICLE_LOW_POWER_INTEGRATED_CIRCUIT=this.addItem(63, "reticle.low_power_integrated_circuit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        RETICLE_NAND_MEMORY_CHIP=this.addItem(64, "reticle.nand_memory_chip").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        RETICLE_NANO_CENTRAL_PROCESSING_UNIT=this.addItem(65, "reticle.nano_central_processing_unit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        RETICLE_NOR_MEMORY_CHIP=this.addItem(66, "reticle.nor_memory_chip").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        RETICLE_POWER_INTEGRATED_CIRCUIT=this.addItem(67, "reticle.power_integrated_circuit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        RETICLE_QBIT_CENTRAL_PROCESSING_UNIT=this.addItem(68, "reticle.qbit_central_processing_unit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        RETICLE_RANDOM_ACCESS_MEMORY=this.addItem(69, "reticle.random_access_memory").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        RETICLE_SIMPLE_SYSTEM_ON_CHIP=this.addItem(70, "reticle.simple_system_on_chip").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        RETICLE_SYSTEM_ON_CHIP=this.addItem(71, "reticle.system_on_chip").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        RETICLE_ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT=this.addItem(72, "reticle.ultra_high_power_integrated_circuit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        RETICLE_ULTRA_LOW_POWER_INTEGRATED_CIRCUIT=this.addItem(73, "reticle.ultra_low_power_integrated_circuit").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);
        RETICLE_SILICON=this.addItem(74, "reticle.silicon").setMaxStackSize(64).setCreativeTabs(CommonProxy.GTQTCore_TAB);

        GRINDBALL_SOAPSTONE = this.addItem(370, "mill.grindball_soapstone").setMaxStackSize(1).addComponents(new MillBallBehavior());
        GRINDBALL_ALUMINIUM = this.addItem(371, "mill.grindball_aluminium").setMaxStackSize(1).addComponents(new MillBallBehavior());

        WRAP_CIRCUIT_ULV = this.addItem(400, "wrap.circuit.ulv");
        WRAP_CIRCUIT_LV = this.addItem(401, "wrap.circuit.lv");
        WRAP_CIRCUIT_MV = this.addItem(402, "wrap.circuit.mv");
        WRAP_CIRCUIT_HV = this.addItem(403, "wrap.circuit.hv");
        WRAP_CIRCUIT_EV = this.addItem(404, "wrap.circuit.ev");
        WRAP_CIRCUIT_IV = this.addItem(405, "wrap.circuit.iv");
        WRAP_CIRCUIT_LuV = this.addItem(406, "wrap.circuit.luv");
        WRAP_CIRCUIT_ZPM = this.addItem(407, "wrap.circuit.zpm");
        WRAP_CIRCUIT_UV = this.addItem(408, "wrap.circuit.uv");
        WRAP_CIRCUIT_UHV = this.addItem(409, "wrap.circuit.uhv");
        WRAP_CIRCUIT_UEV = this.addItem(410, "wrap.circuit.uev");
        WRAP_CIRCUIT_UIV = this.addItem(411, "wrap.circuit.uiv");
        WRAP_CIRCUIT_UXV = this.addItem(412, "wrap.circuit.uxv");
        WRAP_CIRCUIT_OpV = this.addItem(413, "wrap.circuit.opv");
        WRAP_CIRCUIT_MAX = this.addItem(414, "wrap.circuit.max");

        PROSPECTOR_UV = addItem(415, "prospector.uv")
                .addComponents(ElectricStats.createElectricItem(16_000_000_000L, GTValues.UV),
                        new ProspectorScannerBehavior(7, GTValues.UV))
                .setMaxStackSize(1)
                .setCreativeTabs(GregTechAPI.TAB_GREGTECH_TOOLS);

        PROSPECTOR_UV = addItem(416, "prospector.uiv")
                .addComponents(ElectricStats.createElectricItem(256_000_000_000L, GTValues.UIV),
                        new ProspectorScannerBehavior(9, GTValues.UIV))
                .setMaxStackSize(1)
                .setCreativeTabs(GregTechAPI.TAB_GREGTECH_TOOLS);
        DEBUG_STRUCTURE_WRITER = this.addItem(9999, "debug.structure_writer").addComponents(StructureWriteBehavior.INSTANCE);
        DEBUG_STRUCTURE_BUILDER = this.addItem(10000, "debug.structure_builder").addComponents(new MultiblockBuilderBehavior());
    }
}
