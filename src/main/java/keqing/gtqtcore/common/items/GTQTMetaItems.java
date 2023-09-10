package keqing.gtqtcore.common.items;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.StandardMetaItem;

public class GTQTMetaItems {
    /*
    在此处提供物品的实例
    例如：
    public static MetaItem<?>.MetaValueItem 你物品的名字，记得全大写;
     */
    public static StandardMetaItem metaItem1;

    public static MetaItem<?>.MetaValueItem TEST;

    public static void init()
    {
        metaItem1 = new StandardMetaItem();
        metaItem1.setRegistryName("gtqt_meta_item_1");
    }

    public static void initSubItems()
    {
        StandardMetaItem.registerItems();
    }

    private static void registerSubItems() {
        //下面是一个例子，第一个数据（0）是Meta Data，不能重复
        //"test"是该物品的Meta ID，例如circuit.ulv之类的
        //TEST = metaItem1.addItem(0, "test");
    }

}
