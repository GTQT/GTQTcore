package keqing.gtqtcore.common.metatileentities;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.SimpleGeneratorMetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockTurbineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.multi.electric.generator.MetaTileEntityLargeTurbine;
import keqing.gtqtcore.GTQTCoreConfig;
import keqing.gtqtcore.api.GCYSValues;
import keqing.gtqtcore.api.metaileentity.SimpleSteamMetaTileEntity;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing1;
import keqing.gtqtcore.common.metatileentities.multi.generators.*;
import keqing.gtqtcore.common.metatileentities.multi.generators.Tide.MetaTileEntityTideControl;
import keqing.gtqtcore.common.metatileentities.multi.generators.Tide.MetaTileEntityTideUnit;
import keqing.gtqtcore.common.metatileentities.multi.generators.Wind.MetaTileEntityWindGenerator;
import keqing.gtqtcore.common.metatileentities.multi.generators.generatorRework.MetaTileEntityEnergySubstation;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.primitive.MetaTileEntityIndustryWaterPump;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.primitive.MetaTileEntityPrimitiveReactor;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.primitive.MetaTileEntityPrimitiveRoaster;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.primitive.MetaTileEntityPrimitiveTreeFarmer;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.LaserSystem.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.endGame.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.giantEquipment.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.heatExchanger.MetaTileEntityHeatExchanger;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.heatExchanger.MetaTileEntityLargeHeatExchanger;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.heatExchanger.MetaTileEntitySmallHeatExchanger;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.heatSystem.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.ResearchSystem.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.updateSystem.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.pressureSystem.MetaTileEntityAxialCompressor;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.pressureSystem.MetaTileEntityPressureTank;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.pressureSystem.MetaTileEntityTurbomolecularPump;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.resourceCollection.MetaTileEntityAdvancedFluidDrill;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.resourceCollection.MetaTileEntityAdvancedLargeMiner;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.resourceCollection.MetaTileEntityVoidMiner;
import keqing.gtqtcore.common.metatileentities.multi.multiblockpart.*;
import keqing.gtqtcore.common.metatileentities.single.electric.*;
import keqing.gtqtcore.common.metatileentities.single.steam.MetaTileEntityCreativePressurePump;
import keqing.gtqtcore.common.metatileentities.single.steam.MetaTileEntitySteamEjector;
import keqing.gtqtcore.common.metatileentities.single.steam.MetaTileEntitySteamLatexCollector;
import keqing.gtqtcore.common.metatileentities.single.steam.MetaTileEntitySteamVacuumChamber;
import keqing.gtqtcore.common.metatileentities.storage.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import static appeng.core.features.AEFeature.CONDENSER;
import static gregtech.api.GTValues.UHV;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;
import static gregtech.common.metatileentities.MetaTileEntities.registerSimpleMetaTileEntity;
import static keqing.gtqtcore.api.GCYSValues.*;
import static keqing.gtqtcore.api.GTQTValue.gtqtcoreId;
import static keqing.gtqtcore.api.metaileentity.SteamProgressIndicators.COMPRESS;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.utils.GTQTUtil.genericGeneratorTankSizeFunctionPlus;
import static keqing.gtqtcore.api.utils.MultiblockRegistryHelper.registerSimpleSteamMetaTileEntity;
import static keqing.gtqtcore.client.textures.GTQTTextures.*;
import static keqing.gtqtcore.common.block.blocks.BlockCompressedFusionReactor.CasingType.*;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing1.CasingType.HastelloyX78;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3.CasingType.HC_ALLOY_CASING;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing3.CasingType.*;

public class GTQTMetaTileEntities {


    ////////////////////////////io
    /* ---------------------------------------------------------------- Multiblock Parts ---------------------------------------------------------------- */

    public static final MetaTileEntityCreativeEnergyHatch[] CREATIVE_ENERGY_HATCHES = new MetaTileEntityCreativeEnergyHatch[GTValues.V.length];
    public static final SimpleGeneratorMetaTileEntity[] NAQUADAH_REACTOR = new SimpleGeneratorMetaTileEntity[3];
    public static final SimpleGeneratorMetaTileEntity[] ROCKET_ENGINE = new SimpleGeneratorMetaTileEntity[3];
    public static final MetaTileEntityAdvancedFusionReactor[] ADVANCED_FUSION_REACTOR = new MetaTileEntityAdvancedFusionReactor[3];
    public static final SimpleGeneratorMetaTileEntity[] FUEL_CELL_TURBINE = new SimpleGeneratorMetaTileEntity[5];
    public static final SimpleMachineMetaTileEntity[] FLUID_CANNER = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static final SimpleMachineMetaTileEntity[] LAMINATOR = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static final SimpleMachineMetaTileEntity[] FLUID_EXTRACTOR = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static final MetaTileEntityMicrowaveEnergyReceiver[] MICROWAVE_ENERGY_RECEIVER = new MetaTileEntityMicrowaveEnergyReceiver[GTValues.V.length - 1];
    public static final SimpleGeneratorMetaTileEntity[] BIOMASS_GENERATOR = new SimpleGeneratorMetaTileEntity[3];
    public static final SimpleGeneratorMetaTileEntity[] ACID_GENERATOR = new SimpleGeneratorMetaTileEntity[3];
    public static final SimpleGeneratorMetaTileEntity[] PLASMA_GENERATOR = new SimpleGeneratorMetaTileEntity[5];
    public static final MetaTileEntityReinforcedRotorHolder[] REINFORCED_ROTOR_HOLDER = new MetaTileEntityReinforcedRotorHolder[14];
    public static final MetaTileEntityCompressedFusionReactor[] COMPRESSED_FUSION_REACTOR = new MetaTileEntityCompressedFusionReactor[6];
    public static MetaTileEntityWrapSwarmHatch SWARM_HATCH ;
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
    public static MetaTileEntityAdvanceKQCC ADV_KQCC;
    public static MetaTileEntityEnergyInfuser ENERGY_INFUSER;
    public static MetaTileEntityGeneMutagenesis GENE_MUTAGENESIS;
    public static MetaTileEntityIndustrialInductionFurnace INDUSTRIAL_INDUCTION_FURNACE;
    public static MetaTileEntityPressureMachine[] VACUUM_CHAMBER = new MetaTileEntityPressureMachine[V.length - 1];
    public static SimpleSteamMetaTileEntity[] STEAM_SPINNER = new SimpleSteamMetaTileEntity[2];
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
    public static SimpleMachineMetaTileEntity[] CATALYTIC_REFORMER = new SimpleMachineMetaTileEntity[V.length - 1];
    public static SimpleMachineMetaTileEntity[] CONDENSER = new SimpleMachineMetaTileEntity[V.length - 1];
    public static SimpleMachineMetaTileEntity[] SONICATOR = new SimpleMachineMetaTileEntity[V.length - 1];
    public static SimpleMachineMetaTileEntity[] ION_IMPLANTER = new SimpleMachineMetaTileEntity[V.length - 1];
    public static SimpleMachineMetaTileEntity[] CVD_UNIT = new SimpleMachineMetaTileEntity[V.length - 1];
    public static SimpleMachineMetaTileEntity[] FLOTATION_CELL = new SimpleMachineMetaTileEntity[V.length - 1];
    public static SimpleMachineMetaTileEntity[] POLISHER = new SimpleMachineMetaTileEntity[V.length - 1];
    public static SimpleMachineMetaTileEntity[] SIMULATOR = new SimpleMachineMetaTileEntity[V.length - 1];

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
    //public static MetaTileEntityKineticEnergyBattery KINETIC_ENERGY_BATTERY;
    //public static MetaTileEntityGrayElectricPowerBank GRAY_ELECTRIC_POWER_BANK;
    public static MetaTileEntityAssemblyLine ASSEMBLY_LINE;
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
    public static MetaTileEntityLargeBender LARGE_BENDER;
    public static MetaTileEntityLargeCutter LARGE_CUTTER;
    public static MetaTileEntityLargeExtruder LARGE_EXTRUDER;
    public static MetaTileEntityLargeWireMill LARGE_WIREMILL;
    public static MetaTileEntityNanoCoating NANO_COATING;
    public static MetaTileEntityCryogenicFreezer CRYOGENIC_FREEZER;
    public static MetaTileEntityHeatExchanger HEAT_CHANGER;
    public static MetaTileEntityIntegratedOreProcessor INTEGRATED_ORE_PROCESSOR;
    public static MetaTileEntityCrystallizationCrucible LARGE_CRYSTALLIZATION_CRUCIBLE;
    public static MetaTileEntityBurnerReactor LARGE_ROASTER;
    public static MetaTileEntityMegaTurbine MEGA_STEAM_TURBINE;
    public static MetaTileEntityMegaTurbine MEGA_GAS_TURBINE;
    public static MetaTileEntityMegaTurbine MEGA_PLASMA_TURBINE;
    public static MetaTileEntityMegaTurbine MEGA_FUEL_CELL_TURBINE;

    public static MetaTileEntitySteamEjector[] STEAM_EJECTOR = new MetaTileEntitySteamEjector[2];
    public static MetaTileEntitySteamVacuumChamber[] STEAM_VACUUM_CHAMBER = new MetaTileEntitySteamVacuumChamber[2];
    public static MetaTileEntityCreativePressurePump CREATIVE_PRESSURE;
    public static MetaTileEntityPressureTank[] PRESSURE_TANK = new MetaTileEntityPressureTank[3];

    public static MetaTileEntityCombinedSteamTurbine HIGH_PRESSURE_STEAM_TURBINE;
    public static MetaTileEntityCombinedSteamTurbine SUPERCRITICAL_STEAM_TURBINE;
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

    /*
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
    */

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

