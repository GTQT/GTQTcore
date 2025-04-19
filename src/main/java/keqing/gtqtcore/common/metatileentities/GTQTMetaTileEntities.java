package keqing.gtqtcore.common.metatileentities;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SimpleGeneratorMetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockTurbineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.multi.electric.generator.MetaTileEntityLargeTurbine;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMachineHatch;
import keqing.gtqtcore.GTQTCoreConfig;
import keqing.gtqtcore.api.GCYSValues;
import keqing.gtqtcore.api.metaileentity.SimpleSteamMetaTileEntity;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing1;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing5;
import keqing.gtqtcore.common.metatileentities.multi.generators.*;
import keqing.gtqtcore.common.metatileentities.multi.generators.Tide.MetaTileEntityTideControl;
import keqing.gtqtcore.common.metatileentities.multi.generators.Tide.MetaTileEntityTideUnit;
import keqing.gtqtcore.common.metatileentities.multi.generators.Wind.MetaTileEntityWindGenerator;
import keqing.gtqtcore.common.metatileentities.multi.generators.generatorRework.MetaTileEntityEnergySubstation;
import keqing.gtqtcore.common.metatileentities.multi.generators.generatorRework.MetaTileEntityLargeBasicGenerator;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.primitive.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.LaserSystem.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.endGame.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.giantEquipment.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.heatExchanger.MetaTileEntityHeatExchanger;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.heatExchanger.MetaTileEntityLargeHeatExchanger;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.heatExchanger.MetaTileEntitySmallHeatExchanger;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.heatSystem.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.kqcc.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.multiThreading.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.overwriteMultiblocks.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.pressure.MetaTileEntityAxialCompressor;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.pressure.MetaTileEntityPressureTank;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.pressure.MetaTileEntityTurbomolecularPump;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.resourceCollection.MetaTileEntityAdvancedFluidDrill;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.resourceCollection.MetaTileEntityAdvancedLargeMiner;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.resourceCollection.MetaTileEntityVoidMiner;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.steam.MetaTileEntitySawMill;
import keqing.gtqtcore.common.metatileentities.multi.multiblockpart.*;
import keqing.gtqtcore.common.metatileentities.single.electric.*;
import keqing.gtqtcore.common.metatileentities.single.steam.MetaTileEntityCreativePressurePump;
import keqing.gtqtcore.common.metatileentities.single.steam.MetaTileEntitySteamEjector;
import keqing.gtqtcore.common.metatileentities.single.steam.MetaTileEntitySteamLatexCollector;
import keqing.gtqtcore.common.metatileentities.single.steam.MetaTileEntitySteamVacuumChamber;
import keqing.gtqtcore.common.metatileentities.storage.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.MV;
import static gregtech.api.GTValues.UHV;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static gregtech.common.metatileentities.MetaTileEntities.registerSimpleMetaTileEntity;
import static keqing.gtqtcore.api.GCYSValues.*;
import static keqing.gtqtcore.api.GTQTValue.gtqtcoreId;
import static keqing.gtqtcore.api.metaileentity.SteamProgressIndicators.COMPRESS;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.Kevlar;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.utils.GTQTUtil.genericGeneratorTankSizeFunctionPlus;
import static keqing.gtqtcore.api.utils.MultiblockRegistryHelper.registerSimpleSteamMetaTileEntity;
import static keqing.gtqtcore.client.textures.GTQTTextures.*;
import static keqing.gtqtcore.common.block.blocks.BlockCompressedFusionReactor.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing1.CasingType.HastelloyX78;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3.CasingType.HC_ALLOY_CASING;

public class GTQTMetaTileEntities {


    ////////////////////////////io
    /* ---------------------------------------------------------------- Multiblock Parts ---------------------------------------------------------------- */

    public static final MetaTileEntityCreativeEnergyHatch[] CREATIVE_ENERGY_HATCHES = new MetaTileEntityCreativeEnergyHatch[GTValues.V.length];
    public static final SimpleGeneratorMetaTileEntity[] NAQUADAH_REACTOR = new SimpleGeneratorMetaTileEntity[3];
    public static final SimpleGeneratorMetaTileEntity[] ROCKET_ENGINE = new SimpleGeneratorMetaTileEntity[3];
    public static final MetaTileEntityHugeFusionReactor[] HUGE_FUSION_REACTOR = new MetaTileEntityHugeFusionReactor[3];
    public static final SimpleGeneratorMetaTileEntity[] COMBUSTION_GENERATOR = new SimpleGeneratorMetaTileEntity[4];
    public static final SimpleGeneratorMetaTileEntity[] STEAM_TURBINE = new SimpleGeneratorMetaTileEntity[4];
    public static final SimpleGeneratorMetaTileEntity[] GAS_TURBINE = new SimpleGeneratorMetaTileEntity[4];
    public static final SimpleGeneratorMetaTileEntity[] FUEL_CELL_TURBINE = new SimpleGeneratorMetaTileEntity[5];
    public static final SimpleMachineMetaTileEntity[] FLUID_CANNER = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static final SimpleMachineMetaTileEntity[] LAMINATOR = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static final SimpleMachineMetaTileEntity[] FLUID_EXTRACTOR = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static final MetaTileEntityMicrowaveEnergyReceiver[] MICROWAVE_ENERGY_RECEIVER = new MetaTileEntityMicrowaveEnergyReceiver[GTValues.V.length - 1];
    public static final SimpleGeneratorMetaTileEntity[] BIOMASS_GENERATOR = new SimpleGeneratorMetaTileEntity[3];
    public static final SimpleGeneratorMetaTileEntity[] ACID_GENERATOR = new SimpleGeneratorMetaTileEntity[3];
    public static final SimpleGeneratorMetaTileEntity[] PLASMA_GENERATOR = new SimpleGeneratorMetaTileEntity[5];
    public static final MetaTileEntityLargeBasicGenerator[] LBG = new MetaTileEntityLargeBasicGenerator[6];
    public static final MetaTileEntityReinforcedRotorHolder[] REINFORCED_ROTOR_HOLDER = new MetaTileEntityReinforcedRotorHolder[14];
    public static final MetaTileEntityCompressedFusionReactor[] COMPRESSED_FUSION_REACTOR = new MetaTileEntityCompressedFusionReactor[6];
    public static MetaTileEntityWrapSwarmHatch[] SWARM_HATCH = new MetaTileEntityWrapSwarmHatch[5];
    public static MetaTileEntityPressureHatch[] PRESSURE_HATCH = new MetaTileEntityPressureHatch[14];
    //////////////////////////
    public static MetaTileEntityBioHatch BIO_HATCH;
    public static MetaTileEntityHeatHatch[] HEAT_HATCH = new MetaTileEntityHeatHatch[5];
    public static MetaTileEntityElectricHeater[] ELECTRIC_HEATER = new MetaTileEntityElectricHeater[5];
    public static MetaTileEntityOilPool OIL_POOL;
    public static MetaTileEntityAlgaeFarm ALGAE_FARM;
    public static MetaTileEntityComponentAssemblyLine COMPONENT_ASSEMBLY_LINE;
    public static MetaTileEntityBufferHatch MULTIPART_BUFFER_HATCH;
    public static MetaTileEntityBlazingBlastFurnace BLAZING_BLAST_FURNACE;
    public static MetaTileEntityBlazingCZPuller BLAZING_CZ_PULLER;
    public static MetaTileEntityHugeChemicalReactor HUGE_CHEMICAL_REACTOR;
    public static MetaTileEntityAntimatterForge ANTIMATTER_FORGE;
    public static MetaTileEntityAntimatterGenerator ANTIMATTER_GENERATOR;
    public static MetaTileEntityIntegratedMiningDivision INTEGRATED_MINING_DIVISION;
    public static MetaTileEntityHugeAlloyBlastSmelter HUGE_ALLOY_BLAST_FURANCE;
    public static MetaTileEntityLargeHeatExchanger LARGE_HEAT_EXCHANGER;
    public static MetaTileEntitySmallHeatExchanger SMALL_HEAT_EXCHANGER;
    public static MetaTileEntityEvaporationPool EVAPORATION_POOL;
    public static MetaTileEntityElectronMicroscope ELECTRON_MICROSCOPE;
    public static MetaTileEntityHugeBlastFurnace HUGE_BLAST_FURANCE;
    public static MetaTileInfWaterHatch INF_WATER_HATCH;
    public static MetaTileEntityLightningRod[] LIGHTNING_ROD = new MetaTileEntityLightningRod[3];
    public static MetaTileEntityDangoteDistillery DANGOTE_DISTILLERY;
    public static MetaTileEntityResearchSystemNetWork RESEARCH_SYSTEM_NETWORK;
    public static MetaTileEntityGeneratorArray LV_GENERATOR_ARRAY;
    public static MetaTileEntityGeneratorArray MV_GENERATOR_ARRAY;
    public static MetaTileEntityGeneratorArray HV_GENERATOR_ARRAY;
    public static MetaTileEntityGeneratorArray EV_GENERATOR_ARRAY;
    public static MetaTileEntityGeneratorArray IV_GENERATOR_ARRAY;

    public static MetaTileEntityProcessingArray LV_PROCESSING_ARRAY;
    public static MetaTileEntityProcessingArray MV_PROCESSING_ARRAY;
    public static MetaTileEntityProcessingArray HV_PROCESSING_ARRAY;
    public static MetaTileEntityDistillationTower DISTILLATION_TOWER;
    public static MetaTileEntityLargeRocketEngine LARGE_ROCKET_ENGIN;
    public static MetaTileEntityLargeRocketEngine EXTREME_LARGE_ROCKET_ENGIN;

    public static MetaTileEntityIndustryWaterPump INDUSTRY_WATER_PUMP;
    public static MetaTileEntityResearchSystemControlCenter KQCC;
    public static MetaTileEntityPrimitiveReactor PRIMITIVE_REACTOR;
    public static MetaTileEntityVacuumFreezer VACUUM_FREEZER;
    public static MetaTileEntityPyrolysisTower PYROLYSIS_TOWER;
    public static MetaTileEntityQuantumForceTransformer QUANTUM_FORCE_TRANSFORMER;
    public static MetaTileEntityFuelRefineFactory FUEL_REFINE_FACTORY;
    public static MetaTileEntityFermentationTank FERMENTATION_TANK;
    public static MetaTileEntityDigester DIGESTER;
    public static MetaTileEntityStepper STEPPER;
    public static MetaTileEntityIndustrialRefiner INDUSTRIAL_REFINER;
    public static MetaTileEntitySeismicDetector SEISMIC_DETECTOR;
    public static MetaTileEntityTideControl TIDE_CONTROL;
    public static MetaTileEntityTideUnit TIDE_UNIT;
    public static MetaTileEntityWindGenerator WIND_GENERATOR;
    public static MetaTileEntityHugeCrackingUnit HUGE_CRACKING_UNIT;
    public static MetaTileEntityExtremeIndustrialGreenhouse EXTREME_INDUSTRIAL_GREENHOUSE;
    public static MetaTileEntityDissolutionTank DISSOLUTION_TANK;
    public static MetaTileEntityGasCollector GAS_COLLECTOR;
    public static MetaTileEntityHyperReactorMkI HYPER_REACTOR_MKI;
    public static MetaTileEntityHyperReactorMkII HYPER_REACTOR_MKII;
    public static MetaTileEntityHyperReactorMkIII HYPER_REACTOR_MKIII;
    public static MetaTileEntitySepticTank SEPTIC_TANK;
    public static MetaTileEntityLargeCircuitAssemblyLine LARGE_CIRCUIT_ASSEMBLY_LINE;
    public static MetaTileEntityIsaMill ISA_MILL;
    public static MetaTileEntityPCBFactory PCB_FACTORY;
    public static MetaTileEntitySaltField SALT_FLIED;
    public static MetaTileEntityCommonRubbishBin COMMON_RUBBISH_BIN;
    public static SimpleMachineMetaTileEntity[] COMPONENT_ASSEMBLER = new SimpleMachineMetaTileEntity[GTValues.IV + 1];
    public static MetaTileEntityElectronBath ELECTROBATH;
    public static MetaTileEntityBiologicalReaction BIOLOGICAL_REACTION;
    public static MetaTileEntityIndustrialFishingPond INDUSTRIAL_FISHING_POND;
    public static MetaTileEntityLargeNaquadahReactor LARGE_NAQUADAH_REACTOR;
    public static MetaTileEntityNeutralNetworkNexus NEUTRAL_NETWORK_NEXUS;
    public static MetaTileEntityCrackingUnit CRACKER;
    public static MetaTileEntityPyrolyseOven PYROLYSE_OVEN;
    public static MetaTileEntityDistillationKettle DISTILLATION_KETTLE;
    public static MetaTileEntityParticleAccelerator PARTICLE_ACCELERATOR;
    public static MetaTileEntityLargeChemicalReactor LARGE_CHEMICAL_REACTOR;
    public static MetaTileEntityPreciseAssembler PRECISE_ASSEMBLER;
    public static MetaTileEntityFlotationFactory FLOTATION_FACTORY;
    public static MetaTileEntityLargeProcessingFactory LARGE_PROCESSING_FACTORY;
    public static MetaTileEntityVacuumDryingFurnace VACUUM_DRYING_FURNACE;
    public static MetaTileEntityMiningDrill MINING_DRILL;
    public static MetaTileEntityHeatFurnace HEAT_FURNACE;
    public static MetaTileEntityPrimitiveTreeFarmer PRIMITIVE_TREE_FARMER;
    public static MetaTileEntitySawMill SAW_MILL;
    public static MetaTileEntityAdvanceKQCC ADV_KQCC;
    public static MetaTileEntityGeneMutagenesis GENE_MUTAGENESIS;
    public static MetaTileEntityIndustrialInductionFurnace INDUSTRIAL_INDUCTION_FURNACE;
    public static MetaTileEntityParticleAggregator PARTICLE_AGGREGATOR;
    public static MetaTileEntityPressureMachine[] VACUUM_CHAMBER = new MetaTileEntityPressureMachine[V.length - 1];
    public static SimpleSteamMetaTileEntity[] STEAM_SPINNER = new SimpleSteamMetaTileEntity[2];
    public static MetaTileEntityParallelHatch[] PARALLEL_HATCH = new MetaTileEntityParallelHatch[V.length - 1];
    public static MetaTileEntityRadiationHatch[] RADIATION_HATCH = new MetaTileEntityRadiationHatch[5];
    public static MetaTileEntityElectrodeHatch[] ELECTRODE_HATCH = new MetaTileEntityElectrodeHatch[5];
    public static MetaTileEntityDrillHeadHatch[] DRILL_HEAD_HATCH = new MetaTileEntityDrillHeadHatch[5];
    public static MetaTileEntityKQCCPartHatch[] KQCC_HATCH = new MetaTileEntityKQCCPartHatch[9];
    public static MetaTileEntityLocalModelComputationHatch LOCAL_COMPUTER_HATCH;
    public static MetaTileEntityGravitySeparator GRAVITY_SEPARATOR;
    public static MetaTileEntityFrothFlotationTank FROTH_FLOTATION_TANK;
    public static MetaTileEntityOceanPumper OCEAN_PUMPER;
    public static MetaTileEntityClarifier CLARIFIER;
    public static MetaTileEntitySolarPlate SOLAR_PLATE;
    public static MetaTileEntityPowerSupply POWER_SUPPLY;
    public static MetaTileEntitySMSF SMSF;
    public static MetaTileEntityAdvanceNetworkSwitch ADV_NETWORK_SWITCH;
    public static MetaTileEntityMiniDataBank MINI_DATE_BANK;
    public static MetaTileEntityAdvanceDataBank ADV_DATE_BANK;
    public static MetaTileEntityRubbishBin RUBBISH_BIN;
    public static MetaTileEntityAirIntakeHatch AIR_INTAKE_HATCH;
    public static MetaTileEntityAirIntakeHatch EXTREME_AIR_INTAKE_HATCH;
    public static MetaTileEntityAirIntakeHatch ULTIMATE_AIR_INTAKE_HATCH;
    public static MetaTileEntitySingleItemInputBus SINGLE_ITEM_INPUT_BUS;
    public static MetaTileEntitySuperInputBus SUPER_INPUT_BUS;
    public static MetaTileEntitySingleInputBus SINGLE_INPUT_BUS;
    public static MetaTileEntitySteamLatexCollector[] STEAM_LATEX_COLLECTOR = new MetaTileEntitySteamLatexCollector[2];
    public static MetaTileEntityLatexCollector[] LATEX_COLLECTOR = new MetaTileEntityLatexCollector[4];
    public static SimpleMachineMetaTileEntity[] ULTRAVIOLET_LAMP_CHAMBER = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static SimpleMachineMetaTileEntity[] PRESSURE_LAMINATOR = new SimpleMachineMetaTileEntity[V.length - 1];
    public static SimpleMachineMetaTileEntity[] DEHYDRATOR = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static SimpleMachineMetaTileEntity[] BIO_REACTOR = new SimpleMachineMetaTileEntity[V.length - 1];

