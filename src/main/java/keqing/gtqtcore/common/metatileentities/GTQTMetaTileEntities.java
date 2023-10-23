package keqing.gtqtcore.common.metatileentities;

import gregicality.multiblocks.common.metatileentities.multiblockpart.MetaTileEntityParallelHatch;
import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockTurbineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.common.metatileentities.multi.electric.generator.MetaTileEntityLargeTurbine;
import gregtech.common.metatileentities.multi.multiblockpart.*;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.common.metatileentities.multi.generators.MetaTileEntityIModularFissionReactor;
import keqing.gtqtcore.common.metatileentities.multi.generators.MetaTileEntityLightningRod;
import keqing.gtqtcore.common.metatileentities.multi.generators.MetaTileEntityRocket;
import keqing.gtqtcore.common.metatileentities.multi.generators.MetaTileEntityTurbineCombustionChamber;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.MetaTileEntityMultiblockTank;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.*;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.steam.MetaTileEntitySteamBlastFurnace;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.steam.MetaTileEntitySteamOreWasher;
import keqing.gtqtcore.common.metatileentities.multi.multiblockpart.*;

import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;

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
    public static MetaTileEntityBlazingBlastFurnace BLAZING_BLAST_FURNACE ;
    public static MetaTileEntityHugeChemicalReactor HUGE_CHEMICAL_REACTOR;
    public static MetaTileEntityIntegratedMiningDivision INTEGRATED_MINING_DIVISION;
    public static MetaTileEntityHugeMacerator HUGE_MACERATOR;
    public static MetaTileEntityHugeAlloyBlastSmelter HUGE_ALLOY_BLAST_FURANCE;
    public static MetaTileEntityHugeBlastFurnace HUGE_BLAST_FURANCE;
    public static MetaTileEntitySpaceDrilling SPACE_DRILLING;
    public static MetaTileEntityCompressedFusionReactor COMPRESSED_FUSION_REACTOR_MKI;
    public static MetaTileEntityCompressedFusionReactor COMPRESSED_FUSION_REACTOR_MKII;
    public static MetaTileEntityCompressedFusionReactor COMPRESSED_FUSION_REACTOR_MKIII;
    public static final MetaTileEntityCreativeEnergyHatch[] CREATIVE_ENERGY_HATCHES = new MetaTileEntityCreativeEnergyHatch[GTValues.V.length];
    public static MetaTileInfWaterHatch INF_WATER_HATCH;
    public static MetaTileEntityLightningRod[] LIGHTNING_ROD = new MetaTileEntityLightningRod[3];

    public static MetaTileEntityLargeTurbine HUGE_STEAM_TURBINE;
    public static MetaTileEntityLargeTurbine HUGE_GAS_TURBINE;
    public static MetaTileEntityLargeTurbine HUGE_PLASMA_TURBINE;
    public static MetaTileEntityTurbineCombustionChamber HUGE_TURBINE_COMBUSTION_CHAMBER;
    public static MetaTileEntityIModularFissionReactor I_MODULAR_FISSION_REACTOR;
    public static MetaTileEntityRocket ROCKET;
    public static MetaTileEntitySteamOreWasher STEAM_ORE_WASHER;
    public static MetaTileEntitySteamBlastFurnace STEAM_BLAST_FURANCE;
    public static MetaTileEntityMultiblockTank STEEL_TANK;
    public static MetaTileEntityAssemblyLineTower ASSEMBLY_LINE_TOWER;
    public static MetaTileEntityHugeDistillationTower HUGE_DISTILLATION_TOWER;
    public static MetaTileEntityHugeVacuum HUGE_VACUUM;
    public static MetaTileEntityQuantumForceTransformer QUANTUM_FORCE_TRANSFORMER;
    public static MetaTileEntityHugeElectricImplosionCompressor HUGE_ELECTRRIC_IMPLOSION_COMPRESSOR;
    public static final MetaTileEntityMultiFluidHatch[] QUADRUPLE_IMPORT_HATCH = new MetaTileEntityMultiFluidHatch[6]; // EV-UHV
    public static final MetaTileEntityMultiFluidHatch[] NONUPLE_IMPORT_HATCH = new MetaTileEntityMultiFluidHatch[6]; // EV-UHV
    public static final MetaTileEntityMultiFluidHatch[] QUADRUPLE_EXPORT_HATCH = new MetaTileEntityMultiFluidHatch[6]; // EV-UHV
    public static final MetaTileEntityMultiFluidHatch[] NONUPLE_EXPORT_HATCH = new MetaTileEntityMultiFluidHatch[6]; // EV-UHV
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
        HUGE_STEAM_TURBINE = registerMetaTileEntity(3010, new MetaTileEntityLargeTurbine(gtqtcoreId("huge_turbine.steam"), RecipeMaps.STEAM_TURBINE_FUELS, 9, MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STEEL_TURBINE_CASING), MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STEEL_GEARBOX), Textures.SOLID_STEEL_CASING, false, Textures.LARGE_STEAM_TURBINE_OVERLAY));
        HUGE_GAS_TURBINE = registerMetaTileEntity(3011, new MetaTileEntityLargeTurbine(gtqtcoreId("huge_turbine.gas"), RecipeMaps.GAS_TURBINE_FUELS, 10, MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STAINLESS_TURBINE_CASING), MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STAINLESS_STEEL_GEARBOX), Textures.CLEAN_STAINLESS_STEEL_CASING, true, Textures.LARGE_GAS_TURBINE_OVERLAY));
        HUGE_PLASMA_TURBINE = registerMetaTileEntity(3012, new MetaTileEntityLargeTurbine(gtqtcoreId("huge_turbine.plasma"), RecipeMaps.PLASMA_GENERATOR_FUELS, 10, MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TUNGSTENSTEEL_TURBINE_CASING), MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TUNGSTENSTEEL_GEARBOX), Textures.ROBUST_TUNGSTENSTEEL_CASING, false, Textures.LARGE_PLASMA_TURBINE_OVERLAY));
        INTEGRATED_MINING_DIVISION = registerMetaTileEntity(3013, new MetaTileEntityIntegratedMiningDivision(gtqtcoreId("integrated_mining_division")));
        HUGE_TURBINE_COMBUSTION_CHAMBER = registerMetaTileEntity(3014, new MetaTileEntityTurbineCombustionChamber(gtqtcoreId("turbine_combustion_chamber"),4));
        ROCKET = registerMetaTileEntity(3015, new MetaTileEntityRocket(gtqtcoreId("rocket"),5));
        I_MODULAR_FISSION_REACTOR = registerMetaTileEntity(3016, new MetaTileEntityIModularFissionReactor(gtqtcoreId("i_modular_fission_reactor"),5));
        SPACE_DRILLING = registerMetaTileEntity(3017, new MetaTileEntitySpaceDrilling(gtqtcoreId("space_drilling")));
        STEAM_BLAST_FURANCE = registerMetaTileEntity(3018, new MetaTileEntitySteamBlastFurnace(gtqtcoreId("steam_blast_furance")));
        STEAM_ORE_WASHER = registerMetaTileEntity(3019, new MetaTileEntitySteamOreWasher(gtqtcoreId("steam_ore_washer")));
        ASSEMBLY_LINE_TOWER = registerMetaTileEntity(3099, new MetaTileEntityAssemblyLineTower(gtqtcoreId("assembly_line_tower")));
        HUGE_DISTILLATION_TOWER = registerMetaTileEntity(3098, new MetaTileEntityHugeDistillationTower(gtqtcoreId("huge_distillation_tower")));
        HUGE_VACUUM = registerMetaTileEntity(3097, new MetaTileEntityHugeVacuum(gtqtcoreId("huge_vacuum")));
        HUGE_ELECTRRIC_IMPLOSION_COMPRESSOR = registerMetaTileEntity(3096, new MetaTileEntityHugeElectricImplosionCompressor(gtqtcoreId("huge_electric_implosion_compressor")));
        QUANTUM_FORCE_TRANSFORMER = registerMetaTileEntity(3095, new MetaTileEntityQuantumForceTransformer(gtqtcoreId("quantum_force_transform")));
        COMPRESSED_FUSION_REACTOR_MKI = registerMetaTileEntity(3020,new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor_mki"),9));
        COMPRESSED_FUSION_REACTOR_MKII = registerMetaTileEntity(3021,new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor_mkii"),10));
        COMPRESSED_FUSION_REACTOR_MKIII = registerMetaTileEntity(3022,new MetaTileEntityCompressedFusionReactor(gtqtcoreId("compressed_fusion_reactor_mkiii"),11));
        STEEL_TANK = registerMetaTileEntity(3100, new MetaTileEntityMultiblockTank(gtqtcoreId("tank.steel"), true, 1000));

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