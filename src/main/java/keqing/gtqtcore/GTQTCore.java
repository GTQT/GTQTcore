package keqing.gtqtcore;

import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.client.ClientProxy;
import keqing.gtqtcore.common.CommonProxy;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import keqing.gtqtcore.integration.GTQTIntegration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;

@Mod(
        modid = "gtqtcore",
        name="GTQTcore",
        acceptedMinecraftVersions = "[1.12.2,1.13)",
        version = "0.0.1-beta" ,
        dependencies = "required-after:gregtech@[2.7.4-beta,) ;"
)
public class GTQTCore {
    public static final String PACK = "1.4.0_preview_0.8";

    public static final String MODID = "gtqtcore";
    public static final String NAME = "GTQT Core";
    public static final String VERSION = "1.0";

    @Mod.Instance(GTQTCore.MODID)
    public static GTQTCore instance;

    @SidedProxy(
            clientSide = "keqing.gtqtcore.client.ClientProxy",
            serverSide = "keqing.gtqtcore.common.CommonProxy"
    )
    public static CommonProxy proxy;
    public static ClientProxy cproxy;
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        GTQTLog.init(event.getModLog());
        GTQTMetaItems.initialization();
        GTQTMetaTileEntities.initialization();
        GTQTMetaBlocks.init();
        proxy.preLoad();
        proxy.preInit();

    }
    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        GTQTIntegration.init();
    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }
    @Mod.EventHandler
    public void construction(FMLConstructionEvent event) {
        proxy.construction();
    }
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }
    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        proxy.loadComplete();
    }
}

