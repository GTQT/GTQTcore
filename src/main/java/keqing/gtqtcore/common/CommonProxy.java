package keqing.gtqtcore.common;

import gregtech.api.block.VariantItemBlock;
import gregtech.common.items.MetaItems;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTBlockWireCoil;
import keqing.gtqtcore.loaders.recipes.handlers.IntegratedMiningDivision;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import keqing.gtqtcore.loaders.recipes.FuelRecipes;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Objects;
import java.util.function.Function;

import static gregtech.api.GregTechAPI.HEATING_COILS;


@Mod.EventBusSubscriber(
        modid = "gtqtcore"
)
public class CommonProxy {

    public static final CreativeTabs GREGICA_TAB = new CreativeTabs("GTQTCore") {
        @Override
        public ItemStack createIcon() {
            return MetaItems.WETWARE_MAINFRAME_UHV.getStackForm();
        }
    };

    public void preInit( FMLPreInitializationEvent event ) {

    }
    public void init( FMLInitializationEvent event ) {
        FuelRecipes.init();
        IntegratedMiningDivision.init();

        for (GTQTBlockWireCoil.CoilType type : GTQTBlockWireCoil.CoilType.values()) {
            HEATING_COILS.put(GTQTMetaBlocks.WIRE_COIL.getState(type), type);
        }

    }

    public CommonProxy() {
    }

    public void preLoad() {

    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        GTQTLog.logger.info("Registering blocks...");
        IForgeRegistry<Block> registry = event.getRegistry();
        /*
        在此处注册方块
        例子：
        registry.register(方块实例);
        在注册MetaBlock时用到
        */
        registry.register(GTQTMetaBlocks.MULTI_CASING);
        registry.register(GTQTMetaBlocks.ADV_BLOCK);
        registry.register(GTQTMetaBlocks.ADV_GLASS);
        registry.register(GTQTMetaBlocks.WIRE_COIL);

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        GTQTLog.logger.info("Registering Items...");
        IForgeRegistry<Item> registry = event.getRegistry();
        /*
        在此处注册方块的物品
        例子：
        registry.register(createItemBlock(方块实例, VariantItemBlock::new));
        在注册MetaBlock时用到
        */
        registry.register(createItemBlock(GTQTMetaBlocks.MULTI_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.ADV_BLOCK, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.ADV_GLASS, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.WIRE_COIL, VariantItemBlock::new));
    }

    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
        ItemBlock itemBlock = producer.apply(block);
        itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
        return itemBlock;
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event)
    {
        GTQTLog.logger.info("Registering recipes...");

    }

}
