package keqing.gtqtcore.common.items.metaitems;

import gregtech.api.items.toolitem.ToolHelper;
import gregtech.api.items.toolitem.behavior.IToolBehavior;

import keqing.gtqtcore.api.items.GTQTToolHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

/**
 * @see gregtech.common.ToolEventHandlers#onHarvestDrops(BlockEvent.HarvestDropsEvent)
 */
public class HarvestBehavior implements IToolBehavior {

    public static final HarvestBehavior INSTANCE = new HarvestBehavior();

    protected HarvestBehavior() {/**/}

    // ice harvesting is handled in an event elsewhere

    @Override
    public void addBehaviorNBT( ItemStack stack,  NBTTagCompound tag) {
        tag.setBoolean(GTQTToolHelper.HARVEST_KEY, true);
    }

    @Override
    public void addInformation( ItemStack stack,  World world,  List<String> tooltip,
                                ITooltipFlag flag) {
        tooltip.add(I18n.format("item.gt.tool.behavior.silk_ice"));
    }


}