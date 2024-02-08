package keqing.gtqtcore.common.metatileentities;

import com.sun.jna.platform.win32.WinDef;
import gregicality.multiblocks.common.metatileentities.multiblockpart.MetaTileEntityParallelHatch;
import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SimpleGeneratorMetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.common.metatileentities.electric.MetaTileEntitySingleCombustion;
import gregtech.common.metatileentities.electric.MetaTileEntitySingleTurbine;
import gregtech.common.metatileentities.multi.multiblockpart.*;
import gregtech.common.metatileentities.storage.MetaTileEntityQuantumTank;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.metatileentities.multi.generators.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.huge.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.kqcc.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.kqcc.MetaTileEntityCosmicRayDetector;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.overwrite.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.star.MetaTileEntityPlasmaForge;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.star.MetaTileEntityStarBiomimeticFactory;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.star.MetaTileEntityStarMixer;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.steam.MetaTileEntitySteamExtractor;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.steam.MetaTileEntitySteamBlastFurnace;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.steam.MetaTileEntitySteamCompressor;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.steam.MetaTileEntitySteamOreWasher;
import keqing.gtqtcore.common.metatileentities.multi.multiblockpart.*;

import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;

import static gregtech.api.util.GTUtility.genericGeneratorTankSizeFunction;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;
import static keqing.gtqtcore.api.GTQTValue.gtqtcoreId;

public class GTQTMetaTileEntities {

    public static void simpleTiredInit(MetaTileEntity[] tileEntities, IntFunction<MetaTileEntity> function, IntSupplier idSupplier, IntPredicate canAdd){
        for(int i = 0;i<GTValues.V.length;i++){
            if(canAdd.test(i)){
                tileEntities[i] = registerMetaTileEntity(
                        idSupplier.getAsInt(),function.apply(i));
            }
        }
    }
    public static int currentMultiPartID = 16120;
    private static int nextMultiPartID(){
        currentMultiPartID++;
        return currentMultiPartID;
    }
    public static void simpleTiredInit(MetaTileEntity[] tileEntities, IntFunction<MetaTileEntity> function, IntSupplier idSupplier){
        simpleTiredInit(tileEntities,function,idSupplier,(i) -> true);
    }
    public static MetaTileEntityOilPool OIL_POOL;

    public static MetaTileEntityAlgaeFarm ALGAE_FARM;
    public static MetaTileEntityComponentAssemblyLine COMPONENT_ASSEMBLY_LINE;
    public static MetaTileEntityBufferHatch MULTIPART_BUFFER_HATCH;
    public static MetaTileEntityBlazingBlastFurnace BLAZING_BLAST_FURNACE ;
    public static MetaTileEntityHugeChemicalReactor HUGE_CHEMICAL_REACTOR;
    public static MetaTileEntityIntegratedMiningDivision INTEGRATED_MINING_DIVISION;
    public static MetaTileEntityHugeMacerator HUGE_MACERATOR;
    public static MetaTileEntityHugeAlloyBlastSmelter HUGE_ALLOY_BLAST_FURANCE;
    public static MetaTileEntityLagerHeatExchanger LAGER_HEAT_EXCHANGER;
    public static MetaTileEntityHugeBlastFurnace HUGE_BLAST_FURANCE;
    public static MetaTileEntityCompressedFusionReactor COMPRESSED_FUSION_REACTOR_MKI;
    public static MetaTileEntityCompressedFusionReactor COMPRESSED_FUSION_REACTOR_MKII;
    public static MetaTileEntityCompressedFusionReactor COMPRESSED_FUSION_REACTOR_MKIII;
    public static final MetaTileEntityCreativeEnergyHatch[] CREATIVE_ENERGY_HATCHES = new MetaTileEntityCreativeEnergyHatch[GTValues.V.length];
    public static MetaTileInfWaterHatch INF_WATER_HATCH;
    public static MetaTileEntityLightningRod[] LIGHTNING_ROD = new MetaTileEntityLightningRod[3];
    public static MetaTileEntityDangoteDistillery DANGOTE_DISTILLERY;
    public static MetaTileEntitykeQingNet KeQing_NET;
    public static MetaTileEntityDistillationTower DISTILLATION_TOWER;
    public static final SimpleGeneratorMetaTileEntity[] NAQUADAH_REACTOR = new SimpleGeneratorMetaTileEntity[3];
    public static final SimpleGeneratorMetaTileEntity[] ROCKET_ENGINE = new SimpleGeneratorMetaTileEntity[3];
    public static MetaTileEntityTurbineCombustionChamber HUGE_TURBINE_COMBUSTION_CHAMBER;
    public static MetaTileEntityIModularFissionReactor I_MODULAR_FISSION_REACTOR;
    public static MetaTileEntityRocket ROCKET;
    public static MetaTileEntityKQCC KQCC;
    public static MetaTileEntityGantryCrane GANTRY_CRANE;
    public static MetaTileEntitySteamOreWasher STEAM_ORE_WASHER;
    public static MetaTileEntitySteamBlastFurnace STEAM_BLAST_FURANCE;
    public static MetaTileEntityPReactor P_REACTOR;
    public static MetaTileEntityHugeDistillationTower HUGE_DISTILLATION_TOWER;
    public static MetaTileEntityHugeVacuum HUGE_VACUUM;
    public static final MetaTileEntityEFusionReactor[] EFUSION_REACTOR = new MetaTileEntityEFusionReactor[3];
    public static MetaTileEntityVacuumFreezer VACUUM_FREEZER;
    public static MetaTileEntityPyrolysisTower PYROLYSIS_TOWER;
    public static MetaTileEntityQuantumForceTransformer QUANTUM_FORCE_TRANSFORMER;
    public static MetaTileEntityFuelRefineFactory FUEL_REFINE_FACTORY;
    public static MetaTileEntityFermentationTank FERMENTATION_TANK;
    public static MetaTileEntityNaquadahReactorMki NAQUADAH_REACTOR_MKI;
    public static MetaTileEntityNaquadahReactorMkii NAQUADAH_REACTOR_MKII;
    public static MetaTileEntityNaquadahReactorMkiii NAQUADAH_REACTOR_MKIII;
    public static MetaTileEntityDigester DIGESTER;
    public static final MetaTileEntityHugeFusionReactor[] HUGE_FUSION_REACTOR = new MetaTileEntityHugeFusionReactor[3];
    public static MetaTileEntityStarBiomimeticFactory STAR_BIOMIMETIC_FACTORY;
    public static MetaTileEntityPlasmaForge PLASMA_FORGE;
    public static MetaTileEntityStarMixer STAR_MIXER;
    public static MetaTileEntitySteamCompressor STEAM_COMPRESSOR;
    public static  MetaTileEntityStepper STEPPER;
    public static MetaTileEntitySteamExtractor STEAM_EXTRACTOR;
    public static MetaTileEntityHugeCrackingUnit HUGE_CRACKING_UNIT;
    public static MetaTileEntityHugeMiner BASIC_HUGE_MINER;
    public static MetaTileEntityHugeMiner HUGE_MINER;
    public static MetaTileEntityDissolutionTank DISSOLUTION_TANK;
    public static MetaTileEntityHugeMiner ADVANCED_HUGE_MINER;
    public static MetaTileEntityHyperReactorMk1 HYPER_REACTOR_MK1;
    public static MetaTileEntityHyperReactorMk2 HYPER_REACTOR_MK2;
    public static MetaTileEntityHyperReactorMk3 HYPER_REACTOR_MK3;

