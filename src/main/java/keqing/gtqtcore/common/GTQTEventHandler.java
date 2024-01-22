package keqing.gtqtcore.common;

import gregtech.api.items.metaitem.StandardMetaItem;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.unification.material.event.MaterialEvent;
import keqing.gtqtcore.api.items.IExtendedItemBehavior;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.api.unification.OrePrefixAdditions;
import keqing.gtqtcore.api.unification.TJMaterials;
import keqing.gtqtcore.network.GCNetworkManager;
import keqing.gtqtcore.network.packets.MouseEventToSeverPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(
        modid = "gtqtcore"
)

public class GTQTEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    @SideOnly(Side.CLIENT)
    public static void onWheelEvent(MouseEvent event){
        if(event.getDwheel() == 0){
            return;
        }

        Minecraft minecraft = Minecraft.getMinecraft();
        EntityPlayer player = minecraft.player;
        if(player.isSneaking()){
            ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
            Item item = stack.getItem();
            if(item instanceof StandardMetaItem){
                StandardMetaItem metaItem = (StandardMetaItem) item;
                for(IItemBehaviour behaviour : metaItem.getBehaviours(stack)){
                    if(behaviour instanceof IExtendedItemBehavior){
                        IExtendedItemBehavior extendedItemBehavior = (IExtendedItemBehavior) behaviour;
                        if(extendedItemBehavior.canHandleWheelChange()){
                            GCNetworkManager.INSTANCE.sendPacketToServer(new MouseEventToSeverPacket(event.getDwheel()>0));
                            event.setCanceled(true);
                            return;
                        }
                    }
                }
            }
        }
    }
    public GTQTEventHandler() {
    }

    @SubscribeEvent(
            priority = EventPriority.HIGH
    )
    public static void registerMaterials(MaterialEvent event)
    {
        GTQTMaterials.register();
        TJMaterials.register();
        OrePrefixAdditions.init();
        //在此处注册材料
    }

}
