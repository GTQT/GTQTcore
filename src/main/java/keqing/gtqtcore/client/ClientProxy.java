package keqing.gtqtcore.client;

import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.client.renderer.handler.StructureSelectRenderer;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.client.utils.TitleUtils;
import keqing.gtqtcore.common.CommonProxy;

import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber({Side.CLIENT})
public class ClientProxy extends CommonProxy {
    public ClientProxy() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void construction() {
        super.construction();
        TitleUtils.setRandomTitle("*Construction*");
    }
    public void preInit() {
        super.preInit();
        TitleUtils.setRandomTitle("*PreInit*");
    }
    public void init() {
        super.init();
        TitleUtils.setRandomTitle("*PreInit*");
    }
    @Override
    public void postInit() {
        super.postInit();
        TitleUtils.setRandomTitle("*PostInit*");
    }
    @Override
    public void loadComplete() {
        super.loadComplete();
        TitleUtils.setRandomTitle(null);
    }

    public void preLoad()
    {
        super.preLoad();
        GTQTTextures.init();
        GTQTTextures.preInit();
    }
    @SubscribeEvent
    public static void onRenderWorldLast(RenderWorldLastEvent event) {
        StructureSelectRenderer.render(event);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        GTQTMetaBlocks.registerItemModels();
    }
    private long clientTick = 0;
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        clientTick++;

        if (clientTick % 200 == 0) {
            TitleUtils.setRandomTitle(null);
            clientTick=0;
        }
    }
}
