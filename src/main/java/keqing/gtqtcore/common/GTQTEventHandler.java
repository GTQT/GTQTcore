package keqing.gtqtcore.common;

import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.StandardMetaItem;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.event.MaterialEvent;
import gregtech.api.unification.material.properties.PropertyKey;
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

import static gregtech.api.unification.material.info.MaterialFlags.*;
import static keqing.gtqtcore.GTQTCore.PACK;
import static keqing.gtqtcore.GTQTCore.VERSION;
import static keqing.gtqtcore.api.unification.material.info.EPMaterialFlags.GENERATE_COIL;
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
        TJMaterials.register();
        GCYSMaterials.init();
        GTQTMaterials.register();
        OrePrefixAdditions.init();
    }

    public static class PlayerLoginEventHandler {

        private static final String[] lines = {
                GOLD + "============================================",
                BOLD + "Welcome to GregTech QuantumTransition " + GREEN + PACK + LIGHT_PURPLE +"-"+VERSION,
                GRAY + "The current game is"+RED+" beta version",
                GRAY + "All content in this version is for preview only and does not guarantee that the game can be played according to the normal survival mode process.",
                GRAY + "communication Group 1:"+YELLOW+"1073091808"+GRAY+"(QQ)",
                GRAY + "Communication Group 2:"+YELLOW+"494136307"+GRAY+"(QQ)",
                GRAY + "Community Link:"+GREEN+" https://www.mcmod.cn/modpack/590.html ",
                GRAY + "Feedback channel:"+GREEN+" https://github.com/GTQT ",
                GOLD + "============================================"
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