    public static SimpleMachineMetaTileEntity[] LOW_TEMP_ACTIVATOR = new SimpleMachineMetaTileEntity[V.length - 1];
    public static SimpleMachineMetaTileEntity[] POLYMERIZATION_TANK = new SimpleMachineMetaTileEntity[V.length - 1];
    public static SimpleMachineMetaTileEntity[] SPINNER = new SimpleMachineMetaTileEntity[V.length - 1];
    public static SimpleMachineMetaTileEntity[] BURNER_REACTOR = new SimpleMachineMetaTileEntity[V.length - 1];
    public static SimpleMachineMetaTileEntity[] CRYOGENIC_REACTOR = new SimpleMachineMetaTileEntity[V.length - 1];
    public static SimpleSteamMetaTileEntity[] STEAM_ROASTER = new SimpleSteamMetaTileEntity[2];
    public static SimpleMachineMetaTileEntity[] ROASTER = new SimpleMachineMetaTileEntity[V.length - 1];

    public static SimpleMachineMetaTileEntity[] CATALYTIC_REFORMER = new SimpleMachineMetaTileEntity[V.length - 1];
    public static SimpleMachineMetaTileEntity[] SONICATOR = new SimpleMachineMetaTileEntity[V.length - 1];
    public static SimpleMachineMetaTileEntity[] ION_IMPLANTER = new SimpleMachineMetaTileEntity[V.length - 1];
    public static SimpleMachineMetaTileEntity[] CVD_UNIT = new SimpleMachineMetaTileEntity[V.length - 1];
    public static SimpleMachineMetaTileEntity[] FLOTATION_CELL = new SimpleMachineMetaTileEntity[V.length - 1];
    public static SimpleMachineMetaTileEntity[] POLISHER = new SimpleMachineMetaTileEntity[V.length - 1];
    public static SimpleMachineMetaTileEntity[] SIMULATOR = new SimpleMachineMetaTileEntity[V.length - 1];

    public static SimpleMachineMetaTileEntity[] DUPLICATOR = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static SimpleMachineMetaTileEntity[] UU_PRODUCTER = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static SimpleMachineMetaTileEntity[] RECYCLE = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static MetaTileEntityLargeElementDuplicator LARGE_ELEMENT_DUPLICATOR;
    public static MetaTileEntityLargeUUProducter LARGE_UU_PRODUCTER;
    public static MetaTileEntityMillBallHatch MULTIPART_BALL_HATCH;
    public static MetaTileEntityCatalystHatch[] CATALYST_HATCH = new MetaTileEntityCatalystHatch[4];
    public static MetaTileEntityResearchSystemNetworkSwitch KQNS;
    public static MetaTileEntityLaserBlastFurnace LASER_BLAST_FURNACE;
    public static MetaTileEntityLaserAlloyFurnace LASER_ALLOY_FURNACE;
    public static MetaTileEntityLaserCutter LASER_CUTTER;
    public static MetaTileEntityLaserENV LASER_ENV;
    public static MetaTileEntityLaserChemicalPlant LASER_CHEMICAL_PLANT;
    public static MetaTileEntityLaserBooster LASER_FBT;
    public static MetaTileEntityLaserFusionCore LASER_FBC;
    public static MetaTileEntitySBPRC SBPRC;
    public static MetaTileEntitySBPRI SBPRI;
    public static MetaTileEntitySBPRO SBPRO;
    //public static MetaTileEntityMegaCleanroom MEGA_CLEANROOM;
    public static MetaTileEntityAdvancedLargeMiner EXTREME_LARGE_MINER;
    public static MetaTileEntityAdvancedLargeMiner ULTIMATE_LARGE_MINER;
    public static MetaTileEntityAdvancedLargeMiner INFINITY_LARGE_MINER;
    public static MetaTileEntityAdvancedFluidDrill ADVANCED_FLUID_DRILL_RIG;
    public static MetaTileEntityAdvancedFluidDrill EXTREME_FLUID_DRILL_RIG;
    public static MetaTileEntityAdvancedFluidDrill ULTIMATE_FLUID_DRILL_RIG;
    public static MetaTileEntityAdvancedFluidDrill INFINITY_FLUID_DRILL_RIG;
    public static MetaTileEntityVoidMiner[] VOID_MINER = new MetaTileEntityVoidMiner[3];
    public static MetaTileEntityDimensionalMixer DIMENSIONAL_MIXER;
    public static MetaTileEntityDimensionallyPlasmFurnace DIMENSIONAL_PLASMA_FURNACE;
    public static SimpleMachineMetaTileEntity[] AUTO_CHISEL = new SimpleMachineMetaTileEntity[3];
    public static MetaTileEntityThreeDimPrinter THREE_DIM_PRINT;
    public static MetaTileEntityElectronOil ELE_OIL;
    public static MetaTileEntityAdvancedArcFurnace ADV_ARC_FURNACE;
    public static MetaTileEntityBioCentrifuge BIO_CENTRIFUGE;
    public static MetaTileEntityEnzymesReaction ENZYMES_REACTOR;
    public static MetaTileEntityNeutronActivator NEUTRON_ACTIVATOR;
    public static MetaTileEntityChemicalPlant CHEMICAL_PLANT;
    public static MetaTileEntityLaserEngraving LASER_ENGRAVING;
    public static MetaTileEntityWaterPowerStation[] WATER_POWER_STATION = new MetaTileEntityWaterPowerStation[3];
    public static MetaTileLaserBooster[] LASER_BOOSTER = new MetaTileLaserBooster[6];
    public static MetaTileEntityKineticEnergyBattery KINETIC_ENERGY_BATTERY;
    //public static MetaTileEntityGrayElectricPowerBank GRAY_ELECTRIC_POWER_BANK;
    public static MetaTileEntityAssemblyLine ASSEMBLY_LINE;
    public static MetaTileEntityKQCCComputationHatch[] KQCC_COMPUTATION_HATCH_RECEIVER = new MetaTileEntityKQCCComputationHatch[GTValues.V.length - 1];
    public static MetaTileEntityKQCCComputationHatch[] KQCC_COMPUTATION_HATCH_TRANSMITTER = new MetaTileEntityKQCCComputationHatch[GTValues.V.length - 1];
    public static MetaTileHighEnergyLaserHatch[] LASER_INPUT = new MetaTileHighEnergyLaserHatch[GTValues.V.length - 1];
    public static MetaTileHighEnergyLaserHatch[] LASER_OUTPUT = new MetaTileHighEnergyLaserHatch[GTValues.V.length - 1];
    public static MetaTileEntityMSF MSF;
    public static MetaTileEntityNicollDysonBeamer NICOLL_DYSON_BEAMER;
    public static MetaTileEntityDimensionallyBiomimeticFactory DIMENSIONAL_BIOMIMETIC_FACTORY;
    public static MetaTileEntityLargeBiomassGenerator LARGE_BIOMASS_GENERATOR;
    public static MetaTileEntityFluidRubbishBin FLUID_RUBBISH_BIN;
    public static MetaTileEntityParticleAcceleratorIO[] PARTICLE_ACCELERATOR_IO = new MetaTileEntityParticleAcceleratorIO[4];
    public static MetaTileEntityAdvancedAssemblyLine ADVANCED_ASSEMBLY_LINE;
    public static MetaTileEntityLargeGrind LARGE_GRIND;
    public static MetaTileEntityLaserEmitter LASER_EMITTER;
    public static MetaTileEntityLaserTranslation LASER_TRANSLATION;
    public static MetaTileEntitySwitch LASER_SWITCH;
    public static MetaTileEntityLargeForging LARGE_FORGING;
    public static MetaTileEntityPrimitiveRoaster PRIMITIVE_ROASTER;
    public static MetaTileEntityLargeExtractor LARGE_EXTRACTOR;
    public static MetaTileEntityLargeMixer LARGE_MIXER;
    public static MetaTileEntityLargeDesulphurization LARGE_DESULPHURIZATION;
    public static MetaTileEntityLargeFluidizedBed LARGE_FLUIDIZED_BED;
    public static MetaTileEntityNanoCoating NANO_COATING;
    public static MetaTileEntityCryogenicFreezer CRYOGENIC_FREEZER;
    public static MetaTileEntityHeatExchanger HEAT_CHANGER;
    public static MetaTileEntityIntegratedOreProcessor INTEGRATED_ORE_PROCESSOR;
    public static MetaTileEntityCrystallizationCrucible LARGE_CRYSTALLIZATION_CRUCIBLE;
    public static MetaTileEntityIndustrialRoaster LARGE_ROASTER;
    public static MetaTileEntityMegaTurbine MEGA_STEAM_TURBINE;
    public static MetaTileEntityMegaTurbine MEGA_GAS_TURBINE;
    public static MetaTileEntityMegaTurbine MEGA_PLASMA_TURBINE;
    public static MetaTileEntityMegaTurbine MEGA_FUEL_CELL_TURBINE;

    public static MetaTileEntitySteamEjector[] STEAM_EJECTOR=new MetaTileEntitySteamEjector[2];
    public static MetaTileEntitySteamVacuumChamber[] STEAM_VACUUM_CHAMBER=new MetaTileEntitySteamVacuumChamber[2];
    public static MetaTileEntityCreativePressurePump CREATIVE_PRESSURE;
    public static MetaTileEntityPressureTank[] PRESSURE_TANK=new MetaTileEntityPressureTank[3];

    public static MetaTileEntityLargeHighPressueSteamTurbine HIGH_PRESSURE_STEAM_TURBINE;
    public static MetaTileEntityLargeSupercriticalSteamTurbine SUPERCRITICAL_STEAM_TURBINE;
    public static MetaTileEntityMegaTurbine MEGA_HIGH_PRESSURE_STEAM_TURBINE;
    public static MetaTileEntityMegaTurbine MEGA_SUPERCRITICAL_STEAM_TURBINE;

    public static MetaTileEntityAxialCompressor SUBSONIC_AXIAL_COMPRESSOR;
    public static MetaTileEntityAxialCompressor SUPERSONIC_AXIAL_COMPRESSOR;
    public static MetaTileEntityTurbomolecularPump LOW_POWER_TURBOMOLECULAR_PUMP;
    public static MetaTileEntityTurbomolecularPump HIGH_POWER_TURBOMOLECULAR_PUMP;
    public static MetaTileEntityPressureBooster[] PRESSURE_PUMP = new MetaTileEntityPressureBooster[9];
    public static MetaTileEntityPressureBooster[] PRESSURE_COMPRESSOR = new MetaTileEntityPressureBooster[9];
    public static MetaTileEntityGasTank[] GAS_TANK = new MetaTileEntityGasTank[10];


    public static MetaTileEntityPowerSupplyHatch POWER_SUPPLY_HATCH_BASIC;
    public static MetaTileEntityPowerSupplyHatch[] POWER_SUPPLY_HATCH_BATTLE = new MetaTileEntityPowerSupplyHatch[5];
    public static MetaTileEntityPowerSupplyHatch[] POWER_SUPPLY_HATCH_SUPPLY = new MetaTileEntityPowerSupplyHatch[10];

