package keqing.gtqtcore.integration.append.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static keqing.gtqtcore.GTQTCore.MODID;
import static keqing.gtqtcore.common.CommonProxy.GTQTCore_TO;

@Mod.EventBusSubscriber(modid = MODID)
public class ItemRegistry {
    public static ToolPortableCellLarge LARGE_PORTABLE_CELL_2048;
    public static ToolPortableCellLarge LARGE_PORTABLE_CELL_8192;
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        LARGE_PORTABLE_CELL_2048 = new ToolPortableCellLarge(2048,63);
        LARGE_PORTABLE_CELL_2048
                .setRegistryName(MODID, "large_portable_cell_2048")
                .setTranslationKey("large_portable_cell_2048")
                .setCreativeTab(GTQTCore_TO);
        event.getRegistry().register(LARGE_PORTABLE_CELL_2048);

        LARGE_PORTABLE_CELL_8192 = new ToolPortableCellLarge(8192,63);
        LARGE_PORTABLE_CELL_8192
                .setRegistryName(MODID, "large_portable_cell_8192")
                .setTranslationKey("large_portable_cell_8192")
                .setCreativeTab(GTQTCore_TO);

        event.getRegistry().register(LARGE_PORTABLE_CELL_8192);
    }
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        registerModel(LARGE_PORTABLE_CELL_2048);
        registerModel(LARGE_PORTABLE_CELL_8192);
    }

    @SideOnly(Side.CLIENT)
    private static void registerModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}