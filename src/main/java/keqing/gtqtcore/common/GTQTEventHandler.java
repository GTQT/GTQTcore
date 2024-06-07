package keqing.gtqtcore.common;

import gregtech.api.items.metaitem.StandardMetaItem;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.unification.material.event.MaterialEvent;
import keqing.gtqtcore.api.unification.GCYSMaterials;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.api.unification.OrePrefixAdditions;
import keqing.gtqtcore.api.unification.TJMaterials;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import java.util.Objects;

import static keqing.gtqtcore.GTQTCore.PACK;
import static keqing.gtqtcore.GTQTCore.VERSION;
import static net.minecraft.util.text.TextFormatting.*;


@Mod.EventBusSubscriber(
        modid = "gtqtcore"
)

public class GTQTEventHandler {

    public GTQTEventHandler() {
    }

    @SubscribeEvent(
            priority = EventPriority.HIGH
    )
    public static void registerMaterials(MaterialEvent event)
    {
        GTQTMaterials.register();
        TJMaterials.register();
        GCYSMaterials.init();
        OrePrefixAdditions.init();
        //在此处注册材料
    }
    /**
     * Player Login Event Handler.
     *
     * @author Magic_Sweepy
     *
     * <p>
     *     This class is create a {@link PlayerEvent.PlayerLoggedInEvent},
     *     when player log in world, then send message of all useful Modpack infoes to chat.
     * </p>
     *
     * @see CommonProxy#preLoad()
     *
     * @version 2.8.8-beta
     */
    public static class PlayerLoginEventHandler {

        private static final String[] lines = {
                GOLD + "==================================================================",
                BOLD + "欢迎来到格雷:量子跃迁 " + GREEN + PACK + LIGHT_PURPLE +"-"+VERSION,
                GRAY + "当前游戏为" + RED + "测试版本" + GRAY + "或" + RED + "预览版本" + GRAY + "，在游玩过程中若遇到问题请及时反馈",
                GRAY + "该版本中一切内容仅供预览，不保证能够按照正常的生存模式流程进行游戏。",
                GRAY + "官方交流群组一群：" + YELLOW + "1073091808" + GRAY + "（QQ）",
                GRAY + "官方交流群组二群：" + YELLOW + "494136307" + GRAY + "（QQ）",
                GRAY + "社群链接：" + GREEN + "https://www.mcmod.cn/modpack/590.html",
                GRAY + "问题反馈渠道：" + GREEN + "https://github.com/GTQT",
                GOLD + "=================================================================="
        };

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
            Objects.requireNonNull(event.player);
            for (String line : lines) {
                event.player.sendMessage(new TextComponentString(line));
            }
        }
    }

}
