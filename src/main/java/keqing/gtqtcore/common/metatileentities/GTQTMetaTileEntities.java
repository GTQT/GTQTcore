package keqing.gtqtcore.common.metatileentities;

import gregicality.multiblocks.common.metatileentities.multiblockpart.MetaTileEntityParallelHatch;
import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SimpleGeneratorMetaTileEntity;
import gregtech.api.unification.material.Materials;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.common.metatileentities.multi.electric.generator.MetaTileEntityLargeTurbine;
import gregtech.common.metatileentities.multi.multiblockpart.*;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.metatileentities.multi.generators.*;
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
    public static MetaTileEntityMultiblockTank HUGE_TANK;
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
    public static MetaTileEntityLargeTurbine HUGE_STEAM_TURBINE;
    public static MetaTileEntityLargeTurbine HUGE_GAS_TURBINE;
    public static MetaTileEntityLargeTurbine HUGE_PLASMA_TURBINE;
    public static final SimpleGeneratorMetaTileEntity[] NAQUADAH_REACTOR = new SimpleGeneratorMetaTileEntity[3];
    public static final SimpleGeneratorMetaTileEntity[] ROCKET_ENGINE = new SimpleGeneratorMetaTileEntity[3];
    public static MetaTileEntityTurbineCombustionChamber HUGE_TURBINE_COMBUSTION_CHAMBER;
    public static MetaTileEntityIModularFissionReactor I_MODULAR_FISSION_REACTOR;
    public static MetaTileEntityRocket ROCKET;
    public static MetaTileEntitySteamOreWasher STEAM_ORE_WASHER;
    public static MetaTileEntitySteamBlastFurnace STEAM_BLAST_FURANCE;

    public static MetaTileEntityHugeDistillationTower HUGE_DISTILLATION_TOWER;
    public static MetaTileEntityHugeVacuum HUGE_VACUUM;
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
    public static MetaTileEntityFracturing FLUID_DRILLING_RIG;
    public static MetaTileEntityFracturing ADVANCED_FLUID_DRILLING_RIG;
    public static MetaTileEntityLargeNaquadahReactor LARGE_NAQUADAH_REACTOR;
    public static MetaTileEntityHugeElectricImplosionCompressor HUGE_ELECTRRIC_IMPLOSION_COMPRESSOR;
    public static final MetaTileEntityMultiFluidHatch[] BIG_IMPORT_HATCH = new MetaTileEntityMultiFluidHatch[6]; // EV-UHV
    public static final MetaTileEntityMultiFluidHatch[] BIG_EXPORT_HATCH = new MetaTileEntityMultiFluidHatch[6]; // EV-UHV

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

    public static MetaTileEntityCatalystHatch CATALYST_HATCH;
    public static MetaTileEntityChemicalPlant CHEMICAL_PLANT;
    public static final MetaTileEntityRotorHolder[] ROTOR_HOLDER = new MetaTileEntityRotorHolder[12]; //HV, EV, IV, LuV, ZPM, UV

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

        HUGE_DISTILLATION_TOWER = registerMetaTileEntity(3098, new MetaTileEntityHugeDistillationTower(gtqtcoreId("huge_distillation_tower")));
        HUGE_VACUUM = registerMetaTileEntity(3097, new MetaTileEntityHugeVacuum(gtqtcoreId("huge_vacuum")));
        HUGE_ELECTRRIC_IMPLOSION_COMPRESSOR = registerMetaTileEntity(3096, new MetaTileEntityHugeElectricImplosionCompressor(gtqtcoreId("huge_electric_implosion_compressor")));
        QUANTUM_FORCE_TRANSFORMER = registerMetaTileEntity(3095, new MetaTileEntityQuantumForceTransformer(gtqtcoreId("quantum_force_transform")));
        NAQUADAH_REACTOR_MKI = registerMetaTileEntity(3094, new MetaTileEntityNaquadahReactorMki(gtqtcoreId("naquadah_reactor_mki")));
        NAQUADAH_REACTOR_MKII = registerMetaTileEntity(3093, new MetaTileEntityNaquadahReactorMkii(gtqtcoreId("naquadah_reactor_mkii")));
        NAQUADAH_REACTOR_MKIII = registerMetaTileEntity(3092, new MetaTileEntityNaquadahReactorMkiii(gtqtcoreId("naquadah_reactor_mkiii")));
        LAGER_HEAT_EXCHANGER = registerMetaTileEntity(3091, new MetaTileEntityLagerHeatExchanger(gtqtcoreId("lager_heat_exchanger")));
        DANGOTE_DISTILLERY = registerMetaTileEntity(3090, new MetaTileEntityDangoteDistillery(gtqtcoreId("dangote_distillery")));
        COMPRESSED_FUSION_REACTOR_MKI = registerMetaTileEntity(3020,new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor_mki"),9));
        COMPRESSED_FUSION_REACTOR_MKII = registerMetaTileEntity(3021,new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor_mkii"),10));
        COMPRESSED_FUSION_REACTOR_MKIII = registerMetaTileEntity(3022,new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor_mkiii"),11));
        BASIC_HUGE_MINER = registerMetaTileEntity(15000, new MetaTileEntityHugeMiner(gtqtcoreId("large_miner.zpm"), GTValues.ZPM, 1, 9, 7, Materials.Steel, 64));
        HUGE_MINER = registerMetaTileEntity(15001, new MetaTileEntityHugeMiner(gtqtcoreId("large_miner.uv"), GTValues.UV, 1, 11, 8, Materials.Titanium, 128));
        ADVANCED_HUGE_MINER = registerMetaTileEntity(15002, new MetaTileEntityHugeMiner(gtqtcoreId("large_miner.uhv"), GTValues.UHV, 1, 13, 9, Materials.TungstenSteel, 256));
        HUGE_FUSION_REACTOR[0] = registerMetaTileEntity(15003, new MetaTileEntityHugeFusionReactor(gtqtcoreId("fusion_reactor.uhv"), GTValues.UHV));
        HUGE_FUSION_REACTOR[1] = registerMetaTileEntity(15004, new MetaTileEntityHugeFusionReactor(gtqtcoreId("fusion_reactor.uev"), GTValues.UEV));
        HUGE_FUSION_REACTOR[2] = registerMetaTileEntity(15005, new MetaTileEntityHugeFusionReactor(gtqtcoreId("fusion_reactor.uiv"), GTValues.UIV));
        STAR_BIOMIMETIC_FACTORY= registerMetaTileEntity(15006, new MetaTileEntityStarBiomimeticFactory(gtqtcoreId("star_biomimetic_factory")));
        PLASMA_FORGE= registerMetaTileEntity(15007, new MetaTileEntityPlasmaForge(gtqtcoreId("plasma_forge")));
        STAR_MIXER= registerMetaTileEntity(15008, new MetaTileEntityStarMixer(gtqtcoreId("star_mixer")));
        KeQing_NET= registerMetaTileEntity(15009, new MetaTileEntitykeQingNet(gtqtcoreId("keqing_net")));
        HYPER_REACTOR_MK1 = registerMetaTileEntity(15010, new MetaTileEntityHyperReactorMk1(gtqtcoreId("hyper_reactor_mk1")));
        HYPER_REACTOR_MK2 = registerMetaTileEntity(15011, new MetaTileEntityHyperReactorMk2(gtqtcoreId("hyper_reactor_mk2")));
        HYPER_REACTOR_MK3 = registerMetaTileEntity(15012, new MetaTileEntityHyperReactorMk3(gtqtcoreId("hyper_reactor_mk3")));
        LARGE_NAQUADAH_REACTOR = registerMetaTileEntity(15013, new MetaTileEntityLargeNaquadahReactor(gtqtcoreId("large_naquadah_reactor")));
        FUEL_REFINE_FACTORY = registerMetaTileEntity(15014, new MetaTileEntityFuelRefineFactory(gtqtcoreId("fuel_refine_factory")));
        NAQUADAH_REACTOR[0] = registerMetaTileEntity(15015, new SimpleGeneratorMetaTileEntity(gtqtcoreId("naquadah_reactor.iv"), GTQTcoreRecipeMaps.NAQUADAH_REACTOR_RECIPES, GTQTTextures.NAQUADAH_REACTOR_OVERLAY, 5, genericGeneratorTankSizeFunction));
        NAQUADAH_REACTOR[1] = registerMetaTileEntity(15016, new SimpleGeneratorMetaTileEntity(gtqtcoreId("naquadah_reactor.luv"), GTQTcoreRecipeMaps.NAQUADAH_REACTOR_RECIPES, GTQTTextures.NAQUADAH_REACTOR_OVERLAY, 6, genericGeneratorTankSizeFunction));
        NAQUADAH_REACTOR[2] = registerMetaTileEntity(15017, new SimpleGeneratorMetaTileEntity(gtqtcoreId("naquadah_reactor.zpm"),  GTQTcoreRecipeMaps.NAQUADAH_REACTOR_RECIPES, GTQTTextures.NAQUADAH_REACTOR_OVERLAY, 7, genericGeneratorTankSizeFunction));
        ROCKET_ENGINE[0] = registerMetaTileEntity(15018, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rocket_engine.ev"), GTQTcoreRecipeMaps.ROCKET, GTQTTextures.ROCKET_ENGINE_OVERLAY, 4, genericGeneratorTankSizeFunction));
        ROCKET_ENGINE[1] = registerMetaTileEntity(15019, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rocket_engine.iv"), GTQTcoreRecipeMaps.ROCKET, GTQTTextures.ROCKET_ENGINE_OVERLAY, 5, genericGeneratorTankSizeFunction));
        ROCKET_ENGINE[2] = registerMetaTileEntity(15020, new SimpleGeneratorMetaTileEntity(gtqtcoreId("rocket_engine.luv"), GTQTcoreRecipeMaps.ROCKET, GTQTTextures.ROCKET_ENGINE_OVERLAY, 6, genericGeneratorTankSizeFunction));
        DISSOLUTION_TANK = registerMetaTileEntity(15021, new MetaTileEntityDissolutionTank(gtqtcoreId("dissolution_tank")));
        FERMENTATION_TANK= registerMetaTileEntity(15022, new MetaTileEntityFermentationTank(gtqtcoreId("fermentation_tank")));
        MULTIPART_BUFFER_HATCH = registerMetaTileEntity(15023, new MetaTileEntityBufferHatch(gtqtcoreId("buffer_hatch")));
        DIGESTER = registerMetaTileEntity(15024, new MetaTileEntityDigester(gtqtcoreId("digester")));
        COMPONENT_ASSEMBLY_LINE = registerMetaTileEntity(15025, new MetaTileEntityComponentAssemblyLine(gtqtcoreId("component_assembly_line")));
        BASIC_FLUID_DRILLING_RIG = registerMetaTileEntity(15026, new MetaTileEntityFracturing(gtqtcoreId("fracturing.mv"), 2));
        FLUID_DRILLING_RIG = registerMetaTileEntity(15027, new MetaTileEntityFracturing(gtqtcoreId("fracturing.hv"), 3));
        ADVANCED_FLUID_DRILLING_RIG = registerMetaTileEntity(15028, new MetaTileEntityFracturing(gtqtcoreId("fracturing.ev"), 4));
        STEAM_COMPRESSOR = registerMetaTileEntity(15029, new MetaTileEntitySteamCompressor(gtqtcoreId("steam_compressor")));
        STEAM_EXTRACTOR = registerMetaTileEntity(15030, new MetaTileEntitySteamExtractor(gtqtcoreId("steam_extractor")));
        HUGE_TANK = registerMetaTileEntity(15031,
                new MetaTileEntityMultiblockTank(gtqtcoreId("tank.steel"), true, 1000 * 1000));

        registerMetaTileEntity(3105, new MetaTileEntityFluidHatch(gtqtcoreId("fluid_hatch.import.uev"), 11, false));
        registerMetaTileEntity(3120, new MetaTileEntityFluidHatch(gtqtcoreId("fluid_hatch.export.uev"), 11, true));

        registerMetaTileEntity(3023, new MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[9])), 9));
        registerMetaTileEntity(3024, new MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[10])), 10));
        registerMetaTileEntity(3025, new MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[11])), 11));
        registerMetaTileEntity(3026, new MetaTileEntityParallelHatch(gtqtcoreId(String.format("parallel_hatch.%s", GTValues.VN[12])), 12));

        for (int i = GTValues.IV; i <= GTValues.UHV; i++) {
            int index = i - GTValues.IV;
            String tierName = GTValues.VN[i].toLowerCase();
            BIG_IMPORT_HATCH[index + 1] = registerMetaTileEntity(3030 + index, new MetaTileEntityMultiFluidHatch(gtqtcoreId("fluid_hatch.import_16x." + tierName), i, 16, false));
            BIG_EXPORT_HATCH[index + 1] = registerMetaTileEntity(3050 + index, new MetaTileEntityMultiFluidHatch(gtqtcoreId("fluid_hatch.export_16x." + tierName), i, 16, true));
        }


        ROTOR_HOLDER[6] = registerMetaTileEntity(3233, new MetaTileEntityRotorHolder(gtqtcoreId("rotor_holder.uhv"), GTValues.UHV));
        ROTOR_HOLDER[7] = registerMetaTileEntity(3134, new MetaTileEntityRotorHolder(gtqtcoreId("rotor_holder.uev"), GTValues.UEV));
        ROTOR_HOLDER[8] = registerMetaTileEntity(3135, new MetaTileEntityRotorHolder(gtqtcoreId("rotor_holder.uiv"), GTValues.UIV));
        ROTOR_HOLDER[9] = registerMetaTileEntity(3136, new MetaTileEntityRotorHolder(gtqtcoreId("rotor_holder.uxv"), GTValues.UXV));
        ROTOR_HOLDER[10] = registerMetaTileEntity(3137, new MetaTileEntityRotorHolder(gtqtcoreId("rotor_holder.opv"), GTValues.OpV));
        ROTOR_HOLDER[11] = registerMetaTileEntity(3138, new MetaTileEntityRotorHolder(gtqtcoreId("rotor_holder.max"), GTValues.MAX));

        PLUS_ENERGY_INPUT_HATCH[1] = registerMetaTileEntity(3150, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input.uev"), 10, 2, false));
        PLUS_ENERGY_OUTPUT_HATCH[1] = registerMetaTileEntity(3151, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output.uev"), 10, 2, true));
        PLUS_ENERGY_INPUT_HATCH_4A[1] = registerMetaTileEntity(3152, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_4a.uev"), 10,4, false));
        PLUS_ENERGY_INPUT_HATCH_16A[1] = registerMetaTileEntity(3153, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_16a.uev"), 10, 16, false));
        PLUS_ENERGY_OUTPUT_HATCH_4A[1] = registerMetaTileEntity(3154, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_4a.uev"), 10, 4, true));
        PLUS_ENERGY_OUTPUT_HATCH_16A[1] = registerMetaTileEntity(3155, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_16a.uev"), 10, 16, true));

        PLUS_ENERGY_INPUT_HATCH[2] = registerMetaTileEntity(3156, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input.uiv"), 11, 2, false));
        PLUS_ENERGY_OUTPUT_HATCH[2] = registerMetaTileEntity(3157, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output.uiv"), 11, 2, true));
        PLUS_ENERGY_INPUT_HATCH_4A[2] = registerMetaTileEntity(3158, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_4a.uiv"), 11,4, false));
        PLUS_ENERGY_INPUT_HATCH_16A[2] = registerMetaTileEntity(3159, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_16a.uiv"), 11, 16, false));
        PLUS_ENERGY_OUTPUT_HATCH_4A[2] = registerMetaTileEntity(3160, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_4a.uiv"), 11, 4, true));
        PLUS_ENERGY_OUTPUT_HATCH_16A[2] = registerMetaTileEntity(3161, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_16a.uiv"), 11, 16, true));

        PLUS_ENERGY_INPUT_HATCH[3] = registerMetaTileEntity(3162, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input.uxv"), 12, 2, false));
        PLUS_ENERGY_OUTPUT_HATCH[3] = registerMetaTileEntity(3163, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output.uxv"), 12, 2, true));
        PLUS_ENERGY_INPUT_HATCH_4A[3] = registerMetaTileEntity(3164, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_4a.uxv"), 12,4, false));
        PLUS_ENERGY_INPUT_HATCH_16A[3] = registerMetaTileEntity(3165, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_16a.uxv"), 12, 16, false));
        PLUS_ENERGY_OUTPUT_HATCH_4A[3] = registerMetaTileEntity(3166, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_4a.uxv"), 12, 4, true));
        PLUS_ENERGY_OUTPUT_HATCH_16A[3] = registerMetaTileEntity(3167, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_16a.uxv"), 12, 16, true));

        PLUS_ENERGY_INPUT_HATCH[4] = registerMetaTileEntity(3168, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input.opv"), 13, 2, false));
        PLUS_ENERGY_OUTPUT_HATCH[4] = registerMetaTileEntity(3169, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output.opv"), 13, 2, true));
        PLUS_ENERGY_INPUT_HATCH_4A[4] = registerMetaTileEntity(3170, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_4a.opv"), 13,4, false));
        PLUS_ENERGY_INPUT_HATCH_16A[4] = registerMetaTileEntity(3171, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_16a.opv"), 13, 16, false));
        PLUS_ENERGY_OUTPUT_HATCH_4A[4] = registerMetaTileEntity(3172, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_4a.opv"), 13, 4, true));
        PLUS_ENERGY_OUTPUT_HATCH_16A[4] = registerMetaTileEntity(3173, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_16a.opv"), 13, 16, true));

        PLUS_ENERGY_INPUT_HATCH_64A[1] = registerMetaTileEntity(3200, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_64a.uev"), 10, 64, false));
        PLUS_ENERGY_OUTPUT_HATCH_64A[1] = registerMetaTileEntity(3201, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_64a.uev"), 10, 64, true));
        PLUS_ENERGY_INPUT_HATCH_128A[1] = registerMetaTileEntity(3202, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_128a.uev"), 10,128, false));
        PLUS_ENERGY_INPUT_HATCH_512A[1] = registerMetaTileEntity(3203, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_512a.uev"), 10, 512, false));
        PLUS_ENERGY_OUTPUT_HATCH_128A[1] = registerMetaTileEntity(3204, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_128a.uev"), 10, 128, true));
        PLUS_ENERGY_OUTPUT_HATCH_512A[1] = registerMetaTileEntity(3205, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_512a.uev"), 10, 512, true));

        PLUS_ENERGY_INPUT_HATCH_64A[2] = registerMetaTileEntity(3206, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_64a.uiv"), 11, 64, false));
        PLUS_ENERGY_OUTPUT_HATCH_64A[2] = registerMetaTileEntity(3207, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_64a.uiv"), 11, 64, true));
        PLUS_ENERGY_INPUT_HATCH_128A[2] = registerMetaTileEntity(3208, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_128a.uiv"), 11,128, false));
        PLUS_ENERGY_INPUT_HATCH_512A[2] = registerMetaTileEntity(3209, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_512a.uiv"), 11, 512, false));
        PLUS_ENERGY_OUTPUT_HATCH_128A[2] = registerMetaTileEntity(3210, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_128a.uiv"), 11, 128, true));
        PLUS_ENERGY_OUTPUT_HATCH_512A[2] = registerMetaTileEntity(3211, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_512a.uiv"), 11, 512, true));

        PLUS_ENERGY_INPUT_HATCH_64A[3] = registerMetaTileEntity(3212, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_64a.uxv"), 12, 64, false));
        PLUS_ENERGY_OUTPUT_HATCH_64A[3] = registerMetaTileEntity(3213, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_64a.uxv"), 12, 64, true));
        PLUS_ENERGY_INPUT_HATCH_128A[3] = registerMetaTileEntity(3214, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_128a.uxv"), 12,128, false));
        PLUS_ENERGY_INPUT_HATCH_512A[3] = registerMetaTileEntity(3215, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_512a.uxv"), 12, 512, false));
        PLUS_ENERGY_OUTPUT_HATCH_128A[3] = registerMetaTileEntity(3216, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_128a.uxv"), 12, 128, true));
        PLUS_ENERGY_OUTPUT_HATCH_512A[3] = registerMetaTileEntity(3217, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_512a.uxv"), 12, 512, true));

        PLUS_ENERGY_INPUT_HATCH_64A[4] = registerMetaTileEntity(3218, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_64a.opv"), 13, 64, false));
        PLUS_ENERGY_OUTPUT_HATCH_64A[4] = registerMetaTileEntity(3219, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_64a.opv"), 13, 64, true));
        PLUS_ENERGY_INPUT_HATCH_128A[4] = registerMetaTileEntity(3220, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_128a.opv"), 13,128, false));
        PLUS_ENERGY_INPUT_HATCH_512A[4] = registerMetaTileEntity(3221, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.input_512a.opv"), 13, 512, false));
        PLUS_ENERGY_OUTPUT_HATCH_128A[4] = registerMetaTileEntity(3222, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_128a.opv"), 13, 128, true));
        PLUS_ENERGY_OUTPUT_HATCH_512A[4] = registerMetaTileEntity(3223, new MetaTileEntityPlusEnergyHatch(gtqtcoreId("energy_hatch.output_512a.opv"), 13, 512, true));

        simpleTiredInit(CREATIVE_ENERGY_HATCHES,
                (i) -> new MetaTileEntityCreativeEnergyHatch(gtqtcoreId("creative_energy_hatch."+GTValues.VN[i].toLowerCase()),i),
                GTQTMetaTileEntities::nextMultiPartID);

    }

    private static <T extends MetaTileEntity> T registerSingleMetaTileEntity(int id, T mte) {
        if (id > 300) return null;
        return MetaTileEntities.registerMetaTileEntity(id + 20000, mte);
    }

}