package keqing.gtqtcore.client.handler;



import keqing.gtqtcore.client.utils.TitleUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("MethodMayBeStatic")
public class ClientEventHandler {
    public static final ClientEventHandler INSTANCE = new ClientEventHandler();

    private ClientEventHandler() {
    }

    @SubscribeEvent
    public void onServerConnected(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        TitleUtils.setRandomTitleSync(String.format("*%s*", event.getManager().getRemoteAddress()));
    }

    @SubscribeEvent
    public void onServerDisconnected(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        TitleUtils.setRandomTitleSync();
    }
}