    public static MetaTileEntityFracturing BASIC_FLUID_DRILLING_RIG;
    public static MetaTileEntitySepticTank SEPTIC_TANK;
    public static MetaTileEntityIsaMill ISA_MILL;
    public static MetaTileEntityPCB PCB;
    public static MetaTileEntityAlloykiln ALLOY_KILN;
    public static MetaTileEntitySaltField SALT_FLIED;
    public static SimpleMachineMetaTileEntity[] COMPONENT_ASSEMBLER = new SimpleMachineMetaTileEntity[GTValues.IV + 1];
    public static SimpleMachineMetaTileEntity[] DECAY_CHAMBER = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static MetaTileEntityElectrobath ELECTROBATH;
    public static MetaTileEntityBiologicalReaction BIOLOGICAL_REACTION;
    public static MetaTileEntityIndustrialFishingPond INDUSTRIAL_FISHING_POND;
    public static MetaTileEntityFracturing FLUID_DRILLING_RIG;
    public static MetaTileEntityFracturing ADVANCED_FLUID_DRILLING_RIG;
    public static MetaTileEntityLargeNaquadahReactor LARGE_NAQUADAH_REACTOR;
    public static MetaTileEntityCrackingUnit CRACKER;
    public static MetaTileEntityPyrolyseOven PYROLYSE_OVEN;
    public static MetaTileEntityDistillationKettle DISTILLATION_KETTLE;
    public static MetaTileEntityParticleAccelerator PARTICLE_ACCELERATOR;
    public static MetaTileEntityLargeChemicalReactor LARGE_CHEMICAL_REACTOR;
    public static MetaTileEntityPreciseAssembler PRECISE_ASSEMBLER;
    public static MetaTileEntityHugeElectricImplosionCompressor HUGE_ELECTRRIC_IMPLOSION_COMPRESSOR;
    public static MetaTileEntityFlotationFactory FLOTATION_FACTORY;
    public static MetaTileEntityLargeProcessingFactory LAGER_PROCESSING_FACTORY;
    public static MetaTileEntityVacuumDryingFurnace VACUUM_DRYING_FURNACE;
    public static MetaTileEntityMiningDrill MINING_DRILL;
    public static MetaTileEntityIndustrialPrimitiveBlastFurnace INDUSTRIAL_PRIMITIVE_BLAST_FURNACE;

    public static final SimpleGeneratorMetaTileEntity[] COMBUSTION_GENERATOR = new SimpleGeneratorMetaTileEntity[4];
    public static final SimpleGeneratorMetaTileEntity[] STEAM_TURBINE = new SimpleGeneratorMetaTileEntity[4];
    public static final SimpleGeneratorMetaTileEntity[] GAS_TURBINE = new SimpleGeneratorMetaTileEntity[4];

    public static final MetaTileEntityPlusMultiFluidHatch[] BIG_IMPORT_HATCH = new MetaTileEntityPlusMultiFluidHatch[11]; // EV-UHV
    public static final MetaTileEntityPlusMultiFluidHatch[] BIG_EXPORT_HATCH = new MetaTileEntityPlusMultiFluidHatch[11]; // EV-UHV

    public static MetaTileEntityFluidHatch[] IMPORT_FLUID_HATCH = new MetaTileEntityFluidHatch[4];
    public static MetaTileEntityFluidHatch[] EXPORT_FLUID_HATCH = new MetaTileEntityFluidHatch[4];
    public static MetaTileEntityItemBus[] IMPORT_ITEM_HATCH = new MetaTileEntityItemBus[4];
    public static MetaTileEntityItemBus[] EXPORT_ITEM_HATCH = new MetaTileEntityItemBus[4];
    public static MetaTileEntityOceanPumper OCEAN_PUMPER;
    public static MetaTileEntityClarifier CLARIFIER;
    public static MetaTileEntitySolarPlate SOLAR_PLATE;
    public static final MetaTileEntityPlusEnergyHatch[] PLUS_ENERGY_INPUT_HATCH = new MetaTileEntityPlusEnergyHatch[10];
    public static final MetaTileEntityPlusEnergyHatch[] PLUS_ENERGY_INPUT_HATCH_4A = new MetaTileEntityPlusEnergyHatch[10];
    public static final MetaTileEntityPlusEnergyHatch[] PLUS_ENERGY_INPUT_HATCH_16A = new MetaTileEntityPlusEnergyHatch[10];
    public static final MetaTileEntityPlusEnergyHatch[] PLUS_ENERGY_OUTPUT_HATCH = new MetaTileEntityPlusEnergyHatch[10];
    public static final MetaTileEntityPlusEnergyHatch[] PLUS_ENERGY_OUTPUT_HATCH_4A = new MetaTileEntityPlusEnergyHatch[10];
    public static final MetaTileEntityPlusEnergyHatch[] PLUS_ENERGY_OUTPUT_HATCH_16A = new MetaTileEntityPlusEnergyHatch[10];

    public static final MetaTileEntityPlusEnergyHatch[] PLUS_ENERGY_INPUT_HATCH_64A = new MetaTileEntityPlusEnergyHatch[10];
    public static final MetaTileEntityPlusEnergyHatch[] PLUS_ENERGY_INPUT_HATCH_128A = new MetaTileEntityPlusEnergyHatch[10];
    public static final MetaTileEntityPlusEnergyHatch[] PLUS_ENERGY_INPUT_HATCH_512A = new MetaTileEntityPlusEnergyHatch[10];
    public static final MetaTileEntityPlusEnergyHatch[] PLUS_ENERGY_OUTPUT_HATCH_64A = new MetaTileEntityPlusEnergyHatch[10];
    public static final MetaTileEntityPlusEnergyHatch[] PLUS_ENERGY_OUTPUT_HATCH_128A = new MetaTileEntityPlusEnergyHatch[10];
    public static final MetaTileEntityPlusEnergyHatch[] PLUS_ENERGY_OUTPUT_HATCH_512A = new MetaTileEntityPlusEnergyHatch[10];
    public static SimpleMachineMetaTileEntity[] ULTRAVIOLET_LAMP_CHAMBER = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static SimpleMachineMetaTileEntity[] VACUUM_CHAMBER = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static MetaTileEntityMillBallHatch MULTIPART_BALL_HATCH;
    public static MetaTileEntityCatalystHatch CATALYST_HATCH;
    public static MetaTileEntityKQNetworkSwitch KQNS;
    public static MetaTileEntityELEOil ELE_OIL;
    public static MetaTileEntitySpaceElevator SPACE_ELEVATOR;
    public static MetaTileEntityReactionFurnace REACTION_FURNACE;
    public static MetaTileEntityChemicalPlant CHEMICAL_PLANT;
    public static MetaTileEntityLaserEngraving LASER_ENGRAVING;
    public static MetaTileEntityWirelessEnergyHatch[] WIRELESS_EMERGY_HATCH_RECEIVER= new MetaTileEntityWirelessEnergyHatch[11];
    public static MetaTileEntityWirelessEnergyHatch[] WIRELESS_EMERGY_HATCH_TRANSMITTER= new MetaTileEntityWirelessEnergyHatch[11];
    public static final MetaTileEntityRotorHolder[] ROTOR_HOLDER = new MetaTileEntityRotorHolder[12]; //HV, EV, IV, LuV, ZPM, UV

