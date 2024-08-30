package keqing.gtqtcore.common.metatileentities;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SimpleGeneratorMetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockTurbineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.common.metatileentities.multi.electric.generator.MetaTileEntityLargeTurbine;
import gregtech.common.metatileentities.multi.multiblockpart.*;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.metatileentities.multi.generators.*;
import keqing.gtqtcore.common.metatileentities.multi.generators.Tide.MetaTileEntityTideControl;
import keqing.gtqtcore.common.metatileentities.multi.generators.Tide.MetaTileEntityTideUnit;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.core.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.gcys.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.huge.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.kqcc.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.overwrite.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityCryogenicFreezer;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.star.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.steam.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblockpart.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblockpart.MetaTileEntityDataAccessHatch;
import keqing.gtqtcore.common.metatileentities.single.electric.MetaTileEntityBathCondenser;
import keqing.gtqtcore.common.metatileentities.single.electric.MetaTileEntityLatexCollector;
import keqing.gtqtcore.common.metatileentities.single.electric.MetaTileEntityParticleAcceleratorIO;
import keqing.gtqtcore.common.metatileentities.single.steam.MetaTileEntitySteamLatexCollector;
import keqing.gtqtcore.common.metatileentities.storage.MetaTileEntityCommonRubbishBin;
import keqing.gtqtcore.common.metatileentities.storage.MetaTileEntityFluidRubbishBin;
import keqing.gtqtcore.common.metatileentities.storage.MetaTileEntityMultiblockTank;
import keqing.gtqtcore.common.metatileentities.storage.MetaTileEntityRubbishBin;
import keqing.gtqtcore.GTQTCoreConfig;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;
import static keqing.gtqtcore.api.GTQTValue.gtqtcoreId;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.utils.GTQTUtil.genericGeneratorTankSizeFunctionPlus;
import static keqing.gtqtcore.common.block.blocks.GTQTCompressedFusionReactor.CasingType.*;

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
    ////////////////////////////io
    /* ---------------------------------------------------------------- Multiblock Parts ---------------------------------------------------------------- */
    public static final MetaTileEntityAdvancedEnergyHatch[] INPUT_ENERGY_HATCH_4A = new MetaTileEntityAdvancedEnergyHatch[4];
    public static final MetaTileEntityAdvancedEnergyHatch[] INPUT_ENERGY_HATCH_16A = new MetaTileEntityAdvancedEnergyHatch[4];
    public static final MetaTileEntityAdvancedEnergyHatch[] OUTPUT_ENERGY_HATCH_4A = new MetaTileEntityAdvancedEnergyHatch[7];
    public static final MetaTileEntityAdvancedEnergyHatch[] OUTPUT_ENERGY_HATCH_16A = new MetaTileEntityAdvancedEnergyHatch[8];
    public static final MetaTileEntityAdvancedSubstationEnergyHatch[] SUBSTATION_INPUT_ENERGY_HATCH = new MetaTileEntityAdvancedSubstationEnergyHatch[4];
    public static final MetaTileEntityAdvancedSubstationEnergyHatch[] SUBSTATION_OUTPUT_ENERGY_HATCH = new MetaTileEntityAdvancedSubstationEnergyHatch[8];
    public static final MetaTileEntityAdvancedLaserHatch[] LASER_INPUT_HATCH_16384A = new MetaTileEntityAdvancedLaserHatch[14];
    public static final MetaTileEntityAdvancedLaserHatch[] LASER_INPUT_HATCH_65536A = new MetaTileEntityAdvancedLaserHatch[14];
    public static final MetaTileEntityAdvancedLaserHatch[] LASER_INPUT_HATCH_262144A = new MetaTileEntityAdvancedLaserHatch[14];
    public static final MetaTileEntityAdvancedLaserHatch[] LASER_INPUT_HATCH_1048576A = new MetaTileEntityAdvancedLaserHatch[14];
    public static final MetaTileEntityAdvancedLaserHatch[] LASER_OUTPUT_HATCH_16384A = new MetaTileEntityAdvancedLaserHatch[14];
    public static final MetaTileEntityAdvancedLaserHatch[] LASER_OUTPUT_HATCH_65536A = new MetaTileEntityAdvancedLaserHatch[14];
    public static final MetaTileEntityAdvancedLaserHatch[] LASER_OUTPUT_HATCH_262144A = new MetaTileEntityAdvancedLaserHatch[14];
    public static final MetaTileEntityAdvancedLaserHatch[] LASER_OUTPUT_HATCH_1048576A = new MetaTileEntityAdvancedLaserHatch[14];
    public static final MetaTileEntityAdvancedFluidHatch[] IMPORT_FLUID_HATCH = new MetaTileEntityAdvancedFluidHatch[4];
    public static final MetaTileEntityAdvancedFluidHatch[] EXPORT_FLUID_HATCH = new MetaTileEntityAdvancedFluidHatch[4];
    public static final MetaTileEntityAdvancedItemBus[] IMPORT_ITEM_HATCH = new MetaTileEntityAdvancedItemBus[4];
    public static final MetaTileEntityAdvancedItemBus[] EXPORT_ITEM_HATCH = new MetaTileEntityAdvancedItemBus[4];
    public static final MetaTileEntityAdvancedMultiFluidHatch[] QUADRUPLE_IMPORT_FLUID_HATCH = new MetaTileEntityAdvancedMultiFluidHatch[4];
    public static final MetaTileEntityAdvancedMultiFluidHatch[] QUADRUPLE_EXPORT_FLUID_HATCH = new MetaTileEntityAdvancedMultiFluidHatch[4];
    public static final MetaTileEntityAdvancedMultiFluidHatch[] NONUPLE_IMPORT_FLUID_HATCH = new MetaTileEntityAdvancedMultiFluidHatch[4];
    public static final MetaTileEntityAdvancedMultiFluidHatch[] NONUPLE_EXPORT_FLUID_HATCH = new MetaTileEntityAdvancedMultiFluidHatch[4];





    //////////////////////////
    public static MetaTileEntityOilPool OIL_POOL;
    public static MetaTileEntityAlgaeFarm ALGAE_FARM;
    public static MetaTileEntityComponentAssemblyLine COMPONENT_ASSEMBLY_LINE;
    public static MetaTileEntityBufferHatch MULTIPART_BUFFER_HATCH;
    public static MetaTileEntityBlazingBlastFurnace BLAZING_BLAST_FURNACE ;
    public static MetaTileEntityBlazingCZPuller BLAZING_CZ_PULLER ;
    public static MetaTileEntityHugeChemicalReactor HUGE_CHEMICAL_REACTOR;
    public static MetaTileEntityIntegratedMiningDivision INTEGRATED_MINING_DIVISION;
    public static MetaTileEntityHugeMacerator HUGE_MACERATOR;
    public static MetaTileEntityHugeAlloyBlastSmelter HUGE_ALLOY_BLAST_FURANCE;
    public static MetaTileEntityLagerHeatExchanger LAGER_HEAT_EXCHANGER;
    public static MetaTileEntitySmallHeatExchanger SMALL_HEAT_EXCHANGER;
    public static MetaTileEntityEvaporationPool EVAPORATION_POOL;
    public static MetaTileEntityElectronMicroscope ELECTRON_MICROSCOPE;
    public static MetaTileEntityHugeBlastFurnace HUGE_BLAST_FURANCE;
    public static final MetaTileEntityCreativeEnergyHatch[] CREATIVE_ENERGY_HATCHES = new MetaTileEntityCreativeEnergyHatch[GTValues.V.length];
    public static final MetaTileEntityMicrowaveEnergyReceiver[] MICROWAVE_ENERGY_RECEIVER = new MetaTileEntityMicrowaveEnergyReceiver[12];
    public static MetaTileInfWaterHatch INF_WATER_HATCH;
    public static MetaTileEntityLightningRod[] LIGHTNING_ROD = new MetaTileEntityLightningRod[3];
    public static MetaTileEntityDangoteDistillery DANGOTE_DISTILLERY;
    public static MetaTileEntitykeQingNet KeQing_NET;
    public static MetaTileEntityProcessingArray LV_PROCESSING_ARRAY;
    public static MetaTileEntityProcessingArray MV_PROCESSING_ARRAY;
    public static MetaTileEntityProcessingArray HV_PROCESSING_ARRAY;
    public static MetaTileEntityMachineHatch HV_MACHINE_HATCH;
    public static MetaTileEntityDistillationTower DISTILLATION_TOWER;
    public static final SimpleGeneratorMetaTileEntity[] NAQUADAH_REACTOR = new SimpleGeneratorMetaTileEntity[3];
    public static final SimpleGeneratorMetaTileEntity[] ROCKET_ENGINE = new SimpleGeneratorMetaTileEntity[3];
    public static MetaTileEntityTurbineCombustionChamber HUGE_TURBINE_COMBUSTION_CHAMBER;
    public static MetaTileEntityRocket ROCKET;
    public static MetaTileEntityIndustryWaterPump INDUSTRY_WATER_PUMP;
    public static MetaTileEntityKQCC KQCC;
    public static MetaTileEntityGantryCrane GANTRY_CRANE;

    public static MetaTileEntityPReactor P_REACTOR;
    public static MetaTileEntityBReactor B_REACTOR;
    public static MetaTileEntityHugeDistillationTower HUGE_DISTILLATION_TOWER;
    public static MetaTileEntityHugeVacuum HUGE_VACUUM;
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

    public static MetaTileEntityStepper STEPPER;
    public static MetaTileEntitySeismicDetector SEISMIC_DETECTOR;

    public static MetaTileEntityTideControl TIDE_CONTROL;
    public static MetaTileEntityTideUnit TIDE_UNIT;

    public static MetaTileEntityHugeCrackingUnit HUGE_CRACKING_UNIT;
    public static MetaTileEntityHugeMiner BASIC_HUGE_MINER;
    public static MetaTileEntityGreenhousePlus GREEN_HOUSE_PLUS;
    public static MetaTileEntityHugeMiner HUGE_MINER;
    public static MetaTileEntityDataAccessHatch EDATA_ACCESS_HATCH;
    public static MetaTileEntityDataAccessHatch FDATA_ACCESS_HATCH;
    public static MetaTileEntityDissolutionTank DISSOLUTION_TANK;
    public static MetaTileEntityHugeMiner ADVANCED_HUGE_MINER;
    public static MetaTileEntityGasCollector GAS_COLLECTOR;
    public static MetaTileEntityHyperReactorMkI HYPER_REACTOR_MKI;
    public static MetaTileEntityHyperReactorMkII HYPER_REACTOR_MKII;
    public static MetaTileEntityHyperReactorMkIII HYPER_REACTOR_MKIII;
    public static MetaTileEntityFracturing BASIC_FLUID_DRILLING_RIG;
    public static MetaTileEntitySepticTank SEPTIC_TANK;
    public static MetaTileEntityLargeCircuitAssemblyLine LARGE_CIRCUIT_ASSEMBLY_LINE;
    public static MetaTileEntityIsaMill ISA_MILL;
    public static MetaTileEntityPCBFactory PCB_FACTORY;
    public static MetaTileEntityElectricArcFurnace ELECTRIC_ARC_FURNACE;
    public static MetaTileEntityMultiblockTank[] TANK = new MetaTileEntityMultiblockTank[10];

    public static MetaTileEntitySaltField SALT_FLIED;
    public static MetaTileEntityCommonRubbishBin COMMON_RUBBISH_BIN;
    public static SimpleMachineMetaTileEntity[] COMPONENT_ASSEMBLER = new SimpleMachineMetaTileEntity[GTValues.IV + 1];
    public static SimpleMachineMetaTileEntity[] DECAY_CHAMBER = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static MetaTileEntityElectrobath ELECTROBATH;
    public static MetaTileEntityBiologicalReaction BIOLOGICAL_REACTION;
    public static MetaTileEntityIndustrialFishingPond INDUSTRIAL_FISHING_POND;
    public static MetaTileEntityFracturing FLUID_DRILLING_RIG;
    public static MetaTileEntityFracturing ADVANCED_FLUID_DRILLING_RIG;
    public static MetaTileEntityLargeNaquadahReactor LARGE_NAQUADAH_REACTOR;
    public static MetaTileEntityNeutralNetworkNexus NEUTRAL_NETWORK_NEXUS;
    public static MetaTileEntityCrackingUnit CRACKER;
    public static MetaTileEntitySteamFermentationVat STEAM_VAT;
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
    public static MetaTileEntityADVKQCC ADV_KQCC;
    public static MetaTileEntityGeneMutagenesis GENE_MUTAGENESIS;
    public static SimpleMachineMetaTileEntity[] VACUUM_CHAMBER = new SimpleMachineMetaTileEntity[V.length - 1];
    public static MetaTileEntityGravitySeparator GRAVITY_SEPARATOR;
    public static final SimpleGeneratorMetaTileEntity[] COMBUSTION_GENERATOR = new SimpleGeneratorMetaTileEntity[4];
    public static final SimpleGeneratorMetaTileEntity[] STEAM_TURBINE = new SimpleGeneratorMetaTileEntity[4];
    public static final SimpleGeneratorMetaTileEntity[] GAS_TURBINE = new SimpleGeneratorMetaTileEntity[4];
    public static final SimpleGeneratorMetaTileEntity[] FUEL_CELL_TURBINE = new SimpleGeneratorMetaTileEntity[5];
    public static final SimpleGeneratorMetaTileEntity[] RTG = new SimpleGeneratorMetaTileEntity[3];
    public static MetaTileEntityFrothFlotationTank FROTH_FLOTATION_TANK;
    public static MetaTileEntityOceanPumper OCEAN_PUMPER;
    public static MetaTileEntityClarifier CLARIFIER;
    public static MetaTileEntitySolarPlate SOLAR_PLATE;
    public static MetaTileEntityCokingTower COKING_TOWER;
    public static MetaTileEntityPowerSupply POWER_SUPPLY;
    public static MetaTileEntitySMSF SMSF;
    public static MetaTileEntityADVNetworkSwitch ADV_NETWORK_SWITCH;
    public static MetaTileEntityMiniDataBank MINI_DATE_BANK;
    public static MetaTileEntityADVDataBank ADV_DATE_BANK;
    public static MetaTileEntityRubbishBin RUBBISH_BIN;
    public static MetaTileEntityVacuumDistillationTower VACUUM_DISTILLATION_TOWER;
    public static MetaTileEntitySteamLatexCollector[] STEAM_LATEX_COLLECTOR = new MetaTileEntitySteamLatexCollector[2];
    public static MetaTileEntityLatexCollector[] LATEX_COLLECTOR = new MetaTileEntityLatexCollector[4];
    public static SimpleMachineMetaTileEntity[] ULTRAVIOLET_LAMP_CHAMBER = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static SimpleMachineMetaTileEntity[] DEHYDRATOR = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static MetaTileEntityMillBallHatch MULTIPART_BALL_HATCH;
    public static MetaTileEntityCatalystHatch CATALYST_HATCH;
    public static MetaTileEntityKQNetworkSwitch KQNS;
    public static MetaTileEntityMegaCleanroom MEGA_CLEANROOM;
    public static MetaTileEntityDimensionalMixer DIMENSIONAL_MIXER;
    public static MetaTileEntityDimensionallyPlasmFurnace DIMENSIONAL_PLASMA_FURNACE;
    public static SimpleMachineMetaTileEntity[] AUTO_CHISEL = new SimpleMachineMetaTileEntity[3];
    public static MetaTileEntityThreeDim THREE_DIM_PRINT;
    public static MetaTileEntityELEOil ELE_OIL;
    public static MetaTileEntityAdvancedArcFurnace ADV_ARC_FURNACE;
    public static MetaTileEntityBioCentrifuge BIO_CENTRIFUGE;
    public static MetaTileEntityEnzymesReaction ENZYMES_REACTOR;
    public static MetaTileEntityNeutronActivator NEUTRON_ACTIVATOR;
    public static MetaTileEntityReactionFurnace REACTION_FURNACE;
    public static MetaTileEntityChemicalPlant CHEMICAL_PLANT;
    public static MetaTileEntityLaserEngraving LASER_ENGRAVING;
    public static MetaTileEntityWaterPowerStation[] WATER_POWER_STATION = new MetaTileEntityWaterPowerStation[3];
    public static MetaTileEntityKineticEnergyBattery KINETIC_ENERGY_BATTERY;
    public static MetaTileEntityGrayElectricPowerBank GRAY_ELECTRIC_POWER_BANK;
    public static final MetaTileEntityRotorHolder[] ROTOR_HOLDER = new MetaTileEntityRotorHolder[12]; //HV, EV, IV, LuV, ZPM, UV
    public static final SimpleMachineMetaTileEntity[] FLUID_CANNER = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static final SimpleMachineMetaTileEntity[] FLUID_EXTRACTOR = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static MetaTileEntityAssemblyLine ASSEMBLY_LINE;
    public static MetaTileEntityKQCCComputationHatch[] KQCC_COMPUTATION_HATCH_RECEIVER=new MetaTileEntityKQCCComputationHatch[12];
    public static MetaTileEntityKQCCComputationHatch[] KQCC_COMPUTATION_HATCH_TRANSMITTER=new MetaTileEntityKQCCComputationHatch[12];
    public static MetaTileEntityMSF MSF;
    public static MetaTileEntityNicollDysonBeamer NICOLL_DYSON_BEAMER;
    public static MetaTileEntityDimensionallyBiomimeticFactory DIMENSIONAL_BIOMIMETIC_FACTORY;
    public static MetaTileEntityLargeBiomassGenerator LARGE_BIOMASS_GENERATOR;
    public static MetaTileEntityFluidRubbishBin FLUID_RUBBISH_BIN;
    public static MetaTileEntityParticleAcceleratorIO[] PARTICLE_ACCELERATOR_IO=new MetaTileEntityParticleAcceleratorIO[4];
    public static MetaTileEntityAdvancedAssemblyLine ADVANCED_ASSEMBLY_LINE;
    public static MetaTileEntityLargeGrind LAGER_GRIND;
    public static MetaTileEntityLargeForging LAGER_FORGING;
    public static MetaTileEntityLargeExtractor LAGER_EXTRACTOR;
    public static MetaTileEntityNanoCoating NANO_COATING;
    public static final SimpleGeneratorMetaTileEntity[] BIOMASS_GENERATOR = new SimpleGeneratorMetaTileEntity[3];
    public static final SimpleGeneratorMetaTileEntity[] ACID_GENERATOR = new SimpleGeneratorMetaTileEntity[3];
    public static MetaTileEntityCryogenicFreezer CRYOGENIC_FREEZER;
    public static MetaTileEntityHeatExchanger HEAT_CHANGER;
    public static MetaTileEntityHPCAAdvancedComputation HPCA_SUPER_COMPUTATION_COMPONENT;
    public static MetaTileEntityHPCAAdvancedComputation HPCA_ULTIMATE_COMPUTATION_COMPONENT;
    public static MetaTileEntityHPCAAdvancedCooler HPCA_SUPER_COOLER_COMPONENT;
    public static MetaTileEntityHPCAAdvancedCooler HPCA_ULTIMATE_COOLER_COMPONENT;
    public static MetaTileEntityIntegratedOreProcessor INTEGRATED_ORE_PROCESSOR;
    public static MetaTileEntityCrystallizationCrucible CRYSTALLIZATION_CRUCIBLE;
    public static MetaTileEntityIndustrialRoaster ROASTER;
    public static MetaTileEntityMegaTurbine MEGA_STEAM_TURBINE;
    public static MetaTileEntityMegaTurbine MEGA_GAS_TURBINE;
    public static MetaTileEntityMegaTurbine MEGA_PLASMA_TURBINE;
    public static MetaTileEntityLargeOreWasher LAGER_ORE_WASHER;
    public static MetaTileEntityLargeThermalCentrifuge LAGER_THERMAL_CENTRIFUGE;
    public static MetaTileEntityNanoscaleFabricator NANOSCALE_FABRICATOR;
    public static MetaTileEntityCVDUnit CVD_UNIT;
    public static MetaTileEntityPhotolithographyFactory PHOTOLITHOGRAPHY_FACTORY;
    public static MetaTileEntityMicrowaveEnergyReceiverControl MICROWAVE_ENERGY_RECEIVER_CONTROL;
    public static MetaTileEntityEXCVD EX_CVD;
    public static MetaTileEntitySuprachronalNeutroniumForge SUPRACHRONAL_NEUTRONIUM_FORGE;
    public static MetaTileEntityStewStoolStove COAGULATION_TANK;
    public static MetaTileEntityBurnerReactor BURNER_REACTOR;
    public static MetaTileEntityCryoReactor CRYOGENIC_REACTOR;
    public static MetaTileEntityFracker HYDRAULIC_FRACKER;
    public static MetaTileEntityFluidizedBed FLUIDIZED_BED;
    public static MetaTileEntitySonicator SONICATOR;
    public static MetaTileEntityCatalyticReformer CATALYTIC_REFORMER;
    public static MetaTileEntityIndustrialDrill INDUSTRIAL_DRILL;
    public static MetaTileEntityIonImplanter ION_IMPLANTATOR;
    public static MetaTileEntityCZPuller CZ_PULLER;
    public static MetaTileEntityFixBed FIX_BED;
    public static MetaTileEntityDimensionallyTranscendentPlasmaForge DIMENSIONALLY_TRANSCENDENT_PLASMA_FORGE;
    public static MetaTileEntityIndustrialMacerator INDUSTRIAL_MACERATOR;
    public static MetaTileEntityIndustrialCentrifuge INDUSTRIAL_CENTRIFUGE;
    public static MetaTileEntityIndustrialMix INDUSTRIAL_MIX;
    public static MetaTileEntityIndustrialWasher INDUSTRIAL_WASHER;
    public static MetaTileEntityIndustrialHammer INDUSTRIAL_HAMMER;
    public static MetaTileEntityIndustrialFurnace INDUSTRIAL_FURNACE;
    public static MetaTileEntityIndustrialAssemblyLine INDUSTRIAL_ASSEMBLY_LINE;
    public static MetaTileEntityCoreMacerator CORE_MACERATOR;
    public static MetaTileEntityCoreCentrifuge CORE_CENTRIFUGE;
    public static MetaTileEntityCoreMix CORE_MIX;
    public static MetaTileEntityCoreWasher CORE_WASHER;
    public static MetaTileEntityCoreHammer CORE_HAMMER;
    public static MetaTileEntityCoreFurnace CORE_FURNACE;
    public static MetaTileEntityIndustrialBender INDUSTRIAL_BENDER;
    public static MetaTileEntityIndustrialWireMill INDUSTRIAL_WIREMILL;
    public static MetaTileEntityIndustrialExtruder INDUSTRIAL_EXTRUDER;
    public static MetaTileEntityNuclearReactor NUCLEAR_REACTOR;
    public static MetaTileEntityLargeTurbine LARGE_FUEL_TURBINE;
    public static final MetaTileEntityReinforcedRotorHolder[] MULTIPART_REINFORCED_ROTOR_HOLDER = new MetaTileEntityReinforcedRotorHolder[8];
    public static final MetaTileEntityCompressedFusionReactor[] COMPRESSED_FUSION_REACTOR = new MetaTileEntityCompressedFusionReactor[6];
    public static MetaTileEntityBathCondenser[] BATH_CONDENSER=new MetaTileEntityBathCondenser[1];
    public static MetaTileEntityHighPressureCryogenicDistillationPlant HIGH_PRESSURE_CRYOGENIC_DISTILLATION_PLANT;
    public static MetaTileEntityLowPressureCryogenicDistillationPlant LOW_PRESSURE_CRYOGENIC_DISTILLATION_PLANT;

    public static MetaTileEntitySterileCleaningMaintenanceHatch STERILE_CLEANING_MAINTENANCE_HATCH;
    public static MetaTileEntityISO3CleaningMaintenanceHatch ISO3_CLEANING_MAINTENANCE_HATCH;
    public static MetaTileEntityISO2CleaningMaintenanceHatch ISO2_CLEANING_MAINTENANCE_HATCH;
    public static MetaTileEntityISO1CleaningMaintenanceHatch ISO1_CLEANING_MAINTENANCE_HATCH;

    private static int startId = 15600;
    private static final int END_ID = startId + 400;

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
        NAQUADAH_REACTOR[0] = registerMetaTileEntity(3003, new SimpleGeneratorMetaTileEntity(gtqtcoreId("naquadah_reactor.iv"), GTQTcoreRecipeMaps.NAQUADAH_REACTOR_RECIPES, GTQTTextures.NAQUADAH_REACTOR_OVERLAY, 5, genericGeneratorTankSizeFunctionPlus));
        NAQUADAH_REACTOR[1] = registerMetaTileEntity(3004, new SimpleGeneratorMetaTileEntity(gtqtcoreId("naquadah_reactor.luv"), GTQTcoreRecipeMaps.NAQUADAH_REACTOR_RECIPES, GTQTTextures.NAQUADAH_REACTOR_OVERLAY, 6, genericGeneratorTankSizeFunctionPlus));
        NAQUADAH_REACTOR[2] = registerMetaTileEntity(3005, new SimpleGeneratorMetaTileEntity(gtqtcoreId("naquadah_reactor.zpm"),  GTQTcoreRecipeMaps.NAQUADAH_REACTOR_RECIPES, GTQTTextures.NAQUADAH_REACTOR_OVERLAY, 7, genericGeneratorTankSizeFunctionPlus));
        ROCKET_ENGINE[0] = registerMetaTileEntity(3006, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rocket_engine.ev"), GTQTcoreRecipeMaps.ROCKET, GTQTTextures.ROCKET_ENGINE_OVERLAY, 4, genericGeneratorTankSizeFunctionPlus));
        ROCKET_ENGINE[1] = registerMetaTileEntity(3007, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rocket_engine.iv"), GTQTcoreRecipeMaps.ROCKET, GTQTTextures.ROCKET_ENGINE_OVERLAY, 5, genericGeneratorTankSizeFunctionPlus));
        ROCKET_ENGINE[2] = registerMetaTileEntity(3008, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rocket_engine.luv"), GTQTcoreRecipeMaps.ROCKET, GTQTTextures.ROCKET_ENGINE_OVERLAY, 6, genericGeneratorTankSizeFunctionPlus));

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

        RTG[0] = registerMetaTileEntity(3020, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rtg.ev"), GTQTcoreRecipeMaps.RTG_RECIPES, GTQTTextures.RTG_OVERLAY, 4, genericGeneratorTankSizeFunctionPlus,true));
        RTG[1] = registerMetaTileEntity(3021, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rtg.iv"), GTQTcoreRecipeMaps.RTG_RECIPES, GTQTTextures.RTG_OVERLAY, 5, genericGeneratorTankSizeFunctionPlus,true));
        RTG[2] = registerMetaTileEntity(3022, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rtg.luv"), GTQTcoreRecipeMaps.RTG_RECIPES, GTQTTextures.RTG_OVERLAY, 6, genericGeneratorTankSizeFunctionPlus,true));

        BIOMASS_GENERATOR[0] = registerMetaTileEntity(3023, new SimpleGeneratorMetaTileEntity(gtqtcoreId("biomass_generator.ev"), GTQTcoreRecipeMaps.BIOMASS_GENERATOR_RECIPES, GTQTTextures.BIOMASS_GENERATOR_OVERLAY, 4, GTUtility.genericGeneratorTankSizeFunction));
        BIOMASS_GENERATOR[1] = registerMetaTileEntity(3024, new SimpleGeneratorMetaTileEntity(gtqtcoreId("biomass_generator.iv"), GTQTcoreRecipeMaps.BIOMASS_GENERATOR_RECIPES, GTQTTextures.BIOMASS_GENERATOR_OVERLAY, 5, GTUtility.genericGeneratorTankSizeFunction));
        BIOMASS_GENERATOR[2] = registerMetaTileEntity(3025, new SimpleGeneratorMetaTileEntity(gtqtcoreId("biomass_generator.luv"), GTQTcoreRecipeMaps.BIOMASS_GENERATOR_RECIPES, GTQTTextures.BIOMASS_GENERATOR_OVERLAY, 6, GTUtility.genericGeneratorTankSizeFunction));

        ACID_GENERATOR[0] = registerMetaTileEntity(3026, new SimpleGeneratorMetaTileEntity(gtqtcoreId("acid_generator.mv"), GTQTcoreRecipeMaps.ACID_GENERATOR_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 2, GTUtility.genericGeneratorTankSizeFunction));
        ACID_GENERATOR[1] = registerMetaTileEntity(3027, new SimpleGeneratorMetaTileEntity(gtqtcoreId("acid_generator.hv"), GTQTcoreRecipeMaps.ACID_GENERATOR_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 3, GTUtility.genericGeneratorTankSizeFunction));
        ACID_GENERATOR[2] = registerMetaTileEntity(3028, new SimpleGeneratorMetaTileEntity(gtqtcoreId("acid_generator.ev"), GTQTcoreRecipeMaps.ACID_GENERATOR_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 4, GTUtility.genericGeneratorTankSizeFunction));

        //发电设备 多方块

        if(GTQTCoreConfig.MachineSwitch.LastSwitch)HYPER_REACTOR_MKI = registerMetaTileEntity(3053, new MetaTileEntityHyperReactorMkI(gtqtcoreId("hyper_reactor_mk1")));
        if(GTQTCoreConfig.MachineSwitch.LastSwitch)HYPER_REACTOR_MKII = registerMetaTileEntity(3054, new MetaTileEntityHyperReactorMkII(gtqtcoreId("hyper_reactor_mk2")));
        if(GTQTCoreConfig.MachineSwitch.LastSwitch)HYPER_REACTOR_MKIII = registerMetaTileEntity(3055, new MetaTileEntityHyperReactorMkIII(gtqtcoreId("hyper_reactor_mk3")));
        NAQUADAH_REACTOR_MKI = registerMetaTileEntity(3056, new MetaTileEntityNaquadahReactorMki(gtqtcoreId("naquadah_reactor_mki")));
        NAQUADAH_REACTOR_MKII = registerMetaTileEntity(3057, new MetaTileEntityNaquadahReactorMkii(gtqtcoreId("naquadah_reactor_mkii")));
        NAQUADAH_REACTOR_MKIII = registerMetaTileEntity(3058, new MetaTileEntityNaquadahReactorMkiii(gtqtcoreId("naquadah_reactor_mkiii")));
        LARGE_NAQUADAH_REACTOR = registerMetaTileEntity(3059, new MetaTileEntityLargeNaquadahReactor(gtqtcoreId("large_naquadah_reactor")));
        ROCKET = registerMetaTileEntity(3060, new MetaTileEntityRocket(gtqtcoreId("rocket"),7));
        HUGE_TURBINE_COMBUSTION_CHAMBER = registerMetaTileEntity(3061, new MetaTileEntityTurbineCombustionChamber(gtqtcoreId("turbine_combustion_chamber"),4));
        SOLAR_PLATE = registerMetaTileEntity(3062, new MetaTileEntitySolarPlate(gtqtcoreId("solar_plate")));
        NUCLEAR_REACTOR = registerMetaTileEntity(3063, new MetaTileEntityNuclearReactor(gtqtcoreId("nuclear_reactor")));
        WATER_POWER_STATION[0] = registerMetaTileEntity(3064, new MetaTileEntityWaterPowerStation(gtqtcoreId("water_power_station_mk1"),1));
        WATER_POWER_STATION[1] = registerMetaTileEntity(3065, new MetaTileEntityWaterPowerStation(gtqtcoreId("water_power_station_mk2"),2));
        WATER_POWER_STATION[2] = registerMetaTileEntity(3066, new MetaTileEntityWaterPowerStation(gtqtcoreId("water_power_station_mk3"),3));
        MEGA_STEAM_TURBINE = registerMetaTileEntity(3067, new MetaTileEntityMegaTurbine(gtqtcoreId("mega_turbine.steam"), RecipeMaps.STEAM_TURBINE_FUELS, 3, MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STEEL_TURBINE_CASING), MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STEEL_GEARBOX), Textures.SOLID_STEEL_CASING, false, GTQTTextures.MEGA_TURBINE_OVERLAY));
        MEGA_GAS_TURBINE = registerMetaTileEntity(3068, new MetaTileEntityMegaTurbine(gtqtcoreId("mega_turbine.gas"), RecipeMaps.GAS_TURBINE_FUELS, 4, MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STAINLESS_TURBINE_CASING), MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STAINLESS_STEEL_GEARBOX), Textures.CLEAN_STAINLESS_STEEL_CASING, true, GTQTTextures.MEGA_TURBINE_OVERLAY));
        MEGA_PLASMA_TURBINE = registerMetaTileEntity(3069, new MetaTileEntityMegaTurbine(gtqtcoreId("mega_turbine.plasma"), RecipeMaps.PLASMA_GENERATOR_FUELS, 5, MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TUNGSTENSTEEL_TURBINE_CASING), MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TUNGSTENSTEEL_GEARBOX), Textures.ROBUST_TUNGSTENSTEEL_CASING, false, GTQTTextures.MEGA_TURBINE_OVERLAY));
        KINETIC_ENERGY_BATTERY = registerMetaTileEntity(3070, new MetaTileEntityKineticEnergyBattery(gtqtcoreId("kinetic_energy_battery")));
        GRAY_ELECTRIC_POWER_BANK = registerMetaTileEntity(3071, new MetaTileEntityGrayElectricPowerBank(gtqtcoreId("gray_electric_power_bank")));
        LARGE_FUEL_TURBINE = registerMetaTileEntity(3072, new MetaTileEntityLargeTurbine(gtqtcoreId("large_turbine.fuel_cell"),
                GTQTcoreRecipeMaps.FUEL_CELL, 5,
                MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TUNGSTENSTEEL_TURBINE_CASING),
                MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TUNGSTENSTEEL_GEARBOX),
                Textures.ROBUST_TUNGSTENSTEEL_CASING, true, Textures.LARGE_GAS_TURBINE_OVERLAY));

        LARGE_BIOMASS_GENERATOR = registerMetaTileEntity(3073, new MetaTileEntityLargeBiomassGenerator(gtqtcoreId("large_biomass_generator")));

        TIDE_CONTROL = registerMetaTileEntity(3075, new MetaTileEntityTideControl(gtqtcoreId("tide_control")));
        TIDE_UNIT = registerMetaTileEntity(3076, new MetaTileEntityTideUnit(gtqtcoreId("tide_unit")));

        //早期设备

        INDUSTRIAL_PRIMITIVE_BLAST_FURNACE = registerMetaTileEntity(3106, new MetaTileEntityIndustrialPrimitiveBlastFurnace(gtqtcoreId("industrial_primitive_blast_furnace")));
        COAGULATION_TANK = registerMetaTileEntity(3107, new MetaTileEntityStewStoolStove(gtqtcoreId("coagulation_tank")));
        P_REACTOR= registerMetaTileEntity(3108, new MetaTileEntityPReactor(gtqtcoreId("p_reactor")));
        B_REACTOR= registerMetaTileEntity(3109, new MetaTileEntityBReactor(gtqtcoreId("b_reactor")));
        STEAM_VAT= registerMetaTileEntity(3110, new MetaTileEntitySteamFermentationVat(gtqtcoreId("steam_vat")));

        //多核心
        INDUSTRIAL_MACERATOR= registerMetaTileEntity(3120, new MetaTileEntityIndustrialMacerator(gtqtcoreId("industrial_crusher")));
        INDUSTRIAL_CENTRIFUGE= registerMetaTileEntity(3121, new MetaTileEntityIndustrialCentrifuge(gtqtcoreId("industrial_centrifuge")));
        INDUSTRIAL_MIX= registerMetaTileEntity(3122, new MetaTileEntityIndustrialMix(gtqtcoreId("industrial_mix")));
        INDUSTRIAL_WASHER= registerMetaTileEntity(3123, new MetaTileEntityIndustrialWasher(gtqtcoreId("industrial_washer")));
        INDUSTRIAL_HAMMER= registerMetaTileEntity(3124, new MetaTileEntityIndustrialHammer(gtqtcoreId("industrial_hammer")));
        INDUSTRIAL_FURNACE= registerMetaTileEntity(3125, new MetaTileEntityIndustrialFurnace(gtqtcoreId("industrial_furnace")));

        CORE_MACERATOR= registerMetaTileEntity(3126, new MetaTileEntityCoreMacerator(gtqtcoreId("core_crusher")));
        CORE_CENTRIFUGE= registerMetaTileEntity(3127, new MetaTileEntityCoreCentrifuge(gtqtcoreId("core_centrifuge")));
        CORE_MIX= registerMetaTileEntity(3128, new MetaTileEntityCoreMix(gtqtcoreId("core_mix")));
        CORE_WASHER= registerMetaTileEntity(3129, new MetaTileEntityCoreWasher(gtqtcoreId("core_washer")));
        CORE_HAMMER= registerMetaTileEntity(3130, new MetaTileEntityCoreHammer(gtqtcoreId("core_hammer")));
        CORE_FURNACE= registerMetaTileEntity(3131, new MetaTileEntityCoreFurnace(gtqtcoreId("core_furnace")));

        INDUSTRIAL_BENDER= registerMetaTileEntity(3132, new MetaTileEntityIndustrialBender(gtqtcoreId("industrial_bender")));
        INDUSTRIAL_WIREMILL= registerMetaTileEntity(3133, new MetaTileEntityIndustrialWireMill(gtqtcoreId("industrial_wiremill")));
        INDUSTRIAL_EXTRUDER= registerMetaTileEntity(3134, new MetaTileEntityIndustrialExtruder(gtqtcoreId("industrial_extruder")));
        INDUSTRIAL_ASSEMBLY_LINE= registerMetaTileEntity(3135, new MetaTileEntityIndustrialAssemblyLine(gtqtcoreId("industrial_assembly_line")));
        //正常设备
        DISSOLUTION_TANK = registerMetaTileEntity(3150, new MetaTileEntityDissolutionTank(gtqtcoreId("dissolution_tank")));
        BLAZING_BLAST_FURNACE = registerMetaTileEntity(3151, new MetaTileEntityBlazingBlastFurnace(gtqtcoreId("blazing_blast_furnace")));
        CHEMICAL_PLANT = registerMetaTileEntity(3152,new MetaTileEntityChemicalPlant(gtqtcoreId("chemical_plant")));
        INTEGRATED_MINING_DIVISION = registerMetaTileEntity(3153, new MetaTileEntityIntegratedMiningDivision(gtqtcoreId("integrated_mining_division")));
        QUANTUM_FORCE_TRANSFORMER = registerMetaTileEntity(3154, new MetaTileEntityQuantumForceTransformer(gtqtcoreId("quantum_force_transform")));
        SMALL_HEAT_EXCHANGER = registerMetaTileEntity(3155, new MetaTileEntitySmallHeatExchanger(gtqtcoreId("small_heat_exchanger")));
        LAGER_HEAT_EXCHANGER = registerMetaTileEntity(3156, new MetaTileEntityLagerHeatExchanger(gtqtcoreId("lager_heat_exchanger")));
        FUEL_REFINE_FACTORY = registerMetaTileEntity(3157, new MetaTileEntityFuelRefineFactory(gtqtcoreId("fuel_refine_factory")));
        FERMENTATION_TANK= registerMetaTileEntity(3158, new MetaTileEntityFermentationTank(gtqtcoreId("fermentation_tank")));
        DIGESTER = registerMetaTileEntity(3159, new MetaTileEntityDigester(gtqtcoreId("digester")));
        SEPTIC_TANK = registerMetaTileEntity(3160, new MetaTileEntitySepticTank(gtqtcoreId("septic_tank")));
        PCB_FACTORY = registerMetaTileEntity(3161, new MetaTileEntityPCBFactory(gtqtcoreId("pcb_factory")));
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
        HEAT_CHANGER= registerMetaTileEntity(3178, new MetaTileEntityHeatExchanger(gtqtcoreId("heat_exchanger")));
        LASER_ENGRAVING= registerMetaTileEntity(3179, new MetaTileEntityLaserEngraving(gtqtcoreId("laser_engraving")));
        STEPPER= registerMetaTileEntity(3180, new MetaTileEntityStepper(gtqtcoreId("stepper")));
        GANTRY_CRANE= registerMetaTileEntity(3181, new MetaTileEntityGantryCrane(gtqtcoreId("gantry_crane")));
        CLARIFIER= registerMetaTileEntity(3182, new MetaTileEntityClarifier(gtqtcoreId("clarifier")));
        ELE_OIL= registerMetaTileEntity(3183, new MetaTileEntityELEOil(gtqtcoreId("ele_oil")));
        REACTION_FURNACE= registerMetaTileEntity(3184, new MetaTileEntityReactionFurnace(gtqtcoreId("reactor_furnace")));
        THREE_DIM_PRINT= registerMetaTileEntity(3186, new MetaTileEntityThreeDim(gtqtcoreId("three_dim_print")));
        NEUTRON_ACTIVATOR= registerMetaTileEntity(3187, new MetaTileEntityNeutronActivator(gtqtcoreId("neutron_activator")));
        MSF= registerMetaTileEntity(3188, new MetaTileEntityMSF(gtqtcoreId("msf")));
        INTEGRATED_ORE_PROCESSOR= registerMetaTileEntity(3189, new MetaTileEntityIntegratedOreProcessor(gtqtcoreId("integrated_ore_processor")));
        FLUIDIZED_BED= registerMetaTileEntity(3190, new MetaTileEntityFluidizedBed(gtqtcoreId("fluidzed_bed")));
        ELECTRIC_ARC_FURNACE= registerMetaTileEntity(3191, new MetaTileEntityElectricArcFurnace(gtqtcoreId("electric_arc_furnace")));
        GREEN_HOUSE_PLUS= registerMetaTileEntity(3192, new MetaTileEntityGreenhousePlus(gtqtcoreId("green_house_plus")));
        ENZYMES_REACTOR= registerMetaTileEntity(3193, new MetaTileEntityEnzymesReaction(gtqtcoreId("enzymes_reactor")));
        BIO_CENTRIFUGE= registerMetaTileEntity(3194, new MetaTileEntityBioCentrifuge(gtqtcoreId("bio_centrifuge")));
        CRYOGENIC_FREEZER= registerMetaTileEntity(3195, new MetaTileEntityCryogenicFreezer(gtqtcoreId("cryogenic_freezer")));
        NANO_COATING= registerMetaTileEntity(3196, new MetaTileEntityNanoCoating(gtqtcoreId("nano_coating")));
        POWER_SUPPLY= registerMetaTileEntity(3197, new MetaTileEntityPowerSupply(gtqtcoreId("power_supply")));
        FROTH_FLOTATION_TANK = registerMetaTileEntity(3198, new MetaTileEntityFrothFlotationTank(gtqtcoreId("froth_flotation_tank")));
        FIX_BED = registerMetaTileEntity(3199, new MetaTileEntityFixBed(gtqtcoreId("fix_bed")));
        HIGH_PRESSURE_CRYOGENIC_DISTILLATION_PLANT = registerMetaTileEntity(3200, new MetaTileEntityHighPressureCryogenicDistillationPlant(gtqtcoreId("high_pressure_cryogenic_distillation_plant")));
        LOW_PRESSURE_CRYOGENIC_DISTILLATION_PLANT = registerMetaTileEntity(3201, new MetaTileEntityLowPressureCryogenicDistillationPlant(gtqtcoreId("low_pressure_cryogenic_distillation_plant")));
        MICROWAVE_ENERGY_RECEIVER_CONTROL= registerMetaTileEntity(3202, new MetaTileEntityMicrowaveEnergyReceiverControl(gtqtcoreId("microwave_energy_receiver_control")));
        MEGA_CLEANROOM = registerMetaTileEntity(3203, new MetaTileEntityMegaCleanroom(gtqtcoreId("mega_cleanroom")));
        SEISMIC_DETECTOR = registerMetaTileEntity(3204, new MetaTileEntitySeismicDetector(gtqtcoreId("seismic_detector")));
        GRAVITY_SEPARATOR = registerMetaTileEntity(3205, new MetaTileEntityGravitySeparator(gtqtcoreId("gravity_separator")));
        COKING_TOWER = registerMetaTileEntity(3206, new MetaTileEntityCokingTower(gtqtcoreId("coking_tower")));
        SMSF = registerMetaTileEntity(3207, new MetaTileEntitySMSF(gtqtcoreId("smsf")));
        VACUUM_DISTILLATION_TOWER = registerMetaTileEntity(3208, new MetaTileEntityVacuumDistillationTower(gtqtcoreId("vacuum_distillation_tower")));
        ADV_ARC_FURNACE= registerMetaTileEntity(3209, new MetaTileEntityAdvancedArcFurnace(gtqtcoreId("adv_arc_furnace")));
        NEUTRAL_NETWORK_NEXUS = registerMetaTileEntity(3210, new MetaTileEntityNeutralNetworkNexus(gtqtcoreId("neutral_network_nexus")));
        BLAZING_CZ_PULLER = registerMetaTileEntity(3211, new MetaTileEntityBlazingCZPuller(gtqtcoreId("blazing_cz_puller")));
        LARGE_CIRCUIT_ASSEMBLY_LINE = registerMetaTileEntity(3212, new MetaTileEntityLargeCircuitAssemblyLine(gtqtcoreId("large_circuit_assembly_line")));
        PHOTOLITHOGRAPHY_FACTORY = registerMetaTileEntity(3213, new MetaTileEntityPhotolithographyFactory(gtqtcoreId("photolithography_factory")));
        DANGOTE_DISTILLERY = registerMetaTileEntity(3214, new MetaTileEntityDangoteDistillery(gtqtcoreId("dangote_distillery")));
        EVAPORATION_POOL = registerMetaTileEntity(3215, new MetaTileEntityEvaporationPool(gtqtcoreId("evaporation_pool")));
        ELECTRON_MICROSCOPE = registerMetaTileEntity(3216, new MetaTileEntityElectronMicroscope(gtqtcoreId("electron_microscope")));
        GENE_MUTAGENESIS = registerMetaTileEntity(3217, new MetaTileEntityGeneMutagenesis(gtqtcoreId("gene_mutagenesis")));
        //重写设备
        DISTILLATION_TOWER = registerMetaTileEntity(3250, new MetaTileEntityDistillationTower(gtqtcoreId("distillation_tower"),true));
        CRACKER = registerMetaTileEntity(3251, new MetaTileEntityCrackingUnit(gtqtcoreId("cracker")));
        PYROLYSE_OVEN = registerMetaTileEntity(3252, new MetaTileEntityPyrolyseOven(gtqtcoreId("pyrolyse_oven")));
        VACUUM_FREEZER = registerMetaTileEntity(3253, new MetaTileEntityVacuumFreezer(gtqtcoreId("vacuum_freezer")));
        ASSEMBLY_LINE = registerMetaTileEntity(3254, new MetaTileEntityAssemblyLine(gtqtcoreId("assembly_line")));
        LAGER_ORE_WASHER = registerMetaTileEntity(3255, new MetaTileEntityLargeOreWasher(gtqtcoreId("lager_ore_washer")));
        LAGER_THERMAL_CENTRIFUGE = registerMetaTileEntity(3256, new MetaTileEntityLargeThermalCentrifuge(gtqtcoreId("lager_thermal_centrifuge")));
        LAGER_GRIND = registerMetaTileEntity(3257, new MetaTileEntityLargeGrind(gtqtcoreId("lager_grind")));
        LAGER_FORGING = registerMetaTileEntity(3258, new MetaTileEntityLargeForging(gtqtcoreId("lager_forging")));
        LAGER_EXTRACTOR = registerMetaTileEntity(3259, new MetaTileEntityLargeExtractor(gtqtcoreId("lager_extractor")));
        LV_PROCESSING_ARRAY = registerMetaTileEntity(3287, new MetaTileEntityProcessingArray(gtqtcoreId("lv_processing_array"), 1));
        MV_PROCESSING_ARRAY = registerMetaTileEntity(3288, new MetaTileEntityProcessingArray(gtqtcoreId("mv_processing_array"), 2));
        HV_PROCESSING_ARRAY = registerMetaTileEntity(3289, new MetaTileEntityProcessingArray(gtqtcoreId("hv_processing_array"), 3));
        //巨型设备
        HUGE_FUSION_REACTOR[0]=registerMetaTileEntity(3290,new MetaTileEntityHugeFusionReactor(gtqtcoreId("fusion_reactor.uhv"),UHV));
        HUGE_FUSION_REACTOR[1]=registerMetaTileEntity(3291,new MetaTileEntityHugeFusionReactor(gtqtcoreId("fusion_reactor.uev"),UEV));
        HUGE_FUSION_REACTOR[2]=registerMetaTileEntity(3292,new MetaTileEntityHugeFusionReactor(gtqtcoreId("fusion_reactor.uiv"),UIV));

        COMPRESSED_FUSION_REACTOR[0] = registerMetaTileEntity(3293, new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor.luv"), LuV, MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_CASING), MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL), MetaBlocks.FRAMES.get(Materials.Naquadria).getBlock(Materials.Naquadria)));
        COMPRESSED_FUSION_REACTOR[1] = registerMetaTileEntity(3294, new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor.zpm"), ZPM, MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_CASING_MK2), MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_COIL), MetaBlocks.FRAMES.get(Materials.Tritanium).getBlock(Materials.Tritanium)));
        COMPRESSED_FUSION_REACTOR[2] = registerMetaTileEntity(3295, new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor.uv"), UV, MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_CASING_MK3), MetaBlocks.FUSION_CASING.getState(gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_COIL), MetaBlocks.FRAMES.get(Neutronium).getBlock(Neutronium)));
        COMPRESSED_FUSION_REACTOR[3] = registerMetaTileEntity(3296, new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor.uhv"), UHV, GTQTMetaBlocks.COMPRESSED_FUSION_REACTOR.getState(CASING_FUSION_MKIV), GTQTMetaBlocks.COMPRESSED_FUSION_REACTOR.getState(FUSION_COIL_MKII), MetaBlocks.FRAMES.get(Infinity).getBlock(Infinity)));
        COMPRESSED_FUSION_REACTOR[4] = registerMetaTileEntity(3297, new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor.uev"), UEV, GTQTMetaBlocks.COMPRESSED_FUSION_REACTOR.getState(CASING_FUSION_MKV), GTQTMetaBlocks.COMPRESSED_FUSION_REACTOR.getState(FUSION_COIL_MKIII), MetaBlocks.FRAMES.get(MagnetoHydrodynamicallyConstrainedStarMatter).getBlock(MagnetoHydrodynamicallyConstrainedStarMatter)));
        COMPRESSED_FUSION_REACTOR[5] = registerMetaTileEntity(3298, new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor.uiv"), UIV, GTQTMetaBlocks.COMPRESSED_FUSION_REACTOR.getState(CASING_FUSION_MKVI), GTQTMetaBlocks.COMPRESSED_FUSION_REACTOR.getState(FUSION_COIL_MKIV), MetaBlocks.FRAMES.get(Eternity).getBlock(Eternity)));

        ADVANCED_ASSEMBLY_LINE = registerMetaTileEntity(3305, new MetaTileEntityAdvancedAssemblyLine(gtqtcoreId("advanced_assembly_line")));
        COMPONENT_ASSEMBLY_LINE = registerMetaTileEntity(3306, new MetaTileEntityComponentAssemblyLine(gtqtcoreId("component_assembly_line")));

        LAGER_PROCESSING_FACTORY = registerMetaTileEntity(3307, new MetaTileEntityLargeProcessingFactory(gtqtcoreId("large_processing_factory")));
        if(GTQTCoreConfig.MachineSwitch.HugeSwitch)HUGE_DISTILLATION_TOWER = registerMetaTileEntity(3308, new MetaTileEntityHugeDistillationTower(gtqtcoreId("huge_distillation_tower")));
        if(GTQTCoreConfig.MachineSwitch.HugeSwitch)HUGE_VACUUM = registerMetaTileEntity(3309, new MetaTileEntityHugeVacuum(gtqtcoreId("huge_vacuum")));
        if(GTQTCoreConfig.MachineSwitch.HugeSwitch)HUGE_ELECTRRIC_IMPLOSION_COMPRESSOR = registerMetaTileEntity(3310, new MetaTileEntityHugeElectricImplosionCompressor(gtqtcoreId("huge_electric_implosion_compressor")));
        if(GTQTCoreConfig.MachineSwitch.HugeSwitch)HUGE_MACERATOR = registerMetaTileEntity(3311,new MetaTileEntityHugeMacerator(gtqtcoreId("huge_macerator")));
        HUGE_ALLOY_BLAST_FURANCE = registerMetaTileEntity(3312,new MetaTileEntityHugeAlloyBlastSmelter(gtqtcoreId("huge_alloy_blast_smelter")));
        HUGE_BLAST_FURANCE = registerMetaTileEntity(3313,new MetaTileEntityHugeBlastFurnace(gtqtcoreId("huge_blast_furnace")));
        HUGE_CRACKING_UNIT = registerMetaTileEntity(3314, new MetaTileEntityHugeCrackingUnit(gtqtcoreId("huge_cracking_unit")));
        HUGE_CHEMICAL_REACTOR = registerMetaTileEntity(3315, new MetaTileEntityHugeChemicalReactor(gtqtcoreId("huge_chemical_reactor")));
        //star

        if(GTQTCoreConfig.MachineSwitch.LastSwitch)DIMENSIONALLY_TRANSCENDENT_PLASMA_FORGE = registerMetaTileEntity(3350, new MetaTileEntityDimensionallyTranscendentPlasmaForge(gtqtcoreId("dimensionally_transcendent_plasma_forge")));
        if(GTQTCoreConfig.MachineSwitch.LastSwitch)DIMENSIONAL_MIXER = registerMetaTileEntity(3351, new MetaTileEntityDimensionalMixer(gtqtcoreId("dimensional_mixer")));
        if(GTQTCoreConfig.MachineSwitch.LastSwitch)DIMENSIONAL_PLASMA_FURNACE = registerMetaTileEntity(3352, new MetaTileEntityDimensionallyPlasmFurnace(gtqtcoreId("dimensional_plasma_furnace")));
        if(GTQTCoreConfig.MachineSwitch.LastSwitch)DIMENSIONAL_BIOMIMETIC_FACTORY = registerMetaTileEntity(3353, new MetaTileEntityDimensionallyBiomimeticFactory(gtqtcoreId("dimensional_biomimetic_factory")));
        if(GTQTCoreConfig.MachineSwitch.EndGameSwitch)NICOLL_DYSON_BEAMER = registerMetaTileEntity(3354, new MetaTileEntityNicollDysonBeamer(gtqtcoreId("nicoll_dyson_beamer")));
        if(GTQTCoreConfig.MachineSwitch.EndGameSwitch)SUPRACHRONAL_NEUTRONIUM_FORGE = registerMetaTileEntity(3355, new MetaTileEntitySuprachronalNeutroniumForge(gtqtcoreId("suprachronal_neutronium_forge")));

        //资源产出
        BASIC_FLUID_DRILLING_RIG = registerMetaTileEntity(3400, new MetaTileEntityFracturing(gtqtcoreId("fracturing.iv"), 5));
        FLUID_DRILLING_RIG = registerMetaTileEntity(3401, new MetaTileEntityFracturing(gtqtcoreId("fracturing.luv"), 6));
        ADVANCED_FLUID_DRILLING_RIG = registerMetaTileEntity(3402, new MetaTileEntityFracturing(gtqtcoreId("fracturing.zpm"), 7));
        BASIC_HUGE_MINER = registerMetaTileEntity(3403, new MetaTileEntityHugeMiner(gtqtcoreId("large_miner.zpm"), GTValues.ZPM, 1, 8, 7,  64));
        HUGE_MINER = registerMetaTileEntity(3404, new MetaTileEntityHugeMiner(gtqtcoreId("large_miner.uv"), GTValues.UV, 2, 10, 8,  128));
        ADVANCED_HUGE_MINER = registerMetaTileEntity(3405, new MetaTileEntityHugeMiner(gtqtcoreId("large_miner.uhv"), GTValues.UHV, 3, 12, 9 , 256));

        GAS_COLLECTOR = registerMetaTileEntity(3406, new MetaTileEntityGasCollector(gtqtcoreId("gas_collector")));
        INDUSTRY_WATER_PUMP = registerMetaTileEntity(3407, new MetaTileEntityIndustryWaterPump(gtqtcoreId("industry_water_pump")));
        //KQCC
        KQCC= registerMetaTileEntity(3450, new MetaTileEntityKQCC(gtqtcoreId("kqcc")));
        KQNS=registerMetaTileEntity(3451,new MetaTileEntityKQNetworkSwitch(gtqtcoreId("kqns")));
        ADV_KQCC= registerMetaTileEntity(3452, new MetaTileEntityADVKQCC(gtqtcoreId("adv_kqcc")));
        MINI_DATE_BANK = registerMetaTileEntity(3453, new MetaTileEntityMiniDataBank(gtqtcoreId("mini_date_bank")));
        ADV_NETWORK_SWITCH = registerMetaTileEntity(3464, new MetaTileEntityADVNetworkSwitch(gtqtcoreId("adv_network_switch")));
        ADV_DATE_BANK = registerMetaTileEntity(3455, new MetaTileEntityADVDataBank(gtqtcoreId("adv_date_bank")));

        HPCA_SUPER_COMPUTATION_COMPONENT = registerMetaTileEntity(3460, new MetaTileEntityHPCAAdvancedComputation(gtqtcoreId("hpca.super_computation_component"),false));
        HPCA_ULTIMATE_COMPUTATION_COMPONENT = registerMetaTileEntity(3461, new MetaTileEntityHPCAAdvancedComputation(gtqtcoreId("hpca.ultimate_computation_component"),  true));
        HPCA_SUPER_COOLER_COMPONENT = registerMetaTileEntity(3462, new MetaTileEntityHPCAAdvancedCooler(gtqtcoreId("hpca.super_cooler_component"), true, false));
        HPCA_ULTIMATE_COOLER_COMPONENT = registerMetaTileEntity(3463, new MetaTileEntityHPCAAdvancedCooler(gtqtcoreId("hpca.ultimate_cooler_component"),  false, true));

        KeQing_NET= registerMetaTileEntity(3470, new MetaTileEntitykeQingNet(gtqtcoreId("keqing_net")));
        //GCYS
        INDUSTRIAL_DRILL = registerMetaTileEntity(3500, new MetaTileEntityIndustrialDrill(gtqtcoreId("industrial_drill")));
        CATALYTIC_REFORMER = registerMetaTileEntity(3501, new MetaTileEntityCatalyticReformer(gtqtcoreId("catalytic_reformer")));
        SONICATOR = registerMetaTileEntity(3502, new MetaTileEntitySonicator(gtqtcoreId("sonicator")));
        HYDRAULIC_FRACKER = registerMetaTileEntity(3503, new MetaTileEntityFracker(gtqtcoreId("fracker"), GTValues.ZPM));
        NANOSCALE_FABRICATOR = registerMetaTileEntity(3504, new MetaTileEntityNanoscaleFabricator(gtqtcoreId("nanoscale_fabricator")));
        ROASTER = registerMetaTileEntity(3505, new MetaTileEntityIndustrialRoaster(gtqtcoreId("roaster")));
        CRYSTALLIZATION_CRUCIBLE = registerMetaTileEntity(3506, new MetaTileEntityCrystallizationCrucible(gtqtcoreId("crystallization_crucible")));
        CVD_UNIT = registerMetaTileEntity(3507, new MetaTileEntityCVDUnit(gtqtcoreId("cvd_unit")));
        BURNER_REACTOR = registerMetaTileEntity(3508, new MetaTileEntityBurnerReactor(gtqtcoreId("burner_reactor")));
        CRYOGENIC_REACTOR = registerMetaTileEntity(3509, new MetaTileEntityCryoReactor(gtqtcoreId("cryogenic_reactor")));
        ION_IMPLANTATOR = registerMetaTileEntity(3510, new MetaTileEntityIonImplanter(gtqtcoreId("ion_implantator")));
        CZ_PULLER = registerMetaTileEntity(3511, new MetaTileEntityCZPuller(gtqtcoreId("cz_puller")));
        EX_CVD = registerMetaTileEntity(3512, new MetaTileEntityEXCVD(gtqtcoreId("ex_cvd")));

        //存储
        for(int i=0;i<10;i++)
        {
            String tierName = GTValues.VN[i].toLowerCase();
            TANK[i] = registerMetaTileEntity(3520 + i, new MetaTileEntityMultiblockTank(gtqtcoreId("tank." + tierName), i + 1));
        }

        //小机器
        registerSimpleMetaTileEntity(DEHYDRATOR, 14985, "dehydrator", GTQTcoreRecipeMaps.DRYER_RECIPES, Textures.SIFTER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(FLUID_EXTRACTOR, 15000, "fluid_extractor", GTQTcoreRecipeMaps.FLUID_EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(FLUID_CANNER, 15015, "fluid_canner", GTQTcoreRecipeMaps.FLUID_CANNER_RECIPES, Textures.CANNER_OVERLAY, true,GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(COMPONENT_ASSEMBLER, 15030, "component_assembler", GTQTcoreRecipeMaps.COMPONENT_ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(DECAY_CHAMBER, 15045, "decay_chamber", GTQTcoreRecipeMaps.DECAY_CHAMBER_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(VACUUM_CHAMBER, 15060, "vacuum_chamber", GTQTcoreRecipeMaps.VACUUM_CHAMBER_RECIPES, Textures.GAS_COLLECTOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(ULTRAVIOLET_LAMP_CHAMBER, 15075, "ultraviolet_lamp_chamber", GTQTcoreRecipeMaps.ULTRAVIOLET_LAMP_CHAMBER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);

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
        BATH_CONDENSER[0] = registerMetaTileEntity(15103, new MetaTileEntityBathCondenser(gtqtcoreId("bath_condenser")));

        RUBBISH_BIN = registerMetaTileEntity(15104, new MetaTileEntityRubbishBin(gtqtcoreId("rubbish_bin")));
        FLUID_RUBBISH_BIN = registerMetaTileEntity(15105, new MetaTileEntityFluidRubbishBin(gtqtcoreId("fluid_rubbish_bin")));
        COMMON_RUBBISH_BIN = registerMetaTileEntity(15106, new MetaTileEntityCommonRubbishBin(gtqtcoreId("common_rubbish_bin")));
        //仓口
        for (int i = 1; i <= 10; i++) {
            String tierName = GTValues.VN[i].toLowerCase();
            KQCC_COMPUTATION_HATCH_RECEIVER[i] = registerMetaTileEntity(15500 + i-1, new MetaTileEntityKQCCComputationHatch(gtqtcoreId("kqcccomputation_hatch.receiver." + tierName), i,  false));
            KQCC_COMPUTATION_HATCH_TRANSMITTER[i] = registerMetaTileEntity(15510 + i-1, new MetaTileEntityKQCCComputationHatch(gtqtcoreId("kqcccomputation_hatch.transmitter." + tierName), i,  true));
            MICROWAVE_ENERGY_RECEIVER[i] = registerMetaTileEntity(15520 + i-1, new MetaTileEntityMicrowaveEnergyReceiver(gtqtcoreId("microwave_energy_receiver." + tierName), i));
        }

        registerMetaTileEntity(15541, new keqing.gtqtcore.common.metatileentities.multi.multiblockpart.MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[9])), 9));
        registerMetaTileEntity(15542, new keqing.gtqtcore.common.metatileentities.multi.multiblockpart.MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[10])), 10));
        registerMetaTileEntity(15543, new keqing.gtqtcore.common.metatileentities.multi.multiblockpart.MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[11])), 11));
        registerMetaTileEntity(15544, new keqing.gtqtcore.common.metatileentities.multi.multiblockpart.MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[12])), 12));
        registerMetaTileEntity(15545, new keqing.gtqtcore.common.metatileentities.multi.multiblockpart.MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[13])), 13));
        registerMetaTileEntity(15546, new keqing.gtqtcore.common.metatileentities.multi.multiblockpart.MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[14])), 14));
        INF_WATER_HATCH = registerMetaTileEntity(15547,new MetaTileInfWaterHatch(gtqtcoreId("infinite_water_hatch")));
        CATALYST_HATCH = registerMetaTileEntity(15548,new MetaTileEntityCatalystHatch(gtqtcoreId("catalyst_hatch")));
        MULTIPART_BUFFER_HATCH = registerMetaTileEntity(15549, new MetaTileEntityBufferHatch(gtqtcoreId("buffer_hatch")));
        MULTIPART_BALL_HATCH = registerMetaTileEntity(15550, new MetaTileEntityMillBallHatch(gtqtcoreId("mill_ball_hatch")));
        EDATA_ACCESS_HATCH = registerMetaTileEntity(15551, new MetaTileEntityDataAccessHatch(gtqtcoreId("edata_access_hatch"), GTValues.MV, false));
        FDATA_ACCESS_HATCH = registerMetaTileEntity(15552, new MetaTileEntityDataAccessHatch(gtqtcoreId("fdata_access_hatch"), GTValues.UV, false));

        MULTIPART_REINFORCED_ROTOR_HOLDER[0] = registerMetaTileEntity(15553, new MetaTileEntityReinforcedRotorHolder(gtqtcoreId("reinforced_rotor_holder.luv"), 6));
        MULTIPART_REINFORCED_ROTOR_HOLDER[1] = registerMetaTileEntity(15554, new MetaTileEntityReinforcedRotorHolder(gtqtcoreId("reinforced_rotor_holder.zpm"), 7));
        MULTIPART_REINFORCED_ROTOR_HOLDER[2] = registerMetaTileEntity(15555, new MetaTileEntityReinforcedRotorHolder(gtqtcoreId("reinforced_rotor_holder.uv"), 8));
        MULTIPART_REINFORCED_ROTOR_HOLDER[3] = registerMetaTileEntity(15556, new MetaTileEntityReinforcedRotorHolder(gtqtcoreId("reinforced_rotor_holder.uhv"), 9));
        MULTIPART_REINFORCED_ROTOR_HOLDER[4] = registerMetaTileEntity(15557, new MetaTileEntityReinforcedRotorHolder(gtqtcoreId("reinforced_rotor_holder.uev"), 10));
        MULTIPART_REINFORCED_ROTOR_HOLDER[5] = registerMetaTileEntity(15558, new MetaTileEntityReinforcedRotorHolder(gtqtcoreId("reinforced_rotor_holder.uiv"), 11));
        MULTIPART_REINFORCED_ROTOR_HOLDER[6] = registerMetaTileEntity(15559, new MetaTileEntityReinforcedRotorHolder(gtqtcoreId("reinforced_rotor_holder.uxv"), 12));
        MULTIPART_REINFORCED_ROTOR_HOLDER[7] = registerMetaTileEntity(15560, new MetaTileEntityReinforcedRotorHolder(gtqtcoreId("reinforced_rotor_holder.opv"), 13));
        HV_MACHINE_HATCH = registerMetaTileEntity(15561, new MetaTileEntityMachineHatch(gtqtcoreId("hv_machine_hatch"), 3));
        STERILE_CLEANING_MAINTENANCE_HATCH = registerMetaTileEntity(15562, new MetaTileEntitySterileCleaningMaintenanceHatch(gtqtcoreId("maintenance_hatch_sterile_cleanroom_auto")));
        ISO3_CLEANING_MAINTENANCE_HATCH = registerMetaTileEntity(15563, new MetaTileEntityISO3CleaningMaintenanceHatch(gtqtcoreId("maintenance_hatch_iso_3_cleanroom_auto")));
        ISO2_CLEANING_MAINTENANCE_HATCH = registerMetaTileEntity(15564, new MetaTileEntityISO2CleaningMaintenanceHatch(gtqtcoreId("maintenance_hatch_iso_2_cleanroom_auto")));
        ISO1_CLEANING_MAINTENANCE_HATCH = registerMetaTileEntity(15565, new MetaTileEntityISO1CleaningMaintenanceHatch(gtqtcoreId("maintenance_hatch_iso_1_cleanroom_auto")));

        ROTOR_HOLDER[6] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityRotorHolder(gtqtcoreId("rotor_holder.uhv"), GTValues.UHV));
        ROTOR_HOLDER[7] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityRotorHolder(gtqtcoreId("rotor_holder.uev"), GTValues.UEV));
        ROTOR_HOLDER[8] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityRotorHolder(gtqtcoreId("rotor_holder.uiv"), GTValues.UIV));
        ROTOR_HOLDER[9] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityRotorHolder(gtqtcoreId("rotor_holder.uxv"), GTValues.UXV));
        ROTOR_HOLDER[10] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityRotorHolder(gtqtcoreId("rotor_holder.opv"), GTValues.OpV));
        ROTOR_HOLDER[11] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityRotorHolder(gtqtcoreId("rotor_holder.max"), GTValues.MAX));

        /////////////////////////////////////////
        //  ID 14000-14003: UEV-OpV 4A Energy Hatches.
        INPUT_ENERGY_HATCH_4A[0] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.input_4a.uev"), UEV, 4, false));
        INPUT_ENERGY_HATCH_4A[1] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.input_4a.uiv"), UIV, 4, false));
        INPUT_ENERGY_HATCH_4A[2] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.input_4a.uxv"), UXV, 4, false));
        INPUT_ENERGY_HATCH_4A[3] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.input_4a.opv"), OpV, 4, false));

        //  ID 14004-14007: UEV-OpV 16A Energy Hatches.
        INPUT_ENERGY_HATCH_16A[0] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.input_16a.uev"), UEV, 16, false));
        INPUT_ENERGY_HATCH_16A[1] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.input_16a.uiv"), UIV, 16, false));
        INPUT_ENERGY_HATCH_16A[2] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.input_16a.uxv"), UXV, 16, false));
        INPUT_ENERGY_HATCH_16A[3] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.input_16a.opv"), OpV, 16, false));


        //  ID 14008-14014: LV-HV and UEV-OpV 4A Dynamo Hatches.
        OUTPUT_ENERGY_HATCH_4A[0] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.output_4a.lv"), LV, 4, true));
        OUTPUT_ENERGY_HATCH_4A[1] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.output_4a.mv"), MV, 4, true));
        OUTPUT_ENERGY_HATCH_4A[2] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.output_4a.hv"), HV, 4, true));

        OUTPUT_ENERGY_HATCH_4A[3] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.output_4a.uev"), UEV, 4, true));
        OUTPUT_ENERGY_HATCH_4A[4] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.output_4a.uiv"), UIV, 4, true));
        OUTPUT_ENERGY_HATCH_4A[5] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.output_4a.uxv"), UXV, 4, true));
        OUTPUT_ENERGY_HATCH_4A[6] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.output_4a.opv"), OpV, 4, true));


        //  ID 14015-14022: LV-EV and UEV-OpV 16A Dynamo Hatches.
        OUTPUT_ENERGY_HATCH_16A[0] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.output_16a.lv"), LV, 16, true));
        OUTPUT_ENERGY_HATCH_16A[1] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.output_16a.mv"), MV, 16, true));
        OUTPUT_ENERGY_HATCH_16A[2] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.output_16a.hv"), HV, 16, true));
        OUTPUT_ENERGY_HATCH_16A[3] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.output_16a.ev"), EV, 16, true));

        OUTPUT_ENERGY_HATCH_16A[4] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.output_16a.uev"), UEV, 16, true));
        OUTPUT_ENERGY_HATCH_16A[5] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.output_16a.uiv"), UIV, 16, true));
        OUTPUT_ENERGY_HATCH_16A[6] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.output_16a.uxv"), UXV, 16, true));
        OUTPUT_ENERGY_HATCH_16A[7] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedEnergyHatch(gtqtcoreId("energy_hatch.output_16a.opv"), OpV, 16, true));

        SUBSTATION_INPUT_ENERGY_HATCH[0] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedSubstationEnergyHatch(gtqtcoreId("substation_hatch.input_64a.uev"), UEV, 64, false));
        SUBSTATION_INPUT_ENERGY_HATCH[1] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedSubstationEnergyHatch(gtqtcoreId("substation_hatch.input_64a.uiv"), UIV, 64, false));
        SUBSTATION_INPUT_ENERGY_HATCH[2] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedSubstationEnergyHatch(gtqtcoreId("substation_hatch.input_64a.uxv"), UXV, 64, false));
        SUBSTATION_INPUT_ENERGY_HATCH[3] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedSubstationEnergyHatch(gtqtcoreId("substation_hatch.input_64a.opv"), OpV, 64, false));

        SUBSTATION_OUTPUT_ENERGY_HATCH[0] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedSubstationEnergyHatch(gtqtcoreId("substation_hatch.output_64a.lv"), LV, 64, true));
        SUBSTATION_OUTPUT_ENERGY_HATCH[1] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedSubstationEnergyHatch(gtqtcoreId("substation_hatch.output_64a.mv"), MV, 64, true));
        SUBSTATION_OUTPUT_ENERGY_HATCH[2] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedSubstationEnergyHatch(gtqtcoreId("substation_hatch.output_64a.hv"), HV, 64, true));
        SUBSTATION_OUTPUT_ENERGY_HATCH[3] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedSubstationEnergyHatch(gtqtcoreId("substation_hatch.output_64a.ev"), EV, 64, true));

        SUBSTATION_OUTPUT_ENERGY_HATCH[4] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedSubstationEnergyHatch(gtqtcoreId("substation_hatch.output_64a.uev"), UEV, 64, true));
        SUBSTATION_OUTPUT_ENERGY_HATCH[5] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedSubstationEnergyHatch(gtqtcoreId("substation_hatch.output_64a.uiv"), UIV, 64, true));
        SUBSTATION_OUTPUT_ENERGY_HATCH[6] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedSubstationEnergyHatch(gtqtcoreId("substation_hatch.output_64a.uxv"), UXV, 64, true));
        SUBSTATION_OUTPUT_ENERGY_HATCH[7] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedSubstationEnergyHatch(gtqtcoreId("substation_hatch.output_64a.opv"), OpV, 64, true));


        //  ID 14035-14111: IV-OpV 16384A, 65536A, 262144A, 1048576A Laser Target/Source Hatches.
        for (int i = 0; i < (GregTechAPI.isHighTier() ? LASER_INPUT_HATCH_256.length - 1 : Math.min(LASER_INPUT_HATCH_256.length - 1, 4)); ++i) {
            String voltageName = VN[i + 5].toLowerCase();
            LASER_INPUT_HATCH_16384A[i] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedLaserHatch(gtqtcoreId("laser_hatch.target_16384a." + voltageName), i + 5, 16384, false));
            LASER_INPUT_HATCH_65536A[i] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedLaserHatch(gtqtcoreId("laser_hatch.target_65536a." + voltageName), i + 5, 65536, false));
            LASER_INPUT_HATCH_262144A[i] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedLaserHatch(gtqtcoreId("laser_hatch.target_262144a." + voltageName), i + 5, 262144, false));
            LASER_INPUT_HATCH_1048576A[i] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedLaserHatch(gtqtcoreId("laser_hatch.target_1048576a." + voltageName), i + 5, 1048576, false));
            LASER_OUTPUT_HATCH_16384A[i] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedLaserHatch(gtqtcoreId("laser_hatch.source_16384a." + voltageName), i + 5, 16384, true));
            LASER_OUTPUT_HATCH_65536A[i] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedLaserHatch(gtqtcoreId("laser_hatch.source_65536a." + voltageName), i + 5, 65536, true));
            LASER_OUTPUT_HATCH_262144A[i] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedLaserHatch(gtqtcoreId("laser_hatch.source_262144a." + voltageName), i + 5, 262144, true));
            LASER_OUTPUT_HATCH_1048576A[i] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedLaserHatch(gtqtcoreId("laser_hatch.source_1048576a." + voltageName), i + 5, 1048576, true));
        }

            //  ID 14443-14446: UEV-OpV Item Import Hatches.
            IMPORT_ITEM_HATCH[0] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedItemBus(gtqtcoreId("item_hatch.import.uev"), UEV, false));
            IMPORT_ITEM_HATCH[1] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedItemBus(gtqtcoreId("item_hatch.import.uiv"), UIV, false));
            IMPORT_ITEM_HATCH[2] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedItemBus(gtqtcoreId("item_hatch.import.uxv"), UXV, false));
            IMPORT_ITEM_HATCH[3] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedItemBus(gtqtcoreId("item_hatch.import.opv"), OpV, false));


            //  ID 14447-14450: UEV-OpV Item Export Hatches.
            EXPORT_ITEM_HATCH[0] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedItemBus(gtqtcoreId("item_hatch.export.uev"), UEV, true));
            EXPORT_ITEM_HATCH[1] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedItemBus(gtqtcoreId("item_hatch.export.uiv"), UIV, true));
            EXPORT_ITEM_HATCH[2] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedItemBus(gtqtcoreId("item_hatch.export.uxv"), UXV, true));
            EXPORT_ITEM_HATCH[3] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedItemBus(gtqtcoreId("item_hatch.export.opv"), OpV, true));


            //  ID 14451-14454: UEV-OpV Fluid Import Hatches.
            IMPORT_FLUID_HATCH[0] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedFluidHatch(gtqtcoreId("fluid_hatch.import.uev"), UEV, false));
            IMPORT_FLUID_HATCH[1] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedFluidHatch(gtqtcoreId("fluid_hatch.import.uiv"), UIV, false));
            IMPORT_FLUID_HATCH[2] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedFluidHatch(gtqtcoreId("fluid_hatch.import.uxv"), UXV, false));
            IMPORT_FLUID_HATCH[3] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedFluidHatch(gtqtcoreId("fluid_hatch.import.opv"), OpV, false));


            //  ID 14455-14458: UEV-OpV Fluid Export Hatches.
            EXPORT_FLUID_HATCH[0] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedFluidHatch(gtqtcoreId("fluid_hatch.export.uev"), UEV, true));
            EXPORT_FLUID_HATCH[1] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedFluidHatch(gtqtcoreId("fluid_hatch.export.uiv"), UIV, true));
            EXPORT_FLUID_HATCH[2] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedFluidHatch(gtqtcoreId("fluid_hatch.export.uxv"), UXV, true));
            EXPORT_FLUID_HATCH[3] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedFluidHatch(gtqtcoreId("fluid_hatch.export.opv"), OpV, true));

            //  ID 14459-14462: UEV-OpV Quadruple Fluid Import Hatches.
            QUADRUPLE_IMPORT_FLUID_HATCH[0] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedMultiFluidHatch(gtqtcoreId("fluid_hatch.import_4x.uev"), UEV, 4, false));
            QUADRUPLE_IMPORT_FLUID_HATCH[1] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedMultiFluidHatch(gtqtcoreId("fluid_hatch.import_4x.uiv"), UIV, 4, false));
            QUADRUPLE_IMPORT_FLUID_HATCH[2] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedMultiFluidHatch(gtqtcoreId("fluid_hatch.import_4x.uxv"), UXV, 4, false));
            QUADRUPLE_IMPORT_FLUID_HATCH[3] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedMultiFluidHatch(gtqtcoreId("fluid_hatch.import_4x.opv"), OpV, 4, false));


            //  ID 14463-14466: UEV-OpV Nonuple Fluid Import Hatches.
            NONUPLE_IMPORT_FLUID_HATCH[0] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedMultiFluidHatch(gtqtcoreId("fluid_hatch.import_9x.uev"), UEV, 9, false));
            NONUPLE_IMPORT_FLUID_HATCH[1] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedMultiFluidHatch(gtqtcoreId("fluid_hatch.import_9x.uiv"), UIV, 9, false));
            NONUPLE_IMPORT_FLUID_HATCH[2] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedMultiFluidHatch(gtqtcoreId("fluid_hatch.import_9x.uxv"), UXV, 9, false));
            NONUPLE_IMPORT_FLUID_HATCH[3] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedMultiFluidHatch(gtqtcoreId("fluid_hatch.import_9x.opv"), OpV, 9, false));


            //  ID 14467-14470: UEV-OpV Quadruple Fluid Export Hatches.
            QUADRUPLE_EXPORT_FLUID_HATCH[0] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedMultiFluidHatch(gtqtcoreId("fluid_hatch.export_4x.uev"), UEV, 4, true));
            QUADRUPLE_EXPORT_FLUID_HATCH[1] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedMultiFluidHatch(gtqtcoreId("fluid_hatch.export_4x.uiv"), UIV, 4, true));
            QUADRUPLE_EXPORT_FLUID_HATCH[2] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedMultiFluidHatch(gtqtcoreId("fluid_hatch.export_4x.uxv"), UXV, 4, true));
            QUADRUPLE_EXPORT_FLUID_HATCH[3] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedMultiFluidHatch(gtqtcoreId("fluid_hatch.export_4x.opv"), OpV, 4, true));


            //  ID 14471-14474: UEV-OpV Nonuple Fluid Export Hatches.
            NONUPLE_EXPORT_FLUID_HATCH[0] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedMultiFluidHatch(gtqtcoreId("fluid_hatch.export_9x.uev"), UEV, 9, true));
            NONUPLE_EXPORT_FLUID_HATCH[1] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedMultiFluidHatch(gtqtcoreId("fluid_hatch.export_9x.uiv"), UIV, 9, true));
            NONUPLE_EXPORT_FLUID_HATCH[2] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedMultiFluidHatch(gtqtcoreId("fluid_hatch.export_9x.uxv"), UXV, 9, true));
            NONUPLE_EXPORT_FLUID_HATCH[3] = registerMetaTileEntity(getMaterialsId(), new MetaTileEntityAdvancedMultiFluidHatch(gtqtcoreId("fluid_hatch.export_9x.opv"), OpV, 9, true));

            simpleTiredInit(CREATIVE_ENERGY_HATCHES,
                (i) -> new MetaTileEntityCreativeEnergyHatch(gtqtcoreId("creative_energy_hatch."+GTValues.VN[i].toLowerCase()),i),
                GTQTMetaTileEntities::nextMultiPartID);

    }

    private static <T extends MetaTileEntity> T registerSingleMetaTileEntity(int id, T mte) {
        if (id > 300) return null;
        return MetaTileEntities.registerMetaTileEntity(id + 20000, mte);
    }

}