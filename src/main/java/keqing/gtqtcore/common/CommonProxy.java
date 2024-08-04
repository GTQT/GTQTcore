package keqing.gtqtcore.common;

import gregtech.api.GregTechAPI;
import gregtech.api.block.VariantItemBlock;
import gregtech.api.cover.CoverDefinition;
import gregtech.api.recipes.recipeproperties.FusionEUToStartProperty;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.stack.ItemMaterialInfo;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.*;
import keqing.gtqtcore.api.unification.ore.GTQTStoneTypes;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.api.utils.GTQTOreHelper;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTBlockWireCoil;
import keqing.gtqtcore.common.block.blocks.GTQTCrops;
import keqing.gtqtcore.common.block.blocks.GTQTStoneVariantBlock;
import keqing.gtqtcore.common.covers.GTQTCoverBehavior;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.items.metaitems.GTQTMetaToolItems;
import keqing.gtqtcore.loaders.OreDictionaryLoader;
import keqing.gtqtcore.loaders.recipes.*;
import keqing.gtqtcore.loaders.recipes.handlers.GCYSMaterialInfoLoader;
import keqing.gtqtcore.loaders.recipes.handlers.*;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.function.Function;

import static gregtech.api.GregTechAPI.HEATING_COILS;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static keqing.gtqtcore.api.capability.chemical_plant.ChemicalPlantProperties.registerCasingTier;
import static keqing.gtqtcore.api.unification.material.info.EPMaterialFlags.GENERATE_COIL;
import static keqing.gtqtcore.api.utils.ChatCalculatorHelper.eval;
import static keqing.gtqtcore.common.block.GTQTMetaBlocks.*;


@Mod.EventBusSubscriber(
        modid = "gtqtcore"
)
public class CommonProxy {

    public static final CreativeTabs GTQTCore_TAB = new CreativeTabs("gtqtcore") {
        @Override
        public ItemStack createIcon() {
            return GTQTMetaItems.SUPRACAUSAL_MAINFRAME.getStackForm();
        }
    };

    public static final CreativeTabs GTQTCore_BIO = new CreativeTabs("gtqtbio") {
        @Override
        public ItemStack createIcon() {
            return GTQTMetaItems.DNA_ENCODER.getStackForm();
        }
    };
    public static final CreativeTabs GTQTCore_NE = new CreativeTabs("gtqtae") {
        @Override
        public ItemStack createIcon() {
            return GTQTMetaItems.AE_C.getStackForm();
        }
    };
    public static final CreativeTabs GTQTCore_CH = new CreativeTabs("gtqtch") {
        @Override
        public ItemStack createIcon() {
            return GTQTMetaItems.BZ_REACTION_CHAMBER.getStackForm();
        }
    };
    public static final CreativeTabs GTQTCore_NC = new CreativeTabs("gtqtnc") {
        @Override
        public ItemStack createIcon() {
            return GTQTMetaItems.NAQUADAH_ROD_QUAD.getStackForm();
        }
    };

    public static final CreativeTabs GTQTCore_HP = new CreativeTabs("gtqthp") {
        @Override
        public ItemStack createIcon() {
            return GTQTMetaItems.QUANTUM_ANOMALY.getStackForm();
        }
    };

    public static final CreativeTabs GTQTCore_GD = new CreativeTabs("gtqtgd") {
        @Override
        public ItemStack createIcon() {
            return GTQTMetaItems.ND_YAG_LASER.getStackForm();
        }
    };
    public static final CreativeTabs GTQTCore_DISK = new CreativeTabs("gtqtds") {
        @Override
        public ItemStack createIcon() {
            return GTQTMetaItems.DISK_0.getStackForm();
        }
    };
    public static final CreativeTabs GTQTCore_TO = new CreativeTabs("gtqtto") {
        @Override
        public ItemStack createIcon() {
            return GTQTMetaItems.DEBUG_STRUCTURE_WRITER.getStackForm();
        }
    };
    public static final CreativeTabs GTQTCore_PA = new CreativeTabs("gtqttpa") {
        @Override
        public ItemStack createIcon() {
            return GTQTMetaItems.ALPHA.getStackForm();
        }
    };

