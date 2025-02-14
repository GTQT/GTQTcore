package keqing.gtqtcore.integration.append.items;

import appeng.core.AEConfig;
import appeng.items.tools.powered.ToolPortableCell;
import net.minecraft.item.ItemStack;

public class ToolPortableCellLarge extends ToolPortableCell {

    int Bytes;
    int Types;

    public ToolPortableCellLarge(int bytes, int types) {
        super();
        Bytes = bytes;
        Types = types;
    }

    @Override
    public int getBytes(final ItemStack cellItem) {
        return Bytes;
    }

    public int getTotalTypes(ItemStack cellItem) {
        return Types;
    }

    public double getAEMaxPower(ItemStack is) {
        return (double) AEConfig.instance().getPortableCellBattery() * ((double) Bytes / 512);
    }
    public ToolPortableCell getItem() {
        return this;
    }
}