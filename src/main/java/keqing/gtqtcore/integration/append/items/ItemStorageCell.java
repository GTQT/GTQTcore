package keqing.gtqtcore.integration.append.items;

import appeng.api.AEApi;
import appeng.api.storage.IStorageChannel;
import appeng.api.storage.channels.IItemStorageChannel;
import appeng.api.storage.data.IAEItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemStorageCell extends StorageCell<IAEItemStack>{

    public ItemStorageCell(int types, int kilobytes, Item component) {
        super(types, kilobytes, component);
    }


    @Override
    public int getBytesPerType(@Nonnull ItemStack itemStack) {
        return 8;
    }

    @Override
    public double getIdleDrain() {
        return 0.5;
    }

    @Nonnull
    @Override
    public IStorageChannel<IAEItemStack> getChannel() {
        return AEApi.instance().storage().getStorageChannel(IItemStorageChannel.class);
    }
}
