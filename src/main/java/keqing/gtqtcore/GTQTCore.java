package keqing.gtqtcore;

import gregtech.common.ConfigHolder;
import keqing.gtqtcore.api.GTQTAPI;
import keqing.gtqtcore.api.capability.GTQTTileCapabilities;
import keqing.gtqtcore.api.io.advancement.IAdvancementManager;
import keqing.gtqtcore.api.module.IModuleManager;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.client.ClientProxy;
import keqing.gtqtcore.common.CommonProxy;
import keqing.gtqtcore.common.MetaEntities;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import keqing.gtqtcore.common.worldgen.WorldGenAbandonedBase;
import keqing.gtqtcore.core.advancement.AdvancementManager;
import keqing.gtqtcore.core.advancement.AdvancementTriggers;
import keqing.gtqtcore.integration.GTQTIntegration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(
        modid = "gtqtcore",
        name = "GTQTcore",
        acceptedMinecraftVersions = "[1.12.2,1.13)",
        version = "0.0.1-beta",
        dependencies = "required-after:gregtech@[2.9.0-beta,);" +
                "after:gcym@[1.8.0,);" +
                "after:gregtechfoodoption@[1.8.0,);"
)
public class GTQTCore {
    public static final String PACK = "1.8.0";

    public static final String MODID = "gtqtcore";
    public static final String NAME = "GTQT Core";
    public static final String VERSION = "0624(2025/6/24)";

    @Mod.Instance(GTQTCore.MODID)
    public static GTQTCore instance;

    @SidedProxy(
            clientSide = "keqing.gtqtcore.client.ClientProxy",
            serverSide = "keqing.gtqtcore.common.CommonProxy"
    )
    public static CommonProxy proxy;
    public static ClientProxy cproxy;

    // Will be available at the Construction stage.
    public static IModuleManager moduleManager;
    // Will be available at the Pre-Initialization stage.
    public static IAdvancementManager advancementManager;
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Loading Advancement Manager and Advancement Triggers.
        advancementManager = AdvancementManager.getInstance();
        AdvancementTriggers.register();

        ConfigHolder.machines.highTierContent = true;
        ConfigHolder.machines.enableHighTierSolars = true;
        GTQTLog.init(event.getModLog());
        GTQTMetaItems.initialization();
        GTQTTileCapabilities.init();
        GTQTMetaBlocks.init();
        GTQTMetaTileEntities.initialization();
        GTQTAPI.init();
        proxy.preLoad();
        proxy.preInit();
        MetaEntities.init();

    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        GTQTIntegration.init();
        GameRegistry.registerWorldGenerator(new WorldGenAbandonedBase(), 20000);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public void construction(FMLConstructionEvent event) {
        proxy.construction();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        proxy.loadComplete();
    }
}

