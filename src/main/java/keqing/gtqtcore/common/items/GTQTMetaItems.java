package keqing.gtqtcore.common.items;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.StandardMetaItem;
import keqing.gtqtcore.common.block.blocks.GTQTADVGlass;

import java.util.List;

public class GTQTMetaItems {
    /*
    在此处提供物品的实例
    例如：
    public static MetaItem<?>.MetaValueItem 你物品的名字，记得全大写;
     */
    public static final List<MetaItem<?>> ITEMS = GTQTMetaItem1.getMetaItems();
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



    public static void initialization()
    {
        GTQTMetaItem1 item1 = new GTQTMetaItem1();
    }
    public static void initSubItems()
    {
        GTQTMetaItem1.registerItems();
    }



}