    public static final SimpleMachineMetaTileEntity[] FLUID_CANNER = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static final SimpleMachineMetaTileEntity[] FLUID_EXTRACTOR = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static MetaTileEntityAssemblyLine ASSEMBLY_LINE;
    public static MetaTileEntityKQCCComputationHatch[] KQCC_COMPUTATION_HATCH_RECEIVER=new MetaTileEntityKQCCComputationHatch[12];
    public static MetaTileEntityKQCCComputationHatch[] KQCC_COMPUTATION_HATCH_TRANSMITTER=new MetaTileEntityKQCCComputationHatch[12];
    public static MetaTileEntityKQHPCA KQHPCA;
    public static MetaTileEntityCosmicRayDetector COSMIC_RAY_DETECTOR;
    public static MetaTileEntityHPCAAdvancedComputation HPCA_SUPER_COMPUTATION_COMPONENT;
    public static MetaTileEntityHPCAAdvancedComputation HPCA_ULTIMATE_COMPUTATION_COMPONENT;
    public static MetaTileEntityHPCAAdvancedCooler HPCA_SUPER_COOLER_COMPONENT;
    public static MetaTileEntityHPCAAdvancedCooler HPCA_ULTIMATE_COOLER_COMPONENT;

    public static MetaTileEntityCrystallizationCrucible CRYSTALLIZATION_CRUCIBLE;
    public static MetaTileEntityRoaster ROASTER;
    public static MetaTileEntityNanoscaleFabricator NANOSCALE_FABRICATOR;
    public static MetaTileEntityCVDUnit CVD_UNIT;
    public static MetaTileEntityEXCVD EX_CVD;
    public static MetaTileEntityBurnerReactor BURNER_REACTOR;
    public static MetaTileEntityCryoReactor CRYOGENIC_REACTOR;
    public static MetaTileEntityFracker HYDRAULIC_FRACKER;
    public static MetaTileEntitySonicator SONICATOR;
    public static MetaTileEntityCatalyticReformer CATALYTIC_REFORMER;
    public static MetaTileEntityIndustrialDrill INDUSTRIAL_DRILL;
    public static MetaTileEntityIonImplanter ION_IMPLANTATOR;
    public static MetaTileEntityCZPuller CZ_PULLER;

