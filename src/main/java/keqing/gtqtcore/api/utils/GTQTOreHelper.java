package keqing.gtqtcore.api.utils;

import keqing.gtqtcore.common.items.GTQTMetaItems;
import net.minecraft.item.ItemStack;

public class GTQTOreHelper {
    public static String getInfo(int dimension, int type) {
        // 交错
        if (dimension == 20 && type == 1) return "岩石种类：沼泽 变质岩";
        if (dimension == 20 && type == 2) return "岩石种类：沼泽 沉积岩";
        if (dimension == 20 && type == 3) return "岩石种类：沼泽 冲击岩";
        if (dimension == 20 && type == 4) return "岩石种类：沼泽 热液岩";
        // 交错
        if (dimension == 10 && type == 1) return "岩石种类：深渊 变质岩";
        if (dimension == 10 && type == 2) return "岩石种类：深渊 沉积岩";
        if (dimension == 10 && type == 3) return "岩石种类：深渊 冲击岩";
        if (dimension == 10 && type == 4) return "岩石种类：深渊 热液岩";

        // 末地
        if (dimension == 1 && type == 1) return "岩石种类：末地 变质岩";
        if (dimension == 1 && type == 2) return "岩石种类：末地 沉积岩";
        if (dimension == 1 && type == 3) return "岩石种类：末地 冲击岩";
        if (dimension == 1 && type == 4) return "岩石种类：末地 热液岩";

        // 主世界
        if (dimension == 0 && type == 1) return "岩石种类：主世界 变质岩";
        if (dimension == 0 && type == 2) return "岩石种类：主世界 沉积岩";
        if (dimension == 0 && type == 3) return "岩石种类：主世界 冲击岩";
        if (dimension == 0 && type == 4) return "岩石种类：主世界 热液岩";

        // 地狱
        if (dimension == -1) {
            if (type == 1) return "岩石种类：地狱 岩浆冲击岩";
            if (type == 2) return "岩石种类：地狱 岩浆沉积岩";
            if (type == 3) return "岩石种类：地狱 岩浆侵入岩";
            if (type == 4) return "岩石种类：地狱 岩浆侵入岩";
        }

        return "null";
    }

    public static ItemStack setOre(int dimension, int type) {
        if (dimension == -1) {
            if (type == 1) return new ItemStack(GTQTMetaItems.DIAPHRAGMATIC.getMetaItem(), 1, 185);
            if (type == 2) return new ItemStack(GTQTMetaItems.ORTHOSTATIC.getMetaItem(), 1, 186);
            if (type == 3) return new ItemStack(GTQTMetaItems.METAMORPHIC.getMetaItem(), 1, 187);
            if (type == 4) return new ItemStack(GTQTMetaItems.METAMORPHIC.getMetaItem(), 1, 187);
        }

        if (dimension == 0) {
            if (type == 1) return new ItemStack(GTQTMetaItems.BLANK.getMetaItem(), 1, 181);
            if (type == 2) return new ItemStack(GTQTMetaItems.HYDROTHERMAL.getMetaItem(), 1, 182);
            if (type == 3) return new ItemStack(GTQTMetaItems.MAGMATIC_HYDROTHERMAL.getMetaItem(), 1, 183);
            if (type == 4) return new ItemStack(GTQTMetaItems.ALLUVIAL.getMetaItem(), 1, 184);
        }
        if (dimension == 1) {
            if (type == 1) return new ItemStack(GTQTMetaItems.END1.getMetaItem(), 1, 188);
            if (type == 2) return new ItemStack(GTQTMetaItems.END2.getMetaItem(), 1, 189);
            if (type == 3) return new ItemStack(GTQTMetaItems.END3.getMetaItem(), 1, 190);
            if (type == 4) return new ItemStack(GTQTMetaItems.END4.getMetaItem(), 1, 191);
        }
        if (dimension == 10) {
            if (type == 1) return new ItemStack(GTQTMetaItems.BNT1.getMetaItem(), 1, 196);
            if (type == 2) return new ItemStack(GTQTMetaItems.BNT2.getMetaItem(), 1, 197);
            if (type == 3) return new ItemStack(GTQTMetaItems.BNT3.getMetaItem(), 1, 198);
            if (type == 4) return new ItemStack(GTQTMetaItems.BNT4.getMetaItem(), 1, 199);
        }
        if (dimension == 20) {
            if (type == 1) return new ItemStack(GTQTMetaItems.BET1.getMetaItem(), 1, 192);
            if (type == 2) return new ItemStack(GTQTMetaItems.BET2.getMetaItem(), 1, 193);
            if (type == 3) return new ItemStack(GTQTMetaItems.BET3.getMetaItem(), 1, 194);
            if (type == 4) return new ItemStack(GTQTMetaItems.BET4.getMetaItem(), 1, 195);
        }
        return ItemStack.EMPTY;
    }
}