    public static void initialization() {
        GTQTLog.logger.info("Registering MetaTileEntities");
        //发电设备 单方块
        //避雷针
        LIGHTNING_ROD[0] = registerMetaTileEntity(0, new MetaTileEntityLightningRod(gtqtcoreId("lightning_rod.iv"), GTValues.IV));
        LIGHTNING_ROD[1] = registerMetaTileEntity(1, new MetaTileEntityLightningRod(gtqtcoreId("lightning_rod.luv"), LuV));
        LIGHTNING_ROD[2] = registerMetaTileEntity(2, new MetaTileEntityLightningRod(gtqtcoreId("lightning_rod.zpm"), GTValues.ZPM));
        //小硅岩
        NAQUADAH_REACTOR[0] = registerMetaTileEntity(3, new SimpleGeneratorMetaTileEntity(gtqtcoreId("naquadah_reactor.iv"), GTQTcoreRecipeMaps.NAQUADAH_REACTOR, GTQTTextures.NAQUADAH_REACTOR_OVERLAY, 5, genericGeneratorTankSizeFunctionPlus));
        NAQUADAH_REACTOR[1] = registerMetaTileEntity(4, new SimpleGeneratorMetaTileEntity(gtqtcoreId("naquadah_reactor.luv"), GTQTcoreRecipeMaps.NAQUADAH_REACTOR, GTQTTextures.NAQUADAH_REACTOR_OVERLAY, 6, genericGeneratorTankSizeFunctionPlus));
        NAQUADAH_REACTOR[2] = registerMetaTileEntity(5, new SimpleGeneratorMetaTileEntity(gtqtcoreId("naquadah_reactor.zpm"), GTQTcoreRecipeMaps.NAQUADAH_REACTOR, GTQTTextures.NAQUADAH_REACTOR_OVERLAY, 7, genericGeneratorTankSizeFunctionPlus));
        //小火箭
        ROCKET_ENGINE[0] = registerMetaTileEntity(6, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rocket_engine.hv"), GTQTcoreRecipeMaps.ROCKET_RECIPES, GTQTTextures.ROCKET_ENGINE_OVERLAY, 3, genericGeneratorTankSizeFunctionPlus));
        ROCKET_ENGINE[1] = registerMetaTileEntity(7, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rocket_engine.ev"), GTQTcoreRecipeMaps.ROCKET_RECIPES, GTQTTextures.ROCKET_ENGINE_OVERLAY, 4, genericGeneratorTankSizeFunctionPlus));
        ROCKET_ENGINE[2] = registerMetaTileEntity(8, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rocket_engine.iv"), GTQTcoreRecipeMaps.ROCKET_RECIPES, GTQTTextures.ROCKET_ENGINE_OVERLAY, 5, genericGeneratorTankSizeFunctionPlus));
        //小促燃
        FUEL_CELL_TURBINE[0] = registerMetaTileEntity(15, new SimpleGeneratorMetaTileEntity(gtqtcoreId("fuel_cell_turbine.lv"), GTQTcoreRecipeMaps.FUEL_CELL, Textures.COMBUSTION_GENERATOR_OVERLAY, 1, genericGeneratorTankSizeFunctionPlus));
        FUEL_CELL_TURBINE[1] = registerMetaTileEntity(16, new SimpleGeneratorMetaTileEntity(gtqtcoreId("fuel_cell_turbine.mv"), GTQTcoreRecipeMaps.FUEL_CELL, Textures.COMBUSTION_GENERATOR_OVERLAY, 2, genericGeneratorTankSizeFunctionPlus));
        FUEL_CELL_TURBINE[2] = registerMetaTileEntity(17, new SimpleGeneratorMetaTileEntity(gtqtcoreId("fuel_cell_turbine.hv"), GTQTcoreRecipeMaps.FUEL_CELL, Textures.COMBUSTION_GENERATOR_OVERLAY, 3, genericGeneratorTankSizeFunctionPlus));
        FUEL_CELL_TURBINE[3] = registerMetaTileEntity(18, new SimpleGeneratorMetaTileEntity(gtqtcoreId("fuel_cell_turbine.ev"), GTQTcoreRecipeMaps.FUEL_CELL, Textures.COMBUSTION_GENERATOR_OVERLAY, 4, genericGeneratorTankSizeFunctionPlus));
        FUEL_CELL_TURBINE[4] = registerMetaTileEntity(19, new SimpleGeneratorMetaTileEntity(gtqtcoreId("fuel_cell_turbine.iv"), GTQTcoreRecipeMaps.FUEL_CELL, Textures.COMBUSTION_GENERATOR_OVERLAY, 5, genericGeneratorTankSizeFunctionPlus));
        //生物质
        BIOMASS_GENERATOR[0] = registerMetaTileEntity(20, new SimpleGeneratorMetaTileEntity(gtqtcoreId("biomass_generator.ev"), GTQTcoreRecipeMaps.BIOMASS_GENERATOR_RECIPES, GTQTTextures.BIOMASS_GENERATOR_OVERLAY, 4, GTUtility.genericGeneratorTankSizeFunction));
        BIOMASS_GENERATOR[1] = registerMetaTileEntity(21, new SimpleGeneratorMetaTileEntity(gtqtcoreId("biomass_generator.iv"), GTQTcoreRecipeMaps.BIOMASS_GENERATOR_RECIPES, GTQTTextures.BIOMASS_GENERATOR_OVERLAY, 5, GTUtility.genericGeneratorTankSizeFunction));
        BIOMASS_GENERATOR[2] = registerMetaTileEntity(22, new SimpleGeneratorMetaTileEntity(gtqtcoreId("biomass_generator.luv"), GTQTcoreRecipeMaps.BIOMASS_GENERATOR_RECIPES, GTQTTextures.BIOMASS_GENERATOR_OVERLAY, 6, GTUtility.genericGeneratorTankSizeFunction));
        //酸液
        ACID_GENERATOR[0] = registerMetaTileEntity(23, new SimpleGeneratorMetaTileEntity(gtqtcoreId("acid_generator.mv"), GTQTcoreRecipeMaps.ACID_GENERATOR_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 2, GTUtility.genericGeneratorTankSizeFunction));
        ACID_GENERATOR[1] = registerMetaTileEntity(24, new SimpleGeneratorMetaTileEntity(gtqtcoreId("acid_generator.hv"), GTQTcoreRecipeMaps.ACID_GENERATOR_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 3, GTUtility.genericGeneratorTankSizeFunction));
        ACID_GENERATOR[2] = registerMetaTileEntity(25, new SimpleGeneratorMetaTileEntity(gtqtcoreId("acid_generator.ev"), GTQTcoreRecipeMaps.ACID_GENERATOR_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 4, GTUtility.genericGeneratorTankSizeFunction));
        //等离子
        PLASMA_GENERATOR[0] = registerMetaTileEntity(26, new SimpleGeneratorMetaTileEntity(gtqtcoreId("plasma_generator.ev"), RecipeMaps.PLASMA_GENERATOR_FUELS, Textures.FUSION_REACTOR_OVERLAY, 4, GTUtility.genericGeneratorTankSizeFunction));
        PLASMA_GENERATOR[1] = registerMetaTileEntity(27, new SimpleGeneratorMetaTileEntity(gtqtcoreId("plasma_generator.iv"), RecipeMaps.PLASMA_GENERATOR_FUELS, Textures.FUSION_REACTOR_OVERLAY, 5, GTUtility.genericGeneratorTankSizeFunction));
        PLASMA_GENERATOR[2] = registerMetaTileEntity(28, new SimpleGeneratorMetaTileEntity(gtqtcoreId("plasma_generator.luv"), RecipeMaps.PLASMA_GENERATOR_FUELS, Textures.FUSION_REACTOR_OVERLAY, 6, GTUtility.genericGeneratorTankSizeFunction));
        PLASMA_GENERATOR[3] = registerMetaTileEntity(29, new SimpleGeneratorMetaTileEntity(gtqtcoreId("plasma_generator.zpm"), RecipeMaps.PLASMA_GENERATOR_FUELS, Textures.FUSION_REACTOR_OVERLAY, 7, GTUtility.genericGeneratorTankSizeFunction));
        PLASMA_GENERATOR[4] = registerMetaTileEntity(30, new SimpleGeneratorMetaTileEntity(gtqtcoreId("plasma_generator.uv"), RecipeMaps.PLASMA_GENERATOR_FUELS, Textures.FUSION_REACTOR_OVERLAY, 8, GTUtility.genericGeneratorTankSizeFunction));

        //发电设备 多方块
        //高能反应堆
        HYPER_REACTOR_MKI = registerMetaTileEntity(50, new MetaTileEntityHyperReactorMkI(gtqtcoreId("hyper_reactor_mk1")));
        HYPER_REACTOR_MKII = registerMetaTileEntity(51, new MetaTileEntityHyperReactorMkII(gtqtcoreId("hyper_reactor_mk2")));
        HYPER_REACTOR_MKIII = registerMetaTileEntity(52, new MetaTileEntityHyperReactorMkIII(gtqtcoreId("hyper_reactor_mk3")));

        //大硅岩
        LARGE_NAQUADAH_REACTOR = registerMetaTileEntity(55, new MetaTileEntityLargeNaquadahReactor(gtqtcoreId("large_naquadah_reactor")));

        //大火箭
        LARGE_ROCKET_ENGIN = registerMetaTileEntity(60, new MetaTileEntityLargeRocketEngine(gtqtcoreId("large_rocket_engin"), LuV));
        EXTREME_LARGE_ROCKET_ENGIN = registerMetaTileEntity(61, new MetaTileEntityLargeRocketEngine(gtqtcoreId("extreme_large_rocket_engin"), ZPM));

        //巨型涡轮
        MEGA_STEAM_TURBINE = registerMetaTileEntity(65, new MetaTileEntityMegaTurbine(gtqtcoreId("mega_turbine.steam"), RecipeMaps.STEAM_TURBINE_FUELS, 3, MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STEEL_TURBINE_CASING), MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STEEL_GEARBOX), Textures.SOLID_STEEL_CASING, false, GTQTTextures.MEGA_TURBINE_OVERLAY));
        MEGA_GAS_TURBINE = registerMetaTileEntity(66, new MetaTileEntityMegaTurbine(gtqtcoreId("mega_turbine.gas"), RecipeMaps.GAS_TURBINE_FUELS, 4, MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STAINLESS_TURBINE_CASING), MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STAINLESS_STEEL_GEARBOX), Textures.CLEAN_STAINLESS_STEEL_CASING, true, GTQTTextures.MEGA_TURBINE_OVERLAY));
        MEGA_PLASMA_TURBINE = registerMetaTileEntity(67, new MetaTileEntityMegaTurbine(gtqtcoreId("mega_turbine.plasma"), RecipeMaps.PLASMA_GENERATOR_FUELS, 5, GTQTMetaBlocks.blockMultiblockCasing3.getState(HC_ALLOY_CASING), GTQTMetaBlocks.blockMultiblockCasing3.getState(HG1223_GEARBOX), GTQTTextures.HC_ALLOY_CASING, false, GTQTTextures.MEGA_TURBINE_OVERLAY));
        MEGA_FUEL_CELL_TURBINE = registerMetaTileEntity(68, new MetaTileEntityMegaTurbine(gtqtcoreId("mega_turbine.fuel_cell"), FUEL_CELL, 5, GTQTMetaBlocks.blockMultiblockCasing3.getState(NITINOL_MACHINE_CASING), GTQTMetaBlocks.blockMultiblockCasing3.getState(NITINOL_GEARBOX), GTQTTextures.NITINOL_CASING, true, GTQTTextures.MEGA_TURBINE_OVERLAY));

