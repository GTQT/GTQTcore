package keqing.gtqtcore.integration.append.tile;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static keqing.gtqtcore.GTQTCore.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class TileRegistry {
    @SubscribeEvent
    public static void registerTiles(RegistryEvent.Register<Block> event) {

    }
}