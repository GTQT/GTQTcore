package keqing.gtqtcore.api.capability;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.List;

public interface ICatalystHatch extends IItemHandlerModifiable {

    List<ItemStack> getCatalystList();
    void consumeCatalyst(ItemStack catalyst,int amount);
    boolean hasCatalyst(ItemStack catalyst);
}