    //public static MetaTileEntityObjectHolder OBJECT_HOLDER;
    public static void initialization() {
        GTQTLog.logger.info("Registering MetaTileEntities");

        //发电设备 单方块
        LIGHTNING_ROD[0] = registerSingleMetaTileEntity(3000, new MetaTileEntityLightningRod(gtqtcoreId("lightning_rod.zpm"), GTValues.ZPM));
        LIGHTNING_ROD[1] = registerSingleMetaTileEntity(3001, new MetaTileEntityLightningRod(gtqtcoreId("lightning_rod.luv"), GTValues.LuV));
        LIGHTNING_ROD[2] = registerSingleMetaTileEntity(3002, new MetaTileEntityLightningRod(gtqtcoreId("lightning_rod.iv"), GTValues.IV));
        NAQUADAH_REACTOR[0] = registerMetaTileEntity(3003, new SimpleGeneratorMetaTileEntity(gtqtcoreId("naquadah_reactor.iv"), GTQTcoreRecipeMaps.NAQUADAH_REACTOR_RECIPES, GTQTTextures.NAQUADAH_REACTOR_OVERLAY, 5, genericGeneratorTankSizeFunction));
        NAQUADAH_REACTOR[1] = registerMetaTileEntity(3004, new SimpleGeneratorMetaTileEntity(gtqtcoreId("naquadah_reactor.luv"), GTQTcoreRecipeMaps.NAQUADAH_REACTOR_RECIPES, GTQTTextures.NAQUADAH_REACTOR_OVERLAY, 6, genericGeneratorTankSizeFunction));
        NAQUADAH_REACTOR[2] = registerMetaTileEntity(3005, new SimpleGeneratorMetaTileEntity(gtqtcoreId("naquadah_reactor.zpm"),  GTQTcoreRecipeMaps.NAQUADAH_REACTOR_RECIPES, GTQTTextures.NAQUADAH_REACTOR_OVERLAY, 7, genericGeneratorTankSizeFunction));
        ROCKET_ENGINE[0] = registerMetaTileEntity(3006, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rocket_engine.ev"), GTQTcoreRecipeMaps.ROCKET, GTQTTextures.ROCKET_ENGINE_OVERLAY, 4, genericGeneratorTankSizeFunction));
        ROCKET_ENGINE[1] = registerMetaTileEntity(3007, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rocket_engine.iv"), GTQTcoreRecipeMaps.ROCKET, GTQTTextures.ROCKET_ENGINE_OVERLAY, 5, genericGeneratorTankSizeFunction));
        ROCKET_ENGINE[2] = registerMetaTileEntity(3008, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rocket_engine.luv"), GTQTcoreRecipeMaps.ROCKET, GTQTTextures.ROCKET_ENGINE_OVERLAY, 6, genericGeneratorTankSizeFunction));

        COMBUSTION_GENERATOR[0] = registerMetaTileEntity(3009, new MetaTileEntitySingleCombustion(gtqtcoreId("combustion_generator.ev"), RecipeMaps.COMBUSTION_GENERATOR_FUELS, Textures.COMBUSTION_GENERATOR_OVERLAY, 4, genericGeneratorTankSizeFunction));
        COMBUSTION_GENERATOR[1] = registerMetaTileEntity(3010, new MetaTileEntitySingleCombustion(gtqtcoreId("combustion_generator.iv"), RecipeMaps.COMBUSTION_GENERATOR_FUELS, Textures.COMBUSTION_GENERATOR_OVERLAY, 5, genericGeneratorTankSizeFunction));
        STEAM_TURBINE[0] = registerMetaTileEntity(3011, new MetaTileEntitySingleTurbine(gtqtcoreId("steam_turbine.ev"), RecipeMaps.STEAM_TURBINE_FUELS, Textures.STEAM_TURBINE_OVERLAY, 4, GTUtility.steamGeneratorTankSizeFunction));
        STEAM_TURBINE[1] = registerMetaTileEntity(3012, new MetaTileEntitySingleTurbine(gtqtcoreId("steam_turbine.iv"), RecipeMaps.STEAM_TURBINE_FUELS, Textures.STEAM_TURBINE_OVERLAY, 5, GTUtility.steamGeneratorTankSizeFunction));

        GAS_TURBINE[0] = registerMetaTileEntity(3013, new MetaTileEntitySingleTurbine(gtqtcoreId("gas_turbine.ev"), RecipeMaps.GAS_TURBINE_FUELS, Textures.GAS_TURBINE_OVERLAY, 4, genericGeneratorTankSizeFunction));
        GAS_TURBINE[1] = registerMetaTileEntity(3014, new MetaTileEntitySingleTurbine(gtqtcoreId("gas_turbine.iv"), RecipeMaps.GAS_TURBINE_FUELS, Textures.GAS_TURBINE_OVERLAY, 5, genericGeneratorTankSizeFunction));
        //发电设备 多方块
        EFUSION_REACTOR[0] = registerMetaTileEntity(3050, new MetaTileEntityEFusionReactor(gtqtcoreId("fusion_reactor.luv"), GTValues.LuV));
        EFUSION_REACTOR[1] = registerMetaTileEntity(3051, new MetaTileEntityEFusionReactor(gtqtcoreId("fusion_reactor.zpm"), GTValues.ZPM));
        EFUSION_REACTOR[2] = registerMetaTileEntity(3052, new MetaTileEntityEFusionReactor(gtqtcoreId("fusion_reactor.uv"), GTValues.UV));

        HYPER_REACTOR_MK1 = registerMetaTileEntity(3053, new MetaTileEntityHyperReactorMk1(gtqtcoreId("hyper_reactor_mk1")));
        HYPER_REACTOR_MK2 = registerMetaTileEntity(3054, new MetaTileEntityHyperReactorMk2(gtqtcoreId("hyper_reactor_mk2")));
        HYPER_REACTOR_MK3 = registerMetaTileEntity(3055, new MetaTileEntityHyperReactorMk3(gtqtcoreId("hyper_reactor_mk3")));

        NAQUADAH_REACTOR_MKI = registerMetaTileEntity(3056, new MetaTileEntityNaquadahReactorMki(gtqtcoreId("naquadah_reactor_mki")));
        NAQUADAH_REACTOR_MKII = registerMetaTileEntity(3057, new MetaTileEntityNaquadahReactorMkii(gtqtcoreId("naquadah_reactor_mkii")));
        NAQUADAH_REACTOR_MKIII = registerMetaTileEntity(3058, new MetaTileEntityNaquadahReactorMkiii(gtqtcoreId("naquadah_reactor_mkiii")));

        LARGE_NAQUADAH_REACTOR = registerMetaTileEntity(3059, new MetaTileEntityLargeNaquadahReactor(gtqtcoreId("large_naquadah_reactor")));

        ROCKET = registerMetaTileEntity(3060, new MetaTileEntityRocket(gtqtcoreId("rocket"),7));
        HUGE_TURBINE_COMBUSTION_CHAMBER = registerMetaTileEntity(3061, new MetaTileEntityTurbineCombustionChamber(gtqtcoreId("turbine_combustion_chamber"),4));

        SOLAR_PLATE = registerMetaTileEntity(3062, new MetaTileEntitySolarPlate(gtqtcoreId("solar_plate")));

        I_MODULAR_FISSION_REACTOR = registerMetaTileEntity(3063, new MetaTileEntityIModularFissionReactor(gtqtcoreId("i_modular_fission_reactor"),5));
        //早期设备
        ALLOY_KILN = registerMetaTileEntity(3100, new MetaTileEntityAlloykiln(gtqtcoreId("alloy_klin")));
        STEAM_COMPRESSOR = registerMetaTileEntity(3101, new MetaTileEntitySteamCompressor(gtqtcoreId("steam_compressor")));
        STEAM_EXTRACTOR = registerMetaTileEntity(3102, new MetaTileEntitySteamExtractor(gtqtcoreId("steam_extractor")));
        STEAM_BLAST_FURANCE = registerMetaTileEntity(3103, new MetaTileEntitySteamBlastFurnace(gtqtcoreId("steam_blast_furance")));
        STEAM_ORE_WASHER = registerMetaTileEntity(3104, new MetaTileEntitySteamOreWasher(gtqtcoreId("steam_ore_washer")));
        INDUSTRIAL_PRIMITIVE_BLAST_FURNACE = registerMetaTileEntity(3105, new MetaTileEntityIndustrialPrimitiveBlastFurnace(gtqtcoreId("industrial_primitive_blast_furnace")));
        //正常设备
        DISSOLUTION_TANK = registerMetaTileEntity(3150, new MetaTileEntityDissolutionTank(gtqtcoreId("dissolution_tank")));
        BLAZING_BLAST_FURNACE = registerMetaTileEntity(3151, new MetaTileEntityBlazingBlastFurnace(gtqtcoreId("blazing_blast_furnace")));
        CHEMICAL_PLANT = registerMetaTileEntity(3152,new MetaTileEntityChemicalPlant(gtqtcoreId("chemical_plant")));
        INTEGRATED_MINING_DIVISION = registerMetaTileEntity(3153, new MetaTileEntityIntegratedMiningDivision(gtqtcoreId("integrated_mining_division")));
        QUANTUM_FORCE_TRANSFORMER = registerMetaTileEntity(3154, new MetaTileEntityQuantumForceTransformer(gtqtcoreId("quantum_force_transform")));
        LAGER_HEAT_EXCHANGER = registerMetaTileEntity(3155, new MetaTileEntityLagerHeatExchanger(gtqtcoreId("lager_heat_exchanger")));
        DANGOTE_DISTILLERY = registerMetaTileEntity(3156, new MetaTileEntityDangoteDistillery(gtqtcoreId("dangote_distillery")));
        FUEL_REFINE_FACTORY = registerMetaTileEntity(3157, new MetaTileEntityFuelRefineFactory(gtqtcoreId("fuel_refine_factory")));
        FERMENTATION_TANK= registerMetaTileEntity(3158, new MetaTileEntityFermentationTank(gtqtcoreId("fermentation_tank")));
        DIGESTER = registerMetaTileEntity(3159, new MetaTileEntityDigester(gtqtcoreId("digester")));
        SEPTIC_TANK = registerMetaTileEntity(3160, new MetaTileEntitySepticTank(gtqtcoreId("septic_tank")));
        PCB = registerMetaTileEntity(3161, new MetaTileEntityPCB(gtqtcoreId("pcb")));
        INDUSTRIAL_FISHING_POND = registerMetaTileEntity(3162, new MetaTileEntityIndustrialFishingPond(gtqtcoreId("IndustrialFishingPond")));
        ISA_MILL = registerMetaTileEntity(3163, new MetaTileEntityIsaMill(gtqtcoreId("isa_mill")));
        FLOTATION_FACTORY = registerMetaTileEntity(3164, new MetaTileEntityFlotationFactory(gtqtcoreId("flotation_factory")));
        VACUUM_DRYING_FURNACE = registerMetaTileEntity(3165, new MetaTileEntityVacuumDryingFurnace(gtqtcoreId("vacuum_drying_furnace")));
        ALGAE_FARM = registerMetaTileEntity(3166, new MetaTileEntityAlgaeFarm(gtqtcoreId("algae_farm")));
        ELECTROBATH = registerMetaTileEntity(3167, new MetaTileEntityElectrobath(gtqtcoreId("electrobath")));
        SALT_FLIED = registerMetaTileEntity(3168, new MetaTileEntitySaltField(gtqtcoreId("salt_flied")));
        PYROLYSIS_TOWER = registerMetaTileEntity(3169, new MetaTileEntityPyrolysisTower(gtqtcoreId("pyrolysis_tower")));
        PRECISE_ASSEMBLER = registerMetaTileEntity(3170, new MetaTileEntityPreciseAssembler(gtqtcoreId("precise_assembler")));
        PARTICLE_ACCELERATOR = registerMetaTileEntity(3171, new MetaTileEntityParticleAccelerator(gtqtcoreId("particle_accelerator")));
        LARGE_CHEMICAL_REACTOR = registerMetaTileEntity(3172, new MetaTileEntityLargeChemicalReactor(gtqtcoreId("large_chemical_reactor")));
        BIOLOGICAL_REACTION=registerMetaTileEntity(3173,new MetaTileEntityBiologicalReaction(gtqtcoreId(("biological_reaction"))));
        OIL_POOL=registerMetaTileEntity(3174,new MetaTileEntityOilPool(gtqtcoreId(("oil_pool"))));
        MINING_DRILL=registerMetaTileEntity(3175,new MetaTileEntityMiningDrill(gtqtcoreId(("mining_drill"))));
        DISTILLATION_KETTLE=registerMetaTileEntity(3176,new MetaTileEntityDistillationKettle(gtqtcoreId(("distillation_kettle"))));
        OCEAN_PUMPER = registerMetaTileEntity(3177, new MetaTileEntityOceanPumper(gtqtcoreId("ocean_pumper")));
        P_REACTOR= registerMetaTileEntity(3178, new MetaTileEntityPReactor(gtqtcoreId("p_reactor")));
        LASER_ENGRAVING= registerMetaTileEntity(3179, new MetaTileEntityLaserEngraving(gtqtcoreId("laser_engraving")));
        STEPPER= registerMetaTileEntity(3180, new MetaTileEntityStepper(gtqtcoreId("stepper")));
        GANTRY_CRANE= registerMetaTileEntity(3181, new MetaTileEntityGantryCrane(gtqtcoreId("gantry_crane")));
        CLARIFIER= registerMetaTileEntity(3182, new MetaTileEntityClarifier(gtqtcoreId("clarifier")));
        ELE_OIL= registerMetaTileEntity(3183, new MetaTileEntityELEOil(gtqtcoreId("ele_oil")));
        REACTION_FURNACE= registerMetaTileEntity(3184, new MetaTileEntityReactionFurnace(gtqtcoreId("reactor_furnace")));
        SPACE_ELEVATOR= registerMetaTileEntity(3185, new MetaTileEntitySpaceElevator(gtqtcoreId("space_elevator")));
        //重写设备
        DISTILLATION_TOWER = registerMetaTileEntity(3250, new MetaTileEntityDistillationTower(gtqtcoreId("distillation_tower")));
        CRACKER = registerMetaTileEntity(3251, new MetaTileEntityCrackingUnit(gtqtcoreId("cracker")));
        PYROLYSE_OVEN = registerMetaTileEntity(3252, new MetaTileEntityPyrolyseOven(gtqtcoreId("pyrolyse_oven")));
        VACUUM_FREEZER = registerMetaTileEntity(3253, new MetaTileEntityVacuumFreezer(gtqtcoreId("vacuum_freezer")));
        ASSEMBLY_LINE = registerMetaTileEntity(3254, new MetaTileEntityAssemblyLine(gtqtcoreId("assembly_line")));
        //巨型设备
        HUGE_FUSION_REACTOR[0] = registerMetaTileEntity(3300, new MetaTileEntityHugeFusionReactor(gtqtcoreId("fusion_reactor.uhv"), GTValues.UHV));
        HUGE_FUSION_REACTOR[1] = registerMetaTileEntity(3301, new MetaTileEntityHugeFusionReactor(gtqtcoreId("fusion_reactor.uev"), GTValues.UEV));
        HUGE_FUSION_REACTOR[2] = registerMetaTileEntity(3302, new MetaTileEntityHugeFusionReactor(gtqtcoreId("fusion_reactor.uiv"), GTValues.UIV));

        COMPRESSED_FUSION_REACTOR_MKI = registerMetaTileEntity(3303,new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor_mki"),9));
        COMPRESSED_FUSION_REACTOR_MKII = registerMetaTileEntity(3304,new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor_mkii"),10));
        COMPRESSED_FUSION_REACTOR_MKIII = registerMetaTileEntity(3305,new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor_mkiii"),11));
        COMPONENT_ASSEMBLY_LINE = registerMetaTileEntity(3306, new MetaTileEntityComponentAssemblyLine(gtqtcoreId("component_assembly_line")));

        LAGER_PROCESSING_FACTORY = registerMetaTileEntity(3307, new MetaTileEntityLargeProcessingFactory(gtqtcoreId("large_processing_factory")));
        HUGE_DISTILLATION_TOWER = registerMetaTileEntity(3308, new MetaTileEntityHugeDistillationTower(gtqtcoreId("huge_distillation_tower")));
        HUGE_VACUUM = registerMetaTileEntity(3309, new MetaTileEntityHugeVacuum(gtqtcoreId("huge_vacuum")));
        HUGE_ELECTRRIC_IMPLOSION_COMPRESSOR = registerMetaTileEntity(3310, new MetaTileEntityHugeElectricImplosionCompressor(gtqtcoreId("huge_electric_implosion_compressor")));
        HUGE_MACERATOR = registerMetaTileEntity(3311,new MetaTileEntityHugeMacerator(gtqtcoreId("huge_macerator")));
        HUGE_ALLOY_BLAST_FURANCE = registerMetaTileEntity(3312,new MetaTileEntityHugeAlloyBlastSmelter(gtqtcoreId("huge_alloy_blast_smelter")));
        HUGE_BLAST_FURANCE = registerMetaTileEntity(3313,new MetaTileEntityHugeBlastFurnace(gtqtcoreId("huge_blast_furnace")));
        HUGE_CRACKING_UNIT = registerMetaTileEntity(3314, new MetaTileEntityHugeCrackingUnit(gtqtcoreId("huge_cracking_unit")));
        HUGE_CHEMICAL_REACTOR = registerMetaTileEntity(3315, new MetaTileEntityHugeChemicalReactor(gtqtcoreId("huge_chemical_reactor")));
        //star
        STAR_BIOMIMETIC_FACTORY= registerMetaTileEntity(3350, new MetaTileEntityStarBiomimeticFactory(gtqtcoreId("star_biomimetic_factory")));
        PLASMA_FORGE= registerMetaTileEntity(3351, new MetaTileEntityPlasmaForge(gtqtcoreId("plasma_forge")));
        STAR_MIXER= registerMetaTileEntity(3352, new MetaTileEntityStarMixer(gtqtcoreId("star_mixer")));

        //资源产出
        BASIC_FLUID_DRILLING_RIG = registerMetaTileEntity(3400, new MetaTileEntityFracturing(gtqtcoreId("fracturing.mv"), 2));
        FLUID_DRILLING_RIG = registerMetaTileEntity(3401, new MetaTileEntityFracturing(gtqtcoreId("fracturing.hv"), 3));
        ADVANCED_FLUID_DRILLING_RIG = registerMetaTileEntity(3402, new MetaTileEntityFracturing(gtqtcoreId("fracturing.ev"), 4));
        BASIC_HUGE_MINER = registerMetaTileEntity(3403, new MetaTileEntityHugeMiner(gtqtcoreId("large_miner.zpm"), GTValues.ZPM, 1, 8, 7,  64));
        HUGE_MINER = registerMetaTileEntity(3404, new MetaTileEntityHugeMiner(gtqtcoreId("large_miner.uv"), GTValues.UV, 2, 10, 8,  128));
        ADVANCED_HUGE_MINER = registerMetaTileEntity(3405, new MetaTileEntityHugeMiner(gtqtcoreId("large_miner.uhv"), GTValues.UHV, 3, 12, 9 , 256));

        //KQCC
        KQCC= registerMetaTileEntity(3450, new MetaTileEntityKQCC(gtqtcoreId("kqcc")));
        KQNS=registerMetaTileEntity(3451,new MetaTileEntityKQNetworkSwitch(gtqtcoreId("kqns")));
        KeQing_NET= registerMetaTileEntity(3452, new MetaTileEntitykeQingNet(gtqtcoreId("keqing_net")));
        COSMIC_RAY_DETECTOR= registerMetaTileEntity(3453, new MetaTileEntityCosmicRayDetector(gtqtcoreId("cosmic_ray_detector")));

        HPCA_SUPER_COMPUTATION_COMPONENT = registerMetaTileEntity(3461, new MetaTileEntityHPCAAdvancedComputation(gtqtcoreId("hpca.super_computation_component"),false));
        HPCA_ULTIMATE_COMPUTATION_COMPONENT = registerMetaTileEntity(3462, new MetaTileEntityHPCAAdvancedComputation(gtqtcoreId("hpca.ultimate_computation_component"),  true));
        HPCA_SUPER_COOLER_COMPONENT = registerMetaTileEntity(3464, new MetaTileEntityHPCAAdvancedCooler(gtqtcoreId("hpca.super_cooler_component"), true, false));
        HPCA_ULTIMATE_COOLER_COMPONENT = registerMetaTileEntity(3465, new MetaTileEntityHPCAAdvancedCooler(gtqtcoreId("hpca.ultimate_cooler_component"),  false, true));


        //GCYS
        INDUSTRIAL_DRILL = registerMetaTileEntity(3500, new MetaTileEntityIndustrialDrill(gtqtcoreId("industrial_drill")));
        CATALYTIC_REFORMER = registerMetaTileEntity(3501, new MetaTileEntityCatalyticReformer(gtqtcoreId("catalytic_reformer")));
        SONICATOR = registerMetaTileEntity(3502, new MetaTileEntitySonicator(gtqtcoreId("sonicator")));
        HYDRAULIC_FRACKER = registerMetaTileEntity(3503, new MetaTileEntityFracker(gtqtcoreId("fracker"), GTValues.ZPM));
        NANOSCALE_FABRICATOR = registerMetaTileEntity(3504, new MetaTileEntityNanoscaleFabricator(gtqtcoreId("nanoscale_fabricator")));
        ROASTER = registerMetaTileEntity(3505, new MetaTileEntityRoaster(gtqtcoreId("roaster")));
        CRYSTALLIZATION_CRUCIBLE = registerMetaTileEntity(3506, new MetaTileEntityCrystallizationCrucible(gtqtcoreId("crystallization_crucible")));
        CVD_UNIT = registerMetaTileEntity(3507, new MetaTileEntityCVDUnit(gtqtcoreId("cvd_unit")));
        BURNER_REACTOR = registerMetaTileEntity(3508, new MetaTileEntityBurnerReactor(gtqtcoreId("burner_reactor")));
        CRYOGENIC_REACTOR = registerMetaTileEntity(3509, new MetaTileEntityCryoReactor(gtqtcoreId("cryogenic_reactor")));
        ION_IMPLANTATOR = registerMetaTileEntity(3510, new MetaTileEntityIonImplanter(gtqtcoreId("ion_implantator")));
        CZ_PULLER = registerMetaTileEntity(3511, new MetaTileEntityCZPuller(gtqtcoreId("cz_puller")));
        EX_CVD = registerMetaTileEntity(3512, new MetaTileEntityEXCVD(gtqtcoreId("ex_cvd")));
        //小机器
        registerSimpleMetaTileEntity(FLUID_EXTRACTOR, 15000, "fluid_extractor", GTQTcoreRecipeMaps.FLUID_EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(FLUID_CANNER, 15015, "fluid_canner", GTQTcoreRecipeMaps.FLUID_CANNER_RECIPES, Textures.CANNER_OVERLAY, true,GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(COMPONENT_ASSEMBLER, 15030, "component_assembler", GTQTcoreRecipeMaps.COMPONENT_ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(DECAY_CHAMBER, 15045, "decay_chamber", GTQTcoreRecipeMaps.DECAY_CHAMBER_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(VACUUM_CHAMBER, 15060, "vacuum_chamber", GTQTcoreRecipeMaps.VACUUM_CHAMBER_RECIPES, Textures.GAS_COLLECTOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(ULTRAVIOLET_LAMP_CHAMBER, 15075, "ultraviolet_lamp_chamber", GTQTcoreRecipeMaps.ULTRAVIOLET_LAMP_CHAMBER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);

        //仓口
        for (int i = 1; i <= 10; i++) {
            String tierName = GTValues.VN[i].toLowerCase();
            KQCC_COMPUTATION_HATCH_RECEIVER[i] = registerMetaTileEntity(15500 + i-1, new MetaTileEntityKQCCComputationHatch(gtqtcoreId("kqcccomputation_hatch.receiver." + tierName), i,  false));
            KQCC_COMPUTATION_HATCH_TRANSMITTER[i] = registerMetaTileEntity(15510 + i-1, new MetaTileEntityKQCCComputationHatch(gtqtcoreId("kqcccomputation_hatch.transmitter." + tierName), i,  true));
        }
        for (int i = 1; i <= 10; i++) {
            String tierName = GTValues.VN[i].toLowerCase();
            WIRELESS_EMERGY_HATCH_RECEIVER[i] = registerMetaTileEntity(15520 + i-1, new MetaTileEntityWirelessEnergyHatch(gtqtcoreId("wireless_energy_hatch.receiver." + tierName), i,  false));
            WIRELESS_EMERGY_HATCH_TRANSMITTER[i] = registerMetaTileEntity(15530 + i-1, new MetaTileEntityWirelessEnergyHatch(gtqtcoreId("wireless_energy_hatch.transmitter." + tierName), i,  true));
        }

        registerMetaTileEntity(15541, new MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[9])), 9));
        registerMetaTileEntity(15542, new MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[10])), 10));
        registerMetaTileEntity(15543, new MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[11])), 11));
        registerMetaTileEntity(15544, new MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[12])), 12));
        INF_WATER_HATCH = registerMetaTileEntity(15545,new MetaTileInfWaterHatch(gtqtcoreId("infinite_water_hatch")));
        CATALYST_HATCH = registerMetaTileEntity(15546,new MetaTileEntityCatalystHatch(gtqtcoreId("catalyst_hatch")));
        MULTIPART_BUFFER_HATCH = registerMetaTileEntity(15547, new MetaTileEntityBufferHatch(gtqtcoreId("buffer_hatch")));
        MULTIPART_BALL_HATCH = registerMetaTileEntity(15548, new MetaTileEntityMillBallHatch(gtqtcoreId("mill_ball_hatch")));

