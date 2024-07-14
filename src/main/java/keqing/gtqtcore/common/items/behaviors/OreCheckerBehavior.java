package keqing.gtqtcore.common.items.behaviors;

import gregtech.api.items.metaitem.stats.IItemBehaviour;
import keqing.gtqtcore.api.utils.GTQTOreHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import java.util.List;

public class OreCheckerBehavior implements IItemBehaviour {
    public OreCheckerBehavior() {
    }
    public void addInformation(ItemStack stack, List<String> lines) {
        lines.add(I18n.format("使用地震探测器获取当前区块地质"));
        if (stack.hasTagCompound()) {
            NBTTagCompound compound = stack.getTagCompound();
            int kind = compound.getInteger("Kind");
            lines.add(I18n.format(GTQTOreHelper.getInfo(kind)));
        }
    }


}

