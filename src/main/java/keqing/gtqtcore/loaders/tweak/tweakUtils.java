package keqing.gtqtcore.loaders.tweak;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class tweakUtils {
    /*
     * 用法：getItemStack("gendustry:env_processor", 0)    表示 物品+meta
     * 用法：getItemStack("<extrabees:alveary:5>", 4L)     表示 <物品/方块>+数量
     */
    public static ItemStack getItemStack(String itemstr) {
        return getItemStack(itemstr, 0);
    }

    public static ItemStack getItemStack(String itemstr, long num) {
        var item = getItemStack(itemstr, 0);
        item.setCount((int) num);
        return item;
    }

    public static ItemStack getItemStack(String itemstr, int meta) {
        if (itemstr.startsWith("<") && itemstr.endsWith(">"))
            itemstr = itemstr.substring(1, itemstr.length() - 1);
        String[] str = itemstr.split(":");
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(str[0], str[1]));
        if (item != null) {
            if (str.length == 3)
                return new ItemStack(item, 1, Integer.parseInt(str[2]));
            return new ItemStack(item, 1, meta);
        } else {
            return ItemStack.EMPTY;
        }
    }
}
