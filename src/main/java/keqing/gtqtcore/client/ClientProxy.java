package keqing.gtqtcore.client;

import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.CommonProxy;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber({Side.CLIENT})
public class ClientProxy extends CommonProxy {
    public ClientProxy() {
    }

    public void preLoad()
    {
        super.preLoad();
        GTQTTextures.init();
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {

    }

}