    public void postInit() {
    }
    public void construction() {
    }
    public void loadComplete() {
    }
    public void preInit() {
        GTQTMetaToolItems.init();
        GTQTRecipes.registerTool();
    }
    public void preLoad(){
        GTQTStoneTypes.init();
        GTQTcoreRecipeMaps.init();
        MinecraftForge.EVENT_BUS.register(new GTQTEventHandler.PlayerLoginEventHandler());
    }
    public void init() {
        OreDictionaryLoader.init();
        MiscMachineRecipes.init();
        IntegratedMiningDivision.init();
        HeatExchangeRecipes.init();
        ELE.init();
        OreDeal.init();
        KeQingNET.init();
        ISA.init();
        QTF.init();
        ComponentAssemblyLineRecipes.init();
        ComponentAssemblerRecipes.init();
        RocketEngineRecipes.init();
        GTQTRecipesManager.init();
        WrapCircuits.init();
        MetaTileEntityLoader.init();
        MetaTileEntityMachine.init();
        /*
        for (Material material : GregTechAPI.materialManager.getRegisteredMaterials())
        {
             if(material.hasProperty(PropertyKey.WIRE))
                GTQTLog.logger.info(new TextComponentTranslation("",material,material.getProperty(PropertyKey.WIRE).getAmperage(),material.getProperty(PropertyKey.WIRE).getVoltage(),material.getProperty(PropertyKey.WIRE).getLossPerBlock()));
        }

         */
        for (GTQTBlockWireCoil.CoilType type : GTQTBlockWireCoil.CoilType.values()) {
            HEATING_COILS.put(GTQTMetaBlocks.WIRE_COIL.getState(type), type);
        }
    }

    public CommonProxy() {
    }

    @SubscribeEvent
    public static void registerCoverBehavior(GregTechAPI.RegisterEvent<CoverDefinition> event) {
        GTQTLog.logger.info("Registering Cover Behaviors...");
        GTQTCoverBehavior.init();
    }