    public static MetaTileEntityLargeOreWasher LARGE_ORE_WASHER;
    public static MetaTileEntityLargeThermalCentrifuge LARGE_THERMAL_CENTRIFUGE;
    public static MetaTileEntityNanoscaleFabricator LARGE_NANOSCALE_FABRICATOR;
    public static MetaTileEntityCVDUnit LARGE_CVD_UNIT;
    public static MetaTileEntityPhotolithographyFactory PHOTOLITHOGRAPHY_FACTORY;
    public static MetaTileEntityMicrowaveEnergyReceiverControl MICROWAVE_ENERGY_RECEIVER_CONTROL;
    public static MetaTileEntityExtremesCVD LARGE_EX_CVD;
    public static MetaTileEntityChemicalReactor LARGE_CHEMICAL_FACTORY;
    public static MetaTileEntityUltravioletLamp LARGE_ULTRAVIOLET_LAMP;
    public static MetaTileEntityMixer LARGE_MIX;
    public static MetaTileEntityPressureFactory LARGE_PRESSURE_FACTORY;
    public static MetaTileEntitySuprachronalNeutroniumForge SUPRACHRONAL_NEUTRONIUM_FORGE;
    public static MetaTileEntityBurnerReactor LARGE_BURNER_REACTOR;
    public static MetaTileEntityCryoReactor LARGE_CRYOGENIC_REACTOR;
    public static MetaTileEntityFracker LARGE_HYDRAULIC_FRACKER;
    public static MetaTileEntitySonicator LARGE_SONICATOR;
    public static MetaTileEntityCatalyticReformer LARGE_CATALYTIC_REFORMER;
    public static MetaTileEntityIndustrialDrill INDUSTRIAL_DRILL;
    public static MetaTileEntityIonImplanter LARGE_ION_IMPLANTATOR;
    public static MetaTileEntityCZPuller LARGE_CZ_PULLER;
    public static MetaTileEntityEnergySubstation ENERGY_SUBSTATION;
    public static MetaTileEntityNaquadahFuelFactory NAQUADAH_FUEL_FACTORY;
    public static MetaTileEntityPlasmaCondenser PLASMA_CONDENSER;
    public static MetaTileEntityHeatHatchExchange HEAT_HATCH_EXCHANGE;
    public static MetaTileEntityDimensionallyTranscendentPlasmaForge DIMENSIONALLY_TRANSCENDENT_PLASMA_FORGE;
    public static MetaTileEntityIndustrialMacerator INDUSTRIAL_MACERATOR;
    public static MetaTileEntityIndustrialCentrifuge INDUSTRIAL_CENTRIFUGE;
    public static MetaTileEntityIndustrialMix INDUSTRIAL_MIX;
    public static MetaTileEntityIndustrialWasher INDUSTRIAL_WASHER;
    public static MetaTileEntityIndustrialHammer INDUSTRIAL_HAMMER;
    public static MetaTileEntityIndustrialFurnace INDUSTRIAL_FURNACE;
    public static MetaTileEntityIndustrialAssemblyLine INDUSTRIAL_ASSEMBLY_LINE;
    public static MetaTileEntityIndustrialChemicalReactor INDUSTRIAL_CHEMICAL_REACTOR;
    public static MetaTileEntityCoreMacerator CORE_MACERATOR;
    public static MetaTileEntityCoreCentrifuge CORE_CENTRIFUGE;
    public static MetaTileEntityCoreMix CORE_MIX;
    public static MetaTileEntityCoreWasher CORE_WASHER;
    public static MetaTileEntityCoreHammer CORE_HAMMER;
    public static MetaTileEntityCoreFurnace CORE_FURNACE;
    public static MetaTileEntityIndustrialBender INDUSTRIAL_BENDER;
    public static MetaTileEntityIndustrialWireMill INDUSTRIAL_WIREMILL;
    public static MetaTileEntityIndustrialExtruder INDUSTRIAL_EXTRUDER;
    public static MetaTileEntityLargeTurbine LARGE_FUEL_TURBINE;
    public static MetaTileEntitySterileCleaningMaintenanceHatch STERILE_CLEANING_MAINTENANCE_HATCH;
    public static MetaTileEntityISO3CleaningMaintenanceHatch ISO3_CLEANING_MAINTENANCE_HATCH;
    public static MetaTileEntityISO2CleaningMaintenanceHatch ISO2_CLEANING_MAINTENANCE_HATCH;
    public static MetaTileEntityISO1CleaningMaintenanceHatch ISO1_CLEANING_MAINTENANCE_HATCH;
    public static MetaTileEntityParticleHatch PARTICLE_HATCH;
    public static MetaTileEntityGeneratorHatch GENERATOR_HATCH;

    public static MetaTileEntityPlasticCan PE_CAN;
    public static MetaTileEntityPlasticCan PTFE_CAN;
    public static MetaTileEntityPlasticCan ZYLON_CAN;
    public static MetaTileEntityPlasticCan PBI_CAN;
    public static MetaTileEntityPlasticCan KEVLAR_CAN;


    public static MetaTileEntityBridge INV_BRIDGE;
    public static MetaTileEntityBridge TANK_BRIDGE;
    public static MetaTileEntityBridge INV_TANK_BRIDGE;
    public static MetaTileEntityBridge UNIVERSAL_BRIDGE;

    public static MetaTileEntityExtender INV_EXTENDER;
    public static MetaTileEntityExtender TANK_EXTENDER;
    public static MetaTileEntityExtender INV_TANK_EXTENDER;
    public static MetaTileEntityExtender UNIVERSAL_EXTENDER;

    private static int startId = 13000;
    private static final int END_ID = startId + 1000;

    public static void simpleTiredInit(MetaTileEntity[] tileEntities, IntFunction<MetaTileEntity> function, IntSupplier idSupplier, IntPredicate canAdd) {
        for (int i = 0; i < GTValues.V.length; i++) {
            if (canAdd.test(i)) {
                tileEntities[i] = registerMetaTileEntity(idSupplier.getAsInt(), function.apply(i));
            }
        }
    }