        //超临界涡轮
        HIGH_PRESSURE_STEAM_TURBINE = registerMetaTileEntity(70, new MetaTileEntityCombinedSteamTurbine(gtqtcoreId("high_pressure_steam_turbine"), false));
        SUPERCRITICAL_STEAM_TURBINE = registerMetaTileEntity(71, new MetaTileEntityCombinedSteamTurbine(gtqtcoreId("supercritical_steam_turbine"), true));
        MEGA_HIGH_PRESSURE_STEAM_TURBINE = registerMetaTileEntity(72, new MetaTileEntityMegaTurbine(gtqtcoreId("mega_high_pressure_steam_turbine"), GTQTcoreRecipeMaps.HIGH_PRESSURE_STEAM_TURBINE_RECIPES, EV, MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TITANIUM_TURBINE_CASING), MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TITANIUM_GEARBOX), Textures.STABLE_TITANIUM_CASING, false, GTQTTextures.MEGA_TURBINE_OVERLAY));
        MEGA_SUPERCRITICAL_STEAM_TURBINE = registerMetaTileEntity(73, new MetaTileEntityMegaTurbine(gtqtcoreId("mega_supercritical_steam_turbine"), GTQTcoreRecipeMaps.SUPERCRITICAL_STEAM_TURBINE_RECIPES, LuV, GTQTMetaBlocks.blockMultiblockCasing1.getState(BlockMultiblockCasing1.CasingType.MaragingSteel250), MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TUNGSTENSTEEL_GEARBOX), GTQTTextures.MaragingSteel250, false, GTQTTextures.MEGA_TURBINE_OVERLAY));

        //促燃与生物质的大涡轮
        LARGE_FUEL_TURBINE = registerMetaTileEntity(75, new MetaTileEntityLargeTurbine(gtqtcoreId("large_turbine.fuel_cell"), GTQTcoreRecipeMaps.FUEL_CELL, 4, GTQTMetaBlocks.blockMultiblockCasing1.getState(HastelloyX78), MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TUNGSTENSTEEL_GEARBOX), GTQTTextures.HastelloyX78, true, Textures.LARGE_GAS_TURBINE_OVERLAY));
        LARGE_BIOMASS_GENERATOR = registerMetaTileEntity(76, new MetaTileEntityLargeBiomassGenerator(gtqtcoreId("large_biomass_generator")));

        //水电
        WATER_POWER_STATION[0] = registerMetaTileEntity(80, new MetaTileEntityWaterPowerStation(gtqtcoreId("water_power_station_mk1"), 1));
        WATER_POWER_STATION[1] = registerMetaTileEntity(81, new MetaTileEntityWaterPowerStation(gtqtcoreId("water_power_station_mk2"), 2));
        WATER_POWER_STATION[2] = registerMetaTileEntity(82, new MetaTileEntityWaterPowerStation(gtqtcoreId("water_power_station_mk3"), 3));


        TIDE_CONTROL = registerMetaTileEntity(83, new MetaTileEntityTideControl(gtqtcoreId("tide_control")));
        TIDE_UNIT = registerMetaTileEntity(84, new MetaTileEntityTideUnit(gtqtcoreId("tide_unit")));

        //太阳能
        SOLAR_PLATE = registerMetaTileEntity(85, new MetaTileEntitySolarPlate(gtqtcoreId("solar_plate")));

        //风电
        WIND_GENERATOR = registerMetaTileEntity(90, new MetaTileEntityWindGenerator(gtqtcoreId("wind_generator")));

        //动能电池
        //KINETIC_ENERGY_BATTERY = registerMetaTileEntity(99, new MetaTileEntityKineticEnergyBattery(gtqtcoreId("kinetic_energy_battery")));

        //激光
        LASER_BLAST_FURNACE = registerMetaTileEntity(100, new MetaTileEntityLaserBlastFurnace(gtqtcoreId("laser_blast_furnace")));
        LASER_ALLOY_FURNACE = registerMetaTileEntity(101, new MetaTileEntityLaserAlloyFurnace(gtqtcoreId("laser_alloy_furnace")));
        LASER_CUTTER = registerMetaTileEntity(102, new MetaTileEntityLaserCutter(gtqtcoreId("laser_cutter")));
        LASER_ENV = registerMetaTileEntity(103, new MetaTileEntityLaserENV(gtqtcoreId("laser_env")));
        LASER_CHEMICAL_PLANT = registerMetaTileEntity(104, new MetaTileEntityLaserChemicalPlant(gtqtcoreId("laser_chemical_plant")));

        LASER_FBT = registerMetaTileEntity(110, new MetaTileEntityLaserBooster(gtqtcoreId("laser_fbt")));
        LASER_FBC = registerMetaTileEntity(111, new MetaTileEntityLaserFusionCore(gtqtcoreId("laser_fbc")));

        LASER_EMITTER = registerMetaTileEntity(115, new MetaTileEntityLaserEmitter(gtqtcoreId("laser_emitter")));
        LASER_TRANSLATION = registerMetaTileEntity(116, new MetaTileEntityLaserTranslation(gtqtcoreId("laser_translation")));
        LASER_SWITCH = registerMetaTileEntity(117, new MetaTileEntitySwitch(gtqtcoreId("laser_switch")));

        SBPRC = registerMetaTileEntity(120, new MetaTileEntitySBPRC(gtqtcoreId("sbprc")));
        SBPRI = registerMetaTileEntity(121, new MetaTileEntitySBPRI(gtqtcoreId("sbpri")));
        SBPRO = registerMetaTileEntity(122, new MetaTileEntitySBPRO(gtqtcoreId("sbpro")));

        //早期设备
        PRIMITIVE_TREE_FARMER = registerMetaTileEntity(150, new MetaTileEntityPrimitiveTreeFarmer(gtqtcoreId("primitive_tree_farmer")));
        PRIMITIVE_ROASTER = registerMetaTileEntity(152, new MetaTileEntityPrimitiveRoaster(gtqtcoreId("primitive_roaster")));
        HEAT_FURNACE = registerMetaTileEntity(153, new MetaTileEntityHeatFurnace(gtqtcoreId("heat_furnace")));
        PRIMITIVE_REACTOR = registerMetaTileEntity(154, new MetaTileEntityPrimitiveReactor(gtqtcoreId("primitive_reactor")));

        //正常设备
        DISSOLUTION_TANK = registerMetaTileEntity(201, new MetaTileEntityDissolutionTank(gtqtcoreId("dissolution_tank")));
        BLAZING_BLAST_FURNACE = registerMetaTileEntity(202, new MetaTileEntityBlazingBlastFurnace(gtqtcoreId("blazing_blast_furnace")));
        CHEMICAL_PLANT = registerMetaTileEntity(203, new MetaTileEntityChemicalPlant(gtqtcoreId("chemical_plant")));
        INTEGRATED_MINING_DIVISION = registerMetaTileEntity(204, new MetaTileEntityIntegratedMiningDivision(gtqtcoreId("integrated_mining_division")));
        QUANTUM_FORCE_TRANSFORMER = registerMetaTileEntity(205, new MetaTileEntityQuantumForceTransformer(gtqtcoreId("quantum_force_transform")));
        SMALL_HEAT_EXCHANGER = registerMetaTileEntity(206, new MetaTileEntitySmallHeatExchanger(gtqtcoreId("small_heat_exchanger")));
        LARGE_HEAT_EXCHANGER = registerMetaTileEntity(207, new MetaTileEntityLargeHeatExchanger(gtqtcoreId("large_heat_exchanger")));
        FUEL_REFINE_FACTORY = registerMetaTileEntity(208, new MetaTileEntityFuelRefineFactory(gtqtcoreId("fuel_refine_factory")));
        FERMENTATION_TANK = registerMetaTileEntity(209, new MetaTileEntityFermentationTank(gtqtcoreId("fermentation_tank")));
        DIGESTER = registerMetaTileEntity(210, new MetaTileEntityDigester(gtqtcoreId("digester")));
        SEPTIC_TANK = registerMetaTileEntity(211, new MetaTileEntitySepticTank(gtqtcoreId("septic_tank")));
        PCB_FACTORY = registerMetaTileEntity(212, new MetaTileEntityPCBFactory(gtqtcoreId("pcb_factory")));
        INDUSTRIAL_FISHING_POND = registerMetaTileEntity(213, new MetaTileEntityIndustrialFishingPond(gtqtcoreId("IndustrialFishingPond")));
        ISA_MILL = registerMetaTileEntity(214, new MetaTileEntityIsaMill(gtqtcoreId("isa_mill")));
        FLOTATION_FACTORY = registerMetaTileEntity(215, new MetaTileEntityFlotationFactory(gtqtcoreId("flotation_factory")));
        VACUUM_DRYING_FURNACE = registerMetaTileEntity(216, new MetaTileEntityVacuumDryingFurnace(gtqtcoreId("vacuum_drying_furnace")));
        ALGAE_FARM = registerMetaTileEntity(217, new MetaTileEntityAlgaeFarm(gtqtcoreId("algae_farm")));
        ELECTROBATH = registerMetaTileEntity(218, new MetaTileEntityElectronBath(gtqtcoreId("electrobath")));
        SALT_FLIED = registerMetaTileEntity(219, new MetaTileEntitySaltField(gtqtcoreId("salt_flied")));
        PYROLYSIS_TOWER = registerMetaTileEntity(220, new MetaTileEntityPyrolysisTower(gtqtcoreId("pyrolysis_tower")));
        PRECISE_ASSEMBLER = registerMetaTileEntity(221, new MetaTileEntityPreciseAssembler(gtqtcoreId("precise_assembler")));
        PARTICLE_ACCELERATOR = registerMetaTileEntity(222, new MetaTileEntityParticleAccelerator(gtqtcoreId("particle_accelerator")));
        ENERGY_INFUSER = registerMetaTileEntity(223, new MetaTileEntityEnergyInfuser(gtqtcoreId("energy_infuser")));
        BIOLOGICAL_REACTION = registerMetaTileEntity(224, new MetaTileEntityBiologicalReaction(gtqtcoreId(("biological_reaction"))));
        OIL_POOL = registerMetaTileEntity(225, new MetaTileEntityOilPool(gtqtcoreId(("oil_pool"))));
        MINING_DRILL = registerMetaTileEntity(226, new MetaTileEntityMiningDrill(gtqtcoreId(("mining_drill"))));
        DISTILLATION_KETTLE = registerMetaTileEntity(227, new MetaTileEntityDistillationKettle(gtqtcoreId(("distillation_kettle"))));
        OCEAN_PUMPER = registerMetaTileEntity(228, new MetaTileEntityOceanPumper(gtqtcoreId("ocean_pumper")));
        HEAT_CHANGER = registerMetaTileEntity(229, new MetaTileEntityHeatExchanger(gtqtcoreId("heat_exchanger")));
        LASER_ENGRAVING = registerMetaTileEntity(230, new MetaTileEntityLaserEngraving(gtqtcoreId("laser_engraving")));
        STEPPER = registerMetaTileEntity(231, new MetaTileEntityStepper(gtqtcoreId("stepper")));
        INDUSTRIAL_REFINER = registerMetaTileEntity(232, new MetaTileEntityIndustrialRefiner(gtqtcoreId("industrial_refiner")));
        CLARIFIER = registerMetaTileEntity(233, new MetaTileEntityClarifier(gtqtcoreId("clarifier")));
        ELE_OIL = registerMetaTileEntity(234, new MetaTileEntityElectronOil(gtqtcoreId("ele_oil")));
        THREE_DIM_PRINT = registerMetaTileEntity(235, new MetaTileEntityThreeDimPrinter(gtqtcoreId("three_dim_print")));
        NEUTRON_ACTIVATOR = registerMetaTileEntity(236, new MetaTileEntityNeutronActivator(gtqtcoreId("neutron_activator")));
        MSF = registerMetaTileEntity(237, new MetaTileEntityMSF(gtqtcoreId("msf")));
        INTEGRATED_ORE_PROCESSOR = registerMetaTileEntity(238, new MetaTileEntityIntegratedOreProcessor(gtqtcoreId("integrated_ore_processor")));
        EXTREME_INDUSTRIAL_GREENHOUSE = registerMetaTileEntity(239, new MetaTileEntityExtremeIndustrialGreenhouse(gtqtcoreId("extreme_industrial_greenhouse")));
        ENZYMES_REACTOR = registerMetaTileEntity(240, new MetaTileEntityEnzymesReaction(gtqtcoreId("enzymes_reactor")));
        BIO_CENTRIFUGE = registerMetaTileEntity(241, new MetaTileEntityBioCentrifuge(gtqtcoreId("bio_centrifuge")));
        CRYOGENIC_FREEZER = registerMetaTileEntity(242, new MetaTileEntityCryogenicFreezer(gtqtcoreId("cryogenic_freezer")));
        NANO_COATING = registerMetaTileEntity(243, new MetaTileEntityNanoCoating(gtqtcoreId("nano_coating")));
        POWER_SUPPLY = registerMetaTileEntity(244, new MetaTileEntityPowerSupply(gtqtcoreId("power_supply")));
        FROTH_FLOTATION_TANK = registerMetaTileEntity(245, new MetaTileEntityFrothFlotationTank(gtqtcoreId("froth_flotation_tank")));
        MICROWAVE_ENERGY_RECEIVER_CONTROL = registerMetaTileEntity(246, new MetaTileEntityMicrowaveEnergyReceiverControl(gtqtcoreId("microwave_energy_receiver_control")));
        //MEGA_CLEANROOM = registerMetaTileEntity(247, new MetaTileEntityMegaCleanroom(gtqtcoreId("mega_cleanroom")));
        SEISMIC_DETECTOR = registerMetaTileEntity(248, new MetaTileEntitySeismicDetector(gtqtcoreId("seismic_detector")));
        GRAVITY_SEPARATOR = registerMetaTileEntity(249, new MetaTileEntityGravitySeparator(gtqtcoreId("gravity_separator")));
        SMSF = registerMetaTileEntity(250, new MetaTileEntitySMSF(gtqtcoreId("smsf")));
        ADV_ARC_FURNACE = registerMetaTileEntity(251, new MetaTileEntityAdvancedArcFurnace(gtqtcoreId("adv_arc_furnace")));
        NEUTRAL_NETWORK_NEXUS = registerMetaTileEntity(252, new MetaTileEntityNeutralNetworkNexus(gtqtcoreId("neutral_network_nexus")));
        BLAZING_CZ_PULLER = registerMetaTileEntity(253, new MetaTileEntityBlazingCZPuller(gtqtcoreId("blazing_cz_puller")));
        LARGE_CIRCUIT_ASSEMBLY_LINE = registerMetaTileEntity(254, new MetaTileEntityLargeCircuitAssemblyLine(gtqtcoreId("large_circuit_assembly_line")));
        PHOTOLITHOGRAPHY_FACTORY = registerMetaTileEntity(255, new MetaTileEntityPhotolithographyFactory(gtqtcoreId("photolithography_factory")));
        DANGOTE_DISTILLERY = registerMetaTileEntity(256, new MetaTileEntityDangoteDistillery(gtqtcoreId("dangote_distillery")));
        EVAPORATION_POOL = registerMetaTileEntity(257, new MetaTileEntityEvaporationPool(gtqtcoreId("evaporation_pool")));
        ELECTRON_MICROSCOPE = registerMetaTileEntity(258, new MetaTileEntityElectronMicroscope(gtqtcoreId("electron_microscope")));
        GENE_MUTAGENESIS = registerMetaTileEntity(259, new MetaTileEntityGeneMutagenesis(gtqtcoreId("gene_mutagenesis")));
        INDUSTRIAL_INDUCTION_FURNACE = registerMetaTileEntity(260, new MetaTileEntityIndustrialInductionFurnace(gtqtcoreId("industrial_induction_furnace")));
        ENERGY_SUBSTATION = registerMetaTileEntity(261, new MetaTileEntityEnergySubstation(gtqtcoreId("energy_substation")));
        NAQUADAH_FUEL_FACTORY = registerMetaTileEntity(262, new MetaTileEntityNaquadahFuelFactory(gtqtcoreId("naquadah_fuel_factory")));
        PLASMA_CONDENSER = registerMetaTileEntity(263, new MetaTileEntityPlasmaCondenser(gtqtcoreId("plasma_condenser")));
        HEAT_HATCH_EXCHANGE = registerMetaTileEntity(264, new MetaTileEntityHeatHatchExchange(gtqtcoreId("heat_hatch_exchange")));
        LARGE_ELEMENT_DUPLICATOR = registerMetaTileEntity(265, new MetaTileEntityLargeElementDuplicator(gtqtcoreId("large_element_duplicator")));
        LARGE_UU_PRODUCTER = registerMetaTileEntity(266, new MetaTileEntityLargeUUProducter(gtqtcoreId("large_uu_producter")));
        INDUSTRIAL_DRILL = registerMetaTileEntity(267, new MetaTileEntityIndustrialDrill(gtqtcoreId("industrial_drill")));
        LARGE_CATALYTIC_REFORMER = registerMetaTileEntity(268, new MetaTileEntityCatalyticReformer(gtqtcoreId("catalytic_reformer")));
        LARGE_SONICATOR = registerMetaTileEntity(269, new MetaTileEntitySonicator(gtqtcoreId("sonicator")));
        LARGE_HYDRAULIC_FRACKER = registerMetaTileEntity(270, new MetaTileEntityFracker(gtqtcoreId("fracker"), GTValues.ZPM));
        LARGE_NANOSCALE_FABRICATOR = registerMetaTileEntity(271, new MetaTileEntityNanoscaleFabricator(gtqtcoreId("nanoscale_fabricator")));
        LARGE_ROASTER = registerMetaTileEntity(272, new MetaTileEntityBurnerReactor(gtqtcoreId("roaster")));
        LARGE_CRYSTALLIZATION_CRUCIBLE = registerMetaTileEntity(273, new MetaTileEntityCrystallizationCrucible(gtqtcoreId("crystallization_crucible")));
        LARGE_CVD_UNIT = registerMetaTileEntity(274, new MetaTileEntityCVDUnit(gtqtcoreId("cvd_unit")));
        //
        LARGE_CRYOGENIC_REACTOR = registerMetaTileEntity(276, new MetaTileEntityCryoReactor(gtqtcoreId("cryogenic_reactor")));
        LARGE_ION_IMPLANTATOR = registerMetaTileEntity(277, new MetaTileEntityIonImplanter(gtqtcoreId("ion_implantator")));
        LARGE_CZ_PULLER = registerMetaTileEntity(278, new MetaTileEntityCZPuller(gtqtcoreId("cz_puller")));
        LARGE_EX_CVD = registerMetaTileEntity(279, new MetaTileEntityExtremesCVD(gtqtcoreId("ex_cvd")));
        LARGE_CHEMICAL_FACTORY = registerMetaTileEntity(280, new MetaTileEntityChemicalReactor(gtqtcoreId("chemical_factory")));
        LARGE_ULTRAVIOLET_LAMP = registerMetaTileEntity(281, new MetaTileEntityUltravioletLamp(gtqtcoreId("ultraviolet_lamp")));
        LARGE_MIX = registerMetaTileEntity(282, new MetaTileEntityMixer(gtqtcoreId("large_mix")));
        LARGE_PRESSURE_FACTORY = registerMetaTileEntity(283, new MetaTileEntityPressureFactory(gtqtcoreId("large_pressure_factory")));

        //重写设备
        DISTILLATION_TOWER = registerMetaTileEntity(400, new MetaTileEntityDistillationTower(gtqtcoreId("distillation_tower")));
        CRACKER = registerMetaTileEntity(401, new MetaTileEntityCrackingUnit(gtqtcoreId("cracker")));
        PYROLYSE_OVEN = registerMetaTileEntity(402, new MetaTileEntityPyrolyseOven(gtqtcoreId("pyrolyse_oven")));
        VACUUM_FREEZER = registerMetaTileEntity(403, new MetaTileEntityVacuumFreezer(gtqtcoreId("vacuum_freezer")));

        ASSEMBLY_LINE = registerMetaTileEntity(404, new MetaTileEntityAssemblyLine(gtqtcoreId("assembly_line")));
        LARGE_ORE_WASHER = registerMetaTileEntity(405, new MetaTileEntityLargeOreWasher(gtqtcoreId("large_ore_washer")));
        LARGE_THERMAL_CENTRIFUGE = registerMetaTileEntity(406, new MetaTileEntityLargeThermalCentrifuge(gtqtcoreId("large_thermal_centrifuge")));
        LARGE_GRIND = registerMetaTileEntity(407, new MetaTileEntityLargeGrind(gtqtcoreId("large_grind")));
        LARGE_FORGING = registerMetaTileEntity(408, new MetaTileEntityLargeForging(gtqtcoreId("large_forging")));
        LARGE_EXTRACTOR = registerMetaTileEntity(409, new MetaTileEntityLargeExtractor(gtqtcoreId("large_extractor")));
        LARGE_MIXER = registerMetaTileEntity(410, new MetaTileEntityLargeMixer(gtqtcoreId("large_mixer")));
        LARGE_DESULPHURIZATION = registerMetaTileEntity(411, new MetaTileEntityLargeDesulphurization(gtqtcoreId("large_desulphurization")));
        LARGE_FLUIDIZED_BED = registerMetaTileEntity(412, new MetaTileEntityLargeFluidizedBed(gtqtcoreId("large_fluidized_bed")));
        LARGE_CHEMICAL_REACTOR = registerMetaTileEntity(413, new MetaTileEntityLargeChemicalReactor(gtqtcoreId("large_chemical_reactor")));
        LARGE_BENDER = registerMetaTileEntity(414, new MetaTileEntityLargeBender(gtqtcoreId("large_bender")));
        LARGE_CUTTER = registerMetaTileEntity(415, new MetaTileEntityLargeCutter(gtqtcoreId("large_cutter")));
        LARGE_EXTRUDER = registerMetaTileEntity(416, new MetaTileEntityLargeExtruder(gtqtcoreId("large_extruder")));
        LARGE_WIREMILL = registerMetaTileEntity(417, new MetaTileEntityLargeWireMill(gtqtcoreId("large_wiremill")));

        LV_GENERATOR_ARRAY = registerMetaTileEntity(420, new MetaTileEntityGeneratorArray(gtqtcoreId("lv_generator_array"), 1));
        MV_GENERATOR_ARRAY = registerMetaTileEntity(421, new MetaTileEntityGeneratorArray(gtqtcoreId("mv_generator_array"), 2));
        HV_GENERATOR_ARRAY = registerMetaTileEntity(422, new MetaTileEntityGeneratorArray(gtqtcoreId("hv_generator_array"), 3));
        EV_GENERATOR_ARRAY = registerMetaTileEntity(423, new MetaTileEntityGeneratorArray(gtqtcoreId("ev_generator_array"), 4));
        IV_GENERATOR_ARRAY = registerMetaTileEntity(424, new MetaTileEntityGeneratorArray(gtqtcoreId("iv_generator_array"), 5));

        LV_PROCESSING_ARRAY = registerMetaTileEntity(425, new MetaTileEntityProcessingArray(gtqtcoreId("lv_processing_array"), 1));
        MV_PROCESSING_ARRAY = registerMetaTileEntity(426, new MetaTileEntityProcessingArray(gtqtcoreId("mv_processing_array"), 2));
        HV_PROCESSING_ARRAY = registerMetaTileEntity(427, new MetaTileEntityProcessingArray(gtqtcoreId("hv_processing_array"), 3));

        //巨型设备
        //高级聚变
        ADVANCED_FUSION_REACTOR[0] = registerMetaTileEntity(500, new MetaTileEntityAdvancedFusionReactor(gtqtcoreId("fusion_reactor.uhv"), UHV));
        ADVANCED_FUSION_REACTOR[1] = registerMetaTileEntity(501, new MetaTileEntityAdvancedFusionReactor(gtqtcoreId("fusion_reactor.uev"), UEV));
        ADVANCED_FUSION_REACTOR[2] = registerMetaTileEntity(502, new MetaTileEntityAdvancedFusionReactor(gtqtcoreId("fusion_reactor.uiv"), UIV));

        //压缩聚变
        COMPRESSED_FUSION_REACTOR[0] = registerMetaTileEntity(503, new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor.luv"), LuV, MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_CASING), MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL), MetaBlocks.FRAMES.get(Materials.Naquadria).getBlock(Materials.Naquadria)));
        COMPRESSED_FUSION_REACTOR[1] = registerMetaTileEntity(504, new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor.zpm"), ZPM, MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_CASING_MK2), MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_COIL), MetaBlocks.FRAMES.get(Materials.Tritanium).getBlock(Materials.Tritanium)));
        COMPRESSED_FUSION_REACTOR[2] = registerMetaTileEntity(505, new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor.uv"), UV, MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_CASING_MK3), MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_COIL), MetaBlocks.FRAMES.get(Neutronium).getBlock(Neutronium)));
        COMPRESSED_FUSION_REACTOR[3] = registerMetaTileEntity(506, new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor.uhv"), UHV, GTQTMetaBlocks.blockCompressedFusionReactor.getState(CASING_FUSION_MKIV), GTQTMetaBlocks.blockCompressedFusionReactor.getState(FUSION_COIL_MKII), MetaBlocks.FRAMES.get(Infinity).getBlock(Infinity)));
        COMPRESSED_FUSION_REACTOR[4] = registerMetaTileEntity(507, new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor.uev"), UEV, GTQTMetaBlocks.blockCompressedFusionReactor.getState(CASING_FUSION_MKV), GTQTMetaBlocks.blockCompressedFusionReactor.getState(FUSION_COIL_MKIII), MetaBlocks.FRAMES.get(Hypogen).getBlock(Hypogen)));
        COMPRESSED_FUSION_REACTOR[5] = registerMetaTileEntity(508, new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor.uiv"), UIV, GTQTMetaBlocks.blockCompressedFusionReactor.getState(CASING_FUSION_MKVI), GTQTMetaBlocks.blockCompressedFusionReactor.getState(FUSION_COIL_MKIV), MetaBlocks.FRAMES.get(Spacetime).getBlock(Spacetime)));

        //高级装配线
        ADVANCED_ASSEMBLY_LINE = registerMetaTileEntity(510, new MetaTileEntityAdvancedAssemblyLine(gtqtcoreId("advanced_assembly_line")));
        COMPONENT_ASSEMBLY_LINE = registerMetaTileEntity(511, new MetaTileEntityComponentAssemblyLine(gtqtcoreId("component_assembly_line")));

        LARGE_PROCESSING_FACTORY = registerMetaTileEntity(512, new MetaTileEntityLargeProcessingFactory(gtqtcoreId("large_processing_factory")));

        HUGE_BLAST_FURANCE = registerMetaTileEntity(514, new MetaTileEntityHugeBlastFurnace(gtqtcoreId("huge_blast_furnace")));
        HUGE_CRACKING_UNIT = registerMetaTileEntity(515, new MetaTileEntityHugeCrackingUnit(gtqtcoreId("huge_cracking_unit")));
        HUGE_CHEMICAL_REACTOR = registerMetaTileEntity(516, new MetaTileEntityHugeChemicalReactor(gtqtcoreId("huge_chemical_reactor")));

        ANTIMATTER_FORGE = registerMetaTileEntity(520, new MetaTileEntityAntimatterForge(gtqtcoreId("antimatter_forge")));
        ANTIMATTER_GENERATOR = registerMetaTileEntity(521, new MetaTileEntityAntimatterGenerator(gtqtcoreId("antimatter_generator")));


        //star
        DIMENSIONALLY_TRANSCENDENT_PLASMA_FORGE = registerMetaTileEntity(600, new MetaTileEntityDimensionallyTranscendentPlasmaForge(gtqtcoreId("dimensionally_transcendent_plasma_forge")));
        DIMENSIONAL_MIXER = registerMetaTileEntity(601, new MetaTileEntityDimensionalMixer(gtqtcoreId("dimensional_mixer")));

        if (GTQTCoreConfig.MachineSwitch.LastSwitch)
            DIMENSIONAL_PLASMA_FURNACE = registerMetaTileEntity(602, new MetaTileEntityDimensionallyPlasmFurnace(gtqtcoreId("dimensional_plasma_furnace")));
        if (GTQTCoreConfig.MachineSwitch.LastSwitch)
            DIMENSIONAL_BIOMIMETIC_FACTORY = registerMetaTileEntity(603, new MetaTileEntityDimensionallyBiomimeticFactory(gtqtcoreId("dimensional_biomimetic_factory")));
        if (GTQTCoreConfig.MachineSwitch.EndGameSwitch)
            NICOLL_DYSON_BEAMER = registerMetaTileEntity(604, new MetaTileEntityNicollDysonBeamer(gtqtcoreId("nicoll_dyson_beamer")));
        if (GTQTCoreConfig.MachineSwitch.EndGameSwitch)
            SUPRACHRONAL_NEUTRONIUM_FORGE = registerMetaTileEntity(605, new MetaTileEntitySuprachronalNeutroniumForge(gtqtcoreId("suprachronal_neutronium_forge")));

        //资源产出
        GAS_COLLECTOR = registerMetaTileEntity(700, new MetaTileEntityGasCollector(gtqtcoreId("gas_collector")));
        INDUSTRY_WATER_PUMP = registerMetaTileEntity(701, new MetaTileEntityIndustryWaterPump(gtqtcoreId("industry_water_pump")));

        EXTREME_LARGE_MINER = registerMetaTileEntity(702, new MetaTileEntityAdvancedLargeMiner(gtqtcoreId("large_miner.zpm"), 7, 1, 9, 7, Materials.Naquadah, 64));
        ULTIMATE_LARGE_MINER = registerMetaTileEntity(703, new MetaTileEntityAdvancedLargeMiner(gtqtcoreId("large_miner.uv"), 8, 1, 18, 8, Orichalcum, 128));
        INFINITY_LARGE_MINER = registerMetaTileEntity(704, new MetaTileEntityAdvancedLargeMiner(gtqtcoreId("large_miner.uhv"), 9, 1, 36, 9, Adamantium, 256));

        ADVANCED_FLUID_DRILL_RIG = registerMetaTileEntity(705, new MetaTileEntityAdvancedFluidDrill(gtqtcoreId("fluid_drill_rig.iv"), 5));
        EXTREME_FLUID_DRILL_RIG = registerMetaTileEntity(706, new MetaTileEntityAdvancedFluidDrill(gtqtcoreId("fluid_drill_rig.luv"), 6));
        ULTIMATE_FLUID_DRILL_RIG = registerMetaTileEntity(707, new MetaTileEntityAdvancedFluidDrill(gtqtcoreId("fluid_drill_rig.zpm"), 7));
        INFINITY_FLUID_DRILL_RIG = registerMetaTileEntity(708, new MetaTileEntityAdvancedFluidDrill(gtqtcoreId("fluid_drill_rig.uv"), 8));

        VOID_MINER[0] = registerMetaTileEntity(710, new MetaTileEntityVoidMiner(gtqtcoreId("void_miner.luv"), LuV, 9000));
        VOID_MINER[1] = registerMetaTileEntity(711, new MetaTileEntityVoidMiner(gtqtcoreId("void_miner.zpm"), ZPM, 16000));
        VOID_MINER[2] = registerMetaTileEntity(712, new MetaTileEntityVoidMiner(gtqtcoreId("void_miner.uv"), UV, 40000));

        //blocksResearchSystem
        KQCC = registerMetaTileEntity(800, new MetaTileEntityResearchSystemControlCenter(gtqtcoreId("kqcc")));
        KQNS = registerMetaTileEntity(801, new MetaTileEntityResearchSystemNetworkSwitch(gtqtcoreId("kqns")));
        ADV_KQCC = registerMetaTileEntity(802, new MetaTileEntityAdvanceKQCC(gtqtcoreId("adv_kqcc")));
        MINI_DATE_BANK = registerMetaTileEntity(803, new MetaTileEntityMiniDataBank(gtqtcoreId("mini_date_bank")));
        ADV_NETWORK_SWITCH = registerMetaTileEntity(804, new MetaTileEntityAdvanceNetworkSwitch(gtqtcoreId("adv_network_switch")));
        ADV_DATE_BANK = registerMetaTileEntity(805, new MetaTileEntityAdvanceDataBank(gtqtcoreId("adv_date_bank")));
        RESEARCH_SYSTEM_NETWORK = registerMetaTileEntity(806, new MetaTileEntityResearchSystemNetWork(gtqtcoreId("research_system_network")));

        //GCYS

        //pressure
        STEAM_EJECTOR[0] = registerMetaTileEntity(850, new MetaTileEntitySteamEjector(gtqtcoreId("steam_ejector.bronze"), false));
        STEAM_EJECTOR[1] = registerMetaTileEntity(851, new MetaTileEntitySteamEjector(gtqtcoreId("steam_ejector.steel"), true));
        CREATIVE_PRESSURE = registerMetaTileEntity(852, new MetaTileEntityCreativePressurePump(gtqtcoreId("infinite_pressure_pump")));
        PRESSURE_TANK[0] = registerMetaTileEntity(853, new MetaTileEntityPressureTank(gtqtcoreId("pressure_tank.i"), 1));
        PRESSURE_TANK[1] = registerMetaTileEntity(854, new MetaTileEntityPressureTank(gtqtcoreId("pressure_tank.ii"), 2));
        PRESSURE_TANK[2] = registerMetaTileEntity(855, new MetaTileEntityPressureTank(gtqtcoreId("pressure_tank.iii"), 3));

        SUBSONIC_AXIAL_COMPRESSOR = registerMetaTileEntity(860, new MetaTileEntityAxialCompressor(gtqtcoreId("axial_compressor.subsonic"), GTValues.EV, increaseDetailP[6], getPressureChange(4, true)));
        SUPERSONIC_AXIAL_COMPRESSOR = registerMetaTileEntity(861, new MetaTileEntityAxialCompressor(gtqtcoreId("axial_compressor.supersonic"), GTValues.LuV, increaseDetailP[8], getPressureChange(6, true)));
        LOW_POWER_TURBOMOLECULAR_PUMP = registerMetaTileEntity(862, new MetaTileEntityTurbomolecularPump(gtqtcoreId("turbomolecular_pump.low_power"), GTValues.EV, decreaseDetailP[6], getPressureChange(4, true)));
        HIGH_POWER_TURBOMOLECULAR_PUMP = registerMetaTileEntity(863, new MetaTileEntityTurbomolecularPump(gtqtcoreId("turbomolecular_pump.high_power"), GTValues.LuV, decreaseDetailP[8], getPressureChange(6, true)));

        for (int i = 0; i < 9; i++) {
            int id = 870 + i;
            String name = String.format("pressure_pump.%s", GTValues.VN[i + 1]);
            PRESSURE_PUMP[i] = registerMetaTileEntity(id, new MetaTileEntityPressureBooster(gtqtcoreId(name), i + 1, false));
        }
        for (int i = 0; i < 9; i++) {
            int id = 880 + i;
            String name = String.format("pressure_compressor.%s", GTValues.VN[i + 1]);
            PRESSURE_COMPRESSOR[i] = registerMetaTileEntity(id, new MetaTileEntityPressureBooster(gtqtcoreId(name), i + 1, true));
        }

        GAS_TANK[0] = registerMetaTileEntity(890, new MetaTileEntityGasTank(gtqtcoreId("gas_tank.bronze"), Materials.Bronze, 0, 5));
        GAS_TANK[1] = registerMetaTileEntity(891, new MetaTileEntityGasTank(gtqtcoreId("gas_tank.steel"), Materials.Steel, 1, 10));
        GAS_TANK[2] = registerMetaTileEntity(892, new MetaTileEntityGasTank(gtqtcoreId("gas_tank.aluminium"), Materials.Aluminium, 2, 15));
        GAS_TANK[3] = registerMetaTileEntity(893, new MetaTileEntityGasTank(gtqtcoreId("gas_tank.stainlesssteel"), StainlessSteel, 3, 20));
        GAS_TANK[4] = registerMetaTileEntity(894, new MetaTileEntityGasTank(gtqtcoreId("gas_tank.titanium"), Titanium, 4, 25));
        GAS_TANK[5] = registerMetaTileEntity(895, new MetaTileEntityGasTank(gtqtcoreId("gas_tank.tungstensteel"), TungstenSteel, 5, 30));
        GAS_TANK[6] = registerMetaTileEntity(896, new MetaTileEntityGasTank(gtqtcoreId("gas_tank.hssg"), HSSG, 6, 35));
        GAS_TANK[7] = registerMetaTileEntity(897, new MetaTileEntityGasTank(gtqtcoreId("gas_tank.naquadah"), Naquadah, 7, 40));
        GAS_TANK[8] = registerMetaTileEntity(898, new MetaTileEntityGasTank(gtqtcoreId("gas_tank.duranium"), Duranium, 8, 45));
        GAS_TANK[9] = registerMetaTileEntity(899, new MetaTileEntityGasTank(gtqtcoreId("gas_tank.neutronium"), Neutronium, 9, 50));
        //Appeng 4000

        // ID 14001-14010: Drums
        INV_BRIDGE = registerMetaTileEntity(900, new MetaTileEntityBridge(gtqtcoreId("bridge.inv"), cap -> cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, GTQTTextures.INV_BRIDGE, Materials.Steel));
        TANK_BRIDGE = registerMetaTileEntity(901, new MetaTileEntityBridge(gtqtcoreId("bridge.tank"), cap -> cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, GTQTTextures.TANK_BRIDGE, Materials.Steel));
        INV_TANK_BRIDGE = registerMetaTileEntity(902, new MetaTileEntityBridge(gtqtcoreId("bridge.inv_tank"), cap -> cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, GTQTTextures.INV_TANK_BRIDGE, Materials.Steel));
        UNIVERSAL_BRIDGE = registerMetaTileEntity(903, new MetaTileEntityBridge(gtqtcoreId("bridge.universal"), cap -> true, GTQTTextures.UNIVERSAL_BRIDGE, Materials.Aluminium));

        INV_EXTENDER = registerMetaTileEntity(904, new MetaTileEntityExtender(gtqtcoreId("extender.inv"), cap -> cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, GTQTTextures.INV_EXTENDER, Materials.Steel));
        TANK_EXTENDER = registerMetaTileEntity(905, new MetaTileEntityExtender(gtqtcoreId("extender.tank"), cap -> cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, GTQTTextures.TANK_EXTENDER, Materials.Steel));
        INV_TANK_EXTENDER = registerMetaTileEntity(906, new MetaTileEntityExtender(gtqtcoreId("extender.inv_tank"), cap -> cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, GTQTTextures.INV_TANK_EXTENDER, Materials.Steel));
        UNIVERSAL_EXTENDER = registerMetaTileEntity(907, new MetaTileEntityExtender(gtqtcoreId("extender.universal"), cap -> true, GTQTTextures.UNIVERSAL_EXTENDER, Materials.Aluminium));

        PE_CAN = registerMetaTileEntity(910, new MetaTileEntityPlasticCan(gtqtcoreId("drum.pe"), Materials.Polyethylene, 64_000));
        PTFE_CAN = registerMetaTileEntity(911, new MetaTileEntityPlasticCan(gtqtcoreId("drum.ptfe"), Materials.Polytetrafluoroethylene, 128_000));
        ZYLON_CAN = registerMetaTileEntity(912, new MetaTileEntityPlasticCan(gtqtcoreId("drum.zylon"), Zylon, 512_000));
        PBI_CAN = registerMetaTileEntity(913, new MetaTileEntityPlasticCan(gtqtcoreId("drum.pbi"), Materials.Polybenzimidazole, 1024_000));
        KEVLAR_CAN = registerMetaTileEntity(914, new MetaTileEntityPlasticCan(gtqtcoreId("drum.kevlar"), Kevlar, 2048_000));

        //小机器
        STEAM_VACUUM_CHAMBER[0] = registerMetaTileEntity(1000, new MetaTileEntitySteamVacuumChamber(gtqtcoreId("vacuum_chamber.bronze"), false));
        STEAM_VACUUM_CHAMBER[1] = registerMetaTileEntity(1001, new MetaTileEntitySteamVacuumChamber(gtqtcoreId("vacuum_chamber.steel"), true));
        registerSimpleSteamMetaTileEntity(STEAM_SPINNER, 1002, "spinner", GTQTcoreRecipeMaps.SPINNER_RECIPES, COMPRESS, GTQTTextures.SPINNER_OVERL, false);

        registerSimpleMetaTileEntity(SONICATOR, 1010, "sonicator", SONICATION_RECIPES, SONICATOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(ION_IMPLANTER, 1025, "ion_implanter", ION_IMPLANTATOR_RECIPES, ION_IMPLANTER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(CVD_UNIT, 1040, "cvd_unit", CVD_RECIPES, CVD_UNIT_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(FLOTATION_CELL, 1055, "flotation_cell", FLOTATION_RECIPES, FLOTATION_CELL_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(CATALYTIC_REFORMER, 1070, "catalytic_reformer", CATALYTIC_REFORMER_RECIPES, CATALYTIC_REFORMER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);

        registerSimpleMetaTileEntity(CONDENSER, 1085, "condenser", CONDENSER_RECIPES, CONDENSER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.genericGeneratorTankSizeFunction);

        for (int i = 0; i < 13; i++) {
            int id = 1100 + i;
            String name = String.format("burner_reactor.%s", GTValues.VN[i + 1]);
            BURNER_REACTOR[i] = registerMetaTileEntity(id, new MetaTileEntityPressureMachine(gtqtcoreId(name), BURNER_REACTOR_RECIPES, BURNER_REACTOR_OVERLAY, i + 1, true));
        }

        registerSimpleMetaTileEntity(SPINNER, 1115, "spinner", SPINNER_RECIPES, GTQTTextures.SPINNER_OVERL, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(POLYMERIZATION_TANK, 1130, "polymerization_tank", POLYMERIZATION_RECIPES, POLYMERIZATION_TANK_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(LOW_TEMP_ACTIVATOR, 1145, "low_temperature_activator", LOW_TEMP_ACTIVATOR_RECIPES, LOW_TEMP_ACTIVATOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(BIO_REACTOR, 1160, "bio_reactor", GTQTcoreRecipeMaps.BIOLOGICAL_REACTION_RECIPES, GTQTTextures.BIO_REACTOR_OVERLAY, true, GTQTUtil::gtqtId, (tier) -> 16000);
        registerSimpleMetaTileEntity(DEHYDRATOR, 1175, "dehydrator", GTQTcoreRecipeMaps.DRYER_RECIPES, Textures.SIFTER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(FLUID_EXTRACTOR, 1190, "fluid_extractor", GTQTcoreRecipeMaps.FLUID_EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(FLUID_CANNER, 1205, "fluid_canner", GTQTcoreRecipeMaps.FLUID_CANNER_RECIPES, Textures.CANNER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(COMPONENT_ASSEMBLER, 1220, "component_assembler", GTQTcoreRecipeMaps.COMPONENT_ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(LAMINATOR, 1235, "laminator", GTQTcoreRecipeMaps.LAMINATOR_RECIPES, GTQTTextures.LAMINATOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.largeTankSizeFunction);

        for (int i = 0; i < 13; i++) {
            int id = 1250 + i;
            String name = String.format("vacuum_chamber.%s", GTValues.VN[i + 1]);
            VACUUM_CHAMBER[i] = registerMetaTileEntity(id, new MetaTileEntityPressureMachine(gtqtcoreId(name), VACUUM_CHAMBER_RECIPES, Textures.GAS_COLLECTOR_OVERLAY, i + 1, true));
        }

        registerSimpleMetaTileEntity(ULTRAVIOLET_LAMP_CHAMBER, 1265, "ultraviolet_lamp_chamber", GTQTcoreRecipeMaps.ULTRAVIOLET_LAMP_CHAMBER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(PRESSURE_LAMINATOR, 1280, "pressure_laminator", GTQTcoreRecipeMaps.PRESSURE_LAMINATOR_RECIPES, GTQTTextures.LAMINATOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);

        registerSimpleMetaTileEntity(RECYCLE, 1325, "recycle", GTQTcoreRecipeMaps.RECYCLE_RECIPE, Textures.MACERATOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.defaultTankSizeFunction);
        registerSimpleMetaTileEntity(POLISHER, 1340, "polisher", GTQTcoreRecipeMaps.POLISHER_RECIPES, GTQTTextures.POLISHER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(SIMULATOR, 1355, "simulator", GTQTcoreRecipeMaps.SIMULATOR_RECIPES, GTQTTextures.SIMULATOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);

        STEAM_LATEX_COLLECTOR[0] = registerMetaTileEntity(1500, new MetaTileEntitySteamLatexCollector(gtqtcoreId("latex_collector.bronze"), false));
        STEAM_LATEX_COLLECTOR[1] = registerMetaTileEntity(1501, new MetaTileEntitySteamLatexCollector(gtqtcoreId("latex_collector.steel"), true));
        LATEX_COLLECTOR[0] = registerMetaTileEntity(1502, new MetaTileEntityLatexCollector(gtqtcoreId("latex_collector.lv"), 1));
        LATEX_COLLECTOR[1] = registerMetaTileEntity(1503, new MetaTileEntityLatexCollector(gtqtcoreId("latex_collector.mv"), 2));
        LATEX_COLLECTOR[2] = registerMetaTileEntity(1504, new MetaTileEntityLatexCollector(gtqtcoreId("latex_collector.hv"), 3));
        LATEX_COLLECTOR[3] = registerMetaTileEntity(1505, new MetaTileEntityLatexCollector(gtqtcoreId("latex_collector.ev"), 4));
        PARTICLE_ACCELERATOR_IO[0] = registerMetaTileEntity(1506, new MetaTileEntityParticleAcceleratorIO(gtqtcoreId("particle_accelerator_io.lv"), GTQTcoreRecipeMaps.PAC_RECIPES, GTQTTextures.PARTICLE_ACCELERATOR, 1, true));
        PARTICLE_ACCELERATOR_IO[1] = registerMetaTileEntity(1507, new MetaTileEntityParticleAcceleratorIO(gtqtcoreId("particle_accelerator_io.mv"), GTQTcoreRecipeMaps.PAC_RECIPES, GTQTTextures.PARTICLE_ACCELERATOR, 2, true));
        PARTICLE_ACCELERATOR_IO[2] = registerMetaTileEntity(1508, new MetaTileEntityParticleAcceleratorIO(gtqtcoreId("particle_accelerator_io.hv"), GTQTcoreRecipeMaps.PAC_RECIPES, GTQTTextures.PARTICLE_ACCELERATOR, 3, true));
        PARTICLE_ACCELERATOR_IO[3] = registerMetaTileEntity(1509, new MetaTileEntityParticleAcceleratorIO(gtqtcoreId("particle_accelerator_io.ev"), GTQTcoreRecipeMaps.PAC_RECIPES, GTQTTextures.PARTICLE_ACCELERATOR, 4, true));

        //凿子
        AUTO_CHISEL[0] = registerMetaTileEntity(1510, new SimpleMachineMetaTileEntity(gtqtcoreId("auto_chisel.lv"), GTQTcoreRecipeMaps.AUTO_CHISEL_RECIPES, Textures.AUTOCLAVE_OVERLAY, 1, true, GTUtility.genericGeneratorTankSizeFunction));
        AUTO_CHISEL[1] = registerMetaTileEntity(1511, new SimpleMachineMetaTileEntity(gtqtcoreId("auto_chisel.mv"), GTQTcoreRecipeMaps.AUTO_CHISEL_RECIPES, Textures.AUTOCLAVE_OVERLAY, 2, true, GTUtility.genericGeneratorTankSizeFunction));
        AUTO_CHISEL[2] = registerMetaTileEntity(1512, new SimpleMachineMetaTileEntity(gtqtcoreId("auto_chisel.hv"), GTQTcoreRecipeMaps.AUTO_CHISEL_RECIPES, Textures.AUTOCLAVE_OVERLAY, 3, true, GTUtility.genericGeneratorTankSizeFunction));

        RUBBISH_BIN = registerMetaTileEntity(1520, new MetaTileEntityRubbishBin(gtqtcoreId("rubbish_bin")));
        FLUID_RUBBISH_BIN = registerMetaTileEntity(1521, new MetaTileEntityFluidRubbishBin(gtqtcoreId("fluid_rubbish_bin")));
        COMMON_RUBBISH_BIN = registerMetaTileEntity(1522, new MetaTileEntityCommonRubbishBin(gtqtcoreId("common_rubbish_bin")));

        //仓口-1600
        for (int i = 1; i <= GTValues.V.length - 2; i++) {
            String tierName = GTValues.VN[i].toLowerCase();
            MICROWAVE_ENERGY_RECEIVER[i] = registerMetaTileEntity(1600 + i - 1, new MetaTileEntityMicrowaveEnergyReceiver(gtqtcoreId("microwave_energy_receiver." + tierName), i));
            LASER_INPUT[i] = registerMetaTileEntity(1615 + i - 1, new MetaTileHighEnergyLaserHatch(gtqtcoreId("laser.input." + tierName), i, false));
            LASER_OUTPUT[i] = registerMetaTileEntity(1630 + i - 1, new MetaTileHighEnergyLaserHatch(gtqtcoreId("laser.output." + tierName), i, true));
        }

        LASER_BOOSTER[0] = registerMetaTileEntity(1645, new MetaTileLaserBooster(gtqtcoreId("laser_booster." + LuV), LuV));
        LASER_BOOSTER[1] = registerMetaTileEntity(1646, new MetaTileLaserBooster(gtqtcoreId("laser_booster." + ZPM), ZPM));
        LASER_BOOSTER[2] = registerMetaTileEntity(1647, new MetaTileLaserBooster(gtqtcoreId("laser_booster." + UV), UV));
        LASER_BOOSTER[3] = registerMetaTileEntity(1648, new MetaTileLaserBooster(gtqtcoreId("laser_booster." + UHV), UHV));
        LASER_BOOSTER[4] = registerMetaTileEntity(1649, new MetaTileLaserBooster(gtqtcoreId("laser_booster." + UIV), UIV));
        LASER_BOOSTER[5] = registerMetaTileEntity(1650, new MetaTileLaserBooster(gtqtcoreId("laser_booster." + UEV), UEV));

        KQCC_HATCH[0] = registerMetaTileEntity(1651, new MetaTileEntityKQCCPartHatch(gtqtcoreId("kqcc_hatch.ram.1"), "ram", 1));
        KQCC_HATCH[1] = registerMetaTileEntity(1652, new MetaTileEntityKQCCPartHatch(gtqtcoreId("kqcc_hatch.ram.2"), "ram", 2));
        KQCC_HATCH[2] = registerMetaTileEntity(1653, new MetaTileEntityKQCCPartHatch(gtqtcoreId("kqcc_hatch.ram.3"), "ram", 3));

        KQCC_HATCH[3] = registerMetaTileEntity(1654, new MetaTileEntityKQCCPartHatch(gtqtcoreId("kqcc_hatch.cpu.1"), "cpu", 1));
        KQCC_HATCH[4] = registerMetaTileEntity(1655, new MetaTileEntityKQCCPartHatch(gtqtcoreId("kqcc_hatch.cpu.2"), "cpu", 2));
        KQCC_HATCH[5] = registerMetaTileEntity(1656, new MetaTileEntityKQCCPartHatch(gtqtcoreId("kqcc_hatch.cpu.3"), "cpu", 3));

        KQCC_HATCH[6] = registerMetaTileEntity(1657, new MetaTileEntityKQCCPartHatch(gtqtcoreId("kqcc_hatch.gpu.1"), "gpu", 1));
        KQCC_HATCH[7] = registerMetaTileEntity(1658, new MetaTileEntityKQCCPartHatch(gtqtcoreId("kqcc_hatch.gpu.2"), "gpu", 2));
        KQCC_HATCH[8] = registerMetaTileEntity(1659, new MetaTileEntityKQCCPartHatch(gtqtcoreId("kqcc_hatch.gpu.3"), "gpu", 3));

        LOCAL_COMPUTER_HATCH = registerMetaTileEntity(1660, new MetaTileEntityLocalModelComputationHatch(gtqtcoreId("local_computer_hatch")));
        BIO_HATCH = registerMetaTileEntity(1661, new MetaTileEntityBioHatch(gtqtcoreId("bio_hatch")));
        INF_WATER_HATCH = registerMetaTileEntity(1662, new MetaTileInfWaterHatch(gtqtcoreId("infinite_water_hatch")));
        MULTIPART_BUFFER_HATCH = registerMetaTileEntity(1663, new MetaTileEntityBufferHatch(gtqtcoreId("buffer_hatch")));
        MULTIPART_BALL_HATCH = registerMetaTileEntity(1664, new MetaTileEntityMillBallHatch(gtqtcoreId("mill_ball_hatch")));

        AIR_INTAKE_HATCH = registerMetaTileEntity(1665,
                new MetaTileEntityAirIntakeHatch(
                        gtqtcoreId("air_intake_hatch"), IV)
        );
        EXTREME_AIR_INTAKE_HATCH = registerMetaTileEntity(1666,
                new MetaTileEntityAirIntakeHatch(
                        gtqtcoreId("extreme_air_intake_hatch"), ZPM)
        );
        ULTIMATE_AIR_INTAKE_HATCH = registerMetaTileEntity(1667,
                new MetaTileEntityAirIntakeHatch(
                        gtqtcoreId("ultimate_air_intake_hatch"), UHV));

        SINGLE_ITEM_INPUT_BUS = registerMetaTileEntity(1670, new MetaTileEntitySingleItemInputBus(gtqtcoreId("single_item_input_bus")));
        SUPER_INPUT_BUS = registerMetaTileEntity(1671, new MetaTileEntitySuperInputBus(gtqtcoreId("super_input_bus")));
        SINGLE_INPUT_BUS = registerMetaTileEntity(1672, new MetaTileEntitySingleInputBus(gtqtcoreId("single_input_bus")));

        GENERATOR_HATCH = registerMetaTileEntity(1675, new MetaTileEntityGeneratorHatch(gtqtcoreId("generator_hatch"), 3));
        STERILE_CLEANING_MAINTENANCE_HATCH = registerMetaTileEntity(1676, new MetaTileEntitySterileCleaningMaintenanceHatch(gtqtcoreId("maintenance_hatch_sterile_cleanroom_auto")));
        ISO3_CLEANING_MAINTENANCE_HATCH = registerMetaTileEntity(1677, new MetaTileEntityISO3CleaningMaintenanceHatch(gtqtcoreId("maintenance_hatch_iso_3_cleanroom_auto")));
        ISO2_CLEANING_MAINTENANCE_HATCH = registerMetaTileEntity(1678, new MetaTileEntityISO2CleaningMaintenanceHatch(gtqtcoreId("maintenance_hatch_iso_2_cleanroom_auto")));
        ISO1_CLEANING_MAINTENANCE_HATCH = registerMetaTileEntity(1679, new MetaTileEntityISO1CleaningMaintenanceHatch(gtqtcoreId("maintenance_hatch_iso_1_cleanroom_auto")));

        CATALYST_HATCH[0] = registerMetaTileEntity(1680, new MetaTileEntityCatalystHatch(gtqtcoreId("catalyst_hatch.0"), 1));
        CATALYST_HATCH[1] = registerMetaTileEntity(1681, new MetaTileEntityCatalystHatch(gtqtcoreId("catalyst_hatch.1"), 2));
        CATALYST_HATCH[2] = registerMetaTileEntity(1682, new MetaTileEntityCatalystHatch(gtqtcoreId("catalyst_hatch.2"), 3));
        CATALYST_HATCH[3] = registerMetaTileEntity(1683, new MetaTileEntityCatalystHatch(gtqtcoreId("catalyst_hatch.3"), 4));
        PARTICLE_HATCH = registerMetaTileEntity(1685, new MetaTileEntityParticleHatch(gtqtcoreId("particle_hatch")));
        SWARM_HATCH = registerMetaTileEntity(1686, new MetaTileEntityWrapSwarmHatch(gtqtcoreId("swarm_hatch")));


        for (int i = 0; i < 5; i++) {
            int id = 1700 + i;
            String name = String.format("radiation_hatch.%s", GTValues.VN[4 + i]);
            RADIATION_HATCH[i] = registerMetaTileEntity(id, new MetaTileEntityRadiationHatch(gtqtcoreId(name), 4 + i));
        }

        for (int i = 0; i < 5; i++) {
            int id = 1705 + i;
            String name = String.format("electrode_hatch.%s", GTValues.VN[i + 1]);
            ELECTRODE_HATCH[i] = registerMetaTileEntity(id, new MetaTileEntityElectrodeHatch(gtqtcoreId(name), i + 1));
        }

        for (int i = 0; i < 5; i++) {
            int id = 1710 + i;
            String name = String.format("drill_head_hatch.%s", GTValues.VN[i + 1]);
            DRILL_HEAD_HATCH[i] = registerMetaTileEntity(id, new MetaTileEntityDrillHeadHatch(gtqtcoreId(name), i + 1));
        }

        for (int i = 0; i < 5; i++) {
            int id = 1715 + i;
            String name = String.format("heat_hatch.%s", GTValues.VN[i + 1]);
            HEAT_HATCH[i] = registerMetaTileEntity(id, new MetaTileEntityHeatHatch(gtqtcoreId(name), i + 1));
        }
        for (int i = 0; i < 5; i++) {
            int id = 1720 + i;
            String name = String.format("electric_heater.%s", GTValues.VN[i + 1]);
            ELECTRIC_HEATER[i] = registerMetaTileEntity(id, new MetaTileEntityElectricHeater(gtqtcoreId(name), i + 1));
        }

        for (int i = 0; i < 13; i++) {
            String voltageName = VN[i + 1].toLowerCase();
            REINFORCED_ROTOR_HOLDER[i] = registerMetaTileEntity(1730 + i, new MetaTileEntityReinforcedRotorHolder(gtqtcoreId("reinforced_rotor_holder." + voltageName), i + 1));
        }
        for (int i = 0; i < 14; i++) {
            double min = GCYSValues.decreaseDetailP[i];
            double max = GCYSValues.increaseDetailP[i];
            PRESSURE_HATCH[i] = registerMetaTileEntity(1745 + i, new MetaTileEntityPressureHatch(gtqtcoreId(String.format("pressure_hatch.%s", VN[i].toLowerCase())), i, min, max));
        }

        POWER_SUPPLY_HATCH_BASIC = registerMetaTileEntity(1800, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.basic"), "frame", 1));
        POWER_SUPPLY_HATCH_BATTLE[0] = registerMetaTileEntity(1801, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.battle.1"), "battle", 1));
        POWER_SUPPLY_HATCH_BATTLE[1] = registerMetaTileEntity(1802, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.battle.2"), "battle", 2));
        POWER_SUPPLY_HATCH_BATTLE[2] = registerMetaTileEntity(1803, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.battle.3"), "battle", 3));
        POWER_SUPPLY_HATCH_BATTLE[3] = registerMetaTileEntity(1804, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.battle.4"), "battle", 4));
        POWER_SUPPLY_HATCH_BATTLE[4] = registerMetaTileEntity(1805, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.battle.5"), "battle", 5));
        POWER_SUPPLY_HATCH_SUPPLY[0] = registerMetaTileEntity(1806, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.supply.1"), "supply", 1));
        POWER_SUPPLY_HATCH_SUPPLY[1] = registerMetaTileEntity(1807, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.supply.2"), "supply", 2));
        POWER_SUPPLY_HATCH_SUPPLY[2] = registerMetaTileEntity(1808, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.supply.3"), "supply", 3));
        POWER_SUPPLY_HATCH_SUPPLY[3] = registerMetaTileEntity(1809, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.supply.4"), "supply", 4));
        POWER_SUPPLY_HATCH_SUPPLY[4] = registerMetaTileEntity(1810, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.supply.5"), "supply", 5));
        POWER_SUPPLY_HATCH_SUPPLY[5] = registerMetaTileEntity(1811, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.supply.6"), "supply", 6));
        POWER_SUPPLY_HATCH_SUPPLY[6] = registerMetaTileEntity(1812, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.supply.7"), "supply", 7));
        POWER_SUPPLY_HATCH_SUPPLY[7] = registerMetaTileEntity(1813, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.supply.8"), "supply", 8));
        POWER_SUPPLY_HATCH_SUPPLY[8] = registerMetaTileEntity(1814, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.supply.9"), "supply", 9));
        POWER_SUPPLY_HATCH_SUPPLY[9] = registerMetaTileEntity(1815, new MetaTileEntityPowerSupplyHatch(gtqtcoreId("power_supply.supply.10"), "supply", 10));

        for (int i = 0; i < 14; i++) {
            String voltageName = VN[i + 1].toLowerCase();
            CREATIVE_ENERGY_HATCHES[i] = registerMetaTileEntity(1900 + i, new MetaTileEntityCreativeEnergyHatch(gtqtcoreId("creative_energy_hatch." + voltageName), i + 1));
        }

    }
}