        ROTOR_HOLDER[6] = registerMetaTileEntity(16000, new MetaTileEntityRotorHolder(gtqtcoreId("rotor_holder.uhv"), GTValues.UHV));
        ROTOR_HOLDER[7] = registerMetaTileEntity(16001, new MetaTileEntityRotorHolder(gtqtcoreId("rotor_holder.uev"), GTValues.UEV));
        ROTOR_HOLDER[8] = registerMetaTileEntity(16002, new MetaTileEntityRotorHolder(gtqtcoreId("rotor_holder.uiv"), GTValues.UIV));
        ROTOR_HOLDER[9] = registerMetaTileEntity(16003, new MetaTileEntityRotorHolder(gtqtcoreId("rotor_holder.uxv"), GTValues.UXV));
        ROTOR_HOLDER[10] = registerMetaTileEntity(16004, new MetaTileEntityRotorHolder(gtqtcoreId("rotor_holder.opv"), GTValues.OpV));
        ROTOR_HOLDER[11] = registerMetaTileEntity(16005, new MetaTileEntityRotorHolder(gtqtcoreId("rotor_holder.max"), GTValues.MAX));

        PLUS_ENERGY_INPUT_HATCH[1] = registerMetaTileEntity(16006, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input.uev"), 10, 2, false));
        PLUS_ENERGY_OUTPUT_HATCH[1] = registerMetaTileEntity(16007, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output.uev"), 10, 2, true));
        PLUS_ENERGY_INPUT_HATCH_4A[1] = registerMetaTileEntity(16008, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_4a.uev"), 10,4, false));
        PLUS_ENERGY_INPUT_HATCH_16A[1] = registerMetaTileEntity(16009, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_16a.uev"), 10, 16, false));
        PLUS_ENERGY_OUTPUT_HATCH_4A[1] = registerMetaTileEntity(16010, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_4a.uev"), 10, 4, true));
        PLUS_ENERGY_OUTPUT_HATCH_16A[1] = registerMetaTileEntity(16011, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_16a.uev"), 10, 16, true));

        PLUS_ENERGY_INPUT_HATCH[2] = registerMetaTileEntity(16012, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input.uiv"), 11, 2, false));
        PLUS_ENERGY_OUTPUT_HATCH[2] = registerMetaTileEntity(16013, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output.uiv"), 11, 2, true));
        PLUS_ENERGY_INPUT_HATCH_4A[2] = registerMetaTileEntity(16014, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_4a.uiv"), 11,4, false));
        PLUS_ENERGY_INPUT_HATCH_16A[2] = registerMetaTileEntity(16015, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_16a.uiv"), 11, 16, false));
        PLUS_ENERGY_OUTPUT_HATCH_4A[2] = registerMetaTileEntity(16016, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_4a.uiv"), 11, 4, true));
        PLUS_ENERGY_OUTPUT_HATCH_16A[2] = registerMetaTileEntity(16017, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_16a.uiv"), 11, 16, true));

        PLUS_ENERGY_INPUT_HATCH[3] = registerMetaTileEntity(16018, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input.uxv"), 12, 2, false));
        PLUS_ENERGY_OUTPUT_HATCH[3] = registerMetaTileEntity(16019, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output.uxv"), 12, 2, true));
        PLUS_ENERGY_INPUT_HATCH_4A[3] = registerMetaTileEntity(16020, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_4a.uxv"), 12,4, false));
        PLUS_ENERGY_INPUT_HATCH_16A[3] = registerMetaTileEntity(16021, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_16a.uxv"), 12, 16, false));
        PLUS_ENERGY_OUTPUT_HATCH_4A[3] = registerMetaTileEntity(16022, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_4a.uxv"), 12, 4, true));
        PLUS_ENERGY_OUTPUT_HATCH_16A[3] = registerMetaTileEntity(16023, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_16a.uxv"), 12, 16, true));

        PLUS_ENERGY_INPUT_HATCH[4] = registerMetaTileEntity(16024, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input.opv"), 13, 2, false));
        PLUS_ENERGY_OUTPUT_HATCH[4] = registerMetaTileEntity(16025, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output.opv"), 13, 2, true));
        PLUS_ENERGY_INPUT_HATCH_4A[4] = registerMetaTileEntity(16026, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_4a.opv"), 13,4, false));
        PLUS_ENERGY_INPUT_HATCH_16A[4] = registerMetaTileEntity(16027, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_16a.opv"), 13, 16, false));
        PLUS_ENERGY_OUTPUT_HATCH_4A[4] = registerMetaTileEntity(16028, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_4a.opv"), 13, 4, true));
        PLUS_ENERGY_OUTPUT_HATCH_16A[4] = registerMetaTileEntity(16029, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_16a.opv"), 13, 16, true));

        PLUS_ENERGY_INPUT_HATCH_64A[1] = registerMetaTileEntity(16030, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_64a.uev"), 10, 64, false));
        PLUS_ENERGY_OUTPUT_HATCH_64A[1] = registerMetaTileEntity(16031, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_64a.uev"), 10, 64, true));
        PLUS_ENERGY_INPUT_HATCH_128A[1] = registerMetaTileEntity(16032, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_128a.uev"), 10,128, false));
        PLUS_ENERGY_INPUT_HATCH_512A[1] = registerMetaTileEntity(16033, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_512a.uev"), 10, 512, false));
        PLUS_ENERGY_OUTPUT_HATCH_128A[1] = registerMetaTileEntity(16034, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_128a.uev"), 10, 128, true));
        PLUS_ENERGY_OUTPUT_HATCH_512A[1] = registerMetaTileEntity(16035, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_512a.uev"), 10, 512, true));

        PLUS_ENERGY_INPUT_HATCH_64A[2] = registerMetaTileEntity(16036, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_64a.uiv"), 11, 64, false));
        PLUS_ENERGY_OUTPUT_HATCH_64A[2] = registerMetaTileEntity(16037, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_64a.uiv"), 11, 64, true));
        PLUS_ENERGY_INPUT_HATCH_128A[2] = registerMetaTileEntity(16038, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_128a.uiv"), 11,128, false));
        PLUS_ENERGY_INPUT_HATCH_512A[2] = registerMetaTileEntity(16039, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_512a.uiv"), 11, 512, false));
        PLUS_ENERGY_OUTPUT_HATCH_128A[2] = registerMetaTileEntity(16040, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_128a.uiv"), 11, 128, true));
        PLUS_ENERGY_OUTPUT_HATCH_512A[2] = registerMetaTileEntity(16041, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_512a.uiv"), 11, 512, true));

        PLUS_ENERGY_INPUT_HATCH_64A[3] = registerMetaTileEntity(16042, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_64a.uxv"), 12, 64, false));
        PLUS_ENERGY_OUTPUT_HATCH_64A[3] = registerMetaTileEntity(16043, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_64a.uxv"), 12, 64, true));
        PLUS_ENERGY_INPUT_HATCH_128A[3] = registerMetaTileEntity(16044, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_128a.uxv"), 12,128, false));
        PLUS_ENERGY_INPUT_HATCH_512A[3] = registerMetaTileEntity(16045, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_512a.uxv"), 12, 512, false));
        PLUS_ENERGY_OUTPUT_HATCH_128A[3] = registerMetaTileEntity(16046, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_128a.uxv"), 12, 128, true));
        PLUS_ENERGY_OUTPUT_HATCH_512A[3] = registerMetaTileEntity(16047, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_512a.uxv"), 12, 512, true));

        PLUS_ENERGY_INPUT_HATCH_64A[4] = registerMetaTileEntity(16048, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_64a.opv"), 13, 64, false));
        PLUS_ENERGY_OUTPUT_HATCH_64A[4] = registerMetaTileEntity(16049, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_64a.opv"), 13, 64, true));
        PLUS_ENERGY_INPUT_HATCH_128A[4] = registerMetaTileEntity(16070, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_128a.opv"), 13,128, false));
        PLUS_ENERGY_INPUT_HATCH_512A[4] = registerMetaTileEntity(16071, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_512a.opv"), 13, 512, false));
        PLUS_ENERGY_OUTPUT_HATCH_128A[4] = registerMetaTileEntity(16072, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_128a.opv"), 13, 128, true));
        PLUS_ENERGY_OUTPUT_HATCH_512A[4] = registerMetaTileEntity(16073, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_512a.opv"), 13, 512, true));

        IMPORT_FLUID_HATCH[0] = registerMetaTileEntity(16074, new MetaTileEntityFluidHatch(gtqtcoreId("fluid_hatch.import.uev"), 10, false));
        IMPORT_FLUID_HATCH[1] = registerMetaTileEntity(16075, new MetaTileEntityFluidHatch(gtqtcoreId("fluid_hatch.import.uiv"), 11, false));
        IMPORT_FLUID_HATCH[2] = registerMetaTileEntity(16076, new MetaTileEntityFluidHatch(gtqtcoreId("fluid_hatch.import.uxv"), 12, false));
        IMPORT_FLUID_HATCH[3] = registerMetaTileEntity(16077, new MetaTileEntityFluidHatch(gtqtcoreId("fluid_hatch.import.opv"), 13, false));
        EXPORT_FLUID_HATCH[0] = registerMetaTileEntity(16078, new MetaTileEntityFluidHatch(gtqtcoreId("fluid_hatch.export.uev"), 10, true));
        EXPORT_FLUID_HATCH[1] = registerMetaTileEntity(16079, new MetaTileEntityFluidHatch(gtqtcoreId("fluid_hatch.export.uiv"), 11, true));
        EXPORT_FLUID_HATCH[2] = registerMetaTileEntity(16080, new MetaTileEntityFluidHatch(gtqtcoreId("fluid_hatch.export.uxv"), 12, true));
        EXPORT_FLUID_HATCH[3] = registerMetaTileEntity(16081, new MetaTileEntityFluidHatch(gtqtcoreId("fluid_hatch.export.opv"), 13, true));
        IMPORT_ITEM_HATCH[0] = registerMetaTileEntity(16082, new MetaTileEntityItemBus(gtqtcoreId("item_hatch.import.uev"), 10, false));
        IMPORT_ITEM_HATCH[1] = registerMetaTileEntity(16083, new MetaTileEntityItemBus(gtqtcoreId("item_hatch.import.uiv"), 11, false));
        IMPORT_ITEM_HATCH[2] = registerMetaTileEntity(16084, new MetaTileEntityItemBus(gtqtcoreId("item_hatch.import.uxv"), 12, false));
        IMPORT_ITEM_HATCH[3] = registerMetaTileEntity(16085, new MetaTileEntityItemBus(gtqtcoreId("item_hatch.import.opv"), 13, false));
        EXPORT_ITEM_HATCH[0] = registerMetaTileEntity(16086, new MetaTileEntityItemBus(gtqtcoreId("item_hatch.export.uev"), 10, false));
        EXPORT_ITEM_HATCH[1] = registerMetaTileEntity(16087, new MetaTileEntityItemBus(gtqtcoreId("item_hatch.export.uiv"), 11, false));
        EXPORT_ITEM_HATCH[2] = registerMetaTileEntity(16088, new MetaTileEntityItemBus(gtqtcoreId("item_hatch.export.uxv"), 12, false));
        EXPORT_ITEM_HATCH[3] = registerMetaTileEntity(16089, new MetaTileEntityItemBus(gtqtcoreId("item_hatch.export.opv"), 13, false));

        for (int i = GTValues.IV; i <= GTValues.MAX; i++) {
            int index = i - GTValues.IV;
            String tierName = GTValues.VN[i].toLowerCase();
            BIG_IMPORT_HATCH[index + 1] = registerMetaTileEntity(16090 + index, new MetaTileEntityPlusMultiFluidHatch(gtqtcoreId("fluid_hatch.import_16x." + tierName), i, 16, false));
            BIG_EXPORT_HATCH[index + 1] = registerMetaTileEntity(16100 + index, new MetaTileEntityPlusMultiFluidHatch(gtqtcoreId("fluid_hatch.export_16x." + tierName), i, 16, true));
        }

        simpleTiredInit(CREATIVE_ENERGY_HATCHES,
                (i) -> new MetaTileEntityCreativeEnergyHatch(gtqtcoreId("creative_energy_hatch."+GTValues.VN[i].toLowerCase()),i),
                GTQTMetaTileEntities::nextMultiPartID);

    }

    private static <T extends MetaTileEntity> T registerSingleMetaTileEntity(int id, T mte) {
        if (id > 300) return null;
        return MetaTileEntities.registerMetaTileEntity(id + 20000, mte);
    }

}