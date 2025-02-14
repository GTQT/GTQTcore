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
import static keqing.gtqtcore.common.CommonProxy.GTQTCore_NE;
import static keqing.gtqtcore.common.CommonProxy.GTQTCore_TO;

@Mod.EventBusSubscriber(modid = MODID)
public class ItemRegistry {
    public static ToolPortableCellLarge LARGE_PORTABLE_CELL_2048;
    public static ToolPortableCellLarge LARGE_PORTABLE_CELL_8192;
    public static ItemStorageCell TEST_STORAGE_CELL;
    public static Item TEST_STORAGE_CELL_PART;
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

        TEST_STORAGE_CELL = new ItemStorageCell(1,64, TEST_STORAGE_CELL_PART);
        TEST_STORAGE_CELL
                .setTranslationKey("storage_cell")
                .setRegistryName(MODID, "storage_cell")
                .setCreativeTab(GTQTCore_NE);

        event.getRegistry().register(TEST_STORAGE_CELL);

        TEST_STORAGE_CELL_PART = new Item().setRegistryName(MODID, "storage_cell_part")
                .setTranslationKey("storage_cell_part")
                 .setCreativeTab(GTQTCore_NE);

        event.getRegistry().register(TEST_STORAGE_CELL_PART);

//        event.getRegistry().register(new ItemBlock(new BlockCraftScheduler())
//                .setRegistryName(MODID, "craft_scheduler")
//                .setTranslationKey("craft_scheduler")
//                .setCreativeTab(GTQTCore_TO)
//    );
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