package keqing.gtqtcore.common.metatileentities;

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
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.metatileentities.multi.generators.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.steam.MetaTileEntitySteamExtractor;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.steam.MetaTileEntitySteamBlastFurnace;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.steam.MetaTileEntitySteamCompressor;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.steam.MetaTileEntitySteamOreWasher;
import keqing.gtqtcore.common.metatileentities.multi.multiblockpart.*;
import keqing.gtqtcore.common.metatileentities.storage.MetaTileEntityMultiblockTank;

import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;

import static gregtech.api.util.GTUtility.genericGeneratorTankSizeFunction;
import static gregtech.common.metatileentities.MetaTileEntities.*;
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
    public static int currentMultiPartID = 3300;
    private static int nextMultiPartID(){
        currentMultiPartID++;
        return currentMultiPartID;
    }
    public static void simpleTiredInit(MetaTileEntity[] tileEntities, IntFunction<MetaTileEntity> function, IntSupplier idSupplier){
        simpleTiredInit(tileEntities,function,idSupplier,(i) -> true);
    }
    public static MetaTileEntityOilPool OIL_POOL;
    public static MetaTileEntityMultiblockTank HUGE_TANK;
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
    public static  MetaTileEntityStarBiomimeticFactory STAR_BIOMIMETIC_FACTORY;
    public static  MetaTileEntityPlasmaForge PLASMA_FORGE;
    public static MetaTileEntityStarMixer STAR_MIXER;
    public static MetaTileEntitySteamCompressor STEAM_COMPRESSOR;
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
    public static MetaTileEntityChemicalPlant CHEMICAL_PLANT;
    public static final MetaTileEntityRotorHolder[] ROTOR_HOLDER = new MetaTileEntityRotorHolder[12]; //HV, EV, IV, LuV, ZPM, UV

    public static final SimpleMachineMetaTileEntity[] FLUID_CANNER = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static final SimpleMachineMetaTileEntity[] FLUID_EXTRACTOR = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];

    public static MetaTileEntityKQCCComputationHatch[] KQCC_COMPUTATION_HATCH_RECEIVER=new MetaTileEntityKQCCComputationHatch[7];
    public static MetaTileEntityKQCCComputationHatch[] KQCC_COMPUTATION_HATCH_TRANSMITTER=new MetaTileEntityKQCCComputationHatch[7];
    //public static MetaTileEntityObjectHolder OBJECT_HOLDER;
    public static void initialization() {
        GTQTLog.logger.info("Registering MetaTileEntities");

        LIGHTNING_ROD[0] = registerSingleMetaTileEntity(0, new MetaTileEntityLightningRod(gtqtcoreId("lightning_rod.zpm"), GTValues.ZPM));
        LIGHTNING_ROD[1] = registerSingleMetaTileEntity(1, new MetaTileEntityLightningRod(gtqtcoreId("lightning_rod.luv"), GTValues.LuV));
        LIGHTNING_ROD[2] = registerSingleMetaTileEntity(2, new MetaTileEntityLightningRod(gtqtcoreId("lightning_rod.iv"), GTValues.IV));

        HUGE_CHEMICAL_REACTOR = registerMetaTileEntity(3000, new MetaTileEntityHugeChemicalReactor(gtqtcoreId("huge_chemical_reactor")));
        BLAZING_BLAST_FURNACE = registerMetaTileEntity(3001, new MetaTileEntityBlazingBlastFurnace(gtqtcoreId("blazing_blast_furnace")));
        CHEMICAL_PLANT = registerMetaTileEntity(3002,new MetaTileEntityChemicalPlant(gtqtcoreId("chemical_plant")));
        INF_WATER_HATCH = registerMetaTileEntity(3003,new MetaTileInfWaterHatch(gtqtcoreId("infinite_water_hatch")));
        CATALYST_HATCH = registerMetaTileEntity(3004,new MetaTileEntityCatalystHatch(gtqtcoreId("catalyst_hatch")));
        HUGE_MACERATOR = registerMetaTileEntity(3005,new MetaTileEntityHugeMacerator(gtqtcoreId("huge_macerator")));
        HUGE_ALLOY_BLAST_FURANCE = registerMetaTileEntity(3006,new MetaTileEntityHugeAlloyBlastSmelter(gtqtcoreId("huge_alloy_blast_smelter")));
        HUGE_BLAST_FURANCE = registerMetaTileEntity(3007,new MetaTileEntityHugeBlastFurnace(gtqtcoreId("huge_blast_furnace")));
        INTEGRATED_MINING_DIVISION = registerMetaTileEntity(3013, new MetaTileEntityIntegratedMiningDivision(gtqtcoreId("integrated_mining_division")));
        HUGE_TURBINE_COMBUSTION_CHAMBER = registerMetaTileEntity(3014, new MetaTileEntityTurbineCombustionChamber(gtqtcoreId("turbine_combustion_chamber"),4));
        ROCKET = registerMetaTileEntity(3015, new MetaTileEntityRocket(gtqtcoreId("rocket"),7));
        I_MODULAR_FISSION_REACTOR = registerMetaTileEntity(3016, new MetaTileEntityIModularFissionReactor(gtqtcoreId("i_modular_fission_reactor"),5));
        HUGE_CRACKING_UNIT = registerMetaTileEntity(3017, new MetaTileEntityHugeCrackingUnit(gtqtcoreId("huge_cracking_unit")));
        STEAM_BLAST_FURANCE = registerMetaTileEntity(3018, new MetaTileEntitySteamBlastFurnace(gtqtcoreId("steam_blast_furance")));
        STEAM_ORE_WASHER = registerMetaTileEntity(3019, new MetaTileEntitySteamOreWasher(gtqtcoreId("steam_ore_washer")));
        COMPRESSED_FUSION_REACTOR_MKI = registerMetaTileEntity(3020,new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor_mki"),9));
        COMPRESSED_FUSION_REACTOR_MKII = registerMetaTileEntity(3021,new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor_mkii"),10));
        COMPRESSED_FUSION_REACTOR_MKIII = registerMetaTileEntity(3022,new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor_mkiii"),11));
        registerMetaTileEntity(3023, new MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[9])), 9));
        registerMetaTileEntity(3024, new MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[10])), 10));
        registerMetaTileEntity(3025, new MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[11])), 11));
        registerMetaTileEntity(3026, new MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[12])), 12));
        HUGE_DISTILLATION_TOWER = registerMetaTileEntity(3027, new MetaTileEntityHugeDistillationTower(gtqtcoreId("huge_distillation_tower")));
        HUGE_VACUUM = registerMetaTileEntity(3028, new MetaTileEntityHugeVacuum(gtqtcoreId("huge_vacuum")));
        HUGE_ELECTRRIC_IMPLOSION_COMPRESSOR = registerMetaTileEntity(3029, new MetaTileEntityHugeElectricImplosionCompressor(gtqtcoreId("huge_electric_implosion_compressor")));
        QUANTUM_FORCE_TRANSFORMER = registerMetaTileEntity(3030, new MetaTileEntityQuantumForceTransformer(gtqtcoreId("quantum_force_transform")));
        NAQUADAH_REACTOR_MKI = registerMetaTileEntity(3031, new MetaTileEntityNaquadahReactorMki(gtqtcoreId("naquadah_reactor_mki")));
        NAQUADAH_REACTOR_MKII = registerMetaTileEntity(3032, new MetaTileEntityNaquadahReactorMkii(gtqtcoreId("naquadah_reactor_mkii")));
        NAQUADAH_REACTOR_MKIII = registerMetaTileEntity(3033, new MetaTileEntityNaquadahReactorMkiii(gtqtcoreId("naquadah_reactor_mkiii")));
        LAGER_HEAT_EXCHANGER = registerMetaTileEntity(3034, new MetaTileEntityLagerHeatExchanger(gtqtcoreId("lager_heat_exchanger")));
        DANGOTE_DISTILLERY = registerMetaTileEntity(3035, new MetaTileEntityDangoteDistillery(gtqtcoreId("dangote_distillery")));
        BASIC_HUGE_MINER = registerMetaTileEntity(3036, new MetaTileEntityHugeMiner(gtqtcoreId("large_miner.zpm"), GTValues.ZPM, 1, 8, 7,  64));
        HUGE_MINER = registerMetaTileEntity(3037, new MetaTileEntityHugeMiner(gtqtcoreId("large_miner.uv"), GTValues.UV, 2, 10, 8,  128));
        ADVANCED_HUGE_MINER = registerMetaTileEntity(3038, new MetaTileEntityHugeMiner(gtqtcoreId("large_miner.uhv"), GTValues.UHV, 3, 12, 9 , 256));
        HUGE_FUSION_REACTOR[0] = registerMetaTileEntity(3039, new MetaTileEntityHugeFusionReactor(gtqtcoreId("fusion_reactor.uhv"), GTValues.UHV));
        HUGE_FUSION_REACTOR[1] = registerMetaTileEntity(3040, new MetaTileEntityHugeFusionReactor(gtqtcoreId("fusion_reactor.uev"), GTValues.UEV));
        HUGE_FUSION_REACTOR[2] = registerMetaTileEntity(3041, new MetaTileEntityHugeFusionReactor(gtqtcoreId("fusion_reactor.uiv"), GTValues.UIV));
        STAR_BIOMIMETIC_FACTORY= registerMetaTileEntity(3042, new MetaTileEntityStarBiomimeticFactory(gtqtcoreId("star_biomimetic_factory")));
        PLASMA_FORGE= registerMetaTileEntity(3043, new MetaTileEntityPlasmaForge(gtqtcoreId("plasma_forge")));
        STAR_MIXER= registerMetaTileEntity(3044, new MetaTileEntityStarMixer(gtqtcoreId("star_mixer")));
        KeQing_NET= registerMetaTileEntity(3045, new MetaTileEntitykeQingNet(gtqtcoreId("keqing_net")));
        HYPER_REACTOR_MK1 = registerMetaTileEntity(3046, new MetaTileEntityHyperReactorMk1(gtqtcoreId("hyper_reactor_mk1")));
        HYPER_REACTOR_MK2 = registerMetaTileEntity(3047, new MetaTileEntityHyperReactorMk2(gtqtcoreId("hyper_reactor_mk2")));
        HYPER_REACTOR_MK3 = registerMetaTileEntity(3048, new MetaTileEntityHyperReactorMk3(gtqtcoreId("hyper_reactor_mk3")));
        LARGE_NAQUADAH_REACTOR = registerMetaTileEntity(3049, new MetaTileEntityLargeNaquadahReactor(gtqtcoreId("large_naquadah_reactor")));
        FUEL_REFINE_FACTORY = registerMetaTileEntity(3050, new MetaTileEntityFuelRefineFactory(gtqtcoreId("fuel_refine_factory")));
        NAQUADAH_REACTOR[0] = registerMetaTileEntity(3051, new SimpleGeneratorMetaTileEntity(gtqtcoreId("naquadah_reactor.iv"), GTQTcoreRecipeMaps.NAQUADAH_REACTOR_RECIPES, GTQTTextures.NAQUADAH_REACTOR_OVERLAY, 5, genericGeneratorTankSizeFunction));
        NAQUADAH_REACTOR[1] = registerMetaTileEntity(3052, new SimpleGeneratorMetaTileEntity(gtqtcoreId("naquadah_reactor.luv"), GTQTcoreRecipeMaps.NAQUADAH_REACTOR_RECIPES, GTQTTextures.NAQUADAH_REACTOR_OVERLAY, 6, genericGeneratorTankSizeFunction));
        NAQUADAH_REACTOR[2] = registerMetaTileEntity(3053, new SimpleGeneratorMetaTileEntity(gtqtcoreId("naquadah_reactor.zpm"),  GTQTcoreRecipeMaps.NAQUADAH_REACTOR_RECIPES, GTQTTextures.NAQUADAH_REACTOR_OVERLAY, 7, genericGeneratorTankSizeFunction));
        ROCKET_ENGINE[0] = registerMetaTileEntity(3054, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rocket_engine.ev"), GTQTcoreRecipeMaps.ROCKET, GTQTTextures.ROCKET_ENGINE_OVERLAY, 4, genericGeneratorTankSizeFunction));
        ROCKET_ENGINE[1] = registerMetaTileEntity(3055, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rocket_engine.iv"), GTQTcoreRecipeMaps.ROCKET, GTQTTextures.ROCKET_ENGINE_OVERLAY, 5, genericGeneratorTankSizeFunction));
        ROCKET_ENGINE[2] = registerMetaTileEntity(3056, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rocket_engine.luv"), GTQTcoreRecipeMaps.ROCKET, GTQTTextures.ROCKET_ENGINE_OVERLAY, 6, genericGeneratorTankSizeFunction));
        DISSOLUTION_TANK = registerMetaTileEntity(3057, new MetaTileEntityDissolutionTank(gtqtcoreId("dissolution_tank")));
        FERMENTATION_TANK= registerMetaTileEntity(3058, new MetaTileEntityFermentationTank(gtqtcoreId("fermentation_tank")));
        MULTIPART_BUFFER_HATCH = registerMetaTileEntity(3059, new MetaTileEntityBufferHatch(gtqtcoreId("buffer_hatch")));
        DIGESTER = registerMetaTileEntity(3060, new MetaTileEntityDigester(gtqtcoreId("digester")));
        COMPONENT_ASSEMBLY_LINE = registerMetaTileEntity(3061, new MetaTileEntityComponentAssemblyLine(gtqtcoreId("component_assembly_line")));
        BASIC_FLUID_DRILLING_RIG = registerMetaTileEntity(3062, new MetaTileEntityFracturing(gtqtcoreId("fracturing.mv"), 2));
        FLUID_DRILLING_RIG = registerMetaTileEntity(3063, new MetaTileEntityFracturing(gtqtcoreId("fracturing.hv"), 3));
        ADVANCED_FLUID_DRILLING_RIG = registerMetaTileEntity(3064, new MetaTileEntityFracturing(gtqtcoreId("fracturing.ev"), 4));
        STEAM_COMPRESSOR = registerMetaTileEntity(3065, new MetaTileEntitySteamCompressor(gtqtcoreId("steam_compressor")));
        STEAM_EXTRACTOR = registerMetaTileEntity(3066, new MetaTileEntitySteamExtractor(gtqtcoreId("steam_extractor")));
        HUGE_TANK = registerMetaTileEntity(3067,
                new MetaTileEntityMultiblockTank(gtqtcoreId("tank.steel"), true, 1000 * 1000));

        // Diesel Generator, IDs 935-949
        COMBUSTION_GENERATOR[0] = registerMetaTileEntity(3068, new MetaTileEntitySingleCombustion(gtqtcoreId("combustion_generator.ev"), RecipeMaps.COMBUSTION_GENERATOR_FUELS, Textures.COMBUSTION_GENERATOR_OVERLAY, 4, genericGeneratorTankSizeFunction));
        COMBUSTION_GENERATOR[1] = registerMetaTileEntity(3069, new MetaTileEntitySingleCombustion(gtqtcoreId("combustion_generator.iv"), RecipeMaps.COMBUSTION_GENERATOR_FUELS, Textures.COMBUSTION_GENERATOR_OVERLAY, 5, genericGeneratorTankSizeFunction));
        // Steam Turbine, IDs 950-964
        STEAM_TURBINE[0] = registerMetaTileEntity(3070, new MetaTileEntitySingleTurbine(gtqtcoreId("steam_turbine.ev"), RecipeMaps.STEAM_TURBINE_FUELS, Textures.STEAM_TURBINE_OVERLAY, 4, GTUtility.steamGeneratorTankSizeFunction));
        STEAM_TURBINE[1] = registerMetaTileEntity(3071, new MetaTileEntitySingleTurbine(gtqtcoreId("steam_turbine.iv"), RecipeMaps.STEAM_TURBINE_FUELS, Textures.STEAM_TURBINE_OVERLAY, 5, GTUtility.steamGeneratorTankSizeFunction));

        // Gas Turbine, IDs 965-979
        GAS_TURBINE[0] = registerMetaTileEntity(3072, new MetaTileEntitySingleTurbine(gtqtcoreId("gas_turbine.ev"), RecipeMaps.GAS_TURBINE_FUELS, Textures.GAS_TURBINE_OVERLAY, 4, genericGeneratorTankSizeFunction));
        GAS_TURBINE[1] = registerMetaTileEntity(3073, new MetaTileEntitySingleTurbine(gtqtcoreId("gas_turbine.iv"), RecipeMaps.GAS_TURBINE_FUELS, Textures.GAS_TURBINE_OVERLAY, 5, genericGeneratorTankSizeFunction));

        INDUSTRIAL_PRIMITIVE_BLAST_FURNACE = registerMetaTileEntity(3074, new MetaTileEntityIndustrialPrimitiveBlastFurnace(gtqtcoreId("industrial_primitive_blast_furnace")));
        SEPTIC_TANK = registerMetaTileEntity(3075, new MetaTileEntitySepticTank(gtqtcoreId("septic_tank")));
        PCB = registerMetaTileEntity(3076, new MetaTileEntityPCB(gtqtcoreId("pcb")));
        INDUSTRIAL_FISHING_POND = registerMetaTileEntity(3077, new MetaTileEntityIndustrialFishingPond(gtqtcoreId("IndustrialFishingPond")));
        ISA_MILL = registerMetaTileEntity(3078, new MetaTileEntityIsaMill(gtqtcoreId("isa_mill")));
        MULTIPART_BALL_HATCH = registerMetaTileEntity(3079, new MetaTileEntityMillBallHatch(gtqtcoreId("mill_ball_hatch")));
        FLOTATION_FACTORY = registerMetaTileEntity(3080, new MetaTileEntityFlotationFactory(gtqtcoreId("flotation_factory")));
        VACUUM_DRYING_FURNACE = registerMetaTileEntity(3081, new MetaTileEntityVacuumDryingFurnace(gtqtcoreId("vacuum_drying_furnace")));
        ALGAE_FARM = registerMetaTileEntity(3082, new MetaTileEntityAlgaeFarm(gtqtcoreId("algae_farm")));
        LAGER_PROCESSING_FACTORY = registerMetaTileEntity(3083, new MetaTileEntityLargeProcessingFactory(gtqtcoreId("large_processing_factory")));
        ELECTROBATH = registerMetaTileEntity(3084, new MetaTileEntityElectrobath(gtqtcoreId("electrobath")));
        ALLOY_KILN = registerMetaTileEntity(3085, new MetaTileEntityAlloykiln(gtqtcoreId("alloy_klin")));
        SALT_FLIED = registerMetaTileEntity(3086, new MetaTileEntitySaltField(gtqtcoreId("salt_flied")));
        DISTILLATION_TOWER = registerMetaTileEntity(3087, new MetaTileEntityDistillationTower(gtqtcoreId("distillation_tower")));
        CRACKER = registerMetaTileEntity(3088, new MetaTileEntityCrackingUnit(gtqtcoreId("cracker")));
        PYROLYSE_OVEN = registerMetaTileEntity(3089, new MetaTileEntityPyrolyseOven(gtqtcoreId("pyrolyse_oven")));
        VACUUM_FREEZER = registerMetaTileEntity(3090, new MetaTileEntityVacuumFreezer(gtqtcoreId("vacuum_freezer")));
        PYROLYSIS_TOWER = registerMetaTileEntity(3091, new MetaTileEntityPyrolysisTower(gtqtcoreId("pyrolysis_tower")));
        PRECISE_ASSEMBLER = registerMetaTileEntity(3092, new MetaTileEntityPreciseAssembler(gtqtcoreId("precise_assembler")));
        PARTICLE_ACCELERATOR = registerMetaTileEntity(3093, new MetaTileEntityParticleAccelerator(gtqtcoreId("particle_accelerator")));
        LARGE_CHEMICAL_REACTOR = registerMetaTileEntity(3094, new MetaTileEntityLargeChemicalReactor(gtqtcoreId("large_chemical_reactor")));
        BIOLOGICAL_REACTION=registerMetaTileEntity(3095,new MetaTileEntityBiologicalReaction(gtqtcoreId(("biological_reaction"))));
        OIL_POOL=registerMetaTileEntity(3096,new MetaTileEntityOilPool(gtqtcoreId(("oil_pool"))));
        MINING_DRILL=registerMetaTileEntity(3097,new MetaTileEntityMiningDrill(gtqtcoreId(("mining_drill"))));
        DISTILLATION_KETTLE=registerMetaTileEntity(3098,new MetaTileEntityDistillationKettle(gtqtcoreId(("distillation_kettle"))));
        OCEAN_PUMPER = registerMetaTileEntity(3099, new MetaTileEntityOceanPumper(gtqtcoreId("ocean_pumper")));
        SOLAR_PLATE = registerMetaTileEntity(3100, new MetaTileEntitySolarPlate(gtqtcoreId("solar_plate")));
        P_REACTOR= registerMetaTileEntity(3101, new MetaTileEntityPReactor(gtqtcoreId("p_reactor")));
        EFUSION_REACTOR[0] = registerMetaTileEntity(3102, new MetaTileEntityEFusionReactor(gtqtcoreId("fusion_reactor.luv"), GTValues.LuV));
        EFUSION_REACTOR[1] = registerMetaTileEntity(3103, new MetaTileEntityEFusionReactor(gtqtcoreId("fusion_reactor.zpm"), GTValues.ZPM));
        EFUSION_REACTOR[2] = registerMetaTileEntity(3104, new MetaTileEntityEFusionReactor(gtqtcoreId("fusion_reactor.uv"), GTValues.UV));
        KQCC= registerMetaTileEntity(3105, new MetaTileEntityKQCC(gtqtcoreId("kqcc")));

        registerSimpleMetaTileEntity(FLUID_EXTRACTOR, 15000, "fluid_extractor", GTQTcoreRecipeMaps.FLUID_EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(FLUID_CANNER, 15015, "fluid_canner", GTQTcoreRecipeMaps.FLUID_CANNER_RECIPES, Textures.CANNER_OVERLAY, true,GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);

        registerSimpleMetaTileEntity(COMPONENT_ASSEMBLER, 15030, "component_assembler", GTQTcoreRecipeMaps.COMPONENT_ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(DECAY_CHAMBER, 15045, "decay_chamber", GTQTcoreRecipeMaps.DECAY_CHAMBER_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(VACUUM_CHAMBER, 15060, "vacuum_chamber", GTQTcoreRecipeMaps.VACUUM_CHAMBER_RECIPES, Textures.GAS_COLLECTOR_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);
        registerSimpleMetaTileEntity(ULTRAVIOLET_LAMP_CHAMBER, 15075, "ultraviolet_lamp_chamber", GTQTcoreRecipeMaps.ULTRAVIOLET_LAMP_CHAMBER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, true, GTQTUtil::gtqtId, GTUtility.hvCappedTankSizeFunction);

        for (int i = 1; i <= 6; i++) {
            String tierName = GTValues.VN[i].toLowerCase();
            KQCC_COMPUTATION_HATCH_RECEIVER[i] = registerMetaTileEntity(15500 + i-1, new MetaTileEntityKQCCComputationHatch(gtqtcoreId("kqcccomputation_hatch.receiver." + tierName), i,  false));
            KQCC_COMPUTATION_HATCH_TRANSMITTER[i] = registerMetaTileEntity(15506 + i-1, new MetaTileEntityKQCCComputationHatch(gtqtcoreId("kqcccomputation_hatch.transmitter." + tierName), i,  true));
        }

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