    @SubscribeEvent
    public static void initMaterialInfo(GregTechAPI.RegisterEvent<ItemMaterialInfo> event) {
        GCYSMaterialInfoLoader.init();
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
        registry.register(GTQTMetaBlocks.GLASS_CASING);
        registry.register(GTQTMetaBlocks.ADV_GLASS);
        registry.register(GTQTMetaBlocks.WIRE_COIL);
        registry.register(GTQTMetaBlocks.QUANTUM_CASING);
        registry.register(GTQTMetaBlocks.GRAVITON_CASING);
        registry.register(GTQTMetaBlocks.TURBINE_CASING);
        registry.register(GTQTMetaBlocks.TURBINE_CASING1);
        registry.register(GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING);
        registry.register(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE);
        registry.register(GTQTMetaBlocks.ELECTRON_MICROSCOPE);
        registry.register(GTQTMetaBlocks.PARTICLE_ACCELERATOR);
        registry.register(GCYL_CLEANROOM_CASING);
        registry.register(EVAPORATION_BED);
        registry.register(GTQTMetaBlocks.ISA_CASING);
        registry.register(GTQTMetaBlocks.ACTIVE_UNIQUE_CASING);
        registry.register(GTQTMetaBlocks.COMPRESSED_FUSION_REACTOR);
        registry.register(GTQTMetaBlocks.ROAD);
        registry.register(GTQTMetaBlocks.ELECTROBATH);
        registry.register(GTQTMetaBlocks.NUCLEAR_FUSION);
        registry.register(GTQTMetaBlocks.KQCC);
        registry.register(GTQTMetaBlocks.COOLING_COIL);
        registry.register(GTQTMetaBlocks.POWER);
        registry.register(GTQTMetaBlocks.STEPPER);
        registry.register(GTQTMetaBlocks.PCB_FACTORY_CASING);

        registry.register(GTQTCrops.COPPER_CROP);
        registry.register(GTQTCrops.IRON_CROP);
        registry.register(GTQTCrops.TIN_CROP);
        registry.register(GTQTCrops.GOLD_CROP);
        registry.register(GTQTCrops.BRONZE_CROP);
        registry.register(GTQTCrops.CARBON_CROP);

        registry.register(GTQTMetaBlocks.CRUCIBLE);
        registry.register(GTQTMetaBlocks.MULTIBLOCK_CASING);
        registry.register(GTQTMetaBlocks.MULTIBLOCK_CASING_ACTIVE);
        registry.register(GTQTMetaBlocks.TRANSPARENT_CASING);
        registry.register(STNT);

        for (GTQTStoneVariantBlock block : GTQTMetaBlocks.SUSY_STONE_BLOCKS.values()) registry.register(block);

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
        registry.register(createItemBlock(GTQTMetaBlocks.GLASS_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.ADV_GLASS, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.WIRE_COIL, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.QUANTUM_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.GRAVITON_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.TURBINE_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.TURBINE_CASING1, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.QUANTUM_CONSTRAINT_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.COMPONENT_ASSEMBLY_LINE, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.ELECTRON_MICROSCOPE, VariantItemBlock::new));
        registry.register(createItemBlock(GCYL_CLEANROOM_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.COOLING_COIL, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.EVAPORATION_BED, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.ISA_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.ACTIVE_UNIQUE_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.COMPRESSED_FUSION_REACTOR, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.ELECTROBATH, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.PARTICLE_ACCELERATOR, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.ROAD, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.STEPPER, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.KQCC, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.POWER, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.NUCLEAR_FUSION, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.PCB_FACTORY_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.CRUCIBLE, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.MULTIBLOCK_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.MULTIBLOCK_CASING_ACTIVE, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.TRANSPARENT_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(STNT, ItemBlock::new));
        for (GTQTStoneVariantBlock block : GTQTMetaBlocks.SUSY_STONE_BLOCKS.values()) registry.register(createItemBlock(block, VariantItemBlock::new));
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


        NeutronActivatorIOPartProperty.registeredPart(1,"质子");
        NeutronActivatorIOPartProperty.registeredPart(2,"氘核");
        NeutronActivatorIOPartProperty.registeredPart(3,"氚核");
        NeutronActivatorIOPartProperty.registeredPart(4,"氦核（α粒子）");
        NeutronActivatorIOPartProperty.registeredPart(5,"电子");
        registerCasingTier(0, "一级");
        registerCasingTier(1, "二级");
        registerCasingTier(2, "三级");
        registerCasingTier(3, "四级");
        registerCasingTier(4, "五级");
        registerCasingTier(5, "六级");

        int[] array = {-3,-2,-1,0,1, 2, 3, 4,5,6,7,8,81,82,83,84};
        for (int j : array) MDProperties.registeredTier(j, GTQTOreHelper.getInfo(j));


        ELEProperties.registeredTier(1,"一级");
        ELEProperties.registeredTier(2,"二级");
        ELEProperties.registeredTier(3,"三级");
        ELEProperties.registeredTier(4,"四级");
        ELEProperties.registeredTier(5,"五级");

        ERProperty.registeredRate(101,"1 0 1 0 0 酸性");
        ERProperty.registeredRate(102,"1 0 1 1 0 酸性");
        ERProperty.registeredRate(103,"1 1 0 1 0 酸性");
        ERProperty.registeredRate(104,"1 1 0 1 1 酸性");

        ERProperty.registeredRate(201,"2 1 1 3 1 碱性");
        ERProperty.registeredRate(202,"1 2 3 1 1 碱性");
        ERProperty.registeredRate(203,"1 3 2 1 1 碱性");
        ERProperty.registeredRate(204,"2 1 1 3 1 碱性");

        ERProperty.registeredRate(301,"4 1 1 3 2 酸性");
        ERProperty.registeredRate(302,"2 4 2 3 1 酸性");
        ERProperty.registeredRate(303,"2 3 2 4 1 酸性");

        ERProperty.registeredRate(401,"2 5 2 4 3 碱性");

        SwarmTierProperty.registerSwarmTier(1, "I");
        SwarmTierProperty.registerSwarmTier(2, "II");
        SwarmTierProperty.registerSwarmTier(3, "III");

        for(int i=1;i<=5;i=i+1) LASERNetProperty.registeredLaser(i, String.valueOf(i));
        for(int i=1;i<=100;i++) KQNetProperty.registeredNB(i, String.valueOf(i));
        for(int i=1;i<=100;i++) BRProperty.registeredRate(i, String.valueOf(i));
        for(int i=1;i<=100;i++) NeutronActivatorPartProperty.registeredPart(i*200, String.valueOf(i*200));
        for(int i=1;i<=10;i++) NuclearProperties.registeredminTemp(i, String.valueOf(i));
        for(int i=1;i<=10;i++) PAProperty.registeredTier(i, String.valueOf(i));

        PACasingTierProperty.registerPACasingTier(1, "1");
        PACasingTierProperty.registerPACasingTier(2, "2");
        PACasingTierProperty.registerPACasingTier(3,"3");

        PCBFactoryProperty.registerPCBFactoryTier(1, "1");
        PCBFactoryProperty.registerPCBFactoryTier(2, "2");
        PCBFactoryProperty.registerPCBFactoryTier(3, "3");
        PCBFactoryBioUpgradeProperty.registerPCBFactoryBioUpgradeTier(1, "");

        FusionEUToStartProperty.registerFusionTier(9, "(MK4)");
        FusionEUToStartProperty.registerFusionTier(10, "(MK5)");
        FusionEUToStartProperty.registerFusionTier(11, "(MK6)");
        QFTCasingTierProperty.registerQFTCasingTier(1, "1");
        QFTCasingTierProperty.registerQFTCasingTier(2, "2");
        QFTCasingTierProperty.registerQFTCasingTier(3, "3");
        QFTCasingTierProperty.registerQFTCasingTier(4, "4");

        CACasingTierProperty.registerCACasingTier(1, "1");
        CACasingTierProperty.registerCACasingTier(2, "2");
        CACasingTierProperty.registerCACasingTier(3, "3");
        CACasingTierProperty.registerCACasingTier(4, "4");
        CACasingTierProperty.registerCACasingTier(5, "5");
        CACasingTierProperty.registerCACasingTier(6, "6");
        CACasingTierProperty.registerCACasingTier(7, "7");
        CACasingTierProperty.registerCACasingTier(8, "8");
        CACasingTierProperty.registerCACasingTier(9, "9");
        CACasingTierProperty.registerCACasingTier(10, "10");
        CACasingTierProperty.registerCACasingTier(11, "11");
        CACasingTierProperty.registerCACasingTier(12, "12");
        CACasingTierProperty.registerCACasingTier(13, "13");
        CACasingTierProperty.registerCACasingTier(14, "14");
    }

    @SubscribeEvent
    public static void registerServerChatEvents( ServerChatEvent event) {
        String message = event.getMessage();

        if (!message.startsWith("="))
            return;

        if (event.getPlayer() == null)
            return;

        event.setCanceled(true);

        if (message.startsWith("=help")) { //  If player send =help, then return guide of this function.
            for (int i = 1; i <= 12; i++) {
                event.getPlayer().sendMessage(new TextComponentTranslation(String.format("gtqtcore.chat_calculator.help.%s", i), i == 3 ? new TextComponentString("%").setStyle(new Style().setColor(TextFormatting.AQUA)) : new TextComponentString[]{}));
            }
        } else {
            double result; // calculate result
            String stripped = message.substring(1); // strip the = sign
            String[] split = stripped.split(","); // split into expression and args

            event.getPlayer().sendMessage(new TextComponentString(stripped).setStyle(new Style().setColor(TextFormatting.AQUA))); // send the input to only the player
            try {
                result = eval(split[0].toLowerCase(), event.getPlayer());
            } catch (Exception e) {
                // send the error to the player
                event.getPlayer().sendMessage(new TextComponentString(e.getMessage()).setStyle(new Style().setColor(TextFormatting.RED)));
                return;
            }

            // parse arguments
            int decimalPlaces = 3;
            for (int i = 1; i < split.length; i++) {
                String arg = split[i];
                String value = arg.split("=")[1];
                if (arg.startsWith("places")) decimalPlaces = Integer.parseInt(value.replaceAll("\\s", ""));
            }

            // format output
            DecimalFormat formatter = new DecimalFormat("#.###");
            formatter.setMaximumFractionDigits(decimalPlaces);
            String formatted = formatter.format(result);

            // return output
            event.getPlayer().sendMessage(new TextComponentString(formatted).setStyle(new Style().setColor(TextFormatting.GRAY)));
        }
    }

}
