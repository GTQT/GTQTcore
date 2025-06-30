package keqing.gtqtcore.common;

import gregtech.api.GregTechAPI;
import gregtech.api.block.VariantItemBlock;
import gregtech.api.cover.CoverDefinition;
import gregtech.api.recipes.properties.impl.FusionEUToStartProperty;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.crafting.ToolHeadReplaceRecipe;
import gregtech.integration.crafttweaker.block.CTHeatingCoilBlockStats;
import gregtech.loaders.recipe.CraftingComponent;
import keqing.gtqtcore.GTQTCore;
import keqing.gtqtcore.GTQTCoreConfig;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.EnzymesReactionProperty;
import keqing.gtqtcore.api.recipes.properties.NeutronActivatorIOPartProperty;
import keqing.gtqtcore.api.recipes.properties.PCBFactoryBioUpgradeProperty;
import keqing.gtqtcore.api.recipes.properties.SwarmTierProperty;
import keqing.gtqtcore.api.unification.ore.GTQTStoneTypes;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.common.block.GTQTCrops;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockWireCoil;
import keqing.gtqtcore.common.block.blocks.GTQTStoneVariantBlock;
import keqing.gtqtcore.common.covers.GTQTCoverBehavior;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.items.metaitems.GTQTMetaToolItems;
import keqing.gtqtcore.common.pipelike.pressure.BlockPressurePipe;
import keqing.gtqtcore.common.pipelike.pressure.ItemBlockPressurePipe;
import keqing.gtqtcore.common.pipelike.pressure.tile.TileEntityPressurePipe;
import keqing.gtqtcore.loaders.AddHighTierMaterial;
import keqing.gtqtcore.loaders.OreDictionaryLoader;
import keqing.gtqtcore.loaders.recipes.*;
import keqing.gtqtcore.loaders.recipes.component.ComponentAssemblerRecipes;
import keqing.gtqtcore.loaders.recipes.component.MaterialComponents;
import keqing.gtqtcore.loaders.recipes.handlers.*;
import keqing.gtqtcore.loaders.tweak.tweakRecipesManager;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.function.Function;

