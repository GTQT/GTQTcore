package keqing.gtqtcore.common;

import gregtech.api.block.VariantItemBlock;
import gregtech.api.recipes.recipeproperties.FusionEUToStartProperty;
import gregtech.common.items.MetaItems;
import keqing.gtqtcore.api.recipes.properties.CACasingTierProperty;
import keqing.gtqtcore.api.recipes.properties.ELEProperties;
import keqing.gtqtcore.api.recipes.properties.PCBPartProperty;
import keqing.gtqtcore.api.recipes.properties.QFTCasingTierProperty;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTBlockWireCoil;
import keqing.gtqtcore.common.block.blocks.GTQTCrops;
import keqing.gtqtcore.common.items.metaitems.GTQTMetaToolItems;
import keqing.gtqtcore.loaders.recipes.*;
import keqing.gtqtcore.loaders.recipes.handlers.*;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Objects;
import java.util.function.Function;

import static gregtech.api.GregTechAPI.HEATING_COILS;
import static keqing.gtqtcore.api.capability.chemical_plant.ChemicalPlantProperties.registerCasingTier;
import static keqing.gtqtcore.common.block.GTQTMetaBlocks.*;


@Mod.EventBusSubscriber(
        modid = "gtqtcore"
)
public class CommonProxy {

    public static final CreativeTabs GTQTCore_TAB = new CreativeTabs("GTQTCore") {
        @Override
        public ItemStack createIcon() {
            return MetaItems.WETWARE_MAINFRAME_UHV.getStackForm();
        }
    };

    public void preInit( FMLPreInitializationEvent event ) {
        GTQTMetaToolItems.init();
        GTQTRecipes.registerTool();
    }
    public void init( FMLInitializationEvent event ) {
        FuelRecipes.init();
        IntegratedMiningDivision.init();
        HeatExchangeRecipes.init();
        ELE.init();
        KeQingNET.init();
        ISA.init();
        QTF.init();
        ComponentAssemblyLineRecipes.init();
        RocketEngineRecipes.init();
        GTQTRecipesManager.init();
        WrapCircuits.init();
        MetaTileEntityLoader.init();
        MetaTileEntityMachine.init();
        OreRecipeHandler.register();



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
        registry.register(GTQTMetaBlocks.QUANTUM_CASING);
        registry.register(GTQTMetaBlocks.TURBINE_CASING);
        registry.register(GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING);
        registry.register(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE);
        registry.register(GTQTMetaBlocks.ISA_CASING);
        registry.register(GTQTMetaBlocks.ELECTROBATH);

        registry.register(GTQTCrops.COPPER_CROP);
        registry.register(GTQTCrops.IRON_CROP);
        registry.register(GTQTCrops.TIN_CROP);
        registry.register(GTQTCrops.BRONZE_CROP);
        registry.register(GTQTCrops.CARBON_CROP);

        PINE_LOG.setCreativeTab(GTQTCore_TAB);
        PINE_SAPLING.setCreativeTab(GTQTCore_TAB);
        PINE_LEAVES.setCreativeTab(GTQTCore_TAB);
        registry.register(PINE_LOG);
        registry.register(PINE_SAPLING);
        registry.register(PINE_LEAVES);

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
        registry.register(createItemBlock(GTQTMetaBlocks.QUANTUM_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.TURBINE_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.ISA_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.ELECTROBATH, VariantItemBlock::new));

        registry.register(createItemBlock(PINE_LOG, ItemBlock::new));
        registry.register(createItemBlock(PINE_SAPLING, ItemBlock::new));
        registry.register(createItemBlock(PINE_LEAVES, ItemBlock::new));
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

        registerCasingTier(0, "一级");
        registerCasingTier(1, "二级");
        registerCasingTier(2, "三级");
        registerCasingTier(3, "四级");
        registerCasingTier(4, "五级");
        registerCasingTier(5, "六级");

        ELEProperties.registeredTier(1,"一级");
        ELEProperties.registeredTier(2,"二级");
        ELEProperties.registeredTier(3,"三级");
        ELEProperties.registeredTier(4,"四级");
        ELEProperties.registeredTier(5,"五级");

        PCBPartProperty.registeredPart(1,"微生物培养仓");
        PCBPartProperty.registeredPart(2,"化学辅助计算机");
        FusionEUToStartProperty.registerFusionTier(9, "(MK4)");
        FusionEUToStartProperty.registerFusionTier(10, "(MK5)");
        FusionEUToStartProperty.registerFusionTier(11, "(MK6)");
        QFTCasingTierProperty.registerQFTCasingTier(1, I18n.format("gtqtcore.machine.quantum_force_transformer.tier.1"));
        QFTCasingTierProperty.registerQFTCasingTier(2, I18n.format("gtqtcore.machine.quantum_force_transformer.tier.2"));
        QFTCasingTierProperty.registerQFTCasingTier(3, I18n.format("gtqtcore.machine.quantum_force_transformer.tier.3"));
        QFTCasingTierProperty.registerQFTCasingTier(4, I18n.format("gtqtcore.machine.quantum_force_transformer.tier.4"));

        CACasingTierProperty.registerCACasingTier(1, I18n.format("gtqtcore.machine.component_assembly_line.tier.1"));
        CACasingTierProperty.registerCACasingTier(2, I18n.format("gtqtcore.machine.component_assembly_line.tier.2"));
        CACasingTierProperty.registerCACasingTier(3, I18n.format("gtqtcore.machine.component_assembly_line.tier.3"));
        CACasingTierProperty.registerCACasingTier(4, I18n.format("gtqtcore.machine.component_assembly_line.tier.4"));
        CACasingTierProperty.registerCACasingTier(5, I18n.format("gtqtcore.machine.component_assembly_line.tier.5"));
        CACasingTierProperty.registerCACasingTier(6, I18n.format("gtqtcore.machine.component_assembly_line.tier.6"));
        CACasingTierProperty.registerCACasingTier(7, I18n.format("gtqtcore.machine.component_assembly_line.tier.7"));
        CACasingTierProperty.registerCACasingTier(8, I18n.format("gtqtcore.machine.component_assembly_line.tier.8"));
        CACasingTierProperty.registerCACasingTier(9, I18n.format("gtqtcore.machine.component_assembly_line.tier.9"));
        CACasingTierProperty.registerCACasingTier(10, I18n.format("gtqtcore.machine.component_assembly_line.tier.10"));
        CACasingTierProperty.registerCACasingTier(11, I18n.format("gtqtcore.machine.component_assembly_line.tier.11"));
        CACasingTierProperty.registerCACasingTier(12, I18n.format("gtqtcore.machine.component_assembly_line.tier.12"));
        CACasingTierProperty.registerCACasingTier(13, I18n.format("gtqtcore.machine.component_assembly_line.tier.13"));
        CACasingTierProperty.registerCACasingTier(14, I18n.format("gtqtcore.machine.component_assembly_line.tier.14"));
    }

}
