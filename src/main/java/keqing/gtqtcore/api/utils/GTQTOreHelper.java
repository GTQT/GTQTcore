package keqing.gtqtcore.api.utils;

import keqing.gtqtcore.common.items.GTQTMetaItems;
import net.minecraft.item.ItemSaddle;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

public class GTQTOreHelper {
    public static String getInfo(int kind)
    {
        //末地
        if (kind == 81) return"岩石种类：沼泽 变质岩";
        if (kind == 82) return"岩石种类：沼泽 沉积岩";
        if (kind == 83) return"岩石种类：沼泽 冲击岩";
        if (kind == 84) return"岩石种类：沼泽 热液岩";
        //末地
        if (kind == 5) return"岩石种类：末地 变质岩";
        if (kind == 6) return"岩石种类：末地 沉积岩";
        if (kind == 7) return"岩石种类：末地 冲击岩";
        if (kind == 8) return"岩石种类：末地 热液岩";
        //主世界
        if (kind == 1) return"岩石种类：主世界 变质岩";
        if (kind == 2) return"岩石种类：主世界 沉积岩";
        if (kind == 3) return"岩石种类：主世界 冲击岩";
        if (kind == 4) return"岩石种类：主世界 热液岩";

        //地狱
        if (kind == -3) return"岩石种类：地狱 岩浆冲击岩";
        if (kind == -2) return"岩石种类：地狱 岩浆沉积岩";
        if (kind == -1) return"岩石种类：地狱 岩浆侵入岩";

        return "null";
    }
    public static ItemStack setOre(int kind)
    {
        if(kind==-3)return new ItemStack(GTQTMetaItems.DIAPHRAGMATIC.getMetaItem(), 1, 185);
        if(kind==-2)return new ItemStack(GTQTMetaItems.ORTHOSTATIC.getMetaItem(), 1, 186);
        if(kind==-1)return new ItemStack(GTQTMetaItems.METAMORPHIC.getMetaItem(), 1, 187);

        if(kind==1)return new ItemStack(GTQTMetaItems.BLANK.getMetaItem(), 1, 181);
        if(kind==2)return new ItemStack(GTQTMetaItems.HYDROTHERMAL.getMetaItem(), 1, 182);
        if(kind==3)return new ItemStack(GTQTMetaItems.MAGMATIC_HYDROTHERMAL.getMetaItem(), 1, 183);
        if(kind==4)return new ItemStack(GTQTMetaItems.ALLUVIAL.getMetaItem(), 1, 184);

        if(kind==5)return new ItemStack(GTQTMetaItems.END1.getMetaItem(), 1, 188);
        if(kind==6)return new ItemStack(GTQTMetaItems.END2.getMetaItem(), 1, 189);
        if(kind==7)return new ItemStack(GTQTMetaItems.END3.getMetaItem(), 1, 190);
        if(kind==8)return new ItemStack(GTQTMetaItems.END4.getMetaItem(), 1, 191);

        if(kind==81)return new ItemStack(GTQTMetaItems.BET1.getMetaItem(), 1, 192);
        if(kind==82)return new ItemStack(GTQTMetaItems.BET2.getMetaItem(), 1, 193);
        if(kind==83)return new ItemStack(GTQTMetaItems.BET3.getMetaItem(), 1, 194);
        if(kind==84)return new ItemStack(GTQTMetaItems.BET4.getMetaItem(), 1, 195);
        return ItemStack.EMPTY;
    }
}