import static gregtech.api.GregTechAPI.HEATING_COILS;
import static keqing.gtqtcore.api.utils.ChatCalculatorHelper.eval;
import static keqing.gtqtcore.common.block.GTQTMetaBlocks.*;
import static net.minecraft.init.Blocks.DIRT;

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
    public static final CreativeTabs GTQTCore_CO = new CreativeTabs("gtqtco") {
        @Override
        public ItemStack createIcon() {
            return GTQTMetaItems.RETICLE_GRID_MKI.getStackForm();
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

    public CommonProxy() {
    }

    @SubscribeEvent
    public static void registerRecipeHandlers(RegistryEvent.Register<IRecipe> event) {
        GTQTLog.logger.info("Registering recipe handlers...");
        MaterialRecipeHandler.register();
        PipeRecipeHandler.register();
        PartRecipeHandler.register();
        WireCombinationHandler.init();
        WireRecipeHandler.init();
        GemHandler.init();
    }

    @SubscribeEvent
    public static void registerCoverBehavior(GregTechAPI.RegisterEvent<CoverDefinition> event) {
        GTQTLog.logger.info("Registering Cover Behaviors...");
        GTQTCoverBehavior.init();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        GTQTLog.logger.info("Registering blocks...");
        IForgeRegistry<Block> registry = event.getRegistry();
        /*
        在此处注册方块
        例子：
        registry.register(方块实例);
        在注册MetaBlock时用到
        */
        registry.register(GTQTMetaBlocks.blockActiveUniqueCasing);
        registry.register(GTQTMetaBlocks.blockActiveUniqueCasing1);
        registry.register(GTQTMetaBlocks.blockCleanroomCasing);
        registry.register(GTQTMetaBlocks.blockComponentAssemblyLineCasing);
        registry.register(GTQTMetaBlocks.blockCompressedFusionReactor);
        registry.register(GTQTMetaBlocks.blockCoolingCoil);
        registry.register(GTQTMetaBlocks.blockCrucible);
        registry.register(GTQTMetaBlocks.blockElectrolyticBath);
        registry.register(GTQTMetaBlocks.blockElectrolyticMicroscope);
        registry.register(GTQTMetaBlocks.blockEnergyCell);
        registry.register(GTQTMetaBlocks.blockEvaporationBed);
        registry.register(GTQTMetaBlocks.blockGravitonCasing);
        registry.register(GTQTMetaBlocks.blockIsaCasing);
        registry.register(GTQTMetaBlocks.blockMisc);
        registry.register(GTQTMetaBlocks.blockMultiblockCasing);
        registry.register(GTQTMetaBlocks.blockMultiblockCasing1);
        registry.register(GTQTMetaBlocks.blockMultiblockCasing2);
        registry.register(GTQTMetaBlocks.blockMultiblockCasing3);
        registry.register(GTQTMetaBlocks.blockMultiblockCasing4);
        registry.register(GTQTMetaBlocks.blockMultiblockCasing5);
        registry.register(GTQTMetaBlocks.blockMultiblockCasing6);
        registry.register(GTQTMetaBlocks.blockMultiblockCasing7);
        registry.register(GTQTMetaBlocks.FIREBOX_CASING);
        registry.register(GTQTMetaBlocks.blockMultiblockCasingActive);
        registry.register(GTQTMetaBlocks.blockMultiblockGlass);
        registry.register(GTQTMetaBlocks.blockMultiblockGlass1);
        registry.register(GTQTMetaBlocks.blockNuclearCasing);
        registry.register(GTQTMetaBlocks.blockPCBFactoryCasing);
        registry.register(GTQTMetaBlocks.blockParticleAcceleratorCasing);
        registry.register(GTQTMetaBlocks.blockQuantumCasing);
        registry.register(GTQTMetaBlocks.blockQuantumForceTransformerCasing);
        registry.register(GTQTMetaBlocks.blockStepperCasing);
        registry.register(GTQTMetaBlocks.blockTransparentCasing);
        registry.register(GTQTMetaBlocks.blockWireCoil);
        registry.register(GTQTMetaBlocks.blocksResearchSystem);
        registry.register(GTQTMetaBlocks.blockSpacetimeCompression);
        registry.register(GTQTMetaBlocks.blockStabilization);
        registry.register(GTQTMetaBlocks.blockTimeAcceleration);

        registry.register(GTQTCrops.COPPER_CROP);
        registry.register(GTQTCrops.IRON_CROP);
        registry.register(GTQTCrops.TIN_CROP);
        registry.register(GTQTCrops.GOLD_CROP);
        registry.register(GTQTCrops.BRONZE_CROP);
        registry.register(GTQTCrops.CARBON_CROP);

        registry.register(STNT);

        BLOCK_PINE_LOG.setCreativeTab(GTQTCore_TAB);
        BLOCK_PINE_SAPLING.setCreativeTab(GTQTCore_TAB);
        BLOCK_PINE_LEAVES.setCreativeTab(GTQTCore_TAB);
        registry.register(BLOCK_PINE_LOG);
        registry.register(BLOCK_PINE_SAPLING);
        registry.register(BLOCK_PINE_LEAVES);

        for (GTQTStoneVariantBlock block : GTQTMetaBlocks.GTQT_STONE_BLOCKS.values()) registry.register(block);

        for (BlockPressurePipe pipe : GTQTMetaBlocks.PRESSURE_PIPES) registry.register(pipe);


    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        GTQTLog.logger.info("Registering Items...");
        IForgeRegistry<Item> registry = event.getRegistry();
        /*
        在此处注册方块的物品
        例子：
        registry.register(createItemBlock(方块实例, VariantItemBlock::new));
        在注册MetaBlock时用到
        */

        registry.register(createItemBlock(GTQTMetaBlocks.blockActiveUniqueCasing, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockActiveUniqueCasing1, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockComponentAssemblyLineCasing, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockCompressedFusionReactor, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockCoolingCoil, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockCrucible, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockElectrolyticBath, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockElectrolyticMicroscope, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockEnergyCell, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockEvaporationBed, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockGravitonCasing, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockIsaCasing, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockMisc, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockMultiblockCasing, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockMultiblockCasing1, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockMultiblockCasing2, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockMultiblockCasing3, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockMultiblockCasing4, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockMultiblockCasing5, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockMultiblockCasing6, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockMultiblockCasing7, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.FIREBOX_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockMultiblockCasingActive, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockMultiblockGlass, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockMultiblockGlass1, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockNuclearCasing, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockPCBFactoryCasing, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockParticleAcceleratorCasing, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockQuantumCasing, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockQuantumForceTransformerCasing, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockStepperCasing, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockTransparentCasing, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockWireCoil, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blocksResearchSystem, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockSpacetimeCompression, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockStabilization, VariantItemBlock::new));
        registry.register(createItemBlock(GTQTMetaBlocks.blockTimeAcceleration, VariantItemBlock::new));
        registry.register(createItemBlock(blockCleanroomCasing, VariantItemBlock::new));

        registry.register(createItemBlock(STNT, ItemBlock::new));

        registry.register(createItemBlock(BLOCK_PINE_LOG, ItemBlock::new));
        registry.register(createItemBlock(BLOCK_PINE_SAPLING, ItemBlock::new));
        registry.register(createItemBlock(BLOCK_PINE_LEAVES, ItemBlock::new));

        for (GTQTStoneVariantBlock block : GTQTMetaBlocks.GTQT_STONE_BLOCKS.values())
            registry.register(createItemBlock(block, VariantItemBlock::new));

        for (BlockPressurePipe pipe : GTQTMetaBlocks.PRESSURE_PIPES)
            registry.register(createItemBlock(pipe, ItemBlockPressurePipe::new));
    }

    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
        ItemBlock itemBlock = producer.apply(block);
        itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
        return itemBlock;
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        GTQTLog.logger.info("Registering recipes...");
        AddHighTierMaterial.init();

        GTQTRecipesManager.init();
        tweakRecipesManager.init();

        NeutronActivatorIOPartProperty.registeredPart(1, "质子");
        NeutronActivatorIOPartProperty.registeredPart(2, "氘核");
        NeutronActivatorIOPartProperty.registeredPart(3, "氚核");
        NeutronActivatorIOPartProperty.registeredPart(4, "氦核（α粒子）");
        NeutronActivatorIOPartProperty.registeredPart(5, "电子");
        NeutronActivatorIOPartProperty.registeredPart(6, "正电子");
        NeutronActivatorIOPartProperty.registeredPart(7, "钙离子");
        NeutronActivatorIOPartProperty.registeredPart(8, "硼离子");

        SwarmTierProperty.registerSwarmTier(1, "I");
        SwarmTierProperty.registerSwarmTier(2, "II");
        SwarmTierProperty.registerSwarmTier(3, "III");

        PCBFactoryBioUpgradeProperty.registerPCBFactoryBioUpgradeTier(1, "");

        FusionEUToStartProperty.registerFusionTier(9, "(MK4)");
        FusionEUToStartProperty.registerFusionTier(10, "(MK5)");
        FusionEUToStartProperty.registerFusionTier(11, "(MK6)");
    }

    @SubscribeEvent
    public static void registerServerChatEvents(ServerChatEvent event) {
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

    public void postInit() {
    }

    public void construction() {
    }

    public void loadComplete() {
        if (GTQTCoreConfig.debugSwitch.debugSwitch) {
            for (Material material : GregTechAPI.materialManager.getRegisteredMaterials()) {
                if (GTQTCoreConfig.debugSwitch.cableDebug) {
                    if (material.hasProperty(PropertyKey.WIRE))
                        GTQTLog.logger.info("Cable Info/Material:" + material + " Amperage:" + material.getProperty(PropertyKey.WIRE).getAmperage() + " Voltage:" + material.getProperty(PropertyKey.WIRE).getVoltage() + " LossPerBlock:" + material.getProperty(PropertyKey.WIRE).getLossPerBlock());
                }
                if (GTQTCoreConfig.debugSwitch.pipeDebug) {
                    if (material.hasProperty(PropertyKey.FLUID_PIPE))
                        GTQTLog.logger.info("Fluid Pipe Info/Material:" + material + " Priority:" + material.getProperty(PropertyKey.FLUID_PIPE).getPriority() + " MaxFluidTemperature:" + material.getProperty(PropertyKey.FLUID_PIPE).getMaxFluidTemperature() + " Throughput:" + material.getProperty(PropertyKey.FLUID_PIPE).getThroughput() + " Tanks:" + material.getProperty(PropertyKey.FLUID_PIPE).getTanks());

                    if (material.hasProperty(PropertyKey.ITEM_PIPE))
                        GTQTLog.logger.info("Item Pipe Info/Material:" + material + " Priority:" + material.getProperty(PropertyKey.ITEM_PIPE).getPriority() + " TransferRate:" + material.getProperty(PropertyKey.ITEM_PIPE).getTransferRate());
                }
                if (GTQTCoreConfig.debugSwitch.rotorDebug) {
                    if (material.hasProperty(PropertyKey.ROTOR))
                        GTQTLog.logger.info("Rotor Info/Material:" + material + " Damage:" + material.getProperty(PropertyKey.ROTOR).getDamage() + " Durability:" + material.getProperty(PropertyKey.ROTOR).getDurability() + " Speed:" + material.getProperty(PropertyKey.ROTOR).getSpeed());
                }
                if (GTQTCoreConfig.debugSwitch.oreDebug) {
                    if (material.hasProperty(PropertyKey.ORE)) {
                        GTQTLog.logger.info("Ore Info/Material:" + material + " Name:" + material.getLocalizedName());
                    }
                }
            }
            if (GTQTCoreConfig.debugSwitch.crash) throw new RuntimeException("Since you enabled the crash see log option in config, it crashed here, and it's not a bug");
        }
    }

    public void preInit() {
        GTQTMetaToolItems.init();
        GTQTRecipes.registerTool();

        ToolHeadReplaceRecipe.setToolHeadForTool(OrePrefix.toolHeadDrill, GTQTMetaToolItems.SOLDERING_IRON_LV);
        ToolHeadReplaceRecipe.setToolHeadForTool(OrePrefix.toolHeadDrill, GTQTMetaToolItems.SOLDERING_IRON_HV);
        ToolHeadReplaceRecipe.setToolHeadForTool(OrePrefix.toolHeadDrill, GTQTMetaToolItems.SOLDERING_IRON_IV);
    }

    public void preLoad() {
        GTQTStoneTypes.init();
        GTQTcoreRecipeMaps.init();
        MinecraftForge.EVENT_BUS.register(new GTQTEventHandler.PlayerLoginEventHandler());
        GameRegistry.registerTileEntity(TileEntityPressurePipe.class, new ResourceLocation(GTQTCore.MODID, "pressure_pipe"));
    }

    public void init() {


        CopyRecipesHandlers.init();

        for (BlockWireCoil.CoilType type : BlockWireCoil.CoilType.values()) {
            HEATING_COILS.put(GTQTMetaBlocks.blockWireCoil.getState(type), type);
        }

        HEATING_COILS.put(DIRT.getDefaultState(), new CTHeatingCoilBlockStats("dirt", 300, 1, 0, 1, Materials.Iron));
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void registerMaterialComponents(GregTechAPI.RegisterEvent<CraftingComponent> event) {
        GTQTLog.logger.info("Registering material components...");
        MaterialComponents.init();
    }
}
