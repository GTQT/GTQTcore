package keqing.gtqtcore.common;

import gregtech.api.items.toolitem.IGTTool;
import gregtech.api.items.toolitem.ToolHelper;
import gregtech.api.util.TaskScheduler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import keqing.gtqtcore.GTQTCore;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = GTQTCore.MODID)
public class ToolEventHandlers {

    /*
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void BreakEvent(BlockEvent.HarvestDropsEvent event) {
        EntityPlayer player = event.getHarvester();
        if (player != null) {
            ItemStack stack = player.getHeldItemMainhand();
            NBTTagCompound behaviorTag = ToolHelper.getBehaviorsTag(stack);
            Block block = event.getState().getBlock();
            if (behaviorTag.getBoolean("Vajra")) {


                    player.sendMessage(new TextComponentTranslation("触发精准"));
                    Item iceBlock = Item.getItemFromBlock(block);
                    final World world = event.getWorld();
                    final BlockPos icePos = event.getPos();
                    world.setBlockToAir(icePos);
                    if (event.getDrops().stream().noneMatch(drop -> drop.getItem() == iceBlock)) {
                        event.getDrops().add(new ItemStack(iceBlock));
                        ((IGTTool) stack.getItem()).playSound(player);
                    }

            }
        }

    }
    */
}
