package keqing.gtqtcore.integration.append.items;

import appeng.api.storage.channels.IFluidStorageChannel;
import appeng.api.storage.data.IAEFluidStack;
import appeng.core.Api;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class FluidStorageCell extends StorageCell<IAEFluidStack>{

    public FluidStorageCell(int types, int kilobytes, Item component) {
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

	@Override
	public IFluidStorageChannel getChannel() {
		return Api.INSTANCE.storage().getStorageChannel(IFluidStorageChannel.class);
	}
}
