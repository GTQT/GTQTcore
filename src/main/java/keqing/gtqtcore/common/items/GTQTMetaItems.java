package keqing.gtqtcore.common.items;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.StandardMetaItem;
import keqing.gtqtcore.common.block.blocks.GTQTADVGlass;
import net.minecraft.item.ItemStack;

import java.util.List;

public class GTQTMetaItems {
    /*
    在此处提供物品的实例
    例如：
    public static MetaItem<?>.MetaValueItem 你物品的名字，记得全大写;
     */
    public static final List<MetaItem<?>> ITEMS = GTQTMetaItem1.getMetaItems();
    public static MetaItem<?>.MetaValueItem IMPREGNATED_SUBSTRATE;
    public static MetaItem<?>.MetaValueItem IMPREGNATED_EPOXY;
    public static MetaItem<?>.MetaValueItem MEMORY_CARD;
    public static MetaItem<?>.MetaValueItem IMPREGNATED_PLASTIC_SUBSTRATE;
    public static MetaItem<?>.MetaValueItem IMPREGNATED_GRAPHITE_RODS;
    public static MetaItem<?>.MetaValueItem IMPREGNATED_GRAPHITE_RODSA;
    public static MetaItem<?>.MetaValueItem COMMON_ALGAE;
    public static MetaItem<?>.MetaValueItem GREEN_ALGAE;
    public static MetaItem<?>.MetaValueItem RED_ALGAE;
    public static MetaItem<?>.MetaValueItem BROWN_ALGAE;
    public static MetaItem<?>.MetaValueItem GOLD_ALGAE;
    public static MetaItem<?>.MetaValueItem T_ALGAE;
    public static MetaItem<?>.MetaValueItem BIOLOGY_INTEGRATED_CIRCUIT;
    public static MetaItem<?>.MetaValueItem POTASSIUM_ETHYLATE;
    public static MetaItem<?>.MetaValueItem SODIUM_ETHYLATE;
    public static MetaItem<?>.MetaValueItem PINE_CONE;
    public static MetaItem<?>.MetaValueItem PINE_FRAGMENT;
    public static MetaItem<?>.MetaValueItem WRAP_CIRCUIT_ULV;
    public static MetaItem<?>.MetaValueItem WRAP_CIRCUIT_LV;
    public static MetaItem<?>.MetaValueItem WRAP_CIRCUIT_MV;
    public static MetaItem<?>.MetaValueItem WRAP_CIRCUIT_HV;
    public static MetaItem<?>.MetaValueItem WRAP_CIRCUIT_EV;
    public static MetaItem<?>.MetaValueItem WRAP_CIRCUIT_IV;
    public static MetaItem<?>.MetaValueItem WRAP_CIRCUIT_LuV;
    public static MetaItem<?>.MetaValueItem WRAP_CIRCUIT_ZPM;
    public static MetaItem<?>.MetaValueItem WRAP_CIRCUIT_UV;
    public static MetaItem<?>.MetaValueItem WRAP_CIRCUIT_UHV;
    public static MetaItem<?>.MetaValueItem WRAP_CIRCUIT_UEV;
    public static MetaItem<?>.MetaValueItem WRAP_CIRCUIT_UIV;
    public static MetaItem<?>.MetaValueItem WRAP_CIRCUIT_UXV;
    public static MetaItem<?>.MetaValueItem WRAP_CIRCUIT_OpV;
    public static MetaItem<?>.MetaValueItem WRAP_CIRCUIT_MAX;
    public static MetaItem<?>.MetaValueItem GRINDBALL_SOAPSTONE;
    public static MetaItem<?>.MetaValueItem GRINDBALL_ALUMINIUM;
    public static MetaItem<?>.MetaValueItem SODIUM_ETHYLXANTHATE;
    public static MetaItem<?>.MetaValueItem POTASSIUM_ETHYLXANTHATE;

