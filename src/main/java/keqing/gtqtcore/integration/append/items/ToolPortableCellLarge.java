package keqing.gtqtcore.integration.append.items;

import appeng.core.AEConfig;
import appeng.items.tools.powered.ToolPortableCell;
import net.minecraft.item.ItemStack;

public class ToolPortableCellLarge extends ToolPortableCell {

    int Bytes;
    public ToolPortableCellLarge(int bytes) {
        super();
        Bytes = bytes;
    }

    @Override
    public int getBytes(final ItemStack cellItem) {
        return Bytes;
    }

    public double getAEMaxPower(ItemStack is) {
        return (double)AEConfig.instance().getPortableCellBattery()*((double) Bytes /512);
    }
}