    public static void simpleTiredInit(MetaTileEntity[] tileEntities, IntFunction<MetaTileEntity> function, IntSupplier idSupplier) {
        simpleTiredInit(tileEntities, function, idSupplier, (i) -> true);
    }

    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static void initialization() {
        GTQTLog.logger.info("Registering MetaTileEntities");
        //发电设备 单方块
        LIGHTNING_ROD[0] = registerMetaTileEntity(3000, new MetaTileEntityLightningRod(gtqtcoreId("lightning_rod.iv"), GTValues.IV));
        LIGHTNING_ROD[1] = registerMetaTileEntity(3001, new MetaTileEntityLightningRod(gtqtcoreId("lightning_rod.luv"), LuV));
        LIGHTNING_ROD[2] = registerMetaTileEntity(3002, new MetaTileEntityLightningRod(gtqtcoreId("lightning_rod.zpm"), GTValues.ZPM));
        NAQUADAH_REACTOR[0] = registerMetaTileEntity(3003, new SimpleGeneratorMetaTileEntity(gtqtcoreId("naquadah_reactor.iv"), GTQTcoreRecipeMaps.NAQUADAH_REACTOR, GTQTTextures.NAQUADAH_REACTOR_OVERLAY, 5, genericGeneratorTankSizeFunctionPlus));
        NAQUADAH_REACTOR[1] = registerMetaTileEntity(3004, new SimpleGeneratorMetaTileEntity(gtqtcoreId("naquadah_reactor.luv"), GTQTcoreRecipeMaps.NAQUADAH_REACTOR, GTQTTextures.NAQUADAH_REACTOR_OVERLAY, 6, genericGeneratorTankSizeFunctionPlus));
        NAQUADAH_REACTOR[2] = registerMetaTileEntity(3005, new SimpleGeneratorMetaTileEntity(gtqtcoreId("naquadah_reactor.zpm"), GTQTcoreRecipeMaps.NAQUADAH_REACTOR, GTQTTextures.NAQUADAH_REACTOR_OVERLAY, 7, genericGeneratorTankSizeFunctionPlus));
        ROCKET_ENGINE[0] = registerMetaTileEntity(3006, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rocket_engine.hv"), GTQTcoreRecipeMaps.ROCKET_RECIPES, GTQTTextures.ROCKET_ENGINE_OVERLAY, 3, genericGeneratorTankSizeFunctionPlus));
        ROCKET_ENGINE[1] = registerMetaTileEntity(3007, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rocket_engine.ev"), GTQTcoreRecipeMaps.ROCKET_RECIPES, GTQTTextures.ROCKET_ENGINE_OVERLAY, 4, genericGeneratorTankSizeFunctionPlus));
        ROCKET_ENGINE[2] = registerMetaTileEntity(3008, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rocket_engine.iv"), GTQTcoreRecipeMaps.ROCKET_RECIPES, GTQTTextures.ROCKET_ENGINE_OVERLAY, 5, genericGeneratorTankSizeFunctionPlus));

        COMBUSTION_GENERATOR[0] = registerMetaTileEntity(3009, new SimpleGeneratorMetaTileEntity(gtqtcoreId("combustion_generator.ev"), RecipeMaps.COMBUSTION_GENERATOR_FUELS, Textures.COMBUSTION_GENERATOR_OVERLAY, 4, genericGeneratorTankSizeFunctionPlus));
        COMBUSTION_GENERATOR[1] = registerMetaTileEntity(3010, new SimpleGeneratorMetaTileEntity(gtqtcoreId("combustion_generator.iv"), RecipeMaps.COMBUSTION_GENERATOR_FUELS, Textures.COMBUSTION_GENERATOR_OVERLAY, 5, genericGeneratorTankSizeFunctionPlus));
        STEAM_TURBINE[0] = registerMetaTileEntity(3011, new SimpleGeneratorMetaTileEntity(gtqtcoreId("steam_turbine.ev"), RecipeMaps.STEAM_TURBINE_FUELS, Textures.STEAM_TURBINE_OVERLAY, 4, genericGeneratorTankSizeFunctionPlus));
        STEAM_TURBINE[1] = registerMetaTileEntity(3012, new SimpleGeneratorMetaTileEntity(gtqtcoreId("steam_turbine.iv"), RecipeMaps.STEAM_TURBINE_FUELS, Textures.STEAM_TURBINE_OVERLAY, 5, genericGeneratorTankSizeFunctionPlus));
        GAS_TURBINE[0] = registerMetaTileEntity(3013, new SimpleGeneratorMetaTileEntity(gtqtcoreId("gas_turbine.ev"), RecipeMaps.GAS_TURBINE_FUELS, Textures.GAS_TURBINE_OVERLAY, 4, genericGeneratorTankSizeFunctionPlus));
        GAS_TURBINE[1] = registerMetaTileEntity(3014, new SimpleGeneratorMetaTileEntity(gtqtcoreId("gas_turbine.iv"), RecipeMaps.GAS_TURBINE_FUELS, Textures.GAS_TURBINE_OVERLAY, 5, genericGeneratorTankSizeFunctionPlus));

        FUEL_CELL_TURBINE[0] = registerMetaTileEntity(3015, new SimpleGeneratorMetaTileEntity(gtqtcoreId("fuel_cell_turbine.lv"), GTQTcoreRecipeMaps.FUEL_CELL, Textures.COMBUSTION_GENERATOR_OVERLAY, 1, genericGeneratorTankSizeFunctionPlus));
        FUEL_CELL_TURBINE[1] = registerMetaTileEntity(3016, new SimpleGeneratorMetaTileEntity(gtqtcoreId("fuel_cell_turbine.mv"), GTQTcoreRecipeMaps.FUEL_CELL, Textures.COMBUSTION_GENERATOR_OVERLAY, 2, genericGeneratorTankSizeFunctionPlus));
        FUEL_CELL_TURBINE[2] = registerMetaTileEntity(3017, new SimpleGeneratorMetaTileEntity(gtqtcoreId("fuel_cell_turbine.hv"), GTQTcoreRecipeMaps.FUEL_CELL, Textures.COMBUSTION_GENERATOR_OVERLAY, 3, genericGeneratorTankSizeFunctionPlus));
        FUEL_CELL_TURBINE[3] = registerMetaTileEntity(3018, new SimpleGeneratorMetaTileEntity(gtqtcoreId("fuel_cell_turbine.ev"), GTQTcoreRecipeMaps.FUEL_CELL, Textures.COMBUSTION_GENERATOR_OVERLAY, 4, genericGeneratorTankSizeFunctionPlus));
        FUEL_CELL_TURBINE[4] = registerMetaTileEntity(3019, new SimpleGeneratorMetaTileEntity(gtqtcoreId("fuel_cell_turbine.iv"), GTQTcoreRecipeMaps.FUEL_CELL, Textures.COMBUSTION_GENERATOR_OVERLAY, 5, genericGeneratorTankSizeFunctionPlus));

        BIOMASS_GENERATOR[0] = registerMetaTileEntity(3023, new SimpleGeneratorMetaTileEntity(gtqtcoreId("biomass_generator.ev"), GTQTcoreRecipeMaps.BIOMASS_GENERATOR_RECIPES, GTQTTextures.BIOMASS_GENERATOR_OVERLAY, 4, GTUtility.genericGeneratorTankSizeFunction));
        BIOMASS_GENERATOR[1] = registerMetaTileEntity(3024, new SimpleGeneratorMetaTileEntity(gtqtcoreId("biomass_generator.iv"), GTQTcoreRecipeMaps.BIOMASS_GENERATOR_RECIPES, GTQTTextures.BIOMASS_GENERATOR_OVERLAY, 5, GTUtility.genericGeneratorTankSizeFunction));
        BIOMASS_GENERATOR[2] = registerMetaTileEntity(3025, new SimpleGeneratorMetaTileEntity(gtqtcoreId("biomass_generator.luv"), GTQTcoreRecipeMaps.BIOMASS_GENERATOR_RECIPES, GTQTTextures.BIOMASS_GENERATOR_OVERLAY, 6, GTUtility.genericGeneratorTankSizeFunction));

        ACID_GENERATOR[0] = registerMetaTileEntity(3026, new SimpleGeneratorMetaTileEntity(gtqtcoreId("acid_generator.mv"), GTQTcoreRecipeMaps.ACID_GENERATOR_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 2, GTUtility.genericGeneratorTankSizeFunction));
        ACID_GENERATOR[1] = registerMetaTileEntity(3027, new SimpleGeneratorMetaTileEntity(gtqtcoreId("acid_generator.hv"), GTQTcoreRecipeMaps.ACID_GENERATOR_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 3, GTUtility.genericGeneratorTankSizeFunction));
        ACID_GENERATOR[2] = registerMetaTileEntity(3028, new SimpleGeneratorMetaTileEntity(gtqtcoreId("acid_generator.ev"), GTQTcoreRecipeMaps.ACID_GENERATOR_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 4, GTUtility.genericGeneratorTankSizeFunction));

        PLASMA_GENERATOR[0] = registerMetaTileEntity(3029, new SimpleGeneratorMetaTileEntity(gtqtcoreId("plasma_generator.ev"), RecipeMaps.PLASMA_GENERATOR_FUELS, Textures.FUSION_REACTOR_OVERLAY, 4, GTUtility.genericGeneratorTankSizeFunction));
        PLASMA_GENERATOR[1] = registerMetaTileEntity(3030, new SimpleGeneratorMetaTileEntity(gtqtcoreId("plasma_generator.iv"), RecipeMaps.PLASMA_GENERATOR_FUELS, Textures.FUSION_REACTOR_OVERLAY, 5, GTUtility.genericGeneratorTankSizeFunction));
        PLASMA_GENERATOR[2] = registerMetaTileEntity(3031, new SimpleGeneratorMetaTileEntity(gtqtcoreId("plasma_generator.luv"), RecipeMaps.PLASMA_GENERATOR_FUELS, Textures.FUSION_REACTOR_OVERLAY, 6, GTUtility.genericGeneratorTankSizeFunction));
        PLASMA_GENERATOR[3] = registerMetaTileEntity(3032, new SimpleGeneratorMetaTileEntity(gtqtcoreId("plasma_generator.zpm"), RecipeMaps.PLASMA_GENERATOR_FUELS, Textures.FUSION_REACTOR_OVERLAY, 7, GTUtility.genericGeneratorTankSizeFunction));
        PLASMA_GENERATOR[4] = registerMetaTileEntity(3033, new SimpleGeneratorMetaTileEntity(gtqtcoreId("plasma_generator.uv"), RecipeMaps.PLASMA_GENERATOR_FUELS, Textures.FUSION_REACTOR_OVERLAY, 8, GTUtility.genericGeneratorTankSizeFunction));

        //发电设备 多方块
        LBG[0] = registerMetaTileEntity(3050, new MetaTileEntityLargeBasicGenerator(gtqtcoreId("lbg.steam.1"), RecipeMaps.STEAM_TURBINE_FUELS, 1, MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID), MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE), Steel));
        LBG[1] = registerMetaTileEntity(3051, new MetaTileEntityLargeBasicGenerator(gtqtcoreId("lbg.gas.1"), RecipeMaps.GAS_TURBINE_FUELS, 1, MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID), MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE), Steel));
        LBG[2] = registerMetaTileEntity(3052, new MetaTileEntityLargeBasicGenerator(gtqtcoreId("lbg.gas.2"), RecipeMaps.GAS_TURBINE_FUELS, 2, MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.ALUMINIUM_FROSTPROOF), GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.AL_TURBINE_CASING), Aluminium));
        LBG[3] = registerMetaTileEntity(3053, new MetaTileEntityLargeBasicGenerator(gtqtcoreId("lbg.combustion.1"), RecipeMaps.COMBUSTION_GENERATOR_FUELS, 1, MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID), MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE), Steel));
        LBG[4] = registerMetaTileEntity(3054, new MetaTileEntityLargeBasicGenerator(gtqtcoreId("lbg.combustion.2"), RecipeMaps.COMBUSTION_GENERATOR_FUELS, 2, MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.ALUMINIUM_FROSTPROOF), GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.AL_TURBINE_CASING), Aluminium));
        LBG[5] = registerMetaTileEntity(3055, new MetaTileEntityLargeBasicGenerator(gtqtcoreId("lbg.combustion.3"), RecipeMaps.COMBUSTION_GENERATOR_FUELS, 3, MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN), GTQTMetaBlocks.blockMultiblockCasing5.getState(BlockMultiblockCasing5.TurbineCasingType.SA_TURBINE_CASING), StainlessSteel));
        HYPER_REACTOR_MKI = registerMetaTileEntity(3056, new MetaTileEntityHyperReactorMkI(gtqtcoreId("hyper_reactor_mk1")));
        HYPER_REACTOR_MKII = registerMetaTileEntity(3057, new MetaTileEntityHyperReactorMkII(gtqtcoreId("hyper_reactor_mk2")));
        HYPER_REACTOR_MKIII = registerMetaTileEntity(3058, new MetaTileEntityHyperReactorMkIII(gtqtcoreId("hyper_reactor_mk3")));

        LARGE_NAQUADAH_REACTOR = registerMetaTileEntity(3059, new MetaTileEntityLargeNaquadahReactor(gtqtcoreId("large_naquadah_reactor")));
        LARGE_ROCKET_ENGIN = registerMetaTileEntity(3060, new MetaTileEntityLargeRocketEngine(gtqtcoreId("large_rocket_engin"), LuV));
        EXTREME_LARGE_ROCKET_ENGIN = registerMetaTileEntity(3061, new MetaTileEntityLargeRocketEngine(gtqtcoreId("extreme_large_rocket_engin"), ZPM));
        SOLAR_PLATE = registerMetaTileEntity(3062, new MetaTileEntitySolarPlate(gtqtcoreId("solar_plate")));

        //
        WATER_POWER_STATION[0] = registerMetaTileEntity(3064, new MetaTileEntityWaterPowerStation(gtqtcoreId("water_power_station_mk1"), 1));
        WATER_POWER_STATION[1] = registerMetaTileEntity(3065, new MetaTileEntityWaterPowerStation(gtqtcoreId("water_power_station_mk2"), 2));
        WATER_POWER_STATION[2] = registerMetaTileEntity(3066, new MetaTileEntityWaterPowerStation(gtqtcoreId("water_power_station_mk3"), 3));

        MEGA_STEAM_TURBINE = registerMetaTileEntity(3067, new MetaTileEntityMegaTurbine(gtqtcoreId("mega_turbine.steam"), RecipeMaps.STEAM_TURBINE_FUELS, 3, MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STEEL_TURBINE_CASING), MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STEEL_GEARBOX), Textures.SOLID_STEEL_CASING, false, GTQTTextures.MEGA_TURBINE_OVERLAY));
        MEGA_GAS_TURBINE = registerMetaTileEntity(3068, new MetaTileEntityMegaTurbine(gtqtcoreId("mega_turbine.gas"), RecipeMaps.GAS_TURBINE_FUELS, 4, MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STAINLESS_TURBINE_CASING), MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STAINLESS_STEEL_GEARBOX), Textures.CLEAN_STAINLESS_STEEL_CASING, true, GTQTTextures.MEGA_TURBINE_OVERLAY));
        MEGA_PLASMA_TURBINE = registerMetaTileEntity(3069, new MetaTileEntityMegaTurbine(gtqtcoreId("mega_turbine.plasma"), RecipeMaps.PLASMA_GENERATOR_FUELS, 5, GTQTMetaBlocks.blockMultiblockCasing3.getState(HC_ALLOY_CASING), GTQTMetaBlocks.blockMultiblockCasing3.getState(HG1223_GEARBOX), GTQTTextures.HC_ALLOY_CASING, false, GTQTTextures.MEGA_TURBINE_OVERLAY));
        MEGA_FUEL_CELL_TURBINE = registerMetaTileEntity(3063, new MetaTileEntityMegaTurbine(gtqtcoreId("mega_turbine.fuel_cell"), FUEL_CELL, 5, GTQTMetaBlocks.blockMultiblockCasing3.getState(NITINOL_MACHINE_CASING), GTQTMetaBlocks.blockMultiblockCasing3.getState(NITINOL_GEARBOX), GTQTTextures.NITINOL_CASING, true, GTQTTextures.MEGA_TURBINE_OVERLAY));

        HIGH_PRESSURE_STEAM_TURBINE = registerMetaTileEntity(3070, new MetaTileEntityLargeHighPressueSteamTurbine(gtqtcoreId("high_pressure_steam_turbine")));
        SUPERCRITICAL_STEAM_TURBINE = registerMetaTileEntity(3071, new MetaTileEntityLargeSupercriticalSteamTurbine(gtqtcoreId("supercritical_steam_turbine")));
        MEGA_HIGH_PRESSURE_STEAM_TURBINE = registerMetaTileEntity(3072, new MetaTileEntityMegaTurbine(gtqtcoreId("mega_high_pressure_steam_turbine"), GTQTcoreRecipeMaps.HIGH_PRESSURE_STEAM_TURBINE_RECIPES, EV, MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TITANIUM_TURBINE_CASING), MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TITANIUM_GEARBOX), Textures.STABLE_TITANIUM_CASING, false, GTQTTextures.MEGA_TURBINE_OVERLAY));
        MEGA_SUPERCRITICAL_STEAM_TURBINE = registerMetaTileEntity(3073, new MetaTileEntityMegaTurbine(gtqtcoreId("mega_supercritical_steam_turbine"), GTQTcoreRecipeMaps.SUPERCRITICAL_STEAM_TURBINE_RECIPES, LuV, GTQTMetaBlocks.blockMultiblockCasing1.getState(BlockMultiblockCasing1.CasingType.MaragingSteel250), MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TUNGSTENSTEEL_GEARBOX), GTQTTextures.MaragingSteel250, false, GTQTTextures.MEGA_TURBINE_OVERLAY));


        KINETIC_ENERGY_BATTERY = registerMetaTileEntity(3074, new MetaTileEntityKineticEnergyBattery(gtqtcoreId("kinetic_energy_battery")));
        //GRAY_ELECTRIC_POWER_BANK = registerMetaTileEntity(3071, new MetaTileEntityGrayElectricPowerBank(gtqtcoreId("gray_electric_power_bank")));
        LARGE_FUEL_TURBINE = registerMetaTileEntity(3075, new MetaTileEntityLargeTurbine(gtqtcoreId("large_turbine.fuel_cell"), GTQTcoreRecipeMaps.FUEL_CELL, 4, GTQTMetaBlocks.blockMultiblockCasing1.getState(HastelloyX78), MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TUNGSTENSTEEL_GEARBOX), GTQTTextures.HastelloyX78, true, Textures.LARGE_GAS_TURBINE_OVERLAY));

        LARGE_BIOMASS_GENERATOR = registerMetaTileEntity(3076, new MetaTileEntityLargeBiomassGenerator(gtqtcoreId("large_biomass_generator")));

        TIDE_CONTROL = registerMetaTileEntity(3077, new MetaTileEntityTideControl(gtqtcoreId("tide_control")));
        TIDE_UNIT = registerMetaTileEntity(3078, new MetaTileEntityTideUnit(gtqtcoreId("tide_unit")));

        WIND_GENERATOR = registerMetaTileEntity(3079, new MetaTileEntityWindGenerator(gtqtcoreId("wind_generator")));
        //激光
        LASER_BLAST_FURNACE = registerMetaTileEntity(3080, new MetaTileEntityLaserBlastFurnace(gtqtcoreId("laser_blast_furnace")));
        LASER_ALLOY_FURNACE = registerMetaTileEntity(3081, new MetaTileEntityLaserAlloyFurnace(gtqtcoreId("laser_alloy_furnace")));
        LASER_CUTTER = registerMetaTileEntity(3082, new MetaTileEntityLaserCutter(gtqtcoreId("laser_cutter")));
        LASER_ENV = registerMetaTileEntity(3083, new MetaTileEntityLaserENV(gtqtcoreId("laser_env")));
        LASER_CHEMICAL_PLANT = registerMetaTileEntity(3084, new MetaTileEntityLaserChemicalPlant(gtqtcoreId("laser_chemical_plant")));

        LASER_FBT =registerMetaTileEntity(3085, new MetaTileEntityLaserBooster(gtqtcoreId("laser_fbt")));
        LASER_FBC = registerMetaTileEntity(3086, new MetaTileEntityLaserFusionCore(gtqtcoreId("laser_fbc")));

        LASER_EMITTER = registerMetaTileEntity(3090, new MetaTileEntityLaserEmitter(gtqtcoreId("laser_emitter")));
        LASER_TRANSLATION = registerMetaTileEntity(3091, new MetaTileEntityLaserTranslation(gtqtcoreId("laser_translation")));
        LASER_SWITCH = registerMetaTileEntity(3092, new MetaTileEntitySwitch(gtqtcoreId("laser_switch")));

        SBPRC = registerMetaTileEntity(3097, new MetaTileEntitySBPRC(gtqtcoreId("sbprc")));
        SBPRI = registerMetaTileEntity(3098, new MetaTileEntitySBPRI(gtqtcoreId("sbpri")));
        SBPRO = registerMetaTileEntity(3099, new MetaTileEntitySBPRO(gtqtcoreId("sbpro")));
        //早期设备
        PRIMITIVE_TREE_FARMER = registerMetaTileEntity(3100, new MetaTileEntityPrimitiveTreeFarmer(gtqtcoreId("primitive_tree_farmer")));
        SAW_MILL = registerMetaTileEntity(3101, new MetaTileEntitySawMill(gtqtcoreId("saw_mill")));
        PRIMITIVE_ROASTER = registerMetaTileEntity(3102, new MetaTileEntityPrimitiveRoaster(gtqtcoreId("primitive_roaster")));

        HEAT_FURNACE = registerMetaTileEntity(3107, new MetaTileEntityHeatFurnace(gtqtcoreId("heat_furnace")));

        PRIMITIVE_REACTOR = registerMetaTileEntity(3108, new MetaTileEntityPrimitiveReactor(gtqtcoreId("primitive_reactor")));

        //多核心
        INDUSTRIAL_MACERATOR = registerMetaTileEntity(3120, new MetaTileEntityIndustrialMacerator(gtqtcoreId("industrial_crusher")));
        INDUSTRIAL_CENTRIFUGE = registerMetaTileEntity(3121, new MetaTileEntityIndustrialCentrifuge(gtqtcoreId("industrial_centrifuge")));
        INDUSTRIAL_MIX = registerMetaTileEntity(3122, new MetaTileEntityIndustrialMix(gtqtcoreId("industrial_mix")));
        INDUSTRIAL_WASHER = registerMetaTileEntity(3123, new MetaTileEntityIndustrialWasher(gtqtcoreId("industrial_washer")));
        INDUSTRIAL_HAMMER = registerMetaTileEntity(3124, new MetaTileEntityIndustrialHammer(gtqtcoreId("industrial_hammer")));
        INDUSTRIAL_FURNACE = registerMetaTileEntity(3125, new MetaTileEntityIndustrialFurnace(gtqtcoreId("industrial_furnace")));

        CORE_MACERATOR = registerMetaTileEntity(3126, new MetaTileEntityCoreMacerator(gtqtcoreId("core_crusher")));
        CORE_CENTRIFUGE = registerMetaTileEntity(3127, new MetaTileEntityCoreCentrifuge(gtqtcoreId("core_centrifuge")));
        CORE_MIX = registerMetaTileEntity(3128, new MetaTileEntityCoreMix(gtqtcoreId("core_mix")));
        CORE_WASHER = registerMetaTileEntity(3129, new MetaTileEntityCoreWasher(gtqtcoreId("core_washer")));
        CORE_HAMMER = registerMetaTileEntity(3130, new MetaTileEntityCoreHammer(gtqtcoreId("core_hammer")));
        CORE_FURNACE = registerMetaTileEntity(3131, new MetaTileEntityCoreFurnace(gtqtcoreId("core_furnace")));

        INDUSTRIAL_BENDER = registerMetaTileEntity(3132, new MetaTileEntityIndustrialBender(gtqtcoreId("industrial_bender")));
        INDUSTRIAL_WIREMILL = registerMetaTileEntity(3133, new MetaTileEntityIndustrialWireMill(gtqtcoreId("industrial_wiremill")));
        INDUSTRIAL_EXTRUDER = registerMetaTileEntity(3134, new MetaTileEntityIndustrialExtruder(gtqtcoreId("industrial_extruder")));
        INDUSTRIAL_ASSEMBLY_LINE = registerMetaTileEntity(3135, new MetaTileEntityIndustrialAssemblyLine(gtqtcoreId("industrial_assembly_line")));
        INDUSTRIAL_CHEMICAL_REACTOR = registerMetaTileEntity(3136, new MetaTileEntityIndustrialChemicalReactor(gtqtcoreId("industrial_chemical_reactor")));
        //正常设备
        DISSOLUTION_TANK = registerMetaTileEntity(3150, new MetaTileEntityDissolutionTank(gtqtcoreId("dissolution_tank")));
        BLAZING_BLAST_FURNACE = registerMetaTileEntity(3151, new MetaTileEntityBlazingBlastFurnace(gtqtcoreId("blazing_blast_furnace")));
        CHEMICAL_PLANT = registerMetaTileEntity(3152, new MetaTileEntityChemicalPlant(gtqtcoreId("chemical_plant")));
        INTEGRATED_MINING_DIVISION = registerMetaTileEntity(3153, new MetaTileEntityIntegratedMiningDivision(gtqtcoreId("integrated_mining_division")));
        QUANTUM_FORCE_TRANSFORMER = registerMetaTileEntity(3154, new MetaTileEntityQuantumForceTransformer(gtqtcoreId("quantum_force_transform")));
        SMALL_HEAT_EXCHANGER = registerMetaTileEntity(3155, new MetaTileEntitySmallHeatExchanger(gtqtcoreId("small_heat_exchanger")));
        LARGE_HEAT_EXCHANGER = registerMetaTileEntity(3156, new MetaTileEntityLargeHeatExchanger(gtqtcoreId("large_heat_exchanger")));
        FUEL_REFINE_FACTORY = registerMetaTileEntity(3157, new MetaTileEntityFuelRefineFactory(gtqtcoreId("fuel_refine_factory")));
        FERMENTATION_TANK = registerMetaTileEntity(3158, new MetaTileEntityFermentationTank(gtqtcoreId("fermentation_tank")));
        DIGESTER = registerMetaTileEntity(3159, new MetaTileEntityDigester(gtqtcoreId("digester")));
        SEPTIC_TANK = registerMetaTileEntity(3160, new MetaTileEntitySepticTank(gtqtcoreId("septic_tank")));
        PCB_FACTORY = registerMetaTileEntity(3161, new MetaTileEntityPCBFactory(gtqtcoreId("pcb_factory")));
        INDUSTRIAL_FISHING_POND = registerMetaTileEntity(3162, new MetaTileEntityIndustrialFishingPond(gtqtcoreId("IndustrialFishingPond")));
        ISA_MILL = registerMetaTileEntity(3163, new MetaTileEntityIsaMill(gtqtcoreId("isa_mill")));
        FLOTATION_FACTORY = registerMetaTileEntity(3164, new MetaTileEntityFlotationFactory(gtqtcoreId("flotation_factory")));
        VACUUM_DRYING_FURNACE = registerMetaTileEntity(3165, new MetaTileEntityVacuumDryingFurnace(gtqtcoreId("vacuum_drying_furnace")));
        ALGAE_FARM = registerMetaTileEntity(3166, new MetaTileEntityAlgaeFarm(gtqtcoreId("algae_farm")));
        ELECTROBATH = registerMetaTileEntity(3167, new MetaTileEntityElectronBath(gtqtcoreId("electrobath")));
        SALT_FLIED = registerMetaTileEntity(3168, new MetaTileEntitySaltField(gtqtcoreId("salt_flied")));
        PYROLYSIS_TOWER = registerMetaTileEntity(3169, new MetaTileEntityPyrolysisTower(gtqtcoreId("pyrolysis_tower")));
        PRECISE_ASSEMBLER = registerMetaTileEntity(3170, new MetaTileEntityPreciseAssembler(gtqtcoreId("precise_assembler")));
        PARTICLE_ACCELERATOR = registerMetaTileEntity(3171, new MetaTileEntityParticleAccelerator(gtqtcoreId("particle_accelerator")));
        LARGE_CHEMICAL_REACTOR = registerMetaTileEntity(3172, new MetaTileEntityLargeChemicalReactor(gtqtcoreId("large_chemical_reactor")));
        BIOLOGICAL_REACTION = registerMetaTileEntity(3173, new MetaTileEntityBiologicalReaction(gtqtcoreId(("biological_reaction"))));
        OIL_POOL = registerMetaTileEntity(3174, new MetaTileEntityOilPool(gtqtcoreId(("oil_pool"))));
        MINING_DRILL = registerMetaTileEntity(3175, new MetaTileEntityMiningDrill(gtqtcoreId(("mining_drill"))));
        DISTILLATION_KETTLE = registerMetaTileEntity(3176, new MetaTileEntityDistillationKettle(gtqtcoreId(("distillation_kettle"))));
        OCEAN_PUMPER = registerMetaTileEntity(3177, new MetaTileEntityOceanPumper(gtqtcoreId("ocean_pumper")));
        HEAT_CHANGER = registerMetaTileEntity(3178, new MetaTileEntityHeatExchanger(gtqtcoreId("heat_exchanger")));
        LASER_ENGRAVING = registerMetaTileEntity(3179, new MetaTileEntityLaserEngraving(gtqtcoreId("laser_engraving")));
        STEPPER = registerMetaTileEntity(3180, new MetaTileEntityStepper(gtqtcoreId("stepper")));
        INDUSTRIAL_REFINER = registerMetaTileEntity(3181, new MetaTileEntityIndustrialRefiner(gtqtcoreId("industrial_refiner")));
        CLARIFIER = registerMetaTileEntity(3182, new MetaTileEntityClarifier(gtqtcoreId("clarifier")));
        ELE_OIL = registerMetaTileEntity(3183, new MetaTileEntityElectronOil(gtqtcoreId("ele_oil")));
        //
        THREE_DIM_PRINT = registerMetaTileEntity(3186, new MetaTileEntityThreeDimPrinter(gtqtcoreId("three_dim_print")));
        NEUTRON_ACTIVATOR = registerMetaTileEntity(3187, new MetaTileEntityNeutronActivator(gtqtcoreId("neutron_activator")));
        MSF = registerMetaTileEntity(3188, new MetaTileEntityMSF(gtqtcoreId("msf")));
        INTEGRATED_ORE_PROCESSOR = registerMetaTileEntity(3189, new MetaTileEntityIntegratedOreProcessor(gtqtcoreId("integrated_ore_processor")));
        EXTREME_INDUSTRIAL_GREENHOUSE = registerMetaTileEntity(3192, new MetaTileEntityExtremeIndustrialGreenhouse(gtqtcoreId("extreme_industrial_greenhouse")));
        ENZYMES_REACTOR = registerMetaTileEntity(3193, new MetaTileEntityEnzymesReaction(gtqtcoreId("enzymes_reactor")));
        BIO_CENTRIFUGE = registerMetaTileEntity(3194, new MetaTileEntityBioCentrifuge(gtqtcoreId("bio_centrifuge")));
        CRYOGENIC_FREEZER = registerMetaTileEntity(3195, new MetaTileEntityCryogenicFreezer(gtqtcoreId("cryogenic_freezer")));
        NANO_COATING = registerMetaTileEntity(3196, new MetaTileEntityNanoCoating(gtqtcoreId("nano_coating")));
        POWER_SUPPLY = registerMetaTileEntity(3197, new MetaTileEntityPowerSupply(gtqtcoreId("power_supply")));
        FROTH_FLOTATION_TANK = registerMetaTileEntity(3198, new MetaTileEntityFrothFlotationTank(gtqtcoreId("froth_flotation_tank")));
        MICROWAVE_ENERGY_RECEIVER_CONTROL = registerMetaTileEntity(3202, new MetaTileEntityMicrowaveEnergyReceiverControl(gtqtcoreId("microwave_energy_receiver_control")));
        //MEGA_CLEANROOM = registerMetaTileEntity(3203, new MetaTileEntityMegaCleanroom(gtqtcoreId("mega_cleanroom")));
        SEISMIC_DETECTOR = registerMetaTileEntity(3204, new MetaTileEntitySeismicDetector(gtqtcoreId("seismic_detector")));
        GRAVITY_SEPARATOR = registerMetaTileEntity(3205, new MetaTileEntityGravitySeparator(gtqtcoreId("gravity_separator")));
        // COKING_TOWER = registerMetaTileEntity(3206, new MetaTileEntityCokingTower(gtqtcoreId("coking_tower")));
        SMSF = registerMetaTileEntity(3207, new MetaTileEntitySMSF(gtqtcoreId("smsf")));
        ADV_ARC_FURNACE = registerMetaTileEntity(3209, new MetaTileEntityAdvancedArcFurnace(gtqtcoreId("adv_arc_furnace")));
        NEUTRAL_NETWORK_NEXUS = registerMetaTileEntity(3210, new MetaTileEntityNeutralNetworkNexus(gtqtcoreId("neutral_network_nexus")));
        BLAZING_CZ_PULLER = registerMetaTileEntity(3211, new MetaTileEntityBlazingCZPuller(gtqtcoreId("blazing_cz_puller")));
        LARGE_CIRCUIT_ASSEMBLY_LINE = registerMetaTileEntity(3212, new MetaTileEntityLargeCircuitAssemblyLine(gtqtcoreId("large_circuit_assembly_line")));
        PHOTOLITHOGRAPHY_FACTORY = registerMetaTileEntity(3213, new MetaTileEntityPhotolithographyFactory(gtqtcoreId("photolithography_factory")));
        DANGOTE_DISTILLERY = registerMetaTileEntity(3214, new MetaTileEntityDangoteDistillery(gtqtcoreId("dangote_distillery")));
        EVAPORATION_POOL = registerMetaTileEntity(3215, new MetaTileEntityEvaporationPool(gtqtcoreId("evaporation_pool")));
        ELECTRON_MICROSCOPE = registerMetaTileEntity(3216, new MetaTileEntityElectronMicroscope(gtqtcoreId("electron_microscope")));
        GENE_MUTAGENESIS = registerMetaTileEntity(3217, new MetaTileEntityGeneMutagenesis(gtqtcoreId("gene_mutagenesis")));
        PARTICLE_AGGREGATOR = registerMetaTileEntity(3218, new MetaTileEntityParticleAggregator(gtqtcoreId("particle_aggregator")));
        INDUSTRIAL_INDUCTION_FURNACE = registerMetaTileEntity(3219, new MetaTileEntityIndustrialInductionFurnace(gtqtcoreId("industrial_induction_furnace")));
        ENERGY_SUBSTATION = registerMetaTileEntity(3220, new MetaTileEntityEnergySubstation(gtqtcoreId("energy_substation")));
        NAQUADAH_FUEL_FACTORY = registerMetaTileEntity(3221, new MetaTileEntityNaquadahFuelFactory(gtqtcoreId("naquadah_fuel_factory")));
        PLASMA_CONDENSER = registerMetaTileEntity(3222, new MetaTileEntityPlasmaCondenser(gtqtcoreId("plasma_condenser")));
        HEAT_HATCH_EXCHANGE = registerMetaTileEntity(3223, new MetaTileEntityHeatHatchExchange(gtqtcoreId("heat_hatch_exchange")));
        LARGE_ELEMENT_DUPLICATOR = registerMetaTileEntity(3224, new MetaTileEntityLargeElementDuplicator(gtqtcoreId("large_element_duplicator")));
        LARGE_UU_PRODUCTER = registerMetaTileEntity(3225, new MetaTileEntityLargeUUProducter(gtqtcoreId("large_uu_producter")));
        //重写设备
        DISTILLATION_TOWER = registerMetaTileEntity(3250, new MetaTileEntityDistillationTower(gtqtcoreId("distillation_tower")));
        CRACKER = registerMetaTileEntity(3251, new MetaTileEntityCrackingUnit(gtqtcoreId("cracker")));
        PYROLYSE_OVEN = registerMetaTileEntity(3252, new MetaTileEntityPyrolyseOven(gtqtcoreId("pyrolyse_oven")));
        VACUUM_FREEZER = registerMetaTileEntity(3253, new MetaTileEntityVacuumFreezer(gtqtcoreId("vacuum_freezer")));

        ASSEMBLY_LINE = registerMetaTileEntity(3254, new MetaTileEntityAssemblyLine(gtqtcoreId("assembly_line")));
        LARGE_ORE_WASHER = registerMetaTileEntity(3255, new MetaTileEntityLargeOreWasher(gtqtcoreId("large_ore_washer")));
        LARGE_THERMAL_CENTRIFUGE = registerMetaTileEntity(3256, new MetaTileEntityLargeThermalCentrifuge(gtqtcoreId("large_thermal_centrifuge")));
        LARGE_GRIND = registerMetaTileEntity(3257, new MetaTileEntityLargeGrind(gtqtcoreId("large_grind")));
        LARGE_FORGING = registerMetaTileEntity(3258, new MetaTileEntityLargeForging(gtqtcoreId("large_forging")));
        LARGE_EXTRACTOR = registerMetaTileEntity(3259, new MetaTileEntityLargeExtractor(gtqtcoreId("large_extractor")));
        LARGE_MIXER = registerMetaTileEntity(3260, new MetaTileEntityLargeMixer(gtqtcoreId("large_mixer")));
        LARGE_DESULPHURIZATION = registerMetaTileEntity(3261, new MetaTileEntityLargeDesulphurization(gtqtcoreId("large_desulphurization")));
        LARGE_FLUIDIZED_BED = registerMetaTileEntity(3262, new MetaTileEntityLargeFluidizedBed(gtqtcoreId("large_fluidized_bed")));

        LV_GENERATOR_ARRAY= registerMetaTileEntity(3280, new MetaTileEntityGeneratorArray(gtqtcoreId("lv_generator_array"),1));
        MV_GENERATOR_ARRAY= registerMetaTileEntity(3281, new MetaTileEntityGeneratorArray(gtqtcoreId("mv_generator_array"),2));
        HV_GENERATOR_ARRAY= registerMetaTileEntity(3282, new MetaTileEntityGeneratorArray(gtqtcoreId("hv_generator_array"),3));
        EV_GENERATOR_ARRAY= registerMetaTileEntity(3283, new MetaTileEntityGeneratorArray(gtqtcoreId("ev_generator_array"),4));
        IV_GENERATOR_ARRAY= registerMetaTileEntity(3284, new MetaTileEntityGeneratorArray(gtqtcoreId("iv_generator_array"),5));

        LV_PROCESSING_ARRAY = registerMetaTileEntity(3287, new MetaTileEntityProcessingArray(gtqtcoreId("lv_processing_array"), 1));
        MV_PROCESSING_ARRAY = registerMetaTileEntity(3288, new MetaTileEntityProcessingArray(gtqtcoreId("mv_processing_array"), 2));
        HV_PROCESSING_ARRAY = registerMetaTileEntity(3289, new MetaTileEntityProcessingArray(gtqtcoreId("hv_processing_array"), 3));
        //巨型设备
        HUGE_FUSION_REACTOR[0] = registerMetaTileEntity(3290, new MetaTileEntityHugeFusionReactor(gtqtcoreId("fusion_reactor.uhv"), UHV));
        HUGE_FUSION_REACTOR[1] = registerMetaTileEntity(3291, new MetaTileEntityHugeFusionReactor(gtqtcoreId("fusion_reactor.uev"), UEV));
        HUGE_FUSION_REACTOR[2] = registerMetaTileEntity(3292, new MetaTileEntityHugeFusionReactor(gtqtcoreId("fusion_reactor.uiv"), UIV));

        COMPRESSED_FUSION_REACTOR[0] = registerMetaTileEntity(3293, new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor.luv"), LuV, MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_CASING), MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL), MetaBlocks.FRAMES.get(Materials.Naquadria).getBlock(Materials.Naquadria)));
        COMPRESSED_FUSION_REACTOR[1] = registerMetaTileEntity(3294, new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor.zpm"), ZPM, MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_CASING_MK2), MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_COIL), MetaBlocks.FRAMES.get(Materials.Tritanium).getBlock(Materials.Tritanium)));
        COMPRESSED_FUSION_REACTOR[2] = registerMetaTileEntity(3295, new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor.uv"), UV, MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_CASING_MK3), MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_COIL), MetaBlocks.FRAMES.get(Neutronium).getBlock(Neutronium)));
        if (GTQTCoreConfig.MachineSwitch.LastSwitch)
            COMPRESSED_FUSION_REACTOR[3] = registerMetaTileEntity(3296, new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor.uhv"), UHV, GTQTMetaBlocks.blockCompressedFusionReactor.getState(CASING_FUSION_MKIV), GTQTMetaBlocks.blockCompressedFusionReactor.getState(FUSION_COIL_MKII), MetaBlocks.FRAMES.get(Infinity).getBlock(Infinity)));
        if (GTQTCoreConfig.MachineSwitch.LastSwitch)
            COMPRESSED_FUSION_REACTOR[4] = registerMetaTileEntity(3297, new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor.uev"), UEV, GTQTMetaBlocks.blockCompressedFusionReactor.getState(CASING_FUSION_MKV), GTQTMetaBlocks.blockCompressedFusionReactor.getState(FUSION_COIL_MKIII), MetaBlocks.FRAMES.get(MagnetoHydrodynamicallyConstrainedStarMatter).getBlock(MagnetoHydrodynamicallyConstrainedStarMatter)));
        if (GTQTCoreConfig.MachineSwitch.LastSwitch)
            COMPRESSED_FUSION_REACTOR[5] = registerMetaTileEntity(3298, new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor.uiv"), UIV, GTQTMetaBlocks.blockCompressedFusionReactor.getState(CASING_FUSION_MKVI), GTQTMetaBlocks.blockCompressedFusionReactor.getState(FUSION_COIL_MKIV), MetaBlocks.FRAMES.get(Eternity).getBlock(Eternity)));

        ADVANCED_ASSEMBLY_LINE = registerMetaTileEntity(3305, new MetaTileEntityAdvancedAssemblyLine(gtqtcoreId("advanced_assembly_line")));
        COMPONENT_ASSEMBLY_LINE = registerMetaTileEntity(3306, new MetaTileEntityComponentAssemblyLine(gtqtcoreId("component_assembly_line")));

        LARGE_PROCESSING_FACTORY = registerMetaTileEntity(3307, new MetaTileEntityLargeProcessingFactory(gtqtcoreId("large_processing_factory")));

        HUGE_ALLOY_BLAST_FURANCE = registerMetaTileEntity(3312, new MetaTileEntityHugeAlloyBlastSmelter(gtqtcoreId("huge_alloy_blast_smelter")));
        HUGE_BLAST_FURANCE = registerMetaTileEntity(3313, new MetaTileEntityHugeBlastFurnace(gtqtcoreId("huge_blast_furnace")));
        HUGE_CRACKING_UNIT = registerMetaTileEntity(3314, new MetaTileEntityHugeCrackingUnit(gtqtcoreId("huge_cracking_unit")));
        HUGE_CHEMICAL_REACTOR = registerMetaTileEntity(3315, new MetaTileEntityHugeChemicalReactor(gtqtcoreId("huge_chemical_reactor")));

        ANTIMATTER_FORGE = registerMetaTileEntity(3330, new MetaTileEntityAntimatterForge(gtqtcoreId("antimatter_forge")));
        ANTIMATTER_GENERATOR = registerMetaTileEntity(3331, new MetaTileEntityAntimatterGenerator(gtqtcoreId("antimatter_generator")));


        //star

        if (GTQTCoreConfig.MachineSwitch.LastSwitch)
            DIMENSIONALLY_TRANSCENDENT_PLASMA_FORGE = registerMetaTileEntity(3350, new MetaTileEntityDimensionallyTranscendentPlasmaForge(gtqtcoreId("dimensionally_transcendent_plasma_forge")));
        if (GTQTCoreConfig.MachineSwitch.LastSwitch)
            DIMENSIONAL_MIXER = registerMetaTileEntity(3351, new MetaTileEntityDimensionalMixer(gtqtcoreId("dimensional_mixer")));
        if (GTQTCoreConfig.MachineSwitch.LastSwitch)
            DIMENSIONAL_PLASMA_FURNACE = registerMetaTileEntity(3352, new MetaTileEntityDimensionallyPlasmFurnace(gtqtcoreId("dimensional_plasma_furnace")));
        if (GTQTCoreConfig.MachineSwitch.LastSwitch)
            DIMENSIONAL_BIOMIMETIC_FACTORY = registerMetaTileEntity(3353, new MetaTileEntityDimensionallyBiomimeticFactory(gtqtcoreId("dimensional_biomimetic_factory")));
        if (GTQTCoreConfig.MachineSwitch.EndGameSwitch)
            NICOLL_DYSON_BEAMER = registerMetaTileEntity(3354, new MetaTileEntityNicollDysonBeamer(gtqtcoreId("nicoll_dyson_beamer")));
        if (GTQTCoreConfig.MachineSwitch.EndGameSwitch)
            SUPRACHRONAL_NEUTRONIUM_FORGE = registerMetaTileEntity(3355, new MetaTileEntitySuprachronalNeutroniumForge(gtqtcoreId("suprachronal_neutronium_forge")));

        //资源产出
        GAS_COLLECTOR = registerMetaTileEntity(3406, new MetaTileEntityGasCollector(gtqtcoreId("gas_collector")));
        INDUSTRY_WATER_PUMP = registerMetaTileEntity(3407, new MetaTileEntityIndustryWaterPump(gtqtcoreId("industry_water_pump")));

        EXTREME_LARGE_MINER = registerMetaTileEntity(3420, new MetaTileEntityAdvancedLargeMiner(gtqtcoreId("large_miner.zpm"), 7, 1, 9, 7, Materials.Naquadah, 64));
        ULTIMATE_LARGE_MINER = registerMetaTileEntity(3421, new MetaTileEntityAdvancedLargeMiner(gtqtcoreId("large_miner.uv"), 8, 1, 18, 8, Orichalcum, 128));
        INFINITY_LARGE_MINER = registerMetaTileEntity(3422, new MetaTileEntityAdvancedLargeMiner(gtqtcoreId("large_miner.uhv"), 9, 1, 36, 9, Adamantium, 256));

        ADVANCED_FLUID_DRILL_RIG = registerMetaTileEntity(3423, new MetaTileEntityAdvancedFluidDrill(gtqtcoreId("fluid_drill_rig.iv"), 5));
        EXTREME_FLUID_DRILL_RIG = registerMetaTileEntity(3424, new MetaTileEntityAdvancedFluidDrill(gtqtcoreId("fluid_drill_rig.luv"), 6));
        ULTIMATE_FLUID_DRILL_RIG = registerMetaTileEntity(3425, new MetaTileEntityAdvancedFluidDrill(gtqtcoreId("fluid_drill_rig.zpm"), 7));
        INFINITY_FLUID_DRILL_RIG = registerMetaTileEntity(3426, new MetaTileEntityAdvancedFluidDrill(gtqtcoreId("fluid_drill_rig.uv"), 8));

        VOID_MINER[0] = registerMetaTileEntity(3427, new MetaTileEntityVoidMiner(gtqtcoreId("void_miner.luv"), LuV, 9000));
        VOID_MINER[1] = registerMetaTileEntity(3428, new MetaTileEntityVoidMiner(gtqtcoreId("void_miner.zpm"), ZPM, 16000));
        VOID_MINER[2] = registerMetaTileEntity(3429, new MetaTileEntityVoidMiner(gtqtcoreId("void_miner.uv"), UV, 40000));

        //blocksResearchSystem
        KQCC = registerMetaTileEntity(3450, new MetaTileEntityResearchSystemControlCenter(gtqtcoreId("kqcc")));
        KQNS = registerMetaTileEntity(3451, new MetaTileEntityResearchSystemNetworkSwitch(gtqtcoreId("kqns")));
        ADV_KQCC = registerMetaTileEntity(3452, new MetaTileEntityAdvanceKQCC(gtqtcoreId("adv_kqcc")));
        MINI_DATE_BANK = registerMetaTileEntity(3453, new MetaTileEntityMiniDataBank(gtqtcoreId("mini_date_bank")));
        ADV_NETWORK_SWITCH = registerMetaTileEntity(3464, new MetaTileEntityAdvanceNetworkSwitch(gtqtcoreId("adv_network_switch")));
        ADV_DATE_BANK = registerMetaTileEntity(3455, new MetaTileEntityAdvanceDataBank(gtqtcoreId("adv_date_bank")));
        RESEARCH_SYSTEM_NETWORK = registerMetaTileEntity(3470, new MetaTileEntityResearchSystemNetWork(gtqtcoreId("research_system_network")));
        //GCYS
        INDUSTRIAL_DRILL = registerMetaTileEntity(3500, new MetaTileEntityIndustrialDrill(gtqtcoreId("industrial_drill")));
        LARGE_CATALYTIC_REFORMER = registerMetaTileEntity(3501, new MetaTileEntityCatalyticReformer(gtqtcoreId("catalytic_reformer")));
        LARGE_SONICATOR = registerMetaTileEntity(3502, new MetaTileEntitySonicator(gtqtcoreId("sonicator")));
        LARGE_HYDRAULIC_FRACKER = registerMetaTileEntity(3503, new MetaTileEntityFracker(gtqtcoreId("fracker"), GTValues.ZPM));
        LARGE_NANOSCALE_FABRICATOR = registerMetaTileEntity(3504, new MetaTileEntityNanoscaleFabricator(gtqtcoreId("nanoscale_fabricator")));
        LARGE_ROASTER = registerMetaTileEntity(3505, new MetaTileEntityIndustrialRoaster(gtqtcoreId("roaster")));
        LARGE_CRYSTALLIZATION_CRUCIBLE = registerMetaTileEntity(3506, new MetaTileEntityCrystallizationCrucible(gtqtcoreId("crystallization_crucible")));
        LARGE_CVD_UNIT = registerMetaTileEntity(3507, new MetaTileEntityCVDUnit(gtqtcoreId("cvd_unit")));
        LARGE_BURNER_REACTOR = registerMetaTileEntity(3508, new MetaTileEntityBurnerReactor(gtqtcoreId("burner_reactor")));
        LARGE_CRYOGENIC_REACTOR = registerMetaTileEntity(3509, new MetaTileEntityCryoReactor(gtqtcoreId("cryogenic_reactor")));
        LARGE_ION_IMPLANTATOR = registerMetaTileEntity(3510, new MetaTileEntityIonImplanter(gtqtcoreId("ion_implantator")));
        LARGE_CZ_PULLER = registerMetaTileEntity(3511, new MetaTileEntityCZPuller(gtqtcoreId("cz_puller")));
        LARGE_EX_CVD = registerMetaTileEntity(3512, new MetaTileEntityExtremesCVD(gtqtcoreId("ex_cvd")));
        LARGE_CHEMICAL_FACTORY = registerMetaTileEntity(3513, new MetaTileEntityChemicalReactor(gtqtcoreId("chemical_factory")));
        LARGE_ULTRAVIOLET_LAMP = registerMetaTileEntity(3514, new MetaTileEntityUltravioletLamp(gtqtcoreId("ultraviolet_lamp")));
        LARGE_MIX = registerMetaTileEntity(3516, new MetaTileEntityMixer(gtqtcoreId("large_mix")));
        LARGE_PRESSURE_FACTORY = registerMetaTileEntity(3517, new MetaTileEntityPressureFactory(gtqtcoreId("large_pressure_factory")));

        //pressure
        STEAM_EJECTOR[0] = registerMetaTileEntity(3600, new MetaTileEntitySteamEjector(gtqtcoreId("steam_ejector.bronze"),false));
        STEAM_EJECTOR[1] = registerMetaTileEntity(3602, new MetaTileEntitySteamEjector(gtqtcoreId("steam_ejector.steel"),true));
        CREATIVE_PRESSURE = registerMetaTileEntity(3603, new MetaTileEntityCreativePressurePump(gtqtcoreId("infinite_pressure_pump")));
        PRESSURE_TANK[0]= registerMetaTileEntity(3604, new MetaTileEntityPressureTank(gtqtcoreId("pressure_tank.i"),1));
        PRESSURE_TANK[1]= registerMetaTileEntity(3605, new MetaTileEntityPressureTank(gtqtcoreId("pressure_tank.ii"),2));
        PRESSURE_TANK[2]= registerMetaTileEntity(3606, new MetaTileEntityPressureTank(gtqtcoreId("pressure_tank.iii"),3));

        SUBSONIC_AXIAL_COMPRESSOR = registerMetaTileEntity(3620, new MetaTileEntityAxialCompressor(gtqtcoreId("axial_compressor.subsonic"), GTValues.EV, increaseDetailP[6], getPressureChange(4,true)));
        SUPERSONIC_AXIAL_COMPRESSOR = registerMetaTileEntity(3621, new MetaTileEntityAxialCompressor(gtqtcoreId("axial_compressor.supersonic"), GTValues.LuV, increaseDetailP[8], getPressureChange(6,true)));
        LOW_POWER_TURBOMOLECULAR_PUMP = registerMetaTileEntity(3622, new MetaTileEntityTurbomolecularPump(gtqtcoreId("turbomolecular_pump.low_power"), GTValues.EV, decreaseDetailP[6], getPressureChange(4,true)));
        HIGH_POWER_TURBOMOLECULAR_PUMP = registerMetaTileEntity(3623, new MetaTileEntityTurbomolecularPump(gtqtcoreId("turbomolecular_pump.high_power"), GTValues.LuV, decreaseDetailP[8], getPressureChange(6,true)));

        for (int i = 0; i < 9; i++) {
            int id = 3630 + i;
            String name = String.format("pressure_pump.%s", GTValues.VN[i + 1]);
            PRESSURE_PUMP[i] = registerMetaTileEntity(id, new MetaTileEntityPressureBooster(gtqtcoreId(name),i + 1,false));
        }
        for (int i = 0; i < 9; i++) {
            int id = 3640 + i;
            String name = String.format("pressure_compressor.%s", GTValues.VN[i + 1]);
            PRESSURE_COMPRESSOR[i] = registerMetaTileEntity(id, new MetaTileEntityPressureBooster(gtqtcoreId(name),i + 1,true));
        }
        GAS_TANK[0]= registerMetaTileEntity(3650, new MetaTileEntityGasTank(gtqtcoreId("gas_tank.bronze"),Materials.Bronze,0,5));
        GAS_TANK[1]= registerMetaTileEntity(3651, new MetaTileEntityGasTank(gtqtcoreId("gas_tank.steel"),Materials.Steel,1,10));
        GAS_TANK[2]= registerMetaTileEntity(3652, new MetaTileEntityGasTank(gtqtcoreId("gas_tank.aluminium"),Materials.Aluminium,2,15));
        GAS_TANK[3]= registerMetaTileEntity(3653, new MetaTileEntityGasTank(gtqtcoreId("gas_tank.stainlesssteel"), StainlessSteel,3,20));
        GAS_TANK[4]= registerMetaTileEntity(3654, new MetaTileEntityGasTank(gtqtcoreId("gas_tank.titanium"), Titanium,4,25));
        GAS_TANK[5]= registerMetaTileEntity(3655, new MetaTileEntityGasTank(gtqtcoreId("gas_tank.tungstensteel"), TungstenSteel,5,30));
        GAS_TANK[6]= registerMetaTileEntity(3656, new MetaTileEntityGasTank(gtqtcoreId("gas_tank.hssg"), HSSG,6,35));
        GAS_TANK[7]= registerMetaTileEntity(3657, new MetaTileEntityGasTank(gtqtcoreId("gas_tank.naquadah"), Naquadah,7,40));
        GAS_TANK[8]= registerMetaTileEntity(3658, new MetaTileEntityGasTank(gtqtcoreId("gas_tank.duranium"), Duranium,8,45));
        GAS_TANK[9]= registerMetaTileEntity(3659, new MetaTileEntityGasTank(gtqtcoreId("gas_tank.neutronium"), Neutronium,9,50));
        //Appeng 4000

        // ID 14001-14010: Drums
        INV_BRIDGE = registerMetaTileEntity(14460, new MetaTileEntityBridge(gtqtcoreId("bridge.inv"), cap -> cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, GTQTTextures.INV_BRIDGE, Materials.Steel));
        TANK_BRIDGE = registerMetaTileEntity(14461, new MetaTileEntityBridge(gtqtcoreId("bridge.tank"), cap -> cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, GTQTTextures.TANK_BRIDGE, Materials.Steel));
        INV_TANK_BRIDGE = registerMetaTileEntity(14462, new MetaTileEntityBridge(gtqtcoreId("bridge.inv_tank"), cap -> cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, GTQTTextures.INV_TANK_BRIDGE, Materials.Steel));
        UNIVERSAL_BRIDGE = registerMetaTileEntity(14463, new MetaTileEntityBridge(gtqtcoreId("bridge.universal"), cap -> true, GTQTTextures.UNIVERSAL_BRIDGE, Materials.Aluminium));

        INV_EXTENDER = registerMetaTileEntity(14465, new MetaTileEntityExtender(gtqtcoreId("extender.inv"), cap -> cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, GTQTTextures.INV_EXTENDER, Materials.Steel));
        TANK_EXTENDER = registerMetaTileEntity(14466, new MetaTileEntityExtender(gtqtcoreId("extender.tank"), cap -> cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, GTQTTextures.TANK_EXTENDER, Materials.Steel));
        INV_TANK_EXTENDER = registerMetaTileEntity(14467, new MetaTileEntityExtender(gtqtcoreId("extender.inv_tank"), cap -> cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, GTQTTextures.INV_TANK_EXTENDER, Materials.Steel));
        UNIVERSAL_EXTENDER = registerMetaTileEntity(14468, new MetaTileEntityExtender(gtqtcoreId("extender.universal"), cap -> true, GTQTTextures.UNIVERSAL_EXTENDER, Materials.Aluminium));

        PE_CAN = registerMetaTileEntity(14470, new MetaTileEntityPlasticCan(gtqtcoreId("drum.pe"), Materials.Polyethylene, 64_000));
        PTFE_CAN = registerMetaTileEntity(14471, new MetaTileEntityPlasticCan(gtqtcoreId("drum.ptfe"), Materials.Polytetrafluoroethylene, 128_000));
        ZYLON_CAN = registerMetaTileEntity(14472, new MetaTileEntityPlasticCan(gtqtcoreId("drum.zylon"), Zylon, 512_000));
        PBI_CAN = registerMetaTileEntity(14473, new MetaTileEntityPlasticCan(gtqtcoreId("drum.pbi"), Materials.Polybenzimidazole, 1024_000));
        KEVLAR_CAN= registerMetaTileEntity(14474, new MetaTileEntityPlasticCan(gtqtcoreId("drum.kevlar"), Kevlar, 2048_000));

        //小机器
        STEAM_VACUUM_CHAMBER[0] = registerMetaTileEntity(14500, new MetaTileEntitySteamVacuumChamber(gtqtcoreId("vacuum_chamber.bronze"), false));
        STEAM_VACUUM_CHAMBER[1] = registerMetaTileEntity(14501, new MetaTileEntitySteamVacuumChamber(gtqtcoreId("vacuum_chamber.steel"),true));
        registerSimpleSteamMetaTileEntity(STEAM_SPINNER, 14502, "spinner", GTQTcoreRecipeMaps.SPINNER_RECIPES, COMPRESS, GTQTTextures.SPINNER_OVERL, false);
        registerSimpleSteamMetaTileEntity(STEAM_ROASTER, 14504, "roaster", ROASTER_RECIPES, COMPRESS, ROASTER_OVERLAY, false);

        registerSimpleMetaTileEntity(SONICATOR, 14600, "sonicator", SONICATION_RECIPES, SONICATOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(ION_IMPLANTER, 14615, "ion_implanter", ION_IMPLANTATOR_RECIPES, ION_IMPLANTER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(CVD_UNIT, 14630, "cvd_unit", CVD_RECIPES, CVD_UNIT_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(FLOTATION_CELL, 14645, "flotation_cell", FLOTATION_RECIPES, FLOTATION_CELL_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(CATALYTIC_REFORMER, 14660, "catalytic_reformer", CATALYTIC_REFORMER_RECIPES, CATALYTIC_REFORMER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(ROASTER, 14675, "roaster", ROASTER_RECIPES, ROASTER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(CRYOGENIC_REACTOR, 14690, "cryogenic_reactor", CRYOGENIC_REACTOR_RECIPES, CRYOGENIC_REACTOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);

        for (int i = 0; i < 13; i++) {
            int id = 14705 + i;
            String name = String.format("burner_reactor.%s", GTValues.VN[i + 1]);
            BURNER_REACTOR[i] = registerMetaTileEntity(id, new MetaTileEntityPressureMachine(gtqtcoreId(name), BURNER_REACTOR_RECIPES,BURNER_REACTOR_OVERLAY,i + 1,true));
        }

        registerSimpleMetaTileEntity(SPINNER, 14720, "spinner", SPINNER_RECIPES, GTQTTextures.SPINNER_OVERL, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(POLYMERIZATION_TANK, 14735, "polymerization_tank", POLYMERIZATION_RECIPES, POLYMERIZATION_TANK_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(LOW_TEMP_ACTIVATOR, 14750, "low_temperature_activator", LOW_TEMP_ACTIVATOR_RECIPES, LOW_TEMP_ACTIVATOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(BIO_REACTOR, 14765, "bio_reactor", GTQTcoreRecipeMaps.BIOLOGICAL_REACTION_RECIPES, GTQTTextures.BIO_REACTOR_OVERLAY, true, GTQTUtil::gtqtId, (tier) -> 16000);
        registerSimpleMetaTileEntity(DEHYDRATOR, 14780, "dehydrator", GTQTcoreRecipeMaps.DRYER_RECIPES, Textures.SIFTER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(FLUID_EXTRACTOR, 14795, "fluid_extractor", GTQTcoreRecipeMaps.FLUID_EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(FLUID_CANNER, 14810, "fluid_canner", GTQTcoreRecipeMaps.FLUID_CANNER_RECIPES, Textures.CANNER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(COMPONENT_ASSEMBLER, 14825, "component_assembler", GTQTcoreRecipeMaps.COMPONENT_ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(LAMINATOR, 14840, "laminator", GTQTcoreRecipeMaps.LAMINATOR_RECIPES, GTQTTextures.LAMINATOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.largeTankSizeFunction);

        for (int i = 0; i < 13; i++) {
            int id = 14855 + i;
            String name = String.format("vacuum_chamber.%s", GTValues.VN[i + 1]);
            VACUUM_CHAMBER[i] = registerMetaTileEntity(id, new MetaTileEntityPressureMachine(gtqtcoreId(name), VACUUM_CHAMBER_RECIPES,Textures.GAS_COLLECTOR_OVERLAY,i + 1,true));
        }

        registerSimpleMetaTileEntity(ULTRAVIOLET_LAMP_CHAMBER, 14870, "ultraviolet_lamp_chamber", GTQTcoreRecipeMaps.ULTRAVIOLET_LAMP_CHAMBER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(PRESSURE_LAMINATOR, 14885, "pressure_laminator", GTQTcoreRecipeMaps.PRESSURE_LAMINATOR_RECIPES, GTQTTextures.LAMINATOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(UU_PRODUCTER, 14900, "uu_producter", GTQTcoreRecipeMaps.UU_RECIPES, GTQTTextures.UUPRODUCTER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(DUPLICATOR, 14915, "duplicator", GTQTcoreRecipeMaps.COPY_RECIPES, GTQTTextures.DUPLICATOR, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(RECYCLE, 14930, "recycle", GTQTcoreRecipeMaps.RECYCLE_RECIPE, Textures.MACERATOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.defaultTankSizeFunction);
        registerSimpleMetaTileEntity(POLISHER, 14945, "polisher", GTQTcoreRecipeMaps.POLISHER_RECIPES, GTQTTextures.POLISHER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(SIMULATOR, 14960, "simulator", GTQTcoreRecipeMaps.SIMULATOR_RECIPES, GTQTTextures.SIMULATOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);

        STEAM_LATEX_COLLECTOR[0] = registerMetaTileEntity(15090, new MetaTileEntitySteamLatexCollector(gtqtcoreId("latex_collector.bronze"), false));
        STEAM_LATEX_COLLECTOR[1] = registerMetaTileEntity(15091, new MetaTileEntitySteamLatexCollector(gtqtcoreId("latex_collector.steel"), true));
        LATEX_COLLECTOR[0] = registerMetaTileEntity(15092, new MetaTileEntityLatexCollector(gtqtcoreId("latex_collector.lv"), 1));
        LATEX_COLLECTOR[1] = registerMetaTileEntity(15093, new MetaTileEntityLatexCollector(gtqtcoreId("latex_collector.mv"), 2));
        LATEX_COLLECTOR[2] = registerMetaTileEntity(15094, new MetaTileEntityLatexCollector(gtqtcoreId("latex_collector.hv"), 3));
        LATEX_COLLECTOR[3] = registerMetaTileEntity(15095, new MetaTileEntityLatexCollector(gtqtcoreId("latex_collector.ev"), 4));
        PARTICLE_ACCELERATOR_IO[0] = registerMetaTileEntity(15096, new MetaTileEntityParticleAcceleratorIO(gtqtcoreId("particle_accelerator_io.lv"), GTQTcoreRecipeMaps.PAC_RECIPES, GTQTTextures.PARTICLE_ACCELERATOR, 1, true));
        PARTICLE_ACCELERATOR_IO[1] = registerMetaTileEntity(15097, new MetaTileEntityParticleAcceleratorIO(gtqtcoreId("particle_accelerator_io.mv"), GTQTcoreRecipeMaps.PAC_RECIPES, GTQTTextures.PARTICLE_ACCELERATOR, 2, true));
        PARTICLE_ACCELERATOR_IO[2] = registerMetaTileEntity(15098, new MetaTileEntityParticleAcceleratorIO(gtqtcoreId("particle_accelerator_io.hv"), GTQTcoreRecipeMaps.PAC_RECIPES, GTQTTextures.PARTICLE_ACCELERATOR, 3, true));
        PARTICLE_ACCELERATOR_IO[3] = registerMetaTileEntity(15099, new MetaTileEntityParticleAcceleratorIO(gtqtcoreId("particle_accelerator_io.ev"), GTQTcoreRecipeMaps.PAC_RECIPES, GTQTTextures.PARTICLE_ACCELERATOR, 4, true));

        //凿子
        AUTO_CHISEL[0] = registerMetaTileEntity(15100, new SimpleMachineMetaTileEntity(gtqtcoreId("auto_chisel.lv"), GTQTcoreRecipeMaps.AUTO_CHISEL_RECIPES, Textures.AUTOCLAVE_OVERLAY, 1, true, GTUtility.genericGeneratorTankSizeFunction));
        AUTO_CHISEL[1] = registerMetaTileEntity(15101, new SimpleMachineMetaTileEntity(gtqtcoreId("auto_chisel.mv"), GTQTcoreRecipeMaps.AUTO_CHISEL_RECIPES, Textures.AUTOCLAVE_OVERLAY, 2, true, GTUtility.genericGeneratorTankSizeFunction));
        AUTO_CHISEL[2] = registerMetaTileEntity(15102, new SimpleMachineMetaTileEntity(gtqtcoreId("auto_chisel.hv"), GTQTcoreRecipeMaps.AUTO_CHISEL_RECIPES, Textures.AUTOCLAVE_OVERLAY, 3, true, GTUtility.genericGeneratorTankSizeFunction));

        RUBBISH_BIN = registerMetaTileEntity(15104, new MetaTileEntityRubbishBin(gtqtcoreId("rubbish_bin")));
        FLUID_RUBBISH_BIN = registerMetaTileEntity(15105, new MetaTileEntityFluidRubbishBin(gtqtcoreId("fluid_rubbish_bin")));
        COMMON_RUBBISH_BIN = registerMetaTileEntity(15106, new MetaTileEntityCommonRubbishBin(gtqtcoreId("common_rubbish_bin")));

        //仓口
        for (int i = 1; i <= GTValues.V.length - 2; i++) {
            String tierName = GTValues.VN[i].toLowerCase();
            KQCC_COMPUTATION_HATCH_RECEIVER[i] = registerMetaTileEntity(15400 + i - 1, new MetaTileEntityKQCCComputationHatch(gtqtcoreId("kqcccomputation_hatch.receiver." + tierName), i, false));
            KQCC_COMPUTATION_HATCH_TRANSMITTER[i] = registerMetaTileEntity(15415 + i - 1, new MetaTileEntityKQCCComputationHatch(gtqtcoreId("kqcccomputation_hatch.transmitter." + tierName), i, true));
            MICROWAVE_ENERGY_RECEIVER[i] = registerMetaTileEntity(15430 + i - 1, new MetaTileEntityMicrowaveEnergyReceiver(gtqtcoreId("microwave_energy_receiver." + tierName), i));

            LASER_INPUT[i] = registerMetaTileEntity(15445 + i - 1, new MetaTileHighEnergyLaserHatch(gtqtcoreId("laser.input." + tierName), i, false));
            LASER_OUTPUT[i] = registerMetaTileEntity(15460 + i - 1, new MetaTileHighEnergyLaserHatch(gtqtcoreId("laser.output." + tierName), i, true));
        }
        LASER_BOOSTER[0] = registerMetaTileEntity(15475, new MetaTileLaserBooster(gtqtcoreId("laser_booster." + LuV), LuV));
        LASER_BOOSTER[1] = registerMetaTileEntity(15476, new MetaTileLaserBooster(gtqtcoreId("laser_booster." + ZPM), ZPM));
        LASER_BOOSTER[2] = registerMetaTileEntity(15477, new MetaTileLaserBooster(gtqtcoreId("laser_booster." + UV), UV));
        LASER_BOOSTER[3] = registerMetaTileEntity(15478, new MetaTileLaserBooster(gtqtcoreId("laser_booster." + UHV), UHV));
        LASER_BOOSTER[4] = registerMetaTileEntity(15479, new MetaTileLaserBooster(gtqtcoreId("laser_booster." + UIV), UIV));
        LASER_BOOSTER[5] = registerMetaTileEntity(15480, new MetaTileLaserBooster(gtqtcoreId("laser_booster." + UEV), UEV));

        for (int i = 0; i < 14; i++) {
            int id = 15481 + i;
            String name = String.format("parallel_hatch.%s", GTValues.VN[i + 1]);
            PARALLEL_HATCH[i] = registerMetaTileEntity(id, new MetaTileEntityParallelHatch(gtqtcoreId(name), i + 1));
        }
        for (int i = 0; i < 5; i++) {
            int id = 15495 + i;
            String name = String.format("radiation_hatch.%s", GTValues.VN[4 + i]);
            RADIATION_HATCH[i] = registerMetaTileEntity(id, new MetaTileEntityRadiationHatch(gtqtcoreId(name), 4 + i));
        }

        for (int i = 0; i < 5; i++) {
            int id = 15500 + i;
            String name = String.format("electrode_hatch.%s", GTValues.VN[i + 1]);
            ELECTRODE_HATCH[i] = registerMetaTileEntity(id, new MetaTileEntityElectrodeHatch(gtqtcoreId(name), i + 1));
        }
        for (int i = 0; i < 5; i++) {
            int id = 15505 + i;
            String name = String.format("drill_head_hatch.%s", GTValues.VN[i + 1]);
            DRILL_HEAD_HATCH[i] = registerMetaTileEntity(id, new MetaTileEntityDrillHeadHatch(gtqtcoreId(name), i + 1));
        }

        KQCC_HATCH[0] = registerMetaTileEntity(15510, new MetaTileEntityKQCCPartHatch(gtqtcoreId("kqcc_hatch.ram.1"), "ram", 1));
        KQCC_HATCH[1] = registerMetaTileEntity(15511, new MetaTileEntityKQCCPartHatch(gtqtcoreId("kqcc_hatch.ram.2"), "ram", 2));
        KQCC_HATCH[2] = registerMetaTileEntity(15512, new MetaTileEntityKQCCPartHatch(gtqtcoreId("kqcc_hatch.ram.3"), "ram", 3));

        KQCC_HATCH[3] = registerMetaTileEntity(15513, new MetaTileEntityKQCCPartHatch(gtqtcoreId("kqcc_hatch.cpu.1"), "cpu", 1));
        KQCC_HATCH[4] = registerMetaTileEntity(15514, new MetaTileEntityKQCCPartHatch(gtqtcoreId("kqcc_hatch.cpu.2"), "cpu", 2));
        KQCC_HATCH[5] = registerMetaTileEntity(15515, new MetaTileEntityKQCCPartHatch(gtqtcoreId("kqcc_hatch.cpu.3"), "cpu", 3));

        KQCC_HATCH[6] = registerMetaTileEntity(15516, new MetaTileEntityKQCCPartHatch(gtqtcoreId("kqcc_hatch.gpu.1"), "gpu", 1));
        KQCC_HATCH[7] = registerMetaTileEntity(15517, new MetaTileEntityKQCCPartHatch(gtqtcoreId("kqcc_hatch.gpu.2"), "gpu", 2));
        KQCC_HATCH[8] = registerMetaTileEntity(15518, new MetaTileEntityKQCCPartHatch(gtqtcoreId("kqcc_hatch.gpu.3"), "gpu", 3));

        LOCAL_COMPUTER_HATCH = registerMetaTileEntity(15519, new MetaTileEntityLocalModelComputationHatch(gtqtcoreId("local_computer_hatch")));

        for (int i = 0; i < 5; i++) {
            int id = 15520 + i;
            String name = String.format("heat_hatch.%s", GTValues.VN[i + 1]);
            HEAT_HATCH[i] = registerMetaTileEntity(id, new MetaTileEntityHeatHatch(gtqtcoreId(name), i + 1));
        }
        for (int i = 0; i < 5; i++) {
            int id = 15525 + i;
            String name = String.format("electric_heater.%s", GTValues.VN[i + 1]);
            ELECTRIC_HEATER[i] = registerMetaTileEntity(id, new MetaTileEntityElectricHeater(gtqtcoreId(name), i + 1));
        }

        POWER_SUPPLY_HATCH_BASIC = registerMetaTileEntity(15530, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.basic"), "frame", 1));
        POWER_SUPPLY_HATCH_BATTLE[0] = registerMetaTileEntity(15531, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.battle.1"), "battle", 1));
        POWER_SUPPLY_HATCH_BATTLE[1] = registerMetaTileEntity(15532, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.battle.2"), "battle", 2));
        POWER_SUPPLY_HATCH_BATTLE[2] = registerMetaTileEntity(15533, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.battle.3"), "battle", 3));
        POWER_SUPPLY_HATCH_BATTLE[3] = registerMetaTileEntity(15534, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.battle.4"), "battle", 4));
        POWER_SUPPLY_HATCH_BATTLE[4] = registerMetaTileEntity(15535, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.battle.5"), "battle", 5));
        POWER_SUPPLY_HATCH_SUPPLY[0] = registerMetaTileEntity(15536, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.supply.1"), "supply", 1));
        POWER_SUPPLY_HATCH_SUPPLY[1] = registerMetaTileEntity(15537, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.supply.2"), "supply", 2));
        POWER_SUPPLY_HATCH_SUPPLY[2] = registerMetaTileEntity(15538, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.supply.3"), "supply", 3));
        POWER_SUPPLY_HATCH_SUPPLY[3] = registerMetaTileEntity(15539, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.supply.4"), "supply", 4));
        POWER_SUPPLY_HATCH_SUPPLY[4] = registerMetaTileEntity(15540, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.supply.5"), "supply", 5));
        POWER_SUPPLY_HATCH_SUPPLY[5] = registerMetaTileEntity(15541, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.supply.6"), "supply", 6));
        POWER_SUPPLY_HATCH_SUPPLY[6] = registerMetaTileEntity(15542, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.supply.7"), "supply", 7));
        POWER_SUPPLY_HATCH_SUPPLY[7] = registerMetaTileEntity(15543, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.supply.8"), "supply", 8));
        POWER_SUPPLY_HATCH_SUPPLY[8] = registerMetaTileEntity(15544, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.supply.9"), "supply", 9));
        POWER_SUPPLY_HATCH_SUPPLY[9] = registerMetaTileEntity(15545, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.supply.10"), "supply", 10));

        BIO_HATCH = registerMetaTileEntity(15546, new MetaTileEntityBioHatch(gtqtcoreId("bio_hatch")));
        INF_WATER_HATCH = registerMetaTileEntity(15547, new MetaTileInfWaterHatch(gtqtcoreId("infinite_water_hatch")));
        CATALYST_HATCH[0] = registerMetaTileEntity(15548, new MetaTileEntityCatalystHatch(gtqtcoreId("catalyst_hatch.0"), 1));
        MULTIPART_BUFFER_HATCH = registerMetaTileEntity(15549, new MetaTileEntityBufferHatch(gtqtcoreId("buffer_hatch")));
        MULTIPART_BALL_HATCH = registerMetaTileEntity(15550, new MetaTileEntityMillBallHatch(gtqtcoreId("mill_ball_hatch")));
        //LV-OpV Reinforced Rotor Holders.
        for (int i = 0; i < 13; i++) {
            String voltageName = VN[i + 1].toLowerCase();
            REINFORCED_ROTOR_HOLDER[i] = registerMetaTileEntity(15553 + i, new MetaTileEntityReinforcedRotorHolder(gtqtcoreId("reinforced_rotor_holder." + voltageName), i + 1));
        }

        // ID 14501-14999: Several Misc Hatches
        AIR_INTAKE_HATCH = registerMetaTileEntity(15566,
                new MetaTileEntityAirIntakeHatch(
                        gtqtcoreId("air_intake_hatch"), IV)
        );
        EXTREME_AIR_INTAKE_HATCH = registerMetaTileEntity(15567,
                new MetaTileEntityAirIntakeHatch(
                        gtqtcoreId("extreme_air_intake_hatch"), ZPM)
        );
        ULTIMATE_AIR_INTAKE_HATCH = registerMetaTileEntity(15568,
                new MetaTileEntityAirIntakeHatch(
                        gtqtcoreId("ultimate_air_intake_hatch"), UHV));

        SINGLE_ITEM_INPUT_BUS = registerMetaTileEntity(15569, new MetaTileEntitySingleItemInputBus(gtqtcoreId("single_item_input_bus")));
        SUPER_INPUT_BUS = registerMetaTileEntity(15570, new MetaTileEntitySuperInputBus(gtqtcoreId("super_input_bus")));
        SINGLE_INPUT_BUS = registerMetaTileEntity(15571, new MetaTileEntitySingleInputBus(gtqtcoreId("single_input_bus")));
        CATALYST_HATCH[1] = registerMetaTileEntity(15572, new MetaTileEntityCatalystHatch(gtqtcoreId("catalyst_hatch.1"), 2));
        CATALYST_HATCH[2] = registerMetaTileEntity(15573, new MetaTileEntityCatalystHatch(gtqtcoreId("catalyst_hatch.2"), 3));
        CATALYST_HATCH[3] = registerMetaTileEntity(15574, new MetaTileEntityCatalystHatch(gtqtcoreId("catalyst_hatch.3"), 4));

        GENERATOR_HATCH= registerMetaTileEntity(15584, new MetaTileEntityGeneratorHatch(gtqtcoreId("generator_hatch"),3));

        STERILE_CLEANING_MAINTENANCE_HATCH = registerMetaTileEntity(15586, new MetaTileEntitySterileCleaningMaintenanceHatch(gtqtcoreId("maintenance_hatch_sterile_cleanroom_auto")));
        ISO3_CLEANING_MAINTENANCE_HATCH = registerMetaTileEntity(15587, new MetaTileEntityISO3CleaningMaintenanceHatch(gtqtcoreId("maintenance_hatch_iso_3_cleanroom_auto")));
        ISO2_CLEANING_MAINTENANCE_HATCH = registerMetaTileEntity(15588, new MetaTileEntityISO2CleaningMaintenanceHatch(gtqtcoreId("maintenance_hatch_iso_2_cleanroom_auto")));
        ISO1_CLEANING_MAINTENANCE_HATCH = registerMetaTileEntity(15589, new MetaTileEntityISO1CleaningMaintenanceHatch(gtqtcoreId("maintenance_hatch_iso_1_cleanroom_auto")));


        for (int i = 0; i < 5; i++) {
            int id = 15600 + i;
            String name = String.format("swarm_hatch.%s", i);
            SWARM_HATCH[i] = registerMetaTileEntity(id, new MetaTileEntityWrapSwarmHatch(gtqtcoreId(name), i + 5));
        }

        int startId = 15605;
        for (int i = 0; i < 14; i++) {
            double min = GCYSValues.decreaseDetailP[i];
            double max = GCYSValues.increaseDetailP[i];

            PRESSURE_HATCH[i] = registerMetaTileEntity(startId + i, new MetaTileEntityPressureHatch(gtqtcoreId(String.format("pressure_hatch.%s", VN[i].toLowerCase())), i, min, max));
        }

        PARTICLE_HATCH = registerMetaTileEntity(15620, new MetaTileEntityParticleHatch(gtqtcoreId("particle_hatch")));

        simpleTiredInit(CREATIVE_ENERGY_HATCHES, (i) -> new MetaTileEntityCreativeEnergyHatch(gtqtcoreId("creative_energy_hatch." + GTValues.VN[i].toLowerCase()), i), GTQTMetaTileEntities::getMaterialsId);
    }
}