    public static MetaItem<?>.MetaValueItem ROUGH_BIOLOGY_RESIN;
    public static MetaItem<?>.MetaValueItem PURIFIED_ALUMINIUM_MIXTURE;
    public static MetaItem<?>.MetaValueItem CELLULOSE_PULP;
    public static MetaItem<?>.MetaValueItem CELLULOSE_FIBER;
    public static MetaItem<?>.MetaValueItem CELLULOSE_FIBER_RED;
    public static MetaItem<?>.MetaValueItem CELLULOSE_FIBER_YELLOW;
    public static MetaItem<?>.MetaValueItem COMPOST;
    public static MetaItem<?>.MetaValueItem ALGAE_ACID;
    public static MetaItem<?>.MetaValueItem WOOD_PELLETS;
    public static MetaItem<?>.MetaValueItem PELLETS_MOULD;
    public static MetaItem<?>.MetaValueItem ALUMINIUM_PELLETS;
    public static MetaItem<?>.MetaValueItem IRON_CROP;
    public static MetaItem<?>.MetaValueItem TIN_CROP;
    public static MetaItem<?>.MetaValueItem COPPER_CROP;
    public static MetaItem<?>.MetaValueItem BRONZE_CROP;
    public static MetaItem<?>.MetaValueItem CARBON_CROP;
    public static MetaItem<?>.MetaValueItem GOLD_CROP;
    public static MetaItem<?>.MetaValueItem PROSPECTOR_UV;
    public static MetaItem<?>.MetaValueItem PROSPECTOR_UIV;

    public static MetaItem<?>.MetaValueItem RETICLE_ADVANCED_SYSTEM_ON_CHIP;
    public static MetaItem<?>.MetaValueItem RETICLE_CENTRAL_PROCESSING_UNIT;
    public static MetaItem<?>.MetaValueItem RETICLE_INTEGRATED_LOGIC_CIRCUIT;
    public static MetaItem<?>.MetaValueItem RETICLE_LOW_POWER_INTEGRATED_CIRCUIT;
    public static MetaItem<?>.MetaValueItem RETICLE_NAND_MEMORY_CHIP;
    public static MetaItem<?>.MetaValueItem RETICLE_NANO_CENTRAL_PROCESSING_UNIT;
    public static MetaItem<?>.MetaValueItem RETICLE_NOR_MEMORY_CHIP;
    public static MetaItem<?>.MetaValueItem RETICLE_POWER_INTEGRATED_CIRCUIT;
    public static MetaItem<?>.MetaValueItem RETICLE_QBIT_CENTRAL_PROCESSING_UNIT;
    public static MetaItem<?>.MetaValueItem RETICLE_RANDOM_ACCESS_MEMORY;
    public static MetaItem<?>.MetaValueItem RETICLE_SIMPLE_SYSTEM_ON_CHIP;
    public static MetaItem<?>.MetaValueItem RETICLE_SYSTEM_ON_CHIP;
    public static MetaItem<?>.MetaValueItem RETICLE_ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT;
    public static MetaItem<?>.MetaValueItem RETICLE_ULTRA_LOW_POWER_INTEGRATED_CIRCUIT;
    public static MetaItem<?>.MetaValueItem RETICLE_HIGH_POWER_INTEGRATED_CIRCUIT;
    public static MetaItem<?>.MetaValueItem RETICLE_HIGHLY_ADVANCED_SYSTEM_ON_CHIP;
    public static MetaItem<?>.MetaValueItem RETICLE_SILICON;
    public static MetaItem<?>.MetaValueItem DEBUG_STRUCTURE_WRITER;
    public static MetaItem<?>.MetaValueItem DEBUG_STRUCTURE_BUILDER;
    public static MetaItem<?>.MetaValueItem COMPUTERMINER;
    public static MetaItem<?>.MetaValueItem COMPUTERCARGO;
    public static MetaItem<?>.MetaValueItem COMPUTERBUGGY;
    public static MetaItem<?>.MetaValueItem COMPUTERTIER6;
    public static MetaItem<?>.MetaValueItem COMPUTERTIER5;
    public static MetaItem<?>.MetaValueItem COMPUTERTIER4;
    public static MetaItem<?>.MetaValueItem COMPUTERTIER3;
    public static MetaItem<?>.MetaValueItem COMPUTERTIER2;
    public static MetaItem<?>.MetaValueItem COMPUTERTIER1;
    public static MetaItem<?>.MetaValueItem LANDER_MODULE3;
    public static MetaItem<?>.MetaValueItem LANDER_MODULE2;
    public static MetaItem<?>.MetaValueItem LANDER_MODULE1;
    public static MetaItem<?>.MetaValueItem HEAVY_ALLOY_PLATE;
    public static MetaItem<?>.MetaValueItem HEAVY_ALLOY_PLATEA;
    public static MetaItem<?>.MetaValueItem HEAVY_ALLOY_PLATEB;
    public static MetaItem<?>.MetaValueItem HEAVY_ALLOY_PLATEC;
    public static MetaItem<?>.MetaValueItem HEAVY_ALLOY_PLATED;
    public static MetaItem<?>.MetaValueItem HEAVY_ALLOY_PLATEE;


    public static void initialization()
    {
        GTQTMetaItem1 item1 = new GTQTMetaItem1();
    }
    public static void initSubItems()
    {
        GTQTMetaItem1.registerItems();
    }



}
