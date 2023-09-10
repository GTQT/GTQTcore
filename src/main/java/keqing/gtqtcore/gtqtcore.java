package keqing.gtqtcore;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = "gtqtcore",
        name="GTQTcore",
        acceptedMinecraftVersions = "[1.12.2,1.13)",
        version = "0.0.1-beta" ,
        dependencies = "required-after:gregtech@[2.7.3-beta,) ;"
)
public class gtqtcore {
    public static final String MODID = "gtqtcore";
    public static final String NAME = "GTQT Core";
    public static final String VERSION = "1.0";

    @Mod.Instance(gtqtcore.MODID)
    public static gtqtcore instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // TODO
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        // TODO
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        // TODO
    }
}

