package keqing.gtqtcore;

import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.common.CommonProxy;
import keqing.gtqtcore.common.blocks.GTQTMetaBlocks;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = "gtqtcore",
        name="GTQTcore",
        acceptedMinecraftVersions = "[1.12.2,1.13)",
        version = "0.0.1-beta" ,
        dependencies = "required-after:gregtech@[2.7.3-beta,) ;"
)
public class GTQTCore {

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

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        GTQTLog.init(event.getModLog());
        GTQTMetaItems.init();
        GTQTMetaTileEntities.initialization();
        GTQTMetaBlocks.init();
        proxy.preLoad();

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        // TODO
    }
}

