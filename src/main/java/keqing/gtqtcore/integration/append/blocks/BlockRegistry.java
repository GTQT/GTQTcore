package keqing.gtqtcore.integration.append.blocks;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static keqing.gtqtcore.GTQTCore.MODID;
import static keqing.gtqtcore.common.CommonProxy.GTQTCore_NE;

@Mod.EventBusSubscriber(modid = MODID)
public class BlockRegistry {

    public static BlockCraftScheduler CRAFT_SCHEDULER;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        CRAFT_SCHEDULER = new BlockCraftScheduler();
        CRAFT_SCHEDULER
                .setRegistryName(MODID, "craft_scheduler")
                .setTranslationKey("craft_scheduler")
                .setCreativeTab(GTQTCore_NE);
        event.getRegistry().register(CRAFT_SCHEDULER);
    }
}
